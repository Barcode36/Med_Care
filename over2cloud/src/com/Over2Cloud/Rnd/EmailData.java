package com.Over2Cloud.Rnd;



import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.apache.poi.hssf.record.PageBreakRecord.Break;

public class EmailData {
	  private static String saveDirectory;
		 
	    /**
	     * Sets the directory where attached files will be stored.
	     * @param dir absolute path of the directory
	     */
	    public void setSaveDirectory(String dir) {
	        this.saveDirectory = dir;
	    }
	 
   public  void fetch(String pop3Host, String port ,String storeType, String user,
      String password) {
      try {
         // create properties field
         Properties properties = new Properties();
         properties.put("mail.store.protocol", "pop3");
         properties.put("mail.pop3.host", pop3Host);
         properties.put("mail.pop3.port", port);
         properties.put("mail.pop3.starttls.enable", "true");
         
         properties.setProperty("mail.pop3.socketFactory.class",
         "javax.net.ssl.SSLSocketFactory");
 properties.setProperty("mail.pop3.socketFactory.fallback", "false");
 properties.setProperty("mail.pop3.socketFactory.port",
         String.valueOf(port));

 Session session = Session.getDefaultInstance(properties);
         Session emailSession = Session.getDefaultInstance(properties);
         // emailSession.setDebug(true);

         // create the POP3 store object and connect with the pop server
         Store store = emailSession.getStore("pop3s");

         store.connect(pop3Host, user, password);

         // create the folder object and open it
         Folder emailFolder = store.getFolder("INBOX");
         emailFolder.open(Folder.READ_ONLY);

         BufferedReader reader = new BufferedReader(new InputStreamReader(
	      System.in));

         // retrieve the messages from the folder in an array and print it
         Message[] messages = emailFolder.getMessages();
         System.out.println("messages.length---" + messages.length);

         for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            writePart(message);
            String line = reader.readLine();
          /*  if ("YES".equals(line)) {
                message.writeTo(System.out);
             } else if ("QUIT".equals(line)) {
                break;
             }
          }
          */
            String contentType = message.getContentType();
            String attachFiles = "";
            String messageContent = "";
            
           
         if (contentType.contains("multipart")) {
             // content may contain attachments
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


         // close the store and folder objects
         emailFolder.close(false);
         store.close();


         }} catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   public static void main(String[] args) {

      String pop3Host = "pop.gmail.com";// change accordingly
      String mailStoreType = "pop3";
      String port = "995";
      String username = 
         "kadira@dreamsol.biz";// change accordingly
      String password = "dreamsol@123";// change accordingly
      String saveDirectory = "WebContent/inlineImages";
 	 
      EmailData receiver = new EmailData();
      receiver.setSaveDirectory(saveDirectory);
      receiver.fetch(pop3Host ,port,mailStoreType, username, password);

      
       
      
      //Call method fetch
     // fetch(host, mailStoreType, username, password);

   }

   /*
   * This method checks for content-type 
   * based on which, it processes and
   * fetches the content of the message
   */
   public static void writePart(Part p) throws Exception {
      if (p instanceof Message)
         //Call methos writeEnvelope
         writeEnvelope((Message) p);

      System.out.println("----------------------------");
      System.out.println("CONTENT-TYPE: " + p.getContentType());
      System.out.println(" "+p.getContentType());

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
      else if (p.isMimeType("image/jpeg")) {
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

}