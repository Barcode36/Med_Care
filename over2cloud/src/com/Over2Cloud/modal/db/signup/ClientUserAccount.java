package com.Over2Cloud.modal.db.signup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name = "client_info")
public class ClientUserAccount 
{
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="uid") private String uid;
	@Column(name="accountid") private String accountid;
	@Column(name="countryid") private String countryid;
	@Column(name="username") private String username;
	@Column(name="pwd") private String pwd;
	@Column(name="confim_key") private String confim_key;
	@Column(name="reqforupdatpwd") private String requestPwd="NA";
	@Column(name="isconfirmlink") private String isconfirmlink="NA";
	@Column(name="accountFlag") private String accountFlag="0";
	public ClientUserAccount(){}
	
	
	public ClientUserAccount(String accountid, String confim_key,
			String countryid, String pwd, String uid, String username,String accountFlag) {
		this.accountid = accountid;
		this.confim_key = confim_key;
		this.countryid = countryid;
		this.pwd = pwd;
		this.uid = uid;
		this.username = username;
		this.accountFlag = accountFlag;
	}
	
	public ClientUserAccount(String accountid, String confim_key,
			String countryid, String pwd, String uid, String username) {
		this.accountid = accountid;
		this.confim_key = confim_key;
		this.countryid = countryid;
		this.pwd = pwd;
		this.uid = uid;
		this.username = username;
	}
	public String getRequestPwd() {
		return requestPwd;
	}
	public void setRequestPwd(String requestPwd) {
		this.requestPwd = requestPwd;
	}
	public String getIsconfirmlink() {
		return isconfirmlink;
	}
	public void setIsconfirmlink(String isconfirmlink) {
		this.isconfirmlink = isconfirmlink;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getCountryid() {
		return countryid;
	}
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getConfim_key() {
		return confim_key;
	}
	public void setConfim_key(String confim_key) {
		this.confim_key = confim_key;
	}
	public String getAccountFlag() {
		return accountFlag;
	}
	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}
	
	
	
	

}
