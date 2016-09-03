package com.Over2Cloud.ctrl.referral.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class ReferralDoctorIntegrationHelper
{
	public void fetchDoctorDetails(SessionFactory connection)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();

			ob1 = new TableColumes();
			ob1.setColumnname("name");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("spec");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("emp_id");
			ob1.setDatatype("varchar(20)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("add_in_HIS");
			ob1.setDatatype("varchar(50)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("Date");
			ob1.setDatatype("varchar(20)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			


			cbt.createTable22("referral_doctor", TableColumnName, connection);
			
			if(DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:45", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )){
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM dreamsol_dr_vw where ADDED_DATE > TO_TIMESTAMP('"+DateUtil.getDateAfterDays(-60)+" 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')");
				System.out.println("date brfore one day "+DateUtil.getDateAfterDays(-60));
				
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;

				while (rs.next())
				{
					ob = new InsertDataTable();
					ob.setColName("emp_id");
					ob.setDataName(rs.getString("EMPLOYEE_NO"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("name");
					ob.setDataName(rs.getString("PRACTITIONER_NAME"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("spec");
					ob.setDataName(rs.getString("LONG_DESC"));
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("add_in_HIS");
					ob.setDataName(rs.getString("ADDED_DATE"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("Date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					
					System.out.println("doctor name "+rs.getString("EMPLOYEE_NO"));
					System.out.println("PRACTITIONER_NAME name "+rs.getString("PRACTITIONER_NAME"));
					System.out.println("LONG_DESC name "+rs.getString("LONG_DESC"));
					System.out.println("ADDED_DATE  "+rs.getString("ADDED_DATE"));
					
					List list = cbt.executeAllSelectQuery("SELECT emp_id from  referral_doctor where emp_id='"+rs.getString("EMPLOYEE_NO")+"'", connection);
					if (list.size() < 1)
					{
						int id=cbt.insertDataReturnId("referral_doctor", insertData, connection);
						if(id>0)
						{
							System.out.println("Data Inserted.");
						}	
					}
					
					insertData.clear();
				}
				
			}
			else{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM dreamsol_dr_vw where ADDED_DATE > TO_TIMESTAMP('"+DateUtil.getDateAfterDays(-60)+" 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')");
			System.out.println("date brfore one day "+DateUtil.getDateAfterDays(-60));
			
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;

			while (rs.next())
			{
				ob = new InsertDataTable();
				ob.setColName("emp_id");
				ob.setDataName(rs.getString("EMPLOYEE_NO"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("name");
				ob.setDataName(rs.getString("PRACTITIONER_NAME"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("spec");
				ob.setDataName(rs.getString("LONG_DESC"));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("add_in_HIS");
				ob.setDataName(rs.getString("ADDED_DATE"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("Date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
				
				System.out.println("doctor name "+rs.getString("EMPLOYEE_NO"));
				System.out.println("PRACTITIONER_NAME name "+rs.getString("PRACTITIONER_NAME"));
				System.out.println("LONG_DESC name "+rs.getString("LONG_DESC"));
				System.out.println("ADDED_DATE  "+rs.getString("ADDED_DATE"));
				
				List list = cbt.executeAllSelectQuery("SELECT emp_id from  referral_doctor where emp_id='"+rs.getString("EMPLOYEE_NO")+"'", connection);
				if (list.size() < 1)
				{
					int id=cbt.insertDataReturnId("referral_doctor", insertData, connection);
					if(id>0)
					{
						System.out.println("Data Inserted.");
					}	
				}
				
				insertData.clear();
			}
			
			}
			//where ADDED_DATE > TO_TIMESTAMP('2015-09-23 23:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')
			/*ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++ ) {
			  String name = rsmd.getColumnName(i);
			  // Do stuff with name
			  System.out.println(" name "+i+" : "+name );
			}*/
			
		
			// rs = new ClientDataIntegration().fetchDoctorList();

			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}