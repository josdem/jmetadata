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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.MusicBrainzCompleteServiceAdapter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class MusicBrainzCompleteServiceAdapterTest {

  private MusicBrainzCompleteServiceAdapter musicBrainzCompleteServiceAdapter;

  @Mock private MusicBrainzService musicBrainzService;
  @Mock private MetadataService metadataService;

  private final Metadata metadata = new Metadata();

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    musicBrainzCompleteServiceAdapter =
        new MusicBrainzCompleteServiceAdapter(musicBrainzService, metadataService);
  }

  @Test
  @DisplayName("detecting I can complete metadata due to same album")
  void shouldDetectIfCanCompleteMetadataDueToSameAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadataService.isSameAlbum(List.of(metadata))).thenReturn(true);
    assertTrue(musicBrainzCompleteServiceAdapter.canComplete(List.of(metadata)));
  }

  @Test
  @DisplayName("detecting I can complete metadata due to same artist")
  void shouldDetectIfCanCompleteMetadataDueToSameArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadataService.isSameArtist(List.of(metadata))).thenReturn(true);
    assertTrue(musicBrainzCompleteServiceAdapter.canComplete(List.of(metadata)));
  }

  @Test
  @DisplayName("detecting I cannot complete metadata")
  void shouldDetectIfCannotCompleteMetadata(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadataService.isSameAlbum(List.of(metadata))).thenReturn(false);
    when(metadataService.isSameArtist(List.of(metadata))).thenReturn(false);
    assertFalse(musicBrainzCompleteServiceAdapter.canComplete(List.of(metadata)));
  }
}
