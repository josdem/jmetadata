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

package com.josdem.jmetadata.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class Release {
  private String id;
  private int score;

  @SerializedName("status-id")
  private String statusId;

  private int count;
  private String title;
  private String status;

  @SerializedName("text-representation")
  private TextRepresentation textRepresentation;

  @SerializedName("artist-credit")
  private List<ArtistCredit> artistCredit;

  @SerializedName("release-events")
  private List<ReleaseEvent> releaseEvents;

  @SerializedName("release-group")
  private ReleaseGroup releaseGroup;

  private String date;
  private String country;
  private String barcode;

  @SerializedName("label-info")
  private List<LabelInfo> labelInfo;

  @SerializedName("track-count")
  private int trackCount;

  private List<Media> media;
}
