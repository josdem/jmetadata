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

package com.josdem.jmetadata;

public interface ApplicationConstants {
  String LOGIN_FAIL = "Login fail";
  String LOGGED_AS = "Logged as : ";
  String DONE = "Done";
  String OPEN_ERROR = "Error on importing Music";
  String WORKING = "Working";
  int ARTIST_COLUMN = 0;
  int TITLE_COLUMN = 1;
  int ALBUM_COLUMN = 2;
  int GENRE_COLUMN = 3;
  int YEAR_COLUMN = 4;
  int TRACK_NUMBER_COLUMN = 5;
  int TOTAL_TRACKS_NUMBER_COLUMN = 6;
  int CD_NUMBER_COLUMN = 7;
  int TOTAL_CDS_NUMBER_COLUMN = 8;
  int STATUS_COLUMN = 9;
  int WIDTH = 1024;
  int HEIGHT = 600;
  String COVER_ART_FROM_FILE = "Cover Art from File";
  String COVER_ART_FROM_LASTFM = "Cover Art from Lastfm";
  String COVER_ART_FROM_MUSIC_BRAINZ = "Cover Art from MusicBrainz";
  String COVER_ART_FROM_DRAG_AND_DROP = "Cover Art from Drag & Drop";
  String COVER_ART_DEFAULT = "Covert Art not found";
  String APPLY = "Apply";
  String EXPORT = "Export";
  String NEW = "New";
  String READY = "Ready";
  String APPLICATION_NAME = "JMetadata";
  String IMAGE_EXT = "PNG";
  String FILE_EXT = "txt";
  String PREFIX = "JMetadata_";
  String GETTING_ALBUM = "Getting Album from Musicbrainz";
  String GETTING_LAST_FM = "Getting Last.fm Metadata";
  String COMPLETE_DEFAULT_VALUES = "Completing default values";
  String GETTING_FORMATTER = "Formatting metadata";
  String WRITING_METADATA = "Writing Metadata";
  String USERNAME_LABEL = "username:";
  String PASSWORD_LABEL = "password:";
  int THREE_HUNDRED = 300;
  String CORRUPTED_METADATA_LABEL = " has a corrupted coverArt";
  String METADATA_FROM_FILE_LABEL = " title and artist metadata were extracted from file name";
  String AND_ANOTHER = " and another ";
  String DIRECTORY_EMPTY = "I could not find any mp3 or mp4 audio file in the directory";
  String DIRECTORY_NOT_FOUND = "I could not find that directory to scan: ";
  String FILE_NOT_FOUND = "I could not find this file: ";
  String TOO_MUCH_FILES_LOADED = "Too much files loaded, maximum allowed: ";
  String MEDIA_TYPE = "application/json";
  String USER_AGENT = "JMetadata/1.1.0 (contact@josdem.io)";
}
