package com.Over2Cloud.modal.dao.imp.Setting;

import hibernate.common.HibernateSessionFactory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.Over2Cloud.modal.db.Setting.ChunkClientDb;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.Over2Cloud.modal.db.signup.ClientUserAccount;

public class SettingDAO 
{
	
	static final Log log = LogFactory.getLog(SettingDAO.class);
	 @SuppressWarnings("unchecked")
	public boolean ChektheSMTPDetails()
	 {
			Session session = null;
			Smtp nsbC = null;
			List<Smtp> tempList = null;
			try {
						session = HibernateSessionFactory.getSession();
						Transaction tx = session.beginTransaction();
						Criteria criteria = session.createCriteria(Smtp.class);
						criteria.addOrder(Order.asc("id"));
						tempList = criteria.list();
						tx.commit();
			      } catch (Exception e) {
				        e.getMessage();
			   } finally {
				session.flush();
				session.close();
			   }
			if (tempList != null && tempList.size() > 0) {
				nsbC = tempList.get(0);
				return true;
			} else {
				nsbC = new Smtp();
				return false;
				
			}
    }
	 public Smtp objectSMTPDetails()
	 {
			Session session = null;
			Smtp nsbC = null;
			List<Smtp> tempList = null;
			try {
						session = HibernateSessionFactory.getSession();
						Transaction tx = session.beginTransaction();
						Criteria criteria = session.createCriteria(Smtp.class);
						criteria.addOrder(Order.asc("id"));
						tempList = criteria.list();
						tx.commit();
			      } catch (Exception e) {
				        e.getMessage();
			   } finally {
				session.flush();
				session.close();
			   }
			if (tempList != null && tempList.size() > 0) {
				nsbC = tempList.get(0);
				
			} else {
				nsbC = new Smtp();
				
				
			}
			return nsbC;
}
	 
	 
	 
	public ChunkClientDb GettingDynamicConnection(int id)
	{
		Session session = null;
		ChunkClientDb nsbC = null;
		List<ChunkClientDb> tempList = null;
		try {
			session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(ChunkClientDb.class);
			criteria.add(Restrictions.eq("id",id));
			tempList = criteria.list();
			tx.commit();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			session.flush();
			session.close();
		}
		if (tempList != null && tempList.size() > 0) {
			nsbC = tempList.get(0);
		} else {
			nsbC = new ChunkClientDb();
		}
		return nsbC;
	
		
	}

}
