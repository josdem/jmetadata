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
import com.josdem.jmetadata.model.Metadata
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory

private const val ARTIST = "Daniel Kandi"
private const val TITLE = "Make Me Believe"
private const val ALBUM = "Anjunabeats 5"
private const val TRACK_NUMBER = "5"
private const val TOTAL_TRACKS = "13"
private const val GENRE = "Minimal Techno"
private const val YEAR = "2001"
private const val CD_NUMBER = "1"
private const val TOTAL_CDS_NUMBER = "2"

internal class MetadataAdapterTest {
    private val metadataAdapter = MetadataAdapter()

    @Mock private lateinit var metadata: Metadata

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should update artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.ARTIST_COLUMN, ARTIST)
        verify(metadata).artist = ARTIST
    }

    @Test
    fun `should update title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.TITLE_COLUMN, TITLE)
        verify(metadata).title = TITLE
    }

    @Test
    fun `should update album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.ALBUM_COLUMN, ALBUM)
        verify(metadata).album = ALBUM
    }

    @Test
    fun `should update track number`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.TRACK_NUMBER_COLUMN, TRACK_NUMBER)
        verify(metadata).trackNumber = TRACK_NUMBER
    }

    @Test
    fun `should update total tracks number`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN, TOTAL_TRACKS)
        verify(metadata).totalTracks = TOTAL_TRACKS
    }

    @Test
    fun `should update genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.GENRE_COLUMN, GENRE)
        verify(metadata).genre = GENRE
    }

    @Test
    fun `should update year`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.YEAR_COLUMN, YEAR)
        verify(metadata).year = YEAR
    }

    @Test
    fun `should update cd number`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.CD_NUMBER_COLUMN, CD_NUMBER)
        verify(metadata).cdNumber = CD_NUMBER
    }

    @Test
    fun `should update total cds`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadataAdapter.update(metadata, ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN, TOTAL_CDS_NUMBER)
        verify(metadata).totalCds = TOTAL_CDS_NUMBER
    }
}
