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
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.DefaultService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals

internal class DefaultControllerTest {
    private lateinit var defaultController: DefaultController

    @Mock private lateinit var defaultService: DefaultService

    @Mock private lateinit var metadataList: List<Metadata>

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        defaultController = DefaultController(defaultService)
    }

    @Test
    fun `should complete total tracks`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(defaultService.isCompletable(metadataList)).thenReturn(true)

        val result = defaultController.complete(metadataList)

        verify(defaultService).complete(metadataList)
        assertEquals(ActionResult.NEW, result)
    }

    @Test
    fun `should not complete total tracks`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(defaultService.isCompletable(metadataList)).thenReturn(false)

        val result = defaultController.complete(metadataList)

        verify(defaultService, never()).complete(metadataList)
        assertEquals(ActionResult.READY, result)
    }
}
