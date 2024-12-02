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

import com.slychief.javamusicbrainz.ServerUnavailableException;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.annotations.RequestMethod;
import org.jas.action.ActionResult;
import org.jas.action.Actions;
import org.jas.exception.MetadataException;
import org.jas.helper.RetrofitHelper;
import org.jas.metadata.MetadataWriter;
import org.jas.model.Category;
import org.jas.model.CoverArt;
import org.jas.model.Metadata;
import org.jas.model.MusicBrainzResponse;
import org.jas.model.MusicBrainzTrack;
import org.jas.service.LastfmService;
import org.jas.service.MusicBrainzFinderService;
import org.jas.service.RestService;
import org.jas.util.URLStringEncoder;
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
    private MusicBrainzFinderService musicBrainzFinderService;

    private RestService restService;

    private final Map<String, MusicBrainzResponse> cache = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    void setup() {
        restService = RetrofitHelper.getRetrofit().create(RestService.class);
    }

    @RequestMethod(Actions.COMPLETE_ALBUM_METADATA)
    public synchronized ActionResult completeAlbumMetadata(Metadata metadata) {
        try {
            log.info("Trying to complete metadata using MusicBrainz for: {} - {} - {}", metadata.getArtist(), metadata.getTitle(), metadata.getAlbum());
            if (StringUtils.isEmpty(metadata.getAlbum())) {
                MusicBrainzTrack musicBrainzTrack = musicBrainzFinderService.getAlbum(metadata.getArtist(), metadata.getTitle());
                return compareTwoObjectsToFindNewData(metadata, musicBrainzTrack);
            } else {
                log.info("{} - {} has an album: {} I'll try to complete information using MusicBrainz", metadata.getArtist(), metadata.getTitle(), metadata.getAlbum());

                if (cache.get(metadata.getAlbum()) == null) {
                    log.info("Getting categories");
                    var response = restService.getReleases(URLStringEncoder.encode(metadata.getAlbum() + "artist:" + metadata.getArtist()));
                    Response<MusicBrainzResponse> result = response.execute();
                    if (result.isSuccessful()) {
                        MusicBrainzResponse musicBrainzResponse = result.body();
                        cache.put(metadata.getAlbum(), musicBrainzResponse);
                        log.info("MusicBrainzResponse: {}", musicBrainzResponse);
                    } else {
                        log.error("Error getting categories: {}", result.errorBody().string());
                    }
                    return ActionResult.New;
                }
            }
        } catch (ServerUnavailableException sue) {
            log.error(sue.getMessage(), sue);
            return ActionResult.Error;
        } catch (IOException e) {
            return ActionResult.Error;
        }
        return ActionResult.Complete;
    }

    private ActionResult compareTwoObjectsToFindNewData(Metadata metadata, MusicBrainzTrack musicBrainzTrack) {
        if (StringUtils.isNotEmpty(musicBrainzTrack.getAlbum())) {
            log.info("Album found by MusicBrainz: {} for track: {}", musicBrainzTrack.getAlbum(), metadata.getTitle());
            metadata.setAlbum(musicBrainzTrack.getAlbum());
            metadata.setTrackNumber(musicBrainzTrack.getTrackNumber());
            metadata.setTotalTracks(musicBrainzTrack.getTotalTrackNumber());
            metadata.setCdNumber(musicBrainzTrack.getCdNumber());
            metadata.setTotalCds(musicBrainzTrack.getTotalCds());
            return ActionResult.New;
        } else {
            log.info("There is no need to find an album for track: " + metadata.getTitle());
            return ActionResult.NotFound;
        }
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
