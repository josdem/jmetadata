package com.josdem.jmetadata.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MethodWrapperTest {

  private MethodWrapper<String> methodWrapper;
  private MethodWrapper<String>.UsingMethodWrapper<String> usingMethodWrapper;

  @BeforeEach
  void setUp() throws Exception {
    Method method = TestClass.class.getDeclaredMethod("testMethod", String.class);
    Constructor<MethodWrapper> constructor =
        MethodWrapper.class.getDeclaredConstructor(Method.class, Class.class);
    constructor.setAccessible(true);
    methodWrapper = constructor.newInstance(method, String.class);
    usingMethodWrapper = methodWrapper.using(new TestClass());
  }

  @Test
  @DisplayName("initializing MethodWrapper with valid method")
  void testMethodWrapperInitialization() throws Exception {
    Method method = TestClass.class.getDeclaredMethod("testMethod", String.class);
    Constructor<MethodWrapper> constructor =
        MethodWrapper.class.getDeclaredConstructor(Method.class, Class.class);
    constructor.setAccessible(true);
    MethodWrapper<String> localMethodWrapper = constructor.newInstance(method, String.class);

    Method checkMethod = MethodWrapper.class.getDeclaredMethod("check");
    checkMethod.setAccessible(true);
    MethodWrapper<String> checkedMethodWrapper =
        (MethodWrapper<String>) checkMethod.invoke(localMethodWrapper);

    Field methodField = MethodWrapper.class.getDeclaredField("method");
    methodField.setAccessible(true);
    assertEquals(method, methodField.get(checkedMethodWrapper));
  }

  @Test
  @DisplayName("initializing MethodWrapper with invalid method")
  void testMethodWrapperInitializationWithInvalidMethod() {
    assertThrows(
        RuntimeException.class,
        () -> {
          MethodWrapper.forClass("com.josdem.jmetadata.util.NonExistentClass")
              .method("nonExistentMethod")
              .withParameters(String.class)
              .andReturnType(String.class);
        });
  }

  @Test
  @DisplayName("invoking method using MethodWrapper")
  void testInvokeMethod() {
    String result = usingMethodWrapper.invoke("test");
    assertEquals("Hello, test", result);
  }

  @Test
  @DisplayName("invoking method with exception")
  void testInvokeMethodWithException() throws Exception {
    Constructor<MethodWrapper> constructor =
        MethodWrapper.class.getDeclaredConstructor(Method.class, Class.class);
    constructor.setAccessible(true);
    MethodWrapper<String> localMethodWrapper = constructor.newInstance(null, null);
    MethodWrapper<String>.UsingMethodWrapper<String> localUsingMethodWrapper =
        localMethodWrapper.using(null);

    assertNull(localUsingMethodWrapper.invoke("test"));
  }

  @Test
  @DisplayName("getting class by name")
  void testGetClassByName() {
    Class<?> clazz =
        MethodWrapper.getClass("com.josdem.jmetadata.util.MethodWrapperTest$TestClass");
    assertEquals(TestClass.class, clazz);
  }

  @Test
  @DisplayName("getting class by invalid name")
  void testGetClassByInvalidName() {
    assertNull(MethodWrapper.getClass("com.josdem.jmetadata.util.NonExistentClass"));
  }

  // Helper class for testing
  public static class TestClass {
    public String testMethod(String name) {
      return "Hello, " + name;
    }
  }
}
