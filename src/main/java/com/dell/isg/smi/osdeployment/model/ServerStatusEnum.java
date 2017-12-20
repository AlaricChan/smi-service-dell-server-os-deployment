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

public enum ServerStatusEnum {
	UNCONFIGURED("Unconfigured","UNCONFIGURED", 0),
	CONFIGURED("Configured", "CONFIGURED", 1),
	SCHEDULED("Scheduled to Deploy", "SCHEDULED", 2),
	IN_PROGRESS("Deploy In-progress","IN_PROGRESS", 3),
	DEPLOYED("Deployed", "DEPLOYED",4),
	QUARANTINED("Quarantined","QUARANTINED",5);

	/**
	 * The primary key of the record in Task_Type table in the database corresponding to this enum constant.
	 * If the id is changed in database, it must be changed here too.
	 */
	private Integer id;

	/**
	 * Display friendly string for this enum constant.
	 */
	private String label;

	/**
	 * The value for this constant (regardless of display value or enum constant name.
	 */
	private String value;

	private ServerStatusEnum( String label, String value, Integer id )  {
		this.label =label;
		this.value = value;
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	public Integer getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.label;
	}

}
