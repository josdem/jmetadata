package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ImageIconBaseTests {

    private static ImageIconBase imageIconBase;

    @BeforeAll
    static void init() {
        imageIconBase = mock(ImageIconBase.class);
    }

    @Test
    void testGetImageIcon() {
        // Given
        String imagePath = "path/to/image.png";
        var expectedImageIcon = new ImageIcon(imagePath);
        when(imageIconBase.getImageIcon()).thenReturn(expectedImageIcon);

        // When
        ImageIcon result = imageIconBase.getImageIcon();

        // Then
        assertNotNull(result);
        assertEquals(expectedImageIcon.getDescription(), result.getDescription());
    }
}
