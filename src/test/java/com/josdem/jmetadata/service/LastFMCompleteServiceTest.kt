package com.josdem.jmetadata.service

import com.josdem.jmetadata.helper.LastFMAlbumHelper
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory

internal class LastFMCompleteServiceTest {
    private lateinit var service: LastFMCompleteService

    @Mock private lateinit var imageService: ImageService

    @Mock private lateinit var lastFMAlbumHelper: LastFMAlbumHelper

    private var metadata = Metadata()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        service = LastFMCompleteServiceImpl(imageService, lastFMAlbumHelper)
    }

    @Test
    fun `should complete if no metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.artist = "Linas"
        metadata.album = "Time Lapse"

        assertTrue { service.canLastFMHelpToComplete(metadata) }
    }
}
