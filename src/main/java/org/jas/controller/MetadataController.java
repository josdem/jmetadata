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

package org.jas.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.ActionMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.action.Actions;
import org.jas.event.Events;
import org.jas.exception.InvalidId3VersionException;
import org.jas.exception.TooMuchFilesException;
import org.jas.metadata.MetadataException;
import org.jas.model.Metadata;
import org.jas.model.Model;
import org.jas.service.MetadataService;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @understands A class who knows how to load metadata from files
 */

@Controller
public class MetadataController {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ControlEngineConfigurator configurator;
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private Properties properties;

	private List<Metadata> metadataList;

	@ActionMethod(Actions.GET_METADATA)
	public void getMetadata() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int selection = fileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION) {
			File root = fileChooser.getSelectedFile();
			log.info("\nDIRECTORY to SCAN: " + root);
			if(root.exists()){
				configurator.getControlEngine().fireEvent(Events.DIRECTORY_SELECTED, new ValueEvent<String>(root.getAbsolutePath()));
				try {
					metadataList = metadataService.extractMetadata(root);
					if(metadataList.isEmpty()){
						configurator.getControlEngine().fireEvent(Events.DIRECTORY_EMPTY);
					}  else {
						Collections.sort(metadataList);
						sendLoadedEvent(metadataList);
					}
				} catch (IOException e) {
					handleException(e);
				} catch (TagException e) {
					handleException(e);
				} catch (ReadOnlyFileException e) {
					handleException(e);
				} catch (InvalidAudioFrameException e) {
					handleException(e);
				} catch (InvalidId3VersionException e) {
					handleException(e);
				} catch (InterruptedException e) {
					handleException(e);
				} catch (CannotReadException e) {
					handleException(e);
				} catch (MetadataException e) {
					handleException(e);
				} catch (IllegalArgumentException e) {
					sendLoadedEvent(metadataList);
					handleException(e);
				} catch (TooMuchFilesException e) {
					log.error(e, e);
					String maxFilesAllowed = properties.getProperty("max.files.allowed");
					configurator.getControlEngine().fireEvent(Events.MUCH_FILES_LOADED, new ValueEvent<String>(maxFilesAllowed));
				}
			} else {
				configurator.getControlEngine().fireEvent(Events.DIRECTORY_NOT_EXIST, new ValueEvent<String>(root.toString()));
			}
		} else {
			configurator.getControlEngine().fireEvent(Events.DIRECTORY_SELECTED_CANCEL);
		}
	}

	private void sendLoadedEvent(List<Metadata> metadataList) {
		configurator.getControlEngine().set(Model.METADATA, metadataList, null);
		configurator.getControlEngine().fireEvent(Events.LOAD, new ValueEvent<List<Metadata>>(metadataList));
		configurator.getControlEngine().fireEvent(Events.LOADED);
	}

	private void handleException(Exception e) {
		log.error(e, e);
		configurator.getControlEngine().fireEvent(Events.OPEN);
	}
}
