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

import org.jaudiotagger.audio.AudioHeader
import org.jaudiotagger.tag.Tag
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import kotlin.test.assertTrue

internal class JAudioTaggerCollaboratorTest {
    private val jAudioTaggerCollaborator = JAudioTaggerCollaborator()

    @Mock private lateinit var tag: Tag

    @Mock private lateinit var header: AudioHeader

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should validate tag and header`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertTrue { jAudioTaggerCollaborator.isValid(tag, header) }
    }

    @Test
    fun `should detect invalid tag`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertFalse { jAudioTaggerCollaborator.isValid(null, header) }
    }

    @Test
    fun `should detect invalid header`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertFalse { jAudioTaggerCollaborator.isValid(tag, null) }
    }
}
