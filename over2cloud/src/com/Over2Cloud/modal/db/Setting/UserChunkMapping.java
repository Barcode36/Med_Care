package com.Over2Cloud.modal.db.Setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;
@Entity
@Table(name="user_chunk_mapping")
@Proxy(lazy = false)
public class UserChunkMapping implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="user_chunk_slab_from") private String userFromchunk;
	@Column(name="user_chunk_slab_to") private String userTochunk;
	@Column(name="Souce_ip_Domain_address") private String domainNameIp;
	@Column(name="insert_time") private String currenttime;
	@Column(name="country_iso_code") private String isoCuntrycode;
	@Column(name="city") private String city;
	@Column(name="iscity_iscountry_Blocking") private String isblock="L";
	
	@Transient
	private String slachunk;
	@Transient
	private String country;
	@Transient
	private String combinekey;
	
	public String getCombinekey() {
		return combinekey;
	}
	public void setCombinekey(String combinekey) {
		this.combinekey = combinekey;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSlachunk() {
		slachunk=getUserFromchunk()+"_"+getUserTochunk();
		return slachunk;
	}
	public void setSlachunk(String slachunk) {
		this.slachunk = slachunk;
	}
	public UserChunkMapping(){}
	public UserChunkMapping(String city, String currenttime,
			String domainNameIp, String isblock, String isoCuntrycode,
			String userFromchunk, String userTochunk) {
				this.city = city;
				this.currenttime = currenttime;
				this.domainNameIp = domainNameIp;
				this.isblock = isblock;
				this.isoCuntrycode = isoCuntrycode;
				this.userFromchunk = userFromchunk;
				this.userTochunk = userTochunk;
	}
	public String getUserFromchunk() {
		return userFromchunk;
	}
	public void setUserFromchunk(String userFromchunk) {
		this.userFromchunk = userFromchunk;
	}
	public String getUserTochunk() {
		return userTochunk;
	}
	public void setUserTochunk(String userTochunk) {
		this.userTochunk = userTochunk;
	}
	public String getDomainNameIp() {
		return new SingleSpaceImp().ChuckConnection(domainNameIp);
	}
	public void setDomainNameIp(String domainNameIp) {
		this.domainNameIp = domainNameIp;
	}
	public String getCurrenttime() {
		return currenttime;
	}
	public void setCurrenttime(String currenttime) {
		this.currenttime = currenttime;
	}
	public String getIsoCuntrycode() {
		return new SingleSpaceImp().ContryName(isoCuntrycode);
	}
	public void setIsoCuntrycode(String isoCuntrycode) {
		this.isoCuntrycode = isoCuntrycode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsblock() {
		if(isblock.equals("L")){isblock="Live";}
		if(isblock.equals("B")){isblock="Block";}
		return isblock;
	}
	public void setIsblock(String isblock) {
		this.isblock = isblock;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
