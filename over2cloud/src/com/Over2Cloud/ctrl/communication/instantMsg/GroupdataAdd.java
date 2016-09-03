package com.Over2Cloud.ctrl.communication.instantMsg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GroupdataAdd extends ActionSupport{
	static final Log log = LogFactory.getLog(GroupdataAdd.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	private String id=null;
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
	private List<Object>  groupDetail;
	@SuppressWarnings("unchecked")
	public String addgroupdata(){
		CommonforClassdata obhj = new CommonOperAtion();
		String resultString=ERROR;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		List<Object[]> queryResult=null;
	  	StringBuilder StrnObj=new StringBuilder();
		try{
		
			if(creategroup_date!=null && !creategroup_date.equalsIgnoreCase(""))	{
				InsertDataTable ob = new InsertDataTable();
				ob.setColName("groupdate");
				ob.setDataName(creategroup_date);
				insertData.add(ob);
			}
			if(creategroup_time!=null && !creategroup_time.equalsIgnoreCase(""))	{
				InsertDataTable ob = new InsertDataTable();
				ob.setColName("grouptime");
				ob.setDataName(creategroup_time);
				insertData.add(ob);
			}
			if(!name.equalsIgnoreCase("-1") && !name.equalsIgnoreCase(" "))	{
			InsertDataTable ob = new InsertDataTable();
			ob.setColName("groupid");
			ob.setDataName(name);
			insertData.add(ob);
			
			if(id!=null && !id.equalsIgnoreCase("")){
				String[] groupidArray= id.split(",");
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
			List <InsertDataTable> insertDatas=new ArrayList<InsertDataTable>();
			if(createnew_group!=null && !createnew_group.equalsIgnoreCase("")){
		       System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+createnew_group);
				InsertDataTable ob = new InsertDataTable();
				ob.setColName("name");
				ob.setDataName(createnew_group);
				insertDatas.add(ob);
				queryResult =  new CommonOperAtion().insertIntoTableReturnId("group_name", insertDatas,connectionSpace);
				if(queryResult!=null){
					InsertDataTable obc = new InsertDataTable();
					obc.setColName("groupid");
					obc.setDataName(createnew_group);
					insertData.add(obc);
				}
				if(id!=null && !id.equalsIgnoreCase("")){
					String[] groupidArray= id.split(",");
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
					
					boolean status = obhj.insertIntoTableifNotExit("croupdata", insertData,connectionSpace);
					if (status) {
						addActionMessage("Data added SuccessfUlly");
					} else {
						addActionMessage("Opps There is a problem plzz call Mr.Pankaj");
				   }}
				else{
					addActionMessage("Data added SuccessfUlly");
				}
				
			 }
			
			resultString=SUCCESS;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
		
	}
	
	
	public String viewgroupData(){
		String resultString=ERROR;
		Object[] object=null;
		
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		 List<Object> Listhb=new ArrayList<Object>();
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			Map<String,Object> whereClose=new HashMap<String, Object>();
		
					List<String> listdata=new ArrayList<String>();
					listdata.add("id");
					listdata.add("contactid");
					listdata.add("groupid");
					listdata.add("groupdate");
					listdata.add("grouptime");
					int id=1;
					List datatemp= obhj.getSelectdataFromSelectedFields("croupdata",listdata, whereClose, connectionSpace);
					 for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
						 List<String> litdata=new ArrayList<String>();
						 StringBuilder ids=new StringBuilder(); 
						 object = (Object[]) iterator.next();
				       
							StringBuilder query=new StringBuilder("");
							query.append("select count(*) from"+" "+"contactbasicdetails where id in("+object[1].toString()+")");
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
				     	listdataa.add("first_name");
				     	listdataa.add("last_name");
				     	listdataa.add("mobilenumber");
				     	listdataa.add("emailid");
				     	listdataa.add("ids");
				     	listdataa.add("name");
				     	
				         ids.append("Select ");
				         ids.append("contact.id,");
						 ids.append("contact.first_name,");
						 ids.append("contact.last_name,");
						 ids.append("contact.mobilenumber,");
						 ids.append("contact.emailid,");
						 ids.append("contact.department,");
						 ids.append("groupname.name");
						 ids.append(" "+"FROM contactbasicdetails as contact"+" "
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
											id++;
											Listhb.add(one);
										}
									  }
									}
							      }
							   
							   }
					       
							  setGroupDetail(Listhb);
							  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));		
						
						 resultString=SUCCESS;
					 
                         
			}
						  
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return resultString;

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


	public String getSord() {
		return sord;
	}


	public void setSord(String sord) {
		this.sord = sord;
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


	public String getSearchOper() {
		return searchOper;
	}


	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
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

	public String getCreatenew_group() {
		return createnew_group;
	}

	public void setCreatenew_group(String createnew_group) {
		this.createnew_group = createnew_group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Object> getGroupDetail() {
		return groupDetail;
	}


	public void setGroupDetail(List<Object> groupDetail) {
		this.groupDetail = groupDetail;
	}


	
	
}
