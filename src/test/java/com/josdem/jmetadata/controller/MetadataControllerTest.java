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

package com.josdem.jmetadata.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.service.MetadataService;
import java.io.File;
import java.util.Collections;
import java.util.Properties;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class MetadataControllerTest {

  private MetadataController metadataController;

  @Mock private Properties properties;
  @Mock private MetadataService metadataService;
  @Mock private ControlEngineConfigurator configurator;
  @Mock private ControlEngine controlEngine;
  @Mock private JFileChooser fileChooser;
  @Mock private File file;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    metadataController = new MetadataController(properties, metadataService, configurator);
    metadataController.fileChooser = fileChooser;
  }

  @Test
  @DisplayName("informing user selected an empty directory")
  void shouldInformEmptyDirectory(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION);
    when(fileChooser.getSelectedFile()).thenReturn(file);
    when(file.exists()).thenReturn(true);
    when(configurator.getControlEngine()).thenReturn(controlEngine);
    when(metadataService.extractMetadata(file)).thenReturn(Collections.emptyList());

    metadataController.getMetadata();

    verify(fileChooser).setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    verify(controlEngine)
        .fireEvent(Events.DIRECTORY_SELECTED, new ValueEvent<>(file.getAbsolutePath()));
    verify(controlEngine).fireEvent(Events.DIRECTORY_EMPTY);
  }

  @Test
  @DisplayName("informing user the directory does not exist")
  void shouldInformDirectoryDoesNotExist(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());
    when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION);
    when(fileChooser.getSelectedFile()).thenReturn(file);
    when(configurator.getControlEngine()).thenReturn(controlEngine);
    when(file.exists()).thenReturn(false);

    metadataController.getMetadata();

    verify(fileChooser).setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    verify(controlEngine).fireEvent(Events.DIRECTORY_NOT_EXIST, new ValueEvent<>(file.toString()));
  }
}
