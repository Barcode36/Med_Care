package com.Over2Cloud.ctrl.feedback.common;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackUniversalHelper  extends ActionSupport{

	private AtomicInteger AN=new AtomicInteger();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private static int  ANN=0;
	public static void setAN(int an)
	{
	ANN = an;
	}
	
	public List<String> getLoggedInEmpForDept(String empId, String deptId, String module, SessionFactory connectionSpace)
	{
	List<String> forDeptId = new ArrayList<String>();
	StringBuffer buffer = new StringBuffer("select forDept_id from compliance_contacts where emp_id='" + empId + "' and moduleName='" + module + "' and work_status='3'");
	System.out.println("Hellop" + buffer);
	List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	if (object != null)
	{
	forDeptId.add(object.toString());
	}
	}
	}

	return forDeptId;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static String getTicketNo(String deptid,String moduleName,SessionFactory connectionSpace)
	 {
	String ticketno="NA",ticket_type="",ticket_series="",prefix="",sufix="",ticket_no="";
	List ticketSeries = new ArrayList();
	List deptTicketSeries =new ArrayList();
	try {
	   // Code for getting the Ticket Type from table
	  // ticketSeries = new HelpdeskUniversalHelper().getDataFromTable("ticket_series_conf", null, null, null, null, connectionSpace);
	   ticketSeries = new HelpdeskUniversalHelper().getAllData("ticket_series_conf","moduleName",moduleName, "", "",connectionSpace);
	   if (ticketSeries!=null && ticketSeries.size()==1) {
	for (Iterator iterator = ticketSeries.iterator(); iterator .hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	ticket_type=object[1].toString();
	ticket_series=object[2].toString();
	}
	  // Code for getting the first time counters of Feedback Status table, If get ticket counts greater than Zero than go in If block  and if get 0 than go in else block 	
	if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
	ticket_no= new HelpdeskUniversalHelper().getComplTicketSeries(ticket_type,deptid,connectionSpace);
	}
	else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
	ticket_no= new HelpdeskUniversalHelper().getAssetTicketSeries(ticket_type,deptid,connectionSpace);
	}
	else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("ASTM")) {
	ticket_no= new HelpdeskUniversalHelper().getAssetSNOSeries(ticket_type,deptid,connectionSpace);
	}
	else 
	{
	//	System.out.println("ticket_type >>"+ticket_type+" V<<<<<<deptid>>"+deptid+"moduleName>>>>>>>"+moduleName);
	ticket_no= new HelpdeskUniversalHelper().getLatestTicket(ticket_type,deptid,moduleName, connectionSpace);
	}
	  
	  if (ticket_no!=null && !ticket_no.equals("")) {
	  if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("N")) {
	  if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
	 prefix=ticket_no.substring(0, 4);
	 sufix=ticket_no.substring(4, ticket_no.length());
	}
	  else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("FM")) 
	  {
	 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
	 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
	}
	  else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
	     prefix=ticket_no.substring(0, 3);
	 sufix=ticket_no.substring(3, ticket_no.length());
	}
	  else {
	 prefix=ticket_no.substring(0, 2);
	 sufix=ticket_no.substring(2, ticket_no.length());
	}
	  setAN(Integer.parseInt(sufix));
	  ticketno=prefix+ ++ANN;
	 }
	 else if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("D")) {
	 if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
	 prefix=ticket_no.substring(0, 4);
	 sufix=ticket_no.substring(4, ticket_no.length());
	}
	 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("FM")) {
	 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
	 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
	}
	 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
	     prefix=ticket_no.substring(0, 3);
	 sufix=ticket_no.substring(3, ticket_no.length());
	}
	 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("ASTM")) {
	     prefix=ticket_no.substring(0,11);
	 sufix=ticket_no.substring(11, ticket_no.length());
	}
	 else {
	 prefix=ticket_no.substring(0, 2);
	 sufix=ticket_no.substring(2, ticket_no.length());
	}
	  setAN(Integer.parseInt(sufix));
	  ticketno=prefix+ ++ANN;
	 }
	  }
	  else {
	if (ticket_type.equalsIgnoreCase("N")) {
	if (ticket_series!=null && !ticket_series.equals("") && !ticket_series.equals("NA")) {
	ticketno=ticket_series;
	}
	}
	else if (ticket_type.equalsIgnoreCase("D")) {
	deptTicketSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptid,"moduleName",moduleName, "prefix", "ASC",connectionSpace);
	                        if (deptTicketSeries!=null && deptTicketSeries.size()==1) {
	for (Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();) {
	Object[] object1 = (Object[]) iterator2.next();
	if (object1[2]!=null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA")) {
	ticketno=object1[2].toString()+object1[3].toString();
	}
	else {
	ticketno="NA";
	}
	}
	                      }
	}
	 }
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return ticketno;
	}
	
	
	public String getLoginIdEmpName(SessionFactory connection,String userName)
	{
	String empName="NA";
	if(userName!=null)
	{
	try
	{
	String deCryptedId=Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
	StringBuilder buffer=new StringBuilder("select emp.empName from employee_basic as emp");
	buffer.append(" inner join useraccount as usr on emp.useraccountid=usr.id ");
	buffer.append(" where usr.userID= '"+deCryptedId+"'");
	
	List dataList=new createTable().executeAllSelectQuery(buffer.toString(), connection);
	if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
	{
	empName=dataList.get(0).toString();
	}
	
	}
	catch(Exception e)
	{
	
	}
	}
	
	return empName;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String getFloorStatus( String deptid,SessionFactory connection)
	 {

	String  flrStatus = "";
	StringBuffer query= new StringBuffer();
	List flrList = new ArrayList();
	query.append("select floor_status from dept_ticket_series_conf where deptName='"+deptid+"'");
	//System.out.println("Floor Status Query is  "+query.toString());
	Session session = null;  
	    Transaction transaction = null;  
	try 
	  {  
	 session=connection.openSession(); 
	 transaction = session.beginTransaction(); 
	 flrList=session.createSQLQuery(query.toString()).list();  
	 transaction.commit(); 
	  }
	    catch (Exception ex)
	  {
	 transaction.rollback();
	  } 
	finally{
	try {
	session.flush();
	session.close();
	} catch (Exception e) {
	
	}
	}
	//System.out.println("Floor List Size is   "+flrList);
	if(flrList!=null && flrList.size()>0)
        {
	flrStatus=flrList.get(0).toString();
        }
	//System.out.println("Floor Statusssss    "+flrStatus);
	return flrStatus;
	 
	 }
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept_subDept,String module, String level,String floor_status,String floor,SessionFactory connectionSpace) 
	{
	List<Integer> list = new ArrayList<Integer>();
	//String qry = null;
	StringBuilder query =new StringBuilder();
	try {
	String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
	query.append("select distinct emp.id from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
	if (module!=null && !module.equals("") && module.equals("HDM")) {
	query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
	}
	else if (module!=null && !module.equals("") && module.equals("FM")) {
	query.append(" inner join department dept on roaster.dept_subdept = dept.id ");
	query.append(" inner join shift_conf shift on dept.id = shift.dept_subdept ");
	}
	query.append(" where shift.shiftName="+ shiftname+ " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
	query.append(" and shift.shiftFrom<='"+ DateUtil.getCurrentTime()+ "' and shift.shiftTo >'"+ DateUtil.getCurrentTime() + "'");
	if (module!=null && !module.equals("") && module.equals("HDM")) {
	if (floor_status.equalsIgnoreCase("Y")) {
	query.append(" and roaster.floor='"+floor+"'  and dept.id=(select deptid from subdepartment where id='"+ dept_subDept+ "')");
	}
	else if (floor_status.equalsIgnoreCase("N")) {
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	}
	}
	else if (module!=null && !module.equals("") && module.equals("FM")) {
	query.append(" and dept.id='"+ dept_subDept+ "'");
	}
	
	//System.out.println("Emp of withoiut Bed Mapping are as:::::"+query);
	//	System.out.println("First Query   "+query.toString());
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4EscalationWithoutBed(String dept_subDept,String module, String level,String floor_status,String floor,List<Integer> bedList,SessionFactory connectionSpace) 
	{
	List<Integer> list = new ArrayList<Integer>();
	//String qry = null;
	StringBuilder query =new StringBuilder();
	try {
	String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
	query.append("select distinct emp.id from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
	if (module!=null && !module.equals("") && module.equals("HDM")) {
	query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
	}
	else if (module!=null && !module.equals("") && module.equals("FM")) {
	query.append(" inner join department dept on roaster.dept_subdept = dept.id ");
	query.append(" inner join shift_conf shift on dept.id = shift.dept_subdept ");
	}
	query.append(" where shift.shiftName="+ shiftname+ " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
	query.append(" and shift.shiftFrom<='"+ DateUtil.getCurrentTime()+ "' and shift.shiftTo >'"+ DateUtil.getCurrentTime() + "'");
	if (module!=null && !module.equals("") && module.equals("HDM")) {
	if (floor_status.equalsIgnoreCase("Y")) {
	query.append(" and roaster.floor='"+floor+"'  and dept.id=(select deptid from subdepartment where id='"+ dept_subDept+ "')");
	}
	else if (floor_status.equalsIgnoreCase("N")) {
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	}
	}
	else if (module!=null && !module.equals("") && module.equals("FM"))
	{
	query.append(" and dept.id='"+ dept_subDept+ "'");
	if(bedList!=null && bedList.size()>0)
	{
	query.append(" and emp.id  not in"+ bedList.toString().replace("[", "(").replace("]",")") + "");
	}
	}
	
//	System.out.println("Emp of withoiut Bed Mapping are as:::::"+query);
	//	System.out.println("First Query   "+query.toString());
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept,
	String deptLevel, String toLevel,List empId,String module, SessionFactory connectionSpace) {
	List<Integer> list = new ArrayList<Integer>();
	StringBuilder query = new StringBuilder();
	String arr =empId.toString().replace("[", "(").replace("]", ")");
	try {
	query.append("select distinct allot_to from feedback_status as feed_status");
	if (module!=null && !module.equals("") && module.equalsIgnoreCase("HDM")) {
	query.append(" inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id ");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
	query.append(" where sub_dept.id='"+dept_subDept+"' and contacts.level='"+toLevel+"' and allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (module!=null && !module.equals("") && module.equalsIgnoreCase("FM")) {
	query.append(" inner join department dept on feed_status.to_dept_subdept = dept.id ");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
	query.append(" where dept.id='"+dept_subDept+"' and contacts.level='"+toLevel+"' and allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
	}
	query.append(" and feed_status.moduleName='"+ module + "'");
	//	System.out.println("Second Query  "+query.toString());
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	}
	
	List<Integer> getBedMappingEmp(String dept,SessionFactory connectionSpace)
	{
	 List<Integer> list = new ArrayList<Integer>();
         StringBuilder query =new StringBuilder("select distinct emp.id from employee_basic as emp "
         	+ " inner join compliance_contacts contacts on contacts.emp_id = emp.id "
         	+ " inner join bed_mapping as bed on bed.contact_id=contacts.id  where  contacts.forDept_id='"+dept+"'");
      //   System.out.println("Emp of Bed Mapping are as:::::"+query);
         list=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
         return list;
	}
	
	@SuppressWarnings("unchecked")
    public List<Integer> getRandomEmp4BedSpecific(String dept,String location,String level,SessionFactory connectionSpace)
    {
            List<Integer> list = new ArrayList<Integer>();
            StringBuilder query =new StringBuilder();
            try {
                    query.append("  select distinct emp.id from employee_basic as emp");
                    query.append("  inner join compliance_contacts contacts on contacts.emp_id = emp.id");
                    query.append("  inner join bed_mapping bed on contacts.id = bed.contact_id");
                    query.append("  where forDept_id='"+ dept+ "' && bed.bed_no='"+location+"'");
                  //  .out.println("For Bed Mapping >>>>>>"+query);
                    list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
            }
            catch (Exception e) 
            {
                    e.printStackTrace();
            }
            
            if(list!=null && list.size()>0)
            {
            	
            	return list;
            }
            else
            {
            //	System.out.println("In side else for not exist of bed no");
            	List <Integer> bedList=getBedMappingEmp(dept,connectionSpace);
            	List <Integer> nonbedList=getRandomEmp4EscalationWithoutBed(dept, "FM", level, "", "",bedList, connectionSpace);
         //   	System.out.println("Non Bed List is as "+nonbedList.toString());
            	return nonbedList;
            }
    }
	
	@SuppressWarnings("unchecked")
	public String getRandomEmployee(String module, List empId,SessionFactory connectionSpace) {
	/**
	 * need to work on this problem
	 */
	Session session = null;
	List list = new ArrayList();
	String allotmentId = "";
	StringBuilder sb = new StringBuilder();
	try {
	session = HibernateSessionFactory.getSession();
	sb.append("select allot_to from feedback_status where open_date='"+ DateUtil.getCurrentDateUSFormat()+ "' and moduleName='"+module+"'");
	sb.append(" group by allot_to having allot_to in "+ empId.toString().replace("[", "(").replace("]", ")")+ " order by count(allot_to) limit 1 ");
	
	/*qry = "select allot_to from feedback_status where open_date='"
	+ DateUtil.getCurrentDateUSFormat()
	+ "' group by allot_to having allot_to in "
	+ empId.toString().replace("[", "(").replace("]", ")")
	+ " order by count(allot_to) limit 1 ";*/
	//System.out.println("Final list >>>>>>>>>>>>>>>>>>>>>>  "+sb.toString());
	list = new createTable()
	.executeAllSelectQuery(sb.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
	session.flush();
	session.close();
	}
	if (list != null && list.size() > 0) {
	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	Object object = (Object) iterator.next();
	allotmentId = object.toString();
	}
	}
	return allotmentId;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getPendingAllotto(String subdept,String module,SessionFactory connectionSpace) {
	/**
	 * need to work on this problem
	 */
	Session session = null;
	List list = new ArrayList();
	String qry = null;
	try {
	session = HibernateSessionFactory.getSession();
	qry = "select distinct allot_to from feedback_status where open_date='"
	+ DateUtil.getCurrentDateUSFormat()
	+ "' and to_dept_subdept="+subdept+" and status='Pending' and moduleName='"+module+"'";
	//System.out.println("Third Query  "+qry);
	list = new createTable()
	.executeAllSelectQuery(qry, connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
	session.flush();
	session.close();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllData(String table, String searchfield,String searchfieldvalue, String orderfield, String order,SessionFactory connectionSpace) {
	List SubdeptallList = new ArrayList();
	StringBuilder query = new StringBuilder();
	try {
	query.append("select * from " + table + " where "+ searchfield + "='" + searchfieldvalue + "'");
	if (orderfield!=null && !orderfield.equals("") && order!=null && !order.equals("")) {
	query.append(" ORDER BY "+ orderfield + " " + order + "");
	}
	SubdeptallList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	//System.out.println("Query is 111  "+query);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SubdeptallList;
	}
	
	public synchronized static String getReAssignedTicketNo(String ticket_no,String deptId,String toDeptId,String module,SessionFactory connection)
	 {
	//System.out.println(toDeptId+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+deptId);
	String ticketno="NA",prefix="",sufix="",start="";
	String toDeptPrefix="",deptPrefix="",newStr="";
	try 
	{
	//System.out.println("select prefix from dept_ticket_series_conf where deptName='"+toDeptId+"'");
	List toDeptTicketSeries=new createTable().executeAllSelectQuery("select prefix from dept_ticket_series_conf where deptName='"+toDeptId+"'", connection);
	if(toDeptTicketSeries!=null && toDeptTicketSeries.size()>0 && toDeptTicketSeries.get(0)!=null)
	{
	toDeptPrefix=toDeptTicketSeries.get(0).toString();
	}
	//System.out.println(ticket_no+"toDeptPrefix is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+toDeptPrefix);
	newStr=ticket_no.substring(0, ticket_no.indexOf(toDeptPrefix));
	
	String ticket= new HelpdeskUniversalHelper().getLatestTicket("D",deptId,"FM", connection);
	if(ticket!=null && !ticket.equalsIgnoreCase(""))
	{
	//System.out.println("Ticket is as "+ticket);
	prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
	sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
	setAN(Integer.parseInt(sufix));
	ticketno=prefix+ ++ANN;
	}
	else
	{
	List deptTicketSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptId,"moduleName","FM", "prefix", "ASC",connection);
                if (deptTicketSeries!=null && deptTicketSeries.size()==1) {
	for (Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();) {
	Object[] object1 = (Object[]) iterator2.next();
	if (object1[2]!=null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA")) {
	ticketno=object1[2].toString()+object1[3].toString();
	}
	else {
	ticketno="NA";
	}
	}
              }
	
	}
	}
	catch (Exception e) {
	e.printStackTrace();
	}
	//	System.out.println(newStr+""+ticketno);
	return newStr+ticketno;
	}
	
	public boolean getTicketIsComplaintOrNot(String feedStatId,SessionFactory connection)
	{
	//System.out.println("select cat.needEsc from feedback_status as feed inner join feedback_subcategory as cat on cat.id=feed.subCatg where feed.id='"+feedStatId+"'");
	StringBuilder builder=new StringBuilder("select cat.needEsc from feedback_status as feed inner join feedback_subcategory as cat on cat.id=feed.subCatg where feed.id='"+feedStatId+"'");
	List dataList=new createTable().executeAllSelectQuery(builder.toString(), connection);
	if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
	{
	if(dataList.get(0).toString().equalsIgnoreCase("Y"))
	{
	return true;
	}
	else
	{
	return false;
	}
	}
	else
	{
	return false;
	}
	}
	
	public List<FeedbackPojo> setFeedbackDataValusInList(List data, String status,String deptLevel)
	{
	//	System.out.println("Setting Pojo Props");
	List<FeedbackPojo> feedList=new ArrayList<FeedbackPojo>();
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0)
	{
	try
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	
	fbp.setTicket_no(object[0].toString());
	
	if(object[1].toString()!=null)
	{
	fbp.setFeedback_by_dept(object[1].toString());
	}
	
	if (object[2]!=null)
	{
	fbp.setFeed_by(object[2].toString());
	}
	else
	{
	fbp.setFeed_by("NA");
	}
	
	if (object[3]!=null)
	{
	fbp.setFeedback_by_mobno(object[3].toString());
	}
	else
	{
	fbp.setFeedback_by_mobno("NA");
	}
	
	if (object[4]!=null)
	{
	fbp.setFeedback_by_emailid(object[4].toString());
	}
	else
	{
	fbp.setFeedback_by_emailid("NA");
	}
	
	if(object[5].toString()!=null)
	{
	fbp.setFeedback_to_dept(object[5].toString());	
	}
	
	fbp.setFeedback_allot_to(object[6].toString());
	
	fbp.setFeedback_catg(object[7].toString());
	
	fbp.setFeedback_subcatg(object[8].toString());
	fbp.setFeed_brief(object[9].toString());
	
	if(object[10]!=null)
	{
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(object[10].toString()));
	}
	
	
	if(object[11]!=null)
	{
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(object[11].toString()));
	}
	
	fbp.setOpen_time(object[12].toString());
	
	
	
	fbp.setFeed_registerby(object[14].toString());
	
	
	if(object[17]!=null && !object[17].toString().equals(""))
	{
	fbp.setVia_from(object[17].toString());
	}
	
	if (!status.equals("Pending"))
	{
	if (object[18]!=null && !object[18].toString().equals(""))
	{
	fbp.setAction_by(object[18].toString());	
	}
	else
	{
	fbp.setAction_by("NA");
	}
	}
	
	if (object[23]!=null)
	{
	fbp.setLocation(object[23].toString());
	}
	else
	{
	fbp.setLocation("NA");
	}
	
	if (object[20]!=null)
	{
	fbp.setFeedback_remarks(object[20].toString());
	}
	else
	{
	fbp.setFeedback_remarks("NA");
	}
	
	
	if(status!=null && status.equalsIgnoreCase("Resolved"))
	            {
	                    if (object[21]!=null)
	                    {
	                            fbp.setResolve_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
	                    }
	                    else
	                    {
	                            fbp.setResolve_date("NA");
	                    }
	                    
	                    if (object[22]!=null)
	                    {
	                            fbp.setResolve_time(object[22].toString());
	                    }
	                    else
	                    {
	                            fbp.setResolve_time("NA");
	                    }
	                    
	                    if (object[23]!=null)
	                    {
	                            fbp.setResolve_duration(object[23].toString());
	                    }
	                    else
	                    {
	                            fbp.setResolve_duration("NA");
	                    }
	                    
	                    if (object[24]!=null)
	                    {
	                            fbp.setResolve_remark(object[24].toString());
	                    }
	                    else
	                    {
	                            fbp.setResolve_remark("NA");
	                    }
	                    
	                    if (object[25]!=null)
	                    {
	                            fbp.setSpare_used(object[25].toString());
	                    }
	                    else
	                    {
	                            fbp.setSpare_used("NA");
	                    }
	            }
	
	if(status!=null && !status.equalsIgnoreCase(""))
	                {
	fbp.setStatus(status);
	                }
	else if(object[16]!=null && !object[16].toString().equals(""))
	{
	fbp.setStatus(object[16].toString());
	}
	
	feedList.add(fbp);
	}
	}
	catch (Exception e) {
	e.printStackTrace();
	}
	}
	return feedList;
	
	}
	
	
	
	public List getScheduledDataSMS(SessionFactory connection,String flag,String date,String time)
	{
	List dataList=new ArrayList();
	StringBuffer buffer=new StringBuffer("select id,mobno,msg_text from instant_msg where (date<='"+date+"' and time<='"+time+"') && flag='0'");
	//System.out.println("buffer is as "+buffer);
	dataList=new createTable().executeAllSelectQuery(buffer.toString(), connection);
	return dataList;
	}
	
	
	public List getScheduledDataMail(SessionFactory connection,String flag,String date,String time)
	{
	List dataList=new ArrayList();
	StringBuffer buffer=new StringBuffer("select id ,emailid ,subject ,mail_text ,attachment from instant_mail where (date='"+date+"' and time<='"+time+"') && flag='0'");
	//System.out.println("buffer is as "+buffer);
	dataList=new createTable().executeAllSelectQuery(buffer.toString(), connection);
	return dataList;
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
	else
	 {
	selectTableData.append("* from " +tableName);
	 }
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
	//System.out.println("selectTableData.toString() is as <><>><"+selectTableData.toString());
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,SessionFactory connection)
	  {
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  
	selectTableData.append("select ");
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
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
	{
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	}
	} 
	}
	
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
	{
     	 selectTableData.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	selectTableData.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	selectTableData.append(" "+searchField+" like '%"+searchString+"'");
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
	//System.out.println("QQQQQQQQ  "+selectTableData.toString());
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getDataForRoasterApply(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,SessionFactory connection)
	  {
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  
	selectTableData.append("select ");
	if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
	  {
	int i=1;
	for(String H:colmName)
	 {
	if(i<colmName.size())
	selectTableData.append(H+" ,");
	else
	selectTableData.append(H+" from " +tableName+"  as roaster");
	i++;
	 }
	 }
	// Here we get the whole data of a table
	else
	 {
	selectTableData.append("* from "+tableName+" as roaster");
	 }
	selectTableData.append(" inner join subdepartment on roaster.dept_subdept=subdepartment.id ");
	selectTableData.append(" inner join employee_basic on roaster.empId=employee_basic.empId "); 

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
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
	{
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	}
	} 
	}
	
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
	{
     	 selectTableData.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	selectTableData.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	selectTableData.append(" "+searchField+" like '%"+searchString+"'");
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
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,Session session)
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
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
	{
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	}
	} 
	}
	
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
	{
     	 selectTableData.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	selectTableData.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	selectTableData.append(" "+searchField+" like '%"+searchString+"'");
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
	Transaction transaction = null;  
	try {  
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public int getDataCountFromTable(String tableName,Map<String, Object>wherClause, Map<String, List> wherClauseList,SessionFactory connection)
	  {
	int countSize =0;
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  

	selectTableData.append("select count(*) from " +tableName);
	    
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
	
	// Set the values for where clause
	if(wherClauseList!=null && !wherClauseList.isEmpty())
	   {
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	} 
	}
	selectTableData.append(";");
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	if (Data!=null && Data.size()>0) {
	countSize  = Integer.parseInt(Data.get(0).toString());
	}
	return countSize;
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
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' and ");
	else
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' ");
	size++;
	} 
	selectTableData.append(";");
	
//	System.out.println("selectTableData :: is as "+selectTableData);
	//update feedback_status set resolve_remark = 'We Have Started A Register To Note The Names Of The Patients Who Get Missed Out On Our Daily Morning And Evening Rounds. Separate Late Evening Rounds For Such Patients Will Be Held Or Will Be Met Early In He Morning.' , spare_used = 'Ms Anika, Senior Dietician, is taking rounds of the 6th floor. She is also taking rounds with Dr V P Bhalla's team daily. According to her she had met the patient on Friday after waking the patient from sleep. But could not meet the patient on Saturday as the patient had gone for an investigation and was sleeping during the evening rounds.' , resolve_time = '15:50:03' , status = 'Resolved' , resolve_duration = '' , resolve_by = '94' , resolve_date = '2014-09-30' , action_by = 'chandans' where id = '77149' ;
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession();
	transaction = session.beginTransaction();  
	int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
	if(count>0)
	Data=true;
	transaction.commit(); 
	}catch (Exception ex) { ex.printStackTrace();transaction.rollback();} 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateTableColomnWithSession(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock)
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
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' and ");
	else
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	selectTableData.append(";");
	
	Transaction transaction = null;  
	try {  
	    Session session = HibernateSessionFactory.getSession();
	transaction = session.beginTransaction();  
	int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
	if(count>0)
	Data=true;
	transaction.commit(); 
	}catch (Exception ex) {transaction.rollback();} 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackValues(List feedValue, String deptLevel, String feedStatus)
	 {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	if (feedValue!=null && feedValue.size()>0) {
	if (deptLevel.equals("2")) {
	for (Iterator iterator = feedValue.iterator(); iterator
	.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	FeedbackPojo  fbp = new FeedbackPojo();
	 fbp.setId((Integer)obdata[0]);
	 fbp.setTicket_no(obdata[1].toString());
	 fbp.setFeedback_by_dept(obdata[2].toString());
	 fbp.setFeedback_by_subdept(obdata[3].toString());
	 if (obdata[4]!=null && !obdata[4].toString().equals("")) {
	fbp.setFeed_by(obdata[4].toString());
	}
	 else {
	 fbp.setFeed_by("NA");
	}
	 
	if (obdata[5]!=null && !obdata[5].toString().equals("") && (obdata[5].toString().startsWith("9") || obdata[5].toString().startsWith("8") || obdata[5].toString().startsWith("7")) && obdata[5].toString().length()==10) {
	fbp.setFeedback_by_mobno(obdata[5].toString());
	    }
	else {
	 fbp.setFeedback_by_mobno("NA");
	}
	
	if (obdata[6]!=null && !obdata[6].toString().equals("")) {
	fbp.setFeedback_by_emailid(obdata[6].toString());
	        }
	    else {
	    	fbp.setFeedback_by_emailid("NA");
	    }
	 
	fbp.setFeedback_to_dept(obdata[7].toString());
	fbp.setFeedback_to_subdept(obdata[8].toString());
	fbp.setFeedback_allot_to(obdata[9].toString());
	fbp.setFeedtype(obdata[10].toString());
	fbp.setFeedback_catg(obdata[11].toString());
	fbp.setFeedback_subcatg(obdata[12].toString());
	if (obdata[13]!=null && !obdata[13].toString().equals("")) {
	fbp.setFeed_brief(obdata[13].toString());
	        }
	    else {
	    	fbp.setFeed_brief("NA");
	    }
	
	fbp.setFeedaddressing_time(obdata[14].toString());
	
	if (obdata[14]!=null && !obdata[14].toString().equals("") && obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("")) {
	String addr_date_time = DateUtil.newdate_AfterTime(obdata[17].toString(), obdata[18].toString(), obdata[14].toString());
	String[] add_date_time = addr_date_time.split("#");
	for (int i = 0; i < add_date_time.length; i++) {
	fbp.setFeedaddressing_date(DateUtil.convertDateToIndianFormat(add_date_time[0]));
	fbp.setFeedaddressing_time(add_date_time[1].substring(0, 5));
	}
	}
	    else {
	    	fbp.setFeedaddressing_date("NA");
	    	fbp.setFeedaddressing_time("NA");
	    }
	
	if (obdata[16]!=null && !obdata[16].toString().equals("")) {
	fbp.setLocation(obdata[16].toString());
	        }
	    else {
	    	fbp.setLocation("NA");
	    }
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[17].toString()));
	fbp.setOpen_time(obdata[18].toString().substring(0, 5));
	if (feedStatus.equalsIgnoreCase("Pending")) {
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[19].toString()));
	fbp.setEscalation_time(obdata[20].toString().substring(0, 5));
	}
	  
	fbp.setLevel(obdata[21].toString());
	fbp.setStatus(obdata[22].toString());
	fbp.setVia_from(obdata[23].toString());
	fbp.setFeed_registerby(obdata[24].toString());
	
	 if (feedStatus.equalsIgnoreCase("High Priority")) {
	 fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[25].toString()));
	 fbp.setHp_time(obdata[26].toString().substring(0, 5));
	 fbp.setHp_reason(obdata[27].toString());
	}
	
	 if (feedStatus.equalsIgnoreCase("Snooze")) {
	 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
	 fbp.setSn_reason(obdata[28].toString());
	}
	 else {
	 fbp.setSn_reason("NA");
	}
	 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
	 fbp.setSn_on_date(obdata[29].toString());
	}
	 else {
	 fbp.setSn_on_date("NA");
	}
	 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
	 fbp.setSn_on_time(obdata[30].toString());
	}
	 else {
	 fbp.setSn_on_time("NA");
	}
	 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
	 fbp.setSn_date(DateUtil.convertDateToIndianFormat(obdata[31].toString()));
	}
	 else {
	 fbp.setSn_date("NA");
	}
	 if (obdata[32]!=null && !obdata[32].toString().equals("")) {
	 fbp.setSn_time(obdata[32].toString().substring(0, 5));
	}
	 else {
	 fbp.setSn_time("NA");
	} 
	 if (obdata[33]!=null && !obdata[33].toString().equals("")) {
	 fbp.setSn_duration(obdata[33].toString());
	}
	 else {
	 fbp.setSn_duration("NA");
	}  
	}
	
	 if (feedStatus.equalsIgnoreCase("Resolved")) {
	 if (obdata[41]!=null && !obdata[41].toString().equals("")) {
	 fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[41].toString()));
	 }
	 else {
	 fbp.setResolve_date("NA");
	}
	if (obdata[42]!=null && !obdata[42].toString().equals("")) {
	 fbp.setResolve_time(obdata[42].toString().substring(0, 5));
	}
	else {
	 fbp.setResolve_time("NA");
	 }
	
	 if (obdata[43]!=null && !obdata[43].equals("")) {
	 fbp.setResolve_duration(obdata[43].toString());
	  }
	 else {
	   if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("") && obdata[42]!=null && !obdata[42].toString().equals("")) {
	   String dura1=DateUtil.time_difference(obdata[17].toString(), obdata[18].toString(), obdata[41].toString(), obdata[42].toString());
	   fbp.setResolve_duration(dura1);
	   }
	   else {
	   fbp.setResolve_duration("NA");
	   }
	 }
	 
	 if (obdata[44]!=null && !obdata[44].toString().equals("")) {
	 fbp.setResolve_remark(obdata[44].toString());
	}
	 else {
	 fbp.setResolve_remark("NA");
	}
	 fbp.setResolve_by(obdata[45].toString());
	 fbp.setSpare_used(obdata[46].toString());
	}
	 if (!feedStatus.equalsIgnoreCase("") && !feedStatus.equalsIgnoreCase("Pending")) {
	 if (obdata[40]!=null && !obdata[40].toString().equals("")) {
	 fbp.setAction_by(obdata[40].toString());
	}
	 else {
	 fbp.setAction_by("NA");
	}
	}
	feedList.add(fbp);
	}
	}
	else if (deptLevel.equals("1")) {
	for (Iterator iterator = feedValue.iterator(); iterator
	.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	@SuppressWarnings("unused")
	FeedbackPojo  fbp = new FeedbackPojo();
	
	FeedbackPojo  fbp1 = new FeedbackPojo();
	fbp1.setId((Integer)obdata[0]);
	fbp1.setTicket_no(obdata[1].toString());
	fbp1.setFeedback_by_dept(obdata[2].toString());
	 if (obdata[3]!=null && !obdata[3].toString().equals("")) {
	 fbp1.setFeed_by(obdata[3].toString());
	}
	 else {
	 fbp1.setFeed_by("NA");
	}
	 
	if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
	fbp1.setFeedback_by_mobno(obdata[4].toString());
	    }
	else {
	fbp1.setFeedback_by_mobno("NA");
	}
	
	if (obdata[5]!=null && !obdata[5].toString().equals("")) {
	fbp1.setFeedback_by_emailid(obdata[5].toString());
	        }
	    else {
	    	fbp1.setFeedback_by_emailid("NA");
	    }
	 
	fbp1.setFeedback_to_dept(obdata[6].toString());
	fbp1.setFeedback_allot_to(obdata[7].toString());
	fbp1.setFeedtype(obdata[8].toString());
	fbp1.setFeedback_catg(obdata[9].toString());
	fbp1.setFeedback_subcatg(obdata[10].toString());
	fbp1.setFeed_brief(obdata[11].toString());
	if (obdata[14]!=null && !obdata[14].toString().equals("")) {
	fbp1.setLocation(obdata[14].toString());
	        }
	    else {
	    	fbp1.setLocation("NA");
	    }
	fbp1.setOpen_date(obdata[15].toString());
	fbp1.setOpen_time(obdata[16].toString());
	fbp1.setEscalation_date(obdata[17].toString());
	fbp1.setEscalation_time(obdata[18].toString());
	fbp1.setLevel(obdata[19].toString());
	fbp1.setStatus(obdata[20].toString());
	fbp1.setVia_from(obdata[21].toString());
	fbp1.setFeed_registerby(obdata[22].toString());
	feedList.add(fbp1);
	    }
	}
	}
	return feedList;
	 }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setAnalyticalReportValues(List feedValue, String reportFor)
	 {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	if (feedValue!=null && feedValue.size()>0) {
	if (reportFor.equalsIgnoreCase("E")) {
	for (Iterator iterator = feedValue.iterator(); iterator
	.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	FeedbackPojo  fbp = new FeedbackPojo();
	 fbp.setFeedback_to_dept(obdata[0].toString());
	 fbp.setFeedback_to_subdept(obdata[1].toString());
	 fbp.setEmpName(obdata[2].toString());
	 fbp.setMobOne(obdata[3].toString());
	 fbp.setEmailIdOne(obdata[4].toString());	
	 fbp.setCounter(obdata[5].toString());
	 feedList.add(fbp);
	}
	}
	else if (reportFor.equalsIgnoreCase("C")) {
	for (Iterator iterator = feedValue.iterator(); iterator
	.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	FeedbackPojo  fbp = new FeedbackPojo();
	 fbp.setFeedback_by_dept(obdata[0].toString());
	 fbp.setFeedback_by_subdept(obdata[1].toString());
	 fbp.setFeedback_to_dept(obdata[2].toString());
	 fbp.setFeedback_to_subdept(obdata[3].toString());
	 fbp.setFeedback_catg(obdata[4].toString());	
	 fbp.setFeedback_catg(obdata[5].toString());
	 fbp.setFeedback_subcatg(obdata[6].toString());
	 fbp.setCounter(obdata[7].toString());
	 feedList.add(fbp);
	}
	}
	}
	return feedList;
	 }
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String ticketno, String deptLevel,String status,String id, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            	query.append("feedback.feed_by_emailid,emp.empName as alloto,catg.categoryName,");
            	query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,");
            	query.append("feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            	query.append("feedback.level,feedback.status,feedback.via_from,");
            	query.append("feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
            	query.append("emp.mobOne,emp.emailIdOne,dept1.deptName as bydept,dept2.deptName as todept,feedback.id as feedid ");
            	query.append(",feedback.location,feedback.feed_registerby,feedback.clientId");
            	if (status.equalsIgnoreCase("Resolved"))
            	{
            	query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
            	}
            	if(status.equalsIgnoreCase("Noted"))
            	{
            	query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by");
            	}
            	
            	query.append(" ,feedback.Addr_date_time from feedback_status as feedback");
            	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            	query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
            	query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
            	query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            	if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Noted"))
            	{
            	query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
            	}
            	
            	if (id!=null && !id.equals("") && status!=null && !status.equals("") && !status.equals("Re-Assign"))
            	{
            	query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
            	}
            	else if (id!=null && !id.equals("") && status!=null && !status.equals("") && status.equals("Re-Assign"))
            	{
            	query.append(" where feedback.id='"+id+"' ");	
            	}
            	else
            	{
            	query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from feedback_status)");
            	}
       //     	System.out.println("query.toString() is as <>>>>>>>>>>>>>>>"+query.toString());
                    list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getFeedbackDetail4ReportDownload(String by_dept,String to_dept,String tosubdept,String feedType,String categoryId,String subCategory,String from_date,String to_date,String from_time,String to_time,String status, String deptLevel, SessionFactory connectionSpace)
     {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
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
            	query.append(" where ");
            	
            	if (by_dept!=null && !by_dept.equals("-1")) {
            	query.append(" dept1.id='"+by_dept+"'");	
	}
            	
            	if (to_dept!=null && !to_dept.equals("-1") && !by_dept.equals("-1")) {
            	query.append(" and dept2.id='"+to_dept+"'");	
	}
            	
            	if (to_dept!=null && !to_dept.equals("-1") && by_dept.equals("-1")) {
            	query.append(" dept2.id='"+to_dept+"'");	
	}
            	
            	if (tosubdept!=null && !tosubdept.equals("-1")) {
            	query.append(" and feedback.to_dept_subdept='"+tosubdept+"'");	
	}
            	
            	if (feedType!=null  && !feedType.equals("-1")) {
            	query.append(" and feedtype.id='"+feedType+"'");	
	}
            	
            	if (categoryId!=null && !categoryId.equals("-1")) {
            	query.append(" and catg.id='"+categoryId+"'");	
	}
            	
            	if (subCategory!=null && !subCategory.equals("-1")) {
            	query.append(" and subcatg.id='"+subCategory+"'");	
	}
            	
            	if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
            	query.append(" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
            	
            	else if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (by_dept.equals("-1") || to_dept.equals("-1"))) {
            	query.append(" feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
            	
            	if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("")) {
            	query.append(" and feedback.open_time between '"+from_time+"' and '"+to_time+"' ");	
	}
            	else if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && (from_date==null || from_date.equals("") || to_date==null || to_date.equals(""))) {
            	query.append(" feedback.open_time between '"+from_time+"' and '"+to_time+"' ");	
	}
            	
            	if (status!=null && status.equals("Pending") && status.equals("Snooze") && status.equals("High Priority") && status.equals("Resolved") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
            	query.append(" and feedback.status='"+status+"' ");	
	}
            	}
            	
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
                
                if (list!=null && list.size()>0) {
            	int i=1;
            	if (list!=null && list.size()>0) {
            	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            	Object[] object = (Object[]) iterator.next();
            	FeedbackPojo fp =new FeedbackPojo();
            	fp.setId(i);
            	if (object[0]!=null && object[0].toString().equals("")) {
            	fp.setTicket_no("NA");
            	}
            	else {
            	fp.setTicket_no(object[0].toString());
            	}
            	
            	if (object[1]!=null && object[1].toString().equals("")) {
            	fp.setFeed_by("NA");
            	}
            	else {
            	fp.setFeed_by(object[1].toString());
            	}
            	
            	if (object[2]!=null && object[2].toString().equals("")) {
            	fp.setFeedback_by_mobno("NA");
            	}
            	else {
            	fp.setFeedback_by_mobno(object[2].toString());
            	}
            	
            	if (object[3]!=null && object[3].toString().equals("")) {
            	fp.setFeedback_by_emailid("NA");
            	}
            	else {
            	fp.setFeedback_by_emailid(object[3].toString());
            	}
            	
            	if (object[4]!=null && object[4].toString().equals("")) {
            	fp.setFeedback_allot_to("NA");
            	}
            	else {
            	fp.setFeedback_allot_to(object[4].toString());
            	}
            	
            	if (object[5]!=null && object[5].toString().equals("")) {
            	fp.setFeedtype("NA");
            	}
            	else {
            	fp.setFeedtype(object[5].toString());
            	}
            	
            	if (object[6]!=null && object[6].toString().equals("")) {
            	fp.setFeedback_catg("NA");
            	}
            	else {
            	fp.setFeedback_catg(object[6].toString());
            	}
            	
            	if (object[7]!=null && object[7].toString().equals("")) {
            	fp.setFeedback_subcatg("NA");
            	}
            	else {
            	fp.setFeedback_subcatg(object[7].toString());
            	}
            	
            	if (object[8]!=null && object[8].toString().equals("")) {
            	fp.setFeed_brief("NA");
            	}
            	else {
            	fp.setFeed_brief(object[8].toString());
            	}
            	
            	if (object[11]!=null && object[11].toString().equals("")) {
            	fp.setLocation("NA");
            	}
            	else {
            	fp.setLocation(object[11].toString());
            	}
            	
            	if (object[12]!=null && object[12].toString().equals("")) {
            	fp.setOpen_date("NA");
            	}
            	else {
            	fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));
            	}
            	
            	if (object[13]!=null && object[13].toString().equals("")) {
            	fp.setOpen_time("NA");
            	}
            	else {
            	fp.setOpen_time(object[13].toString().substring(0, 5));
            	}
            	
            	if (object[14]!=null && object[14].toString().equals("")) {
            	fp.setEscalation_date("NA");
            	}
            	else {
            	fp.setEscalation_date(DateUtil.convertDateToIndianFormat(object[14].toString()));
            	}
            	
            	
            	if (object[15]!=null && object[15].toString().equals("")) {
            	fp.setEscalation_time("NA");
            	}
            	else {
            	fp.setEscalation_time(object[15].toString().substring(0, 5));
            	}
            	
            	if (object[16]!=null && object[16].toString().equals("")) {
            	fp.setLevel("NA");
            	}
            	else {
            	fp.setLevel(object[16].toString());
            	}
            	
            	if (object[17]!=null && object[17].toString().equals("")) {
            	fp.setStatus("NA");
            	}
            	else {
            	fp.setStatus(object[17].toString());
            	}
            	
            	if (object[18]!=null && object[18].toString().equals("")) {
            	fp.setVia_from("NA");
            	}
            	else {
            	fp.setVia_from(object[18].toString());
            	}
            	
            	if (object[19]==null || object[19].toString().equals("")) {
            	fp.setHp_date("NA");
            	}
            	else {
            	fp.setHp_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
            	}
            	
            	if (object[20]==null || object[20].toString().equals("")) {
            	fp.setHp_time("NA");
            	}
            	else {
            	fp.setHp_time(object[20].toString().substring(0, 5));
            	}
            	
            	if (object[21]==null || object[21].toString().equals("")) {
            	fp.setHp_reason("NA");
            	}
            	else {
            	fp.setHp_reason(object[21].toString());
            	}
            	
            	if (object[22]==null || object[22].toString().equals("")) {
            	fp.setSn_reason("NA");
            	}
            	else {
            	fp.setSn_reason(object[22].toString());
            	}
            	
            	if (object[23]==null || object[23].toString().equals("")) {
            	fp.setSn_on_date("NA");
            	}
            	else {
            	fp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[23].toString()));
            	}
            	
            	if (object[24]==null || object[24].toString().equals("")) {
            	fp.setSn_on_time("NA");
            	}
            	else {
            	fp.setSn_on_time(object[24].toString().substring(0, 5));
            	}
            	
            	if (object[25]==null || object[25].toString().equals("")) {
            	fp.setSn_date("NA");
            	}
            	else {
            	fp.setSn_date(DateUtil.convertDateToIndianFormat(object[25].toString()));
            	}
            	
            	if (object[26]==null || object[26].toString().equals("")) {
            	fp.setSn_time("NA");
            	}
            	else {
            	fp.setSn_time(object[26].toString().substring(0,5));
            	}
            	
            	if (object[27]==null || object[27].toString().equals("")) {
            	fp.setSn_duration("NA");
            	}
            	else {
            	fp.setSn_duration(object[27].toString());
            	}
            	
            	if (object[28]==null || object[28].toString().equals("")) {
            	fp.setIg_date("NA");
            	}
            	else {
            	fp.setIg_date(DateUtil.convertDateToIndianFormat(object[28].toString()));
            	}
            	
            	if (object[29]==null || object[29].toString().equals("")) {
            	fp.setIg_time("NA");
            	}
            	else {
            	fp.setIg_time(object[29].toString().substring(0, 5));
            	}
            	
            	if (object[30]==null || object[30].toString().equals("")) {
            	fp.setIg_reason("NA");
            	}
            	else {
            	fp.setIg_reason(object[30].toString());
            	}
            	
            	if (object[31]==null || object[31].toString().equals("")) {
            	fp.setTransfer_date("NA");
            	}
            	else {
            	fp.setTransfer_date(DateUtil.convertDateToIndianFormat(object[31].toString()));
            	}
            	
            	if (object[32]==null || object[32].toString().equals("")) {
            	fp.setTransfer_time("NA");
            	}
            	else {
            	fp.setTransfer_time(object[32].toString().substring(0, 5));
            	}
            	
            	if (object[33]==null || object[33].toString().equals("")) {
            	fp.setTransfer_reason("NA");
            	}
            	else {
            	fp.setTransfer_reason(object[33].toString());
            	}
            	
            	if (object[34]==null || object[34].toString().equals("")) {
            	fp.setAction_by("NA");
            	}
            	else {
            	fp.setAction_by(object[34].toString());
            	}
            	
            	if (object[37]==null || object[37].toString().equals("")) {
            	fp.setFeedback_by_dept("NA");
            	}
            	else {
            	fp.setFeedback_by_dept(object[37].toString());
            	}
            	
            	if (object[38]==null || object[38].toString().equals("")) {
            	fp.setFeedback_by_subdept("NA");
            	}
            	else {
            	fp.setFeedback_by_subdept(object[38].toString());
            	}
            	
            	if (object[39]==null || object[39].toString().equals("")) {
            	fp.setFeedback_to_dept("NA");
            	}
            	else {
            	fp.setFeedback_to_dept(object[39].toString());
            	}
            	
            	if (object[40]==null || object[40].toString().equals("")) {
            	fp.setFeedback_to_subdept("NA");
            	}
            	else {
            	fp.setFeedback_to_subdept(object[40].toString());
            	}
            	
            	// Add By Padam In 13 Nov
            	if (status.equalsIgnoreCase("Resolved")) {
            	if (object[41]==null || object[41].toString().equals("")) {
                	fp.setResolve_date("NA");
                	}
                	else {
                	fp.setResolve_date(DateUtil.convertDateToIndianFormat(object[41].toString()));
                	}
            	
            	if (object[42]==null || object[42].toString().equals("")) {
                	fp.setResolve_time("NA");
                	}
                	else {
                	fp.setResolve_time(object[42].toString());
                	}
            	
            	if (object[43]==null || object[43].toString().equals("")) {
                	fp.setResolve_duration("NA");
                	}
                	else {
                	fp.setResolve_duration(object[43].toString());
                	}
            	
            	if (object[44]==null || object[44].toString().equals("")) {
                	fp.setResolve_remark("NA");
                	}
                	else {
                	fp.setResolve_remark(object[44].toString());
                	}
            	
            	if (object[45]==null || object[45].toString().equals("")) {
                	fp.setResolve_by("NA");
                	}
                	else {
                	fp.setResolve_by(object[45].toString());
                	}
            	
            	if (object[46]==null || object[46].toString().equals("")) {
                	fp.setSpare_used("NA");
                	}
                	else {
                	fp.setSpare_used(object[46].toString());
                	}
            	}
            	feedList.add(fp);
            	i++;
            	}
            	}
	}
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return feedList;
    }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackStatusCount(String by_dept,String to_dept,String tosubdept,String feedType,String categoryId,String subCategory,String from_date,String to_date,String from_time,String to_time,String status, String deptLevel, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	if (deptLevel.equals("2")) {
            	query.append("select feedback.status,count(*)");
            	
            	query.append(" from feedback_status as feedback");
            	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            	query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
            	query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            	query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
            	query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            	query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            	query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            	query.append(" where ");
            	if (by_dept!=null  && !by_dept.equals("-1")) {
            	query.append(" dept1.id='"+by_dept+"'");	
	}
            	
            	if (to_dept!=null  && !to_dept.equals("-1") && !by_dept.equals("-1")) {
            	query.append(" and dept2.id='"+to_dept+"'");	
	}
            	
            	if (to_dept!=null  && !to_dept.equals("-1") && by_dept.equals("-1")) {
            	query.append(" dept2.id='"+to_dept+"'");	
	}
            	
            	if (tosubdept!=null  && !tosubdept.equals("-1")) {
            	query.append(" and feedback.to_dept_subdept='"+tosubdept+"'");	
	}
            	
            	if (feedType!=null  && !feedType.equals("-1")) {
            	query.append(" and feedtype.id='"+feedType+"'");	
	}
            	
            	if (categoryId!=null && !categoryId.equals("-1")) {
            	query.append(" and catg.id='"+categoryId+"'");	
	}
            	
            	if (subCategory!=null  && !subCategory.equals("-1")) {
            	query.append(" and subcatg.id='"+subCategory+"'");	
	}
            	
            	if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
            	query.append(" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
            	
            	else if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (by_dept.equals("-1") || to_dept.equals("-1"))) {
            	query.append(" feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
            	
            	if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("")) {
            	query.append(" and feedback.open_time between '"+from_time+"' and '"+to_time+"'");	
	}
            	else if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && (from_date==null || from_date.equals("") || to_date==null || to_date.equals(""))) {
            	query.append(" feedback.open_time between '"+from_time+"' and '"+to_time+"'");	
	}
            	
            	if (status!=null && status.equals("Pending") && status.equals("Snooze") && status.equals("High Priority") && status.equals("Resolved") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
            	query.append(" and feedback.status='"+status+"'");	
	}
            	query.append(" group by feedback.status");
            	}
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	@SuppressWarnings("unchecked")
	public boolean checkTable(String table, SessionFactory connection)
	 {

	boolean flag = false;
	List list = null;
	String value = null;
	try {
	SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
	Properties props = sessionFactoryImpl.getProperties();
	String url = props.get("hibernate.connection.url").toString();
	String[] urlArray = url.split(":");
	String db_name = urlArray[urlArray.length - 1];
	String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
	Session session = null;  
	Transaction transaction = null;  
	    session=connection.getCurrentSession(); 
	    transaction = session.beginTransaction(); 
	    list=session.createSQLQuery(query).list();  
	transaction.commit();
	if (list!=null && list.size()>0) {
	value = list.get(0).toString();
	if (value.equalsIgnoreCase("1")) {
	flag=true;
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return flag;
	 
	 }
	
	@SuppressWarnings("unchecked")
	public boolean check_createTable(String table, SessionFactory connection)
	 {
	boolean flag = false;
	List list = null;
	String value = null;
	Session session = null;  
	Transaction transaction = null;  
	try {
	SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
	Properties props = sessionFactoryImpl.getProperties();
	String url = props.get("hibernate.connection.url").toString();
	String[] urlArray = url.split(":");
	String db_name = urlArray[urlArray.length - 1];
	String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
	
	    session=connection.getCurrentSession(); 
	    transaction = session.beginTransaction(); 
	    list=session.createSQLQuery(query).list();  
	transaction.commit();
	}catch (Exception e) {
	e.printStackTrace();
	}
	
	if (list!=null && list.size()>0) {
	value = list.get(0).toString();
	if (value.equalsIgnoreCase("1")) {
	flag=true;
	}
	else { 
	CommonOperInterface cot = new CommonConFactory().createInterface();
	if (table.equalsIgnoreCase("feedback_type")) {
	      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_type_configuration", "", connection, "", 0, "table_name", "feedback_type_configuration");
	      if (colName!= null && colName.size()>0) {
	    	  //Create Table Query Based On Configuration
	  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
	  for (GridDataPropertyView tableColumes : colName) {
	TableColumes tc = new TableColumes();
	tc.setColumnname(tableColumes.getColomnName());
	tc.setDatatype("varchar(255)");
	tc.setConstraint("default NULL");
	tableColumn.add(tc);
	   }
	  TableColumes tc = new TableColumes();
	  
	
	  tc.setColumnname("dept_subdept");
	  tc.setDatatype("varchar(5)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("deptHierarchy");
	  tc.setDatatype("varchar(2)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("hide_show");
	  tc.setDatatype("varchar(5)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(15)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  cot.createTable22("feedback_type", tableColumn, connection);
	}
	}
	   else if (table.equalsIgnoreCase("feedback_category")) {
	      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_category_configuration", "", connection, "", 0, "table_name", "feedback_category_configuration");
	      if (colName!= null && colName.size()>0) {
	    	  
	  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
	  for (GridDataPropertyView tableColumes : colName) {
	TableColumes tc = new TableColumes();
	tc.setColumnname(tableColumes.getColomnName());
	tc.setDatatype("varchar(255)");
	tc.setConstraint("default NULL");
	tableColumn.add(tc);
	   }
	  TableColumes tc = new TableColumes();
	
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(15)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	 cot.createTable22("feedback_category", tableColumn, connection);
	}
	} 
	   else if (table.equalsIgnoreCase("feedback_subcategory")) {
	      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration", "", connection, "", 0, "table_name", "feedback_subcategory_configuration");
	      if (colName!= null && colName.size()>0) {
	
	  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
	  for (GridDataPropertyView tableColumes : colName) {
	  if (!tableColumes.getColomnName().equals("fbType")) {
	  TableColumes tc = new TableColumes();
	tc.setColumnname(tableColumes.getColomnName());
	tc.setDatatype("varchar(255)");
	tc.setConstraint("default NULL");
	tableColumn.add(tc);
	}
	
	   }
	  TableColumes tc = new TableColumes();
	 
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(15)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  cot.createTable22("feedback_subcategory", tableColumn, connection);
	   }
	}
	flag=true;
	}
	}
	
	return flag;
	 }
	
	@SuppressWarnings("unchecked")
	public List getClientConf(SessionFactory connection)
	 {
	List list = null;
	try {
	 String query = "SELECT * FROM client_conf";
	 Session session = null;  
	 Transaction transaction = null;  
	     session=connection.getCurrentSession(); 
	     transaction = session.beginTransaction(); 
	     list=session.createSQLQuery(query).list();  
	 transaction.commit();
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	 }
	
	@SuppressWarnings("unchecked")
	public FeedbackPojo setFeedbackDataValues(List data, String status,String deptLevel)
	{
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0)
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	fbp.setTicket_no(object[0].toString());
	fbp.setFeed_registerby(object[1].toString());
	if (object[1]!=null)
	{
	fbp.setFeed_by(object[1].toString());
	}
	else
	{
	fbp.setFeed_by("NA");
	}
	
	if (object[2]!=null)
	{
	fbp.setFeedback_by_mobno(object[2].toString());
	}
	else
	{
	fbp.setFeedback_by_mobno("NA");
	}
	
	if (object[3]!=null)
	{
	fbp.setFeedback_by_emailid(object[3].toString());
	}
	else
	{
	fbp.setFeedback_by_emailid("NA");
	}
	
	fbp.setFeedback_allot_to(object[4].toString());
	fbp.setFeedback_catg(object[5].toString());
	fbp.setFeedback_subcatg(object[5].toString());
	fbp.setFeed_brief(object[7].toString());
	fbp.setFeedaddressing_time(object[8].toString());
	fbp.setOpen_date(object[9].toString());
	fbp.setOpen_time(object[10].toString());
	
	if (object[11]!=null) 
	{
	fbp.setEscalation_date(object[11].toString());
	}
	else
	{
	fbp.setEscalation_date("NA");
	}
	
	if (object[12]!=null)
	{
	fbp.setEscalation_time(object[12].toString());
	}
	else
	{
	fbp.setEscalation_time("NA");
	}
	
	fbp.setLevel(object[13].toString());
	fbp.setStatus(object[14].toString());
	fbp.setVia_from(object[15].toString());
	
	if (!status.equals("Pending"))
	{
	if (object[19]!=null && !object[19].toString().equals(""))
	{
	fbp.setAction_by(object[19].toString());	
	}
	else
	{
	fbp.setAction_by("NA");
	}
	}
	else if (object[16]!=null && !object[16].toString().equals("") && object[17]!=null && !object[17].toString().equals(""))
	            {
	fbp.setTransfer_date(object[16].toString());
	fbp.setTransfer_time(object[17].toString());
	fbp.setTransfer_reason(object[18].toString());	
	             }
	if(object[22].toString()!=null)
	{
	fbp.setFeedback_by_dept(object[22].toString());
	}
	
	if(object[23].toString()!=null)
	{
	fbp.setFeedback_to_dept(object[23].toString());	
	}
	
	fbp.setMobOne(object[20].toString());
	if (object[21]!=null)
	{
	fbp.setEmailIdOne(object[21].toString());
	}
	else
	{
	fbp.setEmailIdOne("NA");
	}
	            	
	if (object[25]!=null)
	{
	fbp.setLocation(object[25].toString());
	}
	else
	{
	fbp.setLocation("NA");
	}
	
	if (object[26]!=null)
	{
	fbp.setFeed_registerby(object[26].toString());
	}
	else
	{
	fbp.setFeed_registerby("NA");
	}
	
	if (object[27]!=null)
	{
	fbp.setCr_no(object[27].toString());
	}
	else
	{
	fbp.setCr_no("NA");
	}
	
	
	if(status!=null && (status.equalsIgnoreCase("Resolved")|| status.equalsIgnoreCase("Noted")))
	{
	if (object[28]!=null)
	{
	fbp.setResolve_date(object[28].toString());
	}
	        else
	        {
	        	fbp.setResolve_date("NA");
	        }
	                        
	if (object[29]!=null)
	        {
	               fbp.setResolve_time(object[29].toString());
	        }
	else
	{
	fbp.setResolve_time("NA");
	}
	                        
	if (object[30]!=null)
	{
	fbp.setResolve_duration(object[30].toString());
	}
	else
	{
	fbp.setResolve_duration("NA");
	}
	                        
	if (object[31]!=null)
	{
	fbp.setResolve_remark(object[31].toString());
	}
	else
	{
	fbp.setResolve_remark("NA");
	}
	           
	if(status.equalsIgnoreCase("Resolved"))
	{
	if (object[33]!=null)
	{
	fbp.setSpare_used(object[33].toString());
	}
	else
	{
	fbp.setSpare_used("NA");
	}
	
	if (object[34]!=null)
	{
	fbp.setAddr_Date_Time(object[34].toString());
	}
	else
	{
	fbp.setAddr_Date_Time("NA");
	}
	
	}
	}
	else
	{
	if (object[28]!=null)
	{
	fbp.setAddr_Date_Time(object[28].toString());
	}
	else
	{
	fbp.setAddr_Date_Time("NA");
	}
	}
	}
	}
	return fbp;
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackDataforReport(List data,String status,String deptLevel,SessionFactory connectionSpace)
	{
	List<FeedbackPojo> fbpList = new ArrayList<FeedbackPojo>();
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0) {
	for (Iterator iterator = data.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	
	if (object[0]!=null)
	{
	fbp.setTicket_no(object[0].toString());
	}
	else 
	{
	fbp.setTicket_no("NA");
	}
	
	if (object[1]!=null) {
	fbp.setFeedback_by_dept(object[1].toString());
	}
	else {
	fbp.setFeedback_by_dept("NA");
	}
	
	if (object[2]!=null) {
	fbp.setFeed_by(object[2].toString());
	}
	else {
	fbp.setFeed_by("NA");
	}
	
	if (object[3]!=null) {
	fbp.setPatMobNo(object[3].toString());
	}
	else {
	fbp.setPatMobNo("NA");
	}
	
	if (object[4]!=null) {
	fbp.setCr_no(object[4].toString());
	}
	else {
	fbp.setCr_no("NA");
	}
	
	if (object[5]!=null) {
	fbp.setFeedback_to_dept(object[5].toString());
	}
	else {
	fbp.setFeedback_to_dept("NA");
	}
	
	if (object[6]!=null) {
	fbp.setFeedback_allot_to(object[6].toString());
	}
	else {
	fbp.setFeedback_allot_to("NA");
	}
	
	if (object[7]!=null) {
	fbp.setFeedtype(object[7].toString());
	}
	else {
	fbp.setFeedtype("NA");
	}
	
	if (object[8]!=null) {
	fbp.setFeedback_catg(object[8].toString());
	}
	else {
	fbp.setFeedback_catg("NA");
	}
	
	if (object[9]!=null) {
	fbp.setFeedback_subcatg(object[9].toString());
	}
	else {
	fbp.setFeedback_subcatg("NA");
	}
	
	if (object[10]!=null) {
	fbp.setFeed_brief(object[10].toString());
	}
	else {
	fbp.setFeed_brief("NA");
	}
	
	if (object[11]!=null) {
	fbp.setLocation(object[11].toString());
	}
	else {
	fbp.setLocation("NA");
	}
	
	if (object[12]!=null) {
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));
	}
	else {
	fbp.setOpen_date("NA");
	}
	
	if (object[13]!=null) {
	fbp.setOpen_time(object[13].toString().substring(0, 5));
	}
	else {
	fbp.setOpen_time("NA");
	}
	
	if (object[14]!=null) {
	fbp.setLevel(object[14].toString());
	}
	else {
	fbp.setLevel("NA");
	}
	
	if (object[15]!=null) {
	fbp.setStatus(object[15].toString());
	}
	else {
	fbp.setStatus("NA");
	}
	
	if (object[16]!=null) {
	fbp.setVia_from(object[16].toString());
	}
	else {
	fbp.setVia_from("NA");
	}
	
	if (object[17]!=null) {
	fbp.setFeed_registerby(object[17].toString());
	}
	else {
	fbp.setFeed_registerby("NA");
	}
	
	if (status.equalsIgnoreCase("Resolved")) {
	if (object[18]!=null) {
	fbp.setResolve_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
	}
	else {
	fbp.setResolve_date("NA");
	}
	
	if (object[19]!=null) {
	fbp.setResolve_time(object[19].toString().substring(0, 5));
	}
	else {
	fbp.setResolve_time("NA");
	}
	
	if (object[20]!=null) {
	fbp.setResolve_duration(object[20].toString());
	}
	else {
	fbp.setResolve_duration("NA");
	}
	
	if (object[21]!=null) {
	fbp.setResolve_remark(object[21].toString());
	}
	else {
	fbp.setResolve_remark("NA");
	}
	
	if (object[22]!=null) {
	fbp.setResolve_by(object[22].toString());
	}
	else {
	fbp.setResolve_by("NA");
	}
	
	if (object[23]!=null) {
	fbp.setSpare_used(object[23].toString());
	}
	else {
	fbp.setSpare_used("NA");
	}
	}
	
	if (status.equalsIgnoreCase("Snooze")) {
	if (object[18]!=null) {
	fbp.setSn_reason(object[18].toString());
	}
	else {
	fbp.setSn_reason("NA");
	}
	
	if (object[19]!=null && !object[19].toString().equals("") && !object[19].toString().equals("NA")) {
	fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
	}
	else {
	fbp.setSn_on_date("NA");
	}
	
	if (object[20]!=null && !object[20].toString().equals("") && !object[20].toString().equals("NA")) {
	fbp.setSn_on_time(object[20].toString().substring(0, 5));
	}
	else {
	fbp.setSn_on_time("NA");
	}
	
	if (object[21]!=null && !object[21].toString().equals("") && !object[21].toString().equals("NA")) {
	fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
	}
	else {
	fbp.setSn_date("NA");
	}
	
	if (object[22]!=null && !object[22].toString().equals("") && !object[22].toString().equals("NA")) {
	fbp.setSn_time(object[22].toString().substring(0, 5));
	}
	else {
	fbp.setSn_time("NA");
	}
	
	if (object[23]!=null) {
	fbp.setSn_duration(object[23].toString());
	}
	else {
	fbp.setSn_duration("NA");
	}
	}
	if (status.equalsIgnoreCase("Ignore")) {
	if (object[18]!=null) {
	fbp.setIg_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
	}
	else {
	fbp.setIg_date("NA");
	}
	
	if (object[19]!=null) {
	fbp.setIg_time(object[19].toString().substring(0, 5));
	}
	else {
	fbp.setIg_time("NA");
	}
	
	if (object[20]!=null) {
	fbp.setIg_reason(object[20].toString());
	}
	else {
	fbp.setIg_reason("NA");
	}
	}
	if (status.equalsIgnoreCase("High Priority")) {
	if (object[18]!=null) {
	fbp.setHp_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
	}
	else {
	fbp.setHp_date("NA");
	}
	
	if (object[19]!=null) {
	fbp.setHp_time(object[19].toString().substring(0, 5));
	}
	else {
	fbp.setHp_time("NA");
	}
	
	if (object[20]!=null) {
	fbp.setHp_reason(object[20].toString());
	}
	else {
	fbp.setHp_reason("NA");
	}
	}
	
	/*if (deptLevel.equals("2") && (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Snooze"))) {
	if (object[24]!=null) {
	fbp.setFeedback_by_subdept(object[24].toString());
	}
	else {
	fbp.setFeedback_by_subdept("NA");
	}
	
	if (object[25]!=null) {
	fbp.setFeedback_to_subdept(object[25].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	}*/
	
	if (deptLevel.equals("2") && (status.equalsIgnoreCase("Resolved"))) {
	if (object[24]!=null) {
	fbp.setSn_reason(object[24].toString());
	}
	else {
	fbp.setSn_reason("NA");
	}
	
	if (object[25]!=null && !object[25].toString().equals("") && !object[25].toString().equals("NA")) {
	fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[25].toString()));
	}
	else {
	fbp.setSn_on_date("NA");
	}
	
	if (object[26]!=null && !object[26].toString().equals("") && !object[26].toString().equals("NA")) {
	fbp.setSn_on_time(object[26].toString().substring(0, 5));
	}
	else {
	fbp.setSn_on_time("NA");
	}
	
	if (object[27]!=null && !object[27].toString().equals("") && !object[27].toString().equals("NA")) {
	fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[27].toString()));
	}
	else {
	fbp.setSn_date("NA");
	}
	
	if (object[28]!=null && !object[28].toString().equals("") && !object[28].toString().equals("NA")) {
	fbp.setSn_time(object[28].toString().substring(0, 5));
	}
	else {
	fbp.setSn_time("NA");
	}
	
	if (object[29]!=null) {
	fbp.setSn_duration(object[29].toString());
	}
	else {
	fbp.setSn_duration("NA");
	}
	

    	if (object[30]!=null && !object[30].toString().equals("") && object[12]!=null && !object[12].toString().equals("") && object[13]!=null && !object[13].toString().equals("")) {
    	
    	fbp.setAddressingTime(DateUtil.getAddressingDatetime(object[12].toString(), object[13].toString(), "00:00", object[30].toString()));
    	}
    	else {
    	fbp.setAddressingTime("NA");
    	}
	
	if (object[31]!=null) {
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(object[31].toString()));
	}
	else {
	fbp.setEscalation_date("NA");
	}
	
	if (object[32]!=null) {
	fbp.setEscalation_time(object[32].toString());
	}
	else {
	fbp.setEscalation_time("NA");
	}
	
	}
	
	/*	if (deptLevel.equals("2") && (status.equalsIgnoreCase("Ignore") || status.equalsIgnoreCase("High Priority"))) {
	if (object[21]!=null) {
	fbp.setFeedback_by_subdept(object[21].toString());
	}
	else {
	fbp.setFeedback_by_subdept("NA");
	}
	
	if (object[22]!=null) {
	fbp.setFeedback_to_subdept(object[22].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	}*/
	
	if (deptLevel.equals("2") && status.equalsIgnoreCase("Pending")) {

	/*if (object[18]!=null) {
	fbp.setFeedback_by_subdept(object[18].toString());
	}
	else {
	fbp.setFeedback_by_subdept("NA");
	}
	
	if (object[19]!=null) {
	fbp.setFeedback_to_subdept(object[19].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}*/
	
	if (object[18]!=null) {
	fbp.setSn_reason(object[18].toString());
	}
	else {
	fbp.setSn_reason("NA");
	}
	
	if (object[19]!=null && !object[19].toString().equals("") && !object[19].toString().equals("NA")) {
	fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
	}
	else {
	fbp.setSn_on_date("NA");
	}
	
	if (object[20]!=null && !object[20].toString().equals("") && !object[20].toString().equals("NA")) {
	fbp.setSn_on_time(object[20].toString().substring(0, 5));
	}
	else {
	fbp.setSn_on_time("NA");
	}
	
	if (object[21]!=null && !object[21].toString().equals("") && !object[21].toString().equals("NA")) {
	fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
	}
	else {
	fbp.setSn_date("NA");
	}
	
	if (object[22]!=null && !object[22].toString().equals("") && !object[22].toString().equals("NA")) {
	fbp.setSn_time(object[22].toString().substring(0, 5));
	}
	else {
	fbp.setSn_time("NA");
	}
	
	if (object[23]!=null) {
	fbp.setSn_duration(object[23].toString());
	}
	else {
	fbp.setSn_duration("NA");
	}
	
                    if (object[24]!=null && !object[24].toString().equals("") && object[12]!=null && !object[12].toString().equals("") && object[13]!=null && !object[13].toString().equals("")) {
    	
    	fbp.setAddressingTime(DateUtil.getAddressingDatetime(object[12].toString(), object[13].toString(), "00:00", object[24].toString()).substring(11));
    	}
    	else {
    	fbp.setAddressingTime("NA");
    	}
	
	if (object[25]!=null) {
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(object[25].toString()));
	}
	else {
	fbp.setEscalation_date("NA");
	}
	
	if (object[26]!=null) {
	fbp.setEscalation_time(object[26].toString());
	}
	else {
	fbp.setEscalation_time("NA");
	}
	
	}
	
	if(fbp.getTicket_no()!=null)
	{
	 String tempTicketNo=null;
	if(fbp.getTicket_no().length()>4)
	  {
	  tempTicketNo=fbp.getTicket_no().substring(0, 5);
	  }
	  else
	  {
	  tempTicketNo=fbp.getTicket_no();
	  }
	StringBuilder builder=new StringBuilder("select clientId,compType,serviceBy,centerCode,problem,mobNo,emailId from feedbackdata where id=(select feed_data_id from feedback_ticket where feedTicketNo like '"+tempTicketNo+"%')");
	
	Session session = null;  
	Transaction transaction = null;
	List dataList=null;
	try 
	{  
	session=connectionSpace.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dataList=session.createSQLQuery(builder.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{
	transaction.rollback();
	} 
	
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator2 = dataList.iterator(); iterator2 .hasNext();)
	{
	Object[] object2 = (Object[]) iterator2.next();
	if(object2!=null)
	{
	//
	if(object2[0]!=null)
	{
	fbp.setCr_no(object2[0].toString());
	}
	else
	{
	fbp.setCr_no("NA");
	}
	
	if(object2[1]!=null)
	{
	fbp.setPatTyp(object2[1].toString());
	}
	else
	{
	fbp.setPatTyp("NA");
	}
	
	if(object2[2]!=null)
	{
	fbp.setDocName(object2[2].toString());
	}
	else
	{
	fbp.setDocName("NA");
	}
	
	if(object2[3]!=null)
	{
	fbp.setRoomNo(object2[3].toString());
	}
	else
	{
	fbp.setRoomNo("NA");
	}
	

	if(object2[5]!=null)
	{
	fbp.setPatMobNo(object2[5].toString());
	}
	else
	{
	fbp.setPatMobNo("NA");
	}
	
	if(object2[6]!=null)
	{
	fbp.setPatEmailId(object2[6].toString());
	}
	else
	{
	fbp.setPatEmailId("NA");
	}
	//
	}
	}
	}
	
	//	List dataPat=new createTable().executeAllSelectQuery(builder.toString(), connectionSpace);
	}
	
	
	
	/*if (deptLevel.equals("2") && status.equalsIgnoreCase("")) {
	if (object[18]!=null) {
	fbp.setFeedback_by_subdept(object[18].toString());
	}
	else {
	fbp.setFeedback_by_subdept("NA");
	}
	
	if (object[19]!=null) {
	fbp.setFeedback_to_subdept(object[19].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	}*/
	fbpList.add(fbp);	
	}
	}
	return fbpList;
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackDataforReport(List data,String status,String deptLevel)
	{
	List<FeedbackPojo> fbpList = new ArrayList<FeedbackPojo>();
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0) {
	for (Iterator iterator = data.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	
	if (object[0]!=null) {
	fbp.setTicket_no(object[0].toString());
	}
	else {
	fbp.setTicket_no("NA");
	}
	
	if (object[1]!=null) {
	fbp.setFeedback_by_dept(object[1].toString());
	}
	else {
	fbp.setFeedback_by_dept("NA");
	}
	
	if (object[2]!=null) {
	fbp.setFeed_by(object[2].toString());
	}
	else {
	fbp.setFeed_by("NA");
	}
	
	if (object[3]!=null) {
	fbp.setFeedback_by_mobno(object[3].toString());
	}
	else {
	fbp.setFeedback_by_mobno("NA");
	}
	
	if (object[4]!=null) {
	fbp.setFeedback_by_emailid(object[4].toString());
	}
	else {
	fbp.setFeedback_by_emailid("NA");
	}
	
	if (object[5]!=null) {
	fbp.setFeedback_to_dept(object[5].toString());
	}
	else {
	fbp.setFeedback_to_dept("NA");
	}
	
	if (object[6]!=null) {
	fbp.setFeedback_allot_to(object[6].toString());
	}
	else {
	fbp.setFeedback_allot_to("NA");
	}
	
	if (object[7]!=null) {
	fbp.setFeedtype(object[7].toString());
	}
	else {
	fbp.setFeedtype("NA");
	}
	
	if (object[8]!=null) {
	fbp.setFeedback_catg(object[8].toString());
	}
	else {
	fbp.setFeedback_catg("NA");
	}
	
	if (object[9]!=null) {
	fbp.setFeedback_subcatg(object[9].toString());
	}
	else {
	fbp.setFeedback_subcatg("NA");
	}
	
	if (object[10]!=null) {
	fbp.setFeed_brief(object[10].toString());
	}
	else {
	fbp.setFeed_brief("NA");
	}
	
	if (object[11]!=null) {
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(object[11].toString()));
	}
	else {
	fbp.setOpen_date("NA");
	}
	
	if (object[12]!=null) {
	fbp.setOpen_time(object[12].toString().substring(0, 5));
	}
	else {
	fbp.setOpen_time("NA");
	}
	
	if (object[15]!=null) {
	fbp.setLevel(object[15].toString());
	}
	else {
	fbp.setLevel("NA");
	}
	
	if (object[16]!=null) {
	fbp.setStatus(object[16].toString());
	}
	else {
	fbp.setStatus("NA");
	}
	
	if (object[17]!=null) {
	fbp.setFeed_registerby(object[17].toString());
	}
	else {
	fbp.setFeed_registerby("NA");
	}
	
	if (object[23]!=null) {
	fbp.setLocation(object[23].toString());
	}
	else {
	fbp.setLocation("NA");
	}
	
	if (status.equalsIgnoreCase("Resolved"))
	 {
	if (object[18]!=null) {
	   fbp.setResolve_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
	}
	else {
	   fbp.setResolve_date("NA");
	}
	if (object[19]!=null) {
	   fbp.setResolve_time(object[19].toString().substring(0, 5));
	}
	else {
	   fbp.setResolve_time("NA");
	}
	if (object[20]!=null) {
	   fbp.setResolve_duration(object[20].toString());
	}
	else {
	   fbp.setResolve_duration("NA");
	}
	if (object[21]!=null) {
	  fbp.setResolve_remark(object[21].toString());
	}
	else {
	  fbp.setResolve_remark("NA");
	}
	
	if (object[22]!=null) {
	  fbp.setResolve_by(object[22].toString());
	}
	else {
	  fbp.setResolve_by("NA");
	}
	}
	
	
	if (deptLevel.equals("2") && (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Snooze"))) {
	if (object[24]!=null) {
	fbp.setFeedback_by_subdept(object[24].toString());
	}
	else {
	fbp.setFeedback_by_subdept("NA");
	}
	
	if (object[25]!=null) {
	fbp.setFeedback_to_subdept(object[25].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	}
	
	
	if (deptLevel.equals("2") && status.equalsIgnoreCase("Pending")) {
	if (object[18]!=null) {
	fbp.setFeedback_by_subdept(object[18].toString());
	}
	else {
	fbp.setFeedback_by_subdept("NA");
	}
	
	if (object[19]!=null) {
	fbp.setFeedback_to_subdept(object[19].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	}
	fbpList.add(fbp);	
	}
	}
	return fbpList;
	}
	
	@SuppressWarnings("unchecked")
	public List getReportToData( String date, String time,SessionFactory connection)
	 {
	List  reportdatalist = new ArrayList();
	StringBuffer query= new StringBuffer();
	query.append("select emp.empName,emp.mobOne,emp.emailIdOne,report_conf.email_subject,report_conf.report_level,report_conf.dept_subdept,report_conf.deptLevel,report_conf.sms,report_conf.mail,report_conf.report_type,report_conf.id from report_configuration as report_conf ");
	query.append(" inner join employee_basic as emp on report_conf.empId=emp.id ");
	query.append(" where report_conf.report_date='"+date+"' and report_conf.report_time<='"+time+"'");
	Session session = null;  
	    Transaction transaction = null;  
	try 
	  {  
	 session=connection.getCurrentSession(); 
	 transaction = session.beginTransaction(); 
	 reportdatalist=session.createSQLQuery(query.toString()).list();  
	 transaction.commit(); 
	  }
	    catch (Exception ex)
	  {
	 transaction.rollback();
	  } 
	
	return reportdatalist;
	 }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackList(String subDeptId, String feedTypeId, String categoryId,String deptLevel,SessionFactory connection)
	 {
	List  categorylist = new ArrayList();
	StringBuffer str  =new StringBuffer();
	str.append("select feedtype.fbType,catg.categoryName,subcatg.subCategoryName,subcatg.feedBreif,subcatg.addressingTime,subcatg.resolutionTime,subcatg.escalationDuration,subcatg.escalationLevel,subcatg.needEsc from feedback_category as catg");
	str.append(" inner join feedback_type as feedtype on catg.fbType=feedtype.id");
	if (deptLevel.equals("2")) {
	str.append(" inner join subdepartment as subdept on feedtype.dept_subdept=subdept.id ");
	}
	else if (deptLevel.equals("1")) {
	str.append(" inner join department as dept on feedtype.dept_subdept=dept.id ");
	}
	
	str.append(" inner join feedback_subcategory as subcatg on catg.id=subcatg.categoryName");
	str.append(" where ");
	if (subDeptId!=null && !subDeptId.equals("") && !subDeptId.equals("-1")) {
	str.append(" subdept.id ="+subDeptId+"");
	}
	if (feedTypeId!=null && !feedTypeId.equals("") && !feedTypeId.equals("-1")) {
	str.append(" and feedtype.id ="+feedTypeId+" ");
	}
	if (categoryId!=null && !categoryId.equals("") && !categoryId.equals("-1")) {
	str.append(" and catg.id ="+categoryId+"");
	}
	str.append(" and feedtype.deptHierarchy="+deptLevel+" order by catg.categoryName ASC");
	Session session = null;  
	    Transaction transaction = null;  
	try 
	  {  
	 session=connection.getCurrentSession(); 
	 transaction = session.beginTransaction(); 
	 categorylist=session.createSQLQuery(str.toString()).list();  
	 transaction.commit(); 
	  }
	    catch (Exception ex)
	  {
	 transaction.rollback();
	  } 
	
	    
	return categorylist;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEscalation( String deptLevel, String dept, String levelname,SessionFactory connection)
	 {
	String shiftname =DateUtil.getCurrentDateUSFormat().substring(8, 10)+"_date";
	List  escalation = new ArrayList();
	String query="select emp.empName,emp.mobOne,emp.emailIdOne from employee_basic as emp"
	+" inner join designation desg on emp.designation = desg.id"
	+" inner join subdepartment sub_dept on emp.subdept = sub_dept.id"
	+" inner join department dept on sub_dept.deptid = dept.id"
                    +" inner join roaster_conf roaster on emp.empId = roaster.empId"
                    +" inner join shift_conf shift on sub_dept.id = shift.dept_subdept"
	            +" where shift.shiftName="+shiftname+" and emp.status='0' and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='"+levelname+"' and dept.id='"+dept+"' and roaster.deptHierarchy='"+deptLevel+"'";
	    Session session = null;  
	    Transaction transaction = null;  
	    try 
	     {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	escalation=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	    catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	
	return escalation;
	    }
	
	public String getNewDate(String newDateType)
	 {
	String data="";
	 try 
	{
	 if (newDateType.equals("D")) {
	 data = DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("W")) {
	data = DateUtil.getNewDate("day", 7, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("M")) {
	data = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("Q")) {
	data = DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("H")) {
	data = DateUtil.getNewDate("month", 6, DateUtil.getCurrentDateUSFormat());
	}
             else if (newDateType.equals("Y")) {
            	 data = DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat());
	}
	} catch (Exception e) {
	e.printStackTrace();
	} 
	return data;
	 }
	
	@SuppressWarnings("unchecked")
	public String getDeptName(String subdept_dept, String deptLevel, SessionFactory connection)
	 {
	 String data="",query="";
	 List list =null;
	
	 if (deptLevel.equals("2")) {
	 query = "select deptName from department where id=(select deptid from subdepartment where id="+subdept_dept+")";
	}
	
            else if (deptLevel.equals("1")) {
            	query = "select deptName from department where id="+subdept_dept+"";
	}
	    Session session = null;  
	Transaction transaction = null;  
	 try 
	{
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	
	 if(list!=null && list.size()>0)
	            {
	 data=list.get(0).toString();
	            }
	return data;
	 }
	
	@SuppressWarnings("unchecked")
	public List getTicketCounters( String reportLevel, boolean cd_pd,String subdept_dept,String deptLevel,SessionFactory connection)
	 {
	List  counterList = new ArrayList();
	String query="";
	if (deptLevel.equals("2")) 
	{
	if (reportLevel.equals("2")) {
	if (cd_pd) {
	query="select count(*),status from feedback_status_feedback where open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status";
	}
	else {
	query="select count(*),status from feedback_status_feedback where open_date<'"+DateUtil.getCurrentDateUSFormat()+"' group by status";
	}
	}
	else if (reportLevel.equals("1")) {
	if (cd_pd) {
	query="select count(*),status from feedback_status_feedback where open_date='"+DateUtil.getCurrentDateUSFormat()+"' and to_dept_subdept in ("+subdept_dept+") and deptHierarchy="+deptLevel+" group by status";
	}
	else {
	query="select count(*),status from feedback_status_feedback where open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and to_dept_subdept in ("+subdept_dept+") and deptHierarchy="+deptLevel+" group by status";
	}
	}
	}
	else if (deptLevel.equals("1")){
	if (reportLevel.equals("2")) {
	if (cd_pd) {
	query="select count(*),status from feedback_status_feedback where open_date='"+DateUtil.getCurrentDateUSFormat()+"' and to_dept_subdept="+subdept_dept+" and deptHierarchy="+deptLevel+" group by status";
	}
	else {
	query="select count(*),status from feedback_status_feedback where open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and to_dept_subdept="+subdept_dept+" and deptHierarchy="+deptLevel+" group by status";
	}
	}
	else if (reportLevel.equals("1")) {
	if (cd_pd) {
	query="select count(*),status from feedback_status_feedback where open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status";
	}
	else {
	query="select count(*),status from feedback_status_feedback where open_date<'"+DateUtil.getCurrentDateUSFormat()+"' group by status";
	}
	}
	}
	
	//System.out.println("Querry is as >>>>>>>>>>>>>>>>>>>"+query);
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	counterList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	
	return counterList;
	 }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketData( String reportLevel, boolean cd_pd, String  status,String subdept_dept,String deptLevel,SessionFactory connection)
	 {
	List  dataList = new ArrayList();
	  List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
	  StringBuilder query = new StringBuilder("");
	  query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid," +
	" dept2.deptName as todept,emp.empName,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief," +
	" subcatg.addressingTime,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time" +
	",feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.transfer_date,feedback.transfer_time," +
	"feedback.transfer_reason,feedback.action_by ,feedback.location ");
	if (status.equalsIgnoreCase("Resolved"))
	    {
	          query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
	        }
	query.append(" from feedback_status_feedback as feedback" +
	" inner join employee_basic emp on feedback.allot_to= emp.id" +
	" inner join department subdept1 on feedback.by_dept_subdept= subdept1.id " +
	" inner join department subdept2 on feedback.to_dept_subdept= subdept2.id" +
	" inner join department dept1 on subdept1.id= dept1.id " +
	" inner join department dept2 on subdept2.id= dept2.id " +
	" inner join feedback_subcategory_feedback subcatg on feedback.subcatg = subcatg.id " +
	" inner join feedback_category_feedback catg on subcatg.categoryName = catg.id");
        if (status.equalsIgnoreCase("Resolved"))
         {
           query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
         }

	        	// getting data of current day
	if (cd_pd) {
	if (reportLevel.equals("2")) {
	  query.append(" where feedback.status='"+status+"' and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	else if (reportLevel.equals("1")) {
	 query.append(" where feedback.status='"+status+"' and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and feedback.to_dept_subdept in ("+subdept_dept+") and feedback.deptHierarchy="+deptLevel+""); 
	}
	}
	        // End of If Block for getting the data for only the current date in both case for Resolved OR All
	
	// Else Block for getting the data for only the previous date
	else {
	if (reportLevel.equals("2")) {
	  query.append(" where feedback.status in ('Re-Assign', 'Pending', 'Resolved') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	else if (reportLevel.equals("1")) {
	 query.append(" where feedback.status in ('Re-Assign', 'Pending', 'Resolved') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and feedback.to_dept_subdept in ("+subdept_dept+") and feedback.deptHierarchy="+deptLevel+""); 
	}
	}
	//System.out.println("Query for data  "+query.toString());
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dataList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	//	System.out.println("Data is as >>>>>>>>>>>>>>>>>>>>>>>"+dataList.size());
	if (dataList!=null && dataList.size()>0) {
	report = new FeedbackUniversalHelper().setFeedbackDataforReport(dataList, status, deptLevel);
	}
	return report;
	 }
	
	
	@SuppressWarnings("unchecked")
	public List getTicketData( String id,SessionFactory connection)
	 {
	    List  dataList = new ArrayList();
	    StringBuilder query = new StringBuilder("");
	query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");
        query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
        query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
        query.append(",subdept1.subdeptname as bysubdept,subdept2.subdeptname as tosubdept");
        query.append(" from feedback_status as feedback");
        query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	query.append(" inner join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id "); 
	query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
	query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id ");
	query.append(" inner join department dept1 on subdept1.deptid= dept1.id  "); 
	query.append(" where  feedback.id="+id+""); 
	
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dataList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	
	return dataList;
	 }
	
	
	 @SuppressWarnings("unchecked")
	public boolean isExist(String table,String field,String value,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	query1.append("select * from "+table+" where "+field+"='"+value+"'");
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{
	flag=true;
	}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	 
	 @SuppressWarnings("unchecked")
	public boolean isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	    	query1.append("select * from "+table+" where "+field1+"='"+value1+"'");
	    	if (value2!=null && !value2.equals("")) {
	    	query1.append(" and "+field2+"='"+value2+"'");
	}
	    	if (value3!=null && !value3.equals("")) {
	    	query1.append(" and "+field3+"='"+value3+"'");
	}
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{
	flag=true;
	}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	 
	 @SuppressWarnings("unchecked")
	public boolean checkDept_SubDept(String uid,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from feedback_type where dept_subdept=(select subdept from employee_basic where useraccountid="+uid+")");
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{flag=true;}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List getSubDeptList( String subdeptid,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	String query="";
	    query="select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdeptid+"";
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getSubDeptListByUID(String uid,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	StringBuffer query=new StringBuffer();
	query.append("select distinct subdept.id,subdept.subdeptname from employee_basic as emp ");
	    query.append(" inner join subdepartment subdept on emp.subdept=subdept.id ");
	    query.append(" inner join department dept on subdept.deptid= dept.id ");
	    query.append(" where dept.id=(select sdept.deptid from subdepartment as sdept where sdept.id=(select empstats.subdept from employee_basic as empstats where empstats.useraccountid="+uid+")) order by subdept.subdeptname ");
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getDistinctData(String table,String field,SessionFactory connection)
	 {
	List  distinctDataList = new ArrayList();
	StringBuffer query=new StringBuffer();
	query.append("select distinct "+field+" from "+table+" order by "+field+"");
	
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	distinctDataList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return distinctDataList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEmployeeData( String dept_subdeptid,String deptLevel,String searchField, String searchString, String searchOper,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	StringBuffer str = new StringBuffer();
	if (deptLevel.equals("2")) {
	str.append("select emp.id,emp.empId,emp.empName,emp.mobOne,emp.emailIdOne,desg.levelofhierarchy,subdept.subdeptname from employee_basic as emp");
	str.append(" inner join designation as desg on desg.id=emp.designation");
	str.append(" inner join subdepartment as subdept on subdept.id=emp.subdept");
	str.append(" where emp.subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id="+dept_subdeptid+")) and emp.escalationId=0 and emp.status=0");
	  if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	  str.append(" and");
          	  
	  if(searchOper.equalsIgnoreCase("eq"))
	{
	  str.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	str.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	str.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	str.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	str.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	  str.append(";");
	}
	else if (deptLevel.equals("1")) {
	str.append("select emp.id,emp.empId,emp.empName,emp.mobOne,emp.emailIdOne,desg.levelofhierarchy from employee_basic as emp");
	str.append(" inner join designation as desg on desg.id=emp.designation");
	str.append(" inner join department as dept on dept.id=emp.dept");
	str.append(" where emp.dept="+dept_subdeptid+" and emp.escalationId=0");
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	  str.append(" and");
       	  
	  if(searchOper.equalsIgnoreCase("eq"))
	{
	  str.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	str.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	str.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	str.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	str.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	  str.append(";");
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(str.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getRoasterList(boolean flag, String id, String deptLevel, SessionFactory connection)
	 {
	List roasterList = new ArrayList();
	String query="";
	if (flag) {
	query="select * from roaster_conf where deptHierarchy="+deptLevel+" and dept_subdept in(select id from subdepartment where deptid="+id+")";
	}
	else {
	query="select * from roaster_conf where deptHierarchy="+deptLevel+" and dept_subdept="+id+"";
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	roasterList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return roasterList;
	 }
	
	@SuppressWarnings("unchecked")
	public String getValueId( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2, String deptLevel, String fieldvalue3,SessionFactory connection)
	 {
	boolean flag =false;
	String Id=null,query="";
	List  list = new ArrayList();
	query="select id from "+table+" where "+fieldName1+"="+fieldvalue1+" and "+fieldName2+"='"+fieldvalue2+"'";
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	if (list!=null && list.size()<1) {
	list.clear();
	if (table.equalsIgnoreCase("feedback_type")) {
	query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",deptHierarchy,hide_show,userName,date,time) values ("+fieldvalue1+",'"+fieldvalue2+"',"+deptLevel+",1,'"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"');";
	}
	else if (table.equalsIgnoreCase("feedback_category")) {
	query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time) values ("+fieldvalue1+",'"+fieldvalue2+"',1,'"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"');";
	}
	    
	Session session1 = null;  
	Transaction transaction1 = null;  
	try 
	 {  
	session1=connection.getCurrentSession(); 
	transaction1 = session1.beginTransaction(); 
	Query qry=session1.createSQLQuery(query);
	    qry.executeUpdate();
	flag = true;
	transaction1.commit(); 
	 }
	catch (Exception ex)
	{transaction1.rollback();} 
	finally{session1.close();}
	
	    if (flag) {
	    query="select max(id) from "+table+" where "+fieldName1+"="+fieldvalue1+"";
	Session session2 = null;  
	Transaction transaction2 = null;  
	try 
	 {  
	session2=connection.getCurrentSession(); 
	transaction2 = session2.beginTransaction(); 
	list=session2.createSQLQuery(query).list();  
	transaction2.commit(); 
	 }
	catch (Exception ex)
	{transaction2.rollback();} 
	finally{session2.close();}
	   }
	}
	if (list!=null && list.size()>0) {
	Id=list.get(0).toString();
	}
	return Id;
	 }
	
	public SessionFactory session4File()
	{
	SessionFactory connection = null;
	String dbbname = "1_clouddb", ipAddress = null, username = null, paassword = null, port = null;
	try {
	  String dbDetails = new IPAddress().getDBDetails();
	  String db[]=dbDetails.split(",");
	  ipAddress=db[0];
	  username=db[1];
	  paassword=db[2];
	  port=db[3];
	
	  AllConnection Conn = new ConnectionFactory(dbbname,ipAddress, username, paassword, port);
	  AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
	  connection = Ob1.getSession();
	} catch (Exception e) {
	e.printStackTrace();
	}
	return connection;
	}
	
	@SuppressWarnings("unchecked")
	public List getNormalDashboardData(String status,String fromDate, String toDate,SessionFactory connection)
	 {
	List  dashList = new ArrayList();
	StringBuffer query= new StringBuffer();
	query.append("select status,count(*) from feedback_status group by status order by status");
	if (status.equals("CL")) {
	query.append("inner join employee_basic as emp on emp.mobOne=feedback.feed_by_mobno"); 
	}
	else if (status.equals("CR")) {
	query.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
	}
	query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	query.append("where feedback.open_date between '"+fromDate+"' and '"+toDate+"'");
	query.append("group by feedback.status order by feedback.status");
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getDeptDashboardData(String qryfor,SessionFactory connection)
	 {
	List  dashDeptList = new ArrayList();
	String query="";
	    if (qryfor.equalsIgnoreCase("dept")) {
	    	query="select distinct dept.deptName,dept.id from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" inner join department dept on subdept.deptid= dept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' order by feedback.status";
	}
	    else if (qryfor.equalsIgnoreCase("subdept")) {
	    	query="select distinct subdept.subdeptname,subdept.id from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
	}
	    else if (qryfor.equalsIgnoreCase("counter")) {
	    	query="select distinct dept.deptName,dept.id,feedback.status,count(*) from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" inner join department dept on subdept.deptid= dept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
	}
	    else if (qryfor.equalsIgnoreCase("subdept_counter")) {
	    	query="select distinct subdept.subdeptname,subdept.id,feedback.status,count(*) from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashDeptList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashDeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getDashboardCounter(String qryfor,String status,String dept_subdeptid,SessionFactory connection)
	 {
	List  dashDeptList = new ArrayList();
	StringBuffer query=new StringBuffer();
	// Query for getting SubDept List
	     if (qryfor.equalsIgnoreCase("dept")) {
	    	 query.append("select  feedback.status,count(*) from feedback_status as feedback ");
	    	 query.append(" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id");
	    	 query.append(" inner join department dept on subdept.deptid= dept.id");
	    	 if (status.equalsIgnoreCase("All")) {
	    	 query.append(" where dept.id="+dept_subdeptid+"  group by feedback.status order by feedback.status");
	}
	    	 else if (status.equalsIgnoreCase("Resolved")) {
	    	 query.append(" where dept.id="+dept_subdeptid+" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by feedback.status order by feedback.status");
	}
	     }
	    else if (qryfor.equalsIgnoreCase("subdept")) {
	    	 query.append("select  feedback.status,count(*) from feedback_status as feedback ");
	    	 query.append(" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id");
	    	 if (status.equalsIgnoreCase("All")) {
	    	 query.append(" where subdept.id="+dept_subdeptid+" group by feedback.status order by feedback.status");
	}
	    	 else if (status.equalsIgnoreCase("Resolved")) {
	    	 query.append(" where subdept.id="+dept_subdeptid+" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by feedback.status order by feedback.status");
	}
	    }
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashDeptList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashDeptList;
	 }
	
	// get counts of tickets on the basis of status
	@SuppressWarnings("unchecked")
	public List getTicketsCounts(String countsfor,String UID,String fromDate,String toDate,SessionFactory connection)
	 {
	List  dashList = new ArrayList();
	StringBuffer query= new StringBuffer();
	// Query for getting SubDept List
	query.append("select feed_stats.status,count(*) from feedback_status as feed_stats");
	if (countsfor.equals("CL")) {
	      query.append(" inner join employee_basic as emp on emp.mobOne=feed_stats.feed_by_mobno");
	      query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	      query.append(" where userac.id='"+UID+"' and");
	}
	else if (countsfor.equals("CR")) {
	  query.append(" inner join employee_basic as emp on emp.id=feed_stats.allot_to");
	  query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	  query.append(" where userac.id='"+UID+"' and");
	}
	else if (countsfor.equals("HOD")) {
	  query.append(" inner join employee_basic as emp on emp.id=feed_stats.allot_to");
	  query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	  query.append(" where userac.id='"+UID+"' and feed_stats.to_dept_subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id= emp.subdept)) and");
	}
	else if (countsfor.equals("MGMT")) {
	 query.append(" where ");
	}
	 query.append("  open_date between '"+fromDate+"' and '"+toDate+"'");
	 query.append(" group by status order by status");
	   
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashList;
	 }
	
	@SuppressWarnings("unchecked")
	public String getLatestTicket(String seriesType,String deptid, SessionFactory connection)
	 {
	 String ticketno="",query="";
	 List list =null;
	 if (seriesType.equals("N")) {
	 query = "select feed_status.ticket_no from feedback_status as feed_status"
                 +" where feed_status.id=(select max(feed_status.id) from feedback_status)";
	}
	 else if (seriesType.equals("D")) {
	 query = "select feed_status.ticket_no from feedback_status as feed_status"
                 +" where feed_status.id=(select max(feed_status.id) from feedback_status as feed_status where feed_status.to_dept_subdept in (select id from subdepartment where deptid="+deptid+"))";
	}
	  Session session = null;  
	Transaction transaction = null;  
	 try 
	{
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	 if(list!=null && list.size()>0)
	            {
	  ticketno=list.get(0).toString();
	            }
	return ticketno;
	 }
	
	public String getDeptId(String subDept,SessionFactory connection)
    {
            String id=null;
            String str="select deptid from subdepartment where id='"+subDept+"' ";
            List data=new createTable().executeAllSelectQuery(str, connection);
            if(data!=null & data.size()>0)
            {
                    for (Iterator iterator = data.iterator(); iterator.hasNext();)
                    {
                            Object object = (Object) iterator.next();
                            if(object!=null)
                            {
                                    id=object.toString();
                            }
                    }
            }
            return id;
    }
	
	public String getConfigMessage(FeedbackPojo feedbackObj,String title,String status,String deptLevel,boolean flag,String orgName) 
	{
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_registerby())+ ",</b>");	
	}
	   
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag)
	    {
	    	if(status!=null && status.equalsIgnoreCase("Pending"))
	    	{
	    	mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    	}
	    	else if(status!=null && status.equalsIgnoreCase("Resolved"))
	    	{
	    	mailtext.append("Here is a ticket details actioned by you:- <br>");
	    	}
	    	
	    }
	    else if (!flag)
	    {
	    	if(status!=null && status.equalsIgnoreCase("Pending"))
	    	{
	    	mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");
	    	}
	    	else if(status!=null && status.equalsIgnoreCase("Resolved"))
	    	{
	    	mailtext.append("Here is a feedback resoled detail in mail body as opened by you:-<br>");
	    	}
	    	
	    }
	    mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> CR&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getCr_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Room&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.getCurrentDateIndianFormat()+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.getCurrentTime().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Patient Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_by() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Addressing&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.newTime(feedbackObj.getFeedaddressing_time()).substring(0, 5)+ " Hrs</td></tr>");
	
        if (status.equalsIgnoreCase("Resolved")) {
           mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Root Cause:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
    	   mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> CAPA:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
	    }
	    mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	    mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	   }

	public String getConfigMailForReport(int pc,int hc,int sc,int ic,int rc,int total,int cfpc,int cfsc,int cfhc,int cftotal,String empname, List<FeedbackPojo> currentDayResolvedData,List<FeedbackPojo> currentDayPendingData,List<FeedbackPojo> currentDaySnoozeData,List<FeedbackPojo> currentDayHPData,List<FeedbackPojo> currentDayIgData,List<FeedbackPojo> CFData)
	  {
	StringBuilder mailtext=new StringBuilder("");
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>BLK Super Speciality Hospital Pusa Road, New Delhi</b></td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr></tbody></table>");
	        mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
	        mailtext.append("<br><br><b>Hello!!!</b>");
	        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Summary Status For All Department On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime()+"</b></td></tr></tbody></table>");
	 
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Pending</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/D Total</b></td></tr>");
	        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+pc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+hc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+sc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ic+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+rc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfpc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfsc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfhc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+total+"</td></tr></tbody></table>");
	     
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>By&nbsp;Dept</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;On</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Sub&nbsp;Dept.</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	        if (currentDayResolvedData!=null && currentDayResolvedData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayResolvedData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (currentDayPendingData!=null && currentDayPendingData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayPendingData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#cc0066' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#cc0066' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (currentDaySnoozeData!=null && currentDaySnoozeData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDaySnoozeData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='##b2b2f7' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='##b2b2f7' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (currentDayHPData!=null && currentDayHPData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayHPData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#fc6666' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#fc6666' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (currentDayIgData!=null && currentDayIgData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayIgData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#677279' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#677279' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (CFData!=null && CFData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:CFData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#f7b3b3' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_subdept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#f7b3b3' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
	       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
	     
	        return mailtext.toString() ; 
	}
	
	@SuppressWarnings("unchecked")
	public String getTicketNo(String deptid,SessionFactory connectionSpace)
	 {
	String ticketno="NA",ticket_type="",ticket_series="",prefix="",sufix="",ticket_no="";
	List ticketSeries = new ArrayList();
	List deptTicketSeries =new ArrayList();
	try {
	   // Code for getting the Ticket Type from table
	   ticketSeries = new HelpdeskUniversalHelper().getDataFromTable("ticket_series_conf", null, null, null, null, connectionSpace);
	   if (ticketSeries!=null && ticketSeries.size()==1) {
	for (Iterator iterator = ticketSeries.iterator(); iterator
	.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	ticket_type=object[1].toString();
	ticket_series=object[2].toString();
	}
	  // Code for getting the first time counters of Feedback Status table, If get ticket counts greater than Zero than go in If block  and if get 0 than go in else block 	
	  ticket_no= new HelpdeskUniversalHelper().getLatestTicket(ticket_type,deptid,"FM", connectionSpace);
	  if (ticket_no!=null && !ticket_no.equals("")) 
	  {
	  if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("N")) {
	  AN.set(Integer.parseInt(ticket_no));
	  ticketno=prefix+AN.incrementAndGet();
	 }
	 else if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("D")) {
	  prefix=ticket_no.substring(0, 2);
	  sufix=ticket_no.substring(2, ticket_no.length());
	  AN.set(Integer.parseInt(sufix));
	  ticketno=prefix+AN.incrementAndGet();
	 }
	  }
	  else {
	if (ticket_type.equalsIgnoreCase("N")) {
	if (ticket_series!=null && !ticket_series.equals("") && !ticket_series.equals("NA")) {
	ticketno=ticket_series;
	}
	}
	else if (ticket_type.equalsIgnoreCase("D")) {
	deptTicketSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptid, "prefix", "ASC",connectionSpace);
	                        if (deptTicketSeries!=null && deptTicketSeries.size()==1) {
	for (Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();) {
	Object[] object1 = (Object[]) iterator2.next();
	if (object1[2]!=null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA")) {
	ticketno=object1[2].toString()+object1[3].toString();
	}
	else {
	ticketno="NA";
	}
	}
	                      }
	}
	 }
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return ticketno;
	}
	
	public String getDesignationLevel(String userName,SessionFactory connectionSpace)
	{
	String level=null;
	try
	{ 
	String encptUid=Cryptography.encrypt(userName,DES_ENCRYPTION_KEY);
	StringBuilder query1=new StringBuilder("");
	query1.append("SELECT design.levelofhierarchy FROM designation AS design INNER JOIN employee_basic AS emp ON emp.designation=design.id " +
	"INNER JOIN useraccount AS ac ON ac.id=emp.useraccountid WHERE ac.userID='"+encptUid+"'");
	List dataList=new createTable().executeAllSelectQuery(query1.toString(),connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	level=dataList.get(0).toString();
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return level;
	}
	
	
	public String getConfigMailForReportFeedback(int pc,int rc,int rAc,int total,int cfpc,int cfrAc,int cftotal,String empname, List<FeedbackPojo> currentDayResolvedData,List<FeedbackPojo> currentDayPendingData,List<FeedbackPojo> currentDayRAsgnData,List<FeedbackPojo> CFData,String dept)
	{

	StringBuilder mailtext=new StringBuilder("");
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody></table>");
	        mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
	        mailtext.append("<br><br><b>Hello!!!</b>");
	        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Summary Status For "+dept+" On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime()+"</b></td></tr></tbody></table>");
	 
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Re-Assign</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Pending</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Re-Assign</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/D Total</b></td></tr>");
	        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+pc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+rAc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+rc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfrAc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfpc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+total+"</td></tr></tbody></table>");
	     
	       if ((currentDayResolvedData!=null && currentDayResolvedData.size()>0) || (currentDayPendingData!=null && currentDayPendingData.size()>0) || (currentDayRAsgnData!=null && currentDayRAsgnData.size()>0)  || (CFData!=null && CFData.size()>0))
	        {
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>By&nbsp;Dept</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;On</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Level</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>");
	        if (currentDayResolvedData!=null && currentDayResolvedData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayResolvedData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (currentDayPendingData!=null && currentDayPendingData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayPendingData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%' bgcolor='#cc0066' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%'  bgcolor='#cc0066' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (currentDayRAsgnData!=null && currentDayRAsgnData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:currentDayRAsgnData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%' bgcolor='#677279' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%' bgcolor='#677279' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	           }
	       	 }  
	        mailtext.append("</tbody></table>");
	        
	        
	        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	        if (CFData!=null && CFData.size()>0)
	        {
	       	 int i=0;
	     	 for(FeedbackPojo FBP:CFData)
	       	  {
	int k=++i;
	if(k%2!=0)
	{
	mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%' bgcolor='#f7b3b3' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.makeTitle(FBP.getFeed_by())+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_by_dept()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLevel()+"</td><td align='left' width='12%' bgcolor='#f7b3b3' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td></tr>");
	}
	            }
	       	 }  
	        mailtext.append("</tbody></table>");
	    	
	}       
	        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
	       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
	     
	        return mailtext.toString() ; 
	}
	
	
	public AtomicInteger getAN() {
	return AN;
	}
	public void setAN(AtomicInteger an) {
	AN = an;
	}


}