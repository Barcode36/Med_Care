package com.Over2Cloud.common.mail;
/**
 * 
 * @author Khushal Singh
 */

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator{

	String user,password;
	public MailAuthenticator(String user,String password) {
	this.user=user;
	this.password=password; 
	}
	
	public PasswordAuthentication getPasswordAuthentication()
	{
		
		return new PasswordAuthentication(user,password);
		
	}
	
}
