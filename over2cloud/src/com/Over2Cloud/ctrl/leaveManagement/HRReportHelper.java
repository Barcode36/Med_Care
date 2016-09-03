package com.Over2Cloud.ctrl.leaveManagement;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;

public class HRReportHelper
{
	

	@SuppressWarnings("rawtypes")
	public List<AttendancePojo> getAllDataBeforeDate(SessionFactory connectionSpace) 
	{
	List<AttendancePojo> dataList=new ArrayList<AttendancePojo>();
	AttendancePojo attP=null;
	try {
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder(); 

	query .append("select emp.empId,emp.empName,dept.deptname,ar.date1,ar.attendence,ar.in_time,ar.out_time,ar.totalhour,ar.comment ");
	query.append(" FROM attendence_record AS ar  ");
	query.append(" INNER JOIN  compliance_contacts AS cc ON ar.empname=cc.id   ");
	query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
	query.append(" INNER JOIN department AS dept ON emp.deptname= dept.id ");
	query.append("WHERE ar.date1='"+DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat())+"'");
	query.append("  and ar.attendence='Present'");
	query.append("  order by emp.empName asc");
	List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list!=null && list.size()>0) 
	{
	Object[] object=null;
	for (Iterator iterator = list.iterator(); iterator.hasNext();) 
	{
	object = (Object[]) iterator.next();
	if (object!=null) 
	{
	attP =new  AttendancePojo();
	attP.setEmpId(getValueWithNullCheck(object[0]));
	attP.setEmpname(getValueWithNullCheck(object[1]));
	attP.setDeptname(getValueWithNullCheck(object[2]));
	attP.setData1(getValueWithNullCheck(object[3]));
	attP.setAttendance(getValueWithNullCheck(object[4]));
	attP.setIntime(getValueWithNullCheck(object[5]));
	attP.setOuttime(getValueWithNullCheck(object[6]));
	attP.setTotalhour(getValueWithNullCheck(object[7]));
	attP.setComment(getValueWithNullCheck(object[8]));
	}
	dataList.add(attP);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return dataList;
	}
	
	@SuppressWarnings("rawtypes")
	public List<AttendancePojo> getAbsentDataBeforeDate(SessionFactory connectionSpace) 
	{
	List<AttendancePojo> dataList=new ArrayList<AttendancePojo>();
	AttendancePojo attP=null;
	try {
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	//StringBuilder query8=new StringBuilder(); 
	String data1 = null;;
	
	StringBuilder query=new StringBuilder(); 

	query .append("select emp.empId,emp.empName,dept.deptname,ar.lupdate,ar.comment");
	query.append(" FROM attendence_record AS ar  ");
	query.append(" INNER JOIN  compliance_contacts AS cc ON ar.empname=cc.id   ");
	query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
	query.append(" INNER JOIN department AS dept ON emp.deptname= dept.id ");
	query.append(" WHERE ar.date1='"+DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat())+"'");
	query.append("  and ar.attendence='Absent'");
	query.append("  and ar.lupdate IN('SMS','Mail','Call','No Intimation','VMN')");
	query.append("  order by emp.empName asc");
	System.out.println("get absent data :::: " +query);
	
	List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list!=null && list.size()>0) 
	{
	Object[] object=null;
	for (Iterator iterator = list.iterator(); iterator.hasNext();) 
	{
	object = (Object[]) iterator.next();
	if (object!=null) 
	{
	attP =new  AttendancePojo();
	attP.setEmpId(getValueWithNullCheck(object[0]));
	attP.setEmpname(getValueWithNullCheck(object[1]));
	attP.setDeptname(getValueWithNullCheck(object[2]));
	data1 = object[3].toString();
	attP.setComment(getValueWithNullCheck(object[3]));

  if(data1.equalsIgnoreCase("Call"))
   {
	  attP.setUpdate("Call");
   }
  else if(data1.equalsIgnoreCase("VMN"))
  {
	  attP.setUpdate("Emergency leave");
	  attP.setComment("LR" + "+DateUtil.getCurrentDateIndianFormat()+" +"DateUtil.getCurrentTimeHourMin()");
	  
  }
  else if(data1.equalsIgnoreCase("SMS"))
  {
	  attP.setUpdate("SMS");
  }
  else if(data1.equalsIgnoreCase("Mail"))
  {
	  attP.setUpdate("Pre-Schedule Leave");
	  attP.setComment("NA");
  }
  else if(data1.equalsIgnoreCase("No Intimation"))
  {
	  attP.setUpdate("Uninformed");
	  attP.setComment("NA");
  }
	}
	dataList.add(attP);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return dataList;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<AttendancePojo> getAllData(SessionFactory connectionSpace) 
	{
	List<AttendancePojo> dataList=new ArrayList<AttendancePojo>();
	AttendancePojo attP=null;
	try {
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder(); 

	query .append("select emp.empId,emp.empName,dept.deptname,ar.date1,ar.attendence,ar.in_time,ar.out_time,ar.totalhour,ar.comment ");
	query.append(" FROM attendence_record AS ar  ");
	query.append(" INNER JOIN  compliance_contacts AS cc ON ar.empname=cc.id   ");
	query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
	query.append(" INNER JOIN department AS dept ON emp.deptname= dept.id ");
	query.append("WHERE ar.date1='"+DateUtil.getCurrentDateUSFormat()+"'");
	List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list!=null && list.size()>0) 
	{
	Object[] object=null;
	for (Iterator iterator = list.iterator(); iterator.hasNext();) 
	{
	object = (Object[]) iterator.next();
	if (object!=null) 
	{
	attP =new  AttendancePojo();
	attP.setEmpId(getValueWithNullCheck(object[0]));
	attP.setEmpname(getValueWithNullCheck(object[1]));
	attP.setDeptname(getValueWithNullCheck(object[2]));
	attP.setData1(getValueWithNullCheck(object[3]));
	attP.setAttendance(getValueWithNullCheck(object[4]));
	attP.setIntime(getValueWithNullCheck(object[5]));
	attP.setOuttime(getValueWithNullCheck(object[6]));
	attP.setTotalhour(getValueWithNullCheck(object[7]));
	attP.setComment(getValueWithNullCheck(object[8]));
	}
	dataList.add(attP);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return dataList;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<AttendancePojo> getApprovalStatus(SessionFactory connectionSpace) 
	{
	List<AttendancePojo> dataList=new ArrayList<AttendancePojo>();
	AttendancePojo attP=null;
	try {
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder(); 
	query .append("select emp.empId,emp.empName,dept.deptname,lr.status,lr.reason,lr.fdate,lr.tdate,lr.email  as authorized ,lr.odate,lr.ftime,lr.ttime  ");
	//query .append("select emp.empId,emp.empName,dept.deptname,lr.status,lr.reason,lr.fdate,lr.tdate,emp1.empName  as authorized ,lr.odate,lr.ftime,lr.ttime  ");
	query.append(" FROM leave_request AS lr  ");
	query.append(" LEFT JOIN  compliance_contacts AS cc ON lr.leaveunit=cc.id    ");
	query.append(" LEFT JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
	//query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id=lr.email ");
	query.append(" LEFT JOIN department AS dept ON emp.deptname= dept.id  ");
	query.append(" WHERE lr.date1='"+DateUtil.getCurrentDateUSFormat()+"'");
	System.out.println("queryTEST for appro ::::; "  +query);
	List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list!=null && list.size()>0) 
	{
	Object[] object=null;
	String totalDays=null;
	for (Iterator iterator = list.iterator(); iterator.hasNext();) 
	{
	object = (Object[]) iterator.next();
	if (object!=null) 
	{
	attP =new  AttendancePojo();
	attP.setEmpId(getValueWithNullCheck(object[0]));
	attP.setEmpname(getValueWithNullCheck(object[1]));
	attP.setDeptname(getValueWithNullCheck(object[2]));
	attP.setStatus(getValueWithNullCheck(object[3]));
	attP.setReason(getValueWithNullCheck(object[4]));
	if (object[5]!=null && object[6]!=null) 
	{
	attP.setFdate(DateUtil.convertDateToIndianFormat(object[5].toString()));
	System.out.println("attP.getFdatcvce" +attP.getFdate());
	attP.setTdate(DateUtil.convertDateToIndianFormat(object[6].toString()));
	System.out.println("attP.getTdatcvce" +attP.getTdate());
	int TotalNonWorkingDay = new WorkingHourHelper().getTotalNonWorkingDay(object[5].toString(), connectionSpace, cbt, "HR",object[6].toString());
	@SuppressWarnings("static-access")
	int totalworkingday=new DateUtil().getNoOfDays(object[6].toString(), object[5].toString());
	int nototalworkingday=totalworkingday+1;
	int totalcountLeavedays=nototalworkingday-TotalNonWorkingDay;
	attP.setTotalday(totalcountLeavedays + "Days");
	System.out.println("attP.getTotaldays for day" +attP.getTotalday());
	}
	else 
	{
	if (object[8]!=null) 
	{
	System.out.println("11111111>>>"+object[8]);
	System.out.println("22222222>>>"+object[9]);
	attP.setFdate(DateUtil.convertDateToIndianFormat(object[8].toString())+" " + object[9].toString());
	System.out.println("attP.getFdate" +attP.getFdate());
	attP.setTdate(object[10].toString());
	System.out.println("attP.getTdate" +attP.getTdate());
	attP.setTotalday(getTimeDiff(object[9].toString(),object[10].toString()) + "Hrs");
	System.out.println("attP.getTotaldays for hour" +attP.getTotalday());
	}
	}
	if (object[7]!=null) 
	{
	attP.setAutherized(object[7].toString());
	}
	else 
	{
	attP.setAutherized("NO Action");
	}
	}
	dataList.add(attP);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return dataList;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<AttendancePojo> getNewJoiners(SessionFactory connectionSpace) 
	{
	List<AttendancePojo> dataList=new ArrayList<AttendancePojo>();
	AttendancePojo attP=null;
	try {
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder(); 
	query .append("select emp.empId,emp.empName,dept.deptname,emp.mobOne,emp.address,emp.dob,emp.doa,emp.mobTwo,emp.emailIdOne ");
	query.append(" FROM employee_basic AS emp  ");
	query.append(" INNER JOIN groupinfo as grp on grp.id= emp.groupId   ");
	query.append(" INNER JOIN department AS dept ON emp.deptname= dept.id ");
	query.append("  where emp.flag!=1  AND grp.groupName='Employee' AND emp.date='"+DateUtil.getCurrentDateUSFormat()+"' ");
	List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list!=null && list.size()>0) 
	{
	Object[] object=null;
	for (Iterator iterator = list.iterator(); iterator.hasNext();) 
	{
	object = (Object[]) iterator.next();
	if (object!=null) 
	{
	attP =new  AttendancePojo();
	attP.setEmpId(getValueWithNullCheck(object[0]));
	attP.setEmpname(getValueWithNullCheck(object[1]));
	attP.setDeptname(getValueWithNullCheck(object[2]));
	attP.setMobile(getValueWithNullCheck(object[3]));
	attP.setAddress(getValueWithNullCheck(object[4]));
	attP.setDob(getValueWithNullCheck(object[5]));
	attP.setDoa(getValueWithNullCheck(object[6]));
	attP.setEmergencyNo(getValueWithNullCheck(object[7]));
	attP.setEmail_id(getValueWithNullCheck(object[8]));
	}
	dataList.add(attP);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return dataList;
	}
	
	
	
	
	 @SuppressWarnings("rawtypes")
	 public List<AttendancePojo> getEvents(SessionFactory connectionSpace)
	 {
	  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
	  try {
	   AttendancePojo adb;
	   CommonOperInterface cbt = new CommonConFactory().createInterface();
	   StringBuilder query1= new StringBuilder();
	   StringBuilder query2= new StringBuilder();
	 //  String from= DateUtil.returnListOfDatesBetweenTwoDates(DateUtil.getCurrentDateUSFormat(),DateUtil.getNewDate("day", +7, DateUtil.getCurrentDateUSFormat()));
	   List dates =DateUtil.returnListOfDatesBetweenTwoDates(DateUtil.getCurrentDateUSFormat(), DateUtil.getNewDate("day", +7, DateUtil.getCurrentDateUSFormat()));
	    query1.append("Select dob,empName from employee_basic WHERE flag!=1  and (");
	     
	   for (int i = 0; i < dates.size(); i++) 
	   {
	   String str[]=((String) dates.get(i)).split("-");
	   String finalString=str[1]+"-"+str[2];
	   if(i<dates.size()-1)
	   {
	   query1.append(" dob like '%"+finalString+"%'  or ");
	   }
	   else{
	   query1.append(" dob like '%"+finalString+"%'   ");
	   }
	   
	}
	  
	   query1.append(" ) order by dob desc");
	   System.out.println("query1@@@@@@@@@@@@@" +query1);
	  
       
	  
	   List birthdayData = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
	   System.out.println("query1  >>>>>>>>>>>   "  +query1);
	   if (birthdayData != null && birthdayData.size() > 0) {
	    Object[] object = null;
	    for (Iterator iterator = birthdayData.iterator(); iterator
	      .hasNext();) {
	     object = (Object[]) iterator.next();
	     if (object != null) {
	      adb = new AttendancePojo();
	      adb.setEname(object[1].toString());
	      adb.setData1(DateUtil.convertDateToIndianFormat(object[0].toString()));
	      adb.setComment("Birthday");
	      list.add(adb);
	     }
	    }
	   }
	   
	   query2.append("Select doa,empName from employee_basic WHERE flag!=1 and  (");
	     
	   for (int i = 0; i < dates.size(); i++) 
	   {
	   String str[]=((String) dates.get(i)).split("-");
	   String finalString=str[1]+"-"+str[2];
	   if(i<dates.size()-1)
	   {
	   query2.append(" doa like '%"+finalString+"%'  or  ");
	   }
	   else{
	   query2.append("  doa like '%"+finalString+"%'   ");
	   }
	  
	   
	}
	  
	   query2.append(" ) order by doa desc");
	   System.out.println("query2@@@@@@@@@@@@@" +query2);
	   List anniversaryData = cbt.executeAllSelectQuery(query2.toString(),
	     connectionSpace);
	   if (anniversaryData != null && anniversaryData.size() > 0) {
	    Object[] object = null;
	    for (Iterator iterator = anniversaryData.iterator(); iterator
	      .hasNext();) {
	     object = (Object[]) iterator.next();
	     if (object != null) {
	      adb = new AttendancePojo();
	      adb.setEname(object[1].toString());
	      adb.setData1(DateUtil.convertDateToIndianFormat(object[0].toString()));
	      adb.setComment("Anniversary");
	      list.add(adb);
	     }
	    }
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return list;
	 }
	
	 
	 
	 
	public String getValueWithNullCheck(Object value)
	{
	return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("static-access")
	public String writeDataInExcel(List<AttendancePojo> reportData,List<AttendancePojo> reportForApprovalStatus,List<AttendancePojo> reportForNewJoiners) throws Exception 
	{
	    String excelFileName =null;
	    String mergeDateTime=new DateUtil().mergeDateTime();
	    String totalLeaveData = "Attendance Summary Of "+DateUtil.getCurrentDateIndianFormat() ;
	
	    /*List orgData= new LoginImp().getUserInfomation("8", "IN");
	String orgName="";
	
	if (orgData!=null && orgData.size()>0) 
	{
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	orgName=object[0].toString();
	}
	}
	    */
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(totalLeaveData);
	Map<String, CellStyle> styles = createStyles(wb);
	
	Row headerRow = sheet.createRow(2);
	headerRow.setHeightInPoints(15);
	sheet.setFitToPage(true);
	sheet.setHorizontallyCenter(true);

	// Title Row
	Row titleRow = sheet.createRow(0);
	titleRow.setHeightInPoints(22);
	Cell titleCell = titleRow.createCell(0);
	titleCell.setCellValue("DreamSol");
	titleCell.setCellStyle(styles.get("title"));
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,14));
	
/*	// Sub Title Row
	Row subTitleRow = sheet.createRow(1);
	subTitleRow.setHeightInPoints(18);
	Cell subTitleCell = subTitleRow.createCell(0);
	subTitleCell.setCellValue("Daily Dar Report");
	subTitleCell.setCellStyle(styles.get("subTitle"));
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,14));*/
	
	// Creating the folder for holding the Excel files with the defined name
	excelFileName = new CreateFolderOs().createUserDir("hr_report")+ "/HR_SnapShot_"  + mergeDateTime+".xls";
	int header_first=3;
	int index=4;
	int header_1=header_first+(reportForApprovalStatus.size())+3;
 	int index1=header_1+1;
 	int header_2=header_1+(reportForNewJoiners.size())+3;
 	int index2=header_2+1;
 	
 	if (reportForApprovalStatus.size()<=0)
 	{
	 header_1=3;
	 	 index1=4;
	}
	else 
	{
	 header_1=header_first+(reportForApprovalStatus.size())+3;
	 	 index1=header_1+1;
	}
 	
 	if (reportForNewJoiners.size()<=0) 
 	{
	 header_2=header_1;
	 	 index2=index1;
	}
	else 
	{
	 header_2=header_1+(reportForNewJoiners.size())+3;
	 	 index2=header_2+1;
	}
 	
 	
 	if(reportData!=null && reportData.size()>0)
	 {
 	    // Header Row
	Row headerRow10 = sheet.createRow(header_first-1);
	headerRow10.setHeightInPoints(18);
	Cell headerTitleCell10 = headerRow10.createCell(0);
	headerTitleCell10.setCellValue("Attendance Summary Of "+DateUtil.getCurrentDateIndianFormat());
	headerTitleCell10.setCellStyle(styles.get("subTitle_AS"));
	sheet.addMergedRegion(new CellRangeAddress(header_first-1, header_first-1, 0,14));
	
 	// Code Start for showing the data for current day Attendance Summary
	HSSFRow rowHead = sheet.createRow((int) header_first);
	rowHead.createCell((int) 0).setCellValue(" Emp ID ");
	rowHead.createCell((int) 1).setCellValue(" Name ");
	rowHead.createCell((int) 2).setCellValue(" Department ");
	rowHead.createCell((int) 3).setCellValue(" Date");
	rowHead.createCell((int) 4).setCellValue(" Status");
	rowHead.createCell((int) 5).setCellValue(" Time In");
	rowHead.createCell((int) 6).setCellValue(" Time Out");
	rowHead.createCell((int) 7).setCellValue(" Total Hrs");
	rowHead.createCell((int) 8).setCellValue(" Comments ");
	
	  for (AttendancePojo CDRD:reportData)
 	   {
 	HSSFRow rowData=sheet.createRow((int)index);
	rowData.createCell((int) 0).setCellValue(CDRD.getEmpId());
	rowData.createCell((int) 1).setCellValue(CDRD.getEmpname());
	rowData.createCell((int) 2).setCellValue(CDRD.getDeptname());
	rowData.createCell((int) 3).setCellValue(DateUtil.convertDateToIndianFormat(CDRD.getData1()));
	rowData.createCell((int) 4).setCellValue(CDRD.getAttendance());
	rowData.createCell((int) 5).setCellValue(CDRD.getIntime());
	rowData.createCell((int) 6).setCellValue(CDRD.getOuttime());
	rowData.createCell((int) 7).setCellValue(CDRD.getTotalhour());
	rowData.createCell((int) 8).setCellValue(CDRD.getComment());
	index++;
 	sheet.autoSizeColumn(index);
	}
	 }
 	
 	if(reportForApprovalStatus!=null && reportForApprovalStatus.size()>0)
	 {
	    // Header Row
 	Row headerRow14 = sheet.createRow(header_1-1);
 	headerRow14.setHeightInPoints(18);
 	Cell headerTitleCell14 = headerRow14.createCell(0);
 	headerTitleCell14.setCellValue("Leave Request Approval Status of "+DateUtil.getCurrentDateIndianFormat());
 	headerTitleCell14.setCellStyle(styles.get("subTitle_LAS"));
	sheet.addMergedRegion(new CellRangeAddress(header_1-1, header_1-1, 0,14));
	
	// Code Start for showing the data for current day Leave Summary
	HSSFRow rowHead = sheet.createRow((int) header_1);
	rowHead.createCell((int) 0).setCellValue(" Emp ID ");
	rowHead.createCell((int) 1).setCellValue(" Name ");
	rowHead.createCell((int) 2).setCellValue(" Department ");
	rowHead.createCell((int) 3).setCellValue(" Status");
	rowHead.createCell((int) 4).setCellValue(" Reason");
	rowHead.createCell((int) 5).setCellValue(" From");
	rowHead.createCell((int) 6).setCellValue(" To");
	rowHead.createCell((int) 7).setCellValue(" Authorized By ");
	rowHead.createCell((int) 8).setCellValue(" Total Days");
	  for (AttendancePojo CDRD:reportForApprovalStatus)
	   {
	HSSFRow rowData=sheet.createRow((int)index1);
	rowData.createCell((int) 0).setCellValue(CDRD.getEmpId());
	rowData.createCell((int) 1).setCellValue(CDRD.getEmpname());
	rowData.createCell((int) 2).setCellValue(CDRD.getDeptname());
	rowData.createCell((int) 3).setCellValue(CDRD.getStatus());
	rowData.createCell((int) 4).setCellValue(CDRD.getReason());
	rowData.createCell((int) 5).setCellValue(DateUtil.convertDateToIndianFormat(CDRD.getFdate()));
	rowData.createCell((int) 6).setCellValue(DateUtil.convertDateToIndianFormat(CDRD.getTdate()));
	rowData.createCell((int) 7).setCellValue(CDRD.getAutherized());
	rowData.createCell((int) 8).setCellValue(DateUtil.findDaysForDates(CDRD.getTdate(), (CDRD.getFdate())));
	index1++;
	sheet.autoSizeColumn(index1);
	}
	 }
 	
	// Code Start for showing the data for current day New Joiners Summary
	  if (reportForNewJoiners!=null && reportForNewJoiners.size()>0) 
	  {
	  	Row headerRow13 = sheet.createRow(header_2-1);
	  	headerRow13.setHeightInPoints(18);
	Cell headerTitleCell13 = headerRow13.createCell(0);
	headerTitleCell13.setCellValue("New Joiners Status of " +DateUtil.getCurrentDateIndianFormat());
	headerTitleCell13.setCellStyle(styles.get("subTitle_NJ"));
	sheet.addMergedRegion(new CellRangeAddress(header_2-1, header_2-1, 0,14));
	
	  	HSSFRow rowHead1 = sheet.createRow((int) header_2);
	rowHead1.createCell((int) 0).setCellValue(" Emp ID ");
	rowHead1.createCell((int) 1).setCellValue(" Name ");
	rowHead1.createCell((int) 2).setCellValue(" Department ");
	rowHead1.createCell((int) 3).setCellValue(" Mobile No");
	rowHead1.createCell((int) 4).setCellValue(" Address");
	rowHead1.createCell((int) 5).setCellValue(" DOB");
	rowHead1.createCell((int) 6).setCellValue(" DOA");
	rowHead1.createCell((int) 7).setCellValue(" Emergency No.");
	rowHead1.createCell((int) 8).setCellValue(" EMail ID");
	  	
	   for (AttendancePojo CDRD:reportForNewJoiners)
	 	    {
	 	HSSFRow rowData=sheet.createRow((int)index2);
	 	rowData.createCell((int) 0).setCellValue(CDRD.getEmpId());
	rowData.createCell((int) 1).setCellValue(CDRD.getEmpname());
	rowData.createCell((int) 2).setCellValue(CDRD.getDeptname());
	rowData.createCell((int) 3).setCellValue(CDRD.getMobile());
	rowData.createCell((int) 4).setCellValue(CDRD.getAddress());
	rowData.createCell((int) 5).setCellValue(CDRD.getDob());
	rowData.createCell((int) 6).setCellValue(DateUtil.convertDateToIndianFormat(CDRD.getDoa()));
	rowData.createCell((int) 7).setCellValue(CDRD.getEmergencyNo());
	rowData.createCell((int) 8).setCellValue(CDRD.getEmail_id());
	index2++;
	 	sheet.autoSizeColumn(index2);
	  }
	  }
	
 	try
 	 {
 	FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
	 }
 	catch(Exception e)
 	 {
 	e.printStackTrace();
 	 }
 	return excelFileName;
}
	private Map<String, CellStyle> createStyles(Workbook wb) {
	Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	CellStyle style;

	// Title Style
	Font titleFont = wb.createFont();
	titleFont.setFontHeightInPoints((short) 16);
	titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(titleFont);
	style.setWrapText(true);
	styles.put("title", style);

	// Sub Title Style
	Font subTitleFont = wb.createFont();
	subTitleFont.setFontHeightInPoints((short) 14);
	subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont);
	style.setWrapText(true);
	styles.put("subTitle", style);
	
	
	// Sub Title Style for Pending Tickets
	Font subTitleFont_PN = wb.createFont();
	subTitleFont_PN.setFontHeightInPoints((short) 14);
	subTitleFont_PN.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.RED.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_PN);
	style.setWrapText(true);
	styles.put("subTitle_AS", style);
	
	
	// Sub Title Style for Resolved Tickets
	Font subTitleFont_RS = wb.createFont();
	subTitleFont_RS.setFontHeightInPoints((short) 14);
	subTitleFont_RS.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_RS);
	style.setWrapText(true);
	styles.put("subTitle_LAS", style);
	
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_HP = wb.createFont();
	subTitleFont_HP.setFontHeightInPoints((short) 14);
	subTitleFont_HP.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.ORANGE.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_HP);
	style.setWrapText(true);
	styles.put("subTitle_NJ", style);
	
	
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_SN = wb.createFont();
	subTitleFont_SN.setFontHeightInPoints((short) 14);
	subTitleFont_SN.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.YELLOW.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_SN);
	style.setWrapText(true);
	styles.put("subTitle_SN", style);
	
	
	
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_IG = wb.createFont();
	subTitleFont_IG.setFontHeightInPoints((short) 14);
	subTitleFont_IG.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.LAVENDER.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_IG);
	style.setWrapText(true);
	styles.put("subTitle_IG", style);
	
	
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_SH = wb.createFont();
	subTitleFont_SH.setFontHeightInPoints((short) 10);
	subTitleFont_SH.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFont(subTitleFont_SH);
	style.setWrapText(true);
	styles.put("subTitle_SH", style);
	
	

	Font headerFont = wb.createFont();
	headerFont.setFontHeightInPoints((short) 11);
	headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(headerFont);
	styles.put("header", style);

	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_LEFT);
	style.setWrapText(true);
	style.setBorderRight(CellStyle.BORDER_THIN);
	style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderLeft(CellStyle.BORDER_THIN);
	style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderTop(CellStyle.BORDER_THIN);
	style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderBottom(CellStyle.BORDER_THIN);
	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	styles.put("cell", style);
	return styles;
	}
	
	
	public String getConfigMailForReport(List<AttendancePojo> reportData,List<AttendancePojo> reportForApprovalStatus,List<AttendancePojo> reportForNewJoiners,List<AttendancePojo> reportDataBeforeDate,List<AttendancePojo> reportForEvents,List<AttendancePojo> reportForAbsentData) throws Exception  
	{
	
	    StringBuilder mailtext=new StringBuilder("");
	      /*HttpServletRequest req = ServletActionContext.getRequest();
        String ip=InetAddress.getLocalHost().getHostAddress();
        int portNo=req.getServerPort();*/
  	    String URL="<a href=http://over2cloud.co.in/>Click Here</a>";
	    mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td></tr></tbody></table>");
        mailtext.append("<b>Hello!!!</b>");
        mailtext.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Please find the following summary snapshot mapped for your analysis. You may find more details in the attached excel or for dynamic graphical analysis we request, you to please ");
        mailtext.append(URL);
        mailtext.append (" and login with your respective credentials</td></tr></tbody></table>");
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Attendance Summary of  " +DateUtil.getCurrentDateIndianFormat()+ " </b></td></tr></tbody></table>");
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Date</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;In</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;Out</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total&nbsp;Time</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong> Comment</strong></td> </tr>");
        
       if (reportData!=null && reportData.size()>0)
       {
      	 boolean status = false;
       	 int i=0;
	     	 for(AttendancePojo FBP:reportData)
	       {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.getCurrentDateIndianFormat()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAttendance()+"</td>");
	  status=DateUtil.getTimeEntryForAttendance(FBP.getIntime(), "09:30");
	  if (status)
	{
	 mailtext.append("<td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	else
	{
	 mailtext.append("<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	  status=false;
	  status=DateUtil.getTimeEntryForAttendance(FBP.getOuttime(), "18:30");
	  if (!status)
	{
	  	mailtext.append(" <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	else
	{
	mailtext.append(" <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	  
	  mailtext.append(" <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTotalhour()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td></tr>") ; 
	}
	else
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.getCurrentDateIndianFormat()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAttendance()+"</td> ");
	status=DateUtil.getTimeEntryForAttendance(FBP.getIntime(), "09:30");
	if (status)
	{
	mailtext.append("<td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	else
	{
	mailtext.append("<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	status=false;
	status=DateUtil.getTimeEntryForAttendance(FBP.getOuttime(), "18:30");
	  if (!status)
	{
	  	mailtext.append("<td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	else
	{
	mailtext.append("<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	
	  mailtext.append("<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTotalhour()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td></tr>");
	}
	       }
       }
      
       mailtext.append("</tbody></table>");
       if (reportData.size()==0) 
       {
	   mailtext.append("<center>There are no records for the day .</center>");
	   }
       mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Attendance Summary of  " +DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()))+ " </b></td></tr></tbody></table>");
       mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
       mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Date</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;In</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;Out</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total&nbsp;Time</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong> Comment</strong></td> </tr>");
       
       if (reportDataBeforeDate!=null && reportDataBeforeDate.size()>0)
       {
      	 boolean status = false;
       	 int i=0;
	     	 for(AttendancePojo FBP:reportDataBeforeDate)
	       {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()))+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAttendance()+"</td>");
	  status=DateUtil.getTimeEntryForAttendance(FBP.getIntime(), "09:30");
	  if (status)
	{
	 mailtext.append("<td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	else
	{
	 mailtext.append("<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	  status=false;
	  status=DateUtil.getTimeEntryForAttendance(FBP.getOuttime(), "18:30");
	  if (!status)
	{
	  	mailtext.append(" <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	else
	{
	mailtext.append(" <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	  
	  mailtext.append(" <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTotalhour()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td></tr>") ; 
	}
	else
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()))+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAttendance()+"</td> ");
	status=DateUtil.getTimeEntryForAttendance(FBP.getIntime(), "09:30");
	if (status)
	{
	mailtext.append("<td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	else
	{
	mailtext.append("<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getIntime()+"</td>");
	}
	status=false;
	status=DateUtil.getTimeEntryForAttendance(FBP.getOuttime(), "18:30");
	  if (!status)
	{
	  	mailtext.append("<td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	else
	{
	mailtext.append("<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOuttime()+"</td>");
	}
	
	  mailtext.append("<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTotalhour()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td></tr>");
	}
	       }
       }
      
       mailtext.append("</tbody></table>");
       if (reportDataBeforeDate.size()==0) 
       {
	   mailtext.append("<center>There are no records for the day.</center>");
	   }
       
       
       mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Absent Records of  " +DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()))+ "  </b></td></tr></tbody></table>");
       mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
       mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td>"
       	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td>"
       	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td>"
       + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Leave Type</strong></td>"
       	+ "</tr>");
      
       
       if (reportForAbsentData!=null && reportForAbsentData.size()>0)
       {
       	
      	 int i=0;
    	 for(AttendancePojo FBP:reportForAbsentData)
      	 {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getUpdate()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td>"
				+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td>"
				+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td>"
				+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getUpdate()+"</td></tr>");
				}
        }
      }
       mailtext.append("</tbody></table>");
       if (reportForEvents.size()==0) 
       {
       	mailtext.append("<center>There are no absent records for the day .</center>");
	}
       mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Leave Request Approval Status of " +DateUtil.getCurrentDateIndianFormat()+ " </b></td></tr></tbody></table>");
       mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
       mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Reason</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>From</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Authorized By</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total Days/Hrs</strong></td> </tr>");
      
       
       
       
       if (reportForApprovalStatus!=null && reportForApprovalStatus.size()>0)
       {
      	
       	 int i=0;
     	 for(AttendancePojo FBP:reportForApprovalStatus)
       	 {
	int k=++i;
	if(k%2!=0)
	{
	
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getReason()+"</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFdate()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTdate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAutherized()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTotalday()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getReason()+"</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFdate()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTdate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAutherized()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTotalday()+"</td></tr>");
	}
         }
       }
      
    	   
      
        mailtext.append("</tbody></table>");
        if (reportForApprovalStatus.size()==0) 
        {
 	   mailtext.append("<center>There are no leaves requested or approved for today.</center>");
	}
        mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>New Joiners Status of " +DateUtil.getCurrentDateIndianFormat()+ " </b></td></tr></tbody></table>");
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mobile no</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Address</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>DOB</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>DOA</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emergency&nbsp;No</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong> Email_id</strong></td> </tr>");
       
        if (reportForNewJoiners!=null && reportForNewJoiners.size()>0)
        {
       	 int i=0;
     	 for(AttendancePojo FBP:reportForNewJoiners)
       	 {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getMobile()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAddress()+"</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDob()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDoa()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmergencyNo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmail_id()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpId()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmpname()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDeptname()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getMobile()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAddress()+"</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDob()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDoa()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmergencyNo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEmail_id()+"</td></tr>");
	}
         }
       }
        mailtext.append("</tbody></table>");
        if (reportForNewJoiners.size()==0) 
        {
        	mailtext.append("<center>There are no New Joiners for Today.</center>");
	}
       
        mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Events From " +DateUtil.getCurrentDateIndianFormat()+ " to "+DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", +7, DateUtil.getCurrentDateUSFormat()))+" </b></td></tr></tbody></table>");
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td>"
        	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Event</strong></td>"
        	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Date</strong></td></tr>");
       
        
        if (reportForEvents!=null && reportForEvents.size()>0)
        {
        	
       	 int i=0;
     	 for(AttendancePojo FBP:reportForEvents)
       	 {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEname()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getData1()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getEname()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+"</td>"
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getData1()+"</td></tr>");
	}
         }
       }
        mailtext.append("</tbody></table>");
        if (reportForEvents.size()==0) 
        {
        	mailtext.append("<center>No events Schedule for Next Seven Days</center>");
	}
        
       
        
        
        mailtext.append("<br><b>Thanks & Regards,</b>");
        mailtext.append("<br><b>Human Resource & Administration Team</b>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
     
       	
       	
        return mailtext.toString() ; 
}
	
	
private static String getTimeDiff(String fromTime, String toTime){
	
	 short fHour = 0;
	 short fMin = 0;
	 short tHour = 0;
	 short tMin = 0;
	 
	 
	 short finalMin = 0;
	 short finalHour = 0;
	 
	 String returnVal = "";
	 
	if(fromTime != null && fromTime.indexOf(":") > 0){
	  String[] value = fromTime.split(":");
	fHour = Short.parseShort(value[0]);
	fMin = Short.parseShort(value[1]);
	}
	

	if(toTime != null && toTime.indexOf(":") > 0){
	String[] value = toTime.split(":");
	tHour = Short.parseShort(value[0]);
	tMin = Short.parseShort(value[1]);
	}
	
	//Check from time range...
	if(tHour > fHour){ // Return from else
	
	if(tMin < fMin){ //Checking Minute Value id less than add 60 min...
	tMin += (short) 60;
	tHour -= (short) 1;
	}
	
	finalMin  = (short) (tMin -  fMin); 
	finalHour = (short) (tHour - fHour);
	returnVal = finalHour+":"+finalMin;
	}else{
	returnVal = "Invalid Selection";
	}
	
	return returnVal;
}

public static void main(String[] args) {
	System.out.println(getTimeDiff("14:30","15:00"));
}
}
