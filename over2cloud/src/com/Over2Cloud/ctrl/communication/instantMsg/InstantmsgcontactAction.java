package com.Over2Cloud.ctrl.communication.instantMsg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class InstantmsgcontactAction extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private String select_param;
	private JSONArray commonJSONArray;
	private String id=null;
	private String check_list=null;
	private String group_name=null;
	private String createnew_group=null;
	private String name=null;
	private String creategroup_date=null;
	private String creategroup_time=null;
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private String groupid;
	private String template_type;
	private String comment=null;

	private List<Object>  groupDetail;
	private Map<String,Object> mapobject;
	private Map<String,Object> groupmapobject=new HashMap<String, Object>();
	
	
	public Map<String, Object> getGroupmapobject() {
		return groupmapobject;
	}

	public void setGroupmapobject(Map<String, Object> groupmapobject) {
		this.groupmapobject = groupmapobject;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getcontactsubType(){
		String returnString=ERROR;
		commonJSONArray=new JSONArray();
		try{
			
			CommonforClassdata obhj = new CommonOperAtion();
			String query = "SELECT id,deptName FROM department where groupId="+select_param;
			List data = obhj.executeAllSelectQuery(query, connectionSpace);
			if(data.size()>0){
				JSONObject listObject=new JSONObject();
				listObject.put("id", "all_contactSubType");
				listObject.put("name", "All SubContact Type");
				commonJSONArray.add(listObject);
              for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				listObject=new JSONObject();
				listObject.put("id", object[0].toString());
				listObject.put("name", object[1].toString());
				commonJSONArray.add(listObject);
			  }
            
			}
			returnString=SUCCESS;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnString;
		
	}
	
	public String getcontactType(){
		 String returnString=ERROR;
		 commonJSONArray=new JSONArray();
		 mapobject=new HashMap<String,Object>();
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			String query = "SELECT id,empName,mobOne FROM employee_basic where deptname="+select_param;
			List data = obhj.executeAllSelectQuery(query, connectionSpace);
			if(data.size()>0){
				JSONObject listObject=new JSONObject();
				listObject.put("id", "all_contactname");
				listObject.put("name", "All Contact Preson");
				commonJSONArray.add(listObject);
              for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				listObject=new JSONObject();
				listObject.put("id", object[0].toString());
				listObject.put("name", object[1].toString()+"-"+object[2].toString());
				commonJSONArray.add(listObject);
				if(!object[2].toString().equalsIgnoreCase("NA")){
					String[] array= object[2].toString().trim().split(",");
					for (int i = 0; i < array.length; i++) {
						mapobject.put(object[0].toString(), object[1].toString()+"-"+array[i]);
					}
				}else{
				}
			   }
			}
			returnString=SUCCESS;
           
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return returnString;
		
	}
	
	
	public String getcontactmailType(){
		 String returnString=ERROR;
		 commonJSONArray=new JSONArray();
		 mapobject=new HashMap<String,Object>();
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			String query = "SELECT id,empName,emailIdOne FROM employee_basic where deptname="+select_param;
			List data = obhj.executeAllSelectQuery(query, connectionSpace);
			if(data.size()>0){
				JSONObject listObject=new JSONObject();
				listObject.put("id", "all_contactname");
				listObject.put("name", "All Contact Preson");
				commonJSONArray.add(listObject);
             for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				listObject=new JSONObject();
				listObject.put("id", object[0].toString());
				listObject.put("name", object[1].toString()+"-"+object[2].toString());
				commonJSONArray.add(listObject);
				if(!object[2].toString().equalsIgnoreCase("NA")){
					String[] array= object[2].toString().trim().split(",");
					for (int i = 0; i < array.length; i++) {
						mapobject.put(object[0].toString(), object[1].toString()+"-"+array[i]);
					}
				}else{
				}
			   }
			}
			returnString=SUCCESS;
          
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return returnString;
		
	}
	
	
	public String gettemplatedata(){
		 String returnString=ERROR;
		 commonJSONArray=new JSONArray();
		 mapobject=new HashMap<String,Object>();
		 JSONObject listObject;
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			String query = "SELECT id,template_name FROM createtemplate where template_type='"+getTemplate_type()+"' and  status='Approve'" +"  and username='"+userName+"'";
			List data = obhj.executeAllSelectQuery(query, connectionSpace);
			if(data.size()>0){
				
              for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				listObject=new JSONObject();
				listObject.put("id", object[0].toString());
				listObject.put("name", object[1].toString());
				commonJSONArray.add(listObject);
			   }
			}
			returnString=SUCCESS;
           
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return returnString;
		
	}
	
	
	public String getgroup(){
		 String returnString=ERROR;
		groupmapobject=new HashMap<String,Object>();
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			String query = "SELECT id,name FROM group_name where user= '"+userName+"'";
			List data = obhj.executeAllSelectQuery(query, connectionSpace);
			if(data.size()>0){
				JSONObject listObject=new JSONObject();
             for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				groupmapobject.put(object[0].toString(), object[1].toString());
			   }
     		Map<String, Object> sortedMaps = obhj.sortByComparator(groupmapobject);
             setGroupmapobject(sortedMaps);;
			}
			returnString=SUCCESS;
          
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return returnString;
		
	}
	
	
	
	public String addgroupdata(){
		CommonforClassdata obhj = new CommonOperAtion();
		String resultString=ERROR;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	  	StringBuilder StrnObj=new StringBuilder();
		try{
			
				InsertDataTable dateob = new InsertDataTable();
				dateob.setColName("groupdate");
				dateob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(dateob);
				
				InsertDataTable timeob = new InsertDataTable();
				timeob.setColName("grouptime");
				timeob.setDataName(DateUtil.getCurrentTime());
				insertData.add(timeob);
				
				InsertDataTable usereob = new InsertDataTable();
				usereob.setColName("user");
				usereob.setDataName(userName);
				insertData.add(usereob);
				
			if(!group_name.equalsIgnoreCase("-1") && !group_name.equalsIgnoreCase(" "))	{
				String[] Array1 = null;
				String ids=null;
				String query="select id,contactid from"+" "+"croupdata where groupid="+getGroup_name();
				System.out.println("query"+query);
				List  dataCount=obhj.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount.size()>0){
				for (Iterator iterator = dataCount.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					ids=object[0].toString();
					StrnObj.append(object[1].toString());
					Array1=object[1].toString().split(",");
				}
				
				Map<String,Object> SetMap=new HashMap<String, Object>();
				Map<String,Object> WhereClouse=new HashMap<String, Object>();
			if(check_list!=null && !check_list.equalsIgnoreCase("")){
				String[] Array2= check_list.split(",");
				List<String> saveList=new ArrayList<String>(Arrays.asList(Array1));
				List<String> getList=new ArrayList<String>(Arrays.asList(Array2));
				
				saveList.retainAll(getList);  //Now l2 have only common elements of both list this is an optional this will work well when there are thousands of element otherwise only do remove all
				getList.removeAll(saveList);  //Here magic happens this will remove common element from l1 so l1 will have only elements what are not in l2

				if(getList.size()>0){
		        for(String v: getList){
		        	StrnObj.append(",");
		        	StrnObj.append(v);
		            System.out.println(v);
		        }
				SetMap.put("contactid", StrnObj.toString());
				WhereClouse.put("id", ids);
				boolean status = obhj.updateIntoTable("croupdata", SetMap, WhereClouse, connectionSpace);
				if (status) {
					addActionMessage("Data added SuccessfUlly.");
				} else {
					addActionMessage("Opps There is a problem.");
			     }}else{
			    	 addActionMessage("Data Already Exist.");
			     }
			 }
			
				}else{
					InsertDataTable ob = new InsertDataTable();
					ob.setColName("groupid");
					ob.setDataName(group_name);
					insertData.add(ob);
					if(check_list!=null && !check_list.equalsIgnoreCase("")){
						String[] groupidArray= check_list.split(",");
						int sizes=1;
						for (int i=0; i < groupidArray.length; i++) {
							if(sizes<groupidArray.length){
							
						  		StrnObj.append(groupidArray[i].toString().trim());
						  		StrnObj.append(",");}
						  	 else{
						  		StrnObj.append(groupidArray[i].toString().trim());}
							sizes++;
						}
						InsertDataTable obj = new InsertDataTable();
						obj.setColName("contactid");
						obj.setDataName(StrnObj.toString());
						insertData.add(obj);
					}
					
					boolean status = obhj.insertIntoTableifNotExit("croupdata", insertData,connectionSpace);
					if (status) {
						addActionMessage("Data added SuccessfUlly");
					} else {
						addActionMessage("Opps There is a problem");
				     }	
				}
			}
			resultString=SUCCESS;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
		
	}

	public String creategroup(){
		System.out.println(">>>>>>>>>>>>>>>>jklfbaskjfbajshfkjashfkahskj");
		String retrunString=ERROR;
		try{
			List <InsertDataTable> insertDatas=new ArrayList<InsertDataTable>();
			if(createnew_group!=null && !createnew_group.equalsIgnoreCase("")){
				InsertDataTable ob = new InsertDataTable();
				ob.setColName("name");
				ob.setDataName(createnew_group);
				insertDatas.add(ob);
				
				InsertDataTable cobj = new InsertDataTable();
				cobj.setColName("comment");
				cobj.setDataName(getComment());
				insertDatas.add(cobj);
				
				InsertDataTable cobjA = new InsertDataTable();
				cobjA.setColName("user");
				cobjA.setDataName(userName);
				insertDatas.add(cobjA);
				
				boolean status =  new CommonOperAtion().insertIntoTable("group_name", insertDatas,connectionSpace);
				if(status){
					addActionMessage("Data added SuccessfUlly");
				}
				else{
					addActionError("Opps There is some problem");
				}
				
			  }
			
			retrunString=SUCCESS; 
		}catch(Exception e){
			e.printStackTrace();
		}
		return retrunString;
	}
	
	
	
	public String getgroupDataview(){
		String resultString=ERROR;
		Object[] object=null;
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		 List<Object> Listhb=new ArrayList<Object>();
		 List datatemp=null;
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			Map<String,Object> whereClose=new HashMap<String, Object>();
		
					List<String> listdata=new ArrayList<String>();
					listdata.add("id");
					listdata.add("contactid");
					listdata.add("groupid");
					listdata.add("groupdate");
					listdata.add("grouptime");
					if(getGroupid().equalsIgnoreCase("-1") &&  getGroupid()!=null){
					datatemp= obhj.getSelectdataFromSelectedFields("croupdata",listdata, whereClose, connectionSpace);
					}else{
						whereClose.put("groupid", getGroupid());
						datatemp= obhj.getSelectdataFromSelectedFields("croupdata",listdata, whereClose, connectionSpace);
					}
					if(datatemp.size()>0){
					 for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
						 List<String> litdata=new ArrayList<String>();
						 StringBuilder ids=new StringBuilder(); 
						 object = (Object[]) iterator.next();
				       
							StringBuilder query=new StringBuilder("");
							query.append("select count(*) from"+" "+"employee_basic where id in("+object[1].toString()+")");
							System.out.println("query"+query);
							List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if(dataCount!=null){
								BigInteger count=new BigInteger("3");
								for(Iterator it=dataCount.iterator(); it.hasNext();){
									 Object obdata=(Object)it.next();
									 count=(BigInteger)obdata;
								}
								setRecords(count.intValue());
								int to = (getRows() * getPage());
								int from = to - getRows();
								if (to > getRecords())
									to = getRecords();
								
						
				     	List<String> listdataa=new ArrayList<String>();
				     	listdataa.add("id");
				     	listdataa.add("empName");
				     	listdataa.add("mobOne");
				     	listdataa.add("emailIdOne");
				     	listdataa.add("address");
				     	listdataa.add("city");
				     	listdataa.add("name");
				     	
				         ids.append("Select ");
				         ids.append("contact.id,");
						 ids.append("contact.empName,");
						 ids.append("contact.mobOne,");
						 ids.append("contact.emailIdOne,");
						 ids.append("contact.address,");
						 ids.append("contact.city,");
						 ids.append("groupname.name");
						 ids.append(" "+"FROM employee_basic as contact"+" "
						 +"inner join croupdata as gropdata"+" "
						 +"inner join group_name as groupname on gropdata.groupid="+"groupname.id"+" "
						 +"where contact.id in ("+object[1].toString()+")"+" "
						 +"and groupname.id="+"'"+object[2].toString()+"'"
						 );
							if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")){
								if(getSearchOper().equalsIgnoreCase("eq")){
									ids.append(" and "+getSearchField()+" = '"+getSearchString()+"'");}
								else if(getSearchOper().equalsIgnoreCase("cn")){
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"%'");}
								else if(getSearchOper().equalsIgnoreCase("bw")){
									ids.append(" and "+getSearchField()+" like '"+getSearchString()+"%'");}
								else if(getSearchOper().equalsIgnoreCase("ne")){
									ids.append(" and "+getSearchField()+" <> '"+getSearchString()+"'");}
								else if(getSearchOper().equalsIgnoreCase("ew")){
									ids.append(" and "+getSearchField()+" like '%"+getSearchString()+"'");
								}}
							ids.append(" limit "+from+","+to);
							System.out.println("ids"+ids);
							List  data=cbt.executeAllSelectQuery(ids.toString(),connectionSpace);
							String [] fieldsArray = listdataa.toArray(new String[listdataa.size()]);
								if(data!=null)
								{
									if(data.size()>0){
										for(Iterator it=data.iterator(); it.hasNext();){
											Object[] obdata=(Object[])it.next();
											Map<String, Object> one=new HashMap<String, Object>();
											for (int k = 0; k < obdata.length; k++) {
												if(obdata[k]!=null){
												if(k==0)
													one.put(fieldsArray[k], (Integer)obdata[k]);
												else
												   if(fieldsArray[k].equalsIgnoreCase("ids")){
													  one.put(fieldsArray[k], id);
												   }
												   else{
													one.put(fieldsArray[k], obdata[k].toString());
												   }
												}
												
											}
											Listhb.add(one);
										}
									  }
									}
							      }
							   
							   }
					   }
					       
							  setGroupDetail(Listhb);
							  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));		
		        }catch(Exception e){
		    	 e.printStackTrace();
		     }
	  return resultString=SUCCESS;
	}
	
	
	

	public String getCreatenew_group() {
		return createnew_group;
	}

	public void setCreatenew_group(String createnew_group) {
		this.createnew_group = createnew_group;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}
	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}
	public String getSelect_param() {
		return select_param;
	}


	public void setSelect_param(String select_param) {
		this.select_param = select_param;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreategroup_date() {
		return creategroup_date;
	}

	public void setCreategroup_date(String creategroup_date) {
		this.creategroup_date = creategroup_date;
	}

	public String getCreategroup_time() {
		return creategroup_time;
	}

	public void setCreategroup_time(String creategroup_time) {
		this.creategroup_time = creategroup_time;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public List<Object> getGroupDetail() {
		return groupDetail;
	}

	public void setGroupDetail(List<Object> groupDetail) {
		this.groupDetail = groupDetail;
	}

	public Map<String, Object> getMapobject() {
		return mapobject;
	}

	public void setMapobject(Map<String, Object> mapobject) {
		this.mapobject = mapobject;
	}
	public String getCheck_list() {
		return check_list;
	}

	public void setCheck_list(String check_list) {
		this.check_list = check_list;
	}

	public String getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



}
