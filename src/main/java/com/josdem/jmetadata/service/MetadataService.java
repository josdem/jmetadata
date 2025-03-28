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

package com.josdem.jmetadata.service;

import com.josdem.jmetadata.exception.InvalidId3VersionException;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.exception.TooMuchFilesException;
import com.josdem.jmetadata.model.Metadata;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public interface MetadataService {

  List<Metadata> extractMetadata(File root)
      throws IOException,
          InterruptedException,
          TooMuchFilesException,
          CannotReadException,
          TagException,
          ReadOnlyFileException,
          InvalidAudioFrameException,
          MetadataException,
          InvalidId3VersionException;

  boolean isSameAlbum(List<Metadata> metadatas);

  boolean isSameArtist(List<Metadata> metadatas);
}
