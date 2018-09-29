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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;

import org.jas.model.Model;
import org.jas.model.Metadata;
import org.jas.util.FileUtils;
import org.jas.helper.MetadataHelper;
import org.jas.service.ExtractService;
import org.jas.service.MetadataService;
import org.jas.metadata.MetadataReader;
import org.jas.exception.TooMuchFilesException;

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

	private Log log = LogFactory.getLog(this.getClass());

	public List<Metadata> extractMetadata(File root) throws InterruptedException, TooMuchFilesException, CannotReadException, CannotReadException {
		metadataList = new ArrayList<Metadata>();
		filesWithoutMinimumMetadata = metadataHelper.createHashSet();
		List<File> fileList = fileUtils.getFileList(root);
		if(fileList.size() > Integer.parseInt(properties.getProperty("max.files.allowed"))){
			throw new TooMuchFilesException(fileList.size());
		}
		configurator.getControlEngine().set(Model.FILES_WITHOUT_MINIMUM_METADATA, filesWithoutMinimumMetadata, null);
		return getMetadataList(fileList);
	}

	private List<Metadata> getMetadataList(List<File> fileList) throws CannotReadException {
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
