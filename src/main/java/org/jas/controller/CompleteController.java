/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.jas.action.ActionResult;
import org.jas.action.Actions;
import org.jas.metadata.MetadataException;
import org.jas.metadata.MetadataWriter;
import org.jas.model.CoverArt;
import org.jas.model.Metadata;
import org.jas.model.MusicBrainzTrack;
import org.jas.service.LastfmService;
import org.jas.service.MusicBrainzFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who control the MusicBrainz service
 */

@Controller
public class CompleteController {
	private Log log = LogFactory.getLog(this.getClass());
	private MetadataWriter metadataWriter = new MetadataWriter();

	@Autowired
	private LastfmService lastfmService;
	@Autowired
	private MusicBrainzFinder musicBrainzFinder;

	@RequestMethod(Actions.COMPLETE_ALBUM_METADATA)
	public synchronized ActionResult completeAlbumMetadata(Metadata metadata) {
		try {
			log.info("Trying to complete metadata using MusicBrainz for: " + metadata.getArtist() + " - " + metadata.getTitle() + " - " + metadata.getAlbum());
			if (StringUtils.isEmpty(metadata.getAlbum())) {
				MusicBrainzTrack musicBrainzTrack = musicBrainzFinder.getAlbum(metadata.getArtist(), metadata.getTitle());
				return compareTwoObjectsToFindNewData(metadata, musicBrainzTrack);
			} else {
				log.info(metadata.getArtist() + " - " + metadata.getTitle() + " has an album: " + metadata.getAlbum() + " I'll try to complete information using MusicBrainz");
				MusicBrainzTrack musicBrainzTrack = musicBrainzFinder.getByAlbum(metadata.getTitle(), metadata.getAlbum());
				return compareTwoObjectsToFindNewData(metadata, musicBrainzTrack);
			}
		} catch (ServerUnavailableException sue) {
			log.error(sue, sue);
			return ActionResult.Error;
		}
	}

	private ActionResult compareTwoObjectsToFindNewData(Metadata metadata, MusicBrainzTrack musicBrainzTrack) {
		if (StringUtils.isNotEmpty(musicBrainzTrack.getAlbum())) {
			log.info("Album found by MusicBrainz: " + musicBrainzTrack.getAlbum() + " for track: " + metadata.getTitle());
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
		log.info("trying to complete LastFM metadata for: " + metadata.getArtist() + " - " + metadata.getTitle());
		return lastfmService.completeLastFM(metadata);
	}

	@RequestMethod(Actions.WRITE_METADATA)
	public synchronized ActionResult completeAlbum(Metadata metadata) {
		try {
			File file = metadata.getFile();
			log.info("writting: " + metadata.getTitle());
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
				log.info("artwork deleted with result: " + result);
				metadataWriter.writeCoverArt(coverArtNew.getImageIcon());
				metadata.setCoverArt(coverArtNew.getImageIcon());
			}
			return ActionResult.Updated;
		} catch (MetadataException mde) {
			log.error(mde, mde);
			return ActionResult.Error;
		}
	}

}
