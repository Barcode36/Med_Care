package com.Over2Cloud.ctrl.SeekApproval;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class SeekApprovalHelper
{
	public List getUpperLevelContact(String complaintId, String moduleName, SessionFactory connection,CommonOperInterface cbt)
	{
		Map<String, String> contactDetails = new LinkedHashMap<String, String>();
		List dataList = null;
		System.out.println("Module Name "+moduleName);
		if(moduleName!=null && !moduleName.equals("") && complaintId!=null && !complaintId.equals(""))
		{
			StringBuilder query = new StringBuilder();
			if(moduleName.equalsIgnoreCase("ASTM"))
			{
				
					query.append("SELECT contact.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN compliance_contacts AS contact ON contact.emp_id=emp.id");
					query.append(" INNER JOIN outlet_to_contact_mapping AS outlet2cont ON outlet2cont.contactid=contact.id");
					query.append(" INNER JOIN asset_complaint_status AS complaint ON  complaint.location=outlet2cont.outletid ");
					query.append(" WHERE contact.level>1 AND outlet2cont.sendflag='SA' AND complaint.id="+complaintId+" ORDER BY contact.level LIMIT 0,1");
					dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			}
			else if(moduleName.equalsIgnoreCase("HDM"))	
			{
				query.append("SELECT contact.forDept_id,contact.level  ");
				query.append(" FROM compliance_contacts AS contact  ");
				query.append(" INNER JOIN feedback_status AS compliant ON contact.forDept_id = compliant.to_dept_subdept");
				query.append(" AND contact.emp_id = compliant.allot_to");
				query.append(" WHERE contact.moduleName = 'HDM' AND compliant.id="+complaintId);
				System.out.println("query.toString() "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				query.setLength(0);
				if(dataList!=null && dataList.size()>0)
				{
					String forDeptId=null,level=null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							forDeptId = object[0].toString();
							level = object[1].toString();
						}
					}
					dataList.clear();
					if(forDeptId!=null && level!=null)
					{
						query.append("SELECT contact.id,emp.empName FROM employee_basic AS emp");
						query.append(" INNER JOIN compliance_contacts AS contact ON contact.emp_id=emp.id");
						query.append(" WHERE contact.forDept_id="+forDeptId+" AND contact.level>"+level);
						query.append(" AND contact.work_status!=1 AND contact.moduleName = '"+moduleName+"' ORDER BY emp.empName");
						System.out.println("##### "+query.toString());
						dataList = cbt.executeAllSelectQuery(query.toString(), connection);
					}
				}
			}
		}
				
			/*if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						contactDetails.put(object[0].toString(), object[1].toString());
					}
				}
			}*/
		return dataList;
	}
	
	public boolean createTable4SeekApproval(SessionFactory connection,CommonOperInterface cbt)
	{
		boolean status = false;
		List<TableColumes> tableColume = new ArrayList<TableColumes>();
		try
		{
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("complaint_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("module_name");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("approved_by");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("request_on");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("request_at");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("doc_for_approval");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("reason");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("status");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("user");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("approved_on");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("approved_at");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("doc_after_approval");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("approved_comment");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			status = cbt.createTable22("seek_approval_detail", tableColume, connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
		
	}
	public boolean saveData4Approval(Map<String, String> dataMap,SessionFactory connection,CommonOperInterface cbt)
	{
		boolean status = false; 
		try
		{
			if (dataMap!=null && dataMap.size()>0)
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				for(Map.Entry<String, String> entry : dataMap.entrySet())
				{
					ob = new InsertDataTable();
					ob.setColName(entry.getKey());
					ob.setDataName(entry.getValue());
					insertData.add(ob);
				}
				if(insertData!=null && insertData.size()>0)
				{
					status=cbt.insertIntoTable("seek_approval_detail", insertData, connection);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return status;
	}
	
	public List fetchDataForAlert(String complainId,SessionFactory connection,CommonOperInterface cbt,String moduleName)
	{
		List dataList = null;
		StringBuilder query = new StringBuilder();
		try
		{
			if(moduleName!=null)
			{
				if(moduleName.equalsIgnoreCase("ASTM"))
				{
					query.append("SELECT complaint.ticket_no,complaint.feed_by_mobno,complaint.feed_by_emailid,complaint.feed_brief,emp.empName,complaint.feed_by,emp.mobOne");
					query.append(" FROM asset_complaint_status AS complaint");
					query.append(" INNER JOIN employee_basic AS emp ON emp.id = complaint.allot_to");
					query.append(" WHERE complaint.id = "+complainId);
					dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				}
				else if(moduleName.equalsIgnoreCase("HDM"))
				{
					query.append("SELECT complaint.ticket_no,complaint.feed_by_mobno,complaint.feed_by_emailid,complaint.feed_brief,emp.empName,complaint.feed_by,emp.mobOne");
					query.append(" FROM feedback_status AS complaint");
					query.append(" INNER JOIN employee_basic AS emp ON emp.id = complaint.allot_to");
					query.append(" WHERE complaint.id = "+complainId);
					dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return dataList;
	}
	public List fetchDataForAlert(String contactId,SessionFactory connection,CommonOperInterface cbt)
	{
		List dataList = null;
		StringBuilder query = new StringBuilder();
		try
		{
			query.append("SELECT emp.empName,emp.mobOne,emp.emailIdOne FROM employee_basic AS emp");
			query.append(" INNER JOIN compliance_contacts AS contact ON contact.emp_id = emp.id");
			query.append(" WHERE contact.id="+contactId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public List getSeekApprovalData(SessionFactory connection,CommonOperInterface cbt,String moduleName,String approvalStatus,String actionBy,String empOrContId,String maxDateValue,String minDateValue,String reqBy,String reqTo)
	{
		List dataList = null;
		StringBuilder query = new StringBuilder();
		try
		{
			if(moduleName.equalsIgnoreCase("ASTM"))
			{
				query.append("SELECT seek.id,outlet.associateName,ast.assetname,ast.location,feedback.ticket_no,emp1.empname AS allotTo,seek.reason,");
				query.append("seek.request_on,seek.request_at,subcatg.subCategoryName,feedback.feed_brief,");
				query.append("feedback.open_date, feedback.open_time,seek.doc_for_approval,");
				query.append("seek.approved_on, seek.approved_at,emp2.empname AS approvedBy,seek.approved_comment,seek.doc_after_approval,seek.status AS seekstatus");
				query.append(" FROM seek_approval_detail AS seek");
				query.append(" INNER JOIN asset_complaint_status AS feedback On feedback.id = seek.complaint_id");
				query.append(" INNER JOIN asset_detail AS ast ON ast.id = feedback.asset_id");
				query.append(" INNER JOIN asset_allotment_detail AS allot ON allot.assetid = ast.id");
				query.append(" INNER JOIN  associate_basic_data AS outlet ON outlet.id = outletid");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
				query.append(" INNER JOIN employee_basic AS emp1 ON emp1.id = feedback.allot_to");
				query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by");
				query.append(" INNER JOIN employee_basic AS emp2 ON emp2.id = contact.emp_id");
				query.append(" WHERE seek.module_name = '"+moduleName+"' AND seek.status='"+approvalStatus+"'");
				if(actionBy.equalsIgnoreCase("selfRequest"))
				{
					query.append(" AND feedback.allot_to="+empOrContId);
				}
				else if(actionBy.equalsIgnoreCase("selfApprove"))
				{
					query.append(" AND seek.approved_by IN("+empOrContId+")");
				}
				System.out.println("Query String "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			}
			if(moduleName.equalsIgnoreCase("HDM"))
			{
				query.append("SELECT seek.id,dept.deptName,feedback.feed_by,feedback.feed_by_mobno,feedback.ticket_no,emp1.empname AS allotTo,seek.reason,");
				query.append("seek.request_on,seek.request_at,catg.categoryName,feedback.feed_brief,");
				query.append("feedback.open_date, feedback.open_time,seek.doc_for_approval,");
				query.append("seek.approved_on, seek.approved_at,emp2.empname AS approvedBy,seek.approved_comment,seek.doc_after_approval,seek.status AS seekstatus");
				query.append(" FROM seek_approval_detail AS seek");
				query.append(" INNER JOIN feedback_status AS feedback On feedback.id = seek.complaint_id");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = feedback.to_dept_subdept");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
				query.append(" INNER JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
				query.append(" INNER JOIN employee_basic AS emp1 ON emp1.id = feedback.allot_to");
				query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by");
				query.append(" INNER JOIN employee_basic AS emp2 ON emp2.id = contact.emp_id");
				query.append(" WHERE seek.module_name = '"+moduleName+"'");
				if(approvalStatus!=null && !approvalStatus.equalsIgnoreCase("all"))
				{
					query.append(" AND seek.status='"+approvalStatus+"'");
				}
				if(actionBy.equalsIgnoreCase("selfRequest"))
				{
					query.append(" AND feedback.allot_to="+empOrContId);
				}
				else if(actionBy.equalsIgnoreCase("selfApprove"))
				{
					query.append(" AND seek.approved_by IN("+empOrContId+")");
				}
				if(minDateValue!=null && maxDateValue!=null)
				{
					String str[] = minDateValue.split("-");
					if(str[0].length()<3)
					{
						minDateValue = DateUtil.convertDateToUSFormat(minDateValue);
						maxDateValue = DateUtil.convertDateToUSFormat(maxDateValue);
					}
					query.append(" AND seek.request_on BETWEEN '"+minDateValue+"' AND '"+maxDateValue+"'");
				}
				if(reqBy!=null && !reqBy.equalsIgnoreCase("all") && !reqBy.equalsIgnoreCase("No Data"))
				{
					query.append(" AND feedback.allot_to="+reqBy);
				}
				if(reqTo!=null && !reqTo.equalsIgnoreCase("all") && !reqTo.equalsIgnoreCase("No Data"))
				{
					query.append(" AND seek.approved_by="+reqTo);
				}
				
				
				System.out.println("Query String "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public List getSeekApprovalDataCount(SessionFactory connection,CommonOperInterface cbt,String moduleName,String approvalStatus,String actionBy,String empOrContId,String maxDateValue,String minDateValue,String reqBy,String reqTo)
	{
		List dataList = null;
		StringBuilder query = new StringBuilder();
		try
		{
			if(moduleName.equalsIgnoreCase("ASTM"))
			{
				query.append("SELECT count(distinct seek.id),seek.status");
				query.append(" FROM seek_approval_detail AS seek");
				query.append(" INNER JOIN asset_complaint_status AS feedback On feedback.id = seek.complaint_id");
				query.append(" INNER JOIN asset_detail AS ast ON ast.id = feedback.asset_id");
				query.append(" INNER JOIN asset_allotment_detail AS allot ON allot.assetid = ast.id");
				query.append(" INNER JOIN  associate_basic_data AS outlet ON outlet.id = outletid");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
				query.append(" INNER JOIN employee_basic AS emp1 ON emp1.id = feedback.allot_to");
				query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by");
				query.append(" INNER JOIN employee_basic AS emp2 ON emp2.id = contact.emp_id");
				query.append(" WHERE seek.module_name = '"+moduleName+"' AND seek.status='"+approvalStatus+"'");
				if(actionBy.equalsIgnoreCase("selfRequest"))
				{
					query.append(" AND feedback.allot_to="+empOrContId);
				}
				else if(actionBy.equalsIgnoreCase("selfApprove"))
				{
					query.append(" AND seek.approved_by IN("+empOrContId+")");
				}
				query.append(" GROUP BY seek.status ORDER BY seek.id DESC");
				System.out.println("Query String "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			}
			if(moduleName.equalsIgnoreCase("HDM"))
			{
				query.append("SELECT count(distinct seek.id),seek.status");
				query.append(" FROM seek_approval_detail AS seek");
				query.append(" INNER JOIN feedback_status AS feedback On feedback.id = seek.complaint_id");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = feedback.to_dept_subdept");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
				query.append(" INNER JOIN employee_basic AS emp1 ON emp1.id = feedback.allot_to");
				query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by");
				query.append(" INNER JOIN employee_basic AS emp2 ON emp2.id = contact.emp_id");
				query.append(" WHERE seek.module_name = '"+moduleName+"'");
				if(approvalStatus!=null && !approvalStatus.equalsIgnoreCase("all"))
				{
					query.append(" AND seek.status='"+approvalStatus+"'");
				}
				if(actionBy.equalsIgnoreCase("selfRequest"))
				{
					query.append(" AND feedback.allot_to="+empOrContId);
				}
				else if(actionBy.equalsIgnoreCase("selfApprove"))
				{
					query.append(" AND seek.approved_by IN("+empOrContId+")");
				}
				if(minDateValue!=null && maxDateValue!=null)
				{
					String str[] = minDateValue.split("-");
					if(str[0].length()<3)
					{
						minDateValue = DateUtil.convertDateToUSFormat(minDateValue);
						maxDateValue = DateUtil.convertDateToUSFormat(maxDateValue);
					}
					query.append(" AND seek.request_on BETWEEN '"+minDateValue+"' AND '"+maxDateValue+"'");
				}
				if(reqBy!=null && !reqBy.equalsIgnoreCase("all") && !reqBy.equalsIgnoreCase("No Data"))
				{
					query.append(" AND feedback.allot_to="+reqBy);
				}
				if(reqTo!=null && !reqTo.equalsIgnoreCase("all") && !reqTo.equalsIgnoreCase("No Data"))
				{
					query.append(" AND seek.approved_by="+reqTo);
				}
				
				query.append(" GROUP BY seek.status ORDER BY seek.id DESC");
				System.out.println("Query String "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	
	public String getAllContactIdByEmpId(SessionFactory connection,CommonOperInterface cbt, String moduleName,String empId)
	{
		StringBuilder contIds = new StringBuilder();
		String query = "SELECT id FROM compliance_contacts WHERE moduleName='"+moduleName+"' AND work_status!=1 AND emp_id = "+empId;
		System.out.println(query+" @@@@ ");
		List dataList = cbt.executeAllSelectQuery(query, connection);
		if(dataList!=null && dataList.size()>0)
		{
			for(int i=0;i<dataList.size();i++)
				contIds.append(dataList.get(i).toString()+",");
		}
		contIds.append("0");
		return contIds.toString();
	}
	
	public List getCompliantIdBySeekId(SessionFactory connection,CommonOperInterface cbt, String seekId)
	{
		String query = "SELECT complaint_id,approved_by,reason,module_name FROM seek_approval_detail WHERE id="+seekId;
		System.out.println(query);
		List dataList = cbt.executeAllSelectQuery(query, connection);
		return dataList;
	}
	
	public List getActualDocPath(SessionFactory connection,CommonOperInterface cbt, String pkId, String columnName, String tableName)
	{
		String query = "SELECT "+columnName+" FROM "+tableName+" WHERE id="+pkId;
		System.out.println(query);
		List dataList = cbt.executeAllSelectQuery(query, connection);
		return dataList;
	}
	public Map<String, String> getEmpDetails(SessionFactory connection,CommonOperInterface cbt, String empId, String dataFetchFlag)
	{
		Map<String, String> dataMap =new LinkedHashMap<String, String>();
		try
		{
			StringBuilder query = new StringBuilder();
			
			if(dataFetchFlag.equals("upperLevel"))
			{
				query.append("SELECT DISTINCT contact.id ,emp.empName FROM feedback_status AS complaint"); 
				query.append(" INNER JOIN seek_approval_detail AS seek ON seek.complaint_id = complaint.id"); 
				query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by"); 
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id"); 
				query.append(" WHERE complaint.allot_to="+empId+" AND seek.module_name='HDM' ORDER BY emp.empName"); 
			}
			else if(dataFetchFlag.equals("lowerLevel"))
			{
				query.append("SELECT DISTINCT emp.id,emp.empName FROM employee_basic AS emp");
				query.append(" INNER JOIN feedback_status AS complaint ON complaint.allot_to = emp.id");
				query.append(" INNER JOIN seek_approval_detail AS seek ON seek.complaint_id = complaint.id");
				query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by");
				query.append(" WHERE contact.emp_id="+empId+" AND seek.module_name='HDM' ORDER BY emp.empName");
			}
			List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			if(dataList!=null && dataList.size()>0)
			{
				dataMap.put("all", "All");
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
						dataMap.put(object[0].toString(), object[1].toString());
				}
			}
			else
			{
				dataMap.put("No Data", "No Data");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataMap;
	}
	
}
