package com.Over2Cloud.ctrl.communication.instantMsg;

import java.util.List;

public class InstantMessageBean {
	private int id;
	private String messageName;
	private String writeMessage;
	private String name;
	private String mobileNo;
	private String email_id;
	private String writemail;
	private String validNum;
    private String duplicateData;
    private String invalidNum;
    private String blacklist;
    private String status;
    private String groupName;
    private String dept;
    
    private List<InstantMessageBean> detailDataList;
	
	
	public List<InstantMessageBean> getDetailDataList() {
		return detailDataList;
	}
	public void setDetailDataList(List<InstantMessageBean> detailDataList) {
		this.detailDataList = detailDataList;
		
	}
	
	
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getValidNum() {
		return validNum;
	}
	public void setValidNum(String validNum) {
		this.validNum = validNum;
	}
	public String getDuplicateData() {
		return duplicateData;
	}
	public void setDuplicateData(String duplicateData) {
		this.duplicateData = duplicateData;
	}
	public String getInvalidNum() {
		return invalidNum;
	}
	public void setInvalidNum(String invalidNum) {
		this.invalidNum = invalidNum;
	}
	public String getBlacklist() {
		return blacklist;
	}
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getWriteMessage() {
		return writeMessage;
	}
	public void setWriteMessage(String writeMessage) {
		this.writeMessage = writeMessage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getWritemail() {
		return writemail;
	}
	public void setWritemail(String writemail) {
		this.writemail = writemail;
	}
	
	
	
	
	
	

}
