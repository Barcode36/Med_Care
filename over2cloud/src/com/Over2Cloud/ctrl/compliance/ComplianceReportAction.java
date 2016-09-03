package com.Over2Cloud.ctrl.compliance;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@SuppressWarnings("serial")
public class ComplianceReportAction extends ActionSupport {
	
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	static final Log log = LogFactory.getLog(ComplianceReportAction.class);
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String deptLevel = (String) session.get("userDeptLevel");
	private String mainHeaderName;
	private List<GridDataPropertyView> masterViewProp=new ArrayList<GridDataPropertyView>();
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
	private String fromDate;
	private String toDate;
	private String minCount;
	private String maxCount;
	private String actionStatus;
	private String searchPeriodOn;
	private String compReportId;
	private String frequency;
	private InputStream fileInputStream;
	private String fileName;
	private String documentName;
	private Map<String, String> deptName;
	private boolean highLevel=false;
	private String deptId;
	private String tableName;
	private String excelName;
	private String selectedId;
	private String divName;
	private String[] columnNames;
	private String tableAlis;
	private Map<String,String > columnMap;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	private Map<String, String> docMap;
	private String compId;
	private String employee;
	private String callRecurring;

	@SuppressWarnings("unchecked")
	public String beforeViewCompReportData()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	deptName=new LinkedHashMap<String, String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String userContID = null;
	userContID=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[0];
	deptName=getDeptByMappedEmp(userContID,userEmpID,"COMPL","All");
	
	
	fromDate=DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
	toDate=DateUtil.getCurrentDateUSFormat();
	minCount="-30";
	maxCount="30";
	
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("compReport.complainceId");
	gpv.setHeadingName("Task Id");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(50);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("dept.deptName");
	gpv.setHeadingName("Department");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(50);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("ctyp.taskType");
	gpv.setHeadingName("Task Type");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.frequency");
	gpv.setHeadingName("Frequency");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.reminder_for");
	gpv.setHeadingName("Allot To");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(120);
	masterViewProp.add(gpv);
	
	List<GridDataPropertyView> complReportColumn=Configuration.getConfigurationData("mapped_compliance_report_config",accountID,connectionSpace,"",0,"table_name","compliance_report_config");
	if(complReportColumn!=null && complReportColumn.size()>0)
	{
	for(GridDataPropertyView gdp:complReportColumn)
	{
	if(!gdp.getColomnName().equals("costForCompMiss") && !gdp.getColomnName().equals("snoozeDate") && !gdp.getColomnName().equals("download") && !gdp.getColomnName().equals("snoozeTime") && !gdp.getColomnName().equals("rescheduleDate") && !gdp.getColomnName().equals("rescheduleTime")&& !gdp.getColomnName().equals("complainceId"))
	{
	if(actionStatus!=null && actionStatus.equalsIgnoreCase("All"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(100);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(80);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2") || gdp.getColomnName().equalsIgnoreCase("document_action_2") || gdp.getColomnName().equalsIgnoreCase("document_action_1"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	}
	else if(gdp.getColomnName().equalsIgnoreCase("checkid") )
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setFormatter("ViewCompletionTip");
	gpv.setWidth(gdp.getWidth());
	}
	else
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	}
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Pending"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_takeaction") &&  !gdp.getColomnName().equalsIgnoreCase("costForCompMiss") &&  !gdp.getColomnName().equalsIgnoreCase("comments")&& !gdp.getColomnName().equalsIgnoreCase("document_action_2") && !gdp.getColomnName().equalsIgnoreCase("document_action_1") && !gdp.getColomnName().equalsIgnoreCase("actual_expenses") && !gdp.getColomnName().equalsIgnoreCase("difference"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(100);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(80);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	}
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	}
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Reschedule") || actionStatus!=null && actionStatus.equalsIgnoreCase("Snooze"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_takeaction") &&  !gdp.getColomnName().equalsIgnoreCase("costForCompMiss") && !gdp.getColomnName().equalsIgnoreCase("document_action_2") && !gdp.getColomnName().equalsIgnoreCase("document_action_1") && !gdp.getColomnName().equalsIgnoreCase("actual_expenses") && !gdp.getColomnName().equalsIgnoreCase("difference"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(100);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(80);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	}
	else if(!gdp.getColomnName().equalsIgnoreCase("checkid"))
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	}
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Done") || actionStatus!=null && actionStatus.equalsIgnoreCase("Recurring"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_config_1") &&  !gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(100);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(80);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_action_2") || gdp.getColomnName().equalsIgnoreCase("document_action_1"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	}
	else if(gdp.getColomnName().equalsIgnoreCase("checkid") )
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setFormatter("ViewCompletionTip");
	gpv.setWidth(gdp.getWidth());
	}
	else
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	}
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	else if(actionStatus!=null && (actionStatus.equalsIgnoreCase("Level1") || actionStatus.equalsIgnoreCase("Level2") || actionStatus.equalsIgnoreCase("Level3") || actionStatus.equalsIgnoreCase("Level4") || actionStatus.equalsIgnoreCase("Level5")))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_config_1") &&  !gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(100);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(80);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_action_2") || gdp.getColomnName().equalsIgnoreCase("document_action_1"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	}
	else if(gdp.getColomnName().equalsIgnoreCase("checkid") )
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setFormatter("ViewCompletionTip");
	gpv.setWidth(gdp.getWidth());
	}
	else
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	}
	}
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	}
	}
	}
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.remindMode");
	gpv.setHeadingName("Mode");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(50);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_1");
	gpv.setHeadingName("Level 2");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_2");
	gpv.setHeadingName("Level 3");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_3");
	gpv.setHeadingName("Level 4");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_4");
	gpv.setHeadingName("Level 5");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("compReport.download");
	gpv.setHeadingName("Download Document");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setFormatter("downloadDoc");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	session.put("masterViewProp", masterViewProp);
	returnResult=SUCCESS;
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method beforeViewCompReportData of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String searchViewCompReportData()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	deptName=new LinkedHashMap<String, String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String desgLevel=new ComplianceUniversalAction().getCompContactLevel(userName);;
	if(desgLevel!=null && desgLevel.equalsIgnoreCase("5"))
	{
	highLevel=true;
	List  dataCount=cbt.executeAllSelectQuery("SELECT dept.id,dept.deptName FROM department AS dept INNER JOIN compliance_task AS ctn ON ctn.departName=dept.id GROUP BY dept.deptName ORDER BY ctn.departName ASC",connectionSpace);
	if(dataCount!=null && dataCount.size()>0)
	{
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	deptName.put(object[0].toString(), object[1].toString());
	
	}
	}
	}
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("compReport.complainceId");
	gpv.setHeadingName("Task Id");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(90);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("dept.deptName");
	gpv.setHeadingName("Department");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("ctyp.taskType");
	gpv.setHeadingName("Task Type");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.frequency");
	gpv.setHeadingName("Frequency");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.reminder_for");
	gpv.setHeadingName("Allot To");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	List<GridDataPropertyView> complReportColumn=Configuration.getConfigurationData("mapped_compliance_report_config",accountID,connectionSpace,"",0,"table_name","compliance_report_config");
	if(complReportColumn!=null && complReportColumn.size()>0)
	{
	for(GridDataPropertyView gdp:complReportColumn)
	{
	if(!gdp.getColomnName().equals("costForCompMiss") && !gdp.getColomnName().equals("snoozeDate") && !gdp.getColomnName().equals("download") && !gdp.getColomnName().equals("snoozeTime") && !gdp.getColomnName().equals("rescheduleDate") && !gdp.getColomnName().equals("rescheduleTime")&& !gdp.getColomnName().equals("complainceId"))
	{
	if(actionStatus!=null && actionStatus.equalsIgnoreCase("All"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(120);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(50);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2") || gdp.getColomnName().equalsIgnoreCase("document_action_2") || gdp.getColomnName().equalsIgnoreCase("document_action_1"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("checkid") )
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setFormatter("ViewCompletionTip");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Pending"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_takeaction") && !gdp.getColomnName().equalsIgnoreCase("document_action_2") && !gdp.getColomnName().equalsIgnoreCase("document_action_1") &&  !gdp.getColomnName().equalsIgnoreCase("costForCompMiss") &&  !gdp.getColomnName().equalsIgnoreCase("comments")&&  !gdp.getColomnName().equalsIgnoreCase("actual_expenses") &&  !gdp.getColomnName().equalsIgnoreCase("difference"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(120);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(50);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(!gdp.getColomnName().equalsIgnoreCase("checkid"))
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	}
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Reschedule") || actionStatus!=null && actionStatus.equalsIgnoreCase("Snooze"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_takeaction") && !gdp.getColomnName().equalsIgnoreCase("document_action_1") && !gdp.getColomnName().equalsIgnoreCase("document_action_2") &&  !gdp.getColomnName().equalsIgnoreCase("costForCompMiss") &&  !gdp.getColomnName().equalsIgnoreCase("actual_expenses") &&  !gdp.getColomnName().equalsIgnoreCase("difference"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(120);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(50);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(!gdp.getColomnName().equalsIgnoreCase("checkid"))
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	}
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Done") || actionStatus!=null && actionStatus.equalsIgnoreCase("Recurring"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_config_1") &&  !gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(120);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(50);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_action_2") || gdp.getColomnName().equalsIgnoreCase("document_action_1"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("checkid") )
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setFormatter("ViewCompletionTip");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	}
	}
	else if(actionStatus!=null && (actionStatus.equalsIgnoreCase("Level1") || actionStatus.equalsIgnoreCase("Level2") || actionStatus.equalsIgnoreCase("Level3") || actionStatus.equalsIgnoreCase("Level4") || actionStatus.equalsIgnoreCase("Level5")))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("document_config_1") &&  !gdp.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("complID"))
	{
	gpv.setColomnName("ctn.taskName");
	gpv.setHeadingName("Task Name");
	gpv.setWidth(120);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("userName"))
	{
	gpv.setColomnName("userAC.name");
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(50);
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_action_2") || gdp.getColomnName().equalsIgnoreCase("document_action_1"))
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")|| gdp.getColomnName().equalsIgnoreCase("snoozeDate") ) 
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName() +" & Time");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else if(gdp.getColomnName().equalsIgnoreCase("checkid") )
	{
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setFormatter("ViewCompletionTip");
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	else
	{
	gpv.setColomnName("compReport."+gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setWidth(gdp.getWidth());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	masterViewProp.add(gpv);
	}
	}
	}
	}
	}
	}
	}
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.remindMode");
	gpv.setHeadingName("Mode");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(40);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_1");
	gpv.setHeadingName("Level 2");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_2");
	gpv.setHeadingName("Level 3");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_3");
	gpv.setHeadingName("Level 3");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation_level_4");
	gpv.setHeadingName("Level 4");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("compReport.download");
	gpv.setHeadingName("Download Document");
	gpv.setEditable("false");
	gpv.setSearch("false");
	gpv.setHideOrShow("false");
	gpv.setFormatter("downloadDoc");
	gpv.setWidth(30);
	masterViewProp.add(gpv);
	session.put("masterViewProp", masterViewProp);
	returnResult=SUCCESS;
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method beforeViewCompReportData of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ViewCompReportData()
	{
	String retunString=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List  data=null;
	List<Object> Listhb=new ArrayList<Object>();
	StringBuilder strBuilder=new StringBuilder();
	String compIdSeries=null;
	Object object=null;
	if(deptId!=null && !deptId.equalsIgnoreCase("undefined"))
	{
	List  dataCount=null;
	System.out.println(deptId);
	String subStrDeptId = deptId.substring(0,3);
	System.out.println("subStrDeptId "+subStrDeptId);
	if(deptId.contains("All"))
	{
	String qry="SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task AS ctn ON comp.taskName=ctn.id WHERE ctn.departName IN("+deptId.substring(3, deptId.length())+")";
	dataCount=cbt.executeAllSelectQuery(qry,connectionSpace);
	}
	else
	{
	dataCount=cbt.executeAllSelectQuery("SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task AS ctn ON comp.taskName=ctn.id WHERE ctn.departName IN("+deptId+")",connectionSpace);
	}
	if(dataCount!=null && dataCount.size()>0)
	{
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
	{
	object = (Object) iterator.next();
	strBuilder.append(object.toString()+",");
	}
	}
	}
	else
	{
	String userContID = null;
	userContID=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[0];
	 List  dataCount=cbt.executeAllSelectQuery("SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task AS ctn ON comp.taskName=ctn.id WHERE ctn.departName IN(SELECT forDept_id FROM compliance_contacts WHERE id="+userContID+" AND moduleName='COMPL')",connectionSpace);
	 if(dataCount!=null && dataCount.size()>0)
	 {
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
	{
	object = (Object) iterator.next();
	strBuilder.append(object.toString()+",");
	}
	 }
	}
	if(strBuilder!=null && !strBuilder.toString().equalsIgnoreCase(""))
	{
	compIdSeries=strBuilder.toString().substring(0, strBuilder.toString().length()-1);
	}
	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from compliance_report");
	List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	if(dataCount!=null && dataCount.size()>0)
	{
	searchViewCompReportData();
	List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
	
	
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	
	int i=0;
	if(fieldNames!=null && fieldNames.size()>0)
	{
	for(GridDataPropertyView gpv:fieldNames)
	{
	if(i<(fieldNames.size()-1))
	{
	if(gpv.getColomnName()!=null)
	{
	if(gpv.getColomnName().equalsIgnoreCase("id"))
	{
	query.append("compReport.id,");
	}
	else
	{
	query.append(gpv.getColomnName().toString()+",");
	}
	}
	}
	else
	{
	query.append(gpv.getColomnName().toString());
	}
	i++;
	}
	query.append(" FROM compliance_report AS compReport " +
	"INNER JOIN useraccount AS userAC ON userAC.userID=compReport.userName " +
	"INNER JOIN compliance_details AS comp ON comp.id=compReport.complID  " +
	"INNER JOIN compliance_task AS ctn ON ctn.id=comp.taskName  " +
	"INNER JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType  " +
	"INNER JOIN department AS dept ON dept.id=ctn.departName  " +
	"WHERE comp.id IN("+compIdSeries+")");
	if(searchPeriodOn!=null && searchPeriodOn.equalsIgnoreCase("actionTakenDate"))
	{
	String str[]=fromDate.split("-");
	if(str[0].length()<3)
	{
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	toDate=DateUtil.convertDateToUSFormat(toDate);
	query.append(" AND compReport.actionTakenDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
	}
	else
	{
	query.append(" AND compReport.actionTakenDate BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"'");
	}
	}
	else if(searchPeriodOn!=null && searchPeriodOn.equalsIgnoreCase("dueDate"))
	{
	String str[]=fromDate.split("-");
	if(str[0].length()<3)
	{
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	toDate=DateUtil.convertDateToUSFormat(toDate);
	query.append(" AND compReport.dueDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
	}
	else 
	{
	query.append(" AND compReport.dueDate BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"'");
	}
	}
	else if(searchPeriodOn!=null && searchPeriodOn.equalsIgnoreCase("All"))
	{
	if (fromDate!=null & !fromDate.equalsIgnoreCase("All") && toDate!=null && !toDate.equalsIgnoreCase("All"))
                            {
	String str[]=fromDate.split("-");
	if(str[0].length()<3)
	{
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	toDate=DateUtil.convertDateToUSFormat(toDate);
	}
	
	query.append(" AND (compReport.actionTakenDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
	query.append(" OR compReport.dueDate BETWEEN '"+fromDate+"' AND '"+toDate+"')");
	
                            }
	}
	if (frequency!=null && !frequency.equalsIgnoreCase("All")) 
	{
	query.append(" AND comp.frequency = '"+frequency+"'");
	}
	if(actionStatus!=null && !actionStatus.equalsIgnoreCase("ALL"))
	{
	query.append(" AND compReport.actionTaken='"+actionStatus+"'");
	}
	query.append(" ORDER BY compReport.actionTakenDate ASC");
	data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
	
	if(data!=null)
	{
	Object[] obdata=null;
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	int k=0;
	obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for(GridDataPropertyView gpv:fieldNames)
	{
	if(obdata[k]!=null && !obdata[k].toString().equalsIgnoreCase(""))
	{
	if(gpv.getColomnName()!=null)
	{
	if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else if(gpv.getColomnName().equalsIgnoreCase("document_takeaction") || gpv.getColomnName().equalsIgnoreCase("document_action_2") || gpv.getColomnName().equalsIgnoreCase("document_action_1") || gpv.getColomnName().equalsIgnoreCase("document_config_1") || gpv.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	String str=obdata[k].toString().substring(obdata[k].toString().lastIndexOf("//")+2, obdata[k].toString().length());
	String docName=str.substring(14,str.length());
	one.put(gpv.getColomnName(),docName);
	}
	else if(gpv.getColomnName().equalsIgnoreCase("comp.reminder_for")  || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_1") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_2") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_3") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_4")  )
	{
	StringBuilder empName= new StringBuilder();
	String empId=obdata[k].toString().replace("#", ",").substring(0,(obdata[k].toString().length()-1));
	String query2="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
	List data1=cbt.executeAllSelectQuery(query2,connectionSpace );
	for (Iterator iterator = data1.iterator(); iterator.hasNext();) 
	{
	object = (Object) iterator.next();
	empName.append(object.toString()+", ");
	}
	one.put(gpv.getColomnName(),empName.toString().substring(0, empName.toString().length()-2));
	}
	else if(gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
	{
	one.put(gpv.getColomnName(),new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
	}
	else
	{
	one.put(gpv.getColomnName(),obdata[k].toString());
	}
	if(gpv.getColomnName().equalsIgnoreCase("compReport.dueDate") || gpv.getColomnName().equalsIgnoreCase("compReport.actionTakenDate") )
	{
	one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+" "+obdata[k+1].toString());
	}
	}
	}
	else
	{
	one.put(gpv.getColomnName().toString(), "NA");
	}
	k++;
	}
	Listhb.add(one);
	}
	setMasterViewList(Listhb);
	setRecords(masterViewList.size());
	int to = (getRows() * getPage());
	@SuppressWarnings("unused")
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	return SUCCESS;
	}
	}
	}
	retunString=SUCCESS;
	session.remove("masterViewProp");
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method ViewCompReportData of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	else
	{
	retunString=LOGIN;
	}
	return retunString;
	}
	
	@SuppressWarnings("rawtypes")
	public String getDocDownload()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	if(compReportId!=null && !compReportId.equalsIgnoreCase(""))
	{
	List  dataCount=cbt.executeAllSelectQuery("SELECT document_takeaction,document_config_1,document_config_2,document_action_1,document_action_2 FROM compliance_report WHERE id="+compReportId,connectionSpace);
	if(dataCount!=null && dataCount.size()>0)
	{
	docMap=new LinkedHashMap<String, String>();
	String str=null;
	Object[] object=null;
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
	{
	object = (Object[]) iterator.next();
	if(object[0]!=null)
	{
	str=object[0].toString().substring(object[0].toString().lastIndexOf("//")+2, object[0].toString().length());
	documentName=str.substring(14,str.length());
	docMap.put(object[0].toString(),documentName);
	}
	if(object[1]!=null)
	{
	str=object[1].toString().substring(object[1].toString().lastIndexOf("//")+2, object[1].toString().length());
	documentName=str.substring(14,str.length());
	docMap.put(object[1].toString(),documentName);
	}
	if(object[2]!=null)
	{
	str=object[2].toString().substring(object[2].toString().lastIndexOf("//")+2, object[2].toString().length());
	documentName=str.substring(14,str.length());
	docMap.put(object[2].toString(),documentName);
	}
	if(object[3]!=null)
	{
	str=object[3].toString().substring(object[3].toString().lastIndexOf("//")+2, object[3].toString().length());
	documentName=str.substring(14,str.length());
	docMap.put(object[3].toString(),documentName);
	}
	if(object[4]!=null)
	{
	str=object[4].toString().substring(object[4].toString().lastIndexOf("//")+2, object[4].toString().length());
	documentName=str.substring(14,str.length());
	docMap.put(object[4].toString(),documentName);
	}
	
	}
	returnResult=SUCCESS;
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getDocDownload of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	public String docDownload()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	File file=new File(fileName);
	System.out.println("File Name   "+fileName);
	String str = fileName.substring(fileName.lastIndexOf("//")+2, fileName.length());
	System.out.println("STR   "+str+"   STR length "+str.length());
	documentName=str.substring(14,str.length());
	System.out.println("DOC Name  "+documentName);
	fileName=documentName;
	            if(file.exists())
	            {
	            	fileInputStream = new FileInputStream(file);
	            	return SUCCESS;
	            }
	            else
	            {
	                 addActionError("File dose not exist");
	                 return ERROR;
	            }
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method docDownload of class "+getClass(), e);
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	public String getCurrentColumn()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	if (mainHeaderName!=null &&  mainHeaderName.equalsIgnoreCase("taskHistory")) 
	{
	returnResult = getColumnName("mapped_compliance_report_config","compliance_report_config", "compReport",mainHeaderName);
	tableName = "compliance_report";
	excelName = "Operation Task History Data";
	tableAlis = "compReport";
	}
	else 
	{
	returnResult = getColumnName("mapped_compliance_report_config","compliance_report_config", "compReport","");
	tableName = "compliance_report";
	excelName = "Operation Task Report Data";
	tableAlis = "compReport";
	}
	if (employee!=null && !employee.equalsIgnoreCase("") && !employee.equalsIgnoreCase("undefined")) 
	{
	divName = "sendMailDiv";
	}
	else 
	{
	divName = "";
	}
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName,String basicTableName,String tableAllis,String dataFor)
	{
	try
	{
	List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID,connectionSpace, "", 0, "table_name",basicTableName);
	columnMap = new LinkedHashMap<String, String>();
	if (columnList != null && columnList.size() > 0) 
	{
	for (GridDataPropertyView gdp : columnList) 
	{
	if (dataFor!=null && dataFor.equalsIgnoreCase("taskHistory")) 
	{
	if(gdp.getColomnName().equals("complID"))
	{
	columnMap.put("dept.deptName",new ComplianceUniversalAction().getFieldName("mapped_dept_config_param","dept_config_param", "deptName"));
	columnMap.put("comp.reminder_for",new ComplianceUniversalAction().getFieldName("mapped_compliance_configuration","compliance_configuration", "reminder_for"));
	}
	else if(!gdp.getColomnName().equals("complID") &&!gdp.getColomnName().equals("costForCompMiss")  &&!gdp.getColomnName().equals("snoozeDate") && !gdp.getColomnName().equals("snoozeTime") 
	&& !gdp.getColomnName().equals("rescheduleDate") && !gdp.getColomnName().equals("rescheduleTime") && !gdp.getColomnName().equals("rescheduleTime")  && !gdp.getColomnName().equalsIgnoreCase("current_esc_level") && !gdp.getColomnName().equals("download")&& !gdp.getColomnName().equals("costForCompMiss"))
	{
	columnMap.put(tableAllis+"."+gdp.getColomnName(),gdp.getHeadingName());
	}
	}
	else 
	{
	if(gdp.getColomnName().equals("complID"))
	{
	columnMap.put("dept.deptName",new ComplianceUniversalAction().getFieldName("mapped_dept_config_param","dept_config_param", "deptName"));
	columnMap.put("ctyp.taskType",new ComplianceUniversalAction().getFieldName("mapped_compl_task_type_config","compl_task_type_config", "taskType"));
	columnMap.put("ctn.taskName",new ComplianceUniversalAction().getFieldName("mapped_compl_task_config","compl_task_config", "taskName"));
	columnMap.put("comp.reminder_for",new ComplianceUniversalAction().getFieldName("mapped_compliance_configuration","compliance_configuration", "reminder_for"));
	}
	else if(!gdp.getColomnName().equals("snoozeDate") && !gdp.getColomnName().equals("snoozeTime") 
	&& !gdp.getColomnName().equals("rescheduleDate") && !gdp.getColomnName().equals("rescheduleTime") && !gdp.getColomnName().equals("download") && !gdp.getColomnName().equalsIgnoreCase("current_esc_level") && !gdp.getColomnName().equals("costForCompMiss"))
	{
	columnMap.put(tableAllis+"."+gdp.getColomnName(),gdp.getHeadingName());
	}
	}
	}
	}
	if (columnMap != null && columnMap.size() > 0) 
	{
	session.put("columnMap", columnMap);
	}
	return SUCCESS;
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadExcel()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();

	if (sessionFlag)
	{
	try 
	{
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommunicationHelper cmnHelper=new CommunicationHelper();
	List keyList = new ArrayList();
	List titleList = new ArrayList();
	StringBuilder emailIds=null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	if (columnNames != null && columnNames.length > 0) 
	{
	keyList = Arrays.asList(columnNames);
	Map<String, String> tempMap = new LinkedHashMap<String, String>();
	tempMap = (Map<String, String>) session.get("columnMap");
	if(session.containsKey("columnMap"))
	session.remove("columnMap");
	
	List dataList = null;
	StringBuilder query = new StringBuilder("");
	for (int index = 0; index < keyList.size(); index++)
	{
	titleList.add(tempMap.get(keyList.get(index)));
	}
	if (keyList != null && keyList.size() > 0) 
	{
	query.append("SELECT ");
	int i = 0;
	for (Iterator it = keyList.iterator(); it.hasNext();) 
	{
	Object obdata = (Object) it.next();
	if (obdata != null) {
	if (i < keyList.size() - 1) 
	{
	query.append(obdata.toString() + ",");
	}
	else 
	{
	if (obdata.toString().equalsIgnoreCase("comp.compid_prefix")) 
	{
	query.append(obdata.toString()+",comp.compid_suffix");
	}
	else 
	{
	query.append(obdata.toString());	
	}
	}
	}
	i++;
	}
	query.append(" FROM " + tableName + " AS "+ tableAlis);
	query.append(" INNER JOIN useraccount AS userAC ON userAC.userID="+tableAlis+".userName");
	query.append(" INNER JOIN compliance_details AS comp ON comp.id="+tableAlis+".complID");
	query.append(" INNER JOIN compliance_task AS ctn ON ctn.id=comp.taskName");
	query.append(" INNER JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType");
	query.append(" INNER JOIN department AS dept ON dept.id=ctn.departName  ");
	query.append(" WHERE " + tableAlis + ".id IN("+selectedId+")");
	
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	String orgDetail = (String)session.get("orgDetail");
	String orgName="";
	if (orgDetail!=null && !orgDetail.equals("")) 
	{
	orgName = orgDetail.split("#")[0];
	}
	if (dataList!=null && titleList!=null && keyList!=null)
	{
	excelFileName=new ComplianceExcelDownload().writeDataInExcel(dataList,titleList,keyList,excelName,orgName,true,connectionSpace);
	}
	if (employee!=null && !employee.equalsIgnoreCase("") && !employee.equalsIgnoreCase("undefined"))
	{
	
	String mailSubject="Operation Task Detail";
	List emaildata= cbt.executeAllSelectQuery("SELECT emailIdOne FROM employee_basic WHERE id IN("+employee+")", connectionSpace);
	if (emaildata!=null && emaildata.size()>0)
	{
	emailIds=new StringBuilder();
	for (Iterator iterator = emaildata.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	emailIds.append(object.toString()+",");
	}
	}
	String mailText=null;
	if (dataList!=null && titleList!=null && keyList!=null)
	{
	mailText=getConfigMail(dataList,titleList,keyList,mailSubject);
	}
	String str[]=emailIds.toString().split(",");
	if (str!=null)
	{
	for(int j=0;j<str.length;j++)
	{
	if(str[j]!=null && !str[j].equalsIgnoreCase(""))
	{
	cmnHelper.addMail("","",str[j],mailSubject, mailText,"","Pending", "0",excelFileName,"Compliance",connectionSpace);
	}
	}
	}
	}
	File file = new File(excelFileName);
	if(excelFileName!=null)
	{
	excelStream=new FileInputStream(excelFileName);
	}
	if (excelFileName!=null) 
	{
	excelFileName=excelFileName.substring(excelFileName.lastIndexOf("//")+2, excelFileName.length());
	excelFileName = file.getName();
	}
	if(emailIds!=null && emailIds.length()>2 && !emailIds.toString().equalsIgnoreCase("undefined,"))
	{
	returnResult="SENDMAILSUCCESS";
	addActionMessage("Send Mail Successfully ...");
	}
	else 
	{
	returnResult=SUCCESS;
	}
	}
	}
	else
	{
	addActionMessage("There are some error in data!!!!");
	returnResult = ERROR;
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ViewLastMonthCompData()
	{
	String retunString=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List  data=null;
	List<Object> Listhb=new ArrayList<Object>();
	//String compIdSeries=null;
	List  dataCount=null;
	/*if(deptId.equalsIgnoreCase("All"))
	{
	dataCount=cbt.executeAllSelectQuery("SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task AS ctn ON comp.taskName=ctn.id WHERE comp.id IN("+compId+")",connectionSpace);
	}
	else
	{
	dataCount=cbt.executeAllSelectQuery("SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task AS ctn ON comp.taskName=ctn.id WHERE ctn.departName IN("+deptId+") AND comp.id IN("+compId+")",connectionSpace);
	}
	if(dataCount!=null && dataCount.size()>0)
	{
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
	{
	Object object = (Object) iterator.next();
	strBuilder.append(object.toString()+",");
	
	}
	}
	if(strBuilder!=null && !strBuilder.toString().equalsIgnoreCase(""))
	{
	compIdSeries=strBuilder.toString().substring(0, strBuilder.toString().length()-1);
	}*/
	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from compliance_report");
	dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	if(dataCount!=null && dataCount.size()>0)
	{
	List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
	session.remove("masterViewProp");
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	int i=0;
	if(fieldNames!=null && fieldNames.size()>0)
	{
	for(GridDataPropertyView gpv:fieldNames)
	{
	if(i<(fieldNames.size()-1))
	{
	if(gpv.getColomnName()!=null && gpv.getColomnName().equalsIgnoreCase("id"))
	{
	query.append("compReport.id,");
	}
	else
	{
	query.append(gpv.getColomnName().toString()+",");
	}
	}
	else
	{
	query.append(gpv.getColomnName().toString());
	}
	i++;
	}
	query.append(" FROM compliance_report AS compReport " +
	"INNER JOIN useraccount AS userAC ON userAC.userID=compReport.userName " +
	"INNER JOIN compliance_details AS comp ON comp.id=compReport.complID  " +
	"INNER JOIN compliance_task AS ctn ON ctn.id=comp.taskName  " +
	"INNER JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType  " +
	"INNER JOIN department AS dept ON dept.id=ctn.departName  " +
	"WHERE comp.id IN("+compId+")");
	
	if(actionStatus!=null && actionStatus.equalsIgnoreCase("Pending"))
	{
	query.append(" AND compReport.actionTakenDate BETWEEN '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
	query.append(" AND (compReport.actionTaken='Pending' || compReport.actionTaken='Snooze' || compReport.actionTaken='Reschedule')");
	}
	else if(actionStatus!=null && actionStatus.equalsIgnoreCase("Done"))
	{
	query.append(" AND compReport.actionTakenDate BETWEEN '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
	query.append(" AND (compReport.actionTaken='Done' || compReport.actionTaken='Recurring')");
	}
	else if(actionStatus!=null && (actionStatus.equalsIgnoreCase("Level1") || actionStatus.equalsIgnoreCase("Level2") || actionStatus.equalsIgnoreCase("Level3") || actionStatus.equalsIgnoreCase("Level4") || actionStatus.equalsIgnoreCase("Level5")))
	{
	query.append(" AND (compReport.actionTaken='Pending' || compReport.actionTaken='Recurring') AND compReport.current_esc_level='"+actionStatus.charAt(5)+"'");
	}
	
	query.append(" ORDER BY compReport.actionTakenDate ASC");
	data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
	
	if(data!=null)
	{
	Object[] obdata=null;
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	int k=0;
	obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for(GridDataPropertyView gpv:fieldNames)
	{
	if(obdata[k]!=null)
	{
	if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else if(gpv.getColomnName().equalsIgnoreCase("document_takeaction") || gpv.getColomnName().equalsIgnoreCase("document_config_1") || gpv.getColomnName().equalsIgnoreCase("document_config_2"))
	{
	String str=obdata[k].toString().substring(obdata[k].toString().lastIndexOf("//")+2, obdata[k].toString().length());
	String docName=str.substring(14,str.length());
	one.put(gpv.getColomnName(),docName);
	}
	else if(gpv.getColomnName().equalsIgnoreCase("comp.reminder_for") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_1") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_2") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_3") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_4"))
	{
	StringBuilder empName= new StringBuilder();
	String empId=obdata[k].toString().replace("#", ",").substring(0,(obdata[k].toString().length()-1));
	String query2="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
	List data1=cbt.executeAllSelectQuery(query2,connectionSpace );
	for (Iterator iterator = data1.iterator(); iterator.hasNext();) 
	{
	Object object = (Object) iterator.next();
	empName.append(object.toString()+", ");
	}
	one.put(gpv.getColomnName(),empName.toString().substring(0, empName.toString().length()-2));
	}
	else if(gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
	{
	one.put(gpv.getColomnName(),new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
	}
	else
	{
	one.put(gpv.getColomnName(),obdata[k].toString());
	}
	if(gpv.getColomnName().equalsIgnoreCase("compReport.dueDate") || gpv.getColomnName().equalsIgnoreCase("compReport.actionTakenDate") )
	{
	one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+" "+obdata[k+1].toString());
	}
	}
	else
	{
	one.put(gpv.getColomnName().toString(), "NA");
	}
	k++;
	
	}
	Listhb.add(one);
	}
	setMasterViewList(Listhb);
	setRecords(masterViewList.size());
	int to = (getRows() * getPage());
	@SuppressWarnings("unused")
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	return SUCCESS;
	}
	}
	}
	retunString=SUCCESS;
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method ViewCompReportData of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	else
	{
	retunString=LOGIN;
	}
	return retunString;
	}
	@SuppressWarnings("rawtypes")
	public String getConfigMail(List dataList,List titleList,List keyList,String mailTitle) 
    {
    	  StringBuilder mailtext = new StringBuilder("");
	 
	  mailtext.append("<br><b>Hello,</b><br>");
	  mailtext.append(mailTitle);
	  mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
	  mailtext.append("<tr  bgcolor='#e8e7e8'>");
	  for(int i=0;i<titleList.size();i++)
	  {
	  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+titleList.get(i).toString().trim().replace(" ", "&nbsp;")+ "</b></td>");
	  }
	  mailtext.append("</tr>");
	  	if (dataList!=null && dataList.size()>0)
            {
	    Object[] object=null;
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'>");
	object = (Object[]) iterator.next();
	for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
	{
	String dataValue = null;
	if(object[cellIndex]!=null)
	{
	if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	dataValue=DateUtil.convertDateToIndianFormat(object[cellIndex].toString());
	}
	else
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("comp.reminder_for") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_1") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_2") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_3") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_4"))
	{
	String empId=object[cellIndex].toString().replace("#", ",").substring(0,(object[cellIndex].toString().toString().length()-1));
	String query="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
	List data=new createTable().executeAllSelectQuery(query,connectionSpace);
	if(data!=null && data.size()>0)
	{
	StringBuilder builder=new StringBuilder();
	for(int i=0;i<data.size();i++)
	{
	builder.append(data.get(i).toString()+",");
	}
	dataValue=builder.toString().substring(0, builder.toString().length()-1);
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("comp.frequency"))
	{
	if(object[cellIndex].toString().equalsIgnoreCase("W"))
	dataValue="Weekly";
	else if(object[cellIndex].toString().equalsIgnoreCase("OT"))
	dataValue="One Time";
	else if(object[cellIndex].toString().equalsIgnoreCase("D"))
	dataValue="Daily";
	else if(object[cellIndex].toString().equalsIgnoreCase("M"))
	dataValue="Monthly";
	else if(object[cellIndex].toString().equalsIgnoreCase("BM"))
	dataValue="Bi-Monthly";
	else if(object[cellIndex].toString().equalsIgnoreCase("Q"))
	dataValue="Quaterly";
	else if(object[cellIndex].toString().equalsIgnoreCase("HY"))
	dataValue="Half Yearly";
	else if(object[cellIndex].toString().equalsIgnoreCase("Y"))
	dataValue="Yearly";
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("compReport.userName"))
	{
	try
	{
	dataValue=DateUtil.makeTitle((Cryptography.decrypt(object[cellIndex].toString(),DES_ENCRYPTION_KEY)));
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	}
	else
	{
	dataValue=object[cellIndex].toString();
	}
	}
	}
	else
	{
	dataValue="NA";
	}
	mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+dataValue.trim().replace(" ", "&nbsp;")+"</td>");
	}
	mailtext.append("</tr>");
	}
           }
	  mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	  mailtext.append("<BR>");
	  mailtext.append("<BR>");
	  mailtext.append("<br>");
	  mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	  return mailtext.toString();
    }
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getDeptByMappedEmp(String logedContId, String logedEmpid, String moduleName, String keyWith)
	{
	List contactList = new ArrayList();
	Map<String, String> tempMap = new LinkedHashMap<String, String>();
	StringBuilder deptId = new StringBuilder();
	
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String contactId=new ComplianceCommonOperation().getMappedAllContactId(logedEmpid,"COMPL");
	List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for,taskName FROM compliance_details", connectionSpace);
	if (contactId != null)
	{
	String str[] = contactId.split(",");
	for (int i = 0; i < str.length; i++)
	{
	contactList.add("#" + str[i] + "#");
	}
	}
	contactList.add("#" + logedContId + "#");
	StringBuilder taskNameId = new StringBuilder();
	if (dataCount != null && dataCount.size() > 0)
	{
	Object object[]=null;
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
	{
	object = (Object[]) iterator.next();
	String str = "#";
	str = str + object[1].toString();
	for (int i = 0; i < contactList.size(); i++)
	{
	if (str.contains(contactList.get(i).toString()))
	{
	taskNameId.append(object[2].toString() + ",");
	}
	}

	}
	}
	if (taskNameId != null && !taskNameId.toString().equalsIgnoreCase(""))
	{
	String taskId = taskNameId.substring(0, (taskNameId.toString().length() - 1));
	StringBuilder queryString = new StringBuilder();
	queryString.append("select dept.id,dept.deptName from department as dept ");
	queryString.append("INNER JOIN compliance_task as task on dept.id=task.departName ");
	queryString.append("INNER JOIN compliance_details as comp on task.id=comp.taskName ");
	queryString.append("WHERE task.id IN(" + taskId + ")" + "group by dept.deptName");
	
	List deptList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
	if(deptList!=null && deptList.size()>0)
	{
	for (Iterator iterator = deptList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if(object[0]!=null && object[1]!=null)
	deptId.append(object[0].toString()+",");
	
	}
	deptId.append("0");
	System.out.println(" >>>>>> "+deptId);
	if(keyWith!=null && keyWith.equalsIgnoreCase("All"))
	tempMap.put("All"+deptId.toString(), "All Dept");
	
	for (Iterator iterator = deptList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if(object[0]!=null && object[1]!=null)
	tempMap.put(object[0].toString(), object[1].toString());
	
	}
	}
	}
	return tempMap;
	}
	
	
	
	public String getMainHeaderName() {
	return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
	this.mainHeaderName = mainHeaderName;
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
	public String getFromDate() {
	return fromDate;
	}
	public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
	}
	public String getToDate() {
	return toDate;
	}
	public void setToDate(String toDate) {
	this.toDate = toDate;
	}

	public String getMinCount() {
	return minCount;
	}

	public void setMinCount(String minCount) {
	this.minCount = minCount;
	}

	public String getMaxCount() {
	return maxCount;
	}

	public void setMaxCount(String maxCount) {
	this.maxCount = maxCount;
	}
	public String getActionStatus() {
	return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
	this.actionStatus = actionStatus;
	}
	public String getCompReportId() {
	return compReportId;
	}
	public void setCompReportId(String compReportId) {
	this.compReportId = compReportId;
	}
	public InputStream getFileInputStream() {
	return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
	this.fileInputStream = fileInputStream;
	}
	public String getFileName() {
	return fileName;
	}
	public void setFileName(String fileName) {
	this.fileName = fileName;
	}
	public String getDocumentName() {
	return documentName;
	}
	public void setDocumentName(String documentName) {
	this.documentName = documentName;
	}
	public Map<String, String> getDeptName() {
	return deptName;
	}
	public void setDeptName(Map<String, String> deptName) {
	this.deptName = deptName;
	}
	public boolean isHighLevel() {
	return highLevel;
	}
	public void setHighLevel(boolean highLevel) {
	this.highLevel = highLevel;
	}
	public String getDeptId() {
	return deptId;
	}
	public void setDeptId(String deptId) {
	this.deptId = deptId;
	}
	public String getTableName() {
	return tableName;
	}
	public void setTableName(String tableName) {
	this.tableName = tableName;
	}
	public String getExcelName() {
	return excelName;
	}
	public void setExcelName(String excelName) {
	this.excelName = excelName;
	}
	public String getSelectedId() {
	return selectedId;
	}
	public void setSelectedId(String selectedId) {
	this.selectedId = selectedId;
	}
	
	public String getEmployee()
	{
	return employee;
	}

	public void setEmployee(String employee)
	{
	this.employee = employee;
	}

	public String getTableAlis() {
	return tableAlis;
	}
	public void setTableAlis(String tableAlis) {
	this.tableAlis = tableAlis;
	}
	public String[] getColumnNames() {
	return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
	this.columnNames = columnNames;
	}
	public Map<String, String> getColumnMap() {
	return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
	this.columnMap = columnMap;
	}
	public String getExcelFileName() {
	return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
	this.excelFileName = excelFileName;
	}
	public FileInputStream getExcelStream() {
	return excelStream;
	}
	public void setExcelStream(FileInputStream excelStream) {
	this.excelStream = excelStream;
	}
	public InputStream getInputStream() {
	return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
	}
	public String getContentType() {
	return contentType;
	}
	public void setContentType(String contentType) {
	this.contentType = contentType;
	}
	public String getSearchPeriodOn() {
	return searchPeriodOn;
	}
	public void setSearchPeriodOn(String searchPeriodOn) {
	this.searchPeriodOn = searchPeriodOn;
	}
	public Map<String, String> getDocMap() {
	return docMap;
	}
	public void setDocMap(Map<String, String> docMap) {
	this.docMap = docMap;
	}
	public String getFrequency() {
	return frequency;
	}
	public void setFrequency(String frequency) {
	this.frequency = frequency;
	}
	public String getDivName() {
	return divName;
	}
	public void setDivName(String divName) {
	this.divName = divName;
	}
	public String getCompId() {
	return compId;
	}
	public void setCompId(String compId) {
	this.compId = compId;
	}

	public String getCallRecurring()
	{
	return callRecurring;
	}

	public void setCallRecurring(String callRecurring)
	{
	this.callRecurring = callRecurring;
	}
	
}