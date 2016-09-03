package com.Over2Cloud.modal.db.signup;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.Over2Cloud.CommonClasses.DateUtil;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name = "registation_sinup")
public class Registation 
{
		@Id
		@Column(name = "id", nullable = false, unique = true)
		@GeneratedValue(strategy = GenerationType.AUTO) private int id;
		@Column(name="org_reg_name") private String org_Registation_name;
		@Column(name="org_name") private String orgname;
		@Column(name="org_type") private String orgtype;
		@Column(name="industry") private String industry;
		@Column(name="regAddress") private String regaddress;
		@Column(name="city") private String city;
		@Column(name="pincode") private String pincode;
		@Column(name="contactNo") private String contactNo;
		@Column(name="contact_emailid") private String contact_emailid;
		@Column(name="country") private String country;
		@Column(name="confirm_key") private String confirm_key;
		@Column(name="confirm_agreement") private boolean confirm_agreement;
		@Column(name="created_datetime") private String create_datetime="NA";
		@Column(name="update_datetime")  private String update_datetime="NA";
		@Column(name="brief")  private String brief="NA";
		@Column(name="accountType")  private String accountType="NA";
		@Column(name="accountid")  private String accountid="0";
		@Column(name="uuid")  private String uuid="0";
		@Column(name="isAccountActive")  private String isAccountActive="D";
		@Column(name="signupstep")  private String iscomplited="N";
		@Column(name="reg_user")  private String reguser=null;
		@Column(name="user_accountid") private String useraccountid;
		@Column(name="companysize") private String companysize;
		@Column(name="jobfunctionalArea") private String jobfunctionalArea;
		@Column(name="jobtitle") private String jobtitle;
		@Column(name="bPhonenumber") private String bPhonenumber;
		@Column(name="demoAccount") private String demoAccount="0";
		@Column(name="date") private String date=DateUtil.getCurrentDateUSFormat();
		
		public Registation(){}
		public Registation(String city, boolean confirm_agreement,
				String confirm_key, String contactNo, String contact_emailid,
				String country, String create_datetime, String industry,
				String orgname, String orgtype, String pincode, String regaddress,String org_Registation_name,String bief
			    ,String accountType,String accountid,String uuid,String isAccountActive,String reguser,
			    String useraccountid,String companysize,String jobfunctionalArea,String jobtitle, String bPhonenumber){
			this.companysize=companysize;
			this.jobfunctionalArea=jobfunctionalArea;
			this.jobtitle=jobtitle;
			this.bPhonenumber=bPhonenumber;
			this.accountType=accountType;
			this.reguser=reguser;
			this.brief=bief;
			this.org_Registation_name=org_Registation_name;
			this.city = city;
			this.confirm_agreement = confirm_agreement;
			this.confirm_key = confirm_key;
			this.contactNo = contactNo;
			this.contact_emailid = contact_emailid;
			this.country = country;
			this.create_datetime =create_datetime;
			this.industry = industry;
			this.orgname = orgname;
			this.orgtype = orgtype;
			this.pincode = pincode;
			this.regaddress = regaddress;
			this.accountid=accountid;
			this.uuid=uuid;
			this.isAccountActive=isAccountActive;
			this.useraccountid=useraccountid;
		}
		public String getCompanysize() {
			return companysize;
		}
		public void setCompanysize(String companysize) {
			this.companysize = companysize;
		}
		public String getJobfunctionalArea() {
			return jobfunctionalArea;
		}
		public void setJobfunctionalArea(String jobfunctionalArea) {
			this.jobfunctionalArea = jobfunctionalArea;
		}
		public String getJobtitle() {
			return jobtitle;
		}
		public void setJobtitle(String jobtitle) {
			this.jobtitle = jobtitle;
		}
		public String getBPhonenumber() {
			return bPhonenumber;
		}
		public void setBPhonenumber(String phonenumber) {
			bPhonenumber = phonenumber;
		}
		public String getUseraccountid() {
			return useraccountid;
		}
		public void setUseraccountid(String useraccountid) {
			this.useraccountid = useraccountid;
		}
		public String getReguser() {
			return reguser;
		}
		public void setReguser(String reguser) {
			this.reguser = reguser;
		}
		public String getIscomplited() {
			return iscomplited;
		}
		public void setIscomplited(String iscomplited) {
			this.iscomplited = iscomplited;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getAccountid() {
			
			return accountid;
		}
		public void setAccountid(String accountid) {
			this.accountid = accountid;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getIsAccountActive() {
			return isAccountActive;
		}
		public void setIsAccountActive(String isAccountActive) {
			this.isAccountActive = isAccountActive;
		}
		public String getPincode() {
			return pincode;
		}
		public void setPincode(String pincode) {
			this.pincode = pincode;
		}
		public String getContactNo() {
			return contactNo;
		}
		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}
		public String getAccountType() {
			return accountType;
		}
		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}
		public String getBrief() {
			return brief;
		}
		public void setBrief(String brief) {
			this.brief = brief;
		}
		public String getOrg_Registation_name() {
			return org_Registation_name;
		}
		public void setOrg_Registation_name(String org_Registation_name) {
			this.org_Registation_name = org_Registation_name;
		}
		public String getOrgname() {
			return orgname;
		}
		public void setOrgname(String orgname) {
			this.orgname = orgname;
		}
		public String getOrgtype() {
			return orgtype;
		}
		public void setOrgtype(String orgtype) {
			this.orgtype = orgtype;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getRegaddress() {
			return regaddress;
		}
		public void setRegaddress(String regaddress) {
			this.regaddress = regaddress;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getContact_emailid() {
			return contact_emailid;
		}
		public void setContact_emailid(String contact_emailid) {
			this.contact_emailid = contact_emailid;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getConfirm_key() {
			return confirm_key;
		}
		public void setConfirm_key(String confirm_key) {
			this.confirm_key = confirm_key;
		}
		public boolean isConfirm_agreement() {
			return confirm_agreement;
		}
		public void setConfirm_agreement(boolean confirm_agreement) {
			this.confirm_agreement = confirm_agreement;
		}
		public String getCreate_datetime() {
			return create_datetime;
		}
		public void setCreate_datetime(String create_datetime) {
			this.create_datetime = create_datetime;
		}
		public String getUpdate_datetime() {
			return update_datetime;
		}
		public void setUpdate_datetime(String update_datetime) {
			this.update_datetime = update_datetime;
		}
		public String getDemoAccount() {
			return demoAccount;
		}
		public void setDemoAccount(String demoAccount) {
			this.demoAccount = demoAccount;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
}
