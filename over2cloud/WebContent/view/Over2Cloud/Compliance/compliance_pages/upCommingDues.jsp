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
function complTakeAction(cellvalue, options, rowObject) 
{
   return "<a href='#' onClick='complianceTakeAction("+rowObject.id+")'>Take Action</a>";
}
function complianceTakeAction(valuepassed) 
{
   $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?id="+valuepassed);
   $("#takeActionGrid").dialog('open');
   return false;
 }
function printCurrentCompliance(cellvalue, options, rowObject) 
{
      return "<a href='#' onClick='printCompliance("+rowObject.id+")'>Print</a>";
}
function printCompliance(val)
{
  $.ajax( {
	type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/printComplianceDetail.action?complID="+val,
		success : function(subdeptdata) {
		$("#currComplianceDetail").html(subdeptdata);
		},
		error : function() {
			alert("ERROR");
		}
	});
    $("#printCompliance").dialog('open');
}
</SCRIPT>
</head>
<body>
<div class="page_title"><h1><s:property value="%{mainHeaderName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="800"
          height="400"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="800"
          height="400"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>

<sj:dialog id="download_feed_status" modal="true" effect="slide" autoOpen="false" width="350" title="Download" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center','top']" >
</sj:dialog>

<sj:dialog id="printCompliance" modal="true" effect="slide" autoOpen="false" width="600" height="200" title="Print Compliance" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center','top']" >
	<div id="currComplianceDetail"></div>
</sj:dialog>

<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
	<s:url id="upCommingDues_URL" action="getUpCommingCompliances" escapeAmp="false">
		<s:param name="currentDay" value="%{currentDay}" />
		<s:param name="currentWeek" value="%{currentWeek}" />
		<s:param name="currentMonth" value="%{currentMonth}" />
	</s:url>

<sjg:grid 
		  id					 ="gridedittable"
          caption				 ="%{mainHeaderName}"
          href					 ="%{upCommingDues_URL}"
          gridModel				 ="masterViewList"
          navigator				 ="true"
          navigatorAdd			 ="false"
          navigatorDelete		 ="false"
          navigatorView			 ="true"
          navigatorEdit			 ="false"
          navigatorSearch		 ="false"
          rowList				 ="5,10,15,20,25"
          rownumbers			 ="-1"
          viewrecords			 ="true"       
          pager					 ="true"
          pagerButtons			 ="true"
          pagerInput			 ="false"   
          multiselect			 ="true"  
          loadingText			 ="Requesting Data..."  
          rowNum				 ="5"
          navigatorSearch		 ="true"
          navigatorSearchOptions ="{sopt:['eq','ne','cn','bw','ne','ew']}"
          shrinkToFit			 ="false"
          width					 ="800"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
			<sjg:gridColumn 
						name		=		"%{colomnName}"
						index		=		"%{colomnName}"
						title		=		"%{headingName}"
						width		=		"%{width}"
						align		=		"%{align}"
						editable	=		"%{editable}"
						formatter	=		"%{formatter}"
						search		=		"%{search}"
						hidden		=		"%{hideOrShow}"
			 />
		</s:iterator>  
</sjg:grid>
<br>
 
 <sj:dialog 
      		id		 	=	"downloadCompliancedetails" 
      	 	buttons  	=	"{ 
    						'OK Button':function() { okSelected(); },
    						}"  
    		showEffect	=	"slide" 
    		hideEffect	=	"explode" 
    		autoOpen	=	"false" 
    		modal		=	"true" 
    		title		=	"Select Fields"
    		openTopics	=	"openEffectDialog"
    		closeTopics	=	"closeEffectDialog"
    		modal		=	"true" 
    		width		=	"390"
			height		=	"300"
    >
    <div id="downloadCompColumnDiv"></div>
 </sj:dialog>
 
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
