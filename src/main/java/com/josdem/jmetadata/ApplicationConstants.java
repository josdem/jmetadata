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

package com.josdem.jmetadata;

public final class ApplicationConstants {

  private ApplicationConstants() {}

  public static final String LOGIN_FAIL = "Login fail";
  public static final String LOGGED_AS = "Logged as : ";
  public static final String DONE = "Done";
  public static final String OPEN_ERROR = "Error on importing Music";
  public static final String WORKING = "Working";

  public static final int ARTIST_COLUMN = 0;
  public static final int TITLE_COLUMN = 1;
  public static final int ALBUM_COLUMN = 2;
  public static final int GENRE_COLUMN = 3;
  public static final int YEAR_COLUMN = 4;
  public static final int TRACK_NUMBER_COLUMN = 5;
  public static final int TOTAL_TRACKS_NUMBER_COLUMN = 6;
  public static final int CD_NUMBER_COLUMN = 7;
  public static final int TOTAL_CDS_NUMBER_COLUMN = 8;
  public static final int STATUS_COLUMN = 9;

  public static final int WIDTH = 1024;
  public static final int HEIGHT = 600;

  public static final String COVER_ART_FROM_FILE = "Cover Art from File";
  public static final String COVER_ART_FROM_LASTFM = "Cover Art from Lastfm";
  public static final String COVER_ART_FROM_MUSIC_BRAINZ = "Cover Art from MusicBrainz";
  public static final String COVER_ART_FROM_DRAG_AND_DROP = "Cover Art from Drag & Drop";
  public static final String COVER_ART_DEFAULT = "Cover Art not found";

  public static final String APPLY = "Apply";
  public static final String EXPORT = "Export";
  public static final String NEW = "New";
  public static final String READY = "Ready";

  public static final String APPLICATION_NAME = "JMetadata";
  public static final String IMAGE_EXT = "PNG";
  public static final String FILE_EXT = "txt";
  public static final String PREFIX = "JMetadata_";

  public static final String GETTING_ALBUM = "Getting Album from Musicbrainz";
  public static final String GETTING_LAST_FM = "Getting Last.fm Metadata";
  public static final String COMPLETE_DEFAULT_VALUES = "Completing default values";
  public static final String GETTING_FORMATTER = "Formatting metadata";
  public static final String WRITING_METADATA = "Writing Metadata";

  public static final String USERNAME_LABEL = "username:";
  public static final String PASSWORD_LABEL = "password:";

  public static final int THREE_HUNDRED = 300;

  public static final String CORRUPTED_METADATA_LABEL = " has a corrupted coverArt";
  public static final String METADATA_FROM_FILE_LABEL =
      " title and artist metadata were extracted from file name";
  public static final String AND_ANOTHER = " and another ";
  public static final String DIRECTORY_EMPTY =
      "I could not find any mp3 or mp4 audio file in the directory";
  public static final String DIRECTORY_NOT_FOUND = "I could not find that directory to scan: ";
  public static final String FILE_NOT_FOUND = "I could not find this file: ";
  public static final String TOO_MUCH_FILES_LOADED = "Too much files loaded, maximum allowed: ";

  public static final String MEDIA_TYPE = "application/json";
  public static final String USER_AGENT = "JMetadata/1.1.0 (contact@josdem.io)";
}
