package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Slf4j
public class AlbumUtilsTest {

  @Test
  @DisplayName("formatting year from date")
  public void testFormatYear(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());

    // Arrange
    String date = "2025-02-03";

    // Act
    String year = AlbumUtils.formatYear(date);

    // Assert
    assertEquals("2025", year);
  }

  @Test
  @DisplayName("formatting year with invalid date")
  public void testFormatYearWithInvalidDate(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());

    // Arrange
    String date = "20";

    // Act & Assert
    assertThrows(StringIndexOutOfBoundsException.class, () -> {
      AlbumUtils.formatYear(date);
    });
  }

  @Test
  @DisplayName("testing private constructor")
  public void testPrivateConstructor(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());

    // Act & Assert
    assertThrows(IllegalStateException.class, () -> {
      java.lang.reflect.Constructor<AlbumUtils> constructor = AlbumUtils.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      try {
        constructor.newInstance();
      } catch (java.lang.reflect.InvocationTargetException e) {
        throw (IllegalStateException) e.getCause();
      }
    });
  }
}
