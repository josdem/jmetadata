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

import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LastFmCompleteServiceAdapterTest {
    private lateinit var lastFmCompleteServiceAdapter: LastFMCompleteServiceAdapter

    private var metadata = Metadata()

    @Mock
    private lateinit var lastFMCompleteService: LastFMCompleteService

    @Mock
    private lateinit var metadataService: MetadataService

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        lastFmCompleteServiceAdapter = LastFMCompleteServiceAdapter(lastFMCompleteService, metadataService)
    }

    @Test
    fun `should detect if I can complete metadata due to same album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameAlbum(listOf(metadata))).thenReturn(true)
        assertTrue { lastFmCompleteServiceAdapter.canComplete(listOf(metadata)) }
    }

    @Test
    fun `should detect if I can complete metadata due to same artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameArtist(listOf(metadata))).thenReturn(true)
        assertTrue { lastFmCompleteServiceAdapter.canComplete(listOf(metadata)) }
    }

    @Test
    fun `should detect when I cannot complete metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameAlbum(listOf(metadata))).thenReturn(false)
        `when`(metadataService.isSameArtist(listOf(metadata))).thenReturn(false)
        assertFalse { lastFmCompleteServiceAdapter.canComplete(listOf(metadata)) }
    }
}
