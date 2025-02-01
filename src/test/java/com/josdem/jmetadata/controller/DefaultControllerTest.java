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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.DefaultService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class DefaultControllerTest {

  @InjectMocks private final DefaultController defaultController = new DefaultController();

  @Mock private DefaultService defaultService;

  private final List<Metadata> metadatas = new ArrayList<Metadata>();

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("completing total tracks")
  void shouldCompleteTotalTracks(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(defaultService.isCompletable(metadatas)).thenReturn(true);

    var result = defaultController.complete(metadatas);

    verify(defaultService).complete(metadatas);
    assertEquals(ActionResult.New, result);
  }

  @Test
  @DisplayName("not completing total tracks")
  void shouldNotCompleteTotalTracks(TestInfo testInfo) throws Exception {
    log.info(testInfo.getDisplayName());
    when(defaultService.isCompletable(metadatas)).thenReturn(false);

    var result = defaultController.complete(metadatas);

    assertEquals(ActionResult.Ready, result);
  }
}
