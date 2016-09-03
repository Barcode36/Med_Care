package com.Over2Cloud.ctrl.criticalPatient;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
public class CriticalActivityBoard extends GridPropertyBean implements ServletRequestAware
{
HttpServletRequest request;
private String toDate;
private String searchString;
private String fromDate;
private String level;
private String patient_status;
private String status;
private List<Object> viewCriticalData = null;
private JSONArray jsonArr;
private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
private String uhidStatus;


private FileInputStream excelStream;
private String nursingUnit; 
private String excelFileName;
private Map<String, String> columnMap;
private String[] columnNames; 
private String doc_name1;
private String patient_speciality;


public String beforeCriticalViewHeader()
{
	
	try{
		fromDate=(DateUtil.getCurrentDateUSFormat());
		toDate=(DateUtil.getCurrentDateUSFormat());
		return SUCCESS;
	}catch(Exception e)
	{
		e.printStackTrace();
		return ERROR;
	}
}

@SuppressWarnings("unchecked")
public String beforeReferralViewHeader()
{
	if (ValidateSession.checkSession())
	{
		try
		{
			fromDate=(DateUtil.getCurrentDateUSFormat());
			toDate=(DateUtil.getCurrentDateUSFormat());
		
			/*nuDataList = fetchAllNursingUnit(fromDate, toDate);
		 	dataList = fetchAllSpecilty(fromDate, toDate);
			refbyList = fetchAllReferrence(fromDate, toDate);*/
			//fetchDropDownData();
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

public String beforeCriticalView()
{
	//System.out.println("CriticalActivityBoard.beforeCriticalView()");
	if (ValidateSession.checkSession())
	{
		try
		{
	
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	masterViewProp.add(gpv);

	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("ticket_no");
	gpv.setHeadingName("Ticket ID");
	gpv.setFormatter("historyView");
	gpv.setWidth(70);
	masterViewProp.add(gpv);
	
	String empid=(String)session.get("empName").toString();
	String[] subModuleRightsArray = getSubModuleRights(empid);
	 
	for(String s:subModuleRightsArray)
	{
		if(s.equals("escalateCRC_VIEW"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalate");
			gpv.setHeadingName("Escalate");
			gpv.setFormatter("escalateAction");
			gpv.setWidth(30);
			masterViewProp.add(gpv);
		}
		 
	}
	List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_critical_configuration", accountID, connectionSpace, "", 0, "table_name", "critical_configuration");
	for (GridDataPropertyView gdp : returnResult)
	{
		if(!gdp.getColomnName().equalsIgnoreCase("doc_name") && !gdp.getColomnName().equalsIgnoreCase("doc_mobile") 
				&& !gdp.getColomnName().equalsIgnoreCase("test_type") && !gdp.getColomnName().equalsIgnoreCase("ticket_no") )
		{
		gpv = new GridDataPropertyView();
		gpv.setColomnName(gdp.getColomnName());
		gpv.setHeadingName(gdp.getHeadingName());
		gpv.setEditable(gdp.getEditable());
		gpv.setSearch(gdp.getSearch());
		gpv.setWidth(gdp.getWidth());
		gpv.setFormatter(gdp.getFormatter());
		gpv.setHideOrShow(gdp.getHideOrShow());
		masterViewProp.add(gpv);
		}
	}
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("test_type");
	gpv.setHeadingName("Test");
	gpv.setHideOrShow("true");
	gpv.setWidth(50);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("patient_name");
	gpv.setHeadingName("Patient Name");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("patient_mob");
	gpv.setHeadingName("Patient Mobile");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("patient_status");
	gpv.setHeadingName("Type");
	gpv.setHideOrShow("false");
	gpv.setWidth(30);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("adm_doc");
	gpv.setHeadingName("Addmision Doc");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("nursing_unit");
	gpv.setHeadingName("Nursing Unit");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("bed_no");
	gpv.setHeadingName("Bed No");
	gpv.setHideOrShow("true");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("specimen_no");
	gpv.setHeadingName("Specimen No");
	gpv.setHideOrShow("true");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("his_added_at");
	gpv.setHeadingName("His Added At");
	gpv.setHideOrShow("true");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("spec");
	gpv.setHeadingName("Speciality");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("adm_doc_mob");
	gpv.setHeadingName("Add Doc Mob");
	gpv.setWidth(80);
	gpv.setHideOrShow("false");
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("lab_doc");
	gpv.setHeadingName("Lab Doctor");
	gpv.setHideOrShow("false");
	gpv.setWidth(100);
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("lab_doc_mob");
	gpv.setHeadingName("Lab Doc Mobile");
	gpv.setWidth(80);
	gpv.setHideOrShow("false");
	masterViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("patient_id");
	gpv.setHeadingName("Patient ID");
	gpv.setHideOrShow("true");
	masterViewProp.add(gpv);
	
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
private String[] getSubModuleRights(String userId)
{
	String[] value = null;
	try 
	{
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount where name = '"+userId+"'", connectionSpace);
		if(adminRights != null && adminRights.size()>0)
		{
			value = adminRights.get(0).toString().split("#");
		}
	} 
	catch (Exception e) {
		e.printStackTrace();
	} 
	return value;
}
//View Data in activity board
	@SuppressWarnings({ "rawtypes", "unused" })
	public String viewCriticalData()
	{
		 
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from critical_data ", connectionSpace);
				// ////System.out.println("dataCount:"+dataCount);
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to < getRecords())
					to = getRecords();

				StringBuilder query = new StringBuilder();
				query.append("SELECT cridata.id,cridata.ticket_no,cridata.status,cridata.open_date,"
						+ "cridata.open_time,cridata.escalate_date,cridata.escalate_time,cridata.level,"
						+ "cridata.dept,cridata.uhid,cridata.test_type,cpd.patient_name,cpd.patient_mob,cpd.patient_status,cpd.addmision_doc_name,cpd.nursing_unit,cpd.specialty,cpd.addmision_doc_mobile,cridata.doc_name,cridata.doc_mobile,cridata.caller_emp_id,cridata.caller_emp_name,cridata.caller_emp_mobile,cpd.id as pid,cdh.sn_upto_date,cdh.sn_upto_time,cpd.bed_no," +
								"cridata.specimen_no,cridata.his_added_at  FROM critical_data AS cridata ");

				query.append(" LEFT JOIN critical_patient_data AS cpd ON cridata.patient_id=cpd.id");
				query.append(" LEFT JOIN critical_patient_report AS crep ON crep.patient_id=cpd.id ");
				query.append(" LEFT JOIN critical_data_history AS cdh ON cdh.rid=cpd.id ");
				
				query.append(" where cridata.id >'0' ");
				
				
				if(getStatus().equalsIgnoreCase("-1"))
				{
					query.append(" and cridata.status!='close' ");
					query.append(" and cridata.status!='No Further Action Required' ");
					query.append(" and cridata.status!='Ignore' ");
				}
				if(uhidStatus!=null && uhidStatus.equalsIgnoreCase(""))
				{
					query.append(" and cridata.uhid ='"+uhidStatus+"'");
				}
				if (!getStatus().equalsIgnoreCase("-1") && fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
				{
					String str[] = getFromDate().split("-");
				
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" and cridata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					} else
					{
						query.append(" and cridata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
					}
					
				}
				//for status search by aarif
				if(getStatus()!=null && !getStatus().equalsIgnoreCase("-1"))
				{
					//System.out.println("getStatus "+getStatus());
					if(getStatus().contains("AllStatus"))
					{
						//query.append(" and cridata.status='close' ");
					}
					else
					{
						query.append(" and cridata.status='"+getStatus()+"' ");
					}
					
				}
				// for level search by aarif
				if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
				{
					query.append(" and cridata.level='"+getLevel()+"' ");
				}
				// for patient_type search by aarif
				if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
				{
					query.append(" and cpd.patient_status='"+getPatient_status()+"' ");
				}
				if(getNursingUnit()!=null && !getNursingUnit().equalsIgnoreCase("-1"))
				{
					query.append(" and cpd.nursing_unit='"+getNursingUnit()+"' ");
				}
				if(getDoc_name1()!=null && !getDoc_name1().equalsIgnoreCase("-1") && !getDoc_name1().equalsIgnoreCase(""))
				{
					query.append(" and cridata.doc_name='"+getDoc_name1()+"' ");
				}
				if(getPatient_speciality()!=null && !getPatient_speciality().equalsIgnoreCase("-1") && !getDoc_name1().equalsIgnoreCase(""))
				{
					query.append(" and cpd.specialty='"+getPatient_speciality()+"' ");
				}
				

				if(getPatient_speciality()!=null && !getPatient_speciality().equalsIgnoreCase("-1") && !getDoc_name1().equalsIgnoreCase(""))
								{
									query.append(" and cpd.specialty='"+getPatient_speciality()+"' ");
								}
				
				if(getSearchString()!=null && !getSearchString().equalsIgnoreCase(""))
				{
				
					
					query.append("  AND ( cridata.id like '"+getSearchString()+"%' or cridata.ticket_no like '"+getSearchString()+"%' or cridata.status like '"+getSearchString()+"%' or cridata.open_date like '"+getSearchString()+"%' or");
					query.append(" cridata.open_time like '"+getSearchString()+"%' or cridata.escalate_date like '"+getSearchString()+"%' or cridata.escalate_time like '"+getSearchString()+"%' or cridata.level like '"+getSearchString()+"%' or ");
					query.append(" cridata.dept like '"+getSearchString()+"%' or cridata.uhid like '"+getSearchString()+"%' or cridata.test_type like '"+getSearchString()+"%' or" );
					query.append(" cpd.patient_name like '"+getSearchString()+"%' or cpd.patient_mob like '"+getSearchString()+"%' or cpd.patient_status like '"+getSearchString()+"%' or ");
					query.append(" cpd.addmision_doc_name like '"+getSearchString()+"%' or cpd.nursing_unit like '"+getSearchString()+"%' or cpd.specialty like '"+getSearchString()+"%'  or ");
					query.append(" cpd.addmision_doc_mobile like '"+getSearchString()+"%' or cridata.doc_name like '"+getSearchString()+"%' or cridata.doc_mobile like '"+getSearchString()+"%' or ");
					query.append(" cridata.caller_emp_id like '"+getSearchString()+"%' or cridata.caller_emp_name like '"+getSearchString()+"%' or cridata.caller_emp_mobile like '"+getSearchString()+"%' or ");
					query.append(" cridata.caller_emp_id like '"+getSearchString()+"%' or cridata.caller_emp_name like '"+getSearchString()+"%' or cridata.caller_emp_mobile like '"+getSearchString()+"%' )  ");
					
				}
			/*	query.append(" LEFT JOIN test_type AS ty ON ty.id=cridata.test_type ");*/
				query.append(" GROUP BY  cridata.id ORDER BY cridata.id DESC ");
				
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					List<Object> tempList = new ArrayList<Object>();
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
						tempMap.put("id", getValueWithNullCheck(object[0]));
						tempMap.put("ticket_no", getValueWithNullCheck(object[1]));
						if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
						{
							tempMap.put("status", "OCC Park");
						} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I"))
						{
							tempMap.put("status", "Dept Park");
						} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore"))
						{
							tempMap.put("status", "OCC Ignore");
						} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore-I"))
						{
							tempMap.put("status", "Dept Ignore");
						} else
						{
							tempMap.put("status", getValueWithNullCheck(object[2]));
						}
						tempMap.put("open_date", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[3].toString())) + ", " + getValueWithNullCheck(object[4]).substring(0, 5));
						// tempMap.put("escalate_date",
						// getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[5].toString()))
						// + ", " + getValueWithNullCheck(object[6]));
						if (object[2]!=null && object[13]!=null && getValueWithNullCheck(object[2]).equalsIgnoreCase("Informed") || getValueWithNullCheck(object[13]).equalsIgnoreCase("IPD") ||  getValueWithNullCheck(object[13]).equalsIgnoreCase("OPD"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[5].toString(), object[6].toString()));
						} 
						else if ( getValueWithNullCheck(object[24]).equalsIgnoreCase("NA") && getValueWithNullCheck(object[25]).equalsIgnoreCase("NA") &&( getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze")))
						{
							if( object[24]!=null && object[25]!=null) 
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), getValueWithNullCheck(object[24].toString()), getValueWithNullCheck(object[25].toString())));
						} 
						else
						{
							tempMap.put("escalate_date", "NA");
						}
						tempMap.put("level", getValueWithNullCheck(object[7]));
						tempMap.put("dept", getValueWithNullCheck(object[8]));
						tempMap.put("uhid", getValueWithNullCheck(object[9]));
						tempMap.put("test_type", getValueWithNullCheck(object[10]));
						tempMap.put("patient_name", getValueWithNullCheck(object[11]));
						tempMap.put("patient_mob", getValueWithNullCheck(object[12]));
						tempMap.put("patient_status", getValueWithNullCheck(object[13]));
						tempMap.put("adm_doc", getValueWithNullCheck(object[14]));
						tempMap.put("nursing_unit", getValueWithNullCheck(object[15]));
						tempMap.put("spec", getValueWithNullCheck(object[16]));
						tempMap.put("adm_doc_mob", getValueWithNullCheck(object[17]));
						tempMap.put("lab_doc", getValueWithNullCheck(object[18]));
						tempMap.put("lab_doc_mob", getValueWithNullCheck(object[19]));
						tempMap.put("caller_emp_id", getValueWithNullCheck(object[20]));
						tempMap.put("caller_emp_name", getValueWithNullCheck(object[21]));
						tempMap.put("caller_emp_mobile", getValueWithNullCheck(object[22]));
						tempMap.put("patient_id", getValueWithNullCheck(object[23]));
						tempMap.put("bed_no", getValueWithNullCheck(object[26]));
						tempMap.put("specimen_no", getValueWithNullCheck(object[27]));
						tempMap.put("his_added_at",getValueWithNullCheck(object[28]).equalsIgnoreCase("NA")?"NA":DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[28]).split(" ")[0])+" "+getValueWithNullCheck(object[28]).split(" ")[1]);
						tempList.add(tempMap);
					}
					setViewCriticalData(tempList);
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
	public String fetchPatientID(String fromDate,String toDate)
	{
		String patientId="";
		String temp=null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data = cbt.executeAllSelectQuery("select patient_id from critical_data where open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'", connectionSpace);
		if (data != null && data.size() > 0)
		{
			patientId = data.get(0).toString();
			for(Object s:data)
			{
				temp=temp+"'"+s.toString()+"'"+",";
			}
			temp=temp.substring(4,temp.length()-1);
			//System.out.println("temptemptemptemptemp "+temp);
		}
		
		return temp;
	}
	
	//aarif
	
	public String fetchDropDownData()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				jsonArr = new JSONArray();
				JSONArray arr1 = new JSONArray();
				JSONArray arr4 = new JSONArray();
				JSONArray arr3 = new JSONArray();
				JSONObject ob1 = null;
				List l1 = fetchAllNursingUnit(fromDate, toDate);
				//List l2 = fetchAllSpecilty(fromDate, toDate);
				List l3 = fetchAllReferrence(fromDate, toDate);
				
				List l4 = fetchAllSpeciality(fromDate, toDate);//for patient speciality Drtopdown  change by aarif 09-02-2016 start here
			  	
				if (l1 != null && l1.size() > 0)
				{
					for (Iterator iterator1 = l1.iterator(); iterator1.hasNext();)
					{
						Object object = (Object) iterator1.next();
						if (object != null)
						{
							ob1 = new JSONObject();
							ob1.put("name", object.toString());
							arr1.add(ob1);
						}
					}
				}
			
				if (l3 != null && l3.size() > 0)
				{
					for (Iterator iterator = l3.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob1 = new JSONObject();
							//ob1.put("id", object[0].toString());
							ob1.put("name", object.toString());
					 		arr3.add(ob1);
						}
						
					}
				}
				
				if (l4 != null && l4.size() > 0)
				{
					for (Iterator iterator = l4.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob1 = new JSONObject();
							//ob1.put("id", object[0].toString());
							ob1.put("name", object.toString());
					 		arr4.add(ob1);
						}
						
					}
				}
				
				jsonArr.add(arr1);
				jsonArr.add(arr4);
				jsonArr.add(arr3);
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
	
	//for speciality
	/*private List fetchAllSpeciality(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		
		query.append("select distinct cpd.specialty from critical_data as crdata inner join critical_patient_data as cpd ON ");
		query.append(" crdata.uhid=cpd.uhid");
		query.append(" WHERE cpd.specialty  IS NOT NULL ");
		
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND crdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND crdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
		}
			
		if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
		{
			query.append(" AND crdata.level='"+getLevel()+"' ");
		}
		if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
		{
			query.append(" AND cpd.patient_status='"+getPatient_status()+"' ");
		}
		
		if (getStatus() != null && !"-1".equalsIgnoreCase(getStatus()) && !"AllStatus".equalsIgnoreCase(getStatus()))
			{
				query.append(" AND crdata.status='" + getStatus() + "' ");	
			}
			
		
		
		query.append(" ORDER BY cpd.specialty ");
		System.out.println("QQQ speciality  aarif  : "+query.toString());
		List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		return l1;
	}
	
	
	

	private List fetchAllReferrence(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("select distinct crdata.doc_name from critical_data as crdata ");
		query.append(" inner join critical_patient_data as cpd on cpd.uhid=crdata.uhid ");
		query.append(" WHERE cpd.uhid IS NOT NULL ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND crdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND crdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
		}
			
		if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
		{
			query.append(" AND crdata.level='"+getLevel()+"' ");
		}
		if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
		{
			query.append(" AND cpd.patient_status='"+getPatient_status()+"' ");
		}
		
		if (getStatus() != null && !"-1".equalsIgnoreCase(getStatus()))
			{
				query.append(" AND crdata.status='" + getStatus() + "' ");	
			}
		query.append(" ORDER BY crdata.doc_name");
		List l3 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		System.out.println("doctor name aarif "+query.toString());

		return l3;
	}
	
	private List fetchAllNursingUnit(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT nursing_unit FROM critical_data AS crdata");
		query.append(" INNER JOIN critical_patient_data AS cpd ON crdata.uhid=cpd.uhid");
		query.append(" WHERE cpd.nursing_unit IS NOT NULL ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND crdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND crdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
		}
			
		if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
		{
			query.append(" AND crdata.level='"+getLevel()+"' ");
		}
		if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
		{
			query.append(" AND cpd.patient_status='"+getPatient_status()+"' ");
		}
		
		if (getStatus() != null && !"-1".equalsIgnoreCase(getStatus()))
			{
				query.append(" AND crdata.status='" + getStatus() + "' ");	
			}
			
		
		
		query.append(" ORDER BY cpd.nursing_unit ");
		System.out.println("QQQ Nursing Unit aarif  : "+query.toString());
		List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		return l1;
	}*/
	
	
	
	private List fetchAllReferrence(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("select distinct crdata.doc_name from critical_data as crdata ");
		query.append(" inner join critical_patient_data as cpd on  cpd.id=crdata.patient_id ");
		query.append(" WHERE cpd.uhid IS NOT NULL ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND crdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND crdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
		}
			
		if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
		{
			query.append(" AND crdata.level='"+getLevel()+"' ");
		}
		if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
		{
			query.append(" AND cpd.patient_status='"+getPatient_status()+"' ");
		}
		
		if (getStatus() != null && !"-1".equalsIgnoreCase(getStatus()) && !"AllStatus".equalsIgnoreCase(getStatus()))
		{
			
				query.append(" AND crdata.status='" + getStatus() + "' ");	
			
		}
		if (getNursingUnit() != null && !"-1".equalsIgnoreCase(getNursingUnit()))
		{
			query.append(" AND cpd.nursing_unit='" + getNursingUnit() + "' ");	
		}
		if (getPatient_speciality() != null && !"-1".equalsIgnoreCase(getPatient_speciality()))
		{
			query.append(" AND cpd.specialty='" + getPatient_speciality() + "' ");	
		}
		query.append(" ORDER BY crdata.doc_name");
		List l3 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		//System.out.println("doctor name aarif "+query.toString());

		return l3;
	}
	
	private List fetchAllNursingUnit(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT nursing_unit FROM critical_data AS crdata");
		query.append(" INNER JOIN critical_patient_data AS cpd ON cpd.id=crdata.patient_id ");
		query.append(" WHERE cpd.nursing_unit IS NOT NULL ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND crdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND crdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
		}
			
		if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
		{
			query.append(" AND crdata.level='"+getLevel()+"' ");
		}
		if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
		{
			query.append(" AND cpd.patient_status='"+getPatient_status()+"' ");
		}
		
		if (getStatus() != null && !"-1".equalsIgnoreCase(getStatus()) && !"AllStatus".equalsIgnoreCase(getStatus()))
		{
			
				query.append(" AND crdata.status='" + getStatus() + "' ");	
			
		}
		if (getPatient_speciality() != null && !"-1".equalsIgnoreCase(getPatient_speciality()))
		{
			query.append(" AND cpd.specialty='" + getPatient_speciality() + "' ");	
		}
			
		
		
		query.append(" ORDER BY cpd.nursing_unit ");
	//	System.out.println("QQQ Nursing Unit aarif  : "+query.toString());
		List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		return l1;
	}
	
	
	
	private List fetchAllSpeciality(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		
		query.append("select distinct cpd.specialty from critical_data as crdata inner join critical_patient_data as cpd ON ");
		query.append(" cpd.id=crdata.patient_id ");
		query.append(" WHERE cpd.specialty  IS NOT NULL ");
		
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND crdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND crdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
		}
			
		if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
		{
			query.append(" AND crdata.level='"+getLevel()+"' ");
		}
		if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
		{
			query.append(" AND cpd.patient_status='"+getPatient_status()+"' ");
		}
		
		if (getStatus() != null && !"-1".equalsIgnoreCase(getStatus()) && !"AllStatus".equalsIgnoreCase(getStatus()))
		{
			
				query.append(" AND crdata.status='" + getStatus() + "' ");	
			
		}
			
		
		
		query.append(" ORDER BY cpd.specialty ");
		//System.out.println("QQQ speciality  aarif  : "+query.toString());
		List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		return l1;
	}
	
	public String getCounterStatus()
	{boolean sessionFlag = ValidateSession.checkSession();
	if(sessionFlag){
		try{
			jsonArr= new JSONArray();
			JSONObject obj = new JSONObject();
			StringBuilder qry= new StringBuilder();
			
			
			qry.append(" select count(*),status from critical_data where reg_by='"+session.get("empIdofuser").toString().split("-")[1]+"'");
			
			if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase("") )
			{
				String str[] = getFromDate().split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					
					qry.append (" and open_date between '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
				} else
				{
					
					qry.append("and open_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
				}
			}
			
			
			qry.append (" group by status "); 
			
			//System.out.println("counter query "+qry);
			List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
			
			if(data!=null && !data.isEmpty())
			{    String Informed="0", InformedR="0",  NotInformed="0",NotInformedR="0", InformedP="0", InformedRP="0",Ignore="0",Close="0",Snooze="0";
				
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{ 	
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						if(object[1].toString().equalsIgnoreCase("Not Informed"))
						{
							NotInformed=object[0].toString();
						}
						
						else if(object[1].toString().equalsIgnoreCase("Informed-P"))
						{
							InformedP=object[0].toString();
						}
						
						else if(object[1].toString().equalsIgnoreCase("Informed"))
						{
							Informed=object[0].toString();	
						}
						
						else if(object[1].toString().equalsIgnoreCase("Close"))
						{
							Close=object[0].toString();
						}
						else if(object[1].toString().equalsIgnoreCase("Snooze"))
						{
							Snooze=object[0].toString();
						}
						
						else if(object[1].toString().equalsIgnoreCase("Ignore"))
						{
							Ignore=object[0].toString();
						}
						
						
						
					}
				}
				obj.put("NotInformed", NotInformed);
				obj.put("Informed", Informed);
				obj.put("InformedP", InformedP);
				obj.put("Close",Close);
				obj.put("Snooze", Snooze);
				obj.put("Ignore", Ignore);
			
				
				jsonArr.add(obj);
			}
			
			
			
			
			//System.out.println("jsonArr  "+jsonArr);
			//System.out.println(jsonArr.size());
			return SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}else{
		return LOGIN;
	}
	}
	
public String getValueWithNullCheck(Object value)
{
	return ((value==null || value.toString().equals("") ) ?  "NA" : value.toString());
}
/*public String fetchCurrentCritical()
{

	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			columnMap = new LinkedHashMap<String, String>();

			
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_critical_configuration",accountID,connectionSpace,"",0,"table_name","critical_configuration");
			for(GridDataPropertyView gdp:returnResult)
			{
				if(!gdp.getColomnName().equalsIgnoreCase("status"))
				{
					columnMap.put("cd."+gdp.getColomnName(),gdp.getHeadingName());	
				}
			}
			columnMap.put("cpd.id","Patient ID");
			columnMap.put("cpd.patient_name","Patient Name");
			columnMap.put("cpd.patient_status","Patient Type");
			columnMap.put("cpd.patient_mob","Patient Mobile");
			columnMap.put("cpd.nursing_unit","Nursuing Unit");
			columnMap.put("cpr.test_name","Test Name");
			columnMap.put("cd.test_type","Test Type");
			columnMap.put("cpr.test_value","Test Value");
			columnMap.put("cpr.test_unit","Test Unit");
			columnMap.put("cpr.test_comment","Test Comment");
			columnMap.put("cd.status","Status");
			columnMap.put("cdh.comments as not_inform","Not Informed Comment");
			columnMap.put("cdh.comments as inform","Informed Comment");
			columnMap.put("cdh.comments as close","Close Comment");
			columnMap.put("cdh.comments as closep","Close-P Comment");
			columnMap.put("cdh.comments as escalate","Escalate Comment");
			columnMap.put("cdh.comments as sendSMS","Send SMS Mob");
			columnMap.put("cdh.doc_name as doctor_in","Informed Doctor");
			columnMap.put("cdh.doc_name as doctor_close","Close Doctor");
			columnMap.put("cdh.doc_name as doctor_p","Close-P Doctor");
			columnMap.put("cdh.comments as escalate","Escalate Doctor");
			columnMap.put("cdh.escalate_to as l1","L1 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l1m","L1 Esc Mobile");
			columnMap.put("cdh.escalate_to as l2","L2 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l2m","L2 Esc Mobile");
			columnMap.put("cdh.escalate_to as l3","L3 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l3m","L3 Esc Mobile");
			columnMap.put("cdh.escalate_to as l4","L4 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l4m","L4 Esc Mobile");
			columnMap.put("cdh.escalate_to as l5","L5 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l5m","L5 Esc Mobile");
			columnMap.put("cdh.action_time as notinform","Not Informed Time");
			columnMap.put("cdh.action_time as informT","Informed Time");
			columnMap.put("cdh.action_time as closeT","Close Time");
			columnMap.put("cdh.action_time as closePT","Close-P Time");
			columnMap.put("cdh.action_time as escT","Esclate Time");
			columnMap.put("cdh.action_time as smsT","Send SMS Time");
			if(getStatus()!=null && !getStatus().endsWith("-1") && !getStatus().equalsIgnoreCase(""))
			{
				if(getStatus().contains("AllStatus") || getStatus().contains("Close"))
				{
					columnMap.put("cdh.comments as resolutiontime","Resolution Time");
				}
			}
			
			if (columnMap != null && columnMap.size() > 0)
			{
				session.put("columnMap", columnMap);
			}
			return SUCCESS;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
	} else
	{
		return LOGIN;
	}

}*/
public String fetchCurrentCritical()
{

	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			columnMap = new LinkedHashMap<String, String>();

			
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_critical_configuration",accountID,connectionSpace,"",0,"table_name","critical_configuration");
			for(GridDataPropertyView gdp:returnResult)
			{
				
				if(!gdp.getColomnName().equalsIgnoreCase("status"))
				{
					columnMap.put("cd."+gdp.getColomnName(),gdp.getHeadingName());	
				}
				
			}
			columnMap.put("cpd.id","Patient ID");
			columnMap.put("cpd.patient_name","Patient Name");
			columnMap.put("cd.specimen_no","Specimen No");
			columnMap.put("cpd.bed_no","Bed No");
			columnMap.put("cpd.patient_status","Patient Type");
			columnMap.put("cpd.patient_mob","Patient Mobile");
			columnMap.put("cpd.nursing_unit","Nursuing Unit");
			columnMap.put("cpd.specialty","Specialty ");
			columnMap.put("cdh.nursing_name","Nursuing Name");
			columnMap.put("cdh.nursing_comment","Nursuing Comment");
			columnMap.put("cdh.doc_name as docName","Doctor Name");
			columnMap.put("cdh.doc_comment","Doctor Comment");
			columnMap.put("cpr.test_name","Test Name");
			columnMap.put("cd.test_type","Test Type");
			columnMap.put("cpr.test_value","Test Value");
			columnMap.put("cpr.test_unit","Test Unit");
			columnMap.put("cpr.test_comment","Test Comment");
			columnMap.put("cd.status","Status");
			columnMap.put("cdh.comments","Informed Comment");
			columnMap.put("cdh.comments as inform","Informed Comment");
			columnMap.put("cdh.comments as close","Close Comment");
			columnMap.put("cdh.comments as closep","Close-P Comment");
			columnMap.put("cdh.comments as escalate","Escalate Comment");
			columnMap.put("cdh.comments as sendSMS","Send SMS Mob");
			columnMap.put("cdh.doc_name as doctor_in","Informed Doctor");
			columnMap.put("cdh.doc_name as doctor_close","Close Doctor");
			columnMap.put("cdh.doc_name as doctor_p","Close-P Doctor");
			columnMap.put("cdh.comments as escalate","Escalate Doctor");
			columnMap.put("cdh.escalate_to as l1","L1 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l1m","L1 Esc Mobile");
			columnMap.put("cdh.escalate_to as l2","L2 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l2m","L2 Esc Mobile");
			columnMap.put("cdh.escalate_to as l3","L3 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l3m","L3 Esc Mobile");
			columnMap.put("cdh.escalate_to as l4","L4 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l4m","L4 Esc Mobile");
			columnMap.put("cdh.escalate_to as l5","L5 Esc Doctor");
			columnMap.put("cdh.escalate_to_mob as l5m","L5 Esc Mobile");
			columnMap.put("cdh.action_time as notinform","Not Informed Time");
			columnMap.put("cdh.action_time as informT","Informed Time");
			columnMap.put("cdh.action_time as closeT","Close Time");
			columnMap.put("cdh.action_time as closePT","Close-P Time");
			columnMap.put("cdh.action_time as escT","Esclate Time");
			columnMap.put("cdh.action_time as smsT","Send SMS Time");
			columnMap.put("cdh.close_by_id","Close By Doc ID");
			columnMap.put("cdh.close_by_name","Close By Doc ");
			columnMap.put("cdh.assign_to_id","Close By EmpID");
			columnMap.put("cdh.close_by_name as emp","Close By Emp Name ");
			if(getStatus()!=null && !getStatus().endsWith("-1") && !getStatus().equalsIgnoreCase(""))
			{
				if(getStatus().contains("AllStatus") || getStatus().contains("Close"))
				{
					columnMap.put("cdh.comments as resolutiontime","Resolution Time");
				}
			}
			
			if (columnMap != null && columnMap.size() > 0)
			{
				session.put("columnMap", columnMap);
			}
			return SUCCESS;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
	} else
	{
		return LOGIN;
	}

}
public String excelDownload()
{String returnResult = ERROR;
boolean sessionFlag = ValidateSession.checkSession();
excelFileName = "Critical Detail";

if (sessionFlag)
{
	try
	{

		List keyList = new ArrayList();
		List titleList = new ArrayList();
		String patient_id=null;
		com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String empIdofuser = (String) session.get("empIdofuser");
		if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
			return ERROR;

		StringBuilder query = new StringBuilder();
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			if (str[0] != null && str[0].length() > 3)
			{
				patient_id=fetchPatientID(fromDate,toDate);
			} else
			{
				patient_id=fetchPatientID((DateUtil.convertDateToUSFormat(fromDate)),(DateUtil.convertDateToUSFormat(toDate)));
			}
			
		}
		if (columnNames != null && columnNames.length > 0)
		{
			keyList = Arrays.asList(columnNames);
			Map<String, String> tempMap = new LinkedHashMap<String, String>();
			tempMap = (Map<String, String>) session.get("columnMap");
			tempMap.put("refdata.id", "ID");
			if (session.containsKey("columnMap"))
				session.remove("columnMap");

			List dataList = null;
			for (int index = 0; index < keyList.size(); index++)
			{
				titleList.add(tempMap.get(keyList.get(index)));
			}
			if (keyList != null && keyList.size() > 0)
			{
				query.append(" select cdh1.rid,cdh1.comments,cdh1.doc_name,cdh1.action_date,cdh1.action_time from critical_data_history as cdh1 inner join critical_patient_data as cd on cd.id=cdh1.rid where cdh1.status in('Not Informed') and cd.id in("+patient_id+") order by cdh1.id asc ");
				List historyNotInfor = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				query = null;
				query = new StringBuilder();
				
				query.append(" select cdh1.rid,cdh1.comments,cdh1.doc_name,cdh1.action_date,cdh1.action_time,cdh1.nursing_name,cdh1.nursing_comment,cdh1.doc_name as docName,cdh1.doc_comment from critical_data_history as cdh1 inner join critical_patient_data as cd on cd.id=cdh1.rid where cdh1.status in('Informed') and cd.id in("+patient_id+") order by cdh1.id asc ");
				List historyInform = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("historyInform "+historyInform.size());
				query = null;
				query = new StringBuilder();
				 
				query.append(" select cdh1.rid,cdh1.comments,cdh1.doc_name,cdh1.action_date,cdh1.action_time,cdh1.close_by_id,cdh1.close_by_name,cdh1.assign_to_id,cdh1.close_by_name as emp from critical_data_history as cdh1 inner join critical_patient_data as cd on cd.id=cdh1.rid where cdh1.status in('Close','No Further Action Required') and cd.id in("+patient_id+") order by cdh1.id asc ");
				List historyClose = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("historyClose "+historyClose.size());
				query = null;
				query = new StringBuilder();
				
				query.append(" select cdh1.rid,cdh1.comments,cdh1.doc_name,cdh1.action_date,cdh1.action_time from critical_data_history as cdh1 inner join critical_patient_data as cd on cd.id=cdh1.rid where cdh1.status in('Close-P') and cd.id in("+patient_id+") order by cdh1.id asc ");
				List historyClosep = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("historyClosep "+historyClosep.size());
				query = null;
				query = new StringBuilder();
				
				query.append(" select cdh1.rid,cdh1.comments,cdh1.doc_name,cdh1.action_date,cdh1.action_time from critical_data_history as cdh1 inner join critical_patient_data as cd on cd.id=cdh1.rid where cdh1.status in('Escalate') and cd.id in("+patient_id+") order by cdh1.id asc ");
				List historyEsc = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("historyEsc "+historyEsc.size());
				query = null;
				query = new StringBuilder();
				
				query.append(" select cdh1.rid,cdh1.nursing_mob,cdh1.doc_name,cdh1.action_date,cdh1.action_time from critical_data_history as cdh1 inner join critical_patient_data as cd on cd.id=cdh1.rid where cdh1.status in('Send SMS') and cd.id in("+patient_id+") order by cdh1.id asc ");
				List historySendSms = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("historyEsc "+historyEsc.size());
				query = null;
				query = new StringBuilder();
				
				query.append("select cdh1.rid,cdh1.escalate_to,cdh1.escalate_to_mob,cdh1.esc_level,cdh1.action_date,cdh1.action_time from critical_data_history as cdh1 inner join critical_patient_data as cpd on cpd.id=cdh1.rid where cdh1.status in('Escalate','Close','Informed','Not Informed','Close-p') and cpd.id in("+patient_id+") order by cdh1.id asc ");
				List historyescl = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("historyClosep "+historyClosep.size());
				query = null;
				query = new StringBuilder();
				
				 
				query.append("SELECT DISTINCT ");
				int i = 0;
				for (Iterator it = keyList.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null) 
					{ 
							if (i < keyList.size() - 1)
							{
								if(obdata.toString().equalsIgnoreCase("cd.dept"))
								{
									query.append(" dep.deptName, ");
								}
								else if(obdata.toString().equalsIgnoreCase("cd.test_type"))
								{
									query.append(" cpr.test_type, ");
								}
								else if(obdata.toString().equalsIgnoreCase("cd.reg_by"))
								{
								query.append(" eb.empName, ");
								} 
								else{
									
									query.append(obdata.toString() + ",");
								}
							} else
							{
								query.append(obdata.toString());
							} 
					}
					i++;
				}
				query.append(" FROM critical_data as cd ");
				query.append(" inner join employee_basic as eb on eb.id=cd.reg_by ");
				query.append(" inner join department as dep on dep.id=cd.dept ");
				query.append(" inner join critical_patient_data as cpd on cpd.id=cd.patient_id ");
				query.append(" inner join critical_patient_report as cpr on cpr.patient_id=cd.id ");
				query.append(" inner join 	critical_data_history as cdh on cdh.rid=cd.id ");
				query.append(" where cd.id >'0' ");
				
			
				if(getStatus().equalsIgnoreCase("-1"))
				{
					query.append(" and cd.status!='close' ");
				}
				if(uhidStatus!=null && uhidStatus.equalsIgnoreCase(""))
				{
					query.append(" and cd.uhid ='"+uhidStatus+"'");
				}
				if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
				{
					
					String str[] = getFromDate().split("-");
				
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" and cd.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					} else
					{
						query.append(" and cd.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
					}
					
				}
				if(getStatus()!=null && !getStatus().equalsIgnoreCase("-1"))
				{
					//System.out.println("getStatus "+getStatus());
					if(getStatus().contains("AllStatus"))
					{
						//query.append(" and cridata.status='close' ");
					}
					else
					{
						query.append(" and cd.status='"+getStatus()+"' ");
					}
					
				}
				if(getLevel()!=null && !getLevel().equalsIgnoreCase("-1"))
				{
					query.append(" and cd.level='"+getLevel()+"' ");
				}
				if(getPatient_status()!=null && !getPatient_status().equalsIgnoreCase("-1"))
				{
					query.append(" and cpd.patient_status='"+getPatient_status()+"' ");
				}
				if(getNursingUnit()!=null && !getNursingUnit().equalsIgnoreCase("-1"))
				{
					query.append(" and cpd.nursing_unit='"+getNursingUnit()+"' ");
				}
				if(getDoc_name1()!=null && !getDoc_name1().equalsIgnoreCase("-1") && !getDoc_name1().equalsIgnoreCase(""))
				{
					query.append(" and cd.doc_name='"+getDoc_name1()+"' ");
				}
				if(getSearchString()!=null && !getSearchString().equalsIgnoreCase(""))
				{
				
					
					query.append("  AND ( cd.id like '"+getSearchString()+"%' or cd.ticket_no like '"+getSearchString()+"%' or cd.status like '"+getSearchString()+"%' or cd.open_date like '"+getSearchString()+"%' or");
					query.append(" cd.open_time like '"+getSearchString()+"%' or cd.escalate_date like '"+getSearchString()+"%' or cd.escalate_time like '"+getSearchString()+"%' or cd.level like '"+getSearchString()+"%' or ");
					query.append(" cd.dept like '"+getSearchString()+"%' or cd.uhid like '"+getSearchString()+"%' or cd.test_type like '"+getSearchString()+"%' or" );
					query.append(" cpd.patient_name like '"+getSearchString()+"%' or cpd.patient_mob like '"+getSearchString()+"%' or cpd.patient_status like '"+getSearchString()+"%' or ");
					query.append(" cpd.addmision_doc_name like '"+getSearchString()+"%' or cpd.nursing_unit like '"+getSearchString()+"%' or cpd.specialty like '"+getSearchString()+"%'  or ");
					query.append(" cpd.addmision_doc_mobile like '"+getSearchString()+"%' or cd.doc_name like '"+getSearchString()+"%' or cd.doc_mobile like '"+getSearchString()+"%' or ");
					query.append(" cd.caller_emp_id like '"+getSearchString()+"%' or cd.caller_emp_name like '"+getSearchString()+"%' or cd.caller_emp_mobile like '"+getSearchString()+"%' or ");
					query.append(" cd.caller_emp_id like '"+getSearchString()+"%' or cd.caller_emp_name like '"+getSearchString()+"%' or cd.caller_emp_mobile like '"+getSearchString()+"%' )  ");
					
				}
				query.append(" GROUP BY cd.id ORDER BY cd.id DESC ");
				//System.out.println("<<<<>>>>>>>>>>>>>> "+query);
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("<<<<>>>>>>>>>>>>>> "+dataList.size());
				String orgDetail = (String) session.get("orgDetail");
				String orgName = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgName = orgDetail.split("#")[0];
				}
				if (dataList != null && titleList != null && keyList != null)
					
				{
					excelFileName = new CriticalExcelDownload().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, historyNotInfor,historyInform,historyEsc,historyClose,historyClosep,historyescl,historySendSms, connectionSpace);
				}
				if (excelFileName != null)
				{
					excelStream = new FileInputStream(excelFileName);
				}
			}
			returnResult = SUCCESS;
		} else
		{
			addActionMessage("There are some error in data!!!!");
			returnResult = ERROR;
		}
	} catch (Exception e)
	{
		e.printStackTrace();
	}
} else
{
	returnResult = LOGIN;
}
return returnResult;
}







@Override
	public void setServletRequest(HttpServletRequest req)
	{
		this.request=req;
		
	}

public String getToDate()
{
	return toDate;
}

public void setToDate(String toDate)
{
	this.toDate = toDate;
}

public String getFromDate()
{
	return fromDate;
}

public void setFromDate(String fromDate)
{
	this.fromDate = fromDate;
}

public List<Object> getViewCriticalData()
{
	return viewCriticalData;
}

public void setViewCriticalData(List<Object> viewCriticalData)
{
	this.viewCriticalData = viewCriticalData;
}

public List<GridDataPropertyView> getMasterViewProp()
{
	return masterViewProp;
}

public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
{
	this.masterViewProp = masterViewProp;
}

public JSONArray getJsonArr() {
	return jsonArr;
}

public void setJsonArr(JSONArray jsonArr) {
	this.jsonArr = jsonArr;
}

public void setUhidStatus(String uhidStatus) {
	this.uhidStatus = uhidStatus;
}

public String getUhidStatus() {
	return uhidStatus;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
	//System.out.println("value_of_status>>>>>>>"+status);
}

public String getLevel() {
	return level;
}

public void setLevel(String level) {
	//System.out.println("........ "+level);
	this.level = level;
}

public String getPatient_status() {
	return patient_status;
}

public void setPatient_status(String patient_status) {
	//System.out.println("........ "+patient_status);
	this.patient_status = patient_status;
}

public String getSearchString() {
	return searchString;
}

public void setSearchString(String searchString) {
	this.searchString = searchString;
}

public String getNursingUnit() {
	return nursingUnit;
}

public void setNursingUnit(String nursingUnit) {
	this.nursingUnit = nursingUnit;
}

public String getExcelFileName() {
	return excelFileName;
}

public void setExcelFileName(String excelFileName) {
	this.excelFileName = excelFileName;
}

public Map<String, String> getColumnMap() {
	return columnMap;
}

public void setColumnMap(Map<String, String> columnMap) {
	this.columnMap = columnMap;
}

public String[] getColumnNames() {
	return columnNames;
}

public void setColumnNames(String[] columnNames) {
	this.columnNames = columnNames;
}

public FileInputStream getExcelStream() {
	return excelStream;
}

public void setExcelStream(FileInputStream excelStream) {
	this.excelStream = excelStream;
}

public String getDoc_name1() {
	return doc_name1;
}

public void setDoc_name1(String docName1) {
	doc_name1 = docName1;
}

public String getPatient_speciality()
{
	return patient_speciality;
}

public void setPatient_speciality(String patientSpeciality)
{
	patient_speciality = patientSpeciality;
}




}