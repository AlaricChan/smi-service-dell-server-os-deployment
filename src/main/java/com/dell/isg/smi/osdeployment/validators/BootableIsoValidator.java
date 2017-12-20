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
package com.dell.isg.smi.osdeployment.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dell.isg.smi.osdeployment.common.OSDeployUtils;
import com.dell.isg.smi.osdeployment.model.BootableIsoRequest;

/**
 * The Class BootableIsoValidator.
 */
@Component
public class BootableIsoValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(BootableIsoValidator.class.getName());

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return BootableIsoRequest.class.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        logger.info("Validating bootable iso creation request");
        BootableIsoRequest obj = (BootableIsoRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "kickStartFileName", "NotEmpty.kickStartFileName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ksLocation", "NotEmpty.ksLocation");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shareAddress", "NotEmpty.shareAddress");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sourceDir", "NotEmpty.sourceDir");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fileName", "NotEmpty.fileName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationDir", "NotEmpty.destinationDir");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationFileName", "NotEmpty.destinationFileName");
        
        String shareAddress = obj.getShareAddress();

        if (!OSDeployUtils.validateIPAddress(shareAddress)) {
            errors.rejectValue("shareAddress", "NotReachable.shareAddress");
        }
    }
}
