/*
   Copyright 2025 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.service.impl;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.AlbumInfo;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.CompleteService;
import com.josdem.jmetadata.service.MetadataService;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.util.AlbumUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicBrainzCompleteServiceAdapter implements CompleteService {

  private final MusicBrainzService musicBrainzService;
  private final MetadataService metadataService;

  @Override
  public boolean canComplete(List<Metadata> metadataList) {
    return metadataService.isSameAlbum(metadataList) || metadataService.isSameArtist(metadataList);
  }

  @Override
  public AlbumInfo getInfo(Metadata metadata) {
    var album = musicBrainzService.getAlbumByName(metadata.getAlbum());
    if (album == null) {
      return null;
    }
    var albumInfo = new AlbumInfo();
    albumInfo.setYear(AlbumUtils.formatYear(album.getDate()));
    // Optionally, set coverArt and genre if available.
    return albumInfo;
  }

  @Override
  public ActionResult isSomethingNew(AlbumInfo albumInfo, Metadata metadata) {
    boolean updated = false;
    if (albumInfo != null && (metadata.getYear() == null || metadata.getYear().isEmpty())) {
      metadata.setYear(albumInfo.getYear());
      updated = true;
    }
    return updated ? ActionResult.NEW : ActionResult.READY;
  }
}
