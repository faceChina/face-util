package com.zjlp.face.util.exception.enums;


public enum CommonEnum implements ExceptionObject {
	
	COMMON_01(null),
	
	COMMON_02(null, null)
	;
	
	private String logInfo;
	
	private String view;
	
	private CommonEnum(String logInfo){
		this.logInfo = logInfo;
	}
	
	private CommonEnum(String logInfo, String view){
		this.logInfo = logInfo;
		this.view = view;
	}
	@Override
	public String getLogInfo() {
		return logInfo;
	}
	@Override
	public String getErrCode() {
		return this.name();
	}
	@Override
	public String getViewInfo() {
		return view;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	public void setViewInfo(String view) {
		this.view = view;
	}
	
}
