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

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.helper.LastFMAlbumHelper;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.CoverArtType;
import com.josdem.jmetadata.model.LastfmAlbum;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.ImageService;
import com.josdem.jmetadata.service.LastFMCompleteService;
import com.josdem.jmetadata.util.ApplicationState;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;
import java.awt.Image;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastFMCompleteServiceImpl implements LastFMCompleteService {

  private final ImageService imageService;
  private final LastFMAlbumHelper lastfmHelper;

  public boolean canLastFMHelpToComplete(Metadata metadata) {
    var artist = metadata.getArtist();
    var album = metadata.getAlbum();
    return (isMetadataIncomplete(metadata) && hasAlbumAndArtist(artist, album));
  }

  private boolean hasAlbumAndArtist(String artist, String album) {
    return (!StringUtils.isEmpty(album) && !StringUtils.isEmpty(artist));
  }

  private boolean isMetadataIncomplete(Metadata metadata) {
    return (metadata.getCoverArt() == null
        || StringUtils.isEmpty(metadata.getYear())
        || StringUtils.isEmpty(metadata.getGenre()));
  }

  public LastfmAlbum getLastFM(Metadata metadata) throws IOException {
    if (ApplicationState.lastFmCache.get(metadata.getAlbum()) == null) {
      var album = lastfmHelper.getAlbum(metadata.getArtist(), metadata.getAlbum());
      if (album != null) {
        ApplicationState.lastFmCache.put(metadata.getAlbum(), album);
      }
    }
    var lastfmAlbum = new LastfmAlbum();
    setCoverArt(metadata, lastfmAlbum);
    setYear(metadata, lastfmAlbum);
    setGenre(metadata, lastfmAlbum);
    return lastfmAlbum;
  }

  private void setCoverArt(Metadata metadata, LastfmAlbum lastfmAlbum) throws IOException {
    if (metadata.getCoverArt() != null) {
      return;
    }
    String imageURL = StringUtils.EMPTY;
    Album album = ApplicationState.lastFmCache.get(metadata.getAlbum());
    if (album != null) {
      imageURL = album.getImageURL(ImageSize.EXTRALARGE);
      log.info("imageURL: {} from album: {}", imageURL, album.getName());
    }
    if (!StringUtils.isEmpty(imageURL)) {
      Image image = imageService.readImage(imageURL);
      lastfmAlbum.setImageIcon(image);
    }
  }

  private void setGenre(Metadata metadata, LastfmAlbum lastfmAlbum) {
    if (!StringUtils.isEmpty(metadata.getGenre()) || StringUtils.isEmpty(metadata.getAlbum())) {
      return;
    }
    Album album = ApplicationState.lastFmCache.get(metadata.getAlbum());
    String genre = StringUtils.EMPTY;
    if (album != null) {
      genre = lastfmHelper.getGenre(album);
    }
    if (!StringUtils.isEmpty(genre)) {
      log.info("Genre from lastFM: {}", genre);
      lastfmAlbum.setGenre(genre);
    } else {
      lastfmAlbum.setGenre(StringUtils.EMPTY);
    }
  }

  private void setYear(Metadata metadata, LastfmAlbum lastfmAlbum) {
    if (!StringUtils.isEmpty(metadata.getYear())) {
      return;
    }
    Date release = null;
    Album album = ApplicationState.lastFmCache.get(metadata.getAlbum());
    if (album != null) {
      release = album.getReleaseDate();
    }
    if (release != null) {
      log.info("Year date format: " + release);
      lastfmAlbum.setYear(lastfmHelper.getYear(release));
      log.info("Year metadata format: " + lastfmAlbum.getYear());
    } else {
      lastfmAlbum.setYear(StringUtils.EMPTY);
    }
  }

  public ActionResult isSomethingNew(LastfmAlbum lastfmAlbum, Metadata metadata) {
    if (lastfmAlbum.getImageIcon() == null
        && StringUtils.isEmpty(lastfmAlbum.getYear())
        && StringUtils.isEmpty(lastfmAlbum.getGenre())) {
      return ActionResult.Ready;
    }
    if (lastfmAlbum.getImageIcon() != null) {
      CoverArt coverArt = new CoverArt(lastfmAlbum.getImageIcon(), CoverArtType.LAST_FM);
      metadata.setNewCoverArt(coverArt);
    }
    if (StringUtils.isEmpty(metadata.getYear())) {
      metadata.setYear(lastfmAlbum.getYear());
    }
    if (StringUtils.isEmpty(metadata.getGenre())) {
      metadata.setGenre(lastfmAlbum.getGenre());
    }
    return ActionResult.New;
  }
}
