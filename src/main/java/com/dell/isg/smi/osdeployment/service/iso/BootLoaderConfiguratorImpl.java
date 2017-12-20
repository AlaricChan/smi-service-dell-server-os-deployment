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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dell.isg.smi.wsman.utilities.StreamUtils;

/**
 * The Class BootLoaderConfiguratorImpl.
 */
@Component
public class BootLoaderConfiguratorImpl implements BootLoaderConfigurator {

    private static final Logger logger = LoggerFactory.getLogger(BootLoaderConfiguratorImpl.class);


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.isg.smi.osdeployment.service.iso.BootLoaderConfigurator#createBootLoaderConfigFile(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean createBootLoaderConfigFile(String ksFileName, String ksFileLocation, String bootDir) throws IOException {
        logger.trace("entered createBootLoaderConfigFile() {}", ksFileName);
        String strDefault = "bootstate=0";
        boolean bFound = false;
        final String BOOT_CFG = "boot.cfg";
        logger.info("configuring bootloader for customize iso :  ksFileName {} , ksFileLocation {} , bootDir {} ", ksFileName, ksFileLocation, bootDir);

        /** Read the contents of the given file. */
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");

        String bootFileName = bootDir + BOOT_CFG;
        File fSourceStore = new File(bootFileName);

        FileReader frStore = null;
        BufferedReader brStore = null;
        FileWriter fwStore = null;
        try {
            frStore = new FileReader(fSourceStore);
            brStore = new BufferedReader(frStore);

            String incomming = "";
            int index = -1;

            boolean bSearch = true;
            while (bSearch == true) {
                incomming = brStore.readLine();
                if (incomming == null) {
                    bSearch = false;
                } else if ((index = incomming.toLowerCase().indexOf(strDefault)) != -1) {
                    bFound = true;
                    text.append(incomming + NL);
                } else if ((index = incomming.toLowerCase().indexOf("runweasel")) != -1 && bFound) {
                    if (incomming.indexOf("ks=") == -1) {
                        text.append(incomming.substring(0, index + "runweasel".length()) + " ks=" + ksFileLocation + ksFileName + NL);
                    }
                    {
                        // TBD
                    }

                } else {
                    // Keep information in file
                    text.append(incomming + NL);
                }
            }

            File fDestStore = new File(bootFileName);
            fwStore = new FileWriter(fDestStore);
            fwStore.write(text.toString());
        } finally {
            StreamUtils.closeStreamQuietly(brStore);
            StreamUtils.closeStreamQuietly(frStore);
            StreamUtils.closeStreamQuietly(fwStore);
        }

        logger.trace("exiting createBootLoaderConfigFile() {} with {}", ksFileName, bFound);
        return bFound;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.dell.isg.smi.osdeployment.service.iso.BootLoaderConfigurator#createKickStartFile()
     */
    @Override
    public String createKickStartFile() throws IOException {
        throw new NotImplementedException();
    }
}
