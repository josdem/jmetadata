package org.jas.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.jas.action.ActionResult;
import org.jas.controller.DefaultController;
import org.jas.model.Metadata;
import org.jas.service.DefaultService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDefaultController {

	@InjectMocks
	private DefaultController defaultController = new DefaultController();
	
	@Mock
	private DefaultService defaultService;
	@Mock
	private List<Metadata> metadatas;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCompleteTracksTotal() throws Exception {
		when(defaultService.isCompletable(metadatas)).thenReturn(true);
		
		ActionResult result = defaultController.complete(metadatas);
		
		verify(defaultService).complete(metadatas);
		assertEquals(ActionResult.New, result);
	}
	
	@Test
	public void shouldNotCompleteTracksTotal() throws Exception {
		when(defaultService.isCompletable(metadatas)).thenReturn(false);
		
		ActionResult result = defaultController.complete(metadatas);
		
		assertEquals(ActionResult.Complete, result);
	}
}
