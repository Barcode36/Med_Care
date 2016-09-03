package com.Over2Cloud.ConnectionFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
public interface AllConnectionEntry {
	
	public SessionFactory getSession();
}
