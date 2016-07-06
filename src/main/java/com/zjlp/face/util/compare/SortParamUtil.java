package com.zjlp.face.util.compare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SortParamUtil {

	/**
	 * 签名排序[按字典顺序]
	 * @Title: sortParamForSign 
	 * @Description: (这里用一句话描述这个方法的作�? 
	 * @param params 参数集合
	 * @return
	 * @throws FilesException
	 * @date 2014�?�?�?下午5:26:51  
	 * @author dzq
	 */
	public static String sortParamForSign(Map<String, String> params) {
		// 根据参数名字典排�?
		List<String> keyList = Arrays.asList(params.keySet().toArray(new String[params.size()]));
		Collections.sort(keyList);
		// 拼接签名参数
		StringBuilder sb = new StringBuilder();
		for (String k : keyList) {
			if ("sign".equals(k))
				continue;
			sb.append(k).append("=").append(params.get(k)).append("&");
		}
		if (params.size() > 0)
			sb.delete(sb.length() - "&".length(), sb.length());
		
		return sb.toString();
	}
	
	/**
	 * 签名排序[按参数顺序]
	 * @Title: sortSign 
	 * @Description: (这里用一句话描述这个方法的作�? 
	 * @param params
	 * @return
	 * @throws PaymentException
	 * @date 2014�?�?2�?下午4:02:14  
	 * @author Administrator
	 */
	public static String sortSign(Map<String, String> params){
		// 根据参数名字典排�?
		List<String> keyList = Arrays.asList(params.keySet().toArray(new String[params.size()]));
		// 拼接签名参数
		StringBuilder sb = new StringBuilder();
		for (String k : keyList) {
			if ("sign".equals(sb))
				continue;
			sb.append(k).append("=").append(params.get(k)).append("&");
		}
		if (params.size() > 0)
			sb.delete(sb.length() - "&".length(), sb.length());
		return sb.toString();
	}
}
