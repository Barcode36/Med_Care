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
//barchar dash1
$.ajax({
    type : "post",
   
    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
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
                        	 { dataField: 'Pending', displayText: 'Pending', color:'#FF0033'},
                             { dataField: 'Snooze', displayText: 'Snooze' , color:'#A0A0A0' },
                             { dataField: 'Missed', displayText: 'Missed',color:'#C87900' },
                             { dataField: 'Done', displayText: 'Resolved', color:'#009933' }
                            ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#jqxChartbarchar').jqxChart(settings);
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

//for 2axes bar dash 1
$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	    type : "json",
	    success : function(data) {
	    	
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
	                        	 { dataField: 'Pending', displayText: 'Pending', color:'#FF0033'},
	                                { dataField: 'Snooze', displayText: 'Snooze' , color:'#A0A0A0' },
	                                { dataField: 'Missed', displayText: 'Missed',color:'#C87900' },
	                                { dataField: 'Done', displayText: 'Resolved', color:'#009933' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#1stColumn2AxesBar').jqxChart(settings);
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

//bubble chart
$.ajax({
		    type : "post",
		   
		    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
		    type : "json",
		    success : function(data) {
		    	
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
			                                     { dataField: 'Snooze', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Snooze' , color:'#A0A0A0'},
			                                     { dataField: 'Missed', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Missed' ,color:'#C87900'},
			                                     { dataField: 'Done', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Resolved', color:'#009933'}
		                                     ] 
		                          }
		                     ]
		             };
		    	    
		    	// setup the chart
		    	 $('#1stBubbleGraph').jqxChart(settings);
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
		//Line Chart
		
		$.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	  	  	    success : function(statusdata) {
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                              '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
	                									'<br /><b>Pending: </b>' +data.Pending+
	                									'<br /><b>Snooze: </b>' +data.Snooze+
	                									'<br /><b>Missed: </b>' +data.Missed+
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
	  	                                   
	  	                                   { dataField: 'Pending', displayText: 'Pending'},
			                                { dataField: 'Snooze', displayText: 'Snooze' },
			                                { dataField: 'Missed', displayText: 'Missed' },
			                                { dataField: 'Done', displayText: 'Resolved' }		
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#1stLine').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });
		
		//Pie chart
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	    type : "json",
	    success : function(data) {
	    
	    	 var total=0;
	    	 
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Pending);
	    		 }
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: "Pending Task Status",
	                  description: " For All Department" ,
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme12',
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
	    	  $('#1stPieGraph').jqxChart({ enableAnimations: true });
	        $('#1stPieGraph').jqxChart(settings);
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
<s:if test="maximizeDivBlock=='1stBarGraph'">
<div id='jqxChartbarchar' style="width: 100%; height: 300px" align="center"></div>
</s:if>
<!-- // Edited bt Kamlesh 4-10-2014 -->
<s:elseif test="maximizeDivBlock=='1stColumn2AxesBar'">
<div id='1stColumn2AxesBar' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='1stBubbleGraph'">
<div id='1stBubbleGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='1stLine'">
<div id='1stLine' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='1stPieGraph'">
<div id='1stPieGraph' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>
</center>
</body>
</html>