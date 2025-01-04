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

package com.josdem.jmetadata.helper;

import com.josdem.jmetadata.model.MetadataAlbumValues;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class MetadataHelper {

  public Set<File> createHashSet() {
    return new HashSet<>();
  }

  public MetadataAlbumValues createMetadataAlbumValues() {
    return new MetadataAlbumValues();
  }
}
