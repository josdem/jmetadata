/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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

package com.josdem.jmetadata.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import com.josdem.jmetadata.collaborator.JAudioTaggerCollaborator;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.helper.AudioFileHelper;
import com.josdem.jmetadata.helper.ReaderHelper;
import com.josdem.jmetadata.model.Metadata;
import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMp3Reader {
  private static final String ARTIST = "Armin Van Buuren";
  private static final String TITLE = "Control Freak (Sander Van Doorn Remix)";
  private static final String YEAR = "2011";
  private static final String NULL = "null";

  @InjectMocks private Mp3Reader reader = new Mp3Reader();

  @Mock private MP3File audioFile;
  @Mock private File file;
  @Mock private Tag tag;
  @Mock private Artwork artwork;
  @Mock private AudioHeader header;
  @Mock private AudioFileHelper audioFileHelper;
  @Mock private BufferedImage bufferedImage;
  @Mock private ControlEngineConfigurator configurator;
  @Mock private ControlEngine controlEngine;
  @Mock private ReaderHelper readerHelper;
  @Mock private JAudioTaggerCollaborator jAudioTaggerCollaborator;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(audioFileHelper.read(file)).thenReturn(audioFile);
    when(audioFile.getTag()).thenReturn(tag);
    when(audioFile.getAudioHeader()).thenReturn(header);
    when(artwork.getImage()).thenReturn(bufferedImage);
    when(tag.getFirstArtwork()).thenReturn(artwork);
    when(audioFile.hasID3v2Tag()).thenReturn(true);
    when(header.getBitRate()).thenReturn("64");
    when(configurator.getControlEngine()).thenReturn(controlEngine);
    when(jAudioTaggerCollaborator.isValid(tag, header)).thenReturn(true);
  }

  @Test
  public void shouldUpdateID3toV2() throws Exception {
    when(audioFile.hasID3v2Tag()).thenReturn(false);
    reader.getMetadata(file);

    ((MP3File) verify(audioFile)).setID3v2TagOnly((AbstractID3v2Tag) anyObject());
    ((MP3File) verify(audioFile)).commit();
  }

  @Test
  public void shouldGetMetadata() throws Exception {
    when(audioFile.hasID3v2Tag()).thenReturn(true);
    reader.getMetadata(file);

    ((MP3File) verify(audioFile)).getTag();
    ((MP3File) verify(audioFile)).getAudioHeader();
  }

  @Test
  public void shouldGetArtist() throws Exception {
    when(tag.getFirst(FieldKey.ARTIST)).thenReturn(ARTIST);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(ARTIST, metadata.getArtist());
  }

  @Test
  public void shouldGetTitle() throws Exception {
    when(tag.getFirst(FieldKey.TITLE)).thenReturn(TITLE);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(TITLE, metadata.getTitle());
  }

  @Test
  public void shouldGetAlbum() throws Exception {
    String album = "Nobody Seems To Care / Murder Weapon";
    when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(album, metadata.getAlbum());
  }

  @Test
  public void shouldGetGenre() throws Exception {
    String genre = "Minimal Techno";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
    when(readerHelper.getGenre(tag, genre)).thenReturn(genre);

    Metadata metadata = reader.getMetadata(file);

    assertEquals(genre, metadata.getGenre());
  }

  @Test
  public void shouldGetGenreByCode() throws Exception {
    String genreAsCode = "31";
    String genre = "Trance";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genreAsCode);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(genre, metadata.getGenre());
  }

  @Test
  public void shouldGetTrackNumber() throws Exception {
    String trackNumber = "11";
    when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(trackNumber, metadata.getTrackNumber());
  }

  @Test
  public void shouldGetTotalTracks() throws Exception {
    String totalTracks = "20";
    when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(totalTracks);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(totalTracks, metadata.getTotalTracks());
  }

  @Test
  public void shouldReturnZEROInTrackNumberWhenTagIsNull() throws Exception {
    when(tag.getFirst(FieldKey.TRACK)).thenReturn(NULL);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getTrackNumber());
  }

  @Test
  public void shouldReturnZEROInTotalTracksWhenTagIsNull() throws Exception {
    when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(NULL);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getTotalTracks());
  }

  @Test
  public void shouldReturnZEROInTrackNumberWhenNullPointer() throws Exception {
    when(tag.getFirst(FieldKey.TRACK)).thenThrow(new NullPointerException());
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getTrackNumber());
  }

  @Test
  public void shouldReturnZEROInTotalTracksWhenNullPointer() throws Exception {
    when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenThrow(new NullPointerException());
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getTotalTracks());
  }

  @Test
  public void shouldReturnZEROInGettingCdNumberWhenNullPointer() throws Exception {
    when(tag.getFirst(FieldKey.DISC_NO)).thenThrow(new NullPointerException());
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getCdNumber());
  }

  @Test
  public void shouldReturnZEROInGettingTotalCdsWhenNullPointer() throws Exception {
    when(tag.getFirst(FieldKey.DISC_TOTAL)).thenThrow(new NullPointerException());
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getTotalCds());
  }

  @Test
  public void shouldReturnZEROInCdNumberWhenTagIsNull() throws Exception {
    when(tag.getFirst(FieldKey.DISC_NO)).thenReturn(NULL);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getCdNumber());
  }

  @Test
  public void shouldReturnZEROInTotalCdsWhenTagIsNull() throws Exception {
    when(tag.getFirst(FieldKey.DISC_TOTAL)).thenReturn(NULL);
    Metadata metadata = reader.getMetadata(file);

    assertEquals(StringUtils.EMPTY, metadata.getTotalCds());
  }

  @Test
  public void shouldGetLength() throws Exception {
    int length = 325;
    when(header.getBitRate()).thenReturn("64");
    when(audioFile.hasID3v2Tag()).thenReturn(true);
    when(header.getTrackLength()).thenReturn(length);
    when(audioFile.getAudioHeader()).thenReturn(header);

    Metadata metadata = reader.getMetadata(file);

    assertEquals(length, metadata.getLength());
  }

  @Test
  public void shouldGetArtwork() throws Exception {
    reader.getMetadata(file);
    verify(artwork).getImage();
  }

  @Test
  public void shouldGetFile() throws Exception {
    Metadata metadata = reader.getMetadata(file);
    assertNotNull(metadata.getFile());
  }

  @Test
  public void shouldGetYear() throws Exception {
    when(tag.getFirst(FieldKey.YEAR)).thenReturn(YEAR);
    Metadata metadata = reader.getMetadata(file);
    assertEquals(YEAR, metadata.getYear());
  }

  @Test
  public void shouldNotGetCoverArt() throws Exception {
    when(tag.getFirstArtwork()).thenReturn(artwork);
    when(artwork.getImage()).thenReturn(bufferedImage);

    Metadata metadata = reader.getMetadata(file);

    verify(tag).getFirstArtwork();
    assertEquals(bufferedImage, metadata.getCoverArt());
  }

  @Test
  public void shouldNotGetCoverArtIfNull() throws Exception {
    when(tag.getFirstArtwork()).thenReturn(null);
    Metadata metadata = reader.getMetadata(file);

    verify(tag).getFirstArtwork();
    assertEquals(null, metadata.getCoverArt());
  }

  /** TODO: Bug in JAudioTagger null pointer exception when artwork.getImage() */
  @Test
  public void shouldNotGetCoverArtIfImageError() throws Exception {
    when(tag.getFirstArtwork()).thenReturn(artwork);
    when(artwork.getImage()).thenThrow(new NullPointerException());
    when(tag.getFirst(FieldKey.TITLE)).thenReturn(TITLE);

    reader.getMetadata(file);

    verify(tag).getFirstArtwork();
    verify(controlEngine).fireEvent(Events.LOAD_COVER_ART, new ValueEvent<String>(TITLE));
  }

  @Test
  public void shouldKnowWhenMp3IsNotANumberInsideParenthesis() throws Exception {
    String genre = "(None)";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
    reader.getMetadata(file);
    verify(readerHelper).getGenre(tag, genre);
  }

  @Test
  public void shouldReturnNewMetadataWhenNoTagOrNoHeader() throws Exception {
    when(jAudioTaggerCollaborator.isValid(tag, header)).thenReturn(false);

    reader.getMetadata(file);

    verify(tag, never()).getFirst(FieldKey.TITLE);
    verify(header, never()).getTrackLength();
  }
}
