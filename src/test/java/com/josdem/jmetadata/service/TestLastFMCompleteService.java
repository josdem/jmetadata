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

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.helper.LastFMAlbumHelper;
import com.josdem.jmetadata.model.LastfmAlbum;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceImpl;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestLastFMCompleteService {

  @InjectMocks private LastFMCompleteService completeService = new LastFMCompleteServiceImpl();
  @Mock private Metadata metadata;
  @Mock private LastfmAlbum lastfmAlbum;
  @Mock private LastFMAlbumHelper lastfmHelper;
  @Mock private ImageService imageService;
  @Mock private Album albumFromLastFM;
  @Mock private Image image;
  @Mock private HashMap<String, Album> cachedAlbums;

  private final String year = "2011";
  private final String genre = "Minimal Techno";
  private final String artist = "Linas";
  private final String album = "Time Lapse";
  private final String imageURL = "http://userserve-ak.last.fm/serve/300x300/35560281.png";

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(lastfmHelper.getAlbum(artist, album)).thenReturn(albumFromLastFM);
  }

  private void setArtistAndAlbumExpectations() {
    when(metadata.getArtist()).thenReturn(artist);
    when(metadata.getAlbum()).thenReturn(album);
  }

  @Test
  public void shouldCompleteIfNoMetadata() throws Exception {
    setArtistAndAlbumExpectations();

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  public void shouldCompleteIfNoCoverArt() throws Exception {
    setArtistAndAlbumExpectations();
    when(metadata.getYear()).thenReturn(year);
    when(metadata.getGenre()).thenReturn(genre);

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  public void shouldCompleteIfNoGenre() throws Exception {
    setArtistAndAlbumExpectations();
    when(metadata.getCoverArt()).thenReturn(image);
    when(metadata.getYear()).thenReturn(year);

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  @Test
  public void shouldCompleteIfNoYear() throws Exception {
    setArtistAndAlbumExpectations();
    when(metadata.getCoverArt()).thenReturn(image);
    when(metadata.getGenre()).thenReturn(genre);

    assertTrue(completeService.canLastFMHelpToComplete(metadata));
    verify(lastfmHelper).getAlbum(artist, album);
  }

  private void setYearGenreCoverExpectations() {
    when(metadata.getCoverArt()).thenReturn(image);
    when(metadata.getYear()).thenReturn(year);
    when(metadata.getGenre()).thenReturn(genre);
  }

  @Test
  public void shouldNotCompleteIfMetadataIsComplete() throws Exception {
    setArtistAndAlbumExpectations();
    setYearGenreCoverExpectations();

    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

  @Test
  public void shouldNotCompleteIfNoArtist() throws Exception {
    setYearGenreCoverExpectations();
    when(metadata.getAlbum()).thenReturn(album);

    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

  @Test
  public void shouldNotCompleteIfNoAlbum() throws Exception {
    when(metadata.getArtist()).thenReturn(artist);
    setYearGenreCoverExpectations();

    assertFalse(completeService.canLastFMHelpToComplete(metadata));
  }

  @Test
  public void shouldGetLastfm() throws Exception {
    setYearAndGenreExpectations();
    setImageExpectations();

    LastfmAlbum lastFMalbum = completeService.getLastFM(metadata);

    assertEquals(year, lastFMalbum.getYear());
    assertEquals(genre, lastFMalbum.getGenre());
    assertEquals(image, lastFMalbum.getImageIcon());
  }

  @Test
  public void shouldNotGetGenreFromCache() throws Exception {
    setGenreExpectations();
    when(cachedAlbums.get(album)).thenReturn(albumFromLastFM);

    LastfmAlbum lastfmAlbum = completeService.getLastFM(metadata);

    verify(lastfmHelper).getGenre(albumFromLastFM);
    assertEquals(genre, lastfmAlbum.getGenre());
  }

  private void setGenreExpectations() {
    Date date = new Date();
    when(metadata.getAlbum()).thenReturn(album);
    when(albumFromLastFM.getReleaseDate()).thenReturn(date);
    when(lastfmHelper.getYear(date)).thenReturn(year);
    when(lastfmHelper.getGenre(albumFromLastFM)).thenReturn(genre);
  }

  @Test
  public void shouldGetLastfmEvenThoughThereisNoCoverArt() throws Exception {
    setYearAndGenreExpectations();

    LastfmAlbum lastFMalbum = completeService.getLastFM(metadata);

    assertEquals(year, lastFMalbum.getYear());
    assertEquals(genre, lastFMalbum.getGenre());
    assertNull(lastFMalbum.getImageIcon());
  }

  private void setYearAndGenreExpectations() {
    Date date = new Date();
    when(metadata.getAlbum()).thenReturn(album);
    when(cachedAlbums.get(album)).thenReturn(albumFromLastFM);
    when(albumFromLastFM.getReleaseDate()).thenReturn(date);
    when(lastfmHelper.getYear(date)).thenReturn(year);
    when(lastfmHelper.getGenre(albumFromLastFM)).thenReturn(genre);
  }

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

  private void setImageExpectations() throws MalformedURLException, IOException {
    when(albumFromLastFM.getImageURL(ImageSize.EXTRALARGE)).thenReturn(imageURL);
    when(imageService.readImage(imageURL)).thenReturn(image);
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
}
