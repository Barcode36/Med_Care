package com.Over2Cloud.ctrl.helpdesk.EscalationConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;

public class EscalationConfigAction extends GridPropertyBean implements
		ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private Map<String, String> serviceDeptMap;
	private Map<String, String> floorMap;
	private HttpServletRequest request;
	private String deptId;
	private String SubDeptId;
	private JSONArray jsonList;
	private List<Object> masterViewList;
	private String status;
	private String wingName;
	private String floorName;
	private String trashEmpId;
	private String deptFlag;
	private String shiftemployeemappedwing;
	private String shiftId;
	private String wingIds;
	private String viewFlag;
	private String module;
	private String fromTime, toTime;

	CommonOperInterface cbt = new CommonConFactory().createInterface();

	@SuppressWarnings("unchecked")
	public String editEscalationDept() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && Parmname != null
								&& !Parmname.equalsIgnoreCase("tableName")
								&& !Parmname.equalsIgnoreCase("oper")
								&& !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("rowid")) {
							if (Parmname.equalsIgnoreCase("escLevel2")) {
								Parmname = "l2Tol3";
							} else if (Parmname.equalsIgnoreCase("escLevel3")) {
								Parmname = "l3Tol4";
							} else if (Parmname.equalsIgnoreCase("escLevel4")) {
								Parmname = "l4Tol5";
							} else if (Parmname.equalsIgnoreCase("escLevel5")) {
								Parmname = "l5Tol6";
							}
							wherClause.put(Parmname, paramValue);
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("escalation_config_detail",
							wherClause, condtnBlock, connectionSpace);
					returnResult = SUCCESS;
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds) {
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("escalation_config_detail", "id",
							condtIds.toString(), connectionSpace);
					returnResult = SUCCESS;
				}
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String beforeConfigEsc() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				serviceDeptMap = new LinkedHashMap<String, String>();
				List departmentlist = new HelpdeskUniversalAction()
						.getServiceDept_SubDept("2", orgLevel, orgId, "HDM",
								connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0) {
					for (Iterator iterator = departmentlist.iterator(); iterator
							.hasNext();) {
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptMap.put(object1[0].toString(), object1[1]
								.toString());
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public String addEscConfig() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();

				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("escDept");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escSubDept");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevel");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("considerRoaster");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL2L3");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l2Tol3");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL3L4");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l3Tol4");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL4L5");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l4Tol5");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL5L6");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l5Tol6");
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

				cbt.createTable22("escalation_config_detail", tableColume,
						connectionSpace);

				ob = new InsertDataTable();
				ob.setColName("escLevel");
				ob.setDataName(request.getParameter("escLevel"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("considerRoaster");
				ob.setDataName(request.getParameter("considerRoaster"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escLevelL2L3");
				ob.setDataName(request.getParameter("escLevelL2L3"));
				insertData.add(ob);

				if (request.getParameter("escLevelL2L3").equalsIgnoreCase(
						"Customised")) {
					ob = new InsertDataTable();
					ob.setColName("l2Tol3");
					ob.setDataName(request.getParameter("l2Tol3"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("escLevelL3L4");
				ob.setDataName(request.getParameter("escLevelL3L4"));
				insertData.add(ob);

				if (request.getParameter("escLevelL3L4").equalsIgnoreCase(
						"Customised")) {
					ob = new InsertDataTable();
					ob.setColName("l3Tol4");
					ob.setDataName(request.getParameter("l3Tol4"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("escLevelL4L5");
				ob.setDataName(request.getParameter("escLevelL4L5"));
				insertData.add(ob);

				if (request.getParameter("escLevelL4L5").equalsIgnoreCase(
						"Customised")) {
					ob = new InsertDataTable();
					ob.setColName("l4Tol5");
					ob.setDataName(request.getParameter("l4Tol5"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("escLevelL5L6");
				ob.setDataName(request.getParameter("escLevelL5L6"));
				insertData.add(ob);

				if (request.getParameter("escLevelL5L6").equalsIgnoreCase(
						"Customised")) {
					ob = new InsertDataTable();
					ob.setColName("l5Tol6");
					ob.setDataName(request.getParameter("l5Tol6"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat() + " "
						+ DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escDept");
				ob.setDataName(request.getParameter("escDept"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escSubDept");
				ob.setDataName(request.getParameter("escSubDept"));
				insertData.add(ob);
				cbt.insertIntoTable("escalation_config_detail", insertData,
						connectionSpace);
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				addActionMessage("Opps. There are some problem in data save.");
				return ERROR;

			}
		} else {
			return LOGIN;
		}
	}

	public String beforeConfigEscContact() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				serviceDeptMap = new LinkedHashMap<String, String>();
				floorMap = new LinkedHashMap<String, String>();
				StringBuffer query = new StringBuffer();
				query
						.append("SELECT dept.id,dept.deptName FROM department AS dept");
				query
						.append(" INNER JOIN escalation_config_detail AS esc ON esc.escDept = dept.id");
				query
						.append(" WHERE esc.escLevel!='subdept' ORDER BY dept.deptName");
				List dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				query.setLength(0);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {
							serviceDeptMap.put(object[0].toString(), object[1]
									.toString());
						}
					}
				}
				dataList.clear();
				query
						.append("SELECT id,floorname FROM floor_detail ORDER BY floorname");
				dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {
							floorMap.put(object[0].toString(), object[1]
									.toString());
						}
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	// DD class
	@SuppressWarnings("unchecked")
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
							query
									.append("Delete from  emp_wing_mapping where empName='"
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
						StringBuilder query = new StringBuilder();
						query
								.append("Delete from  emp_wing_mapping where empName='"
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

	public String getEmployee() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				jsonList = new JSONArray();
				if (deptId != null) {
					StringBuilder query = new StringBuilder();
					query
							.append("SELECT emp.id, emp.empName,contact.level FROM compliance_contacts AS contact");
					query
							.append(" INNER JOIN subdepartment AS subdept ON subdept.id = contact.forDept_id");
					query
							.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
					query
							.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
					query
							.append(" WHERE contact.moduleName='HDM' AND comp.work_status = '0' AND contact.level>1 AND dept.id ="
									+ deptId);
					// System.out.println(query.toString());
					List dataList = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					if (dataList != null && dataList.size() > 0) {
						for (Iterator iterator = dataList.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null) {
								// System.out.println(object[0].toString()+" "+object[1].toString());
								JSONObject jsonMap = new JSONObject();
								jsonMap.put("id", object[0].toString());
								jsonMap.put("name", object[1].toString()
										+ " - Level " + object[2].toString());
								jsonList.add(jsonMap);
							}
						}
					}
				}
				// System.out.println("jsonList "+jsonList.size());
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public String fetchMappedEmployee() {
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
						.append(" INNER JOIN emp_wing_mapping as ewm on ewm.empName=cc.emp_id");
				query
						.append(" INNER JOIN employee_basic as emp on cc.emp_id=emp.id  ");
				query
						.append(" INNER JOIN subdepartment as sudept on sudept.id=cc.forDept_id");
				query
						.append(" INNER JOIN shift_with_emp_wing as shift on shift.id=ewm.shiftId");
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
							.append(" SELECT shift.shiftName,shift.fromShift,shift.toShift,shift.id from shift_with_emp_wing as shift ");
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

	@SuppressWarnings("rawtypes")
	public String getEmployeebydept() {
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
						.append(" WHERE contact.moduleName='HDM' and contact.work_status='0' and contact.forDept_id IN(SELECT id from subdepartment where deptid='"
								+ getDeptId() + "') ");
				if (searchString != null && !searchString.equalsIgnoreCase("")) {
					query.append(" and (emp.empName like '%" + searchString
							+ "%' OR contact.level like '%" + searchString
							+ "%' OR subdept.subdeptname like '%"
							+ searchString + "%' )");
				}
				query.append(" group by emp.id");
				// System.out.println("Shift Wise List For employee qry::"+query);
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

	public String getFloorWingsDetail() {
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
									.append("SELECT wing.id as wingsid,wing.wingsname from wings_detail as wing ");
							query1
									.append(" INNER JOIN floor_detail as flr on flr.id=wing.floorname ");
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

	public String getAlldept() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try {
				jsonList = new JSONArray();
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();

				if (deptFlag != null && deptFlag.equalsIgnoreCase("dept")) {
					List departmentlist = HUA.getServiceDept_SubDept(deptLevel,
							orgLevel, orgId, "HDM", connectionSpace);
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
				} else {
					jsonList = HUA.getAllSubDeptByDeptId(deptId,
							connectionSpace);
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

	public String fetchDepartmentList() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try {
				jsonList = new JSONArray();
				if (module != null) {
					List departmentlist = fetchDepartmentForModule();
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

	@SuppressWarnings("unchecked")
	public List fetchDepartmentForModule() {
		List departmentlist = null;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				serviceDeptMap = new LinkedHashMap<String, String>();
				StringBuilder query = new StringBuilder("");
				query.append("SELECT DISTINCT dept.id, dept.deptName ");
				query.append("FROM department AS dept ");
				query
						.append("INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id ");
				query
						.append("INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id ");
				query.append("WHERE cc.moduleName='" + module + "' ");
				departmentlist = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return departmentlist;
	}

	public String getDeptRoomFloorDetail() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				jsonList = new JSONArray();

				StringBuilder query = new StringBuilder();
				query
						.append("select wd.id,subdept.subdeptname,flr.floorname,wd.wingsname from escalation_config_detail as esc");
				query
						.append(" inner join escalation_contact_config_detail as escc on escc.escDept=esc.escDept");
				query
						.append(" inner join subdepartment as subdept on subdept.id=esc.escDept");
				query
						.append(" inner join floor_detail as flr on flr.id=escc.floorname");
				query
						.append(" inner join wings_detail as wd on wd.id=escc.wingsname");

				List dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {
							/*
							 * JSONObject innerobj= new JSONObject();
							 * if(object[3]!=null){ innerobj.put("pic",
							 * object[3].toString());
							 * 
							 * }else { innerobj.put("pic", "noImage.JPG"); }
							 * 
							 * 
							 * System.out.println(object[0].toString()+" "+object
							 * [1].toString()); //JSONObject jsonMap = new
							 * JSONObject(); //jsonMap.put("id",
							 * object[0].toString()); //jsonMap.put("name",
							 * object
							 * [1].toString()+" - Level "+object[2].toString());
							 *//*
								 * jsonObject.put(object[1].toString()+" - Level "
								 * +object[2].toString(), innerobj);
								 */
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("dept", object[1].toString());
							innerobj.put("floor", object[2].toString());
							innerobj.put("wing", object[3].toString());

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

	public String checkEscLevel() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				floorMap = new LinkedHashMap<String, String>();
				if (deptId != null) {
					String query = "SELECT escLevel FROM escalation_config_detail WHERE escDept ="
							+ deptId;
					List dataList = cbt.executeAllSelectQuery(query,
							connectionSpace);
					if (dataList != null && dataList.size() > 0) {
						floorMap
								.put("escLevelFlag", dataList.get(0).toString());
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public String addEscContactConfig() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();

				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("escDept");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escSubDept");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("empName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("floorname");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("wingsname");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("level");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("status");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default 'Active'");
				tableColume.add(ob1);

				cbt.createTable22("escalation_contact_config_detail",
						tableColume, connectionSpace);

				ob = new InsertDataTable();
				ob.setColName("escDept");
				ob.setDataName(request.getParameter("escDept"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escSubDept");
				ob.setDataName(request.getParameter("escSubDept"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Active");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("empName");
				ob.setDataName(request.getParameter("empName"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("floorname");
				ob.setDataName(request.getParameter("floorname"));
				insertData.add(ob);
				if (request.getParameter("escLevel").equalsIgnoreCase("wings")) {
					if (!request.getParameter("wingsname").equals("-1")) {
						ob = new InsertDataTable();
						ob.setColName("wingsname");
						ob.setDataName(request.getParameter("wingsname"));
						insertData.add(ob);
					}
				}

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName(request.getParameter("level"));
				insertData.add(ob);
				int i = cbt.insertDataReturnId(
						"escalation_contact_config_detail", insertData,
						connectionSpace);
				// cbt.insertIntoTable(, insertData, connectionSpace);
				JSONObject innerobj = new JSONObject();
				jsonList = new JSONArray();
				innerobj.put("newid", i);
				jsonList.add(innerobj);
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public String viewEscContct() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb = new ArrayList<Object>();
				List dataList = null;
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				query
						.append("SELECT esc.id,dept.deptName,emp.empName,floor.floorname,wings.wingsname,esc.level,dept.id AS deptId,esc.status FROM escalation_contact_config_detail AS esc");
				query
						.append(" INNER JOIN department AS dept ON dept.id = esc.escDept");
				query
						.append(" INNER JOIN employee_basic AS emp ON emp.id = esc.empName");
				query
						.append(" INNER JOIN floor_detail AS floor ON floor.id = esc.floorname");
				query
						.append(" INNER JOIN wings_detail AS wings ON wings.id =esc.wingsname");
				// System.out.println("####### "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);

				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", CUA.getValueWithNullCheck(object[0]));
						one.put("deptName", CUA
								.getValueWithNullCheck(object[1]));
						one
								.put("empName", CUA
										.getValueWithNullCheck(object[2]));
						one.put("floorName", CUA
								.getValueWithNullCheck(object[3]));
						one.put("wingsName", CUA
								.getValueWithNullCheck(object[4]));
						one.put("level", "Level "
								+ CUA.getValueWithNullCheck(object[5]));
						if (CUA.getValueWithNullCheck(object[5]).equals("2"))
							one.put("escLevel2", getEscTime("l2Tol3",
									"escDept", CUA
											.getValueWithNullCheck(object[6])));
						else
							one.put("escLevel2", "00:00");

						if (CUA.getValueWithNullCheck(object[5]).equals("3"))
							one.put("escLevel3", getEscTime("l3Tol4",
									"escDept", CUA
											.getValueWithNullCheck(object[6])));
						else
							one.put("escLevel3", "00:00");

						if (CUA.getValueWithNullCheck(object[5]).equals("4"))
							one.put("escLevel4", getEscTime("l4Tol5",
									"escDept", CUA
											.getValueWithNullCheck(object[6])));
						else
							one.put("escLevel4", "00:00");

						one.put("status", CUA.getValueWithNullCheck(object[7]));

						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList != null && masterViewList.size() > 0) {
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));
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

	public String viewEscDeptNew() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb = new ArrayList<Object>();
				List dataList = null;
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				query
						.append("SELECT esc.id,dept.deptName,subdept.subdeptname,esc.considerRoaster,esc.escLevelL2L3,esc.l2Tol3,");
				query
						.append("esc.escLevelL3L4,esc.l3Tol4,esc.escLevelL4L5,esc.l4Tol5,esc.escLevelL5L6,esc.l5Tol6");
				query.append(" FROM escalation_config_detail AS esc");
				query
						.append(" INNER JOIN subdepartment AS subdept ON subdept.id = esc.escSubDept ");
				query
						.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid ");
				query.append(" group by subdept.subdeptname ");
				// System.out.println("####### "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);

				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", CUA.getValueWithNullCheck(object[0]));
						one.put("deptName", CUA
								.getValueWithNullCheck(object[1]));
						one
								.put("subdept", CUA
										.getValueWithNullCheck(object[2]));
						one.put("considerRoaster", CUA
								.getValueWithNullCheck(object[3]));
						one.put("escLevelL2L3", CUA
								.getValueWithNullCheck(object[4]));
						if (object[5] != null)
							one.put("escLevel2", object[5].toString());
						else
							one.put("escLevel2", "00:00");

						one.put("escLevelL3L4", CUA
								.getValueWithNullCheck(object[6]));

						if (object[7] != null)
							one.put("escLevel3", object[7].toString());
						else
							one.put("escLevel3", "00:00");

						one.put("escLevelL4L5", object[8].toString());

						if (object[9] != null)
							one.put("escLevel4", object[9].toString());
						else
							one.put("escLevel4", "00:00");
						if (object[10] != null)
							one.put("escLevelL5L6", object[10].toString());
						else
							one.put("escLevel6", "00:00");

						if (object[11] != null)
							one.put("escLevel5", object[11].toString());
						else
							one.put("escLevel5", "00:00");

						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList != null && masterViewList.size() > 0) {
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));
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

	@SuppressWarnings("unchecked")
	public String editEscContct() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();

					wherClause.put("status", status);
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("escalation_contact_config_detail",
							wherClause, condtnBlock, connectionSpace);
					returnResult = SUCCESS;
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds) {
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId(
							"escalation_contact_config_detail", "id", condtIds
									.toString(), connectionSpace);
					returnResult = SUCCESS;
				}
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getEscTime(String fieldName, String conField, String conValue) {
		String time = "00:00";
		try {
			String query = "SELECT " + fieldName
					+ " FROM escalation_config_detail WHERE " + conField + " ="
					+ conValue;
			List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0) {
				time = dataList.get(0).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	public String demoMethod() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
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

	public String viewShift() {
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession()) {
			try {
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query
						.append(" select id,shiftName,fromShift,toShift from shift_with_emp_wing where dept_id='"
								+ getDeptId() + "'");
				if (viewFlag != null && viewFlag.equalsIgnoreCase("live")) {
					query.append(" order by id desc ");
				}
				// System.out.println("veiw Sift::"+query);
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
							innerobj.put("shiftName", object[1].toString());
							innerobj.put("to", object[3].toString());
							innerobj.put("from", object[2].toString());

							jsonList.add(innerobj);
						}
					}
				}

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
					query.append(" Select id from shift_with_emp_wing ");
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
									.append("Delete from  emp_wing_mapping where shiftId='"
											+ getShiftId() + "'");
							int result = cbt.executeAllUpdateQuery(query
									.toString(), connectionSpace);
							if (viewFlag == null) {
								StringBuilder query1 = new StringBuilder();
								query1
										.append("Delete from  shift_with_emp_wing where id='"
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
								.append("Delete from  emp_wing_mapping where shiftId='"
										+ getShiftId() + "'");
						int result = cbt.executeAllUpdateQuery(
								query.toString(), connectionSpace);
						if (viewFlag == null) {
							StringBuilder query1 = new StringBuilder();
							query1
									.append("Delete from  shift_with_emp_wing where id='"
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

	public String getSkillDetails() {

		if (ValidateSession.checkSession()) {
			try {
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query
						.append(" select subdept.subdeptname,contacts.level from compliance_contacts as contacts ");
				query
						.append(" INNER JOIN subdepartment as subdept on subdept.id = contacts.forDept_id ");
				query
						.append(" INNER JOIN employee_basic as emp on emp.id = contacts.forDept_id ");
				query
						.append(" where contacts.emp_id='"
								+ trashEmpId
								+ "' and contacts.moduleName='HDM' and contacts.work_status='0'");
				// System.out.println("Qery Skill"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {
							JSONObject innerobj = new JSONObject();
							innerobj.put("skill", object[0]);
							innerobj.put("level", "Level-"
									+ object[1].toString());
							jsonList.add(innerobj);
						}
					}
				}

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public String editShiftTime() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (shiftId != null && !shiftId.equalsIgnoreCase("")) {
					StringBuilder query = new StringBuilder();
					query.append("UPDATE shift_with_emp_wing set fromShift='"
							+ fromTime + "',toShift='" + toTime
							+ "' where id='" + getShiftId() + "'");
					int result = cbt.executeAllUpdateQuery(query.toString(),
							connectionSpace);

				}
				addActionMessage("Shift Updated Successfully!!!");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public Map<String, String> getServiceDeptMap() {
		return serviceDeptMap;
	}

	public void setServiceDeptMap(Map<String, String> serviceDeptMap) {
		this.serviceDeptMap = serviceDeptMap;
	}

	public Map<String, String> getFloorMap() {
		return floorMap;
	}

	public void setFloorMap(Map<String, String> floorMap) {
		this.floorMap = floorMap;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public JSONArray getJsonList() {
		return jsonList;
	}

	public void setJsonList(JSONArray jsonList) {
		this.jsonList = jsonList;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
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

	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}

	public String getSubDeptId() {
		return SubDeptId;
	}

	public void setSubDeptId(String subDeptId) {
		SubDeptId = subDeptId;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}