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

package com.josdem.jmetadata.service

import com.josdem.jmetadata.action.ActionResult
import com.josdem.jmetadata.model.LastfmAlbum
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.LastfmServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.awt.Image

private const val GENRE = "Minimal Techno"

internal class LastfmServiceTest {
    private lateinit var lastfmService: LastfmServiceImpl

    @Mock
    private lateinit var completeService: LastFMCompleteService

    @Mock
    private lateinit var metadata: Metadata

    @Mock
    private lateinit var lastfmAlbum: LastfmAlbum

    @Mock
    private lateinit var imageIcon: Image

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true)
        lastfmService = LastfmServiceImpl(completeService)
    }

    @Test
    fun `should complete metadata using LastFM service`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setCompleteHelperExpectations()
        `when`(lastfmAlbum.imageIcon).thenReturn(imageIcon)
        `when`(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.NEW)

        val result = lastfmService.completeLastFM(metadata)

        verify(completeService).isSomethingNew(lastfmAlbum, metadata)
        assertEquals(ActionResult.NEW, result)
    }

    @Test
    fun `should not complete metadata using LastFM service when no new data`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(completeService.canLastFMHelpToComplete(metadata)).thenReturn(false)

        val result = lastfmService.completeLastFM(metadata)

        assertEquals(ActionResult.READY, result)
    }

    @Test
    fun `should not complete genre if the file has one`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setCompleteHelperExpectations()
        `when`(metadata.genre).thenReturn(GENRE)

        lastfmService.completeLastFM(metadata)

        verify(metadata, never()).genre = isA(String::class.java)
    }

    @Test
    fun `should return metadata complete if Lastfm has no new values`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setCompleteHelperExpectations()
        `when`(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.READY)

        val result = lastfmService.completeLastFM(metadata)

        assertEquals(ActionResult.READY, result)
    }

    private fun setCompleteHelperExpectations() {
        `when`(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true)
        `when`(completeService.getLastFM(metadata)).thenReturn(lastfmAlbum)
    }
}
