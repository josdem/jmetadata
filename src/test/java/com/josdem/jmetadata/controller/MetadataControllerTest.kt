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
import com.josdem.jmetadata.service.MetadataService
import org.asmatron.messengine.ControlEngine
import org.asmatron.messengine.engines.support.ControlEngineConfigurator
import org.asmatron.messengine.event.ValueEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import java.io.File
import java.util.Properties
import javax.swing.JFileChooser

internal class MetadataControllerTest {
    private lateinit var metadataController: MetadataController

    @Mock private lateinit var file: File

    @Mock private lateinit var properties: Properties

    @Mock private lateinit var metadataService: MetadataService

    @Mock private lateinit var controlEngine: ControlEngine

    @Mock private lateinit var controlEngineConfigurator: ControlEngineConfigurator

    @Mock private lateinit var jFileChooser: JFileChooser

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        metadataController = MetadataController(properties, metadataService, controlEngineConfigurator)
        metadataController.fileChooser = jFileChooser
    }

    @Test
    fun `should inform to the user it is selected an empty directory`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()

        `when`(file.exists()).thenReturn(true)
        `when`(metadataService.extractMetadata(file)).thenReturn(emptyList())

        metadataController.getMetadata()

        verify(jFileChooser).fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
        verify(controlEngine).fireEvent(Events.DIRECTORY_SELECTED, ValueEvent(file.absolutePath))
        verify(controlEngine).fireEvent(Events.DIRECTORY_EMPTY)
    }

    private fun setFileChooserExpectations() {
        `when`(jFileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION)
        `when`(jFileChooser.selectedFile).thenReturn(file)
        `when`(controlEngineConfigurator.controlEngine).thenReturn(controlEngine)
    }
}
