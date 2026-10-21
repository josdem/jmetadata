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
}
