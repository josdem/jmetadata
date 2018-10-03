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

package org.jas.service;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import org.jas.model.CoverArt;
import org.jas.model.CoverArtType;
import org.jas.model.LastfmAlbum;
import org.jas.model.Metadata;
import org.jas.action.ActionResult;
import org.jas.helper.LastFMAlbumHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who evaluates metadata and decides if LastFM can help to complete information
 */

@Service
public class LastFMCompleteService {

	@Autowired
	private LastFMAlbumHelper lastfmHelper;
	@Autowired
	private ImageService imageService;

  private HashMap<String, Album> cachedAlbums = new HashMap<String, Album>();

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public boolean canLastFMHelpToComplete(Metadata metadata) {
		String artist = metadata.getArtist();
		String album = metadata.getAlbum();

		if (isMetadataIncomplete(metadata) && hasAlbumAndArtist(artist, album)) {
			Album info = cachedAlbums.get(metadata.getAlbum());
			if(info==null){
				info = lastfmHelper.getAlbum(artist, album);
				if(info!=null){
					String imageUrl = info.getImageURL(ImageSize.EXTRALARGE);
					if(!StringUtils.isEmpty(imageUrl)){
						cachedAlbums.put(metadata.getAlbum(), info);
					}
				}
			}
			return info == null ? false : true;
		}
		return false;
	}

	private boolean hasAlbumAndArtist(String artist, String album) {
		return (!StringUtils.isEmpty(album) && !StringUtils.isEmpty(artist));
	}

	private boolean isMetadataIncomplete(Metadata metadata) {
		return (metadata.getCoverArt() == null || StringUtils.isEmpty(metadata.getYear()) || StringUtils.isEmpty(metadata.getGenre()));
	}

	public LastfmAlbum getLastFM(Metadata metadata) throws MalformedURLException, IOException {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		setCoverArt(metadata, lastfmAlbum);
		setYear(metadata, lastfmAlbum);
		setGenre(metadata, lastfmAlbum);
		return lastfmAlbum;
	}

	private void setCoverArt(Metadata metadata, LastfmAlbum lastfmAlbum) throws MalformedURLException, IOException {
		if(metadata.getCoverArt() != null){
			return;
		}
		String imageURL = StringUtils.EMPTY;
		Album album = cachedAlbums.get(metadata.getAlbum());
		if(album!=null){
			imageURL = album.getImageURL(ImageSize.EXTRALARGE);
			log.info("imageURL: " + imageURL + " from album: " + album.getName());
		}
		if (!StringUtils.isEmpty(imageURL)) {
			Image image = imageService.readImage(imageURL);
			lastfmAlbum.setImageIcon(image);
		}
	}

	private void setGenre(Metadata metadata, LastfmAlbum lastfmAlbum) {
		if(!StringUtils.isEmpty(metadata.getGenre()) || StringUtils.isEmpty(metadata.getAlbum())){
			return;
		}
		Album album = cachedAlbums.get(metadata.getAlbum());
		String genre = StringUtils.EMPTY;
		if(album != null){
			genre = lastfmHelper.getGenre(album);
		}
		if(!StringUtils.isEmpty(genre)){
			log.info("Genre from lastFM: " + genre);
			lastfmAlbum.setGenre(genre);
		} else {
			lastfmAlbum.setGenre(StringUtils.EMPTY);
		}
	}

	private void setYear(Metadata metadata, LastfmAlbum lastfmAlbum) {
		if(!StringUtils.isEmpty(metadata.getYear())){
			return;
		}
		Date release = null;
		Album album = cachedAlbums.get(metadata.getAlbum());
		if(album != null) {
			release = album.getReleaseDate();
		}
		if(release != null){
			log.info("Year date format: " + release);
			lastfmAlbum.setYear(lastfmHelper.getYear(release));
			log.info("Year metadata format: " + lastfmAlbum.getYear());
		} else {
			lastfmAlbum.setYear(StringUtils.EMPTY);
		}
	}

	public ActionResult isSomethingNew(LastfmAlbum lastfmAlbum, Metadata metadata) {
		if(lastfmAlbum.getImageIcon() == null && StringUtils.isEmpty(lastfmAlbum.getYear()) && StringUtils.isEmpty(lastfmAlbum.getGenre())){
			return ActionResult.Complete;
		}
		CoverArt coverArt = new CoverArt(lastfmAlbum.getImageIcon(), CoverArtType.LAST_FM);
		metadata.setNewCoverArt(coverArt);
		if(StringUtils.isEmpty(metadata.getYear())){
			metadata.setYear(lastfmAlbum.getYear());
		}
		if(StringUtils.isEmpty(metadata.getGenre())){
			metadata.setGenre(lastfmAlbum.getGenre());
		}
		return ActionResult.New;
	}

}
