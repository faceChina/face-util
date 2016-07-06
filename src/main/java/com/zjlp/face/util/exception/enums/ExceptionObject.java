package com.zjlp.face.util.exception.enums;

public interface ExceptionObject {

	String getLogInfo();
	
	String getErrCode();
	
	String getViewInfo();
	
	void setLogInfo(String logInfo);
	
	void setViewInfo(String viewInfo);
}
