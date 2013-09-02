package org.jas.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.jas.helper.AudioFileHelper;
import org.jaudiotagger.audio.AudioFile;
import org.junit.Test;

public class TestAudioFileHelper {

	private AudioFileHelper audioFileHelper = new AudioFileHelper();
	
	private File pepeGarden = new File("src/test/resources/audio/Jaytech - Pepe Garden (Original Mix).mp3");
	
	@Test
	public void shouldRead() throws Exception {
		AudioFile read = audioFileHelper.read(pepeGarden);
		assertNotNull(read);
	}

}
