package com.Over2Cloud.modal.dao.imp.signup;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

@SuppressWarnings("serial")
public class BeanApps implements Comparator<BeanApps>,Serializable
{
	private String app_name;
	private String app_code;
	private Map<Integer, String> packageValue;
	
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getApp_code() {
		return app_code;
	}
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}
	
	public Map<Integer, String> getPackageValue() {
		return packageValue;
	}
	public void setPackageValue(Map<Integer, String> packageValue) {
		this.packageValue = packageValue;
	}
	
	@Override
	public int compare(BeanApps o1, BeanApps o2) {
	// TODO Auto-generated method stub
		return o1.getApp_name().compareTo(o2.getApp_name());
	}
	

}
