package com.Over2Cloud.ctrl.referral.escalation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class EscalationAction extends GridPropertyBean implements ServletRequestAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request;
	private List<Object> masterViewList;
	private LinkedHashMap<String, String> serviceDeptMap;
	private String l1, l2, l3, l4, l5, div, sId, sName, nId, nName;
	private String tat2,tat3,tat4,tat5,tat6;
	private String priority,escalation,subDept;
	private Map<String, String> escEmpNextLevel = null;
	private Map<String, String> emplMap,escalationMap,escL1Emp;
	private Map<String, String>	escContMap1,escContMap2,escContMap3,escContMap4;
	private String selectedRemind2Cont,selectedEsc1Cont,selectedEsc2Cont,selectedEsc3Cont,selectedEsc4Cont;
	private JSONArray jsonArr = null;
	private String fromTime,toTime,firstEsc,firstEscFlag;
	public String getEscalation4Edit()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(oper!=null && oper.equalsIgnoreCase("del") && id!=null)
				{
					

					CommonOperInterface cbt=new CommonConFactory().createInterface();
					/*String tempIds[]=getId().split(",");
					StringBuilder condtIds=new StringBuilder();
						int i=1;
						for(String H:tempIds)
						{
							if(i<tempIds.length)
								condtIds.append(H+" ,");
							else
								condtIds.append(H);
							i++;
						}*/
						//StringBuilder query=new StringBuilder("DELETE FROM referral_escalation_detail SET flag='1' WHERE id ="+id+"");
						cbt.deleteAllRecordForId("referral_escalation_detail", "id", id, connectionSpace);
				
					return SUCCESS;
				}
				else if(id!=null && !id.equalsIgnoreCase("undefined"))
				{
					StringBuilder contIdss = new StringBuilder();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					
					StringBuilder query = new StringBuilder();
					query.append("SELECT esc.id,dept.deptName,subdept.subdeptname,esc.l2,esc.tat2,esc.l3,esc.tat3,");
					query.append("esc.l4,esc.tat4,esc.l5,esc.tat5,esc.l6,esc.tat6,esc.priority,esc.escalation, ");
					query.append("esc.fromTime,esc.toTime,esc.firstEsc,esc.firstEscFlag ");
		 			query.append(" FROM referral_escalation_detail AS esc ");
					query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = esc.esc_sub_dept ");
					query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid ");
					query.append("WHERE esc.id="+id);
					//System.out.println("Main Query ::: "+query.toString());
					List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							
							l2 = "#"+CUA.getValueWithNullCheck(object[3]);
							tat2 = CUA.getValueWithNullCheck(object[4]);
							l3 = "#"+CUA.getValueWithNullCheck(object[5]);
							tat3 = CUA.getValueWithNullCheck(object[6]);
							l4 = "#"+CUA.getValueWithNullCheck(object[7]);
							tat4 = CUA.getValueWithNullCheck(object[8]);
							l5 = "#"+CUA.getValueWithNullCheck(object[9]);
							tat5 = CUA.getValueWithNullCheck(object[10]);
							priority=CUA.getValueWithNullCheck(object[13]);
							escalation=CUA.getValueWithNullCheck(object[14]);
							fromTime=CUA.getValueWithNullCheck(object[15]);
							toTime=CUA.getValueWithNullCheck(object[16]);
							firstEsc=CUA.getValueWithNullCheck(object[17]);
							firstEscFlag=CUA.getValueWithNullCheck(object[18]);
							
						
						
						}
						// Escalation Map
						escalationMap = new LinkedHashMap<String, String>();
						escContMap1 = new LinkedHashMap<String, String>();
						escContMap2 = new LinkedHashMap<String, String>();
						escContMap3 = new LinkedHashMap<String, String>();
						escContMap4 = new LinkedHashMap<String, String>();
						
						escL1Emp = new LinkedHashMap<String, String>();
						escL1Emp = getComplianceAllContacts(subDept,null, "CRF");
						
						if(escalation.equalsIgnoreCase("Yes"))
						{
							escalationMap.put("Yes", "Yes");
							escalationMap.put("No", "No");
							
							// 1st Escalation Map
							if(tat2!=null && !tat2.equalsIgnoreCase("#NA"))
							{
								escContMap1.putAll(escL1Emp); 
								//= CCO.getComplianceAllContacts(userDeptId, "COMPL");
								selectedEsc1Cont = "0"+l2.replace("#", ",")+"0";
								contIdss.append(selectedEsc1Cont);
							}
							
							// 2nd Escalation Map
							if(tat3!=null && !tat3.equalsIgnoreCase("#NA"))
							{
								escContMap2 = getComplianceAllContacts(subDept,contIdss.toString(),"CRF");
								selectedEsc2Cont = "0"+l3.replace("#", ",")+"0";
								contIdss.append(","+selectedEsc2Cont);
							}
							else
							{
								escContMap2 = getComplianceAllContacts(subDept,contIdss.toString(),  "CRF");
							}
							
							// 3rd Escalation Map
							if(tat4!=null && !tat4.equalsIgnoreCase("#NA"))
							{
								escContMap3 = getComplianceAllContacts(subDept,contIdss.toString(), "CRF");
								selectedEsc3Cont = "0"+l4.replace("#", ",")+"0";
								contIdss.append(","+selectedEsc3Cont);
							}
							else
							{
								escContMap3 = getComplianceAllContacts(subDept,contIdss.toString(),"CRF");
							}
							
							if(tat5!=null && !tat5.equalsIgnoreCase("#NA"))
							{
								escContMap4 = getComplianceAllContacts(subDept,contIdss.toString(), "CRF");
								selectedEsc4Cont = "0"+l5.replace("#", ",")+"0";
							}
							else
							{
								escContMap4 =getComplianceAllContacts(subDept,contIdss.toString(),  "CRF");
							}
						}
						else if(escalation.equalsIgnoreCase("No"))
						{
							escalationMap.put("No", "No");
							escalationMap.put("Yes", "Yes");
						}
						
						
						// Contact Map
						emplMap = new LinkedHashMap<String, String>();
						 						
						emplMap = getComplianceAllContacts(subDept,null, "CRF");
						
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
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
	
	@SuppressWarnings("unchecked")
	public String EditEscalationAction()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(id!=null)
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					@SuppressWarnings("rawtypes")
					Iterator it = requestParameterNames.iterator();
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", id);
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase(""))
						{
							if (parmName.equals("l2") || parmName.equals("l3") || parmName.equals("l4") || parmName.equals("l5"))
							{
								String empId[] = request.getParameterValues(parmName);
								StringBuilder strIDD = new StringBuilder();
								for (int i = 0; i < empId.length; i++)
								{
									strIDD.append(empId[i]);
									strIDD.append("#");
								}
								setVariables.put(parmName, strIDD.toString());
							}
							else
							{
								setVariables.put(parmName, paramValue);
							}
						}
					}
					boolean status= cbt.updateTableColomn("referral_escalation_detail", setVariables, whereCondition, connectionSpace);
					if(status)
					{
						addActionMessage("Escalation Edit Sucessfully!!!");
					}
					else
					{
						addActionMessage("Some Problem In Escalation Edit");
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Some Problem In Escalation Edit");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public Map<String, String> getComplianceAllContacts(String subDeptId,String ids,  String moduleName)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
			qryString.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE cc.id!='0' ");
			if(ids!=null && !"".equalsIgnoreCase(ids))
			{
				qryString.append(" AND cc.id NOT IN(" + ids + ")");
			}
			if(subDeptId!=null && !"".equalsIgnoreCase(subDeptId))
			{
				qryString.append(" AND forDept_id=" + subDeptId + " ");
			}
			qryString.append(" AND moduleName='" + moduleName + "' AND work_status!=1 ORDER BY empName ASC");
			

			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return empMap;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String beforeConfigEscalation()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				serviceDeptMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuffer query = new StringBuffer();
				query.append("SELECT dept.id,dept.deptName FROM department AS dept");
				query.append(" ORDER BY dept.deptName");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				query.setLength(0);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							serviceDeptMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
				/*query.setLength(0);
				escEmpNextLevel = new LinkedHashMap<String, String>();
				query.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
				query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
				query.append(" WHERE  moduleName='CRF' AND work_status!=1 AND emp.flag='0' order by empName asc");
				// System.out.println("qryStringnxtlevel " + qryString);
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] objectCol = null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						objectCol = (Object[]) iterator.next();
						if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
						{
							escEmpNextLevel.put(objectCol[0].toString(), objectCol[1].toString());
						}
					}
				}*/
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addEscalation()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				boolean isExist = cbt.checkExitOfTable("referral_escalation_detail", connectionSpace, null, null);
				if (!isExist)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname("esc_dept");
					ob1.setDatatype("int(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("esc_sub_dept");
					ob1.setDatatype("int(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("priority");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("l2");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("tat2");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("fromTime");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("toTime");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("firstEsc");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("firstEscFlag");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default Null");
					tableColume.add(ob1);
					
					
					ob1 = new TableColumes();
					ob1.setColumnname("l3");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("tat3");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("l4");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("tat4");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("l5");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("tat5");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("l6");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("tat6");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("user_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("escalation");
					ob1.setDatatype("varchar(3)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					cbt.createTable22("referral_escalation_detail", tableColume, connectionSpace);
					
				//	System.out.println("After table create.............");
				}

				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue = request.getParameter(parmName);
					//System.out.println(parmName + ":::::" + paramValue);
					if (parmName.equals("l2") || parmName.equals("l3") || parmName.equals("l4") || parmName.equals("l5"))
					{
						//System.out.println( request.getParameterValues(parmName));
						String empId[] = request.getParameterValues(parmName);
						StringBuilder strIDD = new StringBuilder();
						for (int i = 0; i < empId.length; i++)
						{
							strIDD.append(empId[i]);
							strIDD.append("#");
						}
						insertData = setParameterInObj(parmName, strIDD.toString(), insertData);
					}
					else if (paramValue != null && !paramValue.equalsIgnoreCase(""))
					{
						ob = new InsertDataTable();
						ob.setColName(parmName);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
				}
				//System.out.println(request.getParameter("__multiselect_l2").length()+":::::::::::::::::::");
				ob = new InsertDataTable();
				ob.setColName("user_name");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Active");
				insertData.add(ob);

				cbt.insertDataReturnId("referral_escalation_detail",insertData, connectionSpace);

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
	
	private List<InsertDataTable> setParameterInObj(String paramName, String paramValue, List<InsertDataTable> insertData)
	{
		InsertDataTable object = new InsertDataTable();
		object.setColName(paramName);
		object.setDataName(paramValue);
		insertData.add(object);
		return insertData;
	}

	public String escalationViewGrid()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
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

	@SuppressWarnings("rawtypes")
	public String getGridData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT esc.id,dept.deptName,subdept.subdeptname,emp1.empName,esc.tat2,esc.l3,esc.tat3,");
				query.append("esc.l4,esc.tat4,esc.l5,esc.tat5,esc.l6,esc.tat6,esc.priority,esc.esc_sub_dept, ");
				query.append("esc.fromTime,esc.toTime,esc.firstEsc,esc.firstEscFlag ");
	 			query.append(" FROM referral_escalation_detail AS esc ");
				query.append(" INNER JOIN compliance_contacts AS comp1 ON comp1.id = esc.l2 ");
				query.append(" INNER JOIN employee_basic AS emp1 ON emp1.id = comp1.emp_id ");
	 			query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = esc.esc_sub_dept ");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid ");
				query.append("  where esc.id!=0 ");
				//System.out.println("####### "+query.toString());
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", getValueWithNullCheck(object[0]));
						one.put("deptName", getValueWithNullCheck(object[1]));
						one.put("subdept", getValueWithNullCheck(object[2]));
						one.put("l2", getValueWithNullCheck(object[3]));
						one.put("tat2", getValueWithNullCheck(object[4]));
						one.put("l3", getValueWithNullCheck(object[5]));
						one.put("tat3", getValueWithNullCheck(object[6]));
						one.put("l4", getValueWithNullCheck(object[7]));
						one.put("tat4", getValueWithNullCheck(object[8]));
						one.put("l5", getValueWithNullCheck(object[9]));
						one.put("tat5", getValueWithNullCheck(object[10]));
						one.put("l6", getValueWithNullCheck(object[11]));
						one.put("tat6", getValueWithNullCheck(object[12]));
						one.put("priority", object[13].toString());
						one.put("subDept", object[14].toString());
			 			one.put("fromTime", getValueWithNullCheck(object[15]));
						one.put("toTime", getValueWithNullCheck(object[16]));
						one.put("firstEsc", getValueWithNullCheck(object[17]));
						one.put("firstEscFlag", getValueWithNullCheck(object[18]));
					
						
						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList != null && masterViewList.size() > 0)
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

	public String nextEscMap4Emp()
	{
		String empID = " ";

		if (l1 != null && !l1.equalsIgnoreCase("undefined") && !l1.equalsIgnoreCase("null"))
		{
			empID = l1;
			// System.out.println("get 2");
		}
		if (l2 != null && !l2.equalsIgnoreCase("undefined") && !l2.equalsIgnoreCase("null"))
		{
			empID = empID + ", " + l2;
			// System.out.println("get 3");
		}
		if (l3 != null && !l3.equalsIgnoreCase("undefined") && !l3.equalsIgnoreCase("null"))
		{
			empID = empID + ", " + l3;
			// System.out.println("get 4");
		}
		if (l4 != null && !l4.equalsIgnoreCase("undefined")&& !l4.equalsIgnoreCase("null"))
		{
			empID = empID + ", " + l4;
			// System.out.println("get 5");
		}
	//	System.out.println(sId+", "+sName+", "+nId+", "+nName);
		escEmpNextLevel = new LinkedHashMap<String, String>();
		escEmpNextLevel = getComplianceAllContacts4NxtExc(empID, "CRF");
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	private Map<String, String> getComplianceAllContacts4NxtExc(String empID2, String module)
	{
		/*String deptId = "";
		String[] loggedUserData = new String[7];
		loggedUserData = getEmpDetailsByUserName("CRF", userName);
		if (loggedUserData != null && loggedUserData.length > 0)
		{
			deptId = loggedUserData[4];
		}*/

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
			qryString.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE moduleName='" + module + "' AND work_status!=1 AND emp.flag='0'  ");
			if(empID2!=null && !empID2.equalsIgnoreCase("") && !empID2.equalsIgnoreCase(" "))
			{
				qryString.append(" AND cc.id NOT IN(" + empID2 + ") ");
			}
			if(subDept!=null && !subDept.equalsIgnoreCase(""))
			{
				qryString.append(" AND forDept_id='"+subDept+"' ");
			}
			qryString.append(" ORDER BY empName ASC");
			//System.out.println("qryStringnxtlevel " + qryString);
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return empMap;
	}

	@SuppressWarnings("rawtypes")
	public String[] getEmpDetailsByUserName(String moduleName, String userName)
	{
		String[] values = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

			StringBuilder query = new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='" + moduleName + "' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id");
			//System.out.println("query.toString()>" + query.toString());
			List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				values = new String[7];
				Object[] object = (Object[]) dataList.get(0);
				values[0] = getValueWithNullCheck(object[0]);
				values[1] = getValueWithNullCheck(object[1]);
				values[2] = getValueWithNullCheck(object[2]);
				values[3] = getValueWithNullCheck(object[3]);
				values[4] = getValueWithNullCheck(object[4]);
				values[5] = getValueWithNullCheck(object[5]);
				values[6] = getValueWithNullCheck(object[6]);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	
	public String getNextEscMap()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (id!=null && !"".equalsIgnoreCase(id))
				{
					jsonArr = new JSONArray();
					StringBuilder query = new StringBuilder();
					query.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
					query.append(" WHERE moduleName='CRF' AND work_status!=1 ");
					if(id!=null && !id.equalsIgnoreCase(""))
					{
						query.append(" AND cc.id NOT IN(" + id + ")");
					}
					if(subDept!=null && !subDept.equalsIgnoreCase(""))
					{
						query.append(" AND forDept_id='"+subDept+"' ");
					}
					query.append(" ORDER BY empName ASC");
					//System.out.println("next::::::"+query);
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && data2.size() > 0)
					{
						JSONObject formDetailsJson = new JSONObject();
						Object[] object = null;
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
								formDetailsJson = new JSONObject();
								formDetailsJson.put("ID", object[0].toString());
								formDetailsJson.put("NAME", object[1].toString());
								jsonArr.add(formDetailsJson);
							}
						}
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
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
			return LOGIN;
		}
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public LinkedHashMap<String, String> getServiceDeptMap()
	{
		return serviceDeptMap;
	}

	public void setServiceDeptMap(LinkedHashMap<String, String> serviceDeptMap)
	{
		this.serviceDeptMap = serviceDeptMap;
	}

	public String getL1()
	{
		return l1;
	}

	public void setL1(String l1)
	{
		this.l1 = l1;
	}

	public String getL2()
	{
		return l2;
	}

	public void setL2(String l2)
	{
		this.l2 = l2;
	}

	public String getL3()
	{
		return l3;
	}

	public void setL3(String l3)
	{
		this.l3 = l3;
	}

	public String getL4()
	{
		return l4;
	}

	public void setL4(String l4)
	{
		this.l4 = l4;
	}

	public String getL5()
	{
		return l5;
	}

	public void setL5(String l5)
	{
		this.l5 = l5;
	}

	public String getDiv()
	{
		return div;
	}

	public void setDiv(String div)
	{
		this.div = div;
	}

	public String getsId()
	{
		return sId;
	}

	public void setsId(String sId)
	{
		this.sId = sId;
	}

	public String getsName()
	{
		return sName;
	}

	public void setsName(String sName)
	{
		this.sName = sName;
	}

	public String getnId()
	{
		return nId;
	}

	public void setnId(String nId)
	{
		this.nId = nId;
	}

	public String getnName()
	{
		return nName;
	}

	public void setnName(String nName)
	{
		this.nName = nName;
	}

	public Map<String, String> getEscEmpNextLevel()
	{
		return escEmpNextLevel;
	}

	public void setEscEmpNextLevel(Map<String, String> escEmpNextLevel)
	{
		this.escEmpNextLevel = escEmpNextLevel;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}
	
	public String getTat2()
	{
		return tat2;
	}

	public void setTat2(String tat2)
	{
		this.tat2 = tat2;
	}

	public String getTat3()
	{
		return tat3;
	}

	public void setTat3(String tat3)
	{
		this.tat3 = tat3;
	}

	public String getTat4()
	{
		return tat4;
	}

	public void setTat4(String tat4)
	{
		this.tat4 = tat4;
	}

	public String getTat5()
	{
		return tat5;
	}

	public void setTat5(String tat5)
	{
		this.tat5 = tat5;
	}

	public String getTat6()
	{
		return tat6;
	}

	public void setTat6(String tat6)
	{
		this.tat6 = tat6;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getEscalation()
	{
		return escalation;
	}

	public void setEscalation(String escalation)
	{
		this.escalation = escalation;
	}

	public Map<String, String> getEmplMap()
	{
		return emplMap;
	}

	public void setEmplMap(Map<String, String> emplMap)
	{
		this.emplMap = emplMap;
	}

	public Map<String, String> getEscalationMap()
	{
		return escalationMap;
	}

	public void setEscalationMap(Map<String, String> escalationMap)
	{
		this.escalationMap = escalationMap;
	}

	public Map<String, String> getEscL1Emp()
	{
		return escL1Emp;
	}

	public void setEscL1Emp(Map<String, String> escL1Emp)
	{
		this.escL1Emp = escL1Emp;
	}

	public Map<String, String> getEscContMap1()
	{
		return escContMap1;
	}

	public void setEscContMap1(Map<String, String> escContMap1)
	{
		this.escContMap1 = escContMap1;
	}

	public Map<String, String> getEscContMap2()
	{
		return escContMap2;
	}

	public void setEscContMap2(Map<String, String> escContMap2)
	{
		this.escContMap2 = escContMap2;
	}

	public Map<String, String> getEscContMap3()
	{
		return escContMap3;
	}

	public void setEscContMap3(Map<String, String> escContMap3)
	{
		this.escContMap3 = escContMap3;
	}

	public Map<String, String> getEscContMap4()
	{
		return escContMap4;
	}

	public void setEscContMap4(Map<String, String> escContMap4)
	{
		this.escContMap4 = escContMap4;
	}

	public String getSelectedRemind2Cont()
	{
		return selectedRemind2Cont;
	}

	public void setSelectedRemind2Cont(String selectedRemind2Cont)
	{
		this.selectedRemind2Cont = selectedRemind2Cont;
	}

	public String getSelectedEsc1Cont()
	{
		return selectedEsc1Cont;
	}

	public void setSelectedEsc1Cont(String selectedEsc1Cont)
	{
		this.selectedEsc1Cont = selectedEsc1Cont;
	}

	public String getSelectedEsc2Cont()
	{
		return selectedEsc2Cont;
	}

	public void setSelectedEsc2Cont(String selectedEsc2Cont)
	{
		this.selectedEsc2Cont = selectedEsc2Cont;
	}

	public String getSelectedEsc3Cont()
	{
		return selectedEsc3Cont;
	}

	public void setSelectedEsc3Cont(String selectedEsc3Cont)
	{
		this.selectedEsc3Cont = selectedEsc3Cont;
	}

	public String getSelectedEsc4Cont()
	{
		return selectedEsc4Cont;
	}

	public void setSelectedEsc4Cont(String selectedEsc4Cont)
	{
		this.selectedEsc4Cont = selectedEsc4Cont;
	}

	public String getSubDept()
	{
		return subDept;
	}

	public void setSubDept(String subDept)
	{
		this.subDept = subDept;
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

	public String getFirstEsc() {
		return firstEsc;
	}

	public void setFirstEsc(String firstEsc) {
		this.firstEsc = firstEsc;
	}

	public String getFirstEscFlag() {
		return firstEscFlag;
	}

	public void setFirstEscFlag(String firstEscFlag) {
		this.firstEscFlag = firstEscFlag;
	}


}
