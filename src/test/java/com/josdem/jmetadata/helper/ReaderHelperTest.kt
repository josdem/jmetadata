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

import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

internal class ReaderHelperTest {
    private val readerHelper = ReaderHelper()

    @Mock private lateinit var tag: Tag

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val genre = "Minimal Techno"
        `when`(tag.getFirst(FieldKey.GENRE)).thenReturn(genre)
        assertEquals(genre, readerHelper.getGenre(tag, genre)) { "should get genre" }
    }

    @Test
    fun `should get genre by code`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val genreAsCode = "(18)"
        val genre = "Techno"
        `when`(tag.getFirst(FieldKey.GENRE)).thenReturn(genreAsCode)
        assertEquals(genre, readerHelper.getGenre(tag, genreAsCode)) { "should get genre by code" }
    }

    @Test
    fun `should return same value when no valid genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val genre = "(None)"
        `when`(tag.getFirst(FieldKey.GENRE)).thenReturn(genre)
        assertEquals(genre, readerHelper.getGenre(tag, genre)) { "should return same value when no valid genre" }
    }
}
