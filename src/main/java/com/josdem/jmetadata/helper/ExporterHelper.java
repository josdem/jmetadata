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

package com.josdem.jmetadata.helper;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.model.ExportPackage;
import com.josdem.jmetadata.model.Metadata;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExporterHelper {

  @Autowired private ImageExporter imageExporter;
  @Autowired private MetadataExporter metadataExporter;

  public ActionResult export(ExportPackage exportPackage)
      throws IOException,
          CannotReadException,
          TagException,
          ReadOnlyFileException,
          MetadataException {
    List<Metadata> metadatas = exportPackage.getMetadataList();
    for (Metadata metadata : metadatas) {
      metadata.setOrderByFile(true);
    }
    Collections.sort(metadatas);
    exportPackage.setMetadataList(metadatas);
    imageExporter.export(exportPackage);
    metadataExporter.export(exportPackage);
    return ActionResult.EXPORTED;
  }
}
