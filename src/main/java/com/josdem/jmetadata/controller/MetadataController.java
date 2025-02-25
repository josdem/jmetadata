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

import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.exception.InvalidId3VersionException;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.exception.TooMuchFilesException;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.service.MetadataService;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.swing.JFileChooser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.annotations.ActionMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MetadataController {

  private final Properties properties;
  private final MetadataService metadataService;
  private final ControlEngineConfigurator configurator;

  private List<Metadata> metadataList;

  @ActionMethod(Actions.GET_METADATA)
  public void getMetadata() {
    var fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    var selection = fileChooser.showOpenDialog(null);
    if (selection == JFileChooser.APPROVE_OPTION) {
      File root = fileChooser.getSelectedFile();
      log.info("DIRECTORY to SCAN: {}", root);
      if (root.exists()) {
        configurator
            .getControlEngine()
            .fireEvent(Events.DIRECTORY_SELECTED, new ValueEvent<>(root.getAbsolutePath()));
        processMetadata(root);
      } else {
        configurator
            .getControlEngine()
            .fireEvent(Events.DIRECTORY_NOT_EXIST, new ValueEvent<>(root.toString()));
      }
    } else {
      configurator.getControlEngine().fireEvent(Events.DIRECTORY_SELECTED_CANCEL);
    }
  }

  private void processMetadata(File root) {
    try {
      metadataList = metadataService.extractMetadata(root);
      if (metadataList.isEmpty()) {
        configurator.getControlEngine().fireEvent(Events.DIRECTORY_EMPTY);
      } else {
        Collections.sort(metadataList);
        sendLoadedEvent(metadataList);
      }
    } catch (IOException
        | TagException
        | ReadOnlyFileException
        | InvalidAudioFrameException
        | InvalidId3VersionException
        | CannotReadException
        | MetadataException e) {
      handleException(e);
    } catch (IllegalArgumentException e) {
      sendLoadedEvent(metadataList);
      handleException(e);
    } catch (TooMuchFilesException e) {
      log.error(e.getMessage(), e);
      String maxFilesAllowed = properties.getProperty("max.files.allowed");
      configurator
          .getControlEngine()
          .fireEvent(Events.MUCH_FILES_LOADED, new ValueEvent<>(maxFilesAllowed));
    } catch (InterruptedException e) {
      log.warn("Thread was interrupted", e);
      Thread.currentThread().interrupt();
    }
  }

  private void sendLoadedEvent(List<Metadata> metadataList) {
    configurator.getControlEngine().set(Model.METADATA, metadataList, null);
    configurator.getControlEngine().fireEvent(Events.LOAD, new ValueEvent<>(metadataList));
    configurator.getControlEngine().fireEvent(Events.LOADED);
  }

  private void handleException(Exception e) {
    log.error(e.getMessage(), e);
    configurator.getControlEngine().fireEvent(Events.OPEN);
  }
}
