package com.Over2Cloud.ctrl.text2mail;

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


import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;



import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.leaveManagement.LeaveHelper;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRgerneration;

import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ConfigKeyword extends ActionSupport implements ServletRequestAware
{
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	//static final //log //log = LogFactory.getLog(ClientCtrlAction.class);
	private HttpServletRequest request;

	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
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
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private String formater;
	private String header;
	private String mainKeyword;
	private List<ConfigurationUtilBean> configKeyTextBox = null;
	private List<ConfigurationUtilBean> configKeyDropDown = null;
	private List<ConfigurationUtilBean> configKeyCheckBox = null;
	private List<ConfigurationUtilBean> configKeyTextArea = null;

	private List<ConfigurationUtilBean> configdescTextBox = null;
	private String groupExist;
	private String group;
	private String ackBySMSExist;
	private String ackBySMS;
	private String CheckBoxExist;
	private String CheckBox;
	private String keyword;
	private String autoReplyMsg;
	private String mailIds;
	private String emailID;
	private String password;
	private String server;
	private String port;
	private String emailIdOne;
	private String empName;
	private String description;
	private String module;
	private String mapEmp1;
	private String mailIdTest;
	private Map<String, String> mainModuleMasterMap = null;
	private JSONArray commonJSONArray = new JSONArray();
	Map<String, String> subKeyMap = null;
	private Map<String, String> moduleMap;
	private Map<String, String> allDeptName = null;
	private Map<String, String> d1 = null;
	private String mainKeywordid;
	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch (Exception e)
		{
			//log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: execute of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	

	
	public String subKeywordDataDD()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("jjjj "+mainKeywordid);

				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder queryCountry = new StringBuilder("");
				subKeyMap = new LinkedHashMap<String, String>();
				List<Object> Listhb1 = new ArrayList<Object>();
				List<Object> Listhb2 = new ArrayList<Object>();
				queryCountry.append("select id, secKeyword from t2msecondarykeyword where flag='Active' and  mainKeyword='" + mainKeywordid + "'");
				List dataCntry = cbt.executeAllSelectQuery(queryCountry.toString(), connectionSpace);
				if (dataCntry != null && dataCntry.size() > 0)
				{
					for (Iterator iterator = dataCntry.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						subKeyMap.put("id", object[1].toString());
						subKeyMap.put("name", object[1].toString());
						commonJSONArray.add(subKeyMap);
						System.out.println(commonJSONArray);
					}
				}
				returnResult = SUCCESS;

			} catch (Exception exp)
			{
				//log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;}
	public String beforeConfigKeyword()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageDataForClient();

			setModuleMap(new LinkedHashMap<String, String>());
			setModuleMap(CommonWork.fetchAppAssignedUser(connectionSpace, userName));
			//System.out.println("module name :: " + moduleMap);

			// for department name
			allDeptName1 = new LinkedHashMap<String, String>();
			String query = "SELECT id,deptName FROM department WHERE flag=0 ORDER BY deptName";
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			//System.out.println("dept list is : " + dataList);
			if (dataList != null && dataList.size() > 0)
			{
				//System.out.println("dept name " + allDeptName1);
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					//System.out.println("dept name1 " + allDeptName1);
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						//System.out.println("dept name2 " + allDeptName1);
						//System.out.println("hsakh+   " + object[0].toString() + "    and     " + object[1].toString());
						allDeptName1.put(object[0].toString(), object[1].toString());
						//System.out.println("dept name3 " + allDeptName1);
						// d1.put(object[0].toString(), object[1].toString());
					}
				}
			}
			
			mainModuleMasterMap = new LinkedHashMap<String, String>();
				List mainModuleMasterData = cbt.executeAllSelectQuery("select id, " + "mainKeyword from t2mmainkeyword where flag='Active' order by mainKeyword ",connectionSpace);

				if (mainModuleMasterData != null) {
					for (Object c : mainModuleMasterData) {
						Object[] dataC = (Object[]) c;
						mainModuleMasterMap.put(dataC[1].toString(), dataC[1]
								.toString());
					}
				}
			

		}
		catch (Exception e)
		{
			//log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// for multiselect department
	private Map<String, String> allDeptName1 = null;
	private String deptID;
	private Map<String, String> empAll = null;

	public String multi()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			

			setModuleMap(new LinkedHashMap<String, String>());
			setModuleMap(CommonWork.fetchAppAssignedUser(connectionSpace, userName));
			
			empAll = new LinkedHashMap<String, String>();
			StringBuilder query = new StringBuilder("");
			query.append("SELECT ctm.id,emp.emailIdOne,emp.empName,emp.deptname FROM compliance_contacts AS ctm ");
			query.append(" INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id");
			/*query.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId");
			query.append(" INNER JOIN subdepartment AS subdept ON ctm.forDept_id=subdept.id ");
			query.append(" INNER JOIN department AS dept ON subdept.deptid=dept.id");*/
			query.append(" WHERE   emp.deptname IN ( ");

			String[] elements = deptID.split(",");
			if (elements.length <= 1)
			{
				for (String element : elements)
				{
					String trimmedElement = element.trim();
					// do something with trimmedElement

					query.append("'" + trimmedElement + "'");
				}

			}
			else
			{
				for (int i = 0; i < elements.length - 1; i++)
				{
					query.append("'" + elements[i] + "',");
				}
				query.append("'" + elements[elements.length - 1] + "'");
			}

			// String str="string,with,comma";
			/*
			 * ArrayList aList= new ArrayList(Arrays.asList(deptID.split(",")));
			 * for(int i=0;i<aList.size();i++) {
			 * //System.out.println(" -->"+aList.get(i)); }
			 */
			query.append(" ) ORDER BY emp.empName");
			//System.out.println("jkhdfkjsdhfkjsdjk >>>>>>>  "+query);

			List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						String s = object[2].toString();
						String s1 = object[1].toString();
						String s3 = s + " / " + s1;
						empAll.put(object[0].toString(), s3);
						// d1.put(object[0].toString(), object[1].toString());
					}
				}
			}

			// /////////////////////

		}
		catch (Exception e)
		{
			//log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String deptid;

	public String multiEmp()
	{
		//System.out.println(" dept id are " + getDeptid());
		return SUCCESS;
	}

	public void setAddPageDataForClient()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_configkeyword_configuration");
			//System.out.println("empuery    >>>>>>>>>>>>>>  " + empuery);
			//System.out.println("empuery  " + empuery);
			
			
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("configkeyword_configuration"))
					{

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_configkeyword_configuration", accountID, connectionSpace, "", 0, "table_name", "configkeyword_configuration");
						//System.out.println("offeringLevel1  " + offeringLevel1.size());
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						//System.out.println("configKeyTextBox  " + configKeyTextBox.size());
						configKeyDropDown = new ArrayList<ConfigurationUtilBean>();
						configKeyCheckBox = new ArrayList<ConfigurationUtilBean>();
						configKeyTextArea = new ArrayList<ConfigurationUtilBean>();
						configdescTextBox = new ArrayList<ConfigurationUtilBean>();
						//System.out.println("configdescTextBox  " + configdescTextBox.size());
						for (GridDataPropertyView gdp : offeringLevel1)
						{
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T")

							&& !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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

								configKeyTextBox.add(obj);
								//System.out.println("configKeyTextBoxcccccc  " + configKeyTextBox.size());

							}

							if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("description")&&!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
								
								configKeyDropDown.add(obj);

							}
							if (gdp.getColType().trim().equalsIgnoreCase("TA") && !gdp.getColomnName().equalsIgnoreCase("description")&&!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
								configKeyTextArea.add(obj);

							}

							if (gdp.getColType().trim().equalsIgnoreCase("C") && !gdp.getColomnName().equalsIgnoreCase("description")&&!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
								if (gdp.getColomnName().equalsIgnoreCase("ackBySMS"))
								{
									CheckBox = gdp.getHeadingName();
									if (gdp.getMandatroy().equalsIgnoreCase("1"))
										CheckBoxExist = "true";
									else
										CheckBoxExist = "false";
								}
								configKeyCheckBox.add(obj);

							}

						}

					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public String afterConfigKeyword()
	{

		String returnType = ERROR;
		boolean status = false;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			Mailtest mt = new Mailtest();
			// create table for lead
			new T2MActionControlDao().createKeywordTable(accountID, connectionSpace);

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;


			//System.out.println("asfisdifngdskngkvd  "+request.getParameter("mailIdTest"));
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			if (requestParameterNames != null && requestParameterNames.size() > 0)
			{
				Iterator it = requestParameterNames.iterator();

				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue = request.getParameter(parmName);
					//System.out.println("param name ==============: " + parmName);
					//System.out.println("paramValue================== : " + paramValue);
					if (paramValue.equalsIgnoreCase("mailIds"))
					{
						String s = request.getParameter("mailIds");
						//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.asdghsgdkfjsdfh" + s);

					}
				}
			}
			String validate[] = getMailIdTest().trim().split(",");
			//System.out.println("validate   >>>>>>>>>>>>>   " +validate);
			String validateData=null;
			
			
			InsertDataTable ob4 = null;
			ob4 = new InsertDataTable();
			ob4.setColName("keyword");
			ob4.setDataName(keyword);
			//System.out.println("ob4.setDataName(keyword); " + ob4.getDataName());
			insertHistory.add(ob4);

			
			String s1 = null;
			ob4 = new InsertDataTable();
			ob4.setColName("mailIds");
			int l=0;
			for (int i = 0; i < validate.length; i++) 
			{
			

				   if(i==0)
				   {
					   validateData = getEmployeeMail(validate[i], connectionSpace);
				   }
				   else{
					   validateData = validateData +", "+ getEmployeeMail(validate[i],  connectionSpace);
					   }
					
				
				
				
				//System.out.println("validate   >>>>>>>>>>>>>   " +validate[i].toString());
				ob4.setDataName(validateData);
				//System.out.println("validate ttt55666  >>>>>>>>>>>>>   " +validateData);
			
				
			}
			//System.out.println("ob4.setDataName     " +ob4.getDataName()) ;
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("autoReplyMsg");
			if (ackBySMS.equalsIgnoreCase("true"))
			{
			ob4.setDataName(autoReplyMsg);
			}
			else
			{
				ob4.setDataName("This keyword is not mapped for Auto Ack message... ");
			}
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("ackBySMS");
			if (ackBySMS.equalsIgnoreCase("true"))
			{
				ob4.setDataName("Yes");
			}
			else
			{
				ob4.setDataName("No");
			}
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("description");
			ob4.setDataName("NA");
			//System.out.println("ndjanfafd " + ob4.getDataName());
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("moduleName");
			ob4.setDataName(getModule());
			//System.out.println(" getModule() " + getModule());
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("mainKeyword");
			ob4.setDataName(getMainKeyword());
			System.out.println(" getMainKeyword " + getMainKeyword());
			insertHistory.add(ob4);


			 
			List dataList = cbt.executeAllSelectQuery("select id from configurekeyword where keyword = '" + keyword + "'", connectionSpace);
			//System.out.println("dataList >>>>>>> " + dataList.size());
			if (dataList.size() > 0)
			{
				addActionMessage("Duplicate: Keyword already exists ! And also used by "+getModule()+" Module");
				returnType = ERROR;
			}
			else
			{
				status = cbt.insertIntoTable("configurekeyword", insertHistory, connectionSpace);
				if (status)
				{
					addActionMessage("Keyword Configured Successfully!!!");
					// boolean
					// mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),mailIds,"Configure Keyword",autoReplyMsg);
					returnType = SUCCESS;
				}
				else
				{
					addActionMessage("ERROR: There is some error !");
					returnType = ERROR;
				}
			}
			/*
			 * if (status) {
			 * addActionMessage("Keyword Configured Successfully!!!"); boolean
			 * mailFlag
			 * =mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword
			 * (),mailIds,"Configure Keyword",autoReplyMsg); return SUCCESS; }
			 * else { addActionError("Oops There is some error in data!");
			 * return ERROR; }
			 */
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}


	public String getEmployeeMail(String uniqueId, SessionFactory connection)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		String data = null;
		List dataList = null;
		try
		{
			qryString.setLength(0);
			qryString.append("select emailIdOne from employee_basic AS emp ");
			qryString.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id ");
			qryString.append(" WHERE cc.id='" + uniqueId + "' ");
			//System.out.println("qryString" + qryString);
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();

					data = getValueWithNullCheck(object);

				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return data;

	}
	
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}
	
	public String beforeViewConfigKeyword()
	{
		//System.out.println("beforeViewConfigKeyword");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return LOGIN;
			}

		}
		else
		{
			return LOGIN;
		}
	}

	public void setViewProp()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);

				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_configkeyword_configuration", accountID, connectionSpace, "", 0, "table_name", "configkeyword_configuration");
				//System.out.println("returnResult   " + returnResult.size());
				for (GridDataPropertyView gdp : returnResult)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());

					masterViewProp.add(gpv);
				}

				/*
				 * gpv = new GridDataPropertyView(); gpv.setColomnName("tags");
				 * gpv.setHeadingName("Searching Tags");
				 * gpv.setEditable("true"); gpv.setSearch("true");
				 * gpv.setHideOrShow("false"); masterViewProp.add(gpv);
				 */
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{

		}
	}

	public String viewKeywordInGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from configurekeyword");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				//System.out.println("dataCount:" + dataCount);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
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

					List fieldNames = Configuration.getColomnList("mapped_configkeyword_configuration", accountID, connectionSpace, "configkeyword_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
							{
								query.append(obdata + ", ");
							}
							else
							{
								query.append(obdata);
							}
						}
						i++;
					}

					query.append(" from configurekeyword ");
					//System.out.println("query:::" + query);

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" where ");

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					// query.append(" limit " + from + "," + to);
					//System.out.println("query::::" + query);

					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null)
					{
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}

									if (fieldNames.get(k).toString().equalsIgnoreCase("time"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
									}
								}
							}

							Listhb.add(one);

						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String modifyKeyword()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("configurekeyword", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("configurekeyword", "id", condtIds.toString(), connectionSpace);
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public List<ConfigurationUtilBean> getConfigKeyTextBox()
	{
		return configKeyTextBox;
	}

	public void setConfigKeyTextBox(List<ConfigurationUtilBean> configKeyTextBox)
	{
		this.configKeyTextBox = configKeyTextBox;
	}

	public List<ConfigurationUtilBean> getConfigKeyDropDown()
	{
		return configKeyDropDown;
	}

	public void setConfigKeyDropDown(List<ConfigurationUtilBean> configKeyDropDown)
	{
		this.configKeyDropDown = configKeyDropDown;
	}

	public List<ConfigurationUtilBean> getConfigKeyCheckBox()
	{
		return configKeyCheckBox;
	}

	public void setConfigKeyCheckBox(List<ConfigurationUtilBean> configKeyCheckBox)
	{
		this.configKeyCheckBox = configKeyCheckBox;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextArea()
	{
		return configKeyTextArea;
	}

	public void setConfigKeyTextArea(List<ConfigurationUtilBean> configKeyTextArea)
	{
		this.configKeyTextArea = configKeyTextArea;
	}

	public String getGroupExist()
	{
		return groupExist;
	}

	public void setGroupExist(String groupExist)
	{
		this.groupExist = groupExist;
	}

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public String getAckBySMSExist()
	{
		return ackBySMSExist;
	}

	public void setAckBySMSExist(String ackBySMSExist)
	{
		this.ackBySMSExist = ackBySMSExist;
	}

	public String getAckBySMS()
	{
		return ackBySMS;
	}

	public void setAckBySMS(String ackBySMS)
	{
		this.ackBySMS = ackBySMS;
	}

	public String getCheckBoxExist()
	{
		return CheckBoxExist;
	}

	public void setCheckBoxExist(String checkBoxExist)
	{
		CheckBoxExist = checkBoxExist;
	}

	public String getCheckBox()
	{
		return CheckBox;
	}

	public void setCheckBox(String checkBox)
	{
		CheckBox = checkBox;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}
	
	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getAutoReplyMsg()
	{
		return autoReplyMsg;
	}

	public void setAutoReplyMsg(String autoReplyMsg)
	{
		this.autoReplyMsg = autoReplyMsg;
	}

	public String getMailIds()
	{
		return mailIds;
	}

	public void setMailIds(String mailIds)
	{
		this.mailIds = mailIds;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public String getModifyFlag()
	{
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag)
	{
		this.modifyFlag = modifyFlag;
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

	public String getFormater()
	{
		return formater;
	}

	public void setFormater(String formater)
	{
		this.formater = formater;
	}

	public String getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header = header;
	}

	public String getEmailID()
	{
		return emailID;
	}

	public void setEmailID(String emailID)
	{
		this.emailID = emailID;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public String getEmailIdOne()
	{
		return emailIdOne;
	}

	public void setEmailIdOne(String emailIdOne)
	{
		this.emailIdOne = emailIdOne;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}





	public Map<String, String> getSubKeyMap()
	{
		return subKeyMap;
	}

	public void setSubKeyMap(Map<String, String> subKeyMap)
	{
		this.subKeyMap = subKeyMap;
	}


	public List<ConfigurationUtilBean> getConfigdescTextBox()
	{
		return configdescTextBox;
	}

	public void setConfigdescTextBox(List<ConfigurationUtilBean> configdescTextBox)
	{
		this.configdescTextBox = configdescTextBox;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Map<String, String> getModuleMap()
	{
		return moduleMap;
	}

	public void setModuleMap(Map<String, String> moduleMap)
	{
		this.moduleMap = moduleMap;
	}

	public Map<String, String> getAllDeptName()
	{
		return allDeptName;
	}

	public void setAllDeptName(Map<String, String> allDeptName)
	{
		this.allDeptName = allDeptName;
	}

	public Map<String, String> getAllDeptName1()
	{
		return allDeptName1;
	}

	public void setAllDeptName1(Map<String, String> allDeptName1)
	{
		this.allDeptName1 = allDeptName1;
	}

	public String getDeptid()
	{
		return deptid;
	}

	public void setDeptid(String deptid)
	{
		this.deptid = deptid;
	}

	public String getDeptID()
	{
		return deptID;
	}

	public void setDeptID(String deptID)
	{
		this.deptID = deptID;
	}

	public Map<String, String> getEmpAll()
	{
		return empAll;
	}

	public void setEmpAll(Map<String, String> empAll)
	{
		this.empAll = empAll;
	}

	public String getMapEmp1()
	{
		return mapEmp1;
	}

	public void setMapEmp1(String mapEmp1)
	{
		this.mapEmp1 = mapEmp1;
	}

	public String getMailIdTest()
	{
		return mailIdTest;
	}

	public void setMailIdTest(String mailIdTest)
	{
		this.mailIdTest = mailIdTest;
	}

	private String mapEmp;

	public String getMapEmp()
	{
		return mapEmp;
	}

	public void setMapEmp(String mapEmp)
	{
		this.mapEmp = mapEmp;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public Map<String, String> getMainModuleMasterMap() {
		return mainModuleMasterMap;
	}

	public void setMainModuleMasterMap(Map<String, String> mainModuleMasterMap) {
		this.mainModuleMasterMap = mainModuleMasterMap;
	}

	public String getMainKeyword() {
		return mainKeyword;
	}

	public void setMainKeyword(String mainKeyword) {
		this.mainKeyword = mainKeyword;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}




	public String getMainKeywordid()
	{
		return mainKeywordid;
	}




	public void setMainKeywordid(String mainKeywordid)
	{
		this.mainKeywordid = mainKeywordid;
	}

	


}
