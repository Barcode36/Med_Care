package com.Over2Cloud.ctrl.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.pool.Size;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.organization.Avatar;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EmployeeActionCtrl extends ActionSupport implements ServletRequestAware{

	static final Log log = LogFactory.getLog(EmployeeActionCtrl.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	String encryptedID=(String)session.get("encryptedID");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private Map<Integer, String> subDepartmentList=null;
	private Map<Integer, String> designationList=null;
	private Map<String, String> empListData=null;
	private String level1=new String();
	private int levelOforganization=2;
	//ajax for designation
	private String orgIdInLevel;
	private String selectedorgId;
	//ajax for sub-dept
	private String deptID;
	//ajax callinf for checking existance of mob in DB
	private Map<String, String> checkEmp;
	
	//EMP BASIC data entry parameters
	private String subdept;
	private String designation;
	private String doj_date;
	private String empName;
	private String comName;
	private String mobOne;
	private String status;
	private String mobTwo;
	private String emailIdOne;
	private String emailIdTwo;
	private String salary;
	private String ccontactNo;
	private String address;
	private String landmark;
	private String city;
	private String cpincode;
	private File empLogo;
	private String empLogoContentType;
	private String empLogoFileName;
	//private File empDocument;
	private String empDocumentContentType;
	private String empDocumentFileName;
	private String empId;
	 private List<ConfigurationUtilBean> empWorkExpFileBox;
	 private List<ConfigurationUtilBean> empPerFileBox;
	public File empDocument;
	
	//Employee Perosnal data entry
	
	
	//private String empName; -->For uniquely finding the detail employee on the basis of mobile number
	 private String fname;
	 private String fmob;
	 private String mname;
	 private String edob_date;
	 private String doa_date;
	 private String sname;
	 private String sdob_date;
	 private String coneName;
	 private String conedob_date;
	 private String ctwoName;
	 private String ctwodob_date;
	 private String addressPer;
	 private String landmarkPer;
	 private String cityPer;
	 private String pincode;
	 private String contactNo;
	 
	 //Employee Bank Account Detail property
	//private String empName; -->For uniquely finding the detail employee on the basis of mobile number
	 private String bankName;
	 private String bankAddress;
	 private String accountNo;
	 private String ifscCode;
	 private String acType;
	 private List<ConfigurationUtilBean> bankFileBox;
	 
	//employee qualification properties 
	//private String empName; -->For uniquely finding the detail employee on the basis of mobile number
	 private String acadmic;
	 private String aggregate;
	 private String subject;
	 private String year;
	 private String college;
	 private String university;
	 private String docFile;
	//employee work exprience properties 
	 private String cname;
	 private String caddress;
	 private String workExpDesg;
	 private String cdept;
	 //private String salary; -->For previous salary
	 private String workEmpId;
	 private String pfrom;
	 private String pto;
	 private String refrenceOne;
	 private String refOneDesg;
	 private String refOneMobNo;
	 private String refOneEmailId;
	 private HttpServletRequest request;

	 private String deptHierarchy;
	 private String deptname;
	 private Map<String, String> getAllSubDept;
	 private Map<String, String> getSubDeptMake;
	 private Map<String, String> getAllDept;
	 private String subDeptIDgggggggggg;
	 private String newPwd;
	 private String changeType;
	 private String userAccountType;
	 boolean hod=false;
	 boolean mgmt=false;
	 boolean normalUser=false;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	 JSONObject responseDetailsJson = null;
	 JSONArray jsonArray = null;

		public JSONObject getResponseDetailsJson()
		{
			return responseDetailsJson;
		}

		public void setResponseDetailsJson(JSONObject responseDetailsJson)
		{
			this.responseDetailsJson = responseDetailsJson;
		}
	 
	 
	 /**
	  * ajax calling for getting the all the mapped designation with an organization based 
	  * on the organzaition ID and the level of organization
	  */
		public String beforeChangePwdFromAccount()
		{
			try 
			{
				normalUser=true;
				empName=(String) session.get("empIdofuser").toString().split("-")[1];
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return SUCCESS;
		}
	public String getDesignationData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			designationList=new HashMap<Integer, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List lowerLevel3=new ArrayList<String>();
			lowerLevel3.add("id");
			lowerLevel3.add("designationname");
			Map<String, Object> temp=new HashMap<String, Object>();
			temp.put("orgnztnlevel",getOrgIdInLevel());
			temp.put("mappedOrgnztnId",getSelectedorgId());
			lowerLevel3=cbt.viewAllDataEitherSelectOrAll("designation",lowerLevel3,temp,connectionSpace);
			if(lowerLevel3!=null && lowerLevel3.size()>0)
			{
				for (Object c : lowerLevel3) {
					Object[] dataC = (Object[]) c;
					designationList.put((Integer)dataC[0], dataC[1].toString());
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getDesignationData of class "+getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * getting the list of all sub -dept based on the selected dept ,
	 * dept is mapped with the organization id and the level of organization hierarchy using the AJAX.
	 * @return
	 */
	
	public String getSubDept()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			subDepartmentList=new HashMap<Integer, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List lowerLevel3=new ArrayList<String>();
			lowerLevel3.add("id");
			lowerLevel3.add("subdeptname");
			Map<String, Object> temp=new HashMap<String, Object>();
			temp.put("deptid",getDeptID());
			lowerLevel3=cbt.viewAllDataEitherSelectOrAll("subdepartment",lowerLevel3,temp,connectionSpace);
			if(lowerLevel3!=null && lowerLevel3.size()>0)
			{
				for (Object c : lowerLevel3) {
					Object[] dataC = (Object[]) c;
					subDepartmentList.put((Integer)dataC[0], dataC[1].toString());
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getSubDept of class "+getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
		
		
/*
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			System.out.println("deptid>>>>>>>"+getDeptID());
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List lowerLevel3 = new ArrayList<String>();
			lowerLevel3.add("id");
			lowerLevel3.add("subdeptname");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("deptid", getDeptID());
			lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("subdepartment",
					lowerLevel3, temp, connectionSpace);
			System.out.println("lowerLevel3 " + lowerLevel3.size());
			if (lowerLevel3 != null && lowerLevel3.size() > 0)
			{

				jsonArray = new JSONArray();
				for (Iterator it = lowerLevel3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					System.out.println("obdata[1].toString()"+obdata[1].toString());
					if (obdata != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("id", obdata[0].toString());
						formDetailsJson
								.put("subdeptname", obdata[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;*/
	
		
		
	}
	
	public String getSubDept2()
	{
		

		try
		{
		
			
			
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List lowerLevel3 = new ArrayList<String>();
			lowerLevel3.add("id");
			lowerLevel3.add("subdeptname");
			Map<String, Object> temp = new HashMap<String, Object>();
			Map<String, Object> order=new HashMap<String, Object>();
			temp.put("deptid", getDeptID());
			order.put("subdeptname", "ASC");
			lowerLevel3 = cbt.viewAllDataEitherSelectOrAllByOrder("subdepartment",
					lowerLevel3, temp,order, connectionSpace);
			System.out.println("lowerLevel3 " + lowerLevel3.size());
			if (lowerLevel3 != null && lowerLevel3.size() > 0)
			{

				jsonArray = new JSONArray();
				for (Iterator it = lowerLevel3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					System.out.println("obdata[1].toString()"+obdata[1].toString());
					if (obdata != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("id", obdata[0].toString());
						formDetailsJson
								.put("subdeptname", obdata[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	
		
		
	}
	
	
	
	/**
	 * Method for checking that the mobile no is already exist in database or not, 
		If the eemployee mobile number is exist in the DB, it will return true
		if the mobile number dose not exist in the DB it will return FALSE, so a user can create with that mobile number,
		IF true is return no employee can create with that mobile number.
	 * 
	 */
	 
	public String getEmpAvailability() {
		String returnResult = ERROR;
		try {
			
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				       if (mobOne != null){
					       checkEmp=new HashMap<String, String>();
					       //check user mobile number
					       boolean sttaus=false;
					       try
					       {
					    	   List lowerLevel3=new ArrayList<String>();
					    	   Map<String, Object> temp=new HashMap<String, Object>();
					    	   temp.put("mobOne",mobOne);
					    	   CommonOperInterface cbt=new CommonConFactory().createInterface();
					    	   lowerLevel3=cbt.viewAllDataEitherSelectOrAll("employee_basic",lowerLevel3,temp,connectionSpace);
								if(lowerLevel3!=null && lowerLevel3.size()>0)
								{
									sttaus=true;
								}
					       }
					       catch(Exception e)
					       {
					    	   
					       }
				       if(!sttaus){
					         checkEmp.put("msg","You Can Create.");
					         checkEmp.put("status","false");
				       }
				       else{
					         checkEmp.put("msg","Already exist.");
					         checkEmp.put("status","true");
				       }
				       }
				       returnResult = SUCCESS;

		   }
		catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getEmpAvailability of class "+getClass(), e);
		    	addActionError("Oopss there is some problem!!!");
		    	return ERROR;
		    }
		return returnResult;
	}
	
	
	/**
	 * Saving all the employee basic details with his unique mobile number and based on the status of the employee existence in the DB,
	 * Also getting saving the employee image and the document if any is attached.
	 * If deptHierarchy==1 it means the employee will mapped with dept or if the deptHierarchy==2 then employee will mapped with sub-dept
	 * @return
	 */
	
	public String createEmpBasic()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getStatus().equalsIgnoreCase("false"))
			{
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
				 TableColumes ob1=new TableColumes();
				 boolean status=false;
				 
				 /*if(deptHierarchy.equalsIgnoreCase("1"))
				 {*/
					// Columns field 1
					 ob1.setColumnname("dept");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
				/*}
				else
				{*/
					// Columns field 2
					 ob1=new TableColumes();
					 ob1.setColumnname("subdept");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
				//}
				 
				 	boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
				    List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_basic_configuration");
					if(org2!=null)
					{
						//create table query based on configuration
						for(GridDataPropertyView gdp:org2)
						{
							if(!gdp.getColomnName().equalsIgnoreCase("subdept"))
							{
								 ob1=new TableColumes();
								 ob1.setColumnname(gdp.getColomnName());
								 ob1.setDatatype("varchar(255)");
								 ob1.setConstraint("default NULL");
								 TableColumnName.add(ob1);
								 if(gdp.getColomnName().equalsIgnoreCase("userName"))
									 userTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("date"))
									 dateTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("time"))
									 timeTrue=true;
							}
						}
						 ob1=new TableColumes();
						 ob1.setColumnname("mapwith");
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 TableColumnName.add(ob1);
						 
						 //for saving the level of employee either on dept or on sub dept
						 ob1=new TableColumes();
						 ob1.setColumnname("mapLevel");
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 TableColumnName.add(ob1);
						//for saving the level of employee either on dept or on sub dept
						 ob1=new TableColumes();
						 ob1.setColumnname("escalationId");
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default 0");
						 TableColumnName.add(ob1);
						 
						cbt.createTable22("employee_basic",TableColumnName,connectionSpace);
					}
				 //insert query for emp basic, First insert the uploaded image and uploaded documnet if any or else leave NULL Both
				 String empImageID=null;
				 String empDocID=null;
				 try
				 {
				   byte[] logoBuf=null;
					if(empLogo!=null)
					{
						logoBuf=new byte[(int)empLogo.length()]; 
						if(logoBuf!=null && logoBuf.length>0)
						{
							FileInputStream fileInStream=new FileInputStream(empLogo);	
							fileInStream.read(logoBuf);
							fileInStream.close();
						}
						Avatar avatar = new Avatar();
						avatar.setImage(logoBuf);
						//System.out.println("logoBuf  "+logoBuf);
						//System.out.println("empLogoContentType  "+empLogoContentType);
						avatar.setContentType(empLogoContentType);
						//System.out.println("avatar"+avatar);
						cbt.addDetails(avatar,connectionSpace);
						empImageID=Integer.toString(avatar.getAvatarId());
					}
					//document uploading part
					logoBuf=null;
					if(empDocument!=null)
					{
						logoBuf=new byte[(int)empDocument.length()]; 
						if(logoBuf!=null && logoBuf.length>0)
						{
							FileInputStream fileInStream=new FileInputStream(empDocument);	
							fileInStream.read(logoBuf);
							fileInStream.close();
						}
						Avatar avatar1 = new Avatar();
						avatar1.setImage(logoBuf);
						avatar1.setContentType(empDocumentContentType);
						cbt.addDetails(avatar1,connectionSpace);
						empDocID=Integer.toString(avatar1.getAvatarId());
						
					}
					
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				 
				 
				 /**
				  * Inserting data in emp basic table with mapped data of dept, designation and all other data
				  */
				 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				 InsertDataTable ob=null;
				 
				 if(empDocID!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("empDocument");
					 ob.setDataName(empDocID);
					 insertData.add(ob);
				 }
				 if(empImageID!=null)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("empLogo");
					 ob.setDataName(empImageID);
					 insertData.add(ob);
				 }
				 if(deptHierarchy.equalsIgnoreCase("1"))
				 {
					 if(getDeptname()!=null)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("dept");
						 ob.setDataName(getDeptname());
						 insertData.add(ob);
					 }
				 }
				 else
				 {
					 if(getSubdept()!=null)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("subdept");
						 ob.setDataName(getSubdept());
						 insertData.add(ob);
					 }
				 }
				 
				 //saving map level
				 
				 ob=new InsertDataTable();
				 ob.setColName("mapLevel");
				 ob.setDataName(deptHierarchy);
				 insertData.add(ob);
				 /**
				  * Getting the request parameters name and their data for insert query, all the fields which are on the form will be received
				  * And oly selected fields except some will be inserted
				  */
				 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				 Collections.sort(requestParameterNames);
				 Iterator it = requestParameterNames.iterator();
				 while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("subdept")
								&& !Parmname.equalsIgnoreCase("deptname")&& !Parmname.equalsIgnoreCase("empLogo")&& !Parmname.equalsIgnoreCase("empDocument")
								&& !Parmname.equalsIgnoreCase("level1")
								&& !Parmname.equalsIgnoreCase("deptHierarchy") && !Parmname.equalsIgnoreCase("orgLevel")
								&& !Parmname.equalsIgnoreCase("levelOforganization")&& !Parmname.equalsIgnoreCase("status")
								&& !Parmname.equalsIgnoreCase("level")&& !Parmname.equalsIgnoreCase("level2org")
								&& !Parmname.equalsIgnoreCase("level3org")&& !Parmname.equalsIgnoreCase("level4org")&& !Parmname.equalsIgnoreCase("level5org")&& !Parmname.equalsIgnoreCase("mapLevel"))
							{
							 ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 ob.setDataName(DateUtil.makeTitle(paramValue));
							 insertData.add(ob);
							}
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
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
				 }
				 
				
				 ob=new InsertDataTable();
				 ob.setColName("status");
				 ob.setDataName("0");
				 insertData.add(ob);
				 
				 
				 status=cbt.insertIntoTable("employee_basic",insertData,connectionSpace);
				 if(status)
				 {
					 addActionMessage("Employee Registered Successfully!!!");
					 return SUCCESS;
				 }
				 else
				 {
					 addActionMessage("Oops There is some error in data!");
					 return SUCCESS;
				 }
			}
			else
			{
				addActionError("Employee Already Exist!!!");
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createEmpBasic of class "+getClass(), e);
			e.printStackTrace();
			addActionMessage("Oops There is some error in data!");
			 return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	 * Creating the all the employee personal data based on the unique mobile number of an employee,
	 * if a mobile number is already in the DB no entry of employee personal data will enter in the DB ,
	 * and one mapping ID of a successful entry will be mapped in the EMP BASIC for reference. 
	 */
	@SuppressWarnings("unchecked")
	public String createEmpPersonal()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
			    boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
				//checking weather already the employee personal details is in the data base or not, emp name contains emp mobile number
				boolean alreadyExist=false;
				try
				{
					List empexistOrNot=new ArrayList<String>();
					Map<String, Object> parameters=new HashMap<String, Object>();
					parameters.put("empName1",getEmpName());
					System.out.println("parameters" +parameters);
					empexistOrNot=cbt.viewAllDataEitherSelectOrAll("employee_personal",empexistOrNot,parameters,connectionSpace);
					if(empexistOrNot!=null && empexistOrNot.size()>0)
					{
						alreadyExist=true;
						System.out.println("alreadyExist" +alreadyExist);
					}
				}
				catch(Exception e)
				{
					//Exception occur first time due to no table existence, no need to print exception
				}
				
				if(getEmpName().equals("-1") || getEmpName()==null)
				{
					addActionMessage("Ooops There Is Some Problem In Employee Name Selection!");
					return SUCCESS;
				}
				if(!alreadyExist)
				{
					//get the data entry id of employee
					int idTemp=0;
					List<String>empData=new ArrayList<String>();
					empData.add("id");
					Map<String, Object> temp=new HashMap<String, Object>();
					temp.put("mobOne",getEmpName());
					empData=cbt.viewAllDataEitherSelectOrAll("employee_basic",empData,temp,connectionSpace);
					if(empData!=null)
					{
						for (Object c : empData) {
							Object dataC = (Object) c;
							idTemp=(Integer)dataC;
						}
					}
					
				  System.out.println(">>>>>   "+idTemp);
					   List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_personal_configuration");
						if(org2!=null)
						{
								//create table query based on configuration
								List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
								empPerFileBox = new ArrayList<ConfigurationUtilBean>();
								
								for(GridDataPropertyView gdp:org2)
								{
									ConfigurationUtilBean objdata = new ConfigurationUtilBean();
									 TableColumes ob1=new TableColumes();
									 ob1=new TableColumes();
									 ob1.setColumnname(gdp.getColomnName());
									 ob1.setDatatype("varchar(255)");
									 ob1.setConstraint("default NULL");
									 Tablecolumesaaa.add(ob1);
									 System.out.println("Tablecolumesaaa" +Tablecolumesaaa);
									 if(gdp.getColomnName().equalsIgnoreCase("userName"))
										 userTrue=true;
									 else if(gdp.getColomnName().equalsIgnoreCase("date"))
										 dateTrue=true;
									 else if(gdp.getColomnName().equalsIgnoreCase("time"))
										 timeTrue=true;
									 System.out.println("gdp.getColType()" +gdp.getColType());
									 if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
												&& !gdp.getColomnName().equalsIgnoreCase("time"))
										{
										 System.out.println("inside f" +gdp.getColType());
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
											empPerFileBox.add(objdata);
											System.out.println("empPerFileBox" +empPerFileBox.size());
										}
									}
								
								
								 cbt.createTable22("employee_personal",Tablecolumesaaa,connectionSpace);
							
								 StringBuilder persnoalIds=new StringBuilder();
								//check if the table is not exist then create if already exist no need for table creation execution
								 boolean status=false;
								 System.out.println("status" +status);
								//insert data in the emp Personal
								 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
								 InsertDataTable ob=null;
								 
								 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
								 Collections.sort(requestParameterNames);
								 Iterator it = requestParameterNames.iterator();
								 while (it.hasNext()) {
										String Parmname = it.next().toString();
										String paramValue=request.getParameter(Parmname);
										if(paramValue!=null &&  !Parmname.equalsIgnoreCase("level1")&& Parmname!=null && !paramValue.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("mobNoPerTemp"))
											{
											 ob=new InsertDataTable();
											 ob.setColName(Parmname);
											 ob.setDataName(paramValue);
											 insertData.add(ob);
											}
										System.out.println("insertData :::" +insertData);
										
								 }
								System.out.println("getEmpDocumentFileName() is as "+getEmpDocumentFileName());
									String renameFilePath=null;
									String docPath1 = null, docPath2 = null;
									if (getEmpDocumentFileName() != null)
									{
										renameFilePath = new CreateFolderOs().createUserDir("Common_DataEmpPer") + "//"  + getEmpDocumentFileName();
										String storeFilePath = new CreateFolderOs().createUserDir("Common_DataEmpPer") + "//" + getEmpDocumentFileName();
										//System.out.println();
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
													System.out.println("setColName" +ob.getColName());
													ob.setDataName(docPath1);
													insertData.add(ob);
													
												}
											}
										}
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
									 ob.setDataName(DateUtil.getCurrentTime());
									 insertData.add(ob);
								 }
								 status=cbt.insertIntoTable("employee_personal",insertData,connectionSpace);
								 
								 int idMAx=cbt.getMaxId("employee_personal",connectionSpace);
								 persnoalIds.append(idMAx);
								 if(status)
								 {

									 StringBuilder query=new StringBuilder();
									 query.append("UPDATE employee_basic SET personalIds ='"+persnoalIds+"' WHERE id='"+idTemp+"'");
									 
									 cbt.updateTableColomn(connectionSpace, query);
									 //update in the emp basic for id mapping
									 addActionMessage("Employee Personal Data Registered Successfully!!!");
								 }
								 else
								 {
									 addActionMessage("Ooops There Is Some Problem In Employee Registration!");
								 }
					}
				}
				else
				{
					addActionMessage("Ooops Employee Personal Details Already Registered, Kindly Edit For Changes!");
				}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createEmpPersonal of class "+getClass(), e);
			addActionMessage("Ooops There Is Some Problem In Employee Registration!");
			return SUCCESS;
		}
		return SUCCESS;
	
	}
	
	/**
	 *Getting all employee list in drop down using ajax for all other modules except employee basic. 
	 * @return
	 */


	public String getEmplDataList()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			List  data=null;
			try
			{
				query.append(" select emp.empName,emp.mobOne from employee_basic as emp ");
				query.append(" INNER Join groupinfo as gi ON gi.id=emp.groupId ");
				query.append(" WHERE gi.groupName ='Employee' ");
				//System.out.println("query  >>>>" +query);
				data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(data!=null)
			{
				jsonArray=new JSONArray();
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 responseDetailsJson = new JSONObject();
					 Object[] obdata=(Object[])it.next();
					 responseDetailsJson.put("id", obdata[1].toString());
					 responseDetailsJson.put("name", obdata[0].toString());
					 jsonArray.add(responseDetailsJson);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getEmplDataList of class "+getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Adding data in the emp bank account based on the unique mobile number.
	 * One entry of each employee will be entered based on the employee mobile number,
	 * refrence ID will also saved in the emp basic	
	 * @return
	 */
	public String createEmpBankDetails()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
				//checking wheathe already the employee perosnal details is in the data base or not, emp name contains emp mobile number
				boolean alreadyExist=false;
				try
				{
					List empexistOrNot=new ArrayList<String>();
					Map<String, Object> parameters=new HashMap<String, Object>();
					parameters.put("empName",getEmpName());
					empexistOrNot=cbt.viewAllDataEitherSelectOrAll("employee_bankdetails",empexistOrNot,parameters,connectionSpace);
					if(empexistOrNot!=null && empexistOrNot.size()>0)
					{
						alreadyExist=true;
					}
				}
				catch(Exception e)
				{
					//Exception occur first time due to no table existance
				}
				
				if(getEmpName().equals("-1") || getEmpName()==null)
				{
					addActionMessage("Ooops There Is Some Problem In Employee Registration!");
					return SUCCESS;
				}
				
				if(!alreadyExist)
				{
					//get the data entry id of employee from emp basic
					int idTemp=0;
					List<String>empData=new ArrayList<String>();
					empData.add("id");
					Map<String, Object> temp=new HashMap<String, Object>();
					temp.put("mobOne",getEmpName());
					empData=cbt.viewAllDataEitherSelectOrAll("employee_basic",empData,temp,connectionSpace);
					if(empData!=null)
					{
						for (Object c : empData) {
							Object dataC = (Object) c;
							idTemp=(Integer)dataC;
						}
					}
					
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_bank_details_configuration");
					if(org2!=null)
					{
							//create table query based on configuration
							List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
							bankFileBox = new ArrayList<ConfigurationUtilBean>();
							for(GridDataPropertyView gdp:org2)
							{
								ConfigurationUtilBean objdata = new ConfigurationUtilBean();
								//System.out.println("createEmpBankDetails");     
								 TableColumes ob1=new TableColumes();
								 ob1=new TableColumes();
								 ob1.setColumnname(gdp.getColomnName());
								 ob1.setDatatype("varchar(255)");
								 ob1.setConstraint("default NULL");
								 Tablecolumesaaa.add(ob1);
								 if(gdp.getColomnName().equalsIgnoreCase("userName"))
									 userTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("date"))
									 dateTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("time"))
									 timeTrue=true;
								 
								 
									//System.out.println("getColomnName" +gdp.getColomnName());
								 if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
											&& !gdp.getColomnName().equalsIgnoreCase("time"))
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
										bankFileBox.add(objdata);
										//System.out.println("bankFileBox" +bankFileBox.size());
									}
							
							}
							
							
							cbt.createTable22("employee_bankdetails",Tablecolumesaaa,connectionSpace);
						
							//check if the table is not exist then create if already exist no need for table creation execution
							 boolean status=false;
							//insert data in the emp Personal
							 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							 InsertDataTable ob=null;
							 
							 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
							 Collections.sort(requestParameterNames);
							 Iterator it = requestParameterNames.iterator();
							 while (it.hasNext()) {
									String Parmname = it.next().toString();
									String paramValue=request.getParameter(Parmname);
									if(paramValue!=null && Parmname!=null && !paramValue.equalsIgnoreCase("") 
											&& !Parmname.equalsIgnoreCase("mobNoPerTemp"))
										{
										 ob=new InsertDataTable();
										 ob.setColName(Parmname);
										 ob.setDataName(paramValue);
										 insertData.add(ob);
										}
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
								 ob.setDataName(DateUtil.getCurrentTime());
								 insertData.add(ob);
							 }
							
					
					//System.out.println("getEmpDocumentFileName() is as "+getEmpDocumentFileName());
					String renameFilePath=null;
					String docPath1 = null, docPath2 = null;
					if (getEmpDocumentFileName() != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Common_Datatoday") + "//"  + getEmpDocumentFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Common_Datatoday") + "//" + getEmpDocumentFileName();
						//System.out.println();
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
									//System.out.println("setColName" +ob.getColName());
									ob.setDataName(docPath1);
									insertData.add(ob);
									
								}
							}
						}
					}
					 status=cbt.insertIntoTable("employee_bankdetails",insertData,connectionSpace);
					 StringBuilder bankIds=new StringBuilder();
					 int idMAx=cbt.getMaxId("employee_bankdetails",connectionSpace);
					 bankIds.append(idMAx);
					 if(status)
					 {
						 
						 Map<String,Object>wherClauseCodntn=new HashMap<String, Object>();
						 wherClauseCodntn.put("id", idTemp);
						 Map<String,Object>wherClause=new HashMap<String, Object>();
						 wherClause.put("bankaccount", bankIds.toString());
						 cbt.updateTableColomn("employee_basic", wherClause,wherClauseCodntn,connectionSpace);
						 addActionMessage("Employee Bank Details Registered Successfully!!!");
					 }
					 else
					 {
						 addActionMessage("Ooops There Is Some Problem In Employee Bank Details!");
					 }
					}
				}
				else
				{
					addActionMessage("Ooops Employee Bank Details Already Registered, Kindly Edit For Changes!");
				}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createEmpBankDetails of class "+getClass(), e);
			addActionMessage("Ooops There Is Some Problem In Employee Bank Details!");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String createEmpWorkExpDetails()
	{
		//System.out.println(">>>>>>>>");
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			    CommonOperInterface cbt=new CommonConFactory().createInterface();
			    boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
				//checking weather already the employee personal details is in the data base or not, emp name contains emp mobile number
				boolean alreadyExist=false;
				try
				{
					List empexistOrNot=new ArrayList<String>();
					Map<String, Object> parameters=new HashMap<String, Object>();
					parameters.put("empName1",getEmpName());
					empexistOrNot=cbt.viewAllDataEitherSelectOrAll("employee_workexpriencedetails",empexistOrNot,parameters,connectionSpace);
					if(empexistOrNot!=null && empexistOrNot.size()>0)
					{
						alreadyExist=true;
					}
				}
				catch(Exception e)
				{
					//Exception occur first time due to no table existence, no need to print exception
				}
				
				if(getEmpName().equals("-1") || getEmpName()==null)
				{
					addActionMessage("Ooops There Is Some Problem In Employee Name Selection!");
					return SUCCESS;
				}
				if(!alreadyExist)
				{
					//get the data entry id of employee
					int idTemp=0;
					List<String>empData=new ArrayList<String>();
					empData.add("id");
					Map<String, Object> temp=new HashMap<String, Object>();
					temp.put("mobOne",getEmpName());
					empData=cbt.viewAllDataEitherSelectOrAll("employee_basic",empData,temp,connectionSpace);
					if(empData!=null)
					{
						for (Object c : empData) {
							Object dataC = (Object) c;
							idTemp=(Integer)dataC;
						}
					}
					
					//System.out.println(">>>>>   "+idTemp);
					   List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_personal_configuration");
						if(org2!=null)
						{
							
								//create table query based on configuration
								List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
								empWorkExpFileBox = new ArrayList<ConfigurationUtilBean>();
								for(GridDataPropertyView gdp:org2)
								{
									ConfigurationUtilBean objdata = new ConfigurationUtilBean();
									 TableColumes ob1=new TableColumes();
									 ob1=new TableColumes();
									 ob1.setColumnname(gdp.getColomnName());
									 ob1.setDatatype("varchar(255)");
									 ob1.setConstraint("default NULL");
									 Tablecolumesaaa.add(ob1);
									 if(gdp.getColomnName().equalsIgnoreCase("userName"))
										 userTrue=true;
									 else if(gdp.getColomnName().equalsIgnoreCase("date"))
										 dateTrue=true;
									 else if(gdp.getColomnName().equalsIgnoreCase("time"))
										 timeTrue=true;
								
								 if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
											&& !gdp.getColomnName().equalsIgnoreCase("time"))
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
										empWorkExpFileBox.add(objdata);
										//System.out.println("bankFileBox" +bankFileBox.size());
									}
								}
								
								 cbt.createTable22("employee_workexpriencedetails",Tablecolumesaaa,connectionSpace);
							
								 StringBuilder workExprienceIDs=new StringBuilder();
								//check if the table is not exist then create if already exist no need for table creation execution
								 boolean status=false;
								//insert data in the emp Personal
								 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
								 InsertDataTable ob=null;
								 
								 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
								 Collections.sort(requestParameterNames);
								 Iterator it = requestParameterNames.iterator();
								 while (it.hasNext()) {
										String Parmname = it.next().toString();
										String paramValue=request.getParameter(Parmname);
										if(paramValue!=null &&  !Parmname.equalsIgnoreCase("level1")&& Parmname!=null && !paramValue.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("mobNoPerTemp"))
											{
											 ob=new InsertDataTable();
											 ob.setColName(Parmname);
											 ob.setDataName(paramValue);
											 insertData.add(ob);
											}
										//System.out.println("insertData :::" +insertData);
										
								 }
								 
								 
									//System.out.println("getEmpDocumentFileName() is as "+getEmpDocumentFileName());
									String renameFilePath=null;
									String docPath1 = null, docPath2 = null;
									if (getEmpDocumentFileName() != null)
									{
										renameFilePath = new CreateFolderOs().createUserDir("Common_DataForEmp") + "//"  + getEmpDocumentFileName();
										String storeFilePath = new CreateFolderOs().createUserDir("Common_DataForEmp") + "//" + getEmpDocumentFileName();
										//System.out.println();
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
													//System.out.println("setColName" +ob.getColName());
													ob.setDataName(docPath1);
													insertData.add(ob);
													
												}
											}
										}
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
									 ob.setDataName(DateUtil.getCurrentTime());
									 insertData.add(ob);
								 }
								 status=cbt.insertIntoTable("employee_workexpriencedetails",insertData,connectionSpace);
								 
								 int idMAx=cbt.getMaxId("employee_workexpriencedetails",connectionSpace);
								 workExprienceIDs.append(idMAx);
								 if(status)
								 {

									 StringBuilder query=new StringBuilder();
									 query.append("UPDATE employee_basic SET workExprienceIDs ='"+workExprienceIDs+"' WHERE id='"+idTemp+"'");
									 
									 cbt.updateTableColomn(connectionSpace, query);
									 //update in the emp basic for id mapping
									 addActionMessage("Employee Personal Data Registered Successfully!!!");
								 }
								 else
								 {
									 addActionMessage("Ooops There Is Some Problem In Employee Registration!");
								 }
					}
				}
				else
				{
					addActionMessage("Ooops Employee Personal Details Already Registered, Kindly Edit For Changes!");
				}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createEmpPersonal of class "+getClass(), e);
			addActionMessage("Ooops There Is Some Problem In Employee Registration!");
			return SUCCESS;
		}
		return SUCCESS;
	
	}
	
	/**
	 * Adding data in the emp work exprince and employee qualification details based on the mobile number selection
	 * Can add multiple data of an employee , all data are unquely identified by emplyee mobile number,
	 * And each entry of data is also mapped in the EMP basic with its respective ID from the DB.
	 */
	public String createEmpPrfsnl()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			
					if(getEmpName().equals("-1") || getEmpName()==null)
					{
						addActionMessage("Ooops There Is Some Problem In Employee Registration!");
						return SUCCESS;
					}
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					//Employee qualification works starts here for table creation
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					 List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
					 TableColumes ob1=new TableColumes();
					// Columes field 2, For mapping the emp perosnal data based on the employee mobile number which is unique for each
					 ob1.setColumnname("empName");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columes field 3
					 ob1=new TableColumes();
					 ob1.setColumnname("acadmic");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columes field 4
					 ob1=new TableColumes();
					 ob1.setColumnname("aggregate");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columes field 5
					 ob1=new TableColumes();
					 ob1.setColumnname("subject");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 6
					 ob1=new TableColumes();
					 ob1.setColumnname("year");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 7
					 ob1=new TableColumes();
					 ob1.setColumnname("college");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 8
					 ob1=new TableColumes();
					 ob1.setColumnname("university");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 9
					 ob1=new TableColumes();
					 ob1.setColumnname("userName");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 10
					 ob1=new TableColumes();
					 ob1.setColumnname("date");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 11
					 ob1=new TableColumes();
					 ob1.setColumnname("time");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					// Columns field 12
					 ob1=new TableColumes();
					 ob1.setColumnname("empDocument");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					//check if the table is not exist then create if already exist no need for table creation execution
					 boolean status=cbt.createTable22("employee_qualificationdeatils",TableColumnName,connectionSpace);
					//Employee qualification works ends here for table creation 
					
					 
					//Employee work experience works starts here for table creation
					 List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_work_exprience_configuration");
						if(org2!=null)
						{
							//create table query based on configuration
							List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
							for(GridDataPropertyView gdp:org2)
							{
								 ob1=new TableColumes();
								 ob1.setColumnname(gdp.getColomnName());
								 ob1.setDatatype("varchar(255)");
								 ob1.setConstraint("default NULL");
								 Tablecolumesaaa.add(ob1);
								 if(gdp.getColomnName().equalsIgnoreCase("userName"))
									 userTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("date"))
									 dateTrue=true;
								 else if(gdp.getColomnName().equalsIgnoreCase("time"))
									 timeTrue=true;
							}
							//check if the table is not exist then create if already exist no need for table creation execution
							cbt.createTable22("employee_workexpriencedetails",Tablecolumesaaa,connectionSpace);
						}
					 
					//Employee work experience works ends here for table creation
					 
					 StringBuilder tempQualificationId=new StringBuilder();
					 StringBuilder tempWorkExprienceId=new StringBuilder();
					 String tempacadmic[]=getAcadmic().split(", ");
					 String tempaggregate[]=getAggregate().split(", ");
					 //System.out.println("tempaggregate" + tempaggregate);
					 String tempsubject[]=getSubject().split(", ");
					 //System.out.println("tempsubject" +tempsubject);
					 String tempyear[]=getYear().split(", ");
					 String tempcollege[]=getCollege().split(", ");
					 String tempuniversity[]=getUniversity().split(", ");
				     String tempDocument[]=getEmpDocumentFileName().split(", ");
				     //System.out.println("tempDocument xx" +getEmpDocumentFileName());
					 for(int i=0;i<tempacadmic.length;i++)
					 {
						 if(!tempacadmic[i].equalsIgnoreCase("") && tempacadmic[i]!=null)
						 {
							 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							 InsertDataTable ob=new InsertDataTable();
							 ob.setColName("empName");
							 ob.setDataName(getEmpName());
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("acadmic");
							 ob.setDataName(tempacadmic[i]);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("aggregate");
							 ob.setDataName(tempaggregate[i]);
							 //System.out.println("tempaggregate" +tempaggregate);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("subject");
							 ob.setDataName(tempsubject[i]);
							 //System.out.println("tempsubject" +tempsubject);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("year");
							 ob.setDataName(tempyear[i]);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("college");
							 ob.setDataName(tempcollege[i]);
							 insertData.add(ob);
							 
							 ob=new InsertDataTable();
							 ob.setColName("university");
							 ob.setDataName(tempuniversity[i]);
							 insertData.add(ob);
							
							 
									String renameFilePath = null;
									if (empDocumentFileName != null)
									{
										renameFilePath = new CreateFolderOs()
												.createUserDir("empDocs")
												+ "//"
												+ tempDocument[i];
										//System.out.println("renameFilePath >>>>" +renameFilePath);
										String storeFilePath1 = new CreateFolderOs().createUserDir("empDocs")
												+ "//"
												+tempDocument[i];
										String str = renameFilePath.replace("//", "/");
										//System.out.println("str >>>>" +str);
										if (storeFilePath1 != null)
										{
											File theFile1 = new File(storeFilePath1);
											File newFileName = new File(str);
											if (theFile1 != null)
											{
												try
												{
													FileUtils.copyFile(empDocument, theFile1);
													if (theFile1.exists())
														theFile1.renameTo(newFileName);
												}
												catch (IOException e)
												{
													e.printStackTrace();
												}
												if (theFile1 != null)
												{
													ob = new InsertDataTable();
													ob.setColName("empDocument");
													ob.setDataName(renameFilePath);
													insertData.add(ob);
												}
											}
										}
									}
							
							
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
							 
							 status=cbt.insertIntoTable("employee_qualificationdeatils",insertData,connectionSpace);
							 int idMAx=cbt.getMaxId("employee_qualificationdeatils",connectionSpace);
							 //System.out.println("idMAx ::::" +idMAx);
							 tempQualificationId.append(idMAx+"#");
							 //System.out.println("tempQualificationId ::::" +tempQualificationId);
							 
						 }
					 }
					 InsertDataTable ob=null;
					 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 Collections.sort(requestParameterNames);
					 Iterator it = requestParameterNames.iterator(); 
					 while (it.hasNext()) {
							String Parmname = it.next().toString();
							String paramValue=request.getParameter(Parmname);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("acadmic")
									&& !Parmname.equalsIgnoreCase("aggregate")&& !Parmname.equalsIgnoreCase("subject")&& !Parmname.equalsIgnoreCase("year")
									&& !Parmname.equalsIgnoreCase("college")&& !Parmname.equalsIgnoreCase("university"))
							{
								 ob=new InsertDataTable();
								 ob.setColName(Parmname);
								 ob.setDataName(paramValue);
								 insertData.add(ob);
							}
							
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
						 ob.setDataName(DateUtil.getCurrentTime());
						 insertData.add(ob);
					 }
					 
					 status=cbt.insertIntoTable("employee_workexpriencedetails",insertData,connectionSpace);
					 int idMAx=cbt.getMaxId("employee_workexpriencedetails",connectionSpace);
					 //System.out.println("idMAx ::::" +idMAx);
					 tempWorkExprienceId.append(idMAx+"#");
					 //System.out.println("tempWorkExprienceId ::::" +tempWorkExprienceId);
					
					   //get the data entry id from emp basic of selected employee
						int idTemp=0;
						String tempqualificationIds=new String();
						String tempworkExprienceIDs=new String();
						List<String>empData=new ArrayList<String>();
						empData.add("id");
						empData.add("workExprienceIDs");
						empData.add("qualificationIds");
						Map<String, Object> temp=new HashMap<String, Object>();
						temp.put("mobOne",getEmpName());
						empData=cbt.viewAllDataEitherSelectOrAll("employee_basic",empData,temp,connectionSpace);
						if(empData!=null)
						{
							for (Object c : empData) {
								Object[] dataC = (Object[]) c;
								idTemp=(Integer)dataC[0];
								if(dataC[1]!=null)
								tempworkExprienceIDs=dataC[1].toString();
								if(dataC[2]!=null)
								tempqualificationIds=dataC[2].toString();
							}
						}
						
						tempWorkExprienceId.append(tempworkExprienceIDs);
						tempQualificationId.append(tempqualificationIds);
						 if(status)
						 {
							 Map<String,Object>wherClauseCodntn=new HashMap<String, Object>();
							 wherClauseCodntn.put("id", idTemp);
							 //update the qualification ids in the empbasic
							 Map<String,Object>wherClause=new HashMap<String, Object>();
							 wherClause.put("workExprienceIDs", tempWorkExprienceId.toString());
							 wherClause.put("qualificationIds", tempQualificationId.toString());
							 cbt.updateTableColomn("employee_basic", wherClause,wherClauseCodntn,connectionSpace);
							 addActionMessage("Employee Professional Details Registered Successfully!!!");
						 }
						 else
						 {
							 addActionMessage("Ooops There Is Some Problem In Employee Professional Details!");
						 }
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createEmpPrfsnl of class "+getClass(), e);
			e.printStackTrace();
			addActionMessage("Ooops There Is Some Problem In Employee Professional Details!");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
public String makeHistory()
{

	try 
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		
		String deptQurry = "select id,deptName from department where flag='Active'";
		List listData3 = cbt.executeAllSelectQuery(deptQurry, connectionSpace);
		if(listData3!=null && listData3.size()>0)
		{
			getAllDept=new HashMap<String, String>();
			for (Iterator iterator = listData3.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object!=null)
				{
					getAllDept.put(object[0].toString(), object[1].toString());
				}
			}
		}
		
		String sql_query = " select userType from useraccount where userID='"+encryptedID+"'";
		List listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
		userAccountType=listData.get(0).toString();
		
		System.out.println("User Ac Type"+userAccountType);
		if(userAccountType.equalsIgnoreCase("H"))
		{
			hod=true;
		}	
		else if(userAccountType.equalsIgnoreCase("M"))
		{
			mgmt=true;
		}	
		else if(userAccountType.equalsIgnoreCase("N"))
		{
			normalUser=true;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

return SUCCESS;
}


public String getEmployeeName()
{
	try
	{

		if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List lowerLevel3 = new ArrayList<String>();
		lowerLevel3.add("id");
		lowerLevel3.add("empName");
		Map<String, Object> temp = new HashMap<String, Object>();
		Map<String, Object> empName = new HashMap<String, Object>();
		empName.put("empName",empName);
		temp.put("subdept", getSubDeptIDgggggggggg());
		lowerLevel3 = cbt.viewAllDataEitherSelectOrAllByOrder("employee_basic",
				lowerLevel3, temp,empName, connectionSpace);
		System.out
				.println("lowerLevel3 kkkkkkkkkkkk " + lowerLevel3.size());
		if (lowerLevel3 != null && lowerLevel3.size() > 0)
		{

			jsonArray = new JSONArray();
			for (Iterator it = lowerLevel3.iterator(); it.hasNext();)
			{
				Object[] obdata = (Object[]) it.next();
				if (obdata != null)
				{
					JSONObject formDetailsJson = new JSONObject();
					formDetailsJson.put("id", obdata[0].toString());
					formDetailsJson.put("empName", obdata[1].toString());

					jsonArray.add(formDetailsJson);
				}
			}

		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	return SUCCESS;
}
private String id;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getDeactiveEmp()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("status", 1);
			condtnBlock.put("id", empName);
			boolean status=cbt.updateTableColomn("employee_basic", wherClause, condtnBlock, connectionSpace);
			if(status)
			{
				// to Deactivate From Contacts Master 
				
				wherClause.clear();
				condtnBlock.clear();
				
				wherClause.put("work_status", 1);
				condtnBlock.put("emp_id", empName);
				
				status=cbt.updateTableColomn("compliance_contacts", wherClause, condtnBlock, connectionSpace);
				
				
				String sql_query = " select useraccountid from employee_basic  where id='"+empName+"'";
				List listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
				String userAccountId=(String) listData.get(0);
				if(!userAccountId.equalsIgnoreCase("0"))
				{
					Map<String, Object> wherClause1 = new HashMap<String, Object>();
					Map<String, Object> condtnBlock1 = new HashMap<String, Object>();
					wherClause1.put("active", 0);
					condtnBlock1.put("id", userAccountId);
					boolean status2=cbt.updateTableColomn("useraccount", wherClause1, condtnBlock1, connectionSpace);
				
					if(status2)
					{
						addActionMessage("Account Deactivated Successfully");
						return SUCCESS;
					}
					else
					{
						addActionError("Error in Making History !!!");
						return ERROR;
					}
				}
				else
				{
					addActionError("Error in Making History !!!");
					return ERROR;
				}
			}
			else
			{
				addActionError("Error in Making History !!!");
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
	
	public String changePwd()
	{
		
		return SUCCESS;
		
	}
	

	public Map<Integer, String> getSubDepartmentList() {
		return subDepartmentList;
	}

	public void setSubDepartmentList(Map<Integer, String> subDepartmentList) {
		this.subDepartmentList = subDepartmentList;
	}

	public Map<Integer, String> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(Map<Integer, String> designationList) {
		this.designationList = designationList;
	}




	public String getLevel1() {
		return level1;
	}


	public void setLevel1(String level1) {
		this.level1 = level1;
	}


	public int getLevelOforganization() {
		return levelOforganization;
	}


	public void setLevelOforganization(int levelOforganization) {
		this.levelOforganization = levelOforganization;
	}


	public String getOrgIdInLevel() {
		return orgIdInLevel;
	}


	public void setOrgIdInLevel(String orgIdInLevel) {
		this.orgIdInLevel = orgIdInLevel;
	}


	public String getSelectedorgId() {
		return selectedorgId;
	}


	public void setSelectedorgId(String selectedorgId) {
		this.selectedorgId = selectedorgId;
	}


	public String getDeptID() {
		return deptID;
	}


	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}



	public Map<String, String> getCheckEmp() {
		return checkEmp;
	}


	public void setCheckEmp(Map<String, String> checkEmp) {
		this.checkEmp = checkEmp;
	}


	public String getSubdept() {
		return subdept;
	}


	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getDoj_date() {
		return doj_date;
	}


	public void setDoj_date(String doj_date) {
		this.doj_date = doj_date;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getComName() {
		return comName;
	}


	public void setComName(String comName) {
		this.comName = comName;
	}


	public String getMobOne() {
		return mobOne;
	}


	public void setMobOne(String mobOne) {
		this.mobOne = mobOne;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMobTwo() {
		return mobTwo;
	}


	public void setMobTwo(String mobTwo) {
		this.mobTwo = mobTwo;
	}


	public String getEmailIdOne() {
		return emailIdOne;
	}


	public void setEmailIdOne(String emailIdOne) {
		this.emailIdOne = emailIdOne;
	}


	public String getEmailIdTwo() {
		return emailIdTwo;
	}


	public void setEmailIdTwo(String emailIdTwo) {
		this.emailIdTwo = emailIdTwo;
	}


	public String getSalary() {
		return salary;
	}


	public void setSalary(String salary) {
		this.salary = salary;
	}




	public String getCcontactNo() {
		return ccontactNo;
	}

	public void setCcontactNo(String ccontactNo) {
		this.ccontactNo = ccontactNo;
	}

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getLandmark() {
		return landmark;
	}


	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCpincode() {
		return cpincode;
	}


	public void setCpincode(String cpincode) {
		this.cpincode = cpincode;
	}


	public File getEmpLogo() {
		return empLogo;
	}


	public void setEmpLogo(File empLogo) {
		this.empLogo = empLogo;
	}


	public String getEmpLogoContentType() {
		return empLogoContentType;
	}


	public void setEmpLogoContentType(String empLogoContentType) {
		this.empLogoContentType = empLogoContentType;
	}


	public String getEmpLogoFileName() {
		return empLogoFileName;
	}


	public void setEmpLogoFileName(String empLogoFileName) {
		this.empLogoFileName = empLogoFileName;
	}


	public File getEmpDocument() {
		return empDocument;
	}


	public void setEmpDocument(File empDocument) {
		this.empDocument = empDocument;
	}


	public String getEmpDocumentContentType() {
		return empDocumentContentType;
	}


	public void setEmpDocumentContentType(String empDocumentContentType) {
		this.empDocumentContentType = empDocumentContentType;
	}


	public String getEmpDocumentFileName() {
		return empDocumentFileName;
	}


	public void setEmpDocumentFileName(String empDocumentFileName) {
		this.empDocumentFileName = empDocumentFileName;
	}


	public Map<String, String> getEmpListData() {
		return empListData;
	}


	public void setEmpListData(Map<String, String> empListData) {
		this.empListData = empListData;
	}



	public String getEdob_date() {
		return edob_date;
	}


	public void setEdob_date(String edob_date) {
		this.edob_date = edob_date;
	}


	public String getDoa_date() {
		return doa_date;
	}


	public void setDoa_date(String doa_date) {
		this.doa_date = doa_date;
	}




	public String getSdob_date() {
		return sdob_date;
	}


	public void setSdob_date(String sdob_date) {
		this.sdob_date = sdob_date;
	}




	public String getConedob_date() {
		return conedob_date;
	}


	public void setConedob_date(String conedob_date) {
		this.conedob_date = conedob_date;
	}




	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFmob() {
		return fmob;
	}

	public void setFmob(String fmob) {
		this.fmob = fmob;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getConeName() {
		return coneName;
	}

	public void setConeName(String coneName) {
		this.coneName = coneName;
	}

	public String getCtwoName() {
		return ctwoName;
	}

	public void setCtwoName(String ctwoName) {
		this.ctwoName = ctwoName;
	}

	public String getCtwodob_date() {
		return ctwodob_date;
	}


	public void setCtwodob_date(String ctwodob_date) {
		this.ctwodob_date = ctwodob_date;
	}


	public String getAddressPer() {
		return addressPer;
	}


	public void setAddressPer(String addressPer) {
		this.addressPer = addressPer;
	}


	public String getLandmarkPer() {
		return landmarkPer;
	}


	public void setLandmarkPer(String landmarkPer) {
		this.landmarkPer = landmarkPer;
	}


	public String getCityPer() {
		return cityPer;
	}


	public void setCityPer(String cityPer) {
		this.cityPer = cityPer;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBankAddress() {
		return bankAddress;
	}


	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getIfscCode() {
		return ifscCode;
	}


	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	public String getAcType() {
		return acType;
	}


	public void setAcType(String acType) {
		this.acType = acType;
	}


	public String getAcadmic() {
		return acadmic;
	}


	public void setAcadmic(String acadmic) {
		this.acadmic = acadmic;
	}


	public String getAggregate() {
		return aggregate;
	}


	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getCollege() {
		return college;
	}


	public void setCollege(String college) {
		this.college = college;
	}


	public String getUniversity() {
		return university;
	}


	public void setUniversity(String university) {
		this.university = university;
	}




	public String getWorkExpDesg() {
		return workExpDesg;
	}


	public void setWorkExpDesg(String workExpDesg) {
		this.workExpDesg = workExpDesg;
	}


	public String getCdept() {
		return cdept;
	}


	public void setCdept(String cdept) {
		this.cdept = cdept;
	}


	public String getWorkEmpId() {
		return workEmpId;
	}


	public void setWorkEmpId(String workEmpId) {
		this.workEmpId = workEmpId;
	}




	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	public String getPfrom() {
		return pfrom;
	}

	public void setPfrom(String pfrom) {
		this.pfrom = pfrom;
	}

	public String getPto() {
		return pto;
	}

	public void setPto(String pto) {
		this.pto = pto;
	}

	public String getRefrenceOne() {
		return refrenceOne;
	}


	public void setRefrenceOne(String refrenceOne) {
		this.refrenceOne = refrenceOne;
	}


	public String getRefOneDesg() {
		return refOneDesg;
	}


	public void setRefOneDesg(String refOneDesg) {
		this.refOneDesg = refOneDesg;
	}


	public String getRefOneMobNo() {
		return refOneMobNo;
	}


	public void setRefOneMobNo(String refOneMobNo) {
		this.refOneMobNo = refOneMobNo;
	}


	public String getRefOneEmailId() {
		return refOneEmailId;
	}


	public void setRefOneEmailId(String refOneEmailId) {
		this.refOneEmailId = refOneEmailId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDeptHierarchy() {
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy) {
		this.deptHierarchy = deptHierarchy;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	public Map<String, String> getGetAllSubDept() {
		return getAllSubDept;
	}

	public void setGetAllSubDept(Map<String, String> getAllSubDept) {
		this.getAllSubDept = getAllSubDept;
	}
	

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getSubDeptIDgggggggggg() {
		return subDeptIDgggggggggg;
	}

	public void setSubDeptIDgggggggggg(String subDeptIDgggggggggg) {
		this.subDeptIDgggggggggg = subDeptIDgggggggggg;
	}
	

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getUserAccountType() {
		return userAccountType;
	}

	public void setUserAccountType(String userAccountType) {
		this.userAccountType = userAccountType;
	}
	

	public boolean isHod() {
		return hod;
	}

	public void setHod(boolean hod) {
		this.hod = hod;
	}
	

	public Map<String, String> getGetAllDept() {
		return getAllDept;
	}

	public void setGetAllDept(Map<String, String> getAllDept) {
		this.getAllDept = getAllDept;
	}
	

	public boolean isMgmt() {
		return mgmt;
	}

	public void setMgmt(boolean mgmt) {
		this.mgmt = mgmt;
	}

	public boolean isNormalUser() {
		return normalUser;
	}

	public void setNormalUser(boolean normalUser) {
		this.normalUser = normalUser;
	}
	
	

	public Map<String, String> getGetSubDeptMake() {
		return getSubDeptMake;
	}

	public void setGetSubDeptMake(Map<String, String> getSubDeptMake) {
		this.getSubDeptMake = getSubDeptMake;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	public List<ConfigurationUtilBean> getBankFileBox() {
		return bankFileBox;
	}

	public void setBankFileBox(List<ConfigurationUtilBean> bankFileBox) {
		this.bankFileBox = bankFileBox;
	}

	public String getDocFile() {
		return docFile;
	}

	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}

	public String getChangeType()
	{
		return changeType;
	}

	public void setChangeType(String changeType)
	{
		this.changeType = changeType;
	}

	public List<ConfigurationUtilBean> getEmpWorkExpFileBox()
	{
		return empWorkExpFileBox;
	}

	public void setEmpWorkExpFileBox(List<ConfigurationUtilBean> empWorkExpFileBox)
	{
		this.empWorkExpFileBox = empWorkExpFileBox;
	}

	public List<ConfigurationUtilBean> getEmpPerFileBox()
	{
		return empPerFileBox;
	}

	public void setEmpPerFileBox(List<ConfigurationUtilBean> empPerFileBox)
	{
		this.empPerFileBox = empPerFileBox;
	}

	
	

	
	
	
	
	
	
}
