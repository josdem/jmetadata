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

package org.jas.gui;

import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.engines.support.ViewEngineConfigurator;
import org.fest.swing.fixture.FrameFixture;
import org.jas.action.Actions;
import org.jas.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestLoginWindow {
    @InjectMocks
    private LoginWindow loginWindow = new LoginWindow();

    private static final String USERNAME_TEXTFIELD_NAME = "loginTextField";
    private static final String PASSWORD_TEXTFIELD_NAME = "passwordLoginField";
    private static final String SIGN_UP_BUTTON_NAME = "buttonCenterLogin";
    private static final Dimension FRAME_DIMENSION = new Dimension(390, 180);
    private FrameFixture window;
    private String user = "josdem";
    private String password = "password";

    @Mock
    private ViewEngineConfigurator configurator;
    @Mock
    private ViewEngine viewEngine;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(configurator.getViewEngine()).thenReturn(viewEngine);
        window = new FrameFixture(loginWindow.getFrame());
        window.show(FRAME_DIMENSION);
    }

    @Test
    public void shouldLoginByActionListener() {
        setUsernameAndPassword();
        window.button(SIGN_UP_BUTTON_NAME).click();

        verify(viewEngine).sendValueAction(eq(Actions.LOGIN), isA(User.class));
    }

    private void setUsernameAndPassword() {
        window.textBox(USERNAME_TEXTFIELD_NAME).enterText(user);
        window.textBox(PASSWORD_TEXTFIELD_NAME).enterText(password);
    }

    @AfterEach
    public void tearDown() throws Exception {
        window.cleanUp();
    }
}
