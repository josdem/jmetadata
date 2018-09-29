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
import java.io.IOException;
import java.util.List;

import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;

import org.jas.model.Metadata;
import org.jas.metadata.MetadataException;
import org.jas.exception.TooMuchFilesException;
import org.jas.exception.InvalidId3VersionException;

/**
* @understands A class who knows how extract metadata from files using a root directory
*/

public interface MetadataService {

  List<Metadata> extractMetadata(File root) throws IOException, InterruptedException, TooMuchFilesException, CannotReadException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException, InvalidId3VersionException ;
	boolean isSameAlbum(List<Metadata> metadatas) throws IOException, CannotReadException, TagException, ReadOnlyFileException, MetadataException ;

}
