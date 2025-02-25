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
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.LastfmAlbum;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.LastfmServiceImpl;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LastfmServiceTest {

  private LastfmService lastfmService;

  @Mock private Metadata metadata;
  @Mock private Image imageIcon;
  @Mock private LastFMCompleteService completeService;
  @Mock private LastfmAlbum lastfmAlbum;

  private String genre = "Minimal Techno";

  @BeforeEach
  void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true);
    lastfmService = new LastfmServiceImpl(completeService);
  }

  @Test
  void shouldCompleteMetadataFromLastfm() throws Exception {
    setCompleteHelperExpectations();
    when(lastfmAlbum.getImageIcon()).thenReturn(imageIcon);
    when(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.NEW);

    ActionResult result = lastfmService.completeLastFM(metadata);

    verify(completeService).isSomethingNew(lastfmAlbum, metadata);
    assertEquals(ActionResult.NEW, result);
  }

  @Test
  void shouldNotCompleteLastfmCoverArtMetadataDueToMetadataComplete() throws Exception {
    when(completeService.canLastFMHelpToComplete(metadata)).thenReturn(false);

    ActionResult result = lastfmService.completeLastFM(metadata);
    assertEquals(ActionResult.READY, result);
  }

  @Test
  void shouldNotCompleteGenreIfAlredyHasOne() throws Exception {
    setCompleteHelperExpectations();
    when(metadata.getGenre()).thenReturn(genre);

    lastfmService.completeLastFM(metadata);

    verify(metadata, never()).setGenre(isA(String.class));
  }

  @Test
  void shouldReturnMetadataCompleteIfLastfmHasNotNewValues() throws Exception {
    setCompleteHelperExpectations();
    when(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.READY);

    ActionResult result = lastfmService.completeLastFM(metadata);
    assertEquals(ActionResult.READY, result);
  }

  @Test
  void shouldReturnSomethingnewValueIfNoBadFormatAndCapitalized() throws Exception {
    setCompleteHelperExpectations();
    when(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.NEW);

    ActionResult result = lastfmService.completeLastFM(metadata);

    assertEquals(ActionResult.NEW, result);
  }

  void setCompleteHelperExpectations() throws MalformedURLException, IOException {
    when(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true);
    when(completeService.getLastFM(metadata)).thenReturn(lastfmAlbum);
  }

  @Test
  void shouldCatchMalformedURLException() throws Exception {
    when(completeService.getLastFM(metadata)).thenThrow(new MalformedURLException());

    ActionResult result = lastfmService.completeLastFM(metadata);

    assertEquals(ActionResult.ERROR, result);
  }

  @Test
  void shouldCatchIOException() throws Exception {
    when(completeService.getLastFM(metadata)).thenThrow(new IOException());

    ActionResult result = lastfmService.completeLastFM(metadata);

    assertEquals(ActionResult.ERROR, result);
  }
}
