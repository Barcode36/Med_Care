package com.Over2Cloud.ctrl.bedTransfer;

public class BedTransferPojo {
	
	
	private String id,patient_id,patient_name,contact_no,encounter_id,current_bed,current_nursuing_unit,req_date_time,requested_nursuing_unit,req_bed_class,req_bed_no,tfr_req_status;
	private String action_date;
	private String action_time;
	private String status;
	private String level;
	private String history_id;
	private String time_difference;
	private String cancel_by;
	private String comment; 
	
	
	
	public String getCancel_by()
	{
		return cancel_by;
	}

	public void setCancel_by(String cancelBy)
	{
		cancel_by = cancelBy;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction_date() {
		return action_date;
	}

	public void setAction_date(String action_date) {
		this.action_date = action_date;
	}

	public String getAction_time() {
		return action_time;
	}

	public void setAction_time(String action_time) {
		this.action_time = action_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHistory_id() {
		return history_id;
	}

	public void setHistory_id(String history_id) {
		this.history_id = history_id;
	}

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getEncounter_id() {
		return encounter_id;
	}

	public void setEncounter_id(String encounter_id) {
		this.encounter_id = encounter_id;
	}

	public String getCurrent_bed() {
		return current_bed;
	}

	public void setCurrent_bed(String current_bed) {
		this.current_bed = current_bed;
	}

	public String getCurrent_nursuing_unit() {
		return current_nursuing_unit;
	}

	public void setCurrent_nursuing_unit(String current_nursuing_unit) {
		this.current_nursuing_unit = current_nursuing_unit;
	}

	public String getReq_date_time() {
		return req_date_time;
	}

	public void setReq_date_time(String req_date_time) {
		this.req_date_time = req_date_time;
	}

	public String getRequested_nursuing_unit() {
		return requested_nursuing_unit;
	}

	public void setRequested_nursuing_unit(String requested_nursuing_unit) {
		this.requested_nursuing_unit = requested_nursuing_unit;
	}

	public String getReq_bed_class() {
		return req_bed_class;
	}

	public void setReq_bed_class(String req_bed_class) {
		this.req_bed_class = req_bed_class;
	}

	public String getReq_bed_no() {
		return req_bed_no;
	}

	public void setReq_bed_no(String req_bed_no) {
		this.req_bed_no = req_bed_no;
	}

	public String getTfr_req_status() {
		return tfr_req_status;
	}

	public void setTfr_req_status(String tfr_req_status) {
		this.tfr_req_status = tfr_req_status;
	}

	public String getTime_difference() {
		return time_difference;
	}

	public void setTime_difference(String time_difference) {
		this.time_difference = time_difference;
	}
	
	

}
