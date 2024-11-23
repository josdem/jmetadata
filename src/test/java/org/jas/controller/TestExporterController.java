/*
   Copyright 2024 Jose Morales contact@josdem.io

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

package org.jas.controller;

import org.jas.action.ActionResult;
import org.jas.helper.ExporterHelper;
import org.jas.model.ExportPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestExporterController {

    @InjectMocks
    private final ExporterController exporterController = new ExporterController();

    @Mock
    private ExportPackage exportPackage;
    @Mock
    private ExporterHelper exporterHelper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("sending metadata")
    public void shouldSendMetadata(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        when(exporterHelper.export(exportPackage)).thenReturn(ActionResult.Exported);

        ActionResult result = exporterController.sendMetadata(exportPackage);

        verify(exporterHelper).export(exportPackage);
        assertEquals(ActionResult.Exported, result);
    }

    @Test
    @DisplayName("reporting error in sending metadata")
    public void shouldReportErrorInSendingMetadata(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        when(exporterHelper.export(exportPackage)).thenThrow(new IOException());

        ActionResult result = exporterController.sendMetadata(exportPackage);

        assertEquals(ActionResult.Error, result);
    }

}
