package com.Over2Cloud.ctrl.text2mail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;


//import org.apache.axis2.description.java2wsdl.bytecode.ParamNameExtractor;

import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.tiles.jsp.taglib.definition.SetCurrentContainerTag;
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
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRgerneration;

import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Consultants extends ActionSupport implements ServletRequestAware
{
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest request;
	private String idcons;
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
	private List<ConfigurationUtilBean> configKeyTextBox = null;
	private List<ConfigurationUtilBean> configKeyDropDown = null;
	private List<ConfigurationUtilBean> configKeyCheckBox = null;
	private List<ConfigurationUtilBean> configKeyTextArea = null;
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
	private String speciality;
	private String specialityExist;
	private String location;
	private String locationExist;
	private LinkedHashMap<String, String> specialityMasterMap;
	private LinkedHashMap<String, String> locationMasterMap;
	private String mobileNo;
	private String emailId;
	String id1, ename, mob;
	String deptid1, groupId;
	private String moduleName;
	private String consW, mobW;
	private int count;
	private int countC;
	private String cname, cmob;
	Map<String, Object> detailsCons;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		} catch (Exception e)
		{
			//log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: execute of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeConsultants()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageDataForClient();

		} catch (Exception e)
		{
			//log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForClient()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_consultants_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("consultants_configuration"))
					{

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_consultants_configuration", accountID, connectionSpace, "", 0, "table_name",
								"consultants_configuration");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						configKeyDropDown = new ArrayList<ConfigurationUtilBean>();
						configKeyCheckBox = new ArrayList<ConfigurationUtilBean>();
						configKeyTextArea = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1)
						{
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								} else
								{
									obj.setMandatory(false);
								}

								configKeyTextBox.add(obj);

							}

							if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								} else
								{
									obj.setMandatory(false);
								}
								if (gdp.getColomnName().equalsIgnoreCase("speciality"))
								{
									speciality = gdp.getHeadingName();
									if (gdp.getMandatroy().equalsIgnoreCase("1"))
										specialityExist = "true";
									else
										specialityExist = "false";
								}
								if (gdp.getColomnName().equalsIgnoreCase("location"))
								{
									location = gdp.getHeadingName();
									if (gdp.getMandatroy().equalsIgnoreCase("1"))
										locationExist = "true";
									else
										locationExist = "false";
								}
								configKeyDropDown.add(obj);

							}
							if (gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());

								if (gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								} else
								{
									obj.setMandatory(false);
								}
								configKeyTextArea.add(obj);

							}
						}
						specialityMasterMap = new LinkedHashMap<String, String>();
						locationMasterMap = new LinkedHashMap<String, String>();
						if (specialityExist != null)
						{
							List specialityMasterData = cbt.executeAllSelectQuery("select id, " + "speciality from speciality order by speciality", connectionSpace);

							if (specialityMasterData != null)
							{
								for (Object c : specialityMasterData)
								{
									Object[] dataC = (Object[]) c;
									specialityMasterMap.put(dataC[0].toString(), dataC[1].toString());
								}
							}
						}
						if (locationExist != null)
						{
							List locationMasterData = cbt.executeAllSelectQuery("select id, " + "location from location_t2m order by location", connectionSpace);
							if (locationMasterData != null)
							{
								for (Object c : locationMasterData)
								{
									Object[] dataC = (Object[]) c;
									locationMasterMap.put(dataC[0].toString(), dataC[1].toString());
								}
							}
						}

					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

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

			StringBuilder queryForEmail = new StringBuilder("");
			queryForEmail.append("select*from email_configuration_data");
			List dataCountForEmail = cbt.executeAllSelectQuery(queryForEmail.toString(), connectionSpace);
			if (dataCountForEmail != null && dataCountForEmail.size() > 0)
			{
				// emailID=(String) dataCount.get(0);
				for (Iterator it = dataCountForEmail.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					emailID = (String) obdata[3];
					password = (String) obdata[4];
					server = (String) obdata[1];
					port = (String) obdata[2];
				}

			}

			InsertDataTable ob4 = null;
			ob4 = new InsertDataTable();
			ob4.setColName("keyword");
			ob4.setDataName(keyword);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("mailIds");
			ob4.setDataName(mailIds);
			insertHistory.add(ob4);
			ob4 = new InsertDataTable();
			ob4.setColName("autoReplyMsg");
			ob4.setDataName(autoReplyMsg);
			insertHistory.add(ob4);
			ob4 = new InsertDataTable();
			ob4.setColName("contact_group");
			ob4.setDataName(group);
			insertHistory.add(ob4);
			ob4 = new InsertDataTable();
			ob4.setColName("ackBySMS");
			if (ackBySMS.equalsIgnoreCase("true"))
			{
				ob4.setDataName("Yes");
			} else
			{
				ob4.setDataName("No");
			}
			insertHistory.add(ob4);

			/*
			 * boolean
			 * mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(
			 * ),getPassword
			 * (),object[2].toString(),"K.R Add Notification",mailtext
			 * .toString()); mailtext.delete(0, mailtext.length()); if(mailFlag)
			 * { //System.out.println(
			 * "**************Mail Sent Successfully**********************"); }
			 */

			if (ackBySMS.equalsIgnoreCase("true"))
			{
				String SMSClient = autoReplyMsg;
				// System.out.println("SMSClient"+SMSClient);
				// SMS On DS mobile NO
				// new SendHttpSMS().sendSMS(object[1].toString(), SMSClient);

			}

			// status = cbt.insertIntoTable("configurekeyword",
			// insertHistory,connectionSpace);

			// System.out.println("status>>>>>>>>>>>>>>>>  "+status);
			List dataList = cbt.executeAllSelectQuery("select id from configurekeyword where keyword = '" + keyword + "'", connectionSpace);
			if (dataList.size() > 0)
			{
				addActionMessage("Duplicate: Keyword already exists !");
				returnType = ERROR;
			} else
			{
				status = cbt.insertIntoTable("configurekeyword", insertHistory, connectionSpace);
				if (status)
				{
					addActionMessage("Keyword Configured Successfully!!!");
					boolean mailFlag = mt.confMailHTML(getServer(), getPort(), getEmailID(), getPassword(), mailIds, "Configure Keyword", autoReplyMsg);
					returnType = SUCCESS;
				} else
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
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String beforeConsultantsView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// setViewProp();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("SELECT count(*) FROM consultants;");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				// System.out.println("dataCount:" + dataCount);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
						setCount(count.intValue());
					}
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				return LOGIN;
			}

		} else
		{
			return LOGIN;
		}
		return SUCCESS;
	}

	public String consultantCount()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// setViewProp();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("SELECT count(*) FROM consultants");
				if (cname.length() != 0 && cname != null)
				{
					query1.append(" where empName like '" + cname + "%'");
				}

				if (cmob.length() != 0 && cmob != null)
				{
					query1.append(" where mobOne like '" + cmob + "%'");
				}

				System.out.println("query1===  " + query1);
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				// System.out.println("dataCount:" + dataCount);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
						setCountC(count.intValue());
						System.out.println("counter " + getCountC());
					}
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				return LOGIN;
			}

		} else
		{
			return LOGIN;
		}
		return SUCCESS;

	}

	public String consultantsView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				System.out.println("conW=" + consW);
				System.out.println("mobW " + mobW);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return LOGIN;
			}

		} else
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
				
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("empName");
				gpv.setHeadingName("Name");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("speciality");
				gpv.setHeadingName("Speciality");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("comName");
				gpv.setHeadingName("Comm. Name");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);

				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("pancard");
				gpv.setHeadingName("Pancard");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("address");
				gpv.setHeadingName("Address");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("location");
				gpv.setHeadingName("Location");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("mobOne");
				gpv.setHeadingName("Mobile No.");
				gpv.setEditable("false");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("emailIdOne");
				gpv.setHeadingName("Email Id");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("flag");
				gpv.setHeadingName("Status");
				gpv.setEditable("true");
				gpv.setSearch("true");
				//gpv.setFormatter("details");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
			
				/*
				 * gpv = new GridDataPropertyView(); gpv.setColomnName("name");
				 * gpv.setHeadingName("Spesiality"); gpv.setEditable("false");
				 * gpv.setSearch("false"); gpv.setSearch(gdp.getFormatter());
				 * gpv.setHideOrShow(gdp.getHideOrShow());
				 * System.out.println("Formater>>>>>>>>>>>>>>>>>>>> " +
				 * gdp.getColomnName()); //details masterViewProp.add(gpv);
				 */

				/*
				 * gpv = new GridDataPropertyView();
				 * gpv.setColomnName(gdp.getColomnName());
				 * gpv.setHeadingName(gdp.getHeadingName());
				 * gpv.setEditable(gdp.getEditable());
				 * gpv.setSearch(gdp.getSearch());
				 * //gpv.setSearch(gdp.getFormatter());
				 * gpv.setHideOrShow(gdp.getHideOrShow());
				 * masterViewProp.add(gpv);
				 */

				/*List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_consultants_configuration", accountID, connectionSpace, "", 0, "table_name",
						"consultants_configuration");
				for (GridDataPropertyView gdp : returnResult)
				{

					if (!gdp.getColomnName().equalsIgnoreCase("anniversary") && !gdp.getColomnName().equalsIgnoreCase("birthday") && !gdp.getColomnName().equalsIgnoreCase("mobTwo")
							&& !gdp.getColomnName().equalsIgnoreCase("emailIdTwo") && !gdp.getColomnName().equalsIgnoreCase("mobileNo3") && !gdp.getColomnName().equalsIgnoreCase("emailId3")
							&& !gdp.getColomnName().equalsIgnoreCase("mobileNo4") && !gdp.getColomnName().equalsIgnoreCase("emailId4") && !gdp.getColomnName().equalsIgnoreCase("mobileNo5")
							&& !gdp.getColomnName().equalsIgnoreCase("emailId5"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setHideOrShow(gdp.getHideOrShow());
						System.out.println("Formater>>>>>>>>>>>>>>>>>>>> " + gdp.getColomnName());
						// details
						masterViewProp.add(gpv);

					}
					
					 * gpv = new GridDataPropertyView();
					 * gpv.setColomnName("comName");
					 * gpv.setHeadingName("Comm Name");
					 * gpv.setHideOrShow("false"); gpv.setFormatter("details");
					 * gpv.setWidth(50);
					 
				}*/

				/*
				 * gpv = new GridDataPropertyView(); gpv.setColomnName("tags");
				 * gpv.setHeadingName("Searching Tags");
				 * gpv.setEditable("true"); gpv.setSearch("true");
				 * gpv.setHideOrShow("false"); masterViewProp.add(gpv);
				 */
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		} else
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
				// System.out.println("dataCount:" + dataCount);
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

					List fieldNames = Configuration.getColomnList("mapped_configKeyword_configuration", accountID, connectionSpace, "configKeyword_configuration");
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
							} else
							{
								query.append(obdata);
							}
						}
						i++;
					}

					query.append(" from configurekeyword ");
					// System.out.println("query:::" + query);

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" where ");

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
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

					query.append(" limit " + from + "," + to);
					// System.out.println("query::::" + query);

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
									} else
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
			} catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		} else
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
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("configurekeyword", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del"))
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

	public String addConsultants()
	{

		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// create table for lead
			new T2MActionControlDao().createConsultantsTable(accountID, connectionSpace);

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			// /
			List<InsertDataTable> insertEmpBasicData = new ArrayList<InsertDataTable>();
			InsertDataTable empbasicObj = null;

			// //

			String query0 = "select id, groupId from department where deptName = 'Consultants'";
			List data3 = cbt.executeAllSelectQuery(query0, connectionSpace);
			String deptid1, groupId;
			if (data3 != null && data3.size() > 0)
			{
				JSONObject formDetailsJson = new JSONObject();
				Object[] object = null;
				for (Iterator iterator = data3.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						setDeptid1(object[0].toString());
						setGroupId(object[1].toString());

					}
				}
			}

			boolean status = false;
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = null;
				if (Parmname.equalsIgnoreCase("emailId") || Parmname.equalsIgnoreCase("mobileNo"))
					paramValue = getString(request.getParameterValues(Parmname));
				else
					paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
				{
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					if (paramValue.equalsIgnoreCase("-1"))
					{
						paramValue = "NA";
					}
					ob.setDataName(paramValue);
					insertData.add(ob);
				}

				// ///manab work
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && !Parmname.equalsIgnoreCase("mobileNo")
						&& !Parmname.equalsIgnoreCase("speciality") && !Parmname.equalsIgnoreCase("location") && !Parmname.equalsIgnoreCase("mobileNo3") && !Parmname.equalsIgnoreCase("pancard"))
				{

					empbasicObj = new InsertDataTable();
					empbasicObj.setColName(Parmname);
					empbasicObj.setDataName(paramValue);
					insertEmpBasicData.add(empbasicObj);
					// System.out.println("Parmname >>>>>   " +Parmname);
					if (Parmname.equalsIgnoreCase("anniversary"))
					{
						paramValue = request.getParameter("anniversary");
						// System.out.println("kkkkkkc    "+paramValue);

					}
				}
			}
			empbasicObj = new InsertDataTable();
			empbasicObj.setColName("groupId");
			empbasicObj.setDataName(getGroupId());
			insertEmpBasicData.add(empbasicObj);

			empbasicObj = new InsertDataTable();
			empbasicObj.setColName("deptName");
			empbasicObj.setDataName(getDeptid1());
			insertEmpBasicData.add(empbasicObj);

			empbasicObj = new InsertDataTable();
			empbasicObj.setColName("regLevel");
			empbasicObj.setDataName("1");
			insertEmpBasicData.add(empbasicObj);

			cbt.insertIntoTable("employee_basic", insertEmpBasicData, connectionSpace);

			// for save 3rd mobile number

			List<String> requestParameterNames1 = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames1);
			Iterator it1 = requestParameterNames1.iterator();
			String paramValue3 = null;
			while (it1.hasNext())
			{
				String Parmname1 = it1.next().toString();
				String paramValue1 = null;
				paramValue1 = request.getParameter(Parmname1);
				if (paramValue1 != null && !paramValue1.equalsIgnoreCase("") && paramValue1.equalsIgnoreCase("mobTwo"))
				{

					paramValue3 = request.getParameter("mobileNo3");
					String paramValue4 = request.getParameter("mobileNo4");
					String paramValue5 = request.getParameter("mobileNo5");
					String paramValue2 = request.getParameter("empName");
					String query1 = "SELECT id,empName,mobTwo FROM employee_basic WHERE  empName='" + paramValue2 + "' AND groupId='" + getGroupId() + "' AND deptname='" + getDeptid1() + "'";
					List data2 = cbt.executeAllSelectQuery(query1, connectionSpace);

					if (data2 != null && data2.size() > 0)
					{
						JSONObject formDetailsJson = new JSONObject();
						Object[] object = null;
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
								setId1(object[0].toString());
								setEname(object[1].toString());
								setMob(object[2].toString());
							}
						}
					}
				}
			}

			// end manab work

			List<InsertDataTable> insertData2 = new ArrayList<InsertDataTable>();
			InsertDataTable ob11 = null;

			String t = mob + "," + paramValue3;

			ob11 = new InsertDataTable();
			ob11.setColName("mobTwo");
			ob11.setDataName(t);
			insertData2.add(ob11);

			cbt.updateIntoTable("employee_basic", insertData2, id1, connectionSpace);

			status = cbt.insertIntoTable("consultants", insertData, connectionSpace);
			if (status)
			{
				addActionMessage("Consultants Registered Successfully!!!");
				return SUCCESS;
			} else
			{
				addActionError("Oops There is some error in data!");
				return ERROR;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String getString(String[] val)
	{
		StringBuilder sb = new StringBuilder();
		for (String s : val)
		{
			if (s != null && !s.trim().equals(""))
			{
				sb.append(s);
				sb.append(",");
			}
		}
		return sb.toString().substring(0, sb.toString().lastIndexOf(","));
	}

	public String viewConsInGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from consultants");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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

					List fieldNames = Configuration.getColomnList("mapped_consultants_configuration", accountID, connectionSpace, "consultants_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{

						// generating the dynamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
							{

								if (obdata.toString().equalsIgnoreCase("speciality"))
								{
									// System.out.println("obdata.toString()"
									// +obdata.toString());
									query.append("sp.speciality,");
									// System.out.println("query speciality  "
									// +query);
								}

								else if (obdata.toString().equalsIgnoreCase("location"))
								{

									query.append("lc.location,");
									// System.out.println("query LOCTION  "
									// +query);
								} else
								{
									query.append("cn." + obdata.toString() + ",");
									// //System.out.println("query LOCTION ELSE "
									// +query);
								}
							} else
							{

								query.append("cn." + obdata.toString());
								// System.out.println("query LOCTION ELSEssssssssssss "
								// +query);
							}

						}
						i++;
					}

					query.append(" from consultants AS cn  ");
					query.append(" LEFT JOIN speciality AS sp ON sp.id=cn.speciality  ");
					query.append(" LEFT JOIN location_t2m AS lc ON lc.id=cn.location ");

					System.out.println("query1:::::::" + query);
					if (consW != null && consW.length() > 1)
					{
						query.append(" where empName like  '" + consW + "%'");
					}

					if (mobW != null && mobW.length() > 1)
					{
						query.append(" where mobOne like  '" + mobW + "%'");
					}
					// System.out.println("query:vcv::" + query);

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" cn." + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" cn. " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" cn. " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" cn. " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" cn. " + getSearchField() + " like '%" + getSearchString() + "'");
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
					System.out.println("query2::::" + query);

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
									System.out.println(obdata[k].toString());
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else
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
									if (fieldNames.get(k).toString().equalsIgnoreCase("location"))
									{
										// System.out.println(" name : "+fieldNames.get(k).toString()+"  data  : "+obdata[k].toString());
										String query2 = "select location, brief from location_t2m where id = '" + obdata[k] + "'";
										List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
										if (data1 != null)
										{
											for (Iterator it1 = data1.iterator(); it1.hasNext();)
											{
												Object[] obdata1 = (Object[]) it1.next();
												// System.out.println("data == "+obdata1[0]);
												one.put(fieldNames.get(k).toString(), obdata1[1].toString());
											}
										}
									}

									if (fieldNames.get(k).toString().equalsIgnoreCase("speciality"))
									{
										System.out.println("kshgkhskghkj >>>>>  " + obdata[k].toString().length());
										if (obdata[k].toString().length() == 0)
										{
											// System.out.println(" name : "+fieldNames.get(k).toString()+"  data  : "+obdata[k].toString());

											one.put(fieldNames.get(k).toString(), "NA");

										}
									}

								}
							}
							one.put("speciality", "NA");
							Listhb.add(one);

						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}

				returnValue = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		} else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String modifyCons()
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
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("consultants", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del"))
				{

					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					System.out.println("got update dele");
					StringBuilder sb = new StringBuilder();
					sb.append("update consultants set flag ='Inactive' where id='"+getId()+"'");
					cbt.updateTableColomn(connectionSpace,sb);
						System.out.println("sb "+sb);
						//Update t2mSecondarykeyword set flag='Inactive' where mainKeyword=(select mainKeyword from t2mMainkeyword where id='4')
					//cbt.updateTableColomn(connectionSpace, "fg");
				}
				return SUCCESS;
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

	public String consDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// System.out.println("idH is +++++++++++++++++++++ " + idH);
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				detailsCons = new LinkedHashMap<String, Object>();
				List data = null;
				query1.append(" select empName ,comName ,address, mobOne, anniversary, birthday, emailIdOne ,mobTwo, emailIdTwo, mobileNo3, emailId3, mobileNo4, emailId4, mobileNo5, emailId5, speciality, pancard, location from consultants where id ='"
						+ idcons + "' ");
				System.out.println(query1);
				data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();


						detailsCons.put("Name", obdata[0].toString());
						detailsCons.put("Address", obdata[2].toString());
						detailsCons.put("Communication Name", obdata[1].toString());
						detailsCons.put("Mobile 1:", obdata[3].toString());
						detailsCons.put("Mobile 2:", obdata[7].toString());
						detailsCons.put("Mobile 3:", obdata[9].toString());
						detailsCons.put("Mobile 4:", obdata[11].toString());
						detailsCons.put("Mobile 5:", obdata[13].toString());
						detailsCons.put("Email Id 1:", obdata[6].toString());
						detailsCons.put("Email Id 2:", obdata[8].toString());
						detailsCons.put("Email Id 3:", obdata[10].toString());
						detailsCons.put("Email Id 4:", obdata[12].toString());
						detailsCons.put("Email Id 5:", obdata[14].toString());
						detailsCons.put("Anniversary", obdata[4].toString());
						detailsCons.put("Birthday", obdata[5].toString());
						detailsCons.put("Pancard", obdata[16].toString());
						detailsCons.put("Speciality", "NA");

						/*String query2 = "select speciality, brief from speciality  where id = '" + obdata[15] + "'";
						System.out.println(query2);
						List data2 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
						if (data2 != null)
						{
							for (Iterator it2 = data2.iterator(); it2.hasNext();)
							{
								Object[] obdata2 = (Object[]) it2.next();
								detailsCons.put("Speciality", "NA");
							}
						}*/

						String query3 = "select location, brief from location_t2m where id = '" + obdata[17] + "'";
						System.out.println(query3);
						List data3 = cbt.executeAllSelectQuery(query3.toString(), connectionSpace);
						if (data3 != null)
						{
							for (Iterator it3 = data3.iterator(); it3.hasNext();)
							{
								Object[] obdata3 = (Object[]) it3.next();
								detailsCons.put("Location", obdata3[0].toString());
							}
						}
						// /
						System.out.println("detailsCons "+detailsCons);
					}
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
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

	public void setRequest(HttpServletRequest request)
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

	public String getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

	public String getSpecialityExist()
	{
		return specialityExist;
	}

	public void setSpecialityExist(String specialityExist)
	{
		this.specialityExist = specialityExist;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getLocationExist()
	{
		return locationExist;
	}

	public void setLocationExist(String locationExist)
	{
		this.locationExist = locationExist;
	}

	public LinkedHashMap<String, String> getSpecialityMasterMap()
	{
		return specialityMasterMap;
	}

	public void setSpecialityMasterMap(LinkedHashMap<String, String> specialityMasterMap)
	{
		this.specialityMasterMap = specialityMasterMap;
	}

	public LinkedHashMap<String, String> getLocationMasterMap()
	{
		return locationMasterMap;
	}

	public void setLocationMasterMap(LinkedHashMap<String, String> locationMasterMap)
	{
		this.locationMasterMap = locationMasterMap;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getId1()
	{
		return id1;
	}

	public void setId1(String id1)
	{
		this.id1 = id1;
	}

	public String getEname()
	{
		return ename;
	}

	public void setEname(String ename)
	{
		this.ename = ename;
	}

	public String getMob()
	{
		return mob;
	}

	public void setMob(String mob)
	{
		this.mob = mob;
	}

	public String getDeptid1()
	{
		return deptid1;
	}

	public void setDeptid1(String deptid1)
	{
		this.deptid1 = deptid1;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getConsW()
	{
		return consW;
	}

	public void setConsW(String consW)
	{
		this.consW = consW;
	}

	public String getMobW()
	{
		return mobW;
	}

	public void setMobW(String mobW)
	{
		this.mobW = mobW;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getCmob()
	{
		return cmob;
	}

	public void setCmob(String cmob)
	{
		this.cmob = cmob;
	}

	public int getCountC()
	{
		return countC;
	}

	public void setCountC(int countC)
	{
		this.countC = countC;
	}

	public String getIdcons()
	{
		return idcons;
	}

	public void setIdcons(String idcons)
	{
		this.idcons = idcons;
	}

	public Map<String, Object> getDetailsCons()
	{
		return detailsCons;
	}

	public void setDetailsCons(Map<String, Object> detailsCons)
	{
		this.detailsCons = detailsCons;
	}

}