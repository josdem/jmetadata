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

package org.jas.util;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j


public class MethodWrapper<T> {
	private final Method method;
	private final Class<?> returnType;


	public static MethodWrapperBuilderPhase1 forClass(String className) {
		return new MethodWrapperBuilder(className);
	}

	public static MethodWrapperBuilderPhase1 forClass(Class<?> clazz) {
		return new MethodWrapperBuilder(clazz);
	}

	private MethodWrapper(Method method, Class<?> returnType) {
		this.method = method;
		this.returnType = returnType;
	}

	private MethodWrapper<T> check() {
		return this;
	}

	public static Class<?> getClass(String string) {
		try {
			return Class.forName(string);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public T invoke(Object... param) {
		return new UsingMethodWrapper<T>(null).invoke(param);
	}

	public UsingMethodWrapper<T> using(Object object) {
		return new UsingMethodWrapper<T>(object);
	}

	public class UsingMethodWrapper<X> {
		private final Object object;

		private UsingMethodWrapper(Object object) {
			this.object = object;
		}

		@SuppressWarnings("unchecked")
		public X invoke(Object... param) {
			try {
				if (returnType == null) {
					method.invoke(object, param);
					return null;
				}
				return (X) method.invoke(object, param);
			} catch (Exception e) {
				log.error(e.getMessage(), new RuntimeException("The method has just exploded in your face"));
				return null;
			}
		}

	}

	public static interface MethodWrapperBuilderPhase1 {
		MethodWrapperBuilderPhase2 method(String methodName);
	}

	public static interface MethodWrapperBuilderPhase2 {
		MethodWrapperBuilderPhase3 withParameters(Class<?>... parameters);
	}

	public static interface MethodWrapperBuilderPhase3 {
		<T> MethodWrapper<T> andReturnType(Class<T> returnType);
	}

	private static class MethodWrapperBuilder implements MethodWrapperBuilderPhase1, MethodWrapperBuilderPhase2,
			MethodWrapperBuilderPhase3 {
		private final Class<?> clazz;
		private String methodName;
		private Class<?>[] parameters;

		private MethodWrapperBuilder(Class<?> clazz) {
			this.clazz = clazz;
		}

		private MethodWrapperBuilder(String className) {
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(className + " does not exist");
			}
		}

		public MethodWrapperBuilder method(String methodName) {
			this.methodName = methodName;
			return this;
		}

		public MethodWrapperBuilder withParameters(Class<?>... parameters) {
			this.parameters = parameters;
			return this;
		}

		public <T> MethodWrapper<T> andReturnType(Class<T> returnType) {
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodName, parameters);
				if (method.getReturnType().equals(returnType)
						|| (returnType == null && method.getReturnType().equals(void.class))) {
					return new MethodWrapper<T>(method, returnType).check();
				}
			} catch (SecurityException e) {
				log.error(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				log.error(e.getMessage(), e);
			}
			throw new RuntimeException("Method " + methodName + " does not exist");
		}

	}
}
