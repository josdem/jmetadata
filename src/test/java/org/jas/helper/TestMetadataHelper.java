package org.jas.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Set;

import org.junit.Test;

public class TestMetadataHelper {

	private MetadataHelper metadataHelper = new MetadataHelper();

	@Test
	public void shouldCreateAHashset() throws Exception {
		Set<File> hashset = metadataHelper.createHashSet();
		assertNotNull(hashset);
	}
	
}
