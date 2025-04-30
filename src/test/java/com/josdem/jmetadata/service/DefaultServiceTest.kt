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

import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.DefaultServiceImpl
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

private const val TOTAL_TRACKS = "2"
private const val TRACK_NUMBER_METADATA_ONE = "1"
private const val CD_NUMBER = "1"
private const val TOTAL_CD_NUMBER = "1"

internal class DefaultServiceTest {
    private lateinit var defaultService: DefaultService

    @Mock private lateinit var metadataService: MetadataService

    @Mock private lateinit var metadataTwo: Metadata

    @Mock private lateinit var metadataOne: Metadata

    private val metadataList = mutableListOf<Metadata>()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        defaultService = DefaultServiceImpl(metadataService)
    }

    @Test
    fun `should complete total tracks`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataOne.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataOne.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataOne.totalCds).thenReturn(TOTAL_CD_NUMBER)
        `when`(metadataOne.totalCds).thenReturn(TOTAL_CD_NUMBER)

        setTracksNumberExpectations()
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)

        assertTrue(defaultService.isCompletable(metadataList))
    }

    @Test
    fun shouldCompleteCdNumber(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataOne.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataTwo.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataOne.totalCds).thenReturn(TOTAL_CD_NUMBER)
        `when`(metadataTwo.totalCds).thenReturn(TOTAL_CD_NUMBER)
        setTracksNumberExpectations()
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)

        assertTrue(defaultService.isCompletable(metadataList))
    }

    @Test
    fun shouldCompleteTotalCds(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataOne.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataTwo.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataOne.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataTwo.cdNumber).thenReturn(CD_NUMBER)
        setTracksNumberExpectations()
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)

        assertTrue(defaultService.isCompletable(metadataList))
    }

    @Test
    fun shouldNotCompleteIfSingleTrack(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val metadataList = mutableListOf<Metadata>()

        `when`(metadataOne.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataTwo.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataOne.totalCds).thenReturn(TOTAL_CD_NUMBER)
        `when`(metadataTwo.totalCds).thenReturn(TOTAL_CD_NUMBER)
        `when`(metadataOne.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataTwo.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)
        metadataList.add(metadataOne)

        defaultService.isCompletable(metadataList)

        verify(metadataOne, never()).totalTracks = TOTAL_TRACKS
        verify(metadataOne, never()).cdNumber = CD_NUMBER
        verify(metadataOne, never()).totalCds = TOTAL_CD_NUMBER
    }

    @Test
    fun `should complete`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)
        setTracksNumberExpectations()

        defaultService.complete(metadataList)

        verify(metadataOne).totalTracks = TOTAL_TRACKS
        verify(metadataOne).cdNumber = CD_NUMBER
        verify(metadataOne).totalCds = TOTAL_CD_NUMBER
        verify(metadataOne).totalTracks = TOTAL_TRACKS
        verify(metadataOne).cdNumber = CD_NUMBER
        verify(metadataOne).totalCds = TOTAL_CD_NUMBER
    }

    @Test
    fun shouldNotCompleteMetadataWhenNoNecessary(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataOne.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataTwo.totalTracks).thenReturn(TOTAL_TRACKS)
        `when`(metadataOne.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataTwo.cdNumber).thenReturn(CD_NUMBER)
        `when`(metadataOne.totalCds).thenReturn(TOTAL_CD_NUMBER)
        `when`(metadataTwo.totalCds).thenReturn(TOTAL_CD_NUMBER)
        `when`(metadataService.isSameAlbum(metadataList)).thenReturn(true)

        assertFalse(defaultService.isCompletable(metadataList))
    }

    @Test
    fun shouldNotCompleteWhenNoTrackNumber(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataOne.trackNumber).thenReturn(StringUtils.EMPTY)
        `when`(metadataTwo.trackNumber).thenReturn(StringUtils.EMPTY)
        metadataList.add(metadataOne)
        metadataList.add(metadataTwo)

        defaultService.complete(metadataList)

        verify(metadataOne, never()).cdNumber = CD_NUMBER
        verify(metadataOne, never()).totalCds = TOTAL_CD_NUMBER
        verify(metadataTwo, never()).cdNumber = CD_NUMBER
        verify(metadataTwo, never()).totalCds = TOTAL_CD_NUMBER
    }

    private fun setTracksNumberExpectations() {
        `when`(metadataOne.trackNumber).thenReturn(TRACK_NUMBER_METADATA_ONE)
        `when`(metadataTwo.trackNumber).thenReturn(TOTAL_TRACKS)
        metadataList.add(metadataOne)
        metadataList.add(metadataTwo)
    }
}
