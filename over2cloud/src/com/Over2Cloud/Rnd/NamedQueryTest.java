package com.Over2Cloud.Rnd;

import hibernate.common.HibernateSessionFactory;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NamedQueryTest {

	public static void main(String[] args) {
		
		Session hSession = HibernateSessionFactory.getSession();
		Transaction hTransaction= hSession.beginTransaction();
		Query hQueryString=hSession.getNamedQuery("pic");
		List data=hQueryString.list();
		hTransaction.commit();
		hSession.close();
		System.out.println(data.size());
	}
}
