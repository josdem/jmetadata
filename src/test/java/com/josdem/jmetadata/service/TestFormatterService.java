/*
   Copyright 2014 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.service;


import org.apache.commons.lang3.StringUtils;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.impl.FormatterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import lombok.extern.slf4j.Slf4j;

@Slf4j


public class TestFormatterService {
    private final FormatterService formatter = new FormatterServiceImpl();

    private final String badFormatA = "¿Cu&aacute;ndo?";
    private final String badFormatAExpected = "¿Cuándo?";
    private final String badFormatE = "¿Qu&eacute;?";
    private final String badFormatEExpected = "¿Qué?";
    private final String badFormatI = "m&iacute;";
    private final String badFormatIExpected = "mí";
    private final String badFormatO = "¿C&oacute;mo?";
    private final String bFormatOExpected = "¿Cómo?";
    private final String badFormatU = "t&uacute;";
    private final String badFormatUExpected = "tú";
    private final String badFormatAcute = "What&acute;s The Time";
    private final String badFormatAcuteExpected = "What's The Time";
    private final String artist = "angel tears";
    private final String expectedArtist = "Angel Tears";
    private final String title = "legends of the fall";
    private final String expectedTitle = "Legends Of The Fall";
    private final String album = "vision";
    private final String expectedAlbum = "Vision";

    private final Metadata metadata = new Metadata();

    @BeforeEach
    public void setup() throws Exception {
        metadata.setArtist(artist);
        metadata.setTitle(title);
        metadata.setAlbum(album);
    }

    @Test
    @DisplayName("validating a accent mark title")
    public void shouldDetectAaccentMarkTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(badFormatA);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatAExpected, metadata.getTitle());

    }

    @Test
    @DisplayName("validating e accent mark title")
    public void shouldDetectEaccentMarkTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(badFormatE);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatEExpected, metadata.getTitle());

    }

    @Test
    @DisplayName("validating i accent mark title")
    public void shouldDetectIaccentMarkTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(badFormatI);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatIExpected, metadata.getTitle());

    }

    @Test
    @DisplayName("validating o accent mark title")
    public void shouldDetectOaccentMarkTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(badFormatO);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(bFormatOExpected, metadata.getTitle());

    }

    @Test
    @DisplayName("validating u accent mark title")
    public void shouldDetectUaccentMarkTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(badFormatU);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatUExpected, metadata.getTitle());

    }

    @Test
    @DisplayName("validating a accent mark artist")
    public void shouldDetectAaccentMarkArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatA);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatAExpected, metadata.getArtist());

    }

    @Test
    @DisplayName("validating e accent mark artist")
    public void shouldDetectEaccentMarkArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatE);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatEExpected, metadata.getArtist());

    }

    @Test
    @DisplayName("validating i accent mark artist")
    public void shouldDetectIaccentMarkArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatI);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatIExpected, metadata.getArtist());

    }

    @Test
    @DisplayName("validating o accent mark artist")
    public void shouldDetectOaccentMarkArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatO);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(bFormatOExpected, metadata.getArtist());

    }

    @Test
    @DisplayName("validating u accent mark artist")
    public void shouldDetectUaccentMarkArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatU);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatUExpected, metadata.getArtist());
    }

    @Test
    @DisplayName("validating a accent mark album")
    public void shouldDetectAaccentMarkAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(badFormatA);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatAExpected, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating e accent mark album")
    public void shouldDetectEaccentMarkAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(badFormatE);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatEExpected, metadata.getAlbum());

    }

    @Test
    @DisplayName("validating i accent mark album")
    public void shouldDetectIaccentMarkAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(badFormatI);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatIExpected, metadata.getAlbum());

    }

    @Test
    @DisplayName("validating o accent mark album")
    public void shouldDetectOaccentMarkAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(badFormatO);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(bFormatOExpected, metadata.getAlbum());

    }

    @Test
    @DisplayName("validating u accent mark album")
    public void shouldDetectUaccentMarkAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(badFormatU);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatUExpected, metadata.getAlbum());

    }

    @Test
    @DisplayName("validating when title is without capital letters")
    public void shouldCapitalizeTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(title);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating when artist is without capital letters")
    public void shouldCapitalizeArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(artist);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating when album is without capital letters")
    public void shouldCapitalizeAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(album);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating when album is all in capital letters")
    public void shouldCapitalizeAlbumWhenUppercase(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(album.toUpperCase());

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating when title is all in capital letters")
    public void shouldCapitalizeTitleWhenUppercase(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(title.toUpperCase());

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating when artist is all in capital letters")
    public void shouldCapitalizeArtistWhenUppercase(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(artist.toUpperCase());

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating when artist has a dash")
    public void shouldCapitalizeArtistWhenDash(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "de-pazz";
        var artistExpected = "De-pazz";

        metadata.setArtist(artist);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(artistExpected, metadata.getArtist());
    }

    @Test
    @DisplayName("validating when title has a dash")
    public void shouldCapitalizeTitleWhenDash(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "blue-eyed";
        var titleExpected = "Blue-eyed";

        metadata.setTitle(title);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(titleExpected, metadata.getTitle());
    }

    @Test
    @DisplayName("validating when album has a dash")
    public void shouldCapitalizeAlbumWhenDash(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "blue-eyed";
        var albumExpected = "Blue-eyed";

        metadata.setAlbum(album);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(albumExpected, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating apostrophe in title")
    public void shouldDetectAcuteMarkTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setTitle(badFormatAcute);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatAcuteExpected, metadata.getTitle());

    }

    @Test
    @DisplayName("validating apostrophe in artist")
    public void shouldDetectAcuteMarkArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatAcute);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatAcuteExpected, metadata.getArtist());
    }

    @Test
    @DisplayName("validating apostrophe in album")
    public void shouldDetectAcuteMarkAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setAlbum(badFormatAcute);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(badFormatAcuteExpected, metadata.getAlbum());
    }

    @Test
    @DisplayName("adding missing capital letters")
    public void shouldAddCapitalLettersWhenParenthesis(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "as the rush comes (chillout mix)";
        var expectedTitle = "As The Rush Comes (chillout Mix)";

        metadata.setTitle(title);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("adding missing capital letters")
    public void shouldAddCapitalLettersWhenNumbers(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "rabbit in your headlights (3d mix)";
        var expectedTitle = "Rabbit In Your Headlights (3d Mix)";

        metadata.setTitle(title);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("adding missing capital letters when apostrophe")
    public void shouldAddCapitalLettersWhenApostrophe(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "jesu, joy of man's desiring";
        var expectedTitle = "Jesu, Joy Of Man's Desiring";

        metadata.setTitle(title);

        assertTrue(formatter.wasCamelized(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating grave accent")
    public void shouldDetectGraveAccent(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Lumi&egrave;res De Skye";
        var expectedTitle = "Lumieres De Skye";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating e acute accent")
    public void shouldDetectAcuteAccent(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Le Vent d'&Eacute;cosse";
        var expectedTitle = "Le Vent d'Écosse";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating e circum-flex accent")
    public void shouldDetectCircumflexAccent(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Entre-Nous... La f&ecirc;te";
        var expectedTitle = "Entre-Nous... La fête";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating u uml accent in title")
    public void shouldDetectUFormatInTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Bl&uuml;chel & von Deylen";
        var expectedTitle = "Blüchel & von Deylen";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating u uml accent in artist")
    public void shouldDetectUFormatInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Bl&uuml;chel & von Deylen";
        var expectedArtist = "Blüchel & von Deylen";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating u uml accent in album")
    public void shouldDetectUFormatInAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "Bl&uuml;chel & von Deylen";
        var expectedAlbum = "Blüchel & von Deylen";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating o uml accent in title")
    public void shouldDetectOFormatInTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Pop&ouml;s";
        var expectedTitle = "Popös";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating o uml accent in artist")
    public void shouldDetectOFormatInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Pop&ouml;s";
        var expectedArtist = "Popös";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating o uml accent in album")
    public void shouldDetectOFormatInAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "Pop&ouml;s";
        var expectedAlbum = "Popös";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating a grave accent in title")
    public void shouldDetectAGraveFormatInTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Déj&agrave; Vu";
        var expectedTitle = "Déja Vu";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating a grave accent in artist")
    public void shouldDetectAGraveFormatInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Déj&agrave; Vu";
        var expectedArtist = "Déja Vu";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating a grave accent in album")
    public void shouldDetectAGraveFormatInAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "Déj&agrave; Vu";
        var expectedAlbum = "Déja Vu";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating degree format in title")
    public void shouldDetectDegreeFormatInTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Beatniks&deg;";
        var expectedTitle = "Beatniks°";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating degree format in artist")
    public void shouldDetectDegreeFormatInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Beatniks&deg;";
        var expectedArtist = "Beatniks°";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating degree format in album")
    public void shouldDetectDegreeFormatInAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "Beatniks&deg;";
        var expectedAlbum = "Beatniks°";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating n tilde in title")
    public void shouldDetectEnTildeInTitle(TestInfo testInfo) {
        var title = "Ni&ntilde;a";
        var expectedTitle = "Niña";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating n tilde in artist")
    public void shouldDetectEnTildeInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Ni&ntilde;a";
        var expectedArtist = "Niña";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating n tilde in album")
    public void shouldDetectEnTildeInAlbum() {
        var album = "Ni&ntilde;a";
        var expectedAlbum = "Niña";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating a umlaut mark in title")
    public void shouldDetectAUmlautMarkInTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "J&auml;hdytin";
        var expectedTitle = "Jähdytin";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating a umlaut mark in artist")
    public void shouldDetectAUmlautMarkInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "J&auml;hdytin";
        var expectedArtist = "Jähdytin";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating a umlaut mark in album")
    public void shouldDetectAUmlautMarkInAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "J&auml;hdytin";
        var expectedAlbum = "Jähdytin";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating metadata is analyzable")
    public void shouldBeAnalyzable(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Beatniks";
        var artist = "Jaytin";
        var album = "Nina";

        metadata.setTitle(title);
        metadata.setArtist(artist);
        metadata.setAlbum(album);

        assertDoesNotThrow(() -> formatter.isAnalyzable(metadata));
    }

    @Test
    @DisplayName("validating metadata is not analyzable due to album")
    public void shouldNotBeAnalyzableDueToAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Beatniks";
        var artist = "Jaytin";

        metadata.setTitle(title);
        metadata.setArtist(artist);
        metadata.setAlbum(null);

        assertThrows(NullPointerException.class, () -> formatter.isAnalyzable(metadata));
    }

    @Test
    @DisplayName("validating metadata is not analyzable due to artist")
    public void shouldNotBeAnalyzableDueToArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "Beatniks";
        var album = "Nina";

        metadata.setTitle(title);
        metadata.setArtist(null);
        metadata.setAlbum(album);

        assertThrows(NullPointerException.class, () -> formatter.isAnalyzable(metadata));
    }

    @Test
    @DisplayName("validating metadata is not analyzable due to title")
    public void shouldNotBeAnalyzableDueToTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Jaytin";
        var album = "Nina";

        metadata.setTitle(null);
        metadata.setArtist(artist);
        metadata.setAlbum(album);

        assertThrows(NullPointerException.class, () -> formatter.isAnalyzable(metadata));
    }

    @Test
    @DisplayName("validating a circle mark in title")
    public void shouldDetectCircleInTitle(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var title = "J&auml;hdytin";
        var expectedTitle = "Jähdytin";

        metadata.setTitle(title);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedTitle, metadata.getTitle());
    }

    @Test
    @DisplayName("validating a circle mark in artist")
    public void shouldDetectCircleInArtist(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var artist = "Aganj&ucirc;";
        var expectedArtist = "Aganjû";

        metadata.setArtist(artist);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedArtist, metadata.getArtist());
    }

    @Test
    @DisplayName("validating a circle mark in album")
    public void shouldDetectCircleInAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var album = "Aganj&ucirc;";
        var expectedAlbum = "Aganjû";

        metadata.setAlbum(album);

        assertTrue(formatter.wasFormatted(metadata));
        assertEquals(expectedAlbum, metadata.getAlbum());
    }

    @Test
    @DisplayName("validating when title is empty")
    public void shouldFormatTitleWhenEmpty(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatA);
        metadata.setTitle(StringUtils.EMPTY);
        metadata.setAlbum(badFormatE);

        assertTrue(formatter.wasFormatted(metadata));
    }

    @Test
    @DisplayName("validating when artist is empty")
    public void shouldFormatArtistWhenEmpty(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(StringUtils.EMPTY);
        metadata.setTitle(badFormatA);
        metadata.setAlbum(badFormatE);

        assertTrue(formatter.wasFormatted(metadata));
    }

    @Test
    @DisplayName("validating when album is empty")
    public void shouldFormatAlbumWhenEmpty(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        metadata.setArtist(badFormatA);
        metadata.setTitle(badFormatE);
        metadata.setAlbum(StringUtils.EMPTY);

        assertTrue(formatter.wasFormatted(metadata));
    }

    @Test
    @DisplayName("getting duration from seconds")
    public void shouldGetDuration(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var length = 397;
        var expectedDuration = "6:37";
        assertEquals(expectedDuration, formatter.getDuration(length));
    }
}


