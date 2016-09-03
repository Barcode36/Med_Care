package com.Over2Cloud.ctrl.hr.group;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
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
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SubGroupActionCtrl extends ActionSupport implements ServletRequestAware
{

	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> groupTextBox=null;
	private HttpServletRequest request;
	private Map<Integer,String> groupMap;
	private String groupId;
	
	
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
	    private List<Object> masterViewList;
	    
	public String addSubGroup()
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
	    	    List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_subgroup_configuration",accountID,connectionSpace,"",0,"table_name","subgroup_configuration");
	    	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	    
	    	    boolean userTrue=false;
                boolean dateTrue=false;
                boolean timeTrue=false;
                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
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
                     else if(gdp.getColomnName().equalsIgnoreCase("createdAt"))
                         timeTrue=true;
                }
	    	    
                cbt.createTable22("subgroupinfo",Tablecolumesaaa,connectionSpace);
                
                boolean status=false;
                List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
                InsertDataTable ob=null;
                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();
                while(it.hasNext())
                {
                	String Parmname = it.next().toString();
                    String paramValue=request.getParameter(Parmname);
                    if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("groupId"))
                    {
                    	ob=new InsertDataTable();
                    	ob.setColName(Parmname);
                        ob.setDataName(paramValue);
                        insertData.add(ob);
                    }
                }
                
                if(groupId!=null)
                {
                	ob=new InsertDataTable();
                	ob.setColName("groupId");
                    ob.setDataName(getGroupId());
                    insertData.add(ob);
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
                    ob.setColName("createdAt");
                    ob.setDataName(DateUtil.getCurrentTimeHourMin());
                    insertData.add(ob);
                }
                
                status=cbt.insertIntoTable("subgroupinfo",insertData,connectionSpace); 
                addActionMessage("Group Added Successfully !!!");
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				addActionError("Oop's there is some problem in adding Group ");
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String beforeAdd()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				setSubGroupAddPageDataFields();
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
	
	
	public void setSubGroupAddPageDataFields()
    {
    	try
    	{
    		Map session = ActionContext.getContext().getSession();
    		String accountID=(String)session.get("accountid");
    	    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
    	    List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_subgroup_configuration",accountID,connectionSpace,"",0,"table_name","subgroup_configuration");
            if(offeringLevel1!=null)
            {
            	groupTextBox=new ArrayList<ConfigurationUtilBean>();
                for(GridDataPropertyView  gdp:offeringLevel1)
                {
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("createdDate") && !gdp.getColomnName().equalsIgnoreCase("createdAt"))
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
                        groupTextBox.add(objdata);
                        
                        
                        if(gdp.getColomnName().equalsIgnoreCase("groupId"))
                        {
                        	groupMap=new HashMap<Integer, String>();
                        	List data=new createTable().executeAllSelectQuery(" select id,groupName from groupinfo ", connectionSpace);
                        	if(data!=null && data.size()>0)
                        	{
                        		for (Iterator iterator = data.iterator(); iterator .hasNext();) 
                        		{
									Object[] object = (Object[]) iterator.next();
									if(object[0]!=null && object[1]!=null)
									{
										groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}
                        	}
                        }
                    }
                }
            }
        
    	}
    	catch (Exception e) {
    	e.printStackTrace();
	}
    }
	public String beforeSubGroupView()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				System.out.println("Hello");
				setSubGroupViewProp();
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
	
	 public void setSubGroupViewProp()
	    {
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
	            
	            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_subgroup_configuration",accountID,connectionSpace,"",0,"table_name","subgroup_configuration");
	            
	          //  System.out.println("returnResult is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>."+returnResult.size());
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
	    	catch (Exception e) {
	    	e.printStackTrace();
		}
	    }
	 
	 public String subGroupViewInGrid()
	 {
		 boolean validFlag=ValidateSession.checkSession();
		 if(validFlag)
		 {
			 try
			 {
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 Map session = ActionContext.getContext().getSession();
				 String accountID=(String)session.get("accountid");
				 SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");			 
				 List  dataCount=cbt.executeAllSelectQuery("select count(*) from subgroupinfo",connectionSpace);

				 if(dataCount!=null && dataCount.size()>0)
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
					 
				 }
				 
				 StringBuilder query=new StringBuilder(" select ");
				 List fieldNames=Configuration.getColomnList("mapped_subgroup_configuration", accountID, connectionSpace, "subgroup_configuration");
				 List<Object> listhb=new ArrayList<Object>();
	                int i=0;
	                for(Iterator it=fieldNames.iterator(); it.hasNext();)
	                {
	                    //generating the dyanamic query based on selected fields
	                        Object obdata=(Object)it.next();
	                        
	                        if(obdata!=null)
	                        {
	                            if(obdata.toString().equalsIgnoreCase("groupId"))
	                            {
	                            	query.append("grp.groupName,");
	                            }
	                            else
	                            {
	                            	if(i<fieldNames.size()-1)
		                                query.append("subgrp."+obdata.toString()+",");
		                            else
		                                query.append("subgrp."+obdata.toString());
	                            }
	                        }
	                        i++;
	                    
	                }
	                query.append(" from subgroupinfo as subgrp inner join groupinfo as grp on grp.id=subgrp.groupId");
	                if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	                {
	                    query.append(" where  ");
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
	                if(data!=null && data.size()>0)
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
	                                    	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("createdDate"))
	                                    	{
	                                    		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()) );
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


	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public List<ConfigurationUtilBean> getGroupTextBox() {
		return groupTextBox;
	}
	public void setGroupTextBox(List<ConfigurationUtilBean> groupTextBox) {
		this.groupTextBox = groupTextBox;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}
	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}
}
