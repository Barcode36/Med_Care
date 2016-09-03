package com.Over2Cloud.ctrl.dischargeAnnounce;

public class DischargeAnnouncePojo {
	
	private String announce_id;
	private String status, action_by, action_at,allot_to,level,time_difference;


	
	//Setter Getter
	
	public String getAnnounce_id() {
		return announce_id;
	}

	public String getTime_difference() {
		return time_difference;
	}

	public void setTime_difference(String time_difference) {
		this.time_difference = time_difference;
	}

	public void setAnnounce_id(String announce_id) {
		this.announce_id = announce_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction_by() {
		return action_by;
	}

	public void setAction_by(String action_by) {
		this.action_by = action_by;
	}

	public String getAction_at() {
		return action_at;
	}

	public void setAction_at(String action_at) {
		this.action_at = action_at;
	}

	public String getAllot_to() {
		return allot_to;
	}

	public void setAllot_to(String allot_to) {
		this.allot_to = allot_to;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	

}
