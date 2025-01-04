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

import com.josdem.jmetadata.observer.NullObservable;
import com.josdem.jmetadata.observer.ObservValue;
import com.josdem.jmetadata.observer.ObserverCollection;
import java.awt.Component;
import java.awt.Point;

public class DragAndDropActionEmpty implements DragAndDropAction {

  @Override
  public boolean isDone() {
    return true;
  }

  @Override
  public void dragExit() {}

  @Override
  public void setLocation(Point location) {}

  @Override
  public ObserverCollection<ObservValue<Component>> onComponentChangedListener() {
    return NullObservable.get();
  }

  @Override
  public void setDropListeners(DnDListenerEntries<DropListener> dropListeners) {}

  @Override
  public void setDragListeners(DnDListenerEntries<DragOverListener> dragListeners) {}

  @Override
  public boolean validate(Point point) {
    return false;
  }

  @Override
  public boolean isDragObjectSet() {
    return true;
  }

  @Override
  public void setDragObject(DraggedObject draggedObject) {}

  @Override
  public boolean drop(Point location) {
    return false;
  }

  @Override
  public Class<?> getContentClass() {
    return null;
  }
}
