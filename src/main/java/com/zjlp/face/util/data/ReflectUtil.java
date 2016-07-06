package com.zjlp.face.util.data;

import java.lang.reflect.Method;

public class ReflectUtil {

	private static final String serialVersionUID = "serialVersionUID";

	public static void setValue(Object obj, String fieldName, Object value) {
		try {
			if (serialVersionUID.equals(fieldName) || null == value) {
				return;
			}
			Method method = obj.getClass().getMethod(getSet("set", fieldName),
					value.getClass());
			method.invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException("error!!!", e);
		}
	}

	public static void setValue(Object obj, String fieldName, Object value,
			Class<?> methodClass) {
		try {
			if (serialVersionUID.equals(fieldName) || null == value) {
				return;
			}
			Method method = methodClass.getMethod(getSet("set", fieldName),
					value.getClass());
			method.invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException("error!!!", e);
		}
	}

	public static Object get(Object obj, String fieldName) {
		try {
			if (serialVersionUID.equals(fieldName)) {
				return null;
			}
			Method method = obj.getClass().getMethod(getSet("get", fieldName));
			return method.invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException("error!!!", e);
		}
	}

	public static Object get(Object obj, String fieldName, Class<?> methodClass) {
		try {
			if (serialVersionUID.equals(fieldName)) {
				return null;
			}
			Method method = methodClass.getMethod(getSet("get", fieldName));
			return method.invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException("error!!!", e);
		}
	}

	private static String getSet(String pre, String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		if (_isLowchar(items[0])) {
			items[0] = (byte) ((char) items[0] - 'a' + 'A');
		}
		return pre + new String(items);
	}

	private static boolean _isLowchar(byte b) {
		return (b >= 97 && b <= 122);
	}
}
