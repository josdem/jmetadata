/*
   Copyright 2024 Jose Morales contact@josdem.io

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

package org.jas.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestURLStringEncoder {

    @Test
    @DisplayName("encoding artist")
    void shouldEncodeAlbum(TestInfo testInfo) {
        log.info(testInfo.getDisplayName());
        String album = "New, Live & Rare";
        String encodedAlbum = URLStringEncoder.encode(album);
        assertEquals("New%2C+Live+%26+Rare", encodedAlbum);
    }

}
