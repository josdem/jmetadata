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

package org.jas.controller;

import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.annotations.RequestMethod;
import org.jas.action.ActionResult;
import org.jas.action.Actions;
import org.jas.exception.MetadataException;
import org.jas.helper.RetrofitHelper;
import org.jas.metadata.MetadataWriter;
import org.jas.model.CoverArt;
import org.jas.model.Metadata;
import org.jas.model.MusicBrainzResponse;
import org.jas.model.MusicBrainzTrack;
import org.jas.service.LastfmService;
import org.jas.service.MetadataService;
import org.jas.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import retrofit2.Response;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CompleteController {

    private MetadataWriter metadataWriter = new MetadataWriter();

    @Autowired
    private LastfmService lastfmService;
    @Autowired
    private MetadataService metadataService;

    private RestService restService;

    private final Map<String, MusicBrainzResponse> cache = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    void setup() {
        restService = RetrofitHelper.getRetrofit().create(RestService.class);
    }

    @RequestMethod(Actions.COMPLETE_ALBUM_METADATA)
    public synchronized ActionResult completeAlbumMetadata(List<Metadata> metadatas) {
        if(!metadataService.isSameAlbum(metadatas) || !metadataService.isSameArtist(metadatas)) {
            return ActionResult.Ready;
        }
        try {
            if (cache.get(metadatas.getFirst().getAlbum()) == null) {
                log.info("Getting releases");
                var response = restService.getReleases(metadatas.getFirst().getAlbum() + " AND " + "artist:" + metadatas.getFirst().getArtist());
                Response<MusicBrainzResponse> result = response.execute();
                if (result.isSuccessful()) {
                    MusicBrainzResponse musicBrainzResponse = result.body();
                    cache.put(metadatas.getFirst().getAlbum(), musicBrainzResponse);
                    log.info("MusicBrainzResponse: {}", musicBrainzResponse);
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
        log.info("trying to complete LastFM metadata for: {} - {}", metadata.getArtist(), metadata.getTitle());
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
