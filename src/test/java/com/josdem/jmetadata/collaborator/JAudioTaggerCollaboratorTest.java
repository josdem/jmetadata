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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.Tag;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JAudioTaggerCollaboratorTest {

  @InjectMocks
  private final JAudioTaggerCollaborator jAudioTaggerCollaborator = new JAudioTaggerCollaborator();

  @Mock private Tag tag;
  @Mock private AudioHeader header;

  @BeforeEach
  void setup() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("validating tag and header")
  void shouldValidateTagAndHeader(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    assertTrue(jAudioTaggerCollaborator.isValid(tag, header));
  }

  @ParameterizedTest
  @MethodSource("metadata")
  @DisplayName("detecting invalid metadata")
  void shouldDetectInvalidTag(Tag tag, AudioHeader header) {
    log.info("Running detecting invalid metadata");
    assertFalse(jAudioTaggerCollaborator.isValid(tag, header));
  }

  private Stream<Arguments> metadata() {
    return Stream.of(Arguments.of(tag, null), Arguments.of(null, header));
  }
}
