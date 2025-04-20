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

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.io.File

@SpringBootTest
@ContextConfiguration(classes = [ApplicationContextSingleton::class, AudioFileHelper::class])
internal class AudioFileHelperTest {
    @Autowired
    private lateinit var audioFileHelper: AudioFileHelper

    private val pepeGardenFile = File("src/test/resources/audio/Jaytech - Pepe Garden (Original Mix).mp3")

    private val log = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `should read an audio file`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertNotNull(audioFileHelper.read(pepeGardenFile)) { "should read an audio file" }
    }
}
