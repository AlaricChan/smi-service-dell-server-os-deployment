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
package com.dell.isg.smi.osdeployment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "buildInfo")
@PropertySource({ 
	  "classpath:buildInfo.properties"
	})
@Component
public class BuildInfo {

	public BuildInfo() {
		super();
	}
	
	private String apiVersion = "1.0";
	
	private String version;
	
	private String tag;
	
	private String date;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	@Override
	public String toString() {
		StringBuffer versionStr = new StringBuffer();
		versionStr.append("API Version :" +apiVersion+"\r\n");
		versionStr.append("Release Version :" +version+"\r\n");
		versionStr.append("Release Tag :" +tag+"\r\n");
		versionStr.append("Release Date :" +date+"\r\n");
		
		return versionStr.toString();
	}
	
}
	


