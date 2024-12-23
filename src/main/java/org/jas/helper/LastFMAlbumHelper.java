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

import org.jas.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import de.umass.lastfm.Album;

import org.jas.config.LastFMConfig;
import org.jas.model.GenreTypes;

import lombok.extern.slf4j.Slf4j;

@Slf4j

/**
 * @understands A class who gets album information from LastFM
*/

@Component
public class LastFMAlbumHelper {

    @Autowired
    private LastFMConfig lastFMConfig;

    public Album getAlbum(String artist, String album) {
        String apiKey = lastFMConfig.getLastFMKey();
        if(!apiKey.equals(Auth.KEY)){
            throw new IllegalStateException("Environment variable 'LASTFM_API_KEY' is not valid");
        }
        return Album.getInfo(artist, album, apiKey);
    }

    public String getYear(Date releaseDate) {
        if (releaseDate == null) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String year = simpleDateFormat.format(releaseDate);
        log.info("Year: {}", year);
        return year;
    }

    public String getGenre(Album album) {
        Collection<String> tags = album.getTags();
        Iterator<String> iterator = tags.iterator();
        while (iterator.hasNext()) {
            String lastFmTag = iterator.next().toLowerCase();
            log.info("lastFmTag: {}", lastFmTag);
            if (GenreTypes.getGenreByName(lastFmTag) != GenreTypes.UNKNOWN) {
                log.info("lastFmTag matched in GenreTypes: {}", GenreTypes.getGenreByName(lastFmTag).getName());
                return GenreTypes.getGenreByName(lastFmTag).getName();
            }
        }
        return StringUtils.EMPTY;
    }
}