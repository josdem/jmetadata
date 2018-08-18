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

package org.jas.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.jas.ApplicationState;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

	public File createTempFile(String prefix) throws IOException {
		return (prefix == StringUtils.EMPTY) ? File.createTempFile(ApplicationState.PREFIX, ApplicationState.IMAGE_EXT) : File.createTempFile(prefix, ApplicationState.IMAGE_EXT);
	}

	public void write(Image bufferedImage, File file) throws IOException {
		ImageIO.write((BufferedImage) bufferedImage, ApplicationState.IMAGE_EXT, file);
	}

	public Image readImage(String imageURL) throws MalformedURLException, IOException {
		return ImageIO.read(new URL(imageURL));
	}

}
