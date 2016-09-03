package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackAddViaTab extends ActionSupport
{
	// client Id of the Organization
	private String client;
	private String mobNo;
	private String targetOn;
	
	private Map<String, String> maps = new HashMap<String, String>();
	HttpServletRequest request;
	HttpServletResponse response;
	
	public List<String> clientsProductsNames(SessionFactory connectionSpace)
	{
		List<String> prodCodes=new ArrayList<String>();
		StringBuffer buffer=new StringBuffer("select productCategory from client_product");
		try
		{
			if(connectionSpace!=null)
			{
				List dataList=new createTable().executeAllSelectQuery(buffer.toString(),connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						
						if(object!=null)
						{
							String  codes[]=object.toString().split("#");
							if(codes!=null && codes.length>0)
							{
								for (int i = 0; i < codes.length; i++)
								{
									if(codes[i]!=null && !codes[i].equalsIgnoreCase(""))
									{
										prodCodes.add(codes[i]);
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		return prodCodes;
	}
	
	public String addFeedbackViaMobTab()
	{
		try
		{
			//System.out.println("Hello going to add Feedback Vai Tab Mode"+getClient());
			if(getClient()!=null && getMobNo()!=null & getTargetOn()!=null )
			{
				Map <String,Object> session=ActionContext.getContext().getSession();
				LoginImp ob1=new LoginImp();
				String Accountinfo[]=getClient().trim().split("-");
				if(ob1.isExit(Accountinfo[1].toString(), Accountinfo[0].toString()))
				{
					if(ob1.isClientBlock(Accountinfo[1].toString(), Accountinfo[0].toString()))
					{
						session.put("accountid",Accountinfo[1].toString());
					}
				}
				
				
				// Getting Session Factory On the Basis of Client Id
				SessionFactory connection = new ConnectionHelper().getSessionFactory(getClient().trim());
				if(connection!=null)
				{
					session.put("connectionSpace",connection);
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_feedback_configuration","",connection,"",0,"table_name","feedback_data_configuration");
			       
					System.out.println("statusColNamesize is as :::::::::::::"+statusColName.size());
					
					if(statusColName!=null)
			        {
			            List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
			            boolean userTrue=false;
			            boolean dateTrue=false;
			            boolean timeTrue=false;
			            for(GridDataPropertyView gdp:statusColName)
			            {
			            	TableColumes ob2=new TableColumes();
			                ob2=new TableColumes();
			                ob2.setColumnname(gdp.getColomnName());
			                ob2.setDatatype("varchar(255)");
			                ob2.setConstraint("default NULL");
			                Tablecolumesaaa.add(ob2);
			                if(gdp.getColomnName().equalsIgnoreCase("userName"))
			                {
			                	userTrue=true;
			                }
			                else if(gdp.getColomnName().equalsIgnoreCase("openDate"))
			                {
			                	 dateTrue=true;
			                }
			                else if(gdp.getColomnName().equalsIgnoreCase("openTime"))
			                {
			                	timeTrue=true;
			                }
			                         
			            }
			            boolean tableCreated=cbt.createTable22("feedbackdata",Tablecolumesaaa,connection);
			          //  System.out.println("Table Created"+tableCreated);
			            request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
			            response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
			            if(tableCreated)
			            {
			            	InsertDataTable ob=null;
			            	String satisfaction=null,name=null,mobNo=null,emailId=null,location=null,remarks=null,patId=null,patType=null;
			            	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			            	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			                Collections.sort(requestParameterNames);
			                Iterator it = requestParameterNames.iterator();
			                double d=0.0;
			                while (it.hasNext())
			                {
			                	String paramName = it.next().toString();
			                    String paramValue=request.getParameter(paramName);
			                     
			                	   	if(paramName.equalsIgnoreCase("client"))
			                	   	{
			                	   		
			                	   	}
			                	   	else
			                	   	{
			                	   		ob=new InsertDataTable();
		                                ob.setColName(paramName);
		                                ob.setDataName(paramValue);
		                                insertData.add(ob);
			                	   	}
			                	if(paramName.equalsIgnoreCase("targetOn"))
			                	{
			                		satisfaction=paramValue;
			                	}
			                	if(paramName.equalsIgnoreCase("clientName"))
			                	{
			                		name=paramValue;
			                	}
			                	if(paramName.equalsIgnoreCase("mobNo"))
			                	{
			                		mobNo=paramValue;
			                	}
			                	if(paramName.equalsIgnoreCase("emailId"))
			                	{
			                		emailId=paramValue;
			                	}
			                	if(paramName!=null && paramName.equalsIgnoreCase("centerCode"))
		                        {
		                        	location=paramValue;
		                        }
			                	
			                	if(paramName!=null && paramName.equalsIgnoreCase("patId"))
		                        {
			                		patId=paramValue;
		                        }
			                	//patId
			                	
			                	if(paramName!=null && paramName.equalsIgnoreCase("userName"))
			                	{
			                		session.put("uName",paramValue);
			                	}
			                }
			                
			                 if(dateTrue)
			                 {
			                     ob=new InsertDataTable();
			                     ob.setColName("openDate");
			                     ob.setDataName(DateUtil.getCurrentDateUSFormat());
			                     insertData.add(ob);
			                 }
			                 if(timeTrue)
			                 {
			                     ob=new InsertDataTable();
			                     ob.setColName("openTime");
			                      ob.setDataName(DateUtil.getCurrentTime());
			                     insertData.add(ob);
			                 }
			                     
			                 ob=new InsertDataTable();
			                 ob.setColName("status");
			                 ob.setDataName("Pending");
			                 insertData.add(ob);
			                 
			                 ob=new InsertDataTable();
			                 ob.setColName("mode");
			                 ob.setDataName("Mob App");
			                 insertData.add(ob);
			                 
			                 ob=new InsertDataTable();
			                 ob.setColName("level");
			                 ob.setDataName("Level1");
			                 insertData.add(ob);
			                 
			                 boolean dataInserted=cbt.insertIntoTable("feedbackdata",insertData,connection); 
			                 
			            //     System.out.println("Data Inserted in Feedback Data>>>>>>>>>>>>>>>>>>>>>>>>>>"+dataInserted+"<<< Mob No is as "+mobNo+">>>>Email Id is as <<>"+emailId);
			                 
			                 if(dataInserted)
			                 {
			                	 String orgName=new ReportsConfigurationDao().getOrganizationName(connection);
			                     String posKeyword=new ReportsConfigurationDao().getKeyWord(connection,"FM","0");
			                     String negKeyword=new ReportsConfigurationDao().getKeyWord(connection,"FM","1");
			                     String sendDate=new ReportsConfigurationDao().getSelectedDataFromTable(connection,"select distinct smsDate from feedbacksmsconfig where moduleName='FM' and keyword in ('"+posKeyword+"','"+negKeyword+"')");
			                	 String sendTime=new ReportsConfigurationDao().getSelectedDataFromTable(connection,"select distinct smsTime from feedbacksmsconfig where moduleName='FM' and keyword in ('"+posKeyword+"','"+negKeyword+"')");
			                	 String virtualNo=new ReportsConfigurationDao().getVirtualNo(connection,"FM");
			                	
			                	 // Acknowledgement MAIL/SMS to Client
			                	 String msg="Dear "+name+", thanks for your valuable feedback filled at "+orgName+". Please SMS \"SCHN\" to 9870845678 if you have not filled the form.";
			                	 if(mobNo!=null)
			                	 {
			                		 dataInserted=new MsgMailCommunication().addScheduledMessage(name,"Patient",mobNo,msg,"","0","0","FM",sendDate,sendTime+":00",connection);
			                		// System.out.println("Data Inserted in SMS Table>>>>>>>>>>>>>>>>>>>>>>"+dataInserted);
			                	 }
			                	 
			                	 if(emailId!=null)
			                	 {
			                		 dataInserted=new MsgMailCommunication().addScheduleMail(name,"Patient", emailId,"Feedback Submission Acknowledgement", msg, "","Pending", "0", null,"FM",sendDate,sendTime+":00",connection);
			                		// System.out.println("Data Inserted in Mail Table>>>>>>>>>>>>>>>>>>>>>>"+dataInserted);
			                	 }
			                	 
			                //	 System.out.println("Ticket Has to be opened"+satisfaction);
			                //	 System.out.println("Data Inserted in Feedback Table");
			                	 maps.put("statusCode", "1201");
			                	 maps.put("statusMessage", "success");
			                	 
			                	 if(session!=null)
			                	 {
			                		session.clear(); 
			                	 }
			                	 if(satisfaction!=null && satisfaction.equalsIgnoreCase("No"))
			                	 {
			                	//	 System.out.println("Before Property File Access");
			                		 Properties configProp=new Properties();
			                         String adminDeptName=null;
			                         try
			                         {
			                        	 InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
			                        	 configProp.load(in);
			                        	 adminDeptName=configProp.getProperty("admin");
			                        	 
			                        	 int subCatId=0;
			                             int adminDeptId=0;
			                             if(adminDeptName!=null)
			                             {
			                             	adminDeptId=new EscalationActionControlDao().getAllSingleCounter(connection,"select id from department where deptName='"+adminDeptName+"'");
			                             	subCatId=new EscalationActionControlDao().getAdminDeptSubCatId(connection,String.valueOf(adminDeptId));
			                             }
			                         //    System.out.println(subCatId+"Admin Dept Id is as >>>>>>>>>>>>>>>>>>>"+adminDeptId);
			                             if(adminDeptId!=0 && adminDeptId>0 && subCatId!=0)
			                             {
			                            	 //System.out.println("Ticketssssssssssssssssssssssssssssssssss");
			                            	 OpenTicketForFeedbackModes tab=new OpenTicketForFeedbackModes();
			                            	
			                            	 int feedDataId=new createTable().getMaxId("feedbackdata", connection);
			                            //	 System.out.println(subCatId+" feedDataId is as "+feedDataId);
			                            	 String ticketNo= tab.openTicket("Mob Tab",String.valueOf(subCatId),connection,name,mobNo,emailId,location,feedDataId,satisfaction,patId,patType,"","");
			                                if(ticketNo!=null && !ticketNo.equalsIgnoreCase(""))
			                                {
			                                	 tableCreated=createFeedback_TicketTable(connection);
				                                 if(tableCreated)
				                                 {
				                                	 tableCreated=insertInFeedbackTicket(connection,feedDataId,ticketNo);
				                                	 connection.close();
				                                 }
			                                }
			                             }
			                         }
			                         catch (Exception e)
			                         {
			                        	 e.printStackTrace();
			                         }
			                         
			                	 }
			                 }
			            }
			        }
				}
				else
				{
					return "ERROR";
				}
			}
			else
			{
				return ERROR;
			}
			return SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	public String getLatestTicketNoInFeedbackStatusFeedback(SessionFactory connection)
	{
		String ticketNo=null;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			int id4=cbt.getMaxId("feedback_status", connection);
			StringBuilder queryNew=new StringBuilder("");
	        queryNew.append(" select * from feedback_status where id='"+id4+"'  ");
	        List  dataNew=cbt.executeAllSelectQuery(queryNew.toString(),connection);
	        for(Iterator iterator=dataNew.iterator();iterator.hasNext();)
	        {
	        	Object object[] = (Object[]) iterator.next();
	            ticketNo=(String) object[1];
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ticketNo;
	}
	public boolean insertInFeedbackTicket(SessionFactory connection,int feedDataId,String ticketNo)
	{
		boolean inserted=false;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();
			
			if(ticketNo!=null)
			{
				int feedStatusId=0;
				StringBuffer buffer=new StringBuffer("select id from feedback_status where ticket_no='"+ticketNo+"'");
				List dataList=cbt.executeAllSelectQuery(buffer.toString(), connection);
				if(dataList!=null&& dataList.size()>0 && dataList.get(0)!=null)
				{
					feedStatusId=Integer.parseInt(dataList.get(0).toString());
				}
				
				InsertDataTable ob4=null;
				ob4=new InsertDataTable();
	            ob4.setColName("feedTicketNo");
	            ob4.setDataName(ticketNo);
	            insertHistory.add(ob4);
	            
	            ob4=new InsertDataTable();
	            ob4.setColName("feed_stat_id");
	            ob4.setDataName(String.valueOf(feedStatusId));
	            insertHistory.add(ob4);

	            ob4=new InsertDataTable();
	            ob4.setColName("feed_data_id");
	            ob4.setDataName(String.valueOf(feedDataId));
	            insertHistory.add(ob4);

	            ob4=new InsertDataTable();
	            ob4.setColName("openDate");
	            ob4.setDataName(DateUtil.getCurrentDateUSFormat());
	            insertHistory.add(ob4);
	            ob4=new InsertDataTable();
	            ob4.setColName("openTime");
	            ob4.setDataName(DateUtil.getCurrentTimeHourMin());
	            ob4.setColName("userName");
	            ob4.setDataName("Tab");
	            insertHistory.add(ob4);

	            inserted= cbt.insertIntoTable("feedback_ticket",insertHistory,connection);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return inserted;
	}
	
	
	public boolean createFeedback_TicketTable(SessionFactory connection)
	{
		boolean created=false;
		try
		{
			List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			TableColumes ob1=new TableColumes();
	        ob1=new TableColumes();
			ob1.setColumnname("feedTicketNo");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
	        
			TableColumes ob2=new TableColumes();
			ob2=new TableColumes();
		 	ob2.setColumnname("feed_stat_id");
		 	ob2.setDatatype("varchar(255)");
		 	ob2.setConstraint("default NULL");
		 	Tablecolumesaaa.add(ob2);
		 	
		 	TableColumes ob3=new TableColumes();
			ob3=new TableColumes();
		 	ob3.setColumnname("feed_data_id");
		 	ob3.setDatatype("varchar(255)");
		 	ob3.setConstraint("default NULL");
		 	Tablecolumesaaa.add(ob3);
		 	
		 	TableColumes tb4=new TableColumes();
			tb4=new TableColumes();
		 	tb4.setColumnname("openDate");
		 	tb4.setDatatype("varchar(255)");
		 	tb4.setConstraint("default NULL");
		 	Tablecolumesaaa.add(tb4);
		 	
		 	TableColumes tb5=new TableColumes();
			tb5=new TableColumes();
		 	tb5.setColumnname("openTime");
		 	tb5.setDatatype("varchar(255)");
		 	tb5.setConstraint("default NULL");
		 	Tablecolumesaaa.add(tb5);
		 	
		 	TableColumes tb6=new TableColumes();
			tb6=new TableColumes();
		 	tb6.setColumnname("userName");
		 	tb6.setDatatype("varchar(255)");
		 	tb6.setConstraint("default NULL");
		 	Tablecolumesaaa.add(tb6);
		 	
		 	TableColumes tb7=new TableColumes();
			tb7=new TableColumes();
		 	tb7.setColumnname("deptFlag");
		 	tb7.setDatatype("varchar(255)");
		 	tb7.setConstraint("default NULL");
		 	Tablecolumesaaa.add(tb7);
		 	
		 	TableColumes tb8=new TableColumes();
			tb8=new TableColumes();
		 	tb8.setColumnname("adminFlag");
		 	tb8.setDatatype("varchar(255)");
		 	tb8.setConstraint("default NULL");
		 	Tablecolumesaaa.add(tb8);
		 	
		 	TableColumes tb9=new TableColumes();
			tb9=new TableColumes();
		 	tb9.setColumnname("dept_feed_stat_id");
		 	tb9.setDatatype("varchar(255)");
		 	tb9.setConstraint("default NULL");
		 	Tablecolumesaaa.add(tb9);
		 	
		 	created=cbt.createTable22("feedback_ticket",Tablecolumesaaa,connection);
		}
	 	catch(Exception e)
	 	{
	 		e.printStackTrace();
	 		created=false;
	 	}
		return created;
	}
	public Map<String, String> getMaps() {
		return maps;
	}
	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public String getTargetOn() {
		return targetOn;
	}
	public void setTargetOn(String targetOn) {
		this.targetOn = targetOn;
	}
}