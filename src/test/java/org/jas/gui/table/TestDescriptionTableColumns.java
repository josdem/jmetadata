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

package org.jas.gui.table;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDescriptionTableColumns {

	@Test
	public void shouldValidateArtistColumn() throws Exception {
		assertEquals("Artist", DescriptionTableColumns.ARTIST.label());
		assertEquals(68, DescriptionTableColumns.ARTIST.minWidth());
		assertEquals(68, DescriptionTableColumns.ARTIST.maxWidth());
	}

	@Test
	public void shouldValidateTrackColumn() throws Exception {
		assertEquals("Track", DescriptionTableColumns.TRACK.label());
		assertEquals(180, DescriptionTableColumns.TRACK.minWidth());
		assertEquals(180, DescriptionTableColumns.TRACK.maxWidth());
	}

	@Test
	public void shouldValidateAlbumColumn() throws Exception {
		assertEquals("Album", DescriptionTableColumns.ALBUM.label());
		assertEquals(68, DescriptionTableColumns.ALBUM.minWidth());
		assertEquals(68, DescriptionTableColumns.ALBUM.maxWidth());
	}

	@Test
	public void shouldValidateGenreColumn() throws Exception {
		assertEquals("Genre", DescriptionTableColumns.GENRE.label());
		assertEquals(67, DescriptionTableColumns.GENRE.minWidth());
		assertEquals(67, DescriptionTableColumns.GENRE.maxWidth());
	}

	@Test
	public void shouldValidateYearColumn() throws Exception {
		assertEquals("Year", DescriptionTableColumns.YEAR.label());
		assertEquals(50, DescriptionTableColumns.YEAR.minWidth());
		assertEquals(50, DescriptionTableColumns.YEAR.maxWidth());
	}

	@Test
	public void shouldValidateTrackNumberColumn() throws Exception {
		assertEquals("# Trk", DescriptionTableColumns.N_TRACK.label());
		assertEquals(50, DescriptionTableColumns.N_TRACK.minWidth());
		assertEquals(50, DescriptionTableColumns.N_TRACK.maxWidth());
	}

	@Test
	public void shouldValidateTotalTracksColumn() throws Exception {
		assertEquals("# Trks", DescriptionTableColumns.N_TRACKS.label());
		assertEquals(50, DescriptionTableColumns.N_TRACKS.minWidth());
		assertEquals(50, DescriptionTableColumns.N_TRACKS.maxWidth());
	}

	@Test
	public void shouldValidateCdNumberColumn() throws Exception {
		assertEquals("# CD", DescriptionTableColumns.N_CD.label());
		assertEquals(50, DescriptionTableColumns.N_CD.minWidth());
		assertEquals(50, DescriptionTableColumns.N_CD.maxWidth());
	}

	@Test
	public void shouldValidateTotalCdsColumn() throws Exception {
		assertEquals("# CDS", DescriptionTableColumns.N_CDS.label());
		assertEquals(50, DescriptionTableColumns.N_CDS.minWidth());
		assertEquals(50, DescriptionTableColumns.N_CDS.maxWidth());
	}

	@Test
	public void shouldValidateStatusColumn() throws Exception {
		assertEquals("Status", DescriptionTableColumns.STATUS.label());
		assertEquals(60, DescriptionTableColumns.STATUS.minWidth());
		assertEquals(60, DescriptionTableColumns.STATUS.maxWidth());
	}

}
