package com.Over2Cloud.ctrl.cps.corporate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CPSBedTypeMaster extends GridPropertyBean implements ServletRequestAware{
	
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	String accountID=(String)session.get("accountid");
	private List<GridDataPropertyView>level1ColmnNames=new ArrayList<GridDataPropertyView>();
	private List<Object> deptDataViewShow;
	private HttpServletRequest request;
	private String orgName=null;
	private String searchFields;
	private String SearchValue;
	private List<ConfigurationUtilBean> departmentTextBox;
	private Map<Integer,String> serviceTypeList;//locationName
	private Map<String, String> locationName;
	private String wildSearch;
	private String status;
	String bed_type,brief;

	public String viewBedTypeHeader()
	{
		return "success";
	}
	public String dataOnGridForBedType()
	{

		try
		{
			setWildSearch(wildSearch);
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setGridColomnNames();
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return "success";
	
		
	}
	
	
	public void setGridColomnNames()
	{
		try{
		GridDataPropertyView gpv=new GridDataPropertyView();
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_cps_bed_type_configuration",accountID,connectionSpace,"",0,"table_name","cps_bed_type_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setWidth(gdp.getWidth());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			level1ColmnNames.add(gpv);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String DisplayData()
	{

		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from CPS_bed_type where status!='Inactive'");
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
				query.append("select ");
				List fieldNames=Configuration.getColomnList("mapped_cps_bed_type_configuration", accountID, connectionSpace, "cps_bed_type_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
						    if(i<fieldNames.size()-1)
						    {
						    	if(obdata.toString().equalsIgnoreCase("service_type"))
						    	{
						    		query.append("st.service_name,");
						    	}
						    	else if(obdata.toString().equalsIgnoreCase("location_name"))
						    	{
						    		query.append("loc.location_name,");
						    	}
						    	else
						    	{
						    		query.append("bt."+obdata.toString()+",");
						    	}
						    }
						    else
						    {
						    	if(obdata.toString().equalsIgnoreCase("service_type"))
						    	{
						    		query.append("st.service_name,");
						    	}
						    	else if(obdata.toString().equalsIgnoreCase("location_name"))
						    	{
						    		query.append("loc.location_name,");
						    	}
						    	else
						    	{
						    		query.append("bt."+obdata.toString()+" ");
						    	}
						    }
					    }
					    i++;
					
				} 
				query.append(" from CPS_bed_type as bt ");
				query.append(" inner join cps_service as st on bt.service_type=st.id ");
				query.append(" inner join cps_location as loc on loc.id=bt.location_name "); 
              	query.append(" where bt.id!='0'");
              	if(status!=null && !status.equalsIgnoreCase("-1"))
              	{
              		query.append(" and bt.status='"+status+"'");
              	}
              	//System.out.println("query "+query);
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
                                  
                                	one.put(fieldNames.get(k).toString(), Integer.parseInt(obdata[j].toString()));
                                  
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
			
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void fetchServiceTypeDD()
	{
		try{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder qry=new StringBuilder("");
			serviceTypeList=new LinkedHashMap<Integer, String>();
			qry.append(" select id, service_name from cps_service ");
			List  data=cbt.executeAllSelectQuery(qry.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
                    Object[] obdata=(Object[])it.next();
                    serviceTypeList.put(Integer.parseInt(obdata[0].toString()), obdata[1].toString());
                    
				}
				
			}
			locationName=new LinkedHashMap<String, String>();
			List dataListLocation = new createTable().executeAllSelectQuery("select id,location_name from cps_location group by location_name", connectionSpace);
			if (dataListLocation != null && dataListLocation.size() > 0)
			{
				for (Iterator<?> iterator = dataListLocation.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						if (!locationName.containsKey(object[0].toString()))
							locationName.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	
	public String beforeBedAdd()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				setDeptAddPageFileds();
				fetchServiceTypeDD();
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
	
	
	public void setDeptAddPageFileds()
	{
		
		try
		{
		List<GridDataPropertyView> offeringLevel1=Configuration.getConfigurationData("mapped_cps_bed_type_configuration",accountID,connectionSpace,"",0,"table_name","cps_bed_type_configuration");
		departmentTextBox = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user_name"))
			{
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
					else
						{
						obj.setMandatory(false);
						}
					departmentTextBox.add(obj);
			}
		}
		}catch (Exception ex)
		{
			ex.printStackTrace();
			}
	
	}
	public String addBedType()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_cps_bed_type_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_bed_type_configuration");
				if (statusColName != null && statusColName.size() > 0) {
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName) {

						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						if (gdp.getColomnName().equalsIgnoreCase("status")) {
							ob1.setConstraint("default 'Active'");
						} else {
							ob1.setConstraint("default NULL");
						}
						tableColume.add(ob1);
						
					}
					cbt.createTable22("cps_location", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !paramValue.equalsIgnoreCase("date")&& !paramValue.equalsIgnoreCase("user_name")&& !paramValue.equalsIgnoreCase("time")
									&& !paramValue.equalsIgnoreCase("location_name")&& !paramValue.equalsIgnoreCase("service_type")&& !paramValue.equalsIgnoreCase("bed_type")&& !paramValue.equalsIgnoreCase("brief")) {
								/*ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);*/
							}
						}
					}
					String testType[]=bed_type.trim().split(",");
					String testBrief[]=brief.trim().split(",");
					if(testType!=null)
					{
						for(int j=0;j<testType.length;j++)
						{
							
							if(testType[j]!=null && testType[j].trim().length()>0)
							{
								ob = new InsertDataTable();
								ob.setColName("location_name");
								ob.setDataName(request.getParameter("location_name"));
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("service_type");
								ob.setDataName(request.getParameter("service_type"));
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("bed_type");
								ob.setDataName(testType[j]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("brief");
								ob.setDataName(testBrief[j]);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("user_name");
								ob.setDataName(userName);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("date");
								ob.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("time");
								ob.setDataName(DateUtil.getCurrentTimeHourMin());
								insertData.add(ob);
								
								status = cbt.insertIntoTable("CPS_bed_type", insertData, connectionSpace);
								insertData.clear();
							}
						}
					} 					
					
					if (status) {
						addActionMessage("Data Save Successfully!!!");
						return SUCCESS;
					}
				} else {
					addActionMessage("Oops There is some error in data!!!!");
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	
	}
	public String editBedDataGrid()
	{
		System.out.println("inside del"+getOper());
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {

			try {
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("cps_bed_type", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
				//	System.out.println("inside del");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update CPS_bed_type set status ='Inactive' where id='" + getId() + "'");
					cbt.updateTableColomn(connectionSpace, sb);
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
 
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public List<GridDataPropertyView> getLevel1ColmnNames() {
		return level1ColmnNames;
	}


	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames) {
		this.level1ColmnNames = level1ColmnNames;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	

	public List<Object> getDeptDataViewShow() {
		return deptDataViewShow;
	}


	public void setDeptDataViewShow(List<Object> deptDataViewShow) {
		this.deptDataViewShow = deptDataViewShow;
	}

	public List<ConfigurationUtilBean> getDepartmentTextBox() {
		return departmentTextBox;
	}


	public void setDepartmentTextBox(List<ConfigurationUtilBean> departmentTextBox) {
		this.departmentTextBox = departmentTextBox;
	}


	public Map<Integer, String> getServiceTypeList() {
		return serviceTypeList;
	}


	public void setServiceTypeList(Map<Integer, String> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}


	public String getWildSearch() {
		return wildSearch;
	}


	public void setWildSearch(String wildSearch) {
		this.wildSearch = wildSearch;
	}


	 

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	public Map<String, String> getLocationName() {
		return locationName;
	}
	public void setLocationName(Map<String, String> locationName) {
		this.locationName = locationName;
	}
	public String getBed_type() {
		return bed_type;
	}
	public void setBed_type(String bedType) {
		bed_type = bedType;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	
}


