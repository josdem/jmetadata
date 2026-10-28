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

package com.josdem.jmetadata.collaborator

import com.josdem.jmetadata.exception.BusinessException
import com.josdem.jmetadata.model.Metadata
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals

private const val ARTIST = "Boris Brejcha"
private const val ALBUM = "Feuerfalter Part 02"
private const val GENRE = "Tech House"
private const val YEAR = "2024"

internal class MetadataCollaboratorTest {
    private val metadataCollaborator = MetadataCollaborator()

    @Mock private lateinit var metadataOne: Metadata

    @Mock private lateinit var metadataTwo: Metadata

    private val metadataList = ArrayList<Metadata>()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.artist).thenReturn(ARTIST)
        `when`(metadataTwo.artist).thenReturn(ARTIST)

        assertEquals(ARTIST, metadataCollaborator.artist)
    }

    @Test
    fun `should validate the collection has different artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.artist).thenReturn(ARTIST)
        `when`(metadataTwo.artist).thenReturn("Paul Van Dyk")

        assertEquals(StringUtils.EMPTY, metadataCollaborator.artist)
    }

    @Test
    fun `should validate an invalid artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.artist).thenReturn(null)
        `when`(metadataTwo.artist).thenReturn(null)

        assertThrows<BusinessException> { metadataCollaborator.artist }
    }

    @Test
    fun `should get album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.album).thenReturn(ALBUM)
        `when`(metadataTwo.album).thenReturn(ALBUM)

        assertEquals(ALBUM, metadataCollaborator.album)
    }

    @Test
    fun `should validate the collection has different album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.album).thenReturn(ALBUM)
        `when`(metadataTwo.album).thenReturn("Elements Of Life")

        assertEquals(StringUtils.EMPTY, metadataCollaborator.album)
    }

    @Test
    fun `should validate an invalid album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.album).thenReturn(null)
        `when`(metadataTwo.album).thenReturn(null)

        assertThrows<BusinessException> { metadataCollaborator.album }
    }

    @Test
    fun `should get genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.genre).thenReturn(GENRE)
        `when`(metadataTwo.genre).thenReturn(GENRE)

        assertEquals(GENRE, metadataCollaborator.genre)
    }

    @Test
    fun `should validate the collection has different genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.genre).thenReturn(GENRE)
        `when`(metadataTwo.genre).thenReturn("Trance")

        assertEquals(StringUtils.EMPTY, metadataCollaborator.genre)
    }

    @Test
    fun `should validate an invalid genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.genre).thenReturn(null)
        `when`(metadataTwo.genre).thenReturn(null)

        assertThrows<BusinessException> { metadataCollaborator.genre }
    }

    @Test
    fun `should get year`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.year).thenReturn(YEAR)
        `when`(metadataTwo.year).thenReturn(YEAR)

        assertEquals(YEAR, metadataCollaborator.year)
    }

    @Test
    fun `should validate the collection has different year`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.year).thenReturn(YEAR)
        `when`(metadataTwo.year).thenReturn("Trance")

        assertEquals(StringUtils.EMPTY, metadataCollaborator.year)
    }

    @Test
    fun `should validate an invalid year`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataExpectations()
        `when`(metadataOne.year).thenReturn(null)
        `when`(metadataTwo.year).thenReturn(null)

        assertThrows<BusinessException> { metadataCollaborator.year }
    }

    private fun setMetadataExpectations() {
        metadataList.add(metadataOne)
        metadataList.add(metadataTwo)
        metadataCollaborator.setMetadataList(metadataList)
    }
}
