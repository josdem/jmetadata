package org.jas.model;

import java.awt.Image;

public class CoverArt {
	private final Image image;
	private final CoverArtType type;

	public CoverArt(Image image, CoverArtType type){
		this.image = image;
		this.type = type;
	}
	
	public Image getImageIcon() {
		return image;
	}
	
	public CoverArtType getType() {
		return type;
	}

}
