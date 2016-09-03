package com.Over2Cloud.ctrl.compliance.complContacts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceContactsAction extends ActionSupport implements ServletRequestAware
{

	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String deptLevel = (String) session.get("userDeptLevel");

	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceContactsAction.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> complDDBox = null;
	private List<ConfigurationUtilBean> complTxtBox = null;

	private Map<Integer, String> subDeptMap = null;
	private Map<Integer, String> empMap = null;
	private Map<String, String> columnMap;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
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
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private List<Object> masterViewList1;
	private Map<String, String> level = null;
	private Map<Integer, String> employeeList;
	private String mainHeaderName;
	private String fromDept;
	private String forDept;
	private String empName;
	private String selectedId;
	private String userDeptId;
	private String userDeptName;
	private String forDeptId;
	private String moduleName;
	private String mappedLevel;
	private JSONArray jsonObjArray;
	private String contactId;
	private String levelName;
	private String groupField;
	private String deptName;
	private String leveldata;
	private String forSubDept_id;
	private List<Object> locationList;
	private String location;
	private JSONArray deptMap = null;
	private JSONArray levelValue = null;
	private String totalCount;
	private Map<String, String> groupMap = null;
	private Map<String, String> subTypeMap;
	private Map<String, String> subDepartmentMap;
	private Map<String, String> locationMap;
	private Map<String, String> levelMap;
	private Map<String, String> moduleList;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String subType;

	@Override
	public String execute()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				level = new LinkedHashMap<String, String>();
				int lastCount = 0;
				if (moduleName != null)
				{

					if (moduleName.equalsIgnoreCase("HDM"))
					{
						lastCount = 6;
					} else if (moduleName.equalsIgnoreCase("WFPM"))
					{
						lastCount = 5;
					} else if (moduleName.equalsIgnoreCase("COMPL"))
					{
						lastCount = 2;
					} else if (moduleName.equalsIgnoreCase("FM"))
					{
						lastCount = 5;
					}

					lastCount = 6;
					for (int i = 2; i <= lastCount; i++)
					{
						level.put(String.valueOf(i), "Level " + i);
					}
				} else
				{
					setModuleName("HDM");
				}

				subTypeMap = new HashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List subTypeList = cbt.executeAllSelectQuery("select distinct dept.id,dept.deptName from department as dept inner join compliance_contacts as compl on compl.fromDept_id=dept.id where compl.moduleName='" + moduleName + "' order by dept.deptName", connectionSpace);
				if (subTypeList != null && subTypeList.size() > 0)
				{
					for (Iterator iterator = subTypeList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								subTypeMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}

				subDepartmentMap = new HashMap<String, String>();
				// CommonOperInterface cbt = new
				// CommonConFactory().createInterface();
				List subDepartmentList = cbt.executeAllSelectQuery("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept inner join department as dept on dept.id=subdept.deptid inner join compliance_contacts as compl on compl.forDept_id=subdept.id and compl.moduleName='" + moduleName + "' order by subdept.subdeptname", connectionSpace);
				if (subDepartmentList != null && subDepartmentList.size() > 0)
				{
					for (Iterator iterator = subDepartmentList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								subDepartmentMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				locationMap = new HashMap<String, String>();
				// CommonOperInterface cbt = new
				// CommonConFactory().createInterface();
				List locationList = cbt.executeAllSelectQuery("select distinct compl.location as ID,compl.location as LocationName from  compliance_contacts as compl  order by compl.location", connectionSpace);
				if (locationList != null && locationList.size() > 0)
				{
					for (Iterator iterator = locationList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								locationMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				/*
				 * totalCount=getCounters(connectionSpace);
				 * System.out.println("totalCount" +totalCount);
				 */
				moduleList = new LinkedHashMap<String, String>();
				moduleList = CommonWork.fetchAppAssignedUser(connectionSpace, userName);

				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	/*
	 * @SuppressWarnings("rawtypes") public String beforeContactAdd() { boolean
	 * sessionFlag = ValidateSession.checkSession(); if (sessionFlag) { try {
	 * ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
	 * 
	 * subDeptMap = new LinkedHashMap<Integer, String>(); //deptMap =
	 * cmnOper.getAllDepartment(); List loggedUserData = new
	 * ComplianceUniversalAction
	 * ().getEmpDataByUserName(Cryptography.encrypt(userName,
	 * DES_ENCRYPTION_KEY), "1"); if (loggedUserData != null &&
	 * loggedUserData.size() > 0) { for (Iterator iterator =
	 * loggedUserData.iterator(); iterator.hasNext();) { Object[] object =
	 * (Object[]) iterator.next(); userDeptId = object[3].toString();
	 * userDeptName = object[4].toString(); } } setAddPageDataFields(); return
	 * SUCCESS; } catch (Exception exp) { log.error("Date : " +
	 * DateUtil.getCurrentDateIndianFormat() + " Time: " +
	 * DateUtil.getCurrentTime() + " " +
	 * "Problem in Compliance  method beforeContactAdd of class " + getClass(),
	 * exp); exp.printStackTrace(); return ERROR; } } else { return LOGIN; } }
	 */

	@SuppressWarnings("rawtypes")
	public String beforeContactAdd()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
				moduleList = new LinkedHashMap<String, String>();
				moduleList = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				// locationList=getAllLocationList(connectionSpace);
				jsonObjArray = new JSONArray();
				// subDeptMap = new LinkedHashMap<Integer, String>();
				// deptMap = cmnOper.getAllDepartment();
				List loggedUserData = new ComplianceUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");
				if (loggedUserData != null && loggedUserData.size() > 0)
				{
					for (Iterator iterator = loggedUserData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userDeptId = object[3].toString();
						userDeptName = object[4].toString();
						jsonObjArray = cmnOper.getAllSubDeptByDeptId(userDeptId);
					}
				}
				setAddPageDataFields();
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getAllLocationList()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonObjArray = new JSONArray();
				if (moduleName != null && moduleName.equalsIgnoreCase("CRF"))
					jsonObjArray = getAllNursingUnitList(connectionSpace);
				else if (moduleName != null && moduleName.equalsIgnoreCase("CPS"))
					jsonObjArray = getAllLocation(connectionSpace);
				else if (moduleName != null && (moduleName.equalsIgnoreCase("Pharmacy") || moduleName.equalsIgnoreCase("LPS") || moduleName.equalsIgnoreCase("LabOrd")))
					jsonObjArray = getAllNursingUnitListPharma(connectionSpace);
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}

	}

	public JSONArray getAllLocation(SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		JSONArray location = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		try
		{
			if (forSubDept_id != null && !forSubDept_id.equalsIgnoreCase("-1") && !forSubDept_id.equalsIgnoreCase("All"))
			{
				query.append("SELECT DISTINCT id,serv_loc FROM cps_service where service_name='" + forSubDept_id + "' ");

				// System.out.println(query.toString());
				data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] objectCol = (Object[]) iterator.next();

						if (objectCol[0] != null && objectCol[1] != null && !objectCol[1].toString().equals(""))
						{
							String loc[] = objectCol[1].toString().split("#");
							for (int a = 0; a < loc.length; a++)
							{
								jsonObj.put("id", loc[a].toString());
								jsonObj.put("name", loc[a].toString());
								location.add(jsonObj);
							}

						}

					}
				}
			} else if (forSubDept_id != null && forSubDept_id.equalsIgnoreCase("For All Sub-Department") || forSubDept_id.equalsIgnoreCase("All") || forSubDept_id.equalsIgnoreCase("Service Manager"))
			{
				query.append("SELECT DISTINCT location_name as id,location_name as name FROM cps_location ");
				data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] objectCol = (Object[]) iterator.next();
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						location.add(jsonObj);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return location;
	}

	@SuppressWarnings("rawtypes")
	public String getSubDepartmentList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonObjArray = new JSONArray();
				jsonObjArray = getSubDepartment(connectionSpace);
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getDepartmentList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonObjArray = new JSONArray();
				jsonObjArray = getAllDepartment(connectionSpace);
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getLocationListData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonObjArray = new JSONArray();
				jsonObjArray = getAllLocationListData(connectionSpace);
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public void setAddPageDataFields()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		String accountID = (String) session.get("accountid");
		if (sessionFlag)
		{
			try
			{
				groupMap = new LinkedHashMap<String, String>();
				List<GridDataPropertyView> complianceFieldsName = Configuration.getConfigurationData("mapped_compl_contact_config", accountID, connectionSpace, "", 0, "table_name", "compl_contact_config");
				if (complianceFieldsName != null)
				{
					complDDBox = new ArrayList<ConfigurationUtilBean>();
					complTxtBox = new ArrayList<ConfigurationUtilBean>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					ConfigurationUtilBean conUtilBean = null;
					for (GridDataPropertyView gdp : complianceFieldsName)
					{
						conUtilBean = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("fromDept_id"))
							{
								String query = "SELECT gp.id,gp.groupName FROM groupinfo AS gp INNER JOIN employee_basic AS emp ON gp.id=emp.groupId ORDER BY gp.groupName";
								List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
								if (dataList != null && dataList.size() > 0)
								{
									for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										groupMap.put(object[0].toString(), object[1].toString());
									}
								}

								conUtilBean.setField_name("Group Name");
								conUtilBean.setField_value("group");
								complDDBox.add(conUtilBean);

								conUtilBean = new ConfigurationUtilBean();
								conUtilBean.setField_name("Sub-Group Name");
								conUtilBean.setField_value("subgroup");
								complDDBox.add(conUtilBean);

								conUtilBean = new ConfigurationUtilBean();
								conUtilBean.setField_name(gdp.getHeadingName());
								conUtilBean.setField_value(gdp.getColomnName());
								conUtilBean.setColType(gdp.getColType());
								conUtilBean.setValidationType(gdp.getValidationType());
								if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
								{
									conUtilBean.setMandatory(true);
								} else
								{
									conUtilBean.setMandatory(false);
								}
								complDDBox.add(conUtilBean);
							}

						} else if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							} else
							{
								conUtilBean.setMandatory(false);
							}
							complTxtBox.add(conUtilBean);
						}
					}
					/*
					 * if(moduleName!=null && moduleName.equalsIgnoreCase("HDM")
					 * && deptLevel.equalsIgnoreCase("2")) { conUtilBean=new
					 * ConfigurationUtilBean();
					 * conUtilBean.setField_name("Sub-Dept Name");
					 * conUtilBean.setField_value("subdeptname");
					 * conUtilBean.setColType("D");
					 * conUtilBean.setMandatory(true);
					 * complDDBox.add(conUtilBean); }
					 */
				}
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
	}

	public String complianceContactsAdd()
	{
		System.out.println("location " + location);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_compl_contact_config", accountID, connectionSpace, "", 0, "table_name", "compl_contact_config");
				if (statusColName != null)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false, flag = false;
					TableColumes ob1 = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("compliance_contacts", Tablecolumesaaa, connectionSpace);

					String module = request.getParameter("moduleName");

					if (empName != null)
						empName.concat(",");

					ob = new InsertDataTable();
					ob.setColName("fromDept_id");
					ob.setDataName(fromDept);
					insertData.add(ob);
					if (moduleName.equalsIgnoreCase("HDM") || moduleName.equalsIgnoreCase("ORD") || moduleName.equalsIgnoreCase("CRF") || moduleName.equalsIgnoreCase("CPS") || moduleName.equalsIgnoreCase("CRC") || moduleName.equalsIgnoreCase("Pharmacy") || moduleName.equalsIgnoreCase("LPS") || moduleName.equalsIgnoreCase("LabOrd"))
					{
						ob = new InsertDataTable();
						ob.setColName("forDept_id");
						ob.setDataName(forSubDept_id);
						insertData.add(ob);
					} else
					{
						String empDept = userDept(connectionSpace, userName);
						ob = new InsertDataTable();
						ob.setColName("forDept_id");
						ob.setDataName(empDept);
						insertData.add(ob);
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

					ob = new InsertDataTable();
					ob.setColName("work_status");
					ob.setDataName("0");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("level");
					ob.setDataName("1");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("moduleName");
					ob.setDataName(module);
					insertData.add(ob);
					if (module != null && module.equalsIgnoreCase("CRF"))
					{
						ob = new InsertDataTable();
						ob.setColName("location");
						ob.setDataName(location);
						insertData.add(ob);
					}
					if (module != null && module.equalsIgnoreCase("CPS"))
					{
						ob = new InsertDataTable();
						ob.setColName("location");
						ob.setDataName(location);
						insertData.add(ob);
					}
					if (module != null && module.equalsIgnoreCase("Pharmacy"))
					{
						ob = new InsertDataTable();
						ob.setColName("location");
						ob.setDataName(location);
						insertData.add(ob);
					}
					if (module != null && module.equalsIgnoreCase("LPS"))
					{
						ob = new InsertDataTable();
						ob.setColName("location");
						ob.setDataName(location);
						insertData.add(ob);
					}
					
					if (module != null && module.equalsIgnoreCase("LabOrd"))
					{
						ob = new InsertDataTable();
						ob.setColName("location");
						ob.setDataName(location);
						insertData.add(ob);
					}

					if (module != null && module.equalsIgnoreCase("HDM"))
					{
						ob = new InsertDataTable();
						ob.setColName("deptLevel");
						ob.setDataName("2");
						insertData.add(ob);
					} else
					{
						ob = new InsertDataTable();
						ob.setColName("deptLevel");
						ob.setDataName("1");
						insertData.add(ob);
					}

					if (empName != null)
					{
						String empArr[] = empName.split(",");
						for (int i = 0; i < empArr.length; i++)
						{
							ob = new InsertDataTable();
							ob.setColName("emp_id");
							ob.setDataName(empArr[i]);
							insertData.add(ob);
							flag = getUpdateContact(forDept, empArr[i], module);
							if (!flag)
							{
								status = cbt.insertIntoTable("compliance_contacts", insertData, connectionSpace);
							}
							insertData.remove(insertData.size() - 1);
						}
					}
					if (status)
					{
						addActionMessage("Contact details added successfully!!!");
						returnResult = SUCCESS;
					} else if (flag)
					{
						addActionMessage("Contact details added successfully!!!");
						returnResult = SUCCESS;
					} else
					{
						addActionMessage("There is some error in data!");
						returnResult = ERROR;
					}
				}

			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method complianceTaskAdd of class " + getClass(), exp);
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String userDept(SessionFactory connectionSpace, String userID)
	{
		String deptName = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("select dp.id from department as dp inner join employee_basic as eb on dp.id=eb.deptname inner join useraccount as ua on ua.id=eb.useraccountid " + "where ua.userID='" + Cryptography.encrypt(userID, DES_ENCRYPTION_KEY) + "'");
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					deptName = obdata.toString();
				}
			}
		} catch (Exception e)
		{

		}
		return deptName;
	}

	@SuppressWarnings("rawtypes")
	public boolean getUpdateContact(String forDept2, String empId, String ModuleName)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "SELECT * FROM compliance_contacts WHERE work_status = '1' AND forDept_id='" + forDept2 + "' AND emp_id ='" + empId + "' AND moduleName='" + moduleName + "'";
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				flag = true;
				StringBuilder query2 = new StringBuilder();
				query2.append("UPDATE compliance_contacts SET work_status = '0' WHERE forDept_id='" + forDept2 + "' AND emp_id ='" + empId + "' AND moduleName='" + moduleName + "'");
				cbt.updateTableColomn(connectionSpace, query2);
			} else
			{
				flag = false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	public String beforeViewComplContacts()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// execute();
				if (new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName) != null && !new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName)[4].equalsIgnoreCase(""))
				{
					mainHeaderName = new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName)[4];
				} else
				{
					mainHeaderName = "NA";
				}
				StringBuilder str = new StringBuilder();
				str.append("{value:'");
				/* System.out.println("module name " + moduleName); */
				if (moduleName != null)
				{
					for (int i = 1; i <= 6; i++)
					{
						str.append(i + ":Level " + i + ";");
					}
				}
				String test = str.substring(0, (str.toString().length() - 1));
				test = test + "'}";
				levelName = test;
				/* System.out.println("level name " + levelName); */
				setViewProp();

				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public void setViewProp()
	{

		GridDataPropertyView gpv = null;
		gpv = new GridDataPropertyView();
		gpv.setColomnName("subdept.subdeptname");
		gpv.setHeadingName("Sub Department");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(250);
		masterViewProp.add(gpv);
		groupField = "subdept.subdeptname";

		gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(50);
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.empName");
		gpv.setHeadingName("Name");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(150);
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("grp.groupName");
		gpv.setHeadingName("From Contact Type");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(250);
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("dept.deptName");
		gpv.setHeadingName("From Contact Sub-Type");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(250);
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.mobOne");
		gpv.setHeadingName("Mobile No");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.emailIdOne");
		gpv.setHeadingName("E-Mail");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		gpv.setWidth(250);
		masterViewProp.add(gpv);

		if (moduleName != null && moduleName.equalsIgnoreCase("Pharmacy"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ctm.location");
			gpv.setHeadingName("Location");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			gpv.setWidth(325);
			masterViewProp.add(gpv);
		}

		if (moduleName != null && moduleName.equalsIgnoreCase("CRF"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ctm.location");
			gpv.setHeadingName("Location");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			gpv.setWidth(325);
			masterViewProp.add(gpv);
		}
		if (moduleName != null && moduleName.equalsIgnoreCase("LPS"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ctm.location");
			gpv.setHeadingName("Location");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			gpv.setWidth(325);
			masterViewProp.add(gpv);
		}
		if (moduleName != null && moduleName.equalsIgnoreCase("LabOrd"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ctm.location");
			gpv.setHeadingName("Location");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			gpv.setWidth(325);
			masterViewProp.add(gpv);
		}


		session.put("masterViewProp", masterViewProp);

	}

	public String getCounters(SessionFactory connectionSpace)
	{
		String dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
		String counter = null;
		StringBuilder builder = new StringBuilder();
		if (dept_subDeptId != null && !dept_subDeptId.equalsIgnoreCase(" "))
		{
			builder.append(" SELECT count(*) FROM compliance_contacts AS ctm ");
			builder.append(" INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id ");
			builder.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId ");
			builder.append(" INNER JOIN department AS dept ON ctm.fromDept_id=dept.id ");
			builder.append("  where ctm.work_status!=1 and ctm.forDept_id=" + dept_subDeptId + " and moduleName='" + moduleName + "'");
		}
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
		{
			counter = dataList.get(0).toString();
		}
		return counter;
	}

	public List getDeptName(String moduleName, String deptID, SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{

			query.append(" SELECT  dt.id,dt.deptName FROM department as dt");
			query.append(" INNER JOIN compliance_contacts as cc ON dt.id=cc.fromDept_id");
			query.append(" WHERE cc.moduleName='" + moduleName + "' AND cc.forDept_id='" + deptID + "' GROUP BY dt.deptName");

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getAllNursingUnitList(SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		JSONArray location = new JSONArray();
		try
		{

			query.append("SELECT DISTINCT rpd.nursing_unit as ID,rpd.nursing_unit as locationName FROM referral_data AS refdata");
			query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
			query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id");
			query.append(" ORDER BY nursing_unit ");

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null && !objectCol[1].toString().equals(""))
					{
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						location.add(jsonObj);
					}

				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return location;
	}

	
	public JSONArray getAllNursingUnitListPharma(SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		JSONArray location = new JSONArray();
		try
		{

			query.append("select nursing_code, nursing_unit from machine_order_nursing_details");
			

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null && !objectCol[1].toString().equals(""))
					{
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						location.add(jsonObj);
					}

				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return location;
	}
	@SuppressWarnings("unchecked")
	public JSONArray getSubDepartment(SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		JSONArray subDepartment = new JSONArray();
		try
		{

			query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept ");
			query.append(" inner join department as dept on dept.id=subdept.deptid ");
			query.append(" inner join compliance_contacts as compl on compl.forDept_id=subdept.id ");
			query.append(" where compl.moduleName='" + moduleName + "'");
			if (subType != null && !subType.equalsIgnoreCase("-1"))
				query.append("   and compl.fromDept_id='" + subType + "'");
			query.append(" order by subdept.subdeptname");

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null && !objectCol[1].toString().equals(""))
					{
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						subDepartment.add(jsonObj);
					}

				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return subDepartment;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getAllDepartment(SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		JSONArray allDepartment = new JSONArray();
		try
		{
			query.append("select distinct dept.id,dept.deptName from department as dept ");
			query.append(" inner join compliance_contacts as compl on compl.fromDept_id=dept.id ");
			query.append(" where compl.moduleName='" + moduleName + "' ");
			query.append(" order by dept.deptName");

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null && !objectCol[1].toString().equals(""))
					{
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						allDepartment.add(jsonObj);
					}

				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return allDepartment;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getAllLocationListData(SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		JSONArray locationList = new JSONArray();
		try
		{

			query.append("select distinct compl.location as ID,compl.location as LocationName from  compliance_contacts as compl ");
			query.append(" where compl.moduleName='" + moduleName + "' ");
			if (subType != null && !subType.equalsIgnoreCase("-1"))
				query.append(" and compl.fromDept_id='" + subType + "' ");
			if (forSubDept_id != null && !forSubDept_id.equalsIgnoreCase("-1"))
				query.append(" and compl.forDept_id='" + forSubDept_id + "' ");
			query.append(" order by compl.location ");

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null && !objectCol[1].toString().equals(""))
					{
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						locationList.add(jsonObj);
					}

				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return locationList;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewComplContacts()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String dept_subDeptId = null;
				/*
				 * System.out.println("user name " + userName + " deplevel " +
				 * deptLevel + "   modulnem " + moduleName);
				 */
				dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
				/*
				 * System.out.println(" ************ dept_subDeptId   " +
				 * dept_subDeptId);
				 */

				List<Object> Listhb = new ArrayList<Object>();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("ctm.id,");
							} else
							{
								query.append(gpv.getColomnName().toString() + ",");
							}
						} else
						{
							query.append(gpv.getColomnName().toString());
						}

						i++;
					}
				}
				query.append(" FROM compliance_contacts AS ctm");

				/*
				 * if (dept_subDeptId != null &&
				 * !dept_subDeptId.equalsIgnoreCase(" ")) { if (moduleName !=
				 * null && moduleName.equalsIgnoreCase("HDM")) { query.append(
				 * " INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id INNER JOIN groupinfo AS grp ON grp.id=emp.groupId INNER JOIN subdepartment AS subdept ON ctm.forDept_id=subdept.id  INNER JOIN department AS dept ON subdept.deptid=dept.id INNER JOIN designation AS  desig ON desig.id=emp.desg "
				 * ); query.append(
				 * "  where ctm.work_status!=1 and ctm.forDept_id IN (select id from subdepartment where deptid='"
				 * + dept_subDeptId + "') and ctm.moduleName='" + moduleName +
				 * "'"); } else { query.append(
				 * " INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id INNER JOIN groupinfo AS grp ON grp.id=emp.groupId INNER JOIN department AS dept ON ctm.fromDept_id=dept.id "
				 * );
				 * query.append("  where ctm.work_status!=1 and ctm.forDept_id="
				 * + dept_subDeptId + " and moduleName='" + moduleName + "'"); }
				 * 
				 * }
				 */

				if (dept_subDeptId != null && !dept_subDeptId.equalsIgnoreCase(" "))
				{
					if (moduleName != null && (moduleName.equalsIgnoreCase("HDM") || moduleName.equalsIgnoreCase("ORD") || moduleName.equalsIgnoreCase("CRF") || moduleName.equalsIgnoreCase("CPS") || moduleName.equalsIgnoreCase("CRC") || moduleName.equalsIgnoreCase("Pharmacy") || moduleName.equalsIgnoreCase("LPS") || moduleName.equalsIgnoreCase("LabOrd")))
					{
						query.append(" INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id INNER JOIN groupinfo AS grp ON grp.id=emp.groupId INNER JOIN subdepartment AS subdept ON ctm.forDept_id=subdept.id  INNER JOIN department AS dept ON subdept.deptid=dept.id ");
						query.append("  where ctm.work_status!=1  ");
						String userType = (String) session.get("userTpe");
						if (userType != null && !userType.equalsIgnoreCase("M"))
						{
							query.append(" and ctm.forDept_id IN (select id from subdepartment where deptid='" + dept_subDeptId + "') ");
						}
						query.append(" and ctm.moduleName='" + moduleName + "' ");
					} else
					{
						query.append(" INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id INNER JOIN groupinfo AS grp ON grp.id=emp.groupId INNER JOIN department AS dept ON ctm.fromDept_id=dept.id ");
						query.append("  where ctm.work_status!=1 and ctm.forDept_id=" + dept_subDeptId + " and ctm.moduleName='" + moduleName + "'");
					}

				}

				/*
				 * else { query.append(" where ctm.work_status=0"); }
				 */
				if (getSubType() != null && !getSubType().equalsIgnoreCase("-1"))
				{
					query.append(" and ctm.fromDept_id='" + getSubType() + "' ");
				}
				if (getLocation() != null && !getLocation().equalsIgnoreCase("-1"))
				{
					query.append(" and ctm.location='" + getLocation() + "' ");
				}
				if (getForSubDept_id() != null && !getForSubDept_id().equalsIgnoreCase("-1"))
				{
					query.append(" and ctm.forDept_id='" + getForSubDept_id() + "' ");
				}

				if (getLeveldata() != null && !getLeveldata().equals("-1"))
				{
					query.append(" and level='" + getLeveldata() + "' ");
				}

				query.append(" ORDER BY emp.empName");
				// System.out.println("*********************************** query is "
				// + query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if (gpv.getColomnName().equalsIgnoreCase("level"))
								{
									one.put(gpv.getColomnName(), "Level-" + obdata[k].toString());
								} else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
								}
							} else
							{
								one.put(gpv.getColomnName().toString(), "NA");
							}
							k++;
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method viewComplContacts of class " + getClass(), exp);
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getDept_SubdeptId(String userName, String deptLevel, String moduleName)
	{
		String dept_subDeptId = null;
		try
		{
			dept_subDeptId = new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName)[3];
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dept_subDeptId;
	}

	public String beforeFindEmpDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();

				gpv.setColomnName("id");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);
				if (deptLevel != null && deptLevel.equalsIgnoreCase("2"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("subdeptname");
					gpv.setHeadingName("Sub Department");
					gpv.setEditable("true");
					gpv.setSearch("true");
					gpv.setHideOrShow("true");
					gpv.setFormatter("false");
					masterViewProp.add(gpv);
				}
				gpv = new GridDataPropertyView();
				gpv.setColomnName("empName");
				gpv.setHeadingName("Name");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("designation");
				gpv.setHeadingName("Designation");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mobOne");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("emailIdOne");
				gpv.setHeadingName("E-Mail");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String findEmpDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<Object> Listhb = new ArrayList<Object>();
				masterViewList = new ArrayList<Object>();
				List empList = new ArrayList();
				StringBuilder queryIS = new StringBuilder();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				empList = cbt.executeAllSelectQuery("SELECT emp_id FROM compliance_contacts WHERE forDept_id='" + getForDeptId() + "' AND work_status in ('0','3')  AND moduleName='" + moduleName + "'", connectionSpace);

				if (empList != null && empList.size() > 0)
				{
					queryIS.append("SELECT emp.id, emp.empName,emp.mobOne,emp.emailIdOne,emp.designation");
					queryIS.append(" FROM employee_basic AS emp ");
					queryIS.append(" WHERE emp.deptname='" + getFromDept() + "'");
					queryIS.append(" AND emp.id NOT IN (SELECT emp_id FROM compliance_contacts WHERE forDept_id='" + getForDeptId() + "' AND work_status in ('0','3') AND emp.flag=0 AND moduleName='" + moduleName + "')");
					queryIS.append(" AND emp.flag=0  order by empName asc");

					/*
					 * queryIS.append(
					 * "SELECT emp.id, emp.empName,emp.mobOne,emp.emailIdOne,sub.subdeptname,design.designationname  FROM employee_basic AS emp "
					 * +
					 * "INNER JOIN subdepartment AS sub ON emp.subdept=sub.id "
					 * + "INNER JOIN department AS dept ON sub.deptid=dept.id "
					 * +
					 * "INNER JOIN designation AS  design ON design.id=emp.designation "
					 * + "WHERE dept.id='" + getFromDept() +
					 * "' AND emp.id NOT IN (SELECT emp_id FROM compliance_contacts WHERE forDept_id='"
					 * + getForDeptId() +
					 * "' AND work_status in ('0','3') AND moduleName='" +
					 * moduleName + "')");
					 * queryIS.append(" order by empName asc");
					 */

				} else
				{
					queryIS.append("SELECT emp.id, emp.empName,emp.mobOne,emp.emailIdOne,emp.designation ");
					queryIS.append(" FROM employee_basic AS emp ");
					queryIS.append(" WHERE emp.deptname='" + getFromDept() + "' AND emp.flag=0  ");
					queryIS.append(" order by empName asc");

				}
				/*
				 * System.out.println("QUERY IS AS *******************::  " +
				 * queryIS.toString());
				 */
				empList = cbt.executeAllSelectQuery(queryIS.toString(), connectionSpace);

				if (empList != null && empList.size() > 0)
				{
					for (Iterator iterator = empList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						if (object[0] != null)
							one.put("id", object[0].toString());
						else
							one.put("id", "NA");

						if (object[1] != null)
							one.put("empName", object[1].toString());
						else
							one.put("empName", "NA");

						if (object[2] != null)
							one.put("mobOne", object[2].toString());
						else
							one.put("mobOne", "NA");

						if (object[3] != null)
							one.put("emailIdOne", object[3].toString());
						else
							one.put("emailIdOne", "NA");

						/*
						 * if (object[4] != null) one.put("subdeptname",
						 * object[4].toString()); else one.put("subdeptname",
						 * "NA");
						 */

						if (object[4] != null)
							one.put("designation", object[4].toString());
						else
							one.put("designation", "NA");

						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeComplContactEdits()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				level = new LinkedHashMap<String, String>();
				level.put("1", "Level 1");
				level.put("2", "Level 2");
				setAddPageDataFields();
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeMappedContact()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				level = new LinkedHashMap<String, String>();
				int lastCount = 0;
				if (moduleName != null)
				{
					/*
					 * if(moduleName.equalsIgnoreCase("HDM")) { lastCount=5; }
					 * else if(moduleName.equalsIgnoreCase("WFPM")) {
					 * lastCount=5; } else
					 * if(moduleName.equalsIgnoreCase("COMPL")) { lastCount=2; }
					 * else if(moduleName.equalsIgnoreCase("FM")) { lastCount=5;
					 * }
					 */
					lastCount = 6;
					for (int i = 2; i <= lastCount; i++)
					{
						level.put(String.valueOf(i), "Level " + i);
					}
				}

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1;

				ob1 = new TableColumes();
				ob1.setColumnname("mapped_with");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("contact_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("contact_mapping_detail", Tablecolumesaaa, connectionSpace);

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeMappedContact of class " + getClass(), e);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getMappedEmp()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (moduleName != null && mappedLevel != null)
				{
					String dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
					if (dept_subDeptId != null)
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						jsonObjArray = new JSONArray();
						StringBuilder qString = new StringBuilder();
						qString.append("SELECT  eb.id,eb.empName FROM compliance_contacts AS cc ");
						qString.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.forDept_id=" + dept_subDeptId);
						qString.append(" AND cc.moduleName='" + moduleName + "' AND level='" + mappedLevel + "' ORDER BY eb.empName");
						// //System.out.println("Query String  "+qString.toString
						// ());
						List data = cbt.executeAllSelectQuery(qString.toString(), connectionSpace);
						if (data != null && data.size() > 0)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null || object[1] != null)
								{
									JSONObject jsonObj = new JSONObject();
									jsonObj.put("id", object[0].toString());
									jsonObj.put("name", object[1].toString());
									jsonObjArray.add(jsonObj);
								}
							}
						}
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getMappedEmp of class " + getClass(), e);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeGetUnMappedEmp()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();

				gpv.setColomnName("id");
				gpv.setHeadingName("id");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("level");
				gpv.setHeadingName("Level");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("otherMapped");
				gpv.setHeadingName("Other Mapped");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				gpv.setAlign("left");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("empName");
				gpv.setHeadingName("Employee");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				gpv.setAlign("left");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mobOne");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("emailIdOne");
				gpv.setHeadingName("E-Mail");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				gpv.setAlign("left");
				masterViewProp.add(gpv);

				returnResult = SUCCESS;

			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeGetUnMappedEmp of class " + getClass(), e);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getUnMappedEmp()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				if (dept_subDeptId != null && !dept_subDeptId.equalsIgnoreCase(" "))
				{
					query1.append("SELECT  COUNT(eb.id) FROM compliance_contacts AS cc");
					query1.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.forDept_id=" + dept_subDeptId);
					query1.append(" AND cc.moduleName='" + moduleName + "' AND level<" + mappedLevel + " AND cc.id");
					query1.append(" NOT IN(SELECT contact_id FROM contact_mapping_detail WHERE mapped_with=" + empName + ") ORDER BY eb.empName");
				}
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					Object obdata1 = null;
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						obdata1 = (Object) it.next();
						count = (BigInteger) obdata1;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("SELECT  cc.id,eb.empName,eb.mobOne,eb.emailIdOne,cc.level FROM compliance_contacts AS cc");
					query.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.forDept_id=" + dept_subDeptId);
					query.append(" AND cc.moduleName='" + moduleName + "' AND level<" + mappedLevel + " AND cc.id");
					query.append(" NOT IN(SELECT contact_id FROM contact_mapping_detail WHERE mapped_with=" + empName + ")");
					query.append(" ORDER BY eb.empName limit " + from + "," + to);
					// //System.out.println(query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							Map<String, Object> one = new LinkedHashMap<String, Object>();
							if (obdata[0] != null)
							{
								one.put("id", obdata[0].toString());
								StringBuilder query2 = new StringBuilder("");
								query2.append("SELECT eb.empName FROM employee_basic AS eb ");
								query2.append(" INNER JOIN contact_mapping_detail AS cmd ON cmd.mapped_with=eb.id");
								query2.append(" WHERE cmd.contact_id=" + obdata[0].toString());
								// //System.out.println("Query String dsvnsdvds "
								// +query2.toString());
								List dataList = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
								StringBuilder empBuilder = new StringBuilder();
								if (dataList != null && dataList.size() > 0)
								{
									for (int i = 0; i < dataList.size(); i++)
									{
										empBuilder.append(dataList.get(i) + ", ");
									}
									if (dataList != null)
									{
										String complianceId = empBuilder.substring(0, (empBuilder.toString().length() - 2));
										one.put("otherMapped", complianceId);
									} else
									{
										one.put("otherMapped", "NA");
									}
								} else
								{
									one.put("otherMapped", "NA");
								}
							} else
							{
								one.put("id", "NA");
							}
							if (obdata[1] != null)
								one.put("empName", obdata[1].toString());
							else
								one.put("empName", "NA");

							if (obdata[2] != null)
								one.put("mobOne", obdata[2].toString());
							else
								one.put("mobOne", "NA");

							if (obdata[3] != null)
								one.put("emailIdOne", obdata[3].toString());
							else
								one.put("emailIdOne", "NA");

							if (obdata[4] != null)
								one.put("level", "Level " + obdata[4].toString());
							else
								one.put("level", "NA");

							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;

			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getUnMappedEmp of class " + getClass(), e);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String mapUnMappedContact()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false, flag = false;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (contactId != null)
				{
					ob = new InsertDataTable();
					ob.setColName("mapped_with");
					ob.setDataName(empName);
					insertData.add(ob);

					String contactArr[] = contactId.split(",");
					for (int i = 0; i < contactArr.length; i++)
					{
						ob = new InsertDataTable();
						ob.setColName("contact_id");
						ob.setDataName(contactArr[i]);
						insertData.add(ob);
						status = cbt.insertIntoTable("contact_mapping_detail", insertData, connectionSpace);
						insertData.remove(insertData.size() - 1);
					}
				}
				if (status)
				{
					addActionMessage("Contact details added successfully!!!");
					returnResult = SUCCESS;
				} else if (flag)
				{
					addActionMessage("Contact details added successfully!!!");
					returnResult = SUCCESS;
				} else
				{
					addActionMessage("There is some error in data!");
					returnResult = ERROR;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method mapUnMappedContact of class " + getClass(), e);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String getContShareEmpDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("SELECT  COUNT(cc.id) " + "FROM compliance_contacts AS cc " + "INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id " + "INNER JOIN contact_sharing_detail AS csd ON csd.contact_id=cc.id " + "where cc.work_status=0 AND csd.sharing_with=" + id + " ORDER BY eb.empName");
				// System.out.println("Query String "+query1.toString());
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					StringBuilder query = new StringBuilder("");
					query.append("SELECT  eb.id,eb.empName,cc.level,eb.mobOne,eb.emailIdOne,eb.designation,grp.groupName " + "FROM compliance_contacts AS cc " + "INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id INNER JOIN groupinfo AS grp ON grp.id=eb.groupId " + "INNER JOIN contact_sharing_detail AS csd ON csd.contact_id=cc.id " + "where cc.work_status=0 AND csd.sharing_with=" + id + " ORDER BY eb.empName");
					// System.out.println(" Query String  "+query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] object = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							object = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							if (object[0] != null)
								one.put("id", object[0].toString());
							else
								one.put("id", "NA");

							if (object[1] != null)
								one.put("shareWithEmpName", object[1].toString());
							else
								one.put("shareWithEmpName", "NA");

							if (object[2] != null)
								one.put("shareEmpLevel", "Level " + object[2].toString());
							else
								one.put("shareEmpLevel", "NA");

							if (object[3] != null)
								one.put("shareWithEmpMob", object[3].toString());
							else
								one.put("shareWithEmpMob", "NA");

							if (object[4] != null)
								one.put("shareWithEmpEmail", object[4].toString());
							else
								one.put("shareWithEmpEmail", "NA");

							if (object[5] != null)
								one.put("shareWithDesignation", object[5].toString());
							else
								one.put("shareWithDesignation", "NA");

							if (object[6] != null)
								one.put("shareGroupName", object[6].toString());
							else
								one.put("shareGroupName", "NA");

							Listhb.add(one);
						}
						setMasterViewList1(Listhb);
						returnResult = SUCCESS;
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getContactLevel of class " + getClass(), e);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getDeptBySubGroup()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				deptMap = new JSONArray();
				JSONObject ob;
				StringBuilder query = new StringBuilder();
				query.append("SELECT dept.id,dept.deptName FROM department AS dept ");
				query.append(" INNER JOIN employee_basic AS emp ON emp.deptname=dept.id ");
				query.append(" WHERE emp.groupId=" + selectedId + " GROUP BY dept.deptName ");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob = new JSONObject();
						Object[] object = (Object[]) iterator.next();
						ob.put("id", object[0].toString());
						ob.put("name", object[1].toString());
						deptMap.add(ob);
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String fetchLevelValue()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				levelValue = new JSONArray();
				JSONObject ob;
				StringBuilder query = new StringBuilder();
				query.append("SELECT level  FROM compliance_contacts AS ctm ");
				query.append(" INNER JOIN department AS dept ON ctm.forDept_id=dept.id ");
				query.append(" WHERE dept.id= ");
				query.append(selectedId);
				query.append(" GROUP BY level");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob = new JSONObject();
						Object object = (Object) iterator.next();
						ob.put("id", object.toString());
						ob.put("name", object.toString());
						levelValue.add(ob);
					}
				}
				StringBuilder xyz = new StringBuilder();
				xyz.append("WHERE dept.id= ");
				xyz.append(selectedId);
				xyz.append(" GROUP BY level");

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public JSONArray getDeptMap()
	{
		return deptMap;
	}

	public void setDeptMap(JSONArray deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<Integer, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<Integer, String> empMap)
	{
		this.empMap = empMap;
	}

	public Map<Integer, String> getEmployeeList()
	{
		return employeeList;
	}

	public void setEmployeeList(Map<Integer, String> employeeList)
	{
		this.employeeList = employeeList;
	}

	public String getFromDept()
	{
		return fromDept;
	}

	public void setFromDept(String fromDept)
	{
		this.fromDept = fromDept;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public String getUserDeptId()
	{
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId)
	{
		this.userDeptId = userDeptId;
	}

	public String getUserDeptName()
	{
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName)
	{
		this.userDeptName = userDeptName;
	}

	public String getForDeptId()
	{
		return forDeptId;
	}

	public void setForDeptId(String forDeptId)
	{
		this.forDeptId = forDeptId;
	}

	public String getForDept()
	{
		return forDept;
	}

	public void setForDept(String forDept)
	{
		this.forDept = forDept;
	}

	public Map<String, String> getLevel()
	{
		return level;
	}

	public void setLevel(Map<String, String> level)
	{
		this.level = level;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getMappedLevel()
	{
		return mappedLevel;
	}

	public void setMappedLevel(String mappedLevel)
	{
		this.mappedLevel = mappedLevel;
	}

	public JSONArray getJsonObjArray()
	{
		return jsonObjArray;
	}

	public void setJsonObjArray(JSONArray jsonObjArray)
	{
		this.jsonObjArray = jsonObjArray;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getLevelName()
	{
		return levelName;
	}

	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}

	public List<Object> getMasterViewList1()
	{
		return masterViewList1;
	}

	public void setMasterViewList1(List<Object> masterViewList1)
	{
		this.masterViewList1 = masterViewList1;
	}

	public List<ConfigurationUtilBean> getComplDDBox()
	{
		return complDDBox;
	}

	public void setComplDDBox(List<ConfigurationUtilBean> complDDBox)
	{
		this.complDDBox = complDDBox;
	}

	public List<ConfigurationUtilBean> getComplTxtBox()
	{
		return complTxtBox;
	}

	public void setComplTxtBox(List<ConfigurationUtilBean> complTxtBox)
	{
		this.complTxtBox = complTxtBox;
	}

	public Map<Integer, String> getSubDeptMap()
	{
		return subDeptMap;
	}

	public void setSubDeptMap(Map<Integer, String> subDeptMap)
	{
		this.subDeptMap = subDeptMap;
	}

	public String getGroupField()
	{
		return groupField;
	}

	public void setGroupField(String groupField)
	{
		this.groupField = groupField;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public Map<String, String> getGroupMap()
	{
		return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap)
	{
		this.groupMap = groupMap;
	}

	public Map<String, String> getSubTypeMap()
	{
		return subTypeMap;
	}

	public void setSubTypeMap(Map<String, String> subTypeMap)
	{
		this.subTypeMap = subTypeMap;
	}

	public String getSubType()
	{
		return subType;
	}

	public void setSubType(String subType)
	{
		this.subType = subType;
	}

	public Map<String, String> getLevelMap()
	{
		return levelMap;
	}

	public void setLevelMap(Map<String, String> levelMap)
	{
		this.levelMap = levelMap;
	}

	public String getLeveldata()
	{
		return leveldata;
	}

	public void setLeveldata(String leveldata)
	{
		this.leveldata = leveldata;
	}

	public void setLevelValue(JSONArray levelValue)
	{
		this.levelValue = levelValue;
	}

	public JSONArray getLevelValue()
	{
		return levelValue;
	}

	public String getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(String totalCount)
	{
		this.totalCount = totalCount;
	}

	public Map<String, String> getModuleList()
	{
		return moduleList;
	}

	public void setModuleList(Map<String, String> moduleList)
	{
		this.moduleList = moduleList;
	}

	public List<Object> getLocationList()
	{
		return locationList;
	}

	public void setLocationList(List<Object> locationList)
	{
		this.locationList = locationList;
	}

	public String getForSubDept_id()
	{
		return forSubDept_id;
	}

	public void setForSubDept_id(String forSubDept_id)
	{
		this.forSubDept_id = forSubDept_id;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public Map<String, String> getSubDepartmentMap()
	{
		return subDepartmentMap;
	}

	public void setSubDepartmentMap(Map<String, String> subDepartmentMap)
	{
		this.subDepartmentMap = subDepartmentMap;
	}

	public Map<String, String> getLocationMap()
	{
		return locationMap;
	}

	public void setLocationMap(Map<String, String> locationMap)
	{
		this.locationMap = locationMap;
	}

}