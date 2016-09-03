package com.Over2Cloud.ctrl.floorsetting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class FloorSettingHelper
{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	public List getFloor(SessionFactory connection, CommonOperInterface cbt, String dataFor)
	{
		List dataList = null;
		try
		{
			if(dataFor!=null && dataFor.equalsIgnoreCase("Floor Detail"))
			{
				String query ="SELECT id,floorname FROM floor_detail ORDER BY floorname";
				dataList = cbt.executeAllSelectQuery(query, connection);
			}
			else if(dataFor!=null && dataFor.equalsIgnoreCase("Group Detail"))	
			{
				String query ="SELECT id,groupName FROM groupinfo WHERE status='Active' ORDER BY groupName";
				dataList = cbt.executeAllSelectQuery(query, connection);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public List getEmployee(SessionFactory connection, CommonOperInterface cbt, String deptId, String groupId)
	{
		List dataList = null;
		try
		{
			String query ="SELECT id,empName FROM employee_basic WHERE groupId="+deptId+" AND deptname="+groupId+" ORDER BY empName";
			dataList = cbt.executeAllSelectQuery(query, connection);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	public String[] getCCEmailId(String to_dept_subdept,String roomNo, SessionFactory connectionSpace,CommonOperInterface cbt)
	{
		List list = new ArrayList();
		StringBuilder ccemailId = new StringBuilder();
		StringBuilder ccmobNo = new StringBuilder();
		StringBuilder qry = new StringBuilder();
		String str[] = new String[2];
		try
		{
			if(roomNo!=null && !roomNo.equals("") && !roomNo.equals("-1"))
			{
				qry.append("SELECT emp.emailIdOne,emp.mobOne FROM employee_basic AS emp");
				qry.append(" INNER JOIN roomwise_cc_emp_mapping AS room ON room.mapped_emp_id=emp.id");
				qry.append(" WHERE room.room_id="+roomNo+" AND room.module_name='HDM' AND room.status='Active' AND emp.flag=0");
				list = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if (list != null && list.size() > 0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null)
							ccemailId.append(object[0].toString() + ",");
						if(object[1]!=null)
							ccmobNo.append(object[1].toString() + ",");
					}
					list.clear();
				}
				
			}
			if(to_dept_subdept!=null && !to_dept_subdept.equals(" ") && !to_dept_subdept.equals("-1"))
			{
				qry.append("SELECT emp.emailIdOne,emp.mobOne FROM employee_basic AS emp");
				qry.append(" INNER JOIN deptwise_cc_emp_mapping AS deptcc ON deptcc.mapped_emp_id = emp.id");
				qry.append(" INNER JOIN subdepartment AS subdept ON subdept.deptid = deptcc.dept_id");
				qry.append(" WHERE subdept.id=18 AND deptcc.module_name='HDM' AND deptcc.status='Active' AND emp.flag=0");
				
				list = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if (list != null && list.size() > 0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null)
							ccemailId.append(object[0].toString() + ",");
						if(object[1]!=null)
							ccmobNo.append(object[1].toString() + ",");
					}
					list.clear();
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if(ccemailId!=null && ccemailId.length()>0)
		{
			str[0] = ccemailId.toString().trim().substring(0, ccemailId.toString().length() - 1);
		}
		if(ccmobNo!=null && ccmobNo.length()>0)
		{
			str[1] = ccmobNo.toString().trim().substring(0, ccmobNo.toString().length() - 1);
		}
		return str;
	}
	
	public Map<String, String> getFloorInfo(SessionFactory connection)
	{
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		try
		{
			String query = "SELECT id,floorname FROM floor_detail ORDER BY floorname";
			List dataList = cbt.executeAllSelectQuery(query, connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						dataMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataMap;
	}

	public Map<String, String> getSubFloorInfo(SessionFactory connection, String floorId)
	{
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		try
		{
			StringBuilder query = new StringBuilder("SELECT id,roomno FROM floor_room_detail");
			if (floorId != null)
				query.append(" WHERE floorname=" + floorId);

			query.append(" ORDER BY roomno");

			List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						dataMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataMap;
	}

	// For Buddy
	@SuppressWarnings("unchecked")
	public List getBudySubLocDetails(SessionFactory connection,String id)
	{
		List empList = new ArrayList();
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append("select subLoc.extention,subLoc.roomno,flr.floorcode from floor_room_detail as subLoc");
			sb.append(" inner join floor_detail as flr on subLoc.floorname=flr.id");
			sb.append(" where subLoc.id='" + id + "'");
			// System.out.println("QQQQ "+sb.toString());
			empList = cbt.executeAllSelectQuery(sb.toString(), connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}
	public List getBudySubLocFloorDetails(SessionFactory connectionSpace,
			String id) {
		List empList = new ArrayList();
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append("select wing.wingsname,subLoc.roomno,flr.floorcode,flr.floorname,subLoc.id as subFloorId,flr.id as floorId from floor_room_detail as subLoc");
			sb.append(" inner join floor_detail as flr on subLoc.floorname=flr.id");
			sb.append(" inner join wings_detail as wing on subLoc.wingsname=wing.id");
			sb.append(" where subLoc.roomno LIKE '%"+id+"%'");
			 System.out.println("QQQQ "+sb.toString());
			empList = cbt.executeAllSelectQuery(sb.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}
}
