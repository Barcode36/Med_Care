package com.Over2Cloud.ctrl.compliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class ComplianceDashboardView extends ActionSupport{

	private static final long serialVersionUID = 9020231504575415301L;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private List<GridDataPropertyView> masterViewProp=new ArrayList<GridDataPropertyView>();
	private 	Integer 		rows = 0;
	private 	Integer 		page = 0;
	private 	String 			sord = "";
	private 	String 			sidx = "";
	private 	String 			searchField = "";
	private 	String 			searchString = "";
	private 	String 			searchOper = "";
	private 	Integer 		total = 0;
	private 	Integer 		records = 0;
	private 	boolean 		loadonce = false;
	private 	String 			oper;
	private 	String 			id;
	private 	List<Object> 	masterViewList;
	private 	String 			mainHeaderName;
	private 	String 			currentDay;
	private 	String 			currentWeek;
	private 	String 			currentMonth;
	private		String          status;
	private		String			complID;
	private 	String 			frequency;
	private 	String 			dueDate;
	private 	String 			departId;
	private 	String 			totalOrMissed;
	private 	String 			login;
	private 	String          password;
	private 	String 			data4;
	private 	String 			compId;
	Map<String,String> currentCompl=new HashMap<String, String>();
	private String deptName;
	private String taskType;
	private String taskName;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String complianceDashboardDataInGrid()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				
				if(dataCount!=null && dataCount.size()>0)
				{
					//getting the list of colmuns
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if (!gpv.getColomnName().equalsIgnoreCase("comp.reminder1") && !gpv.getColomnName().equalsIgnoreCase("comp.reminder2") && !gpv.getColomnName().equalsIgnoreCase("comp.reminder3")) 
							{
								if(i<(fieldNames.size()-4))
								{
									if(gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("comp.id,");
									}
									else if(gpv.getColomnName().equalsIgnoreCase("actionStatus"))
									{
										query.append("comp.actionStatus,");
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
						}
						query.append(" from compliance_details comp");
						query.append(" LEFT JOIN compliance_task ctn on  ctn.id=comp.taskName LEFT JOIN department dept on dept.id=ctn.departName LEFT JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType WHERE ");
						/*if(getFrequency()!=null && getFrequency()!="")
						{
							query.append(" LEFT JOIN compliance_task ctn on  ctn.id=comp.taskName LEFT JOIN department dept on dept.id=ctn.departName LEFT JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType WHERE ");
						}*/
						if(data4!=null && data4.equalsIgnoreCase("mgmtDashboard"))
						{
							query.append("comp.frequency='"+getFrequency()+"' AND");
						}
						if(getDepartId()!=null && getDepartId()!="" && !getDepartId().equalsIgnoreCase("All"))
						{
							query.append(" comp.taskName IN(Select id from compliance_task WHERE departName='"+getDepartId()+"')");
						}
						if(getDepartId()!=null && getDepartId()!="" && getDepartId().equalsIgnoreCase("All"))
						{
							query.append(" comp.taskName IN(Select id from compliance_task )");
						}
						if(data4!=null && data4.equalsIgnoreCase("mgmtDashboard"))
						{
							if(getTotalOrMissed()!=null && getTotalOrMissed()!="" && getTotalOrMissed().equalsIgnoreCase("Missed"))
							{
								
								if(getFrequency().equalsIgnoreCase("W"))
									query.append(" AND frequency='W' AND (actionTaken='Pending' OR actionTaken='Snooze') AND dueDate<'"+DateUtil.getCurrentDateUSFormat()+"'");
								else if(getFrequency().equalsIgnoreCase("M"))
									query.append(" AND frequency='M' AND (actionTaken='Pending' OR actionTaken='Snooze') AND dueDate<'"+DateUtil.getCurrentDateUSFormat()+"'");
								else if(getFrequency().equalsIgnoreCase("Y"))
									query.append(" AND frequency='Y' AND (actionTaken='Pending' OR actionTaken='Snooze') AND dueDate<'"+DateUtil.getCurrentDateUSFormat()+"'");
								
							}
							else
							{
								if(getFrequency().equalsIgnoreCase("W"))
									query.append(" AND frequency='W' AND (actionTaken='Pending' OR actionTaken='Snooze') ");
								else if(getFrequency().equalsIgnoreCase("M"))
									query.append(" AND frequency='M' AND (actionTaken='Pending' OR actionTaken='Snooze') ");
								else if(getFrequency().equalsIgnoreCase("Y"))
									query.append(" AND frequency='Y' AND (actionTaken='Pending' OR actionTaken='Snooze') ");
							}
						}
						else if(data4!=null && data4.equalsIgnoreCase("ageingDashboard"))
						{
							if(getFrequency().equalsIgnoreCase("W"))
								query.append(" AND comp.dueDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNextDateAfter(7)+"' AND actionTaken<>'Done' AND actionTaken<>'Recurring'");
							else if(getFrequency().equalsIgnoreCase("M"))
								query.append(" AND comp.dueDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat())+"' AND actionTaken<>'Done' AND actionTaken<>'Recurring'");
							else if(getFrequency().equalsIgnoreCase("Y"))
								query.append(" AND comp.dueDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat())+"' AND actionTaken<>'Done' AND actionTaken<>'Recurring'");
						}
						else if(data4!=null && status!=null && data4.equalsIgnoreCase("complianceStatus"))
						{
							if(status.equalsIgnoreCase("Pending"))
							{
								query.append(" AND (comp.actionStatus='Pending')");
							}
							else if(status.equalsIgnoreCase("Snooze"))
							{
								query.append(" AND (comp.actionStatus='Snooze')");
							}
							else if(status.equalsIgnoreCase("Missed"))
							{
								query.append(" AND (comp.actionStatus='Pending') AND comp.dueDate<'"+DateUtil.getCurrentDateUSFormat()+"'");
							}
							else if(status.equalsIgnoreCase("Done"))
							{
								query.append(" AND comp.actionStatus='Done'");
							}
						}
						query.append(" AND comp.id IN ("+compId+")");
						query.append(" ORDER BY comp.dueDate ASC");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						if(data!=null && data.size()>0)
						{
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								int k=0;
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for(GridDataPropertyView gpv:fieldNames)
								{
									if (!gpv.getColomnName().equalsIgnoreCase("comp.reminder1")&& !gpv.getColomnName().equalsIgnoreCase("comp.reminder2") && !gpv.getColomnName().equalsIgnoreCase("comp.reminder3")) 
									{
										if(obdata[k]!=null)
										{
											if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else if (gpv.getColomnName().equalsIgnoreCase("id")) 
											{
												one.put(gpv.getColomnName(),obdata[k].toString());
											   String  reminQry= "SELECT reminder_name,remind_date from compliance_reminder WHERE compliance_id IN("+obdata[k].toString()+")";
											   List remData = cbt.executeAllSelectQuery(reminQry, connectionSpace);
											   if (remData!=null && remData.size()>0) 
											   {
												   boolean rem1 =false,rem2=false,rem3=false;
												   for (Iterator iterator = remData.iterator(); iterator.hasNext();) 
												   {
													  Object[] object = (Object[]) iterator.next();
													  if (object[1]!=null) 
													  { 
														  if (object[0]!=null) 
														  {
																if (object[0].toString().equalsIgnoreCase("Reminder 1")) 
																{
																	one.put("comp.reminder1",DateUtil.convertDateToIndianFormat(object[1].toString()));
																	rem1=true;
																}
																if (object[0].toString().equalsIgnoreCase("Reminder 2")) 
																{
																	one.put("comp.reminder2",DateUtil.convertDateToIndianFormat(object[1].toString()));
																	rem2=true;
																}
																if (object[0].toString().equalsIgnoreCase("Reminder 3")) 
																{
																	one.put("comp.reminder3",DateUtil.convertDateToIndianFormat(object[1].toString()));
																	rem3=true;
																}
																if (!rem1) 
																{
																	 one.put("comp.reminder1","NA");
																}
																if (!rem2) 
																{
																	 one.put("comp.reminder2","NA");
																}
																if (!rem3) 
																{
																	 one.put("comp.reminder3","NA");
																}
														   }
													   }
												   }
											   	}
											   if (remData!=null && remData.size()==1) 
											   {
												   one.put("comp.reminder1","NA");
												   one.put("comp.reminder2","NA");
												   one.put("comp.reminder3","NA");
											   }
											   if (remData!=null && remData.size()==2) 
											   {
												   one.put("comp.reminder2","NA");
												   one.put("comp.reminder3","NA");
											   }
											   if (remData!=null && remData.size()==3) 
											   {
												   one.put("comp.reminder3","NA");
											   }
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
												if(empName!=null && !empName.toString().equalsIgnoreCase(""))
													one.put(gpv.getColomnName(),empName.toString().substring(0, empName.toString().length()-2));
												else
													one.put(gpv.getColomnName(),"NA");
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
											{
												one.put(gpv.getColomnName(),new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.action_type"))
											{
												one.put(gpv.getColomnName(),DateUtil.makeTitle(obdata[k].toString()));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("comp.compid_prefix"))
											{
												one.put(gpv.getColomnName(),obdata[k].toString()+obdata[k+1].toString());
											}
											else
											{
												one.put(gpv.getColomnName(),obdata[k].toString());
											}
											if(gpv.getColomnName().equalsIgnoreCase("comp.dueDate") || gpv.getColomnName().equalsIgnoreCase("comp.snooze_date"))
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
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
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
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String getUpCommingUserCompliancesOnDash()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				List  dataCount = null;
				String userEmpID=null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				
				/*StringBuffer compId=new StringBuffer();
				List  dataCount=cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details",connectionSpace);
				String empString="#"+departId+"#";
				System.out.println(">>> "+empString);
				if(dataCount!=null && dataCount.size()>0)
				{
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();) 
					{
						Object object[] = (Object[]) iterator.next();
						String str="#";
						str=str+object[1].toString();
						if(str.contains(empString))
						{
							compId.append(object[0].toString()+",");
						}
					}
				}*/
				compId = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				if(compId!=null && !compId.toString().equalsIgnoreCase(""))
				{
					//String complianceId=compId.substring(0,(compId.toString().length()-1));
					StringBuilder query1=new StringBuilder("");
					query1.append("select count(*) from compliance_details where id IN("+compId+")");
					dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
					int i=0;
					if(dataCount!=null && dataCount.size()>0)
					{
						List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
						session.remove("masterViewProp");
						StringBuilder query=new StringBuilder("");
						if(fieldNames!=null && fieldNames.size()>0)
						{
							query.append("select ");
							
							for(GridDataPropertyView gpv:fieldNames)
							{
								if (!gpv.getColomnName().equalsIgnoreCase("comp.reminder1") && !gpv.getColomnName().equalsIgnoreCase("comp.reminder2") && !gpv.getColomnName().equalsIgnoreCase("comp.reminder3")) 
								{
									if(i<(fieldNames.size()-4))
									{
										if(gpv.getColomnName().equalsIgnoreCase("id"))
										{
											query.append("comp.id,");
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
							}
							query.append(" from compliance_details comp");
							query.append(" LEFT JOIN compliance_task ctn on  ctn.id=comp.taskName LEFT JOIN department dept on dept.id=ctn.departName LEFT JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType WHERE comp.id IN("+compId+")");
							query.append(" AND (comp.actionTaken='Pending' || comp.actionTaken='Snooze')");
							if (frequency!=null && !frequency.equalsIgnoreCase("") && !frequency.equalsIgnoreCase("undefined") && !frequency.equalsIgnoreCase("-1")) 
							{
								query.append(" AND comp.frequency='"+frequency+"'");
							}
							if (dueDate!=null && !dueDate.equalsIgnoreCase("") && !dueDate.equalsIgnoreCase("undefined")) 
							{
								query.append("AND comp.dueDate='"+DateUtil.convertDateToUSFormat(dueDate)+"'");
							}
							if(deptName!=null && !deptName.equalsIgnoreCase("") && !deptName.equalsIgnoreCase("undefined") && !deptName.equalsIgnoreCase("-1"))
							{
								query.append(" AND ctn.departName="+deptName);
							}
							if(taskType!=null && !taskType.equalsIgnoreCase("") && !taskType.equalsIgnoreCase("undefined") && !taskType.equalsIgnoreCase("-1"))
							{
								query.append(" AND ctn.taskType="+taskType);
							}
							if(taskName!=null && !taskName.equalsIgnoreCase("") && !taskName.equalsIgnoreCase("undefined") && !taskName.equalsIgnoreCase("-1"))
							{
								query.append(" AND ctn.id="+taskName);
							}
							query.append(" ORDER BY comp.dueDate");
							data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						    
							if(data!=null && data.size()>0)
							{
								ComplianceReminderHelper RH=new ComplianceReminderHelper();
								for(Iterator it=data.iterator(); it.hasNext();)
								{
									int k=0;
									Object[] obdata=(Object[])it.next();
									Map<String, Object> one=new HashMap<String, Object>();
									for(GridDataPropertyView gpv:fieldNames)
									{
										if (!gpv.getColomnName().equalsIgnoreCase("comp.reminder1")&& !gpv.getColomnName().equalsIgnoreCase("comp.reminder2") && !gpv.getColomnName().equalsIgnoreCase("comp.reminder3")) 
										{
											if(obdata[k]!=null)
											{
												if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
												{
													one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if (gpv.getColomnName().equalsIgnoreCase("id")) 
												{
												   one.put(gpv.getColomnName(),obdata[k].toString());
												   String  reminQry= "SELECT reminder_name,remind_date from compliance_reminder WHERE compliance_id IN("+obdata[k].toString()+")";
												   List remData = cbt.executeAllSelectQuery(reminQry, connectionSpace);

												   if (remData!=null && remData.size()>0) 
												   {
													   boolean rem1 =false,rem2=false,rem3=false;
													   for (Iterator iterator = remData.iterator(); iterator.hasNext();) 
													   {
														  Object[] object = (Object[]) iterator.next();
														  if (object[1]!=null) 
														  { 
															  if (object[0]!=null) 
															  {
																  if (object[0].toString().equalsIgnoreCase("Reminder 1")) 
																	{
																		one.put("comp.reminder1",DateUtil.convertDateToIndianFormat(object[1].toString()));
																		rem1=true;
																	}
																	if (object[0].toString().equalsIgnoreCase("Reminder 2")) 
																	{
																		one.put("comp.reminder2",DateUtil.convertDateToIndianFormat(object[1].toString()));
																		rem2=true;
																	}
																	if (object[0].toString().equalsIgnoreCase("Reminder 3")) 
																	{
																		one.put("comp.reminder3",DateUtil.convertDateToIndianFormat(object[1].toString()));
																		rem3=true;
																	}
																	if (!rem1) 
																	{
																		 one.put("comp.reminder1","NA");
																	}
																	if (!rem2) 
																	{
																		 one.put("comp.reminder2","NA");
																	}
																	if (!rem3) 
																	{
																		 one.put("comp.reminder3","NA");
																	}
															  }
														  }
													   }
												   	}
												   if (remData!=null && remData.size()==1) 
												   {
													   one.put("comp.reminder1","NA");
													   one.put("comp.reminder2","NA");
													   one.put("comp.reminder3","NA");
												   }
												   if (remData!=null && remData.size()==2) 
												   {
													   one.put("comp.reminder2","NA");
													   one.put("comp.reminder3","NA");
												   }
												   if (remData!=null && remData.size()==3) 
												   {
													   one.put("comp.reminder3","NA");
												   }
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
													if (empName!=null && !empName.toString().equalsIgnoreCase("")) 
													{
														one.put(gpv.getColomnName(),empName.toString().substring(0, empName.toString().length()-2));
													}
													else 
													{
														one.put(gpv.getColomnName(),"NA");
													}
												}
												else if(gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
												{
													one.put(gpv.getColomnName(),RH.getFrequencyName(obdata[k].toString()));
												}
												else if(gpv.getColomnName().equalsIgnoreCase("comp.escalation"))
												{
													if (obdata[k].toString().equalsIgnoreCase("Y"))
													{
														one.put(gpv.getColomnName(),"Yes");
													}
													else
													{
														one.put(gpv.getColomnName(),"No");
													}
												}
												else if(gpv.getColomnName().equalsIgnoreCase("comp.action_type"))
												{
													one.put(gpv.getColomnName(),DateUtil.makeTitle(obdata[k].toString()));
												}
												else if(gpv.getColomnName().equalsIgnoreCase("comp.compid_prefix"))
												{
													one.put(gpv.getColomnName(),obdata[k].toString()+obdata[k+1].toString());
												}
												else
												{
													one.put(gpv.getColomnName(),obdata[k].toString());
												}
												if(gpv.getColomnName().equalsIgnoreCase("comp.dueDate") || gpv.getColomnName().equalsIgnoreCase("comp.snooze_date"))
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
									}
									Listhb.add(one);
								}
								setMasterViewList(Listhb);
								setRecords(masterViewList.size());
								int to = (getRows() * getPage());
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
				/*log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getCompAllotedEmp of class "+getClass(), e);*/
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
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
	public String getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}
	public String getCurrentWeek() {
		return currentWeek;
	}
	public void setCurrentWeek(String currentWeek) {
		this.currentWeek = currentWeek;
	}
	public String getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}
	public Map<String, String> getCurrentCompl() {
		return currentCompl;
	}
	public void setCurrentCompl(Map<String, String> currentCompl) {
		this.currentCompl = currentCompl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public String getComplID() {
		return complID;
	}
	public void setComplID(String complID) {
		this.complID = complID;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getTotalOrMissed() {
		return totalOrMissed;
	}
	public void setTotalOrMissed(String totalOrMissed) {
		this.totalOrMissed = totalOrMissed;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}
	
}