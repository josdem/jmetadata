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

import java.awt.Image;

import org.jas.model.LastfmAlbum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestLastfmAlbum {
	@Mock
	private Image imageIcon;
	private String year = "2011";
	private String genre = "Minimal Techno";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateALastfmAlbum() throws Exception {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		lastfmAlbum.setYear(year);
		lastfmAlbum.setGenre(genre );
		lastfmAlbum.setImageIcon(imageIcon);

		assertEquals(year, lastfmAlbum.getYear());
		assertEquals(genre, lastfmAlbum.getGenre());
		assertEquals(imageIcon, lastfmAlbum.getImageIcon());
	}
}
