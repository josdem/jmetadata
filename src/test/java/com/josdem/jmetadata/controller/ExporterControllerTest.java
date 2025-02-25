/*
   Copyright 2025 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.helper.ExporterHelper;
import com.josdem.jmetadata.model.ExportPackage;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class ExporterControllerTest {

  private ExporterController exporterController;

  @Mock private ExportPackage exportPackage;
  @Mock private ExporterHelper exporterHelper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    exporterController = new ExporterController(exporterHelper);
  }

  @Test
  @DisplayName("sending metadata")
  void shouldSendMetadata(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(exporterHelper.export(exportPackage)).thenReturn(ActionResult.EXPORTED);

    var result = exporterController.sendMetadata(exportPackage);

    verify(exporterHelper).export(exportPackage);
    assertEquals(ActionResult.EXPORTED, result);
  }

  @Test
  @DisplayName("reporting error in sending metadata")
  void shouldReportErrorInSendingMetadata(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(exporterHelper.export(exportPackage)).thenThrow(new IOException());

    var result = exporterController.sendMetadata(exportPackage);

    assertEquals(ActionResult.ERROR, result);
  }
}
