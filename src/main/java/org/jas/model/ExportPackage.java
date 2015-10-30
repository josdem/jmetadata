package org.jas.model;

import java.io.File;
import java.util.List;


public class ExportPackage {
	private List<Metadata> metadataList;
	private final File root;

	public ExportPackage(File root, List<Metadata> metadataList) {
		this.root = root;
		this.metadataList = metadataList;
	}
	
	public File getRoot() {
		return root;
	}

	public List<Metadata> getMetadataList() {
		return metadataList;
	}

	public void setMetadataList(List<Metadata> metadatas) {
		this.metadataList = metadatas;
	}
	
}
