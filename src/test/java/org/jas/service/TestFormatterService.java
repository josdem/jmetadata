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

package org.jas.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import org.apache.commons.lang3.StringUtils;

import org.jas.model.Metadata;
import org.jas.service.FormatterService;
import org.jas.service.impl.FormatterServiceImpl;

public class TestFormatterService {
	private FormatterService formatter = new FormatterServiceImpl();

	private String badFormatA = "¿Cu&aacute;ndo?";
	private String badFormatAExpected = "¿Cuándo?";
	private String badFormatE = "¿Qu&eacute;?";
	private String badFormatEExpected = "¿Qué?";
	private String badFormatI = "m&iacute;";
	private String badFormatIExpected = "mí";
	private String badFormatO = "¿C&oacute;mo?";
	private String bFormatOExpected = "¿Cómo?";
	private String badFormatU = "t&uacute;";
	private String badFormatUExpected = "tú";
	private String badFormatAcute = "What&acute;s The Time";
	private String badFormatAcuteExpected = "What's The Time";
	private String artist = "angel tears";
	private String expectedArtist = "Angel Tears";
	private String title = "legends of the fall";
	private String expectedTitle = "Legends Of The Fall";
	private String album = "vision";
	private String expectedAlbum = "Vision";
	private Metadata metadata = new Metadata();


	@Before
	public void setup() throws Exception {
		metadata.setArtist(artist);
		metadata.setTitle(title);
		metadata.setAlbum(album);
	}

	@Test
	public void shouldDetectAaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatA);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatAExpected , metadata.getTitle());

	}

	@Test
	public void shouldDetectEaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatE);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatEExpected , metadata.getTitle());

	}

	@Test
	public void shouldDetectIaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatI);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatIExpected , metadata.getTitle());

	}

	@Test
	public void shouldDetectOaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatO);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(bFormatOExpected , metadata.getTitle());

	}

	@Test
	public void shouldDetectUaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatU);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatUExpected , metadata.getTitle());

	}

	@Test
	public void shouldDetectAaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatA);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatAExpected , metadata.getArtist());

	}

	@Test
	public void shouldDetectEaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatE);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatEExpected , metadata.getArtist());

	}

	@Test
	public void shouldDetectIaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatI);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatIExpected , metadata.getArtist());

	}

	@Test
	public void shouldDetectOaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatO);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(bFormatOExpected , metadata.getArtist());

	}

	@Test
	public void shouldDetectUaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatU);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatUExpected , metadata.getArtist());
	}

	@Test
	public void shouldDetectAaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatA);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatAExpected , metadata.getAlbum());

	}

	@Test
	public void shouldFormatTitleWhenNull() throws Exception {
		metadata.setArtist(badFormatA);
		metadata.setTitle(null);
		metadata.setAlbum(badFormatE);

		assertTrue(formatter.wasFormatted(metadata));
	}

	@Test
	public void shouldFormatTitleWhenEmpty() throws Exception {
		metadata.setArtist(badFormatA);
		metadata.setTitle(StringUtils.EMPTY);
		metadata.setAlbum(badFormatE);

		assertTrue(formatter.wasFormatted(metadata));
	}

	@Test
	public void shouldFormatAlbumWhenNull() throws Exception {
		metadata.setArtist(badFormatA);
		metadata.setTitle(badFormatE);
		metadata.setAlbum(null);

		assertTrue(formatter.wasFormatted(metadata));
	}

	@Test
	public void shouldFormatAlbumWhenEmpty() throws Exception {
		metadata.setArtist(badFormatA);
		metadata.setTitle(badFormatE);
		metadata.setAlbum(StringUtils.EMPTY);

		assertTrue(formatter.wasFormatted(metadata));
	}

	@Test
	public void shouldDetectEaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatE);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatEExpected , metadata.getAlbum());

	}

	@Test
	public void shouldDetectIaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatI);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatIExpected , metadata.getAlbum());

	}

	@Test
	public void shouldDetectOaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatO);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(bFormatOExpected , metadata.getAlbum());

	}

	@Test
	public void shouldDetectUaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatU);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatUExpected , metadata.getAlbum());

	}

	@Test
	public void shouldCapitalizeTitle() throws Exception {
		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldCapitalizeArtist() throws Exception {
		metadata.setArtist(artist);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldCapitalizeAlbum() throws Exception {
		metadata.setAlbum(album);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldCapitalizeAlbumWhenUppercase() throws Exception {
		metadata.setAlbum(album.toUpperCase());

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldCapitalizeTitleWhenUppercase() throws Exception {
		metadata.setTitle(title.toUpperCase());

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldCapitalizeArtistWhenUppercase() throws Exception {
		metadata.setArtist(artist.toUpperCase());

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldCapitalizeArtistWhenDash() throws Exception {
		String artist = "de-pazz";
		String artistExpected = "De-pazz";

		metadata.setArtist(artist);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(artistExpected , metadata.getArtist());
	}

	@Test
	public void shouldCapitalizeTitleWhenDash() throws Exception {
		String title = "blue-eyed";
		String titleExpected = "Blue-eyed";

		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(titleExpected , metadata.getTitle());
	}

	@Test
	public void shouldCapitalizeAlbumWhenDash() throws Exception {
		String album = "blue-eyed";
		String albumExpected = "Blue-eyed";

		metadata.setAlbum(album);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(albumExpected , metadata.getAlbum());
	}

	@Test
	public void shouldGetDuration() throws Exception {
		int lenght = 397;
		String expectedDuration = "6:37";
		assertEquals(expectedDuration, formatter.getDuration(lenght));
	}

	@Test
	public void shouldDetectAcuteMarkTitle() throws Exception {
		metadata.setTitle(badFormatAcute);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatAcuteExpected , metadata.getTitle());

	}

	@Test
	public void shouldDetectAcuteMarkArtist() throws Exception {
		metadata.setArtist(badFormatAcute);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatAcuteExpected , metadata.getArtist());

	}

	@Test
	public void shouldDetectAcuteMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatAcute);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(badFormatAcuteExpected , metadata.getAlbum());

	}

	@Test
	public void shouldCamelizeWhenParenthesis() throws Exception {
		String title = "as the rush comes (chillout mix)";
		String expectedTitle = "As The Rush Comes (chillout Mix)";

		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldCamelizeWhenNumbers() throws Exception {
		String title = "rabbit in your headlights (3d mix)";
		String expectedTitle = "Rabbit In Your Headlights (3d Mix)";

		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldCamelizeWhenApostrophe() throws Exception {
		String title = "jesu, joy of man's desiring";
		String expectedTitle = "Jesu, Joy Of Man's Desiring";

		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetecteGraveAccent() throws Exception {
		String title = "Lumi&egrave;res De Skye";
		String expectedTitle = "Lumières De Skye";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetecteEAcuteAccent() throws Exception {
		String title = "Le Vent d'&Eacute;cosse";
		String expectedTitle = "Le Vent d'Écosse";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetecteCircumflexAccent() throws Exception {
		String title = "Entre-Nous... La f&ecirc;te";
		String expectedTitle = "Entre-Nous... La fête";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldCamelizeWhenI() throws Exception {
		String title = "barakaya (part I)";
		String expectedTitle = "Barakaya (part I)";

		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldCamelizeWhenAmpersand() throws Exception {
		String title = "paco fernandez & neve";
		String expectedTitle = "Paco Fernandez & Neve";

		metadata.setTitle(title);

		assertTrue(formatter.wasCamelized(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldNotReturnNew() throws Exception {
		String artist = "Youth Blood Martydparty Master Edit";
		String title = "6a";
		String album = StringUtils.EMPTY;

		metadata.setArtist(artist);
		metadata.setTitle(title);
		metadata.setAlbum(album);

		assertFalse(formatter.wasCamelized(metadata));
		assertFalse(formatter.wasFormatted(metadata));
	}

	@Test
	public void shouldDetectUFormatInTitle() throws Exception {
		String title = "Bl&uuml;chel & von Deylen";
		String expectedTitle = "Blüchel & von Deylen";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectUFormatInArtist() throws Exception {
		String artist = "Bl&uuml;chel & von Deylen";
		String expectedArtist = "Blüchel & von Deylen";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectUFormatInAlbum() throws Exception {
		String album = "Bl&uuml;chel & von Deylen";
		String expectedAlbum = "Blüchel & von Deylen";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldDetectOFormatInTitle() throws Exception {
		String title = "Pop&ouml;s";
		String expectedTitle = "Popös";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectOFormatInArtist() throws Exception {
		String artist = "Pop&ouml;s";
		String expectedArtist = "Popös";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectOFormatInAlbum() throws Exception {
		String album = "Pop&ouml;s";
		String expectedAlbum = "Popös";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldDetectAGraveFormatInTitle() throws Exception {
		String title = "Déj&agrave; Vu";
		String expectedTitle = "Déjà Vu";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectAGraveFormatInArtist() throws Exception {
		String artist = "Déj&agrave; Vu";
		String expectedArtist = "Déjà Vu";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectAGraveFormatInAlbum() throws Exception {
		String album = "Déj&agrave; Vu";
		String expectedAlbum = "Déjà Vu";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldDetectDegreeFormatInTitle() throws Exception {
		String title = "Beatniks&deg;";
		String expectedTitle = "Beatniks°";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectDegreeFormatInArtist() throws Exception {
		String artist = "Beatniks&deg;";
		String expectedArtist = "Beatniks°";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectDegreeFormatInAlbum() throws Exception {
		String album = "Beatniks&deg;";
		String expectedAlbum = "Beatniks°";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldDetectNtildeInTitle() throws Exception {
		String title = "Ni&ntilde;a";
		String expectedTitle = "Niña";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectNtildeInArtist() throws Exception {
		String artist = "Ni&ntilde;a";
		String expectedArtist = "Niña";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectNtildeInAlbum() throws Exception {
		String album = "Ni&ntilde;a";
		String expectedAlbum = "Niña";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldDetectAUmlautMarkInTitle() throws Exception {
		String title = "J&auml;hdytin";
		String expectedTitle = "Jähdytin";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectAUmlautMarkInArtist() throws Exception {
		String artist = "J&auml;hdytin";
		String expectedArtist = "Jähdytin";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectAUmlautMarkInAlbum() throws Exception {
		String album = "J&auml;hdytin";
		String expectedAlbum = "Jähdytin";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

	@Test
	public void shouldBeAnalyzable() throws Exception {
		String title = "Beatniks";
		String artist = "Jaytin";
		String album = "Nina";

		metadata.setTitle(title);
		metadata.setArtist(artist);
		metadata.setAlbum(album);

		assertTrue(formatter.isAnalyzable(metadata));
	}

	@Test
	public void shouldNotBeAnalyzableDueToAlbum() throws Exception {
		String title = "Beatniks";
		String artist = "Jaytin";

		metadata.setTitle(title);
		metadata.setArtist(artist);
		metadata.setAlbum(null);

		assertFalse(formatter.isAnalyzable(metadata));
	}

	@Test
	public void shouldNotBeAnalyzableDueToArtist() throws Exception {
		String title = "Beatniks";
		String album = "Nina";

		metadata.setTitle(title);
		metadata.setArtist(null);
		metadata.setAlbum(album);

		assertFalse(formatter.isAnalyzable(metadata));
	}

	@Test
	public void shouldNotBeAnalyzableDueToTitle() throws Exception {
		String artist = "Jaytin";
		String album = "Nina";

		metadata.setTitle(null);
		metadata.setArtist(artist);
		metadata.setAlbum(album);

		assertFalse(formatter.isAnalyzable(metadata));
	}

	@Test
	public void shouldDetectUcircInTitle() throws Exception {
		String title = "J&auml;hdytin";
		String expectedTitle = "Jähdytin";

		metadata.setTitle(title);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedTitle , metadata.getTitle());
	}

	@Test
	public void shouldDetectUcircInArtist() throws Exception {
		String artist = "Aganj&ucirc;";
		String expectedArtist = "Aganjû";

		metadata.setArtist(artist);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedArtist , metadata.getArtist());
	}

	@Test
	public void shouldDetectAUcircInAlbum() throws Exception {
		String album = "Aganj&ucirc;";
		String expectedAlbum = "Aganjû";

		metadata.setAlbum(album);

		assertTrue(formatter.wasFormatted(metadata));
		assertEquals(expectedAlbum , metadata.getAlbum());
	}

}
