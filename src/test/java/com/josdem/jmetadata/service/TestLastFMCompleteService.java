/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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

package com.josdem.jmetadata.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.josdem.jmetadata.helper.LastFMAlbumHelper;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceImpl;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class TestLastFMCompleteService {

  private LastFMCompleteService completeService;

  @Mock private LastFMAlbumHelper lastfmHelper;
  @Mock private ImageService imageService;
  @Mock private Album albumFromLastFM;
  @Mock private Image image;

  private final Metadata metadata = new Metadata();

  private final String year = "2011";
  private final String genre = "Minimal Techno";
  private final String artist = "Linas";
  private final String album = "Time Lapse";
  private final String imageURL = "http://userserve-ak.last.fm/serve/300x300/35560281.png";

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.openMocks(this);
    when(lastfmHelper.getAlbum(artist, album)).thenReturn(albumFromLastFM);
    completeService = new LastFMCompleteServiceImpl(imageService, lastfmHelper);
  }

  private void setArtistAndAlbumExpectations() {
    metadata.setArtist(artist);
    metadata.setAlbum(album);
  }

  @Test
  @DisplayName("completing metadata")
  void shouldCompleteIfNoMetadata(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  @DisplayName("completing metadata when no cover art")
  public void shouldCompleteIfNoCoverArt(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();
    metadata.setYear(year);
    metadata.setGenre(genre);

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  @DisplayName("completing metadata when no genre")
  void shouldCompleteIfNoGenre(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();
    metadata.setCoverArt(image);
    metadata.setYear(year);

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  @DisplayName("completing metadata when no year")
  void shouldCompleteIfNoYear(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();
    metadata.setCoverArt(image);
    metadata.setGenre(genre);

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  @DisplayName("not completing metadata")
  void shouldNotCompleteIfMetadataIsComplete(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();
    setYearGenreCoverExpectations();

    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

  private void setYearGenreCoverExpectations() {
    metadata.setCoverArt(image);
    metadata.setYear(year);
    metadata.setGenre(genre);
  }

  @Test
  @DisplayName("not completing metadata when no artist")
  void shouldNotCompleteIfNoArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setYearGenreCoverExpectations();
    metadata.setAlbum(album);

    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

  @Test
  @DisplayName("not completing metadata when no album")
  void shouldNotCompleteIfNoAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setYearGenreCoverExpectations();
    metadata.setArtist(artist);

    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

  @Test
  @DisplayName("getting lastfm metadata")
  void shouldGetLastfm(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();
    setImageExpectations();
    setYearAndGenreExpectations();
    completeService.canLastFMHelpToComplete(metadata);

    var lastFMalbum = completeService.getLastFM(metadata);

    assertEquals(year, lastFMalbum.getYear());
    assertEquals(genre, lastFMalbum.getGenre());
    assertEquals(image, lastFMalbum.getImageIcon());
  }

  private void setYearAndGenreExpectations() {
    var date = new Date();
    when(albumFromLastFM.getReleaseDate()).thenReturn(date);
    when(lastfmHelper.getYear(date)).thenReturn(year);
    when(lastfmHelper.getGenre(albumFromLastFM)).thenReturn(genre);
  }

  private void setImageExpectations() throws IOException {
    when(albumFromLastFM.getImageURL(ImageSize.EXTRALARGE)).thenReturn(imageURL);
    when(imageService.readImage(imageURL)).thenReturn(image);
  }

  /*

  @Test
  public void shouldDetectWhenNothingChanged() throws Exception {
    ActionResult actionResult = completeService.isSomethingNew(lastfmAlbum, metadata);
    assertEquals(ActionResult.Ready, actionResult);
  }

  @Test
  public void shouldDetectLastfmHasNewValues() throws Exception {
    when(lastfmAlbum.getYear()).thenReturn(year);
    ActionResult actionResult = completeService.isSomethingNew(lastfmAlbum, metadata);
    assertEquals(ActionResult.New, actionResult);
  }

  @Test
  public void shouldNotSetCoverArtIfAnyInFile() throws Exception {
    when(metadata.getCoverArt()).thenReturn(image);
    when(metadata.getAlbum()).thenReturn(album);
    when(cachedAlbums.get(album)).thenReturn(albumFromLastFM);
    setImageExpectations();

    LastfmAlbum lastFMalbum = completeService.getLastFM(metadata);

    assertNull(lastFMalbum.getImageIcon());
  }



  @Test
  public void shouldNotAskForGenreIfAlreadyHasOne() throws Exception {
    when(metadata.getAlbum()).thenReturn(album);
    when(cachedAlbums.get(album)).thenReturn(albumFromLastFM);
    when(metadata.getGenre()).thenReturn(genre);

    LastfmAlbum lastFMalbum = completeService.getLastFM(metadata);

    verify(lastfmHelper, never()).getGenre(albumFromLastFM);
    assertTrue(StringUtils.isEmpty(lastFMalbum.getGenre()));
  }

  @Test
  public void shouldNotAskForYearIfAlreadyHasOne() throws Exception {
    when(metadata.getYear()).thenReturn(year);

    LastfmAlbum lastFMalbum = completeService.getLastFM(metadata);

    verify(albumFromLastFM, never()).getReleaseDate();
    assertTrue(StringUtils.isEmpty(lastFMalbum.getYear()));
  }

  @Test
  public void shouldReturnACachedAlbum() throws Exception {
    setArtistAndAlbumExpectations();
    when(cachedAlbums.get(album)).thenReturn(albumFromLastFM);

    boolean result = completeService.canLastFMHelpToComplete(metadata);

    assertTrue(result);
  }

  @Test
  public void shouldReturnAlbumFromLastFM() throws Exception {
    setArtistAndAlbumExpectations();
    when(lastfmHelper.getAlbum(artist, album)).thenReturn(null);

    boolean result = completeService.canLastFMHelpToComplete(metadata);

    assertFalse(result);
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  public void shouldSetACachedAlbumInTheMap() throws Exception {
    setArtistAndAlbumExpectations();
    when(albumFromLastFM.getImageURL(ImageSize.EXTRALARGE)).thenReturn(imageURL);

    completeService.canLastFMHelpToComplete(metadata);

    verify(cachedAlbums).put(album, albumFromLastFM);
  }

  @Test
  public void shouldNotCompleteBecauseNotAlbumFound() throws Exception {
    completeService.getLastFM(metadata);

    verify(lastfmHelper, never()).getGenre(null);
  }

  @Test
  public void shouldCanNotCompleteLastFMDueToHasNoAlbumAndArtist() throws Exception {
    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

   */
}
