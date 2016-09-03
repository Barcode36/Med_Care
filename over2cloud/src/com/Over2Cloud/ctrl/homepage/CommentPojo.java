package com.Over2Cloud.ctrl.homepage;

public class CommentPojo {

	private int id;
	private String commentdata;
	private String date;
	private String time;
	private String empName;
	private String userMatched="true";
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommentdata() {
		return commentdata;
	}
	public void setCommentdata(String commentdata) {
		this.commentdata = commentdata;
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
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getUserMatched() {
		return userMatched;
	}
	public void setUserMatched(String userMatched) {
		this.userMatched = userMatched;
	}
}
