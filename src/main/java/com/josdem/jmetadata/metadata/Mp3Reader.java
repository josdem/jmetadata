/*
   Copyright 2024 Jose Morales contact@josdem.io

   Licensed under the Apache License, Version 2.0 (the "License");
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

package com.josdem.jmetadata.metadata;

import com.josdem.jmetadata.collaborator.JAudioTaggerCollaborator;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.helper.AudioFileHelper;
import com.josdem.jmetadata.helper.ReaderHelper;
import com.josdem.jmetadata.model.GenreTypes;
import com.josdem.jmetadata.model.Metadata;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.event.ValueEvent;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Mp3Reader extends MetadataReader {
  @Autowired private AudioFileHelper audioFileHelper;
  @Autowired private ReaderHelper readerHelper;
  @Autowired private JAudioTaggerCollaborator jAudioTaggerCollaborator;

  private AudioFile audioFile;

  public Metadata getMetadata(File file)
      throws CannotReadException,
          IOException,
          TagException,
          ReadOnlyFileException,
          MetadataException {
    try {
      audioFile = audioFileHelper.read(file);
    } catch (InvalidAudioFrameException ina) {
      return null;
    } catch (FileNotFoundException fnf) {
      log.error("File: " + file.getAbsolutePath() + " Not found");
      configurator
          .getControlEngine()
          .fireEvent(Events.LOAD_FILE, new ValueEvent<String>(file.getAbsolutePath()));
      return null;
    }
    if (audioFile instanceof MP3File) {
      MP3File audioMP3 = (MP3File) audioFile;
      if (!audioMP3.hasID3v2Tag()) {
        AbstractID3v2Tag id3v2tag = new ID3v24Tag();
        audioMP3.setID3v2TagOnly(id3v2tag);
        try {
          audioFile.commit();
        } catch (CannotWriteException cwe) {
          log.error("An error occurs when I tried to update to ID3 v2");
          cwe.printStackTrace();
        }
      }
      tag = audioFile.getTag();
      header = audioFile.getAudioHeader();
      if (jAudioTaggerCollaborator.isValid(tag, header)) {
        return generateMetadata(file);
      }
    }
    return new Metadata();
  }

  @Override
  public String getGenre() {
    String tmpGenre = tag.getFirst(FieldKey.GENRE);
    try {
      int index = Integer.valueOf(tmpGenre);
      return GenreTypes.getGenreByCode(index);
    } catch (NumberFormatException nfe) {
      return readerHelper.getGenre(tag, tmpGenre);
    }
  }
}
