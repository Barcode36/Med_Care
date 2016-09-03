package com.Over2Cloud.ctrl.feedback.service;

/** 
 * @author Avinash 
 * Created On : 08 Feb 2014 
 *  
 * File will Pickup all records discharged patient information from HIS Database 
 */ 
import hibernate.common.HibernateSessionFactory;
import hibernate.common.HisHibernateSessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.InstantCommunication.ConnectionHelper;
 
public class PatientDischargeIntegration implements Runnable{
private static final Log log = LogFactory.getLog(PatientDischargeIntegration.class); 
    
    SessionFactory sessionFacMysql = null; 
    Session sessionHis = null;  
    private PatientDischargeIntegrationHelper CDIH =  new PatientDischargeIntegrationHelper(); 
   
    /** 
     * Constructor will create session  
     */ 
    
    public PatientDischargeIntegration()
    {
    //Getting Hibernate Session for MYSQL... 
    	sessionFacMysql = new ConnectionHelper().getSessionFactory("IN-1"); 
        //Getting Hibernate Session for HIS...  
        sessionHis = HisHibernateSessionFactory.getSession();
    }
public void run() {
	System.out.println("My SQL Session Factory is as "+sessionFacMysql);
	CDIH.getDischargePatDetail(log, sessionFacMysql, sessionHis);
	
}
/** 
     * @category :: Close Hibernate Session At the time of Thread Close. 
     *  
     */ 
    @SuppressWarnings("unused") 
    private void end() { 
        sessionHis.close(); 
        sessionFacMysql.close(); 
    } 
public static void main(String[] args) {
try{
Thread patDis = new Thread(new PatientDischargeIntegration(), "PDI");
patDis.start();
}catch(Exception Ex){
	Ex.printStackTrace();
}

}
}