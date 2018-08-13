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

package org.jas.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

@Component
public class TrackHelper {
	private static final int FIRST_RELEASE = 0;
	private static final String ONE = "1";

	public List<Track> findByTitle(String trackName) throws ServerUnavailableException {
		return Track.findByTitle(trackName);
	}

	public List<Release> getReleases(Track track) {
		List<Release> releases = track.getReleases().getReleases();
		return releases;
	}

	public String getTrackNumber(Track track) {
		List<Release> releases = getReleases(track);
		return (releases.get(FIRST_RELEASE).getTrackList().getOffset()).trim();
	}

	public String getTrackNumber(Release release) {
		return release.getTrackList().getOffset().trim();
	}

	public int getTotalTrackNumber(Track track) throws ServerUnavailableException {
		List<Release> releases = getReleases(track);
		return releases.get(FIRST_RELEASE).getTracks().size();
	}

	public int getTotalTrackNumber(Release release) throws ServerUnavailableException {
		return release.getTracks().size();
	}

	public String getAlbum(Track track) {
		List<Release> releases = getReleases(track);
		return releases.get(FIRST_RELEASE).getTitle();
	}

	public String getArtist(Track track) {
		return track.getArtist().getName();
	}

	public String getCdNumber(Track track) {
		return ONE;
	}

	public String getTotalCds(Track track) {
		return ONE;
	}

	public String getMusicBrainzID(Track track) {
		return track.getId();
	}

	public Release findAlbumByTrack(Track track, String album) {
		List<Release> releases = getReleases(track);
		for (Release release : releases) {
			if(album.equalsIgnoreCase(release.getTitle())){
				return release;
			}
		}
		return null;
	}

}
