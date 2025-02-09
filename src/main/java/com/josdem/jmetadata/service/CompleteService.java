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

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.AlbumInfo;
import com.josdem.jmetadata.model.Metadata;
import java.util.List;

public interface CompleteService {
  /** Determines if the metadata can be completed. */
  boolean canComplete(List<Metadata> metadataList);

  /** Retrieves album information based on the given metadata. */
  AlbumInfo getInfo(Metadata metadata);

  /**
   * Compares the retrieved album information with the current metadata to determine if there is new
   * data.
   */
  ActionResult isSomethingNew(AlbumInfo albumInfo, Metadata metadata);
}
