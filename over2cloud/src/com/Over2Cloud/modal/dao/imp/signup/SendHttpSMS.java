package com.Over2Cloud.modal.dao.imp.signup;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class SendHttpSMS {

	public static boolean sendSMS(String Mobile, String Text) {
		
		boolean returnFlag = false;
		try {
			
			String URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=vlpcsf&password=vlpcsf&clientid=o2cdst1&to=";
			 StringBuffer url = new StringBuffer(URL);
			    String encodedMsg = URLEncoder.encode(Text);
				url.append(Mobile);
				url.append("&text=");
				url.append(encodedMsg);
				
				if (url != null) {
					try {
						 java.net.URL codedURL = new java.net.URL(url.toString());
						 HttpURLConnection HURLC =
						 (HttpURLConnection)codedURL.openConnection();
						 HURLC.connect();
						 int revertCode= HURLC.getResponseCode();
						 HURLC.disconnect();
						  url=null; 
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			
				returnFlag = true;
				
			
		}catch(Exception E) {
			returnFlag = false;
			E.printStackTrace();
		}
		return returnFlag;
	}

}
