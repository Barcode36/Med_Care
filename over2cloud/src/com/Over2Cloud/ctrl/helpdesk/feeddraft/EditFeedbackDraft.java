package com.Over2Cloud.ctrl.helpdesk.feeddraft;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class EditFeedbackDraft extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	/*Map session = ActionContext.getContext().getSession();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";*/
	
	private List<Object> subDeptData;
	private List<Object> feedTypeData;
	private List<Object> feedCategoryData;
	private List<Object> feedSubCategoryData;
	
	///////////////////////////////////////////////
	private String dataFor;
	private String viewType;
	//////////////////////////////////////////////
	
	public String beforeFeedbackDraftView()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
		 try {
			if (userName==null || userName.equalsIgnoreCase("")) 
				return LOGIN;
			
			    setGridColomnNames_new(viewType);
				if (viewType!=null && !viewType.equals("") && viewType.equalsIgnoreCase("SD")) {
						returnResult=SUCCESS;	
				}
				else if (viewType!=null && !viewType.equals("") && viewType.equalsIgnoreCase("D")) {
					
						returnResult="deptSUCCESS";	
				}
		  } catch (Exception e) {
			 e.printStackTrace();
		 }
		}
		return returnResult;
	 }
	
	
	// Method for set Column Names for Feedback Draft Module
	public void setGridColomnNames_new(String viewtype)
	 {
		viewPageColumns = new ArrayList<GridDataPropertyView>();
		if (viewtype.equalsIgnoreCase("SD")) 
		 {
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);
				
		    List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_subdeprtmentconf",accountID,connectionSpace,"",0,"table_name","subdeprtmentconf");
		    if(statusColName!=null && statusColName.size()>0)
		      {
			    for(GridDataPropertyView gdp:statusColName)
			     {
				   if (gdp.getColomnName().equals("subdeptname")) {
						gpv=new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setAlign("Left");
						gpv.setWidth(740);
						gpv.setHideOrShow(gdp.getHideOrShow());
						viewPageColumns.add(gpv);
					   }
				    }
			     }
			 }
			
			if (viewtype.equalsIgnoreCase("D") || viewtype.equalsIgnoreCase("SD")) 
			 {
				GridDataPropertyView gpvt = new GridDataPropertyView();
				gpvt.setColomnName("id");
				gpvt.setHeadingName("Id");
				gpvt.setHideOrShow("true");
				viewPageColumns.add(gpvt);
				
				List<GridDataPropertyView> statusColName4FT=Configuration.getConfigurationData("mapped_feedback_type_configuration",accountID,connectionSpace,"",0,"table_name","feedback_type_configuration");
				if(statusColName4FT!=null&&statusColName4FT.size()>0)
				{
					for(GridDataPropertyView gdpft:statusColName4FT)
					{
						if (gdpft.getColomnName().equals("fbType")) {
							gpvt=new GridDataPropertyView();
							gpvt.setColomnName(gdpft.getColomnName());
							gpvt.setHeadingName(gdpft.getHeadingName());
							gpvt.setEditable(gdpft.getEditable());
							gpvt.setSearch(gdpft.getSearch());
							gpvt.setWidth(647);
							gpvt.setAlign("Left");
							gpvt.setHideOrShow(gdpft.getHideOrShow());
							viewPageColumns.add(gpvt);
						}
					}
				}
				
				
				GridDataPropertyView gpvcag = new GridDataPropertyView();
				gpvcag.setColomnName("id");
				gpvcag.setHeadingName("Id");
				gpvcag.setHideOrShow("true");
				viewPageColumns.add(gpvcag);
				
				List<GridDataPropertyView> statusColName4Cag=Configuration.getConfigurationData("mapped_feedback_category_configuration",accountID,connectionSpace,"",0,"table_name","feedback_category_configuration");
				if(statusColName4Cag!=null && statusColName4Cag.size()>0)
				{
					for(GridDataPropertyView gdpcag:statusColName4Cag)
					{
						if (gdpcag.getColomnName().equals("categoryName")) {
							gpvcag=new GridDataPropertyView();
							gpvcag.setColomnName(gdpcag.getColomnName());
							gpvcag.setHeadingName(gdpcag.getHeadingName());
							gpvcag.setEditable(gdpcag.getEditable());
							gpvcag.setSearch(gdpcag.getSearch());
							gpvcag.setWidth(554);
							gpvcag.setAlign("Left");
							gpvcag.setHideOrShow(gdpcag.getHideOrShow());
							viewPageColumns.add(gpvcag);
						}
					}
				}
				
				
				GridDataPropertyView gdpvscag = new GridDataPropertyView();
				gdpvscag.setColomnName("id");
				gdpvscag.setHeadingName("Id");
				gdpvscag.setHideOrShow("true");
				viewPageColumns.add(gdpvscag);
				
				List<GridDataPropertyView> statusColName4SCag=Configuration.getConfigurationData("mapped_feedback_subcategory_configuration",accountID,connectionSpace,"",0,"table_name","feedback_subcategory_configuration");
				if(statusColName4SCag!=null && statusColName4SCag.size()>0)
				{
					for(GridDataPropertyView gdpscag:statusColName4SCag)
					{
						if (!gdpscag.getColomnName().equals("fbType") && !gdpscag.getColomnName().equals("categoryName") && !gdpscag.getColomnName().equals("addressingTime") 
								&& !gdpscag.getColomnName().equals("resolutionTime") && !gdpscag.getColomnName().equals("escalateTime")
								&& !gdpscag.getColomnName().equals("escalationDuration") && !gdpscag.getColomnName().equals("escalationLevel")
								&& !gdpscag.getColomnName().equals("needEsc")) {
							gdpvscag=new GridDataPropertyView();
							gdpvscag.setColomnName(gdpscag.getColomnName());
							gdpvscag.setHeadingName(gdpscag.getHeadingName());
							gdpvscag.setEditable(gdpscag.getEditable());
							gdpvscag.setSearch(gdpscag.getSearch());
							gdpvscag.setWidth(121);
							gdpvscag.setHideOrShow(gdpscag.getHideOrShow());
							viewPageColumns.add(gdpvscag);
						}
					}
				}
			  }
	    }
	
	
	// Method for getting Sub Department Detail (In Use)
	@SuppressWarnings("unchecked")
	public String viewFeedSubDept()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		 {
			try
			{
				subDeptData=new ArrayList<Object>();
				@SuppressWarnings("unused")
				Map<String, List> whereClauseList = new HashMap<String, List>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("subdeptname", "ASC");
				List data=null;
				String dept_id ="";
				String empName="";
				
				List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
				if (empData!=null && empData.size()>0) {
					for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						dept_id = object[3].toString();
						empName = object[0].toString();
					}
				}
				
			    if (dept_id!=null && !dept_id.equals("")) {
					data=new HelpdeskUniversalHelper().getSubDeptListByUIDRetId(session.get("userTpe").toString(), dept_id, empName, connectionSpace);
				}
			    if(data!=null&&data.size()>0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
				}	
					
					List fieldNames= new ArrayList();
					fieldNames.add("id");
					fieldNames.add("subdeptname");
					
					if(data!=null && data.size()>0)
					{
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata[k]!=null)
								{
										if(k==0)
										{
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
										}
										else
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
								}
							}
							Listhb.add(one);
						}
						setSubDeptData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	
	// Method for getting Feedback Type Data
	@SuppressWarnings("unchecked")
	public String viewDeptwiseFeedType()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String deptId="";
				List EmpDetail = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
				if (EmpDetail!=null && EmpDetail.size()>0) {
					for (Iterator iterator = EmpDetail.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						deptId=object[3].toString();
					}
				}
				
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("fbType", "ASC");
				
				List data=null;
				
				wherClause.put("dept_subdept", deptId);
				//wherClause.put("hide_show", "Active");
				wherClause.put("moduleName", dataFor);
				if (deptId!=null && !deptId.equals("")) {
					data=new HelpdeskUniversalHelper().getDataFromTable("feedback_type", null, wherClause, null, order,searchField,searchString,searchOper, connectionSpace);
				}
						
				if(data!=null && data.size()>0)
				 {		
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_feedback_type_configuration", "", connectionSpace, "feedback_type_configuration");
					
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata[k]!=null)
								{
								  if(k==0)
								     {
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									 }
								  else
									 {
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									 }
								}
							}
							Listhb.add(one);
						}
						setFeedTypeData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
		{returnResult=LOGIN;}
		return returnResult;
	 }
	
	
	// Method for getting Feedback Type Data
	@SuppressWarnings("unchecked")
	public String viewFeedType4HDM()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		 {
			try
			{
				HelpdeskUniversalHelper HUH= new HelpdeskUniversalHelper();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("fbType", "ASC");
				List data=null;
				wherClause.put("dept_subdept", id);
				//wherClause.put("hide_show", "Active");
				wherClause.put("moduleName", "HDM");
				
				data=HUH.getDataFromTable("feedback_type", null, wherClause, null, order,searchField,searchString,searchOper, connectionSpace);
				if(data!=null && data.size()>0)
				{
				    setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_feedback_type_configuration", "", connectionSpace, "feedback_type_configuration");
					
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata[k]!=null)
								{
								  if(k==0)
								     {
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									 }
								  else
									 {
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									 }
								}
							}
							Listhb.add(one);
						}
						setFeedTypeData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
		{returnResult=LOGIN;}
		return returnResult;
	 }
	
    // Method for getting Feedback Category
	@SuppressWarnings("unchecked")
	public String viewFeedCategory()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				HelpdeskUniversalHelper HUH= new HelpdeskUniversalHelper();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("categoryName", "ASC");
				List data=null;
				wherClause.put("fbType", id);
				//wherClause.put("hide_show", "Active");
				data=HUH.getDataFromTable("feedback_category", null, wherClause, null, order,searchField,searchString,searchOper, connectionSpace);
				
				if(data!=null && data.size()>0)
				 {
				setRecords(data.size());
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
				to = getRecords();
					
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=Configuration.getColomnList("mapped_feedback_category_configuration", "", connectionSpace, "feedback_category_configuration");
				fieldNames.add("hide_show");
				
					List<Object> Listhb=new ArrayList<Object>();
					for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata[k]!=null)
								{
								  if(k==0)
								     {
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									 }
								  else
									 {
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									 }
								}
							}
							Listhb.add(one);
						}
					    setFeedCategoryData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	// Method for getting Feedback Sub Category Detail(In Use)
	@SuppressWarnings("unchecked")
	public String viewFeedSubCategory()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				HelpdeskUniversalHelper HUH= new HelpdeskUniversalHelper();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("subCategoryName", "ASC");
				List data=null;
				wherClause.put("categoryName", id);
				//wherClause.put("hide_show", "Active");
				data=HUH.getDataFromTable("feedback_subcategory", null, wherClause, null, order,searchField,searchString,searchOper, connectionSpace);
				
				if(data!=null && data.size()>0)
				 {
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
					to = getRecords();
						
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_feedback_subcategory_configuration", "", connectionSpace, "feedback_subcategory_configuration");
					fieldNames.add("userName");
					fieldNames.add("date");
					fieldNames.add("time");
					fieldNames.add("hide_show");
					fieldNames.remove(1);
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k]!=null)
									{
									  if(k==0)
									     {
										  //System.out.println("Field Name  "+fieldNames.get(k).toString()+"     Field Value  "+(Integer)obdata[k]);
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
										 }
									  else
										 {
										  //System.out.println("Field Name  "+fieldNames.get(k).toString()+"     Field Value  "+obdata[k].toString());
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
										 }
									}
								}
								Listhb.add(one);
							}
						    setFeedSubCategoryData(Listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	// Method for Update Feedback Type (In Use)
	@SuppressWarnings("unchecked")
	public String modifyFeedType()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
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
						String parmName = it.next().toString();
						String paramValue=request.getParameter(parmName);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
								&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
							wherClause.put(parmName, paramValue);
					}
					wherClause.put("userName", userName);
					wherClause.put("date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("time", DateUtil.getCurrentTime());
					
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("feedback_type", wherClause, condtnBlock,connectionSpace);
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
						cbt.deleteAllRecordForId("feedback_type", "id", condtIds.toString(), connectionSpace);
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
	
	
	// Method for Update Feedback Category Name (In Use)
	@SuppressWarnings("unchecked")
	public String modifyfeedCategory()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				/*SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");*/
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
					wherClause.put("userName", userName);
					wherClause.put("date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("time", DateUtil.getCurrentTime());
					condtnBlock.put("id",getId());
					
					System.out.println("Updating Id "+getId());
					
					System.out.println("Updated or not"+cbt.updateTableColomn("feedback_category", wherClause, condtnBlock,connectionSpace));
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
						cbt.deleteAllRecordForId("feedback_category", "id", condtIds.toString(), connectionSpace);
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
	
	

	// Method for Update Feedback Sub Category (In Use)
	@SuppressWarnings("unchecked")
	public String modifyfeedSubCategory()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				/*SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");*/
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String addresTime="",resoTime="",duration="",escalateTime="";
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
								&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid") && !parmName.equalsIgnoreCase("addressingTime") && !parmName.equalsIgnoreCase("resolutionTime") && !parmName.equalsIgnoreCase("escalateTime") && !parmName.equalsIgnoreCase("escalationDuration"))
						{
							wherClause.put(parmName, paramValue);
						}
						else if (parmName.equalsIgnoreCase("addressingTime") || parmName.equalsIgnoreCase("resolutionTime") || parmName.equalsIgnoreCase("escalationDuration")) {
							if (parmName.equalsIgnoreCase("addressingTime")) {
								wherClause.put(parmName, paramValue);
								addresTime=paramValue;
							}
							else if (parmName.equalsIgnoreCase("resolutionTime")) {
								wherClause.put(parmName, paramValue);
								resoTime=paramValue;
							}
							else if (parmName.equalsIgnoreCase("escalationDuration")) {
								wherClause.put(parmName, paramValue);
								duration=paramValue;
							}
							
							if (!addresTime.equals("") && !resoTime.equals("") && !duration.equals("")) {
								escalateTime=DateUtil.getResolutionTime(addresTime, resoTime, duration);
								if (escalateTime!=null && escalateTime!="") {
									wherClause.put("escalateTime", escalateTime);
								}
								else {
									wherClause.put("escalateTime", "24:00");
								}
							}
						}
					}
					wherClause.put("userName", userName);
					wherClause.put("date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("time", DateUtil.getCurrentTime());
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("feedback_subcategory", wherClause, condtnBlock,connectionSpace);
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
						cbt.deleteAllRecordForId("feedback_subcategory", "id", condtIds.toString(), connectionSpace);
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
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

	public List<Object> getSubDeptData() {
		return subDeptData;
	}
	public void setSubDeptData(List<Object> subDeptData) {
		this.subDeptData = subDeptData;
	}
	
	public List<Object> getFeedTypeData() {
		return feedTypeData;
	}
	public void setFeedTypeData(List<Object> feedTypeData) {
		this.feedTypeData = feedTypeData;
	}

	public List<Object> getFeedCategoryData() {
		return feedCategoryData;
	}
	public void setFeedCategoryData(List<Object> feedCategoryData) {
		this.feedCategoryData = feedCategoryData;
	}

	public List<Object> getFeedSubCategoryData() {
		return feedSubCategoryData;
	}
	public void setFeedSubCategoryData(List<Object> feedSubCategoryData) {
		this.feedSubCategoryData = feedSubCategoryData;
	}

	public String getDataFor() {
		return dataFor;
	}
	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
}
