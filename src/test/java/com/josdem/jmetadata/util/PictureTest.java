package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PictureTest {

    private File imageFile;
    private BufferedImage bufferedImage;

    @BeforeEach
    void setUp() throws IOException {
        imageFile = mock(File.class);
        bufferedImage = mock(BufferedImage.class);
    }

    @Test
    void testPictureInitialization() throws IOException {
        when(imageFile.getName()).thenReturn("testImage.jpg");
        when(imageFile.exists()).thenReturn(true);
        when(ImageIO.read(imageFile)).thenReturn(bufferedImage);
        when(bufferedImage.getHeight()).thenReturn(100);
        when(bufferedImage.getWidth()).thenReturn(200);

        var picture = new Picture(imageFile);

        assertEquals("testImage.jpg", picture.getName());
        assertEquals(bufferedImage, picture.getImage());
        assertEquals(100, picture.getImage().getHeight(null));
        assertEquals(200, picture.getImage().getWidth(null));
    }

    @Test
    void testPictureInitializationWithInvalidImage() throws IOException {
        when(imageFile.getName()).thenReturn("invalidImage.txt");
        when(imageFile.exists()).thenReturn(true);
        when(ImageIO.read(imageFile)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> new Picture(imageFile), "not an image");
    }

    @Test
    void testIsProportionedImage() throws IOException {
        when(imageFile.getName()).thenReturn("testImage.jpg");
        when(imageFile.exists()).thenReturn(true);
        when(ImageIO.read(imageFile)).thenReturn(bufferedImage);
        when(bufferedImage.getHeight()).thenReturn(100);
        when(bufferedImage.getWidth()).thenReturn(200);

        var picture = new Picture(imageFile);

        assertTrue(picture.isProportionedImage());
    }

    @Test
    void testIsNotProportionedImage() throws IOException {
        when(imageFile.getName()).thenReturn("testImage.jpg");
        when(imageFile.exists()).thenReturn(true);
        when(ImageIO.read(imageFile)).thenReturn(bufferedImage);
        when(bufferedImage.getHeight()).thenReturn(100);
        when(bufferedImage.getWidth()).thenReturn(300);

        var picture = new Picture(imageFile);

        assertFalse(picture.isProportionedImage());
    }

    @Test
    void testIsValidImageSize() throws IOException {
        when(imageFile.getName()).thenReturn("testImage.jpg");
        when(imageFile.exists()).thenReturn(true);
        when(ImageIO.read(imageFile)).thenReturn(bufferedImage);
        when(bufferedImage.getHeight()).thenReturn(100);
        when(bufferedImage.getWidth()).thenReturn(200);

        var picture = new Picture(imageFile);

        assertTrue(picture.isValidImageSize());
    }

    @Test
    void testIsNotValidImageSize() throws IOException {
        when(imageFile.getName()).thenReturn("testImage.jpg");
        when(imageFile.exists()).thenReturn(true);
        when(ImageIO.read(imageFile)).thenReturn(bufferedImage);
        when(bufferedImage.getHeight()).thenReturn(30);
        when(bufferedImage.getWidth()).thenReturn(30);

        var picture = new Picture(imageFile);

        assertFalse(picture.isValidImageSize());
    }
}
