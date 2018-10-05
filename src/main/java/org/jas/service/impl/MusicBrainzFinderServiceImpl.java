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

package org.jas.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.slychief.javamusicbrainz.entities.Track;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.ServerUnavailableException;

import org.jas.helper.TrackHelper;
import org.jas.model.MusicBrainzTrack;
import org.jas.service.MusicBrainzFinderService;

/**
 * @understands A class who knows how to get Album and track number using MusicBrainz
 */

@Service
public class MusicBrainzFinderServiceImpl implements MusicBrainzFinderService {

	@Autowired
	private TrackHelper trackHelper;

	private List<Track> trackList;

	@Override
	public synchronized MusicBrainzTrack getAlbum(String artist, String trackname) throws ServerUnavailableException {
		MusicBrainzTrack musicBrainzTrack = new MusicBrainzTrack();
		String album = StringUtils.EMPTY;
		trackList = trackHelper.findByTitle(trackname);
		for (Track track : trackList) {
			String artistFromMusicBrainz = trackHelper.getArtist(track);
			if (artist.equalsIgnoreCase(artistFromMusicBrainz)) {
				album = trackHelper.getAlbum(track);
				setMusicBrainzValues(musicBrainzTrack, album, track);
				break;
			}
		}
		return musicBrainzTrack;
	}

	@Override
	public synchronized MusicBrainzTrack getByAlbum(String trackname, String album) throws ServerUnavailableException {
		trackList = trackHelper.findByTitle(trackname);
		MusicBrainzTrack musicBrainzTrack = new MusicBrainzTrack();
		if (!trackList.isEmpty()) {
			for (Track track : trackList) {
				Release release = trackHelper.findAlbumByTrack(track, album);
				if (release != null) {
					setMusicBrainzValues(musicBrainzTrack, album, track);
				}
				break;
			}
		}
		return musicBrainzTrack;
	}

	private void setMusicBrainzValues(MusicBrainzTrack musicBrainzTrack, String album, Track track) throws ServerUnavailableException {
		musicBrainzTrack.setAlbum(album);
		musicBrainzTrack.setTrackNumber(String.valueOf(Integer.parseInt(trackHelper.getTrackNumber(track)) + 1));
		musicBrainzTrack.setTotalTrackNumber(String.valueOf(trackHelper.getTotalTrackNumber(track)));
		musicBrainzTrack.setCdNumber(trackHelper.getCdNumber(track));
		musicBrainzTrack.setTotalCds(trackHelper.getTotalCds(track));
	}

}
