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

class CoverArtTest {

  @Mock private Image imageIcon;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldCreateAnCoverArt() throws Exception {
    CoverArt coverArt = new CoverArt(imageIcon, CoverArtType.DRAG_AND_DROP);
    assertEquals(imageIcon, coverArt.getImage());
    assertEquals(CoverArtType.DRAG_AND_DROP, coverArt.getType());
  }
}
