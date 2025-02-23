package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PictureTest {

  private File validImageFile;
  private File invalidImageFile;
  private BufferedImage bufferedImage;

  @BeforeEach
  void setUp() throws IOException {
    // Create a temporary valid image file
    validImageFile = File.createTempFile("testImage", ".jpg");
    bufferedImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
    ImageIO.write(bufferedImage, "jpg", validImageFile);

    // Create a temporary invalid image file
    invalidImageFile = File.createTempFile("invalidImage", ".txt");
  }

  @Test
  @DisplayName("initializing a valid image")
  void testPictureInitialization() throws IOException {
    Picture picture = new Picture(validImageFile);

    assertEquals(validImageFile.getName(), picture.getName());
    assertEquals(bufferedImage.getHeight(), picture.getImage().getHeight(null));
    assertEquals(bufferedImage.getWidth(), picture.getImage().getWidth(null));
  }

  @Test
  @DisplayName("initializing an invalid image")
  void testPictureInitializationWithInvalidImage() {
    assertThrows(
        IllegalArgumentException.class, () -> new Picture(invalidImageFile), "not an image");
  }

  @Test
  @DisplayName("checking if the image is proportioned")
  void testIsProportionedImage() throws IOException {
    Picture picture = new Picture(validImageFile);

    assertTrue(picture.isProportionedImage());
  }

  @Test
  @DisplayName("checking if the image is not proportioned")
  void testIsNotProportionedImage() throws IOException {
    // Create a temporary image file with different proportions
    File disproportionedImageFile = File.createTempFile("disproportionedImage", ".jpg");
    BufferedImage disproportionedBufferedImage =
        new BufferedImage(300, 100, BufferedImage.TYPE_INT_RGB);
    ImageIO.write(disproportionedBufferedImage, "jpg", disproportionedImageFile);

    Picture picture = new Picture(disproportionedImageFile);

    assertFalse(picture.isProportionedImage());
  }

  @Test
  @DisplayName("checking if the image size is valid")
  void testIsValidImageSize() throws IOException {
    Picture picture = new Picture(validImageFile);

    assertTrue(picture.isValidImageSize());
  }

  @Test
  @DisplayName("checking if the image size is not valid")
  void testIsNotValidImageSize() throws IOException {
    // Create a temporary image file with small size
    File smallImageFile = File.createTempFile("smallImage", ".jpg");
    BufferedImage smallBufferedImage = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
    ImageIO.write(smallBufferedImage, "jpg", smallImageFile);

    Picture picture = new Picture(smallImageFile);

    assertFalse(picture.isValidImageSize());
  }
}
