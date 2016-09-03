package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.compliance.ConfigureComplianceAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class ConfigureCheckListAction extends GridPropertyBean implements ServletRequestAware
{
 
	private Map<String, String> fromDept;
	private HttpServletRequest request;
	private Map<Integer, String> serviceDeptList;
	private String dataId;
	private List<ComplianceDashboardBean> dataList;
	private List<Object> checkListData;
	private Map<String, String> checkListDetails; 
	private Map<String, String> krListDetails; 
	private String FormatFor;
	private String dataFrom;
	private String SubCatID;
	 

	public String ViewConfigureCheckList()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		 {
			try 
			 {
				if (userName==null || userName.equalsIgnoreCase("")) 
					return LOGIN;
				else 
					returnResult=SUCCESS;
				
				 
			 } catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return returnResult;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String viewConfigureDataList()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				checkListData=new ArrayList<Object>();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List<Object> Listhb=new ArrayList<Object>();	
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
			 	List  data=null;
			 	query.append("SELECT cmptip.id,dept.deptName,subdept.subdeptName,feedtype.fbtype,catg.categoryName,subcatg.subCategoryName ");
				query.append(" FROM feedback_completion_tip AS cmptip ");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = cmptip.subCatId");
				query.append(" INNER JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
				query.append(" INNER JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");
			 	query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = feedtype.dept_subdept");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid group by subcatg.subCategoryName order by cmptip.id asc ");
			 	
				//	query.append(" select id,ticket_type,n_series from ticket_series_conf where moduleName='HDM'");
				data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			//	System.out.println("Test*****"+query.toString());
				 if(data!=null && data.size()>0)
					{
						
					//	System.out.println("in Data loop**"+ data.size()+"   "+getRows()+"  "+getPage());
						setRecords(data.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
					//	System.out.println("in Data loop"+ data.size()+"    "+ to +"   "+from);
						 	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
							for (Iterator iterator = data.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								Map<String, Object> one = new HashMap<String, Object>();
								one.put("id", CUA.getValueWithNullCheck(object[0]));
								
								one.put("department", CUA.getValueWithNullCheck(object[1]));
								one.put("subdepartment", CUA.getValueWithNullCheck(object[2]));
								one.put("feedbacktype", CUA.getValueWithNullCheck(object[3]));
								one.put("category", CUA.getValueWithNullCheck(object[4]));
								one.put("subcategory", CUA.getValueWithNullCheck(object[5]));
								  
								
								Listhb.add(one);
							}
							setCheckListData(Listhb);
						  	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
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
	

	@SuppressWarnings("unchecked")
	public String ConfigureCheckList()
	{
		
	//	System.out.println("In addConfigureCheckList method");
		 
		List dataList = null;
		ActivityBoardHelper ABH = new ActivityBoardHelper();
		fromDept = new LinkedHashMap<String, String>();
		dataList = ABH.getAllDepartment(connectionSpace,null);
		if(dataList!=null && dataList.size()>0)
		{
			fromDept.put("all", "Select Department");
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					fromDept.put(object[0].toString(), object[1].toString());
				}
			}
			dataList.clear();
		}
		 
	//	System.out.println("Inside  for firstAction4Complaint");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		 {
		   try 
			{
			    // Method for getting fields for complaint page
			    setComplaintPageFields();
			  
				List departmentlist = new ArrayList();
				
				
				// Start Code for getting Service Department list
				serviceDeptList = new LinkedHashMap<Integer, String>();
				departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2",orgLevel, orgId,"HDM", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0) {
					for (Iterator iterator = departmentlist.iterator(); iterator
							.hasNext();) {
						Object[] object1 = (Object[]) iterator.next();
					serviceDeptList.put((Integer) object1[0], object1[1]
								.toString());
					}
				}
				// End Code for getting Service Department list
			     
				// End Code for getting Service Department list
				returnResult = SUCCESS;
			} catch (Exception e) {
	           e.printStackTrace();
			}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public String getCompletionTipKR()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(dataId!=null)
				{
					StringBuilder query = new StringBuilder();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					ComplianceDashboardBean CDB = null;
					dataList = new ArrayList<ComplianceDashboardBean>();

					query.append("SELECT fd_kr.id AS ctkrid,kr.id AS krid,kr.kr_name,kr.upload1 FROM kr_upload_data AS kr ");
					query.append(" INNER JOIN feedback_status_kr AS fd_kr ON fd_kr.krId=kr.id ");
					query.append(" WHERE fd_kr.subCatId="+dataId+" ORDER BY kr.kr_name");
				//	System.out.println("Q****"+query.toString());
					
					List tempList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					if(tempList!=null && tempList.size()>0)
					{
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							CDB = new ComplianceDashboardBean();
							Object[] object = (Object[]) iterator.next();
							CDB.setId(CUA.getValueWithNullCheck(object[1]));
							CDB.setTaskId(CUA.getValueWithNullCheck(object[0]));
							CDB.setName(CUA.getValueWithNullCheck(object[2]));
							if(object[3]!=null)
							{
								CDB.setDocPath(CUA.getValueWithNullCheck(object[3].toString()));
								CDB.setOrginalDocPath(object[3].toString());
							}
							
							
							
							dataList.add(CDB);
						}
					}
				 
				}
				return SUCCESS;
			}
			catch (Exception e) 
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
	
	@SuppressWarnings(
			{ "rawtypes" })
			public String viewCheckList()
			{
				if (ValidateSession.checkSession())
				{
					try
					{
						checkListDetails = new LinkedHashMap<String, String>();
						krListDetails = new LinkedHashMap<String, String>();
						ComplianceUniversalAction CUA = new ComplianceUniversalAction();
						ComplianceDashboardBean CDB = null;
						dataList = new ArrayList<ComplianceDashboardBean>();

						 StringBuilder qry = new StringBuilder();
						 StringBuilder query = new StringBuilder();
						 
						 String subCateId=null;
						if (dataFrom!=null && dataFrom.equalsIgnoreCase("CompletionTip"))
						{
							query.append("SELECT cmptip.id,cmptip.subCatId");
							query.append(" FROM feedback_completion_tip AS cmptip ");
							query.append(" where cmptip.id="+SubCatID);
						//	System.out.println(" Com****  "+query.toString());
							
							List tempList1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
							if (tempList1 != null && tempList1.size() > 0)
							{
							 	for (Iterator iterator = tempList1.iterator(); iterator.hasNext();)
								{
									 Object[] object = (Object[]) iterator.next();
									 if(object[1]!=null)
										{
										
									 subCateId=object[1].toString();
											 
										}
							 	}
							}
							
							
							
							
							
							
							qry.append("SELECT id,completion_tip FROM feedback_completion_tip WHERE subCatId=" + subCateId + " ORDER BY completion_tip");
						//	System.out.println(" Com****  "+qry.toString());
							
							List tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
							if (tempList != null && tempList.size() > 0)
							{
								int i = 1;
								for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
								{
									FormatFor="comTip";
									Object[] object = (Object[]) iterator.next();
									checkListDetails.put(String.valueOf(i), object[1].toString());
									i++;
								}
							}
							 
						}
						if (dataFrom!=null && dataFrom.equalsIgnoreCase("KR"))
						{
								
							query.append("SELECT kr.id,kr.subCatId");
							query.append(" FROM feedback_status_kr AS kr ");
							query.append(" where kr.id= "+SubCatID);
							//System.out.println(" KR****  "+query.toString());
							List tempList1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
							if (tempList1 != null && tempList1.size() > 0)
							{
							 	for (Iterator iterator = tempList1.iterator(); iterator.hasNext();)
								{
									 Object[] object = (Object[]) iterator.next();
									 if(object[1]!=null)
										{
										
									 subCateId=object[1].toString();
											 
										}
							 	}
							}
							
							qry.append("SELECT fd_kr.id AS ctkrid,kr.id AS krid,kr.kr_name,kr.upload1 FROM kr_upload_data AS kr ");
							qry.append(" INNER JOIN feedback_status_kr AS fd_kr ON fd_kr.krId=kr.id ");
							qry.append(" WHERE fd_kr.subCatId="+subCateId+" ORDER BY kr.kr_name");
						//	System.out.println(" KR1****  "+qry.toString());
							
							List tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
							if (tempList != null && tempList.size() > 0)
							{
								FormatFor="krList";
								for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
								{
									
									CDB = new ComplianceDashboardBean();
									Object[] object = (Object[]) iterator.next();
									CDB.setId(CUA.getValueWithNullCheck(object[1]));
									CDB.setTaskId(CUA.getValueWithNullCheck(object[0]));
									CDB.setName(CUA.getValueWithNullCheck(object[2]));
									if(object[3]!=null)
									{
										CDB.setDocPath(CUA.getValueWithNullCheck(object[3].toString()));
										CDB.setOrginalDocPath(object[3].toString());
									}
									
									
									
									dataList.add(CDB);
								}
							}
							 
						}
					 	 
						return SUCCESS;
					}
					catch (Exception e)
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
 
 
	@SuppressWarnings("unchecked")
	public String saveCheckList()
	{
		
//		System.out.println("In saveConfigureCheckList");
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				InsertDataTable ob = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				 
				 
						// create table query based on configuration
						 	boolean status = false;
						TableColumes ob1 = null;
						List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					 
						// Create Table For KR

						ob1 = new TableColumes();
						ob1.setColumnname("subCatId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("krId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						cbt.createTable22("feedback_status_KR", Tablecolumesaaa, connectionSpace);
						Tablecolumesaaa.clear();

						// Create Table For Completion Tip

						ob1 = new TableColumes();
						ob1.setColumnname("subCatId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("completion_tip");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						cbt.createTable22("feedback_completion_tip", Tablecolumesaaa, connectionSpace);
						Tablecolumesaaa.clear();

						
						
						
						
						// Create Table For completiontip detail with sub categ resolved ticket 
						 	ob1 = new TableColumes();
						ob1.setColumnname("feedbackStatusId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("completionTipId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						cbt.createTable22("feedback_completionTip_details", Tablecolumesaaa, connectionSpace);
						Tablecolumesaaa.clear();
						
						
						String subcatvalue = request.getParameter("subCategory");
						if(subcatvalue!=null)
						{
						status = true;
						ob = new InsertDataTable();
						ob.setColName("subCatId");
						ob.setDataName(subcatvalue);
						insertData.add(ob);
						
						// insert task id into compliance_task_KR table
					
						String krId[] = request.getParameterValues("kr_name");
						if(krId!=null && krId.length>0)
						{
						for (int i = 0; i < krId.length; i++)
						{
							if(krId[i]!=null && !krId[i].equalsIgnoreCase(""))
							{
						
						 		ob = new InsertDataTable();
								ob.setColName("krId");
								ob.setDataName(krId[i]);
								insertData.add(ob);
								cbt.insertIntoTable("feedback_status_KR", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
								
								status=true;
							}
						}
						}
						 	
						String completionTip[] = request.getParameterValues("completion");
						if(completionTip!=null && completionTip.length>0){
						for (int i = 0; i < completionTip.length; i++)
						{
							if(completionTip[i]!=null && !completionTip[i].equalsIgnoreCase(""))
							{
								ob = new InsertDataTable();
								ob.setColName("completion_tip");
								ob.setDataName(completionTip[i]);
								insertData.add(ob);
								cbt.insertIntoTable("feedback_completion_tip", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
								status = true;
						 	}
						}
						}
						}
						if (status)
						{
							addActionMessage("Feedback Check List added successfully!!!");
							return SUCCESS;
						}
						else
						{
							addActionMessage("Oops There is some error in data!");
							return SUCCESS;
						}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
		 	}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;


						
					
	}
	
	
	
	public void setComplaintPageFields() 
	 {
		ConfigurationUtilBean obj;
		pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
		
		//List for Department Data
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID,connectionSpace, "", 0, "table_name","dept_config_param");
		   if (deptList != null && deptList.size() > 0) {
			  for (GridDataPropertyView gdv : deptList) {
				obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("deptName")) {
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					obj.setMandatory(true);
					pageFieldsColumns.add(obj);
				}
			}
		}
		//List for Sub Department Data
		List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID,connectionSpace, "", 0, "table_name","subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0) {
				for (GridDataPropertyView gdv : subdept_deptList) {
					obj = new ConfigurationUtilBean();
					if (gdv.getColomnName().equalsIgnoreCase("subdeptname")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType("D");
						obj.setMandatory(true);
						pageFieldsColumns.add(obj);
					}

				}
			}
		
	    //List for Complaint to Data
		List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_feedback_lodge_configuration",accountID, connectionSpace, "", 0, "table_name","feedback_lodge_configuration");
		if (feedLodgeTags != null && feedLodgeTags.size() > 0) {
			for (GridDataPropertyView gdv : feedLodgeTags) {
				obj = new ConfigurationUtilBean();
				if (!gdv.getColomnName().equals("ticket_no") && !gdv.getColomnName().equals("deptHierarchy") 
						&& !gdv.getColomnName().equals("by_dept_subdept") && !gdv.getColomnName().equals("to_dept_subdept")
						&& !gdv.getColomnName().equals("allot_to") && !gdv.getColomnName().equals("escalation_date")
						&& !gdv.getColomnName().equals("escalation_time") && !gdv.getColomnName().equals("open_date")
						&& !gdv.getColomnName().equals("open_time") && !gdv.getColomnName().equals("level")
						&& !gdv.getColomnName().equals("status") && !gdv.getColomnName().equals("via_from")
						&& !gdv.getColomnName().equals("feed_registerby") && !gdv.getColomnName().equals("action_by")
						&& !gdv.getColomnName().equals("resolve_date") && !gdv.getColomnName().equals("resolve_time")
						&& !gdv.getColomnName().equals("resolve_duration") && !gdv.getColomnName().equals("resolve_remark")
						&& !gdv.getColomnName().equals("resolve_by") && !gdv.getColomnName().equals("spare_used")
						&& !gdv.getColomnName().equals("hp_date") && !gdv.getColomnName().equals("hp_time")
						&& !gdv.getColomnName().equals("hp_reason") && !gdv.getColomnName().equals("sn_reason")
						&& !gdv.getColomnName().equals("sn_on_date") && !gdv.getColomnName().equals("sn_on_time")
						&& !gdv.getColomnName().equals("sn_upto_date") && !gdv.getColomnName().equals("sn_upto_time")
						&& !gdv.getColomnName().equals("sn_duration") && !gdv.getColomnName().equals("ig_date")
						&& !gdv.getColomnName().equals("ig_time") && !gdv.getColomnName().equals("ig_reason")
						&& !gdv.getColomnName().equals("transfer_date") && !gdv.getColomnName().equals("transfer_time")
						&& !gdv.getColomnName().equals("transfer_reason") && !gdv.getColomnName().equals("moduleName")
						&& !gdv.getColomnName().equals("feedback_remarks") && !gdv.getColomnName().equals("transferId")
						&& !gdv.getColomnName().equals("non_working_time") 
				        ) {
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1")) {
						obj.setMandatory(true);
					} else {
						obj.setMandatory(false);
					}
					pageFieldsColumns.add(obj);
				}
			}
		}
		//System.out.println("Finally The List Size is  "+pageFieldsColumns.size());
	}
	

	public Map<String, String> getFromDept() {
		return fromDept;
	}

	public void setFromDept(Map<String, String> fromDept) {
		this.fromDept = fromDept;
	}

	public Map<Integer, String> getServiceDeptList() {
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList) {
		this.serviceDeptList = serviceDeptList;
	}


	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public List<ComplianceDashboardBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<ComplianceDashboardBean> dataList) {
		this.dataList = dataList;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		 this.request=request;
	}



	public List<Object> getCheckListData() {
		return checkListData;
	}



	public void setCheckListData(List<Object> checkListData) {
		this.checkListData = checkListData;
	}
	public String getDataFrom() {
		return dataFrom;
	}



	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	public Map<String, String> getCheckListDetails() {
		return checkListDetails;
	}



	public void setCheckListDetails(Map<String, String> checkListDetails) {
		this.checkListDetails = checkListDetails;
	}


	public String getSubCatID() {
		return SubCatID;
	}



	public void setSubCatID(String subCatID) {
		SubCatID = subCatID;
	}



	public Map<String, String> getKrListDetails() {
		return krListDetails;
	}



	public void setKrListDetails(Map<String, String> krListDetails) {
		this.krListDetails = krListDetails;
	}



	public String getFormatFor() {
		return FormatFor;
	}



	public void setFormatFor(String formatFor) {
		FormatFor = formatFor;
	}



	


	
	
}