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

import java.awt.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LastfmAlbumTest {

  @Mock private Image imageIcon;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldCreateALastfmAlbum() throws Exception {
    final String year = "2011";
    final String genre = "Minimal Techno";
    LastfmAlbum lastfmAlbum = new LastfmAlbum();
    lastfmAlbum.setYear(year);
    lastfmAlbum.setGenre(genre);
    lastfmAlbum.setImageIcon(imageIcon);

    assertEquals(year, lastfmAlbum.getYear());
    assertEquals(genre, lastfmAlbum.getGenre());
    assertEquals(imageIcon, lastfmAlbum.getImageIcon());
  }
}
