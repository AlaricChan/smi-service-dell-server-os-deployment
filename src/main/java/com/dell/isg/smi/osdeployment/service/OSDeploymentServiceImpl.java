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
/**
 *
 */
package com.dell.isg.smi.osdeployment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dell.isg.smi.adapter.server.model.WsmanCredentials;
import com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload;
import com.dell.isg.smi.commons.model.server.JobStatus;
import com.dell.isg.smi.osdeployment.infrastructure.OSDeploymentInfrastructure;
import com.dell.isg.smi.osdeployment.model.Version;


/**
 * The Class OSDeploymentServiceImpl.
 */
@Component
public class OSDeploymentServiceImpl implements OSDeploymentService {

	@Autowired
	private OSDeploymentInfrastructure osDeploymentInfrastructure;

	private static final Logger logger = LoggerFactory.getLogger(OSDeploymentServiceImpl.class.getName());


	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.service.OSDeploymentService#getVersion()
	 */
	@Override
	public Version getVersion() {
		logger.trace("entered OSDeploymentServiceImpl, getVersion()");
		Version version = osDeploymentInfrastructure.getVersion();
	    logger.trace("exiting OSDeploymentServiceImpl, getVersion()");
	    return version;
	}

	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.service.OSDeploymentService#deployOS(com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload)
	 */
	@Override
	public JobStatus deployOS(OSDeploymentPayload payload) {
		logger.trace ("entered OSDeploymentServiceImpl, deployOS(...)");
		JobStatus jobStatus = osDeploymentInfrastructure.deployOS(payload);
	    logger.trace("exiting OSDeploymentServiceImpl, deployOS(...)");
	    return jobStatus;
	}
	
	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.service.OSDeploymentService#detachVirtualMedia(com.dell.isg.smi.adapter.server.model.WsmanCredentials)
	 */
	@Override
	public JobStatus detachVirtualMedia(WsmanCredentials wsmanCredentials){
        logger.trace("entered OSDeploymentServiceImpl, deployOS(...)");
        JobStatus jobStatus = osDeploymentInfrastructure.detachVirtualMedia(wsmanCredentials);
        logger.trace("exiting OSDeploymentServiceImpl, deployOS(...)");
        return jobStatus;
	}
}
