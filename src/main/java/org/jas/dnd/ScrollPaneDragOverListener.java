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

import java.awt.Point;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ScrollPaneDragOverListener extends DragOverAdapter {
	private static final int AREA_FACTOR = 1;
	private static final int VELOCITY_FACTOR = 10;
	private static final int VELOCITY_MS = 500;
	private static final int BEGIN = 160;
	private static final int AUTO_SCROLL_AREA_COUNT = 3;
	private static final int AUTO_SCROLL_AREA_HEIGHT = 12;
	private static final int AUTO_SCROLL_AREA_LIMIT = AUTO_SCROLL_AREA_HEIGHT * AUTO_SCROLL_AREA_COUNT;

	private int lastScrollPaneVelocity = 0;

	private long lastScrollPaneDragOverEvent = System.currentTimeMillis();
	private final JScrollPane scrollPane;

	@Override
	public void updateLocation(Point location) {
		manageVerticalScrollBar(location);
	}

	public ScrollPaneDragOverListener(JScrollPane jScrollPane) {
		scrollPane = jScrollPane;
	}

	void manageVerticalScrollBar(Point location) {
		if (!canProcessDragEvent()) {
			return;
		}
		int adjustment = 3;
		int velocity = checkAreaForScrollVelocity(location.y) * adjustment;
		if (!canStartScrolling(velocity)) {
			return;
		}
		moveScrollbar(velocity);
	}

	private boolean canProcessDragEvent() {
		// process events continuously around 60 ms period
		if (System.currentTimeMillis() - lastScrollPaneDragOverEvent < 60) {
			return false;
		}
		lastScrollPaneDragOverEvent = System.currentTimeMillis();
		return true;
	}

	public int checkAreaForScrollVelocity(int y) {
		int velocity = 0;
		if (isInsideUpperAutoScrollableArea(y)) {
			velocity = getUpperAreaVelocity(y) * -1;
		} else if (isInsideBottomAutoScrollableArea(y)) {
			velocity = getBottomAreaVelocity(y);
		}
		return velocity;
	}

	private int getBottomAreaVelocity(int y) {
		int velocity = VELOCITY_FACTOR;
		int area = AREA_FACTOR;
		int scrollPaneHeight = getScrollPaneHeight();
		while (scrollPaneHeight - AUTO_SCROLL_AREA_HEIGHT * area > y) {
			area++;
			velocity--;
		}
		return velocity;
	}

	int getScrollPaneHeight() {
		return scrollPane.getHeight();
	}

	private int getUpperAreaVelocity(int y) {
		int velocity = VELOCITY_FACTOR;
		int area = AREA_FACTOR;
		while (y > (BEGIN + AUTO_SCROLL_AREA_HEIGHT * area)) {
			area++;
			velocity--;
		}
		return velocity;
	}

	private boolean isInsideUpperAutoScrollableArea(int y) {
		return (0 <= y && y <= (BEGIN + AUTO_SCROLL_AREA_LIMIT));
	}

	private boolean isInsideBottomAutoScrollableArea(int y) {
		int scrollPaneHeight = getScrollPaneHeight();
		int limit = scrollPaneHeight - AUTO_SCROLL_AREA_LIMIT;
		return limit >= 0 && limit <= y && y <= scrollPaneHeight;
	}

	private void moveScrollbar(int velocity) {
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		int currentVal = verticalScrollBar.getValue();
		verticalScrollBar.setValue(currentVal + velocity);
	}

	private boolean canStartScrolling(int velocity) {
		// start scrolling after VELOCITY_MS milliseconds of the first drag over event received
		if (lastScrollPaneVelocity == 0 && velocity != 0) {
			lastScrollPaneDragOverEvent = System.currentTimeMillis() + VELOCITY_MS;
			lastScrollPaneVelocity = velocity;
			return false;
		}
		lastScrollPaneVelocity = velocity;
		return true;
	}

	@Override
	public Class<?>[] handledTypes() {
		return null;
	}

}
