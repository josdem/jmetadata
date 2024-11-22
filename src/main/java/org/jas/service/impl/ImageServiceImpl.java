/*
   Copyright 2014 Jose Morales contact@josdem.io

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

package org.jas.service.impl;

import org.jas.ApplicationState;
import org.jas.service.ImageService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

@Service
public class ImageServiceImpl implements ImageService {

    public File createTempFile() throws IOException {
        return File.createTempFile(ApplicationState.PREFIX, ApplicationState.IMAGE_EXT);
    }

    public void write(Image bufferedImage, File file) throws IOException {
        ImageIO.write((BufferedImage) bufferedImage, ApplicationState.IMAGE_EXT, file);
    }

    public Image readImage(String imageURL) throws IOException {
        return ImageIO.read(URI.create(imageURL).toURL());
    }

}
