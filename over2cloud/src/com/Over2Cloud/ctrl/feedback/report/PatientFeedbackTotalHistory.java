package com.Over2Cloud.ctrl.feedback.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionSupport;

public class PatientFeedbackTotalHistory extends ActionSupport
{
	private String clientId;
	private String id;
	List<GridDataPropertyView> feedbackColumnNames = new ArrayList<GridDataPropertyView>();
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
	
	private List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	
	public String beforeTicketHistoryViewActivity()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				if(getClientId()!=null && !getClientId().equalsIgnoreCase(""))
				{
					setClientId(getClientId());
					setGridColomnNames();
				}
				return SUCCESS;
			}
			catch (Exception e) 
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	
	}
	
	public String beforeTicketHistoryView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				if(getId()!=null && !getId().equalsIgnoreCase(""))
				{
					FeedbackUniversalAction FUA=new FeedbackUniversalAction();
					setClientId(FUA.getFeedbackPatId(getId()));
				}
				setGridColomnNames();
				return SUCCESS;
			}
			catch (Exception e) 
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String showTicketHistoryView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				//System.out.println("client id>>>>>>>>>>>>>"+getClientId());
				if(getClientId()!=null && !getClientId().equalsIgnoreCase(""))
				{
					FeedbackUniversalAction FUA=new FeedbackUniversalAction();
					List dataList=FUA.getFeedbackTicketFullDetailsForClient(getClientId());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
					to = getRecords();
					if(dataList!=null && dataList.size()>0)
					{
						setRecords(dataList.size());
						feedbackList=setFeedbackValues(dataList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				return SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public List<FeedbackPojo> setFeedbackValues(List dataList)
	{
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			{
				Object[] obdata = (Object[]) iterator.next();
				FeedbackPojo pojo=new FeedbackPojo();
				if(obdata[0]!=null)
				{
					pojo.setId(Integer.parseInt(obdata[0].toString()));
				}
				
				if(obdata[1]!=null)
				{
					pojo.setTicket_no(obdata[1].toString());
				}
				
				if(obdata[2]!=null && obdata[3]!=null)
				{
					pojo.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[2].toString()) + ", " + obdata[3].toString().substring(0, 5));
				}
				
				/*if(obdata[3]!=null)
				{
					pojo.setOpen_time(obdata[3].toString());
				}*/
				
				if(obdata[4]!=null)
				{
					pojo.setLocation(obdata[4].toString());
				}
				
				if(obdata[5]!=null)
				{
					pojo.setFeedback_to_dept(obdata[5].toString());
				}
				
				if(obdata[6]!=null)
				{
					pojo.setFeedback_allot_to(obdata[6].toString());
				}
				
				if(obdata[7]!=null)
				{
					pojo.setFeedtype(obdata[7].toString());
				}
				
				if(obdata[8]!=null)
				{
					pojo.setFeedback_catg(obdata[8].toString());
				}
				
				if(obdata[9]!=null)
				{
					pojo.setFeedback_subcatg(obdata[9].toString());
				}
				
				if(obdata[10]!=null)
				{
					pojo.setFeed_brief(obdata[10].toString());
				}
				
				if(obdata[11]!=null)
				{
					pojo.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[11].toString()));
				}
				
				if(obdata[12]!=null)
				{
					pojo.setResolve_time(obdata[12].toString());
				}
				
				if(obdata[13]!=null)
				{
					pojo.setResolve_remark(obdata[13].toString());
				}
				
				if(obdata[14]!=null)
				{
					pojo.setSpare_used(obdata[14].toString());
				}
				
				if(obdata[15]!=null)
				{
					pojo.setStatus(obdata[15].toString());
				}
				
				if(obdata[16]!=null)
				{
					pojo.setVia_from(obdata[16].toString());
				}
				feedList.add(pojo);
			}
		}
		return feedList;
	}
	
	
	public void setGridColomnNames()
	{
		try
		{

			feedbackColumnNames = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("ID");
			gpv.setKey("true");
			gpv.setHideOrShow("true");
			gpv.setAlign("center");
			gpv.setFrozenValue("false");
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticket_no");
			gpv.setHeadingName("Ticket No");
			gpv.setFrozenValue("false");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("open_date");
			gpv.setHeadingName("Date of Entry");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			/*gpv = new GridDataPropertyView();
			gpv.setColomnName("open_time");
			gpv.setHeadingName("Open Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			feedbackColumnNames.add(gpv);*/
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("location");
			gpv.setHeadingName("Room No");
			gpv.setWidth(80);
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedback_to_dept");
			gpv.setHeadingName("To Department");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedback_allot_to");
			gpv.setHeadingName("Allot To");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedback_catg");
			gpv.setHeadingName("Category");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedback_subcatg");
			gpv.setHeadingName("Sub Category");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_brief");
			gpv.setAlign("center");
			gpv.setHeadingName("Brief");
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setAlign("center");
			gpv.setWidth(100);
			gpv.setFormatter("viewStatus");
			gpv.setHideOrShow("false");
			feedbackColumnNames.add(gpv);
			
			/*gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Resolved On");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Resolved At");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("CAPA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("RCA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
*/			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("via_from");
			gpv.setHeadingName("Mode");
			gpv.setAlign("center");
			gpv.setWidth(90);
			feedbackColumnNames.add(gpv);
		
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedtype");
			gpv.setHeadingName("Feedback Type");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List<GridDataPropertyView> getFeedbackColumnNames() {
		return feedbackColumnNames;
	}
	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames) {
		this.feedbackColumnNames = feedbackColumnNames;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
