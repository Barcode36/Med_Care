package com.Over2Cloud.ctrl.feedback.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "PatientInfo")
@Proxy(lazy = false)
public class PatientInfo implements Serializable{


	private static final long serialVersionUID = 1L;

	@Column(name="patientId")
	 private String patientId;  
	
	@Column(name="patientName")
	 private String patientName;
	
	@Column(name="doctorName")
	 private String doctorName;
	
	@Column(name="purOfVisit")
	 private String purOfVisit; 
	
	@Column(name="visitDate")
	 private String visitDate;
	
	@Column(name="dischargeDate")
	 private String dischargeDate;
	
	@Column(name="roomNo")
	 private String roomNo;
	
	@Column(name="patientMobileNo")
	 private String patientMobileNo;
	
	@Column(name="patientEmailId")
	 private String patientEmailId;
	
	@Column(name="insertDate")
	 private String insertDate;  
	
	@Column(name="smsFlag")
	 private String smsFlag;
	
	@Column(name="updateDate")
	 private String updateDate;
	
	@Column(name="patType")
	 private String patType;
	
	@Column(name="visit_type")
	 private String visit_type;  
	
	@Column(name="companytype")
	 private String companytype;
	
	@Column(name="recordId")
	 private String recordId;
	
	@Column(name="discharge_datetime")
	 private String discharge_datetime;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	 private String id;

	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @return the purOfVisit
	 */
	public String getPurOfVisit() {
		return purOfVisit;
	}

	/**
	 * @return the visitDate
	 */
	public String getVisitDate() {
		return visitDate;
	}

	/**
	 * @return the dischargeDate
	 */
	public String getDischargeDate() {
		return dischargeDate;
	}

	/**
	 * @return the roomNo
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * @return the patientMobileNo
	 */
	public String getPatientMobileNo() {
		return patientMobileNo;
	}

	/**
	 * @return the patientEmailId
	 */
	public String getPatientEmailId() {
		return patientEmailId;
	}

	/**
	 * @return the insertDate
	 */
	public String getInsertDate() {
		return insertDate;
	}

	/**
	 * @return the smsFlag
	 */
	public String getSmsFlag() {
		return smsFlag;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @param purOfVisit the purOfVisit to set
	 */
	public void setPurOfVisit(String purOfVisit) {
		this.purOfVisit = purOfVisit;
	}

	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * @param dischargeDate the dischargeDate to set
	 */
	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	/**
	 * @param roomNo the roomNo to set
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * @param patientMobileNo the patientMobileNo to set
	 */
	public void setPatientMobileNo(String patientMobileNo) {
		this.patientMobileNo = patientMobileNo;
	}

	/**
	 * @param patientEmailId the patientEmailId to set
	 */
	public void setPatientEmailId(String patientEmailId) {
		this.patientEmailId = patientEmailId;
	}

	/**
	 * @param insertDate the insertDate to set
	 */
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	/**
	 * @param smsFlag the smsFlag to set
	 */
	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getPatType() {
		return patType;
	}

	public void setPatType(String patType) {
		this.patType = patType;
	}

	public String getVisit_type() {
		return visit_type;
	}

	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}

	public String getCompanytype() {
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getDischarge_datetime() {
		return discharge_datetime;
	}

	public void setDischarge_datetime(String discharge_datetime) {
		this.discharge_datetime = discharge_datetime;
	}  
}