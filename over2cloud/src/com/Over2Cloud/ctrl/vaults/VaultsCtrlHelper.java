package com.Over2Cloud.ctrl.vaults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class VaultsCtrlHelper {

	public boolean uploadFilesCreate(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		boolean status=false;
		List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("filePath");
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
		 status= cbt.createTable22("uploadedfiles",Tablecolumesaaa,connectionSpace);
		 return status;
	}
	
	public boolean uploadFilesInsert(CommonOperInterface cbt,SessionFactory connectionSpace,String filePath,String userName)
	{
		 boolean status=false;
		 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		 InsertDataTable ob=new InsertDataTable();
		 ob.setColName("filePath");
		 ob.setDataName(filePath);
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
		 
		status=cbt.insertIntoTable("uploadedfiles",insertData,connectionSpace);
		return status;
	}
	public List<VaultsPojo> getUserUploadedFiles(CommonOperInterface cbt,SessionFactory connectionSpace,String userName,String searchedData)
	{
		List<VaultsPojo>filesOfuser=new ArrayList<VaultsPojo>();
		
		try
		{
			StringBuilder query=new StringBuilder("select * from uploadedfiles where userName='"+userName+"'");
			if(searchedData!=null && !searchedData.equalsIgnoreCase("") && !searchedData.equalsIgnoreCase("null"))
			{
				query.append(" and filePath like '%"+searchedData+"%'");
			}
			query.append(" order by date desc, time desc");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null && obdata[3]!=null && obdata[4]!=null)
					{
						VaultsPojo vp=new VaultsPojo();
						vp.setUploadType("Owner");
						vp.setId((Integer)obdata[0]);
						vp.setFilePathForDownload(obdata[1].toString());
						vp.setUserName(obdata[2].toString());
						vp.setDateTime(DateUtil.convertDateToIndianFormat(obdata[3].toString())+", "+obdata[4].toString());
						String splitData[]=null;
						if(obdata[1].toString().contains("//"))
						{
							splitData=obdata[1].toString().split("//");
						}
						else if(obdata[1].toString().contains("/"))
						{
							splitData=obdata[1].toString().split("/");
						}
						try
						{
							vp.setFileName(splitData[2]);
						}
						catch(Exception e)
						{
						}
						if(vp.getUserName().equalsIgnoreCase(userName))
						{
							vp.setUserTrue("true");
						}
						else
						{
							vp.setUserTrue("false");
						}
						filesOfuser.add(vp);
					}
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
		return filesOfuser;
	}
	public List<VaultsPojo> getUserSharedFiles(CommonOperInterface cbt,SessionFactory connectionSpace,String empId,String userName,String searchedData)
	{
		List<VaultsPojo>filesOfuser=new ArrayList<VaultsPojo>();
		
		try
		{
			StringBuilder query=new StringBuilder("select id,attachedDocPath,userName,date,time,shareWithEmpid from submitpost " +
					"where attachedDocPath!='NULL' and attachedDocPath!='' " +
					"and attachedDocPath!='0' and shareWithEmpid like '%"+empId+"%' ");
			
			if(searchedData!=null && !searchedData.equalsIgnoreCase("") && !searchedData.equalsIgnoreCase("null"))
			{
				query.append(" and attachedDocPath like '%"+searchedData+"%'");
			}
			query.append(" order by date desc, time desc");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null && obdata[3]!=null && obdata[4]!=null && obdata[5]!=null)
					{
						VaultsPojo vp=new VaultsPojo();
						vp.setUploadType("Owner");
						vp.setId((Integer)obdata[0]);
						vp.setFilePathForDownload(obdata[1].toString());
						vp.setUserName(obdata[2].toString());
						vp.setDateTime(DateUtil.convertDateToIndianFormat(obdata[3].toString())+", "+obdata[4].toString());
						String splitData[]=null;
						if(obdata[1].toString().contains("//"))
						{
							splitData=obdata[1].toString().split("//");
						}
						else if(obdata[1].toString().contains("/"))
						{
							splitData=obdata[1].toString().split("/");
						}
						try
						{
							vp.setFileName(splitData[2]);
						}
						catch(Exception e)
						{
						}
						if(vp.getUserName().equalsIgnoreCase(userName))
						{
							vp.setUserTrue("true");
						}
						else
						{
							vp.setUserTrue("false");
						}
						if(obdata[5]!=null && !obdata[5].toString().equalsIgnoreCase(""))
						{
							String tempData[]=obdata[5].toString().split(",");
							for(String H:tempData)
							{
								if(H.equalsIgnoreCase(empId))
								{
									filesOfuser.add(vp);
									break;
								}
							}
						}
						
					}
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
		return filesOfuser;
	}
}
