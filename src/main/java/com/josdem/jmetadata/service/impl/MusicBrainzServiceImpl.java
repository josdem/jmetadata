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

package com.josdem.jmetadata.service.impl;

import com.josdem.jmetadata.exception.BusinessException;
import com.josdem.jmetadata.helper.RetrofitHelper;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.service.RestService;
import com.josdem.jmetadata.util.ApplicationState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class MusicBrainzServiceImpl implements MusicBrainzService {

    private RestService restService;

    @PostConstruct
    void setup() {
        restService = RetrofitHelper.getRetrofit().create(RestService.class);
    }

    public Album getAlbumByName(String name) {
        log.info("Getting release");
        var musicBrainzResponse = ApplicationState.cache.get(name);
        String id = musicBrainzResponse.getReleases().getFirst().getId();
        var call = restService.getRelease(id);
        try {
            Response<Album> response = call.execute();
            return response.body();
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
