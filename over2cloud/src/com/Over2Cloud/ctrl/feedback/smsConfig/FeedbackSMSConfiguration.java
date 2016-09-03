package com.Over2Cloud.ctrl.feedback.smsConfig;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackConfigBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackSMSConfiguration extends  ActionSupport implements ServletRequestAware
{

	private List<Integer> daysAfter;
	private List<String> keyWordType;
	private List<String> moduleList;
	private String searchType;
	private List<FeedbackConfigBean> feedConfigList = null;
	private String oper;
	private String id;
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private JSONObject keywordJSONObject;
	private String keyword;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String virtualNo;
	private String after;
	private String time;
	private String length;
	private String type;
	private String moduleName;
	
	
	
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
	private List<Object> masterViewList;
	private List<ConfigurationUtilBean> feedbackTextBox;
	private List<ConfigurationUtilBean> feedbackDateBox;
	private List<ConfigurationUtilBean> feedbackTimeBox;
	 private HttpServletRequest request;
	public String beforeSMSConfigView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				setFeedSMSViewProp();
				System.out.println("Hello"+masterViewProp.size());
				
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
	
	public void setFeedSMSViewProp()
	{
		try
    	{
            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            Map session = ActionContext.getContext().getSession();
            String userName=(String)session.get("uName");
            String accountID=(String)session.get("accountid");
            SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_sms_configuration",accountID,connectionSpace,"",0,"table_name","feedback_sms_configuration");
            
            for(GridDataPropertyView gdp:returnResult)
            {
            	gpv=new GridDataPropertyView();
            	gpv.setColomnName(gdp.getColomnName());
                gpv.setHeadingName(gdp.getHeadingName());
                gpv.setEditable(gdp.getEditable());
                gpv.setSearch(gdp.getSearch());
                gpv.setHideOrShow(gdp.getHideOrShow());
                gpv.setFormatter(gdp.getFormatter());
                gpv.setWidth(gdp.getWidth());
                masterViewProp.add(gpv);
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public String viewSmsConfigDataInGrid()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 Map session = ActionContext.getContext().getSession();
				 
				 SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				 String userName=(String)session.get("uName");
				 String accountID=(String)session.get("accountid");
				 StringBuilder query1=new StringBuilder("");
		         query1.append("select count(*) from feedback_sms_config");
		         List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		         
		         if(dataCount!=null)
		            {
		                BigInteger count=new BigInteger("3");
		                for(Iterator it=dataCount.iterator(); it.hasNext();)
		                {
		                     Object obdata=(Object)it.next();
		                     count=(BigInteger)obdata;
		                }
		                setRecords(count.intValue());
		                int to = (getRows() * getPage());
		                int from = to - getRows();
		                if (to > getRecords())
		                    to = getRecords();
		                
		                //getting the list of colmuns
		                StringBuilder query=new StringBuilder("");
		                query.append("select ");
		                List fieldNames=Configuration.getColomnList("mapped_feedback_sms_configuration", accountID, connectionSpace, "feedback_sms_configuration");
		                List<Object> listhb=new ArrayList<Object>();
		                int i=0;
		                for(Iterator it=fieldNames.iterator(); it.hasNext();)
		                {
		                    //generating the dyanamic query based on selected fields
		                        Object obdata=(Object)it.next();
		                        if(obdata!=null)
		                        {
		                            if(i<fieldNames.size()-1)
		                                query.append(obdata.toString()+",");
		                            else
		                                query.append(obdata.toString());
		                        }
		                        i++;
		                    
		                }
		                query.append(" from feedback_sms_config ");
		                if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
		                {
		                    
		                    query.append(" where  ");
		                    //add search  query based on the search operation
		                    if(getSearchOper().equalsIgnoreCase("eq"))
		                    {
		                        query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
		                    }
		                    else if(getSearchOper().equalsIgnoreCase("cn"))
		                    {
		                        query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
		                    }
		                    else if(getSearchOper().equalsIgnoreCase("bw"))
		                    {
		                        query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
		                    }
		                    else if(getSearchOper().equalsIgnoreCase("ne"))
		                    {
		                        query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
		                    }
		                    else if(getSearchOper().equalsIgnoreCase("ew"))
		                    {
		                        query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
		                    }
		                    
		                }
		                List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		                if(data!=null)
		                {
		                	 Collections.reverse(data);
		                    for(Iterator it=data.iterator(); it.hasNext();)
		                    {
		                        Object[] obdata=(Object[])it.next();
		                        Map<String, Object> one=new HashMap<String, Object>();
		                        for (int k = 0; k < fieldNames.size(); k++) {
		                            if(obdata[k]!=null)
		                            {
		                                    if(k==0)
		                                    {
		                                        one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
		                                    }
		                                    else
		                                    {
		                                    	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("smsDate"))
		                                    	{
		                                    		one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
		                                    	}
		                                    	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("type"))
		                                    	{
		                                    		if(obdata[k].toString().equalsIgnoreCase("0"))
		                                    		{
		                                    			one.put(fieldNames.get(k).toString(),"Positive");
		                                    		}
		                                    		else
		                                    		{
		                                    			one.put(fieldNames.get(k).toString(),"Negative");
		                                    		}
		                                    	}
		                                    	else
		                                    	{
		                                    		one.put(fieldNames.get(k).toString(), obdata[k].toString());
		                                    	}
		                                    }
		                                        
		                            }
		                        }
		                        listhb.add(one);
		                    }
		                    setMasterViewList(listhb);
		                    setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	public String execute()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			System.out.println("Inside Execute method");
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String keywordExist()
	{

		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			Map session=ActionContext.getContext().getSession();
			SessionFactory connectionSpace=(SessionFactory) session .get("connectionSpace");
			
			List keywordStatus = cbt.executeAllSelectQuery("select keyword from feedbackSMSConfig where keyword='" + getKeyword() + "'", connectionSpace);
			

			if (keywordStatus != null && keywordStatus.size()>0)
			{
				if (!keywordStatus.isEmpty())
				{
					addActionError("Keyword already exist!");
					keywordJSONObject.put("exist", true);
				} 
				else
				{
					addActionMessage("Keyword Available!");
					keywordJSONObject.put("exist", false);
				}
			}
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String addFeedbackConfig()
	{

		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map session = ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				boolean status=false;    
				    
				List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_feedback_sms_configuration",accountID,connectionSpace,"",0,"table_name","feedback_sms_configuration");
				
				if(statusColName!=null && statusColName.size()>0)
				{
					String keyWord=null;
					 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		                InsertDataTable ob=null;
		                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		                boolean userTrue=false;
		                boolean dateTrue=false;
		                boolean timeTrue=false;
		                for(GridDataPropertyView gdp:statusColName)
		                {
		                     TableColumes ob1=new TableColumes();
		                     ob1=new TableColumes();
		                     ob1.setColumnname(gdp.getColomnName());
		                     ob1.setDatatype("varchar(255)");
		                     ob1.setConstraint("default NULL");
		                     Tablecolumesaaa.add(ob1);
		                     if(gdp.getColomnName().equalsIgnoreCase("userName"))
		                         userTrue=true;
		                     else if(gdp.getColomnName().equalsIgnoreCase("createdDate"))
		                         dateTrue=true;
		                     else if(gdp.getColomnName().equalsIgnoreCase("createdTime"))
		                         timeTrue=true;
		                }
		                
		                cbt.createTable22("feedback_sms_config",Tablecolumesaaa,connectionSpace);
		                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
		                Collections.sort(requestParameterNames);
		                Iterator it = requestParameterNames.iterator();
		                double d=0.0;
		                boolean validTargetOn=false;
		                while (it.hasNext())
		                {
		                	String Parmname = it.next().toString();
	                        String paramValue=request.getParameter(Parmname);
	                        if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
	                        {
	                        	ob=new InsertDataTable();
	                        	ob.setColName(Parmname);
	                            ob.setDataName(paramValue);
	                            insertData.add(ob);
	                            if(Parmname.equalsIgnoreCase("keyWord"))
	                            {
	                            	keyWord=paramValue;
	                            }
	                        }
		                }
		                
		                if(userTrue)
		                 {
		                     ob=new InsertDataTable();
		                     ob.setColName("userName");
		                     ob.setDataName(userName);
		                     insertData.add(ob);
		                 }
		                 if(dateTrue)
		                 {
		                     ob=new InsertDataTable();
		                     ob.setColName("createdDate");
		                     ob.setDataName(DateUtil.getCurrentDateUSFormat());
		                     insertData.add(ob);
		                 }
		                 if(timeTrue)
		                 {
		                     ob=new InsertDataTable();
		                     ob.setColName("createdTime");
		                     ob.setDataName(DateUtil.getCurrentTime());
		                     insertData.add(ob);
		                 }

		                 boolean alredyAdded=HUH.isExist("feedback_sms_config", "keyword",keyWord, connectionSpace);

						 if(!alredyAdded)
						 {
							 status=cbt.insertIntoTable("feedback_sms_config",insertData,connectionSpace); 
						 }
		 				if(status)
		 				{
		 					addActionMessage("SMS Configured Successfully !!!");
		 					return SUCCESS;
		 				}
		 				else
		 				{
		 					addActionMessage("SMS Configured Un-Successfully !!!");
		 					return SUCCESS;
		 				}
				}
				else
				{
					addActionMessage("SMS Configured Un-Successfully !!!");
					return SUCCESS;
				}
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
	
	public String beforeFeedSMSConfigAdd()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				setFeedSMSAddFields();
				
				daysAfter=new ArrayList<Integer>();
				daysAfter.add(0);
				daysAfter.add(1);
				daysAfter.add(2);
				daysAfter.add(3);
				daysAfter.add(4);
				daysAfter.add(5);
				daysAfter.add(6);
				
				keyWordType=new ArrayList<String>();
				keyWordType.add("Satisfied");
				keyWordType.add("Not-Satisfied");

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
	
	public void setFeedSMSAddFields()
	{
    	try
    	{
    		Map session=ActionContext.getContext().getSession();
    		 SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			 String userName=(String)session.get("uName");
			 String accountID=(String)session.get("accountid");
            List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_feedback_sms_configuration",accountID,connectionSpace,"",0,"table_name","feedback_sms_configuration");
            if(offeringLevel1!=null)
            {
                feedbackTextBox=new ArrayList<ConfigurationUtilBean>();
                feedbackDateBox=new ArrayList<ConfigurationUtilBean>();
                feedbackTimeBox=new ArrayList<ConfigurationUtilBean>();
                for(GridDataPropertyView  gdp:offeringLevel1)
                {
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
                    		 && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("createdDate")&& !gdp.getColomnName().equalsIgnoreCase("createdTime")
                           )
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        feedbackTextBox.add(objdata);
                    }
                   else if(gdp.getColType().trim().equalsIgnoreCase("D"))
                   {
                       objdata.setKey(gdp.getColomnName());
                       objdata.setValue(gdp.getHeadingName());
                       objdata.setColType(gdp.getColType());
                       objdata.setValidationType(gdp.getValidationType());
                       if(gdp.getMandatroy()==null)
                       objdata.setMandatory(false);
                       else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                           objdata.setMandatory(false);
                       else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                           objdata.setMandatory(true);
                       feedbackDateBox.add(objdata);
                   }
                   else if(gdp.getColType().trim().equalsIgnoreCase("time"))
                   {
                       objdata.setKey(gdp.getColomnName());
                       objdata.setValue(gdp.getHeadingName());
                       objdata.setColType(gdp.getColType());
                       objdata.setValidationType(gdp.getValidationType());
                       if(gdp.getMandatroy()==null)
                       objdata.setMandatory(false);
                       else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                           objdata.setMandatory(false);
                       else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                           objdata.setMandatory(true);
                       feedbackTimeBox.add(objdata);
                   }
                }
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	
	public String viewFeedbackConfiguration()
	{
		System.out.println("To View ");
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				System.out.println("To View ");
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory) session .get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				StringBuffer query = new StringBuffer("select after, smsTime, keyword, length, type,moduleName,virtualNo from feedbacksmsconfig");
				if (searchType!=null && searchType.equalsIgnoreCase("0")) 
				{
					query.append(" where type='0';");
				}
				else if (searchType!=null && searchType.equalsIgnoreCase("1"))
				{
					query.append(" where type='1'");
				}
				
				System.out.println(""+query);
				
				List dataList = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				
				FeedbackConfigBean configBean = null;
				feedConfigList = new ArrayList<FeedbackConfigBean>();
				if (dataList != null && !dataList.isEmpty()) {
					for (Iterator iterator = dataList.iterator(); iterator .hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						configBean = new FeedbackConfigBean();
						if(object[0]!=null)
						{
							configBean.setAfter(object[0].toString()+" Days");
						}
						
						if(object[1]!=null)
						{
							configBean.setTime(object[1].toString());
						}
						
						if(object[2]!=null)
						{
							configBean.setKeyword(object[2].toString());
						}
						
						if(object[4]!=null)
						{
							configBean.setType((object[4].toString().equalsIgnoreCase( "0") ? "Positive" : "Negative"));
						}
						
						if(object[5]!=null)
						{
							configBean.setModuleName(object[5].toString());
						}
						
						if(object[6]!=null)
						{
							configBean.setVirtualNo(object[6].toString());
						}
						
						feedConfigList.add(configBean);
					}
				}
				setFeedConfigList(feedConfigList);
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
	
	public String editFeedbackConfiguration()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory) session .get("connectionSpace");
				if(getOper().equalsIgnoreCase("del"))
				{
					cbt.deleteAllRecordForId("feedbacksmsconfig", "id", getId(),connectionSpace);
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
	public List<Integer> getDaysAfter() {
		return daysAfter;
	}
	public void setDaysAfter(List<Integer> daysAfter) {
		this.daysAfter = daysAfter;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public List<FeedbackConfigBean> getFeedConfigList() {
		return feedConfigList;
	}
	public void setFeedConfigList(List<FeedbackConfigBean> feedConfigList) {
		this.feedConfigList = feedConfigList;
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
	public List<String> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<String> moduleList) {
		this.moduleList = moduleList;
	}
	public JSONObject getKeywordJSONObject() {
		return keywordJSONObject;
	}
	public void setKeywordJSONObject(JSONObject keywordJSONObject) {
		this.keywordJSONObject = keywordJSONObject;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getVirtualNo() {
		return virtualNo;
	}
	public void setVirtualNo(String virtualNo) {
		this.virtualNo = virtualNo;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public List<ConfigurationUtilBean> getFeedbackTextBox() {
		return feedbackTextBox;
	}
	public void setFeedbackTextBox(List<ConfigurationUtilBean> feedbackTextBox) {
		this.feedbackTextBox = feedbackTextBox;
	}
	public List<ConfigurationUtilBean> getFeedbackDateBox() {
		return feedbackDateBox;
	}
	public void setFeedbackDateBox(List<ConfigurationUtilBean> feedbackDateBox) {
		this.feedbackDateBox = feedbackDateBox;
	}
	public List<ConfigurationUtilBean> getFeedbackTimeBox() {
		return feedbackTimeBox;
	}
	public void setFeedbackTimeBox(List<ConfigurationUtilBean> feedbackTimeBox) {
		this.feedbackTimeBox = feedbackTimeBox;
	}
	public List<String> getKeyWordType() {
		return keyWordType;
	}
	public void setKeyWordType(List<String> keyWordType) {
		this.keyWordType = keyWordType;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}
}