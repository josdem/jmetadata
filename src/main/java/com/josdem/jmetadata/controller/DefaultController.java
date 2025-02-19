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
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.DefaultService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.asmatron.messengine.annotations.RequestMethod;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DefaultController {

  private final DefaultService defaultService;

  @RequestMethod(Actions.COMPLETE_DEFAULT_METADATA)
  public synchronized ActionResult complete(List<Metadata> metadatas)
      throws IOException,
          CannotReadException,
          TagException,
          ReadOnlyFileException,
          MetadataException {
    if (defaultService.isCompletable(metadatas)) {
      defaultService.complete(metadatas);
      return ActionResult.NEW;
    }
    return ActionResult.READY;
  }
}
