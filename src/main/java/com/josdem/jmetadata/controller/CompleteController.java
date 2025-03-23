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

package com.josdem.jmetadata.controller;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.helper.CoverArtRetrofitHelper;
import com.josdem.jmetadata.helper.RetrofitHelper;
import com.josdem.jmetadata.metadata.MetadataWriter;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MusicBrainzResponse;
import com.josdem.jmetadata.service.CoverArtRestService;
import com.josdem.jmetadata.service.LastfmService;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.service.RestService;
import com.josdem.jmetadata.service.impl.LastFMCompleteServiceAdapter;
import com.josdem.jmetadata.service.impl.MusicBrainzCompleteServiceAdapter;
import com.josdem.jmetadata.util.ApplicationState;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.stereotype.Controller;
import retrofit2.Response;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CompleteController {

  private final MetadataWriter metadataWriter;
  private final LastfmService lastfmService;
  private final MusicBrainzService musicBrainzService;
  private final MusicBrainzCompleteServiceAdapter musicBrainzCompleteServiceAdapter;
  private final LastFMCompleteServiceAdapter lastFMCompleteServiceAdapter;

  protected RestService restService;
  private CoverArtRestService coverArtRestService;

  @PostConstruct
  void setup() {
    restService = RetrofitHelper.getRetrofit().create(RestService.class);
    coverArtRestService = CoverArtRetrofitHelper.getRetrofit().create(CoverArtRestService.class);
  }

  @RequestMethod(Actions.COMPLETE_ALBUM_METADATA)
  public synchronized ActionResult completeAlbumMetadata(List<Metadata> metadataList) {
    if (!musicBrainzCompleteServiceAdapter.canComplete(metadataList)) {
      return ActionResult.READY;
    }
    try {
      if (ApplicationState.cache.get(metadataList.getFirst().getAlbum()) == null) {
        log.info("Getting releases");
        var response =
            restService.getReleases(
                metadataList.getFirst().getAlbum()
                    + " AND "
                    + "artist:"
                    + metadataList.getFirst().getArtist());
        Response<MusicBrainzResponse> result = response.execute();
        if (result.isSuccessful()) {
          MusicBrainzResponse musicBrainzResponse = result.body();
          String albumName = metadataList.getFirst().getAlbum();
          ApplicationState.cache.put(albumName, musicBrainzResponse);
          log.info("MusicBrainz Response: {}", musicBrainzResponse);
          if (musicBrainzResponse.getReleases().isEmpty()) {
            return ActionResult.READY;
          }
          Album album = musicBrainzService.getAlbumByName(albumName);
          log.info("MusicBrainz Album: {}", album);
          musicBrainzService.completeYear(metadataList, album);
          if (album.getCoverArtArchive().isFront()) {
            log.info("Getting cover art");
            var coverArtResponse = coverArtRestService.getRelease(album.getId());
            var coverArtResult = coverArtResponse.execute();
            if (coverArtResult.isSuccessful()) {
              var coverArt = coverArtResult.body();
              log.info("Cover Art: {}", coverArt);
              musicBrainzService.completeCoverArt(metadataList, coverArt);
            }
          }
        } else {
          log.error("Error getting releases: {}", result.errorBody());
        }
        return ActionResult.NEW;
      }
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
      return ActionResult.ERROR;
    }
    return ActionResult.COMPLETE;
  }

  @RequestMethod(Actions.COMPLETE_LAST_FM_METADATA)
  public ActionResult completeLastFmMetadata(List<Metadata> metadataList) {
    log.info("trying to complete using LastFM service");
    if (!lastFMCompleteServiceAdapter.canComplete(metadataList)) {
      return ActionResult.READY;
    }
    metadataList.forEach(lastfmService::completeLastFM);
    return ActionResult.NEW;
  }

  @RequestMethod(Actions.WRITE_METADATA)
  public synchronized ActionResult completeAlbum(Metadata metadata) {
    try {
      File file = metadata.getFile();
      log.info("writing: {}", metadata.getTitle());
      metadataWriter.setFile(file);
      metadataWriter.writeArtist(metadata.getArtist());
      metadataWriter.writeTitle(metadata.getTitle());
      metadataWriter.writeAlbum(metadata.getAlbum());
      metadataWriter.writeTrackNumber(metadata.getTrackNumber());
      metadataWriter.writeTotalTracksNumber(metadata.getTotalTracks());
      metadataWriter.writeCdNumber(metadata.getCdNumber());
      metadataWriter.writeTotalCds(metadata.getTotalCds());
      metadataWriter.writeYear(metadata.getYear());
      metadataWriter.writeGenre(metadata.getGenre());
      CoverArt coverArtNew = metadata.getNewCoverArt();
      if (coverArtNew != null) {
        log.info("trying to remove artwork");
        boolean result = metadataWriter.removeCoverArt();
        log.info("artwork deleted with result: {}", result);
        metadataWriter.writeCoverArt(coverArtNew.getImage());
        metadata.setCoverArt(coverArtNew.getImage());
      }
      return ActionResult.UPDATED;
    } catch (BusinessException bse) {
      log.error(bse.getMessage(), bse);
      return ActionResult.ERROR;
    }
  }
}
