package com.Over2Cloud.common.worktimeing;

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
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class WorkingTimeAction extends GridPropertyBean implements ServletRequestAware{

	private HttpServletRequest request;
	private String dataFor;
	private String actionType;
	
	private String dept;
	private String working_type;
	private String holiday;
	private String fromDay;
	private String toDay;
	private String fromTime="00:00";
	private String toTime="24:00";

	private List<GridDataPropertyView> viewColumnList=null;
	private List<ConfigurationUtilBean> createColumnList = null;
	private Map<Integer, String> deptList=null;
	

	public String firstAction() {
		//System.out.println("Inside First Action Method");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				//System.out.println("data forrr  "+dataFor);
				if (dataFor != null && !dataFor.equals("") && actionType!=null && !actionType.equals("") && actionType.equals("Add")) {
					setWorkingTimeTags();
					// Getting Id, Dept Name for Service Department
					if (dataFor!=null && !dataFor.equals("")) {
					deptList = new LinkedHashMap<Integer, String>();
					List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2",orgLevel, orgId,dataFor, connectionSpace);
					if (departmentlist != null && departmentlist.size() > 0) {
						for (Iterator iterator = departmentlist.iterator(); iterator
								.hasNext();) {
							Object[] object1 = (Object[]) iterator.next();
							deptList.put((Integer) object1[0], object1[1].toString());
						}
					  }
					}
					returnResult = "addsuccess";
				}
				else if (dataFor != null && !dataFor.equals("") && actionType!=null && !actionType.equals("") && actionType.equals("View")) {
					//System.out.println("Inside view block");
					workingTimingColumns();
					boolean checkflag = new HelpdeskUniversalHelper().check_createTable("working_timing", connectionSpace);
					//System.out.println("Check Flag Value is  "+checkflag);
					if (checkflag) {
						List dataList = new HelpdeskUniversalAction().getDataList("working_timing", "dept", "moduleName", dataFor, connectionSpace);
						if (dataList!=null && dataList.size()>0) {
						//	System.out.println("Inside Iffffff");
							 for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
								Object object = (Object) iterator.next();
								String deptType =object.toString();
								if (deptType!=null && !deptType.equals("") && !deptType.equals("NA") && deptType.equals("A")) {
									//System.out.println("Inside A Block");
									returnResult=SUCCESS;
								}
								else if (deptType!=null && !deptType.equals("") && !deptType.equals("NA") && deptType.equals("D")) {
									//System.out.println("Inside D Block");
									returnResult="deptsuccess";
								}
								break;
							}
						}
						else
						 {
						//	System.out.println("Inside Elsesesese");
							returnResult = SUCCESS;
						 }
					 }
				 } 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void workingTimingColumns()
	 {
		//System.out.println("Inside Working timings columns");
		viewColumnList = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewColumnList.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("day");
		gpv.setHeadingName("Day");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setWidth(200);
		viewColumnList.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_working_timeing_configuration",accountID,connectionSpace,"",0,"table_name","working_timeing_configuration");
		//System.out.println("Work Timing List Size is  " + returnResult.size());
		for(GridDataPropertyView gdp:returnResult)
		{
			if (gdp.getColomnName()!=null && !gdp.getColomnName().equals("")
					&& !gdp.getColomnName().equals("fromDay")
					&& !gdp.getColomnName().equals("toDay")) 
			        {
						gpv=new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						viewColumnList.add(gpv);
						//System.out.println(gdp.getColomnName());
			}
		}
		
	}
	
	
	public void setWorkingTimeTags() {
		ConfigurationUtilBean obj;
		createColumnList = new ArrayList<ConfigurationUtilBean>();
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_working_timeing_configuration",accountID, connectionSpace, "", 0, "table_name","working_timeing_configuration");
		//System.out.println("Create Column List Size is  "+reportConfigList.size());
		if (reportConfigList != null && reportConfigList.size() > 0) {
			for (GridDataPropertyView gdv : reportConfigList) {
				//System.out.println("Inside  for "+gdv.getColomnName().trim());
				obj = new ConfigurationUtilBean();
				if (!gdv.getColomnName().trim().equalsIgnoreCase("moduleName")
						&& !gdv.getColomnName().trim().equalsIgnoreCase("userName")
						&& !gdv.getColomnName().trim().equalsIgnoreCase("date")
						&& !gdv.getColomnName().trim().equalsIgnoreCase("time"))
				    {
					//System.out.println("Inside If for "+gdv.getColomnName().trim());
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1")) {
						obj.setMandatory(true);
					} else {
						obj.setMandatory(false);
					}
					createColumnList.add(obj);
				}
			}
			//System.out.println("Create Column List Size is  "+createColumnList.size());
		}
	}
	
	
	public String addWorkingTimings()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				/*
				System.out.println("Dept value  "+dept);
				System.out.println("Working Type value  "+working_type);
				System.out.println("Holiday  "+holiday);
				System.out.println("From Day    "+fromDay);
				System.out.println("To Day  "+toDay);
				System.out.println("From Time    "+fromTime);
				System.out.println("To Time  "+toTime);
				
				*/
			/*	HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				MsgMailCommunication MMC = new MsgMailCommunication();*/
				CommonOperInterface cot = new CommonConFactory().createInterface();
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_working_timeing_configuration", "",connectionSpace, "", 0, "table_name","working_timeing_configuration");

				if (colName != null && colName.size() > 0) {
					@SuppressWarnings("unused")
					boolean status = false;
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName) {
						if (!tableColumes.getColomnName().equals("toDay")) 
						 {
							TableColumes ob1 = new TableColumes();
							if (tableColumes.getColomnName().equalsIgnoreCase("fromDay")) {
								ob1.setColumnname("day");
							}
							else {
								ob1.setColumnname(tableColumes.getColomnName());
							}
							ob1.setDatatype("varchar("+ tableColumes.getWidth() + ")");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
						}
					}

					// Method calling for creating table on the basis of above
					cot.createTable22("working_timing", TableColumnName,connectionSpace);
				}
				if (dept!=null && !dept.equals("") && !dept.equalsIgnoreCase("A")) {
					List<GridDataPropertyView> colName2 = Configuration.getConfigurationData("mapped_working_timeing_configuration", "",connectionSpace, "", 0, "table_name","working_timeing_configuration");

					if (colName2 != null && colName2.size() > 0) {
						@SuppressWarnings("unused")
						boolean status2 = false;
						List<TableColumes> TableColumnName2 = new ArrayList<TableColumes>();
						for (GridDataPropertyView tableColumes2 : colName2) {
							if (!tableColumes2.getColomnName().equals("toDay") 
									&& !tableColumes2.getColomnName().equals("working_type") 
									&& !tableColumes2.getColomnName().equals("holiday")) {
								TableColumes ob1 = new TableColumes();
								if (tableColumes2.getColomnName().equalsIgnoreCase("fromDay")) {
									ob1.setColumnname("day");
								}
								else {
									ob1.setColumnname(tableColumes2.getColomnName());
								}
								ob1.setDatatype("varchar("+ tableColumes2.getWidth() + ")");
								ob1.setConstraint("default NULL");
								TableColumnName2.add(ob1);
							}
						}
						// Method calling for creating table on the basis of above
						cot.createTable22("deptwise_working_timing", TableColumnName2,connectionSpace);
				    }
			     } 
				
				
				
				InsertDataTable ob=null;
				String deptValue="";
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
				  String Parmname = it.next().toString();
				  String paramValue=request.getParameter(Parmname);
				  if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("fromDay") && !Parmname.equalsIgnoreCase("today") && !Parmname.equalsIgnoreCase("dataFor") )
				   {
					 // if (working_type!=null && !working_type.equals("") && !working_type.equalsIgnoreCase("ss")) {
					  if (Parmname.equals("dept")) {
						  ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 if (paramValue!=null && !paramValue.equals("") && !paramValue.equals("A")) {
								 deptValue="D";
							 }
							 else {
								 deptValue="A";
							 }
							 ob.setDataName(deptValue);
							 insertData.add(ob);
					 }
					  else if (Parmname.equals("fromTime")) {
						  ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 if (paramValue==null || paramValue.equals("")) {
								 fromTime="00:00";
							 }
							 ob.setDataName(fromTime);
							 insertData.add(ob);
					 }
					  else if (Parmname.equals("toTime")) {
						  ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 if (paramValue==null || paramValue.equals("")) {
								 toTime="24:00";
							 }
							 ob.setDataName(toTime);
							 insertData.add(ob);
					 }
					else {
						 ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue);
						 insertData.add(ob);
					 }  
				 //  }
				   }
				 }
				
				
				
				   /* System.out.println("To time Value is  "+toTime);
				    System.out.println("From time Value is  "+fromTime);*/
					ob=new InsertDataTable();
				    ob.setColName("working_hrs");
				    ob.setDataName(DateUtil.getTimeDifference(toTime, fromTime));
				    insertData.add(ob);
				   
				  
					//System.out.println("DataFor value is   "+dataFor);
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
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					
					boolean status=false;
					int startPoint = Integer.parseInt(fromDay);
					int endPoint = Integer.parseInt(toDay);
					String dayValue="";
					boolean existFlag=false;
					for (int i = startPoint; i <= endPoint; i++) {
						
					    ob=new InsertDataTable();
						ob.setColName("day");
						if (i==1) {
							dayValue="Monday";
						}
						else if (i==2) {
							dayValue="Tuesday";
						}
						else if (i==3) {
							dayValue="Wednesday";
						}
						else if (i==4) {
							dayValue="Thursday";
						}
						else if (i==5) {
							dayValue="Friday";
						}
						else if (i==6) {
							dayValue="Saturday";
						}
						else if (i==7) {
							dayValue="Sunday";
							
						}
						ob.setDataName(dayValue);
						insertData.add(ob);
						if(deptValue.equals("A"))
						{
						 existFlag= new HelpdeskUniversalHelper().isExist("working_timing", "dept", deptValue, "day", dayValue, "moduleName", dataFor, connectionSpace);
						}
						else {
							existFlag= new HelpdeskUniversalHelper().isExist("working_timing", "dept", deptValue, "", "", "moduleName", dataFor, connectionSpace);
							if (existFlag) {
								boolean depttableflag = saveInDeptTable();
							}
						}
						 if (!existFlag) {
							status=cbt.insertIntoTable("working_timing",insertData,connectionSpace); 
						}
						insertData.remove(insertData.size()-1);
					}
						 addActionMessage("Working Time Added Successfully !!");
			}catch (Exception e) {
                e.printStackTrace();
			}
		}
		else {
			returnResult=LOGIN;
		}
		 return returnResult;
	}
	
	
	
	public boolean saveInDeptTable()
	 {
		try {
			//System.out.println("Dept value  "+dept);
		
			CommonOperInterface cot = new CommonConFactory().createInterface();
			
			InsertDataTable ob=null;
			String deptValue="";
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			 CommonOperInterface cbt=new CommonConFactory().createInterface();
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext())
			{
			  String Parmname = it.next().toString();
			  String paramValue=request.getParameter(Parmname);
			  if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("fromDay") && !Parmname.equalsIgnoreCase("today") && !Parmname.equalsIgnoreCase("working_type")  && !Parmname.equalsIgnoreCase("holiday") && !Parmname.equalsIgnoreCase("dataFor") )
			   {
				 /* if (Parmname.equals("dept")) {
					  ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 if (paramValue!=null && !paramValue.equals("") && !paramValue.equals("A")) {
							 deptValue="D";
						 }
						 else {
							 deptValue="A";
						 }
						 ob.setDataName(deptValue);
						 insertData.add(ob);
				 }
				else {*/
					 ob=new InsertDataTable();
					 ob.setColName(Parmname);
					 ob.setDataName(paramValue);
					 insertData.add(ob);
				//}  
			   }
			}
			
			
			  
			//System.out.println("DataFor value is   "+dataFor);
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
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				
				boolean status=false;
				int startPoint = Integer.parseInt(fromDay);
				int endPoint = Integer.parseInt(toDay);
				String dayValue="";
				for (int i = startPoint; i <= endPoint; i++) {
					
				    ob=new InsertDataTable();
					ob.setColName("day");
					if (i==1) {
						dayValue="Monday";
					}
					else if (i==2) {
						dayValue="Tuesday";
					}
					else if (i==3) {
						dayValue="Wednesday";
					}
					else if (i==4) {
						dayValue="Thursday";
					}
					else if (i==5) {
						dayValue="Friday";
					}
					else if (i==6) {
						dayValue="Saturday";
					}
					else if (i==7) {
						dayValue="Sunday";
						
					}
					ob.setDataName(dayValue);
					insertData.add(ob);
					boolean existFlag=false;
				
						existFlag= new HelpdeskUniversalHelper().isExist("deptwise_working_timing", "dept", dept, "day", dayValue, "moduleName", dataFor, connectionSpace);
						
					 if (!existFlag) {
						status=cbt.insertIntoTable("deptwise_working_timing",insertData,connectionSpace); 
					}
					insertData.remove(insertData.size()-1);
				}
				
	//System.out.println("Status Value is   "+status);
			
			
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return true;
	}
	
	private List<Object> masterViewList;
	
	
	
	public String viewNormalWorkTiming()
	{
		String returnResult=ERROR;
	 	boolean sessionFlag=ValidateSession.checkSession();
	 	if(sessionFlag)
	 	{
			try
			{
				
				//System.out.println("dataFor Value   "+dataFor);
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String deptId=null;
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					List fieldNames=new ArrayList();
					query.append("select ");
					List columnList=Configuration.getColomnList("mapped_working_timeing_configuration", accountID, connectionSpace, "working_timeing_configuration");
					columnList.add("day");
					List<Object> Listhb=new ArrayList<Object>();
					int i=0;
					for(Iterator it=columnList.iterator(); it.hasNext();)
					{
						//System.out.println("Value of I  "+i);
						//generating the dyanamic query based on selected fields
						    Object obdata=(Object)it.next();
						    if(obdata!=null)
						    {
						    	if (!obdata.toString().equalsIgnoreCase("fromDay") && !obdata.toString().equalsIgnoreCase("toDay"))
						    	 {
						    		if(i<columnList.size()-1)
						    		{
								    		query.append(obdata.toString()+",");
								    		fieldNames.add(obdata.toString());
						    		}
								    else
								    {
								    	query.append(obdata.toString());
								    	fieldNames.add(obdata.toString());
								    }
								 }
					}
						    i++;
					}
					query.append(" from working_timing where moduleName='"+dataFor+"' ");
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						//add search  query based on the search operation
						query.append(" and");
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
					//System.out.println("Updated Query    :::   "+query.toString());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					
					if(data!=null)
					 {
						Object[] obdata1 = null;
						for(Iterator it1=data.iterator(); it1.hasNext();)
						{
						    obdata1=(Object[])it1.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k <=fieldNames.size()-1; k++)
							{
							   if(obdata1[k]!=null && !obdata1[k].toString().equalsIgnoreCase(""))
								{
									if(k==0)
									{
										//System.out.println(fieldNames.get(k).toString()+" >>>> "+(Integer)obdata1[k]);
									  one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
									}
								    else 
								    {	
										if(fieldNames.get(k).toString().equals("date"))
										{
										//	System.out.println(fieldNames.get(k).toString()+" >>>> "+DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
											one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else if(fieldNames.get(k).toString().equals("time"))
										{
											//System.out.println(fieldNames.get(k).toString()+" >>>> "+obdata1[k].toString().substring(0, obdata1[k].toString().length()-3));
											one.put(fieldNames.get(k).toString(),obdata1[k].toString().substring(0, obdata1[k].toString().length()-3));
										}
										else
										{
											//System.out.println(fieldNames.get(k).toString()+" >>>>>> "+ obdata1[k].toString());
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
										}
									}
								}
							   else
							   {
								   //System.out.println(fieldNames.get(k).toString()+ " >>>> "+"NA");
								   one.put(fieldNames.get(k).toString(), "NA");
							   }
							}
							Listhb.add(one);
						}
						//System.out.println(Listhb.size());
						setRecords(Listhb.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				
				returnResult=SUCCESS;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
	 	}
	 	else
	 	{
	 		returnResult=LOGIN;
	 	}
	 	//System.out.println("Return list is   "+returnResult);
		return returnResult;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String viewDeptwiseWorkTiming()
	{
		//System.out.println("Inside Deptwise method");
		String returnResult=ERROR;
	 	boolean sessionFlag=ValidateSession.checkSession();
	 	if(sessionFlag)
	 	{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String deptId=null;
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					List fieldNames=new ArrayList();
					query.append("select ");
					List columnList=Configuration.getColomnList("mapped_working_timeing_configuration", accountID, connectionSpace, "working_timeing_configuration");
					columnList.add("day");
					List<Object> Listhb=new ArrayList<Object>();
					int i=0;
					for(Iterator it=columnList.iterator(); it.hasNext();)
					 {
						//System.out.println("Value of I  "+i);
						//generating the dyanamic query based on selected fields
						    Object obdata=(Object)it.next();
						    if(obdata!=null)
						     {
						    	if (!obdata.toString().equalsIgnoreCase("working_type")
						    			&& !obdata.toString().equalsIgnoreCase("holiday")
						    			&& !obdata.toString().equalsIgnoreCase("fromDay")
						    			&& !obdata.toString().equalsIgnoreCase("toDay"))
						    	          {
							    		     if(i<columnList.size()-1)
							    		      {
									    		if (obdata.toString().equalsIgnoreCase("dept"))
			                                    {
										    		query.append("dept.deptName as dept"+",");
										    		fieldNames.add(obdata.toString());
			                                    }
									    		else {
									    			query.append("deptworking."+obdata.toString()+",");
										    		fieldNames.add(obdata.toString());
												}
							    		      }
									         else
									          {
									    	    query.append("deptworking."+obdata.toString());
									    	    fieldNames.add(obdata.toString());
									          }
								         }
					                   }
						          i++;
					     }
					query.append(" from deptwise_working_timing as deptworking");
					query.append(" inner join department as dept on  deptworking.dept=dept.id");
					query.append(" where moduleName='HDM' ");
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						//add search  query based on the search operation
						query.append(" and");
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
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					 {
						Object[] obdata1 = null;
						for(Iterator it1=data.iterator(); it1.hasNext();)
						{
						    obdata1=(Object[])it1.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k <=fieldNames.size()-1; k++)
							{
							   if(obdata1[k]!=null && !obdata1[k].toString().equalsIgnoreCase(""))
								{
									if(k==0)
									{
										//System.out.println(fieldNames.get(k).toString()+" >>>> "+(Integer)obdata1[k]);
									  one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
									}
								    else 
								    {	
										if(fieldNames.get(k).toString().equals("date"))
										{
										//	System.out.println(fieldNames.get(k).toString()+" >>>> "+DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
											one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else if(fieldNames.get(k).toString().equals("time"))
										{
											//System.out.println(fieldNames.get(k).toString()+" >>>> "+obdata1[k].toString().substring(0, obdata1[k].toString().length()-3));
											one.put(fieldNames.get(k).toString(),obdata1[k].toString().substring(0, obdata1[k].toString().length()-3));
										}
										else
										{
											//System.out.println(fieldNames.get(k).toString()+" >>>>>> "+ obdata1[k].toString());
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
										}
									}
								}
							   else
							   {
								  // System.out.println(fieldNames.get(k).toString()+ " >>>> "+"NA");
								   one.put(fieldNames.get(k).toString(), "NA");
							   }
							}
							Listhb.add(one);
						}
						//System.out.println(Listhb.size());
						setRecords(Listhb.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				
				returnResult=SUCCESS;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
	 	}
	 	else
	 	{
	 		returnResult=LOGIN;
	 	}
	 //	System.out.println("Return list is   "+returnResult);
		return returnResult;
	}
	
	
	
	
	
	// Method for Update Feedback Type (In Use)
	@SuppressWarnings("unchecked")
	public String modifyDeptWorkingTime()
	{
		//System.out.println("Inside Method");
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				/*SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");*/
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
					cbt.updateTableColomn("deptwise_working_timing", wherClause, condtnBlock,connectionSpace);
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
						cbt.deleteAllRecordForId("feedback_type", "id", condtIds.toString(), connectionSpace);
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
	
	
	// Method for Update Feedback Type (In Use)
	@SuppressWarnings("unchecked")
	public String modifyNormalWorkingTime()
	{
		//System.out.println("Inside Method");
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				/*SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");*/
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
					cbt.updateTableColomn("working_timing", wherClause, condtnBlock,connectionSpace);
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
						cbt.deleteAllRecordForId("working_timing", "id", condtIds.toString(), connectionSpace);
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
	
	
	
	
	

	public String getDataFor() {
		return dataFor;
	}

	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public List<GridDataPropertyView> getViewColumnList() {
		return viewColumnList;
	}

	public void setViewColumnList(List<GridDataPropertyView> viewColumnList) {
		this.viewColumnList = viewColumnList;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public List<ConfigurationUtilBean> getCreateColumnList() {
		return createColumnList;
	}

	public void setCreateColumnList(List<ConfigurationUtilBean> createColumnList) {
		this.createColumnList = createColumnList;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getWorking_type() {
		return working_type;
	}

	public void setWorking_type(String working_type) {
		this.working_type = working_type;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getFromDay() {
		return fromDay;
	}

	public void setFromDay(String fromDay) {
		this.fromDay = fromDay;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	
	
}
