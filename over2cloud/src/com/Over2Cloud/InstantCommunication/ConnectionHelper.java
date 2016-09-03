package com.Over2Cloud.InstantCommunication;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection4Servicefile;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class ConnectionHelper {
	@SuppressWarnings("unchecked")
	public SessionFactory getSessionFactory (String clientid)
	 {
		SessionFactory connection = null;
		
        LoginImp ob1=new LoginImp();
        String Accountinfo[]=clientid.split("-");
        // This If Block Check The Client Id Is Exist Or Not and Not Blocked
        if(ob1.isExit(Accountinfo[1].toString(), Accountinfo[0].toString()) && ob1.isClientBlock(Accountinfo[1].toString(), Accountinfo[0].toString()))
           {
		     try
	    		{  
		    	   // get the chunk space detail for particular client
		    	   List ChunkSpace=ob1.chunkDomainName(Accountinfo[1].toString(), Accountinfo[0].toString());
		    	   if(ChunkSpace != null && !ChunkSpace.isEmpty() &&  ChunkSpace.size()>0)
		    	      {
		    		    for (Iterator iterator = ChunkSpace.iterator(); iterator.hasNext();)
		    		       {
		    			     Object[] objectCol = (Object[]) iterator.next();
   		    			     List<String> colmName=new  ArrayList<String>();
   					         colmName.add("Souce_ip_Domain_address");
   					         colmName.add("id");
   					         Map<String, String> wherClause=new HashMap<String, String>();
   					         wherClause.put("accountid",Accountinfo[1].toString() );
   					         wherClause.put("country_iso_code",Accountinfo[0].toString());
   					        
   					         //AllConnection Conn1=new ConnectionFactory(dbbname1,ipAddress1,username1,paassword1,port1);
		    			     //AllConnectionEntry Ob=Conn1.GetAllCollectionwithArg();
   					         SessionFactory chunkSession= HibernateSessionFactory.getSessionFactory();
		    			     // getting the Client Space Detail
   					         List ClientSpace=new TableInfo().viewAllDataEitherSelectOrAll(objectCol[0].toString().trim()+"_"+objectCol[1].toString().trim()+"_space_configuration", colmName,wherClause,chunkSession);
   					         if(ClientSpace != null && ClientSpace.size()>0)
   					           {
   					        	 for (Iterator iterator1 = ClientSpace.iterator(); iterator1.hasNext();)
   					        	 {
   					        		 String dbbname=null,ipAddress=null,username=null,paassword=null,port=null;
   					        		 Object[] objectCol1 = (Object[]) iterator1.next();
   					        		 List singleSpceServer=ob1.GetClientspace(objectCol1[0].toString().trim());
   					        		 
   					        		 for (Object c : singleSpceServer) {
   					     				Object[] dataC = (Object[]) c;
   					     				dbbname=Accountinfo[1].toString().trim()+"_clouddb";
   					     				ipAddress=dataC[0].toString();
   					     				username=dataC[2].toString();
   					     				paassword=dataC[3].toString();
   					     				port=dataC[1].toString();
   					        		 }
   					        		  AllConnection Conn=new ConnectionFactory(dbbname,ipAddress,username,paassword,port);
   			    			          AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
   			    			          connection=Ob1.getSession();
   					        	 }
   					        }
		    		   }
		    	  }
	      }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		 }
       }
	return connection;
  }
	public Session getSession(String clientid)
	 {
	   Session connection = null;
       LoginImp ob1=new LoginImp();
       String Accountinfo[]=clientid.split("-");
       // This If Block Check The Client Id Is Exist Or Not and Not Blocked
       if(ob1.isExit(Accountinfo[1].toString(), Accountinfo[0].toString()) && ob1.isClientBlock(Accountinfo[1].toString(), Accountinfo[0].toString()))
          {
		     try
	    		{  
		    	   // get the chunk space detail for particular client
		    	   List ChunkSpace=ob1.chunkDomainName(Accountinfo[1].toString(), Accountinfo[0].toString());
		    	   if(ChunkSpace != null && !ChunkSpace.isEmpty() &&  ChunkSpace.size()>0)
		    	      {
		    		    for (Iterator iterator = ChunkSpace.iterator(); iterator.hasNext();)
		    		       {
		    			     Object[] objectCol = (Object[]) iterator.next();
  		    			     List<String> colmName=new  ArrayList<String>();
  					         colmName.add("Souce_ip_Domain_address");
  					         colmName.add("id");
  					         Map<String, String> wherClause=new HashMap<String, String>();
  					         wherClause.put("accountid",Accountinfo[1].toString() );
  					         wherClause.put("country_iso_code",Accountinfo[0].toString());
  					        
  					         //AllConnection Conn1=new ConnectionFactory(dbbname1,ipAddress1,username1,paassword1,port1);
		    			     //AllConnectionEntry Ob=Conn1.GetAllCollectionwithArg();
  					         SessionFactory chunkSession= HibernateSessionFactory.getSessionFactory();
		    			     // getting the Client Space Detail
  					         List ClientSpace=new TableInfo().viewAllDataEitherSelectOrAll(objectCol[0].toString().trim()+"_"+objectCol[1].toString().trim()+"_space_configuration", colmName,wherClause,chunkSession);
  					         if(ClientSpace != null && ClientSpace.size()>0)
  					           {
  					        	 for (Iterator iterator1 = ClientSpace.iterator(); iterator1.hasNext();)
  					        	 {
  					        		 String dbbname=null,ipAddress=null,username=null,paassword=null,port=null;
  					        		 Object[] objectCol1 = (Object[]) iterator1.next();
  					        		 List singleSpceServer=ob1.GetClientspace(objectCol1[0].toString().trim());
  					        		 
  					        		 for (Object c : singleSpceServer) {
  					     				Object[] dataC = (Object[]) c;
  					     				dbbname=Accountinfo[1].toString().trim()+"_clouddb";
  					     				ipAddress=dataC[0].toString();
  					     				username=dataC[2].toString();
  					     				paassword=dataC[3].toString();
  					     				port=dataC[1].toString();
  					        		 }
  					        		 connection=DBDynamicConnection4Servicefile.getSession(dbbname,ipAddress,username,paassword,port);
  					        	 }
  					        }
		    		   }
		    	  }
	      }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		 }
      }
	return connection;
 }
}
