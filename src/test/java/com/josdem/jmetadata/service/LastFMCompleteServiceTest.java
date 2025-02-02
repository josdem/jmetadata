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

package com.josdem.jmetadata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.helper.LastFMAlbumHelper;
import com.josdem.jmetadata.model.LastfmAlbum;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceImpl;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;
import java.awt.Image;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class LastFMCompleteServiceTest {

  private LastFMCompleteService completeService;

  @Mock private LastfmAlbum lastfmAlbum;
  @Mock private LastFMAlbumHelper lastfmHelper;
  @Mock private ImageService imageService;
  @Mock private Album albumFromLastFM;
  @Mock private Image image;

  private final Metadata metadata = new Metadata();

  private final String year = "2011";
  private final String genre = "Minimal Techno";
  private final String artist = "Linas";
  private final String album = "Time Lapse";

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
  void shouldCompleteIfNoCoverArt(TestInfo testInfo) {
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
    var imageURL = "http://userserve-ak.last.fm/serve/300x300/35560281.png";
    when(albumFromLastFM.getImageURL(ImageSize.EXTRALARGE)).thenReturn(imageURL);
    when(imageService.readImage(imageURL)).thenReturn(image);
  }

  @Test
  @DisplayName("detecting when nothing changed")
  public void shouldDetectWhenNothingChanged(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    var actionResult = completeService.isSomethingNew(lastfmAlbum, metadata);
    assertEquals(ActionResult.Ready, actionResult);
  }

  @Test
  @DisplayName("detecting when lastfm has new values")
  void shouldDetectLastfmHasNewValues(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(lastfmAlbum.getYear()).thenReturn(year);

    var actionResult = completeService.isSomethingNew(lastfmAlbum, metadata);
    assertEquals(ActionResult.New, actionResult);
  }

  @Test
  @DisplayName("detecting when lastfm has cover art")
  void shouldNotSetCoverArtIfAnyInFile(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    metadata.setCoverArt(image);
    metadata.setAlbum(album);
    setImageExpectations();

    var lastFMalbum = completeService.getLastFM(metadata);

    assertNull(lastFMalbum.getImageIcon());
  }

  @Test
  @DisplayName("detecting when lastfm has genre")
  void shouldNotAskForGenreIfAlreadyHasOne(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    metadata.setAlbum(album);
    metadata.setGenre(genre);

    var lastFMalbum = completeService.getLastFM(metadata);

    verify(lastfmHelper, never()).getGenre(albumFromLastFM);
    assertTrue(StringUtils.isEmpty(lastFMalbum.getGenre()));
  }

  @Test
  @DisplayName("detecting when lastfm has year")
  void shouldNotAskForYearIfAlreadyHasOne(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    metadata.setYear(year);

    var lastFMalbum = completeService.getLastFM(metadata);

    verify(albumFromLastFM, never()).getReleaseDate();
    assertTrue(StringUtils.isEmpty(lastFMalbum.getYear()));
  }

  @Test
  @DisplayName("not complete from lastFM since it does not have info")
  void shouldNotCompleteFromLastFM(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setArtistAndAlbumExpectations();
    when(lastfmHelper.getAlbum(artist, album)).thenReturn(null);

    assertFalse(completeService.canLastFMHelpToComplete(metadata));

    verify(lastfmHelper).getAlbum(artist, album);
  }
}
