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

import java.awt.Component;
import java.awt.Point;

import com.josdem.jmetadata.observer.ObservValue;
import com.josdem.jmetadata.observer.ObserverCollection;


public interface DragAndDropAction {

	DragAndDropAction EMPTY_ACTION = new DragAndDropActionEmpty();
  boolean validate(Point location);
	void dragExit();
	boolean drop(Point location);
	void setLocation(Point location);
	void setDragObject(DraggedObject draggedObject);
	void setDropListeners(DnDListenerEntries<DropListener> dropListeners);
	void setDragListeners(DnDListenerEntries<DragOverListener> dragListeners);
	ObserverCollection<ObservValue<Component>> onComponentChangedListener();
	Class<?> getContentClass();
	boolean isDragObjectSet();
	boolean isDone();

}
