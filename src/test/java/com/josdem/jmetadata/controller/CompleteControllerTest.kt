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

import com.josdem.jmetadata.action.ActionResult
import com.josdem.jmetadata.exception.BusinessException
import com.josdem.jmetadata.metadata.MetadataWriter
import com.josdem.jmetadata.model.CoverArt
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.LastfmService
import com.josdem.jmetadata.service.MusicBrainzService
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceAdapter
import com.josdem.jmetadata.service.impl.MusicBrainzCompleteServiceAdapter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.never
import org.slf4j.LoggerFactory
import java.awt.Image
import java.io.File

private const val ARTIST = "Dave Deen"
private const val TITLE = "Footprints (Original Mix)"
private const val ALBUM = "Footprints EP"
private const val GENRE = "Trance"
private const val TRACK_NUMBER = "10"
private const val TOTAL_TRACKS = "25"
private const val YEAR = "1990"
private const val CD_NUMBER = "1"
private const val TOTAL_CDS = "2"

internal class CompleteControllerTest {
    private lateinit var completeController: CompleteController

    @Mock private lateinit var file: File

    @Mock private lateinit var metadata: Metadata

    @Mock private lateinit var coverArt: CoverArt

    @Mock private lateinit var image: Image

    @Mock private lateinit var lastfmService: LastfmService

    @Mock private lateinit var metadataWriter: MetadataWriter

    @Mock private lateinit var musicBrainzService: MusicBrainzService

    @Mock private lateinit var musicBrainzServiceAdapter: MusicBrainzCompleteServiceAdapter

    @Mock private lateinit var lastFMCompleteServiceAdapter: LastFMCompleteServiceAdapter

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(metadata.artist).thenReturn(ARTIST)
        `when`(metadata.title).thenReturn(TITLE)
        `when`(metadata.album).thenReturn(ALBUM)
        `when`(metadata.trackNumber).thenReturn(TRACK_NUMBER)
        `when`(metadata.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadata.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadata.totalCds).thenReturn(TOTAL_CDS)
        `when`(metadata.year).thenReturn(YEAR)
        `when`(metadata.genre).thenReturn(GENRE)
        `when`(lastfmService.completeLastFM(metadata)).thenReturn(ActionResult.READY)

        completeController =
            CompleteController(metadataWriter, lastfmService, musicBrainzService, musicBrainzServiceAdapter, lastFMCompleteServiceAdapter)
    }

    @Test
    fun `should complete album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadata.file).thenReturn(file)

        val result = completeController.completeAlbum(metadata)

        verify(metadataWriter).setFile(file)
        verify(metadataWriter).writeArtist(ARTIST)
        verify(metadataWriter).writeTitle(TITLE)
        verify(metadataWriter).writeAlbum(ALBUM)
        verify(metadataWriter).writeTrackNumber(TRACK_NUMBER)
        verify(metadataWriter).writeTotalTracksNumber(TOTAL_TRACKS)
        verify(metadataWriter).writeCdNumber(CD_NUMBER)
        verify(metadataWriter).writeTotalCds(TOTAL_CDS)
        verify(metadataWriter).writeYear(YEAR)
        verify(metadataWriter).writeGenre(GENRE)
        assertEquals(ActionResult.UPDATED, result)
    }

    @Test
    fun `should remove cover art`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadata.newCoverArt).thenReturn(coverArt)
        `when`(coverArt.image).thenReturn(image)

        val result = completeController.completeAlbum(metadata)

        verify(metadataWriter).removeCoverArt()
        verify(metadataWriter).writeCoverArt(image)
        verify(metadata).coverArt = image
        assertEquals(ActionResult.UPDATED, result)
    }

    @Test
    fun `should not complete metadata due to error`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadata.file).thenReturn(file)
        `when`(metadataWriter.writeAlbum(ALBUM)).thenThrow(BusinessException::class.java)

        val result = completeController.completeAlbum(metadata)

        assertEquals(ActionResult.ERROR, result)
    }

    @Test
    fun `should complete metadata with LastFM service`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val metadataList = listOf(metadata)
        `when`(lastFMCompleteServiceAdapter.canComplete(metadataList)).thenReturn(true)

        completeController.completeLastFmMetadata(metadataList)

        verify(lastfmService).completeLastFM(metadata)
    }

    @Test
    fun `should validate lastFM service can not complete metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val metadataList = listOf(metadata)
        `when`(lastFMCompleteServiceAdapter.canComplete(metadataList)).thenReturn(false)

        completeController.completeLastFmMetadata(metadataList)

        verify(lastfmService, never()).completeLastFM(metadata)
    }
}
