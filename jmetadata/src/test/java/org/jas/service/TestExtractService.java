package org.jas.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;

import org.jas.model.Metadata;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestExtractService {

	@InjectMocks
	private ExtractService extractService = new ExtractService();
	
	@Mock
	private File file;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldExtractMetadataFromFileWhenDash() throws Exception {
		String filename = "jenifer lopez - 9A - 112.mp3";
		when(file.getName()).thenReturn(filename);
		
		Metadata result = extractService.extractFromFileName(file);
		
		assertEquals("jenifer lopez ", result.getArtist());
		assertEquals(" 9A ", result.getTitle());
	}
	
	@Test
	public void shouldExtractMetadataFromFileWhenNoDash() throws Exception {
		String expectedName = "jenifer lopez";
		String filename = "jenifer lopez.mp3";
		when(file.getName()).thenReturn(filename);
		
		Metadata result = extractService.extractFromFileName(file);
		
		assertEquals(expectedName, result.getArtist());
		assertEquals(expectedName, result.getTitle());
	}

}
