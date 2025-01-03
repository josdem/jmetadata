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

package com.josdem.jmetadata.helper;


import lombok.RequiredArgsConstructor;
import org.jaudiotagger.audio.AudioFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {ApplicationContextSingleton.class, AudioFileHelper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TestAudioFileHelper {

    private final AudioFileHelper audioFileHelper;

    private final File pepeGarden = new File("src/test/resources/audio/Jaytech - Pepe Garden (Original Mix).mp3");

    @Test
    public void shouldRead() throws Exception {
        AudioFile read = audioFileHelper.read(pepeGarden);
        assertNotNull(read);
    }

}
