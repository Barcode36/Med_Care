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
<script type="text/javascript" src="<s:url value="/js/labOrder/labOrder.js"/>"></script>
 <style>
.ui-multiselect {
        width: 147px !important;
}
</style>
<script type="text/javascript">


function tat(cellvalue, options, row)
{
	//alert(row.escalation_date);
	if(row.escalation_date == '00:00' )
	{
		return "<a href='#' title='Out of TAT' style='font-weight:bold' class='blink_me'><font color='RED' >"+cellvalue+"</font></a>";
	}
	
	
	else
		{
		return "<a href='#' title='' style='color:green' >"+cellvalue+"</font></a>";
		}
	
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
			 $("#lab_search").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
			 $("#sts_search").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
		 });

</script>
</head>


<body>
<div class="list-icon">
	 <div class="head">Lab Order</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	  <div id="InProcess" style="float: left;"> </div>
	  <div id=Progress style="float: left;"> </div>
	  <div id=Hold style="float: left;"></div>
	  <div id=RRA style="float: left;"></div>
	  <div id="Preliminary" style="float: left;"></div>
	  <div id="Rejected" style="float: left;"></div>
	  <div id="Complete" style="float: left;"></div>
	  <div id="Partial" style="float: left;"></div>
	  <div id="Total" style="float: left;"></div>
	   
</div>
 <div id="downDiv" style="float: right; margin-right:20px;margin-top:-32px"> 
	<!--<sj:a id="downloadButton" button="true" buttonIcon="ui-icon-arrowstop-1-s" cssClass="button"  title="Download" cssStyle="height:25px; width:32px" onclick=""></sj:a>
	--><sj:a  button="true" cssStyle="height:25px; width:32px; "  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClick="getDataForPrint()" ></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="getGridView()"></sj:a>
	</div>
 <div class="clear"></div>
  
    <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center" onmouseover="onOverInDiv();" onmouseout="onOverOutDiv();">
   
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td>
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<b>From:</b>
	<sj:datepicker
	cssStyle="height: 26px; width: 88px;" cssClass="button"
	maxDate="today" 
	id="sdate" name="sdate" size="20"
	value="today" readonly="true" yearRange="2013:2050"
	onchange="getGridView();getStatusCounter();UpdateDropFilters();" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="From Date" /> 
	
	<b>To:</b><sj:datepicker
	cssStyle="height: 26px; width: 88px;" cssClass="button"
	id="edate" name="edate" size="20"
	value="today" readonly="true" yearRange="2013:2050"
	onchange="getGridView();getStatusCounter();UpdateDropFilters();" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="To Date" />  
	
	
	 <sj:datepicker placeholder="From" cssStyle="margin:0px 0px 2px 0px;width:62px;height: 20px;"  onchange="getGridView();getStatusCounter();"    cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="stime" name="stime" size="20"   readonly="false"   showOn="focus"/>
<sj:datepicker placeholder="To" cssStyle="margin:0px 0px 2px 0px;width:62px;height: 20px;"  onchange="getGridView();getStatusCounter();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="etime" name="etime" size="20"   readonly="false"   showOn="focus"/>
	
 	
		<!--<div id="locationDiv" style="float: right;"> 
	<b>Location: </b>
	 <s:select
				id="location_search" 
				name="location_search" 
				list="locationMap" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
	</div>
	 -->
	 <div id="sts" style="float: right;"> 
	 <b>Status: </b>
	 <s:select
				id="sts_search" 
				name="sts_search" 
				list="#{'-1':'All', 'Registered':'Registered/Specimen Received/Patient Arrived', 'In Process':'In Process', 'In Progress':'In Progress', 'On Hold (By Department)':'On Hold (By Department)', 'Discontinued':'Discontinued', 'Resulted - Partial':'Resulted - Partial', 'Resulted - Preliminary':'Resulted - Preliminary', 'Resulted - Complete':'Resulted - Complete'}" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:67xp"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
	 
	 
	 <div id="nursingDiv" style="float: right;"> 
	 <b>NU: </b>
	 <s:select
				id="nursing_search" 
				name="nursing_search" 
				list="nursingMap" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:67xp"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			 
	<div id="specialityDiv" style="float: right;"> 
	<b >Spec: </b>
	 <s:select
				id="speciality" 
				name="speciality" 
				list="specialityMap" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			 
	<div id="doctorDiv" style="float: right;"> 
	<b>Adm Doc:</b>
	 <s:select
				id="admittung_search" 
				name="admittung_search" 
				list="admittungMap" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
			
			<div id="LabNameDiv" style="float: right;"> 
	<b>Lab:</b>
	 <s:select
				id="lab_search" 
				name="lab_search" 
				list="labNameMap" 
				onchange="getGridView();UpdateDropFilters ();getStatusCounter();"
				cssStyle="width:67px"
				theme="simple"
				multiple="true"
				cssClass="select"
			/>
			</div>
  	</td></tr></tbody></table>
	</td>
	</tr></tbody></table>
	</div>
	</div>
<div style="overflow: scroll; height: 430px;width: 100%">
				<div id="middleDiv"></div>
			</div>
		</div>


<sj:dialog
          id="takeActionDialogHistory"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Action"
          modal="true"
          width="1000"
          height="370"
          draggable="true"
    	  resizable="true"
          >
          <div id="resultDiv"></div>
</sj:dialog>
</body>

<script type="text/javascript">
 function reloadPage()
{
	var grid = $("#gridLabOrder");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>


</html>