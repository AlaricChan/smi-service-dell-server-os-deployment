/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dell.isg.smi.osdeployment.service;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.commons.utilities.command.CommandResponse;
import com.dell.isg.smi.commons.utilities.command.IssueCommands;
import com.dell.isg.smi.osdeployment.model.BootableIsoRequest;
import com.dell.isg.smi.osdeployment.service.iso.BootLoaderConfigurator;
import com.dell.isg.smi.osdeployment.service.iso.FileExtractor;
import com.dell.isg.smi.osdeployment.service.iso.FileExtractorImpl;

/**
 * The Class MiminalIsoServiceImpl.
 */
@Component
public class MiminalIsoServiceImpl implements MinimalIsoService {
    private static final Logger logger = LoggerFactory.getLogger(MiminalIsoServiceImpl.class);
    public static String SLASH = "/";

    @Autowired
    FileExtractor fileExtractor;

    @Autowired
    BootLoaderConfigurator bootLoaderConfigurator;


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.isg.smi.osdeployment.service.MinimalIsoService#createBootableIso(com.dell.isg.smi.osdeployment.model.BootableIsoRequest)
     */
    @Override
    public String createBootableIso(BootableIsoRequest payload) throws Exception {
        logger.trace("entering createBootableIso()");
        
        /**
         * load string variables from payload data
         */
        String tmpDir = UUID.randomUUID().toString();
        String destDir = payload.getDestinationDir();
        if (!destDir.endsWith("/")) {
            destDir = destDir + SLASH;
        }     
        String extractedDestDir = destDir + tmpDir;
        logger.debug("extracted Destination Dir: {}", extractedDestDir);

        String isoSourceDir = payload.getSourceDir();
        if (!isoSourceDir.endsWith("/")){
            isoSourceDir = isoSourceDir + SLASH;
        }
        logger.debug("iso source dir: {}", isoSourceDir);
        
        String isoFileName = payload.getFileName();
        isoFileName = this.removeSlash(isoFileName);
        logger.debug("iso file name: {}",isoFileName);
        
        String ksLocation = payload.getKsLocation();
        if (!ksLocation.endsWith("/")) {
            ksLocation = ksLocation + SLASH;
        }
        logger.debug("kickstart location: {}", ksLocation);

        String ksName = payload.getKickStartFileName();
        ksName = this.removeSlash(ksName);
        logger.debug("kikstart file name: {}", ksName);
        
        String bootConfigPath = extractedDestDir + SLASH;
        logger.debug("boot config path: {}", bootConfigPath);

        String destFileName = payload.getDestinationFileName();
        destFileName = this.removeSlash(destFileName);
        logger.debug("destination file name: {}", destFileName);
        
        
        /**
         * Perform the actions
         */
        String isoName = "";
        logger.info("Service layer - invoked for creating Bootable ISO");
        try{
            // Extracting ISO content to temporary staging directory
            String dirName = this.extractIsoContents(isoSourceDir, isoFileName, extractedDestDir);
            logger.info("content from ISO {} has been extracted to directoy {}", isoFileName, dirName);  
            
            // Create the boot loader configuration
            bootLoaderConfigurator.createBootLoaderConfigFile(ksName, ksLocation, bootConfigPath);
            logger.info("Bootloader configured for ISO file");
    
            // Re-packaging ISO after boot loader and kickstart file changes
            isoName = this.createBootableIsoImage(destFileName, destDir, extractedDestDir + SLASH);
            logger.info("ISO has been repackaged, and custom bootable ISO ready with name {}", isoName);
        }
        finally{
            // delete temporary staging directory
            File dir = new File(extractedDestDir);
            boolean isDeleted = deleteDirectory(dir);
            logger.info("temporary staging directory {} deleted successfully :", dir, isDeleted);
        }
        
        logger.trace("exiting createBootableIso() with iso name: {}", isoName);
        return isoName;
    }


    private String extractIsoContents(String srcDir, String isoFile, String destinationDir) {
        logger.debug("Extracting ISO contents from file {}{}  to  destination {}", srcDir, isoFile, destinationDir);
        try {
            MiminalIsoServiceImpl.createDirectory(destinationDir);
            FileExtractor fileExtractor = new FileExtractorImpl();
            String isoPath = srcDir + isoFile;
            String targetDir = fileExtractor.extractContentFromISO(isoPath, destinationDir, "");
            logger.debug("extractFilesFromISO completed");
            return targetDir;
        } catch (Exception e) {
            logger.error("error calling extractISOFiles: " + e.getMessage());
            if (e instanceof RuntimeCoreException) {
                throw (RuntimeCoreException) e;
            }
            RuntimeCoreException exception = new RuntimeCoreException();
            exception.setErrorID(267119);
            exception.addAttribute(e.getMessage());
            throw exception;
        }
    }


    private String createBootableIsoImage(String isoName, String destinationDir, String srcDir) {
        String mkisofsCmdStr = "genisoimage -relaxed-filenames -J -R -b isolinux.bin -c boot.cat -no-emul-boot -boot-load-size 4 -boot-info-table -o" + destinationDir + isoName + " " + srcDir;
        logger.info("system command to generate image -  {}", mkisofsCmdStr);
        try {
            CommandResponse response = IssueCommands.issueSystemCommand(mkisofsCmdStr);
            if (!response.getReturnCode().equals("0")) {
                throw new Exception(response.getReturnMessage());
            }
        } catch (Exception e) {
            logger.error("extractMininalISO failed with error: " + e.getMessage(), e);
            RuntimeCoreException exception = new RuntimeCoreException();
            exception.setErrorID(267007);
            exception.addAttribute(e.getMessage());
            throw exception;
        }
        return isoName;
    }


    /*
     * Utility method for eliminating unnecessary PRE or POST name slashes
     */
    private String removeSlash(String sourceString) {
        if (sourceString.startsWith(SLASH)) {
            sourceString = sourceString.substring(1);
        }
        if (sourceString.endsWith(SLASH)) {
            sourceString = sourceString.substring(0, sourceString.length() - 1);
        }
        return sourceString;
    }


    private static boolean deleteDirectory(File path) {
        logger.info("Deleting directory/file: " + path.getPath());
        try {
            if (path.exists()) {
                if (path.isFile()) {
                    return (path.delete());
                }

                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        if (files[i].isFile()) {
                            files[i].delete();
                        }
                    }
                }
            }
            return (path.delete());
        } catch (Exception e) {
            logger.error("Exception thrown while Deleting directory/file: " + path.getPath() + ".  Error is: " + e.getMessage() + " Error Type: " + e.toString(), e);
            return false;
        }
    }


    private static boolean createDirectory(String dirName) {
        logger.info("Creating directory/file: " + dirName);
        try {
            File dir = new File(dirName);
            if (dir.exists()) {
                logger.info("Directory: " + dirName + " already exists");

            }
            return (new File(dirName)).mkdirs();
        } catch (Exception e) {
            logger.error("Exception thrown while Creating directory/file: " + dirName + ".  Error is: " + e.getMessage(), e);
            return false;
        }
    }
}
