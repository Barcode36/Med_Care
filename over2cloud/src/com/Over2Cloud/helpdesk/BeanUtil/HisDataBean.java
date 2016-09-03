package com.Over2Cloud.helpdesk.BeanUtil;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

	/**
	 * 
	 * @author dream sol
	 *CREATE TABLE [dbo].[EMP_DB_HELPDESK](
		
		
		[EMP_OLD_NO] [varchar](15) NULL,
		[doJ] [datetime] NULL,
		[email_id] [varchar](100) NULL,
		[NAME] [varchar](213) NULL,
		[mobile_no] [varchar](70) NOT NULL,
		[desig_code] [smallint] NULL,
		[desig_name] [varchar](50) NOT NULL,
		[department_CODE] [smallint] NULL,
		[department_name] [varchar](100) NULL,
		[SUB_DEPT] [varchar](100) NULL,
		[dos] [datetime] NULL
	) ON [PRIMARY]
        
        
        New Table Structure IS::
        
	 */

	@Entity
	@Table(name = "EMP_DB_HELPDESK")
	public class HisDataBean implements Serializable{
		private static final long serialVersionUID = -5540252939089082253L;
		
		@SuppressWarnings("unused")
		@Id
		@GeneratedValue
		@Column(name="id")
		private int id; 
		
		@Column(name="emp_no")
		private String emp_no;
		
		@Column(name="EMP_OLD_NO")
		private String EMP_OLD_NO;
		
		@Column(name="doj")
		private String doj;
		
		@Column(name="email_id")
		private String email_id;
		
		@Column(name="NAME")
		private String Name;
		
		@Column(name="mobile_no")
		private String mobile_no;
		
		@Column(name="desig_code")
		private String desg_code;
		
		@Column(name="desig_name")
		private String desg_name;
		
		@Column(name="department_CODE")
		private String department_CODE;
		
		@Column(name="department_name")
		private String department_name;
		
		@Column(name="SUB_DEPT")		
		private String SUB_DEPT;
		
		@Column(name="dos")
		private String dos;
		
		@Column(name="DepartmentCode_HIS")
		private String hisDepartmentCode;
		
		@Column(name="Dept_Code")
		private String deptCode;
		
		@Column(name="SubDeptID_HD")
		private String subdeptID;
		
		@Column(name="extno")
		private String extno;

		/**
		 * @return the serialversionuid
		 */
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		/**
		 * @return the emp_no
		 */
		public String getEmp_no() {
			return emp_no;
		}

		/**
		 * @return the eMP_OLD_NO
		 */
		public String getEMP_OLD_NO() {
			return EMP_OLD_NO;
		}

		/**
		 * @return the doj
		 */
		public String getDoj() {
			return doj;
		}

		/**
		 * @return the email_id
		 */
		public String getEmail_id() {
			return email_id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return Name;
		}

		/**
		 * @return the mobile_no
		 */
		public String getMobile_no() {
			return mobile_no;
		}

		/**
		 * @return the desg_code
		 */
		public String getDesg_code() {
			return desg_code;
		}

		/**
		 * @return the desg_name
		 */
		public String getDesg_name() {
			return desg_name;
		}

		/**
		 * @return the department_CODE
		 */
		public String getDepartment_CODE() {
			return department_CODE;
		}

		/**
		 * @return the department_name
		 */
		public String getDepartment_name() {
			return department_name;
		}

		/**
		 * @return the sUB_DEPT
		 */
		public String getSUB_DEPT() {
			return SUB_DEPT;
		}

		/**
		 * @return the dos
		 */
		public String getDos() {
			return dos;
		}

		/**
		 * @return the hisDepartmentCode
		 */
		public String getHisDepartmentCode() {
			return hisDepartmentCode;
		}

		/**
		 * @return the deptCode
		 */
		public String getDeptCode() {
			return deptCode;
		}

		/**
		 * @return the subdeptID
		 */
		public String getSubdeptID() {
			return subdeptID;
		}

		/**
		 * @return the extno
		 */
		public String getExtno() {
			return extno;
		}

		/**
		 * @param emp_no the emp_no to set
		 */
		public void setEmp_no(String emp_no) {
			this.emp_no = emp_no;
		}

		/**
		 * @param eMP_OLD_NO the eMP_OLD_NO to set
		 */
		public void setEMP_OLD_NO(String eMP_OLD_NO) {
			EMP_OLD_NO = eMP_OLD_NO;
		}

		/**
		 * @param doj the doj to set
		 */
		public void setDoj(String doj) {
			this.doj = doj;
		}

		/**
		 * @param email_id the email_id to set
		 */
		public void setEmail_id(String email_id) {
			this.email_id = email_id;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			Name = name;
		}

		/**
		 * @param mobile_no the mobile_no to set
		 */
		public void setMobile_no(String mobile_no) {
			this.mobile_no = mobile_no;
		}

		/**
		 * @param desg_code the desg_code to set
		 */
		public void setDesg_code(String desg_code) {
			this.desg_code = desg_code;
		}

		/**
		 * @param desg_name the desg_name to set
		 */
		public void setDesg_name(String desg_name) {
			this.desg_name = desg_name;
		}

		/**
		 * @param department_CODE the department_CODE to set
		 */
		public void setDepartment_CODE(String department_CODE) {
			this.department_CODE = department_CODE;
		}

		/**
		 * @param department_name the department_name to set
		 */
		public void setDepartment_name(String department_name) {
			this.department_name = department_name;
		}

		/**
		 * @param sUB_DEPT the sUB_DEPT to set
		 */
		public void setSUB_DEPT(String sUB_DEPT) {
			SUB_DEPT = sUB_DEPT;
		}

		/**
		 * @param dos the dos to set
		 */
		public void setDos(String dos) {
			this.dos = dos;
		}

		/**
		 * @param hisDepartmentCode the hisDepartmentCode to set
		 */
		public void setHisDepartmentCode(String hisDepartmentCode) {
			this.hisDepartmentCode = hisDepartmentCode;
		}

		/**
		 * @param deptCode the deptCode to set
		 */
		public void setDeptCode(String deptCode) {
			this.deptCode = deptCode;
		}

		/**
		 * @param subdeptID the subdeptID to set
		 */
		public void setSubdeptID(String subdeptID) {
			this.subdeptID = subdeptID;
		}

		/**
		 * @param extno the extno to set
		 */
		public void setExtno(String extno) {
			this.extno = extno;
		}
	}

