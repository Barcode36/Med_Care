package com.Over2Cloud.ctrl.LongPatientStay;
import java.util.List;
public class LongPatientPojo {
	 
	private String id;
	 
	private String actionDate;
	private String actionTime; 
	private String status; 
	private String level; 
	private String comment; 
	private String action_by, close_by;
  	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getLevel()
	{
		return level;
	}
	public void setLevel(String level)
	{
		this.level = level;
	}
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public String getAction_by()
	{
		return action_by;
	}
	public void setAction_by(String actionBy)
	{
		action_by = actionBy;
	}
	public String getClose_by()
	{
		return close_by;
	}
	public void setClose_by(String closeBy)
	{
		close_by = closeBy;
	}
	 
 
}