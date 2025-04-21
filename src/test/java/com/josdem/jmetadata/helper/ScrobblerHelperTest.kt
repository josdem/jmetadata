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

import com.josdem.jmetadata.action.ActionResult
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.model.Model
import com.josdem.jmetadata.model.User
import de.umass.lastfm.scrobble.ScrobbleResult
import org.apache.commons.lang3.StringUtils
import org.asmatron.messengine.ControlEngine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory

private const val USERNAME = "josdem"
private const val PASSWORD = "password"
private const val TRACK_NUMBER = "1"

internal class ScrobblerHelperTest {
    private lateinit var scrobblerHelper: ScrobblerHelper
    private lateinit var result: ActionResult

    @Mock private lateinit var lastFMTrackHelper: LastFMTrackHelper

    @Mock private lateinit var metadata: Metadata

    @Mock private lateinit var currentUser: User

    @Mock private lateinit var controlEngine: ControlEngine

    @Mock private lateinit var scrobbleResult: ScrobbleResult

    @Mock private lateinit var metadataMap: HashMap<Metadata, Long>

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        scrobblerHelper = ScrobblerHelper(lastFMTrackHelper)
        `when`(currentUser.username).thenReturn(USERNAME)
        `when`(currentUser.password).thenReturn(PASSWORD)
        `when`(controlEngine.get(Model.CURRENT_USER)).thenReturn(currentUser)
        scrobblerHelper.setControlEngine(controlEngine)
    }

    @Test
    fun `should not add scrobbling if track less than 240`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setExpectations()
        `when`(metadata.artist).thenReturn("Above & Beyond")
        `when`(metadata.title).thenReturn("Anjunabeach")

        result = scrobblerHelper.send(metadata)

        notSendToScrobblingMapAssertion()
    }

    @Test
    fun `should not add scrobbling if no artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setExpectations()
        `when`(metadata.artist).thenReturn(StringUtils.EMPTY)
        `when`(metadata.title).thenReturn("Anjunabeach")

        result = scrobblerHelper.send(metadata)

        notSendToScrobblingMapAssertion()
    }

    @Test
    fun `should not add scrobbling if no title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setExpectations()
        `when`(metadata.artist).thenReturn("Above & Beyond")
        `when`(metadata.title).thenReturn(StringUtils.EMPTY)

        result = scrobblerHelper.send(metadata)

        notSendToScrobblingMapAssertion()
    }

    @Test
    fun `should fail when submit a scrobbler`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataMap.get(metadata)).thenReturn(100L)
        setExpectations()
        setMetadataTrackExpectations()
        `when`(scrobbleResult.isSuccessful).thenReturn(false)
        `when`(
            lastFMTrackHelper.scrobble(
                metadata.artist,
                metadata.title,
                100,
                currentUser.session,
            ),
        ).thenReturn(scrobbleResult)

        assertEquals(ActionResult.SESSION_LESS, scrobblerHelper.send(metadata))
    }

    @Test
    fun `should return if no login`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setMetadataTrackExpectations()
        `when`(currentUser.username).thenReturn(StringUtils.EMPTY)

        assertEquals(ActionResult.NOT_LOGGED, scrobblerHelper.send(metadata))
    }

    private fun setExpectations() {
        `when`(metadata.album).thenReturn(StringUtils.EMPTY)
        `when`(metadata.length).thenReturn(1)
        `when`(metadata.trackNumber).thenReturn(TRACK_NUMBER)
    }

    private fun notSendToScrobblingMapAssertion() {
        verify(metadataMap, never()).size
        verify(metadataMap, never()).put(isA(Metadata::class.java), isA(Long::class.java))
        assertEquals(ActionResult.NOT_RECORDED, result)
    }

    private fun setMetadataTrackExpectations() {
        `when`(metadata.length).thenReturn(300)
        `when`(metadata.artist).thenReturn("Above & Beyond")
        `when`(metadata.title).thenReturn("Anjunabeach")
    }
}
