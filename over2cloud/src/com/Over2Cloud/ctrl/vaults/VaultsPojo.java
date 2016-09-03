package com.Over2Cloud.ctrl.vaults;

public class VaultsPojo {

	private int id;
	private String userName;
	private String fileName;
	private String filePathForDownload;
	private String dateTime;
	private String uploadType;
	private String userTrue="false";
	
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePathForDownload() {
		return filePathForDownload;
	}
	public void setFilePathForDownload(String filePathForDownload) {
		this.filePathForDownload = filePathForDownload;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getUserTrue() {
		return userTrue;
	}
	public void setUserTrue(String userTrue) {
		this.userTrue = userTrue;
	}
	
	
}
