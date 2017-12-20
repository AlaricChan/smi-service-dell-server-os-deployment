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
package com.dell.isg.smi.osdeployment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BootableIsoRequest", description = "Creates a scripted bootable ISO, used for installation on target compute node as part of the deployment process")
public class BootableIsoRequest {

	@ApiModelProperty(value = "Kickstart file name", dataType = "string", required = true)
	protected String kickStartFileName;

	@ApiModelProperty(value = "Kickstart file location", dataType = "string", required = true)
	protected String ksLocation;
	
	@ApiModelProperty(value = "ISO file share location", dataType = "string", required = true)
	protected String shareAddress;
	
	@ApiModelProperty(value = "ISO file source directory", dataType = "string", required = true)
	protected String sourceDir;
	
	@ApiModelProperty(value = "ISO file name", dataType = "string", required = true)
	protected String fileName;
	
	@ApiModelProperty(value = "Destination directory for created ISO file", dataType = "string", required = true)
	protected String destinationDir;
	
	@ApiModelProperty(value = "File name for created ISO file", dataType = "string", required = true)
	protected String destinationFileName;


	public String getShareAddress() {
		return shareAddress;
	}
	public void setShareAddress(String shareAddress) {
		this.shareAddress = shareAddress;
	}
	public String getSourceDir() {
		return sourceDir;
	}
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDestinationDir() {
		return destinationDir;
	}
	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}
	public String getDestinationFileName() {
		return destinationFileName;
	}
	public void setDestinationFileName(String destinationFileName) {
		this.destinationFileName = destinationFileName;
	}
	public String getKickStartFileName() {
		return kickStartFileName;
	}
	public void setKickStartFileName(String kickStartFileName) {
		this.kickStartFileName = kickStartFileName;
	}
	public String getKsLocation() {
		return ksLocation;
	}
	public void setKsLocation(String ksLocation) {
		this.ksLocation = ksLocation;
	}

}
