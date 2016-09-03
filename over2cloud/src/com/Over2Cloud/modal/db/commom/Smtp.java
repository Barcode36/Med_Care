package com.Over2Cloud.modal.db.commom;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="smtp_detail")
@Proxy(lazy = false)
public class Smtp implements Serializable{
	
	private int id;
	private String  server;
	private String  port;
	private String  emailid;
	private String  pwd;
	private String  subject;
	private String  createon_date;
	private String  createat;
	public Smtp(String createat, String createon_date,
			String emailid, String port, String pwd, String server,
			String subject) {
		this.createat = createat;
		this.createon_date = createon_date;
		this.emailid = emailid;
		this.port = port;
		this.pwd = pwd;
		this.server = server;
		this.subject = subject;
	}
	public Smtp(){}
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="server")
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
	@Column(name="port")
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	@Column(name="emailid")
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	@Column(name="password")
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	@Column(name="subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name="createon")
	public String getCreateon_date() {
		return createon_date;
	}
	public void setCreateon_date(String createon_date) {
		this.createon_date = createon_date;
	}
	
	@Column(name="createat")
	public String getCreateat() {
		return createat;
	}
	public void setCreateat(String createat) {
		this.createat = createat;
	}

}
