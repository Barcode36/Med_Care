package com.Over2Cloud.ctrl.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.compliance.ComplianceExcelDownload;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.krLibrary.KRActionHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

@SuppressWarnings("serial")
public class CommonContactCtrl extends GridPropertyBean implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(CommonContactCtrl.class);
	private boolean statusFlag = false;

	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> contactTextBox;
	private List<ConfigurationUtilBean> contactDateTimeBox;
	private List<ConfigurationUtilBean> contactFormDDBox;
	private List<ConfigurationUtilBean> contactFileBox;

	private List<ConfigurationUtilBean> contactDD;
	private Map<Integer, String> groupMap;
	private Map<Integer, String> subGroupMap;
	private Map<Integer, String> deptMap;
	private Map<Integer, String> desgMap;
	private Map<Integer, String> industryMap;
	private Map<Integer, String> sourceMap;
	private Map<String, String> vendornameMap;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String searchFields;
	private String SearchValue;
	// For File Upload and Image
	public File empLogo;
	private String empLogoContentType;
	private String empLogoFileName;

	public File empDocument;
	private String empDocumentContentType;
	private String empDocumentFileName;

	private String groupId;
	private String regLevel;
	private String subGroupId;

	private String industryId;

	private JSONArray commonJSONArray = new JSONArray();
	private HttpServletRequest request;
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;

	private Map<Integer, String> officeMap;
	private Map<String, String> checkEmp;

	private String excelFileName;
	private String mobOne;
	private String empId;

	private FileInputStream excelStream;
	// private InputStream inputStream;
	private String contentType;
	private File contactExcel;
	private String contactExcelContentType;
	private String contactExcelFileName;
	private String groupName;
	private String contId;
	private String documentName;
	private Map<String, String> docMap;;
	private InputStream fileInputStream;
	private Map<String, String> totalCount;
	private JSONArray jsonObjArray;
	private JSONArray userTypeMap = null;
	private String selectedId;
	private JSONArray jssonArr;
	private String officeFlag;
	private String country,headoffice,branchoffice;
	private boolean hodStatus=false;
	private boolean mgtStatus=false;
	public String docDownload()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				if(getContId()!=null)
				{
					List docList=cbt.executeAllSelectQuery("select empDocument from employee_basic where id='"+getContId()+"'", connectionSpace);
					if(docList!=null && docList.size()>0)
					{
						docMap=new LinkedHashMap<String, String>();
						String str=null;
						Object object=null;
						for (Iterator iterator = docList.iterator(); iterator.hasNext();)
						{
							object = (Object) iterator.next();
							if(object!=null)
							{
								str=object.toString().substring(object.toString().lastIndexOf("//")+2, object.toString().length());
								documentName=str.substring(14,str.length());
								docMap.put(object.toString(),documentName);
							}
						}
						
						File file=new File(str);
						documentName=str.substring(14,str.length());
			            if(file.exists())
			            {
			            	fileInputStream = new FileInputStream(file);
			            	return SUCCESS;
			            }
			            else
			            {
			                 addActionError("File dose not exist");
			                 return ERROR;
			            }
					}
					
					
				}
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getAvailability()
	{
		try
		{
			  if (mobOne!=null)
	            {
	                checkEmp = new HashMap<String, String>();
	                // check user mobile number
	                boolean status = false;
	                if(mobOne.matches(".*\\d.*")&& mobOne.length()==10){
	                    
	               
	                List lowerLevel3 = new ArrayList<String>();
	                Map<String, Object> temp = new HashMap<String, Object>();
	                temp.put("mobOne", mobOne);
	                if (getGroupId() != null)
	                {
	                    temp.put("groupId", getSubGroupId());
	                }
	                lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("employee_basic", lowerLevel3, temp, connectionSpace);
	                if (lowerLevel3 != null && lowerLevel3.size() > 0)
	                {
	                    status = true;
	                }
	                if (!status)
	                {
	                    checkEmp.put("msg", "You Can Create.");
	                    checkEmp.put("status", "false");
	                }
	                else
	                {
	                    checkEmp.put("msg", "Already exist.");
	                    checkEmp.put("status", "true");
	                }
	                
	                }
	                else
	                {
	                    checkEmp.put("msg", "Please Enter Valid Details");
	                    checkEmp.put("status", "true");
	                }
	            }
	            return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getAvailability of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
	}

	public String beforeAdd()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				setContactAddPageDataFields();
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAdd of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String contactImageView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List dataList = cbt.executeAllSelectQuery("select empLogo from employee_basic where id='" + getId() + "'", connectionSpace);
				if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
				{
					setEmpLogoFileName(dataList.get(0).toString());
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAdd of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String contactViewInGrid()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				String userName = (String) session.get("uName");
				List dataCount = cbt.executeAllSelectQuery("select count(*) from employee_basic where flag!=1", connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
				}
				StringBuilder query = new StringBuilder(" select ");
				List fieldNames = Configuration.getColomnList("mapped_common_contact_configuration", accountID, connectionSpace, "common_contact_configuration");
				List fieldNamesEmp = Configuration.getColomnList("mapped_emp_contact_configuration", accountID, connectionSpace, "emp_contact_configuration");
				List fieldNamesLead=new ArrayList();
				List clientExists=cbt.executeAllSelectQuery("select * from groupinfo where groupName='Client'", connectionSpace);
				if(clientExists!=null && clientExists.size()>0)
				{
					fieldNamesLead = Configuration.getColomnList("mapped_client_contact_configuration", accountID, connectionSpace, "client_contact_configuration");
				}
				List<Object> listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (true)
					{

                    	if(i<fieldNames.size()-1)
                    	{
                    		if(obdata.toString().equalsIgnoreCase("groupId"))
                    	 	{
                    			query.append("grp.groupName,");
                    	 	}
                    		else if (obdata.toString().equalsIgnoreCase("deptname")) 
							{
								query.append("dpt.deptname AS dept1,");
							}
                    		else if(obdata.toString().equalsIgnoreCase("regLevel"))
                    		{
                    			query.append("branch.name AS branch,");
                    		}
                    		else if(obdata.toString().equalsIgnoreCase("id"))
                        	{
                        		query.append("contct.id,country.name as country,head.name as head,");
                        	}
                    	 	else
                    	 	{
                    	 		query.append("contct."+obdata.toString()+",");
                    	 	}
                    	}
                    	else
                    	{
                    		if(obdata.toString().equalsIgnoreCase("groupId"))
                    	 	{
                    			query.append("grp.groupName");
                    	 	}
                    		else if (obdata.toString().equalsIgnoreCase("deptname")) 
							{
								query.append("dpt.deptname AS dept1");
							}
                    		else
                    		{
                    			query.append("contct."+obdata.toString());
                    		}
                    	}
					}
					i++;
				}
				for (Iterator it = fieldNamesEmp.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null && !obdata.toString().equalsIgnoreCase("id"))
					{
						query.append(",contct." + obdata.toString());
						fieldNames.add(obdata.toString());
					}
					i++;
				}
				if(clientExists!=null && clientExists.size()>0)
				{
					for (Iterator it = fieldNamesLead.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null && !obdata.toString().equalsIgnoreCase("id") && obdata != null && !obdata.toString().equalsIgnoreCase("contactName")
								&& !obdata.toString().equalsIgnoreCase("dateOfBirth") && !obdata.toString().equalsIgnoreCase("dateOfJoining") && !obdata.toString().equalsIgnoreCase("dateOfAnnvrsry")
								&& !obdata.toString().equalsIgnoreCase("mobileNo") && !obdata.toString().equalsIgnoreCase("contactEmailId") && !obdata.toString().equalsIgnoreCase("orgAddress")
								)
						{
							if (obdata.toString().equalsIgnoreCase("source"))
							{
								query.append(",sm.sourceName ");
							}
							else if (obdata.toString().equalsIgnoreCase("department"))
							{
								query.append(",dept1.deptName As clientDept ");
							}
							else if (obdata.toString().equalsIgnoreCase("industry"))
							{
								query.append(",idus.industry As indu ");
							}
							else if (obdata.toString().equalsIgnoreCase("subIndustry"))
							{
								query.append(",subIndus.subIndustry As subIndus ");
							}
							else
							{
								query.append(",contct." + obdata.toString());
							}
							
							fieldNames.add(obdata.toString());
						}
						i++;
					}
				}
				 query.append("  from employee_basic as contct" +
		                	" LEFT join department as dpt on dpt.id=contct.deptname " +
		                	" LEFT join groupinfo as grp on grp.id= dpt.groupId " +
		                	" LEFT join department as dept1 on dept1.id=contct.department " +
		                	" LEFT JOIN sourcemaster AS sm ON sm.id=contct.source " +
		                	" LEFT JOIN industrydatalevel1 AS idus ON idus.id=contct.industry " +
		                	" LEFT JOIN subindustrydatalevel2 AS subIndus ON subIndus.id=contct.subIndustry ");
				 query.append(" LEFT join branchoffice_data as branch on grp.regLevel=branch.id  ");
			     query.append(" LEFT JOIN headoffice_data as head on head.id=branch.headOfficeId ");
		         query.append(" LEFT JOIN country_data as country on country.id=head.countryId WHERE");
		         String userType = (String)session.get("userTpe");
	             String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
	             if (userType!=null && userType.equalsIgnoreCase("H")) 
	             {
              		query.append("  head.id="+s[2] +"  ");
              		String deptId[]=new ComplianceUniversalAction().getEmpDataByUserName(userName);
					if (deptId!=null && !deptId.toString().equalsIgnoreCase("")) 
					{
						 query.append(" AND contct.deptname='"+deptId[3]+"'  ");
					}
				 } 
	             else if (userType!=null && userType.equalsIgnoreCase("N"))
	             {
              		query.append("  branch.id="+s[1]+" AND contct.userName='"+userName+"' ");
				 }
				 if(getSearchFields()!=null && !getSearchFields().equalsIgnoreCase("-1") && getSearchValue()!=null && !getSearchValue().equalsIgnoreCase("-1") )
				 {
					if (userType!=null && !userType.equalsIgnoreCase("M")) 
	                {
	                	query.append(" AND ");
					}
					if (!getSearchFields().equalsIgnoreCase("flag"))
					{
						 query.append("  contct."+getSearchFields()+"='"+getSearchValue()+"' AND contct.flag!=1 ");
					}
					else
					{
						query.append("  contct."+getSearchFields()+"='"+getSearchValue()+"' ");
					}
				 }
				 if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				 {
					query.append(" and  ");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}
				}
				System.out.println("query ::::" +query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int j=0;
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[j] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer)obdata[j]);
									if (obdata[j+1]!=null) 
									{
										 one.put("country", obdata[j+1].toString());
									} else {
										 one.put("country","NA");
									}
                                    if (obdata[j+2]!=null)
                                    {
                                    	 one.put("head", obdata[j+2].toString());
									} else {
										 one.put("head", "NA");
									}
                                    j=j+2;
								}
								else
								{
									 if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("empLogo"))
									{
										one.put(fieldNames.get(k).toString(), "View Image");
									}
									else if(obdata[j].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[j].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("flag"))
									{
										if (obdata[j].toString() != null && obdata[j].toString().equalsIgnoreCase("0"))
										{
											one.put(fieldNames.get(k).toString(), "Active");
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "Inactive");
										}
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[j].toString());
									}
								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
							j++;
						}
						listhb.add(one);
					}
					setMasterViewList(listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method contactViewInGrid of class "
								+ getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	@SuppressWarnings("unchecked")
	public String addContact()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				String userName = (String) session.get("uName");
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name","common_contact_configuration");
				boolean userTrue = true;
				boolean dateTrue = true;
				boolean timeTrue = true;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : statusColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					if (gdp.getColomnName().equalsIgnoreCase("flag"))
					{
						ob1.setConstraint("default 0");
					}
					else
					{
						ob1.setConstraint("default NULL");
					}
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName"))
						userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date"))
						dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time"))
						timeTrue = true;
				}
				statusColName.clear();
				statusColName = Configuration.getConfigurationData("mapped_emp_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "emp_contact_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}
				}
				statusColName.clear();
				statusColName = Configuration.getConfigurationData("mapped_client_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("orgAddress") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("mobileNo")
								&& !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("contactName")
								&& !gdp.getColomnName().equalsIgnoreCase("dateOfJoining") && !gdp.getColomnName().equalsIgnoreCase("dateOfAnnvrsry")
								&& !gdp.getColomnName().equalsIgnoreCase("designation"))
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							Tablecolumesaaa.add(ob1);
						}
					}
				}
				cbt.createTable22("employee_basic", Tablecolumesaaa, connectionSpace);
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				String orgName = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
				}
				String vendordept = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				String contactMobNo = null;
				boolean status = false;
				String contactId=null;
				while (it.hasNext())
				{
					String parmname = it.next().toString();
					String paramValue = request.getParameter(parmname);
					if(parmname.equalsIgnoreCase("regLevel") || parmname.equalsIgnoreCase("countryId") || parmname.equalsIgnoreCase("HeadOfficeId") || parmname.equalsIgnoreCase("BranchofficeId"))
					{
						ob = new InsertDataTable();
						if(request.getParameter("regLevel").equalsIgnoreCase("1") && parmname.equalsIgnoreCase("countryId") ){
							ob.setColName("mappedwith");
							ob.setDataName("country");
							insertData.add(ob);
							InsertDataTable ob1= new InsertDataTable();
							ob1.setColName("regLevel");
							ob1.setDataName(request.getParameter("countryID"));
							insertData.add(ob1);
						}else if(request.getParameter("regLevel").equalsIgnoreCase("2") && parmname.equalsIgnoreCase("HeadOfficeId") ){
							ob.setColName("mappedwith");
							ob.setDataName("headoffice");
							insertData.add(ob);
							InsertDataTable ob1= new InsertDataTable();
							ob1.setColName("regLevel");
							ob1.setDataName(request.getParameter("HeadOfficeId"));
							insertData.add(ob1);
							
						}else if (request.getParameter("regLevel").equalsIgnoreCase("3") && parmname.equalsIgnoreCase("BranchofficeId")){
							ob.setColName("mappedwith");
							ob.setDataName("branchoffice");
							insertData.add(ob);
							InsertDataTable ob1= new InsertDataTable();
							ob1.setColName("regLevel");
							ob1.setDataName(request.getParameter("BranchofficeId"));
							insertData.add(ob1);
						}
						
					}else{
											if (parmname.equalsIgnoreCase("contactName"))
											{
												parmname = "empName";
												
											}
											else if (parmname.equalsIgnoreCase("deptname"))
											{
												vendordept = paramValue;
											}
											else if (parmname.equalsIgnoreCase("mobileNo"))
											{
												parmname = "mobOne";
											}
											else if (parmname.equalsIgnoreCase("contactEmailId"))
											{
												parmname = "emailIdOne";
											}
											else if (parmname.equalsIgnoreCase("dateOfBirth"))
											{
												parmname = "dob";
											}
											else if (parmname.equalsIgnoreCase("dateOfAnnvrsry"))
											{
												parmname = "doa";
											}
											else if (parmname.equalsIgnoreCase("dateOfJoining"))
											{
												parmname = "doj";
											}
											else if (parmname.equalsIgnoreCase("orgAddress"))
											{
												parmname = "address";
											}
											else if (parmname != null && parmname.equalsIgnoreCase("mobOne"))
											{
												contactMobNo = paramValue;
											}
											else if(parmname != null && parmname.equalsIgnoreCase("empId"))
											{
												contactId=paramValue;
												if(contactId!=null && !contactId.equalsIgnoreCase(""))
												{
													boolean exists=new HelpdeskUniversalHelper().isExist("employee_basic", "empId",contactId, connectionSpace); 
													if(exists)
													{
														addActionMessage("Contact Already Exists !!!");
														return SUCCESS;
													}
												}
											}
											
											List lowerLevel3 = new ArrayList<String>();
											Map<String, Object> temp = new HashMap<String, Object>();
											temp.put("mobOne", mobOne);
											if (getSubGroupId() != null)
											{
												temp.put("subGroupId", getSubGroupId());
											}
											lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("employee_basic", lowerLevel3, temp, connectionSpace);
											if (lowerLevel3 != null && lowerLevel3.size() > 0)
											{
												status = true;
												addActionMessage("Contact Already Exists. !!!");
												return "success";
											}
											if (!status)
											{
												if (parmname != null && !parmname.equalsIgnoreCase("status") && !parmname.equalsIgnoreCase("empLogo") && !parmname.equalsIgnoreCase("empDocument") && !parmname.equalsIgnoreCase("alternate_mobile") && !parmname.equalsIgnoreCase("alternate_email") && !parmname.equalsIgnoreCase("level") && !parmname.equalsIgnoreCase("vendorname") && !parmname.equalsIgnoreCase("vendornameDrop"))
												{
													ob = new InsertDataTable();
													ob.setColName(parmname);
													
													if (parmname.equalsIgnoreCase("doj") || parmname.equalsIgnoreCase("doa") || parmname.equalsIgnoreCase("dob"))
													{
														ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
													}
													else if (parmname.equalsIgnoreCase("orgName"))
													{
														if (paramValue != null && paramValue.equalsIgnoreCase(""))
														{
															ob.setDataName(orgName);
														}
														else
														{
															ob.setDataName(paramValue);
														}
													}
													else
													{
														ob.setDataName(paramValue);
													}
													insertData.add(ob);
												}
											}
											else
											{
						
											}
											
					}
				}//while end
										if (userTrue)
										{
											ob = new InsertDataTable();
											ob.setColName("userName");
											ob.setDataName(userName);
											insertData.add(ob);
										}
										if (dateTrue)
										{
											ob = new InsertDataTable();
											ob.setColName("date");
											ob.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(ob);
										}
										if (timeTrue)
										{
											ob = new InsertDataTable();
											ob.setColName("time");
											ob.setDataName(DateUtil.getCurrentTimeHourMin());
											insertData.add(ob);
										}
										
									//	System.out.println("getEmpDocumentFileName() is as "+getEmpDocumentFileName());
										String renameFilePath=null;
										String docPath1 = null, docPath2 = null;
										
										if (getEmpDocumentFileName() != null)
										{
											renameFilePath = new CreateFolderOs().createUserDir("Common_Data") + "//" + DateUtil.mergeDateTime() + getEmpDocumentFileName();
											String storeFilePath = new CreateFolderOs().createUserDir("Common_Data") + "//" + getEmpDocumentFileName();
											String str = renameFilePath.replace("//", "/");
											if (storeFilePath != null)
											{
												File theFile = new File(storeFilePath);
												File newFileName = new File(str);
												if (theFile != null)
												{
													try
													{
														FileUtils.copyFile(empDocument, theFile);
														if (theFile.exists())
															theFile.renameTo(newFileName);
													}
													catch (IOException e)
													{
														e.printStackTrace();
													}
													if (theFile != null)
													{
														docPath1 = renameFilePath;
						
														ob = new InsertDataTable();
														ob.setColName("empDocument");
														ob.setDataName(docPath1);
														insertData.add(ob);
														
													}
												}
											}
											/*
											System.out.println("Emp DOc Name is as :"+getEmpDocument());
											System.out.println("Content Type is as ::"+getEmpDocumentContentType());
											String filePath = request.getSession().getServletContext().getRealPath("/images/contactDoc");
											String docName = contactMobNo + "" + new KRActionHelper().getFileExtenstion(getEmpDocumentContentType());
											System.out.println("docNameis as ::::"+docName);
											File fileToCreate = new File(filePath, docName);
											FileUtils.copyFile(this.empLogo, fileToCreate);
											docName = "images/contactDoc/" + docName;*/
						
										/*	if (docName != null)
											{
												ob = new InsertDataTable();
												ob.setColName("empDocument");
												ob.setDataName(docName);
												insertData.add(ob);
											}*/
										}
						
										if (getEmpLogo() != null)
										{
											String filePath = request.getSession().getServletContext().getRealPath("/images/contact/");
											String profilePicName = contactMobNo + "" + new KRActionHelper().getFileExtenstion(getEmpLogoContentType());
											File fileToCreate = new File(filePath, profilePicName);
											FileUtils.copyFile(this.empLogo, fileToCreate);
											profilePicName = "images/contact/" + profilePicName;
						
											if (profilePicName != null)
											{
												ob = new InsertDataTable();
												ob.setColName("empLogo");
												ob.setDataName(profilePicName);
												insertData.add(ob);
											}
										}
										
				
				//status = cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
				int maxId=cbt.insertDataReturnId("employee_basic",insertData,connectionSpace);
				insertData.clear();
				
				String deptName = new CommonHelper().fetchDeptNameById(vendordept);
				// To add data in createvendor_table and vendor_contact_data.
				if(deptName!=null && deptName.equalsIgnoreCase("Existing Vendor"))
				{
					boolean res = addDataToVendor(requestParameterNames);
				}
				//System.out.println("vendordept  "+vendordept+" deptName  "+deptName);
				// To add data in createvendor_table and vendor_contact_data ends here.
				
            	if (maxId>0)
				{
            		StringBuilder fieldsNames=new StringBuilder();
            		StringBuilder fieldsValue=new StringBuilder();
            		if (insertData!=null && !insertData.isEmpty())
					{
						int i=1;
						for (InsertDataTable h : insertData)
						{
							for(GridDataPropertyView gdp:statusColName)
							{
								if(h.getColName().equalsIgnoreCase(gdp.getColomnName()))
								{
									if (i < insertData.size())
									{
										fieldsNames.append(gdp.getHeadingName()+", ");
										if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                    	{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString())+", ");
                                    	}
										else
										{
											fieldsValue.append(h.getDataName()+", ");
										}
										
									}
									else
									{
										fieldsNames.append(gdp.getHeadingName());
										if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                    	{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
                                    	}
										else
										{
											fieldsValue.append(h.getDataName());
										}
									}
								}
							}
							i++;
						}
					}
            		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
            		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Primary Contact",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
            		addActionMessage("Contact Added Successfully !!!");

				}
								return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addContact of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public boolean addDataToVendor(List<String> requestParameterNames)
	{
		boolean res = false;
		List<InsertDataTable> insertDatavendor = new ArrayList<InsertDataTable>();
		InsertDataTable obvendor = null;
		List<InsertDataTable> insertDataContact = new ArrayList<InsertDataTable>();
		InsertDataTable obContact = null;
		String vendornameId = null;
		
		Collections.sort(requestParameterNames);
		Iterator it = requestParameterNames.iterator();
		while (it.hasNext())
		{
			String parmname = it.next().toString();
			String paramValue = request.getParameter(parmname);
			if(parmname.equalsIgnoreCase("vendornameDrop"))
			{
				vendornameId = paramValue;
				System.out.println(vendornameId);
			}
			if(!parmname.equalsIgnoreCase("designation") && !parmname.equalsIgnoreCase("level") && !parmname.equalsIgnoreCase("alternate_mobile") && !parmname.equalsIgnoreCase("alternate_email") && !parmname.equalsIgnoreCase("dob") && !parmname.equalsIgnoreCase("doa") && !parmname.equalsIgnoreCase("comName") && !parmname.equalsIgnoreCase("contactName") && !parmname.equalsIgnoreCase("deptname") && !parmname.equalsIgnoreCase("regLevel") && !parmname.equalsIgnoreCase("groupId")  && !parmname.equalsIgnoreCase("date") && !parmname.equalsIgnoreCase("time") && !parmname.equalsIgnoreCase("userName") && !parmname.equalsIgnoreCase("vendornameDrop"))
			{
				obvendor = new InsertDataTable();
				obvendor.setColName(parmname);
				obvendor.setDataName(paramValue);
				insertDatavendor.add(obvendor);
			}
			
			if(!parmname.equalsIgnoreCase("vendorname")  && !parmname.equalsIgnoreCase("deptname") && !parmname.equalsIgnoreCase("regLevel") && !parmname.equalsIgnoreCase("groupId")  && !parmname.equalsIgnoreCase("date") && !parmname.equalsIgnoreCase("time") && !parmname.equalsIgnoreCase("userName") && !parmname.equalsIgnoreCase("status") && !parmname.equalsIgnoreCase("vendornameDrop"))
			{
				obContact = new InsertDataTable();
				obContact.setColName(parmname);
				if (parmname.equalsIgnoreCase("doa") || parmname.equalsIgnoreCase("dob"))
				{
					obContact.setDataName(DateUtil.convertDateToUSFormat(paramValue));
				}else
				{
					obContact.setDataName(paramValue);
				}
				
				insertDataContact.add(obContact);
			}
			
		}
		
		obvendor = new InsertDataTable();
		obvendor.setColName("status");
		obvendor.setDataName("Active");
		insertDatavendor.add(obvendor);
		
		obvendor = new InsertDataTable();
		obvendor.setColName("userName");
		obvendor.setDataName(userName);
		insertDatavendor.add(obvendor);
		
		
		obvendor = new InsertDataTable();
		obvendor.setColName("date");
		obvendor.setDataName(DateUtil.getCurrentDateUSFormat());
		insertDatavendor.add(obvendor);
		
	
		obvendor = new InsertDataTable();
		obvendor.setColName("time");
		obvendor.setDataName(DateUtil.getCurrentTimeHourMin());
		insertDatavendor.add(obvendor);
		
		/*obContact = new InsertDataTable();
		obContact.setColName("userName");
		obContact.setDataName(userName);
		insertDataContact.add(obContact);
		
		
		obContact = new InsertDataTable();
		obContact.setColName("date");
		obContact.setDataName(DateUtil.getCurrentDateUSFormat());
		insertDataContact.add(obContact);
		
	
		obContact = new InsertDataTable();
		obContact.setColName("time");
		obContact.setDataName(DateUtil.getCurrentTimeHourMin());
		insertDataContact.add(obContact);*/
		int maxId=0;
		if(vendornameId != null && !vendornameId.equalsIgnoreCase("-1")  && !vendornameId.equalsIgnoreCase("New Vendor"))
		{
			System.out.println("vendornameId in if  "+vendornameId); 
			if(Integer.parseInt(vendornameId)>0)
			{
				obContact = new InsertDataTable();
				obContact.setColName("vendorname");
				obContact.setDataName(vendornameId);
				insertDataContact.add(obContact);
				
				int maxIdcont =cbt.insertDataReturnId("vendor_contact_data",insertDataContact,connectionSpace);
				//System.out.println("maxIdcont  "+maxIdcont);
				if(maxIdcont>0)
				{res = true;}
			}
		}else{
			maxId=cbt.insertDataReturnId("createvendor_master",insertDatavendor,connectionSpace);
			if(maxId>0)
			{System.out.println("vendornameId in else  "+maxId); 
				obContact = new InsertDataTable();
				obContact.setColName("vendorname");
				obContact.setDataName(maxId);
				insertDataContact.add(obContact);
				
				int maxIdcont =cbt.insertDataReturnId("vendor_contact_data",insertDataContact,connectionSpace);
				//System.out.println("maxIdcont  "+maxIdcont);
				if(maxIdcont>0)
				{res = true;}
			}
		}
		
		return res;
	}

	public String getMappedContacts()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List data = cbt.executeAllSelectQuery(" select id,groupName from groupinfo where regLevel='" + getRegLevel() + "'", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Object c : data)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log
						.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getMappedContacts of class "
								+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getMappedGroup()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List data = cbt.executeAllSelectQuery(" select id,groupName from groupinfo where regLevel='" + getRegLevel() + "' AND status='Active'", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Object c : data)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getMappedGroup of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getSubGroup()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getGroupId() != null)
				{
					List dataList = cbt.executeAllSelectQuery("select id,deptName from department where groupId='" + getGroupId() + "'", connectionSpace);
					if (dataList != null & dataList.size() > 0)
					{
						for (Object c : dataList)
						{
							Object[] objVar = (Object[]) c;
							JSONObject listObject = new JSONObject();
							listObject.put("id", objVar[0].toString());
							listObject.put("name", objVar[1].toString());
							commonJSONArray.add(listObject);
						}
					}
					/*
					 * List data=newcreateTable().executeAllSelectQuery(
					 * "select groupName from groupinfo where id='"
					 * +getGroupId()+"'",connectionSpace); if(data!=null &&
					 * data.size()>0 && data.get(0)!=null) {
					 * if(data.get(0).toString().equalsIgnoreCase("Employee")) {
					 * List dataList=newcreateTable().executeAllSelectQuery(
					 * "select id,deptName from department where mappedOrgnztnId='"
					 * +getRegLevel()+"'", connectionSpace); if(dataList!=null &
					 * dataList.size()>0) { for (Object c : dataList) { Object[]
					 * objVar = (Object[]) c; JSONObject listObject=new
					 * JSONObject(); listObject.put("id",objVar[0].toString());
					 * listObject.put("name", objVar[1].toString());
					 * commonJSONArray.add(listObject); } } } else { List
					 * dataList=newcreateTable().executeAllSelectQuery(
					 * "select id,subGroup from subgroupinfo where groupId='"
					 * +getGroupId()+"'", connectionSpace); if(dataList!=null &
					 * dataList.size()>0) { for (Object c : dataList) { Object[]
					 * objVar = (Object[]) c; JSONObject listObject=new
					 * JSONObject(); listObject.put("id",objVar[0].toString());
					 * listObject.put("name", objVar[1].toString());
					 * commonJSONArray.add(listObject); } } } }
					 */
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSubGroup of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String getSubIndustry()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getIndustryId() != null)
				{
					List dataList = cbt.executeAllSelectQuery("select id,subIndustry from subindustrydatalevel2 where industry='" + getIndustryId() + "'", connectionSpace);
					if (dataList != null & dataList.size() > 0)
					{
						for (Object c : dataList)
						{
							Object[] objVar = (Object[]) c;
							JSONObject listObject = new JSONObject();
							listObject.put("id", objVar[0].toString());
							listObject.put("name", objVar[1].toString());
							commonJSONArray.add(listObject);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSubIndustry of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getSubContactName(SessionFactory connection)
	{
		String contactName = null;
		if (getSubGroupId() != null)
		{
			List data = cbt.executeAllSelectQuery("select subGroup from subgroupinfo where id='" + getSubGroupId() + "'", connection);
			if (data != null && data.size() > 0 && data.get(0) != null)
			{
				contactName = data.get(0).toString();
			}
		}
		return contactName;
	}

	public String getContactName(SessionFactory connection)
	{
		String contactName = null;
		if (getGroupId() != null)
		{
			List data = cbt.executeAllSelectQuery("select groupName from groupinfo where id='" + getGroupId() + "'", connection);
			if (data != null && data.size() > 0 && data.get(0) != null)
			{
				contactName = data.get(0).toString();
			}
		}
		return contactName;
	}

	public String getContactForm()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (getGroupId() != null)
				{
					String contactName = getContactName(connectionSpace);
					if (contactName != null && !contactName.equalsIgnoreCase(""))
					{
						if (contactName.equalsIgnoreCase("Employee"))
						{
							setContactDataFieldsToAdd("mapped_emp_contact_configuration", "emp_contact_configuration",contactName);
						}
						else if (contactName.equalsIgnoreCase("Associate"))
						{
							setContactDataFieldsToAdd("mapped_associate_basic_configuration", "associate_basic_configuration",contactName);
						}
						else if (contactName.equalsIgnoreCase("Client"))
						{
							setContactDataFieldsToAdd("mapped_client_contact_configuration", "client_contact_configuration",contactName);

							/*
							 * String
							 * subContact=getSubContactName(connectionSpace);
							 * System
							 * .out.println("Sub Contact Name is as "+subContact
							 * ); if(subContact!=null &&
							 * subContact.equalsIgnoreCase("Lead Management")) {
							 * setContactDataFieldsToAdd
							 * ("mapped_client_contact_configuration"
							 * ,"client_contact_configuration"); }
							 */
						}
						else if (contactName.equalsIgnoreCase("Vendor"))
						{
							setContactDataFieldsToAdd("mapped_vendor_contact_master", "vendor_contact_master",contactName);
						}
						else
						{
							setContactDataFieldsToAdd("mapped_emp_contact_configuration", "emp_contact_configuration",contactName);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getContactForm of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setContactDataFieldsToAdd(String mappedTable, String configTable, String contactName)
	{
		try
		{
			List<GridDataPropertyView> gridFields = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name", configTable);

			if (gridFields != null)
			{
				deptMap = new LinkedHashMap<Integer, String>();
				vendornameMap = new LinkedHashMap<String, String>();
				//vendornameMap=new CommonWork().fetchVendorNames(connectionSpace);
				contactFormDDBox = new ArrayList<ConfigurationUtilBean>();
				contactTextBox = new ArrayList<ConfigurationUtilBean>();
				industryMap = new LinkedHashMap<Integer, String>();
				sourceMap = new LinkedHashMap<Integer, String>();
				contactFileBox = new ArrayList<ConfigurationUtilBean>();
				contactDateTimeBox = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("createdDate")&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("createdAt") && !gdp.getColomnName().equalsIgnoreCase("userAccountId") && !gdp.getColomnName().equalsIgnoreCase("flag")&& !gdp.getColomnName().equalsIgnoreCase("time"))
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
						contactTextBox.add(objdata);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("createdDate")
							&& !gdp.getColomnName().equalsIgnoreCase("createdAt"))
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
						contactFileBox.add(objdata);

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("date"))
					{
						if (contactName!=null && contactName.equalsIgnoreCase("Associate")) 
						{
							if (!gdp.getColomnName().equalsIgnoreCase("doj")) 
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
								contactDateTimeBox.add(objdata);	
							}
						}
						else
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
							contactDateTimeBox.add(objdata);
							
						}
						
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("clientName"))
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
						contactFormDDBox.add(objdata);

						if (gdp.getColomnName().equalsIgnoreCase("industry"))
						{
							List data = cbt.executeAllSelectQuery("select id,industry from industrydatalevel1", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										industryMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}

							}
						}
						else if (gdp.getColomnName().equalsIgnoreCase("source"))
						{
							List data = cbt.executeAllSelectQuery("select id,sourceName from sourcemaster", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										sourceMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}

							}
						}
						if (gdp.getColomnName().equalsIgnoreCase("vendorname"))
						{
							List data = cbt.executeAllSelectQuery("select id, vendorname from createvendor_master order by vendorname", connectionSpace);
							int indexVal = 0;
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										// indexVal = Integer.parseInt(object[0].toString());
										vendornameMap.put(object[0].toString(), object[1].toString());
									}
								}
								vendornameMap.put("New Vendor", "New Vendor");

							}
						}
						else if (getRegLevel() != null)
						{
							List data = cbt.executeAllSelectQuery("select id,deptName from department where mappedOrgnztnId='" + getRegLevel() + "'", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										deptMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}
							}
						}
					}
					
					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactDataFieldsToAdd of class "
					+ getClass(), e);
		}
	}

	public void setContactAddPageDataFields()
	{
		try
		{
			List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name",
					"common_contact_configuration");
			if (gridFields != null)
			{
				contactTextBox = new ArrayList<ConfigurationUtilBean>();
				contactDateTimeBox = new ArrayList<ConfigurationUtilBean>();
				contactDD = new ArrayList<ConfigurationUtilBean>();
				contactFileBox = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D"))
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
						contactDD.add(objdata);

						if (gdp.getColomnName().equalsIgnoreCase("groupId"))
						{
							groupMap = new HashMap<Integer, String>();
							/*
							 * List data=new
							 * createTable().executeAllSelectQuery(
							 * " select id,groupName from groupinfo ",
							 * connectionSpace); if(data!=null && data.size()>0)
							 * { for (Iterator iterator = data.iterator();
							 * iterator .hasNext();) { Object[] object =
							 * (Object[]) iterator.next(); if(object[0]!=null &&
							 * object[1]!=null) {
							 * groupMap.put(Integer.parseInt(object
							 * [0].toString()), object[1].toString()); } } }
							 */
						}
						else if (gdp.getColomnName().equalsIgnoreCase("regLevel"))
						{
							String userType = (String)session.get("userTpe");
                        	officeMap=new LinkedHashMap<Integer, String>();
                        	String query =null;
                        	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                        	if (userType!=null && userType.equalsIgnoreCase("H")) 
                        	{
                        		hodStatus=true;
                        		query = "SELECT id,name FROM branchoffice_data WHERE headOfficeId = '"+s[2]+"' ORDER BY name";
							} 
                        	else if(userType!=null && userType.equalsIgnoreCase("M"))
                        	{
                        	    mgtStatus = true;
                        	    hodStatus = true;
                        		query = "SELECT id,name FROM country_data ORDER BY name";
                        	}
                        	else 
                        	{
                        		query = "SELECT id,groupName FROM groupinfo WHERE regLevel = '"+s[1]+"' ORDER BY groupName";
							}
            				if (query!=null)
            				{
            					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
                				if (dataList != null && dataList.size() > 0)
                				{
                					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
                					{
                						Object[] object = (Object[]) iterator.next();
                						if (object[0] != null && object[1] != null)
                						{
                							officeMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
                						}
                					}
                				}
							}
					   }
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("createdDate")
							&& !gdp.getColomnName().equalsIgnoreCase("createdAt") && !gdp.getColomnName().equalsIgnoreCase("userAccountId") && !gdp.getColomnName().equalsIgnoreCase("flag"))
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
						contactTextBox.add(objdata);

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("createdDate")
							&& !gdp.getColomnName().equalsIgnoreCase("createdAt"))
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
						contactFileBox.add(objdata);

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("date"))
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
						contactDateTimeBox.add(objdata);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactAddPageDataFields of class "
					+ getClass(), e);
		}
	}

	public void setContactUploadPageDataFields()
	{
		try
		{
			List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name",
					"common_contact_configuration");
			if (gridFields != null)
			{
				contactDD = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D"))
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

						if (gdp.getColomnName().equalsIgnoreCase("groupId"))
						{
							contactDD.add(objdata);
							groupMap = new HashMap<Integer, String>();
							List data = cbt.executeAllSelectQuery(" select id,groupName from groupinfo ", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}
							}
						}
						else if (gdp.getColomnName().equalsIgnoreCase("subGroupId"))
						{
							contactDD.add(objdata);
							subGroupMap = new HashMap<Integer, String>();
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactUploadPageDataFields of class "
					+ getClass(), e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String beforeContactViewHeader() 
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				groupMap=new LinkedHashMap<Integer, String>();
				deptMap=new LinkedHashMap<Integer, String>();

				String userType = (String)session.get("userTpe");
            	String query =null;
            	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
            	if (userType!=null && userType.equalsIgnoreCase("H")) 
            	{
            		hodStatus=true;
            		query = "SELECT id,name FROM branchoffice_data WHERE headOfficeId = '"+s[2]+"' ORDER BY name";
				} 
            	else if(userType!=null && userType.equalsIgnoreCase("M"))
            	{
            	    mgtStatus = true;
            	    hodStatus = true;
            		query = "SELECT id,name FROM country_data ORDER BY name";
            	}
            	else 
            	{
            		List data=cbt.executeAllSelectQuery("SELECT id ,groupName FROM groupinfo WHERE status='Active' AND regLevel="+s[1]+" ORDER BY groupName ASC", connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}
						}
					}
				}
				if (query!=null)
				{
					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
    				if (dataList != null && dataList.size() > 0)
    				{
    					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
    					{
    						Object[] object = (Object[]) iterator.next();
    						if (object[0] != null && object[1] != null)
    						{
    							deptMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
    						}
    					}
    				}
				}
				String userName = (String) session.get("uName");
				totalCount=new LinkedHashMap<String, String>();
				totalCount=getTotalCounters(userType,userName, connectionSpace);
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class "
								+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,String> getTotalCounters(String userType,String username,SessionFactory connectionSpace)
	{
		Map<String,String> counter=new LinkedHashMap<String, String>();
		
		StringBuilder builder=new StringBuilder(" Select count(*),contct.flag from employee_basic  AS contct LEFT JOIN department as dept on dept.id=contct.deptname LEFT join groupinfo as grp on grp.id=dept.groupId   ");
		builder.append(" LEFT join branchoffice_data as branch on grp.regLevel=branch.id  ");
		builder.append(" LEFT JOIN headoffice_data as head on head.id=branch.headOfficeId ");
		builder.append(" LEFT JOIN country_data as country on country.id=head.countryId ");
      	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
      	if (userType!=null && userType.equalsIgnoreCase("H")) 
      	{
      		builder.append("WHERE  head.id="+s[2] +"  ");
      		String deptId[]=new ComplianceUniversalAction().getEmpDataByUserName(userName);
	   		 if (deptId!=null && !deptId.toString().equalsIgnoreCase("")) 
	   		 {
	   			 builder.append(" AND  contct.deptname='"+deptId[3]+"'   ");
	   		 }
		} 
      	else if (userType!=null && userType.equalsIgnoreCase("N"))
      	{
      		builder.append("WHERE  branch.id="+s[1]+" AND contct.userName='"+userName+"' ");
		}
		builder.append(" GROUP BY contct.flag DESC ");
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0]!=null && object[1]!=null)
				{
					total=total+Integer.parseInt(object[0].toString());
					if ( object[1].toString().equalsIgnoreCase("0"))
					{
						counter.put( "Active",object[0].toString());
					}
					else
					{
						counter.put( "Inactive",object[0].toString());
					}
				}
			}
			counter.put("Total Records", String.valueOf(total));
		}
		return counter;
	}
	
	/*public String getCounters(String userType,String empIDS,SessionFactory connectionSpace)
	{
		String counter=null;
		
		StringBuilder builder=new StringBuilder(" SELECT count(*) FROM useraccount AS ua ");
		builder.append(" INNER JOIN employee_basic AS emp ON emp.useraccountid=ua.id  ");
		builder.append(" INNER JOIN department AS dept ON dept.id=emp.deptName  ");
		if(userType!=null && userType.equalsIgnoreCase("M"))
		{
			builder.append("where emp.regLevel=1");
		}
		
		if(userType!=null && userType.equalsIgnoreCase("N"))
		{
			builder.append("where emp.id=" +empIDS+ " ");
		}
		if(userType!=null && userType.equalsIgnoreCase("H"))
		{
			String deptId[]=new ComplianceUniversalAction().getEmpDataByUserName(userName);
		 if (deptId!=null && !deptId.toString().equalsIgnoreCase("")) 
		 {
			 builder.append(" AND contct.deptname='"+deptId[3]+"'  ");
			 builder.append(" AND contct.regLevel='1' ");
		 }
		}
		System.out.println(builder);
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			counter=dataList.get(0).toString();
		}
		return counter;
	}
	
	
	*/
	
	public String beforeContactView()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				setContactViewProp();
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class "
								+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setContactViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
	        gpv.setColomnName("country");
	        gpv.setHeadingName("Country Office");
	        gpv.setEditable("false");
	        gpv.setSearch("false");
	        gpv.setHideOrShow("false");
	        gpv.setWidth(80);
	        masterViewProp.add(gpv);
	        
	        gpv=new GridDataPropertyView();
	        gpv.setColomnName("head");
	        gpv.setHeadingName("Head Office");
	        gpv.setEditable("false");
	        gpv.setSearch("false");
	        gpv.setHideOrShow("false");
	        gpv.setWidth(80);
	        masterViewProp.add(gpv);

			List<GridDataPropertyView> emp =null;
			List<GridDataPropertyView> returnResult =null;
			List<GridDataPropertyView> lead =null;
			if (searchFields!=null &&  searchFields.equalsIgnoreCase("groupId"))
			{
				String groupName=fetchGroupNameById(SearchValue);
				if (groupName!=null && !groupName.equalsIgnoreCase(""))
				{
					returnResult = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name",
							"common_contact_configuration");
					if (groupName.equalsIgnoreCase("Employee"))
					{
						emp = Configuration.getConfigurationData("mapped_emp_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "emp_contact_configuration");
					}
					else if(groupName.equalsIgnoreCase("Client"))
					{
						emp = Configuration.getConfigurationData("mapped_emp_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "emp_contact_configuration");
						lead = Configuration.getConfigurationData("mapped_client_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
					}
				}
			}
			else
			{
				 emp = Configuration.getConfigurationData("mapped_emp_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "emp_contact_configuration");
				 returnResult = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name",
						"common_contact_configuration");
				lead = Configuration.getConfigurationData("mapped_client_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
			}
		
			// System.out.println(
			// "returnResult is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>."
			// +returnResult.size());
			if (emp!=null)
			{
				for (GridDataPropertyView gdp : emp)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter(gdp.getFormatter());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
			if (returnResult!=null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) 
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						gpv.setFormatter(gdp.getFormatter());
						masterViewProp.add(gpv);
					}
				}
			}
			if (lead!=null)
			{
				for (GridDataPropertyView gdp : lead)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("orgAddress") && !gdp.getColomnName().equalsIgnoreCase("contactName") && !gdp.getColomnName().equalsIgnoreCase("mobileNo")
							&& !gdp.getColomnName().equalsIgnoreCase("contactEmailId") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth")
							&& !gdp.getColomnName().equalsIgnoreCase("dateOfJoining") && !gdp.getColomnName().equalsIgnoreCase("dateOfAnnvrsry"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable("false");
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("userName");
			gpv.setHeadingName("Created By");
			gpv.setEditable("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("date");
			gpv.setHeadingName("Created On");
			gpv.setEditable("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			masterViewProp.add(gpv);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactViewProp of class " + getClass(), e);
		}
	}

	private String fetchGroupNameById(String searchValue2)
	{
		String result =
		null;
		try
		{
			List data=new createTable().executeAllSelectQuery("SELECT groupName FROM groupinfo WHERE id="+searchValue2, connectionSpace);
			if (data!=null && !data.isEmpty())
			{
				result=data.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public String getContBySubGroupId4User()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				String query = "SELECT id,empName FROM employee_basic WHERE deptname=" + subGroupId + " AND useraccountid IS NULL ORDER BY empName";
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null & dataList.size() > 0)
				{
					for (Object c : dataList)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getContBySubGroupId4User of class "
						+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String getEmpDetails()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				checkEmp = new LinkedHashMap<String, String>();
				String query = "SELECT empName,mobOne,emailIdOne FROM employee_basic WHERE id=" + empId;
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] objArr = (Object[]) dataList.get(0);

					checkEmp.put("name", CUA.getValueWithNullCheck(objArr[0]));
					checkEmp.put("mobileNo", CUA.getValueWithNullCheck(objArr[1]));
					checkEmp.put("email", CUA.getValueWithNullCheck(objArr[2]));
				}
				return SUCCESS;
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

	@SuppressWarnings("unchecked")
	public String beforeUpload()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{

				List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name",
						"common_contact_configuration");
				if (gridFields != null)
				{
					contactDD = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : gridFields)
					{
						ConfigurationUtilBean objdata = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
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
							contactDD.add(objdata);
						}
					}
				}

				officeMap = new LinkedHashMap<Integer, String>();
				groupMap = new LinkedHashMap<Integer, String>();
				String user = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				query.append("SELECT emp.id,emp.regLevel FROM employee_basic AS emp ");
				query.append(" INNER JOIN useraccount AS uac ON uac.id=emp.useraccountid ");
				query.append(" WHERE uac.userID='" + user + "'");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList == null || dataList.size() == 0)
				{
					List data = cbt.executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);

					if (data != null && data.size() > 0 && data.get(0) != null)
					{
						String arr[] = data.get(0).toString().split("#");
						for (int i = 0; i < arr.length; i++)
						{
							officeMap.put(i + 1, arr[i].toString());
						}
					}
					statusFlag = true;

				}
				else
				{
					Object[] obj = (Object[]) dataList.get(0);
					String regLevel = obj[1].toString();
					List groupDataList = cbt.executeAllSelectQuery("SELECT id,groupName FROM groupinfo WHERE regLevel=" + regLevel + " AND status='Active' ORDER BY groupName", connectionSpace);
					if (groupDataList != null && groupDataList.size() > 0)
					{
						for (Iterator iterator = groupDataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}

						}
					}

				}
				return SUCCESS;
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

	@SuppressWarnings("unchecked")
	public String downloadFormat()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				String orgName = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
				}
				List titleList = new ArrayList();
				
				if (groupName != null && groupName.equalsIgnoreCase("Employee"))
				{
					titleList.add("Emp Id");
					titleList.add("Emp Name");
					titleList.add("Communication Name");
					titleList.add("Mobile No");
					titleList.add("Email Id");
					titleList.add("Dept Name");
					titleList.add("Designation");
					titleList.add("Address");
					titleList.add("City");
					titleList.add("DOB");
					titleList.add("DOA");
					titleList.add("DOJ");
					titleList.add("Alternate Mobile No");
					titleList.add("Alternate Email Id");
				}
				else if(groupName != null)
				{
					titleList.add("Organisation Name");
					titleList.add("Address");
					titleList.add("Phone No.");
					titleList.add("Website");
					titleList.add("Location");
					titleList.add("Industry");
					titleList.add("Sub-Industry");
					titleList.add("Source");
					titleList.add("Official Email ID");
					titleList.add("Star");
					titleList.add("Potential / Size");
					titleList.add("Input");
					titleList.add("Contact Person");
					titleList.add("Communication Name");
					titleList.add("Influence");
					titleList.add("Mobile No");
					titleList.add("Email Id");
					titleList.add("Dept Name");
					titleList.add("Designation");
					titleList.add("Alternate Mobile No");
					titleList.add("Alternate Email Id");
					titleList.add("DOB");
					titleList.add("DOA");
					titleList.add("Acc Mgr No");
				}

				if(titleList!=null && titleList.size()>0)
				{
					excelFileName = new ComplianceExcelDownload().writeDataInExcel(null, titleList, null, "Excel Format for "+groupName, orgName, false, connectionSpace);
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

	@SuppressWarnings("unchecked")
	public String uploadContact()
	{
		int rowNo=0;
        String result=null;
        boolean validFlag = ValidateSession.checkSession();
        if (validFlag)
        {
            try
            {
                if (groupId != null)
                {
                    StringBuilder getRegLevelQuery = new StringBuilder();
                    getRegLevelQuery.append("SELECT regLevel FROM groupinfo WHERE id=" + groupId);
                    List dataList = cbt.executeAllSelectQuery(getRegLevelQuery.toString(), connectionSpace);
                    if (dataList != null && dataList.size() > 0)
                    {
                        regLevel = dataList.get(0).toString();
                    }
                }

                Map<String, String> empDataMap = null;
                Map<String, String> leadDataMap = null;
                Map<String, String> leadContDataMap = null;
                Map<String, String> clientDataMap = null;
                Map<String, String> clientContDataMap = null;
                Map<String, String> associateDataMap = null;
                Map<String, String> associateContDataMap = null;
                int maxId = 0;
                int leadMaxId = 0;
                int deptMaxId = 0;
                int clientMaxId = 0;
                int associateMaxId = 0;
                Map<String, String> columnDataMap = null;
                ComplianceUniversalAction CUA = new ComplianceUniversalAction();
                String contactId = null;
                if (groupName != null && !groupName.equalsIgnoreCase("Employee"))
                {
                    if(groupName.equalsIgnoreCase("Lead"))
                    {
                        contactId = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName)[0];
                    }
                    
                    InputStream inputStream = new FileInputStream(contactExcel);
                    if (contactExcelFileName.contains(".xlsx"))
                    {
                        GenericReadExcel7 GRBE = new GenericReadExcel7();
                        XSSFSheet excelSheet = GRBE.readExcel(inputStream);
                        rowNo=excelSheet.getPhysicalNumberOfRows()-3;
                        if(rowNo<1)
                        {
                            addActionMessage("Excel Sheet is Blank ");
                            result="error";
                        }
                        else
                        {
                        
                        for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
                        {
                            XSSFRow row = excelSheet.getRow(rowIndex);
                            int cellNo = row.getLastCellNum();
                            if (cellNo > 0)
                            {
                                if (rowIndex >= 3)
                                {
                                    empDataMap = new LinkedHashMap<String, String>();
                                    leadDataMap = new LinkedHashMap<String, String>();
                                    columnDataMap = new LinkedHashMap<String, String>();
                                    leadContDataMap = new LinkedHashMap<String, String>();
                                    clientDataMap = new LinkedHashMap<String, String>();
                                    clientContDataMap = new LinkedHashMap<String, String>();
                                    associateDataMap = new LinkedHashMap<String, String>();
                                    associateContDataMap = new LinkedHashMap<String, String>();
                                    
                                    for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
                                    {
                                        switch (cellIndex)
                                        {
                                            case 0:
                                            {
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    columnDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    leadMaxId = getMaxId("leadgeneration", columnDataMap);
                                                    if (leadMaxId == 0)
                                                    {
                                                        leadDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    columnDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    associateMaxId = getMaxId("associate_basic_data", columnDataMap);
                                                    if (associateMaxId == 0)
                                                    {
                                                        associateDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    columnDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    clientMaxId = getMaxId("client_basic_data", columnDataMap);
                                                    if (clientMaxId == 0)
                                                    {
                                                        clientDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    }
                                                }
                                                break;
                                            }
                                            case 1:
                                            {
                                                leadDataMap.put("leadAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 2:
                                            {
                                                leadDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
    
                                            case 3:
                                            {
                                                leadDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 4:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("location", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("location", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("location", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("location", String.valueOf(maxId));
                                                    }
                                                }
                                                break;
                                            }
                                            case 5:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("industry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("industrydatalevel1", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadDataMap.put("industry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("industrydatalevel1", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("industry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("industrydatalevel1", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("industry", String.valueOf(maxId));
                                                    }
                                                }
    
                                                break;
                                            }
                                            case 6:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("industry", String.valueOf(maxId));
                                                columnDataMap.put("subIndustry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("subindustrydatalevel2", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadDataMap.put("subIndustry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("subindustrydatalevel2", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("subIndustry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("subindustrydatalevel2", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("subIndustry", String.valueOf(maxId));
                                                    }
                                                }
    
                                                break;
                                            }
                                            case 7:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("sourceName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("sourcemaster", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadDataMap.put("sourceName", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("sourcemaster", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("sourceName", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("sourcemaster", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("sourceMaster", String.valueOf(maxId));
                                                    }
                                                }
    
                                                break;
                                            }
                                            case 8:
                                            {
                                                leadDataMap.put("email", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("associateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("companyEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 9:
                                            {
                                                leadDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("associateRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 10:
                                            {
                                                leadDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("size", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 11:
                                            {
                                                leadDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 12:
                                            {
                                                empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 13:
                                            {
                                                empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("communicationName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 14:
                                            {
                                                leadContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 15:
                                            {
                                                empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("contactNum", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 16:
                                            {
                                                empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("emailOfficialContact", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 17:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("deptName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("department", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadContDataMap.put("department", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("department", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateContDataMap.put("department", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("department", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientContDataMap.put("department", String.valueOf(maxId));
                                                    }
                                                }
                                            }
                                            case 18:
                                            {
                                                empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 19:
                                            {
                                                leadContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 20:
                                            {
                                                leadContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 21:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
                                                leadContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
                                                associateContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
                                                clientContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 22:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
                                                leadContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
                                                associateContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
                                                clientContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 23:
                                            {
                                                if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    associateDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
                                                    associateContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
    
                                                    clientDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
                                                    clientContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
    
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        addActionMessage("Excel Sheet Uploaded With "+rowNo+" Rows Successfully");
                        result="success";
                        }
                    }
                    else
                    {
                        GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
                        HSSFSheet excelSheet = GRBE.readExcel(inputStream);
                        rowNo=excelSheet.getPhysicalNumberOfRows()-3;
                        if(rowNo<1)
                        {
                            addActionMessage("Excel Sheet is Blank");
                            result="error";
                        }
                        else
                        {
                        for (int rowIndex = 3; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
                        {
                            HSSFRow row = excelSheet.getRow(rowIndex);
                            int cellNo = row.getLastCellNum();
                            if (cellNo > 0)
                            {
                                if (rowIndex >= 3)
                                {
                                    empDataMap = new LinkedHashMap<String, String>();
                                    leadDataMap = new LinkedHashMap<String, String>();
                                    columnDataMap = new LinkedHashMap<String, String>();
                                    leadContDataMap = new LinkedHashMap<String, String>();
                                    clientDataMap = new LinkedHashMap<String, String>();
                                    clientContDataMap = new LinkedHashMap<String, String>();
                                    associateDataMap = new LinkedHashMap<String, String>();
                                    associateContDataMap = new LinkedHashMap<String, String>();
                                    for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
                                    {
                                        switch (cellIndex)
                                        {
                                            case 0:
                                            {
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    columnDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    leadMaxId = getMaxId("leadgeneration", columnDataMap);
                                                    if (leadMaxId == 0)
                                                    {
                                                        leadDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    columnDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    associateMaxId = getMaxId("associate_basic_data", columnDataMap);
                                                    if (associateMaxId == 0)
                                                    {
                                                        associateDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    columnDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    clientMaxId = getMaxId("client_basic_data", columnDataMap);
                                                    if (clientMaxId == 0)
                                                    {
                                                        clientDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                    }
                                                }
                                                break;
                                            }
                                            case 1:
                                            {
                                                leadDataMap.put("leadAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 2:
                                            {
                                                leadDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
    
                                            case 3:
                                            {
                                                leadDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 4:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("location", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("location", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("location", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("location", String.valueOf(maxId));
                                                    }
                                                }
                                                break;
                                            }
                                            case 5:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("industry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("industrydatalevel1", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadDataMap.put("industry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("industrydatalevel1", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("industry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("industrydatalevel1", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("industry", String.valueOf(maxId));
                                                    }
                                                }
    
                                                break;
                                            }
                                            case 6:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("industry", String.valueOf(maxId));
                                                columnDataMap.put("subIndustry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("subindustrydatalevel2", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadDataMap.put("subIndustry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("subindustrydatalevel2", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("subIndustry", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("subindustrydatalevel2", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("subIndustry", String.valueOf(maxId));
                                                    }
                                                }
    
                                                break;
                                            }
                                            case 7:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("sourceName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("sourcemaster", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadDataMap.put("sourceName", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("sourcemaster", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateDataMap.put("sourceName", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("sourcemaster", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientDataMap.put("sourceMaster", String.valueOf(maxId));
                                                    }
                                                }
    
                                                break;
                                            }
                                            case 8:
                                            {
                                                leadDataMap.put("email", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("associateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("companyEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 9:
                                            {
                                                leadDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("associateRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 10:
                                            {
                                                leadDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("size", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 11:
                                            {
                                                leadDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 12:
                                            {
                                                empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 13:
                                            {
                                                empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("communicationName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 14:
                                            {
                                                leadContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 15:
                                            {
                                                empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("contactNum", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 16:
                                            {
                                                empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("emailOfficialContact", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 17:
                                            {
                                                columnDataMap.clear();
                                                columnDataMap.put("deptName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
    
                                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                                {
                                                    maxId = getMaxId("department", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        leadContDataMap.put("department", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    maxId = getMaxId("department", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        associateContDataMap.put("department", String.valueOf(maxId));
                                                    }
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
                                                    maxId = getMaxId("department", columnDataMap);
                                                    if (maxId > 0)
                                                    {
                                                        clientContDataMap.put("department", String.valueOf(maxId));
                                                    }
                                                }
                                            }
                                            case 18:
                                            {
                                                empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                leadContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 19:
                                            {
                                                leadContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 20:
                                            {
                                                leadContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                associateContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                clientContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 21:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
                                                leadContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
                                                associateContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
                                                clientContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 22:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
                                                leadContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
                                                associateContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
                                                clientContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 23:
                                            {
                                                if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                                {
                                                    associateDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
                                                    associateContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
                                                }
                                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                                {
    
                                                    clientDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
                                                    clientContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
    
                                                }
                                            }
                                        }
                                    }
                                }
                                if (empDataMap != null && empDataMap.size() > 0)
                                {
                                    String empName = null, mobileNo = null;
                                    List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                    for (Map.Entry entry : empDataMap.entrySet())
                                    {

                                        InsertDataTable object = new InsertDataTable();
                                        object.setColName(entry.getKey().toString());
                                        object.setDataName(entry.getValue().toString());
                                        insertData.add(object);
                                        if (entry.getKey().toString().equalsIgnoreCase("empName"))
                                            empName = entry.getValue().toString();

                                        if (entry.getKey().toString().equalsIgnoreCase("mobOne"))
                                            mobileNo = entry.getValue().toString();
                                    }

                                    InsertDataTable object = new InsertDataTable();
                                    object.setColName("regLevel");
                                    object.setDataName(regLevel);
                                    insertData.add(object);

                                    object = new InsertDataTable();
                                    object.setColName("groupId");
                                    object.setDataName(groupId);
                                    insertData.add(object);
                                    
                                    object = new InsertDataTable();
                                    object.setColName("flag");
                                    object.setDataName("0");
                                    insertData.add(object);

                                    object = new InsertDataTable();
                                    object.setColName("deptname");
                                    object.setDataName(subGroupId);
                                    insertData.add(object);
                                    if (empName != null && mobileNo != null && !empName.equalsIgnoreCase("NA") && !mobileNo.equalsIgnoreCase("NA") && groupName != null)
                                    {
                                        cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
                                    }
                                }
                                if (groupName != null && groupName.equalsIgnoreCase("Lead"))
                                {
                                    if (leadMaxId == 0 && leadDataMap != null && leadDataMap.size() > 0)
                                    {
                                        InsertDataTable object = null;
                                        List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                        for (Map.Entry entry : leadDataMap.entrySet())
                                        {
                                            object = new InsertDataTable();
                                            object.setColName(entry.getKey().toString());
                                            object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
                                            insertData.add(object);
                                        }

                                        object = new InsertDataTable();
                                        object.setColName("userName");
                                        object.setDataName(contactId);
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("date");
                                        object.setDataName(DateUtil.getCurrentDateUSFormat());
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("time");
                                        object.setDataName(DateUtil.getCurrentTime());
                                        insertData.add(object);

                                        leadMaxId = cbt.insertDataReturnId("leadgeneration", insertData, connectionSpace);
                                    }
                                    if (leadMaxId > 0 && leadContDataMap != null && leadContDataMap.size() > 0)
                                    {
                                        List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                        InsertDataTable object = null;
                                        for (Map.Entry entry : leadContDataMap.entrySet())
                                        {
                                            object = new InsertDataTable();
                                            object.setColName(entry.getKey().toString());
                                            object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
                                            insertData.add(object);
                                        }

                                        object = new InsertDataTable();
                                        object.setColName("userName");
                                        object.setDataName(contactId);
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("leadName");
                                        object.setDataName(leadMaxId);
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("date");
                                        object.setDataName(DateUtil.getCurrentDateUSFormat());
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("time");
                                        object.setDataName(DateUtil.getCurrentTime());
                                        insertData.add(object);

                                        cbt.insertIntoTable("lead_contact_data", insertData, connectionSpace);
                                    }
                                }
                                else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
                                {
                                    if (associateMaxId == 0 && associateDataMap != null && associateDataMap.size() > 0)
                                    {
                                        InsertDataTable object = null;
                                        List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                        for (Map.Entry entry : associateDataMap.entrySet())
                                        {
                                            object = new InsertDataTable();
                                            object.setColName(entry.getKey().toString());
                                            object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
                                            insertData.add(object);
                                        }

                                        object = new InsertDataTable();
                                        object.setColName("date");
                                        object.setDataName(DateUtil.getCurrentDateUSFormat());
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("time");
                                        object.setDataName(DateUtil.getCurrentTime());
                                        insertData.add(object);

                                        associateMaxId = cbt.insertDataReturnId("associate_basic_data", insertData, connectionSpace);
                                    }
                                    if (associateMaxId > 0 && associateContDataMap != null && associateContDataMap.size() > 0)
                                    {
                                        List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                        InsertDataTable object = null;
                                        for (Map.Entry entry : associateContDataMap.entrySet())
                                        {
                                            object = new InsertDataTable();
                                            object.setColName(entry.getKey().toString());
                                            object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
                                            insertData.add(object);
                                        }

                                        object = new InsertDataTable();
                                        object.setColName("associateName");
                                        object.setDataName(associateMaxId);
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("date");
                                        object.setDataName(DateUtil.getCurrentDateUSFormat());
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("time");
                                        object.setDataName(DateUtil.getCurrentTime());
                                        insertData.add(object);

                                        cbt.insertIntoTable("associate_contact_data", insertData, connectionSpace);
                                    }

                                }
                                else if (groupName != null && groupName.equalsIgnoreCase("Client"))
                                {

                                    if (clientMaxId == 0 && clientDataMap != null && clientDataMap.size() > 0)
                                    {
                                        InsertDataTable object = null;
                                        List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                        for (Map.Entry entry : clientDataMap.entrySet())
                                        {
                                            object = new InsertDataTable();
                                            object.setColName(entry.getKey().toString());
                                            object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
                                            insertData.add(object);
                                        }

                                        object = new InsertDataTable();
                                        object.setColName("date");
                                        object.setDataName(DateUtil.getCurrentDateUSFormat());
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("time");
                                        object.setDataName(DateUtil.getCurrentTime());
                                        insertData.add(object);

                                        clientMaxId = cbt.insertDataReturnId("client_basic_data", insertData, connectionSpace);
                                    }
                                    if (clientMaxId > 0 && clientContDataMap != null && clientContDataMap.size() > 0)
                                    {
                                        List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                        InsertDataTable object = null;
                                        for (Map.Entry entry : clientContDataMap.entrySet())
                                        {

                                            object = new InsertDataTable();
                                            object.setColName(entry.getKey().toString());
                                            object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
                                            insertData.add(object);
                                        }

                                        object = new InsertDataTable();
                                        object.setColName("clientName");
                                        object.setDataName(clientMaxId);
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("date");
                                        object.setDataName(DateUtil.getCurrentDateUSFormat());
                                        insertData.add(object);

                                        object = new InsertDataTable();
                                        object.setColName("time");
                                        object.setDataName(DateUtil.getCurrentTime());
                                        insertData.add(object);

                                        cbt.insertIntoTable("client_contact_data", insertData, connectionSpace);
                                    }

                                }
                            }
                        }
                        addActionMessage("Excel Uploaded with "+rowNo+" Rows Successfully");
                        result="success";
                        }

                    }
                }
                else if(groupName!=null && groupName.equalsIgnoreCase("Employee"))
                {
                    InputStream inputStream = new FileInputStream(contactExcel);
                    if (contactExcelFileName.contains(".xlsx"))
                    {
                        GenericReadExcel7 GRBE = new GenericReadExcel7();
                        XSSFSheet excelSheet = GRBE.readExcel(inputStream);
                        rowNo=excelSheet.getPhysicalNumberOfRows()-3;
                        if(rowNo<1)
                        {
                            addActionMessage("Excel Sheet is Blank");
                            result="error";
                        }
                        else
                        {
                        for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
                        {
                            XSSFRow row = excelSheet.getRow(rowIndex);
                            int cellNo = row.getLastCellNum();
                            if (cellNo > 0)
                            {
                                if (rowIndex >= 3)
                                {
                                    empDataMap = new LinkedHashMap<String, String>();
                                    for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
                                    {
                                        switch (cellIndex)
                                        {
                                            case 0:
                                            {
                                                empDataMap.put("empId", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 1:
                                            {
                                                empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 2:
                                            {
                                                empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 3:
                                            {
                                                empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 4:
                                            {
                                                empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 5:
                                            {
                                                deptMaxId = getDeptIdByGroupId(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                if (deptMaxId > 0)
                                                {
                                                    empDataMap.put("deptname", String.valueOf(deptMaxId));
                                                }
                                            }
                                            case 6:
                                            {
                                                empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 7:
                                            {
                                                empDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 8:
                                            {
                                                empDataMap.put("city", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 9:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 10:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 11:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("doj", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 12:
                                            {
                                                empDataMap.put("mobTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 13:
                                            {
                                                empDataMap.put("emailIdTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                        }
                                    }
                                
                                }
                                if (empDataMap != null && empDataMap.size() > 0)
                                {
                                    String empName = null, mobileNo = null;
                                    List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                    for (Map.Entry entry : empDataMap.entrySet())
                                    {

                                        InsertDataTable object = new InsertDataTable();
                                        object.setColName(entry.getKey().toString());
                                        object.setDataName(entry.getValue().toString());
                                        insertData.add(object);
                                        if (entry.getKey().toString().equalsIgnoreCase("empName"))
                                            empName = entry.getValue().toString();

                                        if (entry.getKey().toString().equalsIgnoreCase("mobOne"))
                                            mobileNo = entry.getValue().toString();
                                    }

                                    InsertDataTable object = new InsertDataTable();
                                    object.setColName("regLevel");
                                    object.setDataName(regLevel);
                                    insertData.add(object);

                                    object = new InsertDataTable();
                                    object.setColName("groupId");
                                    object.setDataName(groupId);
                                    insertData.add(object);

                                    if (empName != null && mobileNo != null && !empName.equalsIgnoreCase("NA") && !mobileNo.equalsIgnoreCase("NA") && groupName != null)
                                    {
                                        cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
                                    }
                                }
                                
                            }
                        }
                        addActionMessage("Excel Uploaded with "+rowNo+" Rows Successfully");
                        result="success";
                        }
                    }
                    else
                    {
                        GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
                        HSSFSheet excelSheet = GRBE.readExcel(inputStream);
                        rowNo=excelSheet.getPhysicalNumberOfRows()-3;
                        if(rowNo<1)
                        {
                            addActionMessage("Excel Sheet is Blank");
                            result="error";
                        }
                        else
                        {
                        for (int rowIndex = 3; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
                        {
                            HSSFRow row = excelSheet.getRow(rowIndex);
                            int cellNo = row.getLastCellNum();
                            if (cellNo > 0)
                            {
                                if (rowIndex >= 3)
                                {
                                    empDataMap = new LinkedHashMap<String, String>();
                                    for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
                                    {
                                        switch (cellIndex)
                                        {
                                            case 0:
                                            {
                                                empDataMap.put("empId", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 1:
                                            {
                                                empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 2:
                                            {
                                                empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 3:
                                            {
                                                empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 4:
                                            {
                                                empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 5:
                                            {
                                                deptMaxId = getDeptIdByGroupId(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                if (deptMaxId > 0)
                                                {
                                                    empDataMap.put("deptname", String.valueOf(deptMaxId));
                                                }
                                            }
                                            case 6:
                                            {
                                                empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 7:
                                            {
                                                empDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 8:
                                            {
                                                empDataMap.put("city", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 9:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 10:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 11:
                                            {
                                                String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                empDataMap.put("doj", CUA.getValueWithNullCheck(newDate));
                                                break;
                                            }
                                            case 12:
                                            {
                                                empDataMap.put("mobTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                            case 13:
                                            {
                                                empDataMap.put("emailIdTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (empDataMap != null && empDataMap.size() > 0)
                                {
                                    String empName = null, mobileNo = null;
                                    List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                                    for (Map.Entry entry : empDataMap.entrySet())
                                    {

                                        InsertDataTable object = new InsertDataTable();
                                        object.setColName(entry.getKey().toString());
                                        object.setDataName(entry.getValue().toString());
                                        insertData.add(object);
                                        if (entry.getKey().toString().equalsIgnoreCase("empName"))
                                            empName = entry.getValue().toString();

                                        if (entry.getKey().toString().equalsIgnoreCase("mobOne"))
                                            mobileNo = entry.getValue().toString();
                                    }

                                    InsertDataTable object = new InsertDataTable();
                                    object.setColName("regLevel");
                                    object.setDataName(regLevel);
                                    insertData.add(object);

                                    object = new InsertDataTable();
                                    object.setColName("groupId");
                                    object.setDataName(groupId);
                                    insertData.add(object);

                                    if (empName != null && mobileNo != null && !empName.equalsIgnoreCase("NA") && !mobileNo.equalsIgnoreCase("NA") && groupName != null)
                                    {
                                        
                                        cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
                                    }
                                }
                            }
                        }
                        addActionMessage("Excel Uploaded with "+rowNo+" Rows Successfully");
                        result="success";
                        }
                    }
                
                }
                
            
                return result;
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

	public int getDeptIdByGroupId(String deptName)
	{
		int id = 0;
		StringBuilder str = new StringBuilder();
		str.append("SELECT dept.id FROM department AS dept ");
		str.append(" INNER JOIN groupinfo AS grinfo ON grinfo.id=dept.groupId ");
		str.append(" WHERE grinfo.groupName='Employee' AND dept.deptName='"+deptName+"'");
		List dataList = cbt.executeAllSelectQuery(str.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			id = Integer.valueOf(dataList.get(0).toString());
		}
		return id;
	}
	
	public int getMaxId(String tableName, Map<String, String> condtnValue)
	{
		int id = 0;
		StringBuilder str = new StringBuilder();
		str.append("SELECT id FROM " + tableName + " WHERE ");
		int i = 0;
		for (Map.Entry entry : condtnValue.entrySet())
		{
			if (i == 0)
			{
				str.append(entry.getKey().toString() + "='" + entry.getValue().toString() + "' ");
			}
			else
			{
				str.append(" AND " + entry.getKey().toString() + "='" + entry.getValue().toString() + "' ");
			}
			i++;
		}
		List dataList = cbt.executeAllSelectQuery(str.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			id = Integer.valueOf(dataList.get(0).toString());
		}
		return id;
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

	public String getContBySubGroupId4UserReset()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				String query = "SELECT id,empName FROM employee_basic WHERE deptname=" + subGroupId + " AND useraccountid is not null ORDER BY empName";

				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null & dataList.size() > 0)
				{
					for (Object c : dataList)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getContBySubGroupId4User of class "
						+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getContIdByMobileNo(String mobileNo, String moduleName)
	{
		String contId = null;
		try
		{
			StringBuilder str = new StringBuilder();
			str.append("select contact.id from compliance_contacts as contact ");
			str.append(" inner join  employee_basic as emp on emp.id=contact.emp_id AND emp.deptname=contact.forDept_id ");
			str.append(" where contact.moduleName='" + moduleName + "' AND emp.mobOne='" + mobileNo + "'");

			List dataList = cbt.executeAllSelectQuery(str.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				contId = dataList.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return contId;
	}

	
	public String editCommonContact()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) 
					{
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
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
						StringBuilder query=new StringBuilder("UPDATE employee_basic SET flag='1' WHERE id IN("+condtIds+")");
						cbt.updateTableColomn(connectionSpace,query);
				}
				return SUCCESS;
			}
			catch(Exception e)
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
	
	public String getDeptBySubGroup() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				jssonArr = new JSONArray();
				JSONObject ob;
				StringBuilder query =new StringBuilder();
				query.append("SELECT dept.id,dept.deptName FROM department AS dept ");
				query.append(" INNER JOIN groupinfo AS gi ON dept.groupId=gi.id ");
				query.append(" WHERE gi.id="+selectedId+" GROUP BY dept.deptName ");
			//	System.out.println("Query String "+query.toString());
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob=new JSONObject();
						Object[] object = (Object[]) iterator.next();
						ob.put("id", object[0].toString());
						ob.put("name", object[1].toString());
						jssonArr.add(ob);
						//System.out.println("deptMap :::"  +jssonArr);
					}
				}
				
				return SUCCESS;
			}
			catch(Exception e)
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
	
	
	public String getMappedDepartment()
	{
		//System.out.println("  getMappedDepartment:::    ");
		boolean valid = ValidateSession.checkSession();
		if (valid) {
			try {
				List data = cbt.executeAllSelectQuery(
						" SELECT dept.id,dept.deptName,gi.id as groupId FROM department as dept INNER JOIN "
						+ "groupinfo AS gi ON gi.id=dept.groupId WHERE gi.groupName='Employee' " 
						+ " AND mappedOrgnztnId='"+getRegLevel() + "' Order by  dept.deptName ASC", connectionSpace);
				if (data != null && data.size() > 0) {
					for (Object c : data) {
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						listObject.put("groupId", objVar[2].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(
						"Date : "
								+ DateUtil.getCurrentDateIndianFormat()
								+ " Time: "
								+ DateUtil.getCurrentTime()
								+ " "
								+ "Problem in Over2Cloud  method getMappedGroup of class "
								+ getClass(), e);
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}
	
	public String officeMappingList(){
		
		String returresult=ERROR;
		boolean flag= ValidateSession.checkSession();
		if(flag){
			
					try{
						jssonArr = new JSONArray();
						String query=null;
						if(officeFlag!=null && officeFlag.equalsIgnoreCase("c")){
							
							query="SELECT id,name from country_data";
							
						}
						else if(officeFlag!=null && officeFlag.equalsIgnoreCase("h") && country!=null && !country.equalsIgnoreCase(""))
						{
							query="SELECT id,name from headoffice_data where countryId='"+getCountry()+"'";
							
						}else if(officeFlag!=null && officeFlag.equalsIgnoreCase("b") && country!=null && !country.equalsIgnoreCase("") 
								&& headoffice!=null && !headoffice.equalsIgnoreCase(""))
						{
							query="SELECT id,name from branchoffice_data where countryId='"+getCountry()+"' AND headOfficeId='"+getHeadoffice()+"'";
						}
						
					List<?> data= new CommonOperAtion().executeAllSelectQuery(query, connectionSpace);
							
							for (Iterator iterator = data.iterator(); iterator
									.hasNext();) 
							{
								Object [] object = (Object[]) iterator.next();
								JSONObject jobj= new JSONObject();
								jobj.put("ID", object[0].toString());
								jobj.put("NAME", object[1].toString());
								jssonArr.add(jobj);
							}
							returresult=SUCCESS;
					}catch(Exception e){
						e.printStackTrace(); 
						log.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method officeMappingList of class "
										+ getClass(), e);
						return ERROR;
					}
			
			
		}else{
			returresult=LOGIN;
		}
		return returresult; 
	}
	
	public JSONArray getJssonArr() {
		return jssonArr;
	}

	public void setJssonArr(JSONArray jssonArr) {
		this.jssonArr = jssonArr;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<ConfigurationUtilBean> getContactTextBox()
	{
		return contactTextBox;
	}

	public void setContactTextBox(List<ConfigurationUtilBean> contactTextBox)
	{
		this.contactTextBox = contactTextBox;
	}

	public Map<Integer, String> getGroupMap()
	{
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap)
	{
		this.groupMap = groupMap;
	}

	public Map<Integer, String> getSubGroupMap()
	{
		return subGroupMap;
	}

	public void setSubGroupMap(Map<Integer, String> subGroupMap)
	{
		this.subGroupMap = subGroupMap;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<ConfigurationUtilBean> getContactDateTimeBox()
	{
		return contactDateTimeBox;
	}

	public void setContactDateTimeBox(List<ConfigurationUtilBean> contactDateTimeBox)
	{
		this.contactDateTimeBox = contactDateTimeBox;
	}

	public List<ConfigurationUtilBean> getContactDD()
	{
		return contactDD;
	}

	public void setContactDD(List<ConfigurationUtilBean> contactDD)
	{
		this.contactDD = contactDD;
	}

	public Map<Integer, String> getOfficeMap()
	{
		return officeMap;
	}

	public void setOfficeMap(Map<Integer, String> officeMap)
	{
		this.officeMap = officeMap;
	}

	public Map<Integer, String> getDeptMap()
	{
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<Integer, String> getDesgMap()
	{
		return desgMap;
	}

	public void setDesgMap(Map<Integer, String> desgMap)
	{
		this.desgMap = desgMap;
	}

	public String getSubGroupId()
	{
		return subGroupId;
	}

	public void setSubGroupId(String subGroupId)
	{
		this.subGroupId = subGroupId;
	}

	public List<ConfigurationUtilBean> getContactFormDDBox()
	{
		return contactFormDDBox;
	}

	public void setContactFormDDBox(List<ConfigurationUtilBean> contactFormDDBox)
	{
		this.contactFormDDBox = contactFormDDBox;
	}

	public List<ConfigurationUtilBean> getContactFileBox()
	{
		return contactFileBox;
	}

	public void setContactFileBox(List<ConfigurationUtilBean> contactFileBox)
	{
		this.contactFileBox = contactFileBox;
	}

	public Map<Integer, String> getIndustryMap()
	{
		return industryMap;
	}

	public void setIndustryMap(Map<Integer, String> industryMap)
	{
		this.industryMap = industryMap;
	}

	public String getIndustryId()
	{
		return industryId;
	}

	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	public String getRegLevel()
	{
		return regLevel;
	}

	public void setRegLevel(String regLevel)
	{
		this.regLevel = regLevel;
	}

	public Map<String, String> getCheckEmp()
	{
		return checkEmp;
	}

	public void setCheckEmp(Map<String, String> checkEmp)
	{
		this.checkEmp = checkEmp;
	}

	public String getMobOne()
	{
		return mobOne;
	}

	public void setMobOne(String mobOne)
	{
		this.mobOne = mobOne;
	}

	public File getEmpLogo()
	{
		return empLogo;
	}

	public void setEmpLogo(File empLogo)
	{
		this.empLogo = empLogo;
	}

	public String getEmpLogoContentType()
	{
		return empLogoContentType;
	}

	public void setEmpLogoContentType(String empLogoContentType)
	{
		this.empLogoContentType = empLogoContentType;
	}

	public String getEmpLogoFileName()
	{
		return empLogoFileName;
	}

	public void setEmpLogoFileName(String empLogoFileName)
	{
		this.empLogoFileName = empLogoFileName;
	}

	public File getEmpDocument()
	{
		return empDocument;
	}

	public void setEmpDocument(File empDocument)
	{
		this.empDocument = empDocument;
	}

	public String getEmpDocumentContentType()
	{
		return empDocumentContentType;
	}

	public void setEmpDocumentContentType(String empDocumentContentType)
	{
		this.empDocumentContentType = empDocumentContentType;
	}

	public String getEmpDocumentFileName()
	{
		return empDocumentFileName;
	}

	public void setEmpDocumentFileName(String empDocumentFileName)
	{
		this.empDocumentFileName = empDocumentFileName;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	/*
	 * public InputStream getInputStream() { return inputStream; }
	 * 
	 * public void setInputStream(InputStream inputStream) { this.inputStream =
	 * inputStream; }
	 */

	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public boolean isStatusFlag()
	{
		return statusFlag;
	}
	public void setStatusFlag(boolean statusFlag)
	{
		this.statusFlag = statusFlag;
	}
	public File getContactExcel()
	{
		return contactExcel;
	}
	public void setContactExcel(File contactExcel)
	{
		this.contactExcel = contactExcel;
	}
	public String getContactExcelContentType()
	{
		return contactExcelContentType;
	}
	public void setContactExcelContentType(String contactExcelContentType)
	{
		this.contactExcelContentType = contactExcelContentType;
	}
	public String getContactExcelFileName()
	{
		return contactExcelFileName;
	}
	public void setContactExcelFileName(String contactExcelFileName)
	{
		this.contactExcelFileName = contactExcelFileName;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	public String getContId() {
		return contId;
	}
	public void setContId(String contId) {
		this.contId = contId;
	}
	public Map<String, String> getDocMap() {
		return docMap;
	}
	public void setDocMap(Map<String, String> docMap) {
		this.docMap = docMap;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public Map<String, String> getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Map<String, String> totalCount)
	{
		this.totalCount = totalCount;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getSearchValue() {
		return SearchValue;
	}

	public void setSearchValue(String searchValue) {
		SearchValue = searchValue;
	}
	

	public JSONArray getJsonObjArray() {
		return jsonObjArray;
	}

	public void setJsonObjArray(JSONArray jsonObjArray) {
		this.jsonObjArray = jsonObjArray;
	}

	
	public JSONArray getUserTypeMap() {
		return userTypeMap;
	}

	public void setUserTypeMap(JSONArray userTypeMap) {
		this.userTypeMap = userTypeMap;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public Map<Integer, String> getSourceMap()
	{
		return sourceMap;
	}

	public void setSourceMap(Map<Integer, String> sourceMap)
	{
		this.sourceMap = sourceMap;
	}


	
	public Map<String, String> getVendornameMap() {
		return vendornameMap;
	}

	public void setVendornameMap(Map<String, String> vendornameMap) {
		this.vendornameMap = vendornameMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;

	}

	public String getOfficeFlag() {
		return officeFlag;
	}

	public void setOfficeFlag(String officeFlag) {
		this.officeFlag = officeFlag;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadoffice() {
		return headoffice;
	}

	public void setHeadoffice(String headoffice) {
		this.headoffice = headoffice;
	}

	public String getBranchoffice() {
		return branchoffice;
	}

	public void setBranchoffice(String branchoffice) {
		this.branchoffice = branchoffice;
	}

	public boolean isHodStatus() {
		return hodStatus;
	}

	public void setHodStatus(boolean hodStatus) {
		this.hodStatus = hodStatus;
	}

	public boolean isMgtStatus() {
		return mgtStatus;
	}

	public void setMgtStatus(boolean mgtStatus) {
		this.mgtStatus = mgtStatus;
	}
	
}