/*
   Copyright 2026 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.controller

import com.josdem.jmetadata.event.Events
import com.josdem.jmetadata.helper.LastFMAuthenticator
import com.josdem.jmetadata.model.Model
import com.josdem.jmetadata.model.User
import de.umass.lastfm.Session
import org.asmatron.messengine.ControlEngine
import org.asmatron.messengine.engines.support.ControlEngineConfigurator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.slf4j.LoggerFactory

private const val USERNAME = "josdem"
private const val PASSWORD = "password"

internal class LoginControllerTest {
    private lateinit var loginController: LoginController

    private lateinit var user: User

    @Mock private lateinit var session: Session

    @Mock private lateinit var controlEngine: ControlEngine

    @Mock private lateinit var lastFMAuthenticator: LastFMAuthenticator

    @Mock private lateinit var controlEngineConfigurator: ControlEngineConfigurator

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(controlEngineConfigurator.controlEngine).thenReturn(controlEngine)
        loginController = LoginController(lastFMAuthenticator, controlEngineConfigurator)
        user = User(USERNAME, PASSWORD)
    }

    @Test
    fun `should login`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(lastFMAuthenticator.login(USERNAME, PASSWORD)).thenReturn(session)

        loginController.login(user)

        verify(controlEngine).set(Model.CURRENT_USER, user, null)
        verify(controlEngine).fireEvent(eq(Events.LOGGED), any())
    }

    @Test
    fun `should not login`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        loginController.login(user)

        verify(controlEngine).fireEvent(Events.LOGIN_FAILED)
    }
}
