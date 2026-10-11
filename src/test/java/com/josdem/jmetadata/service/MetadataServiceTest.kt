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

package com.josdem.jmetadata.service

import com.josdem.jmetadata.helper.MetadataHelper
import com.josdem.jmetadata.metadata.Mp3Reader
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.MetadataServiceImpl
import com.josdem.jmetadata.util.FileUtils
import org.asmatron.messengine.ControlEngine
import org.asmatron.messengine.engines.support.ControlEngineConfigurator
import org.junit.jupiter.api.Assertions.assertTrue
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
import kotlin.test.assertEquals

private const val ALBUM = "Lemon Flavored Kiss"

internal class MetadataServiceTest {
    private lateinit var metadataService: MetadataServiceImpl

    @Mock private lateinit var controlEngineConfigurator: ControlEngineConfigurator

    @Mock private lateinit var controlEngine: ControlEngine

    @Mock private lateinit var properties: Properties

    @Mock private lateinit var metadataHelper: MetadataHelper

    @Mock private lateinit var extractService: ExtractService

    @Mock private lateinit var mp3Reader: Mp3Reader

    @Mock private lateinit var fileUtils: FileUtils

    @Mock private lateinit var pepeGarden: File

    @Mock private lateinit var styleFile: File

    @Mock private lateinit var root: File

    @Mock private lateinit var metadata: Metadata

    @Mock private lateinit var anotherMetadata: Metadata

    @Mock private lateinit var filesWithoutMinimumMetadata: MutableSet<File?>

    private val fileList: MutableList<File?> = ArrayList<File?>()

    private val metadatas: MutableList<Metadata?> = ArrayList<Metadata?>()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(controlEngineConfigurator.controlEngine).thenReturn(controlEngine)
        `when`(properties.getProperty("max.files.allowed")).thenReturn("50")

        metadataService =
            MetadataServiceImpl(
                controlEngineConfigurator,
                metadataHelper,
                extractService,
                mp3Reader,
                fileUtils,
                properties,
            )
    }

    @Test
    fun `should extract metadata when mp3`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMp3Expectations()
        setFileListExpectations()

        val metadataList = metadataService.extractMetadata(root)
        val result = metadataList.first()

        assertEquals(1, metadataList.size)
        verify(fileUtils).getFileList(root)
        assertEquals("Jaytech", result.artist)
    }

    @Test
    fun `should not extracting metadata due to not having a valid audio file`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        fileList.add(styleFile)
        `when`(fileUtils.getFileList(root)).thenReturn(fileList)

        val metadataList = metadataService.extractMetadata(root)

        assertTrue(metadataList.isEmpty(), "should be empty due to not valid audio file")
    }

    @Test
    fun `should clean metadata list`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMp3Expectations()
        setFileListExpectations()

        var metadataList = metadataService.extractMetadata(root)

        assertEquals(1, metadataList.size)

        metadataList = metadataService.extractMetadata(root)

        assertEquals(1, metadataList.size)
    }

    @Test
    fun `should extract metadata from file name`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMp3Expectations()
        fileList.add(pepeGarden)

        `when`(metadataHelper.createHashSet()).thenReturn(filesWithoutMinimumMetadata)
        `when`(fileUtils.getFileList(root)).thenReturn(fileList)

        val metadataList = metadataService.extractMetadata(root)

        assertEquals(1, metadataList.size)
        verify(extractService).extractFromFileName(pepeGarden)
        verify(filesWithoutMinimumMetadata).add(pepeGarden)
    }

    @Test
    fun `should validate same album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadata.album).thenReturn(ALBUM)
        `when`(anotherMetadata.album).thenReturn(ALBUM)
        addMetadatas()

        assertTrue { metadataService.isSameAlbum(metadatas) }
    }

    private fun addMetadatas() {
        metadatas.add(metadata)
        metadatas.add(anotherMetadata)
    }

    private fun setFileListExpectations() {
        `when`(metadata.artist).thenReturn("Jaytech")
        `when`(metadata.title).thenReturn("Pepe Garden (Original Mix)")
        fileList.add(pepeGarden)
        `when`(fileUtils.getFileList(root)).thenReturn(fileList)
    }

    private fun setMp3Expectations() {
        `when`(fileUtils.isMp3File(pepeGarden)).thenReturn(true)
        `when`(mp3Reader.getMetadata(pepeGarden)).thenReturn(metadata)
    }
}
