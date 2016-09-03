<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/PatientMovementTracking/patientMovementTracking.js"/>"></script>
<script type="text/javascript">
function reloadPage()
{
	var grid = $("#gridPatientTracking");
	grid.trigger("reloadGrid",[{current:true}]);
}
document.getElementById('fromDate').value=$("#hfromDate").val();
document.getElementById('toDate').value=$("#htoDate").val();
</script>
 
<style type="text/css">
.TitleBorder {
	float: left;
    height: 27px;
    border: 1px solid #e0bc27;
    margin: 2px 1px 0px 2px;
    background:#E5F2FF;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}
.TitleBorderSer {
	float: left;
    height: 27px;
    border: 1px solid #A5E244;
    margin: 2px 1px 0px 2px;
    background: white;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}
.TitleBorder h1 {
	padding-top: 0px;
	margin: 2px 5px 32px 0px;
}
h1 {
	font-size: 12px;
	    font-weight: bold;
}
.circle {
	width: 30px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #05040c;
    margin-left: 75px;
    margin-top: -53px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.circleSer {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #2F7BDA;
    margin-left: 65px;
    margin-top: -20px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.contents {
	width: 21px;
	color: white;
	background: rgb(33, 140, 255);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

.contentsfree {
	width: 21px;
	color: white;
	background: rgb(231, 113, 92);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

#empinfo tr:nth-child(even) {
	background: #CCC
}

#empinfo tr:nth-child(odd) {
	background: #FFF
}

#empinfo1 tr:nth-child(even) {
	background: #CCC
}

#empinfo1 tr:nth-child(odd) {
	background: #CCC
}
</style>
 </head>
<body>
<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>
<div class="list-icon">
	 <div class="head">Patient Tracking</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	  <div class="head">View </div> 
	    <div id="statusCounter"></div>
</div>
 <div class="clear"></div>
    <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center"  >
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td>
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	 <b>From:</b>
	<sj:datepicker name="fromDate" id="fromDate" maxDate="today" onchange="getGridView();UpdateDropFilters ();getStatusCounter();" cssStyle="width: 8%;" placeholder="Date" value="today" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="1890:2020"  showAnim="slideDown" timepicker="false" duration="fast" displayFormat="dd-mm-yy" cssClass="button" />
	<b>To:</b>
	<sj:datepicker name="toDate" id="toDate" minDate="today" onchange="getGridView();UpdateDropFilters ();getStatusCounter();" cssStyle="width: 8%;" placeholder="Date" value="today" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="1890:2020"  showAnim="slideDown" timepicker="false" duration="fast" displayFormat="dd-mm-yy" cssClass="button" />
	<b>Location: </b>
	 <s:select
				id="location_search" 
				name="location_search" 
				list="locationMap" 
				headerKey="-1"
				headerValue="Select Location"
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:170px"
				theme="simple"
				multiple="false"
				cssClass="select"
			/>
	
	<b>Nursing Unit: </b>
	 <s:select
				id="nursing_search" 
				name="nursing_search" 
				list="nursingMap" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				headerKey="-1"
				headerValue="Select Nursing Unit"
				cssStyle="width:170px"
				theme="simple"
				multiple="false"
				cssClass="select"
			/>
		 <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px;float:right;" button="true"  onclick=" reloadPage();"></sj:a>
	</td></tr></tbody></table>
	</td>
	</tr></tbody></table>
	</div>
	</div>
<div style="overflow: scroll; height: 430px;"><div id="middleDiv"></div></div>
</div>
</body>
</html>