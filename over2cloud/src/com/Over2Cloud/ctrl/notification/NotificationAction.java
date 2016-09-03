package com.Over2Cloud.ctrl.notification;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.hr.EmployeeHistoryCtrl;
import com.Over2Cloud.modal.db.commom.NotificationPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NotificationAction extends ActionSupport{

	static final Log log = LogFactory.getLog(EmployeeHistoryCtrl.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	/**
	 * var names for showing counter of data
	 */
	private int clientActivity=0;
	private int associateActivity=0;
	private int krActivity=0;
	private int orgNotification=0;
	private int viaCall_Count=0;
	private int viaOnline_Count=0;
	private int sn_Count=0;
	private int hp_Count=0;
	private String moduleFlagVar;
	private List<NotificationPojo>notificationData;
	private String date1;
	private String date2;
	private String date3;
	private String status;
	private String viaFrom;
	private String limitFlag;
	private String serverTime;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase("")){
				return LOGIN;	
			}
			serverTime=DateUtil.getCurrentTimeHourMin();
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTimeHourMin()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getCountOfNotification()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			    HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List  dataCount=null;
					try
					{
			            String dept_id ="",accType="",empname="";
			            // List of holding details for logged in user
						List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "");
						if (empData!=null && empData.size()>0)
						{
							for (Iterator iterator = empData.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								empname = object[0].toString();
								dept_id = object[3].toString();
								accType=object[7].toString();
							}
						}
						// Block for getting the data status 'Pending' and Via From mode is call or online
						if (dept_id!=null && !dept_id.equals(""))
						{
								// Feedback Data at sub department level
								if (accType!=null && !accType.equals("") && accType.equals("M"))
								{
									query.append("select count(*) from feedback_status_new  where  status='Pending'");
								}
								else if (accType!=null && !accType.equals("") && accType.equals("H")) {
									query.append("select count(*) from feedback_status_new  where  status='Pending' and to_dept_subdept in "+dept_id+" ");
								}
								else if (accType!=null && !accType.equals("") && accType.equals("N")) {
									query.append("select count(*) from feedback_status_new  where  status='Pending'  and to_dept_subdept in "+dept_id+" and feed_by='"+empname+"' ");
								}
						 }
						 dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						 if(dataCount!=null)
						 {
							    for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
							    {
								   Object object = (Object) iterator.next();
								 
									viaOnline_Count=Integer.parseInt(object.toString());
							    }
						}
						query.delete(0, query.length());
						if(dataCount!=null && dataCount.size()>0)
						{
							dataCount.clear();
						}
						
						if (dept_id!=null && !dept_id.equals(""))
						{
							query.append("select count(*),status from feedback_status_new  where status in ('Snooze', 'High Priority') and to_dept_subdept in ("+dept_id+") group by(status) order by status asc");
						}
				//		 dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(dataCount!=null && dataCount.size()>0)
						{
							BigInteger count=new BigInteger("3");
						    for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						    {
							   Object[] object = (Object[]) iterator.next();
							   if (object[1]!=null && object[1].toString().equalsIgnoreCase("Snooze")) 
							   {
								   count=(BigInteger)object[0];
								   sn_Count=count.intValue();
							   }
							   else if (object[1]!=null && object[1].toString().equalsIgnoreCase("High Priority"))
							   {
								   count=(BigInteger)object[0];
								   hp_Count=count.intValue();
							   }
						    }
						}
						query.delete(0, query.length());
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					/*try
					{
						query.delete(0, query.length());
						query.append("select count(*) from client_takeaction where readFlag=0 and userName='"+userName+"'");
						dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if(dataCount!=null)
							{
								BigInteger count=new BigInteger("3");
								for(Iterator it=dataCount.iterator(); it.hasNext();)
								{
									 Object obdata=(Object)it.next();
									 count=(BigInteger)obdata;
								}
								clientActivity=count.intValue();
							}
					}
					catch(Exception e)
					{
						
					}*/
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getCountOfNotification of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 return ERROR;
		}
		return SUCCESS;
	}

	// Method for getting data of feedback to take action
	@SuppressWarnings("unchecked")
	public String selectData4Feedback()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			/**
			 * moduleFlagVar for identifying from where request of notification view is coming
			 * moduleFlagVar==1 Via Call Mode
			 * moduleFlagVar==2 Via Online Mode
			 * moduleFlagVar==3 Snooze Mode
			 * moduleFlagVar==4 High Priority Mode
			 */
			notificationData=new ArrayList<NotificationPojo>();
			HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
			String deptLevel = (String)session.get("userDeptLevel");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			String dept_subdept_id ="",accType="",empname="";
			
			List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					empname= object[0].toString();
					dept_subdept_id = object[3].toString();
					accType= object[7].toString();
				}
			}
				if (dept_subdept_id!=null && !dept_subdept_id.equals(""))
				 {
					// Feedback Data at sub department level
					if (deptLevel.equals("2"))
					 {
						
						query.append(" select feedback.id,feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
	            		query.append(" feedback.feed_brief,feedback.open_date,feedback.open_time,subcatg.subCategoryName,room.roomno,feedback.escalation_date,");
	            		query.append(" feedback.escalation_time,emp.empName");
	            		query.append(" from feedback_status_new as feedback");
	            		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	            		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
	            		query.append(" inner join floor_room_detail room on feedback.location = room.id");
						if(getModuleFlagVar().equalsIgnoreCase("1") || getModuleFlagVar().equalsIgnoreCase("2"))//for Via Call or Online
						{
							if (accType!=null && !accType.equals("") && accType.equals("M"))
							{
								query.append(" where feedback.status='"+status+"'  ");
							}
							else if (accType!=null && !accType.equals("") && accType.equals("H")) {
								query.append(" where feedback.status='"+status+"'  and feedback.to_dept_subdept in "+dept_subdept_id+" ");
							
							}
							else if (accType!=null && !accType.equals("") && accType.equals("N")) {
								query.append(" where feedback.status='"+status+"'  and feed_by='"+empname+"' ");
							}
							//System.out.println("Selected Data   "+query.toString());
						}
						/*else if(getModuleFlagVar().equalsIgnoreCase("2"))//for via online
						{
							query.append(" where feedback.status='"+status+"' and feedback.deptHierarchy='"+deptLevel+"' and feedback.to_dept_subdept in "+subDeptList.toString().replace("[", "(").replace("]", ")")+" and feedback.via_from='"+viaFrom+"'");
						}*/
						/*else if(getModuleFlagVar().equalsIgnoreCase("3") || getModuleFlagVar().equalsIgnoreCase("4"))//for Snooze Or High Priority Tickets
						{
							query.append(" where feedback.status='"+status+"' and feedback.deptHierarchy='"+deptLevel+"' and feedback.to_dept_subdept in "+subDeptList.toString().replace("[", "(").replace("]", ")")+"");
						}*/
						/*else if(getModuleFlagVar().equalsIgnoreCase("4"))//for High Priority Tickets
						{
							query.append(" where feedback.status='"+status+"' and feedback.deptHierarchy='"+deptLevel+"' and feedback.to_dept_subdept in "+subDeptList.toString().replace("[", "(").replace("]", ")")+"");
						}*/
					  }
					// Feedback Data at Department Level
					if (limitFlag.equals("1")) {
						query.append(" limit 0,5");
					}
				 }
				List  feedData=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(feedData!=null)
					{
						for(Iterator it=feedData.iterator(); it.hasNext();)
						{
							Object[] object=(Object[])it.next();
							NotificationPojo np=new NotificationPojo();
							np.setId((Integer)object[0]);
							np.setTicket_no(object[1].toString());
							np.setFeed_by(object[2].toString());
							np.setFeed_by_mobno(object[3].toString());
							np.setFeed_by_emailid(object[4].toString());
							np.setFeed_brief(object[5].toString());
							np.setOpen_date(DateUtil.convertDateToIndianFormat(object[6].toString()));
							np.setOpen_time(object[7].toString());
							np.setSubcatg(object[8].toString());
							np.setLocation(object[9].toString());
							np.setEscalation_date(object[10].toString());
							np.setEscalation_time(object[11].toString());
							np.setAllot_to(object[12].toString());
							if (status.equalsIgnoreCase("Pending")) {
								np.setTaskBrief("Feedback Alert: Ticket No: "+object[1].toString()+", To Be Closed By: "+DateUtil.convertDateToIndianFormat(object[10].toString())+","+object[11].toString()+ ", Reg. By: "+ DateUtil.makeTitle(object[2].toString())+", Location: "+object[9].toString()+" , Brief: "+ object[5].toString()+ ".");
							}
							else if (status.equalsIgnoreCase("Snooze")) {
								np.setTaskBrief("Feedback Alert: Ticket No: "+object[1].toString()+", To Be Closed By: "+DateUtil.convertDateToIndianFormat(object[10].toString())+","+object[11].toString()+ ", Reg. By: "+ DateUtil.makeTitle(object[2].toString())+", Location: "+object[9].toString()+" , Brief: "+ object[5].toString()+ ".");
							}
							else if (status.equalsIgnoreCase("High Priority")) {
								np.setTaskBrief("Feedback Alert: Ticket No: "+object[1].toString()+", To Be Closed By: "+DateUtil.convertDateToIndianFormat(object[10].toString())+","+object[11].toString()+ ", Reg. By: "+ DateUtil.makeTitle(object[2].toString())+", Location: "+object[9].toString()+" , Brief: "+ object[5].toString()+ ".");
							}
							notificationData.add(np);
						}
					}
		        }
		catch(Exception e)
		{
			 e.printStackTrace();
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	
	public int getClientActivity() {
		return clientActivity;
	}


	public void setClientActivity(int clientActivity) {
		this.clientActivity = clientActivity;
	}


	public int getAssociateActivity() {
		return associateActivity;
	}


	public void setAssociateActivity(int associateActivity) {
		this.associateActivity = associateActivity;
	}


	public int getKrActivity() {
		return krActivity;
	}


	public void setKrActivity(int krActivity) {
		this.krActivity = krActivity;
	}


	public String getModuleFlagVar() {
		return moduleFlagVar;
	}


	public void setModuleFlagVar(String moduleFlagVar) {
		this.moduleFlagVar = moduleFlagVar;
	}


	public List<NotificationPojo> getNotificationData() {
		return notificationData;
	}


	public void setNotificationData(List<NotificationPojo> notificationData) {
		this.notificationData = notificationData;
	}


	public int getOrgNotification() {
		return orgNotification;
	}


	public void setOrgNotification(int orgNotification) {
		this.orgNotification = orgNotification;
	}

	public String getDate1() {
		return date1;
	}


	public void setDate1(String date1) {
		this.date1 = date1;
	}


	public String getDate2() {
		return date2;
	}


	public void setDate2(String date2) {
		this.date2 = date2;
	}


	public String getDate3() {
		return date3;
	}


	public void setDate3(String date3) {
		this.date3 = date3;
	}


	public int getViaCall_Count() {
		return viaCall_Count;
	}


	public void setViaCall_Count(int viaCall_Count) {
		this.viaCall_Count = viaCall_Count;
	}


	public int getViaOnline_Count() {
		return viaOnline_Count;
	}


	public void setViaOnline_Count(int viaOnline_Count) {
		this.viaOnline_Count = viaOnline_Count;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getViaFrom() {
		return viaFrom;
	}


	public void setViaFrom(String viaFrom) {
		this.viaFrom = viaFrom;
	}


	public String getLimitFlag() {
		return limitFlag;
	}


	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}


	public int getSn_Count() {
		return sn_Count;
	}


	public void setSn_Count(int sn_Count) {
		this.sn_Count = sn_Count;
	}


	public int getHp_Count() {
		return hp_Count;
	}


	public void setHp_Count(int hp_Count) {
		this.hp_Count = hp_Count;
	}


	public String getServerTime() {
		return serverTime;
	}


	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
}