# smi-service-dell-server-os-deployment
A Java Spring Boot Microservice that exposes a REST API. Used to perform operating system deployment on Dell servers using the IDRAC's ability to mount and expose an ISO.

This service is intended to support the deployment of various Operating System types.  Currently only ESXI 6.x is supported.

Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved. 

### Purpose
The service-os-deployment container is a stateless spring-boot microservice that exposes a REST API's to:
- Read files from a source ISO and create a new, repackaged ISO that specifies the location of a Kickstart file to use
- deploy an ISO image stored on a network share to to a Dell server
- detach virtual media on a Dell server

---

### How to use

#### Startup

1) On the host system that runs docker, create a directory and mount a remote NFS or CIFS share. Example:
~~~
sudo mkdir /mnt/filesFromFs01
sudo mount 100.100.100.100:/opt/dell/public /mnt/filesFromFs01
~~~

2) Use docker run with the -v option to use the mount above inside of the container.  Example:
~~~
sudo docker run --name osdeployment -p 0.0.0.0:46014:46014 -v /mnt/filesFromFs01:/public -d rackhd/dell-os-deployment:latest
~~~

#### API Definitions

A swagger UI is provided by the microservice at:
~~~
http://<<ip>>:46014/swagger-ui.html
~~~ 

##### Example API Endpoints

###### Create ISO
Endpoint:  POST  /api/1.0/server/osdeployment/iso/create
~~~
{
	"kickStartFileName" :"myCustomKickstart.cfg",
	"ksLocation" : "nfs://100.100.100.100/opt/dell/public/",
	"sourceDir": "/public",
	"fileName": "VMware-VMvisor-Installer-6.0.0.update03-5572656.x86_64-DellEMC_Customized-A04.iso",
	"destinationDir": "/public",
	"shareAddress": "100.100.100.100",
	"destinationFileName": "myCustomIso.iso"
}

~~~

###### Deploy ISO
Endpoint: POST  /api/1.0/server/osdeployment/deploy
~~~
{
	"serverAddress": "100.68.124.121",
	"userName": "root", 
	"password": "calvin",
	"hypervisorType": "ESXi6",
	"hypervisorVersion": "6.5",
	"isoFileShare": 
	{
	    "address": "100.100.100.100",
	    "description": "nfs",
	    "fileName": "myCustomIso.iso",
	    "path": "/public",
	    "scriptDirectory": "NA",
	    "scriptName": "NA",
	    "type": "NFS",
	    "name": "Name-NFS
	}
}
~~~


###### Unmount ISO
Endpoint:  POST /api/1.0/server/osdeployment/iso/detach
~~~
{
	"address": "100.68.124.121",
	"userName": "root", 
	"password": "calvin"
}
~~~


### Licensing
Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

Source code for this microservice is available in repositories at https://github.com/RackHD.  

The microservice makes use of dependent Jar libraries that may be covered by other licenses. In order to comply with the requirements of applicable licenses, the source for dependent libraries used by this microservice is available for download at:  https://bintray.com/rackhd/binary/download_file?file_path=smi-service-dell-server-os-deployment-dependency-sources-devel.zip

Additionally the binary and source jars for all dependent libraries are available for download on Maven Central.

RackHD is a Trademark of Dell EMC

---
