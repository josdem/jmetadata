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

package org.jas.model;

import java.io.File;
import java.awt.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Metadata implements Comparable<Metadata>{
	private String title;
	private String artist;
	private String album;
	private String genre;
	private String trackNumber;
	private String totalTracks;
	private Image coverArt;
	private int length;
	private int bitRate;
	private File file;
	private String cdNumber;
	private String totalCds;
	private String year;
	private CoverArt newCoverArt;
	private boolean metadataFromFile;
	private boolean orderByFile = false;

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public File getFile() {
		return file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void setLenght(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}
	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}

	public int getBitRate() {
		return bitRate;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getTotalTracks() {
		return totalTracks;
	}
	public void setTotalTracks(String totalTracks) {
		this.totalTracks = totalTracks;
	}
	public void setCdNumber(String cdNumber) {
		this.cdNumber = cdNumber;
	}
	public String getCdNumber() {
		return cdNumber;
	}
	public void setTotalCds(String totalCds) {
		this.totalCds = totalCds;
	}
	public String getTotalCds() {
		return totalCds;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYear() {
		return year;
	}
	public Image getCoverArt() {
		return coverArt;
	}
	public void setCoverArt(Image artwork) {
		this.coverArt = artwork;
	}
	public void setNewCoverArt(CoverArt coverArt) {
		this.newCoverArt = coverArt;
	}
	public CoverArt getNewCoverArt() {
		return newCoverArt;
	}
	public boolean isMetadataFromFile() {
		return metadataFromFile ;
	}
	public void setMetadataFromFile(boolean metadataFromFile) {
		this.metadataFromFile = metadataFromFile;
	}

	public boolean isOrderByFile() {
		return orderByFile;
	}

	public void setOrderByFile(boolean orderByFile) {
		this.orderByFile = orderByFile;
	}

	public int compareTo(Metadata metadata) {
		if(metadata.isOrderByFile()){
			return getFile().getName().compareTo(metadata.getFile().getName());
		}
		try{
			int thisTrackNumer = Integer.valueOf(getTrackNumber());
			return thisTrackNumer > Integer.valueOf(metadata.getTrackNumber()) ? 1 : -1;
		} catch (NumberFormatException nfe){
			log.error("Metadata : " + metadata.getTitle() + " has an incorrect trackNumber: " + nfe.getMessage());
			return 0;
		}
	}
}
