package org.jas.service;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.jas.helper.FormatterHelper;
import org.jas.model.Metadata;
import org.springframework.stereotype.Service;

@Service
public class FormatterService {

	private static final int SIXTY = 60;
	private FormatterHelper helper = new FormatterHelper();

	public boolean isABadFormat(Metadata metadata) {
		if (metadata.getTitle().contains("&amp;") || metadata.getArtist().contains("&amp;") || metadata.getAlbum().contains("&amp;") || metadata.getTitle().contains("`")
				|| metadata.getArtist().contains("`") || metadata.getAlbum().contains("`") || metadata.getTitle().contains("&eacute;") || metadata.getArtist().contains("&eacute;")
				|| metadata.getAlbum().contains("&eacute;") || metadata.getTitle().contains("&aacute;") || metadata.getArtist().contains("&aacute;") || metadata.getAlbum().contains("&aacute;")
				|| metadata.getTitle().contains("&iacute;") || metadata.getArtist().contains("&iacute;") || metadata.getAlbum().contains("&iacute;") || metadata.getTitle().contains("&oacute;")
				|| metadata.getArtist().contains("&oacute;") || metadata.getAlbum().contains("&oacute;") || metadata.getTitle().contains("&uacute;") || metadata.getArtist().contains("&uacute;")
				|| metadata.getAlbum().contains("&uacute;") || metadata.getTitle().contains("&acute;") || metadata.getArtist().contains("&acute;") || metadata.getAlbum().contains("&acute;")
				|| metadata.getTitle().contains("&egrave;") || metadata.getArtist().contains("&egrave;") || metadata.getAlbum().contains("&egrave;") || metadata.getTitle().contains("&Eacute;")
				|| metadata.getArtist().contains("&Eacute;") || metadata.getAlbum().contains("&Eacute;") || metadata.getTitle().contains("&ecirc;") || metadata.getArtist().contains("&ecirc;")
				|| metadata.getAlbum().contains("&ecirc;") || metadata.getTitle().contains("&uuml;") || metadata.getArtist().contains("&uuml;") || metadata.getAlbum().contains("&uuml;")
				|| metadata.getTitle().contains("&ouml;") || metadata.getArtist().contains("&ouml;") || metadata.getAlbum().contains("&ouml;") || metadata.getTitle().contains("&szlig;")
				|| metadata.getArtist().contains("&szlig;") || metadata.getAlbum().contains("&szlig;") || metadata.getTitle().contains("&agrave;") || metadata.getArtist().contains("&agrave;")
				|| metadata.getAlbum().contains("&agrave;") || metadata.getTitle().contains("&deg;") || metadata.getArtist().contains("&deg;") || metadata.getAlbum().contains("&deg;")
				|| metadata.getTitle().contains("&ntilde;") || metadata.getArtist().contains("&ntilde;") || metadata.getAlbum().contains("&ntilde;") || metadata.getTitle().contains("&auml;") 
				|| metadata.getArtist().contains("&auml;") || metadata.getAlbum().contains("&auml;") || metadata.getTitle().contains("&ucirc;") 
				|| metadata.getArtist().contains("&ucirc;") || metadata.getAlbum().contains("&ucirc;")) {
			String title = metadata.getTitle().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú").replace("&acute;", "'").replace("&egrave;", "è").replace("&Eacute;", "É").replace("&ecirc;", "ê").replace("&uuml;", "ü").replace("&ouml;", "ö")
					.replace("&agrave;", "à").replace("&deg;", "°").replace("&ntilde;", "ñ").replace("&auml;", "ä").replace("&ucirc;", "û");
			String artist = metadata.getArtist().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú").replace("&acute;", "'").replace("&egrave;", "è").replace("&Eacute;", "É").replace("&ecirc;", "ê").replace("&uuml;", "ü").replace("&uuml;", "ü")
					.replace("&ouml;", "ö").replace("&agrave;", "à").replace("&deg;", "°").replace("&ntilde;", "ñ").replace("&auml;", "ä").replace("&ucirc;", "û");
			String album = metadata.getAlbum().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú").replace("&acute;", "'").replace("&Eacute;", "É").replace("&ecirc;", "ê").replace("&uuml;", "ü").replace("&ouml;", "ö")
					.replace("&agrave;", "à").replace("&deg;", "°").replace("&ntilde;", "ñ").replace("&auml;", "ä").replace("&ucirc;", "û");
			metadata.setTitle(title);
			metadata.setArtist(artist);
			metadata.setAlbum(album);
			return true;
		}
		return false;
	}

	public boolean isNotCamelized(Metadata metadata) {
		boolean camelized = false;
		String artist = metadata.getArtist();
		String artistFormatted = helper.getBasicFormat(metadata.getArtist());
		String title = metadata.getTitle();
		String titleFormatted = helper.getBasicFormat(metadata.getTitle());
		String album = metadata.getAlbum();
		String albumFormatted = helper.getBasicFormat(metadata.getAlbum());
		if (StringUtils.isAllLowerCase(titleFormatted) || StringUtils.isAllUpperCase(titleFormatted)) {
			metadata.setTitle(toCamelCase(title));
			camelized = true;
		}
		if (StringUtils.isAllLowerCase(artistFormatted) || StringUtils.isAllUpperCase(artistFormatted)) {
			metadata.setArtist(toCamelCase(artist));
			camelized = true;
		}
		if (StringUtils.isAllLowerCase(albumFormatted) || StringUtils.isAllUpperCase(albumFormatted)) {
			metadata.setAlbum(toCamelCase(album));
			camelized = true;
		}
		return camelized == true ? true : false;
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

	public boolean isAnalyzable(Metadata metadata) {
		return metadata.getTitle() != null && metadata.getArtist() != null && metadata.getAlbum() !=null;
	}

}
