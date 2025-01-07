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

package com.josdem.jmetadata.service.impl;

import com.josdem.jmetadata.helper.FormatterHelper;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.FormatterService;
import java.util.Objects;
import java.util.StringTokenizer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormatterServiceImpl implements FormatterService {

  private static final int SIXTY = 60;
  private final FormatterHelper helper;

  public Boolean wasFormatted(Metadata metadata) {
    return wasTitleFormatted(metadata)
        || wasArtistFormatted(metadata)
        || wasAlbumFormatted(metadata);
  }

  private Boolean wasTitleFormatted(Metadata metadata) {
    if (StringUtils.isEmpty(metadata.getTitle())) {
      return false;
    }
    String newTitle =
        metadata
            .getTitle()
            .replace("&amp;", "&")
            .replace("`", "'")
            .replace("&eacute;", "é")
            .replace("&aacute;", "á")
            .replace("&iacute;", "í")
            .replace("&oacute;", "ó")
            .replace("&uacute;", "ú")
            .replace("&acute;", "'")
            .replace("&egrave;", "e")
            .replace("&Eacute;", "É")
            .replace("&ecirc;", "ê")
            .replace("&uuml;", "ü")
            .replace("&ouml;", "ö")
            .replace("&agrave;", "a")
            .replace("&deg;", "°")
            .replace("&ntilde;", "ñ")
            .replace("&auml;", "ä")
            .replace("&aring;", "å")
            .replace("&ucirc;", "û");
    if (!newTitle.equals(metadata.getTitle())) {
      metadata.setTitle(newTitle);
      return true;
    }
    return false;
  }

  private Boolean wasArtistFormatted(Metadata metadata) {
    if (StringUtils.isEmpty(metadata.getArtist())) {
      return false;
    }
    String newArtist =
        metadata
            .getArtist()
            .replace("&amp;", "&")
            .replace("`", "'")
            .replace("&eacute;", "é")
            .replace("&aacute;", "á")
            .replace("&iacute;", "í")
            .replace("&oacute;", "ó")
            .replace("&uacute;", "ú")
            .replace("&acute;", "'")
            .replace("&egrave;", "è")
            .replace("&Eacute;", "É")
            .replace("&ecirc;", "ê")
            .replace("&uuml;", "ü")
            .replace("&ouml;", "ö")
            .replace("&agrave;", "a")
            .replace("&deg;", "°")
            .replace("&ntilde;", "ñ")
            .replace("&auml;", "ä")
            .replace("&aring;", "å")
            .replace("&ucirc;", "û");
    if (!newArtist.equals(metadata.getArtist())) {
      metadata.setArtist(newArtist);
      return true;
    }
    return false;
  }

  private Boolean wasAlbumFormatted(Metadata metadata) {
    if (StringUtils.isEmpty(metadata.getAlbum())) {
      return false;
    }
    String newAlbum =
        metadata
            .getAlbum()
            .replace("&amp;", "&")
            .replace("`", "'")
            .replace("&eacute;", "é")
            .replace("&aacute;", "á")
            .replace("&iacute;", "í")
            .replace("&oacute;", "ó")
            .replace("&uacute;", "ú")
            .replace("&acute;", "'")
            .replace("&egrave;", "è")
            .replace("&Eacute;", "É")
            .replace("&ecirc;", "ê")
            .replace("&uuml;", "ü")
            .replace("&ouml;", "ö")
            .replace("&agrave;", "a")
            .replace("&deg;", "°")
            .replace("&ntilde;", "ñ")
            .replace("&auml;", "ä")
            .replace("&aring;", "å")
            .replace("&ucirc;", "û");
    if (!newAlbum.equals(metadata.getAlbum())) {
      metadata.setAlbum(newAlbum);
      return true;
    }
    return false;
  }

  public Boolean wasCamelized(Metadata metadata) {
    Boolean camelized = false;
    String artistFormatted = helper.getBasicFormat(metadata.getArtist());
    String titleFormatted = helper.getBasicFormat(metadata.getTitle());
    String albumFormatted = helper.getBasicFormat(metadata.getAlbum());
    if (StringUtils.isAllLowerCase(titleFormatted) || StringUtils.isAllUpperCase(titleFormatted)) {
      metadata.setTitle(toCamelCase(metadata.getTitle()));
      camelized = true;
    }
    if (StringUtils.isAllLowerCase(artistFormatted)
        || StringUtils.isAllUpperCase(artistFormatted)) {
      metadata.setArtist(toCamelCase(metadata.getArtist()));
      camelized = true;
    }
    if (StringUtils.isAllLowerCase(albumFormatted) || StringUtils.isAllUpperCase(albumFormatted)) {
      metadata.setAlbum(toCamelCase(metadata.getAlbum()));
      camelized = true;
    }
    return camelized;
  }

  private String toCamelCase(String value) {
    StringTokenizer stringTokenizer = new StringTokenizer(value.toLowerCase());
    StringBuilder stringBuilder = new StringBuilder();
    while (stringTokenizer.hasMoreTokens()) {
      stringBuilder.append(StringUtils.capitalize(stringTokenizer.nextToken()));
      stringBuilder.append(" ");
    }
    String[] strings = StringUtils.split(stringBuilder.toString(), " ");
    return StringUtils.join(strings, " ");
  }

  public String getDuration(int length) {
    int seconds = length % SIXTY;
    int minutes = (length - seconds) / SIXTY;
    return minutes + ":" + seconds;
  }

  public void isAnalyzable(Metadata metadata) {
    Objects.requireNonNull(metadata.getTitle());
    Objects.requireNonNull(metadata.getArtist());
    Objects.requireNonNull(metadata.getAlbum());
  }
}
