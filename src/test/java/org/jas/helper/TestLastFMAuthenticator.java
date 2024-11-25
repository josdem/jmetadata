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

package org.jas.helper;

import de.umass.lastfm.Session;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class TestLastFMAuthenticator {

    @InjectMocks
    private final LastFMAuthenticator lastFMAuthenticator = new LastFMAuthenticator();

    @Mock
    private AuthenticatorHelper authenticatorHelper;
    @Mock
    private Session session;

    int result;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldLogin() throws Exception {
        String username = "josdem";
        String password = "validPassword";

        when(authenticatorHelper.getSession(username, password)).thenReturn(session);

        assertNotNull(lastFMAuthenticator.login(username, password));
    }

    @Test
    public void shouldFailAtLoginIfNoUsernameAndPassword() throws Exception {
        String username = StringUtils.EMPTY;
        String password = StringUtils.EMPTY;

        assertNull(lastFMAuthenticator.login(username, password));
    }

    @Test
    public void shouldFailAtLoginIfNoUsername() throws Exception {
        String username = StringUtils.EMPTY;
        String password = "somePassword";

        assertNull(lastFMAuthenticator.login(username, password));
    }

    @Test
    public void shouldFailAtLoginIfNoPassword() throws Exception {
        String username = "someUsername";
        String password = StringUtils.EMPTY;

        assertNull(lastFMAuthenticator.login(username, password));
    }

    @Test
    public void shouldFailAtLogin() throws Exception {
        String username = "josdem";
        String password = "invalidPassword";

        assertNull(lastFMAuthenticator.login(username, password));
    }
}
