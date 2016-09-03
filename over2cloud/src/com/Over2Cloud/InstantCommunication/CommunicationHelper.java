package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CommunicationHelper extends ActionSupport{
	public boolean addMessage(String name, String dept, String mobone, String msg, String ticketno, String status, String statusflag, String module, SessionFactory connection)
	 {
		boolean flag = false;
			try {
		        CommonOperInterface cot = new CommonConFactory().createInterface();
		        TableColumes ob1 = new TableColumes();
		        List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
		
				ob1.setColumnname("name");
				ob1.setDatatype("varchar(80)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("dept");
				ob1.setDatatype("varchar(80)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("mobno");
				ob1.setDatatype("varchar(15)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
		
				ob1 = new TableColumes();
				ob1.setColumnname("msg_text");
				ob1.setDatatype("varchar(250)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("flag");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("status");
				ob1.setDatatype("varchar(100)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("module");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("user");
				ob1.setDatatype("varchar(100)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("update_date");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("update_time");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				TableColumnName.add(ob1);
				
		        cot.createTable22("instant_msg",TableColumnName,connection);
			
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("mobno");
					 ob.setDataName(mobone);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("msg_text");
					 ob.setDataName(msg);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("user");
					 ob.setDataName("prashant");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
			         cot.insertIntoTable("instant_msg",insertData,connection);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
   
   
   public boolean addMail(String name, String dept,String emailid, String subject, String mailtext, String ticketno,String status, String statusflag, String attachment, String module, SessionFactory connection)
	 {
		boolean flag = false;
		
			try {
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
			        
			          TableColumes tc = new TableColumes();
					  
			          tc.setColumnname("name");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("dept");
					  tc.setDatatype("varchar(80)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("emailid");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("subject");
					  tc.setDatatype("varchar(100)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("mail_text");
					  tc.setDatatype("text");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("flag");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("status");
					  tc.setDatatype("varchar(50)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("attachment");
					  tc.setDatatype("varchar(300)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("module");
					  tc.setDatatype("varchar(300)");
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
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_date");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  tc = new TableColumes();
					  tc.setColumnname("update_time");
					  tc.setDatatype("varchar(10)");
					  tc.setConstraint("default NULL");
					  tablecolumn.add(tc);
					  
					  @SuppressWarnings("unused")
					  boolean aa=  cot.createTable22("instant_mail", tablecolumn, connection);
					  
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("emailid");
					 ob.setDataName(emailid);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("subject");
					 ob.setDataName(subject);
					 insertData.add(ob);
				 
				 
					 ob=new InsertDataTable();
					 ob.setColName("mail_text");
					 ob.setDataName(mailtext);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("attachment");
					 ob.setDataName(attachment);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("userName");
					 ob.setDataName("escalate");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
			 
			         cot.insertIntoTable("instant_mail",insertData,connection);
			         insertData.clear();
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
