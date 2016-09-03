package com.Over2Cloud.ctrl.SeekApproval;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.ActionOnFeedback;

public class SeekApproval extends GridPropertyBean
{
	private String moduleName;
	private String complaintId;
	private Map<String, String> upperLevelContact;
	public String approvalDoc;
	private String approvedBy;
	SeekApprovalHelper helperObject = new SeekApprovalHelper();
	private String reason;
	private String approvalStatus;
	private String actionBy;
	private Map<String, String> outletList;
	private List<Object> masterViewList;
	public File approvedDoc;
	private String approvedDocContentType;
	private String approvedDocFileName;
	private String comments;
	private String status;
	private String seekId;
	private String columnName;
	private String tableName;
	private String pkId;
	private Map<String, String> docPathName;
	private String fileName;
	private String documentName;
	private InputStream fileInputStream;
	private String approvedById;
	public CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<String,String> dataMap;
	private Map<String, String> dataCountMap;
	private Map<String, String> requestByMap;
	private Map<String, String> requestToMap;
	private String maxDateValue;
	private String minDateValue;
	private String reqBy;
	private String reqTo;
	
	public String getData4SeekApproval()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("Inisde Method ");
				upperLevelContact = new LinkedHashMap<String, String>();
				List tempList = helperObject.getUpperLevelContact(complaintId,moduleName,connectionSpace,cbt);
				if(tempList!=null && tempList.size()>0)
				{
					for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						upperLevelContact.put(object[0].toString(), object[1].toString());
					}
				}
				System.out.println("upperLevelContact "+upperLevelContact.size());
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String seekApprove()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				boolean status = helperObject.createTable4SeekApproval(connectionSpace,cbt);
				if(status)
				{
					status = false;
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("complaint_id", complaintId);
					dataMap.put("module_name", moduleName);
					dataMap.put("approved_by", approvedBy);
					dataMap.put("request_on", DateUtil.getCurrentDateUSFormat());
					dataMap.put("request_at", DateUtil.getCurrentTimeHourMin());
					dataMap.put("doc_for_approval", approvalDoc);
					dataMap.put("status", "Pending");
					dataMap.put("reason", reason);
					dataMap.put("user", userName);
					
					status = helperObject.saveData4Approval(dataMap,connectionSpace, cbt);
				}
				if(status)
				{
					status = false;
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", complaintId);
					setVariables.put("status", "Hold");
					if(moduleName!=null && moduleName.equalsIgnoreCase("ASTM"))
						status = new HelpdeskUniversalHelper().updateTableColomn("asset_complaint_status", setVariables, whereCondition, connectionSpace);
					else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
						status = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", setVariables, whereCondition, connectionSpace);
					
					if(status)
					{
						addActionMessage("Approval requested sucessfully");
						sendAlert(complaintId,"Approval Alert", moduleName, approvedBy, reason);
					}
					
				}
				
				System.out.println(complaintId+" >>> "+moduleName+" >>> "+approvalDoc);
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Opps, there are some problem in data save.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public void sendAlert(String complaintId,String templeteFor,String moduleName,String contactId,String reason)
	{
		
		List dataList = helperObject.fetchDataForAlert(complaintId,connectionSpace,cbt,moduleName);
		String ticketNo = null, feedByMobileNo = null, feedByEmail = null, feedBrief = null, allotToName =null, feedBy = null;
		String approvedByName = null, approvedByMobileNo = null, approvedByEmailId = null, allotToMobNo = null;
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null)
					ticketNo = object[0].toString();
				
				if(object[1]!=null)
					feedByMobileNo = object[1].toString();
				
				if(object[2]!=null)
					feedByEmail = object[2].toString();
				
				if(object[3]!=null)
					feedBrief = object[3].toString();
				
				if(object[4]!=null)
					allotToName = object[4].toString();
				
				if(object[5]!=null)
					feedBy = object[5].toString();
				
				if(object[6]!=null)
					allotToMobNo = object[6].toString();
				
			}
			dataList.clear();
		}
		dataList = helperObject.fetchDataForAlert(contactId,connectionSpace,cbt);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null)
					approvedByName = object[0].toString();
				
				if(object[1]!=null)
					approvedByMobileNo = object[1].toString();
				
				if(object[2]!=null)
					approvedByEmailId = object[2].toString();
				
			}
			dataList.clear();
		}
		if(templeteFor!=null)
		{
			if(templeteFor.equalsIgnoreCase("Approval Alert"))
			{
				makeTemplete("Approval Alert",  ticketNo,  feedBy,  feedByMobileNo,  feedBrief,  allotToName,  approvedByName, approvedByMobileNo, reason, allotToMobNo, moduleName);
				//makeTemplete("Hold Alert",  ticketNo,  feedBy,  feedByMobileNo,  feedBrief,  allotToName,  approvedByName, approvedByMobileNo, reason);
			}
			else if(templeteFor.equalsIgnoreCase("Approved"))
			{
				makeTemplete("Approved",  ticketNo,  feedBy,  feedByMobileNo,  feedBrief,  allotToName,  approvedByName, approvedByMobileNo, reason, allotToMobNo, moduleName);
				//makeTemplete("Hold To Active Alert",  ticketNo,  feedBy,  feedByMobileNo,  feedBrief,  allotToName,  approvedByName, approvedByMobileNo, reason);
			}
		}
	}
	
	private void makeTemplete(String templeteFlag, String ticketNo, String feedBy, String feedByMobileNo, String feedBrief, String allotToName, String approvedByName,String approvedByMobileNo,String reason,String allotToMobNo, String moduleName)
	{
		if(templeteFlag!=null)
		{
			CommunicationHelper cmnHelper = new CommunicationHelper();
			if(templeteFlag.equalsIgnoreCase("Approval Alert"))
			{
				String msg = "Seek Approval for "+ticketNo+", Brief: "+feedBrief+", Reason: "+reason+" By: "+allotToName+".";
				System.out.println(approvedByMobileNo);
				if (approvedByMobileNo != null && approvedByMobileNo.toString() != "")
					cmnHelper.addMessage(approvedByName, "", approvedByMobileNo, msg, "", "Pending", "0", moduleName, connectionSpace);
				
				msg = "Dear "+feedBy+", your ticket ID "+ticketNo+", Brief: "+feedBrief+" is on hold till necessary approval is seek to execute the same.";
				if (feedByMobileNo != null && feedByMobileNo.toString() != "")
					cmnHelper.addMessage(feedBy, "", feedByMobileNo, msg, "", "Pending", "0", moduleName, connectionSpace);


			}
			if(templeteFlag.equalsIgnoreCase("Approved"))
			{
				String msg = "Approved: "+ticketNo+", Brief: "+feedBrief+", Reason: "+reason+" By: "+approvedByName+".";
				if (allotToMobNo != null && allotToMobNo.toString() != "")
					cmnHelper.addMessage(allotToName, "", allotToMobNo, msg, "", "Pending", "0", moduleName, connectionSpace);
				
				
				msg = "Dear "+feedBy+", your ticket ID "+ticketNo+", Brief: "+feedBrief+" is under action as approval has been given for the same.";
				if (feedByMobileNo != null && feedByMobileNo.toString() != "")
					cmnHelper.addMessage(feedBy, "", feedByMobileNo, msg, "", "Pending", "0", moduleName, connectionSpace);
			}
			/*else if(templeteFlag.equalsIgnoreCase("Approved Alert"))
			{
				
			}
			
			String mailText = getConfigMail(taskName, taskType, dueDate, frequency, mappedTeam, actionStatus, comments, empName, contactId, mailTitle);
			if (emailId != null && emailId.toString() != "")
				cmnHelper.addMail(empName, object2[4].toString(), emailId, mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);*/
		}
	}
	
	
	public String beforeViewSeekApproval()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String empId=(String) session.get("empIdofuser");
				SeekApprovalHelper SAH = new SeekApprovalHelper();
				outletList = new LinkedHashMap<String, String>();
				requestByMap  = new LinkedHashMap<String, String>();
				requestToMap = new LinkedHashMap<String, String>();
				if(moduleName!=null && moduleName.equalsIgnoreCase("ASTM"))
				{
					//outletList = new ActionOnAssetComplaint().outletIdMap();
				}
				else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
				{
					requestToMap = SAH.getEmpDetails(connectionSpace,cbt, empId.split("-")[1], "upperLevel");
					requestByMap = SAH.getEmpDetails(connectionSpace,cbt, empId.split("-")[1], "lowerLevel");
				}
				maxDateValue = DateUtil.getCurrentDateUSFormat();
				minDateValue = DateUtil.getNewDate("day", -30, DateUtil.getCurrentDateUSFormat());
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Opps, there are some problem in data save.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeViewSeekApprovalInGrid()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				if(moduleName!=null && moduleName.equalsIgnoreCase("ASTM"))
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName("outlet");
					gpv.setHeadingName("Outlet");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("assetname");
					gpv.setHeadingName("Asset Name");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					viewPageColumns.add(gpv);
					
				}
				else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName("deptName");
					gpv.setHeadingName("Department");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("true");
					viewPageColumns.add(gpv);
				}
				
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("subcatg");
				gpv.setHeadingName("Requested For");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(140);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("brief");
				gpv.setHeadingName("Brief");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(150);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("reason");
				gpv.setHeadingName("Reason");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(160);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("approvaldoc");
				gpv.setHeadingName("Requested Doc");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setFormatter("downloadApprovaldoc");
				gpv.setWidth(130);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("requeston");
				gpv.setHeadingName("Requested On");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(140);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("allotto");
				gpv.setHeadingName("Requested By");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(150);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("status");
				gpv.setHeadingName("Status");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(90);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("ticketno");
				gpv.setHeadingName("Ticket No");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(90);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("openon");
				gpv.setHeadingName("Opened On");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(140);
				viewPageColumns.add(gpv);
				
				
				if(approvalStatus!=null && (approvalStatus.equalsIgnoreCase("All") || approvalStatus.equalsIgnoreCase("Approved") || approvalStatus.equalsIgnoreCase("Reject")))
				{
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("approvedcomment");
					gpv.setHeadingName("Action Reason");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(100);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("approvedon");
					gpv.setHeadingName("Action On");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(140);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("approvedby");
					gpv.setHeadingName("Action By");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(120);
					viewPageColumns.add(gpv);
					
					
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("approveddoc");
					gpv.setHeadingName("Supported Doc");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(100);
					gpv.setFormatter("downloadApprovedDoc");
					viewPageColumns.add(gpv);
				}
				
				
				
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String viewSeekApproval()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				if(moduleName!=null && actionBy!=null && approvalStatus!=null)
				{
					List<Object> Listhb=new ArrayList<Object>();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					String empId = (String)session.get("empIdofuser").toString().split("-")[1];
					List dataList = null;
					if(actionBy.equalsIgnoreCase("selfRequest"))
					{
						dataList = helperObject.getSeekApprovalData(connectionSpace,cbt, moduleName, approvalStatus, actionBy, empId,maxDateValue,minDateValue,reqBy,reqTo);
					}
					else if(actionBy.equalsIgnoreCase("selfApprove"))
					{
						String contactId = helperObject.getAllContactIdByEmpId(connectionSpace, cbt,moduleName,empId);
						dataList = helperObject.getSeekApprovalData(connectionSpace,cbt, moduleName, approvalStatus, actionBy, contactId,maxDateValue,minDateValue,reqBy,reqTo);
					}
					System.out.println("Data List Size "+dataList.size());
					if(dataList!=null && dataList.size()>0)
					{
						if(moduleName.equalsIgnoreCase("ASTM"))
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								Map<String, Object> one = new HashMap<String, Object>();
								one.put("id", CUA.getValueWithNullCheck(object[0]));
								one.put("outlet", CUA.getValueWithNullCheck(object[1]));
								one.put("assetname", CUA.getValueWithNullCheck(object[2])+" - "+CUA.getValueWithNullCheck(object[3]));
								one.put("ticketno", CUA.getValueWithNullCheck(object[4]));
								one.put("allotto", CUA.getValueWithNullCheck(object[5]));
								one.put("reason", CUA.getValueWithNullCheck(object[6]));
								
								if(object[7]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[7].toString());
									String time = object[8].toString();
									one.put("requeston", date+", "+time);
								}
								
								one.put("subcatg", CUA.getValueWithNullCheck(object[9]));
								one.put("brief", CUA.getValueWithNullCheck(object[10]));
								
								if(object[11]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[11].toString());
									String time = object[12].toString();
									one.put("openon", date+", "+time);
								}
									
								
								if(object[13]!=null && !object[13].equals(""))
								{
									String str=object[13].toString().substring(object[13].toString().lastIndexOf("//")+2, object[13].toString().length());
									String docName=str.substring(14,str.length());
									one.put("approvaldoc", docName);
								}
								else
								{
									one.put("approvaldoc", "NA");
								}
								
								
								if(object[14]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[14].toString());
									String time = object[15].toString();
									one.put("approvedon", date+", "+time);
								}
									
								
								one.put("approvedby", CUA.getValueWithNullCheck(object[16]));
								one.put("approvedcomment", CUA.getValueWithNullCheck(object[17]));
								
								if(object[18]!=null && !object[18].equals(""))
								{
									String str=object[18].toString().substring(object[18].toString().lastIndexOf("//")+2, object[18].toString().length());
									String docName=str.substring(14,str.length());
									one.put("approveddoc", docName);
								}
								else
								{
									one.put("approveddoc", "NA");
								}
								Listhb.add(one);
							}
						}
						else
						{

							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								Map<String, Object> one = new HashMap<String, Object>();
								one.put("id", CUA.getValueWithNullCheck(object[0]));
								one.put("deptName", CUA.getValueWithNullCheck(object[1]));
								one.put("ticketno", CUA.getValueWithNullCheck(object[4]));
								one.put("allotto", CUA.getValueWithNullCheck(object[5]));
								one.put("reason", CUA.getValueWithNullCheck(object[6]));
								
								if(object[7]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[7].toString());
									String time = object[8].toString();
									one.put("requeston", date+", "+time);
								}
								
								one.put("subcatg", CUA.getValueWithNullCheck(object[9]));
								one.put("brief", CUA.getValueWithNullCheck(object[10]));
								
								if(object[11]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[11].toString());
									String time = object[12].toString();
									one.put("openon", date+", "+time.substring(0,time.length()-3));
								}
									
								
								if(object[13]!=null && !object[13].equals(""))
								{
									String str=object[13].toString().substring(object[13].toString().lastIndexOf("//")+2, object[13].toString().length());
									String docName=str.substring(14,str.length());
									one.put("approvaldoc", docName);
								}
								else
								{
									one.put("approvaldoc", "NA");
								}
								
								
								if(object[14]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[14].toString());
									String time = object[15].toString();
									one.put("approvedon", date+", "+time);
								}
									
								
								one.put("approvedby", CUA.getValueWithNullCheck(object[16]));
								one.put("approvedcomment", CUA.getValueWithNullCheck(object[17]));
								
								if(object[18]!=null && !object[18].equals(""))
								{
									String str=object[18].toString().substring(object[18].toString().lastIndexOf("//")+2, object[18].toString().length());
									String docName=str.substring(14,str.length());
									one.put("approveddoc", docName);
								}
								else
								{
									one.put("approveddoc", "NA");
								}
								
								one.put("status", CUA.getValueWithNullCheck(object[19]));
								
								Listhb.add(one);
							}
						
						}
						setMasterViewList(Listhb);
					}
				}
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String actionOnSeekApproval()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("test");
				boolean statusFlag = false;
				Map<String, Object> setVariables = new HashMap<String, Object>();
				Map<String, Object> whereCondition = new HashMap<String, Object>();
				System.out.println("Idddd "+seekId);
				List dataList = helperObject.getCompliantIdBySeekId(connectionSpace,cbt, seekId);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						String reason = null;
						String complainId = object1[0].toString();
						String approvedBy = object1[1].toString();
						String moduleName = object1[3].toString();
						if(object1[2]!=null)
							reason = object1[2].toString();
						
						
						String path = getDocPath();
						if(comments!=null)
							setVariables.put("approved_comment", comments);
						
						if(!status.equals("-1"))
							setVariables.put("status", status);
						
						if(path!=null && !path.equals(""))
							setVariables.put("doc_after_approval", path);
						
						setVariables.put("approved_on", DateUtil.getCurrentDateUSFormat());
						setVariables.put("approved_at", DateUtil.getCurrentTimeHourMin());
						whereCondition.put("id", seekId);
						statusFlag = new HelpdeskUniversalHelper().updateTableColomn("seek_approval_detail", setVariables, whereCondition, connectionSpace);
						whereCondition.clear();
						setVariables.clear();
						
						if(moduleName.equalsIgnoreCase("ASTM") && statusFlag)
						{
							statusFlag = false;
							whereCondition.put("id", complainId);
							setVariables.put("status", "Pending");
							
							statusFlag = new HelpdeskUniversalHelper().updateTableColomn("asset_complaint_status", setVariables, whereCondition, connectionSpace);
							if(statusFlag)
							{
								sendAlert(complainId,"Approved",moduleName,approvedBy,reason);
							}
						}
						else if(moduleName.equalsIgnoreCase("HDM") && statusFlag)
						{
							statusFlag = false;
							whereCondition.put("id", complainId);
							setVariables.put("status", "Pending");
							
							statusFlag = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", setVariables, whereCondition, connectionSpace);
							if(statusFlag)
							{
								sendAlert(complainId,"Approved",moduleName,approvedBy,reason);
							}
						}
						
					}
				}
				
				if(statusFlag)
				{
					addActionMessage("Request Approved sucessfully");
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Opps. There are some problem.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getDocPath()
	{
		String renameFilePath = null;
		if (approvedDocFileName != null)
		{
			renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + approvedDocFileName;
			String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + approvedDocFileName;
			String str = renameFilePath.replace("//", "/");
			// renameFilePath=DateUtil.removeSpace(renameFilePath);
			// storeFilePath=DateUtil.removeSpace(storeFilePath);
			if (storeFilePath != null)
			{
				File theFile = new File(storeFilePath);
				File newFileName = new File(str);
				if (theFile != null)
				{
					try
					{
						FileUtils.copyFile(approvedDoc, theFile);
						if (theFile.exists())
							theFile.renameTo(newFileName);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return renameFilePath;
	}
	
	
	public String beforeTakeAction()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				dataMap = new LinkedHashMap<String, String>();
				if(moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
				{
					String query="SELECT complaint_id,request_on,request_at,reason FROM seek_approval_detail WHERE module_name='HDM' AND id="+seekId;
					System.out.println(query);
					List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						String complainId = null,requestOn = null, reason = null;
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							complainId = object[0].toString();
							requestOn = DateUtil.convertDateToIndianFormat(object[1].toString())+", "+object[2].toString();
							reason = object[3].toString();
						}
						dataMap = new ActionOnFeedback().getTicketDetails(complainId);
						dataMap.put("Requested On", requestOn);
						dataMap.put("Reason", reason);
						dataList.clear();
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getActualDocPath()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				docPathName  = new HashMap<String, String>();
				if(pkId!=null && columnName!=null && tableName!=null)
				{
					List dataList = helperObject.getActualDocPath(connectionSpace,cbt, pkId, columnName, tableName);
					if(dataList!=null && dataList.size()>0)
					{
						if(dataList.get(0)!=null)
						{
							docPathName.put("id", pkId);
							docPathName.put("path", dataList.get(0).toString());
						}
							
						
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String docDownload()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(fileName!=null && fileName.length()>1)
				{
					File file=new File(fileName);
					String str = fileName.substring(fileName.lastIndexOf("//")+2, fileName.length());
					documentName=str.substring(14,str.length());
					fileName=documentName;
		            if(file.exists())
		            {
		            	fileInputStream = new FileInputStream(file);
		            	return SUCCESS;
		            }
		            else
		            {
		                 addActionError("File dose not exist");
		                 return ERROR;
		            }
				}
				else
				{
					 addActionError("File dose not exist");
					 return ERROR;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	public Map<String, String> statusList(String feedStatus)
	{
		Map<String, String> statusList = new LinkedHashMap<String, String>();
		try
		{
			if (feedStatus.equalsIgnoreCase("Pending"))
			{
				statusList.put("Snooze", "Snooze");
				statusList.put("High Priority", "High Priority");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
				statusList.put("Hold", "Seek Approval");
			}
			else if (feedStatus.equalsIgnoreCase("Snooze"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("High Priority", "High Priority");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
				statusList.put("Hold", "Seek Approval");
				
			}
			else if (feedStatus.equalsIgnoreCase("High Priority"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
				statusList.put("Hold", "Seek Approval");
			}
			else if (feedStatus.equalsIgnoreCase("Ignore"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("Resolved", "Resolved");
				statusList.put("High Priority", "High Priority");
				statusList.put("Hold", "Seek Approval");
			}
			else if (feedStatus.equalsIgnoreCase("Resolved"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
				statusList.put("Hold", "Seek Approval");
			}
			/*else if (feedStatus.equalsIgnoreCase("Hold"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("High Priority", "High Priority");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
			}*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusList;
	}

	public String gridDataCount()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				dataCountMap = new LinkedHashMap<String, String>();
				dataCountMap.put("Pending", "0");
				dataCountMap.put("Approved", "0");
				dataCountMap.put("DisApproved", "0");
				if(moduleName!=null && actionBy!=null && approvalStatus!=null)
				{
					List<Object> Listhb=new ArrayList<Object>();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					String empId = (String)session.get("empIdofuser").toString().split("-")[1];
					List dataList = null;
					if(actionBy.equalsIgnoreCase("selfRequest"))
					{
						dataList = helperObject.getSeekApprovalDataCount(connectionSpace,cbt, moduleName, approvalStatus, actionBy, empId,maxDateValue,minDateValue,reqBy,reqTo);
					}
					else if(actionBy.equalsIgnoreCase("selfApprove"))
					{
						String contactId = helperObject.getAllContactIdByEmpId(connectionSpace, cbt,moduleName,empId);
						dataList = helperObject.getSeekApprovalDataCount(connectionSpace,cbt, moduleName, approvalStatus, actionBy, contactId,maxDateValue,minDateValue,reqBy,reqTo);
					}
					System.out.println("Data List Size "+dataList.size());
					int totalValue = 0;
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator3 = dataList.iterator(); iterator3.hasNext();)
						{
							Object[] object2 = (Object[]) iterator3.next();
							if (object2[1].toString().equalsIgnoreCase("Pending"))
							{
								dataCountMap.put("Pending", object2[0].toString());
								totalValue = totalValue + Integer.parseInt(object2[0].toString());
							}
							else if (object2[1].toString().equalsIgnoreCase("Approved"))
							{
								dataCountMap.put("Approved", object2[0].toString());
								totalValue = totalValue + Integer.parseInt(object2[0].toString());
							}
							else if (object2[1].toString().equalsIgnoreCase("Disapproved"))
							{
								dataCountMap.put("DisApproved", object2[0].toString());
								totalValue = totalValue + Integer.parseInt(object2[0].toString());
							}
						}
					}
					dataCountMap.put("total", String.valueOf(totalValue));
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getComplaintId()
	{
		return complaintId;
	}

	public void setComplaintId(String complaintId)
	{
		this.complaintId = complaintId;
	}

	public Map<String, String> getUpperLevelContact()
	{
		return upperLevelContact;
	}

	public void setUpperLevelContact(Map<String, String> upperLevelContact)
	{
		this.upperLevelContact = upperLevelContact;
	}

	public String getApprovalDoc()
	{
		return approvalDoc;
	}

	public void setApprovalDoc(String approvalDoc)
	{
		this.approvalDoc = approvalDoc;
	}

	public String getApprovedBy()
	{
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy)
	{
		this.approvedBy = approvedBy;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public Map<String, String> getOutletList()
	{
		return outletList;
	}

	public void setOutletList(Map<String, String> outletList)
	{
		this.outletList = outletList;
	}

	public String getApprovalStatus()
	{
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus)
	{
		this.approvalStatus = approvalStatus;
	}

	public String getActionBy()
	{
		return actionBy;
	}

	public void setActionBy(String actionBy)
	{
		this.actionBy = actionBy;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public File getApprovedDoc()
	{
		return approvedDoc;
	}

	public void setApprovedDoc(File approvedDoc)
	{
		this.approvedDoc = approvedDoc;
	}

	public String getApprovedDocContentType()
	{
		return approvedDocContentType;
	}

	public void setApprovedDocContentType(String approvedDocContentType)
	{
		this.approvedDocContentType = approvedDocContentType;
	}

	public String getApprovedDocFileName()
	{
		return approvedDocFileName;
	}

	public void setApprovedDocFileName(String approvedDocFileName)
	{
		this.approvedDocFileName = approvedDocFileName;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getSeekId()
	{
		return seekId;
	}

	public void setSeekId(String seekId)
	{
		this.seekId = seekId;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getPkId()
	{
		return pkId;
	}

	public void setPkId(String pkId)
	{
		this.pkId = pkId;
	}

	public Map<String, String> getDocPathName()
	{
		return docPathName;
	}

	public void setDocPathName(Map<String, String> docPathName)
	{
		this.docPathName = docPathName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getApprovedById()
	{
		return approvedById;
	}

	public void setApprovedById(String approvedById)
	{
		this.approvedById = approvedById;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public Map<String, String> getDataCountMap() {
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap) {
		this.dataCountMap = dataCountMap;
	}

	public Map<String, String> getRequestByMap() {
		return requestByMap;
	}

	public void setRequestByMap(Map<String, String> requestByMap) {
		this.requestByMap = requestByMap;
	}

	public Map<String, String> getRequestToMap() {
		return requestToMap;
	}

	public void setRequestToMap(Map<String, String> requestToMap) {
		this.requestToMap = requestToMap;
	}

	public String getMaxDateValue() {
		return maxDateValue;
	}

	public void setMaxDateValue(String maxDateValue) {
		this.maxDateValue = maxDateValue;
	}

	public String getMinDateValue() {
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue) {
		this.minDateValue = minDateValue;
	}

	public String getReqBy() {
		return reqBy;
	}

	public void setReqBy(String reqBy) {
		this.reqBy = reqBy;
	}

	public String getReqTo() {
		return reqTo;
	}

	public void setReqTo(String reqTo) {
		this.reqTo = reqTo;
	}
	
}
