var specialty1='';
var testType1='';
var location1='';
var locationName1='';
var speciality1='';
var specialityName1='';
var recordStatus1='';
var dashboard1='';
var feelColor1='';
var status1='';
var tableFor1='';
UpdateDropFilters();
function UpdateDropFilters()

{ 
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchFilterLocation.action",
	    data:"fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
	    success : function(feeddraft) {
		 $("#locationDiv").html(feeddraft);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchFilterNurshingUnit.action",
    data:"fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
    success : function(feeddraft) {
	 $("#nursingDiv").html(feeddraft); 
},
   error: function() {
            alert("error");
        }
 });
 
}

  
function showTimeDivForReport(status)
{
	 
	 if(status==true)
		 {
	  		$(".statusTime").show();
	  		$("#fromTime").val("");
	  		$("#toTime").val("");
	  		
		 }
	 else if(status==false)
		 {
	 		$(".statusTime").hide();
	 		$("#fromTime").val("");
	  		$("#toTime").val("");
	  		getDataForAllBoard();
		 }
	  
}
 


function getDataForAllBoard()
{
	showStatusPieChart('statusChart','statusPie');
	showProductivityPieChart('productivityChart','productivityPie');
  
}
 
 
  

function nursingUnitWiseStatusForLocation(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	location1=dataContx.Id;
	locationName1=dataContx.locationStatusBar;
	showStatusStackedBar('3rdBlock_1', 'nursingUnitStatusBar', '', recordStatus1, recordStatus1, graph.fillColors,dataContx.Id);
}
function doctorWiseStatusForLocation(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	speciality1=dataContx.Id;
	specialityName1=dataContx.specialityBar;
	showStatusStackedBar('6thBlock_1', 'doctorBar', '', recordStatus1, recordStatus1, graph.fillColors,dataContx.Id);
}
 
 
 

function dataOnDblClk(event)
{
	//alert(machineName);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	location1=dataContx.Id;
	status1=dataContx.Status;
	tableFor1=dataContx.TableFor;
 	getData(location1, status1,tableFor1);
}

function showStatusStackedBar(divId, filterFlag, filterDeptId, status, title, color,id)
{
	$(".statusCounter").hide();
	$(".locationWiseStatusCounter").hide();
	$(".nursingUnitWiseStatusCounter").hide();
	$(".specialityWiseStatusCounter").hide();
	$(".doctorWiseStatusCounter").hide();
 	recordStatus1=status;
	feelColor1=color;
  	var total = 0;
   	var url1='';
	if(filterFlag=='statusBar')
	{
		currentTab="1";
		$("#backbttn1").attr('onclick','').css('display','none');
		$(".statusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	if(filterFlag=='statusBarTAT')
	{
		currentTab="7";
		$("#backbttn1").attr('onclick','').css('display','none');
		$(".statusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	if(filterFlag=='locationStatusBar')
	{
		currentTab="3";
		$("#backbttn1").attr('onclick','').css('display','none');
		$(".locationWiseStatusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	if(filterFlag=='specialityBar')
	{
		currentTab="6";
		$("#backbttn2").attr('onclick','').css('display','none');
		$(".specialityWiseStatusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	if(filterFlag=='priorityBar')
	{
		currentTab="4";
		$("#backbttn1").attr('onclick','').css('display','none');
		$(".priorityStatusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	
	if(filterFlag=='nursingUnitStatusBar')
	{
		currentTab="3";
		$("#backbttn1").attr('onclick','showStatusStackedBar("3rdBlock_1","locationStatusBar" ,"" ,recordStatus1 , recordStatus1, feelColor1,"")').css('display','block');
		$(".nursingUnitWiseStatusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets For Location "+locationName1+":";
	}
	if(filterFlag=='doctorBar')
	{
		currentTab="6";
		$("#backbttn2").attr('onclick','showStatusStackedBar("6thBlock_1","specialityBar" ,"" ,recordStatus1 , recordStatus1, feelColor1,"")').css('display','block');
		$(".doctorWiseStatusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets For Speciality "+specialityName1+":";
	}
	if(filterFlag=='productivityBar')
	{
		currentTab="2";
		$("#backbttn2").attr('onclick','').css('display','none');
		$(".productivityCounter").show();
		dashboard2=filterFlag;
		title=title+" Tickets:";
	}
	 
	 
   	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+filterFlag+"&status="+status+"&id="+id+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + data[i].Counter;
	data[i].total=data[i].Counter;
	}
	 for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
  		drawChartForStatus(divId, data, status, title, color, filterFlag,total);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
 
 
}// end here

  
function showTATStackedBar(divId, filterFlag, filterDeptId, status, title, color,id)
{
	 recordStatus1=status;
	feelColor1=color;
  	var total1 = 0,total2=0;
   	var url1='';
	if(filterFlag=='tatBar')
	{
		currentTab="5";
		$("#backbttn1").attr('onclick','').css('display','none');
		$(".tatStatusCounter").show();
	 	dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	 
   	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/getCountersForTAT.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+filterFlag+"&status="+status+"&id="+id+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 for (var i = 0; i <= data.length - 1; i++)
	{
	total1 = total1 + data[i].WithInTAT;
	data[i].total1=data[i].WithInTAT;
	total2 = total2 + data[i].OutOfTAT;
	data[i].total2=data[i].OutOfTAT;
	
	data[i].totalPer1=data[i].WithInTATper;
	data[i].totalPer2=data[i].OutOfTATper;
	}
	 
	 if ((total1 == 0 && total2==0) || (isNaN(total1) && isNaN(total2) ) )
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
  		drawChartForTAT(divId, data, status, title, color, filterFlag);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
 
 
}// end here


function drawChartForTAT(divId, data, status, title, color, Category)
{
	   
	  var chart='';
	 	  if(data.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"columnSpacing": 50,
	"titles" : [ {
	"text" : " Order Close TAT Counter",
	"size" : 15
	} ],
	"theme" : "light",
	"legend": {
	  	"align": "center",
	  	    "useGraphSettings": true,
	  	  "equalWidths": false,
	  	"markerLabelGap": 4,
		"markerSize": 9,
		"spacing": -20,
		"autoMargins": false,
		"position":"bottom"
	  	  },
	"dataProvider" : data,
	"valueAxes" : [ {
	"gridColor" : "#FFFFFF",
	"gridAlpha" : 0,
	"dashLength" : 0

	} ],
	"gridAboveGraphs" : true,
	"startDuration" : 1,
	"graphs" : [ {
	"balloonText" : "[[title]]: <b>[[value]]</b>",
	"fillAlphas" : 1,
	"lineAlpha" : 0,
	"type" : "column",
	"valueField" : "WithInTAT",
	"labelOffset":10,
	"labelText" : "[[value]] ([[totalPer1]]%)",
	"title" :"With In TAT",
	"fixedColumnWidth" : 20

	},
	{
		"balloonText" : "[[title]]: <b>[[value]]</b>",
		"fillAlphas" : 1,
		"lineAlpha" : 0,
		"type" : "column",
		"valueField" : "OutOfTAT",
		"labelOffset":10,
		"labelText" : "[[value]] ([[totalPer2]]%)",
		"title" : "Out of TAT",
		"fixedColumnWidth" : 20

		}

	],
	"depth3D" : 13,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "Status",
	"categoryAxis" : {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	 	"autoRotateAngle": 0,
  	"autoRotateCount": 0,
  	"autoWrap":false
  	  },
	"export" : {
	"enabled" : true
	}

	});
	  }	  
	  if(chart!='')  
	 	chart.write(divId);	
	  else
	  $('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
}
 


function showStatusPieChart(divId,dataFor)
{
	$(".locationWiseStatusCounter").hide();
	$(".nursingUnitWiseStatusCounter").hide();
 	 var total=0;
   	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+dataFor+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + data[i].Counter;
	data[i].total=data[i].Counter;
	}
	 
	 if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 		var chart1 = AmCharts.makeChart( divId, {
   		  "type": "pie",
   		  "theme": "light",
   		  "dataProvider": data,
   		  "legend": {
   		    "markerType": "circle",
   		    "position": "left",
   		    "marginBottom": 0,
   		    "autoMargins": false
   		  },
   		  
   		  "valueField": "Counter",
   		  "titleField": dataFor,
   		  "outlineAlpha": 0.1,
   		  "depth3D": 15,
   		 "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
		"labelText":"[[percents]]%",
   		  "angle": 20,
   		  "export": {
   		    "enabled": true
   		  }
		} );
				
 		
 	chart1.write(divId);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
 
 
}// end here

  
function showTATPieChart(divId,dataFor)
{
 	 var total=0;
 	currentTab="8";
   	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/getCountersForTATPie.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+dataFor+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val()+"&priority="+$('#prioritySearch').val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
		if(data!=null)
		{
	 for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + data[i].WithInTAT;
	data[i].total=data[i].WithInTAT;
	}
		}
	 if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 		var chart1 = AmCharts.makeChart( divId, {
 			 
 			"titles" : [ {
 			"text" : "Order Close TAT:"+total,
 			"size" : 15
 			} ],
   		  "type": "pie",
   		  "theme": "light",
   		  "dataProvider": data,
   		  "legend": {
   		    "markerType": "circle",
   		    "position": "bottom",
   		    "marginBottom": 0,
   		    "autoMargins": false
   		  },
   		  
   		  "valueField": "WithInTAT",
   		  "titleField": "Status",
   		  "outlineAlpha": 0.1,
   		  "depth3D": 15,
   		 "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
		"labelText":"[[percents]]%",
   		  "angle": 20,
   		  "export": {
   		    "enabled": true
   		  }
		} );
				
 		
 	chart1.write(divId);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
 
 
}// end here




 

      
 
function drawChartForStatus(divId, data, status, title, color, Category,totalData)
{
		var a=0;
		 var chart='';
	  if(Category=='doctorBar' || Category=='specialityBar')
	  {
		    a=40;
	  }
	 if(data.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" :  title+" "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : data,
	"valueAxes" : [ {
	"gridColor" : "#FFFFFF",
	"gridAlpha" : 0,
	"dashLength" : 0

	} ],
	"gridAboveGraphs" : true,
	"startDuration" : 1,
	"graphs" : [ {
	"balloonText" : "[[category]]: <b>[[value]]</b>",
	"fillAlphas" : 1,
	"lineAlpha" : 0,
	"type" : "column",
	"valueField" : "Counter",
	 	"labelOffset":10,
	 	"labelText" :  "[[value]]",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 10

	}

	],
	"depth3D" : 13,
	"angle" : 15,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + Category,
	"categoryAxis" : {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	  "autoRotateAngle": a,
  	"autoRotateCount": 0,
  	"autoWrap":false
  	  },
	"export" : {
	"enabled" : true
	}

	});
	  }	  
	  if(chart!='')  
	 	chart.write(divId);	
	  else
	  $('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	  
	  	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
	   
	
	if(Category=='locationStatusBar')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
		  dataOnDblClk(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
		  nursingUnitWiseStatusForLocation(event);
	};
	  }
	  
	    
	   if(Category=='nursingUnitStatusBar')
		  {
		  AmCharts.doDoubleClick1 = function(event)
		  {
			  dataOnDblClk(event);
		  };
		  AmCharts.doSingleClick1 = function(event)
		{
		  alert("Sorry! No any data on single click");
		};
		  }
	   
	   
	   if(Category=='specialityBar')
		  {
		  AmCharts.doDoubleClick1 = function(event)
		  {
			  dataOnDblClk(event);
		  };
		  AmCharts.doSingleClick1 = function(event)
		{
			  doctorWiseStatusForLocation(event);
		};
		  }
		  
		    
		   if(Category=='doctorBar')
			  {
			  AmCharts.doDoubleClick1 = function(event)
			  {
				  dataOnDblClk(event);
			  };
			  AmCharts.doSingleClick1 = function(event)
			{
			  alert("Sorry! No any data on single click");
			};
			  }
	   
   
	  AmCharts.myClickHandler1 = function(event)
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
	AmCharts.doDoubleClick1(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick1(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler1);
}
 
 
 function showData(id,filterFlag)
 {
	 var url1='';
	 dashboard1=filterFlag+'_table';
 	$("#"+id).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	if(filterFlag=='tatBar')
 		url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/getCountersForTATForTable.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+filterFlag+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val();
 	else
 		url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+filterFlag+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val();
 	 
 	$.ajax( {
 	type :"post",
 	url : url1,
 	success : function(statusdata) 
 	{    
 	$("#"+id).html(statusdata);
 	 
 	},
 	error : function() {
 	alert("error");
 	}
 	});
    
 }

 function getData(id, status, filterFlag)
 {
 	$("#confirmEscalationDialog").dialog({
 	width : 1150,
 	height : 500
 	});
 	 var url1='';
 	$("#confirmEscalationDialog").dialog('open');
 	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	url1="view/Over2Cloud/CorporatePatientServices/Pharmacy/beforeFeedAction.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&tableFor="+filterFlag+"&status="+status+"&id="+id+"&location="+$('#location_search').val()+"&nursingUnit="+$('#nursing_search').val();
  	$.ajax({
 	type : "post",
 	url : url1,
 	  	success : function(feedbackdata)
 	{
 	$("#confirmingEscalation").html(feedbackdata);
 	},
 	error : function()
 	{
 	alert("error");
 	}
 	});
 }
