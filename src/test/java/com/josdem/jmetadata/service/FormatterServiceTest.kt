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
    fun `should detect accent mark title`(testInfo: TestInfo) {
        log.info(testInfo.toString())
        metadata.title = "¿Cu&aacute;ndo?"

        assertTrue { formatterService.wasFormatted(metadata) }
        assertEquals("¿Cuándo?", metadata.title)
    }
}
