package com.Over2Cloud.smsvmn;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.feedback.service.KeyWordRecvModem;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.FeedbackActionModeCtrl;

public class SmsVMNHelper
{

	private final String COMPKEYWORD = "blk";

	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private static final MsgMailCommunication MMC = new MsgMailCommunication();

	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public String[] getClientAccountData(String keyWord)
	{
		String AccountDetails = null;
		String[] keyWords = null;

		try
		{
			// Creating URL
			System.out.println("Connecting to Server");
			String url = "http://95.172.28.178:8080/VMMServer/virtualPoll?Keyword=" + keyWord;
			if (url != null)
			{
				URL codedURL = new URL(url.toString());
				HttpURLConnection HURLC = (HttpURLConnection) codedURL.openConnection();
				HURLC.setRequestMethod("GET");
				HURLC.setDoOutput(true);
				HURLC.setDoInput(true);
				HURLC.setUseCaches(false);
				HURLC.connect();
				AccountDetails = HURLC.getHeaderField("1708");
				HURLC.disconnect();
				if (AccountDetails != null)
				{
					keyWords = AccountDetails.split("_");
					 System.out.println("Found");
				}
			}
		}
		catch (Exception E)
		{
			E.printStackTrace();
		}
		System.out.println("keyWords   " +keyWords.length);
		return keyWords;
		
	}

	@SuppressWarnings("null")
	public void getVirtualNoData(SessionFactory connection)
	{

		try
		{
			boolean revertFlag = false;
			System.out.println(COMPKEYWORD);
			String arrLr[] = getClientAccountData(COMPKEYWORD);
			//System.out.println(arrLr.length);
			//String arrLr[] = {"9250400314","ct IT26526*Testing SMS Mode  ","2014-10-13"};// getClientAccountData(COMPKEYWORD);
			if (arrLr != null)
			{
				System.out.println("arrLr vcvc  "   +arrLr.length);
				
				System.out.println(">>"   +arrLr[1].trim()+"<<<<<");
				
				if (arrLr != null && arrLr[1].trim().indexOf("*") > 0)
				{

					
					String taicketStatus = arrLr[1].trim().substring(0, 2);
					System.out.println(taicketStatus);
					String ticketFormat = arrLr[1].substring(0, arrLr[1].trim().indexOf("*"));
					System.out.println("ticketFormat    " + ticketFormat);
					String taicketNo = arrLr[1].substring(2, arrLr[1].trim().indexOf("*")).toUpperCase();
					String comment = arrLr[1].substring(arrLr[1].trim().indexOf("*") + 1, arrLr[1].length());

					// CT >> Close Ticket >> Resolved
					// HT >> High Priority >> High Priority
					// IT >> Ignore >> Ignore
					// NT >> Noted >> Noted
					String[] empdata = getEmpName(arrLr[0], connection);

					System.out.println("comment    " + comment);
					if (!(comment != null && comment.length() > 0))
						comment = "NA";

					String closeDate = DateUtil.getCurrentDateUSFormat();
					String closeTime = DateUtil.getCurrentTimeHourMin();
					String ticketStatus = "NA";
					String revertSMS = "NA";
					String ticketName = "NA";
					// Closed/ Ignore/ Noted/ High Priority (20)
					if (taicketStatus.equalsIgnoreCase("CT"))
					{
						System.out.println("taicketStatus "+taicketStatus);
						ticketStatus = "Resolved";
						ticketName = "Closed";
						revertFlag = new FeedbackActionModeCtrl().actionBySMS(connection, taicketNo, arrLr[0], comment, closeDate, closeTime, ticketStatus);
						if (empdata[1] != null)
						{
							revertSMS = "Dear " + empdata[1] + ", " + taicketNo + " has been successfully " + ticketName + " as requested by you by SMS mode at " + COMPKEYWORD + ".";
							System.out.println("revertSMS  " + revertSMS);
							new CommunicationHelper().addMessage(empdata[1], "", empdata[2], revertSMS, "", "Pending", "0", "", connection);

						}
						else
						{
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS  " + revertSMS);
							new CommunicationHelper().addMessage("", "", arrLr[0], revertSMS, "", "Pending", "0", "", connection);
						}

					}

					else if (taicketStatus.equalsIgnoreCase("HT") && taicketStatus.trim().length() > 2 && ticketFormat.equals(ticketFormat))
					{
						ticketStatus = "High Priority";
						ticketName = "High Priority";
						revertFlag = new FeedbackActionModeCtrl().actionBySMS(connection, taicketNo, arrLr[0], comment, closeDate, closeTime, ticketStatus);
						if (empdata[1] != null)
						{
							revertSMS = "Dear " + empdata[1] + ", " + taicketNo + " has been successfully " + ticketName + " as requested by you by SMS mode at " + COMPKEYWORD + ".";
							new CommunicationHelper().addMessage(empdata[1], "", empdata[2], revertSMS, "", "Pending", "0", "", connection);
						}
						else
						{
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS  " + revertSMS);
							new CommunicationHelper().addMessage("", "", arrLr[0], revertSMS, "", "Pending", "0", "", connection);
						}
					}
					else if (taicketStatus.equalsIgnoreCase("IT") && taicketStatus.trim().length() > 2 && ticketFormat.equals(ticketFormat))
					{
						ticketStatus = "Ignore";
						ticketName = "Ignore";
						revertFlag = new FeedbackActionModeCtrl().actionBySMS(connection, taicketNo, arrLr[0], comment, closeDate, closeTime, ticketStatus);
						if (empdata[1] != null)
						{
							revertSMS = "Dear " + empdata[1] + ", " + taicketNo + " has been successfully " + ticketName + " as requested by you by SMS mode at " + COMPKEYWORD + ".";
							new CommunicationHelper().addMessage(empdata[1], "", empdata[2], revertSMS, "", "Pending", "0", "", connection);
						}
						else
						{
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS  " + revertSMS);
							new CommunicationHelper().addMessage("", "", arrLr[0], revertSMS, "", "Pending", "0", "", connection);
						}

					}
					else if (taicketStatus.equalsIgnoreCase("NT") && taicketStatus.trim().length() > 2 && ticketFormat.equals(ticketFormat))
					{
						ticketStatus = "Noted";
						ticketName = "Noted";
						revertFlag = new FeedbackActionModeCtrl().actionBySMS(connection, taicketNo, arrLr[0], comment, closeDate, closeTime, ticketStatus);
						if (empdata[1] != null)
						{
							revertSMS = "Dear " + empdata[1] + ", " + taicketNo + " has been successfully " + ticketName + " as requested by you by SMS mode at " + COMPKEYWORD + ".";
							new CommunicationHelper().addMessage(empdata[1], "", empdata[2], revertSMS, "", "Pending", "0", "", connection);
						}
						else
						{
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS  " + revertSMS);
							new CommunicationHelper().addMessage("", "", arrLr[0], revertSMS, "", "Pending", "0", "", connection);
						}
					}

					connection = new ConnectionHelper().getSessionFactory("IN-1");

					boolean tableCreated = new KeyWordRecvModem().createT2MTable(connection);
					if (tableCreated)
					{
						System.out.println("Table created>>>");
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						if (arrLr != null)
						{
							// for (int i = 0; i < arrLr.length; i++) {
							insertData.clear();

							InsertDataTable ob1 = new InsertDataTable();
							ob1.setColName("mobNo");
							ob1.setDataName(arrLr[0]);
							insertData.add(ob1);

							InsertDataTable ob2 = new InsertDataTable();
							ob2.setColName("keyword");
							ob2.setDataName(taicketStatus);
							insertData.add(ob2);

							InsertDataTable ob3 = new InsertDataTable();
							ob3.setColName("date");
							ob3.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob3);

							InsertDataTable ob4 = new InsertDataTable();
							ob4.setColName("time");

							ob4.setDataName(closeTime);
							insertData.add(ob4);

							InsertDataTable ob5 = new InsertDataTable();
							ob5.setColName("status");
							ob5.setDataName("3");
							insertData.add(ob5);

							tableCreated = cbt.insertIntoTable("t2mkeyword", insertData, connection);

							System.out.println("Table Data Inserted Successfully !!! " + tableCreated);

						}
					}

					AllConnection Conn = new ConnectionFactory("localhost");
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					SessionFactory sessfact = Ob1.getSession();

					boolean tableCreated1 = new SmsVMNTable().createSmsVMNTable(sessfact);

					if (tableCreated1)
					{
						// System.out.println("Table Crtaetyed>>>");
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						// System.out.println("arrLrLENGTh" +arrLr.length);
						if (arrLr != null)
						{
							// for (int i = 0; i < arrLr.length; i++) {
							insertData.clear();

							InsertDataTable ob = new InsertDataTable();
							ob.setColName("clientID");
							ob.setDataName(COMPKEYWORD);
							insertData.add(ob);

							InsertDataTable ob1 = new InsertDataTable();
							ob1.setColName("ticketID");
							ob1.setDataName(taicketNo);
							insertData.add(ob1);

							InsertDataTable ob2 = new InsertDataTable();
							ob2.setColName("activityID");
							ob2.setDataName(taicketStatus);
							insertData.add(ob2);

							InsertDataTable ob12 = new InsertDataTable();
							ob12.setColName("comment");
							ob12.setDataName(comment);
							System.out.println("comment" + comment);
							insertData.add(ob12);

							InsertDataTable ob5 = new InsertDataTable();
							ob5.setColName("date");
							ob5.setDataName(DateUtil.getCurrentDateIndianFormat());
							insertData.add(ob5);

							InsertDataTable ob6 = new InsertDataTable();
							ob6.setColName("time");
							ob6.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData.add(ob6);

							InsertDataTable ob7 = new InsertDataTable();
							ob7.setColName("mobileNo");
							ob7.setDataName(arrLr[0]);
							insertData.add(ob7);

							// Create Table and Store into table

							tableCreated1 = cbt.insertIntoTable("sms_keyword_Report", insertData, sessfact);

							System.out.println("Table Data Inserted Successfully sms_keyword_Report!!! " + tableCreated1);

						}

					}
				}
				else if (arrLr != null && arrLr[1].trim().indexOf(" ") > 1)
				{
					String revertSMS = "NA";
					System.out.println("else conditionnn");
					String taicketNo;
					String comment;
					// ctP1001Kindlyfgfhgfgf gfgf
					String tmpMessage = arrLr[1].trim();
					String taicketStatus = tmpMessage.substring(0, 2);

					tmpMessage = tmpMessage.substring(2, tmpMessage.length()).trim(); // P1001
																						// uigfhgfhg

					if (tmpMessage.indexOf(" ") > 1)
					{
						taicketNo = tmpMessage.substring(0, tmpMessage.trim().indexOf(" "));
						comment = arrLr[1].substring(tmpMessage.trim().indexOf(" "), tmpMessage.length());
						System.out.println("tmpMessage" + tmpMessage);
						System.out.println("comment" + comment);

						revertSMS = "Hi, you seems to perform this activity from an Invalid Format (60). Valid Format or Contact 9250400311. ";
						new CommunicationHelper().addMessage("", "", arrLr[0], revertSMS, "", "Pending", "0", "", connection);

					}
					else
					{
						taicketNo = tmpMessage.substring(0, tmpMessage.length());
						comment = "NA";
					}

				}
				else
				{
					if (arrLr != null)
					{
						String revertSMS = "NA";

						revertSMS = "Hi, you seems to perform this activity from an <Unregistered Mobile No./ Invalid Format (60)>. Please use <Registered No./ Valid Format (80)> or Contact 9250400311. ";
						new CommunicationHelper().addMessage("", "", arrLr[0], revertSMS, "", "Pending", "0", "", connection);
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String[] getEmpName(String mobNo, SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		String[] data = new String[3];
		List dataList = null;
		try
		{
			qryString.append(" SELECT emp.id,emp.empName ,emp.mobOne FROM  employee_basic AS emp");
			qryString.append(" WHERE  emp.mobOne='" + mobNo + "' ");

			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1] != null && object[0] != null)
					{
						data[0] = getValueWithNullCheck(object[0]);
						data[1] = getValueWithNullCheck(object[1]);
						data[2] = getValueWithNullCheck(object[2]);
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return data;

	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

}