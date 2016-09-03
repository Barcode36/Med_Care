<%@taglib prefix="s" uri="/struts-tags"%>
<%
	//Anoop 12-10-2013
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
	System.out.println("userRights}}}}}}"+userRights);
%>
<html>
<head>
<script>
// OUR FUNCTION WHICH IS EXECUTED ON LOAD OF THE PAGE.
function digitized()
 {
     var dt = new Date();    // DATE() CONSTRUCTOR FOR CURRENT SYSTEM DATE AND TIME.
     var hrs = dt.getHours();
     var min = dt.getMinutes();
     var sec = dt.getSeconds();

     min = Ticking(min);
     sec = Ticking(sec);

     document.getElementById('dc').innerHTML = hrs + ":" + min + ":" + sec;
     document.getElementById('dc_second').innerHTML = sec;
     if (hrs > 12) { document.getElementById('dc_hour').innerHTML = 'PM'; }
     else { document.getElementById('dc_hour').innerHTML = 'AM'; }

     var time
     // THE ALL IMPORTANT PRE DEFINED JAVASCRIPT METHOD.
     time = setTimeout('digitized()', 1000);      
 }
</script>
</head>
<body  onload="digitized();">
<div class="top">
<div class="logo1"><a href="index.html"><img src="images/logo.png" /></a></div>
<div style="float: left;padding-top: 16px;">
  	<table style="width:130px" align="center" cellspacing="0" cellpadding="0" border="0" rules='groups'>
            <tr>
                <td class="clock" id="dc" style="height: 8px;"></td>  <!-- THE DIGITAL CLOCK. HH:MM:SS Format-->
                <td>
                    <table cellpadding="0" cellspacing="0" border="0" style="display: none;">
                     	 <tr><td class="clocklg" id="dc_hour"></td></tr>      <!-- HOUR IN 'AM' AND 'PM'.  -->
                         <tr><td class="clocklg" id="dc_second"></td></tr>  <!--  SHOWING SECONDS. -->
                    </table>
                </td>
           </tr>
        </table>
  </div>


<div class="logo"><a href="index.html"><img
	src="images/logo.png" /></a></div>

<div class="search-box" style="display: none;">

<div id="searchbox">

<div
	style="position: absolute; width: 417px; z-index: 100002; display: none; top: 57px; visibility: visible;"
	id="search-form">
<form name="search-form" id="search-form">
<table width="100%" cellspacing="0" cellpadding="2" border="0"
	align="center" class="mailClient mailClientBg">
	<tbody>
		<tr>
			<td>
			<table width="100%" cellspacing="0" cellpadding="2" border="0"
				class="small">
				<tbody>
					<tr>
						<td height="30px" class="mailSubHeader"><b>Select modules
						to search in</b></td>
						<td align="right" class="mailSubHeader"><a
							onclick="UnifiedSearch_SelectModuleToggle(true);"
							href="javascript:void(0);">Select All</a> | <a
							onclick="UnifiedSearch_SelectModuleToggle(false);"
							href="javascript:void(0);">UnSelect All</a> <a
							href="javascript:void(0)"
							onclick="hideDiv('search-form');return false"><img border="0"
							src="images/close.gif"></a></td>
					</tr>
				</tbody>
			</table>

			<table width="100%" cellspacing="0" cellpadding="5" border="0"
				class="small">
				<tbody>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="Accounts" class="small" name="search_onlyin">
						Organizations</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Assets" class="small" name="search_onlyin"> Assets</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Calendar" class="small" name="search_onlyin">
						Calendar</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="Campaigns" class="small" name="search_onlyin">
						Campaigns</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="ConfigEditor" class="small" name="search_onlyin">
						ConfigEditor</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Contacts" class="small" name="search_onlyin">
						Contacts</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="CustomerPortal" class="small" name="search_onlyin">
						CustomerPortal</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Documents" class="small" name="search_onlyin">
						Documents</td>
						<td class="dvtCellLabel"><input type="checkbox" value="Faq"
							class="small" name="search_onlyin"> FAQ</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="HelpDesk" class="small" name="search_onlyin">
						Trouble Tickets</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Invoice" class="small" name="search_onlyin">
						Invoice</td>
						<td class="dvtCellLabel"><input type="checkbox" value="Leads"
							class="small" name="search_onlyin"> Leads</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="MailManager" class="small" name="search_onlyin">
						Mail Manager</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="ModComments" class="small" name="search_onlyin">
						Comments</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="PBXManager" class="small" name="search_onlyin">
						PBX Manager</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="Potentials" class="small" name="search_onlyin">
						Opportunities</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="PriceBooks" class="small" name="search_onlyin">
						Price Books</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Products" class="small" name="search_onlyin">
						Products</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="Project" class="small" name="search_onlyin">
						Projects</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="ProjectMilestone" class="small" name="search_onlyin">
						Project Milestones</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="ProjectTask" class="small" name="search_onlyin">
						Project Tasks</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="PurchaseOrder" class="small" name="search_onlyin">
						Purchase Order</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Quotes" class="small" name="search_onlyin"> Quotes</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="SalesOrder" class="small" name="search_onlyin">
						Sales Order</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="ServiceContracts" class="small" name="search_onlyin">
						Service Contracts</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Services" class="small" name="search_onlyin">
						Services</td>
						<td class="dvtCellLabel"><input type="checkbox"
							value="Vendors" class="small" name="search_onlyin">
						Vendors</td>
					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="VtigerBackup" class="small" name="search_onlyin">
						Over2cloud Backup</td>
						<td class="dvtCellLabel"><input type="checkbox" value="WSAPP"
							class="small" name="search_onlyin"> WSAPP</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td height="30px" align="right" class="mailSubHeader"><input
				type="button" onclick="#" value="Cancel"
				class="crmbutton small cancel"> <input type="button"
				onclick="#" value="Apply" class="crmbutton small create"></td>
		</tr>
	</tbody>
</table>
</form>
</div>


<form id="searchform" action="/search" method="get"><input
	type="text" id="s" name="q" value="" />

<div id="arrow"><a href="javascript:void(0);"
	onclick="showDiv('search-form');return false"><img
	src="images/arrow-down-grey.png" width="8" height="6" /></a></div>
<input type="image" src="images/blank.gif" id="sbutton" /></form>
</div>
</div>

<div class="wrap_nav">
<ul class="nav_links">

	<li><a href="javascript:void();"><img
		src="images/profile-thumb-pic.png" /></a>
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="two_column">
	<ul>
		<li><a href="javascript:void();"><img
			style="border: 1px solid #f0f0f2" src="images/profile-pic.png"
			width="102" height="102" /></a></li>

	</ul>
	</div>
	<div class="two_column">
	<ul>
		<li style="font-weight: bold;"><a href="javascript:void();"><s:property
			value="#session['empName']" /></a></li>
		<li style="font-size: 11px; padding: 0 0 15px 10px; text-align: left;">pravendra.saxena@gmail.com</li>
		<li><a href="javascript:void();" id="myaccountpages">My
		Account</a></li>
		<li><a href="<s:url action='logout'/>">Sign Out</a></li>
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>


	<li><a href="javascript:void();"><img
		src="images/setting-icon.png" /></a>
	<div class="dropdown setting_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="two_column">
	<ul>
		<li><a href="javascript:void();" id="settingpage">All Setting</a></li>
		<li><a href="javascript:void();">CRM Setting</a></li>
		<li><a href="javascript:void();">CRM Setting</a></li>
	</ul>
	</div>
	<div class="two_column">
	<ul>
		<li><a href="javascript:void();">All Setting</a></li>
		<li><a href="javascript:void();">CRM Setting</a></li>
		<li><a href="javascript:void();">CRM Setting</a></li>
	</ul>
	</div>

	<div class="clear"></div>
	</div>
	</li>

	<li><a href="javascript:void();"><img
		src="images/help-icon.png" /></a>
	<div class="dropdown help_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>

	<ul>
		<li><a href="javascript:void();">Any Help</a></li>
		<li><a href="javascript:void();">Any Help</a></li>
		<li><a href="javascript:void();">Any Help</a></li>
	</ul>


	<div class="clear"></div>
	</div>
	</li>

</ul>
<div id="searchbox-2">

<div
	style="position: absolute; width: 237px; z-index: 100002; display: none; top: 56px; margin-left: 9px; visibility: visible;"
	id="search-form-2">
<form name="search-form-2" id="search-form-2">
<table width="100%" cellspacing="0" cellpadding="2" border="0"
	align="center" class="mailClient mailClientBg">
	<tbody>
		<tr>
			<td>
			<table width="100%" cellspacing="0" cellpadding="2" border="0"
				class="small">
				<tbody>
					<tr>
						<td align="right" class="mailSubHeader"><a
							href="javascript:void(0)"
							onclick="hideDiv('search-form-2');return false"><img
							border="0" src="images/close.gif"></a></td>
					</tr>
				</tbody>
			</table>

			<table width="100%" cellspacing="0" cellpadding="5" border="0"
				class="small">
				<tbody>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="Accounts" class="small" name="search_onlyin">
						Organizations</td>

					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="Campaigns" class="small" name="search_onlyin">
						Campaigns</td>

					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="CustomerPortal" class="small" name="search_onlyin">
						CustomerPortal</td>

					</tr>
					<tr valign="top">
						<td class="dvtCellLabel"><input type="checkbox"
							value="HelpDesk" class="small" name="search_onlyin">
						Trouble Tickets</td>

					</tr>
					<tr valign="top">

					</tr>
					<tr valign="top">

					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td height="30px" align="right" class="mailSubHeader"><input
				type="button" onclick="#" value="Cancel"
				class="crmbutton small cancel"> <input type="button"
				onclick="#" value="Apply" class="crmbutton small create"></td>
		</tr>
	</tbody>
</table>
</form>
</div>

<form id="searchform-2" action="/search" method="get"><input
	type="text" id="s" name="q" value="" />
<div id="arrow"><a href="javascript:void(0);"
	onclick="showDiv('search-form-2');return false"><img
	src="images/arrow-down-grey.png" width="8" height="6" /></a></div>
</form>
</div>
</div>
</div>
<div class="clear"></div>
<div class="black">
<ul id="mega-menu" class="mega-menu">
	<li><a href="mainFrame.action"><img src="images/home-icon.png"
		alt="Home" width="16" height="16" title="Home" /></a></li>

	<li><a href="#">Dashboard</a>
	    <ul>
			<li><a href="javascript:void();" id="ticket_normal_newdashboard">HDM</a></li>
			<li><a href="javascript:void();"  id="mgmt_compl_dashboard">Compliance</a></li>

		</ul>
	</li>
	
	<%if(userRights.contains("vica") || userRights.contains("vion")){%>
	<li><a href="#">Task Request</a>
	<ul>
		<%if(userRights.contains("vica_ADD") || userRights.contains("vica_VIEW") || userRights.contains("vica_MODIFY")|| userRights.contains("vica_DELETE")) {%>
			<li><a href="javascript:void();" id="feed_via_call">Via Call</a></li>
		<%} %>
		
		<%if(userRights.contains("vion_ADD") || userRights.contains("vion_VIEW") || userRights.contains("vion_MODIFY")|| userRights.contains("vion_DELETE")) {%>
			<li><a href="javascript:void();" id="feed_via_online">Via Online</a></li>
		<%} %>
	</ul>
	</li>
	<%} %>
	
	<li><a href="#">Action</a>
	<ul>
		<li><a href="javascript:void();" id="feed_action_pending">Pending</a></li>
		<li><a href="javascript:void();" id="feed_action_snooze">Snooze</a></li>
		<li><a href="javascript:void();" id="feed_action_hp">High Priority</a></li>
		<li><a href="javascript:void();" id="feed_action_resolved">Resolved</a></li>
	</ul>
	</li>

	<li><a href="javascript:void();" id="roaster_conf_add">Roaster</a></li>
	<li><a href="javascript:void();" id="feed_draft_view">Configure Task</a></li>

	<li><a href="#">Report</a>
	<ul>
		<li><a href="javascript:void();" id="feed_action_feedreport">Download</a></li>
		<li><a href="javascript:void();" id="daily_report_configurtion_view">Configuration</a></li>
		<li><a href="javascript:void();" id="resend_sms_email">Resend SMS/Email</a></li>
		<!--<li><a href="javascript:void();" id="resend_sms_email">Auto Alerts</a></li>-->
		<li><a href="javascript:void();" id="analytical_report">Analytical Reports</a></li>
	</ul>
	</li>

	<%if(userRights.contains("core") || userRights.contains("depa") || userRights.contains("sude")
					  || userRights.contains("desi") || userRights.contains("empl") || userRights.contains("user")		
					  || userRights.contains("loca") || userRights.contains("sour") ) {%>
	<li><a href="#">Compliance </a>
	<ul>
		<%if(userRights.contains("core_ADD") || userRights.contains("core_VIEW") || userRights.contains("core_MODIFY")|| userRights.contains("core_DELETE")) {%>
			<li><a href="javascript:void();" id="compl_configure">Configure</a></li>
		<%} %>
		<%if(userRights.contains("cvel_ADD") || userRights.contains("cvel_VIEW") || userRights.contains("cvel_MODIFY")|| userRights.contains("cvel_DELETE")) {%>
			<li><a href="javascript:void();" id="compl_configure_excel">Configure Via Excel</a></li>
		<%} %>	
		<%if(userRights.contains("rert_ADD") || userRights.contains("rert_VIEW") || userRights.contains("rert_MODIFY")|| userRights.contains("rert_DELETE")) {%>
			<li><a href="javascript:void();" id="compl_report">Report</a></li>
		<%} %>
		<%if(userRights.contains("taty_ADD") || userRights.contains("taty_VIEW") || userRights.contains("taty_MODIFY")|| userRights.contains("taty_DELETE")) {%>
			<li><a href="javascript:void();" id="view_compl_task_type">Task Type</a></li>
		<%} %>
		<%if(userRights.contains("tana_ADD") || userRights.contains("tana_VIEW") || userRights.contains("tana_MODIFY")|| userRights.contains("tana_DELETE")) {%>
			<li><a href="javascript:void();" id="view_compl_task">Task Name</a></li>
		<%} %>
		<%if(userRights.contains("coma_ADD") || userRights.contains("coma_VIEW") || userRights.contains("coma_MODIFY")|| userRights.contains("coma_DELETE")) {%>
			<li><a href="javascript:void();" id="view_compl_contacts">Contact Master</a></li>
		<%} %>
		<%if(userRights.contains("port_ADD") || userRights.contains("port_VIEW") || userRights.contains("port_MODIFY")|| userRights.contains("port_DELETE")) {%>
			<li><a href="javascript:void();" id="transfer_compliance">Port</a></li>
		<%} %>	

	</ul>
	</li>
	<%} %>
	<li><a href="#">Comman Space</a>
	<ul>
		<li><a href="javascript:void();" id="userView">User</a></li>
		<li><a href="javascript:void();" id="employeeView">Employee</a></li>
		<li><a href="javascript:void();" id="shift_view">Configure
		Shift</a></li>
		<li><a href="javascript:void();" id="departmentView">Department</a></li>
		<li><a href="javascript:void();" id="subDepartmentView">Sub
		Department</a></li>
		<li><a href="javascript:void();" id="designationView">Desigation</a></li>
		<li><a href="javascript:void();" id="organizationView">Oragnization</a></li>
		<li><a href="javascript:void();" id="emailConfigView">Email
		Setting</a></li>
		<li><a href="javascript:void();" id="ticket_configurtion_view">Ticket
		Setting</a></li>
	</ul>
	</li>

	<li><a href="index.html"><img src="images/calendar.png" /></a></li>

</ul>
</div>
<div class="clear"></div>
</body>
</html>