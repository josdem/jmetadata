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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory

internal class ExporterHelperTest {
    private lateinit var exporterHelper: ExporterHelper

    @Mock
    private lateinit var imageExporter: ImageExporter

    @Mock
    private lateinit var metadataExporter: MetadataExporter

    @Mock
    private lateinit var exportPackage: ExportPackage

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        exporterHelper = ExporterHelper(imageExporter, metadataExporter)
    }

    @Test
    fun `should export image`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        exporterHelper.export(exportPackage)
        verify(imageExporter).export(exportPackage)
        verify(metadataExporter).export(exportPackage)
    }
}
