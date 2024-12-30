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

package com.josdem.jmetadata.controller;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.metadata.MetadataWriter;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.LastfmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.Image;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestCompleteController {
    private static final String ERROR = "Error";

    @InjectMocks
    private final CompleteController controller = new CompleteController();

    @Mock
    private MetadataWriter metadataWriter;
    @Mock
    private Metadata metadata;
    @Mock
    private File file;
    @Mock
    private LastfmService coverArtService;
    @Mock
    private CoverArt coverArt;
    @Mock
    private Image imageIcon;

    private String artist = "Dave Deen";
    private String title = "Footprints (Original Mix)";
    private String album = "Footprints EP";
    private String genre = "Trance";
    private String trackNumber = "10";
    private String totalTracks = "25";
    private String year = "1990";
    private String cdNumber = "1";
    private String totalCds = "2";

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(metadata.getArtist()).thenReturn(artist);
        when(metadata.getTitle()).thenReturn(title);
        when(metadata.getAlbum()).thenReturn(album);
        when(metadata.getTrackNumber()).thenReturn(trackNumber);
        when(metadata.getTotalTracks()).thenReturn(totalTracks);
        when(metadata.getCdNumber()).thenReturn(cdNumber);
        when(metadata.getTotalCds()).thenReturn(totalCds);
        when(metadata.getYear()).thenReturn(year);
        when(metadata.getGenre()).thenReturn(genre);
        when(coverArtService.completeLastFM(metadata)).thenReturn(ActionResult.Ready);
    }

    @Test
    public void shouldCompleteAlbumInMetadata() throws Exception {
        when(metadata.getFile()).thenReturn(file);

        ActionResult result = controller.completeAlbum(metadata);

        verify(metadataWriter).setFile(file);
        verify(metadataWriter).writeArtist(artist);
        verify(metadataWriter).writeTitle(title);
        verify(metadataWriter).writeAlbum(album);
        verify(metadataWriter).writeTrackNumber(trackNumber.toString());
        verify(metadataWriter).writeTotalTracksNumber(totalTracks.toString());
        verify(metadataWriter).writeCdNumber(cdNumber);
        verify(metadataWriter).writeTotalCds(totalCds);
        verify(metadataWriter).writeYear(year);
        verify(metadataWriter).writeGenre(genre);
        assertEquals(ActionResult.Updated, result);
    }

    @Test
    public void shouldRemoveCoverArt() throws Exception {
        when(metadata.getNewCoverArt()).thenReturn(coverArt);
        when(coverArt.getImage()).thenReturn(imageIcon);

        ActionResult result = controller.completeAlbum(metadata);

        verify(metadataWriter).removeCoverArt();
        verify(metadataWriter).writeCoverArt(imageIcon);
        verify(metadata).setCoverArt(imageIcon);
        assertEquals(ActionResult.Updated, result);
    }

    @Test
    public void shouldNotUpdateMetadata() throws Exception {
        when(metadata.getFile()).thenReturn(file);
        when(metadataWriter.writeAlbum(album)).thenThrow(new MetadataException(ERROR));

        ActionResult result = controller.completeAlbum(metadata);

        assertEquals(ActionResult.Error, result);
    }

    @Test
    public void shouldCompleteCoverArtMetadata() throws Exception {
        controller.completeLastFmMetadata(metadata);
        verify(coverArtService).completeLastFM(metadata);
    }
}