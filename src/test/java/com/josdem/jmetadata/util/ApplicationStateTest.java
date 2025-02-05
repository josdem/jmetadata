package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.josdem.jmetadata.model.MusicBrainzResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class ApplicationStateTest {

  @Test
  @DisplayName("adding and retrieving from cache")
  public void testAddAndRetrieveFromCache(TestInfo testInfo) {
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
  public void testRetrieveNonExistentKeyFromCache(TestInfo testInfo) {
    // Arrange
    String key = "nonExistentKey";

    // Act
    MusicBrainzResponse cachedResponse = ApplicationState.cache.get(key);

    // Assert
    assertNull(cachedResponse);
  }

  @Test
  @DisplayName("removing from cache")
  public void testRemoveFromCache(TestInfo testInfo) {
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
