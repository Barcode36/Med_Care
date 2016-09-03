package com.Over2Cloud.ctrl.feedback.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ActionOnFeedbackTicket extends ActionSupport{
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	
	private String feedid;
	private String ticket_no;
	private String resolver;
	private String remark;
	private String status;
	private String addrDate;
	private String addrTime;
	private String open_date;
	private String open_time;
	private String feedbackBy;
	private String mobileno;
	private String catg;
	private String subCatg;
	private String feed_brief;
	private String allotto;
	private String location;
	
	private String resolveon;
	private String resolveat;
	private String spareused;
	private String actiontaken;
	
	private String hpcomment;
	private String ignorecomment;
	
	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	
	private String todept;
	private String tosubdept;
	private String reAssignRemark;

	private String feedStatus;
	private String headingTitle;
	
	
	private String orgName="";
	private String address="";
	private String city="";
	private String pincode="";
	
	private String fromDate="";
	private String toDate="";
	private String dataFlag="N";
	
	private String dashFor="";
	private String d_subdept_id="";
	
	
	
	///////////////////////////
	private String ticketNo="";
	private String openDate="";
	private String openTime="";
	private String emailId="";
	private String feedBreif="";
	private String feedId="";
	//////////////////////////
	
	
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = true;
	//Grid colomn view
	private String oper;
	private int id;
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	List<GridDataPropertyView> feedbackColumnNames = new ArrayList<GridDataPropertyView>();
	
	private Map<Integer, String> resolverList = new HashMap<Integer, String>();
	private List<String> statusList = new ArrayList<String>();
	
	public String redirectToJSP()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
			    statusList.add("Resolved");
			if (feedStatus.equalsIgnoreCase("Pending")) {
				//System.out.println("Inside Pending Block");
				statusList.add("Snooze");
				statusList.add("High Priority");
			}
			else if (feedStatus.equalsIgnoreCase("Snooze")) {
				statusList.add("High Priority");
			}
			else if (feedStatus.equalsIgnoreCase("High Priority")) {
				statusList.add("Snooze");
			}
				statusList.add("Ignore");
				statusList.add("Re-Assign");
			returnResult = SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	public String getReAllotPage()
	 {
		return SUCCESS;
	 }
	
	public String beforeActionOnFeedback()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
			if ((dashFor==null || dashFor.equals("")) && feedStatus.equals("Pending")  ) {
			//	System.out.println("Inside First If Pending");
				 headingTitle ="Pending Feedback >> View";
				 returnResult = SUCCESS;
			}
			else if ((dashFor==null || dashFor.equals("")) && feedStatus.equals("Snooze")) {
				//System.out.println("Inside First Else If Snooze");
				 headingTitle ="Snooze Feedback >> View";
				 returnResult = SUCCESS;
			}
			else if ((dashFor==null || dashFor.equals("")) && feedStatus.equals("High Priority")) {
				//System.out.println("Inside Second Else If High Priority");
				 headingTitle ="High Priority Feedback >> View";
				 returnResult = SUCCESS;
			}
			else if ((dashFor==null || dashFor.equals("")) && feedStatus.equals("Resolved")) {
				//System.out.println("Inside Third Else If Resolved");
				 fromDate=DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
				 toDate=DateUtil.getCurrentDateUSFormat();
				 headingTitle ="Resolved Feedback >> View";
				 returnResult = SUCCESS;
			}
			else if (dashFor.equals("H") || dashFor.equals("M")) {
				//System.out.println("Inside Fourth Else If >>>>");
				 fromDate=DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
				 toDate=DateUtil.getCurrentDateIndianFormat();
				 returnResult = "dashsuccess";
			}
			setGridColomnNames();
		} catch (Exception e) {
			e.printStackTrace();
		  }
		}
		else {
			returnResult = LOGIN;
		}
	//	System.out.println("Return Result "+returnResult);
		return returnResult;
	}
	
	
	public void setGridColomnNames()
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		feedbackColumnNames.add(gpv); 
		
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_date");
		gpv.setHeadingName("Addr Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_time");
		gpv.setHeadingName("Addr Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		
		if (getFeedStatus().equals("Pending") || getFeedStatus().equals("HP")) {
		gpv=new GridDataPropertyView();
		gpv.setColomnName("escalation_date");
		gpv.setHeadingName("Esc Date");
		gpv.setAlign("center");
		gpv.setWidth(80);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("escalation_time");
		gpv.setHeadingName("Esc Time");
		gpv.setAlign("center");
		gpv.setWidth(80);
		feedbackColumnNames.add(gpv);
		}
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_by_dept");
		gpv.setHeadingName("By Dept");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_by_subdept");
		gpv.setHeadingName("By Sub Dept");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Feedback By");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_by_mobno");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_by_emailid");
		gpv.setHeadingName("Email Id");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_to_dept");
		gpv.setHeadingName("To Department");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_to_subdept");
		gpv.setHeadingName("To Sub Dept");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_catg");
		gpv.setHeadingName("Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_subcatg");
		gpv.setHeadingName("Sub Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("center");
		gpv.setHeadingName("Brief");
		feedbackColumnNames.add(gpv);
		
		
		
		if (getFeedStatus().equals("Snooze")) {
		
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sn_reason");
			gpv.setHeadingName("Snooze Reason");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sn_on_date");
			gpv.setHeadingName("Snooze On");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sn_on_time");
			gpv.setHeadingName("Snooze At");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sn_upto_date");
			gpv.setHeadingName("Snooze Up To");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sn_upto_time");
			gpv.setHeadingName("Snooze Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sn_duration");
			gpv.setHeadingName("Snooze Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}
		
		
		if (getFeedStatus().equals("High Priority")) {
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("hp_date");
			gpv.setHeadingName("HP Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("hp_time");
			gpv.setHeadingName("HP Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("hp_reason");
			gpv.setHeadingName("HP Reason");
			gpv.setWidth(100);
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}
		
		if (getFeedStatus().equals("Resolved")) {
			gpv=new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Resolved On");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Resolved At");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Res. Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("resolve_by");
			gpv.setHeadingName("Resolve By");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("Res. Remark");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("Spare Used");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			}
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Location");
		gpv.setWidth(100);
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(100);
		gpv.setHideOrShow("true");
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("via_from");
		gpv.setHeadingName("Via From");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		if (getFeedStatus().equals("Pending")) {
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feed_registerby");
		gpv.setHeadingName("Register By");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
		}
		if (!getFeedStatus().equals("Pending")) {
		gpv=new GridDataPropertyView();
		gpv.setColomnName("action_by");
		gpv.setHeadingName("Action By");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
		}
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedtype");
		gpv.setHeadingName("Feedback Type");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
	}
	
	@SuppressWarnings("unchecked")
	public String getFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			String userName=(String)session.get("uName");
			String deptLevel = (String)session.get("userDeptLevel");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			
			Map<String, List> whereClauseList = new HashMap<String, List>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			int count = 0 ;
			List data=null;
			String dept_subdept_id ="";
			
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					dept_subdept_id = object[3].toString();
				}
			}
			
			if (dept_subdept_id!=null && !dept_subdept_id.equals("")) {
				if (deptLevel.equals("2")) {
					String dept=new HelpdeskUniversalAction().getField("subdepartment", "deptid", "id", dept_subdept_id,connectionSpace);
					List subDeptList = new HelpdeskUniversalAction().getDataList("subdepartment", "id", "deptid", dept,connectionSpace);
					
					if (dataFlag.equals("N")) {
						toDate=DateUtil.convertDateToUSFormat(toDate);
					}
					else if (dataFlag.equals("M")) {
						toDate=toDate;
						fromDate=fromDate;
					}
					
					count = new HelpdeskUniversalAction().getCount4Feedback("feedback_status","Status",getFeedStatus(),fromDate,toDate, "to_dept_subdept", subDeptList,"deptHierarchy", deptLevel, connectionSpace);
					data=new HelpdeskUniversalAction().getFeedbackDetail("feedback_status", getFeedStatus(),fromDate,toDate, subDeptList, deptLevel,"ticket_no","DESC",searchField,searchString,searchOper, connectionSpace);
				}
				
				else if (deptLevel.equals("1")) {/*
					List deptList = new ArrayList();
					String dept=empData.get(3).toString();
					wherClause.put("to_dept_subdept", dept_subdept_id);
					wherClause.put("deptHierarchy", deptLevel);
					count= new HelpdeskUniversalHelper().getDataCountFromTable("feedback_status", wherClause, whereClauseList, connectionSpace);
					deptList.add(dept);
					data=new HelpdeskUniversalAction().getFeedbackDetail("feedback_status", getFeedStatus(), deptList, deptLevel,"ticket_no","DESC",searchField,searchString,searchOper, connectionSpace);
				*/}
			}
			 
			setRecords(count);
			int to = (getRows() * getPage());
			@SuppressWarnings("unused")
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
			
			if(data!=null && data.size()>0){
				if (deptLevel.equals("2")) {
					feedbackList=new HelpdeskUniversalHelper().setFeedbackValues(data,deptLevel,getFeedStatus());
				}
				else if (deptLevel.equals("1")) {
					feedbackList=new HelpdeskUniversalHelper().setFeedbackValues(data,deptLevel,getFeedStatus());
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
	

	public String geTicketDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			String orgDetail = (String)session.get("orgDetail");
			String[] orgData = null;
			String orgName="",address="",city="",pincode="";
			if (orgDetail!=null && !orgDetail.equals("")) {
				orgData = orgDetail.split("#");
				orgName=orgData[0];
				address=orgData[1];
				city=orgData[2];
				pincode=orgData[3];
			}
			setTicket_no(ticket_no);
			setFeedbackBy(feedbackBy);
			setMobileno(mobileno);
			setOpen_date(open_date);
			setOpen_time(open_time);
			setCatg(catg);
			setFeed_brief(feed_brief);
			setLocation(location);
			setAllotto(allotto);
			setTodept(todept);
			setTosubdept(tosubdept);
			setAddrDate(addrDate);
			setAddrTime(addrTime);
			
			setOrgName(orgName);
			setAddress(address);
			setCity(city);
			setPincode(pincode);
			
			if (feedStatus.equalsIgnoreCase("Resolved")) {
			setResolveon(resolveon);
			setResolveat(resolveat);
			setActiontaken(actiontaken);
			setSpareused(spareused);
			}
			returnResult = SUCCESS;
		 }
		catch(Exception e)
		 {
			 addActionError("Ooops!!! There is some problem in getTicketDetails Method");
			 e.printStackTrace();
		 }
	   }
		else {
			returnResult=LOGIN;
		}
		//System.out.println(returnResult+"sdvc nsd vdn");
		return returnResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getResolverData()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			String deptLevel = (String)session.get("userDeptLevel");
			
			String dept_subdept="";
			String sub_catg="";
			
			@SuppressWarnings("unused")
			createTable cbt=new createTable();
			List empdata=new ArrayList<String>();
			if (feedid!=null && !feedid.equals("")) {
				dept_subdept=new HelpdeskUniversalAction().getField("feedback_status", "to_dept_subdept", "id", feedid,connectionSpace);
				sub_catg=new HelpdeskUniversalAction().getField("feedback_status", "subCatg", "id", feedid,connectionSpace);
			}
			 String tolevel="";
				// Code for getting the Escalation Date and Escalation Time
				List subCategoryList = new HelpdeskUniversalHelper().getAllData("feedback_subcategory", "id",sub_catg, "subCategoryName", "ASC",connectionSpace);
				if (subCategoryList != null && subCategoryList.size() > 0) {
					for (Iterator iterator = subCategoryList.iterator(); iterator
							.hasNext();) {
						Object[] objectCol = (Object[]) iterator.next();
						
						if (objectCol[8] == null) {
							tolevel = "Level1";
						}
						else {
						tolevel = objectCol[8].toString();
						}
					}
				}
			
			//empdata = new HelpdeskUniversalAction().getEmp4Escalation(dept_subdept, deptLevel,"",tolevel, connectionSpace);
			//if (empdata==null || empdata.size()==0) {
				String deptid = new HelpdeskUniversalAction().getData("subdepartment", "deptid", "id", dept_subdept);
				empdata = new HelpdeskUniversalAction().getEmp4EscInAllDept(deptid, deptLevel,"",tolevel, connectionSpace);
			//}
			if (empdata!=null && empdata.size()>0)
			 {
				for (Object c : empdata) {
					Object[] dataC = (Object[]) c;
					resolverList.put((Integer)dataC[0], dataC[1].toString());
				}
			 }
		  returnResult = SUCCESS;  
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String updateFeedbackStatus()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			String userName=(String)session.get("uName");
			String deptLevel = (String)session.get("userDeptLevel");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				FeedbackPojo fbp = new FeedbackPojo();
			    String duration="";
			    Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				//List data = new HelpdeskUniversalHelper().getFeedbackDetail("", deptLevel, getStatus(), getFeedid(), connectionSpace);
				GenericMailSender mailSender = new GenericMailSender();
				boolean mailFlag = false;
				/*if (data!=null && data.size()>0) {
				fbp = new HelpdeskUniversalHelper().setFeedbackDataValues(data,getStatus(),deptLevel);
				System.out.println("Ticket No is>>>>"+fbp.getTicket_no());
				}*/
				if (getStatus().equalsIgnoreCase("Resolved")) {
					if (open_date!=null && !open_date.equals("") && open_time!=null && !open_time.equals("")) {
						duration= DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					  }
				   else {duration="NA"; }
				   wherClause.put("status", getStatus());
				   wherClause.put("action_by", userName);
				   wherClause.put("resolve_date", DateUtil.getCurrentDateUSFormat());
				   wherClause.put("resolve_time", DateUtil.getCurrentTime());
				   wherClause.put("resolve_duration", duration);
				   wherClause.put("resolve_remark", DateUtil.makeTitle(getRemark()));
				   wherClause.put("resolve_by", getResolver());
				   wherClause.put("spare_used", getSpareused());
				   condtnBlock.put("id",getFeedid());
				}
				
				else if (getStatus().equalsIgnoreCase("Snooze")) {
					if (snoozeDate!=null && !snoozeDate.equals("") && snoozeTime!=null && !snoozeTime.equals("")) {
						duration= DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(),DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
					  }
					   else {duration="NA";}
					
					wherClause.put("status", getStatus());
					wherClause.put("sn_reason", DateUtil.makeTitle(getSnoozecomment()));
					wherClause.put("sn_on_date", DateUtil.getCurrentDateUSFormat());
				    wherClause.put("sn_on_time", DateUtil.getCurrentTime());
					wherClause.put("sn_upto_date", DateUtil.convertDateToUSFormat(snoozeDate));
					wherClause.put("sn_upto_time", snoozeTime);
					wherClause.put("sn_duration", duration);
					wherClause.put("action_by", userName);
					condtnBlock.put("id",getFeedid());
				}
				else if (getStatus().equalsIgnoreCase("High Priority")) {
					wherClause.put("status", getStatus());
				    wherClause.put("hp_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("hp_time", DateUtil.getCurrentTime());
					wherClause.put("hp_reason", DateUtil.makeTitle(getHpcomment()));
					wherClause.put("action_by", userName);
					condtnBlock.put("id",getFeedid());
				}
				else if (getStatus().equalsIgnoreCase("Ignore")) {
					wherClause.put("status", getStatus());
				    wherClause.put("ig_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("ig_time", DateUtil.getCurrentTime());
					wherClause.put("ig_reason", DateUtil.makeTitle(getIgnorecomment()));
					wherClause.put("action_by", userName);
					condtnBlock.put("id",getFeedid());
				}
				else if (getStatus().equalsIgnoreCase("Re-Assign")) {
					String esc_duration="",tolevel="",needesc="";
					String subCatgId = new HelpdeskUniversalAction().getData("feedback_status", "subCatg", "id", getFeedid());
					
					// Code for getting the Escalation Date and Escalation Time
					List subCategoryList = new HelpdeskUniversalHelper().getAllData("feedback_subcategory", "id",subCatgId, "subCategoryName", "ASC",connectionSpace);
					if (subCategoryList != null && subCategoryList.size() > 0) {
						for (Iterator iterator = subCategoryList.iterator(); iterator
								.hasNext();) {
							Object[] objectCol = (Object[]) iterator.next();
							if (objectCol[6] == null) {
								esc_duration = "24:00";
							}
							else {
								esc_duration = objectCol[6].toString();
							}
							
							if (objectCol[8] == null) {
								tolevel = "Level1";
							}
							else {
							tolevel = objectCol[8].toString();
							}
							if (objectCol[9] == null) {
								needesc = "Y";
							} else {
								needesc = objectCol[9].toString();
							}
						}
					}
					
					 List allotto = null;
					 List allotto1 = null;
					 String allottoId="";
		             List<String> one = new ArrayList<String>();
			   	     List<String> two = new ArrayList<String>();
    			   
	                try
	                 {/*
	                	if (deptLevel.equals("2")) {
	                        allotto = new FeedbackUniversalAction().getRandomEmp4Escalation(tosubdept,deptLevel,tolevel, connectionSpace);
	                	}
					    else {
					    	allotto = new FeedbackUniversalAction().getRandomEmp4Escalation(todept,deptLevel,tolevel, connectionSpace);
					    }
	                	
	                	if (deptLevel.equals("2")) {
	                        allotto1 = new FeedbackUniversalAction().getRandomEmployee(tosubdept,deptLevel,tolevel,allotto, connectionSpace);
	                	}
					    else {
					    	allotto1 = new FeedbackUniversalAction().getRandomEmployee(todept,deptLevel,tolevel,allotto, connectionSpace);
					    }
	                    
	   	               for (Object object : allotto) {one.add(object.toString());}
	   	               for (Object object : allotto1) {two.add(object.toString());}
	   	            if (one.size()>two.size()) {
						one.removeAll(two);
						if (one.size() > 0) {
							for (Iterator iterator = one.iterator(); iterator
									.hasNext();) {
								Object object = (Object) iterator.next();
								allottoId = object.toString();
								break;
							}
					 }
				    } 
					else {
						allottoId = new HelpdeskUniversalAction().getRandomEmployee("", allotto);
					}
	                 */}
	                catch (Exception e) { e.printStackTrace();}
	             
	              
					
					wherClause.put("status", "Pending");
					if (deptLevel.equals("2")) {
						wherClause.put("to_dept_subdept", getTosubdept());
					}
					else if (deptLevel.equals("1")) {
						wherClause.put("to_dept_subdept", getTosubdept());
					}
					wherClause.put("allot_to", allottoId);
					wherClause.put("transfer_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("transfer_time", DateUtil.getCurrentTime());
					wherClause.put("transfer_reason", getReAssignRemark());
					condtnBlock.put("id",getFeedid());
				}
				
			boolean updateFeed=	new HelpdeskUniversalHelper().updateTableColomn("feedback_status", wherClause, condtnBlock,connectionSpace);
			if (updateFeed) {
				{
					String levelMsg = "", complainatMsg = "",mailText = "",mailSubject="",mailheading="";
					List data = new HelpdeskUniversalHelper().getFeedbackDetail("", deptLevel, getStatus(), getFeedid(), connectionSpace);
					if (data!=null && data.size()>0) {
					fbp = new HelpdeskUniversalHelper().setFeedbackDataValues(data,getStatus(),deptLevel);
					}
				
				if (getStatus().equalsIgnoreCase("Resolved")) {
					 levelMsg = "Close Feedback Alert: Ticket No: "+ fbp.getTicket_no()+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby().trim())+ ", Location: " + fbp.getLocation() + ", Brief: "+ remark+ " is Closed.";
					 complainatMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby().trim())+ ", Ticket No: "+ fbp.getTicket_no()+ ", Location: " + fbp.getLocation() + ", Brief: "+ remark+ " is Closed.";
					 mailSubject = "Close Ticket Alert: "+ fbp.getTicket_no();
					 mailheading = "Close Ticket Alert: "+ fbp.getTicket_no();
				}
				
				else if (getStatus().equalsIgnoreCase("High Priority")) {
					 levelMsg = "High Priority Alert: Ticket No: "+ fbp.getTicket_no()+ ", Reg. By : "+ DateUtil.makeTitle(fbp.getFeed_registerby().trim())+ ", Location: " + fbp.getLocation() + ", Brief: "+ fbp.getFeed_brief() + ",Reason: "+fbp.getHp_reason()+".";
					 complainatMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby().trim())+ ", Ticket No: "+ fbp.getTicket_no()+ ", Location: " + fbp.getLocation() + ", Brief: "+ fbp.getFeed_brief()+ ",Reason: "+fbp.getHp_reason()+" is on High Priority.";
					 mailSubject = "High Priority Feedback Alert: "+ fbp.getTicket_no();
					 mailheading = "High Priority Case Ticket Alert";
				}
				
				else if (getStatus().equalsIgnoreCase("Snooze")) {
					 levelMsg = "Snooze Alert: Ticket No: "+ fbp.getTicket_no()+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby().trim())+ ", Location: " + fbp.getLocation() + ", Brief: "+ fbp.getFeed_brief()+ " will be Snoozed till "+ fbp.getSn_duration() + ",Reason: "+fbp.getSn_reason()+".";
					 complainatMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby().trim())+ ", Ticket No: "+ fbp.getTicket_no()+ ", Location: " + fbp.getLocation() + ", Brief: "+ fbp.getFeed_brief()+ ",Status: Snoozed to "+ fbp.getSn_on_date()+" "+fbp.getSn_on_time()+ " Hrs, Reason:"+fbp.getSn_reason()+".";
					 mailSubject = "Snooze Feedback Alert: "+ fbp.getTicket_no();
					 mailheading = "Snooze Case Ticket Alert";
				}
				
				else if (getStatus().equalsIgnoreCase("Ignore")) {
					 levelMsg = "Ignore Feedback Alert: Ticket No: "+ fbp.getTicket_no()+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ", Location: " + fbp.getLocation() + ", Brief: "+ fbp.getFeed_brief()+ ",Reason: "+fbp.getIg_reason()+" should be Ignored.";
					 complainatMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ", Ticket No: "+ fbp.getTicket_no()+ ", Location: " + fbp.getLocation() + ", Brief: "+ fbp.getFeed_brief()+ ",Reason: "+fbp.getIg_reason()+" is Ignored.";
					 mailSubject = "Ignore Feedback Alert: "+ fbp.getTicket_no();
				     mailheading = "Ignore Case Ticket Alert";
				}
				
				else if (getStatus().equalsIgnoreCase("Re-Assign")) {
					levelMsg = "Open Feedback Alert: Ticket No: "+fbp.getTicket_no()+", To Be Closed By: "+DateUtil.convertDateToIndianFormat(fbp.getEscalation_date())+","+fbp.getEscalation_time().subSequence(0, 5)+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby())+", Location: "+fbp.getLocation()+" , Brief: "+ fbp.getFeed_brief() + ".";
				}
				 
				if (getStatus().equalsIgnoreCase("Resolved") || getStatus().equalsIgnoreCase("High Priority") || getStatus().equalsIgnoreCase("Snooze") || getStatus().equalsIgnoreCase("Ignore")) 
				{
				   if (fbp.getFeedback_by_mobno()!=null && fbp.getFeedback_by_mobno()!="" && fbp.getFeedback_by_mobno().trim().length()==10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7"))) {
					   new MsgMailCommunication().addMessage("","",fbp.getMobOne(), complainatMsg, ticket_no,getStatus(), "0", "HDM");
				   }
				
				   if (fbp.getFeedback_by_emailid()!=null && !fbp.getFeedback_by_emailid().equals("")) {
					   mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp,mailheading,getStatus(),deptLevel,false);
					   new MsgMailCommunication().addMail("","",fbp.getEmailIdOne(),mailSubject, mailText, ticket_no,getStatus(), "0", "", "HDM");
				   }
				   
				   if (fbp.getMobOne()!=null && fbp.getMobOne()!="" && fbp.getMobOne().trim().length()==10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7"))) {
						 new MsgMailCommunication().addMessage("","",fbp.getMobOne(), levelMsg, ticket_no,getStatus(), "0", "HDM");
				   }
				
				   if (fbp.getEmailIdOne()!=null && !fbp.getEmailIdOne().equals("")) {
						mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp,mailheading,getStatus(),deptLevel,true);
						new MsgMailCommunication().addMail("","",fbp.getEmailIdOne(),mailSubject, mailText, ticket_no,"Pending", "0", "", "HDM");
				   }
			    }	
				
				else if (getStatus().equalsIgnoreCase("Re-Assign")) {
					if (fbp.getMobOne()!=null && fbp.getMobOne()!="" && fbp.getMobOne().trim().length()==10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7"))) {
						 new MsgMailCommunication().addMessage("","",fbp.getMobOne(), levelMsg, ticket_no,getStatus(), "0", "HDM");
					}
					if (fbp.getEmailIdOne()!=null && !fbp.getEmailIdOne().equals("")) {
						mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp,mailheading,getStatus(),deptLevel,true);
						new MsgMailCommunication().addMail("","",fbp.getEmailIdOne(),mailSubject, mailText, ticket_no,"Pending", "0", "", "HDM");
				    }
				}
			}
			addActionMessage("Feedback Updated in "+getStatus()+" Successfully !!!");
		returnResult = SUCCESS;
		}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		}
		else {
			returnResult =LOGIN;
		}
		return returnResult;
	}
	
	
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<FeedbackPojo> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<FeedbackPojo> feedbackList) {
		this.feedbackList = feedbackList;
	}


	public String getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(String ticket_no) {
		this.ticket_no = ticket_no;
	}


	public Map<Integer, String> getResolverList() {
		return resolverList;
	}
	public void setResolverList(Map<Integer, String> resolverList) {
		this.resolverList = resolverList;
	}


	public String getResolver() {
		return resolver;
	}


	public void setResolver(String resolver) {
		this.resolver = resolver;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSpareused() {
		return spareused;
	}


	public void setSpareused(String spareused) {
		this.spareused = spareused;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getHpcomment() {
		return hpcomment;
	}
	public void setHpcomment(String hpcomment) {
		this.hpcomment = hpcomment;
	}


	public String getIgnorecomment() {
		return ignorecomment;
	}
	public void setIgnorecomment(String ignorecomment) {
		this.ignorecomment = ignorecomment;
	}


	public String getSnoozeDate() {
		return snoozeDate;
	}


	public void setSnoozeDate(String snoozeDate) {
		this.snoozeDate = snoozeDate;
	}


	public String getSnoozeTime() {
		return snoozeTime;
	}


	public void setSnoozeTime(String snoozeTime) {
		this.snoozeTime = snoozeTime;
	}


	public String getSnoozecomment() {
		return snoozecomment;
	}
	public void setSnoozecomment(String snoozecomment) {
		this.snoozecomment = snoozecomment;
	}

	public String getTosubdept() {
		return tosubdept;
	}

	public void setTosubdept(String tosubdept) {
		this.tosubdept = tosubdept;
	}

	public String getReAssignRemark() {
		return reAssignRemark;
	}

	public void setReAssignRemark(String reAssignRemark) {
		this.reAssignRemark = reAssignRemark;
	}

	public String getFeedid() {
		return feedid;
	}

	public void setFeedid(String feedid) {
		this.feedid = feedid;
	}

	public String getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames() {
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(
			List<GridDataPropertyView> feedbackColumnNames) {
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getHeadingTitle() {
		return headingTitle;
	}

	public void setHeadingTitle(String headingTitle) {
		this.headingTitle = headingTitle;
	}

	public String getFeedbackBy() {
		return feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy) {
		this.feedbackBy = feedbackBy;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getCatg() {
		return catg;
	}

	public void setCatg(String catg) {
		this.catg = catg;
	}

	public String getSubCatg() {
		return subCatg;
	}

	public void setSubCatg(String subCatg) {
		this.subCatg = subCatg;
	}

	public String getFeed_brief() {
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief) {
		this.feed_brief = feed_brief;
	}

	public String getAllotto() {
		return allotto;
	}

	public void setAllotto(String allotto) {
		this.allotto = allotto;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public String getAddrDate() {
		return addrDate;
	}

	public void setAddrDate(String addrDate) {
		this.addrDate = addrDate;
	}

	public String getAddrTime() {
		return addrTime;
	}

	public void setAddrTime(String addrTime) {
		this.addrTime = addrTime;
	}

	public String getResolveon() {
		return resolveon;
	}

	public void setResolveon(String resolveon) {
		this.resolveon = resolveon;
	}

	public String getResolveat() {
		return resolveat;
	}

	public void setResolveat(String resolveat) {
		this.resolveat = resolveat;
	}

	public String getActiontaken() {
		return actiontaken;
	}

	public void setActiontaken(String actiontaken) {
		this.actiontaken = actiontaken;
	}

	public String getTodept() {
		return todept;
	}

	public void setTodept(String todept) {
		this.todept = todept;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
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

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFeedBreif() {
		return feedBreif;
	}

	public void setFeedBreif(String feedBreif) {
		this.feedBreif = feedBreif;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}


	public List<String> getStatusList() {
		return statusList;
	}


	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
}
