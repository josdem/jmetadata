/*
   Copyright 2014 Jose Morales contact@josdem.io

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.josdem.jmetadata.helper;


import com.josdem.jmetadata.ApplicationState;
import com.josdem.jmetadata.service.ImageService;
import com.josdem.jmetadata.service.impl.ImageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;


import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import lombok.extern.slf4j.Slf4j;

@Slf4j


class TestImageHelper {

    private final ImageService imageService = new ImageServiceImpl();


    @Test
    @DisplayName("creating a temp file for cover art")
    public void shouldCreateTempFile(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        File tempFile = imageService.createTempFile();
        assertTrue(tempFile.getName().contains(ApplicationState.PREFIX));
        assertTrue(tempFile.getName().contains(ApplicationState.IMAGE_EXT));
    }
}