package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;

@SuppressWarnings("serial")
public class ActivityBoardSecond extends GridPropertyBean
{
	private JSONArray jsonArray ;
	private String toDepart;
	private String sel_id;
	private String complianId;
	private String roomNo;
	 
	public String getPresentEmployee()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				//String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select count(*),subdepartment.subdeptname,subdepartment.id  as roasterId from  compliance_contacts as comp  ");
				query.append(" inner join subdepartment on comp.forDept_id=subdepartment.id ");
				query.append(" inner join department on subdepartment.deptid=department.id ");
				query.append(" inner join emp_wing_mapping as ewm on ewm.empName=comp.emp_id ");
				query.append(" inner join shift_with_emp_wing shift on ewm.shiftId = shift.id ");
				query.append(" where  comp.work_status = '0' and comp.level=1 and comp.moduleName = 'HDM' "
						+ " and shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "'"
								+ " group by subdepartment.id,comp.id ");
				//System.out.println("Count for Working employees with their Subdepartment ::::"+query);
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				if(data!=null && data.size()>0)
				{
					Map<String, Integer> mp = new HashMap<String, Integer>();
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if( object[0]!=null && object[1]!=null && object[2]!=null )
						{
						    if(mp.keySet().contains(object[1].toString()+"#"+object[2].toString()))
						    {
						        mp.put(object[1].toString()+"#"+object[2].toString(), mp.get(object[1].toString()+"#"+object[2].toString())+1);
				
						    }else
						    {
						        mp.put(object[1].toString()+"#"+object[2].toString(), 1);
						    }
							
						}
					}
					if(mp!=null && mp.size()>0)
					{
						for (Map.Entry<String, Integer> entry : mp.entrySet())
						{
							jobj = new JSONObject();
							jobj.put("counter",entry.getValue());
							jobj.put("subdept", entry.getKey().split("#")[0]);
							jobj.put("subdeptId",entry.getKey().split("#")[1]);
							jsonArray.add(jobj);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
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
	
	
	  public String getRoomDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
			 		CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select feed.id,feed.ticket_no,feed.open_date,feed.open_time,subcatg.subCategoryName from feedback_status_new as feed  ");
				query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feed.subCatg");
				query.append(" LEFT JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
				query.append(" LEFT JOIN floor_room_detail AS floor ON floor.id = feed.location");
		 		query.append(" where  floor.id ='" + roomNo + "' and feed.status='Pending'  order by feed.ticket_no ");
				//System.out.println(" QQ::::"+query);
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				if(data!=null && data.size()>0)
				{
					Map<String, Integer> mp = new HashMap<String, Integer>();
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if( object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null)
						{
						    if(mp.keySet().contains(object[1].toString()+"#"+object[2].toString()))
						    {
						        mp.put(object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString()+"#"+object[3].toString(), mp.get(object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString()+"#"+object[3].toString()+"#"+object[4].toString())+1);
				
						    }else
						    {
						        mp.put(object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString()+"#"+object[3].toString()+"#"+object[4].toString(), 1);
						    }
							
						}
					}
					if(mp!=null && mp.size()>0)
					{
						for (Map.Entry<String, Integer> entry : mp.entrySet())
						{
							jobj = new JSONObject();
							jobj.put("id", entry.getKey().split("#")[0]);
						 	jobj.put("ticketNo", entry.getKey().split("#")[1]);
							jobj.put("openDate",entry.getKey().split("#")[2]);
							jobj.put("openTime",entry.getKey().split("#")[3]);
							jobj.put("subCatg",entry.getKey().split("#")[4]);
							jsonArray.add(jobj);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
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
	
	
	
	
	public String getEmployeeInfo()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				//String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select  distinct  emp.empName,emp.mobOne,emp.emailIdOne,department.deptName,subdepartment.subdeptname ,comp.level,shift.fromShift,shift.toShift,wing.wingsname,flr.floorname,comp.id as comp  from employee_basic  as emp ");
				query.append(" inner join compliance_contacts as comp on comp.emp_id=emp.id ");
				query.append(" inner join subdepartment on comp.forDept_id=subdepartment.id ");
				query.append(" inner join department on subdepartment.deptid=department.id ");
				query.append(" inner join emp_wing_mapping as ewm on ewm.empName=comp.emp_id ");
				query.append(" inner join shift_with_emp_wing shift on ewm.shiftId = shift.id ");
				query.append(" inner join wings_detail as wing on wing.id=ewm.wingsname ");
				query.append(" inner Join floor_detail as flr on flr.id = wing .floorname ");
				query.append(" where  comp.work_status = '0' and comp.level=1  and comp.moduleName = 'HDM'  and shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "' and subdepartment.id='"+toDepart+"' GROUP BY  comp.id");
			  //  System.out.println("free Emp info ::::"+query);
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						jobj = new JSONObject();
						jobj.put("empName",object[0]==null?"NA":object[0].toString());
						jobj.put("mobile", object[1]==null?"NA":object[1].toString());
						jobj.put("email", object[2]==null?"NA":object[2].toString());
						jobj.put("dept", object[3]==null?"NA":object[3].toString());
						jobj.put("subdept", object[4]==null?"NA":object[4].toString());
						jobj.put("level", object[5]==null?"NA":object[5].toString());
						jobj.put("shiftFrom", object[6]==null?"NA":object[6].toString());
						jobj.put("shiftTo", object[7]==null?"NA":object[7].toString());
						jobj.put("wing", object[8]==null?"NA":object[8].toString());
						jobj.put("floor", object[9]==null?"NA":object[9].toString());
						jobj.put("compid", object[10]==null?"NA":object[10].toString());
						
						jsonArray.add(jobj);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
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
	
	@SuppressWarnings("rawtypes")
	public String getEmployeeLocationInfo()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select flr.floorname,wing.wingsname from emp_wing_mapping as ewm  ");
				query.append(" inner join compliance_contacts cc on cc.emp_id = ewm.empName  ");
				query.append(" inner join wings_detail wing on wing.id = ewm.wingsname ");
				query.append(" inner join floor_detail flr on flr.id = wing.floorname ");
				query.append(" inner join shift_with_emp_wing shift on ewm.shiftId = shift.id ");
				query.append(" where  cc.forDept_id='"+toDepart+"' AND cc.id='"+sel_id+"' and shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "' ");
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						jobj = new JSONObject();
						jobj.put("floor",object[0]==null?"NA":object[0].toString());
						jobj.put("wing", object[1]==null?"NA":object[1].toString());
						
						jsonArray.add(jobj);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
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
	public String fetchLocationInfo()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select flr.floorname,wing.wingsname,room.roomno from feedback_status_new as feed  ");
				query.append(" inner join floor_room_detail room on room.id = feed.location  ");
				query.append(" inner join wings_detail wing on wing.id = room.wingsname ");
				query.append(" inner join floor_detail flr on flr.id = wing.floorname ");
				query.append(" where  feed.id='"+complianId+"' ");
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						jobj = new JSONObject();
						jobj.put("floor",object[0]==null?"NA":object[0].toString());
						jobj.put("wing", object[1]==null?"NA":object[1].toString());
						jobj.put("room", object[2]==null?"NA":object[2].toString());
						
						jsonArray.add(jobj);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
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
	public String getfreeEmployee()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select count(*),subdepartment.subdeptname,subdepartment.id from roaster_conf  as roaster ");
				query.append(" inner join compliance_contacts as comp on comp.id=roaster.contactId ");
				query.append(" inner join employee_basic as emp on emp.id = comp.emp_id ");
				query.append(" inner join feedback_status as feed on feed.allot_to=emp.id ");
				query.append(" inner join subdepartment on roaster.dept_subdept=subdepartment.id ");
				query.append(" inner join department on subdepartment.deptid=department.id ");
				query.append(" inner join shift_conf shift on subdepartment.id = shift.dept_subdept ");
				query.append(" where shift.shiftName="+shiftname+" and comp.work_status = 3 and roaster.attendance='Present' ");
				query.append(" and roaster.status='Active' and comp.moduleName = 'HDM'  and shift.shiftFrom<='" + DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "' and feed.status NOT IN ('pending') group by subdepartment.subdeptname ");
				//System.out.println(" free Emp info ::::"+query);
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				
						if(data!=null && data.size()>0){
							for (Iterator iterator = data.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if( object[0]!=null && object[1]!=null && object[2]!=null  ){
									jobj = new JSONObject();
									jobj.put("counter", object[0].toString());
									jobj.put("subdept", object[1].toString());
									jobj.put("subdeptId", object[2].toString());
								}
								jsonArray.add(jobj);
							}
						}
						 

				return SUCCESS;
			}
			catch (Exception e)
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
	
	
	public String getfreeEmployeeInfo()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder();
				String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" select emp.empName,emp.mobOne,emp.emailIdOne,department.deptName,subdepartment.subdeptname ,comp.deptLevel from roaster_conf  as roaster ");
				query.append(" inner join compliance_contacts as comp on comp.id=roaster.contactId ");
				query.append(" inner join employee_basic as emp on emp.id = comp.emp_id ");
				query.append(" inner join feedback_status as feed on feed.allot_to=emp.id ");
				query.append(" inner join subdepartment on roaster.dept_subdept=subdepartment.id ");
				query.append(" inner join department on subdepartment.deptid=department.id ");
				query.append(" inner join shift_conf shift on subdepartment.id = shift.dept_subdept ");
				query.append(" where shift.shiftName="+shiftname+" and comp.work_status = 3 and roaster.attendance='Present' ");
				query.append(" and roaster.status='Active' and comp.moduleName = 'HDM'  and shift.shiftFrom<='" + DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "' and feed.status NOT IN ('pending') group by subdepartment.subdeptname ");
				//System.out.println("Emp info ::::"+query);
				
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				jsonArray = new JSONArray();
				JSONObject jobj=null ;
				
						if(data!=null && data.size()>0){
							for (Iterator iterator = data.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if( object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null && object[4]!=null && object[5]!=null ){
									jobj = new JSONObject();
									jobj.put("empName", object[0].toString());
									jobj.put("mobile", object[1].toString());
									jobj.put("email", object[2].toString());
									jobj.put("dept", object[3].toString());
									jobj.put("subdept", object[4].toString());
									jobj.put("level", object[5].toString());
								}
								jsonArray.add(jobj);
							}
						}
						 

				return SUCCESS;
			}
			catch (Exception e)
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

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getToDepart() {
		return toDepart;
	}

	public void setToDepart(String toDepart) {
		this.toDepart = toDepart;
	}

	public String getSel_id() {
		return sel_id;
	}

	public void setSel_id(String sel_id) {
		this.sel_id = sel_id;
	}

	public String getComplianId() {
		return complianId;
	}

	public void setComplianId(String complianId) {
		this.complianId = complianId;
	}
	
	public String getRoomNo() {
		return roomNo;
	}


	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}


}
