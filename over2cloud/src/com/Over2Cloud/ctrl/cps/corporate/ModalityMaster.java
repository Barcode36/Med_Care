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

import javax.servlet.http.HttpServletRequest;

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
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.opensymphony.xwork2.ActionContext;

public class ModalityMaster extends GridPropertyBean implements ServletRequestAware {
	/**
	 * This is for Location master class
	 * author: Faisal 23-01-2016
	 */
	private static final long serialVersionUID = -192253594566464362L;
	private HttpServletRequest request;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private String searchFields;
	private String status;
	private List<Object> locationMasterData;
	List<ConfigurationUtilBean> packageFields;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<String, String> service;
	private Map<String, String> location;
	private String modality,brief;
	// For grid header call
	public String beforeViewModiality(){
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}
	 
	// for main Grid page Call

	public String modalityView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setModalityView();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}
	// for main keyword column name set Response master grid
	public void setModalityView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_cps_modality_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_modality_configuration");
				for (GridDataPropertyView gdp : returnResult) {
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());

					masterViewProp.add(gpv);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}
	
	// data view in grid for PaymentType master
	public String modalityGridViewData() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from cps_modality");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null) {
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					List fieldNames = Configuration.getColomnList("mapped_cps_modality_configuration", accountID, connectionSpace, "cps_modality_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();) {
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null) {
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
						    		query.append("mo."+obdata.toString()+",");
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
						    		query.append("mo."+obdata.toString()+" ");
						    	}
						    }
					    }
						i++;
					}

					query.append(" from cps_modality as mo  ");
					query.append(" inner join cps_service as st on mo.service_type=st.id  ");
					query.append(" inner join cps_location as loc on loc.id=mo.location_name  "); 
					query.append(" where mo.id!='0'");
	              	if(status!=null && !status.equalsIgnoreCase("-1"))
	              	{
	              		query.append(" and mo.status='"+status+"'");
	              	}
					query.append(" order by modality asc");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null) {
						for (Iterator it = data.iterator(); it.hasNext();) {
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) 
								{
									if(k==0)
	                                {
	                                  
	                                	one.put(fieldNames.get(k).toString(), Integer.parseInt(obdata[k].toString()));
	                                  
	                                }
	                                else
	                                {
	                                	if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	                                	{
	                                		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()) );
	                                	}
	                                	else
	                                	{
	                                		one.put(fieldNames.get(k).toString(), obdata[k].toString());
	                                	}
	                                }
								}
								else
	                            {
	                            	one.put(fieldNames.get(k).toString(),"NA");
	                            }
							}

							Listhb.add(one);

						}
						setLocationMasterData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}

				returnValue = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				returnValue = ERROR;
			}
		} else {
			returnValue = LOGIN;
		}
		return returnValue;
	}
	 //Set Add Page
	public String beforeAddModality()
	{
		System.out.println("check");
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			service=new LinkedHashMap<String, String>();
			String query = null;
			query = "select id,service_name as loc from cps_service GROUP by service_name ORDER BY service_name ";
			service = new LinkedHashMap<String, String>();
			List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (query != null)
			{
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							if (!service.containsKey(object[0].toString()))
								service.put(object[0].toString(), object[1].toString());
						}
					}
				}
			}
			location=new LinkedHashMap<String, String>();
			List dataListLocation = new createTable().executeAllSelectQuery("select id,location_name from cps_location group by location_name", connectionSpace);
			if (dataListLocation != null && dataListLocation.size() > 0)
			{
				for (Iterator<?> iterator = dataListLocation.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						if (!location.containsKey(object[0].toString()))
							location.put(object[0].toString(), object[1].toString());
					}
				}
			}
		
			setPaymentType();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setPaymentType() {
		try
		{
		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_cps_modality_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_modality_configuration");
		packageFields = new ArrayList<ConfigurationUtilBean>();

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
					packageFields.add(obj);
			}
		}
		}catch (Exception ex)
		{
			ex.printStackTrace();
			}
	}
	// add Modality
	public String addModalityData() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_cps_modality_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_modality_configuration");
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
					cbt.createTable22("cps_modality", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !paramValue.equalsIgnoreCase("date")&& !paramValue.equalsIgnoreCase("user_name")&& !paramValue.equalsIgnoreCase("time")
									&& !paramValue.equalsIgnoreCase("location_name")&& !paramValue.equalsIgnoreCase("service_type")&& !paramValue.equalsIgnoreCase("test_type")&& !paramValue.equalsIgnoreCase("test_brief")) {
								/*ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);*/
							}
						}
					}
					String testType[]=modality.trim().split(",");
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
								ob.setColName("modality");
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
								
								status = cbt.insertIntoTable("cps_modality", insertData, connectionSpace);
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
	public String modifyModality()
	{
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
					cbt.updateTableColomn("cps_modality", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
				//	System.out.println("inside del");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update cps_modality set status ='Inactive' where id='" + getId() + "'");
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
	//**************************Greater Setter *****************************************//
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
	public List<ConfigurationUtilBean> getPackageFields() {
		return packageFields;
	}

	public void setPackageFields(List<ConfigurationUtilBean> packageFields) {
		this.packageFields = packageFields;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getLocationMasterData() {
		return locationMasterData;
	}

	public void setLocationMasterData(List<Object> locationMasterData) {
		this.locationMasterData = locationMasterData;
	}

	public Map<String, String> getService() {
		return service;
	}

	public void setService(Map<String, String> service) {
		this.service = service;
	}

	public Map<String, String> getLocation() {
		return location;
	}

	public void setLocation(Map<String, String> location) {
		this.location = location;
	}

	 

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
 
	
 
}
