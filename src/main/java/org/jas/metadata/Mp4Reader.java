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

package org.jas.metadata;

import java.io.File;
import java.io.IOException;

import org.jas.exception.MetadataException;
import org.jas.helper.AudioFileHelper;
import org.jas.model.Metadata;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author josdem (joseluis.delacruz@gmail.com)
* @undestands This class knows how to read metadata from a m4a file
*/

@Service
public class Mp4Reader extends MetadataReader {

	@Autowired
	private AudioFileHelper audioFileHelper;

	public Metadata getMetadata(File file) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException {
		try{
			AudioFile audioFile = audioFileHelper.read(file);
			tag = (Mp4Tag) audioFile.getTag();
			header = audioFile.getAudioHeader();
			return generateMetadata(file);
		} catch (CannotReadException cnr){
			return null;
		}
	}

	@Override
	public String getGenre() {
		return tag.getFirst(FieldKey.GENRE);
	}
}
