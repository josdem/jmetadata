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

import java.io.File;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import org.jas.model.Metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExtractService {

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public Metadata extractFromFileName(File file) {
		String titleComplete = file.getName();
		Metadata metadata = new Metadata();
		metadata.setFile(file);
		try{
			StringTokenizer stringTokenizer = new StringTokenizer(titleComplete, "-");
			String artist = stringTokenizer.nextToken();
			String title = removeExtension(stringTokenizer.nextToken());
			metadata.setArtist(artist);
			metadata.setTitle(title);
		} catch (NoSuchElementException nue){
			String uniqueName = removeExtension(titleComplete);
			metadata.setArtist(uniqueName);
			metadata.setTitle(uniqueName);
			log.info(titleComplete + " has no slash format");
		}
		metadata.setMetadataFromFile(true);
		return metadata;
	}

	private String removeExtension(String name) {
		int extensionIndex = name.lastIndexOf(".");
		return extensionIndex == -1 ? name : name.substring(0, extensionIndex);
	}
}
