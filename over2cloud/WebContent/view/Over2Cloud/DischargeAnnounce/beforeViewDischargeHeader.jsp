<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript" src='<s:url value="/js/dischargeAnnounce/dischargeAnnounceScript.js"/>'></script>
 <style>
.ui-multiselect {
        width: 147px !important;
}
</style>
<script type="text/javascript">
onloadDataGridView();
getStatusCounter();
$(document).ready(function()
		 {

			 $("#status_search").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});

			 $("#level_search").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
			
		 });
</script>

</head>

<body>

<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="300"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>
 
 <sj:dialog
          id="viewTest"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>

<sj:dialog id="completionResult1" showEffect="slide"
			hideEffect="explode" autoOpen="false" 
			cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
			modal="true">
			<div id="result1"></div>
		</sj:dialog>

<div class="list-icon">
	 <div class="head">Discharge Announce</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	  <div id="Open" style="float: left;"> </div>
	  <div id="Close" style="float: left;"> </div>
	  <div id="Total" style="float: left;"></div>
	   
</div>

<!-- Code For Header Strip -->

<div class="listviewBorder" style="margin-top: 0px;width: 100%;margin-left: 0px;" >

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					 <td class="pL10">
					 <td >
				    <sj:datepicker cssStyle="height: 20px; width: 100px;float:left;" tabindex="-1"   cssClass="button"  id="fromDate" name="fromDate" size="20" maxDate="0"   readonly="true" onchange="onloadDataGridView();getStatusCounter();"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" placeholder="From Date"/>
			
			         
				</td>
				
				 <td >
				    <sj:datepicker cssStyle="height: 20px; width: 100px;float:left;" tabindex="-2"   cssClass="button"  id="toDate" name="toDate" size="20" maxDate="0"   readonly="true" onchange="onloadDataGridView();getStatusCounter();"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" placeholder="From Date"/>
			
			         
				</td>
					<td>	
					 <s:textfield name="searchStr" id="searchStr"  placeholder="UHID/Name" theme="simple" cssClass="button" onkeyup="onloadDataGridView();getStatusCounter();" cssStyle="height:22px; width:130px;float:left;"/>
				</td>
				<td>
				
	 <b>&nbsp;&nbsp; STATUS: </b>
	 <s:select
				id="status_search" 
				name="status_search"
				list="#{'MT Pending' : 'MT Pending', 'MT Close':  'MT Close', 'FM Pending' : 'FM Pending' , 'FM Close':  'FM Close', 'BL Pending' : 'BL Pending' , 'BL Close':  'BL Close'}" 
				cssStyle="width:50px"
				onchange="onloadDataGridView();getStatusCounter();"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</td>
			 
<td>
	<b > &nbsp;&nbsp;LEVEL: </b>
	 <s:select
				id="level_search" 
				name="level_search" 
				list="#{'Level 1' : 'Level 1' , 'Level 2' : 'Level 2' , 'Level 3' : 'Level 3' , 'Level 4' : 'Level 4' , 'Level 5' : 'Level 5' }" 
				cssStyle="width:50px"
				onchange="onloadDataGridView();getStatusCounter();"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</td>
			
				<!--
				
				<td>
					<s:select id="status22" name="status" list="#{'-1':'Select Status','All Status':'All Status','CallNotPicked':'Call Not Picked','CallPicked':'Call Picked','Pending':'Pending','Parked':'Parked'}"
						theme="simple" cssClass="button"  cssStyle="width:100%;" onchange="onSearchData();"/>
				</td>
					<td>
						<s:textfield name="wildSearch" id="wildSearchData" tabindex="-1" placeholder="Search Any Value..." theme="simple" cssClass="button" onkeyup="wildSearchData();" cssStyle="height:13px; width:90px;float:left;"/>
					</td>-->
				<!--	<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					
					
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					 
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
			
		
					    --></td></tr>
					    </tbody>
					  </table>
				  </td><!--
				  
				  <td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right; margin-left:500px;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
				   
				  --><!-- Block for insert Right Hand Side Button --><!--
				  <td class="alignright printTitle">
				  
				    <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="createTestNameMaster();">Add</sj:a> 
				    <sj:a id="mappingButton5" button="true"  buttonIcon="ui-icon-plus"  cssClass="button" onclick="uploadExcel();">Upload</sj:a>
				
				  </td>
				  
			--></tr>
		</tbody>
	</table>
</div>
</div>
<div id="result_part"></div>

</div>

</body>
</html>