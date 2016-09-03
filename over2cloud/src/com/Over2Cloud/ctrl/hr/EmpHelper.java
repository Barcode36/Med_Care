package com.Over2Cloud.ctrl.hr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class EmpHelper extends ActionSupport
{
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	String encryptedID=(String)session.get("encryptedID");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");

	private String mobTwo;
	private Map<String, String> docMap;
	private String documentName;
	public void fetchConfigurationForEmp(String mappedTable, String accountID, SessionFactory connectionSpace, String configurationTable,
			List<GridDataPropertyView> listData, boolean isWithId)
	{

		GridDataPropertyView gpv = null;
		if (isWithId)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			listData.add(gpv);
		}

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name",
				configurationTable);
		for (GridDataPropertyView gdp : returnResult)
		{
			if (gdp.getColomnName().equalsIgnoreCase("userName") || gdp.getColomnName().equalsIgnoreCase("date") || gdp.getColomnName().equalsIgnoreCase("time")) continue;

			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setAlign(gdp.getAlign());
			if (gdp.getFormatter() != null && !gdp.getFormatter().equalsIgnoreCase(""))
			{
				gpv.setFormatter(gdp.getFormatter());
			}

			listData.add(gpv);
		}
	}

	public Map<String, String> empBasicFullView(SessionFactory connectionSpace, int id, String mobile, String accountID)
	{
		Map<String, String> mappedFields = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_emp_contact_configuration", accountID, connectionSpace, "", 0,
					"table_name", "emp_contact_configuration");
			StringBuilder queryTemp = new StringBuilder();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			if (returnResult != null && !returnResult.isEmpty())
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				for (GridDataPropertyView gp : returnResult)
				{
					if (gp.getColomnName().equalsIgnoreCase("empName")||gp.getColomnName().equalsIgnoreCase("time") || gp.getColomnName().equalsIgnoreCase("date") || gp.getColomnName().equalsIgnoreCase("userName")|| gp.getColomnName().equalsIgnoreCase("empLogo") || gp.getColomnName().equalsIgnoreCase("empDocument"))
					{
						
						continue;
					}
					mappedFields.put(gp.getHeadingName(), gp.getColomnName());
					queryTemp.append(",ep.");
					queryTemp.append(gp.getColomnName());
				}

				query.append(queryTemp.toString().substring(1));
				query.append(" from employee_basic as ep  where ep.id=");
				query.append(id);
				System.out.println("query >?>>>>>>" +query);
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && !list.isEmpty())
				{
					Object[] obAray = (Object[]) list.get(0);
					int i = 0;
					for (Map.Entry<String, String> entry : mappedFields.entrySet())
					{
						entry.setValue(getFieldValue(obAray[i++]));
					}
				}
				else
				{
					mappedFields.clear();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mappedFields;
	}
	
	
	public Map<String, String> empPersonalFullView(SessionFactory connectionSpace, int id, String mobile, String accountID)
	{
		Map<String, String> mappedFields = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_employee_basic_configuration", accountID, connectionSpace, "", 0,
					"table_name", "employee_personal_configuration");
			StringBuilder queryTemp = new StringBuilder();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			if (returnResult != null && !returnResult.isEmpty())
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				for (GridDataPropertyView gp : returnResult)
				{
					if (gp.getColomnName().equalsIgnoreCase("empName")||gp.getColomnName().equalsIgnoreCase("time") || gp.getColomnName().equalsIgnoreCase("date") || gp.getColomnName().equalsIgnoreCase("userName"))
					{
						
						continue;
					}
					mappedFields.put(gp.getHeadingName(), gp.getColomnName());
					queryTemp.append(",ep.");
					queryTemp.append(gp.getColomnName());
				}

				query.append(queryTemp.toString().substring(1));
				query.append(" from employee_personal as ep inner join employee_basic as eb on eb.mobOne = ep.empName where eb.id=");
				query.append(id);
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && !list.isEmpty())
				{
					Object[] obAray = (Object[]) list.get(0);
					int i = 0;
					for (Map.Entry<String, String> entry : mappedFields.entrySet())
					{
						entry.setValue(getFieldValue(obAray[i++]));
					}
				}
				else
				{
					mappedFields.clear();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mappedFields;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> empBankFullView(SessionFactory connectionSpace, int id, String mobile, String accountID)
	{
		Map<String, String> mappedFields = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_employee_basic_configuration", accountID, connectionSpace, "", 0,
					"table_name", "employee_bank_details_configuration");
			StringBuilder queryTemp = new StringBuilder();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			if (returnResult != null && !returnResult.isEmpty())
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				for (GridDataPropertyView gp : returnResult)
				{
					if (gp.getColomnName().equalsIgnoreCase("empName")||gp.getColomnName().equalsIgnoreCase("time") || gp.getColomnName().equalsIgnoreCase("date") || gp.getColomnName().equalsIgnoreCase("userName")|| gp.getColomnName().equalsIgnoreCase("empDocument"))
					{
						continue;
					}
					mappedFields.put(gp.getHeadingName(), gp.getColomnName());
					queryTemp.append(",ep.");
					queryTemp.append(gp.getColomnName());
				}

				query.append(queryTemp.toString().substring(1));
				query.append(" from employee_bankdetails as ep inner join employee_basic as eb on eb.mobOne = ep.empName where eb.id=");
				query.append(id);
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && !list.isEmpty())
				{
					Object[] obAray = (Object[]) list.get(0);
					int i = 0;
					for (Map.Entry<String, String> entry : mappedFields.entrySet())
					{
						entry.setValue(getFieldValue(obAray[i++]));
					}
				}
				else
				{
					mappedFields.clear();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mappedFields;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> empWorkEmpFullView(SessionFactory connectionSpace, int id, String mobile, String accountID)
	{
		Map<String, String> mappedFields = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_employee_basic_configuration", accountID, connectionSpace, "", 0,
					"table_name", "employee_work_exprience_configuration");
			StringBuilder queryTemp = new StringBuilder();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			if (returnResult != null && !returnResult.isEmpty())
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				for (GridDataPropertyView gp : returnResult)
				{
					if (gp.getColomnName().equalsIgnoreCase("empName")||gp.getColomnName().equalsIgnoreCase("time") || gp.getColomnName().equalsIgnoreCase("date") || gp.getColomnName().equalsIgnoreCase("userName")|| gp.getColomnName().equalsIgnoreCase("empDocument"))
					{
						continue;
					}
					mappedFields.put(gp.getHeadingName(), gp.getColomnName());
					queryTemp.append(",ep.");
					queryTemp.append(gp.getColomnName());
				}

				query.append(queryTemp.toString().substring(1));
				query.append(" from employee_workexpriencedetails as ep inner join employee_basic as eb on eb.mobOne = ep.empName where eb.id=");
				query.append(id);
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && !list.isEmpty())
				{
					Object[] obAray = (Object[]) list.get(0);
					int i = 0;
					for (Map.Entry<String, String> entry : mappedFields.entrySet())
					{
						entry.setValue(getFieldValue(obAray[i++]));
					}
				}
				else
				{
					mappedFields.clear();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mappedFields;
	}
	
public List<EmpBasicPojoBean> empProfessionalFullView(SessionFactory connectionSpace, int id, String mobile, String accountID)
	{
	  EmpBasicPojoBean empbasic=null;
	
	  CommonOperInterface coi = new CommonConFactory().createInterface();
	  List<EmpBasicPojoBean> dataList=new ArrayList<EmpBasicPojoBean>();
		StringBuilder query = new StringBuilder();
		query.append(" select  distinct ep.acadmic,ep.aggregate,ep.subject,ep.year,ep.college,ep.university ");
		query.append(" from employee_qualificationdeatils as ep inner join employee_basic as eb on eb.mobOne = ep.empName  where ep.empName='"+mobile+"'");
		System.out.println(" query  employee_qualificationdeatils " +query);
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
				empbasic =new  EmpBasicPojoBean();
				empbasic.setAcadmic(getFieldValue(object[0]));
				empbasic.setAggregate(getFieldValue(object[1]));
				empbasic.setSubject(getFieldValue(object[2]));
				empbasic.setYear(getFieldValue(object[3]));
				empbasic.setCollege(getFieldValue(object[4]));
				empbasic.setUniversity(getFieldValue(object[5]));
				}
				dataList.add(empbasic);
			}
			
			System.out.println(" dataList SIZE " +dataList.size());
		}
		
		return dataList;
	}
	

public List<EmpBasicPojoBean> empProfessionalDocumentView(SessionFactory connectionSpace, String mobile, String accountID)
{
  EmpBasicPojoBean empbasic=null;

  CommonOperInterface coi = new CommonConFactory().createInterface();
  List<EmpBasicPojoBean> dataList=new ArrayList<EmpBasicPojoBean>();
	StringBuilder query = new StringBuilder();
	query.append(" select  distinct ep.id,ep.empDocument ");
	query.append(" from employee_qualificationdeatils as ep inner join employee_basic as eb on eb.mobOne = ep.empName  where ep.empName='"+mobile+"'");
	System.out.println(" query  employee_qualificationdeatils " +query);
	List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (list != null && list.size() > 0)
	{
		docMap=new LinkedHashMap<String, String>();
		String str=null;
		Object[] object=null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) 
		{
			
			object = (Object[]) iterator.next();
			if (object!=null) 
			{
			empbasic =new  EmpBasicPojoBean();
			
			//empbasic.setEmpDocument(getFieldValue(object[1]).substring(endIndex,StoredTemp.length());
			
			
			//Call Private Method for substring and correct File Name
			
			
			
			empbasic.setEmpDocument(correctFilePath(getFieldValue(object[1])));
			}
			
			dataList.add(empbasic);
		}
		
		
	}
	
	return dataList;
}

public Map<String, String> empBankDocumentView(SessionFactory connectionSpace, String mobile, String accountID)
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append(" select  distinct ep.id,ep.empDocument ");
			query.append(" from employee_bankdetails  as ep inner join employee_basic as eb on eb.mobOne = ep.empName  where ep.empName='"+mobile+"'");
			System.out.println(" query  empDocument " +query);
			List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataCount!=null && dataCount.size()>0)
				{
					docMap=new LinkedHashMap<String, String>();
					String str=null;
					Object[] object=null;
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if(object[1]!=null)
						{
							str=object[1].toString().substring(object[1].toString().lastIndexOf("//")+2, object[1].toString().length());
							System.out.println("str" +str);
							documentName=str;
							System.out.println("documentName" +documentName);
							docMap.put(object[1].toString(),documentName);
							System.out.println("docMap" +docMap);
						}
						
						
					}
					
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	return docMap;
}


public Map<String, String> empBasicDocumentView(SessionFactory connectionSpace, String mobile, String accountID)
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append(" select empDocument from employee_basic ");
			query.append("  where mobOne='"+mobile+"'");
			System.out.println(" query  empDocument " +query);
			List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataCount!=null && dataCount.size()>0)
				{
					docMap=new LinkedHashMap<String, String>();
					String str=null;
					Object[] object=null;
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if(object[0]!=null)
						{
							str=object[0].toString().substring(object[0].toString().lastIndexOf("//")+2, object[0].toString().length());
							System.out.println("str" +str);
							documentName=str;
							System.out.println("documentName" +documentName);
							docMap.put(object[0].toString(),documentName);
							System.out.println("docMap" +docMap);
						}
						
						
					}
					
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	return docMap;
}

public Map<String, String> empPerDocumentView(SessionFactory connectionSpace, String mobile, String accountID)
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append(" select  distinct ep.id,ep.empDocument ");
			query.append(" from employee_personal  as ep inner join employee_basic as eb on eb.mobOne = ep.empName  where ep.empName='"+mobile+"'");
			System.out.println(" query  empDocument " +query);
			List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataCount!=null && dataCount.size()>0)
				{
					docMap=new LinkedHashMap<String, String>();
					String str=null;
					Object[] object=null;
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if(object[1]!=null)
						{
							str=object[1].toString().substring(object[1].toString().lastIndexOf("//")+2, object[1].toString().length());
							System.out.println("str" +str);
							documentName=str;
							System.out.println("documentName" +documentName);
							docMap.put(object[1].toString(),documentName);
							System.out.println("docMap" +docMap);
						}
						
						
					}
					
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	return docMap;
}

public Map<String, String> empworkExpDocumentView(SessionFactory connectionSpace, String mobile, String accountID)
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append(" select  distinct ep.id,ep.empDocument ");
			query.append(" from employee_workexpriencedetails  as ep inner join employee_basic as eb on eb.mobOne = ep.empName  where ep.empName='"+mobile+"'");
			System.out.println(" query  empDocument " +query);
			List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataCount!=null && dataCount.size()>0)
				{
					docMap=new LinkedHashMap<String, String>();
					String str=null;
					Object[] object=null;
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if(object[1]!=null)
						{
							str=object[1].toString().substring(object[1].toString().lastIndexOf("//")+2, object[1].toString().length());
							System.out.println("str" +str);
							documentName=str;
							System.out.println("documentName" +documentName);
							docMap.put(object[1].toString(),documentName);
							System.out.println("docMap" +docMap);
						}
						
						
					}
					
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	return docMap;
}

public String correctFilePath(String prevPath)
{
	String path= "NA";
	
	try
	{
		String filePth = prevPath.substring(prevPath.lastIndexOf("//")+1, prevPath.length());
		
		System.out.println(filePth);
		int endIndex=0;
		
		for(int i =0 ; i < filePth.length() ; i++){
			if( Character.isDigit(filePth.charAt(i))){
				endIndex = i;
			}else{
				break;
			}
			
		}
		path = filePth.substring(endIndex+1,filePth.length());
		System.out.println("path:::" +path);
		
		
		
	}catch(Exception Ex){
		Ex.printStackTrace();
	}
	
	return path;
}

	public static String getFieldValue(Object object)
	{
		return (object == null || object.toString().trim().length() < 1) ? "NA" : object.toString().trim();
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public String[] getEmpId1(String empmob,SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		String[] data=new String[2];
		List dataList = null;
		try
		{
			qryString.append(" SELECT emp.id,emp.mobone FROM  employee_basic AS emp");
			qryString.append(" WHERE emp.mobone='"+empmob+"' ");
			System.out.println("qryString>>>>>getEmpId1>>>>>>>" +qryString);
			
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList!=null && dataList.size()>0) 
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1]!=null && object[0]!=null)
					{
						data[0]=getFieldValue(object[0]);
						data[1]=getFieldValue(object[1]);
						
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

	public Map<String, String> getDocMap() {
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap) {
		this.docMap = docMap;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


}
