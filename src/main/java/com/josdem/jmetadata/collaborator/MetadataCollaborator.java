/*
   Copyright 2026 Jose Morales contact@josdem.io

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

import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.model.Metadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class MetadataCollaborator {

  private List<Metadata> metadataList;

  public String getArtist() {
    metadataList.stream()
        .filter(metadata -> metadata.getArtist() == null)
        .findAny()
        .ifPresent(
            it -> {
              throw new BusinessException("Invalid artist");
            });

    return metadataList.stream().map(Metadata::getArtist).distinct().count() <= 1
        ? metadataList.getFirst().getArtist()
        : StringUtils.EMPTY;
  }

  public String getAlbum() {
    metadataList.stream()
        .filter(metadata -> metadata.getAlbum() == null)
        .findAny()
        .ifPresent(
            it -> {
              throw new BusinessException("Invalid album");
            });
    return metadataList.stream().map(Metadata::getAlbum).distinct().count() <= 1
        ? metadataList.getFirst().getAlbum()
        : StringUtils.EMPTY;
  }

  public String getGenre() {
    metadataList.stream()
        .filter(metadata -> metadata.getGenre() == null)
        .findAny()
        .ifPresent(
            it -> {
              throw new BusinessException("Invalid Genre");
            });
    return metadataList.stream().map(Metadata::getGenre).distinct().count() <= 1
        ? metadataList.getFirst().getGenre()
        : StringUtils.EMPTY;
  }

  public String getYear() {
    metadataList.stream()
        .filter(metadata -> metadata.getYear() == null)
        .findAny()
        .ifPresent(
            it -> {
              throw new BusinessException("Invalid Year");
            });
    return metadataList.stream().map(Metadata::getYear).distinct().count() <= 1
        ? metadataList.getFirst().getYear()
        : StringUtils.EMPTY;
  }

  public String getTotalTracks() {
    metadataList.stream()
        .filter(metadata -> metadata.getTotalTracks() == null)
        .findAny()
        .ifPresent(
            it -> {
              throw new BusinessException("Invalid total tracks");
            });
    return metadataList.stream().map(Metadata::getTotalTracks).distinct().count() <= 1
        ? metadataList.getFirst().getTotalTracks()
        : StringUtils.EMPTY;
  }

  public String getTotalCds() {
    var cds = new ArrayList<>();
    metadataList.forEach(
        metadata ->
            cds.add(Objects.requireNonNull(metadata.getTotalCds(), "totalCds cannot be null")));
    return cds.stream().distinct().count() <= 1
        ? metadataList.getFirst().getTotalCds()
        : StringUtils.EMPTY;
  }

  public String getCdNumber() {
    var cdNumber = new ArrayList<>();
    metadataList.forEach(
        metadata ->
            cdNumber.add(
                Objects.requireNonNull(metadata.getCdNumber(), "cdNumber cannot be null")));
    return cdNumber.stream().distinct().count() <= 1
        ? metadataList.getFirst().getCdNumber()
        : StringUtils.EMPTY;
  }
}
