package com.Over2Cloud.ctrl.referral.activity;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.ReferralExcelDownload;

public class ReferralActivityBoard extends GridPropertyBean
{

	private static final long serialVersionUID = 1L;
	private List<Object> viewReferralData = null;
	private String toDate;
	private String fromDate;
	private String status,level;
	private String nursingUnit, specialty;
	private String priority;
	private List<String> dataList;
	private List<String> nuDataList;
	private JSONArray jsonArr;
	private String toDepart;
	private String excelFileName;
	private String[] columnNames;
	private Map<String, String> columnMap;
	private FileInputStream excelStream;
	private String reffby;
	private List<String> refbyList;
	private String refDocTo;
	private String refDoc;
	private String departmentView;
	private String locationWise;
	private String idAll;
	
	private String uhidStatus;
	public List<GridDataPropertyView> viewPageUHIDColumns=null;
	
	//Ankit 
	public String UHIDCheck(){
		if (ValidateSession.checkSession())
		{
			try
			{
				StringBuilder query= new StringBuilder(" select rpd.uhid as UHIDT from referral_patient_data as rpd inner join referral_data as refdata on  refdata.uhid=rpd.id   " );
				query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
				query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
				query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
				query.append(" where rpd.uhid='"+searchString+"' and refdata.open_date>='"+DateUtil.getDateAfterDays(-1)+"'" +
						" GROUP BY refdata.id ORDER BY refdata.id DESC ");
				
				//// //System.out.println(">>>>   " +query.toString());
				List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if(l1 !=null && l1.size() > 0){
					String link="<a href='#' onclick='showCurrentData()'><font color='red', size='5'><b>Data Found</b></font></a>";
					uhidStatus=link;
				}else{
					String errorlink="<a href='#' ><font  size='3', color='green'>No Data Found</font></a>";
					uhidStatus=errorlink;
				}
				
				return SUCCESS;
			} catch (Exception e){
				addActionMessage("Oops Error!!!");
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
				addActionMessage("Your session out!!! Login again");
				return LOGIN;
		}
		
	}

	
	public String beforeReferralUHIDView()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				setGridUHIDColumns();
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

	public void setGridUHIDColumns()
	{
		try
		{
			viewPageUHIDColumns = new ArrayList<GridDataPropertyView>();
		//	List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_referral_configuration", accountID, connectionSpace, "", 0, "table_name", "referral_configuration");
			GridDataPropertyView gpv = new GridDataPropertyView();
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticket_no");
			gpv.setHeadingName("Ticket ID");
			gpv.setFormatter("historyView");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("openDate");
			gpv.setHeadingName("Open Date");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("openTime");
			gpv.setHeadingName("Open Time");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageUHIDColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalate_date");
			gpv.setHeadingName("TAT");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageUHIDColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setHideOrShow("false");
			gpv.setFormatter("viewLevel");
			gpv.setWidth(50);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			viewPageUHIDColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setHideOrShow("false");
			gpv.setFormatter("viewDetails");
			gpv.setWidth(70);
			viewPageUHIDColumns.add(gpv);
			
			

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ref_to");
			gpv.setHeadingName("Referred To");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("spec");
			gpv.setHeadingName("Speciality");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageUHIDColumns.add(gpv);

		 	
				gpv = new GridDataPropertyView();
				gpv.setColomnName("bed");
				gpv.setHeadingName("Bed");
				gpv.setWidth(50);
				viewPageUHIDColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("nursing_unit");
				gpv.setHeadingName("Nursing Unit");
				gpv.setWidth(100);
				viewPageUHIDColumns.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("ref_by");
				gpv.setHeadingName("Referred By");
				gpv.setHideOrShow("false");
				gpv.setWidth(120);
				viewPageUHIDColumns.add(gpv);

				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("ref_to_sub_spec");
				gpv.setHeadingName("Sub Speciality");
				gpv.setWidth(150);
				viewPageUHIDColumns.add(gpv);
			
				gpv = new GridDataPropertyView();
				gpv.setColomnName("reason");
				gpv.setHeadingName("Reason");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageUHIDColumns.add(gpv);
			 

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}

	}

	
	// View Data in UHID Check for current date
	@SuppressWarnings({ "rawtypes", "unused" })
	public String viewUHIDGridData()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from referral_data ", connectionSpace);
				// //////// //System.out.println("dataCount:"+dataCount);
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
				query.append("SELECT refdata.id,refdata.ticket_no,refdata.status,refdata.open_date,refdata.open_time,refdata.escalate_date,refdata.escalate_time," + "refdata.level,refdata.priority,rpd.uhid,rd.name,rd1.name AS ref_to,refdata.reason,rpd.patient_name,rpd.bed,rpd.nursing_unit,rd.spec,rd1.spec" + " AS ref_spec,refdata.ref_to_sub_spec,rd1.id AS refId,rdh.sn_upto_date,rdh.sn_upto_time FROM referral_data AS refdata ");
				query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
				query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
				query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
				query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
				
				if (uhidStatus != null && !uhidStatus.equalsIgnoreCase("undefined"))
				{
					query.append("where rpd.uhid='"+uhidStatus+"' and refdata.open_date >='"+DateUtil.getDateAfterDays(-1)+"' " +
							"  GROUP BY refdata.id ORDER BY refdata.id DESC ");
				}
					//// //System.out.println("+++  "+ query.toString());
				//query.append(" GROUP BY refdata.id ORDER BY refdata.id DESC ");
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
						 	if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Not Informed") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Informed"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[5].toString(), object[6].toString()));
						} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[20].toString(), object[21].toString()));
						}
						 
						{
							tempMap.put("escalate_date", "00:00");
						}
						tempMap.put("level", getValueWithNullCheck(object[7]));
						tempMap.put("priority", getValueWithNullCheck(object[8]));
						tempMap.put("openDate", DateUtil.convertDateToIndianFormat(object[3].toString()));
						tempMap.put("openTime", getValueWithNullCheck(object[4]));
						tempMap.put("uhid", getValueWithNullCheck(object[9]));
						tempMap.put("ref_by", getValueWithNullCheck(object[10]));
						tempMap.put("ref_to", getValueWithNullCheck(object[11]));
						tempMap.put("reason", getValueWithNullCheck(object[12]));
						tempMap.put("patient_name", getValueWithNullCheck(object[13]));
						tempMap.put("bed", getValueWithNullCheck(object[14]));
						tempMap.put("nursing_unit", getValueWithNullCheck(object[15]));
						tempMap.put("spec", getValueWithNullCheck(object[16]));
						tempMap.put("ref_spec", getValueWithNullCheck(object[17]));
						tempMap.put("ref_to_sub_spec", getValueWithNullCheck(object[18]));
						tempMap.put("ref_Id", getValueWithNullCheck(object[19]));

						tempList.add(tempMap);
					}
					setViewReferralData(tempList);
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
	
	
	
	
	@SuppressWarnings("unchecked")
	public String beforeReferralViewHeader()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				 
				setFromDate(DateUtil.getCurrentDateUSFormat());
				setToDate(DateUtil.getCurrentDateUSFormat());
				String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
				String[] subModuleRightsArray = getSubModuleRights(empid);
				if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for(String s:subModuleRightsArray)
					{
						if(s.equals("deptView_VIEW"))
						{
							 setDepartmentView("DepartmentView");
					 		 
						}
						if(s.equals("locationManagerView_VIEW"))
						{
							 setLocationWise("LocationManagerView");
					 		 
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

	public String fetchDropDownData()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				jsonArr = new JSONArray();
				JSONArray arr1 = new JSONArray();
				JSONArray arr2 = new JSONArray();
				JSONArray arr3 = new JSONArray();
				JSONObject ob1 = null;
				List l1 = fetchAllNursingUnit(fromDate, toDate);
				List l2 = fetchAllSpecilty(fromDate, toDate);
				List l3 = fetchAllReferrence(fromDate, toDate);
			  	
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
				if (l2 != null && l2.size() > 0)
				{
					for (Iterator iterator = l2.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob1 = new JSONObject();
							ob1.put("name", object.toString());
							arr2.add(ob1);
						}
					}
				}
				if (l3 != null && l3.size() > 0)
				{
					for (Iterator iterator = l3.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						if (object[1] != null && object[0] != null)
						{
							ob1 = new JSONObject();
							ob1.put("id", object[0].toString());
							ob1.put("name", object[1].toString());
					 		arr3.add(ob1);
						}
						
					}
				}
				jsonArr.add(arr1);
				jsonArr.add(arr2);
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

	private List fetchAllSpecilty(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT rd1.spec FROM referral_data AS refdata");
		query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1"))
			query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
		query.append(" WHERE refdata.id!=1");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			if (status != null && !"-1".equalsIgnoreCase(status))
			{
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND refdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND refdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
			if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status='" + getStatus() + "' ");
			} else if ("OCC Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");
			} else if ("Dept Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");
			}  else if (!"All".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
			}
		}
		else if (status != null && "-1".equalsIgnoreCase(status))
				{
			query.append(" AND refdata.status IN('Informed','Not Informed','Snooze-I','Snooze') ");
				}
		}
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1"))
			query.append(" And rpd.nursing_unit='"+nursingUnit+"' ");
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' ");
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' And rd1.spec='"+specialty+"' ");
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' And rd1.spec='"+specialty+"' And rpd.nursing_unit='"+nursingUnit+"' ");
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1") && specialty!=null && specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' And rpd.nursing_unit='"+nursingUnit+"' ");
	 
		query.append(" ORDER BY rd1.spec");
		List l2 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		return l2;
	}
	
	
	private List fetchAllReferrence(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT rd.id ,rd.name FROM referral_data AS refdata");
		query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_to=rd.id");
	 	query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
		query.append(" WHERE rd.name IS NOT NULL ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			if (status != null && !"-1".equalsIgnoreCase(status))
			{
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND refdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND refdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
			if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status='" + getStatus() + "' ");
			} else if ("OCC Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");
			}  else if ("Dept Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");
			} else if (!"All".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
			}
			}
			else if (status != null && "-1".equalsIgnoreCase(status))
					{
				query.append(" AND refdata.status IN('Informed','Not Informed','Snooze-I','Snooze') ");
					}
		}
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1"))
			query.append(" And rpd.nursing_unit='"+nursingUnit+"' And rd.spec='"+specialty+"' ");
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1"))
			query.append(" And  rd.spec='"+specialty+"' ");
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1") && specialty!=null && specialty.equalsIgnoreCase("-1"))
			query.append(" And rpd.nursing_unit='"+nursingUnit+"' ");
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1") && refDocTo!=null && refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd.spec='"+specialty+"'  ");
		query.append(" ORDER BY rd.name");
		List l3 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	 	return l3;
	}

	private List fetchAllNursingUnit(String fromDate, String toDate)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT nursing_unit FROM referral_data AS refdata");
		query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
	 	query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id");
		
		query.append(" WHERE rpd.nursing_unit IS NOT NULL ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			String str[] = getFromDate().split("-");
			if (status != null && !"-1".equalsIgnoreCase(status))
			{
			if (str[0] != null && str[0].length() > 3)
			{
				query.append(" AND refdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
			} else
			{
				query.append(" AND refdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
			}
			if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status='" + getStatus() + "' ");
			} else if ("OCC Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");
			}  else if ("Dept Escalate".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");
			} else if (!"All".equalsIgnoreCase(status))
			{
				query.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
			}
			}
			else if (status != null && "-1".equalsIgnoreCase(status))
					{
				query.append(" AND refdata.status IN('Informed','Not Informed','Snooze-I','Snooze') ");
					}
		}
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' ");
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' And rd1.spec='"+specialty+"' ");
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' And rd1.spec='"+specialty+"' And rpd.nursing_unit='"+nursingUnit+"' ");
		if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("-1") && specialty!=null && specialty.equalsIgnoreCase("-1") && refDocTo!=null && !refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.id='"+refDocTo+"' And rpd.nursing_unit='"+nursingUnit+"' ");
		if(nursingUnit!=null && nursingUnit.equalsIgnoreCase("-1") && specialty!=null && !specialty.equalsIgnoreCase("-1") && refDocTo!=null && refDocTo.equalsIgnoreCase("-1"))
			query.append(" And rd1.spec='"+specialty+"' ");
	
		query.append(" ORDER BY nursing_unit ");
	 	List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		return l1;
	}

	public String beforeReferralView()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				setGridColumns();
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

	public String[] getSubModuleRights(String userId)
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
	
	// Setting activity board column names
	public void setGridColumns()
	{
		String empid=(String)session.get("empName").toString();
		String[] subModuleRightsArray = getSubModuleRights(empid);
		try
		{
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_referral_configuration", accountID, connectionSpace, "", 0, "table_name", "referral_configuration");
			GridDataPropertyView gpv = null;
			if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
			{
				for(String s:subModuleRightsArray)
				{
					if(s.equals("action_VIEW"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName("action");
						gpv.setHeadingName("Action");
						gpv.setFormatter("takeAction");
						gpv.setWidth(30);
						viewPageColumns.add(gpv);
					}
					if(s.equals("escalate_VIEW"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName("escalate");
						gpv.setHeadingName("Escalate");
						gpv.setFormatter("escalateAction");
						gpv.setWidth(30);
						viewPageColumns.add(gpv);
					}
					
				}
			}
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ref_Id");
			gpv.setHeadingName("ref_Id");
			gpv.setWidth(30);
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);
			for (GridDataPropertyView gdp : returnResult)
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
				if (gdp.getColomnName().equalsIgnoreCase("uhid"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("patient_name");
					gpv.setHeadingName("Patient Name");
					gpv.setWidth(100);
					viewPageColumns.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("bed");
					gpv.setHeadingName("Bed");
					gpv.setWidth(50);
					viewPageColumns.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("nursing_unit");
					gpv.setHeadingName("Nursing Unit");
					gpv.setWidth(80);
					viewPageColumns.add(gpv);
				} 
				else if (gdp.getColomnName().equalsIgnoreCase("ref_by"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("spec");
					gpv.setHeadingName("Specialty");
					gpv.setWidth(110);
					viewPageColumns.add(gpv);
				} else if (gdp.getColomnName().equalsIgnoreCase("ref_to"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("ref_spec");
					gpv.setHeadingName("Specialty");
					gpv.setWidth(110);
					viewPageColumns.add(gpv);
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("ref_to_sub_spec");
					gpv.setHeadingName("Sub Specialty");
					gpv.setWidth(100);
					viewPageColumns.add(gpv);
				}
				 
			}
			gpv = new GridDataPropertyView();
			gpv.setColomnName("assign_to_name");
			gpv.setHeadingName("Informed To");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);

			 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("informed_at");
			gpv.setHeadingName("Action At");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("caller_emp_id");
			gpv.setHeadingName("Emp Id");
			gpv.setHideOrShow("true");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);

			 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("caller_emp_name");
			gpv.setHeadingName("Emp Name");
			gpv.setHideOrShow("true");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);
			
			
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("noresponse_flag");
			gpv.setHeadingName("noresponse");
			//gpv.setFormatter("noresponseData");
			gpv.setHideOrShow("true");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// View Data in activity board
	@SuppressWarnings({ "rawtypes", "unused" })
	public String viewReferralData()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from referral_data ", connectionSpace);
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
				query.append("SELECT refdata.id,refdata.ticket_no,refdata.status,refdata.open_date,refdata.open_time,refdata.escalate_date,refdata.escalate_time," + "refdata.level,refdata.priority,rpd.uhid,rd.name,rd1.name AS ref_to,refdata.reason,rpd.patient_name,rpd.bed,rpd.nursing_unit,rd.spec,rd1.spec" + " AS ref_spec,refdata.ref_to_sub_spec,rd1.id AS refId,rdh.sn_upto_date,rdh.sn_upto_time,rdh.assign_to_name,rdh.action_date,rdh.action_time,refdata.caller_emp_id,refdata.caller_emp_name ,refdata.no_response_flag FROM referral_data AS refdata ");

				query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
				query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
				query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
				query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
				if(status!=null && ("SnoozeH".equalsIgnoreCase(getStatus()) || "SnoozeI".equalsIgnoreCase(getStatus())))
				{
					query.append(" WHERE refdata.id!=0");
				}
				else
				{
					query.append(" WHERE refdata.status=rdh.status ");
				}
				
				if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase("") && status != null && !"-1".equalsIgnoreCase(status))
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" AND refdata.open_date BETWEEN '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
					} else
					{
						query.append(" AND refdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
					}
				}
				if (getSearchString() != null && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" AND (refdata.ticket_no  LIKE '%" + getSearchString() + "%'");
					query.append(" OR refdata.open_date  LIKE '%" + getSearchString() + "%'");
					query.append(" OR rpd.uhid  LIKE '%" + getSearchString() + "%'");
					query.append(" OR rpd.patient_name  LIKE '%" + getSearchString() + "%'");
					query.append(" OR rpd.bed  LIKE '%" + getSearchString() + "%'");
					query.append(" OR rpd.nursing_unit  LIKE '%" + getSearchString() + "%'");
					query.append(" OR refdata.status  LIKE '" + getSearchString() + "%')");
				}
				if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status)&& !"SnoozeH".equalsIgnoreCase(status)&& !"SnoozeI".equalsIgnoreCase(status))
				{
					query.append(" AND refdata.status='" + getStatus() + "' ");
					
				} else if ("OCC Escalate".equalsIgnoreCase(status))
				{
					query.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");
					
				} else if ("Dept Escalate".equalsIgnoreCase(status))
				{
					query.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");
					
				} else if ("SnoozeH".equalsIgnoreCase(getStatus()))
				{
					query.append(" AND rdh.status = 'Snooze'");
					
				}else if ("SnoozeI".equalsIgnoreCase(getStatus()))
				{
					query.append(" AND rdh.status = 'Snooze-I'");
				}
				else if (!"All".equalsIgnoreCase(status))
				{
					query.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
				}
				else if ("All".equalsIgnoreCase(status))
				{
					//query.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
				}
				else{
					
					query.append(" AND refdata.status IN('Not Informed','Snooze','Snooze-I', 'Seen') ");
				}
				if (priority != null && !"-1".equalsIgnoreCase(priority))
				{
					query.append(" AND refdata.priority='" + getPriority() + "' ");
				}
				if (nursingUnit != null && !"-1".equalsIgnoreCase(nursingUnit))
				{
					query.append(" AND rpd.nursing_unit='" + getNursingUnit() + "' ");
				}
				

				if (refDocTo != null && !"-1".equalsIgnoreCase(refDocTo))
				{
					query.append(" AND rd1.id='"+ getRefDocTo() +"' ");
				}
				
				
				if (specialty != null && !"-1".equalsIgnoreCase(specialty))
				{
					query.append(" AND rd1.spec='" + getSpecialty() + "' ");
				}
				if (level != null && !"-1".equalsIgnoreCase(level))
				{
					query.append(" AND refdata.level='" + getLevel() + "' ");
				}
				//View login for subdet by 
				String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
				String[] subModuleRightsArray = getSubModuleRights(empid);
				if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for(String s:subModuleRightsArray)
					{
						if(s.equals("deptView_VIEW") && locationWise!=null && locationWise.equalsIgnoreCase("No"))
						{
							String subDept=null;
							ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
							subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		 				 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
							{
					 		  query.append(" and rd1.spec in ( "+subDept+" )");
							}
							else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
							{
					 			  query.append(" and rd.spec in ( "+subDept+" )");	
							}
					 		 
						}
						
						if(s.equals("locationView_VIEW"))
						{
							ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
							String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if(nursingUnit!=null && !nursingUnit.contains("All"))
								query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
						}
						if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
						{
						if(s.equals("locationManagerView_VIEW"))
						{
							System.out.println(locationWise+"  "+s);
							ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
							String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if(nursingUnit!=null)
								query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
						}
						}
						 
					}
				}

				query.append(" GROUP BY refdata.id ORDER BY  refdata.escalate_date , refdata.escalate_time , refdata.id  DESC , refdata.priority  DESC  ");
				// System.out.println("query ::::::"+query);
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					List<Object> tempList = new ArrayList<Object>();
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						//System.out.println("object[0]   "+object[0].toString());
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
							tempMap.put("status", "Cancel");
						}
						else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Seen"))
						{
							tempMap.put("status", "Close");
						}else
						{
							tempMap.put("status", getValueWithNullCheck(object[2]));
						}
						tempMap.put("open_date", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[3].toString())) + ", " + getValueWithNullCheck(object[4]).substring(0, 5));
						 
						if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Not Informed") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Informed"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[5].toString(), object[6].toString()));
						} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[20].toString(), object[21].toString()));
						}
						else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Seen"))
						{
				 			tempMap.put("escalate_date", DateUtil.time_difference( object[5].toString(), object[4].toString(),object[23].toString(), object[24].toString()));
						}
						else
						{
					 		tempMap.put("escalate_date", "00:00");
						}
						tempMap.put("level", getValueWithNullCheck(object[7]));
						tempMap.put("priority", getValueWithNullCheck(object[8]));
						tempMap.put("uhid", getValueWithNullCheck(object[9]));
						tempMap.put("ref_by", getValueWithNullCheck(object[10]));
						tempMap.put("ref_to", getValueWithNullCheck(object[11]));
						tempMap.put("reason", getValueWithNullCheck(object[12]));
						tempMap.put("patient_name", getValueWithNullCheck(object[13]));
						tempMap.put("bed", getValueWithNullCheck(object[14]));
						tempMap.put("nursing_unit", getValueWithNullCheck(object[15]));
						tempMap.put("spec", getValueWithNullCheck(object[16]));
						tempMap.put("ref_spec", getValueWithNullCheck(object[17]));
						tempMap.put("ref_to_sub_spec", getValueWithNullCheck(object[18]));
						tempMap.put("ref_Id", getValueWithNullCheck(object[19]));
						tempMap.put("assign_to_name", getValueWithNullCheck(object[22]));
					 	tempMap.put("informed_at", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[23].toString())) + ", " + getValueWithNullCheck(object[24]).substring(0, 5));
						tempMap.put("caller_emp_id", getValueWithNullCheck(object[25]));
						tempMap.put("caller_emp_name", getValueWithNullCheck(object[26]));
						tempMap.put("noresponse_flag", getValueWithNullCheck(object[27]));
					 
					 	tempList.add(tempMap);
					}
					setViewReferralData(tempList);
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

	@SuppressWarnings("unchecked")
	public String getCurrentColumnRefferal()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();

				columnMap.put("refdata.id", "ID");
				columnMap.put("refdata.ticket_no", "Ticket No");
				columnMap.put("refdata.status", "Status");
				columnMap.put("refdata.open_date", "Open Date");
				columnMap.put("refdata.open_time", "Open Time");
				columnMap.put("refdata.escalate_date", "Escalate Date");
				columnMap.put("refdata.escalate_time", "Escalate Time");
				columnMap.put("refdata.level", "Level");
				columnMap.put("refdata.priority", "Priority");
				columnMap.put("rd.name", "Referred By");
				columnMap.put("rd.spec", "Speciality");
				columnMap.put("rd1.name AS refTo", "Referred To");
				columnMap.put("rd1.spec AS refSpec", "Speciality");
				columnMap.put("refdata.ref_to_sub_spec", "Sub Specialty");
				columnMap.put("rpd.uhid", "UHID");
				columnMap.put("rpd.patient_name", "Patient Name");
				columnMap.put("rpd.bed", "Bed");
				columnMap.put("rpd.nursing_unit", "Nursing Unit");
				columnMap.put("rdh.assign_to_id", "Assign To ID");
				columnMap.put("rdh.assign_to_name", "Informed/Seen/Ignore/Park By");
				columnMap.put("rdh.comments", "Remarks");
				columnMap.put("rdh.assign_desig", "Close By Designation");
				columnMap.put("rdh.assign_close_by", "Close By");

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadExcel()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Referral Detail";

		if (sessionFlag)
		{
			try
			{

				List keyList = new ArrayList();
				List titleList = new ArrayList();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userType = null, empId = null, userMobno = null;
				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();
				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

				StringBuilder query = new StringBuilder();
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userMobno = CUA.getValueWithNullCheck(object[1]);
						toDepart = object[3].toString();
					}
				}

				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					List dataList = null;
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						query.append("SELECT DISTINCT ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								} else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" FROM referral_data AS refdata ");
						query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id ");
						query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
						query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
						query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
						if(status!=null && ("SnoozeH".equalsIgnoreCase(getStatus()) || "SnoozeI".equalsIgnoreCase(getStatus())))
						{
							query.append(" WHERE refdata.id!=0");
						}
						else
						{
							query.append(" WHERE refdata.status=rdh.status ");
						}

						if ((fromDate != null && toDate != null))
						{
							String str[] = toDate.split("-");
							if (str[0].length() < 4)
								query.append("  AND refdata.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							else
								query.append("  AND refdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
						}

						if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status)&& !"SnoozeH".equalsIgnoreCase(status)&& !"SnoozeI".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status='" + getStatus() + "' ");
							
						} else if ("OCC Escalate".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");
							
						} else if ("Dept Escalate".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");
							
						} else if ("SnoozeH".equalsIgnoreCase(getStatus()))
						{
							query.append(" AND rdh.status = 'Snooze'");
							
						}else if ("SnoozeI".equalsIgnoreCase(getStatus()))
						{
							query.append(" AND rdh.status = 'Snooze-I'");
						}
						else if (!"All".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
						}
						if (priority != null && !"-1".equalsIgnoreCase(priority))
						{
							query.append(" AND refdata.priority='" + getPriority() + "' ");
						}
						if (nursingUnit != null && !"-1".equalsIgnoreCase(nursingUnit))
						{
							query.append(" AND rpd.nursing_unit='" + getNursingUnit() + "' ");
						}
						if (specialty != null && !"-1".equalsIgnoreCase(specialty))
						{
							query.append(" AND rd1.spec='" + getSpecialty() + "' ");
						}
						if (level != null && !"-1".equalsIgnoreCase(level))
						{
							query.append(" AND refdata.level='" + getLevel() + "' ");
						}

						if (getSearchString() != null && getSearchString() != null && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
						{
							// add search query based on the search operation
							query.append("AND refdata." + getSearchString() + " like '" + getSearchString() + "%'");
						}

						if (getSearchString() != null && getSearchString() != null && getSearchString().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
						{
							query.append("AND (refdata.ticket_no  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.fromDate  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.toDate  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.status  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.spec like '%" + getSearchString() + "%'");
							query.append(" OR refdata.nursingUnit  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.priority1  like '" + getSearchString() + "%'");

						}

						String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
						String[] subModuleRightsArray = getSubModuleRights(empid);
						System.out.println("locationWise  "+locationWise);
						if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
						{
							for(String s:subModuleRightsArray)
							{
								if(s.equals("deptView_VIEW") && locationWise!=null && locationWise.equalsIgnoreCase("No"))
								{
									String subDept=null;
									ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
									subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
				 				 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
									{
							 		  query.append(" and rd1.spec in ( "+subDept+" )");
									}
									else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
									{
							 			  query.append(" and rd.spec in ( "+subDept+" )");	
									}
							 		 
								}
								if(s.equals("locationView_VIEW"))
								{
									ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
									String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
									if(nursingUnit!=null && !nursingUnit.contains("All"))
										query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
								}
								if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
								{
								if(s.equals("locationManagerView_VIEW"))
								{
									ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
									String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
									if(nursingUnit!=null)
										query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
								}
								}
							}
						}
						// //// //System.out.println("query%%%%%   " + query);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
						{

							excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);
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
	
 
	
	public String getCurrentRefferal()
	{


		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("id>>>   "+idAll);
				columnMap = new LinkedHashMap<String, String>();
 
				columnMap.put("refdata.id", "ID");
				columnMap.put("refdata.ticket_no", "Call No");
				columnMap.put("refdata.open_date", "Call Date");
				columnMap.put("refdata.open_time", "Call Time");
				columnMap.put("refdata.caller_emp_id", "Caller ID");
				columnMap.put("refdata.caller_emp_name", "Caller Name");
				columnMap.put("rpd.uhid", "UHID");
				columnMap.put("rpd.patient_name", "Patient Name");
				columnMap.put("rpd.bed", "Bed No");
				columnMap.put("rpd.nursing_unit", "Nursing Unit");
				columnMap.put("rpd.adm_doc", "Adm. Doc");
				columnMap.put("rpd.adm_spec", "Adm. Spec");
				columnMap.put("rd.name", "Ref. Doc");
				columnMap.put("rd.spec", "Ref. Spec.");
				columnMap.put("rd1.name AS refTo", "Refd. Doc");
				columnMap.put("rd1.spec AS refSpec", "Refd. Spec.");
				columnMap.put("refdata.ref_to_sub_spec", "Refd. Sub. Spec");
				columnMap.put("refdata.priority", "Priority");
				columnMap.put("refdata.reason", "Reason");
				columnMap.put("emp.empName as refBy", "Ref. Added By");
				columnMap.put("refdata.escalate_date", "Ref. Add. Date");
				columnMap.put("refdata.escalate_time", "Ref. Add. Time");
				columnMap.put("rdh.action_date", "Informed Date");
				columnMap.put("rdh.action_time", "Informed Time");
				columnMap.put("rdh1.assign_to_id", "Informed To Id");
				columnMap.put("rdh1.assign_to_name", "Informed To Name");
				columnMap.put("rdh.action_by", "Informed Action By");

				columnMap.put("rdh.comments", "Remarks");
				columnMap.put("rdh.status", "Status");
				//
				columnMap.put("emp.empName", "Action By");

				columnMap.put("rdh1.action_date as actDate", "First Esclated On");
				columnMap.put("rdh1.action_time as actTime", "First Escalate Time");
				columnMap.put("rdh1.action_by", "First Esclated By");
				columnMap.put("rdh1.level", "First Esclated to");
				columnMap.put("rdh1.comments", "First Esclated Remarks");

				columnMap.put("rdh2.action_date as actDate", "Second Esclated On");
				columnMap.put("rdh2.action_time as actTime", "Second Escalate Time");
				columnMap.put("rdh2.action_by", "Second Esclated By");
				columnMap.put("rdh2.level", "Second Esclated to");
				columnMap.put("rdh2.comments", "Second Esclated Remarks");

				columnMap.put("rdh3.action_date as actDate", "Third Esclated On");
				columnMap.put("rdh3.action_time as actTime", "Third Escalate Time");
				columnMap.put("rdh3.action_by", "Third Esclated By");
				columnMap.put("rdh3.level", "Third Esclated to");
				columnMap.put("rdh3.comments", "Third Esclated Remarks");

				columnMap.put("rdh4.action_date as actDate", "Fourth Esclated On");
				columnMap.put("rdh4.action_time as actTime", "Fourth Escalate Time");
				columnMap.put("rdh4.action_by", "Fourth Esclated By");
				columnMap.put("rdh4.level", "Fourth Esclated to");
				columnMap.put("rdh4.comments", "Fourth Esclated Remarks");

				columnMap.put("rdh5.action_date as actDate", "Fifth Esclated On");
				columnMap.put("rdh5.action_time as actTime", "Fifth Escalate Time");
				columnMap.put("rdh5.action_by", "Fifth Esclated By");
				columnMap.put("rdh5.level", "Fifth Esclated to");
				columnMap.put("rdh5.comments", "Fifth Esclated Remarks");

				columnMap.put("rdh.assign_to_id as ID", "Seen By ID");
				columnMap.put("rdh.assign_to_name as asName", "Seen By Name");
				columnMap.put("rdh.assign_desig", "Seen By Desg");
				columnMap.put("rdh.assign_close_by", "Closed By");
				columnMap.put("rdh.sn_upto_date", "Seen Add. Date");
				columnMap.put("rdh.sn_upto_time", "Seen Add. Time");
				columnMap.put("rdh8.action_by", "Seen Action By");
				columnMap.put("rdh.resolution_time", "Resolution Time");
				columnMap.put("rdh1.status as no_response", "No Response");

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
	{
System.out.println("Kadir");

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Referral Detail";

		if (sessionFlag)
		{
			try
			{

				List keyList = new ArrayList();
				List titleList = new ArrayList();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

				StringBuilder query = new StringBuilder();
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						toDepart = object[3].toString();
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
						query.append("Select rdh1.action_date,rdh1.action_time,rdh1.escalate_to,rdh1.escalate_to_mob,rdh1.comments,emp.empName,rid from referral_data_history as rdh1 INNER JOIN employee_basic AS emp ON emp.id=rdh1.action_by  where rdh1.status Like 'Escal%' order by rdh1.id asc");
						List escdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query = null;
						query = new StringBuilder();

						query.append(" Select his.assign_to_id,his.assign_to_name,his.assign_desig,his.assign_close_by,his.action_date, his.action_time,emp.empName,rid  from referral_data_history as his  inner join employee_basic as emp on emp.id = his.action_by where his.status like 'See%' order by his.rid asc");
						List resdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query = null;
						query = new StringBuilder();

						query.append("Select his_ref.action_date,his_ref.action_time,his_ref.assign_to_id,his_ref.assign_to_name, emp.empName,rid from referral_data_history as his_ref inner join employee_basic as emp on emp.id=his_ref.action_by where his_ref.status like 'Infor%' order by his_ref.rid asc ");
						List informeddatalist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query = null;
						query = new StringBuilder();

						query.append("Select rdh1.status,rdh1.id,rid from referral_data_history as rdh1 INNER JOIN employee_basic AS emp ON emp.id=rdh1.action_by  where rdh1.status='No Response' order by rdh1.id asc ");
						List noreslist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query = null;
						query = new StringBuilder();

						query.append("SELECT DISTINCT ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)// rdh1.status
							{
								if (!obdata.toString().equalsIgnoreCase("rdh1.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh2.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh3.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh4.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh5.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh1.action_time as actTime")
										&& !obdata.toString().equalsIgnoreCase("rdh2.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh3.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh4.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh5.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh1.level") && !obdata.toString().equalsIgnoreCase("rdh2.level") && !obdata.toString().equalsIgnoreCase("rdh3.level")
										&& !obdata.toString().equalsIgnoreCase("rdh4.level") && !obdata.toString().equalsIgnoreCase("rdh5.level") && !obdata.toString().equalsIgnoreCase("rdh1.comments") && !obdata.toString().equalsIgnoreCase("rdh2.comments") && !obdata.toString().equalsIgnoreCase("rdh3.comments") && !obdata.toString().equalsIgnoreCase("rdh4.comments") && !obdata.toString().equalsIgnoreCase("rdh5.comments") && !obdata.toString().equalsIgnoreCase("rdh1.action_by")
										&& !obdata.toString().equalsIgnoreCase("rdh2.action_by") && !obdata.toString().equalsIgnoreCase("rdh3.action_by") && !obdata.toString().equalsIgnoreCase("rdh4.action_by") && !obdata.toString().equalsIgnoreCase("rdh5.action_by") && !obdata.toString().equalsIgnoreCase("rdh.resolution_time") && !obdata.toString().equalsIgnoreCase("rdh.assign_to_id as ID") && !obdata.toString().equalsIgnoreCase("rdh.assign_to_name as asName")
										&& !obdata.toString().equalsIgnoreCase("rdh.assign_desig") && !obdata.toString().equalsIgnoreCase("rdh.assign_close_by") && !obdata.toString().equalsIgnoreCase("rdh.sn_upto_date") && !obdata.toString().equalsIgnoreCase("rdh.sn_upto_time") && !obdata.toString().equalsIgnoreCase("rdh8.action_by") && !obdata.toString().equalsIgnoreCase("rdh1.status as no_response"))
								{
									if (i < keyList.size() - 1)
									{
										if (obdata.toString().equalsIgnoreCase("emp.empName"))
										{
											query.append(obdata.toString());
										} else
										{
											query.append(obdata.toString() + ",");
										}
									} else
									{
										query.append(obdata.toString());
									}
								}
							}
							i++;
						}
						query.append(" FROM referral_data AS refdata ");
						query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id ");
						query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
						query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
						query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
						query.append(" INNER JOIN referral_data_history AS rdh1 ON rdh1.rid=refdata.id ");
						query.append(" INNER JOIN employee_basic AS emp ON emp.id=rdh1.action_by ");

						 

						if (status != null && ("SnoozeH".equalsIgnoreCase(getStatus()) || "SnoozeI".equalsIgnoreCase(getStatus())))
						{
							query.append(" WHERE refdata.id!=0");
						} else
						{
							query.append(" WHERE refdata.status=rdh.status ");
						}

						if ((fromDate != null && toDate != null && !"-1".equalsIgnoreCase(status)))
						{
							String str[] = toDate.split("-");
							if (str[0].length() < 4)
							{
								query.append("  AND refdata.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							} else
							{
								query.append("  AND refdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
							}
						}

						if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status) && !"SnoozeH".equalsIgnoreCase(status) && !"SnoozeI".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status='" + getStatus() + "' ");

						}

						else if ("-1".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status NOT IN('Ignore','Ignore-I') ");

						} else if ("Escalate".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");

						}

						else if ("Dept Escalate".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");

						} else if ("SnoozeH".equalsIgnoreCase(getStatus()))
						{
							query.append(" AND rdh.status = 'Snooze'");

						} else if ("SnoozeI".equalsIgnoreCase(getStatus()))
						{
							query.append(" AND rdh.status = 'Snooze-I'");
						} else if (!"All".equalsIgnoreCase(status))
						{
							query.append(" AND refdata.status Is NOT Null ");
						}
						if (priority != null && !"-1".equalsIgnoreCase(priority))
						{
							query.append(" AND refdata.priority='" + getPriority() + "' ");
						}
						if (id != null && id.trim().length() > 0 && !id.trim().equalsIgnoreCase("") && !id.equalsIgnoreCase("ubdefined"))
						{
							query.append(" AND refdata.id In (" + this.getId() + ") ");
						}
						if (nursingUnit != null && !"-1".equalsIgnoreCase(nursingUnit))
						{
							query.append(" AND rpd.nursing_unit='" + getNursingUnit() + "' ");
						}
						if (specialty != null && !"-1".equalsIgnoreCase(specialty))
						{
							query.append(" AND rd1.spec='" + getSpecialty() + "' ");
						}
						if (level != null && !"-1".equalsIgnoreCase(level))
						{
							query.append(" AND refdata.level='" + getLevel() + "' ");
						}

						 
						if (getSearchString() != null && getSearchString() != null && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined") && searchString.trim().length() > 0)
						{
							query.append("AND refdata.ticket_no  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.open_date  like '%" + getSearchString() + "%'");
					 		query.append(" OR refdata.status  like '%" + getSearchString() + "%'");
							query.append(" OR rd1.spec like '%" + getSearchString() + "%'");
							query.append(" OR rpd.nursing_unit  like '%" + getSearchString() + "%'");
							query.append(" OR refdata.priority  like '" + getSearchString() + "%'");

						}
					 	String empid = (String) session.get("empName").toString();// .trim().split("-")[1];
						String[] subModuleRightsArray = getSubModuleRights(empid);
						if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
						{
							for (String s : subModuleRightsArray)
							{
								if(s.equals("deptView_VIEW") && locationWise!=null && locationWise.equalsIgnoreCase("No"))
								{
									String subDept=null;
									ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
									subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
				 				 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
									{
							 		  query.append(" and rd1.spec in ( "+subDept+" )");
									}
									else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
									{
							 			  query.append(" and rd.spec in ( "+subDept+" )");	
									}
							 		 
								}
								if(s.equals("locationView_VIEW"))
								{
									ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
									String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
									if(nursingUnit!=null && !nursingUnit.contains("All"))
										query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
								}
								if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
								{
								if(s.equals("locationManagerView_VIEW"))
								{
									ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
									String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
									if(nursingUnit!=null)
										query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
								}
								}
							}
						}

						query.append(" GROUP BY refdata.id ORDER BY refdata.id DESC ");
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
						{
							 	excelFileName = new ReferralExcelDownload().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace, escdataList, resdataList, informeddatalist, noreslist);
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
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public List<Object> getViewReferralData()
	{
		return viewReferralData;
	}

	public void setViewReferralData(List<Object> viewReferralData)
	{
		this.viewReferralData = viewReferralData;
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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getNursingUnit()
	{
		return nursingUnit;
	}

	public void setNursingUnit(String nursingUnit)
	{
		this.nursingUnit = nursingUnit;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public List<String> getDataList()
	{
		return dataList;
	}

	public void setDataList(List<String> dataList)
	{
		this.dataList = dataList;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public String getSpecialty()
	{
		return specialty;
	}

	public void setSpecialty(String specialty)
	{
		this.specialty = specialty;
	}

	public List<String> getNuDataList()
	{
		return nuDataList;
	}

	public void setNuDataList(List<String> nuDataList)
	{
		this.nuDataList = nuDataList;
	}

	public String getToDepart()
	{
		return toDepart;
	}

	public void setToDepart(String toDepart)
	{
		this.toDepart = toDepart;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}


	public String getUhidStatus() {
		return uhidStatus;
	}


	public void setUhidStatus(String uhidStatus) {
		this.uhidStatus = uhidStatus;
	}


	public List<GridDataPropertyView> getViewPageUHIDColumns() {
		return viewPageUHIDColumns;
	}


	public void setViewPageUHIDColumns(
			List<GridDataPropertyView> viewPageUHIDColumns) {
		this.viewPageUHIDColumns = viewPageUHIDColumns;
	}


	public String getReffby() {
		return reffby;
	}


	public void setReffby(String reffby) {
		this.reffby = reffby;
	}


	public List<String> getRefbyList() {
		return refbyList;
	}


	public void setRefbyList(List<String> refbyList) {
		this.refbyList = refbyList;
	}


	public String getRefDocTo() {
		return refDocTo;
	}


	public void setRefDocTo(String refDocTo) {
		this.refDocTo = refDocTo;
	}


	public String getRefDoc() {
		return refDoc;
	}


	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}


	public String getDepartmentView() {
		return departmentView;
	}


	public void setDepartmentView(String departmentView) {
		this.departmentView = departmentView;
	}


	public String getLocationWise() {
		return locationWise;
	}


	public void setLocationWise(String locationWise) {
		this.locationWise = locationWise;
	}


	public String getIdAll() {
		return idAll;
	}


	public void setIdAll(String idAll) {
		this.idAll = idAll;
	}

 
	
	
}
