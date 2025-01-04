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

package com.josdem.jmetadata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.exception.TooMuchFilesException;
import com.josdem.jmetadata.helper.MetadataHelper;
import com.josdem.jmetadata.metadata.Mp3Reader;
import com.josdem.jmetadata.metadata.Mp4Reader;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.MetadataServiceImpl;
import com.josdem.jmetadata.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class TestMetadataService {

  @InjectMocks private final MetadataService metadataService = new MetadataServiceImpl();

  @Mock private FileUtils fileUtils;
  @Mock private File root;
  @Mock private ControlEngineConfigurator configurator;
  @Mock private ControlEngine controlEngine;
  @Mock private MetadataHelper metadataHelper;
  @Mock private ExtractService extractService;
  @Mock private Mp3Reader mp3Reader;
  @Mock private Mp4Reader mp4Reader;
  @Mock private Metadata metadata;
  @Mock private Metadata anotherMetadata;
  @Mock private Set<File> filesWithoutMinimumMetadata;
  @Mock private File pepeGarden;
  @Mock private File checkStyleFile;
  @Mock private File file;
  @Mock private Properties properties;

  private final Integer maxFilesAllowed = 50;

  private final List<Metadata> metadatas = new ArrayList<Metadata>();
  private final List<File> fileList = new ArrayList<>();

  private static final String ALBUM = "Lemon Flavored Kiss";
  private static final String MY_REMIXES = "My Remixes";
  private static final String ARTIST = "Raul Islas";

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(configurator.getControlEngine()).thenReturn(controlEngine);
    when(properties.getProperty("max.files.allowed")).thenReturn(maxFilesAllowed.toString());
  }

  @Test
  @DisplayName("extracting metadata when mp3")
  public void shouldExtractMetadataWhenMp3(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    setMp3Expectations();
    setFileListExpectations();

    List<Metadata> metadatas = metadataService.extractMetadata(root);
    Metadata metadata = metadatas.getFirst();

    verifyExpectations(metadatas, metadata);
  }

  @Test
  @DisplayName("extracting metadata when mp4")
  public void shouldExtractMetadataWhenMp4(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    setMp4Expectations();
    setFileListExpectations();

    List<Metadata> metadatas = metadataService.extractMetadata(root);
    Metadata metadata = metadatas.getFirst();

    verifyExpectations(metadatas, metadata);
  }

  @Test
  @DisplayName("not extracting metadata due to not having a valid audio file")
  public void shouldDetectANotValidAudioFile(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    fileList.add(checkStyleFile);
    when(fileUtils.getFileList(root)).thenReturn(fileList);

    List<Metadata> metadatas = metadataService.extractMetadata(root);

    assertTrue(metadatas.isEmpty(), "should be empty due to not valid audio file");
  }

  @Test
  @DisplayName("cleaning metadata list")
  public void shouldCleanMetadataList(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    setMp4Expectations();
    setFileListExpectations();

    List<Metadata> metadatas = metadataService.extractMetadata(root);

    assertEquals(1, metadatas.size());

    metadatas = metadataService.extractMetadata(root);

    assertEquals(1, metadatas.size());
  }

  @Test
  @DisplayName("extracting metadata from file name")
  public void shouldDetectAFileWithoutMinimumMetadata(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    setMp3Expectations();
    fileList.add(pepeGarden);

    when(metadataHelper.createHashSet()).thenReturn(filesWithoutMinimumMetadata);
    when(fileUtils.getFileList(root)).thenReturn(fileList);

    List<Metadata> metadatas = metadataService.extractMetadata(root);

    assertEquals(1, metadatas.size());
    verify(extractService).extractFromFileName(pepeGarden);
    verify(filesWithoutMinimumMetadata).add(pepeGarden);
  }

  @Test
  @DisplayName("validating same album")
  public void shouldKnowSameAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getAlbum()).thenReturn(ALBUM);
    when(anotherMetadata.getAlbum()).thenReturn(ALBUM);

    addMetadatas();
    assertTrue(metadataService.isSameAlbum(metadatas));
  }

  @Test
  @DisplayName("validating different album")
  public void shouldKnowDifferentAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getAlbum()).thenReturn(ALBUM);
    when(anotherMetadata.getAlbum()).thenReturn(MY_REMIXES);

    addMetadatas();

    assertFalse(metadataService.isSameAlbum(metadatas));
  }

  @Test
  @DisplayName("validating empty album")
  public void shouldKnowWhenMetadataDoesNotHaveAlbum(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getAlbum()).thenReturn(StringUtils.EMPTY);
    when(anotherMetadata.getAlbum()).thenReturn(MY_REMIXES);

    addMetadatas();

    assertFalse(metadataService.isSameAlbum(metadatas));
  }

  @Test
  @DisplayName("validating not the same album if empty")
  public void shouldKnowNotSameAlbumIfEmpty(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getAlbum()).thenReturn(StringUtils.EMPTY);
    when(anotherMetadata.getAlbum()).thenReturn(StringUtils.EMPTY);

    addMetadatas();
    assertFalse(metadataService.isSameAlbum(metadatas));
  }

  @Test
  @DisplayName("validating same artist")
  public void shouldKnowSameArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getArtist()).thenReturn(ARTIST);
    when(anotherMetadata.getArtist()).thenReturn(ARTIST);

    addMetadatas();
    assertTrue(metadataService.isSameArtist(metadatas));
  }

  @Test
  @DisplayName("validating different artist")
  public void shouldKnowDifferentArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getArtist()).thenReturn(ARTIST);
    when(anotherMetadata.getArtist()).thenReturn("Paul Van Dyk");

    addMetadatas();

    assertFalse(metadataService.isSameArtist(metadatas));
  }

  @Test
  @DisplayName("validating empty artist")
  public void shouldKnowWhenMetadataDoesNotHaveArtist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getArtist()).thenReturn(StringUtils.EMPTY);
    when(anotherMetadata.getArtist()).thenReturn(ARTIST);

    addMetadatas();

    assertFalse(metadataService.isSameArtist(metadatas));
  }

  @Test
  @DisplayName("validating not the same artist if empty")
  public void shouldKnowNotSameArtistIfEmpty(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(metadata.getArtist()).thenReturn(StringUtils.EMPTY);
    when(anotherMetadata.getArtist()).thenReturn(StringUtils.EMPTY);

    addMetadatas();
    assertFalse(metadataService.isSameArtist(metadatas));
  }

  @Test
  @DisplayName("not extracting metadata when too much files")
  public void shouldNotExtractWhenTooMuchFiles(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    List<File> fileList = mock(List.class);

    when(fileList.size()).thenReturn(maxFilesAllowed + 1);
    when(fileUtils.getFileList(root)).thenReturn(fileList);

    assertThrows(TooMuchFilesException.class, () -> metadataService.extractMetadata(root));
  }

  private void setMp3Expectations()
      throws CannotReadException,
          IOException,
          TagException,
          ReadOnlyFileException,
          MetadataException {
    when(fileUtils.isMp3File(pepeGarden)).thenReturn(true);
    when(mp3Reader.getMetadata(pepeGarden)).thenReturn(metadata);
  }

  private void setMp4Expectations()
      throws IOException,
          TagException,
          ReadOnlyFileException,
          InvalidAudioFrameException,
          MetadataException {
    when(fileUtils.isM4aFile(pepeGarden)).thenReturn(true);
    when(mp4Reader.getMetadata(pepeGarden)).thenReturn(metadata);
  }

  private void setFileListExpectations() {
    when(metadata.getArtist()).thenReturn("Jaytech");
    when(metadata.getTitle()).thenReturn("Pepe Garden (Original Mix)");
    fileList.add(pepeGarden);
    when(fileUtils.getFileList(root)).thenReturn(fileList);
  }

  private void verifyExpectations(List<Metadata> metadatas, Metadata metadata) {
    assertEquals(1, metadatas.size());
    verify(fileUtils).getFileList(root);
    assertEquals("Jaytech", metadata.getArtist());
  }

  private void addMetadatas() {
    metadatas.add(metadata);
    metadatas.add(anotherMetadata);
  }
}
