package com.Over2Cloud.Rnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDb 
{
	static
	{
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getCon()
	{

		Connection con = null;
		
		//Initialing Variables for database Connection
		
		String dbName = "Rgcil_26apr14";
		String ipAdrs = "10.1.1.9:5434";
		String user = "rgci";
		String pwd = "srishti";
	
		
		
		//String url = "jdbc:postgresql://" + ipAdrs + "/" + dbName + "?user=" + user + "&password=" + pwd;
		
		//Creating Connection 
		try {
			//System.out.println("Requesting For Connection...Timon"+DateUtil.getCurrentTimeStamp());
			
			
			con = DriverManager.getConnection("jdbc:postgresql://10.1.1.9:4500/Rgci_26apr2014",user, pwd);
			
			//System.out.println("Connection Stablish...Timon"+DateUtil.getCurrentTimeStamp());

		} catch (SQLException sqle)
		{
			System.out.println(sqle);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		
		}

		return con;
	}
	
	public static void main(String args[])
	{
		TestDb db=new TestDb();
		System.out.println("Connection is as  :::::"+TestDb.getCon());
	}
	
}
