package com.Over2Cloud.ctrl.communication.blackList;

public class BlackListBean {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String name;
	private String mobileNo;
	private String reason;
	private String validNum;
    private String alreadyPresent;
    private String invalidNum;
	public String getValidNum() {
		return validNum;
	}
	public void setValidNum(String validNum) {
		this.validNum = validNum;
	}
	public String getAlreadyPresent() {
		return alreadyPresent;
	}
	public void setAlreadyPresent(String alreadyPresent) {
		this.alreadyPresent = alreadyPresent;
	}
	public String getInvalidNum() {
		return invalidNum;
	}
	public void setInvalidNum(String invalidNum) {
		this.invalidNum = invalidNum;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
    
    
    
    

}
