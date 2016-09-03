package com.Over2Cloud.ctrl.leaveManagement;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LeaveConfiguration extends GridPropertyBean implements ServletRequestAware {
	static final Log log = LogFactory.getLog(LeaveConfiguration.class);
	private HttpServletRequest request;
	@SuppressWarnings("rawtypes")
	private Map session = ActionContext.getContext().getSession();
	private List<ConfigurationUtilBean> confiColumnDropdown=null;
	private List<ConfigurationUtilBean> confiColumnText=null;
	private List<ConfigurationUtilBean> employeeColumnText=null;
	private Map<Integer,String> employeeType = null;
	private Map<Integer,String> leaveType = null;
	private String id;
	private String mainHeaderName;
	private double extraWorking;
	//GRID OPERATION
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
	//Grid colomn view
	private String oper;
	private List<Object> viewLeaveConfi;
	private List<Object> viewEmployeeType;
	private List<GridDataPropertyView> leaveConfiGridColomns = new ArrayList<GridDataPropertyView>();
	
	@SuppressWarnings({  "rawtypes" })
	public String beforeLeaveConfiAdd()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			try
			{
				String accountID=(String)session.get("accountid");
				List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","leave_confi_configuration");
				confiColumnDropdown=new ArrayList<ConfigurationUtilBean>();
				confiColumnText=new ArrayList<ConfigurationUtilBean>();
				if(requestColumnList!=null&&requestColumnList.size()>0)
				{
					ConfigurationUtilBean cub=null;
					for(GridDataPropertyView  gdp:requestColumnList)
					{
						cub=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("D")&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if(gdp.getMandatroy().equalsIgnoreCase("1")){
								cub.setMandatory(true);
							}else{
								cub.setMandatory(false);
							}
							confiColumnDropdown.add(cub);
						}
						else if(gdp.getColType().equalsIgnoreCase("T"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							
							if(gdp.getMandatroy().equalsIgnoreCase("1")){
								cub.setMandatory(true);
							}else{
								cub.setMandatory(false);
							}
							confiColumnText.add(cub);
						}
					}
				}
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				leaveType= new LinkedHashMap<Integer, String>();
				List leaveTypeData = cbt.executeAllSelectQuery("SELECT id,ltype FROM leave_type",connectionSpace);
				if (leaveTypeData!=null && leaveTypeData.size()>0) 
				{
					Object[] object=null;
					for (Iterator iterator = leaveTypeData.iterator(); iterator.hasNext();) 
					{
						object = (Object[]) iterator.next();
						if (object[0]!=null || object[1]!=null) 
						{
							leaveType.put(Integer.parseInt(object[0].toString()), object[1].toString());
						}
					}
				}
				employeeType =new LinkedHashMap<Integer, String>();
				List data = cbt.executeAllSelectQuery("SELECT id,etype FROM employee_type", connectionSpace);
				if (data!=null && data.size()>0) 
				{
					Object[] object=null;;
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						object = (Object[]) iterator.next();
						if (object[0]!=null || object[1]!=null) 
						{
							employeeType.put(Integer.parseInt(object[0].toString()), object[1].toString());
 					    }
					}
				}
				return SUCCESS;	
			}
			catch(Exception e)
			{
				log.error("Problem in method beforeLeaveConfiAdd of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addleaveConf()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","leave_confi_configuration");
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
					TableColumes ob1=null;
					for(GridDataPropertyView gdp:statusColName)
					{
				
				         ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 tableColume.add(ob1);
						 
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
					}
					 cbt.createTable22("leave_configuration",tableColume,connectionSpace);
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 if(requestParameterNames!=null && requestParameterNames.size()>0)
					 {
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 while (it.hasNext()) 
						 {
							String parmName = it.next().toString();
							String paramValue=request.getParameter(parmName);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null )
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
							 ob.setDataName(DateUtil.makeTitle(userName));
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
							 ob.setDataName(DateUtil.getCurrentTimeHourMin());
							 insertData.add(ob);
						 }
					 status=cbt.insertIntoTable("leave_configuration",insertData,connectionSpace); 
					 if(status)
					 {
						 addActionMessage("Data Save Successfully!!!");
						 return SUCCESS;
					 } 
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!!!!");
					 }
				return SUCCESS;
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
   public String beforeLeaveConfiView()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMainHeaderName("Leave Configuration View");
				setGridColomnNames("mapped_leave_type_configuration","leave_confi_configuration");
				return SUCCESS;		
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

	public void setGridColomnNames(String mappedTable,String configurationTable)
	{
		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		leaveConfiGridColomns.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",configurationTable);
			if(statusColName!=null&&statusColName.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName)
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					leaveConfiGridColomns.add(gpv);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String viewLeaveConfi()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from leave_configuration");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("1");
					Object obdata=null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of column
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_leave_type_configuration", accountID, connectionSpace, "leave_confi_configuration");
					int i=0;
					if(fieldNames!=null&&fieldNames.size()>0)
					{
						Object obdata1=null;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dynamic query based on selected fields
							   obdata1=(Object)it.next();
							    if(obdata1!=null)
							    {
								    if(i<fieldNames.size()-1)
								    	if (obdata1.toString().equalsIgnoreCase("empType")) 
								    	{
								    		query.append("et.etype" +",");
										}
								        else if (obdata1.toString().equalsIgnoreCase("leavetype")) 
								        {
								        	query.append("lt.ltype" +",");
								        }
								        else
								        {
								        	query.append("a."+obdata1.toString()+",");
								        }
								    else
								    	if (obdata1.toString().equalsIgnoreCase("empType")) 
								    	{
								    		query.append("et.etype ");
										}
								        else if (obdata1.toString().equalsIgnoreCase("leavetype")) 
								        {
								        	query.append("lt.ltype ");
								        }
								        else
								        {
								        	query.append("a."+obdata1.toString());
								        }
							    }
							    i++;
						     }
					    }
					query.append(" FROM leave_configuration AS a");
					query.append(" INNER JOIN employee_type AS et ON a.empType = et.id");
					query.append(" INNER JOIN leave_type AS lt ON a.leavetype  =lt.id");
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
					//query.append(" limit "+from+","+to);
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data != null && data.size()>0)
					{
						viewLeaveConfi = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						Object[] obdata1=null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							obdata1=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if(obdata1[k].equals("-1"))
								{
									one.put(fieldNames.get(k).toString(),"NA");	
								}
								else 
								{
									if(k==0)
										one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
									else
										one.put(fieldNames.get(k).toString(), obdata1[k].toString());
								}
							}
							Listhb.add(one);
						}
						setViewLeaveConfi(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				return SUCCESS;
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
	
@SuppressWarnings({ "unchecked", "rawtypes" })
public String modifyLeaveConfi()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
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
							&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
						wherClause.put(parmName, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("leave_configuration", wherClause, condtnBlock,connectionSpace);
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
					cbt.deleteAllRecordForId("leave_configuration", "id", condtIds.toString(), connectionSpace);
			}
			return SUCCESS;
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
public String beforeEmployeeType()
{
    String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		try
		{
			String accountID=(String)session.get("accountid");
			List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","employee_type_configuration");
			employeeColumnText = new ArrayList<ConfigurationUtilBean>();
			if(requestColumnList!=null&&requestColumnList.size()>0)
			{
				ConfigurationUtilBean cub=null;
				for(GridDataPropertyView  gdp:requestColumnList)
				{
					cub=new ConfigurationUtilBean();
					if(gdp.getColType().trim().equalsIgnoreCase("T")&& !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						cub.setKey(gdp.getColomnName());
						cub.setValue(gdp.getHeadingName());
						cub.setColType(gdp.getColType());
						cub.setValidationType(gdp.getValidationType());
						if(gdp.getMandatroy().equalsIgnoreCase("1")){
							cub.setMandatory(true);
						}else{
							cub.setMandatory(false);
						}
						employeeColumnText.add(cub);
					}
				}
			}
			returnResult=SUCCESS;	
		}
		catch(Exception e)
		{
			log.error("Problem in method beforeEmployeeType of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
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

@SuppressWarnings({ "unchecked", "rawtypes" })
public String addEmployeeType()
{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			String userName=(String)session.get("uName");
			String accountID=(String)session.get("accountid");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","employee_type_configuration");
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
				TableColumes ob1=null;
				for(GridDataPropertyView gdp:statusColName)
				{
			         ob1=new TableColumes();
					 ob1.setColumnname(gdp.getColomnName());
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 tableColume.add(ob1);
					 
					 if(gdp.getColomnName().equalsIgnoreCase("userName"))
						 userTrue=true;
					 else if(gdp.getColomnName().equalsIgnoreCase("date"))
						 dateTrue=true;
					 else if(gdp.getColomnName().equalsIgnoreCase("time"))
						 timeTrue=true;
				}
				 cbt.createTable22("employee_type",tableColume,connectionSpace);
				 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				 if(requestParameterNames!=null && requestParameterNames.size()>0)
				 {
					 Collections.sort(requestParameterNames);
					 Iterator it = requestParameterNames.iterator();
					 while (it.hasNext()) 
					 {
						String parmName = it.next().toString();
						String paramValue=request.getParameter(parmName);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null )
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
					 ob.setDataName(DateUtil.makeTitle(userName));
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
					 ob.setDataName(DateUtil.getCurrentTimeHourMin());
					 insertData.add(ob);
				 }
				 status=cbt.insertIntoTable("employee_type",insertData,connectionSpace); 
				 if(status)
				 {
					 addActionMessage("Data Save Successfully!!!");
					 returnResult=SUCCESS;
				 } 
			  }
			  else
			  {
				 addActionMessage("Oops There is some error in data!!!!");
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

public String viewEmployeeType()
{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMainHeaderName("Employee Type >>View");
				setGridColomnNames("mapped_leave_type_configuration","employee_type_configuration");
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

@SuppressWarnings("rawtypes")
public String employeeTypeView()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from employee_type");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null&&dataCount.size()>0)
			{
				BigInteger count=new BigInteger("1");
				Object obdata=null;
				for(Iterator it=dataCount.iterator(); it.hasNext();)
				{
					 obdata=(Object)it.next();
					 count=(BigInteger)obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				
				//getting the list of column
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=Configuration.getColomnList("mapped_leave_type_configuration", accountID, connectionSpace, "employee_type_configuration");
				int i=0;
				if(fieldNames!=null&&fieldNames.size()>0)
				{
					Object obdata1=null;
					for(Iterator it=fieldNames.iterator(); it.hasNext();)
					{
						//generating the dynamic query based on selected fields
						    obdata1=(Object)it.next();
						    if(obdata1!=null)
						    {
							    if(i<fieldNames.size()-1)
							    	query.append(obdata1.toString()+",");
							    else
							    	query.append(obdata1.toString());
						    }
						    i++;
					     }
				    }
				query.append(" from employee_type");
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
				//query.append(" limit "+from+","+to);
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data != null && data.size()>0)
				{
					viewEmployeeType = new ArrayList<Object>();
					List<Object> Listhb = new ArrayList<Object>();
					Object[] obdata1=null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						obdata1=(Object[])it.next();
						Map<String, Object> one=new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) 
						{
							if(obdata1[k].equals("-1"))
							{
								one.put(fieldNames.get(k).toString(),"NA");	
							}
							else 
							{
								if(k==0)
									one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
								else
									if (fieldNames.get(k).toString().equalsIgnoreCase("date")) 
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
									} 
									else 
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}
							}
						}
						Listhb.add(one);
					}
					setViewEmployeeType(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
			}
			return SUCCESS;
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

@SuppressWarnings({ "unchecked", "rawtypes" })
public String modifyEmployeeType()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
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
							&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
						wherClause.put(parmName, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("employee_type", wherClause, condtnBlock,connectionSpace);
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
					cbt.deleteAllRecordForId("employee_type", "id", condtIds.toString(), connectionSpace);
			}
			return SUCCESS;
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






@SuppressWarnings(
		{ "unchecked", "rawtypes" })
		public String addTimeSlotsss()
		{
			System.out.println("inside  addTimeSlot");
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<GridDataPropertyView> statusColName = Configuration
							.getConfigurationData("mapped_time_slot_configuration",
									accountID, connectionSpace, "", 0,
									"table_name", "time_slot_configuration");
					if (statusColName != null && statusColName.size() > 0)
					{
						// create table query based on configuration
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						List<TableColumes> tableColume = new ArrayList<TableColumes>();
						boolean userTrue = false;
						boolean dateTrue = false;
						boolean timeTrue = false;
						for (GridDataPropertyView gdp : statusColName)
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);

							if (gdp.getColomnName().equalsIgnoreCase("userName"))
								userTrue = true;
							else if (gdp.getColomnName().equalsIgnoreCase("date"))
								dateTrue = true;
							else if (gdp.getColomnName().equalsIgnoreCase("time"))
								timeTrue = true;
						}
						cbt.createTable22("time_slot", tableColume,connectionSpace);
						String empName=null;
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						if (requestParameterNames != null && requestParameterNames.size() > 0)
						{
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext())
							{
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								System.out.println("parmName     vbvb       "   +parmName);
								System.out.println("paramValue    vbvbvbvbvb      "   +paramValue);
								if (paramValue != null&& !paramValue.equalsIgnoreCase("")&& parmName != null)
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);   
									if (parmName!=null && parmName.equalsIgnoreCase("empname")) 
									{
										empName=paramValue;
									}
								}
							}
						}
						if (userTrue)
						{
							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(DateUtil.makeTitle(userName));
							insertData.add(ob);
						}
						if (dateTrue)
						{
							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateIndianFormat());
							insertData.add(ob);
						}
						if (timeTrue)
						{
							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertData.add(ob);
						}
						List checkData=cbt.executeAllSelectQuery("SELECT * FROM time_slot WHERE empname='"+empName+"'", connectionSpace);
						
						System.out.println(empName+"   checkData   ::: " +checkData.size());
						if (checkData!=null && checkData.size()>0) 
						{
							addActionMessage("Data Already Exists !!!!!!");
						}
						else
						{
							status = cbt.insertIntoTable("time_slot", insertData,connectionSpace);
						}
						if (status)
						{
							addActionMessage("Data Save Successfully!!!");
						}
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!!!!");
					}
				}
				catch (Exception e)
				{
					returnResult = ERROR;
					e.printStackTrace();
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			System.out.println("returnResult " +returnResult);
			return returnResult;
			
		}






@SuppressWarnings(
		{ "unchecked", "rawtypes" })
		public String addAttendanceMark()
		{
			System.out.println("nfsnff,msd");
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					String day = null;
					String intime = null;
					String outtime = null;
					String totalTime = null;
					String finalIncommin = null;
					String finalOutgoing = null;
					String workingFlag = null;
					String in_time = request.getParameter("in_time");
					String out_time = request.getParameter("out_time");
					String clientVisit = request.getParameter("clientVisit");
					String empid = request.getParameter("empname");
					String date = request.getParameter("date1");
					String status1 = request.getParameter("status");
					AttendancePojo att=new AttendancePojo();
					
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					day = DateUtil.findDayFromDate(date);
					if (day != null)
					{
						if (day.equalsIgnoreCase("Sunday")&& in_time.equalsIgnoreCase("00:00")&& out_time.equalsIgnoreCase("00:00"))
						{
							workingFlag = "Holiday";
						}
						else
						{
							workingFlag = "Working";
						}
					}
					if (in_time.equalsIgnoreCase("00:00")
							&& out_time.equalsIgnoreCase("00:00"))
					{
						totalTime = "NA";
					}
					else
					{
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);
					}
					
					String query = "select ftime,ttime from time_slot where empname='"+ empid + "'";
					List timming = cbt.executeAllSelectQuery(query, connectionSpace);
					if (timming != null && timming.size() > 0)
					{
						Object[] object=null;
						for (Iterator iterator = timming.iterator(); iterator.hasNext();)
						{
					
							object = (Object[]) iterator.next();
							intime = object[0].toString();
							outtime = object[1].toString();
						}
					}
					if (in_time.equalsIgnoreCase("00:00")&& out_time.equalsIgnoreCase("00:00")&& clientVisit.equalsIgnoreCase("Full Day"))
					{
						finalIncommin = "CV";
						finalOutgoing = "CV";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,out_time);
					}
					else if (in_time.equalsIgnoreCase("00:00")
							&& clientVisit.equalsIgnoreCase("Half day"))
					{
						String outgoingStatus = getIncommingStatus(out_time,
								outtime);
						finalOutgoing = getFinalIncommingstatus(outgoingStatus);
						finalIncommin = "CV";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);

					}
					else if (out_time.equalsIgnoreCase("00:00")
							&& clientVisit.equalsIgnoreCase("Half day"))
					{
						String incomingStatus = getIncommingStatus(in_time, intime);
						finalIncommin = getFinalIncommingstatus(incomingStatus);
						finalOutgoing = "CV";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);

					}
					else if (in_time.equalsIgnoreCase("00:00")
							&& clientVisit.equalsIgnoreCase("Full day"))
					{
						String outgoingStatus = getIncommingStatus(out_time,
								outtime);
						finalOutgoing = getFinalIncommingstatus(outgoingStatus);
						finalIncommin = "CV";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);

					}
					else if (out_time.equalsIgnoreCase("00:00")
							&& clientVisit.equalsIgnoreCase("Full day"))
					{
						String incomingStatus = getIncommingStatus(in_time, intime);
						finalIncommin = getFinalIncommingstatus(incomingStatus);
						finalOutgoing = "CV";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);

					}
					else if (in_time.equalsIgnoreCase(intime)
							&& out_time.equalsIgnoreCase(outtime))
					{
						finalIncommin = "On Time";
						finalOutgoing = "On Time";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);
					}
					else if (in_time.equalsIgnoreCase("00:00")
							&& out_time.equalsIgnoreCase("00:00")
							&& day.equalsIgnoreCase("Sunday"))
					{
						workingFlag = "Holiday";
						finalIncommin = "Holiday";
						finalOutgoing = "Holiday";
					}
					else if (in_time.equalsIgnoreCase("00:00")
							&& out_time.equalsIgnoreCase("00:00")
							&& status1.equalsIgnoreCase("Holiday"))
					{
						workingFlag = "Holiday";
						finalIncommin = "Holiday";
						finalOutgoing = "Holiday";
					}
					else
					{
						if (in_time.equalsIgnoreCase("00:00"))
						{
							finalIncommin = "ABSENT";
							totalTime = DateUtil.findDifferenceTwoTime(in_time,
									out_time);
						}
						else
						{
							if (in_time != null && intime != null)
							{
								String incomingStatus = getIncommingStatus(in_time,
										intime);
								if (incomingStatus.equalsIgnoreCase("0:0"))
								{
									finalIncommin = "On Time";
								}
								else
								{
									finalIncommin = getFinalIncommingstatus(incomingStatus);
									totalTime = DateUtil.findDifferenceTwoTime(
											in_time, out_time);
								}
							}
						}
						if (out_time.equalsIgnoreCase("00:00"))
						{
							finalOutgoing = "ABSENT";
							totalTime = DateUtil.findDifferenceTwoTime(in_time,
									out_time);
						}
						else
						{
							if (out_time != null && outtime != null)
							{
								String outgoingStatus = getIncommingStatus(
										out_time, outtime);
								if (outgoingStatus.equalsIgnoreCase("0:0"))
								{
									finalOutgoing = "On Time";
								}
								else
								{
									finalOutgoing = getFinalIncommingstatus(outgoingStatus);
									totalTime = DateUtil.findDifferenceTwoTime(
											in_time, out_time);
								}
							}
						}
					}
					List<GridDataPropertyView> statusColName = Configuration
							.getConfigurationData("mapped_time_slot_configuration",
									accountID, connectionSpace, "", 0,
									"table_name", "attendance_record_configuration");
					if (statusColName != null && statusColName.size() > 0)
					{
						// create table query based on configuration
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						List<TableColumes> tableColume = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : statusColName)
						{
							if (!gdp.getColomnName().equalsIgnoreCase("fdate")
									&& !gdp.getColomnName().equalsIgnoreCase(
											"tdate")
									&& !gdp.getColomnName().equalsIgnoreCase(
											"userName")
									&& !gdp.getColomnName()
											.equalsIgnoreCase("date")
									&& !gdp.getColomnName()
											.equalsIgnoreCase("time"))
							{
								TableColumes ob1 = new TableColumes();
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default NULL");
								tableColume.add(ob1);
							}
							if (day != null)
							{
								if (gdp.getColomnName().equalsIgnoreCase("day"))
								{
									ob = new InsertDataTable();
									ob.setColName("day");
									ob.setDataName(day);
									insertData.add(ob);
								}
							}
							if (gdp.getColomnName().equalsIgnoreCase("working"))
							{
								ob = new InsertDataTable();
								ob.setColName("working");
								ob.setDataName(workingFlag);
								insertData.add(ob);
							}
							if (gdp.getColomnName().equalsIgnoreCase("timeslot"))
							{
								ob = new InsertDataTable();
								ob.setColName("timeslot");
								ob.setDataName(intime + " to " + outtime);
								insertData.add(ob);
							}
							if (gdp.getColomnName().equalsIgnoreCase("beforetime"))
							{
								ob = new InsertDataTable();
								ob.setColName("beforetime");
								ob.setDataName(finalIncommin);
								insertData.add(ob);
							}
							if (gdp.getColomnName().equalsIgnoreCase("aftertime"))
							{
								ob = new InsertDataTable();
								ob.setColName("aftertime");
								ob.setDataName(finalOutgoing);
								insertData.add(ob);
							}
							if (gdp.getColomnName().equalsIgnoreCase("totalhour"))
							{
								ob = new InsertDataTable();
								ob.setColName("totalhour");
								ob.setDataName(totalTime);
								insertData.add(ob);
							}
						}
						cbt.createTable22("attendence_record", tableColume,
								connectionSpace);
						List<String> requestParameterNames = Collections
								.list((Enumeration<String>) request
										.getParameterNames());
						if (requestParameterNames != null
								&& requestParameterNames.size() > 0)
						{
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext())
							{
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								System.out.println("parmName" +parmName);
								System.out.println("paramValue" +paramValue);
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")
										&& parmName != null
										&& !parmName.equalsIgnoreCase("deptname")
										&& !parmName.equalsIgnoreCase("tdate")
										&& !parmName.equalsIgnoreCase("userName")
										&& !parmName.equalsIgnoreCase("date")
										&& !parmName.equalsIgnoreCase("time")
										&& !parmName.equalsIgnoreCase("fdate")
										&& !parmName.equalsIgnoreCase("empid")
										&& !parmName.equalsIgnoreCase("date1"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
								if (parmName.equalsIgnoreCase("date1"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(DateUtil
											.convertDateToUSFormat(paramValue));
									insertData.add(ob);
								}
							}
						}
						status = cbt.insertIntoTable("attendence_record",
								insertData, connectionSpace);
						String current_date=DateUtil.getCurrentDateIndianFormat();
						LeaveHelper lh=new LeaveHelper();
						String LateComingEmpDetails[]=lh.getEmployeeDetails(empid, "HR", connectionSpace);
						if(LateComingEmpDetails!=null )
						{
						status=DateUtil.getTimeEntryForAttendance(in_time, intime);
						}
						System.out.println("status  "  +status);
						if (status)
						{
                             if(current_date!=null)
                             {
							String mailtext1=getMailText(intime,outtime,finalIncommin,LateComingEmpDetails[0]);
							// new MsgMailCommunication().addMail("empName", "",emp_mailIdss,"Schedule Holiday Change Notification ", mailtext1, "","Pending", "0", "", "HR");
							new MsgMailCommunication().addMail(LateComingEmpDetails[0], "",LateComingEmpDetails[1], finalIncommin, mailtext1, "","Pending", "0", "", "HR"); 
                             }
							returnResult = SUCCESS;
						}
					}
					else
					{
						addActionMessage("Oops There is some error in data!!!!");
					}
					addActionMessage("Data Save Successfully!!!");
				}
				catch (Exception e)
				{
					returnResult = ERROR;
					e.printStackTrace();
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		}

public String getMailText(String intime,String outtime,String finalIncommin,String LateComingEmpDetails) 
{
	
	
     StringBuilder mailtext1 = new StringBuilder("");
	try {
		
		   mailtext1.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td></tr></tbody></table>");
	        mailtext1.append("<b>Dear "+LateComingEmpDetails+",</b>");
	        mailtext1.append("<br>");
	       // mailtext1.append("<br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>This is for your information that holiday for <b> "+HName+"</b> on <b>"+DateUtil.convertDateToUSFormat(fDate)+"</b> has been Cancelled  and adjusted with <b>"+hollidayName+" </b> on <b>"+fromDate+"</b>.</table> ");
	        mailtext1.append("<br>This is for your information that as on <b>"+DateUtil.getCurrentDateIndianFormat()+"</b> you were <b> "+finalIncommin+"</b>. as your shift timings are <b>"+intime+" to "+outtime+" hrs </b>.");
	        mailtext1.append("<br> We request you to please be on time");
	        mailtext1.append("<br> <br>");
	        mailtext1.append("<br><b>Thanks & Regards,</b>");
	        mailtext1.append("<br><b>HR & Admin Team</b>");
	        mailtext1.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
	       	mailtext1.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
	     
		System.out.println("mailtext1" +mailtext1);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return mailtext1.toString();
     
	
}

public String getIncommingStatus(String in_time, String intime)
{
	String spin_time[] = in_time.split(":");
	String spintime[] = intime.split(":");
	int time = Integer.parseInt(spintime[0])
			- Integer.parseInt(spin_time[0]);
	int time1 = Integer.parseInt(spintime[1])
			- Integer.parseInt(spin_time[1]);
	String totalTime = time + ":" + time1;
	return totalTime;
}

public String getFinalIncommingstatus(String incomingStatus)
{
	String finalTime = null;
	String timeSplit[] = incomingStatus.split(":");
	if (incomingStatus != null)
	{
		if (timeSplit[0].startsWith("-") && timeSplit[1].startsWith("-"))
		{
			finalTime = "Late by "
					+ timeSplit[0].substring(1, timeSplit[0].length())
					+ " hour "
					+ timeSplit[1].substring(1, timeSplit[1].length())
					+ " mins";
		}
		else if (timeSplit[0].startsWith("-"))
		{
			int time3 = (Integer.parseInt(timeSplit[0].substring(1,
					timeSplit[0].length())) * 60)
					- Integer.parseInt(timeSplit[1]);
			finalTime = "Late by " + time3 + " mins";
		}
		else if (timeSplit[1].startsWith("-"))
		{
			finalTime = "Late by " + timeSplit[0] + " hour "
					+ timeSplit[1].substring(1, timeSplit[1].length())
					+ " mins";
		}
		else
			finalTime = "Early by " + timeSplit[0] + " hour "
					+ timeSplit[1] + " mins";
	}
	return finalTime;
}



@SuppressWarnings(
		{ "unchecked", "rawtypes" })
		public String addAttendanceConfig()
		{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					String tdate = request.getParameter("date");
					SessionFactory connectionSpace = (SessionFactory) session
							.get("connectionSpace");
					String accountID = (String) session.get("accountid");
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					List<GridDataPropertyView> statusColName = Configuration
							.getConfigurationData("mapped_time_slot_configuration",
									accountID, connectionSpace, "", 0,
									"table_name", "record_data_configuration");
					if (statusColName != null && statusColName.size() > 0)
					{
						// create table query based on configuration
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						List<TableColumes> tableColume = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : statusColName)
						{
							if (!gdp.getColomnName().equalsIgnoreCase("date"))
							{
								TableColumes ob1 = new TableColumes();
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default NULL");
								tableColume.add(ob1);
							}
						}
						TableColumes ob11 = new TableColumes();
						ob11 = new TableColumes();
						ob11.setColumnname("tdate");
						ob11.setDatatype("varchar(255)");
						ob11.setConstraint("default NULL");
						tableColume.add(ob11);

						TableColumes ob111 = new TableColumes();
						ob111 = new TableColumes();
						ob111.setColumnname("total_leaves");
						ob111.setDatatype("varchar(255)");
						ob111.setConstraint("default NULL");
						tableColume.add(ob111);

						cbt.createTable22("report_data", tableColume,
								connectionSpace);
						int totalDays = DateUtil.countWeekendDays(DateUtil
								.convertDateToUSFormat(tdate));
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						String empName=null;
						String finaldates = null;
						if (requestParameterNames != null
								&& requestParameterNames.size() > 0)
						{
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext())
							{
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								System.out.println("parmName" +parmName);
								System.out.println("paramValue"+paramValue);
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")
										&& parmName != null
										&& !parmName.equalsIgnoreCase("deptname")
										&& !parmName.equalsIgnoreCase("date")
										&& !parmName.equalsIgnoreCase("total_leaves"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
									if (parmName!=null && parmName.equalsIgnoreCase("empname")) 
									{
										empName=paramValue;
									}
								}
								if (parmName.equalsIgnoreCase("date"))
								{
									ob = new InsertDataTable();
									ob.setColName("tdate");
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
									finaldates=DateUtil.convertDateToUSFormat(paramValue);
								}
							}
							ob = new InsertDataTable();
							ob.setColName("total_leaves");
							ob.setDataName("0");
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("twdays");
							ob.setDataName(totalDays);
							insertData.add(ob); 
									
							List checkData=cbt.executeAllSelectQuery("SELECT * FROM report_data WHERE empname='"+empName+"' And tdate='"+finaldates+"'", connectionSpace);   
							
							if (checkData!=null && checkData.size()>0) 
							{
								addActionMessage("Data Already Exists !!!!!!");
							}
							else
							{
								status = cbt.insertIntoTable("report_data", insertData,connectionSpace);
							}
							
							if (status)
							{
								addActionMessage("Data Save Successfully!!!");
							}
							returnResult = SUCCESS;
						}
					}
					else
					{
						addActionMessage("Oops There is some error in data!!!!");
					}
				}
				catch (Exception e)
				{
					returnResult = ERROR;
					e.printStackTrace();
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		}

   public String beforedownload() 
   {
	   System.out.println("<<<<<<<<<<<<");
	return SUCCESS;
	   
	}



	public List<ConfigurationUtilBean> getConfiColumnDropdown() {
		return confiColumnDropdown;
	}
	public void setConfiColumnDropdown(
			List<ConfigurationUtilBean> confiColumnDropdown) {
		this.confiColumnDropdown = confiColumnDropdown;
	}
	public List<ConfigurationUtilBean> getConfiColumnText() {
		return confiColumnText;
	}
	public void setConfiColumnText(List<ConfigurationUtilBean> confiColumnText) {
		this.confiColumnText = confiColumnText;
	}
	public void setServletRequest(HttpServletRequest request) 
	{
		this.request=request;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public List<GridDataPropertyView> getLeaveConfiGridColomns() {
		return leaveConfiGridColomns;
	}
	public void setLeaveConfiGridColomns(
			List<GridDataPropertyView> leaveConfiGridColomns) {
		this.leaveConfiGridColomns = leaveConfiGridColomns;
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
	public List<Object> getViewLeaveConfi() {
		return viewLeaveConfi;
	}
	public void setViewLeaveConfi(List<Object> viewLeaveConfi) {
		this.viewLeaveConfi = viewLeaveConfi;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ConfigurationUtilBean> getEmployeeColumnText() {
		return employeeColumnText;
	}
	public void setEmployeeColumnText(List<ConfigurationUtilBean> employeeColumnText) {
		this.employeeColumnText = employeeColumnText;
	}
	public List<Object> getViewEmployeeType() {
		return viewEmployeeType;
	}
	public void setViewEmployeeType(List<Object> viewEmployeeType) {
		this.viewEmployeeType = viewEmployeeType;
	}

	public Map<Integer, String> getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(Map<Integer, String> employeeType) {
		this.employeeType = employeeType;
	}

	public Map<Integer, String> getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Map<Integer, String> leaveType) {
		this.leaveType = leaveType;
	}

	public double getExtraWorking() {
		return extraWorking;
	}

	public void setExtraWorking(double extraWorking) {
		this.extraWorking = extraWorking;
	}
	
}
