<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link  type="text/css" href="<s:url value="/css/table.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<SCRIPT type="text/javascript">
 function currentDay(currentDay) 
 {
    $("#complianceDialog").load("/over2cloud/view/Over2Cloud/Compliance/compliance_pages/currentDayDues.action?currentDay=1&currentWeek=0&currentMonth=0");                                             
    $("#complianceDialog").dialog('open');
 }

 function currentWeek(currentWeek) 
 {
    $("#complianceDialog").load("/over2cloud/view/Over2Cloud/Compliance/compliance_pages/currentWeekDues.action?currentDay=0&currentWeek=1&currentMonth=0");                                             
    $("#complianceDialog").dialog('open');
 }

 function currentYear(currentMonth) 
 {
    $("#complianceDialog").load("/over2cloud/view/Over2Cloud/Compliance/compliance_pages/currentMonthDues.action?currentDay=0&currentWeek=0&currentMonth=1");                                             
    $("#complianceDialog").dialog('open');
 }


 
</SCRIPT>
</head>
<body>
<div class="page_title"><h1>Operation Task</h1></div>
<div class="container_block">
<div style="height:200px;overflow:auto;">
		<div class="sub_block">
			<div class="text_heading_compliance">
				<span>Operation Task Details</span>
				<span class="image"><a href="javascript:void();" title="Update this">
					<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
				</span>
			</div>
			<div class="text_heading_compliance">
			  <div class="text_block" id="block">
				<ul>
					<s:iterator value="dashBoardMap">
					  <s:if test="%{key == 'Current Day'}">
						<li>
							<ul>
								<li class="align_left"><s:a href="javascript:void();" onclick="currentDay('currentDay');"><s:property value="%{key}"/></s:a></li>
								<li class="align_right"><s:property value="%{value}"/> <span></span></li>
							</ul>
						</li>
					 </s:if>
					 <s:if test="%{key == 'Current Week'}">
						<li>
							<ul>
								<li class="align_left"><s:a href="javascript:void();" onclick="currentWeek('currentWeek');"><s:property value="%{key}"/></s:a></li>
								<li class="align_right"><s:property value="%{value}"/> <span></span></li>
							</ul>
						</li>
					 </s:if>
					 
					 <s:if test="%{key == 'Current Month'}">
						<li>
							<ul>
								<li class="align_left"><s:a href="javascript:void();" onclick="currentYear('currentMonth');"><s:property value="%{key}"/></s:a></li>
								<li class="align_right"><s:property value="%{value}"/> <span></span></li>
							</ul>
						</li>
					 </s:if>
					 
					 <s:if test="%{key == 'Total'}">
						<li>
							<ul>
								<li class="align_left"><s:a href="javascript:void();" ><s:property value="%{key}"/></s:a></li>
								<li class="align_right"><s:property value="%{value}"/> <span></span></li>
							</ul>
						</li>
					 </s:if>
					 
				  </s:iterator>
			   </ul>
			 </div>
		 </div>
	 </div>
	</div>
	</div>
	
	 <sj:dialog 
	 			id				=	"complianceDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"900" 
	 			title			=	"Operation Task" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			>
     </sj:dialog>
	
</body>
</html>					
