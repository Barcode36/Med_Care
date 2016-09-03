package com.Over2Cloud.ctrl.feedback.bedmapping;

import java.util.ArrayList;
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
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class BedMappingAction extends GridPropertyBean{

	private String deptId;
	private List<Object> bedmappingData;
	
	private JSONArray  commonJSONArray = new JSONArray();
	
	public String viewMappingData()
	{
		new HelpdeskUniversalHelper().check_createTable("bed_mapping", connectionSpace);
		//System.out.println("Inside Method  ");
		return SUCCESS;
	}
	
	public String uploadBedMapping()
	{
		return SUCCESS;
	}
	
	
	private String deptName;
	private String empName;
	private String bedNo;
	
	
	public String addBedMapping()
	{
		String returnResult = ERROR;
		try {
			boolean  flag = new HelpdeskUniversalHelper().isExist("bed_mapping", "contact_id", empName, "bed_no", bedNo, "", "", connectionSpace);
			// System.out.println("Flag Value  "+flag);
			 if (!flag) {
				 InsertDataTable ob=null;
			     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			     CommonOperInterface cot = new CommonConFactory().createInterface();
				 ob=new InsertDataTable();
				 ob.setColName("contact_id");
				 ob.setDataName(empName);
				 insertData.add(ob);
				 
				 ob=new InsertDataTable();
				 ob.setColName("bed_no");
				 ob.setDataName(bedNo);
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
				 boolean status= cot.insertIntoTable("bed_mapping",insertData,connectionSpace);
				 if (status) {
					// System.out.println("Inside Success If");
					 addActionMessage("Bed Mapping Added Successfully !!");
				}
				 else {
					// System.out.println("Inside Success Else");
					addActionError("May be Bed Mapping is Already Exist or Some Error !!!");
				}
			returnResult = SUCCESS;
		} 
		}catch (Exception e) {
		e.printStackTrace();
		}
		return returnResult;
	}
	
	// Method for getting Feedback Type Data
	@SuppressWarnings("unchecked")
	public String getBedMappingData()
	 {
		//System.out.println("Inside getData Method");
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				bedmappingData = new ArrayList<Object>();
				//System.out.println("XYZ value in FB Type is  "+xyz);
				HelpdeskUniversalHelper HUH= new HelpdeskUniversalHelper();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("fbType", "ASC");
				List data=null;
				
			    data= new BedMappingHelper().getBedMappingData();
			
				setRecords(data.size());
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=new ArrayList();
				fieldNames.add("id");
				fieldNames.add("deptName");
				fieldNames.add("empName");
				fieldNames.add("bed_no");
					
				if(data!=null && data.size()>0)
				{
					List<Object> Listhb=new ArrayList<Object>();
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						Map<String, Object> one=new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) {
							if(obdata[k]!=null)
							{
							  if(k==0)
							     {
										one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
								 }
							  else
								 {
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
								 }
							}
						}
						Listhb.add(one);
					}
					setBedmappingData(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				e.printStackTrace();
			}
		}
		else
		{returnResult=LOGIN;}
		return returnResult;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getMappedEmployee() 
	 {
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			 try {
				 if(userName==null || userName.equalsIgnoreCase(""))
						return LOGIN;
				 
					List resultList=new ArrayList<String>();
					StringBuilder sb = new StringBuilder();
					sb.append("select contacts.id,emp.empName from employee_basic as emp");
					sb.append(" inner join compliance_contacts as contacts on emp.id=contacts.emp_id");
					sb.append(" where contacts.forDept_id="+deptId+" ORDER BY emp.empName asc");
				
					System.out.println(">>>"+sb);
					resultList = new createTable().executeAllSelectQuery(sb.toString(),connectionSpace);
					if(resultList!=null && resultList.size()>0)
					 {
						//System.out.println("Inside If block");
						for (Object c : resultList) 
						 {
							Object[] objVar = (Object[]) c;
							JSONObject listObject=new JSONObject();
							listObject.put("id",objVar[0].toString());
							listObject.put("name", objVar[1].toString());
							commonJSONArray.add(listObject);
						 }
					 }
					returnResult = SUCCESS;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		 }
		else 
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public List<Object> getBedmappingData() {
		return bedmappingData;
	}

	public void setBedmappingData(List<Object> bedmappingData) {
		this.bedmappingData = bedmappingData;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
}