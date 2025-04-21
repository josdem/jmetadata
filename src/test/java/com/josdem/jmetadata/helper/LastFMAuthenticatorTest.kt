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

package com.josdem.jmetadata.helper

import de.umass.lastfm.Session
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

internal class LastFMAuthenticatorTest {
    private lateinit var lastFMAuthenticator: LastFMAuthenticator

    @Mock private lateinit var authenticatorHelper: AuthenticatorHelper

    @Mock private lateinit var session: Session

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        lastFMAuthenticator = LastFMAuthenticator(authenticatorHelper)
    }

    @Test
    fun `should login`() {
        val username = "josdem"
        val password = "password"
        `when`(authenticatorHelper.getSession(username, password)).thenReturn(session)
        assertNotNull(lastFMAuthenticator.login(username, password)) { "should login" }
    }
}
