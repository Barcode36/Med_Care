<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript" src="jqwidgets/jqxslider.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
<script type="text/javascript">
function showchart(){
	$("#jqxChart1").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
var  sampleData =null;
$.ajax({
    type : "post",
    url : "view/Over2Cloud/feedback/jsonChartDataDept.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
    type : "json",
    success : function(data) {
    	sampleData = data;
     var settings = {
         title: "",
         description: "",
         enableAnimations: true,
         showLegend: true,
         padding: { left: 5, top: 5, right: 5, bottom: 5 },
         titlePadding: { left: 0, top: 0, right: 0, bottom: 5 },
         source: sampleData,
         xAxis:
             {
                 dataField: 'dept',
                 valuesOnTicks: true
             },
         colorScheme: 'scheme04',
         seriesGroups:
             [
                 {
                     polar: true,
                     startAngle: 0,
                     radius: 180,
                     type: 'stackedcolumn',
                     valueAxis:
                     {
                         description: 'Index Value',
                         formatSettings: { decimalPlaces: 0 }
                     },
                     series: [
                             { dataField: 'Pending', displayText: 'Pending', opacity: 0.7, lineWidth: 1, radius: 2, lineWidth: 2 },
                             { dataField: 'High Priority', displayText: 'High Priority', opacity: 0.7, lineWidth: 1, radius: 2, lineWidth: 2 },
                             { dataField: 'Snooze', displayText: 'Snooze', opacity: 0.7, lineWidth: 1, radius: 2, lineWidth: 2 },
                             { dataField: 'Ignore', displayText: 'Ignore', opacity: 0.7, lineWidth: 1, radius: 2, lineWidth: 2 },
                             { dataField: 'Re-Assign', displayText: 'Re-Assign', opacity: 0.7, lineWidth: 1, radius: 2, lineWidth: 2 },
                             { dataField: 'Resolved', displayText: 'Resolved', opacity: 0.7, lineWidth: 1, radius: 2, lineWidth: 2 , color: '#009933'}
                         ]
                 }
             ]
     };
		         // create the chart
		         $('#jqxChart1').jqxChart(settings);
		         // get the chart's instance
		         var chart = $('#jqxChart1').jqxChart('getInstance');
		         // start angle slider
		         $('#sliderStartAngle1').jqxSlider({ width: 240, min: 0, max: 360, step: 1, ticksFrequency: 20, mode: 'fixed' });
		         $('#sliderStartAngle1').on('change', function (event) {
		             var value = event.args.value;
		             chart.seriesGroups[0].startAngle = value;
		             chart.update();
		         });
		         // radius slider
		         $('#sliderRadius1').jqxSlider({ width: 240, min: 80, max: 540, value: 120, step: 1, ticksFrequency: 50, mode: 'fixed' });
		         $('#sliderRadius1').on('change', function (event) {
		             var value = event.args.value;
		             chart.seriesGroups[0].radius = value;
		             chart.update();
		         });
		         // color scheme drop down
		         var colorsSchemesList = ["scheme01", "scheme02", "scheme03", "scheme04", "scheme05", "scheme06", "scheme07", "scheme08"];
		         $("#dropDownColors1").jqxDropDownList({ source: colorsSchemesList, selectedIndex: 3, width: '200', height: '25', dropDownHeight: 100 });
		         $('#dropDownColors1').on('change', function (event) {
		             var value = event.args.item.value;
		             chart.colorScheme = value;
		             chart.update();
		         });
		         // series type drop down
		         var seriesList = ["splinearea", "spline", "column", "scatter", "stackedcolumn", "stackedsplinearea", "stackedspline"];
		         $("#dropDownSeries1").jqxDropDownList({ source: seriesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 100 });
		         $('#dropDownSeries1').on('select', function (event) {
		             var args = event.args;
		             if (args) {
		                 var value = args.item.value;
		                 chart.seriesGroups[0].type = value;
		                 chart.update();
		             }
		         });
     
    },
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}
showchart();
</script>

   
</head>
<body>
<center>
     <table id="tableattrib" style="width: 850px;" >
        <tr>
            <td style="padding-left:25px">
                <p style="font-family: Verdana; font-size: 12px;">Move the slider to rotate:
                </p>
                <div id='sliderStartAngle1'>
                </div>
            </td >
        
            
            <td style="padding-left:25px">
                <p style="font-family: Verdana; font-size: 12px;">Select the series type:
                </p>
                <div id='dropDownSeries1'>
                </div>
            </td>
       
            <td style="padding-left:25px">
                <p style="font-family: Verdana; font-size: 12px;">Move the slider to change the radius:
                </p>
                <div id='sliderRadius1'>
                </div>
            </td>
          
            <td style="padding-left:25px">
                <p style="font-family: Verdana; font-size: 12px;">Select color scheme:
                </p>
                <div id='dropDownColors1'>
                </div>
            </td>
        </tr>
    </table></center>
<br>
	 <div id='jqxChart1' style="width: 100%; height: 450px" align="center">
    </div>
     
</body>
</html>