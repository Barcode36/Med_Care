package com.Over2Cloud.ctrl.user;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserCtrlAction extends GridPropertyBean
{

	static final Log log = LogFactory.getLog(UserCtrlAction.class);
	Map session = ActionContext.getContext().getSession();
	private String productId;
	private Map<String, String> checkEmp;
	public HttpServletRequest request;
	// user creation properties
	private String empName;
	private String mobileNo;
	private String name;
	private String status;
	private String password;
	private String userID;
	private String userForProductId;
	private List<String> prvlgsList = new ArrayList<String>();
	private List<GridDataPropertyView> userSelectedColnName = new ArrayList<GridDataPropertyView>();
	private String headerName;
	private String editPrvlg;
	private List<Object> userDataList;
	
	private boolean loadonce = false;
	private String oper;
	private int id;
	private String userAccountType;
	private String userAccountId;
	private boolean SFA = true;
	private boolean HD = true;
	private String userType;
	private Map<String,String> toatlcount;
	// Anoop 18-9-2013
	private String smsNotification = null;
	private String emailNotification = null;
	private String passwordCurr = null;
	private String contactName;
	private Map<Integer, String> deptMap;
	private Map<Integer, String> groupMap;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private JSONArray userTypeMap = null;
	private String selectedId;
	private Map<String, String> userMap;
	private String active;
	private boolean hodStatus;
	private boolean mgtStatus;
	// -------------------USER VIEW CODE STARTS
	// HERE------------------------------//
	/***************************************************************************
	 * Method for checking that the user name no is already exist in database or
	 * not If a user is exist in the DB it will return true so that user id will
	 * not be allowed for creation and if it return false it means we can create
	 * the user of that user id in the user account DB table.
	 * 
	 * @return
	 */
    public String usermodifyBefore() 
    {
    	if (ValidateSession.checkSession()) 
    	{
    		userMap=new LinkedHashMap<String, String>();
    		//System.out.println("userType   "+userType);
    		if (userType!=null && userType.equalsIgnoreCase("Management")) 
    		{
				userMap.put("M", "Management");
				userMap.put("N", "Normal");
				userMap.put("H", "HOD");
			}
    		else if(userType!=null && userType.equalsIgnoreCase("HOD"))
    		{
    			userMap.put("H", "HOD");
				userMap.put("M", "Management");
				userMap.put("N", "Normal");
    		}
    		else if(userType!=null && userType.equalsIgnoreCase("Normal"))
    		{
    			userMap.put("N", "Normal");
				userMap.put("H", "HOD");
				userMap.put("M", "Management");
    		}
    		else
    		{
    			userMap.put("H", "HOD");
				userMap.put("M", "Management");
				userMap.put("N", "Normal");
    		}
			return SUCCESS;
		}
    	else 
    	{
    		return LOGIN;
		}
	}
	public String checkUserName()
	{
		String returnResult = ERROR;
		try
		{

			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getUserName() != null)
			{
				checkEmp = new HashMap<String, String>();
				// check user mobile number
				boolean sttaus = false;
				Map<Integer, String> appList = new HashMap<Integer, String>();
				try
				{
					List lowerLevel3 = new ArrayList<String>();
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("userID", Cryptography.encrypt(getUserName(), DES_ENCRYPTION_KEY));
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					try
					{
						lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("useraccount", lowerLevel3, temp, connectionSpace);
					}
					catch (Exception e)
					{

					}
					if (lowerLevel3.size() > 0)
					{
						sttaus = true;
						checkEmp.put("msg", "User already exist");
						checkEmp.put("status", "true");
						return SUCCESS;
					}

					// check for the no of the users.
					// Commented by Anoop, 2-8-2013
					/*
					 * if(getProductId()!=null) {
					 * returnResult=UserCtrlActionHelper
					 * .checkProductUser(getProductId
					 * (),accountID,connectionSpace);
					 * if(!returnResult.equalsIgnoreCase("")) { sttaus=true;
					 * checkEmp.put("msg",returnResult);
					 * checkEmp.put("status","true"); return SUCCESS; } else { }
					 * }
					 */
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (!sttaus)
				{
					checkEmp.put("msg", "You can create User");
					checkEmp.put("status", "false");
				}
				else
				{

				}
			}
			returnResult = SUCCESS;

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method checkUserName of class " + getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return returnResult;
	}

	// Creating user with respective user privilages and action control
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	/*@SuppressWarnings("rawtypes")
	public String createUser()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getStatus().equalsIgnoreCase("false"))
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				StringBuilder rights = new StringBuilder();
				StringBuilder userForProduct=new StringBuilder();
				StringBuilder userForProductName=new StringBuilder();
				HashSet<String> rightsHeadingSet = new HashSet<String>();
				request = ServletActionContext.getRequest();
				if (!getRightsUtility(request, rights, rightsHeadingSet))
				{
					addActionMessage("ERROR: User Rights !");
					return ERROR;
				}
				String rightsName = "";
				for (String h : rightsHeadingSet)
				{
					rightsName += "'" + h + "',";
				}
				//System.out.println("rightsName    " +rightsName);
				rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));
				String appCodes=fetchAppCodeFromRights(rightsName,connectionSpace);
				if (appCodes!=null)
				{
					AllConnection Conn = new ConnectionFactory("localhost");
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					SessionFactory sessfact = Ob1.getSession();
					String qry=null;
					qry="SELECT id,app_code from apps_details WHERE app_code IN("+ appCodes + ")";
					List dataTempWithCode = cbt.executeAllSelectQuery(qry, sessfact);
					if (dataTempWithCode != null && !dataTempWithCode.isEmpty())
					{
						int i=1;
						for (Iterator iterator = dataTempWithCode.iterator(); iterator.hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							if (object[1]!=null && object[0]!=null) 
							{
								
								if(i<dataTempWithCode.size())
								{
									userForProduct.append(object[0].toString()+",");
									userForProductName.append(object[1].toString()+",");
								}
								else
								{
									userForProduct.append(object[0].toString());
									userForProductName.append(object[1].toString());
								}
							}
							i++;
						}
					}
				}
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ob.setColName("mobileNo");
				ob.setDataName(mobileNo);
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("name");
				ob.setDataName(name);
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("userID");
				ob.setDataName(Cryptography.encrypt(getUserID(), DES_ENCRYPTION_KEY));
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("linkVal");
				ob.setDataName(rights.toString());
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("password");
				ob.setDataName(Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY));
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("active");
				ob.setDataName("1");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("userType");
				ob.setDataName(userType);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userForProductId");
				ob.setDataName(userForProduct.toString());
				insertData.add(ob);

				if (insertData != null && insertData.size() > 0)
				{
					int userAcId = coi.insertDataReturnId("useraccount", insertData, connectionSpace);
					if (userAcId > 0)
					{
						String deptId[]=fetchDeptId(contactName,connectionSpace);
						if (deptId[1]!=null && !deptId.equals(""))
						{
							String[] productName=userForProductName.toString().split(",");
							if (productName!=null && !productName.toString().equalsIgnoreCase(""))
							{
								for (String str : productName)
								{
									insertData.clear();
									
									ob = new InsertDataTable();
									ob.setColName("forDept_id");
									ob.setDataName(deptId[1]);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("fromDept_id");
									ob.setDataName(deptId[1]);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("emp_id");
									ob.setDataName(contactName);
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

									ob = new InsertDataTable();
									ob.setColName("work_status");
									ob.setDataName("0");
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("level");
									ob.setDataName("1");
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("moduleName");
									ob.setDataName(str);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("deptLevel");
									ob.setDataName("1");
									insertData.add(ob);
									coi.insertIntoTable("compliance_contacts", insertData, connectionSpace);
								}
							}
						}
						int j = coi.executeAllUpdateQuery("update employee_basic set useraccountid = '" + userAcId + "' where id = '" + contactName + "'", connectionSpace);
						if (j > 0)
						{
							//String ip = new IPAddress().getIPAddress();
							// System.out.println("URLMAIL  "+ ip);
							//String UrlMail = "http://" + ip + request.getContextPath();
							String UrlMail = "over2cloud.co.in" ;
						 //  System.out.println("URLMAIL  "+ UrlMail);
							List rightsHeadingList = coi.executeAllSelectQuery("select Rights_heading from user_rights where rights_name in (" + rightsName + ")", connectionSpace);
							rightsName = "";
							if (rightsHeadingList != null && rightsHeadingList.size() > 0)
							{
								for (Object obj : rightsHeadingList)
									rightsName += obj.toString() + ", ";
							}
							rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));
							
							StringBuilder fieldsNames=new StringBuilder();
	                		StringBuilder fieldsValue=new StringBuilder();
	                		if (insertData!=null && !insertData.isEmpty())
							{
								int i=1;
								for (InsertDataTable h : insertData)
								{
									if (i < insertData.size())
									{
										fieldsNames.append(h.getColName()+", ");
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
										fieldsNames.append(h.getColName());
										if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                    	{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
                                    	}
										else
										{
											fieldsValue.append(h.getDataName());
										}
									}
									i++;
								}
							}
	                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
	                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "User",fieldsValue.toString(), fieldsNames.toString(), userAcId, connectionSpace);
							
							boolean smsFlag = true, emailFlag = true;
							// Mail
							// Sending***************************************
							// *
							// *********************************************
							// **
							if (emailNotification.equals("true"))
							{
								emailFlag = sendEmail(getMobileNo(), UrlMail, getUserID(), getPassword(),rightsName);
							}

							// SMS
							// Sending***************************************
							// *
							// *********************************************
							// ****
							if (smsNotification.equals("true"))
							{
								smsFlag = sendSMS(UrlMail, getUserID(), getPassword(), getMobileNo(), name);
							}

							if (smsFlag && emailFlag)
							{
								addActionMessage("User Registered Successfully. With SMS & Email Notification.");
							}
							else if (smsFlag)
							{
								addActionMessage("User Registered Successfully. With SMS Notification Only.");
							}
							else if (emailFlag)
							{
								addActionMessage("User Registered Successfully. With Email Notification Only.");
							}
							else
							{
								addActionMessage("User Registered Successfully. without notification.");
							}
						}
					}
				}
			}
			else
			{
				addActionMessage("ERROR: User Not Registered Successfully !");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createUser of class " + getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return SUCCESS;
	}*/
	
	@SuppressWarnings("rawtypes")
	public String createUser()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			 
				CommonOperInterface coi = new CommonConFactory().createInterface();
				StringBuilder rights = new StringBuilder();
				StringBuilder userForProduct=new StringBuilder();
				StringBuilder userForProductName=new StringBuilder();
				HashSet<String> rightsHeadingSet = new HashSet<String>();
				request = ServletActionContext.getRequest();
				if (!getRightsUtility(request, rights, rightsHeadingSet))
				{
					addActionMessage("ERROR: User Rights !");
					return ERROR;
				}
				StringBuilder deptList=new StringBuilder();
				String deptList1="";
				for(int a=1;a<=8;a++)
				{
					 
					if(rights.toString().contains(a+"_VIEW"))
						deptList.append(a+",");
				}
				if(deptList.toString().length()>=1)
				{
				deptList1=deptList.toString().substring(0, deptList.toString().length()-1);
			//	System.out.println("LIST : "+deptList.toString().substring(0, deptList.toString().length()-1));
				}
				String rightsName = "";
				for (String h : rightsHeadingSet)
				{
					rightsName += "'" + h + "',";
				}
				rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));
				String appCodes=fetchAppCodeFromRights(rightsName,connectionSpace);
				System.out.println(appCodes+":::rightsName    " +rightsName);
				if (appCodes!=null)
				{
					AllConnection Conn = new ConnectionFactory("localhost");
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					SessionFactory sessfact = Ob1.getSession();
					String qry=null;
					qry="SELECT id,app_code from apps_details WHERE app_code IN("+ appCodes + ")";
					List dataTempWithCode = cbt.executeAllSelectQuery(qry, sessfact);
					if (dataTempWithCode != null && !dataTempWithCode.isEmpty())
					{
						int i=1;
						for (Iterator iterator = dataTempWithCode.iterator(); iterator.hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							if (object[1]!=null && object[0]!=null) 
							{
								
								if(i<dataTempWithCode.size())
								{
									userForProduct.append(object[0].toString()+",");
									userForProductName.append(object[1].toString()+",");
								}
								else
								{
									userForProduct.append(object[0].toString());
									userForProductName.append(object[1].toString());
								}
							}
							i++;
						}
					}
				}
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ob.setColName("mobileNo");
				ob.setDataName(mobileNo);
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("name");
				ob.setDataName(name);
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("userID");
				ob.setDataName(Cryptography.encrypt(getUserID(), DES_ENCRYPTION_KEY));
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("linkVal");
				ob.setDataName(rights.toString());
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("password");
				ob.setDataName(Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY));
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("active");
				ob.setDataName("1");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("userType");
				ob.setDataName(userType);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userForProductId");
				ob.setDataName(userForProduct.toString());
				insertData.add(ob);
				if(deptList1.length()>=1)
				{
				if (insertData != null && insertData.size() > 0)
				{
					int userAcId = coi.insertDataReturnId("useraccount", insertData, connectionSpace);
					if (userAcId > 0)
					{
						insertData.clear();
						
						ob = new InsertDataTable();
						ob.setColName("emp_id");
						ob.setDataName(contactName);
						insertData.add(ob);
						 
						ob = new InsertDataTable();
						ob.setColName("deptList");
						ob.setDataName(deptList1);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("userAccntId");
						ob.setDataName(userAcId);
						insertData.add(ob);
						 
						coi.insertIntoTable("department_rights", insertData, connectionSpace);
					 
						
						String deptId[]=fetchDeptId(contactName,connectionSpace);
						if (deptId[1]!=null && !deptId.equals(""))
						{
							String[] productName=userForProductName.toString().split(",");
							if (productName!=null && !productName.toString().equalsIgnoreCase(""))
							{
								for (String str : productName)
								{
									insertData.clear();
									
									ob = new InsertDataTable();
									ob.setColName("forDept_id");
									ob.setDataName(deptId[1]);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("fromDept_id");
									ob.setDataName(deptId[1]);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("emp_id");
									ob.setDataName(contactName);
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

									ob = new InsertDataTable();
									ob.setColName("work_status");
									ob.setDataName("0");
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("level");
									ob.setDataName("1");
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("moduleName");
									ob.setDataName(str);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("deptLevel");
									ob.setDataName("1");
									insertData.add(ob);
									coi.insertIntoTable("compliance_contacts", insertData, connectionSpace);
									
									insertData.clear();
									 
								}
							}
						}
						int j = coi.executeAllUpdateQuery("update employee_basic set useraccountid = '" + userAcId + "' where id = '" + contactName + "'", connectionSpace);
						if (j > 0)
						{
							//String ip = new IPAddress().getIPAddress();
							// System.out.println("URLMAIL  "+ ip);
							//String UrlMail = "http://" + ip + request.getContextPath();
							String UrlMail = "over2cloud.co.in" ;
						 //  System.out.println("URLMAIL  "+ UrlMail);
							List rightsHeadingList = coi.executeAllSelectQuery("select Rights_heading from user_rights where rights_name in (" + rightsName + ")", connectionSpace);
							rightsName = "";
							if (rightsHeadingList != null && rightsHeadingList.size() > 0)
							{
								for (Object obj : rightsHeadingList)
									rightsName += obj.toString() + ", ";
							}
							rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));
							
							StringBuilder fieldsNames=new StringBuilder();
	                		StringBuilder fieldsValue=new StringBuilder();
	                		if (insertData!=null && !insertData.isEmpty())
							{
								int i=1;
								for (InsertDataTable h : insertData)
								{
									if (i < insertData.size())
									{
										fieldsNames.append(h.getColName()+", ");
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
										fieldsNames.append(h.getColName());
										if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                    	{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
                                    	}
										else
										{
											fieldsValue.append(h.getDataName());
										}
									}
									i++;
								}
							}
	                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
	                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "User",fieldsValue.toString(), fieldsNames.toString(), userAcId, connectionSpace);
							
							boolean smsFlag = true, emailFlag = true;
							// Mail
							// Sending***************************************
							// *
							// *********************************************
							// **
							if (emailNotification.equals("true"))
							{
								emailFlag = sendEmail(getMobileNo(), UrlMail, getUserID(), getPassword(),rightsName);
							}

							// SMS
							// Sending***************************************
							// *
							// *********************************************
							// ****
							if (smsNotification.equals("true"))
							{
								smsFlag = sendSMS(UrlMail, getUserID(), getPassword(), getMobileNo(), name);
							}

							if (smsFlag && emailFlag)
							{
								addActionMessage("User Registered Successfully. With SMS & Email Notification.");
							}
							else if (smsFlag)
							{
								addActionMessage("User Registered Successfully. With SMS Notification Only.");
							}
							else if (emailFlag)
							{
								addActionMessage("User Registered Successfully. With Email Notification Only.");
							}
							else
							{
								addActionMessage("User Registered Successfully. without notification.");
							}
						}
					}
				}
			}
				else
					addActionMessage("ERROR: User Not Registered Successfully !....Please give rights of atleast one department");
			 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createUser of class " + getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String[] fetchDeptId(String empID,SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		String[] data=new String[2];
		List dataList = null;
		try
		{
			qryString.append(" SELECT emp.id,emp.deptname FROM  employee_basic AS emp");
			qryString.append(" WHERE  emp.id='"+empID+"' ");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList!=null && dataList.size()>0) 
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1]!=null && object[0]!=null)
					{
						data[0]=getValueWithNullCheck(object[0]);
						data[1]=getValueWithNullCheck(object[1]);
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return data;
		
	}
	@SuppressWarnings("rawtypes")
	private String fetchAppCodeFromRights(String rightsName,SessionFactory connectionSpace2) 
	{
		String appCode=null;
		try 
		{
			StringBuilder apps=new StringBuilder();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String query="SELECT module_abbreviation FROM user_rights WHERE rights_name IN("+rightsName+") GROUP BY module_abbreviation";
			List data=coi.executeAllSelectQuery(query, connectionSpace2);
			if (data!=null && !data.isEmpty()) 
			{
				int i=1;
				for (Iterator iterator = data.iterator(); iterator.hasNext();) 
				{
					Object object = (Object) iterator.next();
					if (object!=null) 
					{
						if(i<data.size())
						{
							apps.append("'"+object.toString()+"',");
						}
						else
						{
							apps.append("'"+object.toString()+"'");
						}
					}
					i++;
				}
			}
			if (apps.toString()!=null && !apps.toString().equalsIgnoreCase(""))
			{
				appCode=apps.toString();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return appCode;
	}
	/*
	 * public String createUser() { System.out.println("11111111111111111111");
	 * try { if(userName==null || userName.equalsIgnoreCase("")) return LOGIN;
	 * if(getStatus().equalsIgnoreCase("false")) {
	 * System.out.println("444444444444444444444444444"); request =
	 * ServletActionContext.getRequest(); String linkVal =
	 * UserCtrlActionHelper.getLinkRight(request); String sublinkVal =
	 * UserCtrlActionHelper.getSubLinkRight(request); CommonOperInterface
	 * cbt=new CommonConFactory().createInterface(); boolean userTrue=false;
	 * boolean dateTrue=false; boolean timeTrue=false;
	 * List<GridDataPropertyView>
	 * org2=Configuration.getConfigurationData("mapped_user_account_configuration"
	 * ,
	 * accountID,connectionSpace,"",0,"table_name","user_details_configuration")
	 * ; if(org2!=null) { //create table query based on configuration List
	 * <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	 * for(GridDataPropertyView gdp:org2) { TableColumes ob1=new TableColumes();
	 * ob1=new TableColumes(); ob1.setColumnname(gdp.getColomnName());
	 * ob1.setDatatype("varchar(255)"); ob1.setConstraint("default NULL");
	 * Tablecolumesaaa.add(ob1);
	 * if(gdp.getColomnName().equalsIgnoreCase("userName")) userTrue=true; else
	 * if(gdp.getColomnName().equalsIgnoreCase("date")) dateTrue=true; else
	 * if(gdp.getColomnName().equalsIgnoreCase("time")) timeTrue=true; }
	 * cbt.createTable22("useraccount",Tablecolumesaaa,connectionSpace); }
	 * System.out.println("222222222222222222222222222"); //getting the
	 * parameters nd setting their value using loop StringBuilder rights = new
	 * StringBuilder(); List<String> requestParameterNames =
	 * Collections.list((Enumeration<String>) request.getParameterNames());
	 * Collections.sort(requestParameterNames); Iterator it1 =
	 * requestParameterNames.iterator(); List <InsertDataTable> insertData=new
	 * ArrayList<InsertDataTable>(); InsertDataTable ob=null; while
	 * (it1.hasNext()) { String Parmname = it1.next().toString(); String
	 * paramValue=request.getParameter(Parmname); if(paramValue!=null &&
	 * !paramValue.equalsIgnoreCase("") && Parmname!=null &&
	 * !Parmname.equals("userID") && !Parmname.equals("linkVal")&&
	 * !Parmname.equals("sublinkVal")&& !Parmname.equals("password")&&
	 * !Parmname.equals("userForProductId") && !Parmname.equals("deptname")&&
	 * !Parmname.equals("subdept")&& !Parmname.equals("subdept")&&
	 * !Parmname.contains("checkbox") && !Parmname.equals("deptHierarchy")&&
	 * !Parmname.equals("orgLevel")&& !Parmname.equals("levelOforganization") &&
	 * !Parmname.equals("empName")&& !Parmname.equals("level2org")&&
	 * !Parmname.equals("level3org")&& !Parmname.equals("level4org") &&
	 * !Parmname.equals("level5org")&& !Parmname.equals("empnnna")&&
	 * !Parmname.equals("level")&& !Parmname.equals("repassword")&&
	 * !Parmname.equals("status")) {
	 * System.out.println("paramValue:"+paramValue);
	 * if(paramValue.endsWith("_ADD") || paramValue.endsWith("_VIEW") ||
	 * paramValue.endsWith("_MODIFY") || paramValue.endsWith("_DELETE")) {
	 * rights.append(paramValue); } ob=new InsertDataTable();
	 * ob.setColName(Parmname); ob.setDataName(paramValue); insertData.add(ob);
	 * } } System.out.println("rights:::::::::::;"+rights);
	 * //Cryptography.encrypt
	 * (getUsername(),DES_ENCRYPTION_KEY),Cryptography.encrypt
	 * (getPassword(),DES_ENCRYPTION_KEY)) ob=new InsertDataTable();
	 * ob.setColName("userID");
	 * ob.setDataName(Cryptography.encrypt(getUserID(),DES_ENCRYPTION_KEY));
	 * insertData.add(ob); ob=new InsertDataTable(); ob.setColName("linkVal");
	 * ob.setDataName(linkVal); insertData.add(ob); ob=new InsertDataTable();
	 * ob.setColName("sublinkVal"); ob.setDataName(sublinkVal);
	 * insertData.add(ob); ob=new InsertDataTable(); ob.setColName("password");
	 * ob.setDataName(Cryptography.encrypt(getPassword(),DES_ENCRYPTION_KEY));
	 * insertData.add(ob); if(userTrue) { ob=new InsertDataTable();
	 * ob.setColName("userName"); ob.setDataName(userName); insertData.add(ob);
	 * } if(dateTrue) { ob=new InsertDataTable(); ob.setColName("date");
	 * ob.setDataName(DateUtil.getCurrentDateUSFormat()); insertData.add(ob); }
	 * if(timeTrue) { ob=new InsertDataTable(); ob.setColName("time");
	 * ob.setDataName(DateUtil.getCurrentTime()); insertData.add(ob); } int i=1;
	 * String tempProduct[]=getUserForProductId().split(", "); String
	 * selectedProductIds=new String(); Getting Session of cloud db due to
	 * getting the id's of the selected products AllConnection Conn=new
	 * ConnectionFactory("localhost"); AllConnectionEntry
	 * Ob1=Conn.GetAllCollectionwithArg(); SessionFactory
	 * sessfact=Ob1.getSession(); for(String H:tempProduct) {
	 * if(!H.equalsIgnoreCase("")) { StringBuilder getIdsOfProduct=new
	 * StringBuilder(""); getIdsOfProduct.append("select id from "
	 * +"apps_details where App_code='"+H+"'"); List
	 * dataTemp=cbt.executeAllSelectQuery(getIdsOfProduct.toString(),sessfact);
	 * if(dataTemp!=null) { for(Iterator it=dataTemp.iterator(); it.hasNext();)
	 * { Object obdata=(Object)it.next(); if(i < tempProduct.length)
	 * selectedProductIds=selectedProductIds+""+obdata.toString()+", "; else
	 * selectedProductIds=selectedProductIds+""+obdata.toString()+""; i++; } } }
	 * } ob=new InsertDataTable(); ob.setColName("userForProductId");
	 * ob.setDataName(selectedProductIds); insertData.add(ob); ob=new
	 * InsertDataTable(); ob.setColName("validityFrom");
	 * ob.setDataName(DateUtil.getCurrentDateUSFormat()); insertData.add(ob);
	 * ob=new InsertDataTable(); ob.setColName("validityto");
	 * ob.setDataName(DateUtil.getCurrentDateUSFormat()); insertData.add(ob);
	 * boolean
	 * status=cbt.insertIntoTable("useraccount",insertData,connectionSpace);
	 * if(status) { //update the employee table with user account id based on
	 * the selected employee id in user creation page int
	 * idMAx=cbt.getMaxId("useraccount",connectionSpace);
	 * Map<String,Object>wherClause=new HashMap<String, Object>();
	 * Map<String,Object>wherClauseCodntn=new HashMap<String, Object>();
	 * wherClause.put("useraccountid", Integer.toString(idMAx));
	 * wherClauseCodntn.put("id", getEmpName());
	 * cbt.updateTableColomn("employee_basic",
	 * wherClause,wherClauseCodntn,connectionSpace); addActionMessage("User
	 * Registered Successfully!!!"); } else { addActionMessage("Ooops There Is
	 * Some Problem In User Registration!"); } } else { addActionMessage("Ooops
	 * User Name Is Already Registered, Please Try Again With Other User
	 * Name!"); System.out.println("33333333333333333333333333"); } return
	 * SUCCESS; } catch(Exception e) { log.error("Date :
	 * "+DateUtil.getCurrentDateIndianFormat()+" Time:
	 * "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud method
	 * createUser of class "+getClass(), e); e.printStackTrace();
	 * addActionError("Oopss there is some problem!!!"); return ERROR; } }
	 */

	// Anoop 19-9-2013, get rights
	/**
	 * @param request
	 * @param rights
	 * @param rightsHeadingSet
	 * @return returns rights name and rights module name
	 */
	private boolean getRightsUtility(HttpServletRequest request, StringBuilder rights, HashSet<String> rightsHeadingSet)
	{
		boolean flag = false;
		try
		{
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it1 = requestParameterNames.iterator();
			while (it1.hasNext())
			{
				String Parmname = it1.next().toString();
				String paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
				{
					if ((paramValue.endsWith("_ADD") || paramValue.endsWith("_VIEW") || paramValue.endsWith("_MODIFY") || paramValue.endsWith("_DELETE")) && !Parmname.startsWith("_"))
					{
						rights.append(paramValue);
						rights.append("#");
						rightsHeadingSet.add(paramValue.substring(0, paramValue.indexOf('_')));
					}
				}
			}
			flag = true;
		}
		catch (Exception e)
		{
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	// Anoop 19-9-2013, Send SMS
	private boolean sendSMS(String... param)
	{
		boolean smsFlag = true;// true means problem in email sending
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String sms = "Congrats! your login has been successfully created. Plz. login on " + param[0] + " with User ID: " + param[1] + " & Password: " + param[2]
					+ " to reset the same for security reasons. For any issues call " + "0120-4316414.";
			List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("msisdn");
			ob.setDataName(param[3]);
			insertData1.add(ob);

			ob = new InsertDataTable();
			ob.setColName("msg");
			ob.setDataName(sms);
			insertData1.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData1.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData1.add(ob);

			// smsFlag = !coi.insertIntoTable("msg_stats", insertData1,
			// HibernateSessionFactory.getSessionFactory());
			smsFlag = new MsgMailCommunication().addMessage(param[4], "Admin", param[3], sms, "", "Pending", "0", "Common");
		}
		catch (Exception e)
		{
			smsFlag = true;
			e.printStackTrace();
		}
		return smsFlag;
	}

	// Anoop 19-9-2013, Send Mail
	private boolean sendEmail(String... param)
	{
		boolean emailFlag = true;// true means problem in email sending
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String qry="select emailIdOne, comName from employee_basic where mobOne ='"+param[0]+"'";
			//System.out.println("Qry is as :: "+qry);
			List empEmail = coi.executeAllSelectQuery(qry, connectionSpace);
			if (empEmail != null && empEmail.size() > 0)
			{
				Object[] obj=(Object[])empEmail.get(0);
				if (obj[0] != null && obj[1] != null)
				{
					StringBuffer buffer = new StringBuffer(
							"<html><body><p><strong>Dear "
									+ obj[1].toString()
									+ "</strong>,</p><p>Greetings from <strong>"
									+ session.get("orgname").toString()
									+ "</strong>!<br />Congrats, your login has been successfully created.</p><p><center><table border='1'>	<tr><th colspan='2'>Here are the login credentials for the same</th></tr><tr><td width='200px'>URL</td><td width='100px'>"
									+ param[1]
									+ "</td></tr>	<tr><td width='200px'>Login ID</td><td width='200px'>"
									+ param[2]
									+ "</td></tr>	<tr><td width='200px'>Password</td><td width='200px'>"
									+ param[3]
									+ "</td></tr>	<tr><td width='200px'>User Access</td width='200px'><td>"
									+ param[4]
									+ "</td></tr>	</table></center></p><p>We recommend you to please change the password as soon as you get these details for security reasons.</p><p>Please use the application &amp; provide us your valuable feedback at any point of time by clicking on 'Feedback' link provided in the application footer.</p><p>For any issues at any point of time, please contact: <strong>"
									+ "0120-4316414</strong> &amp; also mail with details to <strong>support@dreamsol.biz</strong>.</p><p>&nbsp;</p><p><span style='font-size: smal;"
									+ "color: #993366;'><strong><span style='font-family: 'times new roman', times;'>Thanks &amp; Regards,</span></strong></span><br /><span style='font-size: small; color: #993366;'><strong><span style='font-family: 'times new roman', times;'>Support Team.</span></strong></span></p><p><span style='font-family: 'arial black', 'avant garde'; font-size: small; color: #ff9900;'>DreamSol TeleSolutions Pvt. Ltd.</span><br /><span style='color: #993366;'>An ISO 9001:2000 Certified Company...</span><br />India: C-52, Sector-2, Noida-201301<br /><span style='color: #3366ff;'><a name='www.dreamsol.biz'></a>www.dreamsol.biz | <a name='www.punchsms.com'></a>www.punchsms.com | <a name='www.mgov.in'></a>www.mgov.in | <a name='www.over2cloud.com'></a>www.over2cloud.com</span><br /><span style='color: #000080;'><strong>Innovating Business Processes via automated ICT solutions integrated with unified communications... offered over Public / Private Cloud &amp; Mobile Apps.</strong></span><br />---------------------------------------"
									+ "------------------------------------------------------------------------------<br /><span style='color: #800080;'>This e-mail may contain proprietary and confidential information and is sent for the intended recipient(s) only. If by an addressing or transmission error this mail has been misdirected, you are requested to please notify us by reply e-mail and delete this mail immediately. You are also hereby notified that any use, any form of reproduction, dissemination, copying, disclosure, modification, distribution and / or publication of this e-mail message, contents or its attachment other than by its intended recipient(s) is strictly prohibited. \"Thank you.\"</span><br />\"<span style='color: #99cc00;'>Every 3000 sheets of paper cost us a tree. Let's consider our environmental responsibility before printing this e-mail -</span> <strong><span style='color: #ff0000;'>Save paper</span></strong>.\"<br /><br /></p></body></html>");

					emailFlag = new MsgMailCommunication().addMail( obj[1].toString(), "Admin",obj[0].toString(), "Login Credentials for " + obj[1].toString(), buffer
							.toString(), "", "Pending", "0", "", "FM");
				}
			}
			// emailFlag = !mt .confMailHTML( data[1].toString(),
			// data[2].toString(), data[3].toString(), data[4].toString(),
			// dataEm[0].toString(), "Login Credentials for " +
			// dataEm[1].toString(), ");
		}
		catch (Exception e)
		{
			emailFlag = true;
			e.printStackTrace();
		}
		return emailFlag;
	}

	public String beforeUserView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName = "User Registration >> View";
			editPrvlg = "false";
			setGridColomnNames();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeUserView of class " + getClass(), e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public String beforeUserModify()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName = "User Registration >> Modify";
			editPrvlg = "true";
			setGridColomnNames();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeUserModify of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void setGridColomnNames()
	{
		//id,mobileNo,name,userID,linkVal,sublinkVal,password,date,time,userName
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("dept.deptName");
		gpv.setHeadingName("Department");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.name");
		gpv.setHeadingName("Contact Name");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.mobileNo");
		gpv.setHeadingName("Mobile No");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.userID");
		gpv.setHeadingName("User Id");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.password");
		gpv.setHeadingName("Password");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.userType");
		gpv.setHeadingName("User Type");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.active");
		gpv.setHeadingName("Status");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(60);
		userSelectedColnName.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ua.deActiveOn");
		gpv.setHeadingName("Date");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		userSelectedColnName.add(gpv);
		
			session.put("userSelectedColnName", userSelectedColnName);
	}

	
	
	@SuppressWarnings("rawtypes")
	public Map<String,String> getCounters(String userType,String empIDS,SessionFactory connectionSpace)
	{
		Map<String,String> counter=new LinkedHashMap<String, String>();
		
		StringBuilder builder=new StringBuilder(" SELECT count(*),ua.active FROM useraccount AS ua ");
		builder.append(" INNER JOIN employee_basic AS emp ON emp.useraccountid=ua.id  ");
		builder.append(" INNER JOIN department AS dept ON dept.id=emp.deptName  LEFT join groupinfo as grp on grp.id=dept.groupId   ");
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
	   			 builder.append(" AND  emp.deptname='"+deptId[3]+"'   ");
	   		 }
		} 
      	else if (userType!=null && userType.equalsIgnoreCase("N"))
      	{
      		builder.append("WHERE  branch.id="+s[1]+" AND emp.id=" +empIDS+ " ");
		}
		
		 builder.append(" Group By ua.active ");
		//System.out.println(builder);
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0]!=null && object[1]!=null)
				{
					total=total+Integer.parseInt(object[0].toString());
					if ( object[1].toString().equalsIgnoreCase("1"))
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewUserData()
	{
		try
		{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String empIdofuser= (String)session.get("empIdofuser");
				if (empIdofuser == null
						|| empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				String empId = empIdofuser.split("-")[1].trim();
				
				List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>)session.get("userSelectedColnName");
				if (session.containsKey("userSelectedColnName"))
				{
					if (fieldNames!=null) 
					{
						session.remove("userSelectedColnName");
					}
				}
				StringBuilder dataQuery=new StringBuilder("SELECT ua.id,dept.deptName,ua.name,ua.mobileNo,ua.userID,ua.password,ua.userType,ua.active,ua.deActiveOn");
				dataQuery.append(" FROM useraccount AS ua INNER JOIN employee_basic AS emp ON emp.useraccountid=ua.id ");
				dataQuery.append(" INNER JOIN department AS dept ON dept.id=emp.deptName ");
				dataQuery.append(" LEFT JOIN groupinfo as grp on grp.id = dept.groupId ");
				dataQuery.append(" LEFT JOIN branchoffice_data as brand on grp.regLevel = brand.id ");
				dataQuery.append(" LEFT JOIN headoffice_data as head on head.id=brand.headOfficeId ");
				dataQuery.append(" LEFT JOIN country_data as country on country.id=head.countryId WHERE");
		         String userType = (String)session.get("userTpe");
	             String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
	             if (userType!=null && userType.equalsIgnoreCase("H")) 
	             {
	            	 dataQuery.append("  head.id="+s[2] +"  ");
              		String deptId[]=new ComplianceUniversalAction().getEmpDataByUserName(userName);
					if (deptId!=null && !deptId.toString().equalsIgnoreCase("")) 
					{
						dataQuery.append(" AND emp.deptname='"+deptId[3]+"'  ");
					}
				 } 
	             else if (userType!=null && userType.equalsIgnoreCase("N"))
	             {
	            	 dataQuery.append("  brand.id="+s[1]+" AND emp.id='"+empId+"' ");
				 }
				System.out.println("dataQuery ::::::: " +dataQuery);
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("-1"))
				{
					if (userType!=null && !userType.equalsIgnoreCase("M"))
					{
						dataQuery.append(" AND  ");
					}
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						if (getSearchField().equalsIgnoreCase("ua.active"))
						{
							dataQuery.append("  " + getSearchField() + " = '" + getSearchString() + "'");
						}
						else
						{
							dataQuery.append("  " + getSearchField() + " = '" + getSearchString() + "' AND  ua.active = '1'"); 
						}
						
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						dataQuery.append(" ua." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						dataQuery.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						dataQuery.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						dataQuery.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}
				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						dataQuery.append(" order by " + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						dataQuery.append(" order by " + getSidx() + " DESC");
					}
				}
				System.out.println("Query String "+dataQuery.toString());
				List data = cbt.executeAllSelectQuery(dataQuery.toString(), connectionSpace);
				if (data != null)
				{
					List dataList=new ArrayList();
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						int k=0;
						for(GridDataPropertyView GDPV : fieldNames)
						{
							if(obdata[k] != null)
							{
								if(GDPV.getColomnName().equalsIgnoreCase("ua.userID") || GDPV.getColomnName().equalsIgnoreCase("ua.password"))
								{
									one.put(GDPV.getColomnName(), Cryptography.decrypt(obdata[k].toString(), DES_ENCRYPTION_KEY));
								}
								else if(GDPV.getColomnName().equalsIgnoreCase("ua.userType"))
								{
									one.put(GDPV.getColomnName(), getUserType(obdata[k].toString()));
								}
								else if(GDPV.getColomnName().equalsIgnoreCase("ua.active"))
								{
									if (obdata[k].toString().equalsIgnoreCase("1"))
									{
										one.put(GDPV.getColomnName(),"Active");
									}
									else
									{
										one.put(GDPV.getColomnName(),"Inactive");
									}
								}
								else if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
								{
									one.put(GDPV.getColomnName(),DateUtil.convertDateToIndianFormat( obdata[k].toString()));
								}
								else
								{
									one.put(GDPV.getColomnName(), obdata[k].toString());
								}
							}
							else
							{
								one.put(GDPV.getColomnName(),"NA");
							}
							k++;
						}
						dataList.add(one);
					}
					setUserDataList(dataList);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewUserData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}





	public String getUserType(String abrivation)
	{
		if(abrivation.equalsIgnoreCase("N"))
		{
			abrivation="Normal";
		}
		else if(abrivation.equalsIgnoreCase("H"))
		{
			abrivation="HOD";
		}
		else if(abrivation.equalsIgnoreCase("M"))
		{
			abrivation="Management";
		}
		return abrivation;
	}
	
	
	public String editEmpGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt2 = new CommonConFactory().createInterface();

			if (getOper().equalsIgnoreCase("del"))
			{/*
			 * CommonOperInterface cbt=new CommonConFactory().createInterface();
			 * Map<String, Object>wherClause=new HashMap<String, Object>();
			 * Map<String, Object>condtnBlock=new HashMap<String, Object>();
			 * if(makeHistory.equalsIgnoreCase("0")){ wherClause.put("status",
			 * 1); condtnBlock.put("id",getId());
			 * cbt.updateTableColomn("employee_basic", wherClause,
			 * condtnBlock,connectionSpace); } else{ wherClause.put("status",
			 * 0); condtnBlock.put("id",getId());
			 * cbt.updateTableColomn("employee_basic", wherClause,
			 * condtnBlock,connectionSpace); }
			 */
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method editEmpGrid of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String userShowRights()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			// get privalges, split them by # and add in list and check for
			// blank
			List userprvlgs = new ArrayList<String>();
			userprvlgs.add("sublinkVal");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("id", getId());
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			userprvlgs = cbt.viewAllDataEitherSelectOrAll("useraccount", userprvlgs, temp, connectionSpace);
			if (userprvlgs != null)
			{
				for (Object c : userprvlgs)
				{
					Object data = (Object) c;
					String tempList[] = data.toString().split("#");
					for (String H : tempList)
					{
						if (!H.equalsIgnoreCase("") && H != null)
						{
							prvlgsList.add(H);
						}
					}

				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method userShowRights of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String editUser()
	{
		String returnResult = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("del"))
			{

				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				wherClause.put("active", 0);
				wherClause.put("deActiveOn",DateUtil.getCurrentDateUSFormat());
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("useraccount", wherClause, condtnBlock, connectionSpace);
				StringBuilder fieldsNames=new StringBuilder();
        		StringBuilder dataStore=new StringBuilder();
        		if (wherClause!=null && !wherClause.isEmpty())
				{
					int i=1;
					for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
					{
					    if (i < wherClause.size())
					    {
					    	fieldsNames.append("'"+entry.getKey() + "', ");
					    	dataStore.append(entry.getValue() + ", ");
					    }
						else
						{
							fieldsNames.append("'"+entry.getKey() + "' ");
							dataStore.append(entry.getValue());
						}
						i++;
					}
				}
        		UserHistoryAction UA=new UserHistoryAction();
        		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
        		UA.userHistoryAdd(empIdofuser, "Inactive", "Admin", "User", dataStore.toString(), fieldsNames.toString(),Integer.parseInt( getId()), connectionSpace);
			}
			returnResult = SUCCESS;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method editUser of class " + getClass(), e);
			addActionError("Oops there is some problem!!!");
			return ERROR;
		}
		return returnResult;
	}

	public boolean getLink(String linkVal)
	{
		boolean resultlink = false;
		if (!linkVal.equalsIgnoreCase("") && linkVal != null)
		{
			String data = (String) session.get("linkVal");
			if (data != null && data.toString().contains(linkVal))
				resultlink = true;

		}
		return resultlink;

	}

	public boolean getSubLink(String subLinkVal)
	{
		boolean resultlink = false;
		if (!subLinkVal.equalsIgnoreCase("") && subLinkVal != null)
		{
			String data = (String) session.get("sublinkVal");
			if (data != null && data.toString().contains(subLinkVal))
			{
				resultlink = true;
			}
		}

		return resultlink;

	}

	public String updateUser()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			request = ServletActionContext.getRequest();
			String linkVal = UserCtrlActionHelper.getLinkRight(request);
			String sublinkVal = UserCtrlActionHelper.getSubLinkRight(request);
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			wherClause.put("linkVal", linkVal);
			wherClause.put("sublinkVal", sublinkVal);
			wherClause.put("userForProductId", getUserForProductId());
			int i = 1;
			// check for no of users for the application
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List userprvlgs = new ArrayList<String>();
			userprvlgs.add("userForProductId");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("id", getId());
			userprvlgs = cbt.viewAllDataEitherSelectOrAll("useraccount", userprvlgs, temp, connectionSpace);
			String mappedProduct = new String();
			if (userprvlgs != null)
			{
				for (Object c : userprvlgs)
				{
					Object data = (Object) c;
					if (data != null)
						mappedProduct = data.toString();
				}
			}
			// if no of user per product will increase, no user will updated.
			String tempProduct[] = getUserForProductId().split(", ");
			String selectedProductIds = new String();
			String countProducts = new String();

			// getting the cloud db session for geting data
			AllConnection Conn = new ConnectionFactory("localhost");
			AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
			SessionFactory sessfact = Ob1.getSession();

			for (String H : tempProduct)
			{
				if (!H.equalsIgnoreCase(""))
				{

					StringBuilder getIdsOfProduct = new StringBuilder("");
					getIdsOfProduct.append("select id from apps_details where App_code='" + H + "'");
					List dataTemp = cbt.executeAllSelectQuery(getIdsOfProduct.toString(), sessfact);
					if (dataTemp != null)
					{

						for (Iterator it = dataTemp.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();

							if (i < tempProduct.length)

								selectedProductIds = selectedProductIds + "" + obdata.toString() + ", ";
							else
								selectedProductIds = selectedProductIds + "" + obdata.toString() + "";
							if (!mappedProduct.contains(obdata.toString()))
							{
								if (i < tempProduct.length)

									countProducts = countProducts + "" + H + ",";
								else
									countProducts = countProducts + "" + H + "";
							}
							i++;
						}
					}
				}
			}

			String returnResult = UserCtrlActionHelper.checkProductUser(countProducts, accountID, connectionSpace);
			if (!returnResult.equalsIgnoreCase(""))
			{
				addActionMessage(returnResult);
			}
			else
			{
				wherClause.put("userForProductId", selectedProductIds);
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("useraccount", wherClause, condtnBlock, connectionSpace);
				addActionMessage("User Updated Successfully!");
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method updateUser of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Oops there is some problem!!!");
			return ERROR;
		}

		return SUCCESS;
	}

	/*
	 * Anoop, 17-8-2013 Modify user
	 */
	/**
	 * @return
	 */
	/*@SuppressWarnings("rawtypes")
	public String modifyUserData()
	{

		try
		{
		//	System.out.println("Inside method");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder rights = new StringBuilder();
			request = ServletActionContext.getRequest();
			HashSet<String> rightsHeadingSet = new HashSet<String>();
			if (!getRightsUtility(request, rights, rightsHeadingSet))
			{
				addActionMessage("ERROR: User Rights !");
				return ERROR;
			}
			String rightsName = "";
			for (String h : rightsHeadingSet)
			{
				rightsName += "'" + h + "',";
			}
			if (rightsName != null && !rightsName.equalsIgnoreCase(""))
			{
				rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));
			}
			String appCodes=fetchAppCodeFromRights(rightsName,connectionSpace);
			StringBuilder userForProduct=new StringBuilder();
			if (appCodes!=null)
			{
				AllConnection Conn = new ConnectionFactory("localhost");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory sessfact = Ob1.getSession();
				String qry=null;
				qry="SELECT id,app_code from apps_details WHERE app_code IN("+ appCodes + ")";
				List dataTempWithCode = cbt.executeAllSelectQuery(qry, sessfact);
				if (dataTempWithCode != null && !dataTempWithCode.isEmpty())
				{
					int i=1;
					for (Iterator iterator = dataTempWithCode.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1]!=null && object[0]!=null) 
						{
							
							if(i<dataTempWithCode.size())
							{
								userForProduct.append(object[0].toString()+",");
							}
							else
							{
								userForProduct.append(object[0].toString());
							}
						}
						i++;
					}
				}
			}
			HashMap<String, Object> paramsMap = new HashMap<String, Object>();
			// paramsMap.put("password", Cryptography.encrypt(getPassword(),
			// DES_ENCRYPTION_KEY));
			paramsMap.put("linkVal", rights.toString());
			paramsMap.put("userForProductId", userForProduct.toString());
			paramsMap.put("userType", userType);
			paramsMap.put("active", active);
			paramsMap.put("deActiveOn",DateUtil.getCurrentDateUSFormat());
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("id", getId());
			StringBuilder fieldsNames=new StringBuilder();
    		StringBuilder dataStore=new StringBuilder();
    		if (paramsMap!=null && !paramsMap.isEmpty())
			{
				int i=1;
				for (Map.Entry<String, Object> entry : paramsMap.entrySet()) 
				{
				    if (i < paramsMap.size())
				    {
				    	fieldsNames.append("'"+entry.getKey() + "', ");
				    	dataStore.append(entry.getValue() + ", ");
				    }
					else
					{
						fieldsNames.append("'"+entry.getKey() + "' ");
						 dataStore.append(entry.getValue());
					}
					i++;
				}
			}
			UserHistoryAction UA=new UserHistoryAction();
			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "User",dataStore.toString(), fieldsNames.toString(), Integer.parseInt( getId()), connectionSpace);
			
			boolean status = coi.updateTableColomn("useraccount", paramsMap, whereMap, connectionSpace);
			if (status)
			{

				String ip = new IPAddress().getIPAddress();
				String UrlMail = "http://" + ip + request.getContextPath();
				List rightsHeadingList = coi.executeAllSelectQuery("select Rights_heading from user_rights where rights_name in (" + rightsName + ")", connectionSpace);
				rightsName = "";
				if (rightsHeadingList != null && rightsHeadingList.size() > 0)
				{
					for (Object obj : rightsHeadingList)
						rightsName += obj.toString() + ", ";
				}
				rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));

				boolean smsFlag = true, emailFlag = true;
				// Mail
				//Sending*******************************************************
				// ********************************
				
				 * if (emailNotification.equalsIgnoreCase("true")) { emailFlag =
				 * sendEmail(getMobileNo(), UrlMail, getUserID(), getPassword(),
				 * rightsName); }
				 * 
				 * // SMS //Sending // if
				 * (smsNotification.equalsIgnoreCase("true")) { smsFlag =
				 * sendSMS(UrlMail, getUserID(), getPassword(), mobileNo); }
				 
				addActionMessage("User Modified Successfully.");

			}
			else
				addActionMessage("Error in user modification !");

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyUserData of class " + getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return SUCCESS;
	}

	*/
	
	
	@SuppressWarnings("rawtypes")
	public String modifyUserData()
	{

		try
		{
		//	System.out.println("Inside method");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder rights = new StringBuilder();
			request = ServletActionContext.getRequest();
			HashSet<String> rightsHeadingSet = new HashSet<String>();
			if (!getRightsUtility(request, rights, rightsHeadingSet))
			{
				addActionMessage("ERROR: User Rights !");
				return ERROR;
			}
			 
			StringBuilder deptList=new StringBuilder();
			String deptList1="";
			for(int a=1;a<=8;a++)
			{
				 
				if(rights.toString().contains(a+"_VIEW"))
					deptList.append(a+",");
			}
		 	if(deptList.length()>1)
		 	 deptList1=deptList.toString().substring(0, deptList.toString().length()-1);
			String rightsName = "";
			for (String h : rightsHeadingSet)
			{
				rightsName += "'" + h + "',";
			}
			if (rightsName != null && !rightsName.equalsIgnoreCase(""))
			{
				rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));
			}
			String appCodes=fetchAppCodeFromRights(rightsName,connectionSpace);
			StringBuilder userForProduct=new StringBuilder();
			if (appCodes!=null)
			{
				AllConnection Conn = new ConnectionFactory("localhost");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory sessfact = Ob1.getSession();
				String qry=null;
				qry="SELECT id,app_code from apps_details WHERE app_code IN("+ appCodes + ")";
				List dataTempWithCode = cbt.executeAllSelectQuery(qry, sessfact);
				if (dataTempWithCode != null && !dataTempWithCode.isEmpty())
				{
					int i=1;
					for (Iterator iterator = dataTempWithCode.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1]!=null && object[0]!=null) 
						{
							
							if(i<dataTempWithCode.size())
							{
								userForProduct.append(object[0].toString()+",");
							}
							else
							{
								userForProduct.append(object[0].toString());
							}
						}
						i++;
					}
				}
			}
			HashMap<String, Object> paramsMap = new HashMap<String, Object>();
			HashMap<String, Object> departmentMap = new HashMap<String, Object>();
			// paramsMap.put("password", Cryptography.encrypt(getPassword(),
			// DES_ENCRYPTION_KEY));
			paramsMap.put("linkVal", rights.toString());
			paramsMap.put("userForProductId", userForProduct.toString());
			paramsMap.put("userType", userType);
			paramsMap.put("active", active);
			paramsMap.put("deActiveOn",DateUtil.getCurrentDateUSFormat());
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> whereMapenpId = new HashMap<String, Object>();
			whereMap.put("id", getId());
			StringBuilder fieldsNames=new StringBuilder();
    		StringBuilder dataStore=new StringBuilder();
    		if (paramsMap!=null && !paramsMap.isEmpty())
			{
				int i=1;
				for (Map.Entry<String, Object> entry : paramsMap.entrySet()) 
				{
				    if (i < paramsMap.size())
				    {
				    	fieldsNames.append("'"+entry.getKey() + "', ");
				    	dataStore.append(entry.getValue() + ", ");
				    }
					else
					{
						fieldsNames.append("'"+entry.getKey() + "' ");
						 dataStore.append(entry.getValue());
					}
					i++;
				}
			}
			UserHistoryAction UA=new UserHistoryAction();
			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "User",dataStore.toString(), fieldsNames.toString(), Integer.parseInt( getId()), connectionSpace);
			
			boolean status = coi.updateTableColomn("useraccount", paramsMap, whereMap, connectionSpace);
		 	departmentMap.put("deptList", deptList1);
			whereMapenpId.put("userAccntId", getId());
			boolean status1 = coi.updateTableColomn("department_rights", departmentMap, whereMapenpId, connectionSpace);
			 
			if (status)
			{

				String ip = new IPAddress().getIPAddress();
				String UrlMail = "http://" + ip + request.getContextPath();
				List rightsHeadingList = coi.executeAllSelectQuery("select Rights_heading from user_rights where rights_name in (" + rightsName + ")", connectionSpace);
				rightsName = "";
				if (rightsHeadingList != null && rightsHeadingList.size() > 0)
				{
					for (Object obj : rightsHeadingList)
						rightsName += obj.toString() + ", ";
				}
				rightsName = rightsName.substring(0, rightsName.lastIndexOf(","));

				boolean smsFlag = true, emailFlag = true;
				// Mail
				//Sending*******************************************************
				// ********************************
				/*
				 * if (emailNotification.equalsIgnoreCase("true")) { emailFlag =
				 * sendEmail(getMobileNo(), UrlMail, getUserID(), getPassword(),
				 * rightsName); }
				 * 
				 * // SMS //Sending // if
				 * (smsNotification.equalsIgnoreCase("true")) { smsFlag =
				 * sendSMS(UrlMail, getUserID(), getPassword(), mobileNo); }
				 */
				addActionMessage("User Modified Successfully.");

			}
			else
				addActionMessage("Error in user modification !");

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyUserData of class " + getClass(), e);
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}
		return SUCCESS;
	}
	@SuppressWarnings("rawtypes")
	public String beforeUserViewHeader() 
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
				String empIdofuser= (String)session.get("empIdofuser");
				if (empIdofuser == null
						|| empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				String empId = empIdofuser.split("-")[1].trim();
				toatlcount=new LinkedHashMap<String, String>();
				toatlcount=getCounters(userType,empId, connectionSpace);
			//	System.out.println("toatlcount   "  +toatlcount);
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
	
	public String getDeptName( String deptID,SessionFactory connectionSpace)
	{
		
		StringBuilder deptname = new StringBuilder();
		try {
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
				query.append(" SELECT  dt.id,dt.deptName FROM department as dt");
				query.append(" INNER JOIN compliance_contacts as cc ON dt.id=cc.fromDept_id");
				query.append(" WHERE  cc.forDept_id='"+deptID+"' GROUP BY dt.deptName");
				List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
					{
						Object object = (Object) iterator.next();
						deptname.append(getValueWithNullCheck(object)+",");
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptname.toString();
	}
	
	public String fetchUserType()
	{
		//System.out.println("sasfaf");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				userTypeMap = new JSONArray();
				JSONObject ob;
				StringBuilder query =new StringBuilder();
				query.append("SELECT ua.id,ua.userType FROM useraccount AS ua  ");
				query.append(" INNER JOIN employee_basic AS emp ON emp.useraccountid=ua.id  ");
				query.append(" WHERE emp.deptname="+selectedId+"  ");
				System.out.println("Quercvxvy String "+query.toString());
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob=new JSONObject();
						Object[] object = (Object[]) iterator.next();
						ob.put("id", object[0].toString());
						ob.put("name", object[1].toString());
						userTypeMap.add(ob);
						//System.out.println("userTypeMap ::" +userTypeMap);
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
	
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	
	public Map<String, String> getCheckEmp()
	{
		return checkEmp;
	}

	public void setCheckEmp(Map<String, String> checkEmp)
	{
		this.checkEmp = checkEmp;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public List<GridDataPropertyView> getUserSelectedColnName()
	{
		return userSelectedColnName;
	}

	public void setUserSelectedColnName(List<GridDataPropertyView> userSelectedColnName)
	{
		this.userSelectedColnName = userSelectedColnName;
	}

	public String getHeaderName()
	{
		return headerName;
	}

	public void setHeaderName(String headerName)
	{
		this.headerName = headerName;
	}

	public String getEditPrvlg()
	{
		return editPrvlg;
	}

	public void setEditPrvlg(String editPrvlg)
	{
		this.editPrvlg = editPrvlg;
	}

	public List<Object> getUserDataList()
	{
		return userDataList;
	}

	public void setUserDataList(List<Object> userDataList)
	{
		this.userDataList = userDataList;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
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

	public List<String> getPrvlgsList()
	{
		return prvlgsList;
	}

	public void setPrvlgsList(List<String> prvlgsList)
	{
		this.prvlgsList = prvlgsList;
	}

	public String getUserForProductId()
	{
		return userForProductId;
	}

	public void setUserForProductId(String userForProductId)
	{
		this.userForProductId = userForProductId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public boolean isSFA()
	{
		return SFA;
	}

	public void setSFA(boolean sfa)
	{
		SFA = sfa;
	}

	public boolean isHD()
	{
		return HD;
	}

	public void setHD(boolean hd)
	{
		HD = hd;
	}

	public String getSmsNotification()
	{
		return smsNotification;
	}

	public void setSmsNotification(String smsNotification)
	{
		this.smsNotification = smsNotification;
	}

	public String getEmailNotification()
	{
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification)
	{
		this.emailNotification = emailNotification;
	}

	public String getPasswordCurr()
	{
		return passwordCurr;
	}

	public void setPasswordCurr(String passwordCurr)
	{
		this.passwordCurr = passwordCurr;
	}

	public String getUserAccountType()
	{
		return userAccountType;
	}

	public void setUserAccountType(String userAccountType)
	{
		this.userAccountType = userAccountType;
	}

	public String getUserAccountId()
	{
		return userAccountId;
	}

	public void setUserAccountId(String userAccountId)
	{
		this.userAccountId = userAccountId;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}

	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}

	public Map<String, String> getToatlcount()
	{
		return toatlcount;
	}
	public void setToatlcount(Map<String, String> toatlcount)
	{
		this.toatlcount = toatlcount;
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
	public Map<String, String> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}
	public String getActive()
	{
		return active;
	}
	public void setActive(String active)
	{
		this.active = active;
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