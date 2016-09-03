package com.Over2Cloud.ctrl.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;

public class DSRgerneration {

	
	
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	public void setDSRRecords(String targetAchived, int empId, DSRMode mode,DSRType type)
	{
		
		String[] months = { "January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December" };
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		/*String str[] = DateUtil.getCurrentDateIndianFormat().split("-");
		int day = Integer.parseInt(str[0]);*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		//int month = cal.get(Calendar.MONTH);
		//int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		boolean firstWeek = false;
		boolean secondWeek = false;
		boolean thirdWeek = false;
		boolean fourthWeek = false;
		
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=null;
		
		String firstWeekReport = "0#0#0#0#0#0#0#";
		String secondWeekReport = "0#0#0#0#0#0#0#";
		String thirdWeekReport = "0#0#0#0#0#0#0#";
		String fourthWeekReport = "0#0#0#0#0#0#0#0#0#0#";
		
		Calendar calendar = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		List <TableColumes> tableColumn=new ArrayList<TableColumes>();
		List<String> columnName = new ArrayList<String>();
		
		//columnName.add("id");
		columnName.add("user");
		columnName.add("currentDate");
		columnName.add("currentMonth");
		columnName.add("firstWeekReport");
		columnName.add("fourthWeekReport");
		
		columnName.add("kpi_id");
		columnName.add("kpi_target");
		
		columnName.add("secondWeekReport");
		columnName.add("thirdWeekReport");
		columnName.add("targetPeriodFlag");
		//columnName.add("historyIds");
		
		for(String cloumn : columnName)
		{
			TableColumes ob1=new TableColumes();
			ob1.setColumnname(cloumn);
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
		}
			
		
		boolean status = false;
		String tableName = "";
		if(mode == DSRMode.OFFERING)
		{
			tableName = "offeringDailyReport";
			status = cbt.createTable22("offeringDailyReport",tableColumn,connectionSpace);
		}
		else if(mode == DSRMode.KPI)
		{
			tableName = "dailyreport";
			status = cbt.createTable22("dailyreport",tableColumn,connectionSpace);
		}
		
		
		if(days == 28)
		{
			fourthWeekReport = "0#0#0#0#0#0#0#";
		}
		else if(days == 29) 
		{
			fourthWeekReport = "0#0#0#0#0#0#0#0#";
		}
		else if(days == 30)
		{
			fourthWeekReport = "0#0#0#0#0#0#0#0#0#";
		}
		else if(days == 31)
		{
			fourthWeekReport = "0#0#0#0#0#0#0#0#0#0#";
		}
		if(day >= 1 && day <= 7 )
		{
			firstWeek = true;
		}
		else if(day >= 8 && day <= 14)
		{
			secondWeek = true;
		}
		else if(day >= 15 && day <= 21)
		{
			thirdWeek = true;
		}
		else
		{
			fourthWeek = true;
		}
		List report = null;
		String week = "";
		if(firstWeek)
		{
			week = "firstWeekReport";
			String query = "select firstWeekReport from "+tableName+" where kpi_id="+ targetAchived + " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		else if (secondWeek) {
			week = "secondWeekReport";
			String query = "select secondweekreport from "+tableName+" where kpi_id="+ targetAchived+ " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		else if (thirdWeek) {
			week = "thirdWeekReport";
			String query = "select thirdWeekReport from "+tableName+" where kpi_id="+ targetAchived+ " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		else {
			week = "fourthWeekReport";
			String query = "select fourthWeekReport from "+tableName+" where kpi_id="+ targetAchived+ " and user = '"+userName+"' and currentMonth = '"+getCurrentMonthYear()+"'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		insertData=new ArrayList<InsertDataTable>();
		ob=new InsertDataTable();
		ob.setColName("user");
		ob.setDataName(userName);
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("currentDate");
		ob.setDataName(DateUtil.getCurrentDateUSFormat());
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("currentMonth");
		//ob.setDataName(months[DateUtil.getCurrentMonth()-1]+"-"+ DateUtil.getCurretnYear());
		ob.setDataName(getCurrentMonthYear());
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("targetPeriodFlag");
		ob.setDataName("M");
		insertData.add(ob);
		
		ob=new InsertDataTable();
		ob.setColName("kpi_id");
		ob.setDataName(targetAchived);
		insertData.add(ob);
		
		/**
		 *  for set target value in kpi_target column in dailyreport table.
		 * */
		Map<String, Object>wherClause=new HashMap<String, Object>();
		Map<String, Object>condtnBlock=new HashMap<String, Object>();
		
		String query12 = ""; 
		if(mode == DSRMode.OFFERING)
		{
			query12 = "select offeringId,targetvalue from offeringtarget where empID='"+ empId +"' and targetMonth = '"+getCurrentMonthYear()+"'";
		}
		else if(mode == DSRMode.KPI)
		{
			query12 = "select KPIId,targetvalue from target where empID='"+ empId +"' and targetMonth = '"+getCurrentMonthYear()+"'";
		}
		List list = cbt.executeAllSelectQuery(query12, connectionSpace);
		String kpi = "";
		String targetValue = "";
		
		if(list != null)
		{
			for (Object c : list) {
				Object[] data = (Object[]) c;
				kpi = data[0].toString();
				targetValue = data[1].toString();
			}
			String s[] = kpi.split("#");
			String x[] = targetValue.split("#");
			
			
			for(int i=0; i<s.length; i++)
			{
				if(s[i].equalsIgnoreCase(targetAchived))
				{
					targetValue = x[i];
					//System.out.println("x[i]="+x[i]);
					ob=new InsertDataTable();
					ob.setColName("kpi_target");
					ob.setDataName(targetValue);
					insertData.add(ob);
					
					break;
				}
			}
		}
		
		if( report != null && report.size()>0)
		{
			String weekDays = report.toString();
			if(weekDays != null)
			{
				System.out.println("report:"+report);
				weekDays = weekDays.substring(1, weekDays.length()-1);
				System.out.println("weekDays:"+weekDays);
				String s[] = weekDays.split("#");
				int index=0;
				if(firstWeek)
				{
					index = day-1;
				}
				else if(secondWeek)
				{
					index = day-8;
				}
				else if(thirdWeek)
				{
					index = day-15;
				}
				else
				{
					index = day-22;
				}
				//increment in target achieved in week day
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				wherClause.put(week, stringBuffer.toString());
				wherClause.put("currentDate", DateUtil.getCurrentDateUSFormat());
				wherClause.put("kpi_target", targetValue);
				wherClause.put("mode", type.toString());//update line 18-1-2014
				condtnBlock.put("kpi_id","'"+targetAchived+"'");
				condtnBlock.put("user","'"+userName+"'");
				condtnBlock.put("currentMonth","'"+getCurrentMonthYear()+"'");
				
				cbt.updateTableColomn(tableName, wherClause, condtnBlock,connectionSpace);
			}
		}
		else
		{
			int index=0;
			if(firstWeek)
			{
				String s[] = firstWeekReport.split("#");
				index = Math.abs(day-1);
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				firstWeekReport = stringBuffer.toString();
			}
			else if(secondWeek)
			{
				String s[] = secondWeekReport.split("#");
				index = Math.abs(day-8);
				
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				secondWeekReport = stringBuffer.toString();
			}
			else if(thirdWeek)
			{
				String s[] = thirdWeekReport.split("#");
				index = Math.abs(day-15);
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				thirdWeekReport = stringBuffer.toString();
			}
			else
			{
				String s[] = fourthWeekReport.split("#");
				index = Math.abs(day-22);
				s[index] = String.valueOf(Integer.parseInt(s[index]) + 1);
				
				StringBuffer stringBuffer = new StringBuffer();
				for(int j=0; j<s.length; j++)
				{
					stringBuffer.append(s[j]+"#");
				}
				
				fourthWeekReport = stringBuffer.toString();
			}
			
			ob=new InsertDataTable();
			ob.setColName("firstWeekReport");
			ob.setDataName(firstWeekReport);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("secondWeekReport");
			ob.setDataName(secondWeekReport);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("thirdWeekReport");
			ob.setDataName(thirdWeekReport);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("fourthWeekReport");
			ob.setDataName(fourthWeekReport);
			insertData.add(ob);
			//update line 18-1-2014
			ob=new InsertDataTable();
			ob.setColName("mode");
			ob.setDataName(type.toString());
			insertData.add(ob);
			
			cbt.insertIntoTable(tableName,insertData,connectionSpace);
		}
	}
	
	public void setManualDSRRecords(long id, String empId, DSRMode dsrMode,List<String> requestParameterNames, HttpServletRequest request, DSRType type) {
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		Map<String, Object>wherClause=new HashMap<String, Object>();
		Map<String, Object>condtnBlock=new HashMap<String, Object>();
		
		Collections.sort(requestParameterNames);
		Iterator it = requestParameterNames.iterator();
		while (it.hasNext()) {
			String Parmname = it.next().toString();
			String paramValue=request.getParameter(Parmname);
			wherClause.put(Parmname, paramValue);
		}
	 	Map<Integer, Object> map =  getDateMap(wherClause, request);
	 	
	 	List <TableColumes> tableColumn=new ArrayList<TableColumes>();
		List<String> columnName = new ArrayList<String>();
		
		//columnName.add("id");
		columnName.add("user");
		columnName.add("currentDate");
		columnName.add("currentMonth");
		columnName.add("firstWeekReport");
		columnName.add("fourthWeekReport");
		
		columnName.add("kpi_id");
		columnName.add("kpi_target");
		
		columnName.add("secondWeekReport");
		columnName.add("thirdWeekReport");
		columnName.add("targetPeriodFlag");
		columnName.add("mode");
		//columnName.add("historyIds");
		
		for(String cloumn : columnName)
		{
			TableColumes ob1=new TableColumes();
			ob1.setColumnname(cloumn);
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
		}
			
		
		boolean status = false;
		String tableName = "";
		
		if(dsrMode == DSRMode.OFFERING)
		{
			status = cbt.createTable22("offeringDailyReport",tableColumn,connectionSpace);
		}
		else if(dsrMode == DSRMode.KPI)
		{
			status = cbt.createTable22("dailyreport",tableColumn,connectionSpace);
		}
		
		List  list  = new ArrayList();
		
		if(dsrMode == DSRMode.OFFERING)
		{
			list = cbt.executeAllSelectQuery("select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport from offeringdailyreport where kpi_id="+ id +"  and user='"+userName+"'", connectionSpace);
		}
		else
		{
			list = cbt.executeAllSelectQuery("select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport from dailyreport where kpi_id="+ id +"  and user='"+userName+"'", connectionSpace);
		}
		
		String days = "";
		String daysArray[] = null;
		
		String firstWeek = "";
		String secondWeek = "";
		String thirdWeek = "";
		String fourthWeek = "";

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			
			days =  object[0].toString() + object[1].toString()+ object[2].toString()+object[3].toString();
		}
		daysArray = days.split("#");
		String value = null;
	 	for (Map.Entry<Integer, Object> entry : map.entrySet()) {
	 		
	 		value = entry.getValue().toString();
	 		if(value == null || value.equalsIgnoreCase(""))
	 		{
	 			value = "0";
	 		}
	 		if(entry.getKey() >= 1 && entry.getKey() <= 7 )
			{
				firstWeek += value +"#";
			}
			else if(entry.getKey() >= 8 && entry.getKey() <= 14)
			{
				secondWeek += value +"#";
			}
			else if(entry.getKey() >= 15 && entry.getKey() <= 21)
			{
				thirdWeek += value +"#";
			}
			else
			{
				fourthWeek += value +"#";
			}
	 	}
	 	
	 	if(list.size()>0)
	 	{
		 	wherClause=new HashMap<String, Object>();
		 	
		 	wherClause.put("firstWeekReport", firstWeek);
		 	wherClause.put("secondWeekReport",secondWeek);
		 	wherClause.put("thirdWeekReport",thirdWeek);
		 	wherClause.put("fourthWeekReport", fourthWeek);
			
		 	condtnBlock.put("kpi_id",id);
		 	
		 	if(dsrMode == DSRMode.OFFERING)
		 	{
		 		cbt.updateTableColomn("offeringdailyreport", wherClause, condtnBlock,connectionSpace);
		 	}
		 	else {
		 		cbt.updateTableColomn("dailyreport", wherClause, condtnBlock,connectionSpace);
			}
	 	}
	 	else
	 	{
	 		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			InsertDataTable ob=null;
			String offeringID = null;
			String offeringTarget = null;
			
			String offeringArray[] = null;
			String targetArray[] = null;
			
			
			if(dsrMode == DSRMode.OFFERING)
			{
				list = cbt.executeAllSelectQuery("select offeringId,targetvalue from offeringtarget where empID="+empId, connectionSpace);
			}
			else
			{
				list = cbt.executeAllSelectQuery("select KPIId,targetvalue from target where empID="+empId, connectionSpace);
			}
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) 
			{
				Object[] offeringDetail = (Object[]) iterator.next();
				offeringID = offeringDetail[0].toString();
				offeringTarget = offeringDetail[1].toString();
			}
			
			offeringArray = offeringID.split("#");
			targetArray = offeringTarget.split("#");

			int index = 0;
			for (int i = 0; i < offeringArray.length; i++) {
				if(offeringArray[i].equalsIgnoreCase(String.valueOf(id)))
				{
					index = i;
					break;
				}
			}
			
			insertData=new ArrayList<InsertDataTable>();
			ob=new InsertDataTable();
			ob.setColName("user");
			ob.setDataName(userName);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("currentDate");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("currentMonth");
			ob.setDataName(getCurrentMonthYear());
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("targetPeriodFlag");
			ob.setDataName("M");
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("kpi_id");
			ob.setDataName(id);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("kpi_target");
			ob.setDataName(targetArray[index]);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("firstWeekReport");
			ob.setDataName(firstWeek);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("secondWeekReport");
			ob.setDataName(secondWeek);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("thirdWeekReport");
			ob.setDataName(thirdWeek);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("fourthWeekReport");
			ob.setDataName(fourthWeek);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("mode");
			ob.setDataName((type == DSRType.ONLINE) ? "ONLINE" : "SMS");
			insertData.add(ob);
			
			if(dsrMode == DSRMode.OFFERING)
			{
				cbt.insertIntoTable("offeringdailyreport",insertData,connectionSpace);
			}
			else
			{
				cbt.insertIntoTable("dailyreport",insertData,connectionSpace);
			}
	 	}
		
	}
	private Map<Integer, Object> getDateMap(Map<String, Object> hashMap, HttpServletRequest request)
	{
		Map<Integer, Object> map = new TreeMap<Integer, Object>();
		for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
			
			if(entry.getKey().equalsIgnoreCase("one"))
			{
				map.put(1, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("two"))
			{
				map.put(2, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("three"))
			{
				map.put(3, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("fourth"))
			{
				map.put(4, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("fifth"))
			{
				map.put(5, request.getParameter(entry.getKey()));
			}else if(entry.getKey().equalsIgnoreCase("six"))
			{
				map.put(6, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("seven"))
			{
				map.put(7, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("eight"))
			{
				map.put(8, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("nine"))
			{
				map.put(9, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("ten"))
			{
				map.put(10, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("eleven"))
			{
				map.put(11, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twelve"))
			{
				map.put(12, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("thirteen"))
			{
				map.put(13, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("fourteen"))
			{
				map.put(14, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("fifteen"))
			{
				map.put(15, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("sixteen"))
			{
				map.put(16, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("seventeen"))
			{
				map.put(17, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("eighteen"))
			{
				map.put(18, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("nineteen"))
			{
				map.put(19, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twenty"))
			{
				map.put(20, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentyone"))
			{
				map.put(21, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentytwo"))
			{
				map.put(22, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentythree"))
			{
				map.put(23, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentyfourth"))
			{
				map.put(24, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentyfifth"))
			{
				map.put(25, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentysix"))
			{
				map.put(26, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentyseven"))
			{
				map.put(27, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentyeight"))
			{
				map.put(28, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("twentynine"))
			{
				map.put(29, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("thirty"))
			{
				map.put(30, request.getParameter(entry.getKey()));
			}
			else if(entry.getKey().equalsIgnoreCase("thirtyone"))
			{
				map.put(31, request.getParameter(entry.getKey()));
			}
		}
		return map;
	}
	public String getCurrentMonthYear()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		month = month+1;
		String Month = String.valueOf(month);
		if(month < 10)
			Month = "0"+month; 
		String date = Month+"-"+year;
		System.out.println("date:"+date);//date in format dd-yyyy i.e 04-2013
		return date;
	}
}