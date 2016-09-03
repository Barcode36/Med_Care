package com.Over2Cloud.common.mail;
/**
 * 
 * @author Sanjiv Singh.
 */
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.sun.mail.smtp.*;

public class GenericMailSender {
	
	public boolean sendMail(String toRecipient,String ccRecipient,String subject,String mailText,String attachFile,String server,String port,String from_emailid,String password)
	{
		//System.out.println("CC   "+ccRecipient);
		//System.out.println("TO   "+toRecipient);
		
		boolean sendMailFlag=false;
		try {
			//System.out.println(""+toRecipient+"<><><><><><><>"+getSmtp());
			if(toRecipient!=null && !toRecipient.equals(""))
			{
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol","smtp");
			props.setProperty("mail.host",server);
			props.put("mail.smtp.port",port);
			props.put("mail.smtp.auth", "true");
			
			//if(smtp.getPort().equals("25"))
			if(port.equals("25"))
			{
			//props.put("mail.smtp.starttls.enable","true");
			}
			else
			{
			//props.setProperty("mail.smtp.ssl.trust","*");
			props.put("mail.smtp.socketFactory.port",port);
			props.put("mail.smtp.socketFactory.fallback", "true");
			props.setProperty("mail.smtp.quitwait","false");
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			}
					
			Session session = Session.getInstance(props,new MailAuthenticator(from_emailid,password));
			Transport transport = session.getTransport();	
			MimeMessage message = new MimeMessage(session);
			if(subject!=null && !subject.equals(""))
			 {	
				message.setSubject(subject);	
			 }
			else
			 {
			   // message.setSubject(smtp.getSubject());
			    message.setSubject("Testing Email");
			 }
			
			
			// To Recipient Email Id's
			if (toRecipient!=null && !toRecipient.equals("") && !toRecipient.equals("NA")) {
			
			String recipients[]=null;
			if(toRecipient.contains(","))
			recipients=toRecipient.split(",");
			else
			 {
				recipients=new String[1];
			    recipients[0]=toRecipient;	
			 }
			//System.out.println("Address to email Id  "+recipients);
			InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
			for (int i = 0; i < recipients.length; i++)
			  {
			        addressTo[i] = new InternetAddress(recipients[i]);
			  }
			if (addressTo!=null && addressTo.length>0) {
				message.setRecipients(Message.RecipientType.TO, addressTo);	
			}
			}
			
			
			//Code for CC Recipient Email Id's
			if (ccRecipient!=null && !ccRecipient.equals("") && !ccRecipient.equals("NA")) {
				String ccrecip[]=null;
				if(ccRecipient.contains(","))
					ccrecip=ccRecipient.split(",");
				else
				{
					ccrecip=new String[1];
					ccrecip[0]=ccRecipient;	
				}
				//System.out.println("Address cc email Id  "+ccrecip);
				InternetAddress[] ccTo = new InternetAddress[ccrecip.length]; 
				for (int i = 0; i < ccrecip.length; i++)
				  {
					ccTo[i] = new InternetAddress(ccrecip[i]);
				  }	
				if (ccTo!=null && ccTo.length>0) {
					message.setRecipients(Message.RecipientType.CC, ccTo);
				}
			}
			
			message.setFrom(new InternetAddress(from_emailid));
		    message.setSentDate(new Date());
		    
		    if(subject.equalsIgnoreCase("Appointment Booking For Executive Health Check-up (EHC)")){
		    	
		    	//C:/Tomcat 6.0/webapps/over2cloud/images/ehc_preco.png
		    	////C:/Tomcat 6.0/webapps/over2cloud/images/IPD_PAT_DOC.PDF
		    	String imagePath = "/C:/Tomcat 6.0/webapps/over2cloud/images/ehc_preco.png";
		    	BodyPart messageBodyPart1 = new MimeBodyPart();

				BodyPart messageBodyPart2 = new MimeBodyPart();

					
					//System.out.println("------------------------------------------>>>>>");
					messageBodyPart2.setHeader("Content-ID", "<image>");
					DataSource fds = new FileDataSource(imagePath); // Change
																	// Image
																	// Path
					// second part (the image)

					messageBodyPart2.setDataHandler(new DataHandler(fds)); // according
																			// to
				String htmlText = "NA";
				// This mail has 2 part, the BODY and the embedded image
				MimeMultipart multipart = new MimeMultipart("related");


				 htmlText = mailText+"<br /><center><img src=\"cid:image\"> </center><br/>"+"We look forward to welcome you and ensure best of our services for all your health care needs. <br> Sincerely <br><b>Medanta-The Medicity</b>";
				//htmlText = headerString + " " + footerString;
				// first part (the html)
				messageBodyPart1.setContent(htmlText, "text/html;");
				// add it
				multipart.addBodyPart(messageBodyPart1);

				// Set From: header field of the header.

				// Set To: header field of the header.
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(toRecipient));

				if (!imagePath.equalsIgnoreCase("NA") && imagePath != null) {
					// / add image to the multipart
					multipart.addBodyPart(messageBodyPart2);
				}

				// put everything together
				message.setContent(multipart);
				
				//X-API Header
				//message.addHeader("X-Mh-Cm-Id", compnId+"#ApoMst01");
		    	
		    	
		    }
		    else if (subject.equalsIgnoreCase("Appointment Booking For In-patient department (IPD)"))
		    {
		    	BodyPart messageBodyPart1 = new MimeBodyPart();


					

				String htmlText = "NA";
				MimeMultipart multipart = new MimeMultipart("related");
				 htmlText = mailText+"<br><br>We look forward to welcome you and ensure best of our services for all your health care needs. <br><br><br> Sincerely <br><b>Medanta-The Medicity</b>";
				//htmlText = headerString + " " + footerString;
				// first part (the html)
				messageBodyPart1.setContent(htmlText, "text/html;");
				// add it
				multipart.addBodyPart(messageBodyPart1);

				// Set From: header field of the header.

				// Set To: header field of the header.
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(toRecipient));
				//attachment
				MimeBodyPart mbp = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource("//D:/Medanta10-06-2016/over2cloud/WebContent/images/IPD_PAT_DOC.PDF");
	            mbp.setDataHandler(new DataHandler(fds));
	            mbp.setFileName(fds.getName());
	            multipart.addBodyPart(mbp);
				

				// put everything together
				message.setContent(multipart);
		    }
		    
		    else if (subject.equalsIgnoreCase("Appointment Booking For Out-patient department (OPD)"))
		    {
		    	BodyPart messageBodyPart1 = new MimeBodyPart();


					

				String htmlText = "NA";
				MimeMultipart multipart = new MimeMultipart("related");
				 htmlText = mailText+"<br><br>We look forward to welcome you and ensure best of our services for all your health care needs. <br><br><br> Sincerely <br><b>Medanta-The Medicity</b>";
				//htmlText = headerString + " " + footerString;
				// first part (the html)
				messageBodyPart1.setContent(htmlText, "text/html;");
				// add it
				multipart.addBodyPart(messageBodyPart1);

				// Set From: header field of the header.

				// Set To: header field of the header.
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(toRecipient));


				// put everything together
				message.setContent(multipart);
		    }
		    
		    else{
		    // Code for setting the HTML body in the mail
		    MimeBodyPart mbp1 = new MimeBodyPart();
	        mbp1.setText(mailText);
	        mbp1.setContent(mailText,"text/html");
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(mbp1);
	
	        if (attachFile!=null && !attachFile.equals("")) {
	            MimeBodyPart mbp = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource(attachFile);
	            mbp.setDataHandler(new DataHandler(fds));
	            mbp.setFileName(fds.getName());
	            mp.addBodyPart(mbp);
			}
			message.setContent(mp);
			
		    }
			////
			
			
			
		   	
		    //If setDebug value is turn true all the mail information will be display on console
			session.setDebug(false); 
			session.getDebugOut();
			transport.connect();	
			message.saveChanges();
			Transport.send(message);
			System.out.println("****** Mail Send Successfully on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime()+" Hrs ******");
			transport.close();
			sendMailFlag=true;
			}
		}catch(SMTPSendFailedException se)
			{
			se.printStackTrace();
			
			}
			catch(javax.mail.MessagingException me)
			{
				System.out.println("Exception is as "+me.getCause());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>."+me.getMessage());
				me.printStackTrace();
				
			}
		 catch (Exception e) {
			 e.printStackTrace();
			 
		 }
		 return sendMailFlag;
	}
}