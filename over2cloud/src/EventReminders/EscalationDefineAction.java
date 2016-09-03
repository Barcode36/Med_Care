package EventReminders;

import com.Over2Cloud.action.GridPropertyBean;

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
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.commanOperation;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;



public class EscalationDefineAction extends GridPropertyBean implements ServletRequestAware
{
		private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
		private List<Object> masterViewList;
		static final Log log = LogFactory.getLog(EscalationDefineAction.class);
		private HttpServletRequest request;
		private List<ConfigurationUtilBean> bandDropdown;
		private List<ConfigurationUtilBean> bandTextBox;
		private Map<String, String> deptNameMap;
		private Map<String , String> moduleName;
		private String module=null;
		private String dept=null;
		private JSONArray jsonArray;

		public String beforeStandardEscalation()
		{

			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{ 
					deptNameMap = new LinkedHashMap<String,String>();
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					List subTypeList=cbt.executeAllSelectQuery("select distinct dept.id,dept.deptName from department as dept inner join compliance_contacts as compl on compl.fromDept_id=dept.id order by dept.deptName", connectionSpace);
					if(subTypeList!=null && subTypeList.size()>0)
					{
						for (Iterator iterator = subTypeList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object!=null)
							{
								if(object[0]!=null && object[1]!=null)
								{
									deptNameMap.put(object[0].toString(),object[1].toString());
								}
							}
						}
					}
					/*totalCount=getCounters(connectionSpace);
					System.out.println("totalCount" +totalCount);*/
					moduleName = new LinkedHashMap<String,String>();
					moduleName=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
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

		
		//getting free employee
		public String getfreeEmp(){
			String retrnresult=ERROR;
			boolean flag = ValidateSession.checkSession();
			if(flag){
				try{
					if(module!=null && !module.equalsIgnoreCase("") && dept!=null && !dept.equalsIgnoreCase("")){
						jsonArray = new JSONArray();
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						StringBuffer query=null;
						
						
								query = new StringBuffer("");
								query.append("select cc.id,emp.empName from compliance_contacts as cc");
								query.append(" inner join employee_basic as emp on emp.id=cc.emp_id where cc.forDept_id NOT IN ('"+dept+"') and cc.moduleName NOT IN ('"+module+"')");
								query.append(" group by emp.empName order by emp.empName");
								System.out.println("QRY"+query);
								List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if(data!=null && data.size()>0){
									for (Iterator iterator = data.iterator(); iterator
											.hasNext();) {
										Object[] object = (Object[]) iterator.next();
										if(object[0]!=null && object[1]!=null ){
											JSONObject obj= new JSONObject();
											obj.put("id", object[0].toString());
											obj.put("name", object[1].toString());
											jsonArray.add(obj);
									
			
			
									}
							
						}
						
					}
						
					}
					
					
					retrnresult=SUCCESS;
				}catch(Exception e){
					
				}
			}else{
				retrnresult=LOGIN;
				}
			return retrnresult;
			
		}
		/**
		 * Showing Fields of Add Page
		 */
		public String getStandardescalationdata()
		{
             String returnReulst=ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					if(module!=null && !module.equalsIgnoreCase("") && dept!=null && !dept.equalsIgnoreCase("")){
						jsonArray = new JSONArray();
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						StringBuffer query=null;
						
						for(int i=1;i<=5;i++){
								query = new StringBuffer("");
								query.append("select cc.id,emp.empName from compliance_contacts as cc");
								query.append(" inner join employee_basic as emp on emp.id=cc.emp_id where cc.forDept_id='"+dept+"' and cc.moduleName='"+module+"'");
								query.append(" and cc.level='"+i+"'");
								System.out.println("QRY"+query);
								List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if(data!=null && data.size()>0){
									for (Iterator iterator = data.iterator(); iterator
											.hasNext();) {
										Object[] object = (Object[]) iterator.next();
										if(object[0]!=null && object[1]!=null ){
											JSONObject obj= new JSONObject();
											obj.put("id",object[0].toString());
											obj.put("level"+i,object[1].toString());
											
											jsonArray.add(obj);
										}
			
			
									}
							
						}
						
					}
						
					}
					returnReulst=SUCCESS;
				} catch (Exception exp)
				{
					log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
					exp.printStackTrace();
					returnReulst=ERROR;
				}
			}else{
				returnReulst=LOGIN;
			}
			return returnReulst;
		}
		
		public String getempdetail(){
			String returnresult=ERROR;
			boolean flag=ValidateSession.checkSession();
			if(flag){
				try{
					jsonArray =  new JSONArray();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder query = new StringBuilder("");
					query.append("select dept.deptName,deptfor.deptName as fromDept,cc.moduleName,cc.level,emp.empName,emp.mobOne,emp.emailIdOne,emp.empLogo from compliance_contacts as cc");
					query.append(" INNER JOIN employee_basic as emp on emp.id=cc.emp_id");
					query.append(" INNER JOIN department as dept on dept.id=cc.forDept_id");
					query.append(" INNER JOIN department as deptfor on deptfor.id=cc.fromDept_id");
					query.append(" where cc.id="+dept+"");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					
					for (Iterator iterator = data.iterator(); iterator
							.hasNext();) {
						Object []object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null && object[4]!=null&& object[5]!=null && object[6]!=null){
							JSONObject obj = new JSONObject();
							obj.put("fordept", object[0].toString()==null?"NA":object[0].toString());
							obj.put("fromdept", object[1].toString()==null?"NA":object[1].toString());
							obj.put("module", object[2].toString()==null?"NA":object[2].toString());
							obj.put("level", object[3].toString()==null?"NA":object[3].toString());
							obj.put("empName", object[4].toString()==null?"NA":object[4].toString());
							obj.put("mobile", object[5].toString()==null?"NA":object[5].toString());
							obj.put("email", object[6].toString()==null?"NA":object[6].toString());
							try{
							obj.put("emplogo", object[7].toString()==null?"NA":object[7].toString());
							}catch(NullPointerException nl){
								obj.put("emplogo","NA");
							}
							jsonArray.add(obj);
						}
					}
					
					
					returnresult=SUCCESS;
				}catch(Exception e){
					returnresult=ERROR;
					e.printStackTrace();
				}
			}else{
				returnresult=LOGIN;
			}
			return returnresult;
		}
		public String beforeEmployeeBandView()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
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

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_band_configuration", accountID, connectionSpace, "", 0, "table_name", "band_employee_configuration");
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
		
		public String viewMapBandData()
		{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder query1 = new StringBuilder("");
					query1.append("select count(*) from band_mapped_employee");
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
						List<?> fieldNames = Configuration.getColomnList("mapped_band_configuration", accountID, connectionSpace, "band_employee_configuration");
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


		public String viewBandData()
		{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder query1 = new StringBuilder("");
					query1.append("select count(*) from band");
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
						List<?> fieldNames = Configuration.getColomnList("mapped_band_configuration", accountID, connectionSpace, "band_configuration");
						List<Object> Listhb = new ArrayList<Object>();
						int i = 0;
						for (Iterator<?> it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dyanamic query based on selected
							// fields

							obdata = (Object) it.next();
							if (obdata != null )
							{
								if (obdata.equals("departName"))
								{
									query1.append("dept.deptName,");
								} 
								else if (i < fieldNames.size() - 1)
								{
									query1.append("band." + obdata.toString() + ",");
								} 
								else
								{
									query1.append("band." + obdata.toString() + ",");
								}
							}
							i++;
						}
						query.append(query1.substring(0, query1.toString().length() - 1));
						query.append(" FROM band AS band ");
						query.append(" LEFT JOIN department AS dept ON dept.id=band.departName");
						query.append(" WHERE band.id!=0");
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
						query.append(" ORDER BY band.name ASC");
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

		public String beforeBandHeaderView()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					setViewProp();
					return SUCCESS;
				} catch (Exception exp)
				{
					log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Band  method beforeBandHeaderView of class " + getClass(), exp);
					exp.printStackTrace();
					return ERROR;
				}
			} else
			{
				return LOGIN;
			}
		}

		/**
		 * View Grid Column Name
		 */
		public void setViewProp()
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_band_configuration", accountID, connectionSpace, "", 0, "table_name", "band_configuration");
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

		public String beforeBandAdd()
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

			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_band_configuration", accountID, connectionSpace, "", 0, "table_name", "band_configuration");
					if (gridFields != null)
					{
						bandDropdown = new ArrayList<ConfigurationUtilBean>();
						bandTextBox = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : gridFields)
						{
							ConfigurationUtilBean objdata = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("D"))
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
							} else if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().trim().equalsIgnoreCase("userName") && !gdp.getColomnName().trim().equalsIgnoreCase("date") && !gdp.getColomnName().trim().equalsIgnoreCase("time"))
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
		}

		@SuppressWarnings("unchecked")
		public String addBand()
		{
			String result = ERROR;
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					result = LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				int count=0;
				String query = " select name from band where name='" + request.getParameter("name") + "' and departName='" + request.getParameter("departName")+"'";
				List<?> list = cbt.executeAllSelectQuery(query, connectionSpace);
				if (list != null && list.size() > 0)
				{
					addActionError(" Oops " + request.getParameter("name") + " for Same Department  Band Already Exists !!!");
					result = SUCCESS;
				} 
				else
				{
					List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_band_configuration", accountID, connectionSpace, "", 0, "table_name", "band_configuration");
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
						cbt.createTable22("band", Tablecolumesaaa, connectionSpace);

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
								if (Parmname.equalsIgnoreCase("name"))
								{
									bandName = request.getParameterValues(Parmname);
								}
								else if (Parmname.equalsIgnoreCase("brief"))
								{
									bandBrief = request.getParameterValues(Parmname);
								} 
								else
								{
									ob = new InsertDataTable();
									ob.setColName(Parmname);
									ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
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
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						InsertDataTable idt1 = new InsertDataTable();
						idt1.setColName("name");
						insertData.add(idt1);

						InsertDataTable idt2 = new InsertDataTable();
						idt2.setColName("brief");
						insertData.add(idt2);
						
						for (int i = 0; i < bandName.length; i++)
						{
								if (bandName[i].equalsIgnoreCase(""))
								{
									break;
								}
								idt1.setDataName(bandName[i]);
								idt2.setDataName(bandBrief[i]);
								cbt.insertIntoTable("band", insertData, connectionSpace);
								count++;
							}
						}

						if (count > 0)
						{
							addActionMessage(count + "Band Saved Successfully!!!");
							result = SUCCESS;
						} else
						{
							addActionMessage("Oops!!! There is some error in data.");
							result = SUCCESS;
						}
					}
			} catch (Exception e)
			{
				e.printStackTrace();
				result = ERROR;
			}
			return result;
		}
		
		/*public String viewModifyReferenceRecords()
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
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
								wherClause.put(Parmname, paramValue);
						}
						condtnBlock.put("id", getId());
						cbt.updateTableColomn("reference_records", wherClause, condtnBlock, connectionSpace);
					} else if (getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						String tempIds[] = getId().split(",");
						StringBuilder condtIds = new StringBuilder();
						int i = 1;
						for (String H : tempIds)
						{
							if (i < tempIds.length)
								condtIds.append(H + " ,");
							else
								condtIds.append(H);
							i++;
						}
						StringBuilder query = new StringBuilder();
						query.append("UPDATE band SET status='' WHERE id IN(" + condtIds + ")");
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);

						
						 * query.append(
						 * "UPDATE compliance_task SET status='InActive' WHERE taskType IN("
						 * + condtIds + ")"); cbt.updateTableColomn(connectionSpace,
						 * query);
						 
					}
					returnResult = SUCCESS;
				} catch (Exception exp)
				{
					exp.printStackTrace();
					log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewModifyTaskType of class " + getClass(), exp);
				}
			} else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		}
	*/
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

		@Override
		public void setServletRequest(HttpServletRequest arg0)
		{
			this.request = arg0;
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

		public Map<String, String> getModuleName() {
			return moduleName;
		}

		public void setModuleName(Map<String, String> moduleName) {
			this.moduleName = moduleName;
		}

		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		public String getDept() {
			return dept;
		}

		public void setDept(String dept) {
			this.dept = dept;
		}

		public JSONArray getJsonArray() {
			return jsonArray;
		}

		public void setJsonArray(JSONArray jsonArray) {
			this.jsonArray = jsonArray;
		}

	

}
