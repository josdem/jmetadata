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

import java.util.List;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.asmatron.messengine.annotations.RequestMethod;

import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jas.action.Actions;
import org.jas.model.Metadata;
import org.jas.action.ActionResult;
import org.jas.service.DefaultService;
import org.jas.metadata.MetadataException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who completes metadata based in a compilation or album
 */

@Controller
public class DefaultController {

	@Autowired
	private DefaultService defaultService;

	@RequestMethod(Actions.COMPLETE_DEFAULT_METADATA)
	public synchronized ActionResult complete(List<Metadata> metadatas) throws IOException, CannotReadException, TagException, ReadOnlyFileException, MetadataException {
		if (defaultService.isCompletable(metadatas) == true){
			defaultService.complete(metadatas);
			return ActionResult.New;
		}
		return ActionResult.Complete;
	}

}
