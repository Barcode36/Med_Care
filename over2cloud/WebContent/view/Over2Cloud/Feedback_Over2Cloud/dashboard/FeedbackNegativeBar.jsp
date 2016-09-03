<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'></title>
    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdraw.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxchart.core.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxslider.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
   
    <script type="text/javascript">
        $(document).ready(function () {
        	$.ajax({
        	    type : "post",
        	    url : "view/Over2Cloud/feedback/NegativeFeedPieJsonData.action?block=Stage 1st&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
        	    type : "json",
        	    success : function(data) {
        	    	/* console.log("New dataaaaaa");
        	    	console.log(data); */
        	    	sampleData = data;
        	    	 // prepare jqxChart settings
                       	var settings = {
    	    	
    			title: ' Negative Status Of Tickets For All Mode At Stage 1st',
    			description: "Data From "+$("#sdate").val()+" To "+$("#edate").val(),
                enableAnimations: true,
                showLegend: true,
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'mode',
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
                            columnsGapPercent: 50,
                            seriesGapPercent: 5,
                            valueAxis:
                            {
                                minValue: 0,
                                displayValueAxis: true,
                                description: 'Mode Count',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
										{ dataField: 'Total', displayText: 'Total' },
	                                    { dataField: 'Pending', displayText: 'Pending' },
	                                    { dataField: 'ActionTaken', displayText: 'Action Taken' },
	                                    { dataField: 'NFA', displayText: 'No Further Action' }
	                                   
	                                   
	                                ]
                        }
                    ]
            };
                    // create the chart
                    $('#chartContainer2').jqxChart(settings);
                    // get the chart's instance
                    var chart = $('#chartContainer2').jqxChart('getInstance');
                    // color scheme drop down
                    var colorsSchemesList = ["scheme01", "scheme02", "scheme03", "scheme04", "scheme05", "scheme06", "scheme07", "scheme08"];
                    $("#dropDownColors2").jqxDropDownList({ source: colorsSchemesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 200 });
                    $('#dropDownColors2').on('change', function (event) {
                        var value = event.args.item.value;
                        chart.colorScheme = value;
                        chart.update();
                    });
                  
                    // series type drop down
                    var seriesList = ["splinearea", "spline", "column", "scatter", "stackedcolumn", "stackedsplinearea", "stackedspline"];
                    $("#dropDownSeries2").jqxDropDownList({ source: seriesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 200 });
                    $('#dropDownSeries2').on('select', function (event) {
                        var args = event.args;
                       // alert(JSON.stringify(args));
                        if (args) {
                            var value = args.item.value;
                            var isLine = value.indexOf('line') != -1;
                            var isArea = value.indexOf('area') != -1;
                            //chart.categoryAxis.valuesOnTicks = isLine || isArea;
                            var group = chart.seriesGroups[0];
                            group.series[0].opacity = group.series[0].opacity = isArea ? 0.7 : 1;
                            group.series[0].lineWidth = group.series[0].lineWidth = isLine ? 2 : 1;
                            chart.seriesGroups[0].type = value;
                            chart.update();
                        }
                    });
                    
                    var colorsSchemesList1 = ["Stage 1st", "Stage 2nd"];
                    $("#dropDownColors3").jqxDropDownList({ source: colorsSchemesList1, selectedIndex: 0, width: '100', height: '25', dropDownHeight: 60,dropDownWidth: 100 });
                   
                    $('#dropDownColors3').on('change', function (event) {
                        var value = event.args.item.value;
                       
                        if(value=='Stage 2nd'){
                        $.ajax({
                    	    type : "post",
                    	    url : "view/Over2Cloud/feedback/NegativeFeedPieJsonData.action?block="+value+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
                    	    type : "json",
                    	    success : function(data) {
                    	    	var total=0;
                    	  	    for (var int = 0; int < data.length; int++) {
                    	total=total+parseFloat(data[int].counter);
                    	}
                    	    	sampleData = data;
                    	    	 // prepare jqxChart settings
                                     	var settings = {
    	    	
    			title: ' Negative Status Of Tickets For All Mode At'+value,
    			description: "Data From "+$("#sdate").val()+" To "+$("#edate").val(),
                enableAnimations: true,
                showLegend: true,
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'mode',
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
                            columnsGapPercent: 50,
                            seriesGapPercent: 5,
                            valueAxis:
                            {
                                minValue: 0,
                                displayValueAxis: true,
                                description: 'Mode Count',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
										{ dataField: 'TO', displayText: 'Ticket Opened' },
	                                    { dataField: 'Pending', displayText: 'Pending' },
	                                    { dataField: 'ActionTaken', displayText: 'Action Taken' }
	                                   
	                                   
	                                   
	                                ]
                        }
                    ]
            };
                                    $('#chartContainer2').jqxChart(settings);
                                    
                    	    },
                     	   error: function() {
                     	        alert("error");
                     	    }
                     	 });
                        }else{
                        	
                        	 $.ajax({
                         	    type : "post",
                         	    url : "view/Over2Cloud/feedback/NegativeFeedPieJsonData.action?block="+value+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
                         	    type : "json",
                         	    success : function(data) {
                         	    	var total=0;
                         	  	    for (var int = 0; int < data.length; int++) {
                         	total=total+parseFloat(data[int].counter);
                         	}
                         	    	sampleData = data;
                         	    	 // prepare jqxChart settings
                                          	var settings = {
         	    	
                    title: ' Negative Status Of Tickets For All Mode At'+value,
         			description: "Data From "+$("#sdate").val()+" To "+$("#edate").val(),
                     enableAnimations: true,
                     showLegend: true,
                     padding: { left: 5, top: 5, right: 5, bottom: 5 },
                     titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
                     source: sampleData,
                     categoryAxis:
                         {
                             text: 'Category Axis',
                             textRotationAngle: 0,
                             dataField: 'mode',
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
                                 columnsGapPercent: 50,
                                 seriesGapPercent: 5,
                                 valueAxis:
                                 {
                                     minValue: 0,
                                     displayValueAxis: true,
                                     description: 'Mode Count',
                                     axisSize: 'auto',
                                     tickMarksColor: '#888888',
                                     gridLinesColor: '#777777'
                                 },
                                 series: [
     										{ dataField: 'Total', displayText: 'Total' },
	                                    { dataField: 'Pending', displayText: 'Pending' },
	                                    { dataField: 'ActionTaken', displayText: 'Action Taken' },
	                                    { dataField: 'NFA', displayText: 'No Further Action' }
     	                                   
     	                                   
     	                                   
     	                                ]
                             }
                         ]
                 };
                                         $('#chartContainer2').jqxChart(settings);
                                         
                         	    },
                          	   error: function() {
                          	        alert("error");
                          	    }
                          	 });
                        	
                        	
                        	
                        	
                        }
                     
                        
                    }); 
                  
             
        	    	
        	},
        	   error: function() {
        	        alert("error");
        	    }
        	 });
        });
           
    </script>
</head>
<body class='default'>
<center>
 <table style="width: 100%">
        <tr>
        
        <td>
                <p style="font-family: Verdana; font-size: 12px;">Select Stage:
                </p></td><td>
                <div id='dropDownColors3'>
                </div>
            </td>
            <td style="padding-left: 50px;">
                <p style="font-family: Verdana; font-size: 12px;">Select Chart Type: 
                </p></td><td><div id='dropDownSeries2'>
                </div>
                
            </td>
            <td>
                <p style="font-family: Verdana; font-size: 12px;">Select Color Scheme:
                </p></td><td>
                <div id='dropDownColors2'>
                </div>
            </td>
        </tr>
    </table>
    </center>
        <div id='chartContainer2' style="width: 100%; height: 270px">
        </div>
    
   
</body>
</html>