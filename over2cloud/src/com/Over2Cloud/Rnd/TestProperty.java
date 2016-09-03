package com.Over2Cloud.Rnd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperty
{
	public static void main(String[] args) 
	{
		try
		{
			TestProperty test=new TestProperty();
			Properties configProp=new Properties();
            String ipsAdd=null;
            InputStream in = test.getClass().getResourceAsStream("/com/Over2Cloud/common/mail/mailIps.properties");
       	 	configProp.load(in);
       	 	ipsAdd=configProp.getProperty("ipAdd");
       	 	
       	 	String arr[]=ipsAdd.split("#");
       	 	for (int i = 0; i < arr.length; i++)
       	 	{
       	 	System.out.println(arr[i]);
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
