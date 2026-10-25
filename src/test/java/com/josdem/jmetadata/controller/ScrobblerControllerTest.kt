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

import com.josdem.jmetadata.action.ActionResult
import com.josdem.jmetadata.helper.ScrobblerHelper
import com.josdem.jmetadata.model.Metadata
import org.asmatron.messengine.ControlEngine
import org.asmatron.messengine.engines.support.ControlEngineConfigurator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals

internal class ScrobblerControllerTest {
    private lateinit var scrobblerController: ScrobblerController

    @Mock private lateinit var metadata: Metadata

    @Mock private lateinit var scrobblerHelper: ScrobblerHelper

    @Mock private lateinit var controlEngine: ControlEngine

    @Mock private lateinit var controlEngineConfigurator: ControlEngineConfigurator

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(controlEngineConfigurator.controlEngine).thenReturn(controlEngine)
        scrobblerController = ScrobblerController(scrobblerHelper, controlEngineConfigurator)
    }

    @Test
    fun `should send metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(scrobblerHelper.send(metadata)).thenReturn(ActionResult.NEW)

        val result = scrobblerHelper.send(metadata)

        verify(scrobblerHelper).send(metadata)
        assertEquals(ActionResult.NEW, result)
    }

    @Test
    fun `should inform an error when scrobbling`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(scrobblerHelper.send(metadata)).thenReturn(ActionResult.ERROR)

        val result = scrobblerHelper.send(metadata)

        verify(scrobblerHelper).send(metadata)
        assertEquals(ActionResult.ERROR, result)
    }
}
