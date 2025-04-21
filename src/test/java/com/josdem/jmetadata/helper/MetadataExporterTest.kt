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

import com.josdem.jmetadata.ApplicationConstants
import com.josdem.jmetadata.model.ExportPackage
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.FormatterService
import com.josdem.jmetadata.service.MetadataService
import com.josdem.jmetadata.util.FileUtils
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import java.io.File
import java.io.OutputStream

private const val DURATION = 397
private const val NEW_LINE = "\n"
private const val DASH = " - "
private const val DOT = ". "
private const val PAR_OPEN = " ("
private const val PAR_CLOSE = ")"
private const val BY = " by "

internal class MetadataExporterTest {
    private lateinit var metadataExporter: MetadataExporter

    @Mock private lateinit var fileUtils: FileUtils

    @Mock private lateinit var outStreamWriter: OutStreamWriter

    @Mock private lateinit var metadataService: MetadataService

    @Mock private lateinit var formatterService: FormatterService

    @Mock private lateinit var exportPackage: ExportPackage

    @Mock private lateinit var file: File

    @Mock private lateinit var outputStream: OutputStream

    @Mock private lateinit var metadata: Metadata

    private val metadataList = mutableListOf<Metadata>()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        metadataExporter = MetadataExporter(fileUtils, outStreamWriter, metadataService, formatterService)
        metadataList.add(metadata)
        `when`(metadata.album).thenReturn("Bliksem")
        `when`(metadata.artist).thenReturn("Sander Van Doorn")
        `when`(metadata.title).thenReturn("Bliksem track")
        `when`(metadata.length).thenReturn(DURATION)
        `when`(formatterService.getDuration(DURATION)).thenReturn("6:37")
        `when`(exportPackage.root).thenReturn(file)
        `when`(exportPackage.metadataList).thenReturn(metadataList)
        `when`(fileUtils.createFile(file, StringUtils.EMPTY, ApplicationConstants.FILE_EXT)).thenReturn(file)
        `when`(outStreamWriter.getWriter(file)).thenReturn(outputStream)
    }

    @Test
    fun `should export metadata to file`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)

        metadataExporter.export(exportPackage)

        verify(outputStream).write(metadata.album.toByteArray())
        verify(outputStream).write(BY.toByteArray())
        verify(outputStream, times(2)).write(metadata.artist.toByteArray())
        verify(outputStream, times(3)).write(NEW_LINE.toByteArray())
        verify(metadataService).isSameAlbum(metadataList)
        verify(outputStream).write("1".toByteArray())
        verify(outputStream).write(DOT.toByteArray())
        verify(outputStream).write(DASH.toByteArray())
        verify(outputStream).write(metadata.title.toByteArray())
        verify(outputStream).write(PAR_OPEN.toByteArray())
        verify(outputStream).write(formatterService.getDuration(metadata.length).toByteArray())
        verify(outputStream).write(PAR_CLOSE.toByteArray())
    }
}
