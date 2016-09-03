package com.Over2Cloud.ctrl.helpdesk.shiftconf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.floorsetting.FloorSetting;
import com.Over2Cloud.ctrl.floorsetting.FloorSettingHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;

@SuppressWarnings("serial")
public class ShiftMapAction extends GridPropertyBean 
{
	private Map<Integer, String> serviceDeptList = null;
	private String dataFor;
	private String moduleName;
	private String pageType;
	private String commonId;
	private String shifts;
	private String roomNo;
	private Map<String, String> commonMap = null;
	private Map<String, String> floorMap = null;

	@SuppressWarnings("rawtypes")
	public String beforeShiftMap()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				serviceDeptList = new LinkedHashMap<Integer, String>();
				if (dataFor != null && !dataFor.equals(""))
				{
					List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, dataFor, connectionSpace);
					if (departmentlist != null && departmentlist.size() > 0)
					{
						for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
						{
							Object[] object1 = (Object[]) iterator.next();
							serviceDeptList.put((Integer) object1[0], object1[1].toString());
						}
					}
					floorMap = new LinkedHashMap<String, String>();
					List tempList = new FloorSettingHelper().getFloor(connectionSpace, cbt, "Floor Detail");
					floorMap = new FloorSetting().getMapWith1And2ValueOfList(tempList);
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
	public String fetchMultiselectdata()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List data2 = null;
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				commonMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				StringBuilder query = new StringBuilder();
				if (dataFor != null && dataFor.equalsIgnoreCase("subdept"))
				{
					query.append("SELECT id,subdeptname FROM subdepartment WHERE deptid IN ( " + commonId + ") AND flag=0 order by subdeptname asc");
				}
				else if (dataFor != null && dataFor.equalsIgnoreCase("shift"))
				{
					query.append("SELECT id,shiftName FROM shift_conf WHERE dept_subdept IN ( " + commonId + ") AND moduleName='HDM' order by shiftName asc");
				}
				else if (dataFor != null && dataFor.equalsIgnoreCase("wing"))
				{
					query.append("SELECT id,wingsname FROM wings_detail WHERE floorname IN ( " + commonId + ")  order by wingsname asc");
				}
				else if (dataFor != null && dataFor.equalsIgnoreCase("room"))
				{
					query.append("SELECT id,roomno FROM floor_room_detail WHERE wingsname IN ( " + commonId + ")  order by roomno asc");
				}
				// System.out.println("QUERY IS AS :::::: "+query.toString());
				data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data2 != null && data2.size() > 0)
				{
					Object[] object = null;
					for (Iterator iterator = data2.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[1] != null && !object[1].toString().equalsIgnoreCase(""))
						{
							commonMap.put(object[0].toString(), object[1].toString());
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
			return LOGIN;
		}
	}

	public String mapShiftWithLocation()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				boolean colName = createTableLocation(cot);
				if (colName )
				{
					boolean status = false;
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
					
					if (shifts != null && roomNo != null)
					{
						String empArr[] = shifts.split(",");
						String empArr1[] = roomNo.split(",");
						for (int i = 0; i < empArr.length; i++)
						{
							if (!empArr[i].trim().equalsIgnoreCase(""))
							{
								ob = new InsertDataTable();
								ob.setColName("shift_id");
								ob.setDataName(empArr[i].trim());
								insertData.add(ob);
								for (int j = 0; j < empArr1.length; j++)
								{
									if (!empArr1[j].trim().equalsIgnoreCase(""))
									{
										ob = new InsertDataTable();
										ob.setColName("room_id");
										ob.setDataName(empArr1[j].trim());
										insertData.add(ob);
										String qry= "select * from shift_location_conf where shift_id='"+empArr[i].trim()+"' and room_id='"+empArr1[j].trim()+"'";
										List flag= cot.executeAllSelectQuery(qry, connectionSpace);
										if (flag.size()==0)
										{
											status = cot.insertIntoTable("shift_location_conf", insertData, connectionSpace);
										}
										insertData.remove(insertData.size() - 1);
									}
								}
								insertData.remove(insertData.size() - 1);
							}
						}
					}
					if (status)
					{
						addActionMessage("Shift Map with Location Successfully!");
					}
					else
					{
						addActionError("Oops there is some error!");
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	private boolean createTableLocation(CommonOperInterface cot)
	{
		List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
		TableColumes tc = new TableColumes();

		tc.setColumnname("shift_id");
		tc.setDatatype("varchar(100)");
		tc.setConstraint("default NULL");
		tablecolumn.add(tc);
		
		tc = new TableColumes();
		tc.setColumnname("room_id");
		tc.setDatatype("varchar(100)");
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

		boolean status=cot.createTable22("shift_location_conf", tablecolumn, connectionSpace);
		return status;
	}

	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getPageType()
	{
		return pageType;
	}

	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}

	public String getCommonId()
	{
		return commonId;
	}

	public void setCommonId(String commonId)
	{
		this.commonId = commonId;
	}

	public Map<String, String> getCommonMap()
	{
		return commonMap;
	}

	public void setCommonMap(Map<String, String> commonMap)
	{
		this.commonMap = commonMap;
	}

	public Map<String, String> getFloorMap()
	{
		return floorMap;
	}

	public void setFloorMap(Map<String, String> floorMap)
	{
		this.floorMap = floorMap;
	}

	public String getShifts()
	{
		return shifts;
	}

	public void setShifts(String shifts)
	{
		this.shifts = shifts;
	}

	public String getRoomNo()
	{
		return roomNo;
	}

	public void setRoomNo(String roomNo)
	{
		this.roomNo = roomNo;
	}
	
}
