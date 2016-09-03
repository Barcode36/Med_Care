package com.Over2Cloud.procegure.ProcegureCall;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Over2Cloud.CommonClasses.AbstractImpliment;



public class NativenameQueryDao extends AbstractImpliment<NativenameQueries>{
	
	private static final Log log = LogFactory.getLog(NativenameQueryDao.class);
	
	private String user = null;
	private String superUser = null;
	public NativenameQueryDao(String user,String superUser)
	 {
		this.user = user;
		this.superUser = superUser;
	
	}
	public NativenameQueryDao()
	 {

	}
}
