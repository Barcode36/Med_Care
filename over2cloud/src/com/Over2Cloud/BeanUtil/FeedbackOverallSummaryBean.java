package com.Over2Cloud.BeanUtil;
public class FeedbackOverallSummaryBean {
	private int id;
	private String mode;
	private String yesterdayYes;
	private String yesterdayNo;
	private String todayYes;
	private String todayNo;
	private String totalYes;
	private String totalNo;
	private String pending="0";
	private String positive="0";
	private String negative="0";
	private String noFurtherAction="0";
	private String ticketOpened="0";

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getYesterdayYes() {
		return yesterdayYes;
	}
	public void setYesterdayYes(String yesterdayYes) {
		this.yesterdayYes = yesterdayYes;
	}
	public String getYesterdayNo() {
		return yesterdayNo;
	}
	public void setYesterdayNo(String yesterdayNo) {
		this.yesterdayNo = yesterdayNo;
	}
	public String getTodayYes() {
		return todayYes;
	}
	public void setTodayYes(String todayYes) {
		this.todayYes = todayYes;
	}
	public String getTodayNo() {
		return todayNo;
	}
	public void setTodayNo(String todayNo) {
		this.todayNo = todayNo;
	}
	public String getTotalYes() {
		return totalYes;
	}
	public void setTotalYes(String totalYes) {
		this.totalYes = totalYes;
	}
	public String getTotalNo() {
		return totalNo;
	}
	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}
	public String getPending() {
		return pending;
	}
	public void setPending(String pending) {
		this.pending = pending;
	}
	public String getPositive() {
		return positive;
	}
	public void setPositive(String positive) {
		this.positive = positive;
	}
	public String getNegative() {
		return negative;
	}
	public void setNegative(String negative) {
		this.negative = negative;
	}
	public String getNoFurtherAction() {
		return noFurtherAction;
	}
	public void setNoFurtherAction(String noFurtherAction) {
		this.noFurtherAction = noFurtherAction;
	}
	public String getTicketOpened() {
		return ticketOpened;
	}
	public void setTicketOpened(String ticketOpened) {
		this.ticketOpened = ticketOpened;
	}

	}
