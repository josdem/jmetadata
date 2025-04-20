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

import com.josdem.jmetadata.model.ExportPackage
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.MetadataService
import com.josdem.jmetadata.util.ImageUtils
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import java.awt.Image
import java.io.File

internal class ImageExporterTest {
    private lateinit var imageExporter: ImageExporter
    private lateinit var exportPackage: ExportPackage

    @Mock private lateinit var imageUtils: ImageUtils

    @Mock private lateinit var metadata: Metadata

    @Mock private lateinit var coverArt: Image

    @Mock private lateinit var metadataService: MetadataService

    @Mock private lateinit var root: File

    private val metadataList = mutableListOf<Metadata>()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        val title = "Bliksem"
        val album = "Bliksem Album"
        val artist = "Sander van Doorn"
        MockitoAnnotations.openMocks(this)
        `when`(metadata.album).thenReturn(album)
        `when`(metadata.artist).thenReturn(artist)
        `when`(metadata.title).thenReturn(title)
        metadataList.add(metadata)
        exportPackage = ExportPackage(root, metadataList)
        imageExporter = ImageExporter(imageUtils, metadataService)
    }

    @Test
    fun `should export image`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadata.coverArt).thenReturn(coverArt)
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)
        imageExporter.export(exportPackage)
        verify(imageUtils).saveCoverArtToFile(metadataList.first().coverArt, root, StringUtils.EMPTY)
    }
}
