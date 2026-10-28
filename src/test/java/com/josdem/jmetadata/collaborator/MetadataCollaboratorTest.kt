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

import com.josdem.jmetadata.model.Metadata
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals

private const val ARTIST = "Boris Brejcha"

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

    private fun setMetadataExpectations() {
        metadataList.add(metadataOne)
        metadataList.add(metadataTwo)
        metadataCollaborator.setMetadatas(metadataList)
    }
}
