package com.Over2Cloud.ctrl.compliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceTransferAction extends ActionSupport
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceTransferAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	@SuppressWarnings("rawtypes")
	private Map<Integer, String> empMap = null;
	private Map<Integer, String> deptMap = null;
	private JSONArray jsonArray = null;
	private String userDeptID=null;
	private String modifyFlag;
	private boolean flag =true;
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
	private String fromDept;
	private String deptId;
	private String replaceToEmpName;
	private String selectedId;
	private String replaceByEmpId;
	private String forDept_id;
	private String fromDept_id;
	private List<ConfigurationUtilBean> complTransferColMap=null;
	Map<Integer,String> employeeList;
	private String fromDate;
	private String toDate;
	private String actionStatus;
	private String periodSort;
	private String frequency;
	private String empName;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String beforeAllotedCompView()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				if (modifyFlag!=null && modifyFlag.equalsIgnoreCase("0")) 
				{
					flag=true;
				}
				else if(modifyFlag!=null && modifyFlag.equalsIgnoreCase("1"))
				{
					flag=false;
				}
				ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
				List<GridDataPropertyView>  complianceTransferName=Configuration.getConfigurationData("mapped_comp_transfer_config",accountID,connectionSpace,"",0,"table_name","comp_transfer_config");
				if(complianceTransferName!=null && complianceTransferName.size()>0)
				{
					userDeptID=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[4];
					complTransferColMap=new ArrayList<ConfigurationUtilBean>();
					empMap = new LinkedHashMap<Integer, String>();
					deptMap = new LinkedHashMap<Integer, String>();
					deptMap = cmnOper.getAllContMappedDepartment(userDeptID);
					String empName=new ComplianceUniversalAction().getFieldName("mapped_employee_basic_configuration","employee_basic_configuration","empName");
					for(GridDataPropertyView  gdp:complianceTransferName) 
					{
						ConfigurationUtilBean conUtilBean=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							if(gdp.getColomnName().equalsIgnoreCase("emp_id"))
							{
								conUtilBean.setKey(gdp.getColomnName());
								conUtilBean.setValue(empName);
								conUtilBean.setColType(gdp.getColType());
								empMap = getCompAllotedEmployeeId(userDeptID);
							}
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							complTransferColMap.add(conUtilBean);
						 }
					  }
				   }
					GridDataPropertyView gpv=new GridDataPropertyView();
					gpv.setColomnName("id");
					gpv.setHeadingName("Id");
					gpv.setHideOrShow("true");
					masterViewProp.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("comp.compid_prefix");
					gpv.setHeadingName("Compliance Id");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					masterViewProp.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("comp.compid_suffix");
					gpv.setHeadingName("Compliance Suffix");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("true");
					gpv.setWidth(150);
					masterViewProp.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("ctyp.taskType");
					gpv.setHeadingName("Task Type");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					masterViewProp.add(gpv);
					
					/*gpv=new GridDataPropertyView();
					gpv.setColomnName("comp.escalation");
					gpv.setHeadingName("Escalation");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					masterViewProp.add(gpv);*/
				
					List<GridDataPropertyView> columnMap=Configuration.getConfigurationData("mapped_compliance_configuration",accountID,connectionSpace,"",0,"table_name","compliance_configuration");
					for(GridDataPropertyView gdp:columnMap)
					{
						if(!gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("reminder1") 
								&& !gdp.getColomnName().equals("reminder2") && !gdp.getColomnName().equals("reminder3") && !gdp.getColomnName().equals("taskType") && !gdp.getColomnName().equals("ack_dge")
								&& !gdp.getColomnName().equals("uploadDoc") && !gdp.getColomnName().equals("escalation_level_1")&& !gdp.getColomnName().equals("escalation_level_2")&& !gdp.getColomnName().equals("escalation_level_3")&& !gdp.getColomnName().equals("escalation_level_4") && !gdp.getColomnName().equals("date") && !gdp.getColomnName().equals("time")
								&& !gdp.getColomnName().equals("uploadDoc1") && !gdp.getColomnName().equals("taskBrief") && !gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("compid_prefix") && !gdp.getColomnName().equals("compid_suffix")
								&& !gdp.getColomnName().equals("fromDate")  && !gdp.getColomnName().equals("remindMode") && !gdp.getColomnName().equals("actionTaken")
								&& !gdp.getColomnName().equals("l1EscDuration") && !gdp.getColomnName().equals("l2EscDuration") && !gdp.getColomnName().equals("l3EscDuration") && !gdp.getColomnName().equals("l4EscDuration")
								&& !gdp.getColomnName().equals("comments") && !gdp.getColomnName().equals("snooze_date") && !gdp.getColomnName().equals("snooze_time") && !gdp.getColomnName().equals("completion"))
						{
							gpv=new GridDataPropertyView();
							if(gdp.getColomnName().equalsIgnoreCase("taskName"))
								gpv.setColomnName("ctn."+gdp.getColomnName());
							else
								gpv.setColomnName("comp."+gdp.getColomnName());
							if (gdp.getColomnName().equalsIgnoreCase("userName")) 
							{
								gpv.setHeadingName("Alloted By");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("dueDate")) 
							{
								gpv.setHeadingName(gdp.getHeadingName()+"& Time");
							}
							else
							{
								gpv.setHeadingName(gdp.getHeadingName());
							}
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							gpv.setWidth(gdp.getWidth());
							gpv.setFormatter(gdp.getFormatter());
							masterViewProp.add(gpv);
						}
					}
					gpv=new GridDataPropertyView();
					gpv.setColomnName("cr.actionTakenDate");
					gpv.setHeadingName("Achieved On & At");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					gpv.setFormatter("false");
					masterViewProp.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("cr.actionTakenTime");
					gpv.setHeadingName("Achieved At");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("true");
					gpv.setWidth(150);
					gpv.setFormatter("false");
					masterViewProp.add(gpv);
					
					session.put("masterViewProp", masterViewProp);
					returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method beforeAllotedCompView of class "+getClass(), e);
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
	public Map<Integer, String> getCompAllotedEmployee(String deptId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		Map<Integer, String> empMap = new LinkedHashMap<Integer, String>();
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("SELECT compcont.id,emp.empName FROM employee_basic AS emp " +
					"INNER JOIN compliance_contacts AS compcont ON emp.id=compcont.emp_id WHERE forDept_id="+deptId+" and work_status!=1 and moduleName='COMPL' order by emp.empName asc");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object objectCol[]=null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0] != null && objectCol[1] != null && !objectCol[1].toString().equals(""))
					{
						empMap.put((Integer) objectCol[0], objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getCompAllotedDepartment of class " + getClass(), exp);
		}
		return empMap;
	}
	
	
	@SuppressWarnings("rawtypes")
	public Map<Integer, String> getCompAllotedEmployeeId(String deptId)
	{
		
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		Map<Integer, String> empMap = new LinkedHashMap<Integer, String>();
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("SELECT emp.id,emp.empName FROM employee_basic AS emp " +
					"INNER JOIN compliance_contacts AS compcont ON emp.id=compcont.emp_id WHERE forDept_id="+deptId+" and work_status!=1 and moduleName='COMPL' order by emp.empName asc");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object objectCol[]=null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0] != null && objectCol[1] != null && !objectCol[1].toString().equals(""))
					{
						empMap.put((Integer) objectCol[0], objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getCompAllotedDepartment of class " + getClass(), exp);
		}
		return empMap;
	}
	
	@SuppressWarnings("rawtypes")
	public String getCompAllotedEmp()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				userDeptID=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[4];
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				List data2=null;
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				jsonArray = new JSONArray();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				if (getDeptId()!=null && !getDeptId().equalsIgnoreCase("")) 
				{
					StringBuilder query=new StringBuilder();
					query.append("SELECT emp.id,emp.empName FROM employee_basic AS emp " +
							"INNER JOIN compliance_contacts AS compcont ON emp.id=compcont.emp_id WHERE forDept_id="+userDeptID+" AND compcont.fromDept_id="+deptId+" and work_status!=1 and moduleName='COMPL' order by emp.empName asc");
					data2=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2!=null && data2.size()>0) 
					{
						Object[] object=null;
						JSONObject formDetailsJson = new JSONObject();
						for (Iterator iterator = data2.iterator(); iterator.hasNext();) 
						{
							object = (Object[]) iterator.next();
							if (object!=null) 
							{
								formDetailsJson = new JSONObject();
								formDetailsJson.put("ID", object[0].toString());
								formDetailsJson.put("NAME", object[1].toString());
								jsonArray.add(formDetailsJson);
							}
						}
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getCompAllotedEmp of class "+getClass(), e);
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
	public String getAllotedCompl()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				/*StringBuffer compId=new StringBuffer();
				List  dataCount=cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details",connectionSpace);
				String contactId=null;
				if (empName!=null)
				{
					contactId="#"+empName+"#";
				}
				if(dataCount!=null && dataCount.size()>0)
				{
					Object object[]=null;
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
					{
						object = (Object[]) iterator.next();
						String str="#";
						str=str+object[1].toString();
						if (contactId!=null) 
						{
							System.out.println(str+" >>>> "+contactId);
							if(str.contains(contactId))
							{
								compId.append(object[0].toString()+",");
							}
						}
						if (empName==null) 
						{
							compId.append(object[0].toString()+",");
						}
					}
				}
				System.out.println("compId >> "+compId);*/
				String compId = null;
				List  dataCount = null;
				if(empName!=null)
				{
					compId = getAllComplianceIdByEmpId(empName);
				}
				else if(empName==null)
				{
					String empId=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[6];
					compId = getAllComplianceIdByEmpId(empId);
				}
				if(compId!=null && !compId.toString().equalsIgnoreCase(""))
				{
					//String complianceId=compId.substring(0,(compId.toString().length()-1));
					StringBuilder query1=new StringBuilder("");
					query1.append("select count(*) from compliance_details where id IN("+compId+")");
					dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
					if(dataCount!=null && dataCount.size()>0)
					{
						List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
						session.remove("masterViewProp");
						StringBuilder query=new StringBuilder("");
						if(fieldNames!=null && fieldNames.size()>0)
						{
							query.append("SELECT ");
							int i=0;
							for(GridDataPropertyView gpv:fieldNames)
							{
								if(i<(fieldNames.size()-1))
								{
									if(gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("comp.id,");
									}
									else if(gpv.getColomnName().equalsIgnoreCase("cr.actionTakenDate"))
									{
										if (modifyFlag!=null &&  modifyFlag.equalsIgnoreCase("2")) 
										{
											query.append(gpv.getColomnName().toString()+",");
										}
										else
										{
											query.append(" max(cr.actionTakenDate) AS actionTakenDate,");
										}
									}
									else
									{
										query.append(gpv.getColomnName().toString()+",");
									}
								}
								else
								{
									if(gpv.getColomnName().equalsIgnoreCase("cr.actionTakenTime"))
									{
										if (modifyFlag!=null &&  modifyFlag.equalsIgnoreCase("2")) 
										{
											query.append(gpv.getColomnName().toString());
										}
										else
										{
											query.append(" max(cr.actionTakenTime) AS actionTakenTime");
										}
									}
									else
									{
										query.append(gpv.getColomnName().toString());
									}
								}
								
								i++;
							}
							query.append(" FROM compliance_details comp");
							query.append(" LEFT JOIN compliance_report cr on cr.complID=comp.id ");
							query.append(" LEFT JOIN compliance_task ctn on  ctn.id=comp.taskName ");
							query.append(" LEFT JOIN compl_task_type ctyp on ctyp.id=ctn.taskType ");
							query.append(" LEFT JOIN department dept on dept.id=ctn.departName WHERE comp.id IN("+compId+")  ");
							/*if (empName==null) 
							{
								query.append(" AND ctn.departName="+userDeptID+" ");
							}*/
							if (modifyFlag!=null && !modifyFlag.equalsIgnoreCase("2"))
							{
								query.append(" AND  comp.actionStatus!='Done' ");
							}
							if(periodSort!=null && periodSort.equalsIgnoreCase("actionTakenDate"))
							{
								String str[]=fromDate.split("-");
								if(str[0].length()<3)
								{
									fromDate=DateUtil.convertDateToUSFormat(fromDate);
									toDate=DateUtil.convertDateToUSFormat(toDate);
									query.append(" AND cr.actionTakenDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
								}
								else
								{
									query.append(" AND cr.actionTakenDate BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"'");
								}
							}
							else if(periodSort!=null && periodSort.equalsIgnoreCase("dueDate"))
							{
								String str[]=fromDate.split("-");
								if(str[0].length()<3)
								{
									fromDate=DateUtil.convertDateToUSFormat(fromDate);
									toDate=DateUtil.convertDateToUSFormat(toDate);
									query.append(" AND cr.dueDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
								}
								else 
								{
									query.append(" AND cr.dueDate BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"'");
								}
							}
							else if(periodSort!=null && periodSort.equalsIgnoreCase("All"))
							{
								if (fromDate!=null & !fromDate.equalsIgnoreCase("All") && toDate!=null && !toDate.equalsIgnoreCase("All"))
	                            {
									String str[]=fromDate.split("-");
									if(str[0].length()<3)
									{
										fromDate=DateUtil.convertDateToUSFormat(fromDate);
										toDate=DateUtil.convertDateToUSFormat(toDate);
									}
									query.append(" AND (cr.actionTakenDate BETWEEN '"+fromDate+"' AND '"+toDate+"'");
									query.append(" OR cr.dueDate BETWEEN '"+fromDate+"' AND '"+toDate+"')");
	                            }
							}
							if (frequency!=null && !frequency.equalsIgnoreCase("All")) 
							{
								query.append(" AND comp.frequency = '"+frequency+"'");
							}
							if(actionStatus!=null && !actionStatus.equalsIgnoreCase("ALL"))
							{
								query.append(" AND cr.actionTaken='"+actionStatus+"'");
							}
							query.append(" GROUP BY comp.id ");
							query.append(" ORDER BY comp.actionStatus DESC");
							data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
							if(data!=null &&  data.size()>0)
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
											if(gpv.getColomnName().equalsIgnoreCase("comp.dueDate"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString())+" "+obdata[k+1].toString());
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.reminder_for") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_1") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_2") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_3") || gpv.getColomnName().equalsIgnoreCase("comp.escalation_level_4"))
											{
												StringBuilder empName= new StringBuilder();
												String empId=obdata[k].toString().replace("#", ",").substring(0,(obdata[k].toString().length()-1));
												String query2="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
												List data1=cbt.executeAllSelectQuery(query2,connectionSpace );
												if (data1!=null && data1.size()>0) 
												{
													for (Iterator iterator = data1.iterator(); iterator.hasNext();) 
													{
														Object object = (Object) iterator.next();
														empName.append(object.toString()+", ");
													}
												}
												if (empName!=null) 
												{
													one.put(gpv.getColomnName(),empName.toString().substring(0, empName.toString().length()-2));
												}
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
											{
												one.put(gpv.getColomnName(),new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.compid_prefix"))
											{
												one.put(gpv.getColomnName(),obdata[k].toString()+obdata[k+1].toString());
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.action_type"))
											{
												one.put(gpv.getColomnName(),DateUtil.makeTitle(obdata[k].toString()));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.escalation"))
											{
												if (obdata[k].toString().equalsIgnoreCase("N")) 
												{
													one.put(gpv.getColomnName(),"No");
												} 
												else 
												{
													one.put(gpv.getColomnName(),"Yes");
												}
											}
											else if (gpv.getColomnName().equalsIgnoreCase("cr.actionTakenDate")) 
											{
												one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+" "+obdata[k+1].toString());
											}
											else if (gpv.getColomnName().equalsIgnoreCase("comp.userName")) 
											{
												one.put(gpv.getColomnName(),DateUtil.makeTitle( obdata[k].toString()));
											}
											else
											{
												one.put(gpv.getColomnName(),obdata[k].toString());
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
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getAllotedCompl of class "+getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public List getAllContactIdByEmpId(String empId)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT id FROM compliance_contacts WHERE emp_id="+empId+" AND work_status!=1 AND moduleName='COMPL'");
		List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	public String getAllComplianceIdByEmpId(String empId)
	{
		StringBuilder compId = new StringBuilder();
		List dataList = getAllContactIdByEmpId(empId);
		List  reminderList=cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details",connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				for (Iterator remindItr = reminderList.iterator(); remindItr.hasNext();)
				{
					Object[] remindObj = (Object[]) remindItr.next();
					if(remindObj[0]!=null && remindObj[1]!=null)
					{
						String reminderFor="#"+remindObj[1].toString();
						if(reminderFor.contains("#"+object.toString()+"#"))
						{
							compId.append(remindObj[0].toString()+",");
						}
					}
				}
			}
		}
		compId.append("0");
		return compId.toString();
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	public String updateReminderToPort()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(selectedId!=null)
				{
					String maxId = null,replacedId = null;
					String query2 = "SELECT id FROM compliance_contacts WHERE forDept_id='"+forDept_id+"' AND emp_id='"+replaceToEmpName+"' AND work_status!=1 AND moduleName='COMPL'";
					List dataList=cbt.executeAllSelectQuery(query2,connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						replacedId = dataList.get(0).toString();
					}
					dataList.clear();
					String query1="SELECT id FROM compliance_contacts WHERE forDept_id='"+forDept_id+"' AND emp_id='"+replaceByEmpId+"' AND work_status!=1 AND moduleName='COMPL'";
					dataList=cbt.executeAllSelectQuery(query1,connectionSpace);
					if(dataList.size()==0)
					{
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						InsertDataTable ob=null;
							
						ob=new InsertDataTable();
						ob.setColName("forDept_id");
						ob.setDataName(forDept_id);
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("fromDept_id");
						ob.setDataName(fromDept_id);
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("emp_id");
						ob.setDataName(replaceByEmpId);
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
			  			ob.setColName("work_status");
			  			ob.setDataName("0");
			  			insertData.add(ob);
			  			
			  			ob=new InsertDataTable();
			  			ob.setColName("level");
			  			ob.setDataName("1");
			  			insertData.add(ob);
			  			
			  			ob=new InsertDataTable();
			  			ob.setColName("moduleName");
			  			ob.setDataName("COMPL");
			  			insertData.add(ob);
			  			
			  			ob=new InsertDataTable();
			  			ob.setColName("deptLevel");
			  			ob.setDataName("1");
			  			insertData.add(ob);
		  			
			  			maxId = String.valueOf(cbt.insertDataReturnId("compliance_contacts",insertData,connectionSpace)); 
					}
					else
					{
						maxId = dataList.get(0).toString();
					}
					String query="select id ,reminder_for,escalation_level_1,escalation_level_2,escalation_level_3,escalation_level_4 from compliance_details where id IN("+selectedId+")";
					dataList=cbt.executeAllSelectQuery(query,connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
							Object object[] = (Object[]) iterator.next();
							StringBuilder stringBuilder=new StringBuilder();
							StringBuilder escL1=new StringBuilder();
							StringBuilder escL2=new StringBuilder();
							StringBuilder escL3=new StringBuilder();
							StringBuilder escL4=new StringBuilder();
							Map<String, Object> setVariables=new HashMap<String, Object>();
							Map<String, Object> whereCondition=new HashMap<String, Object>();
							
							if(object[1]!=null && !object[1].toString().equalsIgnoreCase(""))
							{
								String str[]=object[1].toString().split("#");
								for(int j=0;j<str.length;j++)
								{
									if(str[j].equalsIgnoreCase(replacedId))
									{
										str[j]=maxId;
									}
									stringBuilder.append(str[j]+"#");
								}
								setVariables.put("reminder_for", stringBuilder.toString());
							}
							if(object[2]!=null && !object[2].toString().equalsIgnoreCase(""))
							{
								//System.out.println("object[2].toString()   "+object[2].toString());
								String str[]=object[2].toString().split("#");
								for(int j=0;j<str.length;j++)
								{
									if(str[j].equalsIgnoreCase(replacedId))
									{
										str[j]=maxId;
									}
									escL1.append(str[j]+"#");
								}
								setVariables.put("escalation_level_1", escL1.toString());
							}
							if(object[3]!=null && !object[3].toString().equalsIgnoreCase(""))
							{
								//System.out.println("object[3].toString()   "+object[3].toString());
								String str[]=object[3].toString().split("#");
								for(int j=0;j<str.length;j++)
								{
									if(str[j].equalsIgnoreCase(replacedId))
									{
										str[j]=maxId;
									}
									escL2.append(str[j]+"#");
								}
								setVariables.put("escalation_level_2", escL2.toString());
							}
							if(object[4]!=null && !object[4].toString().equalsIgnoreCase(""))
							{
								//System.out.println("object[4].toString()   "+object[4].toString());
								String str[]=object[4].toString().split("#");
								for(int j=0;j<str.length;j++)
								{
									if(str[j].equalsIgnoreCase(replacedId))
									{
										str[j]=maxId;
									}
									escL3.append(str[j]+"#");
								}
								setVariables.put("escalation_level_3", escL3.toString());
							}
							if(object[5]!=null && !object[5].toString().equalsIgnoreCase(""))
							{
								//System.out.println("object[5].toString()   "+object[5].toString());
								String str[]=object[5].toString().split("#");
								for(int j=0;j<str.length;j++)
								{
									if(str[j].equalsIgnoreCase(replacedId))
									{
										str[j]=maxId;
									}
									escL4.append(str[j]+"#");
								}
								setVariables.put("escalation_level_4", escL4.toString());
							}
							whereCondition.put("id",object[0].toString());
							cbt.updateTableColomn("compliance_details", setVariables, whereCondition, connectionSpace);
						}
						returnResult=SUCCESS;
						addActionMessage("Compliance Transfer successfully!!!");
					}
					else
					{
						addActionMessage("OOPS There is some error in data !!!");
						returnResult=SUCCESS;
					}
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method updateReminderToPort of class "+getClass(), e);
				e.printStackTrace();
				addActionMessage("Opps there are some error on transfer...");
			}
			
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public Map<Integer, String> getEmpMap() {
		return empMap;
	}

	public void setEmpMap(Map<Integer, String> empMap) {
		this.empMap = empMap;
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

	public String getFromDept() {
		return fromDept;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public List<ConfigurationUtilBean> getComplTransferColMap() {
		return complTransferColMap;
	}


	public void setComplTransferColMap(
			List<ConfigurationUtilBean> complTransferColMap) {
		this.complTransferColMap = complTransferColMap;
	}

	public Map<Integer, String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Map<Integer, String> employeeList) {
		this.employeeList = employeeList;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	public String getReplaceToEmpName()
	{
		return replaceToEmpName;
	}

	public void setReplaceToEmpName(String replaceToEmpName)
	{
		this.replaceToEmpName = replaceToEmpName;
	}

	public String getReplaceByEmpId()
	{
		return replaceByEmpId;
	}

	public void setReplaceByEmpId(String replaceByEmpId)
	{
		this.replaceByEmpId = replaceByEmpId;
	}

	public String getForDept_id() {
		return forDept_id;
	}

	public void setForDept_id(String forDept_id) {
		this.forDept_id = forDept_id;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getUserDeptID() {
		return userDeptID;
	}

	public void setUserDeptID(String userDeptID) {
		this.userDeptID = userDeptID;
	}

	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getFromDept_id() {
		return fromDept_id;
	}

	public void setFromDept_id(String fromDept_id) {
		this.fromDept_id = fromDept_id;
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

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}

	public String getPeriodSort()
	{
		return periodSort;
	}

	public void setPeriodSort(String periodSort)
	{
		this.periodSort = periodSort;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}
	
}
