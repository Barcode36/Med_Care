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
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.opensymphony.xwork2.ActionContext;

public class EmployeeModification extends GridPropertyBean implements ServletRequestAware

{


	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String excelName=null;
	private List<ConfigurationUtilBean> columnList=null;
	private Map<String, String> columnMap=null;
	HttpServletRequest request;
	
	public String editEmployee()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				if(getOper().equalsIgnoreCase("edit"))
				{
					System.out.println(">>>>>edit>>>>>");
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
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")&& !Parmname.equalsIgnoreCase("takeaction")&& !Parmname.equalsIgnoreCase("flag")&& !Parmname.equalsIgnoreCase("deptname")&& !Parmname.equalsIgnoreCase("groupId")&& !Parmname.equalsIgnoreCase("regLevel"))
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
