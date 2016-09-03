package com.Over2Cloud.ctrl.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.universal.UniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class EmployeeExcelUploadCtrl extends ActionSupport
{
	static final Log log = LogFactory.getLog(EmployeeExcelUploadCtrl.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String orgLevel = (String)session.get("orgnztnlevel");
	String orgId = (String)session.get("mappedOrgnztnId");
	String accountID=(String)session.get("accountid");
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	String deptHierarchy =null;
	boolean checkdept = false;
	private String tableName=null;
	private String tableAllis=null;
	private String excelName=null;
	private String downloadType=null;
	private String download4=null;
	private String[] columnNames;
	private List<ConfigurationUtilBean> columnMap2=null;
	private Map<String, String> columnMap=null;
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
    private Map<Integer, String> deptList = null;
    private Map<Integer, String> subDeptList = null;
    private Map<Integer, String> desigList= null;
    private String subdept_deptId;
    private String deptId;
    private String subdept;
    private String designationname;
	
	
	public String getColumn4Download()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(download4!=null && download4.equals("empBasicDetail"))
				{
					returnResult=getColumnName("mapped_employee_basic_configuration","employee_basic_configuration",tableAllis);
					excelName="Employee Basic Detail";
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getColumn4Download of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName,String basicTableName,String allias)
	{
			String returnResult=ERROR;
			try
			{
				List<GridDataPropertyView> columnList=Configuration.getConfigurationData(mappedTableName,accountID,connectionSpace,"",0,"table_name",basicTableName);
				columnMap2=new ArrayList<ConfigurationUtilBean>();
				columnMap=new LinkedHashMap<String, String>();
				if(columnList!=null&&columnList.size()>0)
				{
					String firstValue,secondValue=null;
					if(downloadType!=null && downloadType.equals("downloadExcel"))
					{
					}
					else
					{
						for(GridDataPropertyView  gdp:columnList)
						{
							if(gdp.getColomnName()!=null)
							{
								ConfigurationUtilBean obj=new ConfigurationUtilBean();
								if(gdp.getColomnName().equals("subdept") && session.get("userDeptLevel").toString().equals("2"))
								{
									firstValue=new UniversalAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName");
									secondValue=new UniversalAction().getFieldName("mapped_subdeprtmentconf", "subdeprtmentconf", "subdeptname");
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey("deptName");
									obj.setValue(firstValue);
									columnMap2.add(obj);
									columnMap.put("deptName",firstValue);
									obj=new ConfigurationUtilBean();
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey("subdept");
									obj.setValue(secondValue);
									columnMap2.add(obj);
									columnMap.put("subdept",secondValue);
								}
								else if(gdp.getColomnName().equals("deptName") && session.get("userDeptLevel").toString().equals("1"))
								{
									firstValue=new UniversalAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName");
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey("deptName");
									obj.setValue(firstValue);
									columnMap2.add(obj);
									columnMap.put("deptName",firstValue);
								}
								else if(gdp.getColomnName().equals("designation"))
								{
									firstValue=new UniversalAction().getFieldName("mapped_designation_confg", "designation_configuration", "designationname");
									secondValue=new UniversalAction().getFieldName("mapped_designation_confg", "designation_configuration", "levelofhierarchy");
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey("designation");
									obj.setValue(firstValue);
									columnMap2.add(obj);
									columnMap.put("designation",firstValue);
									obj=new ConfigurationUtilBean();
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey("levelofhierarchy");
									obj.setValue(secondValue);
									columnMap2.add(obj);
									columnMap.put("levelofhierarchy",secondValue);
								}
								else if(!gdp.getColomnName().equals("userName") && !gdp.getColomnName().equals("date") && !gdp.getColomnName().equals("time"))
								{
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey(gdp.getColomnName());
									obj.setValue(gdp.getHeadingName());
									columnMap2.add(obj);
									columnMap.put(gdp.getColomnName(), gdp.getHeadingName());
								}
							}
						}
					}
					
					
					Iterator<Entry<String, String>> hiterator=columnMap.entrySet().iterator();
				    while (hiterator.hasNext()) 
				    {
				    	Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				    }
					
					if(columnMap!=null && columnMap.size()>0)
					{
						session.put("columnMap", columnMap);
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getColumnName of class "+getClass(), e);
				e.printStackTrace();
			}
			return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String downloadExcel()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		
		if(sessionFlag)
		{
			try
			{
				List keyList=new ArrayList();
				List titleList=new ArrayList();
				if(columnNames!=null && columnNames.length>0)
				{
					keyList=Arrays.asList(columnNames);
					Map<String, String> tempMap=new LinkedHashMap<String, String>();
					tempMap=(Map<String, String>) session.get("columnMap");
					List dataList=null;
					for(int index=0;index<keyList.size();index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if(downloadType!=null && downloadType.equals("uploadExcel"))
					{
						returnResult=new GenericWriteExcel().createExcel(session.get("orgname").toString(),excelName, dataList, titleList, null, excelName);
					}
					returnResult=SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method downloadExcel of class "+getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String readExcelData1111()
    {
    	String returnResult = ERROR;
		try 
		{
			if(upload4.equals("empBasicDetail"))
			{
				if(getColumnName("mapped_employee_basic_configuration","employee_basic_configuration","")!=null)
				{
					returnResult="empBasicConfirm";
					columnMap=(Map<String, String>) session.get("columnMap");
				}
			}
			if (excel != null) 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				columnNameMap=new LinkedHashMap<String, Object>();
				List<String> columnKeyList=new ArrayList<String>();
				Map<String, Object> dataKeyMap=new LinkedHashMap<String, Object>();
				Map<String, String> deptMap=new LinkedHashMap<String, String>();
				Map<String, String> tempMap=new LinkedHashMap<String, String>();
				Map<String, String> subDeptMap=new LinkedHashMap<String, String>();
				Map<String, String> designationMap=new LinkedHashMap<String, String>();
				List<String> gridListData  = new ArrayList<String>();
				//InputStream inputStream = new FileInputStream(excel);
				InputStream inputStream=new FileInputStream(excel);
				List dataList=null;
				List<String> dropDownId=new ArrayList<String>();
				List<String> dropDownValue=new ArrayList<String>();
				int deptCellIndex = 0,subDeptCellIndex= 0,desigCellIndex= 0,levelCellIndex = 0;
				try
				{
					if(session.get("userDeptLevel").toString().equals("1"))
					{
						dataList=new ArrayList();
						dataList=cbt.executeAllSelectQuery("SELECT id,deptName FROM department",connectionSpace);
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null && object[1]!=null && object[1].toString()!="")
								{
									deptMap.put(object[1].toString(), object[0].toString());
								}
							}
						}
					}
					else if(session.get("userDeptLevel").toString().equals("2"))
					{
						dataList=new ArrayList();
						String query="SELECT dept.id,dept.deptName,sdept.id as sdeptid,sdept.subdeptname FROM department AS dept" +
								" INNER JOIN subdepartment AS sdept ON sdept.deptid=dept.id";
						
						dataList=cbt.executeAllSelectQuery(query,connectionSpace);
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null && object[1]!=null && object[1].toString()!="" && object[2]!=null && object[3]!=null && object[3].toString()!="")
								{
									deptMap.put(object[1].toString(), object[0].toString());
									subDeptMap.put(object[3].toString(), object[2].toString());
								}
							}
						}
					}
					
					dataList=new ArrayList();
					dataList=cbt.executeAllSelectQuery("SELECT id,designationname FROM designation",connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null && object[1].toString()!="")
							{
								designationMap.put(object[1].toString(), object[0].toString());
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
				        					    		if(paramPair.getKey().toString().equals("deptName"))
				        					    		{
				        					    			deptCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("subdept"))
				        					    		{
				        					    			subDeptCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("designation"))
				        					    		{
				        					    			desigCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("levelofhierarchy"))
				        					    		{
				        					    			levelCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		columnKeyList.add(paramPair.getKey());
				        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
				        							}
				        						}
					        			    }
					        		 }
					        		 //Employee Mandatory Field
					        		 if(dataKeyMap.containsKey("empName") && dataKeyMap.containsKey("mobOne") && dataKeyMap.containsKey("empId") && dataKeyMap.containsKey("deptName") && dataKeyMap.containsKey("designation"))
					        		 {
					        			setColumnNameMap(dataKeyMap);
					        		 }
					        	}
					        	 else
					        	 {
					        		 int deptMaxId=0;
					        		 int subDeptMaxId=0;
					        		 int desigMaxId=0;
					        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
					        		 {
		        			             if(GRE7.readExcelSheet(row, cellIndex)!=null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
		        			             {
			        			             /** 
						 					 * add source code at here and check what column value you have get
						 					 * 
						 					 * if you have drop type value just check it and pick id of that value and 
						 					 *  and add in List Object ! 
						 					 *
						 					 */	
		        			            	 if(tempMap.containsKey(String.valueOf(cellIndex)))
		        			            	 {
		        			            		 if(deptMap.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
		        			            		 {
		        			            			 dropDownId.add(deptMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
		        			            			 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
		        			            			 deptMaxId=Integer.valueOf(deptMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
		        			            		 }
		        			            		 else if(deptCellIndex==cellIndex)
		        			            		 {
		        			            			 String id = null,value=null;
		        			            			 Iterator<Entry<String, String>> hiterator=deptMap.entrySet().iterator();
						        					 while (hiterator.hasNext()) 
						        					 {
						        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
				        					    		{
						        					    	String query="SELECT id FROM department WHERE deptName='"+GRE7.readExcelSheet(row, cellIndex).toString()+"'";
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
				        			            		deptMaxId=Integer.valueOf(id);
						        					 }
						        					 else
						        					 {
						        						 String levelOforganization = null,level2 = null;
						        						 List level2Tenp=new ArrayList<String>();
						        					     level2Tenp.add("id");
						        						 level2Tenp.add("locCompanyName");
						        						 List<String>colname=new ArrayList<String>();
						        						 colname.add("orgLevel");
						        						 colname.add("levelName");
						        						 level2Tenp=cbt.viewAllDataEitherSelectOrAll("organization_level2",level2Tenp,connectionSpace);
						        						 List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
						        							
						        						 //getting the current logined user organization level
						        						 if(orgntnlevel!=null)
						        						 {
						        							for (Object c : orgntnlevel) {
						        									Object[] dataC = (Object[]) c;
						        									levelOforganization= dataC[0].toString();
						        							}
						        						 }
						        						 else
						        						 {
						        								levelOforganization="2";
						        						 }
						        						 
						        						 if(level2Tenp!=null)
						        						 {
						        								for (Object c : level2Tenp) {
						        									Object[] dataC = (Object[]) c;
						        									level2=dataC[0].toString();
						        								}
						        						 }
						        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("deptName", GRE7.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("mappedOrgnztnId", level2);
						        						 dataWithColumnName.put("orgnztnlevel", levelOforganization);
						        						 
						        						 deptMaxId=new EmpExcelUploadHelper().saveData("mapped_dept_config_param", "dept_config_param", "department", dataWithColumnName);
						        						 if(deptMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(deptMaxId));
						        			            	 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
						        			            	 deptMap.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(deptMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            		 
		        			            		 if(deptMaxId>0 && subDeptCellIndex==cellIndex)
		        			            		 {

		        			            			 String idddd = null,value=null;
		        			            			 String query="SELECT id FROM subdepartment WHERE subdeptname='"+GRE7.readExcelSheet(row, cellIndex).toString()+"' AND deptid="+deptMaxId;
					        					     dataList=cbt.executeAllSelectQuery(query,connectionSpace);
					        					     if(dataList!=null && dataList.size()>0)
					        					     { 
					        					    		idddd=dataList.get(0).toString();
					        					    		value=GRE7.readExcelSheet(row, cellIndex).toString();
					        					     }
						        					 if(idddd!=null && value!=null)
						        					 {
						        						dropDownId.add(idddd);
				        			            		dropDownValue.add(value);
				        			            		subDeptMaxId=Integer.valueOf(idddd);
						        					 }
						        					 else
						        					 {
						        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("subdeptname", GRE7.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("deptid",String.valueOf(deptMaxId));
						        						 
						        						 subDeptMaxId=new EmpExcelUploadHelper().saveData("mapped_subdeprtmentconf", "subdeprtmentconf", "subdepartment", dataWithColumnName);
						        						 if(subDeptMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(subDeptMaxId));
						        			            	 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
						        			            	 subDeptMap.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(subDeptMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            		 
		        			            		 if(desigCellIndex==cellIndex)
		        			            		 {
			        			            		 List<String> colmName=new ArrayList<String>();
			        			            		 colmName.add("id");
			        			            		 Map<String, Object> wherClause =new LinkedHashMap<String, Object>();
			        			            		 wherClause.put("designationname", GRE7.readExcelSheet(row, cellIndex).toString());
			        			            		 wherClause.put("mappedOrgnztnId", subDeptMaxId);
			        			            		 String desId=new EmpExcelUploadHelper().isExist("designation",colmName,wherClause);
			        			            		 if(desId!=null && desId!="")
			        			            		 {
			        			            			 dropDownId.add(desId);
			        			            			 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
			        			            		 }
			        			            		 else if(subDeptMaxId >0 && desigCellIndex==cellIndex)
			        			            		 {
			        			            			 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("designationname",GRE7.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("mappedOrgnztnId",String.valueOf(subDeptMaxId));
						        						 dataWithColumnName.put("orgnztnlevel",session.get("userDeptLevel").toString());
						        						 dataWithColumnName.put("levelofhierarchy",GRE7.readExcelSheet(row, levelCellIndex).toString());
						        						 dataWithColumnName.put("parallelshare","2");
						        						 
						        						 desigMaxId=new EmpExcelUploadHelper().saveData("mapped_designation_confg", "designation_configuration", "designation", dataWithColumnName);
						        						 if(desigMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(desigMaxId));
						        			            	 dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
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
				        							/** 
								 					 *header field matches from header Put Into List and Map Object 
								 					 *
								 					 */	  
				        					    		if(paramPair.getKey().toString().equals("deptName"))
				        					    		{
				        					    			deptCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("subdept"))
				        					    		{
				        					    			subDeptCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("designation"))
				        					    		{
				        					    			desigCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		else if(paramPair.getKey().toString().equals("levelofhierarchy"))
				        					    		{
				        					    			levelCellIndex=cellIndex;
				        					    			tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
				        					    		}
				        					    		columnKeyList.add(paramPair.getKey());
				        					    		dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
				        							}
				        						}
					        			    }
					        		 }
					        		 //Employee Mandatory Field
					        		 if(dataKeyMap.containsKey("empName") && dataKeyMap.containsKey("mobOne") && dataKeyMap.containsKey("empId") && dataKeyMap.containsKey("deptName") && dataKeyMap.containsKey("designation"))
					        		 {
					        			setColumnNameMap(dataKeyMap);
					        		 }
					        	}
					        	 else
					        	 {
					        		 int deptMaxId=0;
					        		 int subDeptMaxId=0;
					        		 int desigMaxId=0;
					        		 for(int cellIndex = 0; cellIndex < cellNo; cellIndex++)
					        		 {
		        			             if(GRBE.readExcelSheet(row, cellIndex)!=null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
		        			             {
			        			             /** 
						 					 * add source code at here and check what column value you have get
						 					 * 
						 					 * if you have drop type value just check it and pick id of that value and 
						 					 *  and add in List Object ! 
						 					 *
						 					 */	
		        			            	 if(tempMap.containsKey(String.valueOf(cellIndex)))
		        			            	 {
		        			            		 if(deptMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
		        			            		 {
		        			            			 dropDownId.add(deptMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		        			            			 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
		        			            			 deptMaxId=Integer.valueOf(deptMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
		        			            		 }
		        			            		 else if(deptCellIndex==cellIndex)
		        			            		 {
		        			            			 String id = null,value=null;
		        			            			 Iterator<Entry<String, String>> hiterator=deptMap.entrySet().iterator();
						        					 while (hiterator.hasNext()) 
						        					 {
						        					    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						        					    if(paramPair.getKey().toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
				        					    		{
						        					    	String query="SELECT id FROM department WHERE deptName='"+GRBE.readExcelSheet(row, cellIndex).toString()+"'";
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
				        			            		deptMaxId=Integer.valueOf(id);
						        					 }
						        					 else
						        					 {
						        						 String levelOforganization = null,level2 = null;
						        						 List level2Tenp=new ArrayList<String>();
						        					     level2Tenp.add("id");
						        						 level2Tenp.add("locCompanyName");
						        						 List<String>colname=new ArrayList<String>();
						        						 colname.add("orgLevel");
						        						 colname.add("levelName");
						        						 level2Tenp=cbt.viewAllDataEitherSelectOrAll("organization_level2",level2Tenp,connectionSpace);
						        						 List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
						        							
						        						 //getting the current logined user organization level
						        						 if(orgntnlevel!=null)
						        						 {
						        							for (Object c : orgntnlevel) {
						        									Object[] dataC = (Object[]) c;
						        									levelOforganization= dataC[0].toString();
						        							}
						        						 }
						        						 else
						        						 {
						        								levelOforganization="2";
						        						 }
						        						 
						        						 if(level2Tenp!=null)
						        						 {
						        								for (Object c : level2Tenp) {
						        									Object[] dataC = (Object[]) c;
						        									level2=dataC[0].toString();
						        								}
						        						 }
						        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("deptName", GRBE.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("mappedOrgnztnId", level2);
						        						 dataWithColumnName.put("orgnztnlevel", levelOforganization);
						        						 
						        						 deptMaxId=new EmpExcelUploadHelper().saveData("mapped_dept_config_param", "dept_config_param", "department", dataWithColumnName);
						        						 if(deptMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(deptMaxId));
						        			            	 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
						        			            	 deptMap.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(deptMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            		 
		        			            		 if(deptMaxId>0 && subDeptCellIndex==cellIndex)
		        			            		 {

		        			            			 String idddd = null,value=null;
		        			            			 String query="SELECT id FROM subdepartment WHERE subdeptname='"+GRBE.readExcelSheet(row, cellIndex).toString()+"' AND deptid="+deptMaxId;
					        					     dataList=cbt.executeAllSelectQuery(query,connectionSpace);
					        					     if(dataList!=null && dataList.size()>0)
					        					     { 
					        					    		idddd=dataList.get(0).toString();
					        					    		value=GRBE.readExcelSheet(row, cellIndex).toString();
					        					     }
						        					 if(idddd!=null && value!=null)
						        					 {
						        						dropDownId.add(idddd);
				        			            		dropDownValue.add(value);
				        			            		subDeptMaxId=Integer.valueOf(idddd);
						        					 }
						        					 else
						        					 {
						        						 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("subdeptname", GRBE.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("deptid",String.valueOf(deptMaxId));
						        						 
						        						 subDeptMaxId=new EmpExcelUploadHelper().saveData("mapped_subdeprtmentconf", "subdeprtmentconf", "subdepartment", dataWithColumnName);
						        						 if(subDeptMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(subDeptMaxId));
						        			            	 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
						        			            	 subDeptMap.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(subDeptMaxId));
						        						 }
						        					 }
		        			            		 }
		        			            		 
		        			            		 if(desigCellIndex==cellIndex)
		        			            		 {
			        			            		 List<String> colmName=new ArrayList<String>();
			        			            		 colmName.add("id");
			        			            		 Map<String, Object> wherClause =new LinkedHashMap<String, Object>();
			        			            		 wherClause.put("designationname", GRBE.readExcelSheet(row, cellIndex).toString());
			        			            		 wherClause.put("mappedOrgnztnId", subDeptMaxId);
			        			            		 String desId=new EmpExcelUploadHelper().isExist("designation",colmName,wherClause);
			        			            		 if(desId!=null && desId!="")
			        			            		 {
			        			            			 dropDownId.add(desId);
			        			            			 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
			        			            		 }
			        			            		 else if(subDeptMaxId >0 && desigCellIndex==cellIndex)
			        			            		 {
			        			            			 Map<String,String> dataWithColumnName=new LinkedHashMap<String, String>();
						        						 dataWithColumnName.put("designationname",GRBE.readExcelSheet(row, cellIndex).toString());
						        						 dataWithColumnName.put("mappedOrgnztnId",String.valueOf(subDeptMaxId));
						        						 dataWithColumnName.put("orgnztnlevel",session.get("userDeptLevel").toString());
						        						 dataWithColumnName.put("levelofhierarchy",GRBE.readExcelSheet(row, levelCellIndex).toString());
						        						 dataWithColumnName.put("parallelshare","2");
						        						 
						        						 desigMaxId=new EmpExcelUploadHelper().saveData("mapped_designation_confg", "designation_configuration", "designation", dataWithColumnName);
						        						 if(desigMaxId>0)
						        						 {
						        							 dropDownId.add(String.valueOf(desigMaxId));
						        			            	 dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
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
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
							"Problem in Over2Cloud  method readExcelData of class "+getClass(), e);
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
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewConfirmationData of class "+getClass(), e);
			e.printStackTrace();
		}
		return returnResult;
	}
	
    
    @SuppressWarnings("unchecked")
	public String saveSelectData()
    {
    	String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				boolean status=false;
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=null;
				if(upload4!=null && !upload4.equals("") && upload4.equals("empBasicDetail"))
				{
					statusColName=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_basic_configuration");
				}
				boolean workStatus=false;
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
						 else if(gdp.getColomnName().equalsIgnoreCase("status"))
							 workStatus=true; 
					}
					if(upload4!=null && !upload4.equals("") && upload4.equals("empBasicDetail"))
					{
						 boolean status1=cbt.createTable22("employee_basic",tableColume,connectionSpace);
						 if(status1)
						 {
							 cbt.altTabSetUnique("employee_basic","mobOne",connectionSpace);
						 }
					}
				}
				List<String> gridListData=null;
				List<String> columnKeyList=null;
				List<String> dropDownValue=new ArrayList<String>();
				List<String> dropDownId=new ArrayList<String>();
				dropDownValue=(List<String>) session.get("dropDownValue");
				dropDownId=(List<String>) session.get("dropDownId");
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
				ArrayList<InsertDataTable> insertData=null;
				InsertDataTable ob=null;
				int tempIndex=0;
				if(gridListData!=null && columnKeyList!=null && gridListData.size()>0 && columnKeyList.size()>0)
				{
					for(int index=0;index<gridListData.size()/columnKeyList.size();index++)
					{
						insertData=new ArrayList<InsertDataTable>();
						for(int index4Col=0;index4Col<columnKeyList.size();index4Col++)
						{
							if(columnKeyList.get(index4Col).toString().equals("deptName") && session.get("userDeptLevel").toString().equals("1"))
							{
								if(dropDownValue!=null && dropDownValue.size()>0)
								{
									for(int i=0;i<dropDownValue.size();i++)
									{
										if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob=new InsertDataTable();
											ob.setColName("dept");
											ob.setDataName(dropDownId.get(i).toString());
											dropDownId.remove(i);
											dropDownValue.remove(i);
											insertData.add(ob);
											break;
										}
									}
									ob=new InsertDataTable();
									ob.setColName("mapLevel");
									ob.setDataName("1");
									insertData.add(ob);
								}
							}
							else if(columnKeyList.get(index4Col).toString().equals("subdept") && session.get("userDeptLevel").toString().equals("2"))
							{
								if(dropDownValue!=null && dropDownValue.size()>0)
								{
									for(int i=0;i<dropDownValue.size();i++)
									{
										if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob=new InsertDataTable();
											ob.setColName(columnKeyList.get(index4Col));
											ob.setDataName(dropDownId.get(i).toString());
											dropDownId.remove(i);
											dropDownValue.remove(i);
											insertData.add(ob);
											break;
										}
									}
									
									ob=new InsertDataTable();
									ob.setColName("mapLevel");
									ob.setDataName("2");
									insertData.add(ob);
								}
							}
							else if(columnKeyList.get(index4Col).toString().equals("designation"))
							{
									if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="")
									{
										if(dropDownValue!=null && dropDownValue.size()>0)
										{
											for(int i=0;i<dropDownValue.size();i++)
											{
												if(gridListData.get(tempIndex).toString()!=null && gridListData.get(tempIndex).toString()!="" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
												{
													ob=new InsertDataTable();
													ob.setColName(columnKeyList.get(index4Col));
													ob.setDataName(dropDownId.get(i).toString());
													insertData.add(ob);
													dropDownId.remove(i);
													dropDownValue.remove(i);
													break;
												}
											}
										}
										
									}
							}
							else if(upload4!=null && !upload4.equals("") && upload4.equals("empBasicDetail") && (!columnKeyList.get(index4Col).toString().equals("deptName") 
									&& !columnKeyList.get(index4Col).toString().equals("subdept") 
											&& !columnKeyList.get(index4Col).toString().equals("designation")
											&& !columnKeyList.get(index4Col).toString().equals("status"))
											&& !columnKeyList.get(index4Col).toString().equals("levelofhierarchy"))
									
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
									ob.setDataName(gridListData.get(tempIndex).toString().trim());
								}
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
						if(workStatus)
						{
							 ob=new InsertDataTable();
							 ob.setColName("status");
							 ob.setDataName("0");
							 insertData.add(ob);
						}
						 
						if(upload4!=null && !upload4.equals("") && upload4.equals("empBasicDetail"))
						{
							status=cbt.insertUniqueDataIntoTable("employee_basic",insertData,connectionSpace);
						}
					}
					if(status)
					{
						addActionMessage("Upload Data Successfully Registered!!!");
						returnResult=SUCCESS;
					}
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
	@SuppressWarnings("unchecked")
	public String getDeptData()
	{
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			deptList = new LinkedHashMap<Integer, String>();
			List departmentlist = new ArrayList();
			deptHierarchy = (String) session.get("userDeptLevel");
				
			// Getting Id, Dept Name for Non Service Department
			departmentlist=cbt.executeAllSelectQuery("SELECT id,deptName FROM department ORDER BY deptName ASC", connectionSpace);
			if (departmentlist!=null && departmentlist.size()>0) {
				for (Iterator iterator = departmentlist.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					deptList.put((Integer)object[0], object[1].toString());
				}
			}
			
			if(departmentlist!=null)
				departmentlist.removeAll(departmentlist);
			returnResult =SUCCESS;
		}		
		catch(Exception e)
		{
			 addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
			 e.printStackTrace();
			 return ERROR;
		}
	  }
	else {returnResult = LOGIN;}
	return returnResult;
	}
    
	public String getSubDeptData()
	{
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			deptHierarchy = (String) session.get("userDeptLevel");
			List tempList = new ArrayList();
				subDeptList = new LinkedHashMap<Integer, String>();
				tempList=cbt.executeAllSelectQuery("SELECT id,subdeptname FROM subdepartment WHERE deptid="+subdept_deptId+" ORDER BY subdeptname ASC", connectionSpace);
				if (tempList!=null && tempList.size()>0) {
					for (Iterator iterator = tempList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						subDeptList.put((Integer)object[0], object[1].toString());
					}
				}
			returnResult =SUCCESS;
		}		
		catch(Exception e)
		{
			 addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
			 e.printStackTrace();
			 return ERROR;
		}
	  }
	else {returnResult = LOGIN;}
	return returnResult;
	}
    public String getDesignation()
    {
    	String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List tempList = new ArrayList();
				desigList= new LinkedHashMap<Integer, String>();
				tempList=cbt.executeAllSelectQuery("SELECT id,designationname FROM designation WHERE mappedOrgnztnId="+subdept_deptId+" ORDER BY designationname ASC", connectionSpace);
				if (tempList!=null && tempList.size()>0) {
					for (Iterator iterator = tempList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						desigList.put((Integer)object[0], object[1].toString());
					}
				}
			returnResult =SUCCESS;
		}		
		catch(Exception e)
		{
			 addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
			 e.printStackTrace();
			 return ERROR;
		}
	  }
	else {returnResult = LOGIN;}
	return returnResult;
    }
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableAllis() {
		return tableAllis;
	}
	public void setTableAllis(String tableAllis) {
		this.tableAllis = tableAllis;
	}
	public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public String getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	public String getDownload4() {
		return download4;
	}
	public void setDownload4(String download4) {
		this.download4 = download4;
	}
	public List<ConfigurationUtilBean> getColumnMap2() {
		return columnMap2;
	}
	public void setColumnMap2(List<ConfigurationUtilBean> columnMap2) {
		this.columnMap2 = columnMap2;
	}
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
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
	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}
	public String getDeptHierarchy() {
		return deptHierarchy;
	}
	public void setDeptHierarchy(String deptHierarchy) {
		this.deptHierarchy = deptHierarchy;
	}
	public Map<Integer, String> getSubDeptList() {
		return subDeptList;
	}
	public Map<Integer, String> getDesigList() {
		return desigList;
	}
	public void setSubDeptList(Map<Integer, String> subDeptList) {
		this.subDeptList = subDeptList;
	}
	public void setDesigList(Map<Integer, String> desigList) {
		this.desigList = desigList;
	}
	public String getSubdept_deptId() {
		return subdept_deptId;
	}
	public void setSubdept_deptId(String subdept_deptId) {
		this.subdept_deptId = subdept_deptId;
	}
	public String getDeptId() {
		return deptId;
	}
	public String getSubdept() {
		return subdept;
	}
	public String getDesignationname() {
		return designationname;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}
	public void setDesignationname(String designationname) {
		this.designationname = designationname;
	}
	
	
	
	
	
}
