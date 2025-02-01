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

package com.josdem.jmetadata.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.MockitoAnnotations;

class TestMetadata {

  private static final Log log = LogFactory.getLog(TestMetadata.class);
  private final Metadata metadata = new Metadata();

  private static final String trackNumber = "2";
  private static final boolean metadataFromFile = true;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("comparing with previous metadata")
  public void shouldDetectPreviousMetadata(TestInfo testInfo) {
    log.info("Running test: " + testInfo.getDisplayName());
    Metadata previousMetadata = new Metadata();
    previousMetadata.setTrackNumber("1");

    metadata.setTrackNumber(trackNumber);
    assertEquals(1, metadata.compareTo(previousMetadata));
  }

  @Test
  @DisplayName("comparing with next metadata")
  public void shouldDetectNextMetadata(TestInfo testInfo) {
    log.info("Running test: " + testInfo.getDisplayName());
    Metadata nextMetadata = new Metadata();
    nextMetadata.setTrackNumber("3");

    metadata.setTrackNumber(trackNumber);
    assertEquals(-1, metadata.compareTo(nextMetadata));
  }

  @Test
  @DisplayName("detecting track number bad format")
  public void shouldRespondAtErrorInTrackNumber(TestInfo testInfo) {
    log.info("Running test: " + testInfo.getDisplayName());
    Metadata weirdMetadata = new Metadata();
    weirdMetadata.setTrackNumber("somethingWrongFormat");

    metadata.setTrackNumber(trackNumber);
    assertEquals(0, metadata.compareTo(weirdMetadata));
  }

  @Test
  @DisplayName("detecting metadata from file")
  public void shouldKnowIfMetadataIsFromFile(TestInfo testInfo) {
    log.info("Running test: " + testInfo.getDisplayName());
    metadata.setMetadataFromFile(metadataFromFile);
    assertEquals(metadataFromFile, metadata.isMetadataFromFile());
  }
}
