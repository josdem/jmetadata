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

package com.josdem.jmetadata;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.josdem.jmetadata.helper.ApplicationContextSingleton;
import org.asmatron.messengine.engines.DefaultEngine;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j


/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to launch ALL the process
 */

public class Launcher {
	private static final String HIPECOTECH_LNF = "org.jas.laf.HipecotechLookAndFeel";


	public Launcher(ConfigurableApplicationContext applicationContext) {
		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		defaultEngine.start();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(HIPECOTECH_LNF);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (UnsupportedLookAndFeelException e) {
			log.error(e.getMessage(), e);
		}
		new Launcher(ApplicationContextSingleton.getApplicationContext());
	}
}
