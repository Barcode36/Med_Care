package com.Over2Cloud.BeanUtil;
import java.util.ArrayList;

public class RightsCategory 
{
	private String module = null;
	private ArrayList<UserRights>  list = null;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public ArrayList<UserRights> getList() {
		return list;
	}
	public void setList(ArrayList<UserRights> list) {
		this.list = list;
	}
}
