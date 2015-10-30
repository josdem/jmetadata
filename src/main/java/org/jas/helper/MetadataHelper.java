package org.jas.helper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.jas.model.MetadataAlbumValues;
import org.springframework.stereotype.Service;

@Service
public class MetadataHelper {

	public Set<File> createHashSet() {
		return new HashSet<File>();
	}

	public MetadataAlbumValues createMetadataAlbumVaues() {
		return new MetadataAlbumValues();
	}

}
