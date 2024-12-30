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

import java.awt.Container;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DragAndDropActionFactoryDefault implements DragAndDropActionFactory {
	ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	@Override
	public DragAndDropAction getAction(Container frame) {
		DragAndDropActionThreaded dragAndDropActionImpl = new DragAndDropActionThreaded(frame);
		dragAndDropActionImpl.start(executorService);
		return dragAndDropActionImpl;
	}

	class DragAndDropActionThreaded extends DragAndDropActionImpl implements Runnable {
		private ScheduledFuture<?> scheduledFuture;

		public DragAndDropActionThreaded(Container currentTriggerFrame) {
			super(currentTriggerFrame);
		}

		public void start(ScheduledExecutorService executorService) {
			stop();
			this.scheduledFuture = executorService.scheduleAtFixedRate(this, 50, 50, TimeUnit.MILLISECONDS);
		}

		@Override
		protected void stop(boolean causeOfDrop) {
			super.stop(causeOfDrop);
			stop();
		}

		private void stop() {
			if (this.scheduledFuture != null) {
				this.scheduledFuture.cancel(false);
				this.scheduledFuture = null;
			}
		}

		@Override
		public void run() {
			updateDragOver();
		}

	}
}
