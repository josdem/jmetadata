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
import com.josdem.jmetadata.model.LastfmAlbum;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.CompleteService;
import com.josdem.jmetadata.service.LastFMCompleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LastFMCompleteServiceAdapter implements CompleteService {

    private final LastFMCompleteService lastFMCompleteService;

    @Override
    public boolean canComplete(Metadata metadata) {
        return lastFMCompleteService.canLastFMHelpToComplete(metadata);
    }

    @Override
    public AlbumInfo getInfo(Metadata metadata) {
        try {
            var lastfmAlbum = lastFMCompleteService.getLastFM(metadata);
            if (lastfmAlbum == null) {
                return null;
            }
            var albumInfo = new AlbumInfo();
            albumInfo.setYear(lastfmAlbum.getYear());
            albumInfo.setCoverArt(lastfmAlbum.getImageIcon());
            albumInfo.setGenre(lastfmAlbum.getGenre());
            return albumInfo;
        } catch (Exception e) {
            log.error("Error retrieving LastFM information: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ActionResult isSomethingNew(AlbumInfo albumInfo, Metadata metadata) {
        var lastfmAlbum = new LastfmAlbum();
        lastfmAlbum.setYear(albumInfo.getYear());
        lastfmAlbum.setGenre(albumInfo.getGenre());
        lastfmAlbum.setImageIcon(albumInfo.getCoverArt());
        return lastFMCompleteService.isSomethingNew(lastfmAlbum, metadata);
    }
}
