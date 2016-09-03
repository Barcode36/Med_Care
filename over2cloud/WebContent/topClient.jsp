<%@taglib prefix="s" uri="/struts-tags"%>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>
<%
//Code for checking the level of departments or sub departments
String dbName=(String)session.getAttribute("Dbname");
int levelid=1;
String namesofDepts[]=new String[3];
String names=(String)session.getAttribute("deptLevel");
String tempnamesofDepts[]=null;
////deptlevel,dept1Name#dept2Name#
if(names!=null && !names.equalsIgnoreCase(""))
{
	tempnamesofDepts=names.split(",");
	levelid=Integer.parseInt(tempnamesofDepts[0]);
	namesofDepts=tempnamesofDepts[1].split("#");
}

String userTpe=(String)session.getAttribute("userTpe");
//System.out.println("Valid Appss on top client::: "+validApp);
%>

<%@page import="org.hibernate.usertype.UserType"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Case</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="css/responsive.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
  <script src="js/jquery-1.9.1.js"></script>
  <script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="<s:url value="/js/common/topclient.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mainconfiguration/mainconfig.js" ></script>
	
</head>
<body onload="getNotify();">
<div class="logo1" id="PageRefresh" style="float: left;">
   <img src="<s:property value="%{orgImgPath}" />" width="180px" height="65px" />
</div>
<div id="serverTimeDiv"
	style="float: left; padding-top: 20px; padding-left: 35px;font-size: 20px; vertical-align:middle; font-family:Arial, Sans-Serif; font-weight:normal; color:#000; "></div>

<div id="notificationDiv" class="notificationRes" style="display: none;"><center><b>Notification bar loading</b><img id="indicator2" src="images/notificationloader.gif"/></center></div>

<div class="wrap_nav">
<ul class="nav_links">
	<li><a href="javascript:void();">
	 <FONT style="font-size: 12px; font-family: Arial, Sans-Serif; font-weight: normal; color: #000; font-weight: bold;"><B><s:property
		value="#session['empName']" /></B></FONT>&nbsp;&nbsp;
		<s:if test="%{profilePicName == null || profilePicName == ''}">
				<img src="images/noImage.JPG" width="20px" height="20px"/>
			</s:if>
			<s:else>
				<img src="<s:property value="%{profilePicName}" />" width="20px" height="20px"/>
			</s:else>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="two_column">
	<ul>
		<s:if test="%{profilePicName == null || profilePicName == ''}">
			<img src="images/noImage.JPG" width="120px" height="120px" />
			
		</s:if>
		<s:else>
			<img src="<s:property value="%{profilePicName}" />" width="120px" height="130px" />
		</s:else>
	</ul>
	</div>
	<div class="two_column">
	<ul>
 
		<li><a href="javascript:void();"><s:property
			value="#session['empName']" /></a></li>
		<li>Dept: <s:property value="%{empDept}" /></li>
		<li>Type: <s:property value="%{userType}" /></li>
		<li><a href="javascript:void();" id="myaccount">My Account</a></li>
		<li><a href="<s:url action='logout'/>"><font color="blue">Sign Out</font></a></li>
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>
<div class="clear"></div>
<!-- Header Start Point -->
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <!--<a class="navbar-brand" href="#">WebSiteName</a>-->
    </div>
    
    <div class="collapse navbar-collapse" id="myNavbar">
    <ul class="nav navbar-nav">
    <li class="active"><a href="mainFrame.action" style="padding: 16px 13px 8px 15px;"><img alt="" src="images/home_icon.png" height="26px;"></a></li>
	<%if(userRights.contains("dashboard_VIEW")){ %>
	<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">Dashboard<span class="caret"></span></a>
	<ul class="dropdown-menu">
													          
													     
													     		<%
	if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H") && !userRights.contains("CPSDash_VIEW"))
	{ %>
	<li><a href="#" id="" onclick="ticket_normal_newdashboard();forHelpFile('Dashboard_HelpdeskOCC');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Helpdesk(OCC)</a></li>
	<li><a href="#" id="" onclick="locStaff_dashboard();forHelpFile('Dashboard_HelpdeskM');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Helpdesk(M)</a></li>
	<%} %>
	<% if(userRights.contains("ORDDash_VIEW"))
	{%>
	<li><a href="#" id="" onclick="machineOrderDashboard();forHelpFile('Dashboard_MachineOrder');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Machine Order</a></li>
 	<%}%>
	<% if(userRights.contains("REFDash_VIEW"))
	{%>
	<li><a href="#" id="" onclick="referralDashboard();forHelpFile('Dashboard_Referral');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Referral</a></li>
	<%}%>
	<% if(userRights.contains("CRCDash_VIEW"))
	{%>
	<li><a href="#" id="" onclick="criticalDashboard();forHelpFile('Dashboard_CRC');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;CRC</a></li>
	<%}%>
	<% if(userRights.contains("CPSDash_VIEW"))
	{%>
	<li><a  href="#" onclick="cpsDashboard();forHelpFile('Dashboard_CPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;CPS</a></li>
	<%}%>											     
	<% if(userRights.contains("Pharma_Dashboard_VIEW"))
	{%>
	<li><a  href="#" onclick="pharmacyDashboard();forHelpFile('Dashboard_Pharma');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Pharmacy</a></li>
	<%}%>													     
	</ul>
	</li>
	<%}%>
	<% if(validApp.contains("HDM")){ %>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Helpdesk<span class="caret"></span></a>
    <ul class="dropdown-menu">
    <%if(userRights.contains("actBoard_VIEW")) 
	{%>
	<li><a href="#" id="viewActivityBoard" onclick="forHelpFile('Helpdesk_ActivityBoard');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	<%}%> 
	<%if(userRights.contains("mapRsc_VIEW")) 
	{%>    
	<li><a href="#" id="emp_wing_Mapping2" onclick="forHelpFile('Helpdesk_MapResources');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Map Resources</a></li>
	<%}%>  
	<%if(userRights.contains("product_VIEW")) 
	{%>  
	<li><a href="#" onclick="getProductivty('HDM','Employee');forHelpFile('Helpdesk_Productivity');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Productivity</a></li>
	<%}%>
    </ul>
    </li>
    <%}%>
	
	<% if(validApp.contains("ORD")){ %>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Order Request<span class="caret"></span></a>
    <ul class="dropdown-menu">
	<%if(userRights.contains("actBoardOrd_VIEW")) 
	{%>
	<li><a href="#" onclick="MachineorderRequestView();forHelpFile('OrderRequest_ActivityBoard');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	<%}%> 
	<%if(userRights.contains("mapResOrd_VIEW")) 
	{%>    
	<li><a href="#" onclick="MachineEmpMap();forHelpFile('OrderRequest_MapResources');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Map Resources</a></li>
	<%}%> 
 
	 </li>
	 </ul>
    <%}%>
    
      
       
    <% if(validApp.contains("CRF")){ %>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Referral<span class="caret"></span></a>
    <ul class="dropdown-menu">
	<%if(userRights.contains("actBoardRef_VIEW")) 
	{%>
	<li><a href="#"  onclick="referralView();forHelpFile('Referral_ActivityBoard');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Activity&nbsp;Board</a></li>
	<%}%> 
	</li>
	</ul>
    <%}%>
    
    
    <% if(validApp.contains("CPS")){%>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Corporate Patient<span class="caret"></span></a>
    <ul class="dropdown-menu">
	<%if(userRights.contains("actBoardOrdCPS_VIEW")) 
	{%>
	<li><a href="#" onclick="CorporatePatientActivityBoard();forHelpFile('CorporatePatient_ActivityBoard');"><span class="glyphicon glyphicon-certificate"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	<%}%> 
	<%if(userRights.contains("cpsMapResource_VIEW")) 
	{%>
	<li><a href="#" onclick="CpsEmpMap();forHelpFile('CorporatePatient_MapResources');"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;Map Resources</a></li>
	<%}%> 
	<%if(userRights.contains("cpsRoster_VIEW")) 
	{%>
	<li><a href="#" onclick="CpsRotaMap();forHelpFile('CorporatePatient_Roster');"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;&nbsp;Roster</a></li>
	<%}%> 
	</li>
	</ul>
    <%}%>
    
    <% if(validApp.contains("CRC")){ %>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Critical Patient<span class="caret"></span></a>
    <ul class="dropdown-menu">
	<%if(userRights.contains("actBoardOrdCRC_VIEW") || userTpe.contains("M") ) 
	{%>
	<li><a href="#"  onclick="criticalPatientView();forHelpFile('CriticalPatient_ActivityBoard');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Activity&nbsp;Board</a></li>
	<%}%> 
	<%if(userRights.contains("actBoardMapCRC_VIEW") || userTpe.contains("M") ) 
	{%>
	<li><a href="#" onclick="criticalMapResource();forHelpFile('CriticalPatient_MapResources');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Map Resources</a></li>
	<%}%>
	</li>
	</ul>
    <%}%>
	   <%if(userRights.contains("LongStay")) 
	{%>
     
     
     
       <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Long Time Stay<span class="caret"></span></a>
    <ul class="dropdown-menu">
	
	<%if(userRights.contains("activityBrd")) 
	{%>
	
	<li><a href="#" onclick="viewLongPatientStay();"><span class="glyphicon glyphicon-certificate"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	 <%}%> 
	 <%if(userRights.contains("addTicketMap")) 
	{%>
	 <li><a href="#" id="emp_Floor_Mapping_newad" onclick=""><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Map Floor Manager</a></li>
	 <%}%> 
	
	 
	</li> 
	</ul>
     <%}%>    	
	<%if(userRights.contains("LabOrder") ) 
	{%>
   
     <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lab Order<span class="caret"></span></a>
    <ul class="dropdown-menu">
	
	<li><a href="#" onclick="viewLabOrder();"><span class="glyphicon glyphicon-certificate"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	 
	 <li><a href="#" id="" onclick="labOrderViewEscalation();"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Manage Escalation</a></li>
	
	
	 
	</li> 
    </ul>
   <%}%> 
     <%if(userRights.contains("LongStay")) 
	{%>
   
     <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Bed Transfer<span class="caret"></span></a>
    <ul class="dropdown-menu">
	
	<li><a href="#" onclick="beforeViewBedTracking();"><span class="glyphicon glyphicon-certificate"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	 
	 <li><a href="#" id="" onclick="bedTrackingEscalation();"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Manage Escalation</a></li>
	
	
	 
	</li> 
    </ul>
   <%}%> 
   <%if(userRights.contains("LongStay")) 
	{%>
   
     <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Discharge Announce<span class="caret"></span></a>
    <ul class="dropdown-menu">
	
	<li><a href="#" onclick="beforeViewDischageAnnounced();"><span class="glyphicon glyphicon-certificate"></span>&nbsp;&nbsp;&nbsp;Activity Board</a></li>
	 
	</li> 
    </ul>
   <%}%> 
	<%if(validApp.contains("CS"))
	{ %>
	<%if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H")){ %>
	<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin<span class="caret"></span></a>
	<ul class="dropdown-menu">
	<%if(userRights.contains("orgn")){%>
	<li><a href="#" id="organizationView" onclick="forHelpFile('Admin_Organization');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Organization</a></li>
	<%}%>
	<%if(userRights.contains("empl_VIEW"))
	{%>
	<li><a href="#" id="contactMaster_add" onclick="forHelpFile('Admin_PrimaryContact');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Primary Contact</a></li>
	<%} %>
	<%if(userRights.contains("user_VIEW"))
	{%>
	<li><a href="#" id="userView" onclick="forHelpFile('Admin_User');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;User</a></li>
	<%} %>
	<%if(userRights.contains("coma_VIEW"))
	{%>
	<li><a href="#" onclick="getContact();forHelpFile('Admin_ManageContact');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Manage&nbsp;Contact</a></li>
	<%} %>
	<%if(userRights.contains("exclus"))
	{%>
	<li><a href="javascript:void();" id="blackListedView" onclick="forHelpFile('Admin_Exclusion');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Exclusion</a></li>
	<%} %> 
	<%if(userRights.contains("rese"))
	{%>
	<li><a href="#" id="resend_sms_email" onclick="forHelpFile('Admin_Communications');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Communications</a></li>
	<%} %>
	<%if(userRights.contains("rpass_VIEW")){%>
	<li><a href="#" id="changePassword" onclick="forHelpFile('Admin_ResetPassword');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Reset Password</a></li>
	<%}%>
	<%if(userRights.contains("desi_VIEW"))
	{%>
	<li><a href="#" id="beforeNewsAlertsView"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Announcements</a></li>
	<%}%>
<%if(userRights.contains("emailConfiguration"))
	{%>
	<li><a href="#" onclick="email_configuration();forHelpFile('Admin_Email_Configuration');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Email Configuration</a></li>
	<%}%>
	<%}%>
	</ul>
	</li>
    <%}%>
        
    <% if(validApp.contains("HDM")){%>
    <%
	if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H"))
	{ %>
    <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Starting Up<span class="caret"></span></a>
    <ul class="dropdown-menu" style="height: 490px !important; overflow: scroll;">
	<li style="background-color: #CC3300;"><a href="#" >Starting Up</a>
	<%if(userRights.contains("orgHrchy_VIEW"))
	{%>
    <li><a href="#" id="BranchOffice_add" onclick="BranchOffice_add();forHelpFile('StartingUp_OrgHierarchy_startup');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Org. Hierarchy</a></li>
    <%}%>
	<%if(userRights.contains("depa"))
	{%>
	<li><a href="#" id="group_add" onclick="forHelpFile('StartingUp_ContactType_startup');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Contact Type</a></li>
	<%}%>
	<%if(userRights.contains("sude"))
	{%>
	<li><a href="#" id="departmentView" onclick="forHelpFile('StartingUp_Department_startup');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Department</a></li>
	<%}%>
	<%if(userRights.contains("wrkHur_VIEW"))
	{%>
	<li><a href="#" id="working" onclick="forHelpFile('StartingUp_WorkingHrs_startup');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Working Hrs.</a></li>	
	<%}%>
	<%if(userRights.contains("autoAlrt_VIEW"))
	{%>
	<li><a href="#" id="sms_Draft_View" onclick="forHelpFile('StartingUp_AutoAlerts_startup');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Auto Alerts</a></li>
	<%}%>
	<%if(userRights.contains("moreSet_VIEW"))
	{%>
	<li><a onclick="forHelpFile('StartingUp_MoreSetting_startup');" href="moreSettings.action"  class="moreSettings"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;More Setting</a></li>
	<%}%>
   </li>
	
	<%if(userRights.contains("REFStart")  )
	{ %>
 	<li style="background-color: #CC3300;"><a href="#" >Referral</a>
	<li><a href="#"  onclick="referralEscalation();forHelpFile('StartingUp_ManageEscalation_masterReferral');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Manage Escalation</a></li>
	</li>
 	<%}%>
	
	<%if(userRights.contains("StartCRC"))
	{ %>
	<li style="background-color: #CC3300;"><a href="#">Critical Result</a>
   	<li><a href="#" onclick="addTestType(); forHelpFile('StartingUp_TestTypeMaster_masterCriticalResult');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Test Type Master</a></li>
	<li><a href="#" onclick="addTestName(); forHelpFile('StartingUp_TestNameMaster_masterCriticalResult');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Test Name Master</a></li>
	<li><a href="#" onclick="criticalEscalation(); forHelpFile('StartingUp_CriticalEscalation_masterCriticalResult');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Critical Escalation</a></li>
	</li>
 	<%}%>
	<li style="background-color: #CC3300;"><a href="#" >Helpdesk</a>
	<%if(userRights.contains("tick_VIEW")){ %>
	<li><a href="#"  onclick="getConfigureTicket('HDM');forHelpFile('StartingUp_HelpdeskTicketSeries_masterHelpdesk');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Ticket Series</a></li>
	<%}%>
	<%if(userRights.contains("mapLoc_VIEW")){ %>
	<li><a href="#" id="floor_add_action" onclick="forHelpFile('StartingUp_MapLocation_masterHelpdesk');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Map Location</a></li>
	<%}%>
	<%if(validApp.contains("HDM") && userRights.contains("sude"))
	{%>
	<li><a href="#" id="subDepartmentView" onclick="forHelpFile('StartingUp_DefineSkillset_masterHelpdesk');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Define Skillset</a></li>
	<%}%>
	<%if(userRights.contains("cota_VIEW")){ %>
	<li><a href="#"  onclick="getConfigureTask('SD','HDM');forHelpFile('StartingUp_HelpdeskConfigureFeedback_masterHelpdesk');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Configure Feedback</a></li>
	<%}%>
	<%if(userRights.contains("manageEsc_VIEW")){ %>
	<li><a href="#" id="esc_dept_config_action_view" onclick="forHelpFile('StartingUp_HelpdeskManageEscalation_masterHelpdesk');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Manage Escalation</a></li>
	<%} %>
	<%if(userRights.contains("reason_VIEW")){ %>
	<li><a href="#" id="task_type" onclick="forHelpFile('StartingUp_HelpdeskReason_masterHelpdesk');"><span class="glyphicon glyphicon-erase"></span>&nbsp;&nbsp;&nbsp;Reason</a></li>
	<%} %>
	<%if(userRights.contains("moreSet_VIEW")){ %>
	<li><a onclick="forHelpFile('StartingUp_HelpdeskMoreSetting_masterHelpdesk');" href="moreSettingsHDM.action"  class="moreSettings"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;More Setting</a></li>
	<%} %>
	 <!--  <%if(true){ %>
	<li><a href="#"  onclick="getSeverity('HDM')">Severity Level</a></li>  
	<li><a href="#"  onclick="viewConfigureCheckList()">Completion Tip</a></li>
	<%}%>-->  
	</li>
	
	
	<!-- CPS MASTER Created By Faisal -->	
	<%if(userRights.contains("cpsStart"))
	{ %>				 
	
	<li style="background-color: #CC3300;"><a href="#" >CPS</a>
	<%if(userRights.contains("cpsCorpData_VIEW")) 
	{%>
	<li><a href="#" onclick="corp_package_view();forHelpFile('StartingUp_CPSCorporateData_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Corporate Data</a></li>
	<%}%> 
	<%if(userRights.contains("cpsManageEsc_VIEW")|| true) 
	{%>
	<li><a href="#"  onclick="CpsEscalation(); forHelpFile('StartingUp_CPSManageEscalation_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Manage Escalation</a></li>
	<%}%> 
	<%if(userRights.contains("cpsServiceMaster_VIEW")|| true) 
	{%>
	<li><a href="#" onclick="service_addMaster(); forHelpFile('StartingUp_CPSServiceMaster_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Service&nbsp;Master&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	<%}%> 
	<%if(userRights.contains("cpsStanderdMaster_VIEW")|| true) 
	{%>
	<li><a href="#" onclick="standered_package(); forHelpFile('StartingUp_CPSStandardPackage_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Standard&nbsp;Package&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	<%}%> 
	<li><a href="#" onclick="location_name(); forHelpFile('StartingUp_CPSLocation_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Location</a></li>
	<li><a href="#" id="bed_type_link" onclick="bed_type_add(); forHelpFile('StartingUp_CPSBedType_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Bed Type</a></li>
	<li><a href="#" onclick="pay_type_add(); forHelpFile('StartingUp_CPSPaymentType_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Payment Type</a></li>
	<li><a href="#" onclick="modiality_add(); forHelpFile('StartingUp_CPSModiality_masterCPS');"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Modiality</a></li>
	<!-- <li><a href="#" onclick="corporate_add();">Corporate&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> 
	<li><a href="#" onclick="speciality_addMaster();">Speciality&nbsp;Master&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	<li><a href="#" onclick="USGServices_addMaster();">USG&nbsp;&&nbsp;Lab&nbsp;Services&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> 
	<li><a href="#" onclick="configureShift('SD','CPS')"> Shift</a></li>
	<li><a href="#" onclick="getRoaster('SD','CPS')">Roaster</a></li>-->
	</li>
	<%}%> 
	
	 
	<% if(userRights.contains("ORDStart") )
	{%>
	<li style="background-color: #CC3300;"><a href="#"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Machine Order</a>
	<li><a href="javascript:void();" id="machineMaster"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Machine</a></li>
	<li><a href="javascript:void();" onclick="machineOrderTypeMaster();"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Machine Type</a></li>
	<li><a href="#" onclick="esc_dept_config_action_productorder();"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Escalation</a></li>
	<li><a href="javascript:void();" id="reasonName"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Reason</a></li>
	<li><a href="moreSettingsORD.action"  class="moreSettings"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;More Setting</a></li>
<li><a href="#" onclick="pharmacyAlert();"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;Pharmacy Alert</a></li>
 	<li><a href="#" onclick="high_cost_med1();"><span class="glyphicon glyphicon-scale"></span>&nbsp;&nbsp;&nbsp;High Cost Medicine Alert</a></li>
	</li>
	<%}%>	 		
	 
	</li>
	</ul>
	<%}%>
    <%}%>
	
    </div>
  </div>
</nav>
</body>
</html>