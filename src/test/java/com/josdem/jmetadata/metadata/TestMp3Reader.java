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

package com.josdem.jmetadata.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.collaborator.JAudioTaggerCollaborator;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.helper.AudioFileHelper;
import com.josdem.jmetadata.helper.ReaderHelper;
import com.josdem.jmetadata.model.Metadata;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
public class TestMp3Reader {
  private static final String ARTIST = "Armin Van Buuren";
  private static final String TITLE = "Control Freak (Sander Van Doorn Remix)";
  private static final String YEAR = "2011";
  private static final String BIT_RATE = "64";

  @InjectMocks private MetadataReader reader = new Mp3Reader();

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
  void setup() throws Exception {
    MockitoAnnotations.openMocks(this);
    when(audioFileHelper.read(file)).thenReturn(audioFile);
    when(audioFile.getTag()).thenReturn(tag);
    when(audioFile.getAudioHeader()).thenReturn(header);
    when(artwork.getImage()).thenReturn(bufferedImage);
    when(tag.getFirstArtwork()).thenReturn(artwork);
    when(audioFile.hasID3v2Tag()).thenReturn(true);
    when(header.getBitRate()).thenReturn(BIT_RATE);
    when(configurator.getControlEngine()).thenReturn(controlEngine);
    when(jAudioTaggerCollaborator.isValid(tag, header)).thenReturn(true);
    when(configurator.getControlEngine()).thenReturn(controlEngine);
  }

  @Test
  @DisplayName("updating ID3 to V2")
  void shouldUpdateID3toV2(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(audioFile.hasID3v2Tag()).thenReturn(false);
    reader.getMetadata(file);

    verify(audioFile).setID3v2TagOnly(isA(AbstractID3v2Tag.class));
    verify(audioFile).commit();
  }

  @Test
  @DisplayName("getting metadata")
  void shouldGetMetadata(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(audioFile.hasID3v2Tag()).thenReturn(true);
    reader.getMetadata(file);

    verify(audioFile).getTag();
    verify(audioFile).getAudioHeader();
  }

  @Test
  @DisplayName("not getting metadata due to invalid audio frame")
  void shouldNotGetMetadataDueToInvalidAudioFrame(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(audioFileHelper.read(file))
        .thenThrow(new InvalidAudioFrameException("Invalid Audio Frame Exception"));

    assertThrows(BusinessException.class, () -> reader.getMetadata(file));

    verify(audioFile, never()).getTag();
    verify(audioFile, never()).getAudioHeader();
  }

  @Test
  @DisplayName("getting artist")
  void shouldGetArtist(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(tag.getFirst(FieldKey.ARTIST)).thenReturn(ARTIST);
    var metadata = reader.getMetadata(file);

    assertEquals(ARTIST, metadata.getArtist());
  }

  @Test
  @DisplayName("getting title")
  void shouldGetTitle(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(tag.getFirst(FieldKey.TITLE)).thenReturn(TITLE);
    var metadata = reader.getMetadata(file);

    assertEquals(TITLE, metadata.getTitle());
  }

  @Test
  @DisplayName("getting album")
  void shouldGetAlbum(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var album = "Nobody Seems To Care / Murder Weapon";
    when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
    var metadata = reader.getMetadata(file);

    assertEquals(album, metadata.getAlbum());
  }

  @Test
  @DisplayName("getting year")
  void shouldGetYear(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(tag.getFirst(FieldKey.YEAR)).thenReturn(YEAR);
    var metadata = reader.getMetadata(file);

    assertEquals(YEAR, metadata.getYear());
  }

  @Test
  @DisplayName("getting length")
  void shouldGetLength(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var length = 325;
    when(header.getTrackLength()).thenReturn(length);
    var metadata = reader.getMetadata(file);

    assertEquals(length, metadata.getLength());
  }

  @Test
  @DisplayName("getting bit rate")
  void shouldGetBitRate(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var metadata = reader.getMetadata(file);

    assertEquals(Integer.parseInt(BIT_RATE), metadata.getBitRate());
  }

  @Test
  @DisplayName("getting variable bit rate")
  void shouldGetVariableBitRate(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var variableBitRate = "~64";
    when(header.getBitRate()).thenReturn(variableBitRate);
    var metadata = reader.getMetadata(file);

    assertEquals(Integer.parseInt(BIT_RATE), metadata.getBitRate());
  }

  @Test
  @DisplayName("getting track number")
  public void shouldGetTrackNumber(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var trackNumber = "11";
    when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
    var metadata = reader.getMetadata(file);

    assertEquals(trackNumber, metadata.getTrackNumber());
  }

  @Test
  @DisplayName("getting total tracks")
  public void shouldGetTotalTracks(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var totalTracks = "20";
    when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(totalTracks);
    var metadata = reader.getMetadata(file);

    assertEquals(totalTracks, metadata.getTotalTracks());
  }

  @Test
  @DisplayName("getting cd number")
  public void shouldGetCdNumber(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var cdNumber = "1";
    when(tag.getFirst(FieldKey.DISC_NO)).thenReturn(cdNumber);
    var metadata = reader.getMetadata(file);

    assertEquals(cdNumber, metadata.getCdNumber());
  }

  @Test
  @DisplayName("getting total cds")
  public void shouldGetTotalCds(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var totalCds = "2";
    when(tag.getFirst(FieldKey.DISC_TOTAL)).thenReturn(totalCds);
    var metadata = reader.getMetadata(file);

    assertEquals(totalCds, metadata.getTotalCds());
  }

  @Test
  @DisplayName("getting genre")
  public void shouldGetGenre(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var genre = "Minimal Techno";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
    when(readerHelper.getGenre(tag, genre)).thenReturn(genre);

    var metadata = reader.getMetadata(file);

    assertEquals(genre, metadata.getGenre());
  }

  @Test
  @DisplayName("getting genre by code")
  public void shouldGetGenreByCode(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    var genreAsCode = "31";
    var genre = "Trance";
    when(tag.getFirst(FieldKey.GENRE)).thenReturn(genreAsCode);
    var metadata = reader.getMetadata(file);

    assertEquals(genre, metadata.getGenre());
  }

  @Test
  @DisplayName("getting cover art")
  public void shouldNotGetCoverArt() throws Exception {
    when(artwork.getImage()).thenReturn(bufferedImage);
    var metadata = reader.getMetadata(file);

    assertEquals(bufferedImage, metadata.getCoverArt());
  }

  @Test
  @DisplayName("not getting cover art if no artwork")
  public void shouldNotGetCoverArtIfNoArtWork(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(tag.getFirstArtwork()).thenReturn(null);
    var metadata = reader.getMetadata(file);

    assertNull(metadata.getCoverArt());
  }

  @Test
  @DisplayName("not getting cover art due to exception")
  public void shouldNotGetCoverArtDueToException(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(artwork.getImage()).thenThrow(new IOException("IO Exception"));
    when(tag.getFirst(FieldKey.TITLE)).thenReturn(TITLE);
    Metadata metadata = reader.getMetadata(file);

    assertNull(metadata.getCoverArt());
    verify(controlEngine).fireEvent(Events.LOAD_COVER_ART, new ValueEvent<>(TITLE));
  }

  @Test
  @DisplayName("getting file")
  public void shouldGetFile(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    Metadata metadata = reader.getMetadata(file);
    assertNotNull(metadata.getFile());
  }

  @Test
  @DisplayName("getting new metadata when no tag or no header")
  public void shouldReturnNewMetadataWhenNoTagOrNoHeader(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(jAudioTaggerCollaborator.isValid(tag, header)).thenReturn(false);

    assertNotNull(reader.getMetadata(file));

    verify(tag, never()).getFirst(FieldKey.TITLE);
    verify(header, never()).getTrackLength();
  }
}
