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

import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.exception.MetadataException;
import com.josdem.jmetadata.model.ExportPackage;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.FormatterService;
import com.josdem.jmetadata.service.MetadataService;
import com.josdem.jmetadata.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetadataExporter {
  private static final String NEW_LINE = "\n";
  private static final String DASH = " - ";
  private static final String DOT = ". ";
  private static final String PAR_OPEN = " (";
  private static final String PAR_CLOSE = ")";
  private static final String BY = " by ";

  private final FileUtils fileUtils;
  private final OutStreamWriter outputStreamWriter;
  private final MetadataService metadataService;
  private final FormatterService formatter;

  public void export(ExportPackage exportPackage)
      throws IOException,
          CannotReadException,
          TagException,
          ReadOnlyFileException,
          MetadataException {
    File file =
        fileUtils.createFile(
            exportPackage.getRoot(), StringUtils.EMPTY, ApplicationConstants.FILE_EXT);
    log.info("Exporting metadata to: {}", file.getAbsolutePath());
    OutputStream writer = outputStreamWriter.getWriter(file);
    int counter = 1;
    List<Metadata> metadataList = exportPackage.getMetadataList();
    if (metadataService.isSameAlbum(metadataList)) {
      writer.write(metadataList.getFirst().getAlbum().getBytes());
      writer.write(BY.getBytes());
      writer.write(metadataList.getFirst().getArtist().getBytes());
      writer.write(NEW_LINE.getBytes());
      writer.write(NEW_LINE.getBytes());
    }
    for (Metadata metadata : metadataList) {
      writer.write(Integer.toString(counter++).getBytes());
      writer.write(DOT.getBytes());
      writer.write(metadata.getArtist().getBytes());
      writer.write(DASH.getBytes());
      writer.write(metadata.getTitle().getBytes());
      writer.write(PAR_OPEN.getBytes());
      writer.write(formatter.getDuration(metadata.getLength()).getBytes());
      writer.write(PAR_CLOSE.getBytes());
      writer.write(NEW_LINE.getBytes());
    }
    writer.flush();
    writer.close();
  }
}
