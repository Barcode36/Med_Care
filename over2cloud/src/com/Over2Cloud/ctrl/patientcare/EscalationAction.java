package com.Over2Cloud.ctrl.patientcare;

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

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class EscalationAction extends GridPropertyBean implements ServletRequestAware{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	HttpServletRequest request;
	private Map<String, String> escEmpNextLevel = null;
	private LinkedHashMap<String, String> serviceDeptMap;
	private List<Object> masterViewList;
	
	public String beforeConfigEscalationCPS(){
		

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				serviceDeptMap = new LinkedHashMap<String, String>();
				StringBuilder query = new StringBuilder("");
				query.append("SELECT DISTINCT dept.id, dept.deptName ");
				query.append("FROM department AS dept ");
				query.append("INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id ");
				query.append("INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id ");
				query.append("WHERE cc.moduleName='CPS' or dept.deptName='OCC'");
				List departmentlist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptMap.put(object1[0].toString(), object1[1].toString());
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
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

	
	// for auto create escalation table for corporate patient service 
	public String addEscConfig()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();

				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("escDept");
				ob1.setDatatype("int(10)");
				ob1.setConstraint("default NULL"); //
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escSubDept");
				ob1.setDatatype("int(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("service_name");
				ob1.setDatatype("varchar(100)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("escLevel");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("considerRoaster");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				ob1 = new TableColumes();
				
				ob1.setColumnname("escLevelL1L2");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("l1Tol2");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL2L3");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l2Tol3");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL3L4");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l3Tol4");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL4L5");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l4Tol5");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("escLevelL5L6");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("l5Tol6");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(50)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				// newly added column 
				ob1 = new TableColumes();
				ob1.setColumnname("module");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				


				cbt.createTable22("escalation_cps_detail", tableColume, connectionSpace);


				ob = new InsertDataTable();
				ob.setColName("module");
				ob.setDataName("CPS");
				insertData.add(ob);
				
				
				
				ob = new InsertDataTable();
				ob.setColName("escLevel");
				ob.setDataName(request.getParameter("escLevel"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("considerRoaster");
				ob.setDataName(request.getParameter("considerRoaster"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escLevelL1L2");
				ob.setDataName(request.getParameter("escLevelL1L2"));
				insertData.add(ob);

				if (request.getParameter("escLevelL1L2").equalsIgnoreCase("Customised"))
				{
					ob = new InsertDataTable();
					ob.setColName("l1Tol2");
					ob.setDataName(request.getParameter("l1Tol2"));
					insertData.add(ob);
				}
				
				ob = new InsertDataTable();
				ob.setColName("escLevelL2L3");
				ob.setDataName(request.getParameter("escLevelL2L3"));
				insertData.add(ob);

				if (request.getParameter("escLevelL2L3").equalsIgnoreCase("Customised"))
				{
					ob = new InsertDataTable();
					ob.setColName("l2Tol3");
					ob.setDataName(request.getParameter("l2Tol3"));
					insertData.add(ob);
				}
				

				ob = new InsertDataTable();
				ob.setColName("escLevelL3L4");
				ob.setDataName(request.getParameter("escLevelL3L4"));
				insertData.add(ob);

				if (request.getParameter("escLevelL3L4").equalsIgnoreCase("Customised"))
				{
					ob = new InsertDataTable();
					ob.setColName("l3Tol4");
					ob.setDataName(request.getParameter("l3Tol4"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("escLevelL4L5");
				ob.setDataName(request.getParameter("escLevelL4L5"));
				insertData.add(ob);

				if (request.getParameter("escLevelL4L5").equalsIgnoreCase("Customised"))
				{
					ob = new InsertDataTable();
					ob.setColName("l4Tol5");
					ob.setDataName(request.getParameter("l4Tol5"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("escLevelL5L6");
				ob.setDataName(request.getParameter("escLevelL5L6"));
				insertData.add(ob);

				if (request.getParameter("escLevelL5L6").equalsIgnoreCase("Customised"))
				{
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
				ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escDept");
				ob.setDataName(request.getParameter("escDept"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escSubDept");
				ob.setDataName(request.getParameter("escSubDept"));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("service_name");
				ob.setDataName(request.getParameter("service_name"));
				insertData.add(ob);
				
				cbt.insertIntoTable("escalation_cps_detail", insertData, connectionSpace);
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addActionMessage("Opps. There are some problem in data save.");
				return ERROR;

			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String viewEscDeptCPS()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb = new ArrayList<Object>();
				List dataList = null;
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				/*query.append("SELECT esc.id,dept.deptName,subdept.subdeptname,esc.considerRoaster,esc.escLevelL2L3,esc.l2Tol3,");
				query.append("esc.escLevelL3L4,esc.l3Tol4,esc.escLevelL4L5,esc.l4Tol5,esc.escLevelL5L6,esc.l5Tol6,esc.module "
						+ ", esc.escLevelL1L2,esc.l1Tol2 ");
				query.append(" FROM escalation_cps_detail AS esc ");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = esc.escSubDept ");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid ");*/
				
				query.append("SELECT esc.id,dept.deptName,subdept.subdeptname,esc.considerRoaster,esc.escLevelL2L3,esc.l2Tol3,");
				query.append("esc.escLevelL3L4,esc.l3Tol4,esc.escLevelL4L5,esc.l4Tol5,esc.escLevelL5L6,esc.l5Tol6,esc.module "
						+ ", esc.escLevelL1L2,esc.l1Tol2,esc.escSubDept,esc.service_name ");
				query.append(" FROM subdepartment AS subdept   ");
				query.append(" right JOIN escalation_cps_detail AS esc ON esc.escSubDept = subdept.id  ");
				query.append(" left JOIN department AS dept ON dept.id = esc.escDept ");
				//query.append(" group by subdept.subdeptname ");
				//System.out.println("####### "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						
						one.put("id", CUA.getValueWithNullCheck(object[0]));
						one.put("deptName", CUA.getValueWithNullCheck(object[1]));
						if(object[2]==null)
						{
							//System.out.println("object[2]object[2]object[2] "+object[15].toString());
							one.put("subdept", CUA.getValueWithNullCheck(object[15]));
						}
						else
						{
							one.put("subdept", CUA.getValueWithNullCheck(object[2]));
						}
						one.put("service_name", CUA.getValueWithNullCheck(object[16]));
						one.put("considerRoaster", CUA.getValueWithNullCheck(object[3]));
						one.put("escLevelL2L3", CUA.getValueWithNullCheck(object[4]));
						if (object[5] != null)
							one.put("escLevel2", object[5].toString());
						else
							one.put("escLevel2", "00:00");

						one.put("escLevelL3L4", CUA.getValueWithNullCheck(object[6]));

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

						if (object[12] != null)
							one.put("module", object[12].toString());
						else
							one.put("module", "NA");
						
						if (object[13] != null)
							one.put("escLevelL1L2", object[13].toString());
						else
							one.put("escLevelL1L2", "NA");
						
						if (object[14] != null)
							one.put("l1Tol2", object[14].toString());
						else
							one.put("l1Tol2", "00:00");
						
						
						
						
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
	
	// for edit escalation level
	public String editEscalationDept()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && Parmname != null && !Parmname.equalsIgnoreCase("tableName") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
						{
							if (Parmname.equalsIgnoreCase("escLevel1"))
							{
								Parmname = "l1Tol2";
							}
							if (Parmname.equalsIgnoreCase("escLevel2"))
							{
								Parmname = "l2Tol3";
							}
							else if (Parmname.equalsIgnoreCase("escLevel3"))
							{
								Parmname = "l3Tol4";
							}
							else if (Parmname.equalsIgnoreCase("escLevel4"))
							{
								Parmname = "l4Tol5";
							}
							else if (Parmname.equalsIgnoreCase("escLevel5"))
							{
								Parmname = "l5Tol6";
							}
							wherClause.put(Parmname, paramValue);
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("escalation_cps_detail", wherClause, condtnBlock, connectionSpace);
					returnResult = SUCCESS;
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					//cbt.executeAllUpdateQuery("update escalation_order_detail as eod set eod.status='Inactive' where id in ("+id+")", connectionSpace);
					cbt.deleteAllRecordForId("escalation_cps_detail", "id", condtIds.toString(), connectionSpace);
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
		
	}
	public Map<String, String> getEscEmpNextLevel()
	{
		return escEmpNextLevel;
	}

	public void setEscEmpNextLevel(Map<String, String> escEmpNextLevel)
	{
		this.escEmpNextLevel = escEmpNextLevel;
	}

	public LinkedHashMap<String, String> getServiceDeptMap() {
		return serviceDeptMap;
	}

	public void setServiceDeptMap(LinkedHashMap<String, String> serviceDeptMap) {
		this.serviceDeptMap = serviceDeptMap;
	}


	public List<Object> getMasterViewList() {
		return masterViewList;
	}


	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

}
