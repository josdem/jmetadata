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

package com.josdem.jmetadata.service;

import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.MusicBrainzResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestService {
  @Headers({
    "Accept: " + ApplicationConstants.MEDIA_TYPE,
    "User-Agent: " + ApplicationConstants.USER_AGENT
  })
  @GET("release")
  Call<MusicBrainzResponse> getReleases(@Query("query") String query);

  @Headers({
    "Accept: " + ApplicationConstants.MEDIA_TYPE,
    "User-Agent: " + ApplicationConstants.USER_AGENT
  })
  @GET("release/{id}")
  Call<Album> getRelease(@Path("id") String id);
}
