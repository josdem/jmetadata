package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.josdem.jmetadata.model.MusicBrainzResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationStateTest {

  @Test
  @DisplayName("adding and retrieving from cache")
  void testAddAndRetrieveFromCache() {
    // Arrange
    String key = "testKey";
    MusicBrainzResponse response = new MusicBrainzResponse();
    ApplicationState.cache.put(key, response);

    // Act
    MusicBrainzResponse cachedResponse = ApplicationState.cache.get(key);

    // Assert
    assertEquals(response, cachedResponse);
  }

  @Test
  @DisplayName("retrieving non-existent key from cache")
  void testRetrieveNonExistentKeyFromCache() {
    // Arrange
    String key = "nonExistentKey";

    // Act
    MusicBrainzResponse cachedResponse = ApplicationState.cache.get(key);

    // Assert
    assertNull(cachedResponse);
  }

  @Test
  @DisplayName("removing from cache")
  void testRemoveFromCache() {
    // Arrange
    String key = "testKey";
    MusicBrainzResponse response = new MusicBrainzResponse();
    ApplicationState.cache.put(key, response);

    // Act
    ApplicationState.cache.remove(key);
    MusicBrainzResponse cachedResponse = ApplicationState.cache.get(key);

    // Assert
    assertNull(cachedResponse);
  }
}
