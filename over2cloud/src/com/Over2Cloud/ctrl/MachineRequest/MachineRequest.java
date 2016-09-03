package com.Over2Cloud.ctrl.MachineRequest;

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

import net.sf.json.JSONArray;
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
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.MachineOrder.MachineOrderHelper;
import com.opensymphony.xwork2.ActionContext;

public class MachineRequest extends GridPropertyBean implements ServletRequestAware {
	/**
	 * This is for Machine order machine master class
	 * author: Manab 12-06-2015
	 */
	private static final long serialVersionUID = -192253594566464362L;
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> configKeyTextBox = null;
	private List<ConfigurationUtilBean> configKeyTextBox4Machine = null;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private String searchFields;
	private String SearchValue;
	private List<Object> machineResponseMasterData;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<String, String> totalCount;
	private JSONArray jsonArr = new JSONArray();
	// For grid header call
	public String machineMasterHeader(){
		return SUCCESS;
	}
	
	
	public String beforecreateAdd()
	{
		//System.out.println("check");
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageMachineKeyword();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	// for add page machine master call 
	public String requestHeader()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		totalCount=new LinkedHashMap<String, String>();
		totalCount=fetchContactTypeCounters(connectionSpace,"status","machine_reason_master");
		return SUCCESS;
	}
	//End
	public Map<String,String> fetchContactTypeCounters(SessionFactory connectionSpace,String colName,String tableName)
	{
		Map<String,String> data=new LinkedHashMap<String, String>();
		try
		{
			int total =0;
			StringBuilder builder=new StringBuilder("select count(*),"+colName+" from "+tableName+" where id!='0' GROUP BY "+colName+" ORDER BY "+colName+"");
			List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0]!=null && object[1]!=null)
					{
						total=total+Integer.parseInt(object[0].toString());
						data.put( object[1].toString(),object[0].toString());
					}
				}
				data.put("Total Records", String.valueOf(total));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	List<ConfigurationUtilBean> corporateTextBox;
	List<ConfigurationUtilBean> corporateDropDown;
	public void setAddPageMachineKeyword() {
		try
		{

		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_reason_configuration", accountID, connectionSpace, "", 0, "table_name", "machine_reason_master_configuration");
		corporateTextBox = new ArrayList<ConfigurationUtilBean>();
		corporateDropDown = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user_Name"))
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
						corporateTextBox.add(obj);
			}

			if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")
					&& !gdp.getColomnName().equalsIgnoreCase("status") )
				{
					//System.out.println("DDDDDDDDDD  "+gdp.getColType().toString());
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					//System.out.println("gdp.getColomnName()  "+gdp.getColomnName().toString());
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
						corporateDropDown.add(obj);
				}
		}
		}catch (Exception ex)
		{
			
			ex.printStackTrace();
			}
	}
	// add machine response 
	public String addMachineMasterResponse() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_reason_configuration", accountID, connectionSpace, "", 0, "table_name", "machine_reason_master_configuration");
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
					cbt.createTable22("machine_reason_master", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null  && !paramValue.equalsIgnoreCase("date")&& !paramValue.equalsIgnoreCase("user_name")&& !paramValue.equalsIgnoreCase("time")) 
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								
								if(paramValue.equalsIgnoreCase("-1") || paramValue.equalsIgnoreCase(""))
								{
									ob.setDataName("NA");
								}
								else
								{
									ob.setDataName(paramValue);
								}
								insertData.add(ob);
							}
						}
					}

					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					//ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					status = cbt.insertIntoTable("machine_reason_master", insertData, connectionSpace);
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
	
	// for main Grid page Call

	public String beforeMaterReasonGrid() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setMachineMasterView();
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
	public void setMachineMasterView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_reason_configuration", accountID, connectionSpace, "", 0, "table_name", "machine_reason_master_configuration");
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
	
	
	// data view in grid for Machine Response master
	public String viewMachineReasonMaster() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from machine_reason_master");

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

					List fieldNames = Configuration.getColomnList("mapped_reason_configuration", accountID, connectionSpace, "machine_reason_master_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();) {
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null) {
							if (i < fieldNames.size() - 1) {
								query.append(obdata + ", ");
							} else {
								query.append(obdata);
							}
						}
						i++;
					}

					query.append(" from machine_reason_master  ");
					if (!getSearchFields().equalsIgnoreCase("-1")) {
						query.append(" where status ='" + getSearchFields() + "'");
					}

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
						if (!getSearchFields().equalsIgnoreCase("-1")) {
							query.append(" and ");
						} else {
							query.append(" where ");
						}

						if (getSearchOper().equalsIgnoreCase("eq")) {
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase("")) {
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" order by reason_name asc");
					//System.out.println("query  "+query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null) {
						for (Iterator it = data.iterator(); it.hasNext();) {
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {
									if (k == 0) {
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else {
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("date")) {
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}

									if (fieldNames.get(k).toString().equalsIgnoreCase("time")) {
										one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
									}
								}
							}

							Listhb.add(one);

						}
						setMachineResponseMasterData(Listhb);
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
	
	// modify Machine Response Data
	public String modifyMachineResponse() {

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
					cbt.updateTableColomn("machine_reason_master", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update machine_reason_master set status ='Inactive' where id='" + getId() + "'");
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
	
	public String machineCartTimeView() {

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {

			try {
				StringBuilder sb = new StringBuilder();
				sb.append(" Select from_time, to_time from machine_cart_time where cart_name='"+id+"'");
				
				List temp=new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
				
				if(temp!=null && !temp.isEmpty())
				{
					jsonArr  = new JSONArray();
					JSONObject jobj = new JSONObject();
					Object[] obj = (Object[]) temp.get(0);
					if(obj[0]!=null && obj[1]!=null)
					{
						jobj.put("from", obj[0].toString());
						jobj.put("to", obj[1].toString());
					}
					jsonArr.add(jobj);
				}
				temp.clear();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	
	
	 public String machineChartTime()
		{
			//System.out.println("check");
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {
				try {

					CommonOperInterface cbt = new CommonConFactory().createInterface();
					
					boolean status = false;
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					
					
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();//
						
						while (it.hasNext())
						{
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (paramValue != null &&  Parmname != null )
							{
								if(Parmname.equalsIgnoreCase("cart_name")){
									condtnBlock.put("cart_name", paramValue);	
								}
								else
								{
									wherClause.put(Parmname, paramValue);
								}
								
								
							}
						}
						wherClause.put("user_name", userName);
						wherClause.put("date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("time", DateUtil.getCurrentTime());
						status=cbt.updateTableColomn("machine_cart_time", wherClause,condtnBlock, connectionSpace);
						if (status) 
						{
							addActionMessage("Data Update Successfully !!!!!");
						}
						else
						{
							addActionMessage("OOPS There is some error in data !!!!!!!!"); 
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
	 
	 public String requestDropDown()
		{
	    	String returnresult=ERROR;
	    	boolean sessionFlag = ValidateSession.checkSession();
	    	if (sessionFlag)
	    	{
	    		try
	    		{
		    			 jsonArr = new JSONArray();
		    			
		    						String[] abc= {"Snooze-I", "Ignore-I", "Resolved"};
		    					
		    			 			JSONObject obj= new JSONObject();
		    			 			for(int i=0; i<abc.length;i++)
		    			 			{
		    			 				obj.put("ID",i );
			    						obj.put("NAME", abc[i]);
			    						jsonArr.add(obj);
		    			 			}
		    				//	System.out.println("jsonArr  "+jsonArr);
	    			returnresult=SUCCESS;
	    		}catch(Exception e){
	    			e.printStackTrace();
	    			returnresult = ERROR;
	    			}
	    	}else {
	    		return LOGIN; 
	    	}
	    	return returnresult;
	    	
		}
		
		
		public String requestAnotherDropDown()
		{
	    	String returnresult=ERROR;
	    	boolean sessionFlag = ValidateSession.checkSession();
	    	if (sessionFlag)
	    	{
	    		try
	    		{
		    			 jsonArr = new JSONArray();
		    			
		    						String[] abc= {"Snooze", "Ignore", "Resolved"};
		    					
		    			 			JSONObject obj= new JSONObject();
		    			 			for(int i=0; i<abc.length;i++)
		    			 			{
		    			 				obj.put("ID",i );
			    						obj.put("NAME", abc[i]);
			    						jsonArr.add(obj);
		    			 			}
	    			returnresult=SUCCESS;
	    		}catch(Exception e){
	    			e.printStackTrace();
	    			returnresult = ERROR;
	    			}
	    	}else {
	    		return LOGIN; 
	    	}
	    	return returnresult;
	    	
		}
	//**************************Greater Setter *****************************************//
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextBox() {
		return configKeyTextBox;
	}

	public void setConfigKeyTextBox(List<ConfigurationUtilBean> configKeyTextBox) {
		this.configKeyTextBox = configKeyTextBox;
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

	public String getSearchValue() {
		return SearchValue;
	}

	public void setSearchValue(String searchValue) {
		SearchValue = searchValue;
	}

	

	

	public List<Object> getMachineResponseMasterData() {
		return machineResponseMasterData;
	}


	public void setMachineResponseMasterData(List<Object> machineResponseMasterData) {
		this.machineResponseMasterData = machineResponseMasterData;
	}


	public void setConfigKeyTextBox4Machine(List<ConfigurationUtilBean> configKeyTextBox4Machine) {
		this.configKeyTextBox4Machine = configKeyTextBox4Machine;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextBox4Machine() {
		return configKeyTextBox4Machine;
	}




	public List<ConfigurationUtilBean> getCorporateTextBox() {
		return corporateTextBox;
	}


	public void setCorporateTextBox(List<ConfigurationUtilBean> corporateTextBox) {
		this.corporateTextBox = corporateTextBox;
	}


	public List<ConfigurationUtilBean> getCorporateDropDown() {
		return corporateDropDown;
	}


	public void setCorporateDropDown(List<ConfigurationUtilBean> corporateDropDown) {
		this.corporateDropDown = corporateDropDown;
	}


	public Map<String, String> getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(Map<String, String> totalCount) {
		this.totalCount = totalCount;
	}


	public JSONArray getJsonArr() {
		return jsonArr;
	}


	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	
	
}
