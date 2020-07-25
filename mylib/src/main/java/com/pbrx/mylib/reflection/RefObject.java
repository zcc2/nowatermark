package com.pbrx.mylib.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zju_wjf
 * 
 * @RefObject 对Object的包装加壳
 * @(1)通过 get-set对任意属性读写.
 * @(2)通过call调用任意方法.
 * @(3)通过unwrap去壳.
 * @(4)通过getParent获得上一级RefObject
 */
public final class RefObject {

	private final RefObject parent;
	private final Field field;
	private Object target;

	private RefObject(Object target) throws RefException {
		checkArgs(target, null, null, true);
		this.target = target;
		this.parent = null;
		this.field = null;
	}

	private RefObject(RefObject parent, Field field) throws RefException {
		checkArgs(null, parent, field, false);
		this.target = null;
		this.parent = parent;
		this.field = field;
	}

	private void checkArgs(Object target, RefObject parent, Field field, boolean isPublic) throws RefException {
		if (isPublic) {
			if (target instanceof RefObject) {
				throw new RefException("target must not be a instance of RefObject!");
			}
		} else {
			if (field == null) {
				throw new RefException("field must not be a null!");
			}

			if (parent == null) {
				throw new RefException("parent must not be a null!");
			}

			if (field.getDeclaringClass() == RefObject.class) {
				throw new RefException("target must not be a instance of RefObject!");
			}
		}
	}

	public RefObject getParent() {
		return this.parent;
	}

	public Class<?> getType() throws RefException {
		if (field != null) {
			return field.getType();
			// return field.getDeclaringClass();
		}
		final Object obj = this.unwrap();
		if (obj == null) {
			return NULL.class;
		} else {
			return obj.getClass();
		}
	}

	public String getName() {
		if (this.field != null) {
			return this.field.getName();
		} else {
			return null;
		}
	}

	public boolean isClass() throws RefException {
		final Class<?> clazz = getType();
		return clazz == Class.class;
	}

	public boolean isPrimitive() throws RefException {
		return RefWrapper.isPrimitive(unwrap());
	}

	public boolean isArray() throws RefException {
		return getType().isArray();
	}

	public Object unwrap() throws RefException {
		try {
			return target != null ? target : field.get(parent.unwrap());
		} catch (IllegalAccessException e) {
			throw new RefException("", e);
		} catch (IllegalArgumentException e) {
			throw new RefException("", e);
		}
	}

	public boolean isNull() throws RefException {
		return this.unwrap() == null;
	}

	public RefObject get(String name) throws RefException {
		if (isTextEmpty(name)) {
			throw new RefException("");
		} else if (this.isClass()) {
			throw new RefException("");
		} else if (this.isNull()) {
			throw new RefException("");
		} else {
			final Field fd = getField(getType(), name);
			return new RefObject(this, fd);
		}
	}
	
	public boolean contains(String name) throws RefException {
		final Field fd = getField(getType(), name);
		return fd != null;
	}

	private static boolean isTextEmpty(String name) {
        return name == null || name.length() == 0;
	}

	private static Field getField(Class<?> clazz, String name) {
		while (clazz != null) {

			try {
				return clazz.getField(name);
			} catch (NoSuchFieldException e) {
			}

			try {
				final Field fd = clazz.getDeclaredField(name);
				return accessible(fd);
			} catch (NoSuchFieldException e) {
			}

			clazz = clazz.getSuperclass();
		}
		return null;
	}

	private static <T extends AccessibleObject> T accessible(T accessible) {
		if (accessible == null) {
			return null;
		}
		if (accessible instanceof Member) {
			Member member = (Member) accessible;
			if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
				return accessible;
			}
		}
		if (!accessible.isAccessible()) {
			accessible.setAccessible(true);
		}
		return accessible;
	}

	private static List<Field> getFieldList(Class<?> clazz) {
		final List<Field> fdList = new LinkedList<Field>();
		while (clazz != null) {

			final Field[] fds = clazz.getFields();
			if (fds != null) {
				for (Field fd : fds) {
					fdList.add(fd);
				}
			}

			final Field[] dfds = clazz.getDeclaredFields();
			if (dfds != null) {
				for (Field fd : dfds) {
					fdList.add(accessible(fd));
				}
			}

			clazz = clazz.getSuperclass();
		}

		return fdList;
	}

	public List<RefObject> getAll() throws RefException {
		final List<RefObject> list = new LinkedList<RefObject>();

		final List<Field> fdList = getFieldList(getType());
		for (Field fd : fdList) {
			list.add(new RefObject(this, fd));
		}
		
		return list;
	}

	public RefObject set(Object value) throws RefException {
		value = unwrap(value);

		if (parent == null) {
			target = value;
		} else {
			try {
				field.set(parent.unwrap(), value);
			} catch (IllegalAccessException e) {
				throw new RefException("", e);
			} catch (IllegalArgumentException e) {
				throw new RefException("", e);
			}
		}
		return this;
	}

	public RefObject set(String name, Object value) throws RefException {
		final RefObject refObj = this.get(name);
		refObj.set(value);
		return this;
	}

	private static Class<?>[] types(Object... values) {
		if (values == null) {
			return new Class[0];
		}

		Class<?>[] result = new Class[values.length];

		for (int i = 0; i < values.length; i++) {
			Object value = values[i];
			result[i] = value == null ? NULL.class : value.getClass();
		}

		return result;
	}

	/**
	 * @param name
	 * @param args
	 * @return
	 * @throws RefException
	 */
	public RefObject call(String name, Object... args) throws RefException {
		final Class<?>[] types = types(args);
		final Object target = this.unwrap();

		try {
			final Method method = exactMethod(name, types);
			accessible(method);
			return new RefObject(method.invoke(target, args));
		} catch (RefException e) {
			try {
				final Method method = similarMethod(name, types);
				accessible(method);
				return new RefObject(method.invoke(target, args));
			} catch (IllegalAccessException e1) {
				throw new RefException(e1);
			} catch (IllegalArgumentException e1) {
				throw new RefException(e1);
			} catch (InvocationTargetException e1) {
				throw new RefException(e1);
			}
		} catch (IllegalAccessException e) {
			throw new RefException(e);
		} catch (IllegalArgumentException e) {
			throw new RefException(e);
		} catch (InvocationTargetException e) {
			throw new RefException(e);
		}
	}

	/**
	 * @param name
	 * @param types
	 * @return
	 * @throws RefException
	 */
	private Method exactMethod(String name, Class<?>[] types) throws RefException {
		Class<?> type = getType();

		try {
			return type.getMethod(name, types);
		} catch (NoSuchMethodException e) {
			do {
				try {
					return type.getDeclaredMethod(name, types);
				} catch (NoSuchMethodException ignore) {
				}

				type = type.getSuperclass();
			} while (type != null);

			throw new RefException("No exact method " + name + " with params " + Arrays.toString(types) + " could be found on type " + getType() + ".");
		}
	}

	/**
	 * @param name
	 * @param types
	 * @return
	 * @throws RefException
	 */
	private Method similarMethod(String name, Class<?>[] types) throws RefException {
		Class<?> type = getType();

		for (Method method : type.getMethods()) {
			if (isSimilarSignature(method, name, types)) {
				return method;
			}
		}

		do {
			for (Method method : type.getDeclaredMethods()) {
				if (isSimilarSignature(method, name, types)) {
					return method;
				}
			}
			type = type.getSuperclass();
		} while (type != null);

		throw new RefException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + getType() + ".");
	}

	private static boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
		return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
	}

	private static final boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
		if (declaredTypes.length == actualTypes.length) {
			for (int i = 0; i < actualTypes.length; i++) {
				if (actualTypes[i] == NULL.class)
					continue;

				if (RefWrapper.wrapClass(declaredTypes[i]).isAssignableFrom(RefWrapper.wrapClass(actualTypes[i])))
					continue;

				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof RefObject) {
			final RefObject refO1 = (RefObject) obj;
			final RefObject refO2 = this;

			if (refO1.target == refO2.target && refO1.parent == refO2.parent && refO1.field == refO2.field) {
				return true;
			}
		}

		return false;
	}

	public int hashCode() {
		final int hash0 = System.identityHashCode(this.target);
		final int hash1 = System.identityHashCode(this.parent);
		final int hash2 = System.identityHashCode(this.field);
		return hash0 ^ hash1 ^ hash2;
	}

	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("target:").append(target).append(",");

		if (parent == null) {
			sb.append("parent:null,");
		} else {
			sb.append("parent:");
			try {
				sb.append(unwrap(parent));
			} catch (RefException e) {
				sb.append(e.getMessage());
			}
			sb.append(",");
		}

		if (field == null) {
			sb.append("field:null");
		} else {
			sb.append("field:").append(field.getDeclaringClass().getName()).append("-").append(field.getName());
		}

		return sb.toString();
	}

	/***
	 * 
	 * static
	 */

	public static RefObject wrap(final Object obj) throws RefException {
		return new RefObject(obj);
	}

	public static Object unwrap(final Object obj) throws RefException {
		if (obj instanceof RefObject) {
			return ((RefObject) obj).unwrap();
		}
		return obj;
	}

	public static final class NULL {
	}
}
