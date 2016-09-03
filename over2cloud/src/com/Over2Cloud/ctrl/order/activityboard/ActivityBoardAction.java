package com.Over2Cloud.ctrl.order.activityboard;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.helpdesk.common.CommonExcelDownload;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.ActionOnFeedback;

@SuppressWarnings("serial")
public class ActivityBoardAction extends GridPropertyBean
{
	private String taskType;
	private String fromDepart;
	private String toDepart;
	private String feedStatus;
	private String escLevel;
	private String escTAT;
	private String fromTime;
	private String toTime;
	private String tableAlis;
	private String divName;
	private Map<String, String> columnMap;
	private String[] columnNames;
	private String excelFileName;
	private String selectedId;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	private List<Object> masterViewProp = new ArrayList<Object>();
	private Map<String, String> dataCountMap;
	private String complianId;
	private String formaterOn;
	private String mainTable;
	private Map<String, String> dataMap;
	private Map<String, String> editableDataMap;
	private String maxDateValue;
	private String minDateValue;
	private String[] resolutionDetail;
	private Map<String, Map<String, String>> finalStatusMap;
	private JSONArray jsonArray;
	private String sel_id;
	private String dept;
	private String category;
	private Map<Integer, String> deptList;
	private List<FeedTicketCyclePojo> list;
	private String cartId,orderId,orderUId,orderName;
	private String dataWild;
	private String heart;
	private String xray;
	private String ultra;
	private String priority;
	private Map<String, String> nursingUnitList;
	private Map<String, String> nursingUnitListForPharmacy;
	private Map<String, String> uhidList;
	private Map<String, String> patientNameList;
	private Map<String, String> encounterIdList;
	private Map<String, String> roomNoList;
	private String nursingUnit;
	
	
	

	//for machine cart
	//cart order Add
	public String saveMachineCart(){
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("cartId");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("orderId");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("orderName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("orderUId");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				

				ob1 = new TableColumes();
				ob1.setColumnname("allotFalg");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("datetime");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				
				cbt.createTable22("machine_order_cart", tableColume, connectionSpace);

				ob = new InsertDataTable();
				ob.setColName("cartId");
				ob.setDataName(cartId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderId");
				ob.setDataName(orderId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderName");
				ob.setDataName(orderName);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("orderUId");
				ob.setDataName(orderUId);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("allotFalg");
				ob.setDataName("0");
				insertData.add(ob);


				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("datetime");
				ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				boolean ckhAdd = cbt.insertIntoTable("machine_order_cart", insertData, connectionSpace);
				//System.out.println("ckhAdd "+ckhAdd);
				StringBuilder qry = new StringBuilder();
				qry.append("select to_time from machine_order_cart_settings where id='"+cartId+"'");
				List data=cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				String new_date=null,new_time=null;
				if(data!=null && !data.isEmpty())
				{
					Object object= data.get(0);
					if(object!=null)
					{
						 String  esctm =null;
						 String currDate=DateUtil.getCurrentDateUSFormat();
						  String timediff=DateUtil.time_difference(currDate, DateUtil.getCurrentTime(), currDate, object.toString());
						if(escTAT!=null && !escTAT.equalsIgnoreCase(""))
						{
							 
							esctm = DateUtil.newdate_AfterEscTime(DateUtil.convertDateToUSFormat(escTAT.split(" ")[0]), escTAT.split(" ")[1],timediff, "Y" );
						}else
						{
							  esctm = DateUtil.newdate_AfterEscTime(currDate, DateUtil.getCurrentTime(),timediff, "Y" );
						}
						if(esctm!=null)
						{
							new_date=esctm.split("#")[0];
							 new_time=esctm.split("#")[1];
						}
						
						 
					}
				}
				
				if(ckhAdd)
				{
					StringBuilder sb = new StringBuilder();
					sb.append("update machine_order_data set status ='Unassigned Cart-"+cartId+"',cart_name='Un-assigned Cart-"+cartId+"',escalation_date='"+new_date+"',escalation_time='"+new_time+"' where id=" + orderId + "");
					cbt.updateTableColomn(connectionSpace, sb);
				}
				
				addActionMessage("Order Added sucessfully.");
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
	public String viewMachineCart()
	{
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession())
		{
			try
			{
				jsonArray = new JSONArray();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				query.append(" select orderId,orderName,orderUId from machine_order_cart where allotFalg='0' and cartId='"+cartId+"'");
				if(searchString!=null && !searchString.equalsIgnoreCase(""))
				{
					query.append(" and orderUId like'%"+searchString+"%'");
				}
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null  && object[2] != null )
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("orderId", object[0]);
							innerobj.put("orderName", object[1].toString());
							innerobj.put("orderUId", object[2].toString());

							jsonArray.add(innerobj);
						}
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
	
	@SuppressWarnings("rawtypes")
	public String removeCartItem()
	{
		// boolean sessionFlag = ValidateSession.checkSession();
		if (ValidateSession.checkSession())
		{
			try
			{
				jsonArray = new JSONArray();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				query.append(" delete from machine_order_cart where cartId='"+cartId+"' and orderId='"+orderId+"'");
				//cbt.executeAllSelectQuery(query, connectionSpace)
				int chkUpdate= cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
				
				if(chkUpdate>0){
					
					StringBuilder sb = new StringBuilder();
					
					String esctm  = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(),"00:15", "Y" );
					sb.append("update machine_order_data set status ='Un-assigned',cart_name=NULL,escalation_date='"+esctm.split("#")[0]+"',escalation_time='"+esctm.split("#")[1]+"' where id=" + orderId + "");
					cbt.updateTableColomn(connectionSpace, sb);
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
	
	@SuppressWarnings("rawtypes")
	public String viewActivityBoardHeader()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();

				departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, "HDM", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
					departmentlist.clear();
				}

				maxDateValue = DateUtil.getCurrentDateUSFormat();
				minDateValue = DateUtil.getCurrentDateUSFormat();
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
	public String viewActivityBoardHeaderCPS()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				maxDateValue = DateUtil.getCurrentDateUSFormat();
				minDateValue = DateUtil.getCurrentDateUSFormat();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				nursingUnitList = new LinkedHashMap<String, String>();
				nursingUnitListForPharmacy = new LinkedHashMap<String, String>();
				uhidList = new LinkedHashMap<String, String>();
				roomNoList = new LinkedHashMap<String, String>();
				patientNameList = new LinkedHashMap<String, String>();
				encounterIdList = new LinkedHashMap<String, String>();
				nursingUnitList.put("-1", "Select Nursing Unit");
				List data = cbt.executeAllSelectQuery("select nu.nursing_unit,ord.id, nu.nursing_code from machine_order_data as ord LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit where ord.openDate between '"+maxDateValue+"' and '"+minDateValue+"' group by ord.nursingUnit ORDER BY ord.nursingUnit" , connectionSpace);
				if (data != null && data.size() > 0)
				{
					
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							nursingUnitList.put(object[2].toString(), object[0].toString());
						}
					}
						data.clear();
				}
		 
				roomNoList.put("-1", "Select Room No");
				uhidList.put("-1", "Select UHID");
				patientNameList.put("-1", "Select Patient Name");
				nursingUnitListForPharmacy.put("-1", "Select Nursing Unit");
				encounterIdList.put("-1", "Select Ticket No");
				data = cbt.executeAllSelectQuery("select ord.uhid as Id1,ord.uhid as Name1, ord.patient_name as Id2,ord.patient_name as Name2, ord.assign_bed_num as Id3,ord.assign_bed_num as Name3,ord.nursing_unit as Id4,ord.nursing_unit as Name4,ord.encounter_id as Id5,ord.document_no as Name5 from pharma_patient_detail as ord where ord.order_date between '"+maxDateValue+"' and '"+minDateValue+"' group by ord.uhid" , connectionSpace);
				System.out.println("hii");
				if (data != null && data.size() > 0)
				{
					
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							uhidList.put(object[0].toString(), object[1].toString());
							patientNameList.put(object[2].toString(), object[3].toString());
							roomNoList.put(object[4].toString(), object[5].toString());
							nursingUnitListForPharmacy.put(object[6].toString(), object[7].toString());
							encounterIdList.put(object[8].toString(), object[9].toString());
						}
					}
						data.clear();
				}
	data = cbt.executeAllSelectQuery("SELECT status FROM machine_order_data where  openDate between '"+maxDateValue+"' and '"+minDateValue+"' GROUP BY status ORDER BY status ", connectionSpace);
	dataMap=new LinkedHashMap<String, String>();
	dataMap.put("All", "All Status");
	if (data != null && data.size() > 0)
	{
		
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
		
	String object = (String) iterator.next();
	if (object != null)
	{
	dataMap.put(object, object);
	}
	}
	data.clear();
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
	public String beforePatientAdd()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				 
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

	
	public String viewActivityBoardColumn()
	{
		try
		{
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("action");
			gpv.setHeadingName("Action");
			gpv.setFormatter("takeAction");
			gpv.setHideOrShow("falsetakeAction");
			gpv.setWidth(35);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticketno");
			gpv.setHeadingName("Ticket No.");
			gpv.setFormatter("statusDetail");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("openat");
			gpv.setHeadingName("Open On");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("Timer");
			gpv.setHeadingName("Timer");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("TAT");
			gpv.setHeadingName("TAT Status");
			gpv.setFormatter("tatDetail");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			//gpv.setFormatter("tatDetail");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("location");
			gpv.setHeadingName("Location");
			gpv.setHideOrShow("false");
			gpv.setFormatter("viewLocation");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("subcatg");
			gpv.setHeadingName("Sub-Category");
			gpv.setHideOrShow("false");
			gpv.setWidth(95);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("brief");
			gpv.setHeadingName("Feedback Brief");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("true");
			gpv.setWidth(60);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("allotedto");
			gpv.setHeadingName("Alloted To");
			gpv.setFormatter("allotToDetail");
			gpv.setWidth(100);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("todept");
			gpv.setHeadingName("To Department");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("category");
			gpv.setHeadingName("Category");
			gpv.setHideOrShow("false");
			gpv.setWidth(95);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedby");
			gpv.setHeadingName("Feedback By");
			gpv.setFormatter("feedByDetail");
			gpv.setHideOrShow("true");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("bydept");
			gpv.setHeadingName("By Department");
			gpv.setHideOrShow("true");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedbacktype");
			gpv.setHeadingName("Feedback Type");
			gpv.setHideOrShow("true");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("lodgemode");
			gpv.setHeadingName("Lodge Mode");
			gpv.setFormatter("lodgeUserDetail");
			gpv.setHideOrShow("true");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("closemode");
			gpv.setHeadingName("Close Mode");
			gpv.setHideOrShow("true");
			gpv.setFormatter("editCloseMode");
			gpv.setWidth(65);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sms_flag");
			gpv.setHeadingName("SMS");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);

			gpv.setFormatter("resendSMS");
			viewPageColumns.add(gpv);

			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}



	public String viewActivityBoardColumnCPS()
	{
		try
		{
			//System.out.println("dataWild     "+dataWild+" mindate= "+minDateValue+"  maxDate= "+maxDateValue);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			 
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);
		 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("action");
			gpv.setHeadingName("Action");
			gpv.setFormatter("takeAction");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setFormatter("priorityVal");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
		 
  
			gpv = new GridDataPropertyView();
			gpv.setColomnName("timer");
			gpv.setHeadingName("Timer");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
		 	gpv.setHideOrShow("false");
			gpv.setWidth(70);
		 	viewPageColumns.add(gpv);
		 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderid");
			gpv.setHeadingName("Order Id");
			gpv.setFormatter("historyView");
		 	gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("order");
			gpv.setHeadingName("Order");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderType");
			gpv.setHeadingName("Order Type");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("uhid");
			gpv.setHeadingName("UHID");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("pName");
			gpv.setHeadingName("Patient Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(200);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("roomNo");
			gpv.setHeadingName("Room No");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("nursingUnit");
			gpv.setHeadingName("Nursing Unit");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
			
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderBy");
			gpv.setHeadingName("Order By");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderAt");
			gpv.setHeadingName("Order At");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("assignMchn");
			gpv.setHeadingName("Assigned Machine");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("department");
			gpv.setHeadingName("Department");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("assignTech");
			gpv.setHeadingName("Assigned Technician");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("openOn");
			gpv.setHeadingName("Open On");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);
			
			

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("true");
			gpv.setWidth(60);
			viewPageColumns.add(gpv);

		 
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	
	
	public String beforeActivityBoardDataForPharmacy()
	{
		try
		{
			//System.out.println("dataWild     "+dataWild+" mindate= "+minDateValue+"  maxDate= "+maxDateValue);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			 
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);
		 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("action");
			gpv.setHeadingName("Action");
			gpv.setFormatter("takeAction");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setFormatter("priorityVal");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
		 
  
			gpv = new GridDataPropertyView();
			gpv.setColomnName("timer");
			gpv.setHeadingName("Timer");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
		 	gpv.setHideOrShow("false");
			gpv.setWidth(70);
		 	viewPageColumns.add(gpv);
		 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderid");
			gpv.setHeadingName("Order Id");
			gpv.setFormatter("historyView");
		 	gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("order");
			gpv.setHeadingName("Order");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderType");
			gpv.setHeadingName("Order Type");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("uhid");
			gpv.setHeadingName("UHID");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("pName");
			gpv.setHeadingName("Patient Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(200);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("roomNo");
			gpv.setHeadingName("Room No");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("nursingUnit");
			gpv.setHeadingName("Nursing Unit");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
			
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderBy");
			gpv.setHeadingName("Order By");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderAt");
			gpv.setHeadingName("Order At");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);
			
	 
			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("true");
			gpv.setWidth(60);
			viewPageColumns.add(gpv);

		 
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	

	public String beforeViewActivityHistoryData()
	{
		try
		{
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticketno");
			gpv.setHeadingName("Ticket No.");
			gpv.setFormatter("statusDetail");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("subcatg");
			gpv.setHeadingName("Sub-Category");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("brief");
			gpv.setHeadingName("Feedback-Brief");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("openat");
			gpv.setHeadingName("Open At");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("closeat");
			gpv.setHeadingName("Close At");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalbreakdown");
			gpv.setHeadingName("Total Breakdown");
			gpv.setWidth(50);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			/*gpv = new GridDataPropertyView();
			gpv.setColomnName("totaltime");
			gpv.setHeadingName("Total Time");
			gpv.setWidth(50);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalworkingtime");
			gpv.setHeadingName("Total Working Time");
			gpv.setWidth(50);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);
*/
			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionby");
			gpv.setHeadingName("Action By");
			gpv.setWidth(120);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);
 
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("remarks");
			gpv.setHeadingName("Remarks");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			 

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			viewPageColumns.add(gpv);

			 

			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}

	}

	
	 
	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
	
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	columnMap = new LinkedHashMap<String, String>();
	columnMap.put("od.id", "Data Id");
	columnMap.put("od.orderid As Order_Id", "Order Id");
	columnMap.put("od.orderName AS Order_Name ", "Order Name");
	columnMap.put("od.orderType AS Order_Type", "Order Type");
	columnMap.put("od.uhid as UHID", "UHID");
	columnMap.put("od.pName as Patient_Name", "Patient Name");
	columnMap.put("od.roomNo as Room_No", "Room No");
	columnMap.put("od.orderBy As Order_By", "Order By");
	columnMap.put("od.orderDate AS Order_Date ", "Order Date");
	columnMap.put("od.orderTime AS Order_Time", "Order Time");
	columnMap.put("mac.serial_No as Assigned_Machine", "Assigned Machine");
	columnMap.put("dept.deptname as Department", "Department");
	columnMap.put("emp.empName as Assined_Techinician", "Assined Techinician");
	columnMap.put("od.treatingDoc As Treating_Doctor", "Treating Doctor");
	columnMap.put("od.nursingUnit AS Nursing_Unit ", "Nursing Unit");
	columnMap.put("od.treatingSpec AS Speciality", "Speciality");
	columnMap.put(" od.priority as Priority", "Priority");
	columnMap.put("od.level as Level", "Current Level");
	//columnMap.put("od.escalation_date As Escalation_Date", "Escalation Date");
	//columnMap.put("od.escalation_time AS Escalation_Time ", "Escalation Time");
	columnMap.put("od.openDate", "Open Date");
	columnMap.put("od.openTime", "Open Time");
	columnMap.put("emp1.empName as Open_By", "Open By");
	
	
	columnMap.put("od.status as Status", "Status");
	columnMap.put("history.action_reason as Action_Remarks", "Action Remarks");
	columnMap.put("emp1.empName as Action_by", "Action By");
	
	columnMap.put("history.action_date as Allot_Date", "Allot Date");
	columnMap.put("history.action_time as Allot_Time", "Allot Time");
	columnMap.put("history.allocate_to", "Allot To");
	columnMap.put("history.action_by", "Allot By");
	
	columnMap.put("history.action_date as Resolve_Date", "Resolve Date");
	columnMap.put("history.action_time as Resolve_Time", "Resolve Time");
	columnMap.put("od.finalActionBy AS Resolved_By", "Resolve By");
	columnMap.put("history.action_by AS Resolved_Action_By", "Resolve Action By");
	
	columnMap.put("rdh1.action_date as actDate", "First Esclated On");
	columnMap.put("rdh1.action_time as actTime", "First Escalate Time");
	columnMap.put("rdh1.escalate_to", "First Esclated to");
	
	columnMap.put("rdh2.action_date as actDate", "Second Esclated On");
	columnMap.put("rdh2.action_time as actTime", "Second Escalate Time");
	columnMap.put("rdh2.escalate_to", "Second Esclated to");
	
	columnMap.put("rdh3.action_date as actDate", "Third Esclated On");
	columnMap.put("rdh3.action_time as actTime", "Third Escalate Time");
	columnMap.put("rdh3.escalate_to", "Third Esclated to");
	
	columnMap.put("rdh4.action_date as actDate", "Fourth Esclated On");
	columnMap.put("rdh4.action_time as actTime", "Fourth Escalate Time");
	columnMap.put("rdh4.escalate_to", "Fourth Esclated to");
	
	columnMap.put("rdh5.action_date as actDate", "Fifth Esclated On");
	columnMap.put("rdh5.action_time as actTime", "Fifth Escalate Time");
	columnMap.put("rdh5.escalate_to", "Fifth Esclated to");
	
	columnMap.put("history.action_by as Snooze_By", " Snooze By");
	columnMap.put("history.snooze_date as Snooze_Date", " Snooze Date");
	columnMap.put("history.snooze_time as Snooze_Time", "Snooze Time");
	/*	columnMap.put("history.action_date as Action_Date", "Snooze Action Date");
	columnMap.put("history.action_time as Action_Time", "Snooze Action Time");*/
	columnMap.put("history.action_reason as Snooze_Reason", "Snooze Reason");
	
	columnMap.put("history.action_by as Cart_Allot_By","Cart Allot By");
	columnMap.put("history.allocate_to as Cart_Allot_To","Cart Allot To");
	columnMap.put("history.action_date as Cart_Allot_Date","Cart Allot Date");
	columnMap.put("history.action_time as Cart_Allot_Time","Cart Allot Time");
	/*	columnMap.put("history.action_date as Action_Date", "Snooze Action Date");
	columnMap.put("history.action_time as Action_Time", "Snooze Action Time");*/
	
	columnMap.put("history.action_date as Cart_Resolve_Date", "Cart Resolve Date");
	columnMap.put("history.action_time as Cart_Resolve_Time", "Cart Resolve Time");
	columnMap.put("od.finalActionBy AS Cart_Resolved_By", "Cart Resolve By");
	columnMap.put("history.action_by AS Cart_Resolved_Action_By","Cart Resolve Action By");
	
	//	columnMap.put("rdh.resolution_time", "Resolution Time");
	
	 
	
	  if (columnMap != null && columnMap.size() > 0)
	{
	session.put("columnMap", columnMap);
	}
	return SUCCESS;
	}
	catch (Exception ex)
	{
	ex.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}

	@SuppressWarnings(
			{ "unchecked", "rawtypes" })
			public String downloadExcel()
			{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			excelFileName = "Order Detail";

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
			query.append("Select osh.action_date,osh.action_time,osh.escalate_to,osh.feedId from order_status_history as osh where osh.status like 'Escalate%' and osh.dept!='17'");
			List escdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query=null;
			query = new StringBuilder();
			
			query.append("Select osh.action_date,osh.action_time,md.finalActionBy,emp.empName,osh.feedId from machine_order_data as md " +
			"inner join order_status_history as osh on md.id=osh.feedId " +
			"left join employee_basic as emp on emp.id=osh.action_by " +
			"where osh.status='Resolved'");
			List resdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query=null;
			query = new StringBuilder();
			
			query.append("Select emp.empName,md.parkedTill,md.parkedTillTime,osh.action_reason,osh.feedId from machine_order_data as md " +
			"inner join order_status_history as osh on md.id=osh.feedId inner join employee_basic as emp on " +
			"emp.id = osh.action_by where osh.status='Snooze'");
			List snoozedatalist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query=null;
			query = new StringBuilder();
			
			query.append("Select emp.empName as Allot_To,emp1.empName,his_ref.action_date,his_ref.action_time,his_ref.feedId from order_status_history as his_ref" +
			" left join employee_basic as emp on emp.id=his_ref.allocate_to " +
			" left join employee_basic as emp1 on emp1.id = his_ref.action_by " +
			" where his_ref.status='Cart Assigned'");
			List cartAdatalist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query=null;
			query = new StringBuilder();
			
			query.append("Select osh.action_date,osh.action_time,md.finalActionBy,emp.empName,osh.feedId from machine_order_data as md " +
			"inner join order_status_history as osh on md.id=osh.feedId " +
			"left join employee_basic as emp on emp.id=osh.action_by " +
			"where osh.status like 'Resolved%'");
			List rescartdatalist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query=null;
			query = new StringBuilder();
			
			query.append("Select his_ref.action_date,his_ref.action_time,emp.empName as Allot_To,emp1.empName,his_ref.feedId from order_status_history as his_ref" +
			" left join employee_basic as emp on emp.id=his_ref.allocate_to " +
			" left join employee_basic as emp1 on emp1.id = his_ref.action_by " +
			" where his_ref.status='Assign'");
			List informdatalist = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			query=null;
			query = new StringBuilder();
			
			
			 	query.append("select distinct ");
			int i = 0;
			for (Iterator it = keyList.iterator(); it.hasNext();)
			{
			Object obdata = (Object) it.next();
			if(!obdata.toString().equalsIgnoreCase("rdh1.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh2.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh3.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh4.action_date as actDate") && !obdata.toString().equalsIgnoreCase("rdh5.action_date as actDate")
			&& !obdata.toString().equalsIgnoreCase("rdh1.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh2.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh3.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh4.action_time as actTime") && !obdata.toString().equalsIgnoreCase("rdh5.action_time as actTime")
			&& !obdata.toString().equalsIgnoreCase("rdh1.escalate_to") && !obdata.toString().equalsIgnoreCase("rdh2.escalate_to") && !obdata.toString().equalsIgnoreCase("rdh3.escalate_to") && !obdata.toString().equalsIgnoreCase("rdh4.escalate_to") && !obdata.toString().equalsIgnoreCase("rdh5.escalate_to")
			&& !obdata.toString().equalsIgnoreCase("rdh1.comments") && !obdata.toString().equalsIgnoreCase("rdh2.comments") && !obdata.toString().equalsIgnoreCase("rdh3.comments") && !obdata.toString().equalsIgnoreCase("rdh4.comments") && !obdata.toString().equalsIgnoreCase("rdh5.comments")
			&& !obdata.toString().equalsIgnoreCase("rdh1.action_by") && !obdata.toString().equalsIgnoreCase("rdh2.action_by") && !obdata.toString().equalsIgnoreCase("rdh3.action_by") && !obdata.toString().equalsIgnoreCase("rdh4.action_by") && !obdata.toString().equalsIgnoreCase("rdh5.action_by")
			&& !obdata.toString().equalsIgnoreCase("rdh.resolution_time") 
			/*&& !obdata.toString().equalsIgnoreCase("history.action_date as Allot_Date") && !obdata.toString().equalsIgnoreCase("history.action_time as Allot_Time")
			&& !obdata.toString().equalsIgnoreCase("history.allocate_to") && !obdata.toString().equalsIgnoreCase("history.action_by")
			
			&& !obdata.toString().equalsIgnoreCase("history.action_date as Resolve_Date") && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve_Time") && !obdata.toString().equalsIgnoreCase("od.finalActionBy AS Resolved_By")
			&& !obdata.toString().equalsIgnoreCase("history.action_by AS Resolved_Action_By")
			&& !obdata.toString().equalsIgnoreCase("history.snooze_date as Snooze_Date") && !obdata.toString().equalsIgnoreCase("history.action_by as Snooze_By") && !obdata.toString().equalsIgnoreCase("history.snooze_time as Snooze_Time")
			&& !obdata.toString().equalsIgnoreCase("history.reason as Snooze_Reason")*/
			)
			{
			if (obdata != null)
			{
			if (i < keyList.size() - 1)
			{
			if(obdata.toString().equalsIgnoreCase("history.action_by AS Cart_Resolved_Action_By"))
			{
			query.append(obdata.toString());
			}else{
			query.append(obdata.toString() + ",");
			}
			}
			else
			{
			query.append(obdata.toString());
			}
			}
			}
			i++;
			}
			query.append(" FROM machine_order_data as od"); 
			query.append(" LEFT JOIN order_status_history AS history ON history.feedId = od.id"); 
			query.append(" LEFT JOIN employee_basic as emp on emp.id=od.assignTech "); 
			query.append(" LEFT JOIN employee_basic as emp1 on emp1.id=history.action_by "); 
			query.append(" LEFT JOIN department as dept on dept.id=od.department"); 
			query.append(" LEFT JOIN machine_master as mac on mac.id=od.assignMchn"); 
			query.append(" WHERE ");
			
			if (getMinDateValue() != null && !getMinDateValue().equals("")
			    && getMaxDateValue() != null && !getMaxDateValue().equals("") && searchString==null || searchString.equalsIgnoreCase("null") )
			{
			query.append(" od.openDate >= '" + DateUtil.convertDateToUSFormat(this.getMinDateValue())
			    + "' and od.openDate <= '" + DateUtil.convertDateToUSFormat(this.getMaxDateValue()) + "'");
			}
			else{
			if(null!=searchString && searchString.trim().length()>0 && !searchString.equalsIgnoreCase("undefined") && !searchString.equalsIgnoreCase("null"))
			{
			query.append(" (od.orderid like '%"+searchString+"%') or (od.uhid like '%"+searchString+"%') or (od.roomNo like '%"+searchString+"%') or (od.pName like '%"+searchString+"%') or (emp.empName like '%"+searchString+"%') or (od.status like '%"+searchString+"%')");
			}
			}
			
			if (feedStatus != null && !feedStatus.equalsIgnoreCase("-1") )
			{
			if(feedStatus.equalsIgnoreCase("All"))
			{
			query.append(" AND history.status Is Not Null ");
			}
			else{
			query.append(" AND history.status = '" + feedStatus + "'");
			}
			}
			     	else if ("-1".equalsIgnoreCase(feedStatus))
			{
			     	query.append(" AND history.status NOT IN('Ignore','CancelHIS') ");
			} 
			if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
			{
			query.append(" AND od.level = '" + escLevel + "'");
			}
			if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id() && !getSearchString().equalsIgnoreCase("undefined"))
			{
			query.append(" AND od.id IN (" + getSel_id() + ") ");
			}
			if (getNursingUnit() != null  && !getNursingUnit().equalsIgnoreCase("") && !getNursingUnit().equalsIgnoreCase("undefined") && !getNursingUnit().equals("-1"))
			{
			query.append(" AND od.nursingUnit='"+getNursingUnit()+"'");
			}
			 
			
			query.append(" GROUP BY od.id Order By od.id ");
			//  System.out.println("@@@@@DOWNLOADEXCEL"+query);
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			String orgDetail = (String) session.get("orgDetail");
			String orgName = "";
			if (orgDetail != null && !orgDetail.equals(""))
			{
			orgName = orgDetail.split("#")[0];
			}
			if (dataList != null && titleList != null && keyList != null)
			{
			excelFileName = new CommonExcelDownload().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace,escdataList,resdataList,snoozedatalist,cartAdatalist,rescartdatalist,informdatalist);
			}
			if (excelFileName != null)
			{
			excelStream = new FileInputStream(excelFileName);
			}
			 	}
			returnResult = SUCCESS;
			}
			else
			{
			addActionMessage("There are some error in data!!!!");
			returnResult = ERROR;
			}
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
		 
			
		 
	@SuppressWarnings({ "rawtypes" })
	public String getDeptRightsId()
	{
		ActivityBoardHelper ABH = new ActivityBoardHelper();
		String empId = null;
		String userType = null;
		List dataList = null;
		String deptList="";
		String empIdofuser = (String) session.get("empIdofuser");
		userType = (String) session.get("userTpe");
		if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
			return ERROR;
		empId = empIdofuser.split("-")[1].trim();

		dataList = ABH.getDeptRightsByEmpId(connectionSpace, empId, userType);
		if (dataList != null && dataList.size() >= 1)
		{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1] != null)
					{
						deptList=object[1].toString();
					}
				}
		}
		 
		return deptList;
	}

	@SuppressWarnings({ "rawtypes", "null" })
	public String getFromDeptId()
	{
		ActivityBoardHelper ABH = new ActivityBoardHelper();
		String empId = null;
		String userType = null;
		List dataList = null;
		StringBuilder fromDepId = new StringBuilder();
		String empIdofuser = (String) session.get("empIdofuser");
		userType = (String) session.get("userTpe");
		if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
			return ERROR;
		empId = empIdofuser.split("-")[1].trim();

		dataList = ABH.getServiceDeptByEmpId(connectionSpace, empId, userType);
		if (dataList == null && dataList.size() == 0)
		{
			dataList = ABH.getAllDepartment(connectionSpace, empId);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						fromDepId.append(object[0].toString() + ",");
					}
				}
			}
		}
		fromDepId.append("0");
		return fromDepId.toString();
	}

	@SuppressWarnings("rawtypes")
	public String viewActivityHistoryData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> tempList = new ArrayList<Object>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String  location = null;
				StringBuilder query = new StringBuilder();
				if (id != null)
				{
					query.append("SELECT feedback.location,feedback.id");
					query.append(" FROM feedback_status_new AS feedback");
					query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id= feedback.subCatg");
					query.append(" WHERE feedback.id=" + id);
					//System.out.println("Get Location : "+query.toString());
					List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					query.setLength(0);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							//feedby = CUA.getValueWithNullCheck(object[0]);
							//mobno = CUA.getValueWithNullCheck(object[1]);
							//catgId = CUA.getValueWithNullCheck(object[2]);
							location = CUA.getValueWithNullCheck(object[0]);
						}
					}
					dataList.clear();

					query.append("SELECT feedback.id, feedback.ticket_no, ");
					query.append("subcatg.subCategoryName, ");
					query.append("feedback.feed_brief, feedback.open_date, feedback.open_time,history.action_date, history.action_time,");
					query.append("history.action_duration, emp.empName as actionBy, history.action_remark,");
					query.append("feedback.level, feedback.status");
			 		query.append(" FROM feedback_status_new AS feedback ");
			 		query.append(" LEFT JOIN feedback_status_history AS history ON history.feedId = feedback.id");
		 			query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
					query.append(" LEFT JOIN employee_basic AS emp ON emp.id = history.action_by");
					query.append(" WHERE feedback.location ='" + location+"'");
			 		query.append(" group by  feedback.ticket_no ORDER BY feedback.id DESC");
			 		//System.out.println("Data Query : "+query.toString());
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					query.setLength(0);
					if (dataList != null && dataList.size() > 0)
					{
						List timeList = new ArrayList();
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();

							Map<String, Object> tempMap = new HashMap<String, Object>();
							tempMap.put("id", CUA.getValueWithNullCheck(object[0]));
							tempMap.put("ticketno", CUA.getValueWithNullCheck(object[1]));
							tempMap.put("subcatg", CUA.getValueWithNullCheck(object[2]));
							tempMap.put("brief", CUA.getValueWithNullCheck(object[3]));
							tempMap.put("openat", DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString().substring(0, object[5].toString().length() - 3));
							if (object[6] != null && object[7] != null)
								tempMap.put("closeat", DateUtil.convertDateToIndianFormat(object[6].toString()) + ", " + object[7].toString().substring(0, object[7].toString().length() - 3));
							else
								tempMap.put("closeat", "NA");

							tempMap.put("totalbreakdown", CUA.getValueWithNullCheck(object[8]));
							tempMap.put("actionby", CUA.getValueWithNullCheck(object[9]));
						 	tempMap.put("remarks", CUA.getValueWithNullCheck(object[10]));
							 	tempMap.put("level", CUA.getValueWithNullCheck(object[11]));
							tempMap.put("status", CUA.getValueWithNullCheck(object[12]));
							/*if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Resolved"))
							{

								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[6]), CUA.getValueWithNullCheck(object[14]), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[8]), object[6].toString(), object[7].toString(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Pending"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Snooze"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("High Priority"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Re-OpenedR"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Ignore"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[20]), CUA.getValueWithNullCheck(object[21]), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("transfer"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[22]), CUA.getValueWithNullCheck(object[23]), cbt);
							}
							if (timeList != null && timeList.size() > 0)
							{
								tempMap.put("totaltime", timeList.get(0).toString());
								tempMap.put("totalworkingtime", timeList.get(1).toString());
							}*/
							tempList.add(tempMap);

						}
					}
					setMasterViewProp(tempList);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
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


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getTotalAndWorkingTime(String complainid, String openDate, String openTime, String snoozeDate, String snoozeTime, String snoozeUp2Date, String snoozeUp2Time, String snoozeDur, String calculateDate, String calculateTime, CommonOperInterface cbt)
	{
		List dataList = new ArrayList();
		try
		{

			String totalHrs = DateUtil.time_difference(openDate, openTime, calculateDate, calculateTime);
			String totalWorkingHrs = totalHrs;
			if (snoozeDate != null && !snoozeDate.equalsIgnoreCase("NA"))
			{
				if (DateUtil.compareDateTime(snoozeUp2Date + " " + snoozeUp2Time, calculateDate + " " + calculateTime))
				{
					totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, snoozeDur);
				}
				else
				{

					String tempTime = DateUtil.time_difference(calculateDate, calculateTime, snoozeDate, snoozeTime);
					totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
				}
			}
			String query = "SELECT request_on,request_at,approved_on,approved_at,status FROM seek_approval_detail WHERE complaint_id =" + complainid;
			List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();)
				{
					Object[] object2 = (Object[]) iterator2.next();
					if (object2[4] != null && !object2[4].toString().equalsIgnoreCase("Pending"))
					{
						String tempTime = DateUtil.time_difference(object2[0].toString(), object2[1].toString(), object2[2].toString(), object2[3].toString());
						totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
					}
					else if (object2[4] != null && object2[4].toString().equalsIgnoreCase("Pending"))
					{
						String tempTime = DateUtil.time_difference(object2[0].toString(), object2[1].toString(), calculateDate, calculateTime);
						totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
					}
				}
			}

			dataList.add(totalHrs);
			dataList.add(totalWorkingHrs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public String getComplaintActivityDeatil()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ActivityBoardHelper helperObject = new ActivityBoardHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataList = null;
				dataMap = new LinkedHashMap<String, String>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				if (formaterOn != null)
				{
					if (formaterOn.equalsIgnoreCase("smsCode"))
					{
						dataMap.put("Ticket Status", "Code");
						dataMap.put("Close Ticket", "MDT CT <Ticket No.>*<Comment>");
						dataMap.put("Ignore Ticket", "MDT IT <Ticket No.>*<Comment>");
						dataMap.put("High-Priority Ticket", "MDT HT <Ticket No.>*<Comment>");
						dataMap.put("Noted Ticket", "MDT NT <Ticket No.>*<Comment>");
					}

					if (formaterOn.equalsIgnoreCase("smsResend"))
					{
						return "success";
					}
				}
				if (complianId != null && formaterOn != null && mainTable != null)
				{
					if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("feed_by"))
					{
						dataList = helperObject.getFeedByEmployeeDetail(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
								dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
							}
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("allot_to"))
					{
						dataList = helperObject.getAllotToEmployeeDetail(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
								dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
							}
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("lodgeMode"))
					{
						dataList = helperObject.getLodgerEmployeeDetail(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
								dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
							}
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("status"))
					{
						dataList = helperObject.getTicketCycle(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							getCycleByOrder(dataList);
						}
					}
					else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("TAT"))
					{
						WorkingHourAction WHA = new WorkingHourAction();
						dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, complianId);
						String addressingDate = null, addressingTime = "00:00", resolutionDate = null, resolutionTime = "00:00";
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Status", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Level", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Open Date & Time", DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[3].toString().substring(0, object[3].toString().length() - 3));
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[4].toString(), object[5].toString(), "Y", object[2].toString(), object[3].toString(), "HDM");
								addressingDate = dateTime.get(0);
								addressingTime = dateTime.get(1);
								resolutionDate = dateTime.get(2);
								resolutionTime = dateTime.get(3);
								dateTime.clear();
								dataMap.put("Addressing Date & Time", DateUtil.convertDateToIndianFormat(addressingDate) + ", " + addressingTime);
								dataMap.put("Resolution Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
								List empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "2", connectionSpace, cbt);
								StringBuilder empName = new StringBuilder();
								if (empList != null)
								{
									for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
									{
										Object[] object2 = (Object[]) iterator2.next();
										if (object2[1] != null)
											empName.append(object2[1].toString() + ", ");
									}
									if (empName != null && empName.length() > 0)
									{
										dataMap.put("L-2 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
										dataMap.put("L-2 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
									}
									empList.clear();
									empName.setLength(0);

									dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "HDM");
									resolutionDate = dateTime.get(0);
									resolutionTime = dateTime.get(1);

									empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "3", connectionSpace, cbt);

									if (empList != null && empList.size() > 0)
									{
										for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
										{
											Object[] object2 = (Object[]) iterator2.next();
											if (object2[1] != null)
												empName.append(object2[1].toString() + ", ");
										}
										if (empName != null && empName.length() > 0)
										{
											dataMap.put("L-3 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
											dataMap.put("L-3 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
										}
										empList.clear();
										empName.setLength(0);

										dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "HDM");
										resolutionDate = dateTime.get(0);
										resolutionTime = dateTime.get(1);
										empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "4", connectionSpace, cbt);

										if (empList != null && empList.size() > 0)
										{
											for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
											{
												Object[] object2 = (Object[]) iterator2.next();
												if (object2[1] != null)
													empName.append(object2[1].toString() + ", ");
											}
											if (empName != null && empName.length() > 0)
											{
												dataMap.put("L-4 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
												dataMap.put("L-4 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
											}
											empList.clear();
											empName.setLength(0);
										}
									}
								}
							}
						}
					}
					else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("closeMode"))
					{
						dataMap = new LinkedHashMap<String, String>();
						editableDataMap = new LinkedHashMap<String, String>();
						dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							dataMap = new ActionOnFeedback().getTicketDetails(complianId);
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								editableDataMap.put("Action Taken", CUA.getValueWithNullCheck(object[7]));
								editableDataMap.put("Resources Used", CUA.getValueWithNullCheck(object[8]));
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
 
	@SuppressWarnings("rawtypes")
	public List getCycleByOrder(List dataList)
	{
		FeedTicketCyclePojo  feed;
		String hisID="0";
		String hisID1="0";
		list= new ArrayList<FeedTicketCyclePojo>();
	//	List timeList = new ArrayList();
	//	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	 //String sndate=null,sntime=null,snuptodate=null,snuptotime=null,snduration=null;
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
		feed = new FeedTicketCyclePojo();
	 Object[] object = (Object[]) iterator.next();
	 if (object[0] != null )
		{
		 if (object[0].toString().equalsIgnoreCase("Pending"))
			 feed.setStatus("Register");
		 else if (object[0].toString().equalsIgnoreCase("Transfer"))
			 feed.setStatus("Re-assign");
		 else if (object[0].toString().equalsIgnoreCase("Snooze"))
			 feed.setStatus("Parked");
		 else if (object[0].toString().equalsIgnoreCase("Re-OpenedR"))
			 feed.setStatus("Re-Opened");
		 else
			 feed.setStatus(object[0].toString());
			 
		}
	 else 
		 feed.setStatus("NA");
	
	 if (object[1] != null )
		{
			feed.setAction_date( DateUtil.convertDateToIndianFormat(object[1].toString()));
		}
	 else 
		 feed.setAction_date( "NA");
	
	 if (object[2] != null )
		{
		  	 if(object[2].toString().length()>5)
		  		 feed.setAction_time(object[2].toString().substring(0, object[2].toString().length() - 3));
		  	 else
		  		feed.setAction_time(object[2].toString());
			   
		}
	 else 
		 feed.setAction_time("NA");
	
	 if (object[3] != null )
		{
			feed.setSn_upto_date ( DateUtil.convertDateToIndianFormat(object[3].toString()));
		}
	 else
		 feed.setSn_upto_date ( "NA");
	 
	 if (object[4] != null )
		{
			feed.setSn_upto_time(object[4].toString());
		}
	 else
		 feed.setSn_upto_time("NA");
		 
	 if (object[5] != null )
		{
			feed.setAction_duration(object[5].toString());
		}
	 else
		 feed.setAction_duration("NA");
	 
	 if (object[6] != null )
		{
			feed.setTasktype(object[6].toString());
		}
	 else
		 feed.setTasktype("NA");
		 
	 if (object[7] != null )
		{
		 if (object[0].toString().equalsIgnoreCase("Escalated"))
			 feed.setActionby ("");
		 else
			feed.setActionby(object[7].toString());
		}
	 else
		 feed.setActionby("NA");
		 
	 if (object[8] != null )
		{
		 
		   if(object[0].toString().equalsIgnoreCase("Snooze"))
		    {
		    	feed.setAction_remark("Reason: "+object[8].toString()+", Parked Upto: "+feed.getSn_upto_date()+", "+feed.getSn_upto_time());
		    }
		    else
		    {
		    	feed.setAction_remark(object[8].toString());
		    }
		}
	 else
		 feed.setAction_remark("NA");
	 
	 if (object[18] != null )
		{
		  if (object[0].toString().equalsIgnoreCase("Pending"))
		  {
			  feed.setAction_remark(object[18].toString());
		  }
			 
		}
	  
	 
	 if (object[8]==null && object[14] != null && !object[14].toString().equalsIgnoreCase("Level1"))
		{
		 
			 feed.setAction_remark(object[14].toString()+": "+object[7].toString());
		 }
	  
	 if (object[11] != null )
		{
			feed.setOpen_date( DateUtil.convertDateToIndianFormat(object[11].toString()));
		}
	 else
		 feed.setOpen_date("NA");
	 
	 if (object[12] != null )
		{
			feed.setOpen_time(object[12].toString().substring(0, object[12].toString().length() - 3));
		}
	 else
		 feed.setOpen_time("NA");
	 
	// System.out.println("hello");
	 if (object[15] != null )
	 {
		  if (!object[0].toString().equalsIgnoreCase("Escalated"))
		  {
			  if (object[0].toString().equalsIgnoreCase("Transfer"))
					 feed.setAllotto (object[13].toString());
				 else
				 feed.setAllotto (object[15].toString());
		  }
		  else if (object[13] != null && object[0].toString().equalsIgnoreCase("Escalated") )
			  feed.setAllotto (object[13].toString());
			
	 }
	 else if(object[15] == null && (object[13] != null))
	 	  
	 		 feed.setAllotto (object[13].toString());
	 else if(object[15] != null && (object[13] != null))
	 {
		 if (!object[0].toString().equalsIgnoreCase("Escalated"))
		 {
			 if (object[0].toString().equalsIgnoreCase("Transfer"))
				 feed.setAllotto (object[13].toString());
			 else
			 feed.setAllotto (object[15].toString());
		 }
		  else if (object[13] != null && object[0].toString().equalsIgnoreCase("Escalated") )
			  feed.setAllotto (object[13].toString());
		
	 }
 		 
	 else
		 feed.setAllotto ("NA");
	 
	
	 
	 if (object[17] != null )
	 {
		  
		 feed.setFeed_by (object[17].toString());
	 }
	 else
		  feed.setFeed_by ("NA");
	 
	 if (object[0] != null )
		{
		 if (object[0].toString().equalsIgnoreCase("Resolved"))
		 
			feed.setStatusmode ("Close" );
		 else
			 feed.setStatusmode ("Open" );
		}
	 else
		 feed.setStatusmode ("NA");
	 if (object[0].toString().equalsIgnoreCase("Snooze"))
	 {
		 /*sndate=object[1].toString();
		 sntime=object[2].toString();
		 snduration=object[5].toString();
		 snuptodate=object[3].toString();
		 snuptotime=object[4].toString();*/
		  
		 feed.setTotal_time(object[5].toString());
		 feed.setWorking_time(object[5].toString());
	 }
	 else if(object[0].toString().equalsIgnoreCase("High Priority"))
	 {
		 String totalTime = DateUtil.time_difference(object[11].toString(), object[12].toString(), object[1].toString(), object[2].toString());
		 feed.setTotal_time(totalTime);
		 feed.setWorking_time(totalTime);
	
	 }
	 else if(object[0].toString().equalsIgnoreCase("Ignore"))
	 {
		/* System.out.println("In ignore :::::    "+sndate+"    "+sntime+"  "+snduration+"   "+snuptodate+"    "+snuptotime);
		 timeList = getTotalAndWorkingTime(object[10].toString(), object[11].toString(), object[12].toString(), CUA.getValueWithNullCheck(sndate), CUA.getValueWithNullCheck(sntime), CUA.getValueWithNullCheck(snduration),
					CUA.getValueWithNullCheck(snuptodate), CUA.getValueWithNullCheck(snuptotime), CUA.getValueWithNullCheck(object[1].toString()), CUA.getValueWithNullCheck(object[2].toString()), cbt);
		*/			
		 String totalTime = DateUtil.time_difference(object[11].toString(), object[12].toString(), object[1].toString(), object[2].toString());
		 feed.setTotal_time(totalTime);
		 feed.setWorking_time(totalTime);
		 
		/* if (timeList != null && timeList.size() > 0)
			{
			 
			 feed.setTotal_time( timeList.get(0).toString());
			 feed.setWorking_time( timeList.get(1).toString());
			}
			timeList.clear();*/
	
	 }
	 list.add(feed);
	}
	 return list;
	}






	public String editSMSModeData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();

				if (resolutionDetail[0] != null || resolutionDetail[0].equals(""))
					wherClause.put("resolve_remark", resolutionDetail[0]);

				if (resolutionDetail[1] != null || resolutionDetail[1].equals(""))
					wherClause.put("spare_used", resolutionDetail[1]);

				condtnBlock.put("id", complianId);
				boolean updateFeed = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
				if (updateFeed)
				{
					addActionMessage("Data Update Sucessfully.");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Opps, There are some problem.");
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

	public Map<String, String> sortByComparator(Map<String, String> unsortMap, final boolean order)
	{

		List<Entry<String, String>> list = new LinkedList<Entry<String, String>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, String>>()
		{
			public int compare(Entry<String, String> o1, Entry<String, String> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		for (Entry<String, String> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
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
	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getFromDepart()
	{
		return fromDepart;
	}

	public void setFromDepart(String fromDepart)
	{
		this.fromDepart = fromDepart;
	}

	public String getToDepart()
	{
		return toDepart;
	}

	public void setToDepart(String toDepart)
	{
		this.toDepart = toDepart;
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

	public Map<String, String> getDataCountMap()
	{
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
		this.dataCountMap = dataCountMap;
	}
	public String getComplianId()
	{
		return complianId;
	}

	public void setComplianId(String complianId)
	{
		this.complianId = complianId;
	}

	public String getFormaterOn()
	{
		return formaterOn;
	}

	public void setFormaterOn(String formaterOn)
	{
		this.formaterOn = formaterOn;
	}

	public String getMainTable()
	{
		return mainTable;
	}

	public void setMainTable(String mainTable)
	{
		this.mainTable = mainTable;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public Map<String, Map<String, String>> getFinalStatusMap()
	{
		return finalStatusMap;
	}

	public void setFinalStatusMap(Map<String, Map<String, String>> finalStatusMap)
	{
		this.finalStatusMap = finalStatusMap;
	}

	public String getTableAlis()
	{
		return tableAlis;
	}

	public void setTableAlis(String tableAlis)
	{
		this.tableAlis = tableAlis;
	}

	public String getDivName()
	{
		return divName;
	}

	public void setDivName(String divName)
	{
		this.divName = divName;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public String[] getResolutionDetail()
	{
		return resolutionDetail;
	}

	public void setResolutionDetail(String[] resolutionDetail)
	{
		this.resolutionDetail = resolutionDetail;
	}

	public Map<String, String> getEditableDataMap()
	{
		return editableDataMap;
	}

	public void setEditableDataMap(Map<String, String> editableDataMap)
	{
		this.editableDataMap = editableDataMap;
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
	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public String getSel_id()
	{
		return sel_id;
	}

	public void setSel_id(String sel_id)
	{
		this.sel_id = sel_id;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}


	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public List<FeedTicketCyclePojo> getList()
	{
		return list;
	}

	public void setList(List<FeedTicketCyclePojo> list)
	{
		this.list = list;
	}
	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderUId() {
		return orderUId;
	}

	public void setOrderUId(String orderUId) {
		this.orderUId = orderUId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public void setDataWild(String dataWild) {
		this.dataWild = dataWild;
	}

	public String getDataWild() {
		return dataWild;
	}

	public String getHeart() {
		return heart;
	}

	public void setHeart(String heart) {
		this.heart = heart;
	}

	public String getXray() {
		return xray;
	}

	public void setXray(String xray) {
		this.xray = xray;
	}

	public String getUltra() {
		return ultra;
	}

	public void setUltra(String ultra) {
		this.ultra = ultra;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPriority() {
		return priority;
	}

	public Map<String, String> getNursingUnitList() {
		return nursingUnitList;
	}

	public void setNursingUnitList(Map<String, String> nursingUnitList) {
		this.nursingUnitList = nursingUnitList;
	}

	public String getNursingUnit()
	{
		return nursingUnit;
	}

	public void setNursingUnit(String nursingUnit)
	{
		this.nursingUnit = nursingUnit;
	}

	public Map<String, String> getNursingUnitListForPharmacy() {
		return nursingUnitListForPharmacy;
	}

	public void setNursingUnitListForPharmacy(
			Map<String, String> nursingUnitListForPharmacy) {
		this.nursingUnitListForPharmacy = nursingUnitListForPharmacy;
	}

	public Map<String, String> getUhidList() {
		return uhidList;
	}

	public void setUhidList(Map<String, String> uhidList) {
		this.uhidList = uhidList;
	}

	public Map<String, String> getPatientNameList() {
		return patientNameList;
	}

	public void setPatientNameList(Map<String, String> patientNameList) {
		this.patientNameList = patientNameList;
	}

	public Map<String, String> getRoomNoList() {
		return roomNoList;
	}

	public void setRoomNoList(Map<String, String> roomNoList) {
		this.roomNoList = roomNoList;
	}

	public Map<String, String> getEncounterIdList() {
		return encounterIdList;
	}

	public void setEncounterIdList(Map<String, String> encounterIdList) {
		this.encounterIdList = encounterIdList;
	}

}