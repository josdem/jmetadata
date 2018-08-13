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
import java.awt.Point;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DragAndDropIterators {
	private static final Log log = LogFactory.getLog(DragAndDropIterators.class);

	public static DoInListeners<Void, DragOverListener> dragEnter(Point point, DraggedObject draggedObject, boolean validationResult,
			Container currentFrame) {
		return new DragEnterIterator(point, draggedObject, validationResult, currentFrame);
	}

	public static DoInListeners<Void, DragOverListener> dragExit(boolean causeOfDrop) {
		return new DragExitIterator(causeOfDrop);
	}

	public static DoInListeners<Void, DragOverListener> updateLocation(Point point, Container currentFrame) {
		return new UpdateLocationIterator(point, currentFrame);
	}

	public static DoInListeners<Void, DragOverListener> dropOcurred(boolean success) {
		return new DropOcurredIterator(success);
	}

	public static DoInListeners<Boolean, DropListener> doDrop(Point point, DraggedObject draggedObject, Container currentFrame) {
		return new DoDropIterator(point, draggedObject, currentFrame);
	}

	public static DoInListeners<Boolean, DropListener> validateDrop(DraggedObject draggedObject, Point point, Container currentFrame) {
		return new ValidateDropIterator(draggedObject, point, currentFrame);
	}

	public static DoInListeners<Void, DragOverListener> dragAllowedChanged(boolean validationResult) {
		return new DragAllowedChangedIterator(validationResult);
	}

	private static final class DragEnterIterator extends DoInListenersAdapter<Void, DragOverListener> {
		private final Point point;
		private final DraggedObject draggedObject;
		private final boolean validationResult;
		private final Container currentFrame;

		private DragEnterIterator(Point point, DraggedObject draggedObject, boolean validationResult, Container currentFrame) {
			this.point = point;
			this.draggedObject = draggedObject;
			this.validationResult = validationResult;
			this.currentFrame = currentFrame;
		}

		@Override
		public Void doIn(DragOverListener listener, Component component, Void lastResult) {
			log("dragEnter", component, listener);
			listener.dragEnter(draggedObject);
			listener.dragAllowedChanged(validationResult);
			Point location = convertToComponentRelativePoint(point, currentFrame, component);
			listener.updateLocation(location);
			return null;
		}
	}

	private static final class DragExitIterator extends DoInListenersAdapter<Void, DragOverListener> {
		private final boolean causeOfDrop;

		private DragExitIterator(boolean causeOfDrop) {
			this.causeOfDrop = causeOfDrop;
		}

		@Override
		public Void doIn(DragOverListener listener, Component component, Void lastResult) {
			log("dragExit", component, listener);
			listener.dragExit(causeOfDrop);
			return null;
		}
	}

	private static final class UpdateLocationIterator extends DoInListenersAdapter<Void, DragOverListener> {
		private final Point point;
		private final Container currentFrame;

		private UpdateLocationIterator(Point point, Container currentFrame) {
			this.point = point;
			this.currentFrame = currentFrame;
		}

		@Override
		public Void doIn(DragOverListener listener, Component component, Void lastResult) {
			log("updateLocation", component, listener);
			Point location = convertToComponentRelativePoint(point, currentFrame, component);
			listener.updateLocation(location);
			return null;
		}
	}

	private static final class DropOcurredIterator extends DoInListenersAdapter<Void, DragOverListener> {
		private final boolean success;

		private DropOcurredIterator(boolean success) {
			this.success = success;
		}

		@Override
		public Void doIn(DragOverListener listener, Component component, Void lastResult) {
			log("dropOcurred", component, listener);
			listener.dropOcurred(success);
			return null;
		}
	}

	private static final class DoDropIterator implements DoInListeners<Boolean, DropListener> {
		private final Point point;
		private final DraggedObject draggedObject;
		private final Container currentFrame;

		// private ExecutorService executorService;

		private DoDropIterator(Point point, DraggedObject draggedObject, Container currentFrame) {
			this.point = point;
			this.draggedObject = draggedObject;
			this.currentFrame = currentFrame;
			// executorService = Executors.newSingleThreadExecutor();
		}

		@Override
		public Boolean doIn(DropListener listener, Component component, Boolean lastResult) {
			log("doDrop", component, listener);
			Point location = convertToComponentRelativePoint(point, currentFrame, component);
			boolean validateDrop = listener.validateDrop(draggedObject, location);
			if (validateDrop) {
				doDrop(listener, draggedObject, location);
			}
			return createBoolean(validateDrop, lastResult);
		}

		private void doDrop(final DropListener listener, final DraggedObject draggedObject, final Point location) {
			// executorService.execute(new Runnable() {
			// @Override
			// public void run() {
			listener.doDrop(draggedObject, location);
			// }
			// });
		}

		@Override
		public void done() {
			// executorService.shutdown();
		}
	}

	private static final class ValidateDropIterator extends DoInListenersAdapter<Boolean, DropListener> {
		private final DraggedObject draggedObject;
		private final Point point;
		private final Container currentFrame;

		private ValidateDropIterator(DraggedObject draggedObject, Point point, Container currentFrame) {
			this.draggedObject = draggedObject;
			this.point = point;
			this.currentFrame = currentFrame;
		}

		@Override
		public Boolean doIn(DropListener listener, Component component, Boolean lastResult) {
			log("validateDrop", component, listener);
			Point location = convertToComponentRelativePoint(point, currentFrame, component);
			boolean validateDrop = listener.validateDrop(draggedObject, location);
			return createBoolean(validateDrop, lastResult);
		}
	}

	private static final class DragAllowedChangedIterator extends DoInListenersAdapter<Void, DragOverListener> {
		private final boolean validationResult;

		private DragAllowedChangedIterator(boolean validationResult) {
			this.validationResult = validationResult;
		}

		@Override
		public Void doIn(DragOverListener listener, Component component, Void lastResult) {
			log("dragAllowedChanged", component, listener);
			listener.dragAllowedChanged(validationResult);
			return null;
		}
	}

	private static Point convertToComponentRelativePoint(Point location, Container currentFrame, Component component) {
		Point componentLocation = new Point(0, 0);
		if (component == currentFrame) {
			componentLocation = (Point) location.clone();
		} else if (component != null) {
			componentLocation = SwingUtilities.convertPoint(currentFrame, location, component);
		}
		return componentLocation;
	}

	public static boolean createBoolean(Boolean... booleans) {
		if (booleans == null || booleans.length == 0) {
			return false;
		}
		for (Boolean bool : booleans) {
			if (bool != null && bool) {
				return true;
			}
		}
		return false;
	}

	private static void log(String method, Object... objects) {
		log.debug("[" + method + "] " + Arrays.toString(objects));
	}
}
