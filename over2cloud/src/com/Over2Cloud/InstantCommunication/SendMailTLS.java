package com.Over2Cloud.InstantCommunication;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendMailTLS {
	public static void main(String[] args) {
		 
		final String username = "helpdesk@blkhospital.com";
		final String password = "help123";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.office365.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("support@dreamsol.biz"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("shailendram@dreamsol.biz"));
			message.setSubject("Testing Subject ON TLS Protocols");
			message.setText("Dear Mail ID User,"
				+ "\n\n No spam to my email, please! From DreamSol Enviourment");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
