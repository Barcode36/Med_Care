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
//bar chart dash4
$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
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
	                        	 { dataField: 'Pending', displayText: 'Pending' , color:'#FF0033' },
	                                { dataField: 'Done', displayText: 'Resolved', color:'#009933' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#4thStackBarGraph').jqxChart(settings);
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
//bar 2 axes
$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
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
	                                 { dataField: 'Pending', displayText: 'Pending' , color:'#FF0033' },
			                                { dataField: 'Done', displayText: 'Resolved', color:'#009933' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#4thColumn2AxesGraph').jqxChart(settings);
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
	 //bubble 4th
	 $.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
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
			                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending', color:'#FF0033' },
			                                     { dataField: 'Done', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Resolved', color:'#009933'}
			                                     ] 
			                          }
			                     ]
			             };
			    	    
			    	// setup the chart
			    	 $('#4thBubbleGraph').jqxChart(settings);
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
	
	//line chart
	$.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	  	  	    success : function(statusdata) {
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                            		 '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
															'<br /><b>Pending: </b>' +data.Pending+
															'<br /><b>Done: </b>' +data.Done+
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
	  	                                   
	  	                                    { dataField: 'Pending', displayText: 'Pending' },
			                                { dataField: 'Done', displayText: 'Resolved' }
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#4thLine').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });	
	//pie graph
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	    type : "json",
	    success : function(data) {
	    	
	    	 var total=0;
	    	 
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].Pending);      
				}
		  	    
	    	 
		  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: "Pending Ticket Previous Month",
	                  description: "For All Department" ,
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
	                                    	dataField: 'Pending',
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
	    	  $('#4thPieGraph').jqxChart({ enableAnimations: true });
	        $('#4thPieGraph').jqxChart(settings);
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
<s:if test="maximizeDivBlock=='4thStackBarGraph'">
<div id='4thStackBarGraph' style="width: 100%; height: 300px" align="center"></div>
</s:if>
<!-- // Edited bt Kamlesh 4-10-2014 -->
<s:elseif test="maximizeDivBlock=='4thColumn2AxesGraph'">
<div id='4thColumn2AxesGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='4thBubbleGraph'">
<div id='4thBubbleGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='4thLine'">
<div id='4thLine' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='4thPieGraph'">
<div id='4thPieGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>
</center>
</body>
</html>