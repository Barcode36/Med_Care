package com.Over2Cloud.service.clientdata.integration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;


public class ClientRequestHandler extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @return the revertMsg
	 */
	
	private HttpServletRequest request;
	private HttpServletResponse response;
    
    public String execute()
     {
       
        String mobileNumber = request.getParameter("to");
        String txt = request.getParameter("text");
        String mode = request.getParameter("mode");
        if(mobileNumber != null && mobileNumber.length() == 10) {
          //Varify number...
           mobileNumber = new ClientRequestHandlerHelper().correctMobileNo(mobileNumber);
//         insert into msg_stats table..
           addMessage("NA",mode.toUpperCase(),mobileNumber, txt,"","Pending", "0",mode.toUpperCase());
           response = ServletActionContext.getResponse();
           response.setStatus(1201);
           addActionMessage("SMS Insert in database Successfully!!!");
        }
        return SUCCESS;
    }
    
    public boolean addMessage(String name, String dept,String mobone, String msg, String ticketno, String status, String statusflag, String module)
	 {
		boolean flag = false;
		@SuppressWarnings("unused")
		String returnResult= ERROR;
		Session connection = new ConnectionHelper().getSession("IN-1");
			try {
			        CommonOperInterface cot = new CommonConFactory().createInterface();
			        TableColumes ob1 = new TableColumes();
			        /*List <TableColumes> TableColumnName=new ArrayList<TableColumes>();
			
					ob1.setColumnname("name");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("dept");
					ob1.setDatatype("varchar(80)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("mobno");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
			
					ob1 = new TableColumes();
					ob1.setColumnname("msg_text");
					ob1.setDatatype("varchar(250)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("flag");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("module");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("user");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("update_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
			        cot.createTable22("instant_msg",TableColumnName,connection);*/
			        
					
				   	 InsertDataTable ob=new InsertDataTable();
				     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					 ob.setColName("name");
					 ob.setDataName(name);
					 insertData.add(ob);
					
					 ob=new InsertDataTable();
					 ob.setColName("dept");
					 ob.setDataName(dept);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("mobno");
					 ob.setDataName(mobone);
					 insertData.add(ob);
				 
					 ob=new InsertDataTable();
					 ob.setColName("msg_text");
					 ob.setDataName(msg);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("flag");
					 ob.setDataName(statusflag);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName(status);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("module");
					 ob.setDataName(module);
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("user");
					 ob.setDataName("prashant");
					 insertData.add(ob);
					 
					 
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_date");
					 ob.setDataName("0000-00-00");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("update_time");
					 ob.setDataName("00-00-00");
					 insertData.add(ob);
			 
			       //  cot.insertIntoTable("instant_msg",insertData,connection);
			         cot.insertIntoTableWithSession("instant_msg", insertData, connection);
			         
			         insertData.clear();
			         
			flag =true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return flag;
	}
	
    
    /**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
