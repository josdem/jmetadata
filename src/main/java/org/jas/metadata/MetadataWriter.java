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
import java.awt.Image;

import org.apache.commons.lang3.StringUtils;

import org.jas.exception.MetadataException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.KeyNotFoundException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;

import org.jas.util.ImageUtils;
import org.jas.helper.ArtworkHelper;
import org.jas.helper.AudioFileHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j

/**
* @understands A class who knows how to write metadata in a audio file
*/

public class MetadataWriter {
	private Tag tag;
	private AudioFile audioFile;
	private AudioFileHelper audioFileIOHelper = new AudioFileHelper();
	private ImageUtils imageUtils = new ImageUtils();
	private ArtworkHelper artworkHelper = new ArtworkHelper();


	public void setFile(File file) {
		try {
			audioFile = audioFileIOHelper.read(file);
			tag = audioFile.getTag();
		} catch (CannotReadException nre) {
			log.error(nre.getMessage(), nre);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		} catch (TagException tae) {
			log.error(tae.getMessage(), tae);
		} catch (ReadOnlyFileException roe) {
			log.error(roe.getMessage(), roe);
		} catch (InvalidAudioFrameException iae) {
			log.error(iae.getMessage(), iae);
		}
	}

	public void writeArtist(String artist) {
		try {
			tag.setField(FieldKey.ARTIST, artist);
			audioFile.commit();
		} catch (KeyNotFoundException kne) {
			log.error(kne.getMessage(), kne);
		} catch (FieldDataInvalidException fie) {
			log.error(fie.getMessage(), fie);
		} catch (CannotWriteException nwe) {
			log.error(nwe.getMessage(), nwe);
		}
	}

	public void writeTitle(String trackName) {
		try {
			tag.setField(FieldKey.TITLE, trackName);
			audioFile.commit();
		} catch (KeyNotFoundException kne) {
			log.error(kne.getMessage(), kne);
		} catch (FieldDataInvalidException fie) {
			log.error(fie.getMessage(), fie);
		} catch (CannotWriteException nwe) {
			log.error(nwe.getMessage(), nwe);
		}
	}

	public boolean writeAlbum(String album) throws MetadataException {
		try {
			tag.setField(FieldKey.ALBUM, album);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		}
	}

	public boolean writeTrackNumber(String trackNumber) throws MetadataException {
		try {
			if(StringUtils.isEmpty(trackNumber)){
				return false;
			}
			tag.setField(FieldKey.TRACK, trackNumber);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		}
	}

	public boolean writeTotalTracksNumber(String totalTracksNumber) throws MetadataException {
		try {
			if(StringUtils.isEmpty(totalTracksNumber)){
				return false;
			}
			tag.setField(FieldKey.TRACK_TOTAL, totalTracksNumber);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		}
	}

	public boolean writeCoverArt(Image lastfmCoverArt) throws MetadataException {
		try {
			File coverArtFile = imageUtils.saveCoverArtToFile(lastfmCoverArt);
			Artwork artwork = artworkHelper.createArtwork();
			artwork.setFromFile(coverArtFile);
			tag.setField(artwork);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		} catch (IOException ioe) {
			throw new MetadataException(ioe.getMessage());
		} catch (NullPointerException nue) {
			throw new MetadataException(nue.getMessage());
		}
	}

	public boolean removeCoverArt() throws MetadataException {
		try {
			tag.deleteArtworkField();
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		}
	}

	public boolean writeCdNumber(String cdNumber) throws MetadataException {
		try {
			if(StringUtils.isEmpty(cdNumber)){
				return false;
			}
			tag.setField(FieldKey.DISC_NO, cdNumber);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		} catch (NullPointerException nue){
			throw new MetadataException(nue.getMessage());
		}
	}

	public boolean writeTotalCds(String totalCds) throws MetadataException {
		try {
			if(StringUtils.isEmpty(totalCds)){
				return false;
			}
			tag.setField(FieldKey.DISC_TOTAL, totalCds);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		} catch (NullPointerException nue){
			throw new MetadataException(nue.getMessage());
		}
	}

	public boolean writeYear(String year) throws MetadataException {
		try {
			if(StringUtils.isEmpty(year)){
				return false;
			}
			tag.setField(FieldKey.YEAR, year);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		} catch (NullPointerException nue){
			throw new MetadataException(nue.getMessage());
		}
	}

	public boolean writeGenre(String genre) throws MetadataException {
		try {
			if(StringUtils.isEmpty(genre)){
				return false;
			}
			tag.setField(FieldKey.GENRE, genre);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		} catch (NullPointerException nue){
			throw new MetadataException(nue.getMessage());
		}
	}

}
