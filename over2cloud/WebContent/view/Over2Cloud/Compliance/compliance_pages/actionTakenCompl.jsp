<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script type="text/javascript">
//
function complTakeAction(cellvalue, options, rowObject) 
{
       return "<a href='#' onClick='complianceTakeAction("+rowObject.id+")'>Take Action</a>";
}

function complianceTakeAction(valuepassed) {
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
	//alert(val);
	 $.ajax( {
		 
		type :"post",
			url :"/mhdm/view/compliance_pages/printComplianceDetail.action?complID="+ val,
			success : function(subdeptdata) {
			$("#currComplianceDetail").html(subdeptdata);
			},
			error : function() {
				alert("ERROR");
			}
		});
	 $("#printCompliance").dialog('open');
}

  function viewDocuments(cellvalue, options, rowObject) 
  {
   return "<a href='#' onClick='viewDocumentsClient("+rowObject.id+")'>View</a>";
  }
 
  function viewDocumentsClient(valuepassed)
  {
	 $("#document").load("<%=request.getContextPath()%>/view/compliance_pages/downloadFileView?id="+valuepassed);
     $("#document").dialog('open');
	 return false;
  }
function cancelButton3() {
	$('#document').dialog('close');
};


function cancelButton2() {
	$('#document').dialog('close');
};

function setDownloadType(type)
{
	if(type == "currentComplianceExcel")
	{
		$("#download_feed_status").load("/mhdm/view/compliance_pages/progressbar.jsp?downloadType="+type+"&status=CurrentComp");
		$("#download_feed_status").dialog('open');
	}
	else if(type == "weekComplianceExcel")
	{
		$("#download_feed_status").load("/mhdm/view/compliance_pages/progressbar.jsp?downloadType="+type+"&status=currentWeekComp");
		$("#download_feed_status").dialog('open');
	}
	else if(type == "monthComplianceExcel")
	{
		$("#download_feed_status").load("/mhdm/view/compliance_pages/progressbar.jsp?downloadType="+type+"&status=currentMonthComp");
		$("#download_feed_status").dialog('open');
	}

	if(type == "currentCompliancePdf")
	{
		$("#download_feed_status").load("/mhdm/view/compliance_pages/progressbar.jsp?downloadType="+type+"&status=CurrentComp");
		$("#download_feed_status").dialog('open');	
	}
}

function sendCompliance(compType, emailId)
{
	var email = $('#'+emailId).val();
	 $.ajax( {
		 
			type :"post",
				url :"/mhdm/view/compliance_pages/instantMailCompliance.action?email="+email+"&compType="+compType,
				success : function() {
					alert("SUCCESS");
				},
				error : function() {
					alert("ERROR");
				}
			});
}
</script>
</head>
<body>
<div class="page_title"><h1><s:property value="%{mainHeaderName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<sj:dialog
          id="frequency"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Operation Task Frequency Informations"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="300"
          draggable="true"
    	  resizable="true"
    	  buttons="{ 
			'Cancel':function() { cancelButton3(); } 
			}"
          >
</sj:dialog>
<sj:dialog
          id="document"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Attached Document"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="300"
          draggable="true"
    	  resizable="true"
    	  buttons="{ 
			'Cancel':function() { cancelButton3(); } 
			}"
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
    	  buttons="{ 
			'Cancel':function() { cancelButton2(); } 
			}"
          >
</sj:dialog>
<sj:dialog
          id="frequency"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Operation Task Frequency Informations"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="300"
          draggable="true"
    	  resizable="true"
    	  buttons="{ 
			'Cancel':function() { cancelButton3(); } 
			}"
          >
</sj:dialog>
<sj:dialog
          id="document"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Attached Document"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="300"
          draggable="true"
    	  resizable="true"
    	  buttons="{ 
			'Cancel':function() { cancelButton3(); } 
			}"
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
    	  buttons="{ 
			'Cancel':function() { cancelButton2(); } 
			}"
          >
</sj:dialog>

<sj:dialog id="download_feed_status" modal="true" effect="slide" autoOpen="false" width="350" title="Download" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center','top']" >
</sj:dialog>

<sj:dialog id="printCompliance" modal="true" effect="slide" autoOpen="false" width="800" height="250" title="Print Operation Task" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center','top']" >
	<div id="currComplianceDetail"></div>
</sj:dialog>

<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
	<s:url id="actionTaken_URL" action="complActionTakenForGrid" />
	
<sjg:grid 
		  id							=			"gridedittable"
		  groupField					=			"['actionStatus']"
		  groupText						=			"['<b>{0} - {1} Pending</b>']"
          caption						=			"%{mainHeaderName}"
          href							=			"%{actionTaken_URL}"
          gridModel						=			"masterViewList"
          navigator						=			"true"
          navigatorAdd					=			"false"
          navigatorView					=			"true"
          navigatorDelete				=			"%{deleteFlag}"
          navigatorEdit					=			"%{modifyFlag}"
          navigatorSearch				=			"false"
          rowList						=			"10,15,20,25"
          rownumbers					=			"-1"
          viewrecords					=			"true"       
          pager							=			"true"
          pagerButtons					=			"true"
          pagerInput					=			"false"   
          multiselect					=			"true"  
          loadingText					=			"Requesting Data..."  
          rowNum						=			"5"
          navigatorEditOptions			=			"{height:230,width:400}"
          navigatorSearchOptions		=			"{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl						=			"%{viewModifyMessageDraft}"
          navigatorEditOptions			=			"{reloadAfterSubmit:true}"
          shrinkToFit					=			"false"
          width="835"
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
</div>
</div>
</div>
</div>
</body>
</html>
