package com.Over2Cloud.ctrl.leaveManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.communication.template.MailTextForCommunication;
import com.Over2Cloud.ctrl.hr.EmpHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class HolidayList extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(HolidayList.class);
	@SuppressWarnings("rawtypes")
	private Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	String encryptedID=(String)session.get("encryptedID");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private List<ConfigurationUtilBean> holidayColumnText=null;
	private List<ConfigurationUtilBean> holidayColumnDate=null;
	private List<ConfigurationUtilBean> policyColumnDropdown=null;
	private List<ConfigurationUtilBean> leaveTypeColumnText=null;
	private String tableName;
	private String excelName;
	private String downloadType;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	private Map<String, String> columnMap=null;
	private List<ConfigurationUtilBean> columnMap2=null;
	private String[] columnNames;
	private HttpServletRequest request;
	private String mainHeaderName;
 	private String download4;
 	private File document;
	private String documentContentType;
	private String documentFileName;
	private String empStatus;
	private InputStream fileInputStream;
	private String fileName;
	private String empname;
	private String fdate;
	private String tdate;
	private Map<Integer,String> employeeType = null;
	private List<AttendancePojo> holidayFullViewMap;
	
	
	Map<String,String> attachFileMap=new LinkedHashMap<String,String>();
	private String id;
		//GRID OPERATION
		private Integer rows = 0;
		// Get the requested page. By default grid sets this to 1.
		private Integer page = 0;
		// sorting order - asc or desc
		private String sord = "";
		// get index row - i.e. user click to sort.
		private String sidx = "";
		// Search Field
		private String searchField = "";
		// The Search String
		private String searchString = "";
		// The Search Operation
		// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
		private String searchOper = "";
		// Your Total Pages
		private Integer total = 0;
		// All Record
		private Integer records = 0;
		private boolean loadonce = false;
		//Grid colomn view
	 private String oper;
	 private List<Object> viewHoliday;
	 private List<Object> viewLeaveType;
	 private List<GridDataPropertyView>holidayGridColomns=new ArrayList<GridDataPropertyView>();
	 private List<GridDataPropertyView>leaveTypeGridColomns=new ArrayList<GridDataPropertyView>();

	 
	public String beforeHoliday()
	{
     String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			try
			{
				String accountID=(String)session.get("accountid");
				List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_leave_request_configuration",accountID,connectionSpace,"",0,"table_name","holiday_list_configuration");
				holidayColumnText=new ArrayList<ConfigurationUtilBean>();
				holidayColumnDate=new ArrayList<ConfigurationUtilBean>();
				if(requestColumnList!=null&&requestColumnList.size()>0)
				{
					for(GridDataPropertyView  gdp:requestColumnList)
					{
						ConfigurationUtilBean cub=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if(gdp.getMandatroy().equalsIgnoreCase("1")){
								cub.setMandatory(true);
							}else{
								cub.setMandatory(false);
							}
							holidayColumnText.add(cub);
						}
						else if(gdp.getColType().equalsIgnoreCase("Date")&& !gdp.getColomnName().equalsIgnoreCase("date"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if(gdp.getMandatroy().equalsIgnoreCase("1")){
								cub.setMandatory(true);
							}else{
								cub.setMandatory(false);
							}
							holidayColumnDate.add(cub);
						}
					}
					returnResult=SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Problem in method beforeHoliday of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addHolidayList()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_leave_request_configuration",accountID,connectionSpace,"",0,"table_name","holiday_list_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
					//create table query based on configuration
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> tableColume=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					for(GridDataPropertyView gdp:statusColName)
					{
						 TableColumes ob1=new TableColumes();
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
					 cbt.createTable22("holiday_list",tableColume,connectionSpace);
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 if(requestParameterNames!=null && requestParameterNames.size()>0)
					 {
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 while (it.hasNext()) {
								String parmName = it.next().toString();
								String paramValue=request.getParameter(parmName);
								if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null && !parmName.equalsIgnoreCase("holidayname") && !parmName.equalsIgnoreCase("hbrief"))
									{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									 insertData.add(ob);
									 }
								else{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(DateUtil.makeTitle(paramValue));
									 insertData.add(ob);
								     }
									}
								   }
						 if(userTrue)
						 {
							 ob=new InsertDataTable();
							 ob.setColName("userName");
							 ob.setDataName(DateUtil.makeTitle(userName));
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
					 status=cbt.insertIntoTable("holiday_list",insertData,connectionSpace); 
					 if(status)
					 {
						 addActionMessage("Data Save Successfully!!!");
						 returnResult=SUCCESS;
					 } 
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!!!!");
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
  public String viewHolidayList()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				System.out.println("inside methoddd :::" );
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				
				setMainHeaderName("Holiday List View");
				setGridColomnNames();
				returnResult=SUCCESS;		
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

	public void setGridColomnNames()
	{
		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		holidayGridColomns.add(gpv);
		try
		{
		List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_leave_request_configuration",accountID,connectionSpace,"",0,"table_name","holiday_list_configuration");
		if(statusColName!=null&&statusColName.size()>0)
		{
			for(GridDataPropertyView gdp:statusColName)
			{
				if(!gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("comment") && !gdp.getColomnName().equalsIgnoreCase("empid") && !gdp.getColomnName().equalsIgnoreCase("name") && !gdp.getColomnName().equalsIgnoreCase("email") && !gdp.getColomnName().equalsIgnoreCase("mobno")){
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				holidayGridColomns.add(gpv);
			}
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String getHolidayView()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from holiday_list");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("1");
					Object obdata=null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("SELECT ");
					List fieldNames=Configuration.getColomnList("mapped_leave_request_configuration", accountID, connectionSpace, "holiday_list_configuration");
					int i=0;
					if(fieldNames!=null&&fieldNames.size()>0)
					{
						Object obdata1=null;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dyanamic query based on selected fields
							    obdata1=(Object)it.next();
							    if(obdata1!=null)
							    {
								    if(i<fieldNames.size()-1)
								    	query.append(obdata1.toString()+",");
								    else
								    	query.append(obdata1.toString());
							    }
							    i++;
						}
					}
					query.append(" FROM holiday_list  order by fdate asc  ");
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" WHERE ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
						}
					}
					System.out.println(">>>>>>>" +query);
					query.append(" limit "+from+","+to);
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null && data.size()>0)
					{
						viewHoliday=new ArrayList<Object>();
						List<Object> Listhb=new ArrayList<Object>();
						Object[] obdata1=null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							obdata1=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata1[k]!=null)
								{
									if(fieldNames.get(k).toString().equals("fdate") || fieldNames.get(k).toString().equals("tdate") || fieldNames.get(k).toString().equals("date"))
									{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
									}
									else{
										if(k==0)
											one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
										else
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}	
								}
							}
							Listhb.add(one);
						}
						setViewHoliday(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult=SUCCESS;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifyHoliday()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String fDate=null;
					String HName=null;
					 String listtemppName="select fdate,holidayname from holiday_list WHERE id= '"+getId()+"' ";
				    	List  data1 = cbt.executeAllSelectQuery(listtemppName,connectionSpace);  
				    	
				    	if(data1!=null && data1.size()>0)
						{
				    		Object[] object = null;
				    		
				    		
							for (Iterator iterator = data1.iterator(); iterator.hasNext();) 
							{
								System.out.println("1111111111111");
								object = (Object[]) iterator.next();
								System.out.println("22222222222222222");
								if (object != null) {
									fDate =  object[0].toString();
									HName = object[1].toString();
									System.out.println("emp_mailIdss   "  +fDate);
									System.out.println("emp_mailIdss   "  +HName);
								}
							}
				    		
				    		
						}
				    
					boolean status=false;
					
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue=request.getParameter(parmName);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
								&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid")&& !parmName.equalsIgnoreCase("empname"))
						{
							if (parmName.equalsIgnoreCase("fdate") || parmName.equalsIgnoreCase("tdate")) 
							{
								wherClause.put(parmName,DateUtil.convertDateToUSFormat(paramValue));
							} 
							else 
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("id",getId());
				 status=cbt.updateTableColomn("holiday_list", wherClause, condtnBlock,connectionSpace);
					// add mail at edit time
					 String fromDate=request.getParameter("fdate");
					 String toDate=request.getParameter("tdate");
					 String hollidayName=request.getParameter("holidayname");
					 String holidayBrief=request.getParameter("hbrief");
				
					 String emp_mailIdss=null;
					 String empName=null;
					 String listOfEmp_mailId="select emp.empName,emp.emailIdOne from employee_basic as emp  INNER Join groupinfo as gi ON gi.id=emp.groupId  WHERE gi.groupName ='Employee' and emp.flag=0 ";
					
					
					 
					 
				    	System.out.println("listOfEmp_mailIdbbbbbbbbdddd" +listOfEmp_mailId);
				    	
				    	List  data13 = cbt.executeAllSelectQuery(listOfEmp_mailId,connectionSpace);
				    	
				    	System.out.println("dataaaa>>>>"+ data13.size());
				    	
				    	if(data13!=null && data13.size()>0)
						{
				    		Object[] object = null;
				    		
				    		
							for (Iterator iterator = data13.iterator(); iterator.hasNext();) 
							{
								object = (Object[]) iterator.next();
								if (object != null) {
									emp_mailIdss =  object[0].toString();
									empName = object[1].toString();
								}
							}
				    		
				    		
						}
				    
					 
	                 

	               
					 if(status)
					 {
						 
									String mailtext1=getMailText(fromDate,toDate,hollidayName,holidayBrief,empName,fDate,HName);
									 new MsgMailCommunication().addMail("empName", "",emp_mailIdss,"Schedule Holiday Change Notification ", mailtext1, "","Pending", "0", "", "HR");
									// new MsgMailCommunication().addMail("", "",emp_mailIdss,"Hoildaymodifiaction ", mailtext1, "","Pending", "0", "", "HR");
									// new MsgMailCommunication().addMail("Sumiti Bajpai", "","sumitib@dreamsol.biz","Schedule Holiday Change Notification ", mailtext1, "","Pending", "0", "", "HR"); 
					 }
					
					
					
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
						cbt.deleteAllRecordForId("holiday_list", "id", condtIds.toString(), connectionSpace);
				}
				returnResult =SUCCESS;
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
	
	
	public String getMailText(String fromDate,String toDate,String hollidayName,String holidayBrief,String empName,String fDate,String HName) 
	{
		
		
	     StringBuilder mailtext1 = new StringBuilder("");
		try {
			
			   mailtext1.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td></tr></tbody></table>");
		        mailtext1.append("<b>Dear "+empName+",</b>");
		        mailtext1.append("<br>");
		       // mailtext1.append("<br><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>This is for your information that holiday for <b> "+HName+"</b> on <b>"+DateUtil.convertDateToUSFormat(fDate)+"</b> has been Cancelled  and adjusted with <b>"+hollidayName+" </b> on <b>"+fromDate+"</b>.</table> ");
		        mailtext1.append("<br>This is for your information that holiday for <b> "+HName+"</b> on <b>"+DateUtil.convertDateToUSFormat(fDate)+"</b> has been Cancelled  and adjusted with <b>"+hollidayName+" </b> on <b>"+fromDate+"</b>.");
		        mailtext1.append("<br> <br>");
		        
		        mailtext1.append("<br><b>Thanks & Regards,</b>");
		        mailtext1.append("<br><b>Human Resource & Administration Team</b>");
		        mailtext1.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
		       	mailtext1.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
		     
			System.out.println("mailtext1" +mailtext1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mailtext1.toString();
	     
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public String beforeLeavePoli()
	{
		
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			try
			{
				String accountID=(String)session.get("accountid");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_leave_request_configuration",accountID,connectionSpace,"",0,"table_name","leave_policy_configuration");
				policyColumnDropdown=new ArrayList<ConfigurationUtilBean>();
				if(requestColumnList!=null&&requestColumnList.size()>0)
				{
					for(GridDataPropertyView  gdp:requestColumnList)
					{
						ConfigurationUtilBean cub=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if(gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							policyColumnDropdown.add(cub);
						}
					}
					employeeType =new LinkedHashMap<Integer, String>();
					List data = cbt.executeAllSelectQuery("SELECT id,etype FROM employee_type", connectionSpace);
					if (data!=null && data.size()>0) 
					{
						Object[] object=null;
						for (Iterator iterator = data.iterator(); iterator.hasNext();) 
						{
							object = (Object[]) iterator.next();
							if (object[0]!=null || object[1]!=null) 
							{
								employeeType.put(Integer.parseInt(object[0].toString()), object[1].toString());
	 					    }
						}
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				log.error("Problem in method beforeLeavePoli of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeLeaveTypeMaster()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			try
			{
				String accountID=(String)session.get("accountid");
				List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","leave_type_configuration");
				leaveTypeColumnText=new ArrayList<ConfigurationUtilBean>();
				if(requestColumnList!=null&&requestColumnList.size()>0)
				{
					ConfigurationUtilBean cub=null;
					for(GridDataPropertyView  gdp:requestColumnList)
					{
						 cub=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if(gdp.getMandatroy().equalsIgnoreCase("1")){
								cub.setMandatory(true);
							}else{
								cub.setMandatory(false);
							}
							leaveTypeColumnText.add(cub);
						}
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				log.error("Problem in method beforeLeaveTypeMaster of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addLeaveTypeMaster()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String accountID=(String)session.get("accountid");
				String userName=(String)session.get("uName");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","leave_type_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
					//create table query based on configuration
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					List <TableColumes> tableColume=new ArrayList<TableColumes>();
					TableColumes ob1=null;
					for(GridDataPropertyView gdp:statusColName)
					{
						 
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
					 cbt.createTable22("leave_type",tableColume,connectionSpace);
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 if(requestParameterNames!=null && requestParameterNames.size()>0)
					 {
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 while (it.hasNext()) 
						 {
								String parmName = it.next().toString();
								String paramValue=request.getParameter(parmName);
								if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null)
								{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(paramValue);
									 insertData.add(ob);
							    }
						}
					}
					 if(userTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("userName");
						 ob.setDataName(DateUtil.makeTitle(userName));
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
					 status=cbt.insertIntoTable("leave_type",insertData,connectionSpace); 
					 if(status)
					 {
						 addActionMessage("Data Save Successfully!!!");
						 returnResult=SUCCESS;
					 } 
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!!!!");
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
  public String beforeLeaveTypeView()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				
				setMainHeaderName("Leave Type View");
				setGridColomnNames1();
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

	public void setGridColomnNames1()
	{
		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		leaveTypeGridColomns.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_leave_type_configuration",accountID,connectionSpace,"",0,"table_name","leave_type_configuration");
			if(statusColName!=null&&statusColName.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName)
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					leaveTypeGridColomns.add(gpv);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String leaveTypeViewData()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("SELECT COUNT(*) FROM leave_type");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("1");
					Object obdata =null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
				
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("SELECT ");
					List fieldNames=Configuration.getColomnList("mapped_leave_type_configuration", accountID, connectionSpace, "leave_type_configuration");
					int i=0;
					if(fieldNames!=null&&fieldNames.size()>0)
					{
						Object obdata1=null;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dyanamic query based on selected fields
						    obdata1=(Object)it.next();
						    if(obdata1!=null)
						    {
							    if(i<fieldNames.size()-1)
							    	query.append(obdata1.toString()+",");
							    else
							    	query.append(obdata1.toString());
						    }
						    i++;
						}
					}
					query.append(" FROM leave_type");
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" WHERE ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
						}
					}
					//query.append(" limit "+from+","+to);
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null && data.size()>0)
					{
						viewLeaveType=new ArrayList<Object>();
						List<Object> Listhb=new ArrayList<Object>();
						Object[] obdata1=null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							obdata1=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if(obdata1[k]==null)
								{
								        one.put(fieldNames.get(k).toString(),"NA");
								}
								else
								{
								    if(k==0)
										one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
									else
										if (fieldNames.get(k).toString().equalsIgnoreCase("date")) 
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else 
										{
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
										}
								}
							}
							Listhb.add(one);
						  }
						  setViewLeaveType(Listhb);
						  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public String modifyLeaveType()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
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
					String parmName = it.next().toString();
					String paramValue=request.getParameter(parmName);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
							&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
						wherClause.put(parmName, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("leave_type", wherClause, condtnBlock,connectionSpace);
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
					cbt.deleteAllRecordForId("leave_type", "id", condtIds.toString(), connectionSpace);
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

// EXCEL PART FOR HOLIDAY LIST...............***********

public String getColumn4Download()
{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			if(download4!=null && download4.equals("holidayList"))
			{
				returnResult=getColumnName("mapped_leave_request_configuration","holiday_list_configuration","holidayList");
				tableName="holiday_list";
				excelName="Holiday List";
			}
			if(download4!=null && download4.equals("dailyReport"))
			{
				returnResult=getColumnName("mapped_time_slot_configuration","attendance_record_configuration","dailyReport");
				tableName="attendence_record";
				excelName="Daily Report";
			}
			if(download4!=null && download4.equals("summaryReport"))
			{
				returnResult=getColumnName("mapped_time_slot_configuration","record_data_configuration","summaryReport");
				tableName="report_data";
				excelName="Summary Report";
			}
			if(download4!=null && download4.equals("totalReport"))
			{
				returnResult=getColumnName("mapped_time_slot_configuration","record_data_configuration","totalReport");
				tableName="report_data";
				excelName="Total Report";
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getColumn4Download of class "+getClass(), e);
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
public String getColumnName(String mappedTableName,String basicTableName,String a)
{
		String returnResult=ERROR;
		try
		{
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			List<GridDataPropertyView> columnList=Configuration.getConfigurationData(mappedTableName,accountID,connectionSpace,"",0,"table_name",basicTableName);
			System.out.println("columnList" + columnList);
			columnMap=new LinkedHashMap<String, String>();
			columnMap2=new ArrayList<ConfigurationUtilBean>();
			System.out.println("columnMapcccccccddf" + columnMap);
			System.out.println("columnMap2cccccc" + columnMap2);
			if(columnList!=null&&columnList.size()>0)
			{
				ConfigurationUtilBean obj=null;
				for(GridDataPropertyView  gdp1:columnList)
				{
					obj=new ConfigurationUtilBean();
					if (a.equalsIgnoreCase("holidayList")) 
					{
						 if(!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("time") && !gdp1.getColomnName().equals("date"))
							{
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								System.out.println("gdp1.getColomnName() "+gdp1.getColomnName());
								System.out.println("gdp1.getHeadingName() "+gdp1.getHeadingName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
					}
					if (a.equalsIgnoreCase("attendanceMark")) 
					{
						 if(!gdp1.getColomnName().equalsIgnoreCase("day") && !gdp1.getColomnName().equals("working") && !gdp1.getColomnName().equals("timeslot") && !gdp1.getColomnName().equals("totalhour")
								 && !gdp1.getColomnName().equals("beforetime") && !gdp1.getColomnName().equals("aftertime") && !gdp1.getColomnName().equals("fdate") && !gdp1.getColomnName().equals("tdate")
								 && !gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("time"))
							  {
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
					 }
					if (a.equalsIgnoreCase("dailyReport")) 
					{
						 if(!gdp1.getColomnName().equalsIgnoreCase("fdate") && !gdp1.getColomnName().equals("time") && !gdp1.getColomnName().equals("empname") && !gdp1.getColomnName().equals("clientVisit")
								 && !gdp1.getColomnName().equals("tdate") && !gdp1.getColomnName().equals("status") && !gdp1.getColomnName().equals("attendence") && !gdp1.getColomnName().equals("lupdate")
								 && !gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("eworking")&& !gdp1.getColomnName().equals("lupdateto")  )
							  {
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
					 }
					if (a.equalsIgnoreCase("summaryReport") || a.equalsIgnoreCase("totalReport")) 
					{
						if (a.equalsIgnoreCase("summaryReport")) 
						{
							ConfigurationUtilBean obj2=new ConfigurationUtilBean();
							obj2.setMandatory(true);
							obj2.setKey("Month");
							obj2.setValue("Month");
							columnMap2.add(obj2);
							columnMap.put("Month", "Month");
						}
						if (a.equalsIgnoreCase("totalReport")) 
						{
							ConfigurationUtilBean obj2=new ConfigurationUtilBean();
							obj2.setMandatory(true);
							obj2.setKey("empname");
							obj2.setValue("Employee Name");
							columnMap2.add(obj2);
							columnMap.put("empname", "Employee Name");
						}
						ConfigurationUtilBean obj1=new ConfigurationUtilBean();
						obj1.setMandatory(true);
						obj1.setKey("CL");
						obj1.setValue("Last C/F");
						columnMap2.add(obj1);
						columnMap.put("CL", "Last C/F");
						
						ConfigurationUtilBean obj3=new ConfigurationUtilBean();
						obj3.setMandatory(true);
						obj3.setKey("CurrentLeave");
						obj3.setValue("Current Leave");
						columnMap2.add(obj3);
						columnMap.put("CurrentLeave", "Current Leave");
						
						ConfigurationUtilBean obj4=new ConfigurationUtilBean();
						obj4.setMandatory(true);
						obj4.setKey("Total");
						obj4.setValue("Total");
						columnMap2.add(obj4);
						columnMap.put("Total", "Total");
						
						ConfigurationUtilBean obj5=new ConfigurationUtilBean();
						obj5.setMandatory(true);
						obj5.setKey("Balance");
						obj5.setValue("Balance");
						columnMap2.add(obj5);
						columnMap.put("Balance", "Balance");
						
						 if(!gdp1.getColomnName().equalsIgnoreCase("twdays") && !gdp1.getColomnName().equals("empname") 
								 && !gdp1.getColomnName().equals("totalDeduction") 
								 && !gdp1.getColomnName().equals("date"))
						    {
								if(gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(gdp1.getHeadingName());
								columnMap2.add(obj);
								columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
							}
					}
				}
				if(columnMap!=null && columnMap.size()>0)
				{
					session.put("columnMap", columnMap);
				}
				returnResult=SUCCESS;
			}
			}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getColumnName of class "+getClass(), e);
			e.printStackTrace();
		}
		return returnResult;
}
@SuppressWarnings({ "unchecked", "rawtypes" })
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
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				Map<String, String> tempMap=new LinkedHashMap<String, String>();
				tempMap=(Map<String, String>) session.get("columnMap");
				List dataList=null;
				StringBuilder query = new StringBuilder("");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				for(int index=0;index<keyList.size();index++)
				{
					titleList.add(tempMap.get(keyList.get(index)));
				}
			    if(downloadType!=null && downloadType.equals("uploadExcel"))
				{
					returnResult=new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.",excelName, dataList, titleList, null, excelName);
				}
			    else
			    {
			    	if (download4.equalsIgnoreCase("holidayList")) 
			    	{
			    		query.append("SELECT ");
						int i = 0;
						Object obdata=null;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								}
								else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" FROM holiday_list ");
						System.out.println("queryvvvvvvvvv" +query);
						System.out.println("fdate" +fdate);
						System.out.println("tdate" +tdate);
						if (fdate!=null && !fdate.equalsIgnoreCase("") && tdate!=null && !tdate.equalsIgnoreCase("") ) 
						{
							query.append(" WHERE fdate BETWEEN '"+DateUtil.convertDateToUSFormat(fdate)+"' AND '"+DateUtil.convertDateToUSFormat(tdate)+"'");
						System.out.println("queryvvvvvvvv" +query);
						} 
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
							if (session.containsKey("columnMap"))
							{
								session.remove("columnMap");
							}
						}
					}
			    	else if (download4.equalsIgnoreCase("dailyReport")) 
			    	{
			    		query.append("SELECT ");
						int i = 0;
						Object obdata=null;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
						    obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								}
								else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" FROM attendence_record ");
						if (getFdate()!=null && getTdate()!=null && getEmpname()!=null) 
						{
							query.append(" WHERE date1 BETWEEN '"+DateUtil.convertDateToUSFormat(getFdate())+"' AND '"+DateUtil.convertDateToUSFormat(getFdate())+"' AND empname='"+getEmpname()+"' order by date1 asc");
						System.out.println("query@@@@@" +query);
						} 
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							System.out.println("dataListbvbvvv" + dataList);
							excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
							System.out.println("excelFileNamennnnnn" + excelFileName);
							if (session.containsKey("columnMap"))
							{
								session.remove("columnMap");
							}
						}
					}
			    	else if (download4.equalsIgnoreCase("summaryReport")) 
			    	{
			           if (session.containsKey("summaryData")) 
			           {
			        	   dataList =   (List) session.get("summaryData");
			        	   session.remove("summaryData");
					   }
						if (dataList != null && dataList.size() > 0)
						{
							
							excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
							if (session.containsKey("columnMap"))
							{
								session.remove("columnMap");
							}
						}
					}
			    	else if (download4.equalsIgnoreCase("totalReport")) 
			    	{
			           if (session.containsKey("totalData")) 
			           {
			        	   dataList =   (List) session.get("totalData");
			        	   session.remove("totalData");
					   }
						if (dataList != null && dataList.size() > 0)
						{
							excelFileName = new GenericWriteExcel().createExcel("DreamSol TeleSolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
							if (session.containsKey("columnMap"))
							{
								session.remove("columnMap");
							}
						}
					}
			    }
			    if(excelFileName!=null)
				{
					excelStream=new FileInputStream(excelFileName);
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
@SuppressWarnings({ "unchecked", "rawtypes" })
 public String addLeavePolicy()
  {
	try
	{
		    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		    CommonOperInterface cbt=new CommonConFactory().createInterface();
		    String accountID=(String)session.get("accountid");
		    List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
		    TableColumes ob1=new TableColumes();
		    boolean status=false;
		    List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_leave_request_configuration",accountID,connectionSpace,"",0,"table_name","leave_policy_configuration");
		    if(org2!=null)
			{
				for(GridDataPropertyView gdp:org2)
				{
					 ob1=new TableColumes();
					 ob1.setColumnname(gdp.getColomnName());
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
				}
					 ob1=new TableColumes();
					 ob1.setColumnname("document");
					 ob1.setDatatype("varchar(255)");
					 ob1.setConstraint("default NULL");
					 TableColumnName.add(ob1);
					 cbt.createTable22("leave_policy",TableColumnName,connectionSpace);
			}
		 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		 InsertDataTable ob=null;
		 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
		 Collections.sort(requestParameterNames);
		 Iterator it = requestParameterNames.iterator();
		 while (it.hasNext()) 
		 {
				String Parmname = it.next().toString();
				String paramValue=request.getParameter(Parmname);
				if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null )
				{
					 ob=new InsertDataTable();
					 ob.setColName(Parmname);
					 ob.setDataName(paramValue);
					 insertData.add(ob);
				}
		 }
	     String storeFilePath = new CreateFolderOs().createUserDir("leaveManagement")+ "//"+getDocumentFileName();
         File theFile = new File(storeFilePath);
         try 
         { 
               FileUtils.copyFile(document, theFile);
         } 
         catch (IOException e) 
         {
               e.printStackTrace();
         }
		 if(theFile!=null)
		 {
			 ob=new InsertDataTable();
			 ob.setColName("document");
			 ob.setDataName(storeFilePath);
			 insertData.add(ob);
		 }
		 status=cbt.insertIntoTable("leave_policy",insertData,connectionSpace);
		 if(status)
		 {
			 addActionMessage("Leave Policy Uploaded Successfully!!!");
			 return SUCCESS;
		 }
		 else
		 {
			 addActionMessage("Oops There is some error in data!");
			 return SUCCESS;
		 }
		}catch(Exception e){
		e.printStackTrace();
		}
	return SUCCESS;
}
@SuppressWarnings({"rawtypes" })
public String getAttachmentdownload() 
{
	String returnString=SUCCESS;
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	try
	{
		String query = "SELECT id,document FROM leave_policy WHERE etype='"+getEmpStatus()+"'";
		List attach=cbt.executeAllSelectQuery(query, connectionSpace);
		if (attach!=null && attach.size()>0) 
		{
			Object[] object=null;
			for (Iterator iterator = attach.iterator(); iterator.hasNext();) 
			{
				object = (Object[]) iterator.next();
				if (object[0]!=null && object[1]!=null) 
				{
					fileName = object[1].toString();
					attachFileMap.put("id", object[0].toString());
					attachFileMap.put("File Path", object[1].toString());
				}
				 returnString=SUCCESS;
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return returnString;
}

public String download() 
{
	System.out.println("download:::");
	boolean sessionFlag=ValidateSession.checkSession();
	if (sessionFlag) 
	{
       try
       {
         if(getId()!=null)
         {
            fileName=getId();
            File file=new File(fileName);
            if(file.exists())
            {
	            fileInputStream = new FileInputStream(file);
	            return "download";
            }
            else
            {
	             addActionError("File dose not exist");
	             return ERROR;
            }
          }
        }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      return LOGIN;
	    }
	}
	else 
	{
		return ERROR;
	}
 return SUCCESS;
}


public String holidayFullView()
{
	String ret = LOGIN;
	if (ValidateSession.checkSession())
	{
		
		try
		{
			
             holidayFullViewMap=new ArrayList<AttendancePojo>();
			
             List<AttendancePojo> halidayMap= holidayFullView(connectionSpace,accountID);
			if (halidayMap!=null) 
			{
				holidayFullViewMap =halidayMap;
			}
			
			
			ret = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	return ret;
}

public List<AttendancePojo> holidayFullView(SessionFactory connectionSpace,String accountID)
{
	AttendancePojo ap=null;

  CommonOperInterface coi = new CommonConFactory().createInterface();
  List<AttendancePojo> dataList=new ArrayList<AttendancePojo>();
  
	StringBuilder query = new StringBuilder();
	query.append(" select ho.fdate,ho.tdate,ho.holidayname,ho.hbrief ");
	query.append(" from holiday_list AS ho order by fdate asc  ");
	System.out.println(" query  holiday " +query);
	List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list != null && list.size() > 0)
	{
		Object[] object=null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) 
		{
			
			object = (Object[]) iterator.next();
			if (object!=null) 
			{
				ap =new  AttendancePojo();
				ap.setFdate(DateUtil.convertDateToIndianFormat((String) object[0]));
				ap.setTdate(DateUtil.convertDateToIndianFormat((String)object[1]));
				ap.setHolidayname(getFieldValue(object[2]));
				ap.setHbrief(getFieldValue(object[3]));
				ap.setDay(DateUtil.findDayFromDate(DateUtil.convertDateToUSFormat((String) object[0])));
				
				
			}
			dataList.add(ap);
		}
		
		System.out.println(" dataList SIZE " +dataList.size());
	}
	
	return dataList;
}


public static String getFieldValue(Object object)
{
	return (object == null || object.toString().trim().length() < 1) ? "NA" : object.toString().trim();
}

public String navigateToPage()
{
	return SUCCESS;
}

	public File getDocument() {
		return document;
	}
	
	public void setDocument(File document) {
		this.document = document;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}
	
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	
	public List<ConfigurationUtilBean> getColumnMap2() {
		return columnMap2;
	}
	
	public void setColumnMap2(List<ConfigurationUtilBean> columnMap2) {
		this.columnMap2 = columnMap2;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getExcelName() {
		return excelName;
	}
	
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public List<Object> getViewLeaveType() {
		return viewLeaveType;
	}

	public void setViewLeaveType(List<Object> viewLeaveType) {
		this.viewLeaveType = viewLeaveType;
	}

	public List<GridDataPropertyView> getLeaveTypeGridColomns() {
		return leaveTypeGridColomns;
	}

	public void setLeaveTypeGridColomns(
			List<GridDataPropertyView> leaveTypeGridColomns) {
		this.leaveTypeGridColomns = leaveTypeGridColomns;
	}
	public List<ConfigurationUtilBean> getHolidayColumnText() {
		return holidayColumnText;
	}

	public void setHolidayColumnText(List<ConfigurationUtilBean> holidayColumnText) {
		this.holidayColumnText = holidayColumnText;
	}

	public List<ConfigurationUtilBean> getHolidayColumnDate() {
		return holidayColumnDate;
	}

	public void setHolidayColumnDate(List<ConfigurationUtilBean> holidayColumnDate) {
		this.holidayColumnDate = holidayColumnDate;
	}

	public List<ConfigurationUtilBean> getPolicyColumnDropdown() {
		return policyColumnDropdown;
	}

	public void setPolicyColumnDropdown(
			List<ConfigurationUtilBean> policyColumnDropdown) {
		this.policyColumnDropdown = policyColumnDropdown;
	}

	public List<ConfigurationUtilBean> getLeaveTypeColumnText() {
		return leaveTypeColumnText;
	}

	public void setLeaveTypeColumnText(
			List<ConfigurationUtilBean> leaveTypeColumnText) {
		this.leaveTypeColumnText = leaveTypeColumnText;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getHolidayGridColomns() {
		return holidayGridColomns;
	}

	public void setHolidayGridColomns(List<GridDataPropertyView> holidayGridColomns) {
		this.holidayGridColomns = holidayGridColomns;
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

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
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

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public List<Object> getViewHoliday() {
		return viewHoliday;
	}

	public void setViewHoliday(List<Object> viewHoliday) {
		this.viewHoliday = viewHoliday;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDownload4() {
		return download4;
	}

	public void setDownload4(String download4) {
		this.download4 = download4;
	}

	public String getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	public String getDocumentContentType() {
		return documentContentType;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public void setDocumentContentType(String documentContentType) {
		this.documentContentType = documentContentType;
	}

	public String getDocumentFileName() {
		return documentFileName;
	}

	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}
	public Map<String, String> getAttachFileMap() {
		return attachFileMap;
	}

	public void setAttachFileMap(Map<String, String> attachFileMap) {
		this.attachFileMap = attachFileMap;
	}

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
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

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public Map<Integer, String> getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(Map<Integer, String> employeeType) {
		this.employeeType = employeeType;
	}

	public List<AttendancePojo> getHolidayFullViewMap() {
		return holidayFullViewMap;
	}

	public void setHolidayFullViewMap(List<AttendancePojo> holidayFullViewMap) {
		this.holidayFullViewMap = holidayFullViewMap;
	}

	

	
}
