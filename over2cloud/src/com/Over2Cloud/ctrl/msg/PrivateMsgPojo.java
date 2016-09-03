package com.Over2Cloud.ctrl.msg;

public class PrivateMsgPojo {

	private int id;
	private String msgBy;
	private String msgTo;
	private String msgData;
	private String msgTime;
	private String msgDate;
	private String senderName;
	private String userTrue="false";
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsgBy() {
		return msgBy;
	}
	public void setMsgBy(String msgBy) {
		this.msgBy = msgBy;
	}
	public String getMsgTo() {
		return msgTo;
	}
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}
	public String getMsgData() {
		return msgData;
	}
	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}
	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	public String getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getUserTrue() {
		return userTrue;
	}
	public void setUserTrue(String userTrue) {
		this.userTrue = userTrue;
	}
	
}
