package com.Over2Cloud.ctrl.compliance.complContacts;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.compliance.ComplianceReportAction;
import com.Over2Cloud.ctrl.compliance.complTaskMaster.ComplianceTaskAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
@SuppressWarnings("serial")
public class ComplianceEditGridAction extends ActionSupport implements ServletRequestAware
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	static final Log log = LogFactory.getLog(ComplianceEditGridAction.class);
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String deptLevel = (String) session.get("userDeptLevel");
	private HttpServletRequest request;
	private String id;
	private String fromDate;
	private String toDate;
	private String complId;
	private String taskName;
	private String[] columnNames;
	private Map<String,String> taskTypeMap;
	private List<GridDataPropertyView> masterViewProp=null;
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private Map<String,String> taskMap;
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
	private List<Object> masterViewList;
	private List<Object> masterViewList4;
	private List<Object> masterViewList3;
	private List<Object> masterViewList2;
	private List<Object> masterViewList1;
	private String moduleName;
	private String gridId5;
	private String leveldata;
	private String groupID;
	private String searchFields;
	private String SearchValue;
	
private Map<String,String> deptName;
	
	public String beforeTaskHistoryAdd()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			 try
			 {
				taskTypeMap = new LinkedHashMap<String, String>();
				deptName=new LinkedHashMap<String, String>();
				String userEmpID=null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				
				
				
				String[] userData = new ComplianceUniversalAction().getEmpDataByUserName(userName);
				String userContID=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[0];
				deptName = new ComplianceReportAction().getDeptByMappedEmp(userContID,userEmpID,"COMPL","");
				//taskTypeMap=ct.getAllTaskType(userData[3]);
				return SUCCESS;
			 }
			 catch(Exception exp)
			 {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method beforeTaskHistoryAdd of class "+getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			 }
		}
	   else
	   {
		  return LOGIN;
	   }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public String taskHistoryGrid()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				masterViewProp = new ArrayList<GridDataPropertyView>();
				taskMap = new LinkedHashMap<String, String>();
				String query ="SELECT cd.id,ct.taskName,frequency,cd.dueDate,dept.deptName FROM compliance_details AS cd "+
								"  INNER JOIN compliance_task AS ct  ON ct.id=cd.taskName "+
						        "  INNER JOIN compliance_report AS cr ON cr.complID =  cd.id "+
						        "  INNER JOIN department AS dept ON ct.departName =  dept.id "+
								" WHERE cd.taskName ='"+getTaskName()+"' ORDER BY cd.dueDate ASC LIMIT 0,1";		
				List data = cbt.executeAllSelectQuery(query, connectionSpace);
				if (data!=null && data.size()>0) 
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null) 
						{
							complId=object[0].toString();
						}
						else
						{
							complId ="0";
						}
						if (object[1]!=null) 
						{
							taskMap.put("Task Name", object[1].toString());
						}
						else
						{
							taskMap.put("Task Name", "NA");
						}
						if (object[4]!=null) 
						{
							taskMap.put("Department",object[4].toString()  );
						}
						else
						{
							taskMap.put("Department", "NA");
						}
						if (object[2]!=null) 
						{
							taskMap.put("Frequency", new ComplianceReminderHelper().getFrequencyName(object[2].toString()));
						}
						else
						{
							taskMap.put("Frequency", "NA");
						}
						if (object[3]!=null) 
						{
							taskMap.put("Start From Due Date",DateUtil.convertDateToIndianFormat(object[3].toString())  );
						}
						else
						{
							taskMap.put("Start From Due Date", "NA");
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
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("dept.deptName");
				gpv.setHeadingName("Department");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("comp.reminder_for");
				gpv.setHeadingName("Allot To");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(2000);
				masterViewProp.add(gpv);
				List<GridDataPropertyView> complReportColumn=Configuration.getConfigurationData("mapped_compliance_report_config",accountID,connectionSpace,"",0,"table_name","compliance_report_config");
				if(complReportColumn!=null && complReportColumn.size()>0)
				{
					for(GridDataPropertyView gdp:complReportColumn)
					{
						if(!gdp.getColomnName().equals("costForCompMiss")&& !gdp.getColomnName().equals("current_esc_level")&& !gdp.getColomnName().equals("complainceId") && !gdp.getColomnName().equals("complID") && !gdp.getColomnName().equals("snoozeDate") && !gdp.getColomnName().equals("download") && !gdp.getColomnName().equals("snoozeTime") && !gdp.getColomnName().equals("rescheduleDate") && !gdp.getColomnName().equals("rescheduleTime"))
						{
							gpv=new GridDataPropertyView();
						    if(gdp.getColomnName().equalsIgnoreCase("userName"))
							{
								gpv.setColomnName("userAC.name");
								gpv.setHeadingName(gdp.getHeadingName());
								gpv.setWidth(2000);
							}
							else
							{
								if(gdp.getColomnName().equalsIgnoreCase("document_takeaction") || gdp.getColomnName().equalsIgnoreCase("document_config_1") || gdp.getColomnName().equalsIgnoreCase("document_config_2"))
								{
									gpv.setColomnName(gdp.getColomnName());
									gpv.setHeadingName(gdp.getHeadingName());
									gpv.setWidth(gdp.getWidth());
								}
								else if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("actionTakenDate")) 
								{
									gpv.setColomnName("compReport."+gdp.getColomnName());
									gpv.setHeadingName(gdp.getHeadingName() +" & Time");
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
	public String viewHistoryOfTask()
	{
		String retunString=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from compliance_report");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null && dataCount.size()>0)
				{
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					StringBuilder query=new StringBuilder("");
					query.append("SELECT ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
								if(i<(fieldNames.size()-1))
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
								"INNER JOIN department AS dept ON dept.id=ctn.departName  " +
								"WHERE compReport.complID IN("+complId+") ");
					    if (getFromDate()!=null && !getFromDate().equalsIgnoreCase("") || getToDate()!=null && !getToDate().equalsIgnoreCase("")) 
					    {
					    	query.append(" AND compReport.dueDate BETWEEN '"+DateUtil.convertDateToUSFormat(getFromDate())+"' AND '"+DateUtil.convertDateToUSFormat(getToDate())+"'");
						}
						query.append(" ORDER BY compReport.dueDate ASC");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						
						if(data!=null)
						{
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								int k=0;
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for(GridDataPropertyView gpv:fieldNames)
								{
										if(obdata[k]!=null)
										{
											if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else if (gpv.getColomnName().equalsIgnoreCase("compReport.dueDate")) 
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("document_takeaction") || gpv.getColomnName().equalsIgnoreCase("document_config_1") || gpv.getColomnName().equalsIgnoreCase("document_config_2"))
											{
												String str=obdata[k].toString().substring(obdata[k].toString().lastIndexOf("//")+2, obdata[k].toString().length());
												String docName=str.substring(14,str.length());
												one.put(gpv.getColomnName(),docName);
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.reminder_for"))
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
						"Problem in Over2Cloud  method viewHistoryOfTask of class "+getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			retunString=LOGIN;
		}
		return retunString;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String historyContactAction()
	{
		//System.out.println(">>>>>>");
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				
				//System.out.println("getOper()     "+getOper());
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
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("compliance_contacts", wherClause, condtnBlock,connectionSpace);
				}
				else if(getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						Map<String, Object> wherClause=new HashMap<String, Object>();
						Map<String, Object> condtnBlock;
						String tempIds[]=getId().split(",");
						wherClause.put("work_status","1");
						for(String H:tempIds)
						{
							condtnBlock=new HashMap<String, Object>();
							condtnBlock.put("id",H);
							cbt.updateTableColomn("compliance_contacts", wherClause, condtnBlock,connectionSpace);
						}
					}
				}
				catch(Exception exp)
				{
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method historyContact of class "+getClass(), exp);
					exp.printStackTrace();
				}
				returnResult=SUCCESS;
			}
	   else
	   {
		  returnResult=LOGIN;
	   }
	  return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
    public String getContactMapping5()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				ComplianceContactsAction cca = new ComplianceContactsAction();
				String dept_subDeptId=null;
				dept_subDeptId=cca.getDept_SubdeptId(userName,"1",moduleName);
				List<Object> Listhb=new ArrayList<Object>();
				String contactId=getUpperMappedEmp(dept_subDeptId,moduleName);
				if(contactId!=null)
				{
					StringBuilder query=new StringBuilder("");
					query.append("SELECT distinct contacts.id,emp.empName,contacts.level,emp.mobOne,emp.emailIdOne,emp.designation,grp.groupName FROM compliance_contacts as contacts ");
					query.append("INNER JOIN contact_mapping_detail as mapping on contacts.id=mapping.mapped_with ");
					query.append("INNER JOIN employee_basic as emp on contacts.emp_id=emp.id ");
					query.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId ");
					query.append(" where contacts.id in ("+contactId+") order by emp.empName");
					 if(getSearchFields()!=null && !getSearchFields().equalsIgnoreCase("-1") && getSearchValue()!=null && !getSearchValue().equalsIgnoreCase("-1") )
					 {
						 query.append(" and emp."+getSearchFields()+"='"+getSearchValue()+"' ");
					 }
				
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						setRecords(data.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						Object[] object = null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							object = (Object[]) it.next();
							Map<String, Object> one=new LinkedHashMap<String, Object>();
							
							if(object[0]!=null)
								one.put("id",object[0].toString());
							else 
								one.put("id","NA");
							
							if(object[1]!=null)
								one.put("gridEmpName5",object[1].toString());
							else 
								one.put("gridEmpName5","NA");
							
							if(object[2]!=null)
								one.put("gridLevel5","Level "+object[2].toString());
							else 
								one.put("gridLevel5","NA");
							
							if(object[4]!=null)
								one.put("gridEmpMob5",object[3].toString());
							else 
								one.put("gridEmpMob5","NA");
							
							if(object[4]!=null)
								one.put("gridEmpEmail5",object[4].toString());
							else 
								one.put("gridEmpEmail5","NA");
							
							if(object[5]!=null)
								one.put("gridEmpDesignation5",object[5].toString());
							else 
								one.put("gridEmpDesignation5","NA");
							
							if(object[6]!=null)
								one.put("gridgroupName5",object[6].toString());
							else 
								one.put("gridgroupName5","NA");
							
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						returnResult=SUCCESS;
					}
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getContactMapping5 of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public String getLeveldata() {
		return leveldata;
	}

	public void setLeveldata(String leveldata) {
		this.leveldata = leveldata;
	}

	@SuppressWarnings("rawtypes")
    public String getContactMapping4()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query=new StringBuilder("");
				query.append("SELECT distinct contacts.id,emp.empName,contacts.level,emp.mobOne,emp.emailIdOne,emp.designation,grp.groupName FROM compliance_contacts as contacts ");
				query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
				query.append("inner join employee_basic as emp on contacts.emp_id=emp.id ");
				query.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId ");
				query.append(" where mapping.mapped_with="+id+" order by emp.empName");
				
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object = null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("gridEmpName4",object[1].toString());
						else 
							one.put("gridEmpName4","NA");
						
						if(object[2]!=null)
							one.put("gridLevel4","Level "+object[2].toString());
						else 
							one.put("gridLevel4","NA");
						
						if(object[4]!=null)
							one.put("gridEmpMob4",object[3].toString());
						else 
							one.put("gridEmpMob4","NA");
						
						if(object[4]!=null)
							one.put("gridEmpEmail4",object[4].toString());
						else 
							one.put("gridEmpEmail4","NA");
						
						if(object[5]!=null)
							one.put("gridEmpDesignation4",object[5].toString());
						else 
							one.put("gridEmpDesignation4","NA");
						
						if(object[6]!=null)
							one.put("gridgroupName4",object[6].toString());
						else 
							one.put("gridgroupName4","NA");
						
						Listhb.add(one);
					}
					setMasterViewList4(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getContactMapping5 of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
    public String getContactMapping3()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query=new StringBuilder("");
				query.append("SELECT distinct contacts.id,emp.empName,contacts.level,emp.mobOne,emp.emailIdOne,emp.designation,grp.groupName FROM compliance_contacts as contacts ");
				query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
				query.append("inner join employee_basic as emp on contacts.emp_id=emp.id ");
				query.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId ");
				query.append(" where mapping.mapped_with="+id+" order by emp.empName");
				
				//System.out.println(" Query String  "+query.toString());
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object = null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
					    object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("gridEmpName3",object[1].toString());
						else 
							one.put("gridEmpName3","NA");
						
						if(object[2]!=null)
							one.put("gridLevel3","Level "+object[2].toString());
						else 
							one.put("gridLevel3","NA");
						
						if(object[4]!=null)
							one.put("gridEmpMob3",object[3].toString());
						else 
							one.put("gridEmpMob3","NA");
						
						if(object[4]!=null)
							one.put("gridEmpEmail3",object[4].toString());
						else 
							one.put("gridEmpEmail3","NA");
						
						if(object[5]!=null)
							one.put("gridEmpDesignation3",object[5].toString());
						else 
							one.put("gridEmpDesignation3","NA");
						
						if(object[6]!=null)
							one.put("gridgroupName3",object[6].toString());
						else 
							one.put("gridgroupName3","NA");
						
						Listhb.add(one);
					}
					setMasterViewList3(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getContactMapping5 of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
    public String getContactMapping2()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query=new StringBuilder("");
				query.append("SELECT distinct contacts.id,emp.empName,contacts.level,emp.mobOne,emp.emailIdOne,emp.designation,grp.groupName FROM compliance_contacts as contacts ");
				query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
				query.append("inner join employee_basic as emp on contacts.emp_id=emp.id ");
				query.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId ");
				query.append(" where mapping.mapped_with="+id+" order by emp.empName");
				
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object = null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						 object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("gridEmpName2",object[1].toString());
						else 
							one.put("gridEmpName2","NA");
						
						if(object[2]!=null)
							one.put("gridLevel2","Level "+object[2].toString());
						else 
							one.put("gridLevel2","NA");
						
						if(object[4]!=null)
							one.put("gridEmpMob2",object[3].toString());
						else 
							one.put("gridEmpMob2","NA");
						
						if(object[4]!=null)
							one.put("gridEmpEmail2",object[4].toString());
						else 
							one.put("gridEmpEmail2","NA");
						
						if(object[5]!=null)
							one.put("gridEmpDesignation2",object[5].toString());
						else 
							one.put("gridEmpDesignation2","NA");
						
						if(object[6]!=null)
							one.put("gridgroupName2",object[6].toString());
						else 
							one.put("gridgroupName2","NA");
						
						Listhb.add(one);
					}
					setMasterViewList2(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getContactMapping5 of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
    public String getContactMapping1()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query=new StringBuilder("");
				query.append("SELECT distinct contacts.id,emp.empName,contacts.level,emp.mobOne,emp.emailIdOne,emp.designation,grp.groupName FROM compliance_contacts as contacts ");
				query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
				query.append("inner join employee_basic as emp on contacts.emp_id=emp.id ");
				query.append(" INNER JOIN groupinfo AS grp ON grp.id=emp.groupId ");
				query.append(" where mapping.mapped_with="+id+" order by emp.empName");
				
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object  =null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("gridEmpName1",object[1].toString());
						else 
							one.put("gridEmpName1","NA");
						
						if(object[2]!=null)
							one.put("gridLevel1","Level "+object[2].toString());
						else 
							one.put("gridLevel1","NA");
						
						if(object[4]!=null)
							one.put("gridEmpMob1",object[3].toString());
						else 
							one.put("gridEmpMob1","NA");
						
						if(object[4]!=null)
							one.put("gridEmpEmail1",object[4].toString());
						else 
							one.put("gridEmpEmail1","NA");
						
						if(object[5]!=null)
							one.put("gridEmpDesignation1",object[5].toString());
						else 
							one.put("gridEmpDesignation1","NA");
						
						if(object[6]!=null)
							one.put("gridgroupName1",object[6].toString());
						else 
							one.put("gridgroupName1","NA");
						
						Listhb.add(one);
					}
					setMasterViewList1(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getContactMapping5 of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
    public String getUpperMappedEmp(String dept_subdeptId,String moduleName)
	{
		String contactId=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT distinct mapping.mapped_with FROM compliance_contacts as contacts ");
			query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.mapped_with ");
			query.append("where contacts.forDept_id="+dept_subdeptId+" and contacts.moduleName='"+moduleName+"'");
			List totalMappedData=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			
			query=new StringBuilder();
			query.append("SELECT distinct mapping.contact_id FROM compliance_contacts as contacts ");
			query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
			query.append("where contacts.forDept_id="+dept_subdeptId+" and contacts.moduleName='"+moduleName+"'");
			
			List totalMappedContactData=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if (totalMappedContactData!=null && totalMappedData!=null) 
			{
				StringBuilder contIdStrBuilder=new StringBuilder();
				for (Iterator iterator = totalMappedData.iterator(); iterator.hasNext();) 
				{
					boolean flag=false;
					Object object = (Object) iterator.next();
					flag=totalMappedContactData.contains(object);
					if (!flag) 
					{
						contIdStrBuilder.append(object+",");
					}
				}
				if(contIdStrBuilder!=null && !contIdStrBuilder.toString().equalsIgnoreCase(""))
				{
					contactId=contIdStrBuilder.toString().substring(0, contIdStrBuilder.toString().length()-1);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return contactId;
	}
	
	@SuppressWarnings({ "unchecked" })
    public String getLevelHierarchy(String moduleName,String empId)
	{
		String contactId=null;
		try
		{
			if(moduleName!=null && empId!=null)
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder mappedEmpId=new StringBuilder();
				List contForDeptId=cbt.executeAllSelectQuery("SELECT forDept_id FROM compliance_contacts WHERE emp_id="+empId+" AND moduleName='"+moduleName+"'",connectionSpace);
				if(contForDeptId!=null && contForDeptId.size()>0)
				{
					StringBuilder strBuilder=new StringBuilder();
					for (int deptCount = 0; deptCount < contForDeptId.size(); deptCount++) 
					{
						StringBuilder query5=new StringBuilder();
						query5.append("SELECT distinct mapping.mapped_with FROM compliance_contacts as contacts ");
						query5.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.mapped_with ");
						query5.append("where contacts.forDept_id in("+contForDeptId.get(deptCount)+") and contacts.moduleName='"+moduleName+"' and contacts.emp_id="+empId);
						List mappedData5=cbt.executeAllSelectQuery(query5.toString(),connectionSpace);
						if(mappedData5!=null && mappedData5.size()>0)
						{
							strBuilder=new StringBuilder();
							for (int count = 0; count < mappedData5.size(); count++) 
							{
								strBuilder.append(mappedData5.get(0)+",");
							}
							String mappedWith=strBuilder.toString().substring(0, strBuilder.length()-1);
							if(mappedWith!=null)
							{
								List  mappedData4=executeInnerQuery(mappedWith);
								if(mappedData4!=null && mappedData4.size()>0)
								{
									for (int i=0;i<1;i++) 
									{
										mappedEmpId.append(mappedData4.get(1).toString()+",");
										
										if(mappedData4.get(0)!=null)
										{
											List  mappedData3=executeInnerQuery(mappedData4.get(0).toString());
											if(mappedData3!=null && mappedData3.size()>0)
											{
												for (int j=0;j<1;j++) 
												{
													mappedEmpId.append(mappedData3.get(1).toString()+",");
													if(mappedData3.get(0)!=null)
													{
														List  mappedData2=executeInnerQuery(mappedData3.get(0).toString());
														if(mappedData2!=null && mappedData2.size()>0)
														{
															for (int k=0;k<1;k++) 
															{
																mappedEmpId.append(mappedData2.get(1).toString()+",");
																if(mappedData2.get(0)!=null)
																{
																	List  mappedData1=executeInnerQuery(mappedData2.get(0).toString());
																	if(mappedData1!=null && mappedData1.size()>0)
																	{
																		mappedEmpId.append(mappedData1.get(1).toString()+",");
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if(mappedEmpId!=null && !mappedEmpId.toString().equalsIgnoreCase(""))
						contactId=mappedEmpId.toString().substring(0, mappedEmpId.toString().length()-1);
					}
						
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return contactId;
	}
	
	/*@SuppressWarnings("rawtypes")
    public String getLevelHierarchyOfContId(String moduleName,String empId)
	{
		String contactId=null;
		try
		{
			if(moduleName!=null && empId!=null)
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder mappedContId=new StringBuilder();
				List contForDeptId=cbt.executeAllSelectQuery("SELECT forDept_id FROM compliance_contacts WHERE emp_id="+empId+" AND moduleName='"+moduleName+"'",connectionSpace);
				if(contForDeptId!=null && contForDeptId.size()>0)
				{
					StringBuilder strBuilder=new StringBuilder();
					for (int deptCount = 0; deptCount < contForDeptId.size(); deptCount++) 
					{
						StringBuilder query5=new StringBuilder();
						query5.append("SELECT distinct mapping.mapped_with FROM compliance_contacts as contacts ");
						query5.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.mapped_with ");
						query5.append("where contacts.forDept_id in("+contForDeptId.get(deptCount)+") and contacts.moduleName='"+moduleName+"' and contacts.emp_id="+empId);
						List mappedData5=cbt.executeAllSelectQuery(query5.toString(),connectionSpace);
						if(mappedData5!=null && mappedData5.size()>0)
						{
							strBuilder=new StringBuilder();
							for (int count = 0; count < mappedData5.size(); count++) 
							{
								strBuilder.append(mappedData5.get(0)+",");
							}
							String mappedWith=strBuilder.toString().substring(0, strBuilder.length()-1);
							if(mappedWith!=null)
							{
								List  mappedData4=executeInnerQuery(mappedWith);
								if(mappedData4!=null && mappedData4.size()>0)
								{
									for (int i=0;i<1;i++) 
									{
										mappedContId.append(mappedData4.get(1).toString()+",");
										
										if(mappedData4.get(0)!=null)
										{
											List  mappedData3=executeInnerQuery(mappedData4.get(0).toString());
											if(mappedData3!=null && mappedData3.size()>0)
											{
												for (int j=0;j<1;j++) 
												{
													mappedContId.append(mappedData3.get(1).toString()+",");
													if(mappedData3.get(0)!=null)
													{
														List  mappedData2=executeInnerQuery(mappedData3.get(0).toString());
														if(mappedData2!=null && mappedData2.size()>0)
														{
															for (int k=0;k<1;k++) 
															{
																mappedContId.append(mappedData2.get(1).toString()+",");
																if(mappedData2.get(0)!=null)
																{
																	List  mappedData1=executeInnerQuery(mappedData2.get(0).toString());
																	if(mappedData1!=null && mappedData1.size()>0)
																	{
																		mappedContId.append(mappedData1.get(1).toString()+",");
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if(mappedContId!=null && !mappedContId.toString().equalsIgnoreCase(""))
						contactId=mappedContId.toString().substring(0, mappedContId.toString().length()-1);
					}
						
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return contactId;
	}*/
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List executeInnerQuery(String ids)
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder mappedEmpId=new StringBuilder();
		StringBuilder nextMappedId=new StringBuilder();
		List dataList=new ArrayList();
		StringBuilder query=new StringBuilder();
		query.append("SELECT distinct contacts.id as contId,emp.id as empId FROM compliance_contacts as contacts ");
		query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
		query.append("inner join employee_basic as emp on contacts.emp_id=emp.id ");
		query.append("where mapping.mapped_with IN("+ids+") order by emp.empName");
		List  mappedData=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(mappedData!=null && mappedData.size()>0)
		{
			for (Iterator iterator1 = mappedData.iterator(); iterator1.hasNext();) 
			{
				Object object1[] = (Object[]) iterator1.next();
				nextMappedId.append(object1[0].toString()+",");
				mappedEmpId.append(object1[1].toString()+",");
			}
		}
		if(nextMappedId!=null && mappedEmpId!=null && !mappedEmpId.toString().equalsIgnoreCase("") && !nextMappedId.toString().equalsIgnoreCase(""))
		{
			dataList.add(nextMappedId.toString().substring(0, nextMappedId.toString().length()-1));
			dataList.add(mappedEmpId.toString().substring(0, mappedEmpId.toString().length()-1));
		}
		return dataList;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public Map<String, String> getTaskTypeMap() {
		return taskTypeMap;
	}

	public void setTaskTypeMap(Map<String, String> taskTypeMap) {
		this.taskTypeMap = taskTypeMap;
	}

	public Map<String, String> getDeptName() {
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName) {
		this.deptName = deptName;
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
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public Map<String, String> getTaskMap() {
		return taskMap;
	}
	public void setTaskMap(Map<String, String> taskMap) {
		this.taskMap = taskMap;
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
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public String getComplId() {
		return complId;
	}
	public void setComplId(String complId) {
		this.complId = complId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getGridId5() {
		return gridId5;
	}
	public void setGridId5(String gridId5) {
		this.gridId5 = gridId5;
	}
	public List<Object> getMasterViewList4() {
		return masterViewList4;
	}
	public void setMasterViewList4(List<Object> masterViewList4) {
		this.masterViewList4 = masterViewList4;
	}
	public List<Object> getMasterViewList3() {
		return masterViewList3;
	}
	public void setMasterViewList3(List<Object> masterViewList3) {
		this.masterViewList3 = masterViewList3;
	}
	public List<Object> getMasterViewList2() {
		return masterViewList2;
	}
	public void setMasterViewList2(List<Object> masterViewList2) {
		this.masterViewList2 = masterViewList2;
	}
	public List<Object> getMasterViewList1() {
		return masterViewList1;
	}
	public void setMasterViewList1(List<Object> masterViewList1) {
		this.masterViewList1 = masterViewList1;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getSearchValue() {
		return SearchValue;
	}

	public void setSearchValue(String searchValue) {
		SearchValue = searchValue;
	}
	
	
	
/*public static void main(String[] args)
{
	
	String str=new ComplianceEditGridAction().getLevelHierarchy("1","FM","2418");
	//System.out.println("Final List   "+str);

}
*/	
	
}
