package com.Over2Cloud.ctrl.feedback.beanUtil;

public class PatientReportPojo {

	private int id;
	private String patType="NA";
	private String patientId="NA";
	private String patientName="NA";
	private String purpVisit="NA";
	private String reverted="No";
	private String satisfaction="NA";
	private String actionTaken="NA";
	private String actionForDept="NA";
	private String visitDateTime="NA";
	private String dischargeDateTime="NA";
	private String roomNo="NA";
	private String mobNo="NA";
	private String emailId="NA";
	private String smsStatus="No";
	private String revertStatus="NA";
	private String revertDate="NA";
	private String revertTime="NA";
	private String status="NA";
	private String feedback_remarks="NA";
	private String ticketNo="NA";
	private String problem="NA";
	private String feedback_to_dept="NA";
	private String feedback_allot_to="NA";
	private String level="NA";
	private String allotTo="NA";
	private String resolve_date="NA";
	private String resolve_time="NA";
	private String rca="NA";
	private String capa="NA";
	private String subCategoryName="NA";
	private String docName;
	
	private String mode;
	private String openDate;
	private String openTime;
	private String patMobNo;
	private String patEmailId;
	private String feedRegisterBy;
	private String resolveDate;
	private String resolveTime;
	private String resolveBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPatType() {
		return patType;
	}
	public void setPatType(String patType) {
		this.patType = patType;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPurpVisit() {
		return purpVisit;
	}
	public void setPurpVisit(String purpVisit) {
		this.purpVisit = purpVisit;
	}
	public String getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	public String getReverted() {
		return reverted;
	}
	public void setReverted(String reverted) {
		this.reverted = reverted;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getActionForDept() {
		return actionForDept;
	}
	public void setActionForDept(String actionForDept) {
		this.actionForDept = actionForDept;
	}
	public String getRca() {
		return rca;
	}
	public void setRca(String rca) {
		this.rca = rca;
	}
	public String getCapa() {
		return capa;
	}
	public void setCapa(String capa) {
		this.capa = capa;
	}
	public String getVisitDateTime() {
		return visitDateTime;
	}
	public void setVisitDateTime(String visitDateTime) {
		this.visitDateTime = visitDateTime;
	}
	public String getDischargeDateTime() {
		return dischargeDateTime;
	}
	public void setDischargeDateTime(String dischargeDateTime) {
		this.dischargeDateTime = dischargeDateTime;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getRevertStatus() {
		return revertStatus;
	}
	public void setRevertStatus(String revertStatus) {
		this.revertStatus = revertStatus;
	}
	public String getRevertDate() {
		return revertDate;
	}
	public void setRevertDate(String revertDate) {
		this.revertDate = revertDate;
	}
	public String getRevertTime() {
		return revertTime;
	}
	public void setRevertTime(String revertTime) {
		this.revertTime = revertTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFeedback_remarks() {
		return feedback_remarks;
	}
	public void setFeedback_remarks(String feedback_remarks) {
		this.feedback_remarks = feedback_remarks;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getFeedback_to_dept() {
		return feedback_to_dept;
	}
	public void setFeedback_to_dept(String feedback_to_dept) {
		this.feedback_to_dept = feedback_to_dept;
	}
	public String getFeedback_allot_to() {
		return feedback_allot_to;
	}
	public void setFeedback_allot_to(String feedback_allot_to) {
		this.feedback_allot_to = feedback_allot_to;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getResolve_date() {
		return resolve_date;
	}
	public void setResolve_date(String resolve_date) {
		this.resolve_date = resolve_date;
	}
	public String getResolve_time() {
		return resolve_time;
	}
	public void setResolve_time(String resolve_time) {
		this.resolve_time = resolve_time;
	}
	public String getAllotTo() {
		return allotTo;
	}
	public void setAllotTo(String allotTo) {
		this.allotTo = allotTo;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getPatMobNo() {
		return patMobNo;
	}
	public void setPatMobNo(String patMobNo) {
		this.patMobNo = patMobNo;
	}
	public String getPatEmailId() {
		return patEmailId;
	}
	public void setPatEmailId(String patEmailId) {
		this.patEmailId = patEmailId;
	}
	public String getFeedRegisterBy() {
		return feedRegisterBy;
	}
	public void setFeedRegisterBy(String feedRegisterBy) {
		this.feedRegisterBy = feedRegisterBy;
	}
	public String getResolveDate() {
		return resolveDate;
	}
	public void setResolveDate(String resolveDate) {
		this.resolveDate = resolveDate;
	}
	public String getResolveTime() {
		return resolveTime;
	}
	public void setResolveTime(String resolveTime) {
		this.resolveTime = resolveTime;
	}
	public String getResolveBy() {
		return resolveBy;
	}
	public void setResolveBy(String resolveBy) {
		this.resolveBy = resolveBy;
	}
}
