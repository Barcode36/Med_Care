package com.Over2Cloud.ctrl.feedback.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.beanUtil.PatientReportPojo;

public class TicketActionHelper 
{
	public List getFullDetailsForTicket(String ticketNo,SessionFactory connectionSpace)
	{
		List list=new ArrayList();
		StringBuffer query=new StringBuffer();
		query.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
		query.append("dept2.deptName as todept,emp.empName,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
		query.append("subcatg.addressingTime,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,");
		query.append("feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.location,feedback.feedback_remarks");
		query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
		query.append(" from feedback_status as feedback");
		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		query.append(" inner join department subdept1 on feedback.by_dept_subdept= subdept1.id");
		query.append(" inner join department subdept2 on feedback.to_dept_subdept= subdept2.id");
		query.append(" inner join department dept1 on subdept1.id= dept1.id");
		query.append(" inner join department dept2 on subdept2.id= dept2.id");
		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
		query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
		query.append(" where feedback.ticket_no like '"+ticketNo+"%' && moduleName='FM'");
		query.append(" ORDER BY feedback.ticket_no ");
		list=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		return list;
	}
	
	
	public List getFullDetailsForTicketDownload(String ticketNo,SessionFactory connectionSpace)
	{
		List list=new ArrayList();
		StringBuffer query=new StringBuffer();
		query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,catg.categoryName,dept1.deptName as bydept,");
		query.append("subcatg.subCategoryName,feedback.feed_brief,dept2.deptName as todept,emp.empName,");
		query.append("feedback.escalation_date,feedback.escalation_time,feedback.open_date,feedback.open_time,feedback.location,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,");
		query.append("feedback.action_by,feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark,emp1.empName as resolve_by,feedback.spare_used,feedback.feedback_remarks");
		query.append(" from feedback_status as feedback");
		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		query.append(" inner join department subdept1 on feedback.by_dept_subdept= subdept1.id");
		query.append(" inner join department subdept2 on feedback.to_dept_subdept= subdept2.id");
		query.append(" inner join department dept1 on subdept1.id= dept1.id");
		query.append(" inner join department dept2 on subdept2.id= dept2.id");
		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
		query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
		query.append(" where feedback.ticket_no like '"+ticketNo+"%' && moduleName='FM'");
		query.append(" ORDER BY feedback.ticket_no ");
		list=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		return list;
	}
	

	
	public List<PatientReportPojo> getFeedbackDetailsReportFeedback(SessionFactory connectionSpace,String startDate,String endDate,String type,String mode)
	{
		List<PatientReportPojo> feedList=new ArrayList<PatientReportPojo>();
		
		StringBuilder builder=new StringBuilder("select fStat.via_from,fStat.ticket_no,fStat.open_date");
		builder.append(",fStat.open_time,fStat.feed_by,fStat.location,fStat.patMobNo,");
		builder.append("emp.empName,fStat.feed_registerby,fStat.status,fStat.resolve_date");
		builder.append(",fStat.resolve_time,fStat.resolve_remark,fStat.spare_used,fStat.resolve_by from feedback_status as fStat");
		
		builder.append(" inner join employee_basic emp on fStat.allot_to= emp.id ");
		
		builder.append(" where fStat.moduleName='FM'");
		
		
		if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
		{
			builder.append(" && fStat.open_date between '"+DateUtil.convertDateToUSFormat(startDate)+"' and '"+DateUtil.convertDateToUSFormat(endDate)+"' ");
		}
		
		if(mode!=null && !mode.equalsIgnoreCase("-1"))
		{
			builder.append(" && fStat.via_from='"+mode+"'");
		}
		System.out.println("builder is as "+builder);
		List data=new createTable().executeAllSelectQuery(builder.toString(), connectionSpace);
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();) 
			{
				PatientReportPojo pojo=new PatientReportPojo();
				Object[] object = (Object[]) iterator.next();
				if(object!=null)
				{
					if(object[0]!=null)
					{
						pojo.setMode(object[0].toString());
					}
					
					if(object[1]!=null)
					{
						pojo.setTicketNo(object[1].toString());
					}
					
					if(object[2]!=null)
					{
						pojo.setOpenDate(DateUtil.convertDateToIndianFormat(object[2].toString()));
					}
					
					if(object[3]!=null)
					{
						pojo.setOpenTime(object[3].toString().substring(0,5));
					}
					
					if(object[4]!=null)
					{
						pojo.setPatientName(DateUtil.makeTitle(object[4].toString()));
					}
					
					if(object[5]!=null)
					{
						pojo.setRoomNo(object[5].toString());
					}
					
					if(object[6]!=null)
					{
						pojo.setPatMobNo(object[6].toString());
					}
					
					if(object[7]!=null)
					{
						pojo.setAllotTo(object[7].toString());
					}
					
					if(object[8]!=null)
					{
						pojo.setFeedRegisterBy(DateUtil.makeTitle(object[8].toString()));
					}
					
					if(object[9]!=null)
					{
						pojo.setStatus(object[9].toString());
					}
					
					if(object[10]!=null)
					{
						pojo.setResolve_date(DateUtil.convertDateToIndianFormat(object[10].toString()));
					}
					
					if(object[11]!=null)
					{
						pojo.setResolve_time(object[11].toString().substring(0,5));
					}
					
					if(object[12]!=null)
					{
						pojo.setRca(object[12].toString());
					}
					
					if(object[13]!=null)
					{
						pojo.setCapa(object[13].toString());
					}
					
					if(object[14]!=null)
					{
						pojo.setResolveBy(object[14].toString());
					}
					
					feedList.add(pojo);
				}
			}
		}
		return feedList;
	}
	
	
	public List<PatientReportPojo> getFeedbackDetailsReport(SessionFactory connectionSpace,String startDate,String endDate,String type,String smsStat)
	{
		List<PatientReportPojo> patList=new ArrayList<PatientReportPojo>();
		try
		{
			StringBuffer query=new StringBuffer("select cr_number,patient_name,visit_type,admission_time,station,mobile_no,email,admit_consultant,insert_date,smsFlag from patientinfo where id!=0");
			if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
			{
				query.append(" && insert_date between '"+DateUtil.convertDateToUSFormat(startDate)+"' and '"+DateUtil.convertDateToUSFormat(endDate)+"' ");
			}
			
			if(type!=null && !type.equalsIgnoreCase("")  && !type.equalsIgnoreCase("-1"))
			{
				query.append(" && patient_type='"+type+"'");
			}
			
			if(smsStat!=null && !smsStat.equalsIgnoreCase("")  && !smsStat.equalsIgnoreCase("-1"))
			{
				if(smsStat.equalsIgnoreCase("Send"))
				{
					query.append(" && smsFlag='3'");
				}
				else if(smsStat.equalsIgnoreCase("Not Send"))
				{
					query.append(" && smsFlag='0'");
				}
			}
			System.out.println("SMS Report >>>"+query);
			List data=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			System.out.println("Data ki Size>>>"+data.size());
			if(data!=null)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if(object!=null)
					{
						System.out.println("Mob No is as >>>>>"+object[5]);
						PatientReportPojo pojo=null;
						if(object[5]!=null)
						{
							pojo=getPatientRevertReceivedDetails(object[5].toString(),connectionSpace);
							
							if(object[0]!=null)
							{
								pojo.setPatientId(object[0].toString());
							}
							
							if(object[1]!=null)
							{
								pojo.setPatientName(object[1].toString());
							}
							
							if(object[2]!=null)
							{
								pojo.setPurpVisit(object[2].toString());
							}
							
							if(object[3]!=null)
							{
								if(object[3].toString().length()>12)
	                    		{
									pojo.setVisitDateTime(object[3].toString().substring(8, 10)+"-"+object[3].toString().substring(5, 7)+"-"+object[3].toString().substring(0, 4)+" "+object[3].toString().substring(object[3].toString().indexOf(" "), object[3].toString().length()));
	                    		}
	                    		else
	                    		{
	                    			pojo.setVisitDateTime(DateUtil.convertDateToIndianFormat(object[3].toString()));
	                    		}
								
							}
							
							if(object[4]!=null)
							{
								if(object[4].toString().length()>12)
	                    		{
									pojo.setDischargeDateTime(object[4].toString().substring(8, 10)+"-"+object[4].toString().substring(5, 7)+"-"+object[4].toString().substring(0, 4)+" "+object[4].toString().substring(object[4].toString().indexOf(" "), object[4].toString().length()));
	                    		}
	                    		else
	                    		{
	                    			pojo.setDischargeDateTime(DateUtil.convertDateToIndianFormat(object[4].toString()));
	                    		}
							}
							
							if(object[5]!=null)
							{
								pojo.setRoomNo(object[5].toString());
							}
							
							if(object[6]!=null)
							{
								pojo.setMobNo(object[6].toString());
							}
							
							if(object[7]!=null)
							{
								pojo.setEmailId(object[7].toString());
							}
							
							if(object[8]!=null)
							{
								pojo.setDocName(object[8].toString());
							}
							
							if(object[9]!=null)
							{
								if(object[9].toString()!=null && object[9].toString().equalsIgnoreCase("3"))
								{
									pojo.setSmsStatus("Send");
								}
								else
								{
									pojo.setSmsStatus("Un-Send");
								}
							}
							
							patList.add(pojo);
							System.out.println("Added...");
						}
						
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return patList;
	}
	
	public List getFullDetailsForTicketSearching(String ticketNo,SessionFactory connectionSpace)
	{
		List list=new ArrayList();
		try
		{
			StringBuffer query=new StringBuffer();
			query.append("select dept1.deptName as bydept,feed_brief,level,feedback.status,emp.empName" +
					" ,feedback.resolve_date,feedback.resolve_remark,feedback.spare_used,subcatg.subCategoryName " +
					" from feedback_status as feedback" +
					" inner join department dept1 on feedback.to_dept_subdept= dept1.id " +
					" inner join employee_basic emp on feedback.allot_to= emp.id " +
					" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id " +
					"  inner join feedback_category catg on subcatg.categoryName = catg.id ");
			query.append(" where feedback.ticket_no like '"+ticketNo+"%' && feedback.moduleName='FM'");
			
			list=new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public PatientReportPojo getAdminActionDetails(String mobNo,SessionFactory connectionSpace)
	{
		PatientReportPojo actionDetails=new PatientReportPojo();
		try
		{
			
			StringBuffer buffer=new StringBuffer("select tickt.feedTicketNo from feedback_ticket as tickt where " +
					"tickt.feed_data_id=(select id from feedbackdata where mobNo='"+mobNo+"' order by id" +
							" desc limit 1)" +
							"order by tickt.id desc limit 1");
			
			List actionDataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
			if(actionDataList!=null && actionDataList.size()>0 && actionDataList.get(0)!=null)
			{
				String ticketNo=actionDataList.get(0).toString();
				if(ticketNo!=null)
				{
					String feedToDept="",problem="",level="",status="",allotedTo="",resDate="",rca="",capa="",subCat="";
					List ticketData=getFullDetailsForTicketSearching(ticketNo,connectionSpace);
					if(ticketData!=null && ticketData.size()>0)
					{
						for (Iterator iterator = ticketData.iterator(); iterator .hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
						
							if(object[0]!=null)
							{
								feedToDept=feedToDept+", "+object[0].toString();								
							}
							
							if(object[1]!=null)
							{
								problem=problem+", "+object[1].toString();
							}
							
							if(object[2]!=null)
							{
								level=level+", "+object[2].toString();
							}
							
							if(object[3]!=null)
							{
								status=status+", "+object[3].toString();
							}
							
							if(object[4]!=null)
							{
								allotedTo=allotedTo+", "+object[4].toString();
							}
							
							if(object[5]!=null)
							{
								resDate=resDate+", "+DateUtil.convertDateToIndianFormat(object[5].toString());
							}
							
							if(object[6]!=null)
							{
								capa=capa+", "+object[6].toString();
							}
							
							if(object[7]!=null)
							{
								rca=rca+", "+object[7].toString();
							}
							
							if(object[8]!=null)
							{
								subCat=subCat+", "+object[8].toString();
							}
						}
					}
					
					if(feedToDept!=null && !feedToDept.equalsIgnoreCase(""))
					{
						actionDetails.setFeedback_to_dept(feedToDept);
					}
					
					if(problem!=null && !problem.equalsIgnoreCase(""))
					{
						actionDetails.setProblem(problem);
					}
					
					if(level!=null && !level.equalsIgnoreCase(""))
					{
						actionDetails.setLevel(level);
					}
					
					if(status!=null && !status.equalsIgnoreCase(""))
					{
						actionDetails.setStatus(status);
					}
					
					if(allotedTo!=null && !allotedTo.equalsIgnoreCase(""))
					{
						actionDetails.setAllotTo(allotedTo);
					}

					if(resDate!=null && !resDate.equalsIgnoreCase(""))
					{
						actionDetails.setResolve_date(resDate);
					}
					
					if(rca!=null && !rca.equalsIgnoreCase(""))
					{
						actionDetails.setRca(rca);
					}
					
					if(capa!=null && !capa.equalsIgnoreCase(""))
					{
						actionDetails.setCapa(capa);
					}
					
					if(subCat!=null && !subCat.equalsIgnoreCase(""))
					{
						actionDetails.setSubCategoryName(subCat);
					}
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return actionDetails;
	}
	
	public String getPatientSatisfaction(String keyWord,SessionFactory connectionSpace)
	{
		String satis=null;
		List keyTypList=new createTable().executeAllSelectQuery("select distinct type from feedback_sms_config  where keyword='"+keyWord+"'", connectionSpace);
		if(keyTypList!=null && keyTypList.size()>0 )
		{
			if(keyTypList.get(0).toString().equalsIgnoreCase("1"))
			{
				satis="Negative";
			}
			else
			{
				satis="Positive";
			}
		}
		else
		{
			satis="NA";
		}
		return satis;
	}
	
	
	
	public PatientReportPojo getPatientRevertReceivedDetails(String mobNo,SessionFactory connectionSpace)
	{
		PatientReportPojo revertInfo=new PatientReportPojo();
		
		try
		{
			List keyWordList=new createTable().executeAllSelectQuery("select distinct keyword,date,time from t2mkeyword where mobNo='"+mobNo+"'", connectionSpace);
			
			if(keyWordList!=null && keyWordList.size()>0)
			{
				revertInfo.setRevertStatus("Reverted");
				for (Iterator iterator2 = keyWordList.iterator(); iterator2 .hasNext();)
				{
					Object[] object2 = (Object[]) iterator2.next();
					if(object2!=null)
					{
						if(object2[0]!=null)
						{
							revertInfo.setSatisfaction(getPatientSatisfaction(object2[0].toString(),connectionSpace));
						}
						if(object2[1]!=null)
						{
							revertInfo.setRevertDate(DateUtil.convertDateToIndianFormat(object2[1].toString()));
						}
						
						if(object2[2]!=null)
						{
							revertInfo.setRevertTime(object2[2].toString());
						}
					}
				}
				
				if(revertInfo.getSatisfaction()!=null && revertInfo.getSatisfaction().equalsIgnoreCase("Negative"))
				{
					PatientReportPojo pojo=getAdminActionDetails(mobNo,connectionSpace);
					if(pojo.getFeedback_to_dept()!=null)
					{
						revertInfo.setFeedback_to_dept(pojo.getFeedback_to_dept().substring(1, pojo.getFeedback_to_dept().length()));
					}
					
					if(pojo.getProblem()!=null)
					{
						revertInfo.setProblem(pojo.getProblem().substring(1, pojo.getProblem().length()));
					}
					
					if(pojo.getLevel()!=null)
					{
						revertInfo.setLevel(pojo.getLevel().substring(1, pojo.getLevel().length()));
					}
					
					if(pojo.getStatus()!=null)
					{
						revertInfo.setStatus(pojo.getStatus().substring(1, pojo.getStatus().length()));
					}
					
					if(pojo.getAllotTo()!=null)
					{
						revertInfo.setAllotTo(pojo.getAllotTo().substring(1, pojo.getAllotTo().length()));
					}
					
					if(pojo.getResolve_date()!=null)
					{
						revertInfo.setResolve_date(pojo.getResolve_date().substring(1, pojo.getResolve_date().length()));
					}
					
					if(pojo.getRca()!=null)
					{
						revertInfo.setRca(pojo.getRca().substring(1, pojo.getRca().length()));
					}
					
					if(pojo.getCapa()!=null)
					{
						revertInfo.setCapa(pojo.getCapa().substring(1, pojo.getCapa().length()));
					}
					
					if(pojo.getSubCategoryName()!=null)
					{
						revertInfo.setSubCategoryName(pojo.getSubCategoryName().substring(1, pojo.getSubCategoryName().length()));
					}
				}
			}
			else
			{
				revertInfo.setRevertStatus("Not Reverted");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return revertInfo;
	}
	
}