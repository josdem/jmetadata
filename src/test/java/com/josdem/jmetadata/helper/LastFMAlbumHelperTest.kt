package com.josdem.jmetadata.helper

import de.umass.lastfm.Album
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.time.LocalDate

internal class LastFMAlbumHelperTest {
    private val lastFMAlbumHelper: LastFMAlbumHelper = LastFMAlbumHelper()

    private val releaseDate = LocalDate.now()

    @Mock private lateinit var album: Album

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get year from release date`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        val year = lastFMAlbumHelper.getYear(releaseDate)
        assertEquals(year, "${releaseDate.year}") { "should get year from release date" }
    }
}
