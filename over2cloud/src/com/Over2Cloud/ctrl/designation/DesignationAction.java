
package com.Over2Cloud.ctrl.designation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.departmen.DeptViewAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DesignationAction extends ActionSupport implements ServletRequestAware{

	static final Log log = LogFactory.getLog(DesignationAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private int levelOforganization;
	private String level2org;
	private String level3org;
	private String level4org;
	private String level5org;
	private String designationName;
	private String parallelShare;
	private String designationName1;
	private String parallelShare1;
	private String level;
	private String level1;
	private String deptHierarchy;
	private String deptname;
	private String subdept;
	//GRID OPERATION
	private List<GridDataPropertyView> masterViewProp=new ArrayList<GridDataPropertyView>();
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
	private List<Object> designationDataGridView;
	private HttpServletRequest request;
	
	
	// Added by Avinash
	private List<ConfigurationUtilBean> designationDropDown;
	private List<ConfigurationUtilBean> designationTextBox;
	private Map<Integer,String> officeMap;
	private Map<Integer,String> deptMap;
	private String mappedOrgId;
	private JSONArray commonJSONArray=new JSONArray();
	
	
	
	public String getMappedDesignation()
	{

		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				List dataList=new createTable().executeAllSelectQuery("select id,designationname from designation where mappedDeptId='"+getDeptname()+"'", connectionSpace);
				if(dataList!=null & dataList.size()>0)
				{
					for (Object c : dataList) 
					 {
						Object[] objVar = (Object[]) c;
						JSONObject listObject=new JSONObject();
						listObject.put("id",objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					 }
				}
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR; 
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getMappedOfcDept()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				List dataList=new createTable().executeAllSelectQuery("select id,deptName from department where mappedOrgnztnId='"+getMappedOrgId()+"'", connectionSpace);
				if(dataList!=null & dataList.size()>0)
				{
					for (Object c : dataList) 
					 {
						Object[] objVar = (Object[]) c;
						JSONObject listObject=new JSONObject();
						listObject.put("id",objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					 }
				}
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR; 
			}
		}
		else
		{
			return LOGIN;
		}
	}
	public void setDesgAddPageFileds()
	{
		try
		{
			List<GridDataPropertyView> gridFields=Configuration.getConfigurationData("mapped_designation_confg",accountID,connectionSpace,"",0,"table_name","designation_configuration");
			if(gridFields!=null)
			{
				designationDropDown=new ArrayList<ConfigurationUtilBean>();
				designationTextBox=new ArrayList<ConfigurationUtilBean>();
				for(GridDataPropertyView  gdp:gridFields)
				{
					ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("orgnztnlevel"))
                    {
                    	objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        designationDropDown.add(objdata);
                        
                        if(gdp.getColomnName().equalsIgnoreCase("mappedOrgnztnId"))
                        {
                        	officeMap=new HashMap<Integer, String>();
                        	List data=new createTable().executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);
                        	
                        	if(data!=null && data.size()>0 && data.get(0)!=null)
                        	{
                        		String arr[]=data.get(0).toString().split("#");
                        		for (int i = 0; i < arr.length; i++) 
                        		{
                        			officeMap.put(i+1, arr[i].toString());
								}
                        	}
                        }
                        else if(gdp.getColomnName().equalsIgnoreCase("mappedDeptId"))
                        {
                        	deptMap=new HashMap<Integer, String>();
                        }
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("flag"))
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        designationTextBox.add(objdata);
                       
                    }
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method setDepartmentAndSubDeptNames(int deptFalgOrSubDept) of class "+getClass(), e);
			e.printStackTrace();
		}
	
	
	}
	
	public String beforeDesgAdd()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				setDesgAddPageFileds();
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR; 
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	public String createDesignation()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				boolean status=false;
			    boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				List<GridDataPropertyView> desgColumns=Configuration.getConfigurationData("mapped_designation_confg",accountID,connectionSpace,"",0,"table_name","designation_configuration");
				if(desgColumns!=null && desgColumns.size()>0)
				{
					for(GridDataPropertyView gdp:desgColumns)
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
					status=cbt.createTable22("designation",Tablecolumesaaa,connectionSpace);
					if(status && getDesignationname()!=null)
					{
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						String desg[]=getDesignationname().trim().split(",");
						for (int i = 0; i < desg.length; i++) 
						{
							if(desg[i]!=null && !desg[i].equalsIgnoreCase(" "))
							{
								InsertDataTable ob=new InsertDataTable();
								ob.setColName("designationname");
								ob.setDataName(desg[i].trim());
								insertData.add(ob);
								
								boolean desgExists=new HelpdeskUniversalHelper().isExist("designation", "designationname", desg[i].trim(), "mappedDeptId", getDeptname(), "","","","", connectionSpace);
								
								if(!desgExists)
								{
									if(getDeptname()!=null)
									{
										InsertDataTable ob1=new InsertDataTable();
										ob1.setColName("mappedDeptId");
										ob1.setDataName(getDeptname());
										insertData.add(ob1);
									}
									
									if(getMappedOrgId()!=null)
									{
										InsertDataTable ob2=new InsertDataTable();
										ob2.setColName("mappedOrgnztnId");
										ob2.setDataName(getMappedOrgId());
										insertData.add(ob2);
									}
									
									if(userTrue)
									 {
										InsertDataTable ob3=new InsertDataTable();
										ob3.setColName("userName");
										ob3.setDataName(userName);
										insertData.add(ob3);
									 }
									if(dateTrue)
									 {
										InsertDataTable ob4=new InsertDataTable();
										ob4.setColName("date");
										ob4.setDataName(DateUtil.getCurrentDateUSFormat());
										insertData.add(ob4);
									 }
									if(timeTrue)
									 {
										InsertDataTable ob5=new InsertDataTable();
										ob5.setColName("time");
										ob5.setDataName(DateUtil.getCurrentTime());
										insertData.add(ob5);
									 }
									
									if(!desgExists)
									 {
										 status=cbt.insertIntoTable("designation",insertData,connectionSpace);
										 insertData.clear();
									 }
									 else
									 {
										 status=false;
									 }
								}
							}
						}
							if(status)
							{
								System.out.println("All is well");
								addActionMessage("Designation Registered Successfully!!!");
								 return SUCCESS;
							}
							else
							{
								 addActionMessage("Designation Already Exists !!");
								 return SUCCESS;
							}
						}
					}
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR; 
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeDesignationView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Designation >> View";
			editDeptData="false";
			setGridColomnNames();
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeDesignationView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeDesignationModify()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			headerName="Designation >> Modify";
			editDeptData="true";
			setGridColomnNames();
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeDesignationModify of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String viewDesignationData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from designation where flag='0'");
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
				List fieldNames=Configuration.getColomnList("mapped_designation_confg", accountID, connectionSpace, "designation_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
                            if(obdata.toString().equalsIgnoreCase("mappedDeptId"))
                            {
                            	query.append("dept.deptName, ");
                            }
                            else
                            {
                            	if(i<fieldNames.size()-1)
	                                query.append("desg."+obdata.toString()+",");
	                            else
	                                query.append("desg."+obdata.toString());
                            }
                        }
					    i++;
					
				}
				
				query.append(" from designation as desg inner join department as dept on dept.id=desg.mappedDeptId where desg.flag=0 ");
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
				
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by "+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by "+getSidx()+" DESC");
		    	    }
		    	    else
		    	    {
		    	    	query.append(" order by desg.designationname");
		    	    }
			    }
				cbt.checkTableColmnAndAltertable(fieldNames,"designation",connectionSpace);
				List<String> officeList=new ArrayList<String>();
				
				officeList=new DeptViewAction().getAllOffices();
				
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{

					//level1Data
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
                                	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("mappedOrgnztnId"))
                                	{
                                		if(obdata[k].toString().equalsIgnoreCase("1") && officeList.get(0)!=null)
                                		{
                                			one.put(fieldNames.get(k).toString(),(officeList.get(0)));
                                		}
                                		else if(obdata[k].toString().equalsIgnoreCase("2") && officeList.get(1)!=null)
                                		{
                                			one.put(fieldNames.get(k).toString(),(officeList.get(1)));
                                		}
                                		else if(obdata[k].toString().equalsIgnoreCase("3") && officeList.get(2)!=null)
                                		{
                                			one.put(fieldNames.get(k).toString(),(officeList.get(2)));
                                		}
                                		else if(obdata[k].toString().equalsIgnoreCase("4") && officeList.get(3)!=null)
                                		{
                                			one.put(fieldNames.get(k).toString(),(officeList.get(3)));
                                		}
                                		else if(obdata[k].toString().equalsIgnoreCase("5") && officeList.get(4)!=null)
                                		{
                                			one.put(fieldNames.get(k).toString(),(officeList.get(4)));
                                		}
                                	}
                                	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
                                	{
                                		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()) );
                                	}
                                	else
                                	{
                                		one.put(fieldNames.get(k).toString(), obdata[k].toString());
                                	}
                                }
							}
						}
						Listhb.add(one);
					}
					setDesignationDataGridView(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewDesignationData of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private String designationname;
	public String editDesignationDataGrid()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getOper().equalsIgnoreCase("edit"))
			{
				//designationname
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
				cbt.updateTableColomn("designation", wherClause, condtnBlock,connectionSpace);
			}
			

			if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				wherClause.put("flag", 1);
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("designation", wherClause, condtnBlock,connectionSpace);
			}
			
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method editDesignationDataGrid of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setGridColomnNames()
	{
    	try
    	{
    		Map session = ActionContext.getContext().getSession();
    		String accountID=(String)session.get("accountid");
    	    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
    		
    		
            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_designation_confg",accountID,connectionSpace,"",0,"table_name","designation_configuration");
            
            for(GridDataPropertyView gdp:returnResult)
            {
            	gpv=new GridDataPropertyView();
                    gpv.setColomnName(gdp.getColomnName());
                    gpv.setHeadingName(gdp.getHeadingName());
                    gpv.setEditable(gdp.getEditable());
                    gpv.setSearch(gdp.getSearch());
                    gpv.setHideOrShow(gdp.getHideOrShow());
                    gpv.setFormatter(gdp.getFormatter());
                    gpv.setWidth(gdp.getWidth());
                    masterViewProp.add(gpv);
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
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
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getParallelShare() {
		return parallelShare;
	}
	public void setParallelShare(String parallelShare) {
		this.parallelShare = parallelShare;
	}
	public String getDesignationName1() {
		return designationName1;
	}
	public void setDesignationName1(String designationName1) {
		this.designationName1 = designationName1;
	}
	public String getParallelShare1() {
		return parallelShare1;
	}
	public void setParallelShare1(String parallelShare1) {
		this.parallelShare1 = parallelShare1;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
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

	public List<Object> getDesignationDataGridView() {
		return designationDataGridView;
	}

	public void setDesignationDataGridView(List<Object> designationDataGridView) {
		this.designationDataGridView = designationDataGridView;
	}

	public String getDesignationname() {
		return designationname;
	}

	public void setDesignationname(String designationname) {
		this.designationname = designationname;
	}
	
	public String getDeptHierarchy() {
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy) {
		this.deptHierarchy = deptHierarchy;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getSubdept() {
		return subdept;
	}

	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}

	public List<ConfigurationUtilBean> getDesignationDropDown() {
		return designationDropDown;
	}

	public void setDesignationDropDown(
			List<ConfigurationUtilBean> designationDropDown) {
		this.designationDropDown = designationDropDown;
	}

	public List<ConfigurationUtilBean> getDesignationTextBox() {
		return designationTextBox;
	}

	public void setDesignationTextBox(List<ConfigurationUtilBean> designationTextBox) {
		this.designationTextBox = designationTextBox;
	}

	public Map<Integer, String> getOfficeMap() {
		return officeMap;
	}

	public void setOfficeMap(Map<Integer, String> officeMap) {
		this.officeMap = officeMap;
	}

	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}

	public String getMappedOrgId() {
		return mappedOrgId;
	}
	public void setMappedOrgId(String mappedOrgId) {
		this.mappedOrgId = mappedOrgId;
	}
	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}
	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
}
