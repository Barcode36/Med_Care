package com.Over2Cloud.modal.db.Setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name="server_db_table")
@Proxy(lazy = false)
public class ClientDbServer {
	
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="mysqlServerName_Ip") private String dbServerIp_name;
	@Column(name="mysql_port") private String dbserverport;
	@Column(name="mysql_user_name") private String dbusername;
	@Column(name="mysql_password") private String dbpassword;
	@Column(name="inser_datetime") private String datetime;
	@Column(name="isocountrycode") private String isoCountrycode;
	@Column(name="isStatus") private String status;
	public ClientDbServer(){}
	public ClientDbServer(String datetime, String dbServerIp_name,
			String dbpassword, String dbusername, String isoCountrycode,
			String status,String dbserverport) {
		this.dbserverport=dbserverport;
		this.datetime = datetime;
		this.dbServerIp_name = dbServerIp_name;
		this.dbpassword = dbpassword;
		this.dbusername = dbusername;
		this.isoCountrycode = isoCountrycode;
		this.status = status;
	}
	
	public String getDbserverport() {
		return dbserverport;
	}

	public void setDbserverport(String dbserverport) {
		this.dbserverport = dbserverport;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDbServerIp_name() {
		return dbServerIp_name;
	}
	public void setDbServerIp_name(String dbServerIp_name) {
		this.dbServerIp_name = dbServerIp_name;
	}
	public String getDbusername() {
		return dbusername;
	}
	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}
	public String getDbpassword() {
		return dbpassword;
	}
	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getIsoCountrycode() {
		return isoCountrycode;
	}
	public void setIsoCountrycode(String isoCountrycode) {
		this.isoCountrycode = isoCountrycode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
	

}
