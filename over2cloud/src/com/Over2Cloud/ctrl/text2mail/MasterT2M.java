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

import org.apache.commons.lang.WordUtils;

import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
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

public class MasterT2M extends ActionSupport implements ServletRequestAware {
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private HttpServletRequest request;
	private Map<String, String> checkMainKey;
	private Map<String, String> checkSecKey;
	private String mainKeywordChk;
	private String secKeywordChk;
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
	private List<Object> t2mViewList;
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
	private Map<String, String> mainKeywordDD;
	private String searchFields1;
	private String SearchValue1;

	public String execute() {
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		} catch (Exception e) {

		}
		return SUCCESS;
	}

	public String beforeSpeciality()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageDataForSpeciality();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForSpeciality() {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_speciality_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("speciality_configuration")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_speciality_configuration", accountID, connectionSpace, "", 0, "table_name", "speciality_configuration");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) {

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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String beforeLocation()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageDataForSpeciality2();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForSpeciality2() {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_location_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("location_configuration")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_location_configuration", accountID, connectionSpace, "", 0, "table_name", "location_configuration");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						configKeyDropDown = new ArrayList<ConfigurationUtilBean>();
						configKeyCheckBox = new ArrayList<ConfigurationUtilBean>();
						configKeyTextArea = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) {

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

							if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) {

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}
								if (gdp.getColomnName().equalsIgnoreCase("contact_group")) {
									group = gdp.getHeadingName();
									if (gdp.getMandatroy().equalsIgnoreCase("1"))
										groupExist = "true";
									else
										groupExist = "false";
								}
								configKeyDropDown.add(obj);

							}
							if (gdp.getColType().trim().equalsIgnoreCase("TA") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) {
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());

								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}
								configKeyTextArea.add(obj);

							}

							if (gdp.getColType().trim().equalsIgnoreCase("C") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) {
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());

								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}
								if (gdp.getColomnName().equalsIgnoreCase("ackBySMS")) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String afterConfigKeyword() {
		String returnType = ERROR;
		boolean status = false;
		try {
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
			if (dataCountForEmail != null && dataCountForEmail.size() > 0) {
				// emailID=(String) dataCount.get(0);
				for (Iterator it = dataCountForEmail.iterator(); it.hasNext();) {
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
			if (ackBySMS.equalsIgnoreCase("true")) {
				ob4.setDataName("Yes");
			} else {
				ob4.setDataName("No");
			}
			insertHistory.add(ob4);

			/*
			 * boolean
			 * mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(
			 * ),getPassword
			 * (),object[2].toString(),"K.R Add Notification",mailtext
			 * .toString()); mailtext.delete(0, mailtext.length()); if(mailFlag)
			 * { ////System.out.println(
			 * "**************Mail Sent Successfully**********************"); }
			 */

			if (ackBySMS.equalsIgnoreCase("true")) {
				String SMSClient = autoReplyMsg;
				// //System.out.println("SMSClient"+SMSClient);
				// SMS On DS mobile NO
				// new SendHttpSMS().sendSMS(object[1].toString(), SMSClient);

			}

			// status = cbt.insertIntoTable("configurekeyword",
			// insertHistory,connectionSpace);

			// //System.out.println("status>>>>>>>>>>>>>>>>  "+status);
			List dataList = cbt.executeAllSelectQuery("select id from configurekeyword where keyword = '" + keyword + "'", connectionSpace);
			if (dataList.size() > 0) {
				addActionMessage("Duplicate: Keyword already exists !");
				returnType = ERROR;
			} else {
				status = cbt.insertIntoTable("configurekeyword", insertHistory, connectionSpace);
				if (status) {
					addActionMessage("Keyword Configured Successfully!!!");
					boolean mailFlag = mt.confMailHTML(getServer(), getPort(), getEmailID(), getPassword(), mailIds, "Configure Keyword", autoReplyMsg);
					returnType = SUCCESS;
				} else {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String beforeSpecialityView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setViewProp();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}

	public void setViewProp() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_speciality_configuration", accountID, connectionSpace, "", 0, "table_name", "speciality_configuration");
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

	public String beforeLocationView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setViewProp2();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}

	public void setViewProp2() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_location_configuration", accountID, connectionSpace, "", 0, "table_name", "location_configuration");
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

	public String addSpeciality()
	{
		
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData("mapped_speciality_configuration",
								accountID, connectionSpace, "", 0, "table_name",
								"speciality_configuration");
				if (statusColName != null) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					/*
					 * boolean userTrue = false; boolean dateTrue = false; boolean
					 * timeTrue = false;
					 */
					for (GridDataPropertyView gdp : statusColName) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
						/*
						 * if (gdp.getColomnName().equalsIgnoreCase("userName"))
						 * userTrue = true; else if
						 * (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue =
						 * true; else if
						 * (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue =
						 * true;
						 */
					}
			 cbt.createTable22("speciality", Tablecolumesaaa,
							connectionSpace);
		

					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("")
								&& !paramValue.equalsIgnoreCase("-1")
								&& Parmname != null) {
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);

						}
					}

				

					status = cbt.insertIntoTable("speciality", insertData,
							connectionSpace);
					if (status) {
						addActionMessage("Speciality Added Successfully");
						return SUCCESS;
					} else {
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			return SUCCESS;
		
		
	}

	public String addLocation()
	{
		
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData("mapped_location_configuration",
								accountID, connectionSpace, "", 0, "table_name",
								"location_configuration");
				if (statusColName != null) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					/*
					 * boolean userTrue = false; boolean dateTrue = false; boolean
					 * timeTrue = false;
					 */
					for (GridDataPropertyView gdp : statusColName) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
						/*
						 * if (gdp.getColomnName().equalsIgnoreCase("userName"))
						 * userTrue = true; else if
						 * (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue =
						 * true; else if
						 * (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue =
						 * true;
						 */
					}
			 cbt.createTable22("location_t2m", Tablecolumesaaa,
							connectionSpace);
		

					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("")
								&& !paramValue.equalsIgnoreCase("-1")
								&& Parmname != null) {
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);

						}
					}

				

					status = cbt.insertIntoTable("location_t2m", insertData,
							connectionSpace);
					if (status) {
						addActionMessage("Location Added Successfully");
						return SUCCESS;
					} else {
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			return SUCCESS;
		
		
	}

	public String viewSpeciality() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from speciality");

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

					List fieldNames = Configuration.getColomnList("mapped_speciality_configuration", accountID, connectionSpace, "speciality_configuration");
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

					query.append(" from speciality order by speciality asc ");
					// //System.out.println("query:::" + query);

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
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

					if (getSord() != null && !getSord().equalsIgnoreCase("")) {
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					// //System.out.println("query::::" + query);

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
						setMasterViewList(Listhb);
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

	@SuppressWarnings("unchecked")
	public String viewLocation() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from location_t2m");

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

					List fieldNames = Configuration.getColomnList("mapped_location_configuration", accountID, connectionSpace, "location_configuration");
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

					query.append(" from location_t2m order by location asc");
					// //System.out.println("query:::" + query);

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
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

					if (getSord() != null && !getSord().equalsIgnoreCase("")) {
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" limit " + from + "," + to);
					// //System.out.println("query::::" + query);

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
						setMasterViewList(Listhb);
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

	public String modifyLocation() {

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
					cbt.updateTableColomn("location_t2m", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds) {
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("location_t2m", "id", condtIds.toString(), connectionSpace);
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

	public String modifySpeciality() {

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
					cbt.updateTableColomn("speciality", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds) {
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("speciality", "id", condtIds.toString(), connectionSpace);
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

	// modify secondary keyword
	public String modifySecondaryKeyword() {

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
					cbt.updateTableColomn("t2msecondarykeyword", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					System.out.println("got update dele");
					StringBuilder sb = new StringBuilder();
					sb.append("update t2msecondarykeyword set flag ='Inactive' where id='" + getId() + "'");
					System.out.println("sb " + sb);
					// cbt.updateTableColomn(connectionSpace, "fg");
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

	// modify main keyword
	public String modifyMainKeyword() {

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
					cbt.updateTableColomn("t2mmainkeyword", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					System.out.println("got update dele");
					StringBuilder sb = new StringBuilder();
					sb.append("update t2mmainkeyword set flag ='Inactive' where id='" + getId() + "'");
					cbt.updateTableColomn(connectionSpace, sb);
					System.out.println("sb " + sb);
					StringBuilder sb1 = new StringBuilder();
					sb1.append("Update t2msecondarykeyword set flag='Inactive' where mainKeyword=(select mainKeyword from t2mmainkeyword where id='" + getId() + "')");
					// Update t2msecondarykeyword set flag='Inactive' where
					// mainKeyword=(select mainKeyword from t2mmainkeyword where
					// id='4')
					// cbt.updateTableColomn(connectionSpace, "fg");
					System.out.println("sb1 " + sb1);
					cbt.updateTableColomn(connectionSpace, sb1);
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

	// secondary keyword beforeSecKeywords
	public String beforeSecKeywords()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageDataForsecKeywords();
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForsecKeywords() {
		System.out.println("ksdfkhfk i just enter");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_t2m_secondarykeywords_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("t2m_secondarykeywords_configuration")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_t2m_secondarykeywords_configuration", accountID, connectionSpace, "", 0, "table_name", "t2m_secondarykeywords_configuration");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							System.out.println("1");
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("deactivateOn") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("flag") && !gdp.getColomnName().equalsIgnoreCase("date")) {

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								System.out.println(gdp.getHeadingName());
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

			mainKeywordDD = new LinkedHashMap<String, String>();
			empData.clear();
			empData = cbt.executeAllSelectQuery("select id, mainKeyword from  t2mmainkeyword where flag='Active'", connectionSpace);
			if (empData != null && empData.size() > 0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null) {
						mainKeywordDD.put(object[1].toString(), object[1].toString());

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Main Keywords

	public String beforemainKeywords()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageDataFormainKeywords();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataFormainKeywords() {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_t2m_mainkeywords_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("t2m_mainkeywords_configuration")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_t2m_mainkeywords_configuration", accountID, connectionSpace, "", 0, "table_name", "t2m_mainkeywords_configuration");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("flag")) {

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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// first call main keyword

	public String beforemainKeywordsView()
	{
		 System.out.println("call");

			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
			} catch (Exception e) {
		
				e.printStackTrace();
			}
			return SUCCESS;
		
		 
	 }

	// for main keyword

	public String beforemainKeyView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setmainKeywordsView();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}

	// for secondary keyword

	public String beforeSecondaryKeywordsT2MView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// setsecKeywordsView();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}

	// for secondary keyword

	public String beforesecKeyView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setsecKeywordsView();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}

	// for sec keyword column name set
	public void setsecKeywordsView() {

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_t2m_secondarykeywords_configuration", accountID, connectionSpace, "", 0, "table_name", "t2m_secondarykeywords_configuration");
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

	// for main keyword column name set
	public void setmainKeywordsView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_t2m_mainkeywords_configuration", accountID, connectionSpace, "", 0, "table_name", "t2m_mainkeywords_configuration");
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

	// add secondary keyword in db
	public String addsecKeyword() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_t2m_secondarykeywords_configuration", accountID, connectionSpace, "", 0, "table_name", "t2m_secondarykeywords_configuration");
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
						if (gdp.getColomnName().equalsIgnoreCase("flag")) {
							ob1.setConstraint("default 'Active'");
						} else {
							ob1.setConstraint("default NULL");
						}
						tableColume.add(ob1);

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
					}
					cbt.createTable22("t2msecondarykeyword", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null) {
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

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
					}
					if (timeTrue) {
						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
					}
					status = cbt.insertIntoTable("t2msecondarykeyword", insertData, connectionSpace);
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

	// add main keyword in db
	public String addMainKeyword() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_t2m_mainkeywords_configuration", accountID, connectionSpace, "", 0, "table_name", "t2m_mainkeywords_configuration");
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
						if (gdp.getColomnName().equalsIgnoreCase("flag")) {
							ob1.setConstraint("default 'Active'");
						} else {
							ob1.setConstraint("default NULL");
						}
						tableColume.add(ob1);

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
					}
					cbt.createTable22("t2mmainkeyword", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null) {
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

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
					}
					if (timeTrue) {
						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
					}
					status = cbt.insertIntoTable("t2mmainkeyword", insertData, connectionSpace);
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

	// data view in grid for main keyword
	public String viewSubKeyword() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from t2msecondarykeyword");

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

					List fieldNames = Configuration.getColomnList("mapped_t2m_secondarykeywords_configuration", accountID, connectionSpace, "t2m_secondarykeywords_configuration");
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

					// //System.out.println("query:::" + query);
					query.append(" from t2msecondarykeyword  ");
					if (!getSearchFields1().equalsIgnoreCase("-1")) {
						query.append(" where flag ='" + getSearchFields1() + "'");
					}

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
						if (!getSearchFields1().equalsIgnoreCase("-1")) {
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
					query.append(" order by secKeyword asc");

					System.out.println("query::::" + query);

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
						setT2mViewList(Listhb);
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

	// data view in grid for main keyword
	public String viewMainKeyword() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				System.out.println(" value " + SearchValue1);
				System.out.println(" value2 " + searchFields1);

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from t2mmainkeyword");

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

					List fieldNames = Configuration.getColomnList("mapped_t2m_mainkeywords_configuration", accountID, connectionSpace, "t2m_mainkeywords_configuration");
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

					query.append(" from t2mmainkeyword  ");
					if (!getSearchFields1().equalsIgnoreCase("-1")) {
						query.append(" where flag ='" + getSearchFields1() + "'");
					}

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
						if (!getSearchFields1().equalsIgnoreCase("-1")) {
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

					query.append(" order by mainKeyword asc");
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
						setT2mViewList(Listhb);
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

	// chk sec keyword
	public String checksecKeyword() {

		String returnResult = ERROR;
		try {

			System.out.println("mainKeywordChk0n " + mainKeywordChk);
			if (getMainKeywordChk() != null) {
				checkSecKey = new HashMap<String, String>();

				boolean sttaus = false;
				Map<Integer, String> appList = new HashMap<Integer, String>();
				try {

					// select mainKeyword from t2mmainkeyword where mainKeyword
					// ="BLK"
					List lowerLevel3 = new ArrayList<String>();
					System.out.println("mainKeywordChk " + mainKeywordChk + "sec key " + secKeywordChk);
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					try {
						lowerLevel3 = cbt.executeAllSelectQuery("Select mainKeyword from t2msecondarykeyword where mainKeyword='" + mainKeywordChk + "' and secKeyword='" + secKeywordChk + "'", connectionSpace);
					} catch (Exception e) {
						System.out.println("no data ");
						e.printStackTrace();
					}
					if (lowerLevel3.size() > 0) {
						sttaus = true;
						checkSecKey.put("msg", "Keyword already exist");
						checkSecKey.put("status", "true");
						System.out.println(checkSecKey);
						return SUCCESS;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!sttaus) {
					checkSecKey.put("msg", "You can create");
					checkSecKey.put("status", "false");
				} else {

				}
			}
			returnResult = SUCCESS;

		} catch (Exception e) {
			// log.error("Date : " + DateUtil.getCurrentDateIndianFormat() +
			// " Time: " + DateUtil.getCurrentTime() + " " +
			// "Problem in Over2Cloud  method checkUserName of class " +
			// getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return returnResult;

	}

	// chk main keyword
	public String checkmainKeyword() {

		String returnResult = ERROR;
		try {

			System.out.println("mainKeywordChk0n " + mainKeywordChk);
			if (getMainKeywordChk() != null) {
				checkMainKey = new HashMap<String, String>();

				boolean sttaus = false;
				Map<Integer, String> appList = new HashMap<Integer, String>();
				try {

					// select mainKeyword from t2mmainkeyword where mainKeyword
					// ="BLK"
					List lowerLevel3 = new ArrayList<String>();
					System.out.println("mainKeywordChk " + mainKeywordChk);
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("mainKeyword", mainKeywordChk);
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					try {
						lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("t2mmainkeyword", lowerLevel3, temp, connectionSpace);
					} catch (Exception e) {
						System.out.println("no data ");
						e.printStackTrace();
					}
					if (lowerLevel3.size() > 0) {
						sttaus = true;
						checkMainKey.put("msg", "Keyword already exist");
						checkMainKey.put("status", "true");
						System.out.println(checkMainKey);
						return SUCCESS;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!sttaus) {
					checkMainKey.put("msg", "You can create");
					checkMainKey.put("status", "false");
				} else {

				}
			}
			returnResult = SUCCESS;

		} catch (Exception e) {
			// log.error("Date : " + DateUtil.getCurrentDateIndianFormat() +
			// " Time: " + DateUtil.getCurrentTime() + " " +
			// "Problem in Over2Cloud  method checkUserName of class " +
			// getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return returnResult;

	}

	public String getMainKeywordChk() {
		return mainKeywordChk;
	}

	public void setMainKeywordChk(String mainKeywordChk) {
		this.mainKeywordChk = mainKeywordChk;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextBox() {
		return configKeyTextBox;
	}

	public void setConfigKeyTextBox(List<ConfigurationUtilBean> configKeyTextBox) {
		this.configKeyTextBox = configKeyTextBox;
	}

	public List<ConfigurationUtilBean> getConfigKeyDropDown() {
		return configKeyDropDown;
	}

	public void setConfigKeyDropDown(List<ConfigurationUtilBean> configKeyDropDown) {
		this.configKeyDropDown = configKeyDropDown;
	}

	public List<ConfigurationUtilBean> getConfigKeyCheckBox() {
		return configKeyCheckBox;
	}

	public void setConfigKeyCheckBox(List<ConfigurationUtilBean> configKeyCheckBox) {
		this.configKeyCheckBox = configKeyCheckBox;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextArea() {
		return configKeyTextArea;
	}

	public void setConfigKeyTextArea(List<ConfigurationUtilBean> configKeyTextArea) {
		this.configKeyTextArea = configKeyTextArea;
	}

	public String getGroupExist() {
		return groupExist;
	}

	public void setGroupExist(String groupExist) {
		this.groupExist = groupExist;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAckBySMSExist() {
		return ackBySMSExist;
	}

	public void setAckBySMSExist(String ackBySMSExist) {
		this.ackBySMSExist = ackBySMSExist;
	}

	public String getAckBySMS() {
		return ackBySMS;
	}

	public void setAckBySMS(String ackBySMS) {
		this.ackBySMS = ackBySMS;
	}

	public String getCheckBoxExist() {
		return CheckBoxExist;
	}

	public void setCheckBoxExist(String checkBoxExist) {
		CheckBoxExist = checkBoxExist;
	}

	public String getCheckBox() {
		return CheckBox;
	}

	public void setCheckBox(String checkBox) {
		CheckBox = checkBox;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAutoReplyMsg() {
		return autoReplyMsg;
	}

	public void setAutoReplyMsg(String autoReplyMsg) {
		this.autoReplyMsg = autoReplyMsg;
	}

	public String getMailIds() {
		return mailIds;
	}

	public void setMailIds(String mailIds) {
		this.mailIds = mailIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
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

	public Map<String, String> getMainKeywordDD() {
		return mainKeywordDD;
	}

	public void setMainKeywordDD(Map<String, String> mainKeywordDD) {
		this.mainKeywordDD = mainKeywordDD;
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

	public String getFormater() {
		return formater;
	}

	public void setFormater(String formater) {
		this.formater = formater;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public List<Object> getT2mViewList() {
		return t2mViewList;
	}

	public void setT2mViewList(List<Object> t2mViewList) {
		this.t2mViewList = t2mViewList;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, String> getCheckMainKey() {
		return checkMainKey;
	}

	public void setCheckMainKey(Map<String, String> checkMainKey) {
		this.checkMainKey = checkMainKey;
	}

	public String getSearchFields1() {
		return searchFields1;
	}

	public void setSearchFields1(String searchFields1) {
		this.searchFields1 = searchFields1;
	}

	public String getSearchValue1() {
		return SearchValue1;
	}

	public void setSearchValue1(String searchValue1) {
		SearchValue1 = searchValue1;
	}

	public String getSecKeywordChk() {
		return secKeywordChk;
	}

	public void setSecKeywordChk(String secKeywordChk) {
		this.secKeywordChk = secKeywordChk;
	}

	public Map<String, String> getCheckSecKey() {
		return checkSecKey;
	}

	public void setCheckSecKey(Map<String, String> checkSecKey) {
		this.checkSecKey = checkSecKey;
	}

}
