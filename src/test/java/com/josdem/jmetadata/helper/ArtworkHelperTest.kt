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

@SpringBootTest
@ContextConfiguration(classes = [ApplicationContextSingleton::class, ArtworkHelper::class])
internal class ArtworkHelperTest {
    @Autowired
    private lateinit var artworkHelper: ArtworkHelper

    private val log = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `should create an artwork helper`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertNotNull(artworkHelper.createArtwork()) { "should create an artwork" }
    }
}
