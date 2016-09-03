package com.Over2Cloud.ctrl.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

@Entity
@Table(name="genric_upload")
@Proxy(lazy = false)
public class Avatar{

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer avatarId;
	@Column(name="image")
	private byte[] image;
	@Column(name="content_type")
	@Type(type="blob")
	private String contentType;
	public Avatar() {
	}
 
	public Avatar(byte[] image) {
		this.image = image;
	}
 
	public Integer getAvatarId() {
		return this.avatarId;
	}
 
	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}
 
	public byte[] getImage() {
		return this.image;
	}
 
	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
