package com.Over2Cloud.common.worktimeing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;

@SuppressWarnings("serial")
public class WorkingTimeHelper extends GridPropertyBean{

	@SuppressWarnings("unchecked")
	public List getWorkingTimeData(String module,String day,String deptid)
	{
		List data= new ArrayList();
		List returnList= new ArrayList();
		StringBuilder query= new StringBuilder();
		try {
			query.append("select dept,holiday,fromTime,toTime from working_timing");
			query.append(" where day='"+day+"' and moduleName='"+module+"'");
		//	System.out.println("Query For Holiday Status  "+query.toString());
			data= new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dept="",holiday="",fromTime="",toTime="";
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
			}
		}
		if (dept!=null && !dept.equals("") && dept.equals("A")) {
			returnList.add(holiday);
			returnList.add(fromTime);
			returnList.add(toTime);
		}
		else if (dept!=null && !dept.equals("") && dept.equals("D")) {
			//System.out.println("Insside D");
			query.setLength(0);data.clear();returnList.clear();
			query.append("select working_timing.holiday,dept_working_timing.fromTime,dept_working_timing.toTime from working_timing as working_timing");
			query.append(" inner join deptwise_working_timing as dept_working_timing on  working_timing.moduleName=dept_working_timing.moduleName");
			query.append(" where working_timing.moduleName='"+module+"' and dept_working_timing.day='"+day+"' and dept_working_timing.dept="+deptid+" ");
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
			}
		}
		//System.out.println("Return List Size is  "+returnList.size());
		return returnList;
	}
	
	
	public String getWorkingDate(String todaydate)
	{
		String date="";
		boolean leaveStatus = false;
		try {
			  date = todaydate;
			  leaveStatus = leaveUpdate(date);
			  if (leaveStatus) {
				  leaveStatus = false;
				  date = DateUtil.getNextDateAfter(1);
				  leaveStatus = leaveUpdate(date);
				  if (leaveStatus) {
					  leaveStatus = false;
					  date = DateUtil.getNextDateAfter(2);
					  leaveStatus = leaveUpdate(date);
					  if (leaveStatus) {
						  leaveStatus = false;
						  date = DateUtil.getNextDateAfter(3);
						  leaveStatus = leaveUpdate(date);
						  if (leaveStatus) {
							  leaveStatus = false;
							  date = DateUtil.getNextDateAfter(4);
							  leaveStatus = leaveUpdate(date);
							  if (leaveStatus) {
								  leaveStatus = false;
								  date = DateUtil.getNextDateAfter(5);
								  leaveStatus = leaveUpdate(date);
								  if (leaveStatus) {
									  leaveStatus = false;
									  date = DateUtil.getNextDateAfter(6);
									  leaveStatus = leaveUpdate(date);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
		return date;
	}
	
	public boolean leaveUpdate(String date)
	{
		boolean flag = false;
		List data =new ArrayList();
		try {
			String query = "select * from holiday_list where holiday_date='"+date+"'";
			data= new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data!=null && data.size()>0) {
			flag= true;
		}
		return flag;
	}
}
