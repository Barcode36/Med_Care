package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackCommonPropertyMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		private String key;
		public  Map<String,String> valueMap;
		public List<ConfigurationUtilBean> producttdetails = null;
		List<GridDataPropertyView> CRMColumnNames;
		
		public List<GridDataPropertyView> getGridColomnNamesNegative()
	    {
	    	CRMColumnNames=new ArrayList<GridDataPropertyView>();

	    	GridDataPropertyView gpv=new GridDataPropertyView();
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("ticketNo");
			gpv.setHeadingName("Ticket No");
			gpv.setFrozenValue("false");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setFrozenValue("false");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("openDate");
			gpv.setHeadingName("Open Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("openTime");
			gpv.setHeadingName("Open Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("escalationDate");
			gpv.setHeadingName("Esc Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("escalationTime");
			gpv.setHeadingName("Esc Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			CRMColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("clientId");
			gpv.setHeadingName("Patient Id");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("clientName");
			gpv.setHeadingName("Patient Name");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("centreCode");
			gpv.setHeadingName("Room No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("problem");
			gpv.setHeadingName("Observation");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("mobNo");
			gpv.setHeadingName("Mobile No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("emailId");
			gpv.setHeadingName("Email");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("refNo");
			gpv.setHeadingName("Reference No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("centreName");
			gpv.setHeadingName("Purpose Of Visit");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("complaintType");
			gpv.setHeadingName("Type");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("mode");
			gpv.setHeadingName("Mode");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("remarks");
			gpv.setHeadingName("Remarks/Complaint");
			gpv.setAlign("center");
			gpv.setWidth(100);
			CRMColumnNames.add(gpv);
			
			return CRMColumnNames;
	    }
		
		public Map<String, List<ConfigurationUtilBean>> commonMap = null;
		{	
			commonMap = new HashMap<String, List<ConfigurationUtilBean>>();
			producttdetails = new ArrayList<ConfigurationUtilBean>();
			try{
				
				String feedType=null;
				if(session.containsKey("feedType"))
				{
					feedType=(String)session.get("feedType");
				}
				
				
				// Do the Hardcoded things here if not from mapping table
				if(feedType!=null && feedType.equalsIgnoreCase("No"))
				{
					List<GridDataPropertyView> basicfieldNames=getGridColomnNamesNegative();
					int l=0;
					for (GridDataPropertyView col : basicfieldNames)
					{
						ConfigurationUtilBean objdata=new ConfigurationUtilBean();
					    if(col!=null)
					    {
						    if(l<basicfieldNames.size()-1)
						    {
							    {
							    	objdata.setKey(col.getColomnName());
						    		objdata.setValue(col.getHeadingName());
						    		if(col.getMandatroy()==null)
						    		{
						    			objdata.setMandatory(false);
						    		}
						    		else if(col.getMandatroy().equalsIgnoreCase("0"))
						    		{
						    			objdata.setMandatory(false);
						    		}
						    		else if(col.getMandatroy().equalsIgnoreCase("1"))
						    		{
						    			objdata.setMandatory(true);
						    		}
						    		producttdetails.add(objdata);
							    }
							}
						    else
						    {
						    	
						    }
					    }
					    l++;
					}
				}
				else
				{
					
					// Do the Dynamic things here if not from mapping table
					List<GridDataPropertyView> basicfieldNames=Configuration.getColomnfieldList("mapped_feedback_configuration", accountID, connectionSpace,"feedback_data_configuration");
					basicfieldNames.addAll(Configuration.getColomnfieldList("mapped_feedback_paperform_rating_configuration", accountID, connectionSpace,"feedback_paperform_rating_configuration"));
					//basicfieldNames.addAll(Configuration.getColomnfieldList("mapped_feedback_paperform_rating_configuration_opd", accountID, connectionSpace,"feedback_paperform_rating_configuration_opd"));
					int l=0;
					for (GridDataPropertyView col : basicfieldNames)
					{

						ConfigurationUtilBean objdata=new ConfigurationUtilBean();
					    if(col!=null) {
						    if(l<basicfieldNames.size()-1){
						    	if(col.getMandatroy()!="0"){
						    		objdata.setKey(col.getColomnName());
						    		objdata.setValue(col.getHeadingName());
						    		if(col.getMandatroy()==null)
						    			objdata.setMandatory(false);
									else if(col.getMandatroy().equalsIgnoreCase("0"))
										objdata.setMandatory(false);
									else if(col.getMandatroy().equalsIgnoreCase("1"))
										objdata.setMandatory(true);
						    		   producttdetails.add(objdata);
						    		}
							    	else{
							    		objdata.setKey(col.getColomnName());
							    		objdata.setValue(col.getHeadingName());
							    		if(col.getMandatroy()==null)
							    			objdata.setMandatory(false);
										else if(col.getMandatroy().equalsIgnoreCase("0"))
											objdata.setMandatory(false);
										else if(col.getMandatroy().equalsIgnoreCase("1"))
											objdata.setMandatory(true);
							    		   producttdetails.add(objdata);
							    		}
							        }
						    else{
						    	objdata.setKey(col.getColomnName());
					    		objdata.setValue(col.getHeadingName());
					    		if(col.getMandatroy()==null)
					    			objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("0"))
									objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("1"))
									objdata.setMandatory(true);
					    		   producttdetails.add(objdata);
						    	 }}
					    l++;
				      
					}
					
				}
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		   
			commonMap.put("productdetails",producttdetails);
      		}
	
	

		   public String getKey() {
			   
	            return key;
	    }

	    public void setKey(String key) {
	            this.key = key;
	    }

	    public List<ConfigurationUtilBean> getValueMap() {
	 
            return commonMap.get(key);
            
         }

         public List<ConfigurationUtilBean> getTitles(String key) {
            return commonMap.get(key);
         }




}
