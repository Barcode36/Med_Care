package com.Over2Cloud.ctrl.helpdesk.roasterconf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.RoasterConfPojo;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;

@SuppressWarnings("serial")
public class RoasterexcelUpload extends GridPropertyBean{
	
	private File empExcel;
	private String empExcelContentType;
	private String empExcelFileName;
	private String selectedId;
	private String subdept;
	private String dept_subDept;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	
	private String dept;
	List<RoasterConfPojo> excelData =null;
	private List<RoasterConfPojo> gridEmpExcelModel;
	
	private String viewType;
	private String dataFor;
	
	
	
	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getDataFor() {
		return dataFor;
	}

	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public String uploadExcel()throws Exception
	{
		String returnResult = ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				 String contactId = "",dept_subDept_Id="";
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}	
			    else {
			    	  HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
					   if(empExcel !=null)
					     {
						   InputStream inputStream = new FileInputStream(empExcel);
					       if(empExcelFileName.contains(".xlsx")){
						   GenericReadExcel7 GRE7 = new GenericReadExcel7();
						   XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						   excelData = new ArrayList<RoasterConfPojo>();
						   for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						    {
						      XSSFRow row = excelSheet.getRow(rowIndex);
						      if (viewType!=null && !viewType.equals("") && viewType.equalsIgnoreCase("SD")) {
						    	  dept_subDept_Id=subdept;
									contactId = getContactId(GRE7.readExcelSheet(row, 0), dept_subDept_Id, dataFor);
								}
						    	else if (viewType!=null && !viewType.equals("") && viewType.equalsIgnoreCase("D")) {
						    		 dept_subDept_Id=dept;
						    		contactId = getContactId(GRE7.readExcelSheet(row, 0), dept_subDept_Id, dataFor);
								}
						      if (contactId!=null && !contactId.equals("") && !contactId.equals("")) 
						      {
						       boolean  status1=HUH.isExist("roaster_conf","contactId", contactId,connectionSpace);
						       if (status1) {
							     int cellNo = row.getLastCellNum();
							     if (cellNo>0)
							      {
								    RoasterConfPojo RCP = new RoasterConfPojo();
								    for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
								     {
									    switch (cellIndex) {
									              case 0:
									            	  RCP.setEmpId(contactId);
		    								      break;
		    								      
									              case 1:
									            	  RCP.setEmpName(GRE7.readExcelSheet(row, cellIndex));
				    							  break;
									             
									              case 2:
									            	  RCP.setFirst(GRE7.readExcelSheet(row, cellIndex));
			    								  break;
		
			                                      case 3:
			                                    	  RCP.setSecond(GRE7.readExcelSheet(row, cellIndex));	
				                                  break;
		
			                                      case 4:
			                                    	  RCP.setThree(GRE7.readExcelSheet(row, cellIndex));
			    	                              break;
			    	                              
			                                      case 5:
			                                    	  RCP.setFour(GRE7.readExcelSheet(row, cellIndex));
			        	                          break;
		
			                                      case 6:
			                                    	  RCP.setFifth(GRE7.readExcelSheet(row, cellIndex));
			    	                              break;
			    	                              
			                                      case 7:
			                                    	  RCP.setSix(GRE7.readExcelSheet(row, cellIndex));
			        	                          break;
		
			                                      case 8:
			                                    	  RCP.setSeven(GRE7.readExcelSheet(row, cellIndex));
			    	                              break;
			    	                              
			                                      case 9:
			                                    	  RCP.setEight(GRE7.readExcelSheet(row, cellIndex));
			        	                          break;
			        	                              
			                                      case 10:
			                                    	  RCP.setNine(GRE7.readExcelSheet(row, cellIndex));
			        	                          break;
			        	                          
			                                      case 11:
			                                    	  RCP.setTen(GRE7.readExcelSheet(row, cellIndex));
			            	                      break;
		                          
			                                      case 12:
			                                    	  RCP.setEleven(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 13:
			                                    	  RCP.setTwelve(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 14:
			                                    	  RCP.setThirteen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 15:
			                                    	  RCP.setFourteen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 16:
			                                    	  RCP.setFifteen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 17:
			                                    	  RCP.setSixteen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 18:
			                                    	  RCP.setSeventeen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 19:
			                                    	  RCP.setEighteen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 20:
			                                    	  RCP.setNineteen(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 21:
			                                    	  RCP.setTwenty(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 22:
			                                    	  RCP.setTwenty_one(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 23:
			                                    	  RCP.setTwenty_two(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 24:
			                                    	  RCP.setTwenty_three(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 25:
			                                    	  RCP.setTwenty_four(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 26:
			                                    	  RCP.setTwenty_five(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 27:
			                                    	  RCP.setTwenty_six(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 28:
			                                    	  RCP.setTwenty_seven(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 29:
			                                    	  RCP.setTwenty_eight(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 30:
			                                    	  RCP.setTwenty_nine(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 31:
			                                    	  RCP.setThirty(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 32:
			                                    	  RCP.setThirty_one(GRE7.readExcelSheet(row, cellIndex));
				            	                  break;
									      }
								       }
								  excelData.add(RCP);
							   }
						     }
						    }
						    }
					       }
					       else  if(empExcelFileName.contains(".xls"))
					            {
					    	   GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						           HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						           excelData = new ArrayList<RoasterConfPojo>();
						           for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++) {
						        	   HSSFRow row = excelSheet.getRow(rowIndex);
						        	   if (viewType!=null && !viewType.equals("") && viewType.equalsIgnoreCase("SD")) {
						        		   dept_subDept_Id=subdept;
											contactId = getContactId(GRBE.readExcelSheet(row, 0), dept_subDept_Id, dataFor);
										}
								    	else if (viewType!=null && !viewType.equals("") && viewType.equalsIgnoreCase("D")) {
								    		dept_subDept_Id=dept;
								    		contactId = getContactId(GRBE.readExcelSheet(row, 0), dept_subDept_Id, dataFor);
										}
								      if (contactId!=null && !contactId.equals("") && !contactId.equals("")) 
								      {
						        	   boolean  status1=HUH.isExist("roaster_Conf","contactId", contactId,connectionSpace);
						        	   if (status1) {
							           int cellNo = row.getLastCellNum();
							           if (cellNo>0)
							            {
								          RoasterConfPojo RCP = new RoasterConfPojo();
								          for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
								            {
									          switch (cellIndex) {
									              case 0:
									            	  RCP.setEmpId(contactId);
		    								      break;
									              
									              case 1:
									            	  RCP.setEmpName(GRBE.readExcelSheet(row, cellIndex));
			    								  break;
		    								      
			                                      case 2:
			                                    	  RCP.setFirst(GRBE.readExcelSheet(row, cellIndex));
			    								  break;
		
			                                      case 3:
			                                    	  RCP.setSecond(GRBE.readExcelSheet(row, cellIndex));	
				                                  break;
		
			                                      case 4:
			                                    	  RCP.setThree(GRBE.readExcelSheet(row, cellIndex));
			    	                              break;
			    	                              
			                                      case 5:
			                                    	  RCP.setFour(GRBE.readExcelSheet(row, cellIndex));
			        	                          break;
		
			                                      case 6:
			                                    	  RCP.setFifth(GRBE.readExcelSheet(row, cellIndex));
			    	                              break;
			    	                              
			                                      case 7:
			                                    	  RCP.setSix(GRBE.readExcelSheet(row, cellIndex));
			        	                          break;
		
			                                      case 8:
			                                    	  RCP.setSeven(GRBE.readExcelSheet(row, cellIndex));
			    	                              break;
			    	                              
			                                      case 9:
			                                    	  RCP.setEight(GRBE.readExcelSheet(row, cellIndex));
			        	                          break;
			        	                              
			                                      case 10:
			                                    	  RCP.setNine(GRBE.readExcelSheet(row, cellIndex));
			        	                          break;
			        	                          
			                                      case 11:
			                                    	  RCP.setTen(GRBE.readExcelSheet(row, cellIndex));
			            	                      break;
		                          
			                                      case 12:
			                                    	  RCP.setEleven(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 13:
			                                    	  RCP.setTwelve(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 14:
			                                    	  RCP.setThirteen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 15:
			                                    	  RCP.setFourteen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 16:
			                                    	  RCP.setFifteen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 17:
			                                    	  RCP.setSixteen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 18:
			                                    	  RCP.setSeventeen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 19:
			                                    	  RCP.setEighteen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 20:
			                                    	  RCP.setNineteen(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 21:
			                                    	  RCP.setTwenty(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 22:
			                                    	  RCP.setTwenty_one(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 23:
			                                    	  RCP.setTwenty_two(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 24:
			                                    	  RCP.setTwenty_three(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 25:
			                                    	  RCP.setTwenty_four(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 26:
			                                    	  RCP.setTwenty_five(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 27:
			                                    	  RCP.setTwenty_six(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 28:
			                                    	  RCP.setTwenty_seven(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 29:
			                                    	  RCP.setTwenty_eight(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 30:
			                                    	  RCP.setTwenty_nine(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 31:
			                                    	  RCP.setThirty(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
			                          
			                                      case 32:
			                                    	  RCP.setThirty_one(GRBE.readExcelSheet(row, cellIndex));
				            	                  break;
									         }
								         }
								     excelData.add(RCP);
							       }
						        }
						     }
						}
						   
					    }
						if (excelData.size()>0) {
							session.put("roasterList", excelData);
							session.put("dept_subDept", dept_subDept_Id);
						}
					 }
					  returnResult =SUCCESS;    
				   }
			} catch (Exception e) {
				e.printStackTrace();
		   }
		}
		return returnResult;
	}
	
	public String getContactId(String empId, String dept_subdept, String module)
	{
		String contactId="NA";
		List list=null;
        StringBuilder sb  = new StringBuilder();
        sb.append("select contacts.id from compliance_contacts as contacts");
		sb.append(" inner join employee_basic as emp on contacts.emp_id =emp.id");
		sb.append(" where contacts.moduleName='"+module+"' and contacts.forDept_id='"+dept_subdept+"' and emp.empId='"+empId+"' ");
			Session session = null;  
			Transaction transaction = null;  
			try 
			 {  
				session=connectionSpace.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(sb.toString()).list();  
				transaction.commit(); 
			 }
			catch (Exception ex)
				{transaction.rollback();} 
			if (list!=null && list.size()>0) {
				contactId=list.get(0).toString();
			}
		return contactId;
	}
	
	@SuppressWarnings("unchecked")
	public String showUploadRoaster()
	{
		String returnResult= ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}	
				else {
					excelData = (List<RoasterConfPojo>)session.get("roasterList");
					if (excelData !=null && excelData.size()>0) {
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
						setGridEmpExcelModel(excelData.subList(from, to));
						// calculate the total pages for the query
	
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
	public String saveExcelData()
	{
		String returnResult = ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else 
				 {
					excelData = (List<RoasterConfPojo>) session.get("roasterList");
					session.remove("roasterList");
					
					String dept_subdept_id = session.get("dept_subDept").toString();
					session.remove("dept_subDept");
					
					Map<String, Object> whereClause = new HashMap<String, Object>();
					Map<String, Object> setValues = new HashMap<String, Object>();
					for (Iterator<RoasterConfPojo> empItr = excelData.iterator(); empItr.hasNext();) {
						RoasterConfPojo EM = empItr.next();
							whereClause.put("contactId", EM.getEmpId());
							setValues.put("empName", EM.getEmpName());
							setValues.put("dept_subdept", dept_subdept_id);
							setValues.put("status", "Active");
							setValues.put("attendance", "Present");
							
							setValues.put("01_date", EM.getFirst());
							setValues.put("02_date", EM.getSecond());
							setValues.put("03_date", EM.getThree());
							setValues.put("04_date", EM.getFour());
							setValues.put("05_date", EM.getFifth());
							setValues.put("06_date", EM.getSix());
							setValues.put("07_date", EM.getSeven());
							setValues.put("08_date", EM.getEight());
							setValues.put("09_date", EM.getNine());
							setValues.put("10_date", EM.getTen());
							setValues.put("11_date", EM.getEleven());
							setValues.put("12_date", EM.getTwelve());
							setValues.put("13_date", EM.getThirteen());
							setValues.put("14_date", EM.getFourteen());
							setValues.put("15_date", EM.getFifteen());
							setValues.put("16_date", EM.getSixteen());
							setValues.put("17_date", EM.getSeventeen());
							setValues.put("18_date", EM.getEighteen());
							setValues.put("19_date", EM.getNineteen());
							setValues.put("20_date", EM.getTwenty());
							setValues.put("21_date", EM.getTwenty_one());
							setValues.put("22_date", EM.getTwenty_two());
							setValues.put("23_date", EM.getTwenty_three());
							setValues.put("24_date", EM.getTwenty_four());
							setValues.put("25_date", EM.getTwenty_five());
							setValues.put("26_date", EM.getTwenty_six());
							setValues.put("27_date", EM.getTwenty_seven());
							setValues.put("28_date", EM.getTwenty_eight());
							setValues.put("29_date", EM.getTwenty_nine());
							setValues.put("30_date", EM.getThirty());
							setValues.put("31_date", EM.getThirty_one());
							setValues.put("date", DateUtil.getCurrentDateUSFormat());
							setValues.put("time", DateUtil.getCurrentTime());
							setValues.put("userName", userName);
						    new HelpdeskUniversalHelper().updateTableColomn("roaster_conf", setValues, whereClause, connectionSpace);
						}
					}
					if(true)
						addActionMessage("Excel Uploaded Successfully.");
					else
						addActionMessage("!!!May be Some or Full Data belongss to Other Sub Department. Otherwise Data is Saved.");
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
	
	
	public String downloadExcelPage()
	{
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getData4Download()
	{
		String returnResult= ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}	
				else {
					String id="";
					boolean flag = false;
					if (deptLevel.equals("2")) {
						if (subdept.equals("-1")) {
							flag=true;
							id=dept;
						}
						else {
							id=subdept;
						}
					}
					else if (deptLevel.equals("1")) {
						id=dept;
					}
					Map<String, Object> conditionClause = new HashMap<String, Object>();
					Map<String, Object> order = new HashMap<String, Object>();
					conditionClause.put("dept_subdept", id);
					order.put("empName", "ASC");
					
				//	List list = new HelpdeskUniversalAction().getDataFromTable("roaster_conf", null, conditionClause, order, connectionSpace);
					
					List list = new HelpdeskUniversalHelper().getRoasterList(flag, id, deptLevel, connectionSpace);
					if (list!=null && list.size()>0) {
						excelData = setRoasterData(list);
					}
					if (excelData !=null && excelData.size()>0) {
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
						setGridEmpExcelModel(excelData.subList(from, to));
						// calculate the total pages for the query
	
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
					if (excelData.size()>0) {
						session.put("roasterDownloadList", excelData);
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
	public String downloadRoaster()
	{
		String returnResult = ERROR;
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
				else 
				 {
					excelData = (List<RoasterConfPojo>) session.get("roasterDownloadList");
					session.remove("roasterDownloadList");
					
					List<RoasterConfPojo> roasterData = new ArrayList<RoasterConfPojo>();
					RoasterConfPojo RCP = null;
					for (Iterator<RoasterConfPojo> empItr = excelData.iterator(); empItr.hasNext();) {
						RoasterConfPojo EM = empItr.next();
						/*if (selectedId.contains(String.valueOf(EM.getEmpId()))) {}*/
						RCP = new RoasterConfPojo();
						RCP.setEmpName(EM.getEmpName());
						RCP.setEmpId(EM.getEmpId());
						RCP.setFirst(EM.getFirst());
						RCP.setSecond(EM.getSecond());
						RCP.setThree(EM.getThree());
						RCP.setFour(EM.getFour());
						RCP.setFifth(EM.getFifth());
						RCP.setSix(EM.getSix());
						RCP.setSeven(EM.getSeven());
						RCP.setEight(EM.getEight());
						RCP.setNine(EM.getNine());
						RCP.setTen(EM.getTen());
						RCP.setEleven(EM.getEleven());
						RCP.setTwelve(EM.getTwelve());
						RCP.setThirteen(EM.getThirteen());
						RCP.setFourteen(EM.getFourteen());
						RCP.setFifteen(EM.getFifteen());
						RCP.setFourteen(EM.getFourteen());
						RCP.setFifteen(EM.getFifteen());
						RCP.setSixteen(EM.getSixteen());
						RCP.setSeventeen(EM.getSeventeen());
						RCP.setEighteen(EM.getEighteen());
						RCP.setNineteen(EM.getNineteen());
						RCP.setTwenty(EM.getTwenty());
						RCP.setTwenty_one(EM.getTwenty_one());
						RCP.setTwenty_two(EM.getTwenty_two());
						RCP.setTwenty_three(EM.getTwenty_three());
						RCP.setTwenty_four(EM.getTwenty_four());
						RCP.setTwenty_five(EM.getTwenty_five());
						RCP.setTwenty_six(EM.getTwenty_six());
						RCP.setTwenty_seven(EM.getTwenty_seven());
						RCP.setTwenty_eight(EM.getTwenty_eight());
						RCP.setTwenty_nine(EM.getTwenty_nine());
						RCP.setThirty(EM.getThirty());
						RCP.setThirty_one(EM.getThirty_one());
						roasterData.add(RCP);
						}
					String filePath=new RoasterExcelDownload().writeDataInExcel(roasterData);
					if(filePath!=null)
					{
						excelStream=new FileInputStream(filePath);
					}
					excelFileName=filePath.substring(4, filePath.length());
				 }
				addActionMessage("Roaster Download Successfully !!!!");
				returnResult = SUCCESS;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public List<RoasterConfPojo> setRoasterData(List list)
	{
		List<RoasterConfPojo> roasterList =new ArrayList<RoasterConfPojo>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			 RoasterConfPojo RCP = new RoasterConfPojo();
			 if (object[0]!=null) {
				 RCP.setId((Integer)object[0]);
			 }
			 
			 if (object[1]!=null) {
				 RCP.setEmpId(object[1].toString());
			 }
		 
			 if (object[2]!=null) {
				 RCP.setEmpName(object[2].toString());
			 }
			 
			 if (object[3]!=null) {
				 RCP.setMobOne(object[3].toString());
			 }
			 
			 if (object[4]!=null) {
				 RCP.setAttendance(object[4].toString());
			 }
			 
			 if (object[5]!=null) {
				 RCP.setStatus(object[5].toString());
			 }
			 
			 if (object[8]!=null) {
				 RCP.setFirst(object[8].toString());
			 }
			 
			 if (object[9]!=null) {
				 RCP.setSecond(object[9].toString());
			 }
			 
			 if (object[10]!=null) {
				 RCP.setThree(object[10].toString());
			 }
			 
			 if (object[11]!=null) {
				 RCP.setFour(object[11].toString());
			 }
			 
			 if (object[12]!=null) {
				 RCP.setFifth(object[12].toString());
			 }
			 
			 if (object[13]!=null) {
				 RCP.setSix(object[13].toString());
			 }
			 
			 if (object[14]!=null) {
				 RCP.setSeven(object[14].toString());
			 }
			 
			 if (object[15]!=null) {
				 RCP.setEight(object[15].toString());
			 }
			 
			 if (object[16]!=null) {
				 RCP.setNine(object[16].toString());
			 }
			 
			 if (object[17]!=null) {
				 RCP.setTen(object[17].toString());
			 }
			 
			 if (object[18]!=null) {
				 RCP.setEleven(object[18].toString());
			 }
			 
			 if (object[19]!=null) {
				 RCP.setTwelve(object[19].toString());
			 }
			 
			 if (object[20]!=null) {
				 RCP.setThirteen(object[20].toString());
			 }
			 
			 if (object[21]!=null) {
				 RCP.setFourteen(object[21].toString());
			 }
			 
			 if (object[22]!=null) {
				 RCP.setFifteen(object[22].toString());
			 }
			 
			 if (object[23]!=null) {
				 RCP.setSixteen(object[23].toString());
			 }
			 
			 if (object[24]!=null) {
				 RCP.setSeventeen(object[24].toString());
			 }
			 
			 if (object[25]!=null) {
				 RCP.setEighteen(object[25].toString());
			 }
			 
			 if (object[26]!=null) {
				 RCP.setNineteen(object[26].toString());
			 }
			 
			 if (object[27]!=null) {
				 RCP.setTwenty(object[27].toString());
			 }
			 
			 if (object[28]!=null) {
				 RCP.setTwenty_one(object[28].toString());
			 }
			 
			 if (object[29]!=null) {
				 RCP.setTwenty_two(object[29].toString());
			 }
			 
			 if (object[30]!=null) {
				 RCP.setTwenty_three(object[30].toString());
			 }
			 
			 if (object[31]!=null) {
				 RCP.setTwenty_four(object[31].toString());
			 }
			 
			 if (object[32]!=null) {
				 RCP.setTwenty_five(object[32].toString());
			 }
			 
			 if (object[33]!=null) {
				 RCP.setTwenty_six(object[33].toString());
			 }
			 
			 if (object[34]!=null) {
				 RCP.setTwenty_seven(object[34].toString());
			 }
			 
			 if (object[35]!=null) {
				 RCP.setTwenty_eight(object[35].toString());
			 }
			 
			 if (object[36]!=null) {
				 RCP.setTwenty_nine(object[36].toString());
			 }
			 
			 if (object[37]!=null) {
				 RCP.setThirty(object[37].toString());
			 }
			 
			 if (object[38]!=null) {
				 RCP.setThirty_one(object[38].toString());
			 }
			 roasterList.add(RCP);
		}
		return roasterList;
	}
	
	
	
	public File getEmpExcel() {
		return empExcel;
	}
	public void setEmpExcel(File empExcel) {
		this.empExcel = empExcel;
	}
	public String getEmpExcelContentType() {
		return empExcelContentType;
	}
	public void setEmpExcelContentType(String empExcelContentType) {
		this.empExcelContentType = empExcelContentType;
	}
	public String getEmpExcelFileName() {
		return empExcelFileName;
	}
	public void setEmpExcelFileName(String empExcelFileName) {
		this.empExcelFileName = empExcelFileName;
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public List<RoasterConfPojo> getExcelData() {
		return excelData;
	}

	public void setExcelData(List<RoasterConfPojo> excelData) {
		this.excelData = excelData;
	}

	public List<RoasterConfPojo> getGridEmpExcelModel() {
		return gridEmpExcelModel;
	}

	public void setGridEmpExcelModel(List<RoasterConfPojo> gridEmpExcelModel) {
		this.gridEmpExcelModel = gridEmpExcelModel;
	}

	public String getSubdept() {
		return subdept;
	}

	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDept_subDept() {
		return dept_subDept;
	}

	public void setDept_subDept(String dept_subDept) {
		this.dept_subDept = dept_subDept;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream) {
		this.excelStream = excelStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
