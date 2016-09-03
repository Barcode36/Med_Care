package com.Over2Cloud.ctrl.helpdesk.reportconfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AnalyticalReport extends ActionSupport 
{

	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	// Grid Variables Declaration
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
	private boolean loadonce = true;
	// Grid column view
	private String oper;
	private int id;
	private String deptId;
	private String subdeptId;
	private String fromDate;
	private String toDate;
	private String dataFor;
	private String byDeptId;
	private String moduleName=null;
	private Map<String, String> serviceDeptList = null;
	private Map<Integer, String> deptList = null;
	List<FeedbackPojo> feedbackList = null;
	Map<String, Integer> graphMap = null;
	private Map<String, Object>	userdata;
	private List<GridDataPropertyView> masterViewProp=null;
	private List<Object> 	masterViewList;
	private String graphData=null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getAnalytics()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String deptHierarchy = (String) session.get("userDeptLevel");
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				HelpdeskUniversalAction HUA= new HelpdeskUniversalAction();
				serviceDeptList=new LinkedHashMap<String, String>();
				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = HUA.getServiceDept_SubDept(deptHierarchy,orgLevel, orgId,moduleName, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0) 
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();) 
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put( object1[0].toString(), object1[1].toString());
					}
				}
				departmentlist.clear();
				departmentlist=HUA.getDataFromTable("department", colmName,wherClause, order, connectionSpace);
				if (departmentlist!=null && departmentlist.size()>0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							deptList.put ( Integer.parseInt(object[0].toString()),object[1].toString());
						}
					}
				}
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
			return  LOGIN;
		}
	}

	public String getAnalyticsForGrid()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
				{
					return "employeeSucess";
				}
				else 
				{
					return "categorySucess";
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
			return  LOGIN;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String viewAnalysisDeatil()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List totalData = null;
				List onTimedata=null;
				List offTimedata=null;
				List snooze=null;
				List missed=null;
				List ignore=null;
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				feedbackList = new ArrayList<FeedbackPojo>();
				if (fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase(""))
				{
					totalData = new HelpdeskUniversalAction().getAnalyticalGridReport(
							dataFor, DateUtil.convertDateToUSFormat(fromDate),
							DateUtil.convertDateToUSFormat(toDate), deptId,
							subdeptId, searchField, searchString, searchOper,connectionSpace,"Total",moduleName,byDeptId);
				    
					if (totalData!=null && totalData.size()>0)
					{
						
						onTimedata = new HelpdeskUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Ontime",moduleName,byDeptId);
					
						offTimedata= new HelpdeskUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Offtime",moduleName,byDeptId);
						
						snooze= new HelpdeskUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Snooze",moduleName,byDeptId);
					
						missed= new HelpdeskUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Missed",moduleName,byDeptId);
					    
						ignore= new HelpdeskUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Ignore",moduleName,byDeptId);
						
					}
				}
				if (totalData != null && totalData.size() > 0)
				{
					setRecords(totalData.size());
					int to = (getRows() * getPage());
					
					if (to > getRecords()) to = getRecords();
					
					if (dataFor.equals("Employee"))
					{
					   feedbackList = HUH.setAnalyticalReportValues(totalData,onTimedata,offTimedata,snooze,missed,ignore,dataFor);
					}
					else if (dataFor.equals("Category"))
					{
						feedbackList = HUH.setAnalyticalReportValues(totalData,onTimedata,offTimedata,snooze,missed,ignore,dataFor);
					}
				    userdata= new LinkedHashMap<String, Object>();
				    int counter=0,ontime=0,offtime=0,missedT=0,snoozeT=0,perontime=0,perofftime=0,permissed=0,ignoreT=0;
				    for (FeedbackPojo fbp : feedbackList)
				    {
					   counter=counter+Integer.valueOf(fbp.getCounter());
					   ontime=ontime+Integer.valueOf(fbp.getOnTime());
					   offtime=offtime+Integer.valueOf(fbp.getOffTime());
					   missedT=missedT+Integer.valueOf(fbp.getMissed());
					   snoozeT=snoozeT+Integer.valueOf(fbp.getSnooze());
					   perontime=perontime+Integer.valueOf(fbp.getPerOnTime());
					   perofftime=perofftime+Integer.valueOf(fbp.getPerOffTime());
					   permissed=permissed+Integer.valueOf(fbp.getPerMissed());
					   ignoreT=ignoreT+Integer.valueOf(fbp.getIgnore());
				    }
				    if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
				    {
				    	 userdata.put("empId", "0");
				    	if (moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
						{
				    		userdata.put("feedback_by_subdept", "Total : ");
						}
				    	else
				    	{
				    		userdata.put("empName", "Total : ");
				    	}
				    }
				    else if(dataFor!=null && dataFor.equalsIgnoreCase("Category"))
				    {
				       userdata.put("feedId", "0");
					   userdata.put("feedback_subcatg", "Total : ");
				    }
				    userdata.put("counter", counter);
				    userdata.put("onTime", ontime);
				    userdata.put("offTime", offtime);
				    userdata.put("missed", missedT);
				    userdata.put("snooze", snoozeT);
				    userdata.put("ignore", ignoreT);
				    userdata.put("perOnTime", perontime);
				    userdata.put("perOffTime", perofftime);
				    userdata.put("perMissed", permissed);
				    userdata.put("graph", counter+","+ontime+","+offtime+","+missedT+","+snoozeT+",");
				}
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
    @SuppressWarnings("rawtypes")
	public String getAnalyticsGraphData()
    {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List catgData=null;
				if (dataFor!=null && !dataFor.equalsIgnoreCase(""))
				{
					 catgData= new HelpdeskUniversalAction().getAnalyticalGridReport(dataFor,DateUtil.convertDateToUSFormat(fromDate),DateUtil.convertDateToUSFormat(toDate),deptId,subdeptId,searchField,searchString,searchOper,connectionSpace,null,moduleName,byDeptId);
					 if (catgData!=null && catgData.size()>0) 
					 {
			    		 graphMap=new LinkedHashMap<String, Integer>();
			    		 Object[] object =null;
						 for (Iterator iterator = catgData.iterator(); iterator.hasNext();) 
						 {
							object = (Object[]) iterator.next();
							graphMap.put(object[1].toString(),Integer.parseInt(object[2].toString()));
						 }
					  }
				}
				if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
				{
					return "graph_emp_success";
				}
				else 
				{
					return "graph_catg_success";
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
			return  LOGIN;
		}
	}
    
    public String getProductivityDataForGrid()
    {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
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
			return  LOGIN;
		}
	}
    @SuppressWarnings("unchecked")
	public void setViewProp()
    {
    	boolean sessionFlag=ValidateSession.checkSession();
    	if(sessionFlag)
    	{
    		String accountID=(String)session.get("accountid");
    		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
    		masterViewProp=new ArrayList<GridDataPropertyView>();
    		
    		GridDataPropertyView gpv=new GridDataPropertyView();
    		gpv.setColomnName("feedback.id");
    		gpv.setHeadingName("Id");
    		gpv.setHideOrShow("true");
    		masterViewProp.add(gpv);
    		
    		gpv=new GridDataPropertyView();
    		gpv.setColomnName("feedback.ticket_no");
    		gpv.setHeadingName("Ticket No");
    		gpv.setHideOrShow("false");
    		gpv.setAlign("center");
    		gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setWidth(80);
    		masterViewProp.add(gpv);
    		
    		gpv=new GridDataPropertyView();
    		gpv.setColomnName("feedback.via_from");
    		gpv.setHeadingName("Mode");
    		gpv.setHideOrShow("false");
    		gpv.setAlign("center");
    		gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setWidth(60);
    		masterViewProp.add(gpv);
    	
    		gpv=new GridDataPropertyView();
    		gpv.setColomnName("feedback.feed_by");
    		gpv.setHeadingName("Feedback By");
    		gpv.setHideOrShow("false");
    		gpv.setAlign("center");
    		gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setWidth(100);
    		masterViewProp.add(gpv);
    		
    		gpv=new GridDataPropertyView();
    		gpv.setColomnName("feedback.feed_by_mobno");
    		gpv.setHeadingName("Mobile No");
    		gpv.setHideOrShow("false");
    		gpv.setAlign("center");
    		gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setWidth(100);
    		masterViewProp.add(gpv);
    		
    		gpv=new GridDataPropertyView();
    		gpv.setColomnName("dept2.deptName");
    		gpv.setHeadingName("By Department");
    		gpv.setHideOrShow("false");
    		gpv.setAlign("center");
    		gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setWidth(100);
    		masterViewProp.add(gpv);
    	
    		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_lodge_configuration",accountID,connectionSpace,"",0,"table_name","feedback_lodge_configuration");
    		for(GridDataPropertyView gdp:returnResult)
    		{
    			if(!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("feed_by_emailid")&& !gdp.getColomnName().equals("ticket_no")&& !gdp.getColomnName().equals("feed_by")&& !gdp.getColomnName().equals("feed_by_mobno")&& !gdp.getColomnName().equals("by_dept_subdept")&& !gdp.getColomnName().equals("via_from")
    					&& !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("escalation_time") && !gdp.getColomnName().equals("hp_date")&& !gdp.getColomnName().equals("sn_on_date")&& !gdp.getColomnName().equals("moduleName")&& !gdp.getColomnName().equals("feedback_remarks")
    					&& !gdp.getColomnName().equals("feedType") && !gdp.getColomnName().equals("feed_registerby") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("hp_time")&& !gdp.getColomnName().equals("sn_on_time")&& !gdp.getColomnName().equals("sn_upto_date")&& !gdp.getColomnName().equals("transfer_reason")&& !gdp.getColomnName().equals("resolutionTime")
    					&& !gdp.getColomnName().equals("to_dept_subdept") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("resolve_by")&& !gdp.getColomnName().equals("hp_reason")&& !gdp.getColomnName().equals("sn_upto_time")&& !gdp.getColomnName().equals("sn_duration")&& !gdp.getColomnName().equals("transfer_date")&& !gdp.getColomnName().equals("transfer_time")
    					&& !gdp.getColomnName().equals("escalation_date") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("sn_reason")&& !gdp.getColomnName().equals("ig_date")&& !gdp.getColomnName().equals("ig_time")&& !gdp.getColomnName().equals("ig_reason"))
    			{
    				gpv=new GridDataPropertyView();
    				if(gdp.getColomnName().equalsIgnoreCase("category"))
    				{
    					gpv.setColomnName("catg.categoryName");
    				}
    				else if(gdp.getColomnName().equalsIgnoreCase("subCatg"))
    				{
    					gpv.setColomnName("subcatg.subCategoryName");
    				}
    				else if(gdp.getColomnName().equalsIgnoreCase("allot_to"))
    				{
    					gpv.setColomnName("emp.empName");
    					gpv.setHideOrShow("true");
    				}
    				else
    				{
    					gpv.setColomnName("feedback."+gdp.getColomnName());
    					gpv.setHideOrShow(gdp.getHideOrShow());
    				}
    				gpv.setHeadingName(gdp.getHeadingName());
    				gpv.setEditable(gdp.getEditable());
    				gpv.setSearch(gdp.getSearch());
    				gpv.setWidth(120);
    				gpv.setAlign("center");
    				masterViewProp.add(gpv);
    			}
    		}
    		session.put("masterViewProp", masterViewProp);
    	}
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String viewProductivityGridData()
    {
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String subDept=null;
				if (subdeptId!=null && subdeptId.equalsIgnoreCase("null"))
				{
					subDept="-1";
				}
				else
				{
					subDept=subdeptId;
				}
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query1=new StringBuilder("");
				query1.append("SELECT count(*) FROM feedback_status");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				
				if(dataCount!=null && dataCount.size()>0)
				{
					//getting the list of colmuns
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
					if (session.containsKey("masterViewProp"))
					{
						session.remove("masterViewProp");
					}
					StringBuilder query=new StringBuilder("");
					query.append("SELECT ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if(i<(fieldNames.size()-1))
							{
								query.append(gpv.getColomnName().toString()+",");
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
							
						}
						query.append(" FROM feedback_status AS feedback");
						query.append(" INNER JOIN employee_basic emp on feedback.allot_to= emp.id");
						if (moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
						{
							query.append(" INNER JOIN subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
							query.append(" INNER JOIN subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
							query.append(" INNER JOIN department dept1 on subdept1.deptid= dept1.id");
							query.append(" INNER JOIN department dept2 on subdept2.deptid= dept2.id");
						}
						else if(moduleName!=null && moduleName.equalsIgnoreCase("FM"))
						{
							query.append(" INNER JOIN department dept1 on feedback.by_dept_subdept= dept1.id");
							query.append(" INNER JOIN department dept2 on feedback.to_dept_subdept= dept2.id");
						}
						query.append(" INNER JOIN feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
						query.append(" INNER JOIN feedback_category catg on subcatg.categoryName = catg.id ");
						query.append(" WHERE open_date between '"+DateUtil.convertDateToUSFormat(fromDate)   +"' AND '"+DateUtil.convertDateToUSFormat(toDate)   +"'");
						
						if (moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
						{
							if (deptId!=null && !deptId.equals("-1") && subDept!=null && subDept.equals("-1")) 
							{
								query.append(" AND  dept2.id="+deptId+" ");
							}
							else if (deptId!=null && !deptId.equals("-1") && subDept!=null && !subDept.equals("-1"))
							{
								query.append(" AND feedback.to_dept_subdept in (SELECT id FROM subdepartment WHERE deptid="+deptId+"  ");
								if (subDept!=null && !subDept.equalsIgnoreCase(""))
								{
									query.append(" AND id='"+subDept+"' ");
								}
								query.append(")");
							}
						}
						else if (moduleName!=null && moduleName.equalsIgnoreCase("FM"))
						{
							if (deptId!=null && !deptId.equals("-1") ) 
							{
								query.append(" AND  dept2.id="+deptId+" ");
							}
						}
						if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
						{
							query.append("AND feedback.allot_to='"+id+"' ");
						}
						else if (dataFor!=null && dataFor.equalsIgnoreCase("Category"))
						{
							if (byDeptId!=null && !byDeptId.equalsIgnoreCase("-1"))
							{
								query.append("AND dept1.id='"+byDeptId+"' ");
							}
							query.append("AND feedback.subCatg='"+id+"' ");
						}
						if (searchField!=null && searchField.equalsIgnoreCase("OnTime"))
						{
							query.append(" AND feedback.level='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' ");
						}
						else if (searchField!=null && searchField.equalsIgnoreCase("Offtime"))
						{
							query.append(" AND feedback.level!='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' AND allot_to=resolve_by ");
						}
						else if (searchField!=null && searchField.equalsIgnoreCase("Snooze"))
						{
							query.append(" AND feedback.level='Level1' AND feedback.status='Snooze'  ");
						}
						else if (searchField!=null && searchField.equalsIgnoreCase("Missed"))
						{
							query.append(" AND feedback.level!='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' AND allot_to!=resolve_by ");
						}
						else if (searchField!=null && searchField.equalsIgnoreCase("Ignore"))
						{
							query.append(" AND feedback.status='Ignore' ");
						}
						query.append(" AND feedback.moduleName='"+moduleName+"'  ");
					    query.append(" ORDER BY feedback.ticket_no ASC ");
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
									if(obdata[k]!=null)
									{
										if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
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
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
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
	public String getProductivityGraphDataValue()
    {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				graphMap=new LinkedHashMap<String, Integer>();
				if (graphData!=null && !graphData.equalsIgnoreCase(""))
				{
					String value[]=graphData.split(",");
					graphMap.put("On Time", Integer.parseInt(value[0]));
					graphMap.put("Off Time", Integer.parseInt(value[1]));
					graphMap.put("Missed", Integer.parseInt(value[2]));
					graphMap.put("Snooze", Integer.parseInt(value[3]));
					graphMap.put("Ignore", Integer.parseInt(value[4]));
				}
				return "graph_emp_success";
			 }
			 catch (Exception e)
			 {
				e.printStackTrace();
				return ERROR;
			 }
		  }
		  else
		  {
			return  LOGIN;
		  }
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

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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
	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public Map<String, Integer> getGraphMap()
	{
		return graphMap;
	}

	public void setGraphMap(Map<String, Integer> graphMap)
	{
		this.graphMap = graphMap;
	}

	
	public Map<String, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<String, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getSubdeptId()
	{
		return subdeptId;
	}

	public void setSubdeptId(String subdeptId)
	{
		this.subdeptId = subdeptId;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public Map<String, Object> getUserdata()
	{
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata)
	{
		this.userdata = userdata;
	}

	public String getByDeptId()
	{
		return byDeptId;
	}

	public void setByDeptId(String byDeptId)
	{
		this.byDeptId = byDeptId;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getGraphData()
	{
		return graphData;
	}

	public void setGraphData(String graphData)
	{
		this.graphData = graphData;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	
}
