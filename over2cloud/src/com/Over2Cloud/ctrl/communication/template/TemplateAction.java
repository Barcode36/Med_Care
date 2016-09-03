package com.Over2Cloud.ctrl.communication.template;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.TemplateUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TemplateAction extends ActionSupport implements ServletRequestAware
{
	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String deptLevel = (String) session.get("userDeptLevel");
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(TemplateAction.class);
	
	private List<GridDataPropertyView> viewTemplateGrid = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewOperatorGrid = new ArrayList<GridDataPropertyView>();
	private Map<Integer, String> operatorNameListData = new HashMap<Integer, String>();
	private List<ConfigurationUtilBean> templateName=null;
	private List<ConfigurationUtilBean> templateID=null;
	private List<ConfigurationUtilBean> operatorname=null;
	private List<Object> viewTemplateData=null;
	private List<Object> viewOperData =null;
    private List<ConfigurationUtilBean> tempList=null;
	private List<ConfigurationUtilBean> responseColumnText=null;
	private List<ConfigurationUtilBean> responseColumnDropdown=null;
	private String templateserise;
	private String remLen;
    private String templateNumber =null;
	private Map<String, String> dataMap;
	private String template_id;
	private String template;
	private String id;
	private String actionStatus;
	private String k =null;
    private String StoredTemp=null;
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
	// Grid colomn view
	private String oper;
	private String template1;
	private String template_type;
	private String template_name;
	private String chekedString;
	
	private String lengthOfTextfield=null;
	private List<TemplateUtilBean> viewtemplateContents;
	
	
	public String beforeTemplateView() 
	{
		System.out.println("   xxxxxxxxxxxxxxxxxxx");
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setTemplateView();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}
	private void setTemplateView() 
	{
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewTemplateGrid.add(gpv);


			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_templates_configuration",accountID, connectionSpace, "", 0, "table_name",
							"templates_configuration");
			for (GridDataPropertyView gdp : msg) {

				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					gpv.setFormatter(gdp.getFormatter());
					viewTemplateGrid.add(gpv);
				}
				
			}
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("takeaction");
			gpv.setHeadingName("Use Template");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setFormatter("templateFormatter");
			viewTemplateGrid.add(gpv);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String addTemplateData() {
		System.out.println("vdxaqhdv");
		String prefixVal="P";
		RandomNumberGenerator randamNum = new RandomNumberGenerator();
		
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			String templateidss =getTemplateId(connectionSpace);
			System.out.println("number vbcdhghgh " + templateidss);
			int otpNumber = randamNum.getNDigitRandomNumber(4);
			
			String strnumber = Integer.toString(otpNumber);
			 templateNumber = prefixVal+strnumber;
			
			
			setaddTemplateData();
			

		} catch (Exception e) {

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private void setaddTemplateData() {
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			templateID = new ArrayList<ConfigurationUtilBean>();
			templateName = new ArrayList<ConfigurationUtilBean>();
			
			List<GridDataPropertyView> msg = Configuration
					.getConfigurationData("mapped_templates_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"templates_configuration");
			if (msg != null) {
				System.out.println("svsvsvsv");
				for (GridDataPropertyView gdp : msg) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time")&& !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("template")&& !gdp.getColomnName().equalsIgnoreCase("status")&& !gdp.getColomnName().equalsIgnoreCase("remLen")&& !gdp.getColomnName().equalsIgnoreCase("operator")&& !gdp.getColomnName().equalsIgnoreCase("Oper_temp_id")&& !gdp.getColomnName().equalsIgnoreCase("flagStatus")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						templateID.add(obj);
					}  
					 else if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"template_id")&& !gdp.getColomnName().equalsIgnoreCase("status")&& !gdp.getColomnName().equalsIgnoreCase("remLen")&& !gdp.getColomnName().equalsIgnoreCase("operator")&& !gdp.getColomnName().equalsIgnoreCase("Oper_temp_id")&& !gdp.getColomnName().equalsIgnoreCase("flagStatus")) {
							
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							templateName.add(obj);
						}
					if (gdp.getColomnName().equalsIgnoreCase("template_id"))
					{
						setTemplateserise(templateNumber);
					}
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	
	
	public String getTemplateId(SessionFactory connectionSpace){
		String templateIdNo = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder("");
		query.append("select max(cast(SUBSTRING(template_id, 2, 4) as unsigned)) from CreateTemplate");
		System.out.println(" query vvxcvxv    "   +query);
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null)
		{
			templateIdNo = data.get(0).toString() ;
		}
		return templateIdNo;
	
	}
	
	public String insertDataForTemplate()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName=(String)session.get("uName");
				String accountID = (String) session.get("accountid");
				String countryId=(String)session.get("countryid");
			   CommonOperInterface cbt = new CommonConFactory().createInterface();
			   List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_templates_configuration",accountID, connectionSpace, "", 0, "table_name","templates_configuration");
				if (msg != null && msg.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false; 
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : msg) 
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						 if(gdp.getColomnName().equalsIgnoreCase("flagStatus"))
						  {
							  ob1.setConstraint("default 'Active'");
						  }
						  else
						  {
							  ob1.setConstraint("default NULL");
						  }
						tableColume.add(ob1);
					}
					cbt.createTable22("CreateTemplate", tableColume,connectionSpace);
					
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
							
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase(getRemLen()))
							{
								if(parmName.equalsIgnoreCase("template_type")){
									template_type=paramValue;
								}
								else if(parmName.equalsIgnoreCase("template_name")){
									template_name=paramValue;
								}
								else{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
								}
							}
						}
					}
					
					ob = new InsertDataTable();
					ob.setColName("template_type");
					ob.setDataName(template_type);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("template_name");
					ob.setDataName(template_name);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Pending");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateIndianFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					
					status=cbt.insertIntoTable("CreateTemplate", insertData, connectionSpace);
					if (status)
					{ 
						addActionMessage("Template Added SuccessFully.");
						returnResult = SUCCESS;
						String  existList11 = null;
				    	String listidQuery11="select locCompanyName from organization_level2";
				    	List  data = cbt.executeAllSelectQuery(listidQuery11,connectionSpace);
				    	if(data!=null && data.size()>0)
						{
				    		existList11= data.get(0).toString() ;
						}
				    	

						 String templated_id=request.getParameter("template_id"); 
						 String template=request.getParameter("template");
						 String username=null;
						 String listUserName="select userName from useraccount";
					    	List  data1 = cbt.executeAllSelectQuery(listUserName,connectionSpace);  
					    	if(data1!=null && data1.size()>0)
							{
					    		username= data1.get(0).toString() ;
							}
						 String s=template.replace("<","&lt;");
						  k=s.replace(">","&gt;");
						 int maxid=new createTable().getMaxId("CreateTemplate",connectionSpace);
						 String emp_mailId=null;
						 String listOfEmp_mailId=("select emp.emailidone from employee_basic as emp inner join useraccount as uac on emp.useraccountid=uac.id");
					    	List  data12 = cbt.executeAllSelectQuery(listOfEmp_mailId,connectionSpace);
					    	if(data12!=null && data12.size()>0)
							{
					    		emp_mailId= data12.get(0).toString() ;
					    		System.out.println("emp_mailId   "+emp_mailId);
							}
						
						 MailTextForCommunication mailtextobj = new MailTextForCommunication(); 
						 
							String mailtext=mailtextobj.getMailTexttt(templated_id,k,DateUtil.getCurrentDateIndianFormat(),DateUtil.getCurrentTime(),maxid,countryId+"-"+accountID,existList11,username,emp_mailId);
							new MsgMailCommunication().addMail( "vivekanand@dreamsol.biz", "Template", mailtext, "", "Pending", "0", "", "Communication", connectionSpace);
						returnResult = SUCCESS;
					}
				} else {
					addActionMessage("Oops There is some error in data!!!!");
				}
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

		
	@SuppressWarnings(
			{ "rawtypes", "unused" })
			public String addResponse4Employee()
			{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String query1="select template_id,template from createtemplate Where  createtemplate.id='"+getId()+"'";
				List employeeDetails=cbt.executeAllSelectQuery(query1, connectionSpace);
				if(employeeDetails!=null && employeeDetails.size()>0)
				{
					Object[] object=null;
				    for (Iterator iterator = employeeDetails.iterator(); iterator.hasNext();) 
				    {
						object = (Object[]) iterator.next();
						template_id=object[0].toString();
						template=object[1].toString();
					}
			    }
				getResponsePageField();  
				return SUCCESS;
			}
			catch(Exception e)
			{
				log.error("Problem in method addResponse4Employee of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else 
		{
			return LOGIN;
		}

		
			}
			

	public void getResponsePageField()
	{
		try
		  {
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			String accountID=(String)session.get("accountid");
			List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_templates_configuration",accountID, connectionSpace, "", 0, "table_name","templates_configuration");
			responseColumnText=new ArrayList<ConfigurationUtilBean>();
			responseColumnDropdown=new ArrayList<ConfigurationUtilBean>();
			if(requestColumnList!=null && requestColumnList.size()>0)
			{
				ConfigurationUtilBean cub=null;
				for(GridDataPropertyView  gdp:requestColumnList)
				{
				    cub=new ConfigurationUtilBean();
					if(gdp.getColType().trim().equalsIgnoreCase("T") || gdp.getColomnName().equalsIgnoreCase("template_id") || gdp.getColomnName().equalsIgnoreCase("template")
							&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time")&& !gdp.getColomnName().equalsIgnoreCase("operator")&& !gdp.getColomnName().equalsIgnoreCase("Oper_temp_id"))
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
						responseColumnText.add(cub);
					}
					else if(gdp.getColType().equalsIgnoreCase("D") && gdp.getColomnName().equalsIgnoreCase("status"))
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
						responseColumnDropdown.add(cub);
					}
				}
			}
			List operatorStorelist = new ArrayList<String>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			operatorNameListData = new HashMap<Integer, String>();
			List colmName1 = new ArrayList<String>();
			colmName1.add("id");
			colmName1.add("operator");
			operatorStorelist = cbt.viewAllDataEitherSelectOrAllWithOPutUnder(
					"operator_master", colmName1, connectionSpace);
			if (operatorStorelist != null && operatorStorelist.size() > 0) {
				for (Iterator iterator = operatorStorelist.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					operatorNameListData.put((Integer) object[0], object[1]
							.toString());
				}
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Problem in method getResponsePageField of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
public String updateRequest()
{

	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			String accountID=(String)session.get("accountid");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			
				boolean status=false;
				 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				 StringBuilder query=new StringBuilder();
				 query.append("update createtemplate set ");
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
							System.out.println("PARAM NAME :::  "+parmName);
							System.out.println("PARAM VALUE  :::  "+paramValue);
							  if(!parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("template") )
							  {
								  if (parmName.equalsIgnoreCase("template_id")) 
								  {
									  if (paramValue.equalsIgnoreCase("Approve")) 
									  {
										   
										  String temp_idsss=null;
											 String listtemppName="select template_id from createtemplate WHERE id= '"+getId()+"' ";
										    	List  data1 = cbt.executeAllSelectQuery(listtemppName,connectionSpace);  
										    	if(data1!=null && data1.size()>0)
												{
										    		temp_idsss= data1.get(0).toString() ;
												}
										 
										 
										    String temdata = temp_idsss.substring(1);
										    String StoreTempData ="R"+temdata;
										    System.out.println("StoreTempData   " +StoreTempData);
										  query.append(""+parmName+"='"+StoreTempData+"'");
										  
										 
									  }
									  else {
										  query.append(""+parmName+"='"+paramValue+"' ");
									}
									  
								  }
								  else 
								  {
									  query.append(""+parmName+"='"+paramValue+"', ");
								   }
							   
							  }
						}
					}
				 }
				 
				
				 StringBuilder query2=new StringBuilder();
				 query2.append(query.substring(0, query.length()));
				 query2.append(" where id='"+getId()+"'");
				 System.out.println("query query" +query);
				 status=cbt.updateTableColomn(connectionSpace,query2);
				 System.out.println("SDtatus  " +status);					 
				 if(status)
				 {
					 addActionMessage("Data Updated Successfully!!!");
					 returnResult=SUCCESS;
				 }
				 else
				 {
					 addActionMessage("Oops There is some error in data!!!!");
				 }
			
		}
		catch(Exception e)
		{
			returnResult=ERROR;
			log.error("Problem in method updateRequest of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds(),e);
			
			e.printStackTrace();
		}
	}
	
	return returnResult;

	
}

	
	
	
	
		@SuppressWarnings("unchecked")
	public String viewTemplateDataGrid()
	{
		System.out.println("hi........");

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
				query1.append("select count(*) from CreateTemplate where username='"+userName+"'");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("1");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of column
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_templates_configuration", accountID, connectionSpace, "templates_configuration");
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dynamic query based on selected fields
							    Object obdata=(Object)it.next();
							    if(obdata!=null)
							    {
								    if(i<fieldNames.size()-1)
								    	query.append(obdata.toString()+",");
								    else
								    	query.append(obdata.toString());
							    }
							    i++;
						     }
					    }
					query.append(" from CreateTemplate where username='"+userName+"'"+" and flagStatus='Active'");
					System.out.println("query" + query);
					
					if (actionStatus!=null && actionStatus.equalsIgnoreCase("ALL"))
					{
					 
					}
					else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Approve"))
					{
						query.append(" AND status ='Approve' ");
					}
					else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Pending"))
					{
						query.append(" AND status ='Pending' ");
					}
					else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Reject"))
					{
						query.append(" AND status ='Reject' ");
					}
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						//query.append(" where ");
						
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
					
					System.out.println(">>>>>>>>>>>       "+query.toString());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);

					if(data!=null && data.size()>0)
					{	
						viewTemplateData=new ArrayList<Object>();
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if (obdata[k]!=null) 
								{
									if(k==0)
										one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									else
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
							Listhb.add(one);
						}
						setViewTemplateData(Listhb);
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
		
		public String  modifyTemplate()
		{
			System.out.println("MMMMMMMMMMMMMMMMMMMMMM    ::::::   "+template1);
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				String accountID=(String)session.get("accountid");
				String countryId=(String)session.get("countryid");
				 CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (getOper().equalsIgnoreCase("edit")) {
					
					String tempp=null;
					 String listtemppName="select template from createtemplate WHERE id= '"+getId()+"' ";
				    	List  data1 = cbt.executeAllSelectQuery(listtemppName,connectionSpace);  
				    	if(data1!=null && data1.size()>0)
						{
				    		tempp= data1.get(0).toString() ;
						}
					
					boolean status=false;
				    Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("")
								&& Parmname != null
								&& !Parmname.equalsIgnoreCase("")
								&& !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper")
								&& !Parmname.equalsIgnoreCase("rowid"))
							if (Parmname.equalsIgnoreCase("template")) 
							{
								paramValue=paramValue.replace("&lt;", "<");
								paramValue=paramValue.replace("&gt;", ">");
								wherClause.put(Parmname, paramValue);
							}
							else
							{
								wherClause.put(Parmname, paramValue);
							}
							
						System.out.println("PARAM NAME  :::   "+Parmname +">>> PARAM VALUE ::::::   "+paramValue);
					}
					condtnBlock.put("id", getId());
					status= cbt.updateTableColomn("createtemplate", wherClause,
							condtnBlock, connectionSpace);
				 String temp_id=request.getParameter("template_id");
				 String templates=request.getParameter("template");
				// String  operatorName= request.getParameterNames("operator");
				 String operatorName=null;
				 String listoperatorName=("select oprm.operator from operator_master as oprm inner join createtemplate as opr on oprm.id=opr.id");
				
			    	System.out.println("listOfEmp_mailIdbbbbbbbbdddd" +listoperatorName);
			    	List  data12 = cbt.executeAllSelectQuery(listoperatorName,connectionSpace);
			    	System.out.println("dataaaa>>>>");
			    	System.out.println("dataaaa>>>>"+ data12.size());
			    	if(data12!=null && data12.size()>0)
					{
			    		operatorName= data12.get(0).toString() ;
			    		System.out.println("operatorNamevvvv"+operatorName);
					}
				 String  operator_temp_ID= request.getParameter("Oper_temp_id");
				 
				 String emp_mailIdss=null;
				 String listOfEmp_mailId=("select emp.emailidone from employee_basic as emp inner join useraccount as uac on emp.useraccountid=uac.id");
				
			    	System.out.println("listOfEmp_mailIdbbbbbbbbdddd" +listOfEmp_mailId);
			    	List  data13 = cbt.executeAllSelectQuery(listOfEmp_mailId,connectionSpace);
			    	System.out.println("dataaaa>>>>");
			    	System.out.println("dataaaa>>>>"+ data12.size());
			    	if(data13!=null && data13.size()>0)
					{
			    		emp_mailIdss= data13.get(0).toString() ;
			    		System.out.println("emp_mailId   "+emp_mailIdss);
					}

                  String username=null;
				 String listUserName="select userName from useraccount";
			    	List  dataa = cbt.executeAllSelectQuery(listUserName,connectionSpace);  
			    	System.out.println("dataaaa>>>>");
			    	System.out.println("dataaaa>>>>"+ dataa.size());
			    	if(dataa!=null && dataa.size()>0)
					{
			    		username= dataa.get(0).toString() ;
			    		System.out.println("username   "+username);
					}


                 String  existList = null;
		    	String listidQuery11="select locCompanyName from organization_level2";
		    	System.out.println("listidQuery11bbbbbbbb" +listidQuery11);
		    	List  data = cbt.executeAllSelectQuery(listidQuery11,connectionSpace);
		    	System.out.println("dataaaa>>>>");
		    	System.out.println("dataaaa>>>>"+ data.size());
		    	if(data!=null && data.size()>0)
				{
		    		existList= data.get(0).toString() ;
		    		System.out.println("existList   "+existList);
				}				
				
				 if(status)
				 {
					            MailTextForCommunication mailtextobj = new MailTextForCommunication();
								String mailtext1=mailtextobj.getMailText(temp_id,templates,tempp);
								 new MsgMailCommunication().addMail( "vivekanand@dreamsol.biz", "Template Modification", mailtext1, "", "Approve", "0", "", "Communication", connectionSpace);
								 
								 String mailtext2=mailtextobj.getMailText2(temp_id,templates,tempp,operatorName,operator_temp_ID,countryId+"-"+accountID,existList,username,emp_mailIdss);
								 new MsgMailCommunication().addMail( "vivekanand@dreamsol.biz", "Template Modification", mailtext2, "", "Approve", "0", "", "Communication", connectionSpace);
				
				 
				 }
				 
				}
				
				
				else if (getOper().equalsIgnoreCase("del"))
				{
					System.out.println(">>>>>>>>");
					
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds) {
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					StringBuilder query=new StringBuilder();
					query.append("UPDATE createtemplate SET flagStatus='InActive' WHERE id IN("+condtIds+")");
					cbt.updateTableColomn(connectionSpace,query);
					query.setLength(0);
					
					query.append("UPDATE createtemplate SET flagStatus='InActive' WHERE template_id IN("+condtIds+")");
					cbt.updateTableColomn(connectionSpace,query);
				}
			} 
			catch (Exception e) {
				
				e.printStackTrace();
				addActionError("Oops There is some error in data!");
				return ERROR;
			}
			return SUCCESS;
		}
		
		
		
		
		
		
	
		public String beforeOperatorView() 
		{
			System.out.println("   xxxxxxxxxxxxxxxxxxx");
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				setbeforeOperatorView();

			} catch (Exception e) {

				e.printStackTrace();
			}
			return SUCCESS;
		}
		private void setbeforeOperatorView() 
		{
			try {
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewOperatorGrid.add(gpv);

				List<GridDataPropertyView> msg = Configuration
						.getConfigurationData("mapped_operator_master",
								accountID, connectionSpace, "", 0, "table_name",
								"operator_configuration");
				for (GridDataPropertyView gdp : msg) {

					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						gpv.setAlign(gdp.getAlign());
						viewOperatorGrid.add(gpv);
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public String addOperatorData() {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setaddOperatorData();
			} catch (Exception e) {

				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
			}

			return SUCCESS;

		}

		private void setaddOperatorData() {
			try {
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				operatorname = new ArrayList<ConfigurationUtilBean>();
				templateName = new ArrayList<ConfigurationUtilBean>();
				
				List<GridDataPropertyView> msg = Configuration
						.getConfigurationData("mapped_operator_master",
								accountID, connectionSpace, "", 0, "table_name",
								"operator_configuration");
				if (msg != null) {
					System.out.println("svsvsvsv");
					for (GridDataPropertyView gdp : msg) {
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("userName")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}

							operatorname.add(obj);
						}  
						
						
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			}

		public String inserOperatorData() {
			System.out.println("vvvvv");
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {
				try {
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					String accountID = (String) session.get("accountid");
				   CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<GridDataPropertyView> msg = Configuration
					.getConfigurationData("mapped_operator_master",accountID, connectionSpace, "", 0, "table_name","operator_configuration");
					if (msg != null && msg.size() > 0) {
						// create table query based on configuration
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						List<TableColumes> tableColume = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : msg) 
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						}
						
						
						System.out.println("adasda");
						cbt.createTable22("Operator_master", tableColume,connectionSpace);
						System.out.println("createee table");	
						
						System.out.println("request:"+request);
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						if (requestParameterNames != null && requestParameterNames.size() > 0) {
								
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext()) {
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								System.out.println("parmName:"+parmName);
								System.out.println("paramvaluee===="+paramValue);
								if (paramValue != null && !paramValue.equalsIgnoreCase(""))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
							}
						}
						
						
						
						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateIndianFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						
						
						System.out.println("insertData:" + insertData.size());
						status=cbt.insertIntoTable("Operator_master", insertData, connectionSpace);
						if (status) {
							addActionMessage("Data Save Successfully!!!");
							returnResult = SUCCESS;
						}
					} else {
						addActionMessage("Oops There is some error in data!!!!");
					}
				} catch (Exception e) {
					returnResult = ERROR;
					e.printStackTrace();
				}
			} else {
				returnResult = LOGIN;
			}
			return returnResult;
		}
		
		@SuppressWarnings("unchecked")
		public String viewOperDataGrid()
		{
			System.out.println("hi........");

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
					query1.append("select count(*) from Operator_master");
					List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
					if(dataCount!=null&&dataCount.size()>0)
					{
						BigInteger count=new BigInteger("1");
						for(Iterator it=dataCount.iterator(); it.hasNext();)
						{
							 Object obdata=(Object)it.next();
							 count=(BigInteger)obdata;
						}
						setRecords(count.intValue());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
						//getting the list of column
						StringBuilder query=new StringBuilder("");
						query.append("select ");
						List fieldNames=Configuration.getColomnList("mapped_operator_master", accountID, connectionSpace, "operator_configuration");
						int i=0;
						if(fieldNames!=null && fieldNames.size()>0)
						{
							for(Iterator it=fieldNames.iterator(); it.hasNext();)
							{
								//generating the dynamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(i<fieldNames.size()-1)
									    	query.append(obdata.toString()+",");
									    else
									    	query.append(obdata.toString());
								    }
								    i++;
							     }
						    }
						query.append(" from Operator_master");
						System.out.println("query" + query);
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");
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
						query.append(" limit "+from+","+to);
						System.out.println(">>>>>>>>>>>"+query.toString());
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						 
						if(data!=null && data.size()>0)
						{	
							viewOperData=new ArrayList<Object>();
							List<Object> Listhb=new ArrayList<Object>();
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) 
								{
									if (obdata[k]!=null) 
									{
										if(k==0)
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
										else
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
								Listhb.add(one);
							}
							setViewOperData(Listhb);
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
	   
		public String viewtemplatemsgwrite(){
			String returnString=ERROR;
			CommonforClassdata obhj = new CommonOperAtion();
			TemplateUtilBean beanobj=null;
			try{
				StringBuilder stringObjt=new StringBuilder("");
				List<TemplateUtilBean> temllistdata = new ArrayList<TemplateUtilBean>();
				stringObjt.append("select ");
				stringObjt.append("id" +",");
				stringObjt.append("template_id"+",");
				stringObjt.append("template");
				stringObjt.append(" from CreateTemplate where status='Approve'");
				List  listdata=obhj.executeAllSelectQuery(stringObjt.toString(),connectionSpace);
				for (Iterator iterator = listdata.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					beanobj=new TemplateUtilBean();
					if(object[0].toString()!=null){
					 beanobj.setId(object[0].toString());
					}if(object[1].toString()!=null){
					 beanobj.setTemplate_id(object[1].toString());}
					 else{beanobj.setTemplate_id(object[1].toString());}
					 if(object[2].toString()!=null){
					 beanobj.setTemplate(object[2].toString());
					}else{beanobj.setTemplate(object[2].toString());}
					 temllistdata.add(beanobj);
				}
				setViewtemplateContents(temllistdata);
				returnString=SUCCESS;
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return returnString;
		}
	
		
		
		

	
	
		@SuppressWarnings("unchecked")
		public String templateSubmission()
		{
			System.out.println("***********************************"+getId());
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {
				try {
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					String accountID = (String) session.get("accountid");
				   CommonOperInterface cbt = new CommonConFactory().createInterface();
				   
				   String listForTemp="select template from createtemplate WHERE id= '"+getId()+"' "+" and username='"+userName+"'";
				    	List  data = cbt.executeAllSelectQuery(listForTemp,connectionSpace);  
				    	if(data!=null && data.size()>0)
						{
				    		StoredTemp= data.get(0).toString() ;
						
						// get pattern from valid String.
						Pattern pattern = Pattern.compile("<.+?>", Pattern.CASE_INSENSITIVE);
						// matching String from matcher
					    Matcher matcher = pattern.matcher(StoredTemp);
					    int temp=0;
					    int endIndex=0;
					    tempList=new ArrayList<ConfigurationUtilBean>();
					    ConfigurationUtilBean ob=null;
					    ConfigurationUtilBean ob1=null;
					   
					    while(matcher.find())
					    {
					    	setChekedString("textfeild");
					    	ob=new ConfigurationUtilBean();
					    	ob1=new ConfigurationUtilBean();
					    	
					    	String str=StoredTemp.substring(temp, matcher.start());
					    	String strTemp=StoredTemp.substring(matcher.start(), matcher.end());
					    	temp=matcher.end();
					    	endIndex=matcher.end();
					    	if(str!=null)
					    	{
					    		ob.setKey(str);
					    		tempList.add(ob);
					    	}
					    	if(strTemp!=null)
					    	{
					    		
					    		ob1.setDefault_value(strTemp.substring(strTemp.indexOf("(")+1, strTemp.indexOf(")")));
					    		ob1.setKey("textfield");
					    		tempList.add(ob1);
					    	}
						
					    }
					    ob=new ConfigurationUtilBean();
					    ob.setKey(StoredTemp.substring(endIndex,StoredTemp.length()));
					    tempList.add(ob);
						}
				    	returnResult=SUCCESS;
				    	
				} catch (Exception e) {
					returnResult = ERROR;
					e.printStackTrace();
				}
			} else {
				returnResult = LOGIN;
			}
			System.out.println(">>>>>>"+returnResult);
			return returnResult;
		
			
			
		}
	
		
		public String insertUseTeplate()
		{

			System.out.println("vvvvv");
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {
				try {
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					String accountID = (String) session.get("accountid");
				   CommonOperInterface cbt = new CommonConFactory().createInterface();
					
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						List<TableColumes> tableColume = new ArrayList<TableColumes>();
						TableColumes ob1 = null;
						
							ob1 = new TableColumes();
							ob1.setColumnname("Temp_Id");
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
							
							
							ob1 = new TableColumes();
							ob1.setColumnname("UserTemplate");
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						
						
						
						cbt.createTable22("CreateUseTemplate", tableColume,connectionSpace);
						
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						StringBuilder templateBuffer=new StringBuilder();
						String tempID=null;
						String templatedata=null;
						
						if (requestParameterNames != null && requestParameterNames.size() > 0) 
						{
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext()) 
							{
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								System.out.println("parmName:"+parmName);
								System.out.println("paramvaluee===="+paramValue);
								if (paramValue != null && !paramValue.equalsIgnoreCase(""))
								{
									if (parmName.equalsIgnoreCase("tempId")) 
									{
										tempID=paramValue;
									}
									else if(parmName.equalsIgnoreCase("templateData"))
									{
										templatedata=paramValue;
										System.out.println("templatedata::::"+templatedata);
									}
									else if(parmName.equalsIgnoreCase("lengthrange"))
									{
										lengthOfTextfield=paramValue;
										System.out.println("templatedata::::"+templatedata);
									}
									else 
									{
										 templateBuffer.append(paramValue+"#");
									}
									
								}
							}
						}
						Pattern pattern = Pattern.compile("<.+?>", Pattern.CASE_INSENSITIVE);
					    Matcher matcher = pattern.matcher(templatedata);
					   StringBuilder test =new StringBuilder();
					    int temp=0;
					    int endIndex=0;
					    List tempList=new ArrayList();
					    String splitTemp[]=templateBuffer.toString().split("#");
					    int i=0;
					    while(matcher.find())
					    {

					    	String str=templatedata.substring(temp, matcher.start());
					    	String strTemp=templatedata.substring(matcher.start(), matcher.end());
					    	System.out.println(str+" >>>> "+strTemp);
					    	System.out.println(matcher.start());
					    	System.out.println(matcher.end());
					    	temp=matcher.end();
					    	endIndex=matcher.end();
					    	if(str!=null)
					    	{
					    		test.append(str);
					    	}
					    	if(strTemp!=null)
					    	{
					    		test.append(splitTemp[i]);
					    	}
						i++;
					}
					    test.append(templatedata.substring(endIndex,templatedata.length()));
						System.out.println(">>>>>>>  Final template is as <>><><>   "+test.toString());
						
						ob = new InsertDataTable();
						ob.setColName("Temp_Id");
						ob.setDataName(tempID);
						insertData.add(ob);
						
						System.out.println("temp buffer ::::  "+templateBuffer);
						ob = new InsertDataTable();
						ob.setColName("UserTemplate");
						ob.setDataName(test.toString());
						insertData.add(ob);
						
						System.out.println("insertData:" + insertData.size());
						status=cbt.insertIntoTable("CreateUseTemplate", insertData, connectionSpace);
						if (status) {
							addActionMessage("Data Save Successfully!!!");
							returnResult = SUCCESS;
						}
					 else {
						addActionMessage("Oops There is some error in data!!!!");
					}
				} catch (Exception e) {
					returnResult = ERROR;
					e.printStackTrace();
				}
			} else {
				returnResult = LOGIN;
			}
			return returnResult;
		
			
		}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public List<GridDataPropertyView> getViewTemplateGrid() {
		return viewTemplateGrid;
	}
	public void setViewTemplateGrid(List<GridDataPropertyView> viewTemplateGrid) {
		this.viewTemplateGrid = viewTemplateGrid;
	}
	public List<ConfigurationUtilBean> getTemplateName() {
		return templateName;
	}
	public void setTemplateName(List<ConfigurationUtilBean> templateName) {
		this.templateName = templateName;
	}
	public List<ConfigurationUtilBean> getTemplateID() {
		return templateID;
	}
	public void setTemplateID(List<ConfigurationUtilBean> templateID) {
		this.templateID = templateID;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Object> getViewTemplateData() {
		return viewTemplateData;
	}
	public void setViewTemplateData(List<Object> viewTemplateData) {
		this.viewTemplateData = viewTemplateData;
	}
	public String getTemplateserise() {
		return templateserise;
	}
	public void setTemplateserise(String templateserise) {
		this.templateserise = templateserise;
	}

	public String getTemplateNumber() {
		return templateNumber;
	}
	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getRemLen() {
		return remLen;
	}
	public void setRemLen(String remLen) {
		this.remLen = remLen;
	}
	public List<GridDataPropertyView> getViewOperatorGrid() {
		return viewOperatorGrid;
	}
	public void setViewOperatorGrid(List<GridDataPropertyView> viewOperatorGrid) {
		this.viewOperatorGrid = viewOperatorGrid;
	}
	public List<ConfigurationUtilBean> getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(List<ConfigurationUtilBean> operatorname) {
		this.operatorname = operatorname;
	}
	public List<Object> getViewOperData() {
		return viewOperData;
	}
	public void setViewOperData(List<Object> viewOperData) {
		this.viewOperData = viewOperData;
	}
	public Map<String, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	public List<ConfigurationUtilBean> getResponseColumnText() {
		return responseColumnText;
	}
	public void setResponseColumnText(List<ConfigurationUtilBean> responseColumnText) {
		this.responseColumnText = responseColumnText;
	}
	public List<ConfigurationUtilBean> getResponseColumnDropdown() {
		return responseColumnDropdown;
	}
	public void setResponseColumnDropdown(
			List<ConfigurationUtilBean> responseColumnDropdown) {
		this.responseColumnDropdown = responseColumnDropdown;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<Integer, String> getOperatorNameListData() {
		return operatorNameListData;
	}
	public void setOperatorNameListData(Map<Integer, String> operatorNameListData) {
		this.operatorNameListData = operatorNameListData;
	}
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getTemplate1() {
		return template1;
	}
	public void setTemplate1(String template1) {
		this.template1 = template1;
	}
	public String getStoredTemp() {
		return StoredTemp;
	}
	public void setStoredTemp(String storedTemp) {
		StoredTemp = storedTemp;
	}
	public List<ConfigurationUtilBean> getTempList() {
		return tempList;
	}
	public void setTempList(List<ConfigurationUtilBean> tempList) {
		this.tempList = tempList;
	}
	public String getLengthOfTextfield() {
		return lengthOfTextfield;
	}
	public void setLengthOfTextfield(String lengthOfTextfield) {
		this.lengthOfTextfield = lengthOfTextfield;
	}
	public List<TemplateUtilBean> getViewtemplateContents() {
		return viewtemplateContents;
	}
	public void setViewtemplateContents(List<TemplateUtilBean> viewtemplateContents) {
		this.viewtemplateContents = viewtemplateContents;
	}
	public String getTemplate_type() {
		return template_type;
	}
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	public String getChekedString() {
		return chekedString;
	}
	public void setChekedString(String chekedString) {
		this.chekedString = chekedString;
	}
	
	

}