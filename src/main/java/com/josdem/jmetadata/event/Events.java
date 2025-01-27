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

package com.josdem.jmetadata.event;

import static org.asmatron.messengine.event.EventId.ev;

import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MetadataAlbumValues;
import com.josdem.jmetadata.model.User;
import java.util.List;
import org.asmatron.messengine.event.EmptyEvent;
import org.asmatron.messengine.event.EventId;
import org.asmatron.messengine.event.ValueEvent;

public interface Events {

  String USER_LOGGED = "userLogged";
  EventId<ValueEvent<User>> LOGGED = ev(USER_LOGGED);

  String USER_LOGIN_FAILED = "userLoginFailed";
  EventId<EmptyEvent> LOGIN_FAILED = ev(USER_LOGIN_FAILED);

  String MUSIC_DIRECTORY_SELECTED = "musicDirectorySelected";
  EventId<ValueEvent<String>> DIRECTORY_SELECTED = ev(MUSIC_DIRECTORY_SELECTED);

  String MUSIC_DIRECTORY_NOT_EXIST = "musicDirectoryNotExist";
  EventId<ValueEvent<String>> DIRECTORY_NOT_EXIST = ev(MUSIC_DIRECTORY_NOT_EXIST);

  String TOO_MUCH_FILES_LOADED = "tooMuchFilesLoaded";
  EventId<ValueEvent<String>> MUCH_FILES_LOADED = ev(TOO_MUCH_FILES_LOADED);

  String MUSIC_DIRECTORY_SELECTED_CANCEL = "musicDirectorySelectedCancel";
  EventId<EmptyEvent> DIRECTORY_SELECTED_CANCEL = ev(MUSIC_DIRECTORY_SELECTED_CANCEL);

  String TRACKS_LOADED = "tracksLoaded";
  EventId<EmptyEvent> LOADED = ev(TRACKS_LOADED);

  String DIRECTORY_EMPTY_EVENT = "directoryEmptyEvent";
  EventId<EmptyEvent> DIRECTORY_EMPTY = ev(DIRECTORY_EMPTY_EVENT);

  String LOAD_METADATA = "loadMetadata";
  EventId<ValueEvent<List<Metadata>>> LOAD = ev(LOAD_METADATA);

  String OPEN_ERROR = "openError";
  EventId<EmptyEvent> OPEN = ev(OPEN_ERROR);

  String APPLY_METADATA = "readyToApplyMetadata";
  EventId<ValueEvent<MetadataAlbumValues>> READY_TO_APPLY = ev(APPLY_METADATA);

  String COVER_ART_FAILED = "coverArtFailed";
  EventId<ValueEvent<String>> LOAD_COVER_ART = ev(COVER_ART_FAILED);

  String LOAD_FILE_FAILED = "loadFileFailed";
  EventId<ValueEvent<String>> LOAD_FILE = ev(LOAD_FILE_FAILED);
}
