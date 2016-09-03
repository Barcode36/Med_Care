package com.Over2Cloud.Rnd;

import java.net.HttpURLConnection;
import java.net.URL;


public class SMSTest {
	public boolean sendSMS(String msg,String mobNo)
	{
System.out.println();		
		StringBuffer url =new StringBuffer("http://64.120.220.52:9080/urldreamclient/dreamurl?userName=f111&password=b111&clientid=Tesdst11&to="+mobNo+"&text="+msg);
		boolean flag=false;
		if (url != null)
		   {
			try
			{
				  URL codedURL = new URL(url.toString());
				  HttpURLConnection HURLC21 =(HttpURLConnection)codedURL.openConnection();
				  HURLC21.connect();
				  int revertCode= HURLC21.getResponseCode();
				//  System.out.println("Revert:"+HURLC.getResponseMessage());
				  HURLC21.disconnect();
				 // System.out.println("Revertcode::"+revertCode);
				//  System.out.println("<><><><><><>URL"+url);
				System.out.println("SMS Send Successfully from first route and get revert code :"+revertCode+"URL <><><><><>"+url.toString());
				flag=true;
			} 
			catch (Exception E)
			{
				flag=false;
				E.printStackTrace();
			}
		   }
			return flag;
	}
public static void main(String arsh[])
{
	SMSTest fAct=new SMSTest();
	System.out.println("SMS Sent <>>>>>>>>>"+fAct.sendSMS("Hi, thanks for your information. We have updated the same. Wish you all the best. Regards.","9250400311"));
}
}
