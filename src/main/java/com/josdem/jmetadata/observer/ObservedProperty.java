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

package com.josdem.jmetadata.observer;

public class ObservedProperty<S, V> {
  private final S source;
  private Observable<ObservePropertyChanged<S, V>> ob =
      new Observable<ObservePropertyChanged<S, V>>();
  private V value;
  private ObservePropertyChanged<S, V> lastEvent;

  public ObservedProperty(S s, V value) {
    this.source = s;
    this.value = value;
  }

  public ObservedProperty(S s) {
    this.source = s;
  }

  public void setValue(V v) {
    if (v == this.value || (this.value != null && this.value.equals(v))) {
      return;
    }
    setValueAndRaiseEvent(v);
  }

  public V getValue() {
    return value;
  }

  public ObserverCollection<ObservePropertyChanged<S, V>> on() {
    return ob;
  }

  public void addAndLaunch(Observer<ObservePropertyChanged<S, V>> observer) {
    ObservePropertyChanged<S, V> event = lastEvent;
    if (event == null) {
      event = new ObservePropertyChanged<S, V>(source, value, value);
    }
    observer.observe(event);
    on().add(observer);
  }

  public void setValueAndRaiseEvent(V newValue) {
    V oldValue = this.value;
    this.value = newValue;
    lastEvent = new ObservePropertyChanged<S, V>(source, newValue, oldValue);
    ob.fire(lastEvent);
  }

  public static <S, V> ObservedProperty<S, V> newProp(S s) {
    return new ObservedProperty<S, V>(s);
  }
}
