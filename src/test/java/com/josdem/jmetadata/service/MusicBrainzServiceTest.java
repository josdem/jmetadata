/*
   Copyright 2024 Jose Morales contact@josdem.io

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

import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.MusicBrainzResponse;
import com.josdem.jmetadata.model.Release;
import com.josdem.jmetadata.service.impl.MusicBrainzServiceImpl;
import com.josdem.jmetadata.util.ApplicationState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class MusicBrainzServiceTest {

    @InjectMocks
    private final MusicBrainzService musicBrainzService = new MusicBrainzServiceImpl();

    @Mock
    private RestService restService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getting release id by name")
    void shouldGetReleaseIdByName(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        var albumId = "b04558a9-b69c-45bd-a6f4-d65706067780";
        var expectedAlbum = new Album();
        expectedAlbum.setId(albumId);
        var musicBrainzResponse = getExpectedResponse(albumId);
        ApplicationState.cache.put("Night Life", musicBrainzResponse);
        var call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(expectedAlbum));
        when(restService.getRelease(albumId)).thenReturn(call);
        var result = musicBrainzService.getAlbumByName("Night Life");
        assertEquals(albumId, result.getId());
    }

    private MusicBrainzResponse getExpectedResponse(String expectedId) {
        var musicBrainzResponse = new MusicBrainzResponse();
        Release release = new Release();
        release.setId(expectedId);
        var releases = List.of(release);
        musicBrainzResponse.setReleases(releases);
        return musicBrainzResponse;
    }
}
