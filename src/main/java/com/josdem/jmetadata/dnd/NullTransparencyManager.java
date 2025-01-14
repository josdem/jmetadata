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

package com.josdem.jmetadata.dnd;

import java.awt.GraphicsConfiguration;
import java.awt.Shape;
import java.awt.Window;

public class NullTransparencyManager implements TransparencyManager {

  @Override
  public boolean isTranslucencySupported(Object kind) {
    return false;
  }

  @Override
  public boolean isTranslucencyCapable(GraphicsConfiguration gc) {
    return false;
  }

  @Override
  public void setWindowShape(Window window, Shape shape) {}

  @Override
  public void setWindowOpacity(Window window, float opacity) {}

  @Override
  public void setWindowOpaque(Window window, boolean opaque) {}

  @Override
  public GraphicsConfiguration getTranslucencyCapableGC() {
    return null;
  }
}
