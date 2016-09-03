package com.Over2Cloud.Rnd;

import javax.persistence.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;

public class DepartmentData {

	public static void main(String[] args) {
		
		DepartmentData dd=new DepartmentData();
		dd.getMappedNames("3","1");
		
	}
	
	public List<String> getMappedNames(String orgLevel,String mappedOrgId)
	{
		List<String> dataList=new ArrayList<String>();
		Session session=null;
		List qryList=null;
		try
		{
			AllConnection Conn1=new ConnectionFactory("37_clouddb","localhost","dreamsol","dreamsol","3306");
	        AllConnectionEntry Ob=Conn1.GetAllCollectionwithArg();
	        SessionFactory chunkSession=Ob.getSession();
	        session=chunkSession.openSession();
	        StringBuffer buffer=new StringBuffer();
	        String tableName=null;
	        String columnName=null;
	        if(orgLevel.equalsIgnoreCase("1"))
	        {
	        	tableName="organization_level1";
	        	columnName="companyRegCity";
	        }
	        else if(orgLevel.equalsIgnoreCase("2"))
	        {
	        	tableName="organization_level2";
	        	columnName="locCompanyRegCity";
	        }
	        else if(orgLevel.equalsIgnoreCase("3"))
	        {
	        	tableName="organization_level3";
	        	columnName="branchCity";
	        }
	        else if(orgLevel.equalsIgnoreCase("4"))
	        {
	        	tableName="organization_level4";
	        	columnName="level4City";
	        }
	        else if(orgLevel.equalsIgnoreCase("5"))
	        {
	        	tableName="organization_level5";
	        	columnName="level5City";
	        }
	        
	        
	        
	        
	        buffer.append("select lvl."+columnName+" from "+tableName+" as lvl");
	       
	        System.out.println("Query  is as <><><><><><><><><><><><>><<>"+buffer.toString());
	        qryList=session.createSQLQuery(buffer.toString()).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		if(qryList!=null)
		{
			for (Iterator iterator = qryList.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				
				System.out.println("Data Retrieved is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+object);
				
			}
		}
		
		return dataList;
	}
}
