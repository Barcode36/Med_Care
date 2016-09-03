package com.Over2Cloud.helpdesk.BeanUtil;

public class FeedbackPojo {
	
	private int id;
	private String empId;
	private String empName;
	private String mobOne;
	private String emailIdOne;
	private String cr_no="NA";
	private String ticket_no;
	private String feedback_by_dept;
	private String feedback_by_subdept;
	private String feed_by;
	private String feedback_by_mobno;
	private String feedback_by_emailid;
	private String feedback_to_dept;
	private String feedback_to_subdept;
	private String feedback_catg;
	private String feedback_subcatg;
	private String feed_brief;
	private String feedaddressing_date;
	private String feedaddressing_time;
	private String feedesc_time;
	private String feedback_allot_to;
	private String escalation_date;
	private String escalation_time;
	private String open_date;
	private String open_time;
	private String location;
	private String level;
	private String status;
	private String via_from;
	private String feed_registerby;
	private String feedtype;
	private String allot_to_mobno;
	private String addressingTime;
	private String resolutionTime;
	
	private String reEmailIdOne;
	private String reMobOne;
	private String reAllotedBy;
	private String reAllotedTo;
	
	
	private String sn_date;
	private String sn_time;
	
	
	
	private String sn_upto_date;
	private String sn_upto_time;
	
	private String sn_duration;
	private String sn_reason;
	private String sn_on_date;
	private String sn_on_time;
	
	private String hp_date;
	private String hp_time;
	private String hp_reason;
	
	private String resolve_date;
	private String resolve_time;
	private String resolve_duration;
	private String resolve_by;
	private String resolve_by_mobno;
	private String resolve_by_emailid;
	private String action_by;
	private String resolve_remark;
	private String spare_used;
	
	private String ig_date;
	private String ig_time;
	private String ig_reason;
	
	private String transfer_date;
	private String transfer_time;
	private String transfer_reason;
	private String counter;
	private String feedback_remarks;
	
	private String asset;
	private String serialno;
	
	private String Addr_date_time;
	private String non_working_time;
	
	
	// Added for productivity
	private String graph="0";
	private String onTime="0";
	private String offTime="0";
	private String missed="0";
	private String snooze="0";
	private String perOnTime="0";
	private String perOffTime="0";
	private String perMissed="0";
	private String feedId="0";
	private String ignore="0";
	
	
	// Added for DREAM_HDm
	private String clientFor;
	private String clientName;
	private String offeringName;
	private String feed_cc;

	private String resolve_rca;
	private String resolve_capa;
	
	
	
	// For Feedback
	private String patTyp="NA";
	private String docName="NA";
	private String roomNo="NA";
	private String patEmailId="NA";
	private String patObsrvtn="NA";
	
	private String addr_Date_Time="NA";
	private String patMobNo="NA";
	private String actionComments="NA";
	private String action_by_mob;
	private String pre_allot_to;
	private String pre_allot_to_mob;
	
	private String reassign_date;
	private String reassign_time;
	private String reassign_reason;
	
	private String reopen_date;
	private String reopen_time;
	private String reopen_reason;
	
	private String pending_date;
	private String pending_time;
	private String pending_reason;
	
	private String reportFor;
	private String reportRange;
	private String historyStatus;
	private String escalation_level;
	private String grand;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getMobOne() {
		return mobOne;
	}
	public void setMobOne(String mobOne) {
		this.mobOne = mobOne;
	}
	public String getEmailIdOne() {
		return emailIdOne;
	}
	public void setEmailIdOne(String emailIdOne) {
		this.emailIdOne = emailIdOne;
	}
	public String getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(String ticket_no) {
		this.ticket_no = ticket_no;
	}
	public String getFeedback_by_dept() {
		return feedback_by_dept;
	}
	public void setFeedback_by_dept(String feedback_by_dept) {
		this.feedback_by_dept = feedback_by_dept;
	}
	public String getFeedback_by_subdept() {
		return feedback_by_subdept;
	}
	public void setFeedback_by_subdept(String feedback_by_subdept) {
		this.feedback_by_subdept = feedback_by_subdept;
	}
	
	public String getFeed_by() {
		return feed_by;
	}
	public void setFeed_by(String feed_by) {
		this.feed_by = feed_by;
	}
	public String getFeed_brief() {
		return feed_brief;
	}
	public void setFeed_brief(String feed_brief) {
		this.feed_brief = feed_brief;
	}
	public String getFeedback_by_mobno() {
		return feedback_by_mobno;
	}
	public void setFeedback_by_mobno(String feedback_by_mobno) {
		this.feedback_by_mobno = feedback_by_mobno;
	}
	public String getFeedback_by_emailid() {
		return feedback_by_emailid;
	}
	public void setFeedback_by_emailid(String feedback_by_emailid) {
		this.feedback_by_emailid = feedback_by_emailid;
	}
	public String getFeedback_to_dept() {
		return feedback_to_dept;
	}
	public void setFeedback_to_dept(String feedback_to_dept) {
		this.feedback_to_dept = feedback_to_dept;
	}
	public String getFeedback_to_subdept() {
		return feedback_to_subdept;
	}
	public void setFeedback_to_subdept(String feedback_to_subdept) {
		this.feedback_to_subdept = feedback_to_subdept;
	}
	public String getFeedback_catg() {
		return feedback_catg;
	}
	public void setFeedback_catg(String feedback_catg) {
		this.feedback_catg = feedback_catg;
	}
	public String getFeedback_subcatg() {
		return feedback_subcatg;
	}
	public void setFeedback_subcatg(String feedback_subcatg) {
		this.feedback_subcatg = feedback_subcatg;
	}
	
	public String getFeedaddressing_time() {
		return feedaddressing_time;
	}
	public void setFeedaddressing_time(String feedaddressing_time) {
		this.feedaddressing_time = feedaddressing_time;
	}
	public String getFeedesc_time() {
		return feedesc_time;
	}
	public void setFeedesc_time(String feedesc_time) {
		this.feedesc_time = feedesc_time;
	}
	public String getFeedback_allot_to() {
		return feedback_allot_to;
	}
	public void setFeedback_allot_to(String feedback_allot_to) {
		this.feedback_allot_to = feedback_allot_to;
	}
	public String getEscalation_date() {
		return escalation_date;
	}
	public void setEscalation_date(String escalation_date) {
		this.escalation_date = escalation_date;
	}
	public String getEscalation_time() {
		return escalation_time;
	}
	public void setEscalation_time(String escalation_time) {
		this.escalation_time = escalation_time;
	}
	public String getOpen_date() {
		return open_date;
	}
	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}
	public String getOpen_time() {
		return open_time;
	}
	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVia_from() {
		return via_from;
	}
	public void setVia_from(String via_from) {
		this.via_from = via_from;
	}
	public String getFeed_registerby() {
		return feed_registerby;
	}
	public void setFeed_registerby(String feed_registerby) {
		this.feed_registerby = feed_registerby;
	}
	public String getFeedtype() {
		return feedtype;
	}
	public void setFeedtype(String feedtype) {
		this.feedtype = feedtype;
	}
	public String getSn_upto_date() {
		return sn_upto_date;
	}
	public void setSn_upto_date(String sn_upto_date) {
		this.sn_upto_date = sn_upto_date;
	}
	public String getSn_upto_time() {
		return sn_upto_time;
	}
	public void setSn_upto_time(String sn_upto_time) {
		this.sn_upto_time = sn_upto_time;
	}
	public String getSn_duration() {
		return sn_duration;
	}
	public void setSn_duration(String sn_duration) {
		this.sn_duration = sn_duration;
	}
	public String getSn_reason() {
		return sn_reason;
	}
	public void setSn_reason(String sn_reason) {
		this.sn_reason = sn_reason;
	}
	public String getSn_on_date() {
		return sn_on_date;
	}
	public void setSn_on_date(String sn_on_date) {
		this.sn_on_date = sn_on_date;
	}
	public String getSn_on_time() {
		return sn_on_time;
	}
	public void setSn_on_time(String sn_on_time) {
		this.sn_on_time = sn_on_time;
	}
	public String getHp_date() {
		return hp_date;
	}
	public void setHp_date(String hp_date) {
		this.hp_date = hp_date;
	}
	public String getHp_time() {
		return hp_time;
	}
	public void setHp_time(String hp_time) {
		this.hp_time = hp_time;
	}
	public String getHp_reason() {
		return hp_reason;
	}
	public void setHp_reason(String hp_reason) {
		this.hp_reason = hp_reason;
	}
	public String getFeedaddressing_date() {
		return feedaddressing_date;
	}
	public void setFeedaddressing_date(String feedaddressing_date) {
		this.feedaddressing_date = feedaddressing_date;
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
	public String getResolve_remark() {
		return resolve_remark;
	}
	public void setResolve_remark(String resolve_remark) {
		this.resolve_remark = resolve_remark;
	}
	public String getAction_by() {
		return action_by;
	}
	public void setAction_by(String action_by) {
		this.action_by = action_by;
	}
	public String getResolve_duration() {
		return resolve_duration;
	}
	public void setResolve_duration(String resolve_duration) {
		this.resolve_duration = resolve_duration;
	}
	public String getResolve_by() {
		return resolve_by;
	}
	public void setResolve_by(String resolve_by) {
		this.resolve_by = resolve_by;
	}
	public String getSpare_used() {
		return spare_used;
	}
	public void setSpare_used(String spare_used) {
		this.spare_used = spare_used;
	}
	public String getIg_date() {
		return ig_date;
	}
	public void setIg_date(String ig_date) {
		this.ig_date = ig_date;
	}
	public String getIg_time() {
		return ig_time;
	}
	public void setIg_time(String ig_time) {
		this.ig_time = ig_time;
	}
	public String getIg_reason() {
		return ig_reason;
	}
	public void setIg_reason(String ig_reason) {
		this.ig_reason = ig_reason;
	}
	public String getTransfer_date() {
		return transfer_date;
	}
	public void setTransfer_date(String transfer_date) {
		this.transfer_date = transfer_date;
	}
	public String getTransfer_time() {
		return transfer_time;
	}
	public void setTransfer_time(String transfer_time) {
		this.transfer_time = transfer_time;
	}
	public String getTransfer_reason() {
		return transfer_reason;
	}
	public void setTransfer_reason(String transfer_reason) {
		this.transfer_reason = transfer_reason;
	}
	public String getAllot_to_mobno() {
		return allot_to_mobno;
	}
	public void setAllot_to_mobno(String allot_to_mobno) {
		this.allot_to_mobno = allot_to_mobno;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public String getResolve_by_mobno() {
		return resolve_by_mobno;
	}
	public void setResolve_by_mobno(String resolve_by_mobno) {
		this.resolve_by_mobno = resolve_by_mobno;
	}
	public String getResolve_by_emailid() {
		return resolve_by_emailid;
	}
	public void setResolve_by_emailid(String resolve_by_emailid) {
		this.resolve_by_emailid = resolve_by_emailid;
	}
	public String getFeedback_remarks() {
		return feedback_remarks;
	}
	public void setFeedback_remarks(String feedback_remarks) {
		this.feedback_remarks = feedback_remarks;
	}
	public String getAddressingTime() {
		return addressingTime;
	}
	public void setAddressingTime(String addressingTime) {
		this.addressingTime = addressingTime;
	}
	public String getResolutionTime() {
		return resolutionTime;
	}
	public void setResolutionTime(String resolutionTime) {
		this.resolutionTime = resolutionTime;
	}
	public String getAsset()
	{
		return asset;
	}
	public void setAsset(String asset)
	{
		this.asset = asset;
	}
	public String getSerialno()
	{
		return serialno;
	}
	public void setSerialno(String serialno)
	{
		this.serialno = serialno;
	}
	public String getGraph() {
		return graph;
	}
	public void setGraph(String graph) {
		this.graph = graph;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	public String getMissed() {
		return missed;
	}
	public void setMissed(String missed) {
		this.missed = missed;
	}
	public String getSnooze() {
		return snooze;
	}
	public void setSnooze(String snooze) {
		this.snooze = snooze;
	}
	public String getPerOnTime() {
		return perOnTime;
	}
	public void setPerOnTime(String perOnTime) {
		this.perOnTime = perOnTime;
	}
	public String getPerOffTime() {
		return perOffTime;
	}
	public void setPerOffTime(String perOffTime) {
		this.perOffTime = perOffTime;
	}
	public String getPerMissed() {
		return perMissed;
	}
	public void setPerMissed(String perMissed) {
		this.perMissed = perMissed;
	}
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public String getIgnore() {
		return ignore;
	}
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	public String getSn_date() {
		return sn_date;
	}
	public void setSn_date(String sn_date) {
		this.sn_date = sn_date;
	}
	public String getSn_time() {
		return sn_time;
	}
	public void setSn_time(String sn_time) {
		this.sn_time = sn_time;
	}
	public String getAddr_date_time() {
		return Addr_date_time;
	}
	public void setAddr_date_time(String addr_date_time) {
		Addr_date_time = addr_date_time;
	}
	public String getNon_working_time() {
		return non_working_time;
	}
	public void setNon_working_time(String non_working_time) {
		this.non_working_time = non_working_time;
	}
	public String getClientFor() {
		return clientFor;
	}
	public void setClientFor(String clientFor) {
		this.clientFor = clientFor;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public String getFeed_cc() {
		return feed_cc;
	}
	public void setFeed_cc(String feed_cc) {
		this.feed_cc = feed_cc;
	}
	public String getResolve_rca() {
		return resolve_rca;
	}
	public void setResolve_rca(String resolve_rca) {
		this.resolve_rca = resolve_rca;
	}
	public String getResolve_capa() {
		return resolve_capa;
	}
	public void setResolve_capa(String resolve_capa) {
		this.resolve_capa = resolve_capa;
	}
	public String getCr_no() {
		return cr_no;
	}
	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
	}
	public String getPatTyp() {
		return patTyp;
	}
	public void setPatTyp(String patTyp) {
		this.patTyp = patTyp;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getPatEmailId() {
		return patEmailId;
	}
	public void setPatEmailId(String patEmailId) {
		this.patEmailId = patEmailId;
	}
	public String getPatObsrvtn() {
		return patObsrvtn;
	}
	public void setPatObsrvtn(String patObsrvtn) {
		this.patObsrvtn = patObsrvtn;
	}
	public String getAddr_Date_Time() {
		return addr_Date_Time;
	}
	public void setAddr_Date_Time(String addr_Date_Time) {
		this.addr_Date_Time = addr_Date_Time;
	}
	public String getActionComments() {
		return actionComments;
	}
	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}
	public String getPatMobNo() {
		return patMobNo;
	}
	public void setPatMobNo(String patMobNo) {
		this.patMobNo = patMobNo;
	}
	public String getReEmailIdOne() {
		return reEmailIdOne;
	}
	public void setReEmailIdOne(String reEmailIdOne) {
		this.reEmailIdOne = reEmailIdOne;
	}
	public String getReMobOne() {
		return reMobOne;
	}
	public void setReMobOne(String reMobOne) {
		this.reMobOne = reMobOne;
	}
	public String getReAllotedBy() {
		return reAllotedBy;
	}
	public void setReAllotedBy(String reAllotedBy) {
		this.reAllotedBy = reAllotedBy;
	}
	public String getReAllotedTo() {
		return reAllotedTo;
	}
	public void setReAllotedTo(String reAllotedTo) {
		this.reAllotedTo = reAllotedTo;
	}
	public String getAction_by_mob() {
		return action_by_mob;
	}
	public void setAction_by_mob(String action_by_mob) {
		this.action_by_mob = action_by_mob;
	}
	public String getPre_allot_to() {
		return pre_allot_to;
	}
	public void setPre_allot_to(String pre_allot_to) {
		this.pre_allot_to = pre_allot_to;
	}
	public String getPre_allot_to_mob() {
		return pre_allot_to_mob;
	}
	public void setPre_allot_to_mob(String pre_allot_to_mob) {
		this.pre_allot_to_mob = pre_allot_to_mob;
	}
	public String getReassign_date() {
		return reassign_date;
	}
	public void setReassign_date(String reassign_date) {
		this.reassign_date = reassign_date;
	}
	public String getReassign_time() {
		return reassign_time;
	}
	public void setReassign_time(String reassign_time) {
		this.reassign_time = reassign_time;
	}
	public String getReassign_reason() {
		return reassign_reason;
	}
	public void setReassign_reason(String reassign_reason) {
		this.reassign_reason = reassign_reason;
	}
	public String getReopen_date() {
		return reopen_date;
	}
	public void setReopen_date(String reopen_date) {
		this.reopen_date = reopen_date;
	}
	public String getReopen_time() {
		return reopen_time;
	}
	public void setReopen_time(String reopen_time) {
		this.reopen_time = reopen_time;
	}
	public String getReopen_reason() {
		return reopen_reason;
	}
	public void setReopen_reason(String reopen_reason) {
		this.reopen_reason = reopen_reason;
	}
	public String getReportFor() {
		return reportFor;
	}
	public void setReportFor(String reportFor) {
		this.reportFor = reportFor;
	}
	public String getReportRange() {
		return reportRange;
	}
	public void setReportRange(String reportRange) {
		this.reportRange = reportRange;
	}
	public String getHistoryStatus() {
		return historyStatus;
	}
	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}
	public String getPending_date() {
		return pending_date;
	}
	public void setPending_date(String pending_date) {
		this.pending_date = pending_date;
	}
	public String getPending_time() {
		return pending_time;
	}
	public void setPending_time(String pending_time) {
		this.pending_time = pending_time;
	}
	public String getPending_reason() {
		return pending_reason;
	}
	public void setPending_reason(String pending_reason) {
		this.pending_reason = pending_reason;
	}
	public String getEscalation_level() {
		return escalation_level;
	}
	public void setEscalation_level(String escalation_level) {
		this.escalation_level = escalation_level;
	}
	public String getGrand() {
		return grand;
	}
	public void setGrand(String grand) {
		this.grand = grand;
	}
	
}
