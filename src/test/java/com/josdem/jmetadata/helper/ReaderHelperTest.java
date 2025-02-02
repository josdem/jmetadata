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

package com.josdem.jmetadata.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class ReaderHelperTest {

  @InjectMocks private ReaderHelper readerHelper = new ReaderHelper();

  @Mock private Tag tag;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("getting genre")
  public void shouldGetGenre(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    var genre = "Minimal Techno";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
    assertEquals(genre, readerHelper.getGenre(tag, genre));
  }

  @Test
  @DisplayName("getting genre by code")
  public void shouldGetGenreByCodeWithParentheses(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    var genreAsCode = "(18)";
    var genre = "Techno";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genreAsCode);
    assertEquals(genre, readerHelper.getGenre(tag, genreAsCode));
  }

  @Test
  @DisplayName("getting genre when not valid number")
  public void shouldKnowWhenMp3IsNotANumberInsideParenthesis(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    var genre = "(None)";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
    assertEquals(genre, readerHelper.getGenre(tag, genre));
  }
}
