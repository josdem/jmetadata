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

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;

import org.jas.observer.Observer;
import org.jas.observer.ObservValue;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MultiLayerDropTargetListener extends DropTargetAdapter implements Observer<ObservValue<Component>> {
	private static boolean lastDropSuccess;

	private DnDListenerCollection<DropListener> dropListeners = new DnDListenerCollection<DropListener>();

	private DnDListenerCollection<DragOverListener> dragListeners = new DnDListenerCollection<DragOverListener>();

	private DragAndDropActionFactory actionFactory;

	private DraggedObjectFactory draggedObjectFactory;

	private DragAndDropAction currentAction;

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public MultiLayerDropTargetListener() {
		this.draggedObjectFactory = new DraggedObjectFactory();
		this.actionFactory = new DragAndDropActionFactoryDefault();
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		getDragAction().dragExit();
		lastDropSuccess = false;
		Window window = null;
		Component component;
		Component component2 = component = dtde.getDropTargetContext().getComponent();
		while (window == null && component != null) {
			if (component instanceof Window) {
				window = (Window) component;
			} else {
				component = component.getParent();
			}
		}
		Container currentTriggerFrame = null;
		if (window == null) {
			if (component2 instanceof Container) {
				currentTriggerFrame = (Container) component2;
			} else {
				currentTriggerFrame = component2.getParent();
			}
		} else {
			currentTriggerFrame = window;
		}
		getDragAction(currentTriggerFrame);
		dragOver(dtde);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		initializeTransferable(dtde.getTransferable(), false);
		getDragAction().setLocation(dtde.getLocation());
		if (getDragAction().validate(dtde.getLocation())) {
			dtde.acceptDrag(DnDConstants.ACTION_COPY);
		} else {
			dtde.rejectDrag();
		}
	}

	@Override
	public void drop(final DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_COPY);
		initializeTransferable(dtde.getTransferable(), true);
		boolean success = getDragAction().drop(dtde.getLocation());
		if (success) {
			log .info("drop success");
		}
		lastDropSuccess = success;
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		getDragAction().dragExit();
	}

	private synchronized DragAndDropAction getDragAction(Container container) {
		getDragAction().dragExit();
		currentAction = actionFactory.getAction(container);
		currentAction.onComponentChangedListener().add(this);
		return currentAction;
	}

	private synchronized DragAndDropAction getDragAction() {
		if (currentAction == null || currentAction.isDone()) {
			return DragAndDropAction.EMPTY_ACTION;
		}
		return currentAction;
	}

	private void initializeTransferable(Transferable transferable, boolean isDrop) {
		if (isDrop) {
			getDragAction().setDragObject(draggedObjectFactory.getContent(transferable));
		} else {
			if (!getDragAction().isDragObjectSet()) {
				getDragAction().setDragObject(draggedObjectFactory.getPreview(transferable));
			}
		}
	}

	public void addDropListener(Component component, DropListener listener) {
		// DebugAssist.showConfirmationDialog("COMPONENT:", component.getClass().getName(), "\nListener:",
		// listener.getClass().getName());
		dropListeners.put(component, listener);
	}

	public void addDragListener(Component component, DragOverListener listener) {
		dragListeners.put(component, listener);
	}

	public void removeListeners(Component component) {
		dragListeners.remove(component);
		dropListeners.remove(component);
	}

	public void removeDropListener(Component component, DropListener listener) {
		dropListeners.remove(component, listener);
	}

	public void removeDragListener(Component component, DragOverListener listener) {
		dragListeners.remove(component, listener);
	}

	@Override
	public void observe(ObservValue<Component> eventArgs) {
		Component c = eventArgs.getValue();
		Class<?> contentClass = getDragAction().getContentClass();
		getDragAction().setDropListeners(dropListeners.getInmediateEntries(contentClass, c));
		getDragAction().setDragListeners(dragListeners.getUpwardEntries(contentClass, c));
	}

	public static boolean isLastDropSuccess() {
		return lastDropSuccess;
	}

}
