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

package org.jas.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.jas.exception.InvalidId3VersionException;
import org.jas.exception.TooMuchFilesException;
import org.jas.helper.MetadataHelper;
import org.jas.metadata.MetadataException;
import org.jas.metadata.Mp3Reader;
import org.jas.metadata.Mp4Reader;
import org.jas.model.Metadata;
import org.jas.service.ExtractService;
import org.jas.service.MetadataService;
import org.jas.util.FileUtils;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestMetadataService {
	@InjectMocks
	private MetadataService metadataService = new MetadataService();

	@Mock
	private FileUtils fileUtils;
	@Mock
	private File root;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private MetadataHelper metadataHelper;
	@Mock
	private ExtractService extractService;
	@Mock
	private Mp3Reader mp3Reader;
	@Mock
	private Mp4Reader mp4Reader;
	@Mock
	private Metadata metadata;
	@Mock
	Metadata anotherMetadata;
	@Mock
	private Set<File> filesWithoutMinimumMetadata;
	@Mock
	private File pepeGarden;
	@Mock
	private File checkStyleFile;
	@Mock
	private File file;
	@Mock
	private Properties properties;

	private Integer maxFilesAllowed = 50;

	private List<Metadata> metadatas = new ArrayList<Metadata>();
	private List<File> fileList = new ArrayList<File>();

	private static final String ALBUM = "Lemon Flavored Kiss";
	private static final String MY_REMIXES = "My Remixes";
	private static final int FIRST_ELEMENT = 0;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
		when(properties.getProperty("max.files.allowed")).thenReturn(maxFilesAllowed.toString());
	}

	@Test
	public void shouldExtractMetadataWhenMp3() throws Exception {
		setMp3Expectations();
		setFileListExpectations();

		List<Metadata> metadatas = metadataService.extractMetadata(root);
		Metadata metadata = metadatas.get(FIRST_ELEMENT);

		verifyExpectations(metadatas, metadata);
	}

	@Test
	public void shouldExtractMetadataWhenMp4() throws Exception {
		setMp4Expectations();
		setFileListExpectations();

		List<Metadata> metadatas = metadataService.extractMetadata(root);
		Metadata metadata = metadatas.get(FIRST_ELEMENT);

		verifyExpectations(metadatas, metadata);
	}

	@Test
	public void shouldDetectANotValidAudioFile() throws Exception {
		setMp4Expectations();
		setFileListExpectations();
		fileList.add(checkStyleFile);

		List<Metadata> metadatas = metadataService.extractMetadata(root);
		Metadata metadata = metadatas.get(FIRST_ELEMENT);

		verifyExpectations(metadatas, metadata);
	}

	private void setMp4Expectations() throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException {
		when(fileUtils.isM4aFile(pepeGarden)).thenReturn(true);
		when(mp4Reader.getMetadata(pepeGarden)).thenReturn(metadata);
	}

	private void verifyExpectations(List<Metadata> metadatas, Metadata metadata) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, InvalidId3VersionException {
		assertEquals(1, metadatas.size());
		verify(fileUtils).getFileList(root);
		assertEquals("Jaytech", metadata.getArtist());
	}

	@Test
	public void shouldCleanMetadataList() throws Exception {
		setMp4Expectations();
		setFileListExpectations();

		List<Metadata> metadatas = metadataService.extractMetadata(root);

		assertEquals(1, metadatas.size());

		metadatas = metadataService.extractMetadata(root);

		assertEquals(1, metadatas.size());
	}

	private void setFileListExpectations() throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		when(metadata.getArtist()).thenReturn("Jaytech");
		when(metadata.getTitle()).thenReturn("Pepe Garden (Original Mix)");
		fileList.add(pepeGarden);
		when(fileUtils.getFileList(root)).thenReturn(fileList);
	}

	private void setMp3Expectations() throws CannotReadException, IOException, TagException, ReadOnlyFileException, MetadataException {
		when(fileUtils.isMp3File(pepeGarden)).thenReturn(true);
		when(mp3Reader.getMetadata(pepeGarden)).thenReturn(metadata);
	}

	@Test
	public void shouldDetectAFileWithoutMinimumMetadata() throws Exception {
		setMp3Expectations();
		fileList.add(pepeGarden);

		when(metadataHelper.createHashSet()).thenReturn(filesWithoutMinimumMetadata);
		when(fileUtils.getFileList(root)).thenReturn(fileList);

		List<Metadata> metadatas = metadataService.extractMetadata(root);

		assertEquals(1, metadatas.size());
		verify(fileUtils, never()).isM4aFile(pepeGarden);
		verify(extractService).extractFromFileName(pepeGarden);
		verify(filesWithoutMinimumMetadata).add(pepeGarden);
	}

	@Test
	public void shouldKnowSameAlbum() throws Exception {
		when(metadata.getAlbum()).thenReturn(ALBUM);
		when(anotherMetadata.getAlbum()).thenReturn(ALBUM);

		addMetadatas();
		assertTrue(metadataService.isSameAlbum(metadatas));
	}

	@Test
	public void shouldKnowDifferentAlbum() throws Exception {
		when(metadata.getAlbum()).thenReturn(ALBUM);
		when(anotherMetadata.getAlbum()).thenReturn(MY_REMIXES);

		addMetadatas();

		assertFalse(metadataService.isSameAlbum(metadatas));
	}

	private void addMetadatas() {
		metadatas.add(metadata);
		metadatas.add(anotherMetadata);
	}

	@Test
	public void shouldKnowWhenNoAlbumIsThere() throws Exception {
		when(metadata.getAlbum()).thenReturn(null);
		when(anotherMetadata.getAlbum()).thenReturn(MY_REMIXES);

		addMetadatas();

		assertFalse(metadataService.isSameAlbum(metadatas));
	}

	@Test (expected=TooMuchFilesException.class)
	@SuppressWarnings("unchecked")
	public void shouldNotExtractWhenTooMuchFiles() throws Exception {
		List<File> fileList = Mockito.mock(ArrayList.class);

		when(fileList.size()).thenReturn(maxFilesAllowed + 1);
		when(fileUtils.getFileList(root)).thenReturn(fileList);

		metadataService.extractMetadata(root);
	}

}
