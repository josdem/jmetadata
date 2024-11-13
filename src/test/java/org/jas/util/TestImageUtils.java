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
import org.jas.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class TestImageUtils {

    private static final Integer THREE_HUNDRED = 300;

    @InjectMocks
    private ImageUtils imageUtils = new ImageUtils();

    @Mock
    private ImageService imageService;
    @Mock
    private Image image;
    @Mock
    private File file;
    @Mock
    private FileUtils fileUtils;
    @Mock
    private File root;

    private String prefix = "MIRI_";
    private String path = "PATH";

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveCoverArtToFile() throws Exception {
        when(imageService.createTempFile(StringUtils.EMPTY)).thenReturn(file);
        when(image.getHeight(isA(ImageObserver.class))).thenReturn(300);

        imageUtils.saveCoverArtToFile(image, StringUtils.EMPTY);

        verify(imageService).createTempFile(StringUtils.EMPTY);
        verify(imageService).write(image, file);
    }

    @Test
    public void shouldNotSaveCoverArtIfNoImage() throws Exception {
        imageUtils.saveCoverArtToFile(null, StringUtils.EMPTY);

        verify(imageService, never()).createTempFile(StringUtils.EMPTY);
    }

    @Test
    public void shouldNotSaveCoverArtIfRootAndNoImage() throws Exception {
        imageUtils.saveCoverArtToFile(null, file, StringUtils.EMPTY);

        verify(fileUtils, never()).createFile(file, StringUtils.EMPTY, ApplicationState.IMAGE_EXT);
    }

    @Test
    public void shouldSaveImageToFile() throws Exception {
        when(fileUtils.createFile(root, prefix, ApplicationState.IMAGE_EXT)).thenReturn(file);
        when(file.getAbsolutePath()).thenReturn(path);
        when(image.getHeight(isA(ImageObserver.class))).thenReturn(THREE_HUNDRED);

        imageUtils.saveCoverArtToFile(image, root, prefix);

        verify(fileUtils).createFile(root, prefix, ApplicationState.IMAGE_EXT);
    }

}
