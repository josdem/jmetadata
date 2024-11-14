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

package org.jas;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jas.helper.ApplicationContextSingleton;
import org.asmatron.messengine.engines.DefaultEngine;
import org.jas.laf.HipecotechLookAndFeel;
import org.springframework.context.ConfigurableApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class Launcher {
	private static final String HIPECOTECH_LNF = "org.jas.laf.HipecotechLookAndFeel";

  private static final Logger log = LoggerFactory.getLogger(Launcher.class);

	public Launcher(ConfigurableApplicationContext applicationContext) {
		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		defaultEngine.start();
	}

	public static void main(String[] args) throws FileNotFoundException, ParseException {

		try {
			HipecotechLookAndFeel hipecotechLookAndFeel = new HipecotechLookAndFeel();
			File file = new File("src/main/resources/style/Hipecotech.xml");
			hipecotechLookAndFeel.load(new FileInputStream(file), HipecotechLookAndFeel.class);
			UIManager.setLookAndFeel(HIPECOTECH_LNF);
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}


		new Launcher(ApplicationContextSingleton.getApplicationContext());
	}
}
