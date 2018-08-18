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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.jas.action.ActionResult;
import org.jas.controller.ScrobblerController;
import org.jas.helper.ScrobblerHelper;
import org.jas.model.Metadata;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestScrobblerController {
	@InjectMocks
	private ScrobblerController controller = new ScrobblerController();

	@Mock

	private Metadata metadata;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
	}

	@Test
	public void shouldSendMetadata() throws Exception {
		when(scrobblerHelper.send(metadata)).thenReturn(ActionResult.New);

		ActionResult result = controller.sendMetadata(metadata);

		verify(scrobblerHelper).send(metadata);
		assertEquals(ActionResult.New, result);
	}

	@Test
	public void shouldDetectWhenErrorInScrobbling() throws Exception {
		when(scrobblerHelper.send(metadata)).thenReturn(ActionResult.Error);

		ActionResult result = controller.sendMetadata(metadata);

		verify(scrobblerHelper).send(metadata);
		assertEquals(ActionResult.Error, result);
	}

	@Test
	public void shouldSetup() throws Exception {
		controller.setup();
		verify(scrobblerHelper).setControlEngine(controlEngine);
	}

	@Test
	public void shouldCatchIOException() throws Exception {
		when(scrobblerHelper.send(metadata)).thenThrow(new IOException());

		ActionResult result = controller.sendMetadata(metadata);

		assertEquals(ActionResult.Error, result);
	}

	@Test
	public void shouldCatchInterruptedException() throws Exception {
		when(scrobblerHelper.send(metadata)).thenThrow(new InterruptedException());

		ActionResult result = controller.sendMetadata(metadata);

		assertEquals(ActionResult.Error, result);
	}
}
