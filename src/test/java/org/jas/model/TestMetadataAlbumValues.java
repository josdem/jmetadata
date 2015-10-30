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
