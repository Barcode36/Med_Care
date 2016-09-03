package com.Over2Cloud.ctrl.order.pharmacy;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.BeanUtil.UserRights;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.criticalPatient.CriticalExcelDownload;

@SuppressWarnings("serial")
public class ActivityBoardGridView extends GridPropertyBean implements ServletRequestAware
{
	private String maxDateValue;
	private String minDateValue;
	private String feedStatus;
	private String escLevel;
	private String escTAT;
	private String complianId;
	private Map<String, String> dataCountMap;
	private List<Object> masterViewProp = new ArrayList<Object>();
	private String dataWild;
	public String priority;
	private String nursingUnit;
	private Map<String, String> dataMap;
	private Map<String, String> dataMap1;
	List<MedicinePojo> data_list = null;
	List medicineList = null;
	private String uhid;
	private String patientName;
	private String roomNo;
	private String encounterId;
	private HttpServletRequest request;
	private ArrayList<ArrayList<String>> ordHisList;
	private JSONArray commonJSONArray;
	private Map<String, String> serviceDeptMap;
	private String deptId;
	private String subDeptId;
	private Map<String, String> dataListMap;
	private String medicine_order_id;
	private String ordId;
	private String recId;
	private String disId;
	private String nursingCode;
	private Map<String, String> columnMap;
	private FileInputStream excelStream;
	private String excelFileName;
	private String[] columnNames;

	public String updateDropFiltersForPharmacy()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				commonJSONArray = new JSONArray();
				JSONObject ob1 = null;
				List l1 = fetchAllList();
				String uhid = "", patient_name = "", assign_bed_num = "", nursing_unit = "", encounter_id = "";
				if (l1 != null && l1.size() > 0)
				{
					ob1 = new JSONObject();
					for (Iterator iterator1 = l1.iterator(); iterator1.hasNext();)
					{
						Object[] object = (Object[]) iterator1.next();

						if (object[0] != null)
						{

							if (!uhid.equalsIgnoreCase(object[1].toString()))
							{
								ob1.put("id1", object[0].toString());
								ob1.put("uhid", object[1].toString());
							}
							if (!patient_name.equalsIgnoreCase(object[3].toString()))
							{
								ob1.put("id2", object[2].toString());
								ob1.put("patient_name", object[3].toString());
							}
							if (!assign_bed_num.equalsIgnoreCase(object[5].toString()))
							{
								ob1.put("id3", object[4].toString());
								ob1.put("assign_bed_num", object[5].toString());
							}
							if (!nursing_unit.equalsIgnoreCase(object[7].toString()))
							{
								ob1.put("id4", object[6].toString());
								ob1.put("nursing_unit", object[7].toString());
							}
							if (!encounter_id.equalsIgnoreCase(object[9].toString()))
							{
								ob1.put("id5", object[8].toString());
								ob1.put("encounter_id", object[9].toString());
							}
							commonJSONArray.add(ob1);
							uhid = object[1].toString();
							patient_name = object[3].toString();
							assign_bed_num = object[5].toString();
							nursing_unit = object[7].toString();
							encounter_id = object[9].toString();
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

	private List fetchAllList()
	{
		List data = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data = cbt.executeAllSelectQuery("select ord.uhid as Id1,ord.uhid as Name1, ord.patient_name as Id2,ord.patient_name as Name2, ord.assign_bed_num as Id3,ord.assign_bed_num as Name3,ord.nursing_unit as Id4,ord.nursing_unit as Name4,ord.encounter_id as Id5,ord.document_no as Name5 from pharma_patient_detail as ord where ord.order_date between '" + DateUtil.convertDateToUSFormat(minDateValue) + "' and '" + DateUtil.convertDateToUSFormat(maxDateValue) + "' group by ord.uhid", connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public String beforeActivityBoardDataForPharmacy()
	{
		try
		{
			
			String userRights = (String) session.get("userRights");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			if (userRights != null && (userRights.contains("Pharma_Close_Action_VIEW") || userRights.contains("Pharma_Ordered_Action_VIEW")))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("action");
				gpv.setHeadingName("Action");
				gpv.setFormatter("takeActionForPharmacy");
				gpv.setHideOrShow("false");
				gpv.setWidth(40);
				viewPageColumns.add(gpv);
			}

			gpv = new GridDataPropertyView();
			gpv.setColomnName("encounter_id");
			gpv.setHeadingName("Ticket No");
			gpv.setFormatter("pharmaHistory");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("document_no");
			gpv.setHeadingName("Document No");
			// gpv.setFormatter("pharmaHistory");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("timer");
			gpv.setHeadingName("Timer");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			gpv.setFormatter("pharmaPriorityColorCode");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setFormatter("pharmaLevelColorCode");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("true");
			gpv.setWidth(100);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("order");
			gpv.setHeadingName("Order At");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("uhid");
			gpv.setHeadingName("UHID");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("patient_name");
			gpv.setHeadingName("Patient Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("assign_bed_num");
			gpv.setHeadingName("Bed No");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("nursing_unit");
			gpv.setHeadingName("Nursing Unit");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("Item");
			gpv.setHeadingName("Medicine");
			gpv.setHideOrShow("false");
			gpv.setFormatter("takeMedicineDetail");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("admitting_pra_name");
			gpv.setHeadingName("Admitting Doctor");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("attending_pra_name");
			gpv.setHeadingName("Attending Doctor");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("speciality");
			gpv.setHeadingName("Speciality");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);

			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public String viewActivityBoardDataForPharmacy()
	{
		// //System.out.println("min date "+minDateValue+"  maxDate:   "+maxDateValue);
		//String userRights = session.getAttribute("userRights");
		//String userRights = ((ServletRequest) session).getAttribute("userRights") == null ? "" : ((ServletRequest) session).getAttribute("userRights").toString();
		String userRights = (String) session.get("userRights");
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from pharma_patient_detail", connectionSpace);
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
				List<Object> tempList = new ArrayList<Object>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT ord.id,ord.uhid as patientID,ord.patient_name,ord.assign_bed_num,ord.level,ord.status,ord.priority,ord.nursing_unit,ord.admitting_pra_name,ord.attending_pra_name,ord.speciality,ord.encounter_id,ord.escalation_date,ord.escalation_time,ord.order_date,ord.order_time, ord.document_no ");
				query.append(" from pharma_patient_detail as ord");

				if(dataWild!=null && !dataWild.equalsIgnoreCase("") && !dataWild.equalsIgnoreCase(" ") && !dataWild.equalsIgnoreCase("-1"))
		 	  	{
		 	  	query.append(" where ord.document_no = '"+dataWild+"' ");
		 	  	} 
				else
				{
				
				if ((minDateValue != null && maxDateValue != null))
				{
					String str[] = minDateValue.split("-");
					if (str[0].length() < 4)
					{
						if (feedStatus != null && !feedStatus.equalsIgnoreCase("All") && !feedStatus.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("undefined"))
							query.append(" where ord.order_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
						else
							query.append(" where ord.order_date BETWEEN '" + DateUtil.getNewDate("day", -1, DateUtil.convertDateToUSFormat(minDateValue)) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");

					} else
					{
						if (feedStatus != null && !feedStatus.equalsIgnoreCase("All") && !feedStatus.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("undefined"))
							query.append(" where ord.order_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
						else
							query.append(" where ord.order_date BETWEEN '" + DateUtil.getNewDate("day", -1, minDateValue) + "' AND '" + maxDateValue + "'");
					}

				}
				if (nursingUnit != null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase(" ") && !nursingUnit.equalsIgnoreCase("-1"))
				{
					query.append(" and ord.nursing_unit='" + nursingUnit + "'");
				}
				if (priority != null && !priority.equalsIgnoreCase("All"))
				{
					query.append(" and ord.priority = '" + priority + "'");
				}
				if (patientName != null && !patientName.equalsIgnoreCase("") && !patientName.equalsIgnoreCase(" ") && !patientName.equalsIgnoreCase("-1"))
				{
					query.append(" and ord.patient_Name = '" + patientName + "'");
				}
				if (uhid != null && !uhid.equalsIgnoreCase("") && !uhid.equalsIgnoreCase(" ") && !uhid.equalsIgnoreCase("-1"))
				{
					query.append(" and ord.uhid = '" + uhid + "'");
				}
				if (roomNo != null && !roomNo.equalsIgnoreCase("") && !roomNo.equalsIgnoreCase(" ") && !roomNo.equalsIgnoreCase("-1"))
				{
					query.append(" and ord.assign_bed_num = '" + roomNo + "'");
				}
				if (feedStatus != null && !feedStatus.equalsIgnoreCase("All") && !feedStatus.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("undefined"))
				{
					query.append(" and ord.status = '" + feedStatus + "'");
				} else
				{
					query.append(" and ord.status Not In ('Close','Close-P','HIS Cancel')");
				}

				if (encounterId != null && !encounterId.equalsIgnoreCase("-1") && !encounterId.equalsIgnoreCase(""))
				{
					query.append(" and ord.document_no = '" + encounterId + "'");
				}
				if (escLevel != null && !escLevel.equalsIgnoreCase("all"))
					query.append(" AND ord.level = '" + escLevel + "'   ");
				
				if(userRights != null && (userRights.contains("Pharma_loc"))){
					String userID= session.get("empIdofuser").toString().split("-")[1];
					String locList = fetchLocationCC(userID, connectionSpace);
					
					query.append(" AND ord.nursing_code In (" + locList + ")   ");
				}
				}
				query.append(" ORDER BY  ord.order_date, ord.order_time desc  ");
				List dataList = null;

				// System.out.println("STATUS : "+feedStatus);
				 //System.out.println("Q : "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
						Object[] object = (Object[]) iterator.next();

						tempMap.put("id", getValueWithNullCheck(object[0]));
						tempMap.put("uhid", getValueWithNullCheck(object[1]));
						tempMap.put("patient_name", getValueWithNullCheck(object[2]));
						tempMap.put("assign_bed_num", getValueWithNullCheck(object[3]));
						tempMap.put("level", "Level" + getValueWithNullCheck(object[4]));
						tempMap.put("status", getValueWithNullCheck(object[5]));
						tempMap.put("priority", getValueWithNullCheck(object[6]));
						tempMap.put("nursing_unit", getValueWithNullCheck(object[7]));
						if (object[12] != null && object[13] != null && (!object[5].toString().equalsIgnoreCase("Close") && !object[5].toString().equalsIgnoreCase("Close-P")))
						{
							tempMap.put("timer", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[12].toString(), object[13].toString()));
						} else
						{
							tempMap.put("timer", "00:00");
						}
						tempMap.put("admitting_pra_name", getValueWithNullCheck(object[8]));
						tempMap.put("attending_pra_name", getValueWithNullCheck(object[9]));
						tempMap.put("speciality", getValueWithNullCheck(object[10]));
						tempMap.put("encounter_id", getValueWithNullCheck(object[11]));
						tempMap.put("document_no", getValueWithNullCheck(object[16]));
						if (object[14] != null && object[15] != null)
							tempMap.put("order", DateUtil.convertDateToIndianFormat(object[14].toString()) + "    " + getValueWithNullCheck(object[15]));
						else
							tempMap.put("order", "NA");
						tempList.add(tempMap);

					}

				}

				setMasterViewProp(tempList);
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				returnResult = SUCCESS;

			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	private String fetchLocationCC(String userID, SessionFactory connectionSpace)
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String str = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select emp_id, location from compliance_contacts where emp_id='"+userID+"' and forDept_id=(Select id from subdepartment where subdeptname='Pharmacy Sub') and moduleName='Pharmacy'".toString(), connectionSpace);
			str = "";
			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				str = str + "'" + object[1].toString() + "' , ";
			}
			str = str + "' ' ";
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return str;
	
	}

	public String beforeTakeActionOnOrder()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				fetchOrderDetails();
				returnResult = SUCCESS;
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

	public String beforeTakeActionInBulk()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				returnResult = SUCCESS;
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

	public String viewPharmaHistoryDetail()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				fetchOrderHistoryDetails();
				returnResult = SUCCESS;
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

	public String viewMedicineDetailItem()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				fetchMedicineDetails();
				returnResult = SUCCESS;
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

	public void fetchOrderDetails()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = null;
			String orderName = "";
			medicineList = new ArrayList();

			List medicineListBefore = null;
			dataMap = new LinkedHashMap<String, String>();
			dataMap1 = new LinkedHashMap<String, String>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT ord.uhid,ord.patient_name,ord.assign_bed_num,ord.nursing_unit,ord.admission_date,ord.admission_time,ord.status,ord.action_date,ord.action_time,ord.action_by,ord.encounter_id,ord.order_date,ord.order_time,ord.document_no,ord.nursing_code from pharma_patient_detail as ord  ");
			query.append(" WHERE ord.id=" + complianId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					dataMap.put("UHID", CUA.getValueWithNullCheck(object[0]));
					dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[1]));
					dataMap.put("Bed No", CUA.getValueWithNullCheck(object[2]));
					dataMap.put("Nursing Unit", CUA.getValueWithNullCheck(object[3]));
					if (object[4] != null && object[5] != null)
						dataMap.put("Admission At", CUA.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[4].toString())) + "  " + CUA.getValueWithNullCheck(object[5]));
					else
						dataMap.put("Admission At", "NA");

					dataMap.put("Status", CUA.getValueWithNullCheck(object[6]));

					if (object[7] != null && object[8] != null)
						dataMap.put("Action At", DateUtil.convertDateToIndianFormat(object[7].toString()) + "  " + CUA.getValueWithNullCheck(object[8]));
					else
						dataMap.put("Action At", "NA");

					if (object[9] != null && !object[9].toString().equalsIgnoreCase("0"))
						dataMap.put("Action By", empNameGet(object[9].toString(), connectionSpace).split("#")[0]);
					else
						dataMap.put("Action By", "Auto");

					if (object[10] != null)
					{
						dataMap.put("Ticket No", CUA.getValueWithNullCheck(object[10]));
						encounterId = object[10].toString();
					}

					if (object[11] != null && object[12] != null)
						dataMap.put("Order At", CUA.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[11].toString())) + "  " + CUA.getValueWithNullCheck(object[12]));
					else
						dataMap.put("Order At", "NA");

					dataMap.put("Document No", CUA.getValueWithNullCheck(object[13]));
					setNursingCode(CUA.getValueWithNullCheck(object[14]));

				}
			}
			StringBuilder query1 = new StringBuilder();
			query1.append("SELECT med.ORDER_ID,med.ITEM_NAME,med.status,med.ORDER_QTY,med.DISP_QTY from pharma_medicine_data as med  ");
			query1.append(" WHERE med.TRN_GROUP_REF='" + encounterId + "'");
			dataList = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					medicineListBefore = new ArrayList();
					if (object[0] != null)
						medicineListBefore.add(object[0].toString());
					if (object[1] != null)
						medicineListBefore.add(object[1].toString());
					if (object[2] != null)
						medicineListBefore.add(object[2].toString());
					if (object[3] != null)
						medicineListBefore.add(object[3].toString());
					if (object[4] != null)
						medicineListBefore.add(object[4].toString());
					medicineList.add(medicineListBefore);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void fetchOrderHistoryDetails()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = null;
			String orderName = "";
			medicineList = new ArrayList();

			List medicineListBefore = null;
			dataMap = new LinkedHashMap<String, String>();
			dataMap1 = new LinkedHashMap<String, String>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT ord.uhid,ord.patient_name,ord.assign_bed_num,ord.nursing_unit,ord.admission_date,ord.admission_time,ord.status,ord.action_date,ord.action_time,ord.action_by,ord.encounter_id,ord.order_date,ord.order_time,ord.document_no from pharma_patient_detail as ord  ");
			query.append(" WHERE ord.id=" + complianId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					dataMap.put("UHID", CUA.getValueWithNullCheck(object[0]));
					dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[1]));
					dataMap.put("Bed No", CUA.getValueWithNullCheck(object[2]));
					dataMap.put("Nursing Unit", CUA.getValueWithNullCheck(object[3]));

					if (object[4] != null && object[5] != null)
						dataMap.put("Admission At", CUA.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[4].toString())) + "  " + CUA.getValueWithNullCheck(object[5]));
					else
						dataMap.put("Admission At", "NA");

					dataMap.put("Status", CUA.getValueWithNullCheck(object[6]));

					if (object[7] != null && object[8] != null)
						dataMap.put("Action At", DateUtil.convertDateToIndianFormat(object[7].toString()) + "  " + CUA.getValueWithNullCheck(object[8]));
					else
						dataMap.put("Action At", "NA");

					if (object[9] != null && !object[9].toString().equalsIgnoreCase("0"))
						dataMap.put("Action By", empNameGet(object[9].toString(), connectionSpace).split("#")[0]);
					else
						dataMap.put("Action By","Auto");

					if (object[10] != null)
					{
						dataMap.put("Ticket No", CUA.getValueWithNullCheck(object[10]));
						encounterId = object[10].toString();
					}

					if (object[11] != null && object[12] != null)
						dataMap.put("Order At", DateUtil.convertDateToIndianFormat(object[11].toString()) + "  " + object[12].toString());
					else
						dataMap.put("Order At", "NA");

					dataMap.put("Document No", CUA.getValueWithNullCheck(object[13]));

				}
			}

			ordHisList = new ArrayList<ArrayList<String>>();
			StringBuilder query1 = new StringBuilder();
			query1.append("select oh.status,oh.action_date,oh.action_time,oh.action_by, oh.close_by_id,oh.close_by_name,oh.comment " + " from pharma_patient_data_history as oh  where oh.encounter_id = '" + encounterId + "' ");
			dataList = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ArrayList<String> tempList1 = null;
			if (dataList != null && dataList.size() > 0)
			{

				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					tempList1 = new ArrayList<String>();

					tempList1.add(CUA.getValueWithNullCheck(object[0]));

					if (object[1] != null)
						tempList1.add(DateUtil.convertDateToIndianFormat(object[1].toString()));
					else
						tempList1.add("NA");

					if (object[2] != null)
						tempList1.add(object[2].toString());
					else
						tempList1.add("NA");
					if (object[3] != null && !object[3].toString().equalsIgnoreCase("Auto"))
						tempList1.add(object[3].toString());
					else if (object[3] != null && object[3].toString().equalsIgnoreCase("Auto"))
						tempList1.add("Auto");
					tempList1.add(CUA.getValueWithNullCheck(object[4]));
					tempList1.add(CUA.getValueWithNullCheck(object[5]));
					tempList1.add(CUA.getValueWithNullCheck(object[6]));
					ordHisList.add(tempList1);
				}

			}
			fetchMedicineDetails();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public String empNameGet(String id, SessionFactory connection)
	{

		String empName = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List dataListEcho = cbt.executeAllSelectQuery("SELECT emp.empName, emp.mobOne, dept.deptName FROM employee_basic AS emp INNER JOIN  department AS dept ON emp.deptname= dept.id where emp.id =" + id + "".toString(), connection);
			if (dataListEcho != null && dataListEcho.size() > 0)
			{
				for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empName = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString();
				}
			}

		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}

		return empName;

	}

	public void fetchMedicineDetails()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			List dataList = null;
			MedicinePojo DP = null;
			data_list = new ArrayList<MedicinePojo>();
			Object[] object = null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT med.ORDER_ID,med.ITEM_NAME,med.ORDER_DATE,med.ORDER_TIME,med.DISPENSED_DATE,med.DISPENSED_TIME,med.STATUS,med.ORDER_QTY,med.DISP_QTY,med.REM_QTY,med.REC_QTY from pharma_medicine_data as med  ");
			query.append(" WHERE med.TRN_GROUP_REF='" + encounterId + "'");
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					DP = new MedicinePojo();
					DP.setId(CUA.getValueWithNullCheck(object[0]));
					DP.setName(CUA.getValueWithNullCheck(object[1]));
					if (object[2] != null && !object[2].toString().equalsIgnoreCase("") && !object[2].toString().equalsIgnoreCase("NA") && object[3] != null)
						DP.setOrderAt(DateUtil.convertDateToIndianFormat(object[2].toString()) + "     " + object[3].toString());
					else
						DP.setOrderAt("NA");

					if (object[4] != null && !object[4].toString().equalsIgnoreCase("") && !object[4].toString().equalsIgnoreCase("NA") && object[5] != null)
						DP.setDispatchAt(DateUtil.convertDateToIndianFormat(object[4].toString()) + "     " + object[5].toString());
					else
						DP.setDispatchAt("NA");

					DP.setStatus(CUA.getValueWithNullCheck(object[6]));
					DP.setQuantity(CUA.getValueWithNullCheck(object[7]));
					DP.setDispatchQuantity(CUA.getValueWithNullCheck(object[8]));
					DP.setRemainingQuantity(CUA.getValueWithNullCheck(object[9]));
					DP.setReceivedQuantity(CUA.getValueWithNullCheck(object[10]));
					data_list.add(DP);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public String actionOnPharmacyOrderLodge()
	{
		String returnResult = ERROR;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append(" update pharma_patient_detail set action_by='" + session.get("empIdofuser").toString().split("-")[1] + "', ");
			sb.append(" action_date='" + DateUtil.getCurrentDateUSFormat() + "',");
			sb.append(" action_time='" + DateUtil.getCurrentTimeHourMin() + "',");
			if (request.getParameter("actionStatus").equalsIgnoreCase("Parked"))
			{
				sb.append(" escalation_date='" + DateUtil.getCurrentDateUSFormat() + "',");
				sb.append(" escalation_time='" + request.getParameter("snoozeTime") + "',");
				sb.append(" snooze_time='" + request.getParameter("snoozeTime") + "',");
			}
			{
				sb.append(" snooze_time='NA',");
			}
			sb.append(" status='" + request.getParameter("actionStatus") + "' ");
			sb.append(" where id=" + complianId + "");
			int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);
			if (chkUpdate > 0)
			{
				Map<String, Object> wherClause = new HashMap<String, Object>();
				wherClause.put("encounter_id", request.getParameter("encounterId"));
				wherClause.put("action_by", (String) session.get("uName"));
				wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
				wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
				if (!request.getParameter("actionStatus").equalsIgnoreCase("Parked"))
				{
					wherClause.put("close_by_id", request.getParameter("closeById"));
					wherClause.put("close_by_name", request.getParameter("closeByName"));
				} else
				{
					wherClause.put("close_by_id", "NA");
					wherClause.put("close_by_name", "NA");
				}
				wherClause.put("comment", request.getParameter("comment"));
				wherClause.put("status", request.getParameter("actionStatus"));

				if (wherClause != null && wherClause.size() > 0)
				{
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					for (Map.Entry<String, Object> entry : wherClause.entrySet())
					{
						ob = new InsertDataTable();
						ob.setColName(entry.getKey());
						ob.setDataName(entry.getValue().toString());
						insertData.add(ob);
					}
					cbt.insertIntoTable("pharma_patient_data_history", insertData, connectionSpace);
					insertData.clear();
				}

				if (request.getParameter("actionStatus").equalsIgnoreCase("Close-P"))
				{
					String qry = "update pharma_medicine_data set STATUS='Not Dispatch',nursing_code='"+request.getParameter("nursingCode")+"', close_by_id='"+request.getParameter("closeById")+"', close_by_name='"+request.getParameter("closeByName")+"', action_by='"+(String) session.get("uName")+"', close_date='"+DateUtil.getCurrentDateIndianFormat()+"', close_time='"+DateUtil.getCurrentTimeHourMin()+"' where ENCOUNTER_ID='" + request.getParameter("encounterId") + "' and STATUS='Pending'";
					new createTable().executeAllUpdateQuery(qry, connectionSpace);
				}
				if (medicine_order_id != null && !medicine_order_id.equalsIgnoreCase(""))
				{
					String qry = null;
					String medicineId[] = medicine_order_id.split(",");
					String receivedQty[] = recId.split(",");
					String dispatchQty[] = disId.split(",");

					// System.out.println(medicine_order_id);
					// System.out.println(recId);
					// System.out.println(disId);

					for (int i = 0; i < medicineId.length; i++)
					{
						if (!dispatchQty[i].trim().equalsIgnoreCase("NA") && !receivedQty[i].trim().equalsIgnoreCase("NA") && !receivedQty[i].trim().equalsIgnoreCase(""))
						{
							qry = "update pharma_medicine_data set REC_QTY='" + receivedQty[i].trim() + "',nursing_code='"+request.getParameter("nursingCode")+"',close_by_id='"+request.getParameter("closeById")+"', close_by_name='"+request.getParameter("closeByName")+"', action_by='"+(String) session.get("uName")+"', close_date='"+DateUtil.getCurrentDateIndianFormat()+"', close_time='"+DateUtil.getCurrentTimeHourMin()+"', Final_status ='Close' where ORDER_ID='" + medicineId[i].trim() + "'";
							new createTable().executeAllUpdateQuery(qry, connectionSpace);
						}

					}

				}

			}
			addActionMessage("Order Close Successfully !!!");
			returnResult = SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;

	}

	@SuppressWarnings("unchecked")
	public String actionOnPharmacyBulkClose()
	{
		String returnResult = ERROR;
		String encounterId = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			for (int j = 0; j < request.getParameter("complianId").split(",").length; j++)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(" update pharma_patient_detail set action_by='" + session.get("empIdofuser").toString().split("-")[1] + "', ");
				sb.append(" action_date='" + DateUtil.getCurrentDateUSFormat() + "',");
				sb.append(" action_time='" + DateUtil.getCurrentTimeHourMin() + "',");
				sb.append(" snooze_time='NA',");
				sb.append(" status='" + request.getParameter("actionStatus") + "' ");
				sb.append(" where id=" + request.getParameter("complianId").split(",")[j] + "");
				int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);
				List dataList = cbt.executeAllSelectQuery("select id,encounter_id from pharma_patient_detail where id='" + request.getParameter("complianId").split(",")[j] + "'", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						encounterId = object[1].toString();

					}
				}
				if (chkUpdate > 0)
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					wherClause.put("encounter_id", encounterId);
					wherClause.put("action_by", (String) session.get("uName"));
					wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
					wherClause.put("close_by_id", request.getParameter("closeById"));
					wherClause.put("close_by_name", request.getParameter("closeByName"));
					wherClause.put("comment", request.getParameter("comment"));
					wherClause.put("status", request.getParameter("actionStatus"));
					if (wherClause != null && wherClause.size() > 0)
					{
						InsertDataTable ob = new InsertDataTable();
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						for (Map.Entry<String, Object> entry : wherClause.entrySet())
						{
							ob = new InsertDataTable();
							ob.setColName(entry.getKey());
							ob.setDataName(entry.getValue().toString());
							insertData.add(ob);
						}
						cbt.insertIntoTable("pharma_patient_data_history", insertData, connectionSpace);
						insertData.clear();
					}
					if (request.getParameter("actionStatus").equalsIgnoreCase("Close-P"))
					{
						String qry = "update pharma_medicine_data set STATUS='Not Dispatch',nursing_code='"+request.getParameter("nursingCode")+"',close_by_id='"+request.getParameter("closeById")+"', close_by_name='"+request.getParameter("closeByName")+"', action_by='"+(String) session.get("uName")+"', close_date='"+DateUtil.getCurrentDateIndianFormat()+"', close_time='"+DateUtil.getCurrentTimeHourMin()+"' where ENCOUNTER_ID='" + encounterId + "' and STATUS='Pending'";
						new createTable().executeAllUpdateQuery(qry, connectionSpace);
					}
					List dataList1 = cbt.executeAllSelectQuery("select DISP_QTY,ORDER_ID from pharma_medicine_data where ENCOUNTER_ID='" + encounterId + "'", connectionSpace);
					if (dataList1 != null && dataList1.size() > 0)
					{
						for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								new createTable().executeAllUpdateQuery("update pharma_medicine_data set REC_QTY='" + object[0].toString() + "',nursing_code='"+request.getParameter("nursingCode")+"',close_by_id='"+request.getParameter("closeById")+"', close_by_name='"+request.getParameter("closeByName")+"', action_by='"+(String) session.get("uName")+"', close_date='"+DateUtil.getCurrentDateIndianFormat()+"', close_time='"+DateUtil.getCurrentTimeHourMin()+"', Final_status ='Close' where ORDER_ID='" + object[1].toString() + "'", connectionSpace);
							}
						}
					}
				}
			}
			addActionMessage("Order Close Successfully !!!");
			returnResult = SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public String fetchEmpNameById()
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String tst = "NA";
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			{
				int hisUHIDLength = 8;
				int uhidLength = uhid.length();
				int templength = 0;
				if (uhidLength < hisUHIDLength)
				{

					templength = hisUHIDLength - uhidLength;
					for (int j = 0; j < templength; j++)
					{
						uhid = "0" + uhid;
					}
				}
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.34:1521:KRP", "spectra", "spectra");
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM EMPLOYEEINFORMATION_VW WHERE PERSONNUM = '" + uhid + "' ");
				commonJSONArray = new JSONArray();
				JSONObject listObject = new JSONObject();
				while (rs.next())
				{
					listObject.put("fName", rs.getString("FIRSTNAME"));
					listObject.put("lName", rs.getString("LASTNAME"));
				}
				commonJSONArray.add(listObject);
			}
			returnResult = "success";
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String beforeAddEmpForPharmacyAlert()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				serviceDeptMap = new LinkedHashMap<String, String>();
				query.append(" select distinct dept.id,dept.deptName from department as dept");
				query.append(" where orgnztnlevel='1' and mappedOrgnztnId='1' and dept.flag='Active' ORDER BY dept.deptName ASC");
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

	public String fetchSubdepartmentList()
	{

		commonJSONArray = new JSONArray();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		JSONArray subdeptMap = new JSONArray();
		List dataList = null;
		try
		{
			qryString.append("select id,subdeptname from subdepartment where status='Active' && deptid=" + deptId + " order by subdeptname asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
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
						commonJSONArray.add(innerobj);
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return SUCCESS;
	}

	public String fetchEmpList()
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		dataListMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT emp.id,emp.empName FROM employee_basic AS emp");
			qryString.append(" Inner join department as dept on dept.id=emp.deptname ");
			qryString.append(" Inner join subdepartment as subdept on subdept.deptid=dept.id ");
			qryString.append("  where emp.flag='0' && dept.id=" + deptId + " and subdept.id=" + subDeptId + "  order by emp.empName asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{

				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{

						dataListMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String fetchPharmacyAlertData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT esc.id,dept.deptName,subdept.subdeptname,emp.empName ");
				query.append(" FROM pharma_dispatch_alert AS esc ");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = esc.emp_name ");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = esc.sub_dept ");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid ");
				query.append("  where esc.status='Active' ");
				// System.out.println("####### "+query.toString());
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", getValueWithNullCheck(object[0]));
						one.put("dept", getValueWithNullCheck(object[1]));
						one.put("sub_dept", getValueWithNullCheck(object[2]));
						one.put("emp_name", getValueWithNullCheck(object[3]));
						Listhb.add(one);
					}
				}
				setMasterViewProp(Listhb);
				if (masterViewProp != null && masterViewProp.size() > 0)
				{
					setRecords(masterViewProp.size());
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

	@SuppressWarnings("rawtypes")
	public String fetchHighCostPharmacyAlertData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT phcm.id,phcm.medicine_code,phcm.brief,phcm.contact_email_id ");
				query.append(" FROM pharma_high_cost_medicine_alert AS phcm ");
				query.append("  where phcm.status='Active' ");
				// System.out.println("####### "+query.toString());
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", getValueWithNullCheck(object[0]));
						one.put("medicine_code", getValueWithNullCheck(object[1]));
						one.put("brief", getValueWithNullCheck(object[2]));
						one.put("contact_email_id", getValueWithNullCheck(object[3]));
						Listhb.add(one);
					}
				}
				setMasterViewProp(Listhb);
				if (masterViewProp != null && masterViewProp.size() > 0)
				{
					setRecords(masterViewProp.size());
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

	@SuppressWarnings("rawtypes")
	public String beforeAddEmpForHighCostPharmacyAlert()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addEmpForPharmacyAlert()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String empId[] = request.getParameterValues("employee");
				for (int i = 0; i < empId.length; i++)
				{
					ob = new InsertDataTable();
					ob.setColName("dept");
					ob.setDataName(request.getParameter("escDept"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("sub_dept");
					ob.setDataName(request.getParameter("escsubDept"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("emp_name");
					ob.setDataName(empId[i]);
					insertData.add(ob);

					cbt.insertDataReturnId("pharma_dispatch_alert", insertData, connectionSpace);
					insertData.clear();
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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addEmpForHighCostPharmacyAlert()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				ob = new InsertDataTable();
				ob.setColName("medicine_code");
				ob.setDataName(request.getParameter("medicine_code"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("brief");
				ob.setDataName(request.getParameter("brief"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("user_name");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Active");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("contact_email_id");
				ob.setDataName(request.getParameter("contact_email_id"));
				insertData.add(ob);

				cbt.insertDataReturnId("pharma_high_cost_medicine_alert", insertData, connectionSpace);
				insertData.clear();

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
	public String editPharmacyAlertData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				if (getOper().equalsIgnoreCase("del"))
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
					cbt.deleteAllRecordForId("pharma_dispatch_alert", "id", condtIds.toString(), connectionSpace);
					returnResult = SUCCESS;
				}
			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String editHighCostPharmacyAlertData()
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
					@SuppressWarnings("unchecked")
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("pharma_high_cost_medicine_alert", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update pharma_high_cost_medicine_alert set status ='Inactive' where id='" + getId() + "'");
					cbt.updateTableColomn(connectionSpace, sb);
				}

			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String fetchCurrentPharmacy()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();
				columnMap.put("PATIENT_ID", "PATIENT_ID");
				columnMap.put("nursing_unit", "Nursing Unit");
				columnMap.put("encounter_id", "ENCOUNTER ID");
				columnMap.put("ORDER_ID", "Order ID");
				//columnMap.put("DISPENSED_DATE_TIME", "DISPENSED DATE TIME");
				columnMap.put("ORDER_QTY", "ORDER QTY");
				columnMap.put("ITEM_NAME", "ITEM NAME");
				columnMap.put("ORD_DATE_TIME", "ORD DATE TIME");
				columnMap.put("DISPENSED_DATE", "DISPENSED DATE");
				columnMap.put("DISPENSED_TIME", "DISPENSED TIME");
				columnMap.put("STATUS", "STATUS");
				
				columnMap.put("Final_status", "Final Status");
				columnMap.put("ORDER_DATE", "ORDER DATE");
				columnMap.put("ORDER_TIME", "ORDER TIME");
				columnMap.put("DISP_NO", "DISP NO");
				columnMap.put("PRIORITY", "PRIORITY");
				//columnMap.put("TRN_GROUP_REF", "TRN GROUP REF");
				columnMap.put("DISP_QTY", "DISP QTY");
				columnMap.put("DOC_NO", "DOC NO");
				columnMap.put("REM_QTY", "REM QTY");
				//columnMap.put("sms_flag", "TAT");
				columnMap.put("REC_QTY", "REC QTY");
				columnMap.put("action_by", "Action By");
				columnMap.put("close_by_id", "Close ID");
				columnMap.put("close_date", "Close Date");
				columnMap.put("close_time", "Close Time");
				columnMap.put("close_by_name", "Close Name");
				columnMap.put("DISP_QTY as dispatch_tot", "Dispatch Total Time");
				columnMap.put("REC_QTY as resolution_time", "Total resolution Time");
				columnMap.put("sms_flag as test", "TAT");
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
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Pharma Medicine Data Detail";

		if (sessionFlag)
		{
			try
			{

				List keyList = new ArrayList();
				List titleList = new ArrayList();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				StringBuilder query = new StringBuilder();
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					tempMap.put("id", "ID");
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
									if (obdata.toString().equalsIgnoreCase("cd.dept"))
									{
										query.append(" dep.deptName, ");
									} else
									{

										query.append(obdata.toString() + ",");
									}
								} else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" FROM pharma_medicine_data ");
						query.append(" where id >'0' ");
						if (maxDateValue != null && minDateValue != null && !maxDateValue.equalsIgnoreCase("") && !minDateValue.equalsIgnoreCase(""))
						{

							String str[] = getMinDateValue().split("-");

							if (str[0] != null && str[0].length() > 3)
							{
								query.append(" and ORDER_DATE BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
							} else
							{
								query.append(" and ORDER_DATE BETWEEN '" + (DateUtil.convertDateToUSFormat(minDateValue)) + "' AND '" + (DateUtil.convertDateToUSFormat(maxDateValue)) + "'");
							}

						}
						if (feedStatus != null && !feedStatus.equalsIgnoreCase("-1"))
						{
							query.append(" and status='" + getFeedStatus() + "'");
						}
						// System.out.println("<<<<>>>>>>>>>>>>>> " + query);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)

						{
							// System.out.println("keyList "+keyList);
							excelFileName = new PharmacyExcelDownload().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);
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

	public String getMaxDateValue()
	{
		return maxDateValue;
	}

	public void setMaxDateValue(String maxDateValue)
	{
		this.maxDateValue = maxDateValue;
	}

	public String getMinDateValue()
	{
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
		this.minDateValue = minDateValue;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public String getEscLevel()
	{
		return escLevel;
	}

	public void setEscLevel(String escLevel)
	{
		this.escLevel = escLevel;
	}

	public String getEscTAT()
	{
		return escTAT;
	}

	public void setEscTAT(String escTAT)
	{
		this.escTAT = escTAT;
	}

	public String getComplianId()
	{
		return complianId;
	}

	public void setComplianId(String complianId)
	{
		this.complianId = complianId;
	}

	public Map<String, String> getDataCountMap()
	{
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
		this.dataCountMap = dataCountMap;
	}

	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getNursingUnit()
	{
		return nursingUnit;
	}

	public void setNursingUnit(String nursingUnit)
	{
		this.nursingUnit = nursingUnit;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<MedicinePojo> getData_list()
	{
		return data_list;
	}

	public void setData_list(List<MedicinePojo> dataList)
	{
		data_list = dataList;
	}

	public Map<String, String> getDataMap1()
	{
		return dataMap1;
	}

	public void setDataMap1(Map<String, String> dataMap1)
	{
		this.dataMap1 = dataMap1;
	}

	public List getMedicineList()
	{
		return medicineList;
	}

	public void setMedicineList(List medicineList)
	{
		this.medicineList = medicineList;
	}

	public String getUhid()
	{
		return uhid;
	}

	public void setUhid(String uhid)
	{
		this.uhid = uhid;
	}

	public String getPatientName()
	{
		return patientName;
	}

	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}

	public String getRoomNo()
	{
		return roomNo;
	}

	public String getEncounterId()
	{
		return encounterId;
	}

	public void setEncounterId(String encounterId)
	{
		this.encounterId = encounterId;
	}

	public void setRoomNo(String roomNo)
	{
		this.roomNo = roomNo;
	}

	public ArrayList<ArrayList<String>> getOrdHisList()
	{
		return ordHisList;
	}

	public void setOrdHisList(ArrayList<ArrayList<String>> ordHisList)
	{
		this.ordHisList = ordHisList;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public String getSubDeptId()
	{
		return subDeptId;
	}

	public void setSubDeptId(String subDeptId)
	{
		this.subDeptId = subDeptId;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public Map<String, String> getServiceDeptMap()
	{
		return serviceDeptMap;
	}

	public void setServiceDeptMap(Map<String, String> serviceDeptMap)
	{
		this.serviceDeptMap = serviceDeptMap;
	}

	public Map<String, String> getDataListMap()
	{
		return dataListMap;
	}

	public void setDataListMap(Map<String, String> dataListMap)
	{
		this.dataListMap = dataListMap;
	}

	public String getMedicine_order_id()
	{
		return medicine_order_id;
	}

	public void setMedicine_order_id(String medicineOrderId)
	{
		medicine_order_id = medicineOrderId;
	}

	public String getOrdId()
	{
		return ordId;
	}

	public void setOrdId(String ordId)
	{
		this.ordId = ordId;
	}

	public String getRecId()
	{
		return recId;
	}

	public void setRecId(String recId)
	{
		this.recId = recId;
	}

	public String getDisId()
	{
		return disId;
	}

	public void setDisId(String disId)
	{
		this.disId = disId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
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

	public String getDataWild() {
		return dataWild;
	}

	public void setDataWild(String dataWild) {
		this.dataWild = dataWild;
	}

	public String getNursingCode()
	{
		return nursingCode;
	}

	public void setNursingCode(String nursingCode)
	{
		this.nursingCode = nursingCode;
	}

}