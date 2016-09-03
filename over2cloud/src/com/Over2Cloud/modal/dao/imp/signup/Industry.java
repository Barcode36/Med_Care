package com.Over2Cloud.modal.dao.imp.signup;

import java.util.Comparator;

public class Industry implements Comparator<Industry>
{
 private String industryname;
 private String industryid;
public String getIndustryname() {
	return industryname;
}
public void setIndustryname(String industryname) {
	this.industryname = industryname;
}
public String getIndustryid() {
	return industryid;
}
public void setIndustryid(String industryid) {
	this.industryid = industryid;
}
@Override
public int compare(Industry o1, Industry o2) {
	// TODO Auto-generated method stub
	return o1.getIndustryname().compareTo(o2.getIndustryname());
}

}
