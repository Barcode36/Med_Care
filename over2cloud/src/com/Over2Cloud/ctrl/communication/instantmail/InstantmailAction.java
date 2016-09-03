package com.Over2Cloud.ctrl.communication.instantmail;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.apache.commons.io.FileUtils;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.FilesUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.communication.blackList.BlackListBean;
import com.Over2Cloud.ctrl.communication.instantMsg.InstantMessageBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class InstantmailAction extends ActionSupport implements
			ServletRequestAware {
		private HttpServletRequest request;
		@SuppressWarnings("unchecked")
		Map session = ActionContext.getContext().getSession();
		String userName = (String) session.get("uName");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				
		private List<GridDataPropertyView> viewInstantMsgGrid = new ArrayList<GridDataPropertyView>();
		private List<ConfigurationUtilBean> mobileTextBox = null;
		private List<ConfigurationUtilBean> writemessageTextBox = null;
		private List<ConfigurationUtilBean> messageNameDropdown = null;
		private Map<Integer, String> deptList = null;
		private Map<Integer, String> serviceDeptList = null;
		private String deptHierarchy;
		boolean checkdept = false;
		private String id;
		private List<ConfigurationUtilBean> subDept_DeptLevelName = null;
		private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
		private String dept;
		private String deptOrSubDeptId;
		private String destination;
		Map<Integer, String> Listmessage = new LinkedHashMap<Integer, String>();
		private Map<Integer, String> messageNameList = new HashMap<Integer, String>();
		private String message;
		Map<Integer, String> template = new LinkedHashMap<Integer, String>();
		private Map<String, String> groupNameListData = new HashMap<String, String>();
		private Map<String, String> templateNameList = new HashMap<String, String>();
		private Map<String, String> contactperson = new HashMap<String, String>();
		Map<Integer, String> mapObject = new HashMap<Integer, String>();
		private Map<String, String> contactListData = new HashMap<String, String>();
		private String excel;
		private String excelFileName;
		public Map<String, Object> columnNameMap = null;
		public String upload4;
		private List<Object> masterViewList = null;
		private Map<String, String> duplicatenum = new HashMap<String, String>();
		private Map<String, String> groupcontactNameListData = new HashMap<String, String>();
		private List<ConfigurationUtilBean> frequency = null;
		private boolean isValid;
		BlackListBean blacklistt = new BlackListBean();
		List<InstantMessageBean> dataList = null;
		List<InstantMessageBean> vdataList = null;
		List<InstantMessageBean> invdataList = null;
		List<InstantMessageBean> bdataList = null;
		List<InstantMessageBean> dudataList = null;
		List<InstantMessageBean> allDataList=null;
		Set<InstantMessageBean> set = null;
		private String mobileNo;
		private String writeMessage;
		private String name;
		private String writemail;
		private String email_id;
		private String validNum;
		private String duplicateData;
		private String invalidNum;
		private String blacklist;
		private String status;
		private String subDeptId = null;
		private String contactId = null;
		private String groupId = null;
		private String mobNo = null;
		private String msgText = null;
		private String deptId = null;
		private String from_email;
		private String csvNumber;
		private String subject;
		private String check_list;
		
		private String frequencys=null;
		private String date=null;
		private String hours=null;
		private String day=null;
		private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
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
		private String messageName = null;
		private Pattern pattern;
		private Matcher matcher;
		private File[] docs=null;
		protected String[] docsFileName;
		private String docfilename;
		public String beforeinstantmessageView() {
			// System.out.println("inside method....");
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				setinstantmessageView();

			} catch (Exception e) {

				e.printStackTrace();
			}
			return SUCCESS;
		}

		private void setinstantmessageView() {

			// System.out.println("inside This method....");
			try {
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewInstantMsgGrid.add(gpv);

				List<GridDataPropertyView> returnResult = Configuration
						.getConfigurationData("mapped_instant_msg_configuration",
								accountID, connectionSpace, "", 0, "table_name",
								"instant_msg_configuration");
				for (GridDataPropertyView gdp : returnResult) {
					if (!gdp.getColomnName().equalsIgnoreCase("frequency") && !gdp.getColomnName().equalsIgnoreCase("msgtype")&& !gdp.getColomnName().equalsIgnoreCase("messageName")&& !gdp.getColomnName().equalsIgnoreCase("messageDraft")) {
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						gpv.setAlign(gdp.getAlign());
						viewInstantMsgGrid.add(gpv);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String beforeInstantmailsend() {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				setInstantMessageDataFields();
				// Code
			} catch (Exception e) {

				addActionError("Ooops There Is Some Problem !");
				e.printStackTrace();
			}

			return SUCCESS;
		}

		private String setInstantMessageDataFields() {
			String returnResult = ERROR;
			try {
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				mobileTextBox = new ArrayList<ConfigurationUtilBean>();
				writemessageTextBox = new ArrayList<ConfigurationUtilBean>();
				messageNameDropdown = new ArrayList<ConfigurationUtilBean>();
				frequency = new ArrayList<ConfigurationUtilBean>();
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				List<GridDataPropertyView> instantMsg = Configuration
						.getConfigurationData("mapped_instant_mail_configuration",
								accountID, connectionSpace, "", 0, "table_name",
								"instant_mail_configuration");
				if (instantMsg != null) {
					for (GridDataPropertyView gdp : instantMsg) {
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						
						if (gdp.getColType().trim().equalsIgnoreCase("T")&& gdp.getColomnName().equalsIgnoreCase("email_id")) {
							
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							mobileTextBox.add(obj);
						
							
						} else if (gdp.getColType().trim().equalsIgnoreCase("T")&& gdp.getColomnName().equalsIgnoreCase("writemail")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							writemessageTextBox.add(obj);
						}

						else if (gdp.getColType().trim().equalsIgnoreCase("D")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("msgtype")
								&& !gdp.getColomnName().equalsIgnoreCase("subDept")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							messageNameDropdown.add(obj);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("R")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setColType(gdp.getColType());
							obj.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							frequency.add(obj);
						}


					}
				}

				/* Get Contact group name */
				CommonforClassdata obhj = new CommonOperAtion();
				Map<String, Object> whereClose = new HashMap<String, Object>();
				List<String> listdata = new ArrayList<String>();
				listdata.add("id");
				listdata.add("groupName");
				List datatemp = obhj.getSelectdataFromSelectedFields("groupinfo",listdata, whereClose, connectionSpace);
				groupcontactNameListData.put("all_contactType", "All Contact Type");
				for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					groupcontactNameListData.put(object[0].toString(), object[1]
							.toString());
				}
				Map<String, String> sortedMap = obhj.sortByComparator(groupcontactNameListData);
				setGroupcontactNameListData(sortedMap);

				
				/* Get group name */ 
				
				List<String> listdatas = new ArrayList<String>();
				listdatas.add("id");
				listdatas.add("name");
				
				List datatempz = obhj.getSelectdataFromSelectedFields("group_name",
						listdatas, whereClose, connectionSpace);
				for (Iterator iterator = datatempz.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					groupNameListData.put(object[0].toString(), object[1]
							.toString());
				}
		
				//* Get template name */ 
				List<String> listtemplate = new ArrayList<String>();
				listtemplate.add("id");
				listtemplate.add("template_name");
				
				List templdata = obhj.getSelectdataFromSelectedFields("mail_template",
						listtemplate, whereClose, connectionSpace);
				for (Iterator iterator = templdata.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					templateNameList.put(object[0].toString(), object[1]
							.toString());
				}
				
				
				Map<String, String> sortedMaps = obhj.sortByComparator(templateNameList);
				setTemplateNameList(sortedMaps);
				
				/* Get Department Data */

				// Getting Id, Dept Name for Non Service Department

				returnResult = SUCCESS;

			} catch (Exception e) {
				e.printStackTrace();
				returnResult = ERROR;
			}

			return returnResult;
		}

		public String templatetext() {
			String returnResult = ERROR;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			@SuppressWarnings("unused")
			String writeMessage = null;
			if (getMessage() != null) {
				String query = "SELECT template_body FROM mail_template where id='"
						+ getMessage() + "'";
				List getlist2 = cbt.executeAllSelectQuery(query, connectionSpace);
				if (getlist2 != null && getlist2.size() > 0) {
					messageName = getlist2.get(0).toString();
				}

				returnResult = SUCCESS;

			}
			return returnResult;
		}
		
		@SuppressWarnings("unchecked")
		public List getDataFromTable(String tableName, List<String> colmName,
				Map<String, Object> wherClause, Map<String, Object> order,
				SessionFactory connection) {
			List Data = null;
			StringBuilder selectTableData = new StringBuilder("");
			selectTableData.append("select ");
			// Set the columns name of a table
			if (colmName != null && !colmName.equals("") && !colmName.isEmpty()) {
				int i = 1;
				for (String H : colmName) {
					if (i < colmName.size())
						selectTableData.append(H + " ,");
					else
						selectTableData.append(H + " from " + tableName);
					i++;
				}
			}
			// Here we get the whole data of a table
			else {
				selectTableData.append(" * from " + tableName);
			}
			// Set the values for where clause
			if (wherClause != null && !wherClause.isEmpty()) {
				if (wherClause.size() > 0) {
					selectTableData.append(" where ");
				}
				int size = 1;
				Set set = wherClause.entrySet();
				Iterator i = set.iterator();
				while (i.hasNext()) {
					Map.Entry me = (Map.Entry) i.next();
					if (size < wherClause.size())
						selectTableData.append((String) me.getKey() + " = "
								+ me.getValue() + " and ");
					else
						selectTableData.append((String) me.getKey() + " = '"
								+ me.getValue() + "'");
					size++;
				}
			}
			// Set the value for type of order for getting data in specific order
			if (order != null && !order.isEmpty()) {
				Set set = order.entrySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					selectTableData.append(" ORDER BY " + me.getKey() + " "
							+ me.getValue() + "");
				}
			}
			selectTableData.append(";");
			Session session = null;
			Transaction transaction = null;
			try {
				session = connection.openSession();
				transaction = session.beginTransaction();
				Data = session.createSQLQuery(selectTableData.toString()).list();
				transaction.commit();
			} catch (Exception ex) {
				transaction.rollback();
			} 
			return Data;
		}

		@SuppressWarnings("unchecked")
		public List getServiceDept_SubDept(String deptLevel, String orgLevel,
				String orgId, SessionFactory connection) {
			boolean flag = new HelpdeskUniversalHelper().checkTable("department",
					connection);
			List dept_subdeptList = null;
			String query = "";
			try {
				if (deptLevel.equals("2")) {
					query = "select id, deptName from department where orgnztnlevel='"
							+ orgLevel
							+ "' and mappedOrgnztnId='"
							+ orgId
							+ "' and id in (select distinct(deptid) from subdepartment where id in (select distinct(dept_subdept) from feedback_type where deptHierarchy='"
							+ deptLevel + "')) ORDER BY deptName ASC;";
				} else if (deptLevel.equals("1")) {
					query = "select id, deptName from department where orgnztnlevel='"
							+ orgLevel
							+ "' and mappedOrgnztnId='"
							+ orgId
							+ "' and id in (select distinct(deptid) from subdepartment where id in (select distinct(dept_subdept) from feedback_type where deptHierarchy='"
							+ deptLevel + "')) ORDER BY deptName ASC;";
				}
				if (flag) {
					Session session = null;
					Transaction transaction = null;
					session = connection.openSession();
					transaction = session.beginTransaction();
					dept_subdeptList = session.createSQLQuery(query).list();
					transaction.commit();
				}
			} catch (SQLGrammarException e) {
				e.printStackTrace();
			}
			return dept_subdeptList;
		}

		public void setsubDept_DeptTags(String level) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				List<GridDataPropertyView> deptList = Configuration
						.getConfigurationData("mapped_dept_config_param",
								accountID, connectionSpace, "", 0, "table_name",
								"dept_config_param");
				subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
				if (deptList != null && deptList.size() > 0) {
					ConfigurationUtilBean cub = new ConfigurationUtilBean();
					for (GridDataPropertyView gdv : deptList) {
						if (gdv.getColomnName().equalsIgnoreCase("deptName")) {
							cub.setKey(gdv.getColomnName());
							cub.setValue(gdv.getHeadingName());
							cub.setColType("D");
							cub.setValidationType(gdv.getValidationType());
							if (gdv.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							subDept_DeptLevelName.add(cub);
						}
					}
				}
				if (level.equals("2")) {
					List<GridDataPropertyView> subdept_deptList = Configuration
							.getConfigurationData("mapped_subdeprtmentconf",
									accountID, connectionSpace, "", 0,
									"table_name", "subdeprtmentconf");
					if (subdept_deptList != null && subdept_deptList.size() > 0) {
						ConfigurationUtilBean cub = new ConfigurationUtilBean();
						for (GridDataPropertyView gdv1 : subdept_deptList) {
							if (gdv1.getColomnName()
									.equalsIgnoreCase("subdeptname")) {
								cub.setKey(gdv1.getColomnName());
								cub.setValue(gdv1.getHeadingName());
								cub.setColType("D");
								cub.setValidationType(gdv1.getValidationType());
								if (gdv1.getMandatroy().equalsIgnoreCase("1")) {
									cub.setMandatory(true);
								} else {
									cub.setMandatory(false);
								}
								subDept_DeptLevelName.add(cub);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		public String getSubDepartment() {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {
				try {
					SessionFactory connectionSpace = (SessionFactory) session
							.get("connectionSpace");
					List list = new ArrayList<String>();
					list.add("id");
					list.add("subdeptname");
					Map<String, Object> temp = new HashMap<String, Object>();
					Map<String, Object> order = new HashMap<String, Object>();
					order.put("subdeptname", "ASC");
					temp.put("deptid", getDept());
					list = getDataFromTable("subdepartment", list, temp, order,
							connectionSpace);
					if (list.size() > 0 && list != null) {
						for (Object c : list) {
							Object[] dataC = (Object[]) c;
							subDeptList
									.put((Integer) dataC[0], dataC[1].toString());
						}
					}
					if (destination != null) {
						destination = destination.replace("subDeptDiv", "");
					}
					returnResult = SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				returnResult = LOGIN;
			}
			return returnResult;
		}



		@SuppressWarnings("unchecked")
		private boolean isMobileBlacklisted(String mobileNo2) {
			boolean flag = false;
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String query = "SELECT mobileno FROM blacklist";
				List data = cbt.executeAllSelectQuery(query, connectionSpace);

				if (data != null && data.size() > 0) {
					for (Iterator iterator = data.iterator(); iterator.hasNext();) {
						Object object = (Object) iterator.next();
						if (object != null) {
							if (object.toString().equalsIgnoreCase(mobileNo2)) {
								flag = true;
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return flag;
		}

		private boolean isMobileDublicate(Map<String,String> duplicateMob,String mobNo2) {
			boolean flag = false;
			try {
				if(duplicateMob.containsKey(mobNo2))
					flag=true;
				else
					flag=false;
						
				/*CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String query = "SELECT mobileNo FROM instantmsg";
				List data = cbt.executeAllSelectQuery(query, connectionSpace);

				if (data != null && data.size() > 0) {
					for (Iterator iterator = data.iterator(); iterator.hasNext();) {
						Object object = (Object) iterator.next();
						if (object != null) {
							if (object.toString().equalsIgnoreCase(mobNo2)) {
								flag = true;
							}
						}
					}
				}*/
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return flag;
		}

		@SuppressWarnings("unchecked")
		public String viewInstantMsgDataGrid() {
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {
				try {
					String accountID = (String) session.get("accountid");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder query1 = new StringBuilder("");
					query1.append("select count(*) from msg_stats");
					List dataCount = cbt.executeAllSelectQuery(query1.toString(),
							connectionSpace);
					if (dataCount != null && dataCount.size() > 0) {
						BigInteger count = new BigInteger("1");
						for (Iterator it = dataCount.iterator(); it.hasNext();) {
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
						List fieldNames = Configuration.getColomnList(
								"mapped_instant_msg_configuration", accountID,
								connectionSpace, "instant_msg_configuration");
						int i = 0;
						List<String> listobj=new ArrayList<String>();
						if (fieldNames != null && fieldNames.size() > 0) {
							for (Iterator it = fieldNames.iterator(); it.hasNext();) {
								// generating the dyanamic query based on selected
								// fields
								Object obdata = (Object) it.next();
								if (obdata != null) {
									if (i < fieldNames.size() - 1){
										if(obdata.toString().equalsIgnoreCase("id")){
											listobj.add("id");
											query.append("sms_id" + ",");}
										else if(obdata.toString().equalsIgnoreCase("writeMessage")){
											listobj.add("writeMessage");
											query.append("msg" + ",");}
										else if(obdata.toString().equalsIgnoreCase("messageDraft")){}
										else if(obdata.toString().equalsIgnoreCase("messageName")){}
	           					        else{
	           					        	listobj.add(obdata.toString());
										query.append(obdata.toString() + ",");
										}
									}
									else{
										query.append(obdata.toString());
										listobj.add(obdata.toString());
									}
								}
								i++;
							}
						}
						query.append(" FROM msg_stats");

						if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
							query.append(" where ");
							// add search query based on the search operation
							if (getSearchOper().equalsIgnoreCase("eq")) {
								query.append(" " + getSearchField() + " = '"
										+ getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("cn")) {
								query.append(" " + getSearchField() + " like '%"
										+ getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("bw")) {
								query.append(" " + getSearchField() + " like '"
										+ getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("ne")) {
								query.append(" " + getSearchField() + " <> '"
										+ getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("ew")) {
								query.append(" " + getSearchField() + " like '%"
										+ getSearchString() + "'");
							}
						}
						query.append(" limit " + from + "," + to);
						List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if (data != null && data.size() > 0) {
							masterViewList = new ArrayList<Object>();
							List<Object> Listhb = new ArrayList<Object>();
							for (Iterator it = data.iterator(); it.hasNext();) {
								Object[] obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (int k = 0; k < obdata.length; k++) {
									if (obdata[k] == null) {
										one.put(listobj.get(k).toString(), "NA");
									} else {
										if (k == 0){
											one.put(listobj.get(k).toString(),
													(Integer) obdata[k]);}
										  else{
											  if(listobj.get(k).toString().equalsIgnoreCase("status")){
												  if(obdata[k].toString().equalsIgnoreCase("0")){
													  one.put(listobj.get(k).toString(),
																"Unsent");  
												  }else{
													  one.put(listobj.get(k).toString(),
														"Sent");
												  }
												
											  }else{
											   one.put(listobj.get(k).toString(),
													obdata[k].toString());}
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
					return SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
					return ERROR;
				}
			} else {
				return LOGIN;
			}
		}
		
		@SuppressWarnings("unchecked")
		public String urlbeforemailsender() {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag) {

				Set<InstantMessageBean> contactSet=null;
				Set<InstantMessageBean> groupset=null;
				try {
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					String accountID = (String) session.get("accountid");
					List<GridDataPropertyView> instant = Configuration
							.getConfigurationData(
									"mapped_instant_mail_configuration", accountID,
									connectionSpace, "", 0, "table_name",
									"instant_mail_configuration");

					if (instant != null && instant.size() > 0) {
						// create table query based on configuration
						List<TableColumes> tableColume = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : instant) {
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						}
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						if (requestParameterNames != null
								&& requestParameterNames.size() > 0) {
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext()) {
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								if (parmName.equalsIgnoreCase("__multiselect_check_list")) {
								} else if (parmName.equalsIgnoreCase("check_list")) {
									contactId = getCheck_list();
								}else if (parmName.equalsIgnoreCase("groupName")) {
									groupId = paramValue;
								} 
								else if (parmName.equalsIgnoreCase("subject")) {
									subject = paramValue;

								} 
								else if (parmName.equalsIgnoreCase("from_email")) {
									from_email = paramValue;
								}
								else if (parmName.equalsIgnoreCase("email_id")) {
									email_id = paramValue;

								} else if (parmName.equalsIgnoreCase("writemail")) {
									writemail = paramValue;

								}
								else if (parmName.equalsIgnoreCase("frequencys")) {
									frequencys = paramValue;
								}
							    else if (parmName.equalsIgnoreCase("date")) {
							    	 date = paramValue;

							    } else if (parmName.equalsIgnoreCase("hours")) {
							    	hours = paramValue;

							    }
							    else if (parmName.equalsIgnoreCase("day")) {
									day = paramValue;

								}
							}
						}
							if (docs != null && docsFileName != null) {
								String filePath = new CreateFolderOs().createUserDir("maildocument");
	                                StringBuffer   str =new StringBuffer();       
					    			for (int i = 0; i < docs.length; i++) {
									File fileToCreate = new File(filePath + "//"+ docsFileName[i]);
									
									FileUtils.copyFile(docs[i], fileToCreate);
									FilesUtil.saveFile(docs[i], docsFileName[i],filePath + File.separator);
									System.out.println("fileName:" + docsFileName[i]);
									
									str.append(docsFileName[i]+",");
					    			}
					    			setDocfilename(str.toString());
						}
						
						if(frequencys!=null && !frequencys.equalsIgnoreCase("")){
						if(frequencys.equals("One-Time"))
						{
			              setFrequencys(frequencys);
			              setDate(date);
			              setHours(hours);
			              setDay(day);
			              
						}
						if(frequencys.equals("Daily"))
						{
				          setFrequencys(frequencys);
				          setDate(date);
			              setHours(hours);
			              setDay(day);

						}
						if(frequencys.equals("Weekly"))
						{
						    setFrequencys(frequencys);
						    setDate(date);
				            setHours(hours);
				            setDay(day);
						}
						if(frequencys.equals("Monthly"))
						{
						    setFrequencys(frequencys);
						    setDate(date);
				            setHours(hours);
				            setDay(day);
						}
						if(frequencys.equals("Yearly"))
						{
						    setFrequencys(frequencys);
						    setDate(date);
				            setHours(hours);
				            setDay(day);
						}
					}
						allDataList= new ArrayList<InstantMessageBean>();
						set = new HashSet<InstantMessageBean>();
						contactSet = new HashSet<InstantMessageBean>();
						groupset = new HashSet<InstantMessageBean>();
						InstantMessageBean bean = null;
						if(contactId!=null && !contactId.equalsIgnoreCase("-1"))
						{
							
							List listcontact = getEmailidByContact(contactId);
							  for (Iterator iterator = listcontact.iterator(); iterator	.hasNext();) {
								bean = new InstantMessageBean();
								Object[] object = (Object[]) iterator.next();
								bean.setId(Integer.parseInt(object[0].toString()));
								bean.setEmail_id(object[1].toString());
								bean.setName(object[2].toString());
								bean.setWritemail(writemail);
								contactSet.add(bean);
								set.add(bean);
							}
						}
						if(groupId!=null && !groupId.equalsIgnoreCase("-1"))
						{
							
							List listemailId = getEmailidBygroup(Integer.parseInt(groupId));
							for (Iterator iterator = listemailId.iterator(); iterator.hasNext();) {
								bean = new InstantMessageBean();
								Object[] object = (Object[]) iterator.next();
								bean.setId(Integer.parseInt(object[0].toString()));
								bean.setEmail_id(object[1].toString());
								bean.setName(object[2].toString());
								bean.setWritemail(writemail);
								groupset.add(bean);
								set.add(bean);
							}
						}
						// get contact from csv format.
						if (email_id != null && !email_id.equals("")) {
							String csvData[] = email_id.split(",");
							dataList = new ArrayList<InstantMessageBean>();
							vdataList = new ArrayList<InstantMessageBean>();
							invdataList = new ArrayList<InstantMessageBean>();
							bdataList = new ArrayList<InstantMessageBean>();
							dudataList = new ArrayList<InstantMessageBean>();
							ArrayList<String> tempDuplicateList = new ArrayList<String>();
							StringBuilder email_ids = new StringBuilder();
							for (int i = 0; i < csvData.length; i++)
							{
								isValid = false;
								if (csvData[i] != null) {
									System.out.println("csvData[] " + csvData[i]);
									csvData[i] = csvData[i].trim();
									pattern = Pattern.compile(EMAIL_PATTERN);
									matcher = pattern.matcher(csvData[i].toString());
									if (matcher.matches()){
										isValid = true;
									} else {
										InstantMessageBean ims = new InstantMessageBean();
										ims.setName("NA");
										ims.setEmail_id(csvData[i]);
										ims.setWritemail(writemail);
										invdataList.add(ims);
									 }
									if (isValid)
									{
										if (isMobileBlacklisted(csvData[i])) {
											InstantMessageBean ims = new InstantMessageBean();
											ims.setName("NA");
											ims.setEmail_id(csvData[i]);
											ims.setWritemail(writemail);
											bdataList.add(ims);
										}
										else 
										{
											if (tempDuplicateList.contains(csvData[i]))
											{
											InstantMessageBean ims = new InstantMessageBean();
											ims.setName("NA");
											ims.setEmail_id(csvData[i]);
											ims.setWritemail(writemail);
											dudataList.add(ims);

										  } else {
											InstantMessageBean ims = new InstantMessageBean();
											ims.setName("NA");
											ims.setEmail_id(csvData[i]);
											ims.setWritemail(writemail);
											email_ids.append(csvData[i]+",");
											vdataList.add(ims);
											set.add(ims);
											tempDuplicateList.add(csvData[i]);
										}
									}
								  }

								}
							}
							if (email_id!=null) 
							{
								email_id = email_ids.toString();
								
							}
						}
						allDataList.addAll(contactSet);
						allDataList.addAll(groupset);
						returnResult = SUCCESS;
					} else {
						addActionMessage("Oops There is some error in data!!!!");
					}
				} catch (Exception e) {
					returnResult = ERROR;
					 e.printStackTrace();
				}
			} else {
				returnResult = LOGIN;
			}
			System.out.println(">>>>>>>>>>"+returnResult);
			return returnResult;

		}
		@SuppressWarnings("unchecked")
		public String insertConfirmationmailaction() {
			System.out.println("********************************************************************heglkjdlkgjlkfsjglksjgkljsdklgklsdglksdjg");
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag) 
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					try 
					{
						SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
						CommonforClassdata obhj = new CommonOperAtion();
						
						boolean status = false;
						
						ob = new InsertDataTable();
						ob.setColName("writemail");
						ob.setDataName(writemail);
						insertData.add(ob);
						
							ob = new InsertDataTable();
							ob.setColName("grp_name");
							ob.setDataName("Na");
							insertData.add(ob);

							
							ob = new InsertDataTable();
							ob.setColName("status");
							ob.setDataName(0);
							insertData.add(ob);
							
							
							ob = new InsertDataTable();
							ob.setColName("sent_date");
							ob.setDataName(DateUtil.getCurrentDateIndianFormat());
							insertData.add(ob);
							
							

							ob = new InsertDataTable();
							ob.setColName("sent_time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("subject");
							ob.setDataName(subject);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("from_email");
							ob.setDataName(from_email);
							insertData.add(ob);
							
							
							if(docfilename!=null && !docfilename.equalsIgnoreCase("")){
								ob = new InsertDataTable();
								ob.setColName("attachment");
								ob.setDataName(docfilename);
								insertData.add(ob);
							}
							
							String csvData[] = null;
							if(email_id!= null && !email_id.equalsIgnoreCase("")){
						     csvData = email_id.split(",");}else{
						    	 List<String> listobj=new ArrayList<String>();
									List listcontact = getEmaildByContact(contactId);
									for (Iterator iterator = listcontact.iterator(); iterator
											.hasNext();) {
		                                   Object[] object = (Object[]) iterator.next();
		                                   listobj.add(object[1].toString());
									}
									csvData= listobj.toArray(new String[listobj.size()]);
							}
							
							if(frequencys!=null && !frequencys.equalsIgnoreCase("")){
								String mailType="Schedule";
								ob = new InsertDataTable();
								ob.setColName("frequencys");
								ob.setDataName(frequencys);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("hours");
								ob.setDataName(getHours());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("date");
								ob.setDataName(getDate());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("day");
								ob.setDataName(getDay());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("mailType");
								ob.setDataName(mailType);
								insertData.add(ob);
								
								for(int i=0;i<csvData.length;i++)
								{
									ob = new InsertDataTable();
									ob.setColName("mail_to");
									ob.setDataName(csvData[i]);
									insertData.add(ob);
									status = obhj.insertIntoTable("instantmail", insertData,connectionSpace);
								}
							}else{
								    String mailType="Instant";
									ob = new InsertDataTable();
									ob.setColName("mailType");
									ob.setDataName(mailType);
									insertData.add(ob);
									
								for(int i=0;i<csvData.length;i++)
								{
									ob = new InsertDataTable();
									ob.setColName("mail_to");
									ob.setDataName(csvData[i]);
									insertData.add(ob);
									status = obhj.insertIntoTable("instantmail", insertData,connectionSpace);
									insertData.remove(insertData.size()-1);
								}
							}
							if (status) 
							{
								addActionMessage("Mail Send Successfully !!!");
								returnResult = SUCCESS;
							}
					
					} catch (Exception e) 
					{
						returnResult = ERROR;
						e.printStackTrace();
					}
				} 
				else 
				{
					returnResult = LOGIN;
					
				}
				System.out.println(">>>>>>>>>>>>>>. Result Type:  "+ returnResult);
				return returnResult;

			}
		

		private List getMobileNoBySubDept(int subDeptID)
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query ="select id,mobOne,empName from employee_basic where subdept='"+subDeptId+"'";
				return cbt.executeAllSelectQuery(query, connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private List getEmaildByContact(String contactId)
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query1="select id,emailIdOne,empName from employee_basic where id in ( "+contactId+" )";
				return cbt.executeAllSelectQuery(query1, connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private List getMobileNoByDept(int deptId)
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query11="select id,mobOne,empName from employee_basic where dept='"+deptId+"'";
				System.out.println("query11::::::" + query11);
				return cbt.executeAllSelectQuery(query11, connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private List getMobileNoByContact(int contactId)
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query1="select id,mobilenumber,first_name from contactbasicdetails where id='"+contactId+"'";
				return cbt.executeAllSelectQuery(query1, connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private List getEmailidByContact(String contactId)
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query1="select id,emailIdOne,empName from employee_basic where id in ( "+contactId+" )";
				return cbt.executeAllSelectQuery(query1, connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		private List getEmailidBygroup(int groupId)
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query1="select contbale.id,contbale.emailIdOne,contbale.empName from employee_basic as contbale " +
						" where  contbale.groupId="+groupId;
				return cbt.executeAllSelectQuery(query1, connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		
		public boolean validate(final String hex) {
			 
			matcher = pattern.matcher(hex);
			return matcher.matches();
		}
	 
		
		

		public String getUpload4() {
			return upload4;
		}

		public List<Object> getMasterViewList() {
			return masterViewList;
		}

		public void setMasterViewList(List<Object> masterViewList) {
			this.masterViewList = masterViewList;
		}

		public void setUpload4(String upload4) {
			this.upload4 = upload4;
		}

		public String getExcelFileName() {
			return excelFileName;
		}

		public void setExcelFileName(String excelFileName) {
			this.excelFileName = excelFileName;
		}

		public Map<String, Object> getColumnNameMap() {
			return columnNameMap;
		}

		public void setColumnNameMap(Map<String, Object> columnNameMap) {
			this.columnNameMap = columnNameMap;
		}


		public String getExcel() {
			return excel;
		}

		public void setExcel(String excel) {
			this.excel = excel;
		}

		public String getCsvNumber() {
			return csvNumber;
		}

		public void setCsvNumber(String csvNumber) {
			this.csvNumber = csvNumber;
		}

		public Map<String, String> getContactListData() {
			return contactListData;
		}

		public void setContactListData(Map<String, String> contactListData) {
			this.contactListData = contactListData;
		}


		public Map<Integer, String> getMessageNameList() {
			return messageNameList;
		}

		public void setMessageNameList(Map<Integer, String> messageNameList) {
			this.messageNameList = messageNameList;
		}

		public Map<Integer, String> getListmessage() {
			return Listmessage;
		}

		public void setListmessage(Map<Integer, String> listmessage) {
			Listmessage = listmessage;
		}

		public List<ConfigurationUtilBean> getSubDept_DeptLevelName() {
			return subDept_DeptLevelName;
		}

		public void setSubDept_DeptLevelName(
				List<ConfigurationUtilBean> subDept_DeptLevelName) {
			this.subDept_DeptLevelName = subDept_DeptLevelName;
		}

		public Map<Integer, String> getSubDeptList() {
			return subDeptList;
		}

		public void setSubDeptList(Map<Integer, String> subDeptList) {
			this.subDeptList = subDeptList;
		}

		public String getDept() {
			return dept;
		}

		public void setDept(String dept) {
			this.dept = dept;
		}

		public String getDeptOrSubDeptId() {
			return deptOrSubDeptId;
		}

		public void setDeptOrSubDeptId(String deptOrSubDeptId) {
			this.deptOrSubDeptId = deptOrSubDeptId;
		}

		public Map<Integer, String> getDeptList() {
			return deptList;
		}

		public void setDeptList(Map<Integer, String> deptList) {
			this.deptList = deptList;
		}

		public Map<Integer, String> getServiceDeptList() {
			return serviceDeptList;
		}

		public void setServiceDeptList(Map<Integer, String> serviceDeptList) {
			this.serviceDeptList = serviceDeptList;
		}

		public String getDeptHierarchy() {
			return deptHierarchy;
		}

		public void setDeptHierarchy(String deptHierarchy) {
			this.deptHierarchy = deptHierarchy;
		}

		public boolean isCheckdept() {
			return checkdept;
		}

		public void setCheckdept(boolean checkdept) {
			this.checkdept = checkdept;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List<ConfigurationUtilBean> getMobileTextBox() {
			return mobileTextBox;
		}

		public void setMobileTextBox(List<ConfigurationUtilBean> mobileTextBox) {
			this.mobileTextBox = mobileTextBox;
		}

		public List<ConfigurationUtilBean> getWritemessageTextBox() {
			return writemessageTextBox;
		}

		public void setWritemessageTextBox(
				List<ConfigurationUtilBean> writemessageTextBox) {
			this.writemessageTextBox = writemessageTextBox;
		}

		public List<GridDataPropertyView> getViewInstantMsgGrid() {
			return viewInstantMsgGrid;
		}

		public void setViewInstantMsgGrid(
				List<GridDataPropertyView> viewInstantMsgGrid) {
			this.viewInstantMsgGrid = viewInstantMsgGrid;
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

		public List<ConfigurationUtilBean> getMessageNameDropdown() {
			return messageNameDropdown;
		}

		public void setMessageNameDropdown(
				List<ConfigurationUtilBean> messageNameDropdown) {
			this.messageNameDropdown = messageNameDropdown;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getMessageName() {
			return messageName;
		}

		public void setMessageName(String messageName) {
			this.messageName = messageName;
		}

		public Map<String, String> getGroupNameListData() {
			return groupNameListData;
		}

		public void setGroupNameListData(Map<String, String> groupNameListData) {
			this.groupNameListData = groupNameListData;
		}

		public Map<String, String> getContactperson() {
			return contactperson;
		}

		public void setContactperson(Map<String, String> contactperson) {
			this.contactperson = contactperson;
		}

		public Map<Integer, String> getMapObject() {
			return mapObject;
		}

		public void setMapObject(Map<Integer, String> mapObject) {
			this.mapObject = mapObject;
		}

		public String getMobileNo() {
			return mobileNo;
		}

		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}

		public String getWriteMessage() {
			return writeMessage;
		}

		public void setWriteMessage(String writeMessage) {
			this.writeMessage = writeMessage;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValidNum() {
			return validNum;
		}

		public void setValidNum(String validNum) {
			this.validNum = validNum;
		}

		public String getDuplicateData() {
			return duplicateData;
		}

		public void setDuplicateData(String duplicateData) {
			this.duplicateData = duplicateData;
		}

		public String getInvalidNum() {
			return invalidNum;
		}

		public void setInvalidNum(String invalidNum) {
			this.invalidNum = invalidNum;
		}

		public String getBlacklist() {
			return blacklist;
		}

		public void setBlacklist(String blacklist) {
			this.blacklist = blacklist;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getSubDeptId() {
			return subDeptId;
		}

		public void setSubDeptId(String subDeptId) {
			this.subDeptId = subDeptId;
		}

		public String getContactId() {
			return contactId;
		}

		public void setContactId(String contactId) {
			this.contactId = contactId;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getMobNo() {
			return mobNo;
		}

		public void setMobNo(String mobNo) {
			this.mobNo = mobNo;
		}

		public String getMsgText() {
			return msgText;
		}

		public void setMsgText(String msgText) {
			this.msgText = msgText;
		}

		@Override
		public void setServletRequest(HttpServletRequest request) {
			this.request = request;
		}
		public String getDeptId() {
			return deptId;
		}

		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}

		public List<ConfigurationUtilBean> getFrequency() {
			return frequency;
		}

		public void setFrequency(List<ConfigurationUtilBean> frequency) {
			this.frequency = frequency;
		}

		public String getFrequencys() {
			return frequencys;
		}

		public void setFrequencys(String frequencys) {
			this.frequencys = frequencys;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getHours() {
			return hours;
		}

		public void setHours(String hours) {
			this.hours = hours;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getWritemail() {
			return writemail;
		}

		public void setWritemail(String writemail) {
			this.writemail = writemail;
		}

		public String getEmail_id() {
			return email_id;
		}

		public void setEmail_id(String email_id) {
			this.email_id = email_id;
		}

		public BlackListBean getBlacklistt() {
			return blacklistt;
		}

		public void setBlacklistt(BlackListBean blacklistt) {
			this.blacklistt = blacklistt;
		}

		public List<InstantMessageBean> getDataList() {
			return dataList;
		}

		public void setDataList(List<InstantMessageBean> dataList) {
			this.dataList = dataList;
		}

		public List<InstantMessageBean> getVdataList() {
			return vdataList;
		}

		public void setVdataList(List<InstantMessageBean> vdataList) {
			this.vdataList = vdataList;
		}

		public List<InstantMessageBean> getInvdataList() {
			return invdataList;
		}

		public void setInvdataList(List<InstantMessageBean> invdataList) {
			this.invdataList = invdataList;
		}

		public List<InstantMessageBean> getBdataList() {
			return bdataList;
		}

		public void setBdataList(List<InstantMessageBean> bdataList) {
			this.bdataList = bdataList;
		}

		public List<InstantMessageBean> getDudataList() {
			return dudataList;
		}

		public void setDudataList(List<InstantMessageBean> dudataList) {
			this.dudataList = dudataList;
		}

		public Map<String, String> getDuplicatenum() {
			return duplicatenum;
		}

		public void setDuplicatenum(Map<String, String> duplicatenum) {
			this.duplicatenum = duplicatenum;
		}

		public Set<InstantMessageBean> getSet() {
			return set;
		}

		public void setSet(Set<InstantMessageBean> set) {
			this.set = set;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public Map<String, String> getGroupcontactNameListData() {
			return groupcontactNameListData;
		}

		public void setGroupcontactNameListData(
				Map<String, String> groupcontactNameListData) {
			this.groupcontactNameListData = groupcontactNameListData;
		}

		public String getCheck_list() {
			return check_list;
		}

		public void setCheck_list(String check_list) {
			this.check_list = check_list;
		}

		public File[] getDocs() {
			return docs;
		}

		public void setDocs(File[] docs) {
			this.docs = docs;
		}

		public String getFrom_email() {
			return from_email;
		}

		public void setFrom_email(String from_email) {
			this.from_email = from_email;
		}

		public String[] getDocsFileName() {
			return docsFileName;
		}

		public void setDocsFileName(String[] docsFileName) {
			this.docsFileName = docsFileName;
		}

		public String getDocfilename() {
			return docfilename;
		}

		public void setDocfilename(String docfilename) {
			this.docfilename = docfilename;
		}

		public Map<String, String> getTemplateNameList() {
			return templateNameList;
		}

		public void setTemplateNameList(Map<String, String> templateNameList) {
			this.templateNameList = templateNameList;
		}
		

	}
