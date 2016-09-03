package com.Over2Cloud.ctrl.helpdesk.shiftconf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
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
public class ShiftConfAction extends GridPropertyBean {
	private HttpServletRequest request;
	
	private String dataFor;
	private String moduleName;
	private String pageType;
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> serviceDeptList = null;
	

	public String firstAction4AddShift()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			serviceDeptList = new LinkedHashMap<Integer, String>();
			if (dataFor!=null && !dataFor.equals("")) {
				List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2",orgLevel, orgId,dataFor, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0) {
					for (Iterator iterator = departmentlist.iterator(); iterator
							.hasNext();) {
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put((Integer) object1[0], object1[1]
								.toString());
					}
				  }
				}
			
			setShiftTags(pageType);
			if (pageType!=null && !pageType.equals("") && pageType.equals("D")) {
				returnResult = "deptSuccess";
			}
			else {
				returnResult = SUCCESS;
			}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
    public void setShiftTags(String pageType) {
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
		
		if (pageType.equals("SD")) 
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
		
		List<GridDataPropertyView> shiftList = Configuration.getConfigurationData("mapped_shift_configuration", accountID,connectionSpace, "", 0, "table_name","shift_configuration");
		if (shiftList != null && shiftList.size() > 0) {
			for (GridDataPropertyView gdv : shiftList) {
				obj = new ConfigurationUtilBean();
					if (gdv.getColomnName()!=null && !gdv.getColomnName().equals("") && !gdv.getColomnName().equals("moduleName")) {
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
	

	// Method for adding the Shift Detail
	@SuppressWarnings("unchecked")
	public String addShiftConf()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_shift_configuration", "", connectionSpace, "", 0, "table_name", "shift_configuration");
		
		      if (colName !=null && colName.size()>0) {
		    	@SuppressWarnings("unused")
				InsertDataTable idt =null;
				boolean status=false;
				  
			    List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
				for (GridDataPropertyView tableColumes : colName) {
					TableColumes tc = new TableColumes();
					tc.setColumnname(tableColumes.getColomnName());
					tc.setDatatype("varchar(100)");
					tc.setConstraint("default NULL");
					tablecolumn.add(tc);
				 }
				  
                  TableColumes tc = new TableColumes();
				  
				  tc.setColumnname("dept_subdept");
				  tc.setDatatype("varchar(5)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("userName");
				  tc.setDatatype("varchar(25)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("date");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("time");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  cot.createTable22("shift_conf", tablecolumn, connectionSpace);
				  HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
				  String shiftName=null;
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
						
						// Set the value of Dept_SubDept Value on the basis of Dept Hierarchy
						if (paramName.equals("subdeptname")) {
								subdept_dept=paramValue;
							}
							
					    else if (paramName.equals("deptname")) {
								subdept_dept=paramValue;
							}
						
					    else if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& paramName!=null && !paramName.equalsIgnoreCase("deptname")
								&& !paramName.equalsIgnoreCase("subdeptname")&& !paramName.equalsIgnoreCase("viewType"))
						   {
					    	    //adding the parameters list.
							    paramList.add(paramName);
							    if(statusTemp)
							     {
								   String tempParamValueSize[]=request.getParameterValues(paramName);
								   for(String H:tempParamValueSize)
								    {
								      if(!H.equalsIgnoreCase("") && H!=null)
									  paramValueSize++;	
								    }
								   statusTemp=false;
							     }
						   }
						 }
						
					   String parmValuew[][]=new String[paramList.size()][paramValueSize];
					   int m=0;
					   for (Object c : paramList)
					    {
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
							List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							for(int k=0;k<paramList.size();k++)
							{
								 ob=new InsertDataTable();
								 ob.setColName(paramList.get(k).toString());
								 ob.setDataName(parmValuew[k][i]);
								 insertData.add(ob);
								 if(paramList.get(k).toString().equalsIgnoreCase("shiftName"))
								 {
									 shiftName=parmValuew[k][i];
								 }
							}
							
							 ob=new InsertDataTable();
							 ob.setColName("dept_subdept");
							 ob.setDataName(subdept_dept);
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
							 
							 boolean alredyAdded=HUH.isExist("shift_conf", "shiftName",shiftName,"dept_subdept",subdept_dept,"","", connectionSpace);

							 if(!alredyAdded)
							 {
								 status=cot.insertIntoTable("shift_conf",insertData,connectionSpace);
							 }
							 
							 if (status)
							 {
									addActionMessage("Shift Added Successfully !!");
							 }
							 else 
							 {
									addActionError("May be Shift Name is Already Exist or Some Error !!");
							 }
					}
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
	

	public String getDataFor() {
		return dataFor;
	}
	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
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


	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	
}

