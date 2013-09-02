package org.jas.helper;

import org.jas.model.GenreTypes;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Service;

@Service
public class ReaderHelper {

	public String getGenre(Tag tag, String genre) {
		try{
			if (genre != null && genre.startsWith("(")) {
				int index = Integer.valueOf(genre.substring(genre.indexOf('(') + 1, genre.indexOf(')')));
				return GenreTypes.getGenreByCode(index);
			} else {
				return tag.getFirst(FieldKey.GENRE);
			}
		} catch (NumberFormatException nue){
			return tag.getFirst(FieldKey.GENRE);
		}
	}

}
