package com.Over2Cloud.emailSetting;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.hr.group.GroupActionCtrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class EmailSetting extends ActionSupport implements ServletRequestAware
{

	private HttpServletRequest request;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private List<ConfigurationUtilBean> emailColumnDropdown = null;
	private List<ConfigurationUtilBean> emailColumnText = null;
	private List<GridDataPropertyView> viewEmailGrid = new ArrayList<GridDataPropertyView>();
	private JSONArray jsonArr = null;
	private Map<String, String> empolyeeMap = null;
	private List<Object> viewEmailData = null;

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
	private Map<String,String> moduleMap;
	private Map<String,String> count;


	public String beforeEmailSettingAdd()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			moduleMap =new LinkedHashMap<String, String>();
			moduleMap=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
			setbeforeEmailSettingAdd();
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private void setbeforeEmailSettingAdd()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			emailColumnText = new ArrayList<ConfigurationUtilBean>();
			emailColumnDropdown = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_email_configuration", accountID, connectionSpace, "", 0, "table_name", "email_configuration");
			if (msg != null)
			{
				for (GridDataPropertyView gdp : msg)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("date"))

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
						emailColumnText.add(obj);
					}
					else if (gdp.getColType().equalsIgnoreCase("D") )
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
						emailColumnDropdown.add(obj);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public String beforeEmailSettingViewHeader()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			count=new LinkedHashMap<String, String>();
			count=new GroupActionCtrl().fetchContactTypeCounters(connectionSpace, "status", "email_configuration_data");
			
			 moduleMap = new LinkedHashMap<String,String>();
			 moduleMap=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
		   
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeEmailSettingView()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setEmailSettingView();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setEmailSettingView()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewEmailGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_email_configuration", accountID, connectionSpace, "", 0, "table_name", "email_configuration");
			for (GridDataPropertyView gdp : msg)
			{
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewEmailGrid.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String insertEmailSettingAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_email_configuration", accountID, connectionSpace, "", 0, "table_name", "email_configuration");

				if (msg != null && msg.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					TableColumes ob1 = new TableColumes();
					for (GridDataPropertyView gdp : msg)
					{
						
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						if (gdp.getColomnName().equalsIgnoreCase("status")) 
						{
							ob1.setConstraint("default 'Active'");
						}
						else
						{
							ob1.setConstraint("default NULL");
						}
						tableColume.add(ob1);
					}
					cbt.createTable22("email_configuration_data", tableColume, connectionSpace);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase(""))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					//cbt.insertIntoTable("email_configuration_data",insertData,connectionSpace);
					  int maxId=cbt.insertDataReturnId("email_configuration_data",insertData,connectionSpace);
	                	if (maxId>0)
						{
	                		StringBuilder fieldsNames=new StringBuilder();
	                		StringBuilder fieldsValue=new StringBuilder();
	                		if (insertData!=null && !insertData.isEmpty())
							{
								int i=1;
								for (InsertDataTable h : insertData)
								{
									for(GridDataPropertyView gdp:msg)
									{
										if(h.getColName().equalsIgnoreCase(gdp.getColomnName()))
										{
											if (i < insertData.size())
											{
												fieldsNames.append(gdp.getHeadingName()+", ");
												if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
		                                    	{
													fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString())+", ");
		                                    	}
												else
												{
													fieldsValue.append(h.getDataName()+", ");
												}
												
											}
											else
											{
												fieldsNames.append(gdp.getHeadingName());
												if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
		                                    	{
													fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
		                                    	}
												else
												{
													fieldsValue.append(h.getDataName());
												}
											}
										}
									}
									i++;
								}
							}
	                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
	                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Email Setting",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
						
						}
					addActionMessage("Data Added Successfully !!!");
						return "success";
				}
				else
				{
					 addActionMessage("Oops there is some problem !!!");
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
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	@SuppressWarnings({ "rawtypes", "unused" })
	public String ViewEmailSetting()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from email_configuration_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_email_configuration", accountID, connectionSpace, "email_configuration");
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dynamic query based on selected fields
							    Object obdata=(Object)it.next();
							    if(obdata!=null)
							    {
								    if(i<fieldNames.size()-1)
								    	query.append(obdata.toString()+",");
								    else
								    	query.append(obdata.toString());
							    }
							    i++;
						     }
					    }
					query.append(" FROM email_configuration_data   ");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")&& !getSearchString().equalsIgnoreCase("-1"))
					{
						query.append(" WHERE ");
						//add search  query based on the search operation
						if (!getSearchField().equalsIgnoreCase("status")) 
						{
							query.append(" status='Active' AND ");
						}
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

					System.out.println("@@@@@@ " + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						viewEmailData = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
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
										if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                    		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()) );
										else if(fieldNames.get(k).toString().equalsIgnoreCase("moduleName"))
											one.put(fieldNames.get(k).toString(), getModulename(obdata[k].toString()));
										else
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									
									
								}
								else
								{
									one.put(fieldNames.get(k).toString(),"NA");
								}
							}
							Listhb.add(one);
						}
						setViewEmailData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getModulename(String fetchModuleNam)
	{
		String arr=null;
	
    	if (fetchModuleNam.equalsIgnoreCase("COMPL")) 
    	{
    		arr= "Operations Task";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("HDM")) 
    	{ 
    		arr= "Hepldesk";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("FM")) 
    	{ 
    		arr= "Patient Delight";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("WFPM")) 
    	{
    		arr= "Work Force";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("ASTM")) 
    	{
    		arr= "Asset";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("HR")) 
    	{
    		arr= "HR";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("DAR")) 
    	{
    		arr= "Projects";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("VAM")) 
    	{
    	  arr= "Visitor Alert";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("DREAM_HDM")) 
    	{
    	  arr= "Compliants";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("KR")) 
    	{
    	  arr= "KR";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("CS")) 
    	{
    	  arr= "Admin";
		}
    	else if (fetchModuleNam.equalsIgnoreCase("COM")) 
    	{
    	  arr= "Communication";
		}
    	else
    	{
    		arr= fetchModuleNam;
    	}
		return arr;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifyEmailGrid()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
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
							if (parmName.equalsIgnoreCase("status"))
							{
								wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
							}
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("email_configuration_data", wherClause, condtnBlock,connectionSpace);
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
            		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"email_configuration");
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
            			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Email Setting",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
    				}
				}
				else if(getOper().equalsIgnoreCase("del"))
				{
					 CommonOperInterface cbt = new CommonConFactory().createInterface(); 
					 String tempIds[] = getId().split(",");
					 StringBuilder condtIds = new StringBuilder(); 
					 Map<String, Object> wherClause = new HashMap<String, Object>();
					 Map<String, Object> condtnBlock = new HashMap<String, Object>();
					 int i = 1; 
					 for(String H : tempIds) 
					 { 
						 if (i < tempIds.length)
					          condtIds.append(H + " ,"); 
						 else 
							  condtIds.append(H); 
						 i++; 
					 }
					 wherClause.put("status","Inactive");
					 wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
					 condtnBlock.put("id", condtIds.toString());
					 cbt.updateTableColomn("email_configuration_data", wherClause,condtnBlock, connectionSpace);
					 StringBuilder fieldsNames=new StringBuilder();
					 StringBuilder dataStore=new StringBuilder();
             		if (wherClause!=null && !wherClause.isEmpty())
						{
							int i1=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
							    if (i1 < wherClause.size())
									fieldsNames.append("'"+entry.getKey() + "', ");
								else
									fieldsNames.append("'"+entry.getKey() + "' ");
								i1++;
							}
						}
             		UserHistoryAction UA=new UserHistoryAction();
             		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"email_configuration");
             		if (fieldValue!=null && fieldValue.size()>0)
					{
            			StringBuilder dataField =new StringBuilder();
            			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
    					{
    						Object[] object = (Object[]) iterator.next();
    						if (wherClause!=null && !wherClause.isEmpty())
    						{
    							 i=1;
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
            		new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "Email Setting", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
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
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	

	public Map<String, String> getEmpolyeeMap()
	{
		return empolyeeMap;
	}

	public void setEmpolyeeMap(Map<String, String> empolyeeMap)
	{
		this.empolyeeMap = empolyeeMap;
	}

	

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<GridDataPropertyView> getViewEmailGrid() {
		return viewEmailGrid;
	}

	public void setViewEmailGrid(List<GridDataPropertyView> viewEmailGrid) {
		this.viewEmailGrid = viewEmailGrid;
	}

	public List<ConfigurationUtilBean> getEmailColumnDropdown() {
		return emailColumnDropdown;
	}

	public void setEmailColumnDropdown(
			List<ConfigurationUtilBean> emailColumnDropdown) {
		this.emailColumnDropdown = emailColumnDropdown;
	}

	public List<ConfigurationUtilBean> getEmailColumnText() {
		return emailColumnText;
	}

	public void setEmailColumnText(List<ConfigurationUtilBean> emailColumnText) {
		this.emailColumnText = emailColumnText;
	}

	

	public Map<String, String> getModuleMap() {
		return moduleMap;
	}

	public void setModuleMap(Map<String, String> moduleMap) {
		this.moduleMap = moduleMap;
	}

	public List<Object> getViewEmailData() {
		return viewEmailData;
	}

	public void setViewEmailData(List<Object> viewEmailData) {
		this.viewEmailData = viewEmailData;
	}

	public Map<String, String> getCount()
	{
		return count;
	}

	public void setCount(Map<String, String> count)
	{
		this.count = count;
	}


}

