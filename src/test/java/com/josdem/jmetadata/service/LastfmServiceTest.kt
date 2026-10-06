package com.josdem.jmetadata.service

import com.josdem.jmetadata.action.ActionResult
import com.josdem.jmetadata.model.LastfmAlbum
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.LastfmServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.awt.Image

internal class LastfmServiceTest {
    private lateinit var lastfmService: LastfmServiceImpl

    @Mock
    private lateinit var completeService: LastFMCompleteService

    @Mock
    private lateinit var metadata: Metadata

    @Mock
    private lateinit var lastfmAlbum: LastfmAlbum

    @Mock
    private lateinit var imageIcon: Image

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true)
        lastfmService = LastfmServiceImpl(completeService)
    }

    @Test
    fun `should complete metadata using LastFM service`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setCompleteHelperExpectations()
        `when`(lastfmAlbum.imageIcon).thenReturn(imageIcon)
        `when`(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.NEW)

        val result = lastfmService.completeLastFM(metadata)

        verify(completeService).isSomethingNew(lastfmAlbum, metadata)
        assertEquals(ActionResult.NEW, result)
    }

    private fun setCompleteHelperExpectations() {
        `when`(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true)
        `when`(completeService.getLastFM(metadata)).thenReturn(lastfmAlbum)
    }
}
