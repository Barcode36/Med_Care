package com.Over2Cloud.ctrl.compliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceUniversalAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ConfigureComplianceAction.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String destination;
	private String subDept;
    private String dept;
	boolean checkdept = false;
    private String deptOrSubDeptId;
	private Map<Integer, String> deptList = null;
    private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
	private Map<String, String> subDept_DeptLevelName = null;
	private List<ConfigurationUtilBean> complianceTextBox=null;
	private Map<String, String> complianceCheckBox=null;
	private List<ConfigurationUtilBean> complianceFile=null;
	private List<ConfigurationUtilBean> complianceRadio=null;
	private Map<String, String> complianceDropDown=null;
	private Map<String,String> viaFrom;
	private Map<String,String> complType;
	private Map<String,String> remindToMap;
	private Map<String,String> frequencyMap;

	@Override
	public String execute(){

		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			   returnResult=SUCCESS;				
		 }
		 else
		 {
				returnResult=LOGIN;
		 }
		 return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String firstActionMethod()
	{
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try
				{
					String orgLevel = (String)session.get("orgnztnlevel");
					String orgId = (String)session.get("mappedOrgnztnId");
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			
					deptList = new LinkedHashMap<Integer, String>();
					List departmentlist = new ArrayList();
			
					List colmName = new ArrayList();
					Map<String, Object> order = new HashMap<String, Object>();
					Map<String, Object> wherClause = new HashMap<String, Object>();
			
					List<String>colname=new ArrayList<String>();
					colname.add("orgLevel");
					colname.add("levelName");
					String deptHierarchy = (String) session.get("userDeptLevel");
					if (deptHierarchy.equals("2")) 
					{
						setsubDept_DeptTags(deptHierarchy);
						checkdept=true;
					}
					else if (deptHierarchy.equals("1"))
					{
						setsubDept_DeptTags(deptHierarchy);
						checkdept=false;
					}
			
					colmName.add("id");
					colmName.add("deptName");
					wherClause.put("orgnztnlevel", orgLevel);
					wherClause.put("mappedOrgnztnId", orgId);
					order.put("deptName", "ASC");
					ComplianceCommonOperation co=new ComplianceCommonOperation();
					departmentlist = co.getDataFromTable("department", colmName, wherClause, order, connectionSpace);
					if (departmentlist.size()>0) 
					{
						Object[] object=null;
						for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							deptList.put((Integer)object[0], object[1].toString());
						}
					}
					departmentlist.removeAll(departmentlist);
					
					setAddPageDataFields();
					complType=new TreeMap<String, String>();
					complType.put("1", "Imp:Urget");
					complType.put("2", "Imp:Not Urget");
					complType.put("3", "Not Urget:Imp");
					complType.put("4", "Not Imp:Not Urget");
					
					setComplType(complType);
					viaFrom=new LinkedHashMap<String, String>();
					viaFrom.put("modeSms", "SMS");
					viaFrom.put("modeMail", "Mail");
					viaFrom.put("modeBoth", "Both");
					viaFrom.put("modeNone", "None");
					setViaFrom(viaFrom);
					
					remindToMap=new HashMap<String,String>();
					remindToMap.put("remToSelf","Self");
					remindToMap.put("remToOther","Other");
					remindToMap.put("remToBoth","Both");
					setRemindToMap(remindToMap);
					
					frequencyMap=new HashMap<String, String>();
					frequencyMap.put("recFreq","Recurring");
					frequencyMap.put("oneTimeFreq","One Time");
					setFrequencyMap(frequencyMap);
					
				    returnResult=SUCCESS;				
			}
			catch (Exception exp)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method firstActionMethod of class "+getClass(), exp);
				exp.printStackTrace();
				
			}
		}
		else 
		{
			returnResult=LOGIN;
		}
			return returnResult;
	}
	
	public void setsubDept_DeptTags(String level)
	 {
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
		subDept_DeptLevelName = new LinkedHashMap<String, String>();
		if (deptList!=null && deptList.size()>0)
		 {
			for (GridDataPropertyView gdv : deptList)
			 {
			   if (gdv.getColomnName().equalsIgnoreCase("deptName")) {
				   subDept_DeptLevelName.put(gdv.getColomnName(), gdv.getHeadingName());
			   }
			 }
		}
		if (level.equals("2")) {
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf",accountID,connectionSpace,"",0,"table_name","subdeprtmentconf");	
			if (subdept_deptList!=null && subdept_deptList.size()>0)
			 {
				for (GridDataPropertyView gdv1 : subdept_deptList)
				 {
				   if (gdv1.getColomnName().equalsIgnoreCase("subdeptname")) {
					   subDept_DeptLevelName.put(gdv1.getColomnName(), gdv1.getHeadingName());
				   }
				  
				 }
			}
		}
	 }
	
	 public void setAddPageDataFields()
	 {
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
		 try
			{
				List<GridDataPropertyView>  complianceFieldsName=Configuration.getConfigurationData("mapped_compliance_configuration",accountID,connectionSpace,"",0,"table_name","compliance_configuration");
				if(complianceFieldsName!=null)
				{
					complianceTextBox=new ArrayList<ConfigurationUtilBean>();
					complianceCheckBox=new LinkedHashMap<String, String>();
					complianceFile=new ArrayList<ConfigurationUtilBean>();
					complianceRadio=new ArrayList<ConfigurationUtilBean>();
					complianceDropDown=new LinkedHashMap<String, String>();

					for(GridDataPropertyView  gdp:complianceFieldsName)
					{
						ConfigurationUtilBean conUtilBean=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							complianceTextBox.add(conUtilBean);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("R") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							complianceRadio.add(conUtilBean);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							if(gdp.getMandatroy()!=null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);	
							}
							else
							{
								conUtilBean.setMandatory(false);	
							}
							complianceFile.add(conUtilBean);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("C") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							complianceCheckBox.put(gdp.getColomnName(), gdp.getHeadingName());
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							complianceDropDown.put(gdp.getColomnName(), gdp.getHeadingName());
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		  }
	  }

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String getSubDepartment() 
		 {
			String returnResult= ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
			  try 
			  {
				        SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						List list=new ArrayList<String>();
						list.add("id");
						list.add("subdeptname");
						Map<String, Object> temp=new HashMap<String, Object>();
						Map<String, Object> order=new HashMap<String, Object>();
						order.put("subdeptname", "ASC");
						temp.put("deptid",getDept());
						list=new HelpdeskUniversalHelper().getDataFromTable("subdepartment",list,temp,null,order,connectionSpace);
						if(list.size()>0)
						 {
							for (Object c : list) {
								Object[] dataC = (Object[]) c;
								subDeptList.put((Integer)dataC[0], dataC[1].toString());
							}
						 }
						if (destination!=null) {
							destination = destination.replace("subDeptDiv", "");
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
				returnResult =LOGIN;
			}
			return 	returnResult;
		}

	@SuppressWarnings("unchecked")
	public List getEmpDataByUserName(String uName, String deptLevel)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid,dept.id as deptid, dept.deptName,emp.id as empid,emp.city,uac.userType from employee_basic as emp" + " inner join subdepartment as sdept on emp.subdept=sdept.id" + " inner join department as dept on sdept.deptid=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
						+ uName + "'";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid,emp.city from employee_basic as emp" + " inner join department as dept on emp.deptname=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}
		
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSubDept() {
		return subDept;
	}

	public void setSubDept(String subDept) {
		this.subDept = subDept;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptOrSubDeptId() {
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId) {
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public Map<Integer, String> getSubDeptList() {
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList) {
		this.subDeptList = subDeptList;
	}

	public Map<String, String> getSubDept_DeptLevelName() {
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(Map<String, String> subDept_DeptLevelName) {
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public SessionFactory getConnectionSpace() {
		return connectionSpace;
	}

	public void setConnectionSpace(SessionFactory connectionSpace) {
		this.connectionSpace = connectionSpace;
	}

	public List<ConfigurationUtilBean> getComplianceTextBox() {
		return complianceTextBox;
	}

	public void setComplianceTextBox(List<ConfigurationUtilBean> complianceTextBox) {
		this.complianceTextBox = complianceTextBox;
	}

	public List<ConfigurationUtilBean> getComplianceFile() {
		return complianceFile;
	}

	public void setComplianceFile(List<ConfigurationUtilBean> complianceFile) {
		this.complianceFile = complianceFile;
	}

	public Map<String, String> getComplianceCheckBox() {
		return complianceCheckBox;
	}

	public void setComplianceCheckBox(Map<String, String> complianceCheckBox) {
		this.complianceCheckBox = complianceCheckBox;
	}

	public List<ConfigurationUtilBean> getComplianceRadio() {
		return complianceRadio;
	}

	public void setComplianceRadio(List<ConfigurationUtilBean> complianceRadio) {
		this.complianceRadio = complianceRadio;
	}

	public Map<String, String> getViaFrom() {
		return viaFrom;
	}

	public void setViaFrom(Map<String, String> viaFrom) {
		this.viaFrom = viaFrom;
	}

	public Map<String, String> getComplType() {
		return complType;
	}

	public void setComplType(Map<String, String> complType) {
		this.complType = complType;
	}

	public Map<String, String> getRemindToMap() {
		return remindToMap;
	}

	public void setRemindToMap(Map<String, String> remindToMap) {
		this.remindToMap = remindToMap;
	}

	public Map<String, String> getFrequencyMap() {
		return frequencyMap;
	}

	public void setFrequencyMap(Map<String, String> frequencyMap) {
		this.frequencyMap = frequencyMap;
	}
	public Map<String, String> getComplianceDropDown() {
		return complianceDropDown;
	}

	public void setComplianceDropDown(Map<String, String> complianceDropDown) {
		this.complianceDropDown = complianceDropDown;
	}

 }
