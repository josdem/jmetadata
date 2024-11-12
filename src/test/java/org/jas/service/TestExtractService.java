/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestExtractService {

    @InjectMocks
    private ExtractService extractService = new ExtractServiceImpl();

    @Mock
    private File file;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldExtractMetadataFromFileWhenDash() throws Exception {
        String filename = "jenifer lopez - 9A - 112.mp3";
        when(file.getName()).thenReturn(filename);

        Metadata result = extractService.extractFromFileName(file);

        assertEquals("jenifer lopez ", result.getArtist());
        assertEquals(" 9A ", result.getTitle());
    }

    @Test
    public void shouldExtractMetadataFromFileWhenNoDash() throws Exception {
        String expectedName = "jenifer lopez";
        String filename = "jenifer lopez.mp3";
        when(file.getName()).thenReturn(filename);

        Metadata result = extractService.extractFromFileName(file);

        assertEquals(expectedName, result.getArtist());
        assertEquals(expectedName, result.getTitle());
    }

}
