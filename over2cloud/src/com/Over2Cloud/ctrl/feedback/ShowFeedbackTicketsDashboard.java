package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowFeedbackTicketsDashboard extends ActionSupport
{
	
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");

	private String toSubDeptName;
	private String searchFlagName;
	
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
	List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	
	
	
	public String showFeedbackDeptDashboard()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
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
			
			 if(getToSubDeptName()!=null)
			 {
				 List pCareSubDeptId=new createTable().executeAllSelectQuery("select id from subdepartment where subdeptname="+getToSubDeptName(), connectionSpace);
	             if(pCareSubDeptId!=null)
	             {
	             	for (Iterator iterator = pCareSubDeptId.iterator(); iterator.hasNext();)
	             	{
							Object object = (Object) iterator.next();
							if(object!=null)
							{
								dept_subdept_id=object.toString();
							}
						}
	             }
			 }
			 
			 String str=getSearchFlagName();
			 searchFlagName=str.trim().substring(1,str.length()-1);
		//	System.out.println(getSearchFlagName()+"SUBSTRINGSSSSSSSSSSS"+getSearchFlagName().substring(1,str.length()-1));
			
			// Code Commented on 05 Sept for Dept Dashboard
			/*List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					//dept_subdept_id = object[3].toString();
				}
			}*/
			if (getToSubDeptName()!=null && !getToSubDeptName().equals(""))
			{
				if (deptLevel.equals("2")) {
					
					
					/* Commented  by Avinash
					//String dept=new HelpdeskUniversalAction().getField("subdepartment", "deptid", "id", dept_subdept_id,connectionSpace);
					List subDeptList = new ArrayList();
				//	subDeptList.add(new HelpdeskUniversalAction().getField("subdepartment", "id", "deptid",dept_subdept_id,connectionSpace));
				//	List subDeptList = new HelpdeskUniversalAction().getDataList("subdepartment", "id", "deptid", dept,connectionSpace);
					
					subDeptList.clear();
					subDeptList.add(dept_subdept_id);
					if(getSearchFlagName()!=null && (getSearchFlagName().contains("Pending") || getSearchFlagName().contains("Resolved") ))
					{
						System.out.println("For Getting Status>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						count = new HelpdeskUniversalAction().getCount4FeedbackDashboard("feedback_status","Status",getSearchFlagName(), "to_dept_subdept", subDeptList,"deptHierarchy", deptLevel, connectionSpace);
						data=new HelpdeskUniversalAction().getFeedbackDetail("feedback_status",getSearchFlagName(), subDeptList, deptLevel,"ticket_no","DESC","status",getSearchFlagName(),searchOper, connectionSpace);
					}
					else if(getSearchFlagName()!=null && (getSearchFlagName().contains("level")))
					{
						count = new HelpdeskUniversalAction().getCount4FeedbackDashboard("feedback_status","level",getSearchFlagName(), "to_dept_subdept", subDeptList,"deptHierarchy", deptLevel, connectionSpace);
						data=new HelpdeskUniversalAction().getFeedbackDetail("feedback_status",null, subDeptList, deptLevel,"ticket_no","DESC","level",getSearchFlagName(),searchOper, connectionSpace);
					}
					
					
				*/
					
				}
				else if (deptLevel.equals("1"))
				{/*
					System.out.println("For Dept Level1>>>>>>>>>>>>>>>>>>>>>>>");
					List deptList = new ArrayList();
					String dept=getToSubDeptName();
					wherClause.put("to_dept_subdept", dept_subdept_id);
					wherClause.put("deptHierarchy", deptLevel);
					count= new HelpdeskUniversalHelper().getDataCountFromTable("feedback_status", wherClause, whereClauseList, connectionSpace);
					deptList.add(dept);
					data=new HelpdeskUniversalAction().getFeedbackDetail("feedback_status", getSearchFlagName(), deptList, deptLevel,"ticket_no","DESC",searchField,searchString,searchOper, connectionSpace);
				*/}
			}
			 
			setRecords(count);
			int to = (getRows() * getPage());
			@SuppressWarnings("unused")
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
			
			if(data!=null && data.size()>0)
			{
				if (deptLevel.equals("2"))
				{
					feedbackList=new HelpdeskUniversalHelper().setFeedbackValues(data,deptLevel,getSearchFlagName());
				}
				else if (deptLevel.equals("1"))
				{
					feedbackList=new HelpdeskUniversalHelper().setFeedbackValues(data,deptLevel,getSearchFlagName());
				}
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				returnResult = SUCCESS;
			}
			
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToSubDeptName() {
		return toSubDeptName;
	}

	public void setToSubDeptName(String toSubDeptName) {
		this.toSubDeptName = toSubDeptName;
	}

	public String getSearchFlagName() {
		return searchFlagName;
	}

	public void setSearchFlagName(String searchFlagName) {
		this.searchFlagName = searchFlagName;
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

	public List<FeedbackPojo> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList) {
		this.feedbackList = feedbackList;
	}
	
}
