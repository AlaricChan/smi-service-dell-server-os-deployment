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

public class NetworkSettings {
	private boolean dhcpForIp;
	private String ipAddress;
	private String subNetmask;
	private String defaultGateway;
	private String hostName;
	private String preferredDNSServer;
	private String alternateDNSServer;
	private int vLanId;


	public boolean isDhcpForIp() {
		return dhcpForIp;
	}
	public void setDhcpForIp(boolean dhcpForIp) {
		this.dhcpForIp = dhcpForIp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSubNetmask() {
		return subNetmask;
	}

	public void setSubNetmask(String subNetmask) {
		this.subNetmask = subNetmask;
	}

	public String getDefaultGateway() {
		return defaultGateway;
	}

	public void setDefaultGateway(String defaultGateway) {
		this.defaultGateway = defaultGateway;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPreferredDNSServer() {
		return preferredDNSServer;
	}

	public void setPreferredDNSServer(String preferredDNSServer) {
		this.preferredDNSServer = preferredDNSServer;
	}

	public String getAlternateDNSServer() {
		return alternateDNSServer;
	}

	public void setAlternateDNSServer(String alternateDNSServer) {
		this.alternateDNSServer = alternateDNSServer;
	}

	public int getvLanId() {
		return vLanId;
	}

	public void setvLanId(int vLanId) {
		this.vLanId = vLanId;
	}
}
