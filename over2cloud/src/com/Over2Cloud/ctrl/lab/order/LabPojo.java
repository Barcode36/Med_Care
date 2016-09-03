package com.Over2Cloud.ctrl.lab.order;
import java.util.List;
public class LabPojo {
	 
	private String id;
	private String specimenNo;
	private String serviceName;
	private String testUnit;
	private String specCol;
	private String specReg;
	private String specDis;
	private String lowRange="0";
  	private String highRange="0";
	private String resullt="0";
	private String actionDate;
	private String actionTime; 
	private String status; 
	private String level;
	private String history_id;
	private String time_difference;
  	public String getLevel()
	{
		return level;
	}
	public String getHistory_id()
	{
		return history_id;
	}
	public void setHistory_id(String historyId)
	{
		history_id = historyId;
	}
	public void setLevel(String level)
	{
		this.level = level;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecimenNo()
	{
		return specimenNo;
	}
	public void setSpecimenNo(String specimenNo)
	{
		this.specimenNo = specimenNo;
	}
	public String getServiceName()
	{
		return serviceName;
	}
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}
	public String getTestUnit()
	{
		return testUnit;
	}
	public void setTestUnit(String testUnit)
	{
		this.testUnit = testUnit;
	}
	public String getLowRange()
	{
		return lowRange;
	}
	public void setLowRange(String lowRange)
	{
		this.lowRange = lowRange;
	}
	public String getHighRange()
	{
		return highRange;
	}
	public void setHighRange(String highRange)
	{
		this.highRange = highRange;
	}
	public String getResullt()
	{
		return resullt;
	}
	public void setResullt(String resullt)
	{
		this.resullt = resullt;
	}
	public String getActionDate()
	{
		return actionDate;
	}
	public void setActionDate(String actionDate)
	{
		this.actionDate = actionDate;
	}
	public String getActionTime()
	{
		return actionTime;
	}
	public void setActionTime(String actionTime)
	{
		this.actionTime = actionTime;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getSpecCol()
	{
		return specCol;
	}
	public void setSpecCol(String specCol)
	{
		this.specCol = specCol;
	}
	public String getSpecReg()
	{
		return specReg;
	}
	public void setSpecReg(String specReg)
	{
		this.specReg = specReg;
	}
	public String getSpecDis()
	{
		return specDis;
	}
	public void setSpecDis(String specDis)
	{
		this.specDis = specDis;
	}
	public void setTime_difference(String time_difference)
	{
		this.time_difference = time_difference;
	}
	public String getTime_difference()
	{
		return time_difference;
	}
	 
 
}