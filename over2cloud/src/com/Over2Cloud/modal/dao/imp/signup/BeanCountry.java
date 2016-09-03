package com.Over2Cloud.modal.dao.imp.signup;

import java.util.Comparator;

public class BeanCountry implements Comparator<BeanCountry>
{
	private String contryName;
	private String iso_code;
	
	public String getContryName() {
		return contryName;
	}
	public void setContryName(String contryName) {
		this.contryName = contryName;
	}
	public String getIso_code() {
		return iso_code;
	}
	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}
	@Override
	public int compare(BeanCountry o1, BeanCountry o2) {
		// TODO Auto-generated method stub
		return o1.getContryName().compareTo(o2.getContryName());
	}
	
	
	

}
