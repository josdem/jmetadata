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

import static org.junit.Assert.*;

import java.awt.Image;

import org.jas.model.MetadataAlbumValues;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestMetadataAlbumValues {
	private static final String GENRE = "Trance";
	private static final String ARTIST = "Cosmic Gate";
	private static final String ALBUM = "Sign Of The Times (Deluxe Edition)";
	private static final String YEAR = "2010";
	private static final String TRACKS = "34";
	private static final String CD = "1";
	private static final String CDS = "1";

	private MetadataAlbumValues metadataAlbumValues = new MetadataAlbumValues();

	@Mock
	private Image coverArt;

	@Before
	public void setup() throws Exception {

	}

	@Test
	public void shouldCreateAMetadataAlbumValues() throws Exception {
		metadataAlbumValues.setArtist(ARTIST);
		metadataAlbumValues.setAlbum(ALBUM);
		metadataAlbumValues.setGenre(GENRE);
		metadataAlbumValues.setYear(YEAR);
		metadataAlbumValues.setTracks(TRACKS);
		metadataAlbumValues.setCd(CD);
		metadataAlbumValues.setCds(CDS);
		metadataAlbumValues.setCoverart(coverArt);

		assertEquals(ARTIST, metadataAlbumValues.getArtist());
		assertEquals(ALBUM, metadataAlbumValues.getAlbum());
		assertEquals(GENRE, metadataAlbumValues.getGenre());
		assertEquals(YEAR, metadataAlbumValues.getYear());
		assertEquals(TRACKS, metadataAlbumValues.getTracks());
		assertEquals(CD, metadataAlbumValues.getCd());
		assertEquals(CDS, metadataAlbumValues.getCds());
		assertEquals(coverArt, metadataAlbumValues.getCoverArt());
	}

}
