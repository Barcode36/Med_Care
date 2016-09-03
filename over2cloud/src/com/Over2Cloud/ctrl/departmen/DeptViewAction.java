package com.Over2Cloud.ctrl.departmen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeptViewAction extends GridPropertyBean implements ServletRequestAware{

	static final Log log = LogFactory.getLog(DeptViewAction.class);
	private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private String deptName;
	private List<Object> deptDataViewShow;
	private HttpServletRequest request;
	private String orgName=null;
	private String searchFields;
	private String SearchValue;
	
	
	public String viewDeptData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from department where flag!='Inactive'");
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
				List fieldNames=Configuration.getColomnList("mapped_dept_config_param", accountID, connectionSpace, "dept_config_param");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
						    if(i<fieldNames.size()-1)
						    {
						    	if(obdata.toString().equalsIgnoreCase("groupId"))
						    	{
						    		query.append("grp.groupName,");
						    	}
						    	else if(obdata.toString().equalsIgnoreCase("mappedOrgnztnId"))
						    	{
						    		query.append("branch.name,");
						    	}
						    	else if(obdata.toString().equalsIgnoreCase("id"))
                            	{
                            		query.append("dept.id,country.name as country,head.name as head,");
                            	}
						    	else
						    	{
						    		query.append("dept."+obdata.toString()+",");
						    	}
						    }
						    else
						    {
						    	if(obdata.toString().equalsIgnoreCase("groupId"))
						    	{
						    		query.append("grp.groupName");
						    	}
						    	else
						    	{
						    		query.append("dept."+obdata.toString());
						    	}
						    }
					    }
					    i++;
					
				} 
				query.append(" from department as dept LEFT join groupinfo as grp on grp.id=dept.groupId   "  );
				query.append(" LEFT join branchoffice_data as branch on grp.regLevel=branch.id  ");
				query.append(" LEFT JOIN headoffice_data as head on head.id=branch.headOfficeId ");
	            query.append(" LEFT JOIN country_data as country on country.id=head.countryId WHERE");
	            String userType = (String)session.get("userTpe");
              	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
              	if (userType!=null && userType.equalsIgnoreCase("H")) 
              	{
              		query.append("  head.id="+s[2] +"  ");
				} 
              	else if (userType!=null && userType.equalsIgnoreCase("N"))
              	{
              		query.append("  branch.id="+s[1]+" AND dept.userName='"+userName+"' ");
				}
				if(getSearchFields()!=null && !getSearchFields().equalsIgnoreCase("-1") && getSearchValue()!=null && !getSearchValue().equalsIgnoreCase("-1") )
				{
					if (userType!=null && !userType.equalsIgnoreCase("M")) 
                	{
                		query.append(" AND ");
					}
					if (!getSearchFields().equalsIgnoreCase("flag"))
					{
						query.append("   dept."+getSearchFields()+"='"+getSearchValue()+"'  AND dept.flag='Active'");
					}
					else
					{
						query.append("   dept."+getSearchFields()+"='"+getSearchValue()+"' ");
					}
				}
				System.out.println("query :;:" +query);
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					if(getSearchField().matches("date"))
                	{
                		setSearchString(DateUtil.convertDateToIndianFormat(getSearchString().toString()) );
                	}
					query.append(" AND ");
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
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by "+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by "+getSidx()+" DESC");
		    	    }
			    }
				
				query.append(" order by dept.deptName asc ");
				//query.append(" limit "+from+","+to);
				
				cbt.checkTableColmnAndAltertable(fieldNames,"department",connectionSpace);
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null && data.size()>0)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
                        Object[] obdata=(Object[])it.next();
                        Map<String, Object> one=new HashMap<String, Object>();
                        int j=0;
                        for (int k = 0; k < fieldNames.size(); k++) 
                        {
                            if(obdata[j]!=null)
                            {
                                if(k==0)
                                {
                                    one.put(fieldNames.get(k).toString(), (Integer)obdata[j]);
                                    if (obdata[j+1]!=null) {
                                    	  one.put("country", obdata[j+1].toString());
									} else {
										  one.put("country","NA");
									}
                                    if (obdata[j+2]!=null) {
                                    	one.put("head", obdata[j+2].toString());
									} else {
										one.put("head", "NA");
									}
                                    
                                    j=j+2;
                                }
                                else
                                {
                                	if(obdata[j].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                	{
                                		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[j].toString()) );
                                	}
                                	else
                                	{
                                		one.put(fieldNames.get(k).toString(), obdata[j].toString());
                                	}
                                }
                                        
                            }
                            else
                            {
                            	one.put(fieldNames.get(k).toString(),"NA");
                            }
                            j++;
                        }
                        Listhb.add(one);
                    }
					setDeptDataViewShow(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewDeptData of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public List<String> getAllOffices()
	{
		List<String> office=new ArrayList<String>();
		
		List data=new createTable().executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);
		
		if(data!=null && data.size()>0)
		{
			if(data!=null && data.size()>0 && data.get(0)!=null)
			{
				String arr[]=data.get(0).toString().split("#");
				if(arr!=null)
				{
					for (int i = 0; i < arr.length; i++) 
					{
						if(arr[i]!=null)
						{
							office.add(arr[i]);
						}
					}
				}
			}
		}
		return office;
	}
	
	@SuppressWarnings("rawtypes")
	public String editDeptDataGrid()
	{
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
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
					if (Parmname.equalsIgnoreCase("flag"))
					{
						wherClause.put("deactivateOn",DateUtil.getCurrentDateUSFormat());
					}
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("department", wherClause, condtnBlock,connectionSpace);
				StringBuilder fieldsNames=new StringBuilder();
        		StringBuilder dataStore=new StringBuilder();
        		if (wherClause!=null && !wherClause.isEmpty())
				{
					int i=1;
					for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
					{
					    if (i < wherClause.size())
							fieldsNames.append("'"+entry.getKey() + "', ");
						else
							fieldsNames.append("'"+entry.getKey() + "' ");
						i++;
					}
				}
        		UserHistoryAction UA=new UserHistoryAction();
        		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"dept_config_param");
        		if (fieldValue!=null && fieldValue.size()>0)
				{
        			StringBuilder dataField =new StringBuilder();
					for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (wherClause!=null && !wherClause.isEmpty())
						{
							int i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
								if (object[1].toString().equalsIgnoreCase(entry.getKey()))
								{
									  if (i < fieldValue.size())
									  {
										  dataStore.append(entry.getValue() + ", ");
									      dataField.append(object[0].toString() +", ");
									  }
									  else
									  {
										  dataStore.append(entry.getValue());
									      dataField.append(object[0].toString());
									  }
								}
								i++;
							}
						}
					}
        			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
        			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Contact Sub Type",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
				}
			}
			if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				wherClause.put("flag", "Inactive");
				wherClause.put("deactivateOn",DateUtil.getCurrentDateUSFormat());
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("department", wherClause, condtnBlock,connectionSpace);
				StringBuilder fieldsNames=new StringBuilder();
        		StringBuilder dataStore=new StringBuilder();
        		if (wherClause!=null && !wherClause.isEmpty())
				{
					int i=1;
					for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
					{
					    if (i < wherClause.size())
							fieldsNames.append("'"+entry.getKey() + "', ");
						else
							fieldsNames.append("'"+entry.getKey() + "' ");
						i++;
					}
				}
        		UserHistoryAction UA=new UserHistoryAction();
        		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"dept_config_param");
        		if (fieldValue!=null && fieldValue.size()>0)
				{
        			StringBuilder dataField =new StringBuilder();
        			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (wherClause!=null && !wherClause.isEmpty())
						{
							int i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
								if (object[1].toString().equalsIgnoreCase(entry.getKey()))
								{
									  if (i < fieldValue.size())
									  {
										  dataStore.append(entry.getValue() + ", ");
									      dataField.append(object[0].toString() +", ");
									  }
									  else
									  {
										  dataStore.append(entry.getValue());
									      dataField.append(object[0].toString());
									  }
								}
								i++;
							}
						}
					}
        		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
        		new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "Contact Sub Type", dataStore.toString(), dataField.toString(),  Integer.parseInt(getId()), connectionSpace);
			   }  
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method editDeptDataGrid of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	
	public List<Object> getDeptDataViewShow() {
		return deptDataViewShow;
	}

	public void setDeptDataViewShow(List<Object> deptDataViewShow) {
		this.deptDataViewShow = deptDataViewShow;
	}
	
	

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}


	public String getSearchFields() {
		return searchFields;
	}


	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}


	public String getSearchValue() {
		return SearchValue;
	}


	public void setSearchValue(String searchValue) {
		SearchValue = searchValue;
	}
	
	
}
