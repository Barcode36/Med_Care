package com.Over2Cloud.ctrl.Signup;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

public class SignUpHelperForDemo
{
	public String getMaxIdRegSignUpForDemoUser()
	{
		String regSignId=null;
		Session session=null;
		List data=new ArrayList();
		try
		{
			session=HibernateSessionFactory.getSession();
			data=session.createSQLQuery("select max(id) from registation_sinup").list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		if(data.size()>0 && data.get(0)!=null && !data.get(0).toString().equalsIgnoreCase(""))
		{
			regSignId=data.get(0).toString();
		}
		return regSignId;
	}
}
