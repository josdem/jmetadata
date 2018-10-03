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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnDListenerCollection<T extends DragAndDropListener> {
	private Map<Component, List<T>> listeners = new HashMap<Component, List<T>>();
  private static final Logger log = LoggerFactory.getLogger(DnDListenerCollection.class);

	public void put(Component component, T listener) {
		List<T> listeners = null;
		synchronized (this.listeners) {
			listeners = this.listeners.get(component);
			if (listeners == null) {
				listeners = new ArrayList<T>();
				this.listeners.put(component, listeners);
			}
		}
		synchronized (listeners) {
			for (T t : listeners) {
				if (t.getClass().equals(listener.getClass()) && t.equals(listener)) {
					log.warn("DUPLICATE HANDLER FOR COMPONENT: " + component + " >> " + listener.getClass().getName());
					return;
				}
			}
			listeners.add(listener);
		}
	}

	public void remove(Component component) {
		listeners.remove(component);
	}

	public void remove(Component component, T listener) {
		List<T> listeners = this.listeners.get(component);
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				this.listeners.remove(component);
			}
		}
	}

	public DnDListenerEntries<T> getInmediateEntries(Class<?> clazz, Component component) {
		DnDListenerEntries<T> entries = new DnDListenerEntries<T>();
		if (component == null) {
			return entries;
		}
		List<T> listeners = this.listeners.get(component);
		if (listeners != null) {
			for (T t : listeners) {
				entries.put(clazz, component, t);
			}
		}
		if (entries.isEmpty()) {
			return getInmediateEntries(clazz, component.getParent());
		} else {
			return entries;
		}
	}

	public DnDListenerEntries<T> getUpwardEntries(Class<?> clazz, Component c) {
		DnDListenerEntries<T> dragOverListeners = new DnDListenerEntries<T>();
		addListenersToMotionMap(clazz, c, dragOverListeners);
		return dragOverListeners;
	}

	private void addListenersToMotionMap(Class<?> clazz, Component c, DnDListenerEntries<T> result) {
		if (c == null) {
			return;
		}
		List<T> list = listeners.get(c);
		if (list != null) {
			for (T t : list) {
				result.put(clazz, c, t);
			}
		}
		addListenersToMotionMap(clazz, c.getParent(), result);
	}
}
