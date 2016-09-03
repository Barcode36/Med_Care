package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class EscalationHelper {


	@SuppressWarnings("unchecked")
	public List getWorkingTimeData(String module,String day,String deptid,SessionFactory connectionSpace)
	{
		//List data= new ArrayList();
		List returnList= new ArrayList();
		/*StringBuilder query= new StringBuilder();
		try {
		//	boolean check_table = new HelpdeskUniversalHelper().check_Table("working_timing", connectionSpace);
			//if (check_table) 
			if(false)//we are not considering working hour for Medanta as every departments have 24 hr shift
			{
			query.append("select dept,holiday,fromTime,toTime,working_hrs from working_timing");
			query.append(" where day='"+day+"' and moduleName='"+module+"'");
			//System.out.println("Query   :::   "+query.toString());
			data= new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data!=null && data.size()>0) 
						{
							String dept="",holiday="",fromTime="",toTime="",working_hrs="24:00";
							for (Iterator iterator = data.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0]!=null && !object[0].toString().equals("")) {
									dept=object[0].toString();
								}
								if (object[1]!=null && !object[1].toString().equals("")) {
									holiday=object[1].toString();
								}
								if (object[2]!=null && !object[2].toString().equals("")) {
									fromTime=object[2].toString();
								}
								if (object[3]!=null && !object[3].toString().equals("")) {
									toTime=object[3].toString();
								}
								if (object[4]!=null && !object[4].toString().equals("")) {
									working_hrs=object[4].toString();
								}
							}
						  if (dept!=null && !dept.equals("")  ) 
						  {
							query.setLength(0);data.clear();returnList.clear();
							 
							query.append("select holiday,fromTime,toTime from working_timing ");
							query.append(" where moduleName='"+module+"' and day='"+day+"' ");
							query.append(" and dept="+deptid+" ");
						 	data= new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
							 
							if (data!=null && data.size()>0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();) {
									Object[] object = (Object[]) iterator.next();
									if (object[0]!=null && !object[0].toString().equals("")) {
										holiday=object[0].toString();
									}
									if (object[1]!=null && !object[1].toString().equals("")) {
										fromTime=object[1].toString();
									}
									if (object[2]!=null && !object[2].toString().equals("")) {
										toTime=object[2].toString();
									}
								}
								returnList.add(holiday);
								returnList.add(fromTime);
								returnList.add(toTime);
							}
							else {
								returnList.add("N");
								returnList.add("00:01");
								returnList.add("23:59");
								returnList.add("24:00");
							}
						
						
						
						
						  }
						  
						  
				  }
			
			
			}
			else {
				returnList.add("N");
				returnList.add("00:01");
				returnList.add("23:59");
				returnList.add("24:00");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		returnList.add("N");
		returnList.add("00:01");
		returnList.add("23:59");
		returnList.add("24:00");
		return returnList;
	}
	
	public List getWorkingTiming(String working_day,String module,SessionFactory connectionSpace)
	{
		List data=new ArrayList();
		try {
			StringBuilder query= new StringBuilder();
			query.append("select dept,holiday,fromTime,toTime,working_hrs from working_timing");
			query.append(" where day='"+working_day+"' and moduleName='"+module+"'");
			//System.out.println("Working hrs"+query);
			data= new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List getNextWorkingTimeData(String module,String newdate,String nonworkingtiming,String time_difference,String deptid,SessionFactory connectionSpace)
	{
		boolean working_flag = false;
		List data= new ArrayList();
		List returnList= new ArrayList();
		String dept="",holiday="",fromTime="",toTime="",workinghr="",nonworkinghr11="";
		
		String final_non_working=nonworkingtiming;
		String main_time_diff=time_difference;
		String updateddate=newdate;
		String working_day= DateUtil.getDayofDate(updateddate);
		try {
			 boolean check_table_flag =new HelpdeskUniversalHelper().check_Table("working_timing", connectionSpace);
			 if (check_table_flag) {
				data  = getWorkingTiming(working_day,module, connectionSpace);
			
				if (data!=null && data.size()>0) {
					for (Iterator iterator = data.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && !object[0].toString().equals("")) {
							dept=object[0].toString();
						}
						if (object[1]!=null && !object[1].toString().equals("")) {
							holiday=object[1].toString();
						}
						if (object[2]!=null && !object[2].toString().equals("")) {
							fromTime=object[2].toString();
						}
						if (object[3]!=null && !object[3].toString().equals("")) {
							toTime=object[3].toString();
						}
						if (object[4]!=null && !object[4].toString().equals("")) {
							workinghr=object[4].toString();
						}
					
					}
					//System.out.println("main_time_dif is as ...."+main_time_diff);
					main_time_diff=DateUtil.getcorrecctTime(main_time_diff);
					//System.out.println("main_time_diffis as >>"+main_time_diff);
					workinghr=DateUtil.getcorrecctTime(workinghr);
					//System.out.println(">>>"+main_time_diff+">>>"+workinghr);
					working_flag = DateUtil.checkTwoTime(main_time_diff, workinghr);
					if (working_flag) {
					
					    // Check Next date  
						final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
						final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
						main_time_diff=DateUtil.getTimeDifference(main_time_diff, workinghr);
						updateddate=DateUtil.getNewDate("day",1,updateddate);
						working_day= DateUtil.getDayofDate(updateddate);
						data.clear();
						data  = getWorkingTiming(working_day,module, connectionSpace);
						
						if (data!=null && data.size()>0) {
							for (Iterator iterator = data.iterator(); iterator.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								if (object[0]!=null && !object[0].toString().equals("")) {
									dept=object[0].toString();
								}
								if (object[1]!=null && !object[1].toString().equals("")) {
									holiday=object[1].toString();
								}
								if (object[2]!=null && !object[2].toString().equals("")) {
									fromTime=object[2].toString();
								}
								if (object[3]!=null && !object[3].toString().equals("")) {
									toTime=object[3].toString();
								}
								if (object[4]!=null && !object[4].toString().equals("")) {
									workinghr=object[4].toString();
								}
							}
						
							main_time_diff=DateUtil.getcorrecctTime(main_time_diff);
							workinghr=DateUtil.getcorrecctTime(workinghr);
							working_flag = DateUtil.checkTwoTime(main_time_diff, workinghr);
							if (working_flag) {
								// Check Next date  
								final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
								final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
								main_time_diff=DateUtil.getTimeDifference(main_time_diff, workinghr);
								updateddate=DateUtil.getNewDate("day",1,updateddate);
								working_day= DateUtil.getDayofDate(updateddate);
								data.clear();
								data  = getWorkingTiming(working_day,module, connectionSpace);
								
								if (data!=null && data.size()>0) {
									for (Iterator iterator = data.iterator(); iterator.hasNext();) {
										Object[] object = (Object[]) iterator.next();
										if (object[0]!=null && !object[0].toString().equals("")) {
											dept=object[0].toString();
										}
										if (object[1]!=null && !object[1].toString().equals("")) {
											holiday=object[1].toString();
										}
										if (object[2]!=null && !object[2].toString().equals("")) {
											fromTime=object[2].toString();
										}
										if (object[3]!=null && !object[3].toString().equals("")) {
											toTime=object[3].toString();
										}
										if (object[4]!=null && !object[4].toString().equals("")) {
											workinghr=object[4].toString();
										}
									
									}
									
									main_time_diff=DateUtil.getcorrecctTime(main_time_diff);
									workinghr=DateUtil.getcorrecctTime(workinghr);
									
									working_flag = DateUtil.checkTwoTime(main_time_diff, workinghr);
									if (working_flag) {
										// Check Next date  
										final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
										final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
										main_time_diff=DateUtil.getTimeDifference(main_time_diff, workinghr);
										updateddate=DateUtil.getNewDate("day",1,updateddate);
										working_day= DateUtil.getDayofDate(updateddate);
										data.clear();
										data  = getWorkingTiming(working_day,module, connectionSpace);
										
										if (data!=null && data.size()>0) {
											for (Iterator iterator = data.iterator(); iterator.hasNext();) {
												Object[] object = (Object[]) iterator.next();
												if (object[0]!=null && !object[0].toString().equals("")) {
													dept=object[0].toString();
												}
												if (object[1]!=null && !object[1].toString().equals("")) {
													holiday=object[1].toString();
												}
												if (object[2]!=null && !object[2].toString().equals("")) {
													fromTime=object[2].toString();
												}
												if (object[3]!=null && !object[3].toString().equals("")) {
													toTime=object[3].toString();
												}
												if (object[4]!=null && !object[4].toString().equals("")) {
													workinghr=object[4].toString();
												}
												
											}
											
											main_time_diff=DateUtil.getcorrecctTime(main_time_diff);
											workinghr=DateUtil.getcorrecctTime(workinghr);
											
											working_flag = DateUtil.checkTwoTime(main_time_diff, workinghr);
											if (working_flag) {
												//System.out.println("Inside If");
											}
											else {
												final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
											}
									}
									
									}
									else {
										final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
									}
							}
							}
							else {
								final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
							}
						}
					}
					else {
					
						final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
						final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
					}
				}
				else {
					
					updateddate=DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(updateddate),1);
					working_day= DateUtil.getDayofDate(updateddate);
					data  = getWorkingTiming(working_day,module, connectionSpace);

					for (Iterator iterator = data.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && !object[0].toString().equals("")) {
							dept=object[0].toString();
						}
						if (object[1]!=null && !object[1].toString().equals("")) {
							holiday=object[1].toString();
						}
						if (object[2]!=null && !object[2].toString().equals("")) {
							fromTime=object[2].toString();
						}
						if (object[3]!=null && !object[3].toString().equals("")) {
							toTime=object[3].toString();
						}
						if (object[4]!=null && !object[4].toString().equals("")) {
							workinghr=object[4].toString();
						}
					}
					working_flag = DateUtil.checkTwoTime(time_difference, workinghr);
					if (working_flag) {
						// Check Next date  
						final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
						final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
						updateddate=DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(updateddate),1);
						working_day= DateUtil.getDayofDate(updateddate);
						data.clear();
						data  = getWorkingTiming(working_day,module, connectionSpace);
						
						if (data!=null && data.size()>0) {
							for (Iterator iterator = data.iterator(); iterator.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								if (object[0]!=null && !object[0].toString().equals("")) {
									dept=object[0].toString();
								}
								if (object[1]!=null && !object[1].toString().equals("")) {
									holiday=object[1].toString();
								}
								if (object[2]!=null && !object[2].toString().equals("")) {
									fromTime=object[2].toString();
								}
								if (object[3]!=null && !object[3].toString().equals("")) {
									toTime=object[3].toString();
								}
								if (object[4]!=null && !object[4].toString().equals("")) {
									workinghr=object[4].toString();
								}
								
							}
							working_flag = DateUtil.checkTwoTime(time_difference, workinghr);
							if (working_flag) {
								// Check Next date  
								final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
								final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
								updateddate=DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(updateddate),1);
								 working_day= DateUtil.getDayofDate(updateddate);
									data.clear();
									data  = getWorkingTiming(working_day,module, connectionSpace);
									
									if (data!=null && data.size()>0) {
										for (Iterator iterator = data.iterator(); iterator.hasNext();) {
											Object[] object = (Object[]) iterator.next();
											if (object[0]!=null && !object[0].toString().equals("")) {
												dept=object[0].toString();
											}
											if (object[1]!=null && !object[1].toString().equals("")) {
												holiday=object[1].toString();
											}
											if (object[2]!=null && !object[2].toString().equals("")) {
												fromTime=object[2].toString();
											}
											if (object[3]!=null && !object[3].toString().equals("")) {
												toTime=object[3].toString();
											}
											if (object[4]!=null && !object[4].toString().equals("")) {
												workinghr=object[4].toString();
											}
										}
										working_flag = DateUtil.checkTwoTime(time_difference, workinghr);
										if (working_flag) {
											// Check Next date  
											final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
											final_non_working=DateUtil.addTwoTime(final_non_working, DateUtil.time_difference(updateddate, toTime, updateddate, "24:00"));
											 updateddate=newdate;
											 working_day= DateUtil.getDayofDate(updateddate);
										}
										else {
											// Calculate data on current date
											 final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
										}
									}
							}
							else {
								// Calculate data on current date
								 final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
							}
						}
					}
					else {
						// Calculate data on current date
						 final_non_working=DateUtil.addTwoTime(nonworkingtiming, DateUtil.time_difference(updateddate, "00:00", updateddate, fromTime));
					}
				}
			/*if (dept!=null && !dept.equals("") && dept.equals("A")) {
				returnList.add(holiday);
				returnList.add(fromTime);
				returnList.add(toTime);
				returnList.add(final_non_working);
				returnList.add(main_time_diff);
				returnList.add(updateddate);
			}*/
			  if (dept!=null && !dept.equals("") ) {
				StringBuilder query= new StringBuilder();
				query.setLength(0);data.clear();returnList.clear();
				query.append("select  holiday,fromTime,toTime from working_timing ");
			 	query.append(" where moduleName='"+module+"' and  day='"+working_day+"' and  dept="+deptid+" ");
				data= new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (data!=null && data.size()>0) {
					for (Iterator iterator = data.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && !object[0].toString().equals("")) {
							holiday=object[0].toString();
						}
						if (object[1]!=null && !object[1].toString().equals("")) {
							fromTime=object[1].toString();
						}
						if (object[2]!=null && !object[2].toString().equals("")) {
							toTime=object[2].toString();
						}
					}
					returnList.add(holiday);
					returnList.add(fromTime);
					returnList.add(toTime);
					returnList.add(final_non_working);
					returnList.add(main_time_diff);
					returnList.add(updateddate);
				}
			}
	}
	else {
		returnList.add("N");
		returnList.add("00:00");
		returnList.add("24:00");
		returnList.add("00:00");
		returnList.add(main_time_diff);
		returnList.add(updateddate);
	}
			 } catch (Exception e) {
		e.printStackTrace();
	}
		return returnList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean leaveUpdate(String date,SessionFactory connectionSpace)
	{
		boolean flag = false;
		List data =new ArrayList();
		try {
			boolean check_table = new HelpdeskUniversalHelper().check_Table("holiday_list", connectionSpace);
			if (check_table) {
				String query = "select * from holiday_list where holiday_date='"+date+"'";
				//System.out.println("Leave Query "+query);
				data= new createTable().executeAllSelectQuery(query, connectionSpace);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data!=null && data.size()>0) {
			flag= true;
		}
		return flag;
	}
	
	public String getNextOrPreviousWorkingDate(String leavetype,String todaydate,SessionFactory connectionSpace)
	 {
		String date=todaydate;
		boolean leaveStatus = false;
		try {
			leaveStatus = leaveUpdate(date,connectionSpace);
			 if (leaveStatus) {
			for (int i = 1; i < 20; i++) {
				
				 
					  leaveStatus = false;
					  if (leavetype.equals("P")) {
						  date = DateUtil.getNextDateAfter(-i);
					  }
					  else if (leavetype.equals("N")) {
						  date = DateUtil.getNextDateAfter(i);
					  }
					  
					  leaveStatus = leaveUpdate(date,connectionSpace);
					 if (!leaveStatus) {
						break;
					}
					 else {
						 leaveStatus=true;
						 continue;
					}
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
		return date;
	}
}

