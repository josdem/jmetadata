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

package org.jas.metadata;

import org.apache.commons.lang3.StringUtils;
import org.jas.helper.ArtworkHelper;
import org.jas.helper.AudioFileHelper;
import org.jas.util.ImageUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */

public class TestMetadataWriter {
    @InjectMocks
    private final MetadataWriter metadataWriter = new MetadataWriter();

    @Mock
    private AudioFile audioFile;
    @Mock
    private Tag tag;
    @Mock
    private File file;
    @Mock
    private AudioFileHelper audioFileHelper;
    @Mock
    private Image image;
    @Mock
    private ImageUtils imageUtils;
    @Mock
    private ArtworkHelper artworkHelper;
    @Mock
    private Artwork artwork;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        when(audioFile.getTag()).thenReturn(tag);
    }

    @Test
    public void shouldSetFile() throws Exception {
        when(audioFileHelper.read(file)).thenReturn(audioFile);
        metadataWriter.setFile(file);

        verify(audioFile).getTag();
    }

    @Test
    public void shouldWriteArtist() throws Exception {
        String artist = "Markus Schulz";
        metadataWriter.writeArtist(artist);

        verify(tag).setField(FieldKey.ARTIST, artist);
        verify(audioFile).commit();
    }

    @Test
    public void shouldWriteTrackName() throws Exception {
        String trackName = "Nowhere";
        metadataWriter.writeTitle(trackName);

        verify(tag).setField(FieldKey.TITLE, trackName);
        verify(audioFile).commit();
    }

    @Test
    public void shouldWriteAlbum() throws Exception {
        String album = "Sahara Nights";
        metadataWriter.writeAlbum(album);

        verify(tag).setField(FieldKey.ALBUM, album);
        verify(audioFile).commit();
    }

    @Test
    public void shouldWriteTrackNumber() throws Exception {
        String trackNumber = "1";

        metadataWriter.writeTrackNumber(trackNumber);
        verify(tag).setField(FieldKey.TRACK, trackNumber);
        verify(audioFile).commit();
    }

    @Test
    public void shouldWriteTotalTracksNumber() throws Exception {
        String totalTracksNumber = "10";

        metadataWriter.writeTotalTracksNumber(totalTracksNumber);
        verify(tag).setField(FieldKey.TRACK_TOTAL, totalTracksNumber);
        verify(audioFile).commit();
    }

    @Test
    public void shouldWriteCoverArt() throws Exception {
        when(imageUtils.saveCoverArtToFile(image, StringUtils.EMPTY)).thenReturn(file);
        when(artworkHelper.createArtwork()).thenReturn(artwork);

        metadataWriter.writeCoverArt(image);

        verify(imageUtils).saveCoverArtToFile(image, StringUtils.EMPTY);
        verify(artwork).setFromFile(file);
        verify(tag).setField(isA(Artwork.class));
        verify(audioFile).commit();
    }

    @Test
    public void shouldWriteCdNumber() throws Exception {
        String cdNumber = "1";

        boolean result = metadataWriter.writeCdNumber(cdNumber);

        verify(tag).setField(FieldKey.DISC_NO, cdNumber);
        verify(audioFile).commit();
        assertTrue(result);
    }

    @Test
    public void shouldWriteTotalCds() throws Exception {
        String totalCds = "2";

        boolean result = metadataWriter.writeTotalCds(totalCds);

        verify(tag).setField(FieldKey.DISC_TOTAL, totalCds);
        verify(audioFile).commit();
        assertTrue(result);
    }

    @Test
    public void shouldNotWritetotalTracksNumberTrackNumberIfEmptyString() throws Exception {
        assertFalse(metadataWriter.writeTrackNumber(StringUtils.EMPTY));
    }

    @Test
    public void shouldNotWriteTotalTracksNumberIfEmptyString() throws Exception {
        assertFalse(metadataWriter.writeTotalTracksNumber(StringUtils.EMPTY));
    }

    @Test
    public void shouldNotWriteCdNumberIfEmptyString() throws Exception {
        assertFalse(metadataWriter.writeCdNumber(StringUtils.EMPTY));
    }

    @Test
    public void shouldNotWriteTotalCdsIfEmptyString() throws Exception {
        assertFalse(metadataWriter.writeTotalCds(StringUtils.EMPTY));
    }

    @Test
    public void shouldWriteYear() throws Exception {
        String year = "1990";

        metadataWriter.writeYear(year);
        verify(tag).setField(FieldKey.YEAR, year);
        verify(audioFile).commit();
    }

    @Test
    public void shouldNotWriteYearIfEmptyString() throws Exception {
        assertFalse(metadataWriter.writeYear(StringUtils.EMPTY));
    }

    @Test
    public void shouldWriteGenre() throws Exception {
        String genre = "Minimal Techno";

        metadataWriter.writeGenre(genre);
        verify(tag).setField(FieldKey.GENRE, genre);
        verify(audioFile).commit();
    }

    @Test
    public void shouldNotWriteGenreIfEmptyString() throws Exception {
        assertFalse(metadataWriter.writeGenre(StringUtils.EMPTY));
    }

    @Test
    public void shouldRemoveCoverArt() throws Exception {
        assertTrue(metadataWriter.removeCoverArt());

        verify(tag).deleteArtworkField();
        verify(audioFile).commit();
    }
}
