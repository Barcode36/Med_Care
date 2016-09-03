package com.Over2Cloud.ctrl.leaveManagement;

import java.util.List;

public class AttendancePojo 
{
	private int id;
	private String empname;
	private String date;
	private String intime;
	private String outtime;
	private String status;
	private String attendance;
	private String comment;
	private String extraworking;
	private String clientvisit;
	private String lupdate;
	private String lupdateto;
	private String present;
	private String absent;
	private String leavePrevious;
	private String leaveNext;
	private String fullCV;
	private String halfCV;
	private String fdate;
	private String tdate;
	private String holidayname;
	private String hbrief;
	private String day;
	private String month;
	private String call="0";
	private String mail="0";
	private String sms="0";
	private String vmn="0";
	private String nointimation="0";
	private String before;
	private String min15="0";
	private String min30="0";
	private String mon45="0";
	private String hour1="0";
	private String hour2="0";
	private String ename;
	private String data1;
	private String cf=null;
	private String lnextmonth=null;
	private String total_leaves=null;
	private double finalLeaveGranted=0.0;
	private double totalValue=0.0;
	private String CheckIn=null;
	private String CheckOut=null;
	private String empId=null;
	private String deptname=null;
	private String totalhour=null;
	private String mobile=null;
	private String emergencyNo=null;
	private String address=null;
	private String dob=null;
	private String doa=null;
	private String email_id=null;
	private String reason=null;
	private String totalday=null;
	private String autherized=null;
	private String odate;
	private String ftime;
	private String ttime;
	private String Keyword;
	private String description;
	private String update;
	
	
	 
	private List<AttendancePojo> detailList;
	
	
	
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getKeyword() {
		return Keyword;
	}
	public void setKeyword(String keyword) {
		Keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getExtraworking() {
		return extraworking;
	}
	public void setExtraworking(String extraworking) {
		this.extraworking = extraworking;
	}
	public String getClientvisit() {
		return clientvisit;
	}
	public void setClientvisit(String clientvisit) {
		this.clientvisit = clientvisit;
	}
	public String getLupdate() {
		return lupdate;
	}
	public void setLupdate(String lupdate) {
		this.lupdate = lupdate;
	}
	public String getLupdateto() {
		return lupdateto;
	}
	public void setLupdateto(String lupdateto) {
		this.lupdateto = lupdateto;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
	public String getAbsent() {
		return absent;
	}
	public void setAbsent(String absent) {
		this.absent = absent;
	}
	public String getLeavePrevious() {
		return leavePrevious;
	}
	public void setLeavePrevious(String leavePrevious) {
		this.leavePrevious = leavePrevious;
	}
	public String getLeaveNext() {
		return leaveNext;
	}
	public void setLeaveNext(String leaveNext) {
		this.leaveNext = leaveNext;
	}
	public List<AttendancePojo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<AttendancePojo> detailList) {
		this.detailList = detailList;
	}
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getHolidayname() {
		return holidayname;
	}
	public void setHolidayname(String holidayname) {
		this.holidayname = holidayname;
	}
	public String getHbrief() {
		return hbrief;
	}
	public void setHbrief(String hbrief) {
		this.hbrief = hbrief;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getFullCV() {
		return fullCV;
	}
	public void setFullCV(String fullCV) {
		this.fullCV = fullCV;
	}
	public String getHalfCV() {
		return halfCV;
	}
	public void setHalfCV(String halfCV) {
		this.halfCV = halfCV;
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getVmn() {
		return vmn;
	}
	public void setVmn(String vmn) {
		this.vmn = vmn;
	}
	public String getNointimation() {
		return nointimation;
	}
	public void setNointimation(String nointimation) {
		this.nointimation = nointimation;
	}
	public String getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public String getMin15() {
		return min15;
	}
	public void setMin15(String min15) {
		this.min15 = min15;
	}
	public String getMin30() {
		return min30;
	}
	public void setMin30(String min30) {
		this.min30 = min30;
	}
	public String getMon45() {
		return mon45;
	}
	public void setMon45(String mon45) {
		this.mon45 = mon45;
	}
	public String getHour1() {
		return hour1;
	}
	public void setHour1(String hour1) {
		this.hour1 = hour1;
	}
	public String getHour2() {
		return hour2;
	}
	public void setHour2(String hour2) {
		this.hour2 = hour2;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getLnextmonth() {
		return lnextmonth;
	}
	public void setLnextmonth(String lnextmonth) {
		this.lnextmonth = lnextmonth;
	}
	public String getTotal_leaves() {
		return total_leaves;
	}
	public void setTotal_leaves(String total_leaves) {
		this.total_leaves = total_leaves;
	}
	public double getFinalLeaveGranted() {
		return finalLeaveGranted;
	}
	public void setFinalLeaveGranted(double finalLeaveGranted) {
		this.finalLeaveGranted = finalLeaveGranted;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public String getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}
	public String getCheckOut() {
		return CheckOut;
	}
	public void setCheckOut(String checkOut) {
		CheckOut = checkOut;
	}
	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getDeptname()
	{
		return deptname;
	}
	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}
	public String getTotalhour()
	{
		return totalhour;
	}
	public void setTotalhour(String totalhour)
	{
		this.totalhour = totalhour;
	}
	public String getMobile()
	{
		return mobile;
	}
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	public String getEmergencyNo()
	{
		return emergencyNo;
	}
	public void setEmergencyNo(String emergencyNo)
	{
		this.emergencyNo = emergencyNo;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getDob()
	{
		return dob;
	}
	public void setDob(String dob)
	{
		this.dob = dob;
	}
	public String getDoa()
	{
		return doa;
	}
	public void setDoa(String doa)
	{
		this.doa = doa;
	}
	public String getEmail_id()
	{
		return email_id;
	}
	public void setEmail_id(String email_id)
	{
		this.email_id = email_id;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public String getTotalday()
	{
		return totalday;
	}
	public void setTotalday(String totalday)
	{
		this.totalday = totalday;
	}
	public String getAutherized()
	{
		return autherized;
	}
	public void setAutherized(String autherized)
	{
		this.autherized = autherized;
	}
	public String getOdate() {
		return odate;
	}
	public void setOdate(String odate) {
		this.odate = odate;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
	public String getTtime() {
		return ttime;
	}
	public void setTtime(String ttime) {
		this.ttime = ttime;
	}
	
	
}
