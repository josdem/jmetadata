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

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.helper.LastFMAuthenticator;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.model.User;
import de.umass.lastfm.Session;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class LoginControllerTest {

  private LoginController controller;

  @Mock private LastFMAuthenticator lastfmAuthenticator;
  @Mock private ControlEngineConfigurator configurator;
  @Mock private ControlEngine controlEngine;
  @Mock private Session session;

  private final String username = "josdem";
  private final String password = "password";

  private User credentials;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    when(configurator.getControlEngine()).thenReturn(controlEngine);

    credentials = new User(username, password);
    controller = new LoginController(lastfmAuthenticator, configurator);
  }

  @Test
  @DisplayName("login as a user")
  void shouldLogin(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(lastfmAuthenticator.login(username, password)).thenReturn(session);

    controller.login(credentials);

    verify(lastfmAuthenticator).login(username, password);
    verify(controlEngine).set(Model.CURRENT_USER, credentials, null);
    verify(controlEngine).fireEvent(eq(Events.LOGGED), isA(ValueEvent.class));
  }

  @Test
  @DisplayName("login as a user failed")
  void shouldFailAtLogin(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    controller.login(credentials);

    verify(lastfmAuthenticator).login(username, password);
    verify(controlEngine, never()).set(Model.CURRENT_USER, credentials, null);
    verify(controlEngine).fireEvent(Events.LOGIN_FAILED);
  }

  @Test
  @DisplayName("login as a user failed due to exception")
  public void shouldFailDueToException(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(lastfmAuthenticator.login(username, password)).thenThrow(new IOException());

    controller.login(credentials);

    verify(controlEngine).fireEvent(Events.LOGIN_FAILED);
  }
}
