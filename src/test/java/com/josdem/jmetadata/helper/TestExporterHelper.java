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

package com.josdem.jmetadata.helper;

import com.josdem.jmetadata.model.ExportPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TestExporterHelper {
    @InjectMocks
    private ExporterHelper exporterHelper = new ExporterHelper();

    @Mock
    private ImageExporter imageExporter;
    @Mock
    private MetadataExporter metadataExporter;
    @Mock
    private ExportPackage exportPackage;


    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldExportImage() throws Exception {
        exporterHelper.export(exportPackage);
        verify(imageExporter).export(exportPackage);
        verify(metadataExporter).export(exportPackage);
    }

}
