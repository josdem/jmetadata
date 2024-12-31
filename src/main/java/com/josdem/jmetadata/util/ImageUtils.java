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

package com.josdem.jmetadata.util;

import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.service.ImageService;
import com.josdem.jmetadata.service.impl.ImageServiceImpl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class ImageUtils {
    private ImageService imageService = new ImageServiceImpl();

    private FileUtils fileUtils = new FileUtils();
    private static final int THREE_HUNDRED = 300;


    public Image resize(Image image, int width, int height) {
        BufferedImage bufferedImage = (BufferedImage) image;
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private void write(Image bufferedImage, File file) throws IOException {
        imageService.write(bufferedImage, file);
    }

    public void saveCoverArtToFile(Image image, File root, String prefix) throws IOException {
        if (image == null) {
            return;
        }
        File file = fileUtils.createFile(root, prefix, ApplicationConstants.IMAGE_EXT);
        saveImage(image, file);
    }

    public File saveCoverArtToFile(Image image) throws IOException {
        if (image == null) {
            return null;
        }
        File file = imageService.createTempFile();
        saveImage(image, file);
        return file;
    }

    private void saveImage(Image image, File file) throws IOException {
        log.info("Saving image: {}", file.getAbsolutePath());
        if (!is300Image(image)) {
            image = resize(image, ApplicationConstants.THREE_HUNDRED, ApplicationConstants.THREE_HUNDRED);
        }
        write(image, file);
    }

    public boolean is300Image(Image image) {
        int imageHeight = image.getHeight(new ImageObserver() {

            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
        log.info("Image height: " + imageHeight);
        return imageHeight == THREE_HUNDRED ? true : false;
    }

}
