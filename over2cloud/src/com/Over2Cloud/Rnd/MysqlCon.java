package com.Over2Cloud.Rnd;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MysqlCon{
public static void main(String args[]){  
	try{  
		
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		//System.out.print("modifiedDate: " + modifiedDate);
		
		String sdf = new SimpleDateFormat("HH:mm:ss").format(date);
		
		System.out.print("sdf: " + sdf);
	Class.forName("com.mysql.jdbc.Driver");  
	  
	Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://115.112.184.21:3306/2_clouddb","dreamsol","dreamsol");  
	  
	//here sonoo is database name, root is username and password  
	  
	Statement stmt=(Statement) con.createStatement();  
	  
	ResultSet rs=stmt.executeQuery("select * from contact_type");  
	  
	while(rs.next())  
	System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
	  
	con.close();  
	  
	}catch(Exception e){ System.out.println(e);}  
	  
	}  
	} 
