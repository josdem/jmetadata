/*
   Copyright 2025 Jose Morales contact@josdem.io

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

import java.awt.Point;

public abstract class DragOverAdapter implements DragOverListener {
  @Override
  public void dragEnter(DraggedObject o) {}

  @Override
  public void dragExit(boolean dropped) {}

  @Override
  public void updateLocation(Point location) {}

  @Override
  public void dragAllowedChanged(boolean newStatus) {}

  @Override
  public void dropOcurred(boolean success) {}
}
