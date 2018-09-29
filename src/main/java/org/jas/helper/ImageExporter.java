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

package org.jas.helper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;

import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jas.model.Metadata;
import org.jas.util.ImageUtils;
import org.jas.model.ExportPackage;
import org.jas.service.MetadataService;
import org.jas.metadata.MetadataException;

@Service
public class ImageExporter {

	@Autowired
	private MetadataService metadataService;

	private ImageUtils imageUtils = new ImageUtils();

	public void export(ExportPackage exportPackage) throws IOException, CannotReadException, TagException, ReadOnlyFileException, MetadataException {
		List<Metadata> metadataList = exportPackage.getMetadataList();
		if(metadataList.get(0).getCoverArt() == null){
			return;
		}
		File root = exportPackage.getRoot();
		if (metadataService.isSameAlbum(metadataList)){
			imageUtils.saveCoverArtToFile(metadataList.get(0).getCoverArt(), root, StringUtils.EMPTY);
		} else {
			for (Metadata metadata : metadataList) {
				imageUtils.saveCoverArtToFile(metadata.getCoverArt(), root, metadata.getArtist() + "-" + metadata.getTitle());
			}
		}
	}
}
