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

package com.josdem.jmetadata.model;

import java.awt.Image;
import java.io.File;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class Metadata implements Comparable<Metadata> {
  private String title;
  private String artist;
  private String album;
  private String genre;
  private String trackNumber;
  private String totalTracks;
  private Image coverArt;
  private int length;
  private int bitRate;
  private File file;
  private String cdNumber;
  private String totalCds;
  private String year;
  private CoverArt newCoverArt;
  private boolean metadataFromFile;
  private boolean orderByFile = false;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public int compareTo(Metadata metadata) {
    if (metadata.isOrderByFile()) {
      return getFile().getName().compareTo(metadata.getFile().getName());
    }
    try {
      var thisTrackNumber = Integer.parseInt(getTrackNumber());
      return Integer.compare(thisTrackNumber, Integer.parseInt(metadata.getTrackNumber()));
    } catch (NumberFormatException nfe) {
      log.info(
          "Metadata : {} has an incorrect trackNumber: {}", metadata.getTitle(), nfe.getMessage());
      return 0;
    }
  }
}
