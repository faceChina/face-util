package com.zjlp.face.util.file.img;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class ImageUtils {
	
	/**默认图片路径*/
	public static final String INIT_IMG_PATH = "/resource/base/img/";
	
	
	/**
	 * 上传图片
	 * @Title: uploadFile 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param shopNo
	 * @param inputName
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FilesException
	 * @date 2014年8月5日 上午11:48:22  
	 * @author Administrator
	 */
	
	public static String getTFSZoomPath(String fileNo,String zoom){
		if(StringUtils.isNotBlank(fileNo) && fileNo.indexOf(INIT_IMG_PATH) == -1){
			//图片服务器获取
			StringBuffer sb = new StringBuffer("_q").append(zoom);
			if(StringUtils.isNotBlank(zoom) && StringUtils.isNotBlank(fileNo) && fileNo.indexOf(sb.toString()) ==-1){
				fileNo = new StringBuffer(fileNo.substring(0, fileNo.indexOf(".")))
							.append("_q").append(zoom)
							.append(fileNo.substring(fileNo.indexOf("."), fileNo.length())).toString();
			}
			return fileNo;
		} else {
			//系统默认图片
			return fileNo;
		}
	}

}
