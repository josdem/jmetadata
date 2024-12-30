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

package com.josdem.jmetadata.helper;


import org.asmatron.messengine.engines.DefaultEngine;
import com.josdem.jmetadata.gui.LoginWindow;
import com.josdem.jmetadata.gui.MainWindow;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestApplicationContextSingleton {

    @Test
    public void shouldCreateAnApplicationContext() throws Exception {
        ConfigurableApplicationContext applicationContext = ApplicationContextSingleton.getApplicationContext();
        DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
        MainWindow mainWindow = applicationContext.getBean(MainWindow.class);
        LoginWindow loginWindow = applicationContext.getBean(LoginWindow.class);
        assertNotNull(defaultEngine);
        assertNotNull(mainWindow);
        assertNotNull(loginWindow);
    }

}
