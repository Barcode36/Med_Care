<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page pageEncoding="UTF-8" %>
<%
String userTpe=(String)session.getAttribute("userTpe");
%>
<html>
<body>
<div class="sidebar">
			<div class="sidebar_block">
				<div class="logo"><a href="javascript:void();">
					<img src="images/logo.png" width="184" height="40" alt="Over2Cloud" title="Over2Cloud" /></a>
				</div>
				<div class="page_text">Your dashboard</div>
				<div class="main_menu">
					<div class="main_menu_sub_block">
						<div class="member_image"><a href="javascript:void();" title="Change image"><img height="60" width="60" title="" alt="" src="images/profile.png"></a></div>
						<div class="member_details">
							<h1><a href="javascript:void();" title="Manage profile"><s:property value="#session['empName']" /></a></h1>
							<p><s:property value="#session['currentdate']" /></p>
						</div>
					</div>
					<div class="main_menu_sub_block">
						<div class="left_side_menu" id="left_side_menu">
							<ul>
								<li><a href="javascript:void();" title="Manage feeds and update" onclick="show_sub_menu('menu_tab','sub_menu','1','6');" id="menu_tab1" class="this_active feeds"><b>Feeds</b></a>
									<ul style=" display:none;" id="sub_menu1">
										<li><a href="javascript:void();" id="feedToMe"><b>To Me</b></a></li>
										<li><a href="javascript:void();" id="feedToAll"><b>All</b></a></li>
									</ul>
								</li>
								<!-- <li><a href="javascript:void();" title="Manage your contacts" onclick="show_sub_menu('menu_tab','sub_menu','2','7');" id="menu_tab2" class="this_active people"><b>People</b></a>
									<ul style=" display:none;" id="sub_menu2">
										<li><a href="javascript:void();"><b>All People</b></a></li>
									</ul>
								</li> -->
								<li><a href="javascript:void();" title="Manage your vaults" onclick="show_sub_menu('menu_tab','sub_menu','2','6');" id="menu_tab2" class="this_active vaults"><b>Vaults</b></a>
									<ul style=" display:none;" id="sub_menu2">
										<li><a href="javascript:void();" id="myVaults"><b>My Vault</b></a></li>
										<li><a href="javascript:void();" id="allVaults"><b>All Vaults</b></a></li>
										<li><a href="javascript:void();" class="settings" id="sharedVaults"><b>Shared Vaults</b></a></li>
									</ul>
								</li>
								<li><a href="javascript:void();" title="Manage your task and calendar" onclick="show_sub_menu('menu_tab','sub_menu','3','6');" id="menu_tab3" class="this_active tasks"><b>Tasks</b></a>
									<ul style=" display:none;" id="sub_menu3">
										<li><a href="javascript:void();" id="myTasks"><b>My Tasks</b></a></li>
										<li><a href="javascript:void();" id="manageTasks"><b>Manage Tasks</b></a></li>
									</ul>
								</li>
								<li><a href="javascript:void();" title="Manage your groups" onclick="show_sub_menu('menu_tab','sub_menu','4','6');" id="menu_tab4" class="this_active groups"><b>Groups</b></a>
									<ul style=" display:none;" id="sub_menu4">
										<li><a href="javascript:void();" id="myGroups"><b>My Groups</b></a></li>
										<li><a href="javascript:void();" id="allGroups"><b>All Groups</b></a></li>
									</ul>
								</li>
								<li><a href="javascript:void();" title="Manage your message" onclick="show_sub_menu('menu_tab','sub_menu','5','6');" id="menu_tab5" class="this_active messages"><b>Private Messages</b></a>
									<ul style=" display:none;" id="sub_menu5">
										<li><a href="javascript:void();" id="msgCreate"><b>Create</b></a></li>
										<li><a href="javascript:void();" id="msgView"><b>View</b></a></li>
									</ul>
								</li>
								<li><a href="javascript:void();" title="Manage your application" onclick="show_sub_menu('menu_tab','sub_menu','6','6');" id="menu_tab6" class="this_active admin"><b>Admin</b></a>
									<ul style=" display:none;" id="sub_menu6">
									    <%if(userTpe.equalsIgnoreCase("o")){ %>
										<li><a href="javascript:void();" id="notification_org_view"><b>Notification</b></a></li>
										<%} %>
										<li><a href="javascript:void();" id="product_details"><b>Products details</b></a></li>
										<li><a href="javascript:void();"><b>Testing Option 3</b></a></li>
										<li><a href="javascript:void();"><b>Testing Option 4</b></a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>