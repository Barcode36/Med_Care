package com.Over2Cloud.ctrl.bedTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.LongPatientStay.viewLongPatientStayActivityBoard;

public class BedTransferActivityBoard extends GridPropertyBean {
	
	static final Log log = LogFactory.getLog(viewLongPatientStayActivityBoard.class);
	CommonOperInterface coi = new CommonConFactory().createInterface();
	private String fromDate,toDate;
	private List<Object> masterViewProp = new ArrayList<Object>();
	List<BedTransferPojo> history_list = null;
	List<BedTransferPojo> rejectedCount=null; 
	List<BedTransferPojo>esc_list=null;
 private String id,patient_id,patient_name,contact_no,encounter_id,current_bed,current_nursuing_unit,req_date_time,requested_nursuing_unit,req_bed_class,req_bed_no,tfr_req_status;
	private String status,add_date_time;
	
	private JSONArray jsonArr = null;
	
	public String beforeViewActivityBoard()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				
				fromDate= DateUtil.getCurrentDateIndianFormat();
				toDate  = DateUtil.getCurrentDateIndianFormat();;
					returnResult = SUCCESS;
				/*	fetchFilterspeciality();
					fetchFilterAdmitting();
					fetchFilterLocation();
					fetchFilterNurshingUnit();*/
					 // createParameterList();
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
	
	public String beforeActivityBoardView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setGridColomnNames();
		}
		catch(Exception e)
		{
			 e.printStackTrace();
			 return ERROR;
		}
		return SUCCESS;
	}
	public void setGridColomnNames()
	{
		viewPageColumns=new ArrayList<GridDataPropertyView>();
		try{
		GridDataPropertyView gpv=new GridDataPropertyView();
		List<GridDataPropertyView>returnResult=getConfigurationData("mapped_dreamsol_ip_trfr_vw_configuration",accountID,connectionSpace,"",0,"table_name","dreamsol_ip_trfr_vw_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setWidth(gdp.getWidth());
			gpv.setSearch(gdp.getSearch());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setHideOrShow(gdp.getHideOrShow());
			viewPageColumns.add(gpv);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String viewActivityBoardData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=getColomnList("mapped_dreamsol_ip_trfr_vw_configuration", accountID, connectionSpace, "dreamsol_ip_trfr_vw_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
						    if(i<fieldNames.size()-1)
						    {
						    		query.append("bdtrsfr."+obdata.toString()+",");
						    }
						    else
						    {
						    		query.append("bdtrsfr."+obdata.toString()+" ");
						    }
					    }
					    i++;
				} 
				query.append(" from dreamsol_ip_trfr_vw as bdtrsfr  where bdtrsfr.id>0 "  );
              	if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase("") )
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" AND bdtrsfr.REQUEST_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					} else
					{
						
						query.append(" AND bdtrsfr.REQUEST_DATE BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
					}
					
				}
				
				query.append(" order by bdtrsfr.id DESC ");
				System.out.println("query :;:" +query);
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null && data.size()>0)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
                        Object[] obdata=(Object[])it.next();
                        Map<String, Object> one=new HashMap<String, Object>();
                        int j=0;
                        for (int k = 0; k < fieldNames.size(); k++) 
                        {
                            if(obdata[j]!=null && obdata[j]!="")
                            {
	                                if(k==0)
	                                {
	                                  
	                                	one.put(fieldNames.get(k).toString(), Integer.parseInt(obdata[j].toString()));
	                                  
	                                }
	                                else
	                                {
	            						
	                                	if(obdata[j].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))   //+12
	                                	{
	                                		if(fieldNames.get(k).toString().equalsIgnoreCase("REQUEST_DATE") || fieldNames.get(k).toString().equalsIgnoreCase("ADMISSION_DATE"))
	                                			one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[j].toString())+", "+obdata[j+1].toString());
	                                	}
	                                	else
	                                	{
	                                			one.put(fieldNames.get(k).toString(),obdata[j].toString());
	                                	}
	                                }
	                                
	    						
                               }
                            
                            else
                            {
                            	one.put(fieldNames.get(k).toString(),"NA");
                            }
                            if (!obdata[13].toString().equalsIgnoreCase("4") || obdata[13].toString().equalsIgnoreCase("9"))
    						{
    						 		one.put("escalation_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), obdata[21].toString(), obdata[22].toString()));
    						 		//System.out.println(">> "+one);
    						}
                            else {
                            	one.put("escalation_date", "Completed");
							}
                         	
                            j++;
                            }
                        Listhb.add(one);
                           
                           
                        }
                       
                    }
					setMasterViewProp(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
		catch(Exception e)
		{
			
			 e.printStackTrace();
		}
		return SUCCESS;
	
		
		
	}
	
	
	public String bedTransferHistory()
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
			BedTransferPojo HP = null;
			//data_list = new ArrayList<BedTransferPojo>();
			history_list = new ArrayList<BedTransferPojo>();
			rejectedCount = new ArrayList<BedTransferPojo>();
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
			 */
			/*HPB4 = new LabPojo();
			HPB4.setActionDate(specCol.split(" ")[0]);
			HPB4.setActionTime(specCol.split(" ")[1].toString().substring(0, 5));
			HPB4.setStatus("Specimen Collected");
			HPB4.setLevel("Level 1");
			history_list.add(HPB4);*/
			
			/*
			HPB4 = new LabPojo();
			HPB4.setActionDate(specDis.split(" ")[0]);
			HPB4.setActionTime(specDis.split(" ")[1].toString().substring(0, 5));
			HPB4.setStatus("Specimen Dispatch");
			HPB4.setLevel("Level 1");
			history_list.add(HPB4);*/
			
			
/*			HPB4 = new LabPojo();
			HPB4.setActionDate(specReg.split(" ")[0]);
			HPB4.setActionTime(specReg.split(" ")[1].toString().substring(0, 5));
			HPB4.setStatus("Specimen Registered");
			HPB4.setLevel("Level 1");
			history_list.add(HPB4);*/
			CharSequence str1="Escalate";
			StringBuilder query = new StringBuilder("");
			query.append(" select his.action_date,his.action_time,his.status,his.level,his.id,his.current_bed,his.dream_id from dreamsol_ip_trfr_vw_history as his  ");
			query.append(" where his.encounter_id=" + encounter_id);
			//query.append(" group by his.status ");
			query.append(" order by dream_id, his.action_date  ,  his.action_time  ");
		  List	data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				String date="",time="";
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object[2]!=null && !object[2].toString().contains(str1))
					{
						HP = new BedTransferPojo();
						if(object[0]!=null &&checkNull(object[0]).split("-")[0].length()>2  )
						{
							HP.setAction_date(DateUtil.convertDateToIndianFormat(checkNull(object[0]))+", "+checkNull(object[1]));
						}
						else
						{
							HP.setAction_date(checkNull(object[0])+", "+checkNull(object[1]));
						}
						
						HP.setStatus(checkNull(object[2]));
						HP.setLevel(checkNull(object[3]));
						HP.setHistory_id(checkNull(object[4]));
						HP.setCurrent_bed(checkNull(object[5]));
						HP.setId(checkNull(object[6]));
					
						if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") || checkNull(object[2]).equalsIgnoreCase("Transfer Request Initiated"))
						{
							HP.setTime_difference("-:-");
						}
						else
						{
							HP.setTime_difference(DateUtil.time_difference(date, time, checkNull(object[0]), checkNull(object[1])));
							
						}
						date=checkNull(object[0]);
						time=checkNull(object[1]);
						history_list.add(HP);
					}
					
				}
			}
			query.setLength(0);
			query.append(" select his.action_date,his.action_time,his.status,his.cancel_by,his.comment,his.current_bed from dreamsol_ip_trfr_vw_history as his  ");
			query.append(" where his.status='Transfer Cancel' AND his.encounter_id=" + encounter_id);
			query.append(" order by his.action_time  ,his.action_date desc  ");
			data=null;
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object[2]!=null && !object[2].toString().contains(str1))
					{
						HP = new BedTransferPojo();
						if(object[0]!=null &&checkNull(object[0]).split("-")[0].length()>2  )
						{
							HP.setAction_date(DateUtil.convertDateToIndianFormat(checkNull(object[0]))+", "+checkNull(object[1]));
						}
						else
						{
							HP.setAction_date(checkNull(object[0])+", "+checkNull(object[1]));
						}
						
						HP.setStatus(checkNull(object[2]));
						HP.setCancel_by(checkNull(object[3]));  
						HP.setComment(checkNull(object[4])); 
						HP.setCurrent_bed(checkNull(object[5]));
						rejectedCount.add(HP);
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
			BedTransferPojo HP = null;
			String statusFirst="";
			int i=0;
			System.out.println(status);
			CharSequence str="Escalate "+status;
			
			System.out.println(str.toString());
			StringBuilder query = new StringBuilder("");
			query.append(" select action_date,action_time, status, comment,level from dreamsol_ip_trfr_vw_history   ");
			query.append("where dream_id='" + id+"'  order by id ");
			List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				esc_list = new ArrayList<BedTransferPojo>();
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
						Object[] object = (Object[]) iterator.next();
						if(object[2]!=null && checkNull(object[2]).contains(str))
						{
							HP = new BedTransferPojo();
							HP.setAction_date(DateUtil.convertDateToIndianFormat(checkNull(object[0]))+", "+checkNull(object[1]));
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
	
	
	
	
	
	//***********************Common Code For Configuration Using Order By Sequence****************************
	
	public  List<GridDataPropertyView> getConfigurationData(String mappedMainTableName,String accountID,SessionFactory connectionSpace,String paramType,int limit,String propName,String valueName)
	{
		String mappedIds=null;
		String mappedTable=null;
		StringBuffer mappedIdsList=new StringBuffer();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List<GridDataPropertyView>returnResult=new ArrayList<GridDataPropertyView>();
		StringBuilder query1=new StringBuilder("select mappedid ,table_name from "+mappedMainTableName);
		try{
		if(!propName.equalsIgnoreCase("") && !valueName.equalsIgnoreCase("") && valueName!=null && propName!=null)
		{
			query1.append(" where "+propName+"='"+valueName+"'");
		}
		//query1.append(" limit "+limit+",1");
		List  getMappedTableForConfg=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		if(getMappedTableForConfg!=null)
		{
			for (Object c1 : getMappedTableForConfg) {
				Object[] dataC1 = (Object[]) c1;
				mappedIds=dataC1[0].toString().trim();
				mappedTable=dataC1[1].toString().trim();
			}
			if(mappedIds!=null)
			{
				String tempMappedIds[]=mappedIds.split("#");
				int i=1;
				for(String H:tempMappedIds)
				{
					if(i<tempMappedIds.length)
						mappedIdsList.append("'"+H.trim()+"' ,");
					else
						mappedIdsList.append("'"+H.trim()+"'");
					i++;
				}
			}
			StringBuilder query=new StringBuilder("select field_name,field_value,hideOrShow,formatter,align,width,editable,search,colType,mandatroy,validationType from "+mappedTable.trim() +" where id in ("+mappedIdsList+") and activeType='true' ORDER BY sequence ");
			//query.append("order by field_name");
			//System.out.println("Querry is as "+query);
			List  mappedDataWithColmAndLevel=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(mappedDataWithColmAndLevel!=null)
			{
				for (Object c1 : mappedDataWithColmAndLevel) {
					Object[] dataC1 = (Object[]) c1;
					GridDataPropertyView gpv=new GridDataPropertyView();
					
					if(dataC1[0]!=null)
						gpv.setHeadingName(dataC1[0].toString().trim());
					if(dataC1[1]!=null)
						gpv.setColomnName(dataC1[1].toString().trim());
					if(dataC1[2]!=null)
						gpv.setHideOrShow(dataC1[2].toString().trim());
					if(dataC1[3]!=null)
						gpv.setFormatter(dataC1[3].toString().trim());
					if(dataC1[4]!=null)
						gpv.setAlign(dataC1[4].toString().trim());
					if(dataC1[5]!=null)
						gpv.setWidth(Integer.parseInt(dataC1[5].toString().trim()));
					if(dataC1[6]!=null)
						gpv.setEditable(dataC1[6].toString().trim());
					if(dataC1[7]!=null)
						gpv.setSearch(dataC1[7].toString().trim());
					if(dataC1[8]!=null)
						gpv.setColType(dataC1[8].toString().trim());
					if(dataC1[9]!=null)
						gpv.setMandatroy(dataC1[9].toString().trim());
					if(dataC1[10]!=null)
						gpv.setValidationType(dataC1[10].toString().trim());
					returnResult.add(gpv);
					//returnResult.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
				}
			}}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	
	public  List<String> getColomnList(String mappedTable,String accountID,SessionFactory connectionSpace,String mappedConfTbale)
	{
		List ColomnList=new ArrayList<String>();
		ColomnList.add("id");
		List<GridDataPropertyView>returnResult=getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",mappedConfTbale);
		for(GridDataPropertyView gdp:returnResult)
		{
			ColomnList.add(gdp.getColomnName());
			
		}
		return ColomnList;
	}
	
	
	//********************************************************************************************************
	
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
				query.append(" SELECT count(*),trfr.TFR_REQ_STATUS FROM dreamsol_ip_trfr_vw as trfr ");
				if (fromDate != null && toDate != null)
				{
					query.append(" where trfr.REQUEST_DATE between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
				}
				/*if (stime != null && etime != null && !stime.trim().isEmpty() && !etime.trim().isEmpty())
				{
					query.append(" and lo.ORD_TIME between '" + stime + "' and '" + etime + "' ");
				}*/
				/*String empid = (String) session.get("empName").toString();
				String[] subModuleRightsArray = fetchSubModuleRights(empid);*/
				/*if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
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
				}*/
				/*if (nursingUnit != null && !nursingUnit.equalsIgnoreCase("-1") && !nursingUnit.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.WARD In (" + nursingUnit + ") ");
				// if(location !=null && !location.equalsIgnoreCase("-1") &&
				// !location.equalsIgnoreCase("null"))
				// query.append(" and divw.floor In("+location+") ");
				if (labName != null && !labName.equalsIgnoreCase("-1") && !labName.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.LabName In(" + labName + ") ");
				if (addDoctor != null && !addDoctor.equalsIgnoreCase("-1") && !addDoctor.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.ADMITING_DOCTOR In(" + addDoctor + ") ");
				if (speciality != null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
					query.append(" and lo.ORDERING_DOCTOR_SPECIALTY In(" + speciality + ") ");*/
				query.append(" group by trfr.TFR_REQ_STATUS  ");
				
				System.out.println("Counter>>>>>>>>>>>>>>>>>> "+query);
				dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] object =null;
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						 object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							if(object[1].toString().equalsIgnoreCase("0"))
							{
								obj.put("Initiated", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("1"))
							{
								obj.put("Accepted", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("3"))
							{
								obj.put("Nursing", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("4"))
							{
								obj.put("Completed", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("9"))
							{
								obj.put("Cancel", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							jsonArr.add(obj);
						}
					}
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
	
	
	
	
	

	String checkNull(Object obj)
	{
		String returnResult = "NA";
		if (obj != null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1"))
		{
			returnResult = obj.toString().trim();
		}
		return returnResult;
	}
	
	// setter Getter
	
	
	public CommonOperInterface getCoi() {
		return coi;
	}

	public void setCoi(CommonOperInterface coi) {
		this.coi = coi;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public static Log getLog() {
		return log;
	}

	public List<Object> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public List<BedTransferPojo> getHistory_list() {
		return history_list;
	}

	public void setHistory_list(List<BedTransferPojo> history_list) {
		this.history_list = history_list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getEncounter_id() {
		return encounter_id;
	}

	public void setEncounter_id(String encounter_id) {
		this.encounter_id = encounter_id;
	}

	public String getCurrent_bed() {
		return current_bed;
	}

	public void setCurrent_bed(String current_bed) {
		this.current_bed = current_bed;
	}

	public String getCurrent_nursuing_unit() {
		return current_nursuing_unit;
	}

	public void setCurrent_nursuing_unit(String current_nursuing_unit) {
		this.current_nursuing_unit = current_nursuing_unit;
	}

	public String getReq_date_time() {
		return req_date_time;
	}

	public void setReq_date_time(String req_date_time) {
		this.req_date_time = req_date_time;
	}

	public String getRequested_nursuing_unit() {
		return requested_nursuing_unit;
	}

	public void setRequested_nursuing_unit(String requested_nursuing_unit) {
		this.requested_nursuing_unit = requested_nursuing_unit;
	}

	public String getReq_bed_class() {
		return req_bed_class;
	}

	public void setReq_bed_class(String req_bed_class) {
		this.req_bed_class = req_bed_class;
	}

	public String getReq_bed_no() {
		return req_bed_no;
	}

	public void setReq_bed_no(String req_bed_no) {
		this.req_bed_no = req_bed_no;
	}

	public String getTfr_req_status() {
		return tfr_req_status;
	}

	public void setTfr_req_status(String tfr_req_status) {
		this.tfr_req_status = tfr_req_status;
	}

	public List<BedTransferPojo> getEsc_list() {
		return esc_list;
	}

	public void setEsc_list(List<BedTransferPojo> esc_list) {
		this.esc_list = esc_list;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdd_date_time() {
		return add_date_time;
	}

	public void setAdd_date_time(String add_date_time) {
		this.add_date_time = add_date_time;
	}

	public JSONArray getJsonArr() {
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	public List<BedTransferPojo> getRejectedCount()
	{
		return rejectedCount;
	}

	public void setRejectedCount(List<BedTransferPojo> rejectedCount)
	{
		this.rejectedCount = rejectedCount;
	}

	
	
	
	
	
}
