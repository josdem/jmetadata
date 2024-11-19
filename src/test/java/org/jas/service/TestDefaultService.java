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

package org.jas.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jas.model.Metadata;
import org.jas.service.impl.DefaultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestDefaultService {

    private static final String TOTAL_TRACKS = "2";
    private static final String TRACK_NUMBER_METADATA_ONE = "1";
    private static final String CD_NUMBER = "1";
    private static final String TOTAL_CD_NUMBER = "1";
    private static final Log log = LogFactory.getLog(TestDefaultService.class);

    @InjectMocks
    private final DefaultService defaultService = new DefaultServiceImpl();

    @Mock
    private Metadata metadata_one;
    @Mock
    private Metadata metadata_two;
    @Mock
    private MetadataService metadataService;

    private final List<Metadata> metadatas = new ArrayList<>();

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("validating we can complete metadata due to we do not have total tracks")
    public void shouldCompleteTotalTracks(TestInfo testInfo) throws Exception {
        log.info("Running test: {}" + testInfo.getDisplayName());
        when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        setTracksNumberExpectations();
        when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

        assertTrue(defaultService.isCompletable(metadatas));
    }

    @Test
    @DisplayName("validating we can complete metadata due to we do not have cd number")
    public void shouldCompleteCdNumber(TestInfo testInfo) throws Exception {
        log.info("Running test: {}" + testInfo.getDisplayName());
        when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        setTracksNumberExpectations();
        when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

        assertTrue(defaultService.isCompletable(metadatas));
    }

    @Test
    @DisplayName("validating we can complete metadata due to we do not have total cds")
    public void shouldCompleteTotalCds(TestInfo testInfo) throws Exception {
        log.info("Running test: {}" + testInfo.getDisplayName());
        when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
        setTracksNumberExpectations();
        when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

        assertTrue(defaultService.isCompletable(metadatas));
    }

    @Test
    @DisplayName("validating we can not complete metadata due to we have one track")
    public void shouldNotCompleteIfSingleTrack(TestInfo testInfo) throws Exception {
        log.info("Running test: {}" + testInfo.getDisplayName());
        var metadatas = new ArrayList<Metadata>();

        when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadataService.isSameAlbum(metadatas)).thenReturn(true);
        metadatas.add(metadata_one);

        defaultService.isCompletable(metadatas);

        verify(metadata_one, never()).setTotalTracks(TOTAL_TRACKS);
        verify(metadata_one, never()).setCdNumber(CD_NUMBER);
        verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
    }

    @Test
    public void shouldComplete() throws Exception {
        when(metadataService.isSameAlbum(metadatas)).thenReturn(true);
        setTracksNumberExpectations();

        defaultService.complete(metadatas);

        verify(metadata_one).setTotalTracks(TOTAL_TRACKS);
        verify(metadata_one).setCdNumber(CD_NUMBER);
        verify(metadata_one).setTotalCds(TOTAL_CD_NUMBER);
        verify(metadata_two).setTotalTracks(TOTAL_TRACKS);
        verify(metadata_two).setCdNumber(CD_NUMBER);
        verify(metadata_two).setTotalCds(TOTAL_CD_NUMBER);
    }

    private void setTracksNumberExpectations() {
        when(metadata_one.getTrackNumber()).thenReturn(TRACK_NUMBER_METADATA_ONE);
        when(metadata_two.getTrackNumber()).thenReturn(TOTAL_TRACKS);
        metadatas.add(metadata_one);
        metadatas.add(metadata_two);
    }

    @Test
    public void shouldNotCompleteMetadataWhenNoNecesary() throws Exception {
        when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
        when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
        when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
        when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

        assertFalse(defaultService.isCompletable(metadatas));
    }

    @Test
    public void shouldNotCompleteWhenNoTrackNumber() {
        when(metadata_one.getTotalTracks()).thenReturn(StringUtils.EMPTY);
        metadatas.add(metadata_one);
        defaultService.complete(metadatas);
        verify(metadata_one, never()).setCdNumber(CD_NUMBER);
        verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
    }

}
