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
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

private const val USERNAME = "josdem"

internal class LastFMAuthenticatorTest {
    private lateinit var lastFMAuthenticator: LastFMAuthenticator

    @Mock private lateinit var authenticatorHelper: AuthenticatorHelper

    @Mock private lateinit var session: Session

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        lastFMAuthenticator = LastFMAuthenticator(authenticatorHelper)
    }

    @Test
    fun `should login`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val username = USERNAME
        val password = "password"
        `when`(authenticatorHelper.getSession(username, password)).thenReturn(session)
        assertNotNull(lastFMAuthenticator.login(username, password)) { "should login" }
    }

    @Test
    fun `should fail to login due to not username nor password`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val username = StringUtils.EMPTY
        val password = StringUtils.EMPTY
        assertNull(lastFMAuthenticator.login(username, password)) { "should fail to login due to not username nor password" }
    }

    @Test
    fun `should fail to login`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val username = USERNAME
        val password = "invalidPassword"
        assertNull(lastFMAuthenticator.login(username, password)) { "should fail to login due to invalid credentials" }
    }
}
