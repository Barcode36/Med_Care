package com.Over2Cloud.ctrl.helpdesk.dashboard;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.helpdesk.BeanUtil.HelpdeskDashboardPojo;

@SuppressWarnings("serial")
public class ManagementDashHelper extends GridPropertyBean
{
	List<FeedbackPojo> feedbackList = null;
	List<HelpdeskDashboardPojo> dataList=null; 
	private String dataCheck="";
	private String feedStatus;
	private String fromDate;
	private String toDate;
	private String dashFor;
	private String d_subdept_id;
	private String dataFlag;
	private String level;
	private String excelFileName;
	private FileInputStream excelStream;
	private String dept_subdeptid;
	private Map<String, String> columnMap;
	private String[] columnNames;
	public String beforeCategoryView()
    { 
    	return SUCCESS;
    }
	
	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();
				columnMap.put("feedback.ticket_no", "Ticket No");
				columnMap.put("dept2.deptName AS todept ", "To Department");
				columnMap.put("feedtype.fbType  as tosubdept", "Sub Department");
				columnMap.put("emp.empName AS allot_to", "Alloted To");
				columnMap.put("catg.categoryName", "Category");
				columnMap.put("subcatg.subCategoryName", "Sub-category");
				columnMap.put("feedback.feed_brief", "Feddback-Brief");
				columnMap.put("fld.roomno", "Location");
				columnMap.put("dept1.deptName AS bydept", "By Department");
				columnMap.put("feedback.feed_by", "Feedback By");
				columnMap.put("feedback.feed_by_mobno", " Mobile No");
				columnMap.put("feedback.feed_by_emailid", "Email Id");
				columnMap.put("feedback.open_date", "Open Date");
				columnMap.put("feedback.open_time", " Open Time");
				columnMap.put("feedback.Addr_date_time", " Address Date Time");
				columnMap.put("feedback.escalation_date", "Escalation Date");
				columnMap.put("feedback.escalation_time", "Escalation Time");
				columnMap.put("feedback.level", "TAT Status");
				columnMap.put("feedback.status", "Current Status");
				columnMap.put("feedback.feed_registerby", "Feed Register By");
				if (!feedStatus.equalsIgnoreCase("Pending"))
				{
					columnMap.put("history.action_date", " Action Date");
					columnMap.put("history.action_time", "Action Time");
					columnMap.put("history.action_duration","Action Duration"); 
					columnMap.put("history.action_remark", " Action Remarks");
					columnMap.put("emp1.empName AS Actionby", "Action By");
					if (feedStatus.equalsIgnoreCase("Snooze"))
					{
						columnMap.put("history.sn_upto_date", "Snooze Upto Date");
						columnMap.put("history.sn_upto_time", "Snooze Upto Time");
					}
				}
				 
				  if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
				 
				return SUCCESS;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String downloadExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Feedback Status Detail";
		if (sessionFlag)
		{
			try
			{
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userType = null, empId = null, userMobno = null,empname=null;
				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();
				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

				StringBuilder query = new StringBuilder();
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userMobno = CUA.getValueWithNullCheck(object[1]);
						empname=object[0].toString();
					}
				}
				keyList = Arrays.asList(columnNames);
				Map<String, String> tempMap = new LinkedHashMap<String, String>();
				tempMap = (Map<String, String>) session.get("columnMap");
				if (session.containsKey("columnMap"))
					session.remove("columnMap");

				for (int index = 0; index < keyList.size(); index++)
				{
					titleList.add(tempMap.get(keyList.get(index)));
				}
					List dataList = null;
						query.append("select feedback.ticket_no,dept2.deptName as todept,feedtype.fbType as tosubdept,");
						query.append("emp.empName as allot_to,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
						query.append("frd.roomno,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
						query.append("feedback.open_date,feedback.open_time,feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,");
						query.append("feedback.level,feedback.status,feedback.feed_registerby ");
					
						if (!feedStatus.equalsIgnoreCase("Pending"))
						{
							query.append(",history.action_date,history.action_time,history.action_duration,history.action_remark, emp1.empName as action_by ");
							if (feedStatus.equalsIgnoreCase("Snooze"))
							{
								query.append(",history.sn_upto_date,history.sn_upto_time ");
							}
						}
						query.append(" from feedback_status_new as feedback");
						query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
						query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
						query.append(" inner join department dept2 on dept2.id = feedback.to_dept_subdept");
						query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
						query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
						query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
						query.append(" inner join floor_room_detail as frd on frd.id = feedback.location ");
						if(!feedStatus.equalsIgnoreCase("Pending"))
						{
							query.append(" inner join feedback_status_history as history on feedback.id=history.feedId");	
							query.append(" inner join employee_basic emp1 on history.action_by= emp1.id");
						}
						if(feedStatus!=null && !feedStatus.equalsIgnoreCase(""))
						{
							query.append(" where feedback.status='" + feedStatus + "'");
						}
						else
						{	
							query.append(" where feedback.id!=0 ");
						}	
						if(feedStatus.equalsIgnoreCase("Resolved") || feedStatus.equalsIgnoreCase("Snooze") || feedStatus.equalsIgnoreCase("Ignore"))
						{
							query.append(" and history.status=feedback.status ");	
						}
						else if(!feedStatus.equalsIgnoreCase("Pending"))
						{
							query.append(" and history.feedId=feedback.id ");
						}

						if(dataFlag!=null && dataFlag.equals("T"))
						{
							if (dashFor != null && !dashFor.equals("") && dashFor.equals("M"))
							{
								query.append(" and dept2.id='" + dept_subdeptid + "'");
							}
							else if (dashFor != null && !dashFor.equals("") && dashFor.equals("H"))
							{
								query.append(" and feedtype.dept_subdept='" + dept_subdeptid + "'");
							}
							else if (dashFor != null && !dashFor.equals("") && dashFor.equals("N"))
							{
								query.append(" and subdept2.id='" + dept_subdeptid + "' and feedback.feed_by='" + empname + "'");
							}
						}
						//For Levels
						else if(dataFlag!=null && dataFlag.equals("L"))
						{
							if (dashFor != null && !dashFor.equals("") && dashFor.equals("M"))
							{
								query.append(" and feedback.level='" + level + "'");
							}
							
							else if (dashFor != null && !dashFor.equals("") && dashFor.equals("H"))
							{
								query.append(" and dept2.id='" + dept_subdeptid + "' and feedback.level='" + level + "'");
							}
							
							else if (dashFor != null && !dashFor.equals("") && dashFor.equals("N"))
							{
								query.append("  and feedback.feed_by='" + empname + "' and feedback.level='" + level + "'");
							}
						}
						//For Category
						else if(dataFlag!=null && dataFlag.equals("C"))
						{
							query.append(" and feedback.subCatg='" + dept_subdeptid + "'");
						}
						
						if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
						{
							query.append(" and");

							if (searchOper.equalsIgnoreCase("eq"))
							{
								query.append(" " + searchField + " = '" + searchString + "'");
							}
							else if (searchOper.equalsIgnoreCase("cn"))
							{
								query.append(" " + searchField + " like '%" + searchString + "%'");
							}
							else if (searchOper.equalsIgnoreCase("bw"))
							{
								query.append(" " + searchField + " like '" + searchString + "%'");
							}
							else if (searchOper.equalsIgnoreCase("ne"))
							{
								query.append(" " + searchField + " <> '" + searchString + "'");
							}
							else if (searchOper.equalsIgnoreCase("ew"))
							{
								query.append(" " + searchField + " like '%" + searchString + "'");
							}
						}
						query.append(" AND feedback.open_date BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"' ");
						query.append(" GROUP BY feedback.id ORDER BY feedback.id DESC");
						// System.out.println("sel id >>>>>>>>>>>   "+getSel_id());
						//System.out.println("query%%%%%   "+query);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
						{

							excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);
						}
						if (excelFileName != null)
						{
							excelStream = new FileInputStream(excelFileName);
						}
						 		//}
					returnResult = SUCCESS;
				//}
				
			
			}	
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	@SuppressWarnings("rawtypes")
	public String getFeedbackDetailNew()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			dataList = new ArrayList<HelpdeskDashboardPojo>();
			
			List data=null;
			String dept_id="",empname="";
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "2");
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					empname=object[0].toString();
					dept_id=object[3].toString();
				}
			}
			
			data=getDataForDashboard(feedStatus,fromDate,toDate,dashFor, d_subdept_id,dataFlag,"HDM",empname,level,searchField,searchString,searchOper, connectionSpace);
		
			if(data!=null && data.size()>0){ 
			setRecords(data.size());
			int to = (getRows() * getPage());
			@SuppressWarnings("unused")
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
				if (deptLevel.equals("2")) {
					dataList=setFeedbackValue(data,deptLevel,getFeedStatus());
					//System.out.println("size feedback:::::"+dataList.size());
					dataCheck="Confirm";
				}
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			}
			returnResult = SUCCESS;
		}
		catch(Exception e)
		{
			 addActionError("Ooops!!! There is some problem in getting Feedback Data");
			 e.printStackTrace();
		}
		}
		else {
			returnResult=LOGIN;
		}
		return returnResult;
	}
	@SuppressWarnings("unchecked")
	public List<HelpdeskDashboardPojo> setFeedbackValue(List feedValue, String deptLevel, String feedStatus)
	  {
	

	List<HelpdeskDashboardPojo> feedList = new ArrayList<HelpdeskDashboardPojo>();
	if(feedValue!=null && feedValue.size()>0) 
	 {
	for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	HelpdeskDashboardPojo  fbp = new HelpdeskDashboardPojo();
	 fbp.setId((Integer)obdata[0]);
	 fbp.setTicket_no(obdata[1].toString());
	 fbp.setFeedback_by_dept(obdata[9].toString());
	
	if (obdata[10]!=null && !obdata[10].toString().equals("")) {
	fbp.setFeed_by(DateUtil.makeTitle(obdata[10].toString()));
	}
	else {
	 fbp.setFeed_by("NA");
	}
	 
	if (obdata[11]!=null && !obdata[11].toString().equals("") && (obdata[11].toString().startsWith("9") || obdata[11].toString().startsWith("8") || obdata[11].toString().startsWith("7")) && obdata[11].toString().length()==10) {
	fbp.setFeedback_by_mobno(obdata[11].toString());
	    }
	else {
	 fbp.setFeedback_by_mobno("NA");
	}
	
	if (obdata[12]!=null && !obdata[12].toString().equals("")) {
	fbp.setFeedback_by_emailid(obdata[12].toString());
	        }
	    else {
	    	fbp.setFeedback_by_emailid("NA");
	    }
	 
	fbp.setFeedback_to_dept(obdata[2].toString());
	fbp.setFeedback_to_subdept(obdata[3].toString());
	fbp.setFeedback_allot_to(DateUtil.makeTitle(obdata[4].toString()));
	fbp.setFeedtype(obdata[5].toString());
	fbp.setFeedback_catg(obdata[5].toString());
	fbp.setFeedback_subcatg(obdata[6].toString());
	if (obdata[7]!=null && !obdata[7].toString().equals("")) {
	fbp.setFeed_brief(obdata[7].toString());
	        }
	    else {
	    	fbp.setFeed_brief("NA");
	    }
	
	if (obdata[8]!=null && !obdata[8].toString().equals("")) {
	fbp.setLocation(obdata[8].toString());
	        }
	    else {
	    	fbp.setLocation("NA");
	    }
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[13].toString()));
	fbp.setOpen_time(obdata[14].toString().substring(0, 6));
	if (obdata[15]!=null && !obdata[15].toString().equals("")) {
	 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[15].toString().substring(0, 10))+" / "+ obdata[15].toString().substring(11, 16));
	 }
	 else {
	 fbp.setAddressingTime("NA");
	 } 
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString())+" / "+obdata[17].toString());
	  
	fbp.setLevel(obdata[18].toString());
	fbp.setStatus(obdata[19].toString());
	fbp.setFeed_registerby(DateUtil.makeTitle(obdata[20].toString()));
	
	
	  
	feedList.add(fbp);
	}
	}
	return feedList;
	  }	
	public String viewCategoryDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				//System.out.println("Category Id is   "+id);
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				
				feedbackList = new ArrayList<FeedbackPojo>();
				
				@SuppressWarnings("rawtypes")
				List data = HUA.getCategoryDetail(id);
					
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					
				 feedbackList = HUH.setCategoryDetail(data);
				 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes" )
	public List getDashboardCounter(String qryfor,String status,String empName,String dept_subdeptid,SessionFactory connection,String fromDate, String toDate)
	 {
		List  dashDeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		// Query for getting SubDept List
	     if (qryfor.equalsIgnoreCase("dept")) {
	    	 query.append("select  feedback.status,count(*) from feedback_status_new as feedback ");
	    	 query.append(" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id");
	    	 query.append(" inner join department dept on subdept.deptid= dept.id");
	    	 if (status.equalsIgnoreCase("All")) {
	    		 query.append(" where feedback.to_dept_subdept="+dept_subdeptid+"  and feedback.open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"' group by feedback.status order by feedback.status");
			
	    	 }
	    	 /*else if (status.equalsIgnoreCase("Resolved")) {
	    		 
	    		 query.append(" where dept.id="+dept_subdeptid+" and feedback.resolve_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'  group by feedback.status order by feedback.status");
			}*/
	     }
	    else if (qryfor.equalsIgnoreCase("subdept")) {
	    	 query.append("select  feedback.status,count(*) from feedback_status_new as feedback ");
	    	
	    	 if (status.equalsIgnoreCase("All")) {
	    		 query.append(" inner join feedback_subcategory subcat on subcat.id=feedback.subCatg ");
		    	 query.append(" inner join feedback_category cat on cat.id=subcat.categoryName ");
		    	 query.append(" inner join feedback_type feedType on feedType.id=cat.fbType ");
		    	 query.append(" inner join subdepartment subdept on subdept.id= feedType.dept_subdept ");
	    		 query.append(" where feedType.dept_subdept="+dept_subdeptid+" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'   group by feedback.status order by feedback.status");
			}
	    	 /*else if (status.equalsIgnoreCase("Resolved")) {
	    		 query.append(" inner join feedback_subcategory subcat on subcat.id=feedback.subCatg ");
		    	 query.append(" inner join feedback_category cat on cat.id=subcat.categoryName ");
		    	 query.append(" inner join feedback_type feedType on feedType.id=cat.fbType ");
		    	 query.append(" inner join subdepartment subdept on subdept.id= feedType.dept_subdept ");
		    	 query.append(" inner join feedback_status_history as feedhis on feedhis.feedId=feedback.id ");
	    		 query.append(" where feedType.dept_subdept="+dept_subdeptid+" and feedback.status='Resolved' and feedhis.status='Resolved' and feedhis.action_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'   group by feedback.status order by feedback.status");
			}*/
	    	 else if (status.equalsIgnoreCase("Normal")) {
	    		 query.append(" where feedback.feed_by='"+empName+"' and subdept.id="+dept_subdeptid+" and feedback.resolve_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'   group by feedback.status order by feedback.status");
			}
	    	 else if (status.equalsIgnoreCase("empResolved")) {
	    		 query.append(" where feedback.feed_by='"+empName+"' and subdept.id="+dept_subdeptid+" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'   group by feedback.status order by feedback.status");
			}
	    }
	    // System.out.println("@@@@@@@@@@@@"+query);
		
			dashDeptList=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	
		return dashDeptList;
	 }
	
	//Abhay
	public List getCounterDataList(int  deptId, String fromDate, String toDate,String status_for, SessionFactory connectionSpace) 
	{
		StringBuilder query=new StringBuilder();
	    query.append("select  count(*),open_date from feedback_status_new as feedback ");
	    query.append(" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id");
	    query.append(" inner join department dept on subdept.deptid= dept.id");
	    query.append(" where feedback.to_dept_subdept="+deptId+"  and feedback.open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"' ");
	    if(status_for!=null && !status_for.equalsIgnoreCase("Total"))
	    {
	    	 query.append(" AND feedback.status='"+status_for+"'");	
	    }
	    query.append(" group by feedback.open_date order by feedback.open_date");
	    List list=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	    return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getDataForDashboard(String status, String fromDate, String toDate, String dashFor, String dept_subdeptid, String dataFlag, String module, String empname, String level,
			String searchField, String searchString, String searchOper, SessionFactory connectionSpace)
	{// System.out.println("Emp name  :::::::::::::::::::>>>>>>>>>>>>>>> "+ empname);
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			query.append("select feedback.id,feedback.ticket_no,dept2.deptName as todept,feedtype.fbType as tosubdept,");
			query.append("emp.empName as allot_to,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
			query.append("frd.roomno,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
			query.append("feedback.open_date,feedback.open_time,feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,");
			query.append("feedback.level,feedback.status,feedback.feed_registerby ");
		/*
			if (!status.equalsIgnoreCase("Pending"))
			{
				query.append(",history.action_date,history.action_time,history.action_duration,history.action_remark, emp1.empName as action_by ");
				if (status.equalsIgnoreCase("Snooze"))
				{
					query.append(",history.sn_upto_date,history.sn_upto_time ");
				}
			}*/
			
			query.append(" from feedback_status_new as feedback");
			query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			
			//query.append(" inner join department dept2 on dept2.id = feedback.to_dept_subdept");
			//query.append(" INNER JOIN subdepartment subdept ON feedback.to_dept_subdept= subdept.id ");
			query.append(" inner join department dept2 on dept2.id = feedback.to_dept_subdept");
			
			query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			query.append(" inner join floor_room_detail as frd on frd.id = feedback.location ");
			/*if(!status.equalsIgnoreCase("Pending"))
			{
				query.append(" inner join feedback_status_history as history on feedback.id=history.feedId");	
				query.append(" inner join employee_basic emp1 on history.action_by= emp1.id");
			}*/
			if(status!=null && !status.equalsIgnoreCase("") && !status.equalsIgnoreCase("All"))
			{
				query.append(" where feedback.status='" + status + "'");
			}
			else
			{	
				query.append(" where feedback.status is not null ");
			}	
			/*if(status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Snooze") || status.equalsIgnoreCase("Ignore") || status.equalsIgnoreCase("All"))
			{
				//query.append(" and history.status=feedback.status ");	
			}
			else if(!status.equalsIgnoreCase("Pending"))
			{
				query.append(" and history.feedId=feedback.id ");
			}*/
			//System.out.println(dashFor+"      "+dept_subdeptid);
			if(dataFlag!=null && (dataFlag.equals("T") || dataFlag.equals("Re")))
			{
				if (dashFor != null && !dashFor.equals("") && dashFor.equals("M"))
				{
					if(dept_subdeptid.equals("-1"))
					{
						query.append(" and feedback.to_dept_subdept IS Not NULL");
					}
					else{
					query.append(" and feedback.to_dept_subdept='" + dept_subdeptid + "'");
					}
				}
				else if (dashFor != null && !dashFor.equals("") && dashFor.equals("H"))
				{
					if(dept_subdeptid.equals("-1"))
					{
						query.append(" and feedtype.dept_subdept IS Not NULL");
					}
					else{
						query.append(" and feedtype.dept_subdept='" + dept_subdeptid + "'");	
					}
				}
				else if (dashFor != null && !dashFor.equals("") && dashFor.equals("N"))
				{
					query.append(" and feedback.to_dept_subdept='" + dept_subdeptid + "' and feedback.feed_by='" + empname + "'");
				}
			}
			//For Levels
			else if(dataFlag!=null && dataFlag.equals("L"))
			{
				if (dashFor != null && !dashFor.equals("") && dashFor.equals("M"))
				{
					if(level.equals("-1") && !dept_subdeptid.equalsIgnoreCase("All"))
					{
						query.append(" and feedback.level='"+dept_subdeptid+"' ");	
					}
					else if(level.equals("-1") && dept_subdeptid.equalsIgnoreCase("All"))
					{
						//query.append(" and feedback.to_dept_subdept='" + level + "' ");	
					}
					else if(!level.equals("-1") && dept_subdeptid.equalsIgnoreCase("All"))
					{
						query.append(" and feedback.to_dept_subdept='" + level + "' ");	
					}
					else{
						query.append(" and feedback.to_dept_subdept='" + level + "' and feedback.level='"+dept_subdeptid+"' ");	
					}
				}
				
				else if (dashFor != null && !dashFor.equals("") && dashFor.equals("H"))
				{
					if(level.equals("-1"))
					{
						query.append(" and feedback.to_dept_subdept='" + dept_subdeptid + "' ");	
					}
					else if(level.equals("-1") && dept_subdeptid.equalsIgnoreCase("All"))
					{
						//query.append(" and feedback.level='" + level + "' ");	
					}
					else if(!level.equals("-1") && dept_subdeptid.equalsIgnoreCase("All"))
					{
						query.append(" and feedback.level='" + level + "' ");	
					}
					else{
					query.append(" and feedtype.dept_subdept='" +level+ "' and feedback.level='" + dept_subdeptid + "'");
					}
				}
				
				else if (dashFor != null && !dashFor.equals("") && dashFor.equals("N"))
				{
					if(level.equals("-1"))
					{
						query.append(" and feedback.feed_by='" + empname + "' ");	
					}
					else{
						query.append("  and feedback.feed_by='" + empname + "' and feedback.level='" + level + "'");	
					}
				}
			}
			//For Category
			else if(dataFlag!=null && dataFlag.equals("C"))
			{
				query.append(" and feedback.subCatg='" + dept_subdeptid + "'");
			}
			
			if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq"))
				{
					query.append(" " + searchField + " = '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("cn"))
				{
					query.append(" " + searchField + " like '%" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("bw"))
				{
					query.append(" " + searchField + " like '" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("ne"))
				{
					query.append(" " + searchField + " <> '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("ew"))
				{
					query.append(" " + searchField + " like '%" + searchString + "'");
				}
			}
			query.append(" AND feedback.open_date BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"' ");
			query.append(" ORDER BY feedback.id DESC");
		//	System.out.println("QQQQQQ   Padam      ::: "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	

}

	
	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}


	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public String getDataCheck() {
		return dataCheck;
	}

	public void setDataCheck(String dataCheck) {
		this.dataCheck = dataCheck;
	}

	public String getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDashFor() {
		return dashFor;
	}

	public void setDashFor(String dashFor) {
		this.dashFor = dashFor;
	}

	public String getD_subdept_id() {
		return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id) {
		this.d_subdept_id = d_subdept_id;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getDept_subdeptid() {
		return dept_subdeptid;
	}

	public void setDept_subdeptid(String dept_subdeptid) {
		this.dept_subdeptid = dept_subdeptid;
	}

	public Map<String, String> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public List<HelpdeskDashboardPojo> getDataList() {
		return dataList;
	}

	public void setDataList(List<HelpdeskDashboardPojo> dataList) {
		this.dataList = dataList;
	}
	

}