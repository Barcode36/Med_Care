package com.Over2Cloud.ctrl.leaveManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ExcelUpload extends ActionSupport{
	
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private File excel;
    private String excelContentType;
    private String excelFileName;
    private Integer rows=0;
    private Integer total=0;
    private Integer records=0;
    private Integer page=0;
    private String selectedId=null;
    public Map<String,Object> columnNameMap=null;
    public String upload4;
    public List<Object> gridDataList;
    List<AttendancePojo> excelData =null;
    private List<AttendancePojo> gridModelList;
    
    @SuppressWarnings("unchecked")
	public String readExcelData()
    {
    	System.out.println(">>>>>>>>>>>>>");
    	String returnResult = ERROR;
		try 
		{
			if (excel != null) 
			{
				columnNameMap=new LinkedHashMap<String, Object>();
				Map<String, String> columnMap=new LinkedHashMap<String, String>();
				List<String> columnKeyList=new ArrayList<String>();
				Map<String, Object> dataKeyMap=new LinkedHashMap<String, Object>();
				List<String> gridListData  = new ArrayList<String>();
				InputStream inputStream = new FileInputStream(excel);
				Map<String, String> tempMap=new LinkedHashMap<String, String>();
				
				if(upload4.equals("holidayList"))
				{
					if(new HolidayList().getColumnName("mapped_leave_request_configuration","holiday_list_configuration","holidayList")!=null)
					{
						returnResult=SUCCESS;
						columnMap=(Map<String, String>) session.get("columnMap");
					}
				}
				if (upload4.equals("attendanceMark")) 
				{
					if(new HolidayList().getColumnName("mapped_time_slot_configuration","attendance_record_configuration","attendanceMark")!=null)
					{
						returnResult="attendanceMark";
						columnMap=(Map<String, String>) session.get("columnMap");
					}
				}
				if (upload4.equalsIgnoreCase("holidayList")) 
				{
					try
					{
						if(excelFileName.contains(".xlsx"))
						{
							System.out.println("inside fhjh");
							GenericReadExcel7 GRE7 = new GenericReadExcel7();
							XSSFSheet excelSheet = GRE7.readExcel(inputStream);
							for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								XSSFRow row = excelSheet.getRow(rowIndex);
						        int cellNo = row.getLastCellNum();
						         if (cellNo > 0) 
						         {
						        	 if(rowIndex==2)
						        	 {
						        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
						        		 {
				        					    Iterator<Entry<String, String>> hiterator=columnMap.entrySet().iterator();
				        					    while (hiterator.hasNext()) 
				        					    {
				        					    	Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				        					    	if(GRE7.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim()))
				        					    	{
				        							/** 
								 					 *header field matches from header Put Into List and Map Object 
								 					 *
								 					 */	   
				        					    		if(paramPair.getKey().toString().equals("deptName") || paramPair.getKey().toString().equals("subdeptname") || paramPair.getKey().toString().equals("assetid") || paramPair.getKey().toString().equals("vendorname"))
				        					    		{
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		columnKeyList.add(paramPair.getKey());
				        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
				        							}
				        						}
						        		 }
					        				 setColumnNameMap(dataKeyMap);
					        			 }
						        	}
						        	 
						         }
							}
						else
						{
							GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
					    	HSSFSheet excelSheet = GRBE.readExcel(inputStream);
							for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								HSSFRow row = excelSheet.getRow(rowIndex);
						        int cellNo = row.getLastCellNum();
						        if (cellNo > 0) 
						        {
						    		if(rowIndex==2)
						    		{
						        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
						        		 {
				        					    Iterator<Entry<String, String>> hiterator=columnMap.entrySet().iterator();
				        					    while (hiterator.hasNext()) 
				        					    {
				        					    	Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				        					    	if(GRBE.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim()))
				        					    	{
				        							/** 
								 					 *header field matches from header Put Into List and Map Object 
								 					 *
								 					 */	   
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		
				        					    		columnKeyList.add(paramPair.getKey());
				        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
				        							}
				        						}
						        		 }
						        		
						        		 setColumnNameMap(dataKeyMap);
						        	}
						    		else
						    		{
						        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
						        		 {
			        			             if(GRBE.readExcelSheet(row, cellIndex)!=null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
			        			             {
			        			            	
			        			            	 gridListData.add(GRBE.readExcelSheet(row, cellIndex).toString()); 
			        			             }  
			        			             else
			        			             {
			        			            	gridListData.add("NA");
			        			             }
						        		 }
						    		}
						        }
							}
						}
						if (gridListData!=null && gridListData.size() > 0) 
						{
							session.put("gridListData", gridListData);
							session.put("columnKeyList", columnKeyList);
					    }
						if(session.containsKey("columnMap"))
						{
							session.remove("columnMap");
						}
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				if (upload4.equalsIgnoreCase("attendanceMark")) 
				{
				try {
					if (excelFileName.contains(".xlsx")) 
					{
						   GenericReadExcel7 GRE7 = new GenericReadExcel7();
						   XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						   excelData = new ArrayList<AttendancePojo>();
						   for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						    {
						      XSSFRow row = excelSheet.getRow(rowIndex);
						    
								 int cellNo = row.getLastCellNum();
							     if (cellNo>0)
							      {
							    	AttendancePojo FDP = new AttendancePojo();
							        FDP.setId(rowIndex);
								    for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
								     {
								    	 switch (cellIndex) 
								    	 {
								              case 0:
						            	          FDP.setEmpname(GRE7.readExcelSheet(row, cellIndex));
   								          break;
								    
								              case 1:
						            	          FDP.setDate(DateUtil.convertDateToIndianFormat(GRE7.readExcelSheet(row, cellIndex)));
   								          break;
								    
								              case 2:
								            	  FDP.setIntime(DateUtil.convertTimeInMinuteAndSecond(GRE7.readExcelSheet(row, cellIndex)));
		    								  break;
	    								      
		                                      case 3:
		                                    	  FDP.setOuttime(DateUtil.convertTimeInMinuteAndSecond(GRE7.readExcelSheet(row, cellIndex)));
		    								  break;
	
		                                      case 4:
		                                    	  FDP.setStatus(GRE7.readExcelSheet(row, cellIndex));	
			                                  break;
	
		                                      case 5:
		                                    	  FDP.setAttendance(GRE7.readExcelSheet(row, cellIndex));
		    	                              break;
		    	                              
		                                      case 6:
		                                    	  FDP.setExtraworking(GRE7.readExcelSheet(row, cellIndex));
		    	                              break;
		    	                              
		                                      case 7:
		                                    	  FDP.setClientvisit(GRE7.readExcelSheet(row, cellIndex));
		    	                              break;
		    	                              
		                                      case 8:
		                                    	  FDP.setComment(GRE7.readExcelSheet(row, cellIndex));
		    	                              break;
		    	                              
		                                      case 9:
		                                    	  FDP.setLupdate(GRE7.readExcelSheet(row, cellIndex));
		    	                              break;
		    	                              
		                                      case 10:
		                                    	  FDP.setLupdateto(GRE7.readExcelSheet(row, cellIndex));
		    	                              break;
								          }
								    	
								       }
								  excelData.add(FDP);
							   }
						  
						    }
						   }
				     }
				       catch (Exception e) 
				       {
						e.printStackTrace();
					}	 
				       if(excelData!=null && excelData.size()>0){
				       session.put("attendanceList", excelData);
				       }
				}
			 }
		}				
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;	
    }
    @SuppressWarnings("unchecked")
	public String showUpload()
	{
		String returnResult= ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				 String userName = (String)session.get("uName");
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}	
				else {
					excelData = (List<AttendancePojo>)session.get("attendanceList");
					if (excelData !=null && excelData.size()>0) {
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
						setGridModelList(excelData.subList(from, to));
	
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
					returnResult =SUCCESS;
				}
			} catch (Exception e) {
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}
    @SuppressWarnings("unchecked")
	public String viewConfirmationData()
    {
		String returnResult = ERROR;
		try
		{
			List<String> gridListData = (List<String>) session.get("gridListData");
			
			List<String> columnKeyList = (List<String>) session.get("columnKeyList");
			List<Object> tempList=new ArrayList<Object>();
			if (gridListData != null && gridListData.size() > 0) 
			{
				setRecords(gridListData.size());
				int to = (getRows() * getPage());
				if (to > getRecords())
					to = getRecords();
				if(gridListData.size()>0)
				{
						gridDataList=new ArrayList<Object>();
						int i=0;
						Map<String, Object> one=new HashMap<String, Object>();
						String [] fieldsArray = columnKeyList.toArray(new String[columnKeyList.size()]);
						String [] fieldsArrayss = gridListData.toArray(new String[gridListData.size()]);
						if(fieldsArrayss!=null && fieldsArray!=null)
						{
							for (int k = 0; k < fieldsArrayss.length; k++) 
							{
								one.put(fieldsArray[i].toString().trim(), fieldsArrayss[k].toString());
								i++;
								if(i==fieldsArray.length)
								{
									i=0;
									tempList.add(one);
									one=new HashMap<String, Object>();
								}
							}
						}
						setGridDataList(tempList);
				}
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			}
			returnResult = SUCCESS;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return returnResult;
	}
    @SuppressWarnings("unchecked")
	public String saveSelectData()
    {
    	System.out.println("saveSelectData :::::: ");
    	String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=null;
				if(upload4!=null && !upload4.equals("") && upload4.equals("holidayList"))
				{
					statusColName=Configuration.getConfigurationData("mapped_leave_request_configuration",accountID,connectionSpace,"",0,"table_name","holiday_list_configuration");
				}
			
				boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
				if(statusColName!=null&&statusColName.size()>0)
				{
					//create table query based on configuration
					List <TableColumes> tableColume=new ArrayList<TableColumes>();
					
					for(GridDataPropertyView gdp:statusColName)
					{
						 TableColumes ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 tableColume.add(ob1);
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
						
					}
					if(upload4!=null && !upload4.equals("") && upload4.equals("holidayList"))
					{
						 boolean status1=cbt.createTable22("holiday_list",tableColume,connectionSpace);
						 /*if(status1)
						 {
							 new createTable().altTabSetUnique("holiday_list","id",connectionSpace);
						 }*/
					}
					 
				}
				
				List<String> gridListData = (List<String>) session.get("gridListData");
				List<String> columnKeyList = (List<String>) session.get("columnKeyList");
				
				ArrayList<InsertDataTable> insertData=null;
				InsertDataTable ob=null;
				session.remove("gridListData");
				session.remove("columnKeyList");
			
				int tempIndex=0;
				for(int index=0;index<gridListData.size()/columnKeyList.size();index++)
				{
					insertData=new ArrayList<InsertDataTable>();
					for(int index4Col=0;index4Col<columnKeyList.size();index4Col++)
					{
					 if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" &&  upload4.equals("holidayList") )
						{
						 if(columnKeyList.get(index4Col).toString().equals("fdate") || columnKeyList.get(index4Col).toString().equals("tdate") )
						 { 
							    ob=new InsertDataTable();
								ob.setColName(columnKeyList.get(index4Col).toString());
								if(gridListData.get(tempIndex).toString().matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
								{
									ob.setDataName(DateUtil.convertHyphenDateToUSFormat(gridListData.get(tempIndex).toString()));
									
								}
								//for Date Check With (DD/MM/YYYY OR D/M/YY)
								else if(gridListData.get(tempIndex).toString().matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
								{
									ob.setDataName(DateUtil.convertSlashDateToUSFormat(gridListData.get(tempIndex).toString()));
								}
								else
								{
									ob.setDataName(DateUtil.convertDateToUSFormat(gridListData.get(tempIndex).toString()));
									System.out.println("columnKggggggeyList" +columnKeyList.get(index4Col).toString());
									System.out.println("columnKbnbnbeyList" +DateUtil.makeTitle(gridListData.get(tempIndex).toString()));
								}
								insertData.add(ob);	
						
						 }
						 else
						 {
							ob=new InsertDataTable();
							ob.setColName(columnKeyList.get(index4Col).toString());
							ob.setDataName(DateUtil.makeTitle(gridListData.get(tempIndex).toString()));
							insertData.add(ob);
							System.out.println("columnKeyList" +columnKeyList.get(index4Col).toString());
							System.out.println("columnKeyList" +DateUtil.makeTitle(gridListData.get(tempIndex).toString()));
						 }
						}
				   
						tempIndex++;
					}
						if(userTrue)
						{
							ob=new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(DateUtil.makeTitle(userName));
							insertData.add(ob);
						}
						if(dateTrue)
						{
							ob=new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateIndianFormat());
							insertData.add(ob);
						}
						if(timeTrue)
						{
							ob=new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData.add(ob);
						}
				
					if(upload4!=null && !upload4.equals("") && upload4.equals("holidayList"))
					{
						new createTable().insertIntoTable("holiday_list",insertData,connectionSpace);
					}
				}
				addActionMessage("Upload Data Successfully Registered!!!");
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				returnResult=ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String saveAttendanceData()
	{
		String returnResult = ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				  String day=null;
				  String intime=null;
				  String outtime=null;
				  String totalTime=null;
				  String finalIncommin=null;
				  String finalOutgoing=null;
				  String workingFlag=null;
				  String userName = (String)session.get("uName");
				  SessionFactory connectionSpace =(SessionFactory) session.get("connectionSpace");
				  CommonOperInterface cot = new CommonConFactory().createInterface();
				  LeaveHelper LH=new LeaveHelper();
				if (userName == null || userName.equals("")) 
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else 
				 {
				/*	List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_time_slot_configuration",accountID,connectionSpace,"",0,"table_name","attendance_record_configuration");
					if(statusColName!=null&&statusColName.size()>0)
					{
						List <TableColumes> tableColume=new ArrayList<TableColumes>();
						
						for(GridDataPropertyView gdp:statusColName)
						{
							if(!gdp.getColomnName().equalsIgnoreCase("fdate") && !gdp.getColomnName().equalsIgnoreCase("tdate") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
							{
							 TableColumes ob1=new TableColumes();
					         ob1=new TableColumes();
							 ob1.setColumnname(gdp.getColomnName());
							 ob1.setDatatype("varchar(255)");
							 ob1.setConstraint("default NULL");
							 tableColume.add(ob1);
							 
							}
						}
						 cot.createTable22("attendence_record",tableColume,connectionSpace);
					}*/
					excelData = (List<AttendancePojo>) session.get("attendanceList");
					session.remove("attendanceList");
					 
					 
					InsertDataTable ob=null;
				for (Iterator<AttendancePojo> empItr = excelData.iterator(); empItr.hasNext();) 
					{
						    AttendancePojo FDP = empItr.next();
						    String loggedDept[]=LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
							 String empIDS[]=LH.getEmpId1(FDP.getEmpname(), connectionSpace);
							 String contactID=LH.getContactIdForExcel("HR", loggedDept[4], empIDS[0], empIDS[1], connectionSpace);
							 System.out.println("contactID :::::" +contactID);
						  
						    day = DateUtil.findDayFromDate((FDP.getDate()));
						    System.out.println(" day :::FIND:;; " +day);
							
							if(day!=null)
							{
								if(day.equalsIgnoreCase("Sunday")&& FDP.getIntime().equalsIgnoreCase("00:00")&& FDP.getOuttime().equalsIgnoreCase("00:00"))
								{
									workingFlag="Holiday";
								}
								else
								{
									workingFlag="Working";
								}
							}
							if(FDP.getIntime().equalsIgnoreCase("00:00")&& FDP.getOuttime().equalsIgnoreCase("00:00"))
							{
								totalTime="NA";
							}else
							{
								totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
								System.out.println("totalTime  ::::  " +totalTime);
							}
							String query="select ftime,ttime from time_slot where empname='"+contactID+"'";
							System.out.println("queryTTTTTTT  :::" +query);
							List timming=cot.executeAllSelectQuery(query, connectionSpace);
							if(timming!=null && timming.size()>0)
							{
								Object[] object=null;
								for (Iterator iterator = timming.iterator(); iterator.hasNext();)
								{
									object = (Object[]) iterator.next();
									intime=object[0].toString();
									outtime=object[1].toString();
								}
							}
							if(FDP.getIntime().equalsIgnoreCase("00:00")&& FDP.getOuttime().equalsIgnoreCase("00:00") && FDP.getClientvisit().equalsIgnoreCase("Full Day"))
							{
						        finalIncommin="CV";
						        finalOutgoing="CV";
						        totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
							}
							else if(FDP.getIntime().equalsIgnoreCase("00:00")&& FDP.getClientvisit().equalsIgnoreCase("Half day"))
							{
									String outgoingStatus=new AttendenceManagement().getIncommingStatus(FDP.getOuttime(),outtime);
									finalOutgoing=new AttendenceManagement().getFinalIncommingstatus(outgoingStatus); 
									finalIncommin="CV";
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
									
							}else if(FDP.getOuttime().equalsIgnoreCase("00:00")&& FDP.getClientvisit().equalsIgnoreCase("Half day"))
							{
									 String incomingStatus=new AttendenceManagement().getIncommingStatus( FDP.getIntime(), intime);
									 finalIncommin=new AttendenceManagement().getFinalIncommingstatus(incomingStatus); 
									finalOutgoing="CV";
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
									
							}else if(FDP.getIntime().equalsIgnoreCase("00:00")&& FDP.getClientvisit().equalsIgnoreCase("Full day"))
							{
									String outgoingStatus=new AttendenceManagement().getIncommingStatus(FDP.getOuttime(),outtime);
									finalOutgoing=new AttendenceManagement().getFinalIncommingstatus(outgoingStatus); 
									finalIncommin="CV";
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
									
						   }else if(FDP.getOuttime().equalsIgnoreCase("00:00")&& FDP.getClientvisit().equalsIgnoreCase("Full day")){
									 String incomingStatus=new AttendenceManagement().getIncommingStatus( FDP.getIntime(), intime);
									 finalIncommin=new AttendenceManagement().getFinalIncommingstatus(incomingStatus); 
									finalOutgoing="CV";
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
									
								}else if(FDP.getIntime().equalsIgnoreCase(intime) && FDP.getOuttime().equalsIgnoreCase(outtime)){
									finalIncommin="On Time";
									finalOutgoing="On Time";
									totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
								}else if(FDP.getIntime().equalsIgnoreCase("00:00") && FDP.getOuttime().equalsIgnoreCase("00:00")&& day.equalsIgnoreCase("Sunday")){
									workingFlag="Holiday";
									finalIncommin="Holiday";
									finalOutgoing="Holiday";
								}
								else if(FDP.getIntime().equalsIgnoreCase("00:00") && FDP.getOuttime().equalsIgnoreCase("00:00")&& FDP.getStatus().equalsIgnoreCase("Holiday")){
									workingFlag="Holiday";
									finalIncommin="Holiday";
									finalOutgoing="Holiday";
								}
					           else{
								if(FDP.getIntime().equalsIgnoreCase("00:00")){
									finalIncommin="ABSENT";
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
								 }
								else{
									 if(FDP.getIntime()!=null && intime!=null){
									 String incomingStatus=new AttendenceManagement().getIncommingStatus( FDP.getIntime(), intime);
									 if(incomingStatus.equalsIgnoreCase("0:0")){
										 finalIncommin="On Time"; 
										 
									 }else{
									 finalIncommin=new AttendenceManagement().getFinalIncommingstatus(incomingStatus); 
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
									 }
									 }
								 }
								if(FDP.getOuttime().equalsIgnoreCase("00:00")){
									finalOutgoing="ABSENT";
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
								}
								else{
									if(FDP.getOuttime()!=null && outtime!=null){
									String outgoingStatus=new AttendenceManagement().getIncommingStatus(FDP.getOuttime(),outtime);
									if(outgoingStatus.equalsIgnoreCase("0:0")){
										
										finalOutgoing="On Time";
									}else{
									finalOutgoing=new AttendenceManagement().getFinalIncommingstatus(outgoingStatus);
									 totalTime=DateUtil.findDifferenceTwoTime(FDP.getIntime(),FDP.getOuttime());
									 }
								   }
								 }
							   }
							
							  // Getting contact id from empId  *****
							
							   List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							
							         ob=new InsertDataTable();
							         ob.setColName("date1");
									 ob.setDataName(DateUtil.convertDateToUSFormat(FDP.getDate()));
									 insertData.add(ob);
									 System.out.println("insertData date1     " + insertData);
									 
							         if(day!=null)
							         {
									 ob=new InsertDataTable();
									 ob.setColName("day");
									 ob.setDataName(day);
									 insertData.add(ob);
									 System.out.println("insertData     " + insertData);
								      }
								
									 ob=new InsertDataTable();
									 ob.setColName("working");
									 ob.setDataName(workingFlag);
									 insertData.add(ob);
									
									 ob=new InsertDataTable();
									 ob.setColName("timeslot");
									 System.out.println(" insertData for timeslot" +ob.getDataName());
									 ob.setDataName(intime+" to "+outtime);
									 insertData.add(ob);
									 System.out.println(" insertData for timeslot" + insertData);
									
									 ob=new InsertDataTable();
							         ob.setColName("in_time");
									 ob.setDataName(FDP.getIntime());
									 insertData.add(ob);
									   
									 ob=new InsertDataTable();
							         ob.setColName("out_time");
									 ob.setDataName(FDP.getOuttime());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("beforetime");
									 ob.setDataName(finalIncommin);
									 insertData.add(ob);
									 System.out.println(" insertData for beforetime" + insertData);
									
									 ob=new InsertDataTable();
									 ob.setColName("aftertime");
									 ob.setDataName(finalOutgoing);
									 insertData.add(ob);
									 System.out.println(" insertData for aftertime" + insertData);
									
									 ob=new InsertDataTable();
									 ob.setColName("totalhour");
									 ob.setDataName(totalTime);
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("comment");
									 ob.setDataName(FDP.getComment());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("empname");
									 System.out.println("getColName   " + ob.getColName());
									 ob.setDataName(contactID);
									 System.out.println("getDataName    " + ob.getDataName());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("status");
									 ob.setDataName(FDP.getStatus());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("attendence");
									 ob.setDataName(FDP.getAttendance());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("eworking");
									 ob.setDataName(FDP.getExtraworking());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("clientVisit");
									 ob.setDataName(FDP.getClientvisit());
									 insertData.add(ob);
									 
									 /*ob=new InsertDataTable();
									 ob.setColName("totalhour");
									 ob.setDataName(totalTime);
									 insertData.add(ob);*/
									 
									 ob=new InsertDataTable();
									 ob.setColName("lupdate");
									 ob.setDataName(FDP.getLupdate());
									 insertData.add(ob);
									 
									 ob=new InsertDataTable();
									 ob.setColName("lupdateto");
									 ob.setDataName(FDP.getLupdateto());
									 insertData.add(ob);
								boolean status= cot.insertIntoTable("attendence_record",insertData,connectionSpace);
									
					}
				 }
					if(true)
						addActionMessage("Excel Uploaded Successfully.");
					else
						addActionMessage("!!!May be Some Problem.");
					returnResult = SUCCESS;
				}
	
			 catch (Exception e) {
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String changeExcelDateFormat(String date)
	{
		String newDate = null;

		// for Date Check With (YYYY-DD-MM OR YY-D-M)
		if (date.matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
		{
			newDate = DateUtil.convertHyphenDateToUSFormat(date);
		}
		// for Date Check With (DD/MM/YYYY OR D/M/YY)
		else if (date.matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
		{
			newDate = DateUtil.convertSlashDateToUSFormat(date);
		}
		return newDate;
	}
	
	
	
	
	public File getExcel() {
		return excel;
	}
	public void setExcel(File excel) {
		this.excel = excel;
	}
	public String getExcelContentType() {
		return excelContentType;
	}
	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public Map<String, Object> getColumnNameMap() {
		return columnNameMap;
	}

	public void setColumnNameMap(Map<String, Object> columnNameMap) {
		this.columnNameMap = columnNameMap;
	}

	public String getUpload4() {
		return upload4;
	}

	public void setUpload4(String upload4) {
		this.upload4 = upload4;
	}


	public List<Object> getGridDataList() {
		return gridDataList;
	}


	public void setGridDataList(List<Object> gridDataList) {
		this.gridDataList = gridDataList;
	}
	public List<AttendancePojo> getGridModelList() {
		return gridModelList;
	}
	public void setGridModelList(List<AttendancePojo> gridModelList) {
		this.gridModelList = gridModelList;
	}
	
}
