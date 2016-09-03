package com.Over2Cloud.ctrl.feedback;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import hibernate.common.HibernateSessionFactory;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.hibernate.*;

public class LodgeFeedbackHelper
{

    private AtomicInteger AN;

    public LodgeFeedbackHelper()
    {
        AN = new AtomicInteger();
    }

    public List getEmpDataByUserName(String uName, String deptLevel)
    {
        List empList = new ArrayList();
        String empall = null;
        try
        {
            Map session = ActionContext.getContext().getSession();
            SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
            if(deptLevel.equals("2"))
            {
                empall = (new StringBuilder("select emp.empName,emp.mobOne,emp.emailIdOne,dept.id, dept.deptName,emp.id as empId from employe" +
"e_basic as emp inner join department as dept on dept.id=emp.deptname inner join " +
"useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
)).append(uName).append("'").toString();
            } else
            {
                empall = (new StringBuilder("select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName f" +
"rom employee_basic as emp inner join department as dept on emp.dept=dept.id inne" +
"r join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
)).append(uName).append("'").toString();
            }
            empList = (new createTable()).executeAllSelectQuery(empall, connectionSpace);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return empList;
    }

    public FeedbackPojo getPatientDetails(String id, SessionFactory connectionSpace)
    {
        FeedbackPojo pojo = null;
        StringBuffer buffer = new StringBuffer((new StringBuilder("select clientName,centerCode,mobNo,emailId,clientId from feedbackdata where id='")).append(id).append("'").toString());
        List data = (new createTable()).executeAllSelectQuery(buffer.toString(), connectionSpace);
        if(data != null && data.size() > 0)
        {
            for(Iterator iterator = data.iterator(); iterator.hasNext();)
            {
                Object object[] = (Object[])iterator.next();
                if(object != null)
                {
                    pojo = new FeedbackPojo();
                    if(object[0] != null)
                    {
                        pojo.setName(object[0].toString());
                    } else
                    {
                        pojo.setName("NA");
                    }
                    if(object[1] != null)
                    {
                        pojo.setCentreCode(object[1].toString());
                    } else
                    {
                        pojo.setCentreCode("NA");
                    }
                    if(object[2] != null)
                    {
                        pojo.setMobileNo(object[2].toString());
                    } else
                    {
                        pojo.setMobileNo("NA");
                    }
                    if(object[3] != null)
                    {
                        pojo.setEmailId(object[3].toString());
                    } else
                    {
                        pojo.setEmailId("NA");
                    }
                    if(object[4] != null)
                    {
                        pojo.setCrNo(object[4].toString());
                    } else
                    {
                        pojo.setCrNo("NA");
                    }
                }
            }

        }
        return pojo;
    }

    public FeedbackPojo getPatientDetailsByPatId(String patId,String patType, SessionFactory connectionSpace)
    {
        FeedbackPojo pojo = null;
        StringBuilder buffer = new StringBuilder("select patient_name,station,mobile_no,email,cr_number,patientId from patientinfo where cr_number ='"+patId+"'");
        if(patType!=null && patType.equalsIgnoreCase("IPD"))
        {
        	buffer.append(" and patient_type='IPD'");
        }
        
        buffer.append(" order by id desc limit 1");
    //    System.out.println("VBuffer is as "+buffer);
        List data = (new createTable()).executeAllSelectQuery(buffer.toString(), connectionSpace);
        if(data != null && data.size() > 0)
        {
            for(Iterator iterator = data.iterator(); iterator.hasNext();)
            {
                Object object[] = (Object[])iterator.next();
                if(object != null)
                {
                    pojo = new FeedbackPojo();
                    if(object[0] != null)
                    {
                        pojo.setName(object[0].toString());
                    } else
                    {
                        pojo.setName("NA");
                    }
                    if(object[1] != null)
                    {
                        pojo.setCentreCode(object[1].toString());
                    } else
                    {
                        pojo.setCentreCode("NA");
                    }
                    if(object[2] != null)
                    {
                        pojo.setMobileNo(object[2].toString());
                    } else
                    {
                        pojo.setMobileNo("NA");
                    }
                    if(object[3] != null)
                    {
                        pojo.setEmailId(object[3].toString());
                    } else
                    {
                        pojo.setEmailId("NA");
                    }
                    if(object[4] != null)
                    {
                        pojo.setCrNo(object[4].toString());
                    } else
                    {
                        pojo.setCrNo("NA");
                    }
                    if(object[5] != null)
                    {
                        pojo.setPatId(object[5].toString());
                    } else
                    {
                        pojo.setPatId("NA");
                    }
                    
                }
            }

        }
        return pojo;
    }

    public List getAllData(String table, String searchfield, String searchfieldvalue, String orderfield, String order)
    {
        List SubdeptallList = new ArrayList();
        try
        {
            Map session = ActionContext.getContext().getSession();
            SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
            String subdeptall = (new StringBuilder("select * from ")).append(table).append(" where ").append(searchfield).append("='").append(searchfieldvalue).append("' ORDER BY ").append(orderfield).append(" ").append(order).toString();
            SubdeptallList = (new createTable()).executeAllSelectQuery(subdeptall, connectionSpace);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return SubdeptallList;
    }

    public List getEmpWithoutRoasterForFeedback(String uName, String deptLevel)
    {
        List empList = new ArrayList();
        String empall = null;
        try
        {
            Map session = ActionContext.getContext().getSession();
            SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
            if(deptLevel.equals("2"))
            {
                empall = (new StringBuilder("select emp.empName,emp.mobOne,emp.emailIdOne,dept.id, dept.deptName from employe" +
"e_basic as emp inner join department as dept on dept.id=emp.deptname  inner join" +
" useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
)).append(uName).append("'").toString();
            } else
            {
                empall = (new StringBuilder("select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName f" +
"rom employee_basic as emp inner join department as dept on emp.dept=dept.id inne" +
"r join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
)).append(uName).append("'").toString();
            }
            empList = (new createTable()).executeAllSelectQuery(empall, connectionSpace);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return empList;
    }

    public String getTicketNo(String deptid, SessionFactory connectionSpace)
    {
        String ticketno = "NA";
        String ticket_type = "";
        String ticket_series = "";
        String prefix = "";
        String sufix = "";
        String ticket_no = "";
        List ticketSeries = new ArrayList();
        List deptTicketSeries = new ArrayList();
        try
        {
            ticketSeries = (new HelpdeskUniversalHelper()).getDataFromTable("ticket_series_conf", null, null, null, null, connectionSpace);
            if(ticketSeries != null && ticketSeries.size() == 1)
            {
                for(Iterator iterator = ticketSeries.iterator(); iterator.hasNext();)
                {
                    Object object[] = (Object[])iterator.next();
                    ticket_type = object[1].toString();
                    ticket_series = object[2].toString();
                }

                List table_data_count = (new HelpdeskUniversalHelper()).getDataFromTable("feedback_status_feedback", null, null, null, null, connectionSpace);
                if(table_data_count != null && table_data_count.size() > 0)
                {
                    ticket_no = getLatestTicket(ticket_type, deptid, connectionSpace);
                    if(ticket_no != null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("N"))
                    {
                        AN.set(Integer.parseInt(ticket_no));
                        ticketno = (new StringBuilder(String.valueOf(prefix))).append(AN.incrementAndGet()).toString();
                    } else
                    if(ticket_no != null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("D"))
                    {
                        prefix = ticket_no.substring(5, 7);
                        sufix = ticket_no.substring(7, ticket_no.length());
                        AN.set(Integer.parseInt(sufix));
                        ticketno = (new StringBuilder(String.valueOf(prefix))).append(AN.incrementAndGet()).toString();
                    } else
                    if((ticket_no == null || ticket_no.equals("")) && ticket_type.equalsIgnoreCase("D"))
                    {
                        deptTicketSeries = getAllData("dept_ticket_series_conf", "deptName", deptid, "prefix", "ASC");
                        if(deptTicketSeries != null && deptTicketSeries.size() == 1)
                        {
                            for(Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();)
                            {
                                Object object1[] = (Object[])iterator2.next();
                                if(object1[2] != null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA"))
                                {
                                    ticketno = (new StringBuilder(String.valueOf(object1[2].toString()))).append(object1[3].toString()).toString();
                                } else
                                {
                                    ticketno = "NA";
                                }
                            }

                        }
                    } else
                    if((ticket_no == null || ticket_no.equals("")) && ticket_type.equalsIgnoreCase("N"))
                    {
                        if(ticket_series != null && !ticket_series.equals("") && !ticket_series.equals("NA"))
                        {
                            ticketno = ticket_series;
                        } else
                        {
                            ticketno = "NA";
                        }
                    } else
                    {
                        ticketno = "NA";
                    }
                } else
                if(ticket_type.equalsIgnoreCase("N"))
                {
                    if(ticket_series != null && !ticket_series.equals("") && !ticket_series.equals("NA"))
                    {
                        ticketno = ticket_series;
                    } else
                    {
                        ticketno = "NA";
                    }
                } else
                if(ticket_type.equalsIgnoreCase("D"))
                {
                    deptTicketSeries = getAllData("dept_ticket_series_conf", "dept_id", deptid, "prefix", "ASC");
                    if(deptTicketSeries != null && deptTicketSeries.size() == 1)
                    {
                        for(Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();)
                        {
                            Object object1[] = (Object[])iterator2.next();
                            if(object1[2] != null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA"))
                            {
                                ticketno = (new StringBuilder(String.valueOf(object1[2].toString()))).append(object1[3].toString()).toString();
                            } else
                            {
                                ticketno = "NA";
                            }
                        }

                    } else
                    {
                        ticketno = "NA";
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ticketno;
    }

    public String getLatestTicket(String seriesType, String deptid, SessionFactory connection)
    {
        String ticketno = "";
        String query = "";
        List list = null;
        if(seriesType.equals("N"))
        {
            query = "select feed_status.ticket_no from feedback_status_feedback as feed_status where " +
"feed_status.id=(select max(feed_status.id) from feedback_status_feedback)"
;
        } else
        if(seriesType.equals("D"))
        {
            query = (new StringBuilder("select feed_status.ticket_no from feedback_status_feedback as feed_status where " +
"feed_status.id=(select max(feed_status.id) from feedback_status_feedback as feed" +
"_status where feed_status.to_dept_subdept in (select id from department where id" +
"="
)).append(deptid).append("))").toString();
        }
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = connection.getCurrentSession();
            transaction = session.beginTransaction();
            list = session.createSQLQuery(query).list();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
        }
        if(list != null && list.size() > 0)
        {
            ticketno = list.get(0).toString();
        }
        return ticketno;
    }

    public List getRandomEmp4EscalationFeedback(String dept_subDept, String deptLevel, SessionFactory connectionSpace, String level)
    {
        List list = new ArrayList();
        String qry = null;
        try
        {
            if(deptLevel.equals("2"))
            {
                qry = (new StringBuilder("select contcts.emp_id from compliance_contacts as contcts inner join employee_ba" +
"sic as emp on contcts.emp_id=emp.id where contcts.forDept_id='"
)).append(dept_subDept).append("' &&  contcts.level='").append(level).append("' && contcts.moduleName='FM'").toString();
            } else
            {
                deptLevel.equals("1");
            }
            list = (new createTable()).executeAllSelectQuery(qry, connectionSpace);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public List getRandomEmployee(String dept_subDept, String deptLevel, SessionFactory connectionSpace)
    {
        List list = new ArrayList();
        String qry = null;
        try
        {
            qry = (new StringBuilder("select distinct allot_to from feedback_status_feedback where to_dept_subdept='")).append(dept_subDept).append("' and deptHierarchy='").append(deptLevel).append("' and open_date='").append(DateUtil.getCurrentDateUSFormat()).append("'").toString();
            list = (new createTable()).executeAllSelectQuery(qry, connectionSpace);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public String getRandomEmployee(String tableId, List empId)
    {
        SessionFactory connectionSpace;
        Session session;
        List list;
        String allotmentId;
        Map session1 = ActionContext.getContext().getSession();
        connectionSpace = (SessionFactory)session1.get("connectionSpace");
        session = null;
        list = new ArrayList();
        allotmentId = "";
        String qry = null;
        try
        {
            session = HibernateSessionFactory.getSession();
            qry = (new StringBuilder("select allot_to from feedback_status_feedback where open_date='")).append(DateUtil.getCurrentDateUSFormat()).append("' group by allot_to having allot_to in ").append(empId.toString().replace("[", "(").replace("]", ")")).append(" order by count(allot_to) limit 1 ").toString();
            list = (new createTable()).executeAllSelectQuery(qry, connectionSpace);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	if(session.isOpen())
        	{
        		session.flush();
                session.close();
        	}
        }
        
        if(list != null && list.size() > 0)
        {
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                Object object = iterator.next();
                allotmentId = object.toString();
            }

        }
        return allotmentId;
    }
}