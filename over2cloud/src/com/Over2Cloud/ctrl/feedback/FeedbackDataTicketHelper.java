package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class FeedbackDataTicketHelper {

	
	public String getConfigMessage(FeedbackPojo feedbackObj,String title,String status,String deptLevel,boolean flag) {
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_registerby())+ ",</b>");	
		}
	   
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
		mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
		}
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.getCurrentDateIndianFormat()+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.getCurrentTime().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
		if (deptLevel.equals("2")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Sub-Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_subdept() + "</td></tr>");
		}
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Addressing&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.newTime(feedbackObj.getFeedaddressing_time()).substring(0, 5)+ " Hrs</td></tr>");
		if (status.equalsIgnoreCase("High Priority")) {
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> High Priority Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getHp_reason() + " </td></tr>");
		}
		else if (status.equalsIgnoreCase("Snooze")) {
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getSn_date()) + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_time() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Ignore")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ignore Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getIg_reason() + "</td></tr>");
		}
		
        else if (status.equalsIgnoreCase("Resolved")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Spare Used:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve Remark:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
		}
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		try
		{
			InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/CommonClasses/ipAddress.properties");
			Properties configProp = new Properties();
			configProp.load(in);
			
			String hostName=configProp.getProperty("serverIp");
			mailtext.append("Click on the Link Below to Close the Ticket");
			mailtext.append("<br>");
			mailtext.append("http://"+hostName+"/cloudapp/view/Over2Cloud/feedback/actionOnTicketByMail.action?feedId="+feedbackObj.getId()+"&ticketNo="+feedbackObj.getTicket_no()+"&feedbackBy="+feedbackObj.getFeed_registerby()+"&mobileno="+feedbackObj.getFeedback_by_mobno()+"&emailId="+feedbackObj.getFeedback_by_emailid()+"&feedBreif="+feedbackObj.getFeed_brief().replaceAll(" ","%20")+"&openDate="+feedbackObj.getOpen_date()+"&openTime="+feedbackObj.getOpen_time()+"&allotto="+feedbackObj.getFeedback_allot_to());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
      }
	
	public FeedbackPojo setFeedbackDataValues(List data, String status,String deptLevel)
	{
		FeedbackPojo fbp =null;
		if (data!=null && data.size()>0) {
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				fbp.setTicket_no(object[0].toString());
				fbp.setFeed_registerby(object[1].toString());
				fbp.setFeedback_by_mobno(object[2].toString());
				fbp.setFeedback_by_emailid(object[3].toString());
				fbp.setFeedback_allot_to(object[4].toString());
				fbp.setFeedtype(object[5].toString());
				fbp.setFeedback_catg(object[6].toString());
				fbp.setFeedback_subcatg(object[7].toString());
				fbp.setFeed_brief(object[8].toString());
				fbp.setFeedaddressing_time(object[9].toString());
				if(object[11]!=null)
				{
					fbp.setLocation(object[11].toString());
				}
				
				fbp.setOpen_date(object[12].toString());
				fbp.setOpen_time(object[13].toString());
				fbp.setEscalation_date(object[14].toString());
				fbp.setEscalation_time(object[15].toString());
				fbp.setLevel(object[16].toString());
				fbp.setStatus(object[17].toString());
				fbp.setVia_from(object[18].toString());
				
				if (!status.equals("Pending")) {
				fbp.setAction_by(object[34].toString());	
				}
				
				if (status.equals("High Priority")) {
				fbp.setHp_date(object[19].toString());	
				fbp.setHp_time(object[20].toString());	
				fbp.setHp_reason(object[21].toString());	
				}
				else if (status.equals("Snooze")) {
			    fbp.setSn_reason(object[22].toString());
			    fbp.setSn_on_date(object[23].toString());
			    fbp.setSn_on_time(object[24].toString());
			    fbp.setSn_date(object[25].toString());
			    fbp.setSn_time(object[26].toString());
			    fbp.setSn_duration(object[27].toString());
				}
                else if (status.equals("Ignore")) {
                fbp.setIg_date(object[28].toString());
     			fbp.setIg_time(object[29].toString());
     			fbp.setIg_reason(object[30].toString());
				}
                else if (object[31]!=null && !object[31].toString().equals("") && object[32]!=null && !object[32].toString().equals("")) {
                fbp.setTransfer_date(object[31].toString());
         		fbp.setTransfer_time(object[32].toString());
         		fbp.setTransfer_reason(object[33].toString());	
				}
				
                else if (deptLevel.equals("2") && status.equals("Resolved")) {
                fbp.setResolve_date(object[41].toString());
                fbp.setResolve_time(object[42].toString());
                fbp.setResolve_duration(object[43].toString());
                fbp.setResolve_remark(object[44].toString());
                fbp.setResolve_by(object[45].toString());
                fbp.setSpare_used(object[46].toString());
				}
				
                else if (deptLevel.equals("1") && status.equals("Resolved")) {
                fbp.setResolve_date(object[39].toString());
                fbp.setResolve_time(object[40].toString());
                fbp.setResolve_duration(object[41].toString());
                fbp.setResolve_remark(object[42].toString());
                fbp.setResolve_by(object[43].toString());
                fbp.setSpare_used(object[44].toString());
    		    }
				
				if (deptLevel.equals("2")) {
                fbp.setFeedback_by_dept(object[37].toString());
             	fbp.setFeedback_by_subdept(object[38].toString());
             	fbp.setFeedback_to_dept(object[39].toString());	
             	fbp.setFeedback_to_subdept(object[40].toString());
    		    }
				else if (deptLevel.equals("1")) {
				fbp.setFeedback_by_dept(object[37].toString());
	            fbp.setFeedback_to_dept(object[38].toString());	
				}
				fbp.setMobOne(object[35].toString());
	            fbp.setEmailIdOne(object[36].toString());	
			}
		}
		return fbp;
	}
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String ticketno, String deptLevel,String status,String id, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	if (deptLevel.equals("2")) {
            		query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            		query.append("feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
            		query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
            		query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            		query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
            		query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
            		query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
            		query.append("emp.mobOne,emp.emailIdOne,dept1.deptName as bydept,subdept1.subdeptname as bysubdept,dept2.deptName as todept,subdept2.subdeptname as tosubdept");
            		if (status.equalsIgnoreCase("Resolved")) {
            		query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
					}
            		query.append(" from feedback_status as feedback");
            		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            		query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
            		query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            		query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		if (status.equalsIgnoreCase("Resolved")) {
            		query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
					}
            		if (id!=null && !id.equals("") && status!=null && !status.equals("")) {
            			query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
					}
            		else {
            		query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from feedback_status)");
					}
            	}
            	
            	else if (deptLevel.equals("1")) {
            		query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            		query.append("feedback.feed_by_emailid,emp.empName as allotto,feedtype.fbType,catg.categoryName,");
            		query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
            		query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            		query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
            		query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
            		query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
            		query.append("dept1.deptName as bydept,dept2.deptName as todept");
            		if (status.equalsIgnoreCase("Resolved")) {
            			query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
                    }
            		query.append(" from feedback_status as feedback");
            		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            		query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
            		query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		if (status.equalsIgnoreCase("Resolved")) {
                		query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
    				}
                	if (id!=null && !id.equals("")) {
                			query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
    				}
                	else {
                		query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from feedback_status)");
    				}
     	        }
                    list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	@SuppressWarnings("unchecked")
	public boolean updateTableColomn(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock,SessionFactory connection)
	 {
		boolean Data=false;
		StringBuilder selectTableData = new StringBuilder("");  
		
		selectTableData.append("update " + tableName+" set ");
		int size=1;
		Set set =parameterClause.entrySet(); 
		Iterator i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<parameterClause.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		
		if(condtnBlock.size()>0)
		{
			selectTableData.append(" where ");
		}
		size=1;
		set =condtnBlock.entrySet(); 
		i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<condtnBlock.size())
				selectTableData.append(me.getKey()+" = "+me.getValue()+" and ");
			else
				selectTableData.append(me.getKey()+" = "+me.getValue()+"");
			size++;
		} 
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
				if(count>0)
					Data=true;
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		return Data;
	}
	
	
	  public List getEmpWithoutRoasterForFeedback(String uName, String deptLevel,SessionFactory connection)
	   {

		//   System.out.println("Inside to get Emp Without Roaster<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   List  empList = new ArrayList();
		   String empall= null;
		   try 
			{
			   if (deptLevel.equals("2")) {
				   empall="select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid,dept.id, dept.deptName from employee_basic as emp"
						+" inner join subdepartment as sdept on emp.subdept=sdept.id"
						+" inner join department as dept on sdept.deptid=dept.id"
						+" inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"+uName+"'";
			   }
			   else {
				   empall="select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName from employee_basic as emp"
					    +" inner join department as dept on emp.dept=dept.id"
						+" inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"+uName+"'";
			   }
			  	   empList=new createTable().executeAllSelectQuery(empall,connection);
			} catch (Exception e)
			  {
				 e.printStackTrace();
			  } 
			return empList;
	     
	   }
	
	  @SuppressWarnings("unchecked")
		public List getEscalation( String deptLevel, String dept, String levelname,SessionFactory connection)
		 {
			String shiftname =DateUtil.getCurrentDateUSFormat().substring(8, 10)+"_date";
			List  escalation = new ArrayList();
			String query="select emp.empName,emp.mobOne,emp.emailIdOne,sub_dept.subdeptname from employee_basic as emp"
						+" inner join designation desg on emp.designation = desg.id"
						+" inner join subdepartment sub_dept on emp.subdept = sub_dept.id"
			            +" where desg.levelofhierarchy='"+levelname+"' and sub_dept.id='"+dept+"'";

		//	System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+query.toString());
					    Session session = null;  
					    Transaction transaction = null;  
					    try 
					     {  
				            session=connection.openSession(); 
							transaction = session.beginTransaction(); 
							escalation=session.createSQLQuery(query).list();  
							transaction.commit(); 
						 }
					    catch (Exception ex)
						 {
							transaction.rollback();
						 } 
			
					    finally{
						    session.close();
					    }
				return escalation;
		    }
	
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,SessionFactory connection)
	  {
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		selectTableData.append("select ");
		
		// Set the columns name of a table
		if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
		  {
			int i=1;
			for(String H:colmName)
			 {
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
					i++;
			 }
		 }
		
		// Here we get the whole data of a table
		else
		 {
				selectTableData.append("* from " +tableName);
		 }
	    
		// Set the values for where clause
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
					size++;
				} 
			}
		
		// Set the values for where clause List
		if(wherClauseList!=null && !wherClauseList.isEmpty())
		   {
			 if(wherClauseList.size()>0)
				{
					selectTableData.append(" and ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue().toString().replace("[", "(").replace("]", ")")+"'");
					size++;
				} 
			}
		
		 // Set the value for type of order for getting data in specific order
		 if(order!=null && !order.isEmpty())
		   {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
			    }
		   }
			selectTableData.append(";");
			Session session = null;  
			Transaction transaction = null;  
			try {  
		            session=connection.openSession(); 
					transaction = session.beginTransaction(); 
				//	System.out.println("selectTableData.toString() is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+selectTableData.toString());
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			finally{
				session.close();
			}
			return Data;
		}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept_subDept, String deptLevel, SessionFactory connectionSpace)
     {
            List<Integer> list=new ArrayList<Integer>();
            String qry=null;
            try
            {
            	String shiftname =DateUtil.getCurrentDateUSFormat().substring(8, 10)+"_date";
            	if (deptLevel.equals("2")) {
            		qry= " select emp.id from employee_basic as emp" 
                     	+" inner join designation desg on emp.designation = desg.id"
                     	+" inner join subdepartment sub_dept on emp.subdept = sub_dept.id"
                     	+" inner join roaster_conf roaster on emp.empId = roaster.empId"
                     	+" inner join shift_conf shift on sub_dept.id = shift.dept_subdept"
                        +" where shift.shiftName="+shiftname+" and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='Level1' and sub_dept.id='"+dept_subDept+"' and shift.deptHierarchy='2' and shift.shiftFrom<='"+DateUtil.getCurrentTime()+"' and shift.shiftTo >'"+DateUtil.getCurrentTime()+"'";
				}
            	else if (deptLevel.equals("1")) {
            		qry= " select emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp" 
                     	+" inner join designation desg on emp.designation = desg.id"
                     	+" inner join department dept on emp.dept = dept.id"
                     	+" inner join roaster_conf roaster on emp.empId = roaster.empId"
                     	+" inner join shift_conf shift on dept.id = shift.dept_subdept"
                        +" where shift.shiftName="+shiftname+" and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='Level1' and dept.id='"+dept_subDept+"' and shift.deptHierarchy='1' and shift.shiftFrom<='"+DateUtil.getCurrentTime()+"' and shift.shiftTo >'"+DateUtil.getCurrentTime()+"'";
				}
            //	System.out.println("Allot To Qry  ::  "+qry);
            	list=new createTable().executeAllSelectQuery(qry,connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept, String deptLevel, SessionFactory connectionSpace)
     {
            List<Integer> list=new ArrayList<Integer>();
            String qry=null;
            try
            {
                    qry= "select distinct allot_to from feedback_status where to_dept_subdept='"+dept_subDept+"' and deptHierarchy='"+deptLevel+"' and open_date='"+DateUtil.getCurrentDateUSFormat()+"'" ;
                    list=new createTable().executeAllSelectQuery(qry,connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
}
