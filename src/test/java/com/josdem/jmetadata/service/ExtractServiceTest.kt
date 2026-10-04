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

import com.josdem.jmetadata.service.impl.ExtractServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.test.assertEquals

internal class ExtractServiceTest {
    private lateinit var extractService: ExtractService

    @Mock private lateinit var file: File

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        extractService = ExtractServiceImpl()
    }

    @Test
    fun `should extract metadata from file when dash`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val fileName = "Jennifer Lopez - 9A - 112.mp3"
        `when`(file.name).thenReturn(fileName)

        val result = extractService.extractFromFileName(file)

        assertEquals("Jennifer Lopez", result.artist)
        assertEquals("9A", result.title)
    }
}
