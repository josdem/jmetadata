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

package com.josdem.jmetadata.controller;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.helper.CoverArtRetrofitHelper;
import com.josdem.jmetadata.helper.RetrofitHelper;
import com.josdem.jmetadata.metadata.MetadataWriter;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MusicBrainzResponse;
import com.josdem.jmetadata.service.CoverArtRestService;
import com.josdem.jmetadata.service.LastfmService;
import com.josdem.jmetadata.service.MetadataService;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.service.RestService;
import com.josdem.jmetadata.util.ApplicationState;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.asmatron.messengine.annotations.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import retrofit2.Response;

@Controller
public class CompleteController {

  private MetadataWriter metadataWriter = new MetadataWriter();

  @Autowired private LastfmService lastfmService;
  @Autowired private MetadataService metadataService;
  @Autowired private MusicBrainzService musicBrainzService;

  private RestService restService;
  private CoverArtRestService coverArtRestService;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @PostConstruct
  void setup() {
    restService = RetrofitHelper.getRetrofit().create(RestService.class);
    coverArtRestService = CoverArtRetrofitHelper.getRetrofit().create(CoverArtRestService.class);
  }

  @RequestMethod(Actions.COMPLETE_ALBUM_METADATA)
  public synchronized ActionResult completeAlbumMetadata(List<Metadata> metadatas) {
    if (!metadataService.isSameAlbum(metadatas) || !metadataService.isSameArtist(metadatas)) {
      return ActionResult.Ready;
    }
    try {
      if (ApplicationState.cache.get(metadatas.getFirst().getAlbum()) == null) {
        log.info("Getting releases");
        var response =
            restService.getReleases(
                metadatas.getFirst().getAlbum()
                    + " AND "
                    + "artist:"
                    + metadatas.getFirst().getArtist());
        Response<MusicBrainzResponse> result = response.execute();
        if (result.isSuccessful()) {
          MusicBrainzResponse musicBrainzResponse = result.body();
          String albumName = metadatas.getFirst().getAlbum();
          ApplicationState.cache.put(albumName, musicBrainzResponse);
          log.info("MusicBrainz Response: {}", musicBrainzResponse);
          Album album = musicBrainzService.getAlbumByName(albumName);
          log.info("MusicBrainz Album: {}", album);
          if (album.getCoverArtArchive().isFront()) {
            log.info("Getting cover art");
            var coverArtResponse = coverArtRestService.getRelease(album.getId());
            var coverArtResult = coverArtResponse.execute();
            if (coverArtResult.isSuccessful()) {
              var coverArt = coverArtResult.body();
              log.info("Cover Art: {}", coverArt);
            }
          }
        } else {
          log.error("Error getting releases: {}", result.errorBody());
        }
        return ActionResult.New;
      }
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
      return ActionResult.Error;
    }
    return ActionResult.Complete;
  }

  @RequestMethod(Actions.COMPLETE_LAST_FM_METADATA)
  public ActionResult completeLastFmMetadata(Metadata metadata) {
    log.info(
        "trying to complete LastFM metadata for: {} - {}",
        metadata.getArtist(),
        metadata.getTitle());
    return lastfmService.completeLastFM(metadata);
  }

  @RequestMethod(Actions.WRITE_METADATA)
  public synchronized ActionResult completeAlbum(Metadata metadata) {
    try {
      File file = metadata.getFile();
      log.info("writting: {}", metadata.getTitle());
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
      return ActionResult.Updated;
    } catch (MetadataException mde) {
      log.error(mde.getMessage(), mde);
      return ActionResult.Error;
    }
  }
}
