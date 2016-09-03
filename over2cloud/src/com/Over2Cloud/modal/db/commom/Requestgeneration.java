package com.Over2Cloud.modal.db.commom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="requestdeposit")
@Proxy(lazy = false)
public class Requestgeneration 
{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="accountid") private String accountid;
	@Column(name="country") private String country;
	@Column(name="typeofreq") private String typeofreq;
	@Column(name="subject") private String subject;
	@Column(name="clientdiscription") private String clientdiscription;
	@Column(name="serverDiscription") private String serverDiscription;
	@Column(name="insertime") private String insertime;
	@Column(name="inserdate") private String inserdate;
	@Column(name="clientNotification") private String clientNotification;
	@Column(name="Over2CloudNoification") private String over2CloudNoification;
    public Requestgeneration(){}
	public Requestgeneration(String over2CloudNoification, String accountid,
			String clientNotification, String clientdiscription,
			String country, String inserdate, String insertime,
			String serverDiscription, String subject, String typeofreq) {
		this.over2CloudNoification = over2CloudNoification;
		this.accountid = accountid;
		this.clientNotification = clientNotification;
		this.clientdiscription = clientdiscription;
		this.country = country;
		this.inserdate = inserdate;
		this.insertime = insertime;
		this.serverDiscription = serverDiscription;
		this.subject = subject;
		this.typeofreq = typeofreq;
	}
	
	public String getClientdiscription() {
		return clientdiscription;
	}

	public void setClientdiscription(String clientdiscription) {
		this.clientdiscription = clientdiscription;
	}

	public String getServerDiscription() {
		return serverDiscription;
	}

	public void setServerDiscription(String serverDiscription) {
		this.serverDiscription = serverDiscription;
	}

	public String getClientNotification() {
		return clientNotification;
	}

	public void setClientNotification(String clientNotification) {
		this.clientNotification = clientNotification;
	}
	public String getOver2CloudNoification() {
		return over2CloudNoification;
	}
	public void setOver2CloudNoification(String over2CloudNoification) {
		this.over2CloudNoification = over2CloudNoification;
	}
	public String getInserdate() {
		return inserdate;
	}

	public void setInserdate(String inserdate) {
		this.inserdate = inserdate;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTypeofreq() {
		return typeofreq;
	}
	public void setTypeofreq(String typeofreq) {
		this.typeofreq = typeofreq;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getInsertime() {
		return insertime;
	}
	public void setInsertime(String insertime) {
		this.insertime = insertime;
	}
	
	
	
	
	
}
