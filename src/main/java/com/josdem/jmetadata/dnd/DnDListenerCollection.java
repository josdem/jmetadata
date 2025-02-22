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

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DnDListenerCollection<T extends DragAndDropListener> {
  private Map<Component, List<T>> listeners = new HashMap<>();

  public void put(Component component, T listener) {
    List<T> componentListeners = null;
    synchronized (this.listeners) {
      componentListeners = this.listeners.get(component);
      if (componentListeners == null) {
        componentListeners = new ArrayList<>();
        this.listeners.put(component, componentListeners);
      }
    }
    synchronized (componentListeners) {
      for (T t : componentListeners) {
        if (t.getClass().equals(listener.getClass()) && t.equals(listener)) {
          log.warn(
              "DUPLICATE HANDLER FOR COMPONENT: "
                  + component
                  + " >> "
                  + listener.getClass().getName());
          return;
        }
      }
      componentListeners.add(listener);
    }
  }

  public void remove(Component component) {
    listeners.remove(component);
  }

  public void remove(Component component, T listener) {
    List<T> componentListeners = this.listeners.get(component);
    if (componentListeners != null) {
      componentListeners.remove(listener);
      if (componentListeners.isEmpty()) {
        this.listeners.remove(component);
      }
    }
  }

  public DnDListenerEntries<T> getInmediateEntries(Class<?> clazz, Component component) {
    DnDListenerEntries<T> entries = new DnDListenerEntries<>();
    if (component == null) {
      return entries;
    }
    List<T> componentListeners = this.listeners.get(component);
    if (componentListeners != null) {
      for (T t : componentListeners) {
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
    DnDListenerEntries<T> dragOverListeners = new DnDListenerEntries<>();
    addListenersToMotionMap(clazz, c, dragOverListeners);
    return dragOverListeners;
  }

  private void addListenersToMotionMap(Class<?> clazz, Component c, DnDListenerEntries<T> result) {
    if (c == null) {
      return;
    }
    List<T> componentListeners = listeners.get(c);
    if (componentListeners != null) {
      for (T t : componentListeners) {
        result.put(clazz, c, t);
      }
    }
    addListenersToMotionMap(clazz, c.getParent(), result);
  }
}
