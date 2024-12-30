/*
   Copyright 2024 Jose Morales contact@josdem.io

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

import de.umass.lastfm.Album;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.josdem.jmetadata.Auth;
import com.josdem.jmetadata.model.GenreTypes;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Slf4j
@Component
public class LastFMAlbumHelper {

    public Album getAlbum(String artist, String album) {
        return Album.getInfo(artist, album, Auth.KEY);
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