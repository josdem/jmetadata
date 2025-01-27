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

package com.josdem.jmetadata.observer;

import java.awt.Component;

public class NullObservable<T extends ObserveObject> extends Observable<T> {
  private static final ObserverCollection<ObservValue<Component>> INSTANCE =
      new NullObservable<ObservValue<Component>>();

  private NullObservable() {}

  public boolean fire(T param) {
    return true;
  }

  public void add(Observer<T> l) {}

  public void remove(Observer<T> listener) {}

  public void clean() {}

  @SuppressWarnings("unchecked")
  public static <T extends ObserveObject> Observable<T> get() {
    return (Observable<T>) INSTANCE;
  }

  @SuppressWarnings("unchecked")
  public static <T extends ObserveObject> ObserverCollection<T> getCollection() {
    return (ObserverCollection<T>) INSTANCE;
  }
}
