package com.Over2Cloud.ctrl.feedback.adminDept;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.subdepartmen.SubDepartmentAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminDeptAction extends ActionSupport{

	static final Log log = LogFactory.getLog(AdminDeptAction.class);
	
	Map session = ActionContext.getContext().getSession();
    //HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    String userName=(String)session.get("uName");
    String accountID=(String)session.get("accountid");
    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
    
    private String idList;
    
    private String id;
    private String dept_Name;
    private String userName1=userName;
    private String allote_date;
    private String allote_time;
	
	public String beforeDepartment()
    {
	   System.out.println("Ticket id for Dept>>>>>>>>>>"+idList);
	   System.out.println("UserName>>>>>>>>>>"+userName1);
	   List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	    boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
	        {
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				
				
                	 Tablecolumesaaa.clear();
                     // Creating table Admin_Dept_info
                	 TableColumes ob1=new TableColumes();
                     ob1=new TableColumes();
                     ob1.setColumnname("admin_dept_id");
                     ob1.setDatatype("varchar(255)");
                     ob1.setConstraint("default NULL");
                     Tablecolumesaaa.add(ob1);
                     
                     /*TableColumes ob2=new TableColumes();
                     ob2.setColumnname("dept_Name");
                     ob2.setDatatype("varchar(255)");
                     ob2.setConstraint("default NULL");
                     Tablecolumesaaa.add(ob2);*/
                     
                     TableColumes ob3=new TableColumes();
                     ob3.setColumnname("userName");
                     ob3.setDatatype("varchar(255)");
                     ob3.setConstraint("default NULL");
                     Tablecolumesaaa.add(ob3);
                    
                     TableColumes ob4=new TableColumes();
                     ob4.setColumnname("allote_date");
                     ob4.setDatatype("varchar(255)");
                     ob4.setConstraint("default NULL");
                     Tablecolumesaaa.add(ob4);
                    
                     TableColumes ob5=new TableColumes();
                     ob5.setColumnname("allote_time");
                     ob5.setDatatype("varchar(255)");
                     ob5.setConstraint("default NULL");
                     Tablecolumesaaa.add(ob5);
                     
                     boolean tableCreated=cbt.createTable22("Admin_Dept_info",Tablecolumesaaa,connectionSpace);
                     
                     System.out.println("Admin_Dept_info table Created successfully>>>>>>>>>"+tableCreated);
                     boolean status = false;
					if(tableCreated)
                     {
                    		List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();	 
                            InsertDataTable Idt=null;
                            Idt=new InsertDataTable();
                            
                            Idt.setColName("admin_dept_id");
                            Idt.setDataName(idList);
                            insertHistory.add(Idt);
                            
                            /*Idt.setColName("dept_Name");
                            Idt.setDataName(dept_Name);
                            insertHistory.add(Idt);*/
                           
                            Idt=new InsertDataTable(); 
                            Idt.setColName("userName");
                            Idt.setDataName(userName1);
                            insertHistory.add(Idt);
                          
                            Idt=new InsertDataTable();
                            Idt.setColName("allote_date");
                            Idt.setDataName(DateUtil.getCurrentDateUSFormat());
                            insertHistory.add(Idt);
                            
                            Idt=new InsertDataTable();
                            Idt.setColName("allote_time");
                            //Idt.setDataName(allote_time);
                            Idt.setDataName(DateUtil.getCurrentTime());
                            insertHistory.add(Idt);
                            //remove dublicacySSS
                            int maxId=cbt.getMaxId("Admin_Dept_info", connectionSpace);
                           
                            if(maxId==0)
                            {
                            	status=cbt.insertIntoTable("Admin_Dept_info",insertHistory,connectionSpace);
                            	if(status)
               				 {
               					 addActionMessage("Deparment Alloted Succesfully!!!!!!!!");
               					 return SUCCESS;
               				 }
               				 else
               				 {
               					 addActionMessage("Oops Department Alloted Already!!!!!!");
               					 return SUCCESS;
               				 }
                            }
                            else
                            {
                            	 addActionMessage("Oops There is some error in data!");
               					 return SUCCESS;
                            }
                            
                          //  addActionMessage("Data Added Succesfully!!!!!");
                     }
                    
                    
                     
	        }
	        catch(Exception e)
	        {
				// e.printStackTrace();
				 //return ERROR;
				 addActionError("Oops There is some error in data!");
                 return ERROR;
	        }
	       
		}
		else {
			return LOGIN;
		}
		return accountID;
        
    }
	
	public String beforeAdminDept()
	{
		boolean valid=ValidateSession.checkSession();
		if(!valid)
		{
			return LOGIN;
		}
			
		try
		{
			System.out.println(">>>>>>>>>>>. Inside beforeAdminDept");
		}catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
		
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getDept_Name() {
		return dept_Name;
	}



	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}



	public String getUserName1() {
		return userName1;
	}



	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}



	public String getAllote_date() {
		return allote_date;
	}



	public void setAllote_date(String allote_date) {
		this.allote_date = allote_date;
	}



	public String getAllote_time() {
		return allote_time;
	}



	public void setAllote_time(String allote_time) {
		this.allote_time = allote_time;
	}



	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	
}
