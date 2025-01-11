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

package com.josdem.jmetadata.service.impl;

import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.exception.TooMuchFilesException;
import com.josdem.jmetadata.helper.MetadataHelper;
import com.josdem.jmetadata.metadata.MetadataReader;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.service.ExtractService;
import com.josdem.jmetadata.service.MetadataService;
import com.josdem.jmetadata.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetadataServiceImpl implements MetadataService {

  @Autowired private ControlEngineConfigurator configurator;
  @Autowired private MetadataHelper metadataHelper;
  @Autowired private ExtractService extractService;
  @Autowired private MetadataReader mp3Reader;
  @Autowired private FileUtils fileUtils;
  @Autowired private Properties properties;

  private List<Metadata> metadataList;
  private Set<File> filesWithoutMinimumMetadata;

  public List<Metadata> extractMetadata(File root)
      throws IOException,
          TooMuchFilesException,
          CannotReadException,
          TagException,
          ReadOnlyFileException {
    metadataList = new ArrayList<>();
    filesWithoutMinimumMetadata = metadataHelper.createHashSet();
    List<File> fileList = fileUtils.getFileList(root);
    log.info("properties: {}", properties.getProperty("max.files.allowed"));
    if (fileList.size() > Integer.parseInt(properties.getProperty("max.files.allowed"))) {
      throw new TooMuchFilesException(fileList.size());
    }
    configurator
        .getControlEngine()
        .set(Model.FILES_WITHOUT_MINIMUM_METADATA, filesWithoutMinimumMetadata, null);
    return getMetadataList(fileList);
  }

  private List<Metadata> getMetadataList(List<File> fileList)
      throws IOException,
          CannotReadException,
          TagException,
          ReadOnlyFileException {
    for (File file : fileList) {
      log.info("Reading file: {}", file.getName());
      Metadata metadata = null;
      if (fileUtils.isMp3File(file)) {
        metadata = mp3Reader.getMetadata(file);
      } else if (fileUtils.isM4aFile(file)) {
        log.info("{} not supported audio File", file.getAbsoluteFile());
      }
      if (metadata == null) {
        log.info("{} is not a valid audio File", file.getAbsoluteFile());
      } else if (StringUtils.isNotEmpty(metadata.getArtist())
          && StringUtils.isNotEmpty(metadata.getTitle())) {
        metadataList.add(metadata);
      } else {
        metadataList.add(extractService.extractFromFileName(file));
        filesWithoutMinimumMetadata.add(file);
      }
    }
    return metadataList;
  }

  public boolean isSameAlbum(List<Metadata> metadatas) {
    var albums = new ArrayList<String>();
    metadatas.forEach(
        metadata ->
            albums.add(Objects.requireNonNull(metadata.getAlbum(), "album cannot be null")));
    return albums.stream().distinct().count() == 1
        && !metadatas.getFirst().getAlbum().equals(StringUtils.EMPTY);
  }

  public boolean isSameArtist(List<Metadata> metadatas) {
    var artists = new ArrayList<String>();
    metadatas.forEach(
        metadata ->
            artists.add(Objects.requireNonNull(metadata.getArtist(), "artist cannot be null")));
    return artists.stream().distinct().count() == 1
        && !metadatas.getFirst().getArtist().equals(StringUtils.EMPTY);
  }
}
