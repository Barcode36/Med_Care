package com.Over2Cloud.ctrl.feedback.bedmapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackDraftPojo;

public  class BedMappingHelper extends GridPropertyBean implements ServletRequestAware{

	HttpServletRequest request;
	
	private String empName;
	private File bedMappingExcel;
	private String bedMappingExcelContentType;
	private String bedMappingExcelFileName;
	String moduleName="FM";
	List<BedMappingPojo> excelData =null;
	private List<BedMappingPojo> gridBedMappingExcelModel;
	
	private JSONArray commonJSONArray=new JSONArray();
	
	public List getBedMappingData()
	{
		List mappingdata = new ArrayList();
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("select bed.id,dept.deptName,emp.empName,bed.bed_no from compliance_contacts as contacts");
			sb.append(" inner join department as dept on contacts.forDept_id=dept.id");
			sb.append(" inner join employee_basic as emp on contacts.emp_id=emp.id");
			sb.append(" inner join bed_mapping as bed on contacts.id=bed.contact_id");
			mappingdata = new createTable().executeAllSelectQuery(sb.toString(),connectionSpace);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mappingdata;
	}
	
	
	@SuppressWarnings("unchecked")
	public String uploadExcel()throws Exception
	{
		String returnResult = ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			//System.out.println("Iside Excel Upload Method ::::  >>>  ");
			try {
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}	
			    else {
			    	 String categoryId ="",feed_Id="";
			    	  HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
					   if(bedMappingExcel !=null)
					     {
						   InputStream inputStream = new FileInputStream(bedMappingExcel);
					       if(bedMappingExcelFileName.contains(".xlsx")){
					    	  // System.out.println("Inside xlsx method");
						   GenericReadExcel7 GRE7 = new GenericReadExcel7();
						   XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						   excelData = new ArrayList<BedMappingPojo>();
						   for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						    {
						      XSSFRow row = excelSheet.getRow(rowIndex);
						      boolean bed_mapping_flag= HUH.check_createTable("bed_mapping", connectionSpace);
						     // System.out.println("Feed Type Flag "+feed_type);
						      if (bed_mapping_flag) {
						     
								   int cellNo = row.getLastCellNum();
								//   System.out.println("Cell No "+cellNo);
							     if (cellNo>0)
							      {
							    	BedMappingPojo BMP = new BedMappingPojo();
							    	BMP.setEmp_contact_id(empName);
							    	BMP.setId(rowIndex);
								    for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
								     {
									    switch (cellIndex) {
									              case 0:
									            	  BMP.setBed_name(GRE7.readExcelSheet(row, cellIndex));
	    								          break;
									      }
								       }
								  excelData.add(BMP);
							   }
						     }
						    }
						   }
					       else  if(bedMappingExcelFileName.contains(".xls"))
					            {
					    	 //  System.out.println("Inside xls method");
					    	   GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						           HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						           excelData = new ArrayList<BedMappingPojo>();
						           for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++) {
						        	   HSSFRow row = excelSheet.getRow(rowIndex);
						        	   boolean bed_mapping_flag= HUH.check_createTable("bed_mapping", connectionSpace);
						        	   if (bed_mapping_flag) {
							           int cellNo = row.getLastCellNum();
							           if (cellNo>0)
							            {
							        	  BedMappingPojo BMP = new BedMappingPojo();
							        	  BMP.setId(rowIndex);
							        	  BMP.setEmp_contact_id(empName);
								          for(int cellIndex = 0; cellIndex< cellNo; cellIndex++ )
								            {
									          switch (cellIndex) {
									          case 0:
									        	  BMP.setBed_name(GRBE.readExcelSheet(row, cellIndex));
    								          break;
									         }
								         }
								  excelData.add(BMP);
							  }
						  }
						}
					    }else {}
						if (excelData.size()>0) {
							session.put("bedMappingList", excelData);
						}
					 }
					  returnResult =SUCCESS;    
			} }catch (Exception e) {
				e.printStackTrace();
		}
		}	else {
				returnResult=LOGIN;
			}
		return returnResult;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String showUploadBedMapping()
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
					//System.out.println("Inside show upload method");
					excelData = (List<BedMappingPojo>)session.get("bedMappingList");
					if (excelData !=null && excelData.size()>0) {
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
						setGridBedMappingExcelModel(excelData.subList(from, to));
	
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
					returnResult =SUCCESS;
				}
			} catch (Exception e) {
			}
		}else
			{
				return LOGIN;
			}
	//	System.out.println("Return Result Method  "+returnResult);
			return returnResult;
	}

	
	

	@SuppressWarnings("unchecked")
	public String saveBedMappingExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try {
				  CommonOperInterface cot = new CommonConFactory().createInterface();
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else 
				 {
					excelData = (List<BedMappingPojo>) session.get("bedMappingList");
					session.remove("bedMappingList");
					boolean flag =false;
					InsertDataTable ob=null;
					for (Iterator<BedMappingPojo> empItr = excelData.iterator(); empItr.hasNext();) {
						BedMappingPojo FDP = empItr.next();
						 flag = new HelpdeskUniversalHelper().isExist("bed_mapping", "contact_id", FDP.getEmp_contact_id(), "bed_no", FDP.getBed_name(), "", "", connectionSpace);
						 //System.out.println("Flag Value  "+flag);
						 if (!flag) {
							
						     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							
							 ob=new InsertDataTable();
							 ob.setColName("contact_id");
							 ob.setDataName(FDP.getEmp_contact_id());
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("bed_no");
							 ob.setDataName(FDP.getBed_name());
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("userName");
							 ob.setDataName(userName);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("date");
							 ob.setDataName(DateUtil.getCurrentDateUSFormat());
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("time");
							 ob.setDataName(DateUtil.getCurrentTime());
							 insertData.add(ob);
							boolean status= cot.insertIntoTable("bed_mapping",insertData,connectionSpace);
							if (status) {
								flag=false;
							} 
						 }
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


	@SuppressWarnings("unchecked")
	public String modifyBedMapping()
	{
		System.out.println("Action is as "+getOper());
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(getOper().equalsIgnoreCase("edit"))
				{
				//	System.out.println("Inside Edit If ");
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
					//	System.out.println("Param Name  "+parmName);
						String paramValue=request.getParameter(parmName);
					//	System.out.println("Param Value  "+paramValue);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
								&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
						{
							boolean exists=new HelpdeskUniversalHelper().isExist("bed_mapping", "bed_no",paramValue,connectionSpace);
							if(!exists)
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					wherClause.put("userName", userName);
					wherClause.put("date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("time", DateUtil.getCurrentTime());
					
					condtnBlock.put("id",getId());
					//boolean flag=new HelpdeskUniversalHelper().updateTableColomn("bed_mapping", wherClause, condtnBlock,connectionSpace);
					//System.out.println("Flag Value is  "+flag);
					cbt.updateTableColomn("bed_mapping", wherClause, condtnBlock,connectionSpace);
					returnResult=SUCCESS;
				}
				else if(getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String tempIds[]=getId().split(",");
					StringBuilder condtIds=new StringBuilder();
						int i=1;
						for(String H:tempIds)
						{
							if(i<tempIds.length)
								condtIds.append(H+" ,");
							else
								condtIds.append(H);
							i++;
						}
						cbt.deleteAllRecordForId("bed_mapping", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch(Exception e)
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
	public String getMappedEmpList() 
	 {
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			StringBuilder qry = new StringBuilder();
			 try {
				// System.out.println("Id Value is   "+id);
				 if(userName==null || userName.equalsIgnoreCase(""))
						return LOGIN;
				   
				    String deptId = getDeptId(id,connectionSpace);
				    if (deptId!=null && !deptId.equals("") && !deptId.equals("NA")) {
								List resultList=new ArrayList<String>();
								qry.append(" select distinct emp.id,emp.empName from employee_basic as emp");
								qry.append(" inner join compliance_contacts as contacts on emp.id= contacts.emp_id ");
								qry.append(" inner join department as dept on contacts.forDept_id= dept.id ");
								qry.append(" inner join bed_mapping as bed on contacts.id=bed.contact_id ");
								qry.append(" where contacts.forDept_id="+deptId+"");
							//	System.out.println("Contact List  "+qry.toString());
								resultList= new HelpdeskUniversalHelper().getData(qry.toString(),connectionSpace);
								if(resultList.size()>0)
								 {
									for (Object c : resultList) 
									 {
										Object[] objVar = (Object[]) c;
										JSONObject listObject=new JSONObject();
										listObject.put("id",objVar[0].toString());
										listObject.put("name", objVar[1].toString());
										commonJSONArray.add(listObject);
									 }
								 }
					}
					returnResult = SUCCESS;
				}
				catch(Exception e)
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
	
	public String getDeptId(String id, SessionFactory connection)
	{
		String deptid="NA";
		List resultList = new ArrayList();
		StringBuilder deptQuery= new StringBuilder();
		try {
			    deptQuery.append("select distinct contacts.forDept_id from compliance_contacts as contacts");
			    deptQuery.append(" inner join bed_mapping as bed on contacts.id=bed.contact_id");
			    deptQuery.append(" where bed.id="+id+"");
			   // System.out.println("Dept Query   "+deptQuery.toString());
			    resultList= new HelpdeskUniversalHelper().getData(deptQuery.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resultList!=null && resultList.size()>0) {
			deptid= resultList.get(0).toString();
		}
		else {
			deptid="NA";
		}
		return deptid;
	}


	public List<BedMappingPojo> getExcelData() {
		return excelData;
	}


	public File getBedMappingExcel() {
		return bedMappingExcel;
	}


	public void setBedMappingExcel(File bedMappingExcel) {
		this.bedMappingExcel = bedMappingExcel;
	}


	public String getBedMappingExcelContentType() {
		return bedMappingExcelContentType;
	}


	public void setBedMappingExcelContentType(String bedMappingExcelContentType) {
		this.bedMappingExcelContentType = bedMappingExcelContentType;
	}


	public String getBedMappingExcelFileName() {
		return bedMappingExcelFileName;
	}


	public void setBedMappingExcelFileName(String bedMappingExcelFileName) {
		this.bedMappingExcelFileName = bedMappingExcelFileName;
	}


	public void setExcelData(List<BedMappingPojo> excelData) {
		this.excelData = excelData;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public List<BedMappingPojo> getGridBedMappingExcelModel() {
		return gridBedMappingExcelModel;
	}


	public void setGridBedMappingExcelModel(
			List<BedMappingPojo> gridBedMappingExcelModel) {
		this.gridBedMappingExcelModel = gridBedMappingExcelModel;
	}

	

	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}


	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
	
	
}
