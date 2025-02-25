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

import com.josdem.jmetadata.observer.NullObservable;
import com.josdem.jmetadata.observer.ObservValue;
import com.josdem.jmetadata.observer.Observable;
import com.josdem.jmetadata.observer.ObserverCollection;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;

public class DragAndDropActionImpl implements DragAndDropAction {
  private DnDListenerEntries<DropListener> dropListeners = DnDListenerEntries.empty();
  private DnDListenerEntries<DragOverListener> dragListeners = DnDListenerEntries.empty();
  private Observable<ObservValue<Component>> currentComponentChanged =
      new Observable<ObservValue<Component>>();

  private DraggedObject draggedObject = null;
  private boolean validationResult = false;

  private final Container currentFrame;
  private Component currentComponent;
  private Point location = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
  private boolean done = false;

  public DragAndDropActionImpl(Container currentTriggerFrame) {
    this.currentFrame = currentTriggerFrame;
  }

  @Override
  public boolean validate(final Point point) {
    if (isDone()) {
      return false;
    }
    Boolean result = false;
    if (isDragObjectSet()) {
      final DraggedObject currentDraggedObject = this.draggedObject;
      final DnDListenerEntries<DropListener> currentDropListeners = this.dropListeners;
      result =
          currentDropListeners.forEach(
              DragAndDropIterators.validateDrop(currentDraggedObject, point, currentFrame));
      result = DragAndDropIterators.createBoolean(result);
    } else {
      result = true;
    }
    if (this.validationResult != result.booleanValue()) {
      final DnDListenerEntries<DragOverListener> currentDragListeners = this.dragListeners;
      this.validationResult = result.booleanValue();
      currentDragListeners.forEach(DragAndDropIterators.dragAllowedChanged(result.booleanValue()));
    }
    return validationResult;
  }

  @Override
  public void dragExit() {
    stop(false);
  }

  @Override
  public boolean drop(final Point point) {
    final DraggedObject currentDraggedObject = this.draggedObject;
    final DnDListenerEntries<DropListener> currentDropListeners = this.dropListeners;
    final DnDListenerEntries<DragOverListener> currentDragListeners = this.dragListeners;
    final Container currentFrameInstance = this.currentFrame;
    final boolean isDone = isDone();
    final boolean isDragObjectSet = isDragObjectSet();
    stop(true);
    boolean success = false;
    if (!(isDone || !isDragObjectSet)) {
      Boolean result =
          currentDropListeners.forEach(
              DragAndDropIterators.doDrop(point, currentDraggedObject, currentFrameInstance));
      success = DragAndDropIterators.createBoolean(result);
    }
    currentDragListeners.forEach(DragAndDropIterators.dropOcurred(success));
    return success;
  }

  public void setLocation(Point newLocation) {
    if (isDone()) {
      return;
    }
    if (!this.location.equals(newLocation)) {
      this.location = newLocation;
      Component newComponent = currentFrame.findComponentAt(newLocation);
      if (newComponent != null && !newComponent.equals(currentComponent)) {
        currentComponent = newComponent;
        currentComponentChanged.fire(new ObservValue<Component>(newComponent));
      }
    }
  }

  @Override
  public void setDragObject(DraggedObject newDraggedObject) {
    if (this.draggedObject != newDraggedObject) {
      this.draggedObject = newDraggedObject;
      currentComponentChanged.fire(new ObservValue<Component>(currentComponent));
    }
  }

  public void setDropListeners(DnDListenerEntries<DropListener> newDropListeners) {
    if (newDropListeners == null || isDone()) {
      newDropListeners = DnDListenerEntries.empty();
    }
    this.dropListeners = newDropListeners;
    validate(location);
  }

  public void setDragListeners(DnDListenerEntries<DragOverListener> newDragListeners) {
    setDragListeners(newDragListeners, false);
  }

  private void setDragListeners(
      DnDListenerEntries<DragOverListener> newDragListeners, boolean isForDrop) {
    if (newDragListeners == null || isDone()) {
      newDragListeners = DnDListenerEntries.empty();
    }
    Point currentLocation = this.location;
    DnDListenerEntries<DragOverListener> lastListeners = this.dragListeners;
    lastListeners.forEachExclude(DragAndDropIterators.dragExit(isForDrop), newDragListeners);
    DraggedObject draggedObj = draggedObject;
    boolean valRes = validationResult;
    Container frame = currentFrame;
    newDragListeners.forEachExclude(
        DragAndDropIterators.dragEnter(currentLocation, draggedObj, valRes, frame), lastListeners);
    this.dragListeners = newDragListeners;
  }

  @Override
  public ObserverCollection<ObservValue<Component>> onComponentChangedListener() {
    return this.currentComponentChanged;
  }

  @Override
  public Class<?> getContentClass() {
    if (isDragObjectSet()) {
      return draggedObject.getContentClass();
    } else {
      return null;
    }
  }

  @Override
  public boolean isDragObjectSet() {
    if (isDone()) {
      return true;
    }
    return draggedObject != null && draggedObject.get() != null;
  }

  @Override
  public boolean isDone() {
    return done;
  }

  protected void stop(boolean causeOfDrop) {
    if (!isDone()) {
      done = true;
      currentComponentChanged.clean();
      currentComponentChanged = NullObservable.get();
      setDragListeners(null, causeOfDrop);
      setDropListeners(null);
    }
  }

  protected void updateDragOver() {
    if (isDone()) {
      return;
    }
    final Point currentPoint = location;
    final DnDListenerEntries<DragOverListener> currentDragListeners = this.dragListeners;
    currentDragListeners.forEach(DragAndDropIterators.updateLocation(currentPoint, currentFrame));
  }
}
