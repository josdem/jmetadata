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

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.controller.MetadataController;
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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SuppressWarnings("unchecked")
public class TestMetadataController {

	@InjectMocks
	private MetadataController controller = new MetadataController();

	@Mock
	private JFileChooser fileChooser;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private MetadataService metadataExtractor;
	@Mock
	private File root;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private Metadata metadata;
	@Mock
	private File firstTrackFile;
	@Mock
	private File secondTrackFile;

	private List<Metadata> metadataList = new ArrayList<Metadata>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(fileChooser.getSelectedFile()).thenReturn(root);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
	}

	@Test
	public void shouldGetMetadata() throws Exception {
		setFileChooserExpectations();
		metadataList.add(metadata);
		when(metadataExtractor.extractMetadata(root)).thenReturn(metadataList);

		controller.getMetadata();

		fileChooserSetupExpectations();
		verify(fileChooser).getSelectedFile();
		verify(controlEngine).fireEvent(eq(Events.DIRECTORY_SELECTED), isA(ValueEvent.class));
		verify(controlEngine).set(Model.METADATA, metadataList, null);
		verify(metadataExtractor).extractMetadata(root);
		verify(controlEngine).fireEvent(Events.LOAD, new ValueEvent<List<Metadata>>(metadataList));
		verify(controlEngine).fireEvent(Events.LOADED);
	}

	@Test
	public void shouldGetAnOrderListByTrackNumnber() throws Exception {
		setFileChooserExpectations();

		Metadata firstTrack = new Metadata();
		firstTrack.setOrderByFile(false);
		firstTrack.setTrackNumber("1");

		Metadata secondTrack = new Metadata();
		secondTrack.setOrderByFile(false);
		secondTrack.setTrackNumber("2");

		metadataList.add(secondTrack);
		metadataList.add(firstTrack);

		controller.getMetadata();

		assertEquals("1", metadataList.get(0).getTrackNumber());
		assertEquals("2", metadataList.get(1).getTrackNumber());
	}

	@Test
	public void shouldGetAnOrderListByName() throws Exception {
		setFileChooserExpectations();
		when(firstTrackFile.getName()).thenReturn("B");
		when(secondTrackFile.getName()).thenReturn("A");

		Metadata firstTrack = new Metadata();
		firstTrack.setOrderByFile(true);
		firstTrack.setFile(firstTrackFile);
		firstTrack.setTrackNumber("1");

		Metadata secondTrack = new Metadata();
		secondTrack.setOrderByFile(true);
		secondTrack.setFile(secondTrackFile);
		secondTrack.setTrackNumber("2");

		metadataList.add(secondTrack);
		metadataList.add(firstTrack);

		controller.getMetadata();

		assertEquals("2", metadataList.get(0).getTrackNumber());
		assertEquals("1", metadataList.get(1).getTrackNumber());
	}

	private void setFileChooserExpectations() throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException, MetadataException, TooMuchFilesException {
		when(root.exists()).thenReturn(true);
		when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(fileChooser.getSelectedFile()).thenReturn(root);
		when(metadataExtractor.extractMetadata(root)).thenReturn(metadataList);
	}

	private void fileChooserSetupExpectations() {
		verify(fileChooser).setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		verify(fileChooser).showOpenDialog(null);
	}

	@Test
	public void shouldKnowWhenUserCancel() throws Exception {
		when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.CANCEL_OPTION);

		controller.getMetadata();

		fileChooserSetupExpectations();
		verify(fileChooser, never()).getSelectedFile();
		verify(controlEngine, never()).fireEvent(eq(Events.DIRECTORY_SELECTED), isA(ValueEvent.class));
		verify(metadataExtractor, never()).extractMetadata(root);
		verify(controlEngine, never()).fireEvent(Events.LOADED);
	}

	@Test
	public void shouldNotFindAnyAudioFile() throws Exception {
		setFileChooserExpectations();

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.DIRECTORY_EMPTY);
		verify(controlEngine, never()).fireEvent(Events.LOADED);
	}

	@Test
	public void shouldCatchAnIOException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new IOException());

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnTagException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new TagException());

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnReadOnlyFileException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new ReadOnlyFileException());

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnInvalidAudioFrameException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new InvalidAudioFrameException(null));

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnInvalidId3VersionException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new InvalidId3VersionException());

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnInterruptedException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new InterruptedException());

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnCannotReadException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new CannotReadException());

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnMetadataException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new MetadataException(null));

		controller.getMetadata();

		verify(controlEngine).fireEvent(Events.OPEN);
	}

	@Test
	public void shouldCatchAnIllegalArgumentException() throws Exception {
		when(root.exists()).thenReturn(true);
		when(metadataExtractor.extractMetadata(root)).thenThrow(new IllegalArgumentException());

		controller.getMetadata();

		verify(controlEngine).set(Model.METADATA, null, null);
		verify(controlEngine).fireEvent(Events.LOAD, new ValueEvent<List<Metadata>>(null));
		verify(controlEngine).fireEvent(Events.LOADED);
	}
}
