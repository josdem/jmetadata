/*
   Copyright 2025 Jose Morales contact@josdem.io

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
import com.josdem.jmetadata.helper.RetrofitInstance;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.CoverArtResponse;
import com.josdem.jmetadata.model.CoverArtType;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.ImageService;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.service.RestService;
import com.josdem.jmetadata.util.AlbumUtils;
import com.josdem.jmetadata.util.ApplicationState;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Slf4j
@Service
@Setter
@RequiredArgsConstructor
public class MusicBrainzServiceImpl implements MusicBrainzService {

  private RestService restService;
  private final ImageService imageService;
  private final RetrofitInstance retrofitInstance;

  @PostConstruct
  public void setup() {
    restService = retrofitInstance.getRetrofit().create(RestService.class);
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

  @Override
  public List<Metadata> completeYear(List<Metadata> metadataList, Album album) {
    log.info("Trying to complete year from album");
    if (StringUtils.isEmpty(album.getDate()) || StringUtils.isBlank(album.getDate())) {
      throw new BusinessException("Album date is not valid");
    }
    metadataList.forEach(
        metadata -> {
          if (StringUtils.isEmpty(metadata.getYear()) || StringUtils.isBlank(metadata.getYear())) {
            metadata.setYear(AlbumUtils.formatYear(album.getDate()));
          }
        });
    return metadataList;
  }

  @Override
  public List<Metadata> completeCoverArt(
      List<Metadata> metadataList, CoverArtResponse coverArtResponse) {
    log.info("Trying to complete cover art from response");
    if (coverArtResponse.getImages().isEmpty()) {
      throw new BusinessException("Cover art is not available");
    }
    var coverArtUrl = coverArtResponse.getImages().getFirst().getThumbnails().getLarge();
    try {
      var coverArtImage = imageService.readImage(coverArtUrl);
      metadataList.forEach(
          metadata -> {
            if (metadata.getCoverArt() == null) {
              metadata.setCoverArt(coverArtImage);
              CoverArt coverArt = new CoverArt(coverArtImage, CoverArtType.MUSIC_BRAINZ);
              metadata.setNewCoverArt(coverArt);
            }
          });
    } catch (IOException ioe) {
      throw new BusinessException("Error reading image: " + ioe.getMessage());
    }
    return metadataList;
  }
}
