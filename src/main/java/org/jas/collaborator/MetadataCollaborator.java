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

package org.jas.collaborator;

import org.apache.commons.lang3.StringUtils;
import org.jas.model.Metadata;

import java.util.ArrayList;
import java.util.List;

public class MetadataCollaborator {

    private List<Metadata> metadatas;

    public void setMetadatas(List<Metadata> metadatas) {
        this.metadatas = metadatas;
    }

    public String getArtist() {
        var artists = new ArrayList<>();
        metadatas.forEach(metadata -> artists.add(metadata.getArtist()));
        return artists.stream().distinct().count() <= 1 && metadatas.getFirst().getArtist() != null ? metadatas.getFirst().getArtist() : StringUtils.EMPTY;
    }

    public String getAlbum() {
        var albums = new ArrayList<>();
        metadatas.forEach(metadata -> albums.add(metadata.getAlbum()));
        return albums.stream().distinct().count() <= 1 && metadatas.getFirst().getAlbum() != null ? metadatas.getFirst().getAlbum() : StringUtils.EMPTY;
    }

    public String getGenre() {
        var genres = new ArrayList<>();
        metadatas.forEach(metadata -> genres.add(metadata.getGenre()));
        return genres.stream().distinct().count() <= 1 && metadatas.getFirst().getGenre() != null ? metadatas.getFirst().getGenre() : StringUtils.EMPTY;
    }

    public String getYear() {
        var years = new ArrayList<>();
        metadatas.forEach(metadata -> years.add(metadata.getYear()));
        return years.stream().distinct().count() <= 1 && metadatas.getFirst().getYear() != null ? metadatas.getFirst().getYear() : StringUtils.EMPTY;
    }

    public String getTotalTracks() {
        var tracks = new ArrayList<>();
        metadatas.forEach(metadata -> tracks.add(metadata.getTotalTracks()));
        return tracks.stream().distinct().count() <= 1 && metadatas.getFirst().getTotalTracks() != null ? metadatas.getFirst().getTotalTracks() : StringUtils.EMPTY;
    }

    public String getTotalCds() {
        var cds = new ArrayList<>();
        metadatas.forEach(metadata -> cds.add(metadata.getTotalCds()));
        return cds.stream().distinct().count() <= 1 && metadatas.getFirst().getTotalCds() != null ? metadatas.getFirst().getTotalCds() : StringUtils.EMPTY;
    }

    public String getCdNumber() {
        var cdNumber = new ArrayList<>();
        metadatas.forEach(metadata -> cdNumber.add(metadata.getCdNumber()));
        return cdNumber.stream().distinct().count() <= 1 && metadatas.getFirst().getCdNumber() != null ? metadatas.getFirst().getCdNumber() : StringUtils.EMPTY;
    }

}
