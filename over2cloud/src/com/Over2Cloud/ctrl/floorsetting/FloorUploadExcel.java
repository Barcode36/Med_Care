package com.Over2Cloud.ctrl.floorsetting;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feeddraft.FeedbackExcelDownload;
import com.Over2Cloud.ctrl.floorsetting.FloorPojo;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;

@SuppressWarnings("serial")
public class FloorUploadExcel extends GridPropertyBean
{
	String wingsname1=null;
	private File feedbackExcel;
	private String feedbackExcelContentType;
	private String feedbackExcelFileName;

	private String excelFileName;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;

	
	private String floorname;
	private String wingsname;
	private String floorcode;
	
	
	
	
	List<FloorPojo> excelData = null;
	private List<FloorPojo> gridFbDraftExcelModel;

	@SuppressWarnings("unchecked")
	public String uploadExcel() throws Exception
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
					System.out.println("feedbackExcel "+feedbackExcel);
					if (feedbackExcel != null)
					{
						InputStream inputStream = new FileInputStream(feedbackExcel);
						
						if (feedbackExcelFileName.contains(".xlsx"))
						{
							GenericReadExcel7 GRE7 = new GenericReadExcel7();
							XSSFSheet excelSheet = GRE7.readExcel(inputStream);
							excelData = new ArrayList<FloorPojo>();
							for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								XSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									FloorPojo FDP = new FloorPojo();
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										if(cellNo==3)
										{
											FDP.setFloorcode("NA");
											System.out.println("Default--For FloorCode"+FDP.getFloorcode());
											FDP.setExtention("NA");
											System.out.println("Default--For FExtention"+FDP.getExtention());
										}
										switch (cellIndex)
										{
											case 0:
											FDP.setFloorname(GRE7.readExcelSheet(row, cellIndex));
											break;

											case 1:
												FDP.setWingsname(GRE7.readExcelSheet(row, cellIndex));
												break;
											case 2:
											FDP.setRoomno(GRE7.readExcelSheet(row, cellIndex));
											break;
											case 3:
												if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
												{
													FDP.setFloorcode(GRE7.readExcelSheet(row, cellIndex));
												}
												else
												{
													FDP.setFloorcode("NA");
												}
												break;
											case 4:
												if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
												{
													FDP.setExtention(GRE7.readExcelSheet(row, cellIndex));
												}
												else
												{
													FDP.setFloorcode("NA");
												}
												break;
															
										}
									}
									excelData.add(FDP);
								}
							}
						}
						else if (feedbackExcelFileName.contains(".xls"))
						{GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						excelData = new ArrayList<FloorPojo>();
						for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						{
							HSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0)
								{
									FloorPojo FDP = new FloorPojo();
									System.out.println("Cell No--"+cellNo);
									
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										
									{
										if(cellNo==3)
										{
											FDP.setFloorcode("NA");
											System.out.println("Default--For FloorCode"+FDP.getFloorcode());
											FDP.setExtention("NA");
											System.out.println("Default--For FExtention"+FDP.getExtention());
										}
							
										switch (cellIndex)
										{
											case 0:
											FDP.setFloorname(GRBE.readExcelSheet(row, cellIndex));
											System.out.println("SetFloorname--"+FDP.getFloorname());
															
											break;

											case 1:
											FDP.setWingsname(GRBE.readExcelSheet(row, cellIndex));
											System.out.println("SetWingsName--"+FDP.getWingsname());
											break;
											

											case 2:
											FDP.setRoomno(GRBE.readExcelSheet(row, cellIndex));
											System.out.println("SetRoom NO--"+FDP.getRoomno());
											break;

											
											case 3:
												System.out.println("Floor Code---");
												if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equals(""))
												{
													FDP.setFloorcode(GRBE.readExcelSheet(row, cellIndex));
													System.out.println("Floor Code in If Part--"+FDP.getFloorcode());
												}
												else
												{
													FDP.setFloorcode("NA");
													System.out.println("Floor Code in Else Part--"+FDP.getFloorcode());
												}
												break;
											case 4:
												if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equals(""))
												{
													FDP.setExtention(GRBE.readExcelSheet(row, cellIndex));
													System.out.println("Floor Code in If Part 4--"+FDP.getExtention());
												}
												else
												{
													FDP.setExtention("NA");
													System.out.println("Floor Code in Else Part 4--"+FDP.getExtention());
												}
												break;
											/*FDP.setExtention(GRBE.readExcelSheet(row, cellIndex));
											System.out.println("SetExtention--"+FDP.getExtention());
											break;*/
										}
									}
									excelData.add(FDP);
								}
							}
						}
						if (excelData.size() > 0)
						{
							session.put("fbDraftList", excelData);
						}
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;

	}

	@SuppressWarnings("unchecked")
	public String showUploadFbDraft()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<FloorPojo>) session.get("fbDraftList");
					if (excelData != null && excelData.size() > 0)
					{
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();

						setGridFbDraftExcelModel(excelData.subList(from, to));

						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
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
		int floorID=0;
		boolean status=false;
		String returnResult = ERROR;
		String floor=null;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<FloorPojo>) session.get("fbDraftList");
					
					
					
					
					session.remove("fbDraftList");
					boolean flag = false;
					InsertDataTable ob = null;
					for (Iterator<FloorPojo> it = excelData.iterator(); it.hasNext();)
					{
						
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						FloorPojo object=new FloorPojo();
						System.out.println("inside iterator");
						object= it.next();
						floor=getfloorname1();
						if(floor==null)
						{
								System.out.println("inside null");
								ob=new InsertDataTable();
								ob.setColName("floorname");
								ob.setDataName(object.getFloorname());
								insertData.add(ob);
								
								
								
								 ob=new InsertDataTable();
						         ob.setColName("floorcode");
								 ob.setDataName(object.getFloorcode());
								 insertData.add(ob);
									 
									 
								 ob = new InsertDataTable();
								 ob.setColName("userName");
								 ob.setDataName(userName);
								 insertData.add(ob);
										
								 ob = new InsertDataTable();
								 ob.setColName("date");
								 ob.setDataName(DateUtil.getCurrentDateUSFormat());
								 insertData.add(ob);
									
								 ob = new InsertDataTable();
								 ob.setColName("time");
								 ob.setDataName(DateUtil.getCurrentTimeHourMin());
								 insertData.add(ob);
									
								 status = cot.insertIntoTable("floor_detail", insertData, connectionSpace);
								 
								 
								 String wingsname=object.getWingsname();
								 
								 System.out.println("in nullll part wingname "+wingsname);
								 
								 String floorname=object.getFloorname();
								 
								 String roomno=object.getRoomno();
								 String extention=object.getExtention();
								 
								 check(roomno,floorname,wingsname,extention);
						}
						else if(floor.equalsIgnoreCase(object.getFloorname()))
						{
							System.out.println("inside if else");
							/*for(int i1=0; i1<excelData.size();i1++)
							{*/
								String roomno=object.getRoomno();
								 String wingsname=object.getWingsname();
								 
								 System.out.println("in if else part wingname "+wingsname);
								 
								 String floorname=object.getFloorname();
								 String extention=object.getExtention();
								 check(roomno,floorname,wingsname,extention);
								//check2(roomno,wingsname,floorname,extention);
							/*}*/

						}
						
						else
						{
							System.out.println("inside else");
							ob=new InsertDataTable();
							ob.setColName("floorname");
							ob.setDataName(object.getFloorname());
							System.out.println("Floor-name :"+object.getFloorname());
							insertData.add(ob);
							
							 ob=new InsertDataTable();
					         ob.setColName("floorcode");
							 ob.setDataName(object.getFloorcode());
							 System.out.println("Floor-Code :"+object.getFloorcode());
							 insertData.add(ob);
								 
								 
							 ob = new InsertDataTable();
							 ob.setColName("userName");
							 ob.setDataName(userName);
							 insertData.add(ob);
									
							 ob = new InsertDataTable();
							 ob.setColName("date");
							 ob.setDataName(DateUtil.getCurrentDateUSFormat());
							 insertData.add(ob);
								
							 ob = new InsertDataTable();
							 ob.setColName("time");
							 ob.setDataName(DateUtil.getCurrentTimeHourMin());
							 insertData.add(ob);
								
							 status = cot.insertIntoTable("floor_detail", insertData, connectionSpace);
							 
							 String roomno=object.getRoomno();
							 String wingsname=object.getWingsname();
							 
							 System.out.println("in else part wingname "+wingsname);
							 
							 String floorname=object.getFloorname();
							 String extention=object.getExtention();
							 check(roomno,floorname,wingsname,extention);
						}
					}
					 if (status)
					 {
						flag = false;
					 }
				}
				if (true)
					addActionMessage("Excel Uploaded Successfully.");
				else
					addActionMessage("!!!May be Some or Full Data belongss to Other Sub Department. Otherwise Data is Saved.");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}
	public String getfloorname1()
	{
		String floorn=null;
			
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT floorname FROM floor_detail");
		System.out.println("query====Floorname000===="+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				floorn= (String) it.next();
				
			}
		}
		
		return floorn;
	}
	public void check(String roomno,String floorcode,String wingsname,String extention) 
	{
		
		
		System.out.println("in check part   wingname  "+wingsname);
		
		boolean status=false;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		InsertDataTable ob = null;
		int floorid=0;
		boolean flag=false;
		
	    try 
		{
	    	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	    	FloorPojo object=new FloorPojo();
	    	wingsname1=getwingsname1();
	    	System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+wingsname1);
	    	if(wingsname1==null)
	    	{
	    		System.out.println(" wing inside nullllll");
	    		ob=new InsertDataTable();
				ob.setColName("floorname");
				floorid=getFloorID(floorcode);
				ob.setDataName(floorid);
				insertData.add(ob);
					

		    	
		    	ob=new InsertDataTable();
		        ob.setColName("wingsname");
				ob.setDataName(wingsname);
				insertData.add(ob);
				 
				status = cot.insertIntoTable("wings_detail", insertData, connectionSpace);
				
				/*String roomno=object.getRoomno();
				String floorname=object.getFloorname();
				String extention=object.getExtention();
				wingsname=object.getWingsname();*/
				
				check2(roomno,wingsname,floorcode,extention);
				
				
	    	}
	    	else if(wingsname1.equalsIgnoreCase(wingsname))
			{
				
					/*String roomno=object.getRoomno();
					String floorname=object.getFloorname();
					String extention=object.getExtention();*/
					check2(roomno,wingsname,floorcode,extention);
				

			}
	    	else
	    	{
	    		System.out.println("inside wing else");
	    		ob=new InsertDataTable();
				ob.setColName("floorname");
				floorid=getFloorID(floorcode);
				ob.setDataName(floorid);
				insertData.add(ob);
					

		    	
		    	ob=new InsertDataTable();
		        ob.setColName("wingsname");
				ob.setDataName(wingsname);
				insertData.add(ob);
				 
				status = cot.insertIntoTable("wings_detail", insertData, connectionSpace);
				
				/*String roomno=object.getRoomno();
				String floorname=object.getFloorname();
				String extention=object.getExtention();*/
				
				check2(roomno,wingsname,floorcode,extention);
	    	}
	    	
			if (status)
			{
				flag = false;
			}	
		}
	    catch (Exception e) 
	    {
			System.out.println(e);
		} 
	}
	public String getwingsname1()
	{
		String Wingsname1=null;
			
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT wingsname FROM wings_detail");
		System.out.println("query====wingsname===="+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				Wingsname1= (String) it.next();
				
			}
		}
		
		return Wingsname1;
	}
	public int getFloorID(String floorname)
	{
		int id = 0;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT id FROM floor_detail where floorname='"+floorname+"'");
		System.out.println("query========"+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		System.out.println("size is:"+dataList.size());
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				id=(Integer) it.next();
				//System.out.println("id :"+id);
			}
		}
		return id;
	}
	public void check2(String roomno,String wingsname,String floorname,String extention) 
	{
		//System.out.println("jjjjj  floorcode "+floorcode);
		CommonOperInterface cot = new CommonConFactory().createInterface();
		InsertDataTable ob = null;
		int floorid=0;
		int wingsid=0;
		boolean flag=false;
	    try 
		{
	    	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	    	FloorPojo object=new FloorPojo();
	    	ob=new InsertDataTable();
	    	
	    	//System.out.println(" Inner Try  floorcode 2 "+floorcode);
			ob.setColName("floorname");
			floorid=getFloorID2(floorname);
			System.out.println("floorId :"+floorid);
			ob.setDataName(floorid);
			insertData.add(ob);
				

			ob=new InsertDataTable();
			System.out.println("roomno  :"+roomno);
	        ob.setColName("roomno");
			ob.setDataName(roomno);
			insertData.add(ob);
	    	
			
			ob=new InsertDataTable();
			System.out.println("extention  :"+extention);
	        ob.setColName("extention");
			ob.setDataName(extention);
			insertData.add(ob);
			
			
	    	ob=new InsertDataTable();
			System.out.println("wingsname :"+wingsname);
	        ob.setColName("wingsname");
	        wingsid=getWingdId(wingsname);
			ob.setDataName(wingsid);
			insertData.add(ob);
			 
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(userName);
			insertData.add(ob);
				
			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);
				
			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTimeHourMin());
			insertData.add(ob);
			
			boolean status = cot.insertIntoTable("floor_room_detail", insertData, connectionSpace);
			
			if (status)
			{
				flag = false;
			}	
		}
	    catch (Exception e) 
	    {
			System.out.println(e);
		} 
	}
	public int getFloorID2(String floorname)
	{
		int id = 0;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT id FROM floor_detail where floorname='"+floorname+"'");
		System.out.println("query========"+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		System.out.println("size is:"+dataList.size());
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				id=(Integer) it.next();
				//System.out.println("id :"+id);
			}
		}
		return id;
	}
	public int getWingdId(String wingsname)
	{
		int id = 0;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT id FROM wings_detail where wingsname='"+wingsname+"'");
		System.out.println("query========"+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		System.out.println("size is:"+dataList.size());
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				id=(Integer) it.next();
				//System.out.println("id :"+id);
			}
		}
		return id;
	}
	public int getFloornameID(String floorname)
	{
		int id = 0;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT id FROM floor_detail where floorname='"+floorname+"'");
		System.out.println("query===get Floor id====="+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		System.out.println("size is:"+dataList.size());
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				id=(Integer) it.next();
				//System.out.println("id :"+id);
			}
		}
		return id;
	}
	public String downloadFbDraftPage()
	{
		return SUCCESS;
	}

	/*@SuppressWarnings("unchecked")
	public String getData4Download()
	{
		// System.out.println("Inside Method    ");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					List list = new HelpdeskUniversalHelper().getFeedbackList(deptId, subdeptname, fbType, categoryName, viewType, connectionSpace);
					if (list != null && list.size() > 0)
					{
						excelData = setFedDraftData(list);
					}
					if (excelData != null && excelData.size() > 0)
					{
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();

						setGridFbDraftExcelModel(excelData.subList(from, to));
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
					if (excelData.size() > 0)
					{
						session.put("feedDraftDownloadList", excelData);
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}
*/
	/*@SuppressWarnings("unchecked")
	public String downloadFeedbackDraft()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<FloorPojo>) session.get("feedDraftDownloadList");
					session.remove("feedDraftDownloadList");

					List<FloorPojo> feedDraftData = new ArrayList<FloorPojo>();
					FeedbackDraftPojo FP = null;
					for (Iterator<FeedbackDraftPojo> empItr = excelData.iterator(); empItr.hasNext();)
					{
						FeedbackDraftPojo PDF = empItr.next();
						// if (selectedId.contains(String.valueOf(PDF.getId())))
						// {
						FP = new FeedbackDraftPojo();
						FP.setFeedtype(PDF.getFeedtype());
						FP.setCategory(PDF.getCategory());
						FP.setSub_category(PDF.getSub_category());
						FP.setFeed_msg(PDF.getFeed_msg());
						FP.setAddressing_time(PDF.getAddressing_time());
						FP.setResolution_time(PDF.getResolution_time());
						FP.setEscDuration(PDF.getEscDuration());
						FP.setEsclevel(PDF.getEsclevel());
						FP.setNeedesc(PDF.getNeedesc());
						FP.setSeverity(PDF.getSeverity());
						// }
						feedDraftData.add(FP);
					}
					String filePath = new FeedbackExcelDownload().writeDataInExcel(feedDraftData);
					if (filePath != null)
					{
						excelStream = new FileInputStream(filePath);
					}
					excelFileName = filePath.substring(4, filePath.length());
				}
				addActionMessage("Feedback Draft Download Successfully !!!!");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
*/
	/*@SuppressWarnings("unchecked")
	public List<FloorPojo> setFedDraftData(List list)
	{
		List<FloorPojo> feedDraftList = new ArrayList<FloorPojo>();
		int i = 1;
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			Object[] object = (Object[]) iterator.next();
			FloorPojo FDP = new FloorPojo();
			FDP.setId(i);
			if (object[0] != null)
			{
				FDP.setFloorname(object[0].toString());
			}

			if (object[1] != null)
			{
				FDP.setRoomno(object[1].toString());
			}

			if (object[2] != null)
			{
				FDP.setWingsname(object[2].toString());
			}

			
			feedDraftList.add(FDP);
			i++;
		}
		return feedDraftList;
	}*/

	public File getFeedbackExcel()
	{
		return feedbackExcel;
	}

	public void setFeedbackExcel(File feedbackExcel)
	{
		this.feedbackExcel = feedbackExcel;
	}

	public String getFeedbackExcelContentType()
	{
		return feedbackExcelContentType;
	}

	public void setFeedbackExcelContentType(String feedbackExcelContentType)
	{
		this.feedbackExcelContentType = feedbackExcelContentType;
	}

	public String getFeedbackExcelFileName()
	{
		return feedbackExcelFileName;
	}

	public void setFeedbackExcelFileName(String feedbackExcelFileName)
	{
		this.feedbackExcelFileName = feedbackExcelFileName;
	}

	public List<FloorPojo> getExcelData()
	{
		return excelData;
	}

	public void setExcelData(List<FloorPojo> excelData)
	{
		this.excelData = excelData;
	}

	public List<FloorPojo> getGridFbDraftExcelModel()
	{
		return gridFbDraftExcelModel;
	}

	public void setGridFbDraftExcelModel(List<FloorPojo> gridFbDraftExcelModel)
	{
		this.gridFbDraftExcelModel = gridFbDraftExcelModel;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}
	public String getFloorname()
	{
		return floorname;
	}

	public void setFloorname(String floorname)
	{
		this.floorname = floorname;
	}

	public String getWingsname()
	{
		return wingsname;
	}

	public void setWingsname(String wingsname)
	{
		this.wingsname = wingsname;
	}

	public String getFloorcode() {
		return floorcode;
	}

	public void setFloorcode(String floorcode) {
		this.floorcode = floorcode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
