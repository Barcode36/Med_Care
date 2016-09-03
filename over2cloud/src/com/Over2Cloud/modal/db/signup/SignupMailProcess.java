package com.Over2Cloud.modal.db.signup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="signup_product")
public class SignupMailProcess {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="orgnizationName") private String orgnizationName;
	@Column(name="regUserName") private String regUserName;
	@Column(name="URL") private String uRL;
	@Column(name="isStatus") private String isStatus;
	@Column(name="reg_id") private int reg_id;
	public SignupMailProcess(){}
	public SignupMailProcess(String url, String isStatus,
			String orgnizationName, String regUserName,int reg_id) {
		this.reg_id=reg_id;
		this.uRL = url;
		this.isStatus = isStatus;
		this.orgnizationName = orgnizationName;
		this.regUserName = regUserName;
	}
	public int getReg_id() {
		return reg_id;
	}
	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrgnizationName() {
		return orgnizationName;
	}
	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public String getURL() {
		return uRL;
	}
	public void setURL(String url) {
		uRL = url;
	}
	public String getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(String isStatus) {
		this.isStatus = isStatus;
	}
}
