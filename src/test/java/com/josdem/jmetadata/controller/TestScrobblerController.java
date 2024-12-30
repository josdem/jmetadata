/*
   Copyright 2024 Jose Morales contact@josdem.io

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

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.helper.ScrobblerHelper;
import com.josdem.jmetadata.model.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import lombok.extern.slf4j.Slf4j;

@Slf4j


class TestScrobblerController {

    @InjectMocks
    private final ScrobblerController controller = new ScrobblerController();

    @Mock
    private Metadata metadata;
    @Mock
    private ControlEngineConfigurator configurator;
    @Mock
    private ControlEngine controlEngine;
    @Mock
    private ScrobblerHelper scrobblerHelper;


    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(configurator.getControlEngine()).thenReturn(controlEngine);
    }

    @Test
    @DisplayName("sending metadata")
    public void shouldSendMetadata(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        when(scrobblerHelper.send(metadata)).thenReturn(ActionResult.New);

        ActionResult result = controller.sendMetadata(metadata);

        verify(scrobblerHelper).send(metadata);
        assertEquals(ActionResult.New, result);
    }

    @Test
    @DisplayName("detecting error in scrobbling")
    public void shouldDetectWhenErrorInScrobbling(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        when(scrobblerHelper.send(metadata)).thenReturn(ActionResult.Error);

        ActionResult result = controller.sendMetadata(metadata);

        verify(scrobblerHelper).send(metadata);
        assertEquals(ActionResult.Error, result);
    }

    @Test
    @DisplayName("setting up scrobbler")
    public void shouldSetup(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        controller.setup();
        verify(scrobblerHelper).setControlEngine(controlEngine);
    }

    @Test
    @DisplayName("catching IOException")
    public void shouldCatchIOException(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        when(scrobblerHelper.send(metadata)).thenThrow(new IOException());

        ActionResult result = controller.sendMetadata(metadata);

        assertEquals(ActionResult.Error, result);
    }

    @Test
    @DisplayName("catching InterruptedException")
    public void shouldCatchInterruptedException(TestInfo testInfo) throws Exception {
        log.info(testInfo.getDisplayName());
        when(scrobblerHelper.send(metadata)).thenThrow(new InterruptedException());

        ActionResult result = controller.sendMetadata(metadata);

        assertEquals(ActionResult.Error, result);
    }
}