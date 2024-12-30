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

import de.umass.lastfm.Session;
import org.asmatron.messengine.annotations.ActionMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.helper.LastFMAuthenticator;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private LastFMAuthenticator lastfmAuthenticator;
    @Autowired
    private ControlEngineConfigurator configurator;

 

    @ActionMethod(Actions.LOGIN_ID)
    public void login(User user) {
        var username = user.getUsername();
        var password = user.getPassword();
        log.info("Sending login information for user: {}", username);
        try {
            Session session = lastfmAuthenticator.login(username, password);
            if (session != null) {
                user.setSession(session);
                configurator.getControlEngine().set(Model.CURRENT_USER, user, null);
                configurator.getControlEngine().fireEvent(Events.LOGGED, new ValueEvent<>(user));
            } else {
                log.warn("Session for user {} failed", username);
                configurator.getControlEngine().fireEvent(Events.LOGIN_FAILED);
            }
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
            configurator.getControlEngine().fireEvent(Events.LOGIN_FAILED);
        }
    }

}
