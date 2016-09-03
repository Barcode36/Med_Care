package com.Over2Cloud.ctrl.helpdesk.feeddraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class FeedbackDraftAction extends GridPropertyBean implements ServletRequestAware{
	
	private HttpServletRequest request;

	private String dataFor;
	private String pageType;
	private String pageFor;
	private String feedLevel1;
	private String feedLevel2;
	private String feedLevel3;
	private int levelOfFeedDraft=1;
	private Map<Integer, String> deptList=null;
	private Map<Integer, String> serviceSubDeptList=null;
	private Map<Integer, String> feedTypeList=null;
	
    private String deptId;
    private String deptName;
	
	@SuppressWarnings("unchecked")
	public String firstAction4AddOrUpdate()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			List departmentlist =null;
			deptList = new LinkedHashMap<Integer, String>();
			
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			/*List colmName = new ArrayList();
			Map<String, Object> order = new HashMap<String, Object>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			
			
			colmName.add("id");
			colmName.add("deptName");
			wherClause.put("orgnztnlevel", orgLevel);
			wherClause.put("mappedOrgnztnId", orgId);
			order.put("deptName", "ASC");*/
			// Getting Id, Dept Name for Non Service Department
			departmentlist = new HelpdeskUniversalAction().getParticularDepartment(dataFor, connectionSpace);
			if (departmentlist != null && departmentlist.size() > 0) {
				for (Iterator iterator = departmentlist.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					deptList.put((Integer) object[0], object[1].toString());
				}
			}
			
			// get Feedback Draft Level and Column Names
				String feedlevels[] = null;
				List<String> colname = new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");
				List feedBackDraft = cbt.viewAllDataEitherSelectOrAll("mapped_feedbackdraft_level", colname,connectionSpace);
				if (feedBackDraft != null && feedBackDraft.size() > 0) {
					for (Object c : feedBackDraft) {
						Object[] dataC = (Object[]) c;
						levelOfFeedDraft = Integer.parseInt((String) dataC[0]);
						feedlevels = dataC[1].toString().split("#");
					}
				}
				if (feedlevels != null) {
					if (levelOfFeedDraft > 0) {
						feedLevel1 = feedlevels[0];
					}
					if (levelOfFeedDraft > 1) {
						feedLevel2 = feedlevels[1];
					}
					if (levelOfFeedDraft > 2) {
						feedLevel3 = feedlevels[2];
					}
				}
				
				setfeedBackDraftTags(pageType,levelOfFeedDraft,pageFor);
			if (pageType!=null && !pageType.equals("") && pageType.equals("D")) {
				returnResult = "deptSUCCESS";
			}
			else {
				returnResult = SUCCESS;
			}
			
			if (pageFor!=null && !pageFor.equals("") && pageFor.equalsIgnoreCase("D")) {
				if (pageType!=null && !pageType.equals("") && pageType.equalsIgnoreCase("D")) {
					returnResult="deptSUCCESS";
				}
				else if (pageType!=null && !pageType.equals("") && pageType.equalsIgnoreCase("SD")) {
					returnResult="success";
				}
			}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String firstAction4Download()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					deptId = object[3].toString();
					deptName = object[4].toString();
				}
			}
			List dataList = null;
				if (pageType!=null && !pageType.equals("") && pageType.equalsIgnoreCase("D")) {
					dataList = new HelpdeskUniversalAction().getServiceDeptOrFeedType(deptId, dataFor, "FT");
					feedTypeList= new LinkedHashMap<Integer, String>();
					if (dataList!=null && dataList.size()>0) {
						for (Iterator iterator = dataList.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							feedTypeList.put((Integer)object[0], object[1].toString());
						}
					}
					returnResult="deptSUCCESS";
				}
				else if (pageType!=null && !pageType.equals("") && pageType.equalsIgnoreCase("SD")) {
					dataList = new HelpdeskUniversalAction().getServiceDeptOrFeedType(deptId, dataFor, "SD");
					serviceSubDeptList = new LinkedHashMap<Integer, String>();
                    if (dataList!=null && dataList.size()>0) {
						for (Iterator iterator = dataList.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							serviceSubDeptList.put((Integer)object[0], object[1].toString());
						}
					 }
					returnResult="success";
				}
				
				setfeedBackDraftTags(pageType,2,pageFor);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	// Method for getting the field Name for feedback Draft Module
	public void setfeedBackDraftTags(String page_Type,int flag,String page_for) {
		ConfigurationUtilBean obj;
		pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
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
		
		if (page_Type.equals("SD")) 
		 {
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID,connectionSpace, "", 0, "table_name","subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0) {
				for (GridDataPropertyView gdv1 : subdept_deptList) {
					obj = new ConfigurationUtilBean();
					if (gdv1.getColomnName().equalsIgnoreCase("subdeptname")) {
						obj.setKey(gdv1.getColomnName());
						obj.setValue(gdv1.getHeadingName());
						obj.setValidationType(gdv1.getValidationType());
						obj.setColType("D");
						obj.setMandatory(true);
						pageFieldsColumns.add(obj);
					}
				}
			}
		 }
		
		if (page_for!=null && !page_for.equals("") && !page_for.equalsIgnoreCase("U") &&( page_for.equalsIgnoreCase("D") || page_for.equalsIgnoreCase("A"))) {
		
		if (flag > 0) {
			List<GridDataPropertyView> feedbackTypeList = Configuration.getConfigurationData("mapped_feedback_type_configuration",accountID, connectionSpace, "", 0, "table_name","feedback_type_configuration");
			if (feedbackTypeList != null && feedbackTypeList.size() > 0) {
				for (GridDataPropertyView gdv : feedbackTypeList) {
					if (!gdv.getColomnName().equals("moduleName")) {
					obj = new ConfigurationUtilBean();
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
		}
		if (flag > 1) {
			List<GridDataPropertyView> feedbackCategoryList = Configuration.getConfigurationData("mapped_feedback_category_configuration",accountID, connectionSpace, "", 0, "table_name","feedback_category_configuration");
			if (feedbackCategoryList != null && feedbackCategoryList.size() > 0) {
				for (GridDataPropertyView gdv : feedbackCategoryList) {
					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("T")
							|| gdv.getColType().trim().equalsIgnoreCase("D")) {
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
		}
		if (flag > 2) {
			List<GridDataPropertyView> feedbackSubCategoryList = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration",accountID, connectionSpace, "", 0, "table_name","feedback_subcategory_configuration");
			if (feedbackSubCategoryList != null
					&& feedbackSubCategoryList.size() > 0) {
				for (GridDataPropertyView gdv : feedbackSubCategoryList) {

					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("D")) {
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
					} else if (gdv.getColType().trim().equalsIgnoreCase("T")) {
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
					} else if (gdv.getColType().trim().equalsIgnoreCase("Time")) {
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
		}
	  }	
	}
	
	
	@SuppressWarnings("unchecked")
	public String addFeedbackType()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_type_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_type_configuration");
		      if (colName!= null && colName.size()>0) {
		    	  //Create Table Query Based On Configuration
		    	  boolean status=false;
				  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
				  for (GridDataPropertyView tableColumes : colName) {
					TableColumes tc = new TableColumes();
					tc.setColumnname(tableColumes.getColomnName());
					tc.setDatatype("varchar(255)");
					tc.setConstraint("default NULL");
					tableColumn.add(tc);
				   }
				  TableColumes tc = new TableColumes();
				
				  tc.setColumnname("dept_subdept");
				  tc.setDatatype("varchar(5)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("hide_show");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("userName");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("date");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("time");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
			     cot.createTable22("feedback_type", tableColumn, connectionSpace);
			   
				 List<String> requestParameters = Collections.list((Enumeration<String>)request.getParameterNames());
				  if (requestParameters!=null && requestParameters.size()>0) {
					Collections.sort(requestParameters);
					Iterator it = requestParameters.iterator();
					String subdept_dept = null;
					InsertDataTable ob=new InsertDataTable();
 					
					 List paramList=new ArrayList<String>();
					 int paramValueSize=0;
					 boolean statusTemp=true;
					 while (it.hasNext()) {
						String paramName = it.next().toString();
						String paramValue=request.getParameter(paramName);
						
						if (paramName.equals("subdeptname")) {
								subdept_dept=paramValue;
							}
					    else if (paramName.equals("deptName")) {
								subdept_dept=paramValue;
							}
					    else if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& paramName!=null 
								&& !paramName.equalsIgnoreCase("deptName")
								&& !paramName.equalsIgnoreCase("subdeptname") && !paramName.equalsIgnoreCase("viewType")  && !paramName.equalsIgnoreCase("moduleName"))
						       {
								//adding the parameters list.
								paramList.add(paramName);
								if(statusTemp)
								  {
									String tempParamValueSize[]=request.getParameterValues(paramName);
									for(String H:tempParamValueSize)
									 {
									   //counting one time size of the parameter value
									   if(H!=null && !H.equalsIgnoreCase(""))
											paramValueSize++;	
									 }
								  }
							   }
					       }
						
						 String parmValuew[][]=new String[paramList.size()][paramValueSize];
						 int m=0;
						 for (Object c : paramList) {
									Object Parmname = (Object) c;
									String paramValue1[]=request.getParameterValues(Parmname.toString());
									for(int j=0;j<paramValueSize;j++)
									{
										if(paramValue1[j]!=null && !paramValue1[j].equalsIgnoreCase(""))
										{
											parmValuew[m][j]=paramValue1[j];
										}
									}
						             m++;
								   }
						
						 for(int i=0;i<paramValueSize;i++)
							{
							 boolean flag=false;
								List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
								for(int k=0;k<paramList.size();k++)
								{
									flag=new HelpdeskUniversalHelper().isExist("feedback_type", "fbType", parmValuew[k][i], "dept_subdept", subdept_dept, "deptHierarchy",deptLevel,"moduleName",request.getParameter("moduleName"), connectionSpace);
									 ob=new InsertDataTable();
									 ob.setColName(paramList.get(k).toString());
									 ob.setDataName(parmValuew[k][i]);
									 insertData.add(ob);
								}
								if (!flag) {
								 ob=new InsertDataTable();
								 ob.setColName("moduleName");
								 ob.setDataName(request.getParameter("moduleName"));
								 insertData.add(ob);
									
								 ob=new InsertDataTable();
								 ob.setColName("dept_subdept");
								 ob.setDataName(subdept_dept);
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("hide_show");
								 ob.setDataName("Active");
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("userName");
								 ob.setDataName(userName);
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("date");
								 ob.setDataName(DateUtil.getCurrentDateUSFormat());
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("time");
								 ob.setDataName(DateUtil.getCurrentTime());
								 insertData.add(ob);
								 status=cot.insertIntoTable("feedback_type",insertData,connectionSpace);
								}
							}
						   }
					if (status) {
					 addActionMessage("Feedback Type Added Successfully !!");
				}
				 else {
					addActionError("May be Feedback Type is Already Exist or Some Error !!!");
				}
		      }
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
	
	
	
	@SuppressWarnings("unchecked")
	public String addFeedbackCategory()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_category_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_category_configuration");
		      if (colName!= null && colName.size()>0) {
		    	  //Create Table Query Based On Configuration
		    	  
		    	  boolean status=false, timeTrue=false;
				  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
				  for (GridDataPropertyView tableColumes : colName) {
					  if (timeTrue) {
						
					}
					TableColumes tc = new TableColumes();
					tc.setColumnname(tableColumes.getColomnName());
					tc.setDatatype("varchar(255)");
					tc.setConstraint("default NULL");
					tableColumn.add(tc);
				   }
				  TableColumes tc = new TableColumes();
				 
				
				  tc.setColumnname("hide_show");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("userName");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("date");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("time");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  cot.createTable22("feedback_category", tableColumn, connectionSpace);
				
				  List<String> requestParameters = Collections.list((Enumeration<String>)request.getParameterNames());
				  if (requestParameters!=null && requestParameters.size()>0) {
					Collections.sort(requestParameters);
					Iterator it = requestParameters.iterator();
					InsertDataTable ob=new InsertDataTable();
					//String tempParamValueSize[]=null;
					
					String feedType=null;
					
					 List paramList=new ArrayList<String>();
					 int paramValueSize=0;
					 boolean statusTemp=true;
					while (it.hasNext()) {
						String paramName = it.next().toString();
						String paramValue=request.getParameter(paramName);
						
						if (paramName!=null && paramName.equals("feedTypeId") && !paramValue.equalsIgnoreCase("-1") ) {
							feedType=paramValue;
						}
						
						else if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& paramName!=null 
								&&  !paramName.equalsIgnoreCase("deptname")
								&& !paramName.equalsIgnoreCase("subdeptname")&& !paramName.equalsIgnoreCase("moduleName") && !paramName.equalsIgnoreCase("viewType")
								&& !paramName.equalsIgnoreCase("level1") && !paramName.equalsIgnoreCase("feedTypeId"))
						   {
							
								//adding the parameters list.
								paramList.add(paramName);
								if(statusTemp)
								  {
									String tempParamValueSize[]=request.getParameterValues(paramName);
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
						 int m=0;
						 for (Object c : paramList) {
									Object Parmname = (Object) c;
									String paramValue1[]=request.getParameterValues(Parmname.toString());
									for(int j=0;j<paramValueSize;j++)
									{
										if(!paramValue1[j].equalsIgnoreCase("") && paramValue1[j]!=null)
										{
											parmValuew[m][j]=paramValue1[j];
										}
									}
						             m++;
								   }
						 
						 for(int i=0;i<paramValueSize;i++)
							{
							    boolean flag=false;
								List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
								for(int k=0;k<paramList.size();k++)
								{
									flag=new HelpdeskUniversalHelper().isExist("feedback_category", "categoryName", parmValuew[k][i], "fbType", feedType, "", "", connectionSpace);
									//System.out.println("Fiel Name  "+paramList.get(k).toString()+"   Field Value   "+parmValuew[k][i]);
									if (!paramList.get(k).toString().equals("hide_show")) {
										ob=new InsertDataTable();
										 ob.setColName(paramList.get(k).toString());
										 ob.setDataName(parmValuew[k][i]);
										 insertData.add(ob);
									}
								}
								if (!flag) {
							     ob=new InsertDataTable();
								 ob.setColName("fbType");
								 ob.setDataName(feedType);
								 insertData.add(ob);
								 
								 
								 ob=new InsertDataTable();
								 ob.setColName("hide_show");
								 ob.setDataName("Active");
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("userName");
								 ob.setDataName(userName);
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("date");
								 ob.setDataName(DateUtil.getCurrentDateUSFormat());
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("time");
								 ob.setDataName(DateUtil.getCurrentTime());
								 insertData.add(ob);
								 status=cot.insertIntoTable("feedback_category",insertData,connectionSpace);
							}
						   }
				  }
					if (status) {
					 addActionMessage("Category Added Successfully !!");
				}
				 else {
					addActionError("May be Category Already Exist  or Some Problem in Adding Feedback Category !!");
				}
		      }
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
	

	@SuppressWarnings("unchecked")
	public String addFeedbackSubCategory()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_subcategory_configuration");
		      if (colName!= null && colName.size()>0) {
		    	  //Create Table Query Based On Configuration
		    	  boolean status=false;
				  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
				  for (GridDataPropertyView tableColumes : colName) {
					  if (!tableColumes.getColomnName().equals("fbType")) {
						  TableColumes tc = new TableColumes();
							tc.setColumnname(tableColumes.getColomnName());
							tc.setDatatype("varchar(255)");
							tc.setConstraint("default NULL");
							tableColumn.add(tc);
					}
					
				   }
				  TableColumes tc = new TableColumes();
				  
				 // Hide By padam
				  tc.setColumnname("hide_show");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("userName");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("date");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("time");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tableColumn.add(tc);
				  
				  cot.createTable22("feedback_subcategory", tableColumn, connectionSpace);
			   
				  List<String> requestParameters = Collections.list((Enumeration<String>)request.getParameterNames());
		          if (requestParameters!=null && requestParameters.size()>0) {
					Collections.sort(requestParameters);
					Iterator it = requestParameters.iterator();
					InsertDataTable ob=new InsertDataTable();
					//String tempParamValueSize[]=null;
					
					String categoryId=null;
					
					 List paramList=new ArrayList<String>();
					 int paramValueSize=0;
					 boolean statusTemp=true;
 					 while (it.hasNext()) {
						String paramName = it.next().toString();
						String paramValue=request.getParameter(paramName);
						
						if (paramName!=null && paramName.equals("categoryId") && !paramValue.equalsIgnoreCase("-1") ) {
							categoryId=paramValue;
						}
						
						else if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& paramName!=null 
								&& !paramName.equalsIgnoreCase("deptname")
								&& !paramName.equalsIgnoreCase("subdeptname")&& !paramName.equalsIgnoreCase("moduleName") && !paramName.equalsIgnoreCase("viewType")
								&& !paramName.equalsIgnoreCase("level1") && !paramName.equalsIgnoreCase("feedTypeId") && !paramName.equalsIgnoreCase("categoryId"))
						   {
								//adding the parameters list.
								paramList.add(paramName);
								if(statusTemp)
								  {
									String tempParamValueSize[]=request.getParameterValues(paramName);
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
						 int m=0;
						 for (Object c : paramList) {
									Object Parmname = (Object) c;
									String paramValue1[]=request.getParameterValues(Parmname.toString());
									for(int j=0;j<paramValueSize;j++)
									{
										if(!paramValue1[j].equalsIgnoreCase("") && paramValue1[j]!=null)
										{
											parmValuew[m][j]=paramValue1[j];
										}
									}
						             m++;
								   }
						 for(int i=0;i<paramValueSize;i++)
							{
							   boolean flag =false;
								List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
								for(int k=0;k<paramList.size();k++)
								{
									if (paramList.get(k)!=null && paramList.get(k).toString().equals("subCategoryName")) {
										flag=new HelpdeskUniversalHelper().isExist("feedback_subcategory", "subCategoryName", parmValuew[k][i], "categoryName", categoryId, "", "", connectionSpace);	
										if (!flag) {
											 ob=new InsertDataTable();
											 ob.setColName(paramList.get(k).toString());
											 ob.setDataName(parmValuew[k][i]);
											 insertData.add(ob);
									}
									}
									else {
										 ob=new InsertDataTable();
										 ob.setColName(paramList.get(k).toString());
										 ob.setDataName(parmValuew[k][i]);
										 insertData.add(ob);
									}
								}
								
								if (!flag) {
							     ob=new InsertDataTable();
								 ob.setColName("categoryName");
								 ob.setDataName(categoryId);
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("hide_show");
								 ob.setDataName("Active");
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("userName");
								 ob.setDataName(userName);
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("date");
								 ob.setDataName(DateUtil.getCurrentDateUSFormat());
								 insertData.add(ob);
								 
								 ob=new InsertDataTable();
								 ob.setColName("time");
								 ob.setDataName(DateUtil.getCurrentTime());
								 insertData.add(ob);
								 status=cot.insertIntoTable("feedback_subcategory",insertData,connectionSpace);
								 flag =false;
								}
							 }
						   }
					if (status) {
					 addActionMessage("Feedback Sub Category Added Successfully !!");
				}
				 else {
					addActionError("May be some Sub Category are duplicate or Some Problem in Adding Feedback Sub Category !!");
				}
		      }
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



	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	public String getDataFor() {
		return dataFor;
	}
	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public String getFeedLevel1() {
		return feedLevel1;
	}
	public void setFeedLevel1(String feedLevel1) {
		this.feedLevel1 = feedLevel1;
	}

	public String getFeedLevel2() {
		return feedLevel2;
	}
	public void setFeedLevel2(String feedLevel2) {
		this.feedLevel2 = feedLevel2;
	}

	public String getFeedLevel3() {
		return feedLevel3;
	}
	public void setFeedLevel3(String feedLevel3) {
		this.feedLevel3 = feedLevel3;
	}
	public int getLevelOfFeedDraft() {
		return levelOfFeedDraft;
	}
	public void setLevelOfFeedDraft(int levelOfFeedDraft) {
		this.levelOfFeedDraft = levelOfFeedDraft;
	}
	public String getPageFor() {
		return pageFor;
	}
	public void setPageFor(String pageFor) {
		this.pageFor = pageFor;
	}

	public Map<Integer, String> getServiceSubDeptList() {
		return serviceSubDeptList;
	}
	public void setServiceSubDeptList(Map<Integer, String> serviceSubDeptList) {
		this.serviceSubDeptList = serviceSubDeptList;
	}

	public Map<Integer, String> getFeedTypeList() {
		return feedTypeList;
	}
	public void setFeedTypeList(Map<Integer, String> feedTypeList) {
		this.feedTypeList = feedTypeList;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
