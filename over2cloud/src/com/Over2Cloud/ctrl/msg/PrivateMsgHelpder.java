package com.Over2Cloud.ctrl.msg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class PrivateMsgHelpder {

	public boolean createGroup(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		boolean status=false;
		List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 
		 ob1.setColumnname("msgtoempid");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("msgdata");
		 ob1.setDatatype("Text");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("readFlag");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default 0");
		 Tablecolumesaaa.add(ob1);
		 
		 status= cbt.createTable22("privatemsg",Tablecolumesaaa,connectionSpace);
		 return status;
	}
	
	public boolean insertGroup(CommonOperInterface cbt,SessionFactory connectionSpace,
			String msgtoempid,String msgdata,String userName)
	{
		 boolean status=false;
		 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		 InsertDataTable ob=new InsertDataTable();
		 ob.setColName("msgtoempid");
		 ob.setDataName(msgtoempid);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("msgdata");
		 ob.setDataName(msgdata);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("userName");
		 ob.setDataName(userName);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("date");
		 ob.setDataName(DateUtil.getCurrentDateUSFormat());
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("time");
		 ob.setDataName(DateUtil.getCurrentTime());
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("readFlag");
		 ob.setDataName("0");
		 insertData.add(ob);
		 
		status=cbt.insertIntoTable("privatemsg",insertData,connectionSpace);
		return status;
	}
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public List<PrivateMsgPojo> getUniquePrivateMessage(SessionFactory connectionSpace,String empID,String userName,String to,String searchedData)
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder();
		query.append("SELECT *FROM (select psm.msgtoempid,psm.userName,psm.date,psm.time,eb.empName,psm.msgdata from privatemsg " +
				"as psm left join employee_basic as eb on eb.id=psm.msgtoempid where psm.msgtoempid='"+empID+"' or psm.userName='"+userName+"'" +
				" order by psm.date desc,psm.time desc) as psm group by psm.msgtoempid");
		if(searchedData!=null && !searchedData.equalsIgnoreCase("")  && !searchedData.equalsIgnoreCase("null"))
		{
			//search code will append here later
		}
		query.append(" order by psm.date desc,psm.time desc  limit "+to+",5");
		List<PrivateMsgPojo>uniqueMsgData=new ArrayList<PrivateMsgPojo>();
		//System.out.println(query.toString());
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				Object[] obdata=(Object[])it.next();
				PrivateMsgPojo psb=new PrivateMsgPojo();
				psb.setId(Integer.parseInt(obdata[0].toString()));
				psb.setMsgBy(obdata[1].toString());
				psb.setMsgDate(DateUtil.convertDateToIndianFormat(obdata[2].toString()));
				psb.setMsgTime(obdata[3].toString());
				if(obdata[4]==null)
				{
					psb.setMsgTo("Admin");
				}
				else
				{
					psb.setMsgTo(obdata[4].toString());
				}
				if(obdata[5]==null)
				{
					psb.setMsgData("No Data");
				}
				else
				{
					psb.setMsgData(obdata[5].toString());
				}
				if(psb.getMsgBy().equalsIgnoreCase(userName))
				{
					psb.setUserTrue("true");
				}
				uniqueMsgData.add(psb);
			}
		}
		return uniqueMsgData;
	}
	public void deleteFullConv(SessionFactory connectionSpace,String empID,String userName)
	{
		Session session = null;  
		Transaction transaction = null;  
		try
		{
			session=connectionSpace.openSession();
			transaction = session.beginTransaction();  
			StringBuilder query=new StringBuilder("delete from privatemsg where msgtoempid='"+empID+"' and userName='"+userName+"'");
			session.createSQLQuery(query.toString()).executeUpdate();
			transaction.commit(); 
			session.close();
		}
		catch (SQLGrammarException ex) {
			ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
				session.close();
				} 
		
		 
	}
	
	public List<PrivateMsgPojo> getFullConversasion(SessionFactory connectionSpace,String empID,String userName)
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder();
		String tempEmpid=null;
		String tempuserName=null;
		try
		{
			query.append("select id from employee_basic where useraccountid=(select id from useraccount where " +
					"userID='"+Cryptography.encrypt(userName,DES_ENCRYPTION_KEY)+"')");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object obdata=(Object)it.next();
					if(obdata!=null)
					{
						tempEmpid=obdata.toString();
					}
					else
					{
						tempEmpid="100000";
					}
				}
			}
			query.delete(0, query.length());
			query.append("select ua.userID from useraccount as ua inner join employee_basic as eb on ua.id=eb.useraccountid where eb.id="+empID);
			data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object obdata=(Object)it.next();
					if(obdata!=null)
					{
						tempuserName=Cryptography.decrypt(obdata.toString(),DES_ENCRYPTION_KEY);
					}
				}
				if(tempuserName==null)
				{
					query.delete(0, query.length());
					query.append("select userID from useraccount order by id limit 0,1 ");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it1=data.iterator(); it1.hasNext();)
						{
							Object obdata1=(Object)it1.next();
							if(obdata1!=null)
							{
								tempuserName=Cryptography.decrypt(obdata1.toString(),DES_ENCRYPTION_KEY);
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			
		}
		query.delete(0, query.length());
		query.append("select pvm.id,pvm.msgdata,pvm.date,pvm.time,pvm.userName,pvm.msgtoempid,eb.empName from privatemsg as pvm left join employee_basic as eb on " +
				"eb.id=pvm.msgtoempid where pvm.msgtoempid='"+empID+"' " +
				"and pvm.userName='"+userName+"'");
		//System.out.println("tempuserName  "+tempuserName+"  tempEmpid  "+tempEmpid);
		if(tempuserName!=null && !tempuserName.equalsIgnoreCase("")
				&& tempEmpid!=null && !tempEmpid.equalsIgnoreCase(""))
		{
			query.append("or pvm.msgtoempid='"+tempEmpid+"' and pvm.userName='"+tempuserName+"'");
		}
		query.append(" order by date desc, time desc");	
			
		/*
		 * select pvm.id,pvm.msgdata,pvm.date,pvm.time,pvm.userName,pvm.msgtoempid,eb.empName from privatemsg as pvm 
			left join employee_basic as eb on eb.id=pvm.msgtoempid 
			where pvm.msgtoempid='100000' and pvm.userName='pankaj' 
			or pvm.msgtoempid='2' and pvm.userName='sandeepp' order by date desc, time desc
		 * 
		 */
		List<PrivateMsgPojo>uniqueMsgData=new ArrayList<PrivateMsgPojo>();
		//System.out.println(query.toString());
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				Object[] obdata=(Object[])it.next();
				PrivateMsgPojo psb=new PrivateMsgPojo();
				psb.setId((Integer)obdata[0]);
				psb.setMsgData(obdata[1].toString());
				psb.setMsgDate(DateUtil.convertDateToIndianFormat(obdata[2].toString()));
				psb.setMsgTime(obdata[3].toString());
				psb.setMsgBy(obdata[4].toString());
				if(obdata[4].toString().equalsIgnoreCase(userName))
				{
					psb.setUserTrue("true");
				}
				if(obdata[6]!=null)
					psb.setMsgTo(obdata[6].toString());
				else
					psb.setMsgTo("Admin");
				uniqueMsgData.add(psb);
			}
		}
		return uniqueMsgData;
	}
}
