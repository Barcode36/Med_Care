<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<script>

function createTaskType(){
	$("#form_reg").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeDarAddAction.action");
	}


function openDialog(id)
{
	//alert("rrrrrrrrrrrrrrr"+id);
	$("#dashboard_status").load("view/Over2Cloud/DAROver2Cloud/getDashBoardProductivity.action?idDash="+id+"&modifyFlag=0&deleteFlag=0");
}
</script>
<div class="page_title"><h1>Productivity Management Sheet Area</h1></div>
<div class="container_block">
						
						
						<div class="sub_block">
							<div class="text_heading">
								<span>Productivity Sheet DashBoard</span>
								<span class="image"><a href="javascript:void();" title="Update this">
									<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
								</span>
							</div>
							<div class="text">
								<div class="text_block" id="block">
									<ul>
									 <li>
                                            <ul>
                                                <li class="align_left"><b><h3>Task Status</h3></b></li>
                                                <li class="align_right"><span> <b><h3> Value</h3></b></span></li>
                                            </ul>
                                        </li>
                                        	<s:iterator value="dashBoardFortoday">
                                     <s:set var="applicationCode" value="app_code" />
                                      <s:set var="applicationName" value="app_name" />
                                        <li>
                                            <ul>
                                                <li class="align_left"><s:property value="%{key}"/></li>
                                                <li class="align_right"><a href="#" style="color:blue; text-decoration:  none;" onclick="openDialog(1)"><s:property value="%{value}"/></a> <span></span></li>
                                           
                                            </ul>
                                        </li>
                                        </s:iterator>
										<li class="last">
											<ul>
												<li class="align_left"></li>
											</ul>
										</li>
									</ul>
								</div>
							</div>
						</div>
						
						<div class="sub_block">
							<div class="text_heading">
								<span>Task Details</span>
								<span class="image"><a href="javascript:void();" title="Update this">
									<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
								</span>
							</div>
							<div class="text">
								<div class="text_block">
									<ul>
										<li>
                                            <ul>
                                                <li class="align_left"><b><h3>Task Status</h3></b></li>
                                                <li class="align_right"><span> <b><h3> Value</h3></b></span></li>
                                            </ul>
                                       </li>
									</ul>
										<s:iterator value="dashBoardFortommorow">
                                     <s:set var="applicationCode" value="app_code" />
                                      <s:set var="applicationName" value="app_name" />
                                        <li>
                                            <ul>
                                                <li class="align_left"><s:property value="%{key}"/></li>
                                                <li class="align_right"><a href="#" style="color:blue; text-decoration:  none;" onclick="openDialog(2)"><s:property value="%{value}"/></a> <span></span></li>
                                           
                                            </ul>
                                        </li>
                                        </s:iterator>
								</div>
							</div>
						</div>
									<div class="sub_block">
							<div class="text_heading">
								<span>Task Details</span>
								<span class="image"><a href="javascript:void();" title="Update this">
									<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
								</span>
							</div>
							<div class="text">
								<div class="text_block">
									<ul>
										<li>
                                            <ul>
                                                <li class="align_left"><b><h3>Task Status</h3></b></li>
                                                <li class="align_right"><span> <b><h3> Value</h3></b></span></li>
                                            </ul>
                                       </li>
									</ul>
										<s:iterator value="dashBoardForYesterday">
                                     <s:set var="applicationCode" value="app_code" />
                                      <s:set var="applicationName" value="app_name" />
                                        <li>
                                            <ul>
                                                <li class="align_left"><s:property value="%{key}"/></li>
                                                <li class="align_right"><a href="#" style="color:blue; text-decoration:  none;" onclick="openDialog(3)"><s:property value="%{value}"/></a> <span></span></li>
                                           
                                            </ul>
                                        </li>
                                        </s:iterator>
								</div>
							</div>
						</div>
					</div>
				  <div id="dashboard_status"></div>
                <sj:dialog 
                 id                =    "dashboard_status" 
                 modal            =    "true" 
                 effect            =    "slide" 
                 autoOpen        =    "false" 
                 width            =    "800" 
                 height           =   "300"
                 title            =    "Daily Activity Report" 
                 overlayColor    =    "#fff" 
                 overlayOpacity    =    "1.0" 
                 >
            </sj:dialog>    	
				