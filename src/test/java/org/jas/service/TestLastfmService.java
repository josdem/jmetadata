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

import org.jas.action.ActionResult;
import org.jas.model.LastfmAlbum;
import org.jas.model.Metadata;
import org.jas.service.impl.LastfmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class TestLastfmService {

    @InjectMocks
    private LastfmService lastfmService = new LastfmServiceImpl();

    @Mock
    private Metadata metadata;
    @Mock
    private Image imageIcon;
    @Mock
    private LastFMCompleteService completeService;
    @Mock
    private LastfmAlbum lastfmAlbum;

    private String genre = "Minimal Techno";

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true);
    }

    @Test
    public void shouldCompleteMetadataFromLastfm() throws Exception {
        setCompleteHelperExpectations();
        when(lastfmAlbum.getImageIcon()).thenReturn(imageIcon);
        when(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.New);

        ActionResult result = lastfmService.completeLastFM(metadata);

        verify(completeService).isSomethingNew(lastfmAlbum, metadata);
        assertEquals(ActionResult.New, result);
    }

    @Test
    public void shouldNotCompleteLastfmCoverArtMetadataDueToMetadataComplete() throws Exception {
        when(completeService.canLastFMHelpToComplete(metadata)).thenReturn(false);

        ActionResult result = lastfmService.completeLastFM(metadata);
        assertEquals(ActionResult.Ready, result);
    }

    @Test
    public void shouldNotCompleteGenreIfAlredyHasOne() throws Exception {
        setCompleteHelperExpectations();
        when(metadata.getGenre()).thenReturn(genre);

        lastfmService.completeLastFM(metadata);

        verify(metadata, never()).setGenre(isA(String.class));
    }

    @Test
    public void shouldReturnMetadataCompleteIfLastfmHasNotNewValues() throws Exception {
        setCompleteHelperExpectations();
        when(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.Ready);

        ActionResult result = lastfmService.completeLastFM(metadata);
        assertEquals(ActionResult.Ready, result);
    }

    @Test
    public void shouldReturnSomethingnewValueIfNoBadFormatAndCapitalized() throws Exception {
        setCompleteHelperExpectations();
        when(completeService.isSomethingNew(lastfmAlbum, metadata)).thenReturn(ActionResult.New);

        ActionResult result = lastfmService.completeLastFM(metadata);

        assertEquals(ActionResult.New, result);
    }

    private void setCompleteHelperExpectations() throws MalformedURLException,
            IOException {
        when(completeService.canLastFMHelpToComplete(metadata)).thenReturn(true);
        when(completeService.getLastFM(metadata)).thenReturn(lastfmAlbum);
    }

    @Test
    public void shouldCatchMalformedURLException() throws Exception {
        when(completeService.getLastFM(metadata)).thenThrow(new MalformedURLException());

        ActionResult result = lastfmService.completeLastFM(metadata);

        assertEquals(ActionResult.Error, result);
    }

    @Test
    public void shouldCatchIOException() throws Exception {
        when(completeService.getLastFM(metadata)).thenThrow(new IOException());

        ActionResult result = lastfmService.completeLastFM(metadata);

        assertEquals(ActionResult.Error, result);
    }

}
