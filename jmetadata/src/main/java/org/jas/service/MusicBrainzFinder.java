package org.jas.service;

import org.jas.model.MusicBrainzTrack;

import com.slychief.javamusicbrainz.ServerUnavailableException;

public interface MusicBrainzFinder {
	
	public MusicBrainzTrack getAlbum(String artist, String trackname) throws ServerUnavailableException;
	public MusicBrainzTrack getByAlbum(String trackname, String album) throws ServerUnavailableException;

}
