package com.zjlp.face.util.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * Json工具类
 * 
 * @ClassName: JsonUtil
 * @Description: (这里用一句话描述这个类的作用)
 * @author dzq
 * @date 2014年8月23日 下午4:58:19
 */
public class JsonUtil {

	/**
	 * 
	 * 将Json字符串转换成对象
	 * 
	 * @Title: toBean
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonString
	 * @param clazz
	 *            类型
	 * @return
	 * @throws Exception
	 * @date 2014年5月8日 下午3:55:18
	 * @author dzq
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static <T> T toBean(String jsonString, Class<T> clazz)
			throws RuntimeException {
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonString);
			if (null == jsonObject) {
				throw new RuntimeException("Json参数转换失败!转换后的对象为空");
			}
			return (T) jsonObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!", e);
		}
	}

	/**
	 * 
	 * 将Json字符串转换成对象
	 * 
	 * @Title: toBean
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonString
	 * @param clazz
	 *            类型
	 * @return
	 * @throws Exception
	 * @date 2014年5月8日 下午3:55:18
	 * @author dzq
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static <T> T toBean(String jsonString, Class<T> clazz,
			String[] ignoreFields) throws RuntimeException {
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(ignoreFields);
			JSONObject jsonObject = JSONObject.fromObject(jsonString,
					jsonConfig);
			if (null == jsonObject) {
				throw new RuntimeException("Json参数转换失败!转换后的对象为空");
			}
			return (T) jsonObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!", e);
		}
	}

	/**
	 * List<String>
	 * @Title: toArray 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @date 2014年7月31日 下午5:16:12  
	 * @author dzq
	 */
	@SuppressWarnings("unchecked")
	public static <T, E> T toArray(String jsonString,Class<E> clazz)throws Exception{
		List<E> list = new ArrayList<E>();
		try {
			JSONArray array = JSONArray.fromObject(jsonString);
			for (int i = 0; i < array.size(); i++) {
				list.add((E)array.get(i));
			}
			return (T) list;
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!",e);
		}
	}
	
	/**
	 * List<String>
	 * 
	 * @Title: toArray
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @date 2014年7月31日 下午5:16:12
	 * @author dzq
	 */
	public static <E> List<E> toArray(String jsonString) throws RuntimeException {
		try {
			JSONArray array = JSONArray.fromObject(jsonString);
			return coverListFromJsonArr(array);
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <E> List<E> coverListFromJsonArr(JSONArray array) {
		if (null == array || array.isEmpty()) return null;
		List<E> list = new ArrayList<E>();
		for (Object obj : array) {
			list.add((E) obj);
		}
		return list;
	}

	/**
	 * LiST<oBJ>
	 * 
	 * @Title: toArrayBean
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @date 2014年7月31日 下午5:16:25
	 * @author dzq
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static <T, E> T toArrayBean(String jsonString, Class<E> clazz)
			throws RuntimeException {
		List<E> list = new ArrayList<E>();
		try {
			JSONArray array = JSONArray.fromObject(jsonString);
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				if (null == jsonObject) {
					throw new RuntimeException("Json参数转换失败!转换后的对象为空");
				}
				E obj = (E) jsonObject.toBean(jsonObject, clazz);
				list.add(obj);
			}
			return (T) list;
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!", e);
		}
	}

	/**
	 * LiST<oBJ>
	 * 
	 * @Title: toArrayBean
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @date 2014年7月31日 下午5:16:25
	 * @author dzq
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static <T, E> T toArrayBean(String jsonString, Class<E> clazz,
			String[] ignoreFields) throws RuntimeException {
		List<E> list = new ArrayList<E>();
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(ignoreFields);
			JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				if (null == jsonObject) {
					throw new RuntimeException("Json参数转换失败!转换后的对象为空");
				}
				E obj = (E) jsonObject.toBean(jsonObject, clazz);
				list.add(obj);
			}
			return (T) list;
		} catch (Exception e) {
			throw new RuntimeException("Json参数转换时发生异常!", e);
		}
	}

	/**
	 * 集合json转换(可以进行属性过滤)
	 * @Title: fromCollection 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param object
	 * @param isFilter
	 * @param propertys
	 * @return
	 * @date 2015年3月5日 下午2:38:07  
	 * @author lys
	 */
	public static <E> String fromCollection(Collection<E> object,
			boolean isFilter, String[] propertys) {
		JsonConfig jsonConfig = getJsonConfig(isFilter, propertys);
		return JSONArray.fromObject(object, jsonConfig).toString();
	}
	
	/**
	 * 集合json转换(不可以进行属性过滤)
	 * @Title: fromCollection 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param collection
	 * @return
	 * @date 2015年3月5日 下午2:37:33  
	 * @author lys
	 */
	public static <E> String fromCollection(Collection<E> collection) {
		return JSONArray.fromObject(collection).toString();
	}

	/**
	 * Object 对象转换Json类型
	 * 
	 * @Title: fromObject
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param object
	 * @param flag
	 *            true 过滤 false or null 只选择
	 * @param isnot
	 * @return
	 * @date 2014年11月18日 下午3:52:32
	 * @author Administrator
	 */
	public static String fromObject(Object object, final boolean isFilter,
			String[] propertys) {
		JsonConfig jsonConfig = getJsonConfig(isFilter, propertys);
		return JSONSerializer.toJSON(object, jsonConfig).toString();
	}

	/**
	 * Map 对象转换Json类型(注意：未经过测试，不建议使用)
	 * 
	 * @Title: fromObject
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param map
	 * @param clazz
	 * @param isFilter
	 * @param propertys
	 * @return
	 * @date 2014年11月18日 下午4:26:03
	 * @author Administrator
	 */
	@Deprecated
	public static <E, K> String fromMap(Map<K, E> map, boolean isFilter,
			String[] propertys) {
		JsonConfig jsonConfig = getJsonConfig(isFilter, propertys);
		jsonConfig.setIgnoreDefaultExcludes(true);
		return JSONObject.fromObject(map).toString();
	}

	/**
	 * 配置项设置
	 * @Title: getJsonConfig 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param isFilter
	 * @param propertys
	 * @return
	 * @date 2015年3月5日 下午2:10:28  
	 * @author lys
	 */
	private static JsonConfig getJsonConfig(final boolean isFilter,
			String[] propertys) {
		final List<String> propertyList = new ArrayList<String>(
				Arrays.asList(propertys));
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			
			public boolean apply(Object source, String name, Object value) {
				// 返回 true, 表示这个属性将被过滤掉
				if (isFilter) {// 过滤 isFilter=true
					return propertyList.contains(name);
				} else {
					// 不过滤 isFilter=false
					return !propertyList.contains(name);
				}
			}
		});
		return jsonConfig;
	}
	
	@Override
	public String toString() {
		return "JsonUtil []";
	}


}
