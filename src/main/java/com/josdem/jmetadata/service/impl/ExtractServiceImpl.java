/*
   Copyright 2014 Jose Morales contact@josdem.io

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

import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.ExtractService;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExtractServiceImpl implements ExtractService {

  public Metadata extractFromFileName(File file) {
    String titleComplete = file.getName();
    Metadata metadata = new Metadata();
    metadata.setFile(file);
    try {
      StringTokenizer stringTokenizer = new StringTokenizer(titleComplete, "-");
      String artist = stringTokenizer.nextToken().trim();
      String title = removeExtension(stringTokenizer.nextToken().trim());
      metadata.setArtist(artist);
      metadata.setTitle(title);
    } catch (NoSuchElementException nue) {
      String uniqueName = removeExtension(titleComplete);
      metadata.setArtist(uniqueName);
      metadata.setTitle(uniqueName);
      log.info(titleComplete, "{} has no next token");
    }
    metadata.setMetadataFromFile(true);
    return metadata;
  }

  private String removeExtension(String name) {
    int extensionIndex = name.lastIndexOf(".");
    return extensionIndex == -1 ? name : name.substring(0, extensionIndex);
  }
}
