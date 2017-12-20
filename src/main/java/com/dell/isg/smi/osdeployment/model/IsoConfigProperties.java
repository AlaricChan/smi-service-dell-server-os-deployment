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

import com.dell.isg.smi.commons.model.fileshare.FileShare;

public class IsoConfigProperties {

	private NetworkSettings networkSettings;
	private String uuid;
	private String nicForMgmtTasks;
	private boolean installOMSA;
	private InstallationTypeEnum mediaType;
	private FileShare sourceIsoLocation;
	private String sourceIsoFileName;
	private String destinationIsoNfsServerIp;
	private String destinationIsoNfsFolder;
	private String callbackUri;
	private DeploymentISOType isoType;

	public FileShare getSourceIsoLocation() {
		return sourceIsoLocation;
	}

	public void setSourceIsoLocation(FileShare sourceIsoLocation) {
		this.sourceIsoLocation = sourceIsoLocation;
	}

	public String getSourceIsoFileName() {
		return sourceIsoFileName;
	}

	public void setSourceIsoFileName(String sourceIsoFileName) {
		this.sourceIsoFileName = sourceIsoFileName;
	}

	public String getDestinationIsoNfsServerIp() {
		return destinationIsoNfsServerIp;
	}

	public void setDestinationIsoNfsServerIp(String destinationIsoNfsServerIp) {
		this.destinationIsoNfsServerIp = destinationIsoNfsServerIp;
	}

	public String getDestinationIsoNfsFolder() {
		return destinationIsoNfsFolder;
	}

	public void setDestinationIsoNfsFolder(String destinationIsoNfsFolder) {
		this.destinationIsoNfsFolder = destinationIsoNfsFolder;
	}

	public NetworkSettings getNetworkSettings() {
		return networkSettings;
	}

	public void setNetworkSettings(NetworkSettings networkSettings) {
		this.networkSettings = networkSettings;
	}

	/**
	 * @return the mediaType
	 */
	public InstallationTypeEnum getMediaType() {
		return mediaType;
	}

	/**
	 * @param mediaType the storageType to set
	 */
	public void setMediaType(InstallationTypeEnum mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return the installOMSA
	 */
	public boolean isInstallOMSA() {
		return installOMSA;
	}

	/**
	 * @param installOMSA the installOMSA to set
	 */
	public void setInstallOMSA(boolean installOMSA) {
		this.installOMSA = installOMSA;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public String getNicForMgmtTasks() {
		return nicForMgmtTasks;
	}

	public void setNicForMgmtTasks(String nicForMgmtTasks) {
		this.nicForMgmtTasks = nicForMgmtTasks;
	}

	public String getCallbackUri() {
		return callbackUri;
	}

	public void setCallbackUri(String callbackUri) {
		this.callbackUri = callbackUri;
	}

	public DeploymentISOType getIsoType() {
		return isoType;
	}

	public void setIsoType(DeploymentISOType isoType) {
		this.isoType = isoType;
	}

}
