package com.Over2Cloud.ctrl.LongPatientStay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;

public class FloorMapping extends GridPropertyBean {
	private JSONArray jsonList;
	private String deptFlag;
	private String deptId;
	private String status;
	private String wingName;
	private String floorName;
	private String trashEmpId;
	private String shiftemployeemappedwing;
	private String shiftId;
	private String wingIds;
	private String viewFlag;
	private String module;
	private String fromTime, toTime;
	private String shift_name;
	private String fromShift;
	private String toShift;
	private String shiftID;
	private String mployeemappedwing,empId;
	CommonOperInterface cbt = new CommonConFactory().createInterface();

	public String getAlldept() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try {
				jsonList = new JSONArray();
				//HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();

				if (deptFlag != null && deptFlag.equalsIgnoreCase("dept")) {
					List departmentlist = fetchServiceDept_SubDept(deptLevel,
							orgLevel, orgId, "LPS", connectionSpace);
					if (departmentlist != null && departmentlist.size() > 0) {
						for (Iterator iterator = departmentlist.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null) {

								JSONObject innerobj = new JSONObject();
								innerobj.put("id", object[0]);
								innerobj.put("dept", object[1].toString());
								jsonList.add(innerobj);
							}
						}
					}
				} 

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}
	
	
	
	public String fetchEmployeebydept() {
		
		
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query
						.append("SELECT emp.id, emp.empName,contact.level,emp.empLogo,subdept.subdeptname FROM compliance_contacts AS contact");
				query
						.append(" INNER JOIN subdepartment AS subdept ON subdept.id = contact.forDept_id");
				query
						.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
				query
						.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
				query
						.append(" WHERE   contact.work_status='0' and contact.forDept_id IN('"+ getDeptId() + "') ");
				if (searchString != null && !searchString.equalsIgnoreCase("")) {
					query.append(" and (emp.empName like '%" + searchString
							+ "%' OR contact.level like '%" + searchString
							+ "%' OR subdept.subdeptname like '%"
							+ searchString + "%' )");
				}
				query.append(" group by emp.id");
				System.out.println("Shift Wise List For employee qry::"+query);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[3] != null) {
								innerobj.put("logo", object[3].toString());
							} else {
								innerobj.put("logo", "images/noImage.JPG");
							}
							jsonList.add(innerobj);
						}
					}
				}

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public String fetchFloorWingsDetail() {
		System.out.println("get the call ");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				jsonList = new JSONArray();
				StringBuffer query1 = new StringBuffer(
						"select flr.id,flr.floorname from floor_detail as flr order by flr.floorname asc ");
				List dataList1 = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);

				if (dataList1 != null && dataList1.size() > 0) {
					for (Iterator iterator = dataList1.iterator(); iterator
							.hasNext();) {
						query1.delete(0, query1.length());
						Map<String, List> innerobj = new HashMap<String, List>();
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {

							query1
									.append("select nu.id as nuid, nu.nursing_unit from machine_order_nursing_details as nu  ");
							query1
									.append(" INNER JOIN floor_detail as flr on flr.id=nu.floorname ");
							query1.append(" where flr.id='"
									+ object[0].toString() + "'");
							List dataList2 = cbt.executeAllSelectQuery(query1
									.toString(), connectionSpace);
							int i = 0;
							if (dataList2 != null && dataList2.size() > 0) {
								List tempList = new ArrayList();
								for (Iterator iterator1 = dataList2.iterator(); iterator1
										.hasNext();) {
									Map<String, String> innerobj1 = new HashMap<String, String>();
									Object[] object2 = (Object[]) iterator1
											.next();
									if (object2[0] != null
											&& object2[1] != null) {
										innerobj1.put("wingid", object2[0]
												.toString());
										innerobj1.put("wingname", object2[1]
												.toString());
										tempList.add(innerobj1);
									}
								}
								innerobj.put(object[1].toString(), tempList);
								jsonList.add(innerobj);
							}
						}
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	
	
	public String fetchMappedEmployee() {
		System.out.println("get the bloody call");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query
						.append("SELECT emp.id as empid,emp.empName,cc.level,sudept.subdeptname,shift.shiftName,shift.fromShift,shift.toShift ");
				query.append(",ewm.wingsname,shift.id");
				query.append(" from compliance_contacts as cc ");
				query
						.append(" INNER JOIN long_stay_emp_wing_mapping as ewm on ewm.empName=cc.emp_id");
				query
						.append(" INNER JOIN employee_basic as emp on cc.emp_id=emp.id  ");
				query
						.append(" INNER JOIN subdepartment as sudept on sudept.id=cc.forDept_id");
				query
						.append(" INNER JOIN long_stay_shift_with_emp_wing as shift on shift.id=ewm.shiftId");
				if (viewFlag != null && viewFlag.equalsIgnoreCase("T")) {

				} else {
					query.append(" where shift.fromShift<='"
							+ DateUtil.getCurrentTime()
							+ "' and shift.toShift >'"
							+ DateUtil.getCurrentTime() + "'");
				}
				if (wingIds != null && !wingIds.equalsIgnoreCase("")) {
					query
							.append(" and ewm.wingsname IN("
									+ wingIds
											.substring(0, wingIds.length() - 1)
									+ ") ");
				}

				query.append(" and shift.dept_id IN(" + deptId + ")");
				if (shiftId != null && !shiftId.equalsIgnoreCase("")) {
					query.append(" and shift.id='" + shiftId + "'");
				}
				query.append(" group by emp.id,ewm.wingsname ");
				 System.out.println("Qyer: " + query);
				List dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null
								&& object[2] != null && object[3] != null) {
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[4] != null) {
								innerobj.put("shiftName", object[4].toString());
							} else {
								innerobj.put("shiftName", "NA");
							}
							if (object[5] != null) {
								innerobj.put("shiftFrom", object[5].toString());
							} else {
								innerobj.put("shiftFrom", "NA");
							}
							if (object[6] != null) {
								innerobj.put("shiftTo", object[6].toString());
							} else {
								innerobj.put("shiftTo", "NA");
							}
							if (object[8] != null) {
								innerobj.put("shiftid", object[8].toString());
							}
							if (object[7] != null) {
								innerobj.put("wingid", object[7].toString());
							}

							jsonList.add(innerobj);
						}
					}
				} else {
					query.delete(0, query.length());
					query
							.append(" SELECT shift.shiftName,shift.fromShift,shift.toShift,shift.id from long_stay_shift_with_emp_wing as shift ");
					if (viewFlag != null && viewFlag.equalsIgnoreCase("T")) {
						query.append(" where ");
					} else {
						query.append(" where shift.fromShift<='"
								+ DateUtil.getCurrentTime()
								+ "' and shift.toShift >'"
								+ DateUtil.getCurrentTime() + "' and");
					}

					if (shiftId != null && !shiftId.equalsIgnoreCase("")) {
						query.append("  shift.id='" + shiftId + "' and ");
					}
					query.append(" shift.dept_id IN(" + deptId + ")");
					query.append(" limit 1");
					List dataList1 = cbt.executeAllSelectQuery(
							query.toString(), connectionSpace);

					for (Iterator iterator = dataList1.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						JSONObject innerobj = new JSONObject();
						if (object[0] != null) {
							innerobj.put("shiftName", object[0].toString());
						}
						if (object[1] != null) {
							innerobj.put("shiftFrom", object[1].toString());
						}
						if (object[2] != null) {
							innerobj.put("shiftTo", object[2].toString());
						}
						if (object[3] != null) {
							innerobj.put("shiftid", object[3].toString());
						}

						jsonList.add(innerobj);
					}

				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}
	
	public String addShiftWithWingMapSave(){
		System.out.println("get the call from where it want");
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
					
					System.out.println("shiftID "+shiftID);
					if(shiftID.contains(".0")){
						shiftID = shiftID.replaceAll(".0", "");
					}
					System.out.println("shiftID "+shiftID);
					ob.setDataName(shiftID);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
				
					if(shiftemployeemappedwing!=null && !shiftemployeemappedwing.equalsIgnoreCase(""))
					{
						String ab[] =shiftemployeemappedwing.split(",");
						if (ab!=null && !ab.equals("")) 
						{
							for (int i = 0; i < ab.length; i++) 
							{
								ob = new InsertDataTable();
								ob.setColName("empName");
								ob.setDataName(ab[i].split("#")[0]);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("wingsname");
								ob.setDataName(ab[i].split("#")[1]);
								insertData.add(ob);
								cbt.insertIntoTable("long_stay_emp_wing_mapping", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
								insertData.remove(insertData.size()-1);
							}
						}
					}
					else if(mployeemappedwing!=null && !mployeemappedwing.equalsIgnoreCase(""))
					{
						String ab[] =mployeemappedwing.split(",");
						if (ab!=null && !ab.equals("")) 
						{
							for (int i = 0; i < ab.length; i++) 
							{
								ob = new InsertDataTable();
								ob.setColName("empName");
								ob.setDataName(empId);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("wingsname");
								ob.setDataName(ab[i]);
								insertData.add(ob);
								cbt.insertIntoTable("long_stay_emp_wing_mapping", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
								insertData.remove(insertData.size()-1);
							}
						}
					}
					addActionMessage("Data saved sucessfully.");
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	
	
	public String deleteEmpByTrashMethod() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String[] trash = trashEmpId.split(",");
				if (trash.length > 1) {
					String[] temp = trashEmpId.split(",");
					@SuppressWarnings("rawtypes")
					HashMap<String, String> emp = new HashMap<String, String>();
					for (int i = 0; i < temp.length; i++) {
						emp.put(temp[i].split("#")[1], temp[i].split("#")[0]);
					}

					if (emp.size() > 0) {
						StringBuilder query = new StringBuilder();
						Set set = emp.entrySet();
						Iterator i = set.iterator();

						while (i.hasNext()) {
							query.delete(0, query.length());
							Map.Entry me = (Map.Entry) i.next();
							
							System.out.println("shiftID "+shiftId);
							if(shiftId.contains(".0")){
								shiftId = shiftId.replaceAll(".0", "");
							}
							query
									.append("Delete from  long_stay_emp_wing_mapping where empName='"
											+ (String) me.getValue() + "'");
							query
									.append(" and wingsname='" + me.getKey()
											+ "'");
							query.append(" and shiftId='" + shiftId + "'");
							cbt.executeAllUpdateQuery(query.toString(),
									connectionSpace);

						}
					}

				} else {
					if (trashEmpId.contains("#")) {
						trashEmpId = trashEmpId.split("#")[0];
					}
					if (trashEmpId != null && !trashEmpId.equalsIgnoreCase("")) {
						System.out.println("shiftID "+shiftId);
						if(shiftId.contains(".0")){
							shiftId = shiftId.replaceAll(".0", "");
						}
						StringBuilder query = new StringBuilder();
						query
								.append("Delete from  long_stay_emp_wing_mapping where empName='"
										+ getTrashEmpId() + "'");
						if (wingIds != null && !wingIds.equalsIgnoreCase("")) {
							query.append(" and wingsname='" + wingIds + "'");
						}
						query.append(" and shiftId='" + shiftId + "'");
						cbt.executeAllUpdateQuery(query.toString(),
								connectionSpace);
					}
				}

				addActionMessage("Employee Unmapped Successfully!!!");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}
	
	
	public String deleteShift() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (shiftId != null && !shiftId.equalsIgnoreCase("")) {

					StringBuilder query = new StringBuilder();
					query.append(" Select id from long_stay_shift_with_emp_wing ");
					query.append(" where fromShift<='"
							+ DateUtil.getCurrentTime() + "' and toShift >'"
							+ DateUtil.getCurrentTime() + "'");
					query.append(" and dept_id='" + deptId + "'");
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					if (data != null && data.size() > 0) {
						Object obj = data.get(0);

						if (!obj.toString().equalsIgnoreCase(shiftId)) {
							query.delete(0, query.length());
							query
									.append("Delete from  long_stay_emp_wing_mapping where shiftId='"
											+ getShiftId() + "'");
							int result = cbt.executeAllUpdateQuery(query
									.toString(), connectionSpace);
							if (viewFlag == null) {
								StringBuilder query1 = new StringBuilder();
								query1
										.append("Delete from  long_stay_shift_with_emp_wing where id='"
												+ getShiftId() + "'");
								cbt.executeAllUpdateQuery(query1.toString(),
										connectionSpace);
							}
							addActionMessage("Shift Deleted Successfully!!!");
							return SUCCESS;

						} else {
							addActionMessage("Sorry!! You can not delete an active shift.");
							return SUCCESS;
						}
					} else {
						query.delete(0, query.length());
						query
								.append("Delete from  long_stay_emp_wing_mapping where shiftId='"
										+ getShiftId() + "'");
						int result = cbt.executeAllUpdateQuery(
								query.toString(), connectionSpace);
						if (viewFlag == null) {
							StringBuilder query1 = new StringBuilder();
							query1
									.append("Delete from  long_stay_shift_with_emp_wing where id='"
											+ getShiftId() + "'");
							cbt.executeAllUpdateQuery(query1.toString(),
									connectionSpace);
						}
						if (viewFlag == null) {
							addActionMessage("Shift Deleted Successfully!!!");
						} else {
							addActionMessage("Shift Cleared Successfully!!!");
						}

						return SUCCESS;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
		addActionMessage("Some problem in deleting data!!");
		return SUCCESS;
	}
	
	public String saveEmpWing() {
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession()) {
			try {
				// system.out.println("id "+shiftemployeemappedwing);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}
	
	public String addShiftWithWingMap() 
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
					ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					int shiftID=cbt.insertDataReturnId("long_stay_shift_with_emp_wing", insertData, connectionSpace);
					insertData.clear();
					
					if (shiftID!=0)
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
						ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
					
						if(shiftemployeemappedwing!=null && !shiftemployeemappedwing.equalsIgnoreCase(""))
						{
							String ab[] =shiftemployeemappedwing.split(",");
							if (ab!=null && !ab.equals("")) 
							{
								for (int i = 0; i < ab.length; i++) 
								{
									ob = new InsertDataTable();
									ob.setColName("empName");
									ob.setDataName(ab[i].split("#")[0]);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("wingsname");
									ob.setDataName(ab[i].split("#")[1]);
									insertData.add(ob);
									
									cbt.insertIntoTable("long_stay_emp_wing_mapping", insertData, connectionSpace);
									insertData.remove(insertData.size()-1);
									insertData.remove(insertData.size()-1);
								}
							}
						}
						addActionMessage("Data saved sucessfully.");
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	
	
	
	private boolean createShiftTable(CommonOperInterface cbt) 
	{
		boolean flag=false;
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
		
		flag=cbt.createTable22("long_stay_shift_with_emp_wing", tableColume, connectionSpace);
		tableColume.clear();
		
		ob1 = new TableColumes();
		ob1.setColumnname("empName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		tableColume.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("wingsname");
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
		
		cbt.createTable22("long_stay_emp_wing_mapping", tableColume, connectionSpace);
		
		return flag;
	}
	private List fetchServiceDept_SubDept(String deptLevel, String orgLevel,
			String orgId, String string, SessionFactory connectionSpace) {
		// TODO Auto-generated method stub
		List dept_subdeptList = null;
		StringBuilder qry = new StringBuilder();
		try {
		
			qry.append(" select sub.id, sub.subdeptname from subdepartment as sub  where sub.deptid = 7 ");
			Session session = null;
			Transaction transaction = null;
			session = connectionSpace.getCurrentSession();
			transaction = session.beginTransaction();
			dept_subdeptList = session.createSQLQuery(qry.toString()).list();
			transaction.commit();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dept_subdeptList;
	}

	public JSONArray getJsonList() {
		return jsonList;
	}

	public void setJsonList(JSONArray jsonList) {
		this.jsonList = jsonList;
	}

	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getWingName() {
		return wingName;
	}



	public void setWingName(String wingName) {
		this.wingName = wingName;
	}



	public String getFloorName() {
		return floorName;
	}



	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}



	public String getTrashEmpId() {
		return trashEmpId;
	}



	public void setTrashEmpId(String trashEmpId) {
		this.trashEmpId = trashEmpId;
	}



	public String getShiftemployeemappedwing() {
		return shiftemployeemappedwing;
	}



	public void setShiftemployeemappedwing(String shiftemployeemappedwing) {
		this.shiftemployeemappedwing = shiftemployeemappedwing;
	}



	public String getShiftId() {
		return shiftId;
	}



	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}



	public String getWingIds() {
		return wingIds;
	}



	public void setWingIds(String wingIds) {
		this.wingIds = wingIds;
	}



	public String getViewFlag() {
		return viewFlag;
	}



	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}



	public String getModule() {
		return module;
	}



	public void setModule(String module) {
		this.module = module;
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



	public String getShift_name() {
		return shift_name;
	}



	public void setShift_name(String shiftName) {
		shift_name = shiftName;
	}



	public String getFromShift() {
		return fromShift;
	}



	public void setFromShift(String fromShift) {
		this.fromShift = fromShift;
	}



	public String getToShift() {
		return toShift;
	}



	public void setToShift(String toShift) {
		this.toShift = toShift;
	}



	public String getShiftID() {
		return shiftID;
	}



	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}



	public String getMployeemappedwing() {
		return mployeemappedwing;
	}



	public void setMployeemappedwing(String mployeemappedwing) {
		this.mployeemappedwing = mployeemappedwing;
	}



	public String getEmpId() {
		return empId;
	}



	public void setEmpId(String empId) {
		this.empId = empId;
	}

}
