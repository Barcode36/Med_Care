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
        	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/FeedbackChartsJson.action?block=7rdBlock",
        	    type : "json",
        	    success : function(data) {
        	    	console.log("New data1");
        	    	console.log(data);
        	    	sampleData = data;
        	    	 // prepare jqxChart settings
                    var settings = {
                        title: " ",
                        description: " ",
                        enableAnimations: true,
                        animationDuration: 1000,
                        enableAxisTextAnimation: true,
                        showLegend: true,
                        padding: { left: 5, top: 5, right: 5, bottom: 5 },
                        titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
                        source: data,
                        xAxis:
                            {
                        	 	dataField: 'TypeName',
                                unitInterval: 1,
                                gridLinesInterval: 2,
                                valuesOnTicks: false
                            },
                        colorScheme: 'scheme05',
                        seriesGroups:
                            [
                                {
                                    type: 'stackedcolumn',
                                    columnsGapPercent: 50,
                                    alignEndPointsWithIntervals: true,
                                    valueAxis:
                                    {
                                    	 minValue: 0,
                                         displayValueAxis: true,
                                         description: 'Ticket Counter',
                                         axisSize: 'auto',
                                         tickMarksColor: '#888888',
                                         gridLinesColor: '#777777',
                                    },
                                    series: [
                                            { dataField: 'TypeTotal', displayText: 'Feedback Type Total', opacity: 1, lineWidth: 1, symbolType: 'circle', fillColorSymbolSelected: 'white', radius: 15 ,showLabels: true,
                                            	labelsVerticalAlignment: 'top', 
                                            	labelsOffset: { x: 0, y: -20 },
                                                formatFunction: function (value) {
                                                	if(value==0)
                                                		return '';
                                                    return parseFloat(value ).toFixed(0);
                                                }},
                                                
                                                { dataField: 'TypeToday', displayText: 'Feeback Type Today', opacity: 1, lineWidth: 1, symbolType: 'circle', fillColorSymbolSelected: 'white', radius: 15 ,showLabels: true,
                                                	labelsVerticalAlignment: 'top', 
                                                	labelsOffset: { x: 0, y: -20 },
                                                    formatFunction: function (value) {
                                                    	if(value==0)
                                                    		return '';
                                                        return parseFloat(value ).toFixed(0);
                                                    }},
                                          
                                        ]
                                }
                            ]
                    };
                    // create the chart
                    $('#chartContainer3').jqxChart(settings);
                    // get the chart's instance
                    var chart = $('#chartContainer3').jqxChart('getInstance');
                    // color scheme drop down
                    var colorsSchemesList = ["scheme01", "scheme02", "scheme03", "scheme04", "scheme05", "scheme06", "scheme07", "scheme08"];
                    $("#dropDownColors3").jqxDropDownList({ source: colorsSchemesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 100 });
                    $('#dropDownColors3').on('change', function (event) {
                        var value = event.args.item.value;
                        chart.colorScheme = value;
                        chart.update();
                    });
                  
                    // series type drop down
                    var seriesList = ["splinearea", "spline", "column", "scatter", "stackedcolumn", "stackedsplinearea", "stackedspline"];
                    $("#dropDownSeries3").jqxDropDownList({ source: seriesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 100 });
                    $('#dropDownSeries3').on('select', function (event) {
                        var args = event.args;
                       // alert(JSON.stringify(args));
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
                    // auto update timer
                   
             
        	    	
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
                </p><div id='dropDownSeries3'>
                </div>
                
            </td>
            <td>
                <p style="font-family: Verdana; font-size: 12px;">Select Color Scheme:
                </p>
                <div id='dropDownColors3'>
                </div>
            </td>
        </tr>
    </table>
    </center>
        <div id='chartContainer3' style="width: 100%; height: 270px">
        </div>
    
   
</body>
</html>