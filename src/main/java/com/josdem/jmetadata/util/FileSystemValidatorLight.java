/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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

package com.josdem.jmetadata.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystemValidatorLight {

  private final List<File> folderList = new ArrayList<File>();
  private final List<File> trackList = new ArrayList<File>();
  private final List<File> playlistList = new ArrayList<File>();

  public FileSystemValidatorLight(boolean fromExternalDevicesPanel, Iterable<File> files) {
    for (File file : files) {
      validateFile(file);
    }
  }

  private void validateFile(File file) {
    if (file.isHidden()) {
      return;
    }
    if (file.isDirectory()) {
      File[] listFiles = file.listFiles();
      boolean fold = false;
      for (File file2 : listFiles) {
        if (file2.isDirectory() && !file2.isHidden()) {
          fold = true;
          break;
        }
      }
      if (fold) {
        folderList.add(file);
      } else {
        playlistList.add(file);
      }

    } else {
      trackList.add(file);
    }
  }

  public boolean hasError() {
    return false;
  }
}
