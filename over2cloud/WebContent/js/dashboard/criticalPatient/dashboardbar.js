var specialty1='';
var testType1='';
var location1='';
var locationName1='';
var recordStatus1='';
var dashboard1='';
var feelColor1='';

var specialty2='';
var testType2='';
var location2='';
var locationName2='';
var dashboard2='';
var recordStatus2='';
var feelColor2='';
 
 

function crcReport(){
  	 
$("#criticalReportDialog").dialog('open');
$("#criticalReport").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
type : "post",
url : "view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$("#fromDateSearch").val()+"&toDate="+$("#toDateSearch").val()+"&tableFor=crcReport"+"&fromTime="+$("#fTime").val()+"&toTime="+$("#tTime").val()+"&statusFor="+$("#serviceID").val(),
success : function(data) {
		$("#criticalReport").html(data);
},
error: function() {
    alert("error");
}
});
}
function showTimeDiv(status)
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
function showTimeDivForReport(status)
{
	 
	 if(status==true)
		 {
	  		$(".statusTime1").show();
	  		$("#fTime").val("");
	  		$("#tTime").val("");
	  		
		 }
	 else if(status==false)
		 {
	 		$(".statusTime1").hide();
	 		$("#fTime").val("");
	  		$("#tTime").val("");
	  		crcReport();
		 }
	  
}
function searchCriticalData(){
	 
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Critical/beforeCritical.action",
	    success : function(data) {
      		$("#confirmingEscalation").html(data);
		},
	    error: function() {
           alert("error");
       }
	 });
}

 


function getDataForAllBoard()
{

	if(dashboard1=='specialty')
 		showStatusStackedBar('statusChart', 'specialty', '', 'All', 'All', '#09A1A4');
	else if(dashboard1=='doctor')
		showStatusStackedBar('statusChart', 'doctor', '', 'All', 'All', '#09A1A4',specialty1 );
	else if(dashboard1=='testType')
		showStatusStackedBar('statusChart', 'testType', '', 'All', 'All', '#09A1A4');
	else if(dashboard1=='testName')
		showStatusStackedBar('statusChart', 'testName', '', 'All', 'All', '#09A1A4',testType1 );
	else if(dashboard1=='location')
		showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4');
	else if(dashboard1=='nursingUnit')
		showStatusStackedBar('statusChart', 'nursingUnit', '', 'All', 'All', '#09A1A4',location1 );
	else if(dashboard1=='specialty_table')
		showData('statusChart','','specialty');
	else if(dashboard1=='doctor_table')
		showData('statusChart','','doctor');
	else if(dashboard1=='testType_table')
		showData('statusChart','','testType');
	else if(dashboard1=='testName_table')
		showData('statusChart','','testName');
	else if(dashboard1=='location_table')
		showData('statusChart','','location');
	else if(dashboard1=='nursingUnit_table')
		showData('statusChart','','nursingUnit');
	
	
	
	if(dashboard2=='specialtyProductivity')
		showProductivityStackedBar('productivityChart', 'specialtyProductivity', '', 'All', 'All', '#09A1A4');
	else if(dashboard2=='doctorProductivity')
		showProductivityStackedBar('productivityChart', 'doctorProductivity', '', 'All', 'All', '#09A1A4',specialty2 );
	else if(dashboard2=='testTypeProductivity')
		showProductivityStackedBar('productivityChart', 'testTypeProductivity', '', 'All', 'All', '#09A1A4');
	else if(dashboard2=='testNameProductivity')
		showProductivityStackedBar('productivityChart', 'testNameProductivity', '', 'All', 'All', '#09A1A4',testType2 );
	else if(dashboard2=='locationProductivity')
		showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4');
	else if(dashboard2=='nursingUnitProductivity')
		showProductivityStackedBar('productivityChart', 'nursingUnitProductivity', '', 'All', 'All', '#09A1A4',location2 );
	else if(dashboard2=='specialtyProductivity_table')
		showData('productivityChart','','specialtyProductivity');
	else if(dashboard2=='doctorProductivity_table')
		showData('productivityChart','','doctorProductivity');
	else if(dashboard2=='testTypeProductivity_table')
		showData('productivityChart','','testTypeProductivity');
	else if(dashboard2=='testNameProductivity_table')
		showData('productivityChart','','testNameProductivity');
	else if(dashboard2=='locationProductivity_table')
		showData('productivityChart','','locationProductivity');
	else if(dashboard2=='nursingUnitProductivity_table')
		showData('productivityChart','','nursingUnitProductivity');
		
	 
}

function getDataForStatusCounter()
{

	if(dashboard1=='specialty')
 		showStatusStackedBar('statusChart', 'specialty', '', 'All', 'All', '#09A1A4');
	else if(dashboard1=='doctor')
		showStatusStackedBar('statusChart', 'doctor', '', 'All', 'All', '#09A1A4',specialty1 );
	else if(dashboard1=='testType')
		showStatusStackedBar('statusChart', 'testType', '', 'All', 'All', '#09A1A4');
	else if(dashboard1=='testName')
		showStatusStackedBar('statusChart', 'testName', '', 'All', 'All', '#09A1A4',testType1 );
	else if(dashboard1=='location')
		showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4');
	else if(dashboard1=='nursingUnit')
		showStatusStackedBar('statusChart', 'nursingUnit', '', 'All', 'All', '#09A1A4',location1 );
	else if(dashboard1=='specialty_table')
		showData('statusChart','','specialty');
	else if(dashboard1=='doctor_table')
		showData('statusChart','','doctor');
	else if(dashboard1=='testType_table')
		showData('statusChart','','testType');
	else if(dashboard1=='testName_table')
		showData('statusChart','','testName');
	else if(dashboard1=='location_table')
		showData('statusChart','','location');
	else if(dashboard1=='nursingUnit_table')
		showData('statusChart','','nursingUnit');
	
	
	
	 
	 
}
function getDataForProductivity()
{
 	
	if(dashboard2=='specialtyProductivity')
		showProductivityStackedBar('productivityChart', 'specialtyProductivity', '', 'All', 'All', '#09A1A4');
	else if(dashboard2=='doctorProductivity')
		showProductivityStackedBar('productivityChart', 'doctorProductivity', '', 'All', 'All', '#09A1A4',specialty2 );
	else if(dashboard2=='testTypeProductivity')
		showProductivityStackedBar('productivityChart', 'testTypeProductivity', '', 'All', 'All', '#09A1A4');
	else if(dashboard2=='testNameProductivity')
		showProductivityStackedBar('productivityChart', 'testNameProductivity', '', 'All', 'All', '#09A1A4',testType2 );
	else if(dashboard2=='locationProductivity')
		showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4');
	else if(dashboard2=='nursingUnitProductivity')
		showProductivityStackedBar('productivityChart', 'nursingUnitProductivity', '', 'All', 'All', '#09A1A4',location2 );
	else if(dashboard2=='specialtyProductivity_table')
		showData('productivityChart','','specialtyProductivity');
	else if(dashboard2=='doctorProductivity_table')
		showData('productivityChart','','doctorProductivity');
	else if(dashboard2=='testTypeProductivity_table')
		showData('productivityChart','','testTypeProductivity');
	else if(dashboard2=='testNameProductivity_table')
		showData('productivityChart','','testNameProductivity');
	else if(dashboard2=='locationProductivity_table')
		showData('productivityChart','','locationProductivity');
	else if(dashboard2=='nursingUnitProductivity_table')
		showData('productivityChart','','nursingUnitProductivity');
		
	 
}
//For Double click
function handleClickStatus(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	var dataFor = graph.title;

	if (dataFor == 'Resolve')
	{
	dataFor = 'Resolved';
	} else if (dataFor == 'Park')
	{
	dataFor = 'Snooze';
	}  
	getDataForStatus(dataContx.deptId, dataFor, 'SC', 'status', '');
}

function getDataForStatus(id, status, dataForId, flag, level)
{
	  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
  	
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?dataFlag=" + flag + "&feedStatus=" + status + "&machineId=" + id +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
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

 
 

function doctorWiseChartForSpecialty(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	specialty1=dataContx.Id;
	showStatusStackedBar('statusChart', 'doctor', '', recordStatus1, recordStatus1, graph.fillColors,dataContx.Id);
}

function testNameWiseChartForTestType(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	testType1=dataContx.Id;
	showStatusStackedBar('statusChart', 'testName', '', recordStatus1, recordStatus1, graph.fillColors,dataContx.Id);
}
 

function nursingUnitWiseChartForLocation(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	location1=dataContx.Id;
	locationName1=dataContx.location;
	showStatusStackedBar('statusChart', 'nursingUnit', '', recordStatus1, recordStatus1, graph.fillColors,dataContx.Id);
}

function doctorWiseProductivityChartForspecialty(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	specialty2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'doctorProductivity', '', recordStatus2, recordStatus2, graph.fillColors,dataContx.Id);
}


function testNameWiseProductivityChartForTestType(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	testType2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'testNameProductivity', '', recordStatus2, recordStatus2, graph.fillColors,dataContx.Id);
}
 
function nursingUnitWiseProductivityChartForLocation(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	location2=dataContx.Id;
	locationName2=dataContx.locationProductivity;
	showProductivityStackedBar('productivityChart', 'nursingUnitProductivity', '', recordStatus2, recordStatus2, graph.fillColors,dataContx.Id);
}
 

function showStatusStackedBar(divId, filterFlag, filterDeptId, status, title, color,Id)
{
 	recordStatus1=status;
	feelColor1=color;
	var statusFor=$("#statusFor").val();
 	var total = 0;
   	var url1='';
	if(filterFlag=='specialty')
	{
		$("#backbttn1").attr('onclick','').css('display','none');
		$(".statusSpecialtyPanel").show();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
		$("spec1").css('background-color','red');
		dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	else if(filterFlag=='doctor')
	{
	 		$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","specialty" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").show();
		$(".statusNursingUnitPanel").hide();
		dashboard1=filterFlag;
		title=title+" Tickets For "+specialty1+" Specialty:";
		
	}
	else if(filterFlag=='testType')
	{
	 	$("#backbttn1").attr('onclick','').css('display','none');
		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").show();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
		dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	else if(filterFlag=='testName')
	{
		 	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","testType" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").show();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
		dashboard1=filterFlag;
		title=title+" Tickets For "+testType1+" Test Type:";
	}
	else if(filterFlag=='location')
	{
	 	$("#backbttn1").attr('onclick','').css('display','none');
		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").show();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
		dashboard1=filterFlag;
		title=title+" Tickets:";
	}
	else if(filterFlag=='nursingUnit')
	{
	 	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","location" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").show();
		dashboard1=filterFlag;
		title=title+" Tickets For "+locationName1+" Location:";
	}
   	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&id="+Id;
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

  
 

function showProductivityStackedBar(divId, filterFlag, filterDeptId, status, title, color,Id)
{
 	recordStatus2=status;
	feelColor2=color;
	var statusFor=$("#statusForProductivity").val();
	var productivityFor=$("#productivityFor").val();
	var productivityLimit=$("#productivityLimit").val();
  	var total = 0;
   	var url1='';
   if(filterFlag=='specialtyProductivity')
	{
		$("#backbttn2").attr('onclick','').css('display','none');
	 	$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").show();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
 		dashboard2=filterFlag;
 		title=title+" Tickets:";
	}
	else if(filterFlag=='doctorProductivity')
	{
		$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","specialtyProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	 	$(".doctorProductivityPanel").show();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
		dashboard2=filterFlag;
		title=title+" Tickets For "+specialty2+" Specialty:";
		
	}
	else if(filterFlag=='testTypeProductivity')
	{
		$("#backbttn2").attr('onclick','').css('display','none');
	 	$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").show();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
		dashboard2=filterFlag;
		title=title+" Tickets:";
	}
	else if(filterFlag=='testNameProductivity')
	{
		$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","testTypeProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	 	$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").show();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
		dashboard2=filterFlag;
		title=title+" Tickets For "+testType2+" Test Type:";
	}
	else if(filterFlag=='locationProductivity')
	{
		$("#backbttn2").attr('onclick','').css('display','none');
	 	$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").show();
		$(".nursingUnitProductivityPanel").hide();
		dashboard2=filterFlag;
		title=title+" Tickets:";
	}
	else if(filterFlag=='nursingUnitProductivity')
	{
		$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","locationProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	 	$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").show();
		dashboard2=filterFlag;
		title=title+" Tickets For "+locationName2+" Location:";
	}
 
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&statusFor="+statusFor+"&id="+Id+"&productivityFor="+productivityFor+"&productivityLimit="+productivityLimit;
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
 		 
 		drawChartForProductivity(divId, data, status, title, color, filterFlag,total);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
  
   if (productivityLimit=='' && status!='All')  
   	{
     	alert("Please Enter On Time Limit..!!");
   	}
   		
}// end here

    
 
function drawChartForStatus(divId, data, status, title, color, Category,totalData)
{
	
	 
	  
	  var chart='';
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
	 	"labelText" : "[[totalPer]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 10

	}

	],
	"depth3D" : 7,
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
  	 	"autoRotateAngle": 20,
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
	   
	
	if(Category=='testType')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
		  testNameWiseChartForTestType(event);
	};
	  }
	  
	  
	  
	  
	   if(Category=='testName')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	  
	   
	   if(Category=='specialty')
		  {
		  AmCharts.doDoubleClick1 = function(event)
		  {
		//  handleClickStatusLocation(event);
		  };
		  AmCharts.doSingleClick1 = function(event)
		{
			  doctorWiseChartForSpecialty(event);
		};
		  }
	   
	   if(Category=='doctor')
		  {
		  AmCharts.doDoubleClick1 = function(event)
		  {
		//  handleClickStatusLocation(event);
		  };
		  AmCharts.doSingleClick1 = function(event)
		{
		  alert("Sorry! No any data on single click");
		};
		  }
	   
	   
	   
	   
		  
		   
		   if(Category=='location')
			  {
			  AmCharts.doDoubleClick1 = function(event)
			  {
			//  handleClickStatusLocation(event);
			  };
			  AmCharts.doSingleClick1 = function(event)
			{
				  nursingUnitWiseChartForLocation(event);
			};
			  }
		  
		   if(Category=='nursingUnit')
			  {
			  AmCharts.doDoubleClick1 = function(event)
			  {
			//  handleClickStatusLocation(event);
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
 


function drawChartForProductivity(divId, data, status, title, color, Category,totalData)
{
	
	 
	  
	  var chart='';
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
	 	"labelText" : "[[totalPer]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 10

	}

	],
	"depth3D" : 7,
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
  	 	"autoRotateAngle": 20,
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
	 
	if(Category=='specialtyProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
		  doctorWiseProductivityChartForspecialty(event);
	};
	  }
	  
	  
	  
	  
	   if(Category=='doctorProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	   
	   if(Category=='testTypeProductivity')
		  {
		  AmCharts.doDoubleClick2 = function(event)
		  {
		//  handleClickStatusLocation(event);
		  };
		  AmCharts.doSingleClick2 = function(event)
		{
			  testNameWiseProductivityChartForTestType(event);
		};
		  }
		  
		  
		  
		  
		   if(Category=='testNameProductivity')
		  {
		  AmCharts.doDoubleClick2 = function(event)
		  {
		//  handleClickStatusLocation(event);
		  };
		  AmCharts.doSingleClick2 = function(event)
		{
		  alert("Sorry! No any data on single click");
		};
		  }
	  
		   
		   if(Category=='locationProductivity')
			  {
			  AmCharts.doDoubleClick2 = function(event)
			  {
			//  handleClickStatusLocation(event);
			  };
			  AmCharts.doSingleClick2 = function(event)
			{
				  nursingUnitWiseProductivityChartForLocation(event);
			};
			  }
			  
			  
			  
			  
			   if(Category=='nursingUnitProductivity')
			  {
			  AmCharts.doDoubleClick2 = function(event)
			  {
			//  handleClickStatusLocation(event);
			  };
			  AmCharts.doSingleClick2 = function(event)
			{
			  alert("Sorry! No any data on single click");
			};
			  }
	  
	 
	  
	  
	  
	   
	  
	  
	  AmCharts.myClickHandler2 = function(event)
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
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler2);
}
 


function getData(Id, status, filterFlag, flag1,flag2)
{
 
	var fromDate='';
	var toDate='';
	var fromTime='';
	var toTime='';
	var statusFor='';
	var productivityFor='';
	var productivityLimit='';
	if(filterFlag=='specialtyProductivity' || filterFlag=='doctorProductivity'  || filterFlag=='testTypeProductivity'  || filterFlag=='testNameProductivity' || filterFlag=='locationProductivity' || filterFlag=='nursingUnitProductivity')
	{
		fromDate=$('#sdate').val();
		toDate=$('#edate').val();
		fromTime=$("#fromTime").val();
		toTime=$("#toTime").val();
		statusFor=$("#statusForProductivity").val();
		productivityFor=$("#productivityFor").val();
		productivityLimit=$("#productivityLimit").val();
	}
	if(filterFlag=='specialty' || filterFlag=='doctor'  || filterFlag=='testType'  || filterFlag=='testName' || filterFlag=='location' || filterFlag=='nursingUnit')
	{
		fromTime=$("#fromTime").val();
		toTime=$("#toTime").val();
		fromDate=$('#sdate').val();
		toDate=$('#edate').val();
		 statusFor=$("#statusFor").val();
	}
	if(filterFlag=='crcReport')
	{
		fromDate=$('#fromDateSearch').val();
		toDate=$('#toDateSearch').val();
		fromTime=$("#fTime").val();
		toTime=$("tTime").val();
		statusFor=$("#serviceID").val()
	}
	 
	 $("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	 var url1='';
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/beforeFeedAction.action?fromDate="+fromDate+"&toDate="+toDate+"&status="+status+"&tableFor="+filterFlag+"&fromTime="+fromTime+"&toTime="+toTime+"&statusFor="+statusFor+"&id="+Id+"&productivityFor="+productivityFor+"&productivityLimit="+productivityLimit;
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
 


 
 
 
 function showData(id,flag,val)
 {
	 	var url1='';
 	$("#"+id).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
  	if(val=='specialty')
 	{
 		dashboard1=val+'_table';
 		var statusFor=$("#statusFor").val();
 		$(".statusSpecialtyPanel").show();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
 	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus1+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
 	}
 	else if(val=='doctor')
 	{
 		dashboard1=val+'_table';
 		var statusFor=$("#statusFor").val();
 		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").show();
		$(".statusNursingUnitPanel").hide();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus1+"&tableFor="+val+"&id="+specialty1+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
	else if(val=='testType')
 	{
 		dashboard1=val+'_table';
 		var statusFor=$("#statusFor").val();
 		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").show();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus1+"&tableFor="+val+"&id="+specialty1+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
	else if(val=='testName')
 	{
 		dashboard1=val+'_table';
 		var statusFor=$("#statusFor").val();
 		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").show();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus1+"&tableFor="+val+"&id="+testType1+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
	else if(val=='location')
 	{
 		dashboard1=val+'_table';
 		var statusFor=$("#statusFor").val();
 		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").show();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").hide();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus1+"&tableFor="+val+"&id="+specialty1+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
	else if(val=='nursingUnit')
 	{
 		dashboard1=val+'_table';
 		var statusFor=$("#statusFor").val();
 		$(".statusSpecialtyPanel").hide();
		$(".statusTestTypePanel").hide();
		$(".statusLocationPanel").hide();
		$(".statusTestNamePanel").hide();
		$(".statusDoctorPanel").hide();
		$(".statusNursingUnitPanel").show();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus1+"&tableFor="+val+"&id="+location1+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
  	 
 	}
 	else if(val=='specialtyProductivity')
 	{
 		dashboard2=val+'_table';
 		var statusFor=$("#statusForProductivity").val();
 		$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").show();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
 	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus2+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
 	}
 	else if(val=='doctorProductivity')
 	{
 		dashboard2=val+'_table';
 		var statusFor=$("#statusForProductivity").val();
 		$(".doctorProductivityPanel").show();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus2+"&tableFor="+val+"&id="+specialty2+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
 	else if(val=='testTypeProductivity')
 	{
 		dashboard2=val+'_table';
 		var statusFor=$("#statusForProductivity").val();
 		$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").show();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
 	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus2+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
 	}
 	else if(val=='testNameProductivity')
 	{
 		dashboard2=val+'_table';
 		var statusFor=$("#statusForProductivity").val();
 		$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").show();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").hide();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus2+"&tableFor="+val+"&id="+testType2+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
 	else if(val=='locationProductivity')
 	{
 		dashboard2=val+'_table';
 		var statusFor=$("#statusForProductivity").val();
 		$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").show();
		$(".nursingUnitProductivityPanel").hide();
 	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus2+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
 	}
 	else if(val=='nursingUnitProductivity')
 	{
 		dashboard2=val+'_table';
 		var statusFor=$("#statusForProductivity").val();
 		$(".doctorProductivityPanel").hide();
		$(".specialtyProductivityPanel").hide();
		$(".testTypeProductivityPanel").hide();
		$(".testNameProductivityPanel").hide();
		$(".locationProductivityPanel").hide();
		$(".nursingUnitProductivityPanel").show();
	  	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+recordStatus2+"&tableFor="+val+"&id="+location2+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	  	 
 	}
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
 
   