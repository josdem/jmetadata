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

import com.josdem.jmetadata.helper.LastFMAlbumHelper
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

internal class LastFMCompleteServiceTest {
    private lateinit var service: LastFMCompleteService

    @Mock private lateinit var imageService: ImageService

    @Mock private lateinit var lastFMAlbumHelper: LastFMAlbumHelper

    private var metadata = Metadata()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        service = LastFMCompleteServiceImpl(imageService, lastFMAlbumHelper)
    }

    @Test
    fun `should complete if no metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "Linas"
        metadata.album = "Time Lapse"

        assertTrue { service.canLastFMHelpToComplete(metadata) }
    }
}
