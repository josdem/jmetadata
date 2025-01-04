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

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @understands a class who knows how to get the application context from spring
 */
public class ApplicationContextSingleton {
  private static ConfigurableApplicationContext applicationContext;

  public static ConfigurableApplicationContext getApplicationContext() {
    if (applicationContext == null) {
      applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    }
    return applicationContext;
  }
}
