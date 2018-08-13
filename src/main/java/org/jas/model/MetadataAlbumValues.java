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

import java.awt.Image;

public class MetadataAlbumValues {
	private String genre;
	private String album;
	private String tracks;
	private String cd;
	private String cds;
	private Image coverArt;
	private String year;
	private String artist;

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbum() {
		return album;
	}

	public void setTracks(String tracks) {
		this.tracks = tracks;
	}

	public String getTracks() {
		return tracks;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCd() {
		return cd;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	public String getCds() {
		return cds;
	}

	public void setCoverart(Image coverArt) {
		this.coverArt = coverArt;
	}

	public Image getCoverArt() {
		return coverArt;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}

}
