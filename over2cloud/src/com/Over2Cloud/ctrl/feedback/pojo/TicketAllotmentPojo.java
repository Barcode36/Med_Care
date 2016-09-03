package com.Over2Cloud.ctrl.feedback.pojo;

public class TicketAllotmentPojo
{
	private String empName="NA";
	private String totalPending;
	private String highPriority;
	private String snooze;
	private String ignore;
	private String reAsign;
	private String resolved;
	private String totalTickets="0";
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTotalPending() {
		return totalPending;
	}
	public void setTotalPending(String totalPending) {
		this.totalPending = totalPending;
	}
	public String getHighPriority() {
		return highPriority;
	}
	public void setHighPriority(String highPriority) {
		this.highPriority = highPriority;
	}
	public String getSnooze() {
		return snooze;
	}
	public void setSnooze(String snooze) {
		this.snooze = snooze;
	}
	public String getIgnore() {
		return ignore;
	}
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	public String getReAsign() {
		return reAsign;
	}
	public void setReAsign(String reAsign) {
		this.reAsign = reAsign;
	}
	public String getResolved() {
		return resolved;
	}
	public void setResolved(String resolved) {
		this.resolved = resolved;
	}
	public String getTotalTickets() {
		return totalTickets;
	}
	public void setTotalTickets(String totalTickets) {
		this.totalTickets = totalTickets;
	}
}
