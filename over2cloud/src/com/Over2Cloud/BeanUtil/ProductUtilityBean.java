package com.Over2Cloud.BeanUtil;

import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;

public class ProductUtilityBean {
	private String accountid;
	private String countryid;
	private String orgname;
	private String orgRegname;
	private String accounttype;
	private String productcode;
	private String productuser;
	private String totaluser;
	private String validityfrom;
	private String valiudityto;
	private String reqRechargUpperHric;
	private String mailtoOrgnization;
	private String productlist;
	
	public String getProductlist() {
		return productlist;
	}
	public void setProductlist(String productlist) {
		this.productlist = productlist;
	}
	public String getValidityfrom() {
		return validityfrom;
	}
	public void setValidityfrom(String validityfrom) {
		this.validityfrom = validityfrom;
	}
	public String getValiudityto() {
		return valiudityto;
	}
	public void setValiudityto(String valiudityto) {
		this.valiudityto = valiudityto;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getCountryid() {
		if(countryid==null){countryid="NA";}else{countryid=new SingleSpaceImp().ContryName(countryid);}
		return countryid;
	}
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getOrgRegname() {
		return orgRegname;
	}
	public void setOrgRegname(String orgRegname) {
		this.orgRegname = orgRegname;
	}
	public String getAccounttype() {
		if(accounttype.equals("CAA"))
		{
			accounttype="Cloud Associate Account";
		}
		else if(accounttype.equals("COA"))
		{
			accounttype="Cloud Organization Account";
		}
		else if(accounttype.equals("LOA"))
		{
			accounttype="Local Organization Account";
		}
		else if(accounttype.equals("O"))
		{
			accounttype="Other";
		}
		else 
		{
			accounttype="NA";
		}
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductuser() {
		return productuser;
	}
	public void setProductuser(String productuser) {
		this.productuser = productuser;
	}
	public String getTotaluser() {
		return totaluser;
	}
	public void setTotaluser(String totaluser) {
		this.totaluser = totaluser;
	}
	public String getReqRechargUpperHric() {
		return reqRechargUpperHric;
	}
	public void setReqRechargUpperHric(String reqRechargUpperHric) {
		this.reqRechargUpperHric = reqRechargUpperHric;
	}
	public String getMailtoOrgnization() {
		return mailtoOrgnization;
	}
	public void setMailtoOrgnization(String mailtoOrgnization) {
		this.mailtoOrgnization = mailtoOrgnization;
	}
	
	
	
	
	

}
