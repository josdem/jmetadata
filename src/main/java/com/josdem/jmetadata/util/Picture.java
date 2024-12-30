/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.josdem.jmetadata.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Picture {
	private static final double PROPORTION = 0.45;
	private static final int MIN_SIZE = 47;
	private BufferedImage image;
	private String name;
	private int height;
	private int width;

	public Picture(File file) throws IOException {
		image = ImageIO.read(file);
		if (image == null) {
			throw new IllegalArgumentException("not an image");
		}
		name = file.getName();
		height = image.getHeight();
		width = image.getWidth();
	}

	public Image getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public boolean isProportionedImage() {
		double min = Math.min(width, height);
		int max = Math.max(width, height);
		return min / max >= PROPORTION;
	}

	public boolean isValidImageSize() {
		return width > MIN_SIZE && height > MIN_SIZE;
	}

}
