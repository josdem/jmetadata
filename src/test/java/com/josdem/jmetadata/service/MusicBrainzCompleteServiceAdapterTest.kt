package com.josdem.jmetadata.service

import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.MusicBrainzCompleteServiceAdapter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

internal class MusicBrainzCompleteServiceAdapterTest {
    private lateinit var musicBrainzCompleteServiceAdapter: MusicBrainzCompleteServiceAdapter

    private var metadata = Metadata()

    @Mock
    private lateinit var musicBrainzService: MusicBrainzService

    @Mock
    private lateinit var metadataService: MetadataService

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        musicBrainzCompleteServiceAdapter = MusicBrainzCompleteServiceAdapter(musicBrainzService, metadataService)
    }

    @Test
    fun `should detect if I can complete metadata due to same album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameAlbum(listOf(metadata))).thenReturn(true)
        assertTrue { musicBrainzCompleteServiceAdapter.canComplete(listOf(metadata)) }
    }

    @Test
    fun `should detect if I can complete metadata due to same artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameArtist(listOf(metadata))).thenReturn(true)
        assertTrue { musicBrainzCompleteServiceAdapter.canComplete(listOf(metadata)) }
    }

    @Test
    fun `should validate that I cannot complete metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(metadataService.isSameAlbum(listOf(metadata))).thenReturn(false)
        `when`(metadataService.isSameArtist(listOf(metadata))).thenReturn(false)
        assertTrue { !musicBrainzCompleteServiceAdapter.canComplete(listOf(metadata)) }
    }
}
