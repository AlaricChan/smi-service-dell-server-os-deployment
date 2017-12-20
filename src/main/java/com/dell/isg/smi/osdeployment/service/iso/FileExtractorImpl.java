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
package com.dell.isg.smi.osdeployment.service.iso;

import java.io.File;
import java.util.Enumeration;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.provider.UriParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.didion.loopy.iso9660.ISO9660FileEntry;
import net.didion.loopy.iso9660.ISO9660FileSystem;
import net.didion.loopy.vfs.provider.iso.IsoFileObject;
import net.didion.loopy.vfs.provider.iso.IsoFileSystem;

/**
 * The Class FileExtractorImpl. This class will read the ISO file and extract the files into a folder.
 */
@Component
public class FileExtractorImpl implements FileExtractor {
    private static final Logger logger = LoggerFactory.getLogger(FileExtractorImpl.class);


    /**
     * Extract content from ISO.
     *
     * @param isoFile the iso file
     * @param destFolder the dest folder
     * @param excludeFolder the exclude folder
     * @return the string
     * @throws Exception the exception
     */
    public String extractContentFromISO(String isoFile, String destFolder, String excludeFolder) throws Exception {
        logger.trace("entered extractContentFromISO()");

        // verify the iso file exists
        File fSource = new File(isoFile);
        if (!fSource.exists()) {
            logger.error("File: {} does not exists", isoFile);
            throw new RuntimeException("File: " + isoFile + " does not exists");
        }

        // open the source iso
        ISO9660FileSystem sourceIsoFileSystem = null;
        try {
            logger.debug("attempting to create an instance of the source iso file system");
            sourceIsoFileSystem = new ISO9660FileSystem(fSource, true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        // copy all of the iso files to the destination
        FileObject file = null;
        IsoFileSystem system = null;
        FileSystemManager fileSystemManager = null;
        FileObject destinationFolderFileObject = null;
        try {
            logger.debug("getting info about the source iso");
            file = VFS.getManager().resolveFile("iso:file:///" + isoFile);
            system = (IsoFileSystem) file.getFileSystem();

            logger.debug("resolving the destination folder");
            fileSystemManager = system.getFileSystemManager();
            destinationFolderFileObject = fileSystemManager.resolveFile(destFolder);

            logger.debug("beginning to extract the contents");
            @SuppressWarnings("rawtypes")
            Enumeration entries = sourceIsoFileSystem.getEntries();
            while (entries.hasMoreElements()) {
                ISO9660FileEntry entry = (ISO9660FileEntry) entries.nextElement();
                String name = entry.getPath();
                if ("".equals(name)) {
                    continue;
                }

                // use lower case for the destination name
                // if the last character in the name is a period, strip it out
                String nameLowerCase = name.toLowerCase();
                String destinationFileName = nameLowerCase;
                if (nameLowerCase.endsWith(".")) {
                    logger.debug("file name ends with a . {} ", nameLowerCase);
                    destinationFileName = nameLowerCase.substring(0, nameLowerCase.length() - 1);
                }
                logger.debug("Destination file name is: {}", destinationFileName);

                FileObject destinationFileObject = null;
                IsoFileObject fileObj = null;
                FileName filename = fileSystemManager.resolveName(system.getRootName(), UriParser.encode(name));
                try {
                    fileObj = (IsoFileObject) system.resolveFile(filename);
                    destinationFileObject = fileSystemManager.resolveFile(destinationFolderFileObject, destinationFileName);
                    destinationFileObject.copyFrom(fileObj, Selectors.SELECT_ALL);
                } catch (FileSystemException e) {
                    logger.error("exception while copying file: " + filename, e);
                    throw e;
                    // } catch (java.lang.IndexOutOfBoundsException ex) {
                    // logger.debug("caught index out of bounds exception", ex);
                    // continue;
                } finally {
                    if (null != fileObj) {
                        fileObj.close();
                    }
                    if (null != destinationFileObject) {
                        destinationFileObject.close();
                    }
                }
            }
            logger.debug("finished extracting the contents");
        } finally {
            if (null != destinationFolderFileObject) {
                destinationFolderFileObject.close();
            }
            if (null != system) {
                system.close();
            }
            if (null != file) {
                file.close();
            }
            if (null != sourceIsoFileSystem) {
                sourceIsoFileSystem.close();
            }
            if (null != fileSystemManager) {
                fileSystemManager.closeFileSystem(system);
            }
        }

        logger.trace("exiting extractContentFromISO()");
        return destFolder;
    }

}
