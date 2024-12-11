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

package org.jas.observer;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j


public class Observable<T extends ObserveObject> implements ObserverCollection<T> {
	private List<Observer<T>> observers = new ArrayList<Observer<T>>();


	public boolean fire(T param) {
		if (param == null) {
			return true;
		}
		boolean ok = true;
		List<Observer<T>> observers = new ArrayList<Observer<T>>(this.observers);
		for (Observer<T> listener : observers) {
			try {
				listener.observe(param);
			} catch (Exception e) {
				ok = false;
				log.error(e.getMessage(), e);
			}
		}
		return ok;
	}

	public void add(Observer<T> l) {
		if (l == null) {
			throw new NullPointerException("Can't add a null listener.");
		}
		observers.add(l);
	}

	public void remove(Observer<T> listener) {
		observers.remove(listener);
	}

	public void clean() {
		observers.clear();
	}
}
