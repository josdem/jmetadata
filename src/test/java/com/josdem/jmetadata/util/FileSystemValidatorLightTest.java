package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSystemValidatorLightTest {

  private FileSystemValidatorLight fileSystemValidatorLight;

  @BeforeEach
  void setUp() {
    fileSystemValidatorLight = new FileSystemValidatorLight(false, Collections.emptyList());
  }

  @Test
  void testValidateFileWithHiddenFile() {
    var hiddenFile = mock(File.class);
    when(hiddenFile.isHidden()).thenReturn(true);

    fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(hiddenFile));

    assertTrue(fileSystemValidatorLight.getFolderList().isEmpty());
    assertTrue(fileSystemValidatorLight.getTrackList().isEmpty());
    assertTrue(fileSystemValidatorLight.getPlaylistList().isEmpty());
  }

  @Test
  void testValidateFileWithDirectoryContainingSubdirectory() {
    var subDirectory = mock(File.class);
    when(subDirectory.isDirectory()).thenReturn(true);
    when(subDirectory.isHidden()).thenReturn(false);

    var directory = mock(File.class);
    when(directory.isDirectory()).thenReturn(true);
    when(directory.isHidden()).thenReturn(false);
    when(directory.listFiles()).thenReturn(new File[] {subDirectory});

    fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(directory));

    assertEquals(1, fileSystemValidatorLight.getFolderList().size());
    assertTrue(fileSystemValidatorLight.getTrackList().isEmpty());
    assertTrue(fileSystemValidatorLight.getPlaylistList().isEmpty());
  }

  @Test
  void testValidateFileWithDirectoryContainingOnlyFiles() {
    var file1 = mock(File.class);
    when(file1.isDirectory()).thenReturn(false);
    when(file1.isHidden()).thenReturn(false);

    var file2 = mock(File.class);
    when(file2.isDirectory()).thenReturn(false);
    when(file2.isHidden()).thenReturn(false);

    var directory = mock(File.class);
    when(directory.isDirectory()).thenReturn(true);
    when(directory.isHidden()).thenReturn(false);
    when(directory.listFiles()).thenReturn(new File[] {file1, file2});

    fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(directory));

    assertTrue(fileSystemValidatorLight.getFolderList().isEmpty());
    assertTrue(fileSystemValidatorLight.getTrackList().isEmpty());
    assertEquals(1, fileSystemValidatorLight.getPlaylistList().size());
  }

  @Test
  void testValidateFileWithRegularFile() {
    var regularFile = mock(File.class);
    when(regularFile.isDirectory()).thenReturn(false);
    when(regularFile.isHidden()).thenReturn(false);

    fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(regularFile));

    assertTrue(fileSystemValidatorLight.getFolderList().isEmpty());
    assertEquals(1, fileSystemValidatorLight.getTrackList().size());
    assertTrue(fileSystemValidatorLight.getPlaylistList().isEmpty());
  }

  @Test
  void testHasError() {
    assertFalse(fileSystemValidatorLight.hasError());
  }
}
