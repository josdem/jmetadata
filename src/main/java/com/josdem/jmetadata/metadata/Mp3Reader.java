/*
   Copyright 2025 Jose Morales contact@josdem.io

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
import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.helper.AudioFileHelper;
import com.josdem.jmetadata.helper.ReaderHelper;
import com.josdem.jmetadata.model.GenreTypes;
import com.josdem.jmetadata.model.Metadata;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Mp3Reader implements MetadataReader {
  private static final String NULL = "null";

  @Autowired private AudioFileHelper audioFileHelper;
  @Autowired private ReaderHelper readerHelper;
  @Autowired private JAudioTaggerCollaborator jAudioTaggerCollaborator;
  @Autowired protected ControlEngineConfigurator configurator;

  protected Tag tag;
  protected AudioHeader header;

  public Metadata getMetadata(File file)
      throws CannotReadException, IOException, TagException, ReadOnlyFileException {
    AudioFile audioFile;
    try {
      audioFile = audioFileHelper.read(file);
    } catch (InvalidAudioFrameException ina) {
      throw new BusinessException("Invalid Audio Frame Exception: " + ina.getMessage());
    } catch (FileNotFoundException fnf) {
      log.error("File: {} Not found", file.getAbsolutePath());
      configurator
          .getControlEngine()
          .fireEvent(Events.LOAD_FILE, new ValueEvent<>(file.getAbsolutePath()));
      throw new BusinessException("Invalid Audio Frame Exception: " + fnf.getMessage());
    }
    if (audioFile instanceof MP3File audioMP3) {
      if (!audioMP3.hasID3v2Tag()) {
        AbstractID3v2Tag id3v2tag = new ID3v24Tag();
        audioMP3.setID3v2TagOnly(id3v2tag);
        try {
          audioFile.commit();
        } catch (CannotWriteException cwe) {
          throw new BusinessException(
              "An error occurs when I tried to update to ID3 v2" + cwe.getMessage());
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
      int index = Integer.parseInt(tmpGenre);
      return GenreTypes.getGenreByCode(index);
    } catch (NumberFormatException nfe) {
      return readerHelper.getGenre(tag, tmpGenre);
    }
  }

  private String getArtist() {
    return tag.getFirst(FieldKey.ARTIST);
  }

  private String getTitle() {
    return tag.getFirst(FieldKey.TITLE);
  }

  private String getAlbum() {
    return tag.getFirst(FieldKey.ALBUM);
  }

  private String getYear() {
    return tag.getFirst(FieldKey.YEAR);
  }

  private int getLength() {
    return header.getTrackLength();
  }

  private int getBitRate() {
    var bitRate = header.getBitRate().replace("~", "");
    return Integer.parseInt(bitRate);
  }

  private String getTrackNumber() {
    try {
      var trackNumber = tag.getFirst(FieldKey.TRACK);
      return trackNumber.equals(NULL) ? StringUtils.EMPTY : trackNumber;
    } catch (NullPointerException nue) {
      log.warn("NullPointer Exception in getting TrackNumber at: {}", getTitle());
      return StringUtils.EMPTY;
    }
  }

  private String getTotalTracks() {
    try {
      var totalTracks = tag.getFirst(FieldKey.TRACK_TOTAL);
      return totalTracks.equals(NULL) ? StringUtils.EMPTY : totalTracks;
    } catch (NullPointerException nue) {
      log.warn("NullPointer Exception in getting Total Tracks at: {}", getTitle());
      return StringUtils.EMPTY;
    }
  }

  private String getCdNumber() {
    try {
      var cdNumber = tag.getFirst(FieldKey.DISC_NO);
      return cdNumber.equals(NULL) ? StringUtils.EMPTY : cdNumber;
    } catch (NullPointerException nue) {
      log.warn("NullPointer Exception in getting CD Number at: {}", getTitle());
      return StringUtils.EMPTY;
    }
  }

  private String getTotalCds() {
    try {
      var cdsTotal = tag.getFirst(FieldKey.DISC_TOTAL);
      return cdsTotal.equals(NULL) ? StringUtils.EMPTY : cdsTotal;
    } catch (NullPointerException nue) {
      log.warn("NullPointer Exception in getting Total CDs Number at: {}", getTitle());
      return StringUtils.EMPTY;
    }
  }

  /** TODO: Bug in JAudioTagger null pointer exception when artwork.getImage() */
  private Image getCoverArt(Metadata metadata) {
    try {
      if (tag == null) return null;
      Artwork artwork = tag.getFirstArtwork();
      log.info("{} has cover art?: {}", getTitle(), artwork != null);
      return artwork == null ? null : artwork.getImage();
    } catch (IllegalArgumentException | IOException | NullPointerException iae) {
      return handleCoverArtException(metadata, iae);
    }
  }

  private Image handleCoverArtException(Metadata metadata, Exception exc) {
    log.info(
        "couldn't get coverArt for file: {} with error: {}", metadata.getTitle(), exc.getMessage());
    configurator.getControlEngine().fireEvent(Events.LOAD_COVER_ART, new ValueEvent<>(getTitle()));
    return null;
  }

  protected Metadata generateMetadata(File file) {
    Metadata metadata = new Metadata();
    metadata.setCoverArt(getCoverArt(metadata));
    metadata.setTitle(getTitle());
    metadata.setArtist(getArtist());
    metadata.setAlbum(getAlbum());
    metadata.setGenre(getGenre());
    metadata.setYear(getYear());
    metadata.setLength(getLength());
    metadata.setTrackNumber(getTrackNumber());
    metadata.setTotalTracks(getTotalTracks());
    metadata.setCdNumber(getCdNumber());
    metadata.setTotalCds(getTotalCds());
    metadata.setBitRate(getBitRate());
    metadata.setFile(file);
    return metadata;
  }
}
