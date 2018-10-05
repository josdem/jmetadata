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

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

import org.apache.commons.lang3.StringUtils;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Track;

import org.jas.helper.TrackHelper;
import org.jas.service.MusicBrainzFinder;
import org.jas.service.impl.MusicBrainzFinderImpl;
import org.jas.model.MusicBrainzTrack;

public class TestTrackFinder {
	private static final String TOTAL_TRACKS = "10";
	private static final Object ZERO = "0";
	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CDS = "2";

	@InjectMocks
	private MusicBrainzFinder trackFinder = new MusicBrainzFinderImpl();

	@Mock
	private TrackHelper trackHelper;
	@Mock
	private Track track;

	private String artist = "Sander Van Doorn";
	private String title = "The Bottle Hymn 2.0";
	private String album = "The Bottle Hymn 2.0 EP";

	private List<Track> trackList= new ArrayList<Track>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		trackList.add(track);
	}

	@Test
	public void shouldNotFindAnyAlbum() throws Exception {
		MusicBrainzTrack result = trackFinder.getAlbum(artist, title);

		assertTrue(StringUtils.isEmpty(result.getAlbum()));
		assertEquals(ZERO, result.getTrackNumber());
	}

	@Test
	public void shouldGetAlbum() throws Exception {
		String expectedTrack = "2";
		setTrackHelperExpectations();

		MusicBrainzTrack result = trackFinder.getAlbum(artist, title);

		verifyTrackHelperExpectations(expectedTrack, result);
	}

	@Test
	public void shouldGetAlbumIgnoreCase() throws Exception {
		String expectedTrack = "2";
		setTrackHelperExpectations();
		when(trackHelper.getArtist(track)).thenReturn("sander van doorn");

		MusicBrainzTrack result = trackFinder.getAlbum(artist, title);

		verifyTrackHelperExpectations(expectedTrack, result);
	}

	private void verifyTrackHelperExpectations(String expectedTrack, MusicBrainzTrack result) {
		assertEquals(album, result.getAlbum());
		assertEquals(expectedTrack, result.getTrackNumber());
		assertEquals(TOTAL_TRACKS, result.getTotalTrackNumber());
		assertEquals(CD_NUMBER, result.getCdNumber());
		assertEquals(TOTAL_CDS, result.getTotalCds());
	}

	private void setTrackHelperExpectations() throws ServerUnavailableException {
		when(trackHelper.findByTitle(title)).thenReturn(trackList);
		when(trackHelper.getArtist(track)).thenReturn(artist);
		when(trackHelper.getTrackNumber(track)).thenReturn("1");
		when(trackHelper.getAlbum(track)).thenReturn(album);
		when(trackHelper.getTotalTrackNumber(track)).thenReturn(Integer.valueOf(TOTAL_TRACKS));
		when(trackHelper.getCdNumber(track)).thenReturn(CD_NUMBER);
		when(trackHelper.getTotalCds(track)).thenReturn(TOTAL_CDS);
	}
}
