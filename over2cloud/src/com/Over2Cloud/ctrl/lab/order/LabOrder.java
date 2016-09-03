package com.Over2Cloud.ctrl.lab.order;

import java.math.BigInteger;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.order.pharmacy.DashboardPojo;

@SuppressWarnings("serial")
public class LabOrder extends GridPropertyBean
{

	private List<Object> viewList;
	static final Log log = LogFactory.getLog(LabOrder.class);
	List<LabPojo> data_list = null;
	List<LabPojo> history_list = null;
	CommonOperInterface coi = new CommonConFactory().createInterface();
	public Map<String, String> locationMap;
	public Map<String, String> nursingMap;
	public Map<String, String> specialityMap;
	public Map<String, String> admittungMap;
	public Map<String, String> labNameMap;
	
	private String sdate, edate, stime, etime, speciality, location, specimenNo, contact, addDoctor, ordDoctor, bedNo, nursingUnit, uhid, orderAt, orderDate, orderTime, specCol, specReg, specDis, labName, sts;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private HttpServletRequest request;
	private JSONArray jsonArr = null;
	List<LabPojo>esc_list=null;

	public String status;

	public String viewHistoryDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				returnResult = SUCCESS;
				fetchHistoryDetailsData();
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

	private void fetchHistoryDetailsData()
	{
		try
		{
			String date="",time="";
			String dateUS="", timeUS="";
			LabPojo DP = null;
			LabPojo HP = null;
			LabPojo HPB4 = null;
			List data = null;
			if (orderDate != null)
			{
				setOrderAt(orderDate);

			}
			data_list = new ArrayList<LabPojo>();
			history_list = new ArrayList<LabPojo>();
			/*
			 * StringBuilder queryTemp = new StringBuilder("");
			 * queryTemp.append(
			 * " select drm.SERVICE_NAME,drm.LabName  from dreamsol_ip_cr_tst_vw as drm "
			 * ); queryTemp.append(" where drm.SPECIMEN_NO="+id); data =
			 * coi.executeAllSelectQuery(queryTemp.toString(), connectionSpace);
			 * if (data != null && data.size()>0) { for (Iterator iterator =
			 * data.iterator(); iterator.hasNext();) { Object[] object =
			 * (Object[]) iterator.next(); DP = new LabPojo();
			 * DP.setServiceName(checkNull(object[0]));
			 * if(checkNull(object[1]).equalsIgnoreCase("HEM"))
			 * DP.setTestUnit("Hematology"); else
			 * if(checkNull(object[1]).equalsIgnoreCase("BIO"))
			 * DP.setTestUnit("Biochemistry"); // else
			 * if(checkNull(object[1]).equalsIgnoreCase("BTS")) //
			 * DP.setTestUnit("Hematology"); // else
			 * if(checkNull(object[1]).equalsIgnoreCase("HISP")) //
			 * DP.setTestUnit("Hematology"); else
			 * if(checkNull(object[1]).equalsIgnoreCase("MIC"))
			 * DP.setTestUnit("Microbiology"); else
			 * DP.setTestUnit(checkNull(object[1])); data_list.add(DP); } }
			 * orderAt
			 */
			
			System.out.println("orderAt>>>>>>>>>>>>>>>>>          "+orderAt);
			HPB4 = new LabPojo();
			//HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specCol.split(" ")[0]) );
			if(orderAt.split("  ")[0]!=null && orderAt.split("  ")[0].split("-")[0].length()>2  )
			{
				HPB4.setActionDate(DateUtil.convertDateToIndianFormat(orderAt.split("  ")[0]));
				dateUS = DateUtil.convertDateToIndianFormat(orderAt.split("  ")[0]);
			}
			else
			{
				HPB4.setActionDate(orderAt.split("  ")[0]);
				dateUS = orderAt.split("  ")[0];
			}
			HPB4.setActionTime(orderAt.split("  ")[1].toString());
			timeUS = orderAt.split("  ")[1].toString();
			HPB4.setStatus("Order At");
			HPB4.setLevel("Level 1");
			HPB4.setTime_difference("-:-");
			history_list.add(HPB4);
			date = dateUS;
			time = timeUS;
			
			
			HPB4 = new LabPojo();
			//HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specCol.split(" ")[0]) );
			if(specCol.split(" ")[0]!=null && specCol.split(" ")[0].split("-")[0].length()>2  )
			{
				HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specCol.split(" ")[0]));
				dateUS = DateUtil.convertDateToIndianFormat(specCol.split(" ")[0]);
				
			}
			else
			{
				HPB4.setActionDate(specCol.split(" ")[0]);
				dateUS = specCol.split(" ")[0];
			}
			HPB4.setActionTime(specCol.split(" ")[1].toString());
			timeUS = specCol.split(" ")[1].toString();
			HPB4.setStatus("Specimen Collected");
			HPB4.setLevel("Level 1");
			System.out.println("date "+date+" time "+time);
			if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") )
			{
				HPB4.setTime_difference("-:-");
			}
			else
			{
				HPB4.setTime_difference(DateUtil.time_difference(date, time, checkNull(dateUS), checkNull(timeUS)));
				
			}
			date = dateUS;
			time = timeUS;
			history_list.add(HPB4);
			
			
			HPB4 = new LabPojo();
			//HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specDis.split(" ")[0]));
			if(specDis.split(" ")[0]!=null && specDis.split(" ")[0].split("-")[0].length()>2  )
			{
				HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specDis.split(" ")[0]));
				dateUS = DateUtil.convertDateToIndianFormat(specDis.split(" ")[0]);
			}
			else
			{
				HPB4.setActionDate(specDis.split(" ")[0]);
				dateUS = specDis.split(" ")[0];
			}
			HPB4.setActionTime(specDis.split(" ")[1].toString());
			timeUS = specDis.split(" ")[1].toString();
			HPB4.setStatus("Specimen Dispatch");
			HPB4.setLevel("Level 1");
			if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") )
			{
				HPB4.setTime_difference("-:-");
			}
			else
			{
				HPB4.setTime_difference(DateUtil.time_difference(date, time, checkNull(dateUS), checkNull(timeUS)));
				//HPB4.setTime_difference(DateUtil.time_difference(date, time, checkNull(DateUtil.convertDateToUSFormat(specDis.split(" ")[0].toString())), checkNull(specDis.split(" ")[1])));
				
			}
			date = dateUS;
			time = timeUS;
			history_list.add(HPB4);
			/*
			 * if(object[0]!=null &&checkNull(object[0]).split("-")[0].length()>2  )
						{
							HP.setAction_date(DateUtil.convertDateToIndianFormat(checkNull(object[0]))+", "+checkNull(object[1]));
						}
						else
						{
							HP.setAction_date(checkNull(object[0])+", "+checkNull(object[1]));
						}
			 * 
			 */
			
			HPB4 = new LabPojo();
			//HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specReg.split(" ")[0]));
			if(specReg.split(" ")[0]!=null && specReg.split(" ")[0].split("-")[0].length()>2  )
			{
				HPB4.setActionDate(DateUtil.convertDateToIndianFormat(specReg.split(" ")[0]));
				dateUS = DateUtil.convertDateToIndianFormat(specReg.split(" ")[0]);
			}
			else
			{
				HPB4.setActionDate(specReg.split(" ")[0]);
				dateUS = specReg.split(" ")[0];
			}
			HPB4.setActionTime(specReg.split(" ")[1].toString());
			HPB4.setStatus("Specimen Registered");
			HPB4.setLevel("Level 1");
			if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") )
			{
				HPB4.setTime_difference("-:-");
			}
			else
			{
				HPB4.setTime_difference(DateUtil.time_difference(date, time, checkNull(dateUS), checkNull(timeUS)));
				//HPB4.setTime_difference(DateUtil.time_difference(date, time, checkNull(DateUtil.convertDateToUSFormat(specDis.split(" ")[0].toString())), checkNull(specDis.split(" ")[1])));
				
			}
			history_list.add(HPB4);
			
			date = dateUS;
			time = timeUS;
			
			CharSequence str1="Escalate";
			StringBuilder query = new StringBuilder("");
			query.append(" select his.action_date,his.action_time,his.status,his.level,his.dream_view_id from lab_order_history as his  ");
			query.append(" where his.specimen_no=" + id);
			query.append(" and status != 'Registered/Specimen Received/Patient Arrived' and status != 'Registered' and status not like '%Escalate%'");
			query.append(" group by his.status ");
			query.append(" order by his.action_date , his.action_time asc ");
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[2]!=null && !object[2].toString().contains(str1))
					{
						HP = new LabPojo();
						HP.setActionDate(checkNull(object[0]));
						HP.setActionTime(checkNull(object[1]));
						HP.setStatus(checkNull(object[2]));
						HP.setLevel(checkNull(object[3]));
						HP.setHistory_id(checkNull(object[4]));
						System.out.println("date "+date+" time "+time);
						if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") || checkNull(object[2]).equalsIgnoreCase("Transfer Request Initiated"))
						{
							HP.setTime_difference("-:-");
						}
						else
						{
							HP.setTime_difference(DateUtil.time_difference(DateUtil.convertDateToUSFormat(date), time, checkNull(DateUtil.convertDateToUSFormat(object[0].toString())), checkNull(object[1])));
							
						}
						
						if(object[0].toString()!=null && object[0].toString().split("-")[0].length()>2  )
						{
							dateUS = DateUtil.convertDateToIndianFormat(specDis.split(" ")[0]);
						}
						else
						{
							dateUS = specDis.split(" ")[0];
						}
						date=dateUS;
						time=checkNull(object[1]);
						
						
						history_list.add(HP);
					}
					
				}

			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}

	}

	public String viewLabOrderHeader()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				returnResult = SUCCESS;
				fetchFilterspeciality();
				fetchFilterAdmitting();
				fetchFilterLocation();
				fetchFilterNurshingUnit();
				fetchLabName();
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

	public String fetchFilterspeciality()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		specialityMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				if (sdate == null && edate == null)
				{
					sdate = DateUtil.getCurrentDateIndianFormat();
					edate = DateUtil.getCurrentDateIndianFormat();
				}
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct ORDERING_DOCTOR_SPECIALTY as id, ORDERING_DOCTOR_SPECIALTY as name from dreamsol_ip_cr_tst_vw ");
				if (sdate != null && edate != null)
				{
					qry.append(" where ORD_DATE between '" + DateUtil.convertDateToUSFormat(sdate) + "' and '" + DateUtil.convertDateToUSFormat(edate) + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								qry.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				qry.append(" order by  ORDERING_DOCTOR_SPECIALTY ");
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					if (data != null && data.size() > 0)
					{
						for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								specialityMap.put("'" + object[0].toString() + "'", object[1].toString());
							}
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
	
	
	public String fetchLabName()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		labNameMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				if (sdate == null && edate == null)
				{
					sdate = DateUtil.getCurrentDateIndianFormat();
					edate = DateUtil.getCurrentDateIndianFormat();
				}
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct LabName as id, LabName as name from dreamsol_ip_cr_tst_vw ");
				if (sdate != null && edate != null)
				{
					qry.append(" where ORD_DATE between '" + DateUtil.convertDateToUSFormat(sdate) + "' and '" + DateUtil.convertDateToUSFormat(edate) + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								qry.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				qry.append(" order by  LabName ");
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					if (data != null && data.size() > 0)
					{
						for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								
								
									if(object[1].toString().equalsIgnoreCase("BIO"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "BIOCHEMISTRY");
										//obdata[k] = "BIOCHEMISTRY";
									}
									if(object[1].toString().equalsIgnoreCase("HEM"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "HEMATOLOGY");
									}
									if(object[1].toString().equalsIgnoreCase("BLDB"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "BLOOD BANK");
									}
									if(object[1].toString().equalsIgnoreCase("IMMU"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "MOLECULAR GEN and IMMUNOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("MOLB"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "MOLECULAR BIOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("MIC"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "MICROBIOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("CLPA"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "CLINICAL PATHOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("HISP"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "HISTOPATHOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("BTS"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "BLOOD TRANSFUSION SERVICES");
									}
									
									
									if(object[1].toString().equalsIgnoreCase("OUTS"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "OUTSOURCED LAB");
									}
										
									if(object[1].toString().equalsIgnoreCase("MCB"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "MICROBIOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("SAN"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "Sandoor Labs");
									}
									
									if(object[1].toString().equalsIgnoreCase("CYTP"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "CYTOPATHOLOGY");
									}
									
									if(object[1].toString().equalsIgnoreCase("SCL"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "CLINICAL PATHOLOGY");
									}
										
									if(object[1].toString().equalsIgnoreCase("LAL"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "DR.LAL PATH LABS");
									}
									
									if(object[1].toString().equalsIgnoreCase("ONCQ"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "QUEST DIAGNOSTIC");
									}
									
									if(object[1].toString().equalsIgnoreCase("ONQL"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "ONQUEST LAB");
									}
									
									if(object[1].toString().equalsIgnoreCase("MYCO"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "MYCOBACTERIOLOGY");
									}
									
									
									if(object[1].toString().equalsIgnoreCase("CDS"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "CORE DIAGNOSTICS");
									}
									
									
									if(object[1].toString().equalsIgnoreCase("PBS"))
									{
										labNameMap.put("'" + object[0].toString() + "'", "Genomic Testing Lab");
									}
							}
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

	public String fetchFilterAdmitting()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		admittungMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				if (sdate == null && edate == null)
				{
					sdate = DateUtil.getCurrentDateIndianFormat();
					edate = DateUtil.getCurrentDateIndianFormat();
				}

				StringBuilder qry = new StringBuilder();
				qry.append("select distinct ADMITING_DOCTOR as id, ADMITING_DOCTOR as name from dreamsol_ip_cr_tst_vw  ");
				if (sdate != null && edate != null)
				{
					qry.append(" where ORD_DATE between '" + DateUtil.convertDateToUSFormat(sdate) + "' and '" + DateUtil.convertDateToUSFormat(edate) + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								qry.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				qry.append(" order by  ADMITING_DOCTOR  asc ");
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					if (data != null && data.size() > 0)
					{
						for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								admittungMap.put("'" + object[0].toString() + "'", object[1].toString());
							}
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

	public String fetchFilterLocation()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		locationMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				if (sdate == null && edate == null)
				{
					sdate = DateUtil.getCurrentDateIndianFormat();
					edate = DateUtil.getCurrentDateIndianFormat();
				}
				StringBuilder qry = new StringBuilder();
				qry.append(" select distinct WARD as id, WARD as name from dreamsol_ip_cr_tst_vw  ");
				if (sdate != null && edate != null)
				{
					qry.append(" where ORD_DATE between '" + DateUtil.convertDateToUSFormat(sdate) + "' and '" + DateUtil.convertDateToUSFormat(edate) + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								qry.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				qry.append(" order by WARD asc ");
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					if (data != null && data.size() > 0)
					{
						for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								locationMap.put("'" + object[0].toString() + "'", object[1].toString());
							}
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

	public String fetchFilterNurshingUnit()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		nursingMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				if (sdate == null && edate == null)
				{
					sdate = DateUtil.getCurrentDateIndianFormat();
					edate = DateUtil.getCurrentDateIndianFormat();
				}
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct WARD as id, WARD as name from dreamsol_ip_cr_tst_vw ");
				if (sdate != null && edate != null)
				{
					qry.append(" where ORD_DATE between '" + DateUtil.convertDateToUSFormat(sdate) + "' and '" + DateUtil.convertDateToUSFormat(edate) + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								qry.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				qry.append(" order by WARD asc ");
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					if (data != null && data.size() > 0)
					{
						for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								nursingMap.put("'" + object[0].toString() + "'", object[1].toString());
							}
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

	String checkNull(Object obj)
	{
		String returnResult = "NA";
		if (obj != null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1"))
		{
			returnResult = obj.toString().trim();
		}
		return returnResult;
	}

	public String viewLabOrderDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("stsff    "+sts);
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewProps();
				System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
				returnResult = SUCCESS;
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

	void setMasterViewProps()
	{

		viewPageColumns = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPageColumns.add(gpv);

		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_lab_order_configuration", accountID, connectionSpace, "", 0, "table_name", "lab_order_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter(gdp.getFormatter());
					gpv.setWidth(gdp.getWidth());
					viewPageColumns.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	/*public String viewLabOrderData()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			StringBuilder query = new StringBuilder("");
			query.append(" select count(*) from dreamsol_ip_cr_tst_vw ");
			List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null)
			{
				BigInteger count = new BigInteger("3");
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				if (to > getRecords())
					to = getRecords();
				data = null;
				query.setLength(0);
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from dreamsol_ip_cr_tst_vw as lo ");

				List fieldNames = Configuration.getColomnList("mapped_lab_order_configuration", accountID, connectionSpace, "lab_order_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						queryTemp.append("lo." + obdata.toString() + " ,");
					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(queryEnd.toString());
				query.append(" where lo.id <> 0 ");

				if (sdate != null && edate != null)
				{
					if(sdate.split("-")[0].length()>2  ){
						
					}
					else{
						sdate = DateUtil.convertDateToUSFormat(sdate);
					}
					if(edate.split("-")[0].length()>2  ){
						
					}
					else{
						edate = DateUtil.convertDateToUSFormat(edate);
					}
					
					query.append(" and lo.ORD_DATE between '" + sdate + "' and '" + edate + "' ");
				}

				if (stime != null && etime != null && !stime.trim().isEmpty() && !etime.trim().isEmpty())
				{
					
					query.append(" and lo.ORD_TIME between '" + stime + "' and '" + etime + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								query.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				if (nursingUnit != null && !nursingUnit.equalsIgnoreCase("-1") && !nursingUnit.equalsIgnoreCase("null"))
					query.append(" and lo.WARD In (" + nursingUnit + ") ");
				// if(location !=null && !location.equalsIgnoreCase("-1") &&
				// !location.equalsIgnoreCase("null"))
				// query.append(" and divw.floor In("+location+") ");
				if (labName != null && !labName.equalsIgnoreCase("-1") && !labName.equalsIgnoreCase("null"))
					query.append(" and LabName In(" + labName + ") ");
				if (addDoctor != null && !addDoctor.equalsIgnoreCase("-1") && !addDoctor.equalsIgnoreCase("null"))
					query.append(" and lo.ADMITING_DOCTOR In(" + addDoctor + ") ");
				if (speciality != null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null"))
					query.append(" and lo.ORDERING_DOCTOR_SPECIALTY In(" + speciality + ") ");
				query.append(" group by SPECIMEN_NO ");
				query.append(" order by lo.Order_Date desc ");
				System.out.println("query >>>  "+query);
				data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				String unloack = "UNLOCK TABLES";
				coi.executeAllUpdateQuery(unloack, connectionSpace);

				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								}
								else
								{
									if (fieldNames.get(k).toString().contains("ORD_DATE"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(checkNull(obdata[k]).toString()) + "  " + checkNull(obdata[k + 1]).toString();
									}
									if (fieldNames.get(k).toString().contains("LabName"))
									{
										
										if(obdata[k].toString().equalsIgnoreCase("BIO"))
										{
											obdata[k] = "BIOCHEMISTRY";
										}
										if(obdata[k].toString().equalsIgnoreCase("HEM"))
										{
											obdata[k] = "HEMATOLOGY";
										}
										if(obdata[k].toString().equalsIgnoreCase("BLDB"))
										{
											obdata[k] = "BLOOD BANK";
										}
										if(obdata[k].toString().equalsIgnoreCase("IMMU"))
										{
											obdata[k] = "MOLECULAR GEN and IMMUNOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("MOLB"))
										{
											obdata[k] = "MOLECULAR BIOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("MIC"))
										{
											obdata[k] = "MICROBIOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("CLPA"))
										{
											obdata[k] = "CLINICAL PATHOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("HISP"))
										{
											obdata[k] = "HISTOPATHOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("BTS"))
										{
											obdata[k] = "BLOOD TRANSFUSION SERVICES";
										}
										
										
										if(obdata[k].toString().equalsIgnoreCase("OUTS"))
										{
											obdata[k] = "OUTSOURCED LAB";
										}
											
										if(obdata[k].toString().equalsIgnoreCase("MCB"))
										{
											obdata[k] = "MICROBIOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("SAN"))
										{
											obdata[k] = "Sandoor Labs";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("CYTP"))
										{
											obdata[k] = "CYTOPATHOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("SCL"))
										{
											obdata[k] = "CLINICAL PATHOLOGY";
										}
											
										if(obdata[k].toString().equalsIgnoreCase("LAL"))
										{
											obdata[k] = "DR.LAL PATH LABS";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("ONCQ"))
										{
											obdata[k] = "QUEST DIAGNOSTIC";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("ONQL"))
										{
											obdata[k] = "ONQUEST LAB";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("MYCO"))
										{
											obdata[k] = "MYCOBACTERIOLOGY";
										}
										
										
										if(obdata[k].toString().equalsIgnoreCase("CDS"))
										{
											obdata[k] = "CORE DIAGNOSTICS";
										}
										
										
										if(obdata[k].toString().equalsIgnoreCase("PBS"))
										{
											obdata[k] = "Genomic Testing Lab";
										}
										
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
						}
						Listhb.add(one);
					}
					setViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					data = null;
					return "success";
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}*/
	
	public String viewLabOrderData()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			StringBuilder query = new StringBuilder("");
			query.append(" select count(*) from dreamsol_ip_cr_tst_vw ");
			List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null)
			{
				BigInteger count = new BigInteger("3");
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				if (to > getRecords())
					to = getRecords();
				data = null;
				query.setLength(0);
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from dreamsol_ip_cr_tst_vw as lo ");

				List fieldNames = Configuration.getColomnList("mapped_lab_order_configuration", accountID, connectionSpace, "lab_order_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				List<Object> listData1 = new ArrayList<Object>();
				List<Object> listData2 = new ArrayList<Object>();
				List<Object> listData3 = new ArrayList<Object>();
				List<Object> listData4 = new ArrayList<Object>();
				List<Object> listData5 = new ArrayList<Object>();
				List<Object> listData6 = new ArrayList<Object>();
				List<Object> listData7 = new ArrayList<Object>();
				List<Object> listData8 = new ArrayList<Object>();
				
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						queryTemp.append("lo." + obdata.toString() + " ,");
					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(queryEnd.toString());
				query.append(" where lo.id <> 0   ");

				if (sdate != null && edate != null)
				{
					if(sdate.split("-")[0].length()>2  ){
						
					}
					else{
						sdate = DateUtil.convertDateToUSFormat(sdate);
					}
					if(edate.split("-")[0].length()>2  ){
						
					}
					else{
						edate = DateUtil.convertDateToUSFormat(edate);
					}
					
					query.append(" and lo.ORD_DATE between '" + sdate + "' and '" + edate + "' ");
				}

				if (stime != null && etime != null && !stime.trim().isEmpty() && !etime.trim().isEmpty())
				{
					
					query.append(" and lo.ORD_TIME between '" + stime + "' and '" + etime + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								query.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				if (nursingUnit != null && !nursingUnit.equalsIgnoreCase("-1") && !nursingUnit.equalsIgnoreCase("null"))
					query.append(" and lo.WARD In (" + nursingUnit + ") ");
				if (sts != null && !sts.equalsIgnoreCase("-1") && !sts.equalsIgnoreCase("null"))
					query.append(" and lo.LONG_DESC In (" + isCommonSeparated(sts) + ") ");
				// if(location !=null && !location.equalsIgnoreCase("-1") &&
				// !location.equalsIgnoreCase("null"))
				// query.append(" and divw.floor In("+location+") ");
				if (labName != null && !labName.equalsIgnoreCase("-1") && !labName.equalsIgnoreCase("null"))
					query.append(" and LabName In(" + labName + ") ");
				if (addDoctor != null && !addDoctor.equalsIgnoreCase("-1") && !addDoctor.equalsIgnoreCase("null"))
					query.append(" and lo.ADMITING_DOCTOR In(" + addDoctor + ") ");
				if (speciality != null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null"))
					query.append(" and lo.ORDERING_DOCTOR_SPECIALTY In(" + speciality + ") ");
				query.append(" group by SPECIMEN_NO ");
				query.append(" order by lo.Order_Date asc ");
				System.out.println("query >>>  "+query);
				data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				String unloack = "UNLOCK TABLES";
				coi.executeAllUpdateQuery(unloack, connectionSpace);

				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								}
								else
								{
									if (fieldNames.get(k).toString().contains("ORD_DATE"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(checkNull(obdata[k]).toString()) + "  " + checkNull(obdata[k + 1]).toString();
									}
									
									if (fieldNames.get(k).toString().contains("LabName"))
									{
										
										if(obdata[k].toString().equalsIgnoreCase("BIO"))
										{
											obdata[k] = "BIOCHEMISTRY";
										}
										if(obdata[k].toString().equalsIgnoreCase("HEM"))
										{
											obdata[k] = "HEMATOLOGY";
										}
										if(obdata[k].toString().equalsIgnoreCase("BLDB"))
										{
											obdata[k] = "BLOOD BANK";
										}
										if(obdata[k].toString().equalsIgnoreCase("IMMU"))
										{
											obdata[k] = "MOLECULAR GEN and IMMUNOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("MOLB"))
										{
											obdata[k] = "MOLECULAR BIOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("MIC"))
										{
											obdata[k] = "MICROBIOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("CLPA"))
										{
											obdata[k] = "CLINICAL PATHOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("HISP"))
										{
											obdata[k] = "HISTOPATHOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("BTS"))
										{
											obdata[k] = "BLOOD TRANSFUSION SERVICES";
										}
										
										
										if(obdata[k].toString().equalsIgnoreCase("OUTS"))
										{
											obdata[k] = "OUTSOURCED LAB";
										}
											
										if(obdata[k].toString().equalsIgnoreCase("MCB"))
										{
											obdata[k] = "MICROBIOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("SAN"))
										{
											obdata[k] = "Sandoor Labs";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("CYTP"))
										{
											obdata[k] = "CYTOPATHOLOGY";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("SCL"))
										{
											obdata[k] = "CLINICAL PATHOLOGY";
										}
											
										if(obdata[k].toString().equalsIgnoreCase("LAL"))
										{
											obdata[k] = "DR.LAL PATH LABS";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("ONCQ"))
										{
											obdata[k] = "QUEST DIAGNOSTIC";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("ONQL"))
										{
											obdata[k] = "ONQUEST LAB";
										}
										
										if(obdata[k].toString().equalsIgnoreCase("MYCO"))
										{
											obdata[k] = "MYCOBACTERIOLOGY";
										}
										
										
										if(obdata[k].toString().equalsIgnoreCase("CDS"))
										{
											obdata[k] = "CORE DIAGNOSTICS";
										}
										
										
										if(obdata[k].toString().equalsIgnoreCase("PBS"))
										{
											obdata[k] = "Genomic Testing Lab";
										}
										
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());
									if (fieldNames.get(k).toString().contains("escalation_date"))
									{
										one.put("escalation_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), obdata[18].toString(), obdata[19].toString()));
										//obdata[k] = DateUtil.convertDateToIndianFormat(checkNull(obdata[k]).toString()) + "  " + checkNull(obdata[k + 1]).toString();
									}
								}
							}
						}
						if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("Registered"))
						{
							listData1.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("In Process"))
						{
							listData2.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("In Progress"))
						{
							listData3.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("On Hold (By Department)"))
						{
							listData4.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("Rejected"))
						{
							listData5.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("Resulted - Preliminary"))
						{
							listData6.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("Resulted - Partial"))
						{
							listData7.add(one);
						}
						else if(obdata[12]!=null && obdata[12].toString().equalsIgnoreCase("Resulted - Complete"))
						{
							listData8.add(one);
						}
						else
						{
							Listhb.add(one);
						}
						
					
						
						
					}
					listData1.addAll(listData2);
					listData1.addAll(listData3);
					listData1.addAll(listData4);
					listData1.addAll(listData5);
					listData1.addAll(listData6);
					listData1.addAll(listData7);
					listData1.addAll(listData8);
					listData1.addAll(Listhb);
					System.out.println(listData1.size());
					System.out.println(listData2.size());
					System.out.println(listData3.size());
					System.out.println(listData4.size());
					System.out.println(listData5.size());
					System.out.println(listData6.size());
					System.out.println(Listhb.size());
					
					System.out.println("Total Size>>>>>>>>>>>>"+listData1.size());
					setViewList(listData1);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					data = null;
					return "success";
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}


	public String fetchCounterStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				int total=0;
				jsonArr = new JSONArray();
				List dataList;
				JSONObject obj = new JSONObject();
				StringBuilder query = new StringBuilder("");
				if (!ValidateSession.checkSession())
					return LOGIN;
				query.append(" select count(*),a.LONG_DESC from (SELECT count(*),lo.LONG_DESC FROM dreamsol_ip_cr_tst_vw as lo ");
				if (sdate != null && edate != null)
				{
					query.append(" where lo.ORD_DATE between '" + DateUtil.convertDateToUSFormat(sdate) + "' and '" + DateUtil.convertDateToUSFormat(edate) + "' ");
				}
				if (stime != null && etime != null && !stime.trim().isEmpty() && !etime.trim().isEmpty())
				{
					query.append(" and lo.ORD_TIME between '" + stime + "' and '" + etime + "' ");
				}
				String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("labOrderlocation_VIEW"))
						{
							String nursingUnit = fetchNursingUnitByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (nursingUnit != null && !nursingUnit.contains("All"))
								query.append(" and NURSING_UNIT_CODE In ( " + nursingUnit + " )");
						}

					}
				}
				if (nursingUnit != null && !nursingUnit.equalsIgnoreCase("-1") && !nursingUnit.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.WARD In (" + nursingUnit + ") ");
				// if(location !=null && !location.equalsIgnoreCase("-1") &&
				// !location.equalsIgnoreCase("null"))
				// query.append(" and divw.floor In("+location+") ");
				if (labName != null && !labName.equalsIgnoreCase("-1") && !labName.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.LabName In(" + labName + ") ");
				if (addDoctor != null && !addDoctor.equalsIgnoreCase("-1") && !addDoctor.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.ADMITING_DOCTOR In(" + addDoctor + ") ");
				if (speciality != null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.ORDERING_DOCTOR_SPECIALTY In(" + speciality + ") ");
				query.append(" group by lo.SPECIMEN_NO ) as a group by a.LONG_DESC ");
				
				System.out.println(query);
				dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] object =null;
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						 object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							if(object[1].toString().equalsIgnoreCase("In Process"))
							{
								obj.put("InProcess", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Registered"))
							{
								obj.put("RRA", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Rejected"))
							{
								obj.put("Rejected", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Resulted - Complete"))
							{
								obj.put("Complete", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Resulted - Partial"))
							{
								obj.put("Partial", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("On Hold (By Department)"))
							{
								obj.put("Hold", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("In Progress"))
							{
								obj.put("Progress", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Resulted - Preliminary"))
							{
								obj.put("Preliminary", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							jsonArr.add(obj);
						}
					}
					System.out.println("total   "+total);
					obj.put("Total",total);
					jsonArr.add(obj);
				}
				/*if (dataList != null && !dataList.isEmpty())
				{
					JSONObject obj = new JSONObject();
					obj.put("Total", dataList.get(0));
					jsonArr.add(obj);
				}*/
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

	public List<Object> getViewList()
	{
		return viewList;
	}

	private String isCommonSeparated(String s)
	{
		if (s.contains(","))
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.split(",").length; i++)
			{
				sb.append("'" + s.split(",")[i] + "'");
				if (i < s.split(",").length - 1)
				{
					sb.append(",");
				}
			}
			return sb.toString();
		}
		else
		{
			return "'" + s + "'";
		}
	}

	public String[] fetchSubModuleRights(String userId)
	{
		String[] value = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount where name = '" + userId + "'", connectionSpace);
			if (adminRights != null && adminRights.size() > 0)
			{
				value = adminRights.get(0).toString().split("#");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}

	public String fetchNursingUnitByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		@SuppressWarnings("unused")
		String subDeptName = null;
		String temp = "";
		try
		{
			List dataList = cbt.executeAllSelectQuery("select cc.location from compliance_contacts as cc  where  cc.emp_id ='" + empId + "' and cc.work_status='0' and cc.moduleName='LabOrd' ", connection);

			if (dataList != null && dataList.size() > 0)
			{
				subDeptName = dataList.get(0).toString();
				for (Object s : dataList)
				{
					temp = temp + "'" + s.toString() + "'" + ",";
				}
				temp = temp.substring(0, temp.length() - 1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return temp;
	}
	
//Ecsalation Detail
	
	public String viewHistoryEscalationDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				returnResult = SUCCESS;
				escalationDetail();
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
	
	public void escalationDetail()
	{

		try
		{
			LabPojo DP = null;
			LabPojo HP = null;
			LabPojo HPB4 = null;
			List data = null;
			String statusFirst="";
			int i=0;
			System.out.println(status);
			CharSequence str="Escalate "+status;
			
			System.out.println(str.toString());
			StringBuilder query = new StringBuilder("");
			query.append(" select action_date,action_time, status, comment,level from lab_order_history   ");
			query.append("where dream_view_id='"+id+"'  order by id ");
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				esc_list = new ArrayList<LabPojo>();
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
						Object[] object = (Object[]) iterator.next();
						if(object[2]!=null && checkNull(object[2]).contains(str))
						{
							HP = new LabPojo();
							HP.setActionTime(checkNull(object[0])+", "+checkNull(object[1]));
							HP.setStatus(checkNull(object[2]));
							HP.setHistory_id(checkNull(object[3])); // history_id treat as comment here
							HP.setLevel(checkNull(object[4]));
							esc_list.add(HP);
						}
					
				}	
					
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}

	
		
	}
	

	public void setViewList(List<Object> viewList)
	{
		this.viewList = viewList;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Map<String, String> getSpecialityMap()
	{
		return specialityMap;
	}

	public void setSpecialityMap(Map<String, String> specialityMap)
	{
		this.specialityMap = specialityMap;
	}

	public Map<String, String> getLocationMap()
	{
		return locationMap;
	}

	public void setLocationMap(Map<String, String> locationMap)
	{
		this.locationMap = locationMap;
	}

	public String getSdate()
	{
		return sdate;
	}

	public void setSdate(String sdate)
	{
		this.sdate = sdate;
	}

	public String getEdate()
	{
		return edate;
	}

	public void setEdate(String edate)
	{
		this.edate = edate;
	}

	public String getStime()
	{
		return stime;
	}

	public void setStime(String stime)
	{
		this.stime = stime;
	}

	public String getEtime()
	{
		return etime;
	}

	public void setEtime(String etime)
	{
		this.etime = etime;
	}

	public String getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getSpecimenNo()
	{
		return specimenNo;
	}

	public void setSpecimenNo(String specimenNo)
	{
		this.specimenNo = specimenNo;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

	public String getAddDoctor()
	{
		return addDoctor;
	}

	public void setAddDoctor(String addDoctor)
	{
		this.addDoctor = addDoctor;
	}

	public String getOrdDoctor()
	{
		return ordDoctor;
	}

	public void setOrdDoctor(String ordDoctor)
	{
		this.ordDoctor = ordDoctor;
	}

	public String getBedNo()
	{
		return bedNo;
	}

	public void setBedNo(String bedNo)
	{
		this.bedNo = bedNo;
	}

	public String getNursingUnit()
	{
		return nursingUnit;
	}

	public void setNursingUnit(String nursingUnit)
	{
		this.nursingUnit = nursingUnit;
	}

	public String getUhid()
	{
		return uhid;
	}

	public void setUhid(String uhid)
	{
		this.uhid = uhid;
	}

	public List<LabPojo> getData_list()
	{
		return data_list;
	}

	public void setData_list(List<LabPojo> dataList)
	{
		data_list = dataList;
	}

	public Map<String, String> getNursingMap()
	{
		return nursingMap;
	}

	public void setNursingMap(Map<String, String> nursingMap)
	{
		this.nursingMap = nursingMap;
	}

	public Map<String, String> getAdmittungMap()
	{
		return admittungMap;
	}

	public void setAdmittungMap(Map<String, String> admittungMap)
	{
		this.admittungMap = admittungMap;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public List<LabPojo> getHistory_list()
	{
		return history_list;
	}

	public void setHistory_list(List<LabPojo> historyList)
	{
		history_list = historyList;
	}

	public String getOrderAt()
	{
		return orderAt;
	}

	public void setOrderAt(String orderAt)
	{
		this.orderAt = orderAt;
	}

	public String getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	public String getOrderTime()
	{
		return orderTime;
	}

	public void setOrderTime(String orderTime)
	{
		this.orderTime = orderTime;
	}

	public String getSpecCol() {
		return specCol;
	}

	public void setSpecCol(String specCol) {
		this.specCol = specCol;
	}

	public String getSpecReg() {
		return specReg;
	}

	public void setSpecReg(String specReg) {
		this.specReg = specReg;
	}

	public String getSpecDis() {
		return specDis;
	}

	public void setSpecDis(String specDis) {
		this.specDis = specDis;
	}

	public Map<String, String> getLabNameMap() {
		return labNameMap;
	}

	public void setLabNameMap(Map<String, String> labNameMap) {
		this.labNameMap = labNameMap;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public List<LabPojo> getEsc_list()
	{
		return esc_list;
	}

	public void setEsc_list(List<LabPojo> escList)
	{
		esc_list = escList;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getSts()
	{
		return sts;
	}

	public void setSts(String sts)
	{
		this.sts = sts;
	}

}