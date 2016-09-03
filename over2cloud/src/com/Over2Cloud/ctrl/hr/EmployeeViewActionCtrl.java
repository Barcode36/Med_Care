/**
 * @author Rahul Srivastava 8010717140
 * This class of code is written for managing all the view work of employee related and modification of data
 * 
 */

package com.Over2Cloud.ctrl.hr;

import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.departmen.DeptViewAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EmployeeViewActionCtrl extends ActionSupport implements ServletRequestAware{
	
	static final Log log = LogFactory.getLog(EmployeeViewActionCtrl.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	String encryptedID=(String)session.get("encryptedID");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	/**
	 * Properties uses
	 * employeeGridColomns-->For Setting the grid colomn dynamically based on selected field
	 * headerName--> for setting the name of the grid header and page header based on the action
	 * editDeptData--> Flag value either true or false for setting a grid modification able or not
	 * empDetail--> Emp basic pojo bean
	 * empOtherDataView--> Employee all other data pojo bean like exprience, qualification, personal, bank etc....
	 */
	private String empModuleFalgForDeptSubDept;
	private List<GridDataPropertyView>employeeGridColomns=new ArrayList<GridDataPropertyView>();
	private String headerName;
	private String editDeptData;
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
	private int id;
	private List<Object>  empDetail;
	private String empName;
	private String comName;
	private String mobTwo;
	private String emailIdOne;
	private String emailIdTwo;
	private String salary;
	private String cContactNo;
	private String address;
	private String landmark;
	private String city;
	private String cpincode;
	private String moduleFlag;
	private List<Object> empOtherDataView;
	private String dept;
	private String subDept;
	private int gridWidth;
	private String gridId;
	private String designation;
	private String desigID;
	private String makeHistory;
	private List<GridDataPropertyView>viewPropertiesColomnName=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> masterViewList;
	private Map<String, String>						empPersonalFullViewMap;
	private Map<String, String>						empBankFullViewMap;
	private String record;
	private List<EmpBasicPojoBean> empProfessionalViewMap; 
	private Map<String, String>	 empBasicFullViewMap;
	private Map<String, String>	 empworkExpFullViewMap;
	private String fetchEmpName;
	Map<String,String> attachFileMap=new LinkedHashMap<String,String>();
	private InputStream fileInputStream;
	private String fileName;
	private String documentName;
	private Map<String,String> checkStatus;
	private String statusCheck;
	private Map<Integer, String> deptMap;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String fieldser;
	private String fieldval;
	private String searchFields;
	private String SearchValue;
	private String toatlcount;
	private String activecount;
	private String inActivecount;
	
	/**
	 * EMployee other view work starts from here 
	 */
	public String employeeView()
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
				log
						.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class "
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

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name","common_contact_configuration");
		    List<GridDataPropertyView> emp = Configuration.getConfigurationData("mapped_emp_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "emp_contact_configuration");
			gpv=new GridDataPropertyView();
			gpv.setColomnName("takeaction");
			gpv.setHeadingName("View");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("employeeFormatter");
			masterViewProp.add(gpv);
			for (GridDataPropertyView gdp : returnResult)
			{
				
			if(!gdp.getColomnName().equalsIgnoreCase("groupId"))
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
				//System.out.println("setColomnName" +gdp.getColomnName());
			}
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactViewProp of class " + getClass(), e);
		}
	}
	@SuppressWarnings("unchecked")
	public String empFullView()
	{
		String ret = LOGIN;
		if (ValidateSession.checkSession())
		{
			
			try
			{
				if (record!=null && !record.equalsIgnoreCase("") && record.equalsIgnoreCase("1")) 
				{
					
					ComplianceUniversalAction CU=new ComplianceUniversalAction();
					String loggedDetails[]=CU.getEmpDataByUserName(userName);
					String EmpName[]=CU.getEmpDataByUserName(userName);
					 fetchEmpName=EmpName[0];
					id = Integer.parseInt(loggedDetails[5]);
					mobTwo = loggedDetails[1];
					System.out.println("fetchEmpName ::::  "+fetchEmpName);
					
				}
				empBasicFullViewMap=new LinkedHashMap<String, String>();
				empPersonalFullViewMap=new LinkedHashMap<String, String>();
				empBankFullViewMap=new LinkedHashMap<String, String>();
				empProfessionalViewMap=new ArrayList<EmpBasicPojoBean>();
				empworkExpFullViewMap=new LinkedHashMap<String, String>();
				EmpHelper eh=new EmpHelper();
				//System.out.println("ID ::::  "+id);
				//System.out.println("mobTwo ::::  "+mobTwo);
				
				Map<String,String> basicRecordMap= eh.empBasicFullView(connectionSpace, id, mobTwo, accountID);
				if (basicRecordMap!=null) 
				{
					empBasicFullViewMap =basicRecordMap;
				}
				
				Map<String,String> personaMap= eh.empPersonalFullView(connectionSpace, id, mobTwo, accountID);
				if (personaMap!=null) 
				{
					empPersonalFullViewMap =personaMap;
				}
				Map<String,String> bankMap= eh.empBankFullView(connectionSpace, id, mobTwo, accountID);
				if (bankMap!=null) 
				{
					empBankFullViewMap =bankMap;
				}
				List<EmpBasicPojoBean> proffMap = eh.empProfessionalFullView(connectionSpace, id, mobTwo, accountID);
				if (proffMap!=null)
				{
					empProfessionalViewMap =proffMap;
				}
				Map<String,String> workExpMap= eh.empWorkEmpFullView(connectionSpace, id, mobTwo, accountID);
				if (workExpMap!=null) 
				{
					empworkExpFullViewMap =workExpMap;
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
	
	
	 public String getAttachmentdownload() 
	   {
			String ret = LOGIN;
			if (ValidateSession.checkSession())
			{
				
				try
				{
					
					
					empProfessionalViewMap=new ArrayList<EmpBasicPojoBean>();;
					EmpHelper eh=new EmpHelper();
					//System.out.println("ID ::::  "+id);
					//System.out.println("mobTwo ::::  "+mobTwo);
					
					List<EmpBasicPojoBean> proffMap = eh.empProfessionalDocumentView(connectionSpace, mobTwo, accountID);
					if (proffMap!=null)
					{
						empProfessionalViewMap =proffMap;
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
	 
	 
	 public String downloadBankDetails() 
	 {
			//System.out.println(">>>>");
			
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					System.out.println("FILE NAME  :::  "+fileName);
					fileName = new CreateFolderOs().createUserDir("Common_Datatoday")+ "//"+fileName;
					//System.out.println("FILE NAME  :::  "+fileName);
					File file=new File(fileName);
		            if(file!=null && file.exists())
		            {
		            	fileInputStream = new FileInputStream(file);
		            	fileName = file.getName();
		            	return "download";
		            }
		            else
		            {
		                 addActionError("File dose not exist");
		                 return ERROR;
		            }
				}
				catch(Exception e)
				{
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
							"Problem in Over2Cloud  method download of class "+getClass(), e);
					e.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
	 
	 public String download() 
		{
			System.out.println(">>>>");
			
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					//System.out.println("FILE NAME  :::  "+fileName);
					fileName = new CreateFolderOs().createUserDir("empDocs")+ "//"+fileName;
					//System.out.println("FILE NAME  :::  "+fileName);
					File file=new File(fileName);
		            if(file!=null && file.exists())
		            {
		            	fileInputStream = new FileInputStream(file);
		            	fileName = file.getName();
		            	return "download";
		            }
		            else
		            {
		                 addActionError("File dose not exist");
		                 return ERROR;
		            }
				}
				catch(Exception e)
				{
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
							"Problem in Over2Cloud  method download of class "+getClass(), e);
					e.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
		
	 public String basicEmpDocdownload() 
	   {
			String ret = LOGIN;
			if (ValidateSession.checkSession())
			{
				
				try
				{
					
					
					empBasicFullViewMap=new LinkedHashMap<String, String>();
					EmpHelper eh=new EmpHelper();
					//System.out.println("ID ::::  "+id);
					//System.out.println("mobTwo ::::  "+mobTwo);
					
					Map<String,String> basicEmpMap=  eh.empBasicDocumentView(connectionSpace, mobTwo, accountID);
					if (basicEmpMap!=null) 
					{
						System.out.println("bankMap >>>>>>"  +basicEmpMap);
						empBasicFullViewMap.putAll(basicEmpMap);
						empBasicFullViewMap =basicEmpMap;
						//System.out.println("empBasicFullViewMap >>>>>>"  +empBasicFullViewMap.size());
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
	 
	 public String bankDocdownload() 
	   {
			String ret = LOGIN;
			if (ValidateSession.checkSession())
			{
				
				try
				{
					
					
					empBankFullViewMap=new LinkedHashMap<String, String>();
					EmpHelper eh=new EmpHelper();
					//System.out.println("ID ::::  "+id);
					//System.out.println("mobTwo ::::  "+mobTwo);
					
					Map<String,String> bankMap=  eh.empBankDocumentView(connectionSpace, mobTwo, accountID);
					if (bankMap!=null) 
					{
						//System.out.println("bankMap >>>>>>"  +bankMap);
						empBankFullViewMap.putAll(bankMap);
						empBankFullViewMap =bankMap;
						//System.out.println("empBankFullViewMap >>>>>>"  +empBankFullViewMap.size());
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
	 
	 
	 public String empPerDocdownload() 
	   {
			String ret = LOGIN;
			if (ValidateSession.checkSession())
			{
				
				try
				{
					
					
					empPersonalFullViewMap=new LinkedHashMap<String, String>();
					EmpHelper eh=new EmpHelper();
					//System.out.println("ID ::::  "+id);
					//System.out.println("mobTwo ::::  "+mobTwo);
					
					Map<String,String> EmpperMap=  eh.empPerDocumentView(connectionSpace, mobTwo, accountID);
					if (EmpperMap!=null) 
					{
						//System.out.println("bankMap >>>>>>"  +EmpperMap);
						empPersonalFullViewMap.putAll(EmpperMap);
						empPersonalFullViewMap =EmpperMap;
						//System.out.println("empPersonalFullViewMap >>>>>>"  +empPersonalFullViewMap.size());
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
	 
	 
	 public String downloadEmpPerDetails() 
		{
			//System.out.println(">>>>");
			
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
				//	System.out.println("FILE NAME  :::  "+fileName);
					fileName = new CreateFolderOs().createUserDir("Common_DataEmpPer")+ "//"+fileName;
				//	System.out.println("FILE NAME  :::  "+fileName);
					File file=new File(fileName);
		            if(file!=null && file.exists())
		            {
		            	fileInputStream = new FileInputStream(file);
		            	fileName = file.getName();
		            	return "download";
		            }
		            else
		            {
		                 addActionError("File dose not exist");
		                 return ERROR;
		            }
				}
				catch(Exception e)
				{
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
							"Problem in Over2Cloud  method download of class "+getClass(), e);
					e.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
	 
	 
	 
	 public String empWorkExpDocdownload() 
	   {
			String ret = LOGIN;
			if (ValidateSession.checkSession())
			{
				
				try
				{
					
					
					empworkExpFullViewMap=new LinkedHashMap<String, String>();
					EmpHelper eh=new EmpHelper();
					//System.out.println("ID ::::  "+id);
					//System.out.println("mobTwo ::::  "+mobTwo);
					
					Map<String,String> workExpMap=  eh.empworkExpDocumentView(connectionSpace, mobTwo, accountID);
					if (workExpMap!=null) 
					{
						//System.out.println("bankMap >>>>>>"  +workExpMap);
						empworkExpFullViewMap.putAll(workExpMap);
						empworkExpFullViewMap =workExpMap;
						//System.out.println("empworkExpFullViewMap >>>>>>"  +empworkExpFullViewMap.size());
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
	 
	 
	 public String downloadWorkExpDetails() 
		{
			//System.out.println(">>>>");
			
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					//System.out.println("FILE NAME  :::  "+fileName);
					fileName = new CreateFolderOs().createUserDir("Common_DataForEmp")+ "//"+fileName;
					//System.out.println("FILE NAME  :::  "+fileName);
					File file=new File(fileName);
		            if(file!=null && file.exists())
		            {
		            	fileInputStream = new FileInputStream(file);
		            	fileName = file.getName();
		            	return "download";
		            }
		            else
		            {
		                 addActionError("File dose not exist");
		                 return ERROR;
		            }
				}
				catch(Exception e)
				{
					log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
							"Problem in Over2Cloud  method download of class "+getClass(), e);
					e.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
	
	public String beforeEmployeeView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Employee >> View";
			setEditDeptData("false");
			setGridColomnNames();
			int level=setDepartmentAndSubDeptNames();
			setEmpModuleFalgForDeptSubDept(Integer.toString(level));
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeEmployeeView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeEmployeeModify()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Employee >> Modify";
			setEditDeptData("true");
			setGridColomnNames();
			int level=setDepartmentAndSubDeptNames();
			setEmpModuleFalgForDeptSubDept(Integer.toString(level));
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeEmployeeModify of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeOpenPersonalRecordsView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Employee Personal";
			setId(getId());
			setEditDeptData(getEditDeptData().trim());
			setGridColomnForPersonal();
			setModuleFlag(getModuleFlag().trim());
			gridWidth=2000;
			setViewPropertiesColomnName(getViewPropertiesColomnName());
			gridId="grid1Main";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeOpenPersonalRecordsView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeQualificationRecord()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Employee Qualification";
			setEditDeptData(getEditDeptData().trim());
			setId(getId());
			setGridColomnForQualification();
			setModuleFlag(getModuleFlag().trim());
			setViewPropertiesColomnName(getViewPropertiesColomnName());
			gridWidth=900;
			gridId="grid2Main";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeQualificationRecord of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeWorkExprienceView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Employee Work Exprience";
			setEditDeptData(getEditDeptData().trim());
			setId(getId());
			setGridColomnForWorkExprience();
			setModuleFlag(getModuleFlag().trim());
			gridWidth=1200;
			setViewPropertiesColomnName(getViewPropertiesColomnName());
			gridId="grid3Main";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeWorkExprienceView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	public String employeeViewInGrid()
	{ 
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
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
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
				}
				StringBuilder query = new StringBuilder(" select ");
				List fieldNames = Configuration.getColomnList("mapped_common_contact_configuration", accountID, connectionSpace, "common_contact_configuration");

				List fieldNamesEmp = Configuration.getColomnList("mapped_emp_contact_configuration", accountID, connectionSpace, "emp_contact_configuration");

				

				List<Object> listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
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
								query.append("dpt.deptname,");
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
								query.append("dpt.deptname");
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

				
				

				 query.append("  from employee_basic as contct" +
		                	" inner join groupinfo as grp on grp.id= contct.groupId " +
		                	" inner join department as dpt on dpt.id=contct.deptname " +
		                	"  Where grp.groupName='Employee' ");
				// System.out.println("query :::::"  +query);
				 if(getStatusCheck()!=null && !getStatusCheck().equalsIgnoreCase("-1"))
                 {
					 query.append(" and  contct.flag = "+getStatusCheck()+"");
                 }
				 if(getSearchFields()!=null && !getSearchFields().equalsIgnoreCase("-1") && getSearchValue()!=null && !getSearchValue().equalsIgnoreCase("-1") )
				 {
					 query.append(" and contct."+getSearchFields()+"='"+getSearchValue()+"' ");
				 }
				 if(getFieldser()!=null && !getFieldser().equalsIgnoreCase("-1") && getFieldval()!=null && !getFieldval().equalsIgnoreCase("-1"))
                 {
					 query.append(" and contct. " + getFieldser() + " like '%" + getFieldval() + "%'");
					
                 }
				 //System.out.println("SEARCHhhhhhhhhhhhhhhhhh  ::::::: " +query);
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
				List indList = new ArrayList();
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					List<String> officeList = new DeptViewAction().getAllOffices();

					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								}
								else
								{
									if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("regLevel"))
									{
										if (obdata[k].toString().equalsIgnoreCase("1") && officeList.get(0) != null)
										{
											one.put(fieldNames.get(k).toString(), (officeList.get(0)));
										}
										else if (obdata[k].toString().equalsIgnoreCase("2") && officeList.get(1) != null)
										{
											one.put(fieldNames.get(k).toString(), (officeList.get(1)));
										}
										else if (obdata[k].toString().equalsIgnoreCase("3") && officeList.get(2) != null)
										{
											one.put(fieldNames.get(k).toString(), (officeList.get(2)));
										}
										else if (obdata[k].toString().equalsIgnoreCase("4") && officeList.get(3) != null)
										{
											one.put(fieldNames.get(k).toString(), (officeList.get(3)));
										}
										else if (obdata[k].toString().equalsIgnoreCase("5") && officeList.get(4) != null)
										{
											one.put(fieldNames.get(k).toString(), (officeList.get(4)));
										}
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("industry"))
									{
										indList = new createTable().executeAllSelectQuery("select industry from industrydatalevel1 where id='" + obdata[k].toString() + "'", connectionSpace);
										if (indList != null && indList.size() > 0 && indList.get(0) != null)
										{
											one.put(fieldNames.get(k).toString(), indList.get(0));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}

									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("subIndustry"))
									{
										indList = new createTable().executeAllSelectQuery("select subIndustry from subindustrydatalevel2 where id='" + obdata[k].toString() + "'", connectionSpace);
										if (indList != null && indList.size() > 0 && indList.get(0) != null)
										{
											one.put(fieldNames.get(k).toString(), indList.get(0));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}

									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("dob") || fieldNames.get(k).toString().equalsIgnoreCase("doa")
											|| fieldNames.get(k).toString().equalsIgnoreCase("doj") || fieldNames.get(k).toString().equalsIgnoreCase("createdDate")
											|| fieldNames.get(k).toString().equalsIgnoreCase("deactiveOn"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("empLogo"))
									{
										one.put(fieldNames.get(k).toString(), "View Image");
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("flag"))
									{
										if (obdata[k].toString() != null && obdata[k].toString().equalsIgnoreCase("0"))
										{
											one.put(fieldNames.get(k).toString(), "Active");
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "In-Active");
										}
									}
									else
									{
										if (obdata[k].toString() != null)
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}
									}
								}
							}
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
				log
						.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method contactViewInGrid of class "
								+ getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	public String beforeBankAccountDetails()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Employee Bank Account";
			setEditDeptData(getEditDeptData().trim());
			setId(getId());
			setGridColomnBankAccountDetails();
			setModuleFlag(getModuleFlag().trim());
			gridWidth=830;
			setViewPropertiesColomnName(getViewPropertiesColomnName());
			gridId="grid4Main";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeBankAccountDetails of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	public String employeeGridDataView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setEditDeptData(getEditDeptData());
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			
			String sql_query2 = " select employee_basic.subdept from employee_basic INNER JOIN useraccount on useraccount.id=employee_basic.useraccountid where useraccount.userID='"+encryptedID+"'";
			List listData2 = cbt.executeAllSelectQuery(sql_query2, connectionSpace);
			
			
			String sql_query3 = " select deptid from subdepartment where id='"+listData2.get(0)+"' ";
			List listData3 = cbt.executeAllSelectQuery(sql_query3, connectionSpace);
			
			
			String deptLevel=session.get("deptLevel").toString();
			String temp[]=deptLevel.split(",");
			deptLevel=temp[0];
			StringBuilder query1=new StringBuilder("");
			//query1.append("select count(*) from employee_basic where subdept in(select id from subdepartment where deptid='"+listData3.get(0)+"')");
			if(makeHistory.equalsIgnoreCase("0")){
			query1.append("select count(*) from employee_basic where subdept in(select id from subdepartment) and status='0'");
			}
			else{
				query1.append("select count(*) from employee_basic where subdept in(select id from subdepartment)and status='1'");
			}
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null)
			{
				BigInteger count=new BigInteger("3");
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
				
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				//query.append("select ");
				query.append("select dp.deptname, ");
				List<String> fieldNames=Configuration.getColomnList("mapped_employee_basic_configuration", accountID, connectionSpace, "employee_basic_configuration");
				//fieldNames.add("deptname");
				fieldNames.add(0, "deptname");
				fieldNames.add("levelofhierarchy");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
					    	if(deptLevel.equalsIgnoreCase("2") && obdata.toString().equalsIgnoreCase("subdept"))
					    	{
							    if(i<fieldNames.size()-1)
							    	query.append("subdept.subdeptname,");
							    else
							    	query.append("subdept.subdeptname,");
					    	}
					    	else if(deptLevel.equalsIgnoreCase("1") && obdata.toString().equalsIgnoreCase("subdept"))
					    	{
							    if(i<fieldNames.size()-1)
							    	query.append("dept.deptname,");
							    else
							    	query.append("dept.deptname,");
					    	}
					    	else if(obdata.toString().equalsIgnoreCase("designation"))
					    	{
							    if(i<fieldNames.size()-1)
							    {
							    	query.append("desg.designationname,");
							    }
							    else
							    {
							    	query.append("desg.designationname,");
							    }
					    	}
					    	else if(!obdata.toString().equalsIgnoreCase("subdept") && !obdata.toString().equalsIgnoreCase("deptname") && !obdata.toString().equalsIgnoreCase("levelofhierarchy"))
					    	{
					    		if(i<fieldNames.size()-1)
							    	query.append("emp."+obdata.toString()+",");
							    else
							    	query.append("emp."+obdata.toString()+",");
					    	}
					    	else if(obdata.toString().equalsIgnoreCase("levelofhierarchy"))
					    	{
					    		query.append("desg.levelofhierarchy");
					    	}
					    	
					    }
					    i++;
					
				}
				
				
					
				if(deptLevel.equalsIgnoreCase("1"))
				{
					query.append(" from employee_basic as emp left join department as dept  " +
							"on dept.id=emp.dept left join designation as desg on emp.designation=desg.id");
				}
				else if(deptLevel.equalsIgnoreCase("2"))
				{
					query.append(" from employee_basic as emp left join subdepartment " +
							"as subdept on subdept.id=emp.subdept left join designation as desg  on emp.designation=desg.id");
				}
				//query.append(" left join department as dp on dp.id=subdept.deptid  where  subdept in(select id from subdepartment where deptid='"+listData3.get(0)+"') ");
				query.append(" left join department as dp on dp.id=subdept.deptid  where  subdept in(select id from subdepartment) ");
				
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and emp."+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and emp."+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and emp."+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and emp."+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and emp."+getSearchField()+" like '%"+getSearchString()+"'");
					}
					
				}
				
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by emp."+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by emp."+getSidx()+" DESC");
		    	    }
			    } 
				if(makeHistory.equalsIgnoreCase("0")){
				query.append("  and  emp.status=0 order by dp.deptname " );}
				else{	query.append("  and  emp.status=1 order by dp.deptname " );}
				//query.append(" limit "+from+","+to);
				
				/**
				 * **************************checking for colomon change due to configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames,"employee_basic",connectionSpace);
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					if(data.size()>0)
					{
							/*
							 * id,subdept,designation,doj_date,empName,comName,mobOne,mobTwo,emailIdOne,emailIdTwo,salary,cContactNo,address,landmark,city,
							 * cpincode,empLogo,empDocument,userName,personalIds,qualificationIds,workExprienceIDs,bankaccount,date,time,useraccountid,status,empId
							 */
							
								for(Iterator it=data.iterator(); it.hasNext();)
								{
									Object[] obdata=(Object[])it.next();
									Map<String, Object> one=new HashMap<String, Object>();
									for (int k = 0; k < fieldNames.size(); k++) {
										if (obdata[k] == null
												|| obdata[k].toString()
														.equalsIgnoreCase("")) {
											obdata[k] = "NA";
										}
										if(obdata[k]!=null)
										{
											if(fieldNames.get(k).toString().equalsIgnoreCase("status"))
											{
												String status=new String();
												if(obdata[k].toString().equalsIgnoreCase("0"))
												{
													status="Working";
												}
												else
												{
													status="Working";
												}
													one.put(fieldNames.get(k).toString(), status);
											}
											else
											{
												if(obdata[k]!=null)
												{
													if(k==1)
													{
														one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
													}
													else
													{
														if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
														{
															one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
														}
														else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("doj_date"))
														{
															one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
														}
														else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
														{
															one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
														}
														else if(fieldNames.get(k).toString().equalsIgnoreCase("levelofhierarchy"))
														{
															one.put(fieldNames.get(k).toString(),obdata[k].toString());
														}
														
														else
														{
															one.put(fieldNames.get(k).toString(),obdata[k].toString());
														}
													}
												}
												
											}
								
										}
									}
									Listhb.add(one);
								}
							
								setEmpDetail(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method employeeGridDataView of class "+getClass(), e);
			 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
/*	public String employeeGridDataView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setEditDeptData(getEditDeptData());
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			
			String sql_query2 = " select employee_basic.subdept from employee_basic INNER JOIN useraccount on useraccount.id=employee_basic.useraccountid where useraccount.userID='"+encryptedID+"'";
			List listData2 = cbt.executeAllSelectQuery(sql_query2, connectionSpace);
			
			
			String sql_query3 = " select deptid from subdepartment where id='"+listData2.get(0)+"' ";
			List listData3 = cbt.executeAllSelectQuery(sql_query3, connectionSpace);
			
			
			String deptLevel=session.get("deptLevel").toString();
			String temp[]=deptLevel.split(",");
			deptLevel=temp[0];
			StringBuilder query1=new StringBuilder("");
			//query1.append("select count(*) from employee_basic where subdept in(select id from subdepartment where deptid='"+listData3.get(0)+"')");
			if(makeHistory.equalsIgnoreCase("0")){
			query1.append("select count(*) from employee_basic where subdept in(select id from subdepartment) and status='0'");
			}
			else{
				query1.append("select count(*) from employee_basic where subdept in(select id from subdepartment)and status='1'");
			}
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null)
			{
				BigInteger count=new BigInteger("3");
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
				
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				//query.append("select ");
				query.append("select dp.deptname, ");
				List<String> fieldNames=Configuration.getColomnList("mapped_employee_basic_configuration", accountID, connectionSpace, "employee_basic_configuration");
				//fieldNames.add("deptname");
				fieldNames.add(0, "deptname");
				fieldNames.add("levelofhierarchy");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
					    	if(deptLevel.equalsIgnoreCase("2") && obdata.toString().equalsIgnoreCase("subdept"))
					    	{
							    if(i<fieldNames.size()-1)
							    	query.append("subdept.subdeptname,");
							    else
							    	query.append("subdept.subdeptname,");
					    	}
					    	else if(deptLevel.equalsIgnoreCase("1") && obdata.toString().equalsIgnoreCase("subdept"))
					    	{
							    if(i<fieldNames.size()-1)
							    	query.append("dept.deptname,");
							    else
							    	query.append("dept.deptname,");
					    	}
					    	else if(obdata.toString().equalsIgnoreCase("designation"))
					    	{
							    if(i<fieldNames.size()-1)
							    {
							    	query.append("desg.designationname,");
							    }
							    else
							    {
							    	query.append("desg.designationname,");
							    }
					    	}
					    	else if(!obdata.toString().equalsIgnoreCase("subdept") && !obdata.toString().equalsIgnoreCase("deptname") && !obdata.toString().equalsIgnoreCase("levelofhierarchy"))
					    	{
					    		if(i<fieldNames.size()-1)
							    	query.append("emp."+obdata.toString()+",");
							    else
							    	query.append("emp."+obdata.toString()+",");
					    	}
					    	else if(obdata.toString().equalsIgnoreCase("levelofhierarchy"))
					    	{
					    		query.append("desg.levelofhierarchy");
					    	}
					    	
					    }
					    i++;
					
				}
				
				
					
				if(deptLevel.equalsIgnoreCase("1"))
				{
					query.append(" from employee_basic as emp left join department as dept  " +
							"on dept.id=emp.dept left join designation as desg on emp.designation=desg.id");
				}
				else if(deptLevel.equalsIgnoreCase("2"))
				{
					query.append(" from employee_basic as emp left join subdepartment " +
							"as subdept on subdept.id=emp.subdept left join designation as desg  on emp.designation=desg.id");
				}
				//query.append(" left join department as dp on dp.id=subdept.deptid  where  subdept in(select id from subdepartment where deptid='"+listData3.get(0)+"') ");
				query.append(" left join department as dp on dp.id=subdept.deptid  where  subdept in(select id from subdepartment) ");
				
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and emp."+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and emp."+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and emp."+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and emp."+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and emp."+getSearchField()+" like '%"+getSearchString()+"'");
					}
					
				}
				
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by emp."+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by emp."+getSidx()+" DESC");
		    	    }
			    } 
				if(makeHistory.equalsIgnoreCase("0")){
				query.append("  and  emp.status=0 order by dp.deptname " );}
				else{	query.append("  and  emp.status=1 order by dp.deptname " );}
				//query.append(" limit "+from+","+to);
				
				*//**
				 * **************************checking for colomon change due to configuration if then alter table
				 *//*
				cbt.checkTableColmnAndAltertable(fieldNames,"employee_basic",connectionSpace);
				System.out.println("Employee Query "+query.toString());
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					
					
					if(data.size()>0)
					{
							
							 * id,subdept,designation,doj_date,empName,comName,mobOne,mobTwo,emailIdOne,emailIdTwo,salary,cContactNo,address,landmark,city,
							 * cpincode,empLogo,empDocument,userName,personalIds,qualificationIds,workExprienceIDs,bankaccount,date,time,useraccountid,status,empId
							 
							
								for(Iterator it=data.iterator(); it.hasNext();)
								{
									Object[] obdata=(Object[])it.next();
									Map<String, Object> one=new HashMap<String, Object>();
									for (int k = 0; k < fieldNames.size(); k++) {
										if (obdata[k] == null
												|| obdata[k].toString()
														.equalsIgnoreCase("")) {
											obdata[k] = "NA";
										}
										if(obdata[k]!=null)
										{
											if(fieldNames.get(k).toString().equalsIgnoreCase("status"))
											{
												String status=new String();
												if(obdata[k].toString().equalsIgnoreCase("0"))
												{
													status="Working";
												}
												else
												{
													status="Working";
												}
													one.put(fieldNames.get(k).toString(), status);
											}
											else
											{
												if(obdata[k]!=null)
												{
													if(k==1)
													{
														one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
													}
													else
													{
														if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
														{
															one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
														}
														else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("doj_date"))
														{
															one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
														}
														else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
														{
															one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
														}
														else if(fieldNames.get(k).toString().equalsIgnoreCase("levelofhierarchy"))
														{
															one.put(fieldNames.get(k).toString(),obdata[k].toString());
														}
														
														else
														{
															one.put(fieldNames.get(k).toString(),obdata[k].toString());
														}
													}
												}
												
											}
								
										}
									}
									Listhb.add(one);
								}
							
								setEmpDetail(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method employeeGridDataView of class "+getClass(), e);
			 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			 e.printStackTrace();
		}
		return SUCCESS;
	}*/
	
	/**
	 * Edit EMP Basic details
	 * @return
	 */
	
	
	public void setGridColomnNames()
	{
		/*
		 * id,subdept,designation,doj_date,empName,comName,mobOne,mobTwo,emailIdOne,emailIdTwo,salary,cContactNo,address,landmark,city,cpincode,empLogo
			,empDocument,userName,personalIds,qualificationIds,workExprienceIDs,bankaccount,date,time,useraccountid
		 */
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		String sql_query2 = " select employee_basic.subdept from employee_basic INNER JOIN useraccount on useraccount.id=employee_basic.useraccountid where useraccount.userID='"+encryptedID+"'";
		List listData2 = cbt.executeAllSelectQuery(sql_query2, connectionSpace);
		
		String sql_query22 = " select deptid from subdepartment where id='"+listData2.get(0)+"'";
		List listData22 = cbt.executeAllSelectQuery(sql_query22, connectionSpace);
		
		String sql_query3 = " select distinct designation from employee_basic where subdept in(select id from subdepartment where deptid='"+listData22.get(0)+"')";
		List listData3 = cbt.executeAllSelectQuery(sql_query3, connectionSpace);
		String str=listData3.toString();
		String str2=str.substring(1, str.length()-1);
		
		String sql_query33 = "select id, designationname from designation where id in("+str2+")";
		List listData33 = cbt.executeAllSelectQuery(sql_query33, connectionSpace);
		StringBuilder de=new StringBuilder("");
		de.append("{value:'");
		for (Iterator iterator = listData33.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			desigID=object[0].toString();
		    designation =object[1].toString();
		   
		    de.append(desigID+":"+ designation+";");
			
		}
		de.deleteCharAt(de.length()-1);
		de.append("'}" );
	
		
		
		
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Emp Id");
		gpv.setHideOrShow("true");
		gpv.setWidth(100);
		employeeGridColomns.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("deptname");
		gpv.setHeadingName("Department");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		employeeGridColomns.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_basic_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			if(gdp.getColomnName().equals("subdept"))
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName("subdept");
				int level=setDepartmentAndSubDeptNames();
				if(level==1)
				{
					gpv.setHeadingName(dept);
				}
				if(level==2)
				{
					gpv.setHeadingName(subDept);
				}
				gpv.setEditable("false");
				gpv.setHideOrShow("false");
				gpv.setFrozenValue("true");
				gpv.setSearch("false");
				gpv.setWidth(212);
				employeeGridColomns.add(gpv);
			}
			else if(gdp.getColomnName().equals("dept"))
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName("subdept");
				int level=setDepartmentAndSubDeptNames();
				if(level==1)
				{
					gpv.setHeadingName(dept);
				}
				if(level==2)
				{
					gpv.setHeadingName(subDept);
				}
				gpv.setEditable("false");
				gpv.setHideOrShow("false");
				gpv.setFrozenValue("true");
				gpv.setSearch("false");
				gpv.setWidth(100);
				employeeGridColomns.add(gpv);
			}
			else if(gdp.getColomnName().equals("designation"))
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				gpv.setFormatter(gdp.getFormatter());
				gpv.setEdittype("select");
				gpv.setEditoptions(de.toString());
				employeeGridColomns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("levelofhierarchy");
				gpv.setHeadingName("Level");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				employeeGridColomns.add(gpv);
			}
			else
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				gpv.setFormatter(gdp.getFormatter());
				employeeGridColomns.add(gpv);
			}
			
		}
			
		
			//formatter mapping based on other table data mapping that can be multiple
			StringBuilder empuery=new StringBuilder("");
			empuery.append("select table_name from mapped_employee_basic_configuration");
			List  empData=cbt.executeAllSelectQuery(empuery.toString(),connectionSpace);
			for(Iterator it=empData.iterator(); it.hasNext();)
			{
					 Object obdata=(Object)it.next();
					 if(obdata!=null)
					 {
						if(obdata.toString().equalsIgnoreCase("employee_personal_configuration"))
						 {
							    gpv=new GridDataPropertyView();
								gpv.setColomnName("empName");
								gpv.setHeadingName("Personal Record");
								gpv.setFormatter("personalRecordsView");
								gpv.setSearch("false");
								employeeGridColomns.add(gpv);
						 }
						 else if(obdata.toString().equalsIgnoreCase("employee_work_exprience_configuration"))
						 {
							    gpv=new GridDataPropertyView();
								gpv.setColomnName("empName");
								gpv.setHeadingName("Qualification Record");
								gpv.setFormatter("qualificationRecordsView");
								gpv.setSearch("false");
								employeeGridColomns.add(gpv);
								gpv=new GridDataPropertyView();
								gpv.setColomnName("empName");
								gpv.setHeadingName("Work Exprience");
								gpv.setFormatter("workExprienceView");
								gpv.setSearch("false");
								employeeGridColomns.add(gpv);
						 }
						 else if(obdata.toString().equalsIgnoreCase("employee_bank_details_configuration"))
						 {
							    gpv=new GridDataPropertyView();
								gpv.setColomnName("empName");
								gpv.setHeadingName("Bank Account");
								gpv.setFormatter("bankAccountView");
								gpv.setSearch("false");
								employeeGridColomns.add(gpv);
						 }
					 }
			}
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("KRA KPI");
			gpv.setHideOrShow("true");
			gpv.setFormatter("krakpiview");
			employeeGridColomns.add(gpv);
			/*gpv=new GridDataPropertyView();
			gpv.setColomnName("workExprienceIDs");
			gpv.setHeadingName("Work Exprience");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			employeeGridColomns.add(gpv);*/
	}
	
	
	
	public String viewallOtherDetails()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			    List selectFields=new ArrayList<String>();
			   selectFields.add("personalIds");
			   selectFields.add("qualificationIds");
			   selectFields.add("workExprienceIDs");
			   selectFields.add("bankaccount");
	    	   Map<String, Object> temp=new HashMap<String, Object>();
	    	   temp.put("id",getId());
	    	   CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	   selectFields=cbt.viewAllDataEitherSelectOrAll("employee_basic",selectFields,temp,connectionSpace);
	    	   
			if(getModuleFlag().equalsIgnoreCase("1"))
			{
				//Employee Personal Details
				String mappedIds=null;
				for (Object c : selectFields) {
					Object[] dataC = (Object[]) c;
					if(dataC[0]!=null)
					{
						mappedIds=dataC[0].toString();
						String tempIds[]=mappedIds.split("#");
						int i=1;
						StringBuilder idsTemp=new StringBuilder();
						for(String H:tempIds)
						{
							if(!H.equalsIgnoreCase(""))
							{
								if(i<tempIds.length)
								{
									idsTemp.append("'"+H+"',");		
									i++;
								}
								else
								{
									idsTemp.append("'"+H+"'");	
								}
							}
						}
						
						StringBuilder query1=new StringBuilder("");
						query1.append("select count(*) from employee_personal");
						List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
						
						if(dataCount!=null)
						{
							BigInteger count=new BigInteger("3");
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
							
							//getting the list of colmuns
							StringBuilder ids=new StringBuilder("");
							ids.append("select ");
							List fieldNames=Configuration.getColomnList("mapped_employee_basic_configuration", accountID, connectionSpace, "employee_personal_configuration");
							List<Object> Listhb=new ArrayList<Object>();
							int l=0;
							for(Iterator it=fieldNames.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(l<fieldNames.size()-1)
									    	ids.append(obdata.toString()+",");
									    else
									    	ids.append(obdata.toString());
								    }
								    l++;
								
							}
							
						ids.append(" from employee_personal where id in ("+idsTemp.toString()+")");
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							//add search  query based on the search operation
							if(getSearchOper().equalsIgnoreCase("eq"))
							{
								ids.append(" and "+getSearchField()+" = '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("cn"))
							{
								ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("bw"))
							{
								ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("ne"))
							{
								ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("ew"))
							{
								ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
							}
							
						}
						
						if (getSord() != null && !getSord().equalsIgnoreCase(""))
					    {
							if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
							{
								ids.append(" order by "+getSidx());
							}
				    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
				    	    {
				    	    	ids.append(" order by "+getSidx()+" DESC");
				    	    }
					    }
						
						ids.append(" limit "+from+","+to);
						
						/**
						 * **************************checking for colomon change due to configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,"employee_personal",connectionSpace);
						
						List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
							if(data!=null)
							{
								if(data.size()>0)
								{
									for(Iterator it=data.iterator(); it.hasNext();)
									{
										Object[] obdata=(Object[])it.next();
										Map<String, Object> one=new HashMap<String, Object>();
										for (int k = 0; k < fieldNames.size(); k++) {
											if(obdata[k]!=null)
											{
											if(k==0)
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											else
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}
										Listhb.add(one);
									}
									setEmpOtherDataView(Listhb);
								}
								setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							}
						}
					}
					else
					{
						return ERROR;
					}
					
				}
				
				
			}
			else if(getModuleFlag().equalsIgnoreCase("2"))
			{
				List fieldNames=new ArrayList<String>();
				fieldNames.add("id");
				fieldNames.add("empName");
				fieldNames.add("acadmic");
				fieldNames.add("aggregate");
				fieldNames.add("subject");
				fieldNames.add("year");
				fieldNames.add("college");
				fieldNames.add("university");
				fieldNames.add("userName");
				fieldNames.add("date");
				fieldNames.add("time");
				//Employee qualification details
				//id,empName,acadmic,aggregate,subject,year,college,university,userName,date,time
				String mappedIds=null;
				for (Object c : selectFields) {
					Object[] dataC = (Object[]) c;
					if(dataC[1]!=null)
					{
						mappedIds=dataC[1].toString();
						String tempIds[]=mappedIds.split("#");
						int i=1;
						StringBuilder idsTemp=new StringBuilder();
						for(String H:tempIds)
						{
							if(!H.equalsIgnoreCase(""))
							{
								if(i<tempIds.length)
								{
									idsTemp.append("'"+H+"',");	
									i++;
								}
								else
								{
									idsTemp.append("'"+H+"'");	
								}
							}
						}
						
						StringBuilder query1=new StringBuilder("");
						query1.append("select count(*) from employee_qualificationdeatils");
						List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
						if(dataCount!=null)
						{
							BigInteger count=new BigInteger("3");
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
							
							
							StringBuilder ids=new StringBuilder();
							
							ids.append("select ");
							List<Object> Listhb=new ArrayList<Object>();
							int l=0;
							for(Iterator it=fieldNames.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(l<fieldNames.size()-1)
									    	ids.append(obdata.toString()+",");
									    else
									    	ids.append(obdata.toString());
								    }
								    l++;
								
							}
							
							ids.append(" from employee_qualificationdeatils where id in ("+idsTemp.toString()+")");
							
							if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
							{
								//add search  query based on the search operation
								if(getSearchOper().equalsIgnoreCase("eq"))
								{
									ids.append(" and "+getSearchField()+" = '"+getSearchString()+"'");
								}
								else if(getSearchOper().equalsIgnoreCase("cn"))
								{
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");
								}
								else if(getSearchOper().equalsIgnoreCase("bw"))
								{
									ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");
								}
								else if(getSearchOper().equalsIgnoreCase("ne"))
								{
									ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");
								}
								else if(getSearchOper().equalsIgnoreCase("ew"))
								{
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
								}
								
							}
							
							if (getSord() != null && !getSord().equalsIgnoreCase(""))
						    {
								if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
								{
									ids.append(" order by "+getSidx());
								}
					    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
					    	    {
					    	    	ids.append(" order by "+getSidx()+" DESC");
					    	    }
						    }
							
							
							
							ids.append(" limit "+from+","+to);
							
							/**
							 * **************************checking for colomon change due to configuration if then alter table
							 */
							cbt.checkTableColmnAndAltertable(fieldNames,"employee_qualificationdeatils",connectionSpace);
							
							List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
								if(data!=null)
								{
									if(data.size()>0)
									{
										for(Iterator it=data.iterator(); it.hasNext();)
										{
											Object[] obdata=(Object[])it.next();
											Map<String, Object> one=new HashMap<String, Object>();
											for (int k = 0; k < fieldNames.size(); k++) {
												if(obdata[k]!=null)
												{
												if(k==0)
													one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
												else
													one.put(fieldNames.get(k).toString(), obdata[k].toString());
												}
											}
											Listhb.add(one);
										}
										setEmpOtherDataView(Listhb);
									}
									setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
								}
							}
					}
					else
					{
						return ERROR;
					}
					
				}
			}
			else if(getModuleFlag().equalsIgnoreCase("3"))
			{
				//Employee WorkExprienceView details
				String mappedIds=null;
				for (Object c : selectFields) {
					Object[] dataC = (Object[]) c;
					if(dataC[2]!=null)
					{
						mappedIds=dataC[2].toString();
						String tempIds[]=mappedIds.split("#");
						int i=1;
						StringBuilder idsTemp=new StringBuilder();
						for(String H:tempIds)
						{
							if(!H.equalsIgnoreCase(""))
							{
								if(i<tempIds.length)
								{
									idsTemp.append("'"+H+"',");	
									i++;
								}
								else
								{
									idsTemp.append("'"+H+"'");		
								}
							}
						}
						
						StringBuilder query1=new StringBuilder("");
						query1.append("select count(*) from employee_workexpriencedetails");
						List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
						if(dataCount!=null)
							{
							BigInteger count=new BigInteger("3");
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
							
							//getting the list of colmuns
							StringBuilder ids=new StringBuilder("");
							ids.append("select ");
							List fieldNames=Configuration.getColomnList("mapped_employee_basic_configuration", accountID, connectionSpace, "employee_work_exprience_configuration");
							List<Object> Listhb=new ArrayList<Object>();
							int l=0;
							for(Iterator it=fieldNames.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(l<fieldNames.size()-1)
									    	ids.append(obdata.toString()+",");
									    else
									    	ids.append(obdata.toString());
								    }
								    l++;
								
							}
							
							ids.append(" from employee_workexpriencedetails where id in ("+idsTemp.toString()+")");
							if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
							{
								//add search  query based on the search operation
								if(getSearchOper().equalsIgnoreCase("eq"))
								{
									ids.append(" and "+getSearchField()+" = '"+getSearchString()+"'");
								}
								else if(getSearchOper().equalsIgnoreCase("cn"))
								{
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");
								}
								else if(getSearchOper().equalsIgnoreCase("bw"))
								{
									ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");
								}
								else if(getSearchOper().equalsIgnoreCase("ne"))
								{
									ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");
								}
								else if(getSearchOper().equalsIgnoreCase("ew"))
								{
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
								}
								
							}
							
							if (getSord() != null && !getSord().equalsIgnoreCase(""))
						    {
								if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
								{
									ids.append(" order by "+getSidx());
								}
					    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
					    	    {
					    	    	ids.append(" order by "+getSidx()+" DESC");
					    	    }
						    }
							
							
							
							ids.append(" limit "+from+","+to);
							
							
							/**
							 * **************************checking for colomon change due to configuration if then alter table
							 */
							cbt.checkTableColmnAndAltertable(fieldNames,"employee_workexpriencedetails",connectionSpace);
							
							
							List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
								if(data!=null)
								{
									for(Iterator it=data.iterator(); it.hasNext();)
									{
										Object[] obdata=(Object[])it.next();
										Map<String, Object> one=new HashMap<String, Object>();
										for (int k = 0; k < fieldNames.size(); k++) {
											if(obdata[k]!=null)
											{
											if(k==0)
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											else
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}
										Listhb.add(one);
									}
									setEmpOtherDataView(Listhb);
								setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
								}
							}
						
					}
					else
					{
						return ERROR;
					}
					
				}
			}
			else if(getModuleFlag().equalsIgnoreCase("4"))
			{
				//Employee BankAccountDetails details
				//id,empName,bankName,bankAddress,accountNo,ifscCode,acType,userName,date,time
				String mappedIds=null;
				for (Object c : selectFields) {
					Object[] dataC = (Object[]) c;
					if(dataC[3]!=null)
					{
						mappedIds=dataC[3].toString();
						String tempIds[]=mappedIds.split("#");
						int i=1;
						StringBuilder idsTemp=new StringBuilder();
						for(String H:tempIds)
						{
							if(!H.equalsIgnoreCase(""))
							{
								if(i<tempIds.length)
								{
									idsTemp.append("'"+H+"',");	
									i++;
								}
								else
								{
									idsTemp.append("'"+H+"'");	
								}
							}
						}
						
						StringBuilder query1=new StringBuilder("");
						query1.append("select count(*) from employee_bankdetails");
						List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
						if(dataCount!=null)
						{
							BigInteger count=new BigInteger("3");
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
							
							//getting the list of colmuns
							StringBuilder ids=new StringBuilder("");
							ids.append("select ");
							List fieldNames=Configuration.getColomnList("mapped_employee_basic_configuration", accountID, connectionSpace, "employee_bank_details_configuration");
							List<Object> Listhb=new ArrayList<Object>();
							int l=0;
							for(Iterator it=fieldNames.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    Object obdata=(Object)it.next();
								    if(obdata!=null)
								    {
									    if(l<fieldNames.size()-1)
									    	ids.append(obdata.toString()+",");
									    else
									    	ids.append(obdata.toString());
								    }
								    l++;
								
							}
							
							ids.append(" from employee_bankdetails where id in ("+idsTemp.toString()+")");
							if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
							{
								//add search  query based on the search operation
								if(getSearchOper().equalsIgnoreCase("eq"))
								{
									ids.append(" and "+getSearchField()+" = '"+getSearchString()+"'");
								}
								else if(getSearchOper().equalsIgnoreCase("cn"))
								{
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");
								}
								else if(getSearchOper().equalsIgnoreCase("bw"))
								{
									ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");
								}
								else if(getSearchOper().equalsIgnoreCase("ne"))
								{
									ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");
								}
								else if(getSearchOper().equalsIgnoreCase("ew"))
								{
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
								}
								
							}
							
							if (getSord() != null && !getSord().equalsIgnoreCase(""))
						    {
								if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
								{
									ids.append(" order by "+getSidx());
								}
					    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
					    	    {
					    	    	ids.append(" order by "+getSidx()+" DESC");
					    	    }
						    }
							
							
							
							ids.append(" limit "+from+","+to);
							
							
							/**
							 * **************************checking for colomon change due to configuration if then alter table
							 */
							cbt.checkTableColmnAndAltertable(fieldNames,"employee_bankdetails",connectionSpace);
							
							
							List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
								if(data!=null)
								{
									for(Iterator it=data.iterator(); it.hasNext();)
									{
										Object[] obdata=(Object[])it.next();
										Map<String, Object> one=new HashMap<String, Object>();
										for (int k = 0; k < fieldNames.size(); k++) {
											if(obdata[k]!=null)
											{
											if(k==0)
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											else
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}
										Listhb.add(one);
									}
									setEmpOtherDataView(Listhb);
									setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
								
								}
						}
					}
					else
					{
						return ERROR;
					}
					
				}
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewallOtherDetails of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/*
	 * Method for updating all the type of employee information
	 */
	
	public String editEmpGrid()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
            CommonOperInterface cbt2 = new CommonConFactory().createInterface();
		    String sql_query = "select subdept from employee_basic where id='"+getId()+"'";
			List listData = cbt2.executeAllSelectQuery(sql_query, connectionSpace);
			
			String sql_query2 = "select distinct (designation) from employee_basic where subdept='"+listData.get(0)+"'";
			List listData2 = cbt2.executeAllSelectQuery(sql_query, connectionSpace);
			
			
			if(getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("makeHistory")	&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
			}
			
			if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				if(makeHistory.equalsIgnoreCase("0")){
				wherClause.put("status", 1);
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
				}
				else{
					wherClause.put("status", 0);
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method editEmpGrid of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String editOtherDetails()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			if(getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				if(getModuleFlag().equalsIgnoreCase("1"))
				{
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")&& !Parmname.equalsIgnoreCase("moduleFlag"))
							wherClause.put(Parmname, paramValue);
					}
					
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_personal", wherClause, condtnBlock,connectionSpace);
				}
				else if(getModuleFlag().equalsIgnoreCase("2"))
				{
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")&& !Parmname.equalsIgnoreCase("moduleFlag"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_qualificationdeatils", wherClause, condtnBlock,connectionSpace);
				}
				else if(getModuleFlag().equalsIgnoreCase("3"))
				{
					//Employee WorkExprienceView details
					
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")&& !Parmname.equalsIgnoreCase("moduleFlag"))
							wherClause.put(Parmname, paramValue);
					}
					
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_workexpriencedetails", wherClause, condtnBlock,connectionSpace);
				}
				else if(getModuleFlag().equalsIgnoreCase("4"))
				{
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")&& !Parmname.equalsIgnoreCase("moduleFlag"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_bankdetails", wherClause, condtnBlock,connectionSpace);
				}
			}
			else
			{
				addActionError("Oops their is some problem!!!");
				return ERROR;
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method editOtherDetails of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public int setDepartmentAndSubDeptNames()
	{
		int level=1;
		try
		{
				String namesofDepts[]=new String[3];
				StringBuilder query=new StringBuilder("");
					query.append("select levelName,orgLevel from mapped_dept_level_config");
						List  data=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
						
						if(data!=null)
						{
							String names=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object[] obdata=(Object[])it.next();
								if(obdata[0]!=null)
									names=obdata[0].toString();
								  level=Integer.parseInt(obdata[1].toString());
								
							}
							try
							{
							namesofDepts=names.split("#");
							if(namesofDepts.length==0)
								dept=namesofDepts[0];
							if(namesofDepts.length>0)
								subDept=namesofDepts[1];
							}
							catch(Exception e)
							{
								
							}
						}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method setDepartmentAndSubDeptNames of class "+getClass(), e);
			e.printStackTrace();
		}
		return level;
	}
	
	public void setGridColomnForPersonal()
	{
		//id,empName,fname,fmob,mname,edob_date,doa_date,sname,sdob_date,coneName,conedob_date,ctwoName,
		//ctwodob_date,addressPer,landmarkPer,cityPer,pincode,contactNo,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		gpv.setWidth(100);
		viewPropertiesColomnName.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",1,"table_name","employee_personal_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setFormatter(gdp.getFormatter());
			viewPropertiesColomnName.add(gpv);
			
		}

	}
	
	public void setGridColomnForQualification()
	{
		//id,empName,acadmic,aggregate,subject,year,college,university,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("empName");
		gpv.setHeadingName("Name");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("acadmic");
		gpv.setHeadingName("Acadmic Name");
		gpv.setEditable("true");
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("aggregate");
		gpv.setHeadingName("Aggregate");
		gpv.setEditable("true");
		gpv.setWidth(100);
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("subject");
		gpv.setHeadingName("Subject");
		gpv.setEditable("true");
		gpv.setWidth(150);
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("year");
		gpv.setHeadingName("Passing Year");
		gpv.setEditable("true");
		gpv.setWidth(100);
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("college");
		gpv.setHeadingName("College");
		gpv.setEditable("true");
		gpv.setWidth(150);
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("university");
		gpv.setHeadingName("University Name");
		gpv.setEditable("true");
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("userName");
		gpv.setHeadingName("Created By");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("date");
		gpv.setHeadingName("Created On");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
		gpv=new GridDataPropertyView();
		gpv.setColomnName("time");
		gpv.setHeadingName("Created Time");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
	}
	
	public void setGridColomnForWorkExprience()
	{
		//id,empName,cName,cAddress,workExpDesg,cdept,salary,workEmpId,pFrom,
		//pTo,refrenceOne,refOneDesg,refOneMobNo,refOneEmailId,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",2,"table_name","employee_work_exprience_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setFormatter(gdp.getFormatter());
			viewPropertiesColomnName.add(gpv);
			
		}

	}
	
	public void setGridColomnBankAccountDetails()
	{
		//id,empName,bankName,bankAddress,accountNo,ifscCode,acType,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPropertiesColomnName.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",3,"table_name","employee_bank_details_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setFormatter(gdp.getFormatter());
			viewPropertiesColomnName.add(gpv);
			
		}
		
	}
	
	private Map<String,String>empKpiLiist=null;
	public String viewMappedKRAKPI()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			empKpiLiist=CommonWork.getMappedKPIOfEmployee(Integer.toString(getId()), connectionSpace);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewMappedKRAKPI of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String employeeViewHeader()
	{
		System.out.println("employeeViewHeader");
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			deptMap=new LinkedHashMap<Integer, String>();
			List data = cbt.executeAllSelectQuery("select id,deptName from department ORDER BY deptName ASC ", connectionSpace);
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
			checkStatus=new HashMap<String, String>();
			checkStatus.put("0","Active");
			checkStatus.put("1","InActive");
			checkStatus.put("-1","All");
			setCheckStatus(checkStatus);
			toatlcount=getCounters( connectionSpace);
			activecount=getActiveCounters( connectionSpace);
			inActivecount=getInActiveCounters( connectionSpace);
			
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getCounters(SessionFactory connectionSpace)
	{
		String counter=null;
		
		StringBuilder builder=new StringBuilder(" SELECT count(*) FROM employee_basic as emp  ");
		builder.append(" INNER Join groupinfo as gi ON gi.id=emp.groupId ");
		builder.append(" WHERE gi.groupName ='Employee' ");
		System.out.println(builder);
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			counter=dataList.get(0).toString();
		}
		return counter;
	}
	
	public String getInActiveCounters(SessionFactory connectionSpace)
	{
		String counter=null;
		
		StringBuilder builder=new StringBuilder(" SELECT count(*) FROM employee_basic as emp  ");
		builder.append(" INNER Join groupinfo as gi ON gi.id=emp.groupId ");
		builder.append(" WHERE gi.groupName ='Employee' ");
		builder.append(" AND flag =1 ");
		System.out.println(builder);
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			counter=dataList.get(0).toString();
		}
		return counter;
	}
	public String getActiveCounters(SessionFactory connectionSpace)
	{
		String counter=null;
		
		StringBuilder builder=new StringBuilder(" SELECT count(*) FROM employee_basic as emp  ");
		builder.append(" INNER Join groupinfo as gi ON gi.id=emp.groupId ");
		builder.append(" WHERE gi.groupName ='Employee' ");
		builder.append(" AND flag =0 ");
		System.out.println(builder);
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			counter=dataList.get(0).toString();
		}
		return counter;
	}
	
	
	
	public Map<String, String> getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Map<String, String> checkStatus) {
		this.checkStatus = checkStatus;
	}

	public List<GridDataPropertyView> getEmployeeGridColomns() {
		return employeeGridColomns;
	}

	public void setEmployeeGridColomns(
			List<GridDataPropertyView> employeeGridColomns) {
		this.employeeGridColomns = employeeGridColomns;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getEditDeptData() {
		return editDeptData;
	}

	public void setEditDeptData(String editDeptData) {
		this.editDeptData = editDeptData;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public List<Object> getEmpDetail() {
		return empDetail;
	}

	public void setEmpDetail(List<Object> empDetail) {
		this.empDetail = empDetail;
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

	public String getCContactNo() {
		return cContactNo;
	}

	public void setCContactNo(String contactNo) {
		cContactNo = contactNo;
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

	public List<GridDataPropertyView> getViewPropertiesColomnName() {
		return viewPropertiesColomnName;
	}

	public void setViewPropertiesColomnName(
			List<GridDataPropertyView> viewPropertiesColomnName) {
		this.viewPropertiesColomnName = viewPropertiesColomnName;
	}

	public String getModuleFlag() {
		return moduleFlag;
	}

	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
	}


	public List<Object> getEmpOtherDataView() {
		return empOtherDataView;
	}

	public void setEmpOtherDataView(List<Object> empOtherDataView) {
		this.empOtherDataView = empOtherDataView;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}


	public String getEmpModuleFalgForDeptSubDept() {
		return empModuleFalgForDeptSubDept;
	}

	public void setEmpModuleFalgForDeptSubDept(String empModuleFalgForDeptSubDept) {
		this.empModuleFalgForDeptSubDept = empModuleFalgForDeptSubDept;
	}
	
	public Map<String, String> getEmpKpiLiist() {
		return empKpiLiist;
	}

	public void setEmpKpiLiist(Map<String, String> empKpiLiist) {
		this.empKpiLiist = empKpiLiist;
	}
	

	public String getMakeHistory() {
		return makeHistory;
	}

	public void setMakeHistory(String makeHistory) {
		this.makeHistory = makeHistory;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getRecord() {
		return record;
	}

	public String getFetchEmpName() {
		return fetchEmpName;
	}

	public void setFetchEmpName(String fetchEmpName) {
		this.fetchEmpName = fetchEmpName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Map<String, String> getEmpPersonalFullViewMap() {
		return empPersonalFullViewMap;
	}

	public void setEmpPersonalFullViewMap(Map<String, String> empPersonalFullViewMap) {
		this.empPersonalFullViewMap = empPersonalFullViewMap;
	}

	public Map<String, String> getEmpBankFullViewMap() {
		return empBankFullViewMap;
	}

	public void setEmpBankFullViewMap(Map<String, String> empBankFullViewMap) {
		this.empBankFullViewMap = empBankFullViewMap;
	}


	public List<EmpBasicPojoBean> getEmpProfessionalViewMap() {
		return empProfessionalViewMap;
	}

	public void setEmpProfessionalViewMap(
			List<EmpBasicPojoBean> empProfessionalViewMap) {
		this.empProfessionalViewMap = empProfessionalViewMap;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getStatusCheck() {
		return statusCheck;
	}

	public void setStatusCheck(String statusCheck) {
		this.statusCheck = statusCheck;
	}

	public Map<String, String> getEmpBasicFullViewMap() {
		return empBasicFullViewMap;
	}

	public void setEmpBasicFullViewMap(Map<String, String> empBasicFullViewMap) {
		this.empBasicFullViewMap = empBasicFullViewMap;
	}

	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}

	public String getFieldser() {
		return fieldser;
	}

	public void setFieldser(String fieldser) {
		this.fieldser = fieldser;
	}

	public String getFieldval() {
		return fieldval;
	}

	public void setFieldval(String fieldval) {
		this.fieldval = fieldval;
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

	public String getToatlcount() {
		return toatlcount;
	}

	public void setToatlcount(String toatlcount) {
		this.toatlcount = toatlcount;
	}

	public void setActivecount(String activecount) {
		this.activecount = activecount;
	}

	public String getActivecount() {
		return activecount;
	}

	public void setInActivecount(String inActivecount) {
		this.inActivecount = inActivecount;
	}

	public String getInActivecount() {
		return inActivecount;
	}

	public Map<String, String> getEmpworkExpFullViewMap() {
		return empworkExpFullViewMap;
	}

	public void setEmpworkExpFullViewMap(Map<String, String> empworkExpFullViewMap) {
		this.empworkExpFullViewMap = empworkExpFullViewMap;
	}

	
	
}
