package com.Over2Cloud.ctrl.ServiceListener;

/*
 * Created on Apr 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.sun.mail.smtp.SMTPSendFailedException;


public class Mailsender 
  {
	
	public boolean confMail(String urMailServer, String urPort, String from,String pass,
			String toRecipient, String subj, String mailText) {
		boolean status = false;
		
		System.out.println("inside without attachment");
		String statusCode="0";
		final String user = from;
        final String password = pass;
               
				String toDay = DateUtil.getCurrentDateUSFormat();
				String time= DateUtil.getCurrentTimewithSeconds();
				
				try{
					//Security.addProvider(new com.sun.net.ssl.internet.ssl.provider());
					if(!urPort.equals("")&& urPort.equalsIgnoreCase("25"))
					{	
						
					Properties props = new Properties();
					props.setProperty("mail.transport.protocol", "smtp");
					props.setProperty("mail.host",urMailServer);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port",urPort);
				    props.put("mail.smtp.socketFactory.port",urPort);
					//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.socketFactory.fallback", "false");
					props.setProperty("mail.smtp.quitwait","false");		
					
					
					    Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
						{ 					
						protected PasswordAuthentication getPasswordAuthentication() 
						{						
							return new PasswordAuthentication(user,password);					
						}				
						});
							
					Transport transport = ses.getTransport();	
				
				
					String recipients[]=toRecipient.split(",");
				//System.out.println(ses.getProperties());
				MimeMessage msg = new MimeMessage(ses);
				msg.setSubject(subj);		
				msg.setFrom(new InternetAddress(from));
			  //  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipient));
			    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
			        for (int i = 0; i < recipients.length; i++)
			    {
			        addressTo[i] = new InternetAddress(recipients[i]);
			    }
			    msg.setRecipients(Message.RecipientType.TO, addressTo);	
				msg.setSentDate(new Date());
			
				msg.setContent(mailText.toString(),"text/HTML");
				//ses.setDebug(true); 
		    	transport.connect();		
				Transport.send(msg);		
				transport.close();
				status=true;
				statusCode="1";
				}
				else{
					
				 	
					Properties props = new Properties();
					//Security.addProvider(new com.sun.net.ssl.internet.ssl.provider());
					props.setProperty("mail.transport.protocol", "smtp");
					props.setProperty("mail.host",urMailServer);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port",urPort);
				    props.put("mail.smtp.socketFactory.port",urPort);
					props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.socketFactory.fallback", "false");
					props.setProperty("mail.smtp.quitwait","false");		
					 Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
								{ 					
								protected PasswordAuthentication getPasswordAuthentication() 
								{						
									return new PasswordAuthentication(user,password);					
								}				
								});
									
							Transport transport = ses.getTransport();	
						
						
							String recipients[]=toRecipient.split(",");
						//System.out.println(ses.getProperties());
						MimeMessage msg = new MimeMessage(ses);
					
						msg.setSubject(subj);		
									
						msg.setFrom(new InternetAddress(from));
					  //  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipient));
					    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
					        for (int i = 0; i < recipients.length; i++)
					    {
					        addressTo[i] = new InternetAddress(recipients[i]);
					    }
					    msg.setRecipients(Message.RecipientType.TO, addressTo);	
						msg.setSentDate(new Date());
					
						msg.setContent(mailText.toString(),"text/HTML");
						//System.out.println("****** THIS IS TEST LINE *********"+msg);		
						//ses.setDebug(true); 
				    	transport.connect();		
						Transport.send(msg);		
						transport.close();
						status=true;
						statusCode="1";
								
				}
					
					
				}catch(SMTPSendFailedException se)
				{
					se.printStackTrace();
					System.out.println("Server Requires Authentication");
					
				}
				catch(javax.mail.MessagingException me)
				{
					me.printStackTrace();
					
				}
				catch (Exception e) {
					
					e.printStackTrace();
				}
							
				return status;
			}
		
	public boolean confMailHTML(String urMailServer, String urPort, String from,String pass,
			String toRecipient, String subj, String mailText) {
		boolean status = false;
		String statusCode="0";
		final String user = from;
        final String password = pass;
               
				String toDay = DateUtil.getCurrentDateUSFormat();
				String time= DateUtil.getCurrentTimewithSeconds();
				
				try{
					//Security.addProvider(new com.sun.net.ssl.internet.ssl.provider());
					
					if(!urPort.equals("")&& urPort.equalsIgnoreCase("25"))
					{	
					Properties props = new Properties();
					props.setProperty("mail.transport.protocol", "smtp");
					props.setProperty("mail.host",urMailServer);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port",urPort);
				    props.put("mail.smtp.socketFactory.port",urPort);
					//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.socketFactory.fallback", "false");
					props.setProperty("mail.smtp.quitwait","false");		
					
					
					    Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
						{ 					
						protected PasswordAuthentication getPasswordAuthentication() 
						{						
							return new PasswordAuthentication(user,password);					
						}				
						});
							
					Transport transport = ses.getTransport();	
				
				
					String recipients[]=toRecipient.split(",");
				//System.out.println(ses.getProperties());
				MimeMessage msg = new MimeMessage(ses);
				msg.setSubject(subj);		
				msg.setFrom(new InternetAddress(from));
			  //  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipient));
			    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
			        for (int i = 0; i < recipients.length; i++)
			    {
			        addressTo[i] = new InternetAddress(recipients[i]);
			    }
			    msg.setRecipients(Message.RecipientType.TO, addressTo);	
				msg.setSentDate(new Date());
			
				msg.setContent(mailText.toString(),"text/html");
				//ses.setDebug(true); 
		    	transport.connect();		
				Transport.send(msg);		
				transport.close();
				status=true;
				statusCode="1";
				}
				else{
					
				 	
					Properties props = new Properties();
					//Security.addProvider(new com.sun.net.ssl.internet.ssl.provider());
					props.setProperty("mail.transport.protocol", "smtp");
					props.setProperty("mail.host",urMailServer);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port",urPort);
				    props.put("mail.smtp.socketFactory.port",urPort);
					props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.socketFactory.fallback", "false");
					props.setProperty("mail.smtp.quitwait","false");		
					 Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
								{ 					
								protected PasswordAuthentication getPasswordAuthentication() 
								{						
									return new PasswordAuthentication(user,password);					
								}				
								});
									
							Transport transport = ses.getTransport();	
						
						
							String recipients[]=toRecipient.split(",");
						//System.out.println(ses.getProperties());
						MimeMessage msg = new MimeMessage(ses);
					
						msg.setSubject(subj);		
									
						msg.setFrom(new InternetAddress(from));
					  //  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipient));
					    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
					        for (int i = 0; i < recipients.length; i++)
					    {
					        addressTo[i] = new InternetAddress(recipients[i]);
					    }
					    msg.setRecipients(Message.RecipientType.TO, addressTo);	
						msg.setSentDate(new Date());
					
						msg.setContent(mailText.toString(),"text/html");
						//System.out.println("****** THIS IS TEST LINE *********"+msg);		
						//ses.setDebug(true); 
				    	transport.connect();		
						Transport.send(msg);		
						transport.close();
						status=true;
						statusCode="1";
								
				}
					
					
				}catch(SMTPSendFailedException se)
				{
					se.printStackTrace();
					System.out.println("Server Requires Authentication");
					
				}
				catch(javax.mail.MessagingException me)
				{
					me.printStackTrace();
					
				}
				catch (Exception e) {
					
					e.printStackTrace();
				}
							
				return status;
			}
	public boolean confMailwithAttachment(String urMailServer, String urPort, String from,
			String pass, String toRecipient, String subj, String mailText, String filename) {
		
		System.out.println("inside attchment");
		
		
		boolean status = false;

		
		String statusCode = "0";

		final String user = from;
		final String password = pass;
		//******************************************************************************
		//this iF coding for message with attachment................................
		//******************************************************************************

		
			
			String toDay = DateUtil.getCurrentDateUSFormat();
			String time = DateUtil.getCurrentTimewithSeconds();
			try {
				//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 	
				if(!urPort.equals("")&& urPort.equalsIgnoreCase("25"))
				{	
					System.out.println("inside mail server");
				Properties props = new Properties();
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.host",urMailServer);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port",urPort);
			    props.put("mail.smtp.socketFactory.port",urPort);
				//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.quitwait","false");		
				
				
				    Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
					{ 					
					protected PasswordAuthentication getPasswordAuthentication() 
					{						
						return new PasswordAuthentication(user,password);					
					}				
					});
						
				Transport transport = ses.getTransport();	
			
				MimeMessage msg = new MimeMessage(ses);
				msg.setSubject(subj);

				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.TO, toRecipient);
				msg.setSentDate(new Date());
				msg.setSubject(subj);

				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(mailText);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename.substring(filename
						.lastIndexOf("\\") + 1));
				multipart.addBodyPart(messageBodyPart);

				msg.setContent(multipart);
				//   ses.setDebug(true); 

				transport.connect();
				Transport.send(msg);

				transport.close();

				statusCode = "1";
				status = true;
				}
				else{

					System.out.println("inside gmail");
				Properties props = new Properties();
				//Security.addProvider(new com.sun.net.ssl.internet.ssl.provider());
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.host",urMailServer);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port",urPort);
			    props.put("mail.smtp.socketFactory.port",urPort);
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.quitwait","false");		
				 Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
							{ 					
							protected PasswordAuthentication getPasswordAuthentication() 
							{						
								return new PasswordAuthentication(user,password);					
							}				
							});
								
						Transport transport = ses.getTransport();	
					
					
						@SuppressWarnings("unused")
						String recipients[]=toRecipient.split(",");

				MimeMessage msg = new MimeMessage(ses);

				msg.setSubject(subj);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.TO, toRecipient);
				msg.setSentDate(new Date());
				msg.setSubject(subj);

				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(mailText);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename.substring(filename
						.lastIndexOf("\\") + 1));
				multipart.addBodyPart(messageBodyPart);

				msg.setContent(multipart);
				//   ses.setDebug(true); 

				transport.connect();
				Transport.send(msg);

				transport.close();

				statusCode = "1";
				status = true;
				}
			} catch(SMTPSendFailedException se) {
				se.printStackTrace();
				System.out.println("Server Requires Authentication");

				statusCode = "2";
			} catch (javax.mail.MessagingException me) {
				me.printStackTrace();
				System.out.println("Could Not Connect to host server");
				statusCode = "3";
			} catch (Exception e) {

				e.printStackTrace();
			}
			

		return status;
	}
	public boolean confMailwithAttachmenttextbody(String urMailServer, String urPort, String from,
			String pass, String toRecipient, String subj, String mailText, String filename) {
		
		System.out.println("inside attchment");
		
		
		boolean status = false;

		
		String statusCode = "0";

		final String user = from;
		final String password = pass;
		//******************************************************************************
		//this iF coding for message with attachment................................
		//******************************************************************************

		
			
			String toDay = DateUtil.getCurrentDateUSFormat();
			String time = DateUtil.getCurrentTimewithSeconds();
			try {
				//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 	
				if(!urPort.equals("")&& urPort.equalsIgnoreCase("25"))
				{	
					System.out.println("inside mail server");
				Properties props = new Properties();
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.host",urMailServer);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port",urPort);
			    props.put("mail.smtp.socketFactory.port",urPort);
				//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.quitwait","false");		
				
				
				    Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
					{ 					
					protected PasswordAuthentication getPasswordAuthentication() 
					{						
						return new PasswordAuthentication(user,password);					
					}				
					});
						
				Transport transport = ses.getTransport();	
			
				MimeMessage msg = new MimeMessage(ses);
				msg.setSubject(subj);

				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.TO, toRecipient);
				msg.setSentDate(new Date());
				msg.setSubject(subj);

				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(mailText);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename.substring(filename
						.lastIndexOf("\\") + 1));
				multipart.addBodyPart(messageBodyPart);
				msg.setContent(mailText.toString(),"text/html");
				msg.setContent(multipart);
				//   ses.setDebug(true); 

				transport.connect();
				Transport.send(msg);

				transport.close();

				statusCode = "1";
				status = true;
				}
				else{

					System.out.println("inside gmail");
				Properties props = new Properties();
				//Security.addProvider(new com.sun.net.ssl.internet.ssl.provider());
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.host",urMailServer);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port",urPort);
			    props.put("mail.smtp.socketFactory.port",urPort);
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.quitwait","false");		
				 Session ses = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
							{ 					
							protected PasswordAuthentication getPasswordAuthentication() 
							{						
								return new PasswordAuthentication(user,password);					
							}				
							});
								
						Transport transport = ses.getTransport();	
					
					
						@SuppressWarnings("unused")
						String recipients[]=toRecipient.split(",");

				MimeMessage msg = new MimeMessage(ses);

				msg.setSubject(subj);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.TO, toRecipient);
				msg.setSentDate(new Date());
				msg.setSubject(subj);

				//   ses.setDebug(true); 

				  BodyPart messageBodyPart = new MimeBodyPart();
			        Multipart multipart = new MimeMultipart();
			        BodyPart htmlPart = new MimeBodyPart();
			        htmlPart.setContent(mailText, "text/html");
			        DataSource source = new FileDataSource(filename);
			        messageBodyPart.setDataHandler(new DataHandler(source));
			        messageBodyPart.setFileName(filename.substring(filename
							.lastIndexOf("\\") + 1));
			        multipart.addBodyPart(messageBodyPart);
			        multipart.addBodyPart(htmlPart);
			        msg.setContent(multipart);
				
				transport.connect();
				Transport.send(msg);

				transport.close();

				statusCode = "1";
				status = true;
				}
			} catch(SMTPSendFailedException se) {
				se.printStackTrace();
				System.out.println("Server Requires Authentication");

				statusCode = "2";
			} catch (javax.mail.MessagingException me) {
				me.printStackTrace();
				System.out.println("Could Not Connect to host server");
				statusCode = "3";
			} catch (Exception e) {

				e.printStackTrace();
			}
			

		return status;
	}
	
	
	
	@SuppressWarnings("unused")
	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator() {
			String username = "auth-user";
			String password = "auth-password";
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication(String user,String password) {
			return authentication;
		}
	}
}
