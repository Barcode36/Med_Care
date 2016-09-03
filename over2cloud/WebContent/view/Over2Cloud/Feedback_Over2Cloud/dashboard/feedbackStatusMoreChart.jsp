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
        //	alert("?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val());
        	$.ajax({
        	    type : "post",
        	    url : "view/Over2Cloud/feedback/jsonChartFeedbackModes.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
        	    type : "json",
        	    success : function(data) {
        	    	console.log("Modesssssss");
        	    	console.log(data);
        	    	sampleData = data;
        	    	 // prepare jqxChart settings
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
                        dataField: 'Mode',
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
										{ dataField: 'Positive', displayText: 'Positive' },
	                                    { dataField: 'Negative', displayText: 'Negative' },
	                                    { dataField: 'Pending', displayText: 'Pending' },
	                                    { dataField: 'NoAct', displayText: 'No Futher Action Required' },
	                                    { dataField: 'TicketOpened', displayText: 'Ticket Opened' },
	                                   
	                                ]
                        }
                    ]
            };
                    // create the chart
                    $('#chartContainer').jqxChart(settings);
                    // get the chart's instance
                    var chart = $('#chartContainer').jqxChart('getInstance');
                    // color scheme drop down
                    var colorsSchemesList = ["scheme01", "scheme02", "scheme03", "scheme04", "scheme05", "scheme06", "scheme07", "scheme08"];
                    $("#dropDownColors").jqxDropDownList({ source: colorsSchemesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 200 });
                    $('#dropDownColors').on('change', function (event) {
                        var value = event.args.item.value;
                        chart.colorScheme = value;
                        chart.update();
                    });
                  
                    // series type drop down
                    var seriesList = ["splinearea", "spline", "column", "scatter", "stackedcolumn", "stackedsplinearea", "stackedspline"];
                    $("#dropDownSeries").jqxDropDownList({ source: seriesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 200 });
                    $('#dropDownSeries').on('select', function (event) {
                        var args = event.args;
                        if (args) {
                            var value = args.item.value;
                            var isLine = value.indexOf('line') != -1;
                            var isArea = value.indexOf('area') != -1;
                            //chart.categoryAxis.valuesOnTicks = isLine || isArea;
                            var group = chart.seriesGroups[0];
                            group.series[0].opacity = group.series[1].opacity = isArea ? 0.7 : 1;
                            group.series[0].lineWidth = group.series[1].lineWidth = isLine ? 2 : 1;
                            chart.seriesGroups[0].type = value;
                            chart.update();
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
 <table style="width: 680px">
        <tr>
            <td style="padding-left: 50px;">
                <p style="font-family: Verdana; font-size: 12px;">Select Chart Type: 
                </p><div id='dropDownSeries'>
                </div>
                
            </td>
            <td>
                <p style="font-family: Verdana; font-size: 12px;">Select Color Scheme:
                </p>
                <div id='dropDownColors'>
                </div>
            </td>
        </tr>
    </table>
    </center>
        <div id='chartContainer' style="width: 100%; height: 270px">
        </div>
    
   
</body>
</html>