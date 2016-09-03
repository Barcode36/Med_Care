package com.Over2Cloud.ctrl.text2mail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.leaveManagement.LeaveHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Mapping extends ActionSupport implements ServletRequestAware {
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	
	private HttpServletRequest request;
	String deptLevel = (String) session.get("userDeptLevel");
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
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
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private String formater;
	private String header;
	private LinkedHashMap<String, String> locationMasterMap;
	private String map;
	
	
	
	
	
	public String execute() {
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		} catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeMappingView()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();			
			List locationMasterData = cbt
			.executeAllSelectQuery(
					"SELECT ctm.id,emp.empName FROM compliance_contacts AS ctm INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id INNER JOIN subdepartment AS subdept ON subdept.id=emp.subdept INNER JOIN department AS dept ON dept.id=subdept.deptid  where ctm.work_status=0 and forDept_id=1 and moduleName='T2M' and level=1 ORDER BY emp.empName",
					connectionSpace);
	locationMasterMap = new LinkedHashMap<String, String>();
	if (locationMasterData != null) {
		for (Object c : locationMasterData) {
			Object[] dataC = (Object[]) c;
			locationMasterMap.put(dataC[0].toString(), dataC[1]
					.toString());
		}
	}

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String mapWithExecutive()
	{
		try
		{
			System.out.println("id>>>>>>>>>>>>>>>>>>>"+id+" map>>>>>>>>>>>>>>>>> "+map);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();
			wherClause.put("map", getMap());
			condtnBlock.put("id", getId());
		boolean map2=cbt.updateTableColomn("consultants", wherClause,
					condtnBlock, connectionSpace);
		if(map2)
		{
			System.out.println("Mapped Successfully>>>>>>>>>>>>");
		}	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String beforeMappingView2()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return LOGIN;
			}
			
			
		}
		else
		{
			return LOGIN;
		}
	}
	
	public void setViewProp()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("empName");
				gpv.setHeadingName("Executive");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				List<GridDataPropertyView> returnResult = Configuration
						.getConfigurationData(
								"mapped_consultants_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"consultants_configuration");
				for (GridDataPropertyView gdp : returnResult)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					
						
					masterViewProp.add(gpv);
				}
				
				
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{

		}
	}
	
	public String viewConsInGrid2()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from consultants");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);
				System.out.println("dataCount:" + dataCount);
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
					query.append("select eb.empName , ");

					List fieldNames = Configuration.getColomnList(
							"mapped_consultants_configuration", accountID,
							connectionSpace, "consultants_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1) query.append("ca."+obdata
									.toString()
									+ ",");
							else query.append("ca."+obdata.toString());
						}
						i++;
					}
					fieldNames.add(0, "empName");

					query.append(" from consultants as ca left join compliance_contacts  as cc on ca.map=cc.id left join employee_basic as eb on cc.emp_id=eb.id where ca.map!=0 ");
					System.out.println("query:::" + query);

					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" where ");

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null
								&& !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null
								&& !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" limit " + from + "," + to);
					System.out.println("query::::" + query);

					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);

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
									if (fieldNames.get(k).toString().equalsIgnoreCase(
											"userID"))
									{}
									//modified 12-10-2013
									else if (fieldNames.get(k).toString().equalsIgnoreCase(
											"password"))
									{}
									////////////////////
									else
									{
										if (k == 1)
										{
											one.put(fieldNames.get(k).toString(),
													(Integer) obdata[k]);
										}
										else
										{
											if (fieldNames.get(k) != null
													&& fieldNames.get(k).toString()
															.equalsIgnoreCase("date"))
											{
												one
														.put(
																fieldNames.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											}
											else if (fieldNames.get(k) != null
													&& fieldNames.get(k).toString()
															.equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),
														(obdata[k].toString()
																.substring(0, 5)));
											}
											else
											{
												one.put(fieldNames.get(k).toString(),
														(obdata[k].toString()));
											}
										}
									}
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	
	public String mapContacts()
	{
		try
		{
			
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();	
			StringBuilder query=new StringBuilder();
			LeaveHelper LH=new LeaveHelper();
			List data2=null;
			String dept_subDeptId = null;
			dept_subDeptId = getDept_SubdeptId(userName,deptLevel,"T2M");
			System.out.println(dept_subDeptId);
			query.append("SELECT ctm.id,emp.emailIdOne,emp.empName FROM compliance_contacts AS ctm " +
					" INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id INNER JOIN groupinfo AS grp ON grp.id=emp.groupId INNER JOIN subdepartment AS subdept ON ctm.forDept_id=subdept.id  INNER JOIN department AS dept ON subdept.deptid=dept.id "
					 + " where emp.flag=0 " +
					"and ctm.forDept_id=" + dept_subDeptId + " and moduleName='T2M'   ORDER BY emp.empName" );
			System.out.println(">>>>>>>>>MAPPING" +query);
			List locationMasterData = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	locationMasterMap = new LinkedHashMap<String, String>();
	if (locationMasterData != null) {
		for (Object c : locationMasterData) {
			Object[] dataC = (Object[]) c;
			locationMasterMap.put(dataC[1].toString(), dataC[2]
					.toString());
		}
	}
	
			
		}
		
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
		
	}
	
	public String getDept_SubdeptId(String userName,String deptLevel,String moduleName)
	{
		String dept_subDeptId = null;
		try
		{
			dept_subDeptId=new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName)[3];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dept_subDeptId;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
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

	public String getFormater() {
		return formater;
	}

	public void setFormater(String formater) {
		this.formater = formater;
	}
	

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	

	public LinkedHashMap<String, String> getLocationMasterMap() {
		return locationMasterMap;
	}

	public void setLocationMasterMap(LinkedHashMap<String, String> locationMasterMap) {
		this.locationMasterMap = locationMasterMap;
	}
	
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}
	
	
	

}
