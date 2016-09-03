package com.Over2Cloud.ctrl.compliance;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceTemplateAction extends ActionSupport implements ServletRequestAware{

	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceTemplateAction.class);
	
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> compTemplateList=null;
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
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
	
	public String beforeComplTemplateAdd(){
		
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				  setAddPageDataFields();
				  returnResult=SUCCESS;	
			}
			catch (Exception exp) 
			{
				exp.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method beforeComplTemplateAdd of class "+getClass(), exp);
			}
	   }
	   else
	   {
		  returnResult=LOGIN;
	   }
	  return returnResult;
	}

	
	public void setAddPageDataFields() {
		
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				List<GridDataPropertyView>  complianceFieldsName=Configuration.getConfigurationData("mapped_compl_template_config",accountID,connectionSpace,"",0,"table_name","compl_template_config");
				if(complianceFieldsName!=null)
				{
					compTemplateList=new ArrayList<ConfigurationUtilBean>();
					for(GridDataPropertyView  gdp:complianceFieldsName)
					{
						ConfigurationUtilBean conUtilBean=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());
							if(gdp.getMandatroy().equalsIgnoreCase("0"))
								conUtilBean.setMandatory(false);
							else
								conUtilBean.setMandatory(true);
							
							compTemplateList.add(conUtilBean);
						}
					}
				}
			}
			catch (Exception exp) 
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method setAddPageDataFields of class "+getClass(), exp);
				exp.printStackTrace();
			}
		 }
	  }
	
  @SuppressWarnings({ "unchecked", "rawtypes" })
public String addComplTemplate()
   {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();

				  List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_compl_template_config",accountID,connectionSpace,"",0,"table_name","compl_template_config");
				  if(statusColName!=null)
				  {
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
					TableColumes ob1=null;
					for(GridDataPropertyView gdp:statusColName)
					{
					  ob1=new TableColumes();
					  ob1.setColumnname(gdp.getColomnName());
					  ob1.setDatatype("varchar(255)");
					  
					  if(gdp.getColomnName().equalsIgnoreCase("status"))
					  {
						  ob1.setConstraint("default 'Active'");
					  }
					  else
					  {
						  ob1.setConstraint("default NULL");
					  }
					  Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("compl_template",Tablecolumesaaa,connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
					  String Parmname = it.next().toString();
					  String paramValue=request.getParameter(Parmname);
					  if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
					   {
						 ob=new InsertDataTable();
						 ob.setColName(Parmname);
						 ob.setDataName(paramValue);
						 insertData.add(ob);
					   }
					 }
				    ob=new InsertDataTable();
				    ob.setColName("userName");
				    ob.setDataName(userName);
				    insertData.add(ob);
				   
					ob=new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					
					insertData.add(ob);
				    ob=new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
				    status=cbt.insertIntoTable("compl_template",insertData,connectionSpace); 
				    if(status)
					{
						addActionMessage("Compliance Template added successfully!!!");
						return SUCCESS;
					}
					else
					{
					    addActionMessage("Oops There is some error in data!");
					    return SUCCESS;
					}
				}
			   
				returnResult=SUCCESS;	
			}
			catch (Exception exp) 
			{
				exp.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method addTaskType of class "+getClass(), exp);
			}
	   }
	   else
	   {
		  returnResult=LOGIN;
	   }
	  return returnResult;
	}
	
	
	public String beforeCompTemplateView()
	{
	 	  boolean sessionFlag=ValidateSession.checkSession();
	 	  if(sessionFlag)
	 	  {
	 		  try
	 		  {
	 			  setViewProp();
	 			  return SUCCESS;
	 		  }
	 		  catch(Exception exp)
	 		  {
	 			  log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Compliance  method BeforeCompTemplateView of class "+getClass(), exp);
	 			  exp.printStackTrace();
	 			  return ERROR;
	 		  }
	 	  }
	 	  else
	 	  {
	 		  return LOGIN;
	 	  }
	}
	
	public void setViewProp()
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_compl_template_config",accountID,connectionSpace,"",0,"table_name","compl_template_config");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			masterViewProp.add(gpv);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String viewCompTemplate()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from compl_template");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null && dataCount.size()>0)
				{
					BigInteger count=new BigInteger("3");
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
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_compl_template_config", accountID, connectionSpace, "compl_template_config");
					List<Object> Listhb=new ArrayList<Object>();
					int i=0;
					for(Iterator it=fieldNames.iterator(); it.hasNext();)
					{
						//generating the dyanamic query based on selected fields
						   obdata=(Object)it.next();
						    if(obdata!=null)
						    {
							    if(i<fieldNames.size()-1)
							    	query.append(obdata.toString()+",");
							    else
							    	query.append(obdata.toString());
						    }
						    i++;
						
					}
					query.append(" from compl_template where status='Active'");
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
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
					query.append(" ORDER BY event_name limit "+from+","+to);
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null && data.size()>0)
					{
						Object[] obdata1=null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							 obdata1=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								   if(obdata1[k]!=null && !obdata1[k].toString().equalsIgnoreCase(""))
									{
										if(fieldNames.get(k).toString().equals("date"))
										{
											one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else if(fieldNames.get(k).toString().equals("userName"))
										{
											one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata1[k].toString()));
										}
										else
										{
											one.put(fieldNames.get(k).toString(),obdata1[k].toString());
										}
									}
								   else
								   {
									   one.put(fieldNames.get(k).toString(), "NA");
								   }
								}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
			  returnResult=SUCCESS;	
			}
			catch (Exception exp) 
			{
				exp.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method ViewCompTemplate of class "+getClass(), exp);
			}
	   }
	   else
	   {
		  returnResult=LOGIN;
	   }
	  return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifyCompTemplate()
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
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") 
							&& Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("compl_template", wherClause, condtnBlock,connectionSpace);
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
						StringBuilder query=new StringBuilder("UPDATE compl_template SET status='InActive' WHERE id IN("+condtIds+")");
						cbt.updateTableColomn(connectionSpace,query);
				}
				returnResult=SUCCESS;
			}
			catch (Exception exp) 
			{
				exp.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method viewModifyTaskType of class "+getClass(), exp);
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
	public List<ConfigurationUtilBean> getCompTemplateList() {
		return compTemplateList;
	}
	public void setCompTemplateList(List<ConfigurationUtilBean> compTemplateList) {
		this.compTemplateList = compTemplateList;
	}
	
}
