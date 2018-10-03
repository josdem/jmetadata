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

package org.jas.service.impl;

import java.io.File;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;

import org.asmatron.messengine.engines.support.ControlEngineConfigurator;

import org.jas.model.Model;
import org.jas.model.Metadata;
import org.jas.util.FileUtils;
import org.jas.helper.MetadataHelper;
import org.jas.service.ExtractService;
import org.jas.service.MetadataService;
import org.jas.metadata.MetadataReader;
import org.jas.metadata.MetadataException;
import org.jas.exception.TooMuchFilesException;
import org.jas.exception.InvalidId3VersionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @understands A class who knows how extract metadata from files using a root directory
*/

@Service
public class MetadataServiceImpl implements MetadataService {

  @Autowired
	private ControlEngineConfigurator configurator;
	@Autowired
	private MetadataHelper metadataHelper;
	@Autowired
	private ExtractService extractService;
	@Autowired
	private MetadataReader mp3Reader;
	@Autowired
	private MetadataReader mp4Reader;
	@Autowired
	private Properties properties;

	private List<Metadata> metadataList;
	private Set<File> filesWithoutMinimumMetadata;
	private FileUtils fileUtils = new FileUtils();

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public List<Metadata> extractMetadata(File root) throws IOException, InterruptedException, TooMuchFilesException, CannotReadException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException, InvalidId3VersionException {
		metadataList = new ArrayList<Metadata>();
		filesWithoutMinimumMetadata = metadataHelper.createHashSet();
		List<File> fileList = fileUtils.getFileList(root);
		if(fileList.size() > Integer.parseInt(properties.getProperty("max.files.allowed"))){
			throw new TooMuchFilesException(fileList.size());
		}
		configurator.getControlEngine().set(Model.FILES_WITHOUT_MINIMUM_METADATA, filesWithoutMinimumMetadata, null);
		return getMetadataList(fileList);
	}

	private List<Metadata> getMetadataList(List<File> fileList) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException {
		for (File file : fileList) {
			log.info("Reading file: " + file.getName());
			Metadata metadata = null;
			if (fileUtils.isMp3File(file)) {
				metadata = mp3Reader.getMetadata(file);
			} else if (fileUtils.isM4aFile(file)) {
				metadata = mp4Reader.getMetadata(file);
			}

			if (metadata == null) {
				log.info(file.getAbsoluteFile() + " is not a valid Audio File");
			} else if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())) {
				metadataList.add(metadata);
			} else {
				metadataList.add(extractService.extractFromFileName(file));
				filesWithoutMinimumMetadata.add(file);
			}
		}
		return metadataList;
	}

	public boolean isSameAlbum(List<Metadata> metadatas) {
		for(int i = 0 ; i < metadatas.size() - 1  ; i++){
			if (metadatas.get(i).getAlbum() == null){
				return false;
			}
			if(!metadatas.get(i).getAlbum().equals(metadatas.get(i+1).getAlbum())){
				return false;
			}
		}
		return true;
	}

}
