package com.Over2Cloud.ctrl.smsEmailDraft;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.loader.custom.Return;

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
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.hr.group.GroupActionCtrl;
import com.Over2Cloud.emailSetting.EmailSetting;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.net.httpserver.Authenticator.Success;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
@SuppressWarnings("serial")
public class SmsEmailDraft extends ActionSupport implements ServletRequestAware
{
	
	private HttpServletRequest request;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
    private List<GridDataPropertyView> viewEmailDraftGrid=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewSmsDraftGrid=new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> smsColumnText = null;
	private List<ConfigurationUtilBean> smsColumnDrop = null;
	private List<ConfigurationUtilBean> emaildraftColumnText = null;
	private List<Object> viewsmsData = null;
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
	private Map<String,String> totalMap;
	private Map<String, String> moduleMap;
	private List<ConfigurationUtilBean> emaildraftColumnDrop;
	
	public String viewsmsdraftHeader()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			totalMap = new LinkedHashMap<String, String>();
			totalMap=new GroupActionCtrl().fetchContactTypeCounters(connectionSpace, "status", "sms_draft_data");
			moduleMap=new LinkedHashMap<String,String>();
			moduleMap=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String viewEmaildraftsHeader()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			totalMap = new LinkedHashMap<String, String>();
			totalMap=new GroupActionCtrl().fetchContactTypeCounters(connectionSpace, "status", "email_draft_data");
			moduleMap=new LinkedHashMap<String,String>();
			moduleMap=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String viewEmaildrafts()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setEmaildraftView();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private void setEmaildraftView()
	{

		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewEmailDraftGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_email_draft_configuration", accountID, connectionSpace, "", 0, "table_name", "email_draft_configuration");
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
					viewEmailDraftGrid.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	
	}
	
	@SuppressWarnings({ "rawtypes" })
	public String ViewEmailDraftInGrid()
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
				query1.append("select count(*) from email_draft_data");
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_email_draft_configuration", accountID, connectionSpace, "email_draft_configuration");
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
					query.append(" from email_draft_data  ");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")&& !getSearchString().equalsIgnoreCase("-1"))
					{
						query.append(" where ");
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

					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						EmailSetting ES=new EmailSetting();
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
											
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										else if(fieldNames.get(k).toString().equalsIgnoreCase("module"))
											one.put(fieldNames.get(k).toString(), ES.getModulename(obdata[k].toString()));
										
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
	
	
	
	public String addEmaildraft()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			moduleMap=new LinkedHashMap<String,String>();
			moduleMap=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
			setaddEmaildraft();
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private void setaddEmaildraft()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			emaildraftColumnText = new ArrayList<ConfigurationUtilBean>();
			emaildraftColumnDrop = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_email_draft_configuration", accountID, connectionSpace, "", 0, "table_name", "email_draft_configuration");
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
						emaildraftColumnText.add(obj);
					}
					else if(gdp.getColType().trim().equalsIgnoreCase("D"))
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
						emaildraftColumnDrop.add(obj);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifyEmailDraftInGrid()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
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
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && Parmname != null&& !Parmname.equalsIgnoreCase("tableName")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
						{
							wherClause.put(Parmname, paramValue);
							if (Parmname.equalsIgnoreCase("status"))
							{
								wherClause.put("deactive",DateUtil.getCurrentDateUSFormat());
							}
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("email_draft_data", wherClause,condtnBlock, connectionSpace);

            		StringBuilder fieldsNames=new StringBuilder();
            		StringBuilder dataStore=new StringBuilder();
            		StringBuilder dataField=new StringBuilder();
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
            		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"email_draft_configuration");
            		if (fieldValue!=null && fieldValue.size()>0)
					{
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
            			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Email Draft",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
            		
				
				}
			    else if (getOper().equalsIgnoreCase("del"))
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
					 wherClause.put("deactive",DateUtil.getCurrentDateUSFormat());
					 condtnBlock.put("id", condtIds.toString());
					 cbt.updateTableColomn("email_draft_data", wherClause,condtnBlock, connectionSpace);
					 StringBuilder fieldsNames=new StringBuilder();
					 StringBuilder dataStore=new StringBuilder();
                		StringBuilder dataField=new StringBuilder();
                		if (wherClause!=null && !wherClause.isEmpty())
						{
							 i=1;
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
                		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"email_draft_configuration");
                		if (fieldValue!=null && fieldValue.size()>0)
						{
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
                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "Email Draft", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
			   }
				return  SUCCESS;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String insertEmaildraft()
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
				List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_email_draft_configuration", accountID, connectionSpace, "", 0, "table_name", "email_draft_configuration");

				if (msg != null && msg.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : msg)
					{
						TableColumes ob1 = new TableColumes();
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

					cbt.createTable22("email_draft_data", tableColume, connectionSpace);

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
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					int maxId=cbt.insertDataReturnId("email_draft_data",insertData,connectionSpace);
					 
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
	            		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Email Draft",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
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
	public String viewsmsdraft()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setsmsEmaildraft();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}
	


	private void setsmsEmaildraft()
	{

		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewSmsDraftGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_sms_draft_configuration", accountID, connectionSpace, "", 0, "table_name", "sms_draft_configuration");
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
					viewSmsDraftGrid.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public String ViewSmsDraftInGrid()
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
				query1.append("select count(*) from sms_draft_data");
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
					List fieldNames = Configuration.getColomnList("mapped_sms_draft_configuration", accountID, connectionSpace, "sms_draft_configuration");
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
					query.append(" from sms_draft_data  ");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("") &&  !getSearchString().equalsIgnoreCase("-1"))
					{
						query.append(" where ");
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

					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						EmailSetting ES=new EmailSetting();
						viewsmsData = new ArrayList<Object>();
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
											
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										else if(fieldNames.get(k).toString().equalsIgnoreCase("module"))
											one.put(fieldNames.get(k).toString(), ES.getModulename(obdata[k].toString()));
										
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
						setViewsmsData(Listhb);
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

	
	public String addsmsdraft()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setaddsmsdraft();
			moduleMap=new LinkedHashMap<String,String>();
			moduleMap=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private void setaddsmsdraft()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			smsColumnText = new ArrayList<ConfigurationUtilBean>();
			smsColumnDrop = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_sms_draft_configuration", accountID, connectionSpace, "", 0, "table_name", "sms_draft_configuration");
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
						smsColumnText.add(obj);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("date"))

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
						smsColumnDrop.add(obj);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String insertsmsdraft()
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
				List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_sms_draft_configuration", accountID, connectionSpace, "", 0, "table_name", "sms_draft_configuration");

				if (msg != null && msg.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : msg)
					{
						TableColumes ob1 = new TableColumes();
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

					cbt.createTable22("sms_draft_data", tableColume, connectionSpace);

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
					
					int maxId=cbt.insertDataReturnId("sms_draft_data",insertData,connectionSpace);
					 
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
	            		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "SMS Draft",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifySmsDraftInGrid()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
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
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && Parmname != null&& !Parmname.equalsIgnoreCase("tableName")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
						{
							wherClause.put(Parmname, paramValue);
							if (Parmname.equalsIgnoreCase("status"))
							{
								wherClause.put("deactive",DateUtil.getCurrentDateUSFormat());
							}
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("sms_draft_data", wherClause,condtnBlock, connectionSpace);

            		StringBuilder fieldsNames=new StringBuilder();
            		StringBuilder dataStore=new StringBuilder();
            		StringBuilder dataField=new StringBuilder();
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
            		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"sms_draft_configuration");
            		if (fieldValue!=null && fieldValue.size()>0)
					{
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
            			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "SMS Draft",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
            		
				
				}
			    else if (getOper().equalsIgnoreCase("del"))
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
					 wherClause.put("deactive",DateUtil.getCurrentDateUSFormat());
					 condtnBlock.put("id", condtIds.toString());
					 cbt.updateTableColomn("sms_draft_data", wherClause,condtnBlock, connectionSpace);
					 StringBuilder fieldsNames=new StringBuilder();
					 StringBuilder dataStore=new StringBuilder();
                		StringBuilder dataField=new StringBuilder();
                		if (wherClause!=null && !wherClause.isEmpty())
						{
							 i=1;
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
                		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"sms_draft_configuration");
                		if (fieldValue!=null && fieldValue.size()>0)
						{
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
                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "SMS Draft", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
			   }
				return  SUCCESS;
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

	public List<GridDataPropertyView> getViewEmailDraftGrid() {
		return viewEmailDraftGrid;
	}

	public void setViewEmailDraftGrid(List<GridDataPropertyView> viewEmailDraftGrid) {
		this.viewEmailDraftGrid = viewEmailDraftGrid;
	}

	public List<GridDataPropertyView> getViewSmsDraftGrid() {
		return viewSmsDraftGrid;
	}

	public void setViewSmsDraftGrid(List<GridDataPropertyView> viewSmsDraftGrid) {
		this.viewSmsDraftGrid = viewSmsDraftGrid;
	}

	public List<ConfigurationUtilBean> getSmsColumnText() {
		return smsColumnText;
	}

	public void setSmsColumnText(List<ConfigurationUtilBean> smsColumnText) {
		this.smsColumnText = smsColumnText;
	}

	public List<ConfigurationUtilBean> getEmaildraftColumnText() {
		return emaildraftColumnText;
	}

	public void setEmaildraftColumnText(
			List<ConfigurationUtilBean> emaildraftColumnText) {
		this.emaildraftColumnText = emaildraftColumnText;
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

	public List<Object> getViewsmsData() {
		return viewsmsData;
	}

	public void setViewsmsData(List<Object> viewsmsData) {
		this.viewsmsData = viewsmsData;
	}

	public List<Object> getViewEmailData() {
		return viewEmailData;
	}

	public void setViewEmailData(List<Object> viewEmailData) {
		this.viewEmailData = viewEmailData;
	}

	public Map<String,String> getTotalMap()
	{
		return totalMap;
	}

	public void setTotalMap(Map<String,String> totalMap)
	{
		this.totalMap = totalMap;
	}


	public List<ConfigurationUtilBean> getSmsColumnDrop()
	{
		return smsColumnDrop;
	}


	public void setSmsColumnDrop(List<ConfigurationUtilBean> smsColumnDrop)
	{
		this.smsColumnDrop = smsColumnDrop;
	}


	public Map<String, String> getModuleMap()
	{
		return moduleMap;
	}


	public void setModuleMap(Map<String, String> moduleMap)
	{
		this.moduleMap = moduleMap;
	}


	public List<ConfigurationUtilBean> getEmaildraftColumnDrop()
	{
		return emaildraftColumnDrop;
	}


	public void setEmaildraftColumnDrop(List<ConfigurationUtilBean> emaildraftColumnDrop)
	{
		this.emaildraftColumnDrop = emaildraftColumnDrop;
	}
	

}
