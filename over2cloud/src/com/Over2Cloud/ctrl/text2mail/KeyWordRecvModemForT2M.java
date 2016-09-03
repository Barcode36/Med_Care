package com.Over2Cloud.ctrl.text2mail;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.text2mail.T2mVirtualNoDataReceiver;
import com.opensymphony.xwork2.ActionContext;

public class KeyWordRecvModemForT2M 
{


	private static Log log=LogFactory.getLog(KeyWordRecvModemForT2M.class);
	HttpServletRequest request;
	HttpServletResponse response;
	private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
	private  String uName, exceutive, ecxeId;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private static final MsgMailCommunication MMC = new MsgMailCommunication();

	
	
	public boolean createT2MTable(SessionFactory connection)
	{
		boolean tableCreated=false;
		
		List <TableColumes> tableColumns=new ArrayList<TableColumes>();
	
		TableColumes ob1=new TableColumes();
        ob1.setColumnname("mobNo");
        ob1.setDatatype("varchar(255)");
        ob1.setConstraint("default NULL");
        tableColumns.add(ob1);
        
        TableColumes ob2=new TableColumes();
        ob2=new TableColumes();
        ob2.setColumnname("keyword");
        ob2.setDatatype("text");
        ob2.setConstraint("default NULL");
        tableColumns.add(ob2);
        
        TableColumes ob3=new TableColumes();
        ob3=new TableColumes();
        ob3.setColumnname("date");
        ob3.setDatatype("varchar(255)");
        ob3.setConstraint("default NULL");
        tableColumns.add(ob3);
        
        TableColumes ob4=new TableColumes();
        ob4=new TableColumes();
        ob4.setColumnname("time");
        ob4.setDatatype("varchar(255)");
        ob4.setConstraint("default NULL");
        tableColumns.add(ob4);
        
        TableColumes ob5=new TableColumes();
        ob5=new TableColumes();
        ob5.setColumnname("status");
        ob5.setDatatype("tinyint(1)");
        ob5.setConstraint("default NULL");
        tableColumns.add(ob5);
        
        TableColumes ob6=new TableColumes();
        ob6=new TableColumes();
        ob6.setColumnname("updatedDate");
        ob6.setDatatype("varchar(20)");
        ob6.setConstraint("default NULL");
        tableColumns.add(ob6);
        
        TableColumes ob7=new TableColumes();
        ob7=new TableColumes();
        ob7.setColumnname("updatedTime");
        ob7.setDatatype("varchar(20)");
        ob7.setConstraint("default NULL");
        tableColumns.add(ob7);
        
        if(tableColumns!=null && tableColumns.size()>0)
        {
        	tableCreated=cbt.createTable22("t2mkeyword",tableColumns,connection);
        }
		return tableCreated;
	}
	
	@SuppressWarnings("unused")
	public String addRecvKeywordT2m()
	{
		try
		{
			request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			if(request!=null)
			{
				String reqMobNo=request.getParameter("to");
				String reqKeyword=request.getParameter("text");
				
				if(reqMobNo!=null && reqKeyword!=null)
				{
					SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-7");
					String[] keywordData = getKeywordsDetails(reqKeyword, connection);
					String[] empdata = new KeyWordRecvModemForT2M().getEmpDetailsByMobileNo(reqMobNo,connection);
					String revertSMS="NA";
					

					if(keywordData!=null && keywordData[3].toString().equalsIgnoreCase("Yes"))
					{
					if (empdata != null && keywordData!=null)
					{
						String query = ("SELECT cc.id, cc.userName FROM compliance_contacts AS cc INNER JOIN employee_basic "
								+ "AS emp ON emp.id=cc.emp_id WHERE emp.mobOne='"+reqMobNo+"' AND cc.moduleName='T2M'");
						
						List userName= cbt.executeAllSelectQuery(query, connection);
						if(userName!=null && userName.size()>0)
						{
							for (Iterator iterator = userName.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								setuName(object[1].toString()); 
							}
						}
					MMC.addMessageHR(empdata[0], "", reqMobNo,keywordData[1], "", "Pending", "0", "T2M",connection);
					String keywordDataMapMailIds[]= keywordData[2].trim().split(",");
					for (int i = 0; i < keywordDataMapMailIds.length; i++)
					{	
						String userName1;
						try {
							userName1 = (Cryptography.encrypt(getuName(),DES_ENCRYPTION_KEY));
							System.out.println("userName1  ..." +userName1);
							String query122 ="SELECT id, name  FROM useraccount where userID ='"+userName1+"'";
							System.out.println("query1  ..." +query122);
							List userid= cbt.executeAllSelectQuery(query122, connectionSpace);
							if(userid!=null && userid.size()>0)
							{
								for (Iterator iterator = userid.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									setExceutive(object[0].toString()); 
									
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String s = null;
						String query2= "select id, empName from employee_basic where useraccountid ='"+getExceutive().toString()+"'";
						List data12 = cbt.executeAllSelectQuery(query2.toString(),connection);
						
						if(data12 != null)
						{
						for (Iterator it12 = data12.iterator(); it12.hasNext();)
						{
						Object[] obdata12 =(Object[]) it12.next();
						s=obdata12[1].toString();
						}
						}
					//String	mailBody1 = new T2mVirtualNoDataReceiver().getMailBody1(empdata[0],reqMobNo,s,keywordData[0],DateUtil.getCurrentTimeHourMin(),DateUtil.getCurrentDateIndianFormat());
						
						//new MsgMailCommunication() .addMailHR (empdata[0],"",keywordDataMapMailIds[i],"SMS To Mail Report",mailBody1,"", "Pending",
								//"0","","T2M",connection);
					}
				}
			}
					else if (keywordData != null && keywordData[3].toString().equalsIgnoreCase("No")) 
					{
						if (empdata != null && keywordData != null)
						{
		                     // get Executive
							
							String query = ("SELECT cc.id, cc.userName FROM compliance_contacts AS cc INNER JOIN employee_basic "
									+ "AS emp ON emp.id=cc.emp_id WHERE emp.mobOne='"+reqMobNo+"' AND cc.moduleName='T2M'");
							
							List userName= cbt.executeAllSelectQuery(query, connection);
							if(userName!=null && userName.size()>0)
							{
								for (Iterator iterator = userName.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									setuName(object[1].toString()); 
								}
							}
						MMC.addMessageHR(empdata[0], "", reqMobNo,keywordData[1], "", "Pending", "0", "T2M",connection);
						String keywordDataMapMailIds[]= keywordData[2].trim().split(",");
						for (int i = 0; i < keywordDataMapMailIds.length; i++)
						{	
							
							String userName1;
							try {
								userName1 = (Cryptography.encrypt(getuName(),DES_ENCRYPTION_KEY));
								System.out.println("userName1  ..." +userName1);
								String query122 ="SELECT id, name  FROM useraccount where userID ='"+userName1+"'";
								System.out.println("query1  ..." +query122);
								List userid= cbt.executeAllSelectQuery(query122, connectionSpace);
								if(userid!=null && userid.size()>0)
								{
									for (Iterator iterator = userid.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										setExceutive(object[0].toString()); 
										
									}
								}
							} 
							catch (Exception e) {
								e.printStackTrace();
							}
							String s = null;
							String query2= "select id, empName from employee_basic where useraccountid ='"+getExceutive().toString()+"'";
							List data12 = cbt.executeAllSelectQuery(query2.toString(),connection);
							
							if(data12 != null)
							{
								for (Iterator it12 = data12.iterator(); it12.hasNext();)
								{
										Object[] obdata12 =(Object[]) it12.next();
										s=obdata12[1].toString();
								}
							}
						//String	mailBody1 = new T2mVirtualNoDataReceiver().getMailBody1(empdata[0],reqMobNo,s,keywordData[0],DateUtil.getCurrentTimeHourMin(),DateUtil.getCurrentDateIndianFormat());
							if (keywordDataMapMailIds != null) 
							{
								//new MsgMailCommunication().addMailHR(empdata[0],"", keywordDataMapMailIds[i],"SMS To Mail Report", mailBody1, "",
								//"Pending", "0", "", "T2M", connection);
						}
					}
				}
			}
					else
					{
						System.out.println("There is some error ");
					}
					
				  if (keywordData!=null && empdata == null)
					{
						revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
						new CommunicationHelper().addMessage("","",reqMobNo,revertSMS,"","Pending","0","T2M",connection);
					}
					else if (empdata!=null && keywordData == null)
					{
						revertSMS = "You have sent wrong keyword. Kindly Send Again. ";
						new CommunicationHelper().addMessage("","",reqMobNo,revertSMS,"","Pending","0","T2M",connection);
					}
					else
					{
						System.out.println("There is some error ");
					}
					
					boolean tableCreated=createT2MTable(connection);
					if(tableCreated)
					{
	                		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	                		
	                		InsertDataTable ob1=new InsertDataTable();
	                        ob1.setColName("mobNo");
	                        ob1.setDataName(reqMobNo);
	                        insertData.add(ob1);
	                        
	                        InsertDataTable ob2=new InsertDataTable();
	                        ob2.setColName("keyword");
	                        ob2.setDataName(reqKeyword);
	                        insertData.add(ob2);
	                        
	                        InsertDataTable ob3=new InsertDataTable();
	                        ob3.setColName("date");
	                        ob3.setDataName(DateUtil.getCurrentDateUSFormat());
	                        insertData.add(ob3);
	                        
	                        InsertDataTable ob4=new InsertDataTable();
	                        ob4.setColName("time");
	                        ob4.setDataName(DateUtil.getCurrentTimewithSeconds());
	                        insertData.add(ob4);
	                        
	                        InsertDataTable ob5=new InsertDataTable();
	                        ob5.setColName("status");
	                        ob5.setDataName("0");
	                        insertData.add(ob5);
	                        
	                        tableCreated=cbt.insertIntoTable("t2mkeyword",insertData,connection); 
	                }
					
					boolean tableCreated1 = createPullReportTable(connection);
					if (empdata != null && keywordData!=null)
					{
					if (tableCreated1) 
					{
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						
							InsertDataTable obj = null;
							
							obj = new InsertDataTable();
							obj.setColName("name");
							obj.setDataName(empdata[0]);
							insertData.add(obj);

							obj = new InsertDataTable();
							obj.setColName("mobileNo");
							obj.setDataName(reqMobNo);
							insertData.add(obj);

							obj = new InsertDataTable();
							obj.setColName("incomingKeyword");
							obj.setDataName(reqKeyword);
							insertData.add(obj);
							
							obj = new InsertDataTable();
							obj.setColName("comment");
							obj.setDataName("");
							insertData.add(obj);

					       obj = new InsertDataTable();
							obj.setColName("date");
							obj.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(obj);

							obj = new InsertDataTable();
							obj.setColName("time");
							obj.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData.add(obj);
							
							obj = new InsertDataTable();
							obj.setColName("excecutive");
							String userName = (Cryptography.encrypt(getuName(),DES_ENCRYPTION_KEY));
							String query ="SELECT id, name  FROM useraccount where userID ='"+userName+"'";
							List userid= cbt.executeAllSelectQuery(query, connection);
							if(userid!=null && userid.size()>0)
							{
								for (Iterator iterator = userid.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									setExceutive(object[0].toString()); 
									obj.setDataName(getExceutive());
								}
							}
							insertData.add(obj);
							
							obj = new InsertDataTable();
							obj.setColName("speciality");
							obj.setDataName(empdata[2]);
							insertData.add(obj);

							obj = new InsertDataTable();
							obj.setColName("location");
							obj.setDataName(empdata[3]);
							insertData.add(obj);

							
							obj = new InsertDataTable();
							obj.setColName("autoAck");
							obj.setDataName(keywordData[1]);
							insertData.add(obj);

						cbt.insertIntoTable("pull_report_t2m",insertData,connection);
						insertData.clear();

					
							
					}
				}
			 }
		  }
			return "success";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
	
	
	public boolean createPullReportTable(SessionFactory connection)
	{
		boolean tableCreated=false;
		
		List <TableColumes> tableColumns=new ArrayList<TableColumes>();
	
		
		TableColumes ob=new TableColumes();
        ob.setColumnname("name");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("mobileNo");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("incomingKeyword");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("comment");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("date");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("time");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("excecutive");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("speciality");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("location");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
        
        ob=new TableColumes();
        ob.setColumnname("autoAck");
        ob.setDatatype("varchar(255)");
        ob.setConstraint("default NULL");
        tableColumns.add(ob);
       
        if(tableColumns!=null && tableColumns.size()>0)
        {
        	tableCreated=cbt.createTable22("pull_report_t2m",tableColumns,connection);
        }
		return tableCreated;
	}
	
	 @SuppressWarnings("unchecked")
	 public String[] getEmpDetailsByMobileNo( String mobNo, SessionFactory connectionSpace) {
	  String[] values = null;

	  try {
	   CommonOperInterface coi = new CommonConFactory().createInterface();

	   StringBuilder query = new StringBuilder();
	   query.append("select cn.empName,cn.mobOne,cn.speciality,cn.location from consultants as cn ");
	   query.append(" where cn.mobone='" + mobNo + "' ");
	   System.out.println("query "+query);

	   List dataList = coi.executeAllSelectQuery(query.toString(),
	     connectionSpace);
	   if (dataList != null && dataList.size() > 0) {
	    values = new String[4];
	    Object[] object = (Object[]) dataList.get(0);
	    values[0] = getValueWithNullCheck(object[0]);
	    values[1] = getValueWithNullCheck(object[1]);
	    values[2] = getValueWithNullCheck(object[2]);
	    values[3] = getValueWithNullCheck(object[3]);
	  

	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return values;
	 }
	
	 
	 @SuppressWarnings("unchecked")
	 public String[] getKeywordsDetails( String keyword,
	   SessionFactory connectionSpace) {
	  String[] values = null;

	  try {
	   CommonOperInterface coi = new CommonConFactory().createInterface();

	   StringBuilder query = new StringBuilder();
	   query.append("select ck.keyword,ck.autoReplyMsg,ck.mailIds,ck.ackBySMS from configurekeyword as ck ");
	   query.append(" inner join t2msecondarykeyword as t2m on t2m.secKeyword=ck.keyword");
	   query.append(" where t2m.flag='Active' and ck.keyword='" + keyword + "' ");
	   System.out.println(query);
	   List dataList = coi.executeAllSelectQuery(query.toString(),connectionSpace);
	   if (dataList != null && dataList.size() > 0) {
	    values = new String[4];
	    Object[] object = (Object[]) dataList.get(0);
	    values[0] = getValueWithNullCheck(object[0]);
	    values[1] = getValueWithNullCheck(object[1]);
	    values[2] = getValueWithNullCheck(object[2]);
	    values[3] = getValueWithNullCheck(object[3]);
	  
	  

	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return values;
	 }
	 
	 public String getValueWithNullCheck(Object value) {
		  return (value == null || value.toString().equals("")) ? "NA" : value
		    .toString();
		 }
	 
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			String reqMobNo=request.getParameter("to");
			String reqKeyword=request.getParameter("text");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			String reqMobNo=request.getParameter("to");
			String reqKeyword=request.getParameter("text");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getExceutive() {
		return exceutive;
	}

	public void setExceutive(String exceutive) {
		this.exceutive = exceutive;
	}

	public String getEcxeId() {
		return ecxeId;
	}

	public void setEcxeId(String ecxeId) {
		this.ecxeId = ecxeId;
	}



	
}