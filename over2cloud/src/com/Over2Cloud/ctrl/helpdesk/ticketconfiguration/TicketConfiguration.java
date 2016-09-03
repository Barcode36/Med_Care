package com.Over2Cloud.ctrl.helpdesk.ticketconfiguration;

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
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class TicketConfiguration extends GridPropertyBean implements ServletRequestAware{
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	
	private HttpServletRequest request;
	private String deptid;
	
	private String ticket_type;
	private String n_series;
	private String deptName;
	private String d_series;
	private String floor_status;
	private String prefix;
	private String flag;
	private String dataFor;
	private List<Object> ticketSeriesData;
	
	private Map<Integer, String> serviceDeptList = null;
	private Map<String, String> ticketSeriesType = null;
	
	// Method for setting Column Name before setting (In Use)
	public String beforeTicketSeriesView()
	 {
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) 
	 {
	try 
	 {
	if (userName==null || userName.equalsIgnoreCase("")) 
	return LOGIN;
	
	
	boolean table_exist = new HelpdeskUniversalHelper().checkTable("ticket_series_conf", connectionSpace);
	if (table_exist) {
	String ticket_type="";
	ticket_type = new HelpdeskUniversalAction().getData("ticket_series_conf", "ticket_type", "moduleName", dataFor);
	if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("N")) {
	setGridColomnNames(ticket_type);
	returnResult=SUCCESS;	
	}
	else if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("D")) {
	setGridColomnNames(ticket_type);
	returnResult="deptSUCCESS";	
	}
	else {
	setGridColomnNames("N");
	returnResult=SUCCESS;	
	}
	}
	else {
	setGridColomnNames("N");
	returnResult=SUCCESS;	
	}
	 } catch (Exception e) {
	e.printStackTrace();
	}
	 }
	return returnResult;
	}
	
	// Method for setting Column Name before setting (In Use)
	@SuppressWarnings("unchecked")
	public String beforeTicketSeriesAdd()
	 {
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) 
	 {
	try 
	 {
	if (userName==null || userName.equalsIgnoreCase("")) 
	return LOGIN;
	
	if (dataFor!=null && !dataFor.equals("")) {
	serviceDeptList = new LinkedHashMap<Integer, String>();
	List departmentlist = new HelpdeskUniversalHelper().getServiceDept_SubDept(orgLevel, orgId,dataFor, connectionSpace);
	if (departmentlist != null && departmentlist.size() > 0) {
	for (Iterator iterator = departmentlist.iterator(); iterator
	.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	serviceDeptList.put((Integer) object1[0], object1[1].toString());
	}
	returnResult = SUCCESS;
	 }
	}
	    getTicketSeriesFields();
	returnResult = SUCCESS;
	 } catch (Exception e) {
	e.printStackTrace();
	}
	 }
	return returnResult;
	}
	
	
	  public void getTicketSeriesFields() {
	ConfigurationUtilBean obj;
	pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
	ticketSeriesType = new LinkedHashMap<String, String>();
	List<GridDataPropertyView> ticketSeriesList = Configuration.getConfigurationData("mapped_ticket_panel_configuration", accountID,connectionSpace, "", 0, "table_name","ticket_panel_configuration");
	if (ticketSeriesList != null && ticketSeriesList.size() > 0) {
	for (GridDataPropertyView gdv : ticketSeriesList) {
	obj = new ConfigurationUtilBean();
	if (gdv.getColomnName()!=null && !gdv.getColomnName().equals("") && !gdv.getColomnName().equals("moduleName")) {
	obj.setKey(gdv.getColomnName());
	obj.setValue(gdv.getHeadingName());
	obj.setValidationType(gdv.getValidationType());
	obj.setColType(gdv.getColType());
	if (gdv.getMandatroy().toString().equals("1")) {
	obj.setMandatory(true);
	} else {
	obj.setMandatory(false);
	}
	pageFieldsColumns.add(obj);
	}
	 }
	 }
	String query = "SELECT ticket_type FROM ticket_series_conf WHERE moduleName='"+dataFor+"'";
	List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	if(dataList.get(0).toString().equalsIgnoreCase("D"))
	{
	ticketSeriesType.put("D", "Dept Wise");
	ticketSeriesType.put("A", "All Dept");
	}
	}
	else
	{
	ticketSeriesType.put("N", "Normal");
	ticketSeriesType.put("D", "Dept Wise");
	ticketSeriesType.put("A", "All Dept");
	}
	/*
	  if (pageFieldsColumns!=null && pageFieldsColumns.size()>0)SSS {
	for (ConfigurationUtilBean kkk : pageFieldsColumns) {
	System.out.println("Fields name "+kkk.getKey());
	}
	  }*/
	  }
	
	
	
	// Method for set Grid Columns Name (In Use)
	 public void setGridColomnNames(String viewTpye)
	 {
	   viewPageColumns = new ArrayList<GridDataPropertyView>();
	       GridDataPropertyView gdpv = new GridDataPropertyView();
	       gdpv.setColomnName("id");
	       gdpv.setHeadingName("Id");
	       gdpv.setHideOrShow("true");
	       viewPageColumns.add(gdpv);
	       
	        // Column Name set for Sub Departments
	List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_ticket_panel_configuration","",connectionSpace,"",0,"table_name","ticket_panel_configuration");
	    if (viewTpye.equalsIgnoreCase("N"))
	 {
	   	if(statusColName!=null && statusColName.size()>0)
	 {
	   for(GridDataPropertyView gdp:statusColName)
	 {
	if (!gdp.getColomnName().equals("deptName") && !gdp.getColomnName().equals("prefix")
	&& !gdp.getColomnName().equals("prefix_count") && !gdp.getColomnName().equals("floor_status")
	&& !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("d_series")) {
	gdpv=new GridDataPropertyView();
	gdpv.setColomnName(gdp.getColomnName());
	gdpv.setHeadingName(gdp.getHeadingName());
	gdpv.setEditable(gdp.getEditable());
	gdpv.setSearch(gdp.getSearch());
	gdpv.setAlign("Left");
	gdpv.setWidth(400);
	gdpv.setHideOrShow(gdp.getHideOrShow());
	viewPageColumns.add(gdpv);
	 }
	   }
	   }
	   }
	    else if (viewTpye.equalsIgnoreCase("D"))
	 {
	   	if(statusColName!=null && statusColName.size()>0)
	 {
	   for(GridDataPropertyView gdp:statusColName)
	 {
	if (!gdp.getColomnName().equals("ticket_type") && !gdp.getColomnName().equals("n_series") && !gdp.getColomnName().equals("floor_status") && !gdp.getColomnName().equals("moduleName")) {
	gdpv=new GridDataPropertyView();
	gdpv.setColomnName(gdp.getColomnName());
	gdpv.setHeadingName(gdp.getHeadingName());
	gdpv.setEditable(gdp.getEditable());
	gdpv.setSearch(gdp.getSearch());
	gdpv.setAlign("Left");
	gdpv.setWidth(200);
	gdpv.setHideOrShow(gdp.getHideOrShow());
	viewPageColumns.add(gdpv);
	 }
	   }
	   }
	   }
	   /* if (viewPageColumns!=null && viewPageColumns.size()>0) {
	for (GridDataPropertyView obb : viewPageColumns) {
	System.out.println("Column  "+obb.getColomnName());
	}
	}*/
	}

	public String getPrefixViaAjax()
	{
	try {
	String str= new HelpdeskUniversalAction().getData("department", "deptName", "id", getDeptid());
	    if (str!=null && !str.equals("")) {
	    	if (dataFor!=null && dataFor.equalsIgnoreCase("COMPL")) {
	    	prefix="C"+str.substring(0, 2).toUpperCase()+"D";
	}
	else {
	prefix=str.substring(0, 2).toUpperCase();
	}
	}
	    else {
	prefix="NA";
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	
	
	// Method for adding the Shift Detail
	public String addTicketSeries()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
	try {
	      CommonOperInterface cot = new CommonConFactory().createInterface();
	      boolean updateflag =false;
	      
	      if (ticket_type.equalsIgnoreCase("N") || ticket_type.equalsIgnoreCase("D")) {
	    	  List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
                  TableColumes tc = new TableColumes();
	  
	  tc.setColumnname("ticket_type");
	  tc.setDatatype("varchar(5)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("n_series");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("moduleName");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("abb");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("prefix");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  
	  
	  tc = new TableColumes();
	  tc.setColumnname("frequency");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("substring_length");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  @SuppressWarnings("unused")
	  boolean aa=  cot.createTable22("ticket_series_conf", tablecolumn, connectionSpace);
	  }
	      if (ticket_type.equalsIgnoreCase("D")) {
	    	  List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
	    	  TableColumes tc = new TableColumes();
	  
	  tc.setColumnname("deptName");
	  tc.setDatatype("varchar(50)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("d_series");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  
	  
	  tc = new TableColumes();
	  tc.setColumnname("floor_status");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("moduleName");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("abb");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("frequency");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("deptfit");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("prefix");
	  tc.setDatatype("varchar(20)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("substring_length");
	  tc.setDatatype("varchar(20)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(25)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tablecolumn.add(tc);
	  
	  @SuppressWarnings("unused")
	  boolean aa=  cot.createTable22("dept_ticket_series_conf", tablecolumn, connectionSpace);
	  }
	      
	      if (ticket_type.equalsIgnoreCase("N") || ticket_type.equalsIgnoreCase("D")) {
	    	boolean flag = new HelpdeskUniversalHelper().isExist("ticket_series_conf", "moduleName",dataFor, connectionSpace);
	    	  if (!flag) {
	    	   List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=new InsertDataTable();
	 int length=0;
	      
	 ob.setColName("ticket_type");
	 ob.setDataName(ticket_type);
	 insertData.add(ob);
	 
	 if (ticket_type.equalsIgnoreCase("N")){
	 ob=new InsertDataTable();
	 ob.setColName("n_series");
	 ob.setDataName(n_series);
	 insertData.add(ob);
	 
	 if(request.getParameter("abb")!=null && !request.getParameter("abb").equals(" "))
	 {
	 ob=new InsertDataTable();
	 ob.setColName("abb");
	 ob.setDataName(request.getParameter("abb"));
	 insertData.add(ob);
	 length+=request.getParameter("abb").length();
	 }
	 
	 if(request.getParameter("frequency")!=null && !request.getParameter("frequency").equals(" ") && !request.getParameter("frequency").equalsIgnoreCase("No"))
	 {
	 ob=new InsertDataTable();
	 ob.setColName("frequency");
	 ob.setDataName(request.getParameter("frequency"));
	 insertData.add(ob);
	 length+=1;
	 }
	 if(length>0)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("substring_length");
	 ob.setDataName(length);
	 insertData.add(ob);
	 }
	 if(length>0)
	 {
	 prefix = prefix.substring(0,length);
	 }
	 ob=new InsertDataTable();
	 ob.setColName("prefix");
	 ob.setDataName(prefix);
	 insertData.add(ob);
	 
	 }
	 else if (ticket_type.equalsIgnoreCase("D")) {
	 ob=new InsertDataTable();
	 ob.setColName("n_series");
	 ob.setDataName("NA");
	 insertData.add(ob);
	}
	 
	 ob=new InsertDataTable();
	 ob.setColName("moduleName");
	 ob.setDataName(dataFor);
	 insertData.add(ob);
	 
	         ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 
	 updateflag=cot.insertIntoTable("ticket_series_conf",insertData,connectionSpace);
	     }
	      }
	      
	      if (ticket_type.equalsIgnoreCase("D")) {
	    	  boolean check = new HelpdeskUniversalHelper().isExist("ticket_series_conf", "ticket_type", "D","moduleName",dataFor,"","", connectionSpace);
	    	  if (check) {
	    	  boolean flag= new HelpdeskUniversalHelper().isExist("dept_ticket_series_conf", "deptName", deptName,"moduleName",dataFor,"","", connectionSpace);
	    	  if (!flag) {
	         List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=new InsertDataTable();
	 int length=0;
	 
	    	     ob.setColName("deptName");
	 ob.setDataName(deptName);
	 insertData.add(ob);
	 
	 
	 
	 ob=new InsertDataTable();
	 ob.setColName("d_series");
	 ob.setDataName(d_series);
	 insertData.add(ob);
	 

	 if(request.getParameter("abb")!=null && !request.getParameter("abb").equals(" "))
	 {
	 ob=new InsertDataTable();
	 ob.setColName("abb");
	 ob.setDataName(request.getParameter("abb"));
	 insertData.add(ob);
	 length+=request.getParameter("abb").length();
	 }
	 
	 if(request.getParameter("frequency")!=null && !request.getParameter("frequency").equals(" ") && !request.getParameter("frequency").equalsIgnoreCase("No"))
	 {
	 ob=new InsertDataTable();
	 ob.setColName("frequency");
	 ob.setDataName(request.getParameter("frequency"));
	 insertData.add(ob);
	 length+=1;
	 }
	 
	 
	 
	 ob=new InsertDataTable();
	 ob.setColName("deptfit");
	 ob.setDataName(request.getParameter("deptfit"));
	 insertData.add(ob);
	 length+=Integer.parseInt(request.getParameter("deptfit"));
	 
	 if(length>0)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("substring_length");
	 ob.setDataName(length);
	 insertData.add(ob);
	 }
	 
	 if(length>0)
	 {
	 prefix = prefix.substring(0,length);
	 }
	 ob=new InsertDataTable();
	 ob.setColName("prefix");
	 ob.setDataName(prefix);
	 insertData.add(ob);
	 
	 
	 
	 ob=new InsertDataTable();
	 ob.setColName("floor_status");
	 ob.setDataName(floor_status);
	 insertData.add(ob);
	 
	         ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("moduleName");
	 ob.setDataName(dataFor);
	 insertData.add(ob);
	 updateflag=cot.insertIntoTable("dept_ticket_series_conf",insertData,connectionSpace);
	     }
	    	  }
	      else {
	    	  updateflag = false;
	  }
	   }
	      
	     if(ticket_type.equalsIgnoreCase("A"))
	      {
	    	 List departmentlist = new HelpdeskUniversalHelper().getServiceDept_SubDept(orgLevel, orgId,dataFor, connectionSpace);
	if (departmentlist != null && departmentlist.size() > 0) {
	for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
	{
	Object[] object1 = (Object[]) iterator.next();
	boolean check = new HelpdeskUniversalHelper().isExist("ticket_series_conf", "ticket_type", "D","moduleName",dataFor,"","", connectionSpace);
	    	  if (check) {
	    	  boolean flag= new HelpdeskUniversalHelper().isExist("dept_ticket_series_conf", "deptName", object1[0].toString(),"moduleName",dataFor,"","", connectionSpace);
	    	  if (!flag) {
	         List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=new InsertDataTable();
	 StringBuilder starBuilder=new StringBuilder();
	 int length=0;
	 
	    	     ob.setColName("deptName");
	 ob.setDataName(object1[0].toString());
	 insertData.add(ob);
	 
	 
	 
	 ob=new InsertDataTable();
	 ob.setColName("d_series");
	 ob.setDataName(d_series);
	 insertData.add(ob);
	 

	 if(request.getParameter("abb")!=null && !request.getParameter("abb").equals(" "))
	 {
	 ob=new InsertDataTable();
	 ob.setColName("abb");
	 ob.setDataName(request.getParameter("abb"));
	 insertData.add(ob);
	 length+=request.getParameter("abb").length();
	 starBuilder.append(request.getParameter("abb"));
	 }
	 
	 ob=new InsertDataTable();
	 ob.setColName("deptfit");
	 ob.setDataName(request.getParameter("deptfit"));
	 insertData.add(ob);
	 length+=Integer.parseInt(request.getParameter("deptfit"));
	 starBuilder.append(object1[1].toString().substring(0, Integer.parseInt(request.getParameter("deptfit"))));
	 
	 if(request.getParameter("frequency")!=null && !request.getParameter("frequency").equals(" ") && !request.getParameter("frequency").equalsIgnoreCase("No"))
	 {
	 ob=new InsertDataTable();
	 ob.setColName("frequency");
	 ob.setDataName(request.getParameter("frequency"));
	 insertData.add(ob);
	 length+=1;
	 starBuilder.append("F");
	 }
	 
	 
	 
	
	 
	 if(length>0)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("substring_length");
	 ob.setDataName(length);
	 insertData.add(ob);
	 }
	 
	 if(length>0)
	 {
	 prefix = starBuilder.toString().substring(0,length).toUpperCase();
	 }
	 ob=new InsertDataTable();
	 ob.setColName("prefix");
	 ob.setDataName(prefix);
	 insertData.add(ob);
	 
	 
	 
	 ob=new InsertDataTable();
	 ob.setColName("floor_status");
	 ob.setDataName(floor_status);
	 insertData.add(ob);
	 
	         ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 
	 ob=new InsertDataTable();
	 ob.setColName("moduleName");
	 ob.setDataName(dataFor);
	 insertData.add(ob);
	 updateflag=cot.insertIntoTable("dept_ticket_series_conf",insertData,connectionSpace);
	     }
	    	  }
	      else {
	    	  updateflag = false;
	  }
	}
	returnResult = SUCCESS;
	 }
	      }
	  if (updateflag) {
	addActionMessage("Ticket Series Added Successfully !!");
	}
	  else {
	addActionError("May be Ticket Series  Alredy Added OR Some Problem in Adding Ticket Series !!");
	}
	//}
	  	returnResult = SUCCESS;
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	else {
	returnResult = LOGIN;
	}
	return returnResult;	
	}
	
	
	

	@SuppressWarnings("unchecked")
	public String ticketSeriesView()
	 {
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	ticketSeriesData=new ArrayList<Object>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	//getting the list of colmuns
	StringBuilder query=new StringBuilder("");
	List fieldNames= new ArrayList();
	String ticket_type="";
	List  data=null;
	
	boolean table_exist = new HelpdeskUniversalHelper().checkTable("ticket_series_conf", connectionSpace);
	if (table_exist) 
	 {
	   ticket_type = new HelpdeskUniversalAction().getData("ticket_series_conf", "ticket_type", "moduleName", dataFor);
	if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("N")) {
	query.append(" select id,ticket_type,n_series from ticket_series_conf where moduleName='"+dataFor+"'");
	}
	else if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("D")) {
	query.append(" select dept_series.id,dept.deptName,dept_series.prefix,dept_series.d_series from dept_ticket_series_conf as dept_series");
	query.append(" inner join department as dept on  dept_series.deptName=dept.id");
	query.append(" where dept_series.moduleName='"+dataFor+"'");
	}
	else {
	query.append(" select id,ticket_type,n_series from ticket_series_conf where moduleName='"+dataFor+"'");
	}
	data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	  }
	
	if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equals("N"))
	 {
	fieldNames.add("id");
	fieldNames.add("ticket_type");
	fieldNames.add("n_series");
	 }
	else if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equals("D"))
	 {
	fieldNames.add("id");
	fieldNames.add("deptName");
	fieldNames.add("prefix");
	fieldNames.add("d_series");
	 }
	
	if(data!=null && data.size()>0)
	{
	
	setRecords(data.size());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	
	List<Object> Listhb=new ArrayList<Object>();
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for (int k = 0; k < fieldNames.size(); k++) {
	if(obdata[k]!=null)
	{
	if(k==0)
	{
	one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
	}
	else
	{
	one.put(fieldNames.get(k).toString(), obdata[k].toString());
	}
	}
	}
	Listhb.add(one);
	}
	setTicketSeriesData(Listhb);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	returnResult=SUCCESS;
	}
	catch(Exception e)
	{
	returnResult=ERROR;
	e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public String editTicketSeries()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	if(getOper().equalsIgnoreCase("edit"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	Map<String, Object>wherClause=new HashMap<String, Object>();
	Map<String, Object>condtnBlock=new HashMap<String, Object>();
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	while (it.hasNext())
	{
	String parmName = it.next().toString();
	String paramValue=request.getParameter(parmName);
	if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
	&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("dataFor") && !parmName.equalsIgnoreCase("rowid") && !parmName.equalsIgnoreCase("flag"))
	wherClause.put(parmName, paramValue);
	}
	wherClause.put("userName", userName);
	wherClause.put("date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("time", DateUtil.getCurrentTime());
	
	condtnBlock.put("id",getId());
	
	 ticket_type = new HelpdeskUniversalAction().getData("ticket_series_conf", "ticket_type", "moduleName", dataFor);
	if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("N")) {
	cbt.updateTableColomn("ticket_series_conf", wherClause, condtnBlock,connectionSpace);
	}
	else if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("D")) {
	cbt.updateTableColomn("dept_ticket_series_conf", wherClause, condtnBlock,connectionSpace);
	}
	returnResult=SUCCESS;
	}
	else if(getOper().equalsIgnoreCase("del"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String tempIds[]=getId().split(",");
	StringBuilder condtIds=new StringBuilder();
	int i=1;
	for(String H:tempIds)
	 {
	if(i<tempIds.length)
	condtIds.append(H+" ,");
	else
	condtIds.append(H);
	i++;
	 }
	
	   ticket_type = new HelpdeskUniversalAction().getData("ticket_series_conf", "ticket_type", "moduleName", dataFor);
	if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("N")) {
	cbt.deleteAllRecordForId("ticket_series_conf", "id", condtIds.toString(), connectionSpace);
	}
	else if (ticket_type!=null && !ticket_type.equals("") && ticket_type.equalsIgnoreCase("D")) {
	cbt.deleteAllRecordForId("dept_ticket_series_conf", "id", condtIds.toString(), connectionSpace);
	}
	}
	}
	catch(Exception e)
	{
	returnResult=ERROR;
	 e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	public synchronized String getTicketSeries(String moduleName,String deptId)
	{
	String finalTicketSeries="NA";
	int substringLength=0;
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String query="SELECT ticket_type,n_series,prefix,substring_length FROM ticket_series_conf WHERE moduleName='"+moduleName+"'";
	//System.out.println("Q1 "+query);
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	if(object[0]!=null && object[0].toString().equalsIgnoreCase("D"))
	{
	// department wise ticket series
	query ="SELECT prefix,d_series,substring_length FROM dept_ticket_series_conf WHERE moduleName='"+moduleName+"' AND deptName="+deptId;
	//System.out.println("Q2 "+query);

	List tempDataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(tempDataList!=null && tempDataList.size()>0)
	{
	Object[] obj=(Object[])tempDataList.get(0);
	substringLength = Integer.valueOf(obj[2].toString());
	finalTicketSeries = getMaxDeptWiseTicketSeries(moduleName,cbt,substringLength,deptId);
	//System.out.println("final   "+finalTicketSeries);

	if(finalTicketSeries==null)
	{
	
	if(obj[0]!=null && !obj[0].equals(""))
	{
	finalTicketSeries = obj[0].toString();
	}
	if(obj[1]!=null && !obj[1].equals(""))
	{
	finalTicketSeries=finalTicketSeries+String.valueOf(obj[1].toString());
	}
	}
	/*else
	{
	if(object[1]!=null && !object[1].equals("") && object[2]!=null && !object[2].equals(""))
	{
	finalTicketSeries =object[2].toString()+object[1].toString();
	}
	else
	{
	finalTicketSeries =object[1].toString();
	}
	}*/
	}
	}
	else if(object[0]!=null && object[0].toString().equalsIgnoreCase("N"))
	{
	// Normal Ticket Series
	if(object[3]!=null && !object[3].equals(""))
	substringLength = Integer.valueOf(object[3].toString());
	
	
	finalTicketSeries = getMaxNormalTicketSeries(moduleName,cbt,substringLength);
	if(finalTicketSeries==null)
	{
	if(object[2]!=null && !object[2].equals(""))
	{
	finalTicketSeries =object[2].toString()+object[1].toString();
	}
	else
	{
	finalTicketSeries =object[1].toString();
	}
	}
	}
	}
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	
	//System.out.println("final2  "+finalTicketSeries);
	return finalTicketSeries+"#"+String.valueOf(substringLength);
	}
	
	
	
	
	public synchronized String getTicketSeriesForReOpened(String ticketNo,String moduleName,String deptId)
	{
	String finalTicketSeries="NA";
	int substringLength=0;
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String query="SELECT ticket_type,n_series,prefix,substring_length FROM ticket_series_conf WHERE moduleName='"+moduleName+"'";
	//System.out.println("Q1 "+query);
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	if(object[0]!=null && object[0].toString().equalsIgnoreCase("D"))
	{
	// department wise ticket series
	query ="SELECT prefix,d_series,substring_length FROM dept_ticket_series_conf WHERE moduleName='"+moduleName+"' AND deptName="+deptId;
	//System.out.println("Q2 "+query);

	List tempDataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(tempDataList!=null && tempDataList.size()>0)
	{
	Object[] obj=(Object[])tempDataList.get(0);
	substringLength = Integer.valueOf(obj[2].toString());
	finalTicketSeries = getMaxDeptWiseTicketSeriesForReOpened(ticketNo,substringLength,moduleName);
	//System.out.println("final   "+finalTicketSeries);

	if(finalTicketSeries==null)
	{
	
	if(obj[0]!=null && !obj[0].equals(""))
	{
	finalTicketSeries = obj[0].toString();
	}
	if(obj[1]!=null && !obj[1].equals(""))
	{
	finalTicketSeries=finalTicketSeries+String.valueOf(obj[1].toString());
	}
	}
	/*else
	{
	if(object[0]!=null && !object[0].equals(""))
	{
	finalTicketSeries =object[2].toString()+object[1].toString();
	}
	else
	{
	finalTicketSeries =object[1].toString();
	}
	}*/
	}
	}
	else if(object[0]!=null && object[0].toString().equalsIgnoreCase("N"))
	{
	// Normal Ticket Series
	if(object[3]!=null && !object[3].equals(""))
	substringLength = Integer.valueOf(object[3].toString());
	
	
	finalTicketSeries = getMaxNormalTicketSeriesForReOpened(ticketNo,substringLength,moduleName);
	if(finalTicketSeries==null)
	{
	if(object[2]!=null && !object[2].equals(""))
	{
	finalTicketSeries =object[2].toString()+object[1].toString();
	}
	else
	{
	finalTicketSeries =object[1].toString();
	}
	}
	}
	}
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	
	//System.out.println("final2  "+finalTicketSeries);
	return finalTicketSeries+"#"+String.valueOf(substringLength);
	}
	
	
	public synchronized String getMaxNormalTicketSeries(String moduleName, CommonOperInterface cbt, int substringLength)
	{
	int maxId = 0;
	String finalString=null;
	try
	{
	if(moduleName!=null)
	{
	if(moduleName.equalsIgnoreCase("COMPL"))
	{
	String query = "SELECT compid_suffix,compid_prefix FROM compliance_details  ORDER BY id DESC LIMIT 1";
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	Object[] obj=(Object[])dataList.get(0);
	if(obj[1]!=null && !obj[1].equals(""))
	{
	finalString = obj[1].toString();
	}
	if(obj[0]!=null && !obj[0].equals(""))
	{
	maxId = Integer.parseInt(obj[0].toString())+1;
	finalString=finalString+String.valueOf(maxId);
	}
	}
	}
	else if(moduleName.equalsIgnoreCase("HDM"))
	{
	String ticketNo="";
	String query = "SELECT ticket_no FROM feedback_status WHERE moduleName='"+moduleName+"' ORDER BY id DESC LIMIT 1";
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{ 	
	ticketNo=dataList.get(0).toString();
	 
	}
	String prefix=null; 
	int suffix =0;
	if(substringLength>0)
	{
	prefix = ticketNo.substring(0, substringLength);
	suffix = Integer.valueOf(ticketNo.substring(substringLength,ticketNo.length()))+1;
	finalString = prefix+String.valueOf(suffix);
	}	 
	}
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	
	
	
	public synchronized String getMaxNormalTicketSeriesForReOpened(String ticketNo, int substringLength,String moduleName)
	{
	int maxId = 0;
	String finalString=null;
	try
	{
	 
	/*if(moduleName.equalsIgnoreCase("COMPL"))
	{
	String query = "SELECT compid_suffix,compid_prefix FROM compliance_details  ORDER BY id DESC LIMIT 1";
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	Object[] obj=(Object[])dataList.get(0);
	if(obj[1]!=null && !obj[1].equals(""))
	{
	finalString = obj[1].toString();
	}
	if(obj[0]!=null && !obj[0].equals(""))
	{
	maxId = Integer.parseInt(obj[0].toString())+1;
	finalString=finalString+String.valueOf(maxId);
	}
	}
	}*/
	if(moduleName.equalsIgnoreCase("HDM"))
	{
	 
	String prefix=null; 
	int suffix =0;
	if(substringLength>0)
	{
	prefix = ticketNo.substring(0, substringLength);
	suffix = Integer.valueOf(ticketNo.substring(substringLength,ticketNo.length()));
	finalString = prefix+String.valueOf(suffix+"R");
	}
	}
 
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	
	
	public synchronized String getMaxDeptWiseTicketSeries(String moduleName, CommonOperInterface cbt, int substringLength, String deptId)
	{
	int maxId = 0;
	String finalString=null;
	try
	{
	if(moduleName!=null)
	{
	if(moduleName.equalsIgnoreCase("COMPL"))
	{
	String query = "SELECT compid_suffix,compid_prefix FROM compliance_details  ORDER BY id DESC LIMIT 1";
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	Object[] obj=(Object[])dataList.get(0);
	if(obj[1]!=null && !obj[1].equals(""))
	{
	finalString = obj[1].toString();
	}
	if(obj[0]!=null && !obj[0].equals(""))
	{
	maxId = Integer.parseInt(obj[0].toString())+1;
	finalString=finalString+String.valueOf(maxId);
	}
	}
	}
	else if(moduleName.equalsIgnoreCase("HDM"))
	{
	String ticketNo="" ;
	String query = "SELECT ticket_no FROM feedback_status_new WHERE  ticket_no not Like '%R%' AND to_dept_subdept IN("+deptId+") ORDER BY id DESC LIMIT 1";
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{ 	
	ticketNo=dataList.get(0).toString();
	}
	String prefix=null; 
	int suffix =0;
	if(substringLength>0 && !ticketNo.equalsIgnoreCase(""))
	{
	prefix = ticketNo.substring(0, substringLength);
	suffix = Integer.valueOf(ticketNo.substring(substringLength,ticketNo.length()))+1;
	finalString = prefix+String.valueOf(suffix);
	 
	}
	}
	}
	 
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	
	
	
	public synchronized String getMaxDeptWiseTicketSeriesForReOpened(String ticketNo, int substringLength, String moduleName)
	{
	String finalString=null;
	try
	{
	 
	/*if(moduleName.equalsIgnoreCase("COMPL"))
	{
	String query = "SELECT compid_suffix,compid_prefix FROM compliance_details  ORDER BY id DESC LIMIT 1";
	List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	Object[] obj=(Object[])dataList.get(0);
	if(obj[1]!=null && !obj[1].equals(""))
	{
	finalString = obj[1].toString();
	}
	if(obj[0]!=null && !obj[0].equals(""))
	{
	maxId = Integer.parseInt(obj[0].toString())+1;
	finalString=finalString+String.valueOf(maxId);
	}
	}
	}*/
	 if(moduleName.equalsIgnoreCase("HDM"))
	{
	 
	String prefix=null; 
	int suffix =0;
	if(substringLength>0)
	{
	prefix = ticketNo.substring(0, substringLength);
	suffix = Integer.valueOf(ticketNo.substring(substringLength,ticketNo.length()));
	finalString = prefix+String.valueOf(suffix+"R");
	 
	}
	}
	}
	 
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	
	
	
	public String getDeptid() {
	return deptid;
	}
	public void setDeptid(String deptid) {
	this.deptid = deptid;
	}

	public String getPrefix() {
	return prefix;
	}
	public void setPrefix(String prefix) {
	this.prefix = prefix;
	}

	public String getTicket_type() {
	return ticket_type;
	}
	public void setTicket_type(String ticket_type) {
	this.ticket_type = ticket_type;
	}

	public String getN_series() {
	return n_series;
	}
	public void setN_series(String n_series) {
	this.n_series = n_series;
	}

	public String getDeptName() {
	return deptName;
	}
	public void setDeptName(String deptName) {
	this.deptName = deptName;
	}

	public String getD_series() {
	return d_series;
	}
	public void setD_series(String d_series) {
	this.d_series = d_series;
	}

	public List<Object> getTicketSeriesData() {
	return ticketSeriesData;
	}
	public void setTicketSeriesData(List<Object> ticketSeriesData) {
	this.ticketSeriesData = ticketSeriesData;
	}

	public String getFlag() {
	return flag;
	}
	public void setFlag(String flag) {
	this.flag = flag;
	}
	public String getFloor_status() {
	return floor_status;
	}
	public void setFloor_status(String floor_status) {
	this.floor_status = floor_status;
	}

	public String getDataFor() {
	return dataFor;
	}
	public void setDataFor(String dataFor) {
	this.dataFor = dataFor;
	}
	
	public Map<Integer, String> getServiceDeptList() {
	return serviceDeptList;
	}
	public void setServiceDeptList(Map<Integer, String> serviceDeptList) {
	this.serviceDeptList = serviceDeptList;
	}

	public Map<String, String> getTicketSeriesType() {
	return ticketSeriesType;
	}

	public void setTicketSeriesType(Map<String, String> ticketSeriesType) {
	this.ticketSeriesType = ticketSeriesType;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
	this.request=request;
	
	}
}