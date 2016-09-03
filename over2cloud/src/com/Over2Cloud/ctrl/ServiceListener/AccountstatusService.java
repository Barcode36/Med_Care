package com.Over2Cloud.ctrl.ServiceListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccountstatusService {
	// Saved response.
    private static java.util.Map<String,java.util.List<String>> responseHeader = null;
    private static java.net.URL responseURL = null;
    private static int responseCode = -1;
    private static String MIMEtype  = null;
    private static String charset   = null;
    private Object content   = null;
	// this method used for all type of method to send
	public static String getAccountDetails(String clientid){
		String decodedString = null;
		try {
		       if(clientid!=null && !clientid.equalsIgnoreCase(""))
		        {
		    	   String URL = null;
		    	   try {
				          StringBuffer url = new StringBuffer();
						//  URL = "http://115.112.185.85:6060/urldreamclient/accounturl?clientId="+clientid;
						  URL = "http://23.254.128.22:9080/urldreamclient/accounturl?clientId="+clientid;
				          url.append(URL);
						  URL codedURL = new URL(url.toString());
						  HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
						  HURLC.connect();
						  BufferedReader in = new BufferedReader(new InputStreamReader(HURLC.getInputStream()));
                            decodedString = in.readLine(); 
						  HURLC.disconnect();
						}
					 catch (Exception E)
					  {
						URL = null;
						E.printStackTrace();
					 }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodedString;
	}	

}
