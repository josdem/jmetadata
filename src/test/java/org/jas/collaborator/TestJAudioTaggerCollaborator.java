/*
   Copyright 2014 Jose Morales contact@josdem.io

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

package org.jas.collaborator;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestJAudioTaggerCollaborator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    private final JAudioTaggerCollaborator jAudioTaggerCollaborator = new JAudioTaggerCollaborator();

    @Mock
    private Tag tag;
    @Mock
    private AudioHeader header;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("validating tag and header")
    public void shouldValidateTagAndHeader(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        assertTrue(jAudioTaggerCollaborator.isValid(tag, header));
    }

    @Test
    @DisplayName("detecting invalid tag")
    public void shouldDetectInvalidTag(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        assertFalse(jAudioTaggerCollaborator.isValid(null, header));
    }

    @Test
    @DisplayName("detecting invalid header")
    public void shouldDetectInvalidHeader(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        assertFalse(jAudioTaggerCollaborator.isValid(tag, null));
    }

}
