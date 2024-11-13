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

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Track;
import org.apache.commons.lang3.StringUtils;
import org.jas.model.MusicBrainzTrack;
import org.jas.service.MusicBrainzFinderService;
import org.jas.service.impl.MusicBrainzFinderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TestMusicBrainzFinderService {
    private static final String TOTAL_TRACKS = "10";
    private static final Object ZERO = "0";
    private static final String CD_NUMBER = "1";
    private static final String TOTAL_CDS = "2";

    @InjectMocks
    private MusicBrainzFinderService musicBrainzFinderService = new MusicBrainzFinderServiceImpl();

    @Mock
    private TrackHelper trackHelper;
    @Mock
    private Track track;

    private String artist = "Sander Van Doorn";
    private String title = "The Bottle Hymn 2.0";
    private String album = "The Bottle Hymn 2.0 EP";

    private List<Track> trackList = new ArrayList<Track>();

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        trackList.add(track);
    }

    @Test
    public void shouldNotFindAnyAlbum() throws Exception {
        MusicBrainzTrack result = musicBrainzFinderService.getAlbum(artist, title);

        assertTrue(StringUtils.isEmpty(result.getAlbum()));
        assertEquals(ZERO, result.getTrackNumber());
    }

    @Test
    public void shouldGetAlbum() throws Exception {
        String expectedTrack = "2";
        setTrackHelperExpectations();

        MusicBrainzTrack result = musicBrainzFinderService.getAlbum(artist, title);

        verifyTrackHelperExpectations(expectedTrack, result);
    }

    @Test
    public void shouldGetAlbumIgnoreCase() throws Exception {
        String expectedTrack = "2";
        setTrackHelperExpectations();
        when(trackHelper.getArtist(track)).thenReturn("sander van doorn");

        MusicBrainzTrack result = musicBrainzFinderService.getAlbum(artist, title);

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
