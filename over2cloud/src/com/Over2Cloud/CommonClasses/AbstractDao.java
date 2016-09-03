package com.Over2Cloud.CommonClasses;

import hibernate.common.HibernateSessionFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Over2Cloud.procegure.annotation.SqlQueries;
import com.Over2Cloud.procegure.annotation.SqlQuery1;

@SuppressWarnings("unchecked")
public abstract class AbstractDao<T>
{
	private static final Log log = LogFactory.getLog(AbstractDao.class);
	private String superUser;
	private String User;
	public AbstractDao(){};
	public AbstractDao(String superUser,String User) {
		this.superUser = superUser;
		this.User=User;
	}
	Class<T> Classtype;
	{
		Classtype = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public boolean updateDataFromSqlProcedure(String query, Map paramMap)
	{
		boolean flag=false;
		Query hQuery = null;
        String queryString=null;
        Session session  = null;
		try {
			// Getting Session
			session = HibernateSessionFactory.getSession();
			Transaction Tx = session.beginTransaction();
			queryString=getSqlNameQuery(query);
			hQuery = session.createSQLQuery(queryString);
			Iterator<Object> hIterator = paramMap.entrySet().iterator();
			while (hIterator.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hIterator.next();
				hQuery.setParameter(paramPair.getKey(), paramPair.getValue());}
			 hQuery.executeUpdate();
			 //setSerializable(arg0, arg1)
			 flag=true;
			 Tx.commit();

		} catch (Exception e) {
			log.error("@ERP::>> Problem in "
									+ getClass()
									+ " in method getUniqueDataFromProcedure(String query,Map paramMap)",e);} finally {
								try {
									 session.flush();
				                      HibernateSessionFactory.closeSession();} catch (Exception hExp) {
				log.error("@ERP::>> Problem in " + getClass()
						+ " in Closing Hibernate Session", hExp);
			}

		}
		return flag;

	
	}
	public String getSqlNameQuery(String nameString) {
		String queryString = null;
		try {
			com.Over2Cloud.procegure.annotation.SqlQueries annotations = (com.Over2Cloud.procegure.annotation.SqlQueries)Classtype.getAnnotation(SqlQueries.class);
			SqlQuery1[] sqlAnnotations = (com.Over2Cloud.procegure.annotation.SqlQuery1[]) annotations.value();
			for (SqlQuery1 sqlObj : sqlAnnotations) 
			{
				if (!sqlObj.name().equals("")&& sqlObj.name().equals(nameString)){queryString = sqlObj.query();}
			}
		} catch (Exception e) {
			log.error("@Sfa::>> Problem in "+ getClass()+ " in method getSqlNameQuery(String nameString)",e);
		}
		return queryString;
	}
    public List<T> getCallProcedure(String query,Map paramMap ) 
    {
		Session session  = null;
		List<T>  ListofOutlet= null;
		Query procegureQuery = null;
		try 
		{
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			procegureQuery = session.getNamedQuery(query);
			Iterator<Object> hIterator=paramMap.entrySet().iterator(); 
			while (hIterator.hasNext())
			{Map.Entry<String,Object> paramPair=(Map.Entry<String, Object>)hIterator.next(); 
			 procegureQuery.setParameter(paramPair.getKey(),paramPair.getValue());}
			procegureQuery.setParameter("superuser",superUser);
			ListofOutlet = procegureQuery.list();
			tx.commit();

		} catch (Exception e) {
			log.error("@ERP::>> Problem in "
									+ getClass()
									+ " in method getHierarchyEmp(int levelId,int deptId,String query)",e);
		} finally {
			try {session.flush();
				HibernateSessionFactory.closeSession();}
			catch (Exception hExp) {
				log.error("@ERP::>> Problem in " + getClass()
						+ " in Closing Hibernate Session", hExp);
			}

		}
		return ListofOutlet;

	}
    public List GetDataFromDirectSqlProcegure(String Progegurename,Map paramMap)
    { 
    	List DataObject = null;
    	Session session  = null;
    	Query  Query = null;
    	try
    	{
    		session = HibernateSessionFactory.getSession();
			Transaction Tx = session.beginTransaction();
			Query = session.createSQLQuery(getSqlNameQuery(Progegurename));
			Iterator<Object> Iter=paramMap.entrySet().iterator();
			while (Iter.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) Iter.next();
				Query.setParameter(paramPair.getKey(), paramPair.getValue());
			}
			DataObject=Query.list();
			Tx.commit();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally{
    		session.flush();
    		session.close();
    	}
    	return DataObject;
    }

	
}
