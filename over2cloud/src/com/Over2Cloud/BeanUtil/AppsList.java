package com.Over2Cloud.BeanUtil;

import java.util.Comparator;

public class AppsList  implements Comparator<AppsList>{
	private String applicationName;
	private String appsCode;
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getAppsCode() {
		return appsCode;
	}
	public void setAppsCode(String appsCode) {
		this.appsCode = appsCode;
	}
	@Override
	public int compare(AppsList o1, AppsList o2) {
		// TODO Auto-generated method stub
		return o1.getApplicationName().compareTo(o2.getApplicationName());
	}
	

}
