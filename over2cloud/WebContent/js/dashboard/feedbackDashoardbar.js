
function showBarChart(){
	
	$("#jqxChart").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	var  sampleData =null;
$.ajax({
    type : "post",
    url : "view/Over2Cloud/feedback/jsonChartDataDept.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
    type : "json",
    success : function(data) {
    	sampleData = data;
    	console.log("Dept");
    	console.log(data);
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
                                alternatingBackgroundColor: '#E5E5E5',
                                alternatingBackgroundColor2: '#F5F5F5',
                                alternatingBackgroundOpacity: 0.5
                            },
                            series: 
                        	[
                                { dataField: 'Pending', displayText: 'Pending', color: '#FF0033' },
                                { dataField: 'High', displayText: 'High Priority',color:'#003399' },
                                { dataField: 'Snooze', displayText: 'Snooze',color: '#A0A0A0' },
                                { dataField: 'Ignore', displayText: 'Ignore', color: 'Purple' },
                                { dataField: 'Re-Assign', displayText: 'Re-Assign' , color: 'Pink' },
                                { dataField: 'Noted', displayText: 'Noted' , color: '#990033' },
                                { dataField: 'Resolved', displayText: 'Resolved', color: '#009933' }
                            ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#jqxChart').jqxChart(settings);
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
        alert("error");
    }
 });
}//end here

function showBarChart2(){
var  sampleData =null;
$.ajax({
    type : "post",
    url : "view/Over2Cloud/feedback/jsonChartFeedbackModes.action",
    type : "json",
    success : function(data) {
    	console.log("Modes");
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
										{ dataField: 'Paper', displayText: 'Paper' },
	                                    { dataField: 'SMS', displayText: 'SMS' },
	                                    { dataField: 'Mobile Tab', displayText: 'MobApp' },
	                                    { dataField: 'Kiosk', displayText: 'Kiosk' },
	                                    { dataField: 'IVRS', displayText: 'IVRS' },
	                                    { dataField: 'Online', displayText: 'Online' }
	                                ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#block2').jqxChart(settings);
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
        alert("error");
    }
 });

}//end here

//dashboard 2 pie
function dash2pie(){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/jsonChartFeedbackModes.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	var total=0;
	  	    for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Counter);
	}
	    	  var settings = {
	                  title: "",
	                  description: "",
	                  enableAnimations: true,
	              showLegend: true,
	              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme06',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    {
	                                    	dataField: 'Modes',
	                                    	displayText: 'Category',
	                                    	labelRadius: 70,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#block2').jqxChart({ enableAnimations: true });
	        $('#block2').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#CategChart').jqxChart({ enableAnimations: false });
	            $('#CategChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#CategChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#CategChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
}

function lineStatus() {
	
	$("#jqxChart").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	var  sampleData =null;
	$.ajax({
  	  	    type : "post",
  	  	    url : "view/Over2Cloud/feedback/jsonChartDataDept.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
  	  	    success : function(statusdata) {
  	  	    	
  	  	    	
  	  	    	sampleData=statusdata;
  	  	    	
  	  	    var toolTipCustomFormatFn = function (key,value) {
              var data=statusdata[value];
                return                              '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
                	'<br /><b>Pending: </b>' +data.Pending+
                	'<br /><b>Snooze: </b>' +data.Snooze+
                	'<br /><b>Resolved: </b>' +data.Resolved+
                	'<br /><b>Ignore: </b>' +data.Ignore+
                	'<br /><b>Noted: </b>' +data.Noted+
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
                                { dataField: 'High', displayText: 'High Priority' },
                                { dataField: 'Snooze', displayText: 'Snooze' },
                                { dataField: 'Ignore', displayText: 'Ignore' },
                                { dataField: 'Re-Assign', displayText: 'Re-Assign'  },
                                { dataField: 'Noted', displayText: 'Noted'  },
                                { dataField: 'Resolved', displayText: 'Resolved' }
	  	                        
  	                                ]
  	                        }
  	                    ]
  	            };
  	            
  	            $('#jqxChart').jqxChart(settings);
  	  	    	
  	  	},
  	  	   error: function() {
  	              alert("error");
  	          }
  	  	 });
	}//end here

function showLevelColumn2Axes(){
	
	$("#jqxChart").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/jsonChartDataDept.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
	    type : "json",
	    success : function(data) {
	    	sampleData = data;
	    	console.log("Dept");
	    	console.log(data);
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
	                                
                                { 
                                	dataField: 'Pending', 
                                	displayText: 'Pending', 
                                	color: '#FF0033', 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top', 
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                },
                                { 
                                	dataField: 'High', 
                                	displayText: 'High Priority',
                                	color:'#003399', 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top',
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                },
                                { 
                                	dataField: 'Snooze', 
                                	displayText: 'Snooze',
                                	color: '#A0A0A0', 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top',
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                },
                                { 
                                	dataField: 'Ignore', 
                                	displayText: 'Ignore', 
                                	color: 'Purple', 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top',
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                },
                                { 
                                	dataField: 'Re-Assign', 
                                	displayText: 'Re-Assign' , 
                                	color: 'Pink', 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top',
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                },
                                { 
                                	dataField: 'Noted', 
                                	displayText: 'Noted' , 
                                	color: '#990033' , 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top',
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                },
                                { 
                                	dataField: 'Resolved', 
                                	displayText: 'Resolved', 
                                	color: '#009933' , 
                                	showLabels: true,
                                	labelsVerticalAlignment: 'top',
                                	labelsOffset: { x: 0, y: -20 },
                                    formatFunction: function (value) {
                                    	if(value==0)
                                    		return '';
                                        return parseFloat(value ).toFixed(0);
                                    }
                                	
                                }
	                            
                                
                                ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#jqxChart').jqxChart(settings);
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
	        alert("error");
	    }
	 });
}

function bubbleLevel(){
	$("#jqxChart").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/jsonChartDataDept.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
	    type : "json",
	    success : function(data) {
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
	                            	 	minValue: 0,
	                                displayValueAxis: true,
	                                axisSize: 'auto',
	                                 description: 'Ticket Counter'
	                                 
	                             },
	                             series: [
	                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending',color:'#FF0033' },
	                                     { dataField: 'HighPriority', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'High Prority',color:'#003399' },
	                                     { dataField: 'Snooze', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Snooze',color:'#A0A0A0' },
	                                     { dataField: 'Ignore', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Ignore',color:'Purple' },
	                                     { dataField: 'Re-Assign', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Re-Assign',color:'Pink' },
	                                     { dataField: 'Noted', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Noted',color:'#990033' },
	                                     { dataField: 'Resolved', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Resolved',color:'#009933' }
	                                     ]
	           }              
	                     ]
	             };
	    	
	    	// setup the chart
	    	 $('#jqxChart').jqxChart(settings);
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

//maximise polar
function maximisepolar(){
	$("#maxmizeViewPolar").dialog({height:580,width:1200});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/maximizeDivplr.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });

	
}

function showMore(){
	$("#maxmizeViewPolar").dialog({height:400,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/FeedbackModeMoreChart.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
	
	
}
function showMore2(){
	$("#maxmizeViewPolar").dialog({height:400,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/FeedbackNegativeBar.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
	
}

function showCharts(){
	$("#maxmizeViewPolar").dialog({height:400,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/FeedbackCharts.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}
function NegativeFeedAnlysisPie(){
	$("#maxmizeViewPolar").dialog({height:600,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/NegativeFeedPie.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

function showChartsType(){
	$("#maxmizeViewPolar").dialog({height:400,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/FeedbackChartType.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}


function show2ndblockpie(){
	$("#maxmizeViewPolar").dialog({height:600,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/dashboard_Graph/FeedbackActionPie2ndblock.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

function show3rdblockpie(){
	$("#maxmizeViewPolar").dialog({height:600,width:950});
	$("#maxmizeViewPolar").dialog('open');
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/pieChartFeedbackModes.action",
	    success : function(data) 
	    {
			$("#maximizeDataPlr").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

