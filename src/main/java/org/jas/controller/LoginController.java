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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.ActionMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.jas.action.Actions;
import org.jas.event.Events;
import org.jas.helper.LastFMAuthenticator;
import org.jas.model.Model;
import org.jas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.umass.lastfm.Session;


/**
 * @understands A class who control Login process
*/

@Controller
public class LoginController {
	private LastFMAuthenticator lastfmAuthenticator = new LastFMAuthenticator();
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ControlEngineConfigurator configurator;

	@ActionMethod(Actions.LOGIN_ID)
	public void login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			Session session = lastfmAuthenticator.login(username, password);
			if (session != null) {
				user.setSession(session);
				configurator.getControlEngine().set(Model.CURRENT_USER, user, null);
				configurator.getControlEngine().fireEvent(Events.LOGGED, new ValueEvent<User>(user));
			} else {
				configurator.getControlEngine().fireEvent(Events.LOGIN_FAILED);
			}
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			configurator.getControlEngine().fireEvent(Events.LOGIN_FAILED);
		}
	}

}
