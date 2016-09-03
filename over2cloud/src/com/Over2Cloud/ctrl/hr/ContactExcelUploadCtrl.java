package com.Over2Cloud.ctrl.hr;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.compliance.ComplianceExcelDownload;
import com.Over2Cloud.ctrl.universal.UniversalAction;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ContactExcelUploadCtrl  extends GridPropertyBean implements ServletRequestAware{

	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String excelName=null;
	private List<ConfigurationUtilBean> columnList=null;
	private Map<String, String> columnMap=null;
	HttpServletRequest request;
	
	@SuppressWarnings("rawtypes")
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
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")&& !Parmname.equalsIgnoreCase("flag"))
							wherClause.put(Parmname, paramValue);
						if (Parmname.equalsIgnoreCase("flag"))
						{
							if (paramValue.equalsIgnoreCase("Active"))
							{
								wherClause.put(Parmname, "0");
								StringBuilder query=new StringBuilder();
								query.append(" UPDATE compliance_contacts SET work_status='0' WHERE emp_id IN("+getId()+")");
								System.out.println("employee query ::::::: "+query.toString());
								cbt.updateTableColomn(connectionSpace,query);
							}
							else
							{
								wherClause.put(Parmname, "1");
								StringBuilder query=new StringBuilder();
								query.append(" UPDATE compliance_contacts SET work_status='1' WHERE emp_id IN("+getId()+")");
								System.out.println("employee query ::::::: "+query.toString());
								cbt.updateTableColomn(connectionSpace,query);
							}
							wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
						}
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
					StringBuilder fieldsNames=new StringBuilder();
	        		StringBuilder dataStore=new StringBuilder();
	        		if (wherClause!=null && !wherClause.isEmpty())
					{
						int i=1;
						for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
						{
						    if (i < wherClause.size())
								fieldsNames.append("'"+entry.getKey() + "', ");
							else
								fieldsNames.append("'"+entry.getKey() + "' ");
							i++;
						}
					}
	        		UserHistoryAction UA=new UserHistoryAction();
	        		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"emp_contact_configuration");
	        		if (fieldValue!=null && fieldValue.size()>0)
					{
	        			StringBuilder dataField =new StringBuilder();
						for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (wherClause!=null && !wherClause.isEmpty())
							{
								int i=1;
								for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
								{
									if (object[1].toString().equalsIgnoreCase(entry.getKey()))
									{
										  if (i < fieldValue.size())
										  {
											  dataStore.append(entry.getValue() + ", ");
										      dataField.append(object[0].toString() +", ");
										  }
										  else
										  {
											  dataStore.append(entry.getValue());
										      dataField.append(object[0].toString());
										  }
									}
									i++;
								}
							}
						}
	        			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
	        			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Primary Contact",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
				}
				else if(getOper().equalsIgnoreCase("del"))
				{
					    CommonOperInterface cbt=new CommonConFactory().createInterface();
				
						Map<String, Object>wherClause=new HashMap<String, Object>();
						Map<String, Object>condtnBlock=new HashMap<String, Object>();
						wherClause.put("flag", "1");
						wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
						condtnBlock.put("id",getId());
						cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
						StringBuilder query=new StringBuilder();
						query.append(" UPDATE useraccount SET active='0' WHERE id IN(SELECT useraccountid FROM employee_basic WHERE id IN("+getId()+"))");
						cbt.updateTableColomn(connectionSpace,query);
						query.setLength(0);
						query.append(" UPDATE compliance_contacts SET work_status='1' WHERE emp_id IN("+getId()+")");
						cbt.updateTableColomn(connectionSpace,query);
						
						StringBuilder fieldsNames=new StringBuilder();
		        		StringBuilder dataStore=new StringBuilder();
		        		if (wherClause!=null && !wherClause.isEmpty())
						{
							int i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
							    if (i < wherClause.size())
									fieldsNames.append("'"+entry.getKey() + "', ");
								else
									fieldsNames.append("'"+entry.getKey() + "' ");
								i++;
							}
						}
		        		UserHistoryAction UA=new UserHistoryAction();
		        		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"common_contact_configuration");
		        		if (fieldValue!=null && fieldValue.size()>0)
						{
		        			StringBuilder dataField =new StringBuilder();
		        			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (wherClause!=null && !wherClause.isEmpty())
								{
									int i=1;
									for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
									{
										if (object[1].toString().equalsIgnoreCase(entry.getKey()))
										{
											  if (i < fieldValue.size())
											  {
												  dataStore.append(entry.getValue() + ", ");
											      dataField.append(object[0].toString() +", ");
											  }
											  else
											  {
												  dataStore.append(entry.getValue());
											      dataField.append(object[0].toString());
											  }
										}
										i++;
									}
								}
							}
		        		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
		        		UA.userHistoryAdd(empIdofuser, "Inactive", "Admin", "Primary Contact", dataStore.toString(), dataField.toString(),Integer.parseInt(getId()), connectionSpace);
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
	
	public String downloadFormat()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				
				List<GridDataPropertyView> gridFields=Configuration.getConfigurationData("mapped_common_contact_configuration",accountID,connectionSpace,"",0,"table_name","common_contact_configuration");
				if(gridFields!=null && gridFields.size()>0)
				{
					List fieldNames=new ArrayList();
					for(GridDataPropertyView  gdp:gridFields)
					{
						fieldNames.add(gdp.getHeadingName());
					}
					
					if(fieldNames!=null && fieldNames.size()>0)
					{
						String orgDetail = (String)session.get("orgDetail");
						String[] orgData = null;
						String orgName="";
						if (orgDetail!=null && !orgDetail.equals("")) 
						{
							orgData = orgDetail.split("#");
							orgName=orgData[0];
						}
						excelFileName=new ContactExcelDownload().writeDataInExcel(null,fieldNames,null,"Contact",orgName,false,connectionSpace);
						if(excelFileName!=null)
						{
							excelStream=new FileInputStream(excelFileName);
						}
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}

	public String getColumn4Download()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				returnResult=getColumnName("mapped_common_contact_configuration","common_contact_configuration");
				excelName="Employee Basic Detail";
				
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
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
	public String getColumnName(String mappedTableName,String basicTableName)
	{
			String returnResult=ERROR;
			try
			{
				Map session = ActionContext.getContext().getSession();
				String orgLevel = (String)session.get("orgnztnlevel");
				String orgId = (String)session.get("mappedOrgnztnId");
				String accountID=(String)session.get("accountid");
				String userName=(String)session.get("uName");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				
				List<GridDataPropertyView> colList=Configuration.getConfigurationData(mappedTableName,accountID,connectionSpace,"",0,"table_name",basicTableName);
				columnList=new ArrayList<ConfigurationUtilBean>();
				columnMap=new LinkedHashMap<String, String>();
				if(columnList!=null&&columnList.size()>0)
				{
					String firstValue,secondValue=null;
					for(GridDataPropertyView  gdp:colList)
						{
							if(gdp.getColomnName()!=null)
							{
								ConfigurationUtilBean obj=new ConfigurationUtilBean();
									if(gdp.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey(gdp.getColomnName());
									obj.setValue(gdp.getHeadingName());
									columnList.add(obj);
									columnMap.put(gdp.getColomnName(), gdp.getHeadingName());
							}
						}
					
					Iterator<Entry<String, String>> hiterator=columnMap.entrySet().iterator();
				    while (hiterator.hasNext()) 
				    {
				    	Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				    }
					
					if(columnMap!=null && columnMap.size()>0)
					{
						session.put("columnMap", columnMap);
					}
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return returnResult;
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

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
}
