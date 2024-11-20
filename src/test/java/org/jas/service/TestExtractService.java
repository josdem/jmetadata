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

package org.jas.service;

import org.jas.model.Metadata;
import org.jas.service.impl.ExtractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TestExtractService {

    public static final String EXPECTED_ARTIST = "Jennifer Lopez";

    @InjectMocks
    private ExtractService extractService = new ExtractServiceImpl();

    @Mock
    private File file;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should extract metadata from file when dash")
    public void shouldExtractMetadataFromFileWhenDash(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        String filename = "Jennifer Lopez - 9A - 112.mp3";
        when(file.getName()).thenReturn(filename);

        Metadata result = extractService.extractFromFileName(file);

        assertEquals(EXPECTED_ARTIST, result.getArtist());
        assertEquals("9A", result.getTitle());
    }

    @Test
    @DisplayName("Should extract metadata from file when no dash")
    public void shouldExtractMetadataFromFileWhenNoDash(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        String expectedName = EXPECTED_ARTIST;
        String filename = "Jennifer Lopez.mp3";
        when(file.getName()).thenReturn(filename);

        Metadata result = extractService.extractFromFileName(file);

        assertEquals(expectedName, result.getArtist());
        assertEquals(expectedName, result.getTitle());
    }

}
