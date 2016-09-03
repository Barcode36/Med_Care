<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="content_block_top">
					<div class="heading"><h1><s:property value="#session['orgname']" /></h1></div>
					<div class="profile">
						<div class="profile_pic"><a href="javascript:void();" onmouseout="var change_pic = document.getElementById('mychange_pic'); change_pic.style.display='none';" onmouseover="var change_pic = document.getElementById('mychange_pic'); change_pic.style.display='block';">
							<img src="images/profile.png" width="51" height="65" alt="" title="" /></a>
							<div class="change_pic" id="mychange_pic"><a href="javascript:void();">Update</a></div>
						</div>							
						<div class="profile_desc">
							<div class="date"><s:property value="#session['currentdate']" /></div>
							<div class="profile_details">
								<ul>
									<li><a href="javascript:void();" class="profile_name"><s:property value="#session['empName']" /></a>
										<ul class="sub_menu">
											<li><a href="<s:url action="mainFrame"/>" target="_top">Home</a></li>
											<li><a href="<s:url action="logout"/>" target="_top">Logout</a></li>
										</ul>
									</li>
								</ul>
							</div>
						</div>							
					</div>
</div>