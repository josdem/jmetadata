package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.josdem.jmetadata.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Slf4j
class AlbumUtilsTest {

  @Test
  @DisplayName("formatting year from date")
  void testFormatYear(TestInfo testInfo) {
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
  void testFormatYearWithInvalidDate(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());

    // Arrange
    String date = "20";

    // Act & Assert
    assertThrows(BusinessException.class, () -> AlbumUtils.formatYear(date));
  }

  @Test
  @DisplayName("formatting year with non-date string")
  void testFormatYearWithNonDateString(TestInfo testInfo) {
    log.info(testInfo.getDisplayName());

    // Arrange
    String date = "thisIsNotValidDate";

    // Act & Assert
    assertThrows(BusinessException.class, () -> AlbumUtils.formatYear(date));
  }
}
