package com.Over2Cloud.ctrl.subdepartmen;

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

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SubDepartmentAction extends ActionSupport implements ServletRequestAware{

	
	static final Log log = LogFactory.getLog(SubDepartmentAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private int levelOforganization;
	private String level2org;
	private String level3org;
	private String level4org;
	private String level5org;
	private String subdeptName;
	private String subdeptName1;
	private String deptname;//for current selected dept id
	private List<GridDataPropertyView>level1ColmnNames=new ArrayList<GridDataPropertyView>();
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
	private List<Object> subDeptDataGridView;
	private String subdeptname;
	private HttpServletRequest request;
	
	
	public String createSubDepartment()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getDeptname()!=null && !getDeptname().equalsIgnoreCase("") && !getDeptname().equalsIgnoreCase("-1") && getSubdeptName()!=null) {
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
				 List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_subdeprtmentconf",accountID,connectionSpace,"",0,"table_name","subdeprtmentconf");
					if(org2!=null)
					{
						//create table query based on configuration
						List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
						for(GridDataPropertyView gdp:org2)
						{
							 TableColumes ob1=new TableColumes();
							 ob1=new TableColumes();
							 ob1.setColumnname(gdp.getColomnName());
							 ob1.setDatatype("varchar(255)");
							 ob1.setConstraint("default NULL");
							 Tablecolumesaaa.add(ob1);
							 if(gdp.getColomnName().equalsIgnoreCase("userName"))
								 userTrue=true;
							 else if(gdp.getColomnName().equalsIgnoreCase("date"))
								 dateTrue=true;
							 else if(gdp.getColomnName().equalsIgnoreCase("time"))
								 timeTrue=true;
						}
						cbt.createTable22("subdepartment",Tablecolumesaaa,connectionSpace);
					}
					 boolean status=false;
					//getting the parameters nd setting their value using loop
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 Collections.sort(requestParameterNames);
					 Iterator it = requestParameterNames.iterator();
					 InsertDataTable ob=null;
					 List paramList=new ArrayList<String>();
					 int paramValueSize=0;
					 boolean statusTemp=true;
					 String deptid=null;
					 while (it.hasNext()) {	
							String Parmname = it.next().toString();
							String paramValue=request.getParameter(Parmname);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && Parmname.equalsIgnoreCase("deptname"))
									{
								
									 deptid=paramValue;
									}
							else if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null 
									 && !Parmname.equalsIgnoreCase("orgLevel") && !Parmname.equalsIgnoreCase("levelOforganization")&& !Parmname.equalsIgnoreCase("level1")&& !Parmname.equalsIgnoreCase("level")&& !Parmname.equalsIgnoreCase("level2org")
									&& !Parmname.equalsIgnoreCase("level3org")&& !Parmname.equalsIgnoreCase("level4org")&& !Parmname.equalsIgnoreCase("level5org")&& !Parmname.equalsIgnoreCase("deptname"))
										{
												//adding the parameters list.
												paramList.add(Parmname);
										   		if(statusTemp)
										   		{
											   		String tempParamValueSize[]=request.getParameterValues(Parmname);
											   		for(String H:tempParamValueSize)
											   		{
											   			//counting one time size of the parameter value
											   			if(!H.equalsIgnoreCase("") && H!=null)
											   				paramValueSize++;	
											   		}
											   		statusTemp=false;
										   		}
										}
								}
					 
							 String parmValuew[][]=new String[paramList.size()][paramValueSize];
							 
							 boolean subDeptExists=false;
							 
								int m=0;
									for (Object c : paramList) {
										Object Parmname = (Object) c;
										String paramValue[]=request.getParameterValues(Parmname.toString());
										for(int j=0;j<paramValueSize;j++)
										{
											if(!paramValue[j].equalsIgnoreCase("") && paramValue[j]!=null)
											{
												parmValuew[m][j]=DateUtil.makeTitle(paramValue[j]);
											}
										}
										m++;
									}
									for(int i=0;i<paramValueSize;i++)
									{
										List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
										for(int k=0;k<paramList.size();k++)
										{
											subDeptExists=new HelpdeskUniversalHelper().isExist("subdepartment", "subdeptname", parmValuew[k][i], "deptid", deptid, "","","","", connectionSpace);
											if(!subDeptExists)
											{
												 ob=new InsertDataTable();
												 ob.setColName(paramList.get(k).toString());
												 ob.setDataName(parmValuew[k][i]);
												 insertData.add(ob);
											}
											
										}
										 ob=new InsertDataTable();
										 ob.setColName("deptid");
										 ob.setDataName(deptid);
										 insertData.add(ob);
										 if(userTrue)
										 {
											 ob=new InsertDataTable();
											 ob.setColName("userName");
											 ob.setDataName(userName);
											 insertData.add(ob);
										 }
										 if(dateTrue)
										 {
											 ob=new InsertDataTable();
											 ob.setColName("date");
											 ob.setDataName(DateUtil.getCurrentDateUSFormat());
											 insertData.add(ob);
										 }
										 if(timeTrue)
										 {
											 ob=new InsertDataTable();
											 ob.setColName("time");
											 ob.setDataName(DateUtil.getCurrentTime());
											 insertData.add(ob);
										 }
										 if(!subDeptExists)
										 {
											 status=cbt.insertIntoTable("subdepartment",insertData,connectionSpace);
											 addActionMessage("Sub-department Registered Successfully!!!");
											 return SUCCESS;
										 }
										 else
										 {
											 addActionMessage("Sub-department Already Exists !!!");
											 return SUCCESS;
										 }
									}
									
						if(!status)
						 {
							 addActionMessage("Oops There is some error in data!");
							 return SUCCESS;
						 }
						
			}
			else
			{
				addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createSubDepartment of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String beforeSubDepartmentView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			editDeptData="false";
			setGridColomnNames();
			setDepartmentAndSubDeptNames();
			headerName=headerName+" >> View";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeSubDepartmentView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeSubDepartmentModify()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			editDeptData="true";
			setGridColomnNames();
			setDepartmentAndSubDeptNames();
			headerName=headerName+" >> Modify";
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeSubDepartmentModify of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setDepartmentAndSubDeptNames()
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
				String namesofDepts[]=new String[3];
				StringBuilder query=new StringBuilder("");
					query.append("select levelName from mapped_dept_level_config");
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(data!=null)
						{
							String names=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object obdata=(Object)it.next();
								if(obdata!=null)
									names=obdata.toString();
									
								
							}
							namesofDepts=names.split("#");
						}
						headerName=namesofDepts[1];
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method setDepartmentAndSubDeptNames of class "+getClass(), e);
			e.printStackTrace();
		}
	}
	
	public String viewSubDeptData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from subdepartment");
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
				query.append("select ");
				List fieldNames=Configuration.getColomnList("mapped_subdeprtmentconf", accountID, connectionSpace, "subdeprtmentconf");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
					    	if(obdata.toString().equalsIgnoreCase("deptid"))
					    	{
							    if(i<fieldNames.size()-1)
							    	query.append("dept.deptname,");
							    else
							    	query.append("dept.deptname");
					    	}
					    	else
					    	{
					    		if(i<fieldNames.size()-1)
							    	query.append("subdept."+obdata.toString()+",");
							    else
							    	query.append("subdept."+obdata.toString());
					    	}
					    }
					    i++;
					
				}
				
				query.append(" from subdepartment as " +
						"subdept inner join department as dept on subdept.deptid=dept.id where subdept.flag=0");
				
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and subdept."+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and subdept."+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and subdept."+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and subdept."+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and subdept."+getSearchField()+" like '%"+getSearchString()+"'");
					}
					
				}
				
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by subdept."+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by subdept."+getSidx()+" DESC");
		    	    }
			    }
				
				
				query.append(" limit "+from+","+to);
				System.out.println("QUERY :::::"   +query.toString());
				
				/**
				 * **************************checking for colomon change due to configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames,"subdepartment",connectionSpace);
				
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
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
								{
									one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
								}
								else
								{
									if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
									{
										one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
									}
									else
									{
										one.put(fieldNames.get(k).toString(),obdata[k].toString());
									}
								}
							}
						}
						Listhb.add(one);
					}
					setSubDeptDataGridView(Listhb);
					
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewSubDeptData of class "+getClass(), e);
			 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setGridColomnNames()
	{
		//id,subdeptname,deptid,organizationLevel,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Dept Id");
		gpv.setHideOrShow("true");
		level1ColmnNames.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_subdeprtmentconf",accountID,connectionSpace,"",0,"table_name","subdeprtmentconf");
		
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setWidth(gdp.getWidth());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			level1ColmnNames.add(gpv);
		}
	}
	public String editSubDeptDataGrid()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
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
							&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("subdepartment", wherClause, condtnBlock,connectionSpace);
			}
			
			if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				wherClause.put("flag", 1);
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("subdepartment", wherClause, condtnBlock,connectionSpace);
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method editSubDeptDataGrid of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	public int getLevelOforganization() {
		return levelOforganization;
	}
	public void setLevelOforganization(int levelOforganization) {
		this.levelOforganization = levelOforganization;
	}
	public String getLevel2org() {
		return level2org;
	}
	public void setLevel2org(String level2org) {
		this.level2org = level2org;
	}
	public String getLevel3org() {
		return level3org;
	}
	public void setLevel3org(String level3org) {
		this.level3org = level3org;
	}
	public String getLevel4org() {
		return level4org;
	}
	public void setLevel4org(String level4org) {
		this.level4org = level4org;
	}
	public String getLevel5org() {
		return level5org;
	}
	public void setLevel5org(String level5org) {
		this.level5org = level5org;
	}
	public String getSubdeptName() {
		return subdeptName;
	}
	public void setSubdeptName(String subdeptName) {
		this.subdeptName = subdeptName;
	}
	public String getSubdeptName1() {
		return subdeptName1;
	}
	public void setSubdeptName1(String subdeptName1) {
		this.subdeptName1 = subdeptName1;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}


	public List<GridDataPropertyView> getLevel1ColmnNames() {
		return level1ColmnNames;
	}


	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames) {
		this.level1ColmnNames = level1ColmnNames;
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



	public List<Object> getSubDeptDataGridView() {
		return subDeptDataGridView;
	}


	public void setSubDeptDataGridView(List<Object> subDeptDataGridView) {
		this.subDeptDataGridView = subDeptDataGridView;
	}


	public String getSubdeptname() {
		return subdeptname;
	}


	public void setSubdeptname(String subdeptname) {
		this.subdeptname = subdeptname;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

}
