package com.Over2Cloud.ctrl.leaveManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;

public class AttendanceHelper 
{

	@SuppressWarnings("rawtypes")
	public void attendanceData(SessionFactory connectionClient)
	{ 
		String day=null;
		String workingFlag=null;
		String totalTime=null;
		String t_intime=null;
		String t_outtime=null;
		String finalIncommin=null;
		String finalOutgoing=null;
		String status=null;
		String attendance=null;
		String eworking=null;
		boolean holidayFlag=false;
		
		   try
		   { 
				//String currentDate=DateUtil.getCurrentDateUSFormat();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List getAttendenceData=cbt.executeAllSelectQuery("SELECT empid,date,in_time,out_time FROM attendence_details WHERE date='2013-12-28' and active_id='1'", connectionClient);
				if (getAttendenceData!=null && getAttendenceData.size()>0) 
				{ 
					if(DateUtil.getCurrentTimeHourMin().equalsIgnoreCase("10:17"))
					{  Object[] object=null;
					   for (Iterator iterator = getAttendenceData.iterator(); iterator.hasNext();) 
						{ 
						     object = (Object[]) iterator.next(); 
						    String empname = object[0].toString(); 
						    String date = object[1].toString(); 
						    String intime = object[2].toString(); 
						    String outtime = object[3].toString(); 
						    
							day = DateUtil.findDayFromDate(date);
							List holidayInfo=cbt.executeAllSelectQuery("SELECT fdate FROM holiday_list WHERE fdate='"+date+"'", connectionClient);
							if (holidayInfo!=null && holidayInfo.size()>0) 
							{
								holidayFlag = true;
							}
							String query="select ftime,ttime from time_slot where empname='"+empname+"'";
							List timming=cbt.executeAllSelectQuery(query, connectionClient);
							if(timming!=null && timming.size()>0)
							{
								Object[] object2=null;
								for (Iterator iterator2 = timming.iterator(); iterator2.hasNext();) 
								{
									 object2 = (Object[]) iterator2.next();
									if (object2[0]!=null) 
									{
										t_intime = object2[0].toString();
									}
									if (object2[1]!=null) 
									{
										t_outtime = object2[1].toString();
									}
								}
							}
							if (holidayFlag) 
							{
								if (intime!=null && !intime.equalsIgnoreCase("") && outtime!=null && !outtime.equalsIgnoreCase("")) 
								{
									totalTime=DateUtil.findDifferenceTwoTime(intime,outtime);
									workingFlag = "Holiday";
								    if ((Integer.parseInt(intime.split(":")[0]) >= 13 && Integer.parseInt(totalTime.split(":")[0]) < 5 ) || (Integer.parseInt(outtime.split(":")[0]) <= 13 && Integer.parseInt(totalTime.split(":")[0])< 5 )) 
									{
										eworking = "0.5";
										status = "0.5";
										attendance = "Half Day";
									}
									else
									{
										eworking = "1";
										status = "1";
										attendance = "Present";
									}
									
									if(intime.equalsIgnoreCase(t_intime) && outtime.equalsIgnoreCase(t_outtime))
									{
										finalIncommin = "On Time";
										finalOutgoing = "On Time";
									}
									else
									{
										String incomingStatus = new AttendanceHelper().getIncommingStatus(intime,t_intime);
										finalIncommin = new AttendanceHelper().getFinalIncommingstatus(incomingStatus); 
									    String outgoingStatus = new AttendanceHelper().getIncommingStatus(outtime, t_outtime);
									    finalOutgoing = new AttendanceHelper().getFinalIncommingstatus(outgoingStatus);
									}
								}
								else 
								{
									totalTime = "NA";
									workingFlag = "Holiday";
									eworking = "0";
									status = "-1";
									attendance = "-1";
									finalIncommin = "Holiday";
									finalOutgoing = "Holiday";
								}
							} 
							else 
							{
								if (intime!=null && !intime.equalsIgnoreCase("") && day.equalsIgnoreCase("Sunday")) 
								{
									totalTime=DateUtil.findDifferenceTwoTime(intime,outtime);
									workingFlag = "Holiday";
									if ((Integer.parseInt(intime.split(":")[0]) >= 13 && Integer.parseInt(totalTime.split(":")[0]) < 5 ) || (Integer.parseInt(outtime.split(":")[0]) <= 13 && Integer.parseInt(totalTime.split(":")[0])< 5 )) 
									{
										eworking = "0.5";
										status = "0.5";
										attendance = "Half Day";
									}
									else
									{
										eworking = "1";
										status = "1";
										attendance = "Present";
									}
									if(intime.equalsIgnoreCase(t_intime) && outtime.equalsIgnoreCase(t_outtime))
									{
										finalIncommin = "On Time";
										finalOutgoing = "On Time";
									}
									else
									{
										String incomingStatus = new AttendanceHelper().getIncommingStatus(intime,t_intime);
										finalIncommin = new AttendanceHelper().getFinalIncommingstatus(incomingStatus); 
									    String outgoingStatus = new AttendanceHelper().getIncommingStatus(outtime, t_outtime);
									    finalOutgoing = new AttendanceHelper().getFinalIncommingstatus(outgoingStatus);
									}
								}
								else if(intime!=null && intime.equalsIgnoreCase("") && outtime!=null && outtime.equalsIgnoreCase("") && day.equalsIgnoreCase("Sunday") )
								{
									totalTime = "NA";
									workingFlag = "Holiday";
									eworking = "0";
									status = "-1";
									attendance = "-1";
									finalIncommin ="Holiday";
									finalOutgoing = "Holiday";
								}
								else
								{
									if (intime!=null && !intime.equalsIgnoreCase("") && outtime!=null && !outtime.equalsIgnoreCase("")) 
									{
										totalTime=DateUtil.findDifferenceTwoTime(intime,outtime);
										workingFlag = "Working";
										if ((Integer.parseInt(intime.split(":")[0]) >= 13 && Integer.parseInt(totalTime.split(":")[0]) < 5 ) || (Integer.parseInt(outtime.split(":")[0]) <= 13 && Integer.parseInt(totalTime.split(":")[0])< 5 )) 
										{
											eworking = "0";
											status="0.5";
											attendance="Half Day";
										}
										else
										{
											eworking = "0";
											status="1";
											attendance="Present";
										}
										if(intime.equalsIgnoreCase(t_intime) && outtime.equalsIgnoreCase(t_outtime))
										{
											finalIncommin = "On Time";
											finalOutgoing = "On Time";
										}
										else
										{
											String incomingStatus = new AttendanceHelper().getIncommingStatus(intime,t_intime);
											finalIncommin = new AttendanceHelper(). getFinalIncommingstatus(incomingStatus); 
										    String outgoingStatus =new AttendanceHelper().getIncommingStatus(outtime, t_outtime);
										    finalOutgoing = new AttendanceHelper().getFinalIncommingstatus(outgoingStatus);
										}
									}
									else
									{
										totalTime="NA";
										workingFlag = "Working";
										eworking = "0";
										status="0";
										attendance="Absent";
										finalIncommin = "Absent";
										finalOutgoing = "Absent";
									}
								}
							}
							System.out.println(" IN TIME :::::::::"+intime);
							System.out.println(" OUT TIME :::::::::"+outtime);
							
							System.out.println(" TIME SLOT IN TIME :::::::  "+t_intime);
							System.out.println(" TIME SLOT OUT TIME :::::::  "+t_outtime);
							
							System.out.println(" TOTAL TIME :::::::::::  "+totalTime);
							System.out.println(" DATE :::::::::::  "+date);
							System.out.println(" DAY :::::::::::  "+day);
							System.out.println(" WORKING FLAG :::::::::::  "+workingFlag);
							System.out.println(" EXTRA WORKING :::::::::::  "+eworking);
							System.out.println(" STATUS :::::::::::  "+status);
							System.out.println(" ATTENDANCE :::::::::::  "+attendance);
							System.out.println(" FINAL INCOMMING :::::::::::  "+finalIncommin);
							System.out.println(" FINAL OUTGOING :::::::::::  "+finalOutgoing);
							
							List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							InsertDataTable ob=null;
							
							ob= new InsertDataTable();
							ob.setColName("date1");
							ob.setDataName(date);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("day");
							ob.setDataName(day);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("working");
							ob.setDataName(workingFlag);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("timeslot");
							ob.setDataName(t_intime+" to "+t_outtime);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("in_time");
							ob.setDataName(intime);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("out_time");
							ob.setDataName(outtime);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("totalhour");
							ob.setDataName(totalTime);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("beforetime");
							ob.setDataName(finalIncommin);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("aftertime");
							ob.setDataName(finalOutgoing);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("comment");
							ob.setDataName("Ok");
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("empname");
							ob.setDataName(empname);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("status");
							ob.setDataName(status);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("attendence");
							ob.setDataName(attendance);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("eworking");
							ob.setDataName(eworking);
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("clientVisit");
							ob.setDataName("NA");
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("lupdate");
							ob.setDataName("NA");
							insertData.add(ob);
							
							ob= new InsertDataTable();
							ob.setColName("lupdateto");
							ob.setDataName("NA");
							insertData.add(ob);
							
							boolean	 status1=cbt.insertIntoTable("attendence_record",insertData,connectionClient); 
							System.out.println("status1 :::::::::: "+status1);
							System.out.println("------------------------------------------------------------------------");
							
						}
					}
					 Runtime rt = Runtime.getRuntime();
					 rt.gc();
					 System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					 Thread.sleep(1000*5);
					 System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				}
		   }
		   catch (Exception e) 
		   { 
			  e.printStackTrace(); 
		   }
	}
	
	public String getIncommingStatus(String in_time, String intime) 
	   {
			String spin_time[]=in_time.split(":");
			String spintime[]=intime.split(":");
			int time=Integer.parseInt(spintime[0])-Integer.parseInt(spin_time[0]);
			int time1=Integer.parseInt(spintime[1])-Integer.parseInt(spin_time[1]);
			String totalTime=time+":"+time1;
		return totalTime;
	   }
	  
	   public String getFinalIncommingstatus(String incomingStatus) 
	   {
			String finalTime=null;
			String timeSplit[]=incomingStatus.split(":");
			if(incomingStatus!=null)
			{
				if(timeSplit[0].startsWith("-") && timeSplit[1].startsWith("-"))
				{
					 finalTime="Late By "+timeSplit[0].substring(1,timeSplit[0].length())+" hour "+timeSplit[1].substring(1,timeSplit[1].length())+" mins";
				}
				else if(timeSplit[0].startsWith("-"))
				{
					int time3=(Integer.parseInt(timeSplit[0].substring(1,timeSplit[0].length()))*60)-Integer.parseInt(timeSplit[1]);
					finalTime="Late By "+time3+" mins";
				}
				else if(timeSplit[1].startsWith("-"))
				{
					finalTime="Late By "+timeSplit[0]+" hour "+timeSplit[1].substring(1,timeSplit[1].length())+" mins";
				}
				else 
					finalTime="Early By "+timeSplit[0]+" hour "+timeSplit[1]+" mins";
			 }
			return finalTime;
	   }
	
	
}
