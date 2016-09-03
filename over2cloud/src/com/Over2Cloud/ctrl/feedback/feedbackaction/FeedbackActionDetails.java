package com.Over2Cloud.ctrl.feedback.feedbackaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackActionDetails extends ActionSupport {

	private String ticketNo;
	private String status;
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
	
	List<GridDataPropertyView> feedbackColumnNames = new ArrayList<GridDataPropertyView>();
	List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	
	public String getActionTakenOnTicket()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				setGridColomnNames();
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
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
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(100);
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
		gpv.setHeadingName("Response Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_time");
		gpv.setHeadingName("Response Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);
		
		
		if (getStatus().equals("Pending")) {
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
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Patient Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Location");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("center");
		gpv.setHeadingName("Feedback/Complaint");
		feedbackColumnNames.add(gpv);
		
		if (getStatus().equals("Pending")) {
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feed_registerby");
			gpv.setHeadingName("Register By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
			}
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("feedback_by_dept");
		gpv.setHeadingName("By Dept");
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
		
		if (getStatus().equals("Resolved")) {
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
			gpv.setHeadingName("CAPA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("RCA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			}
		
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("via_from");
		gpv.setHeadingName("Via From");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		if (!getStatus().equals("Pending")) {
		gpv=new GridDataPropertyView();
		gpv.setColomnName("action_by");
		gpv.setHeadingName("Action By");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
		}
	}
	
	public String getFeedbackDetailFeedback()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				String deptLevel = (String)session.get("userDeptLevel");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				
				Map<String, List> whereClauseList = new HashMap<String, List>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				int count = 0 ;
				List data=null;
				String dept_subdept_id ="";
				
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
				if (empData!=null && empData.size()>0) {
					for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						dept_subdept_id = object[4].toString();
					}
				}
				
				
				if (dept_subdept_id!=null && !dept_subdept_id.equals("")) {
					
					data=new FeedbackUniversalAction().getFeedbackActionTakenDetails(getTicketNo(),getStatus(),connectionSpace);
				}
				 
				setRecords(data.size());
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				
				if(data!=null && data.size()>0){
						feedbackList=new FeedbackUniversalAction().setFeedbackValues(data,deptLevel,getStatus());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String getFeedbackDetail()
	{
		System.out.println("Status is as "+getStatus());
		System.out.println("Ticket No is as "+getTicketNo());
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			Map session = ActionContext.getContext().getSession();
			String userName=(String)session.get("uName");
			String deptLevel = (String)session.get("userDeptLevel");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			
			Map<String, List> whereClauseList = new HashMap<String, List>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			int count = 0 ;
			List data=null;
			String dept_subdept_id ="";
			
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					dept_subdept_id = object[4].toString();
				}
			}
			
			if (dept_subdept_id!=null && !dept_subdept_id.equals("")) {
			
				data=new FeedbackUniversalAction().getFeedbackActionTakenDetails(getTicketNo(),getStatus(),connectionSpace);
			}
			 
			setRecords(data.size());
			int to = (getRows() * getPage());
			@SuppressWarnings("unused")
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
			
			if(data!=null && data.size()>0){
					feedbackList=new FeedbackUniversalAction().setFeedbackValues(data,deptLevel,getStatus());
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
	
	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames() {
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(
			List<GridDataPropertyView> feedbackColumnNames) {
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
