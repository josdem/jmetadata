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

package com.josdem.jmetadata.metadata;

import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.helper.ArtworkHelper;
import com.josdem.jmetadata.helper.AudioFileHelper;
import com.josdem.jmetadata.util.ImageUtils;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetadataWriter {
  private Tag tag;
  private AudioFile audioFile;

  private final ImageUtils imageUtils;
  private final AudioFileHelper audioFileIOHelper;
  private final ArtworkHelper artworkHelper;

  public void setFile(File file) {
    try {
      audioFile = audioFileIOHelper.read(file);
      tag = audioFile.getTag();
    } catch (CannotReadException
        | IOException
        | TagException
        | ReadOnlyFileException
        | InvalidAudioFrameException nre) {
      log.error(nre.getMessage(), nre);
    }
  }

  public void writeArtist(String artist) {
    try {
      tag.setField(FieldKey.ARTIST, artist);
      audioFile.commit();
    } catch (KeyNotFoundException | FieldDataInvalidException | CannotWriteException kne) {
      throw new BusinessException(kne.getMessage());
    }
  }

  public void writeTitle(String trackName) {
    try {
      tag.setField(FieldKey.TITLE, trackName);
      audioFile.commit();
    } catch (KeyNotFoundException | FieldDataInvalidException | CannotWriteException kne) {
      throw new BusinessException(kne.getMessage());
    }
  }

  public boolean writeAlbum(String album) throws MetadataException {
    try {
      tag.setField(FieldKey.ALBUM, album);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException | FieldDataInvalidException | CannotWriteException kne) {
      throw new BusinessException(kne.getMessage());
    }
  }

  public boolean writeTrackNumber(String trackNumber) throws MetadataException {
    try {
      if (StringUtils.isEmpty(trackNumber)) {
        return false;
      }
      tag.setField(FieldKey.TRACK, trackNumber);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException | FieldDataInvalidException | CannotWriteException kne) {
      throw new BusinessException(kne.getMessage());
    }
  }

  public boolean writeTotalTracksNumber(String totalTracksNumber) throws MetadataException {
    try {
      if (StringUtils.isEmpty(totalTracksNumber)) {
        return false;
      }
      tag.setField(FieldKey.TRACK_TOTAL, totalTracksNumber);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException | FieldDataInvalidException | CannotWriteException kne) {
      throw new BusinessException(kne.getMessage());
    }
  }

  public void writeCoverArt(Image lastfmCoverArt) throws MetadataException {
    try {
      File coverArtFile = imageUtils.saveCoverArtToFile(lastfmCoverArt);
      Artwork artwork = artworkHelper.createArtwork();
      artwork.setFromFile(coverArtFile);
      tag.setField(artwork);
      audioFile.commit();
    } catch (KeyNotFoundException
        | FieldDataInvalidException
        | CannotWriteException
        | IOException
        | NullPointerException kne) {
      throw new MetadataException(kne.getMessage());
    }
  }

  public boolean removeCoverArt() throws MetadataException {
    try {
      tag.deleteArtworkField();
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException | CannotWriteException kne) {
      throw new MetadataException(kne.getMessage());
    }
  }

  public boolean writeCdNumber(String cdNumber) throws MetadataException {
    try {
      if (StringUtils.isEmpty(cdNumber)) {
        return false;
      }
      tag.setField(FieldKey.DISC_NO, cdNumber);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException
        | FieldDataInvalidException
        | CannotWriteException
        | NullPointerException kne) {
      throw new MetadataException(kne.getMessage());
    }
  }

  public boolean writeTotalCds(String totalCds) throws MetadataException {
    try {
      if (StringUtils.isEmpty(totalCds)) {
        return false;
      }
      tag.setField(FieldKey.DISC_TOTAL, totalCds);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException
        | FieldDataInvalidException
        | CannotWriteException
        | NullPointerException kne) {
      throw new MetadataException(kne.getMessage());
    }
  }

  public boolean writeYear(String year) throws MetadataException {
    try {
      if (StringUtils.isEmpty(year)) {
        return false;
      }
      tag.setField(FieldKey.YEAR, year);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException
        | FieldDataInvalidException
        | CannotWriteException
        | NullPointerException kne) {
      throw new MetadataException(kne.getMessage());
    }
  }

  public boolean writeGenre(String genre) throws MetadataException {
    try {
      if (StringUtils.isEmpty(genre)) {
        return false;
      }
      tag.setField(FieldKey.GENRE, genre);
      audioFile.commit();
      return true;
    } catch (KeyNotFoundException
        | FieldDataInvalidException
        | CannotWriteException
        | NullPointerException kne) {
      throw new MetadataException(kne.getMessage());
    }
  }
}
