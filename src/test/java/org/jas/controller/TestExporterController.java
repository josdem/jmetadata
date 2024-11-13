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

package org.jas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.jas.action.ActionResult;
import org.jas.controller.ExporterController;
import org.jas.helper.ExporterHelper;
import org.jas.model.ExportPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestExporterController {

	@InjectMocks
	private ExporterController exporterController = new ExporterController();

	@Mock
	private ExportPackage exportPackage;
	@Mock
	private ExporterHelper exporterHelper;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSendMetadata() throws Exception {
		when(exporterHelper.export(exportPackage)).thenReturn(ActionResult.Exported);

		ActionResult result = exporterController.sendMetadata(exportPackage);

		verify(exporterHelper).export(exportPackage);
		assertEquals(ActionResult.Exported, result);
	}

	@Test
	public void shouldReportErrorInSendingMetadata() throws Exception {
		when(exporterHelper.export(exportPackage)).thenThrow(new IOException());

		ActionResult result = exporterController.sendMetadata(exportPackage);

		assertEquals(ActionResult.Error, result);
	}

}
