package com.Over2Cloud.ctrl.compliance.complContacts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ContactsMappingAction extends ActionSupport implements ServletRequestAware
{
	@SuppressWarnings("rawtypes")
    Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String deptLevel = (String) session.get("userDeptLevel");
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceContactsAction.class);
	@SuppressWarnings("unused")
    private HttpServletRequest request;
	private List<ConfigurationUtilBean> complTaskTxtBox=null;
	private List<ConfigurationUtilBean> complTaskTxtBox1=null;
	private Map<Integer, String> deptMap = null;
	private Map<Integer, String> empMap = null;
	private List<ConfigurationUtilBean> commonDropDown = null;
	private Map<String,String > columnMap;
	private List<GridDataPropertyView> masterViewProp=new ArrayList<GridDataPropertyView>();
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private List<Object> masterViewList1;
	private Map<String,String> level = null;
	private Map<Integer, String> employeeList;
	private String destination;
	private String fromDept;
	private String forDept;
	private String empName;
	private String selectedId;
	private String userDeptId;
	private String userDeptName;
	private String forDeptId;
	private String moduleName;
	private String mappedLevel;
	private JSONArray jsonObjArray;
	private String contactId;
	private String mapping_sharing;
	private String deptName;
	private Map<String, String> groupMap = null;
	private JSONArray levelValueForMapp = null;
	private Map<String, String> moduleList=null;
	
	public String beforeMappedContact()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				level=new LinkedHashMap<String, String>();
				level=getLevelList(moduleName);
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				TableColumes ob1;
				
				ob1=new TableColumes();
				ob1.setColumnname("mapped_with");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("contact_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				cbt.createTable22("contact_mapping_detail",Tablecolumesaaa,connectionSpace);
				getLogedUserDeptName();
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method beforeMappedContact of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public Map<String, String> getLevelList(String dataFor)
	{
		Map<String, String> levelMap=new LinkedHashMap<String, String>();
		if(dataFor!=null)
		{
			int lastCount=0;
			
			lastCount=5;
			for(int i=2;i<=lastCount;i++)
			{
				levelMap.put(String.valueOf(i),"Level "+i);
			}
		}
		return levelMap;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public String getMappedEmp()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				if(moduleName!=null && mappedLevel!=null)
				{
					ComplianceContactsAction cca = new ComplianceContactsAction();
					String dept_subDeptId=cca.getDept_SubdeptId(userName,deptLevel,moduleName);
					if(dept_subDeptId!=null)
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						jsonObjArray = new JSONArray();
						StringBuilder qString = new StringBuilder();
					    qString.append("SELECT  cc.id,eb.empName FROM compliance_contacts AS cc ");
					    qString.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.forDept_id="+dept_subDeptId);
					    qString.append(" AND cc.moduleName='"+moduleName+"' AND level='"+mappedLevel+"' ORDER BY eb.empName");
					    ////System.out.println("Query String  "+qString.toString());
					    List data = cbt.executeAllSelectQuery(qString.toString(), connectionSpace);
	                    if (data!=null && data.size()>0) 
	                    {
	                    	for (Iterator iterator = data.iterator(); iterator.hasNext();) 
	                    	{
								Object[] object = (Object[]) iterator.next();
								if (object[0]!=null ||object[1]!=null) 
								{
									JSONObject jsonObj=new JSONObject();
									jsonObj.put("id", object[0].toString());
									jsonObj.put("name", object[1].toString());
									jsonObjArray.add(jsonObj);
								}
							}
						}
					}
				}
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getMappedEmp of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	
	public String beforeGetUnMappedEmp()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
	        	GridDataPropertyView gpv=new GridDataPropertyView();

	        	gpv.setColomnName("id");
	     		gpv.setHeadingName("id");
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("true");
	     		gpv.setFormatter("false");
	     		masterViewProp.add(gpv); 
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("empName");
	     		if(mapping_sharing!=null)
	     		{
	     			if(mapping_sharing.equalsIgnoreCase("contactMapping"))
	     			{
	     				gpv.setHeadingName("Unmapped Person");
	     			}
	     			else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
	     			{
	     				gpv.setHeadingName("Unshared Person");
	     			}
	     		}
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("false");
	     		gpv.setFormatter("false");
	     		gpv.setAlign("center");
	     		masterViewProp.add(gpv);
	     		
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("groupName");
	     		gpv.setHeadingName("Contact Type");
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("false");
	     		gpv.setFormatter("false");
	     		gpv.setAlign("center");
	     		masterViewProp.add(gpv);
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("designation");
	     		gpv.setHeadingName("Designation");
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("false");
	     		gpv.setFormatter("false");
	     		gpv.setAlign("center");
	     		masterViewProp.add(gpv);
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("mobOne");
	     		gpv.setHeadingName("Mobile No");
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("false");
	     		gpv.setFormatter("false");
	     		masterViewProp.add(gpv);
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("emailIdOne");
	     		gpv.setHeadingName("E-Mail");
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("true");
	     		gpv.setFormatter("false");
	     		gpv.setAlign("center");
	     		masterViewProp.add(gpv);
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("level");
	     		gpv.setHeadingName("Level");
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		if(mapping_sharing!=null)
	     		{
	     			if(mapping_sharing.equalsIgnoreCase("contactMapping"))
	     			{
	     				gpv.setHideOrShow("false");
	     			}
	     			else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
	     			{
	     				gpv.setHideOrShow("true");
	     			}
	     		}
	     		gpv.setFormatter("false");
	     		masterViewProp.add(gpv); 
	     		
	     		gpv=new GridDataPropertyView();
	     		gpv.setColomnName("otherMapped");
	     		if(mapping_sharing!=null)
	     		{
	     			if(mapping_sharing.equalsIgnoreCase("contactMapping"))
	     			{
	     				gpv.setHeadingName("Already Mapped With");
	     			}
	     			else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
	     			{
	     				gpv.setHeadingName("Already Shared With");
	     			}
	     		}
	     		
	     		gpv.setEditable("true");
	     		gpv.setSearch("true");
	     		gpv.setHideOrShow("false");
	     		gpv.setFormatter("false");
	     		gpv.setAlign("center");
	     		masterViewProp.add(gpv);
	     		
	     		returnResult=SUCCESS;
	         
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method beforeGetUnMappedEmp of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
		@SuppressWarnings({  "rawtypes", "unused" })
		public String getUnMappedEmp()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag) 
			{
				try 
				{
					ComplianceContactsAction cca = new ComplianceContactsAction();
					String dept_subDeptId=cca.getDept_SubdeptId(userName,deptLevel,moduleName);
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					List<Object> Listhb=new ArrayList<Object>();
					StringBuilder query1=new StringBuilder("");
					List  dataCount=new ArrayList();
					if(dept_subDeptId!=null && !dept_subDeptId.equalsIgnoreCase(" "))
					{
						if(mapping_sharing!=null)
						{
							if(mapping_sharing.equalsIgnoreCase("contactMapping"))
							{
								query1.append("SELECT  COUNT(eb.id) FROM compliance_contacts AS cc");
								query1.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.forDept_id="+dept_subDeptId);
								query1.append(" AND cc.moduleName='"+moduleName+"' AND level<"+mappedLevel+" AND cc.id");
								query1.append(" NOT IN(SELECT contact_id FROM contact_mapping_detail WHERE mapped_with="+empName+") AND cc.work_status!=1 ORDER BY eb.empName");
							}
							else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
							{
								query1.append("SELECT  COUNT(eb.id) FROM compliance_contacts AS cc");
								query1.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.forDept_id="+dept_subDeptId);
								query1.append(" AND cc.moduleName='"+moduleName+"' AND level="+mappedLevel+" AND cc.id!="+empName+" AND cc.id");
								query1.append(" NOT IN(SELECT contact_id FROM contact_sharing_detail WHERE sharing_with="+empName+") AND cc.work_status!=1 ORDER BY eb.empName");
							}
							dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
						}
					}
					if(dataCount!=null && dataCount.size()>0)
					{
						BigInteger count=new BigInteger("3");
						Object obdata1 = null;
						for(Iterator it=dataCount.iterator(); it.hasNext();)
						{
							 obdata1=(Object)it.next();
							 count=(BigInteger)obdata1;
						}
						setRecords(count.intValue());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						
						//getting the list of colmuns
						StringBuilder query=new StringBuilder("");
						if(mapping_sharing.equalsIgnoreCase("contactMapping"))
						{
							query.append("SELECT  cc.id,eb.empName,eb.mobOne,eb.emailIdOne,cc.level,eb.designation,grp.groupName  FROM compliance_contacts AS cc");
							query.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id INNER JOIN groupinfo AS grp ON grp.id=eb.groupId WHERE cc.forDept_id="+dept_subDeptId);
							query.append(" AND cc.moduleName='"+moduleName+"' AND level<"+mappedLevel+" AND cc.id");
							query.append(" NOT IN(SELECT contact_id FROM contact_mapping_detail WHERE mapped_with="+empName+") AND cc.work_status=0");
							query.append(" ORDER BY eb.empName");
						}
						else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
						{
							query.append("SELECT  cc.id,eb.empName,eb.mobOne,eb.emailIdOne,cc.level,eb.designation,grp.groupName FROM compliance_contacts AS cc");
							query.append(" INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id INNER JOIN groupinfo AS grp ON grp.id=eb.groupId WHERE cc.forDept_id="+dept_subDeptId);
							query.append(" AND cc.moduleName='"+moduleName+"' AND level="+mappedLevel+" AND cc.id!="+empName+" AND cc.id");
							query.append(" NOT IN(SELECT contact_id FROM contact_sharing_detail WHERE sharing_with="+empName+") AND cc.work_status=0");
							query.append(" ORDER BY eb.empName");
						}
					    List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(data!=null)
						{
							Object[] obdata = null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								obdata=(Object[])it.next();
								Map<String, Object> one=new LinkedHashMap<String, Object>();
								if(obdata[0]!=null)
								{
									one.put("id",obdata[0].toString());
									StringBuilder query2=new StringBuilder("");
									if(mapping_sharing.equalsIgnoreCase("contactMapping"))
									{
										query2.append("SELECT eb.empName FROM employee_basic AS eb ");
										query2.append("INNER JOIN compliance_contacts AS cc ON cc.emp_id=eb.id ");
										query2.append("INNER JOIN contact_mapping_detail AS cmd ON cmd.mapped_with=cc.id ");
										query2.append("WHERE cmd.contact_id="+obdata[0].toString());
									}
									else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
									{
										query2.append("SELECT eb.empName FROM employee_basic AS eb ");
										query2.append("INNER JOIN compliance_contacts AS cc ON cc.emp_id=eb.id ");
										query2.append("INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id ");
										query2.append("WHERE csd.contact_id="+obdata[0].toString());
									}
									List  dataList=cbt.executeAllSelectQuery(query2.toString(),connectionSpace);
									StringBuilder empBuilder=new StringBuilder();
									if(dataList!=null && dataList.size()>0)
									{
										for(int i=0;i<dataList.size();i++)
										{
											empBuilder.append(dataList.get(i)+", ");
										}
										if(dataList!=null)
										{
											String complianceId=empBuilder.substring(0,(empBuilder.toString().length()-2));
											one.put("otherMapped",complianceId);
										}
										else
										{
											one.put("otherMapped","NA");
										}
									}
									else
									{
										one.put("otherMapped","NA");
									}
								}
								else
								{
									one.put("id","NA");
								}
								if(obdata[1]!=null)
									one.put("empName",obdata[1].toString());
								else
									one.put("empName","NA");
								
								if(obdata[2]!=null)
									one.put("mobOne",obdata[2].toString());
								else
									one.put("mobOne","NA");
								
								if(obdata[3]!=null)
									one.put("emailIdOne",obdata[3].toString());
								else
									one.put("emailIdOne","NA");
								
								if(obdata[4]!=null)
									one.put("level","Level "+obdata[4].toString());
								else
									one.put("level","NA");
								
								if(obdata[5]!=null)
									one.put("designation",obdata[5].toString());
								else
									one.put("designation","NA");
								
								if(obdata[6]!=null)
									one.put("groupName",obdata[6].toString());
								else
									one.put("groupName","NA");
								
								Listhb.add(one);
							}
							setMasterViewList1(Listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
				}
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getUnMappedEmp of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
		
	public String mapUnMappedContact()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				InsertDataTable ob=null;
				boolean status=false ;
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				if(contactId!=null)
	  			{
					if(mapping_sharing!=null)
					{
						if(mapping_sharing.equalsIgnoreCase("contactMapping"))
						{
							ob=new InsertDataTable();
							ob.setColName("mapped_with");
							ob.setDataName(empName);
							insertData.add(ob);
							
			  				String contactArr[]=contactId.split(",");
			  				for(int i=0;i<contactArr.length;i++)
							{
								 ob=new InsertDataTable();
								 ob.setColName("contact_id");
								 ob.setDataName(contactArr[i]);
								 insertData.add(ob);
								 status=cbt.insertIntoTable("contact_mapping_detail",insertData,connectionSpace);
								 insertData.remove(insertData.size()-1);
							}
			  				if(status)
				  			{
				  				addActionMessage("Contact mapped successfully!!!");
				  				returnResult=SUCCESS;
				  			}
				  			else
				  			{
				  				addActionMessage("There is some error in data mapping!");
				  				returnResult=ERROR;
						  	}
						}
						else if(mapping_sharing.equalsIgnoreCase("contactSharing"))
						{
							ob=new InsertDataTable();
							ob.setColName("sharing_with");
							ob.setDataName(empName);
							insertData.add(ob);
							
			  				String contactArr[]=contactId.split(",");
			  				for(int i=0;i<contactArr.length;i++)
							{
								 ob=new InsertDataTable();
								 ob.setColName("contact_id");
								 ob.setDataName(contactArr[i]);
								 insertData.add(ob);
								 status=cbt.insertIntoTable("contact_sharing_detail",insertData,connectionSpace);
								 insertData.remove(insertData.size()-1);
							}
			  				if(status)
				  			{
				  				addActionMessage("Contact shared successfully!!!");
				  				returnResult=SUCCESS;
				  			}
				  			else
				  			{
				  				addActionMessage("There is some error in data sharing!");
				  				returnResult=ERROR;
						  	}
						}
					}
	  			}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method mapUnMappedContact of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public String createContactSharing()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				level=new LinkedHashMap<String, String>();
				commonDropDown=new ArrayList<ConfigurationUtilBean>();
				
				ConfigurationUtilBean conUtilBean=null;
				conUtilBean=new ConfigurationUtilBean();
				conUtilBean.setField_name("Level");
				conUtilBean.setField_value("level");
				conUtilBean.setColType("D");
				conUtilBean.setMandatory(true);	
				commonDropDown.add(conUtilBean);
				
				conUtilBean=new ConfigurationUtilBean();
				conUtilBean.setField_name("Mapped Employee");
				conUtilBean.setField_value("emp");
				conUtilBean.setColType("D");
				conUtilBean.setMandatory(true);	
				commonDropDown.add(conUtilBean);
				
				conUtilBean=new ConfigurationUtilBean();
				conUtilBean.setField_name("Share With");
				conUtilBean.setField_value("shareWith");
				conUtilBean.setColType("D");
				conUtilBean.setMandatory(true);	
				commonDropDown.add(conUtilBean);
				
				if(moduleName!=null)
				{
					int lastCount=0;
					
					lastCount=5;
					for(int i=1;i<=lastCount;i++)
					{
						level.put(String.valueOf(i),"Level "+i);
					}
				}
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				TableColumes ob1;
				
				ob1=new TableColumes();
				ob1.setColumnname("sharing_with");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1=new TableColumes();
				ob1.setColumnname("contact_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				cbt.createTable22("contact_sharing_detail",Tablecolumesaaa,connectionSpace);
				getLogedUserDeptName();
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method createContactSharing of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	public String getNameofModule()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method beforeMappedContact of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	public String beforeGetContactSharing()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try
			{
				getLogedUserDeptName();
				groupMap=new LinkedHashMap<String, String>();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String query="SELECT gp.id,gp.groupName FROM groupinfo AS gp INNER JOIN employee_basic AS emp ON gp.id=emp.groupId ORDER BY gp.groupName";
				List data=cbt.executeAllSelectQuery(query, connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							groupMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
				moduleList=new LinkedHashMap<String, String>();
				moduleList=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
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
	
	@SuppressWarnings("rawtypes")
    public String getLogedUserDeptName()
	{
		try
		{
			deptName=new ComplianceUniversalAction().getEmpDataByUserName(userName)[4];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return deptName;
	}
	
	@SuppressWarnings({"rawtypes" })
	public String getEmpContactLevel()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String dept_subDeptId=null;
				ComplianceContactsAction cca = new ComplianceContactsAction();
				dept_subDeptId= cca.getDept_SubdeptId(userName,"1",moduleName);
				List<Object> Listhb=new ArrayList<Object>();
				StringBuilder query1=new StringBuilder("");
				if(dept_subDeptId!=null && !dept_subDeptId.equalsIgnoreCase(""))
				{
					query1.append("SELECT  COUNT(cc.id) " +
							"FROM contact_sharing_detail AS csd " +
							"INNER JOIN compliance_contacts AS cc ON cc.id=csd.sharing_with " +
							"INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id " +
							"WHERE cc.work_status!=1 and cc.forDept_id='"+dept_subDeptId+"' " +
							"and cc.moduleName='"+moduleName+"'" +
							"GROUP BY csd.sharing_with" );
				}
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null)
				{
					BigInteger count=new BigInteger("3");
					Object obdata = null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					
					StringBuilder query=new StringBuilder("");
					query.append("SELECT  cc.id,eb.empName,cc.level,eb.mobOne,eb.emailIdOne,eb.designation,grp.groupName " +
							"FROM contact_sharing_detail AS csd " +
							"INNER JOIN compliance_contacts AS cc ON cc.id=csd.sharing_with " +
							"INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id " +
							"INNER JOIN groupinfo AS grp ON grp.id=eb.groupId "+
							"WHERE cc.work_status!=1 and cc.forDept_id='"+dept_subDeptId+"' " +
							"and cc.moduleName='"+moduleName+"'" +
							" GROUP BY csd.sharing_with ORDER BY eb.empName" );
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						Object[] object = null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							object = (Object[]) it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							
							if(object[0]!=null)
								one.put("id",object[0].toString());
							else 
								one.put("id","NA");
							
							if(object[1]!=null)
								one.put("mappedEmpName",object[1].toString());
							else 
								one.put("mappedEmpName","NA");
							
							if(object[2]!=null)
								one.put("level","Level "+object[2].toString());
							else 
								one.put("level","NA");
							
							if(object[4]!=null)
								one.put("mappedEmpMob",object[3].toString());
							else 
								one.put("mappedEmpMob","NA");
							
							if(object[4]!=null)
								one.put("mappedEmpEmail",object[4].toString());
							else 
								one.put("mappedEmpEmail","NA");
							
							if(object[5]!=null)
								one.put("mappedDesignation",object[5].toString());
							else 
								one.put("mappedDesignation","NA");
							
							if(object[6]!=null)
								one.put("mappedGroupName",object[6].toString());
							else 
								one.put("mappedGroupName","NA");
							
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						returnResult=SUCCESS;
					}
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method getContactLevel of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public String fetchLevel()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				levelValueForMapp = new JSONArray();
				JSONObject ob;
				StringBuilder query =new StringBuilder();
				query.append("SELECT ctm.level  FROM compliance_contacts AS ctm ");
				query.append(" LEFT JOIN contact_mapping_detail AS cmd ON cmd.mapped_with=ctm.id  ");
				query.append(" INNER JOIN employee_basic AS emp ON ctm.emp_id=emp.id   ");
				query.append(" INNER JOIN department AS dept ON ctm.forDept_id=emp.deptname   ");
				query.append(" INNER JOIN groupinfo AS gi ON emp.groupId=gi.id    ");
				query.append(" WHERE  gi.id= ");
			    query.append(selectedId);
			    query.append(" GROUP BY level");
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob=new JSONObject();
						Object object = (Object) iterator.next();
						ob.put("id", object.toString());
						ob.put("name",object.toString());
						levelValueForMapp.add(ob);
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
	
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
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
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public List<ConfigurationUtilBean> getComplTaskTxtBox() {
		return complTaskTxtBox;
	}
	public void setComplTaskTxtBox(List<ConfigurationUtilBean> complTaskTxtBox) {
		this.complTaskTxtBox = complTaskTxtBox;
	}
	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}
	public List<ConfigurationUtilBean> getCommonDropDown() {
		return commonDropDown;
	}
	public void setCommonDropDown(List<ConfigurationUtilBean> commonDropDown) {
		this.commonDropDown = commonDropDown;
	}
	public Map<Integer, String> getEmpMap() {
		return empMap;
	}
	public void setEmpMap(Map<Integer, String> empMap) {
		this.empMap = empMap;
	}
	public Map<Integer, String> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(Map<Integer, String> employeeList) {
		this.employeeList = employeeList;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getFromDept() {
		return fromDept;
	}
	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	public String getUserDeptId() {
		return userDeptId;
	}
	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}
	public String getUserDeptName() {
		return userDeptName;
	}
	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}
	public String getForDeptId() {
		return forDeptId;
	}
	public void setForDeptId(String forDeptId) {
		this.forDeptId = forDeptId;
	}
	public String getForDept() {
		return forDept;
	}
	public void setForDept(String forDept) {
		this.forDept = forDept;
	}
	public List<ConfigurationUtilBean> getComplTaskTxtBox1() {
		return complTaskTxtBox1;
	}
	public void setComplTaskTxtBox1(List<ConfigurationUtilBean> complTaskTxtBox1) {
		this.complTaskTxtBox1 = complTaskTxtBox1;
	}
	public Map<String, String> getLevel() {
		return level;
	}
	public void setLevel(Map<String, String> level) {
		this.level = level;
	}
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getMappedLevel() {
		return mappedLevel;
	}
	public void setMappedLevel(String mappedLevel) {
		this.mappedLevel = mappedLevel;
	}
	public JSONArray getJsonObjArray() {
		return jsonObjArray;
	}
	public void setJsonObjArray(JSONArray jsonObjArray) {
		this.jsonObjArray = jsonObjArray;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getMapping_sharing() {
		return mapping_sharing;
	}
	public void setMapping_sharing(String mapping_sharing) {
		this.mapping_sharing = mapping_sharing;
	}
	public List<Object> getMasterViewList1() {
		return masterViewList1;
	}
	public void setMasterViewList1(List<Object> masterViewList1) {
		this.masterViewList1 = masterViewList1;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Map<String, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap) {
		this.groupMap = groupMap;
	}

	public JSONArray getLevelValueForMapp() {
		return levelValueForMapp;
	}

	public void setLevelValueForMapp(JSONArray levelValueForMapp) {
		this.levelValueForMapp = levelValueForMapp;
	}

	public  Map<String, String> getModuleList() {
		return moduleList;
	}

	public void setModuleList( Map<String, String> moduleList) {
		this.moduleList = moduleList;
	}
	
	
}
