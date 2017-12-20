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
package com.dell.isg.smi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dell.isg.smi.osdeployment.Application;

import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class Swagger2MarkupTest {
	
    private static final String API_URI = "http://localhost:46014/v2/api-docs?group=os-deployment";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    //@Before
    public void setup() throws IOException {
       this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

    }
    
    @Ignore
    @Test
    public void convertSwaggerToAsciiDoc() throws Exception {
        String outputDir = System.getProperty("staticdocs.outputDir")+File.separatorChar+"V1.0";
        Swagger2MarkupResultHandler.Builder builder = Swagger2MarkupResultHandler.outputDirectory(outputDir);
        mockMvc.perform(get(API_URI).accept(MediaType.APPLICATION_JSON)).andDo(builder.build()).andExpect(status().isOk());
    }
}
