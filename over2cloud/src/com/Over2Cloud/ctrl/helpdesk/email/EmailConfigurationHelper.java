package com.Over2Cloud.ctrl.helpdesk.email;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class EmailConfigurationHelper {
	
	public boolean registerFeedback(String feedBy,String feedBy_Emailid,String subcatgid,String feedBrief,String level,String allottoId,String to_subdept,String esc_duration,String needesc,String ticketno,SessionFactory connectionSpace)
	 {
		boolean flag=false;
		try 
		  {
			CommonOperInterface cot = new CommonConFactory().createInterface();
			List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "",connectionSpace, "", 0, "table_name","feedback_lodge_configuration");

			if (colName != null && colName.size() > 0) {
				@SuppressWarnings("unused")
				boolean status = false;
				List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
				
				for (GridDataPropertyView tableColumes : colName) {
					if (!tableColumes.getColomnName().equals("uniqueid")
							&& !tableColumes.getColomnName().equals("feedType")
							&& !tableColumes.getColomnName().equals("category")
							&& !tableColumes.getColomnName().equals("resolutionTime")) {
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(tableColumes.getColomnName());
						ob1.setDatatype("varchar("+ tableColumes.getWidth() + ")");
						ob1.setConstraint("default NULL");
						TableColumnName.add(ob1);
					}
				}
				// Method calling for creating table on the basis of above
				cot.createTable22("feedback_status", TableColumnName,connectionSpace);

				// Object creation for Feedback Pojo Bean
				//FeedbackPojo fbp = null;
				// Local variables declaration
				String  escalation_date = "", escalation_time = "";
			
				
				//String modify_time_duration = DateUtil.getResolutionTime(res_time, esc_time, esc_duration);
				
				
			//	System.out.println("Escalation Duration  "+esc_duration);
				//System.out.println("Need Escalation  "+needesc);
				String date_time = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), esc_duration, needesc);
				//System.out.println("Date Time Array is    "+date_time);
				String[] date_time_arr = date_time.split("#");
				if (date_time_arr.length > 0) {
					escalation_date = date_time_arr[0];
					escalation_time = date_time_arr[1];
				}


					/*if (deptLevel != null && deptLevel.equals("2")) {
						to_dept_subdept = tosubdept;
					} else if (deptLevel != null && deptLevel.equals("1")) {
						to_dept_subdept = todept;
					}*/
				
				if (ticketno!=null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId!=null && !allottoId.equals("")) {
				// Setting the column values after getting from the page
				InsertDataTable ob = new InsertDataTable();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				ob.setColName("ticket_no");
				ob.setDataName(ticketno);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by");
				ob.setDataName(feedBy);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by_mobno");
				ob.setDataName("NA");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by_emailid");
				ob.setDataName(feedBy_Emailid);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("deptHierarchy");
				ob.setDataName("2");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("by_dept_subdept");
				ob.setDataName("1");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("subcatg");
				ob.setDataName(subcatgid);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_brief");
				ob.setDataName(feedBrief);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("to_dept_subdept");
				ob.setDataName(to_subdept);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("allot_to");
				ob.setDataName(allottoId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_date");
				ob.setDataName(escalation_date);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_time");
				ob.setDataName(escalation_time);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("location");
				ob.setDataName("Email Server");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName(level);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Pending");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("via_from");
				ob.setDataName("email");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_registerby");
				ob.setDataName("khushal");
				insertData.add(ob);

				// Method calling for inserting the values in the
				// feedback_status table
				 flag = cot.insertIntoTable("feedback_status",insertData, connectionSpace);
				insertData.clear();

				// Block for sending SMS and Mail to the Engineer and
				// status table successfully
				if (flag) {/*
					// List for holding the data of currently lodged
					List data = new HelpdeskUniversalHelper().getFeedbackDetail(ticketno, deptLevel,"Pending", "", connectionSpace);
					fbp = new HelpdeskUniversalHelper().setFeedbackDataValues(data, "Pending",deptLevel);
					printTicket(fbp);
					if (fbp != null) {
						// Block for sending sms for Level1 Engineer
						if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10  &&  !fbp.getMobOne().startsWith("NA") && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7"))) {
							String levelMsg = "Open Feedback Alert: Ticket No: "+ fbp.getTicket_no()+ ", To Be Closed By: "+ fbp.getEscalation_date()+ ","+ fbp.getEscalation_time()+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ", Location: "+ fbp.getLocation()+ " , Brief: "+ fbp.getFeed_brief()+ ".";
							new MsgMailCommunication().addMessage(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getMobOne(), levelMsg, ticketno,"Pending", "0","HDM");
						}
						// Block for sending sms for Complainant
						if (getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7"))) {
							String complainMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ","+ " Ticket No: "+ fbp.getTicket_no()+ ", Addressing Time: "+ DateUtil.getCurrentDateIndianFormat()+ ","+ DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5)+ ", Location: " + fbp.getLocation()+ " , Brief: " + fbp.getFeed_brief()+ " is open.";
							new MsgMailCommunication().addMessage(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_mobno(), complainMsg,ticketno,"Pending", "0", "HDM");
						}
						// Block for creating mail for Level1 Engineer
						if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA")) {
							mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp,"Open Feedback Alert","Pending", deptLevel, true);
							new MsgMailCommunication().addMail(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getEmailIdOne(),"Open Feedback Alert", mailText,ticketno, "Pending", "0","","HDM");
						}
						// Block for creating mail for complainant
						if (getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("") && !fbp.getFeedback_by_emailid().equals("NA")) {
							mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp,"Open Feedback Alert","Pending", deptLevel, false);
							new MsgMailCommunication().addMail(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_emailid(),"Open Feedback Alert", mailText,ticketno,"Pending", "0","","HDM");
						}
					}
				*/}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	return flag;}

}
