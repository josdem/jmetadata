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

package org.jas.dnd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransparencyManagerFactory {
	private static TransparencyManager manager;

  private static final Logger log = LoggerFactory.getLogger(TransparencyManagerFactory.class);

	public static TransparencyManager getManager() {
		if (manager != null) {
			return manager;
		}
		try {
			manager = new Jdk6u10TransparencyManager();
			return manager;
		} catch (Throwable t) {
			log.error("Unexpected exception during TransparencyManager instantiation.", t);
		}
		manager = new NullTransparencyManager();
		return manager;
	}

}
