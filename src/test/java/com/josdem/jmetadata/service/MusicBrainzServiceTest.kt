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

import com.josdem.jmetadata.exception.BusinessException
import com.josdem.jmetadata.helper.RetrofitInstance
import com.josdem.jmetadata.model.Album
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.model.MusicBrainzResponse
import com.josdem.jmetadata.model.Release
import com.josdem.jmetadata.service.impl.MusicBrainzServiceImpl
import com.josdem.jmetadata.util.ApplicationState
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException

private const val ALBUM_NAME = "Night Life"
private const val ARTIST = "Pet shop boys"
private const val ALBUM_ID = "b04558a9-b69c-45bd-a6f4-d65706067780"

internal class MusicBrainzServiceTest {
    private lateinit var musicBrainzService: MusicBrainzServiceImpl

    private val metadata = Metadata()
    private val album = Album()

    @Mock private lateinit var retrofit: Retrofit

    @Mock private lateinit var call: Call<Album>

    @Mock private lateinit var restService: RestService

    @Mock private lateinit var imageService: ImageService

    @Mock private lateinit var retrofitInstance: RetrofitInstance

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val musicBrainzResponse = getExpectedResponse()
        ApplicationState.cache[ALBUM_NAME] = musicBrainzResponse
        `when`(retrofitInstance.retrofit).thenReturn(retrofit)
        `when`(retrofit.create(RestService::class.java)).thenReturn(restService)
        `when`(restService.getRelease(ALBUM_ID)).thenReturn(call)
        musicBrainzService = MusicBrainzServiceImpl(imageService, retrofitInstance)
        musicBrainzService.setup()
    }

    @Test
    fun `should getting release id by name`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val expectedAlbum = Album()
        expectedAlbum.id = ALBUM_ID
        `when`(call.execute()).thenReturn(retrofit2.Response.success(expectedAlbum))

        val result = musicBrainzService.getAlbumByName(ALBUM_NAME)

        assertEquals(ALBUM_ID, result.id)
    }

    @Test
    fun `should not getting release id by name due to exception`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(call.execute()).thenThrow(IOException("Network error"))
        assertThrows<RuntimeException> { musicBrainzService.getAlbumByName(ALBUM_NAME) }
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = ["", " "])
    fun `should complete year from album`(metadataYear: String?) {
        log.info("should complete year from album with metadataYear: $metadataYear")
        setMetadataExpectations()
        metadata.setYear(metadataYear)
        val metadataList = listOf(metadata)
        album.date = "1999-03-29"

        val result = musicBrainzService.completeYear(metadataList, album)

        assertEquals("1999", result.first().year)
        assertEquals(1, result.size)
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = ["", " "])
    fun `should not complete year if not valid format`(metadataYear: String?) {
        log.info("should not complete year if not valid format with metadataYear: $metadataYear")
        setMetadataExpectations()
        metadata.year = StringUtils.EMPTY
        val metadataList = listOf(metadata)
        album.date = metadataYear

        assertThrows<BusinessException> { musicBrainzService.completeYear(metadataList, album) }
    }

    private fun setMetadataExpectations() {
        metadata.album = ALBUM_NAME
        metadata.artist = ARTIST
    }

    private fun getExpectedResponse(): MusicBrainzResponse {
        val musicBrainzResponse = MusicBrainzResponse()
        val release = Release()
        release.id = ALBUM_ID
        val releases = listOf(release)
        musicBrainzResponse.releases = releases
        return musicBrainzResponse
    }
}
