

package com.Over2Cloud.service.clientdata.integration;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class DischargeAutoClosingTicket {
	
	@SuppressWarnings("unchecked")
	public void closeAutoReopen(SessionFactory clientConnection)
		{
			java.sql.Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			System.out.println("day  "+day);
			
				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					if(DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(),
							"09:45", DateUtil.getCurrentDateUSFormat(),
							DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(),
									DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" ))
					
					{
						con = DriverManager.getConnection(
								"jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol",
								"Dream_1$3");
						System.out.println("redirect  66 Server......");
						
					}
					else if ((day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "20:30", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:30" )))
					{
						
						con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
						System.out.println("redirect  66 Server......");
						
					}else{
						
						con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
						System.out.println("okey  88 Server......");
						
					}
					
					st = con.createStatement();
					
					//System.out.println("task run");
					
					CommonOperInterface cot = new CommonConFactory().createInterface();
					createHISTable(clientConnection,cot);
					StringBuilder qry1=new StringBuilder();
					qry1.append(" select hh.feed_id,hh.bed_refer_no from feedback_status_new as fsn");
					qry1.append(" inner join feedback_hdm_his_mapping hh on hh.feed_id=fsn.id where fsn.feed_registerby='discharge' ");
						qry1.append(" and fsn.status='Pending'");
					List data=cot.executeAllSelectQuery(qry1.toString(), clientConnection);
								
					if(data!=null && data.size()>0)
								{
						
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									String kk = "p";
									Object[] object = (Object[]) iterator.next();
									
									//this is  for  HIS database to make connection as previous
									StringBuilder qry2=new StringBuilder();
									qry2.append("select BED_BOOKING_REF_NO from dreamsol_dis_vw ");// view
									qry2.append(" where BED_BOOKING_REF_NO='"+object[1].toString()+"'");
									// add in where clause bed_reference_number if record found then do nothing 
									//if not found tehn close ticket
									
									rs = st.executeQuery(qry2.toString());
									//kk = object[0].toString();
									while(rs.next())
									{
										System.out.println("FeedId>>>>>>>>>>>>>>."+rs.getString("BED_BOOKING_REF_NO"));
										kk= rs.getString("BED_BOOKING_REF_NO");
									}
									
									System.out.println("kk:::"+kk);
									if(kk.equalsIgnoreCase("p"))
									{
										closeTicketFirst(object[0].toString(),cot,clientConnection);
									}
								
								}
										
								}
					
						
				}
					catch(Exception e){
						e.printStackTrace();
					}finally
					{
						try
						{	if(con!=null)
							con.close();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					}

		}
	
	public boolean closeTicketFirst(String ticketFeedId,CommonOperInterface cot,SessionFactory clientConnection)
	{
	System.out.println("DischargeAutoClosingTicket.closeTicketFirst()");
		boolean statuss=false;
		StringBuilder qry4=new StringBuilder("");
		 qry4.append("update feedback_status_new set status='Resolved' where id='"+ticketFeedId+"' ");
		try{
			
		 boolean status=cot.updateTableColomn(clientConnection, qry4);
		 if(status){
		 /*send message to feed_by*/
		 StringBuilder str=new StringBuilder("");
		 str.append("select fsn.ticket_no, loc.roomno,empbs.empName,subcotegor.subCategoryName ,dept.deptName ," +
	 		"fsn.feed_by_mobno,fsn.open_date,fsn.open_time,fsn.allot_to,empbs.mobOne from feedback_status_new as fsn");
	 str.append(" inner join floor_room_detail as loc on loc.id=fsn.location ");
	 str.append(" inner join feedback_subcategory as subcotegor on subcotegor.id=fsn.subCatg ");
	 str.append(" inner join employee_basic as empbs on empbs.id=fsn.allot_to ");
	 str.append(" inner join department  as dept on dept.id=fsn.to_dept_subdept ");
	 str.append(" where fsn.id='"+ticketFeedId+"' ");
		 System.out.println("qry  :  "+str.toString());
		 String ticket_no="",room_no="",allotto="",sub_cat="",to_dept="", mobile="",open_date="",open_time="",allot_to="",action_duration="";
		 String mobile_allot_to="";//mobile number who is doing job
		 
		 List msg=cot.executeAllSelectQuery(str.toString(), clientConnection);
		
		 if(msg!=null && msg.size()>0)
			 {
			/* for(int i=0;i<=8;i++){
				 System.out.println("AA  :  "+msg.get(i).toString());
			 }*/
			for (Iterator iterator = msg.iterator(); iterator.hasNext();) {
				Object objj[] = (Object[]) iterator.next();
				
				ticket_no=objj[0].toString();
				room_no=objj[1].toString();
				allotto=objj[2].toString();	
				sub_cat=objj[3].toString();
				to_dept=objj[4].toString();
				mobile=objj[5].toString();
				open_date=objj[6].toString();
				open_time=objj[7].toString();
				allot_to=objj[8].toString();
				mobile_allot_to=objj[9].toString();
				
				
				 if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
					{
					 action_duration = DateUtil.time_difference(open_date, open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					}
				 
					
					 
					// code to inser histery after closing ticket
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						InsertDataTable ob=new InsertDataTable();
								ob = new InsertDataTable();
								ob.setColName("feedId");
								ob.setDataName(ticketFeedId);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("status");
								ob.setDataName("Resolved");
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("action_date");
								ob.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("action_time");
								ob.setDataName(DateUtil.getCurrentTime());
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("action_duration");
								ob.setDataName(action_duration);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("action_by");
								ob.setDataName(allot_to);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("action_remark");
								ob.setDataName("Auto Close");
								insertData.add(ob);
								
								cot.insertIntoTable("feedback_status_history", insertData, clientConnection);
					 
				 String levelMsg = "Close: " + ticket_no+ ", Loc: " +room_no + ", Assign To: " + allotto + ", Call Desc: " + sub_cat + " is Closed.";
				/* CH.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "HDM",clientConnection);*/
				 CommunicationHelper CH=new CommunicationHelper();
				// CH.addMessage(allotto, to_dept, mobile, levelMsg, ticket_no, "Pending", "0", "HDM",clientConnection);
				 CH.addMessage(allotto, to_dept, mobile_allot_to, levelMsg, ticket_no, "Pending", "0", "HDM",clientConnection);
				 
			}
			}
		 }
		 statuss=true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return statuss;
	}
	
	
	
	private boolean createHISTable(SessionFactory clientConnection, CommonOperInterface cot)
	{
		TableColumes ob = null;
		List<TableColumes> create = new ArrayList<TableColumes>();

		ob = new TableColumes();
		ob.setColumnname("bed_refer_no");
		ob.setDatatype("varchar (30)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("insert_dateTime_client");
		ob.setDatatype("varchar (50)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("bed_number");
		ob.setDatatype("varchar (10)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("block_type");
		ob.setDatatype("varchar (50)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("username");
		ob.setDatatype("varchar (20)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("open_date");
		ob.setDatatype("varchar (20)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("open_time");
		ob.setDatatype("varchar (15)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("feed_id");
		ob.setDatatype("int (10)");
		ob.setConstraint("default NULL");
		create.add(ob);

		boolean i = cot.createTable22("feedback_hdm_his_mapping", create, clientConnection);
		create.clear();

		return i;
	}
	
	
}