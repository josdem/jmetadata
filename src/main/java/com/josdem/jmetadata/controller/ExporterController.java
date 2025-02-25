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
import com.josdem.jmetadata.helper.ExporterHelper;
import com.josdem.jmetadata.model.ExportPackage;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asmatron.messengine.annotations.RequestMethod;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExporterController {

  private final ExporterHelper exporterHelper;

  @RequestMethod(Actions.EXPORT_METADATA)
  public ActionResult sendMetadata(ExportPackage exportPackage)
      throws CannotReadException, TagException, ReadOnlyFileException, MetadataException {
    try {
      return exporterHelper.export(exportPackage);
    } catch (IOException ioe) {
      log.error(ioe.getMessage(), ioe);
      return ActionResult.ERROR;
    }
  }
}
