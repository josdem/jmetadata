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

package com.josdem.jmetadata.helper;

import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.model.Metadata;

public class MetadataAdapter {

	public void update(Metadata metadata, int column, String value) {
		if(column == ApplicationConstants.ARTIST_COLUMN){
			metadata.setArtist(value);
		}
		if (column == ApplicationConstants.TITLE_COLUMN){
			metadata.setTitle(value);
		}
		if (column == ApplicationConstants.ALBUM_COLUMN){
			metadata.setAlbum(value);
		}
		if (column == ApplicationConstants.YEAR_COLUMN){
			metadata.setYear(value);
		}
		if (column == ApplicationConstants.TRACK_NUMBER_COLUMN){
			metadata.setTrackNumber(value);
		}
		if (column == ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN){
			metadata.setTotalTracks(value);
		}
		if (column == ApplicationConstants.CD_NUMBER_COLUMN){
			metadata.setCdNumber(value);
		}
		if (column == ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN){
			metadata.setTotalCds(value);
		}
		if (column == ApplicationConstants.GENRE_COLUMN){
			metadata.setGenre(value);
		}
	}

}
