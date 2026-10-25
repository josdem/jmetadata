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
import com.josdem.jmetadata.exception.TooMuchFilesException
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.model.Model
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
import java.io.IOException
import java.util.Properties
import javax.swing.JFileChooser

private const val MAX_FILES_ALLOWED = "50"

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

    @Test
    fun `should inform to the user that the directory does not exist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()
        `when`(file.exists()).thenReturn(false)

        metadataController.getMetadata()

        verify(jFileChooser).fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
        verify(controlEngine).fireEvent(Events.DIRECTORY_NOT_EXIST, ValueEvent(file.toString()))
    }

    @Test
    fun `should detect user selects cancel option`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()
        `when`(jFileChooser.showOpenDialog(null)).thenReturn(JFileChooser.CANCEL_OPTION)

        metadataController.getMetadata()

        verify(controlEngine).fireEvent(Events.DIRECTORY_SELECTED_CANCEL)
    }

    @Test
    fun `should get metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()
        `when`(file.exists()).thenReturn(true)
        val metadata1 = Metadata()
        val metadata2 = Metadata()
        metadata1.title = "Wish you were here"
        metadata2.title = "The man who sold the world"
        val metadataList = listOf(metadata1, metadata2)
        `when`(metadataService.extractMetadata(file)).thenReturn(metadataList)

        metadataController.getMetadata()

        verify(jFileChooser).fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
        verify(controlEngine).set(Model.METADATA, metadataList, null)
        verify(controlEngine).fireEvent(Events.LOAD, ValueEvent(metadataList))
        verify(controlEngine).fireEvent(Events.LOADED)
    }

    @Test
    fun `should catch IOException`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()
        `when`(file.exists()).thenReturn(true)
        `when`(metadataService.extractMetadata(file)).thenThrow(IOException())

        metadataController.getMetadata()

        verify(controlEngine).fireEvent(Events.OPEN)
    }

    @Test
    fun `should catch illegal argument exception`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()
        `when`(file.exists()).thenReturn(true)
        `when`(metadataService.extractMetadata(file)).thenThrow(IllegalArgumentException())

        metadataController.getMetadata()

        verify(controlEngine).fireEvent(Events.OPEN)
    }

    @Test
    fun `should inform to the user too many files were selected`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setFileChooserExpectations()
        `when`(file.exists()).thenReturn(true)
        `when`(properties.getProperty("max.files.allowed")).thenReturn(MAX_FILES_ALLOWED)
        `when`(metadataService.extractMetadata(file)).thenThrow(TooMuchFilesException(Integer.valueOf(MAX_FILES_ALLOWED)))

        metadataController.getMetadata()

        verify(controlEngine).fireEvent(Events.DIRECTORY_SELECTED, ValueEvent(file.absolutePath))
        verify(controlEngine).fireEvent(Events.MUCH_FILES_LOADED, ValueEvent(MAX_FILES_ALLOWED))
    }

    private fun setFileChooserExpectations() {
        `when`(jFileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION)
        `when`(jFileChooser.selectedFile).thenReturn(file)
        `when`(controlEngineConfigurator.controlEngine).thenReturn(controlEngine)
    }
}
