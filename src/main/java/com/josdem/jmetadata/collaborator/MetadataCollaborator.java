/*
   Copyright 2014 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.collaborator;

import org.apache.commons.lang3.StringUtils;
import com.josdem.jmetadata.model.Metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MetadataCollaborator {

    private List<Metadata> metadatas;

    public void setMetadatas(List<Metadata> metadatas) {
        this.metadatas = metadatas;
    }

    public String getArtist() {
        var artists = new ArrayList<>();
        metadatas.forEach(metadata -> artists.add(Objects.requireNonNull(metadata.getArtist(), "artist cannot be null")));
        return artists.stream().distinct().count() <= 1 ? metadatas.getFirst().getArtist() : StringUtils.EMPTY;
    }

    public String getAlbum() {
        var albums = new ArrayList<>();
        metadatas.forEach(metadata -> albums.add(Objects.requireNonNull(metadata.getAlbum(), "album cannot be null")));
        return albums.stream().distinct().count() <= 1 ? metadatas.getFirst().getAlbum() : StringUtils.EMPTY;
    }

    public String getGenre() {
        var genres = new ArrayList<>();
        metadatas.forEach(metadata -> genres.add(Objects.requireNonNull(metadata.getGenre(), "genre cannot be null")));
        return genres.stream().distinct().count() <= 1 ? metadatas.getFirst().getGenre() : StringUtils.EMPTY;
    }

    public String getYear() {
        var years = new ArrayList<>();
        metadatas.forEach(metadata -> years.add(Objects.requireNonNull(metadata.getYear(), "year cannot be null")));
        return years.stream().distinct().count() <= 1 ? metadatas.getFirst().getYear() : StringUtils.EMPTY;
    }

    public String getTotalTracks() {
        var tracks = new ArrayList<>();
        metadatas.forEach(metadata -> tracks.add(Objects.requireNonNull(metadata.getTotalTracks(), "totalTracks cannot be null")));
        return tracks.stream().distinct().count() <= 1 ? metadatas.getFirst().getTotalTracks() : StringUtils.EMPTY;
    }

    public String getTotalCds() {
        var cds = new ArrayList<>();
        metadatas.forEach(metadata -> cds.add(Objects.requireNonNull(metadata.getTotalCds(), "totalCds cannot be null")));
        return cds.stream().distinct().count() <= 1 ? metadatas.getFirst().getTotalCds() : StringUtils.EMPTY;
    }

    public String getCdNumber() {
        var cdNumber = new ArrayList<>();
        metadatas.forEach(metadata -> cdNumber.add(Objects.requireNonNull(metadata.getCdNumber(), "cdNumber cannot be null")));
        return cdNumber.stream().distinct().count() <= 1 ? metadatas.getFirst().getCdNumber() : StringUtils.EMPTY;
    }

}
