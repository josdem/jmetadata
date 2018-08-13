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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.event.Events;
import org.jas.model.Metadata;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author josdem (joseluis.delacruz@gmail.com)
* @undestands A class who gather all common methods getting Metadata
*/

@Service
public abstract class MetadataReader {
	private static final String NULL = "null";
	protected Tag tag;
	protected AudioHeader header;
	protected Log log = LogFactory.getLog(this.getClass());

	@Autowired
	protected ControlEngineConfigurator configurator;

	public abstract String getGenre();
	public abstract Metadata getMetadata(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException;

	public MetadataReader() {
		turnOffLogMessages();
	}

	private void turnOffLogMessages() {
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(Level.OFF);
		}
	}

	private String getArtist()
		return tag.getFirst(FieldKey.ARTIST);
	}

	private String getTitle(){
		return tag.getFirst(FieldKey.TITLE);
	}

	private String getAlbum(){
		return tag.getFirst(FieldKey.ALBUM);
	}

	private String getYear() {
		return tag.getFirst(FieldKey.YEAR);
	}

	private int getLength(){
		return header.getTrackLength();
	}

	private int getBitRate() {
		// Case variable bitRate
		String bitRate = header.getBitRate().replace("~", "");
		return Integer.parseInt(bitRate);
	}

	private String getTrackNumber(){
		try{
			String trackNumber = tag.getFirst(FieldKey.TRACK);
			return trackNumber == NULL ? StringUtils.EMPTY : trackNumber;
		} catch (NullPointerException nue){
			log.warn("NullPointer Exception in getting TrackNumber at: " + getTitle());
			return StringUtils.EMPTY;
		}
	}
	
	private String getTotalTracks(){
		try{
			String totalTracks = tag.getFirst(FieldKey.TRACK_TOTAL);
			return totalTracks == NULL ? StringUtils.EMPTY : totalTracks;
		} catch(NullPointerException nue){
			log.warn("NullPointer Exception in getting Total Tracks at: " + getTitle());
			return StringUtils.EMPTY;
		}
	}
	
	private String getCdNumber() {
		try{
			String cdNumber = tag.getFirst(FieldKey.DISC_NO);
			return cdNumber == NULL ? StringUtils.EMPTY : cdNumber;
		} catch (NullPointerException nue){
			log.warn("NullPointer Exception in getting CD Number at: " + getTitle());
			return StringUtils.EMPTY;
		}
	}

	private String getTotalCds() {
		try{
			String cdsTotal = tag.getFirst(FieldKey.DISC_TOTAL);
			return cdsTotal == NULL ? StringUtils.EMPTY : cdsTotal;
		} catch (NullPointerException nue){
			log.warn("NullPointer Exception in getting Total CDs Number at: " + getTitle());
			return StringUtils.EMPTY;
		}
	}

	/**
	 * TODO: Bug in JAudioTagger null pointer exception when artwork.getImage()
	 */
	private Image getCoverArt(Metadata metadata) throws MetadataException {
		try{
			if(tag == null) return null;
			Artwork artwork = tag.getFirstArtwork();
			log.info(getTitle() + " has cover art?: " + (artwork != null));
			return artwork==null ? null: artwork.getImage();
		} catch(IllegalArgumentException iae){
			return handleCoverArtException(metadata, iae);
		} catch (IOException ioe){
			return handleCoverArtException(metadata, ioe);
		} catch (NullPointerException nue){
			return handleCoverArtException(metadata, nue);
		}
	}

	private Image handleCoverArtException(Metadata metadata, Exception exc) {
		log.info("couldn't get coverArt for file: " + metadata.getTitle());
		log.error("Exception: " + exc.getMessage());
		configurator.getControlEngine().fireEvent(Events.LOAD_COVER_ART, new ValueEvent<String>(getTitle()));
		return null;
	}

	protected Metadata generateMetadata(File file) throws IOException, MetadataException {
		Metadata metadata = new Metadata();
		metadata.setCoverArt(getCoverArt(metadata));
		metadata.setTitle(getTitle());
		metadata.setArtist(getArtist());
		metadata.setAlbum(getAlbum());
		metadata.setGenre(getGenre());
		metadata.setYear(getYear());
		metadata.setLenght(getLength());
		metadata.setTrackNumber(getTrackNumber());
		metadata.setTotalTracks(getTotalTracks());
		metadata.setCdNumber(getCdNumber());
		metadata.setTotalCds(getTotalCds());
		metadata.setBitRate(getBitRate());
		metadata.setFile(file);
		return metadata;
	}

}
