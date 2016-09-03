package com.Over2Cloud.Rnd;

import hibernate.common.HibernateSessionFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Session;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;

public class TestUser {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		System.out.println("date===::"+DateUtil.getNextDateAfter(Integer.parseInt("-2")));
	}
}
