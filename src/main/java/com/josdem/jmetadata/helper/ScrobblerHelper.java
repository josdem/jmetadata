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

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.model.User;
import de.umass.lastfm.Session;
import de.umass.lastfm.scrobble.ScrobbleResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.ControlEngine;
import org.springframework.stereotype.Service;

@Slf4j

/**
 * @understands A class who knows how to send scrobblings
 */
@Service
public class ScrobblerHelper {
  private static final int ONE_THOUSAND = 1000;
  private static final int MIN_LENGHT = 240;
  private static final int REQUEST_PERIOD = 250;
  private Map<Metadata, Long> metadataMap = new HashMap<Metadata, Long>();
  private LastFMTrackHelper lastFMTrackHelper = new LastFMTrackHelper();
  private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private static final int DELTA = 120;

  private ControlEngine controlEngine;

  private ActionResult scrobbling(Metadata metadata) throws IOException, InterruptedException {
    User currentUser = controlEngine.get(Model.CURRENT_USER);
    if (StringUtils.isEmpty(currentUser.getUsername())) {
      return ActionResult.NotLogged;
    }

    if (currentUser.getSession() != null) {
      try {
        // According to Caching Rule (http://www.u-mass.de/lastfm/doc)
        ScheduledFuture<ActionResult> future =
            scheduler.schedule(
                new ScrobbleTask(metadata, currentUser.getSession()),
                REQUEST_PERIOD,
                TimeUnit.MICROSECONDS);
        return future.get();
      } catch (ExecutionException eex) {
        log.error(eex.getMessage(), eex);
        return ActionResult.Error;
      }
    } else {
      log.error("There isn't a valid session");
      return ActionResult.Sessionless;
    }
  }

  public ActionResult send(Metadata metadata) throws IOException, InterruptedException {
    long time = (System.currentTimeMillis() / ONE_THOUSAND);

    // According to submission rules http://www.last.fm/api/submissions
    if (StringUtils.isNotEmpty(metadata.getArtist())
        && StringUtils.isNotEmpty(metadata.getTitle())
        && metadata.getLength() > MIN_LENGHT) {
      long startTime = time - (metadataMap.size() * DELTA);
      metadataMap.put(metadata, startTime);
      return scrobbling(metadata);
    }
    return ActionResult.Not_Scrobbleable;
  }

  public void setControlEngine(ControlEngine controlEngine) {
    this.controlEngine = controlEngine;
  }

  private class ScrobbleTask implements Callable<ActionResult> {
    private final Metadata metadata;
    private final Session session;

    public ScrobbleTask(Metadata metadata, Session session) {
      this.metadata = metadata;
      this.session = session;
    }

    @Override
    public ActionResult call() throws Exception {
      ScrobbleResult result =
          lastFMTrackHelper.scrobble(
              metadata.getArtist(),
              metadata.getTitle(),
              metadataMap.get(metadata).intValue(),
              session);
      if (result.isSuccessful() && !result.isIgnored()) {
        log.debug(
            metadata.getArtist()
                + " - "
                + metadata.getTitle()
                + " scrobbling to Last.fm was Successful");
        return ActionResult.Sent;
      } else {
        log.error("Submitting track " + metadata.getTitle() + " to Last.fm failed: " + result);
        return ActionResult.Error;
      }
    }
  }
}
