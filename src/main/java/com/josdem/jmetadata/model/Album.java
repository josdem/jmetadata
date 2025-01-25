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

package com.josdem.jmetadata.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class Album {
  private String date;
  private String barcode;
  private String status;
  private String title;
  private String country;

  @SerializedName("cover-art-archive")
  private CoverArtArchive coverArtArchive;

  @SerializedName("release-events")
  private List<ReleaseEvent> releaseEvents;

  private String packaging;
  private String disambiguation;

  @SerializedName("text-representation")
  private TextRepresentation textRepresentation;

  private String id;
  private String quality;
  private String asin;

  @SerializedName("status-id")
  private String statusId;

  @SerializedName("packaging-id")
  private String packagingId;
}
