var dataForOrder='dashboard1';
var dataForStatus='dashboard1';
var filters=null;
var widthSize=3;


function beforeSpecialityAnalysis(){
	$("#maxmizeSpecDialog").dialog({
	title:"Referral Speciality Wise Counter",
	width : 1250,
	height : 630
	});
	$("#maxmizeSpecDialog").dialog('open');
 	dataForStatus='table4';
	showSpeciality('Day');
}
function showSpeciality(filterFlag){
 	var url1='';
	var designation=$("#designation").val();
 	var fromMonth=null;
	var fromYear=null;
	var toYear=null;
	var fromTime=$("#fromTimeSpec").val();
	var toTime=$("#toTimeSpec").val();
	if(filterFlag=='Day')
 	{
 	 fromMonth=$("#fromMonthStatusSpec1").val();
 	  fromYear=$("#fromYearStatusSpec1").val();
 	}
 	else if(filterFlag=='Month')
	{
	 	  fromYear=$("#fromYearStatusSpec2").val();
	}
 	else if(filterFlag=='Week')
	{

	 fromMonth=$("#fromMonthStatusSpec3").val();
	  fromYear=$("#fromYearStatusSpec3").val();
	}
 	else if(filterFlag=='Year')
	{
	  fromYear=$("#fromYearStatusSpec4").val();
	 toYear=$("#toYearStatusSpec").val();
	}
	$("#maximizeSpecDiv").html("<center><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");

	url1="view/Over2Cloud/Referral/Dashboard_Pages/referralSpecialityCounterTable.action?dashFor=Speciality"+""+"&fromYear="+fromYear+"&fromMonth="+fromMonth+"&filterFlag="+filterFlag+"&toYear="+toYear+"&fromTime="+fromTime+"&toTime="+toTime+"&attendedBy="+designation+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDateSpec").val()+"&toDate1="+$("#toDateSpec").val();
   
 	$.ajax({
	type : "post",
	url : url1,
	success : function(data)
	{
	$("#maximizeSpecDiv").html(data);
	},
	error : function()
	{
	alert("error");
	}
	});
}
//For Double click
function handleClickStatus(event)
{
	var flag=null;
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	var dataFor = graph.title;
 	if (dataFor == 'Open')
	{
	dataFor = "'Informed','Not Informed','Snooze','Snooze-I'";
	flag='Status';
	} 
	else if (dataFor == 'Close')
	{
	dataFor = "'Seen'";
	flag='Status';
	}  
	else if (dataFor == 'Total')
	{
	dataFor = "'Informed','Not Informed','Snooze','Snooze-I','Seen'";
	flag='Status';
	} 
	else if (dataFor == 'Routine' || dataFor == 'Urgent' || dataFor == 'Stat')
	{
	flag='Order';
	} 
	else if (dataFor == 'Resident' || dataFor == 'Consultant' || dataFor == 'Medical Officer')
	{
	flag='Attended';
	} 
   	//alert("indexFor : "+indexFor+" dataContx: "+dataContx.Date+" dataFor : "+dataFor);
 	 
	getDataForStatus(dataContx.Date, dataFor, flag);
}

function getDataForStatus(date, status,  flag)
{
	var locationWise='No';
	if( $('#locationView').is(":checked"))
	{
		locationWise='Yes';
	}
	else 
	{
		locationWise='NO';
	}
	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
  	
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/Referral/Dashboard_Pages/beforeStatusGridView.action?flag=" + flag + "&feedStatus=" + status +"&filterFlag="+filters+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&specialty="+$("#specialty").val()+"&graphDate="+date+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise,
	success : function(data)
	{
	$("#confirmingEscalation").html(data);
	},
	error : function()
	{
	alert("error");
	}
	});
}
function showReferralOrderCounter(divId, filter, filterFlag, status, title, color)
{
	var total = 0;
  	var fromMonth=null;
	var fromYear=null;
	var toYear=null;
	var fromTime=$("#fromTime").val();
	var toTime=$("#toTime").val();
	var specialty=$("#specialty").val();
	var locationWise='No';
	if( $('#locationView').is(":checked"))
	{
		locationWise='Yes';
	}
	else 
	{
		locationWise='NO';
	}
	filters=filterFlag;
	if(specialty!='' && specialty!='-1' && specialty!=undefined)
	{
	title=" Tickets For "+specialty+" Speciality";
	}
	else{
	title="All Tickets";
	}
	//All Priority
 	if(filterFlag=='Day')
 	{
 	 fromMonth=$("#fromMonthStatus1").val();
 	  fromYear=$("#fromYearStatus1").val();
 	}
 	else if(filterFlag=='Month')
	{
	 	  fromYear=$("#fromYearStatus2").val();
	}
 	else if(filterFlag=='Week')
	{

	 fromMonth=$("#fromMonthStatus3").val();
	  fromYear=$("#fromYearStatus3").val();
	}
 	else if(filterFlag=='Year')
	{
	  fromYear=$("#fromYearStatus4").val();
	 toYear=$("#toYearStatus").val();
	}
	  	var url1='';
	  	dataForOrder='dashboard1';
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/Referral/Dashboard_Pages/referralOrderCounter.action?fromYear="+fromYear+"&fromMonth="+fromMonth+"&filterFlag="+filterFlag+"&toYear="+toYear+"&fromTime="+fromTime+"&toTime="+toTime+"&specialty="+specialty+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise;
	//url1="view/Over2Cloud/Referral/Dashboard_Pages/referralOrderCounter.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
 	if(specialty==undefined)
	{
		if($("#departmentView").val()!=null && $("#departmentView").val()=='DepartmentView' && locationWise=='NO')
		{
	 	specialty=$("#subDept").val();
 		title=" Tickets For "+specialty+" Speciality";
		}
		else
		{
			title=" Tickets For All Speciality";
		}
	} 
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	if(data.length>10)
	widthSize=3;
	else
	widthSize=15;
	  	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	 	  	total = total + data[i].Routine + data[i].Stat+ data[i].Urgent;
	 	   	  	data[i].total=data[i].Routine + data[i].Stat+ data[i].Urgent;
	 	}
	  	 	for (var x in data) 
	 	{
	  	 	if(data[x].total>0)
	  	 	{
	 	 	 	  	data[x].RoutinePer = Math.round(data[x].Routine / data[x].total * 100);
	 	 	data[x].UrgentPer = Math.round(data[x].Urgent /data[x].total * 100);
	 	 	data[x].StatPer = Math.round(data[x].Stat /data[x].total * 100);
	  	 	}
	  	 	else
	  	 	{
	  	 	 
	 	 	 	  	data[x].RoutinePer = 0;
	 	 	data[x].UrgentPer = 0;
	 	 	data[x].StatPer = 0;
	  	 	 
	  	 	}
	 	 }
 	 	 	 
	  	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	 	if(filterFlag=='Month' || filterFlag=='Week' || filterFlag=='Year')
 	drawReferralOrderCounter(divId, data, status, title, color, "Date",total);
 	 	else if(filterFlag=='Day' || filterFlag=='Date')
 	drawReferralOrderCounterForDay(divId, data, status, title, color, "Date",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	 
  	 
 
}// end here

function drawReferralOrderCounter(divId,data,status,title,color,categ,totalData){
	
	
	console.log(data);
	var chart = AmCharts.makeChart( divId, {
  	  "type": "serial",
  	"columnSpacing": 15,
  	"titles" : [ {
	"text" :   title+": "+totalData,
	"size" : 15
	} ],
  	  "theme": "light",
  	"legend": {
  	"align": "center",
  	    "useGraphSettings": true,
  	  "equalWidths": false,
  	"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false,
	"position":"bottom"
  	  },
  	  "dataProvider": data,
  	  "valueAxes": [ {
  	  "stackType":"none",
  	    "gridColor": "#FFFFFF",
  	    "gridAlpha": 0,
  	    "dashLength": 0,
  	   
  	    "totalTextOffset":10  	    
  	  } ],
  	  "gridAboveGraphs": true,
  	  "startDuration": 1,
  	  "graphs": [ {
  	    "balloonText": "[[category]]: <b>[[value]]</b>",
  	    "fillAlphas": 1,
  	    "lineAlpha": 0,
  	    "type": "column",
  	    "valueField": "Routine",
  	"labelOffset":10,
	 	"labelText" :"[[RoutinePer]]%",
	
  	    "title":"Routine",
  	    "fixedColumnWidth":15
  	    	
  	  },
  	{
    	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	    "fillAlphas": 1,
    	    "lineAlpha": 0,
    	    "type": "column",
    	    "valueField": "Urgent",
    	"labelOffset":10,
	 	"labelText" :"[[UrgentPer]]%",
	
    	    "title":"Urgent",
    	    "fixedColumnWidth":15
    	    	
    	  },{
    	  	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	  	    "fillAlphas": 1,
    	  	    "lineAlpha": 0,
    	  	    "type": "column",
    	  	    "valueField": "Stat",
    	  	"labelOffset":10,
	 	"labelText" :"[[StatPer]]%",
	
    	  	    "title":"Stat",
    	  	    "fixedColumnWidth":15
    	  	    	
    	  	  } 
  	  
  	  ],
  	"depth3D": 10,
  	"angle": 20,
  	  
  	 "chartCursorSettings": {
	  	"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
  	  "categoryField": ""+categ,
  	  "categoryAxis": {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	  "autoWrap":true
  	  },
  	  "export": {
  	    "enabled": true
  	  }

  	} );
  	chart.write(divId);
  	
  	 AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
   	}


function drawReferralOrderCounterForDay(divId,data,status,title,color,categ,totalData){
	
 	console.log(data);
	var chart = AmCharts.makeChart( divId, {
  	  "type": "serial",
  	"columnSpacing": 20,
  	"titles" : [ {
	"text" :   title+": "+totalData,
	"size" : 15
	} ],
  	  "theme": "light",
  	"legend": {
  	"align": "left",
  	    "useGraphSettings": true,
  	  "equalWidths": false,
  	"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false,
	"position":"absolute"
  	  },
  	  "dataProvider": data,
  	  "valueAxes": [ {
  	  "stackType":"none",
  	    "gridColor": "#FFFFFF",
  	    "gridAlpha": 0,
  	    "dashLength": 0,
  	 	    "totalTextOffset":10  	    
  	  } ],
  	  "gridAboveGraphs": true,
  	  "startDuration": 1,
  	  "graphs": [ {
  	    "balloonText": "[[category]]: <b>[[value]]</b>",
  	    "fillAlphas": 1,
  	    "lineAlpha": 0,
  	    "type": "column",
  	    "valueField": "Routine",
  	"labelOffset":10,
	 	"labelText" : "[[RoutinePer]]%",
	
  	    "title":"Routine",
  	    "fixedColumnWidth":widthSize
  	    	
  	  },
  	{
    	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	    "fillAlphas": 1,
    	    "lineAlpha": 0,
    	    "type": "column",
    	    "valueField": "Urgent",
    	"labelOffset":10,
	 	"labelText" : "[[UrgentPer]]%",
	 	    "title":"Urgent",
    	    "fixedColumnWidth":widthSize
    	    	
    	  },{
    	  	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	  	    "fillAlphas": 1,
    	  	    "lineAlpha": 0,
    	  	    "type": "column",
    	  	    "valueField": "Stat",
    	  	"labelOffset":10,
	 	"labelText" : "[[StatPer]]%",
	 	 "title":"Stat",
    	  	    "fixedColumnWidth":widthSize
    	  	    	
    	  	  } 
  	  
  	  ],
  	"depth3D": 3,
  	"angle": 10,
  	  
  	 "chartCursorSettings": {
	  	"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
  	  "categoryField": ""+categ,
  	  "categoryAxis": {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	 	"autoRotateAngle": 40,
  	"autoRotateCount": 0,
  	"autoWrap":false
  	  },
  	  "export": {
  	    "enabled": true
  	  }

  	} );
  	chart.write(divId);
  	 AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
   	}


function showReferralAttendedCounter(divId, filter, filterFlag, status, title, color)
{
	 	var total = 0;
	  	var fromMonth=null;
	var fromYear=null;
	var toYear=null;
	var fromTime=$("#fromTime").val();
	var toTime=$("#toTime").val();
	var locationWise='No';
	if( $('#locationView').is(":checked"))
	{
		locationWise='Yes';
	}
	else 
	{
		locationWise='NO';
	}
	var specialty=$("#specialty").val();
	 	if(specialty!='' && specialty!='-1' && specialty!=undefined)
	{
	title=" Tickets For "+specialty+" Speciality";
	 
	}
	else{
	title="All Tickets";
	}
	filters=filterFlag;
	 	if(filterFlag=='Day')
	 	{
	 	 fromMonth=$("#fromMonthStatus1").val();
	 	  fromYear=$("#fromYearStatus1").val();
	 	}
	 	else if(filterFlag=='Month')
 	{
 	 	  fromYear=$("#fromYearStatus2").val();
 	}
	 	else if(filterFlag=='Week')
 	{

	 fromMonth=$("#fromMonthStatus3").val();
	  fromYear=$("#fromYearStatus3").val();
 	}
	 	else if(filterFlag=='Year')
 	{
 	  fromYear=$("#fromYearStatus4").val();
 	 toYear=$("#toYearStatus").val();
 	}
	 	dataForStatus='dashboard2';
	  	var url1='';
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/Referral/Dashboard_Pages/referralAttendedCounter.action?fromYear="+fromYear+"&fromMonth="+fromMonth+"&filterFlag="+filterFlag+"&toYear="+toYear+"&fromTime="+fromTime+"&toTime="+toTime+"&specialty="+specialty+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise;
	//url1="view/Over2Cloud/Referral/Dashboard_Pages/referralOrderCounter.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	if(specialty==undefined)
	{
		if($("#departmentView").val()!=null && $("#departmentView").val()=='DepartmentView' && locationWise=='NO')
		{
	 	specialty=$("#subDept").val();
 		title=" Tickets For "+specialty+" Speciality";
		}
		else
		{
			title=" Tickets For All Speciality";
		}
	} 	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	if(data.length>10)
	widthSize=3;
	else
	widthSize=15;
	for (var i = 0; i <= data.length - 1; i++)
	{
	//alert(total+"    "+data[i].Consultant+""+ data[i].Resident+"  "+data[i].MedicalOfficer);
	 	 	  	total = total + data[i].Consultant + data[i].Resident+ data[i].MedicalOfficer;
	 	   	  	data[i].total=+ data[i].Consultant + data[i].Resident+ data[i].MedicalOfficer;
	 	}
	 	for (var x in data) 
	 	{
	 	 	 	  if(data[x].total>0)
	 	 	 	  {
	 	data[x].ConsultantPer = Math.round(data[x].Consultant / data[x].total * 100);
	 	 	data[x].ResidentPer = Math.round(data[x].Resident /data[x].total * 100);
	 	 	data[x].MedicalOfficerPer = Math.round(data[x].MedicalOfficer /data[x].total * 100);
	 	 	data[x].totalPer = Math.round(data[x].total /data[x].total * 100);
	 	 	 	  }
	 	 	 	  else
	 	 	 	  {
	 	 	 	data[x].ConsultantPer = 0;
	 	 	data[x].ResidentPer = 0;
	 	 	data[x].MedicalOfficerPer =0;
	 	 	 	  
	 	 	 	  }
	 	 	
	 	 }
	  	 	 
	  	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	 	if(filterFlag=='Month' || filterFlag=='Week' || filterFlag=='Year')
 	drawReferralAttendedCounter(divId, data, status, title, color, "Date",total);
 	 	else if(filterFlag=='Day' || filterFlag=='Date')
 	drawReferralAttendedCounterForDay(divId, data, status, title, color, "Date",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	 
  	 
 
}// end here



function drawReferralAttendedCounter(divId,data,status,title,color,categ,totalData){
	
	
	console.log(data);
	var chart = AmCharts.makeChart( divId, {
  	  "type": "serial",
  	"columnSpacing": 15,
  	"titles" : [ {
	"text" :  title+": "+totalData,
	"size" : 15
	} ],
  	  "theme": "light",
  	"legend": {
  	"align": "center",
  	    "useGraphSettings": true,
  	  "equalWidths": false,
  	"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false,
	"position":"bottom"
  	  },
  	  "dataProvider": data,
  	  "valueAxes": [ {
  	  "stackType":"none",
  	    "gridColor": "#FFFFFF",
  	    "gridAlpha": 0,
  	    "dashLength": 0,
  	   
  	    "totalTextOffset":10  	    
  	  } ],
  	  
  	  "gridAboveGraphs": true,
  	  "startDuration": 1,
  	  "graphs": [ {
  	    "balloonText": "[[category]]: <b>[[value]]</b>",
  	    "fillAlphas": 1,
  	    "lineAlpha": 0,
  	    "type": "column",
  	    "valueField": "Consultant",
  	"labelOffset":10,
	 	"labelText" : "[[ConsultantPer]]%",
	
  	    "title":"Consultant",
  	    "fixedColumnWidth":15
  	    	
  	  },
  	{
    	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	    "fillAlphas": 1,
    	    "lineAlpha": 0,
    	    "type": "column",
    	    "valueField": "Resident",
    	"labelOffset":10,
	 	"labelText" : "[[ResidentPer]]%",
	
    	    "title":"Resident",
    	    "fixedColumnWidth":15
    	    	
    	  },{
    	  	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	  	    "fillAlphas": 1,
    	  	    "lineAlpha": 0,
    	  	    "type": "column",
    	  	    "valueField": "MedicalOfficer",
    	  	"labelOffset":10,
	 	"labelText" : "[[MedicalOfficerPer]]%",
	
    	  	    "title":"Medical Officer",
    	  	    "fixedColumnWidth":15
    	  	    	
    	  	  } 
  	  
  	  ],
  	"depth3D": 10,
  	"angle": 20,
  	  
  	 "chartCursorSettings": {
	  	"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
  	  "categoryField": ""+categ,
  	  "categoryAxis": {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	  "autoWrap":true
  	  },
  	  "export": {
  	    "enabled": true
  	  }

  	} );
  	chart.write(divId);
  	 AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
   	}


function drawReferralAttendedCounterForDay(divId,data,status,title,color,categ,totalData){
	
	
	console.log(data);
	var chart = AmCharts.makeChart( divId, {
  	  "type": "serial",
  	"columnSpacing": 20,
  	"titles" : [ {
	"text" :  title+": "+totalData,
	"size" : 15
	} ],
  	  "theme": "light",
  	"legend": {
  	"align": "left",
  	    "useGraphSettings": true,
  	  "equalWidths": false,
  	"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false,
	"position":"absolute"
  	  },
  	  "dataProvider": data,
  	  "valueAxes": [ {
  	  "stackType":"none",
  	    "gridColor": "#FFFFFF",
  	    "gridAlpha": 0,
  	    "dashLength": 0,
  	 	    "totalTextOffset":10  	    
  	  } ],
  	  "gridAboveGraphs": true,
  	  "startDuration": 1,
  	  "graphs": [ {
  	    "balloonText": "[[category]]: <b>[[value]]</b>",
  	    "fillAlphas": 1,
  	    "lineAlpha": 0,
  	    "type": "column",
  	    "valueField": "Consultant",
  	"labelOffset":10,
	 	"labelText" : "[[ConsultantPer]]%",
	
  	    "title":"Consultant",
  	    "fixedColumnWidth":widthSize
  	    	
  	  },
  	{
    	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	    "fillAlphas": 1,
    	    "lineAlpha": 0,
    	    "type": "column",
    	    "valueField": "Resident",
    	"labelOffset":10,
	 	"labelText" : "[[ResidentPer]]%",
	 	    "title":"Resident",
    	    "fixedColumnWidth":widthSize
    	    	
    	  },{
    	  	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	  	    "fillAlphas": 1,
    	  	    "lineAlpha": 0,
    	  	    "type": "column",
    	  	    "valueField": "MedicalOfficer",
    	  	"labelOffset":10,
	 	"labelText" : "[[MedicalOfficerPer]]%",
	 	 "title":"Medical Officer",
    	  	    "fixedColumnWidth":widthSize
    	  	    	
    	  	  } 
  	  
  	  ],
  	"depth3D": 3,
  	"angle": 10,
  	  
  	 "chartCursorSettings": {
	  	"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
  	  "categoryField": ""+categ,
  	  "categoryAxis": {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	 	"autoRotateAngle": 40,
  	"autoRotateCount": 0,
  	"autoWrap":true
  	  },
  	  "export": {
  	    "enabled": true
  	  }

  	} );
  	chart.write(divId);
  	 AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
   	}




function showReferralStatusCounter(divId, filter, filterFlag, status, title, color)
{
	 	var total = 0;
	  	var fromMonth=null;
	var fromYear=null;
	var toYear=null;
	var fromTime=$("#fromTime").val();
	var toTime=$("#toTime").val();
	var specialty=$("#specialty").val();
	var refDoc=$("#refDoc").val();
	var locationWise='No';
	if( $('#locationView').is(":checked"))
	{
		locationWise='Yes';
	}
	else 
	{
		locationWise='NO';
	}
	if(specialty!='' && specialty!='-1' && specialty!=undefined)
	{
	title=" Tickets For "+specialty+" Speciality";
	 
	}
	else{
	title="All Tickets"
	}
	filters=filterFlag;
	 	if(filterFlag=='Day')
	 	{
	 	 fromMonth=$("#fromMonthStatus1").val();
	 	  fromYear=$("#fromYearStatus1").val();
	 	}
	 	else if(filterFlag=='Month')
 	{
 	 	  fromYear=$("#fromYearStatus2").val();
 	}
	 	else if(filterFlag=='Week')
 	{

	 fromMonth=$("#fromMonthStatus3").val();
	  fromYear=$("#fromYearStatus3").val();
 	}
	 	else if(filterFlag=='Year')
 	{
 	  fromYear=$("#fromYearStatus4").val();
 	 toYear=$("#toYearStatus").val();
 	}
	 	dataForStatus='dashboard1';
	  	var url1='';
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/Referral/Dashboard_Pages/referralStatusCounter.action?fromYear="+fromYear+"&fromMonth="+fromMonth+"&filterFlag="+filterFlag+"&toYear="+toYear+"&fromTime="+fromTime+"&toTime="+toTime+"&specialty="+specialty+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise;
	//url1="view/Over2Cloud/Referral/Dashboard_Pages/referralOrderCounter.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	if(specialty==undefined)
	{
		if($("#departmentView").val()!=null && $("#departmentView").val()=='DepartmentView' && locationWise=='NO')
		{
	 	specialty=$("#subDept").val();
 		title=" Tickets For "+specialty+" Speciality";
		}
		else
		{
			title=" Tickets For All Speciality";
		}
	} 
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	if(data.length>10)
	widthSize=3;
	else
	widthSize=15;
	for (var i = 0; i <= data.length - 1; i++)
	{
	 	 	  	total = total + data[i].Close + data[i].Open;
	 	   	  	data[i].total=data[i].Close + data[i].Open;
	 	}
	  	for (var x in data) 
	 	{
	 	 	 	  if(data[x].total>0)
	 	 	 	  {
	 	data[x].ClosePer = Math.round(data[x].Close / data[x].total * 100);
	 	 	data[x].OpenPer = Math.round(data[x].Open /data[x].total * 100);
	 	 	data[x].totalPer = Math.round(data[x].total /data[x].total * 100);
	 	 	 	  }
	 	 	 	  else
	 	 	 	  {
	 	 	 	data[x].ClosePer = 0;
	 	 	data[x].OpenPer = 0;
	 	 	data[x].totalPer =0;
	 	 	 	  
	 	 	 	  }
	 	 	
	 	 }
	  	 	 
	  	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	 	if(filterFlag=='Month' || filterFlag=='Week' || filterFlag=='Year')
 	drawReferralStatusCounter(divId, data, status, title, color, "Date",total);
 	 	else if(filterFlag=='Day' || filterFlag=='Date')
 	drawReferralStatusCounterForDay(divId, data, status, title, color, "Date",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	 
  	 
 
}// end here





function drawReferralStatusCounter(divId,data,status,title,color,categ,totalData){
	
	
	console.log(data);
	var chart = AmCharts.makeChart( divId, {
  	  "type": "serial",
  	"columnSpacing": 15,
  	"titles" : [ {
	"text" :  title+": "+totalData,
	"size" : 15
	} ],
  	  "theme": "light",
  	"legend": {
  	"align": "center",
  	    "useGraphSettings": true,
  	  "equalWidths": false,
  	"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false,
	"position":"bottom"
  	  },
  	  "dataProvider": data,
  	  "valueAxes": [ {
  	  "stackType":"none",
  	    "gridColor": "#FFFFFF",
  	    "gridAlpha": 0,
  	    "dashLength": 0,
  	   
  	    "totalTextOffset":10  	    
  	  } ],
  	  
  	  "gridAboveGraphs": true,
  	  "startDuration": 1,
  	  "graphs": [ {
  	    "balloonText": "[[category]]: <b>[[value]]</b>",
  	    "fillAlphas": 1,
  	    "lineAlpha": 0,
  	    "type": "column",
  	    "valueField": "Close",
  	"labelOffset":10,
	 	"labelText" : "[[ClosePer]]%",
	
  	    "title":"Close",
  	    "fixedColumnWidth":15
  	    	
  	  },
  	{
    	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	    "fillAlphas": 1,
    	    "lineAlpha": 0,
    	    "type": "column",
    	    "valueField": "Open",
    	"labelOffset":10,
	 	"labelText" : "[[OpenPer]]%",
	
    	    "title":"Open",
    	    "fixedColumnWidth":15
    	    	
    	  } 
  	  
  	  ],
  	"depth3D": 10,
  	"angle": 20,
  	  
  	 "chartCursorSettings": {
	  	"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
  	  "categoryField": ""+categ,
  	  "categoryAxis": {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	  "autoWrap":true
  	  },
  	  "export": {
  	    "enabled": true
  	  }

  	} );
  	chart.write(divId);
  	
  	 AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
   	}


function drawReferralStatusCounterForDay(divId,data,status,title,color,categ,totalData){
	
	
	console.log(data);
	var chart = AmCharts.makeChart( divId, {
  	  "type": "serial",
  	"columnSpacing": 20,
  	"titles" : [ {
	"text" :  title+": "+totalData,
	"size" : 15
	} ],
  	  "theme": "light",
  	"legend": {
  	"align": "left",
  	    "useGraphSettings": true,
  	  "equalWidths": false,
  	"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false,
	"position":"absolute"
  	  },
  	  "dataProvider": data,
  	  "valueAxes": [ {
  	  "stackType":"none",
  	    "gridColor": "#FFFFFF",
  	    "gridAlpha": 0,
  	    "dashLength": 0,
  	 	    "totalTextOffset":10  	    
  	  } ],
  	  "gridAboveGraphs": true,
  	  "startDuration": 1,
  	  "graphs": [ {
  	    "balloonText": "[[category]]: <b>[[value]]</b>",
  	    "fillAlphas": 1,
  	    "lineAlpha": 0,
  	    "type": "column",
  	    "valueField": "Close",
  	"labelOffset":10,
	 	"labelText" : "[[ClosePer]]%",
	
  	    "title":"Close",
  	    "fixedColumnWidth":widthSize
  	    	
  	  },
  	{
    	    "balloonText": "[[category]]: <b>[[value]]</b>",
    	    "fillAlphas": 1,
    	    "lineAlpha": 0,
    	    "type": "column",
    	    "valueField": "Open",
    	"labelOffset":10,
	 	"labelText" : "[[OpenPer]]%",
	 	    "title":"Open",
    	    "fixedColumnWidth":widthSize
    	    	
    	  } 
  	  
  	  ],
  	"depth3D": 3,
  	"angle": 10,
  	  
  	 "chartCursorSettings": {
	  	"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
  	  "categoryField": ""+categ,
  	  "categoryAxis": {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	 	"autoRotateAngle": 40,
  	"autoRotateCount": 0,
  	"autoWrap":true
  	  },
  	  "export": {
  	    "enabled": true
  	  }

  	} );
  	chart.write(divId);
  	
  	 AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
   	}


function getData(date, status, flag)
{
	var locationWise='No';
	if( $('#locationView').is(":checked"))
	{
		locationWise='Yes';
	}
	else 
	{
		locationWise='NO';
	}
	  var filters=$("#dateRangeStatus").val();
	 var attendedBy=null;
	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	 var url1='';
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 
	if(flag=='Status')
	{
	if(status=='All')
	status="'Informed','Not Informed','Snooze','Snooze-I','Seen'";
	
	else if(status=='Open')
	status= "'Informed','Not Informed','Snooze','Snooze-I'";
	else if(status=='Close')
	status="'Seen'";
 	}
	if(flag=='Speciality')
	{
	 	 attendedBy=$("#designation").val();
	 }
	url1 = "view/Over2Cloud/Referral/Dashboard_Pages/beforeStatusGridView.action?flag=" + flag + "&feedStatus=" + status +"&filterFlag="+filters+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&specialty="+$("#specialty").val()+"&graphDate="+date+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&attendedBy="+attendedBy+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise;

 
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
 
 
 function showData(id,filterFlag,type)
 {
	 var fromMonth=null;
	var fromYear=null;
	var toYear=null;
	var locationWise='No';
	if( $('#locationView').is(":checked"))
	{
		locationWise='Yes';
	}
	else 
	{
		locationWise='NO';
	}
	var specialty=$("#specialty").val();
	var refDoc=$("#refDoc").val();
	$("#"+id).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	if(id=='referralOrder')
 	{
 	if(filterFlag=='Day')
 	{
 	 fromMonth=$("#fromMonthStatus1").val();
 	  fromYear=$("#fromYearStatus1").val();
 	}
 	else if(filterFlag=='Month')
	{
	 	  fromYear=$("#fromYearStatus2").val();
	}
 	else if(filterFlag=='Week')
	{

	 fromMonth=$("#fromMonthStatus3").val();
	  fromYear=$("#fromYearStatus3").val();
	}
 	else if(filterFlag=='Year')
	{
	  fromYear=$("#fromYearStatus4").val();
	 toYear=$("#toYearStatus").val();
	}
 	 	var url1='';
 	 	dataForOrder='table1';
 	  	url1="view/Over2Cloud/Referral/Dashboard_Pages/referralOrderCounterTable.action?fromYear="+fromYear+"&fromMonth="+fromMonth+"&filterFlag="+filterFlag+"&toYear="+toYear+"&dashFor="+type+"&specialty="+specialty+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise;
  	$.ajax( {
 	type :"post",
 	url : url1,
 	success : function(data) 
 	{    
 	
 	 	 	$("#"+id).html(data);
 	 
 	},
 	
  	error : function() {
 	alert("error");
 	}
 	});
  } 
 	
 	if(id=='referralStatus')
 	{
 	 	 	if(filterFlag=='Day')
	 	{
	 	 fromMonth=$("#fromMonthStatus1").val();
	 	  fromYear=$("#fromYearStatus1").val();
	 	}
	 	else if(filterFlag=='Month')
 	{
 	 	  fromYear=$("#fromYearStatus2").val();
 	}
	 	else if(filterFlag=='Week')
 	{

	 fromMonth=$("#fromMonthStatus3").val();
	  fromYear=$("#fromYearStatus3").val();
 	}
	 	else if(filterFlag=='Year')
 	{
 	  fromYear=$("#fromYearStatus4").val();
 	 toYear=$("#toYearStatus").val();
 	}
 	 	var url1='';
 	 	if(type=='Speciality')
 	 	dataForStatus='table3';
 	 	else if(type=='Attended')
 	 	dataForStatus='table2';
 	 	else if(type=='Status')
 	 	dataForStatus='table1';
 	  	url1="view/Over2Cloud/Referral/Dashboard_Pages/referralStatusCounterTable.action?fromYear="+fromYear+"&fromMonth="+fromMonth+"&filterFlag="+filterFlag+"&toYear="+toYear+"&dashFor="+type+"&specialty="+specialty+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&emergency="+$("#emergency").val()+"&fromDate1="+$("#fromDate").val()+"&toDate1="+$("#toDate").val()+"&attendedBy=Doctor"+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise;
  	$.ajax( {
 	type :"post",
 	url : url1,
 	success : function(data) 
 	{    
 	   	$("#"+id).html(data);
 	   	},
 	error : function() {
 	alert("error");
 	}
 	});
  } 
 	
 }