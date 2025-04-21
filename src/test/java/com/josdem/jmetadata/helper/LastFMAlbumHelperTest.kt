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

import de.umass.lastfm.Album
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.time.LocalDate

internal class LastFMAlbumHelperTest {
    private val lastFMAlbumHelper: LastFMAlbumHelper = LastFMAlbumHelper()

    private val releaseDate = LocalDate.now()

    @Mock private lateinit var album: Album

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get year from release date`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val year = lastFMAlbumHelper.getYear(releaseDate)
        assertEquals(year, "${releaseDate.year}") { "should get year from release date" }
    }

    @Test
    fun `should get empty year when no info`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val year = lastFMAlbumHelper.getYear(null)
        assertEquals(year, "") { "should get empty year when no info" }
    }

    @Test
    fun `should get genres from album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val tags = listOf("House")
        `when`(album.tags).thenReturn(tags)
        val result = lastFMAlbumHelper.getGenre(album)
        assertEquals("House", result) { "should get genre from album" }
    }

    @Test
    fun `should get empty genre when no info`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val tags = listOf("ThisGenreDoesNotExist")
        `when`(album.tags).thenReturn(tags)
        val result = lastFMAlbumHelper.getGenre(album)
        assertEquals(StringUtils.EMPTY, result) { "should get empty genre when no info" }
    }
}
