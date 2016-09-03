package com.Over2Cloud.ctrl.WorkingHrs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class WorkingHourAction
{
	WorkingHourHelper WHH = new WorkingHourHelper();
	@SuppressWarnings("unchecked")
	public List<String> getAddressingEscTime(SessionFactory connection, CommonOperInterface cbt, String addressingTime, String resolutionTime, String escFlag, String dateForCalculation, String timeForCalculation, String moduleName)
	{
		List<String> addressingResTime = new ArrayList<String>();
		try
		{
			if(addressingTime!=null && resolutionTime!=null)
			{
				List<String> dayAndDateArr = WHH.getDayDate(dateForCalculation, connection, cbt, moduleName);
				if(dayAndDateArr!=null && dayAndDateArr.size()>0)
				{
					if(dayAndDateArr.get(0)!=null && !dayAndDateArr.get(0).equals("") && dayAndDateArr.get(1)!=null && !dayAndDateArr.get(1).equals(""))
					{
						String workingDay = dayAndDateArr.get(0);
						String workingDate = dayAndDateArr.get(1);
						List dataList = WHH.getWorkingTime(connection, cbt, moduleName, workingDay);
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null && object[1]!=null)
								{
									String startTime = object[0].toString();
									String endTime = object[1].toString();
									String workingHrs = object[2].toString();
									
									List<Object> addressingResolutionList = getTime(connection, cbt, addressingTime, resolutionTime,workingDate, startTime, endTime, workingHrs, moduleName, escFlag, timeForCalculation);
									if(addressingResolutionList!=null && addressingResolutionList.size()>0)
									{
										List<String> adderssList =  (List<String>) addressingResolutionList.get(0);
										List<String> resolutionList = (List<String>) addressingResolutionList.get(1);
										addressingResTime.add(adderssList.get(0));
										addressingResTime.add(adderssList.get(1));
										
										addressingResTime.add(resolutionList.get(0));
										addressingResTime.add(resolutionList.get(1));
									}
								}
							}
						}
						else
						{
							//No Holiday & Working hour calculation.
							String afterAddTime = DateUtil.newdate_AfterEscTime(dayAndDateArr.get(1), timeForCalculation, addressingTime, "Y");
							if(afterAddTime!=null)
							{
								String addressDate = afterAddTime.split("#")[0];
								String addressTime = afterAddTime.split("#")[1];
								
								addressingResTime.add(addressDate);
								addressingResTime.add(addressTime);
								
								afterAddTime = DateUtil.newdate_AfterEscTime(addressDate, addressTime, resolutionTime, escFlag);
								
								String escDate = afterAddTime.split("#")[0];
								String escTime = afterAddTime.split("#")[1];
								
								addressingResTime.add(escDate);
								addressingResTime.add(escTime);
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return addressingResTime;
	}
	public List<Object> getTime(SessionFactory connection, CommonOperInterface cbt, String addressingTime, String resolutionTime,String workingDate, String startTime, String endTime, String workingHrs, String moduleName, String escFlag, String timeForCalculation)
	{
		List<Object> addressingResolutionList = new ArrayList<Object>();
		List<String> resolutionDateTimeList = new ArrayList<String>();
		List<String> addressingDateTimeList = new ArrayList<String>();
		
		String ticketLanding = DateUtil.timeInBetween(workingDate, startTime, endTime, timeForCalculation);
		//System.out.println(ticketLanding);
		if(ticketLanding!=null)
		{
			if(ticketLanding.equalsIgnoreCase("middle"))
			{
				String remainingWorkingTime = DateUtil.findDifferenceTwoTime(timeForCalculation, endTime);
				//System.out.println(remainingWorkingTime);
				boolean flag = DateUtil.checkTwoTimeWithMilSec(addressingTime, remainingWorkingTime);
				//System.out.println(flag);
				if(flag)
				{
					String actualAddressingTime = DateUtil.addTwoTime(timeForCalculation, addressingTime);
					String vartualResolutionTime = DateUtil.addTwoTime(actualAddressingTime, resolutionTime);
					
					flag = DateUtil.checkTwoTimeWithMilSec(endTime, vartualResolutionTime);
					//System.out.println(flag);
					if(flag)
					{
						if(escFlag!=null && escFlag.equalsIgnoreCase("Y"))
						{
							//System.out.println(endTime+" >>cccc> "+ vartualResolutionTime);
							String todayResTime = DateUtil.findDifferenceTwoTime(actualAddressingTime, endTime);
							//System.out.println(todayResTime+" >xxddxx>> "+ resolutionTime);
							String nextResTime = DateUtil.findDifferenceTwoTime(todayResTime, resolutionTime);
							String forNextDate = DateUtil.getNewDate("day", 1, workingDate);
							resolutionDateTimeList= getActualWorkingTime(connection, cbt, forNextDate, startTime, nextResTime, endTime, moduleName, workingHrs);
						}
						else
						{
							//System.out.println(workingDate+" >>> "+actualAddressingTime+" >>> "+resolutionTime+" >>> "+escFlag);
							String afterAddTime = DateUtil.newdate_AfterEscTime(workingDate, actualAddressingTime, resolutionTime, escFlag);
							
							String escDate = afterAddTime.split("#")[0];
							String escTime = afterAddTime.split("#")[1];
							
							resolutionDateTimeList.add(escDate);
							resolutionDateTimeList.add(escTime);
						}
					}
					else
					{
						resolutionDateTimeList.add(workingDate);
						resolutionDateTimeList.add(vartualResolutionTime);
					}
					addressingDateTimeList.add(workingDate);
					addressingDateTimeList.add(actualAddressingTime);
				}
				else
				{
					String remainingAddresssTime = DateUtil.findDifferenceTwoTime(remainingWorkingTime, addressingTime);
					//System.out.println("sdfvjfdkb "+remainingAddresssTime);
					String forNextDate = DateUtil.getNewDate("day", 1, workingDate);
					addressingDateTimeList= getActualWorkingTime(connection, cbt, forNextDate, startTime, remainingAddresssTime, endTime, moduleName, workingHrs);
					if(escFlag!=null && escFlag.equalsIgnoreCase("Y"))
					{
						String tempWorkingHrs = DateUtil.findDifferenceTwoTime(addressingDateTimeList.get(1), endTime);
						resolutionDateTimeList= getActualWorkingTime(connection, cbt, addressingDateTimeList.get(0), addressingDateTimeList.get(1), resolutionTime, endTime, moduleName, tempWorkingHrs);
					}
					else
					{
						String afterAddTime = DateUtil.newdate_AfterEscTime(addressingDateTimeList.get(0), addressingDateTimeList.get(1), resolutionTime, escFlag);
						
						String escDate = afterAddTime.split("#")[0];
						String escTime = afterAddTime.split("#")[1];
						
						resolutionDateTimeList.add(escDate);
						resolutionDateTimeList.add(escTime);
					}
				}
			}
			else if(ticketLanding.equalsIgnoreCase("after"))
			{
				String forNextDate = DateUtil.getNewDate("day", 1, workingDate);
				addressingDateTimeList= getActualWorkingTime(connection, cbt, forNextDate, startTime, addressingTime, endTime, moduleName, workingHrs);
				if(escFlag!=null && escFlag.equalsIgnoreCase("Y"))
				{
					String tempWorkingHrs = DateUtil.findDifferenceTwoTime(addressingDateTimeList.get(1), endTime);
					resolutionDateTimeList= getActualWorkingTime(connection, cbt, addressingDateTimeList.get(0), addressingDateTimeList.get(1), resolutionTime, endTime, moduleName, tempWorkingHrs);
				}
				else
				{
					String afterAddTime = DateUtil.newdate_AfterEscTime(addressingDateTimeList.get(0), addressingDateTimeList.get(1), resolutionTime, escFlag);
					
					String escDate = afterAddTime.split("#")[0];
					String escTime = afterAddTime.split("#")[1];
					
					resolutionDateTimeList.add(escDate);
					resolutionDateTimeList.add(escTime);
				}
			}
			else if(ticketLanding.equalsIgnoreCase("before"))
			{
				addressingDateTimeList= getActualWorkingTime(connection, cbt, workingDate, startTime, addressingTime, endTime, moduleName, workingHrs);
				if(escFlag!=null && escFlag.equalsIgnoreCase("Y"))
				{
					String tempWorkingHrs = DateUtil.findDifferenceTwoTime(addressingDateTimeList.get(1), endTime);
					resolutionDateTimeList= getActualWorkingTime(connection, cbt, addressingDateTimeList.get(0), addressingDateTimeList.get(1), resolutionTime, endTime, moduleName, tempWorkingHrs);
				}
				else
				{
					String afterAddTime = DateUtil.newdate_AfterEscTime(addressingDateTimeList.get(0), addressingDateTimeList.get(1), resolutionTime, escFlag);
					
					String escDate = afterAddTime.split("#")[0];
					String escTime = afterAddTime.split("#")[1];
					
					resolutionDateTimeList.add(escDate);
					resolutionDateTimeList.add(escTime);
				}
			}
		}
		addressingResolutionList.add(addressingDateTimeList);
		addressingResolutionList.add(resolutionDateTimeList);
		return addressingResolutionList;
	}
	
	public List<String> getActualWorkingTime(SessionFactory connection, CommonOperInterface cbt, String workingDate, String startTime, String timeForCalculation, String endTime, String moduleName, String workingHrs)
	{
		List<String> addrsingOrEscTime = new ArrayList<String>();
		//System.out.println(workingDate+" @@@@ "+startTime+" "+timeForCalculation);
		
		String newDateTime = DateUtil.newdate_AfterEscTime(workingDate, startTime, timeForCalculation,"Y");
		//System.out.println("Trace::: Time :::>>"+newDateTime);
		String vartualDateTime[] = newDateTime.split("#");
		//System.out.println(vartualDateTime[0]+" >>>> "+ vartualDateTime[1]+" >>>> "+ workingDate+" >>>> "+endTime);
		boolean flag = DateUtil.comparebetweenTwodateTime(vartualDateTime[0], vartualDateTime[1], workingDate, endTime);
		//System.out.println(flag);
		
		if(flag)
		{
			workingDate = vartualDateTime[0];
			startTime = vartualDateTime[1];
			addrsingOrEscTime.add(vartualDateTime[0]);
			addrsingOrEscTime.add(vartualDateTime[1]);
		}
		else
		{
		//	System.out.println(workingHrs+" &&& "+timeForCalculation);
			String remainTime = DateUtil.findDifferenceTwoTime(workingHrs, timeForCalculation);
		//	System.out.println("remainTime "+remainTime);
			if(remainTime.contains("-"))
			{
				remainTime = remainTime.replace("-", "");
			}
			String newDate = DateUtil.getNewDate("day", 1, workingDate);
			List<String> dayAndDateArr = WHH.getDayDate(newDate, connection, cbt, moduleName);
			if(dayAndDateArr.get(0)!=null && !dayAndDateArr.get(0).equals("") && dayAndDateArr.get(1)!=null && !dayAndDateArr.get(1).equals(""))
			{
				String workingDay = dayAndDateArr.get(0);
				workingDate = dayAndDateArr.get(1);
				List dataList = WHH.getWorkingTime(connection, cbt, moduleName, workingDay);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							startTime = object[0].toString();
							endTime = object[1].toString();
							workingHrs = object[2].toString();
							addrsingOrEscTime = getActualWorkingTime(connection, cbt, workingDate, startTime, remainTime, endTime, moduleName, workingHrs);
						}
					}
				}
				else
				{
					addrsingOrEscTime.add(vartualDateTime[0]);
					addrsingOrEscTime.add(vartualDateTime[1]);
					workingDate = vartualDateTime[0];
					startTime = vartualDateTime[1];
				}
			}
			else
			{
				addrsingOrEscTime.add(vartualDateTime[0]);
				addrsingOrEscTime.add(vartualDateTime[1]);
				workingDate = vartualDateTime[0];
				startTime = vartualDateTime[1];
			}
		}
		return addrsingOrEscTime;
	}
	public String test()
	{
		//System.out.println("dsvndskndvjk");
		return "success";
	}
}