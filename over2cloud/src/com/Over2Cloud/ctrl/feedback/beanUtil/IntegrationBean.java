package com.Over2Cloud.ctrl.feedback.beanUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name = "patient_ip")
public class IntegrationBean {
//"cr_number";"patient_name";"mobile_no";"residential_phone_no";"email";"visit_type";"patient_type";"inpatient_number";"
	//admit_consultant";"consult_clinician_name";"station_unit";"station";"admission_time";"discharge_datetime"
	
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	
	@Column(name="cr_number")
	private String cr_number;
	
	@Column(name="patient_name")
	private String patient_name;
	
	@Column(name="patient_name")
	private String mobile_no;
	
	@Column(name="patient_name")
	private String residential_phone_no;
	
	@Column(name="patient_name")
	private String email;
	
	@Column(name="patient_name")
	private String visit_type;
	
	@Column(name="patient_name")
	private String patient_type;
	
	@Column(name="patient_name")
	private String inpatient_number;
	
	@Column(name="patient_name")
	private String admit_consultant;
	private String consult_clinician_name;
	private String station_unit;
	private String station;
	private String admission_time;
	private String discharge_datetime;
	public String getCr_number() {
		return cr_number;
	}
	public void setCr_number(String cr_number) {
		this.cr_number = cr_number;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getResidential_phone_no() {
		return residential_phone_no;
	}
	public void setResidential_phone_no(String residential_phone_no) {
		this.residential_phone_no = residential_phone_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVisit_type() {
		return visit_type;
	}
	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}
	public String getPatient_type() {
		return patient_type;
	}
	public void setPatient_type(String patient_type) {
		this.patient_type = patient_type;
	}
	public String getInpatient_number() {
		return inpatient_number;
	}
	public void setInpatient_number(String inpatient_number) {
		this.inpatient_number = inpatient_number;
	}
	public String getAdmit_consultant() {
		return admit_consultant;
	}
	public void setAdmit_consultant(String admit_consultant) {
		this.admit_consultant = admit_consultant;
	}
	public String getConsult_clinician_name() {
		return consult_clinician_name;
	}
	public void setConsult_clinician_name(String consult_clinician_name) {
		this.consult_clinician_name = consult_clinician_name;
	}
	public String getStation_unit() {
		return station_unit;
	}
	public void setStation_unit(String station_unit) {
		this.station_unit = station_unit;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getAdmission_time() {
		return admission_time;
	}
	public void setAdmission_time(String admission_time) {
		this.admission_time = admission_time;
	}
	public String getDischarge_datetime() {
		return discharge_datetime;
	}
	public void setDischarge_datetime(String discharge_datetime) {
		this.discharge_datetime = discharge_datetime;
	}
}
