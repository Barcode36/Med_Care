package com.Over2Cloud.ctrl.compliance.complContacts;

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
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class ComplianceBranchOfficeAction extends GridPropertyBean implements ServletRequestAware {


	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewPropCountry = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewPropHead = new ArrayList<GridDataPropertyView>();
	private List<Object> masterViewList;
	static final Log log = LogFactory.getLog(ComplianceBranchOfficeAction.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> bandDropdown;
	private List<ConfigurationUtilBean> headofficedd;
	private List<ConfigurationUtilBean> bandTextBox;
	private Map<String, String> deptNameMap;
	private Map<String, String> headOfficeName;
	private String countryId;
	private JSONArray jsonArray;
	 
	private String deptName;
	private String selectedorgId;
	
	private String regLevel;
    private String countryoffc;
    private String headofficeId;
   private String groupId;
    private String  groupName;
    private String mappedOrgnztnId;
	public String beforeMapBand()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String query = "SELECT id,deptName FROM department WHERE flag='Active' ORDER BY deptName";
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					deptNameMap = new LinkedHashMap<String, String>();
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							deptNameMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
				/*setMapPageDataFields();*/
				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in KRA KPI  method beforeBandMap of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}
/*
	*//**
	 * Showing Fields of Add Page
	 *//*
	public void setMapPageDataFields()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_country_master", accountID, connectionSpace, "", 0, "table_name", "country_configuration");
				if (gridFields != null)
				{
					bandDropdown = new ArrayList<ConfigurationUtilBean>();
					bandTextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : gridFields)
					{
						ConfigurationUtilBean objdata = new ConfigurationUtilBean();
						 if (gdp.getColType().trim().equalsIgnoreCase("T") )
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							bandTextBox.add(objdata);
						}
					}
				}
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
	}*/
	
	public String beforeBranchOfficeConfig()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				/**
				 *  set Country Grid Column Name
				 *  
				 */
				
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewPropCountry.add(gpv);

				List<GridDataPropertyView> returnResult1 = Configuration.getConfigurationData("mapped_country_master", accountID, connectionSpace, "", 0, "table_name", "country_configuration");
				for (GridDataPropertyView gdp : returnResult1)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setWidth(gdp.getWidth());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewPropCountry.add(gpv);
				}
				
				/**
				 * Head Office Grid Column Name
				 */
				
				GridDataPropertyView gpvh = new GridDataPropertyView();
				gpvh.setColomnName("id");
				gpvh.setHeadingName("Id");
				gpvh.setHideOrShow("true");
				masterViewPropHead.add(gpvh);

				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_headoffice_master", accountID, connectionSpace, "", 0, "table_name", "headoffice_configuration");
				for (GridDataPropertyView gdp : returnResult)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setWidth(gdp.getWidth());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewPropHead.add(gpv);
				}
				
				
				
				setViewGridColumns();
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Band  method beforeBandHeaderView of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} 
		else
		{
			return LOGIN;
		}
	}
	
	public void setViewGridColumns()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_branch_master", accountID, connectionSpace, "", 0, "table_name", "branch_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setWidth(gdp.getWidth());
			gpv.setHideOrShow(gdp.getHideOrShow());
			masterViewProp.add(gpv);
		}
	}
	
	/*public String viewCountry()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from Country_data");
				List<?> dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator<?> it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("SELECT ");
					List<?> fieldNames = Configuration.getColomnList("mapped_country_master", accountID, connectionSpace, "country_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator<?> it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						obdata = (Object) it.next();
						if (obdata != null )
						{
							if (obdata.equals("bandName"))
							{
								query1.append("band.name,");
							} 
							else if (i < fieldNames.size() - 1)
							{
								query1.append("bme." + obdata.toString() + ",");
							} 
							else
							{
								query1.append("bme." + obdata.toString() + ",");
							}
						}
						i++;
					}
					query.append(query1.substring(0, query1.toString().length() - 1));
					query.append(" FROM band AS band_mapped_employee as bme ");
					query.append(" INNER JOIN band AS band ON band.id=bme.bandName");
					query.append(" INNER JOIN department AS dept ON dept.id=band.departName");
					query.append(" WHERE bme.id!=0");
					
					 * if (userType != null && !userType.equalsIgnoreCase("M"))
					 * { String str[] = new
					 * ComplianceUniversalAction().getEmpDataByUserName
					 * (userName); deptId = str[3];
					 * query.append("AND kr.departName=" + deptId); }
					 
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					query.append(" ORDER BY band.name ASC");
					System.out.println("band employee query>>>>>" + query);
					List<?> data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata11 = null;
						for (Iterator<?> it = data.iterator(); it.hasNext();)
						{
							obdata11 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata11[k] != null && !obdata11[k].toString().equalsIgnoreCase(""))
								{
									if (fieldNames.get(k).toString().equals("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata11[k].toString()));
									} else if (fieldNames.get(k).toString().equals("userName"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata11[k].toString()));
									} else
									{
										one.put(fieldNames.get(k).toString(), obdata11[k].toString());
									}
								} else
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
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewBandData of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
*/

	public String viewBranchOffice()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from branchoffice_data");
				List<?> dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator<?> it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("SELECT ");
					List<?> fieldNames = Configuration.getColomnList("mapped_branch_master", accountID, connectionSpace, "branch_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator<?> it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields

						obdata = (Object) it.next();
						if (obdata != null )
						{
							if (obdata.equals("countryId"))
							{
								query1.append("cdata.name as countryName,");
							}else if (obdata.equals("headOfficeId"))
							{
								query1.append("head.name as headOfficeName,");
							} 
							else if (i < fieldNames.size() - 1)
							{
								query1.append("branch." + obdata.toString() + ",");
							} 
							else
							{
								query1.append("branch." + obdata.toString() + ",");
							}
						}
						i++;
					}
					query.append(query1.substring(0, query1.toString().length() - 1));
					query.append(" FROM branchoffice_data as branch");
					query.append(" LEFT JOIN country_data as cdata on cdata.id=branch.countryId");
					query.append(" LEFT JOIN headoffice_data as head on head.id=branch.headOfficeId");
					query.append(" WHERE head.id!=0 and head.id='"+id+"'");
					/*
					 * if (userType != null && !userType.equalsIgnoreCase("M"))
					 * { String str[] = new
					 * ComplianceUniversalAction().getEmpDataByUserName
					 * (userName); deptId = str[3];
					 * query.append("AND kr.departName=" + deptId); }
					 */
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					System.out.println("band query>>>>>" + query);
					List<?> data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata11 = null;
						for (Iterator<?> it = data.iterator(); it.hasNext();)
						{
							obdata11 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata11[k] != null && !obdata11[k].toString().equalsIgnoreCase(""))
								{
									if (fieldNames.get(k).toString().equals("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata11[k].toString()));
									} else if (fieldNames.get(k).toString().equals("userName"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata11[k].toString()));
									} else
									{
										one.put(fieldNames.get(k).toString(), obdata11[k].toString());
									}
								} else
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
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewBandData of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	

public String getHeadOfficeList(){
	String returnresult=ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
		String query="SELECT id,name From headoffice_data where countryID='"+getCountryId()+"' ORDER BY name";
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
						obj.put("ID",object[0].toString() );
						obj.put("NAME", object[1].toString());
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
public String getBranchOfficeList()
{
	String returnresult=ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			String query="SELECT id,name From branchoffice_data where headOfficeId='"+getCountryId()+"' ORDER BY name";
			System.out.println("QUEry :::::::: "+query);
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
						obj.put("ID",object[0].toString() );
						obj.put("NAME", object[1].toString());
						jsonArray.add(obj);
					}
				}
			}
			returnresult = SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			returnresult = ERROR;
		}
	}
	else 
	{
		returnresult = LOGIN;
	}
	return returnresult;
}
	public String beforeBranchAdd()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String query = "SELECT id,name FROM country_data ORDER BY name";
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					deptNameMap = new LinkedHashMap<String, String>();
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							deptNameMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
				setAddPageDataFields();
				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in KRA KPI  method beforeBandAdd of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	/**
	 * Showing Fields of Add Page
	 */
	public void setAddPageDataFields()
	{

		
			try
			{
				List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_branch_master", accountID, connectionSpace, "", 0, "table_name", "branch_configuration");
				if (gridFields != null)
				{
					bandDropdown = new ArrayList<ConfigurationUtilBean>();
					bandTextBox = new ArrayList<ConfigurationUtilBean>();
					headofficedd = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : gridFields)
					{
						ConfigurationUtilBean objdata = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("D")&&gdp.getColomnName().equalsIgnoreCase("countryId"))
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							bandDropdown.add(objdata);
						}else if (gdp.getColType().trim().equalsIgnoreCase("D")&&gdp.getColomnName().equalsIgnoreCase("headOfficeId"))
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							headofficedd.add(objdata);
						}
							else
						 if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().trim().equalsIgnoreCase("userName") && !gdp.getColomnName().trim().equalsIgnoreCase("date") && !gdp.getColomnName().trim().equalsIgnoreCase("time"))
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							bandTextBox.add(objdata);
						}
					}
				}
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
				exp.printStackTrace();
			}
	
	}

	@SuppressWarnings("unchecked")
	public String addBranchOffice()
	{
		String result = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				result = LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			int count=0;
			String query = " select name from branchoffice_data where name='" + request.getParameter("name") + "'";
			List<?> list = cbt.executeAllSelectQuery(query, connectionSpace);
			if (list != null && list.size() > 0)
			{
				addActionError(" Oops " + request.getParameter("name") + " Already Exists !!!");
				result = SUCCESS;
			} 
			else
			{
				List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_branch_master", accountID, connectionSpace, "", 0, "table_name", "branch_configuration");
				if (org2 != null)
				{
					// create table query based on configuration
					for (GridDataPropertyView gdp : org2)
					{
						TableColumes ob1 = null;
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");

						Tablecolumesaaa.add(ob1);
						if (gdp.getColomnName().equalsIgnoreCase("userName"))
						{
						} else if (gdp.getColomnName().equalsIgnoreCase("date"))
						{
						} else if (gdp.getColomnName().equalsIgnoreCase("time"))
						{
						}
					}
					cbt.createTable22("branchoffice_data", Tablecolumesaaa, connectionSpace);

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator<String> it = requestParameterNames.iterator();
					String[] bandName = null;
					String[] bandBrief = null;

					InsertDataTable ob = null;
					while (it.hasNext())
					{
						String Parmname = it.next().toString();// control Name
						//request.getParameter(Parmname);

						if (Parmname != null && !Parmname.trim().equals(""))
						{
						
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
								insertData.add(ob);
							
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					
				
					cbt.insertIntoTable("branchoffice_data", insertData, connectionSpace);
					count++;
					

					if (count > 0)
					{
						addActionMessage("Branch Office Saved Successfully!!!");
						result = SUCCESS;
					} else
					{
						addActionMessage("Oops!!! There is some error in data.");
						result = SUCCESS;
					}
				}}
		} catch (Exception e)
		{
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}
	

//	modified By  Mohd kadir  on 10-06-2016
	
	
	public String fetchBranchValid()
	{
		System.out.println("*******&&&&&&&&&&******Check validation for branch " );
		String  result=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder("");
			query.append("  ");
		 
				JSONObject obj = new JSONObject();
				 query.append("select dept.id,country.name as country,head.name as head,dept.deptName,dept.orgnztnlevel,branch.name,dept.userName,dept.flag,grp.groupName,dept.headofficeId,dept.regLevel from department as dept");
			
				 query.append(" LEFT join groupinfo as grp on grp.id=dept.groupId ");
				 query.append(" LEFT join branchoffice_data as branch on grp.regLevel=branch.id  ");
				query.append(" LEFT JOIN headoffice_data as head on head.id=branch.headOfficeId ");
				query.append(" LEFT JOIN country_data as country on country.id=head.countryId ");
				query.append(" WHERE   dept.flag='Active'  and dept.headofficeId='"+headofficeId+"' and dept.reglevel='"+regLevel+"' and dept.groupId='"+groupId+"' and dept.deptName='"+deptName+"' ORDER BY groupName DESC ");
				System.out.println("    "+groupName+"QUEry ::::::::>>>>OOOO "+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					jsonArray = new JSONArray();
					System.out.println("json object >>>>>>>>>>>> "+jsonArray);
				System.out.println("json object >>>>>>>>>>>> "+	dataList);
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("YES","All Ready Exist");
					 
							
							jsonArray.add(obj);
							System.out.println("ID        "+object[0].toString());
							System.out.println("name11    "+object[1].toString());
							System.out.println("name22    "+object[2].toString());
							System.out.println("name33    "+object[3].toString());
							System.out.println("department44    "+object[4].toString());
							System.out.println("department55   "+object[5].toString());
							System.out.println("name11    "+object[6].toString());
							System.out.println("name22    "+object[7].toString());
							System.out.println("name33    "+object[8].toString());
							System.out.println("department44    "+object[9].toString());
							System.out.println("department55   "+object[10].toString());
						}
					
					}
					
				}

				System.out.println("  SIZE               "+jsonArray.size());
				result = SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = ERROR;
			}
		}
		else 
		{
			result = LOGIN;
		}
		return result;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}


	public List<ConfigurationUtilBean> getBandDropdown()
	{
		return bandDropdown;
	}

	public void setBandDropdown(List<ConfigurationUtilBean> bandDropdown)
	{
		this.bandDropdown = bandDropdown;
	}

	public List<ConfigurationUtilBean> getBandTextBox()
	{
		return bandTextBox;
	}

	public void setBandTextBox(List<ConfigurationUtilBean> bandTextBox)
	{
		this.bandTextBox = bandTextBox;
	}

	public Map<String, String> getDeptNameMap()
	{
		return deptNameMap;
	}

	public void setDeptNameMap(Map<String, String> deptNameMap)
	{
		this.deptNameMap = deptNameMap;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}
	public Map<String, String> getHeadOfficeName() {
		return headOfficeName;
	}
	public void setHeadOfficeName(Map<String, String> headOfficeName) {
		this.headOfficeName = headOfficeName;
	}
	public List<ConfigurationUtilBean> getHeadofficedd() {
		return headofficedd;
	}
	public void setHeadofficedd(List<ConfigurationUtilBean> headofficedd) {
		this.headofficedd = headofficedd;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public List<GridDataPropertyView> getMasterViewPropCountry() {
		return masterViewPropCountry;
	}
	public void setMasterViewPropCountry(
			List<GridDataPropertyView> masterViewPropCountry) {
		this.masterViewPropCountry = masterViewPropCountry;
	}
	public List<GridDataPropertyView> getMasterViewPropHead() {
		return masterViewPropHead;
	}
	public void setMasterViewPropHead(List<GridDataPropertyView> masterViewPropHead) {
		this.masterViewPropHead = masterViewPropHead;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public String getSelectedorgId()
	{
		return selectedorgId;
	}
	public void setSelectedorgId(String selectedorgId)
	{
		this.selectedorgId = selectedorgId;
	}
	public String getRegLevel()
	{
		return regLevel;
	}
	public void setRegLevel(String regLevel)
	{
		this.regLevel = regLevel;
	}
	public String getCountryoffc()
	{
		return countryoffc;
	}
	public void setCountryoffc(String countryoffc)
	{
		this.countryoffc = countryoffc;
	}
	public String getHeadofficeId()
	{
		return headofficeId;
	}
	public void setHeadofficeId(String headofficeId)
	{
		this.headofficeId = headofficeId;
	}
	public String getGroupId()
	{
		return groupId;
	}
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	public String getMappedOrgnztnId()
	{
		return mappedOrgnztnId;
	}
	public void setMappedOrgnztnId(String mappedOrgnztnId)
	{
		this.mappedOrgnztnId = mappedOrgnztnId;
	}



}
