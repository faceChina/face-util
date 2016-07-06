package com.zjlp.face.util.compare;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

/**
 * 比较器工具类
 * 
 * 1.目前支持的字段类型为实现comparable
 * 
 * @ClassName: TComparator
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015�?�?0�?下午4:33:58
 * @param <T>
 */
public class TComparator<T> implements Comparator<T> {
	
	private Logger log = Logger.getLogger(getClass());

	public static final Integer FILTER = 1;

	public static final Integer NOT_FILTER = 2;

	private List<String> fields = null;
	private Class<?> clazz = null;

	private TComparator(Class<?> clazz, List<String> fields) {
		this.clazz = clazz;
		this.fields = fields;
		this.fields.remove("serialVersionUID");
		log.info(fields.toString());
		System.out.println(fields.toString());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(Object o1, Object o2) {
		Assert.isTrue(o1.getClass().equals(clazz), "error o1's class.");
		Assert.isTrue(o2.getClass().equals(clazz), "error o2's class.");
		try {
			Field[] list = clazz.getDeclaredFields();
			for (Field field : list) {
				if (!fields.contains(field.getName())) {
					continue;
				}
				Object v1 = getter(o1, field);
				Object v2 = getter(o2, field);
				if (canCompara(field)) {
					if (null == v1 && null == v2) {
						continue;
					} else if (null == v1) {
						return -1;
					} else if (null == v2) {
						return 1;
					}
					int rs = ((Comparable) v1).compareTo((Comparable) v2);
					if (0 != rs) {
						return rs;
					}
					continue;
				} else {
					throw new RuntimeException("Filed '" + field.getName()
							+ "' is not a compara type!!!");
				}
			}
			return 0;
		} catch (Exception e) {
			throw new RuntimeException("error!!!", e);
		}
	}

	private boolean canCompara(Field field) {
		Class<?>[] classArr = field.getType().getInterfaces();
		if (null == classArr || classArr.length == 0) {
			return false;
		}
		for (Class<?> clazz : classArr) {
			if (clazz.equals(Comparable.class)) {
				return true;
			}
		}
		return false;
	}

	public static <T> Comparator<T> getComparator(Class<T> clazz,
			Integer filterType, String...filterFileds) {
		List<String> fields = getFieldNames(clazz);
		// 全部对比
		if (null == filterType) {
			return new TComparator<T>(clazz, fields);
		}
		// 判断输入
		Assert.isTrue(FILTER.equals(filterType) || NOT_FILTER.equals(filterType),
				"error filterType!!!");
		boolean isfilter = FILTER.equals(filterType);
		Assert.notEmpty(filterFileds, "filterFileds can not be empty!!!");
		List<String> filterFiledNames = Arrays.asList(filterFileds);
		Assert.isTrue(fields.containsAll(filterFiledNames), "filterFileds can not be empty!!!");
		// 构建
		if (isfilter) {
			fields.removeAll(Arrays.asList(filterFileds));
			return new TComparator<T>(clazz, fields);
		} else {
			return new TComparator<T>(clazz, filterFiledNames);
		}
	}

	public static <T> Comparator<T> getComparator(Class<T> clazz) {
		String[] params = null;
		return getComparator(clazz, null, params);
	}

	private static <T> List<String> getFieldNames(Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<String> result = new ArrayList<String>();
		for (Field field : fields) {
			result.add(field.getName());
		}
		return result;
	}
	
	private static Object getter(Object obj,Field field) {
		   Method method;
		try {
			method = obj.getClass().getMethod(_getMethodName(field.getName()));
		    return method.invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException("error!!!", e);
		}
	}
	
	 private static String _getMethodName(String fildeName) throws Exception{  
	     byte[] items = fildeName.getBytes();
	     if (_isLowchar(items[0])) {
	    	 items[0] = (byte) ((char) items[0] - 'a' + 'A'); 
	     }
	     return "get" + new String(items);  
	 } 
	 
	 private static boolean _isLowchar(byte b) {
		 return (b >= 97 && b <= 122);
	 }

}
