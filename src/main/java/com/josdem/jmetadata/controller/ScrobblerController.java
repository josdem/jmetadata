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

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.helper.ScrobblerHelper;
import com.josdem.jmetadata.model.Metadata;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.annotations.RequestMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ScrobblerController {

  @Autowired private ScrobblerHelper scrobblerHelper;
  @Autowired private ControlEngineConfigurator configurator;

  @PostConstruct
  public void setup() {
    scrobblerHelper.setControlEngine(configurator.getControlEngine());
  }

  @RequestMethod(Actions.SEND_METADATA)
  public ActionResult sendMetadata(Metadata metadata) {
    try {
      log.info("Sending scrobbling for: {}", metadata.getTitle());
      return scrobblerHelper.send(metadata);
    } catch (IOException | InterruptedException ioe) {
      log.error(ioe.getMessage(), ioe);
      return ActionResult.Error;
    }
  }
}
