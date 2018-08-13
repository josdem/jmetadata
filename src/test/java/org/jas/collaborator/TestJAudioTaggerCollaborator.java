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
