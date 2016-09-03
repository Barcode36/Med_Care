function stackedcolumnchar() {
	var  sampleData =null;


	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: '              ',
	                description: '        ',
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
	                                { dataField: 'Pending', displayText: 'Pending' },
	                                { dataField: 'Snooze', displayText: 'Snooze' },
	                                { dataField: 'Missed', displayText: 'Missed' },
	                                { dataField: 'Done', displayText: 'Resolved' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#maxjqxChart1').jqxChart(settings);
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
}//end here

function dashTwoAxescolumnchart() {
	var  sampleData =null;
	
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: '     ',
	                description: '      ',
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
	                                { dataField: 'Pending', displayText: 'Pending' },
	                                { dataField: 'Snooze', displayText: 'Snooze' },
	                                { dataField: 'Missed', displayText: 'Missed' },
	                                { dataField: 'Done', displayText: 'Resolved' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#maxjqxChart2Axes').jqxChart(settings);
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
}//dashTwoAxescolumnchart() End
function dashbubbleChart() {
	var  sampleData =null;
		$.ajax({
		    type : "post",
		   
		    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
		    type : "json",
		    success : function(data) {
		    	
		    	console.log(data);
		    	sampleData = data;
		    	 var settings = {
		                 title: '  ',
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
		                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending' },
		                                     { dataField: 'Snooze', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Snooze' },
		                                     { dataField: 'Missed', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Missed' },
		                                     { dataField: 'Done', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Resolved'}
		                                     ]
		                          }
		                     ]
		             };
		    	    
		    	// setup the chart
		    	 $('#maxjqxChartBubbleTotalCompl').jqxChart(settings);
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
	}//dashbubbleChart() End
function showbarfreq() {
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: '  ',
	                description: '  ',
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
	                                { dataField: 'Yearly Total', displayText: 'Yearly Total' },
	                                { dataField: 'Yearly Missed', displayText: 'Yearly Missed' },
	                                { dataField: 'Monthly Total', displayText: 'Monthly Total' },
	                                { dataField: 'Monthly Missed', displayText: 'Monthly Missed' },
	                                { dataField: 'Weekly Total', displayText: 'Weekly Total'},
	                                { dataField: 'Weekly Missed', displayText: 'Weekly Missed' }
	                            ]
	                            
	                        }
	                    ]
	            };
	    	// setup the chart
	    	
	        $('#maxjqxChartStackFreq').jqxChart(settings);
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
	
}//end here

function showbarfreqtwoAxes() {
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: '  ',
	                description: '  ',
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
	                                { dataField: 'Yearly Total', displayText: 'Yearly Total' },
	                                { dataField: 'Yearly Missed', displayText: 'Yearly Missed' },
	                                { dataField: 'Monthly Total', displayText: 'Monthly Total' },
	                                { dataField: 'Monthly Missed', displayText: 'Monthly Missed' },
	                                { dataField: 'Weekly Total', displayText: 'Weekly Total'},
	                                { dataField: 'Weekly Missed', displayText: 'Weekly Missed'}
	                            ]
	                        }
	                    ]
	            };
	    	// setup the chart
	        $('#maxjqxChart2AxesFreq').jqxChart(settings);
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
	
}//end here 

function freBubble() {
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
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
	                                     { dataField: 'Yearly Total', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Yearly Total' },
	                                     { dataField: 'Yearly Missed', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Yearly Missed' },
	                                     { dataField: 'Monthly Total', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Monthly Total' },
	                                     { dataField: 'Monthly Missed', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Monthly Missed'},
	                                     { dataField: 'Weekly Total', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Weekly Total'},
	                                     { dataField: 'Weekly Missed', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Weekly Missed'}
	                                     ]
	                          }
	                     ]
	             };
	    	    
	    	// setup the chart
	    	 $('#jqxChartFreqBubble').jqxChart(settings);
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
}//end here
function showbarAgieng() {
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
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
	                                { dataField: 'This Year', displayText: 'This Year' },
	                                { dataField: 'This Month', displayText: 'This Month' },
	                                { dataField: 'This Week', displayText: 'This Week'  },
	                              
	                            ]
	                        }
	                    ]
	            };
	    	// setup the chart
	        $('#jqxChartBarAgieng').jqxChart(settings);
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
}//end here
function showbarAgiengtwoAxes() {
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
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
	                                { dataField: 'This Year', displayText: 'This Year' },
	                                { dataField: 'This Month', displayText: 'This Month' },
	                                { dataField: 'This Week', displayText: 'This Week'  },
	                              
	                            ]
	                        }
	                    ]
	            };
	    	// setup the chart
	        $('#jqxChart2AxesAgieng').jqxChart(settings);
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
}//end here
function showbarTodays() {
	var  sampleData =null;
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
	                                { dataField: 'Self Due', displayText: 'Self Due' },
	                                { dataField: 'Team Due', displayText: 'Team Due' },
	                                { dataField: 'Self Reminder 1', displayText: 'Self Reminder 1'  },
	                                { dataField: 'Team Reminder 1', displayText: 'Team Reminder 1'  },
	                                { dataField: 'Self Reminder 2', displayText: 'Self Reminder 2'  },
	                                { dataField: 'Team Reminder 2', displayText: 'Team Reminder 2'  },
	                                { dataField: 'Self Reminder 3', displayText: 'Self Reminder 3'  },
	                                { dataField: 'Team Reminder 3', displayText: 'Team Reminder 3'  }
	                                
	                               
	                               
	                            ]
	                        }
	                    ]
	            };
	    	// setup the chart
	        $('#jqxChartBarTodayRem').jqxChart(settings);
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
}//end here
function showbarTodaystwoAxes() {
	var  sampleData =null;
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
	                                { dataField: 'Self Due', displayText: 'Self Due' },
	                                { dataField: 'Team Due', displayText: 'Team Due' },
	                                { dataField: 'Self Reminder 1', displayText: 'Self Reminder 1'  },
	                                { dataField: 'Team Reminder 1', displayText: 'Team Reminder 1'  },
	                                { dataField: 'Self Reminder 2', displayText: 'Self Reminder 2'  },
	                                { dataField: 'Team Reminder 2', displayText: 'Team Reminder 2'  },
	                                { dataField: 'Self Reminder 3', displayText: 'Self Reminder 3'  },
	                                { dataField: 'Team Reminder 3', displayText: 'Team Reminder 3'  }
	                                
	                               
	                               
	                            ]
	                        }
	                    ]
	            };
	    	// setup the chart
	        $('#jqxChart2AxesToday').jqxChart(settings);
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
}//end here
function AgiengBubble() {
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
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
	                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	                 source: sampleData,
	                 xAxis:
	                     {
	                         dataField: 'dept',
	                         valuesOnTicks: false
	                     },
	                 colorScheme: 'scheme01',
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
	                                     { dataField: 'This Year', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'This Year' },
	                                     { dataField: 'This Month', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'This Month' },
	                                     { dataField: 'This Week', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'This Week' }
	                                     
	                                     ]
					           }              
	                     ]
	             };
	    	    
	    	// setup the chart
	    	 $('#jqxChartAgiengBubble').jqxChart(settings);
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
}//end here



function bubbleChartTodaysRem() {
	var  sampleData =null;
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
	                                     { dataField: 'Self Due', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Self Due' },
	                                     { dataField: 'Team Due', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Team Due' },
	                                     { dataField: 'Self Reminder 1', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 1' },
	                                     { dataField: 'Team Reminder 1', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 1'},
	                                     { dataField: 'Self Reminder 2', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 2'},
	                                     { dataField: 'Team Reminder 2', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 2'},
	                                     { dataField: 'Self Reminder 3', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 3'},
	                                     { dataField: 'Team Reminder 3', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 3'}
	                                     ]
	                          }
	                     ]  
                         
	             };
	    	    
	    	// setup the chart
	    	 $('#jqxChartTodayRemBubble').jqxChart(settings);
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
	
}//end here
function prevMonthStackedBar() {
	var  sampleData =null;


	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: '              ',
	                description: '        ',
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
	                                { dataField: 'Pending', displayText: 'Pending' },
	                                { dataField: 'Done', displayText: 'Resolved' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#jqxChartBarPrevMonth').jqxChart(settings);
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
}//end here

function prevMonthColumn2Axes() {
	var  sampleData =null;


	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var settings = {
	    	    	
	    			title: '              ',
	                description: '        ',
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
	                                { dataField: 'Pending', displayText: 'Pending' },
	                                { dataField: 'Done', displayText: 'Resolved' }
	                            ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#jqxChart2AxesPrevMonth').jqxChart(settings);
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
}//end  here 
function bubbleChartPrevMonthCompl() {
	
	var  sampleData =null;
	$.ajax({
	    type : "post",
	   
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	 var settings = {
	                 title: '  ',
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
	                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending' },
	                                     { dataField: 'Done', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Resolved'}
	                                     ]
	                          }
	                     ]
	             };
	    	    
	    	// setup the chart
	    	 $('#jqxChartPrevMonthComplBubble').jqxChart(settings);
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
}