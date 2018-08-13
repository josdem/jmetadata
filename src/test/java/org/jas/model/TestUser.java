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

package org.jas.model;

import static org.junit.Assert.assertEquals;

import org.jas.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;


public class TestUser {
	private User user;
	private String username = "josdem";
	private String password = "password";
	@Mock
	private Session session;

	@Before
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
