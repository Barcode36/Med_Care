<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%//String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
//String empIdofuser = (String) session.getAttribute("empIdofuser");
///String allotTOId=(String)session.getAttribute("AllotToId");
//////System.out.println("Emp ID "+empIdofuser+" AllotToID "+allotTOId);
%>
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/cpservice/common.js"/>"></script>


<title>Insert title here</title>

<script type="text/javascript">
function patDetail(cellvalue, options, rowObject){
	
	return "<a href='#'  onclick=getPatientDetail("+rowObject.id+")>"+cellvalue+"</a>";
}

function getPatientDetail(id){
	
	 var temp='<table width="100%" hieght="200px"><tr><td>UHID: </td><td>MM221313</td></tr>';
	 temp+='<tr><td>Patient Name: </td><td>Mr. XYZ</td></tr>';
	 temp+='<tr><td>Mobile No: </td><td>8464564564</td></tr>';
	 temp+='<tr><td>Address: </td><td>xyz</td></tr>';
	 
	$("#takeActionGrid").dialog({title: 'Patient Information Of: MR. XYZ',width: 400,height: 200});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	
	$("#takeActionGrid").html(temp);
	
	
}
function takeAction(cellvalue, options, rowObject) 
{

	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
}

function takeActionOnClick(complainId) 
{
	 
	var feedStatus = jQuery("#gridedittable").jqGrid('getCell',complainId,'status') ;
	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',complainId,'ticketno') ;
	var dateTime = jQuery("#gridedittable").jqGrid('getCell',complainId,'openat') ;
	 
	$("#takeActionGrid").dialog({title: 'Take Action On: '+ticketNo+ ' Opened On: '+dateTime+ '',width: 1000,height: 600});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus+"&feedId="+complainId,
	success : function(data)
	{
	$("#takeActionGrid").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
 
}
getGrid();
function getGrid(){
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/viewActivityBoardColumnCPS.action",
	    success : function(feeddraft) {
       $("#"+"viewDataDiv").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addFeedback()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/getAddPage.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
 
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="870" width="1200" showEffect="drop"></sj:dialog>
 
 <sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Seek Approval Action"
          modal="true"
          closeTopics="closeEffectDialog"
         
          >
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Corporate Patient </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Activity 
	</div>
	
</div>	
<div class="clear"></div>
 <div style="width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	 	    <tbody>
	    <tr>
		    <td >
		       	      	<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:auto; width:auto;margin-right: 2px;"  title="Add"   onClick="addFeedback();" buttonIcon="ui-icon-add">Add</sj:a>
		       	      
		     	 
		    </td>
	    </tr>
	        </tbody>
	 </table>
	 
	
</div>
 
<div id="viewDataDiv" ></div>
 
<!-- Code End for Header Strip -->

</div>
</div>
</div>
</body>
</html>