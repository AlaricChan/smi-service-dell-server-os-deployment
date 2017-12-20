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
package com.dell.isg.smi.osdeployment.common;

import java.net.InetAddress;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class OSDeployUtils.
 */
public class OSDeployUtils {
	
	/**
	 * This Method validates the IP address.
	 *
	 * @param ipAddress the IP to be validated
	 * @return true if reachable otherwise false
	 */
	public static boolean validateIPAddress(String ipAddress) {
		try {
			if (StringUtils.isNotBlank(ipAddress)) {
				return InetAddress.getByName(ipAddress).isReachable(2000);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
