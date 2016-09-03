package com.Over2Cloud.ctrl.communication.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MailTemplateAction extends ActionSupport {
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private String template_name;
	private String template_body;
	
	


	public String befortemplateadd() {
		String returnString = ERROR;
		try {
			returnString = SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String addtemplateaction() {
		String returnString = ERROR;
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;
		try {
			 CommonOperInterface cbt = new CommonConFactory().createInterface();
			ob = new InsertDataTable();
			ob.setColName("template_body");
			ob.setDataName(template_body);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("template_name");
			ob.setDataName(template_name);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(userName);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			boolean status=cbt.insertIntoTable("mail_template", insertData, connectionSpace);
			if (status) {
				addActionMessage("Data Save Successfully!!!");
				returnString = SUCCESS;
			}
		  else {
			addActionMessage("Oops There is some error in data!!!!");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_body() {
		return template_body;
	}

	public void setTemplate_body(String template_body) {
		this.template_body = template_body;
	}
	
	

}
