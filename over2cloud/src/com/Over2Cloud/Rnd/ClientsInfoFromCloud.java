package com.Over2Cloud.Rnd;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class ClientsInfoFromCloud 
{
	private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	public static void main(String[] args) 
	{
		
	}
	
	public static List<String> getAllClientsList()
	{
		List<String> clientsList=new ArrayList<String>();
		List clients=new ArrayList();
		Session session=null;
		try
		{
			session=HibernateSessionFactory.getSession();
			Query qry=session.createSQLQuery("select id,countryid from client_info");
			clientsList=qry.list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(clients!=null && clients.size()>0)
		{
			for (Iterator iterator = clients.iterator(); iterator.hasNext();) 
			{
				Object[] obj=(Object[])iterator.next();
				clientsList.add(""+obj[1]+obj[0]+"");
			}
		}
		return clientsList;
	}
}
