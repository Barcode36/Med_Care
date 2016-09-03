package com.Over2Cloud.ctrl.Signup;

import java.util.List;

import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.Over2Cloud.modal.db.signup.ClientUserAccount;
import com.Over2Cloud.modal.db.signup.Registation;
import com.opensymphony.xwork2.ActionSupport;

public class PasswordVarificationLink extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pp;
	private String uacn;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public String getPp() {
		return pp;
	}
	public void setPp(String pp) {
		this.pp = pp;
	}
	public String getUacn() {
		return uacn;
	}
	public void setUacn(String uacn) {
		this.uacn = uacn;
	}
	public String LinkpasswordVarification()
	{
		try{
		    @SuppressWarnings("unused")
			String Accountinfo[]=(Cryptography.decrypt(getUacn(),DES_ENCRYPTION_KEY).toString()).split("-");
		    ClientUserAccount CUA=new signupImp().getClientUserAccountObject(Integer.parseInt(Accountinfo[1].trim().toString()),Accountinfo[0].trim().toString());
		    if(CUA.getIsconfirmlink().equals("Y"))
			{
				 CommonforClass eventDao=new CommanOper();
				    if(CUA.getId()!=0){
				    	      if(CUA.getRequestPwd().equals(getPp()))
				    	      {
				    	    	  CUA.setIsconfirmlink("N");
				    	    	  CUA.setPwd(getPp());
				    	    	  CUA.setRequestPwd("NA");
				    	    	  if(eventDao.UpdateDetails(CUA)){
				    	    		  return SUCCESS;}
				    	    	  else{return ERROR;}
				    	      }
				    	      else
				    	      {
				    	    	return ERROR;  
				    	      }
				    }else{return ERROR;}
			}
			else{return ERROR;}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return SUCCESS;
	}
	
	
	
}
