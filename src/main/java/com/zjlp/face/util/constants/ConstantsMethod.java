package com.zjlp.face.util.constants;

public final class ConstantsMethod {

	private ConstantsMethod() {
		// no-implements
	}

	/**
	 * 如果为空值，则返回0
	 * 
	 * @Title: nvlLong
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param l
	 *            long类型实例
	 * @return
	 * @date 2015年3月16日 下午4:27:02
	 * @author lys
	 */
	public static Long nvlLong(Long l) {
		return null == l ? 0L : l;
	}

	/**
	 * 如果为空值，则返回0
	 * 
	 * @Title: nvlInteger
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param i
	 *            integer类型实例
	 * @return
	 * @date 2015年3月16日 下午4:27:21
	 * @author lys
	 */
	public static Integer nvlInteger(Integer i) {
		return null == i ? 0 : i;
	}

	/**
	 * 重要信息字段半隐藏
	 * 
	 * <p>
	 * 
	 * 例：<br>
	 * 
	 * replaceToHide("18655015835", 3, 3, "*") -> 186*****835
	 * 
	 * @Title: replaceToHide
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param inputString
	 *            原信息（不可为空）
	 * @param len1
	 *            左偏移
	 * @param len2
	 *            右偏移
	 * @param charString
	 *            替换字符
	 * @return
	 * @date 2015年3月16日 下午4:44:53
	 * @author lys
	 */
	public static String replaceToHide(String inputString, int len1, int len2,
			String charString) {
		if (inputString.length() <= len1 + len2) {
			return inputString;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(inputString.substring(0, len1));
		for (int i = 0; i < len1 + len2 - 1; i++) {
			sb.append(charString);
		}
		sb.append(inputString.substring(inputString.length() - len2,
				inputString.length()));
		return sb.toString();
	}
	
	/**
	 * 重要信息字段半隐藏
	 * 
	 * <p>
	 * 
	 * 注：<br>
	 * 替换字符为*<br>
	 * 例：<br>
	 * replaceToHide("18655015835", 3, 3, "*") -> 186*****835
	 * 
	 * @Title: replaceToHide
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param inputString
	 *            原信息（不可为空）
	 * @param len1
	 *            左偏移
	 * @param len2
	 *            右偏移
	 * @return
	 * @date 2015年3月16日 下午4:44:53
	 * @author lys
	 */
	public static String replaceToHide(String inputString, int len1, int len2) {
		return replaceToHide(inputString, len1, len2, "*");
	}

	public static void main(String[] args) {
		System.out.println(replaceToHide("18655015835", 3, 3, "*"));
	}

}
