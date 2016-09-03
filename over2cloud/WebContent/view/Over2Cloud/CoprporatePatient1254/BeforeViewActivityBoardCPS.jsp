<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%//String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
//String empIdofuser = (String) session.getAttribute("empIdofuser");
///String allotTOId=(String)session.getAttribute("AllotToId");
//System.out.println("Emp ID "+empIdofuser+" AllotToID "+allotTOId);
%>
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/cpservices/common.js"/>"></script>


<title>Insert title here</title>

<script type="text/javascript">

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
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/cpservies/viewActivityBoardColumnCPS.action",
	    success : function(feeddraft) {
       $("#"+"viewDataDiv").html(feeddraft);
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
          width="1000"
          height="400"
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



  <div id="viewDataDiv" style="overflow:scroll ; max-height:400px; " align="center" ></div>
 
<!-- Code End for Header Strip -->
</div>	



</body>
</html>