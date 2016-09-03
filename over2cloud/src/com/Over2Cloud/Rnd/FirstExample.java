package com.Over2Cloud.Rnd;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
public class FirstExample {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://115.112.184.21:3306/2_clouddb";

   //  Database credentials
   static final String USER = "dreamsol";
   static final String PASS = "dreamsol";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
	  
	  Date dateOne = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(dateOne);
		System.out.print("modifiedDate: " + modifiedDate);
      sql = "select id ,mobile_no, msg_text, module_name, insert_date, insert_time from instant_msg where flag='0' and insert_date<='"+modifiedDate+"'";
	  System.out.print(" " + sql);
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
    	  String id  = rs.getString("id");
         String mobno = rs.getString("mobile_no");
         String msg_text = rs.getString("msg_text");
         String module = rs.getString("module_name");
		 String date = rs.getString("insert_date");
         String time = rs.getString("insert_time");

         //Display values
         System.out.print("ID: " + id);
         System.out.print(", mobno: " + mobno);
         System.out.print(", msg_text: " + msg_text);
         System.out.println(", module: " + module);
		 System.out.println(", date: " + date);
		 System.out.println(", time: " + time);
		 sendSMS(mobno, msg_text,module,id, stmt);
		 
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
   
   
  /* private  void sendSMS(String msisdn,String fmsg,String module, String id)
	 {
		try {
			  
		      
		      String clientid="dmudst04";
		      String  senderid="DTSHDM";
				 //http://23.254.128.22:9080/urldreamclient/dreamurl?userName=DrmsolHdm&password=DrmsolHdm&clientid=dmudst04&to=9250400311&text=00:20:00%20Transactional%20Route%20is%20well...&Senderid=DTSHDM
		      
		      if((msisdn != null &&  !msisdn.equals("")) && msisdn.length() == 10 && ((msisdn.startsWith("9") || msisdn.startsWith("8") || msisdn.startsWith("7")) && fmsg!= null && !fmsg.equals("")))
		        {
					try {
						  String URL = null;
				          StringBuffer url = new StringBuffer();
				          URL = "http://23.254.128.22:9080/urldreamclient/dreamurl?userName=DrmsolHdm&password=DrmsolHdm";
				           // URL = "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=demourl&password=demourl";
				          URL=URL+"&clientid="+clientid+"&to=";
			    	   
				          url.append(URL);
	   				      url.append(msisdn);
	   				      url.append("&text=");
	   				      url.append(encodedMsg);
	   				      url.append("&Senderid=");
	   				      url.append(senderid);
						  
						  URL codedURL = new URL(url.toString());
						  HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
						  HURLC.connect();
						  System.out.println("URL>>  "+url);
						  int revertCode= HURLC.getResponseCode();
						  HURLC.disconnect();
						  if (revertCode==1201 || revertCode==1202 || revertCode==1204)
						   {
							  System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+revertCode); 
   						     url = null; 
						   }
						  else if(revertCode==200 || revertCode==505 || revertCode==503){
							    
							     System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
	  						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
	  						     //HUH.updateData(updateQry, connection);
		                         url = null;
						  }
						  else
						   {
				             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
  						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
  						    // HUH.updateData(updateQry, connection);
	                         url = null;
						   }
					}
					catch (Exception E)
					  {
						try {
	   						 String URL = null;
	    				     StringBuffer url = new StringBuffer();
	    				     //URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
	    				     URL = "http://94.102.149.210:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
				             URL=URL+"&clientid="+clientid+"&to=";
			    	   
				             url.append(URL);
	   				         url.append(msisdn);
	   				         url.append("&text=");
	   				         url.append(encodedMsg);
		   				     url.append("&Senderid=");
		   				     url.append(senderid);
						  
					         URL codedURL = new URL(url.toString());
					         HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
					         HURLC.connect();
					         int revertCode= HURLC.getResponseCode();
					         HURLC.disconnect();
					         if (revertCode==1201 || revertCode==1202 || revertCode==1204)
							   {
					               System.out.println("SMS Send Successfully From Second Route and Get Revert Code :"+revertCode); 
	    						   url = null; 
							   }
					         else if (revertCode==200) {
								    System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
									updateQry="update instant_msg set flag='ui',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
									//HUH.updateData(updateQry, connection);
			                        url = null;
							   }
							  else if (revertCode==505) {
								    System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
									updateQry="update instant_msg set flag='vi',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
									//HUH.updateData(updateQry, connection);
			                        url = null;
							   }
							  else
							   {
					             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
	   						     updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
	   						     //HUH.updateData(updateQry, connection);
		                         url = null;
							   }
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					 }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}

private static void sendSMS(String msisdn,String fmsg,String module, String id, Statement stmt)
{
	// TODO Auto-generated method stub

	try {
		  
	      String updateQry="";
	      String clientid="dmudst04";
	      String  senderid="DTSHDM";
	      String encodedMsg = URLEncoder.encode(fmsg);
			 //http://23.254.128.22:9080/urldreamclient/dreamurl?userName=DrmsolHdm&password=DrmsolHdm&clientid=dmudst04&to=9250400311&text=00:20:00%20Transactional%20Route%20is%20well...&Senderid=DTSHDM
	      
	      if((msisdn != null &&  !msisdn.equals("")) && msisdn.length() == 10 && ((msisdn.startsWith("9") || msisdn.startsWith("8") || msisdn.startsWith("7")) && fmsg!= null && !fmsg.equals("")))
	        {
				try {
					  String URL = null;
			          StringBuffer url = new StringBuffer();
			          URL = "http://23.254.128.22:9080/urldreamclient/dreamurl?userName=DrmsolHdm&password=DrmsolHdm";
			           // URL = "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=demourl&password=demourl";
			          URL=URL+"&clientid="+clientid+"&to=";
		    	   
			          url.append(URL);
   				      url.append(msisdn);
   				      url.append("&text=");
   				      url.append(encodedMsg);
   				      url.append("&Senderid=");
   				      url.append(senderid);
					  
					  URL codedURL = new URL(url.toString());
					  HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
					  HURLC.connect();
					  System.out.println("URL>>  "+url);
					  int revertCode= HURLC.getResponseCode();
					  HURLC.disconnect();
					  if (revertCode==1201 || revertCode==1202 || revertCode==1204)
					   {
						  System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+revertCode); 
						     url = null; 
					   }
					  else if(revertCode==200 || revertCode==505 || revertCode==503){
						    
						     System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
  						     //updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
  						     //HUH.updateData(updateQry, connection);
	                         url = null;
					  }
					  else
					   {
			             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
						    // updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
						    // HUH.updateData(updateQry, connection);
                         url = null;
					   }
				}
				catch (Exception E)
				  {
					try {
   						 String URL = null;
    				     StringBuffer url = new StringBuffer();
    				     //URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
    				     URL = "http://94.102.149.210:6060/urldreamclient/dreamurl?userName=demourl&password=demourl";
			             URL=URL+"&clientid="+clientid+"&to=";
		    	   
			             url.append(URL);
   				         url.append(msisdn);
   				         url.append("&text=");
   				         url.append(encodedMsg);
	   				     url.append("&Senderid=");
	   				     url.append(senderid);
					  
				         URL codedURL = new URL(url.toString());
				         HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
				         HURLC.connect();
				         int revertCode= HURLC.getResponseCode();
				         HURLC.disconnect();
				         if (revertCode==1201 || revertCode==1202 || revertCode==1204)
						   {
				               System.out.println("SMS Send Successfully From Second Route and Get Revert Code :"+revertCode); 
    						   url = null; 
						   }
				         else if (revertCode==200) {
							    System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
								//updateQry="update instant_msg set flag='ui',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								//HUH.updateData(updateQry, connection);
		                        url = null;
						   }
						  else if (revertCode==505) {
							    System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
								//updateQry="update instant_msg set flag='vi',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								//HUH.updateData(updateQry, connection);
		                        url = null;
						   }
						  else
						   {
				             System.out.println("SMS Not Send Successfully From Second Route and Get Revert Code :"+revertCode); 
   						    // updateQry="update instant_msg set flag='err',status='Pending',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
   						     //HUH.updateData(updateQry, connection);
	                         url = null;
						   }
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				 }
	    }
	} catch (Exception e) {
		e.printStackTrace();
	}

	
}


}//end FirstExample
