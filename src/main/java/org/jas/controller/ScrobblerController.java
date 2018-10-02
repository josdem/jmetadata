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

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.asmatron.messengine.annotations.RequestMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;

import org.jas.model.Metadata;
import org.jas.action.Actions;
import org.jas.action.ActionResult;
import org.jas.helper.ScrobblerHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @understands A class who manage ALL scrobbler actions
*/

@Controller
public class ScrobblerController {

	@Autowired
	private ScrobblerHelper scrobblerHelper;
	@Autowired
	private ControlEngineConfigurator configurator;

  private Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void setup() {
		scrobblerHelper.setControlEngine(configurator.getControlEngine());
	}

	@RequestMethod(Actions.SEND_METADATA)
	public ActionResult sendMetadata(Metadata metadata) {
		try {
			log.info("Sending scrobbling for: " + metadata.getTitle());
			return scrobblerHelper.send(metadata);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		} catch (InterruptedException ine) {
			log.error(ine.getMessage(), ine);
		}
		return ActionResult.Error;
	}

}
