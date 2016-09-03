package com.Over2Cloud.ctrl.homepage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class HomePageActionCtrlHelper {

	public void createTable(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("empID");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("sharedempName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("textData");
		 ob1.setDatatype("text");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("shareWithEmpid");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default 0");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("attachedDocPath");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("entryTime");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("readFlag");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("postFlag");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("pollPost");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("linkPost");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("grpID");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 cbt.createTable22("submitpost",Tablecolumesaaa,connectionSpace);
	}
	
	public boolean insertData(CommonOperInterface cbt,SessionFactory connectionSpace,String empID,
			String sharedempName,String textData,String shareWithEmpid,String attachedDocPath,String date,String time,String userName,
			String postFlag,String linkPostId,String pollPostPostId,String groupID)
	{
		boolean status=false;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=new InsertDataTable();
		ob.setColName("empID");
		ob.setDataName(empID);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("sharedempName");
		ob.setDataName(sharedempName);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("textData");
		ob.setDataName(textData);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("shareWithEmpid");
		ob.setDataName(shareWithEmpid);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("attachedDocPath");
		ob.setDataName(attachedDocPath);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("date");
		ob.setDataName(date);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("time");
		ob.setDataName(time);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("userName");
		ob.setDataName(userName);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("readFlag");
		ob.setDataName("0");
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("entryTime");
		ob.setDataName(DateUtil.getCurrentTime());
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("postFlag");
		ob.setDataName(postFlag);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("pollPost");
		ob.setDataName(pollPostPostId);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("linkPost");
		ob.setDataName(linkPostId);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("grpID");
		ob.setDataName(groupID);
		insertData.add(ob);
		
		status=cbt.insertIntoTable("submitpost",insertData,connectionSpace);
		return status;
		
	}
	
	public List<PojoBeanForHomePosts> getAllDataMappedWithCurrentLoginUser(SessionFactory connectionSpace,String empID,String userName,
			String to,String from,String postType)
	{
		List data=null;
		List<PojoBeanForHomePosts> dataTemp=new ArrayList<PojoBeanForHomePosts>();
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
				//getting only shared post with that employee
				query.append("select id,empID,sharedempName,textData,shareWithEmpid,attachedDocPath,date,time,userName,postFlag,pollPost,linkPost,grpID from submitpost " +
					" order by date desc,entryTime desc  limit "+to+","+from);
			data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					PojoBeanForHomePosts pb=new PojoBeanForHomePosts();
					Object[] obdata=(Object[])it.next();
					if(obdata!=null)
					{
							if(obdata[0]!=null)
								pb.setId((Integer)obdata[0]);
							if(obdata[1]!=null)
								pb.setEmpID((String)obdata[1]);
							if(obdata[2]!=null)
								pb.setSharedempName((String)obdata[2]);
							if(obdata[3]!=null)
								pb.setTextData((String)obdata[3]);
							if(obdata[4]!=null)
								pb.setShareWithEmpid((String)obdata[4]);
							if(obdata[5]!=null)
							{
								pb.setAttachedDocPath((String)obdata[5]);
								String splitData[]=null;
								if(obdata[5].toString().contains("//"))
								{
									splitData=obdata[5].toString().split("//");
								}
								else if(obdata[5].toString().contains("/"))
								{
									splitData=obdata[5].toString().split("/");
								}
								try
								{
									pb.setAttachDocName(splitData[2]);
								}
								catch(Exception e)
								{
								}
							}
							if(obdata[6]!=null)
							{
								pb.setDate((String)obdata[6]);
								if(pb.getDate().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
								{
									pb.setDate("Today At ");
								}
								else
								{
									pb.setDate(DateUtil.convertDateToIndianFormat(pb.getDate()));
								}
							}
							if(obdata[7]!=null)
								pb.setTime((String)obdata[7]);
							if(obdata[8]!=null)
							{
								//checking wheather the current logined user is the post user so he have privilages of deleting post, comment
								pb.setUserName((String)obdata[8]);
								if(pb.getUserName().equalsIgnoreCase(userName))
								{
									pb.setUserMatched("true");
								}
								else
								{
									pb.setUserMatched("false");
								}
							}
							
							
							if(obdata[10]!=null)// if that post is of type poll
							{
								pb.setPollPost((String)obdata[10]);
								query.delete(0, query.length());
								query.append("select id,c1,c2 from submitpollpost where id="+pb.getPollPost());
								List dataTempOfPoll=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if(dataTempOfPoll!=null)
								{
									for(Iterator itComment=dataTempOfPoll.iterator(); itComment.hasNext();)
									{
										Object[] objectPollPost=(Object[])itComment.next();
										if(objectPollPost!=null)
										{
											pb.setC1(objectPollPost[1].toString());
											pb.setC2(objectPollPost[2].toString());
										}
									}
								}
							}
							if(obdata[11]!=null)//if that post is of type link
							{
								pb.setLinkPost((String)obdata[11]);
								query.delete(0, query.length());
								query.append("select linkurl,linkname from submitlinkpost where id="+pb.getLinkPost());
								List dataTempOfPoll=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if(dataTempOfPoll!=null)
								{
									for(Iterator itComment=dataTempOfPoll.iterator(); itComment.hasNext();)
									{
										Object[] objectPollPost=(Object[])itComment.next();
										if(objectPollPost!=null)
										{
											pb.setLinkURL(objectPollPost[0].toString());
											pb.setLinkName(objectPollPost[1].toString());
										}
									}
								}
							}
							/**
							 * getting the list of comment of on a post
							 * 
							 */
							try
							{
								List<CommentPojo>commentData=new ArrayList<CommentPojo>();
								query.delete(0, query.length());
								query.append("select id,textData,date,time,empName,userName from submitpostcomments where postId="+pb.getId()+"  order by date desc,entryTime desc limit 0,5");
								List dataTempOfComment=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if(dataTempOfComment!=null)
								{
									for(Iterator itComment=dataTempOfComment.iterator(); itComment.hasNext();)
									{
										CommentPojo cp=new CommentPojo();
										Object[] objectComment=(Object[])itComment.next();
										if(objectComment!=null)
										{
											if(objectComment[0]!=null)
												cp.setId((Integer)objectComment[0]);
											if(objectComment[1]!=null)
												cp.setCommentdata(objectComment[1].toString());
											if(objectComment[2]!=null)
											{
												cp.setDate(objectComment[2].toString());
												if(cp.getDate().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
												{
													cp.setDate("Today At ");
												}
												else
												{
													cp.setDate(DateUtil.convertDateToIndianFormat(cp.getDate()));
												}
											}
											if(objectComment[3]!=null)
												cp.setTime(objectComment[3].toString());
											if(objectComment[4]!=null)
												cp.setEmpName(objectComment[4].toString());
											if(objectComment[5]!=null)
											{
												if(objectComment[5].toString().equalsIgnoreCase(userName))
												{
													cp.setUserMatched("true");
												}
												else
												{
													cp.setUserMatched("false");
												}
											}
										}
										commentData.add(cp);
									}
									
								}
								//CommentPojo
								pb.setCommentData(commentData);
							}
							catch(Exception e)
							{
								
							}
							
							/**
							 * 
							 * getting total no of comment for a like
							 */
							try
							{
								int likes=0;
								query.delete(0, query.length());
								query.append("select empID from submitpostlike where postId="+pb.getId());
								List dataTempOfLike=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if(dataTempOfLike!=null)
								{
									for(Iterator itComment=dataTempOfLike.iterator(); itComment.hasNext();)
									{
										Object objectLike=(Object)itComment.next();
										likes=dataTempOfLike.size();//getting total no of likes for a post and also checking wheather the current user have liked that post or not
										if(empID.equalsIgnoreCase(objectLike.toString()))
										{
											pb.setLiked("false");
											break;
										}
									}
								}
								pb.setTotalLikes(likes);
							}
							catch(Exception e)
							{
								
							}
							
							Map<String, String>empIDs=new HashMap<String, String>();
							if(postType==null || postType.equalsIgnoreCase(""))//for all common ,shared
							{
								if((pb.getShareWithEmpid()==null || pb.getShareWithEmpid().equalsIgnoreCase("") || pb.getShareWithEmpid().equalsIgnoreCase("0"))
										&& (obdata[12]==null || obdata[12].toString().equalsIgnoreCase("null") || obdata[12].toString().equalsIgnoreCase("-1")))
								{
										dataTemp.add(pb);
								}
								else
								{
									if(pb.getEmpID().equalsIgnoreCase(empID))
									{
										dataTemp.add(pb);
									}
									String temp[]=pb.getShareWithEmpid().split(",");
									for(String H:temp)
									{
										if(!H.equalsIgnoreCase(""))
										{
											if(empID.equalsIgnoreCase(H))
											{
												empIDs.put(H, H);
												
											}
										}
									}
									if(empIDs!=null && empIDs.size()>0)
									{
										for(int i=0;i<empIDs.size();i++)
											dataTemp.add(pb);
										
									}
								}
							}
							else//for shared post only
							{
								String temp[]=pb.getShareWithEmpid().split(",");
								for(String H:temp)
								{
									if(!H.equalsIgnoreCase(""))
									{
										if(empID.equalsIgnoreCase(H))
										{
											empIDs.put(H, H);
											
										}
									}
								}
								if(empIDs!=null && empIDs.size()>0)
								{
									for(int i=0;i<empIDs.size();i++)
										dataTemp.add(pb);
									
								}
								
							}
							/*
							 * 
							 * Group sharing view code done aleady here
							 */
							if(obdata[12]!=null && !obdata[12].toString().equalsIgnoreCase("") && !obdata[12].toString().equalsIgnoreCase("-1")
								 && !obdata[12].toString().equalsIgnoreCase("null"))
							{
								query.delete(0, query.length());
								query.append("select mappedid from groupinfomapping where mappedlevelid='1' and grpid="+obdata[12].toString());
								List dataOfGroup=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if(dataOfGroup!=null)
								{
									for(Iterator itComment=dataOfGroup.iterator(); itComment.hasNext();)
									{
										Object objectLike=(Object)itComment.next();
										if(objectLike!=null)
										{
											String status=empIDs.get(objectLike.toString());
											if(status==null && objectLike.toString().equalsIgnoreCase(empID))
											{
												//ading for member in a group
												dataTemp.add(pb);
											}
										}
									}
									}
								}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataTemp;
	}
	
	public void createTableForComment(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("empID");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("empName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("postId");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("textData");
		 ob1.setDatatype("text");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("entryTime");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("readFlag");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 cbt.createTable22("submitpostcomments",Tablecolumesaaa,connectionSpace);
	}
	
	public boolean insertDataForComment(CommonOperInterface cbt,SessionFactory connectionSpace,String empID,String empName,
			String postId,String textData,String date,String time,String userName)
	{
		boolean status=false;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=new InsertDataTable();
		ob.setColName("empID");
		ob.setDataName(empID);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("empName");
		ob.setDataName(empName);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("postId");
		ob.setDataName(postId);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("textData");
		ob.setDataName(textData);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("date");
		ob.setDataName(date);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("time");
		ob.setDataName(time);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("userName");
		ob.setDataName(userName);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("readFlag");
		ob.setDataName("0");
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("entryTime");
		ob.setDataName(DateUtil.getCurrentTime());
		insertData.add(ob);
		status=cbt.insertIntoTable("submitpostcomments",insertData,connectionSpace);
		return status;
	}
	
	
	public void createTableForLike(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("empID");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("empName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("postId");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("entryTime");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("readFlag");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 cbt.createTable22("submitpostlike",Tablecolumesaaa,connectionSpace);
	}
	
	public boolean insertDataForLike(CommonOperInterface cbt,SessionFactory connectionSpace,String empID,String empName,
			String postId,String date,String time,String userName)
	{
		boolean status=false;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=new InsertDataTable();
		ob.setColName("empID");
		ob.setDataName(empID);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("empName");
		ob.setDataName(empName);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("postId");
		ob.setDataName(postId);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("date");
		ob.setDataName(date);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("time");
		ob.setDataName(time);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("userName");
		ob.setDataName(userName);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("readFlag");
		ob.setDataName("0");
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("entryTime");
		ob.setDataName(DateUtil.getCurrentTime());
		insertData.add(ob);
		status=cbt.insertIntoTable("submitpostlike",insertData,connectionSpace);
		return status;
	}
	
	
	public void createTableOfLink(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("linkurl");
		 ob1.setDatatype("text");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("linkname");
		 ob1.setDatatype("text");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);

		 cbt.createTable22("submitlinkpost",Tablecolumesaaa,connectionSpace);
	}
	
	public boolean insertDataInLink(CommonOperInterface cbt,SessionFactory connectionSpace,String linkURL,String linkName)
	{
		boolean status=false;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=new InsertDataTable();
		ob.setColName("linkurl");
		ob.setDataName(linkURL);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("linkname");
		ob.setDataName(linkName);
		insertData.add(ob);
		status=cbt.insertIntoTable("submitlinkpost",insertData,connectionSpace);
		return status;
	}
	
	public void createTableOfPoll(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("c1");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("c2");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 cbt.createTable22("submitpollpost",Tablecolumesaaa,connectionSpace);
	}
	
	public boolean insertDataInPoll(CommonOperInterface cbt,SessionFactory connectionSpace,String c1,String c2)
	{
		boolean status=false;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		InsertDataTable ob=new InsertDataTable();
		ob.setColName("c1");
		ob.setDataName(c1);
		insertData.add(ob);
		ob=new InsertDataTable();
		ob.setColName("c2");
		ob.setDataName(c2);
		insertData.add(ob);
		status=cbt.insertIntoTable("submitpollpost",insertData,connectionSpace);
		return status;
	}
	
	
	public boolean likeExistDelete(CommonOperInterface cbt,SessionFactory connectionSpace,String empID,String postID)
	{
		
		boolean status=true;
		StringBuilder query=new StringBuilder("select count(*) from submitpostlike where empID='"+empID+"' and postId='"+postID+"'");
		List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null)
		{
			for(Iterator itComment=data.iterator(); itComment.hasNext();)
			{
				Object objectLike=(Object)itComment.next();
				BigInteger big=new BigInteger("3");
				big=(BigInteger)objectLike;
				if(big.intValue()==0)//no exist
					status=true;
				else
				{
						//delete query for a post
					Session session = connectionSpace.openSession();  
					Transaction transaction = null;  
					transaction = session.beginTransaction();  
					query.delete(0, query.length());
					query.append("delete from submitpostlike where empID='"+empID+"' and postId='"+postID+"'");
					int size=session.createSQLQuery(query.toString()).executeUpdate();
					if(size!=0)
						status=false;
					transaction.commit(); 
					session.close();
				}
			}
		}
		return status;
	}
	public List<CommentPojo> getCommentForPagination(String postID,SessionFactory connectionSpace,String userName,String toComment)
	{
		List<CommentPojo>commentData=new ArrayList<CommentPojo>();
		try
		{
			StringBuilder query=new StringBuilder("");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			query.append("select id,textData,date,time,empName,userName from submitpostcomments where postId="+postID+"  order by date desc,entryTime desc limit "+toComment+",5");
			List dataTempOfComment=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataTempOfComment!=null)
			{
				for(Iterator itComment=dataTempOfComment.iterator(); itComment.hasNext();)
				{
					CommentPojo cp=new CommentPojo();
					Object[] objectComment=(Object[])itComment.next();
					if(objectComment!=null)
					{
						if(objectComment[0]!=null)
							cp.setId((Integer)objectComment[0]);
						if(objectComment[1]!=null)
							cp.setCommentdata(objectComment[1].toString());
						if(objectComment[2]!=null)
						{
							cp.setDate(objectComment[2].toString());
							if(cp.getDate().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
							{
								cp.setDate("Today At ");
							}
							else
							{
								cp.setDate(DateUtil.convertDateToIndianFormat(cp.getDate()));
							}
						}
						if(objectComment[3]!=null)
							cp.setTime(objectComment[3].toString());
						if(objectComment[4]!=null)
							cp.setEmpName(objectComment[4].toString());
						if(objectComment[5]!=null)
						{
							if(objectComment[5].toString().equalsIgnoreCase(userName))
							{
								cp.setUserMatched("true");
							}
							else
							{
								cp.setUserMatched("false");
							}
						}
					}
					commentData.add(cp);
				}
				
			}
		}
		catch(Exception e)
		{
			
		}
		return commentData;
	}
	
	public Map<Integer, String> getGroupsmapped(SessionFactory connectionSpace,String userName)
	{
		Map<Integer, String> mappedGrops=new HashMap<Integer, String>();
		
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("select id,grpName from groupinfo where userName='"+userName+"' or grpLevel=1");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && obdata[0]!=null && obdata[1]!=null)
					{
						mappedGrops.put((Integer)obdata[0], obdata[1].toString());
					}
				}
			}
		} 
		catch(Exception e)
		{
			
		}
		
		return mappedGrops;
	}
}
