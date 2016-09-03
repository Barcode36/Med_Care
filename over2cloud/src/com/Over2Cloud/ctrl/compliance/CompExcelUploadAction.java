package com.Over2Cloud.ctrl.compliance;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CompExcelUploadAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(CompExcelUploadAction.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	public Map<String,Object> columnNameMap=null;
	private Map<String, String> columnMap=null;
	private File excel;
    private String excelContentType;
    private Integer rows=0;
    private Integer total=0;
    private Integer records=0;
    private Integer page=0;
    public List<Object> gridDataList;
    public String userDeptID;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadFormat()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try 
			{
				
				columnMap=new LinkedHashMap<String, String>();
				String orgDetail = (String)session.get("orgDetail");
				String[] orgData = null;
				String orgName="";
				if (orgDetail!=null && !orgDetail.equals("")) 
				{
					orgData = orgDetail.split("#");
					orgName=orgData[0];
				}
				List titleList=new ArrayList();
				String fieldName=null;
				fieldName=new ComplianceUniversalAction().getFieldName("mapped_compl_task_type_config", "compl_task_type_config", "taskType");
				titleList.add(fieldName);
				columnMap.put("taskType",fieldName);
				List<GridDataPropertyView>  taskNameFieldsName=Configuration.getConfigurationData("mapped_compl_task_config",accountID,connectionSpace,"",0,"table_name","compl_task_config");
				for(GridDataPropertyView  gdp:taskNameFieldsName)
				{
					if(!gdp.getColomnName().equalsIgnoreCase("departName") && !gdp.getColomnName().equalsIgnoreCase("taskType") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")  && !gdp.getColomnName().equalsIgnoreCase("status"))
					{
						titleList.add(gdp.getHeadingName());
						columnMap.put(gdp.getColomnName(),gdp.getHeadingName());
					}
				}
				/*List<GridDataPropertyView>  configCompFieldsName=Configuration.getConfigurationData("mapped_compliance_configuration",accountID,connectionSpace,"",0,"table_name","compliance_configuration");
				for(GridDataPropertyView  gdp:configCompFieldsName)
				{
					if(gdp.getColomnName().equalsIgnoreCase("frequency") || gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("dueTime"))
					{
						titleList.add(gdp.getHeadingName());
						columnMap.put(gdp.getColomnName(),gdp.getHeadingName());
					}
				}*/
				excelFileName=new ComplianceExcelDownload().writeDataInExcel(null,titleList,null,"Configure Task",orgName,false,connectionSpace);
				if(excelFileName!=null)
				{
					excelStream=new FileInputStream(excelFileName);
				}
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String readExcelData()
    {
    	String returnResult = ERROR;
		try 
		{
			if (excel != null) 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				columnNameMap=new LinkedHashMap<String, Object>();
				List<String> columnKeyList=new ArrayList<String>();
				Map<String, Object> dataKeyMap=new LinkedHashMap<String, Object>();
				Map<String, String> taskType=new LinkedHashMap<String, String>();
				Map<String, String> tempMap=new LinkedHashMap<String, String>();
				Map<String, String> taskName=new LinkedHashMap<String, String>();
				List<String> gridListData  = new ArrayList<String>();
				FileInputStream inputStream = new FileInputStream(excel);
				List dataList=null;
				List<String> dropDownId=new ArrayList<String>();
				List<String> dropDownValue=new ArrayList<String>();
				int taskTypeCellIndex = 0,taskNameCellIndex= 0,taskBriefCellIndex=0,msgCellIndex=0,completionCellIndex=0,accountCellIndex=0,budgetCellIndex=0;
				downloadFormat();
				try
				{
					
					//userDeptID=new ComplianceUniversalAction().getEmpDetailsByUserName("COMPL",userName)[4];
					Object[] object =null;
					dataList=new ArrayList();
					dataList=cbt.executeAllSelectQuery("SELECT id,taskType FROM compl_task_type",connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
						    object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null && object[1].toString()!="")
							{
								taskType.put(object[1].toString(), object[0].toString());
							}
						}
					}
					dataList=new ArrayList();
					dataList=cbt.executeAllSelectQuery("SELECT id,taskName FROM compliance_task",connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
							object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null && object[1].toString()!="")
							{
								taskName.put(object[1].toString(), object[0].toString());
							}
						}
					}
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
					        			    if(columnMap!=null && columnMap.size()>0)
					        			    {
				        					    Iterator<Entry<String, String>> hiterator=columnMap.entrySet().iterator();
				        					    while (hiterator.hasNext()) 
				        					    {
				        					    	Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				        					    	if(paramPair.getValue()!=null && GRE7.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim()))
				        					    	{
				        							/** 
								 					 *header field matches from header Put Into List and Map Object 
								 					 *
								 					 */	  
				        					    		if(paramPair.getKey().toString().equals("taskType"))
				        					    		{
				        					    			taskTypeCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("taskName"))
				        					    		{
				        					    			taskNameCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("taskBrief"))
				        					    		{
				        					    			taskBriefCellIndex=cellIndex;
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("msg"))
				        					    		{
				        					    			msgCellIndex=cellIndex;
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("accounting"))
				        					    		{
				        					    			accountCellIndex=cellIndex;
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("budgetary"))
				        					    		{
				        					    			budgetCellIndex=cellIndex;
				        					    		}
				        					    		/*else if(paramPair.getKey().toString().equals("completion"))
				        					    		{
				        					    			completionCellIndex=cellIndex;
				        					    		}*/
				        					    		columnKeyList.add(paramPair.getKey());
				        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
				        							}
				        						}
					        			    }
					        		 }
					        		 //Employee Mandatory Field
					        		 if(dataKeyMap.containsKey("taskType") && dataKeyMap.containsKey("taskName"))
					        		 {
					        			setColumnNameMap(dataKeyMap);
					        		 }
					        	}
					        	 else
					        	 {
					        		 int taskTypeMaxId=0;
					        		 int taskNameMaxId=0;
					        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
					        		 {
		        			             if(GRE7.readExcelSheet(row, cellIndex)!=null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
		        			             {
		        			            	 if(tempMap.containsKey(String.valueOf(cellIndex)))
		        			            	 {
		        			            		 if(taskType.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
		        			            		 {
		        			            			 //dropDownId.add(taskType.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		        			            			 //dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
		        			            			 taskTypeMaxId=Integer.valueOf(taskType.get(GRE7.readExcelSheet(row, cellIndex).toString()));
		        			            		 }
		        			            		 else if(taskTypeCellIndex==cellIndex)
		        			            		 {
		        			            			 String id = null,value=null;
		        			            			 Iterator<Entry<String, String>> hiterator=taskType.entrySet().iterator();
						        					 while (hiterator.hasNext()) 
						        					 {
						        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
				        					    		{
						        					    	String query="SELECT id FROM compl_task_type WHERE departName="+userDeptID+" AND taskType='"+GRE7.readExcelSheet(row, cellIndex).toString()+"'";
						        					    	dataList=cbt.executeAllSelectQuery(query,connectionSpace);
						        					    	if(dataList!=null)
						        					    		id=dataList.get(0).toString();
						        					    	value=GRE7.readExcelSheet(row, cellIndex).toString();
				        					    		}
						        					 }
						        					 
						        					 if(id!=null && value!=null && id!="" && value!="")
						        					 {
						        						//dropDownId.add(id);
				        			            		//dropDownValue.add(value);
				        			            		taskTypeMaxId=Integer.valueOf(id);
						        					 }
						        					 else
						        					 {
						        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("taskType", GRE7.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("taskTypeBrief", GRE7.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("departName",userDeptID);
						        						 taskTypeMaxId=saveData("mapped_compl_task_type_config", "compl_task_type_config", "compl_task_type", dataWithColumnName);
						        						 if(taskTypeMaxId>0)
						        						 {
						        							 //dropDownId.add(String.valueOf(taskTypeMaxId));
						        			            	 //dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
						        			            	 taskType.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(taskTypeMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            		 if(taskName.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
		        			            		 {
		        			            			 taskNameMaxId=Integer.valueOf(taskName.get(GRE7.readExcelSheet(row, cellIndex).toString()));
		        			            			 dropDownId.add(taskName.get(GRE7.readExcelSheet(row, cellIndex).toString()));
		        			            			 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
		        			            		 }
		        			            		 else if(taskTypeMaxId>0 && taskNameCellIndex==cellIndex)
		        			            		 {
		        			            			 String idddd = null,value=null;
		        			            			 Iterator<Entry<String, String>> hiterator=taskName.entrySet().iterator();
						        					 while (hiterator.hasNext()) 
						        					 {
						        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
				        					    		{
						        					    	String query="SELECT id FROM compliance_task WHERE departName="+userDeptID+" AND taskName='"+GRE7.readExcelSheet(row, cellIndex).toString()+"'";
						        					    	dataList=cbt.executeAllSelectQuery(query,connectionSpace);
						        					    	if(dataList!=null)
						        					    		idddd=dataList.get(0).toString();
						        					    	
						        					    	value=GRE7.readExcelSheet(row, cellIndex).toString();
				        					    		}
						        					 }
						        					 if(idddd!=null && value!=null)
						        					 {
						        						dropDownId.add(idddd);
				        			            		dropDownValue.add(value);
						        					 }
						        					 else
						        					 {
			        			            			 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("taskName", GRE7.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("taskType",String.valueOf(taskTypeMaxId));
						        						 dataWithColumnName.put("taskBrief",GRE7.readExcelSheet(row, taskBriefCellIndex).toString());
						        						 dataWithColumnName.put("msg",GRE7.readExcelSheet(row, msgCellIndex).toString());
						        						 dataWithColumnName.put("accounting",GRE7.readExcelSheet(row, accountCellIndex).toString());
						        						 dataWithColumnName.put("budgetary",GRE7.readExcelSheet(row, budgetCellIndex).toString());
						        						 //dataWithColumnName.put("completion",GRE7.readExcelSheet(row, completionCellIndex).toString());
						        						 dataWithColumnName.put("departName",userDeptID);
						        						 
						        						 taskNameMaxId=saveData("mapped_compl_task_config", "compl_task_config", "compliance_task", dataWithColumnName);
						        						 
						        						 if(taskNameMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(taskNameMaxId));
						        			            	 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
						        			            	 taskName.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(taskNameMaxId));
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
				        			    if(columnMap!=null && columnMap.size()>0)
				        			    {
			        					    Iterator<Entry<String, String>> hiterator=columnMap.entrySet().iterator();
			        					    while (hiterator.hasNext()) 
			        					    {
			        					    	Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
			        					    	if(paramPair.getValue()!=null && GRBE.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim()))
			        					    	{
			        					    		if(paramPair.getKey().toString().equals("taskType"))
			        					    		{
			        					    			taskTypeCellIndex=cellIndex;
			        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
			        					    		}
			        					    		else if(paramPair.getKey().toString().equals("taskName"))
			        					    		{
			        					    			taskNameCellIndex=cellIndex;
			        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
			        					    		}
			        					    		else if(paramPair.getKey().toString().equals("taskBrief"))
			        					    		{
			        					    			taskBriefCellIndex=cellIndex;
			        					    		}
			        					    		else if(paramPair.getKey().toString().equals("msg"))
			        					    		{
			        					    			msgCellIndex=cellIndex;
			        					    		}
			        					    		/*else if(paramPair.getKey().toString().equals("completion"))
			        					    		{
			        					    			completionCellIndex=cellIndex;
			        					    		}*/
			        					    		else if(paramPair.getKey().toString().equals("accounting"))
			        					    		{
			        					    			accountCellIndex=cellIndex;
			        					    		}
			        					    		else if(paramPair.getKey().toString().equals("budgetary"))
			        					    		{
			        					    			budgetCellIndex=cellIndex;
			        					    		}
			        					    		columnKeyList.add(paramPair.getKey());
			        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
			        							}
			        						}
				        			    }
					        		 }
					        		 //Employee Mandatory Field
					        		 if(dataKeyMap.containsKey("taskType") && dataKeyMap.containsKey("taskName"))
					        		 {
					        			setColumnNameMap(dataKeyMap);
					        		 }
					        	}
					        	 else
					        	 {
					        		 int taskTypeMaxId=0;
					        		 int taskNameMaxId=0;
					        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
					        		 {
		        			             if(GRBE.readExcelSheet(row, cellIndex)!=null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
		        			             {
		        			            	 if(tempMap.containsKey(String.valueOf(cellIndex)))
		        			            	 {
		        			            		 if(taskType.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
		        			            		 {
		        			            			 //dropDownId.add(taskType.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		        			            			 //dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
		        			            			 taskTypeMaxId=Integer.valueOf(taskType.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		        			            		 }
		        			            		 else if(taskTypeCellIndex==cellIndex)
		        			            		 {
		        			            			 String id = null,value=null;
		        			            			 Iterator<Entry<String, String>> hiterator=taskType.entrySet().iterator();
						        					 while (hiterator.hasNext()) 
						        					 {
						        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
				        					    		{
						        					    	String query="SELECT id FROM compl_task_type WHERE departName="+userDeptID+" AND taskType='"+GRBE.readExcelSheet(row, cellIndex).toString()+"'";
						        					    	
						        					    	dataList=cbt.executeAllSelectQuery(query,connectionSpace);
						        					    	if(dataList!=null)
						        					    		id=dataList.get(0).toString();
						        					    	value=GRBE.readExcelSheet(row, cellIndex).toString();
				        					    		}
						        					 }
						        					 if(id!=null && value!=null && id!="" && value!="")
						        					 {
						        						//dropDownId.add(id);
				        			            		//dropDownValue.add(value);
				        			            		taskTypeMaxId=Integer.valueOf(id);
						        					 }
						        					 else
						        					 {
						        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("taskType", GRBE.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("taskTypeBrief", GRBE.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("departName",userDeptID);
						        						 System.out.println("taskType "+GRBE.readExcelSheet(row, cellIndex).toString());
						        						 System.out.println("taskTypeBrief "+GRBE.readExcelSheet(row, cellIndex).toString());
						        						 System.out.println("departName "+userDeptID);
						        						 taskTypeMaxId=saveData("mapped_compl_task_type_config", "compl_task_type_config", "compl_task_type", dataWithColumnName);
						        						 if(taskTypeMaxId>0)
						        						 {
						        							 //dropDownId.add(String.valueOf(taskTypeMaxId));
						        			            	 //dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
						        			            	 taskType.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(taskTypeMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            		 if(taskName.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
		        			            		 {
		        			            			 String query="SELECT id FROM compliance_task WHERE departName="+userDeptID+" AND taskType="+taskTypeMaxId+" AND taskName='"+GRBE.readExcelSheet(row, cellIndex).toString()+"'";
				        					    	 System.out.println(query);
		        			            			 dataList=cbt.executeAllSelectQuery(query,connectionSpace);
				        					    	 if(dataList!=null && dataList.size()>0)
				        					    	 {
				        					    		//idddd=dataList.get(0).toString();
				        					    		//taskNameMaxId=Integer.parseInt(idddd);
				        					    		taskNameMaxId=Integer.valueOf(taskName.get(GRBE.readExcelSheet(row, cellIndex).toString()));
			        			            			dropDownId.add(taskName.get(GRBE.readExcelSheet(row, cellIndex).toString()));
			        			            			dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
				        					    	 }
				        					    	 else
				        					    	 {
				        					    		 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("taskName", GRBE.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("taskType",String.valueOf(taskTypeMaxId));
						        						 dataWithColumnName.put("taskBrief",GRBE.readExcelSheet(row, taskBriefCellIndex).toString());
						        						 dataWithColumnName.put("msg",GRBE.readExcelSheet(row, msgCellIndex).toString());
						        						 dataWithColumnName.put("accounting",GRBE.readExcelSheet(row, accountCellIndex).toString());
						        						 dataWithColumnName.put("budgetary",GRBE.readExcelSheet(row, budgetCellIndex).toString());
						        						 //dataWithColumnName.put("completion",GRBE.readExcelSheet(row, completionCellIndex).toString());
						        						 dataWithColumnName.put("departName",userDeptID);
						        						 
						        						 taskNameMaxId=saveData("mapped_compl_task_config", "compl_task_config", "compliance_task", dataWithColumnName);
						        						 if(taskNameMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(taskNameMaxId));
						        			            	 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
						        			            	 taskName.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(taskNameMaxId));
						        						 }
				        					    	 }
		        			            		 }
		        			            		 else if(taskTypeMaxId>0 && taskNameCellIndex==cellIndex)
		        			            		 {
		        			            			 String idddd = null,value=null;
		        			            			 Iterator<Entry<String, String>> hiterator=taskName.entrySet().iterator();
						        					 while (hiterator.hasNext()) 
						        					 {
						        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
				        					    		{
						        					    	String query="SELECT id FROM compliance_task WHERE departName="+userDeptID+" AND taskType="+taskTypeMaxId+" AND taskName='"+GRBE.readExcelSheet(row, cellIndex).toString()+"'";
						        					    	dataList=cbt.executeAllSelectQuery(query,connectionSpace);
						        					    	if(dataList!=null)
						        					    		idddd=dataList.get(0).toString();
						        					    	
						        					    	value=GRBE.readExcelSheet(row, cellIndex).toString();
				        					    		}
						        					 }
						        					 if(idddd!=null && value!=null)
						        					 {
						        						dropDownId.add(idddd);
				        			            		dropDownValue.add(value);
						        					 }
						        					 else
						        					 {
						        						 String query="SELECT id FROM compliance_task WHERE departName="+userDeptID+" AND taskType="+taskTypeMaxId+" AND taskName='"+GRBE.readExcelSheet(row, cellIndex).toString()+"'";
					        					    	 dataList=cbt.executeAllSelectQuery(query,connectionSpace);
					        					    	 if(dataList!=null && dataList.size()>0)
					        					    	 {
					        					    		idddd=dataList.get(0).toString();
					        					    		taskNameMaxId=Integer.parseInt(idddd);
					        					    	 }
					        					    	 else
					        					    	 {
					        					    		 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
							        						 dataWithColumnName.put("taskName", GRBE.readExcelSheet(row, cellIndex).toString());
							        						 dataWithColumnName.put("taskType",String.valueOf(taskTypeMaxId));
							        						 dataWithColumnName.put("taskBrief",GRBE.readExcelSheet(row, taskBriefCellIndex).toString());
							        						 dataWithColumnName.put("msg",GRBE.readExcelSheet(row, msgCellIndex).toString());
							        						 dataWithColumnName.put("accounting",GRBE.readExcelSheet(row, accountCellIndex).toString());
							        						 dataWithColumnName.put("budgetary",GRBE.readExcelSheet(row, budgetCellIndex).toString());
							        						 //dataWithColumnName.put("completion",GRBE.readExcelSheet(row, completionCellIndex).toString());
							        						 dataWithColumnName.put("departName",userDeptID);
							        						 
							        						 taskNameMaxId=saveData("mapped_compl_task_config", "compl_task_config", "compliance_task", dataWithColumnName);
					        					    	 }
						        						 
						        						 if(taskNameMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(taskNameMaxId));
						        			            	 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
						        			            	 taskName.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(taskNameMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            	 }
		        			            	 gridListData.add(GRBE.readExcelSheet(row, cellIndex).toString()); 
		        			             }  
					        		 }
					        	 }
					         }
						}
					}
					/*if (dropDownId!=null && dropDownId.size() > 0) 
					{
						session.put("dropDownId", dropDownId);
						session.put("dropDownValue", dropDownValue);
				    }
					if (gridListData!=null && gridListData.size() > 0) 
					{
						session.put("gridListData", gridListData);
						session.put("columnKeyList", columnKeyList);
				    }*/
					if(session.containsKey("columnMap"))
					{
						session.remove("columnMap");
					}
					addActionMessage("Data Upload Successfully.");
					returnResult=SUCCESS;
				}
				catch(Exception e)
				{
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
							"Problem in Over2Cloud  method readExcelData of class "+getClass(), e);
					e.printStackTrace();
					addActionMessage("Oops. there are some problem in data");
					returnResult = ERROR;
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
								if(fieldsArrayss[k].toString().matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
								{
									one.put(fieldsArray[i].toString().trim(),DateUtil.convertHyphenDateToUSFormat(fieldsArrayss[k].toString()));
								}
								//for Date Check With (DD/MM/YYYY OR D/M/YY)
								else if(fieldsArrayss[k].toString().matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
								{
									one.put(fieldsArray[i].toString().trim(),DateUtil.convertSlashDateToUSFormat(fieldsArrayss[k].toString()));
								}
								else
								{
									one.put(fieldsArray[i].toString().trim(), fieldsArrayss[k].toString());
								}
								i++;
								if(i==fieldsArray.length)
								{
									i=0;
									tempList.add(one);
									one = new HashMap<String, Object>();
								}
							}
						}
						setGridDataList(tempList);
				}
				setRecords(gridDataList.size());
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			}
			returnResult = SUCCESS;
		}
		catch (Exception e) 
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewConfirmationData of class "+getClass(), e);
			e.printStackTrace();
		}
		return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public String saveSelectData111()
    {
    	String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				InsertDataTable ob=null;
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				List <TableColumes> tableColume=new ArrayList<TableColumes>();
				
				TableColumes ob1=new TableColumes();
				
				ob1=new TableColumes();
				ob1.setColumnname("taskName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("taskBrief");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("msg");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("frequency");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("completion");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("dueDate");
				ob1.setDatatype("varchar(15)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("dueTime");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("configStatus");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				boolean status=cbt.createTable22("comp_excelupload_data",tableColume,connectionSpace);
				
				List<String> gridListData = (List<String>) session.get("gridListData");
				List<String> columnKeyList = (List<String>) session.get("columnKeyList");
				List<String> dropDownValue=(List<String>) session.get("dropDownValue");
				List<String> dropDownId=(List<String>) session.get("dropDownId");
				session.remove("gridListData");
				session.remove("columnKeyList");
				session.remove("dropDownValue");
				session.remove("dropDownId");
				
				int tempIndex=0;
				for(int index=0;index<gridListData.size()/columnKeyList.size();index++)
				{
					insertData=new ArrayList<InsertDataTable>();
					for(int index4Col=0;index4Col<columnKeyList.size();index4Col++)
					{
						if(columnKeyList.get(index4Col).toString().equals("taskName"))
						{
							for(int i=0;i<dropDownValue.size();i++)
							{
								if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
								{
									ob=new InsertDataTable();
									ob.setColName("taskName");
									ob.setDataName(dropDownId.get(i).toString());
									insertData.add(ob);
									dropDownId.remove(i);
									dropDownValue.remove(i);
									break;
								}
							}
						}
						else if(!columnKeyList.get(index4Col).toString().equals("taskType"))
						{
							if(gridListData.get(tempIndex)!=null)
							{
								ob=new InsertDataTable();
								ob.setColName(columnKeyList.get(index4Col).toString());
								//for Date Check With (YYYY-DD-MM OR YY-D-M)
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
									ob.setDataName(gridListData.get(tempIndex).toString());
								}
								insertData.add(ob);
							}
						}
						tempIndex++;
					}
					ob=new InsertDataTable();
					ob.setColName("configStatus");
					ob.setDataName("0");
					insertData.add(ob);
					cbt.insertIntoTable("comp_excelupload_data",insertData,connectionSpace);
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
	
	public int saveData(String mappedTable,String configTable,String dataTable,Map<String,String> dataWithColumnName)
	{

		List<GridDataPropertyView> statusColName=null;
		int maxId=0;
		statusColName=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",configTable);
		if(statusColName!=null&&statusColName.size()>0)
		{
			boolean userTrue=false;
			boolean dateTrue=false;
			boolean timeTrue=false;
			InsertDataTable ob=null;
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			//create table query based on configuration
			List <TableColumes> tableColume=new ArrayList<TableColumes>();
			
			for(GridDataPropertyView gdp:statusColName)
			{
				 TableColumes ob1=null;
				 ob1=new TableColumes();
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
			
			cbt.createTable22(dataTable,tableColume,connectionSpace);
			Iterator<Entry<String, String>> hiterator=dataWithColumnName.entrySet().iterator();
			while (hiterator.hasNext()) 
			{
				    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				    ob=new InsertDataTable();
					ob.setColName(paramPair.getKey().toString());
					ob.setDataName(paramPair.getValue().toString());
					insertData.add(ob);
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
				 ob.setDataName(DateUtil.getCurrentTimeHourMin());
				 insertData.add(ob);
			}
			maxId=cbt.insertDataReturnId(dataTable,insertData,connectionSpace);
		}
		return maxId;
	}
	@SuppressWarnings("rawtypes")
	public String isExist(String tableName, List<String> colmName,Map<String, Object>wherClause)
	{
		String id=null;
		StringBuilder selectTableData = new StringBuilder("");  
		if(colmName!=null && colmName.size()>0)
		{
			selectTableData.append("select ");
			int i=1;
			for(String H:colmName)
			{
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " +tableName);
		}
		if(wherClause!=null)
		{
			if(wherClause.size()>0)
			{
				selectTableData.append(" where ");
			}
			int size=1;
			Set set =wherClause.entrySet(); 
			Iterator i = set.iterator();
			while(i.hasNext())
			{ 
				Map.Entry me = (Map.Entry)i.next();
				if(size<wherClause.size())
					selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"' and ");
				else
					selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
				size++;
			} 
		}
		List data=cbt.executeAllSelectQuery(selectTableData.toString(),connectionSpace);
		try
		{
			if(data!=null && data.size()>0)
			{
				id=data.get(0).toString();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return id;
		
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

	public Map<String, Object> getColumnNameMap() {
		return columnNameMap;
	}

	public void setColumnNameMap(Map<String, Object> columnNameMap) {
		this.columnNameMap = columnNameMap;
	}

	public Map<String, String> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
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

	public List<Object> getGridDataList() {
		return gridDataList;
	}

	public void setGridDataList(List<Object> gridDataList) {
		this.gridDataList = gridDataList;
	}

	public String getUserDeptID()
	{
		return userDeptID;
	}

	public void setUserDeptID(String userDeptID)
	{
		this.userDeptID = userDeptID;
	}
	
	
}
