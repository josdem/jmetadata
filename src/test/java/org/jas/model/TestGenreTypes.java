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

package org.jas.model;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.jas.model.GenreTypes;
import org.junit.Test;


public class TestGenreTypes {

	@Test
	public void shouldGetMinimalAsGenre() throws Exception {
		int minimalCode = 152;
		assertEquals("Minimal", GenreTypes.getGenreByCode(minimalCode));
	}

	@Test
	public void shouldGetHouseAsGenre() throws Exception {
		int houseCode = 35;
		assertEquals("House", GenreTypes.getGenreByCode(houseCode));
	}

	@Test
	public void shouldReturnEmptyStringIfNoGenre() throws Exception {
		int unknownCode = 500;
		assertEquals(StringUtils.EMPTY, GenreTypes.getGenreByCode(unknownCode));
	}

	@Test
	public void shouldGetGenreByCode() throws Exception {
		int genreAsCode = 18;
		String genre = "Techno";

		String result = GenreTypes.getGenreByCode(genreAsCode);
		assertEquals(genre, result);
	}

	@Test
	public void shouldGetCodeByGenre() throws Exception {
		int genreAsCode = 18;
		String genre = "Techno";

		GenreTypes genreType = GenreTypes.getGenreByName(genre);
		assertEquals(genreAsCode, genreType.getCode());
	}

	@Test
	public void shouldGetUnknownCodeByGenre() throws Exception {
		int genreAsCode = 148;
		String genre = "Minimal Techno";

		GenreTypes genreType = GenreTypes.getGenreByName(genre);
		assertEquals(genreAsCode, genreType.getCode());
	}
}
