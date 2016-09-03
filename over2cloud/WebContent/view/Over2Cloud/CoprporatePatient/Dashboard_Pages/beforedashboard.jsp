<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script type="text/javascript" src="<s:url value="/js/cpservice/cpsDashboard.js"/>"></script>
<script src="amcharts/amcharts.js"></script>
<script src="amcharts/serial.js"></script>
<script src="amcharts/pie.js"></script>
<script src="amcharts/amstock.js"></script>
<script src="amcharts/themes/Pielight.js"></script>
<script src="amcharts/themes/light.js"></script>
<script  src="amcharts/plugins/export/export.js"></script>
<link rel="stylesheet" href="amcharts/plugins/export/export.css" type="text/css" />
<link rel="stylesheet" href="css/CPS/style.css" type="text/css" />
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">

<title>Over2cloud</title>
<style type="text/css">
	

.amcharts-graph-graph2 .amcharts-graph-stroke {
  stroke-dasharray: 4px 5px;
  stroke-linejoin: round;
  stroke-linecap: round;
  -webkit-animation: am-moving-dashes 1s linear infinite;
  animation: am-moving-dashes 1s linear infinite;
}

@-webkit-keyframes am-moving-dashes {
  100% {
    stroke-dashoffset: -28px;
  }
}
@keyframes am-moving-dashes {
  100% {
    stroke-dashoffset: -28px;
  }
}
	
	
	
#mini{
           
            overflow: auto;
            overflow-y: hidden;
            margin: 0 auto;
            white-space: nowrap;
            width:100%;
        }
        #jqxChart44{
           
            overflow: auto;
            overflow-y: hidden;
            margin: 0 auto;
            white-space: nowrap;
            width:100%;
        }
        element.style {
    display: none !important;
}

</style>
<script type="text/javascript">
showChart('1stBarGraph','jqxChart11','Kaizen','33thDataBlockDiv','','','');

$(document).ready(function() {
    $(".tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tab-content").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
   
});
$("#tab2").click(function(){
	showChart('1stBarGraph','jqxChart22','Kaizen','22thDataBlockDiv','','','');
});
$("#tab3").click(function(){
	showChart('1stBarGraph','jqxChart33','Kaizen','1stTableData','','','');
});

</script>

</head>
<body>
<div id="tabs-container">
    <ul class="tabs-menu">
  <li class="current"><a href="#tab-1" >Account Manager</a></li>
    <li><a href="#tab-2" id="tab2">Corporate Type</a></li>
    <li><a href="#tab-3" id="tab3">Services Wise</a></li>
    </ul>
     <div id="tab-1" class="tab-content">
           <div class="contentdiv-small" style="width: 111%;height:405px;margin-top: -11px;" >
           <div id="1stDataBlockDiv">
       <center>
        <div class="clear"></div>
       <b style=" margin-left: -9%;">Data</b>&nbsp;From&nbsp;
				<sj:datepicker name="fromdate" id="fromdate"  value="%{fromDate}"    readonly="true"  changeMonth="true" changeYear="true" yearRange="2014:2030" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px;width: 78px;"   cssClass="textField" placeholder="Select Date"/>
				&nbsp;To&nbsp;
				<sj:datepicker  name="todate" id="todate" value="%{toDate}"   readonly="true"  changeMonth="true" changeYear="true" yearRange="2014:2030" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px;width: 78px;"   cssClass="textField" placeholder="Select Date"/>
				<sj:a id="search" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="refreshAccountManager('1stBarGraph','jqxChart11','Kaizen','33thDataBlockDiv')"></sj:a>
        </center>
        <center>&nbsp;&nbsp;<strong><i>Double Click on Bar for Pie Chart</i> </strong> </center> 
			<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stPieGraph','jqxChart11','Kaizen','33thDataBlockDiv')"><img src="images/pie_chart_icon.jpg" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
			<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stLineGraph','jqxChart11','Kaizen','33thDataBlockDiv')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
			<div class="clear"></div>
			<div id='jqxChart11' style="width: 100%; height: 301px;margin-top: 0%;" align="center"></div>
			<div id="legend11" style="display: block;margin-left: 31%;margin-top: 0%;height: 0px;">
			
			</div>
		  </div>
		  </div>
     </div>
     <div id="tab-2" class="tab-content">
   			<div class="contentdiv-small" style="width: 126%;height:377px;margin-top: 2px;" >
				<div id="2rdDataBlockDiv">
				<center>
        <div class="clear"></div>
       <b style=" margin-left: 0%;">Data</b>
						&nbsp;From&nbsp;
						<sj:datepicker name="fromdate" id="fromdate1"  value="%{fromDate}"    readonly="true"  changeMonth="true" changeYear="true" yearRange="2014:2030" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px;width: 78px;"   cssClass="textField" placeholder="Select Date"/>
						
						&nbsp;To&nbsp;
						<sj:datepicker  name="todate" id="todate1" value="%{toDate}"   readonly="true"  changeMonth="true" changeYear="true" yearRange="2014:2030" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px;width: 78px;"   cssClass="textField" placeholder="Select Date"/>
						<sj:a id="searchBu" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="refreshCorporateType('1stBarGraph','jqxChart22','Kaizen','22thDataBlockDiv')"></sj:a>
        </center>
							<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stBarGraph','jqxChart33','Kaizen','1stTableData')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
							<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="fetchMiniDash('jqxChart33')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
							<div class="clear"></div>
							<div id='jqxChart22' style="width: 100%; height: 335px" align="center"></div>
							<div id="legend" style="display: block;margin-left: 31%;margin-top: 0%;height: 0px;">
							
							</div>
	 		 	</div>
 		 	</div>
 	</div>
		<div id="tab-3" class="tab-content">
	 		<div class="contentdiv-small" style="width: 111%;height:376px;margin-top: -11px;" >
					<div id="3rdDataBlockDiv">
					<center>
        <div class="clear"></div>
       <b style=" margin-left: 0%;">Data</b>
						&nbsp;From&nbsp;
						<sj:datepicker name="fromdate" id="fromdate2"  value="%{fromDate}"    readonly="true"  changeMonth="true" changeYear="true" yearRange="2014:2030" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px;width: 78px;"   cssClass="textField" placeholder="Select Date"/>
						
						&nbsp;To&nbsp;
						<sj:datepicker  name="todate" id="todate2" value="%{toDate}"   readonly="true"  changeMonth="true" changeYear="true" yearRange="2014:2030" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px;width: 78px;"   cssClass="textField" placeholder="Select Date"/>
						<sj:a id="searchBut1" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="refreshService('1stBarGraph','jqxChart33','Kaizen','1stTableData')"></sj:a>
        </center>
						<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stBarGraph','jqxChart33','Kaizen','1stTableData')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
						<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="fetchMiniDash('jqxChart33')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
						 -->
						<div class="clear"></div>
						<div id='jqxChart33' style="width: 100%; height: 335px" align="center"></div>
						
					</div>
			</div>
        </div>
	<sj:dialog
          id="PaiDataDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          width="1200" 
          height="800"
          modal="true"
          closeTopics="closeEffectDialog"
          cssStyle="overflow: hidden;"
          
          >
          <div id="PaiDataDiv" style="width: 100%; height: 500px;margin-top: 0%;overflow: hidden !important;" align="center"></div>
         
	</sj:dialog>
	<sj:dialog
          id="gridDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          width="1200" 
          height="800"
          modal="true"
          closeTopics="closeEffectDialog"
          >
            <div id="gridDataDiv" style="width: 100%; height: 500px;margin-top: 0%;" align="center"></div>
	</sj:dialog>
	<sj:dialog
          id="gridDialogPie"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          width="1200" 
          height="800"
          modal="true"
          cssStyle="overflow: hidden;"
          closeTopics="closeEffectDialog"
          >
            <div id="gridDataDivPie" style="width: 136%; height: 429.16px; margin-top: 0%;overflow: hidden;" align="center"></div>
	</sj:dialog>
	
</div>
</body>
</html>