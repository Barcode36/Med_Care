package com.Over2Cloud.ctrl.text2mail;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.service.KeyWordRecvHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.text2mail.KeyWordRecvModemForT2M;

public class T2mVirtualNoDataReceiver

{
	private static FeedbackPojo feedPojo = new FeedbackPojo();
	private String uName, exceutive, ecxeId;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private static final MsgMailCommunication MMC = new MsgMailCommunication();

	public String[] getClientAccountData(String keyWord)
	{
		String AccountDetails = null;
		String[] keyWords = null;

		try
		{
			// Creating URL
			// String kky="DS";
			String url = "http://115.112.237.111:8080/VMMServer/virtualPoll?Keyword=" + keyWord;
			System.out.println(url);
			if (url != null)
			{
				URL codedURL = new URL(url.toString());
				HttpURLConnection HURLC = (HttpURLConnection) codedURL.openConnection();
				HURLC.setRequestMethod("GET");
				HURLC.setDoOutput(true);
				HURLC.setDoInput(true);
				HURLC.setUseCaches(false);
				HURLC.connect();
				String getkkyy = HURLC.getHeaderField("1708");
				System.out.println(" getkkyy    " + getkkyy);
				AccountDetails = HURLC.getHeaderField("1708");// "9250400311_EG 16:00*TEST_2014-12-22";
				// HURLC.getHeaderField("1708");
				HURLC.disconnect();
				if (AccountDetails != null)
				{
					keyWords = AccountDetails.split("_");

				}
			}
		} catch (Exception E)
		{
			E.printStackTrace();
		}
		return keyWords;
	}

	public void rescvSMS(String mob, String msg)
	{
		int revertCode = 0;
		String URL = null;
		msg = msg.replaceAll("\\s", "");

		String encodedmsg = URLEncoder.encode(msg.trim());

		try
		{

			StringBuffer url = new StringBuffer();
			URL = "http://172.17.17.126:8080/T2MBLK/info?msg=" + encodedmsg + "&msisdn=" + mob.trim();

			url.append(URL);
			System.out.println("URL " + url);
			URL codedURL = new URL(url.toString());
			HttpURLConnection HURLC = (HttpURLConnection) codedURL.openConnection();
			HURLC.connect();
			System.out.println("connect ");
			revertCode = HURLC.getResponseCode();
			System.out.println("revertCode1 " + revertCode);
			HURLC.disconnect();

			// print result
			System.out.println("URL " + URL);
			System.out.println("revertCode " + revertCode);

		} catch (Exception E)
		{
			URL = null;
			E.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	public void getVirtualNoData(SessionFactory connection)
	{
		try
		{
			System.out.println("get call");
			String arr[] =  getClientAccountData("BLK");//getClientAccountData("BLK");//getClientAccountData("BLK");//{ "9810114031", "Y"};
																		// 
																		// 
																		// };//
																		// //{"9250400311_KW TEST_2015-02-19"
System.out.println(" arr[]arr[]   "+arr.toString());
			String[] keyword = new String[2];
			String sub = null;
			String comment = "NA";

			if (arr != null)
			{
				sub = arr[0];
				// keyword = sub.sub
				keyword = arr[1].split(" ");
				if (keyword[0].equalsIgnoreCase("Y") || keyword[0].equalsIgnoreCase("N"))
				{
					System.out.println("BLK y or n");
					String chkKey = null;
					if (keyword[0].equalsIgnoreCase("N"))
					{
						chkKey = "1";
					} else
					{
						chkKey = "0";
					}

					feedPojo = new KeyWordRecvHelper().getFullDetailsRecvedKeyWord(sub, keyword[0].toString(), connection);
					new KeyWordRecvHelper().addDataOfFeedbackReceived(feedPojo, connection, chkKey);
				}
				// comment = keyword[1];
				else
				{
					 //rescvSMS(arr[0], arr[1]);

					if (arr[0].trim().indexOf("_") > 0)
					{

					} /*
					 * else { keyword[0] = arr[1].trim().toUpperCase(); }
					 */

					String[] keywordData = new KeyWordRecvModemForT2M().getKeywordsDetails(keyword[0], connection);
					String[] empdata = new KeyWordRecvModemForT2M().getEmpDetailsByMobileNo(sub, connection);
					String revertSMS = "NA";

					if (keywordData != null)
					{
						if (empdata != null)
						{
							// get Consultant Id

							String query = ("SELECT cc.id, cc.userName FROM compliance_contacts AS cc INNER JOIN employee_basic " + "AS emp ON emp.id=cc.emp_id WHERE emp.mobOne='" + sub + "' AND cc.moduleName='REF'");
							List userName = cbt.executeAllSelectQuery(query, connection);
							if (userName != null && userName.size() > 0)
							{
								for (Iterator iterator = userName.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();

									setuName(object[0].toString());
								}
							}
							MMC.addMessageHR(empdata[0], "", sub, keywordData[1], "", "Pending", "0", "T2M", connection);
							String keywordDataMapMailIds[] = keywordData[2].trim().split(",");

							for (int i = 0; i < keywordDataMapMailIds.length; i++)
							{
								String query1 = "select cm.mapped_with, cc.userName from contact_mapping_detail as cm inner join compliance_contacts as cc on cc.id=cm.mapped_with	where cm.contact_id='" + getuName() + "'";
								List userid = cbt.executeAllSelectQuery(query1, connection);
								if (userid != null && userid.size() > 0)
								{
									for (Iterator iterator = userid.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										setExceutive(object[1].toString());
									}
								}

								String s = null;
								// get executive name

								String query2 = "select id, empName from employee_basic where useraccountid ='" + getExceutive().toString() + "'";

								List data12 = cbt.executeAllSelectQuery(query2.toString(), connection);

								if (data12 != null)
								{
									for (Iterator it12 = data12.iterator(); it12.hasNext();)
									{
										Object[] obdata12 = (Object[]) it12.next();

										s = obdata12[1].toString();

									}
								}

								String executiveFinal = "NA";
								String queryExe = "select distinct emp.empName, emp.id from employee_basic as emp inner join compliance_contacts as cc on emp.id=cc.emp_id where cc.userName='" + getExceutive().toString() + "'";
								List data125 = cbt.executeAllSelectQuery(queryExe.toString(), connection);

								if (data125 != null)
								{
									for (Iterator it12 = data125.iterator(); it12.hasNext();)
									{
										Object[] obdata12 = (Object[]) it12.next();

										executiveFinal = obdata12[0].toString();

									}
								}

								String mailBody1 = getMailBody1(empdata[0], sub, executiveFinal, keywordData[0], DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateIndianFormat(), arr[1].toString());
								String fullKeyword = "BLK" + keyword[0].toString();
								new MsgMailCommunication().addMailHR(empdata[0], "", keywordDataMapMailIds[i], "SMS To Mail Report for keyword " + fullKeyword + "", mailBody1, "", "Pending", "0", "", "T2M", connection);
							}

						}
					}

					else if (keywordData != null && keywordData[3].toString().equalsIgnoreCase("No"))
					{
						if (empdata != null && keywordData != null)
						{
							// manab change for get Executive

							String query = ("SELECT cc.id, cc.userName FROM compliance_contacts AS cc INNER JOIN employee_basic " + "AS emp ON emp.id=cc.emp_id WHERE emp.mobOne='" + sub + "' AND cc.moduleName='T2M'");
							List userName = cbt.executeAllSelectQuery(query, connection);
							if (userName != null && userName.size() > 0)
							{
								for (Iterator iterator = userName.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									setuName(object[0].toString());
								}
							}

							String keywordDataMapMailIds[] = keywordData[2].trim().split(",");
							for (int i = 0; i < keywordDataMapMailIds.length; i++)
							{

								String query1 = "select cm.mapped_with, cc.userName from contact_mapping_detail as cm inner join compliance_contacts as cc on cc.id=cm.mapped_with	where cm.contact_id='" + getuName() + "'";
								List userid = cbt.executeAllSelectQuery(query1, connection);
								if (userid != null && userid.size() > 0)
								{
									for (Iterator iterator = userid.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										setExceutive(object[1].toString());
									}
								}

								String executiveFinal = "NA";
								String queryExe = "select distinct emp.empName, emp.id from employee_basic as emp inner join compliance_contacts as cc on emp.id=cc.emp_id where cc.userName='" + getExceutive().toString() + "'";
								List data125 = cbt.executeAllSelectQuery(queryExe.toString(), connection);

								if (data125 != null)
								{
									for (Iterator it12 = data125.iterator(); it12.hasNext();)
									{
										Object[] obdata12 = (Object[]) it12.next();

										executiveFinal = obdata12[0].toString();

									}
								}
								System.out.println("massage body : "+arr[1].toString());
								String mailBody1 = getMailBody1(empdata[0], sub, executiveFinal, keywordData[0], DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateIndianFormat(), arr[1].toString());
								String fullKeyword = "BLK" + keyword[0].toString();
								if (keywordDataMapMailIds != null)
								{
									new MsgMailCommunication().addMailHR(empdata[0], "", keywordDataMapMailIds[i], "SMS To Mail Report for keyword " + fullKeyword + "", mailBody1, "", "Pending", "0", "", "T2M", connection);
								}
							}
						}
					}

					else
					{
						System.out.println("last call ");
					}
					if (keywordData != null && empdata == null)
					{

						revertSMS = "Hi, thanks for your information. We have updated the same. Wish you all the best. Regards.";
						new CommunicationHelper().addMessage("", "", sub, revertSMS, "", "Pending", "0", "T2M", connection);
					}

					else if (empdata != null && keywordData == null)
					{
						revertSMS = "You have sent wrong keyword. Kindly Send Again. ";
						new CommunicationHelper().addMessage("", "", sub, revertSMS, "", "Pending", "0", "T2M", connection);
					}

					else
					{
					}

					boolean tableCreated = new KeyWordRecvModemForT2M().createT2MTable(connection);
					if (tableCreated)
					{
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						if (arr != null)
						{
							for (int i = 0; i < arr.length; i++)
							{
								insertData.clear();

								InsertDataTable ob1 = new InsertDataTable();
								ob1.setColName("mobNo");
								ob1.setDataName(sub);
								insertData.add(ob1);

								InsertDataTable ob2 = new InsertDataTable();
								ob2.setColName("keyword");
								ob2.setDataName(sub);
								insertData.add(ob2);

								InsertDataTable ob3 = new InsertDataTable();
								ob3.setColName("date");
								ob3.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob3);

								InsertDataTable ob4 = new InsertDataTable();
								ob4.setColName("time");
								ob4.setDataName(DateUtil.getCurrentTimeHourMin());
								insertData.add(ob4);

								InsertDataTable ob5 = new InsertDataTable();
								ob5.setColName("status");
								ob5.setDataName("0");
								insertData.add(ob5);

								boolean exists = new HelpdeskUniversalHelper().isExist("t2mkeyword", "mobNo", sub, "date", DateUtil.getCurrentDateUSFormat(), "", "", connection);
								if (!exists)
								{
									tableCreated = cbt.insertIntoTable("t2mkeyword", insertData, connection);

								}
							}
						}

					} else
					{
					}
					insertIntoTable(empdata, connection, sub, keyword[0], arr[1].toString());
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getMailBody1(String name, String mobNo, String executive, String keyword, String currentTimeHourMin, String currentDateIndianFormat, String comment)
	{
		StringBuilder mailText = new StringBuilder("");

		mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>" + "<font color='#000000' size='2'>Hi,</font></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
				+ "<font color='#000000' size='2'> Kindly find the following auto generated information mapped for you via your E-mail ID</font></td></tr></table>" + "<br>" + "<font color='990066' size='2'><center><h2><b> SMS To Mail Report</b> </h2></center></font></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>" + "<tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Name:</font></td> " + "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ name
				+ "&nbsp;</font></td>"

				+ "</tr><tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Mobile No.:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ mobNo
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>PAN Card No.:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ "NA"
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Town:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ "NA"
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Executive Name :</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ executive
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Message:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ comment
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Time:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;"
				+ currentTimeHourMin
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='990066'><td  align='left' width='20%'><font style='font-weight:bold;color:#FFFFFF;'>Date :</font></td>" + "<td  align='left' width='30%'><font style='font-weight:bold;color:#FFFFFF;'>&nbsp;" + currentDateIndianFormat + "&nbsp;</font></td></tr>"

				+ "   </table>");
		mailText.append("<tr><td><br></td></tr>");
		mailText.append("<tr><td><br></td></tr>");
		mailText.append("<tr><td>Hope this information was useful for you.</td></tr>");

		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
		mailText.append("<tr><td>IT Team.</td></tr>");

		mailText.append("<br><b>___________________________________</b>");
		mailText.append("<br>");
		mailText.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then. Please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to support@dreamsol.biz</FONT>");
		return mailText.toString();

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void insertIntoTable(String[] empdata, SessionFactory connection, String sub, String keyword, String comment) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{ 
		try{
		String query = ("SELECT cc.id, cc.userName FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE emp.mobOne='" + sub + "' AND cc.moduleName='REF'");
		List userName = cbt.executeAllSelectQuery(query, connection);
		if (userName != null && userName.size() > 0)
		{
			for (Iterator iterator = userName.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();

				setuName(object[0].toString());
			}
		}
		
		System.out.println(" jut enter11 "+getuName());
		
		if(!keyword.equalsIgnoreCase("TEST"))
		{
			System.out.println(" jut enter ");
		//boolean tableCreated1 = new KeyWordRecvModemForT2M().createPullReportTable(connection);
		String[] keywordData = new KeyWordRecvModemForT2M().getKeywordsDetails(keyword, connection);
		String[] empdata1 = new KeyWordRecvModemForT2M().getEmpDetailsByMobileNo(sub, connection);
		System.out.println(" jut empdata "+empdata);
		System.out.println(" jut sub "+sub);
		System.out.println(" jut keyword "+keyword);
		System.out.println(" jut comment "+comment);
		System.out.println(" jut enter "+empdata1[0]+"   "+empdata1[1]+"   "+empdata1[2]+"  "+empdata1[3]);
		if (empdata1 != null && keywordData != null)
		{
			
				
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable obj = null;

				obj = new InsertDataTable();
				obj.setColName("name");
				obj.setDataName(empdata1[0]);
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("mobileNo");
				obj.setDataName(sub);
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("incomingKeyword");
				obj.setDataName(keywordData[0]);
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("comment");
				obj.setDataName(comment);
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("date");
				obj.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("time");
				obj.setDataName(DateUtil.getCurrentTimewithSeconds());
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("excecutive");
				String query1 = "select cm.mapped_with, cc.emp_id from contact_mapping_detail as cm inner join compliance_contacts as cc on cc.id=cm.mapped_with	where cm.contact_id='" + getuName() + "'";
				System.out.println("query1 "+query1);
				List userid = cbt.executeAllSelectQuery(query1, connection);
				if (userid != null && userid.size() > 0)
				{
					for (Iterator iterator = userid.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();

						setExceutive(object[1].toString());
					}
				}
				obj.setDataName(getExceutive());
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("speciality");
				obj.setDataName(empdata1[2]);
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("location");
				obj.setDataName(empdata1[3]);
				insertData.add(obj);

				obj = new InsertDataTable();
				obj.setColName("autoAck");
				obj.setDataName(comment);
				insertData.add(obj);
				
				obj = new InsertDataTable();
				obj.setColName("history");
				obj.setDataName("referred");
				insertData.add(obj);

				cbt.insertIntoTable("pull_report_t2m", insertData, connection);
				insertData.clear();

			

		} else
		{

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable obj = null;

			obj = new InsertDataTable();
			obj.setColName("name");
			obj.setDataName("Un mapped");
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("mobileNo");
			obj.setDataName(sub);
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("incomingKeyword");
			obj.setDataName(keywordData[0]);
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("comment");
			obj.setDataName(comment);
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("date");
			obj.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("time");
			obj.setDataName(DateUtil.getCurrentTimewithSeconds());
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("excecutive");

			obj.setDataName("Un Mapped");
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("speciality");
			obj.setDataName("Un Mapped");
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("location");
			obj.setDataName("Un Mapped");
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("autoAck");
			obj.setDataName(comment);
			insertData.add(obj);

			obj = new InsertDataTable();
			obj.setColName("history");
			obj.setDataName("referred");
			insertData.add(obj);
			/*if(!keywordData[0].toString().equalsIgnoreCase("test")){
			cbt.insertIntoTable("pull_report_t2m", insertData, connection);
			}*/
			
			cbt.insertIntoTable("pull_report_t2m", insertData, connection);
			insertData.clear();
			

			String keywordDataMapMailIds[] = keywordData[2].trim().split(",");
			for (int i = 0; i < keywordDataMapMailIds.length; i++)
			{
				String mailBody1 = getMailBody1("NA", sub, "NA", keywordData[0], DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateIndianFormat(), comment);
				String fullKeyword = "BLK" + keyword;
				new MsgMailCommunication().addMailHR("Un Mapped", "", keywordDataMapMailIds[i], "SMS To Mail Report for keyword " + fullKeyword + "", mailBody1, "", "Pending", "0", "", "T2M", connection);
			}

		}
	}
	}catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
	}

	public static void main(String sddss[])
	{

	}

	public String getuName()
	{
		return uName;
	}

	public void setuName(String uName)
	{
		this.uName = uName;
	}

	public String getExceutive()
	{
		return exceutive;
	}

	public void setExceutive(String exceutive)
	{
		this.exceutive = exceutive;
	}

	public String getEcxeId()
	{
		return ecxeId;
	}

	public void setEcxeId(String ecxeId)
	{
		this.ecxeId = ecxeId;
	}

}