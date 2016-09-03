package com.Over2Cloud.ctrl.helpdesk.email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class EmailConfigureAction extends ActionSupport implements ServletRequestAware{

	private HttpServletRequest request;
	Map session = ActionContext.getContext().getSession();
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	@SuppressWarnings("unchecked")
	public String addEmailConfigDetail()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
			  String userName = (String)session.get("uName");
			  String accountID = (String)session.get("accountid");
			  SessionFactory connectionSpace =(SessionFactory) session.get("connectionSpace");
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_email_receiver_config", accountID, connectionSpace, "", 0, "table_name", "email_receiver_config");
		      if (colName!= null && colName.size()>0) {
		    	  //Create Table Query Based On Configuration
		    	  boolean status=false;
				  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
				  for (GridDataPropertyView tableColumes : colName) {
					  if (tableColumes.getColomnName()!=null && !tableColumes.getColomnName().equals("") && !tableColumes.getColomnName().equals("feedType")  && !tableColumes.getColomnName().equals("category")) {
						  TableColumes tc = new TableColumes();
							tc.setColumnname(tableColumes.getColomnName());
							tc.setDatatype("varchar(255)");
							tc.setConstraint("default NULL");
							tableColumn.add(tc);
					}
				   }
				  TableColumes tc = new TableColumes();
				
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
				  
				boolean aa=  cot.createTable22("email_registration", tableColumn, connectionSpace);
			   
				 List<String> requestParameters = Collections.list((Enumeration<String>)request.getParameterNames());
				  if (requestParameters!=null && requestParameters.size()>0)
				  {
						Collections.sort(requestParameters);
						Iterator it = requestParameters.iterator();
						InsertDataTable ob=null;
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						 while (it.hasNext()) {
							 
							 String paramName = it.next().toString();
							 if (paramName!=null && !paramName.equals("") && !paramName.equals("feedType") && !paramName.equals("category") && !paramName.equals("deptName")  && !paramName.equals("subdeptname")) {
								 ob=new InsertDataTable();
								 String paramValue=request.getParameter(paramName);
								 ob.setColName(paramName);
								 ob.setDataName(paramValue);
								 insertData.add(ob);
							}
						}
									 
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
						 status=cot.insertIntoTable("email_registration",insertData,connectionSpace);
					  }
					if (status) {
					 addActionMessage("Email Id Registration Successfully !!");
				}
				 else {
					addActionError("May be Data is Already Exist or Some Error !!!");
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
	
	

}
