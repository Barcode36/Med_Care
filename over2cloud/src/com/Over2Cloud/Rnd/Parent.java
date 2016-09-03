package com.Over2Cloud.Rnd;

import java.util.List;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;

public class Parent {
	private List<Child> childList;
	private List<ConfigurationUtilBean> childList1;

	public List<Child> getChildList() {
		return childList;
	}

	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}

	public List<ConfigurationUtilBean> getChildList1() {
		return childList1;
	}

	public void setChildList1(List<ConfigurationUtilBean> childList1) {
		this.childList1 = childList1;
	}
	
}
