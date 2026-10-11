package com.josdem.jmetadata.service

import com.josdem.jmetadata.helper.MetadataHelper
import com.josdem.jmetadata.metadata.Mp3Reader
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.MetadataServiceImpl
import com.josdem.jmetadata.util.FileUtils
import org.asmatron.messengine.ControlEngine
import org.asmatron.messengine.engines.support.ControlEngineConfigurator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.io.File
import java.util.*


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

    @Mock private lateinit var root: File

    @Mock private lateinit var metadata: Metadata

    private val fileList: MutableList<File?> = ArrayList<File?>()

    private val log = LoggerFactory.getLogger(this::class.java)


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(controlEngineConfigurator.controlEngine).thenReturn(controlEngine)
        `when`(properties.getProperty("max.files.allowed")).thenReturn("50")

        metadataService = MetadataServiceImpl(
            controlEngineConfigurator,
            metadataHelper,
            extractService,
            mp3Reader,
            fileUtils,
            properties
        )
    }

    @Test
    fun `should extract metadata when mp3`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMp3Expectations()
        setFileListExpectations()

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