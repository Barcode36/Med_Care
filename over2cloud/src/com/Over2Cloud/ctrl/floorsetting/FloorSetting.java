package com.Over2Cloud.ctrl.floorsetting;

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
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.compliance.complContacts.ComplianceContactsAction;

public class FloorSetting extends GridPropertyBean implements ServletRequestAware
{
	private String flag;
	private String headingName;
	private HttpServletRequest request;
	private String floorname;
	private String wingsname;
	private String floorcode;
	private String roomno;
	private String deptId,groupId,mappWith;
	
	private Map<String, String> floorMap;
	private Map<String, String> empMap;
	private String empId;
	private List<Object> masterViewList;
	private List<Object> masterViewList0;
	private List<Object> masterViewList1;
	private List<Object> masterViewList2;
	private EmpBasicPojoBean empObj = null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	
	public String CreateFloorSetting()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
				if(flag!=null)
				{
					if(flag.equalsIgnoreCase("CreateFloor"))
					{
						headingName="Floor Setting";
						List<GridDataPropertyView> buddyColumnList = Configuration.getConfigurationData("mapped_floor_setting_configuration", accountID, connectionSpace, "", 0, "table_name", "floor_setting_configuration");
						if(buddyColumnList!=null && buddyColumnList.size()>0)
						{
							ConfigurationUtilBean obj;
							for (GridDataPropertyView gdp : buddyColumnList)
							{
								if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
								{
									obj = new ConfigurationUtilBean();
									obj.setKey(gdp.getColomnName());
									obj.setValue(gdp.getHeadingName());
									obj.setValidationType(gdp.getValidationType());
									obj.setColType(gdp.getColType());
									if (gdp.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									pageFieldsColumns.add(obj);
								}
							}
						}
					}
					else if(flag.equalsIgnoreCase("CreateRoom"))
					{
						headingName="Room Setting";
						floorMap = new LinkedHashMap<String, String>();
						List tempList = new FloorSettingHelper().getFloor(connectionSpace,cbt,"Floor Detail");
						floorMap = getMapWith1And2ValueOfList(tempList);
						
						List<GridDataPropertyView> buddyColumnList = Configuration.getConfigurationData("mapped_floor_room_setting_configuration", accountID, connectionSpace, "", 0, "table_name", "floor_room_setting_configuration");
						if(buddyColumnList!=null && buddyColumnList.size()>0)
						{
							ConfigurationUtilBean obj;
							for (GridDataPropertyView gdp : buddyColumnList)
							{
								if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("extention") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
								{
									obj = new ConfigurationUtilBean();
									obj.setKey(gdp.getColomnName());
									obj.setValue(gdp.getHeadingName());
									obj.setValidationType(gdp.getValidationType());
									obj.setColType(gdp.getColType());
									if (gdp.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									pageFieldsColumns.add(obj);
								}
							}
						}
					}
					else if(flag.equalsIgnoreCase("CreateWings"))
					{
						headingName="Wings Setting";
						floorMap = new LinkedHashMap<String, String>();
						List tempList = new FloorSettingHelper().getFloor(connectionSpace,cbt,"Floor Detail");
						floorMap = getMapWith1And2ValueOfList(tempList);
						
						List<GridDataPropertyView> buddyColumnList = Configuration.getConfigurationData("mapped_wings_setting_configuration", accountID, connectionSpace, "", 0, "table_name", "wings_setting_configuration");
						if(buddyColumnList!=null && buddyColumnList.size()>0)
						{
							ConfigurationUtilBean obj;
							for (GridDataPropertyView gdp : buddyColumnList)
							{
								if (gdp.getColType().trim().equalsIgnoreCase("T"))
								{
									obj = new ConfigurationUtilBean();
									obj.setKey(gdp.getColomnName());
									obj.setValue(gdp.getHeadingName());
									obj.setValidationType(gdp.getValidationType());
									obj.setColType(gdp.getColType());
									if (gdp.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									pageFieldsColumns.add(obj);
								}
							}
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
	
	
	public Map<String, String> getMapWith1And2ValueOfList(List tempList)
	{
		Map<String, String> tempMap = new LinkedHashMap<String, String>();
		if(tempList!=null && tempList.size()>0)
		{
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					tempMap.put(object[0].toString(), object[1].toString());
				}
			}
		}
		return tempMap;
	}
	//function for go on upload.jsp page  
	  public String uploadExcel()
	  {
		 return "success";
	  }
	
	@SuppressWarnings("unchecked")
	public String addFloor()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_floor_setting_configuration", accountID, connectionSpace, "", 0, "table_name", "floor_setting_configuration");

				boolean status = false;
				if (statusColName != null && statusColName.size() > 0)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("floor_detail", tableColume, connectionSpace);
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					if(floorname!=null)
					{
						String[] floorNameArr = floorname.split(",");
						String[] floorCodeArr = floorcode.split(",");
						for (int i = 0; i < floorNameArr.length; i++) 
						{
							if(floorNameArr[i]!=null && !floorNameArr[i].equals(" "))
							{
								ob = new InsertDataTable();
								ob.setColName("floorname");
								ob.setDataName(floorNameArr[i].trim());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("floorcode");
								ob.setDataName(floorCodeArr[i].trim());
								insertData.add(ob);
								
								List dataList = cbt.executeAllSelectQuery("SELECT id FROM floor_detail WHERE floorname='"+floorNameArr[i].trim()+"' OR floorcode='"+floorCodeArr[i].trim()+"'", connectionSpace);
								if(dataList==null || dataList.size()==0)
									status=cbt.insertIntoTable("floor_detail", insertData, connectionSpace);
								
								insertData.remove(insertData.size()-2);
								insertData.remove(insertData.size()-1);
							}
						}
					}
				}
				if(status)
				{
					addActionMessage("Floor save sucessfully.");
					return SUCCESS;
				}
				else 
				{
					addActionMessage("There are some problem in save data.");
					return ERROR;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				addActionMessage("There are some problem in save data.");
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String addWings()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_wings_setting_configuration", accountID, connectionSpace, "", 0, "table_name", "wings_setting_configuration");

				boolean status = false;
				if (statusColName != null && statusColName.size() > 0)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("wings_detail", tableColume, connectionSpace);
					
					ob = new InsertDataTable();
					ob.setColName("floorname");
					ob.setDataName(floorname);
					insertData.add(ob);
					
					if(floorname!=null)
					{
						String[] wingsNameArr = wingsname.split(",");
						for (int i = 0; i < wingsNameArr.length; i++) 
						{
							if(wingsNameArr[i]!=null && !wingsNameArr[i].equals(" "))
							{
								ob = new InsertDataTable();
								ob.setColName("wingsname");
								ob.setDataName(wingsNameArr[i].trim());
								insertData.add(ob);
								
								status=cbt.insertIntoTable("wings_detail", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
							}
						}
					}
				}
				if(status)
				{
					addActionMessage("Wings save sucessfully.");
					return SUCCESS;
				}
				else 
				{
					addActionMessage("There are some problem in save data.");
					return ERROR;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				addActionMessage("There are some problem in save data.");
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String addRoom()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_floor_room_setting_configuration", accountID, connectionSpace, "", 0, "table_name", "floor_room_setting_configuration");

				boolean status = false;
				if (statusColName != null && statusColName.size() > 0)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("floor_room_detail", tableColume, connectionSpace);
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("floorname");
					ob.setDataName(floorname);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("wingsname");
					ob.setDataName(wingsname);
					insertData.add(ob);
					
					if(roomno!=null)
					{
						String[] roomNoWithExt = roomno.split(",");
						System.out.println(roomNoWithExt.length+" >>> roomNoWithExt");
						if(roomNoWithExt!=null && roomNoWithExt.length>0)
						{
							for(int i=0; i<roomNoWithExt.length;i++)
							{
								// if extension no enter with # separated 
								
								/*String[] roomNo=roomNoWithExt[i].split("#");
								System.out.println();
								
								ob = new InsertDataTable();
								ob.setColName("roomno");
								ob.setDataName(roomNo[0].trim());
								insertData.add(ob);
								
								if(roomNo.length>1)
								{
									ob = new InsertDataTable();
									ob.setColName("extention");
									ob.setDataName(roomNo[1].trim());
									insertData.add(ob);
								}
								
								
								List dataList = cbt.executeAllSelectQuery("SELECT id FROM floor_room_detail WHERE roomno='"+roomNo[0].trim()+"' AND floorname='"+floorname+"'", connectionSpace);
								if(dataList==null || dataList.size()==0)
								status=cbt.insertIntoTable("floor_room_detail", insertData, connectionSpace);
								
								insertData.remove(insertData.size()-2);
								insertData.remove(insertData.size()-1);*/
								
								
								// only room no with comma separated 
								ob = new InsertDataTable();
								ob.setColName("roomno");
								ob.setDataName(roomNoWithExt[i].trim());
								insertData.add(ob);
								
								
								List dataList = cbt.executeAllSelectQuery("SELECT id FROM floor_room_detail WHERE roomno='"+roomNoWithExt[i].trim()+"' AND floorname='"+floorname+"'", connectionSpace);
								if(dataList==null || dataList.size()==0)
								status=cbt.insertIntoTable("floor_room_detail", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
							}
						}
					}
				}
				addActionMessage("Room no & Ext. saved sucessfully");
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Opps. There are some problem in data save.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String CreateCCSetting()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				TableColumes ob1;
				
				ob1 = new TableColumes();
				ob1.setColumnname("mapped_emp_id");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("dept_id");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("module_name");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("status");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default 'Active'");
				tableColume.add(ob1);
				
				cbt.createTable22("deptwise_cc_emp_mapping", tableColume, connectionSpace);
				tableColume.clear();
				
				ob1 = new TableColumes();
				ob1.setColumnname("mapped_emp_id");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("room_id");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("module_name");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("status");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default 'Active'");
				tableColume.add(ob1);
				
				cbt.createTable22("roomwise_cc_emp_mapping", tableColume, connectionSpace);
				tableColume.clear();
				
				
				List tempList = new FloorSettingHelper().getFloor(connectionSpace,cbt,"Group Detail");
				floorMap = getMapWith1And2ValueOfList(tempList);
				
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
	
	public String getEmployee()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				empMap = new LinkedHashMap<String, String>();
				if(deptId!=null && !deptId.equalsIgnoreCase("undefined") && groupId!=null && !groupId.equalsIgnoreCase("undefined"))
				{
					List tempList = new FloorSettingHelper().getEmployee(connectionSpace,cbt,deptId,groupId);
					empMap = getMapWith1And2ValueOfList(tempList);
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
	
	public String BeforeGetUnMappedData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		String returnResult = "error";
		System.out.println("@@@@@ "+mappWith);
		if (sessionFlag)
		{
			try
			{
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				
				if(mappWith!=null && mappWith.equalsIgnoreCase("deptWise"))
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName("deptName");
					gpv.setHeadingName("Department");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(1260);
					viewPageColumns.add(gpv);
					
					returnResult="DeptWise";
					
				}
				else if(mappWith!=null && mappWith.equalsIgnoreCase("roomWise"))
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName("floorName");
					gpv.setHeadingName("Floor Name");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(250);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("floorCode");
					gpv.setHeadingName("Floor Code");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(300);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("roomno");
					gpv.setHeadingName("Room No");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(330);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("extention");
					gpv.setHeadingName("Extention");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(330);
					viewPageColumns.add(gpv);
					
					returnResult="FloorWise";
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				returnResult="error";
			}
		}
		else
		{
			returnResult="login";
		}
		return returnResult;
	}
	
	public String gridUnMappedDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				System.out.println(empId);
				if(empId!=null && empId!=null)
				{
					List<Object> Listhb=new ArrayList<Object>();
					List dataList = null;
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					StringBuilder query = new StringBuilder();
					query.append("SELECT DISTINCT dept.id,dept.deptName FROM department AS dept");
					query.append(" INNER JOIN subdepartment AS subdept ON subdept.deptid = dept.id");
					query.append(" INNER JOIN feedback_type AS ftype ON ftype.dept_subdept = subdept.id");
					query.append(" WHERE ftype.moduleName='HDM'");
					query.append(" AND dept.id NOT IN(SELECT dept_id FROM deptwise_cc_emp_mapping WHERE module_name='HDM' AND mapped_emp_id IN("+empId+"))");
					System.out.println("####### "+query.toString());
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							Map<String, Object> one = new HashMap<String, Object>();
							one.put("id", CUA.getValueWithNullCheck(object[0]));
							one.put("deptName", CUA.getValueWithNullCheck(object[1]));
							
							Listhb.add(one);
						}
					}
					setMasterViewList(Listhb);
				}
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	public String gridUnMappedRoom()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				System.out.println(empId);
				if(empId!=null && empId!=null)
				{
					List<Object> Listhb=new ArrayList<Object>();
					List dataList = null;
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					StringBuilder query = new StringBuilder();
					query.append("SELECT DISTINCT room.id,floor.floorname,floor.floorcode,room.roomno,room.extention");
					query.append(" FROM floor_room_detail AS room");
					query.append(" INNER JOIN floor_detail AS floor ON floor.id = room.floorname");
					query.append(" WHERE room.id NOT IN(SELECT room_id FROM roomwise_cc_emp_mapping WHERE mapped_emp_id IN("+empId+"))");
					System.out.println("####### "+query.toString());
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							Map<String, Object> one = new HashMap<String, Object>();
							one.put("id", CUA.getValueWithNullCheck(object[0]));
							one.put("floorName", CUA.getValueWithNullCheck(object[1]));
							one.put("floorCode", CUA.getValueWithNullCheck(object[2]));
							one.put("roomno", CUA.getValueWithNullCheck(object[3]));
							one.put("extention", CUA.getValueWithNullCheck(object[4]));
							Listhb.add(one);
						}
					}
					setMasterViewList(Listhb);
				}
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	public String mapDeptCC()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				
				if(deptId!=null)
				{
					String[] deptIdArr = deptId.split(",");
					String[] empIdArr = empId.split(",");
					for (int i = 0; i < deptIdArr.length; i++) 
					{
						if(deptIdArr[i]!=null && !deptIdArr[i].equals(" "))
						{
							ob = new InsertDataTable();
							ob.setColName("dept_id");
							ob.setDataName(deptIdArr[i].trim());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("module_name");
							ob.setDataName("HDM");
							insertData.add(ob);
							
							for(int j=0; j < empIdArr.length; j++)
							{
								if(empIdArr[j]!=null && !empIdArr[j].equals("-1") && !empIdArr[j].equals(" "))
								{
									ob = new InsertDataTable();
									ob.setColName("mapped_emp_id");
									ob.setDataName(empIdArr[j].trim());
									insertData.add(ob);
									
									cbt.insertIntoTable("deptwise_cc_emp_mapping", insertData, connectionSpace);
									insertData.remove(insertData.size()-1);
								}
							}
							insertData.clear();
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
	
	public String mapRoomCC()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				
				if(roomno!=null)
				{
					String[] roomNoArr = roomno.split(",");
					String[] empIdArr = empId.split(",");
					for (int i = 0; i < roomNoArr.length; i++) 
					{
						if(roomNoArr[i]!=null && !roomNoArr[i].equals(" "))
						{
							ob = new InsertDataTable();
							ob.setColName("room_id");
							ob.setDataName(roomNoArr[i].trim());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("module_name");
							ob.setDataName("HDM");
							insertData.add(ob);
							
							for(int j=0; j < empIdArr.length; j++)
							{
								if(empIdArr[j]!=null && !empIdArr[j].equals("-1") && !empIdArr[j].equals(" "))
								{
									ob = new InsertDataTable();
									ob.setColName("mapped_emp_id");
									ob.setDataName(empIdArr[j].trim());
									insertData.add(ob);
									
									cbt.insertIntoTable("roomwise_cc_emp_mapping", insertData, connectionSpace);
									insertData.remove(insertData.size()-1);
								}
							}
							insertData.clear();
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
	
	public String gridMappedFloor1()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT id,floorname,floorcode FROM floor_detail ORDER BY floorname");
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
							one.put("gridFloorName",object[1].toString());
						else 
							one.put("gridFloorName","NA");
						
						if(object[2]!=null)
							one.put("gridFloorCode",object[2].toString());
						else 
							one.put("gridFloorCode","NA");
						
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
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
	
	public String gridMappedWings()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				masterViewList0 = new ArrayList<Object>();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT id,wingsname FROM wings_detail WHERE floorname="+id+" ORDER BY wingsname ASC ");
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
							one.put("gridWings",object[1].toString());
						else 
							one.put("roomNo","NA");
						
						Listhb.add(one);
					}
					setMasterViewList0(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
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
	
	public String gridMappedRoom2()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT id,roomno,extention FROM floor_room_detail WHERE wingsname="+id+" ORDER BY roomno");
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
							one.put("roomNo",object[1].toString());
						else 
							one.put("roomNo","NA");
						
						if(object[2]!=null)
							one.put("extention",object[2].toString());
						else 
							one.put("extention","NA");
						
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
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public String gridMappedEmp3()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT room.id,emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName");
				query.append(" FROM roomwise_cc_emp_mapping AS room");
				query.append(" INNER JOIN employee_basic AS emp ON room.mapped_emp_id=emp.id");
				query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname");
				query.append(" WHERE room.room_id="+id+" ORDER BY emp.empName");
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
							one.put("empName",object[1].toString());
						else 
							one.put("empName","NA");
						
						if(object[1]!=null)
							one.put("mobileNo",object[2].toString());
						else 
							one.put("mobileNo","NA");
						
						if(object[1]!=null)
							one.put("emailid",object[3].toString());
						else 
							one.put("emailid","NA");
						
						if(object[2]!=null)
							one.put("department",object[4].toString());
						else 
							one.put("department","NA");
						
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
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	

	public String demo()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
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
	
	// Method for getting Sub Loc Details for Sub Location
		@SuppressWarnings("unchecked")
		public String getsubLocationDetails()
		{
			String returnResult = ERROR;
			try
			{
				System.out.println("Id Value is   " + id);
				empObj = new EmpBasicPojoBean();
				List subLocationList = new FloorSettingHelper().getBudySubLocDetails(connectionSpace,id);
				if (subLocationList != null && subLocationList.size() > 0)
				{
					for (Iterator iterator = subLocationList.iterator(); iterator.hasNext();)
					{
						Object[] objectCol = (Object[]) iterator.next();

						// Set the value of Intercome No
						if (objectCol[0] == null || objectCol[0].toString().equals(""))
						{
							empObj.setOther2("NA");
						}
						else
						{
							empObj.setOther2(objectCol[0].toString());
						}

						// Set the value of Location
						if (objectCol[1] != null || !objectCol[1].toString().equals("") || objectCol[2] != null || !objectCol[2].toString().equals(""))
						{
							empObj.setOther1(objectCol[2].toString() + " - " + objectCol[1].toString());
						}
						else
						{
							empObj.setOther1("NA");
						}
					}
				}

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return returnResult;
		}
	
		@SuppressWarnings("unchecked")
		public String getsubLocationFloorDetails()
		{
			String returnResult = ERROR;
			try
			{
				empObj = new EmpBasicPojoBean();
				List subLocationList = new FloorSettingHelper().getBudySubLocFloorDetails(connectionSpace,id);
				if (subLocationList != null && subLocationList.size() > 0)
				{
					for (Iterator iterator = subLocationList.iterator(); iterator.hasNext();)
					{
						Object[] objectCol = (Object[]) iterator.next();

						// Set the value of Intercome No
						if (objectCol[0] == null || objectCol[0].toString().equals(""))
						{
							empObj.setOther2("NA");
						}
						else
						{
							empObj.setOther2(objectCol[0].toString());
						}
						if (objectCol[3] == null || objectCol[3].toString().equals(""))
						{
							empObj.setOther3("NA");
						}
						else
						{
							empObj.setOther3(objectCol[3].toString());
						}
						if (objectCol[4] == null || objectCol[4].toString().equals(""))
						{
							empObj.setOther4("NA");
						}
						else
						{
							empObj.setOther4(objectCol[4].toString());
						}
						if (objectCol[5] == null || objectCol[5].toString().equals(""))
						{
							empObj.setOther5("NA");
						}
						else
						{
							empObj.setOther5(objectCol[5].toString());
						}
						// Set the value of Location
						if (objectCol[1] != null || !objectCol[1].toString().equals("") || objectCol[2] != null || !objectCol[2].toString().equals(""))
						{
							empObj.setOther1(objectCol[2].toString() + " - " + objectCol[1].toString());
						}
						else
						{
							empObj.setOther1("NA");
						}
					}
				}

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return returnResult;
		}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
		
	}
	public String getFlag()
	{
		return flag;
	}
	public void setFlag(String flag)
	{
		this.flag = flag;
	}
	public String getHeadingName()
	{
		return headingName;
	}
	public void setHeadingName(String headingName)
	{
		this.headingName = headingName;
	}
	public String getFloorname()
	{
		return floorname;
	}
	public void setFloorname(String floorname)
	{
		this.floorname = floorname;
	}
	public String getFloorcode()
	{
		return floorcode;
	}
	public void setFloorcode(String floorcode)
	{
		this.floorcode = floorcode;
	}
	public Map<String, String> getFloorMap()
	{
		return floorMap;
	}
	public void setFloorMap(Map<String, String> floorMap)
	{
		this.floorMap = floorMap;
	}
	public String getRoomno()
	{
		return roomno;
	}
	public void setRoomno(String roomno)
	{
		this.roomno = roomno;
	}
	public String getDeptId()
	{
		return deptId;
	}
	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}
	public String getGroupId()
	{
		return groupId;
	}
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getMappWith()
	{
		return mappWith;
	}

	public void setMappWith(String mappWith)
	{
		this.mappWith = mappWith;
	}
	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}


	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}


	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}


	public EmpBasicPojoBean getEmpObj()
	{
		return empObj;
	}


	public void setEmpObj(EmpBasicPojoBean empObj)
	{
		this.empObj = empObj;
	}


	public List<Object> getMasterViewList1()
	{
		return masterViewList1;
	}


	public void setMasterViewList1(List<Object> masterViewList1)
	{
		this.masterViewList1 = masterViewList1;
	}


	public List<Object> getMasterViewList2()
	{
		return masterViewList2;
	}


	public void setMasterViewList2(List<Object> masterViewList2)
	{
		this.masterViewList2 = masterViewList2;
	}


	public String getWingsname() {
		return wingsname;
	}


	public void setWingsname(String wingsname) {
		this.wingsname = wingsname;
	}


	public List<Object> getMasterViewList0() {
		return masterViewList0;
	}


	public void setMasterViewList0(List<Object> masterViewList0) {
		this.masterViewList0 = masterViewList0;
	}
	
	

}
