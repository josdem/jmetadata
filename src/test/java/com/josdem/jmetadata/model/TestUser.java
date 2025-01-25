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

package com.josdem.jmetadata.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.umass.lastfm.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TestUser {
  private User user;
  private final String username = "josdem";
  private final String password = "password";

  @Mock private Session session;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    user = new User(username, password);
  }

  @Test
  public void shouldGetUsernameAndPassword() throws Exception {
    assertEquals(username, user.getUsername());
    assertEquals(password, user.getPassword());
  }

  @Test
  public void shouldGetSession() throws Exception {
    user.setSession(session);
    assertEquals(session, user.getSession());
  }
}
