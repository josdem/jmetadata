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

public class SimpleDraggedObject implements DraggedObject {
	private final Object wrappedObject;

	public SimpleDraggedObject(Object wrappedObject) {
		this.wrappedObject = wrappedObject;
	}

	public Class<?> getContentClass() {
		return wrappedObject.getClass();
	}

	@Override
	public <T> T get(Class<T> clazz) {
		if (clazz == null) {
			@SuppressWarnings("unchecked")
			T wrap = (T) wrappedObject;
			return wrap;
		}
		if (is(clazz)) {
			@SuppressWarnings("unchecked")
			T wrap = (T) wrappedObject;
			return wrap;
		}
		return null;
	}

	@Override
	public Object get() {
		return wrappedObject;
	}

	@Override
	public boolean is(Class<?>... clazz) {
		return is(wrappedObject, clazz);
	}

	public static boolean is(Object object, Class<?>... clazz) {
		if (clazz == null || clazz.length == 0) {
			return object == null;
		}
		if (object != null) {
			Class<?> wrapClass = object.getClass();
			for (Class<?> targetClass : clazz) {
				if (targetClass.isAssignableFrom(wrapClass)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

}
