package com.Over2Cloud.smsvmn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;

public class SmsVMNTable 
{
	private static Log log=LogFactory.getLog(SmsVMNTable.class);
	HttpServletRequest request;
	HttpServletResponse response;
	private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
	public boolean createSmsVMNTable(SessionFactory sessfact)
	{
		System.out.println("Table created For createSmsVMNTable ...");
		boolean tableCreated=false;
		List <TableColumes> tableColumns=new ArrayList<TableColumes>();
		TableColumes ob=new TableColumes();
        ob.setColumnname("clientID");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
		TableColumes ob1=new TableColumes();
        ob1.setColumnname("ticketID");
        ob1.setDatatype("varchar(255)");
        ob1.setConstraint("default NULL");
        tableColumns.add(ob1);
        
        TableColumes ob2=new TableColumes();
        ob2=new TableColumes();
        ob2.setColumnname("activityID");
        ob2.setDatatype("varchar(255)");
        ob2.setConstraint("default NULL");
        tableColumns.add(ob2);
        
        TableColumes ob3=new TableColumes();
        ob3=new TableColumes();
        ob3.setColumnname("comment");
        ob3.setDatatype("text");
        ob3.setConstraint("default NULL");
        tableColumns.add(ob3);
        
        TableColumes ob8=new TableColumes();
        ob8=new TableColumes();
        ob8.setColumnname("date");
        ob8.setDatatype("varchar(255)");
        ob8.setConstraint("default NULL");
        tableColumns.add(ob8);
        
        TableColumes ob33=new TableColumes();
        ob33=new TableColumes();
        ob33.setColumnname("time");
        ob33.setDatatype("varchar(255)");
        ob33.setConstraint("default NULL");
        tableColumns.add(ob33);
        
       
        
        TableColumes ob5=new TableColumes();
        ob5=new TableColumes();
        ob5.setColumnname("mobileNo");
        ob5.setDatatype("varchar(255)");
        ob5.setConstraint("default NULL");
        tableColumns.add(ob5);
        
       
        
       
        System.out.println("tableColumns SIZE "  +tableColumns.size());
        if(tableColumns!=null && tableColumns.size()>0)
        {
        	tableCreated=cbt.createTable22("sms_keyword_Report",tableColumns,sessfact);
        	 System.out.println("tableCreated "  +tableCreated);
        }
		return tableCreated;
	}
	
	
	
	
	
	
	
	
	
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("Inside GET");
		try
		{
			String reqMobNo=request.getParameter("to");
			String reqKeyword=request.getParameter("text");
			System.out.println("reqMobNo >>>>>>>>>>>>>:: "+reqMobNo);
			System.out.println("reqKeyword >>>>>>>>>>>>>:: "+reqKeyword);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("Inside POST");
		try
		{
			String reqMobNo=request.getParameter("to");
			String reqKeyword=request.getParameter("text");
			System.out.println("reqMobNo >>>>>>>>>>>>>:: "+reqMobNo);
			System.out.println("reqKeyword >>>>>>>>>>>>>:: "+reqKeyword);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	


	
	
	
}