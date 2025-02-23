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

package com.josdem.jmetadata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.metadata.MetadataWriter;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.LastfmService;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceAdapter;
import com.josdem.jmetadata.service.impl.MusicBrainzCompleteServiceAdapter;
import java.awt.Image;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class CompleteControllerTest {
  private static final String ERROR = "Error";

  private CompleteController controller;

  @Mock private MetadataWriter metadataWriter;
  @Mock private Metadata metadata;
  @Mock private File file;
  @Mock private LastfmService coverArtService;
  @Mock private MusicBrainzService musicBrainzService;
  @Mock private CoverArt coverArt;
  @Mock private Image imageIcon;
  @Mock private LastFMCompleteServiceAdapter lastFMCompleteServiceAdapter;
  @Mock private MusicBrainzCompleteServiceAdapter musicBrainzCompleteServiceAdapter;

  private String artist = "Dave Deen";
  private String title = "Footprints (Original Mix)";
  private String album = "Footprints EP";
  private String genre = "Trance";
  private String trackNumber = "10";
  private String totalTracks = "25";
  private String year = "1990";
  private String cdNumber = "1";
  private String totalCds = "2";

  @BeforeEach
  void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(metadata.getArtist()).thenReturn(artist);
    when(metadata.getTitle()).thenReturn(title);
    when(metadata.getAlbum()).thenReturn(album);
    when(metadata.getTrackNumber()).thenReturn(trackNumber);
    when(metadata.getTotalTracks()).thenReturn(totalTracks);
    when(metadata.getCdNumber()).thenReturn(cdNumber);
    when(metadata.getTotalCds()).thenReturn(totalCds);
    when(metadata.getYear()).thenReturn(year);
    when(metadata.getGenre()).thenReturn(genre);
    when(coverArtService.completeLastFM(metadata)).thenReturn(ActionResult.Ready);
    controller =
        new CompleteController(
            metadataWriter,
            coverArtService,
            musicBrainzService,
            musicBrainzCompleteServiceAdapter,
            lastFMCompleteServiceAdapter);
  }

  @Test
  public void shouldCompleteAlbumInMetadata() throws Exception {
    when(metadata.getFile()).thenReturn(file);

    ActionResult result = controller.completeAlbum(metadata);

    verify(metadataWriter).setFile(file);
    verify(metadataWriter).writeArtist(artist);
    verify(metadataWriter).writeTitle(title);
    verify(metadataWriter).writeAlbum(album);
    verify(metadataWriter).writeTrackNumber(trackNumber.toString());
    verify(metadataWriter).writeTotalTracksNumber(totalTracks.toString());
    verify(metadataWriter).writeCdNumber(cdNumber);
    verify(metadataWriter).writeTotalCds(totalCds);
    verify(metadataWriter).writeYear(year);
    verify(metadataWriter).writeGenre(genre);
    assertEquals(ActionResult.Updated, result);
  }

  @Test
  public void shouldRemoveCoverArt() throws Exception {
    when(metadata.getNewCoverArt()).thenReturn(coverArt);
    when(coverArt.getImage()).thenReturn(imageIcon);

    ActionResult result = controller.completeAlbum(metadata);

    verify(metadataWriter).removeCoverArt();
    verify(metadataWriter).writeCoverArt(imageIcon);
    verify(metadata).setCoverArt(imageIcon);
    assertEquals(ActionResult.Updated, result);
  }

  @Test
  @DisplayName("not completing metadata")
  void shouldNotUpdateMetadata(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getFile()).thenReturn(file);
    when(metadataWriter.writeAlbum(album)).thenThrow(new BusinessException(ERROR));

    var result = controller.completeAlbum(metadata);

    assertEquals(ActionResult.Error, result);
  }

  @Test
  @DisplayName("completing metadata with LastFM service")
  void shouldCompleteMetadata() {
    var metadataList = List.of(metadata);
    when(lastFMCompleteServiceAdapter.canComplete(metadataList)).thenReturn(true);
    controller.completeLastFmMetadata(metadataList);
    verify(coverArtService).completeLastFM(metadata);
  }

  @Test
  @DisplayName("not completing metadata with LastFM service")
  void shouldNotCompleteMetadata() {
    var metadataList = List.of(metadata);
    when(lastFMCompleteServiceAdapter.canComplete(metadataList)).thenReturn(false);
    controller.completeLastFmMetadata(metadataList);
    verify(coverArtService, never()).completeLastFM(metadata);
  }
}
