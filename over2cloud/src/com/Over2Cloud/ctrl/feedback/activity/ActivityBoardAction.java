package com.Over2Cloud.ctrl.feedback.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.feedback.LodgeFeedbackForFeedback;
import com.Over2Cloud.ctrl.feedback.LodgeFeedbackHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;

public class ActivityBoardAction
{
	public String clientId;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private final CommonOperInterface cbt=new CommonConFactory().createInterface();
	private String subCategory;
	private String subdept;
	private String feedDataId;
	private String todept;
	private String recvEmail;
	private String feed_brief;
	private String recvSMS;
	
	private final MsgMailCommunication communication=new MsgMailCommunication();
	String  escalation_date = "NA",escalation_time = "NA",resolution_date = "NA",
			resolution_time = "NA",level_resolution_date = "NA", level_resolution_time = "NA",non_working_timing="00:00",new_escalation_datetime="0000-00-00#00:00";
	public String actionOnActivity()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid && getClientId()!=null)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				
				
				
				
				FeedbackUniversalAction FUA=new FeedbackUniversalAction();
				FeedbackUniversalHelper FUH=new FeedbackUniversalHelper();
				FeedbackPojo pojo=null;
				
				// Getting Details of Person who is opening ticket
				List userDetailsList = new LodgeFeedbackHelper().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
				String openBy="NA";
				String openByMailId="NA";
				String openByMobNo="NA",Address_Date_Time="NA";;
				String mode="",room_bed_no="";
				String res_time="",adrr_time="",tolevel="", esc_time="",allottoId="",to_dept_subdept = "",by_dept_subdept = "",ticketno="",feedby="",feedbymob="",feedbyemailid="",mailText ="",needesc="";
				if(userDetailsList!=null && userDetailsList.size()>0)
				{
					for (Iterator iterator = userDetailsList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							if(object[0] != null)
							{
								openBy=object[0].toString();
							}
							if(object[1] != null)
							{
								openByMobNo=object[1].toString();
							}
							if(object[2] != null)
							{
								openByMailId=object[2].toString();
							}
							if(object[3]!=null)
							{
								by_dept_subdept=object[3].toString();
							}
						}
					}
					
					// Getting Configuration Details
					List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
					if(colName!=null && colName.size()>0)
					{
						List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
						for (GridDataPropertyView tableColumes : colName)
						{
							if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category") && !tableColumes.getColomnName().equals("resolutionTime")) 
							{
								TableColumes ob1 = new TableColumes();
								ob1.setColumnname(tableColumes.getColomnName());
								ob1.setDatatype("varchar("+tableColumes.getWidth()+")");
								ob1.setConstraint("default NULL");
								TableColumnName.add(ob1);
							}
						}
						cbt.createTable22("feedback_status",TableColumnName,connectionSpace);
					}
					// Code for getting the Escalation Date and Escalation Time
					List subCategoryList = FUH.getAllData("feedback_subcategory", "id",subCategory, "subCategoryName", "ASC",connectionSpace);
					if (subCategoryList != null && subCategoryList.size() > 0) 
					{
						for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();) 
						{
							Object[] objectCol = (Object[]) iterator.next();
							if (objectCol[4] == null) 
							{
								adrr_time = "06:00";
							}
							else
							{
								adrr_time = objectCol[4].toString();
							}
							if (objectCol[5] == null)
							{
								res_time = "10:00";
							}
					    	else 
					    	{
					    		res_time = objectCol[5].toString();
					    	}
					    	
					    	if (objectCol[8] == null)
					    	{
					    		tolevel = "Level1";
					    	}
					    	else 
					    	{
					    		tolevel = objectCol[8].toString();
					    	}
					    	if (objectCol[9] == null)
					    	{
					    		needesc = "Y";
					    	} 
					    	else
					    	{
					    		needesc = objectCol[9].toString();
					    	}
						}
					}
					
					String[] adddate_time = null;
					if (needesc!=null && !needesc.equals("") && needesc.equals("Y"))
					{
						new LodgeFeedbackForFeedback().getNextEscalationDateTime(adrr_time,res_time,subdept,"FM",connectionSpace);
						String[] newdate_time = null;
						if(new_escalation_datetime != null && new_escalation_datetime != "")
						{
							newdate_time = new_escalation_datetime.split("#");
							if (newdate_time.length > 0)
							{
								escalation_date = newdate_time[0];
								escalation_time = newdate_time[1];
							}
						}
				    	 Address_Date_Time = DateUtil.newdate_BeforeTime(escalation_date, escalation_time, res_time);
				    	 if (Address_Date_Time!=null && !Address_Date_Time.equals("") && !Address_Date_Time.equals("NA")) 
				    	 {
				    		 String esc_start_time="00:00";
				    		 String esc_end_time="24:00";
				    		 String esc_working_day="";
				    		 esc_working_day= DateUtil.getDayofDate(escalation_date);
				    		 List workingTimingData=null;
				    		 workingTimingData= new EscalationHelper().getWorkingTimeData("HDM",esc_working_day,subdept,connectionSpace);
				    		 if (workingTimingData!=null && workingTimingData.size()>0)
				    		 {
				    			 String time_status ="",time_diff="",working_hrs="";
				    			 adddate_time=Address_Date_Time.split("#");
				    			 esc_start_time=workingTimingData.get(1).toString();
				    			 esc_end_time=workingTimingData.get(2).toString();

				    			 time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
				    			 if (time_status!=null && !time_status.equals("") && time_status.equals("before")) 
				    			 {
				    				 time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], escalation_date, esc_start_time);
				    				 String backdate_working_Hrs="24:00";
				    				 String previous_working_date="";
				    				 previous_working_date=new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(escalation_date),-1), connectionSpace);
				    				 esc_working_day= DateUtil.getDayofDate(previous_working_date);
				    				 workingTimingData.clear();
				    				 workingTimingData= new EscalationHelper().getWorkingTimeData("HDM",esc_working_day,subdept,connectionSpace);
				    				 if (workingTimingData!=null && workingTimingData.size()>0)
				    				 {
				    					 esc_start_time=workingTimingData.get(1).toString();
				    					 esc_end_time=workingTimingData.get(2).toString();
				    					 working_hrs =workingTimingData.get(3).toString();
				    					 if (DateUtil.checkTwoTime(working_hrs, time_diff))
				    					 {
				    						 Address_Date_Time= DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
				    					 }
				    					 else
				    					 {
				    						 time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
				    						 previous_working_date=new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNewDate("day",-1,previous_working_date), connectionSpace);
				    						 esc_working_day= DateUtil.getDayofDate(previous_working_date);
				    						 workingTimingData.clear();
				    						 workingTimingData= new EscalationHelper().getWorkingTimeData("HDM",esc_working_day,subdept,connectionSpace);
				    						 if (workingTimingData!=null && workingTimingData.size()>0)
				    						 {
				    							 esc_start_time=workingTimingData.get(1).toString();
				    							 esc_end_time=workingTimingData.get(2).toString();
				    							 working_hrs =workingTimingData.get(3).toString();
				    							 if (DateUtil.checkTwoTime(working_hrs, time_diff)) 
				    							 {
				    								 Address_Date_Time= DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
				    							 }
				    							 else
				    							 {
				    								 time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
				    							 }
				    						 }
				    					 }
				    				 }
				    			 }
				    		 }
				    	 }
					}
					else
					{
						escalation_date = "0000-00-00";
						escalation_time = "00:00";
						Address_Date_Time = DateUtil.newdate_AfterTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime() , adrr_time);
						String [] date_time_arr = Address_Date_Time.split("#");
						String esc_start_time="00:00";
						String esc_end_time="24:00";
						String esc_working_day="";
						esc_working_day= DateUtil.getDayofDate(date_time_arr[0]);
						List workingTimingData=null;
						workingTimingData= new EscalationHelper().getWorkingTimeData("FM",esc_working_day,subdept,connectionSpace);
						if (workingTimingData!=null && workingTimingData.size()>0)
						{
							String time_status ="",time_diff="",working_hrs="";
							adddate_time=Address_Date_Time.split("#");
							esc_start_time=workingTimingData.get(1).toString();
							esc_end_time=workingTimingData.get(2).toString();
							time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
							if (time_status!=null && !time_status.equals("") && (time_status.equals("before") || time_status.equals("after"))) 
							{
								time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], adddate_time[0], esc_start_time);
								String backdate_working_Hrs="24:00";
								String previous_working_date="";
								String ddd1 = DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]),-1);
								previous_working_date=new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]),-1), connectionSpace);
								esc_working_day= DateUtil.getDayofDate(previous_working_date);
								workingTimingData.clear();
								workingTimingData= new EscalationHelper().getWorkingTimeData("FM",esc_working_day,subdept,connectionSpace);
								if (needesc.equalsIgnoreCase("Y") && workingTimingData!=null && workingTimingData.size()>0)
								{
									esc_start_time=workingTimingData.get(1).toString();
									esc_end_time=workingTimingData.get(2).toString();
									working_hrs =workingTimingData.get(4).toString();
									if (DateUtil.checkTwoTime(working_hrs, time_diff)) 
									{
										Address_Date_Time= DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
									}
									else 
									{
										time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
										previous_working_date=new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(previous_working_date,-1), connectionSpace);
										esc_working_day= DateUtil.getDayofDate(previous_working_date);
										workingTimingData.clear();
										workingTimingData= new EscalationHelper().getWorkingTimeData("FM",esc_working_day,subdept,connectionSpace);
										if (workingTimingData!=null && workingTimingData.size()>0)
										{
											esc_start_time=workingTimingData.get(1).toString();
											esc_end_time=workingTimingData.get(2).toString();
											working_hrs =workingTimingData.get(4).toString();
											if (DateUtil.checkTwoTime(working_hrs, time_diff))
											{
												Address_Date_Time= DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
											}
											else
											{
												time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
											}
										}
									}
								}
							}
						}
					}
					
					// via From
					StringBuffer buffer=new StringBuffer("select mode,centerCode from feedbackdata where id='"+getFeedDataId()+"'");
					List data=cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
					if(data!=null & data.size()>0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
                            if(object[0]!=null)
                            {
                                    mode=object[0].toString();
                            }
                            if(object[1]!=null && !object[1].toString().equals(""))
                            {
                                    room_bed_no=object[1].toString();
                            }
                            else {
                                    room_bed_no="NA";
                            }
						}
					}
            	
					if (deptLevel!=null && deptLevel.equals("2"))
					{
						to_dept_subdept = subdept;
					}
					else if (deptLevel!=null && deptLevel.equals("1"))
					{
						to_dept_subdept = todept;
					}
					
					ticketno = new FeedbackUniversalHelper().getTicketNo(subdept,"FM", connectionSpace);
					ticketno="PCC"+ticketno;
					List allotto = null;
					List allotto1 = null;
					List<String> one = new ArrayList<String>();
					List<String> two = new ArrayList<String>();
	   	        
					String toDeptId=subdept;
					List<String> two_new = new ArrayList<String>(); 
					if(toDeptId!=null)
					{
						try 
						{
							pojo=new LodgeFeedbackHelper().getPatientDetailsByPatId(getClientId(),"",connectionSpace);
							boolean bedMapping=FUH.checkTable("bed_mapping",connectionSpace);
							if (bedMapping && toDeptId!=null && !toDeptId.equals("")) 
							{
								String floor_status= FUH.getFloorStatus(toDeptId,connectionSpace);
								if(floor_status!=null && floor_status.equalsIgnoreCase("B") && pojo.getCentreCode()!=null && !pojo.getCentreCode().equalsIgnoreCase(""))
								{
									allotto = FUH.getRandomEmp4BedSpecific(toDeptId,pojo.getCentreCode(),tolevel.substring(5),connectionSpace);
								}
								else
								{
									allotto = FUA.getRandomEmp4Escalation(toDeptId,"FM",tolevel.substring(5),"","",connectionSpace);
								}
							}
							else
							{
								allotto = FUA.getRandomEmp4Escalation(toDeptId,"FM",tolevel.substring(5),"","",connectionSpace);
							}
	   	
							if (allotto==null || allotto.size()==0)
							{
								String newToLevel= "";
								newToLevel = ""+(Integer.parseInt(tolevel.substring(5))+1);
								allotto = FUA.getRandomEmp4Escalation(toDeptId,"FM",newToLevel,"","",connectionSpace);
								if (allotto==null || allotto.size()==0) 
								{
									newToLevel = ""+(Integer.parseInt(newToLevel)+1);
									allotto = FUA.getRandomEmp4Escalation(toDeptId,"FM",newToLevel,"","",connectionSpace);
									if (allotto==null || allotto.size()==0) 
									{
										newToLevel = ""+(Integer.parseInt(newToLevel)+1);
										allotto = FUA.getRandomEmp4Escalation(toDeptId,"FM",newToLevel,"","",connectionSpace);
									}
								}
							}
							allotto1 = FUH.getRandomEmployee(toDeptId,deptLevel,tolevel.substring(5),allotto,"FM", connectionSpace);
							if (allotto!=null && allotto.size()>0)
							{
								for (Object object : allotto)
								{
									one.add(object.toString());
								}
							}
							if (allotto1!=null && allotto1.size()>0)
							{
								for (Object object : allotto1) 
								{
									two.add(object.toString());
								}
							}
							if (one!=null && one.size()>two.size()) 
							{
								one.removeAll(two);
								if (one.size() > 0)
								{
									for (Iterator iterator = one.iterator(); iterator.hasNext();) {
										Object object = (Object) iterator.next();
										allottoId = object.toString();
										break;
									}
								}
							} 
							else
							{
								List pending_alloted = FUA.getPendingAllotto(toDeptId,"FM");
								if (pending_alloted!=null && pending_alloted.size()>0) 
								{
									for (Object object : pending_alloted) 
									{
										two_new.add(object.toString());
									}
								}

								if (two_new!=null && two_new.size()>0) 
								{
									one.removeAll(two_new);
								}
								if (one!=null && one.size()>0)
								{
									allottoId = FUA.getRandomEmployee("FM", one);
								}
								else 
								{
									allottoId = FUA.getRandomEmployee("FM", allotto);
								}
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
						}
	   	   
					}
				// allottoId
					boolean status1=false;
					if(allottoId!=null && !(allottoId.equalsIgnoreCase("")) && pojo.getCrNo()!=null && !pojo.getCrNo().equalsIgnoreCase("NA") && pojo.getName()!=null && !pojo.getName().equalsIgnoreCase("NA"))
					{
						InsertDataTable ob=new InsertDataTable();
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						ob.setColName("ticket_no");
						ob.setDataName(ticketno);
						insertData.add(ob);
   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("feed_by_mobno");
				   	 	ob.setDataName(openByMobNo);
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("feed_by_emailid");
				   	 	ob.setDataName(openByMailId);
				   	 	insertData.add(ob);
				   	 	
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("feed_by");
				   	 	ob.setDataName(pojo.getName());
				   	 	insertData.add(ob);
				   	 	
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("location");
				   	 	ob.setDataName(pojo.getCentreCode());
				   	 	insertData.add(ob);
				   	 	
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("clientId");
				   	 	ob.setDataName(pojo.getCrNo());
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("patMobNo");
				   	 	ob.setDataName(pojo.getMobileNo());
				   	 	insertData.add(ob);
				   	 	
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("patEmailId");
				   	 	ob.setDataName(pojo.getEmailId());
				   	 	insertData.add(ob);
				   	 	
				   	 ob=new InsertDataTable();
				   	 	ob.setColName("deptHierarchy");
				   	 	ob.setDataName(deptLevel);
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("by_dept_subdept");
				   	 	ob.setDataName(by_dept_subdept);
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("subcatg");
				   	 	ob.setDataName(subCategory);
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("feed_brief");
				   	 	ob.setDataName(getFeed_brief());
				   	 	insertData.add(ob);
	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("to_dept_subdept");
				   	 	ob.setDataName(to_dept_subdept);
				   	 	insertData.add(ob);
	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("allot_to");
				   	 	ob.setDataName(allottoId);
				   	 	insertData.add(ob);
	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("escalation_date");
				   	 	ob.setDataName(escalation_date);
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("escalation_time");
				   	 	ob.setDataName(escalation_time);
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("Addr_date_time");
				   	 	ob.setDataName(Address_Date_Time);
				   	 	insertData.add(ob);
				   	 	
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("open_date");
				   	 	ob.setDataName(DateUtil.getCurrentDateUSFormat());
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("open_time");
				   	 	ob.setDataName(DateUtil.getCurrentTime());
				   	 	insertData.add(ob);
				   	 
				   	 	
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("level");
				   	 	ob.setDataName("Level1");
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("status");
				   	 	ob.setDataName("Pending");
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("via_from");
				   	 	ob.setDataName("Activity");
				   	 	insertData.add(ob);
				   	 
				   	 	ob=new InsertDataTable();
				   	 	ob.setColName("feed_registerby");
				   	 	ob.setDataName(openBy);
				   	 	insertData.add(ob);
				   	 	
				   	 	ob=new InsertDataTable();
	                    ob.setColName("moduleName");
	                    ob.setDataName("FM");
	                    insertData.add(ob);
	                    
	                    ob = new InsertDataTable();
	                	ob.setColName("non_working_time");
	                	ob.setDataName(non_working_timing);
	                	insertData.add(ob);
	                	
	                	status1=cbt.insertIntoTable("feedback_status",insertData,connectionSpace);
	                	insertData.clear();
	                	
	                	com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo fbp =null;
	                	if(status1)
	                	{
	                		List feedData = new FeedbackUniversalHelper().getFeedbackDetail(ticketno, deptLevel,"Pending","", connectionSpace);
							fbp = new FeedbackUniversalHelper().setFeedbackDataValues(feedData,"Pending",deptLevel);
							setTodept(fbp.getFeedback_to_dept());
							printTicket(fbp);
							
							if (fbp!=null) 
							{
								String orgName=new ReportsConfigurationDao().getOrganizationName(connectionSpace);
								boolean mailFlag=false;
								if (needesc.equalsIgnoreCase("Y") && fbp.getMobOne()!=null && fbp.getMobOne()!="" && fbp.getMobOne().trim().length()==10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
								{
									String levelMsg="Open Alert: Ticket No: "+fbp.getTicket_no()+", Open By: "+DateUtil.makeTitle(fbp.getFeed_registerby())+" for "+DateUtil.makeTitle(fbp.getFeed_by())+","+fbp.getCr_no()+", Location: "+fbp.getLocation()+", Feedback: "+fbp.getFeedback_subcatg()+", To be closed by :"+DateUtil.convertDateToIndianFormat(fbp.getEscalation_date())+","+fbp.getEscalation_time();
									communication.addMessage(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getMobOne(), levelMsg, ticketno, "Pending", "0","FM");
									
									if (needesc.equalsIgnoreCase("Y") && getRecvSMS().equals("true") && fbp.getFeedback_by_mobno()!=null && fbp.getFeedback_by_mobno()!="" && fbp.getFeedback_by_mobno().trim().length()==10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
									{
										String complainMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby()) + ","+ " Ticket No: " +fbp.getTicket_no()+ ", for "+fbp.getFeed_by()+", Location: "+fbp.getLocation()+", Feedback: "+fbp.getFeedback_subcatg() + ", Resolution Time: "+DateUtil.getCurrentDateIndianFormat()+","+DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5)+" ,Status is : Open";
			    		    	    	communication.addMessage(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_mobno(), complainMsg, ticketno, "Pending", "0","FM");
									}
									
									if (needesc.equalsIgnoreCase("Y") && fbp.getEmailIdOne()!=null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp,"Open Feedback Alert","Pending",deptLevel,true,orgName);
										communication.addMail(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketno,"Pending", "0",null,"FM");
									}
									
									if (needesc.equalsIgnoreCase("Y") && getRecvEmail().equals("true") && fbp.getFeedback_by_emailid()!=null && !fbp.getFeedback_by_emailid().equals("") && !fbp.getFeedback_by_emailid().equals("NA"))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp,"Open Feedback Alert","Pending",deptLevel,false,orgName);
										communication.addMail(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_emailid(), "Open Feedback Alert", mailText.toString(), ticketno,"Pending", "0",null,"FM");
									}
								}
							}
	                	}
					}
				}
				return "success";
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public void printTicket(com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo data)
	{
		Map session = ActionContext.getContext().getSession();
		String orgDetail = (String)session.get("orgDetail");
		String[] orgData = null;
		String orgName="",address="",city="",pincode="";
		if (orgDetail!=null && !orgDetail.equals("")) 
		{
			orgData = orgDetail.split("#");
			orgName=orgData[0];
			address=DateUtil.makeTitle(orgData[1]);
			city=DateUtil.makeTitle(orgData[2]);
			pincode=orgData[3];
		}
	}
	
	
	
	public String getRecvEmail() {
		return recvEmail;
	}

	public void setRecvEmail(String recvEmail) {
		this.recvEmail = recvEmail;
	}

	public String getFeed_brief() {
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief) {
		this.feed_brief = feed_brief;
	}

	public String getSubdept() {
		return subdept;
	}

	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}

	public String getTodept() {
		return todept;
	}

	public void setTodept(String todept) {
		this.todept = todept;
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getFeedDataId() {
		return feedDataId;
	}
	public void setFeedDataId(String feedDataId) {
		this.feedDataId = feedDataId;
	}
	public String getRecvSMS() {
		return recvSMS;
	}

	public void setRecvSMS(String recvSMS) {
		this.recvSMS = recvSMS;
	}
}
