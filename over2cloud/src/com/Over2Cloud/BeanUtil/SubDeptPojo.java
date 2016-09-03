package com.Over2Cloud.BeanUtil;

public class SubDeptPojo {

	
	//id,subdeptname,deptid,organizationLevel,userName,date,time
	private int id;
	private String subdeptname;
	private String deptid;
	private String organizationLevel;
	private String userName;
	private String date;
	private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSubdeptname() {
		return subdeptname;
	}
	public void setSubdeptname(String subdeptname) {
		this.subdeptname = subdeptname;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getOrganizationLevel() {
		return organizationLevel;
	}
	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
	}
	
}
