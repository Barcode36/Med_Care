<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript">
timePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}
</script>

<script type="text/javascript">
function getmoduleName()
{
	var moduleName=$("#moduleName").val();

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/getNameofModule.action?&moduleName="+moduleName,
		
	    success : function(data) 
	    {
			$("#"+"gridMap").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	
}
getmoduleName();
</script>


</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Map Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View For <s:property value="%{deptName}"/> Department</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	<tr></tr>
	<tr><td></td></tr>
	<tr>
	    <td>
		    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
		    <tbody><tr><td class="pL10"></td></tr></tbody>
		    </table>
	    </td>
	     <!-- azad 25 July --> 
	    <td class="alignleft printTitle">
	         <s:select 
			    id="moduleName"
				name="moduleName"
				list="moduleList"
				headerKey="-1"
				headerValue="Select Module Name"
				cssClass="button"
				cssClass="button"
			    cssStyle="margin-top: -2px;margin-left:-31px"		
			    theme="simple"
                onchange="getmoduleName();"
			  />
	    
	    </td>
	    
	    
	    <td class="alignright printTitle">
	      <%if(userRights.contains("mapcon_ADD"))
		     {%>
	         <sj:a id="mappingButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="mapNewContact('moduleName');">Map Contact</sj:a>
	      <%} %>
	    </td>   
	</tr>
	</tbody>
	</table>
	</div>
	</div>
<div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<div id="gridMap"></div>
</div>
</div>
</div>
</body>
</html>