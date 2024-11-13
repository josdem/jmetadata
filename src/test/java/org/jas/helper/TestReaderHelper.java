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

package org.jas.helper;

import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestReaderHelper {

    @InjectMocks
    private ReaderHelper readerHelper = new ReaderHelper();

    @Mock
    private Tag tag;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetGenre() throws Exception {
        String genre = "Minimal Techno";
        when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
        assertEquals(genre, readerHelper.getGenre(tag, genre));
    }

    @Test
    public void shouldGetGenreByCodeWithParentheses() throws Exception {
        String genreAsCode = "(18)";
        String genre = "Techno";
        when(tag.getFirst(FieldKey.GENRE)).thenReturn(genreAsCode);
        assertEquals(genre, readerHelper.getGenre(tag, genreAsCode));
    }

    @Test
    public void shouldKnowWhenMp3IsNotANumberInsideParenthesis() throws Exception {
        String genre = "(None)";
        when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
        assertEquals(genre, readerHelper.getGenre(tag, genre));
    }
}
