package com.Over2Cloud.CommonClasses;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
public class DistributedTableInfo 
{
public boolean createDataBasesInDistributedSpace(String dbName,SessionFactory connection)
	{
		boolean flag=false;
		Session session = null;
		
		try{
		    session=connection.openSession();
			String countrylistidQuery="create database if not exists `"+dbName+"`;";
			int query =session.createSQLQuery(countrylistidQuery).executeUpdate();
			if(query>0)
			{flag=true;}
			else
			{flag=false;}
			Thread.sleep(3*1000);
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			session.flush();session.close();	
		}
		return flag; 
	}
public boolean createTableInDistributedSpace(String destinationDbName,String destinationTableName,String sourceDbName,String sourceTableName,SessionFactory connection )
	{
		boolean flag=false;
		Session session = null;
		try
		{

		    session=connection.openSession();
			String countrylistidQuery="CREATE TABLE "+destinationDbName+"."+destinationTableName+" LIKE "+sourceDbName+"."+sourceTableName+";";
			//System.out.println("countrylistidQuery   "+countrylistidQuery);
			int query =session.createSQLQuery(countrylistidQuery).executeUpdate();
			if(query>0){flag=true;}
			else{flag=false;}
		}
		catch (HibernateException he) {
			he.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			session.flush();session.close();}
		return flag;
	}
public boolean insertDataInDistributedTable(String dbname,String tableName, List<InsertDataTable> Tablecolumesaaa,SessionFactory Connection )
	{
		boolean flage=true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "+dbname+".`"+tableName+"` (");  
		int i=1;
		for(InsertDataTable h:Tablecolumesaaa)
			{
				if(i<Tablecolumesaaa.size())
					createTableQuery.append(h.getColName()+", ");
				else
					createTableQuery.append(h.getColName()+")");
				i++;
			}
		createTableQuery.append(" VALUES (");
		i=1;
		for(InsertDataTable h:Tablecolumesaaa)
		{
			if(i<Tablecolumesaaa.size())
				createTableQuery.append("'"+h.getDataName()+"', ");
			else
				createTableQuery.append("'"+h.getDataName()+"')");
			i++;
		}
			createTableQuery.append(" ;");
			Session session = null;  
			Transaction transaction = null;  
			try {  
					session=Connection.openSession();
					transaction = session.beginTransaction();  
					session.createSQLQuery(createTableQuery.toString()).executeUpdate();  
					transaction.commit(); 
				}catch (Exception ex) {
					ex.printStackTrace();
					flage=false;transaction.rollback();}  
		return flage;
	}
/*public boolean DropTableInDistributedleavel1(String DbName,String TableName,String Connection)
{
	boolean flag=false;
	Session session = null;
	try
	{
	    AllConnection Conn=new ConnectionFactory(Connection);
	    AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
	    session=Ob1.getSession();
	    session.beginTransaction();
		String countrylistidQuery="DROP TABLE "+DbName+"."+TableName+"";
		int query =session.createSQLQuery(countrylistidQuery).executeUpdate();
		session.getTransaction().commit();
		if(query>0)
		{flag=true;}
		else
		{flag=false;}
		Thread.sleep(3*1000);
	}
	catch(HibernateException Ex)
	{
		Ex.printStackTrace();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		session.flush();session.close();	
	}
	return flag;
}*/






public boolean insertBulkData(String destinationDbName,String destinationTableName,String sourceDbName,String sourceTableName,SessionFactory connection)
{
	boolean flag=false;
	Session session = null;
	try
	{

	    session=connection.openSession();
	    String countrylistidQuery="INSERT "+destinationDbName+"."+destinationTableName+" SELECT * FROM "+sourceDbName+"."+sourceTableName;
		int query =session.createSQLQuery(countrylistidQuery).executeUpdate();
		if(query>0){flag=true;}
		else{flag=false;}
	}
	catch (HibernateException he) {
		he.printStackTrace();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		session.flush();session.close();}
	return flag;
}

public static void deleteDemoClientDBAfterBackup(String dbName,SessionFactory sessfactNew) {
    Session session = null;
    try 
	{
			session = sessfactNew.openSession();
			String listidQuery="drop database "+dbName;
			Query query =session.createSQLQuery(listidQuery);
			int status=query.executeUpdate();
	}catch (Exception e) {
	} 
	 finally {session.flush();session.close();}
}
}
