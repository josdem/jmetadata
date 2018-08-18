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

package org.jas.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.OutputStream;

import org.jas.helper.OutStreamWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TestOutStreamWriter {

	@InjectMocks
	private OutStreamWriter outStreamWriter = new OutStreamWriter();

	private File pepeGarden = new File("src/test/resources/checkstyle/checkstyle.xml");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetWriter() throws Exception {
		OutputStream writer = outStreamWriter.getWriter(pepeGarden);
		assertNotNull(writer);
	}

}
