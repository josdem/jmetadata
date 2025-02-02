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

package com.josdem.jmetadata.collaborator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.model.Metadata;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class MetadataCollaboratorTest {

  @InjectMocks private final MetadataCollaborator metadataCollaborator = new MetadataCollaborator();

  private final List<Metadata> metadatas = new ArrayList<Metadata>();

  @Mock private Metadata metadataOne;
  @Mock private Metadata metadataTwo;

  private final String artist = "artist";
  private final String album = "album";
  private final String genre = "genre";
  private final String year = "year";
  private final String totalTracks = "totalTracks";
  private final String totalCds = "totalCds";
  private final String cdNumber = "cdNumber";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("getting artist")
  void shouldGetArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getArtist()).thenReturn(artist);
    when(metadataTwo.getArtist()).thenReturn(artist);

    assertEquals(artist, metadataCollaborator.getArtist());
  }

  private void setMetadatasExpectations() {
    metadatas.add(metadataOne);
    metadatas.add(metadataTwo);
    metadataCollaborator.setMetadatas(metadatas);
  }

  @Test
  @DisplayName("not getting artist")
  void shouldNotGetArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getArtist()).thenReturn(artist);
    when(metadataTwo.getArtist()).thenReturn("otherArtist");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getArtist());
  }

  @Test
  @DisplayName("not accepting null artist")
  void shouldGetEmptyArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getArtist()).thenReturn(null);
    when(metadataTwo.getArtist()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getArtist);
  }

  @Test
  @DisplayName("getting album")
  void shouldGetAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getAlbum()).thenReturn(album);
    when(metadataTwo.getAlbum()).thenReturn(album);

    assertEquals(album, metadataCollaborator.getAlbum());
  }

  @Test
  @DisplayName("not getting album")
  void shouldNotGetAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getAlbum()).thenReturn(album);
    when(metadataTwo.getAlbum()).thenReturn("otherAlbum");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getAlbum());
  }

  @Test
  @DisplayName("note accepting null album")
  void shouldGetEmptyAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getAlbum()).thenReturn(null);
    when(metadataTwo.getAlbum()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getAlbum);
  }

  @Test
  @DisplayName("getting genre")
  void shouldGetGenre(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getGenre()).thenReturn(genre);
    when(metadataTwo.getGenre()).thenReturn(genre);

    assertEquals(genre, metadataCollaborator.getGenre());
  }

  @Test
  @DisplayName("not getting genre")
  void shouldNotGetGenre(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getGenre()).thenReturn(genre);
    when(metadataTwo.getGenre()).thenReturn("otherGenre");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getGenre());
  }

  @Test
  @DisplayName("not accepting null genre")
  void shouldGetEmptyGenre(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getGenre()).thenReturn(null);
    when(metadataTwo.getGenre()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getGenre);
  }

  @Test
  @DisplayName("getting year")
  void shouldGetYear(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getYear()).thenReturn(year);
    when(metadataTwo.getYear()).thenReturn(year);

    assertEquals(year, metadataCollaborator.getYear());
  }

  @Test
  @DisplayName("not getting year")
  void shouldNotGetYear(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getYear()).thenReturn(year);
    when(metadataTwo.getYear()).thenReturn("otherYear");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getYear());
  }

  @Test
  @DisplayName("not accepting null year")
  void shouldGetEmptyYear(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getYear()).thenReturn(null);
    when(metadataTwo.getYear()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getYear);
    ;
  }

  @Test
  @DisplayName("getting total tracks")
  void shouldGetTotalTracks(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getTotalTracks()).thenReturn(totalTracks);
    when(metadataTwo.getTotalTracks()).thenReturn(totalTracks);

    assertEquals(totalTracks, metadataCollaborator.getTotalTracks());
  }

  @Test
  @DisplayName("not getting total tracks")
  void shouldNotGetTotalTracks(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getTotalTracks()).thenReturn(totalTracks);
    when(metadataTwo.getTotalTracks()).thenReturn("otherTotalTracks");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getTotalTracks());
  }

  @Test
  @DisplayName("not accepting null total tracks")
  void shouldGetEmptyTotalTracks(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getTotalTracks()).thenReturn(null);
    when(metadataTwo.getTotalTracks()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getTotalTracks);
  }

  @Test
  @DisplayName("getting cd number")
  void shouldGetCdNumber(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getCdNumber()).thenReturn(cdNumber);
    when(metadataTwo.getCdNumber()).thenReturn(cdNumber);

    assertEquals(cdNumber, metadataCollaborator.getCdNumber());
  }

  @Test
  @DisplayName("not getting cd number")
  void shouldNotGetCdNumber(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getCdNumber()).thenReturn(cdNumber);
    when(metadataTwo.getCdNumber()).thenReturn("otherCdNumber");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getCdNumber());
  }

  @Test
  @DisplayName("not accepting null cd number")
  void shouldGetEmptyCdNumber(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getCdNumber()).thenReturn(null);
    when(metadataTwo.getCdNumber()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getCdNumber);
  }

  @Test
  @DisplayName("getting total cds")
  void shouldGetTotalCds(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getTotalCds()).thenReturn(totalCds);
    when(metadataTwo.getTotalCds()).thenReturn(totalCds);

    assertEquals(totalCds, metadataCollaborator.getTotalCds());
  }

  @Test
  @DisplayName("not getting total cds")
  void shouldNotGetTotalCds(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getTotalCds()).thenReturn(totalCds);
    when(metadataTwo.getTotalCds()).thenReturn("otherCds");

    assertEquals(StringUtils.EMPTY, metadataCollaborator.getTotalCds());
  }

  @Test
  @DisplayName("not accepting null total cds")
  void shouldGetEmptyTotalCds(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    setMetadatasExpectations();
    when(metadataOne.getTotalCds()).thenReturn(null);
    when(metadataTwo.getTotalCds()).thenReturn(null);

    assertThrows(NullPointerException.class, metadataCollaborator::getTotalCds);
  }
}
