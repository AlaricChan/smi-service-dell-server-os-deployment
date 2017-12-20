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
package com.dell.isg.smi.osdeployment.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dell.isg.smi.adapter.server.model.WsmanCredentials;
import com.dell.isg.smi.adapter.server.osdeployment.OSDeploymentAdapter;
import com.dell.isg.smi.commons.model.fileshare.FileShare;
import com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload;
import com.dell.isg.smi.commons.model.server.JobStatus;
import com.dell.isg.smi.commons.model.server.JobStatusEnum;
import com.dell.isg.smi.osdeployment.model.Version;


/**
 * The Class OSDeploymentInfrastructureImpl.
 */
@Component
public class OSDeploymentInfrastructureImpl implements OSDeploymentInfrastructure {

	@Autowired
	private OSDeploymentAdapter osDeploymentAdapter=null;
	private static final Logger logger = LoggerFactory.getLogger(OSDeploymentInfrastructureImpl.class.getName());

	@Override
	public Version getVersion() {
		return new Version();
	}


	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.infrastructure.OSDeploymentInfrastructure#deployOS(com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload)
	 */
	@Override
	public JobStatus deployOS(OSDeploymentPayload request) {
		logger.trace("entered deployOS");
		WsmanCredentials credentials=new WsmanCredentials(request.getServerAddress(),request.getUserName(),request.getPassword());
		FileShare fileShare=request.getIsoFileShare();
		
		JobStatus jobStatus = osDeploymentAdapter.connectToNetworkISO( credentials, fileShare.getAddress(), fileShare.getPath(), fileShare.getFileName() );
		if (jobStatus.getJobId()!=null){
		    logger.info("Starting Operating System deployment on server {}", request.getServerAddress());
		    JobStatus rebootStatus = osDeploymentAdapter.rebootServer( credentials.getAddress(), credentials.getUserName(), credentials.getPassword() );
    		if ( JobStatusEnum.LCJobStatus.FAILED.toString().equalsIgnoreCase( rebootStatus.getStatus() ) ){
    		    logger.trace( "exiting deployOS with rebootStatus {}", rebootStatus.getStatus() );
    			return rebootStatus;
    		}
		}
		
		logger.trace("exiting deployOS");
		return jobStatus;
	}
	
	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.infrastructure.OSDeploymentInfrastructure#detachVirtualMedia(com.dell.isg.smi.adapter.server.model.WsmanCredentials)
	 */
	public JobStatus detachVirtualMedia(WsmanCredentials wsmanCredentials){
	    logger.trace("entered detachVirtualMedia" );
	    JobStatus jobStatus = osDeploymentAdapter.detachISO(wsmanCredentials);
	    logger.trace("exiting detachVirtualMedia" );
	    return jobStatus;
	}

}