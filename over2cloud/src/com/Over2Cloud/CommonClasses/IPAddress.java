package com.Over2Cloud.CommonClasses;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class IPAddress {
	private Properties configProp = new Properties();
	public String getIPAddress()
	{
		String ip=null;
		try
		{
			String iPAdrs=InetAddress.getLocalHost().getHostAddress();
			HttpServletRequest req = ServletActionContext.getRequest();
			int portNo=req.getServerPort();
			ip=iPAdrs+":"+portNo;
			
			/*InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/CommonClasses/ipAddress.properties");
			configProp.load(in);
			ip=configProp.getProperty("serverIp");*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ip;
	}
	
	public String getDBDetails()
	{
		StringBuilder dbDetals=new StringBuilder();
		try
		{
			InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/CommonClasses/ipAddress.properties");
			configProp.load(in);
			String hostName=configProp.getProperty("hostName");
			String userName=configProp.getProperty("userName");
			String password=configProp.getProperty("password");
			String port=configProp.getProperty("port");
			dbDetals.append(hostName+","+userName+","+password+","+port);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbDetals.toString();
	}
}
