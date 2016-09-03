package com.Over2Cloud.CommonClasses;
import hibernate.common.HibernateSessionFactory;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.ClassType;

import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.annotation.SqlQueries;
import com.Over2Cloud.annotation.SqlQuery;
import com.opensymphony.xwork2.ActionContext;
@SuppressWarnings({ "unchecked", "hiding" })
public abstract class AbstractCommonOperation<ClassType>   implements CommonforClass
{
	Map session = ActionContext.getContext().getSession();
	String superUser=  (String) session.get("superUser");
	String User= (String) session.get("uName");
	Class<ClassType> entityClass;
	private Transaction tx;
	{
		entityClass = (Class<ClassType>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}
	public AbstractCommonOperation() {
	   }
	// Log Impliment Define Variable
	static final Log log = LogFactory.getLog(AbstractCommonOperation.class);
	/*  1
	 * Add Data All Record In User(non-Javadoc)
	 * @see com.dreamsol.common.commanOperation#addDetails()
	 */
	public boolean addDetails(Object objct){
		Session session = null;
		boolean flag = true;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			session.save(objct);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			 log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:addDetails()",e);
		}
		finally 
		{
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		return flag;
	}
   
	/* 2:::2
	 * Update Of The Record Of the Data(non-Javadoc)
	 * @see com.dreamsol.common.commanOperation#UpdateDetails(java.lang.Object)
	 */
	public boolean UpdateDetails(Object UpdateObject) {
		Session session = null;
		boolean flag = true;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			session.update(UpdateObject);
			tx.commit();
		} catch (Exception e) {
			flag = false;
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:UpdateDetails()",e);
		} finally {
			
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		return flag;
	}
    
	/* 
	 * Delete Record All Of the Data(non-Javadoc)
	 * @see com.dreamsol.common.commanOperation#DeleteRecord(java.lang.Object)
	 */
	public boolean DeleteRecord(Object ObjectOfRecord,Class cCls ) {
    	Session session = null;
		boolean flag = true;
		try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls);
				session.delete(ObjectOfRecord);
				tx.commit();
	     	}
				catch (Exception e) 
				{   flag = false;
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:DeleteRecord()",e);
				} 
			finally
			{
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			}
		return flag;
			
		}
	
	/* 
	 * View All Record In generic type
	 * @see com.dreamsol.common.commanOperation#ServicesviewData(java.lang.Object)
	 */
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls) {
		    final Log log = LogFactory.getLog(cCls.getName());
		    List Servicesobjects = null;
            Criteria criteria=null;
            Session session=null;
            try {
             session=HibernateSessionFactory.getSession();
            criteria = session.createCriteria(cCls.getName());
            if(!GetRowTypeSorting.equals("")){
    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
    		}
    	  if(SearchOperation!=null && !SearchOperation.equals("")){
    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}
    	   }
    	  criteria.setFirstResult(to);
    	  criteria.setFirstResult(from);
    	  Servicesobjects = criteria.list();
    	  }
    	 catch(Exception e)
    	 {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
    	 finally
    	 {
    		 try 
    		 {
    			 if(session.isOpen())
    				{
    				 HibernateSessionFactory.closeSession();
    				}
    			 
    		 }
    		 catch (Exception e2)
    		 {
    			 log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
          return Servicesobjects;
        }  
	 public List getDropdownvalueAsIn(String ParameterPass, int ID,List UserList,Class cCls)
	    {
	       Session session = null;
		   List tempList = null;
			try{
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				if(ParameterPass!=null && !ParameterPass.equals(""))
				criteria.add(Restrictions.eq(ParameterPass,ID));
				criteria.add(Restrictions.in("User",UserList));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			} finally {
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			 }
			return tempList;
	    }
	    
	    
	
	/* 
	 * View All Record In generic type
	 * @see com.dreamsol.common.commanOperation#ServicesviewData(java.lang.Object)
	 */
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,int flag,Class cCls) {
		    final Log log = LogFactory.getLog(cCls.getName());
		    List Servicesobjects = null;
            Criteria criteria=null;
            Session session=null;
            try {
            session=HibernateSessionFactory.getSession();
            criteria = session.createCriteria(cCls.getName());
            if(!GetRowTypeSorting.equals("")){
    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
    		}
    	  if(SearchOperation!=null && !SearchOperation.equals("")){
    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}
    	   }
    	  criteria.add(Restrictions.eq("status",flag));
    	  criteria.setFirstResult(to);
    	  criteria.setFirstResult(from);
    	  Servicesobjects = criteria.list();
    	  }
    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
    	 finally{try {
    		 if(session.isOpen())
				{
    			 HibernateSessionFactory.closeSession();
				}
    		 
    	 
    	 
    	 
    	 }catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
          return Servicesobjects;
        }  
	
	/* 
	 * View All Record In generic type
	 * @see com.dreamsol.common.commanOperation#ServicesviewData(java.lang.Object)
	 */
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String ParId ,int id,Class cCls) {
		    final Log log = LogFactory.getLog(cCls.getName());
		    List Servicesobjects = null;
            Criteria criteria=null;
            Session session=null;
            try {
             session=HibernateSessionFactory.getSession();
            criteria = session.createCriteria(cCls.getName());
            if(!GetRowTypeSorting.equals("")){
    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
    		}
    		if(SearchOperation!=null && !SearchOperation.equals("")){
    		if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
    		if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
    		if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
    		if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
    		if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}
    	    }
    		if(ParId != null && ParId != "")
    			criteria.add(Restrictions.eq(ParId,id));
    		criteria.setFirstResult(to);
    		criteria.setFirstResult(from);
    	    Servicesobjects = criteria.list();
    		   }
    		catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
    		finally
    		{
    			try
    			{
    				if(session.isOpen())
    				{
    					HibernateSessionFactory.closeSession();
    				}
    			}
    			catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
            return Servicesobjects;
        }   
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String ParId ,int id,
    		Class cCls,String paramName,String paramValue) {
		    final Log log = LogFactory.getLog(cCls.getName());
		    List Servicesobjects = null;
            Criteria criteria=null;
            Session session=null;
            try {
            session=HibernateSessionFactory.getSession();
            criteria = session.createCriteria(cCls.getName());
            if(!GetRowTypeSorting.equals("")){
    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
    		}
    		if(SearchOperation!=null && !SearchOperation.equals("")){
    		if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
    		if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
    		if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
    		if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
    		if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}
    	    }
    		criteria.add(Restrictions.eq(paramName,paramValue));
    		//criteria.add(Restrictions.eq(ParId,id));
    		criteria.setFirstResult(to);
    		criteria.setFirstResult(from);
    	    Servicesobjects = criteria.list();
    		   }
    		catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
    		finally{try {if(session.isOpen())
			{
				HibernateSessionFactory.closeSession();
			}}catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
            return Servicesobjects;
        }   
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,HashMap<String, Object> conditionForKeyvalue,Class cCls) {
		    final Log log = LogFactory.getLog(cCls.getName());
		    List Servicesobjects = null;
            Criteria criteria=null;
            Session session=null;
            try {
             session=HibernateSessionFactory.getSession();
            criteria = session.createCriteria(cCls.getName());
            if(!GetRowTypeSorting.equals("")){
    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
    		}
    		if(SearchOperation!=null && !SearchOperation.equals("")){
    		if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
    		if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
    		if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
    		if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
    		if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}
    	    }
    		Set set =conditionForKeyvalue.entrySet(); 
			Iterator i = set.iterator();
			while(i.hasNext())
			{ Map.Entry me = (Map.Entry)i.next();
			  criteria.add(Restrictions.eq((String)me.getKey(),me.getValue()));
			} 
    		if(to!=-1 && from!=-1)
    		{
    		criteria.setFirstResult(to);
    		criteria.setFirstResult(from);
    		}
    	    Servicesobjects = criteria.list();
    		   }
    		catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
    		finally
    		{
    			try 
    			{
    				if(session.isOpen())
    				{
    					HibernateSessionFactory.closeSession();
    				}
    				}
    			catch (Exception e2)
    			{
    				log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
            return Servicesobjects;
        }   
	

	/* 4
	 * Get Record Of the data in DB.....(non-Javadoc)
	 * @see com.dreamsol.common.commanOperation#getRecordStatus()
	 */
    public int getRecordStatus(Class cCls )
    {
		Session GVCSess = null;
		Criteria GVCCriteria=null;
		List GVCList = null;
        int rowCount=0;
		try{
			  GVCSess = HibernateSessionFactory.getSession();
			  GVCCriteria =GVCSess.createCriteria(cCls.getName());
			  GVCCriteria.setProjection(Projections.rowCount());
			  GVCList = GVCCriteria.list();
			  if(GVCList!=null && GVCList.size()>0)
				  rowCount=Integer.parseInt(GVCList.get(0).toString());
		    } catch(Exception e) {log.error("@ERP::>> AbstractCommonOperation::AssetsDaoImpl && Method:getRecordStatus()",e);}
		      finally{try {
		    	  if(GVCSess.isOpen())
		    	  {
		    		  HibernateSessionFactory.closeSession();  
		    	  }
		      }
		      catch(Exception e2){log.error("@ERP::>> AbstractCommonOperation::AssetsDaoImpl && Method_Finally_Block:getRecordStatus()",e2);}
			  }
		return rowCount;
	}
    
	/* 
	 * View All Record In generic type
	 * @see com.dreamsol.common.commanOperation#ServicesviewData(java.lang.Object)
	 */
    public Object findRecordForPk(int id,Class cCls) {
    	SessionFactory sf = null;
		Session ses = null;
        Object obj = null;
        try {
        	ses= HibernateSessionFactory.getSession();
        	// here we are using load method of hibernate for Lazy loading
            obj = ses.load(cCls.getName(),id);
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ses.isOpen())
				{
					ses.flush();
					ses.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
        return obj;
    } 
    
    // For Drop down Value Pickup Method**************
    public List getDropdownvalue(String ParameterPass, String ID,Class cCls)
    {
    	   Session session = null;
		   List tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(cCls.getName());
			criteria.add(Restrictions.eq(ParameterPass,ID));
			criteria.add(Restrictions.eq("User",User));
			criteria.add(Restrictions.eq("superUser",superUser));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
		} finally {
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		return tempList;
}
    
    public List getDropdownvalue(String ParameterPass,Class cCls)
    {
   	   Session session = null;
	   List tempList = null;
	try {
		session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(cCls.getName());
		criteria.addOrder(Order.asc(ParameterPass));
		//criteria.add(Restrictions.eq("User",User));
		criteria.add(Restrictions.eq("superUser",superUser));
		tempList = criteria.list();
		tx.commit();
	} catch (Exception e) {
		log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
	} finally {
		if(session.isOpen())
		{
			session.flush();
			session.close();
		}
	}
	return tempList;
}
	/**
	 * create and match restriction for follwoing field
	 */
    public List getMaxdateValues(String ParameterPass,Class cCls)
    {
       java.util.Date Cdate = new Date();
   	   Session session = null;
	   List tempList = null;
	try {
		session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(cCls.getName());
		criteria.add(Restrictions.eq("User",User));
		criteria.add(Restrictions.eq("superUser",superUser));
		/**
		 * create and match restriction for follwoing field
		 */
		Disjunction max_dateTH = Restrictions.disjunction();
		max_dateTH.add(Restrictions.eq(ParameterPass, Cdate));
		Disjunction max_dateEQ = Restrictions.disjunction();
		max_dateEQ.add(Restrictions.lt(ParameterPass, Cdate));
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(max_dateTH).add(max_dateEQ);
		criteria.add(disjunction);	
		criteria.addOrder(Order.asc(ParameterPass));
		criteria.add(Restrictions.eq("User",User));
		criteria.add(Restrictions.eq("superUser",superUser));
		tempList = criteria.list();
		tx.commit();
	} catch (Exception e) {
		log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
	} finally {
		if(session.isOpen())
		{
			session.flush();
			session.close();
		}
	}
	return tempList;
}
    
    
    public List getDropdownvalue(String ParameterPass, int ID,Class cCls)
    {
       Session session = null;
	   List tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(cCls.getName());
			criteria.add(Restrictions.eq(ParameterPass,ID));
			criteria.add(Restrictions.eq("User",User));
			criteria.add(Restrictions.eq("superUser",superUser));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
		} finally {
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		 }
		return tempList;
    }
    
	@Override
	//  
	public List getDropdownvalue(String ParameterPass, List ID, Class cCls,
	   String oper,boolean isadmin,String superuserparameter,String superusername ) {
 	   Session session = null;
	   List tempList = null;
	try {
		session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(cCls.getName());
		 if(!isadmin){
		 if(oper.equals("in")){criteria.add(Restrictions.in(ParameterPass, ID));}
		 if(oper.equals("like")){criteria.add(Restrictions.like(ParameterPass, ID));}
		 if(oper.equals("eq")){criteria.add(Restrictions.eq(ParameterPass, ID));}
		 }
		criteria.add(Restrictions.eq(superuserparameter, superusername));
		tempList = criteria.list();
		tx.commit();
	  } catch (Exception e) {
		log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
	  } finally {
		  if(session.isOpen())
			{
				session.flush();
				session.close();
			}
	 }
	return tempList;
}
	/**
	 * get Joing Between two Object..........................................!!
	 */
public List getJoingTableId(String Param,int Id, String name,Class clss,Class class1){
    	Session session=null;
    	List tempList=null;
    	try{
    		session=HibernateSessionFactory.getSession();
    		Transaction tx=session.beginTransaction();
    		
    		DetachedCriteria ownerCriteria = DetachedCriteria.forClass(clss);
    		ProjectionList proList = Projections.projectionList();
    		proList.add(Projections.property(name));
    		ownerCriteria.setProjection(proList);
    		ownerCriteria.add(Restrictions.eq(Param,Id));
    		/**
    		 * get load another bean object for create inner query
    		 */
    		Criteria criteria = session.createCriteria(class1);
    		criteria.add(Property.forName("id").in(ownerCriteria));
    		//criteria.add(Restrictions.eq("User",User));
    		criteria.add(Restrictions.eq("superUser",superUser));
    	    tempList = criteria.list();
			tx.commit();
    	}catch (Exception e) {
			log.error("@ERP::>> Method:getDropdownvalue()",e);
    	}
    	finally {
    		if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
    	return tempList;
    }

/**
 * get Joing Between two Object..........................................!!
 */
public List getinnerjoindata(String Param,int Id, String name,Class clss,Class class1){
	Session session=null;
	List tempList=null;
	try{
		session=HibernateSessionFactory.getSession();
		Transaction tx=session.beginTransaction();
		
		DetachedCriteria ownerCriteria = DetachedCriteria.forClass(clss);
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property(name));
		ownerCriteria.setProjection(proList);
		ownerCriteria.add(Restrictions.eq(Param,Id));
		/**
		 * get load another bean object for create inner query
		 */
		Criteria criteria = session.createCriteria(class1);
		criteria.add(Property.forName("id").in(ownerCriteria));
		//criteria.add(Restrictions.eq("User",User));
		//criteria.add(Restrictions.eq("superUser",superUser));
	    tempList = criteria.list();
		tx.commit();
	}catch (Exception e) {
		log.error("@ERP::>> Method:getDropdownvalue()",e);
	}
	finally {
		if(session.isOpen())
		{
			session.flush();
			session.close();
		}
	}
	return tempList;
}

      /**
      * get Selected Field value .....................................................!!
      */
      /**
      * get Selected Field value .....................................................!!
      */
    public List getSelectedvalues(String PassSelectFieldValue[],HashMap<String, String> conditionForKeyvalue, Class cCls) 
    {Session GSess = null;
	List GTList = null;
	try 
	{
		GSess = HibernateSessionFactory.getSession();
		Criteria criteria = GSess.createCriteria(cCls.getName());
		if(PassSelectFieldValue.length>0)
		{
			ProjectionList proList = Projections.projectionList();
		    for(String h:PassSelectFieldValue){proList.add(Projections.property(h));}
			criteria.setProjection(proList);
		}
		Set set =conditionForKeyvalue.entrySet(); 
		if(set.size()>0)
		{
			Iterator i = set.iterator();
			while(i.hasNext())
			{ Map.Entry me = (Map.Entry)i.next();
			  criteria.add(Restrictions.eq((String)me.getKey(),me.getValue()));
			} 
			
		}
		GTList = criteria.list();
	} catch (Exception e) {
		log
				.error(
						"@ERP::>> Problem in UserAccountDaoImpl in method getSuperNType()",
						e);

	} finally {
		try {
			if(GSess.isOpen())
			{
				HibernateSessionFactory.closeSession();
			}
		} catch (Exception e2) {
			log.error("@ERP::>> Problem in Closing Hibernate Session ", e2);

		}

	}

	return GTList;}
    public List getDropdownvalue(String ParameterPass, int ID,Class cCls,String superUser)
    {
    	   Session session = null;
		   List tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(cCls.getName());
			criteria.add(Restrictions.eq(ParameterPass,ID));
			criteria.add(Restrictions.eq("superuser",superUser));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
		} finally {
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		return tempList;
}

 
    
    
    
    
    public Object getUniqueDataByField(String searchField,
			Object searchString,Class clszz) {
		Object dataObject = null;
		Criteria hCriteria = null;
		Session hSession=null;
		Transaction hTransaction;
		try {
			hSession = HibernateSessionFactory.getSession();
			hTransaction = hSession.beginTransaction();
			if (searchField != null && !searchField.equals("") &&
					searchString!=null) {
				hCriteria = hSession.createCriteria(clszz.getName()).add(
						Restrictions.eq(searchField, searchString));

				if (superUser != null && !superUser.equals(""))
					hCriteria.add(Restrictions.eq("superUser", superUser));

				hCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

				dataObject = (ClassType)hCriteria.uniqueResult();
				
			}
			hTransaction.commit();
			

		} catch (Exception e) {

			log
					.error(
							"@ERP::>> Problem in "
									+ getClass()
									+ " in method getDataByField(String searchField, Object searchString)",
							e);

		} finally {
			try {
				if(hSession.isOpen())
				{
					hSession.flush();
					HibernateSessionFactory.closeSession();
				}
				
			} catch (Exception hExp) {
				log.error("@ERP::>> Problem in " + getClass()
						+ " in Closing Hibernate Session", hExp);
			}

		}
		return dataObject;
	}
public Object getUniqueDataByField(String searchField,
			Object searchString,String parameter,Object paramValue,Object paramValue1,String user,Class clszz) {
		Object dataObject = null;
		Criteria hCriteria = null;
		Session hSession=null;
		Transaction hTransaction=null;
		try {
			hSession = HibernateSessionFactory.getSession();
			hTransaction = hSession.beginTransaction();
			if (searchField != null && !searchField.equals("") &&searchString!=null) {
				hCriteria = hSession.createCriteria(clszz.getName()).add(
						Restrictions.eq(searchField, searchString));
				if (superUser != null && !superUser.equals(""))
					hCriteria.add(Restrictions.eq("superUser", superUser));
				hCriteria.add(Restrictions.eq("User", user));
				hCriteria.add(Restrictions.between(parameter, paramValue,paramValue1));
				hCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				dataObject = (ClassType)hCriteria.uniqueResult();
				
			}
			hTransaction.commit();}
		catch (Exception e) {
			log.error("@SFA::>> Problem in "+ getClass()+ " in method getDataByField(String searchField, Object searchString)",e);

		} finally {
			try {
				if(hSession.isOpen())
				{
					hSession.flush();
					HibernateSessionFactory.closeSession();
				}
				
			} catch (Exception hExp) {
				log.error("@SFA::>> Problem in " + getClass()
						+ " in Closing Hibernate Session", hExp);
			}

		}
		return dataObject;
	}
public Object getUniqueDataByField(String searchField,
			Object searchString,String parameter,Object paramValue,String user,Class clszz) {
		Object dataObject = null;
		Criteria hCriteria = null;
		Session hSession=null;
		Transaction hTransaction=null;
		try {
			hSession = HibernateSessionFactory.getSession();
			hTransaction = hSession.beginTransaction();
			if (searchField != null && !searchField.equals("") &&searchString!=null) {
				hCriteria = hSession.createCriteria(clszz.getName()).add(
						Restrictions.eq(searchField, searchString));
				if (superUser != null && !superUser.equals(""))
					//hCriteria.add(Restrictions.eq("superUser", superUser));
				//hCriteria.add(Restrictions.eq("User", user));
				hCriteria.add(Restrictions.eq(parameter,paramValue) );
				hCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				dataObject = (ClassType)hCriteria.uniqueResult();
		
			}
			hTransaction.commit();}
		catch (Exception e) {
			log.error("@SFA::>> Problem in "+ getClass()+ " in method getDataByField(String searchField, Object searchString)",e);

		} finally {
			try {
				if(hSession.isOpen())
				{
					hSession.flush();
					HibernateSessionFactory.closeSession();
				}
			} catch (Exception hExp) {
				log.error("@SFA::>> Problem in " + getClass()
						+ " in Closing Hibernate Session", hExp);
			}

		}
		return dataObject;
	}
 
	public List getNonUniqueDataFromSqlProcedure(String query, Map paramMap  ) {
		List mapDataObj = null;
		Query hQuery = null;
        String queryString=null;
        Session session = null;
		try {
			// Getting Session
			session = HibernateSessionFactory.getSession();
			 tx= session.beginTransaction();
			queryString=getSqlNameQuery(query);
			hQuery = session.createSQLQuery(queryString);
			Iterator<Object> hIterator = paramMap.entrySet().iterator();
			while (hIterator.hasNext()) {
				Map.Entry<String, Object> paramPair = (Map.Entry<String, Object>) hIterator.next();
				hQuery.setParameter(paramPair.getKey(), paramPair.getValue());
			}
			//System.out.println("***************"+superUser);
			//hQuery.setParameter("superuser", superUser);
			mapDataObj = hQuery.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log
					.error(
							"@ERP::>> Problem in "
									+ getClass()
									+ " in method getUniqueDataFromProcedure(String query,Map paramMap)",
							e);

		} finally {
			try {
				if(session.isOpen())
				{
					session.flush();
					HibernateSessionFactory.closeSession();
				}
				
			} catch (Exception hExp) {
				log.error("@ERP::>> Problem in " + getClass()
						+ " in Closing Hibernate Session", hExp);
			}

		}
		return mapDataObj;

	}
	public String getSqlNameQuery(String nameString) {
		String queryString = null;
		try {
			SqlQueries annotations = (com.Over2Cloud.annotation.SqlQueries) entityClass.getAnnotation(SqlQueries.class);
			SqlQuery[] sqlAnnotations = (com.Over2Cloud.annotation.SqlQuery[]) annotations.value();
			for (SqlQuery sqlObj : sqlAnnotations) {
				if (!sqlObj.name().equals("")
						&& sqlObj.name().equals(nameString))
					queryString = sqlObj.query();
			}
		} catch (Exception e) {
			log.error(
					"@ERP::>> Problem in "+ getClass()
							+ " in method getSqlNameQuery(String nameString)",
					e);
		}
		return queryString;

	}
	public String getSqlNameQuery(Class classEntity, String nameString) {
		String queryString = null;
		try {
			SqlQueries annotations = (SqlQueries) classEntity.getAnnotation(SqlQueries.class);
			SqlQuery[] sqlAnnotations = (SqlQuery[]) annotations.value();
			for (SqlQuery sqlObj : sqlAnnotations) {
				if (!sqlObj.name().equals("")
						&& sqlObj.name().equals(nameString))
					queryString = sqlObj.query();
			}
		} catch (Exception e) {
			log
			.error(
					"@ERP::>> Problem in "
							+ getClass()
							+ " in method getSqlNameQuery(Class classEntity, String nameString)",
					e);
		}
		return queryString;

	}
	public List getDropdownvalueWithOutUser(String ParameterPass, int ID,String currentmonth,Class cCls)
    {
       Session session = null;
	   List tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(cCls.getName());
			criteria.add(Restrictions.eq(ParameterPass,ID));
			criteria.add(Restrictions.eq("currentmonth",currentmonth));
			//criteria.add(Restrictions.eq("superUser",superUser));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
		} finally {
			
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
			
		 }
		return tempList;
    }

	 public List getDropdownvalue1(String ParameterPass, int ID,Class cCls,String superUser)
    {
    	   Session session = null;
		   List tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(cCls.getName());
			criteria.add(Restrictions.eq(ParameterPass,ID));
			//criteria.add(Restrictions.eq("superUser",superUser));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
		} finally {
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		return tempList;
}
	 public List ServicesviewData(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String UserYes,String Superyes) {
			
			
		     
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	            if(!GetRowTypeSorting.equals("")){
	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
	    		}
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}}
	    	  //if(UserYes.equalsIgnoreCase("Yes")){criteria.add(Restrictions.eq("User",User));}
	    	  //if(Superyes.equalsIgnoreCase("Yes")){ criteria.add(Restrictions.eq("superUser",superUser));}
	    	 
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally{
	    		 try
	    		 {
	    		 if(session.isOpen())
	    		 {
	    			 HibernateSessionFactory.closeSession();
	    		 }
	    		 
	    	 }catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }
	 /* 
		 * View All current month Record In generic type
		 * @see com.dreamsol.common.commanOperation#ServicesviewData(java.lang.Object)
		 */
		public List ServicesviewData(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String currentmonth) {
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	            if(!GetRowTypeSorting.equals("")){
	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
	    		}
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}
	    	   }
	  		 // criteria.add(Restrictions.eq("User",User));
			 // criteria.add(Restrictions.eq("superUser",superUser));
			  criteria.add(Restrictions.eq("currentmonth",currentmonth));
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally{try {
	    		 if(session.isOpen())
	 			{
	 				session.flush();
	 				session.close();
	 			}
	    		 
	    	 }catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }  
	 public List ServicesviewforuserData(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String user,String superuser) {
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	            if(!GetRowTypeSorting.equals("")){
	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
	    		}
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}}
	    	    //criteria.add(Restrictions.eq("User",user));
	    	    //criteria.add(Restrictions.eq("superUser",superuser));
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally{
	    		 try 
	    		 {
	    			 if(session.isOpen())
	    				{
	    					session.flush();
	    					session.close();
	    				}
	    		}catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }
	
	 public List ServicesviewforuserDataNewUser(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String user,String superuser) {
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	            if(!GetRowTypeSorting.equals("")){
	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
	    		}
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}}
	    	    //criteria.add(Restrictions.eq("user",user));
	    	    //criteria.add(Restrictions.eq("superUser",superuser));
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally
	    	 {
	    		 try 
	    		 {
	    			 if(session.isOpen())
	    			 {
	    				 session.flush();
	    				 session.close();
	    			 }
	    		 }
	    		 catch (Exception e2)
	    		 {log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }

	 public List getDropdownvalueEKA(String ParameterPass, String ID,Class cCls)
	    {
	    	   Session session = null;
			   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				if(!ParameterPass.equalsIgnoreCase("") && !ID.equalsIgnoreCase(""))
				criteria.add(Restrictions.eq(ParameterPass,ID));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			} finally {
				if(session.isOpen())
   			 	{
					session.flush();
   				 	session.close();
   			 	}
			}
			return tempList;
	}
	 
	 public List getDropdownvalueEKAName(String ParameterPass, Object ID,Class cCls)
	    {
	    	   Session session = null;
			   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				criteria.add(Restrictions.eq(ParameterPass,ID));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			} finally
				{
				if(session.isOpen())
   			 	{
					session.flush();
					session.close();
   			 	}
			}
			return tempList;
	}
	 public Object findRecordForObject(String id,Class cCls) {
	    	SessionFactory sf = null;
			Session ses = null;
	        Object obj = null;
	        try {
	        	ses= HibernateSessionFactory.getSession();
	        	// here we are using load method of hibernate for Lazy loading
	        	Criteria criteria = ses.createCriteria(cCls.getName());
				criteria.add(Restrictions.eq("user",id));
				obj=criteria.uniqueResult();
	        } catch (Exception e) {
				e.printStackTrace();
			} finally {
				
				if(ses.isOpen())
				{
					try {
						ses.flush();
						ses.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
				}
				
			}
	        return obj;
	    }  
	 
	 public List getDropdownvalueWithOutUser(String ParameterPass, int ID,Class cCls)
	    {
	       Session session = null;
		   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				criteria.add(Restrictions.eq(ParameterPass,ID));
				criteria.add(Restrictions.eq("superUser",superUser));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			} finally {
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			 }
			return tempList;
	    }

	 public List ServicesviewforuserDataWithoutUser(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String user,String superuser) {
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	            if(!GetRowTypeSorting.equals("")){
	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
	    		}
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}}
	    	  criteria.add(Restrictions.eq("superUser",superuser));
	    	  // check for  some restriction
	    	  if(user.equals("1")){
	    		 // criteria.add(Restrictions.eq("status",Integer.parseInt(user))); 
	    		  }
	    	  if(user.equals("0")){
		    		// criteria.add(Restrictions.eq("status",Integer.parseInt(user)));
	    		  }
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally
	    	 {
	    		 try
	    		 {
	    			 if(session.isOpen())
	    			 {
	    				 HibernateSessionFactory.closeSession();
	    			 }
	    				 
	    		 }
	    		 catch (Exception e2)
	    		 {log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }
	 
	 public List ServicesviewforuserDataWithoutUser(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String currentmonth,Class cCls) {
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	            if(!GetRowTypeSorting.equals("")){
	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
	    		}
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}}
	    	  //criteria.add(Restrictions.eq("superUser",superUser));
	    	  //criteria.add(Restrictions.eq("currentmonth",currentmonth));
	    	  
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally
	    	 {try 
	    	 {
	    		 if(session.isOpen())
	    		 {
	    			 HibernateSessionFactory.closeSession();
	    		 }
	    		 
	    	 }
	    	 catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }
	 
	 /**
	  * 
	  * code added by sandeep on 22-06-2012
	 
	 /**
	  * 
	  * code added by sandeep on 22-06-2012
	  * 
	  * 
	  */
	 
	 public List viewDataByLike(String tag)
		{
			Session session=null;
			List Data=null;
			try
			{
			String temp = "%"+tag+"%";
			session=HibernateSessionFactory.getSession();
			String sql_query = "select id,process_type,process_stage,level1,level2,level3,level4,level5 from ProcessgmtBean kr where kr.process_type like ?";
		    Query query=session.createQuery(sql_query);
		    query.setString(0, temp);
		    Data=query.list();
			}
			catch(Exception e)
			{
				
				e.printStackTrace();
			}
			 finally {
					if(session.isOpen())
					{
						session.flush();
						session.close();
					}
				}
			return Data;
		}

	 
	 public List multiSearchViewdata(int to, int from, JSONObject jsonFilter, String SortType,String GetRowTypeSorting, Class clazz)
			{
				 List Servicesobjects = null;
		         Criteria criteria=null;
		         String datefrom=null;
		         String dateto=null;
		         boolean flag=false;
				  if(jsonFilter != null ){
				    	 String groupOp = jsonFilter.getString("groupOp");
				    	 JSONArray rules = jsonFilter.getJSONArray("rules");
				 		 int rulesCount = JSONArray.getDimensions(rules)[0];
				 		Session session=null;
				 		 try
				 		 {
				 			 session=HibernateSessionFactory.getSession();
				             criteria = session.createCriteria(clazz.getName());
				             if(!GetRowTypeSorting.equals("")){
				 	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
				 	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
				 	    		}
				 			 for (int i = 0; i < rulesCount; i++){
					 			JSONObject rule = rules.getJSONObject(i);
								if(rule.getString("field").equals("datefrom")){
								datefrom=rule.getString("data");     
								}
								if(rule.getString("field").equals("dateto")){
								dateto=rule.getString("data");	 
								}
							
								if((rule.getString("op").trim().equals("eq")) && (!rule.getString("field").equals("datefrom")&&!rule.getString("field").equals("dateto"))){
								if(rule.getString("op").trim().equals("eq")) criteria.add(Restrictions.eq(rule.getString("field"), rule.getString("data")));
								}
								if (rule.getString("op").trim().equals("ne")) criteria.add(Restrictions.ne(rule.getString("field"), rule.getString("data")));
								if (rule.getString("op").trim().equals("lt")) criteria.add(Restrictions.lt(rule.getString("field"), rule.getString("data") + "%"));
								if (rule.getString("op").trim().equals("gt")) criteria.add(Restrictions.gt(rule.getString("field"), "%" + rule.getString("data") + "%"));
								if (rule.getString("op").trim().equals("ge")) criteria.add(Restrictions.ge(rule.getString("field"), "%" + rule.getString("data") + "%"));
								if (rule.getString("op").trim().equals("le")) criteria.add(Restrictions.le(rule.getString("field"), "%" + rule.getString("data") + "%"));
				 			 }
				 		  if((datefrom!=null && !datefrom.equalsIgnoreCase(""))&& (datefrom!=null && !datefrom.equalsIgnoreCase(""))){
				 		  criteria.add(Restrictions.between("currentdate",datefrom,dateto));}
				          //criteria.add(Restrictions.eq("User",User));
				      	 // criteria.add(Restrictions.eq("superUser",superUser));
				       	  criteria.setFirstResult(to);
				       	  criteria.setFirstResult(from);
				       	  Servicesobjects = criteria.list();	
				 		 }
				    catch(Exception e){e.printStackTrace();}
				 		 finally {
								if(session.isOpen())
								{
									session.flush();
									session.close();
								}
							}
				  }
				return Servicesobjects;
			}

	 /**
	  * method added by sandeep on 20-08-2012 & 22-8-2012
	  */
	 
	 public List getDropdownvalueForHierarchywithsuperuser(String ParameterPass, String ID,Class cCls)
	    {
	    	   Session session = null;
			   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				criteria.add(Restrictions.eq(ParameterPass,ID));
				//criteria.add(Restrictions.eq("User",User));
				criteria.add(Restrictions.eq("superuser",superUser));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			}  finally {
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			}
			return tempList;
	} 
	 
	 public List getDesignationLevelName(String ParameterPass, int ID,Class cCls)
	    {
	    	   Session session = null;
			   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				criteria.add(Restrictions.eq(ParameterPass,ID));
				//criteria.add(Restrictions.eq("User",User));
				criteria.add(Restrictions.eq("superuser",superUser));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			}  finally {
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			}
			return tempList;
	} 
	 public List ServicesviewforuserDataWithoutUserWithParam(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String param,String val,String superuser) {
			    final Log log = LogFactory.getLog(cCls.getName());
			    List Servicesobjects = null;
	            Criteria criteria=null;
	            Session session=null;
	            try {
	            session=HibernateSessionFactory.getSession();
	            criteria = session.createCriteria(cCls.getName());
	    	  if(SearchOperation!=null && !SearchOperation.equals("")){
	    	  if(SearchOperation.equals("eq")){criteria.add(Restrictions.eq(SearchField,SearchString));}
	    	  if(SearchOperation.equals("cn")){criteria.add(Restrictions.ilike(SearchField,"%"+SearchString+"%"));}
	    	  if(SearchOperation.equals("bw")){criteria.add(Restrictions.ilike(SearchField,SearchString+"%"));}
	    	  if(SearchOperation.equals("ne")){criteria.add(Restrictions.ne(SearchField, SearchString));}
	    	  if(SearchOperation.equals("ew")){criteria.add(Restrictions.ilike(SearchField, "%"+SearchString));}}
	    	  criteria.add(Restrictions.eq(param,val));
	    	  //criteria.add(Restrictions.eq("superUser",superuser));
	    	  if(to!=-1 && from !=-1)
	    	  {
	    	  criteria.setFirstResult(to);
	    	  criteria.setFirstResult(from);
	    	  }
	    	  Servicesobjects = criteria.list();
	    	  }
	    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
	    	 finally{
	    		 try 
	    		 {
	    			 if(session.isOpen())
	 				{
	 					session.flush();
	 					session.close();
	 				}
	    			 }
	    		 catch (Exception e2)
	    		 {log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
	          return Servicesobjects;
	        }
	 
	 
	 public List multiSearchViewdataforalluser(int to, int from, JSONObject jsonFilter, String SortType,String GetRowTypeSorting, Class clazz)
			{
				 List Servicesobjects = null;
		         Criteria criteria=null;
		         String datefrom=null;
		         String dateto=null;
		         boolean flag=false;
				  if(jsonFilter != null ){
				    	 String groupOp = jsonFilter.getString("groupOp");
				    	 JSONArray rules = jsonFilter.getJSONArray("rules");
				 		 int rulesCount = JSONArray.getDimensions(rules)[0];
				 		Session session=null;
				 		 try
				 		 {
				 			 session=HibernateSessionFactory.getSession();
				             criteria = session.createCriteria(clazz.getName());
				             if(!GetRowTypeSorting.equals("")){
				 	    		if(SortType.equals("asc")){criteria.addOrder(Order.asc(GetRowTypeSorting));}
				 	    	    if(SortType.equals("desc")){criteria.addOrder(Order.desc(GetRowTypeSorting));}
				 	    		}
				 			 for (int i = 0; i < rulesCount; i++){
					 			JSONObject rule = rules.getJSONObject(i);
								if(rule.getString("field").equals("datefrom")){
								datefrom=rule.getString("data");     
								}
								if(rule.getString("field").equals("dateto")){
								dateto=rule.getString("data");	 
								}
							
								if((rule.getString("op").trim().equals("eq")) && (!rule.getString("field").equals("datefrom")&&!rule.getString("field").equals("dateto"))){
								if(rule.getString("op").trim().equals("eq")) criteria.add(Restrictions.eq(rule.getString("field"), rule.getString("data")));
								}
								if (rule.getString("op").trim().equals("ne")) criteria.add(Restrictions.ne(rule.getString("field"), rule.getString("data")));
								if (rule.getString("op").trim().equals("lt")) criteria.add(Restrictions.lt(rule.getString("field"), rule.getString("data") + "%"));
								if (rule.getString("op").trim().equals("gt")) criteria.add(Restrictions.gt(rule.getString("field"), "%" + rule.getString("data") + "%"));
								if (rule.getString("op").trim().equals("ge")) criteria.add(Restrictions.ge(rule.getString("field"), "%" + rule.getString("data") + "%"));
								if (rule.getString("op").trim().equals("le")) criteria.add(Restrictions.le(rule.getString("field"), "%" + rule.getString("data") + "%"));
				 			 }
				 		  if((datefrom!=null && !datefrom.equalsIgnoreCase(""))&& (datefrom!=null && !datefrom.equalsIgnoreCase(""))){
				 		  criteria.add(Restrictions.between("currentdate",datefrom,dateto));}
				      	  //criteria.add(Restrictions.eq("superUser",superUser));
				       	  criteria.setFirstResult(to);
				       	  criteria.setFirstResult(from);
				       	  Servicesobjects = criteria.list();	
				 		 }
				    catch(Exception e){e.printStackTrace();}
				 		 finally
				 		 {
				 			if(session.isOpen())
			 				{
			 					session.flush();
			 					session.close();
			 				}
				 		 }
				  }
				return Servicesobjects;
			}
	 public Object getUniqueDataByField(String searchField,
				Object searchString,String parameter,Object paramValue,Class clszz) {
			Object dataObject = null;
			Criteria hCriteria = null;
			Session hSession=null;
			Transaction hTransaction;
			try {
				hSession = HibernateSessionFactory.getSession();
				hTransaction = hSession.beginTransaction();
				if (searchField != null && !searchField.equals("") &&
						searchString!=null) {
					hCriteria = hSession.createCriteria(clszz.getName()).add(
							Restrictions.eq(searchField, searchString));

					if (superUser != null && !superUser.equals(""))
						
						//hCriteria.add(Restrictions.eq("superUser", superUser));

					hCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

					dataObject = (ClassType)hCriteria.uniqueResult();
				}
				hTransaction.commit();
				

			} catch (Exception e) {

				log
						.error(
								"@ERP::>> Problem in "
										+ getClass()
										+ " in method getDataByField(String searchField, Object searchString)",
								e);

			} finally {
				try {
					if(hSession.isOpen())
	 				{
						hSession.flush();
						HibernateSessionFactory.closeSession();
	 				}
					
				} catch (Exception hExp) {
					log.error("@ERP::>> Problem in " + getClass()
							+ " in Closing Hibernate Session", hExp);
				}

			}
			return dataObject;
		}
		
	  
	    // For Drop down Value Pickup Method**************
	    public List getDropdownvalue(String ParameterPass, String user,String superuser,Class cCls)
	    {
	    	   Session session = null;
			   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				criteria.addOrder(Order.asc(ParameterPass));
				//criteria.add(Restrictions.eq("User",User));
				//criteria.add(Restrictions.eq("superUser",superUser));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			} finally {
				if(session.isOpen())
 				{
 					session.flush();
 					session.close();
 				}
			}
			return tempList;
	}
	    
	    public List getDropdownvalueWithOutuser(String ParameterPass, String ID,Class cCls)
	    {
	       Session session = null;
		   List tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(cCls.getName());
				if(!ParameterPass.equalsIgnoreCase("") && !ID.equalsIgnoreCase(""))
				criteria.add(Restrictions.eq(ParameterPass,ID));
				//criteria.add(Restrictions.eq("superuser",superUser));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
			} finally {
				if(session.isOpen())
 				{
 					session.flush();
 					session.close();
 				}
			 }
			return tempList;
	    }
	    
	    public List cloudgetDropdownvalue(String ParameterPass,Class cCls)
	    {
	   	   Session session = null;
		   List tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(cCls.getName());
			criteria.addOrder(Order.asc(ParameterPass));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:getDropdownvalue()",e);
		} finally {
			if(session.isOpen())
				{
					session.flush();
					session.close();
				}
		}
		return tempList;
	}
}
