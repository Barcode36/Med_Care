package com.Over2Cloud.ctrl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ContactLoginCtrl extends ActionSupport
{
	static final Log log =LogFactory.getLog(ContactLoginCtrl.class);
	private static CommonOperInterface cbt=new CommonConFactory().createInterface();
	private List<ConfigurationUtilBean> userDD;
	Map<Integer,String> officeMap=null;
	Map<Integer,String> groupMap=null;
	Map<Integer,String> contactMap=null;
	
	public String beforeUserCreate()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				System.out.println("Hello ");
				setUserAddPageDataFields();
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method beforeUserCreate of class "+getClass(), e);
				return "error";
			
			}
		}
		else
		{
			return LOGIN;
		}
	}
	@SuppressWarnings("unchecked")
	public void setUserAddPageDataFields()
	{
		try
		{
    		Map session = ActionContext.getContext().getSession();
    		String accountID=(String)session.get("accountid");
    	    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
    	    List<GridDataPropertyView> gridFields=Configuration.getConfigurationData("mapped_user_account_configuration",accountID,connectionSpace,"",0,"table_name","user_details_configuration");
            System.out.println("gridFields is as "+gridFields.size());
    	    if(gridFields!=null && gridFields.size()>0)
            {
            	userDD=new ArrayList<ConfigurationUtilBean>();
                for(GridDataPropertyView  gdp:gridFields)
                {
                	System.out.println("Col Type is as "+gdp.getColType());
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("D"))
                    {
                    	System.out.println("Col Name is as"+gdp.getColomnName());
                    	
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        userDD.add(objdata);
                        
                        
                        if(gdp.getColomnName().equalsIgnoreCase("groupId"))
                        {
                        	groupMap=new HashMap<Integer, String>();
                        }
                        else if(gdp.getColomnName().equalsIgnoreCase("regLevel"))
                        {
                        	officeMap=new HashMap<Integer, String>();
                        	List data=cbt.executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);
                        	
                        	if(data!=null && data.size()>0 && data.get(0)!=null)
                        	{
                        		String arr[]=data.get(0).toString().split("#");
                        		for (int i = 0; i < arr.length; i++) 
                        		{
                        			officeMap.put(i+1, arr[i].toString());
								}
                        	}
                        }
                        else if(gdp.getColomnName().equalsIgnoreCase("contactName"))
                        {
                        	contactMap=new HashMap<Integer, String>();
                        }
                    }
                }
            
            }
    	    
    	    System.out.println("userDD size is as "+getUserDD().size());
    	    
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method setUserAddPageDataFields of class "+getClass(), e);
		}
	}
	public List<ConfigurationUtilBean> getUserDD() {
		return userDD;
	}
	public void setUserDD(List<ConfigurationUtilBean> userDD) {
		this.userDD = userDD;
	}
	public Map<Integer, String> getOfficeMap() {
		return officeMap;
	}
	public void setOfficeMap(Map<Integer, String> officeMap) {
		this.officeMap = officeMap;
	}
	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}
	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}
	public Map<Integer, String> getContactMap() {
		return contactMap;
	}
	public void setContactMap(Map<Integer, String> contactMap) {
		this.contactMap = contactMap;
	}
}
