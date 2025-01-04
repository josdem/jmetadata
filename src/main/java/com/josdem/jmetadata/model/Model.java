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

import static org.asmatron.messengine.model.ModelId.model;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.asmatron.messengine.model.ModelId;

public interface Model {

  String CURRENT_USER_ID = "application.currentUser";
  ModelId<User> CURRENT_USER = model(CURRENT_USER_ID);

  String METADATA_LIST = "application.metadataList";
  ModelId<List<Metadata>> METADATA = model(METADATA_LIST);

  String METADATA_ARTIST_LIST = "application.metadataArtistList";
  ModelId<Set<Metadata>> METADATA_ARTIST = model(METADATA_ARTIST_LIST);

  String FILES_WITHOUT_MINIMUM_METADATA_LIST = "application.filesWithoutMinimumMetadata";
  ModelId<Set<File>> FILES_WITHOUT_MINIMUM_METADATA = model(FILES_WITHOUT_MINIMUM_METADATA_LIST);
}
