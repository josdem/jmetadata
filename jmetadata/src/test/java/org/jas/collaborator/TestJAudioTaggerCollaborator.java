package org.jas.collaborator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.Tag;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestJAudioTaggerCollaborator {

	@InjectMocks
	private JAudioTaggerCollaborator jAudioTaggerCollaborator = new JAudioTaggerCollaborator();
	
	@Mock
	private Tag tag;
	@Mock
	private AudioHeader header;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldValidateTagAndHeader() throws Exception {
		assertTrue(jAudioTaggerCollaborator.isValid(tag, header));
	}
	
	@Test
	public void shouldDetectInvalidTag() throws Exception {
		tag = null;
		assertFalse(jAudioTaggerCollaborator.isValid(tag, header));
	}
	
	@Test
	public void shouldDetectInvalidHeader() throws Exception {
		header = null;
		assertFalse(jAudioTaggerCollaborator.isValid(tag, header));
	}

}
