package com.Over2Cloud.ctrl.cps.corporate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
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
import org.hibernate.ejb.criteria.expression.function.TrimFunction;

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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Package extends ActionSupport implements
		ServletRequestAware {

	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	CommonOperInterface					coi							= new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	
	static final Log log = LogFactory.getLog(CorporateMasterAction.class);
	// Get the requested page. By default grid sets this to 1.
	private Integer rows = 0;
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
	// Grid colomn view
	private String oper;

	private String id;
	private JSONArray jsonArray;
	
	
	
	private Map<String,String> packList;
	
	
	List<ConfigurationUtilBean> packageFields;
	
	
	
	public String viewPackageHeader() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public String beforeAddPackage(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAddPageDataFields();
				createPackageList();
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	Map<String,String> createPackageList(){
		packList=new LinkedHashMap<String,String>();
		List dataList=coi.executeAllSelectQuery("select distinct id, pack_name from cps_package;", connectionSpace);
		if(dataList!=null && dataList.size() > 0){
			for(Iterator itr=dataList.iterator();itr.hasNext();){
				Object[] obj =(Object []) itr.next();
				if(obj[0]!=null && obj[1]!=null)
				packList.put(obj[0].toString(),obj[1].toString());
			}
		}
		return packList;
	}
	
	
	
	public void setAddPageDataFields() {
		try
		{

		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_corp_package_configuration", accountID, connectionSpace, "", 0, "table_name", "corp_package_configuration");
		packageFields = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName"))
			{
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
					else
						{
						obj.setMandatory(false);
						}
					packageFields.add(obj);
					
			}

			
		}
		}catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
			}
	}

	public String addPackageDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData(
								"mapped_corp_package_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"corp_package_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							Tablecolumesaaa.add(ob1);
					}
					
						status=	cbt.createTable22("corp_package", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] subPack=new String[0];
					Object [] cost=new String[0];
					Object [] code=new String[0];
					Object [] fromD=new String[0];
					Object [] toD=new String[0];
					Object [] brief=new String[0];
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
						{
							if(Parmname.equalsIgnoreCase("pack_subpack")){
								subPack=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("pack_cost")){
								cost=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("pack_code")){
								code=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("pack_from")){
								fromD=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("pack_to")){
								toD=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("pack_brief")){
								brief=request.getParameterValues(Parmname);
							}
							else{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
							}
						}
					}
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
					
					//status = cbt.insertIntoTable("corp_package",insertData, connectionSpace);
					
					/*////System.out.println(Arrays.asList(subPack));
					////System.out.println(Arrays.asList(cost));
					////System.out.println(Arrays.asList(code));
					////System.out.println(Arrays.asList(fromD));
					////System.out.println(Arrays.asList(toD));
					////System.out.println(Arrays.asList(brief));*/
					
					int count=0;
					
					for(int i=0;i<subPack.length;i++){
						
						if(subPack[i]!=null && !subPack[i].toString().trim().equalsIgnoreCase("")){
					InsertDataTable	idt1 = new InsertDataTable();
					idt1.setColName("pack_subpack");
					idt1.setDataName(checkNull(subPack[i]));
						insertData.add(idt1);
					
						InsertDataTable	idt2 = new InsertDataTable();
						idt2.setColName("pack_cost");
						idt2.setDataName(checkNull(cost[i]));
							insertData.add(idt2);
					
						InsertDataTable	idt3 = new InsertDataTable();
							idt3.setColName("pack_code");
							idt3.setDataName(checkNull(code[i]));
								insertData.add(idt3);			
						
						InsertDataTable	idt4 = new InsertDataTable();
							idt4.setColName("pack_from");
							idt4.setDataName(checkNull(fromD[i]));
							insertData.add(idt4);	
							
						InsertDataTable	idt5 = new InsertDataTable();
							idt5.setColName("pack_to");
							idt5.setDataName(checkNull(toD[i]));
							insertData.add(idt5);			
							
						InsertDataTable	idt6 = new InsertDataTable();
							idt6.setColName("pack_brief");
							idt6.setDataName(checkNull(brief[i]));
							insertData.add(idt6);	
							
							status = cbt.insertIntoTable("corp_package",insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
							insertData.remove(idt2);
							insertData.remove(idt3);
							insertData.remove(idt4);
							insertData.remove(idt5);
							insertData.remove(idt6);
						}
					}
					
					
					insertData.clear();
				
					if (status)
					{
						addActionMessage("Package Added successfully: "+count);
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	String checkNull(Object obj){
		String returnResult="NA";
		if(obj!=null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1") ){
			returnResult=obj.toString();
		}
		return returnResult;
	}
	

	  public String fetchCorpList(){
	    	String returnresult=ERROR;
	    	boolean sessionFlag = ValidateSession.checkSession();
	    	if (sessionFlag)
	    	{
	    		try
	    		{//countryID='"+getCountryId()+"'
	    		String query="select id,corp_name from corporate_master where corp_type = '"+searchField+"'  ORDER BY corp_name ";
	    		List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
	    		
	    		 if (dataList != null && dataList.size() > 0)
	    			{
	    			 jsonArray = new JSONArray();
	    				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
	    				{
	    					Object[] object = (Object[]) iterator.next();
	    					if (object[0] != null && object[1] != null)
	    					{
	    						JSONObject obj= new JSONObject();
	    						obj.put("id",object[0].toString() );
	    						obj.put("name", object[1].toString());
	    						jsonArray.add(obj);
	    					}
	    				}
	    			}
	    			
	    			returnresult=SUCCESS;
	    		}catch(Exception e){
	    			e.printStackTrace();
	    			returnresult = ERROR;
	    			}
	    	}else {
	    		return LOGIN; 
	    	}
	    	return returnresult;
	    }

	
	
	
	
	public String viewPackageDetails(){

		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewProps();
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
	
	 void setMasterViewProps(){

		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_corp_package_configuration",accountID,connectionSpace,"",0,"table_name","corp_package_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
							for(GridDataPropertyView gdp:statusColName)
							{
									gpv = new GridDataPropertyView();
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
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	 
	 public String viewPackageData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from corp_package");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
				Object obdata = (Object) it.next();
				count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords()) to = getRecords();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from corp_package as cp left join cps_package as pack on pack.id=cp.pack_name left join corporate_master as cc on cc.id=cp.corp_name ");

				List fieldNames = Configuration.getColomnList("mapped_corp_package_configuration", accountID, connectionSpace, "corp_package_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
									if(obdata.toString().equalsIgnoreCase("pack_name")){
										queryTemp.append("pack." + obdata.toString() + ", ");
									}else
									if(obdata.toString().equalsIgnoreCase("corp_name")){
										queryTemp.append("cc." + obdata.toString() + ", ");
									}
									else{
										queryTemp.append("cp." + obdata.toString() + ",");
									}
								}
							
					}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" cp.status='Active' ");
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
				// add search query based on the search operation
				query.append(" and ");

				if (getSearchOper().equalsIgnoreCase("eq"))
				{
				query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
				}
				else if (getSearchOper().equalsIgnoreCase("cn"))
				{
				query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
				}
				else if (getSearchOper().equalsIgnoreCase("bw"))
				{
				query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
				}
				else if (getSearchOper().equalsIgnoreCase("ne"))
				{
				query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
				}
				else if (getSearchOper().equalsIgnoreCase("ew"))
				{
				query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
				}
  
				}
				
				query.append(" order by pack.pack_name ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//////System.out.println("query.toString()>>"+query.toString()); 
				if (data != null)
				{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
				Object[] obdata = (Object[]) it.next();
				Map<String, Object> one = new HashMap<String, Object>();
					for (int k = 0; k < fieldNames.size(); k++)
					{
						if (obdata[k] != null)
						{
							if (k == 0)
							{
								one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
							}
							else
							{
								if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
								{
									obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
								}
								one.put(fieldNames.get(k).toString(), checkNull(obdata[k]));
							}
						}else{
							one.put(fieldNames.get(k).toString(), "NA");
						}
					}
					Listhb.add(one);
				}
					setViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					return "success";
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
	 }
	 
	 public String editPackagesDataGrid()
	 {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					if (getOper().equalsIgnoreCase("edit"))
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
							{
								if (parmName.equalsIgnoreCase("userName"))
								{
									wherClause.put(parmName, userName);
								}
								else if (parmName.equalsIgnoreCase("date"))
								{
									wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
								}
								else if (parmName.equalsIgnoreCase("time"))
								{
									wherClause.put(parmName, DateUtil.getCurrentTimeHourMin());
								}
								else
								{
									wherClause.put(parmName, paramValue);
								}
							}
						}
						condtnBlock.put("id", getId());
						boolean status = cbt.updateTableColomn("corp_package", wherClause, condtnBlock, connectionSpace);
						if (status)
						{
							returnResult = SUCCESS;
						}
					}
					else if (getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						String tempIds[] = getId().split(",");
						for (String H : tempIds)
						{
							Map<String, Object> wherClause = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							wherClause.put("status", "Inactive");
							condtnBlock.put("id", id);
							cbt.updateTableColomn("corp_package", wherClause, condtnBlock, connectionSpace);
							returnResult = SUCCESS;
						}
					}
				}
				catch (Exception e)
				{
					log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method corporate master of class " + getClass(), e);
					e.printStackTrace();
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		
		 
	 }

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getViewList() {
		return viewList;
	}

	public void setViewList(List<Object> viewList) {
		this.viewList = viewList;
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

	public List<ConfigurationUtilBean> getPackageFields() {
		return packageFields;
	}

	public void setPackageFields(List<ConfigurationUtilBean> packageFields) {
		this.packageFields = packageFields;
	}

	public Map<String, String> getPackList() {
		return packList;
	}

	public void setPackList(Map<String, String> packList) {
		this.packList = packList;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	
}
