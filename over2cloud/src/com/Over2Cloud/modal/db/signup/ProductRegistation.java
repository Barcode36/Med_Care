package com.Over2Cloud.modal.db.signup;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name = "product_intrested")
public class ProductRegistation 
{
		@Id
		@Column(name = "productid", nullable = false, unique = true)
		@GeneratedValue(strategy = GenerationType.AUTO) private int id;
		@Column(name="productcode") private String productcode;
		@Column(name="productuser") private String productuser;
		@Column(name="insert_time") private String inserttime;
		@Column(name="confirm_key") private String confirm_key;
		@Column(name="accountid") private String accountid;
		@Column(name="uuid") private String uuid;
		@Column(name="reg_id") private String reg_id;
		@Column(name="validity_from") private String validity_from;
		@Column(name="validity_to") private String validity_to;
		@Column(name="contryid") private String contryid;
		
		
		public ProductRegistation(String accountid, String confirm_key,
				String inserttime, String productcode, String productuser,
				String reg_id, String uuid,String validity_from,String validity_to,String contryid ) {
			this.accountid = accountid;
			this.confirm_key = confirm_key;
			this.inserttime = inserttime;
			this.productcode = productcode;
			this.productuser = productuser;
			this.reg_id = reg_id;
			this.uuid = uuid;
			this.validity_from=validity_from;
			this.validity_to=validity_to;
			this.contryid=contryid;
		}
		
		public String getContryid() {
			return contryid;
		}
		public void setContryid(String contryid) {
			this.contryid = contryid;
		}
		public String getValidity_from() {
			return validity_from;
		}
		public void setValidity_from(String validity_from) {
			this.validity_from = validity_from;
		}
		public String getValidity_to() {
			return validity_to;
		}
		public void setValidity_to(String validity_to) {
			this.validity_to = validity_to;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getProductcode() {
			return productcode;
		}
		public void setProductcode(String productcode) {
			this.productcode = productcode;
		}
		public String getProductuser() {
			return productuser;
		}
		public void setProductuser(String productuser) {
			this.productuser = productuser;
		}
		public String getInserttime() {
			return inserttime;
		}
		public void setInserttime(String inserttime) {
			this.inserttime = inserttime;
		}
		public String getConfirm_key() {
			return confirm_key;
		}
		public void setConfirm_key(String confirm_key) {
			this.confirm_key = confirm_key;
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
		public String getReg_id() {
			return reg_id;
		}
		public void setReg_id(String reg_id) {
			this.reg_id = reg_id;
		}
	
		
		
}
