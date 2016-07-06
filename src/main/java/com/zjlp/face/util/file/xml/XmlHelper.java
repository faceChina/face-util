package com.zjlp.face.util.file.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XML工具类
 * @ClassName: XmlHelper 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2014年9月18日 上午9:42:41
 */
public class XmlHelper {
	
	public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	public static final String HTM_ANGLE_LEFT_BEGIN = "<";
	
	public static final String HTM_ANGLE_RIGHT_BEGIN = "</";
	
	public static final String HTM_ANGLE_END = ">";
	
	public static final String POINT = ".";
	
	private static Map<Class<?>,Unmarshaller> uMap = new HashMap<Class<?>,Unmarshaller>();
	
	private static Map<Class<?>,Marshaller> mMap = new HashMap<Class<?>,Marshaller>();
	
	/**
	 * 将对象转换成xml格式的字符串
	 * @param obj
	 * @return
	 */
	public static String objectToXML(Object obj) {
		String clzzName = obj.getClass().getName();
		String rootName = clzzName.substring(clzzName.lastIndexOf(POINT) + 1, clzzName.length());
		XStream xstream = new XStream(new DomDriver());
		xstream.alias(rootName, obj.getClass());
		return xstream.toXML(obj);
	}
	
	
	/**
	 * 获取xml字符串中的标签内容
	 * @Title: getTagContent 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param xmlString 所要解析的xml文本
	 * @param tagName 标签名
	 * @return
	 * @date 2014年9月1日 下午1:45:06  
	 * @author Administrator
	 */
	public static String getTagContent(String xmlString, String tagName) {
        return getTagContent(xmlString, tagName, 1);
    }
	
	/**
	 * 获取xml字符串中的标签内容
	 * @Title: getTagContent 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param xmlString 所要解析的xml文本
	 * @param tagName 标签名
	 * @param index 排序位
	 * @return
	 * @date 2014年9月18日 上午9:07:45  
	 * @author Administrator
	 */
	public static String getTagContent(String xmlString, String tagName, int index) {
		StringBuilder sb = new StringBuilder();
		String beginTag = sb.append(HTM_ANGLE_LEFT_BEGIN).append(tagName).append(HTM_ANGLE_END).toString();
		int startIdx = 0;
		int len = -1;
		for (int i = 1; i <= index; i++) {
			if (-1 == (len = xmlString.indexOf(beginTag, startIdx))) {
				return null;
			}
			startIdx = len + beginTag.length();
		}
		sb.delete(0, sb.length());
        String endTag = sb.append(HTM_ANGLE_RIGHT_BEGIN).append(tagName).append(HTM_ANGLE_END).toString();
        int endIdx = xmlString.indexOf(endTag, startIdx);
        if (endIdx <= startIdx) return null;
        return xmlString.substring(startIdx, endIdx);
	}
	
	/**
	 * 将xml字符串中的值提取到对象中
	 * @Title: parseXml 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param xmlString
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @date 2014年9月5日 上午11:25:33  
	 * @author Administrator
	 */
	public static <T> T parseXml(String xmlString ,Class<? extends T> clazz) throws Exception{
		 try {
			 Field[] fields = clazz.getDeclaredFields();
			 T obj = clazz.newInstance();
			 String fieldText;
			 for (Field field : fields) {
				   if (null == (fieldText = getTagContent(xmlString, field.getName()))) continue; 
                  _setter(obj, field, fieldText);
			 }
			 if (null == obj) {
				   throw new RuntimeException("对象转换失败");
			 }
			return obj;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 将xml字符串中的值提取到对象中
	 * @Title: parseXmlCdata 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param xmlString
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @date 2014年9月5日 上午11:25:33  
	 * @author Administrator
	 */
	public static <T> T parseXmlCdata(String xmlString ,Class<? extends T> clazz) throws Exception{
		 try {
			 Field[] fields = clazz.getDeclaredFields();
			 T obj = clazz.newInstance();
			 String fieldText;
			 for (Field field : fields) {
				   if (null == (fieldText = getTagContent(xmlString, field.getName()))) continue; 
				   if(fieldText.indexOf("<![CDATA[") != -1) {
					   fieldText = fieldText.replace("<![CDATA[", "").replace("]]>", "");
				   }
                 _setter(obj, field, fieldText);
			 }
			 if (null == obj) {
				   throw new RuntimeException("对象转换失败");
			 }
			return obj;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 解析xml文本中的多行数据
	 * @Title: parseListXml 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param xmlString xml文本
	 * @param clazz 行数据类名
	 * @return
	 * @throws Exception
	 * @date 2014年9月18日 上午10:14:28  
	 * @author Administrator
	 */
	public static <T> List<T> parseListXml(String xmlString, Class<? extends T> clazz) throws Exception {
		return parseListXml(xmlString, clazz, "list", "row");
	}
	
	
	/**
	 * 解析xml文本中的多行数据
	 * @Title: parseListXml 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param xmlString xml文本
	 * @param clazz 行数据类名
	 * @param listTagName list标签名
	 * @param rowTagName 行数据标签名
	 * @return
	 * @throws Exception
	 * @date 2014年9月18日 上午10:12:03  
	 * @author Administrator
	 */
	public static <T> List<T> parseListXml(String xmlString, Class<? extends T> clazz, String listTagName, String rowTagName)
			throws Exception {
		try {
			String listXml = getTagContent(xmlString, listTagName);
			if (StringUtils.isBlank(listXml)) return null;
			List<T> list = new ArrayList<T>();
			String rowXml = null;
			while (StringUtils.isNotBlank((rowXml = getTagContent(listXml, rowTagName, list.size() + 1)))) {
				T obj = parseXml(rowXml, clazz);
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 根据严格的bean名设置属性值，如果没有对应的字段->忽略
	 * @Title: _setter 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param obj
	 * @param field
	 * @param value
	 * @throws Exception
	 * @date 2014年9月16日 上午9:45:28  
	 * @author Administrator
	 */
	private static void _setter(Object obj,Field field, Object value) throws Exception {
		   Method method;
		try {
			method = obj.getClass().getMethod(_getMethodName(field.getName()),field.getType());
			if (field.getGenericType().toString().equals(  
	               "class java.lang.Integer")) {
			   value = Integer.valueOf((String) value);
		    }
		    method.invoke(obj, value);
		} catch (NoSuchMethodException e) {
			//Ignore
		}
	}
	
	/**
	 * 获取对应字段的set方法
	 * @Title: _getMethodName 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param fildeName
	 * @return
	 * @throws Exception
	 * @date 2014年9月16日 上午11:45:26  
	 * @author Administrator
	 */
	 private static String _getMethodName(String fildeName) throws Exception{  
	     byte[] items = fildeName.getBytes();  
	     //如果第一个字符是大写则不作变换，否则第一个字母变为大写
	     if (_isLowchar(items[0])) {
	    	 items[0] = (byte) ((char) items[0] - 'a' + 'A'); 
	     }
	     return "set" + new String(items);  
	 } 
	 
	 /**
	  * 判断字符是否为小写
	  * @Title: isLowchar 
	  * @Description: (这里用一句话描述这个方法的作用) 
	  * @param b
	  * @return
	  * @date 2014年9月16日 上午11:11:10  
	  * @author Administrator
	  */
	 private static boolean _isLowchar(byte b) {
		 return (b >= 97 && b <= 122);
	 }
	 
	 
	/**
	 * 特殊字符的转换
	 * 
	 * @Title: replacelineChar
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param reqStr
	 * @return
	 * @date 2014年9月26日 下午2:10:12
	 * @author Administrator
	 */
	public static final String replacelineChar(String reqStr) {
		return reqStr.replace("\n", "").replace("\t", "").replace(" ", "");
	}
	 
	/**
	 * 将对象转换成xml格式的字符串
	 * @param obj
	 * @return
	 */
	public static String objectToXMLCDATA(Object obj) {
		xstreamCdata.alias("xml", obj.getClass());
		return xstreamCdata.toXML(obj);
	}
	
	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("rawtypes")
	public static <T> String newsMessageToXml(Object newsMessage, Class<T> article) throws InstantiationException, IllegalAccessException {
		xstreamCdata.alias("xml", (Class) newsMessage);
		xstreamCdata.alias("item", (Class) article.newInstance());
		return xstreamCdata.toXML(newsMessage);
	}
	
	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 */
	private static XStream xstreamCdata = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			PrettyPrintWriter out2 = new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings({ "rawtypes" })
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
			return out2;
		}
	});
	
	/**
	 * XML to Object
	 * @param <T>
	 * @param clazz
	 * @param xml
	 * @return
	 */
	public static <T> T convertToObject(Class<T> clazz,String xml){
		return convertToObject(clazz,new StringReader(xml));
	}

	/**
	 * XML to Object
	 * @param <T>
	 * @param clazz
	 * @param inputStream
	 * @return
	 */
	public static <T> T convertToObject(Class<T> clazz,InputStream inputStream){
		return convertToObject(clazz,new InputStreamReader(inputStream));
	}

	/**
	 * XML to Object
	 * @param <T>
	 * @param clazz
	 * @param reader
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToObject(Class<T> clazz,Reader reader){
		try {
			if(!uMap.containsKey(clazz)){
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				uMap.put(clazz,unmarshaller);
			}
			return (T)uMap.get(clazz).unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object to XML
	 * @param object
	 * @return
	 */
	public static String convertToXML(Object object){
		try {
			if(!mMap.containsKey(object.getClass())){
				JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
	                public void escape(char[] ac, int i, int j, boolean flag,Writer writer) throws IOException {
	                writer.write( ac, i, j ); }
	            });
				mMap.put(object.getClass(), marshaller);
			}
			StringWriter stringWriter = new StringWriter();
			mMap.get(object.getClass()).marshal(object,stringWriter);
			return stringWriter.getBuffer().toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
