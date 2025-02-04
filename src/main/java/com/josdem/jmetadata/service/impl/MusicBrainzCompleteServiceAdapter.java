package com.josdem.jmetadata.service.impl;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.Album;
import com.josdem.jmetadata.model.AlbumInfo;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.CompleteService;
import com.josdem.jmetadata.service.MusicBrainzService;
import com.josdem.jmetadata.util.AlbumUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MusicBrainzCompleteServiceAdapter implements CompleteService {

  private final MusicBrainzService musicBrainzService;

  @Override
  public boolean canComplete(Metadata metadata) {
    return metadata.getAlbum() != null && !metadata.getAlbum().trim().isEmpty();
  }

  @Override
  public AlbumInfo getInfo(Metadata metadata) {
    Album album = musicBrainzService.getAlbumByName(metadata.getAlbum());
    if (album == null) {
      return null;
    }
    AlbumInfo albumInfo = new AlbumInfo();
    albumInfo.setYear(AlbumUtils.formatYear(album.getDate()));
    // Optionally, set coverArt and genre if available.
    return albumInfo;
  }

  @Override
  public ActionResult isSomethingNew(AlbumInfo albumInfo, Metadata metadata) {
    boolean updated = false;
    if (albumInfo != null) {
      if (metadata.getYear() == null || metadata.getYear().isEmpty()) {
        metadata.setYear(albumInfo.getYear());
        updated = true;
      }
      // Additional logic for coverArt and genre can be added here.
    }
    return updated ? ActionResult.New : ActionResult.Ready;
  }
}
