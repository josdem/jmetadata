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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.jas.helper.TrackHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.entities.Artist;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.ReleaseList;
import com.slychief.javamusicbrainz.entities.Track;
import com.slychief.javamusicbrainz.entities.TrackList;

public class TestTrackHelper {
	@InjectMocks
	private TrackHelper trackHelper = new TrackHelper();

	@Mock
	private Track track;
	@Mock
	private ReleaseList releaseList;
	@Mock
	private Release release;
	@Mock
	private TrackList trackList;
	@Mock
	private List<Track> tracks;
	@SuppressWarnings("rawtypes")
	@Mock
	private Artist artist;

	private List<Release> releases;
	private String trackNumber = "3";
	private int totalTracks = 10;

	private String album = "Indigo (Original Mix)";
	private String artistName = "Activa Pres Solar Movement";
	private static final String ONE = "1";
	private static final String ID = "35188948-6b68-2113-a75e-f4ead4fd2047";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		releases = new ArrayList<Release>();
		releases.add(release);
	}
	@Test
	public void shouldGetTrackNumber() throws Exception {
		setReleaseExpectations();
		when(release.getTrackList()).thenReturn(trackList);
		when(trackList.getOffset()).thenReturn(trackNumber);

		assertEquals (trackNumber, trackHelper.getTrackNumber(track));

	}

	@Test
	public void shouldGetTotalNumbers() throws Exception {
		setReleaseExpectations();
		when(release.getTracks()).thenReturn(tracks);
		when(tracks.size()).thenReturn(totalTracks);

		assertEquals(totalTracks, trackHelper.getTotalTrackNumber(track));
	}

	private void setReleaseExpectations() {
		when(track.getReleases()).thenReturn(releaseList);
		when(releaseList.getReleases()).thenReturn(releases);
	}

	@Test
	public void shouldGetAlbum() throws Exception {
		setReleaseExpectations();
		when(release.getTitle()).thenReturn(album);

		assertEquals(album, trackHelper.getAlbum(track));
	}

	@Test
	public void shouldGetArtist() throws Exception {
		when(track.getArtist()).thenReturn(artist);
		when(artist.getName()).thenReturn(artistName);

		assertEquals(artistName, trackHelper.getArtist(track));
	}

	@Test
	public void shouldGetMusicBrainzID() throws Exception {
		when(track.getId()).thenReturn(ID);

		assertEquals(ID, trackHelper.getMusicBrainzID(track));
	}

	@Test
	public void shouldGetCdNumber() throws Exception {
		assertEquals(ONE, trackHelper.getCdNumber(track));
	}

	@Test
	public void shouldGetTotalCds() throws Exception {
		assertEquals(ONE, trackHelper.getTotalCds(track));
	}
}
