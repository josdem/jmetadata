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

import java.util.List;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jas.model.Metadata;
import org.jas.metadata.MetadataException;

@Service
public class DefaultService {

	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";

	@Autowired
	private MetadataService metadataService;
	private Log log = LogFactory.getLog(getClass());

	public Boolean isCompletable(List<Metadata> metadatas) throws IOException, CannotReadException, TagException, ReadOnlyFileException, MetadataException {
		return (metadatas.size() < 2 || !metadataService.isSameAlbum(metadatas)) ? false : isSomethingMissing(metadatas);
	}

	public void complete(List<Metadata> metadatas){
		String title = StringUtils.EMPTY;
		try{
			for (Metadata metadata : metadatas) {
				title = metadata.getTitle();
				metadata.setTotalTracks(String.valueOf(getTotalTracks(metadatas)));
				metadata.setCdNumber(CD_NUMBER);
				metadata.setTotalCds(TOTAL_CD_NUMBER);
			}
		} catch (NumberFormatException nfe){
			log.warn("NumberFormatException caused by track: " + title + " - "  + nfe.getMessage());
		}
	}

	private boolean isSomethingMissing(List<Metadata> metadatas) {
		for (Metadata metadata : metadatas) {
			if(StringUtils.isEmpty(metadata.getTotalTracks()) || StringUtils.isEmpty(metadata.getCdNumber()) || StringUtils.isEmpty(metadata.getTotalCds())){
				return true;
			}
		}
		return false;
	}

	private int getTotalTracks(List<Metadata> metadatas) {
		int biggerTrackNumber = 0;
		for (Metadata metadata : metadatas) {
			Integer metadataTrackNumber = Integer.valueOf(metadata.getTrackNumber());
			if(metadataTrackNumber > biggerTrackNumber){
				biggerTrackNumber = metadataTrackNumber;
			}
		}
		return biggerTrackNumber;
	}

}
