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
package com.dell.isg.smi.osdeployment.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dell.isg.smi.adapter.server.model.WsmanCredentials;
import com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload;
import com.dell.isg.smi.commons.model.server.JobStatus;
import com.dell.isg.smi.osdeployment.BuildInfo;
import com.dell.isg.smi.osdeployment.common.UriConstants;
import com.dell.isg.smi.osdeployment.common.model.Version;
import com.dell.isg.smi.osdeployment.model.BootableIsoRequest;
import com.dell.isg.smi.osdeployment.model.ServiceResponse;

import io.swagger.annotations.ApiOperation;

/**
 * The Interface IosDeploymentController.
 */
public interface IosDeploymentController {


	/**
	 * Version.
	 *
	 * @return the version
	 */
	@ApiOperation(value = UriConstants.VERSION, nickname = "OSDeployment-version", notes = "This REST endpoint returns the deployed"
			+ " Microservice version, used for hypervisor installaion on compute node", response = BuildInfo.class)
	@RequestMapping(value = UriConstants.VERSION, method = RequestMethod.GET, produces = "application/json")
	public BuildInfo version();

	
	/**
	 * Deploy OS.
	 *
	 * @param payload the payload
	 * @param bindingResult the binding result
	 * @return the response entity
	 */
	@ApiOperation(value = UriConstants.DEPLOY, nickname = "OSDeployment-Deploy", notes = "This REST endpoint returns job status for"
			+ " hypervisor deployment process, kickedoff on compute node for OS installation", response = JobStatus.class)
	@RequestMapping(value = UriConstants.DEPLOY, method = RequestMethod.POST)
	public ResponseEntity<ServiceResponse> deployOS(@RequestBody @Valid OSDeploymentPayload payload, BindingResult bindingResult);

	
	/**
	 * Creates the bootable iso.
	 *
	 * @param payload the payload
	 * @param bindingResult the binding result
	 * @return the response entity
	 */
	@ApiOperation(value = UriConstants.CREATE_ISO, nickname = "OSDeployment-Minimal-ISO", notes = "This REST endpoint returns name for newly created customized ISO, "
			+ "this method creates a scripted bootable ISO, used for installation on target compute node as part of the deployment process ", response = String.class)
	@RequestMapping(value = UriConstants.CREATE_ISO, method = RequestMethod.POST)
	public ResponseEntity<ServiceResponse> createBootableIso(@RequestBody @Valid BootableIsoRequest payload, BindingResult bindingResult);
	
	
	/**
	 * Detach virtual media.
	 *
	 * @param wsmanCredentials the wsman credentials
	 * @return the response entity
	 */
	@ApiOperation(value = UriConstants.VERSION, nickname = "OSDeployment-Deatch-ISO", notes = "This REST endpoint detaches attached virtual media", response = JobStatus.class)
	@RequestMapping(value = UriConstants.DETACH_ISO, method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse> detachVirtualMedia(@RequestBody @Valid WsmanCredentials wsmanCredentials);
}
