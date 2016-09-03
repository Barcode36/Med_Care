package com.Over2Cloud.ctrl.hr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class EmployeeUniversalCtrl extends ActionSupport implements ServletRequestAware
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String empModuleFalgForDeptSubDept;
	private int empForAddBasic=0;
	private int empForAddPrsl=0;
	private int empForAddWorkQua=0;
	private int empForAddBaank=0;
	private int empForAddWorkExpirence=0;
	//emp personal add page generation
	private List<ConfigurationUtilBean>empPerLevels=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empPerCalendr=new ArrayList<ConfigurationUtilBean>();
	private String empNameForOther;
	//emp work exprience add page generation
	private List<ConfigurationUtilBean>empWorkExpLevels=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empWorkExpCalendr=new ArrayList<ConfigurationUtilBean>();
	//emp bank add page generation
	private String accountTypeLevel;
	private List<ConfigurationUtilBean>empBankLevels=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean> contactDD;
	private Map<Integer, String> officeMap;
	private ArrayList<ConfigurationUtilBean> contactFormDDBox;
	private ArrayList<ConfigurationUtilBean> contactTextBox;
	private Map<Integer, String> industryMap;
	private ArrayList<ConfigurationUtilBean> contactFileBox;
	private ArrayList<ConfigurationUtilBean> contactDateTimeBox;
	private Map<Integer, String> deptMap;
	private HttpServletRequest request;
	// For File Upload and Image
	public File empLogo;
	private String empLogoContentType;
	private String empLogoFileName;

	public File empDocument;
	private String empDocumentContentType;
	private String empDocumentFileName;
	
	private String subGroupId;
	private String mobOne;
	 private List<ConfigurationUtilBean> bankFileBox;
	 private List<ConfigurationUtilBean> empWorkExpFileBox;
	 private List<ConfigurationUtilBean> empPerFileBox;
	 
	 
	public String beforeEmployeeCreate()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			if(getEmpModuleFalgForDeptSubDept()!=null)
			{
				//set the employee basic , prsnl , qualification or bank detail accordin based on the selected level of configuration
				StringBuilder empuery=new StringBuilder("");
				empuery.append("select table_name from mapped_employee_basic_configuration");
				List  empData=cbt.executeAllSelectQuery(empuery.toString(),connectionSpace);
				
				if(empData!=null)
				{
					for(Iterator it=empData.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 
						 if(obdata!=null)
						 {
							 if(obdata.toString().equalsIgnoreCase("employee_basic_configuration"))
							 {
								 empForAddBasic=1;
							 }
							 else if(obdata.toString().equalsIgnoreCase("employee_personal_configuration"))
							 {
								 empForAddPrsl=1;
							 }
							 else if(obdata.toString().equalsIgnoreCase("employee_work_exprience_configuration"))
							 {
								 empForAddWorkQua=1;
							 }
							 else if(obdata.toString().equalsIgnoreCase("employee_work_exprience_configuration"))
							 {
								 empForAddWorkExpirence=1;
							 }
							 else if(obdata.toString().equalsIgnoreCase("employee_bank_details_configuration"))
							 {
								 empForAddBaank=1;
							 }
						 }
					}
				}
				setViewPageProps();
				
			}
		}
		catch(Exception e)
		{
			 addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			 e.printStackTrace();
			 return ERROR;
		}
		return SUCCESS;
	}
	public void setViewPageProps()
	{
		try
		{
			if(empForAddBasic==1)
			{
				setContactAddPageDataFields();
				setContactDataFieldsToAdd("mapped_emp_contact_configuration","emp_contact_configuration");
			}
			if(empForAddPrsl==1)
			{	 
				List<GridDataPropertyView>empPer=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_personal_configuration");
				ConfigurationUtilBean objEjb=null;
				if(empPer!=null)
				{
					empPerFileBox = new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView gdp:empPer)
					{
					    objEjb=new ConfigurationUtilBean();
						if(gdp.getColType().equalsIgnoreCase("T"))
						{
							if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
							    objEjb.setKey(gdp.getColomnName());
		   					         objEjb.setValue(gdp.getHeadingName());
		   					         objEjb.setColType(gdp.getColType());
		   					         objEjb.setValidationType(gdp.getValidationType());
		   					         if(gdp.getMandatroy()==null)
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
		   							objEjb.setMandatory(true);
		   					      empPerLevels.add(objEjb);
							}
							   
						}
						if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
						 objEjb.setKey(gdp.getColomnName());
						 objEjb.setValue(gdp.getHeadingName());
						 objEjb.setColType(gdp.getColType());
						 objEjb.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objEjb.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objEjb.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objEjb.setMandatory(true);
							empPerFileBox.add(objEjb);
							//System.out.println("empPerFileBox" +empPerFileBox.size());
						}
						
						else if(gdp.getColType().equalsIgnoreCase("Time")){
						    objEjb.setKey(gdp.getColomnName());
	   					         objEjb.setValue(gdp.getHeadingName());
	   					         objEjb.setColType(gdp.getColType());
	   					         objEjb.setValidationType(gdp.getValidationType());
	   					         if(gdp.getMandatroy()==null)
	   							objEjb.setMandatory(false);
	   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   							objEjb.setMandatory(false);
	   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   							objEjb.setMandatory(true);
							empPerCalendr.add(objEjb);
						}
						else if(gdp.getColType().equalsIgnoreCase("D"))
							empNameForOther=gdp.getHeadingName();
					}
				}
			}
			if(empForAddWorkQua==1)
			{
				List<GridDataPropertyView>empQua=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_work_exprience_configuration");
				ConfigurationUtilBean objEjb=null;
				if(empQua!=null)
				{
					empWorkExpFileBox = new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView gdp:empQua)
					{
					    objEjb= new ConfigurationUtilBean();
						if(gdp.getColType().equalsIgnoreCase("T"))
						{
							if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
							    objEjb.setKey(gdp.getColomnName());
		   					         objEjb.setValue(gdp.getHeadingName());
		   					         objEjb.setColType(gdp.getColType());
		   					         objEjb.setValidationType(gdp.getValidationType());
		   					         if(gdp.getMandatroy()==null)
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
		   							objEjb.setMandatory(true);
		   					         empWorkExpLevels.add(objEjb);
							}
						}
						else if(gdp.getColType().equalsIgnoreCase("Time")){
						    objEjb.setKey(gdp.getColomnName());
	   					         objEjb.setValue(gdp.getHeadingName());
	   					         objEjb.setColType(gdp.getColType());
	   					         objEjb.setValidationType(gdp.getValidationType());
	   					         if(gdp.getMandatroy()==null)
	   							objEjb.setMandatory(false);
	   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   							objEjb.setMandatory(false);
	   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   							objEjb.setMandatory(true);
	   					         empWorkExpCalendr.add(objEjb);
						}
						 if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{
							 objEjb.setKey(gdp.getColomnName());
							 objEjb.setValue(gdp.getHeadingName());
							 objEjb.setColType(gdp.getColType());
							 objEjb.setValidationType(gdp.getValidationType());
								if (gdp.getMandatroy() == null)
									objEjb.setMandatory(false);
								else if (gdp.getMandatroy().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if (gdp.getMandatroy().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								empWorkExpFileBox.add(objEjb);
								//System.out.println("empWorkExpFileBox" +empWorkExpFileBox.size());
							}
							
						else if(gdp.getColType().equalsIgnoreCase("D"))
							empNameForOther=gdp.getHeadingName();
					}
				}
			}
			//System.out.println("empForAddWorkExpirence" +empForAddWorkExpirence);
			if(empForAddWorkExpirence==1)
			{
				//System.out.println("empForAddWorkExpirence" +empForAddWorkExpirence);
				List<GridDataPropertyView>empQua=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_work_exprience_configuration");
				ConfigurationUtilBean objEjb=null;
				if(empQua!=null)
				{
					bankFileBox = new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView gdp:empQua)
					{
					    objEjb= new ConfigurationUtilBean();
						if(gdp.getColType().equalsIgnoreCase("T"))
						{
							if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
							    objEjb.setKey(gdp.getColomnName());
		   					         objEjb.setValue(gdp.getHeadingName());
		   					         objEjb.setColType(gdp.getColType());
		   					         objEjb.setValidationType(gdp.getValidationType());
		   					         if(gdp.getMandatroy()==null)
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
		   							objEjb.setMandatory(true);
		   					         empWorkExpLevels.add(objEjb);
							}
						}
						else if(gdp.getColType().equalsIgnoreCase("Time")){
						    objEjb.setKey(gdp.getColomnName());
	   					         objEjb.setValue(gdp.getHeadingName());
	   					         objEjb.setColType(gdp.getColType());
	   					         objEjb.setValidationType(gdp.getValidationType());
	   					         if(gdp.getMandatroy()==null)
	   							objEjb.setMandatory(false);
	   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   							objEjb.setMandatory(false);
	   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   							objEjb.setMandatory(true);
	   					         empWorkExpCalendr.add(objEjb);
						}
							
						else if(gdp.getColType().equalsIgnoreCase("D"))
							empNameForOther=gdp.getHeadingName();
					}
				}
			}
			if(empForAddBaank==1)
			{
				//for bank details
				List<GridDataPropertyView>empBank=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_bank_details_configuration");
				ConfigurationUtilBean objEjb=null;
				if(empBank!=null)
				{
					bankFileBox = new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView gdp:empBank)
					{
					    objEjb=new ConfigurationUtilBean();
						if(gdp.getColType().equalsIgnoreCase("T"))
						{
							if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
							    objEjb.setKey(gdp.getColomnName());
		   					         objEjb.setValue(gdp.getHeadingName());
		   					         objEjb.setColType(gdp.getColType());
		   					         objEjb.setValidationType(gdp.getValidationType());
		   					         if(gdp.getMandatroy()==null)
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("0"))
		   							objEjb.setMandatory(false);
		   						else if(gdp.getMandatroy().equalsIgnoreCase("1"))
		   							objEjb.setMandatory(true);
		   					         empBankLevels.add(objEjb);
							}
						}
						//System.out.println("getColomnName" +gdp.getColomnName());
						 if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
									&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{
							 objEjb.setKey(gdp.getColomnName());
							 objEjb.setValue(gdp.getHeadingName());
							 objEjb.setColType(gdp.getColType());
							 objEjb.setValidationType(gdp.getValidationType());
								if (gdp.getMandatroy() == null)
									objEjb.setMandatory(false);
								else if (gdp.getMandatroy().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if (gdp.getMandatroy().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								bankFileBox.add(objEjb);
								//System.out.println("bankFileBox" +bankFileBox.size());
							}
					
						
						else if(gdp.getColType().equalsIgnoreCase("D") && gdp.getColomnName().equalsIgnoreCase("empName"))
							empNameForOther=gdp.getHeadingName();
						else if(gdp.getColType().equalsIgnoreCase("D"))
							accountTypeLevel=gdp.getHeadingName();
					}
				}
			}
		}
		catch(Exception me)
		{
			me.printStackTrace();
		}
	}

	public void setContactAddPageDataFields() {
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<GridDataPropertyView> gridFields = Configuration
					.getConfigurationData(
							"mapped_common_contact_configuration", accountID,
							connectionSpace, "", 0, "table_name",
							"common_contact_configuration");
			if (gridFields != null) {
				contactDD = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields) {
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")) {
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

						 if (gdp.getColomnName().equalsIgnoreCase(
								"regLevel")) {
							officeMap = new HashMap<Integer, String>();
							List data = cbt
									.executeAllSelectQuery(
											" select levelName from mapped_orgleinfo_level ",
											connectionSpace);

							if (data != null && data.size() > 0
									&& data.get(0) != null) {
								String arr[] = data.get(0).toString()
										.split("#");
								for (int i = 0; i < arr.length; i++) {
									officeMap.put(i + 1, arr[i].toString());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	@SuppressWarnings("rawtypes")
	public void setContactDataFieldsToAdd(String mappedTable, String configTable) {
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<GridDataPropertyView> gridFields = Configuration
					.getConfigurationData(mappedTable, accountID,
							connectionSpace, "", 0, "table_name", configTable);

			if (gridFields != null) {
				deptMap = new HashMap<Integer, String>();
				contactFormDDBox = new ArrayList<ConfigurationUtilBean>();
				contactTextBox = new ArrayList<ConfigurationUtilBean>();
				industryMap = new HashMap<Integer, String>();
				contactFileBox = new ArrayList<ConfigurationUtilBean>();
				contactDateTimeBox = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields) {
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"createdDate")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"createdAt")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"userAccountId")
							&& !gdp.getColomnName().equalsIgnoreCase("flag")) {
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
					} else if (gdp.getColType().trim().equalsIgnoreCase("F")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"createdDate")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"createdAt")) {
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

					} else if (gdp.getColType().trim().equalsIgnoreCase("date")) {
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
					} else if (gdp.getColType().trim().equalsIgnoreCase("D")) {
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

						if (gdp.getColomnName().equalsIgnoreCase("industry")) {
							List data = cbt.executeAllSelectQuery("select id,industry from industrydatalevel1",
											connectionSpace);
							if (data != null && data.size() > 0) {
								for (Iterator iterator = data.iterator(); iterator
										.hasNext();) {
									Object[] object = (Object[]) iterator
											.next();
									if (object[0] != null && object[1] != null) {
										industryMap.put(
												Integer.parseInt(object[0]
														.toString()), object[1]
														.toString());
									}
								}

							}
						} /*else if (getRegLevel() != null) {
							List data = cbt.executeAllSelectQuery(
									"select id,deptName from department where mappedOrgnztnId='"
											+ getRegLevel() + "'",
									connectionSpace);
							if (data != null && data.size() > 0) {
								for (Iterator iterator = data.iterator(); iterator
										.hasNext();) {
									Object[] object = (Object[]) iterator
											.next();
									if (object[0] != null && object[1] != null) {
										deptMap.put(Integer.parseInt(object[0]
												.toString()), object[1]
												.toString());
									}
								}
							}
						}*/
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getEmpModuleFalgForDeptSubDept() {
		return empModuleFalgForDeptSubDept;
	}
	public void setEmpModuleFalgForDeptSubDept(
			String empModuleFalgForDeptSubDept) {
		this.empModuleFalgForDeptSubDept = empModuleFalgForDeptSubDept;
	}
	public int getEmpForAddBasic() {
		return empForAddBasic;
	}
	public void setEmpForAddBasic(int empForAddBasic) {
		this.empForAddBasic = empForAddBasic;
	}
	public int getEmpForAddPrsl() {
		return empForAddPrsl;
	}
	public void setEmpForAddPrsl(int empForAddPrsl) {
		this.empForAddPrsl = empForAddPrsl;
	}
	public int getEmpForAddWorkQua() {
		return empForAddWorkQua;
	}
	public void setEmpForAddWorkQua(int empForAddWorkQua) {
		this.empForAddWorkQua = empForAddWorkQua;
	}
	public int getEmpForAddBaank() {
		return empForAddBaank;
	}
	public void setEmpForAddBaank(int empForAddBaank) {
		this.empForAddBaank = empForAddBaank;
	}
	public List<ConfigurationUtilBean> getEmpPerLevels() {
		return empPerLevels;
	}
	public void setEmpPerLevels(List<ConfigurationUtilBean> empPerLevels) {
		this.empPerLevels = empPerLevels;
	}
	public List<ConfigurationUtilBean> getEmpPerCalendr() {
		return empPerCalendr;
	}
	public void setEmpPerCalendr(List<ConfigurationUtilBean> empPerCalendr) {
		this.empPerCalendr = empPerCalendr;
	}
	public String getEmpNameForOther() {
		return empNameForOther;
	}
	public void setEmpNameForOther(String empNameForOther) {
		this.empNameForOther = empNameForOther;
	}
	public List<ConfigurationUtilBean> getEmpWorkExpLevels() {
		return empWorkExpLevels;
	}
	public void setEmpWorkExpLevels(List<ConfigurationUtilBean> empWorkExpLevels) {
		this.empWorkExpLevels = empWorkExpLevels;
	}
	public List<ConfigurationUtilBean> getEmpWorkExpCalendr() {
		return empWorkExpCalendr;
	}
	public void setEmpWorkExpCalendr(List<ConfigurationUtilBean> empWorkExpCalendr) {
		this.empWorkExpCalendr = empWorkExpCalendr;
	}
	public String getAccountTypeLevel() {
		return accountTypeLevel;
	}
	public void setAccountTypeLevel(String accountTypeLevel) {
		this.accountTypeLevel = accountTypeLevel;
	}
	public List<ConfigurationUtilBean> getEmpBankLevels() {
		return empBankLevels;
	}
	public void setEmpBankLevels(List<ConfigurationUtilBean> empBankLevels) {
		this.empBankLevels = empBankLevels;
	}
	public List<ConfigurationUtilBean> getContactDD() {
		return contactDD;
	}
	public void setContactDD(List<ConfigurationUtilBean> contactDD) {
		this.contactDD = contactDD;
	}
	public Map<Integer, String> getOfficeMap() {
		return officeMap;
	}
	public void setOfficeMap(Map<Integer, String> officeMap) {
		this.officeMap = officeMap;
	}
	public ArrayList<ConfigurationUtilBean> getContactFormDDBox() {
		return contactFormDDBox;
	}
	public void setContactFormDDBox(
			ArrayList<ConfigurationUtilBean> contactFormDDBox) {
		this.contactFormDDBox = contactFormDDBox;
	}
	public ArrayList<ConfigurationUtilBean> getContactTextBox() {
		return contactTextBox;
	}
	public void setContactTextBox(ArrayList<ConfigurationUtilBean> contactTextBox) {
		this.contactTextBox = contactTextBox;
	}
	public Map<Integer, String> getIndustryMap() {
		return industryMap;
	}
	public void setIndustryMap(Map<Integer, String> industryMap) {
		this.industryMap = industryMap;
	}
	public ArrayList<ConfigurationUtilBean> getContactFileBox() {
		return contactFileBox;
	}
	public void setContactFileBox(ArrayList<ConfigurationUtilBean> contactFileBox) {
		this.contactFileBox = contactFileBox;
	}
	public ArrayList<ConfigurationUtilBean> getContactDateTimeBox() {
		return contactDateTimeBox;
	}
	public void setContactDateTimeBox(
			ArrayList<ConfigurationUtilBean> contactDateTimeBox) {
		this.contactDateTimeBox = contactDateTimeBox;
	}
	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
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
	public String getSubGroupId() {
		return subGroupId;
	}
	public void setSubGroupId(String subGroupId) {
		this.subGroupId = subGroupId;
	}
	public String getMobOne() {
		return mobOne;
	}
	public void setMobOne(String mobOne) {
		this.mobOne = mobOne;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;

	}
	public List<ConfigurationUtilBean> getBankFileBox() {
		return bankFileBox;
	}
	public void setBankFileBox(List<ConfigurationUtilBean> bankFileBox) {
		this.bankFileBox = bankFileBox;
	}
	public int getEmpForAddWorkExpirence() {
		return empForAddWorkExpirence;
	}
	public void setEmpForAddWorkExpirence(int empForAddWorkExpirence) {
		this.empForAddWorkExpirence = empForAddWorkExpirence;
	}
	public List<ConfigurationUtilBean> getEmpWorkExpFileBox() {
		return empWorkExpFileBox;
	}
	public void setEmpWorkExpFileBox(List<ConfigurationUtilBean> empWorkExpFileBox) {
		this.empWorkExpFileBox = empWorkExpFileBox;
	}
	public List<ConfigurationUtilBean> getEmpPerFileBox() {
		return empPerFileBox;
	}
	public void setEmpPerFileBox(List<ConfigurationUtilBean> empPerFileBox) {
		this.empPerFileBox = empPerFileBox;
	}
	
	
}
