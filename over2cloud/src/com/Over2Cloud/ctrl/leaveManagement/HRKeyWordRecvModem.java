package com.Over2Cloud.ctrl.leaveManagement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;

public class HRKeyWordRecvModem 

{
	
	

	private static Log log=LogFactory.getLog(HRKeyWordRecvModem.class);
	HttpServletRequest request;
	HttpServletResponse response;
	private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	
	public boolean createT2MTable(SessionFactory connection)
	{
		System.out.println("Table created For T2M ...");
		boolean tableCreated=false;
		
		List <TableColumes> tableColumns=new ArrayList<TableColumes>();
	
		TableColumes ob1=new TableColumes();
        ob1.setColumnname("mobNo");
        ob1.setDatatype("varchar(255)");
        ob1.setConstraint("default NULL");
        tableColumns.add(ob1);
        
        TableColumes ob2=new TableColumes();
        ob2=new TableColumes();
        ob2.setColumnname("keyword");
        ob2.setDatatype("varchar(255)");
        ob2.setConstraint("default NULL");
        tableColumns.add(ob2);
        
        TableColumes ob3=new TableColumes();
        ob3=new TableColumes();
        ob3.setColumnname("date");
        ob3.setDatatype("varchar(255)");
        ob3.setConstraint("default NULL");
        tableColumns.add(ob3);
        
        TableColumes ob4=new TableColumes();
        ob4=new TableColumes();
        ob4.setColumnname("time");
        ob4.setDatatype("varchar(255)");
        ob4.setConstraint("default NULL");
        tableColumns.add(ob4);
        
        TableColumes ob5=new TableColumes();
        ob5=new TableColumes();
        ob5.setColumnname("status");
        ob5.setDatatype("tinyint(1)");
        ob5.setConstraint("default NULL");
        tableColumns.add(ob5);
        
        TableColumes ob6=new TableColumes();
        ob6=new TableColumes();
        ob6.setColumnname("updatedDate");
        ob6.setDatatype("varchar(20)");
        ob6.setConstraint("default NULL");
        tableColumns.add(ob6);
        
        TableColumes ob7=new TableColumes();
        ob7=new TableColumes();
        ob7.setColumnname("updatedTime");
        ob7.setDatatype("varchar(20)");
        ob7.setConstraint("default NULL");
        tableColumns.add(ob7);
        
        if(tableColumns!=null && tableColumns.size()>0)
        {
        	tableCreated=cbt.createTable22("t2mkeyword",tableColumns,connection);
        }
		return tableCreated;
	}
	
	public String addRecvKeyword()
	{
		try
		{
			request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
			if(request!=null)
			{
				String reqMobNo=request.getParameter("to");
				String reqKeyword=request.getParameter("text");
				System.out.println("Mob No :::"+reqMobNo+"Kyword ::::::::::"+reqKeyword);
				if(reqMobNo!=null && reqKeyword!=null)
				{
					SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
					boolean tableCreated=createT2MTable(connection);
					if(tableCreated)
					{
	                		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	                		
	                		InsertDataTable ob1=new InsertDataTable();
	                        ob1.setColName("mobNo");
	                        ob1.setDataName(reqMobNo);
	                        insertData.add(ob1);
	                        
	                        InsertDataTable ob2=new InsertDataTable();
	                        ob2.setColName("keyword");
	                        ob2.setDataName(reqKeyword);
	                        insertData.add(ob2);
	                        
	                        InsertDataTable ob3=new InsertDataTable();
	                        ob3.setColName("date");
	                        ob3.setDataName(DateUtil.getCurrentDateUSFormat());
	                        insertData.add(ob3);
	                        
	                        InsertDataTable ob4=new InsertDataTable();
	                        ob4.setColName("time");
	                        ob4.setDataName(DateUtil.getCurrentTimeHourMin());
	                        insertData.add(ob4);
	                        
	                        InsertDataTable ob5=new InsertDataTable();
	                        ob5.setColName("status");
	                        ob5.setDataName("0");
	                        insertData.add(ob5);
	                        
	                        tableCreated=cbt.insertIntoTable("t2mkeyword",insertData,connection); 
	                        
	                        System.out.println("Table Data Inserted Successfully !!! "+tableCreated);
	                }
				}
			}
			return "success";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
	
	
	
	
	public boolean createMSG_StateTable(SessionFactory connection)
	{
		System.out.println("Table created For T2M ...");
		boolean tableCreated=false;
		
		List <TableColumes> tableColumns=new ArrayList<TableColumes>();
	
		
		TableColumes ob=new TableColumes();
        ob.setColumnname("empName");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
		TableColumes ob1=new TableColumes();
        ob1.setColumnname("mobNo");
        ob1.setDatatype("varchar(255)");
        ob1.setConstraint("default NULL");
        tableColumns.add(ob1);
        
        TableColumes ob2=new TableColumes();
        ob2=new TableColumes();
        ob2.setColumnname("IncomingSms");
        ob2.setDatatype("varchar(255)");
        ob2.setConstraint("default NULL");
        tableColumns.add(ob2);
        
        TableColumes ob3=new TableColumes();
        ob3=new TableColumes();
        ob3.setColumnname("OutGoingSms");
        ob3.setDatatype("text");
        ob3.setConstraint("default NULL");
        tableColumns.add(ob3);
        
        TableColumes ob8=new TableColumes();
        ob8=new TableColumes();
        ob8.setColumnname("Reason");
        ob8.setDatatype("varchar(255)");
        ob8.setConstraint("default NULL");
        tableColumns.add(ob8);
        
        TableColumes ob33=new TableColumes();
        ob33=new TableColumes();
        ob33.setColumnname("description");
        ob33.setDatatype("varchar(255)");
        ob33.setConstraint("default NULL");
        tableColumns.add(ob33);
        
        TableColumes ob4=new TableColumes();
        ob4=new TableColumes();
        ob4.setColumnname("OutGoingMail");
        ob4.setDatatype("text");
        ob4.setConstraint("default NULL");
        tableColumns.add(ob4);
        
        TableColumes ob5=new TableColumes();
        ob5=new TableColumes();
        ob5.setColumnname("InComingDate");
        ob5.setDatatype("varchar(255)");
        ob5.setConstraint("default NULL");
        tableColumns.add(ob5);
        
        TableColumes ob6=new TableColumes();
        ob6=new TableColumes();
        ob6.setColumnname("InComingTime");
        ob6.setDatatype("varchar(255)");
        ob6.setConstraint("default NULL");
        tableColumns.add(ob6);
        
       
        System.out.println("tableColumns SIZE "  +tableColumns.size());
        if(tableColumns!=null && tableColumns.size()>0)
        {
        	tableCreated=cbt.createTable22("leave_sms_Report",tableColumns,connection);
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
