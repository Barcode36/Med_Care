package com.Over2Cloud.ctrl.leaveManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;

public class LeaveHelper {
 public static final String DES_ENCRYPTION_KEY = "ankitsin";

 @SuppressWarnings("rawtypes")
 public List getEmpId(String username, String deptHierarchy,
   SessionFactory connectionSpace) {
  String query = null;
  List id = null;
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  try {
   if (deptHierarchy.equalsIgnoreCase("2")) {
    query = "select emp.empId from employee_basic as emp"
      + " inner join subdepartment as sdept on emp.subdept=sdept.id"
      + " inner join department as dept on sdept.deptid=dept.id"
      + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
      + username + "'";

   } else {
    query = "select emp.empId from employee_basic as emp"
      + " inner join department as dept on emp.dept=dept.id"
      + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
      + username + "'";

   }
   id = cbt.executeAllSelectQuery(query, connectionSpace);
  } catch (Exception e) {
   e.printStackTrace();
  }
  return id;
 }

 @SuppressWarnings("rawtypes")
 public List getEmpIdd(String username, String deptHierarchy,
   SessionFactory connectionSpace) {
  String query = null;
  List id = null;
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  try {
   if (deptHierarchy.equalsIgnoreCase("2")) {
    query = "select emp.id from employee_basic as emp"
      + " inner join subdepartment as sdept on emp.subdept=sdept.id"
      + " inner join department as dept on sdept.deptid=dept.id"
      + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
      + username + "'";

   } else {
    query = "select emp.id from employee_basic as emp"
      + " inner join department as dept on emp.dept=dept.id"
      + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
      + username + "'";

   }
   id = cbt.executeAllSelectQuery(query, connectionSpace);
  } catch (Exception e) {
   e.printStackTrace();
  }
  return id;
 }

 @SuppressWarnings("rawtypes")
 public List<AttendancePojo> getAllData(String empId,
   SessionFactory connectionSpace, String deptlevel) {
  String query = null;
  String query2 = null;
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
  AttendancePojo adb;
  try {
   adb = new AttendancePojo();
   /*
    * String fromDate=DateUtil.getCurrentDateUSFormat(); String[]
    * datearr=fromDate.split("-"); String
    * toDate=datearr[0]+"-"+datearr[1]+"-"+"01";
    */
   String fromDate = "2013-10-12";
   String toDate = "2013-10-01";
   // String ondate="2013-10-31";
   query = "SELECT count(*) FROM attendence_record WHERE date1 between '"
     + toDate
     + "' AND '"
     + fromDate
     + "' AND empname='"
     + empId
     + "' AND status='1' AND attendence='Present'";
   List totalPresent = cbt.executeAllSelectQuery(query,
     connectionSpace);
   if (totalPresent != null && totalPresent.size() > 0) {
    adb.setPresent(totalPresent.get(0).toString());
   } else {
    adb.setPresent("0");
   }
   query2 = "SELECT count(*) FROM attendence_record WHERE date1 between '"
     + toDate
     + "' AND '"
     + fromDate
     + "' AND empname='"
     + empId
     + "' AND status='0' AND attendence='Absent'";
   List totalAbsent = cbt.executeAllSelectQuery(query2,
     connectionSpace);
   if (totalAbsent != null && totalAbsent.size() > 0) {
    adb.setAbsent(totalAbsent.get(0).toString());
   } else {
    adb.setAbsent("0");
   }
   String query1 = "SELECT count(*) FROM attendence_record WHERE date1 between '"
     + toDate
     + "' AND '"
     + fromDate
     + "' AND empname='"
     + empId
     + "' AND eworking!='0'";
   List totaleworking = cbt.executeAllSelectQuery(query1,
     connectionSpace);
   if (totaleworking != null && totaleworking.size() > 0) {
    adb.setExtraworking(totaleworking.get(0).toString());
   } else {
    adb.setExtraworking("0");
   }
   String date = DateUtil.getLastDateFromLastDate(-1, fromDate);
   if (date != null) {
    String datearr[] = date.split("-");
    int totaldays = DateUtil.countDaysInMonth(
      Integer.parseInt(datearr[1]),
      Integer.parseInt(datearr[0]));
    String finalDate = datearr[0] + "-" + datearr[1] + "-"
      + totaldays;
    String query3 = "SELECT cf,lnextmonth FROM report_data WHERE tdate = '"
      + finalDate + "' AND empname='" + empId + "'";
    List data = cbt.executeAllSelectQuery(query3, connectionSpace);
    if (data != null && data.size() > 0) {
     Object[] object = null;
     for (Iterator iterator = data.iterator(); iterator
       .hasNext();) {
      object = (Object[]) iterator.next();
      adb.setLeavePrevious(object[0].toString());
      adb.setLeaveNext(object[1].toString());
     }
    } else {
     adb.setLeavePrevious("0");
     adb.setLeaveNext("0");
    }
   }
   // String
   // query4="SELECT count(*) FROM attendence_record WHERE date1 between '"+toDate+"' AND '"+fromDate+"' AND empname='"+empId+"' AND status='1' AND attendence='Present' AND clientVisit!='None'";
   // List totalCV=cbt.executeAllSelectQuery(query4, connectionSpace);
   /*
    * if (totalCV!=null && totalCV.size()>0) {
    * adb.setTotalCV(totalCV.get(0).toString()); } else {
    * adb.setTotalCV("0"); }
    */
   list.add(adb);
  } catch (Exception e) {
   e.printStackTrace();
  }
  return list;
 }

 @SuppressWarnings("rawtypes")
 public List<AttendancePojo> getAllholiday(SessionFactory connectionSpace) {
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
  AttendancePojo adb;
  try {

   String fromDate = DateUtil.getCurrentDateUSFormat();
   String[] datearr = fromDate.split("-");
   String toDate = datearr[0] + "-" + datearr[1] + "-" + "01";

   // String toDate="2013-11-01";
   // String ondate="2013-11-31";
   int totaldays = DateUtil.countDaysInMonth(
     Integer.parseInt(datearr[1]), Integer.parseInt(datearr[0]));
   String ondate = datearr[0] + "-" + datearr[1] + "-" + totaldays;
   String query5 = "SELECT fdate,tdate,holidayname,hbrief FROM holiday_list WHERE fdate between '"
     + toDate + "' AND '" + ondate + "'";
   List tHoliday = cbt.executeAllSelectQuery(query5, connectionSpace);
   if (tHoliday != null && tHoliday.size() > 0) {
    Object[] object = null;
    for (Iterator iterator = tHoliday.iterator(); iterator
      .hasNext();) {
     object = (Object[]) iterator.next();
     if (object != null) {
      adb = new AttendancePojo();
      adb.setFdate(DateUtil
        .convertDateToIndianFormat(object[0].toString()));
      adb.setTdate(DateUtil
        .convertDateToIndianFormat(object[1].toString()));
      adb.setHolidayname(object[2].toString());
      adb.setHbrief(object[3].toString());
      list.add(adb);
     }
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return list;

 }

 @SuppressWarnings("rawtypes")
 public List<AttendancePojo> getAllDashDetails(String empId,
   SessionFactory connectionSpace) {
  String query = null;
  String query2 = null;
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
  AttendancePojo adb;
  try {

   query = "SELECT tdate FROM report_data WHERE empname='" + empId
     + "'";
   List month = cbt.executeAllSelectQuery(query, connectionSpace);
   if (month != null && month.size() > 0) {
    Object object = null;
    for (Iterator iterator = month.iterator(); iterator.hasNext();) {
     adb = new AttendancePojo();
     object = (Object) iterator.next();
     if (object != null) {
      adb.setMonth(DateUtil.convertDateToIndianFormat(object
        .toString()));
      String[] monthSplit = object.toString().split("-");
      String toDate = monthSplit[0] + "-" + monthSplit[1]
        + "-" + "01";
      query2 = "SELECT count(*) FROM attendence_record WHERE date1 between '"
        + toDate
        + "' AND '"
        + object.toString()
        + "' AND empname='"
        + empId
        + "' AND status='1' AND attendence='Present'";
      List present = cbt.executeAllSelectQuery(query2,
        connectionSpace);
      if (present != null && present.size() > 0) {
       adb.setPresent(present.get(0).toString());
      }
      List absent = cbt
        .executeAllSelectQuery(
          "SELECT count(*) FROM attendence_record WHERE date1 between '"
            + toDate
            + "' AND '"
            + object.toString()
            + "' AND empname='"
            + empId
            + "' AND status='0' AND attendence='Absent'",
          connectionSpace);
      if (absent != null && absent.size() > 0) {
       adb.setAbsent(absent.get(0).toString());
      }
      List eworking = cbt.executeAllSelectQuery(
        "SELECT extraworking,cf,lnextmonth FROM report_data WHERE empname='"
          + empId + "' AND tdate='"
          + object.toString() + "'",
        connectionSpace);
      if (eworking != null && eworking.size() > 0) {
       Object[] object2 = null;
       for (Iterator iterator2 = eworking.iterator(); iterator2
         .hasNext();) {
        object2 = (Object[]) iterator2.next();
        if (object2 != null) {
         adb.setExtraworking(object2[0].toString());
         adb.setLeavePrevious(object2[1].toString());
         adb.setLeaveNext(object2[2].toString());
        }
       }
      }
      String query3 = "SELECT count(*) FROM attendence_record WHERE date1 between '"
        + toDate
        + "' AND '"
        + object.toString()
        + "' AND empname='"
        + empId
        + "' AND status='1' AND attendence='Present' AND clientVisit='Full Day'";
      List fullCV = cbt.executeAllSelectQuery(query3,
        connectionSpace);
      if (fullCV != null && fullCV.size() > 0) {
       adb.setFullCV(fullCV.get(0).toString());
      }
      String query4 = "SELECT count(*) FROM attendence_record WHERE date1 between '"
        + toDate
        + "' AND '"
        + object.toString()
        + "' AND empname='"
        + empId
        + "' AND status='1' AND attendence='Present' AND clientVisit='Half Day'";
      List halfCV = cbt.executeAllSelectQuery(query4,
        connectionSpace);
      if (halfCV != null && halfCV.size() > 0) {
       adb.setHalfCV(halfCV.get(0).toString());
      }
      list.add(adb);
     }
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }

  return list;
 }

 @SuppressWarnings("rawtypes")
 public List<AttendancePojo> getAllUpdateData(String empId,
   SessionFactory connectionSpace) {
  String query10 = null;
  String query11 = null;
  String query16 = null;
  String query13 = null;
  String query14 = null;
  String query15 = null;
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
  AttendancePojo adb, obj, obj1;
  try {
   String query = "SELECT tdate FROM report_data WHERE empname='"
     + empId + "'";
   List month = cbt.executeAllSelectQuery(query, connectionSpace);
   if (month != null && month.size() > 0) {
    Object object = null;
    for (Iterator iterator = month.iterator(); iterator.hasNext();) {
     adb = new AttendancePojo();
     object = (Object) iterator.next();
     if (object != null) {
      adb.setMonth(DateUtil.convertDateToIndianFormat(object
        .toString()));
      String[] monthSplit = object.toString().split("-");
      String toDate = monthSplit[0] + "-" + monthSplit[1]
        + "-" + "01";
      String query33 = "SELECT count(lupdate),lupdate FROM attendence_record WHERE date1 between '"
        + toDate
        + "' AND '"
        + object.toString()
        + "' AND empname='"
        + empId
        + "' AND status='0' AND attendence='Absent' AND lupdate IN('Call','SMS','VMN','No Intimation','Mail') GROUP BY lupdate";
      List lupdateList = cbt.executeAllSelectQuery(query33,
        connectionSpace);
      if (lupdateList != null && lupdateList.size() > 0) {
       obj = new AttendancePojo();
       Object[] object2 = null;
       for (Iterator iterator2 = lupdateList.iterator(); iterator2
         .hasNext();) {
        object2 = (Object[]) iterator2.next();
        if (object2 != null) {
         if (object2[1].toString().equalsIgnoreCase(
           "Call")) {
          obj.setCall(object2[0].toString());
         }
         if (object2[1].toString().equalsIgnoreCase(
           "SMS")) {
          obj.setSms(object2[0].toString());
         }
         if (object2[1].toString().equalsIgnoreCase(
           "Mail")) {
          obj.setMail(object2[0].toString());
         }
         if (object2[1].toString().equalsIgnoreCase(
           "VMN")) {
          obj.setVmn(object2[0].toString());
         }
         if (object2[1].toString().equalsIgnoreCase(
           "No Intimation")) {
          obj.setNointimation(object2[0]
            .toString());
         }
        }
       }
       adb.setCall(obj.getCall());
       adb.setSms(obj.getSms());
       adb.setMail(obj.getMail());
       adb.setVmn(obj.getVmn());
       adb.setNointimation(obj.getNointimation());
      }
      String query22 = "SELECT ftime FROM time_slot WHERE empname='"
        + empId + "'";
      List entryTime = cbt.executeAllSelectQuery(query22,
        connectionSpace);
      if (entryTime != null && entryTime.size() > 0) {
       obj1 = new AttendancePojo();
       if (entryTime.get(0).toString()
         .equalsIgnoreCase("09:15")) {
        query10 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '08:30' AND '"
          + entryTime.get(0).toString() + "'";
        query11 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '"
          + entryTime.get(0).toString()
          + "' AND '09:30'";
        query13 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '09:30' AND '09:45'";
        query14 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '09:45' AND '10:15'";
        query15 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '10:15' AND '11:15'";
        query16 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '11:15' AND '18:30'";

        if (cbt.executeAllSelectQuery(query10,
          connectionSpace) != null) {
         obj1.setBefore(cbt
           .executeAllSelectQuery(query10,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query11,
          connectionSpace) != null) {
         obj1.setMin15(cbt
           .executeAllSelectQuery(query11,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query13,
          connectionSpace) != null) {
         obj1.setMin30(cbt
           .executeAllSelectQuery(query13,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query14,
          connectionSpace) != null) {
         obj1.setMon45(cbt
           .executeAllSelectQuery(query14,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query15,
          connectionSpace) != null) {
         obj1.setHour1(cbt
           .executeAllSelectQuery(query15,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query16,
          connectionSpace) != null) {
         obj1.setHour2(cbt
           .executeAllSelectQuery(query16,
             connectionSpace).get(0)
           .toString());
        }

       }
       if (entryTime.get(0).toString()
         .equalsIgnoreCase("09:30")) {
        query10 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '08:45' AND '"
          + entryTime.get(0).toString() + "'";
        query11 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '"
          + entryTime.get(0).toString()
          + "' AND '09:45'";
        query13 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '09:45' AND '10:00'";
        query14 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '10:00' AND '10:30'";
        query15 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '10:30' AND '11:30'";
        query16 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '11:30' AND '18:30'";

        if (cbt.executeAllSelectQuery(query10,
          connectionSpace) != null) {
         obj1.setBefore(cbt
           .executeAllSelectQuery(query10,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query11,
          connectionSpace) != null) {
         obj1.setMin15(cbt
           .executeAllSelectQuery(query11,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query13,
          connectionSpace) != null) {
         obj1.setMin30(cbt
           .executeAllSelectQuery(query13,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query14,
          connectionSpace) != null) {
         obj1.setMon45(cbt
           .executeAllSelectQuery(query14,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query15,
          connectionSpace) != null) {
         obj1.setHour1(cbt
           .executeAllSelectQuery(query15,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query16,
          connectionSpace) != null) {
         obj1.setHour2(cbt
           .executeAllSelectQuery(query16,
             connectionSpace).get(0)
           .toString());
        }
       }
       if (entryTime.get(0).toString()
         .equalsIgnoreCase("10:00")) {
        query10 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '09:00' AND '"
          + entryTime.get(0).toString() + "'";
        query11 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '"
          + entryTime.get(0).toString()
          + "' AND '10:15'";
        query13 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '10:15' AND '10:30'";
        query14 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '10:30' AND '11:00'";
        query15 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '11:00' AND '11:45'";
        query16 = "select count(*) from attendence_record where empname='"
          + empId
          + "' AND date1 between '"
          + toDate
          + "' AND '"
          + object.toString()
          + "' AND in_time between '11:45' AND '18:30'";

        if (cbt.executeAllSelectQuery(query10,
          connectionSpace) != null) {
         obj1.setBefore(cbt
           .executeAllSelectQuery(query10,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query11,
          connectionSpace) != null) {
         obj1.setMin15(cbt
           .executeAllSelectQuery(query11,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query13,
          connectionSpace) != null) {
         obj1.setMin30(cbt
           .executeAllSelectQuery(query13,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query14,
          connectionSpace) != null) {
         obj1.setMon45(cbt
           .executeAllSelectQuery(query14,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query15,
          connectionSpace) != null) {
         obj1.setHour1(cbt
           .executeAllSelectQuery(query15,
             connectionSpace).get(0)
           .toString());
        }
        if (cbt.executeAllSelectQuery(query16,
          connectionSpace) != null) {
         obj1.setHour2(cbt
           .executeAllSelectQuery(query16,
             connectionSpace).get(0)
           .toString());
        }
       }
       adb.setBefore(obj1.getBefore());
       adb.setMin15(obj1.getMin15());
       adb.setMin30(obj1.getMin30());
       adb.setMon45(obj1.getMon45());
       adb.setHour1(obj1.getHour1());
       adb.setHour2(obj1.getHour2());
      }
      list.add(adb);
     }
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return list;
 }

 @SuppressWarnings("rawtypes")
 public List<AttendancePojo> getAllEventDetails(String empId,
   SessionFactory connectionSpace) {
  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
  try {
   AttendancePojo adb;
   String query = null;
   CommonOperInterface cbt = new CommonConFactory().createInterface();
   String toDate = DateUtil.getNextDateAfter(15);
   query = "SELECT fdate,holidayname FROM holiday_list WHERE fdate BETWEEN '"
     + DateUtil.getCurrentDateUSFormat()
     + "' AND '"
     + toDate
     + "'";
   List holidayData = cbt
     .executeAllSelectQuery(query, connectionSpace);
   if (holidayData != null && holidayData.size() > 0) {
    Object[] object = null;
    for (Iterator iterator = holidayData.iterator(); iterator
      .hasNext();) {
     object = (Object[]) iterator.next();
     if (object != null) {
      adb = new AttendancePojo();
      adb.setFdate(DateUtil
        .convertDateToIndianFormat(object[0].toString()));
      adb.setHolidayname(object[1].toString());
      adb.setHbrief("Holiday");
      list.add(adb);
     }
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }

  return list;
 }

 @SuppressWarnings("rawtypes")
 public List<AttendancePojo> getAllBirthDay(String empid,
   SessionFactory connectionSpace) {
  List<AttendancePojo> list = new ArrayList<AttendancePojo>();
  try {
   AttendancePojo adb;
   CommonOperInterface cbt = new CommonConFactory().createInterface();
   String from[] = DateUtil.getCurrentDateUSFormat().split("-");
   int a = 0;
   String b = null;
   if (Integer.parseInt(from[1].toString()) < 12) {
    a = Integer.parseInt(from[1].toString()) + 1;
    if (a < 10) {
     b = "0" + String.valueOf(a);
    } else {
     b = String.valueOf(a);
    }
   } else {
    a = 1;
    if (a < 10) {
     b = "0" + String.valueOf(a);
    } else {
     b = String.valueOf(a);
    }
   }
   String query1 = "Select ep.edob_date,eb.empName from employee_personal as ep "
     + " INNER JOIN employee_basic as eb WHERE ep.empName=eb.mobOne AND edob_date IN(SELECT edob_date from employee_personal "
     + "WHERE edob_date like '%-"
     + from[1].toString()
     + "-%' or ep.edob_date like '%-" + b + "-%')";
   List birthdayData = cbt.executeAllSelectQuery(query1,
     connectionSpace);
   if (birthdayData != null && birthdayData.size() > 0) {
    Object[] object = null;
    for (Iterator iterator = birthdayData.iterator(); iterator
      .hasNext();) {
     object = (Object[]) iterator.next();
     if (object != null) {
      adb = new AttendancePojo();
      adb.setEname(object[1].toString());
      adb.setData1(DateUtil
        .convertDateToIndianFormat(object[0].toString()));
      adb.setComment("Birthday");
      list.add(adb);
     }
    }
   }
   String query2 = "Select ep.doa_date,eb.empName from employee_personal as ep "
     + " INNER JOIN employee_basic as eb WHERE ep.empName=eb.mobOne AND doa_date IN(SELECT doa_date from employee_personal "
     + "WHERE doa_date like '%-"
     + from[1].toString()
     + "-%' or ep.doa_date like '%-" + b + "-%')";
   List anniversaryData = cbt.executeAllSelectQuery(query2,
     connectionSpace);
   if (anniversaryData != null && anniversaryData.size() > 0) {
    Object[] object = null;
    for (Iterator iterator = anniversaryData.iterator(); iterator
      .hasNext();) {
     object = (Object[]) iterator.next();
     if (object != null) {
      adb = new AttendancePojo();
      adb.setEmpname(object[1].toString());
      adb.setDate(DateUtil
        .convertDateToIndianFormat(object[0].toString()));
      adb.setDay("Anniversary");
      list.add(adb);
     }
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return list;
 }

 @SuppressWarnings("unchecked")
 public String[] getEmpDetailsByUserName(String moduleName, String userName,
   SessionFactory connectionSpace) {
  String[] values = null;

  try {
   CommonOperInterface coi = new CommonConFactory().createInterface();
   userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

   StringBuilder query = new StringBuilder();
   query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
   query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
   query.append(" inner join department as dept on emp.deptname=dept.id");
   query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"
     + moduleName + "' and uac.userID='");
   query.append(userName + "' and contact.forDept_id=dept.id");
   query.append( " and emp.flag=0 and contact.work_status=0 "  );
   query.append(" order by emp.empName asc ");
   System.out.println("Common Helper Class " + query.toString());
   List dataList = coi.executeAllSelectQuery(query.toString(),
     connectionSpace);
   if (dataList != null && dataList.size() > 0) {
    values = new String[7];
    Object[] object = (Object[]) dataList.get(0);
    values[0] = getValueWithNullCheck(object[0]);
    values[1] = getValueWithNullCheck(object[1]);
    values[2] = getValueWithNullCheck(object[2]);
    values[3] = getValueWithNullCheck(object[3]);
    values[4] = getValueWithNullCheck(object[4]);
    values[5] = getValueWithNullCheck(object[5]);
    values[6] = getValueWithNullCheck(object[6]);
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return values;
 }

 @SuppressWarnings("unchecked")
 public String[] getEmpDetailsByMobileNo(String moduleName, String mobNo,
   SessionFactory connectionSpace) {
  String[] values = null;

  try {
   CommonOperInterface coi = new CommonConFactory().createInterface();

   StringBuilder query = new StringBuilder();
   query.append("select contact.id,emp.empname,emp.emailidone,emp.empId, dept.deptName,emp.id as empid,emp.mobOne,uac.userID from employee_basic as emp ");
   query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
   query.append(" inner join department as dept on emp.deptname=dept.id");
   query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"
     + moduleName + "' and emp.mobone='");
   query.append(mobNo + "' and contact.forDept_id=dept.id");
   System.out.println("Common Helper Class " + query.toString());
   List dataList = coi.executeAllSelectQuery(query.toString(),
     connectionSpace);
   if (dataList != null && dataList.size() > 0) {
    values = new String[8];
    Object[] object = (Object[]) dataList.get(0);
    values[0] = getValueWithNullCheck(object[0]);
    values[1] = getValueWithNullCheck(object[1]);
    values[2] = getValueWithNullCheck(object[2]);
    values[3] = getValueWithNullCheck(object[3]);
    values[4] = getValueWithNullCheck(object[4]);
    values[5] = getValueWithNullCheck(object[5]);
    values[6] = getValueWithNullCheck(object[6]);
    values[7] = getValueWithNullCheck(object[7]);

   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return values;
 }

 
 
 
/* public String[] getTest( String mobNo,SessionFactory connectionSpace) {
		  String[] values = null;

		  try {
		   CommonOperInterface coi = new CommonConFactory().createInterface();

		   StringBuilder query = new StringBuilder();
		   query.append("select contact.id,emp.empname,emp.emailidone,emp.empId, dept.deptName,emp.id as empid,emp.mobOne,uac.userID from employee_basic as emp ");
		   query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
		   query.append(" inner join department as dept on emp.deptname=dept.id");
		   query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"
		     + moduleName + "' and emp.mobone='");
		   query.append(mobNo + "' and contact.forDept_id=dept.id");
		   System.out.println("Common Helper Class " + query.toString());
		   List dataList = coi.executeAllSelectQuery(query.toString(),
		     connectionSpace);
		   if (dataList != null && dataList.size() > 0) {
		    values = new String[8];
		    Object[] object = (Object[]) dataList.get(0);
		    values[0] = getValueWithNullCheck(object[0]);
		    values[1] = getValueWithNullCheck(object[1]);
		    values[2] = getValueWithNullCheck(object[2]);
		    values[3] = getValueWithNullCheck(object[3]);
		    values[4] = getValueWithNullCheck(object[4]);
		    values[5] = getValueWithNullCheck(object[5]);
		    values[6] = getValueWithNullCheck(object[6]);
		    values[7] = getValueWithNullCheck(object[7]);

		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return values;
		 }*/
 
 public String getValueWithNullCheck(Object value) {
  return (value == null || value.toString().equals("")) ? "NA" : value
    .toString();
 }

 /*
  * public List<EmpBasicPojoBean> empProfessionalFullView(SessionFactory
  * connectionSpace, String moduleName, String mobNo, String accountID) {
  * EmpBasicPojoBean empbasic=null;
  * 
  * CommonOperInterface coi = new CommonConFactory().createInterface();
  * List<EmpBasicPojoBean> dataList=new ArrayList<EmpBasicPojoBean>();
  * StringBuilder query = new StringBuilder(); query.append(
  * "select contact.id,emp.empname,emp.emailidone,emp.empId, dept.deptName,emp.id as empid from employee_basic as emp "
  * ); query.append(
  * " inner join compliance_contacts as contact on contact.emp_id=emp.id");
  * query.append(" inner join department as dept on emp.deptname=dept.id");
  * query.append(
  * " inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"
  * +moduleName+"' and emp.mobone='"); query.append(mobNo +
  * "' and contact.forDept_id=dept.id"); List list =
  * coi.executeAllSelectQuery(query.toString(), connectionSpace);
  * query.setLength(0); if (list != null && list.size() > 0) { Object[]
  * object=null; for (Iterator iterator = list.iterator();
  * iterator.hasNext();) {
  * 
  * object = (Object[]) iterator.next(); if (object!=null) { empbasic =new
  * EmpBasicPojoBean(); empbasic.setEmpName(getFieldValue(object[1]));
  * empbasic.setEmailIdOne(getFieldValue(object[2]));
  * empbasic.setEmpId(getFieldValue(object[3]));
  * empbasic.setDeptName(getFieldValue(object[4]));
  * 
  * } dataList.add(empbasic); }
  * 
  * System.out.println(" dataList SIZE " +dataList.size()); }
  * 
  * return dataList; }
  * 
  * public static String getFieldValue(Object object) { return (object ==
  * null || object.toString().trim().length() < 1) ? "NA" :
  * object.toString().trim(); }
  */

 public String getContactIdForEmp(String moduleName, String userName,
   SessionFactory connectionSpace) {
  StringBuilder contactId = new StringBuilder();
  try {
   CommonOperInterface coi = new CommonConFactory().createInterface();
   userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

   StringBuilder query = new StringBuilder();
   query.append("select contact.id from employee_basic as emp ");
   query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
   query.append(" inner join department as dept on emp.deptname=dept.id");
   query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"
     + moduleName + "' and uac.userID='");
   query.append(userName + "' ");
   List dataList = coi.executeAllSelectQuery(query.toString(),
     connectionSpace);
   if (dataList != null && dataList.size() > 0) {
    for (Iterator iterator = dataList.iterator(); iterator
      .hasNext();) {
     Object object = (Object) iterator.next();
     contactId.append(getValueWithNullCheck(object) + ",");
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return contactId.toString();
 }

 public String getContactId(String moduleNam,SessionFactory connectionSpace2, String empId) {
  String contactId = null;
  try {
   String query = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.emp_id='"
     + empId + "' AND moduleName='" + moduleNam + "' ";
   List data = new createTable().executeAllSelectQuery(query,
     connectionSpace2);
   if (data != null && data.size() > 0) {
    contactId = data.toString();
    contactId = contactId.replace("[", "").replace("]", "");
   }

  } catch (Exception e) {
   e.printStackTrace();
  }
  return contactId;
 }

 public String getContactIdforEmp(String moduleNam,SessionFactory connectionSpace2, String mobno) {
  String contactId = null;
  try {
   String query = "SELECT cc.id FROM compliance_contacts AS cc " +
     "INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.emp_id=emp.id " +
     "AND moduleName='" + moduleNam + "' and emp.mobone='" +mobno + "' ";
   List data = new createTable().executeAllSelectQuery(query,
     connectionSpace2);
   if (data != null && data.size() > 0) {
    contactId = data.toString();
    contactId = contactId.replace("[", "").replace("]", "");
   }

  } catch (Exception e) {
   e.printStackTrace();
  }
  return contactId;
 }

 
 
 
 public String[] getEmployeeDetails(String uniqueId, String moduleName,
   SessionFactory connection) {
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  StringBuilder qryString = new StringBuilder();
  String[] data = new String[3];
  List dataList = null;
  try {
   qryString
     .append("select empName,emailIdOne,cc.level from employee_basic AS emp ");
   qryString
     .append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id ");
   qryString.append(" WHERE cc.id='" + uniqueId
     + "' AND cc.moduleName='" + moduleName + "'");
   System.out.println("qryString" + qryString);
   dataList = cbt.executeAllSelectQuery(qryString.toString(),
     connection);
   if (dataList != null && dataList.size() > 0) {
    for (Iterator iterator = dataList.iterator(); iterator
      .hasNext();) {
     Object[] object = (Object[]) iterator.next();
     if (object[1] != null && object[0] != null) {
      data[0] = getValueWithNullCheck(object[0]);
      data[1] = getValueWithNullCheck(object[1]);
      data[2] = getValueWithNullCheck(object[2]);
     }
    }
   }
  } catch (Exception exp) {
   exp.printStackTrace();
  }
  return data;

 }

 
 public String[] getEmployeeMailID(String moduleName,
   SessionFactory connection) {
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  StringBuilder qryString = new StringBuilder();
  String[] data = new String[3];
  List dataList = null;
  try {
   qryString
     .append("select empName,emailIdOne,cc.level from employee_basic AS emp ");
   qryString
     .append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id ");
   qryString.append("cc.moduleName='" + moduleName + "'");
   System.out.println("qryString" + qryString);
   dataList = cbt.executeAllSelectQuery(qryString.toString(),
     connection);
   if (dataList != null && dataList.size() > 0) {
    for (Iterator iterator = dataList.iterator(); iterator
      .hasNext();) {
     Object[] object = (Object[]) iterator.next();
     if (object[1] != null && object[0] != null) {
      data[0] = getValueWithNullCheck(object[0]);
      data[1] = getValueWithNullCheck(object[1]);
      data[2] = getValueWithNullCheck(object[2]);
     }
    }
   }
  } catch (Exception exp) {
   exp.printStackTrace();
  }
  return data;

 }
 
 
 public List getDeptName(String moduleName, String deptID,
   SessionFactory connectionSpace) {
  List data = new ArrayList();
  StringBuilder query = new StringBuilder();
  try {

   query.append(" SELECT  dt.id,dt.deptName FROM department as dt");
   query.append(" INNER JOIN compliance_contacts as cc ON dt.id=cc.fromDept_id");
   query.append(" WHERE cc.moduleName='" + moduleName
     + "' AND cc.forDept_id='" + deptID
     + "' GROUP BY dt.deptName  order by dt.deptName asc ");
System.out.println(">>>>>>>>>>>>>>>>>>>>>getDeptName>>>>>>>>>>>>>>>>>>>>       "  +query);
   data = new createTable().executeAllSelectQuery(query.toString(),
     connectionSpace);

  } catch (Exception e) {
   e.printStackTrace();
  }
  return data;
 }

 @SuppressWarnings("rawtypes")
 public List getOnChangeEmployeeData(String moduleName, String forDeptID,
   String fromDeptID, SessionFactory connectionSpace) {
  List data = new ArrayList();
  StringBuilder query = new StringBuilder();
  try {

   query.append(" SELECT cc.id,emp.empName FROM compliance_contacts AS cc ");
   query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
   query.append(" INNER JOIN department AS dept ON dept.id=cc.forDept_id ");
   query.append(" WHERE  cc.forDept_id= '" + forDeptID
     + "'  AND cc.moduleName='" + moduleName + "'  ");
   if (fromDeptID != null && !fromDeptID.equalsIgnoreCase("")) {
    query.append(" AND cc.fromDept_id='" + fromDeptID + "' ");
   }
   query.append(" and emp.flag=0 and cc.work_status=0 ");
   query.append(" GROUP BY emp.empName order by emp.empName asc ");
   System.out.println("QUERY IS AS  ::::  " + query.toString());

   data = new createTable().executeAllSelectQuery(query.toString(),
     connectionSpace);

  } catch (Exception e) {
   e.printStackTrace();
  }
  return data;

 }

 @SuppressWarnings("rawtypes")
 public String[] getEmpId1(String empID, SessionFactory connectionSpace) {
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  StringBuilder qryString = new StringBuilder();
  String[] data = new String[2];
  List dataList = null;
  try {
   qryString
     .append(" SELECT emp.id,emp.deptname FROM  employee_basic AS emp");
   qryString.append(" WHERE  emp.empId='" + empID + "' ");
   System.out.println("qryString>>>>>getEmpId1>>>>>>>" + qryString);

   dataList = cbt.executeAllSelectQuery(qryString.toString(),
     connectionSpace);
   if (dataList != null && dataList.size() > 0) {
    for (Iterator iterator = dataList.iterator(); iterator
      .hasNext();) {
     Object[] object = (Object[]) iterator.next();
     if (object[1] != null && object[0] != null) {
      data[0] = getValueWithNullCheck(object[0]);
      data[1] = getValueWithNullCheck(object[1]);

     }
    }
   }
  } catch (Exception exp) {
   exp.printStackTrace();
  }
  return data;

 }

 @SuppressWarnings("rawtypes")
 public String getContactIdForExcel(String moduleNam, String loggedID,
   String empId, String empDeptId, SessionFactory connectionSpace2) {
  System.out.println("moduleNam :::>>>>" + moduleNam);
  System.out.println("empId :::>>>>" + empId);
  System.out.println("empDeptId :::>>>>" + empDeptId);
  CommonOperInterface cbt = new CommonConFactory().createInterface();
  StringBuilder contactId = new StringBuilder();

  List dataList = null;
  try {
   StringBuilder qryString = new StringBuilder();
   qryString.append(" SELECT id FROM compliance_contacts ");
   qryString.append(" WHERE forDept_id='" + loggedID
     + "' AND fromDept_id='" + empDeptId + "' AND emp_id='"
     + empId + "' AND moduleName='" + moduleNam + "' ");
   System.out.println("qryString ::::" + qryString);
   dataList = cbt.executeAllSelectQuery(qryString.toString(),
     connectionSpace2);
   if (dataList != null && dataList.size() > 0) {
    for (Iterator iterator = dataList.iterator(); iterator
      .hasNext();) {
     Object object = (Object) iterator.next();
     contactId.append(getValueWithNullCheck(object));
     System.out.println("contactId :::::" + contactId);
    }
   }
  } catch (Exception exp) {
   exp.printStackTrace();
  }
  return contactId.toString();

 }

 public String getIncommingStatusForBiometrics(String in_time, String intime) {
  System.out.println("in_time ::: " + in_time);
  System.out.println("intime ::: " + intime);
  String spin_time[] = in_time.split(":");
  String spintime[] = intime.split(":");
  int time = Integer.parseInt(spintime[0])
    - Integer.parseInt(spin_time[0]);
  int time1 = Integer.parseInt(spintime[1])
    - Integer.parseInt(spin_time[1]);
  String totalTime = time + ":" + time1;
  return totalTime;
 }

 public String getFinalIncommingstatusForBiometrics(String incomingStatus) {
  System.out.println(":::::::FinalIncommingstatus::::::::::");
  String finalTime = null;
  String timeSplit[] = incomingStatus.split(":");
  if (incomingStatus != null) {
   if (timeSplit[0].startsWith("-") && timeSplit[1].startsWith("-")) {
    finalTime = "Late By "
      + timeSplit[0].substring(1, timeSplit[0].length())
      + " hour "
      + timeSplit[1].substring(1, timeSplit[1].length())
      + " mins";
   } else if (timeSplit[0].startsWith("-")) {
    int time3 = (Integer.parseInt(timeSplit[0].substring(1,
      timeSplit[0].length())) * 60)
      - Integer.parseInt(timeSplit[1]);
    finalTime = "Late By " + time3 + " mins";
   } else if (timeSplit[1].startsWith("-")) {
    finalTime = "Late By " + timeSplit[0] + " hour "
      + timeSplit[1].substring(1, timeSplit[1].length())
      + " mins";
   } else
    finalTime = "Early By " + timeSplit[0] + " hour "
      + timeSplit[1] + " mins";
  }
  return finalTime;
 }

 public List getTimeEntryForAttendance(String entryTime, String exitTime,
   SessionFactory connectionSpace) {
  List data = new ArrayList();
  StringBuilder query = new StringBuilder();
  try {

   query.append("Select in_time ,out_time from attendence_record ");
   query.append(" where '" + entryTime + "'>'09:30' or '" + exitTime
     + "' < '18:30' ");
   System.out.println("execute Query :: " + query);

   data = new createTable().executeAllSelectQuery(query.toString(),
     connectionSpace);

  } catch (Exception e) {
   e.printStackTrace();
  }
  return data;
 }

}