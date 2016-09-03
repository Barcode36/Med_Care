package com.Over2Cloud.BeanUtil;

import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;


public class RegHieInformation 
{
	
		private String	username;
		private String	orgname;
		private String	accounttype;
		private String	orgusername;
		private String	productcode;
		private String productappuser;
		private int	   productuser;
		private String validifyfrom;
		private String valididyto;
		private int    productid;
		private String combinekey;
		private String accountid;
		private String isblock;
		private String city;
		private String country;
		private String signupstatus;
		private String accountids;
		private String islive;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getOrgname() {
			return orgname;
		}
		public void setOrgname(String orgname) {
			this.orgname = orgname;
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
		public String getOrgusername() {
			return orgusername;
		}
		public void setOrgusername(String orgusername) {
			this.orgusername = orgusername;
		}
		public String getProductcode() {
			return productcode;
		}
		public void setProductcode(String productcode) {
			this.productcode = productcode;
		}
		public String getProductappuser() {
			return productappuser;
		}
		public void setProductappuser(String productappuser) {
			this.productappuser = productappuser;
		}
		public int getProductuser() {
			return productuser;
		}
		public void setProductuser(int productuser) {
			this.productuser = productuser;
		}
		public String getValidifyfrom() {
			return validifyfrom;
		}
		public void setValidifyfrom(String validifyfrom) {
			this.validifyfrom = validifyfrom;
		}
		public String getValididyto() {
			return valididyto;
		}
		public void setValididyto(String valididyto) {
			this.valididyto = valididyto;
		}
		public int getProductid() {
			return productid;
		}
		public void setProductid(int productid) {
			this.productid = productid;
		}
		public String getCombinekey() {
			return combinekey;
		}
		public void setCombinekey(String combinekey) {
			this.combinekey = combinekey;
		}
		public String getAccountid() {
			return accountid;
		}
		public void setAccountid(String accountid) {
			this.accountid = accountid;
		}
		public String getIsblock() {
			return isblock;
		}
		public void setIsblock(String isblock) {
			this.isblock = isblock;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			if(country==null){country="NA";}else{country=new SingleSpaceImp().ContryName(country);}
	         
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getSignupstatus() {
			if(signupstatus==null){signupstatus="NA";}else{
				if(signupstatus.equalsIgnoreCase("C"))
				{
					signupstatus="Completed";
				}
				else
				{
					signupstatus="Not Completed";
				}
				
			}
					return signupstatus;
				}
		public void setSignupstatus(String signupstatus) {
			this.signupstatus = signupstatus;
		}
		public String getAccountids() {
			return accountids;
		}
		public void setAccountids(String accountids) {
			this.accountids = accountids;
		}
		public String getIslive() {
			return islive;
		}
		public void setIslive(String islive) {
			this.islive = islive;
		}
		


}
