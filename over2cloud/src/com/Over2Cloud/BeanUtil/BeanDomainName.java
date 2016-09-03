package com.Over2Cloud.BeanUtil;

import java.util.Comparator;
public class BeanDomainName implements Comparator<BeanDomainName>{
	private String id;
	private String domainname;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getDomainname() {
		return domainname;
	}
	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}
	@Override
	public int compare(BeanDomainName o1, BeanDomainName o2) {
		// TODO Auto-generated method stub

		return o1.getDomainname().compareTo(o2.getDomainname());
	}
	
}
