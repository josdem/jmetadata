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
import com.josdem.jmetadata.service.FormatterService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

internal class FormatterControllerTest {
    private lateinit var formatterController: FormatterController

    @Mock private lateinit var metadata: Metadata

    @Mock private lateinit var formatterService: FormatterService

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        formatterController = FormatterController(formatterService)
    }

    @Test
    fun `should format`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(formatterService.wasFormatted(metadata)).thenReturn(true)
        `when`(formatterService.wasCamelized(metadata)).thenReturn(false)

        val result = formatterController.format(metadata)

        assertEquals(ActionResult.NEW, result)
    }

    @Test
    fun `should format when no camelized`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(formatterService.wasFormatted(metadata)).thenReturn(false)
        `when`(formatterService.wasCamelized(metadata)).thenReturn(true)

        val result = formatterController.format(metadata)

        assertEquals(ActionResult.NEW, result)
    }

    @Test
    fun `should not format`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(formatterService.wasFormatted(metadata)).thenReturn(false)
        `when`(formatterService.wasCamelized(metadata)).thenReturn(false)

        val result = formatterController.format(metadata)

        assertEquals(ActionResult.READY, result)
    }
}
