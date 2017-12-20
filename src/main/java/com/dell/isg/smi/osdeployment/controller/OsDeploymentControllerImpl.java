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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dell.isg.smi.adapter.server.model.WsmanCredentials;
import com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload;
import com.dell.isg.smi.commons.model.server.JobStatus;
import com.dell.isg.smi.commons.model.server.JobStatusEnum;
import com.dell.isg.smi.osdeployment.BuildInfo;
import com.dell.isg.smi.osdeployment.model.BootableIsoRequest;
import com.dell.isg.smi.osdeployment.model.MessageKey;
import com.dell.isg.smi.osdeployment.model.ServiceResponse;
import com.dell.isg.smi.osdeployment.service.MinimalIsoService;
import com.dell.isg.smi.osdeployment.service.OSDeploymentService;
import com.dell.isg.smi.osdeployment.validators.BootableIsoValidator;
import com.dell.isg.smi.osdeployment.validators.DeployOsValidator;

/**
 * The Class OsDeploymentControllerImpl.
 */
@RestController
public class OsDeploymentControllerImpl implements IosDeploymentController {

	private static final Logger logger = LoggerFactory.getLogger(OsDeploymentControllerImpl.class.getName());

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@Autowired
	private OSDeploymentService osDeploymentService;

	@Autowired
	private MinimalIsoService minimalIsoService;

	@Autowired
	private DeployOsValidator deployOsValidator;

	@Autowired
	private BootableIsoValidator bootableIsoValidator;
	
    @Autowired
    private BuildInfo buildInfo;

	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.controller.IosDeploymentController#version()
	 */
	@Override
	public BuildInfo version() {
		logger.trace("Controller invoked for deployed microservice version");
		return buildInfo;
	}

	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.controller.IosDeploymentController#deployOS(com.dell.isg.smi.commons.model.osdeployment.OSDeploymentPayload, org.springframework.validation.BindingResult)
	 */
	@Override
	public ResponseEntity<ServiceResponse> deployOS(@RequestBody @Valid OSDeploymentPayload request, BindingResult bindingResult) {
		logger.trace("Controller invoked for hypervisor installation on server");
		try {
			deployOsValidator.validate(request, bindingResult);
			if (null == request || bindingResult.hasErrors()) {
				logger.error("Invalid Request or validation failure");
				ResponseEntity<ServiceResponse> invalidRequestResponse = getInvalidRequestResponse(bindingResult,
						MessageKey.INVALID_REQUEST);
				return invalidRequestResponse;
			}

			JobStatus jobStatus = null;
			logger.info("Controller invoked for hypervisor installation on server {}", request.getServerAddress());

			jobStatus = osDeploymentService.deployOS(request);

			String requestMsg = messageSource.getMessage(MessageKey.REQUEST_SUCCESS.getKey(), null, Locale.getDefault());
			ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.OK, requestMsg, jobStatus);
			return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
		} catch (Exception exp) {
			String error = exp.getMessage();
			String failureMsg = messageSource.getMessage(MessageKey.REQUEST_PROCESS_FAILED.getKey(), null,
					Locale.getDefault());
			ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, failureMsg, error);
			return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
		}
	}

	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.controller.IosDeploymentController#createBootableIso(com.dell.isg.smi.osdeployment.model.BootableIsoRequest, org.springframework.validation.BindingResult)
	 */
	@Override
	public ResponseEntity<ServiceResponse> createBootableIso(@RequestBody @Valid BootableIsoRequest request,
			BindingResult bindingResult) {
		logger.trace("Controller invoked for creating Bootable ISO externalized kickstart file");
		try {
			bootableIsoValidator.validate(request, bindingResult);
			if (null == request || bindingResult.hasErrors()) {
				logger.error("Invalid Request or validation failure");
				ResponseEntity<ServiceResponse> invalidRequestResponse = getInvalidRequestResponse(bindingResult,
						MessageKey.INVALID_REQUEST);
				return invalidRequestResponse;
			}

			String fileName = "";
			logger.info("Process for creating customized ISO with externalized kickstart file has started");

			fileName = minimalIsoService.createBootableIso(request);
			logger.info("Customized ISO for OS installation has been created with file name {} ", fileName);

			String requestMsg = messageSource.getMessage(MessageKey.REQUEST_SUCCESS.getKey(), null,Locale.getDefault());
			ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.OK, requestMsg, fileName);
			return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
		} catch (Exception e) {
			String error = e.getMessage();
			String failureMsg = messageSource.getMessage(MessageKey.REQUEST_PROCESS_FAILED.getKey(), null, Locale.getDefault());
			ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, failureMsg, error);
			return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
		}
	}

	
	/* (non-Javadoc)
	 * @see com.dell.isg.smi.osdeployment.controller.IosDeploymentController#detachVirtualMedia(com.dell.isg.smi.adapter.server.model.WsmanCredentials)
	 */
	@Override
    public ResponseEntity<ServiceResponse> detachVirtualMedia(@RequestBody @Valid WsmanCredentials wsmanCredentials){
	    JobStatus jobStatus = null;
	    try{
    	    jobStatus = osDeploymentService.detachVirtualMedia(wsmanCredentials);
    	    if(jobStatus.getStatus().equals(JobStatusEnum.LCJobStatus.COMPLETED.toString())){
    	        String requestMsg = messageSource.getMessage(MessageKey.REQUEST_SUCCESS.getKey(), null, Locale.getDefault());
    	        ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.OK, requestMsg, jobStatus);
    	        return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
    	    }
    	    else{
                String requestMsg = messageSource.getMessage(MessageKey.REQUEST_PROCESS_FAILED.getKey(), null, Locale.getDefault());
                ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.EXPECTATION_FAILED, requestMsg, jobStatus);
                return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
    	    }
	    }
	    catch(Exception e){
            String error = e.getMessage();
            String failureMsg = messageSource.getMessage(MessageKey.REQUEST_PROCESS_FAILED.getKey(), null, Locale.getDefault());
            ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, failureMsg, error);
            return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
	    }
	}
	
	/*
	 * Utility method for input validation
	 */
	@SuppressWarnings("unused")
	private boolean isArgValid(String value) {
		boolean isValid = true;
		if (value == null || value.isEmpty()) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Gets the invalid request response.
	 *
	 * @param bindingResult the binding result
	 * @param messageKey  the MessageKey
	 * @return the invalid request response
	 */
	private ResponseEntity<ServiceResponse> getInvalidRequestResponse(BindingResult bindingResult, MessageKey messageKey) {
	    List<ObjectError> allObjErrors = bindingResult.getAllErrors();
		List<String> errors = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(allObjErrors)) {
			for (ObjectError objError : allObjErrors) {
				if (objError instanceof FieldError) {
					String errorMsg = messageSource.getMessage(objError, null);
					logger.error("Error message for validation failure: " + errorMsg);
					errors.add(errorMsg);
				}
			}
		}
		String invalidReqMsg = messageSource.getMessage(messageKey.getKey(), null, Locale.getDefault());
		ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.BAD_REQUEST, invalidReqMsg, errors);
		return new ResponseEntity<ServiceResponse>(serviceResponse, new HttpHeaders(), serviceResponse.getStatus());
	}
	
}
