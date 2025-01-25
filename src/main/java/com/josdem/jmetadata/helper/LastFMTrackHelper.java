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

import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleResult;

public class LastFMTrackHelper {

  public ScrobbleResult scrobble(String artist, String title, int time, Session session) {
    return Track.scrobble(artist, title, time, session);
  }
}
