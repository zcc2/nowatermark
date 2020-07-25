package com.pbrx.mylib.reflection;


import java.util.HashMap;

public class RefWrapper {
	private final static HashMap<Class<?>, Class<?>> UNWRAPPER = new HashMap<Class<?>, Class<?>>();
	private final static HashMap<Class<?>, Class<?>> WRAPPER = new HashMap<Class<?>, Class<?>>();

	static {
		UNWRAPPER.put(Boolean.class, boolean.class);
		UNWRAPPER.put(Byte.class, byte.class);
		UNWRAPPER.put(Character.class, char.class);
		UNWRAPPER.put(Short.class, short.class);
		UNWRAPPER.put(Integer.class, int.class);
		UNWRAPPER.put(Long.class, long.class);
		UNWRAPPER.put(Float.class, float.class);
		UNWRAPPER.put(Double.class, double.class);
		UNWRAPPER.put(Void.class, void.class);

		WRAPPER.put(boolean.class, Boolean.class);
		WRAPPER.put(byte.class, Byte.class);
		WRAPPER.put(char.class, Character.class);
		WRAPPER.put(short.class, Short.class);
		WRAPPER.put(int.class, Integer.class);
		WRAPPER.put(long.class, Long.class);
		WRAPPER.put(float.class, Float.class);
		WRAPPER.put(double.class, Double.class);
		WRAPPER.put(void.class, Void.class);
	}

	public static Class<?> wrapClass(Class<?> clazz) {
		final Class<?> clazzWraped = WRAPPER.get(clazz);
		return clazzWraped == null ? clazz : clazzWraped;
	}

	public static Class<?> unwrapClass(Class<?> clazz) {
		final Class<?> clazzUnwraped = UNWRAPPER.get(clazz);
		return clazzUnwraped == null ? clazz : clazzUnwraped;
	}
	
	public static boolean isPrimitive(Object obj) {
		if(obj != null) {
			return isPrimitiveClass(obj.getClass());
		} else {
			return false;
		}
	}
	
	public static boolean isPrimitiveClass(Class<?> clazz) {
		return UNWRAPPER.containsKey(clazz);
	}
}
