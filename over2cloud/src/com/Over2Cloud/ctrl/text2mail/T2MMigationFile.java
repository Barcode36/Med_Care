package com.Over2Cloud.ctrl.text2mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;


import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.leaveManagement.LeaveHelper;

public class T2MMigationFile extends Thread 
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private static SessionFactory connection=null;
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
private String uName;
private String exceutive;
@SuppressWarnings("static-access")
public T2MMigationFile(SessionFactory connection)
  {
	this.connection=connection;
	
  }
/*
	@SuppressWarnings("unused")
	public void run() {

		try {  

			 CommonOperInterface coi = new CommonConFactory().createInterface();

			   StringBuilder query = new StringBuilder();
			   query.append("select mobile_no, msg, date, time from mis_data where msg like 'blk%' and msg not like 'blky%' and date between '2014-01-01' and '2015-01-31' limit 50000");
			  System.out.println("query  "   +query);
			   List dataList = coi.executeAllSelectQuery(query.toString(),connection);
			   if (dataList != null && dataList.size() > 0) {
			 // String[]  values = new String[12];
			    Object[] object = (Object[]) dataList.get(0);
			    values[0] = getValueWithNullCheck(object[0]);
			    values[1] = getValueWithNullCheck(object[1]);
			    values[2] = getValueWithNullCheck(object[2]);
			    values[3] = getValueWithNullCheck(object[3]);
			    
			  
System.out.println("mobile no "+values[0].toString());
			   }
	    	}	
	   
		catch (Exception E) 
		{
			E.printStackTrace();
		}

	}*/

	 @SuppressWarnings("unchecked")
	 public void run() {
		

	  try {
	   CommonOperInterface coi = new CommonConFactory().createInterface();

	   StringBuilder query = new StringBuilder();
	   query.append("select mobile_no, msg, date, time from mis_data where msg like 'intblk%' and msg not like 'blky%' and msg not like 'blkn%' and date between '2014-01-01' and '2015-01-31' ");
	  System.out.println("query  "   +query);
	
	 List data = cbt.executeAllSelectQuery(query.toString(),connection);
		if (data != null && data.size() > 0)
		{
			Object[] obdata1=null;
			for (Iterator it = data.iterator(); it.hasNext();)
			{
				obdata1 = (Object[]) it.next();
				
					 
					  List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						
							//System.out.println("cot ::: " +cot);

						String query0 = ("SELECT cc.id, cc.userName FROM compliance_contacts AS cc INNER JOIN employee_basic "
								+ "AS emp ON emp.id=cc.emp_id WHERE emp.mobOne='"+ obdata1[0].toString() + "' AND cc.moduleName='T2M'");
						List userName = cbt.executeAllSelectQuery(query0,connection);
						if (userName != null && userName.size() > 0) {
							for (Iterator iterator = userName.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								
								setuName(object[0].toString());
							}
						}
						 
						//select number, name, email_id ,dept_id,customerid,emp_id,dateofbirth,dateofanniversary,user,number1,pancardno,town from customer_data 
						String[] empdata1 = new KeyWordRecvModemForT2M().getEmpDetailsByMobileNo(obdata1[0].toString(), connection);
							if(empdata1!=null){
						ob = new InsertDataTable();
						ob.setColName("mobileNo");
						ob.setDataName(obdata1[0]);
						insertData.add(ob);
						
						

							ob = new InsertDataTable();
							ob.setColName("incomingKeyword");
							ob.setDataName("INTBLK");
							insertData.add(ob);

							
							ob = new InsertDataTable();
							ob.setColName("speciality");
							ob.setDataName("NA");
							insertData.add(ob);
							
							
							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(obdata1[2]);
							insertData.add(ob);
							
							
							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(obdata1[3]);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("autoAck");
							ob.setDataName(obdata1[1]);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("excecutive");
							
							String query1 = "select cm.mapped_with, cc.emp_id from contact_mapping_detail as cm inner join compliance_contacts as cc on cc.id=cm.mapped_with	where cm.contact_id='"+getuName()+"'";
							List userid = cbt.executeAllSelectQuery(query1,
									connection);
							if (userid != null && userid.size() > 0) {
								for (Iterator iterator = userid.iterator(); iterator
										.hasNext();) {
									Object[] object = (Object[]) iterator
											.next();
									
									setExceutive(object[1].toString());
								}
							}
							
							
							ob.setDataName(getExceutive());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("location");
							ob.setDataName(empdata1[3]);
							insertData.add(ob);
							
							
							ob = new InsertDataTable();
							ob.setColName("name");
							ob.setDataName(empdata1[0]);
							insertData.add(ob);
							
							
							
							
							
				    	//System.out.println("");
							
							
							System.out.println("insertData ::: " +insertData);
							

						new createTable().insertIntoTable("pull_report_t2m",insertData,connection);
							}
				    	
				
			}
			
		}
	  
	  
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 
	 }

	public String getValueWithNullCheck(Object value) {
		  return (value == null || value.toString().equals("")) ? "NA" : value
		    .toString();
		 }
	public static void main(String[] args)
	{
		Thread.State state ;
		try{
			connection = new ConnectionHelper().getSessionFactory("IN-7");
			
				Thread uclient=new Thread(new T2MMigationFile(connection));
				state = uclient.getState(); 

			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
			
		}catch(Exception E){
			E.printStackTrace();
		}
	}

	public String getuName()
	{
		return uName;
	}

	public void setuName(String uName)
	{
		this.uName = uName;
	}

	public String getExceutive()
	{
		return exceutive;
	}

	public void setExceutive(String exceutive)
	{
		this.exceutive = exceutive;
	}

	/*
	 * id
empName
comName
address
mobOne
anniversary
birthday
emailIdOne
mobTwo
emailIdTwo
mobileNo3
emailId3
mobileNo4
emailId4
mobileNo5
emailId5
speciality
pancard
location
	 */
	
	 
	/*
	 * 
number
name
email_id
dept_id
customerid
emp_id
dateofbirth
dateofanniversary
user
number1
pancardno
town
executive

	 */

}