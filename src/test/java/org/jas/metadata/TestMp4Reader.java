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
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.event.Events;
import org.jas.helper.AudioFileHelper;
import org.jas.model.Metadata;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestMp4Reader {
    private static final String TITLE = "Control Freak (Sander Van Doorn Remix)";
    private static final String NULL = "null";

    @InjectMocks
    private final Mp4Reader reader = new Mp4Reader();

    @Mock
    private File file;
    @Mock
    private Mp4Tag tag;
    @Mock
    private AudioFile audioFile;
    @Mock
    private AudioHeader header;
    @Mock
    private Artwork artwork;
    @Mock
    private AudioFileHelper audioFileHelper;
    @Mock
    private BufferedImage bufferedImage;
    @Mock
    private ControlEngineConfigurator configurator;
    @Mock
    private ControlEngine controlEngine;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(audioFileHelper.read(file)).thenReturn(audioFile);
        when(audioFile.getTag()).thenReturn(tag);
        when(audioFile.getAudioHeader()).thenReturn(header);
        when(artwork.getImage()).thenReturn(bufferedImage);
        when(tag.getFirstArtwork()).thenReturn(artwork);
        when(header.getBitRate()).thenReturn("64");
        when(configurator.getControlEngine()).thenReturn(controlEngine);
    }

    @Test
    public void shouldGetAlbum() throws Exception {
        String album = "Simple Pleasures";
        when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(album, metadata.getAlbum());
    }

    @Test
    public void shouldGetGenre() throws Exception {
        String genre = "Minimal Techno";
        when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(genre, metadata.getGenre());
    }

    @Test
    public void shouldGetArtist() throws Exception {
        String artist = "Ferry Corsten";
        when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(artist, metadata.getArtist());
    }

    @Test
    public void shouldGetLength() throws Exception {
        int length = 325;
        when(header.getTrackLength()).thenReturn(length);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(length, metadata.getLength());
    }

    @Test
    public void shouldGetTitle() throws Exception {
        String title = "A Magical Moment";
        when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(title, metadata.getTitle());
    }

    @Test
    public void shouldGetTrackNumber() throws Exception {
        String trackNumber = "11";
        when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(trackNumber, metadata.getTrackNumber());
    }

    @Test
    public void shouldGetTotalTracks() throws Exception {
        String totalTracks = "20";
        when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(totalTracks);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(totalTracks, metadata.getTotalTracks());
    }

    @Test
    public void shouldReturnZEROInTrackNumberWhenTagIsNull() throws Exception {
        when(tag.getFirst(FieldKey.TRACK)).thenReturn(NULL);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getTrackNumber());
    }

    @Test
    public void shouldReturnZEROInTotalTracksWhenTagIsNull() throws Exception {
        when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(NULL);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getTotalTracks());
    }

    @Test
    public void shouldReturnZEROInTrackNumberWhenNullPointer() throws Exception {
        when(tag.getFirst(FieldKey.TRACK)).thenThrow(new NullPointerException());
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getTrackNumber());
    }

    @Test
    public void shouldReturnZEROInTotalTracksWhenNullPointer() throws Exception {
        when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenThrow(new NullPointerException());
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getTotalTracks());
    }

    @Test
    public void shouldReturnZEROInGettingCdNumberWhenNullPointer() throws Exception {
        when(tag.getFirst(FieldKey.DISC_NO)).thenThrow(new NullPointerException());
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getCdNumber());
    }

    @Test
    public void shouldReturnZEROInGettingTotalCdsWhenNullPointer() throws Exception {
        when(tag.getFirst(FieldKey.DISC_TOTAL)).thenThrow(new NullPointerException());
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getTotalCds());
    }

    @Test
    public void shouldReturnZEROInCdNumberWhenTagIsNull() throws Exception {
        when(tag.getFirst(FieldKey.DISC_NO)).thenReturn(NULL);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getCdNumber());
    }

    @Test
    public void shouldReturnZEROInTotalCdsWhenTagIsNull() throws Exception {
        when(tag.getFirst(FieldKey.DISC_TOTAL)).thenReturn(NULL);
        Metadata metadata = reader.getMetadata(file);

        assertEquals(StringUtils.EMPTY, metadata.getTotalCds());
    }

    @Test
    public void shouldGetArtwork() throws Exception {
        reader.getMetadata(file);
        verify(artwork).getImage();
    }

    @Test
    public void shouldNotGetCoverArt() throws Exception {
        when(tag.getFirstArtwork()).thenReturn(artwork);
        when(artwork.getImage()).thenReturn(bufferedImage);

        Metadata metadata = reader.getMetadata(file);

        verify(tag).getFirstArtwork();
        assertEquals(bufferedImage, metadata.getCoverArt());
    }

    @Test
    public void shouldNotGetCoverArtIfNull() throws Exception {
        when(tag.getFirstArtwork()).thenReturn(null);
        Metadata metadata = reader.getMetadata(file);

        verify(tag).getFirstArtwork();
        assertEquals(null, metadata.getCoverArt());
    }

    /**
     * Bug in JAudioTagger null pointer exception when artwork.getImage()
     */
    @Test
    public void shouldNotGetCoverArtIfImageError() throws Exception {
        when(tag.getFirstArtwork()).thenReturn(artwork);
        when(artwork.getImage()).thenThrow(new NullPointerException());
        when(tag.getFirst(FieldKey.TITLE)).thenReturn(TITLE);

        reader.getMetadata(file);

        verify(tag).getFirstArtwork();
        verify(controlEngine).fireEvent(Events.LOAD_COVER_ART, new ValueEvent<String>(TITLE));
    }

}
