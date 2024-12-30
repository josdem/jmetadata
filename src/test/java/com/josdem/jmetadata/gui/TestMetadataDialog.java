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

package com.josdem.jmetadata.gui;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.fest.swing.fixture.FrameFixture;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.helper.MetadataHelper;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MetadataAlbumValues;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.util.Environment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestMetadataDialog {
    private static final int HEIGHT = 450;
    private static final int WIDTH = 400;
    private static final String ARTIST = "Armin Van Buuren";
    private static final String ALBUM = "Mirage";
    private static final String GENRE = "Trance";
    private static final String YEAR = "2010";
    private static final String TRACKS = "16";
    private static final String CD = "1";
    private static final String CDS = "1";

    private static final String ARTIST_INPUT = "artistTextField";
    private static final String ALBUM_INPUT = "albumTextField";
    private static final String GENRE_INPUT = "genreTextField";
    private static final String YEAR_INPUT = "yearTextField";
    private static final String TRACKS_INPUT = "tracksTextField";
    private static final String CD_INPUT = "cdTextField";
    private static final String CDS_INPUT = "cdsTextField";

    private static final String APPLY_BUTTON_NAME = "buttonApply";

    @Mock
    private ControlEngineConfigurator controlEngineConfigurator;
    @Mock
    private ControlEngine controlEngine;
    @Mock
    private MetadataHelper metadataHelper;
    @Mock
    private MetadataAlbumValues metadataAlbumValues;
    @Mock
    private Metadata metadata;

    private FrameFixture window;
    private final JFrame frame = new JFrame();
    private final MainWindow mainWindow = new MainWindow();
    private final List<Metadata> metadatas = new ArrayList<>();

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(controlEngineConfigurator.getControlEngine()).thenReturn(controlEngine);
    }

    @Test
    public void shouldSetAlbumValues() throws Exception {
        //Avoid running in Linux since is not working properly
        if (!Environment.isLinux()) {
            metadatas.add(metadata);
            when(metadataHelper.createMetadataAlbumVaues()).thenReturn(metadataAlbumValues);
            when(controlEngine.get(Model.METADATA)).thenReturn(metadatas);

            String message = "message";
            MetadataDialog metadataDialog = new MetadataDialog(mainWindow, controlEngineConfigurator, message);
            metadataDialog.setMetadataHelper(metadataHelper);
            frame.add(metadataDialog.getContentPane());
            window = new FrameFixture(frame);
            window.show();
            window.resizeTo(new Dimension(WIDTH, HEIGHT));
            window.textBox(ARTIST_INPUT).enterText(ARTIST);
            window.textBox(ALBUM_INPUT).enterText(ALBUM);
            window.textBox(GENRE_INPUT).enterText(GENRE);
            window.textBox(YEAR_INPUT).enterText(YEAR);
            window.textBox(TRACKS_INPUT).enterText(TRACKS);
            window.textBox(CD_INPUT).enterText(CD);
            window.textBox(CDS_INPUT).enterText(CDS);

            window.button(APPLY_BUTTON_NAME).click();

            verify(metadataAlbumValues).setArtist(ARTIST);
            verify(metadataAlbumValues).setAlbum(ALBUM);
            verify(metadataAlbumValues).setGenre(GENRE);
            verify(metadataAlbumValues).setYear(YEAR);
            verify(metadataAlbumValues).setTracks(TRACKS);
            verify(metadataAlbumValues).setCd(CD);
            verify(metadataAlbumValues).setCds(CDS);
            verify(controlEngine).fireEvent(Events.READY_TO_APPLY, new ValueEvent<MetadataAlbumValues>(metadataAlbumValues));
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (!Environment.isLinux()) {
            window.cleanUp();
        }
    }

}
