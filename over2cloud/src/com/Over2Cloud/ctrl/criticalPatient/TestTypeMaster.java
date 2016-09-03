package com.Over2Cloud.ctrl.criticalPatient;

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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class TestTypeMaster extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	private List reportTypeList;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> viewTestTypeMaster;
	
	public String viewAddTestType()
	{

		String result = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				setAddPageDataFields();
				result = SUCCESS;
			}
			catch (Exception e)
			{
				result = ERROR;
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Showing Fields of Add Page
	 */
	@SuppressWarnings("rawtypes")
	public void setAddPageDataFields()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> labTypeListFieldsName = Configuration.getConfigurationData("mapped_test_type_configuration", accountID, connectionSpace, "", 0, "table_name", "test_type_configuration");
				if (labTypeListFieldsName != null)
				{
					reportTypeList = new ArrayList();
					for (GridDataPropertyView gdp : labTypeListFieldsName)
					{
						ConfigurationUtilBean conUtilBean = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());
							conUtilBean.setColType(gdp.getColType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							conUtilBean.setValidationType(gdp.getValidationType());
							reportTypeList.add(conUtilBean);
						}
					}

				}
			}
			catch (Exception exp)
			{

				exp.printStackTrace();
			}
		}
	}

	public String addTestType()
	{
		String result = ERROR;
		try
		{
			boolean session = ValidateSession.checkSession();
			if (session)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<com.Over2Cloud.Rnd.TableColumes> TableColumnName = new ArrayList<TableColumes>();

				String query = " select test_type from test_type where test_type='" + request.getParameter("test_type") + "' AND status='Active'";

				List list = cbt.executeAllSelectQuery(query, connectionSpace);
				if (list != null && list.size() > 0)
				{
					addActionMessage(" Oops " + request.getParameter("test_type") + " Already Exists !!!!!!!!!!!!!!!!!!!");
					result = SUCCESS;
				}
				else
				{
					List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_test_type_configuration", accountID, connectionSpace, "", 0, "table_name", "test_type_configuration");
					if (org2 != null)
					{
						// create table query based on configuration

						for (GridDataPropertyView gdp : org2)
						{

							if (gdp.getColomnName().equalsIgnoreCase("status"))
							{
								TableColumes ob1 = new TableColumes();
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default 'Active' ");
								TableColumnName.add(ob1);
							}
							else
							{
								TableColumes ob1 = new TableColumes();
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default NULL");
								TableColumnName.add(ob1);
							}
						}

						cbt.createTable22("test_type", TableColumnName, connectionSpace);

						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();

						InsertDataTable ob = null;
						while (it.hasNext())
						{
							String Parmname = it.next().toString();// control
																	// Name
																	// stored in
							// Paramname
							String paramValue = request.getParameter(Parmname);

							System.out.println(" Param Name "+Parmname);
							System.out.println(" Param Name "+paramValue);
							if (Parmname != null && !Parmname.trim().equals(""))
							{

								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
								insertData.add(ob);

							}
						}

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						boolean flag = cbt.insertIntoTable("test_type", insertData, connectionSpace);
						
						if (flag)
						{
							addActionMessage(" Test Type Add Successfulluy ..");
							result = SUCCESS;
						}
						else
						{
							addActionMessage(" Opps Some Error in Data Insertion !!!!!!!!!!!");
							result = ERROR;
						}

					}
				}
			}
			else
			{
				result = ERROR;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}
	//Header addTestType
	public String beforeAddTestType()
	{
		return "success";
	}
	// for main Grid page Call

	public String beforeTestTypeMaterGrid() {
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
	// for main keyword column name set AddTestType master grid
	public void setMachineMasterView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_test_type_configuration", accountID, connectionSpace, "", 0, "table_name", "test_type_configuration");
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
	// data view in grid for addTestType Response master
	@SuppressWarnings("unchecked")
	public String viewTestTypeMaster() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from test_type");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null) {
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					List fieldNames = Configuration.getColomnList("mapped_test_type_configuration", accountID, connectionSpace, "test_type_configuration");
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

					query.append(" from test_type  ");
					
					

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) 
					{
						
						query.append(" where ");
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

					

					query.append(" order by test_type asc");
					System.out.println("query  "+query);
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
								else
								{
									one.put(fieldNames.get(k).toString(),"NA");
								}
							}

							Listhb.add(one);

						}
						setViewTestTypeMaster(Listhb);
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
	public String modifyTestType() {

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
					cbt.updateTableColomn("test_type", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update test_type set status ='Inactive' where id='" + getId() + "'");
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
	
	
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<ConfigurationUtilBean> getReportTypeList()
	{
		return reportTypeList;
	}

	public void setReportTypeList(List<ConfigurationUtilBean> reportTypeList)
	{
		this.reportTypeList = reportTypeList;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getViewTestTypeMaster() {
		return viewTestTypeMaster;
	}

	public void setViewTestTypeMaster(List<Object> viewTestTypeMaster) {
		this.viewTestTypeMaster = viewTestTypeMaster;
	}
	
}
