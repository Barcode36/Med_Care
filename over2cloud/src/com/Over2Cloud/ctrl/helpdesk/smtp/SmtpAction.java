package com.Over2Cloud.ctrl.helpdesk.smtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

public class SmtpAction extends ActionSupport implements ServletRequestAware{

	Map session = ActionContext.getContext().getSession();
	private HttpServletRequest request;
	
	
	
	public String addSmtpDetail()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			
		try {
			  String userName = (String) session.get("uName");
			  SessionFactory connectionSpace =(SessionFactory) session.get("connectionSpace");
		      CommonOperInterface cot = new CommonConFactory().createInterface();
		      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_client_smtp_configuration", "", connectionSpace, "", 0, "table_name", "client_smtp_configuration");
		      if (colName !=null && colName.size()>0) {
			    	@SuppressWarnings("unused")
					InsertDataTable idt =null;
					boolean status=false;
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					  
				    List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName) {
						TableColumes tc = new TableColumes();
						tc.setColumnname(tableColumes.getColomnName());
						tc.setDatatype("varchar(100)");
						tc.setConstraint("default NULL");
						tablecolumn.add(tc);
						
						if(tableColumes.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(tableColumes.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(tableColumes.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
					 }
					  @SuppressWarnings("unused")
					  boolean aa=cot.createTable22("smtp_detail", tablecolumn, connectionSpace);
					  
					  List smtpdata = new HelpdeskUniversalAction().getDataFromTable("smtp_detail", null, null, null, connectionSpace);
					  if (smtpdata.size()==0) {
					  List<String> requestParameters = Collections.list((Enumeration<String>)request.getParameterNames());
					  if (requestParameters!=null && requestParameters.size()>0) {
						  Collections.sort(requestParameters);
							 Iterator it = requestParameters.iterator();
							 while (it.hasNext()) {
									String parmName = it.next().toString();
									String paramValue=request.getParameter(parmName);
									
									if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null && !parmName.equalsIgnoreCase("deptHierarchy"))
									{
										idt=new InsertDataTable();
										idt.setColName(parmName);
										idt.setDataName(paramValue);
										insertData.add(idt);
									}
							 }
							 if(userTrue)
							 {
								 idt=new InsertDataTable();
								 idt.setColName("userName");
								 idt.setDataName(userName);
								 insertData.add(idt);
							 }
							 if(dateTrue)
							 {
								 idt=new InsertDataTable();
								 idt.setColName("date");
								 idt.setDataName(DateUtil.getCurrentDateUSFormat());
								 insertData.add(idt);
							 }
							 if(timeTrue)
							 {
								 idt=new InsertDataTable();
								 idt.setColName("time");
								 idt.setDataName(DateUtil.getCurrentTime());
								 insertData.add(idt);
							 }
							 
							 status=cot.insertIntoTable("smtp_detail",insertData,connectionSpace); 
							 if(status)
							 {
								 addActionMessage("SMTP Detail Added Successfully!!!");
								 returnResult=SUCCESS;
							 }
							 else
							 {
								 addActionMessage("Oops There is some error!!!");
							 } 
					  }
					}
					  else {
						  returnResult=SUCCESS;
						  addActionMessage("Data Already available in the table!!!");
					}
		      }
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
}