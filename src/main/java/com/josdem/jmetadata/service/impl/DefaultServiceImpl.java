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

import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.service.DefaultService;
import com.josdem.jmetadata.service.MetadataService;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultServiceImpl implements DefaultService {

  private static final String CD_NUMBER = "1";
  private static final String TOTAL_CD_NUMBER = "1";

  private final MetadataService metadataService;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public boolean isCompletable(List<Metadata> metadatas) {
    return metadatas.size() >= 2
        && metadataService.isSameAlbum(metadatas)
        && isSomethingMissing(metadatas);
  }

  public void complete(List<Metadata> metadatas) {
    metadatas.forEach(
        metadata -> {
          try {
            metadata.setTotalTracks(String.valueOf(getTotalTracks(metadatas)));
            metadata.setCdNumber(CD_NUMBER);
            metadata.setTotalCds(TOTAL_CD_NUMBER);
          } catch (NumberFormatException nfe) {
            log.warn(
                "NumberFormatException caused by track: {} - {}",
                metadata.getTitle(),
                nfe.getMessage());
          }
        });
  }

  private boolean isSomethingMissing(List<Metadata> metadatas) {
    Optional<Metadata> any =
        metadatas.stream()
            .filter(
                metadata ->
                    StringUtils.isEmpty(metadata.getTotalTracks())
                        || StringUtils.isEmpty(metadata.getCdNumber())
                        || StringUtils.isEmpty(metadata.getTotalCds()))
            .findAny();
    return any.isPresent();
  }

  private int getTotalTracks(List<Metadata> metadatas) {
    OptionalInt optionalInt =
        metadatas.stream().mapToInt(metadata -> Integer.parseInt(metadata.getTrackNumber())).max();
    return optionalInt.isPresent() ? optionalInt.getAsInt() : 0;
  }
}
