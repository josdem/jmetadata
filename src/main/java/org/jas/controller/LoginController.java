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

package org.jas.controller;

import de.umass.lastfm.Session;
import org.asmatron.messengine.annotations.ActionMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.action.Actions;
import org.jas.event.Events;
import org.jas.helper.LastFMAuthenticator;
import org.jas.model.Model;
import org.jas.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private LastFMAuthenticator lastfmAuthenticator;
    @Autowired
    private ControlEngineConfigurator configurator;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
