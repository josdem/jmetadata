package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FileSystemValidatorLightTest {

    private FileSystemValidatorLight fileSystemValidatorLight;

    @BeforeEach
    void setUp() {
        // Initialize the FileSystemValidatorLight with an empty list
        fileSystemValidatorLight = new FileSystemValidatorLight(false, Collections.emptyList());
    }

    @Test
    void testValidateFileWithHiddenFile() {
        File hiddenFile = Mockito.mock(File.class);
        Mockito.when(hiddenFile.isHidden()).thenReturn(true);

        fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(hiddenFile));

        assertTrue(fileSystemValidatorLight.getFolderList().isEmpty());
        assertTrue(fileSystemValidatorLight.getTrackList().isEmpty());
        assertTrue(fileSystemValidatorLight.getPlaylistList().isEmpty());
    }

    @Test
    void testValidateFileWithDirectoryContainingSubdirectory() {
        File subDirectory = Mockito.mock(File.class);
        Mockito.when(subDirectory.isDirectory()).thenReturn(true);
        Mockito.when(subDirectory.isHidden()).thenReturn(false);

        File directory = Mockito.mock(File.class);
        Mockito.when(directory.isDirectory()).thenReturn(true);
        Mockito.when(directory.isHidden()).thenReturn(false);
        Mockito.when(directory.listFiles()).thenReturn(new File[]{subDirectory});

        fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(directory));

        assertEquals(1, fileSystemValidatorLight.getFolderList().size());
        assertTrue(fileSystemValidatorLight.getTrackList().isEmpty());
        assertTrue(fileSystemValidatorLight.getPlaylistList().isEmpty());
    }

    @Test
    void testValidateFileWithDirectoryContainingOnlyFiles() {
        File file1 = Mockito.mock(File.class);
        Mockito.when(file1.isDirectory()).thenReturn(false);
        Mockito.when(file1.isHidden()).thenReturn(false);

        File file2 = Mockito.mock(File.class);
        Mockito.when(file2.isDirectory()).thenReturn(false);
        Mockito.when(file2.isHidden()).thenReturn(false);

        File directory = Mockito.mock(File.class);
        Mockito.when(directory.isDirectory()).thenReturn(true);
        Mockito.when(directory.isHidden()).thenReturn(false);
        Mockito.when(directory.listFiles()).thenReturn(new File[]{file1, file2});

        fileSystemValidatorLight = new FileSystemValidatorLight(false, Arrays.asList(directory));

        assertTrue(fileSystemValidatorLight.getFolderList().isEmpty());
        assertTrue(fileSystemValidatorLight.getTrackList().isEmpty());
        assertEquals(1, fileSystemValidatorLight.getPlaylistList().size());
    }

    @Test
    void testValidateFileWithRegularFile() {
        File regularFile = Mockito.mock(File.class);
        Mockito.when(regularFile.isDirectory()).thenReturn(false);
        Mockito.when(regularFile.isHidden()).thenReturn(false);

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
