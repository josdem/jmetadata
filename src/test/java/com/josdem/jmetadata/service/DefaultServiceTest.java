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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.DefaultServiceImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class DefaultServiceTest {

  private static final String TOTAL_TRACKS = "2";
  private static final String TRACK_NUMBER_METADATA_ONE = "1";
  private static final String CD_NUMBER = "1";
  private static final String TOTAL_CD_NUMBER = "1";

  private DefaultService defaultService;

  @Mock private Metadata metadata_one;
  @Mock private Metadata metadata_two;
  @Mock private MetadataService metadataService;

  private final List<Metadata> metadatas = new ArrayList<>();

  @BeforeEach
  void setup() throws Exception {
    MockitoAnnotations.openMocks(this);
    defaultService = new DefaultServiceImpl(metadataService);
  }

  @Test
  @DisplayName("validating we can complete metadata due to we do not have total tracks")
  void shouldCompleteTotalTracks(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    setTracksNumberExpectations();
    when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

    assertTrue(defaultService.isCompletable(metadatas));
  }

  @Test
  @DisplayName("validating we can complete metadata due to we do not have cd number")
  void shouldCompleteCdNumber(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    setTracksNumberExpectations();
    when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

    assertTrue(defaultService.isCompletable(metadatas));
  }

  @Test
  @DisplayName("validating we can complete metadata due to we do not have total cds")
  void shouldCompleteTotalCds(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
    setTracksNumberExpectations();
    when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

    assertTrue(defaultService.isCompletable(metadatas));
  }

  @Test
  @DisplayName("validating we can not complete metadata due to we have one track")
  void shouldNotCompleteIfSingleTrack(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var metadataList = new ArrayList<Metadata>();

    when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadataService.isSameAlbum(metadataList)).thenReturn(true);
    metadataList.add(metadata_one);

    defaultService.isCompletable(metadataList);

    verify(metadata_one, never()).setTotalTracks(TOTAL_TRACKS);
    verify(metadata_one, never()).setCdNumber(CD_NUMBER);
    verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
  }

  @Test
  @DisplayName("completing metadata")
  void shouldComplete(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(metadataService.isSameAlbum(metadatas)).thenReturn(true);
    setTracksNumberExpectations();

    defaultService.complete(metadatas);

    verify(metadata_one).setTotalTracks(TOTAL_TRACKS);
    verify(metadata_one).setCdNumber(CD_NUMBER);
    verify(metadata_one).setTotalCds(TOTAL_CD_NUMBER);
    verify(metadata_two).setTotalTracks(TOTAL_TRACKS);
    verify(metadata_two).setCdNumber(CD_NUMBER);
    verify(metadata_two).setTotalCds(TOTAL_CD_NUMBER);
  }

  @Test
  @DisplayName("validating we do not need to complete metadata")
  void shouldNotCompleteMetadataWhenNoNecessary(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
    when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
    when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
    when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

    assertFalse(defaultService.isCompletable(metadatas));
  }

  @Test
  @DisplayName("validating we cannot complete metadata due to not having track number")
  void shouldNotCompleteWhenNoTrackNumber(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata_one.getTrackNumber()).thenReturn(StringUtils.EMPTY);
    when(metadata_two.getTrackNumber()).thenReturn(StringUtils.EMPTY);
    metadatas.add(metadata_one);
    metadatas.add(metadata_two);

    defaultService.complete(metadatas);

    verify(metadata_one, never()).setCdNumber(CD_NUMBER);
    verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
    verify(metadata_two, never()).setCdNumber(CD_NUMBER);
    verify(metadata_two, never()).setTotalCds(TOTAL_CD_NUMBER);
  }

  private void setTracksNumberExpectations() {
    when(metadata_one.getTrackNumber()).thenReturn(TRACK_NUMBER_METADATA_ONE);
    when(metadata_two.getTrackNumber()).thenReturn(TOTAL_TRACKS);
    metadatas.add(metadata_one);
    metadatas.add(metadata_two);
  }
}
