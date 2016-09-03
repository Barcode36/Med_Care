package com.Over2Cloud.ctrl.criticalPatient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.aspose.cells.Row;
public class TestNameMaster extends GridPropertyBean implements ServletRequestAware  {
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> textBox = null;
	private List<ConfigurationUtilBean> dropDown = null;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private Map<String, String> testTypeMap;
	private List<Object> viewTestNameMaster;
	
	private File feedbackExcel;
	private String feedbackExcelContentType;
	private String feedbackExcelFileName;

	private String excelFileName;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	String floor=null;
	private String[] test_type;
	private String test_name;
	private String test_unit;
	private String min;
	private String max;
	private String gender;
	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	int totalCount=0;
	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	List<TestNamePojo> excelData = null;
	private List<TestNamePojo> gridFbDraftExcelModel;
	
	public String viewAddTestName() {
		
		String result = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				setAddPageDataFields();
				List testType= cbt.executeAllSelectQuery(" Select id,test_type from test_type where status='Active'", connectionSpace);
				if(testType!=null && testType.size()>0)
				{
					testTypeMap=new LinkedHashMap<String, String>();
					for (Iterator iterator = testType.iterator(); iterator.hasNext();)
					{
						
						Object[] object = (Object[]) iterator.next();
						testTypeMap.put(object[0].toString(), object[1].toString());
					}
				}
				result = SUCCESS;
			} catch (Exception e) {
				result = ERROR;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Showing Fields of Add Page
	 */
	@SuppressWarnings("rawtypes")
	public void setAddPageDataFields()
	{
		try
		{
			List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_test_name_configuration", accountID, connectionSpace, "", 0, "table_name", "test_name_configuration");
			textBox = new ArrayList<ConfigurationUtilBean>();
			dropDown = new ArrayList<ConfigurationUtilBean>();
			if (fieldList != null && fieldList.size() > 0)
			{
				for (GridDataPropertyView gdp : fieldList)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						textBox.add(objdata);
					} else if (gdp.getColType().trim().equalsIgnoreCase("D"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						dropDown.add(objdata);

					} 
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public String addTestName()
	{
		boolean flag=false;
		String result = ERROR;
		try
		{
			boolean session = ValidateSession.checkSession();
			if (session)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<com.Over2Cloud.Rnd.TableColumes> TableColumnName = new ArrayList<TableColumes>();

					List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_test_name_configuration", accountID, connectionSpace, "", 0, "table_name", "test_name_configuration");
					if (org2 != null)
					{
						for (GridDataPropertyView gdp : org2)
						{

							if (gdp.getColomnName().equalsIgnoreCase("status"))
							{
								TableColumes ob1 = new TableColumes();
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default 'Active' ");
								TableColumnName.add(ob1);
							}
							else
							{
								TableColumes ob1 = new TableColumes();
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								ob1.setConstraint("default NULL");
								TableColumnName.add(ob1);
							}
						}

						cbt.createTable22("test_name", TableColumnName, connectionSpace);

						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();

						InsertDataTable ob = null;
						while (it.hasNext())
						{
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (Parmname != null && !Parmname.trim().equals("") && !Parmname.trim().equals("__multiselect_test_type") && !Parmname.trim().equals("test_type") && !Parmname.trim().equals("test_name") && !Parmname.trim().equals("test_unit"))//
							{
							}
						}
						 String[] check_test = test_name.split(",");
						 String[] check_unit = test_unit.split(",");
						 String [] minVal=min.split(",");
						 String [] maxVal=max.split(",");
						 String [] genderVal=gender.split(",");
						 
						String[] testType = test_type;
						String test = "";
						System.out.println("test_type "+test_type);
						if (testType != null)
						{
							for (int i = 0; i < testType.length; i++)
							{
								if(!testType[i].equalsIgnoreCase(""))
								{
									test += testType[i]+"#";
								}
							}
						}
						String[] abc=test.split("#");
						int i = 0;
						if(abc.length>1)
						{
							for(i=0; i<abc.length; i++)
							{
								System.out.println(abc[i]);
								ob = new InsertDataTable();
								ob.setColName("test_type");
								ob.setDataName(abc[i]);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("test_name");
								ob.setDataName(request.getParameter("test_name"));
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("test_unit");
								ob.setDataName(request.getParameter("test_unit"));
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("min");
								ob.setDataName(request.getParameter("min"));
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("max");
								ob.setDataName(request.getParameter("max"));
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("gender");
								ob.setDataName(request.getParameter("gender"));
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
								ob.setDataName(DateUtil.getCurrentTime());
								insertData.add(ob);
								
								flag = cbt.insertIntoTable("test_name", insertData, connectionSpace);
								insertData.clear();
							}
						}
						else if(check_test!=null)
						{
							for(int j=0;j<check_test.length;j++)
							{
								
								if(check_test[j]!=null && check_test[j].trim().length()>0)
								{
									ob = new InsertDataTable();
									ob.setColName("test_name");
									ob.setDataName(check_test[j].trim());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("test_unit");
									ob.setDataName(check_unit[j].trim());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("min");
									ob.setDataName(minVal[j].trim());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("max");
									ob.setDataName(maxVal[j].trim());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("gender");
									ob.setDataName(genderVal[j].trim());
									insertData.add(ob);
									
									
									
									
									ob = new InsertDataTable();
									ob.setColName("test_type");
									ob.setDataName(abc[i]);
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
									ob.setDataName(DateUtil.getCurrentTime());
									insertData.add(ob);
									
									flag = cbt.insertIntoTable("test_name", insertData, connectionSpace);
									insertData.clear();
								}
							}
						}
						if (flag)
						{
							addActionMessage(" Test Type Add Successfulluy ..");
							result = SUCCESS;
						}
						else
						{
							addActionMessage(" Opps Some Error in Data Insertion !!!!!!!!!!!");
							result = ERROR;
						}

					}
			}
			else
			{
				result = ERROR;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	
	}
	public String beforeAddTestName()
	{
		return "success";
	}

	public String beforeTestNameMater() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setMachineMasterView();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}
	// for main keyword column name set AddTestType master grid
	public void setMachineMasterView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_test_name_configuration", accountID, connectionSpace, "", 0, "table_name", "test_name_configuration");
				for (GridDataPropertyView gdp : returnResult) {
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());

					masterViewProp.add(gpv);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}

	
	// data view in grid for addTestType Response master
	@SuppressWarnings("unchecked")
	public String viewTestNameMaster() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from test_type");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null) {
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					List fieldNames = Configuration.getColomnList("mapped_test_name_configuration", accountID, connectionSpace, "test_name_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();) 
					{
						Object obdata = (Object) it.next();
						if (obdata != null) 
						{
							if (i < fieldNames.size() - 1) 
							{
								if(obdata.equals("test_type"))
								{
									query.append("tt.test_type as testType ,");
								}
								else
								{
									query.append(" tn. "+obdata + ", ");
								}
								
							}
							else 
							{
								query.append(" tn. "+obdata );
							}
						}
						i++;
					}

					query.append(" from test_name as tn ");
					query.append(" right join test_type as tt on tt.id=tn.test_type where tn.status='Active' ");
					
					

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) 
					{
						
						query.append(" and ");
						if (getSearchOper().equalsIgnoreCase("eq")) {
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					

					query.append(" order by tn.test_name asc");
					//System.out.println("query  "+query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null) {
						for (Iterator it = data.iterator(); it.hasNext();) {
							StringBuilder dataList=new StringBuilder();
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {
									if (k == 0) {
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else {
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("date")) {
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}

									if (fieldNames.get(k).toString().equalsIgnoreCase("time")) {
										one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
									}
								}else{
									
									one.put(fieldNames.get(k).toString(), "NA");  //add  by aarif for showing NA on grid for blank value 02-01-2016
									
								}
							}

							Listhb.add(one);

						}
						setViewTestNameMaster(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}

				returnValue = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				returnValue = ERROR;
			}
		} else {
			returnValue = LOGIN;
		}
		return returnValue;
	}

	// modify Machine Response Data
	public String modifyTestName() {

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {

			try {
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					@SuppressWarnings("unchecked")
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("test_name", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update test_name set status ='Inactive' where id='" + getId() + "'");
					cbt.updateTableColomn(connectionSpace, sb);
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	//Upload Excel
	@SuppressWarnings("unchecked")
	public String readExcel() throws Exception
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				System.out.println("feedbackExcel "+feedbackExcel);
				if (feedbackExcel != null)
				{
					InputStream inputStream = new FileInputStream(feedbackExcel);
					
					if (feedbackExcelFileName.contains(".xlsx"))
					{
						GenericReadExcel7 GRE7 = new GenericReadExcel7();
						XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						excelData = new ArrayList<TestNamePojo>();
						for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						{
							XSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0)
							{
								TestNamePojo FDP = new TestNamePojo();
								for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
								{
									
									switch (cellIndex)
									{
										case 0:
										
										if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
										{
											FDP.setTest_type(GRE7.readExcelSheet(row, cellIndex));
											System.out.println("A coloumn  "+GRE7.readExcelSheet(row, cellIndex));
										}
										else
										{
											FDP.setTest_type("NA");
											System.out.println("inside else");
										}
										break;

										case 1:
											
											if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
											{
												FDP.setTest_name(GRE7.readExcelSheet(row, cellIndex));
												System.out.println("A coloumn  "+GRE7.readExcelSheet(row, cellIndex));
											}
											else
											{
												FDP.setTest_name("NA");
												System.out.println("inside else");
											}
											break;
										
										case 4:
											if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
											{
												FDP.setTest_unit(GRE7.readExcelSheet(row, cellIndex));
												System.out.println("A coloumn  "+GRE7.readExcelSheet(row, cellIndex));
											}
											else
											{
												FDP.setTest_unit("NA");
												System.out.println("inside else");
											}
										
										break;
										case 3:
											if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
											{
												FDP.setMax(GRE7.readExcelSheet(row, cellIndex));
												System.out.println("A coloumn  "+GRE7.readExcelSheet(row, cellIndex));
											}
											else
											{
												FDP.setMax("NA");
												System.out.println("inside else");
											}
										
										break;
										case 2:
											if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
											{
												FDP.setMin(GRE7.readExcelSheet(row, cellIndex));
												System.out.println("A coloumn  "+GRE7.readExcelSheet(row, cellIndex));
											}
											else
											{
												FDP.setMin("NA");
												System.out.println("inside else");
											}
										
										break;
										
														
									}
								}
								excelData.add(FDP);
							}
						}
					}
					else if (feedbackExcelFileName.contains(".xls"))
					{
						GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
					HSSFSheet excelSheet = GRBE.readExcel(inputStream);
					excelData = new ArrayList<TestNamePojo>();
					for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
					{
						HSSFRow row = excelSheet.getRow(rowIndex);
						int cellNo = row.getLastCellNum();
						if (cellNo > 0)
							{
								TestNamePojo FDP = new TestNamePojo();
								System.out.println("Cell No--"+cellNo);
								
								for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									
								{
									switch (cellIndex)
									{
										case 0:
										
										if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equals(""))
										{
											FDP.setTest_type(GRBE.readExcelSheet(row, cellIndex));
											System.out.println("TestType--"+FDP.getTest_type());
										}
										else
										{
											FDP.setTest_type("NA");
											System.out.println("TestType--"+FDP.getTest_type());
										}			
										break;

										case 1:
										if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equals(""))
										{

											FDP.setTest_name(GRBE.readExcelSheet(row, cellIndex));
											System.out.println("TestName--"+FDP.getTest_name());
										}
										else
										{

											FDP.setTest_name("NA");
											System.out.println("TestName--"+FDP.getTest_name());
										}		
										break;
										
										case 2:
											
											if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equals(""))
											{

												FDP.setTest_unit(GRBE.readExcelSheet(row, cellIndex));
												System.out.println("Brief--"+FDP.getTest_unit());
											}
											else
											{

												FDP.setTest_unit("NA");
												System.out.println("Brief--"+FDP.getTest_unit());
											}		
											break;

										
										
										
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
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;

	}
	
	public String beforeUpload()
	{
		return "success";
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
					excelData = (List<TestNamePojo>) session.get("fbDraftList");
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
	@SuppressWarnings({ "unused", "unchecked" })
	public String saveExcelData()
	{

		
		boolean status=false;
		String returnResult = ERROR;
		
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				System.out.println("UserName "+userName);
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<TestNamePojo>) session.get("fbDraftList");
					
					System.out.println("bbbb "+excelData.size());
					
					
					
					session.remove("fbDraftList");
					boolean flag = false;
					InsertDataTable ob = null;
					for (Iterator<TestNamePojo> it = excelData.iterator(); it.hasNext();)
					{
						
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						TestNamePojo object=new TestNamePojo();
						object= it.next();
						List checkData=cot.executeAllSelectQuery("SELECT * FROM test_type where test_type='"+object.getTest_type()+"'", connectionSpace);
						if (checkData.size()==0) 
						{
							ob=new InsertDataTable();
							ob.setColName("test_type");
							ob.setDataName(object.getTest_type());
							insertData.add(ob);
							
							
							
							 ob=new InsertDataTable();
					         ob.setColName("test_type_brief");
							 ob.setDataName(object.getBrief());
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
								
							 status = cot.insertIntoTable("test_type", insertData, connectionSpace);
							 
							 
							 String testType=object.getTest_type();
							 String testName=object.getTest_name();
							 String testUnit=object.getTest_unit();
							 							 								 
							 String min=object.getMin();// add by aarif to add min and max valiue in test_name table 31-12-2015
							 String max=object.getMax();
							 
							 							 								 
							 insertTest_Name(testType,testName,testUnit,min,max);  // add by aarif to add min and max valiue in test_name table 31-12-2015
						
						}
						
						else if(checkData!=null &&checkData.size()>0)
						{
							/*List checkDatainTestName=cot.executeAllSelectQuery("SELECT * FROM test_name where test_name='"+object.getTest_name()+"'", connectionSpace);
							if(checkDatainTestName.size()==0){	*/
							String testName=object.getTest_name();
								String testUnit=object.getTest_unit();
								 String testType=object.getTest_type();
								 String min=object.getMin();// add by aarif to add min and max valiue in test_name table 31-12-2015
								 String max=object.getMax();
								 
								 							 								 
								 insertTest_Name(testType,testName,testUnit,min,max);  // add by aarif to add min and max valiue in test_name table 31-12-2015
							/*}else{
								System.out.println("Duplicate test name");
								
							}*/

						}
						else
						{
							ob=new InsertDataTable();
							ob.setColName("test_type");
							ob.setDataName(object.getTest_type());
							insertData.add(ob);
							
							
							
							 ob=new InsertDataTable();
					         ob.setColName("test_type_brief");
							 ob.setDataName("NA");
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
								
							 status = cot.insertIntoTable("test_type", insertData, connectionSpace);
							 
							 
							 String testType=object.getTest_type();
							 String testName=object.getTest_name();
							 String testUnit=object.getTest_unit();
							 
							 String min=object.getMin();// add by aarif to add min and max valiue in test_name table 31-12-2015
							 String max=object.getMax();
							 
							 							 								 
							 insertTest_Name(testType,testName,testUnit,min,max);  // add by aarif to add min and max valiue in test_name table 31-12-2015
						}
						
					}
					 if (status)
					 {
						flag = true;
					 }
				}
				
				
				if (true)
				{
					addActionMessage("Excel Uploaded Successfully, Total data: "+excelData.size());
				}
					
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
	
	public void insertTest_Name(String testType,String testName,String unit, String min, String max)   //change by aarif to add min and max value in test_name table 31-12-2015
	{
		boolean status=false;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		InsertDataTable ob = null;
		int floorid=0;
		boolean flag=false;
		
	    try 
		{
	    	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	    	
    		
    		ob=new InsertDataTable();
			ob.setColName("test_type");
			floorid=getTestType(testType);
			ob.setDataName(floorid);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("min");
			
			ob.setDataName(min);
			insertData.add(ob);
			
			ob=new InsertDataTable();
			ob.setColName("max");
		
			ob.setDataName(max);
			insertData.add(ob);
				

	    	
	    	ob=new InsertDataTable();
	        ob.setColName("test_name");
			ob.setDataName(testName);
			insertData.add(ob);
			
			ob=new InsertDataTable();
	        ob.setColName("test_unit");
			ob.setDataName(unit);
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
			 
			status = cot.insertIntoTable("test_name", insertData, connectionSpace);
			
	    	
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
	
	public int getTestType(String testType_ID)
	{
		int id = 0;
		CommonOperInterface cot = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("SELECT id FROM test_type where test_type='"+testType_ID+"'");
		//System.out.println("query========"+query);
		List dataList = cot.executeAllSelectQuery(query.toString(), connectionSpace);
		if (dataList != null)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
				id=(Integer) it.next();
			}
		}
		return id;
	}
	
	
	//Download Formate
	public String downloadFormat()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				List titleList = new ArrayList();
				
				titleList.add("Test Type");
				titleList.add("Test Name");
				titleList.add("Test Unit");
				
				String orgName = "Formate Test Name";
				if(titleList!=null && titleList.size()>0)
				{
					excelFileName = writeDataInExcel(null, titleList, null, "Test Name ",orgName+ "", true, connectionSpace);
					System.out.println("excelFileName  "+excelFileName);
					if (excelFileName != null)
					{
						excelStream = new FileInputStream(excelFileName);
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
	
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);
		
		if(needToStore)
		{
			excelFileName = new CreateFolderOs().createUserDir("TestName")+  mergeDateTime+".xls";
		}
			
		else
		{
			excelFileName="TestName"+ mergeDateTime+".xls";
		}
		
			//excelFileName="TestName"+ mergeDateTime+".xls";
		
		//int header_first=2;
		int index=0;
		HSSFRow rowHead = sheet.createRow((int) index);
		for(int i=0;i<titleList.size();i++)
		{
			Cell subTitleCell22 = rowHead.createCell((i));
			subTitleCell22.setCellValue(titleList.get(i).toString());
			subTitleCell22.setCellStyle(styles.get("mytittle"));
		}
		
		try
		{
			FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return excelFileName;
	}
	
	@SuppressWarnings("unused")
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;

		
		// mytittle
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("mytittle", style);
		
		return styles;
	}
	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request=req;		
	}



	public Map<String, String> getTestTypeMap()
	{
		return testTypeMap;
	}



	public void setTestTypeMap(Map<String, String> testTypeMap)
	{
		this.testTypeMap = testTypeMap;
	}



	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}



	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	
	public List<Object> getViewTestNameMaster() {
		return viewTestNameMaster;
	}



	public void setViewTestNameMaster(List<Object> viewTestNameMaster) {
		this.viewTestNameMaster = viewTestNameMaster;
	}

	

	

	public File getFeedbackExcel() {
		return feedbackExcel;
	}

	public void setFeedbackExcel(File feedbackExcel) {
		this.feedbackExcel = feedbackExcel;
	}

	public String getFeedbackExcelContentType() {
		return feedbackExcelContentType;
	}

	public void setFeedbackExcelContentType(String feedbackExcelContentType) {
		this.feedbackExcelContentType = feedbackExcelContentType;
	}

	public String getFeedbackExcelFileName() {
		return feedbackExcelFileName;
	}

	public void setFeedbackExcelFileName(String feedbackExcelFileName) {
		this.feedbackExcelFileName = feedbackExcelFileName;
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

	public List<TestNamePojo> getExcelData() {
		return excelData;
	}

	public void setExcelData(List<TestNamePojo> excelData) {
		this.excelData = excelData;
	}

	public List<TestNamePojo> getGridFbDraftExcelModel() {
		return gridFbDraftExcelModel;
	}

	public void setGridFbDraftExcelModel(List<TestNamePojo> gridFbDraftExcelModel) {
		this.gridFbDraftExcelModel = gridFbDraftExcelModel;
	}

	

	public String[] getTest_type() {
		return test_type;
	}

	public void setTest_type(String[] test_type) {
		this.test_type = test_type;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<ConfigurationUtilBean> getTextBox() {
		return textBox;
	}

	public void setTextBox(List<ConfigurationUtilBean> textBox) {
		this.textBox = textBox;
	}

	public List<ConfigurationUtilBean> getDropDown() {
		return dropDown;
	}

	public void setDropDown(List<ConfigurationUtilBean> dropDown) {
		this.dropDown = dropDown;
	}

	public String getTest_unit() {
		return test_unit;
	}

	public void setTest_unit(String test_unit) {
		this.test_unit = test_unit;
	}

}