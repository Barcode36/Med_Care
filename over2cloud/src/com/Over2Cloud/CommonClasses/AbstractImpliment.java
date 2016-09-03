package com.Over2Cloud.CommonClasses;

import hibernate.common.HibernateSessionFactory;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.annotation.NamedQueries;
import com.Over2Cloud.annotation.NamedQuery;
import com.Over2Cloud.annotation.SqlQueries;
import com.Over2Cloud.annotation.SqlQuery;


/**
 * 
 * @author Pankaj Rajput
 *
 */

@SuppressWarnings("unchecked")
public class   AbstractImpliment <ClassType> implements CommonforClassdata{
	static final Log log = LogFactory.getLog(AbstractImpliment.class);
	Class<ClassType> entityClass;
	//All Hibernate Interface 
	Transaction hTransaction=null;
    Session hSession = null;
    Query hQuery = null;
    Criteria hCriteria=null;
    String hQueryString=null;
	{
		entityClass = (Class<ClassType>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	@SuppressWarnings("unchecked")
	public boolean Prosessrequest(String userName, String password,Class cCls) {
		boolean loginFlag = false;
        List tempList=null;
		try {
			hSession =  HibernateSessionFactory.getSession();
		
			Transaction tx = hSession.beginTransaction();
			hCriteria = hSession.createCriteria(cCls.getName());
			hCriteria.add(Restrictions.eq("loginid", userName)).add(Restrictions.eq("password", password));
			tempList=hCriteria.list();
			tx.commit();
			if (tempList!= null)
				loginFlag = true;
		} catch (Exception e) {
		} finally {
			try {
				///
			} catch (Exception e2) {
			}}
		return loginFlag;
	}
	public boolean Createtabledata(String tableName, List<TableColumes> Tablecolumesaaa,SessionFactory connection){
		boolean flag=true;
		 StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS "+"`" +tableName+"` (");  
		 createTableQuery.append("`id`");  
		 createTableQuery.append("int(10) "); 
		 createTableQuery.append("NOT NULL AUTO_INCREMENT,");
		 List<String>colNames=new ArrayList<String>();
			// append Column 
			for(TableColumes h:Tablecolumesaaa)
				{
				    createTableQuery.append("`"+h.getColumnname()+"` ");  
					createTableQuery.append(h.getDatatype()+" ");  
					createTableQuery.append(h.getConstraint()+", ");  
					colNames.add(h.getColumnname());
				}
			 createTableQuery.append(" PRIMARY KEY  (`id`))");
			 createTableQuery.append("ENGINE=MyISAM AUTO_INCREMENT=10001 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC");
			 
			 try{
				 
		            hSession=connection.openSession();
				  // hSession = HibernateSessionFactory.getSession(); 
				     hTransaction = hSession.beginTransaction();  
				     int count = hSession.createSQLQuery(createTableQuery.toString()).executeUpdate();  
				     hTransaction.commit(); 
				     checkTableColmnAndAltertable(colNames,tableName,connection);
			 }catch (Exception e) {
				 e.printStackTrace();
					flag=false;hTransaction.rollback();}  
				// TODO: handle exception
			 finally{
				try{
					 ///
					 ///
				}catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				} 
			 }
			 
			return flag;
	}
	
	public boolean checkTableColmnAndAltertable(List<String>colName,String tableName,SessionFactory connection)
	{
		boolean status =false;
		StringBuilder checkQuery=new StringBuilder("desc "+tableName);
		List descTable=executeAllSelectQuery(checkQuery.toString(), connection);
		List<String>descTableColname=new ArrayList<String>();
		List<String>returnResult=new ArrayList<String>();
		if(descTable!=null){
			for(Iterator it=descTable.iterator(); it.hasNext();){
				Object[] obdata=(Object[])it.next();
				if(obdata[0]!=null){
					descTableColname.add(obdata[0].toString());
				}
			}
			if(colName!=null && colName.size()>0){
				for(int i=0;i<colName.size();i++)
				{
					if(descTableColname.contains(colName.get(i))){
						}
					else
						returnResult.add(colName.get(i));
				}
			}
			if (returnResult.size()>0) {
			//alter table work here 
			StringBuilder alterTable=new StringBuilder("alter table "+tableName+" add (");
			int k=1;
			for(int i=0;i<returnResult.size();i++){
				if(k<returnResult.size())
					alterTable.append(returnResult.get(i)+" varchar(255) default NULL, ");
				else
					alterTable.append(returnResult.get(i)+" varchar(255) default NULL");
				k++;
			}
			alterTable.append(")");
			executeAlter(alterTable.toString(), connection);
			}
		}
		return status;
	}
	public int executeAlter(String query,SessionFactory connection){
		int size=0;
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				size=session.createSQLQuery(query).executeUpdate(); 
				transaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method executeAlter() of class "+getClass(), ex);
				ex.printStackTrace();
				transaction.rollback();
				} 
		return size;
	}
	
	
	public void updateMsgStatus(String sms_id, String type,String tablename ,SessionFactory connection) {
		Transaction transaction = null;
		Session session = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			if(type.equalsIgnoreCase("One-Time"))
			{
				String hql = "UPDATE "+tablename+" set status=3 where sms_id="+sms_id;
				session.createSQLQuery(hql).executeUpdate();
			}
			if(type.equalsIgnoreCase("Daily"))
			{
				String date = DateUtil.getNextDateAfter(1);
				String hql = "UPDATE "+tablename+" set date='"+date+"' where sms_id="+sms_id ;
				session.createSQLQuery(hql).executeUpdate();
			}
			
			if(type.equalsIgnoreCase("Weekly"))
			{
				String date = DateUtil.getNextDateAfter(7);
				String hql = "UPDATE "+tablename+" set date='"+date+"' where sms_id="+sms_id ;
				session.createSQLQuery(hql).executeUpdate();
			}
			if(type.equalsIgnoreCase("Monthly"))
			{
				String date = DateUtil.getNextDateAftermonth();
				String hql = "UPDATE "+tablename+" set date='"+date+"' where sms_id="+sms_id ;
				session.createSQLQuery(hql).executeUpdate();
			}
			
			if(type.equalsIgnoreCase("Yearly"))
			{
				String date = DateUtil.getNextDateAfter(365);
				
				String hql = "UPDATE "+tablename+" set date='"+date+"' where sms_id="+sms_id ;
				session.createSQLQuery(hql).executeUpdate();
				
			}
			transaction.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}

	public List executeAllSelectQuery(String query,SessionFactory connection)
	{
		List Data=null;
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				Data=session.createSQLQuery(query).list();  
				transaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method executeAllSelectQuery() of class "+getClass(), ex);
				ex.printStackTrace();
				transaction.rollback();
				} 
		return Data;
	}
	
	public List executeAllupdateQuery(String query,SessionFactory connection)
	{
		List Data=null;
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				session.createSQLQuery(query.toString()).executeUpdate();
				transaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method executeAllSelectQuery() of class "+getClass(), ex);
				ex.printStackTrace();
				transaction.rollback();
				} 
		return Data;
	}
	/**
	 * @author Pankaj Rajput
	 * @param tablename
	 * @param Tablecolumesaaa
	 * @return
	 */
	public boolean altertabledata(String tablename,List<TableColumes> Tablecolumesaaa){
		boolean flage=true;
		StringBuilder alterTableQuery = new StringBuilder("Alter table "+tablename+" "+"ADD COLUMN"+" ");  
		// append Column 
		for(TableColumes h:Tablecolumesaaa)
			{
			alterTableQuery.append(" "+h.getColumnname()+" ");  
			alterTableQuery.append(h.getDatatype()+" ");   
			alterTableQuery.append(h.getConstraint());  
			}
		   alterTableQuery.append(";");
		
		try{
			hSession = HibernateSessionFactory.getSession();
		    hTransaction = hSession.beginTransaction();
		    hSession.createSQLQuery(alterTableQuery.toString()).executeUpdate();  
		     hTransaction.commit(); 
		 }catch (Exception e) {
			 e.printStackTrace();
			 flage=false;hTransaction.rollback();}  
			// TODO: handle exception
		 finally{
			try{
				 ///
				 ///
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			} 
		 }
		return flage;
		}
	
	public boolean insertIntoTable(String tableName, List<InsertDataTable> Tablecolumesaaa,SessionFactory connection)
	{
		boolean flage=true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "+ "`" +tableName+"` (");  
		int i=1;
		// append Column 
		for(InsertDataTable h:Tablecolumesaaa)
			{
				if(i<Tablecolumesaaa.size())
					createTableQuery.append(h.getColName()+", ");
				else
					createTableQuery.append(h.getColName()+")");
				i++;
			}
		// append Column Values
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
			
			try {  
		            hSession=connection.openSession();
				    // hSession = HibernateSessionFactory.getSession(); 
				     hTransaction = hSession.beginTransaction();  
				     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>88888888888"+createTableQuery.toString());
				     hSession.createSQLQuery(createTableQuery.toString()).executeUpdate();  
				     hTransaction.commit(); 
				}catch (Exception ex) {
					ex.printStackTrace();
					flage=false;hTransaction.rollback();}  
				   finally{
					   try{
						   ///
							///
					   }catch (Exception e) {
						   e.printStackTrace();
						// TODO: handle exception
					}
				   }
				
		return flage;
	}
	public boolean batchinsertIntoTable(String tableName, String createTableQuery,Session hSession)
	{
		boolean flage=true;
			try {  
				   // hSession = HibernateSessionFactory.getSession(); 
				     hTransaction = hSession.beginTransaction();  
				     hSession.createSQLQuery(createTableQuery.toString()).executeUpdate();  
				     hTransaction.commit(); 
				}catch (Exception ex) {
					ex.printStackTrace();
					flage=false;hTransaction.rollback();}  
				   finally{
					   try{
						   ///
							///
					   }catch (Exception e) {
						   e.printStackTrace();
						// TODO: handle exception
					}
				   }
				
		return flage;
	}
	public boolean batchinsertIntoTable(String tableName, String createTableQuery,SessionFactory connectSession)
	{
		boolean flage=true;
		Session hSession=null;
			try {  
				     hSession = connectSession.getCurrentSession(); 
				     hTransaction = hSession.beginTransaction();  
				     hSession.createSQLQuery(createTableQuery.toString()).executeUpdate();  
				     hTransaction.commit(); 
				}catch (Exception ex) {
					ex.printStackTrace();
					flage=false;hTransaction.rollback();}  
				   finally{
					   try{
						   ///
							///
					   }catch (Exception e) {
						   e.printStackTrace();
						// TODO: handle exception
					}
				   }
				
		return flage;
	}
	public void updateMail(String sms_id, String type,String tablename ,SessionFactory connection) {
		Transaction transaction = null;
		Session session = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			if(type.equalsIgnoreCase("One-Time"))
			{
				String hql = "UPDATE "+tablename+" set status=3 where id="+sms_id;
				System.out.println("????????"+hql);
				session.createSQLQuery(hql).executeUpdate();
			}
			if(type.equalsIgnoreCase("Daily"))
			{
				String date = DateUtil.getNextDateAfter(1);
				String hql = "UPDATE "+tablename+" set date='"+date+"' where id="+sms_id ;
				System.out.println("????????"+hql);
				session.createSQLQuery(hql).executeUpdate();
			}
			
			if(type.equalsIgnoreCase("Weekly"))
			{
				String date = DateUtil.getNextDateAfter(7);
				String hql = "UPDATE "+tablename+" set date='"+date+"' where id="+sms_id ;
				System.out.println("????????"+hql);
				session.createSQLQuery(hql).executeUpdate();
			}
			if(type.equalsIgnoreCase("Monthly"))
			{
				String date = DateUtil.getNextDateAftermonth();
				String hql = "UPDATE "+tablename+" set date='"+date+"' where id="+sms_id ;
				System.out.println("????????"+hql);
				session.createSQLQuery(hql).executeUpdate();
			}
			
			if(type.equalsIgnoreCase("Yearly"))
			{
				String date = DateUtil.getNextDateAfter(365);
				
				String hql = "UPDATE "+tablename+" set date='"+date+"' where id="+sms_id ;
				session.createSQLQuery(hql).executeUpdate();
				
			}
			transaction.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	public boolean deleteAllRecordForId(String tableName,String colName,String condValue,SessionFactory connection)
	{
		boolean status=false;
		
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				StringBuilder query=new StringBuilder();
				query.append("delete from "+tableName+" where "+colName+" in("+condValue+")");
				int size=session.createSQLQuery(query.toString()).executeUpdate();
				if(size!=0)
					status=true;
				transaction.commit(); 
				session.close();
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteAllRecordForId() of class "+getClass(), ex);
				ex.printStackTrace();
				transaction.rollback();
				session.close();
				} 
		
		return status;
	}
	public boolean deleteAllRecordForId(String tableName,Map<String, Object>whereClose,SessionFactory connection)
	{
		boolean status=false;
		
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				StringBuilder query=new StringBuilder();
				query.append("delete from "+tableName+" ");
			    int size=1;
				if(whereClose.size()>0){
					query.append("WHERE"+" ");
					Iterator<Entry<String, Object>> hiterator= whereClose.entrySet().iterator();
					while (hiterator.hasNext()) {
						Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hiterator.next();
						if(size<whereClose.size())
							query.append(paramPair.getKey()+" = "+paramPair.getValue()+" and ");	
						else
							query.append(paramPair.getKey()+" = '"+paramPair.getValue()+"'");
						size++;
				}
			}
				query.append(";");
				System.out.println("query>>>>>>>>>>>"+query);
				int sizez=session.createSQLQuery(query.toString()).executeUpdate();
				if(sizez!=0)
					status=true;
				transaction.commit(); 
				session.close();
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteAllRecordForId() of class "+getClass(), ex);
				ex.printStackTrace();
				transaction.rollback();
				session.close();
				} 
		
		return status;
	}

	public boolean insertIntoTableifNotExit(String tableName, List<InsertDataTable> Tablecolumesaaa, SessionFactory connection){
		boolean flage=true;
		StringBuilder createTableQuery = new StringBuilder("INSERT IGNORE INTO "+ "`" +tableName+"` (");  
		int i=1;
		// append Column 
		for(InsertDataTable h:Tablecolumesaaa)
			{
				if(i<Tablecolumesaaa.size())
					createTableQuery.append(h.getColName()+", ");
				else
					createTableQuery.append(h.getColName()+")");
				i++;
			}
		// append Column Values
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
			
			try {  
		            hSession=connection.openSession();
				    // hSession = HibernateSessionFactory.getSession(); 
				     hTransaction = hSession.beginTransaction();  
				     hSession.createSQLQuery(createTableQuery.toString()).executeUpdate();  
				     hTransaction.commit(); 
				}catch (Exception ex) {
					ex.printStackTrace();
					flage=false;hTransaction.rollback();}  
				   finally{
					   try{
						   ///
							///
					   }catch (Exception e) {
						   e.printStackTrace();
						// TODO: handle exception
					}
				   }
				
		return flage;
	}
	
	
	/**
	 * @Update Method
	 * @param tableName
	 * @param idd
	 * @param paramMap
	 * @return
	 */
	public List insertIntoTableReturnId(String tableName, List<InsertDataTable> Tablecolumesaaa,SessionFactory connection)
	{
		boolean flage=true;
	
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "+ "`" +tableName+"` (");  
		StringBuilder selectTableQuery = new StringBuilder("SELECT LAST_INSERT_ID()");  
		int i=1;
		List Listobject = null;
		// append Column 
		for(InsertDataTable h:Tablecolumesaaa)
			{
				if(i<Tablecolumesaaa.size())
					createTableQuery.append(h.getColName()+", ");
				else
					createTableQuery.append(h.getColName()+")");
				i++;
			}
		// append Column Values
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
			try {  
				     hSession = connection.openSession(); 
				     hTransaction = hSession.beginTransaction();  
				     hSession.createSQLQuery(createTableQuery.toString()).executeUpdate();  
				     Query hQuery=hSession.createSQLQuery(selectTableQuery.toString());
				     Listobject= hQuery.list();
				  
				     hTransaction.commit(); 
				}catch (Exception ex) {
					ex.printStackTrace();
					flage=false;hTransaction.rollback();}  
				   finally{
					   try{
						   ///
							///
					   }catch (Exception e) {
						   e.printStackTrace();
						// TODO: handle exception
					}
				   }
				
		return Listobject;
	}
	

	
	/**
	 * @author Pankaj
	 * @param tablename
	 * @param paramList
	 * @param paramMasp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSelectTabledata(String tablename, Map whereClose,SessionFactory connection){
		StringBuilder selectTableQuery = new StringBuilder("SELECT * FROM "+""+tablename+" ");  
		List mapDataObj = null;
		Transaction hTransaction=null;
	    Session hSession = null;
	    Query hQuery=null;
	    int size=1;
		if(whereClose.size()>0){
			selectTableQuery.append("WHERE"+" ");
			Iterator<Object> hiterator=whereClose.entrySet().iterator();
			while (hiterator.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hiterator.next();
				if(size<whereClose.size())
				selectTableQuery.append(paramPair.getKey()+" = "+paramPair.getValue()+" and ");	
				else
					selectTableQuery.append(paramPair.getKey()+" = '"+paramPair.getValue()+"'");
				size++;
		}
	}
		selectTableQuery.append(";");
		try{
			
	            hSession=connection.openSession();
		     hTransaction = hSession.beginTransaction();   
		     hQuery=hSession.createSQLQuery(selectTableQuery.toString());
		     mapDataObj= hQuery.list();
		     hTransaction.commit(); 	
	}catch (Exception ex) {
		ex.printStackTrace();
		hTransaction.rollback();
		}  
	   finally{
		   try{
			   ///
				///
		   }catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
	   }
		return mapDataObj;
	}
	@SuppressWarnings("unchecked")
	public List getSelectdataFromSelectedFields(String tablename,List<String> selFields, Map whereClose,SessionFactory connection){
		StringBuilder selectTableQuery = new StringBuilder("SELECT");  
		List mapDataObj = null;
		Transaction hTransaction=null;
	    Session hSession = null;
	    Query hQuery=null;
	    int size=1;
	    String [] fieldvalue=null;
	    if(selFields.size()>0){
		    int sizes=1;
	    	fieldvalue =  (String[]) selFields.toArray(new String[selFields.size()]);
	     for (int i = 0; i < fieldvalue.length; i++) {
	    	 if(sizes<fieldvalue.length){
	    	 selectTableQuery.append(" "+fieldvalue[i]+" "+",");
	    	 }else{
	    		 selectTableQuery.append(" "+fieldvalue[i]+" ");
	    	 }
	    	 sizes++;
		}
	}
	    selectTableQuery.append(" "+"FROM "+""+tablename+" ");  
		if(whereClose.size()>0){
			selectTableQuery.append("WHERE"+" ");
			Iterator<Object> hiterator=whereClose.entrySet().iterator();
			while (hiterator.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hiterator.next();
				if(size<whereClose.size())
				selectTableQuery.append(paramPair.getKey()+" = "+paramPair.getValue()+" and ");	
				else
					selectTableQuery.append(paramPair.getKey()+" = '"+paramPair.getValue()+"'");
				size++;
		}
	}
		selectTableQuery.append(";");
		try{
			
	            hSession=connection.openSession();
		     hTransaction = hSession.beginTransaction();   
		     hQuery=hSession.createSQLQuery(selectTableQuery.toString());
		     mapDataObj= hQuery.list();
		     hTransaction.commit(); 	
	}catch (Exception ex) {
		ex.printStackTrace();
		hTransaction.rollback();
		}  
	   finally{
		   try{
			   ///
				///
		   }catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
	   }
		return mapDataObj;
	}
	/**
	 * @Update Method
	 * @param tableName
	 * @param idd
	 * @param paramMap
	 * @return
	 */
	public boolean updateIntoTable(String tableName,Map SetMap,Map WhereClouse,SessionFactory connectionSpace){
		boolean flage=true;
		StringBuilder updateTableQuery = new StringBuilder("UPDATE "+tableName+" "+"SET"+" ");  
	    Iterator<Object> hIterator = SetMap.entrySet().iterator();
	    int size=1;
		while(hIterator.hasNext()){ 
			Map.Entry me = (Map.Entry)hIterator.next();
			if(size<SetMap.size())
				updateTableQuery.append(me.getKey()+" = '"+me.getValue()+"' ,");
			else
				updateTableQuery.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		if(WhereClouse.size()>0){
			updateTableQuery.append(" WHERE"+" ");
	     size=1;
		 Iterator<Object> hIteratorr = WhereClouse.entrySet().iterator();
			while(hIteratorr.hasNext()){ 
				Map.Entry me = (Map.Entry)hIteratorr.next();
				if(size<WhereClouse.size())
					updateTableQuery.append(me.getKey()+" = '"+me.getValue()+"' and ");
				else
					updateTableQuery.append(me.getKey()+" = '"+me.getValue()+"'");
				size++;
			} 
		}
		try {  
			System.out.println(">>>>>>>Update query"+updateTableQuery.toString());
		     //hSession = HibernateSessionFactory.getSession(); connectionSpace dbName;
            hSession= connectionSpace.openSession();
            hTransaction=hSession.beginTransaction();
            hSession.createSQLQuery(updateTableQuery.toString()).executeUpdate();
            hTransaction.commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			flage=false;
			hTransaction.rollback();
			}  
		   finally{
			   try{
				   ///
					///
			   }catch (Exception e) {
				   e.printStackTrace();
				// TODO: handle exception
			}
		   }
		
		return flage;
	}
	
	public boolean selectIfdataExist(String tableName,Map WhereClouse,SessionFactory connectionSpace){
		boolean flage=true;
		StringBuilder updateTableQuery = new StringBuilder("select * from "+tableName);  
	    int size=1;
		if(WhereClouse.size()>0){
			updateTableQuery.append(" WHERE"+" ");
	     size=1;
		 Iterator<Object> hIteratorr = WhereClouse.entrySet().iterator();
			while(hIteratorr.hasNext()){ 
				Map.Entry me = (Map.Entry)hIteratorr.next();
				if(size<WhereClouse.size())
					updateTableQuery.append(me.getKey()+" = '"+me.getValue()+"' and ");
				else
					updateTableQuery.append(me.getKey()+" = '"+me.getValue()+"'");
				size++;
			} 
		}
		try {  
		     //hSession = HibernateSessionFactory.getSession(); connectionSpace dbName;
            hSession= connectionSpace.openSession();
            hTransaction=hSession.beginTransaction();
            hSession.createSQLQuery(updateTableQuery.toString());
            hTransaction.commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			flage=false;
			hTransaction.rollback();
			}  
		   finally{
			   try{
				   ///
					///
			   }catch (Exception e) {
				   e.printStackTrace();
				// TODO: handle exception
			}
		   }
		
		return flage;
	}
	/**
	 * @Update Method
	 * @param tableName
	 * @param idd
	 * @param paramMap
	 * @return
	 */
	public boolean updateIntomultipleTable(List multipletable,Map SetMap,Map WhereClouse,Map subWhereClouse,SessionFactory connectionSpace){
		boolean flage=true;
		List<String>descTableColname=new ArrayList<String>();
		Map<String, Object> setWhereClouse=new HashMap<String, Object>();
	    int size=1;
		for(Iterator itr=multipletable.iterator(); itr.hasNext();){
		    Object cobdata=(Object)itr.next();
		    if(cobdata!=null) {
		    	
				StringBuilder updateTableQuery = new StringBuilder("UPDATE "+cobdata.toString()+" "+"SET"+" ");  
				StringBuilder checkQuery=new StringBuilder("desc "+cobdata.toString());
				System.out.println("Table"+cobdata.toString());
				
				
				List descTable=executeAllSelectQuery(checkQuery.toString(), connectionSpace);
				if(descTable!=null){
					for(Iterator it=descTable.iterator(); it.hasNext();){
						Object[] obdata=(Object[])it.next();
						if(obdata[0]!=null){
							descTableColname.add(obdata[0].toString());
							
						}
					}
				}
				 Iterator<Object> whIterator = SetMap.entrySet().iterator();
					while(whIterator.hasNext()){ 
				  Map.Entry wme = (Map.Entry)whIterator.next();
				  if(wme.getValue()!=null){
						if(descTableColname.contains(wme.getValue())){}
						else
					 	    setWhereClouse.put(wme.getKey().toString(),wme.getValue());
				
					    }
			      	}
						if(setWhereClouse.size()>0){
					    int ksize=1;
						 Iterator<Entry<String, Object>> hIteratorr = setWhereClouse.entrySet().iterator();
							while(hIteratorr.hasNext()){ 
								Map.Entry sme = (Map.Entry)hIteratorr.next();
								if(ksize<setWhereClouse.size())
									updateTableQuery.append(sme.getKey()+" = '"+sme.getValue()+"' ,");
								else
									updateTableQuery.append(sme.getKey()+" = '"+sme.getValue()+"'");
								ksize++;
							} 
						}	
					if(size==1){
						updateTableQuery.append("WHERE"+" ");
				     int jsize=1;
					 Iterator<Object> hIteratorr = WhereClouse.entrySet().iterator();
						while(hIteratorr.hasNext()){ 
							Map.Entry fme = (Map.Entry)hIteratorr.next();
							if(jsize<WhereClouse.size())
								updateTableQuery.append(fme.getKey()+" = '"+fme.getValue()+"' ,");
							else
								updateTableQuery.append(fme.getKey()+" = '"+fme.getValue()+"'");
							jsize++;
						} 
					} else{
					if(subWhereClouse.size()>0){
					updateTableQuery.append("WHERE"+" ");
				     int ssize=1;
					 Iterator<Object> hIteratorr = subWhereClouse.entrySet().iterator();
						while(hIteratorr.hasNext()){ 
							Map.Entry fme = (Map.Entry)hIteratorr.next();
							if(ssize<subWhereClouse.size())
								updateTableQuery.append(fme.getKey()+" = '"+fme.getValue()+"' ,");
							else
								updateTableQuery.append(fme.getKey()+" = '"+fme.getValue()+"'");
							ssize++;
						} 
					  }
					}
		try {  
			//System.out.println("Updatequery"+updateTableQuery.toString());
            hSession= connectionSpace.openSession();
            hTransaction=hSession.beginTransaction();
            hSession.createSQLQuery(updateTableQuery.toString()).executeUpdate();
            hTransaction.commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			flage=false;
			hTransaction.rollback();
			}  
		   finally{
			   try{
				   ///
					///
			   }catch (Exception e) {
				   e.printStackTrace();
				// TODO: handle exception
			  }
			   }
		   size++;
		    }
		 }
		
		return flage;
	}
	
	@Override
	public boolean addDetails(Object objct) {
		// TODU All Variable
		boolean flag = true;
		try {
			hSession = HibernateSessionFactory.getSession();
			hTransaction = hSession.beginTransaction();
			hSession.save(objct);
			hTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		finally {
			try{
				///
				///
		 }catch (Exception e2) {
			e2.printStackTrace();
			// TODO: handle exception
		   }
		 }
		return flag;
	}
	@Override
	public boolean DeleteRecord(Object ObjectOfRecord, Class cls) {
		boolean flag = true;
		try{
			hSession = HibernateSessionFactory.getSession();
			hTransaction= hSession.beginTransaction();
			hSession.delete(ObjectOfRecord);
			hTransaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
			// TODO: handle exception
		}
		 finally{
			   try{
				   ///
				   ///
			   }catch (Exception e2) {
				   e2.printStackTrace();
				// TODO: handle exception
			}
			 
		 }
		
		// TODO Auto-generated method stub
		return flag;
	}
	@Override
	public boolean UpdateDetails(Object UpdateObject) {
		boolean flag = true;
		try{
			hSession = HibernateSessionFactory.getSession();
			hTransaction= hSession.beginTransaction();
			hSession.update(UpdateObject);
			hTransaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
			// TODO: handle exception
		}
		 finally{
			   try{
				   ///
				   ///
			        }catch (Exception e2) {
				   e2.printStackTrace();
				// TODO: handle exception
			}
			 
		 }
		
		// TODO Auto-generated method stub
		return flag;
	}	
	public List getNonUniqueData(String query, Map paramMap  ) {
		List mapDataObj = null;
		try {
			// Getting Session
			hSession = HibernateSessionFactory.getSession();
			hTransaction= hSession.beginTransaction();
			hQueryString=getNameQuery(query);
			hQuery = hSession.createSQLQuery(hQueryString);
		
			Iterator<Object> hIterator = paramMap.entrySet().iterator();
			while (hIterator.hasNext()) {
				  Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hIterator.next();
				//System.out.println("paramPair.getKey()"+paramPair.getKey()+"paramPair.getValue()"+paramPair.getValue());
				hQuery.setParameter(paramPair.getKey(), paramPair.getValue());
			}
			mapDataObj = hQuery.list();
			hTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				///
				///
			} catch (Exception hExp) {
				hExp.printStackTrace();
			}

		}
		return mapDataObj;
	}
	
	@SuppressWarnings("unchecked")
	public List getSelectTabledatafromListId(String tablename, String whereClose,SessionFactory connection){
		StringBuilder selectTableQuery = new StringBuilder("SELECT * FROM "+""+tablename+" "+"where id in ("+whereClose+")");  
		List mapDataObj = null;
		Transaction hTransaction=null;
	    Session hSession = null;
	    Query hQuery=null;
		try{
			
	            hSession=connection.openSession();
		     hTransaction = hSession.beginTransaction();   
		     hQuery=hSession.createSQLQuery(selectTableQuery.toString());
		     mapDataObj= hQuery.list();
		     hTransaction.commit(); 	
	}catch (Exception ex) {
		ex.printStackTrace();
		hTransaction.rollback();
		}  
	   finally{
		   try{
			   ///
				///
		   }catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
	   }
		return mapDataObj;
	}
	  
	public List getNonUniqueDataFromSqlProcedure(String query, Map paramMap  ) {
		List mapDataObj = null;
		try {
			// Getting Session
			hSession = HibernateSessionFactory.getSession();
			hTransaction= hSession.beginTransaction();
			hQueryString=getSqlNameQuery(query);
			hQuery = hSession.createSQLQuery(hQueryString);
		
			Iterator<Object> hIterator = paramMap.entrySet().iterator();
			while (hIterator.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hIterator.next();
				//System.out.println("paramPair.getKey()"+paramPair.getKey()+"paramPair.getValue()"+paramPair.getValue());
				hQuery.setParameter(paramPair.getKey(), paramPair.getValue());
			}
			mapDataObj = hQuery.list();
			hTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				///
				///
			} catch (Exception hExp) {
				hExp.printStackTrace();
			}

		}
		return mapDataObj;
	}
	public List getUniqueDataFromProcedure(String query, Map paramMap) {
		List mapDataObj = null;
		try {
			// Getting Session
			hSession = HibernateSessionFactory.getSession();
			hTransaction = hSession.beginTransaction();
			hQuery = hSession.getNamedQuery(query);
			Iterator<Object> hIterator = paramMap.entrySet().iterator();
			while (hIterator.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hIterator.next();
				hQuery.setParameter(paramPair.getKey(), paramPair.getValue());
			}
			//hQuery.setParameter("superuser", superUser);
			mapDataObj =  hQuery.list();
			hTransaction.commit();

		} catch (Exception e) {
			hTransaction.rollback();
		} finally {
			try {
				///
				HibernateSessionFactory.closeSession();
			} catch (Exception hExp) {
				hExp.printStackTrace();
			}

		}
		return mapDataObj;

	}
	public List viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,Map<String, Object>wherClause,SessionFactory connection)
	{
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		if(colmName!=null && colmName.size()>0)
		{
			selectTableData.append("select ");
			int i=1;
			for(String H:colmName)
			{
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " +tableName);
		}
		if(wherClause!=null)
		{
			if(wherClause.size()>0)
			{
				selectTableData.append(" where ");
			}
			int size=1;
			Set set =wherClause.entrySet(); 
			Iterator i = set.iterator();
			while(i.hasNext())
			{ 
				Map.Entry me = (Map.Entry)i.next();
				if(size<wherClause.size())
					selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
				else
					selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
				size++;
			} 
		}
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.getCurrentSession(); 
				transaction = session.beginTransaction();  
				Data=session.createSQLQuery(selectTableData.toString()).list();  
				transaction.commit(); 
			}catch (Exception ex) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,Map<String, Object>wherClause,SessionFactory connection) of class "+getClass(), ex);
				transaction.rollback();} 
		return Data;
	}
	//over ride method
	public List viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,SessionFactory connection)
	{
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		if(colmName!=null && colmName.size()>0)
		{
			selectTableData.append("select ");
			int i=1;
			for(String H:colmName)
			{
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from "+tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from "+tableName);
		}
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				Data=session.createSQLQuery(selectTableData.toString()).list();  
				transaction.commit(); 
			}catch (Exception ex) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,SessionFactory connection) of class "+getClass(), ex);
				transaction.rollback();} 
		return Data;
	}
	
	public Object getDataFromSqlProcedure(String query, Map paramMap)
	 {
		Object mapDataObj = null;
		String queryString = null;
		try {
				//Getting Session
				hSession = HibernateSessionFactory.getSession();
				hTransaction = hSession.beginTransaction();
				queryString = getSqlNameQuery(query);
				hQuery = hSession.createSQLQuery(queryString);
				Iterator<Object> hIterator = paramMap.entrySet().iterator();
				while (hIterator.hasNext()) {
					Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hIterator.next();
					//System.out.println("Key Value is>>>>>>"+paramPair.getKey());
					//System.out.println(" Value is>>>>>>"+paramPair.getValue());
					hQuery.setParameter(paramPair.getKey(), paramPair.getValue());
				}
				//hQuery.setParameter("superuser", superUser);
				//hQuery.setParameter("superUser2", superUser2);
				mapDataObj = (ClassType) hQuery.uniqueResult();
				//System.out.println(mapDataObj);
				hTransaction.commit();

			} catch (Exception e) {
				hTransaction.rollback();
			} finally {
				try {
					///
					HibernateSessionFactory.closeSession();
				} catch (Exception hExp) {
				}

			}
			return mapDataObj;

		}
	public List<Integer> getDataFromProcedure(String query, String searchField,
			Object searchObject) {
		List<Integer> mapDataList = null;
		try {
			// Getting Session

			hSession = HibernateSessionFactory.getSession();
			hTransaction = hSession.beginTransaction();
			hQuery = hSession.getNamedQuery(query);
			hQuery.setParameter(searchField, searchObject);
			mapDataList = hQuery.list();
			hTransaction.commit();

		} catch (Exception e) {
			hTransaction.rollback();
			e.printStackTrace();
		} finally {
			try {
				///
				HibernateSessionFactory.closeSession();
			} catch (Exception hExp) {
				hExp.printStackTrace();
			}

		}
		return mapDataList;

	}
	
	public boolean selectAllRecordForId(String tableName,String collmnId,String colmidValue,SessionFactory connection)
	{
		boolean status=false;
		
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				StringBuilder query=new StringBuilder();
				query.append("select * from "+tableName+" where "+collmnId+" in("+colmidValue+")");
				int size=session.createSQLQuery(query.toString()).executeUpdate();
				if(size!=0)
					status=true;
				transaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteAllRecordForId() of class "+getClass(), ex);
				ex.printStackTrace();
				transaction.rollback();
				} 
		
		return status;
	}
	
	
	public boolean selectRecordForId(String tableName,List<String> ColumnName, String collmnId,String colmidValue,SessionFactory connection)
	{
		boolean status=false;
		StringBuilder selectTableData = new StringBuilder("");  
		if(ColumnName!=null && ColumnName.size()>0){
			selectTableData.append("select ");
			int i=1;
			for(String H:ColumnName){
				if(i<ColumnName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from "+tableName);
				i++;
			}
		}  
		selectTableData.append(" "+tableName+" where "+collmnId+" in("+colmidValue+")");
		try {  
			   hSession=connection.openSession();
				hTransaction = hSession.beginTransaction();  
				int size=hSession.createSQLQuery(selectTableData.toString()).executeUpdate();
				if(size!=0)
					status=true;
				hTransaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			hTransaction.rollback();
			} 
		catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method selectRecordForId() of class "+getClass(), ex);
				ex.printStackTrace();
				hTransaction.rollback();
				} 
		
		return status;
	}
	
	
	public List<ClassType> getDataFromProcedure(String query,Map paramMap){
		List<ClassType> ObjectData=null;
		try{
			hSession=HibernateSessionFactory.getSession();
			hTransaction= hSession.beginTransaction();
			hQuery=hSession.getNamedQuery(query);
		   Iterator<Object> hIterator=paramMap.entrySet().iterator();
			while (hIterator.hasNext()) {
				Map.Entry<String, Object> paramPair=(Map.Entry<String, Object>)hIterator.next();
				hQuery.setParameter(paramPair.getKey(),paramPair.getValue());
			}
			//hQuery.setParameter("user", User);
			//hQuery.setParameter("superUser", superUser);
			ObjectData=hQuery.list();
			hTransaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		 finally{
			 try{
				 
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				// TODO: handle exception
			}
			 
		 }
		return ObjectData;
		
	}
	/** String to
	 * @Auther Pankaj Rajput
	 * @param getNameQuery
	 * @return 
	 */
   public String getNameQuery(String nameQueryStringd){
	   String NamedqueryString = null;
	   try{
		
		   NamedQueries  annotations=(com.Over2Cloud.annotation.NamedQueries)entityClass.getAnnotation(NamedQueries.class);
		   NamedQuery[] nameAnnotations= (com.Over2Cloud.annotation.NamedQuery[]) annotations.value();
			for (NamedQuery sqlObj : nameAnnotations) {
				if (!sqlObj.name().equals("")
						&& sqlObj.name().equals(nameQueryStringd))
					NamedqueryString = sqlObj.query();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return NamedqueryString;
   }
	
   public String getNativeNameQuery(Class classEntity,String nameQueryStringd){
	   String NamedqueryString = null;
	   try{
		   NamedQueries  annotations=(NamedQueries)entityClass.getAnnotation(NamedQueries.class);
		   NamedQuery[] nameAnnotations= (com.Over2Cloud.annotation.NamedQuery[]) annotations.value();
		 //System.out.println(sqlAnnotations.length);
			for (NamedQuery sqlObj : nameAnnotations) {
				if (!sqlObj.name().equals("")
						&& sqlObj.name().equals(nameQueryStringd))
					NamedqueryString = sqlObj.query();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	   return NamedqueryString;
   }
	/** String to
	 * @Auther Pankaj Rajput
	 * @param getSqlNameQuery
	 * @return 
	 */
	
	public String getSqlNameQuery(String nameString) {
		String queryString = null;
		try {
			SqlQueries annotations = (com.Over2Cloud.annotation.SqlQueries) entityClass.getAnnotation(SqlQueries.class);
			SqlQuery[] sqlAnnotations = (com.Over2Cloud.annotation.SqlQuery[]) annotations.value();
		
			//System.out.println(sqlAnnotations.length);
			for (SqlQuery sqlObj : sqlAnnotations) {
				if (!sqlObj.name().equals("")
						&& sqlObj.name().equals(nameString))
					queryString = sqlObj.query();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return queryString;

	}
	public String getSqlNameQuery(Class classEntity, String nameString) {
		String queryString = null;
		try {
			SqlQueries annotations = (SqlQueries) classEntity.getAnnotation(SqlQueries.class);
			SqlQuery[] sqlAnnotations = (SqlQuery[]) annotations.value();
			//System.out.println(sqlAnnotations.length);
			for (SqlQuery sqlObj : sqlAnnotations) {
				if (!sqlObj.name().equals("")
						&& sqlObj.name().equals(nameString))
					queryString = sqlObj.query();
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return queryString;

	}
	@SuppressWarnings({ "unchecked", "unused" })
	public  Map sortByComparator(Map unsortMap) {
		 
		List list = new LinkedList(unsortMap.entrySet());
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
                                       .compareTo(((Map.Entry) (o2)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

 
}
