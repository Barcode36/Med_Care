package com.Over2Cloud.ctrl.feedback.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.ctrl.feedback.pojo.PatientInfo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class PatientDischargeIntegrationHelper {

	public boolean createPatientTableInClientDB(SessionFactory connection)
	{
		boolean created=false;
		try
		{
			List <TableColumes> tableColumns=new ArrayList<TableColumes>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			TableColumes ob1=new TableColumes();
	        ob1=new TableColumes();
			ob1.setColumnname("cr_number");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumns.add(ob1);
	        
			TableColumes ob2=new TableColumes();
			ob2=new TableColumes();
		 	ob2.setColumnname("patient_name");
		 	ob2.setDatatype("varchar(255)");
		 	ob2.setConstraint("default NULL");
		 	tableColumns.add(ob2);
		 	
		 	TableColumes tb8=new TableColumes();
			tb8=new TableColumes();
		 	tb8.setColumnname("mobile_no");
		 	tb8.setDatatype("varchar(255)");
		 	tb8.setConstraint("default NULL");
		 	tableColumns.add(tb8);
		 	
		 	TableColumes tb9=new TableColumes();
			tb9=new TableColumes();
		 	tb9.setColumnname("email");
		 	tb9.setDatatype("varchar(255)");
		 	tb9.setConstraint("default NULL");
		 	tableColumns.add(tb9);
		 	
		 	TableColumes tb4=new TableColumes();
			tb4=new TableColumes();
		 	tb4.setColumnname("visit_type");
		 	tb4.setDatatype("varchar(255)");
		 	tb4.setConstraint("default NULL");
		 	tableColumns.add(tb4);
		 	
		 	TableColumes tb13=new TableColumes();
			tb13=new TableColumes();
		 	tb13.setColumnname("patient_type");
		 	tb13.setDatatype("varchar(255)");
		 	tb13.setConstraint("default NULL");
		 	tableColumns.add(tb13);
		 	
		 	TableColumes ob3=new TableColumes();
			ob3=new TableColumes();
		 	ob3.setColumnname("admit_consultant");
		 	ob3.setDatatype("varchar(255)");
		 	ob3.setConstraint("default NULL");
		 	tableColumns.add(ob3);
		 	
		 	TableColumes tb7=new TableColumes();
			tb7=new TableColumes();
		 	tb7.setColumnname("station");
		 	tb7.setDatatype("varchar(255)");
		 	tb7.setConstraint("default NULL");
		 	tableColumns.add(tb7);
		 	
		 	TableColumes tb5=new TableColumes();
			tb5=new TableColumes();
		 	tb5.setColumnname("admission_time");
		 	tb5.setDatatype("varchar(255)");
		 	tb5.setConstraint("default NULL");
		 	tableColumns.add(tb5);
		 	
		 	TableColumes tb6=new TableColumes();
			tb6=new TableColumes();
		 	tb6.setColumnname("discharge_datetime");
		 	tb6.setDatatype("varchar(255)");
		 	tb6.setConstraint("default NULL");
		 	tableColumns.add(tb6);
		 	
		 	TableColumes tb10=new TableColumes();
			tb10=new TableColumes();
		 	tb10.setColumnname("insert_date");
		 	tb10.setDatatype("varchar(255)");
		 	tb10.setConstraint("default NULL");
		 	tableColumns.add(tb10);
		 	
		 	TableColumes tb14=new TableColumes();
			tb14=new TableColumes();
		 	tb14.setColumnname("insert_time");
		 	tb14.setDatatype("varchar(255)");
		 	tb14.setConstraint("default NULL");
		 	tableColumns.add(tb14);
		 	
		 	TableColumes tb11=new TableColumes();
			tb11=new TableColumes();
		 	tb11.setColumnname("status");
		 	tb11.setDatatype("varchar(255)");
		 	tb11.setConstraint("default NULL");
		 	tableColumns.add(tb11);
		 	
		 	TableColumes tb12=new TableColumes();
			tb12=new TableColumes();
		 	tb12.setColumnname("sms_date");
		 	tb12.setDatatype("varchar(255)");
		 	tb12.setConstraint("default NULL");
		 	tableColumns.add(tb12);
		 	
		 	TableColumes tb15=new TableColumes();
		 	tb15.setColumnname("sms_time");
		 	tb15.setDatatype("varchar(255)");
		 	tb15.setConstraint("default NULL");
		 	tableColumns.add(tb15);
		 	
		 	// patientId
		 	TableColumes tb16=new TableColumes();
		 	tb16.setColumnname("patientId");
		 	tb16.setDatatype("varchar(255)");
		 	tb16.setConstraint("default NULL");
		 	tableColumns.add(tb16);
		 	
		 	TableColumes tb17=new TableColumes();
		 	tb17.setColumnname("smsFlag");
		 	tb17.setDatatype("varchar(10)");
		 	tb17.setConstraint("default 0");
		 	tableColumns.add(tb17);
		 	
		 	created=cbt.createTable22("patientinfo",tableColumns,connection);
		}
	 	catch(Exception e)
	 	{
	 		e.printStackTrace();
	 		created=false;
	 	}
		return created;
	}
	
	public void getDischargePatDetail(Log log, SessionFactory sessionFactMysql, Session sessionHis) {
		
	
		List<String> empID  = new ArrayList<String>();
		Transaction tx = null;
		Transaction hisTx = null;
		String patientId = "";
		try {
		     // sessionHis = HisHibernateSessionFactory.getSession();
		      Query query = sessionHis.createSQLQuery(" Select registration_no,patientName,doctorName,purOfVisit,VisitDate," +
		      		"dischargeDate,roomNo,patientMobileNo,patintEmailId,[Patient Type],patientId,recordId,companytype" +
		      		"  from PatientInfo where smsFlag='0' ");
			  List userData = query.list();
				if(userData != null && userData.size() > 0)
				{
					boolean createPatTable=createPatientTableInClientDB(sessionFactMysql);
					// creating table in client DB
					if(createPatTable)
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						List <InsertDataTable> insertList=new ArrayList<InsertDataTable>();
						for (Iterator iterator = userData.iterator(); iterator .hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							
							insertList.clear();
							
							InsertDataTable ob1= new InsertDataTable();
				            ob1.setColName("cr_number");
				            if(object[0]!=null)
				            {
				            	ob1.setDataName(object[0].toString().trim());
				            	patientId=object[0].toString().trim();
				            }
				            else
				            {
				            	ob1.setDataName("NA");
				            }
				            insertList.add(ob1);
							
							
				            InsertDataTable ob2= new InsertDataTable();
				            ob2.setColName("patient_name");
				            if(object[1]!=null)
				            {
				            	ob2.setDataName(object[1].toString().trim());
				            }
				            else
				            {
				            	ob2.setDataName("NA");
				            }
				            insertList.add(ob2);
				            
				            InsertDataTable ob8= new InsertDataTable();
				            ob8.setColName("mobile_no");
				            if(object[7]!=null)
				            {
				            	ob8.setDataName(object[7].toString().trim());
				            }
				            else
				            {
				            	ob8.setDataName("NA");
				            }
				            insertList.add(ob8);
				            
				            
				            InsertDataTable ob9= new InsertDataTable();
				            ob9.setColName("email");
				            if(object[8]!=null)
				            {
				            	ob9.setDataName(object[8].toString().trim());
				            }
				            else
				            {
				            	ob9.setDataName("NA");
				            }
				            insertList.add(ob9);
				            
				            InsertDataTable ob4= new InsertDataTable();
				            ob4.setColName("visit_type");
				            if(object[3]!=null)
				            {
				            	ob4.setDataName(object[3].toString().trim());
				            }
				            else
				            {
				            	ob4.setDataName("NA");
				            }
				            insertList.add(ob4);
				            
				            InsertDataTable ob13= new InsertDataTable();
				            ob13.setColName("patient_type");
				            if(object[9]!=null)
				            {
				            	ob13.setDataName(object[9].toString().trim());
				            }
				            else
				            {
				            	ob13.setDataName("NA");
				            }
				            insertList.add(ob13);
				            
				            
				          //patientId
				            InsertDataTable ob16= new InsertDataTable();
				            ob16.setColName("patientId");
				            if(object[10]!=null)
				            {
				            	ob16.setDataName(object[10].toString().trim());
				            }
				            else
				            {
				            	ob16.setDataName("NA");
				            }
				            insertList.add(ob16);
				           
				            
				            
				            InsertDataTable ob18= new InsertDataTable();
				            ob18.setColName("recordId");
				            if(object[11]!=null)
				            {
				            	ob18.setDataName(object[11].toString().trim());
				            }
				            else
				            {
				            	ob18.setDataName("NA");
				            }
				            insertList.add(ob18);
				            
				            InsertDataTable ob17= new InsertDataTable();
				            ob17.setColName("companytype");
				            if(object[12]!=null)
				            {
				            	ob17.setDataName(object[12].toString().trim());
				            }
				            else
				            {
				            	ob17.setDataName("NA");
				            }
				            insertList.add(ob17);
				            
				            InsertDataTable ob3= new InsertDataTable();
				            ob3.setColName("admit_consultant");
				            if(object[2]!=null)
				            {
				            	ob3.setDataName(object[2].toString().trim());
				            }
				            else
				            {
				            	ob3.setDataName("NA");
				            }
				            insertList.add(ob3);
				            
				            InsertDataTable ob7= new InsertDataTable();
				            ob7.setColName("station");
				            if(object[6]!=null)
				            {
				            	ob7.setDataName(object[6].toString().trim());
				            }
				            else
				            {
				            	ob7.setDataName("NA");
				            }
				            insertList.add(ob7);
				            
				            
				            InsertDataTable ob5= new InsertDataTable();
				            ob5.setColName("admission_time");
				            if(object[4]!=null)
				            {
				            	ob5.setDataName(object[4].toString().trim());
				            }
				            else
				            {
				            	ob5.setDataName("NA");
				            }
				            insertList.add(ob5);
				            
				            
				            InsertDataTable ob6= new InsertDataTable();
				            ob6.setColName("discharge_datetime");
				            if(object[5]!=null && !object[5].toString().startsWith("1900"))
				            {
				            	ob6.setDataName(object[5].toString().trim());
				            }
				            else
				            {
				            	ob6.setDataName("NA");
				            }
				            insertList.add(ob6);
				            
				            InsertDataTable ob10= new InsertDataTable();
				            ob10.setColName("insert_date");
				            ob10.setDataName(DateUtil.getCurrentDateUSFormat());
				            insertList.add(ob10); 
				           
				            InsertDataTable ob14= new InsertDataTable();
				            ob14.setColName("insert_time");
				            ob14.setDataName(DateUtil.getCurrentTimeHourMin());
				            insertList.add(ob14);
				            
				            
				            InsertDataTable ob11= new InsertDataTable();
				            ob11.setColName("status");
				            ob11.setDataName("0");
				            insertList.add(ob11);
				            
				            
				            InsertDataTable ob12= new InsertDataTable();
				            ob12.setColName("sms_date");
				            ob12.setDataName("0");
				            insertList.add(ob12);
				            
				            InsertDataTable ob15= new InsertDataTable();
				            ob15.setColName("sms_time");
				            ob15.setDataName("0");
				            insertList.add(ob15);
				            
				            boolean inserted= cbt.insertIntoTable("patientinfo",insertList,sessionFactMysql);
				            if(inserted)
				            {
				            	StringBuffer buffer=new StringBuffer(" update patientinfo set smsFlag='3'  " +"where smsFlag='0' and recordId="+object[11].toString()+"");

				            	Transaction trans=sessionHis.beginTransaction();
				            	Query queryUpdate=sessionHis.createSQLQuery(buffer.toString());
								int count=queryUpdate.executeUpdate();
								trans.commit();
								if(count>0)
								{
									System.out.println("Updated Successfully");
								}
								/*Query queryUpdate = sessionHis.createSQLQuery(" Update PatientInfo set smsFlag='3'  " +"where smsFlag='0' and patientId="+patientId);	
								 hisTx = sessionHis.beginTransaction();
								sessionHis.update(queryUpdate);
								hisTx.commit();*/
				            }
						  
						}
					}
						
						log.info("@ClientDataIntegration @ Data Successfully imported "+DateUtil.getCurrentTimeStamp());
				}else {
					log.error("@ClientDataIntegration @"+DateUtil.getCurrentTimeStamp()+" :: No Record Found");
				}	
		}catch(Exception E) {
			E.printStackTrace();
			if(hisTx != null) hisTx.rollback();
			if(tx != null) tx.rollback();
		}
		 Runtime rt = Runtime.getRuntime();
			rt.gc();		
	}
}