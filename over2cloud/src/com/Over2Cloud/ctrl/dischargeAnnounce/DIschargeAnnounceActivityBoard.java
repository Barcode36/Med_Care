package com.Over2Cloud.ctrl.dischargeAnnounce;



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
import com.Over2Cloud.ctrl.bedTransfer.BedTransferPojo;

public class DIschargeAnnounceActivityBoard extends GridPropertyBean {
	
	static final Log log = LogFactory.getLog(viewLongPatientStayActivityBoard.class);
	CommonOperInterface coi = new CommonConFactory().createInterface();
	private String fromDate,toDate;
	private List<Object> masterViewProp = new ArrayList<Object>();
	List<DischargeAnnouncePojo> history_list_mt = null;
	List<DischargeAnnouncePojo> history_list_bl=null;
	List<BedTransferPojo>esc_list=null;
 private String id,patient_id,patient_name,encounter_id,current_bed,current_nursuing_unit,PRACTITIONER_NAME,BL_LEVEL,MT_LEVEL,BLNG_GRP_CUST_CODE;
private String event_status,mt_ticket_no, bl_ticket_no;	
 private String status,add_date_time;
 private String historyFor,level,searchStr;

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
		List<GridDataPropertyView>returnResult=getConfigurationData("mapped_dreamsol_discharge_announce_vw_configuration",accountID,connectionSpace,"",0,"table_name","dreamsol_discharge_announce_vw_configuration");
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
		//	System.out.println(status+"................."+level);
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=getColomnList("mapped_dreamsol_discharge_announce_vw_configuration", accountID, connectionSpace, "dreamsol_discharge_announce_vw_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
						    if(i<fieldNames.size()-1)
						    {
						    		query.append("disan."+obdata.toString()+",");
						    }
						    else
						    {
						    		query.append("disan."+obdata.toString()+" ");
						    }
					    }
					    i++;
				} 
				
				query.append(" from dreamsol_discharge_announce_vw as disan  where disan.id>0 "  );
              	if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase("") )
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" AND disan.INSERT_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					} else
					{
						
						query.append(" AND disan.INSERT_DATE BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
					}
					
				}
              	if(status!=null && !status.equalsIgnoreCase("-1") && !status.equalsIgnoreCase("null"))
              	{
              		String[] str=status.split(",");
              		
	              		for(int k=0;str.length>k;k++)
	              		{
	              			if(k==0)
	              			{
	              				query.append(" AND ( ");
	              			}
	              			else if(k>0)
	              			{
	              				query.append(" OR ");
	              			}
	              			if(str[k].equalsIgnoreCase("MT Pending"))
	              			{
	              				query.append("  disan.MT_STATUS='MT-Pending' ");
	              			}
	              			else if(str[k].equalsIgnoreCase("MT Close") )
	              			{
	              				query.append("   disan.MT_STATUS IN('FM-Pending','Close') ");
	              			}
	              			else if(str[k].equalsIgnoreCase("FM Pending"))
	              			{
	              				query.append("   disan.MT_STATUS='FM-Pending' ");
	              			}
	              			else if(str[k].equalsIgnoreCase("FM Close"))
	              			{
	              				query.append("   disan.MT_STATUS='Close' ");
	              			}
	              			else if(str[k].equalsIgnoreCase("BL Pending"))
	              			{
	              				query.append("   disan.BL_STATUS='BL-Pending' ");
	              			}
	              			else if(str[k].equalsIgnoreCase("BL Close"))
	              			{
	              				query.append("  disan.BL_STATUS='Close' ");
	              			}
	              			
	              		}
	              		query.append(" ) ");
              	}
            	if(level!=null && !level.equalsIgnoreCase("-1") && !level.equalsIgnoreCase("null"))
              	{
              		String[] str=status.split(",");
              		
              			for(int k=0;str.length>k;k++)
	              		{
	              			if(k==0)
	              			{
	              				query.append(" AND (");
	              			}
	              			else if(k>0)
	              			{
	              				query.append(" OR ");
	              			}
	              			
	              			if(str[k].equalsIgnoreCase("MT Pending"))
	              			{
	              				query.append("  disan.MT_Level IN ('"+level+"') ");
	              			}
	              			else if(str[k].equalsIgnoreCase("MT Close") )
	              			{
	              				query.append("  disan.MT_Level IN ('"+level+"')  ");
	              			}
	              			else if(str[k].equalsIgnoreCase("FM Pending"))
	              			{
	              				query.append("  disan.FM_Level IN ('"+level+"')  ");
	              			}
	              			else if(str[k].equalsIgnoreCase("FM Close"))
	              			{
	              				query.append("  disan.FM_Level IN ('"+level+"') ");
	              			}
	              			else if(str[k].equalsIgnoreCase("BL Pending"))
	              			{
	              				query.append("  disan.BL_Level IN ('"+level+"') ");
	              			}
	              			else if(str[k].equalsIgnoreCase("BL Close"))
	              			{
	              				query.append("  disan.BL_Level IN ('"+level+"') ");
	              			}
	              			else
	              			{
	              				query.append("  disan.BL_Level IN ('"+level+"') OR disan.FM_Level IN ('"+level+"')  ");
	              			}
	              			
	              		}
              			query.append(" ) ");
              			
              	}
            	if(searchStr!=null && !searchStr.equalsIgnoreCase("") && !searchStr.equalsIgnoreCase("null"))
            	{
            		query.append("AND disan.PATIENT_ID  like '%"+searchStr+"%' ");
            		query.append("OR disan.PATIENT_NAME  like '%"+searchStr+"%' ");
            	}
            	
				
				query.append(" order by disan.id DESC ");
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
	                                		/*if(fieldNames.get(k).toString().equalsIgnoreCase("REQUEST_DATE") || fieldNames.get(k).toString().equalsIgnoreCase("ADMISSION_DATE"))*/
	                                			one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[j].toString())+", "+checkNull(obdata[j+1]));
	                                	}
	                                	else
	                                	{
	                                			one.put(fieldNames.get(k).toString(),obdata[j].toString());
	                                	}
	                                }
                               }
                            else
                            {
                            	 if(fieldNames.get(k).toString().equalsIgnoreCase("MT_FM_TAT"))
	                                {
                            			//System.out.println(DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), checkNull(obdata[28]), checkNull(obdata[29]))+"inside>>>>>>>>>>>>>>>>"+fieldNames.get(k).toString());
	                                	one.put(fieldNames.get(k).toString(),DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), checkNull(obdata[28]), checkNull(obdata[29])));
	                                }
	                                else if(fieldNames.get(k).toString().equalsIgnoreCase("BL_TAT"))
	                                {
	                                	one.put(fieldNames.get(k).toString(),DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), checkNull(obdata[30]), checkNull(obdata[31])));
	                                }
	                                else
	                                {
	                                	one.put(fieldNames.get(k).toString(),"NA");
	                                }
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
	
	
	public String announceCommonHistory()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(historyFor.equalsIgnoreCase("both"))
				{
					returnResult = SUCCESS;
				}
				else
				{
					returnResult = "successSeperate";
				}
				fetchHistoryDetailsData(historyFor);
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

	private void fetchHistoryDetailsData( String historyFor)
	{
		try
		{
			DischargeAnnouncePojo HP = null;
			//data_list = new ArrayList<BedTransferPojo>();
			history_list_mt = new ArrayList<DischargeAnnouncePojo>();
			history_list_bl = new ArrayList<DischargeAnnouncePojo>();
			
			StringBuilder query = new StringBuilder("");
			query.append(" select his.action_date,his.action_time,his.status,his.level,his.comment from discharge_announce_history as his  ");
			query.append(" where his.dream_id=" + id);
			//query.append(" group by his.status ");
			query.append(" order by his.action_date  ,his.action_time   ");
		  List	data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		  CharSequence check="BL";
		  if (data != null && data.size() > 0)
			{
				String date="",time="";
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
								if(historyFor.equalsIgnoreCase("both"))
								{
									if(object[2].toString().contains(check))
									{
										
										HP = new DischargeAnnouncePojo();
										HP.setAction_at(checkNull(object[0])+", "+checkNull(object[1].toString()));
										HP.setStatus(checkNull(object[2]));
										HP.setAction_by("Auto");
										HP.setLevel(checkNull(object[3]));
										HP.setAllot_to(checkNull(object[4]));
										if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") || checkNull(object[2]).equalsIgnoreCase("BL Pending"))
										{
											HP.setTime_difference("--:--");
										}
										else
										{
											HP.setTime_difference(DateUtil.time_difference(date, time, checkNull(object[0]), checkNull(object[1])));
											
										}
										
										history_list_bl.add(HP);
									}
									else
									{
										HP = new DischargeAnnouncePojo();
										HP.setAction_at(checkNull(object[0])+", "+checkNull(object[1].toString()));
										HP.setStatus(checkNull(object[2]));
										HP.setAction_by("Auto");
										HP.setLevel(checkNull(object[3]));
										HP.setAllot_to(checkNull(object[4]));
										if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") || checkNull(object[2]).equalsIgnoreCase("MT Pending"))
										{
											HP.setTime_difference("--:--");
										}
										else
										{
											HP.setTime_difference(DateUtil.time_difference(date, time, checkNull(object[0]), checkNull(object[1])));
											
										}
										history_list_mt.add(HP);	
									}
								}
				
								else if(historyFor.equalsIgnoreCase("MT"))
								{
									if(!object[2].toString().contains(check))
									{
										
										HP = new DischargeAnnouncePojo();
										HP.setAction_at(checkNull(object[0])+", "+checkNull(object[1].toString()));
										HP.setStatus(checkNull(object[2]));
										HP.setAction_by("Auto");
										HP.setLevel(checkNull(object[3]));
										HP.setAllot_to(checkNull(object[4]));
										if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") || checkNull(object[2]).equalsIgnoreCase("MT Pending"))
										{
											HP.setTime_difference("--:--");
										}
										else
										{
											HP.setTime_difference(DateUtil.time_difference(date, time, checkNull(object[0]), checkNull(object[1])));
											
										}
										history_list_mt.add(HP);
									}
									
									
								}
								else if(historyFor.equalsIgnoreCase("BILLING"))
								{
									if(object[2].toString().contains(check))
									{
										
										HP = new DischargeAnnouncePojo();
										HP.setAction_at(checkNull(object[0])+", "+checkNull(object[1].toString()));
										HP.setStatus(checkNull(object[2]));
										HP.setAction_by("Auto");
										HP.setLevel(checkNull(object[3]));
										HP.setAllot_to(checkNull(object[4]));
										if(date.equalsIgnoreCase("") || time.equalsIgnoreCase("") || checkNull(object[2]).equalsIgnoreCase("BL Pending"))
										{
											HP.setTime_difference("--:--");
										}
										else
										{
											HP.setTime_difference(DateUtil.time_difference(date, time, checkNull(object[0]), checkNull(object[1])));
											
										}
										history_list_bl.add(HP);
									}
								}
								date=checkNull(object[0]);
								time=checkNull(object[1]);
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
				query.append(" SELECT count(*),disann.STATUS FROM dreamsol_discharge_announce_vw as disann ");
				if (fromDate != null && toDate != null)
				{
					query.append(" where disann.INSERT_DATE between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
				}
			
				query.append(" group by disann.STATUS  ");
				
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
							if(object[1].toString().equalsIgnoreCase("Open"))
							{
								obj.put("Open", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Close"))
							{
								obj.put("Close", object[0].toString());
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


	public String getEvent_status() {
		return event_status;
	}

	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}

	public String getMt_ticket_no() {
		return mt_ticket_no;
	}

	public void setMt_ticket_no(String mt_ticket_no) {
		this.mt_ticket_no = mt_ticket_no;
	}

	public String getBl_ticket_no() {
		return bl_ticket_no;
	}

	public void setBl_ticket_no(String bl_ticket_no) {
		this.bl_ticket_no = bl_ticket_no;
	}


	public List<DischargeAnnouncePojo> getHistory_list_mt() {
		return history_list_mt;
	}

	public void setHistory_list_mt(List<DischargeAnnouncePojo> history_list_mt) {
		this.history_list_mt = history_list_mt;
	}

	public List<DischargeAnnouncePojo> getHistory_list_bl() {
		return history_list_bl;
	}

	public void setHistory_list_bl(List<DischargeAnnouncePojo> history_list_bl) {
		this.history_list_bl = history_list_bl;
	}

	public String getHistoryFor() {
		return historyFor;
	}

	public void setHistoryFor(String historyFor) {
		this.historyFor = historyFor;
	}

	public String getPRACTITIONER_NAME() {
		return PRACTITIONER_NAME;
	}

	public void setPRACTITIONER_NAME(String practitioner_name) {
		PRACTITIONER_NAME = practitioner_name;
	}

	public String getBL_LEVEL() {
		return BL_LEVEL;
	}

	public void setBL_LEVEL(String bl_level) {
		BL_LEVEL = bl_level;
	}

	public String getMT_LEVEL() {
		return MT_LEVEL;
	}

	public void setMT_LEVEL(String mt_level) {
		MT_LEVEL = mt_level;
	}

	public String getBLNG_GRP_CUST_CODE() {
		return BLNG_GRP_CUST_CODE;
	}

	public void setBLNG_GRP_CUST_CODE(String blng_grp_cust_code) {
		BLNG_GRP_CUST_CODE = blng_grp_cust_code;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	
	
}
