<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/LongPatientStay/longPatientStay.js"/>"></script>
<script type="text/javascript">
var flagForDownload='patient_view';
getGridView();
function takeActionTicket(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
}

function takeActionOnClick(id) 
{
	 
	var uhid = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'UHIID');
	var ticket = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ticket_no');
	var patName = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'PATIENT_NAME');
	var nursing = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'nursing_unit');
	var bedNo = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ASSIGN_BED_NUM');
	var admDoc = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ADMITTING_PRACTITIONER_NAME');
	var admSpec = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'SPECIALITY');
	var atnDoc = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ATTENDING_PRACTITIONER_NAME');
	var admAt = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ADMISSION_DATE');
	var location = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'floor');
	var level = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'level');
	$("#takeActionGrid").html("");
	$("#takeActionGrid").dialog({title: 'Take Action for '+ticket,width: 1000,height: 490});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax({	
		type : "post",
		url : "/over2cloud/view/Over2Cloud/LongPatientStay/beforeTakeActionLongPatient.action?id="+id+"&uhid="+uhid+"&patName="+patName+"&nursing="+nursing+"&bedNo="+bedNo+"&admDoc="+admDoc+"&admSpec="+admSpec+"&location="+location
		+"&atnDoc="+atnDoc+"&admAt="+admAt+"&level="+level,  
		success : function(data) 
		{
		    $("#takeActionGrid").html(data);
		    
		},
		error: function() {
		    alert("error");
		}
	});
	 	
 	
}


function historyView(cellvalue, options, row) 
{
	return "<a  href='#' onclick='historyDetails("+row.id+");'><b style='color:blue;cursor: pointer;'>"+cellvalue+"</b></a>";
}

function historyDetails(id)
{
	var uhid = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'UHIID');
	var ticket = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ticket_no');
	var patName = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'PATIENT_NAME');
	var nursing = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'nursing_unit');
	var bedNo = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ASSIGN_BED_NUM');
	var admDoc = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ADMITTING_PRACTITIONER_NAME');
	var admSpec = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'SPECIALITY');
	var atnDoc = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ATTENDING_PRACTITIONER_NAME');
	var admAt = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'ADMISSION_DATE');
	var location = jQuery("#gridLongPatientStay1").jqGrid('getCell',id,'floor');
	 $("#takeActionGridHistory").html("");
	$("#takeActionGridHistory").dialog({title: 'History for '+ticket,width: 1000,height: 490});
	$("#takeActionGridHistory").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGridHistory").dialog('open');
	$.ajax({	
		type : "post",
		url : "/over2cloud/view/Over2Cloud/LongPatientStay/beforeViewTicketHistory.action?id="+id+"&uhid="+uhid+"&patName="+patName+"&nursing="+nursing+"&bedNo="+bedNo+"&admDoc="+admDoc+"&admSpec="+admSpec+"&location="+location
		+"&atnDoc="+atnDoc+"&admAt="+admAt,  
		success : function(data) 
		{
		    $("#takeActionGridHistory").html(data);
		    
		},
		error: function() {
		    alert("error");
		}
	});
}

function getMySearch(s,h) 
{
	
	$("."+s).show();
	$("."+h).hide();
	
}
	function getDataForPrint() 
	{
		//$("#takeActionGrid").dialog('close');
		 var printContents = document.getElementById("middleDiv").innerHTML;
		 var myWindow = window.open("","myWindow","width=900,height=600"); 
		 myWindow.document.write(printContents);
		 myWindow.moveTo(300,200); 
		 myWindow.print();
		 myWindow.close();
	}
	$(document).ready(function()
			 {
				 $("#location_search").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});

				 $("#speciality").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});
				 $("#nursing_search").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});
				 $("#admittung_search").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});

				 $("#location_search_ticket").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});

				 $("#speciality_ticket").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});
				 $("#nursing_search_ticket").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});
				 $("#admittung_search_ticket").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 1000]
				 	});
				  
			 });

</script>
 <style>
.ui-multiselect {
        width: 157px !important;
}
</style>
</head>
<body>
 <sj:dialog
          id="takeActionGrid"
          autoOpen="false" 
        modal="true"
        width="1029"
        height="450"
        onCompleteTopics="close"
          position="center"
          >
</sj:dialog>

<sj:dialog
          id="takeActionGridHistory"
          autoOpen="false" 
        modal="true"
        width="1029"
        height="450"
        onCompleteTopics="close"
          position="center"
          >
</sj:dialog>
<sj:dialog 
    	id="downloadExcelActivity" 
    	buttons="{ 
    		'Cancel':function() { cancelButton(); } 
    		}" 
    	autoOpen="false" 
    	modal="true" 
    	onCompleteTopics="close"
    	width="310"
		height="400"
    	title=" Download Excel"
    	resizable="false"
    />
<div class="list-icon">
		<div class="longView"  style="display:block">
	 <div class="head"  >Long Time Patient Stay</div>
	 <div class="head"  ><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	    <div class="head"  id="statusCounter"></div>
	    </div>
	    <div class="longViewTicket" style="display:none">
	 <div class="head"  >Patient Admitting</div>
	 <div class="head"  ><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	    <div class="head" id="pending"></div>
	    <div class="head" id="close"></div>
	    <div class="head" id="parked"></div>
	     <div class="head" id="total"></div>
	    </div>
	    
	  
</div>
<div id="downDiv" style="float: right;margin-top: -34px;margin-right:20px"> 
	 	<sj:a id="long1" title="Long Patient Stay View"   cssClass="button" cssStyle="height:30px; width:110px; " button="true"  onclick="UpdateDropFilters (); getGridView(); getMySearch('longView','longViewTicket');">Patient View</sj:a>
	 	<% if(userRights.contains("AddTicket")){ %>
	 	<sj:a id="long2" title="Ticket"   cssClass="button" cssStyle="height:30px; width:80px; " button="true"  onclick="getGridViewForTicket();getMySearch('longViewTicket','longView'); ">Ticket</sj:a>
	 <%}%>
	<sj:a id="downloadButton" button="true" buttonIcon="ui-icon-arrowstop-1-s" cssClass="button"  title="Download" cssStyle="height:25px; width:32px" onclick="downloadExcelActivity1();"></sj:a>
	<sj:a  button="true" cssStyle="height:25px; width:32px; "  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClick="getDataForPrint()" ></sj:a>
	</div>
 <div class="clear"></div>
    <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center"  >
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td>
	 <div class='longView' style="display:block">
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0">
	 <tbody><tr><td class="pL10"> 
	  
	<s:select
			id="fromDays" 
			name="fromDays" 
			headerKey="-1"
			headerValue="From Days"
			list="days" 
		 	cssStyle="width: 62px;display:none "
			theme="simple"
			cssClass="select"
			multiple="false"
			/>	
			
			<s:select
			id="toDays" 
			name="toDays" 
			headerKey="-1"
			headerValue="To Days"
			list="days" 
		 	cssStyle="width: 44px;display:none "
			theme="simple"
			cssClass="select"
			multiple="false"
			/>	
				
		 
			<s:select
				id="range" 
				name="range" 
				list="#{'-1':'Select Option','Greater':'Is Greater than','GreaterAndEqual':'Is Greater than and Equal to','Less':'Is Less than','LessAndEqual':'Is Less than and Equal to','Equal':'Is Equal to','DateRange':'Date Wise','Other':'Other'}" 
				onchange="showHideMyDiv(this.value)"
				cssStyle="width:106px;float:left"
				theme="simple"
				multiple="false"
				cssClass="select"
			/>
			<div id="datePickerStatus" style="float: left;display: none;">
			From:<sj:datepicker
	cssStyle="height: 26px; width: 93px;" cssClass="button"
	id="sdate" name="sdate" size="20"
	value="" readonly="true" yearRange="2013:2050"
	onchange="" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="From Date" /> 
	To:<sj:datepicker
	cssStyle="height: 26px; width: 93px;" cssClass="button"
	id="edate" name="edate" size="20"
	value="" readonly="true" yearRange="2013:2050"
	onchange="" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="To Date" /> </div>
			
			<s:select
				id="rangeForDate" 
				name="rangeForDate" 
				list="#{'-1':'Select Option','Greater':'Is Greater than','GreaterAndEqual':'Is Greater than and Equal to','Less':'Is Less than','LessAndEqual':'Is Less than and Equal to','Equal':'Is Equal to','OtherForDate':'Other'}" 
				cssStyle="width:106px;float:left;display: none;"
				onchange="showHideMyDivForDate(this.value)"
				theme="simple"
				multiple="false"
				cssClass="select"
			/>
			<s:textfield  name="patDaysForDate" id="patDaysForDate"  maxlength="4" onblur="getGridView();UpdateDropFilters ();getStatusCounter();" cssStyle="margin-top: -30px;width:42px" cssClass="textField" placeholder="Days"/>
			<s:textfield  name="patDays" id="patDays"  maxlength="4" onblur="getGridView();UpdateDropFilters ();getStatusCounter();" cssStyle="margin-top: -30px;width:42px" cssClass="textField" placeholder="Days"/>
			 
			<s:select
			id="fromDaysForDate" 
			name="fromDaysForDate" 
			headerKey="-1"
			headerValue="From Days"
			list="days" 
		 	cssStyle="width: 62px;display:none "
			theme="simple"
			cssClass="select"
			multiple="false"
			/>	
			
			<s:select
			id="toDaysForDate" 
			name="toDaysForDate" 
			headerKey="-1"
			headerValue="To Days"
			list="days" 
		 	cssStyle="width: 44px;display:none "
			theme="simple"
			cssClass="select"
			multiple="false"
			/>	
			<b>Days</b>
			<sj:a id="searchButton" title="Search"   cssClass="button" cssStyle="height:30px; width:80px; " button="true"  onclick="UpdateDropFilters (); getGridView(); getStatusCounter();">Search</sj:a>
			
	<div id="locationDiv" style="float: right;"> 
	<b>Location: </b>
	 <s:select
				id="location_search" 
				name="location_search" 
				list="locationMap" 
				onchange="getGridView();getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
	</div>
	 <div id="nursingDiv" style="float: right;"> 
	 <b>Nursing: </b>
	 <s:select
				id="nursing_search" 
				name="nursing_search" 
				list="nursingMap" 
				onchange="getGridView() ;getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			 
	<div id="specialityDiv" style="float: right;"> 
	<b >Speciality: </b>
	 <s:select
				id="speciality" 
				name="speciality" 
				list="specialityMap" 
				onchange="getGridView() ;getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			 
	<div id="doctorDiv" style="float: right;"> 
	<b>Adm Doctor:</b>
	 <s:select
				id="admittung_search" 
				name="admittung_search" 
				list="admittungMap" 
				onchange="getGridView() ;getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
	 
	 	 	
	</td></tr></tbody>
	 
 
	</table>
	</div>
	<div class='longViewTicket' style="display:none">
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0">
	 <tbody><tr><td class="pL10"> 
	From:<sj:datepicker
	cssStyle="height: 26px; width: 93px;" cssClass="button"
	id="startDate" name="startDate" size="20"
	value="" readonly="true" yearRange="2013:2050"
	onchange="getGridViewForTicket();UpdateDropFiltersForTicket();" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="From Date" /> 
	To:<sj:datepicker
	cssStyle="height: 26px; width: 93px;" cssClass="button"
	id="endDate" name="endDate" size="20"
	value="" readonly="true" yearRange="2013:2050"
	onchange="getGridViewForTicket();UpdateDropFiltersForTicket();" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="To Date" />  
	 <div id="locationDivTicket" style="float: right;"> 
	<b>Location: </b>
	 <s:select
				id="location_search_ticket" 
				name="location_search_ticket" 
				list="locationMap" 
				onchange="getGridViewForTicket();getStatusCounterForTicket();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
	</div>
	 <div id="nursingDivTicket" style="float: right;"> 
	 <b>Nursing: </b>
	 <s:select
				id="nursing_search_ticket" 
				name="nursing_search_ticket" 
				list="nursingMap" 
				onchange="getGridViewForTicket() ;getStatusCounterForTicket();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			 
	<div id="specialityDivTicket" style="float: right;"> 
	<b >Speciality: </b>
	 <s:select
				id="speciality_ticket" 
				name="speciality_ticket" 
				list="specialityMap" 
				onchange="getGridViewForTicket() ;getStatusCounterForTicket();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			 
	<div id="doctorDivTicket" style="float: right;"> 
	<b>AdmDoctor:</b>
	 <s:select
				id="admittung_search_ticket" 
				name="admittung_search_ticket" 
				list="admittungMap" 
				onchange="getGridViewForTicket() ;getStatusCounterForTicket();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
	
	 </td></tr></tbody>
	</table>
	</div>
	
	
	
	</td>
	</tr></tbody></table>
	</div>
	</div>
<div style="overflow: scroll; height: 430px;"><div id="middleDiv"></div></div>
</div>
</body>
</html>