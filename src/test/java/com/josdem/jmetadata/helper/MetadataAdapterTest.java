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

import static org.mockito.Mockito.verify;

import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.model.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMetadataAdapter {
  private final MetadataAdapter adapter = new MetadataAdapter();

  @Mock private Metadata metadata;
  private static final String ARTIST = "Daniel Kandi";
  private static final String TITLE = "Make Me Believe";
  private static final String ALBUM = "Anjunabeats 5";
  private static final String TRACK_NUMBER = "5";
  private static final String TOTAL_TRACKS = "13";
  private static final String GENRE_COLUMN = "Minimal Techno";
  private static final String YEAR_COLUMN = "2001";
  private static final String CD_NUMBER = "1";

  private static final String TOTAL_CDS_NUMBER = null;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldUpdateArtist() throws Exception {
    adapter.update(metadata, ApplicationConstants.ARTIST_COLUMN, ARTIST);

    verify(metadata).setArtist(ARTIST);
  }

  @Test
  public void shouldUpdateTitle() throws Exception {
    adapter.update(metadata, ApplicationConstants.TITLE_COLUMN, TITLE);

    verify(metadata).setTitle(TITLE);
  }

  @Test
  public void shouldUpdateAlbum() throws Exception {
    adapter.update(metadata, ApplicationConstants.ALBUM_COLUMN, ALBUM);

    verify(metadata).setAlbum(ALBUM);
  }

  @Test
  public void shouldUpdateTrackNumber() throws Exception {
    adapter.update(metadata, ApplicationConstants.TRACK_NUMBER_COLUMN, TRACK_NUMBER);

    verify(metadata).setTrackNumber(TRACK_NUMBER);
  }

  @Test
  public void shouldUpdateTotalTracksNumber() throws Exception {
    adapter.update(metadata, ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN, TOTAL_TRACKS);

    verify(metadata).setTotalTracks(TOTAL_TRACKS);
  }

  @Test
  public void shouldUpdateGenre() throws Exception {
    adapter.update(metadata, ApplicationConstants.GENRE_COLUMN, GENRE_COLUMN);

    verify(metadata).setGenre(GENRE_COLUMN);
  }

  @Test
  public void shouldUpdateYear() throws Exception {
    adapter.update(metadata, ApplicationConstants.YEAR_COLUMN, YEAR_COLUMN);

    verify(metadata).setYear(YEAR_COLUMN);
  }

  @Test
  public void shouldUpdateCdNumber() throws Exception {
    adapter.update(metadata, ApplicationConstants.CD_NUMBER_COLUMN, CD_NUMBER);

    verify(metadata).setCdNumber(CD_NUMBER);
  }

  @Test
  public void shouldUpdateTotalCds() throws Exception {
    adapter.update(metadata, ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN, TOTAL_CDS_NUMBER);

    verify(metadata).setTotalCds(TOTAL_CDS_NUMBER);
  }
}
