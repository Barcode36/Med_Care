package com.Over2Cloud.ctrl.helpdesk.roasterconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class RoasterConfAction extends GridPropertyBean implements ServletRequestAware{
	
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	
	
	private String headerName;
	private String editRoasterConf;
	private String selectedId;
	
	private String status;
	private String attendance;
	private String shift_name;
	
	private boolean updateflag=false;
	private boolean editflag=false;
	
	@SuppressWarnings("unchecked")
	public String addEmpInRoaster()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
		      if (userName!=null && !userName.equals("")) {
                  
		    	  TableColumes tc = new TableColumes();
                  tc.setColumnname("contactId");
				  tc.setDatatype("varchar(25)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("empName");
				  tc.setDatatype("varchar(100)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
                  
				  tc = new TableColumes();
				  tc.setColumnname("attendance");
				  tc.setDatatype("varchar(20)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("status");
				  tc.setDatatype("varchar(25)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("floor");
				  tc.setDatatype("varchar(25)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
                  tc = new TableColumes();
				  tc.setColumnname("dept_subdept");
				  tc.setDatatype("varchar(5)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  
				  tc = new TableColumes();
				  tc.setColumnname("01_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("02_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("03_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("04_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("05_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("06_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("07_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("08_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("09_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("10_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("11_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("12_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("13_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("14_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("15_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("16_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("17_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("18_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("19_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("20_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("21_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("22_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("23_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("24_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("25_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("26_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("27_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("28_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("29_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("30_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
				  tc.setColumnname("31_date");
				  tc.setDatatype("varchar(15)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  
				  tc = new TableColumes();
				  tc.setColumnname("userName");
				  tc.setDatatype("varchar(15)");
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
				  
				  @SuppressWarnings("unused")
				  boolean aa=  cot.createTable22("roaster_conf", tablecolumn, connectionSpace);
				  String deptLevel = (String)session.get("userDeptLevel");
				 if (getSelectedId() != null && getSelectedId().contains(",")) {
					 String empId=null,empName=null, dept_subdept=null, level=null;
					 String[] xx = getSelectedId().split(",");
					 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 InsertDataTable ob=new InsertDataTable();
					 for (int indx = 0; indx < xx.length; indx++) {
					 if (!xx[indx].equals(""))
					  {
						 List empList = new HelpdeskUniversalAction().getEmployeeData4Roaster(xx[indx]);
						 if (empList!=null && !empList.isEmpty()) {

								for (Iterator iterator = empList.iterator(); iterator
										.hasNext();) {
									Object[] object = (Object[]) iterator.next();
									
									if (object[0]!=null && !object[0].toString().equals("")) {
										empId= object[0].toString();
									}
									if (object[2]!=null && !object[2].toString().equals("")) {
										dept_subdept= object[2].toString();
									}
									else {
										dept_subdept= "NA";
									}
									
									if (object[3]!=null && !object[3].toString().equals("")) {
										empName= object[3].toString();
									}
									else {
										empName= "NA";
									}
								}
						 }
						 
						 boolean flag = new HelpdeskUniversalHelper().isExist("roaster_conf", "contactId", empId, "dept_subdept", dept_subdept, "level",level, connectionSpace);
						 if (!flag) {
						 ob.setColName("contactId");
						 ob.setDataName(empId);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("empName");
						 ob.setDataName(empName);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("attendance");
						 ob.setDataName("Absent");
						 insertData.add(ob);
				 
						 ob=new InsertDataTable();
						 ob.setColName("status");
						 ob.setDataName("D-Active");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("floor");
						 ob.setDataName("NA");
						 insertData.add(ob);
				 
						 ob=new InsertDataTable();
						 ob.setColName("dept_subdept");
						 ob.setDataName(dept_subdept);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("01_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("02_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("03_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("04_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("05_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("06_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("07_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("08_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("09_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("10_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("11_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("12_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("13_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("14_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("15_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("16_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("17_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("18_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("19_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("20_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("21_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("22_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("23_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("24_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("25_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("26_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("27_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("28_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("29_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("30_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("31_date");
						 ob.setDataName("NA");
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
						 
						 updateflag=cot.insertIntoTable("roaster_conf",insertData,connectionSpace);
						 if (updateflag) {
						 editflag = editEmployeeDetail(xx[indx]);
						}
						 insertData.clear();
					   }
				      }
				    }
				 }
				 else {
				         List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					     if (!getSelectedId().equals("")) {
						 String empId=null,empName=null, dept_subdept=null, level=null;
						 List empList = new HelpdeskUniversalAction().getEmployeeData4Roaster(getSelectedId());
						 if (empList!=null && !empList.isEmpty()) {
							for (Iterator iterator = empList.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								
								if (object[0]!=null && !object[0].toString().equals("")) {
									empId= object[0].toString();
								}
								if (object[2]!=null && !object[2].toString().equals("")) {
									dept_subdept= object[2].toString();
								}
								else {
									dept_subdept= "NA";
								}
								
								if (object[3]!=null && !object[3].toString().equals("")) {
									empName= object[3].toString();
								}
								else {
									empName= "NA";
								}
							}
						 }
						 boolean flag = new HelpdeskUniversalHelper().isExist("roaster_conf", "contactId", empId, "dept_subdept", dept_subdept, "level", level, connectionSpace);
						 if (!flag) {
						 InsertDataTable ob=new InsertDataTable(); 
						 ob.setColName("contactId");
						 ob.setDataName(empId);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("empName");
						 ob.setDataName(empName);
						 insertData.add(ob);
				 
						 ob=new InsertDataTable();
						 ob.setColName("attendance");
						 ob.setDataName("Absent");
						 insertData.add(ob);
				 
						 ob=new InsertDataTable();
						 ob.setColName("status");
						 ob.setDataName("D-Active");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("floor");
						 ob.setDataName("NA");
						 insertData.add(ob);
				 
						 ob=new InsertDataTable();
						 ob.setColName("dept_subdept");
						 ob.setDataName(dept_subdept);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("deptHierarchy");
						 ob.setDataName(deptLevel);
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("01_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("02_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("03_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("04_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("05_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("06_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("07_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("08_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("09_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("10_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("11_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("12_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("13_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("14_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("15_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("16_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("17_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("18_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("19_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("20_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("21_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 
						 ob=new InsertDataTable();
						 ob.setColName("22_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("23_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("24_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("25_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("26_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("27_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("28_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("29_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("30_date");
						 ob.setDataName("NA");
						 insertData.add(ob);
						 
						 ob=new InsertDataTable();
						 ob.setColName("31_date");
						 ob.setDataName("NA");
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
						 updateflag=cot.insertIntoTable("roaster_conf",insertData,connectionSpace);
						 if (updateflag) {
						  editflag = editEmployeeDetail(getSelectedId());
						}
						 insertData.clear();
					   }
					  }
					 }
				     returnResult = SUCCESS;
				     }
		     // System.out.println("Edit Flag Value is   "+editflag);
				     if (editflag) {addActionMessage("Employee Added Successsfully In Roaster. Please Refresh the Page for Getting the Updated Result !!!");}
				     else {addActionError("!!! Some Problem In Adding Employee In Roaster");}
		          }
		     catch (Exception e) {e.printStackTrace();}
		  }
		else {
			 returnResult = LOGIN;
		}
	         return returnResult;	
	}
	
	
	
	public boolean editEmployeeDetail(String empId)
	 {
		boolean flag = false;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
		  try {
			    SessionFactory connectionSpace =(SessionFactory) session.get("connectionSpace");
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				wherClause.put("work_status", "3");
				condtnBlock.put("id",empId);
				new createTable().updateTableColomn("compliance_contacts", wherClause, condtnBlock,connectionSpace);
				flag = true;
		      }
		  catch(Exception e)
		  {e.printStackTrace();}
		}
		return flag;
	 }
	

	

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getEditRoasterConf() {
		return editRoasterConf;
	}

	public void setEditRoasterConf(String editRoasterConf) {
		this.editRoasterConf = editRoasterConf;
	}

	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getShift_name() {
		return shift_name;
	}

	public void setShift_name(String shift_name) {
		this.shift_name = shift_name;
	}
	
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
	this.request=request;	
	}
}
