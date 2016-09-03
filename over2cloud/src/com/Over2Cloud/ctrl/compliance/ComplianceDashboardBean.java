package com.Over2Cloud.ctrl.compliance;

import java.util.List;

public class ComplianceDashboardBean {
	
	private String dueWeekly;
	private String dueMonthly;
	private String dueYearly;
	private String departName;
	private String departId;
	private String annualTotal="0";
	private String annualMissed="0";
	private String monthlyTotal="0";
	private String monthlyMissed="0";
	private String weeklyTotal="0";
	private String weeklyMissed="0";
	private String taskName;
	private String frequency;
	private String taskBrief;
	private String compId;
	private String dueDate="0";
	private String dueTime;
	private String remindTo;
	private String actionStatus;
	private String pending="0";
	private String done="0";
	private String missed="0";
	private String snooze="0";
	private String currentEscLevel="No Esc";
	private String esc1Count="0";
	private String esc2Count="0";
	private String esc3Count="0";
	private String esc4Count="0";
	private String esc5Count="0";
	private String taskType;
    private String mappedTeam;
    private String empId;
    private String actionType;
    private String comment;
    private String actionTakenOn="NA";
    private String actionBy="NA";
    private String lastActionComment="NA";
    private String minDate;
    private String maxDate;
    private String selfRem1="0";
    private String selfRem2="0";
    private String selfRem3="0";
    private String selfDueDate="0";
    private String teamRem1="0";
    private String teamRem2="0";
    private String teamRem3="0";
    private String teamDueDate="0";
    private String selfDueCompliance="0";
    private String selfRem1Compliance="0";
    private String selfRem2Compliance="0"; 
    private String selfRem3Compliance="0";
    private String teamDueCompliance="0";
    private String teamRem1Compliance="0";
    private String teamRem2Compliance="0";
    private String teamRem3Compliance="0";
    private String selfCompliance="0";
    private String name ;
    private String totalTask = "0";
    private String onTime  = "0";
    private String offTime = "0";
    private String perOnTime = "0";
    private String perOffTime  = "0";
    private String perMissed = "0";
    private String graph;
    private String taskId;
    private String id;
    private String docPath;
    private String docPath1;
    private String docPath2;
    private String orginalDocPath;
    private String cost;
    private String mobNo;
    private String emailId;
    private String budgeted;
    private String actual;
    private String difference;
    
  
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
	private List<ComplianceDashboardBean> complList;
	
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public List<ComplianceDashboardBean> getComplList() {
		return complList;
	}
	public void setComplList(List<ComplianceDashboardBean> complList) {
		this.complList = complList;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getDueWeekly() {
		return dueWeekly;
	}
	public void setDueWeekly(String dueWeekly) {
		this.dueWeekly = dueWeekly;
	}
	public String getDueMonthly() {
		return dueMonthly;
	}
	public void setDueMonthly(String dueMonthly) {
		this.dueMonthly = dueMonthly;
	}
	public String getDueYearly() {
		return dueYearly;
	}
	public void setDueYearly(String dueYearly) {
		this.dueYearly = dueYearly;
	}
	public String getAnnualTotal() {
		return annualTotal;
	}
	public void setAnnualTotal(String annualTotal) {
		this.annualTotal = annualTotal;
	}
	public String getAnnualMissed() {
		return annualMissed;
	}
	public void setAnnualMissed(String annualMissed) {
		this.annualMissed = annualMissed;
	}
	public String getMonthlyTotal() {
		return monthlyTotal;
	}
	public void setMonthlyTotal(String monthlyTotal) {
		this.monthlyTotal = monthlyTotal;
	}
	public String getMonthlyMissed() {
		return monthlyMissed;
	}
	public void setMonthlyMissed(String monthlyMissed) {
		this.monthlyMissed = monthlyMissed;
	}
	public String getWeeklyTotal() {
		return weeklyTotal;
	}
	public void setWeeklyTotal(String weeklyTotal) {
		this.weeklyTotal = weeklyTotal;
	}
	public String getWeeklyMissed() {
		return weeklyMissed;
	}
	public void setWeeklyMissed(String weeklyMissed) {
		this.weeklyMissed = weeklyMissed;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getTaskBrief() {
		return taskBrief;
	}
	public void setTaskBrief(String taskBrief) {
		this.taskBrief = taskBrief;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getRemindTo() {
		return remindTo;
	}
	public void setRemindTo(String remindTo) {
		this.remindTo = remindTo;
	}
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	public String getPending() {
		return pending;
	}
	public void setPending(String pending) {
		this.pending = pending;
	}
	public String getDone() {
		return done;
	}
	public void setDone(String done) {
		this.done = done;
	}
	public String getMissed() {
		return missed;
	}
	public void setMissed(String missed) {
		this.missed = missed;
	}
	public String getCurrentEscLevel() {
		return currentEscLevel;
	}
	public void setCurrentEscLevel(String currentEscLevel) {
		this.currentEscLevel = currentEscLevel;
	}
	public String getEsc1Count() {
		return esc1Count;
	}
	public void setEsc1Count(String esc1Count) {
		this.esc1Count = esc1Count;
	}
	public String getEsc2Count() {
		return esc2Count;
	}
	public void setEsc2Count(String esc2Count) {
		this.esc2Count = esc2Count;
	}
	public String getEsc3Count() {
		return esc3Count;
	}
	public void setEsc3Count(String esc3Count) {
		this.esc3Count = esc3Count;
	}
	public String getEsc4Count() {
		return esc4Count;
	}
	public void setEsc4Count(String esc4Count) {
		this.esc4Count = esc4Count;
	}
	public String getEsc5Count() {
		return esc5Count;
	}
	public void setEsc5Count(String esc5Count) {
		this.esc5Count = esc5Count;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getMappedTeam() {
		return mappedTeam;
	}
	public void setMappedTeam(String mappedTeam) {
		this.mappedTeam = mappedTeam;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getActionTakenOn() {
		return actionTakenOn;
	}
	public void setActionTakenOn(String actionTakenOn) {
		this.actionTakenOn = actionTakenOn;
	}
	public String getActionBy() {
		return actionBy;
	}
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}
	public String getLastActionComment() {
		return lastActionComment;
	}
	public void setLastActionComment(String lastActionComment) {
		this.lastActionComment = lastActionComment;
	}
	public String getSnooze() {
		return snooze;
	}
	public void setSnooze(String snooze) {
		this.snooze = snooze;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public String getSelfRem1() {
		return selfRem1;
	}
	public void setSelfRem1(String selfRem1) {
		this.selfRem1 = selfRem1;
	}
	public String getSelfRem2() {
		return selfRem2;
	}
	public void setSelfRem2(String selfRem2) {
		this.selfRem2 = selfRem2;
	}
	public String getSelfRem3() {
		return selfRem3;
	}
	public void setSelfRem3(String selfRem3) {
		this.selfRem3 = selfRem3;
	}
	public String getTeamRem1() {
		return teamRem1;
	}
	public void setTeamRem1(String teamRem1) {
		this.teamRem1 = teamRem1;
	}
	public String getTeamRem2() {
		return teamRem2;
	}
	public void setTeamRem2(String teamRem2) {
		this.teamRem2 = teamRem2;
	}
	public String getTeamRem3() {
		return teamRem3;
	}
	public void setTeamRem3(String teamRem3) {
		this.teamRem3 = teamRem3;
	}
	public String getTeamDueDate() {
		return teamDueDate;
	}
	public void setTeamDueDate(String teamDueDate) {
		this.teamDueDate = teamDueDate;
	}
	public String getSelfDueDate() {
		return selfDueDate;
	}
	public void setSelfDueDate(String selfDueDate) {
		this.selfDueDate = selfDueDate;
	}
	public String getSelfDueCompliance() {
		return selfDueCompliance;
	}
	public void setSelfDueCompliance(String selfDueCompliance) {
		this.selfDueCompliance = selfDueCompliance;
	}
	public String getSelfRem1Compliance() {
		return selfRem1Compliance;
	}
	public void setSelfRem1Compliance(String selfRem1Compliance) {
		this.selfRem1Compliance = selfRem1Compliance;
	}
	public String getSelfRem2Compliance() {
		return selfRem2Compliance;
	}
	public void setSelfRem2Compliance(String selfRem2Compliance) {
		this.selfRem2Compliance = selfRem2Compliance;
	}
	public String getSelfRem3Compliance() {
		return selfRem3Compliance;
	}
	public void setSelfRem3Compliance(String selfRem3Compliance) {
		this.selfRem3Compliance = selfRem3Compliance;
	}
	public String getTeamDueCompliance() {
		return teamDueCompliance;
	}
	public void setTeamDueCompliance(String teamDueCompliance) {
		this.teamDueCompliance = teamDueCompliance;
	}
	public String getTeamRem1Compliance() {
		return teamRem1Compliance;
	}
	public void setTeamRem1Compliance(String teamRem1Compliance) {
		this.teamRem1Compliance = teamRem1Compliance;
	}
	public String getTeamRem2Compliance() {
		return teamRem2Compliance;
	}
	public void setTeamRem2Compliance(String teamRem2Compliance) {
		this.teamRem2Compliance = teamRem2Compliance;
	}
	public String getTeamRem3Compliance() {
		return teamRem3Compliance;
	}
	public void setTeamRem3Compliance(String teamRem3Compliance) {
		this.teamRem3Compliance = teamRem3Compliance;
	}
	public String getSelfCompliance() {
		return selfCompliance;
	}
	public void setSelfCompliance(String selfCompliance) {
		this.selfCompliance = selfCompliance;
	}
	public String getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(String totalTask) {
		this.totalTask = totalTask;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGraph() {
		return graph;
	}
	public void setGraph(String graph) {
		this.graph = graph;
	}
	public String getTaskId()
	{
		return taskId;
	}
	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getDocPath()
	{
		return docPath;
	}
	public void setDocPath(String docPath)
	{
		this.docPath = docPath;
	}
	public String getOrginalDocPath()
	{
		return orginalDocPath;
	}
	public void setOrginalDocPath(String orginalDocPath)
	{
		this.orginalDocPath = orginalDocPath;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDocPath1() {
		return docPath1;
	}
	public void setDocPath1(String docPath1) {
		this.docPath1 = docPath1;
	}
	public String getBudgeted() {
		return budgeted;
	}
	public void setBudgeted(String budgeted) {
		this.budgeted = budgeted;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getDifference() {
		return difference;
	}
	public void setDifference(String difference) {
		this.difference = difference;
	}
	public String getDocPath2()
	{
		return docPath2;
	}
	public void setDocPath2(String docPath2)
	{
		this.docPath2 = docPath2;
	}
	
}
