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

package org.jas.helper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.jas.helper.AuthenticatorHelper;
import org.jas.helper.LastFMAuthenticator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;

/**
* @author josdem (joseluis.delacruz@gmail.com)
*/

public class TestLastFMAuthenticator {
	@InjectMocks
	private LastFMAuthenticator controller = new LastFMAuthenticator();

	@Mock
	private AuthenticatorHelper authenticatorHelper;
	@Mock
	private Session session;

	int result;

	@Before
	public void initialize(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldLogin() throws Exception {
		String username = "josdem";
		String password = "validPassword";

		when(authenticatorHelper.getSession(username, password)).thenReturn(session);

		assertNotNull(controller.login(username, password));
	}

	@Test
	public void shouldFailAtLoginIfNoUsernameAndPassword() throws Exception {
		String username = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;

		assertNull(controller.login(username, password));
	}

	@Test
	public void shouldFailAtLoginIfNoUsername() throws Exception {
		String username = StringUtils.EMPTY;
		String password = "somePassword";

		assertNull(controller.login(username, password));
	}

	@Test
	public void shouldFailAtLoginIfNoPassword() throws Exception {
		String username = "someUsername";
		String password = StringUtils.EMPTY;

		assertNull(controller.login(username, password));
	}

	@Test
	public void shouldFailAtLogin() throws Exception {
		String username = "josdem";
		String password = "invalidPassword";

		assertNull(controller.login(username, password));
	}
}
