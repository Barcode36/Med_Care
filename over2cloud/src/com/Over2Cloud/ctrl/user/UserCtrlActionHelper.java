package com.Over2Cloud.ctrl.user;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class UserCtrlActionHelper {

	
	static final Log log = LogFactory.getLog(UserCtrlActionHelper.class);
	public static String getLinkRight(HttpServletRequest request) {
		String LinkLabel[] = {"PreMatureLead","LeadWorking","PerspectiveClient","ExisitingClientMaster","AssociateMaster","ExisitingAssociateMaster","SMSReportInOut"
				,"DailySMSReport","MonthlyTarget","KR","user_admin","company_admin","EmployeeAdmin","Hierarchy","Product","KPIMgmt","SMSTemplate","ProcessSetting"
				,"BPMSetting","StatusMaster","AssociateType","associatecatg","sourceMaster","setting","SubDepartment","Designation","Qualification","SMTPConfiguration","NewsEvents"
				,"IndustryMaster","SubIndustryMaster","LocationMaster","KRAActivity","lostMaster","LostMgmt","ComplainMessenger","incentive"
				,"communicationMaster","groupMaster"
		//for MHDM
				,"dashboard","dept_wise","graphic","via_online","via_call","fresolution","fdraft","compliance"
		};
		StringBuffer linkRights = new StringBuffer();

		for (String link : LinkLabel) {
			

			if (request.getParameter(link) != null) {
				linkRights.append(request.getParameter(link) + "#");
			}

		}
		return linkRights.toString();
	}
	
	public static  String getSubLinkRight(HttpServletRequest request) {
		String subLinkLabel[] = {
				//Lead Generation
				"PMA","PMV","PMD","LWA","LWV","LWD"
				//Cleiint
				,"PCSMA","PCSMV","PCSMD","PCSMM","PCSMR","ECSMV","ECSMD","ECSMM","ECSMR"
				//Associate
				,"ASMA","ASMV","ASMD","ASMM","ASMR","ASMMapping","EASMV","EASMD","EASMM","EASMR","EASMMapping"
				//Report
				,"SMSRTV","SMSRTDownload","DSRV","DSRDownload"
				//target
				,"META","METE","METV","METDownload"
				//KR
				,"KRA","KRV","KRD","KRSearch"
				//admin
				,"UAA","UAV","UAM","UAD","UADownload","CAA","CAV","CAM","CAD","CADownload"
				//HR
				,"EAA","EAV","EAM","EAD","EADownload","EReport","HIA"
				//Product
				,"ProductA","ProductV"
				//SFA Setting
				,"SMSTA","SMSTV","SMSTM","SMSTD","SMSTDownload"
				,"PSA","PSV","PSD","BPMA","BPMV","BPMD","SourceMA","SourceMV","SourceMM","SourceMD","ATA","ATV","ATD","ACA","ACV","ACD","SMA","SMV","SMM","SMD","ACM","ATM","SMM"
				//Setting
				,"SA","SV","SM","SD","subDA","subDV","subDM","subDD","desgA","desgV","desgM","desgD","quaA","quaV","quaM","quaD"
				,"SMTPA","SMTPV","SMTPD","SMTPM","NewsEventsA","NewsEventsV","IMA","IMV","IMD","SIMA","SIMV","SIMD","LMA","LMV","LMD","IMM","SIMM","LMM"
				//productivty mgmt
				,"KPIA","KPIV","KPIM","KPID","KPIDownload","KPIReport"
				,"KRAActivityA","KRAActivityV","KRAActivityM","KRAActivityD","KRAActivityDownload","KRAActivityReport"
				,"ComplainMessengerA","ComplainMessengerV","ComplainMessengerDownload","ComplainMessengerReport"
				//lost master
				,"LostMgmtV","LostMgmtTA"
				//lost mgmt
				,"lostMA","lostMV","lostMM","lostMD"
				//incenitve
				,"incentiveAdd","incentiveView","incentiveDelete"
				//comunication
				,"communicationInstant","communicationSchedule","communicationInstantReport","communicationScheduleReport"
				//group
				,"groupCreate","groupAdd","groupView"
				
				//for MHDM
				,"DA","VV","DM","DD","DWA","DWV","DWM","DWD","GV","VOA","VOV","VCA","VCV","FRP","FRS","FRHP","FDA","FDV","FDM","FDD","CA","CV"
				,"CM","CD"
		};

		StringBuffer subLinkRights = new StringBuffer();

		for (String subLink : subLinkLabel) {
			
			if (request.getParameter(subLink) != null
					&& request.getParameter(subLink).equals("true")) {
				subLinkRights.append(subLink + "#");
			}

		}
		return subLinkRights.toString();
	}
	
	public static String checkProductUser(String getProductId,String accountID,SessionFactory connectionSpace)
	{
		String status=new String();
		try
		{
			//check for the no of the users.
			String selectedProductIds=new String();
			Map<String,Object>currentUserCountPerApp=new HashMap<String, Object>();
			Map<String,String>purchasedUserCountPerApp=new HashMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			if(getProductId!=null)
			{
				String tempProduct[]=getProductId.split(",");
				String selectedProductsCodeList=new String();
				int i=1;
				for(String H:tempProduct)
				{
					if(!H.equalsIgnoreCase(""))
					{
						if(i < tempProduct.length)
							
							selectedProductIds=selectedProductIds+"'"+H+"',";
						else
							selectedProductIds=selectedProductIds+"'"+H+"'";
						
						selectedProductsCodeList=selectedProductsCodeList+H+"#";
						i++;
						StringBuilder getIdsOfProduct=new StringBuilder("");
						StringBuilder query=new StringBuilder("");
						getIdsOfProduct.append("select id from apps_details where App_code='"+H+"'");
						String tempH=null;
						List  dataTemp=cbt.executeAllSelectQuery(getIdsOfProduct.toString(),connectionSpace);
						if(dataTemp!=null)
						{
							for(Iterator it=dataTemp.iterator(); it.hasNext();)
							{
								Object obdata=(Object)it.next();
								tempH=obdata.toString();
							}
						}
						query.append("select count(*) from useraccount where userForProductId like '%"+tempH+"%'");
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(data!=null)
						{
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object obdata=(Object)it.next();
								BigInteger count=new BigInteger("3");
								count=(BigInteger)obdata;
								currentUserCountPerApp.put(H, count);
							}
						}
					}
				}
				if(!selectedProductIds.equalsIgnoreCase("") && selectedProductIds!=null)
				{
					
					List<String>appListName=new ArrayList<String>();
					appListName.add("productCategory");
					appListName.add("productuser");
					appListName=cbt.viewAllDataEitherSelectOrAll("client_product",appListName,connectionSpace);
					String productList=new String();
					String productUserList=new String();
					if(appListName.size()>0)
					{
						for (Object c : appListName) {
							Object[] dataC = (Object[]) c;
							if(dataC!=null)
							{
								productList=dataC[0].toString();
								productUserList=dataC[1].toString();
							}
						}
						String selectedProductsCodes[]=selectedProductsCodeList.split("#");
						String purchasedProductCodes[]=productList.split("#");
						String purchasedProductUserNo[]=productUserList.split("#");
						for(int k=0;k<purchasedProductCodes.length;k++)
						{
							for(int j=0;j<selectedProductsCodes.length;j++)
							{
								if(selectedProductsCodes[j].equals(purchasedProductCodes[k]))
								{
									purchasedUserCountPerApp.put(purchasedProductCodes[k], purchasedProductUserNo[k]);
									break;
								}
							}
						}
						/*for (Map.Entry<String, Object> entry : currentUserCountPerApp.entrySet()) {
						    System.out.println("currentUserCountPerApp  Key = " + entry.getKey() + ", Value = " + entry.getValue());
						}
						System.out.println("---------------------------------------------------------");
						for (Map.Entry<String, String> entry : purchasedUserCountPerApp.entrySet()) {
						    System.out.println("purchasedUserCountPerApp  Key = " + entry.getKey() + ", Value = " + entry.getValue());
						}
						System.out.println("*********CHECKING FOR NO OF USERS PER APP**************");*/
						List currentCountKey = new ArrayList(currentUserCountPerApp.keySet());
						List purchasedCountProductKey = new ArrayList(purchasedUserCountPerApp.keySet());
						List currentCountValue = new ArrayList(currentUserCountPerApp.values());
						List purchasedCountValue = new ArrayList(purchasedUserCountPerApp.values());
						for (i = 0; i < currentCountValue.size(); i++) {
							
						    Object currentKey = currentCountKey.get(i);
						    for(int j=0;j<purchasedCountProductKey.size();j++)
						    {
						    	Object purchasedKey = purchasedCountProductKey.get(j);
						    	if(currentKey.toString().equalsIgnoreCase(purchasedKey.toString()))
						    	{
						    		 Object currentKeyValue = currentCountValue.get(i);
							    	 Object purchasedValue = purchasedCountValue.get(j);
						    		 int a=Integer.parseInt(currentKeyValue.toString()); 
							    	 int b=Integer.parseInt(purchasedValue.toString()); 
								     if(a>=b)
								     {
								    	//System.out.println("exceedd");
								    	return status="User Exceeding: "+purchasedKey.toString();
								     }
						    }
						}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method checkProductUser(String getProductId,String accountID,SessionFactory connectionSpace) of class UserCtrlActionHelper", e);
			e.printStackTrace();
		}
		
		return status;
	}
}
