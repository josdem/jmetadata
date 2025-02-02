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

package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.ApplicationConstants;
import java.io.File;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.lang.NonNull;

class FileUtilsTest {

  private static final String ONLY_DIGITS_REGEX = "[0-9]+";

  @InjectMocks private final FileUtils fileUtils = new FileUtils();

  @Mock private File file;

  private final File root = new File("src/test/resources/audio");

  private final Logger log = Logger.getLogger(this.getClass().getName());

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("scanning a directory")
  public void shouldScanADirectory(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    var root = new MockFile("root");
    var fileList = fileUtils.getFileList(root);
    assertEquals(2, fileList.size());
  }

  @Test
  @DisplayName("creating a temp file for image file")
  public void shouldCreateFileForImage(TestInfo testInfo) throws Exception {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    var result = fileUtils.createFile(root, StringUtils.EMPTY, ApplicationConstants.IMAGE_EXT);
    assertTrue(result.getName().matches("JMetadata_" + ONLY_DIGITS_REGEX + ".png"));
  }

  @Test
  @DisplayName("getting mp3 file")
  public void shouldKnowIfIsMp3File(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    when(file.getPath()).thenReturn("somePath.mp3");
    assertTrue(fileUtils.isMp3File(file));
  }

  @Test
  @DisplayName("not getting mp3 file")
  public void shouldKnowIfIsNotMp3File(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    when(file.getPath()).thenReturn("somePath.wma");
    assertFalse(fileUtils.isMp3File(file));
  }

  @Test
  @DisplayName("getting mp4 file")
  public void shouldKnowIfIsMp4File(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    when(file.getPath()).thenReturn("somePath.m4a");
    assertTrue(fileUtils.isM4aFile(file));
  }

  @Test
  @DisplayName("not getting mp4 file")
  public void shouldKnowIfIsNotMp4File(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    when(file.getPath()).thenReturn("somePath.wma");
    assertFalse(fileUtils.isM4aFile(file));
  }

  @Test
  @DisplayName("creating a temp file")
  public void shouldCreateTempFile(TestInfo testInfo) throws Exception {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    File tempFile = fileUtils.createTempFile();
    assertTrue(tempFile.getName().startsWith(ApplicationConstants.PREFIX));
    assertTrue(tempFile.getName().endsWith(ApplicationConstants.FILE_EXT));
  }

  @Test
  @DisplayName("creating a temp file as text file")
  public void shouldCreateFileForFile(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    File result = fileUtils.createFile(root, StringUtils.EMPTY, ApplicationConstants.FILE_EXT);
    assertTrue(result.getName().matches("JMetadata_" + ONLY_DIGITS_REGEX + ".txt"));
  }

  @Test
  @DisplayName("creating a temp file as text file with prefix")
  public void shouldCreateFileForFileWithPrefix(TestInfo testInfo) {
    log.info(() -> "Running test: " + testInfo.getDisplayName());
    String prefix = "MIRI_";
    File result = fileUtils.createFile(root, prefix, ApplicationConstants.FILE_EXT);
    assertTrue(result.getName().matches(prefix + ONLY_DIGITS_REGEX + ".txt"));
  }

  private static class MockFile extends File {
    String path = "src\\test\\resources";

    public MockFile(String pathname) {
      super(pathname);
    }

    public String[] list() {
      return new String[] {"fileNameOne", "fileNameTwo"};
    }

    @NonNull
    public String getAbsolutePath() {
      return path;
    }
  }
}
