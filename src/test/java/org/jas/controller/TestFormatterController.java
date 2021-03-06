/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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

package org.jas.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.jas.action.ActionResult;
import org.jas.controller.FormatterController;
import org.jas.model.Metadata;
import org.jas.service.FormatterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestFormatterController {

	@InjectMocks
	private FormatterController formatterController = new FormatterController();

	@Mock
	private FormatterService formatterService;
	@Mock
	private Metadata metadata;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldFormatWhenBadFormat() throws Exception {
		when(formatterService.isAnalyzable(metadata)).thenReturn(true);
		when(formatterService.wasFormatted(metadata)).thenReturn(true);
		when(formatterService.wasCamelized(metadata)).thenReturn(false);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.New, result);
	}

	@Test
	public void shouldFormatWhenNotCamelized() throws Exception {
		when(formatterService.isAnalyzable(metadata)).thenReturn(true);
		when(formatterService.wasFormatted(metadata)).thenReturn(false);
		when(formatterService.wasCamelized(metadata)).thenReturn(true);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.New, result);
	}

	@Test
	public void shouldReturnComplete() throws Exception {
		when(formatterService.isAnalyzable(metadata)).thenReturn(true);
		when(formatterService.wasFormatted(metadata)).thenReturn(false);
		when(formatterService.wasCamelized(metadata)).thenReturn(false);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.Complete, result);
	}

	@Test
	public void shouldReturnCompleteSinceIsNotAnalyzable() throws Exception {
		when(formatterService.isAnalyzable(metadata)).thenReturn(false);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.Complete, result);
		verify(formatterService, never()).wasFormatted(metadata);
		verify(formatterService, never()).wasCamelized(metadata);
	}

}
