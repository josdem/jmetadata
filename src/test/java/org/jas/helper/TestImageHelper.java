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

package org.jas.helper;


import org.jas.ApplicationState;
import org.jas.service.ImageService;
import org.jas.service.impl.ImageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestImageHelper {

    private final ImageService imageService = new ImageServiceImpl();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("creating a temp file for cover art")
    public void shouldCreateTempFile(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        File tempFile = imageService.createTempFile();
        assertTrue(tempFile.getName().contains(ApplicationState.PREFIX));
        assertTrue(tempFile.getName().contains(ApplicationState.IMAGE_EXT));
    }
}
