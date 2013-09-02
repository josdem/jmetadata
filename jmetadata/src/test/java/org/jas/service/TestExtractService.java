package org.jas.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.jas.model.Metadata;
import org.jas.service.ExtractService;
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
	@Mock
	private Metadata metadata;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldExtractMetadataFromFileWhenDash() throws Exception {
		String filename = "jenifer lopez - 9A - 112.mp3";
		when(file.getName()).thenReturn(filename);
		when(metadata.getFile()).thenReturn(file);
		
		extractService.extractFromFileName(metadata);
		
		verify(metadata).setArtist("jenifer lopez ");
		verify(metadata).setTitle(" 9A ");
	}
	
	@Test
	public void shouldExtractMetadataFromFileWhenNoDash() throws Exception {
		String expectedName = "jenifer lopez";
		String filename = "jenifer lopez.mp3";
		when(file.getName()).thenReturn(filename);
		when(metadata.getFile()).thenReturn(file);
		
		extractService.extractFromFileName(metadata);
		
		verify(metadata).setArtist(expectedName);
		verify(metadata).setTitle(expectedName);
	}

}
