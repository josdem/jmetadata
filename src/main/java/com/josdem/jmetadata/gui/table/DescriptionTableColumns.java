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

package com.josdem.jmetadata.gui.table;

public enum DescriptionTableColumns {
  ARTIST("Artist", 68, 68),
  TRACK("Track", 180, 180),
  ALBUM("Album", 68, 68),
  GENRE("Genre", 67, 67),
  YEAR("Year", 50, 50),
  N_TRACK("# Trk", 50, 50),
  N_TRACKS("# Trks", 50, 50),
  N_CD("# CD", 50, 50),
  N_CDS("# CDS", 50, 50),
  STATUS("Status", 60, 60);

  private final String name;
  private final int minWidth;
  private final int maxWidth;

  private DescriptionTableColumns(String name, int minWidth, int maxWidth) {
    this.name = name;
    this.minWidth = minWidth;
    this.maxWidth = maxWidth;
  }

  public String label() {
    return getName() == null ? "" : getName();
  }

  public int maxWidth() {
    return maxWidth;
  }

  public int minWidth() {
    return minWidth;
  }

  public String getName() {
    return name;
  }
}
