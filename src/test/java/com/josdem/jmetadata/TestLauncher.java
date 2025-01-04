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

package com.josdem.jmetadata;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.asmatron.messengine.engines.DefaultEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

public class TestLauncher {
  @Mock private DefaultEngine defaultEngine;
  @Mock private ConfigurableApplicationContext context;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldInitialize() throws Exception {
    when(context.getBean(DefaultEngine.class)).thenReturn(defaultEngine);

    new Launcher(context);

    verify(context).getBean(DefaultEngine.class);
    verify(defaultEngine).start();
  }
}
