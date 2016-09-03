var chart=null;
function getChartForTrend(divId) {
	if(filterDeptName=='')
	{
		filterDeptName='All Department';
	}	
	$("#maxmizeViewDialog").dialog({title:"Trend For "+filterDeptName,height:500,width:1250,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
  	  	    type : "post",
  	  	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4TrendAnalysis.action?filterFlag="+filterFlag+"&dept_id="+filterDeptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
  	  	    success : function(data) {
  	  	    	console.log(data);
  	  	    	
  	  	    	
  	  	     chart = AmCharts.makeChart(divId, {
  	  	    "type": "serial",
  	  	    "theme": "light",
  	  	    "path": "amcharts",
  	  	    "legend": {
  	  	        "useGraphSettings": true
  	  	    },
  	  	    "dataProvider":data,
  	  	    "valueAxes": [{
  	  	        "id":"v1",
  	  	        "axisColor": "#F04E2F",
  	  	        "axisThickness": 2,
  	  	        "gridAlpha": 0,
  	  	        "axisAlpha": 1,
  	  	        "position": "left"
  	  	    }],
  	  	    "graphs": [{
  	  	        "valueAxis": "v1",
  	  	         "type":"column",
  	  	     "labelText":"[[value]]",
  	  	        "title": "Total Tickets",
  	  	        "valueField": "Total",
  	  			"fillAlphas": 1,
  	  		"fixedColumnWidth":30
  	  	    }],
  	  	"depth3D": 20,
  		"angle": 30,
  	  	    "chartScrollbar": {},
  	  	    "chartCursor": {
  	  	        "cursorPosition": "mouse"
  	  	    },
  	  	    "categoryField": "Date",
  	  	    "categoryAxis": {
  	  	        "parseDates": true,
  	  	        "axisColor": "#DADADA",
  	  	        "minorGridEnabled": true
  	  	    },
  	  	"periodSelector": {
  	     "periods": [ {
  	       "period": "DD",
  	        "count": 10,
  	        "label": "10 days"
  	      }, {
  	        "period": "MM",
  	        "count": 1,
  	        "label": "1 month"
  	      }, {
  	        "period": "YYYY",
  	        "count": 1,
  	        "label": "1 year"
  	      }, {
  	        "period": "YTD",
  	        "label": "YTD"
  	      }, {
  	        "period": "MAX",
  	        "label": "MAX"
  	      } ]
  	    },

  	    "panelsSettings": {
  	      "usePrefixes": true
  	    },
  	  	    "export": {
  	  	    	"enabled": true,
  	  	        "position": "bottom-right"
  	  	     }
  	  	});
  	  	    
  	  	chart.write(divId);

  	  /*	chart.addListener("dataUpdated", zoomChart);
  	  	zoomChart();*/
  	  		
	    	},
  	  	   error: function() {
  	              alert("error");
  	          }
	    	
  	  	 });
	 
	}//end here

/*function zoomChart(){
    chart.zoomToIndexes(chart.dataProvider.length - 20, chart.dataProvider.length - 1);
}*/
