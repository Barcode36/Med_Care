package com.Over2Cloud.ctrl.MachineOrder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;

public class MachineOrderType extends GridPropertyBean implements ServletRequestAware  {

	private static final long serialVersionUID = -192253594566464362L;
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> configKeyTextBox = null;
	private List<ConfigurationUtilBean> configKeyTextBox4Machine = null;
	private List<GridDataPropertyView> viewPageColumns = new ArrayList<GridDataPropertyView>();
	private JSONArray jsonList;
	private String searchFields;
	private String SearchValue;
	private List<Object> machineMasterData;
	private Map<String,String> nurseUnit=null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	private String order_type;
	
	
	public String machineOrderTypeHeader(){
		return SUCCESS;
	}
	
	
	// for add page machine master call 
		public String beforeMachineOrderTypeAdd()
		{
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAddPageMachineOrderType();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return SUCCESS;
		}
	
	
	//add form fields view
	public void setAddPageMachineOrderType() {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_machine_ordertype_conf");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("machine_ordertype_conf")) {
								//System.out.println("chk>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_machine_ordertype_conf", accountID, connectionSpace, "", 0, "table_name", "machine_ordertype_conf");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") 
									&& !gdp.getColomnName().equalsIgnoreCase("userName") 
									&& !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("status")
									&& !gdp.getColomnName().equalsIgnoreCase("time")
									)
							{
							//	System.out.println(gdp.getHeadingName());
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}
								configKeyTextBox.add(obj);
							}
						}
					}
				}
			}
			
			// for machineOrderType name
			empuery.delete(0, empuery.length());
			empuery.append("select table_name from mapped_machine_order_conf");
			List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData1.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("machine_order_conf")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_machine_order_conf", accountID, connectionSpace, "", 0, "table_name", "machine_order_conf");
						configKeyTextBox4Machine = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") 
									&& !gdp.getColomnName().equalsIgnoreCase("userName") 
									&& !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("status")
									&& !gdp.getColomnName().equalsIgnoreCase("time")
									) {

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}
								configKeyTextBox4Machine.add(obj);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
		// auto cart add settings
	
	public String autoCartSet()
	{
		StringBuilder qryBuilder = new StringBuilder("select id, nursing_unit from machine_order_nursing_details where status='1' order by nursing_unit");
		List temp = new createTable().executeAllSelectQuery(qryBuilder.toString(), connectionSpace);
		nurseUnit = new HashMap<String, String>();
		if(temp!=null && !temp.isEmpty())
		{
			for (Iterator iterator = temp.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null){
					nurseUnit.put(object[0].toString(), object[1].toString());
				}
			}
		}
		return SUCCESS;
	}
	
	// auto cart redirect
	
	public String autoCartHeader(){
		return SUCCESS;
	}
	
	// cart setting add 
	public String addAutoCartSet(){
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_machine_order_cart_settings", accountID, connectionSpace, "", 0, "table_name", "machine_order_cart_settings_config");
				if (statusColName != null && statusColName.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
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

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							dateTrue = true;
						
					}
					
					new createTable().createTable22("machine_order_cart_settings", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator<String> it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						if (Parmname != null && !Parmname.trim().equals("") && !Parmname.trim().equalsIgnoreCase("__multiselect_nurse")&& !Parmname.trim().equalsIgnoreCase("nurse"))
						{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
								insertData.add(ob);
						}
					}

					
					if(userTrue){
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
					}
					if(timeTrue){
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					}
					
					String [] nurse= request.getParameterValues("nurse");
					//System.out.println(request.getParameterValues("nurse"));
					if(nurse.length>0){
						for(int i=0; i<=nurse.length-1;i++)
						{
							ob = new InsertDataTable();
							ob.setColName("nursing_unit");
							ob.setDataName(nurse[i]);
							insertData.add(ob);
							status = cbt.insertIntoTable("machine_order_cart_settings", insertData, connectionSpace);
							insertData.remove(insertData.size()-1);
						}
						
					}
					
					
					if (status) {
						addActionMessage("Data Save Successfully!!!");
						return SUCCESS;
					}
				 else {
					addActionMessage("Oops There is some error in data!!!!");
				}}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	
	}
	
	// view for grid form into machine_order db
	public String viewDataOrderType() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_machine_ordertype_conf", accountID, connectionSpace, "", 0, "table_name", "machine_ordertype_conf");
				for (GridDataPropertyView gdp : returnResult) {
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());

					viewPageColumns.add(gpv);
				}
				return SUCCESS;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
				return LOGIN;
		}
		return ERROR;
	}
	
	// view for column name of machine order cart settings
	
	public String viewCartSetData(){

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_machine_order_cart_settings", accountID, connectionSpace, "", 0, "table_name", "machine_order_cart_settings_config");
				for (GridDataPropertyView gdp : returnResult) {
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());

					viewPageColumns.add(gpv);
				}
				return SUCCESS;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
				return LOGIN;
		}
		return ERROR;
	
		
	
	}
	
	// fetch data for grid machine order type
	public String fetchGridData(){

		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				//System.out.println(" value " + SearchValue);
				//System.out.println(" value2 " + searchFields);

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from machine_ordertype");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				// //System.out.println("dataCount:" + dataCount);
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

					List fieldNames = Configuration.getColomnList("mapped_machine_ordertype_conf", accountID, connectionSpace, "machine_ordertype_conf");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();) {
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null) {
							if (i < fieldNames.size() - 1) {
								if(obdata.toString().equalsIgnoreCase("orderId")){
								query.append("mo.order_name , ");
								}else{
									query.append("mot."+obdata + ", ");
								}
							} else {
								query.append("mot."+obdata);
							}
						}
						i++;
					}

					query.append(" from machine_ordertype as mot "
							+ " inner join machine_order as mo on mo.id=mot.orderId ");
					if (!getSearchFields().equalsIgnoreCase("-1")) {
						query.append(" where mot.status ='" + getSearchFields() + "'");
					}else{
						query.append(" where mot.status ='Active' ");
					}

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
						if (!getSearchFields().equalsIgnoreCase("-1")) {
							query.append(" and ");
						} else {
							query.append(" where ");
						}

						if (getSearchOper().equalsIgnoreCase("eq")) {
							if(getSearchField().equalsIgnoreCase("orderId"))
								setSearchField("order_name");
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

					query.append(" order by mo.order_name asc");
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
						setMachineMasterData(Listhb);
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
	
	//fetch data for machine cart setting of auto allocation 
	
	public String fetchGridDataforCartSet(){


		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
			//	System.out.println(" value " + SearchValue);
			//	System.out.println(" value2 " + searchFields);

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from machine_order_cart_settings");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				// //System.out.println("dataCount:" + dataCount);
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

					List fieldNames = Configuration.getColomnList("mapped_machine_order_cart_settings", accountID, connectionSpace, "machine_order_cart_settings_config");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();) {
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null ) {
							if (i < fieldNames.size() - 1) {
								if(!obdata.toString().equalsIgnoreCase("nursing_unit")){
									query.append("mocd."+obdata + ", ");
								}else{
									query.append("mon.nursing_unit" + ", ");
								}
								
							} else 
							{
								query.append("mocd."+obdata);
							}
							
						}
						i++;
					}

					query.append(" from machine_order_cart_settings as mocd");
					query.append(" INNER JOIN machine_order_nursing_details as mon on mon.id=mocd.nursing_unit");
					query.append(" where mocd.status='Active' ");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
						query.append(" and ");
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

					//System.out.println("QQQQQ>>"+query.toString());
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
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString().split(" ")[0])+" "+obdata[k].toString().split(" ")[1]);
										
										// DateUtil.convertDateToIndianFormat(obdata[k].toString())
									}

									
								}
							}

							Listhb.add(one);

						}
						setMachineMasterData(Listhb);
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
	
	
	
	// add data into machine_order
	public String addMachineOrder() {
		//System.out.println("addMachineOrder >>>>>>>>>>>>>>>>>>.  ");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_machine_order_conf", accountID, connectionSpace, "", 0, "table_name", "machine_order_conf");
				if (statusColName != null && statusColName.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
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

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							dateTrue = true;
						
					}
					
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator<String> it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						if (Parmname != null && !Parmname.trim().equals(""))
						{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
								insertData.add(ob);
						}
					}

					if(userTrue){
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
					}
					if(dateTrue){
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					}
					if(timeTrue){
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					}
					
					status = cbt.insertIntoTable("machine_order", insertData, connectionSpace);
					
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
	

	// add data into machine_ordertype
	public String addMachineOrderType() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_machine_ordertype_conf", accountID, connectionSpace, "", 0, "table_name", "machine_ordertype_conf");
				if (statusColName != null && statusColName.size() > 0) {
					// create table query based on configuration
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
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

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							dateTrue = true;
						
					}
					cbt.createTable22("machine_ordertype", tableColume, connectionSpace);
					String orderId[]=null;
				//	System.out.println(getOrder_type()+ " <<<<<<<<<<<");
					orderId=getOrder_type().trim().split(",");
				//	System.out.println("OrderID :::  ");
			//		for(String otype:orderId){System.out.println(otype);}
					if(orderId.length>0 && orderId!=null){
					for(String otype:orderId){
						if(otype!=null && !otype.trim().equalsIgnoreCase("") && !otype.equalsIgnoreCase("undefined")){

							List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
							InsertDataTable ob = null;
							
								ob = new InsertDataTable();
								ob.setColName("order_type");
								ob.setDataName(otype);
								insertData.add(ob);
								
								if (userTrue) {
									ob = new InsertDataTable();
									ob.setColName("userName");
									ob.setDataName(DateUtil.makeTitle(userName));
									insertData.add(ob);
								}
								
								if (dateTrue) {
									ob = new InsertDataTable();
									ob.setColName("date");
									ob.setDataName(DateUtil.getCurrentDateUSFormat());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("time");
									ob.setDataName(DateUtil.getCurrentTimeHourMin());
									insertData.add(ob);
								}

								List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
								Collections.sort(requestParameterNames);
								Iterator it = requestParameterNames.iterator();
								while (it.hasNext()) {
									String parmName = it.next().toString();
									String paramValue = request.getParameter(parmName);
									if (paramValue != null && !paramValue.equalsIgnoreCase("") 
											&& parmName != null && !parmName.equalsIgnoreCase("order_type")) {
										ob = new InsertDataTable();
										ob.setColName(parmName);
										ob.setDataName(paramValue);
										insertData.add(ob);
										}
									}
									status = cbt.insertIntoTable("machine_ordertype", insertData, connectionSpace);
						}
						
						
					}
					if (status) {
						addActionMessage("Data Save Successfully!!!");
					}
					}else{
						addActionMessage("oops error to add data. ");
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

	// modify Machine data
	public String modifyMachineOrderData() {

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
					cbt.updateTableColomn("machine_ordertype", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update machine_ordertype set status ='Inactive' where id='" + getId() + "'");
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
	
	
	

	public String getAllOrderNames()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try
			{
				jsonList = new JSONArray();
				String query=" select id,order_name from machine_order ";
				List machinelist = cbt.executeAllSelectQuery(query, connectionSpace);
				if (machinelist != null && machinelist.size() > 0)
				{
					for (Iterator iterator = machinelist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("ordername", object[1].toString());
							jsonList.add(innerobj);
						}
					}
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
	
	

	
	
	
	
	
	
	
	/*
	 * Getter & setter
	 * 
	 * */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}


	public List<ConfigurationUtilBean> getConfigKeyTextBox() {
		return configKeyTextBox;
	}


	public void setConfigKeyTextBox(List<ConfigurationUtilBean> configKeyTextBox) {
		this.configKeyTextBox = configKeyTextBox;
	}


	public List<ConfigurationUtilBean> getConfigKeyTextBox4Machine() {
		return configKeyTextBox4Machine;
	}


	public void setConfigKeyTextBox4Machine(
			List<ConfigurationUtilBean> configKeyTextBox4Machine) {
		this.configKeyTextBox4Machine = configKeyTextBox4Machine;
	}


	public List<GridDataPropertyView> getMasterViewProp() {
		return viewPageColumns;
	}


	public void setMasterViewProp(List<GridDataPropertyView> viewPageColumns) {
		this.viewPageColumns = viewPageColumns;
	}


	public JSONArray getJsonList() {
		return jsonList;
	}


	public void setJsonList(JSONArray jsonList) {
		this.jsonList = jsonList;
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


	public List<Object> getMachineMasterData() {
		return machineMasterData;
	}


	public void setMachineMasterData(List<Object> machineMasterData) {
		this.machineMasterData = machineMasterData;
	}


	public String getOrder_type() {
		return order_type;
	}


	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}


	public Map<String,String> getNurseUnit() {
		return nurseUnit;
	}


	public void setNurseUnit(Map<String,String> nurseUnit) {
		this.nurseUnit = nurseUnit;
	}
	
}
