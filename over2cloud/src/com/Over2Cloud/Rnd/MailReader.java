package com.Over2Cloud.Rnd;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;

public class MailReader
{
	Properties properties = null;
	private Session session = null;
	private Store store = null;
	private Folder inbox = null;
	private static FeedbackPojo feedPojo = new FeedbackPojo();
	 private String userName = "kuldeep.dreamsol@gmail.com";// provide user
	// name
	 private String password = "inn0vate";// provide password
	//private String userName;
	//private String password;
	String mail_body;
	private static String saveDirectory;
	 
    /**
     * Sets the directory where attached files will be stored.
     * @param dir absolute path of the directory
     */
    public void setSaveDirectory(String dir) {
        this.saveDirectory = dir;
    }
	public void readMails(SessionFactory connection)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();

		String server = "smtp.gmail.com", port = "587";
		try
		{

			/*List list = cbt.executeAllSelectQuery("SELECT email_id,password,server,port FROM email_configuration_data WHERE module_name='FM'", connection);
			if (list != null && list.size() > 0)
			{}*/
			if (userName != null && password != null && server != null && port != null)
			{
				properties = new Properties();
				properties.setProperty("mail.host", server);
				properties.setProperty("mail.port", port);
				properties.setProperty("mail.transport.protocol", "imaps");
				properties.setProperty("mail.smtp.timeout", "6000");
			
				session = Session.getInstance(properties, new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(userName, password);
					}
				});
				
				try
				{
					
					store = session.getStore("imaps");
					store.connect();
					inbox = store.getFolder("INBOX");
					inbox.open(Folder.READ_WRITE);
					Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
					System.out.println("Number of mails = " + messages.length);

					SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

					for (int i = 0; i < messages.length; i++)
					{
						
						Message message = messages[i];
						writePart(message);
						message.setFlag(Flags.Flag.SEEN, true);
						Address[] from = message.getFrom();
						// System.out.println("-------------------------------");
						
						 String contentType = message.getContentType();
				            String attachFiles = "";
				            String messageContent = "";
				            
				            String mail_body=processMessageBody(message);
				         if (contentType.contains("multipart")) {
				             // content may contain attachments
				        	 System.out.println(" >>>>>>>>>AttachMent Found>>>>>>>>>>>>>>"+mail_body);
				             Multipart multiPart = (Multipart) message.getContent();
				             int numberOfParts = multiPart.getCount();
				             for (int partCount = 0; partCount < numberOfParts; partCount++) {
				                 MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
				                 if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				                     // this part is attachment
				                     String fileName = part.getContentType();
				                     attachFiles += fileName + ", ";
				                     part.saveFile(saveDirectory + File.separator + fileName);
				                     
				                     System.out.println(" >>>>>>>>>AttachMent Found>>>>>>>>>>>>>>"+fileName);
				                     // this part may be the message content
				                     messageContent = part.getContent().toString();
				                 }
				             }

				             if (attachFiles.length() > 1) {
				                 attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
				             }
				         } else if (contentType.contains("text/plain")
				                 || contentType.contains("/image"  )) {
				             Object content = message.getContent();
				             if (content != null) {
				                 messageContent = content.toString();
				             }
				         }

						Date date = null;
						try
						{
							date = parseFormat.parse(message.getSentDate().toString());
						} catch (ParseException e)
						{
							e.printStackTrace();
						}
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
						SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
						// String result = format.format(date);
						// System.out.println("Date : " + format.format(date));
						// System.out.println("Time : " + time.format(date));
						// System.out.println("Name : " +
						// from[0].toString().split("<")[0]);
						// System.out.println("E-mail : " +
						// (from[0].toString().split("<")[1]).subSequence(0,
						// from[0].toString().split("<")[1].lastIndexOf(">")));
						// System.out.println("Subject: " +
						// message.getSubject());
						//System.out.println("::::::::"+from[0]);
						String email=null;
						try {
							email = (from[0].toString().split("<")[1]).subSequence(0, from[0].toString().split("<")[1].lastIndexOf(">")).toString();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//String mail_body=processMessageBody(message);
						System.out.println("is mime    "+message.isMimeType(mail_body));
						//if (mail_body.length() > 1000)
							//mail_body = email;
						if (message.isMimeType("multipart/related")) {
							System.out.println("yse");
				            Multipart mp = (Multipart)message.getContent();
				           // Image image = null;
				            for (int j = 0; j < mp.getCount(); j++) {
				                Part bp = mp.getBodyPart(i);
				                if (bp.isMimeType("image/jpeg")) {
				                    System.out.println("yse");
				                    continue;
				                }
				            }
						}
				        
						 System.out.println("Content :" + mail_body);
						if (email != null && !email.equalsIgnoreCase(""))
						{
							getVirtualNoData(connection, email, mail_body, from[0].toString().split("<")[0]);
						}
					}

					// Convert to MimeMessage after search

					// inbox.open(Folder.READ_WRITE);
					// inbox.getMessage(myMsgID).getContent();

					inbox.close(true);
					store.close();
				} catch (NoSuchProviderException e)
				{
					e.printStackTrace();
				} catch (MessagingException e)
				{
					e.printStackTrace();
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	 /*public static void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	    	  saveDirectory = "WebContent/inlineImages";
	         writeEnvelope((Message) p);

	      System.out.println("----------------------------");
	      System.out.println("CONTENT-TYPE:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + p.getContentType());
	      System.out.println(" "+p.isMimeType("image/jpeg"));

	      //check if the content is plain text
	      if (p.isMimeType("image/jpeg")) {
		         System.out.println("--------> image/jpeg");
		         String contectName = p.getContentType();
		         System.out.println("ooooooooooooo"+contectName);
		         System.out.println("content type" + p.getContentType());
		         File f = new File(saveDirectory + File.separator + "InlineImage"+new Date().getTime()+".jpeg");
		         DataOutputStream output = new DataOutputStream(
		            new BufferedOutputStream(new FileOutputStream(f)));
		            com.sun.mail.util.BASE64DecoderStream test = 
		                 (com.sun.mail.util.BASE64DecoderStream) p
		                  .getContent();
		         byte[] buffer = new byte[p.getSize()];
		         int bytesRead;
		         System.out.println("hjhmbvmbvmvbmvbnvb"+p.getContent()+"wwwwwwwwwwww>SIZE"+test.available()+"                "+p.getSize());
		         while ((bytesRead = test.read(buffer)) != -1) {
		            output.write(buffer, 0, bytesRead);
		         }
		         System.out.println("XXXXXXXXXXXXXx"+p.getContent()+"FFFFFFFFFFFFFFFFF"+test.available()+" SSSSSSSSSSSSSSSS###########               "+p.getSize());
		      }
	      else if (p.isMimeType("text/plain")) {
	         System.out.println("This is plain text");
	         System.out.println("---------------------------");
	         System.out.println((String) p.getContent());
	      } 
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	         System.out.println("This is a Multipart");
	         System.out.println("---------------------------");
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	         System.out.println("This is a Nested Message");
	         System.out.println("---------------------------");
	         writePart((Part) p.getContent());
	      } 
	      //check if the content is an inline image
	     
	      else if (p.getContentType().contains("image/")) {
	          System.out.println(">>>>>>>>> image  call");
	         System.out.println("content type" + p.getFileName()+"Dispositon     "+ p.getDisposition()+"     size@@@@@@@@@@@@@@@@@@@"+p.getSize());
	         File f = new File(saveDirectory + File.separator + "InlineImage"+new Date().getTime()+".png");
	         DataOutputStream output = new DataOutputStream(
	            new BufferedOutputStream(new FileOutputStream(f)));
	            com.sun.mail.util.BASE64DecoderStream test = 
	                 (com.sun.mail.util.BASE64DecoderStream) p
	                  .getContent();
	         byte[] buffer = new byte[p.getSize()];
	         int bytesRead;
	         System.out.println("hjhmbvmbvmvbmvbnvb"+p.getContent()+"wwwwwwwwwwww>SIZE"+test.available()+"                "+p.getSize());
	         while ((bytesRead = test.read(buffer)) != -1) {
	            output.write(buffer, 0, bytesRead);
	         }
	         System.out.println("XXXXXXXXXXXXXx"+p.getContent()+"FFFFFFFFFFFFFFFFF"+test.available()+" SSSSSSSSSSSSSSSS###########               "+p.getSize());
	      }
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            System.out.println("This is a string");
	            System.out.println("---------------------------");
	            System.out.println((String) o);
	         } 
	         else if (o instanceof InputStream) {
	            System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(+c);
	         } 
	         else {
	            System.out.println("This is an unknown type");
	            System.out.println("---------------------------");
	            System.out.println(o.toString());
	         }
	      }

	   }
	 */
	 
	 /*public static void writeEnvelope(Message m) throws Exception {
	      System.out.println("This is the message envelope");
	      System.out.println("---------------------------");
	      Address[] a;

	      // FROM
	      if ((a = m.getFrom()) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("FROM: " + a[j].toString());
	      }

	      // TO
	      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("TO: " + a[j].toString());
	      }

	      // SUBJECT
	      if (m.getSubject() != null)
	         System.out.println("SUBJECT: " + m.getSubject());

	   }*/
	
	 public static void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	         writeEnvelope((Message) p);

	      System.out.println("----------------------------");
	      System.out.println("CONTENT-TYPE:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + p.getContentType());
	      System.out.println(" "+p.getContentType());
	      System.out.println("CONTENT-TYPE:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	         System.out.println("This is plain text");
	         System.out.println("---------------------------");
	         System.out.println((String) p.getContent());
	      } 
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	         System.out.println("This is a Multipart");
	         System.out.println("---------------------------");
	         Multipart mp = (Multipart) p.getContent();
	       
	         System.out.println("This is a Multipart"+p.getContentType()+"  @@@@@@@@@--+   "+p.getSize()+"p.getContent()    "+p.getContent());
	         System.out.println("----------------->>>>MULTIPART object----------"+mp);
	         int count = mp.getCount();
	         System.out.println("----------------->>>>MULTIPART  COUNT----------"+count);
	         for (int i = 0; i < count; i++)
	        	 
	            writePart(mp.getBodyPart(i));
	        System.out.println(" ##########3"+mp.getBodyPart(1));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	         System.out.println("This is a Nested Message");
	         System.out.println("---------------------------");
	         writePart((Part) p.getContent());
	      } 
	      //check if the content is an inline image
	      else if (p.isMimeType("image/jpeg") ||p.isMimeType("image/png") ) {
	    	  saveDirectory = "WebContent/inlineImages";
	         System.out.println("--------> image/jpeg");
	         String contectName = p.getContentType();
	         System.out.println("ooooooooooooo"+contectName);
	         System.out.println("content type" + p.getContentType());
	         File f = new File(saveDirectory + File.separator + "InlineImage"+new Date().getTime()+".jpeg");
	         DataOutputStream output = new DataOutputStream(
	            new BufferedOutputStream(new FileOutputStream(f)));
	            com.sun.mail.util.BASE64DecoderStream test = 
	                 (com.sun.mail.util.BASE64DecoderStream) p
	                  .getContent();
	         byte[] buffer = new byte[p.getSize()];
	         int bytesRead;
	         System.out.println("hjhmbvmbvmvbmvbnvb"+p.getContent()+"wwwwwwwwwwww>SIZE"+test.available()+"                "+p.getSize());
	         while ((bytesRead = test.read(buffer)) != -1) {
	            output.write(buffer, 0, bytesRead);
	         }
	         System.out.println("XXXXXXXXXXXXXx"+p.getContent()+"FFFFFFFFFFFFFFFFF"+test.available()+" SSSSSSSSSSSSSSSS###########               "+p.getSize());
	      }
	      else if (p.getContentType().contains("image/")) {
	    	  saveDirectory = "WebContent/inlineImages";
	          System.out.println(">>>>>>>>> image  call");
	         System.out.println("content type" + p.getFileName()+"Dispositon     "+ p.getDisposition()+"     size@@@@@@@@@@@@@@@@@@@"+p.getSize());
	         File f = new File(saveDirectory + File.separator + "InlineImage"+new Date().getTime()+".png");
	         DataOutputStream output = new DataOutputStream(
	            new BufferedOutputStream(new FileOutputStream(f)));
	            com.sun.mail.util.BASE64DecoderStream test = 
	                 (com.sun.mail.util.BASE64DecoderStream) p
	                  .getContent();
	         byte[] buffer = new byte[p.getSize()];
	         int bytesRead;
	         System.out.println("hjhmbvmbvmvbmvbnvb"+p.getContent()+"wwwwwwwwwwww>SIZE"+test.available()+"                "+p.getSize());
	         while ((bytesRead = test.read(buffer)) != -1) {
	            output.write(buffer, 0, bytesRead);
	         }
	         System.out.println("XXXXXXXXXXXXXx"+p.getContent()+"FFFFFFFFFFFFFFFFF"+test.available()+" SSSSSSSSSSSSSSSS###########               "+p.getSize());
	      }
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            System.out.println("This is a string");
	            System.out.println("---------------------------");
	            System.out.println((String) o);
	         } 
	         else if (o instanceof InputStream) {
	            System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(+c);
	         } 
	         else {
	            System.out.println("This is an unknown type");
	            System.out.println("---------------------------");
	            System.out.println(o.toString());
	         }
	      }

	   }
	   /*
	   * This method would print FROM,TO and SUBJECT of the message
	   */
	   public static void writeEnvelope(Message m) throws Exception {
	      System.out.println("This is the message envelope");
	      System.out.println("---------------------------");
	      Address[] a;

	      // FROM
	      if ((a = m.getFrom()) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("FROM: " + a[j].toString());
	      }

	      // TO
	      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("TO: " + a[j].toString());
	      }

	      // SUBJECT
	      if (m.getSubject() != null)
	         System.out.println("SUBJECT: " + m.getSubject());

	   }

	
	public String processMessageBody(Message message)
	{
		String mail_body=null;
		try
		{
			
			Object content = message.getContent();
			// check for string
			// then check for multipart
			if (content instanceof String)
			{
				/*
				 * //String remove = "<div dir='ltr'>hello   manab tets</div>";
				 * // content = content.toString().remove("is", "");
				 * System.out.println("MailReader.processMessageBody()");
				 * System.out.print(content.toString().split("\\<div dir=")[0]);
				 * System.out.print(content.toString().split("\\<div dir=")[1]);
				 */
				// System.out.println("MailReader.processMessageBody()   end ///////////////////////////////////////////////");
			} else if (content instanceof Multipart)
			{
				Multipart multiPart = (Multipart) content;
				mail_body=procesMultiPart(multiPart);
			} else if (content instanceof InputStream)
			{
				InputStream inStream = (InputStream) content;
				int ch;
				while ((ch = inStream.read()) != -1)
				{
					// System.out.println("MailReader.processMessageBody()   end else if ");
					// System.out.print(""+ch);
				}
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
		return mail_body;
	}

	public String procesMultiPart(Multipart content)
	{
		String mail_body=null;
		try
		{
			int multiPartCount = content.getCount();
			for (int i = 0; i < multiPartCount - 1; i++)
			{
				BodyPart bodyPart = content.getBodyPart(i);
				Object o;

				o = bodyPart.getContent();
				if (o instanceof String)
				{
					// System.out.println("MailReader.procesMultiPart()");
					/* System.out.print(((String) o).split("<div")[0]); */
					mail_body = ((String) o);
					
					mail_body = mail_body.replaceAll("<style([\\s\\S]+?)</style>", "");
					// System.out.println(((String) o).split("*")[0]);

				} else if (o instanceof Multipart)
				{
					procesMultiPart((Multipart) o);
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
		return mail_body;
	}

	public void getVirtualNoData(SessionFactory connection, CharSequence email, String mail_body, String name)
	{
		/*try
		{
			FeedbackPojo feedPojo = new MailFeedbackHelper().getFullDetailsRecvedKeyWord(email.toString(), connection, mail_body, name);
			new MailFeedbackHelper().addDataOfFeedbackReceived(feedPojo, connection, "1","Email");
			if (email != null)
			{
				String text = composeMail(name);
				new MsgMailCommunication().addScheduleMail(name, "", email.toString(), "Auto-reply: We have received your email", text, "", "Pending", "0", "", "FM", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimewithSeconds(), connection);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}*/
	}

	private String composeMail(String name)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("Dear " + DateUtil.makeTitle(name) + ",");
		mailtext.append("<br><table><tbody>");
		mailtext.append("<br><tr><td>Thank you for your feedback. Our team will get in touch with you for more details.</td></tr>");
		mailtext.append("</tbody></table><br>Thanks !!!</FONT>");
		return mailtext.toString();
	}

	/*
	 * public static void main(String[] args) { MailReader sample = new
	 * MailReader(); sample.readMails(); }
	 */
}