package com.Over2Cloud.ctrl.buddy;

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
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BuddySetting extends ActionSupport implements ServletRequestAware 
{
	static final Log log = LogFactory.getLog(BuddySetting.class);
	private HttpServletRequest request;
	private String mainHeaderName;
	private Map<Integer, String> deptList = null;
	String deptHierarchy =null;
	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;
	boolean checkdept = false;
	private List<ConfigurationUtilBean> buddyFloorColumnMap;
	private List<ConfigurationUtilBean> buddyDropDowmColumnMap;
	private Map<Integer, String> subDeptList;
	private Map<Integer, String> employeeList;
	private Map<Integer,String>  subFloorList;
	private String dept;
	private String destination;
	private String deptOrSubDeptId;
	private Map<String, String> columnMap=null;
	private List<ConfigurationUtilBean> columnMap2=null;
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
	// For Grid
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
	//private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private String download4;
	private String tableName;
	private String tableName1;
	private String excelName;
	private String[] columnNames;
	private String downloadType;
	
	@SuppressWarnings("unchecked")
	public String beforeAddBuddy() 
	{
		String returnString=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				String orgLevel = (String)session.get("orgnztnlevel");
				String orgId = (String)session.get("mappedOrgnztnId");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				getPageFields();
				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();
				
				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				
				List<String>colname=new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");
				
				/*Get Department Data*/
				deptHierarchy = (String) session.get("userDeptLevel");
				if(deptHierarchy!=null){
				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept=true;
				}
				else if (deptHierarchy.equals("1")) 
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept=false;
				}
				}
				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				
				// Getting Id, Dept Name for Non Service Department
				//departmentlist =new AssetUniversalHelper().getDataFromTable("department", colmName, wherClause, order, connectionSpace);
				if (departmentlist!=null && departmentlist.size()>0) {
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();) {
							
						Object[] object = (Object[]) iterator.next();
						if (object!=null && object[0]!=null && object[1].toString()!=null) 
						{
							deptList.put((Integer)object[0], object[1].toString());
						}
					}
				}
				
				if(departmentlist!=null)
					departmentlist.removeAll(departmentlist);
				
				returnString =SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			returnString=LOGIN;
		}
		return returnString;
}
	
	@SuppressWarnings("unchecked")
	private void getPageFields() 
	{
		try {
			Map session = ActionContext.getContext().getSession();
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			List<GridDataPropertyView> buddyColumnList=Configuration.getConfigurationData("mapped_buddy_setting",accountID,connectionSpace,"",0,"table_name","buddy_setting_configuration");
			buddyFloorColumnMap=new ArrayList<ConfigurationUtilBean>();
			buddyDropDowmColumnMap=new ArrayList<ConfigurationUtilBean>();
			ConfigurationUtilBean obj;
			if(buddyColumnList!=null&&buddyColumnList.size()>0)
			{
				for(GridDataPropertyView  gdp:buddyColumnList)
				{
					if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase("deptHierarchy") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept"))
					{
						obj=new ConfigurationUtilBean();
						obj.setKey(gdp.getColomnName());
						obj.setValue(gdp.getHeadingName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if(gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						buddyFloorColumnMap.add(obj);
					}
					if(gdp.getColType().trim().equalsIgnoreCase("D"))
					{
					ConfigurationUtilBean timeObj=new ConfigurationUtilBean();
					timeObj.setKey(gdp.getColomnName());
					timeObj.setValue(gdp.getHeadingName());
					timeObj.setValidationType(gdp.getValidationType());
					timeObj.setColType(gdp.getColType());
					if(gdp.getMandatroy().toString().equals("1"))
					{
						timeObj.setMandatory(true);
					}
					else
					{
						timeObj.setMandatory(false);
					}
					buddyDropDowmColumnMap.add(timeObj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void setsubDept_DeptTags(String level)
	{
		Map session = ActionContext.getContext().getSession();
		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
		subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
		if (deptList!=null && deptList.size()>0)
		 {
			for (GridDataPropertyView gdv : deptList)
			 {
				ConfigurationUtilBean obj=new ConfigurationUtilBean();
			     if (gdv.getColomnName().equalsIgnoreCase("deptName")) 
			     {
				    obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					if (gdv.getMandatroy().equalsIgnoreCase("1")) 
					{
						obj.setMandatory(true);
					} else {
						obj.setMandatory(false);
					}
				   subDept_DeptLevelName.add(obj);
			   }
		    }
		}
		if (level.equals("2")) 
		{
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf",accountID,connectionSpace,"",0,"table_name","subdeprtmentconf");	
			if (subdept_deptList!=null && subdept_deptList.size()>0)
			 {
				for (GridDataPropertyView gdv1 : subdept_deptList)
				 {
					ConfigurationUtilBean obj=new ConfigurationUtilBean();
				   if (gdv1.getColomnName().equalsIgnoreCase("subdeptname")) 
				   {
					    obj.setKey(gdv1.getColomnName());
						obj.setValue(gdv1.getHeadingName());
						obj.setValidationType(gdv1.getValidationType());
						obj.setColType("D");
						if (gdv1.getMandatroy().equalsIgnoreCase("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
					   subDept_DeptLevelName.add(obj);
				   }
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getSubDepartment() 
	{
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
		  try {
			        Map session = ActionContext.getContext().getSession();
			        SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					List list=new ArrayList<String>();
					list.add("id");
					list.add("subdeptname");
					Map<String, Object> temp=new HashMap<String, Object>();
					Map<String, Object> order=new HashMap<String, Object>();
					order.put("subdeptname", "ASC");
					temp.put("deptid",getDept());
					subDeptList=new LinkedHashMap<Integer, String>();
					list=new HelpdeskUniversalHelper().getDataFromTable("subdepartment",list,temp,null,order,connectionSpace);
					if(list!=null && list.size()>0)
					 {
						for (Object c : list) {
							Object[] dataC = (Object[]) c;
							subDeptList.put((Integer)dataC[0], dataC[1].toString());
						}
					 }
					if (destination!=null) {
						destination = destination.replace("subDeptDiv", "");
					}
					returnResult = SUCCESS;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		   }
		else {
			returnResult =LOGIN;
		}
		return 	returnResult;
}
	@SuppressWarnings("unchecked")
	public String getEmpDetail() 
	{
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			 try {
				 Map session = ActionContext.getContext().getSession();
				 String userName=(String) session.get("uName");
				 SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				 if(userName==null || userName.equalsIgnoreCase(""))
						return LOGIN;
				 
					String deptLevel = (String)session.get("userDeptLevel");
					List empList=new ArrayList<String>();
					empList.add("id");
					empList.add("empName");
					Map<String, Object> temp=new HashMap<String, Object>();
					if (deptLevel.equals("2")) {
						temp.put("subdept",getDeptOrSubDeptId());
					}
					else if (deptLevel.equals("1")) {
						temp.put("dept",getDeptOrSubDeptId());
					}
					
					Map<String, Object> order=new HashMap<String, Object>();
					order.put("empName","ASC");
					
					//empList= new AssetUniversalHelper().getDataFromTable("employee_basic", empList, temp,null, order, connectionSpace);
					employeeList=new LinkedHashMap<Integer, String>();
					if(empList!=null && empList.size()>0)
					{
						for (Object c : empList) {
							Object[] dataC = (Object[]) c;
							employeeList.put((Integer)dataC[0], dataC[1].toString());
						}
					}
					if (destination!=null) {
						destination = destination.replace("EmpDiv", "");
					}
					returnResult = SUCCESS;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		   }
		else {
			returnResult = LOGIN;
		}
	return returnResult;
}	
	
	@SuppressWarnings("unchecked")
	public String buddyAddFloor() 
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
                Map session=ActionContext.getContext().getSession(); 
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_buddy_setting",accountID,connectionSpace,"",0,"table_name","buddy_setting_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
					//create table query based on configuration
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> tableColume=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					for(GridDataPropertyView gdp:statusColName)
					{
						if(!gdp.getColomnName().equalsIgnoreCase("floorname1") && !gdp.getColomnName().equalsIgnoreCase("intecom") && !gdp.getColomnName().equalsIgnoreCase("subfloorcode")
								&& !gdp.getColomnName().equalsIgnoreCase("subfloorname") && !gdp.getColomnName().equalsIgnoreCase("employeeid")){
						 TableColumes ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 tableColume.add(ob1);
						}
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
					}
					
					 @SuppressWarnings("unused")
					boolean status1=cbt.createTable22("buddy_floor_info",tableColume,connectionSpace);
					
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 if(requestParameterNames!=null&&requestParameterNames.size()>0)
					 {
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 while (it.hasNext()) 
						 {
								String parmName = it.next().toString();
								String paramValue=request.getParameter(parmName);
								
								if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null && !parmName.equalsIgnoreCase("deptHierarchy")) 
								{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(paramValue);
									 insertData.add(ob);
								}
						   }
					 }
					 if(userTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("userName");
						 ob.setDataName(userName);
						 insertData.add(ob);
					  }
					 if(dateTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("date");
						 ob.setDataName(DateUtil.getCurrentDateUSFormat());
						 insertData.add(ob);
					 }
					 if(timeTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("time");
						 ob.setDataName(DateUtil.getCurrentTime());
						 insertData.add(ob);
					 }
					 status=cbt.insertIntoTable("buddy_floor_info",insertData,connectionSpace); 
					 if(status)
					 {
						 addActionMessage("Buddy Registered Successfully!!!");
						 return SUCCESS;
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!");
						 return ERROR;
					 } 
				}
				else
				{
					addActionMessage("Buddy not Added Successfully !!");
					return ERROR;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
		return LOGIN;
	}
}
	@SuppressWarnings("unchecked")
	public String getFloorData() 
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if (sessionFlag) 
		 {
		try {
			 Map session = ActionContext.getContext().getSession();
			 String userName=(String) session.get("uName");
			 SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			 if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
			 
				List floorList=new ArrayList<String>();
				floorList.add("id");
				floorList.add("floorname");
				
				Map<String, Object> order=new HashMap<String, Object>();
				order.put("floorname","ASC");
				
				//floorList= new AssetUniversalHelper().getDataFromTable("buddy_floor_info", floorList,null,null, order, connectionSpace);
				subFloorList=new LinkedHashMap<Integer, String>();
				if(floorList!=null && floorList.size()>0)
				{
					for (Object c : floorList) 
					{
						Object[] dataC = (Object[]) c;
						if (dataC[1]!=null && dataC!=null && dataC[0]!=null) 
						{
							subFloorList.put((Integer)dataC[0], dataC[1].toString());
						}
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} 
		else 
		{
          return ERROR;
		}
}
	
   @SuppressWarnings("unchecked")
public String subFloorDataAdd() 
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_buddy_setting",accountID,connectionSpace,"",0,"table_name","buddy_setting_configuration");
				
				if(statusColName!=null&&statusColName.size()>0)
				{
					//create table query based on configuration
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> tableColume=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					for(GridDataPropertyView gdp:statusColName)
					{
					 if (!gdp.getColomnName().equalsIgnoreCase("floorcode") && !gdp.getColomnName().equalsIgnoreCase("floorname"))
						{
						 TableColumes ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 tableColume.add(ob1);
						}
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
					 }
					 TableColumes ob1=new TableColumes();
					 ob1.setColumnname("deptHierarchy");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 tableColume.add(ob1);
					 
					 ob1=new TableColumes();
					 ob1.setColumnname("dept_subdept");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 tableColume.add(ob1);
					 
					 @SuppressWarnings("unused")
					 boolean status1=cbt.createTable22("buddy_sub_floor_info",tableColume,connectionSpace);
					
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 String deptLevel = (String)session.get("userDeptLevel");
					 String subdept_dept = null;
					 if(requestParameterNames!=null&&requestParameterNames.size()>0)
					 {
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 while (it.hasNext()) 
						 {
								String parmName = it.next().toString();
								String paramValue=request.getParameter(parmName);
								
								 if(parmName.equalsIgnoreCase("deptname")&& deptLevel.equals("1"))
								 {
									 subdept_dept=paramValue;
								 }
								 else if(parmName.equalsIgnoreCase("subdeptname")&& deptLevel.equals("2"))
								 {
									 subdept_dept=paramValue;
								 }
								if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null && !parmName.equalsIgnoreCase("deptHierarchy") && 
										!parmName.equalsIgnoreCase("deptname") && !parmName.equalsIgnoreCase("subdeptname"))
								{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(paramValue);
									 insertData.add(ob);
								}
						   }
					  }
					 if(subdept_dept!=null)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("dept_subdept");
						 ob.setDataName(subdept_dept);
						 insertData.add(ob);
					 }
					 if(deptLevel!=null)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("deptHierarchy");
						 ob.setDataName(deptLevel);
						 insertData.add(ob);
					 }
					 if(userTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("userName");
						 ob.setDataName(userName);
						 insertData.add(ob);
					 }
					 if(dateTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("date");
						 ob.setDataName(DateUtil.getCurrentDateUSFormat());
						 insertData.add(ob);
					 }
					 if(timeTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("time");
						 ob.setDataName(DateUtil.getCurrentTime());
						 insertData.add(ob);
					 }
					 status=cbt.insertIntoTable("buddy_sub_floor_info",insertData,connectionSpace); 
					 if(status)
					 {
						 addActionMessage("Buddy Registered Successfully!!!");
						 return SUCCESS;
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!");
						 return ERROR;
					 } 
				}
				else {
					addActionMessage("Buddy Not Added Successfully");
					return ERROR;
				}
			}
			catch(Exception e)
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
	@SuppressWarnings("unchecked")
	public String beforeViewBuddy() 
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				
		        setMainHeaderName("Buddy >> View");
				getColumn4View("mapped_buddy_setting","buddy_setting_configuration");
				returnResult=SUCCESS;		
			}
			catch(Exception e)
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
	@SuppressWarnings("unchecked")
	private void getColumn4View(String mappedTable, String columnTable) 
	{
		try
		{
		Map session=ActionContext.getContext().getSession();
		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		masterViewProp=new ArrayList<GridDataPropertyView>();
		List<GridDataPropertyView> statusColName=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",columnTable);
		if(statusColName!=null&&statusColName.size()>0)
		{
			for(GridDataPropertyView gdp:statusColName)
			{
				if (!gdp.getColomnName().equalsIgnoreCase("floorname")&& !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time")) 
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
			gpv=new GridDataPropertyView();
			gpv.setColomnName("dept");
			gpv.setHeadingName("Buddy Dept");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("sub_dept");
			gpv.setHeadingName("Buddy SubDept");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("mob_no");
			gpv.setHeadingName("Buddy Mobile No");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("email_id");
			gpv.setHeadingName("Buddy Email Id");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			masterViewProp.add(gpv);
		}
		else
		{
			addActionMessage("Buddy Table Is Not properly Configured !");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
}

	@SuppressWarnings("unchecked")
	public String viewBuddyData() 
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from buddy_sub_floor_info");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("SELECT ");
					List fieldNames=new ArrayList<String>();
					fieldNames=Configuration.getColomnList("mapped_buddy_setting", accountID, connectionSpace, "buddy_setting_configuration");
					if(fieldNames.contains("userName") && fieldNames.contains("date") && fieldNames.contains("time")){
						fieldNames.remove("userName");
						fieldNames.remove("date");
						fieldNames.remove("time");
						fieldNames.remove("id");
						fieldNames.remove("floorname");
						
					}
					fieldNames.add("dept");
					fieldNames.add("sub_dept");
					fieldNames.add("mob_no");
					fieldNames.add("email_id");
					
					query.append("flr.floorcode,flr.floorname,subf.intecom,subf.subfloorcode,subf.subfloorname,emp.empName,dept.deptName,sdept.subdeptname,emp.mobOne,emp.emailIdOne FROM buddy_sub_floor_info as subf "+ 
                    "INNER JOIN buddy_floor_info as flr on flr.id=subf.floorname1 "+
                    "INNER JOIN employee_basic as emp on emp.id=subf.employeeid "+
					"INNER JOIN subdepartment as sdept on sdept.id=subf.dept_subdept "+
                    "INNER JOIN department as dept on dept.id=sdept.deptid");
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
						}
						
					}
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
				    {
						if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
						{
							query.append(" "+getSidx());
						}
			    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
			    	    {
			    	    	query.append(" "+getSidx()+" DESC");
			    	    }
				    }
					query.append(" limit "+from+","+to);
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null&&data.size()>0)
					{
						List<Object> tempList=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null)
							{
								Map<String, Object> tempMap=new HashMap<String, Object>();
								for(int k=0;k<obdata.length;k++)
								{
										if(obdata[k]!=null)
									   {
											tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
									   }
								}
								tempList.add(tempMap);
							}
						}
						setMasterViewList(tempList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
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
	
	public String getColumn4Download() 
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(download4!=null && download4.equals("buddyDetail"))
				{
					returnResult=getColumnName("mapped_buddy_setting","buddy_setting_configuration","");
					tableName="buddy_floor_info";
					tableName="buddy_sub_floor_info";
					excelName="buddyUpload";
				}
			}
			catch(Exception e)
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
	
	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName,String basicTableName,String a)
	{
			String returnResult=ERROR;
			try
			{
				Map session=ActionContext.getContext().getSession();
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				List<GridDataPropertyView> columnList=Configuration.getConfigurationData(mappedTableName,accountID,connectionSpace,"",0,"table_name",basicTableName);
				columnMap=new LinkedHashMap<String, String>();
				columnMap2=new ArrayList<ConfigurationUtilBean>();
				if(columnList!=null&&columnList.size()>0)
				{
					ConfigurationUtilBean obj;
						for(GridDataPropertyView  gdp1:columnList)
						{
							obj=new ConfigurationUtilBean();
							 if(!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("time") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("floorname1") &&  !gdp1.getColomnName().equals("employeeid"))
							{
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
					    }
						obj=new ConfigurationUtilBean();
						obj.setKey("mobno");
						obj.setValue("Buddy Mobile No");
						columnMap2.add(obj);
						columnMap.put("mobno","Buddy Mobile No");
					
					if(columnMap!=null && columnMap.size()>0)
					{
						session.put("columnMap", columnMap);
					}
					returnResult=SUCCESS;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	return returnResult;
}
	@SuppressWarnings("unchecked")
	public String downloadExcel()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				Map session= ActionContext.getContext().getSession();
				List keyList=new ArrayList();
				List titleList=new ArrayList();
				if(columnNames!=null && columnNames.length>0)
				{
					keyList=Arrays.asList(columnNames);
					Map<String, String> tempMap=new LinkedHashMap<String, String>();
					tempMap=(Map<String, String>) session.get("columnMap");
					List dataList=null;
					for(int index=0;index<keyList.size();index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
				    if(downloadType!=null && downloadType.equals("uploadExcel"))
					{
						returnResult=new GenericWriteExcel().createExcel(session.get("orgname").toString(),excelName, dataList, titleList, null, excelName);
					}
					returnResult=SUCCESS;
				}
			}
			catch(Exception e)
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
	@SuppressWarnings("unchecked")
	public int saveData(String mappedTable,String configTable,String dataTable,Map<String,String> dataWithColumnName)
	{   
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		Map session= ActionContext.getContext().getSession();
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		List<GridDataPropertyView> statusColName=null;
		String accountID=(String)session.get("accountid");
		String userName=(String) session.get("uName");
		int maxId=0;
		statusColName=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",configTable);
		if(statusColName!=null&&statusColName.size()>0)
		{
			boolean userTrue=false;
			boolean dateTrue=false;
			boolean timeTrue=false;
			InsertDataTable ob=null;
			//create table query based on configuration
			List <TableColumes> tableColume=new ArrayList<TableColumes>();
			
			for(GridDataPropertyView gdp:statusColName)
			{
				if (!gdp.getColomnName().equalsIgnoreCase("floorname1") && !gdp.getColomnName().equalsIgnoreCase("intecom") && !gdp.getColomnName().equalsIgnoreCase("subfloorcode") && !gdp.getColomnName().equalsIgnoreCase("subfloorname") && !gdp.getColomnName().equalsIgnoreCase("employeeid")) {
			
				 TableColumes ob1=new TableColumes();
				 ob1=new TableColumes();
				 ob1.setColumnname(gdp.getColomnName());
				 ob1.setDatatype("varchar(255)");
				 ob1.setConstraint("default NULL");
				 tableColume.add(ob1);
				}
				 if(gdp.getColomnName().equalsIgnoreCase("userName"))
					 userTrue=true;
				 else if(gdp.getColomnName().equalsIgnoreCase("date"))
					 dateTrue=true;
				 else if(gdp.getColomnName().equalsIgnoreCase("time"))
					 timeTrue=true;
			}
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			cbt.createTable22(dataTable,tableColume,connectionSpace);
			Iterator<Entry<String, String>> hiterator=dataWithColumnName.entrySet().iterator();
			while (hiterator.hasNext()) 
			{
				    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				    ob=new InsertDataTable();
					ob.setColName(paramPair.getKey().toString());
					if(paramPair.getValue()!=null)
					ob.setDataName(paramPair.getValue().toString());
					insertData.add(ob);
			}
			if(userTrue)
			{
				 ob=new InsertDataTable();
				 ob.setColName("userName");
				 ob.setDataName(userName);
				 insertData.add(ob);
			}
			if(dateTrue)
			{
				 ob=new InsertDataTable();
				 ob.setColName("date");
				 ob.setDataName(DateUtil.getCurrentDateUSFormat());
				 insertData.add(ob);
			}
			if(timeTrue)
			{
				 ob=new InsertDataTable();
				 ob.setColName("time");
				 ob.setDataName(DateUtil.getCurrentTime());
				 insertData.add(ob);
			}
			maxId=cbt.insertDataReturnId(dataTable,insertData,connectionSpace);
		}
		return maxId;
}
	
	@SuppressWarnings("unchecked")
	public String modifyBuddy()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				if(getOper().equalsIgnoreCase("edit"))
				{
					StringBuffer columnBuffer=new StringBuffer("");
					StringBuffer dataBuffer=new StringBuffer("");
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
								&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
						{
							
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName+"#");
								dataBuffer.append(paramValue+"#");
						}
						
					}
					condtnBlock.put("id",getId());
					@SuppressWarnings("unused")
					boolean status=cbt.updateTableColomn("buddy_sub_floor_info", wherClause, condtnBlock,connectionSpace);
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
						cbt.deleteAllRecordForId("buddy_sub_floor_info", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch(Exception e)
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
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	public List<ConfigurationUtilBean> getSubDept_DeptLevelName() {
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(
			List<ConfigurationUtilBean> subDept_DeptLevelName) {
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public List<ConfigurationUtilBean> getBuddyFloorColumnMap() {
		return buddyFloorColumnMap;
	}

	public void setBuddyFloorColumnMap(
			List<ConfigurationUtilBean> buddyFloorColumnMap) {
		this.buddyFloorColumnMap = buddyFloorColumnMap;
	}

	public List<ConfigurationUtilBean> getBuddyDropDowmColumnMap() {
		return buddyDropDowmColumnMap;
	}

	public void setBuddyDropDowmColumnMap(
			List<ConfigurationUtilBean> buddyDropDowmColumnMap) {
		this.buddyDropDowmColumnMap = buddyDropDowmColumnMap;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public Map<Integer, String> getSubDeptList() {
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList) {
		this.subDeptList = subDeptList;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptHierarchy() {
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy) {
		this.deptHierarchy = deptHierarchy;
	}

	public String getDeptOrSubDeptId() {
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId) {
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public Map<Integer, String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Map<Integer, String> employeeList) {
		this.employeeList = employeeList;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Map<Integer, String> getSubFloorList() {
		return subFloorList;
	}

	public void setSubFloorList(Map<Integer, String> subFloorList) {
		this.subFloorList = subFloorList;
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public String getDownload4() {
		return download4;
	}

	public void setDownload4(String download4) {
		this.download4 = download4;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName1() {
		return tableName1;
	}

	public void setTableName1(String tableName1) {
		this.tableName1 = tableName1;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public Map<String, String> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}

	public List<ConfigurationUtilBean> getColumnMap2() {
		return columnMap2;
	}

	public void setColumnMap2(List<ConfigurationUtilBean> columnMap2) {
		this.columnMap2 = columnMap2;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
