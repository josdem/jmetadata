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
