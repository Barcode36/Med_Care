package com.Over2Cloud.ctrl.feedback;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.opensymphony.xwork2.ActionContext;


public class KeyWordRecvReportCtrl extends GridPropertyBean
{
	static final Log log = LogFactory.getLog(KeyWordRecvReportCtrl.class);
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
	private final CommonOperInterface cbt=new CommonConFactory().createInterface();
	private List<Object> masterViewList;
	
	private Map<String,String> keyTypeMap;
	private String fromDate;
	private String toDate;
	private String keyType;
	
	public Map<String,String> getKeywordsMap(SessionFactory connectionSpace)
	{
		Map<String,String> map=new HashMap<String, String>();
		
		List dataList=cbt.executeAllSelectQuery("select distinct keyword from t2mkeyword", connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					map.put(object.toString(), object.toString());
				}
			}
		}
		
		return map;
	}
	
	public String execute()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				keyTypeMap=new HashMap<String, String>();
				Map session = ActionContext.getContext().getSession();
				String accountID=(String)session.get("accountid");
			    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			    keyTypeMap=getKeywordsMap(connectionSpace);
			    
			    fromDate=DateUtil.getNextDateAfter(-6);
				toDate=DateUtil.getCurrentDateUSFormat();
				
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
	
	public String getKeyWordsDetailsInGrid()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID=(String)session.get("accountid");
			    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			    
			    
	            StringBuilder query1=new StringBuilder("");
	            query1.append("select count(*) from t2mkeyword where id!=0");
	            /*if(getFromDate()!=null && getToDate()!=null && !getFromDate().equalsIgnoreCase(""))
	            {
	            	
	            }*/
	            if(getKeyType()!=null && !getKeyType().equalsIgnoreCase("-1"))
	            {
	            	query1.append(" and keyword='"+getKeyType()+"'");
	            }
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
	                List fieldNames=Configuration.getColomnList("mapped_keyword_report_configuration", accountID, connectionSpace, "keyword_report_configuration");
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
	                query.append(" from t2mkeyword where id!=0 ");
	                
	                System.out.println("Key Type "+getKeyType());
	                
	                if(getFromDate()!=null && getToDate()!= null )
	                {
	                	String str[]=getFromDate().split("-");
	                	if(str[0]!=null && str[0].length()>3)
	                	{
	                		query.append(" and date between '"+((getFromDate()))+"' and '"+((getToDate()))+"'");
	                	}
	                	else
	                	{
	                		query.append(" and date between '"+(DateUtil.convertDateToUSFormat(getFromDate()))+"' and '"+(DateUtil.convertDateToUSFormat(getToDate()))+"'");
	                	}
	                }
	                else
	                {
	                	query.append(" and date between '"+(DateUtil.getNextDateAfter(-6))+"' and '"+(DateUtil.getCurrentDateUSFormat())+"'");
	                }
	                if(getKeyType()!=null && !getKeyType().equalsIgnoreCase("-1"))
		            {
	                	query.append(" and keyword='"+getKeyType()+"'");
		            }
	                if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	                {
	                    
	                    query.append(" and  ");
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
	                System.out.println("><<<<<<<<<<<<<<<<<<<<<"+query);
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
	                                    	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	                                    	}
	                                    	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("updatedDate"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	                                    	}
	                                    	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("updatedTime"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(),(obdata[k].toString().subSequence(0, 5)));
	                                    	}
	                                    	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("keyword"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(),obdata[k].toString().toUpperCase());
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
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	
	public String beforeKeyWordsView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			System.out.println("KEY TYPE isa s   >>>>>"+getKeyType());
			System.out.println("FROM DATE::"+getFromDate());
			System.out.println("To Dste"+getToDate());
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID=(String)session.get("accountid");
			    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			    
	            GridDataPropertyView gpv=new GridDataPropertyView();
	            gpv.setColomnName("id");
	            gpv.setHeadingName("Id");
	            gpv.setHideOrShow("true");
	            masterViewProp.add(gpv);
	            
	            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_keyword_report_configuration",accountID,connectionSpace,"",0,"table_name","keyword_report_configuration");
	            
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
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
		                    "Problem in Over2Cloud  method execute of class "+getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
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
	public Map<String, String> getKeyTypeMap() {
		return keyTypeMap;
	}
	public void setKeyTypeMap(Map<String, String> keyTypeMap) {
		this.keyTypeMap = keyTypeMap;
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
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
}
