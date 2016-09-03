package com.Over2Cloud.modal.db.commom;

public class NotificationPojo {

	private int id;
	private String taskName;
	private String taskBrief;
	private String flag="false";
	// Variables for Help Desk
	private String ticket_no;
	private String feed_by;
	private String feed_by_mobno;
	private String feed_by_emailid;
	private String feed_brief;
	private String open_date;
	private String open_time;
	private String subcatg;
	private String location;
	private String allot_to;
	private String escalation_date;
	private String escalation_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskBrief() {
		return taskBrief;
	}
	public void setTaskBrief(String taskBrief) {
		this.taskBrief = taskBrief;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(String ticket_no) {
		this.ticket_no = ticket_no;
	}
	public String getFeed_by() {
		return feed_by;
	}
	public void setFeed_by(String feed_by) {
		this.feed_by = feed_by;
	}
	public String getFeed_by_mobno() {
		return feed_by_mobno;
	}
	public void setFeed_by_mobno(String feed_by_mobno) {
		this.feed_by_mobno = feed_by_mobno;
	}
	public String getFeed_by_emailid() {
		return feed_by_emailid;
	}
	public void setFeed_by_emailid(String feed_by_emailid) {
		this.feed_by_emailid = feed_by_emailid;
	}
	public String getFeed_brief() {
		return feed_brief;
	}
	public void setFeed_brief(String feed_brief) {
		this.feed_brief = feed_brief;
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
	public String getSubcatg() {
		return subcatg;
	}
	public void setSubcatg(String subcatg) {
		this.subcatg = subcatg;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAllot_to() {
		return allot_to;
	}
	public void setAllot_to(String allot_to) {
		this.allot_to = allot_to;
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
	
}
