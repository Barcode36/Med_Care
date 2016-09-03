package com.Over2Cloud.ctrl.buddy;

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
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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

public class ExcelUpload extends ActionSupport{
	
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
    
    @SuppressWarnings("unchecked")
public String readExcelData()
{
	String returnResult = ERROR;
	try 
	{
		if (excel != null) 
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			columnNameMap=new LinkedHashMap<String, Object>();
			Map<String, String> columnMap=new LinkedHashMap<String, String>();
			List<String> columnKeyList=new ArrayList<String>();
			Map<String, Object> dataKeyMap=new LinkedHashMap<String, Object>();
			List<String> gridListData  = new ArrayList<String>();
			InputStream inputStream = new FileInputStream(excel);
			Map<String, String> tempMap=new LinkedHashMap<String, String>();
			Map<String, String> floorMap=new LinkedHashMap<String, String>();
			List<String> dropDownId=new ArrayList<String>();
			List<String> dropDownValue=new ArrayList<String>();
			List<String> employeeDetails=new ArrayList<String>();
			int floorCellIndex = 0,floorNameIndex=0,mobnoNameIndex=0;
			List dataList=null;
			
			if(upload4.equals("buddyDetail"))
			{
				if(new BuddySetting().getColumnName("mapped_buddy_setting","buddy_setting_configuration","")!=null)
				{
					returnResult=SUCCESS;
					columnMap=(Map<String, String>) session.get("columnMap");
				}
			}
			dataList=new ArrayList();
			dataList=cbt.executeAllSelectQuery("SELECT id,floorcode FROM buddy_floor_info",connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null && object[1].toString()!="")
					{
						floorMap.put(object[1].toString(), object[0].toString());
					}
				}
			}
			try
			{
				if(excelFileName.contains(".xlsx"))
				{
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
        							  
        					    		if(paramPair.getKey().toString().equals("floorcode"))
        					    		{
        					    			floorCellIndex=cellIndex;
        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
        					    		}
        					    		columnKeyList.add(paramPair.getKey());
        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
        							}
        						}
		        		     }
	        				 setColumnNameMap(dataKeyMap);
	        			 }
	        	 else {

	        		 int codeMaxId=0;
	        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
	        		 {
			             if(GRE7.readExcelSheet(row, cellIndex)!=null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
			             {
			            	 if(tempMap.containsKey(String.valueOf(cellIndex)))
			            	 {
			            		 if(floorMap.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
			            		 {
			            			 dropDownId.add(floorMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
			            			 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
			            			 codeMaxId=Integer.valueOf(floorMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
			            		 }
			            		 else if(floorCellIndex==cellIndex)
			            		 {
			            			 String id = null,value=null;
			            			 Iterator<Entry<String, String>> hiterator=floorMap.entrySet().iterator();
		        					 while (hiterator.hasNext()) 
		        					 {
		        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
		        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
        					    		{
		        					    	String query="SELECT id FROM buddy_floor_info WHERE floorcode='"+GRE7.readExcelSheet(row, cellIndex).toString()+"'";
		        					    	dataList=cbt.executeAllSelectQuery(query,connectionSpace);
		        					    	if(dataList!=null && dataList.size()>0)
		        					    		id=dataList.get(0).toString();
		        					    	
		        					    	value=GRE7.readExcelSheet(row, cellIndex).toString();
        					    		}
		        					 }
		        					 
		        					 if(id!=null && value!=null && id!="" && value!="")
		        					 {
		        						dropDownId.add(id);
        			            		dropDownValue.add(value);
        			            		codeMaxId=Integer.valueOf(id);
		        					 }
		        					 else
		        					 {
		        						
		        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
		        						 dataWithColumnName.put("floorcode", GRE7.readExcelSheet(row, cellIndex).toString());
		        						 dataWithColumnName.put("floorname", GRE7.readExcelSheet(row, cellIndex).toString());
		        						 
		        						 codeMaxId=new BuddySetting().saveData("mapped_buddy_setting", "buddy_setting_configuration", "buddy_floor_info", dataWithColumnName);
		        						 if(codeMaxId>0)
		        						 {
		        							 dropDownId.add(String.valueOf(codeMaxId));
		        			            	 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
		        			            	 floorMap.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(codeMaxId));
		        						 }
		        					 }
			            		 }
			            	 }
			            	 gridListData.add(GRE7.readExcelSheet(row, cellIndex).toString()); 
			             }  
	        		 }
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
    							  
    					    		if(paramPair.getKey().toString().equals("floorcode"))
    					    		{
    					    			floorCellIndex=cellIndex;
    					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
    					    		}
    					    		if(paramPair.getKey().toString().equals("floorname"))
    					    		{
    					    			floorNameIndex=cellIndex;
    					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
    					    		}
    					    		if (paramPair.getKey().toString().equals("mobno")) {
    					    			mobnoNameIndex=cellIndex;
    					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
									}
    					    		columnKeyList.add(paramPair.getKey());
    					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
    							}
    						}
	        		    }
	        		setColumnNameMap(dataKeyMap);
	        	}
	    		else
	    		{
	    			 int codeMaxId=0;
	        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
	        		 {
		            	 if(tempMap.containsKey(String.valueOf(cellIndex)))
		            	 {
		            		 if(floorMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
		            		 {
		            			 dropDownId.add(floorMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		            			 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
		            			 codeMaxId=Integer.valueOf(floorMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		            		 }
		            		 else if(floorCellIndex==cellIndex)
		            		 {
		            			 String id = null,value=null;
		            			 Iterator<Entry<String, String>> hiterator=floorMap.entrySet().iterator();
	        					 while (hiterator.hasNext()) 
	        					 {
	        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
	        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
    					    		{
	        					    	String query="SELECT id FROM buddy_floor_info WHERE floorcode='"+GRBE.readExcelSheet(row, cellIndex).toString()+"'";
	        					    	dataList=cbt.executeAllSelectQuery(query,connectionSpace);
	        					    	if(dataList!=null && dataList.size()>0)
	        					    		id=dataList.get(0).toString();
	        					    	
	        					    	value=GRBE.readExcelSheet(row, cellIndex).toString();
    					    		}
	        					 }
	        					 
	        					 if(id!=null && value!=null && id!="" && value!="")
	        					 {
	        						dropDownId.add(id);
    			            		dropDownValue.add(value);
    			            		codeMaxId=Integer.valueOf(id);
	        					 }
	        					 else
	        					 {
	        						
	        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
	        						 dataWithColumnName.put("floorcode", GRBE.readExcelSheet(row, cellIndex).toString());
	        						 dataWithColumnName.put("floorname", GRBE.readExcelSheet(row, floorNameIndex).toString());
	        						 System.out
											.println(GRBE.readExcelSheet(row, floorNameIndex).toString());
	        						 
	        						 codeMaxId=new BuddySetting().saveData("mapped_buddy_setting", "buddy_setting_configuration", "buddy_floor_info", dataWithColumnName);
	        						 if(codeMaxId>0)
	        						 {
	        							 dropDownId.add(String.valueOf(codeMaxId));
	        			            	 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
	        			            	 floorMap.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(codeMaxId));
	        						 }
	        					 }
		            		 }
		            		 else if (mobnoNameIndex==cellIndex) {
								List data=null;
								data=cbt.executeAllSelectQuery("SELECT id,subdept FROM employee_basic WHERE mobOne='"+GRBE.readExcelSheet(row, mobnoNameIndex).toString()+"'", connectionSpace);
							 
								for (Iterator iterator = data.iterator(); iterator.hasNext();) {
										
									Object[] object = (Object[]) iterator.next();
									dropDownId.add(object[0].toString()+"#"+object[1].toString());
									dropDownValue.add(GRBE.readExcelSheet(row, mobnoNameIndex).toString());
									floorMap.put(GRBE.readExcelSheet(row, mobnoNameIndex).toString(), object[0].toString());
								}
		            		}
		            	 }
		            	 gridListData.add(GRBE.readExcelSheet(row, cellIndex).toString()); 
		            }
	    		}
	        }
		}
	}
	if (dropDownId!=null && dropDownId.size() > 0) 
	{
		session.put("dropDownId", dropDownId);
		session.put("dropDownValue", dropDownValue);
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
}
catch(Exception e)
{
	e.printStackTrace();
}
return returnResult;	
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
				int from = to - getRows();
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
	public String saveSelectData()
    {
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
				boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
				if(upload4!=null && !upload4.equals("") && upload4.equals("buddyDetail"))
				{
					statusColName=Configuration.getConfigurationData("mapped_buddy_setting",accountID,connectionSpace,"",0,"table_name","buddy_setting_configuration");
				}
				if(statusColName!=null&&statusColName.size()>0)
				{
					List <TableColumes> tableColume1=new ArrayList<TableColumes>();
					for(GridDataPropertyView gdp:statusColName)
					{
					 if(!gdp.getColomnName().equalsIgnoreCase("floorcode") && !gdp.getColomnName().equalsIgnoreCase("floorname") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) 
						{
							 TableColumes ob1=new TableColumes();
							 ob1.setColumnname(gdp.getColomnName());
							 ob1.setDatatype("varchar(255)");
							 ob1.setConstraint("default NULL");
							 tableColume1.add(ob1);
						}
					 if(gdp.getColomnName().equalsIgnoreCase("userName"))
						 userTrue=true;
					 else if(gdp.getColomnName().equalsIgnoreCase("date"))
						 dateTrue=true;
					 else if(gdp.getColomnName().equalsIgnoreCase("time"))
						 timeTrue=true;
					}
						 TableColumes ob2=new TableColumes();
						 ob2=new TableColumes();
						 ob2.setColumnname("dept_subdept");
						 ob2.setDatatype("varchar(255)");
						 ob2.setConstraint("default NULL");
						 tableColume1.add(ob2);
					
					if(upload4!=null && !upload4.equals("") && upload4.equals("buddyDetail"))
					{ 
						boolean status1=cbt.createTable22("buddy_sub_floor_info",tableColume1,connectionSpace);
					}
				}
				List<String> gridListData=null;
				boolean status=false;
				List<String> columnKeyList=null;
				List<String> dropDownValue=new ArrayList<String>();
				List<String> dropDownId=new ArrayList<String>();
				dropDownValue=(List<String>) session.get("dropDownValue");
				dropDownId=(List<String>) session.get("dropDownId");
				ArrayList<InsertDataTable> insertData=null;
				InsertDataTable ob=null;
				if(session.containsKey("gridListData") && session.containsKey("columnKeyList"))
				{
					gridListData = (List<String>) session.get("gridListData");
					columnKeyList = (List<String>) session.get("columnKeyList");
					
					session.remove("gridListData");
					session.remove("columnKeyList");
				}
				if(session.containsKey("dropDownValue"))
				{
					session.remove("dropDownValue");
					session.remove("dropDownId");
				}
				
				int tempIndex=0;

				for(int index=0;index<gridListData.size()/columnKeyList.size();index++)
				{
					insertData=new ArrayList<InsertDataTable>();
					for(int index4Col=0;index4Col<columnKeyList.size();index4Col++)
					{
						if(columnKeyList.get(index4Col).toString().equals("floorcode"))
						{
							if(dropDownValue!=null && dropDownValue.size()>0)
							{
								for(int i=0;i<dropDownValue.size();i++)
								{
									if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
									{
										ob=new InsertDataTable();
										ob.setColName("floorname1");
										ob.setDataName(dropDownId.get(i).toString());
										dropDownId.remove(i);
										dropDownValue.remove(i);
										insertData.add(ob);
										break;
									}
								}
							}
						}
						else if(columnKeyList.get(index4Col).toString().equals("mobno"))
						{
							if(dropDownValue!=null && dropDownValue.size()>0)
							{
								for(int i=0;i<dropDownValue.size();i++)
								{
									if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
									{
										String str[]=dropDownId.get(i).toString().split("#");
										ob=new InsertDataTable();
										ob.setColName("employeeid");
										ob.setDataName(str[0]);
										dropDownId.remove(i);
										dropDownValue.remove(i);
										insertData.add(ob);
										
										ob=new InsertDataTable();
										ob.setColName("dept_subdept");
										ob.setDataName(str[1]);
										insertData.add(ob);
										
										break;
									}
								}
							}
						}
						else if(!columnKeyList.get(index4Col).toString().equals("floorname") && !columnKeyList.get(index4Col).toString().equals("mobno"))
						{
							ob=new InsertDataTable();
							ob.setColName(columnKeyList.get(index4Col).toString());
							ob.setDataName(gridListData.get(tempIndex).toString());
							insertData.add(ob);
						}
						tempIndex++;
					}
					if(userTrue)
					{
						ob=new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);
					}
					if(dateTrue)
					{
						ob=new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
					}
					if(timeTrue)
					{
						ob=new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimewithSeconds());
						insertData.add(ob);
					}
					
					ob=new InsertDataTable();
					ob.setColName("deptHierarchy");
					ob.setDataName(session.get("userDeptLevel").toString());
					insertData.add(ob); 
					
				 status=cbt.insertIntoTable("buddy_sub_floor_info",insertData,connectionSpace);
				
				}
				if(status)
				{
					addActionMessage("Upload Data Successfully Registered!!!");
					returnResult=SUCCESS;
				}
				else {
					addActionMessage("There is some problem in excel!!!!");
				}
			
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
	
}
