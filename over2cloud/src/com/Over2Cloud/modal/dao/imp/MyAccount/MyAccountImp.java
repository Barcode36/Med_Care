package com.Over2Cloud.modal.dao.imp.MyAccount;

import hibernate.common.HibernateSessionFactory;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;

public class MyAccountImp 
{
	
	
	@SuppressWarnings("unchecked")
	public List GetAllAccountDetails() {
		List  existList = null;
	    Session session = null;
	    try 
		{
		        session=HibernateSessionFactory.getSession();
				String listidQuery=	"select clientinfo.id," +
									"reg.org_name," +
									"reg.org_reg_name," +
									"reg.accountType," +
									"ProI.productcode, " +
									"ProI.productuser, " +
									"ProI.validity_from," +
									"ProI.validity_to ," +
									"reg.country " +
									"from client_info as clientinfo " +
									" inner join product_intrested as ProI  on ProI.accountid=clientinfo.accountid and ProI.uuid=clientinfo.uid and ProI.confirm_key=clientinfo.confim_key  " +
									" inner join registation_sinup as reg  on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key ";
				Query query =session.createSQLQuery(listidQuery);
				existList=query.list();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		 finally {session.flush();session.close();}
		 return existList;
	}
	
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls) {
		    final Log log = LogFactory.getLog(cCls.getName());
		    List Servicesobjects = null;
            Criteria criteria=null;
            try {
            Session session=HibernateSessionFactory.getSession();
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
    	  criteria.add(Restrictions.eq("over2CloudNoification","Y"));
    	  criteria.setFirstResult(to);
    	  criteria.setFirstResult(from);
    	  Servicesobjects = criteria.list();
    	  }
    	 catch(Exception e) {log.error("@ERP::>> ActionDAO::AbstractCommonOperation && Method:ServicesviewData()Class Dyanmic:::"+cCls+":::",e);}
    	 finally{try {HibernateSessionFactory.closeSession();}catch (Exception e2){log.error("@ERP::>> ActionDAO::AssetsDaoImpl && Method_Finally_Block:ServicesviewData()"+cCls+":::",e2);}}
          return Servicesobjects;
        }  
	

}
