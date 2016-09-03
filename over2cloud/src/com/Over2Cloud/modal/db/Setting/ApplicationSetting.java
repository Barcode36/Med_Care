package com.Over2Cloud.modal.db.Setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="apps_details")
@Proxy(lazy = false)
public class ApplicationSetting {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="app_name") private String app_name;
	@Column(name="app_code") private String app_code;
	@Column(name="app_insertime") private String app_insertime;
	@Column(name="app_updatetime") private String app_updatetime;
	@Column(name="iso_country") private String iso_country;
	public  ApplicationSetting(){}
	public ApplicationSetting(String app_code, String app_insertime,String app_name, String app_updatetime, String iso_country) {
		this.app_code = app_code;
		this.app_insertime = app_insertime;
		this.app_name = app_name;
		this.app_updatetime = app_updatetime;
		this.iso_country = iso_country;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getApp_code() {
		return app_code;
	}
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}
	public String getApp_insertime() {
		return app_insertime;
	}
	public void setApp_insertime(String app_insertime) {
		this.app_insertime = app_insertime;
	}
	public String getApp_updatetime() {
		return app_updatetime;
	}
	public void setApp_updatetime(String app_updatetime) {
		this.app_updatetime = app_updatetime;
	}
	public String getIso_country() {
		return iso_country;
	}
	public void setIso_country(String iso_country) {
		this.iso_country = iso_country;
	}
	
	

	
	
	
	
}
