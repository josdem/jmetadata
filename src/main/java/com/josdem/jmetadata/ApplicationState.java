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

package com.josdem.jmetadata;

import java.io.File;


/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands define ALL constants on JaudioScrobbler
 */

public interface ApplicationState {
	static String DESTFILE_PATH = System.getProperty("java.io.tmpdir") + File.separator;

	static final String LOGIN_FAIL = "Login fail";
	static final String LOGGED_AS = "Logged as : ";
	static final String DONE = "Done";
	static final String NETWORK_ERROR = "Internet Connection Error";
	static final String OPEN_ERROR = "Error on importing Music";
	static final String WORKING = "Working";
	static final int ARTIST_COLUMN = 0;
	static final int TITLE_COLUMN = 1;
	static final int ALBUM_COLUMN = 2;
	static final int GENRE_COLUMN = 3;
	static final int YEAR_COLUMN = 4;
	static final int TRACK_NUMBER_COLUMN = 5;
	static final int TOTAL_TRACKS_NUMBER_COLUMN = 6;
	static final int CD_NUMBER_COLUMN = 7;
	static final int TOTAL_CDS_NUMBER_COLUMN = 8;
	static final int STATUS_COLUMN = 9;
	static final int WIDTH = 1024;
	static final int HEIGHT = 600;
	static final String COVER_ART_FROM_FILE = "Cover Art from File";
	static final String COVER_ART_FROM_LASTFM = "Cover Art from Lastfm";
	static final String COVER_ART_FROM_DRAG_AND_DROP = "Cover Art from Drag & Drop";
	static final String COVER_ART_DEFAULT = "Covert Art not found";
	static final String APPLY = "Apply";
	static final String EXPORT = "Export";
	static final String NEW = "New";
	static final String READY = "Ready";
	static final String APPLICATION_NAME = "JMetadata";
	static final String IMAGE_EXT = "PNG";
	static final String FILE_EXT = "txt";
	static final String PREFIX = "JMetadata_";
	static final String GETTING_ALBUM = "Getting Album from Musicbrainz";
	static final String GETTING_LAST_FM = "Getting Last.fm Metadata";
	static final String COMPLETE_DEFAULT_VALUES = "Completing default values";
	static final String GETTING_FORMATTER = "Formatting metadata";
	static final String WRITING_METADATA = "Writing Metadata";
	static final String USERNAME_LABEL = "username:";
	static final String PASSWORD_LABEL = "password:";
	static final int THREE_HUNDRED = 300;
	static final String CORRUPTED_METADATA_LABEL = " has a corrupted coverArt";
	static final String METADATA_FROM_FILE_LABEL = " title and artist metadata were extracted from file name";
	static final String AND_ANOTHER = " and another ";
	static final String DIRECTORY_EMPTY = "I could not find any mp3 or mp4 audio file in the directory";
	static final String DIRECTORY_NOT_FOUND = "I could not find that directory to scan: ";
	static final String FILE_NOT_FOUND = "I could not find this file: ";
	static final String TOO_MUCH_FILES_LOADED = "Too much files loaded, maximum allowed: ";
}
