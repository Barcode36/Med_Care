function showChart(block,DivId,module,divBlock,view,title,status){
//alert("block "+block+" DivId "+DivId+" module "+module+" divBlock "+divBlock+" view "+view+" title "+title+" status "+status);
	// Second Tab Chart

	if(divBlock=='22thDataBlockDiv')
	{
		 $("#jqxChart22").show();
		 $("#jqxChart22Total").show();
		 $("#jqxChart44").hide();
		$("#ShowtakeActionDiv").hide();
		if(block=='1stBarGraph')
		{
			// All OG Bar Chart
			$("#legend").show();
			showBarCalled2ndDiv('stackedcolumn',module,DivId,divBlock,view,title);
		}
		else if(block=='1stLineGraph'){
			// Specific OG Month Wise Bar Chart
			
			$("#legend").hide();
			 $("#jqxChart22").hide();
			 $("#jqxChart22Total").hide();
			 
				$("#jqxChart44").show();
			showBarCalled2ndDivLine('stackedcolumn',module,'jqxChart44',divBlock,view,title,status);
		}
	}
	// 3rd Tab Plant Wise
	else if(divBlock=='1stTableData')
	{
		//All Plant Bar Chart
		if(block=='1stBarGraph'){
			$("#legend3").show();
			showBarCalled('stackedcolumn',module,DivId,divBlock,view);
		}
		else if(block=='1stLineGraph'){
			//Specific Plant Month Wise Bar Chart
	
			showBarCalledLine('stackedcolumn',module,DivId,divBlock,view,title);
		}
		
	}
	// 1 Tab Bar Char & Line Chart
	else if(divBlock=='33thDataBlockDiv')
	{
	if(block=='1stBarGraph'){
		//alert("Hellow");
		$("#legend11").show();
		showBarCalled3rd('stackedcolumn',module,DivId,divBlock,view);
	}
	
	else if(block=='1stLineGraph'){
		$("#legend11").hide();
		//alert("mm "+ccc);
		var cc='Account Manager Ticket Status';
		 $('#gridDialog').dialog({title:cc,width:1240,height:555});
		 $("#gridDialog").dialog('open');
		 $("#gridDataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		showBarCalled3rd('line',module,'gridDataDiv',divBlock);
	}
	else if(block=='1stPieGraph')
	{
		$("#legend11").hide();
		var cc='Account Manager Ticket Status';
		 $('#gridDialogPie').dialog({title:cc,width:1240,height:533});
		 $("#gridDialogPie").dialog('open');
		 $("#gridDataDivPie").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		showBarCalled3rd('pie',module,'gridDataDivPie',divBlock);
	}	
		
	}
}



var fistTabJson;
//first Tab Bar & Line Chart
function showBarCalled3rd(chartype,module,DivId,divBlock,view,color)
{
var total=0;
var title;
var fromDate=$("#fromdate").val();
var  toDate=$("#todate").val();

$("#"+DivId).html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
if(view!='fullView')
{
maxDivId3=chartype;
}
if (view=='' || view==undefined)
{
if(fistTabJson)
	{
	total=total+parseFloat(fistTabJson[fistTabJson.length-1].Counter);
	if(chartype=='stackedcolumn'){	var chart = AmCharts.makeChart(""+DivId+"", {
  		 "type": "serial",
		  "addClassNames": true,
		  "theme": "light",
		  "path": "amcharts",
		  "autoMargins": false,
		  "marginLeft": 30,
		  "marginRight": 8,
		  "marginTop": 10,
		  "marginBottom": 26,
		  "balloon": {
		    "adjustBorderColor": false,
		    "horizontalPadding": 10,
		    "verticalPadding": 8,
		    "color": "#ffffff"
		  },
		  /*"titles": [{
				"text":"Account Manager",
				"size": 15
			}],*/
		  "dataProvider": fistTabJson,
		  "valueAxes": [{
			  "stackType": "regular",
		    "axisAlpha": 0,
		    "position": "left"
		  }],
		  "startDuration": 0,
   	    "graphs": [{
   	      
   	        "fillAlphas": 0.6,
   	        "labelText": "[[value]]",
   	        "lineAlpha": 0,
   	        "type": "column",
   	    	"color": "#000000",
   	        "valueField": "Counter",
   	        "fixedColumnWidth":30,
   	     "fillColors": "#8C0E0E",
   	        "showBalloon" : "true",
   	        "fillAlphas": 0.6,
   	        "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>"
   	    },
   	    {
   	        "id": "graph2",
   	        "balloonText": "<span style='font-size:12px;'>Account Manager [[Per]]%",
   	        "bullet": "round",
   	        "lineThickness": 3,
   	        "lineColor":"#ff5050",
   	        "bulletSize": 7,
   	     "labelText": "[[Per]]%",
   	        "bulletBorderAlpha": 1,
   	        "bulletColor": "#FFFFFF",
   	        "useLineColorForBulletBorder": true,
   	        "bulletBorderThickness": 5,
   	        "fillAlphas": 0,
   	        "lineAlpha": 1,
   	        "title": "Total",
   	        "valueField": "Counter"
   	      }
   	    ],
   		  "categoryField": "Name",
			  "categoryAxis": {
			    "gridPosition": "start",
			    "axisAlpha": 0,
			    "tickLength": 0
			  },
			  "export": {
			    "enabled": true
			  }
			});
	
	}
	if(chartype=='line'){

		
	var chart = AmCharts.makeChart(""+DivId+"", 
			{
		  "type": "serial",
		  "addClassNames": true,
		  "theme": "light",
		  "path": "amcharts",
		  "autoMargins": false,
		  "marginLeft": 30,
		  "marginRight": 8,
		  "marginTop": 10,
		  "marginBottom": 26,
		  "balloon": {
		    "adjustBorderColor": false,
		    "horizontalPadding": 10,
		    "verticalPadding": 8,
		    "color": "#ffffff"
		  },

		  "dataProvider": fistTabJson,
		  "valueAxes": [{
		    "axisAlpha": 0,
		    "position": "left"
		  }],
		  "startDuration": 1,
		  "graphs": [{
			  "fixedColumnWidth":30,
	   	        "showBalloon" : "true",
			    "alphaField": "alpha",
			    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
			    "fillAlphas": 1,
			    "title": "Resolved",
			    "type": "column",
			    "lineColor": "#B0DE09",
			    "valueField": "Resolved",
			   // "valueField": "Counter",
			    "dashLengthField": "dashLengthColumn"
			    //"labelText": "R:[[value]]"
			
			  },
			  {
				  "fixedColumnWidth":30,
		   	        "showBalloon" : "true",
    			    "alphaField": "alpha",
    			    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
    			    "fillAlphas": 1,
    			    "title": "Stage-1 Pending Request",
    			    "type": "column",
    			    "valueField": "Stage-1 Pending Request",
    			    "dashLengthField": "dashLengthColumn"
    			    //"labelText": "S1-P:[[value]]"
    			  
    			  },
    			  {
    				  "fixedColumnWidth":30,
    		   	        "showBalloon" : "true",
        			    "alphaField": "alpha",
        			    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
        			    "fillAlphas": 1,
        			    "title": "Stage-2",
        			    "type": "column",
        			    "valueField": "Stage-2",
        			    "dashLengthField": "dashLengthColumn"
        			   // "labelText": "S2:[[value]]"
        			  
        			  },
			  {
		  },
		  
		],
		  "categoryField": "Name",
		  "categoryAxis": {
		    "gridPosition": "start",
		    "axisAlpha": 0,
		    "tickLength": 0
		  },
		  "chartCursor": {
    	        "categoryBalloonEnabled": true,
    	        "cursorAlpha": 0,
    	        "zoomable": true
    	    },
    	    
		  "export": {
		    "enabled": true
		  }
		});


}

	if(chartype=='pie'){
	
	var chart = AmCharts.makeChart(""+DivId+"", 
			{  	  	  	    	  "type": "pie",
	    	  "theme": "dark",
	  	    	  "path": "amchart",
	  	    	/* "legend": {
	  	    	    "markerType": "circle",
	  	    	    "position": "right",
	  	    	    "marginRight": -30,
	  	    	    "autoMargins": false
	  	    	  }, */
	  	    	"legend": {
	  	    	    "markerType": "circle",
	  	    	    "position": "right",
	  	    	 "marginRight":500,
	  	    	    "autoMargins": false,
	  	    	 "markerSize":15,
	  	    	"verticalGap":1
	  	    	
	  	    	  },
	  	    	 
	  		
	  	    	  "dataProvider":fistTabJson ,
	  	    	  "valueField": "Counter",
	  	    	  "titleField": "Name",
	  	    	  "outlineAlpha": 0.4,
	  	    	  "depth3D": 15,
	  	    	 "labelText": "[[title]] : [[Per]] %",
	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
	  	    	  "angle": 30,
	  	    	  "export": {
	  	    	 "marginRight":500,
	  	    	    "enabled": true
	  	    	  }
	  	    	} );
	  	    	jQuery( '.chart-input' ).off().on( 'input change', function() {
	  	    	  var property = jQuery( this ).data( 'property' );
	  	    	  var target = chart;
	  	    	  var value = Number( this.value );
	  	    	  chart.startDuration = 0;

	  	    	  if ( property == 'innerRadius' ) {
	  	    	    value += "%";
	  	    	  }

	  	    	  target[ property ] = value;
	  	    	  chart.validateNow();
	  	    	} );


}

	chart.write(""+DivId+"");
  	  AmCharts.clickTimeout = 0; // this will hold setTimeout reference
  	AmCharts.lastClick = 0; // last click timestamp
  	AmCharts.doubleClickDuration = 300; 
  	AmCharts.doSingleClick2 = function(event)
  	{
  	//handleFirstClickForAllFirstPie(event);
  	handleBarClick(event);
  	}
  	AmCharts.doDoubleClick2 = function(event)
  	{
  	handleFirstBarClickForPie(event);
  	}

  	// create click handler
  	AmCharts.myClickHandler = function(event)
  	{
  	
  		var ts = (new Date()).getTime();
  		if ((ts - AmCharts.lastClick) < AmCharts.doubleClickDuration)
  		{
  			if (AmCharts.clickTimeout)
  			{
  				clearTimeout(AmCharts.clickTimeout);
  			}
  			// reset last click
  			AmCharts.lastClick = 0;
  			// now let's do whatever we want to do on double-click
  			AmCharts.doDoubleClick2(event);
  		} else
  		{
  			// single click!
  			// let's delay it to see if a second click will come through
  			AmCharts.clickTimeout = setTimeout(function()
  			{
  				AmCharts.doSingleClick2(event);
  			}, AmCharts.doubleClickDuration);
  		}
  		AmCharts.lastClick = ts;
  	}
  	// add handler to the chart
  	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
/* 	chart.addListener("clickGraphItem", handleBarClick);*/


	}
else
	{
$.ajax({
    type : "post",
    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchAccountManagerStatus.action?maximizeDivBlock="+divBlock+"&fromDate="+fromDate+"&toDate="+toDate,
    success : function(subdeptdata) 
    {
    	fistTabJson=subdeptdata;
    	console.log("first tab  json data");
    	console.log(subdeptdata);
    	if(chartype=='stackedcolumn'){
    		
    		var chart = AmCharts.makeChart(""+DivId+"", {
   		 "type": "serial",
		  "addClassNames": true,
		  "theme": "light",
		  "path": "amcharts",
		  "autoMargins": false,
		  "marginLeft": 30,
		  "marginRight": 8,
		  "marginTop": 10,
		  "marginBottom": 26,
		  "balloon": {
		    "adjustBorderColor": false,
		    "horizontalPadding": 10,
		    "verticalPadding": 8,
		    "color": "#ffffff"
		  },
		  /*"titles": [{
				"text":"Account Manager",
				"size": 15
			}],*/
		  "dataProvider": subdeptdata,
		  "valueAxes": [{
			  "stackType": "regular",
		    "axisAlpha": 0,
		    "position": "left"
		  }],
		  "startDuration": 0,
    	    "graphs": [{
    	      
    	        "fillAlphas": 0.6,
    	        "labelText": "[[value]]",
    	        "lineAlpha": 0,
    	        "type": "column",
    	    	"color": "#000000",
    	        "valueField": "Counter",
    	        "fixedColumnWidth":30,
    	        "fillColors": "#8C0E0E",
    	        "showBalloon" : "true",
    	        "fillAlphas": 0.6,
    	        "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>"
    	    },
    	    {
    	        "id": "graph2",
    	        "balloonText": "<span style='font-size:12px;'>Account Manager [[Per]]%",
    	        "bullet": "round",
    	        "lineThickness": 3,
    	        "lineColor":"#ff5050",
    	        "bulletSize": 7,
    	        "labelText": "[[Per]]%",
    	        "bulletBorderAlpha": 1,
    	        "bulletColor": "#FFFFFF",
    	        "useLineColorForBulletBorder": true,
    	        "bulletBorderThickness": 5,
    	        "fillAlphas": 0,
    	        "lineAlpha": 1,
    	        "title": "Total",
    	        "valueField": "Counter"
    	      }
    	    ],
    		  "categoryField": "Name",
			  "categoryAxis": {
			    "gridPosition": "start",
			    "axisAlpha": 0,
			    "tickLength": 0
			  },
			  "export": {
			    "enabled": true
			  }
			});
    	
    	}
    	chart.write(""+DivId+"");
    	
	  	  AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	  	AmCharts.lastClick = 0; // last click timestamp
	  	AmCharts.doubleClickDuration = 300; 
	  	AmCharts.doSingleClick2 = function(event)
	  	{
	  		//handleFirstBarClickForPie(event);
	  	handleBarClick(event);
	  	}
	  	AmCharts.doDoubleClick2 = function(event)
	  	{
	  	handleFirstBarClickForPie(event);
	  	}

	  	// create click handler
	  	AmCharts.myClickHandler = function(event)
	  	{
	  	
	  		var ts = (new Date()).getTime();
	  		if ((ts - AmCharts.lastClick) < AmCharts.doubleClickDuration)
	  		{
	  			if (AmCharts.clickTimeout)
	  			{
	  				clearTimeout(AmCharts.clickTimeout);
	  			}
	  			// reset last click
	  			AmCharts.lastClick = 0;
	  			// now let's do whatever we want to do on double-click
	  			AmCharts.doDoubleClick2(event);
	  		} else
	  		{
	  			// single click!
	  			// let's delay it to see if a second click will come through
	  			AmCharts.clickTimeout = setTimeout(function()
	  			{
	  				AmCharts.doSingleClick2(event);
	  			}, AmCharts.doubleClickDuration);
	  		}
	  		AmCharts.lastClick = ts;
	  	}
	  	// add handler to the chart
	  	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
   /* 	chart.addListener("clickGraphItem", handleBarClick);*/
    
    },
   error: function() {
        //alert("error");
    }
 });
}
}
else
{
//alert("else "+view);
/*chartype,module,DivId,divBlock,view*/
console.log("before");
console.log(fistTabJson);
if(view=='All')
	{
	viewAllCalledFirstDiv(DivId, fistTabJson, view,  color)
	}
else
	{
	console.log("first tab chart "+fistTabJson);
	drawStatusChart(DivId, fistTabJson, view,  color,"OG" );
	}
}
}

function handleBarClick(event)
{
	//alert("jsadfk");
	console.log(event);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	showAgeingComplDetails(dataContx.accId,'SuggestedByMe',undefined,dataContx.plantId,'Account Manager : '+dataContx.Name);
	//showAgeingComplDetails();
}
//Show data in Grid When Click 2nd Tab Table for Take Action
//function showAgeingComplDetails()
var ccc;
function showAgeingComplDetails(id,type,implementOn,OnClickplantId,title)
{
	//alert(title);
	ccc=title
	var fromDate=$("#fromdate").val();
	var  toDate=$("#todate").val();
	 $('#gridDialog').dialog({title:title,width:1240,height:550});
	 $("#gridDialog").dialog('open');
	 $("#gridDataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewAccountManagerPage.action",
		    data: " id="+id+"&fromDate="+fromDate+"&toDate="+toDate,//&dataFor="+status+"&reviewDate="+implementOn+"&action="+action+"&mode=action&otherPlant="+plantId,
		    success : function(data) 
		    {
		        $("#gridDataDiv").html(data);
		    },
		    error: function() 
		    {
	            //alert("error");
	        }
		 });

}
function handleFirstBarClickForPie(event)
{
	var ogtitle;
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var title=graph.title;
	var dataContx = data[indexFor].dataContext;
	showPieCalled(dataContx.accId,'11thDataBlockDiv',550,1100,'Account Manager : '+dataContx.Name);
}
function showPieCalled(PlantId,divBlock,height,width,title,login)  {
		if(PlantId=="Sum")
			{
			showPieCalledTotal('Total',divBlock,height,width,"Total Kaizen Shared By OG's To "+login);
			}
		else{
			var fromdate;
			var todate;
		if(divBlock=='1stTableData')
			{
			 fromdate=$("#fromdate").val();
			  todate=$("#todate").val();
			}
		else
			{
			 fromdate=$("#fromdate12").val();
			  todate=$("#todate12").val();
			}
		$("#PaiDataDialog").dialog({title:title,height:'550',width:'100%',dialogClass:'transparent'});
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getjspforPieChart.action?maximizeDivBlock="+divBlock+"&fromdate="+fromdate+"&todate="+todate+"&id="+PlantId,
	  	    success : function(data) {
	  	    	$("#PaiDataDialog").dialog('open');	
	  	    	$("#PaiDataDiv").html(data);
	  	    },
	   	   error: function() {
	   	    }
	   	 });
		}
	  }
//2nd Tab
var corporateJsonData;
function showBarCalled2ndDiv(chartype,module,DivId,divBlock,view){
	//alert(divBlock);
	var fromdate=$("#fromdate1").val();
	var todate=$("#todate1").val();
	var total=0;
	var title;
	
	var angle;
	
	$("#"+DivId).html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonData.action?maximizeDivBlock="+divBlock+"&fromDate="+fromdate+"&toDate="+todate,
	    success : function(data) 
	    {
  	  	    console.log(data);
	    	/*if(plantId=="-1")
	    		{
	    		value=	data[0].Plant;
	    		}*/
	    	  for (var int = 0; int < data.length; int++) {
	    		  total=total+parseFloat(data[int].Score);
	    	  }
	  		angle=10;
	  		corporateJsonData=data;
	    		var chart = AmCharts.makeChart(DivId, {
	    			"type": "pie",
	  	  	    	  "theme": "light",
	  	  	    	  "path": "amchart",
	  	  	    	/* "legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	    "marginRight": -30,
	  	  	    	    "autoMargins": false
	  	  	    	  }, */
	  	  	    	"legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	    "marginRight":500,
	  	  	    	    "autoMargins": false,
	  	  	    	 "markerSize":15,
	  	  	    	"verticalGap":1
	  	  	    	  },
	  	  	    	 "titles": [{
	 	  				"text":"Corporate Wise Status",
	 	  				"size": 15
	 	  			}
	 	  		],
	 	  	  "dataProvider":corporateJsonData ,
	  	    	  "valueField": "Score",
	  	    	  "titleField": "Name",
	  	    	  "outlineAlpha": 0.4,
	  	    	  "depth3D": 15,
	  	    	 "labelText": "[[title]] : [[Per]] %",
	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
	  	    	  "angle": 30,
	  	    	  "export": {
	  	    	 "marginRight":1000,
	  	    	    "enabled": true
	  	    	  }
	  	    	} );
	    		jQuery( '.chart-input' ).off().on( 'input change', function() {
	  	  	    	  var property = jQuery( this ).data( 'property' );
	  	  	    	  var target = chart;
	  	  	    	  var value = Number( this.value );
	  	  	    	  chart.startDuration = 0;

	  	  	    	  if ( property == 'innerRadius' ) {
	  	  	    	    value += "%";
	  	  	    	  }

	  	  	    	  target[ property ] = value;
	  	  	    	  chart.validateNow();
	  	  	    	} );
	    		chart.write(""+DivId+"");
   		 

	    		chart.addListener("clickSlice", handlePieClickpieChartHead);
    	  
	    },
	   error: function() {
           //alert("error");
       }
	 });
}
function handlePieClickpie1(event)
{
	alert("piechartTable");
	var ogtitle;
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var title=graph.title;
	var dataContx = data[indexFor].dataContext;
	showPieCalled(dataContx.corId,'piechartTable',550,1100,ogtitle);
}
//2nd Tab
function handlePieClickpieChartHead(event)
{
	
	var indexFor = event.dataItem;
	var dataContx = indexFor.dataContext;
	console.log(event);
	console.log(dataContx);
	showPieCalled(dataContx.corId,'piechartTable',550,1100,'Corporate Name : '+dataContx.Name);
}


function getData(PlantId,divBlock,height,width,title)  {
	
    //  var value = event.args.item.value;
 var fromdate=$("#fromdate").val();
	var  todate=$("#todate").val();
	var ogId=$("#ogId").val();
	
	$("#piechartTable").dialog({title:'Department Wise Kaizen Shared ',height:'400',width:'85%',dialogClass:'transparent'});
/*	$("#maxmizeViewDialog").dialog('open');*/

	/* $('#maximizeDataDiv').html(data);*/
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonData.action?maximizeDivBlock="+divBlock+"&fromdate="+fromdate+"&todate="+todate,
  	    success : function(data) {
  	    console.log("asset chart data for board 1");
	    	console.log(data);
  	      $("#piechartTable").dialog('open');
  	
  	    	sampleData = data;
  	    	var chart = AmCharts.makeChart( "pieChart4", {
  	    	  "type": "pie",
  	    	  "theme": "light",
  	    	  "path": "amchart",
  	    	"legend": {
  	    	    "markerType": "circle",
  	    	    "position": "right",
  	    	    "marginRight": 80,
  	    	    "autoMargins": false,
  	    	  "markerSize":15,
	  	    	"verticalGap":1
  	    	  },
  	    	 "titles": [{
	  				"text":title,
	  				"size": 15
	  			}
	  		],
  	    	  "dataProvider":sampleData,
  	    	  "valueField": "Score",
  	    	  "titleField": "dept",
  	    	  "outlineAlpha": 0.4,
  	    	  "depth3D": 15,
  	    	  "labelText": "[[title]] : [[value]]",
  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
  	    	  "angle": 30,
  	    	  "export": {
  	    	    "enabled": true
  	    	  }
  	    	} );
  	    	jQuery( '.chart-input' ).off().on( 'input change', function() {
  	    	  var property = jQuery( this ).data( 'property' );
  	    	  var target = chart;
  	    	  var value = Number( this.value );
  	    	  chart.startDuration = 0;

  	    	  if ( property == 'innerRadius' ) {
  	    	    value += "%";
  	    	  }

  	    	  target[ property ] = value;
  	    	  chart.validateNow();
  	    	} );
  	  	chart.write("pieChart4");
  	 
  	  	  /*   } */ },
   	   error: function() {
   	        alert("error");
   	    }
   	 });
   
      
  } 

//Third Graph
var plantJsonData;
function showBarCalled(chartype,module,DivId,divBlock,view){
	var fromdate=$("#fromdate2").val();
	var todate=$("#todate2").val();
	var plantId="-1";
	var total=0;
	var title;
	
	var angle;
	if(view!='fullView')
	{
		maxDivId1=chartype;
	}
	$("#"+DivId).html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(plantJsonData)
		{
		
		for (var int = 0; int < plantJsonData.length; int++) {
  		  total=total+parseFloat(plantJsonData[int].Score);
  	  }
		var chart = AmCharts.makeChart(""+DivId+"", {
	   		 "type": "serial",
			  "addClassNames": true,
			  "theme": "light",
			  "path": "amcharts",
			  "autoMargins": false,
			  "marginLeft": 30,
			  "marginRight": 8,
			  "marginTop": 10,
			  "marginBottom": 26,
			  "balloon": {
			    "adjustBorderColor": false,
			    "horizontalPadding": 10,
			    "verticalPadding": 8,
			    "color": "#ffffff"
			  },
			  /*"titles": [{
					"text":"Account Manager",
					"size": 15
				}],*/
			  "dataProvider": plantJsonData,
			  "valueAxes": [{
				  "stackType": "regular",
			    "axisAlpha": 0,
			    "position": "left"
			  }],
			  "startDuration": 0,
	    	    "graphs": [{
	    	      
	    	        "fillAlphas": 0.6,
	    	        "labelText": "[[value]]",
	    	        "lineAlpha": 0,
	    	        "type": "column",
	    	    	"color": "#000000",
	    	        "valueField": "Score",
	    	        "fixedColumnWidth":30,
	    	        "showBalloon" : "true",
	    	        "fillAlphas": 0.6,
	    	        "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>"
	    	    },
	    	    {
	    	        "id": "graph2",
	    	        "balloonText": "<span style='font-size:12px;'>Services [[Per]]%",
	    	        "bullet": "round",
	    	        "lineThickness": 3,
	    	        "bulletSize": 7,
	    	        "labelText": "[[Per]]%",
	    	        "bulletBorderAlpha": 1,
	    	        "bulletColor": "#FFFFFF",
	    	        "useLineColorForBulletBorder": true,
	    	        "bulletBorderThickness": 3,
	    	        "fillAlphas": 0,
	    	        "lineAlpha": 1,
	    	        "title": "Total",
	    	        "valueField": "Score"
	    	      }
	    	    ],
	    		  "categoryField": "month",
				  "categoryAxis": {
				    "gridPosition": "start",
				    "axisAlpha": 0,
				    "tickLength": 0,
				    "labelRotation": 10
				  },
				  "export": {
				    "enabled": true
				  }
				});
 	  	chart.write(""+DivId+"");
	  AmCharts.clickTimeout = 0; // this will hold setTimeout reference
  	AmCharts.lastClick = 0; // last click timestamp
  	AmCharts.doubleClickDuration = 300; 
  	AmCharts.doSingleClick3 = function(event)
  	{
  		handlePlantClickedBar(event);
  	}
  	AmCharts.myClickHandler = function(event)
  	{
  		var ts = (new Date()).getTime();
  		if ((ts - AmCharts.lastClick) < AmCharts.doubleClickDuration)
  		{
  			if (AmCharts.clickTimeout)
  			{
  				clearTimeout(AmCharts.clickTimeout);
  			}
  			// reset last click
  			AmCharts.lastClick = 0;
  			// now let's do whatever we want to do on double-click
  			AmCharts.doDoubleClick3(event);
  		} else
  		{
  			// single click!
  			// let's delay it to see if a second click will come through
  			AmCharts.clickTimeout = setTimeout(function()
  			{
  				AmCharts.doSingleClick3(event);
  			}, AmCharts.doubleClickDuration);
  		}
  		AmCharts.lastClick = ts;
  	}
  	// add handler to the chart
  	chart.addListener("clickGraphItem", AmCharts.myClickHandler);

	
		}
	else{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonData.action?maximizeDivBlock="+divBlock+"&fromDate="+fromdate+"&toDate="+todate,
	    success : function(data) 
	    {
  	  	    console.log(data);
	    	/*if(plantId=="-1")
	    		{
	    		value=	data[0].Plant;
	    		}*/
	    	  for (var int = 0; int < data.length; int++) {
	    		  total=total+parseFloat(data[int].Score);
	    	  }
	  		angle=10;
  	  	    plantJsonData=data;
	    		var chart = AmCharts.makeChart(""+DivId+"",  {
	   	   		 "type": "serial",
				  "addClassNames": true,
				  "theme": "light",
				  "path": "amcharts",
				  "autoMargins": false,
				  "marginLeft": 30,
				  "marginRight": 8,
				  "marginTop": 10,
				  "marginBottom": 26,
				  "balloon": {
				    "adjustBorderColor": false,
				    "horizontalPadding": 10,
				    "verticalPadding": 8,
				    "color": "#ffffff"
				  },
				  /*"titles": [{
						"text":"Account Manager",
						"size": 15
					}],*/
				  "dataProvider": plantJsonData,
				  "valueAxes": [{
					  "stackType": "regular",
				    "axisAlpha": 0,
				    "position": "left"
				  }],
				  "startDuration": 0,
		    	    "graphs": [{
		    	      
		    	        "fillAlphas": 0.6,
		    	        "labelText": "[[value]]",
		    	        "lineAlpha": 0,
		    	        "type": "column",
		    	    	"color": "#000000",
		    	        "valueField": "Score",
		    	        "fixedColumnWidth":30,
		    	        "showBalloon" : "true",
		    	        "fillAlphas": 0.6,
		    	        "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>"
		    	    },
		    	    {
		    	        "id": "graph2",
		    	        "balloonText": "<span style='font-size:12px;'>Services [[Per]]%",
		    	        "bullet": "round",
		    	        "lineThickness": 3,
		    	        "bulletSize": 7,
		    	        "labelText": "[[Per]]%",
		    	        "bulletBorderAlpha": 1,
		    	        "bulletColor": "#FFFFFF",
		    	        "useLineColorForBulletBorder": true,
		    	        "bulletBorderThickness": 3,
		    	        "fillAlphas": 0,
		    	        "lineAlpha": 1,
		    	        "title": "Total",
		    	        "valueField": "Score"
		    	      }
		    	    ],
		    		  "categoryField": "month",
					  "categoryAxis": {
					    "gridPosition": "start",
					    "axisAlpha": 0,
					    "tickLength": 0,
					    "labelRotation": 10
					  },
					  "export": {
					    "enabled": true
					  }
					});
   	  	  	chart.write(DivId);
   		  AmCharts.clickTimeout = 0; // this will hold setTimeout reference
    	  	AmCharts.lastClick = 0; // last click timestamp
    	  	AmCharts.doubleClickDuration = 300; 
    	  	AmCharts.doSingleClick3 = function(event)
    	  	{
    	  		//handleFirstClickForAllThreePie(event);
    	  		handlePlantClickedBar(event);
    	  	}

    	  	// create click handler
    	  	AmCharts.myClickHandler = function(event)
    	  	{
    	  		
    	  		var ts = (new Date()).getTime();
    	  		if ((ts - AmCharts.lastClick) < AmCharts.doubleClickDuration)
    	  		{
    	  			if (AmCharts.clickTimeout)
    	  			{
    	  				clearTimeout(AmCharts.clickTimeout);
    	  			}
    	  			// reset last click
    	  			AmCharts.lastClick = 0;
    	  			// now let's do whatever we want to do on double-click
    	  			AmCharts.doDoubleClick3(event);
    	  		} else
    	  		{
    	  			// single click!
    	  			// let's delay it to see if a second click will come through
    	  			AmCharts.clickTimeout = setTimeout(function()
    	  			{
    	  				AmCharts.doSingleClick3(event);
    	  			}, AmCharts.doubleClickDuration);
    	  		}
    	  		AmCharts.lastClick = ts;
    	  	}
    	  	// add handler to the chart
    	  	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
	    
	    	
	    },
	   error: function() {
           //alert("error");
       }
	 });
}
}


//Third Tab
function handlePlantClickedBar(event)
{ 
	var ogtitle;
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var title=graph.title;
	var dataContx = data[indexFor].dataContext;
	showPieCalled(dataContx.serId,'1stTableData',550,1100,ogtitle);
}
//1 Refresh button from Date wise datasearch  
function refreshAccountManager(block,DivId,module,divBlock,view,title,status){
	
	var fromDate=$("#fromdate").val();
	var  toDate=$("#todate").val();
	var plantId="-1";
	var module="Kaizen";
	if(view!='fullView')
	{
		maxDivId1="stackedcolumn";
	}
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchAccountManagerStatus.action?maximizeDivBlock="+divBlock+"&fromDate="+fromDate+"&toDate="+toDate,
			    success : function(data) 
			    {
				fistTabJson=data;
			    	showBarCalled3rd("stackedcolumn",module,DivId,divBlock,view)
			    	/*showBarCalled2ndDivKaizen('stackedcolumn',module,DivId,divBlock,view,title);*/
			    },
			   error: function() {
		            //alert("error");
		        }
			 });
}
function refreshCorporateType(block,DivId,module,divBlock,view,title,status){
	
	var fromDate=$("#fromdate1").val();
	var  toDate=$("#todate1").val();
	var plantId="-1";
	var module="Kaizen";
	if(view!='fullView')
	{
		maxDivId1="stackedcolumn";
	}
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonData.action?maximizeDivBlock="+divBlock+"&fromDate="+fromDate+"&toDate="+toDate,
			    success : function(data) 
			    {
				corporateJsonData=data;
				showBarCalled2ndDiv("stackedcolumn",module,DivId,divBlock,view)
			    	/*showBarCalled2ndDivKaizen('stackedcolumn',module,DivId,divBlock,view,title);*/
			    },
			   error: function() {
		            //alert("error");
		        }
			 });
}
function refreshService(block,DivId,module,divBlock,view,title,status){
	
	var fromDate=$("#fromdate2").val();
	var  toDate=$("#todate2").val();
	var plantId="-1";
	var module="Kaizen";
	if(view!='fullView')
	{
		maxDivId1="stackedcolumn";
	}
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonData.action?maximizeDivBlock="+divBlock+"&fromDate="+fromDate+"&toDate="+toDate,
			    success : function(data) 
			    {
				plantJsonData=data;
				showBarCalled("stackedcolumn",module,DivId,divBlock,view)
			    },
			   error: function() {
		            //alert("error");
		        }
			 });
}