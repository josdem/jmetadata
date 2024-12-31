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

import com.josdem.jmetadata.model.MusicBrainzResponse;
import com.josdem.jmetadata.model.Release;
import com.josdem.jmetadata.service.impl.MusicBrainzServiceImpl;
import com.josdem.jmetadata.util.ApplicationState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class MusicBrainzServiceTest {
    private final MusicBrainzService musicBrainzService = new MusicBrainzServiceImpl();

    @Test
    @DisplayName("getting release id by name")
    void shouldGetReleaseIdByName(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        var expectedId = "b04558a9-b69c-45bd-a6f4-d65706067780";
        var musicBrainzResponse = getExpectedResponse(expectedId);
        ApplicationState.cache.put("Night Life", musicBrainzResponse);
        var result = musicBrainzService.getAlbumByName("Night Life");
        assertEquals(expectedId, result.getId());
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
