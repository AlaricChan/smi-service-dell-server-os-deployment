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

import com.dell.isg.smi.osdeployment.model.BootableIsoRequest;

/**
 * The Interface MinimalIsoService.
 */
public interface MinimalIsoService {
		
	/**
	 * Creates the bootable iso.
	 *
	 * @param payload the payload
	 * @return the string
	 * @throws Exception the exception
	 */
	public String createBootableIso(BootableIsoRequest payload) throws Exception; 
}
