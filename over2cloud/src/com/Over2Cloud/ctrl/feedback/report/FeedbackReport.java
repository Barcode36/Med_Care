package com.Over2Cloud.ctrl.feedback.report;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.FeedbackReportHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackReport implements Action
{
	static final Log log = LogFactory.getLog(FeedbackReport.class);
	private List<ConfigurationUtilBean> reportSearchDD;
	private List<ConfigurationUtilBean> reportSearchTestField;
	private List <String> modesList = null;
	private List <String> feedType = null;
	private String mode,type;
	private List<GridDataPropertyView> masterViewProp;
	private List<Object> masterViewList;
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
    
    
    
    private String endDate;
    private String startDate;
    
	public void setFeedbackViewProp()
    {
    	try
    	{

    		Map session=ActionContext.getContext().getSession();
   		 	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
   		 	String accountID=(String)session.get("accountid");
   		 	
    		masterViewProp=new ArrayList<GridDataPropertyView>();
            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            System.out.println("Mode is as "+getMode());
            
            
            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_configuration",accountID,connectionSpace,"",0,"table_name","feedback_data_configuration");
            
          //  System.out.println("returnResult is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>."+returnResult.size());
            for(GridDataPropertyView gdp:returnResult)
            {
            	if(getMode().equalsIgnoreCase("-1") || getMode().equalsIgnoreCase("SMS"))
            	{
            		if(!gdp.getColomnName().equalsIgnoreCase("userName") &&  !gdp.getColomnName().equalsIgnoreCase("comments") && !gdp.getColomnName().equalsIgnoreCase("tabId") && !gdp.getColomnName().equalsIgnoreCase("refNo") && !gdp.getColomnName().equalsIgnoreCase("blkId") && !gdp.getColomnName().equalsIgnoreCase("nameEmp") && !gdp.getColomnName().equalsIgnoreCase("appFor")  && !gdp.getColomnName().equalsIgnoreCase("recommend") && !gdp.getColomnName().equalsIgnoreCase("overAll") && !gdp.getColomnName().equalsIgnoreCase("choseHospital") && !gdp.getColomnName().equalsIgnoreCase("companytype") && !gdp.getColomnName().equalsIgnoreCase("companytype"))
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
            	else if(getMode().equalsIgnoreCase("Paper"))
            	{
            		if(!gdp.getColomnName().equalsIgnoreCase("tabId"))
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
            }
            
            if(getMode().equalsIgnoreCase("SMS"))
    		{
    			gpv=new GridDataPropertyView();
                gpv.setColomnName("revertedOn");
                gpv.setHeadingName("Reverted On");
                gpv.setEditable("false");
                gpv.setSearch("true");
                gpv.setHideOrShow("false");
                gpv.setWidth(50);
                masterViewProp.add(gpv);
    		}
            
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    }
	
	public void setFeedbackNegViewProp()
	{
    	try
    	{
    		Map session=ActionContext.getContext().getSession();
   		 	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
   		 	String accountID=(String)session.get("accountid");
   		 	
    		masterViewProp=new ArrayList<GridDataPropertyView>();
            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_negative_data_configuration",accountID,connectionSpace,"",0,"table_name","feedback_negative_data_configuration");
            
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
	
	public String beforeFeedbackReportSearchDataView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				if(getType()!=null && getType().equalsIgnoreCase("-1") || getType().equalsIgnoreCase("Positive"))
				{
					setFeedbackViewProp();
				}
				else if(getType().equalsIgnoreCase("Negative"))
				{
					setFeedbackNegViewProp();
				}
				else
				{
					setFeedbackViewProp();
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
	
	
	public String showFeedbackReportSearchDataGrid()
	{

		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
	   		 	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	   		 	String accountID=(String)session.get("accountid");
	            CommonOperInterface cbt=new CommonConFactory().createInterface();
	           
	            StringBuilder query1=new StringBuilder(" select count(*) from feedbackdata where id!=0");
	            
	            /*if(getType()!=null && getType().equalsIgnoreCase("Positive"))
	            {
	            	query1.append(" &&  targetOn='Yes'");
	            }
	            else if(getType()!=null && getType().equalsIgnoreCase("Negative"))
	            {
	            	query1.append(" &&  targetOn='No'");
	            }*/
	            
	            
	            
	            /*if(getMode()!=null && !getMode().equalsIgnoreCase("-1"))
	            {
	            	query1.append(" && mode='"+getMode()+"'");
	            }*/
	            
	            
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
	                
	                StringBuilder query=new StringBuilder("");
	                
	                List fieldNames=null;
	                if(getType()!=null && getType().equalsIgnoreCase("Negative"))
	                {
	                	fieldNames=Configuration.getColomnList("mapped_feedback_negative_data_configuration", accountID, connectionSpace, "feedback_negative_data_configuration");
	                	
	                	query.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," +
	                    	 	" feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," +
	                    	 	" feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem," +
	                    	 	" feedData.compType,feedData.handledBy,feedData.remarks," +
	                    	 	" feedData.kword,feedData.ip,feedData.status,feedData.mode,feedbck.resolve_remark,feedbck.spare_used,feedbck.resolve_date,feedbck.resolve_time from feedback_ticket as feedbt" +
	                    	 	" inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " +
	                    	 	" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where feedData.targetOn='No' and  feedbck.status!='Resolved' ");
	                	if(getStartDate()!=null && getEndDate()!=null)
		                {
		                	query.append(" && feedData.openDate between '"+DateUtil.convertDateToUSFormat(getStartDate())+"' and '"+DateUtil.convertDateToUSFormat(getEndDate())+"' ");
		                }
	                	
	                	query.append(" order by feedData.openDate DESC");
	                }
	                else
	                {
	                	query.append("select ");
	                	fieldNames=Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
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
		                
		                query.append(" from feedbackdata where id!=0");
		                if(getMode()!=null && !getMode().equalsIgnoreCase("-1"))
		                {
		                	query.append(" && mode ='"+getMode()+"'");
		                }
		                
		                if(getType()!=null && !getType().equalsIgnoreCase("-1"))
			            {
			            	query.append(" &&  compType='"+getType()+"'");
			            }
		                
		                if(getStartDate()!=null && getEndDate()!=null)
		                {
		                	String str[]=getStartDate().split("-");
                         	if(str[0]!=null && str[0].length()>3)
                         	{
                         		query.append(" and openDate between '"+((getStartDate()))+"' and '"+((getEndDate()))+"'");
                         	}
                         	else
                        	{
                         		query.append(" and openDate between '"+(DateUtil.convertDateToUSFormat(getStartDate()))+"' and '"+(DateUtil.convertDateToUSFormat(getEndDate()))+"'");
                        	}
		                }
	                }
	                System.out.println("Query is as >>>"+query);
	                List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	          //      System.out.println("Data ka size is as >>> "+data.size());
	                List<Object> listhb=new ArrayList<Object>();
	                if(data!=null)
	                {
	                	FeedbackReportHelper FRH=new FeedbackReportHelper();
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
	                                    	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("openDate"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	                                    	}
	                                    	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("mobNo"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(),(obdata[k].toString()));
	                                    		one.put("revertedOn",(DateUtil.convertDateToIndianFormat(FRH.getPatientRevertDate(obdata[k].toString(), connectionSpace))));
	                                    		
	                                    	}
	                                    	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("discharge_datetime"))
	                                    	{
	                                    		/*System.out.println("Hello i am Avinash "+obdata[k]);
	                                    		if(!obdata[k].toString().equalsIgnoreCase("NA") && obdata[k]!=null && obdata[k].toString().length()>9)
	                                    		{
	                                    			one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(8, 10)+"-"+obdata[k].toString().substring(5, 7)+"-"+obdata[k].toString().substring(0, 4)+" "+obdata[k].toString().substring(obdata[k].toString().indexOf(" "), obdata[k].toString().length()));
	                                    		}
	                                    		else*/
                                    			{
	                                    			one.put(fieldNames.get(k).toString(),(obdata[k].toString()));
                                    			}
	                                    	}
	                                    	else
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(), obdata[k].toString());
	                                    	}
	                                    }
	                                //    listhb.add(one);     
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
	
	public void setReportsPageViewProp()
	{
		try
		{
			Map session=ActionContext.getContext().getSession();
   		 	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
   		 	String userName=(String)session.get("uName");
   		 	String accountID=(String)session.get("accountid");
   		 	
   		 	List<GridDataPropertyView> configTableDataList=Configuration.getConfigurationData("mapped_feedback_report_configuration",accountID,connectionSpace,"",0,"table_name","feedback_report_configuration");
   		 	if(configTableDataList!=null && configTableDataList.size()>0)
   		 	{
   		 		reportSearchDD=new ArrayList<ConfigurationUtilBean>();	
   		 		reportSearchTestField=new ArrayList<ConfigurationUtilBean>();
   		 		for(GridDataPropertyView  gdp:configTableDataList)
   		 		{
   		 			ConfigurationUtilBean objdata= new ConfigurationUtilBean();
   		 			if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("smsStatus") && !gdp.getColomnName().equalsIgnoreCase("patientType"))
   		 			{
   		 				objdata.setKey(gdp.getColomnName());
   		 				objdata.setValue(gdp.getHeadingName());
   		 				objdata.setColType(gdp.getColType());
   		 				objdata.setValidationType(gdp.getValidationType());
   		 				if(gdp.getMandatroy()==null)
   		 				{
   		 					objdata.setMandatory(false);
   		 				}
   		 				else if(gdp.getMandatroy().equalsIgnoreCase("0"))
   		 				{
   		 					objdata.setMandatory(false);
   		 				}
   		 				else if(gdp.getMandatroy().equalsIgnoreCase("1"))
   		 				{
   		 				objdata.setMandatory(true);
   		 				}
                       
   		 			reportSearchDD.add(objdata);
   		 			}
   		 			else if(gdp.getColType().trim().equalsIgnoreCase("T"))
   		 			{

   		 				objdata.setKey(gdp.getColomnName());
   		 				objdata.setValue(gdp.getHeadingName());
   		 				objdata.setColType(gdp.getColType());
   		 				objdata.setValidationType(gdp.getValidationType());
   		 				if(gdp.getMandatroy()==null)
   		 				{
   		 					objdata.setMandatory(false);
   		 				}
   		 				else if(gdp.getMandatroy().equalsIgnoreCase("0"))
   		 				{
   		 					objdata.setMandatory(false);
   		 				}
   		 				else if(gdp.getMandatroy().equalsIgnoreCase("1"))
   		 				{
   		 				objdata.setMandatory(true);
   		 				}
   		 				reportSearchTestField.add(objdata);
   		 			}
   		 			
   		 			
   		 			if(gdp.getColomnName().equalsIgnoreCase("mode"))
   		 			{
   		 				modesList=new ArrayList<String>();
   		 				modesList.add("Paper");
   		 				modesList.add("SMS");
   		 			}
   		 			else if(gdp.getColomnName().equalsIgnoreCase("type"))
		 			{
		 				feedType =new ArrayList<String>();
		 				feedType.add("Positive");
		 				feedType.add("Negative");
		 			}
   		 		}
   		 	}
   		 	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String beforeFeedbackReportSearchView()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				setReportsPageViewProp();
				setStartDate(DateUtil.getNextDateAfter(-6));
				setEndDate(DateUtil.getNextDateAfter(0));
				return SUCCESS;
			}
			catch (Exception e) {
				log.error("Exception in class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimeHourMin() , e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	@Override
	public String execute() throws Exception
	{
		return SUCCESS;
	}
	public List<ConfigurationUtilBean> getReportSearchDD() {
		return reportSearchDD;
	}
	public void setReportSearchDD(List<ConfigurationUtilBean> reportSearchDD) {
		this.reportSearchDD = reportSearchDD;
	}

	public List<String> getModesList() {
		return modesList;
	}

	public void setModesList(List<String> modesList) {
		this.modesList = modesList;
	}

	public List<String> getFeedType() {
		return feedType;
	}

	public void setFeedType(List<String> feedType) {
		this.feedType = feedType;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	public List<ConfigurationUtilBean> getReportSearchTestField() {
		return reportSearchTestField;
	}
	public void setReportSearchTestField(
			List<ConfigurationUtilBean> reportSearchTestField) {
		this.reportSearchTestField = reportSearchTestField;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
