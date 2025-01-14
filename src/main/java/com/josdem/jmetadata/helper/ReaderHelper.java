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

import com.josdem.jmetadata.model.GenreTypes;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Service;

@Service
public class ReaderHelper {

  public String getGenre(Tag tag, String genre) {
    try {
      if (genre != null && genre.startsWith("(")) {
        int index = Integer.parseInt(genre.substring(genre.indexOf('(') + 1, genre.indexOf(')')));
        return GenreTypes.getGenreByCode(index);
      } else {
        return tag.getFirst(FieldKey.GENRE);
      }
    } catch (NumberFormatException nue) {
      return tag.getFirst(FieldKey.GENRE);
    }
  }
}
