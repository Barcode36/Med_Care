package com.Over2Cloud.BeanUtil;

public class ReportPojo {

	private int id;
	private String name;
	private String description;
	private String querry;
	private String columnName;
	private String savedOn;
	private String savedAt;
	private String takeAction;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuerry() {
		return querry;
	}
	public void setQuerry(String querry) {
		this.querry = querry;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getSavedOn() {
		return savedOn;
	}
	public void setSavedOn(String savedOn) {
		this.savedOn = savedOn;
	}
	public String getSavedAt() {
		return savedAt;
	}
	public void setSavedAt(String savedAt) {
		this.savedAt = savedAt;
	}
	public String getTakeAction() {
		return takeAction;
	}
	public void setTakeAction(String takeAction) {
		this.takeAction = takeAction;
	}

}
