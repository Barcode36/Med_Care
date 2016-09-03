<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var  sampleData =null;
var title='Ticket Status';
var des='';
//barchar dash2
$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {

	    	if (data.length>0)
	    		des='For Department ';
	    	des='For Departments ';
	    	 for (var int = 0; int < data.length; int++) {
	    		 if (int==0){
	    		 des=des.concat(data[int].dept);
	    		 }
	    		 else if(int >0&&data.length==2)
	    		 {
	    			 des=des.concat(' & '+data[int].dept);
	    		 }else if (int >0&&data.length>2){
	    			 
	    			 des=des.concat(', '+data[int].dept);
	    			
	    			 if (data.length==data.length1){
	    				 des=des.concat(' & '+data[int].dept);	 
	    			 }
	    		}
	 		 }
	    	
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: ''+title,
	                description: ''+des,
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
	                source: sampleData,
	                categoryAxis:
	                    {
	                        text: 'Category Axis',
	                        textRotationAngle: 0,
	                        dataField: 'dept',
	                        showTickMarks: true,
	                        tickMarksInterval: 1,
	                        tickMarksColor: '#888888',
	                        unitInterval: 1,
	                        showGridLines: true,
	                        gridLinesInterval: 1,
	                        gridLinesColor: '#888888',
	                        axisSize: 'auto'
	                    },
	                colorScheme: 'scheme06',
	                seriesGroups:
	                    [
	                        {
	                            type: 'stackedcolumn',
	                            columnsGapPercent: 100,
	                            seriesGapPercent: 5,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                description: 'Ticket Counters',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: 
	                        	[
	                        	 { dataField: 'YearlyTotal', displayText: 'Yearly Total',color:'#FF0000' },
	                                { dataField: 'YearlyMissed', displayText: 'Yearly Missed',color:'#779900' },
	                                { dataField: 'MonthlyTotal', displayText: 'Monthly Total',color:'#009900' },
	                                { dataField: 'MonthlyMissed', displayText: 'Monthly Missed' ,color:'#C87900'},
	                                { dataField: 'WeeklyTotal', displayText: 'Weekly Total',color:'#F76F90'},
	                                { dataField: 'WeeklyMissed', displayText: 'Weekly Missed',color:'#CC9095'}
	                            ]
	                            
	                        }
	                    ]
	            };
	    	// setup the chart
	    	
	        $('#2ndStackBarGraph').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#jqxChart').jqxChart({ enableAnimations: false });
	            $('#jqxChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#jqxChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	 
//column 2 AXes
$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: ''+title,
	                description: ''+des,
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
	                source: sampleData,
	                categoryAxis:
	                    {
	                        text: 'Category Axis',
	                        textRotationAngle: 0,
	                        dataField: 'dept',
	                        showTickMarks: true,
	                        tickMarksInterval: 1,
	                        tickMarksColor: '#888888',
	                        unitInterval: 1,
	                        showGridLines: true,
	                        gridLinesInterval: 1,
	                        gridLinesColor: '#888888',
	                        axisSize: 'auto'
	                    },
	                colorScheme: 'scheme06',
	                seriesGroups:
	                    [
	                        {
	                            type: 'column',
	                            columnsGapPercent: 100,
	                            seriesGapPercent: 5,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                description: 'Ticket Counters',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: 
	                        	[
	                        	 { dataField: 'YearlyTotal', displayText: 'Yearly Total',color:'#FF0000' },
	                                { dataField: 'YearlyMissed', displayText: 'Yearly Missed',color:'#779900' },
	                                { dataField: 'MonthlyTotal', displayText: 'Monthly Total',color:'#009900' },
	                                { dataField: 'MonthlyMissed', displayText: 'Monthly Missed' ,color:'#C87900'},
	                                { dataField: 'WeeklyTotal', displayText: 'Weekly Total',color:'#F76F90'},
	                                { dataField: 'WeeklyMissed', displayText: 'Weekly Missed',color:'#CC9095'}
	                            ]
	                        }
	                    ]
	            };
	    	// setup the chart
	        $('#2ndColumn2AxesGraph').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#jqxChart').jqxChart({ enableAnimations: false });
	            $('#jqxChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#jqxChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	 
	 //bubble Graph
	 $.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	 var settings = {
	    				title: ''+title,
		                description: ''+des,
	                 enableAnimations: true,
	                 showLegend: true,
	                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	                 source: sampleData,
	                 xAxis:
	                     {
	                         dataField: 'dept',
	                         valuesOnTicks: false
	                     },
	                 colorScheme: 'scheme06',
	                 seriesGroups:
	                     [
	                         {
	                             type: 'bubble',
	                             valueAxis:
	                             {
	                                 unitInterval: 10,
	                                 minValue: 0,
	                                 maxValue: 50,
	                                 description: 'Ticket Counter'
	                                 
	                             },
	                             series: [
	                                     { dataField: 'YearlyTotal', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Yearly Total' ,color:'#FF0000'},
	                                     { dataField: 'YearlyMissed', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Yearly Missed',color:'#779900' },
	                                     { dataField: 'MonthlyTotal', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Monthly Total' ,color:'#009900'},
	                                     { dataField: 'MonthlyMissed', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Monthly Missed',color:'#C87900'},
	                                     { dataField: 'WeeklyTotal', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Weekly Total',color:'#F76F90'},
	                                     { dataField: 'WeeklyMissed', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Weekly Missed',color:'#CC9095'}
	                                     ]
	                          }
	                     ]
	             };
	    	    
	    	// setup the chart
	    	 $('#2ndBubbleGraph').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#jqxChart').jqxChart({ enableAnimations: false });
	            $('#jqxChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#jqxChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	 
	 //Line Graph
	 $.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	  	  	    success : function(statusdata) {
	  	  	    	console.log("Status Data");
	  	  	    	console.log(statusdata);
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                              '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
	                									'<br /><b>Yearly Total: </b>' +data.YearlyTotal+
	                									'<br /><b>Yearly Missed: </b>' +data.YearlyMissed+
	                									'<br /><b>Monthly Total: </b>' +data.MonthlyTotal+
	                									'<br /><b>Monthly Missed: </b>' +data.MonthlyMissed+
	                									'<br /><b>Weekly Total: </b>' +data.WeeklyTotal+
	                									'<br /><b>Weekly Missed: </b>' +data.WeeklyMissed+
	                									'<br /><b>Weekly Missed: </b>' +data.WeeklyMissed+
	                									'</DIV>';
	            };

	  	  	    var settings = {
	  	  	    	title: ''+title,
	                description: ''+des,
	  	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	  	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	  	                source: sampleData,
	  	              enableAnimations: true,
	  	                
	  	                categoryAxis:
	  	                    {	
	  	                		textRotationAngle:0,
	  	                        dataField: 'dept',
	  	                       
	  	                        showGridLines: false,
	  	                    },
	  	                colorScheme: 'scheme04',
	  	                seriesGroups:
	  	                    [                    
	  	                        {
	  	                            type: 'line',
	  	                          showLabels: true,
	  	                          
	  	                           /*  formatSettings:
	  	                            {
	  	                                prefix: 'Time ',
	  	                                decimalPlaces: 0,
	  	                                sufix: ' min'
	  	                            } */
	  	                          toolTipFormatFunction: toolTipCustomFormatFn,

	  	                            valueAxis:
	  	                            {
	  	                            	minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counter',
		                                axisSize: 'auto',
	  	                            },
	  	                            series: [
	  	                                   
												{ dataField: 'YearlyTotal', displayText: 'Yearly Total'},
												{ dataField: 'YearlyMissed', displayText: 'Yearly Missed' },
												{ dataField: 'MonthlyTotal', displayText: 'Monthly Total' },
												{ dataField: 'MonthlyMissed', displayText: 'Monthly Missed'},
												{ dataField: 'WeeklyTotal', displayText: 'Weekly Total'},
												{ dataField: 'WeeklyMissed', displayText: 'Weekly Missed',color:'#000000'}	
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#2ndLineChart').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });
	 
	 //pie graph
	 $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {
	    	
	    	 var total=0;
	    	
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].YearlyTotal);      
				}
		  	    
	    	
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: "Yearly Totoal Ticket Status",
	                  description: "For All Department",
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme11',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: 'YearlyTotal',
	                                    	displayText: 'dept',
	                                    	labelRadius: 200,
	                                    	initialAngle: 15,
	                                    	radius: 100,
	                                    	centerOffset: 0,
	                                    	formatFunction: function (value) {
	                                         	
	                                             if (isNaN(value))
	                                                 return value;
	                                             return parseFloat((value/total)*100).toFixed(1)+'%' ;
	                                         }
	                                    	
	                                    }
	                                    
	                                    
	                                  ]
	                          }
	                      ]
	              };
	    	// setup the chart
	    	
	    	  $('#2ndPieGraph').jqxChart({ enableAnimations: true });
	        $('#2ndPieGraph').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
</script>
<title>Insert title here</title>
</head>
<body>
<center>
<s:if test="maximizeDivBlock=='2ndStackBarGraph'">
<div id='2ndStackBarGraph' style="width: 100%; height: 300px" align="center"></div>
</s:if>
<!-- // Edited bt Kamlesh 4-10-2014 -->
<s:elseif test="maximizeDivBlock=='2ndColumn2AxesGraph'">
<div id='2ndColumn2AxesGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='2ndBubbleGraph'">
<div id='2ndBubbleGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='2ndLine'">
<div id='2ndLineChart' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='2ndPieGraph'">
<div id='2ndPieGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>
</center>
</body>
</html>