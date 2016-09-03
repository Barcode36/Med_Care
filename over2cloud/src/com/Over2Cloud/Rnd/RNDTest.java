package com.Over2Cloud.Rnd;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class RNDTest {

	/**
	 * @param args
	 */
	private static List <InsertDataTable> insertList=new ArrayList<InsertDataTable>();
	public static void main(String[] args) 
	{
		
		try
		{
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
			String name="APS";
			 StringBuilder b=new StringBuilder("INSERT INTO test (insert_time, name)" +
		        		" SELECT * FROM (SELECT '"+DateUtil.getCurrentDateIndianFormat()+"', '"+name+"') AS " +
		        				"tmp WHERE NOT EXISTS (" +
		        		" SELECT name FROM test WHERE name='"+name+"') LIMIT 1;");
		     Session session=connection.openSession();
		     Transaction tx=session.beginTransaction();
		     Query qry=session.createSQLQuery(b.toString());
		     System.out.println(""+qry.executeUpdate());
		     tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
