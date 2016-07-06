package com.zjlp.face.util.page;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.zjlp.face.util.date.DateUtil;


/**
 * 辅助数据包
 * @ClassName: AideDto 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2014年10月10日 上午9:24:09
 */
public class Aide implements Serializable{
	
	private static final long serialVersionUID = -3206546891265867493L;
	//开始页
	private Integer startNum;
	//页行数
	private Integer pageSizeNum;
	// 时间
	private Integer timesType;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	
	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	public Integer getPageSizeNum() {
		return pageSizeNum;
	}
	public void setPageSizeNum(Integer pageSizeNum) {
		this.pageSizeNum = pageSizeNum;
	}
	public Integer getTimesType() {
		return null != timesType ? timesType : -1;
	}
	public void setTimesType(Integer timesType) {
		this.timesType = timesType;
		this.calculationTime();
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public void calculationTime() {
		Calendar c = Calendar.getInstance(); // 得到当前日期和时间
		Date currentDate = c.getTime();
		this.endTime = currentDate;
		currentDate = DateUtil.getZeroPoint(currentDate);
		if (null == timesType) {
			timesType = 0;
		}
		switch (this.timesType) {
		case 1:
			this.startTime = DateUtil.getZeroPoint(currentDate);
			break;
		case 2:
			this.startTime = DateUtil.getLastTimeForDays(currentDate, 1);
			this.endTime = DateUtil.getZeroPoint(currentDate);
			break;
		case 7:
			this.startTime = DateUtil.getLastTimeForDays(currentDate, 7);
			break;
		case 30:
			this.startTime = DateUtil.getLastTimeForMonths(currentDate, 1);
			break;
		case 90:
			this.startTime = DateUtil.getLastTimeForMonths(currentDate, 3);
			this.endTime = currentDate;
			break;
		case 365:
			this.startTime = DateUtil.getLastTimeForYears(currentDate, 1);
			break;
		default:
			return;
		}

	}

}
