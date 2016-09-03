package com.Over2Cloud.ctrl.patientcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.opensymphony.xwork2.ActionContext;

public class CpsEmpMapResource extends GridPropertyBean implements ServletRequestAware
{
	/**
	 * 
	 * author: Sanjay :18:01:2016
	 */
	private static final long serialVersionUID = -192253594566464362L;
	private HttpServletRequest request;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	// String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private JSONArray jsonList;
	private String searchFields;
	private String SearchValue;
	private String deptFlag;
	private String deptId;
	private String trashEmpId;
	private String shiftId;
	private String wingIds;
	private String viewFlag;
	private String shiftEmpMapSubdept;
	private String empId;
	private String shiftID;
	private String empMapSubdept;
	private String shift_name;
	private String fromShift;
	private String toShift;
	private String fromTime;
	private String toTime;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private JSONArray commonJSONArray = new JSONArray();

	// get list for dept
	public String getAllDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try
			{
				jsonList = new JSONArray();
				if (deptFlag != null && deptFlag.equalsIgnoreCase("dept"))
				{
					List departmentlist = getServiceDept_SubDept("CPS");
					if (departmentlist != null && departmentlist.size() > 0)
					{
						for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{

								JSONObject innerobj = new JSONObject();
								innerobj.put("id", object[0]);
								innerobj.put("dept", object[1].toString());
								jsonList.add(innerobj);
							}
						}
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}

	}

	public List getServiceDept_SubDept(String module)
	{

		StringBuilder qry = new StringBuilder();
		List dataList = null;
		try
		{
			if (module.equalsIgnoreCase("CPS"))
			{
				// System.out.println("In CPS");
				qry.append("SELECT DISTINCT dept.id, dept.deptName FROM department AS dept");
				qry.append(" INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id");
				qry.append(" INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id");
				qry.append(" WHERE cc.moduleName='" + module + "'");
			}

			else
			{
				qry.append(" select distinct dept.id,dept.deptName from department as dept");
				qry.append("  ORDER BY dept.deptName ASC");
			}
			dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);

		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return dataList;

	}

	// get list of Emp

	public String getEmployeeByDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append("SELECT emp.id, emp.empName,contact.level,emp.empLogo,subdept.subdeptname FROM compliance_contacts AS contact");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = contact.forDept_id");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
				query.append(" WHERE contact.moduleName='CPS' AND  contact.forDept_id IN(SELECT id from subdepartment where deptid='" + getDeptId() + "') ");
				if (deptId != null && !deptId.equalsIgnoreCase("8"))
					query.append(" And contact.level!='1' ");
				if (searchString != null && !searchString.equalsIgnoreCase(""))
				{
					query.append(" and (emp.empName like '%" + searchString + "%' OR contact.level like '%" + searchString + "%' OR subdept.subdeptname like '%" + searchString + "%' )");
				}
				query.append(" group by emp.id");
				// System.out.println("Shift Wise List For employee qry::"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[3] != null)
							{
								innerobj.put("logo", object[3].toString());
							} else
							{
								innerobj.put("logo", "images/noImage.JPG");
							}
							jsonList.add(innerobj);
						}
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getSkillDetails()
	{

		if (ValidateSession.checkSession())
		{
			try
			{
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append(" select subdept.subdeptname,contacts.level from compliance_contacts as contacts ");
				query.append(" INNER JOIN subdepartment as subdept on subdept.id = contacts.forDept_id ");
				query.append(" INNER JOIN employee_basic as emp on emp.id = contacts.forDept_id ");
				query.append(" where contacts.emp_id='" + trashEmpId + "' and contacts.moduleName='CPS' and contacts.work_status='0'");
				// System.out.println("Qery Skill"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("skill", object[0]);
							innerobj.put("level", "Level-" + object[1].toString());
							jsonList.add(innerobj);
						}
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getDepartmentMapDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonList = new JSONArray();
				Map<String, String> innerobj1;
				List tempList;
				Map<String, List> innerobj;
				if (deptId != null && !deptId.equalsIgnoreCase("OCC"))
				{
					StringBuffer query1 = new StringBuffer("SELECT  id, service_name FROM cps_service order by service_name");
					List dataList1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					// System.out.println(query1.toString());

					if (dataList1 != null && dataList1.size() > 0)
					{
						for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
						{
							query1.delete(0, query1.length());
							innerobj = new HashMap<String, List>();
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{

								query1.append("SELECT  id, serv_loc FROM cps_service ");
								query1.append(" WHERE service_name LIKE '%" + object[1].toString() + "%' ");
								// System.out.println(query1.toString());
								List dataList2 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
								int i = 0;
								String location_id = null;
								if (dataList2 != null && dataList2.size() > 0)
								{
									tempList = new ArrayList();
									for (Iterator iterator1 = dataList2.iterator(); iterator1.hasNext();)
									{

										Object[] object2 = (Object[]) iterator1.next();
										if (object2[0] != null && object2[1] != null)
										{
											String location[] = object2[1].toString().split("#");
											// System.out.println(location.length);
											for (int a = 0; a < location.length; a++)
											{
												query1.delete(0, query1.length());
												query1.append("SELECT  id,location_name FROM cps_location where location_name like '%" + location[a] + "%' ");
												List dataList = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

												if (dataList != null && dataList.size() > 0)
												{
													for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
													{
														Object[] object3 = (Object[]) iterator2.next();
														if (object3[0] != null && object3[1] != null)
														{
															location_id = object3[0].toString();
														}
													}
												}

												innerobj1 = new HashMap<String, String>();
												innerobj1.put("wingid", object2[0].toString() + "_" + location_id);
												innerobj1.put("wingname", location[a]);
												tempList.add(innerobj1);
											}

										}
									}

									innerobj.put(object[1].toString(), tempList);
									jsonList.add(innerobj);
								}
							}
						}
					}
				}
				if (deptId != null && deptId.equalsIgnoreCase("OCC"))
				{
					innerobj1 = new HashMap<String, String>();
					innerobj1.put("wingid", "OCC");
					innerobj1.put("wingname", "OCC");
					tempList = new ArrayList();
					tempList.add(innerobj1);
					innerobj = new HashMap<String, List>();
					innerobj.put("OCC", tempList);
					jsonList.add(innerobj);

				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getMappedEmployee()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			String wings[] = wingIds.substring(0, wingIds.length() - 1).split(",");
			try
			{
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append("SELECT emp.id as empid,emp.empName,cc.level,sudept.subdeptname,shift.shiftName,shift.fromShift,shift.toShift ");
				query.append(",ewm.cps_service_id,shift.id");
				query.append(" from compliance_contacts as cc ");
				query.append(" INNER JOIN cps_emp_subdept_mapping as ewm on ewm.empName=cc.emp_id");
				query.append(" INNER JOIN employee_basic as emp on cc.emp_id=emp.id  ");
				query.append(" INNER JOIN subdepartment as sudept on sudept.id=cc.forDept_id");
				query.append(" INNER JOIN cps_shift_with_emp_mapping as shift on shift.id=ewm.shiftId");
				if (viewFlag != null && viewFlag.equalsIgnoreCase("T"))
				{

				} else
				{
					query.append(" where shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "'");
				}
				if (wingIds != null && !wingIds.equalsIgnoreCase(""))
				{
					query.append(" and ewm.cps_service_id IN(");
					for (int i = 0; i < wings.length - 2; i++)
					{
						query.append("'" + wings[i] + "',");
					}
					query.append("'" + wings[wings.length - 1] + "')");
				}

				query.append(" and shift.dept_id IN(" + deptId + ")");
				if (shiftId != null && !shiftId.equalsIgnoreCase(""))
				{
					query.append(" and shift.id='" + shiftId + "'");
				}
				query.append(" group by emp.id,ewm.cps_service_id ");
				// System.out.println(wingIds+"          "+wingIds.length()+"         "+
				// (wingIds.length()-1 )+"     Qyer: " + query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[2] != null && object[3] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[4] != null)
							{
								innerobj.put("shiftName", object[4].toString());
							} else
							{
								innerobj.put("shiftName", "NA");
							}
							if (object[5] != null)
							{
								innerobj.put("shiftFrom", object[5].toString());
							} else
							{
								innerobj.put("shiftFrom", "NA");
							}
							if (object[6] != null)
							{
								innerobj.put("shiftTo", object[6].toString());
							} else
							{
								innerobj.put("shiftTo", "NA");
							}
							if (object[8] != null)
							{
								innerobj.put("shiftid", object[8].toString());
							}
							if (object[7] != null)
							{
								innerobj.put("wingid", object[7].toString());
							}

							jsonList.add(innerobj);
						}
					}
				} else
				{
					query.delete(0, query.length());
					query.append(" SELECT shift.shiftName,shift.fromShift,shift.toShift,shift.id from cps_shift_with_emp_mapping as shift ");
					if (viewFlag != null && viewFlag.equalsIgnoreCase("T"))
					{
						query.append(" where ");
					} else
					{
						query.append(" where shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "' and");
					}

					if (shiftId != null && !shiftId.equalsIgnoreCase(""))
					{
						query.append("  shift.id='" + shiftId + "' and ");
					}
					query.append(" shift.dept_id IN(" + deptId + ")");
					query.append(" limit 1");
					List dataList1 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						JSONObject innerobj = new JSONObject();
						if (object[0] != null)
						{
							innerobj.put("shiftName", object[0].toString());
						}
						if (object[1] != null)
						{
							innerobj.put("shiftFrom", object[1].toString());
						}
						if (object[2] != null)
						{
							innerobj.put("shiftTo", object[2].toString());
						}
						if (object[3] != null)
						{
							innerobj.put("shiftid", object[3].toString());
						}

						jsonList.add(innerobj);
					}

				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String deleteEmpByTrashMethod()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String[] trash = trashEmpId.split(",");
				if (trash.length > 1)
				{
					String[] temp = trashEmpId.split(",");
					@SuppressWarnings("rawtypes")
					HashMap<String, String> emp = new HashMap<String, String>();
					for (int i = 0; i < temp.length; i++)
					{
						emp.put(temp[i].split("#")[1], temp[i].split("#")[0]);
					}

					if (emp.size() > 0)
					{
						StringBuilder query = new StringBuilder();
						Set set = emp.entrySet();
						Iterator i = set.iterator();

						while (i.hasNext())
						{
							query.delete(0, query.length());
							Map.Entry me = (Map.Entry) i.next();
							query.append("Delete from  cps_emp_subdept_mapping where empName='" + (String) me.getValue() + "'");
							query.append(" and cps_service_id='" + me.getKey() + "'");
							query.append(" and shiftId='" + shiftId + "'");
							cbt.executeAllUpdateQuery(query.toString(), connectionSpace);

						}
					}

				} else
				{
					if (trashEmpId.contains("#"))
					{
						trashEmpId = trashEmpId.split("#")[0];
					}
					if (trashEmpId != null && !trashEmpId.equalsIgnoreCase(""))
					{
						StringBuilder query = new StringBuilder();
						query.append("Delete from  cps_emp_subdept_mapping where empName='" + getTrashEmpId() + "'");
						if (wingIds != null && !wingIds.equalsIgnoreCase(""))
						{
							query.append(" and cps_service_id='" + wingIds + "'");
						}
						query.append(" and shiftId='" + shiftId + "'");
						cbt.executeAllUpdateQuery(query.toString(), connectionSpace);

					}
				}

				addActionMessage("Employee Unmapped Successfully!!!");
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String saveEmpShiftMapping()
	{
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession())
		{
			try
			{
				// system.out.println("id "+shiftemployeemappedwing);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String deleteShift()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (shiftId != null && !shiftId.equalsIgnoreCase(""))
				{

					StringBuilder query = new StringBuilder();
					query.append(" Select id from cps_shift_with_emp_mapping ");
					query.append(" where fromShift<='" + DateUtil.getCurrentTime() + "' and toShift >'" + DateUtil.getCurrentTime() + "'");
					query.append(" and dept_id='" + deptId + "'");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						Object obj = data.get(0);

						if (!obj.toString().equalsIgnoreCase(shiftId))
						{
							query.delete(0, query.length());
							query.append("Delete from  cps_emp_subdept_mapping where shiftId='" + getShiftId() + "'");
							int result = cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
							if (viewFlag == null)
							{
								StringBuilder query1 = new StringBuilder();
								query1.append("Delete from  cps_shift_with_emp_mapping where id='" + getShiftId() + "'");
								cbt.executeAllUpdateQuery(query1.toString(), connectionSpace);
							}
							addActionMessage("Shift Deleted Successfully!!!");
							return SUCCESS;

						} else
						{
							addActionMessage("Sorry!! You can not delete an active shift.");
							return SUCCESS;
						}
					} else
					{
						query.delete(0, query.length());
						query.append("Delete from  cps_emp_subdept_mapping where shiftId='" + getShiftId() + "'");
						int result = cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
						if (viewFlag == null)
						{
							StringBuilder query1 = new StringBuilder();
							query1.append("Delete from  cps_shift_with_emp_mapping where id='" + getShiftId() + "'");
							cbt.executeAllUpdateQuery(query1.toString(), connectionSpace);
						}
						if (viewFlag == null)
						{
							addActionMessage("Shift Deleted Successfully!!!");
						} else
						{
							addActionMessage("Shift Cleared Successfully!!!");
						}

						return SUCCESS;
					}

				}

			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
		addActionMessage("Some problem in deleting data!!");
		return SUCCESS;
	}

	public String viewShift()
	{
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession())
		{
			try
			{
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append(" select id,shiftName,fromShift,toShift from cps_shift_with_emp_mapping where dept_id='" + getDeptId() + "'");
				if (viewFlag != null && viewFlag.equalsIgnoreCase("live"))
				{
					query.append(" order by id desc ");
				}
				// System.out.println("veiw Sift::"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[2] != null && object[3] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("shiftName", object[1].toString());
							innerobj.put("to", object[3].toString());
							innerobj.put("from", object[2].toString());

							jsonList.add(innerobj);
						}
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String addShiftWithSubdeptMap()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = createShiftTable(cbt);
				if (status)
				{
					ob = new InsertDataTable();
					ob.setColName("shiftName");
					ob.setDataName(shift_name);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("fromShift");
					ob.setDataName(fromShift);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("toShift");
					ob.setDataName(toShift);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("dept_id");
					ob.setDataName(deptId);
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					int shiftID = cbt.insertDataReturnId("cps_shift_with_emp_mapping", insertData, connectionSpace);
					insertData.clear();

					if (shiftID != 0)
					{
						ob = new InsertDataTable();
						ob.setColName("shiftId");
						ob.setDataName(shiftID);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);

						if (shiftEmpMapSubdept != null && !shiftEmpMapSubdept.equalsIgnoreCase(""))
						{
							String ab[] = shiftEmpMapSubdept.split(",");
							if (ab != null && !ab.equals(""))
							{
								for (int i = 0; i < ab.length; i++)
								{
									String abc[] = ab[i].split("#")[1].split("_");
									ob = new InsertDataTable();
									ob.setColName("empName");
									ob.setDataName(ab[i].split("#")[0]);
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("cps_service_id");
									ob.setDataName(ab[i].split("#")[1]);
									insertData.add(ob);

									if (abc.length >= 2)
									{
										ob = new InsertDataTable();
										ob.setColName("cps_location_id");
										ob.setDataName(abc[1]);
										insertData.add(ob);
									} else
									{
										ob = new InsertDataTable();
										ob.setColName("cps_location_id");
										ob.setDataName("All");
										insertData.add(ob);
									}

									cbt.insertIntoTable("cps_emp_subdept_mapping", insertData, connectionSpace);
									insertData.remove(insertData.size() - 1);
									insertData.remove(insertData.size() - 1);
									insertData.remove(insertData.size() - 1);
								}
							}
						}
						addActionMessage("Data saved sucessfully.");
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String addShiftWithSubdeptMapSave()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = createShiftTable(cbt);
				if (status)
				{
					ob = new InsertDataTable();
					ob.setColName("shiftId");
					ob.setDataName(shiftID);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					if (shiftEmpMapSubdept != null && !shiftEmpMapSubdept.equalsIgnoreCase(""))
					{
						String ab[] = shiftEmpMapSubdept.split(",");

						if (ab != null && !ab.equals(""))
						{
							for (int i = 0; i < ab.length; i++)
							{
								String abc[] = ab[i].split("#")[1].split("_");
								ob = new InsertDataTable();
								ob.setColName("empName");
								ob.setDataName(ab[i].split("#")[0]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("cps_service_id");
								ob.setDataName(ab[i].split("#")[1]);
								insertData.add(ob);

								if (abc.length >= 2)
								{
									ob = new InsertDataTable();
									ob.setColName("cps_location_id");
									ob.setDataName(abc[1]);
									insertData.add(ob);
								} else
								{
									ob = new InsertDataTable();
									ob.setColName("cps_location_id");
									ob.setDataName("All");
									insertData.add(ob);
								}
								cbt.insertIntoTable("cps_emp_subdept_mapping", insertData, connectionSpace);
								insertData.remove(insertData.size() - 1);
								insertData.remove(insertData.size() - 1);
								insertData.remove(insertData.size() - 1);
							}
						}
					} else if (empMapSubdept != null && !empMapSubdept.equalsIgnoreCase(""))
					{
						String ab[] = empMapSubdept.split(",");
						if (ab != null && !ab.equals(""))
						{
							for (int i = 0; i < ab.length; i++)
							{
								String abc[] = ab[i].split("#")[1].split("_");
								ob = new InsertDataTable();
								ob.setColName("empName");
								ob.setDataName(empId);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("cps_service_id");
								ob.setDataName(ab[i].split("#")[1]);
								insertData.add(ob);

								if (abc.length >= 2)
								{
									ob = new InsertDataTable();
									ob.setColName("cps_location_id");
									ob.setDataName(abc[1]);
									insertData.add(ob);
								} else
								{
									ob = new InsertDataTable();
									ob.setColName("cps_location_id");
									ob.setDataName("All");
									insertData.add(ob);
								}

								cbt.insertIntoTable("cps_emp_subdept_mapping", insertData, connectionSpace);
								insertData.remove(insertData.size() - 1);
								insertData.remove(insertData.size() - 1);
								insertData.remove(insertData.size() - 1);
							}
						}
					}
					addActionMessage("Data saved sucessfully.");
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	private boolean createShiftTable(CommonOperInterface cbt)
	{
		boolean flag = false;
		List<TableColumes> tableColume = new ArrayList<TableColumes>();

		TableColumes ob1 = new TableColumes();

		ob1 = new TableColumes();
		ob1.setColumnname("shiftName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("fromShift");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("toShift");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("dept_id");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("userName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("date");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		flag = cbt.createTable22("cps_shift_with_emp_mapping", tableColume, connectionSpace);
		tableColume.clear();

		ob1 = new TableColumes();
		ob1.setColumnname("empName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("cps_service_id");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("shiftId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("userName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("date");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);

		cbt.createTable22("cps_emp_subdept_mapping", tableColume, connectionSpace);

		return flag;
	}

	public String saveEmpSubdept()
	{
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession())
		{
			try
			{
				// system.out.println("id "+shiftemployeemappedwing);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String editShiftTime()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (shiftId != null && !shiftId.equalsIgnoreCase(""))
				{
					StringBuilder query = new StringBuilder();
					query.append("UPDATE cps_shift_with_emp_mapping set fromShift='" + fromTime + "',toShift='" + toTime + "' where id='" + getShiftId() + "'");
					cbt.executeAllUpdateQuery(query.toString(), connectionSpace);

				}
				addActionMessage("Shift Updated Successfully!!!");
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		} else
		{
			return LOGIN;
		}
	}

	// **************************Greater Setter
	// *****************************************//
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getSearchFields()
	{
		return searchFields;
	}

	public void setSearchFields(String searchFields)
	{
		this.searchFields = searchFields;
	}

	public String getSearchValue()
	{
		return SearchValue;
	}

	public void setSearchValue(String searchValue)
	{
		SearchValue = searchValue;
	}

	public void setJsonList(JSONArray jsonList)
	{
		this.jsonList = jsonList;
	}

	public JSONArray getJsonList()
	{
		return jsonList;
	}

	public String getDeptFlag()
	{
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag)
	{
		this.deptFlag = deptFlag;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public String getTrashEmpId()
	{
		return trashEmpId;
	}

	public void setTrashEmpId(String trashEmpId)
	{
		this.trashEmpId = trashEmpId;
	}

	public String getShiftId()
	{
		return shiftId;
	}

	public void setShiftId(String shiftId)
	{
		this.shiftId = shiftId;
	}

	public String getWingIds()
	{
		return wingIds;
	}

	public void setWingIds(String wingIds)
	{
		this.wingIds = wingIds;
	}

	public String getViewFlag()
	{
		return viewFlag;
	}

	public void setViewFlag(String viewFlag)
	{
		this.viewFlag = viewFlag;
	}

	public String getShiftEmpMapSubdept()
	{
		return shiftEmpMapSubdept;
	}

	public void setShiftEmpMapSubdept(String shiftEmpMapSubdept)
	{
		this.shiftEmpMapSubdept = shiftEmpMapSubdept;
	}

	public String getShiftID()
	{
		return shiftID;
	}

	public void setShiftID(String shiftID)
	{
		this.shiftID = shiftID;
	}

	public String getEmpMapSubdept()
	{
		return empMapSubdept;
	}

	public void setEmpMapSubdept(String empMapSubdept)
	{
		this.empMapSubdept = empMapSubdept;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getShift_name()
	{
		return shift_name;
	}

	public void setShift_name(String shiftName)
	{
		shift_name = shiftName;
	}

	public String getFromShift()
	{
		return fromShift;
	}

	public void setFromShift(String fromShift)
	{
		this.fromShift = fromShift;
	}

	public String getToShift()
	{
		return toShift;
	}

	public void setToShift(String toShift)
	{
		this.toShift = toShift;
	}

	public String getFromTime()
	{
		return fromTime;
	}

	public void setFromTime(String fromTime)
	{
		this.fromTime = fromTime;
	}

	public String getToTime()
	{
		return toTime;
	}

	public void setToTime(String toTime)
	{
		this.toTime = toTime;
	}

}