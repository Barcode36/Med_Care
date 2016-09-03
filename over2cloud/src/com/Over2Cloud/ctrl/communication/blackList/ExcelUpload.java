package com.Over2Cloud.ctrl.communication.blackList;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.SmsShowBean;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.common.communication.UploadSmsExceldata;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

@SuppressWarnings("serial")
public class ExcelUpload extends ActionSupport 
{
	    @SuppressWarnings("unchecked")
	    Map session = ActionContext.getContext().getSession();
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		static final Log log = LogFactory.getLog(UploadSmsExceldata.class);
		public String upload4;
	 	List<BlackListBean> excelData =null;
	 	public List<Object> gridDataList;
	    private List<BlackListBean> gridModelList;
	    private String excelFileName;
	    private String excel;
	    private String text;
	    private String textFileName;
	    public Map<String,Object> columnNameMap=null;
	    private Integer rows=0;
	    private Integer total=0;
	    private Integer records=0;
	    private Integer page=0;
	    private String select_id;
	    private int id = 1;
	    public String getSelect_id() {
			return select_id;
		}

		public void setSelect_id(String select_id) {
			this.select_id = select_id;
		}

		private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	   
	  
	    @SuppressWarnings("unchecked")
	public String readExcelData()
	{
		String returnResult = ERROR;
		try 
		{
			if (excel != null) 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				columnNameMap=new LinkedHashMap<String, Object>();
				InputStream inputStream = new FileInputStream(excel);
				
				  try 
				  {
					if (excelFileName.contains(".xls")) 
					{
						GenericReadExcel7 GRE7 = new GenericReadExcel7();
							// call method for getting excel sheet
							XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						excelData = new ArrayList<BlackListBean>();
						for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++) {
							XSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							BlackListBean blb =new BlackListBean();
							blb.setId(id);
						     for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
						     { 
						    	 switch (cellIndex) 
						    	 {
						              case 0:
						              {    
						            	  String query="SELECT mobileno FROM blacklist";
						            	  List data=cbt.executeAllSelectQuery(query, connectionSpace);
						            	  if (data!=null && data.size()>0) 
						            	  {
											for (Iterator iterator = data.iterator(); iterator.hasNext();) 
											{
												Object object = (Object) iterator.next();
												if (object!=null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase("")) 
												{
													if (object.toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex))) {
														blb.setAlreadyPresent(GRE7.readExcelSheet(row, cellIndex));
													} 
												}
											}
										  }
						            	  if (GRE7.readExcelSheet(row, cellIndex).startsWith("9")||GRE7.readExcelSheet(row, cellIndex).startsWith("8")||GRE7.readExcelSheet(row, cellIndex).startsWith("7") || GRE7.readExcelSheet(row, cellIndex).length()==10) 
						            	  {
						            		  blb.setValidNum(GRE7.readExcelSheet(row, cellIndex));
						            		  id++;
						            	  }
						            	  else
						            	  {
						            		  blb.setInvalidNum(GRE7.readExcelSheet(row, cellIndex));
						            	  }
						            		  break;
						              }
						          }
							}
						     excelData.add(blb);
						}
						}else{
							
							GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
							HSSFWorkbook workBook = GRBE.multireadExcel(inputStream);
							excelData = new ArrayList<BlackListBean>();
							for (int sheetNO = 0; sheetNO < workBook.getNumberOfSheets(); sheetNO++) {
								if (workBook.getSheetAt(sheetNO) != null) {
									HSSFSheet excelSheet = workBook.getSheetAt(sheetNO);
									for (int rowIndex = 1; rowIndex <= excelSheet.getLastRowNum(); rowIndex++) {
								HSSFRow row = excelSheet.getRow(rowIndex);
						        int cellNo = row.getLastCellNum();
								BlackListBean blb =new BlackListBean();
							     for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
							     { 
							    	 switch (cellIndex) 
							    	 {
							              case 0:
							              {    
							            	  String query="SELECT mobileno FROM blacklist";
							            	  List data=cbt.executeAllSelectQuery(query, connectionSpace);
							            	  if (data!=null && data.size()>0) 
							            	  {
												for (Iterator iterator = data.iterator(); iterator.hasNext();) 
												{
													Object object = (Object) iterator.next();
													if (object!=null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase("")) 
													{
														if (object.toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex))) {
															
															blb.setAlreadyPresent(GRBE.readExcelSheet(row, cellIndex));
														} 
													}
												}
											  }
							            	  if (GRBE.readExcelSheet(row, cellIndex).startsWith("9")||GRBE.readExcelSheet(row, cellIndex).startsWith("8")||GRBE.readExcelSheet(row, cellIndex).startsWith("7") || GRBE.readExcelSheet(row, cellIndex).length()==10) 
							            	  {
							            		  blb.setValidNum(GRBE.readExcelSheet(row, cellIndex));
							            	  }
							            	  else
							            	  {
							            		  blb.setInvalidNum(GRBE.readExcelSheet(row, cellIndex));
							            	  }
							            		  break;
							              }
							          }
								}
							     excelData.add(blb);
							}
						
					}
					returnResult = SUCCESS;
						}
					}
				  }
			    catch (Exception e) 
			    {
					e.printStackTrace();
				}	 
		       if(excelData!=null && excelData.size()>0)
		       {
		    	   session.put("blacklistList", excelData);
		       }
			}
	}				
	catch(Exception e)
	{
		e.printStackTrace();
	}
	 return returnResult=SUCCESS;	
}

		public String uploadTextFiledata() {
			String returnResult = ERROR;
			try {
				String user = session.get("uName").toString();

				if (user == null || user.equals("")) {
					session.clear();
					returnResult = LOGIN;

				} else {
					//activity log work starts here
					CommonOperInterface cbt=new CommonConFactory().createInterface();
			   	   //activity log work ends here
					// Check for null
					if (text == null) {
						addActionError("Select a file to upload!");
						addFieldError("ExcelUplaod", "Select a Text file to upload");
						returnResult = SUCCESS;
						return returnResult;
					}

					if (text != null) {
						InputStream inputStream = new FileInputStream(text);
						if (textFileName.contains(".txt")) {
							DataInputStream in = new DataInputStream(inputStream);
							// BufferReader ..
							BufferedReader br = new BufferedReader(new InputStreamReader(in));

							try {
								String strLine;
								excelData = new ArrayList<BlackListBean>();
								// Read File Line By Line
								while ((strLine = br.readLine()) != null) {
									 System.out.println("strLine>>>>>>>>>>>>>>>>>>"+strLine);
									BlackListBean blb =new BlackListBean();
								            	  String query="SELECT mobileno FROM blacklist";
								            	  List data=cbt.executeAllSelectQuery(query, connectionSpace);
								            	  if (data!=null && data.size()>0) 
								            	  {
													for (Iterator iterator = data.iterator(); iterator.hasNext();) 
													{
														Object object = (Object) iterator.next();
															if (object.toString().equalsIgnoreCase(strLine)) {
																
																blb.setAlreadyPresent(strLine);
															} 
													  }
												  }
								            	  if (strLine.startsWith("9")||strLine.startsWith("8")||strLine.startsWith("7") || strLine.length()==10) {
								            		  blb.setValidNum(strLine);
								            	  }
								            	  else {
								            		  blb.setInvalidNum(strLine);
								            	  }
									      blb.setId(id);
										id++;
										// Print the content on the console
										excelData.add(blb);
								}
								if (excelData.size() > 0) {
									 session.put("blacklistList", excelData);
								}
								// Close the input stream
								in.close();
							} catch (Exception e) {// Catch exception if any
								log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method UploadTextFiledata of class "+getClass(), e);
								addActionError("Oops there is some problem!!!");
								return ERROR;
							}
						}
						returnResult = SUCCESS;
					}
				}

			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method UploadTextFiledata of class "+getClass(), e);
				addActionError("Oops there is some problem!!!");
				return ERROR;
			}
			return returnResult;

		}
	    
		 @SuppressWarnings("unchecked")
		public String showUpload()
			{
			 System.out.println("inside show upload");
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
							excelData = (List<BlackListBean>)session.get("blacklistList");
							if (excelData !=null && excelData.size()>0) {
								System.out.println(" excelData" + excelData.size());
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
			public String saveblacklistData() {
				String returnResult = ERROR;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				try{
					String user=  session.get("uName").toString();
					if(user==null || user.equalsIgnoreCase("") )
					{
						session.clear();
						addActionMessage("Youe Session has been Finished!");
						returnResult = LOGIN;
					}
			    	else{
			    		     InsertDataTable ob = null;
			    		     String[] selectedvalidIdArray=null;
			    		      if(select_id!=null && !select_id.equalsIgnoreCase("") ) {
			    		    		excelData = (List<BlackListBean>)session.get("blacklistList");
				    		    	session.remove("blacklistList");
				    		    	selectedvalidIdArray=select_id.trim().split(",");
			    		         }
			    				 boolean msgflag = false;
			    				if(excelData!=null)
			    				{
				    					 for (Iterator SMSdata = excelData.iterator(); SMSdata.hasNext();) {
				    						 BlackListBean smsObjS = (BlackListBean) SMSdata.next();
				    						  for (int i = 0; i < selectedvalidIdArray.length; i++) {
						    				      if(selectedvalidIdArray[i].contains(Integer.toString(smsObjS.getId()))) {
						    				    	  List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						    				    	  System.out
															.println(">>>>>>>Mobile No"+smsObjS.getValidNum());
						    				    	    ob = new InsertDataTable();
							   							ob.setColName("mobileno");
							   							ob.setDataName(smsObjS.getValidNum());
							   							insertData.add(ob);
							   							
							   							ob = new InsertDataTable();
							   							ob.setColName("name");
							   							ob.setDataName("NA");
							   							insertData.add(ob);
							   							
							   							ob = new InsertDataTable();
							   							ob.setColName("reason");
							   							ob.setDataName("NA");
							   							insertData.add(ob);
							   						
							   							ob = new InsertDataTable();
							   							ob.setColName("fromdate");
							   							ob.setDataName("NA");
							   							insertData.add(ob);
							   							
							   							ob = new InsertDataTable();
							   							ob.setColName("todate");
							   							ob.setDataName("NA");
							   							insertData.add(ob);
							   							
							   							ob = new InsertDataTable();
							   							ob.setColName("duration");
							   							ob.setDataName("NA");
							   							insertData.add(ob);
							   							
							   	    					ob = new InsertDataTable();
							   							ob.setColName("user");
							   							ob.setDataName(user);
							   							insertData.add(ob);
							   							msgflag = cbt.insertIntoTable("blacklist", insertData,connectionSpace);
						    				          }	    
						    				       }
				    				        }
			    				}
			    				if(msgflag){
			    					addActionMessage("Data Added Successfully.");
			    				}else{
			    					addActionMessage("You Must be Upload Data Again.");
			    				}
			    		    }
					       
				}
				catch (Exception e) {
					e.printStackTrace();
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method updateAssociatStatus of class "+getClass(), e);
					addActionError("Oops there is some problem!!!");
					return ERROR;
				}
				return SUCCESS;
			}

		 
	/*	 public String beforeSaveXls() {
				String returnValue = ERROR;
				if (ValidateSession.checkSession()) {
					GridDataPropertyView gpv = new GridDataPropertyView();
					gpv.setColomnName("validNum");
					gpv.setHeadingName("Valid Numbers");
					gpv.setHideOrShow("false");
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("alreadyPresent");
					gpv.setHeadingName("Already Present");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setAlign("center");
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("invalidNum");
					gpv.setHeadingName("InValid Numbers");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setAlign("center");
					masterViewProp.add(gpv);
					
					
					
					System.out.println("masterViewProp:" + masterViewProp.size());

					returnValue = SUCCESS;
				} else {
					returnValue = LOGIN;
				}
				return returnValue;
			}
		 */
		 public String viewConfirmationData()
		 {
			 return SUCCESS;
		 }

		
		public List<GridDataPropertyView> getMasterViewProp() {
			return masterViewProp;
		}

		public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
			this.masterViewProp = masterViewProp;
		}

		public Integer getRows() {
			return rows;
		}

		public void setRows(Integer rows) {
			this.rows = rows;
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

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}

		public String getUpload4() {
			return upload4;
		}

		public void setUpload4(String upload4) {
			this.upload4 = upload4;
		}

		

		public List<BlackListBean> getExcelData() {
			return excelData;
		}

		public void setExcelData(List<BlackListBean> excelData) {
			this.excelData = excelData;
		}

		
		public List<BlackListBean> getGridModelList() {
			return gridModelList;
		}

		public void setGridModelList(List<BlackListBean> gridModelList) {
			this.gridModelList = gridModelList;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTextFileName() {
			return textFileName;
		}

		public void setTextFileName(String textFileName) {
			this.textFileName = textFileName;
		}

		public String getExcelFileName() {
			return excelFileName;
		}

		public void setExcelFileName(String excelFileName) {
			this.excelFileName = excelFileName;
		}

		public String getExcel() {
			return excel;
		}

		public void setExcel(String excel) {
			this.excel = excel;
		}

		public Map<String, Object> getColumnNameMap() {
			return columnNameMap;
		}

		public void setColumnNameMap(Map<String, Object> columnNameMap) {
			this.columnNameMap = columnNameMap;
		}

		public List<Object> getGridDataList() {
			return gridDataList;
		}

		public void setGridDataList(List<Object> gridDataList) {
			this.gridDataList = gridDataList;
		}
		 
		 
		 
		 
		    
			}
