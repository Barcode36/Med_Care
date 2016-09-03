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
//bar chart dash4
$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: ' ',
			                description: ' ',
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
			                colorScheme: 'scheme01',
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
			                                { dataField: 'SelfDue', displayText: 'Self Due' },
			                                { dataField: 'TeamDue', displayText: 'Team Due' },
			                                { dataField: 'SelfReminder1', displayText: 'Self Reminder 1'  },
			                                { dataField: 'TeamReminder1', displayText: 'Team Reminder 1'  },
			                                { dataField: 'SelfReminder2', displayText: 'Self Reminder 2'  },
			                                { dataField: 'TeamReminder2', displayText: 'Team Reminder 2'  },
			                                { dataField: 'SelfReminder3', displayText: 'Self Reminder 3'  },
			                                { dataField: 'TeamReminder3', displayText: 'Team Reminder 3'  }
			                                
			                               
			                               
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#7thStackBarGraph').jqxChart(settings);
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
	
	//2 Axes bar
	$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: ' ',
			                description: ' ',
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
			                colorScheme: 'scheme01',
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
			                                { dataField: 'SelfDue', displayText: 'Self Due' },
			                                { dataField: 'TeamDue', displayText: 'Team Due' },
			                                { dataField: 'SelfReminder1', displayText: 'Self Reminder 1'  },
			                                { dataField: 'TeamReminder1', displayText: 'Team Reminder 1'  },
			                                { dataField: 'SelfReminder2', displayText: 'Self Reminder 2'  },
			                                { dataField: 'TeamReminder2', displayText: 'Team Reminder 2'  },
			                                { dataField: 'SelfReminder3', displayText: 'Self Reminder 3'  },
			                                { dataField: 'TeamReminder3', displayText: 'Team Reminder 3'  }
			                                
			                               
			                               
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#7thColumn2AxesGraph').jqxChart(settings);
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
	
//bubble graph

$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	 var settings = {
			                 title: ' ',
			                 description: '  ',
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
			                                     { dataField: 'SelfDue', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Self Due' },
			                                     { dataField: 'TeamDue', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Team Due' },
			                                     { dataField: 'SelfReminder1', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 1' },
			                                     { dataField: 'TeamReminder1', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 1'},
			                                     { dataField: 'SelfReminder2', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 2'},
			                                     { dataField: 'TeamReminder2', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 2'},
			                                     { dataField: 'SelfReminder3', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 3'},
			                                     { dataField: 'TeamReminder3', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 3'}
			                                     ]
			                          }
			                     ]  
		                         
			             };
			    	    
			    	// setup the chart
			    	 $('#7thBubbleGraph').jqxChart(settings);
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
	  	      url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
	  	  	    success : function(statusdata) {
	  	  	    	console.log("Status Data");
	  	  	    	console.log(statusdata);
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                            		 '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
															'<br /><b>Self Due: </b>' +data.SelfDue+
															'<br /><b>Team Due: </b>' +data.TeamDue+
															'<br /><b>Self Reminder 1: </b>' +data.SelfReminder1+
															'<br /><b>Team Reminder 1: </b>' +data.TeamReminder2+
															'<br /><b>Sel Reminder 2: </b>' +data.SelReminder2+
															'<br /><b>Team Reminder 2: </b>' +data.TeamReminder2+
															'<br /><b>Sel Reminder 3: </b>' +data.SelReminder3+
															'<br /><b>Team Reminder 3: </b>' +data.TeamReminder3+
															'</DIV>';
	            };
	          
	  	  	    var settings = {
	  	                title: " ",
	  	                description: " ",
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
	  	                                   
												{ dataField: 'SelfDue', displayText: 'Self Due' },
												{ dataField: 'TeamDue', displayText: 'Team Due' },
												{ dataField: 'SelfReminder1', displayText: 'Self Reminder 1'  },
												{ dataField: 'TeamReminder1', displayText: 'Team Reminder 1'  },
												{ dataField: 'SelReminder2', displayText: 'Self Reminder 2'  },
												{ dataField: 'TeamReminder2', displayText: 'Team Reminder 2'  },
												{ dataField: 'SelfReminder3', displayText: 'Self Reminder 3'  },
												{ dataField: 'TeamReminder3', displayText: 'Team Reminder 3'  }
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#7thLine').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });	
			 //pie graph
			 $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Pie Data");
	    	console.log(data);
	    	 var total=0;
	    	 
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].SelfDue);      
				}
		  	    
	    	 
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: "Self Due Today Ticket Detail",
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
	                                    	dataField: 'SelfDue',
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
	    	  $('#7thPieGraph').jqxChart({ enableAnimations: true });
	        $('#7thPieGraph').jqxChart(settings);
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
<s:if test="maximizeDivBlock=='7thStackBarGraph'">
<div id='7thStackBarGraph' style="width: 100%; height: 200px" align="center"></div>
</s:if>
<!-- // Edited bt Kamlesh 4-10-2014 -->
<s:elseif test="maximizeDivBlock=='7thColumn2AxesGraph'">
<div id='7thColumn2AxesGraph' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='7thBubbleGraph'">
<div id='7thBubbleGraph' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='7thLine'">
<div id='7thLine' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='7thPieGraph'">
<div id='7thPieGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>
</center>
</body>
</html>