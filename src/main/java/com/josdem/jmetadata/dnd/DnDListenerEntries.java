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
import java.util.ArrayList;
import java.util.List;

public class DnDListenerEntries<T extends DragAndDropListener> {
  @SuppressWarnings({"rawtypes"})
  private static final DnDListenerEntries<?> EMPTY = new DnDListenerEntries();

  private List<DnDEntry<T>> listeners;

  public DnDListenerEntries() {
    this.listeners = new ArrayList<DnDEntry<T>>();
  }

  public <R> R forEach(DoInListeners<R, T> doIn) {
    return forEachExclude(doIn, null);
  }

  public <R> R forEachExclude(DoInListeners<R, T> doIn, DnDListenerEntries<T> excludedListeners) {
    R lastResult = null;
    for (DnDEntry<T> listener : listeners) {
      if (excludedListeners == null
          || !excludedListeners.containsValue(listener.getKey(), listener.getValue())) {
        lastResult = doIn.doIn(listener.getValue(), listener.getKey(), lastResult);
      }
    }
    doIn.done();
    return lastResult;
  }

  public void put(Class<?> clazz, Component component, T t) {
    if (this == EMPTY) {
      return;
    }
    if (isValid(clazz, t.handledTypes())) {
      listeners.add(new DnDEntry<T>(component, t));
    }
  }

  private boolean isValid(Class<?> clazz, Class<?>[] handledTypes) {
    if (clazz == null || handledTypes == null || handledTypes.length == 0) {
      return true;
    }
    for (Class<?> class1 : handledTypes) {
      if (clazz.isAssignableFrom(class1)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsValue(Component component, T value) {
    if (value == null || component == null) {
      return false;
    }
    for (DnDEntry<T> entry : listeners) {
      if (eq(value, entry.getValue()) && eq(component, entry.getKey())) {
        return true;
      }
    }
    return false;
  }

  private boolean eq(Object value, Object entry) {
    return value == entry || value.equals(entry);
  }

  public boolean isEmpty() {
    return listeners.isEmpty();
  }

  public int size() {
    return listeners.size();
  }

  class DnDEntry<Z> {
    private final Component component;
    private final Z t;

    public DnDEntry(Component component, Z t) {
      this.component = component;
      this.t = t;
    }

    public Component getKey() {
      return component;
    }

    public Z getValue() {
      return t;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends DragAndDropListener> DnDListenerEntries<T> empty() {
    return (DnDListenerEntries<T>) EMPTY;
  }
}
