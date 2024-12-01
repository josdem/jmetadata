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
