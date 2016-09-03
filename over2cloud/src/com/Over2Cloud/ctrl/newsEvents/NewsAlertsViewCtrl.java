package com.Over2Cloud.ctrl.newsEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.activityboard.ActivityBoardHelper;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NewsAlertsViewCtrl extends ActionSupport implements ServletRequestAware 
{
	private String oper;
	  // get how many rows we want to have into the grid - rowNum attribute in the
	  // grid
	  private Integer             rows             = 0;

	  // Get the requested page. By default grid sets this to 1.
	  private Integer             page             = 0;

	  // sorting order - asc or desc
	  private String              sord;

	  // get index row - i.e. user click to sort.
	  private String              sidx;

	  // Search Field
	  private String              searchField;

	  // The Search String
	  private String              searchString;

	  // Limit the result when using local data, value form attribute rowTotal
	  private Integer             totalrows;

	  // he Search Operation
	  // ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	  private String              searchOper;

	  // Your Total Pages
	  private Integer             total            = 0;

	  // All Records
	  private Integer             records          = 0;
	  private boolean             loadonce         = false;
	  private List<NewsAlertsPojo> newsAlertsList;
	  private String mainHeaderName;
	  private List<Parent> parentTakeaction = null;
	  HttpServletRequest request;
	  private String dataFor="All";
	  private String              id;
		Map session = ActionContext.getContext().getSession();
		String userName = (String) session.get("uName");
		String encryptedID = (String) session.get("encryptedID");
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session .get("connectionSpace");
	  public String getAllNewsAlerts()
		{
			boolean valid=ValidateSession.checkSession();
			if(valid)
			{
				try
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map session = ActionContext.getContext().getSession();
					String userName=(String)session.get("uName");
					String accountID=(String)session.get("accountid");
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					
					newsAlertsList=new ArrayList<NewsAlertsPojo>();
					
					StringBuffer buffer=new StringBuffer("select news.id,news.newsName,news.newsDesc,news.startDate,news.EndDate,dept.deptName,news.showFlag,news.severity, news.module_name from newsandalerts as news INNER JOIN department as dept on dept.id=news.byDept   order by id desc");
					List data=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
					if(data!=null && data.size()>0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object!=null)
							{
								NewsAlertsPojo newsPojo=new NewsAlertsPojo();
								if(object[0]!=null)
								{
									newsPojo.setId(Integer.parseInt(object[0].toString()));
								}
								
								if(object[1]!=null)
								{
									newsPojo.setNewsName(object[1].toString());
								}
								
								if(object[2]!=null)
								{
									newsPojo.setNewsDesc(object[2].toString());
								}
								
								if(object[3]!=null)
								{
									newsPojo.setStartDate(DateUtil.convertDateToIndianFormat(object[3].toString()));
								}
								
								if(object[4]!=null)
								{
									newsPojo.setEndDate(DateUtil.convertDateToIndianFormat(object[4].toString()));
								}
								
								if(object[5]!=null)
								{
									newsPojo.setAddedBy(object[5].toString());
								}
								
								if(object[6]!=null)
								{
									newsPojo.setAddedOn(DateUtil.convertDateToIndianFormat(object[6].toString()));
								}
								
								if(object[7]!=null)
								{
									newsPojo.setAddedAt(object[7].toString());
								}
								if(object[8]!=null)
								{
									newsPojo.setModule_name(object[8].toString());
								}
								
								
								newsAlertsList.add(newsPojo);
							}
						}
					}
					
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					setRecords(newsAlertsList.size());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	  public String beforeNewsEventsAdd()
		{
			boolean valid=ValidateSession.checkSession();
			if(valid)
			{
				try
				{
					setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
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
	  
	  
	  public String viewNewsandalerts()
	  {
			boolean sessionFlag = ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					String empId,empDept=null;
					List dataList = null;
					ActivityBoardHelper ABH = new ActivityBoardHelper();
					
					setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
					String empIdofuser = (String) session.get("empIdofuser");
				 	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;
				 		empId = empIdofuser.split("-")[1].trim();

				 		dataList = ABH.getAllDepartment(connectionSpace, empId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									empDept=object[0].toString();
								}
							}
						}
				 
				    newsAlertsList=new FeedbackDashboardDao().getDashboardAlertsList("News", connectionSpace,empDept);
				
			//	    System.out.println("News and Alerts Size is as :::"+getNewsAlertsList().size());
				    
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
	  
	  public String editNewsndevents()
	  { String returnResult=null;
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && Parmname != null&& !Parmname.equalsIgnoreCase("tableName")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
					{
						if(Parmname.equalsIgnoreCase("startDate") || Parmname.equalsIgnoreCase("endDate"))
						{
							paramValue=DateUtil.convertDateToUSFormat(paramValue);
						}
						wherClause.put(Parmname, paramValue);
					}
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("newsandalerts", wherClause, condtnBlock,connectionSpace);
				returnResult=SUCCESS;
			}
			if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String tempIds[]=getId().split(",");
				StringBuilder condtIds=new StringBuilder();
				int i=1;
				for(String H:tempIds)
				{
					if(i<tempIds.length)
						condtIds.append(H+" ,");
					else
						condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("newsandalerts", "id", condtIds.toString(), connectionSpace);
				returnResult=SUCCESS;
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;}
	  
	  
	  
	  
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
	public Integer getTotalrows() {
		return totalrows;
	}
	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
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
	public List<NewsAlertsPojo> getNewsAlertsList() {
		return newsAlertsList;
	}
	public void setNewsAlertsList(List<NewsAlertsPojo> newsAlertsList) {
		this.newsAlertsList = newsAlertsList;
	}



	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public List<Parent> getParentTakeaction() {
		return parentTakeaction;
	}
	public void setParentTakeaction(List<Parent> parentTakeaction) {
		this.parentTakeaction = parentTakeaction;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}



	public String getDataFor() {
		return dataFor;
	}
	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
