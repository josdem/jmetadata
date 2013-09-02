package org.jas.model;

import static org.junit.Assert.assertEquals;

import java.awt.Image;

import org.jas.model.CoverArt;
import org.jas.model.CoverArtType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestCoverArt {

	@Mock
	private Image imageIcon;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateAnCoverArt() throws Exception {
		CoverArt coverArt = new CoverArt(imageIcon, CoverArtType.DRAG_AND_DROP);
		assertEquals(imageIcon, coverArt.getImageIcon());
		assertEquals(CoverArtType.DRAG_AND_DROP, coverArt.getType());
	}
	
}
