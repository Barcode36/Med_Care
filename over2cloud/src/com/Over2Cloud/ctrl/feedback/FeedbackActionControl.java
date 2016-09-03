package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CounterPojo;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.feedback.pojo.PatientInfo;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.feedback.service.FeedbackViaTab;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FeedbackActionControl extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(FeedbackActionControl.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	// HttpServletRequest request = (HttpServletRequest)
	// ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	String userType = (String) session.get("userTpe");
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> feedbackTextBox = null;

	private String paperRefNo = "";

	private String mainHeaderName;
	private String modifyFlag = "";
	private String flage;
	private String flag;
	private String deleteFlag = "";
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private String count;

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
	private String feedTicketId;
	private List<Object> masterViewList;
	private boolean ddTrue;
	private boolean stafftrue;
	private boolean costingTrue;
	private boolean treatmentTrue;
	private boolean ambienceTrue;
	private boolean billingTrue;
	private boolean resultTrue;
	private boolean overAllTrue;
	private boolean otherTrue;

	// Added By Avinash
	private boolean validityTrue;
	private boolean administrativeTrue;

	// Added Ends
	private String ddValue;

	private String mobileNo;
	private String name;
	private String emailId;
	private String clientId;

	private String cleanliness;
	private String commentsHyg;
	private String staffBehaviour;
	private String costingFactor;
	private String treatment;
	private String ambience;
	private String billingServices;
	private String resultSatis;
	private String overAll;
	private String comment;
	private String actionName;
	private String actionDesc;
	private String actionMob;
	private String actionEmail;
	private String commentsCosting;
	private String commentsStaff;
	private String commentsTreatment;
	private String commentsAmbience;
	private String commentsBilling;
	private String commentsResult;
	private String commentsAll;
	private String actionTaken;
	private String comments;
	private String docterName;
	private String purposeOfVisit;

	private String validity;
	private String admin;
	private String otherFacil;
	private String emailID;
	private String hospitalName = "RGCIRC";
	private String patientId;
	private String tabUserId;

	private Map<Integer, String> sourceList = null;
	private String status;
	private String level;

	private String remoteAdd;
	private int serverPort;
	private String contextName;

	private String formater;
	private String targetOn;
	private String ticketNo;
	private String openDate;
	private String openTime;
	private String choseHospital;

	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	private InputStream inputStream;
	private List<Parent> parentTakeaction = null;
	private Map<String, String> leadAActionTextBox = null;
	private Map<Integer, String> targetKpiLiist = null;
	private String fir;
	private String feedDataId;

	private String todayPositive;
	private String todayNegative;
	private String todayTotal;

	private String graphHeader;
	private String graphHeader1;
	private String graphHeader2;
	private String graphHeader3;
	private String graphHeader4;
	private String graphHeader5;
	private String graphHeader6;
	private String graphHeader7;
	private Map<String, Integer> pieDataMap;
	private Map<String, Integer> pieDataMap1;
	private Map<String, Integer> pieDataMap2;
	private Map<String, Integer> pieDataMap3;
	private Map<String, Integer> pieDataMap4;
	private Map<String, Integer> pieDataMap5;
	private Map<String, Integer> pieDataMap6;
	private Map<String, Integer> pieDataMap7;
	private Map<String, Integer> pieDataMapYes;
	private Map<String, Integer> pieDataMapNo;

	List<GridDataPropertyView> CRMColumnNames;

	private String mode;
	private String searchFor;
	private String fromDate;
	private String toDate;
	private String patType;
	private String feedBack;
	private String spec;
	private String docName;

	private Map<String, String> speciality;
	private Map<String, String> treatedBy;
	private Map<String, String> feedbackActionStatus;

	private String actionStatus;

	private Map<String, String> actionNameMap;
	private PatientInfo patObj;

	private String crNo;
	private String patName;
	private String mobNo;
	private String roomNo;
	private String specality;
	private String observation;

	private String totalPaper;
	private String posPaper;
	private String negPaper;
	private String posSMS;
	private String totalPos;
	private String totalNeg;

	private JSONObject patObj1;
	private JSONObject paperViewObj;
	private JSONObject paperViewObjIPD;

	private String compType;

	public String getPatientsDataInGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from patientinfo");
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList("mapped_patient_configuration", accountID, connectionSpace, "patient_configuration");
				List<Object> listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1) query.append(obdata.toString() + ",");
						else query.append(obdata.toString());
					}
					i++;

				}
				query.append(" from patientinfo where id!=0 ");

				if (getFromDate() != null && getToDate() != null)
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" and insert_date between '" + ((getFromDate())) + "' and '" + ((getToDate())) + "'");
					}
					else
					{
						query.append(" and insert_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' and '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
					}
				}
				else
				{
					query.append(" and insert_date between '" + (DateUtil.getNextDateAfter(-6)) + "' and '" + (DateUtil.getCurrentDateUSFormat()) + "'");
				}

				if (getPatType() != null && !getPatType().equalsIgnoreCase("-1"))
				{
					query.append(" and patient_type='" + getPatType() + "'");
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{

					query.append(" and  ");
					// add search query based on the search operation
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
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					Collections.reverse(data);
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();

						// System.out.println("Obdata :::: :"+obdata.length);
						//System.out.println("fieldNames sdize "+fieldNames.size
						// ());

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
									if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("visitDate"))
									{
										if (obdata[k].toString().length() > 12)
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(8, 10) + "-" + obdata[k].toString().substring(5, 7) + "-" + obdata[k].toString().substring(0, 4) + " " + obdata[k].toString().substring(obdata[k].toString().indexOf(" "), obdata[k].toString().length()));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("dischargeDate"))
									{
										if (obdata[k].toString().length() > 12)
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(8, 10) + "-" + obdata[k].toString().substring(5, 7) + "-" + obdata[k].toString().substring(0, 4) + " " + obdata[k].toString().substring(obdata[k].toString().indexOf(" "), obdata[k].toString().length()));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("updateDate"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("insertDate"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("smsFlag"))
									{
										if (obdata[k].toString() != null && obdata[k].toString().equalsIgnoreCase("0"))
										{
											one.put(fieldNames.get(k).toString(), "No");
										}
										else if (obdata[k].toString() != null && obdata[k].toString().equalsIgnoreCase("3"))
										{
											one.put(fieldNames.get(k).toString(), "Yes");
										}

									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}

							}
						}
						listhb.add(one);
					}
					setMasterViewList(listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getGraph()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			pieDataMap = new HashMap<String, Integer>();
			pieDataMap1 = new HashMap<String, Integer>();
			pieDataMap2 = new HashMap<String, Integer>();
			pieDataMap3 = new HashMap<String, Integer>();
			pieDataMap4 = new HashMap<String, Integer>();
			pieDataMap5 = new HashMap<String, Integer>();
			pieDataMap6 = new HashMap<String, Integer>();
			pieDataMap7 = new HashMap<String, Integer>();

			try
			{/*
			 * SessionFactory
			 * connectionSpace=(SessionFactory)session.get("connectionSpace");
			 * // boolean tableExists=new
			 * createTable().tableExists("feedbackdata",connectionSpace); //
			 * System
			 * .out.println("Table >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			 * +tableExists); if(true) { List<String> whrClausParam=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "cleanliness"," select "); int counter=0;
			 * graphHeader="Pie Chart For Front Office"; for(String s
			 * :whrClausParam) { counter=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "cleanliness",s); pieDataMap.put(s, counter); }
			 * List<String> whrClausParam1=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "staffBehaviour"," select "); int counter1=0;
			 * graphHeader1="Pie Chart For Medical Care"; for(String s
			 * :whrClausParam1) { counter1=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "staffBehaviour",s); pieDataMap1.put(s,
			 * counter1); } List<String> whrClausParam2=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "costingFactor"," select "); int counter2=0;
			 * graphHeader2="Pie Chart For Nurshing Care"; for(String s
			 * :whrClausParam2) { counter2=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "costingFactor",s); pieDataMap2.put(s, counter2);
			 * } List<String> whrClausParam3=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "treatment"," select "); int counter3=0;
			 * graphHeader3="Pie Chart For Diagnostics"; for(String s
			 * :whrClausParam3) { counter3=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "treatment",s); pieDataMap3.put(s, counter3); }
			 * List<String> whrClausParam4=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "ambience"," select "); int counter4=0;
			 * graphHeader4="Pie Chart For Pharmacy"; for(String s
			 * :whrClausParam4) { counter4=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "ambience",s); pieDataMap4.put(s, counter4); }
			 * List<String> whrClausParam5=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "billingServices"," select "); int counter5=0;
			 * graphHeader5="Pie Chart For Over All Services"; for(String s
			 * :whrClausParam5) { counter5=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "billingServices",s); pieDataMap5.put(s,
			 * counter5); } List<String> whrClausParam6=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "OverAll"," select "); int counter6=0;
			 * graphHeader6="Pie Chart For Other Facilities"; for(String s
			 * :whrClausParam6) { counter6=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "Overallexperience",s); pieDataMap6.put(s,
			 * counter6); } List<String> whrClausParam7=new
			 * EscalationActionControlDao().getGraphData(connectionSpace,
			 * "feedbackdata", "targetOn"," select "); int counter7=0;
			 * graphHeader7="Pie Chart For "; for(String s :whrClausParam7) {
			 * counter7=new
			 * EscalationActionControlDao().getGraphDataCounter(connectionSpace,
			 * "feedbackdata", "targetOn",s); pieDataMap7.put(s, counter7); }
			 * 
			 * }
			 * 
			 * setMainHeaderName("Graphical Report s of All Department>> View");
			 */
				return SUCCESS;
			}
			catch (Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getJsonDataForId()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid && getPatientId() != null && !getPatientId().equalsIgnoreCase(""))
		{
			try
			{
				patObj = new PatientInfo();

				// StringBuffer buffer=new StringBuffer(
				// "select cr_number,patient_name,consult_clinician_name,admission_time,discharge_datetime,station_unit,mobile_no,email,patient_type from patientinfo where cr_number='"
				// +getPatientId().substring(0,
				// getPatientId().length()-1)+"' and patient_type like '"
				// +type+"%' order by id desc limit 1");
				StringBuffer buffer = new StringBuffer("select cr_number,patient_name,patient_type,station,mobile_no,email,admit_consultant,visit_type,companytype,patientId,discharge_datetime from patientinfo where cr_number='" + getPatientId() + "' and patient_type ='" + getPatType() + "' order by id desc limit 1");
				// StringBuffer buffer=new StringBuffer(
				// "select cr_number,patient_name,consult_clinician_name,admission_time,discharge_datetime,station_unit,mobile_no,email,patient_type from patientinfo where cr_number='"
				// +getPatientId().substring(0,
				// getPatientId().length()-1)+"' and patient_type like '"
				// +type+"%' order by id desc limit 1");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List dataList = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null)
							{
								patObj.setPatientId(object[0].toString());
							}
							else
							{
								patObj.setPatientId("NA");
							}

							if (object[1] != null)
							{
								patObj.setPatientName(object[1].toString());
							}
							else
							{
								patObj.setPatientName("NA");
							}

							if (object[2] != null)
							{
								patObj.setPatType(object[2].toString());
							}
							else
							{
								patObj.setPatType("NA");
							}

							if (object[3] != null)
							{
								patObj.setRoomNo(object[3].toString());
							}
							else
							{
								patObj.setRoomNo("NA");
							}

							if (object[4] != null)
							{
								patObj.setPatientMobileNo(object[4].toString());
							}
							else
							{
								patObj.setPatientMobileNo("NA");
							}

							if (object[5] != null)
							{
								patObj.setPatientEmailId(object[5].toString());
							}
							else
							{
								patObj.setPatientEmailId("NA");
							}

							if (object[6] != null)
							{
								patObj.setDoctorName(object[6].toString());
							}
							else
							{
								patObj.setDoctorName("NA");
							}

							if (object[7] != null)
							{
								patObj.setVisit_type(object[7].toString());
							}
							else
							{
								patObj.setVisit_type("NA");
							}

							if (object[8] != null)
							{
								patObj.setCompanytype(object[8].toString());
							}
							else
							{
								patObj.setCompanytype("NA");
							}

							if (object[9] != null)
							{
								patObj.setRecordId(object[9].toString());
							}
							else
							{
								patObj.setRecordId("NA");
							}

							if (object[10] != null)
							{
								patObj.setDischarge_datetime(object[10].toString());
							}
							else
							{
								patObj.setDischarge_datetime("NA");
							}
						}

						boolean flag = new HelpdeskUniversalHelper().isExist("feedbackdata", "clientId", patObj.getPatientId(), "patientId", patObj.getRecordId(), "", "", connectionSpace);
						if (flag)
						{
							// System.out.println("Already Exists...");
							patObj.setPatType("error");
							return "already";
						}
					}
					return SUCCESS;
				}
				else
				{
					return "unsuccess";
				}

			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeleadAdd of class " + getClass(), e);
				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}

	}

	public void setPatientViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_patient_configuration", accountID, connectionSpace, "", 0, "table_name", "patient_configuration");

			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("tabId"))
				{

				}
				else
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter(gdp.getFormatter());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) { return LOGIN; }

			fromDate = DateUtil.getNextDateAfter(-6);
			toDate = DateUtil.getCurrentDateUSFormat();

			setPatType(getPatType());

			speciality = new HashMap<String, String>();
			FeedbackHelper helper = new FeedbackHelper();
			speciality = helper.getDistinctColVal(connectionSpace, "centreName");

			treatedBy = helper.getDistinctColVal(connectionSpace, "serviceBy");

			feedbackActionStatus = helper.getDistinctColVal(connectionSpace, "status");

			totalPaper = helper.getFeedbackCounters("Paper", "", connectionSpace);
			posPaper = helper.getFeedbackCounters("Paper", "Yes", connectionSpace);
			negPaper = helper.getFeedbackCounters("Paper", "No", connectionSpace);

			totalPos = helper.getFeedbackCounters("", "Yes", connectionSpace);
			totalNeg = helper.getFeedbackCounters("", "No", connectionSpace);

			posSMS = helper.getFeedbackCounters("SMS", "Yes", connectionSpace);

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), e);
			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getPatientViewPage()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				setPatientViewProp();
				return SUCCESS;
			}
			catch (Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String beforeFeedbackAdd()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				setPaperRefNo(new FeedbackDashboardDao().getPaperRefNo(connectionSpace));
				// System.out.println("Ref No is as "+getPaperRefNo());
				setFeedbackAddPageDataFields();
				setMainHeaderName("Paper Feedback >> Add");
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeleadAdd of class " + getClass(), e);
				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setFeedbackAddPageDataFields()
	{
		try
		{

			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_feedback_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_data_configuration");
			if (offeringLevel1 != null)
			{
				feedbackTextBox = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : offeringLevel1)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("openDate") && !gdp.getColomnName().equalsIgnoreCase("openTime") && !gdp.getColomnName().equalsIgnoreCase("ip") && !gdp.getColomnName().equalsIgnoreCase("kword") && !gdp.getColomnName().equalsIgnoreCase("handledBy") && !gdp.getColomnName().equalsIgnoreCase("level") && !gdp.getColomnName().equalsIgnoreCase("mode") && !gdp.getColomnName().equalsIgnoreCase("targetOn") && !gdp.getColomnName().equalsIgnoreCase("tabId")
							&& !gdp.getColomnName().equalsIgnoreCase("comment") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("actionComments") && !gdp.getColomnName().equalsIgnoreCase("comments") && !gdp.getColomnName().equalsIgnoreCase("blkId") && !gdp.getColomnName().equalsIgnoreCase("nameEmp") && !gdp.getColomnName().equalsIgnoreCase("appFor"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null) objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
						feedbackTextBox.add(objdata);
					}

					else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("openDate") && !gdp.getColomnName().equalsIgnoreCase("openTime"))
					{
						if (gdp.getColomnName().equalsIgnoreCase("cleanliness"))
						{
							cleanliness = gdp.getHeadingName();
							ddTrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("billingServices"))
						{
							billingServices = gdp.getHeadingName();
							costingTrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("overAll"))
						{
							overAll = gdp.getHeadingName();
							overAllTrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("other"))
						{
							otherFacil = gdp.getHeadingName();
							otherTrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("staffBehaviour"))
						{
							staffBehaviour = gdp.getHeadingName();
							stafftrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("costingFactor"))
						{
							costingFactor = gdp.getHeadingName();
							costingTrue = true;
						}
						if (gdp.getColomnName().equalsIgnoreCase("treatment"))
						{
							treatment = gdp.getHeadingName();
							treatmentTrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("ambience"))
						{
							ambience = gdp.getHeadingName();
							ambienceTrue = true;
						}
						else if (gdp.getColomnName().equalsIgnoreCase("resultSatis"))
						{
							resultSatis = gdp.getHeadingName();
							resultTrue = true;
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

	// For Paper
	public String addFeedbackViaPaper()
	{
		try
		{
			ActivityBoardHelper helper = new ActivityBoardHelper();
			String location = "", remarks = "", patType = "", patientId = "";
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_feedback_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_data_configuration");
			if (statusColName != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertDataIntoRating = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				InsertDataTable ratingobj = null;
				
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

				List<TableColumes> TablecolumesaaaIP = new ArrayList<TableColumes>();
				List<TableColumes> TablecolumesaaaOP = new ArrayList<TableColumes>();

				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;

				boolean negFeedback = false;

				List<GridDataPropertyView> statusColNamePaperRatingOPD = Configuration.getConfigurationData("mapped_feedback_paperform_rating_configuration_opd", accountID, connectionSpace, "", 0, "table_name", "feedback_paperform_rating_configuration_opd");
				List<GridDataPropertyView> statusColNamePaperRatingIPD = Configuration.getConfigurationData("mapped_feedback_paperform_rating_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_paperform_rating_configuration");

				for (GridDataPropertyView gdp : statusColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("openDate")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("openTime")) timeTrue = true;
				}

				for (GridDataPropertyView gdp : statusColNamePaperRatingOPD)
				{

					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					TablecolumesaaaOP.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("openDate")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("openTime")) timeTrue = true;

				}

				for (GridDataPropertyView gdp : statusColNamePaperRatingIPD)
				{

					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					TablecolumesaaaIP.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("openDate")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("openTime")) timeTrue = true;

				}

				cbt.createTable22("feedback_paper_rating", TablecolumesaaaOP, connectionSpace);
				cbt.createTable22("feedback_paper_rating", TablecolumesaaaIP, connectionSpace);

				cbt.createTable22("feedbackdata", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());

				
				InsertDataTable recommendColumn=new InsertDataTable();
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				double d = 0.0;
				boolean validTargetOn = false;
				String overAllComment = "";
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						if (Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("refNo") || Parmname.equalsIgnoreCase("clientId") || Parmname.equalsIgnoreCase("clientName") || Parmname.equalsIgnoreCase("compType") || Parmname.equalsIgnoreCase("centerCode") || Parmname.equalsIgnoreCase("mobNo") || Parmname.equalsIgnoreCase("emailId") || Parmname.equalsIgnoreCase("serviceBy") || Parmname.equalsIgnoreCase("centreName") || Parmname.equalsIgnoreCase("choseHospital") || Parmname.equalsIgnoreCase("targetOn") || Parmname.equalsIgnoreCase("overAll")
								|| Parmname.equalsIgnoreCase("patientId") || Parmname.equalsIgnoreCase("companytype") || Parmname.equalsIgnoreCase("discharge_datetime"))
						{
							ob = new InsertDataTable();

							if (Parmname.equalsIgnoreCase("patientId"))
							{
								patientId = paramValue;
								ob.setColName(Parmname);
							}
							else if (Parmname.equalsIgnoreCase("clientId"))
							{
								clientId = paramValue;
								ob.setColName(Parmname);
							}
							else if (Parmname.equalsIgnoreCase("overAll"))
							{
								overAllComment = paramValue;
								ob.setColName(Parmname);
							}
							else if (Parmname.equalsIgnoreCase("targetOn"))
							{
								//System.out.println("Recommed is as "+paramValue
								// );
								targetOn = paramValue;
								ob.setColName("recommend");
								
								recommendColumn.setColName("recommend");
								recommendColumn.setDataName(paramValue);
								
							}
							else
							{
								ob.setColName(Parmname);
							}

							if (Parmname.equalsIgnoreCase("choseHospital"))
							{
								ob.setDataName(getChoseHospital());
							}
							else
							{
								ob.setDataName(paramValue);
							}
							insertData.add(ob);
						}
						else
						{
							ratingobj = new InsertDataTable();
							ratingobj.setColName(Parmname);
							ratingobj.setDataName(paramValue);
							insertDataIntoRating.add(ratingobj);
						}

						// System.out.println("Target On is as "+targetOn);
						if (Parmname.equalsIgnoreCase("front_ease") || Parmname.equalsIgnoreCase("responseToQueries") || Parmname.equalsIgnoreCase("courtesybehaviour") || Parmname.equalsIgnoreCase("counselling") || Parmname.equalsIgnoreCase("admission") || Parmname.equalsIgnoreCase("attentivePrCaring") || Parmname.equalsIgnoreCase("responseCommDoctor") || Parmname.equalsIgnoreCase("diagnosis") || Parmname.equalsIgnoreCase("treatment") || Parmname.equalsIgnoreCase("attentiveprompt") || Parmname.equalsIgnoreCase("responseCommunication") || Parmname.equalsIgnoreCase("procedures")
								|| Parmname.equalsIgnoreCase("medication") || Parmname.equalsIgnoreCase("careAtHome") || Parmname.equalsIgnoreCase("qualityUpkeep") || Parmname.equalsIgnoreCase("behivourResponseTime") || Parmname.equalsIgnoreCase("functioningMainenance") || Parmname.equalsIgnoreCase("qualityofFoods") || Parmname.equalsIgnoreCase("timelineService") || Parmname.equalsIgnoreCase("behivourResponse") || Parmname.equalsIgnoreCase("assessmentCounseling") || Parmname.equalsIgnoreCase("responsetoQuery") || Parmname.equalsIgnoreCase("efficiencyBillingdesk")
								|| Parmname.equalsIgnoreCase("dischargetime") || Parmname.equalsIgnoreCase("pharmacyservices") || Parmname.equalsIgnoreCase("cafeteriaDyning") || Parmname.equalsIgnoreCase("overallsecurity") || Parmname.equalsIgnoreCase("overallservices") || Parmname.equalsIgnoreCase("ease_Contact") || Parmname.equalsIgnoreCase("res_queries") || Parmname.equalsIgnoreCase("polndCourt") || Parmname.equalsIgnoreCase("regndBill") || Parmname.equalsIgnoreCase("wait4Consltnt") || Parmname.equalsIgnoreCase("diagnosis") || Parmname.equalsIgnoreCase("treatment")
								|| Parmname.equalsIgnoreCase("medication") || Parmname.equalsIgnoreCase("careAtHome") || Parmname.equalsIgnoreCase("sampleColl") || Parmname.equalsIgnoreCase("labServices") || Parmname.equalsIgnoreCase("radiologyServices") || Parmname.equalsIgnoreCase("cardiology") || Parmname.equalsIgnoreCase("endoscopy") || Parmname.equalsIgnoreCase("neurology") || Parmname.equalsIgnoreCase("urology") || Parmname.equalsIgnoreCase("preHealth") || Parmname.equalsIgnoreCase("anyOther") || Parmname.equalsIgnoreCase("cleanWashroom") || Parmname.equalsIgnoreCase("unkeepFacility")
								|| Parmname.equalsIgnoreCase("locndDirSignages") || Parmname.equalsIgnoreCase("chemistShop") || Parmname.equalsIgnoreCase("waitingAreas") || Parmname.equalsIgnoreCase("cafeteria") || Parmname.equalsIgnoreCase("parking") || Parmname.equalsIgnoreCase("security") || Parmname.equalsIgnoreCase("overallService") || Parmname.equalsIgnoreCase("res_queries") || Parmname.equalsIgnoreCase("polndCourt") || Parmname.equalsIgnoreCase("regndBill") || Parmname.equalsIgnoreCase("wait4Consltnt") || Parmname.equalsIgnoreCase("diagnosis") || Parmname.equalsIgnoreCase("treatment")
								|| Parmname.equalsIgnoreCase("medication") || Parmname.equalsIgnoreCase("careAtHome") || Parmname.equalsIgnoreCase("sampleColl") || Parmname.equalsIgnoreCase("labServices") || Parmname.equalsIgnoreCase("radiologyServices") || Parmname.equalsIgnoreCase("cardiology"))
						{
							if (paramValue.equalsIgnoreCase("-1") || paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("2"))
							{
								negFeedback = true;
							}
						}
					}
					if (Parmname != null && Parmname.equalsIgnoreCase("mobNo"))
					{
						mobileNo = paramValue;
					}
					if (Parmname != null && Parmname.equalsIgnoreCase("clientName"))
					{
						name = paramValue;
					}

					if (Parmname != null && Parmname.equalsIgnoreCase("emailId"))
					{
						emailId = paramValue;
					}

					if (Parmname != null && Parmname.equalsIgnoreCase("centerCode"))
					{
						location = paramValue;
					}

					if (Parmname != null && Parmname.equalsIgnoreCase("remarks"))
					{
						remarks = paramValue;
					}

					if (Parmname != null && Parmname.equalsIgnoreCase("clientId"))
					{
						clientId = paramValue;
					}

					if (Parmname != null && Parmname.equalsIgnoreCase("compType"))
					{
						patType = paramValue;
					}
				}
				// System.out.println("OverAllCommet>>>"+overAllComment);
				if (overAllComment.equalsIgnoreCase("No"))
				{
					// System.out.println(
					// "Setting Target On Value as No >>>>>>>>>>>>>>>>>>>>>>>>>>>"
					// );
					targetOn = "No";
				}
				if (userTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
				}
				if (dateTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("openDate");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
				}
				if (timeTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("openTime");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Pending");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("mode");
				ob.setDataName("Paper");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("Level1");
				insertData.add(ob);

				//System.out.println("Target On is as >>>>>>>>>>>>>>>>>"+targetOn
				// );
				ob = new InsertDataTable();
				ob.setColName("targetOn");
				ob.setDataName(targetOn);
				insertData.add(ob);

				if (true)
				{
					status = cbt.insertIntoTable("feedbackdata", insertData, connectionSpace);
				}
				else
				{
					return "exists";
				}

				// Code added for Rating Starts

				if (status)
				{
					int feedbackrowid = FeedbackHelper.getRowId(clientId, connectionSpace);
					ratingobj = new InsertDataTable();
					ratingobj.setColName("max_id_feeddbackdata");
					ratingobj.setDataName(feedbackrowid);
					insertDataIntoRating.add(ratingobj);

					boolean statusrating = cbt.insertIntoTable("feedback_paper_rating", insertDataIntoRating, connectionSpace);

				}
				insertDataIntoRating.add(recommendColumn);

				// Code added for Rating Ends

				boolean send = false;

				String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);
				String posKeyword = new ReportsConfigurationDao().getKeyWord(connectionSpace, "FM", "0");
				String negKeyword = new ReportsConfigurationDao().getKeyWord(connectionSpace, "FM", "1");
				// String sendDate=new
				// ReportsConfigurationDao().getSelectedDataFromTable
				// (connectionSpace,
				// "select distinct smsDate from feedback_sms_config where keyword in ('"
				// +posKeyword+"','"+negKeyword+"')");
				// String sendTime=new
				// ReportsConfigurationDao().getSelectedDataFromTable
				// (connectionSpace,
				// "select distinct smsTime from feedback_sms_config where keyword in ('"
				// +posKeyword+"','"+negKeyword+"')");
				String virtualNo = new ReportsConfigurationDao().getVirtualNo(connectionSpace, "FM");

				if (status && mobileNo != null)
				{
					hospitalName = orgName;

					// Code to Send SMS
					String msg = "Dear " + name + ", thanks for your valuable feedback filled at " + hospitalName + ". Please SMS " + negKeyword + " to " + virtualNo + " if you have not filled the form.";
					// commented to change the draft for Patient
					// send= new
					// MsgMailCommunication().addScheduledMessage(name,
					// "Patient",
					// mobileNo,msg,"","0","0","FM",sendDate,sendTime+":00"
					// ,connectionSpace);
				}
				String msg = "Dear " + name + ", thanks for your valuable feedback filled at " + hospitalName + ". Please SMS " + negKeyword + " to " + virtualNo + " if you have not filled the form.";
				StringBuilder mailtext = new StringBuilder("");
				mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><B>" + msg + "</FONT></div>");
				if (emailId != null)
				{
					// send=new
					// MsgMailCommunication().addScheduleMail(name,"Patient",
					// emailId,"Feedback Submission Acknowledgement",
					// mailtext.toString(), "","Pending", "0",
					// null,"FM",sendDate,sendTime+":00",connectionSpace);
				}
				if (send)
				{
				}
				if (status)
				{
					if (targetOn != null)
					{
						Properties configProp = new Properties();
						FeedbackViaTab tab = new FeedbackViaTab();
						String adminDeptName = null;
						// Note Change Selected ID Value
						try
						{
							InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
							configProp.load(in);
							adminDeptName = configProp.getProperty("admin");
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						int subCatId = 0;
						int adminDeptId = 0;
						// System.out.println();
						if (adminDeptName != null)
						{
							adminDeptId = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, "select id from department where deptName='" + adminDeptName + "'");
							// System.out.println(negFeedback+
							// "<>< >"+targetOn+"<overAllComment>"
							// +overAllComment);
							if (negFeedback || targetOn.equalsIgnoreCase("No") || overAllComment.equalsIgnoreCase("No"))
							{
								// System.out.println(
								// "Rating >>>>>>>>>>>>>>>>>>>>>"+negFeedback);
								subCatId = new EscalationActionControlDao().getAdminDeptSubCatId(connectionSpace, String.valueOf(adminDeptId));
							}
							else
							{
								subCatId = new EscalationActionControlDao().getAdminDeptSubCatIdPos(connectionSpace, String.valueOf(adminDeptId));
							}
							// System.out.println("Sub Cat is as "+subCatId);
						}
						if (adminDeptId != 0 && adminDeptId > 0 && subCatId != 0)
						{
							ticketNo = tab.openTicket(connectionSpace, String.valueOf(subCatId), "Paper", name, mobileNo, emailId, String.valueOf(adminDeptId), String.valueOf(adminDeptId), location, remarks, targetOn, clientId, patType, userName, patientId);
							// System.out.println("Ticket No is as "+ticketNo);
							if (ticketNo != null && !ticketNo.equalsIgnoreCase(""))
							{
								int id4 = 0;
								StringBuffer buffer = new StringBuffer("select id from feedback_status where ticket_no='" + ticketNo + "'");
								List dataList = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
								if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
								{
									id4 = Integer.parseInt(dataList.get(0).toString());
								}
								if (id4 != 0)
								{
									StringBuilder queryNew = new StringBuilder("");
									queryNew.append(" select * from feedback_status where id='" + id4 + "'  ");
									List dataNew = cbt.executeAllSelectQuery(queryNew.toString(), connectionSpace);
									for (Iterator iterator = dataNew.iterator(); iterator.hasNext();)
									{
										Object object[] = (Object[]) iterator.next();
										openDate = (String) object[13];
										openTime = (String) object[14];
									}
									Tablecolumesaaa.clear();
									TableColumes ob1 = new TableColumes();
									ob1 = new TableColumes();
									ob1.setColumnname("feedTicketNo");
									ob1.setDatatype("varchar(255)");
									ob1.setConstraint("default NULL");
									Tablecolumesaaa.add(ob1);

									TableColumes ob2 = new TableColumes();
									ob2 = new TableColumes();
									ob2.setColumnname("feed_stat_id");
									ob2.setDatatype("varchar(255)");
									ob2.setConstraint("default NULL");
									Tablecolumesaaa.add(ob2);

									TableColumes ob3 = new TableColumes();
									ob3 = new TableColumes();
									ob3.setColumnname("feed_data_id");
									ob3.setDatatype("varchar(255)");
									ob3.setConstraint("default NULL");
									Tablecolumesaaa.add(ob3);

									TableColumes tb4 = new TableColumes();
									tb4 = new TableColumes();
									tb4.setColumnname("openDate");
									tb4.setDatatype("varchar(255)");
									tb4.setConstraint("default NULL");
									Tablecolumesaaa.add(tb4);

									TableColumes tb5 = new TableColumes();
									tb5 = new TableColumes();
									tb5.setColumnname("openTime");
									tb5.setDatatype("varchar(255)");
									tb5.setConstraint("default NULL");
									Tablecolumesaaa.add(tb5);

									TableColumes tb6 = new TableColumes();
									tb6 = new TableColumes();
									tb6.setColumnname("userName");
									tb6.setDatatype("varchar(255)");
									tb6.setConstraint("default NULL");
									Tablecolumesaaa.add(tb6);

									TableColumes tb7 = new TableColumes();
									tb7 = new TableColumes();
									tb7.setColumnname("deptFlag");
									tb7.setDatatype("varchar(255)");
									tb7.setConstraint("default NULL");
									Tablecolumesaaa.add(tb7);

									TableColumes tb8 = new TableColumes();
									tb8 = new TableColumes();
									tb8.setColumnname("adminFlag");
									tb8.setDatatype("varchar(255)");
									tb8.setConstraint("default NULL");
									Tablecolumesaaa.add(tb8);

									TableColumes tb9 = new TableColumes();
									tb9 = new TableColumes();
									tb9.setColumnname("dept_feed_stat_id");
									tb9.setDatatype("varchar(255)");
									tb9.setConstraint("default NULL");
									Tablecolumesaaa.add(tb9);

									int maxId = cbt.getMaxId("feedbackdata", connectionSpace);
									boolean tableCreated = cbt.createTable22("feedback_ticket", Tablecolumesaaa, connectionSpace);
									if (tableCreated)
									{
										List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
										InsertDataTable ob4 = null;
										ob4 = new InsertDataTable();
										ob4.setColName("feedTicketNo");
										ob4.setDataName(ticketNo);
										insertHistory.add(ob4);
										ob4 = new InsertDataTable();

										ob4.setColName("feed_stat_id");
										ob4.setDataName(id4);
										insertHistory.add(ob4);
										ob4 = new InsertDataTable();

										ob4.setColName("feed_data_id");
										ob4.setDataName(maxId);
										insertHistory.add(ob4);
										ob4 = new InsertDataTable();
										ob4.setColName("openDate");
										ob4.setDataName(openDate);
										insertHistory.add(ob4);

										ob4 = new InsertDataTable();
										ob4.setColName("openTime");
										ob4.setDataName(openTime);
										ob4.setColName("userName");
										ob4.setDataName(userName);
										insertHistory.add(ob4);
										tableCreated = cbt.insertIntoTable("feedback_ticket", insertHistory, connectionSpace);
										// addActionMessage(
										// "Your feedback added Successfully!!!"
										// );
										String allotToId = helper.getTicketAllotToEmpId(String.valueOf(adminDeptId), "Level1", location, "2", connectionSpace);
										for (InsertDataTable i : insertDataIntoRating)
										{
											if (!i.getDataName().equals("0") && !i.getColName().equalsIgnoreCase("max_id_feeddbackdata") && !i.getColName().equalsIgnoreCase("choseHospital"))
											{
												String subCat = helper.getRatingDetails(connectionSpace, i.getColName());
												if (subCat != null)
												{
													ticketNo=helper.getLatestTicketNoForRating(connectionSpace, ticketNo);
													String[] ticketArray = helper.getDeptDetailsForSubCat(subCat, i.getDataName().toString(), connectionSpace);

													tableCreated = helper.addFeedbackRating(ticketNo,maxId, location, userName, "Paper", allotToId, ticketArray[0], ticketArray[1], String.valueOf(adminDeptId), emailId, clientId, name, "", mobileNo, "",patientId, connectionSpace);
												}
											}
										}
										getFeedbackAddMessage(clientId);
										return SUCCESS;
									}
								}
							}
						}
					}
				}
				else
				{
					addActionError("Oops There is some error in data!");
					return ERROR;
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method leadGenerationAdd of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		addActionMessage("Your feedback added Successfully!!!");
		return SUCCESS;
	}

	public void getFeedbackAddMessage(String clientId)
	{
		todayNegative = "0";
		todayPositive = "0";
		StringBuilder buffer = new StringBuilder("select count(*),targetOn from feedbackdata where openDate='" + DateUtil.getCurrentDateUSFormat() + "' group by targetOn ");
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null && object[0] != null && object[1] != null)
				{
					if (object[1].toString().equalsIgnoreCase("No"))
					{
						todayNegative = (object[0].toString());
					}
					else if (object[1].toString().equalsIgnoreCase("Yes"))
					{
						todayPositive = (object[0].toString());
					}
				}
			}
		}
		// System.out.println(""+todayNegative+">>>Positive<<<<"+todayPositive);
		todayTotal = String.valueOf(Integer.parseInt(todayPositive) + Integer.parseInt(todayNegative));
	}

	public String beforeFeedbackView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			if (getModifyFlag().equalsIgnoreCase("0")) setModifyFlag("false");
			else setModifyFlag("true");
			if (getDeleteFlag().equalsIgnoreCase("0")) setDeleteFlag("false");
			else setDeleteFlag("true");

			if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false")) setMainHeaderName("Feedback >> Total View");
			else if (getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false")) setMainHeaderName("Feedback >> Modify");
			else if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true")) setMainHeaderName("Feedback >> Delete");

			setFeedbackViewProp();
			setFeedBack(getFeedBack());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeTotalFeedView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{

				CRMColumnNames = new ArrayList<GridDataPropertyView>();

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv = new GridDataPropertyView();
				gpv.setColomnName("clientId");
				gpv.setHeadingName("CR No");
				gpv.setAlign("center");
				gpv.setWidth(100);
				CRMColumnNames.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("clientName");
				gpv.setHeadingName("Patient Name");
				gpv.setAlign("center");
				gpv.setWidth(200);
				CRMColumnNames.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("centreCode");
				gpv.setHeadingName("Room No");
				gpv.setAlign("center");
				gpv.setWidth(150);
				CRMColumnNames.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("complaintType");
				gpv.setHeadingName("Type");
				gpv.setAlign("center");
				gpv.setWidth(100);
				CRMColumnNames.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mode");
				gpv.setHeadingName("Mode");
				gpv.setAlign("center");
				gpv.setWidth(100);
				CRMColumnNames.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comments");
				gpv.setHeadingName("Comments");
				gpv.setAlign("center");
				gpv.setWidth(100);
				CRMColumnNames.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("actionComments");
				gpv.setHeadingName("Action Comments");
				gpv.setAlign("center");
				gpv.setWidth(100);
				CRMColumnNames.add(gpv);

				return SUCCESS;
			}
			catch (Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	/**
	 * For Paper Feedback Rating.
	 * */

	public String getPaperFeedbackRating()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				setClientId(getClientId());
				if (getCompType() != null && getCompType().equalsIgnoreCase("OPD"))
				{
					// System.out.println("OPD Form called >>>  ");
					return "opdsuccess";
				}
				else
				{
					// System.out.println("IPD Success >>> ");
					return SUCCESS;
				}
			}
			catch (Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getPaperFeedbacDataInJsonOPD()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid && getClientId() != null)
		{
			try
			{
				paperViewObj = new JSONObject();
				StringBuilder buffer = new StringBuilder("select fpr.ease_Contact, fpr.res_queries, fpr.polndCourt, fpr.regndBill, fpr.wait4Consltnt, fpr.diagnosis,fpr.treatment,fpr.medication,fpr.sampleColl,fpr.labServices,fpr.radiologyServices,fpr.careAtHome,fpr.neurology,fpr.urology,fpr.preHealth,fpr.anyOther,fpr.cleanWashroom,fpr.unkeepFacility,fpr.locndDirSignages,fpr.chemistShop,fpr.waitingAreas,fpr.cafeteria,fpr.parking,fpr.security, fpr.overallService,fpr.targetOn,fpr.choseHospital,fpr.cardiology,fpr.endoscopy from feedback_paper_rating as fpr");
				buffer.append(" left join feedbackdata as fdata on fdata.id = fpr.max_id_feeddbackdata");
				buffer.append(" where fdata.clientId='" + getClientId() + "'");
				buffer.append(" and fpr.max_id_feeddbackdata ='" + getId() + "'");
				buffer.append(" limit 1");
				// System.out.println(""+buffer);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List dataList = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					int totalFrontOffice = 0, totalDoctors = 0, totalDiag = 0, totalHk = 0, totalFacilities = 0;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null)
							{
								paperViewObj.put("ease_Contact", object[0].toString());
								totalFrontOffice += Integer.parseInt(object[0].toString());
							}
							else
							{
								paperViewObj.put("ease_Contact", "NA");
							}

							if (object[1] != null)
							{
								paperViewObj.put("res_queries", object[1].toString());
								totalFrontOffice += Integer.parseInt(object[1].toString());
							}
							else
							{
								paperViewObj.put("res_queries", "NA");
							}

							if (object[2] != null)
							{
								paperViewObj.put("polndCourt", object[2].toString());
								totalFrontOffice += Integer.parseInt(object[2].toString());
							}
							else
							{
								paperViewObj.put("polndCourt", "NA");
							}

							if (object[3] != null)
							{
								paperViewObj.put("regndBill", object[3].toString());
								totalFrontOffice += Integer.parseInt(object[3].toString());
							}
							else
							{
								paperViewObj.put("regndBill", "NA");
							}

							if (object[4] != null)
							{
								paperViewObj.put("wait4Consltnt", object[4].toString());
								totalDoctors += Integer.parseInt(object[4].toString());
							}
							else
							{
								paperViewObj.put("wait4Consltnt", "NA");
							}

							if (object[5] != null)
							{
								paperViewObj.put("diagnosis", object[5].toString());
								totalDoctors += Integer.parseInt(object[5].toString());
							}
							else
							{
								paperViewObj.put("diagnosis", "NA");
							}

							if (object[6] != null)
							{
								paperViewObj.put("treatment", object[6].toString());
								totalDoctors += Integer.parseInt(object[6].toString());
							}
							else
							{
								paperViewObj.put("treatment", "NA");
							}

							if (object[7] != null)
							{
								paperViewObj.put("medication", object[7].toString());
								totalDoctors += Integer.parseInt(object[7].toString());
							}
							else
							{
								paperViewObj.put("medication", "NA");
							}

							if (object[11] != null)
							{
								paperViewObj.put("careAtHome", object[11].toString());
								totalDoctors += Integer.parseInt(object[11].toString());
							}
							else
							{
								paperViewObj.put("careAtHome", "NA");
							}

							if (object[8] != null)
							{
								paperViewObj.put("sampleColl", object[8].toString());
								totalDiag += Integer.parseInt(object[8].toString());
							}
							else
							{
								paperViewObj.put("sampleColl", "NA");
							}

							if (object[9] != null)
							{
								paperViewObj.put("labServices", object[9].toString());
								totalDiag += Integer.parseInt(object[9].toString());
							}
							else
							{
								paperViewObj.put("labServices", "NA");
							}

							if (object[10] != null)
							{
								paperViewObj.put("radiologyServices", object[10].toString());
								totalDiag += Integer.parseInt(object[10].toString());
							}
							else
							{
								paperViewObj.put("radiologyServices", "NA");
							}

							if (object[27] != null)
							{
								paperViewObj.put("cardiology", object[27].toString());
								totalDiag += Integer.parseInt(object[27].toString());
							}
							else
							{
								paperViewObj.put("recommend", "NA");
							}

							if (object[28] != null)
							{
								paperViewObj.put("endoscopy", object[28].toString());
								totalDiag += Integer.parseInt(object[28].toString());
							}
							else
							{
								paperViewObj.put("endoscopy", "NA");
							}

							if (object[12] != null)
							{
								paperViewObj.put("neurology", object[12].toString());
								totalDiag += Integer.parseInt(object[12].toString());
							}
							else
							{
								paperViewObj.put("neurology", "NA");
							}

							if (object[13] != null)
							{
								paperViewObj.put("urology", object[13].toString());
								totalDiag += Integer.parseInt(object[13].toString());
							}
							else
							{
								paperViewObj.put("urology", "NA");
							}

							if (object[14] != null)
							{
								paperViewObj.put("preHealth", object[14].toString());
								totalDiag += Integer.parseInt(object[14].toString());
							}
							else
							{
								paperViewObj.put("preHealth", "NA");
							}

							if (object[15] != null)
							{
								paperViewObj.put("anyOther", object[15].toString());
								totalDiag += Integer.parseInt(object[15].toString());
							}
							else
							{
								paperViewObj.put("anyOther", "NA");
							}

							if (object[16] != null)
							{
								paperViewObj.put("cleanWashroom", object[16].toString());
								totalHk += Integer.parseInt(object[16].toString());
							}
							else
							{
								paperViewObj.put("cleanWashroom", "NA");
							}

							if (object[17] != null)
							{
								paperViewObj.put("unkeepFacility", object[17].toString());
								totalHk += Integer.parseInt(object[17].toString());
							}
							else
							{
								paperViewObj.put("unkeepFacility", "NA");
							}

							if (object[18] != null)
							{
								paperViewObj.put("locndDirSignages", object[18].toString());
								totalFacilities += Integer.parseInt(object[18].toString());
							}
							else
							{
								paperViewObj.put("locndDirSignages", "NA");
							}

							if (object[19] != null)
							{
								paperViewObj.put("chemistShop", object[19].toString());
								totalFacilities += Integer.parseInt(object[19].toString());
							}
							else
							{
								paperViewObj.put("chemistShop", "NA");
							}

							if (object[20] != null)
							{
								paperViewObj.put("waitingAreas", object[20].toString());
								totalFacilities += Integer.parseInt(object[20].toString());
							}
							else
							{
								paperViewObj.put("waitingAreas", "NA");
							}

							if (object[21] != null)
							{
								paperViewObj.put("cafeteria", object[21].toString());
								totalFacilities += Integer.parseInt(object[21].toString());
							}
							else
							{
								paperViewObj.put("cafeteria", "NA");
							}

							if (object[22] != null)
							{
								paperViewObj.put("parking", object[22].toString());
								totalFacilities += Integer.parseInt(object[22].toString());
							}
							else
							{
								paperViewObj.put("parking", "NA");
							}

							if (object[23] != null)
							{
								paperViewObj.put("security", object[23].toString());
								totalFacilities += Integer.parseInt(object[23].toString());
							}
							else
							{
								paperViewObj.put("security", "NA");
							}

							if (object[24] != null)
							{
								paperViewObj.put("overallService", object[24].toString());
								totalFacilities += Integer.parseInt(object[24].toString());
							}
							else
							{
								paperViewObj.put("overallService", "NA");
							}

							if (object[25] != null)
							{
								paperViewObj.put("targetOn", object[25].toString());
							}
							else
							{
								paperViewObj.put("targetOn", "NA");
							}
							// Chose Hospital
							if (object[26] != null)
							{
								paperViewObj.put("choseHospital", object[26].toString());
							}
							else
							{
								paperViewObj.put("choseHospital", "NA");
							}

							// Setting Avg For Front Office / Call Center
							if (totalFrontOffice != 0)
							{
								// System.out.println("totalFrontOffice ias as"+
								// totalFrontOffice);
								double avgFrontOffice = totalFrontOffice / 4;
								// System.out.println("avgFrontOfficeis as "+
								// avgFrontOffice);
								paperViewObj.put("avgFronOffice", avgFrontOffice);
							}

							if (totalDoctors != 0)
							{
								double avgDoct = totalDoctors / 5;
								paperViewObj.put("avgDoctotrs", avgDoct);
							}

							if (totalDiag != 0)
							{
								double avgDiag = totalDiag / 9;
								paperViewObj.put("avgDiag", avgDiag);
							}

							if (totalHk != 0)
							{
								double avgHk = totalHk / 2;
								paperViewObj.put("avgHosekeeping", avgHk);
							}

							if (totalFacilities != 0)
							{
								double avgTotal = totalFacilities / 7;
								paperViewObj.put("avgOtherFacilities", avgTotal);
							}
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getPaperDataForIdInJson of class " + getClass(), e);
				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}

	}

	public String getPaperFeedbacDataInJson()
	{
		// System.out.println("Hello getPaperFeedbacDataInJson");
		int avgfrontoffice = 0, avgdoctor = 0, avgnurses = 0, avgHousekeeping = 0, avgDietetics = 0, avgDischarge = 0, avgOther = 0, totalAvg = 0;
		boolean valid = ValidateSession.checkSession();
		if (valid && getClientId() != null)
		{
			try
			{
				paperViewObjIPD = new JSONObject();
				// String type=getPatientId();
				StringBuffer buffer = new StringBuffer(
						"select fpr.responseToQueries, fpr.counselling, fpr.admission, fpr.courtesybehaviour, fpr.attentivePrCaring, fpr.responseCommDoctor,fpr.diagnosis,fpr.treatment,fpr.attentiveprompt,fpr.responseCommunication,fpr.procedures,fpr.medication,fpr.careAtHome,fpr.qualityUpkeep,fpr.behivourResponseTime,fpr.functioningMainenance,fpr.qualityofFoods,fpr.timelineService,fpr.behivourResponse,fpr.assessmentCounseling,fpr.responsetoQuery,fpr.efficiencyBillingdesk,fpr.dischargetime,fpr.pharmacyservices, fpr.cafeteriaDyning,fpr.overallsecurity,fpr.overallservices,fpr.recommend,fpr.choseHospital,fpr.front_ease from feedback_paper_rating as fpr");
				buffer.append(" left join feedbackdata as fdata on fdata.id = fpr.max_id_feeddbackdata");
				buffer.append(" where fdata.clientId='" + getClientId() + "'");
				buffer.append(" and fpr.max_id_feeddbackdata ='" + getId() + "'");
				buffer.append(" limit 1");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				//System.out.println("buffer.toString()    "+buffer.toString());
				List dataList = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{

							if (object[0] != null)
							{
								paperViewObjIPD.put("responseToQueries", object[0].toString());
								avgfrontoffice += Integer.parseInt(object[0].toString());
							}
							else
							{
								paperViewObjIPD.put("responseToQueries", "NA");
							}

							if (object[1] != null)
							{
								paperViewObjIPD.put("counselling", object[1].toString());
								avgfrontoffice += Integer.parseInt(object[1].toString());
							}
							else
							{
								paperViewObjIPD.put("counselling", "NA");
							}

							if (object[2] != null)
							{
								paperViewObjIPD.put("admission", object[2].toString());
								avgfrontoffice += Integer.parseInt(object[2].toString());
							}
							else
							{
								paperViewObjIPD.put("admission", "NA");
							}

							if (object[3] != null)
							{
								paperViewObjIPD.put("courtesybehaviour", object[3].toString());
								avgfrontoffice += Integer.parseInt(object[3].toString());
							}
							else
							{
								paperViewObjIPD.put("courtesybehaviour", "NA");
							}

							if (object[29] != null)
							{
								paperViewObjIPD.put("front_ease", object[29].toString());
								avgfrontoffice += Integer.parseInt(object[29].toString());
							}
							else
							{
								paperViewObjIPD.put("front_ease", "NA");
							}

							if (object[4] != null)
							{
								paperViewObjIPD.put("attentivePrCaring", object[4].toString());
								avgdoctor += Integer.parseInt(object[4].toString());
							}
							else
							{
								paperViewObjIPD.put("attentivePrCaring", "NA");
							}

							if (object[5] != null)
							{
								paperViewObjIPD.put("responseCommDoctor", object[5].toString());
								avgdoctor += Integer.parseInt(object[5].toString());
							}
							else
							{
								paperViewObjIPD.put("responseCommDoctor", "NA");
							}
							if (object[6] != null)
							{
								paperViewObjIPD.put("diagnosis", object[6].toString());
								avgdoctor += Integer.parseInt(object[6].toString());
							}
							else
							{
								paperViewObjIPD.put("diagnosis", "NA");
							}
							if (object[7] != null)
							{
								paperViewObjIPD.put("treatment", object[7].toString());
								avgdoctor += Integer.parseInt(object[7].toString());
							}
							else
							{
								paperViewObjIPD.put("treatment", "NA");
							}
							if (object[8] != null)
							{
								paperViewObjIPD.put("attentiveprompt", object[8].toString());
								avgnurses += Integer.parseInt(object[8].toString());
							}
							else
							{
								paperViewObjIPD.put("attentiveprompt", "NA");
							}
							if (object[9] != null)
							{
								paperViewObjIPD.put("responseCommunication", object[9].toString());
								avgnurses += Integer.parseInt(object[9].toString());
							}
							else
							{
								paperViewObjIPD.put("responseCommunication", "NA");
							}
							if (object[10] != null)
							{
								paperViewObjIPD.put("procedures", object[10].toString());
								avgnurses += Integer.parseInt(object[10].toString());
							}
							else
							{
								paperViewObjIPD.put("procedures", "NA");
							}
							if (object[11] != null)
							{
								paperViewObjIPD.put("medication", object[11].toString());
								avgnurses += Integer.parseInt(object[11].toString());
							}
							else
							{
								paperViewObjIPD.put("medication", "NA");
							}
							if (object[12] != null)
							{
								paperViewObjIPD.put("careAtHome", object[12].toString());
								avgnurses += Integer.parseInt(object[12].toString());
							}
							else
							{
								paperViewObjIPD.put("careAtHome", "NA");
							}
							if (object[13] != null)
							{
								paperViewObjIPD.put("qualityUpkeep", object[13].toString());
								avgHousekeeping += Integer.parseInt(object[13].toString());
							}
							else
							{
								paperViewObjIPD.put("qualityUpkeep", "NA");
							}
							if (object[14] != null)
							{
								paperViewObjIPD.put("behivourResponseTime", object[14].toString());
								avgHousekeeping += Integer.parseInt(object[14].toString());
							}
							else
							{
								paperViewObjIPD.put("behivourResponseTime", "NA");
							}
							if (object[15] != null)
							{
								paperViewObjIPD.put("functioningMainenance", object[15].toString());
								avgHousekeeping += Integer.parseInt(object[15].toString());
							}
							else
							{
								paperViewObjIPD.put("functioningMainenance", "NA");
							}
							if (object[16] != null)
							{
								paperViewObjIPD.put("qualityofFoods", object[16].toString());
								avgDietetics += Integer.parseInt(object[16].toString());
							}
							else
							{
								paperViewObjIPD.put("qualityofFoods", "NA");
							}
							if (object[17] != null)
							{
								paperViewObjIPD.put("timelineService", object[17].toString());
								avgDietetics += Integer.parseInt(object[17].toString());
							}
							else
							{
								paperViewObjIPD.put("timelineService", "NA");
							}
							if (object[18] != null)
							{
								paperViewObjIPD.put("behivourResponse", object[18].toString());
								avgDietetics += Integer.parseInt(object[18].toString());
							}
							else
							{
								paperViewObjIPD.put("behivourResponse", "NA");
							}
							if (object[19] != null)
							{
								paperViewObjIPD.put("assessmentCounseling", object[19].toString());
								avgDietetics += Integer.parseInt(object[19].toString());
							}
							else
							{
								paperViewObjIPD.put("assessmentCounseling", "NA");
							}
							if (object[20] != null)
							{
								paperViewObjIPD.put("responsetoQuery", object[20].toString());
								avgDischarge += Integer.parseInt(object[20].toString());
							}
							else
							{
								paperViewObjIPD.put("responsetoQuery", "NA");
							}
							if (object[21] != null)
							{
								paperViewObjIPD.put("efficiencyBillingdesk", object[21].toString());
								avgDischarge += Integer.parseInt(object[21].toString());
							}
							else
							{
								paperViewObjIPD.put("efficiencyBillingdesk", "NA");
							}
							if (object[22] != null)
							{
								paperViewObjIPD.put("dischargetime", object[22].toString());
								avgDischarge += Integer.parseInt(object[22].toString());
							}
							else
							{
								paperViewObjIPD.put("dischargetime", "NA");
							}
							if (object[23] != null)
							{
								paperViewObjIPD.put("pharmacyservices", object[23].toString());
								avgOther += Integer.parseInt(object[23].toString());
							}
							else
							{
								paperViewObjIPD.put("pharmacyservices", "NA");
							}
							if (object[24] != null)
							{
								paperViewObjIPD.put("cafeteriaDyning", object[24].toString());
								avgOther += Integer.parseInt(object[24].toString());
							}
							else
							{
								paperViewObjIPD.put("cafeteriaDyning", "NA");
							}
							if (object[25] != null)
							{
								paperViewObjIPD.put("overallsecurity", object[25].toString());
								avgOther += Integer.parseInt(object[25].toString());
							}
							else
							{
								paperViewObjIPD.put("overallsecurity", "NA");
							}
							if (object[26] != null)
							{
								paperViewObjIPD.put("overallservices", object[26].toString());
								avgOther += Integer.parseInt(object[26].toString());
							}
							else
							{
								paperViewObjIPD.put("overallservices", "NA");
							}
							if (object[27] != null)
							{
								paperViewObjIPD.put("recommend", object[27].toString());
							}
							else
							{
								paperViewObjIPD.put("recommend", "NA");
							}
							if (object[28] != null)
							{
								paperViewObjIPD.put("choseHospital", object[28].toString());
							}
							else
							{
								paperViewObjIPD.put("choseHospital", "NA");
							}

						}
					}
					paperViewObjIPD.put("avgFronOffice", Integer.toString(avgfrontoffice / 5));
					paperViewObjIPD.put("avgDoctotrs", Integer.toString(avgdoctor / 4));
					paperViewObjIPD.put("avgNurses", Integer.toString(avgnurses / 5));
					paperViewObjIPD.put("avgHosekeeping", Integer.toString(avgHousekeeping / 3));
					paperViewObjIPD.put("avgDiatics", Integer.toString(avgDietetics / 4));
					paperViewObjIPD.put("avgDischarge", Integer.toString(avgDischarge / 3));
					paperViewObjIPD.put("avgOthers", Integer.toString(avgOther / 4));
					// totalAvg +=
					// avgfrontoffice+avgdoctor+avgnurses+avgHousekeeping
					// +avgDietetics+avgDischarge+avgOther;
					totalAvg += (avgfrontoffice / 5) + (avgdoctor / 4) + (avgnurses / 5) + (avgHousekeeping / 3) + (avgDietetics / 4) + (avgDischarge / 3) + (avgOther / 4);
					paperViewObjIPD.put("totalAverage", Integer.toString(totalAvg / 7));
				}

				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getPaperDataForIdInJson of class " + getClass(), e);
				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}

	}

	/**
	 * Method to show data for PCC.
	 * */
	public String beforePccFeedbackView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				actionNameMap = new HashMap<String, String>();
				StringBuilder buffer = new StringBuilder("select id,name from actioninfo where id='1' order by name asc");
				List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					CounterPojo pojo = new CounterPojo();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								actionNameMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
		}
		return SUCCESS;
	}

	public String viewPccFeedbackInGridPaper()
	{
		//System.out.println("show data>>viewPccFeedbackInGridPaper>>>>>>>>>>>>>"
		// );
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String deptLevel = (String) session.get("userDeptLevel");
			String userType = (String) session.get("userTpe");
			String loggedEmpId = "";
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					loggedEmpId = object[5].toString();
				}
			}

			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from feedbackdata");
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				query.append(" feeddata.id,feeddata.centerCode,feeddata.serviceBy,feeddata.mode,");
				query.append(" feeddata.userName,feeddata.status,pinfo.visit_type,feedbck.resolve_date,feedbck.resolve_time,");
				query.append(" feedbck.resolve_duration,feedbck.feed_brief,feeddata.compType, feedbck.resolve_remark, feedbck.spare_used from feedbackdata as feeddata");
				query.append(" left join feedback_ticket as feedbt on feedbt.feed_data_id=feeddata.id");
				query.append(" left join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id");
				query.append(" left join patientinfo as pinfo on pinfo.cr_number=feeddata.clientId");
				if (getClientId() != null && !getClientId().equalsIgnoreCase(""))
				{
					query.append(" and feeddata.clientId = '" + getClientId() + "'");
				}

				if (getCompType() != null && !getCompType().equalsIgnoreCase(""))
				{
					query.append(" and feeddata.compType = '" + getCompType() + "'");
				}

				if (userType != null && !userType.equalsIgnoreCase("M"))
				{
					query.append(" and feedData.handledBy='" + loggedEmpId + "' or feedData.userName='" + userName + "' ");
				}
				query.append(" order by feedData.id DESC");

				// System.out.println("query>>>>>>"+query.toString());
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{

					query.append(" &&  ");
					// add search query based on the search operation
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
				FeedbackHelper helper = new FeedbackHelper();
				String mobNo = null;
				// System.out.println("Paper >>>"+query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				List<Object> Listhb = new ArrayList<Object>();
				if (data != null)
				{

					Collections.reverse(data);
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", obdata[0].toString());
						one.put("centerCode", obdata[1].toString());
						one.put("serviceBy", obdata[2].toString());
						one.put("mode", obdata[3].toString());
						one.put("status", obdata[5].toString());
						if (obdata[6] != null)
						{
							one.put("visit_type", obdata[6].toString());
						}
						else
						{
							one.put("visit_type", "NA");
						}

						if (obdata[7] != null)
						{
							one.put("resolve_date", DateUtil.convertDateToIndianFormat(obdata[7].toString()) + " " + obdata[8].toString());
						}
						else
						{
							one.put("resolve_date", "NA");
						}

						if (obdata[9] != null)
						{
							one.put("resolve_duration", obdata[9].toString());
						}
						else
						{
							one.put("resolve_duration", "NA");
						}

						// one.put("purOfVisit", obdata[7].toString());

						// one.put("fbType", obdata[6].toString());
						// one.put("centerName", obdata[7].toString());
						// one.put("categoryName", obdata[8].toString());
						// one.put("subCategoryName", obdata[9].toString());
						if (obdata[10] != null)
						{
							one.put("feed_brief", obdata[10].toString());
						}
						else
						{
							one.put("feed_brief", "NA");
						}

						one.put("compType", obdata[11].toString());

						// one.put("openDate", obdata[3].toString());

						if (obdata[12] != null)
						{
							one.put("resolve_remark", obdata[12].toString());
						}
						else
						{
							one.put("resolve_remark", "NA");
						}
						if (obdata[13] != null)
						{
							one.put("spare_used", obdata[13].toString());
						}
						else
						{
							one.put("spare_used", "NA");
						}

						one.put("action", "Take Action");
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String getPccDataForIdInJson()
	{
		// System.out.println("In getPccDataForIdInJson>>>");
		boolean valid = ValidateSession.checkSession();
		if (valid && getClientId() != null)
		{
			try
			{
				// patObj=new PatientInfo();
				patObj1 = new JSONObject();
				// String type=getPatientId();
				StringBuffer buffer = new StringBuffer("select pinf.patient_name, pinf.mobile_no, pinf.admit_consultant, pinf.visit_type, pinf.patient_type, pinf.station from patientinfo as pinf");
				buffer.append(" where cr_number='" + getClientId() + "' and pinf.patient_type='IPD'");
				buffer.append(" order by id desc limit 1");
				// System.out.println("Querry for MRD No is as " + buffer);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List dataList = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				// System.out.println("Size is as "+dataList.size());
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							patObj1.put("patientExists", "Yes");
							if (object[0] != null)
							{
								patObj1.put("patientName", object[0].toString());
							}
							else
							{
								patObj1.put("patientName", "NA");
							}

							if (object[1] != null)
							{
								patObj1.put("patientMobileNo", object[1].toString());
							}
							else
							{
								patObj1.put("patientMobileNo", "NA");
							}

							if (object[2] != null)
							{
								patObj1.put("doctorName", object[2].toString());
							}
							else
							{
								patObj1.put("doctorName", "NA");
							}

							if (object[3] != null)
							{
								patObj1.put("purOfVisit", object[3].toString());
							}
							else
							{
								patObj1.put("purOfVisit", "NA");
							}

							if (object[4] != null)
							{
								patObj1.put("patientType", object[4].toString());
							}
							else
							{
								patObj1.put("patientType", "NA");
							}

							if (object[5] != null)
							{
								patObj1.put("roomNo", object[5].toString());
							}
							else
							{
								patObj1.put("roomNo", "NA");
							}
						}
					}
					return SUCCESS;
				}
				else
				{
					patObj1.put("patientExists", "No");
					return SUCCESS;
				}

			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getPccDataForIdInJson of class " + getClass(), e);
				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String beforepccPatientView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) { return LOGIN; }

			setPatType(getPatType());
			speciality = new HashMap<String, String>();
			FeedbackHelper helper = new FeedbackHelper();
			speciality = helper.getDistinctColVal(connectionSpace, "centreName");

			treatedBy = helper.getDistinctColVal(connectionSpace, "serviceBy");
			feedbackActionStatus = helper.getDistinctColVal(connectionSpace, "status");

			name = helper.getPatientName(connectionSpace, getClientId());
			mobileNo = helper.getMobileNumber(connectionSpace, getClientId());
			count = helper.getCount(connectionSpace, getClientId());
			if (count == null || count.equalsIgnoreCase("NA") || count.equalsIgnoreCase("0"))
			{
				count = "There is no Feedback History Avaialble for this MRD No.";
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), e);
			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	public String beforepccFeedbackView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			if (getModifyFlag().equalsIgnoreCase("0")) setModifyFlag("false");
			else setModifyFlag("true");
			if (getDeleteFlag().equalsIgnoreCase("0")) setDeleteFlag("false");
			else setDeleteFlag("true");

			if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false")) setMainHeaderName("Feedback >> Total View");
			else if (getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false")) setMainHeaderName("Feedback >> Modify");
			else if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true")) setMainHeaderName("Feedback >> Delete");

			// setFeedbackViewProp();
			setPccFeedbackViewProp();
			setFeedBack(getFeedBack());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPaperFeedbackData()
	{
		setClientId(getClientId());
		return SUCCESS;
	}

	public void setPccFeedbackViewProp()
	{
		try
		{
			masterViewProp.clear();

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("compType");
			gpv.setHeadingName("Patient Type");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("visitDate");
			gpv.setHeadingName("Visit Date");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("centerCode");
			gpv.setHeadingName("Room No");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("purOfVisit");
			gpv.setHeadingName("Purpose Of Visit");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setKey("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("serviceBy");
			gpv.setHeadingName("Treating Doctor");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("mode");
			gpv.setHeadingName("FeedBack Mode");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("takeAction");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("fbType");
			gpv.setHeadingName("Type");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("centerName");
			gpv.setHeadingName("Dept");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("categoryName");
			gpv.setHeadingName("Category");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("subCategoryName");
			gpv.setHeadingName("Sub-Category");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_brief");
			gpv.setHeadingName("Brief");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("openDate");
			gpv.setHeadingName("Date");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("RCA");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("CAPA");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Closed On & At");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Total Time Taken");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getFeedbackForAction()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				// System.out.println("FeedbackData Id is as "+getId());
				actionNameMap = new HashMap<String, String>();

				StringBuilder bufr = new StringBuilder("select clientId,clientName,mobNo,emailId,actionComments,openDate,centerCode from feedbackdata where id='" + getId() + "'");
				List dataList = cbt.executeAllSelectQuery(bufr.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null)
							{
								setCrNo(object[0].toString());
							}
							else
							{
								setCrNo("NA");
							}

							if (object[1] != null)
							{
								setPatName(object[1].toString());
							}
							else
							{
								setPatName("NA");
							}

							if (object[2] != null)
							{
								setMobNo(object[2].toString());
							}
							else
							{
								setMobNo("NA");
							}

							if (object[3] != null)
							{
								setEmailId(object[3].toString());
							}
							else
							{
								setEmailId("NA");
							}

							if (object[4] != null)
							{
								setObservation(object[4].toString());
							}
							else
							{
								setObservation("NA");
							}

							if (object[5] != null)
							{
								setOpenDate(object[5].toString());
							}
							else
							{
								setOpenDate("NA");
							}

							if (object[6] != null)
							{
								setRoomNo(object[6].toString());
							}
							else
							{
								setRoomNo("NA");
							}
						}
					}
				}

				StringBuilder buffer = new StringBuilder("select id,name from actioninfo order by name asc");
				List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					CounterPojo pojo = new CounterPojo();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								actionNameMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setFeedbackViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionCounter");
			gpv.setHeadingName("Count");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("feedbackDetails");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("action");
			gpv.setHeadingName("Action");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("takeAction");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedTicketId");
			gpv.setHeadingName("Ticket Id");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			gpv.setWidth(50);
			gpv.setKey("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticketNo");
			gpv.setHeadingName("Ticket No");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_feedback_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_data_configuration");

			for (GridDataPropertyView gdp : returnResult)
			{
				/*
				 * //if(gdp.getColomnName().equalsIgnoreCase("tabId") ||
				 * gdp.getColomnName().equalsIgnoreCase("handledBy") ||
				 * gdp.getColomnName().equalsIgnoreCase("cleanliness") ||
				 * gdp.getColomnName().equalsIgnoreCase("billingServices") ||
				 * gdp.getColomnName().equalsIgnoreCase("overall") ||
				 * gdp.getColomnName().equalsIgnoreCase("other") ||
				 * gdp.getColomnName().equalsIgnoreCase("security") ||
				 * gdp.getColomnName().equalsIgnoreCase("waitingAreas") ||
				 * gdp.getColomnName().equalsIgnoreCase("ambience") ||
				 * gdp.getColomnName
				 * ().equalsIgnoreCase("availabilityofmedicines") ||
				 * gdp.getColomnName().equalsIgnoreCase("availabilityofreports")
				 * || gdp.getColomnName().equalsIgnoreCase("billingexperience")
				 * || gdp.getColomnName().equalsIgnoreCase("cafeteria") ||
				 * gdp.getColomnName().equalsIgnoreCase("cleanlinessofhospital")
				 * ||
				 * gdp.getColomnName().equalsIgnoreCase("courtesyoffrontstaff")
				 * || gdp.getColomnName().equalsIgnoreCase("courtesyofstaff") ||
				 * gdp.getColomnName().equalsIgnoreCase("doctorwaitingtime") ||
				 * gdp.getColomnName().equalsIgnoreCase("instructionsofStaff")
				 * || gdp.getColomnName().equalsIgnoreCase("makingappointment")
				 * || gdp.getColomnName().equalsIgnoreCase("nursingCare") ||
				 * gdp.getColomnName().equalsIgnoreCase("otherFacilities") ||
				 * gdp.getColomnName().equalsIgnoreCase("overallnursing") ||
				 * gdp.getColomnName().equalsIgnoreCase("parkingservices") ||
				 * gdp.getColomnName().equalsIgnoreCase("promptnessofstaff") ||
				 * gdp.getColomnName().equalsIgnoreCase("refertoothers") ||
				 * gdp.getColomnName().equalsIgnoreCase("resultSatis") ||
				 * gdp.getColomnName().equalsIgnoreCase("staffBehaviour") ||
				 * gdp.getColomnName().equalsIgnoreCase("timegivenbythedoctor")
				 * || gdp.getColomnName().equalsIgnoreCase("treatment") ) {
				 * 
				 * } else
				 */

				{
					// System.out.println("Mode is as "+getMode());
					if (getMode() != null && !getMode().equalsIgnoreCase("Paper"))
					{
						if (getMode() != null && getMode().equalsIgnoreCase("SMS"))
						{
							if (!gdp.getColomnName().equalsIgnoreCase("refNo"))
							{
								gpv = new GridDataPropertyView();
								gpv.setColomnName(gdp.getColomnName());
								gpv.setHeadingName(gdp.getHeadingName());
								gpv.setEditable(gdp.getEditable());
								gpv.setSearch(gdp.getSearch());
								gpv.setHideOrShow(gdp.getHideOrShow());
								gpv.setFormatter(gdp.getFormatter());
								gpv.setWidth(gdp.getWidth());
								masterViewProp.add(gpv);
							}
						}
						else
						{
							if (!gdp.getColomnName().equalsIgnoreCase("tabId") && !gdp.getColomnName().equalsIgnoreCase("refNo") && !gdp.getColomnName().equalsIgnoreCase("choseHospital") && !gdp.getColomnName().equalsIgnoreCase("overAll") && !gdp.getColomnName().equalsIgnoreCase("recommend") && !gdp.getColomnName().equalsIgnoreCase("comments") && !gdp.getColomnName().equalsIgnoreCase("blkId") && !gdp.getColomnName().equalsIgnoreCase("nameEmp") && !gdp.getColomnName().equalsIgnoreCase("appFor"))
							{
								gpv = new GridDataPropertyView();
								gpv.setColomnName(gdp.getColomnName());
								gpv.setHeadingName(gdp.getHeadingName());
								gpv.setEditable(gdp.getEditable());
								gpv.setSearch(gdp.getSearch());
								gpv.setHideOrShow(gdp.getHideOrShow());
								gpv.setFormatter(gdp.getFormatter());
								gpv.setWidth(gdp.getWidth());
								masterViewProp.add(gpv);
							}
						}
					}
					else
					{
						// System.out.println("gdp.getColomnName() isa as "+gdp.
						// getColomnName());
						if (!gdp.getColomnName().equalsIgnoreCase("tabId"))
						{
							gpv = new GridDataPropertyView();
							gpv.setColomnName(gdp.getColomnName());
							gpv.setHeadingName(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							gpv.setFormatter(gdp.getFormatter());
							gpv.setWidth(gdp.getWidth());
							masterViewProp.add(gpv);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// System.out.println("masterViewProp size is as :::::"+masterViewProp.
		// size());
	}

	public String beforeCustomerNegView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getFlage().equalsIgnoreCase("negative") && getSearchString().equalsIgnoreCase("takeAction"))
				{
					setMainHeaderName("Feedback >>Take Action ");
					setGridColomnNamesNegative();
				}
				else if (getFlage().equalsIgnoreCase("negative"))
				{
					setMainHeaderName("Feedback >>Negative Response ");
					setGridColomnNamesNegative();

					/*
					 * speciality=new HashMap<String, String>(); FeedbackHelper
					 * helper=new FeedbackHelper();
					 * speciality=helper.getDistinctColVal
					 * (connectionSpace,"centreName");
					 * 
					 * treatedBy=helper.getDistinctColVal(connectionSpace,
					 * "serviceBy");
					 */

				}
				else if (getFlage().equalsIgnoreCase("positive"))
				{
					setMainHeaderName("Feedback >>Positive Response ");
					// System.out.println("Positive Feedback");
					setFeedbackViewProp();
				}
				else
				{
					setMainHeaderName("Feedback >>Positive Response ");
					setGridColomnNamesPositive();
				}

				setFlage(getFlage());
				setMode(getMode());
				setFromDate(getFromDate());
				setToDate(getToDate());
				setSpec(getSpec());
				setDocName(getDocName());

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

	public void setGridColomnNamesPositive()
	{
		CRMColumnNames = new ArrayList<GridDataPropertyView>();

		GridDataPropertyView gpv = new GridDataPropertyView();

		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientId");
		gpv.setHeadingName("Patient Id");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientName");
		gpv.setHeadingName("Patient Name");
		gpv.setAlign("center");
		gpv.setWidth(200);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("centreCode");
		gpv.setHeadingName("Room No");
		gpv.setAlign("center");
		gpv.setWidth(150);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("mobNo");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emailId");
		gpv.setHeadingName("Email");
		gpv.setAlign("center");
		gpv.setWidth(200);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("problem");
		gpv.setHeadingName("Observation");
		gpv.setAlign("center");
		gpv.setWidth(250);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("complaintType");
		gpv.setHeadingName("Type");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("remarks");
		gpv.setHeadingName("Remarks");
		gpv.setAlign("center");
		gpv.setWidth(200);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("mode");
		gpv.setHeadingName("Mode");
		gpv.setAlign("center");
		gpv.setWidth(200);
		CRMColumnNames.add(gpv);

	}

	public String getActionTakenFeedback()
	{
		boolean flag = ValidateSession.checkSession();
		if (flag)
		{
			try
			{
				if (getSearchString() != null && getSearchString().equalsIgnoreCase("actionTaken"))
				{
					setGridColomnNamesNegative();
					// System.out.println("Hello"+CRMColumnNames.size());
					setFlage("actionTaken");
					return SUCCESS;
				}
				else
				{
					return SUCCESS;
				}
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

	public void setGridColomnNamesNegative()
	{
		CRMColumnNames = new ArrayList<GridDataPropertyView>();

		GridDataPropertyView gpv = new GridDataPropertyView();

		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		CRMColumnNames.add(gpv);

		gpv.setColomnName("feedback_ticketId");
		gpv.setHeadingName("id");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("actionCounter");
		gpv.setHeadingName("Count");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(40);
		gpv.setFormatter("actionDetails");
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("action");
		gpv.setHeadingName("Action");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(80);
		gpv.setFormatter("ticketNo2");
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(80);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticketNo");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(80);
		if (getSearchString() != null && getSearchString().equalsIgnoreCase("actionTaken"))
		{

		}
		else
		{
			// gpv.setFormatter("ticketNo2");
		}
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientId");
		gpv.setHeadingName("MRD No");
		gpv.setAlign("center");
		gpv.setWidth(60);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("patientId");
		gpv.setHeadingName("Patient Id");
		gpv.setAlign("center");
		gpv.setWidth(60);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientName");
		gpv.setHeadingName("Patient Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("complaintType");
		gpv.setHeadingName("Patient Type");
		gpv.setAlign("center");
		gpv.setWidth(80);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("centreCode");
		gpv.setHeadingName("Room No");
		gpv.setAlign("center");
		gpv.setWidth(55);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("mobNo");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(80);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emailId");
		gpv.setHeadingName("Email");
		gpv.setAlign("center");
		gpv.setWidth(140);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("serviceBy");
		gpv.setHeadingName("Treating Doctor");
		gpv.setAlign("center");
		gpv.setWidth(140);
		CRMColumnNames.add(gpv);

		/*
		 * gpv=new GridDataPropertyView(); gpv.setColomnName("refNo");
		 * gpv.setHeadingName("Reference No"); gpv.setAlign("center");
		 * gpv.setWidth(61); CRMColumnNames.add(gpv);
		 * 
		 * gpv=new GridDataPropertyView(); gpv.setColomnName("centreName");
		 * gpv.setHeadingName("Speciality"); gpv.setAlign("center");
		 * gpv.setWidth(80); CRMColumnNames.add(gpv);
		 */

		/*
		 * gpv=new GridDataPropertyView(); gpv.setColomnName("level");
		 * gpv.setHeadingName("Level"); gpv.setAlign("center");
		 * gpv.setWidth(50); CRMColumnNames.add(gpv);
		 */

		gpv = new GridDataPropertyView();
		gpv.setColomnName("openDate");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(80);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("openTime");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(60);
		CRMColumnNames.add(gpv);

		/*
		 * gpv=new GridDataPropertyView(); gpv.setColomnName("escalationDate");
		 * gpv.setHeadingName("Esc Date"); gpv.setAlign("center");
		 * gpv.setWidth(70); CRMColumnNames.add(gpv);
		 * 
		 * gpv=new GridDataPropertyView(); gpv.setColomnName("escalationTime");
		 * gpv.setHeadingName("Esc Time"); gpv.setAlign("center");
		 * gpv.setWidth(65); CRMColumnNames.add(gpv);
		 */

		gpv = new GridDataPropertyView();
		gpv.setColomnName("mode");
		gpv.setHeadingName("Mode");
		gpv.setAlign("center");
		gpv.setWidth(50);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("handledBy");
		gpv.setHeadingName("Handled By");
		gpv.setAlign("center");
		gpv.setWidth(50);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("userName");
		gpv.setHeadingName("User");
		gpv.setAlign("center");
		gpv.setWidth(50);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("companytype");
		gpv.setHeadingName("Company Type");
		gpv.setAlign("center");
		gpv.setWidth(80);
		CRMColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("discharge_datetime");
		gpv.setHeadingName("Discharged On");
		gpv.setAlign("center");
		gpv.setWidth(80);
		CRMColumnNames.add(gpv);

		if (getSearchString() != null && getSearchString().equalsIgnoreCase("actionTaken"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionTaken");
			gpv.setHeadingName("Action Taken");
			gpv.setAlign("center");
			gpv.setWidth(70);
			CRMColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionDate");
			gpv.setHeadingName("Action Date");
			gpv.setAlign("center");
			gpv.setWidth(70);
			CRMColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionTime");
			gpv.setHeadingName("Action Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			CRMColumnNames.add(gpv);
		}
	}

	public String beforeCustomerPostiveView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getFlage() != null && getFlage().equalsIgnoreCase("positive")) setMainHeaderName("Feedback >>Positive Response ");
				else
				{
					setMainHeaderName("Feedback >> Response ");
				}
				setGridColomnNamesPositive();

				fromDate = DateUtil.getNextDateAfter(-6);
				toDate = DateUtil.getCurrentDateUSFormat();

				speciality = new HashMap<String, String>();
				FeedbackHelper helper = new FeedbackHelper();
				speciality = helper.getDistinctColVal(connectionSpace, "centreName");

				treatedBy = helper.getDistinctColVal(connectionSpace, "serviceBy");

				feedbackActionStatus = helper.getDistinctColVal(connectionSpace, "status");

				totalPos = helper.getFeedbackCounters("", "Yes", connectionSpace);
				posSMS = helper.getFeedbackCounters("SMS", "Yes", connectionSpace);

				setFlage(getFlage());
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

	public String viewFeedbackInGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from feedbackdata");
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1) query.append(obdata.toString() + ",");
						else query.append(obdata.toString());
					}
					i++;

				}
				query.append(" from feedbackdata ");
				if (getMode() != null)
				{
					query.append(" where mode like '" + getMode() + "'");
					if (getSearchFor() != null)
					{
						if (getSearchFor().equalsIgnoreCase("yesterdayYes"))
						{
							query.append("  and (targetOn='Yes' && openDate='" + DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()) + "' )");
						}
						else if (getSearchFor().equalsIgnoreCase("yesterdayNo"))
						{
							query.append(" and (targetOn='No' && openDate='" + DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()) + "' )");
						}
						else if (getSearchFor().equalsIgnoreCase("todayYes"))
						{
							query.append(" and ( targetOn='Yes' && openDate='" + DateUtil.getCurrentDateUSFormat() + "' )");
						}
						else if (getSearchFor().equalsIgnoreCase("todayNo"))
						{
							query.append(" and ( targetOn='No' && openDate='" + DateUtil.getCurrentDateUSFormat() + "' )");
						}
						else if (getSearchFor().equalsIgnoreCase("totalYes"))
						{
							query.append(" and targetOn='Yes'");
						}
						else if (getSearchFor().equalsIgnoreCase("totalNo"))
						{
							query.append(" and targetOn='No'");
						}
					}
				}
				else if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{

					query.append(" where ");
					// add search query based on the search operation
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
				// System.out.println(getMode()+"queryqueryqueryquery"+query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					Collections.reverse(data);
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
									if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("openDate"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("openTime"))
									{
										one.put(fieldNames.get(k).toString(), (obdata[k].toString().substring(0, 5)));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("handledBy"))
									{
										String handledBy = new HelpdeskUniversalAction().getData("employee_basic", "empName", "id", obdata[k].toString());
										if (handledBy != null)
										{
											one.put(fieldNames.get(k).toString(), (handledBy));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), ("NA"));
										}
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("handledBy"))
									{
										String handledBy = new HelpdeskUniversalAction().getData("employee_basic", "empName", "id", obdata[k].toString());
										if (handledBy != null)
										{
											one.put(fieldNames.get(k).toString(), (handledBy));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), ("NA"));
										}
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("targetOn"))
									{
										if (obdata[k] != null && obdata[k].toString().equalsIgnoreCase("No"))
										{
											one.put(fieldNames.get(k).toString(), "Negative");
										}
										else if (obdata[k] != null && obdata[k].toString().equalsIgnoreCase("Yes"))
										{
											one.put(fieldNames.get(k).toString(), "Positive");
										}
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}

							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewFeedbackInGridPaper()
	{

		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) { return LOGIN; }
			String empId = null;
			String deptLevel = (String) session.get("userDeptLevel");
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);

			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empId = object[5].toString();
				}
			}

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from feedbackdata");
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
				// fieldNames.add("status");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1) query.append("feeddata." + obdata.toString() + ",");
						else query.append("feeddata." + obdata.toString());
					}
					i++;

				}
				query.append(" ,feedbt.id as feedTicketId,feedbt.feedTicketNo from feedbackdata as feeddata" + " inner join feedback_ticket as feedbt on feedbt.feed_data_id=feeddata.id " + " inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id " + " where feeddata.mode like 'Paper%'");

				if (userType != null)
				{
					if (userType.equalsIgnoreCase("N") && empId != null)
					{
						query.append(" and (feeddata.handledBy='" + empId + "' or feeddata.userName='" + userName + "')");
					}
					else
					{

					}
				}
				if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" and feeddata.openDate between '" + ((getFromDate())) + "' and '" + ((getToDate())) + "'");
					}
					else
					{
						query.append(" and feeddata.openDate between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' and '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
					}
				}
				else
				{
					query.append(" and feeddata.openDate between '" + (DateUtil.getNextDateAfter(-6)) + "' and '" + (DateUtil.getCurrentDateUSFormat()) + "'");
				}

				if (getPatType() != null && !getPatType().equalsIgnoreCase("-1"))
				{
					query.append(" and feeddata.compType = '" + getPatType() + "'");
				}

				if (getSpec() != null && !getSpec().equalsIgnoreCase("-1"))
				{
					query.append(" and feeddata.centreName = '" + getSpec() + "'");
				}

				if (getDocName() != null && !getDocName().equalsIgnoreCase("-1"))
				{
					query.append(" and feeddata.serviceBy = '" + getDocName() + "'");
				}

				if (getFeedBack() != null && !getFeedBack().equalsIgnoreCase("-1"))
				{
					if (getFeedBack().equalsIgnoreCase("Positive"))
					{
						query.append(" and feeddata.targetOn = 'Yes'");
					}
					else if (getFeedBack().equalsIgnoreCase("Negative"))
					{
						query.append(" and feeddata.targetOn = 'No'");
					}
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{

					query.append(" &&  ");
					// add search query based on the search operation
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
				FeedbackHelper helper = new FeedbackHelper();
				FeedbackUniversalHelper feedHelper = new FeedbackUniversalHelper();
				String mobNo = null;
				// System.out.println("Paper >>>"+query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					fieldNames.add("feedTicketId");
					fieldNames.add("ticketNo");
					Collections.reverse(data);
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
									if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("openDate"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("openTime"))
									{
										one.put(fieldNames.get(k).toString(), (obdata[k].toString().substring(0, 5)));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("handledBy"))
									{
										String handledBy = new HelpdeskUniversalAction().getData("employee_basic", "empName", "id", obdata[k].toString());
										if (handledBy != null)
										{
											one.put(fieldNames.get(k).toString(), (handledBy));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), ("NA"));
										}
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("userName"))
									{
										one.put(fieldNames.get(k).toString(), feedHelper.getLoginIdEmpName(connectionSpace, obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("targetOn"))
									{
										if (obdata[k] != null && obdata[k].toString().equalsIgnoreCase("No"))
										{
											one.put(fieldNames.get(k).toString(), "Negative");
										}
										else if (obdata[k] != null && obdata[k].toString().equalsIgnoreCase("Yes"))
										{
											one.put(fieldNames.get(k).toString(), "Positive");
										}
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
										if (fieldNames.get(k).toString().equalsIgnoreCase("mobNo"))
										{
											mobNo = obdata[k].toString();
										}
									}
								}

							}
						}
						if (mobNo != null)
						{
							one.put("actionCounter", helper.getCustomerFeedbackDetails(connectionSpace, mobNo));
						}
						else
						{
							one.put("actionCounter", "NA");
						}

						one.put("action", "Take Action");
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String viewModifyFeedback()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("feedbackdata", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public int avg(String hygiene)
	{
		int x = 0;
		if (hygiene.equalsIgnoreCase("Poor"))
		{
			x = 1;
		}
		else if (hygiene.equalsIgnoreCase("Average"))
		{
			x = 2;
		}
		else if (hygiene.equalsIgnoreCase("Good"))
		{
			x = 3;
		}
		else if (hygiene.equalsIgnoreCase("Very Good"))
		{
			x = 4;
		}
		else if (hygiene.equalsIgnoreCase("Excellent"))
		{
			x = 5;
		}
		return x;
	}

	public CustomerPojo getCustomerDetails(String feedId)
	{
		CustomerPojo feed = new CustomerPojo();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (feedId != null)
		{
			feed.setId(Integer.parseInt(feedId));
			StringBuilder builder = new StringBuilder("select problem,compType,clientId,clientName,centerCode,centreName,mobNo,openDate,openTime,emailId from feedbackdata where id='" + feedId + "'");
			List data = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						if (object[0] != null)
						{
							feed.setProblem(object[0].toString());
						}
						else
						{
							feed.setProblem("NA");
						}

						if (object[1] != null)
						{
							feed.setCompType(object[1].toString());
						}
						else
						{
							feed.setCompType("NA");
						}

						if (object[2] != null)
						{
							feed.setClientId(object[2].toString());
						}
						else
						{
							feed.setClientId("NA");
						}

						if (object[3] != null)
						{
							feed.setClientName(object[3].toString());
						}
						else
						{
							feed.setClientName("NA");
						}

						if (object[4] != null)
						{
							feed.setCentreCode(object[4].toString());
						}
						else
						{
							feed.setCentreCode("NA");
						}

						if (object[5] != null)
						{
							feed.setCentreName(object[5].toString());
						}
						else
						{
							feed.setCentreName("NA");
						}

						if (object[6] != null)
						{
							feed.setMobNo(object[6].toString());
						}
						else
						{
							feed.setMobNo("NA");
						}

						if (object[7] != null)
						{
							feed.setOpenDate(DateUtil.convertDateToIndianFormat(object[7].toString()));
						}
						else
						{
							feed.setOpenDate("NA");
						}

						if (object[8] != null)
						{
							feed.setOpenTime(object[8].toString().substring(0, 5));
						}
						else
						{
							feed.setOpenTime("NA");
						}

						if (object[9] != null)
						{
							feed.setEmailId(object[9].toString());
						}
						else
						{
							feed.setEmailId("NA");
						}

					}
				}
			}
		}
		return feed;
	}

	public CustomerPojo getCustomerInfo(String feedTicketId)
	{
		CustomerPojo feed = new CustomerPojo();

		/*
		 * StringBuilder query=newStringBuilder(
		 * "select feedbt.id as feedback_ticketId,feedbt.feedTicketNo," +
		 * "feedData.openDate,feedData.openTime,feedbck.escalation_date,feedbck.escalation_time"
		 * +
		 * ",feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo,feedData.emailId,"
		 * +
		 * "feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem,"
		 * + "feedData.kword,feedData.ip " + "from feedback_ticket as feedbt " +
		 * "inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id "
		 * +
		 * "inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id "
		 * + "where feedbt.id='"+feedTicketId+"'");
		 */

		StringBuilder queryNew2 = new StringBuilder("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," + " feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," + " feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id," + " feedData.compType,feedData.handledBy" + " from feedback_ticket as feedbt" + " inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id "
				+ " inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where feedbt.id='" + feedTicketId + "' order by feedData.openDate DESC");

		// System.out.println(
		// "queryNew2 is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><"
		// +queryNew2);
		List data = new createTable().executeAllSelectQuery(queryNew2.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			int i = 1;
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] type = (Object[]) iterator.next();

				if (type[0] != null)
				{
					feed.setFeedTicketId(Integer.parseInt(type[0].toString()));
				}

				if (type[1] != null)
				{
					feed.setTicketNo(type[1].toString());
				}
				else
				{
					feed.setTicketNo("NA");
				}

				if (type[2] != null)
				{
					feed.setOpenDate(DateUtil.convertDateToIndianFormat(type[2].toString()));
				}
				else
				{
					feed.setOpenDate("NA");
				}

				if (type[3] != null)
				{
					feed.setOpenTime(type[3].toString().substring(0, 5));
				}
				else
				{
					feed.setOpenTime("NA");
				}

				if (type[4] != null)
				{
					feed.setEscalationDate(DateUtil.convertDateToIndianFormat(type[4].toString()));
				}
				else
				{
					feed.setEscalationDate("NA");
				}

				if (type[5] != null)
				{
					feed.setEscalationTime(type[5].toString());
				}
				else
				{
					feed.setEscalationTime("NA");
				}

				if (type[6] != null)
				{
					feed.setLevel(type[6].toString());
				}
				else
				{
					feed.setLevel("NA");
				}

				if (type[7] != null)
				{
					feed.setClientId(type[7].toString());
				}
				else
				{
					feed.setClientId("NA");
				}

				if (type[8] != null)
				{
					feed.setClientName(type[8].toString());
				}
				else
				{
					feed.setClientName("NA");
				}

				if (type[9] != null)
				{
					feed.setMobNo(type[9].toString());
				}
				else
				{
					feed.setMobNo("NA");
				}

				if (type[10] != null)
				{
					feed.setEmailId(type[10].toString());
				}
				else
				{
					feed.setEmailId("NA");
				}

				if (type[11] != null)
				{
					feed.setRefNo(type[12].toString());
				}
				else
				{
					feed.setRefNo("NA");
				}

				if (type[12] != null)
				{
					feed.setCentreCode(type[12].toString());
				}
				else
				{
					feed.setCentreCode("NA");
				}

				if (type[13] != null)
				{
					feed.setCentreName(type[13].toString());
				}
				else
				{
					feed.setCentreName("NA");
				}

				if (type[14] != null)
				{
					feed.setId(Integer.parseInt(type[14].toString()));
				}

				if (type[14] != null)
				{
					feed.setComplaintType(type[14].toString());
				}
				else
				{
					feed.setComplaintType("NA");
				}

				if (type[15] != null)
				{
					feed.setHandledBy(type[15].toString());
				}
				else
				{
					feed.setHandledBy("NA");
				}
			}
		}
		return feed;
	}

	public List<Object> getValue(int id2)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append(" select * from feedbackdata where id='" + id2 + "' ");
		// System.out.println("query1>>> "+query1);
		List data3 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
		return data3;
	}

	public List<Object> getValue2(int id2)
	{
		// System.out.println("Id in the getValue2 method >>>>>> "+id2);

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append(" select * from feedbackdata where id='" + id2 + "' ");
		// System.out.println("session>>> "+connectionSpace);
		List data3 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
		return data3;
	}

	public String getTicketNo(int id2)
	{
		String data = null;
		StringBuffer buffer = new StringBuffer("select feedTicketNo from feedback_ticket where id='" + id2 + "'");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		//System.out.println("Ticket No Querry is as <><>><<>><><<><><>><"+buffer
		// .toString());
		List data3 = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (data3 != null && data3.size() > 0)
		{
			for (Iterator iterator = data3.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					data = object.toString();
				}
			}
		}
		return data;
	}

	public String actionTaken()
	{
		try
		{

			// System.out.println("inside the action taken method>>>>>>>>"+
			// getFeedDataId()+">>> Action Name >>>>>"+getActionName());

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			if (getActionName() != null && !(getActionName().equalsIgnoreCase("")))
			{

				List<TableColumes> tableColumn = new ArrayList<TableColumes>();
				List<String> columnName = new ArrayList<String>();

				// columnName.add("id");
				columnName.add("name");
				columnName.add("actionDate");
				columnName.add("actionTime");
				columnName.add("feedDataId");
				columnName.add("remarks");
				columnName.add("fir");

				for (String cloumn : columnName)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(cloumn);
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);
				}

				boolean status = cbt.createTable22("actiontaken", tableColumn, connectionSpace);
				// System.out.println("Table Created>>>>>>>>>"+status);

				String name = new ReportsConfigurationDao().getTableColumnVal("actioninfo", "name", "id", getActionName(), connectionSpace);
				wherClause.put("status", name);

				List<InsertDataTable> insertAction = new ArrayList<InsertDataTable>();
				InsertDataTable ob1 = null;
				ob1 = new InsertDataTable();
				ob1.setColName("name");
				ob1.setDataName(name);
				insertAction.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("actionDate");
				ob1.setDataName(DateUtil.getCurrentDateUSFormat());
				insertAction.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("actionTime");
				ob1.setDataName(DateUtil.getCurrentTimeHourMin());
				insertAction.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("feedDataId");
				ob1.setDataName(getFeedDataId());
				insertAction.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("fir");
				ob1.setDataName(getFir());
				insertAction.add(ob1);

				ob1 = new InsertDataTable();
				ob1.setColName("remarks");
				ob1.setDataName(getComments());
				insertAction.add(ob1);

				cbt.insertIntoTable("actiontaken", insertAction, connectionSpace);
			}

			wherClause.put("problem", getFir());
			wherClause.put("remarks", getComments());

			condtnBlock.put("id", getFeedDataId());

			// System.out.println("get id >>>>> "+getId());
			cbt.updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);

			/*
			 * List <InsertDataTable> insertAction=new
			 * ArrayList<InsertDataTable>(); InsertDataTable ob1=null; ob1=new
			 * InsertDataTable(); ob1.setColName("name");
			 * ob1.setDataName(actionName); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("mobileNo");
			 * ob1.setDataName(actionMob); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("description");
			 * ob1.setDataName(actionDesc); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("emailId");
			 * ob1.setDataName(actionEmail); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("status");
			 * ob1.setDataName(status); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("action");
			 * ob1.setDataName(actionTaken); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("comments");
			 * ob1.setDataName(comments); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsHyg");
			 * ob1.setDataName(commentsHyg); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsStaff");
			 * ob1.setDataName(commentsStaff); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsCosting");
			 * ob1.setDataName(commentsCosting); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsTreatment");
			 * ob1.setDataName(commentsTreatment); insertAction.add(ob1);
			 * ob1=new InsertDataTable(); ob1.setColName("commentsAmbience");
			 * ob1.setDataName(commentsAmbience); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsBilling");
			 * ob1.setDataName(commentsBilling); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsResult");
			 * ob1.setDataName(commentsResult); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("commentsAll");
			 * ob1.setDataName(commentsAll); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("level");
			 * ob1.setDataName(level); insertAction.add(ob1); ob1=new
			 * InsertDataTable(); ob1.setColName("actionDate");
			 * ob1.setDataName(DateUtil.getCurrentDateUSFormat());
			 * insertAction.add(ob1); ob1=new InsertDataTable();
			 * ob1.setColName("actionTime");
			 * ob1.setDataName(DateUtil.getCurrentTime());
			 * insertAction.add(ob1); // ob1=new InsertDataTable();
			 * ob1.setColName("fir"); ob1.setDataName(getFir());
			 * insertAction.add(ob1);
			 * 
			 * if(getFeedDataId()!=null) { ob1=new InsertDataTable();
			 * ob1.setColName("feedDataId"); ob1.setDataName(getFeedDataId());
			 * insertAction.add(ob1); }
			 * 
			 * cbt.insertIntoTable("actiontaken",insertAction,connectionSpace);
			 */

			int feed_stat_id = new EscalationActionControlDao().feedStatusIdbyFeedDataId(connectionSpace, "feedback_ticket", "feed_data_id", getId(), "feed_stat_id");
			boolean flag = new EscalationActionControlDao().updateFeedStatForFeedback(connectionSpace, "Close", feed_stat_id);

			addActionMessage("Action Taken Successfully !!!");

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewAction()
	{

		// System.out.println("Inside the ViewAction method>>>>>>>>>>>>>>");
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public boolean isStafftrue()
	{
		return stafftrue;
	}

	public void setStafftrue(boolean stafftrue)
	{
		this.stafftrue = stafftrue;
	}

	public String getCleanliness()
	{
		return cleanliness;
	}

	public void setCleanliness(String cleanliness)
	{

		if (cleanliness == null)
		{
			this.cleanliness = "NA";
		}
		else
		{
			this.cleanliness = cleanliness;
		}

	}

	public Map<Integer, String> getSourceList()
	{
		return sourceList;
	}

	public void setSourceList(Map<Integer, String> sourceList)
	{
		this.sourceList = sourceList;
	}

	public String getModifyFlag()
	{
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag)
	{
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag)
	{
		this.deleteFlag = deleteFlag;
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

	public boolean isDdTrue()
	{
		return ddTrue;
	}

	public void setDdTrue(boolean ddTrue)
	{
		this.ddTrue = ddTrue;
	}

	public String getDdValue()
	{
		return ddValue;
	}

	public void setDdValue(String ddValue)
	{
		this.ddValue = ddValue;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public List<Parent> getParentTakeaction()
	{
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction)
	{
		this.parentTakeaction = parentTakeaction;
	}

	public Map<String, String> getLeadAActionTextBox()
	{
		return leadAActionTextBox;
	}

	public void setLeadAActionTextBox(Map<String, String> leadAActionTextBox)
	{
		this.leadAActionTextBox = leadAActionTextBox;
	}

	public Map<Integer, String> getTargetKpiLiist()
	{
		return targetKpiLiist;
	}

	public void setTargetKpiLiist(Map<Integer, String> targetKpiLiist)
	{
		this.targetKpiLiist = targetKpiLiist;
	}

	public String getStaffBehaviour()
	{
		return staffBehaviour;
	}

	public void setStaffBehaviour(String staffBehaviour)
	{

		if (staffBehaviour == null)
		{
			this.staffBehaviour = "NA";
		}
		else
		{
			this.staffBehaviour = staffBehaviour;
		}

	}

	public String getCostingFactor()
	{
		return costingFactor;
	}

	public void setCostingFactor(String costingFactor)
	{
		if (costingFactor == null)
		{
			this.costingFactor = "NA";
		}
		else
		{
			this.costingFactor = costingFactor;
		}

	}

	public String getTreatment()
	{
		return treatment;
	}

	public void setTreatment(String treatment)
	{

		if (treatment == null)
		{
			this.treatment = "NA";
		}
		else
		{
			this.treatment = treatment;
		}

	}

	public String getAmbience()
	{
		return ambience;
	}

	public void setAmbience(String ambience)
	{

		if (ambience == null)
		{
			this.ambience = "NA";
		}
		else
		{
			this.ambience = ambience;
		}
	}

	public String getBillingServices()
	{
		return billingServices;
	}

	public void setBillingServices(String billingServices)
	{

		if (ambience == null)
		{
			this.ambience = "NA";
		}
		else
		{
			this.billingServices = billingServices;
		}

	}

	public String getResultSatis()
	{

		return resultSatis;
	}

	public void setResultSatis(String resultSatis)
	{
		if (resultSatis == null)
		{
			this.resultSatis = "NA";
		}
		else
		{
			this.resultSatis = resultSatis;
		}

	}

	public String getOverAll()
	{
		return overAll;
	}

	public void setOverAll(String overAll)
	{
		if (overAll == null)
		{
			this.overAll = "NA";
		}
		else
		{
			this.overAll = overAll;
		}

	}

	public boolean isCostingTrue()
	{
		return costingTrue;
	}

	public void setCostingTrue(boolean costingTrue)
	{
		this.costingTrue = costingTrue;
	}

	public boolean isTreatmentTrue()
	{
		return treatmentTrue;
	}

	public void setTreatmentTrue(boolean treatmentTrue)
	{
		this.treatmentTrue = treatmentTrue;
	}

	public boolean isAmbienceTrue()
	{
		return ambienceTrue;
	}

	public void setAmbienceTrue(boolean ambienceTrue)
	{
		this.ambienceTrue = ambienceTrue;
	}

	public boolean isBillingTrue()
	{
		return billingTrue;
	}

	public void setBillingTrue(boolean billingTrue)
	{
		this.billingTrue = billingTrue;
	}

	public boolean isOverAllTrue()
	{
		return overAllTrue;
	}

	public void setOverAllTrue(boolean overAllTrue)
	{
		this.overAllTrue = overAllTrue;
	}

	public boolean isResultTrue()
	{
		return resultTrue;
	}

	public void setResultTrue(boolean resultTrue)
	{
		this.resultTrue = resultTrue;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getCommentsHyg()
	{
		return commentsHyg;
	}

	public void setCommentsHyg(String commentsHyg)
	{
		this.commentsHyg = commentsHyg;
	}

	public String getActionName()
	{
		return actionName;
	}

	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public String getCommentsStaff()
	{
		return commentsStaff;
	}

	public void setCommentsStaff(String commentsStaff)
	{
		this.commentsStaff = commentsStaff;
	}

	public String getActionDesc()
	{
		return actionDesc;
	}

	public void setActionDesc(String actionDesc)
	{
		this.actionDesc = actionDesc;
	}

	public String getActionMob()
	{
		return actionMob;
	}

	public void setActionMob(String actionMob)
	{
		this.actionMob = actionMob;
	}

	public String getActionEmail()
	{
		return actionEmail;
	}

	public void setActionEmail(String actionEmail)
	{
		this.actionEmail = actionEmail;
	}

	public String getCommentsCosting()
	{
		return commentsCosting;
	}

	public void setCommentsCosting(String commentsCosting)
	{
		this.commentsCosting = commentsCosting;
	}

	public String getCommentsTreatment()
	{
		return commentsTreatment;
	}

	public void setCommentsTreatment(String commentsTreatment)
	{
		this.commentsTreatment = commentsTreatment;
	}

	public String getCommentsAmbience()
	{
		return commentsAmbience;
	}

	public void setCommentsAmbience(String commentsAmbience)
	{
		this.commentsAmbience = commentsAmbience;
	}

	public String getCommentsBilling()
	{
		return commentsBilling;
	}

	public void setCommentsBilling(String commentsBilling)
	{
		this.commentsBilling = commentsBilling;
	}

	public String getCommentsResult()
	{
		return commentsResult;
	}

	public void setCommentsResult(String commentsResult)
	{
		this.commentsResult = commentsResult;
	}

	public String getCommentsAll()
	{
		return commentsAll;
	}

	public void setCommentsAll(String commentsAll)
	{
		this.commentsAll = commentsAll;
	}

	public String getActionTaken()
	{
		return actionTaken;
	}

	public void setActionTaken(String actionTaken)
	{
		this.actionTaken = actionTaken;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getRemoteAdd()
	{
		return remoteAdd;
	}

	public void setRemoteAdd(String remoteAdd)
	{
		this.remoteAdd = remoteAdd;
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	public String getContextName()
	{
		return contextName;
	}

	public void setContextName(String contextName)
	{
		this.contextName = contextName;
	}

	public String getTargetOn()
	{
		return targetOn;
	}

	public void setTargetOn(String targetOn)
	{
		this.targetOn = targetOn;
	}

	public String getFormater()
	{
		return formater;
	}

	public void setFormater(String formater)
	{
		this.formater = formater;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getAccountID()
	{
		return accountID;
	}

	public void setAccountID(String accountID)
	{
		this.accountID = accountID;
	}

	public String getDocterName()
	{
		return docterName;
	}

	public void setDocterName(String docterName)
	{
		this.docterName = docterName;
	}

	public String getPurposeOfVisit()
	{
		return purposeOfVisit;
	}

	public void setPurposeOfVisit(String purposeOfVisit)
	{
		// System.out.println("inside purpose of visit");
		if (purposeOfVisit == null)
		{
			this.purposeOfVisit = "NA";
		}
		else
		{
			this.purposeOfVisit = purposeOfVisit;
		}

	}

	public String getEmailID()
	{
		return emailID;
	}

	public void setEmailID(String emailID)
	{
		this.emailID = emailID;
	}

	public String getGraphHeader()
	{
		return graphHeader;
	}

	public void setGraphHeader(String graphHeader)
	{
		this.graphHeader = graphHeader;
	}

	public Map<String, Integer> getPieDataMap()
	{
		return pieDataMap;
	}

	public void setPieDataMap(Map<String, Integer> pieDataMap)
	{
		this.pieDataMap = pieDataMap;
	}

	public Map<String, Integer> getPieDataMap1()
	{
		return pieDataMap1;
	}

	public void setPieDataMap1(Map<String, Integer> pieDataMap1)
	{
		this.pieDataMap1 = pieDataMap1;
	}

	public String getGraphHeader1()
	{
		return graphHeader1;
	}

	public void setGraphHeader1(String graphHeader1)
	{
		this.graphHeader1 = graphHeader1;
	}

	public String getGraphHeader2()
	{
		return graphHeader2;
	}

	public void setGraphHeader2(String graphHeader2)
	{
		this.graphHeader2 = graphHeader2;
	}

	public String getGraphHeader3()
	{
		return graphHeader3;
	}

	public void setGraphHeader3(String graphHeader3)
	{
		this.graphHeader3 = graphHeader3;
	}

	public String getGraphHeader4()
	{
		return graphHeader4;
	}

	public void setGraphHeader4(String graphHeader4)
	{
		this.graphHeader4 = graphHeader4;
	}

	public String getGraphHeader5()
	{
		return graphHeader5;
	}

	public void setGraphHeader5(String graphHeader5)
	{
		this.graphHeader5 = graphHeader5;
	}

	public String getGraphHeader6()
	{
		return graphHeader6;
	}

	public void setGraphHeader6(String graphHeader6)
	{
		this.graphHeader6 = graphHeader6;
	}

	public Map<String, Integer> getPieDataMap2()
	{
		return pieDataMap2;
	}

	public void setPieDataMap2(Map<String, Integer> pieDataMap2)
	{
		this.pieDataMap2 = pieDataMap2;
	}

	public Map<String, Integer> getPieDataMap3()
	{
		return pieDataMap3;
	}

	public void setPieDataMap3(Map<String, Integer> pieDataMap3)
	{
		this.pieDataMap3 = pieDataMap3;
	}

	public Map<String, Integer> getPieDataMap4()
	{
		return pieDataMap4;
	}

	public void setPieDataMap4(Map<String, Integer> pieDataMap4)
	{
		this.pieDataMap4 = pieDataMap4;
	}

	public Map<String, Integer> getPieDataMap5()
	{
		return pieDataMap5;
	}

	public void setPieDataMap5(Map<String, Integer> pieDataMap5)
	{
		this.pieDataMap5 = pieDataMap5;
	}

	public Map<String, Integer> getPieDataMap6()
	{
		return pieDataMap6;
	}

	public void setPieDataMap6(Map<String, Integer> pieDataMap6)
	{
		this.pieDataMap6 = pieDataMap6;
	}

	public Map<String, Integer> getPieDataMapYes()
	{
		return pieDataMapYes;
	}

	public void setPieDataMapYes(Map<String, Integer> pieDataMapYes)
	{
		this.pieDataMapYes = pieDataMapYes;
	}

	public Map<String, Integer> getPieDataMapNo()
	{
		return pieDataMapNo;
	}

	public void setPieDataMapNo(Map<String, Integer> pieDataMapNo)
	{
		this.pieDataMapNo = pieDataMapNo;
	}

	public List<ConfigurationUtilBean> getFeedbackTextBox()
	{
		return feedbackTextBox;
	}

	public void setFeedbackTextBox(List<ConfigurationUtilBean> feedbackTextBox)
	{
		this.feedbackTextBox = feedbackTextBox;
	}

	public String getGraphHeader7()
	{
		return graphHeader7;
	}

	public void setGraphHeader7(String graphHeader7)
	{
		this.graphHeader7 = graphHeader7;
	}

	public Map<String, Integer> getPieDataMap7()
	{
		return pieDataMap7;
	}

	public void setPieDataMap7(Map<String, Integer> pieDataMap7)
	{
		this.pieDataMap7 = pieDataMap7;
	}

	public String getFir()
	{
		return fir;
	}

	public void setFir(String fir)
	{
		this.fir = fir;
	}

	public String getPatientId()
	{
		return patientId;
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public String getTabUserId()
	{
		return tabUserId;
	}

	public void setTabUserId(String tabUserId)
	{
		this.tabUserId = tabUserId;
	}

	public String getFeedDataId()
	{
		return feedDataId;
	}

	public void setFeedDataId(String feedDataId)
	{
		this.feedDataId = feedDataId;
	}

	public String getFlage()
	{
		return flage;
	}

	public void setFlage(String flage)
	{
		this.flage = flage;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public boolean isValidityTrue()
	{
		return validityTrue;
	}

	public void setValidityTrue(boolean validityTrue)
	{
		this.validityTrue = validityTrue;
	}

	public boolean isAdministrativeTrue()
	{
		return administrativeTrue;
	}

	public void setAdministrativeTrue(boolean administrativeTrue)
	{
		this.administrativeTrue = administrativeTrue;
	}

	public String getValidity()
	{
		return validity;
	}

	public void setValidity(String validity)
	{
		this.validity = validity;
	}

	public String getAdmin()
	{
		return admin;
	}

	public void setAdmin(String admin)
	{
		this.admin = admin;
	}

	public List<GridDataPropertyView> getCRMColumnNames()
	{
		return CRMColumnNames;
	}

	public void setCRMColumnNames(List<GridDataPropertyView> columnNames)
	{
		CRMColumnNames = columnNames;
	}

	public boolean isOtherTrue()
	{
		return otherTrue;
	}

	public void setOtherTrue(boolean otherTrue)
	{
		this.otherTrue = otherTrue;
	}

	public String getOtherFacil()
	{
		return otherFacil;
	}

	public void setOtherFacil(String otherFacil)
	{
		this.otherFacil = otherFacil;
	}

	public String getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(String openDate)
	{
		this.openDate = openDate;
	}

	public String getOpenTime()
	{
		return openTime;
	}

	public void setOpenTime(String openTime)
	{
		this.openTime = openTime;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getSearchFor()
	{
		return searchFor;
	}

	public void setSearchFor(String searchFor)
	{
		this.searchFor = searchFor;
	}

	public PatientInfo getPatObj()
	{
		return patObj;
	}

	public void setPatObj(PatientInfo patObj)
	{
		this.patObj = patObj;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getPatType()
	{
		return patType;
	}

	public void setPatType(String patType)
	{
		this.patType = patType;
	}

	public Map<String, String> getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(Map<String, String> speciality)
	{
		this.speciality = speciality;
	}

	public Map<String, String> getTreatedBy()
	{
		return treatedBy;
	}

	public void setTreatedBy(Map<String, String> treatedBy)
	{
		this.treatedBy = treatedBy;
	}

	public String getSpec()
	{
		return spec;
	}

	public void setSpec(String spec)
	{
		this.spec = spec;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public Map<String, String> getActionNameMap()
	{
		return actionNameMap;
	}

	public void setActionNameMap(Map<String, String> actionNameMap)
	{
		this.actionNameMap = actionNameMap;
	}

	public String getFeedTicketId()
	{
		return feedTicketId;
	}

	public void setFeedTicketId(String feedTicketId)
	{
		this.feedTicketId = feedTicketId;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getCrNo()
	{
		return crNo;
	}

	public void setCrNo(String crNo)
	{
		this.crNo = crNo;
	}

	public String getPatName()
	{
		return patName;
	}

	public void setPatName(String patName)
	{
		this.patName = patName;
	}

	public String getMobNo()
	{
		return mobNo;
	}

	public void setMobNo(String mobNo)
	{
		this.mobNo = mobNo;
	}

	public String getRoomNo()
	{
		return roomNo;
	}

	public void setRoomNo(String roomNo)
	{
		this.roomNo = roomNo;
	}

	public String getSpecality()
	{
		return specality;
	}

	public void setSpecality(String specality)
	{
		this.specality = specality;
	}

	public String getObservation()
	{
		return observation;
	}

	public void setObservation(String observation)
	{
		this.observation = observation;
	}

	public String getTotalPaper()
	{
		return totalPaper;
	}

	public void setTotalPaper(String totalPaper)
	{
		this.totalPaper = totalPaper;
	}

	public String getPosPaper()
	{
		return posPaper;
	}

	public void setPosPaper(String posPaper)
	{
		this.posPaper = posPaper;
	}

	public String getNegPaper()
	{
		return negPaper;
	}

	public void setNegPaper(String negPaper)
	{
		this.negPaper = negPaper;
	}

	public String getFeedBack()
	{
		return feedBack;
	}

	public void setFeedBack(String feedBack)
	{
		this.feedBack = feedBack;
	}

	public String getPosSMS()
	{
		return posSMS;
	}

	public void setPosSMS(String posSMS)
	{
		this.posSMS = posSMS;
	}

	public String getTotalPos()
	{
		return totalPos;
	}

	public void setTotalPos(String totalPos)
	{
		this.totalPos = totalPos;
	}

	public String getTotalNeg()
	{
		return totalNeg;
	}

	public void setTotalNeg(String totalNeg)
	{
		this.totalNeg = totalNeg;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public JSONObject getPatObj1()
	{
		return patObj1;
	}

	public void setPatObj1(JSONObject patObj1)
	{
		this.patObj1 = patObj1;
	}

	public JSONObject getPaperViewObj()
	{
		return paperViewObj;
	}

	public void setPaperViewObj(JSONObject paperViewObj)
	{
		this.paperViewObj = paperViewObj;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	public String getCompType()
	{
		return compType;
	}

	public void setCompType(String compType)
	{
		this.compType = compType;
	}

	public String getPaperRefNo()
	{
		return paperRefNo;
	}

	public void setPaperRefNo(String paperRefNo)
	{
		this.paperRefNo = paperRefNo;
	}

	public JSONObject getPaperViewObjIPD()
	{
		return paperViewObjIPD;
	}

	public void setPaperViewObjIPD(JSONObject paperViewObjIPD)
	{
		this.paperViewObjIPD = paperViewObjIPD;
	}

	public String getTodayPositive()
	{
		return todayPositive;
	}

	public void setTodayPositive(String todayPositive)
	{
		this.todayPositive = todayPositive;
	}

	public String getTodayNegative()
	{
		return todayNegative;
	}

	public void setTodayNegative(String todayNegative)
	{
		this.todayNegative = todayNegative;
	}

	public String getTodayTotal()
	{
		return todayTotal;
	}

	public void setTodayTotal(String todayTotal)
	{
		this.todayTotal = todayTotal;
	}

	public String getChoseHospital()
	{
		return choseHospital;
	}

	public void setChoseHospital(String choseHospital)
	{
		this.choseHospital = choseHospital;
	}

	public Map<String, String> getFeedbackActionStatus()
	{
		return feedbackActionStatus;
	}

	public void setFeedbackActionStatus(Map<String, String> feedbackActionStatus)
	{
		this.feedbackActionStatus = feedbackActionStatus;
	}

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}
}