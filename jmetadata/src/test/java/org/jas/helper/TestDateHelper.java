package org.jas.helper;

import static org.junit.Assert.*;

import org.jas.helper.DateHelper;
import org.junit.Test;

public class TestDateHelper {

	private DateHelper dateHelper = new DateHelper();
	
	@Test
	public void shouldGetTimestamp() throws Exception {
		long timestamp = dateHelper.getTimestamp();
		assertTrue(timestamp > 0);
	}

}
