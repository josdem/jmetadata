package org.jas.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.OutputStream;

import org.jas.helper.OutStreamWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TestOutStreamWriter {

	@InjectMocks
	private OutStreamWriter outStreamWriter = new OutStreamWriter();
	
	private File pepeGarden = new File("src/test/resources/checkstyle/checkstyle.xml");
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetWriter() throws Exception {
		OutputStream writer = outStreamWriter.getWriter(pepeGarden);
		assertNotNull(writer);
	}

}
