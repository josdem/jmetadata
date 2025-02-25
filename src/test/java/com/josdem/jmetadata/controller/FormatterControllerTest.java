/*
   Copyright 2025 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.FormatterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FormatterControllerTest {

  private FormatterController formatterController;

  @Mock private FormatterService formatterService;
  @Mock private Metadata metadata;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    formatterController = new FormatterController(formatterService);
  }

  @Test
  public void shouldFormatWhenBadFormat() throws Exception {
    when(formatterService.wasFormatted(metadata)).thenReturn(true);
    when(formatterService.wasCamelized(metadata)).thenReturn(false);
    ActionResult result = formatterController.format(metadata);
    assertEquals(ActionResult.NEW, result);
  }

  @Test
  public void shouldFormatWhenNotCamelized() throws Exception {
    when(formatterService.wasFormatted(metadata)).thenReturn(false);
    when(formatterService.wasCamelized(metadata)).thenReturn(true);
    ActionResult result = formatterController.format(metadata);
    assertEquals(ActionResult.NEW, result);
  }

  @Test
  public void shouldReturnComplete() throws Exception {
    when(formatterService.wasFormatted(metadata)).thenReturn(false);
    when(formatterService.wasCamelized(metadata)).thenReturn(false);
    ActionResult result = formatterController.format(metadata);
    assertEquals(ActionResult.READY, result);
  }
}
