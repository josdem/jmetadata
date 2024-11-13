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

package org.jas.util;

import org.apache.commons.lang3.StringUtils;
import org.jas.ApplicationState;
import org.jas.helper.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TestFileUtils {

    @InjectMocks
    private final FileUtils fileUtils = new FileUtils();

    @Mock
    private DateHelper fileHelper;
    @Mock
    private File file;

    private long timestamp = 1332562352428L;
    private File root = new File("src/test/resources/audio");

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldScanADirectory() throws Exception {
        File root = new MockFile("root");
        List<File> fileList = fileUtils.getFileList(root);
        assertEquals(2, fileList.size());
    }

    @SuppressWarnings("serial")
    class MockFile extends File {
        String path = "src\\test\\resources";

        public MockFile(String pathname) {
            super(pathname);
        }

        public String[] list() {
            String[] fileList = {"fileNameOne", "fileNameTwo"};
            return fileList;
        }

        public String getAbsolutePath() {
            return path;
        }
    }

    @Test
    public void shouldCreateFileForImage() throws Exception {
        String expectedPath = "JAS_1332562352428.png";
        when(fileHelper.getTimestamp()).thenReturn(timestamp);
        File result = fileUtils.createFile(root, StringUtils.EMPTY, ApplicationState.IMAGE_EXT);

        assertEquals(expectedPath, result.getName());
    }

    @Test
    public void shouldKnowIfIsMp3File() throws Exception {
        when(file.getPath()).thenReturn("somePath.mp3");
        assertTrue(fileUtils.isMp3File(file));
    }

    @Test
    public void shouldKnowIfIsNotMp3File() throws Exception {
        when(file.getPath()).thenReturn("somePath.wma");
        assertFalse(fileUtils.isMp3File(file));
    }

    @Test
    public void shouldKnowIfIsMp4File() throws Exception {
        when(file.getPath()).thenReturn("somePath.m4a");
        assertTrue(fileUtils.isM4aFile(file));
    }

    @Test
    public void shouldKnowIfIsNotMp4File() throws Exception {
        when(file.getPath()).thenReturn("somePath.wma");
        assertFalse(fileUtils.isM4aFile(file));
    }

    @Test
    public void shouldCreateTempfile() throws Exception {
        File tempFile = fileUtils.createTempFile();
        assertTrue(tempFile.getName().startsWith(ApplicationState.PREFIX));
        assertTrue(tempFile.getName().endsWith(ApplicationState.FILE_EXT));
    }

    @Test
    public void shouldCreateFileForFile() throws Exception {
        String expectedPath = "JAS_1332562352428.txt";
        when(fileHelper.getTimestamp()).thenReturn(timestamp);
        File result = fileUtils.createFile(root, StringUtils.EMPTY, ApplicationState.FILE_EXT);

        assertEquals(expectedPath, result.getName());
    }

    @Test
    public void shouldCreateFileForFileWithPrefix() throws Exception {
        String expectedPath = "MIRI_1332562352428.txt";
        String prefix = "MIRI_";
        when(fileHelper.getTimestamp()).thenReturn(timestamp);
        File result = fileUtils.createFile(root, prefix, ApplicationState.FILE_EXT);

        assertEquals(expectedPath, result.getName());
    }

}
