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

import com.josdem.jmetadata.helper.ApplicationContextSingleton
import com.josdem.jmetadata.helper.FormatterHelper
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.FormatterServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import kotlin.test.assertTrue

private const val ARTIST = "angel tears"
private const val TITLE = "legends of the fall"
private const val ALBUM = "vision"

@SpringBootTest
@ContextConfiguration(classes = [ApplicationContextSingleton::class, FormatterServiceImpl::class, FormatterHelper::class])
internal class FormatterServiceTest {
    @Autowired
    private lateinit var formatterService: FormatterService

    private val metadata = Metadata()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        metadata.artist = ARTIST
        metadata.title = TITLE
        metadata.album = ALBUM
    }

    @Test
    fun `should format a accent mark title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.title = "¿Cu&aacute;ndo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cuándo?", metadata.title)
    }

    @Test
    fun `should format e accent mark title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.title = "¿Qu&eacute;?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Qué?", metadata.title)
    }

    @Test
    fun `should format i accent mark title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.title = "m&iacute;"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("mí", metadata.title)
    }

    @Test
    fun `should format o accent mark title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.title = "¿C&oacute;mo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cómo?", metadata.title)
    }

    @Test
    fun `should format u accent mark title`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.title = "t&uacute;"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("tú", metadata.title)
    }

    @Test
    fun `should format a accent mark artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "¿Cu&aacute;ndo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cuándo?", metadata.artist)
    }

    @Test
    fun `should format e accent mark artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "¿Qu&eacute;?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Qué?", metadata.artist)
    }

    @Test
    fun `should format i accent mark artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "m&iacute;"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("mí", metadata.artist)
    }

    @Test
    fun `should format o accent mark artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "¿C&oacute;mo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cómo?", metadata.artist)
    }

    @Test
    fun `should format u accent mark artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "t&uacute;"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("tú", metadata.artist)
    }

    @Test
    fun `should format a accent mark album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = "¿Cu&aacute;ndo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cuándo?", metadata.album)
    }

    @Test
    fun `should format e accent mark album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = "¿Qu&eacute;?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Qué?", metadata.album)
    }

    @Test
    fun `should format i accent mark album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = "m&iacute;"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("mí", metadata.album)
    }

    @Test
    fun `should format o accent mark album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = "¿C&oacute;mo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cómo?", metadata.album)
    }

    @Test
    fun `should format u accent mark album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = "t&uacute;"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("tú", metadata.album)
    }

    @Test
    fun `should validate when title is without capital letters`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.title = TITLE

        assertTrue { formatterService.wasCamelized(metadata) }
        assertEquals("Legends Of The Fall", metadata.title)
    }

    @Test
    fun `should validate when artist is without capital letters`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = ARTIST

        assertTrue { formatterService.wasCamelized(metadata) }
        assertEquals("Angel Tears", metadata.artist)
    }

    @Test
    fun `should validate when album is without capital letters`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = ALBUM

        assertTrue { formatterService.wasCamelized(metadata) }
        assertEquals("Vision", metadata.album)
    }
}
