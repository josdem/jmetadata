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

package com.josdem.jmetadata.service.impl;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.LastfmAlbum;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.LastFMCompleteService;
import com.josdem.jmetadata.service.LastfmService;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastfmServiceImpl implements LastfmService {

  private final LastFMCompleteService completeService;

  public synchronized ActionResult completeLastFM(Metadata metadata) {
    try {
      if (completeService.canLastFMHelpToComplete(metadata)) {
        LastfmAlbum lastfmAlbum = completeService.getLastFM(metadata);
        return completeService.isSomethingNew(lastfmAlbum, metadata);
      } else {
        return ActionResult.READY;
      }
    } catch (MalformedURLException mfe) {
      log.error("MalformedURLException: {}", mfe.getMessage(), mfe);
      return ActionResult.ERROR;
    } catch (IOException ioe) {
      log.error("IOException: {}", ioe.getMessage(), ioe);
      return ActionResult.ERROR;
    } catch (IllegalStateException ise) {
      log.error("Missing environment variables for LastFM API Key or Secret: {}", ise.getMessage());
      return ActionResult.ERROR;
    }
  }
}
