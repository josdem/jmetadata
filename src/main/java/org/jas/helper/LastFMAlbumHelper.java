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

package org.jas.helper;

import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import de.umass.lastfm.Album;

import org.jas.Auth;
import org.jas.model.GenreTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @understands A class who gets album information from LastFM
*/

@Component
public class LastFMAlbumHelper {
  private Logger log = LoggerFactory.getLogger(this.getClass());

	public Album getAlbum(String artist, String album) {
		return Album.getInfo(artist, album, Auth.KEY);
	}

	public String getYear(Date releaseDate) {
		if(releaseDate == null){
			return StringUtils.EMPTY;
		}
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		String year = simpleDateformat.format(releaseDate);
		log.info("Year: " + year);
		return year;
	}

	public String getGenre(Album album) {
		Collection<String> tags = album.getTags();
		Iterator<String> iterator = tags.iterator();
		while(iterator.hasNext()){
			String lastFmTag = (String) iterator.next().toLowerCase();
			log.info("lastFmTag: " + lastFmTag);
			if(GenreTypes.getGenreByName(lastFmTag) != GenreTypes.UNKNOWN){
				log.info("lastFmTag matched in GenreTypes: " + GenreTypes.getGenreByName(lastFmTag).getName());
				return GenreTypes.getGenreByName(lastFmTag).getName();
			}
		}
		return StringUtils.EMPTY;
	}

}
