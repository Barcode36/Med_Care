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

//Bar Chart
$.ajax({
    type : "post",
    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
    type : "json",
    success : function(data) {
    	var total=0;
    	//alert(data.length);
    	//console.log(data);
    	for (var i=0;i<data.length-1;i++){
    		total=total+parseFloat(data[i].Pending)+parseFloat(data[i].HighPriority)+parseFloat(data[i].Snooze)+parseFloat(data[i].Ignore)+parseFloat(data[i].Resolved);
    	}
    	if(total==0||isNaN(total)){
	  		
	  	    $('#levelChart1').html("<center><img src='images/Column-Chart-2Axes.png' width='400' height='180' style='opacity:.4'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
	  	    }else{
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
                        dataField: 'Level',
                        showTickMarks: true,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        showGridLines: false,
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
                                description: 'Ticket Counter',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
										{ dataField: 'Pending', displayText: 'Pending', color:'#FF0033' },
										{ dataField: 'HighPriority', displayText: 'High Priority', color:'#003399'  },
										{ dataField: 'Snooze', displayText: 'Snooze', color:'#A0A0A0'  },
										{ dataField: 'Resolved', displayText: 'Resolved', color:'#009933'  }
                                ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#levelChart1').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#levelChart1').jqxChart({ enableAnimations: false });
            $('#levelChart1').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#levelChart1').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#levelChart1').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
}},
   error: function() {
        alert("error");
    }
 });
 
 //column 2 Axes
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
    type : "json",
    success : function(data) {
    	var total=0;
    	//alert(data.length);
    	//console.log(data);
    	for (var i=0;i<data.length-1;i++){
    		total=total+parseFloat(data[i].Pending)+parseFloat(data[i].HighPriority)+parseFloat(data[i].Snooze)+parseFloat(data[i].Ignore)+parseFloat(data[i].Resolved);
    	}
    	if(total==0||isNaN(total)){
	  		
	  	    $('#levelChart2').html("<center><img src='images/Column-Chart-2Axes.png' width='400' height='180' style='opacity:.4'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
	  	    }else{
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
                        dataField: 'Level',
                        showTickMarks: true,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        showGridLines: false,
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
                                description: 'Ticket Counter',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
										{ dataField: 'Pending', displayText: 'Pending', color:'#FF0033' },
										{ dataField: 'HighPriority', displayText: 'High Priority', color:'#003399'  },
										{ dataField: 'Snooze', displayText: 'Snooze', color:'#A0A0A0'  },
										{ dataField: 'Resolved', displayText: 'Resolved', color:'#009933'  }
                                ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#levelChart2').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#levelChart').jqxChart({ enableAnimations: false });
            $('#levelChart').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#levelChart1').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#levelChart1').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
}},
   error: function() {
        alert("error");
    }
 });
 
 //Line Chart Level
 $.ajax({
	  	    type : "post",
	  	  url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
	  	    success : function(data) {
	  	    	var total=0;
		    	//alert(data.length);
		    	//console.log(data);
		    	for (var i=0;i<data.length-1;i++){
		    		total=total+parseFloat(data[i].Pending)+parseFloat(data[i].HighPriority)+parseFloat(data[i].Snooze)+parseFloat(data[i].Ignore)+parseFloat(data[i].Resolved);
		    	}
		    	//alert(total);
		    	if(total==0||isNaN(total)){
			  		
	    	  	    $('#levelChart3').html("<center><img src='images/line-graph.jpg' width='700' height='300' style='opacity:.4'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
	    	  	    }else{
	  	    	sampleData=data;
	  	    	
	  	    var toolTipCustomFormatFn = function (key,value) {
          var data1=data[value];
            return                              '<DIV style="text-align:left"><b>Department: </b>' + data1.Level+
            									'<br /><b>Pending: </b>' +data1.Pending+
            									'<br /><b>High Priority: </b>' +data1.HighPriority+
            									'<br /><b>Snooze: </b>' +data1.Snooze+
            									'<br /><b>Done: </b>' +data1.Resolved+
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
	                        dataField: 'Level',
	                       
	                        showGridLines: false,
	                    },
	                colorScheme: 'scheme01',
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
										{ dataField: 'HighPriority', displayText: 'High Priority' },
										{ dataField: 'Snooze', displayText: 'Snooze' },
										{ dataField: 'Resolved', displayText: 'Resolved' }
										  	                        
	                                ]
	                        }
	                    ]
	            };
	            
	            $('#levelChart3').jqxChart(settings);
	  	    	
	  	}},
	  	   error: function() {
	              alert("error");
	          }
	  	 });
 
 //bubble
 $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
	    type : "json",
	    success : function(data) {
	    	var total=0;
	    	//alert(data.length);
	    	//console.log(data);
	    	for (var i=0;i<data.length-1;i++){
	    		total=total+parseFloat(data[i].Pending)+parseFloat(data[i].HighPriority)+parseFloat(data[i].Snooze)+parseFloat(data[i].Ignore)+parseFloat(data[i].Resolved);
	    	}
	    	if(total==0||isNaN(total)){
		  		
		  	    $('#levelChart4').html("<center><img src='images/Column-Chart-2Axes.png' width='700' height='300' style='opacity:.4'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
		  	    }else{
	    //	console.log(data);
	    	sampleData = data;
	    	 var settings = {
	                 title: ' ',
	                 description: 'For All Levels ',
	                 enableAnimations: true,
	                 showLegend: true,
	                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	                 source: sampleData,
	                 xAxis:
	                     {
	                         dataField: 'Level',
	                         valuesOnTicks: false
	                     },
	                 colorScheme: 'scheme01',
	                 seriesGroups:
	                     [
	                         {
	                             type: 'bubble',
	                             valueAxis:
	                             {
	                            	 	minValue: 0,
		                                displayValueAxis: true,
		                                axisSize: 'auto',
	                                 description: 'Ticket Counter'
	                                 
	                             },
	                             series: [
	                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending',color:'#FF0033' },
	                                     { dataField: 'HightPriority', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'High Prority',color:'#003399' },
	                                     { dataField: 'Snooze', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Snooze',color:'#A0A0A0' },
	                                     { dataField: 'Resolved', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Resolved',color:'#009933' }
	                                     
	                                     ]
					           }              
	                     ]
	             };
	    	    
	    	// setup the chart
	    	 $('#levelChart4').jqxChart(settings);
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
	    	
	}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
 
 //Pending Pie
 $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
	    type : "json",
	    success : function(data) {
	    	
   		
	    	
	    	sampleData = data;
	    	var total=0;
	    	 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Pending);
	    	 } if(total==0||isNaN(total)){
			  		
	    	  	    $('#levelChart5').html("<center><img src='images/noPie2.png' width='600' height='300' style='opacity:.5'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
	    	  	    }else{
	    	  var settings = {
	                  title: "Pending Ticket Status ",
	                  description: " For All Levels",
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme02',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: 'Pending',
	                                    	displayText: 'Level',
	                                    	labelRadius: 200,
	                                    	initialAngle: 15,
	                                    	radius: 75,
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
	    	  $('#levelChart5').jqxChart({ enableAnimations: true });
	        $('#levelChart5').jqxChart(settings);
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
	    	
	}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
</script>
<title>Insert title here</title>
</head>
<body>
<center>


<s:if test="maximizeDivBlock=='level_StackedBar'">
<div id='levelChart1' style="width: 100%; height: 350px" align="center"></div>
</s:if>
<s:elseif test="maximizeDivBlock=='level_Column2Axes'">
<div id='levelChart2' style="width: 100%; height: 350px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='level_LineChart'">
<div id='levelChart3' style="width: 100%; height: 350px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='level_BubbleChart'">
<div id='levelChart4' style="width: 100%; height: 350px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='level_PiePendingChart'">
<div id='levelChart5' style="width: 100%; height: 350px" align="center"></div>
</s:elseif>
<s:elseif test="maximizeDivBlock=='level_table'">
<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="40%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Level</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;PN</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;HP</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;SN</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;RS</b></td>
	</tr>
</table>
<s:iterator id="rsCompl1dfrgcvxfzcvzdf"  status="status" value="%{leveldashObj.dashList}" >
	<table border="1" width="100%" align="center">
 	<tr>
 	    <td align="left" width="40%"   bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/></b></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.pc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','High Priority','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.hpc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.sc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.rc"/></b></a></td>
		
 	</tr>
 	</table>
</s:iterator>
</s:elseif>


</center>
</body>
</html>