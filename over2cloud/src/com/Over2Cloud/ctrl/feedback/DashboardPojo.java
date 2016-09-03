package com.Over2Cloud.ctrl.feedback;

public class DashboardPojo {
	private String title;
private int totalYes;
private int totalNo;
private String deptName;
public int getTotalYes() {
	return totalYes;
}
public void setTotalYes(int totalYes) {
	this.totalYes = totalYes;
}
public int getTotalNo() {
	return totalNo;
}
public void setTotalNo(int totalNo) {
	this.totalNo = totalNo;
}
public String getDeptName() {
	return deptName;
}
public void setDeptName(String deptName) {
	this.deptName = deptName;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

}
