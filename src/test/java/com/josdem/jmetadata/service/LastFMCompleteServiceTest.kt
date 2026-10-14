

package com.josdem.jmetadata.service

import com.josdem.jmetadata.action.ActionResult
import com.josdem.jmetadata.helper.LastFMAlbumHelper
import com.josdem.jmetadata.model.LastfmAlbum
import com.josdem.jmetadata.model.Metadata
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceImpl
import com.josdem.jmetadata.util.ApplicationState
import de.umass.lastfm.Album
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.isA
import org.slf4j.LoggerFactory
import java.awt.Image
import java.util.Date

private const val ARTIST = "Linas"
private const val ALBUM = "Time Lapse"
private const val YEAR = "2011"
private const val GENRE = "Minimal Techno"

internal class LastFMCompleteServiceTest {
    private lateinit var service: LastFMCompleteService

    @Mock private lateinit var lastfmAlbum: LastfmAlbum

    @Mock private lateinit var imageService: ImageService

    @Mock private lateinit var lastFMAlbumHelper: LastFMAlbumHelper

    @Mock private lateinit var image: Image

    @Mock private lateinit var albumLastFM: Album

    private var metadata = Metadata()

    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(lastFMAlbumHelper.getAlbum(ARTIST, ALBUM)).thenReturn(albumLastFM)
        service = LastFMCompleteServiceImpl(imageService, lastFMAlbumHelper)
    }

    @Test
    fun `should complete if no metadata`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()

        assertTrue { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should complete if no cover art`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()
        metadata.year = YEAR
        metadata.genre = GENRE

        assertTrue { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should complete if no genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()
        metadata.year = YEAR
        metadata.coverArt = image

        assertTrue { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should complete if no year`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()
        metadata.genre = GENRE
        metadata.coverArt = image

        assertTrue { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should validate if metadata is complete`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()
        setYearGenreAndCoverArt()

        assertFalse { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should not complete if no artist`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setYearGenreAndCoverArt()
        metadata.album = ALBUM

        assertFalse { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should not complete if no album`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setYearGenreAndCoverArt()
        metadata.artist = ARTIST

        assertFalse { service.canLastFMHelpToComplete(metadata) }
    }

    @Test
    fun `should get metadata from lastFm`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()
        setImageLastFM()
        setYearAndGenreExpectations()

        val result = service.getLastFM(metadata)

        assertEquals(YEAR, result.year)
        assertEquals(GENRE, result.genre)
        assertEquals(image, result.imageIcon)
        assertEquals(albumLastFM, ApplicationState.lastFmCache[ALBUM])
    }

    @Test
    fun `should detect when nothing changed`(testInfo: TestInfo) {
        log.info(testInfo.displayName)

        val result = service.isSomethingNew(lastfmAlbum, metadata)

        assertEquals(ActionResult.READY, result)
    }

    @Test
    fun `should detect when year changed`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        `when`(lastfmAlbum.year).thenReturn(YEAR)

        val result = service.isSomethingNew(lastfmAlbum, metadata)

        assertEquals(ActionResult.NEW, result)
    }

    @Test
    fun `should detect when lastfm album has genre`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = ALBUM
        metadata.genre = GENRE

        val result = service.getLastFM(metadata)

        verify(lastFMAlbumHelper, never()).getGenre(albumLastFM)
        assertTrue(StringUtils.isEmpty(result.genre))
    }

    @Test
    fun `should detect when lastfm album has year`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        metadata.album = ALBUM
        metadata.year = YEAR

        val result = service.getLastFM(metadata)

        verify(albumLastFM, never()).releaseDate
        assertTrue(StringUtils.isEmpty(result.year))
    }

    @Test
    fun `should not complete from lastFM since it does not have info`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        setArtistAndAlbum()
        `when`(lastFMAlbumHelper.getAlbum(ARTIST, ALBUM)).thenReturn(null)

        service.getLastFM(metadata)

        assertNull(ApplicationState.lastFmCache[ALBUM])
    }

    private fun setArtistAndAlbum() {
        metadata.artist = ARTIST
        metadata.album = ALBUM
    }

    private fun setYearGenreAndCoverArt() {
        metadata.year = YEAR
        metadata.genre = GENRE
        metadata.coverArt = image
    }

    private fun setImageLastFM() {
        val imageURL = "http://userserve-ak.last.fm/serve/300x300/35560281.png"
        `when`(albumLastFM.getImageURL(isA())).thenReturn(imageURL)
        `when`(imageService.readImage(imageURL)).thenReturn(image)
    }

    private fun setYearAndGenreExpectations() {
        val date = Date()
        `when`(albumLastFM.releaseDate).thenReturn(date)
        `when`(lastFMAlbumHelper.getYear(any())).thenReturn(YEAR)
        `when`(lastFMAlbumHelper.getGenre(albumLastFM)).thenReturn(GENRE)
    }
}
