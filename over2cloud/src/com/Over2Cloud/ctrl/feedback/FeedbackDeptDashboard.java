package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.CounterPojo;
import com.Over2Cloud.BeanUtil.FeedDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackTicketPojo;
import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackDeptDashboard extends ActionSupport{

	private List<NewsAlertsPojo> newsList;
	private Map<String,Integer> pieFeedStatMap;
	private String feedStatHeader;
	private List<FeedbackTicketPojo> feedTicketList;
	private List<FeedbackDataPojo> feedDataDashboardList;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private Map<String,Integer> pieDeptMap;
	private int graphWidth;
	private int graphHeight;
	private String labelName;
	private String counterDataHeader;
	private List<CounterPojo> patFeedList;
	public String beforeDeptFeedCounter()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=session.get("uName").toString();
				boolean showAllFlag=new FeedbackDashboardDao().getUserAbovLevel1OrNot(connectionSpace, userName);
				List<Integer> subDeptId=new ArrayList<Integer>();
				if(!showAllFlag)
				{
					subDeptId.add(Integer.parseInt(new FeedbackDashboardDao().getLoggedInEmpInfo(connectionSpace, userName, "subdept")));
					setCounterDataHeader("Patient Feedback Details");
					setLabelName("Feedback");
					patFeedList=new FeedbackDashboardDao().getDeptRemarksData(connectionSpace,subDeptId.get(0).toString());
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
	
	public String beforeDeptFeedGraphShowDialog()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=session.get("uName").toString();
				boolean showAllFlag=new FeedbackDashboardDao().getUserAbovLevel1OrNot(connectionSpace, userName);
				List<Integer> subDeptId=new ArrayList<Integer>();
				if(!showAllFlag)
				{
					subDeptId.add(Integer.parseInt(new FeedbackDashboardDao().getLoggedInEmpInfo(connectionSpace, userName, "subdept")));
					pieDeptMap=new HashMap<String, Integer>();
					if(subDeptId.size()>0)
					{
						pieDeptMap=new FeedbackDashboardDao().getDeptRemarksMap(connectionSpace,String.valueOf(subDeptId.get(0)));
						setGraphHeight(400);
						setGraphWidth(400);
					}
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
	public String beforeDeptFeedGraphRefresh()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=session.get("uName").toString();
				boolean showAllFlag=new FeedbackDashboardDao().getUserAbovLevel1OrNot(connectionSpace, userName);
				List<Integer> subDeptId=new ArrayList<Integer>();
				if(!showAllFlag)
				{
					subDeptId.add(Integer.parseInt(new FeedbackDashboardDao().getLoggedInEmpInfo(connectionSpace, userName, "subdept")));
					pieDeptMap=new HashMap<String, Integer>();
					if(subDeptId.size()>0)
					{
						pieDeptMap=new FeedbackDashboardDao().getDeptRemarksMap(connectionSpace,String.valueOf(subDeptId.get(0)));
						setGraphHeight(200);
						setGraphWidth(250);
					}
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
	public String beforeDashboardShow()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=session.get("uName").toString();
				String deptLevel = (String)session.get("userDeptLevel");
				
				
				boolean showAllFlag=new FeedbackDashboardDao().getUserAbovLevel1OrNot(connectionSpace, userName);
				List<Integer> subDeptId=new ArrayList<Integer>();
				if(!showAllFlag)
				{
					int userSubDept=0;
					userSubDept=Integer.parseInt(new FeedbackDashboardDao().getLoggedInEmpInfo(connectionSpace, userName, "subdept"));
					if(userSubDept!=0)
					{
						subDeptId.add(userSubDept);
					}
					
					
					// 1st Quadrant
					pieDeptMap=new HashMap<String, Integer>();
					
					if(subDeptId.size()>0)
					{
						pieDeptMap=new FeedbackDashboardDao().getDeptRemarksMap(connectionSpace,String.valueOf(subDeptId.get(0)));
					}
					//ends
					
					// 2nd Quadrant
					String subDeptName=null;
					List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
					if (empData!=null && empData.size()>0)
					{
						for (Iterator iterator = empData.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object!=null)
							{
								if(object[6]!=null)
								{
									subDeptName = object[6].toString();
								}
							}
						}
					}
					feedDataDashboardList=new FeedbackDashboardDao().getFeedbackCountersDeptWise(connectionSpace,subDeptName);
					
					// Ends
					
					// 3rd Quadrant
					pieFeedStatMap=new HashMap<String, Integer>();
					feedStatHeader="Ticket Status Chart";
					pieFeedStatMap=new FeedbackDashboardDao().getFeedStatMapDeptWise(connectionSpace, subDeptId);
					// ends
					
					
					// 4th Quadrant
					
					// Ends
					
					// 5th Quadrant
					feedTicketList=new FeedbackDashboardDao().getFeedTicketDashboardDeptWise(connectionSpace,subDeptId);
					// ends
					
				}
				else
				{
					
				}
			
				// Thought of Day Starts
				newsList=new ArrayList<NewsAlertsPojo>();
				newsList=new FeedbackDashboardDao().getDashboardAlertsList("News", connectionSpace,null);
				// Thought of Day Ends
				
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
	public List<NewsAlertsPojo> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsAlertsPojo> newsList) {
		this.newsList = newsList;
	}
	public Map<String, Integer> getPieFeedStatMap() {
		return pieFeedStatMap;
	}
	public void setPieFeedStatMap(Map<String, Integer> pieFeedStatMap) {
		this.pieFeedStatMap = pieFeedStatMap;
	}
	public String getFeedStatHeader() {
		return feedStatHeader;
	}
	public void setFeedStatHeader(String feedStatHeader) {
		this.feedStatHeader = feedStatHeader;
	}
	public List<FeedbackTicketPojo> getFeedTicketList() {
		return feedTicketList;
	}
	public void setFeedTicketList(List<FeedbackTicketPojo> feedTicketList) {
		this.feedTicketList = feedTicketList;
	}
	public List<FeedbackDataPojo> getFeedDataDashboardList() {
		return feedDataDashboardList;
	}
	public void setFeedDataDashboardList(
			List<FeedbackDataPojo> feedDataDashboardList) {
		this.feedDataDashboardList = feedDataDashboardList;
	}
	public Map<String, Integer> getPieDeptMap() {
		return pieDeptMap;
	}
	public void setPieDeptMap(Map<String, Integer> pieDeptMap) {
		this.pieDeptMap = pieDeptMap;
	}
	public int getGraphWidth() {
		return graphWidth;
	}
	public void setGraphWidth(int graphWidth) {
		this.graphWidth = graphWidth;
	}
	public int getGraphHeight() {
		return graphHeight;
	}
	public void setGraphHeight(int graphHeight) {
		this.graphHeight = graphHeight;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getCounterDataHeader() {
		return counterDataHeader;
	}
	public void setCounterDataHeader(String counterDataHeader) {
		this.counterDataHeader = counterDataHeader;
	}
	public List<CounterPojo> getPatFeedList() {
		return patFeedList;
	}
	public void setPatFeedList(List<CounterPojo> patFeedList) {
		this.patFeedList = patFeedList;
	}
}
