package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.FeedbackTicketPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackOverallSummaryCtrl extends ActionSupport
{
	private String mode;
	private List<FeedbackTicketPojo> ticketDataList;
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
	private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private String id;
	
	public String beforeSelectedDataView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				if(getSearchField()!=null && (getSearchField().equalsIgnoreCase("YesterdayYes") || getSearchField().equalsIgnoreCase("TodayYes") || getSearchField().equalsIgnoreCase("TotalYes")))
				{
					return "positiveSuccess";
				}
				else if(getSearchField()!=null && (getSearchField().equalsIgnoreCase("YesterdayNo") || getSearchField().equalsIgnoreCase("TodayNo") || getSearchField().equalsIgnoreCase("TotalNo")))
				{
					return "negativeSuccess";
				}
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
	
	public String selectedDataViewInGrid()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				
				StringBuffer buffer=new StringBuffer("select feedData.id,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedData.date,feedData.time,feedData.comment from feedbackdata as feedData where id!=0 ");
				if(getSearchField()!=null && (getSearchField().equalsIgnoreCase("YesterdayYes") ))
				{
					buffer.append(" && feedData.date='"+DateUtil.getNextDateAfter(-1)+"'");
				}
				else if(getSearchField()!=null && (getSearchField().equalsIgnoreCase("TodayYes") ))
				{
					buffer.append(" && feedData.date='"+DateUtil.getNextDateAfter(-1)+"'");
				}
				
				buffer.append(" && feedData.targetOn='Yes' order by feedData.date DESC");
				
				List dataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
				 List<FeedbackTicketPojo> tempList=new ArrayList<FeedbackTicketPojo>();
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{

						FeedbackTicketPojo feed=new FeedbackTicketPojo();
						Object[] type = (Object[]) iterator.next();
						
						if(getSearchField()!=null && (getSearchField().equalsIgnoreCase("YesterdayYes") || getSearchField().equalsIgnoreCase("TodayYes") || getSearchField().equalsIgnoreCase("TotalYes")))
						{
							if(type[0]!=null)
							{
								feed.setId(Integer.parseInt(type[0].toString()));
							}
							if(type[1]!=null)
							{
								feed.setFeedBy(type[1].toString());
							}
							if(type[2]!=null)
							{
								feed.setFeedByMob(type[2].toString());
							}
							if(type[3]!=null)
							{
								feed.setFeedByMailId(type[3].toString());
							}
							if(type[4]!=null)
							{
								feed.setDoctorName(type[4].toString());
							}
							if(type[5]!=null)
							{
								feed.setPurposeVisit(type[5].toString());
							}
							if(type[6]!=null)
							{
								feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[6].toString()));
							}
							if(type[7]!=null)
							{
								feed.setFeedTime(type[7].toString().substring(0,5));
							}	
							if(type[8]!=null)
							{
								feed.setComments(type[8].toString());
							}	
						}
						else if(getSearchField()!=null && (getSearchField().equalsIgnoreCase("YesterdayNo") || getSearchField().equalsIgnoreCase("TodayNo") || getSearchField().equalsIgnoreCase("TotalNo")))
						{
							if(type[0]!=null)
							{
								feed.setId(Integer.parseInt(type[0].toString()));
							}
							if(type[1]!=null)
							{
								feed.setTicketNo(type[1].toString());
							}
							if(type[2]!=null)
							{
								feed.setFeedBy(type[2].toString());
							}
							if(type[3]!=null)
							{
								feed.setFeedByMob(type[3].toString());
							}
							if(type[4]!=null)
							{
								feed.setFeedByMailId(type[4].toString());
							}
							if(type[5]!=null)
							{
								feed.setDoctorName(type[5].toString());
							}
							if(type[6]!=null)
							{
								feed.setPurposeVisit(type[6].toString());
							}
							if(type[7]!=null)
							{
								feed.setFeedLevel(type[7].toString());
							}
							if(type[8]!=null)
							{
								feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[8].toString()));
							}
							if(type[9]!=null)
							{
								feed.setFeedTime(type[9].toString().substring(0,5));
							}
							if(type[10]!=null)
							{
								feed.setStatus(type[10].toString());
							}	
							if(type[11]!=null)
							{
								feed.setComments(type[11].toString());
							}
						}
								
						tempList.add(feed);
					}
				}
				setTicketDataList(tempList);
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
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public List<FeedbackTicketPojo> getTicketDataList() {
		return ticketDataList;
	}

	public void setTicketDataList(List<FeedbackTicketPojo> ticketDataList) {
		this.ticketDataList = ticketDataList;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
