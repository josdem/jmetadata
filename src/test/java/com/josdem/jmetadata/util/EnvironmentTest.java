package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnvironmentTest {

  private String originalOsName;

  @BeforeEach
  void setUp() {
    originalOsName = System.getProperty("os.name");
  }

  @AfterEach
  void tearDown() {
    System.setProperty("os.name", originalOsName);
  }

  @Test
  void testIsLinuxWhenLinux() {
    System.setProperty("os.name", "Linux");
    assertTrue(Environment.isLinux(), "Expected isLinux to return true for Linux OS");
  }

  @Test
  void testIsLinuxWhenNotLinux() {
    System.setProperty("os.name", "Windows 10");
    assertFalse(Environment.isLinux(), "Expected isLinux to return false for non-Linux OS");
  }

  @Test
  void testIsLinuxWhenMac() {
    System.setProperty("os.name", "Mac OS X");
    assertFalse(Environment.isLinux(), "Expected isLinux to return false for Mac OS");
  }

  @Test
  void testIsLinuxWhenLinuxCaseInsensitive() {
    System.setProperty("os.name", "LiNuX");
    assertTrue(Environment.isLinux(), "Expected isLinux to return true for mixed-case Linux OS");
  }
}
