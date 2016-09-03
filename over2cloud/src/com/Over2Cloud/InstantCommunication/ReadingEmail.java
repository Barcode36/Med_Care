package com.Over2Cloud.InstantCommunication;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.*;
import javax.mail.search.FlagTerm;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.email.EmailConfigurationHelper;
import com.opensymphony.xwork2.ActionSupport;
@SuppressWarnings("serial")
public class ReadingEmail extends ActionSupport{
	private AtomicInteger AN=new AtomicInteger();
   // public static void main(String[] args) {
    	public void testRead(SessionFactory connection, List emailRegistry){
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
        	System.out.println("Email Registry Size is  "+emailRegistry.size());
        	String name="",mobno="",subject="",emailid="",mode="",send_to_id="",send_to_name="",send_to_mobno="",send_to_emailid="",register_password="",register_emailid="",mailText="",ticketno="";
        	String subdeptid="",deptname="",subcatgid="",feedBrief="",esc_duration="",level="",needEsc="";
        	Object content="";
			String[] email_arr= new String[2];
			if (emailRegistry!=null && emailRegistry.size()>0) {
				for (Iterator iterator = emailRegistry.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					register_emailid=object[0].toString();
					register_password=object[1].toString();
					mode=object[2].toString();
					send_to_id=object[3].toString();
					send_to_name=object[4].toString();
					send_to_mobno=object[5].toString();
					send_to_emailid=object[6].toString();
					subdeptid=object[7].toString();
					deptname=object[10].toString();
					subcatgid=object[11].toString();
					feedBrief=object[12].toString();
					esc_duration=object[13].toString();
					level=object[14].toString();
					needEsc=object[15].toString();
					
					
					System.out.println("Email Id ::: >>>>>>  "+register_emailid);
					System.out.println("Password ::: >>>>>>  "+register_password);
	             
					
					Session session = Session.getInstance(props, null);
		            Store store = session.getStore();
		            store.connect("imap.gmail.com", register_emailid, register_password);
			            
		            Folder folder = store.getFolder("INBOX");
		            folder.open(Folder.READ_WRITE);
		            Flags seen = new Flags(Flags.Flag.SEEN);
		            FlagTerm unseenFlagTerm = new FlagTerm(seen,false);
		            Message message[] = folder.search(unseenFlagTerm); 
		            for (int i=0; i< message.length;i++)
		             {
		            	subject=message[i].getSubject();
		                
		                Multipart mp = (Multipart) message[i].getContent();
		                BodyPart bp = mp.getBodyPart(0);
		                content=bp.getContent();
		                
		                Address[] in = message[i].getFrom();
		                for (Address address : in)
		                 {
		                    emailid=address.toString();
		                 }
			            if (emailid!=null && !emailid.equals("")) {
			            	 email_arr =emailid.split("<");
				                if (email_arr!=null && email_arr.length>0)
				                 {
				                	name=email_arr[0];
				                	emailid=email_arr[1].replace("<", "").replace(">", "").trim();
								 }
						}    
		               
		              
		                if (name!=null && !name.equals("") && emailid!=null && !emailid.equals("") && content!=null && !content.equals("")) {
		                	boolean checkTable = new HelpdeskUniversalHelper().checkTable("feedback_status", connection);
		                	System.out.println("Check Table Flag  "+checkTable);
		                	if (checkTable) {
		                		ticketno = getTicketNo(connection);
		                		System.out.println("Ticket No inside If "+ticketno);
		                		if (ticketno==null || ticketno.equals("")) {
		                			ticketno = "10001";
								}
		                		System.out.println("Ticket No inside If After Check  "+ticketno);
							}
		                	else {
		                		ticketno = "1000";
		                		System.out.println("Ticket No inside Else "+ticketno);
							}
		                	if (ticketno!=null && !ticketno.equals("")) {
		                	//boolean saveData = savedata(name,emailid,subject,content.toString(),ticketno,"EMAIL",mode,"NA","NA",connection);
		                	boolean saveData=new EmailConfigurationHelper().registerFeedback(name,emailid,subcatgid,feedBrief,level,send_to_id,subdeptid,esc_duration,needEsc,ticketno,connection);
		                	
		                	
		                	
		                	System.out.println("Save Data Value is  "+saveData);
		                	if (saveData) {/*
		                		if (send_to_emailid != null && !send_to_emailid.equals("") && !send_to_emailid.equals("NA")) {
									mailText = new HelpdeskUniversalHelper().getMailConfig(send_to,"Confirmation Mail","Pending", "2", true);
									new CommunicationHelper().addMail(send_to,"IT",send_to_emailid,"Confirmation Mail", mailText,"","Pending", "0","","HDM",connection);
								}
		                		if (emailid != null && !emailid.equals("") && !emailid.equals("NA")) {
									mailText = new HelpdeskUniversalHelper().getMailConfig(name,"Confirmation Mail","Pending", "2", false);
									new CommunicationHelper().addMail(name,"IT",emailid,"Confirmation Mail", mailText,"","Pending", "0","","HDM",connection);
								}
							  */}
		                    }
						  }
		                }
		             folder.close(true);
		             store.close();
				}
			}
			
           
            
             Runtime rt = Runtime.getRuntime();
			 rt.gc();
			 System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
			 Thread.sleep(1000*5);
			 System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
    	
   public boolean savedata(String name,String emailid,String subject,String content,String ticketno,String via_from,String module,String mobno,String msg_text,SessionFactory connection)
   	 {
	   boolean flag = false;
		try {
	        CommonOperInterface cot = new CommonConFactory().createInterface();
	        TableColumes ob1 = new TableColumes();
	        List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
	
			ob1.setColumnname("name");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("mobno");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("mob_msg");
			ob1.setDatatype("varchar(150)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("emailid");
			ob1.setDatatype("varchar(150)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("subject");
			ob1.setDatatype("varchar(250)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
	
			ob1 = new TableColumes();
			ob1.setColumnname("content");
			ob1.setDatatype("varchar(250)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("ticket_Series");
			ob1.setDatatype("varchar(30)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("via_from");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("module");
			ob1.setDatatype("varchar(20)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("flag");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("update_date");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("update_time");
			ob1.setDatatype("varchar(10)");
			ob1.setConstraint("default NULL");
			TableColumnName.add(ob1);
			
			cot.createTable22("msg_mail_contacts",TableColumnName,connection);
			
		  	 InsertDataTable ob=new InsertDataTable();
		     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			 ob.setColName("name");
			 ob.setDataName(name);
			 insertData.add(ob);
			 
			  
			 ob=new InsertDataTable();
			 ob.setColName("mobno");
			 ob.setDataName(mobno);
			 insertData.add(ob);
		 
			 ob=new InsertDataTable();
			 ob.setColName("mob_msg");
			 ob.setDataName(msg_text);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("emailid");
			 ob.setDataName(emailid);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("subject");
			 ob.setDataName(subject);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("content");
			 ob.setDataName(content);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("ticket_series");
			 ob.setDataName(ticketno);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("via_from");
			 ob.setDataName(via_from);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("module");
			 ob.setDataName(module);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("flag");
			 ob.setDataName("0");
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("date");
			 ob.setDataName(DateUtil.getCurrentDateUSFormat());
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("time");
			 ob.setDataName(DateUtil.getCurrentTime());
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("update_date");
			 ob.setDataName("0000-00-00");
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("update_time");
			 ob.setDataName("00:00:00");
			 insertData.add(ob);
		 
			 flag=cot.insertIntoTable("msg_mail_contacts", insertData, connection);
	         insertData.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
   	 }
   
   @SuppressWarnings("unchecked")
	public String getTicketNo(SessionFactory connectionSpace)
	 {
		String ticketno="",ticket_no="";
		try {
			  // Code for getting the first time counters of Feedback Status table, If get ticket counts greater than Zero than go in If block  and if get 0 than go in else block 	
			  ticket_no= new HelpdeskUniversalHelper().getLatestTicket4Email(connectionSpace);
			  if (ticket_no!=null && !ticket_no.equals("") && !ticket_no.equals("NA")) {
					  AN.set(Integer.parseInt(ticket_no));
					  ticketno=""+AN.incrementAndGet();
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketno;
	}

public AtomicInteger getAN() {
	return AN;
}

public void setAN(AtomicInteger an) {
	AN = an;
}
}
