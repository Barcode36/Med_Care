package com.Over2Cloud.InstantCommunication;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TLSMail {/*

		public static void main(String[] args) treffrdfr{
			 
			final String username = "helpdesk@blkhospital.com";
			final String password = "help@123";
			
			
			
			List<String> ips = new ArrayList<String>();
			try {
	          String line;
	          Process p = Runtime.getRuntime().exec("nslookup smtp.office365.com");
	          
	           BufferedReader input =
	                  new BufferedReader(new InputStreamReader(p.getInputStream()));
	           
	           
	           
	          while ((line = input.readLine()) != null) {
	        	  
	        	  if(line.startsWith("Address:")){
	        		  
	        		 String[] ip = line.split(":");
	        		 
	        		 if(ip[1].indexOf("#") > 0) continue;
	        		 else    		 ips.add(ip[1].trim());
	        	  }
	        	  
	        	  
	          }
	          input.close();
	      } catch (Exception err) {
	          err.printStackTrace();
	      }	
		  
			
			System.out.println(ips);
			
			//TRY into All IPS
			
			for(String serverIp : ips){
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", serverIp);
				props.put("mail.smtp.port", "587");
		 
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
				session.setDebug(true);
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("helpdesk@blkhospital.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("shailendram@dreamsol.biz"));
					message.setSubject("Testing Subject ON TLS Protocols >>><<<<<<<<<<");
					message.setText("Dear Mail ID User,\n\n No spam to my email, please!");
		 
					Transport trnsport;
					trnsport = session.getTransport("smtp");
					trnsport.connect();
					//Transport.send(message);
		 
					message.saveChanges(); 
					trnsport.sendMessage(message, message.getAllRecipients());
					trnsport.close();
					
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}
			
			
		}

*/}
