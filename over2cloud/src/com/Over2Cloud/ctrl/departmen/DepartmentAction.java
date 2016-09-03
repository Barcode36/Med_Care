package com.Over2Cloud.ctrl.departmen;

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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DepartmentAction extends ActionSupport implements ServletRequestAware{

	static final Log log = LogFactory.getLog(DepartmentAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private int levelOforganization=2;
	private String level1=new String();
	private Map<Integer, String>deptList=new LinkedHashMap<Integer, String>();
	private Map<Integer, String>levelList=new HashMap<Integer, String>();
	private String orgIdInLevel;
	//department creation parameters
	private String level2org;
	private String level3org;
	private String level4org;
	private String level5org;
	private String deptName1;
	private String deptName;
	private String selectedorgId;
	//Grid view
	private String headerName;
	private String editDeptData;
	private int id;
	private List<GridDataPropertyView>level1ColmnNames=new ArrayList<GridDataPropertyView>();
	private String headerNameForDept;
	private HttpServletRequest request;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String searchFields;
	private String SearchValue;
	private Map<Integer, String> deptMap;
	private Map<String,String> totalCount;
	private String totalGroupName;
	
	// Added after 8th April by Avinash
	
	private List<ConfigurationUtilBean> departmentDropDown;
	private List<ConfigurationUtilBean> departmentTextBox;
	private Map<Integer,String> officeMap;
	private Map<Integer,String> groupMap;
	private boolean hodStatus;
	private boolean mgtStatus;
	
	public void setDeptAddPageFileds()
	{
		try
		{
			List<GridDataPropertyView> gridFields=Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
			if(gridFields!=null)
			{
				departmentDropDown=new ArrayList<ConfigurationUtilBean>();
				departmentTextBox=new ArrayList<ConfigurationUtilBean>();
				for(GridDataPropertyView  gdp:gridFields)
				{
					ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("orgnztnlevel"))
                    {
                    	objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        departmentDropDown.add(objdata);
                        
                        if(gdp.getColomnName().equalsIgnoreCase("mappedOrgnztnId"))
                        {
                        	String userType = (String)session.get("userTpe");
                        	officeMap=new LinkedHashMap<Integer, String>();
                        	String query =null;
                        	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                        	if (userType!=null && userType.equalsIgnoreCase("H")) 
                        	{
                        		hodStatus=true;
                        		query = "SELECT id,name FROM branchoffice_data WHERE headOfficeId = '"+s[2]+"' ORDER BY name";
							} 
                        	else if(userType!=null && userType.equalsIgnoreCase("M"))
                        	{
                        	    mgtStatus = true;
                        	    hodStatus = true;
                        		query = "SELECT id,name FROM country_data ORDER BY name";
                        	}
                        	else 
                        	{
                        		query = "SELECT id,groupName FROM groupinfo WHERE regLevel = '"+s[1]+"' ORDER BY groupName";
							}
            				if (query!=null)
            				{
            					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
                				if (dataList != null && dataList.size() > 0)
                				{
                					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
                					{
                						Object[] object = (Object[]) iterator.next();
                						if (object[0] != null && object[1] != null)
                						{
                							officeMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
                						}
                					}
                				}
							}
                        }
                        else if(gdp.getColomnName().equalsIgnoreCase("groupId"))
                        {
                        	groupMap=new HashMap<Integer, String>();
                        }
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("flag"))
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        departmentTextBox.add(objdata);
                       
                    }
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method setDepartmentAndSubDeptNames(int deptFalgOrSubDept) of class "+getClass(), e);
			e.printStackTrace();
		}
	
	}
	
	@SuppressWarnings("unused")
	public String checkDeptAvailability()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				String searchQry="select flag from department where groupId='"+getSelectedorgId()+"' and deptName like '"+getDeptName()+"%' ";
				List data=new createTable().executeAllSelectQuery(searchQry, connectionSpace);;
				if(data.size()>0 && data!=null && data.get(0)!=null)
				{
					if(data.get(0).equals("Inactive"))
					{
						addActionMessage("Contact Sub Type Already Exists InActive Mode plz Make It Active !!!");
					}
					else
					{
						addActionMessage("Contact Sub Type Already Exists !!!");
					}	
				}
				else
				{
					addActionMessage("Contact Sub Type Doesnot Exists !!!");
				}
				
				return SUCCESS;
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
	
	public String beforeDeptAdd()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				setDeptAddPageFileds();
				return SUCCESS;
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
	
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String createDepartment()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				System.out.println(">>>>>>>>>>>>>>>>>>>");
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
	    	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	    boolean userTrue=false;
                boolean dateTrue=false;
                boolean timeTrue=false;
                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
                for(GridDataPropertyView gdp:statusColName)
                {
                     if(gdp.getColomnName()!=null)
                     {
                    	 TableColumes ob1=new TableColumes();
                         ob1=new TableColumes();
                         ob1.setColumnname(gdp.getColomnName());
                         ob1.setDatatype("varchar(255)");
                         if(gdp.getColomnName().equalsIgnoreCase("flag"))
                         {
                        	 ob1.setConstraint("default 'Active'");
                         }
                         else
                         {
                        	 ob1.setConstraint("default NULL");
                         }
                         Tablecolumesaaa.add(ob1);
                         if(gdp.getColomnName().equalsIgnoreCase("userName"))
                             userTrue=true;
                         else if(gdp.getColomnName().equalsIgnoreCase("date"))
                             dateTrue=true;
                         else if(gdp.getColomnName().equalsIgnoreCase("time"))
                             timeTrue=true;
                     }
                }
                boolean exists=false;
                cbt.createTable22("department",Tablecolumesaaa,connectionSpace);
             
                List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
                InsertDataTable ob=null;
                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();
               
                String /*mappedOrg=null,*/groupId=null;
                while(it.hasNext())
                {
                	String Parmname = it.next().toString();
                    String paramValue=request.getParameter(Parmname);
                    /*if(Parmname.equalsIgnoreCase("mappedOrgnztnId"))
                    {
                    	mappedOrg=paramValue;
                    }
                    else */
                    if(Parmname.equalsIgnoreCase("groupId"))
                    {
                    	groupId=paramValue;
                    }
                }
                //change insude mappingcontact
                if(groupId!=null)
                {
                	String arr[]=getDeptName().trim().split(",");
                	/*String mappedwithcheck=request.getParameter("mappedOrgnztnId");
                	String mappedwith=null;
                	if(mappedwithcheck.equalsIgnoreCase("1")){
                		mappedwith="country";
                	}else if(mappedwithcheck.equalsIgnoreCase("2")){
                		mappedwith="headoffice";
                	}else {
                		mappedwith="branchoffice";
                	}*/
                	if(arr!=null )
                	{
                		for (int i = 0; i < arr.length; i++) 
                		{
							if(arr[i]!=null && !arr[i].equalsIgnoreCase(" "))
							{
								insertData=new ArrayList<InsertDataTable>();
								exists=new HelpdeskUniversalHelper().isExist("department", "deptName", arr[i].trim(),"groupId",groupId,"","",connectionSpace);
								
								if(!exists)
								{
									ob=new InsertDataTable();
					                ob.setColName("deptName");
					                ob.setDataName(arr[i].trim());
					                insertData.add(ob);
					                
					                /*ob=new InsertDataTable();
						            ob.setColName("mappedOrgnztnId");
						            ob.setDataName(mappedOrg);
						            insertData.add(ob);*/
						            
						            ob=new InsertDataTable();
						            ob.setColName("groupId");
						            ob.setDataName(groupId);
						            insertData.add(ob);
						                
						            ob=new InsertDataTable();
						            ob.setColName("orgnztnlevel");
						            ob.setDataName("1");
						            insertData.add(ob);
						                
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
						                    ob.setDataName(DateUtil.getCurrentTimeHourMin());
						                    insertData.add(ob);
						            }
						          // cbt.insertIntoTable("department",insertData,connectionSpace);
						            int maxId=cbt.insertDataReturnId("department",insertData,connectionSpace);
				                	if (maxId>0)
									{
				                		StringBuilder fieldsNames=new StringBuilder();
				                		StringBuilder fieldsValue=new StringBuilder();
				                		if (insertData!=null && !insertData.isEmpty())
										{
											int j=1;
											for (InsertDataTable h : insertData)
											{
												for(GridDataPropertyView gdp:statusColName)
												{
													if(h.getColName().equalsIgnoreCase(gdp.getColomnName()))
													{
														if (j < insertData.size())
														{
															fieldsNames.append(gdp.getHeadingName()+", ");
															if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
					                                    	{
																fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString())+", ");
					                                    	}
															else
															{
																fieldsValue.append(h.getDataName()+", ");
															}
														}
														else
														{
															fieldsNames.append(gdp.getHeadingName());
															if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
					                                    	{
																fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
					                                    	}
															else
															{
																fieldsValue.append(h.getDataName());
															}
														}
													}
												}
												j++;
											}
										}
				                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
				                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Contact Sub Type",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
									}
								}
								else
								{
									addActionMessage("Contact Sub Type "+ arr[i].trim()+" Already Exists !!!");
								}
							}
						}
                	}
                }
                if (!exists)
				{
                	 addActionMessage("Contact Sub Type Added Successfully !!!");
				}
				return SUCCESS;
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
		
		
		
		
		
		/*
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getLevel2org()!=null)
			{
				 String mappedorgId=new String();
				 try
				 {
						 if(getLevelOforganization()==2 && getLevel2org()!=null && !getLevel2org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel2org();
						 }
						 else if(getLevelOforganization()==3 && getLevel3org()!=null && !getLevel3org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel3org();
						 }
						 else if(getLevelOforganization()==4 && getLevel4org()!=null && !getLevel4org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel4org();
						 }
						 else if(getLevelOforganization()==5 && getLevel5org()!=null && !getLevel5org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel5org();
						 }
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				 boolean status=false;
				 if(mappedorgId!=null && !mappedorgId.equalsIgnoreCase(""))
				 {
					 CommonOperInterface cbt=new CommonConFactory().createInterface();
					 List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
					    boolean userTrue=false;
						boolean dateTrue=false;
						boolean timeTrue=false;	
					        if(org2!=null)
						{
							//create table query based on configuration
							List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
							for(GridDataPropertyView gdp:org2)
							{
								 TableColumes ob1=new TableColumes();
								 ob1=new TableColumes();
								 ob1.setColumnname(gdp.getColomnName());
								 ob1.setDatatype("varchar(255)");
								 ob1.setConstraint("default NULL");
								 Tablecolumesaaa.add(ob1);
								 if(gdp.getColomnName().equalsIgnoreCase("userName"))
									 userTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("date"))
									 dateTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("time"))
									 timeTrue=true;
							}
							cbt.createTable22("department",Tablecolumesaaa,connectionSpace);
						}
						
						//getting the parameters nd setting their value using loop
						 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 InsertDataTable ob=null;
						 List paramList=new ArrayList<String>();
						 int paramValueSize=0;
						 boolean statusTemp=true;
						while (it.hasNext()) {
							String Parmname = it.next().toString();
							String paramValue[]=request.getParameterValues(Parmname);
						   if(paramValue!=null && Parmname!=null 
								   && Parmname.equalsIgnoreCase("orgnztnlevel"))
							{
							   setLevelOforganization(Integer.parseInt(paramValue[0]));
							   
							}
						   else if(paramValue!=null && Parmname!=null 
									 && !Parmname.equalsIgnoreCase("orgLevel")
									&& !Parmname.equalsIgnoreCase("levelOforganization")&& !Parmname.equalsIgnoreCase("level1")&& !Parmname.equalsIgnoreCase("level")&& !Parmname.equalsIgnoreCase("level2org")
									&& !Parmname.equalsIgnoreCase("level3org")&& !Parmname.equalsIgnoreCase("level4org")&& !Parmname.equalsIgnoreCase("level5org"))
								{
							   		//adding the parameters list.
							   		paramList.add(Parmname);
							   		if(statusTemp)
							   		{
								   		String tempParamValueSize[]=request.getParameterValues(Parmname);
								   		for(String H:tempParamValueSize)
								   		{
								   			//counting one time size of the parameter value
								   			if(!H.equalsIgnoreCase("") && H!=null)
								   				paramValueSize++;	
								   		}
								   		statusTemp=false;
							   		}
								}
						}
						String parmValuew[][]=new String[paramList.size()][paramValueSize];
						int m=0;
							for (Object c : paramList) {
								Object Parmname = (Object) c;
								String paramValue[]=request.getParameterValues(Parmname.toString());
								for(int j=0;j<paramValueSize;j++)
								{
									if(!paramValue[j].equalsIgnoreCase("") && paramValue[j]!=null)
									{
										parmValuew[m][j]=paramValue[j];
									}
								}
								m++;
							}
						
						for(int i=0;i<paramValueSize;i++)
						{
							boolean deptblank=true;
							boolean depExists=false;
							List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							for(int k=0;k<paramList.size();k++)
							{
								if(paramList.get(k).toString().equalsIgnoreCase("deptName"))
								{
									if(parmValuew[k][i].toString()==null || !parmValuew[k][i].toString().equalsIgnoreCase(""))
									{
										deptblank=false;
										depExists=new HelpdeskUniversalHelper().isExist("department", "deptName", parmValuew[k][i], "", "", "","","","", connectionSpace);
									}
								}
								 ob=new InsertDataTable();
								 ob.setColName(paramList.get(k).toString());
								 ob.setDataName(DateUtil.makeTitle(parmValuew[k][i]));
								 insertData.add(ob);
							}
							 ob=new InsertDataTable();
							 ob.setColName("mappedOrgnztnId");
							 ob.setDataName(mappedorgId);
							 insertData.add(ob);
							 ob=new InsertDataTable();
							 ob.setColName("orgnztnlevel");
							 ob.setDataName(getLevelOforganization());
							 insertData.add(ob);
							 
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
							 if(!deptblank)
							 {
								 if(!depExists)
								 {
									 status=cbt.insertIntoTable("department",insertData,connectionSpace);
									 addActionMessage("Department Registered Successfully!!!");
									 return SUCCESS;
								 }
								 else
								 {
									 addActionMessage("Department Already Exists!!!");
									 return SUCCESS;
								 }
								 
							 }
						}
				 }
				 if(status)
				 {
					 addActionMessage("Department Registered Successfully!!!");
					 return SUCCESS;
				 }
				 else
				 {
					 addActionMessage("Oops There is some error in data!");
					 return SUCCESS;
				 }
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createDepartment of class "+getClass(), e);
			e.printStackTrace();
			 addActionError("Ooops There Is Some Problem In Department Creation!");
			 return ERROR;
		}
		return SUCCESS;
	*/
		
	}
	
	/**
	 * Ajax calling for getting the list of all dept based on the selected level of organization and the organization ID
	 * @return
	 */
	public String getDeptData()
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List lowerLevel3=new ArrayList<String>();
			lowerLevel3.add("id");
			lowerLevel3.add("deptname");
			Map<String, Object> temp=new HashMap<String, Object>();
			Map<String, Object> order=new HashMap<String, Object>();
			temp.put("orgnztnlevel",getOrgIdInLevel());
			temp.put("mappedOrgnztnId",getSelectedorgId());
			order.put("deptname", "ASC");
			lowerLevel3=cbt.viewAllDataEitherSelectOrAllByOrder("department",lowerLevel3,temp,order,connectionSpace);
			if(temp!=null && temp.size()>0)
			{
				for (Object c : lowerLevel3) {
					Object[] dataC = (Object[]) c;
					deptList.put((Integer)dataC[0], dataC[1].toString());
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getDeptData of class "+getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String beforeDepartmentView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			editDeptData="false";
			setGridColomnNames();
			setDepartmentAndSubDeptNames();
			headerName=headerNameForDept+" >> View";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeDepartmentView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeDepartmentModify()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			editDeptData="true";
			setGridColomnNames();
			setDepartmentAndSubDeptNames();
			headerName=headerNameForDept+" >> Modify";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeDepartmentModify of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public void setDepartmentAndSubDeptNames()
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
				String namesofDepts[]=new String[3];
				StringBuilder query=new StringBuilder("");
						query.append("select levelName from "+"mapped_dept_level_config");
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(data!=null)
						{
							String names=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object obdata=(Object)it.next();
								if(obdata!=null)
									names=obdata.toString();
									
								
							}
							namesofDepts=names.split("#");
						}
							headerNameForDept=namesofDepts[0];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setGridColomnNames()
	{
		
		//id,deptname,orgnztnlevel,mappedOrgnztnId,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Registration Id");
		gpv.setHideOrShow("true");
		level1ColmnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
        gpv.setColomnName("country");
        gpv.setHeadingName("Country Office");
        gpv.setEditable("false");
        gpv.setSearch("false");
        gpv.setHideOrShow("false");
        gpv.setWidth(80);
        level1ColmnNames.add(gpv);
        
        gpv=new GridDataPropertyView();
        gpv.setColomnName("head");
        gpv.setHeadingName("Head Office");
        gpv.setEditable("false");
        gpv.setSearch("false");
        gpv.setHideOrShow("false");
        gpv.setWidth(80);
        level1ColmnNames.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setWidth(gdp.getWidth());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			level1ColmnNames.add(gpv);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public String beforeDepartmentViewHeader() 
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				groupMap=new LinkedHashMap<Integer, String>();
				deptMap=new LinkedHashMap<Integer, String>();
				
				String userType = (String)session.get("userTpe");
            	String query =null;
            	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
            	if (userType!=null && userType.equalsIgnoreCase("H")) 
            	{
            		hodStatus=true;
            		query = "SELECT id,name FROM branchoffice_data WHERE headOfficeId = '"+s[2]+"' ORDER BY name";
				} 
            	else if(userType!=null && userType.equalsIgnoreCase("M"))
            	{
            	    mgtStatus = true;
            	    hodStatus = true;
            		query = "SELECT id,name FROM country_data ORDER BY name";
            	}
            	else 
            	{
            		List data=cbt.executeAllSelectQuery("SELECT id ,groupName FROM groupinfo WHERE status='Active' AND regLevel="+s[1]+" ORDER BY groupName ASC", connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}
						}
					}
				}
				if (query!=null)
				{
					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
    				if (dataList != null && dataList.size() > 0)
    				{
    					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
    					{
    						Object[] object = (Object[]) iterator.next();
    						if (object[0] != null && object[1] != null)
    						{
    							deptMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
    						}
    					}
    				}
				}
				String userName = (String) session.get("empName");
				
				totalCount=new LinkedHashMap<String, String>();
				
				totalCount=getToatalContactsTypeCounters(userType,userName, connectionSpace);
				//totalGroupName=getContactsTypeCounters(connectionSpace);
				
				
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class "
								+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,String> getToatalContactsTypeCounters(String userType,String username,SessionFactory connectionSpace)
	{
		Map<String,String> counter=new LinkedHashMap<String, String>();
		
		StringBuilder builder=new StringBuilder("select count(*),dept.flag from department AS dept  LEFT join groupinfo as grp on grp.id=dept.groupId   ");
		int total =0;
		 
		builder.append(" LEFT join branchoffice_data as branch on grp.regLevel=branch.id  ");
		builder.append(" LEFT JOIN headoffice_data as head on head.id=branch.headOfficeId ");
		builder.append(" LEFT JOIN country_data as country on country.id=head.countryId ");
      	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
      	if (userType!=null && userType.equalsIgnoreCase("H")) 
      	{
      		builder.append(" WHERE head.id="+s[2] +"  ");
			} 
      	else if (userType!=null && userType.equalsIgnoreCase("N"))
      	{
      		builder.append(" WHERE  branch.id="+s[1]+" AND dept.userName='"+userName+"' ");
		}
		builder.append(" GROUP BY flag");
		System.out.println("Builder :::: "+builder.toString());
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0]!=null && object[1]!=null)
				{
					total=total+Integer.parseInt(object[0].toString());
					counter.put( object[1].toString(),object[0].toString());
				}
			}
			counter.put("Total Records", String.valueOf(total));
		}
		return counter;
	}
	
	
	public int getLevelOforganization() {
		return levelOforganization;
	}
	public void setLevelOforganization(int levelOforganization) {
		this.levelOforganization = levelOforganization;
	}
	
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getOrgIdInLevel() {
		return orgIdInLevel;
	}

	public void setOrgIdInLevel(String orgIdInLevel) {
		this.orgIdInLevel = orgIdInLevel;
	}

	public String getLevel2org() {
		return level2org;
	}

	public void setLevel2org(String level2org) {
		this.level2org = level2org;
	}

	public String getLevel3org() {
		return level3org;
	}

	public void setLevel3org(String level3org) {
		this.level3org = level3org;
	}

	public String getLevel4org() {
		return level4org;
	}

	public void setLevel4org(String level4org) {
		this.level4org = level4org;
	}

	public String getLevel5org() {
		return level5org;
	}

	public void setLevel5org(String level5org) {
		this.level5org = level5org;
	}

	public String getDeptName1() {
		return deptName1;
	}

	public void setDeptName1(String deptName1) {
		this.deptName1 = deptName1;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public String getSelectedorgId() {
		return selectedorgId;
	}

	public void setSelectedorgId(String selectedorgId) {
		this.selectedorgId = selectedorgId;
	}

	public Map<Integer, String> getLevelList() {
		return levelList;
	}

	public void setLevelList(Map<Integer, String> levelList) {
		this.levelList = levelList;
	}


	public String getHeaderName() {
		return headerName;
	}


	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}


	public String getEditDeptData() {
		return editDeptData;
	}


	public void setEditDeptData(String editDeptData) {
		this.editDeptData = editDeptData;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public List<GridDataPropertyView> getLevel1ColmnNames() {
		return level1ColmnNames;
	}


	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames) {
		this.level1ColmnNames = level1ColmnNames;
	}

	public List<ConfigurationUtilBean> getDepartmentDropDown() {
		return departmentDropDown;
	}
	public void setDepartmentDropDown(List<ConfigurationUtilBean> departmentDropDown) {
		this.departmentDropDown = departmentDropDown;
	}

	public List<ConfigurationUtilBean> getDepartmentTextBox() {
		return departmentTextBox;
	}

	public void setDepartmentTextBox(List<ConfigurationUtilBean> departmentTextBox) {
		this.departmentTextBox = departmentTextBox;
	}
	public Map<Integer, String> getOfficeMap() {
		return officeMap;
	}

	public void setOfficeMap(Map<Integer, String> officeMap) {
		this.officeMap = officeMap;
	}

	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
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

	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}



	public Map<String, String> getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Map<String, String> totalCount)
	{
		this.totalCount = totalCount;
	}

	public String getTotalGroupName() {
		return totalGroupName;
	}

	public void setTotalGroupName(String totalGroupName) {
		this.totalGroupName = totalGroupName;
	}

	public boolean isMgtStatus() {
		return mgtStatus;
	}

	public void setMgtStatus(boolean mgtStatus) {
		this.mgtStatus = mgtStatus;
	}

	public boolean isHodStatus() {
		return hodStatus;
	}

	public void setHodStatus(boolean hodStatus) {
		this.hodStatus = hodStatus;
	}
	
	
}