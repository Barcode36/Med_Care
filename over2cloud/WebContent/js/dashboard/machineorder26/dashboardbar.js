var title = '';
var title1 = '';
var dataFor1='';
var status1='';
var color1='';
var desc = '';
var statusJsonData = [];
var statusJsonData1 = [];
var levelJsonData = [];
var categJsonData = [];
var measure1 = 'Location';
var dataForDept='dept';
var deptName='';
var filterDeptName='';
var dataDeptId='';
var filterDeptId='';
var filterFlag='M';
var categoryField = '';
var isMaxDept=false;
var escalated=false;
var UGFlag=false;
var ICUFlag=false;
var IPDFlag=false;
var AllFlag=false;
var machineName='';
var totalData='';
 var locationId='';
 var locationName='';
 var timeId='';
 var timeName='';
function getEscalatedMain(val){
	if(val)
	{
	getEscalated(true);
	}
	else
	{
	getEscalated(false);
	
	}
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
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&dataFlag=" + flag + "&feedStatus=" + status + "&machineId=" + id +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
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


function handleClickLevel(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
	var deptId=$("#deptname1").val();
	var machineName=$("#machinename1").val();
	 	if (dataFor == 'Resolve')
	 	dataFor = 'Resolved';
	 	else if(dataFor=='Park')
	 	dataFor='Snooze';
	getDataForLevel(dataContx.level, dataFor, deptId, 'level', machineName);
}

function getDataForLevel(level, status, deptId, flag, machineName)
{
	 
	//var dataFor = $("#" + dataForId).val();
  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&dataFlag=" + flag + "&dashFor=" + deptId +"&feedId=" + level +"&feedStatus=" + status + "&machineId=" + machineName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
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


function handleClickLocationLevel(event)
{
	//alert(machineName);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
	var deptId=$("#deptname1").val();
	var machineName=$("#machinename1").val();
	  
	getDataForLocationLevel(dataContx.floorId, dataFor, deptId, 'location', machineName);
}

function getDataForLocationLevel(location, level, dataForId, flag, mName)
{
	 
	//var dataFor = $("#" + dataForId).val();
  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + level + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&location=" + location + "&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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


function handleClickStatusNursingUnitChart(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	var title=dataFor;
 	var color=graph.fillColors;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	else if(dataFor=='All')
 	dataFor='total';
 	 
	locationId=dataContx.deptId;
	locationName=dataContx.dept;
	//getDataForNursingUnitStatus(dataContx.floorId, dataFor, dept, 'nursingStatus', mName);
	showNursingStackedBar('nursingChart', 'N', '', dataFor, title, color,dataContx.deptId,dataContx.dept);
}
function handleClickStatusTimeNursingUnitChart(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	var title=dataFor;
 	var color=graph.fillColors;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	else if(dataFor=='All')
 	dataFor='total';
 	 
	locationId=dataContx.deptId;
	locationName=dataContx.dept;
	//getDataForNursingUnitStatus(dataContx.floorId, dataFor, dept, 'nursingStatus', mName);
	showTimeFloorNursingUnitStackedBar('nursingChart', '', '', dataFor, title, color,dataContx.deptId,dataContx.dept,timeId,timeName);
}
 
function handleClickOrderNursingUnitChart(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	var title=dataFor;
 	var color=graph.fillColors;
  	  if(dataFor=='All')
 	dataFor='total';
 	 locationId=dataContx.deptId;
 	locationName=dataContx.dept;
	//getDataForNursingUnitStatus(dataContx.floorId, dataFor, dept, 'nursingStatus', mName);
 	showNursingStackedBarOrderType('nursingChart', 'N', '', dataFor, title, color,dataContx.deptId,dataContx.dept);
}

function handleClickStatusTimeFloorChart(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	var title=dataFor;
 	title1=dataFor;
  	var color=graph.fillColors;
  	color1==color;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	else if(dataFor=='All')
 	dataFor='total';
 	 
	timeId=dataContx.timeId;
	timeName=dataContx.time;
	dataFor1=dataFor;
	//getDataForNursingUnitStatus(dataContx.floorId, dataFor, dept, 'nursingStatus', mName);
	showTimeFloorStackedBar('nursingChart', 'N', '', dataFor, title, color,dataContx.timeId,dataContx.time);
}


function handleClickStatusNursingUnitTime(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	var dept=$("#deptname2").val();
	var mName=$("#machinename2").val();
	getDataForStatusNursingUnitTime(dataContx.deptId,timeId,locationId, dataFor, dept, 'nursingUnitTime', mName);
}

function getDataForStatusNursingUnitTime(nursingId,timeId,location, status, dataForId, flag, mName)
{
	//alert(nursingId+"    "+location+"   "+timeId+"   "+ status+"   "+ dataForId+"   "+ flag+"   "+mName); 
	
	 	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&time=" + timeId +"&nursingId=" + nursingId +"&location=" + location +"&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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

function handleClickStatusFloorTime(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	var dept=$("#deptname2").val();
	var mName=$("#machinename2").val();
	getDataForStatusFloorTime(dataContx.deptId,timeId, dataFor, dept, 'floorTime', mName);
}

function getDataForStatusFloorTime(location,timeId, status, dataForId, flag, mName)
{
	//alert(location+"   "+timeId+"   "+ status+"   "+ dataForId+"   "+ flag+"   "+mName); 
	
	 	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&time=" + timeId +"&location=" + location +"&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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

function handleClickStatusTime(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	var dept=$("#deptname2").val();
	var mName=$("#machinename2").val();
	getDataForStatusTime(dataContx.timeId, dataFor, dept, 'statusTime', mName);
}
 


function getDataForStatusTime(timeId, status, dataForId, flag, mName)
{
	 
	 	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&time=" + timeId + "&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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

function handleClickStatusNursingUnit(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	var dept=$("#deptname2").val();
	var mName=$("#machinename2").val();
	getDataForNursingUnitStatus(dataContx.deptId, dataFor, dept, 'nursingStatus', mName);
}

function getDataForNursingUnitStatus(nursingUnit, status, dataForId, flag, mName)
{
	 
	 	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&location=" + nursingUnit + "&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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

function handleClickStatusLocation(event)
{
	 var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	if(dataFor == 'Resolve')
 	dataFor = 'Resolved';
 	else if(dataFor=='Park')
 	dataFor='Snooze';
 	var dept=$("#deptname2").val();
	var mName=$("#machinename2").val();
	getDataForLocationStatus(dataContx.deptId, dataFor, dept, 'floorStatus', mName);
}

function getDataForLocationStatus(id, status, dataForId, flag, mName)
{
	  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	 	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&location=" + id + "&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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


function handleClickOrderNursingUnit(event)
{
	//alert(machineName);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	var dept=$("#deptname2").val();
	var machineName=$("#machinename2").val();  
	getDataForNursingUnitOrder(dataContx.deptId, dataFor, dept, 'nursingOrder', machineName);
}

function getDataForNursingUnitOrder(nursingUnit, priority, dataForId, flag, machineName)
{
	 
	//var dataFor = $("#" + dataForId).val();
  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&feedStatus=" + priority + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&location=" + nursingUnit + "&machineId=" + machineName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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

function handleClickOrderLocation(event)
{
	//alert(machineName);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
 	var dept=$("#deptname2").val();
	var machineName=$("#machinename2").val();  
	getDataForLocationOrder(dataContx.deptId, dataFor, dept, 'floorOrder', machineName);
}

function getDataForLocationOrder(id, priority, dataForId, flag, machineName)
{
	 
	//var dataFor = $("#" + dataForId).val();
  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + priority + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&location=" + id + "&machineId=" + machineName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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

function handleClickLocationStatus(event)
{
	//alert(machineName);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	var dataFor = graph.title;
	var deptId=$("#deptname1").val();
	  
	getDataForLocationStatus(dataContx.floorId, dataFor, deptId, 'locationStatus', machineName);
}

function getDataForLocationStatus(location, status, dataForId, flag, mName)
{
	 
	//var dataFor = $("#" + dataForId).val();
  	$("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + dataForId + "&location=" + location + "&machineId=" + mName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val(),
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



function showTimeFloorNursingUnitStackedBar(divId, filterFlag, filterDeptId, status, title, color,locationId,locationName,timeId,timeName)
{
	$("#backbttn").attr('onclick','showTimeFloorStackedBar("nursingChart", "N", "", dataFor1, title1, color1,timeId,timeName);').css('display','block');
	$(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	$(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").show();
 	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
 	 	status='total';
	maxDivId1 = '11thStackedBar';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	 	var machineName=$("#machinename2").val();
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4NursingStatusCounters.action?filterFlag=NT&filterDeptId="+deptId+"&machineName="+machineName+"&location="+locationId+"&time="+timeId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	 	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	  	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 statusJsonData = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	sampleData = data;
 	drawAllStatusChartForNursingUnitTime(divId, sampleData, status, title, color, "dept",locationName,timeName,total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	if(status!='total')
	{
	var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze +  statusJsonData[i].Resolved;
	
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Resolved')
	 {
	statusJsonData[x].ResolvedPer = Math.round(statusJsonData[x].Resolved / total * 100);
	totalData=totalData+statusJsonData[x].Resolved;
	
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData[x].PendingPer = Math.round(statusJsonData[x].Pending / total * 100);
	 totalData=totalData+statusJsonData[x].Pending;
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData[x].SnoozePer = Math.round(statusJsonData[x].Snooze / total * 100);
	 totalData=totalData+statusJsonData[x].Snooze;
	 }
	  
	 
	}

	drawAllStatusChartForNursingUnitTime(divId, statusJsonData, status, title, color, "dept",locationName,timeName,totalData);
 	}
 
}// end here


function showNursingStackedBar(divId, filterFlag, filterDeptId, status, title, color,locationId,locationName)
{
	//alert(divId+"  "+ filterFlag+"   "+ filterDeptId+"    "+ status+"   "+ title+"   "+color+"     "+locationId);
	$("#backbttn").attr('onclick','showFloorStatusStackedBar("nursingChart","" ,"" ,"" , "All", "#09A1A4")').css('display','block');
	$(".nursingStatusPanel").show();
	$(".nursingOrderPanel").hide();
	$(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
 	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
 	 	maxDivId1 = '3rdStackedBar';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	 	var machineName=$("#machinename2").val();
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4NursingStatusCounters.action?filterFlag=N&filterDeptId="+deptId+"&machineName="+machineName+"&location="+locationId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	 	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	 	 	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 
	 	statusJsonData = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	sampleData = data;
 	drawAllStatusChartForNursingUnit(divId, sampleData, status, title, color, "dept",locationName,total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	if(status!='total')
	{
	var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze +  statusJsonData[i].Resolved;
	
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Resolved')
	 {
	statusJsonData[x].ResolvedPer = Math.round(statusJsonData[x].Resolved / total * 100);
	totalData=totalData+statusJsonData[x].Resolved;
	
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData[x].PendingPer = Math.round(statusJsonData[x].Pending / total * 100);
	 totalData=totalData+statusJsonData[x].Pending;
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData[x].SnoozePer = Math.round(statusJsonData[x].Snooze / total * 100);
	 totalData=totalData+statusJsonData[x].Snooze;
	 }
	  
	 
	}

	drawAllStatusChartForNursingUnit(divId, statusJsonData, status, title, color, "dept",locationName,totalData);
 	}
 
}// end here

function showTimeStatusStackedBar(divId, filterFlag, filterDeptId, status, title, color)
{
	$("#backbttn").attr('onclick','').css('display','none');

 	$(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
 	$(".floorOrderPanel").hide();
	$(".floorStatusPanel").hide();
	$(".timeStatusPanel").show();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
 
	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
	 
	if (status == '')
	{
	 	status='total';
	maxDivId1 = '9thStackedBar';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/getBarChart4DeptCountersTime.action?filterDeptId="+deptId+"&machineName="+machineName+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	 	 	
	 	 	
	 	 	
	 	 	
	 	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	  	statusJsonData = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	sampleData = data;
 	drawAllStatusChartForTime(divId, sampleData, status, title, color, "time",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	} else
	{
	var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze +  statusJsonData[i].Resolved;
	 
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Resolved')
	 {
	statusJsonData[x].ResolvedPer = Math.round(statusJsonData[x].Resolved / total * 100);
	totalData=totalData+statusJsonData[x].Resolved ;
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData[x].PendingPer = Math.round(statusJsonData[x].Pending / total * 100);
	 totalData=totalData+statusJsonData[x].Pending ;
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData[x].SnoozePer = Math.round(statusJsonData[x].Snooze / total * 100);
	 totalData=totalData+statusJsonData[x].Snooze ;
	 }
	  
	 
	}

	drawAllStatusChartForTime(divId, statusJsonData, status, title, color, "time",totalData);
 	}
 
}// end here

function drawAllStatusChartForTime(divId, data, status, title, color, categ,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets: "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	 
	  
	  AmCharts.doDoubleClick = function(event)
	  {
	  handleClickStatusTime(event);
	  };
	  AmCharts.doSingleClick = function(event)
	{
	  handleClickStatusTimeFloorChart(event);
	};
	   
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
}

function showTimeFloorStackedBar(divId, filterFlag, filterDeptId, status, title, color,timeId,timeName)
{
	$("#backbttn").attr('onclick','showTimeStatusStackedBar("nursingChart", "", "", "", "All", "#09A1A4")').css('display','block');	
	$(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	$(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").show();
	$(".nursingUnitTimeStatusPanel").hide();
 	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
 	  	maxDivId1 = '10thStackedBar';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	 	var machineName=$("#machinename2").val();
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4NursingStatusCounters.action?filterFlag=T"+"&filterDeptId="+deptId+"&machineName="+machineName+"&time="+timeId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	 	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	 	 	
	 	 	
	 	 	
	 	 	
	 	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 	 	statusJsonData = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	sampleData = data;
 	drawAllStatusChartForTimeFloor(divId, sampleData, status, title, color, "dept",timeName,total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	 if(status != 'total')
	{
	var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze +  statusJsonData[i].Resolved;
	
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Resolved')
	 {
	statusJsonData[x].ResolvedPer = Math.round(statusJsonData[x].Resolved / total * 100);
	totalData=totalData+statusJsonData[x].Resolved;
	
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData[x].PendingPer = Math.round(statusJsonData[x].Pending / total * 100);
	 totalData=totalData+statusJsonData[x].Pending;
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData[x].SnoozePer = Math.round(statusJsonData[x].Snooze / total * 100);
	 totalData=totalData+statusJsonData[x].Snooze;
	 }
	  
	 
	}

	drawAllStatusChartForTimeFloor(divId, statusJsonData, status, title, color, "dept",timeName,totalData);
 	}
 
}// end here



function drawAllStatusChartForTimeFloor(divId, data, status, title, color, categ,timeName,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets At "+timeName+" : "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	
	 
	  AmCharts.doDoubleClick = function(event)
	  	{
	  	handleClickStatusFloorTime(event);
	  	};
	  	AmCharts.doSingleClick = function(event)
	{
	  	handleClickStatusTimeNursingUnitChart(event);
	};
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler); 
}

 



function showFloorStatusStackedBar(divId, filterFlag, filterDeptId, status, title, color)
{
	
	$("#backbttn").attr('onclick','').css('display','none');
  	$(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
 	$(".floorOrderPanel").hide();
	$(".floorStatusPanel").show();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
  	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
	 
	if (status == '')
	{
	 	status='total';
	maxDivId1 = '7thStackedBar';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	/*if(deptId!='-1')
	{
	var str=$("#deptname2").find('option:selected').text();
	title=str+" Total ";
	}*/
	var machineName=$("#machinename2").val();
	
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4NursingStatusCounters.action?data4=table&filterFlag=F"+"&filterDeptId="+deptId+"&machineName="+machineName+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	 	 	
	 	 	
	 	 	
	 	 	
	 	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 	 	statusJsonData = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	sampleData = data;
 	drawAllStatusChartForLocation(divId, sampleData, status, title, color, "dept",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	} else
	{
	var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze +  statusJsonData[i].Resolved;
	 
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Resolved')
	 {
	statusJsonData[x].ResolvedPer = Math.round(statusJsonData[x].Resolved / total * 100);
	totalData=totalData+statusJsonData[x].Resolved ;
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData[x].PendingPer = Math.round(statusJsonData[x].Pending / total * 100);
	 totalData=totalData+statusJsonData[x].Pending ;
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData[x].SnoozePer = Math.round(statusJsonData[x].Snooze / total * 100);
	 totalData=totalData+statusJsonData[x].Snooze ;
	 }
	  
	 
	}

	drawAllStatusChartForLocation(divId, statusJsonData, status, title, color, "dept",totalData);
 	}
 
}// end here

function drawAllStatusChartForLocation(divId, data, status, title, color, categ,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets: "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	 
	  
	  AmCharts.doDoubleClick = function(event)
	  {
	  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick = function(event)
	{
	  handleClickStatusNursingUnitChart(event);
	};
	   
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
}

 


function drawAllStatusChartForNursingUnit(divId, data, status, title, color, categ,locationName,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets For "+locationName+" : "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	
	 
	  	AmCharts.doDoubleClick = function(event)
	  	{
	  	handleClickStatusNursingUnit(event);
	  	};
	  	AmCharts.doSingleClick = function(event)
	{
	 alert("No any data on single click.....!!!");
	};
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
}

 
function drawAllStatusChartForNursingUnitTime(divId, data, status, title, color, categ,locationName,timeName,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets For "+locationName+" At "+timeName+" : "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	 	AmCharts.doDoubleClick = function(event)
	  	{
	  	handleClickStatusNursingUnitTime(event);
	  	}; 
	  	AmCharts.doSingleClick = function(event)
	{
	 alert("No any data on single click.....!!!");
	};
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
}




function showNursingStackedBarOrderType(divId, filterFlag, filterDeptId, status, title, color,locationId,locationName)
{
	$("#backbttn").attr('onclick','showFloorOrderTypeStackedBar("nursingChart", "", "", "", "All", "#09A1A4")').css('display','block');
 	$(".nursingOrderPanel").show();
	$(".nursingStatusPanel").hide();
	$(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
 
	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
	 	maxDivId1 = '4thStackedBar';
	 	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4NursingOrderTypeCounters.action?filterFlag=N"+"&filterDeptId="+deptId+"&machineName="+machineName+"&location="+locationId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Stat + data[i].Routine + data[i].Urgent;
	data[i].total=data[i].Stat + data[i].Routine + data[i].Urgent;
	}
	 	statusJsonData = data;
	
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
 	sampleData = data;
 	drawAllOrderTypeChartForNursingUnit(divId, sampleData, status, title, color, "dept",locationName,total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	if(status!='total')
	{
	 var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Routine + statusJsonData[i].Urgent +  statusJsonData[i].Stat;
	
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Routine')
	 {
	statusJsonData[x].RoutinePer = Math.round(statusJsonData[x].Routine / total * 100);
	totalData=totalData+statusJsonData[x].Routine;
	
	 }	
	 else if(status=='Urgent')
	 {
	 statusJsonData[x].UrgentPer = Math.round(statusJsonData[x].Urgent/ total * 100);
	 totalData=totalData+statusJsonData[x].Urgent;
	 }
	 else if(status=='Stat')
	 {
	 statusJsonData[x].StatPer = Math.round(statusJsonData[x].Stat/ total * 100);
	 totalData=totalData+statusJsonData[x].Stat;
	 }
	  
	 
	}

	drawAllOrderTypeChartForNursingUnit(divId, statusJsonData, status, title, color, "dept",locationName,totalData);
	 	}
	
}// end here



function showFloorOrderTypeStackedBar(divId, filterFlag, filterDeptId, status, title, color)
{
	$("#backbttn").attr('onclick','').css('display','none');
 	$(".nursingOrderPanel").hide();
	$(".nursingStatusPanel").hide();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").show();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
 
	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
	 
	if (status == '')
	{
	maxDivId1 = '8thStackedBar';
	 status='total';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	 	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4NursingOrderTypeCounters.action?filterFlag=F"+"&filterDeptId="+deptId+"&machineName="+machineName+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&escFlag="+$("#esc").val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Stat + data[i].Routine + data[i].Urgent;
	data[i].total=data[i].Stat + data[i].Routine + data[i].Urgent;
	}
	 	statusJsonData = data;
	
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
 	sampleData = data;
 	drawAllOrderTypeChartForLocation(divId, sampleData, status, title, color, "dept",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	}  
	
	 else
	{
	 var totalData=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Routine + statusJsonData[i].Urgent +  statusJsonData[i].Stat;
	
	}
	 
	for (var x in statusJsonData) 
	{
	 if(status=='Routine')
	 {
	statusJsonData[x].RoutinePer = Math.round(statusJsonData[x].Routine / total * 100);
	totalData=totalData+statusJsonData[x].Routine;
	
	 }	
	 else if(status=='Urgent')
	 {
	 statusJsonData[x].UrgentPer = Math.round(statusJsonData[x].Urgent/ total * 100);
	 totalData=totalData+statusJsonData[x].Urgent;
	 }
	 else if(status=='Stat')
	 {
	 statusJsonData[x].StatPer = Math.round(statusJsonData[x].Stat/ total * 100);
	 totalData=totalData+statusJsonData[x].Stat;
	 }
	  
	 
	}

	drawAllOrderTypeChartForLocation(divId, statusJsonData, status, title, color, "dept",totalData);
	 	}
	
}// end here


function drawAllOrderTypeChartForLocation(divId, data, status, title, color, categ,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Routine')
	 {
	 if(data[index].Routine>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Urgent')
	 {
	 if(data[index].Urgent>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Stat')
	 {
	 if(data[index].Stat>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " OrderType Tickets: "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	
	  AmCharts.doDoubleClick = function(event)
	{
	handleClickOrderNursingUnit(event);
	};
	 
	  AmCharts.doSingleClick = function(event)
	{
	  handleClickOrderNursingUnitChart(event);
	};
 
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
}
 




function drawAllOrderTypeChartForNursingUnit(divId, data, status, title, color, categ,locationName,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Routine')
	 {
	 if(data[index].Routine>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Urgent')
	 {
	 if(data[index].Urgent>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Stat')
	 {
	 if(data[index].Stat>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  //console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " OrderType Tickets For "+locationName+" : "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 30

	}

	],
	"depth3D" : 10,
	"angle" : 20,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	// it's less than that - it's a
	
	  AmCharts.doDoubleClick = function(event)
	{
	handleClickOrderNursingUnit(event);
	};
	 
  
	AmCharts.doSingleClick = function(event)
	{
	 alert("No any data on single click.....!!!");
	};
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
	AmCharts.doDoubleClick(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
}
 





function StatckedChartStatus(divId, filterFlag, filterDeptId, status, title, color)
{
	$(".statusPanel1").show();
	 	title1 = title;
	color1 = color;
	status1=status;
	var total = 0;
	 
	if (status == '')
	{
	status='total';
	maxDivId3 = '1stStackedBar';
	var sampleData = null;
	var url1='';
	var deptId=$("#deptname").val();
	 $('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	/*if(deptId!='-1')
	{
	var str=$("#deptname").find('option:selected').text();
	title=str+" Total ";
	}*/
	 
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4DeptCounters.action?filterFlag=H&filterDeptId="+deptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	 	 	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	 	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 
	 	statusJsonData1 = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	sampleData = data;
 	 	drawStatusChart(divId, sampleData, status, title, color, "dept",total);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	} else
	{
	 var totalData=0;
	for (var i = 0; i <= statusJsonData1.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData1[i].Pending + statusJsonData1[i].Snooze +  statusJsonData1[i].Resolved;
	
	}
	 
	for (var x in statusJsonData1) 
	{
	 if(status=='Resolved')
	 {
	statusJsonData1[x].ResolvedPer = Math.round(statusJsonData1[x].Resolved / total * 100);
	totalData=totalData+statusJsonData1[x].Resolved;
	
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData1[x].PendingPer = Math.round(statusJsonData1[x].Pending / total * 100);
	totalData=totalData+statusJsonData1[x].Pending;
	
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData1[x].SnoozePer = Math.round(statusJsonData1[x].Snooze / total * 100);
	totalData=totalData+statusJsonData1[x].Snooze;
	
	 }
	  
	 
	}

	 	 	drawStatusChart(divId, statusJsonData1, status, title, color, "dept",totalData);
 	}

 
}// end here


function drawStatusChart(divId, data, status, title, color, categ,totalData)
{
	
	var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	 
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	  });
	  
	  var chart='';
	  console.log(status);
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets: "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 	"labelOffset":10,
	 	"labelText" : "[["+status+"Per]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 50

	}

	],
	"depth3D" : 20,
	"angle" : 30,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	  
	  
	  AmCharts.doDoubleClick = function(event)
	{
	handleClickStatus(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
}






function showLeveStackedBar(divId, filterFlag, filterDeptId, status, title, color)
{
 	$(".levelPanel").show();
 	$(".levelPanelLoc").hide();
 	 var total = 0;
	
	status2 = status;
	title2 = title;
	color2 = color;
	maxDivId2 = '2ndStackedBar';
	var deptId = $("#deptname1").val();
	/*if(deptId!='-1')
	{
	var str=$("#deptname1").find('option:selected').text();
	title=str+" Total ";
	}*/
	var machineName = $("#machinename1").val();
	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	
	var url1 = '';
	if (status == '')
	{
	status='total';
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/jsonChartData4LevelCounters.action?filterFlag=H&machineName="+machineName+"&filterDeptId="+deptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	$.ajax({
	type : "post",
	url : url1,
	async : false,
	type : "json",
	success : function(data)
	{
	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Snooze + data[i].Resolved;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Resolved;
	}
	 	 	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 	levelJsonData = data;
	 	if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
	 	else
	{
	 	 	drawLevelChart(divId, data, status, title, color, "level",total);
	
	}
	 	},
	error : function()
	{
	alert("error");
	}
	});
	}
	else
	{
	var totalData=0;
	for (var i = 0; i <= levelJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + levelJsonData[i].Pending + levelJsonData[i].Snooze +  levelJsonData[i].Resolved;
	
	}
	 
	for (var x in levelJsonData) 
	{
	 if(status=='Resolved')
	 {
	 levelJsonData[x].ResolvedPer = Math.round(levelJsonData[x].Resolved / total * 100);
	totalData=totalData+levelJsonData[x].Resolved;
	 	 }	
	 else if(status=='Pending')
	 {
	 levelJsonData[x].PendingPer = Math.round(levelJsonData[x].Pending / total * 100);
	totalData=totalData+levelJsonData[x].Pending;
	
	 }
	 else if(status=='Snooze')
	 {
	 levelJsonData[x].SnoozePer = Math.round(levelJsonData[x].Snooze / total * 100);
	totalData=totalData+levelJsonData[x].Snooze;
	
	 }
	  
	 
	}
	drawLevelChart(divId, levelJsonData, status, title, color, "level",totalData);
	
	}

}

function drawLevelChart(divId, data, status, title, color, categ,totalData)
{
	 var jsonObj = [];
	
	  $(data).each(function(index)
	  {
	 if(status=='Resolved')
	 {
	 if(data[index].Resolved>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }	
	 else if(status=='Pending')
	 {
	 if(data[index].Pending>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Snooze')
	 {
	 if(data[index].Snooze>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='total')
	 {
	 if(data[index].total>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 
	  });
	 //console.log(jsonObj);
	  var chart='';
	  if(jsonObj.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" : title + " Tickets: "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : jsonObj,
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
	"valueField" : "" + status,
	 "labelOffset":10,
	"labelText" : "[["+status+"Per]]%",
	
	"title" : "" + title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 50

	}

	],
	"depth3D" : 20,
	"angle" : 30,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + categ,
	"categoryAxis" : {
	"gridPosition" : "start",
	"gridAlpha" : 0,
	"tickPosition" : "start",
	"tickLength" : 10,
	"autoWrap" : true
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
	   
	    AmCharts.doDoubleClick= function(event)
	{
	    	 handleClickLevel(event);
	};
	  if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.doDoubleClick);
	}
	  	 
} 

 


function getData(id, status, flag, dataForId, level)
{
	alert("ID : "+id+" Status : "+status+" Flag : "+flag);
 	var deptId=$("#deptname1").val();
 	var machineName=$("#machinename1").val();
 	var deptId1=$("#deptname2").val();
 	var machineName1=$("#machinename2").val();
	
	 $("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	 var url1='';
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(flag=='level')
	url1 = "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&dataFlag=" + flag + "&dashFor=" + deptId +"&feedId=" + id +"&feedStatus=" + status + "&machineId=" + machineName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='location')
	 	url1 = "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&dataFlag=" + flag + "&dashFor=" + deptId +"&location=" + id +"&feedStatus=" + status + "&machineId=" + machineName +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
 	else if(flag=='status')
	url1 = "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&dataFlag=" + flag + "&feedStatus=" + status + "&machineId=" + id +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='nursingStatus')
	 	url1 ="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&location=" + id + "&machineId=" + machineName1 + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='nursingOrder')
	 	url1= "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&machineId=" + machineName1 +"&location=" + id + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='floorStatus')
	url1 ="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&machineId=" + machineName1 + "&location=" + id + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='floorOrder')
	url1= "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&machineId=" + machineName1 + "&location=" + id + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='statusTime')
	 url1= "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?data4=table&feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&machineId=" + machineName1 + "&time=" + id + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='floorTime')
	 	url1= "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&machineId=" + machineName1 + "&time=" + timeId +"&location=" + id +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	else if(flag=='nursingUnitTime')
	  url1= "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/beforeFeedAction.action?feedStatus=" + status + "&dataFlag=" + flag + "&dashFor=" + deptId1 + "&machineId=" + machineName1 + "&time=" + timeId +"&nursingId=" + id +"&location=" + locationId +"&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+"&escFlag="+$("#esc").val();
	
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
 


function beforeMaximise(divBlock, height, width, blockHeading)
{
 	if (maxDivId1 == '1stTable')
	{
	maximizeDiv4Analytics(height, width, blockHeading);
	showData('jqxChart', 'maximizeDataDiv','');
	} 
	else if (maxDivId1 == '1stStackedBar')
	{
	 	maximizeDiv4Analytics(height, width, blockHeading);
	StatckedChartStatus('maximizeDataDiv', '', '', status1, title1, color1);
	 
	}
	 
}
 
 function maximizeDiv4Analytics(height, width, blockHeading)
{
	$("#maxmizeViewDialog").dialog({
	title : blockHeading,
	height : height,
	width : width,
	dialogClass : 'transparent'
	});
	$("#maxmizeViewDialog").dialog('open');
}
 

 
 
 function showData(id,flag,val)
 {
	 
 	 
 	$("#"+id).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	
 	if(id=='jqxChart')
 	{
 	$(".statusPanel1").hide();
 	 	maxDivId3='1stTable';
 	var url1='';
 	var deptId=$("#deptname").val();
 	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=Status"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag=H"+"&filterDeptId="+deptId;
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
  } else if(id=='mytest' && flag=='statusLevel')
 	{
 	 $(".levelPanel").show();
 	 $(".levelPanelLoc").hide();
 	
  	 maxDivId2='2ndTable';
 	var url1='';
	var deptId=$("#deptname1").val();
	var machineName=$("#machinename1").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=Level&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&filterDeptId="+deptId;
	
 	 
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
  else if(id=='mytest' && flag=='locationLevel')
	{
	 $(".levelPanel").hide();
	 $(".levelPanelLoc").show();
	 
	 maxDivId2='6thTable';
	var url1='';
	var deptId=$("#deptname1").val();
	var machineName=$("#machinename1").val();
	  	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=locationLevel&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&filterDeptId="+deptId;
	  
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
  else if(id=='nursingChart' && val=='status')
	{
	  $(".nursingStatusPanel").show();
	$(".nursingOrderPanel").hide();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
	 
	
	maxDivId1='3rdTable';
	var url1='';
	var deptId=$("#deptname2").val();
	var mName=$("#machinename2").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=NursingStatus"+"&machineName="+mName+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag=N"+"&filterDeptId="+deptId+"&escFlag="+$("#esc").val()+"&location="+locationId;
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
 	
  else if(id=='nursingChart' && val=='statusFloor')
	{
	  $(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	$(".floorOrderPanel").hide();
	$(".floorStatusPanel").show();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
	 
	maxDivId1='7thTable';
	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=statusFloor"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&filterFlag=H"+"&filterDeptId="+deptId+"&escFlag="+$("#esc").val();
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
	
 	
  else if(id=='nursingChart' && val=='order')
	{
	  $(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").show();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
	 
	 maxDivId1='4thTable';
	 var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=NursingOrder"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag=H"+"&filterDeptId="+deptId+"&machineName="+machineName+"&escFlag="+$("#esc").val()+"&location="+locationId;
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
 	
  else if(id=='nursingChart' && val=='orderFloor')
	{
	  $(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").show();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
	 
	
	 maxDivId1='8thTable';
	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=orderFloor"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&filterFlag=H"+"&filterDeptId="+deptId+"&escFlag="+$("#esc").val();
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
  else if(id=='nursingChart' && val=='statusTime')
	{
	  $(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").show();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").hide();
	 
	
	maxDivId1='9thTable';
	 	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=statusTime"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&filterFlag=H"+"&filterDeptId="+deptId+"&escFlag="+$("#esc").val();
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
 	 else if(id=='nursingChart' && val=='floorTime')
	{
	  $(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").show();
	$(".nursingUnitTimeStatusPanel").hide();
	maxDivId1='10thTable';
	 	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=floorTime"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&time="+timeId+"&filterDeptId="+deptId+"&escFlag="+$("#esc").val();
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
 	else if(id=='nursingChart' && val=='nursingUnitTime')
	{
	  $(".nursingStatusPanel").hide();
	$(".nursingOrderPanel").hide();
	 $(".floorStatusPanel").hide();
	$(".floorOrderPanel").hide();
	$(".timeStatusPanel").hide();
	$(".floorTimeStatusPanel").hide();
	$(".nursingUnitTimeStatusPanel").show();
	maxDivId1='11thTable';
	 	var url1='';
	var deptId=$("#deptname2").val();
	var machineName=$("#machinename2").val();
	 	url1="view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=nursingUnitTime"+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&machineName="+machineName+"&location="+locationId+"&time="+timeId+"&filterDeptId="+deptId+"&escFlag="+$("#esc").val();
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

 }
 