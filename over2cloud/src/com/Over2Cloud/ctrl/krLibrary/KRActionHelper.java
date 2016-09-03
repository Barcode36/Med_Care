package com.Over2Cloud.ctrl.krLibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

public class KRActionHelper 
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	
	public String getFileExtenstion(String contentType) {
		if (contentType.equals("image/pjpeg")) {
		return ".jpg";
		} else if (contentType.equals("image/jpeg")) {
		return ".jpg";
		} 
		else if (contentType.equals("image/bmp")) {
			return ".bmp";
			} 
		else if (contentType.equals("image/jpg")) {
			return ".jpg";
			} 
		else if (contentType.equals("image/gif")) {
		return ".gif";
		} else if (contentType.equals("image/png")) {
		return ".png";
		}
		else if (contentType.equals("text/css")) {
			return ".css";
			} 
		else if (contentType.equals("text/html")) {
			return ".html";
			} 
		else if (contentType.equals("text/plain")) {
			return ".txt";
			} 
		else if (contentType.equals("text/xml")) {
			return ".xml";
			} 
		else if (contentType.equals("video/mpeg")) {
			return ".mpeg";
			} 
		else if (contentType.equals("video/mp4")) {
			return ".mp4";
			} 
		else if (contentType.equals("video/ogg")) {
			return ".ogg";
			} 
		else if (contentType.equals("application/pdf")) {
			return ".pdf";
			} 
		else if (contentType.equals("application/zip")) {
			return ".zip";
			} 
		else if (contentType.equals("image/tiff")) {
			return ".tiff";
			} 
		else if (contentType.equals("application/vnd.oasis.opendocument.text")) {
			return ".txt";
			} 
		else if (contentType.equals("application/vnd.ms-excel")) {
			return ".xls";
			} 
		else if (contentType.equals("application/vnd.ms-powerpoint")) {
			return ".ppt";
			} 
		else if (contentType.equals("application/msword")) {
			return ".docx";
			} 
		
		else if (contentType.equals("audio/basic")) {
			return ".mp3";
			} 
		else if (contentType.equals("audio/mpeg")) {
			return ".mpeg";
			} 
		else if (contentType.equals("audio/x-ms-wma")) {
			return ".wma";
			} 
		else if (contentType.equals("audio/x-wav")) {
			return ".wav";
			} 
		else if (contentType.equals("audio/mp3")) {
			return ".mp3";
			} 
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			return ".docx";
			} 
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
			return ".pptx";
			}
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return ".xlsx";
			} 
		else if (contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
			return ".pptx";
			} 
		else if (contentType.equals("video/x-msvideo")) {
			return ".avi";
			} 
		else if (contentType.equals("application/x-msclip")) {
			return ".clp";
			} 
		else if (contentType.equals("text/css")) {
			return ".css";
			} 
		else if (contentType.equals("application/x-msdownload")) {
			return ".dll";
			} 
		else if (contentType.equals("application/x-dvi")) {
			return ".dvi";
			}
		else if (contentType.equals("application/x-gtar")) {
			return ".gtar";
			}
		else if (contentType.equals("image/x-icon")) {
			return ".ico";
			}
		else if (contentType.equals("image/pipeg")) {
			return ".jfif";
			}
		else if (contentType.equals("application/x-javascript")) {
			return ".js";
			}
		else if (contentType.equals("audio/mid")) {
			return ".mid";
			}
		else if (contentType.equals("video/quicktime")) {
			return ".mov";
			}
		else if (contentType.equals("application/pdf")) {
			return ".pdf";
			}
		else if (contentType.equals("video/quicktime")) {
			return ".qt";
			}
		else if (contentType.equals("audio/x-wav")) {
			return ".wav";
			}
		else if (contentType.equals("application/zip")) {
			return ".zip";
			}
		else {
		return null;
		}
		}

	@SuppressWarnings("rawtypes")
	public List getGroupName(SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select id, groupName from kr_group_data WHERE status='0'", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getAssignedDepartment(SessionFactory connectionSpace,String depid) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.deptName FROM department as dept  ");
			query.append("INNER JOIN compliance_contacts as cc ON cc.fromDept_id=dept.id ");
			query.append(" WHERE cc.forDept_id='"+depid+"' AND cc.moduleName='KR' AND dept.flag='Active' GROUP BY dept.deptName ORDER BY dept.deptName");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
	public String[] getEmpDetailsByUserName(String moduleName,String userName,SessionFactory connectionSpace)
	{
		String[] values =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			
			StringBuilder query=new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"+moduleName+"' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id AND emp.flag=0 AND contact.work_status=0 ");
			//System.out.println("Common Helper Class "+query.toString());
			List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				values=new String[7];
				Object[] object=(Object[])dataList.get(0);
				values[0]=getValueWithNullCheck(object[0]);
				values[1]=getValueWithNullCheck(object[1]);
				values[2]=getValueWithNullCheck(object[2]);
				values[3]=getValueWithNullCheck(object[3]);
				values[4]=getValueWithNullCheck(object[4]);
				values[5]=getValueWithNullCheck(object[5]);
				values[6]=getValueWithNullCheck(object[6]);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	@SuppressWarnings("rawtypes")
	public List getCreatedByDetails(SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select userName from kr_upload_data ORDER BY userName", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("rawtypes")
	public List getDeptName(SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select id, deptName from department ORDER BY deptName", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getContactType(SessionFactory connectionSpace)
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			list = coi.executeAllSelectQuery("select id,groupName from groupinfo WHERE regLevel='1'", connectionSpace);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getContactFromDept(String toDept, String fromDeptId,
			SessionFactory connectionSpace)
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT cc.id,emp.empName FROM compliance_contacts as cc  ");
			query.append("INNER JOIN department as dept ON cc.forDept_id=dept.id ");
			query.append("INNER JOIN employee_basic as emp ON cc.emp_id=emp.id ");
			query.append(" WHERE cc.forDept_id='"+toDept+"' AND cc.fromDept_id IN ("+fromDeptId+") AND cc.moduleName='KR' AND emp.flag=0 AND cc.work_status=0 GROUP BY emp.empName ORDER BY emp.empName");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public Object getMultipleDeptName(String empId,
			SessionFactory connectionSpace)
	{
		String deptName=null;
		StringBuilder dept=new StringBuilder();
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.deptName FROM department AS dept   ");
			query.append("INNER JOIN employee_basic AS emp ON emp.deptname =dept.id ");
			query.append("INNER JOIN compliance_contacts AS cc ON cc.emp_id =emp.id ");
			query.append(" WHERE cc.id IN("+empId+") GROUP BY dept.deptName");
			//System.out.println("QQQQQQQQQ   "+query.toString());
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
				
			if (list!=null && list.size()>0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						dept.append(object.toString()+", ");
					}
				}
				deptName=dept.toString().substring(0, dept.toString().length()-2);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return deptName;
	}

	@SuppressWarnings("rawtypes")
	public Object getMultipleEmpName(String empId,
			SessionFactory connectionSpace)
	{
		String employeeName=null;
		StringBuilder emp=new StringBuilder();
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT emp.empName FROM department AS dept   ");
			query.append("INNER JOIN employee_basic AS emp ON emp.deptname =dept.id ");
			query.append("INNER JOIN compliance_contacts AS cc ON cc.emp_id =emp.id ");
			query.append(" WHERE cc.id IN("+empId+") ");
			//System.out.println("QQQQQQQQQ   "+query.toString());
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
				
			if (list!=null && list.size()>0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						emp.append(object.toString()+", ");
					}
				}
				employeeName=emp.toString().substring(0, emp.toString().length()-2);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return employeeName;
	}
	@SuppressWarnings("rawtypes")
	public String  contactIdLoggenedMulti(String empId,String moduleName,SessionFactory connectionSpace) 
	{
		String employeeName=null;
		StringBuilder emp=new StringBuilder();
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT cc.id FROM compliance_contacts   ");
			query.append("AS cc LEFT JOIN employee_basic As emp ON emp.id=cc.emp_id ");
			query.append(" WHERE cc.emp_id ="+empId+" AND moduleName='"+moduleName+"'");
			System.out.println("QQQQQQQQQ   "+query.toString());
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
				
			if (list!=null && list.size()>0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						emp.append(object.toString()+", ");
					}
				}
				employeeName=emp.toString().substring(0, emp.toString().length()-2);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		return employeeName;
	}
	@SuppressWarnings("rawtypes")
	public List getGroupAssignedDepartment(SessionFactory connectionSpace,String forDept) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.deptName FROM department as dept  ");
			query.append("INNER JOIN compliance_contacts as cc ON cc.fromDept_id=dept.id ");
			query.append("INNER JOIN kr_group_data as krgroup ON krgroup.dept=dept.id ");
			query.append(" WHERE cc.forDept_id='"+forDept+"' AND cc.moduleName='KR' GROUP BY dept.deptName ORDER BY dept.deptName");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List getLibraryAssignedDepartment(SessionFactory connectionSpace,String forDept) {
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.deptName FROM department as dept  ");
			query.append("INNER JOIN compliance_contacts as cc ON cc.fromDept_id=dept.id ");
			query.append("INNER JOIN kr_group_data as krgroup ON krgroup.dept=dept.id ");
			query.append("INNER JOIN kr_sub_group_data as krsubgroup ON krsubgroup.groupName=krgroup.id ");
			query.append("INNER JOIN kr_upload_data as upload ON upload.subGroupName=krsubgroup.id ");
			query.append(" WHERE cc.forDept_id='"+forDept+"' AND cc.moduleName='KR' GROUP BY dept.deptName ORDER BY dept.deptName");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List getSharedAssignedDepartment(SessionFactory connectionSpace,
			String fromDept) {
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT dept.id,dept.deptName FROM department as dept  ");
			query.append("INNER JOIN compliance_contacts as cc ON cc.fromDept_id=dept.id ");
			query.append("INNER JOIN kr_group_data as krgroup ON krgroup.dept=dept.id ");
			query.append("INNER JOIN kr_sub_group_data as krsubgroup ON krsubgroup.groupName=krgroup.id ");
			query.append("INNER JOIN kr_upload_data as upload ON upload.subGroupName=krsubgroup.id ");
			query.append("INNER JOIN kr_share_data as share ON share.doc_name=upload.id ");
			query.append(" WHERE cc.forDept_id='"+fromDept+"' AND cc.moduleName='KR' GROUP BY dept.deptName ORDER BY dept.deptName");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List fetchAllKRDetails(int maxId,SessionFactory connectionSpace) 
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.deptName,krGroup.groupName  ");
			query.append(",subGroup.subGroupName,krshare.accessType,krshare.actionReq,krshare.dueShareDate,krshare.dueDate,krshare.frequency,krshare.ratingRequired,krshare.specificAction,krshare.repeatUpTo FROM kr_share_data AS krshare ");
			query.append("LEFT JOIN  kr_upload_data AS upload ON krshare.doc_name=upload.id ");
			query.append("LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id ");
			query.append("LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
			query.append("LEFT JOIN department AS dept ON dept.id=krGroup.dept ");
			query.append(" WHERE krshare.id='"+maxId+"'");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	
	@SuppressWarnings("rawtypes")
	public String fetchCountValue(String tableName, SessionFactory connectionSpace) 
	{
		String count=null;
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT COUNT(*) FROM ");
			query.append(tableName);
			if (tableName!=null && tableName.equalsIgnoreCase("kr_group_data")) 
			{
				query.append(" WHERE status='0'");
			}
			else if(tableName!=null && tableName.equalsIgnoreCase("kr_sub_group_data"))
			{
				query.append(" WHERE flag='0'");
			}
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list!=null && !list.isEmpty()) 
			{
				count=list.get(0).toString();
			}		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	public String fetchDocumentHistory(String shareId,String dataFor, SessionFactory connectionSpace) 
	{
		String shareID=null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			if (dataFor!=null && dataFor.equalsIgnoreCase("download")) 
			{
				query.append("SELECT COUNT(*) FROM kr_share_download_history AS download LEFT JOIN kr_share_data AS krShare ON krShare.id=download.shareId WHERE krShare.doc_name="+shareId);
			}
			else if(dataFor!=null && dataFor.equalsIgnoreCase("edit"))
			{
				query.append("SELECT COUNT(*) FROM kr_share_report_data AS report LEFT JOIN kr_share_contact_data AS contact on report.krSharingId=contact.id LEFT JOIN kr_share_data AS krshare on contact.kr_share_id=krshare.id WHERE krshare.doc_name="+shareId);
			}
			else if(dataFor!=null && dataFor.equalsIgnoreCase("share"))
			{
				query.append("SELECT COUNT(*) FROM kr_share_data WHERE doc_name="+shareId);
			}
			//System.out.println("query.toString()    "+query.toString());
			List data=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data!=null && data.size()>0) 
			{
				shareID=data.get(0).toString();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return shareID;
	}

	@SuppressWarnings("rawtypes")
	public List fetchAllKRUploadDetails(int docId,SessionFactory connectionSpace)
	{
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.deptName,krGroup.groupName  ");
			query.append(",subGroup.subGroupName,upload.accessType,upload.userName,upload.date,upload.time FROM kr_upload_data AS upload ");
			query.append("LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id ");
			query.append("LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
			query.append("LEFT JOIN department AS dept ON dept.id=krGroup.dept ");
			query.append(" WHERE upload.id='"+docId+"'");
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	public boolean createTableContacts(SessionFactory connectionSpace) 
	{
		boolean flag=false;
		try 
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// Code for creating table dynamically
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob2 = new TableColumes();
		
			ob2 = new TableColumes();
			ob2.setColumnname("kr_share_id");
			ob2.setDatatype("varchar(25)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("contactId");
			ob2.setDatatype("varchar(10)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("share_on");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);
		
			ob2 = new TableColumes();
			ob2.setColumnname("action_date");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("remind_interval");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("status");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);
			
			ob2 = new TableColumes();
			ob2.setColumnname("reportId");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);
			flag = cbt.createTable22("kr_share_contact_data", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableSpecificPoints(SessionFactory connectionSpace) 
	{
		boolean flag=false;
		try 
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// Code for creating table dynamically
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob2 = new TableColumes();
		
			ob2 = new TableColumes();
			ob2.setColumnname("share_id");
			ob2.setDatatype("varchar(25)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("specific_points");
			ob2.setDatatype("varchar(250)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			
			flag = cbt.createTable22("kr_share_specific_points_data", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public int saveIntoContactTable(Map<String, String> dataWithColumnName,SessionFactory connectionSpace,String tableName) 
	{
		int maxId=0;
		try 
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			Iterator<Entry<String, String>> hiterator = dataWithColumnName.entrySet().iterator();
			while (hiterator.hasNext())
			{
				Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				ob = new InsertDataTable();
				ob.setColName(paramPair.getKey().toString());
				ob.setDataName(paramPair.getValue().toString());
				insertData.add(ob);
			}
			 boolean status= cbt.insertIntoTable(tableName, insertData, connectionSpace);
			 if (status)
			 {
				   maxId=cbt.getMaxId(tableName, connectionSpace);
			 }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return maxId;
	}

	@SuppressWarnings("rawtypes")
	public String fetchContactDetails(int maxId, SessionFactory connectionSpace) 
	{
		String contactName=null;
		List list =new ArrayList<String>();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT contactId from kr_share_contact_data where kr_share_id='"+maxId+"' ");
			
			list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list!=null && list.size()>0) {
				contactName=list.toString().replace("[", "").replace("]", "");
			}
					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return contactName;
	}

	@SuppressWarnings("rawtypes")
	public String fetchCountersContact(String shareId,SessionFactory connectionSpace)
	{
		String counter="0";
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String qry="SELECT count(*),status from kr_share_contact_data where kr_share_id ='"+shareId+"' GROUP BY status";
			List data=coi.executeAllSelectQuery(qry, connectionSpace);
			if (data!=null && data.size()>0)
			{
				int done=0;
				int total=0;
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0]!=null)
					{
						total=total+Integer.parseInt(object[0].toString());
						if (object[1].toString().equalsIgnoreCase("Done"))
						{
							done=Integer.parseInt(object[0].toString());
						}
					}
				}
				counter=String.valueOf(done)+"/"+String.valueOf(total);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return counter;
	}

	
}
