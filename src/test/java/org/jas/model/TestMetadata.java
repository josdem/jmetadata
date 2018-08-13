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

package org.jas.model;

import static org.junit.Assert.assertEquals;

import java.awt.Image;
import java.io.File;

import org.jas.model.CoverArt;
import org.jas.model.Metadata;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestMetadata {
	private Metadata metadata = new Metadata();

	@Mock
	private Image artwork;
	@Mock
	private File file;
	@Mock
	private CoverArt coverArt;

	private static final String title = "Reverie (Dash Berlin Remix)";
	private static final String artist = "First State feat. Sarah Howells";
	private static final String album = "Reverie";
	private static final String genre = "Vocal Trance";
	private static final String trackNumber = "2";
	private static final String totalTracks = "5";
	private static final int length = 697;
	private static final int bitRate = 320;
	private static final String cdNumber = "1";
	private static final String totalCds = "1";
	private static final String year = "2011";
	private static final boolean metadataFromFile = true;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetTitle() throws Exception {
		metadata.setTitle(title );
		assertEquals(title, metadata.getTitle());
	}

	@Test
	public void shouldGetArtist() throws Exception {
		metadata.setArtist(artist);
		assertEquals(artist, metadata.getArtist());
	}

	@Test
	public void shouldGetAlbum() throws Exception {
		metadata.setAlbum(album );
		assertEquals(album, metadata.getAlbum());
	}

	@Test
	public void shouldGetGenre() throws Exception {
		metadata.setGenre(genre);
		assertEquals(genre, metadata.getGenre());
	}

	@Test
	public void shouldGetTrackNumber() throws Exception {
		metadata.setTrackNumber(trackNumber);
		assertEquals(trackNumber, metadata.getTrackNumber());
	}

	@Test
	public void shouldGetTotalTracks() throws Exception {
		metadata.setTotalTracks(totalTracks);
		assertEquals(totalTracks, metadata.getTotalTracks());
	}

	@Test
	public void shouldGetArtWork() throws Exception {
		metadata.setCoverArt(artwork);
		assertEquals(artwork, metadata.getCoverArt());
	}

	@Test
	public void shouldGetLength() throws Exception {
		metadata.setLenght(length);
		assertEquals(length, metadata.getLength());
	}

	@Test
	public void shouldGetBitRate() throws Exception {
		metadata.setBitRate(bitRate);
		assertEquals(bitRate, metadata.getBitRate());
	}

	@Test
	public void shouldGetFile() throws Exception {
		metadata.setFile(file);
		assertEquals(file, metadata.getFile());
	}

	@Test
	public void shouldGetCdNumber() throws Exception {
		metadata.setCdNumber(cdNumber);
		assertEquals(cdNumber, metadata.getCdNumber());
	}

	@Test
	public void shouldGetTotalCds() throws Exception {
		metadata.setTotalCds(totalCds);
		assertEquals(totalCds, metadata.getTotalCds());
	}

	@Test
	public void shouldGetYear() throws Exception {
		metadata.setYear(year);
		assertEquals(year, metadata.getYear());
	}

	@Test
	public void shouldGetNewCoverArt() throws Exception {
		metadata.setNewCoverArt(coverArt);
		assertEquals(coverArt, metadata.getNewCoverArt());
	}

	@Test
	public void shouldDetectPreviousMetadata() throws Exception {
		Metadata previousMetadata = new Metadata();
		previousMetadata.setTrackNumber("1");

		metadata.setTrackNumber(trackNumber);
		assertEquals(1, metadata.compareTo(previousMetadata));
	}

	@Test
	public void shouldDetectNextMetadata() throws Exception {
		Metadata nextMetadata = new Metadata();
		nextMetadata.setTrackNumber("3");

		metadata.setTrackNumber(trackNumber);
		assertEquals(-1, metadata.compareTo(nextMetadata));
	}

	@Test
	public void shouldRespondAtErrorInTrackNumber() throws Exception {
		Metadata rareMetadata = new Metadata();
		rareMetadata.setTrackNumber("somethingWrong");

		metadata.setTrackNumber(trackNumber);
		assertEquals(0, metadata.compareTo(rareMetadata));
	}

	@Test
	public void shouldKnowIfMetadataIsFromFile() throws Exception {
		metadata.setMetadataFromFile(metadataFromFile);
		assertEquals(metadataFromFile, metadata.isMetadataFromFile());
	}
}
