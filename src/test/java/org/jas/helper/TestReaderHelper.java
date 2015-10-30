package org.jas.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.jas.helper.ReaderHelper;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestReaderHelper {

	@InjectMocks
	private ReaderHelper readerHelper = new ReaderHelper();
	
	@Mock
	private Tag tag;
	
	@Before
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
