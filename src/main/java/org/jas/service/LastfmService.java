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

package org.jas.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.jas.model.Metadata;
import org.jas.model.LastfmAlbum;
import org.jas.action.ActionResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who completes metadata using LastFM service
 */

@Service
public class LastfmService {

	@Autowired
	private LastFMCompleteService completeService;

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public synchronized ActionResult completeLastFM(Metadata metadata) {
		try {
			if (completeService.canLastFMHelpToComplete(metadata)) {
				LastfmAlbum lastfmAlbum = completeService.getLastFM(metadata);
				return completeService.isSomethingNew(lastfmAlbum, metadata);
			} else {
				return ActionResult.Complete;
			}
		} catch (MalformedURLException mfe) {
			log.error(mfe.getMessage(), mfe);
			return ActionResult.Error;
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return ActionResult.Error;
		}
	}
}
