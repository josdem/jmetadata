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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.CoverArtImage;
import com.josdem.jmetadata.model.CoverArtResponse;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MusicBrainzResponse;
import com.josdem.jmetadata.model.Release;
import com.josdem.jmetadata.model.Thumbnail;
import com.josdem.jmetadata.service.impl.MusicBrainzServiceImpl;
import com.josdem.jmetadata.util.ApplicationState;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
public class MusicBrainzServiceTest {

  private static final String ALBUM_NAME = "Night Life";
  private static final String ALBUM_ID = "b04558a9-b69c-45bd-a6f4-d65706067780";
  private static final String COVER_ART_URL =
      "http://coverartarchive.org/release/7f3e3b3b-0b0b-4b3b-8b3b-4f3b3b3b3b3b/1234567890.jpg";

  @InjectMocks private MusicBrainzService musicBrainzService = new MusicBrainzServiceImpl();

  @Mock private RestService restService;

  @Mock private Call<Album> call;

  @Mock private ImageService imageService;

  @Mock private Image image;

  private final Metadata metadata = new Metadata();
  private final Album album = new Album();

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    MusicBrainzResponse musicBrainzResponse = getExpectedResponse();
    ApplicationState.cache.put("Night Life", musicBrainzResponse);
  }

  @Test
  @DisplayName("getting release id by name")
  void shouldGetReleaseIdByName(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var expectedAlbum = new Album();
    expectedAlbum.setId(ALBUM_ID);
    when(call.execute()).thenReturn(Response.success(expectedAlbum));
    when(restService.getRelease(ALBUM_ID)).thenReturn(call);

    var result = musicBrainzService.getAlbumByName(ALBUM_NAME);
    assertEquals(ALBUM_ID, result.getId());
  }

  @Test
  @DisplayName("not getting release id by name due to exception")
  void shouldNotGetReleaseIdByNameDueToException(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(call.execute()).thenThrow(new IOException("Error"));
    when(restService.getRelease(ALBUM_ID)).thenReturn(call);

    assertThrows(BusinessException.class, () -> musicBrainzService.getAlbumByName(ALBUM_NAME));
  }

  private MusicBrainzResponse getExpectedResponse() {
    var musicBrainzResponse = new MusicBrainzResponse();
    Release release = new Release();
    release.setId(ALBUM_ID);
    var releases = List.of(release);
    musicBrainzResponse.setReleases(releases);
    return musicBrainzResponse;
  }

  @DisplayName("completing year from album")
  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", " "})
  void shouldCompleteYearFromAlbum(String metadataYear) {
    setMetadataExpectations();
    metadata.setYear(metadataYear);
    var metadataList = List.of(metadata);
    album.setDate("1999-03-29");

    var result = musicBrainzService.completeYear(metadataList, album);

    assertEquals("1999", result.getFirst().getYear());
    assertEquals(1, result.size());
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", " "})
  @DisplayName("not completing year if not valid format")
  void shouldNotCompleteYearIfNotValidFormat(String date) {
    setMetadataExpectations();
    metadata.setYear(StringUtils.EMPTY);
    var metadataList = List.of(metadata);
    album.setDate(date);

    assertThrows(
        BusinessException.class, () -> musicBrainzService.completeYear(metadataList, album));
  }

  private void setMetadataExpectations() {
    metadata.setAlbum("Nightlife");
    metadata.setArtist("Pet shop boys");
  }

  @Test
  @DisplayName("not completing cover art since response is empty")
  void shouldNotCompleteCoverArtSinceResponseIsEmpty(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    var coverArtResponse = new CoverArtResponse();
    coverArtResponse.setImages(List.of());
    setMetadataExpectations();
    metadata.setCoverArt(null);
    var metadataList = List.of(metadata);
    assertThrows(
        BusinessException.class,
        () -> musicBrainzService.completeCoverArt(metadataList, coverArtResponse));
  }

  @Test
  @DisplayName("completing cover art")
  void shouldCompleteCoverArt(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());

    var coverArtResponse = setCoverArtExpectations();
    setMetadataExpectations();
    metadata.setCoverArt(null);
    var metadataList = List.of(metadata);
    when(imageService.readImage(COVER_ART_URL)).thenReturn(image);

    var result = musicBrainzService.completeCoverArt(metadataList, coverArtResponse);

    assertEquals(image, result.getFirst().getCoverArt());
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("not completing cover art since error reading image")
  void shouldNotCompleteCoverArtSinceErrorReadingImage(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());

    var coverArtResponse = setCoverArtExpectations();
    setMetadataExpectations();
    metadata.setCoverArt(null);
    var metadataList = List.of(metadata);
    when(imageService.readImage(COVER_ART_URL)).thenThrow(new IOException("Error"));

    assertThrows(
        BusinessException.class,
        () -> musicBrainzService.completeCoverArt(metadataList, coverArtResponse));
  }

  @Test
  @DisplayName("not completing cover art since it already exists")
  void shouldNotCompleteCoverArtSinceItAlreadyExists(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());

    var existingImage = mock(Image.class);
    var coverArtResponse = setCoverArtExpectations();
    setMetadataExpectations();
    metadata.setCoverArt(existingImage);
    var metadataList = List.of(metadata);

    var result = musicBrainzService.completeCoverArt(metadataList, coverArtResponse);

    assertEquals(existingImage, result.getFirst().getCoverArt());
    assertEquals(1, result.size());
  }

  @NotNull
  private static CoverArtResponse setCoverArtExpectations() {
    var coverArtResponse = new CoverArtResponse();
    var coverArtImage = new CoverArtImage();
    var thumbnails = new Thumbnail();
    thumbnails.setLarge(COVER_ART_URL);
    coverArtImage.setThumbnails(thumbnails);
    coverArtResponse.setImages(List.of(coverArtImage));
    return coverArtResponse;
  }
}
