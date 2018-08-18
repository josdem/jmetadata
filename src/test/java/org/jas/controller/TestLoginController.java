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

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.controller.LoginController;
import org.jas.event.Events;
import org.jas.helper.LastFMAuthenticator;
import org.jas.model.Model;
import org.jas.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;

public class TestLoginController {
	@InjectMocks
	private LoginController controller = new LoginController();

	@Mock
	private LastFMAuthenticator lastfmAuthenticator;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private Session session;

	private String username = "josdem";
	private String password = "password";
	private User credentials;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);

		credentials = new User(username, password);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldLogin() throws Exception {
		when(lastfmAuthenticator.login(username, password)).thenReturn(session);

		controller.login(credentials);

		verify(lastfmAuthenticator).login(username, password);
		verify(controlEngine).set(Model.CURRENT_USER, credentials, null);
		verify(controlEngine).fireEvent(eq(Events.LOGGED), isA(ValueEvent.class));
	}

	@Test
	public void shouldfailAtLogin() throws Exception {
		controller.login(credentials);

		verify(lastfmAuthenticator).login(username, password);
		verify(controlEngine, never()).set(Model.CURRENT_USER, credentials, null);
		verify(controlEngine).fireEvent(Events.LOGIN_FAILED);
	}

	@Test
	public void shouldKnowAIOException() throws Exception {
		when(lastfmAuthenticator.login(username, password)).thenThrow(new IOException());

		controller.login(credentials);

		verify(controlEngine, never()).set(Model.CURRENT_USER, credentials, null);
		verify(controlEngine).fireEvent(Events.LOGIN_FAILED);
	}

}
