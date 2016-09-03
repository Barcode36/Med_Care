package com.Over2Cloud.helpdesk.BeanUtil;

public class FeedbackDraftPojo {
	
	private int id;
	private String feedtype;
	private String subdept;
	private String category;
	private String feedtype_id;
	private String sub_category;
	private String feed_msg;
	private String addressing_time;
	private String resolution_time;
	private String escalation_time;
	private String escDuration;
	private String esclevel;
	private String needesc;
	private String deptId,deptName;
	
	private String userName;
	private String date;
	private String time;
	
	private String severity;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeedtype() {
		return feedtype;
	}
	public void setFeedtype(String feedtype) {
		this.feedtype = feedtype;
	}
	public String getSubdept() {
		return subdept;
	}
	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFeedtype_id() {
		return feedtype_id;
	}
	public void setFeedtype_id(String feedtype_id) {
		this.feedtype_id = feedtype_id;
	}
	
	public String getSub_category() {
		return sub_category;
	}
	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}
	public String getFeed_msg() {
		return feed_msg;
	}
	public void setFeed_msg(String feed_msg) {
		this.feed_msg = feed_msg;
	}
	public String getAddressing_time() {
		return addressing_time;
	}
	public void setAddressing_time(String addressing_time) {
		this.addressing_time = addressing_time;
	}
	public String getEscalation_time() {
		return escalation_time;
	}
	public void setEscalation_time(String escalation_time) {
		this.escalation_time = escalation_time;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getResolution_time() {
		return resolution_time;
	}
	public void setResolution_time(String resolution_time) {
		this.resolution_time = resolution_time;
	}
	public String getEscDuration() {
		return escDuration;
	}
	public void setEscDuration(String escDuration) {
		this.escDuration = escDuration;
	}
	public String getEsclevel() {
		return esclevel;
	}
	public void setEsclevel(String esclevel) {
		this.esclevel = esclevel;
	}
	public String getNeedesc() {
		return needesc;
	}
	public void setNeedesc(String needesc) {
		this.needesc = needesc;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
