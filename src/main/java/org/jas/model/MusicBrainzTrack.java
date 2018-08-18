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

import org.apache.commons.lang3.StringUtils;

public class MusicBrainzTrack {
	private String album;
	private String trackNumber;
	private String totalTrackNumber;
	private String cdNumber;
	private String totalCds;

	public MusicBrainzTrack() {
		album = StringUtils.EMPTY;
		trackNumber = "0";
	}

	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getTotalTrackNumber() {
		return totalTrackNumber;
	}
	public void setTotalTrackNumber(String totalTrackNumber) {
		this.totalTrackNumber = totalTrackNumber;
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
}
