package com.Over2Cloud.ctrl.feedback.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackReportPojo;
import com.Over2Cloud.ctrl.feedback.beanUtil.PatientReportPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;

public class FeedbackHelper
{
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private static FeedbackUniversalHelper FUA = new FeedbackUniversalHelper();

	public String getCustIdFromFeed(String feedId, SessionFactory connectionSpace)
	{
	String custId = null;
	List data = cbt.executeAllSelectQuery("select clientId from feedback_status where id='" + feedId + "'", connectionSpace);
	if (data != null && data.size() > 0)
	{
	if (data.get(0) != null)
	{
	return data.get(0).toString();
	}
	else
	{
	return "NA";
	}
	}
	return custId;
	}

	public String getConfigMailForReport(int pc, int hc, int sc, int ic, int rc, int total, int cfpc, int cfsc, int cfhc, int cftotal, int totalSnooze, String reportLevel, List<FeedbackReportPojo> FRP, String deptComment, String report_type, FeedbackReportPojo FRP4Counter, String empname, List<FeedbackPojo> currentDayResolvedData, List<FeedbackPojo> currentDayPendingData, List<FeedbackPojo> currentDaySnoozeData, List<FeedbackPojo> currentDayHPData, List<FeedbackPojo> currentDayIgData,
	List<FeedbackPojo> CFData, String ip, int cfp_Total, int cd_Total, int cd_Pending, int total_snooze, int CDR_Total)
	{
	String url = "<a href=http://" + ip + "/over2cloud>click here</a>";

	StringBuilder mailtext = new StringBuilder("");
	// mailtext.append(
	// "<br><br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	// +orgName+"</b></td></tr></tbody></table>");
	// mailtext.append(
	// "<table width='100%' align='left' style='border: 0'><tbody><tr><td align='left' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody></table>"
	// );
	// mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
	// mailtext.append("<br><br><b>Hello!!!</b>");
	// mailtext.append(
	// "<br><br>Please find the following summary snapshot mapped for your analysis. You may find more details in the attached excel or for dynamic graphical analysis we request, you to please "
	// +url+" and login with your respective credentials.<br>");
	mailtext.append("<br><br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Patient Feedback Analyses Report for " + DateUtil.getCurrentDateIndianFormat() + " At " + DateUtil.getCurrentTime().substring(0, 5) + "</b></td></tr></tbody></table>");
	// mailtext.append("<br>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	mailtext.append("<tr  bgcolor='#848482'><td colspan='5' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Status By Various Modes</b></td></tr>");

	if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("D"))
	{
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Resolved</b></td></tr>");
	}
	else if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("W"))
	{
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Resolved</b></td></tr>");
	}
	else if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("M"))
	{
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Resolved</b></td></tr>");
	}
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + cfpc + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + total + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pc
	+ "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + totalSnooze + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + rc + "</td></tr>");
	mailtext.append("</tbody></table>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("D"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Feedback Summary Status for All Departments by All Modes</b></td></tr>");
	}
	else if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("W"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Feedback Summary Status for All Departments by All Modes</b></td></tr>");
	}
	else if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("M"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Feedback Summary Status for All Departments by All Modes</b></td></tr>");
	}

	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;SMS&nbsp;Mode</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Feedback&nbsp;Seek</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	+ FRP4Counter.getSt() + "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FRP4Counter.getSnr()
	+ "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='left' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FRP4Counter.getSn() + "</b></td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;Paper&nbsp;Mode</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Received</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	+ FRP4Counter.getPt() + "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FRP4Counter.getPp()
	+ "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='left' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FRP4Counter.getPn() + "</b></td></tr>");
	mailtext.append("</tbody></table>");

	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	mailtext.append("<tr  bgcolor='#848482'><td colspan='6' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Summary Status for All Departments by All Modes</b></td></tr>");
	if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("D"))
	{
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Resolved</b></td></tr>");
	}
	else if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("W"))
	{
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week  Resolved</b></td></tr>");
	}
	else if (report_type != null && !report_type.equals("") && report_type.equalsIgnoreCase("M"))
	{
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Resolved</b></td></tr>");
	}

	for (FeedbackReportPojo FRPObj : FRP)
	{
	mailtext
	.append("<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FRPObj.getDeptName() + "</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FRPObj.getCFP() + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FRPObj.getCDT() + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FRPObj.getCDP() + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FRPObj.getTS() + "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FRPObj.getCDR()
	+ "</td></tr>");
	}
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + cfp_Total + "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + cd_Total
	+ "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + cd_Pending + "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + total_snooze + "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + CDR_Total + "</b></td></tr>");
	mailtext.append("</tbody></table>");
	mailtext.append("<br></br>");

	}

	if (currentDayResolvedData != null && currentDayResolvedData.size() > 0)
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved Patient Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved Patient Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}

	int i = 0;
	for (FeedbackPojo FBP : currentDayResolvedData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	else
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}
	mailtext.append("</tbody></table>");
	}

	if (currentDayPendingData != null && currentDayPendingData.size() > 0)
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pending Tickets Patient Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pending Tickets Patient Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}

	int i = 0;
	for (FeedbackPojo FBP : currentDayPendingData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	else
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}
	mailtext.append("</tbody></table>");
	}

	if (currentDaySnoozeData != null && currentDaySnoozeData.size() > 0)
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Tickets Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Tickets Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}

	int i = 0;
	for (FeedbackPojo FBP : currentDaySnoozeData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	else
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}
	mailtext.append("</tbody></table>");
	}

	if (currentDayHPData != null && currentDayHPData.size() > 0)
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority Tickets Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority Tickets Feedback Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}

	int i = 0;
	for (FeedbackPojo FBP : currentDayHPData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	else
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}

	mailtext.append("</tbody></table>");
	}

	if (currentDayIgData != null && currentDayIgData.size() > 0)
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore Tickets Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore Tickets Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}

	int i = 0;
	for (FeedbackPojo FBP : currentDayIgData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	else
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}

	mailtext.append("</tbody></table>");
	}

	if (CFData != null && CFData.size() > 0)
	{
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Carry Forward Patient Ticket Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Carry Forward Patient Ticket Summary For " + deptComment + "</b></td></tr>");
	mailtext
	.append("<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	}

	int i = 0;
	for (FeedbackPojo FBP : CFData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	else
	{
	if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("1"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getStatus() + "</td></tr>");
	}
	else if (reportLevel != null && !reportLevel.equals("") && reportLevel.equals("2"))
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getCr_no() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + (FBP.getPatMobNo()) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getVia_from()
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time().substring(0, 5) + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_catg()
	+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeed_brief() + "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}

	mailtext.append("</tbody></table>");
	}
	// mailtext.append(
	// "<table><tbody></tbody></table><br><b>Thanks & Regards,<br>Patient Delight Application Team<br></b>"
	// );
	// mailtext.append(
	// "---------------------------------------------------------------------------------------------------------------------<br><font face ='TIMESROMAN' size='1'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>"
	// );
	return mailtext.toString();
	}

	public PatientReportPojo getPatientDetails(String feedId, SessionFactory connectionSpace)
	{
	PatientReportPojo pojo = null;
	String crNo = getCustIdFromFeed(feedId, connectionSpace);
	if (crNo != null && !crNo.equalsIgnoreCase("NA"))
	{
	// System.out.println(
	// "select cr_number,patient_name,patient_type,station_unit,mobile_no,email,consult_clinician_name,discharge_datetime from patientinfo where cr_number='"
	// +crNo+"'");
	List data = cbt.executeAllSelectQuery("select cr_number,patient_name,patient_type,station,mobile_no,email,admit_consultant,discharge_datetime from patientinfo where cr_number='" + crNo + "'", connectionSpace);
	if (data != null && data.size() > 0)
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	pojo = new PatientReportPojo();
	if (object != null)
	{
	if (object[0] != null)
	{
	pojo.setPatientId(object[0].toString());
	}
	else
	{
	pojo.setPatientId("NA");
	}

	if (object[1] != null)
	{
	pojo.setPatientName(object[1].toString());
	}
	else
	{
	pojo.setPatientName("NA");
	}

	if (object[2] != null)
	{
	pojo.setPatType(object[2].toString());
	}
	else
	{
	pojo.setPatType("NA");
	}

	if (object[3] != null)
	{
	pojo.setRoomNo(object[3].toString());
	}
	else
	{
	pojo.setRoomNo("NA");
	}

	if (object[4] != null)
	{
	pojo.setMobNo(object[4].toString());
	}
	else
	{
	pojo.setMobNo("NA");
	}

	if (object[5] != null)
	{
	pojo.setEmailId(object[5].toString());
	}
	else
	{
	pojo.setEmailId("NA");
	}

	if (object[6] != null)
	{
	pojo.setDocName(object[6].toString());
	}
	else
	{
	pojo.setDocName("NA");
	}

	if (object[7] != null)
	{
	pojo.setDischargeDateTime(object[7].toString());
	}
	else
	{
	pojo.setDischargeDateTime("NA");
	}
	}
	}
	}
	}

	return pojo;
	}

	public String getCount(SessionFactory connectionSpace, String patientid)
	{
	String counter = null;

	StringBuilder builder = new StringBuilder("select count(*) from feedbackdata where clientId ='" + patientid + "'");

	// System.out.println(builder);
	List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
	{
	counter = dataList.get(0).toString();
	}
	return counter;
	}

	public String getPatientName(SessionFactory connectionSpace, String patientid)
	{
	String patientname = null;

	StringBuilder builder = new StringBuilder("select patient_name from patientinfo where cr_number = '" + patientid + "'");

	// System.out.println(builder);
	List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
	{
	patientname = dataList.get(0).toString();
	}
	return patientname;
	}

	public String getMobileNumber(SessionFactory connectionSpace, String patientid)
	{
	String patientmob = null;

	StringBuilder builder = new StringBuilder("select mobile_no from patientinfo where cr_number = '" + patientid + "'");

	// System.out.println(builder);
	List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
	{
	patientmob = dataList.get(0).toString();
	}
	return patientmob;
	}

	public static int getRowId(String clientId, SessionFactory connectionSpace)
	{
	int id = 0;
	StringBuilder buffer = new StringBuilder("select max(id) from feedbackdata where clientId = '" + clientId + "'");
	List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
	if (data != null && data.size() > 0 && data.get(0) != null)
	{
	id = Integer.parseInt(data.get(0).toString());
	}
	else
	{
	id = 0;
	}
	return id;
	}

	public FeedbackPojo getPatientDetailsByCRNo(FeedbackPojo pojo, SessionFactory connectionSpace)
	{
	try
	{
	StringBuilder builder = new StringBuilder("select clientId,compType,serviceBy,centerCode,problem,mobNo,emailId from feedbackdata where clientId='" + pojo.getCr_no() + "')");
	List data = new createTable().executeAllSelectQuery(builder.toString(), connectionSpace);
	if (data != null && data.size() > 0)
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object != null)
	{
	if (object[0] != null)
	{
	pojo.setCr_no(object[0].toString());
	}
	if (object[1] != null)
	{
	pojo.setPatTyp(object[1].toString());
	}
	if (object[2] != null)
	{
	pojo.setDocName(object[2].toString());
	}
	if (object[3] != null)
	{
	pojo.setRoomNo(object[3].toString());
	}
	if (object[4] != null)
	{
	pojo.setPatObsrvtn(object[4].toString());
	}

	if (object[5] != null)
	{
	pojo.setPatMobNo(object[5].toString());
	}

	if (object[6] != null)
	{
	pojo.setPatEmailId(object[6].toString());
	}
	}
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return pojo;
	}

	public String getDeptTicketsCounters(String mode, String deptId, String empId, SessionFactory connectionSpace)
	{
	String counter = null;
	List<String> deptList = new ArrayList<String>();
	if (!new FeedbackUniversalAction().isAdminDept(Integer.parseInt(deptId)))
	{
	deptList = new FeedbackUniversalAction().getLoggedInEmpForDept(empId, deptId, "FM", connectionSpace);
	}

	StringBuilder builder = new StringBuilder("select count(*) from feedback_status where id!=0 and moduleName='FM'");

	if (mode != null && !mode.equalsIgnoreCase(""))
	{
	builder.append(" and via_from like '" + mode + "%'");
	}

	if (deptList.size() > 0)
	{
	builder.append(" and to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")") + "");
	}

	List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
	{
	counter = dataList.get(0).toString();
	}

	return counter;
	}

	public String getFeedbackCounters(String mode, String type, SessionFactory connectionSpace)
	{
	String counter = null;

	StringBuilder builder = new StringBuilder("select count(*) from feedbackdata where id!=0");
	if (mode != null && !mode.equalsIgnoreCase(""))
	{
	builder.append(" and mode like '" + mode + "%'");
	}

	if (type != null && !type.equalsIgnoreCase(""))
	{
	builder.append(" and targetOn ='" + type + "'");
	}
	// System.out.println(builder);
	List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
	{
	counter = dataList.get(0).toString();
	}
	return counter;
	}

	public Map<String, String> getDistinctColVal(SessionFactory connectionSpace, String type)
	{
	Map<String, String> map = new HashMap<String, String>();

	StringBuilder buffer = new StringBuilder("select distinct " + type + " from feedbackdata");
	List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
	if (data != null && data.size() > 0)
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	if (object != null)
	{
	map.put(object.toString(), object.toString());
	}
	}
	}
	return map;
	}

	public int getCustomerFeedbackDetails(SessionFactory connectionSpace, String mobNo)
	{
	int count = 0;
	StringBuilder buffer = new StringBuilder("select count(*) from feedbackdata where mobNo='" + mobNo + "'");
	List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
	if (data != null && data.size() > 0 && data.get(0) != null)
	{
	count = Integer.parseInt(data.get(0).toString());
	}
	else
	{
	count = 0;
	}
	return count;
	}

	public String getCounterForNegFeedback(SessionFactory connectionSpace, String ticketNo)
	{
	String count = "0";
	StringBuilder builder = new StringBuilder("select count(*) from feedback_status where ticket_no like '" + ticketNo + "%'");
	List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	if (object != null)
	{
	count = object.toString();
	}
	}
	}
	// System.out.println("Counterrrrr"+count);
	return count;
	}

	public String getOrgName(SessionFactory connectionSpace)
	{
	String orgName = null;

	List dataList = cbt.executeAllSelectQuery("select companyName from organization_level1", connectionSpace);

	if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
	{
	orgName = dataList.get(0).toString();
	}
	else
	{
	orgName = "NA";
	}
	return orgName;
	}

	@SuppressWarnings("unchecked")
	public List getTicketCounters(String reportLevel, String reportType, boolean cd_pd, String subdept_dept, String deptLevel, SessionFactory connection)
	{
	List counterList = new ArrayList();
	StringBuilder qry = new StringBuilder();
	if (deptLevel.equals("2"))
	{
	qry.append("select count(*),status from feedback_status as feedback");
	qry.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	// Applying inner join at sub department level
	qry.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	qry.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	qry.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	qry.append(" inner join department dept2 on feedtype.dept_subdept= dept2.id  ");
	qry.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id  ");

	if (reportLevel.equals("2"))
	{
	if (cd_pd)
	{
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" where feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	}
	else
	{
	qry.append(" where feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' ");
	}
	}
	else if (reportLevel.equals("1"))
	{
	if (cd_pd)
	{
	if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("D"))
	{
	qry.append(" where feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept =" + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept = " + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept =" + subdept_dept + "");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept  =" + subdept_dept + "");
	}
	}
	else
	{
	qry.append(" where feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	}
	qry.append(" and feedback.moduleName='FM' and feedtype.fbType='Complaint' and to_dept_subdept ='" + subdept_dept + "' group by status");
	}
	else if (deptLevel.equals("1"))
	{/*
	 * qry.append("select count(*),status from feedback_status"); if
	 * (reportLevel.equals("2")) { if (cd_pd) { if (reportType!=null &&
	 * !reportType.equals("") && reportType.equals("D")) {
	 * qry.append(" where open_date='"
	 * +DateUtil.getCurrentDateUSFormat()+"' and to_dept_subdept="
	 * +subdept_dept
	 * +" and deptHierarchy="+deptLevel+" and moduleName='FM' group by status"
	 * ); } else if (reportType!=null && !reportType.equals("") &&
	 * reportType.equals("W")) {
	 * qry.append(" where open_date between '"+DateUtil.getNewDate("week",
	 * -1,DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.
	 * getCurrentDateUSFormat
	 * ()+"' and to_dept_subdept="+subdept_dept+" and deptHierarchy="
	 * +deptLevel+" and moduleName='FM' group by status"); } else if
	 * (reportType!=null && !reportType.equals("") &&
	 * reportType.equals("M")) {
	 * qry.append(" where open_date between '"+DateUtil.getNewDate("month",
	 * -1,DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.
	 * getCurrentDateUSFormat
	 * ()+"' and to_dept_subdept="+subdept_dept+" and deptHierarchy="
	 * +deptLevel+" and moduleName='FM' group by status"); } else if
	 * (reportType!=null && !reportType.equals("") &&
	 * reportType.equals("Q")) {
	 * qry.append(" where open_date between '"+DateUtil.getNewDate("month",
	 * -3,DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.
	 * getCurrentDateUSFormat
	 * ()+"' and to_dept_subdept="+subdept_dept+" and deptHierarchy="
	 * +deptLevel+" and moduleName='FM' group by status"); } else if
	 * (reportType!=null && !reportType.equals("") &&
	 * reportType.equals("H")) {
	 * qry.append(" where open_date between '"+DateUtil.getNewDate("month",
	 * -6,DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.
	 * getCurrentDateUSFormat
	 * ()+"' and to_dept_subdept="+subdept_dept+" and deptHierarchy="
	 * +deptLevel+" and moduleName='FM' group by status"); } } else {
	 * qry.append(" where open_date<'"+DateUtil.getCurrentDateUSFormat()+
	 * "' and to_dept_subdept="
	 * +subdept_dept+" and deptHierarchy="+deptLevel+
	 * " and moduleName='FM' group by status"); } } else if
	 * (reportLevel.equals("1")) { if (cd_pd) {
	 * qry.append(" where open_date='"+DateUtil.getCurrentDateUSFormat()+
	 * "' and moduleName='FM' group by status"); } else {
	 * qry.append("  where open_date<'"+DateUtil.getCurrentDateUSFormat()+
	 * "' and moduleName='FM' group by status"); } }
	 */
	}
	//System.out.println("Counter Query ::::::::   " + qry.toString());
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}

	return counterList;
	}

	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketData(String reportLevel, String reportType, boolean cd_pd, String status, String subdept_dept, String deptLevel, SessionFactory connection)
	{
	List dataList = new ArrayList();
	List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
	StringBuilder query = new StringBuilder("");
	query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by," + " feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName," + " catg.categoryName,subcatg.subCategoryName,feedback.feed_brief," + " subcatg.addressingTime,feedback.open_date,feedback.open_time,feedback.escalation_date," + " feedback.escalation_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,"
	+ " feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.location," + " feedback.feedback_remarks ");

	// Block for get resolved data
	if (status.equalsIgnoreCase("Resolved"))
	{
	query.append(", feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used ");
	}
	// Block for get Snooze data
	else if (status.equalsIgnoreCase("Snooze"))
	{
	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration ");
	}
	// Block for get High Priority data
	else if (status.equalsIgnoreCase("High Priority"))
	{
	query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason ");
	}
	// Block for get ignore data
	else if (status.equalsIgnoreCase("Ignore"))
	{
	query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason ");
	}

	query.append(" from feedback_status as feedback");

	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id ");
	// Applying inner join at sub department level

	query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	query.append(" inner join department subdept1 on feedback.by_dept_subdept= subdept1.id");
	query.append(" inner join department subdept2 on feedback.to_dept_subdept= subdept2.id");
	query.append(" inner join department dept1 on subdept1.id= dept1.id ");
	query.append(" inner join department dept2 on subdept2.id= dept2.id");

	// Apply inner join for getting the data for Resolved Ticket
	if (status.equalsIgnoreCase("Resolved"))
	{
	query.append("  inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
	}

	// getting data of current day
	if (cd_pd)
	{
	query.append(" where feedback.status='" + status + "' and feedback.moduleName='FM' ");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	query.append(" and feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("W"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("M"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("Q"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("H"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}

	if (reportLevel.equals("1"))
	{
	query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=(select deptid from subdepartment where id=" + subdept_dept + ")) and feedback.deptHierarchy=" + deptLevel + "");
	}
	}
	// End of If Block for getting the data for only the current date in
	// both case for Resolved OR All

	// Else Block for getting the data for only the previous date
	else
	{
	if (reportLevel.equals("2"))
	{
	query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' and feedback.moduleName='FM'  ");
	}
	else if (reportLevel.equals("1"))
	{
	query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' and feedback.to_dept_subdept in (select id from subdepartment where deptid=(select deptid from subdepartment where id=" + subdept_dept + ")) and feedback.deptHierarchy=" + deptLevel + "");
	}
	}

	// System.out.println(" Data Querry is as "+query);
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	dataList = session.createSQLQuery(query.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	ex.printStackTrace();
	transaction.rollback();
	}
	// System.out.println("dataList size is as :::::::::::::::::"+dataList.
	// size());
	if (dataList != null && dataList.size() > 0)
	{
	report = new FeedbackUniversalHelper().setFeedbackDataValusInList(dataList, status, deptLevel);

	}
	//System.out.println("report size is as ::::::::::::::::::"+report.size(
	// ));
	return report;
	}

	/*
	 * public String getConfigMailForReport(int pc,int hc,int sc,int ic,int
	 * rc,int total,int cfpc,int cfsc,int cfhc,int cftotal,int
	 * totalSnooze,String reportLevel,List<FeedbackReportPojo> FRP,String
	 * deptComment,String report_type,FeedbackReportPojo FRP4Counter,String
	 * empname, List<FeedbackPojo> currentDayResolvedData,List<FeedbackPojo>
	 * currentDayPendingData,List<FeedbackPojo>
	 * currentDaySnoozeData,List<FeedbackPojo>
	 * currentDayHPData,List<FeedbackPojo> currentDayIgData,List<FeedbackPojo>
	 * CFData,String ip,int cfp_Total,int cd_Total,int cd_Pending,int
	 * total_snooze,int CDR_Total) { String url
	 * ="<a href=http://"+ip+"/over2cloud>click here</a>";
	 * 
	 * StringBuilder mailtext=new StringBuilder(""); //mailtext.append(
	 * "<br><br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +orgName+"</b></td></tr></tbody></table>"); //mailtext.append(
	 * "<table width='100%' align='left' style='border: 0'><tbody><tr><td align='left' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody></table>"
	 * ); mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
	 * mailtext.append("<br><br><b>Hello!!!</b>");mailtext.append(
	 * "<br><br>Please find the following summary snapshot mapped for your analysis. You may find more details in the attached excel or for dynamic graphical analysis we request, you to please "
	 * +url+" and login with your respective credentials.<br>"); //
	 * mailtext.append(
	 * "<br><br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Patient Delight Score for "
	 * +
	 * deptComment+" Department as on "+DateUtil.getCurrentDateIndianFormat()+" At "
	 * +DateUtil.getCurrentTime().substring(0,
	 * 5)+"</b></td></tr></tbody></table>");
	 * 
	 * if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * );mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='5' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Status By Various Modes</b></td></tr>"
	 * );
	 * 
	 * if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("D")) {mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Resolved</b></td></tr>"
	 * ); } else if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("W")) {mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Resolved</b></td></tr>"
	 * ); } else if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("M")) {mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Resolved</b></td></tr>"
	 * ); }mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +cfpc+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +total+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +pc+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +totalSnooze+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +rc+"</td></tr>"); mailtext.append("</tbody></table>"); } else if
	 * (reportLevel!=null && !reportLevel.equals("") && reportLevel.equals("2"))
	 * {mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("D")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Feedback Summary Status for All Departments by All Modes</b></td></tr>"
	 * ); } else if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("W")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Feedback Summary Status for All Departments by All Modes</b></td></tr>"
	 * ); } else if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("M")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Feedback Summary Status for All Departments by All Modes</b></td></tr>"
	 * ); }
	 * 
	 * mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;SMS&nbsp;Mode</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Feedback&nbsp;Seek</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRP4Counter.getSt()+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRP4Counter.getSnr()+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='left' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRP4Counter.getSn()+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;Paper&nbsp;Mode</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Received</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRP4Counter.getPt()+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='left' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRP4Counter.getPp()+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='left' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRP4Counter.getPn()+"</b></td></tr>");
	 * mailtext.append("</tbody></table>");
	 * 
	 * 
	 * mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * );mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='6' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Summary Status for All Departments by All Modes</b></td></tr>"
	 * ); if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("D")) {mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Resolved</b></td></tr>"
	 * ); } else if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("W")) {mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Week  Resolved</b></td></tr>"
	 * ); } else if (report_type!=null && !report_type.equals("") &&
	 * report_type.equalsIgnoreCase("M")) {mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Carry Forward Pending</b></td></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Total</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Pending</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Total Snooze</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Last Month Resolved</b></td></tr>"
	 * ); }
	 * 
	 * for(FeedbackReportPojo FRPObj:FRP) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +FRPObj.getDeptName()+
	 * "</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FRPObj.getCFP()+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FRPObj.getCDT()+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FRPObj.getCDP()+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FRPObj.getTS()+
	 * "</td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FRPObj.getCDR()+"</td></tr>"); }mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total</b></td><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +cfp_Total+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +cd_Total+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +cd_Pending+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +total_snooze+
	 * "</b></td><td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
	 * +CDR_Total+"</b></td></tr>"); mailtext.append("</tbody></table>");
	 * mailtext.append("<br></br>");
	 * 
	 * }
	 * 
	 * 
	 * if (currentDayResolvedData!=null && currentDayResolvedData.size()>0) {
	 * mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved Patient Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); } else if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved Patient Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); }
	 * 
	 * 
	 * int i=0; for(FeedbackPojo FBP:currentDayResolvedData) { int k=++i;
	 * if(k%2!=0) { if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } else { if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td>  <td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } }
	 * mailtext.append("</tbody></table>"); }
	 * 
	 * 
	 * if (currentDayPendingData!=null && currentDayPendingData.size()>0) {
	 * mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='8' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pending Tickets Patient Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); } else if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pending Tickets Patient Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); }
	 * 
	 * int i=0; for(FeedbackPojo FBP:currentDayPendingData) { int k=++i;
	 * if(k%2!=0) { if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } else { if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } }
	 * mailtext.append("</tbody></table>"); }
	 * 
	 * if (currentDaySnoozeData!=null && currentDaySnoozeData.size()>0) {
	 * mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='8' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Tickets Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); } else if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Tickets Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); }
	 * 
	 * int i=0; for(FeedbackPojo FBP:currentDaySnoozeData) { int k=++i;
	 * if(k%2!=0) { if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } else { if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } }
	 * mailtext.append("</tbody></table>"); }
	 * 
	 * if (currentDayHPData!=null && currentDayHPData.size()>0) {
	 * mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='8' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority Tickets Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); } else if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority Tickets Feedback Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); }
	 * 
	 * int i=0; for(FeedbackPojo FBP:currentDayHPData) { int k=++i; if(k%2!=0) {
	 * if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } else { if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } }
	 * 
	 * mailtext.append("</tbody></table>"); }
	 * 
	 * if (currentDayIgData!=null && currentDayIgData.size()>0) {
	 * mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='8' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore Tickets Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); } else if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore Tickets Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); }
	 * 
	 * int i=0; for(FeedbackPojo FBP:currentDayIgData) { int k=++i; if(k%2!=0) {
	 * if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } else { if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } }
	 * 
	 * mailtext.append("</tbody></table>"); }
	 * 
	 * if (CFData!=null && CFData.size()>0) {mailtext.append(
	 * "<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>"
	 * ); if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='8' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Carry Forward Patient Ticket Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); } else if (reportLevel!=null && !reportLevel.equals("") &&
	 * reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#848482'><td colspan='10' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Carry Forward Patient Ticket Summary For "
	 * +deptComment+"</b></td></tr>");mailtext.append(
	 * "<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>"
	 * ); }
	 * 
	 * int i=0; for(FeedbackPojo FBP:CFData) { int k=++i; if(k%2!=0) { if
	 * (reportLevel!=null && !reportLevel.equals("") && reportLevel.equals("1"))
	 * {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } else { if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("1")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } else if (reportLevel!=null &&
	 * !reportLevel.equals("") && reportLevel.equals("2")) {mailtext.append(
	 * "<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getCr_no()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +DateUtil.makeTitle(FBP.getFeed_by())+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +(FBP.getPatMobNo())+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getTicket_no()+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getOpen_date()+" "+FBP.getOpen_time().substring(0,5)+
	 * "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_catg()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeed_brief()+
	 * "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_to_dept()+
	 * "</td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getFeedback_allot_to()+
	 * "</td><td align='left' width='10%' bgcolor='#E41B17' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	 * +FBP.getStatus()+"</td></tr>"); } } }
	 * 
	 * mailtext.append("</tbody></table>"); }mailtext.append(
	 * "<table><tbody></tbody></table><br><b>Thanks & Regards,<br>Patient Delight Application Team<br></b>"
	 * );mailtext.append(
	 * "---------------------------------------------------------------------------------------------------------------------<br><font face ='TIMESROMAN' size='1'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>"
	 * ); return mailtext.toString() ; }
	 */

	@SuppressWarnings("unchecked")
	public String getConfigMailForReport(int pc, int hc, int sc, int ic, int rc, int total, int cfpc, int cfsc, int cfhc, int cftotal, String empname, List<FeedbackPojo> currentDayResolvedData, List<FeedbackPojo> currentDayPendingData, List<FeedbackPojo> currentDaySnoozeData, List<FeedbackPojo> currentDayHPData, List<FeedbackPojo> currentDayIgData, List<FeedbackPojo> CFData)
	{
	List orgData = new LoginImp().getUserInfomation("1", "IN");
	String orgName = "";
	if (orgData != null && orgData.size() > 0)
	{
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	orgName = object[0].toString();
	}
	}
	StringBuilder mailtext = new StringBuilder("");
	mailtext.append("<br><br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + orgName + "</b></td></tr></tbody></table>");
	mailtext.append("<table width='100%' align='left' style='border: 0'><tbody><tr><td align='left' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody></table>");
	mailtext.append("<b>Dear " + DateUtil.makeTitle(empname) + ",</b>");
	mailtext.append("<br><br><b>Hello!!!</b>");
	mailtext.append("<br><br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Ticket Summary Status For All Department On " + DateUtil.getCurrentDateIndianFormat() + " At " + DateUtil.getCurrentTime() + "</b></td></tr></tbody></table>");

	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	mailtext
	.append("<tr bgcolor='#8db7d6'><td align='left'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Pending</b></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/D Total</b></td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pc + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + rc + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + cfpc
	+ "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + total + "</td></tr></tbody></table>");

	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	mailtext
	.append("<tr  bgcolor='#8db7d6'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>By&nbsp;Dept</strong></td> <td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;On</strong></td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td> <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	if (currentDayResolvedData != null && currentDayResolvedData.size() > 0)
	{
	int i = 0;
	for (FeedbackPojo FBP : currentDayResolvedData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_by_dept() + "</td> <td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_time()
	+ "</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation()
	+ "</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_by_dept() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_time()
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation()
	+ "</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}
	mailtext.append("</tbody></table>");

	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (currentDayPendingData != null && currentDayPendingData.size() > 0)
	{
	int i = 0;
	for (FeedbackPojo FBP : currentDayPendingData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_by_dept() + "</td> <td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_time()
	+ "</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation()
	+ "</td><td align='left' width='12%' bgcolor='#cc0066' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_by_dept() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_time()
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation()
	+ "</td><td align='left' width='12%'  bgcolor='#cc0066' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}
	mailtext.append("</tbody></table>");

	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='left'><tbody>");
	if (CFData != null && CFData.size() > 0)
	{
	int i = 0;
	for (FeedbackPojo FBP : CFData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_by_dept() + "</td> <td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_time()
	+ "</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation()
	+ "</td><td align='left' width='12%' bgcolor='#f7b3b3' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getTicket_no() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by())
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_by_dept() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + "</td><td align='left' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_time()
	+ "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_to_dept() + "</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation()
	+ "</td><td align='left' width='12%' bgcolor='#f7b3b3' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	}
	}
	}
	mailtext.append("</tbody></table>");

	mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
	mailtext
	.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
	return mailtext.toString();
	}

	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketDataforReport(String reportLevel, String reportType, boolean cd_pd, String status, String subdept_dept, String deptLevel, SessionFactory connection)
	{
	List dataList = new ArrayList();
	List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
	StringBuilder query = new StringBuilder("");
	query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.patMobNo,feedback.clientId,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");

	// Block for get resolved data
	if (status.equalsIgnoreCase("Resolved"))
	{
	query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
	}
	// Block for get Snooze data
	else if (status.equalsIgnoreCase("Snooze"))
	{
	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
	}
	// Block for get High Priority data
	else if (status.equalsIgnoreCase("High Priority"))
	{
	query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
	}
	// Block for get ignore data
	else if (status.equalsIgnoreCase("Ignore"))
	{
	query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason");
	}
	// getting the data regarding Sub Department at Level 2
	/*
	 * if (deptLevel.equals("2")) {query.append(
	 * ",subdept1.subdeptname as bysubdept,subdept2.subdeptname as tosubdept"
	 * ); }
	 */

	if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending"))
	{
	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
	}
	query.append(",subcatg.addressingTime,feedback.escalation_date,feedback.escalation_time");
	query.append(" from feedback_status as feedback");

	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	// Applying inner join at sub department level
	if (deptLevel.equals("2"))
	{
	query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	query.append(" inner join department dept2 on feedtype.dept_subdept= dept2.id  ");
	query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id  ");
	}

	// Apply inner join for getting the data for Resolved Ticket
	if (status.equalsIgnoreCase("Resolved"))
	{
	query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
	}

	// getting data of current day
	if (cd_pd)
	{
	query.append(" where feedback.status='" + status + "' ");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	query.append(" and feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("W"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("M"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("Q"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equals("H"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}

	if (reportLevel.equals("1"))
	{
	query.append(" and feedback.to_dept_subdept=" + subdept_dept + "");
	}
	}
	// End of If Block for getting the data for only the current date in
	// both case for Resolved OR All

	// Else Block for getting the data for only the previous date
	else
	{
	if (reportLevel.equals("2"))
	{
	query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportLevel.equals("1"))
	{
	query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' and feedback.to_dept_subdept=" + subdept_dept + " ");
	}
	}
	query.append(" and feedback.moduleName='FM' and feedtype.fbType='Complaint'");
	//System.out.println("DAta Query  " + query.toString());
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	dataList = session.createSQLQuery(query.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}

	if (dataList != null && dataList.size() > 0)
	{
	report = new FeedbackUniversalHelper().setFeedbackDataforReport(dataList, status, deptLevel, connection);
	}
	return report;
	}

	@SuppressWarnings("unchecked")
	public List getPaperData(String reportType, SessionFactory connection)
	{
	List counterList = new ArrayList();
	StringBuilder qry = new StringBuilder();
	qry.append("select targetOn,count(*) from feedbackdata");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" where openDate='" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where openDate between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where openDate between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where openDate between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where openDate between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	qry.append(" and mode='Paper' group by targetOn");
	// System.out.println("Paper Qry  "+qry.toString());
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}

	return counterList;
	}

	public int getSMSDataForTotalSeek(String reportType, SessionFactory connection)
	{
	int counter = 0;
	StringBuilder qry = new StringBuilder("select count(*) from instant_msg where msg_text like '%Thanks for availing services at %'");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" and update_date='" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" and update_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" and update_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" and update_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" and update_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	Session session = null;
	Transaction transaction = null;
	List counterList = new ArrayList();
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}
	if (counterList != null && counterList.size() > 0)
	{
	counter = Integer.parseInt(counterList.get(0).toString());
	}
	return counter;
	}

	public int getRevertedSMSDetailsCurrent(boolean positive, boolean negative, String reportType, SessionFactory connection)
	{

	int counter = 0;
	StringBuilder qry = new StringBuilder("select count(*) from t2mkeyword");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" where updatedDate ='" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	//qry.append(" and updatedTime >'"+DateUtil.getCurrentTimewithSeconds()+
	// "'");
	if (positive)
	{/*
	 * List<String> keyList=new ArrayList<String>();
	 * keyList=getKeyWord(connection,"0"); if(keyList!=null) {
	 * keyList.toString().replace("[", "('");
	 * keyList.toString().replace("]", "')");
	 * keyList.toString().replace(",", "','");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>"
	 * +keyList); qry.append("and keyword in "+keyList); }
	 */
	String posKey = getKeyWord(connection, "0");
	if (posKey != null)
	{
	qry.append("and keyword = '" + posKey + "'");
	}

	}
	else if (negative)
	{/*
	 * List<String> keyList=new ArrayList<String>();
	 * keyList=getKeyWord(connection,"1"); if(keyList!=null) {
	 * keyList.toString().replace("[", "('");
	 * keyList.toString().replace("]", "')");
	 * keyList.toString().replace(",", "','");
	 * qry.append("and keyword in "+keyList); }
	 */

	String negKey = getKeyWord(connection, "1");
	if (negKey != null)
	{
	qry.append("and keyword = '" + negKey + "'");
	}
	}
	else
	{
	}
	Session session = null;
	Transaction transaction = null;
	List counterList = new ArrayList();
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}
	if (counterList != null && counterList.size() > 0)
	{
	counter = Integer.parseInt(counterList.get(0).toString());
	}
	return counter;
	}

	/*
	 * public int getRevertedSMSDetails(boolean positive,boolean negative,String
	 * reportType,SessionFactory connection) {
	 * 
	 * int counter=0; StringBuilder qry=new
	 * StringBuilder("select count(*) from t2mkeyword"); if (reportType!=null &&
	 * !reportType.equals("") && reportType.equals("D")) {
	 * qry.append(" where updatedDate>='"+DateUtil.getNextDateAfter(-1)+"'"); }
	 * else if (reportType!=null && !reportType.equals("") &&
	 * reportType.equalsIgnoreCase("W")) {
	 * qry.append(" where updatedDate between '"+DateUtil.getNewDate("week", -1,
	 * DateUtil
	 * .getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat
	 * ()+"'"); } else if (reportType!=null && !reportType.equals("") &&
	 * reportType.equalsIgnoreCase("M")) {
	 * qry.append(" where updatedDate between '"+DateUtil.getNewDate("month",
	 * -1,
	 * DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat
	 * ()+"'"); } else if (reportType!=null && !reportType.equals("") &&
	 * reportType.equalsIgnoreCase("Q")) {
	 * qry.append(" where updatedDate between '"+DateUtil.getNewDate("month",
	 * -3,
	 * DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat
	 * ()+"'"); } else if (reportType!=null && !reportType.equals("") &&
	 * reportType.equalsIgnoreCase("H")) {
	 * qry.append(" where updatedDate between '"+DateUtil.getNewDate("month",
	 * -6,
	 * DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat
	 * ()+"'"); }
	 * qry.append(" and updatedTime >'"+DateUtil.getCurrentTimewithSeconds
	 * ()+"'"); List<String> keyList=new ArrayList<String>(); if(positive) {
	 * keyList=getKeyWord(connection,"0"); if(keyList!=null) {
	 * qry.append("and keyword='"+keyList+"'"); } } else if(negative) {
	 * keyList=getKeyWord(connection,"0"); if(keyList!=null) {
	 * qry.append("and keyword='"+keyList+"'"); } } else {
	 * 
	 * } System.out.println("T2m Keyword "+qry); Session session = null;
	 * Transaction transaction = null; List counterList = new ArrayList(); try {
	 * session=connection.getCurrentSession(); transaction =
	 * session.beginTransaction();
	 * counterList=session.createSQLQuery(qry.toString()).list();
	 * transaction.commit(); } catch (Exception ex) { transaction.rollback(); }
	 * if (counterList!=null && counterList.size()>0) {
	 * counter=Integer.parseInt(counterList.get(0).toString()); } return
	 * counter;
	 * 
	 * }
	 */

	/*
	 * public List<String> getKeyWord(SessionFactory connection,String type) {
	 * List<String> keyWord=new ArrayList<String>(); Session session=null; List
	 * counterList=null; try { session=connection.getCurrentSession();
	 * counterList=session.createSQLQuery(
	 * "select keyword from feedback_sms_config where type='"+type+"'").list();
	 * } catch (Exception ex) { ex.printStackTrace(); } if(counterList!=null &&
	 * counterList.size()>0) { for (Iterator iterator = counterList.iterator();
	 * iterator .hasNext();) { Object object = (Object) iterator.next();
	 * if(object!=null) { keyWord.add(object.toString()); } } } return keyWord;
	 * }
	 */

	public String getKeyWord(SessionFactory connection, String type)
	{
	String keyWord = null;
	Session session = null;
	Transaction transaction = null;
	List counterList = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery("select keyword from feedback_sms_config where type ='" + type + "'").list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	ex.printStackTrace();
	transaction.rollback();
	}
	if (counterList != null && counterList.size() > 0)
	{
	if (counterList.get(0) != null)
	{
	keyWord = counterList.get(0).toString();
	}
	}
	return keyWord;
	}

	@SuppressWarnings("unchecked")
	public int getSMSData(String reportType, String smsMode, SessionFactory connection)
	{
	int counter = 0;
	List counterList = new ArrayList();
	StringBuilder qry = new StringBuilder();
	qry.append("select count(*) from patientinfo");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" where updateDate='" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where updateDate between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where updateDate between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where updateDate between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where updateDate between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	if (smsMode != null && !smsMode.equals("") && smsMode.equalsIgnoreCase("A"))
	{
	qry.append(" and smsFlag in ('3','5','7')");
	}
	else if (smsMode != null && !smsMode.equals("") && smsMode.equalsIgnoreCase("NR"))
	{
	qry.append(" and smsFlag='7'");
	}
	if (smsMode != null && !smsMode.equals("") && smsMode.equalsIgnoreCase("N"))
	{
	qry.append(" and smsFlag='5'");
	}
	// System.out.println("SMS Qry  "+qry.toString());
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}
	if (counterList != null && counterList.size() > 0)
	{
	counter = Integer.parseInt(counterList.get(0).toString());
	}
	return counter;
	}

	public int getRevertedSMSDetailsPrevious(boolean positive, boolean negative, String reportType, SessionFactory connection)
	{

	int counter = 0;
	StringBuilder qry = new StringBuilder("select count(*) from t2mkeyword");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" where updatedDate>='" + DateUtil.getNextDateAfter(-1) + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where updatedDate between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	}
	qry.append(" and updatedTime >'" + DateUtil.getCurrentTimewithSeconds() + "'");
	if (positive)
	{/*
	 * List<String> keyList=new ArrayList<String>();
	 * keyList=getKeyWord(connection,"0"); if(keyList!=null) {
	 * keyList.toString().replace("[", "('");
	 * keyList.toString().replace("]", "')");
	 * keyList.toString().replace(",", "','");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>"
	 * +keyList); qry.append("and keyword in "+keyList); }
	 */
	String posKey = getKeyWord(connection, "0");
	if (posKey != null)
	{
	qry.append("and keyword = '" + posKey + "'");
	}
	}
	else if (negative)
	{/*
	 * List<String> keyList=new ArrayList<String>();
	 * keyList=getKeyWord(connection,"1"); if(keyList!=null) {
	 * keyList.toString().replace("[", "('");
	 * keyList.toString().replace("]", "')");
	 * keyList.toString().replace(",", "','");
	 * qry.append("and keyword in "+keyList); }
	 */

	String negKey = getKeyWord(connection, "1");
	if (negKey != null)
	{
	qry.append("and keyword = '" + negKey + "'");
	}

	}
	else
	{

	}
	Session session = null;
	Transaction transaction = null;
	List counterList = new ArrayList();
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	}
	catch (Exception ex)
	{
	transaction.rollback();
	}
	if (counterList != null && counterList.size() > 0)
	{
	counter = Integer.parseInt(counterList.get(0).toString());
	}
	return counter;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getTicketsCounters(String fromDate, String toDate, String status, String mode, String ticketNo, String dept, String catg, String patName, String feedBackType, String feedBy, String userType, String userName, String loggedEmpId, String dept_subdept_id, String subCat, String clientId, SessionFactory connectionSpace)
	{
	// TODO Auto-generated method stub

	Map dataCountMap = new LinkedHashMap<String, String>();
	StringBuilder query = new StringBuilder("");
	List dataList = new ArrayList();
	List list = null;
	int pending = 0, snooze = 0, resolved = 0, ticketOpened = 0, noted = 0, reassign = 0, noFurtherAction = 0;
	dataList.add(getCountList(fromDate, toDate, status, mode, ticketNo, dept, catg, patName, feedBackType, feedBy, userType, userName, loggedEmpId, dept_subdept_id, subCat, clientId, "feedback_status", connectionSpace));
	dataList.add(getCountList(fromDate, toDate, status, mode, ticketNo, dept, catg, patName, feedBackType, feedBy, userType, userName, loggedEmpId, dept_subdept_id, subCat, clientId, "feedback_status_feed_paperRating", connectionSpace));
	int totalValue = 0;
	dataCountMap.put("Pending", "0");
	dataCountMap.put("Snooze", "0");
	dataCountMap.put("Resolved", "0");
	dataCountMap.put("TicketOpened", "0");
	dataCountMap.put("Noted", "0");
	dataCountMap.put("Reassign", "0");
	dataCountMap.put("NoFurtherAction", "0");
	if (dataList != null && dataList.size() > 0)
	for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
	{
	list = (List) iterator1.next();
	for (Iterator iterator = list.iterator(); iterator.hasNext();)
	{
	Object[] object2 = (Object[]) iterator.next();

	if (object2[1].toString().equalsIgnoreCase("Pending"))
	{
	pending += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	else if (object2[1].toString().equalsIgnoreCase("Snooze"))
	{
	snooze += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	else if (object2[1].toString().equalsIgnoreCase("Resolved"))
	{
	resolved += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	else if (object2[1].toString().equalsIgnoreCase("Noted"))
	{
	noted += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	else if (object2[1].toString().equalsIgnoreCase("Re-Assign"))
	{
	reassign += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	else if (object2[1].toString().equalsIgnoreCase("Ticket Opened"))
	{
	ticketOpened += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	else if (object2[1].toString().equalsIgnoreCase("No Further Action Required"))
	{
	noFurtherAction += Integer.parseInt(object2[0].toString());
	totalValue = totalValue + Integer.parseInt(object2[0].toString());
	}
	}
	}
	// System.out.println("total"+totalValue);
	dataCountMap.put("Pending", pending);
	dataCountMap.put("Snooze", snooze);
	dataCountMap.put("Resolved", resolved);
	dataCountMap.put("Noted", noted);
	dataCountMap.put("Reassign", reassign);
	dataCountMap.put("TicketOpened", ticketOpened);
	dataCountMap.put("NoFurtherAction", noFurtherAction);
	dataCountMap.put("total", String.valueOf(totalValue));
	return dataCountMap;
	}

	public List getCountList(String fromDate, String toDate, String status, String mode, String ticketNo, String dept, String catg, String patName, String feedBackType, String feedBy, String userType, String userName, String loggedEmpId, String dept_subdept_id, String subCat, String clientId, String tableName, SessionFactory connectionSpace)
	{
	List dataList = null;
	ActivityBoardHelper helper = new ActivityBoardHelper();
	StringBuilder query = new StringBuilder("");
	query.append("select count(distinct feedback.id),feedback.status  from " + tableName + " as feedback inner join employee_basic emp on feedback.allot_to= emp.id inner join department dept2 on feedback.to_dept_subdept= dept2.id inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id inner join feedback_category catg on subcatg.categoryName = catg.id inner join feedback_type feedtype on catg.fbType = feedtype.id where feedback.id !='0' and feedback.moduleName='FM' ");

	if (tableName.equalsIgnoreCase("feedback_status_feed_paperRating"))
	{
	query.append(" and (feedback.feed_brief not like '%Good')"); // for
	query.append(" and (feedback.feed_brief not like '%Very Good')"); // for
	query.append(" and (feedback.feed_brief not like '%Excellent')"); // for
	query.append(" and (feedback.feed_brief not like '%yes')");

	}

	if (userType != null && userType.equalsIgnoreCase("M"))
	{

	}
	else if (userType != null && userType.equalsIgnoreCase("H"))
	{
	List departList = FUA.getLoggedInEmpForDept(loggedEmpId, dept_subdept_id, "FM", connectionSpace);
	if (departList.size() > 0)
	{
	query.append(" and feedback.to_dept_subdept in " + departList.toString().replace("[", "(").replace("]", ")") + "");
	}
	}
	else
	{
	if (userName != null)
	{
	query.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + loggedEmpId + "')");
	}
	}
	if (clientId != null && !clientId.equalsIgnoreCase(""))
	{
	query.append(" and feedback.clientId='" + clientId + "'");
	}
	else if (patName != null && !patName.equalsIgnoreCase(""))
	{

	query.append(" and feedback.feed_by like '" + patName + "%'");
	}
	else
	{

	if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
	{
	String str[] = fromDate.split("-");
	if (str[0] != null && str[0].length() > 3)
	{
	query.append(" and feedback.open_date between '" + ((fromDate)) + "' and '" + ((toDate)) + "'");
	}
	else
	{
	query.append(" and feedback.open_date between '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' and '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
	}
	}
	if (status != null && !status.equalsIgnoreCase("-1"))
	{
	query.append(" and feedback.status= '" + status + "'");
	}
	else
	{
	query.append(" and feedback.status='Pending'");
	}
	/*
	 * if(getPatType()!=null && !getPatType().equalsIgnoreCase("-1")) {
	 * query.append(" and compType= '"+getPatType()+" '"); }
	 */
	if (mode != null && !mode.equalsIgnoreCase("-1"))
	{
	query.append(" and feedback.via_from= '" + mode + " '");
	}
	if (ticketNo != null && !ticketNo.equalsIgnoreCase("-1"))
	{
	query.append(" and feedback.ticket_no= '" + ticketNo + " '");
	}

	if (feedBy != null && !feedBy.equalsIgnoreCase("-1"))
	{
	query.append(" and feedback.feed_by= '" + feedBy + " '");
	}

	if (dept != null && !dept.equalsIgnoreCase("-1"))
	{
	query.append(" and feedback.to_dept_subdept='" + dept + " '");
	}

	if (catg != null && !catg.equalsIgnoreCase("-1"))
	{
	query.append(" and catg.id='" + catg + " '");
	}

	if (subCat != null && !subCat.equalsIgnoreCase("-1"))
	{
	query.append(" and subcatg.id='" + subCat + " '");
	}

	if (feedBackType != null && !feedBackType.equalsIgnoreCase("-1"))
	{
	if (feedBackType.equalsIgnoreCase("Negative"))
	{
	query.append(" and subcatg.needEsc= 'Y'");
	}
	else if (feedBackType.equalsIgnoreCase("Positive"))
	{
	query.append(" and subcatg.needEsc= 'N'");
	}
	}
	else
	{
	/*int adminDept = helper.getAdminDeptId(connectionSpace);
	if (adminDept != 0 && adminDept > 0)
	{
	query.append(" and feedback.to_dept_subdept!='" + adminDept + "'");
	}*/
	}

	}
	query.append(" group by feedback.status");
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	return dataList;
	}
}