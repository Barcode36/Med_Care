var title = '';
var title1 = '';
var desc = '';
var statusJsonData = [];
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
function getChartTitle()
{
	if ($.trim($("#sdate").val()) != "")
	{
	desc = 'Data From ' + $("#sdate").val() + ' TO ' + $("#edate").val();
	} else
	{
	desc = 'Data From ' + $("#hfromDate").val() + ' TO ' + $("#htoDate").val();
	}
	var dept = $("#deptname").val();
	if (dept == "-1")
	{
	title = 'All Department ';

	} else if (dept != "-1")
	{
	title = $("#deptname").find(":selected").text();

	}
	return title;
}

function getChartTitle2()
{

	if ($.trim($("#sdate").val()) != "")
	{
	desc = 'Data From ' + $("#sdate").val() + ' TO ' + $("#edate").val();
	} else
	{
	desc = 'Data From ' + $("#hfromDate").val() + ' TO ' + $("#htoDate").val();
	}

	var dept = $("#deptname1").val();
	if (dept == "-1")
	{
	title1 = 'All Department ';

	} else if (dept != "-1")
	{
	title1 = $("#deptname").find(":selected").text();

	}
	return title1;
}

// category pie

function StatckedChartStatus(divId, filterFlag, filterDeptId, status, title, color)
{
	$(".statusPanel1").show();
	$(".statusPanelPie").hide();
	title1 = title;
	color1 = color;
	if (status == '')
	{
	if(status1=='')
	{
	status='total';
	status1 = status;
	}	
	else
	{
	status=status1;
	}	
	maxDivId1 = '1stStackedBar';
	var sampleData = null;
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action?filterFlag=" + filterFlag + "&filterDeptId=" + filterDeptId + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
	async : false,
	type : "json",
	success : function(data)
	{
	
	var total = 0;
	// alert(data.length);
	// //console.log(data);
	for (var i = 0; i <= data.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + data[i].Pending + data[i].Snooze + data[i].Ignore + data[i].Resolved + data[i].Reassigned + data[i].Reopened;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Ignore + data[i].Resolved + data[i].Reassigned + data[i].Reopened;
	}
	
	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	//console.log(total);
	
	for (var x in data) 
	{
	 if(status=='Resolved')
	 {
	 data[x].ResolvedPer = Math.round(data[x].Resolved / total * 100);
	
	 }	
	 else if(status=='Pending')
	 {
	 data[x].PendingPer = Math.round(data[x].Pending / total * 100);
	 }
	 else if(status=='Snooze')
	 {
	 data[x].SnoozePer = Math.round(data[x].Snooze / total * 100);
	 }
	 else if(status=='Reassigned')
	 {
	 data[x].ReassignedPer = Math.round(data[x].Reassigned / total * 100);
	 }
	 else if(status=='Reopened')
	 {
	 data[x].ReopenedPer = Math.round(data[x].Reopened / total * 100);
	 }
	 else if(status=='Ignore')
	 {
	 data[x].IgnorePer = Math.round(data[x].Ignore / total * 100);
	 }
	 
	}
	statusJsonData = data;
//console.log(data);
	// alert(total);
	if (total == 0 || isNaN(total))
	{

	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} else
	{
	sampleData = data;
	title=title+" Tickets For All Department";
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
	status1 = status;
	 
	// alert(data.length);
	// //console.log(data);
	var total = 0;
	var count=0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze + statusJsonData[i].Ignore + statusJsonData[i].Resolved + statusJsonData[i].Reassigned + statusJsonData[i].Reopened;
	
	}
	//alert(status);
	
	for (var x in statusJsonData) 
	{
	 if(status=='Resolved')
	 {
	 statusJsonData[x].ResolvedPer = Math.round(statusJsonData[x].Resolved / total * 100);
	 count=count+statusJsonData[x].Resolved;
	 }	
	 else if(status=='Pending')
	 {
	 statusJsonData[x].PendingPer = Math.round(statusJsonData[x].Pending / total * 100);
	 count=count+statusJsonData[x].Pending;
	 }
	 else if(status=='Snooze')
	 {
	 statusJsonData[x].SnoozePer = Math.round(statusJsonData[x].Snooze / total * 100);
	 count=count+statusJsonData[x].Snooze;
	 }
	 else if(status=='Reassigned')
	 {
	 statusJsonData[x].ReassignedPer = Math.round(statusJsonData[x].Reassigned / total * 100);
	 count=count+statusJsonData[x].Reassigned;
	 }
	 else if(status=='Reopened')
	 {
	 statusJsonData[x].ReopenedPer = Math.round(statusJsonData[x].Reopened / total * 100);
	 count=count+statusJsonData[x].Reopened;
	 }
	 else if(status=='Ignore')
	 {
	 statusJsonData[x].IgnorePer = Math.round(statusJsonData[x].Ignore / total * 100);
	 count=count+statusJsonData[x].Ignore;
	 }
	
	}
	
	if(status=='total')
	 {
	count=total;
	 }
	title=title+" Tickets For All Department";
	drawStatusChart(divId, statusJsonData, status, title, color, "dept",count);
	
	//drawStatusChart(divId, statusJsonData, status, title, color, "dept");
	}

	// alert('from : '+ $('#sdate').val()+' to: '+$('#edate').val());



}// end here

function drawStatusChart(divId, data, status, title, color, categ,total)
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
	 else if(status=='Reassigned')
	 {
	 if(data[index].Reassigned>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Reopened')
	 {
	 if(data[index].Reopened>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Ignore')
	 {
	 if(data[index].Ignore>0)
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
	"text" : title+": "+total,
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
	"labelText" : "[["+status+"Per]]%",
	"labelOffset":10,
	"title" : "" + title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 40

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
	  else
	  {
	  $('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	  }	  
	if(chart!=""){
	chart.write(divId);	
	}
	
	/*chart.addListener("clickGraphItem", handleClickData);*/
	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; 
	AmCharts.doSingleClick111 = function(event)
	{
	handleClickData(event);
	}
	AmCharts.doDoubleClick111 = function(event)
	{
	handleClickStatus(event);
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
	AmCharts.doDoubleClick111(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick111(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	}
	// add handler to the chart
	if(chart!=""){
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
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
	} else if (dataFor == 'Reassigned')
	{
	dataFor = 'Re-assigned';
	} else if (dataFor == 'Reopened')
	{
	dataFor = 'Re-opened';
	}
	getData(dataContx.deptId, dataFor, 'SC', 'dataFor', '');
}

//For Single click
function handleClickData(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	var divId='jqxChart';
	////console.log(dataContx);
	filterDeptId=dataContx.deptId;
	filterDeptName=dataContx.dept;
	if(isMaxDept)
	{
	divId='maximizeDataDiv';
	}	
	if(dataForDept=="dept")
	{
	dataForDept='subdept';
	filterFlag='H';
	StatckedChartStatus(divId, 'H', dataContx.deptId, '', title1, color1, 'column');
	dataDeptId=dataContx.deptId;
	deptName=dataContx.dept;
	}
	else if(dataForDept=="subdept")
	{
	dataForDept='subcatg';
	filterFlag='C';
	StatckedChartStatus(divId, 'C', dataContx.deptId, '', title1, color1, 'column');
	}
	if(dataForDept=='subdept' || dataForDept=='subcatg')
	{
	if($("#dataFor").val()=="H" && dataForDept=='subdept')
	{
	 $("#backBtnDept").attr('onclick','').css('display','none');	
	}
	else
	{
	$("#backBtnDept").attr('onclick','getBackDept()').css('display','block');	
	}	
	}
	else
	{
	$("#backBtnDept").attr('onclick','').css('display','none');
	}	
	$("#headerId").text(""+filterDeptName);
}
//click on back button
function getBackDept()
{
	
	if(dataForDept=="subdept")
	{
	dataForDept='dept';
	$("#backBtnDept").attr('onclick','').css('display','none');
	filterFlag='M';
	filterDeptId='';
	$("#headerId").text("All Department");
	StatckedChartStatus('jqxChart', 'M', '', '', title1, color1, 'column');
	}
	else if(dataForDept=="subcatg")
	{
	dataForDept='subdept';
	filterFlag='H';
	filterDeptId=dataDeptId;
	filterDeptName=deptName;
	if($("#dataFor").val()=="H" && dataForDept=='subdept')
	{
	 $("#backBtnDept").attr('onclick','').css('display','none');	
	 $("#headerId").text( $("#headerIdHidden").text());
	}
	else
	{
	$("#headerId").text(""+filterDeptName);
	}	
	StatckedChartStatus('jqxChart', 'H', dataDeptId, '', title1, color1, 'column');
	}
}




// handler for level
function handleClick1(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	var dataFor = graph.title;
	/*
	 * //console.log(event); //console.log(graph.title);
	 * //console.log(dataContx.Level); //console.log(dataContx.dept);
	 */
	if (dataFor == 'Resolve')
	{
	dataFor = 'Resolved';
	} else if (dataFor == 'Park')
	{
	dataFor = 'Snooze';
	} else if (dataFor == 'Reassigned')
	{
	dataFor = 'Re-assigned';
	} else if (dataFor == 'Reopened')
	{
	dataFor = 'Re-opened';
	}

	getData('', dataFor, 'L', 'dataFor', dataContx.Level);
}

// handler for dept
/*function handleClick(event)
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
	} else if (dataFor == 'Reassigned')
	{
	dataFor = 'Re-assigned';
	} else if (dataFor == 'Reopened')
	{
	dataFor = 'Re-opened';
	}
	getData(dataContx.deptId, dataFor, 'T', 'dataFor', 'Level1');
}*/

/*function showPieStatus(divId, status, title, description)
{
	status1 = status;
	$(".statusPanel1").hide();
	$(".statusPanelPie").show();

	maxDivId1 = '1stPendingPie';

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action?fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
	async : false,
	type : "json",
	success : function(data)
	{
	// //console.log(data);

	sampleData = data;
	var total = 0;
	if (status == 'Pending')
	{

	for (var int = 0; int < data.length; int++)
	{
	total = total + parseFloat(data[int].Pending);
	}

	} else if (status == 'Snooze')
	{

	for (var int = 0; int < data.length; int++)
	{
	total = total + parseFloat(data[int].Snooze);
	}

	} else if (status == 'Ignore')
	{

	for (var int = 0; int < data.length; int++)
	{
	total = total + parseFloat(data[int].Ignore);
	}

	} else if (status == 'Resolved')
	{

	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + parseFloat(data[i].Resolved);
	}

	} else if (status == 'Reassgined')
	{

	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + parseFloat(data[i].Reassgined);
	}

	} else if (status == 'Reopened')
	{

	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + parseFloat(data[i].Reopened);
	}

	}

	if (total == 0 || isNaN(total))
	{

	$('#' + divId).html("<center><img src='images/noPie2.png' width='160' height='160' style='opacity:.5'  /><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available For " + title + "</font></center>");
	} else
	{
	var chart = AmCharts.makeChart(divId, {
	"type" : "pie",
	"theme" : "light",
	"titles" : [ {
	"text" : "" + status + "Tickets",
	"size" : 12
	} ],
	"path" : "amcharts",
	"depth3D" : 20,
	"angle" : 30,
	"dataProvider" : data,
	"valueField" : status,

	"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[dept]]</b>: <b>[[Status]]</b> ([[percents]]%)</span>",
	"export" : {
	"enabled" : true
	}
	});

	chart.write(divId);

	}
	},
	error : function()
	{
	alert("error from jsonArray data ");
	}
	});

}// end here
*/
//for 2nd Level chart
function showLeveStackedBar(divId, filterFlag, filterDeptId, status, title, color,type)
{
	$(".levelPanel").show();
	$(".levelPanelPie").hide();
	status2 = status;
	title2 = title;
	color2 = color;
	
	maxDivId2 = '2ndStackedBar';
	var deptId = $("#deptname1").val();
	var escalated=null;
	var reopen=null;
	if( $('#escLevel').is(":checked"))
	{
	escalated='Yes';
	}	
	else
	{
	escalated='No';
	}	
	if( $('#reopenLevel').is(":checked"))
	{
	reopen='Yes';
	}	
	else
	{
	reopen='No';
	}	
	
	
	
	/*var escLevel = $("#escLevel").val();
	var deptId = $("#reopenLevel").val();*/
	var url1 = '';
	if (deptId == '-1')
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?filterFlag=" + filterFlag + "&filterDeptId=" + filterDeptId + "&fromDate=" + $('#sdate').val().trim() + "&toDate=" + $('#edate').val().trim()+ "&reopen=" +reopen+ "&escalated=" + escalated;
	title=title+" Tickets For All Department";
	} 
	else
	{
	var str=$("#deptname1").find('option:selected').text();
	title=title+" Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?filterFlag=H&filterDeptId=" + deptId + "&fromDate=" + $('#sdate').val().trim() + "&toDate=" + $('#edate').val().trim()+ "&reopen=" +reopen+ "&escalated=" + escalated;
	}
	if (status == '')
	{
	if(status=='')
	{
	status='total';
	status2 = status;
	}	
	else
	{
	status=status2;
	}	
	
	 
	//console.log(status);
	//	console.log(url1);
	$.ajax({
	type : "post",
	url : url1,
	async : false,
	type : "json",
	success : function(data)
	{
	//console.log(data);
	levelJsonData = data;
	// var total=0;
	//console.log(data);
	var total = 0;
	if (data.length == '0')
	{
	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} else
	{
	
	// alert(data.length);
	// //console.log(data);
	for (var i = 0; i <= data.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + data[i].Pending + data[i].Snooze + data[i].Ignore + data[i].Resolved + data[i].Reassigned + data[i].Reopened;
	data[i].total=data[i].Pending + data[i].Snooze + data[i].Ignore + data[i].Resolved + data[i].Reassigned + data[i].Reopened;
	}
	
	for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	//console.log(total);
	
	for (var x in data) 
	{
	 if(status=='Resolved')
	 {
	 data[x].ResolvedPer = Math.round(data[x].Resolved / total * 100);
	 total=total+data[x].Resolved;
	 }	
	 else if(status=='Pending')
	 {
	 data[x].PendingPer = Math.round(data[x].Pending / total * 100);
	 total=total+data[x].Pending;
	 }
	 else if(status=='Snooze')
	 {
	 data[x].SnoozePer = Math.round(data[x].Snooze / total * 100);
	 total=total+data[x].Snooze;
	 }
	 else if(status=='Reassigned')
	 {
	 data[x].ReassignedPer = Math.round(data[x].Reassigned / total * 100);
	 total=total+data[x].Reassigned;
	 }
	 else if(status=='Reopened')
	 {
	 data[x].ReopenedPer = Math.round(data[x].Reopened / total * 100);
	 total=total+data[x].Reopened;
	 }
	 else if(status=='Ignore')
	 {
	 data[x].IgnorePer = Math.round(data[x].Ignore / total * 100);
	 total=total+data[x].Ignore;
	 }
	 
	}
	drawLevelChart(divId, data, status, title, color, "Level",total);
	
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	} else
	{
	var total = 0;
	var count = 0;
	// alert(data.length);
	// //console.log(data);
	total = 0;
	for (var i = 0; i <= statusJsonData.length - 1; i++)
	{
	// alert(data[i].Pending);
	total = total + statusJsonData[i].Pending + statusJsonData[i].Snooze + statusJsonData[i].Ignore + statusJsonData[i].Resolved + statusJsonData[i].Reassigned + statusJsonData[i].Reopened;
	}
	/*else{
	total=0;	
	}*/
	
	for (var x in levelJsonData) 
	{
	 if(status=='Resolved')
	 {
	 count=count+levelJsonData[x].Resolved;
	 levelJsonData[x].ResolvedPer = Math.round(levelJsonData[x].Resolved / total * 100);
	 }	
	 else if(status=='Pending')
	 {
	 count=count+levelJsonData[x].Pending;
	 levelJsonData[x].PendingPer = Math.round(levelJsonData[x].Pending / total * 100);
	 }
	 else if(status=='Snooze')
	 {
	 count=count+levelJsonData[x].Snooze;
	 levelJsonData[x].SnoozePer = Math.round(levelJsonData[x].Snooze / total * 100);
	 }
	 else if(status=='Reassigned')
	 {
	 count=count+levelJsonData[x].Reassigned;
	 levelJsonData[x].ReassignedPer = Math.round(levelJsonData[x].Reassigned / total * 100);
	 }
	 else if(status=='Reopened')
	 {
	 count=count+levelJsonData[x].Reopened;
	 levelJsonData[x].ReopenedPer = Math.round(levelJsonData[x].Reopened / total * 100);
	 }
	 else if(status=='Ignore')
	 {
	 count=count+levelJsonData[x].Ignore;
	 levelJsonData[x].IgnorePer = Math.round(levelJsonData[x].Ignore / total * 100);
	 }
	
	}
	if(status=='total')
	 {
	count=total;
	 }
	drawLevelChart(divId, levelJsonData, status, title, color, "Level",count);
	
	}

}

function drawLevelChart(divId, data, status, title, color, categ,total)
{
	// //console.log(data);
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
	 else if(status=='Reassigned')
	 {
	 if(data[index].Reassigned>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Reopened')
	 {
	 if(data[index].Reopened>0)
	     {
	jsonObj.push(data[index]);
	     }
	 }
	 else if(status=='Ignore')
	 {
	 if(data[index].Ignore>0)
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
	"text" : title+": "+total,
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
	"labelText" : "[["+status+"Per]]%",
	"labelOffset":10,
	"title" : "" + title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 40

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
	  else
	  {
	  $('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	  }	  
	
	  if(chart!=""){
	chart.write(divId);	
	}
	//chart.addListener("clickGraphItem", handleClick);
	  if(chart!=""){
	chart.addListener("clickGraphItem", handleClick1);
	  }
}


function showPieLevel(divId, level, title, description)
{
	status2 = level;
	$(".levelPanel").hide();
	$(".levelPanelPie").show();
	maxDivId2 = '2ndPendingPie';

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action?fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
	type : "json",
	success : function(data)
	{
	var total = 0;
	if (level == 'Pending')
	{

	for (var int = 0; int < data.length; int++)
	{
	total = total + parseFloat(data[int].Pending);
	}

	} else if (level == 'Snooze')
	{

	for (var int = 0; int < data.length; int++)
	{
	total = total + parseFloat(data[int].Snooze);
	}

	} else if (level == 'Resolved')
	{

	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + parseFloat(data[i].Resolved);
	}

	} else if (level == 'Reassgined')
	{

	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + parseFloat(data[i].Reassgined);
	}

	} else if (level == 'Reopened')
	{

	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + parseFloat(data[i].Reopened);
	}

	}

	if (total == 0 || isNaN(total))
	{

	$('#' + divId).html("<center><img src='images/noPie2.png' width='160' height='160' style='opacity:.5'  /><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available For " + title + "</font></center>");
	} else
	{
	sampleData = data;
	var chart = AmCharts.makeChart(divId, {
	"type" : "pie",
	"theme" : "light",
	"titles" : [ {
	"text" : "" + level + "Tickets",
	"size" : 15
	} ],
	"depth3D" : 20,
	"angle" : 30,
	"path" : "amcharts",
	"dataProvider" : data,
	"valueField" : level,
	"titleField" : "Level",
	"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[Status]]</b> ([[percents]]%)</span>",
	"export" : {
	"enabled" : true
	}
	});

	chart.write(divId);

	}
	},
	error : function()
	{
	alert("error from jsonArray data ");
	}
	});
}// end here

// dashboard 3rd
// category Pie
function showPieCateg(divId)
{

	maxDivId3 = "PieCateg";
	var dept = $("#deptnameCateg").val();
	var dashFor = 'M';
	if (dept != -1)
	{
	dashFor = 'H';
	}
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action?filterFlag=" + dashFor + "&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
	type : "json",
	success : function(data)
	{
	if (data.length == '0')
	{
	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} else
	{
	var chart = AmCharts.makeChart(divId, {
	"type" : "pie",
	"theme" : "light",
	"path" : "amcharts",
	"dataProvider" : data,
	"valueField" : "Counter",
	"depth3D" : 20,
	"angle" : 30,
	"labelText" : "[[Counter]]:[[percents]]%",
	"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[Category]]</b>: <b>[[Counter]]</b>([[percents]]%)</span>",
	"export" : {
	"enabled" : true
	}
	});
	chart.write(divId);
	chart.addListener("clickSlice", handlePieClick);
	}
	},
	error : function()
	{
	alert("error from jsonArray data ");
	}
	});
}

//For Pie slice click
function handlePieClick(event)
{
	var indexFor = event.dataItem;
	var dataContx = indexFor.dataContext;
	getData(dataContx.Id, '', 'C', 'dataFor', '');
}

//For subcategory bar chart
function showBarCatg(divId)
{

	maxDivId3 = "BarCateg";
	var dept = $("#deptnameCateg").val();
	var dashFor = 'M';
	if (dept != -1)
	{
	dashFor = 'H';
	}
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action?filterFlag=" + dashFor + "&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
	type : "json",
	success : function(data)
	{
	if (data.length == '0')
	{
	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} else
	{
	var chart = AmCharts.makeChart(divId, {
	"type" : "serial",
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
	 "fillColorsField": "color",
	"lineAlpha" : 0,
	"type" : "column",
	"valueField" : "Counter",
	"labelText" : "[[value]]",
	"fixedColumnWidth" : 30
	}],
	"depth3D" : 20,
	"angle" : 30,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "Category",
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
	chart.write(divId);
	chart.addListener("clickGraphItem", handleBarClick);
	}
	},
	error : function()
	{
	alert("error from jsonArray data ");
	}
	});
}

//For subcategory bar click
function handleBarClick(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	getData(dataContx.Id, '', 'C', 'dataFor', '');
}

function StatckedChartStatusLoc12(measure)
{
	taFlag=false;
	saFlag=false;
	waFlag=false;
	faFlag=false;
	$("#backbttn").attr('onclick', '').css('display', 'none');
	$("#locationbttn").attr('onclick','StatckedChartStatusLoc12("Location")');
	$("#timebttn").attr('onclick', 'StatckedChartStatusLoc12("time")').css('opacity', '1');
	$("#staffbttn").attr('onclick', 'StatckedChartStatusLoc12("staff")').css('opacity', '1');
	measure1 = measure;
	var url1 = '';
	var dept = $("#deptnameLoc").val();
	title="All Resolved & Pending Tickets"
	if (measure == 'Location')
	{
	if (dept == "-1" && escalated == false && !reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersLocation.action?fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();

	} else if (dept != "-1" && escalated == false && !reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersLocation.action?filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (escalated && dept != "-1" && !reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersLocation.action?filterFlag=escalated&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (escalated && dept == "-1" && !reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersLocation.action?filterFlag=escalated&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (!escalated && reopen && dept != "-1")
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersLocation.action?filterFlag=reopen&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (!escalated && reopen && dept == "-1")
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersLocation.action?filterFlag=reopen&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if ((!escalated && reopen && dept == "-1") || (!escalated && reopen && dept != "-1"))
	{
	alert("Please Select One Status!!");
	}
	} else if (measure == 'time')
	{
	if (dept == "-1" && escalated == false && !reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersTime.action?fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (dept != "-1" && escalated == false && !reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersTime.action?filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (escalated && dept != "-1" && !reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersTime.action?filterFlag=escalated&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (escalated && dept == "-1" && !reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersTime.action?filterFlag=escalated&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (!escalated && reopen && dept != "-1")
	{var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersTime.action?filterFlag=reopen&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (!escalated && reopen && dept == "-1")
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersTime.action?filterFlag=reopen&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	}
	} else if (measure == 'staff')
	{
	if (dept != "-1" && !escalated && !reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersStaff.action?filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (escalated && dept != "-1" && !reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersStaff.action?filterFlag=escalated&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	}else if (escalated && dept == "-1" && !reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersStaff.action?filterFlag=escalated&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	}  
	else if (!escalated && dept != "-1" && reopen)
	{
	var str=$("#deptnameLoc").find('option:selected').text();
	title="Resolved & Pending Tickets For "+str+" Department";
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersStaff.action?filterFlag=reopen&filterDeptId=" + dept + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	} else if (!escalated && dept == "-1" && reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersStaff.action?filterFlag=reopen&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();
	}
	
	else if (dept == "-1" && !escalated && !reopen)
	{
	url1 = "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getBarChart4DeptCountersStaff.action?fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val();	
	}
	}
	if (measure == 'Location')
	{
	measure = 'floor';
	}
//console.log(url1);
//alert(url1);
	if (url1 != "")
	{
	$("#mytest").html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	$.ajax({
	type : "post",
	url : url1,
	type : "json",
	success : function(data)
	{
	// //console.log(data.log);
	var total=0;
	if (data.length == '0')
	{
	$("#mytest").html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} else
	{
	

	for (var i = 0; i <= data.length - 1; i++)
	{
	 	total = total + data[i].Pending + data[i].Resolved ;
	}

	var totalResolved=0;
	for (var i = 0; i <= data.length - 1; i++)
	{
	totalResolved = totalResolved + data[i].Resolved ;
	
	}
	var totalPending=0;
	for (var i = 0; i <= data.length - 1; i++)
	{
	totalPending = totalPending + data[i].Pending ;
	
	}
	for (var x in data)
	{
	 data[x].PendingPer = Math.round(data[x].Pending / totalPending * 100);
	}
	for (var x in data)
	{
	 
	data[x].ResolvedPer = Math.round(data[x].Resolved / totalResolved * 100);
	 	}
	 
	 	
	console.log(data);
	var chart = AmCharts.makeChart("mytest", {
	"type" : "serial",
	"addClassNames" : true,
	"titles" : [ {
	"text" :  title+": "+total+" (Resolved: "+totalResolved+ " & Pending: "+totalPending+")",
	"size" : 15
	} ],
	"theme" : "light",
	"path" : "amcharts",
	"autoMargins" : false,
	"marginLeft" : 30,
	"marginRight" : 8,
	"marginTop" : 10,
	"marginBottom" : 26,
	"balloon" : {
	"adjustBorderColor" : false,
	"horizontalPadding" : 10,
	"verticalPadding" : 8,
	"color" : "#ffffff"
	},

	"dataProvider" : data,
	"valueAxes" : [ {
	"position" : "left",
	"gridAlpha" : 0
	} ],
	"startDuration" : 1,
	"graphs" : [ {
	"alphaField" : "alpha",
	"balloonText" : "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
	"fillAlphas" : 1,
	"title" : "Resolve",
	"type" : "column",
	"valueField" : "Resolved",
	"labelText" : "[[ResolvedPer]]%",
	"labelOffset":10,
	"dashLengthField" : "dashLengthColumn",
	"fixedColumnWidth" : 30
	}, {
	"id" : "graph2",
	"balloonText" : "<span style='font-size:12px;color:black;'>[[title]] in [[category]]:<br><span style='font-size:20px;color:black;'>[[value]]</span> [[additional]] ([[PendingPer]]%)</span>",
	"bullet" : "round",
	"lineThickness" : 3,
	"bulletSize" : 7,
	"bulletBorderAlpha" : 1,
	"bulletColor" : "#FFFFFF",
	"useLineColorForBulletBorder" : true,
	"bulletBorderThickness" : 3,
	"fillAlphas" : 0,
	"lineAlpha" : 1,
	"title" : "Pending",
	"valueField" : "Pending"
	} ],
	"depth3D" : 20,
	"angle" : 30,
	"categoryField" : "" + measure,
	"categoryAxis" : {
	"gridPosition" : "start",
	"axisAlpha" : 1,
	"axisColor" : "#B2B2B2",
	"tickPosition" : "start",
	"tickLength" : 10,
	"gridAlpha" : 0,

	"autoWrap" : true
	},
	"legend" : {
	"useGraphSettings" : true,
	"position" : "absolute"
	},

	"export" : {
	"enabled" : true
	}
	});
	chart.write("mytest");
	//singleDoubleClick("1",AmCharts);
	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
	// it's less than that - it's a
	// double click

	AmCharts.doSingleClick = function(event)
	{
	//alert("single");
	//handleClick(event);
	
	if (measure == 'floor')
	{
	 getFloorAnalysis(event);
	}
	if (measure == 'time')
	{
	 getTimeFloorAnalysis(event);
	}if (measure == 'staff')
	{
	 getStaffFloorAnalysis(event);
	}
	
	}
	var temp1='Location';
	if(escalated)
	{
	temp1='escalated';
	}
	else if(reopen)
	{
	temp1='reopen';
	}
	//handler for data fetch
	AmCharts.doDoubleClick = function(event)
	{
	var chart = event.chart;
	var categoryField = chart.categoryField;
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
	} else if (dataFor == 'Reassigned')
	{
	dataFor = 'Re-assigned';
	} else if (dataFor == 'Reopened')
	{
	dataFor = 'Re-opened';
	}
	if (categoryField == 'floor')
	{
	getDataLoc('', dataFor, 'LD', 'dataFor', dataContx.floorId,temp1);
	} else if (categoryField == 'staff')
	{
	getDataLoc('', dataFor, 'SD', 'dataFor', dataContx.staffId,temp1);
	} else if (categoryField == 'time')
	{
	getDataLoc('', dataFor, 'TD', 'dataFor', dataContx.timeId,temp1);
	}
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
	}
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler);
	
	}
	},
	error : function()
	{
	alert("error");
	}
	});
	}
}// end

// method for floor analysis
function getFloorAnalysis(event)
{
	var chart = event.chart;

	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	categoryField = data[indexFor].category;
	var floorId = dataContx.floorId;
	var dataFor = graph.title;
	faFlag = true;
	indexId = floorId;
	indexValue = graph.title;
	if (indexValue == 'Resolve')
	{
	indexValue = 'Resolved';
	}
	StatckedFloorAnalysis("mytest", "Location");
	$("#locationbttn").attr('onclick', 'StatckedFloorAnalysis("mytest","Location")');
	$("#timebttn").attr('onclick', 'StatckedFloorAnalysis("mytest","Time")');
	$("#staffbttn").attr('onclick', 'StatckedFloorAnalysis("mytest","Staff")');
}
//handler for timeClick 
function getTimeFloorAnalysis(event){
	//console.log(event);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	categoryField = data[indexFor].category;
	var floorId = dataContx.timeId;
	
	
	indexId = floorId;
	indexValue = graph.title;
	if (indexValue == 'Resolve')
	{
	indexValue = 'Resolved';
	}
	//console.log(measure1);
	timeAnalysis(measure1);
	
	$("#locationbttn").attr('onclick', 'timeAnalysis("time")');
	$("#timebttn").attr('onclick', '').css('opacity', '.3');
	$("#staffbttn").attr('onclick', 'timeAnalysis("staff")');
}

//staff handler

function getStaffFloorAnalysis(event){
	////console.log(event);
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	categoryField = data[indexFor].category;
	var staff = dataContx.staffId;
	
	
	indexId = staff;
	indexValue = graph.title;
	if (indexValue == 'Resolve')
	{
	indexValue = 'Resolved';
	}
	////console.log(measure1);
	staffAnalysis(measure1);
	
	$("#locationbttn").attr('onclick', 'staffAnalysis("Location")');
	$("#staffbttn").attr('onclick', '').css('opacity', '.3');
	$("#timebttn").attr('onclick', 'staffAnalysis("time")');
}

function timeAnalysis(measure){
	$("#backbttn").attr('onclick','StatckedChartStatusLoc12(measure1)').css('display','block');
	measure1=measure;
	xaxis3=measure;
	//alert(measure);
	taFlag = true;
	var dept = $("#deptnameLoc").val();
	var url1='';
	 if(dept!="-1"  && !escalated && !reopen && taFlag)
	{
	url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getTimeFloorAnalysis.action?dataFlag="+indexId+"&xaxis="+measure+"&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept=="-1" && !escalated && !reopen && taFlag)
	{
	url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getTimeFloorAnalysis.action?dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 
	 else if(dept=="-1" &&  escalated && !reopen && taFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getTimeFloorAnalysis.action?filterFlag=escalated&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept=="-1"  && !escalated && reopen && taFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getTimeFloorAnalysis.action?filterFlag=reopen&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept!="-1"  && escalated && !reopen && taFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getTimeFloorAnalysis.action?filterFlag=escalated&filterDeptId="+dept+"&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept!="-1" && !escalated && reopen && taFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getTimeFloorAnalysis.action?filterFlag=reopen&filterDeptId="+dept+"&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 
	 if (measure == 'time')
	{
	 measure = 'floor';
	}else if (measure == 'staff'){
	measure = 'staff';
	}
	//console.log(url1);
	 
	
	 if(url1!=""){
	 $("#mytest").html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	 $.ajax({
	    type : "post",
	    url : url1,
	    type : "json",
	    success : function(data) {
	    	//console.log(data);
	    	var total=0;
	    	if(indexValue=='Resolved'){
	    	for (var i=0;i<=data.length-1;i++){
	    	total=total+data[i].Resolved;
	    	}
	    	}else if (indexValue=='Pending'){
	    	for (var i=0;i<=data.length-1;i++){
	    	total=total+data[i].Pending;
	    	}
	    	}
	    	for (var x in data)
	{
	 
	data[x].ResolvedPer = Math.round(data[x].Resolved / total * 100);
	//total=total+data[x].Resolved;
	}
	    
	     	var chart = AmCharts.makeChart("mytest", {
	  	  "type": "serial",
	  	  "titles": [{
	  	"text":  getFloorChartTitle('floor')+": "+total,
	  	  	  	"size": 15
	  	  	  	}
	  	  	  	],
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

	  	  "dataProvider": data,
	  	 "valueAxes": [{
	  	        "position": "left",
	  	        "gridAlpha":0
	  	    }],
	  	  "startDuration": 1,
	  	  "graphs": [{
	  	    "alphaField": "alpha",
	  	    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
	  	    "fillAlphas": 1,
	  	    "title": "Resolve",
	  	    "type": "column",
	  	    "valueField": "Resolved",
	  	  "labelText" : "[[ResolvedPer]]%",
	  	   "labelOffset":10,
	  	    "dashLengthField": "dashLengthColumn",
	  	    "fixedColumnWidth":30
	  	  }, {
	  	    "id": "graph2",
	  	    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
	  	    "bullet": "round",
	  	    "lineThickness": 3,
	  	    "bulletSize": 7,
	  	    "bulletBorderAlpha": 1,
	  	    "bulletColor": "#FFFFFF",
	  	    "useLineColorForBulletBorder": true,
	  	    "bulletBorderThickness": 3,
	  	    "fillAlphas": 0,
	  	    "lineAlpha": 1,
	  	    "title": "Pending",
	  	    "valueField": "Pending"
	  	  }],
	  	"depth3D": 20,
	  	"angle": 30,
	  	  "categoryField": ""+measure,
	  	  "categoryAxis": {
	  	    "gridPosition": "start",
	  	    "axisAlpha": 1,
	  	    "axisColor":"#B2B2B2",
	  	    "tickPosition": "start",
	  	    "tickLength": 10,
	  	    "gridAlpha":0,
	  	  
	  	   "autoWrap":true
	  	  },
	  	"legend": {
	  	    "useGraphSettings": true,
	  	    "position":"absolute"
	  	  },

	  	  "export": {
	  	    "enabled": true
	  	  }
	  	});
	  	chart.write("mytest");
	  //singleDoubleClick("1",AmCharts);
	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
	// it's less than that - it's a
	// double click

	AmCharts.doSingleClick2 = function(event)
	{
	
	}

	AmCharts.doDoubleClick2 = function(event)
	{
	console.log(event);
	var chart = event.chart;
	var categoryField = chart.categoryField;
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	var dataFor = graph.title;
	
	var temp1='Location';
	if(escalated)
	{
	temp1='escalated';
	}
	else if(reopen)
	{
	temp1='reopen';
	}
	var temp='';
	
	if (dataFor == 'Resolve')
	{
	dataFor = 'Resolved';
	} else if (dataFor == 'Park')
	{
	dataFor = 'Snooze';
	} else if (dataFor == 'Reassigned')
	{
	dataFor = 'Re-assigned';
	} else if (dataFor == 'Reopened')
	{
	dataFor = 'Re-opened';
	}
	if (categoryField == 'floor')
	{
	
	temp="TAL";
	
	getDataLoc('', dataFor, temp, 'dataFor', dataContx.floorId+"KK"+indexId,temp1);
	}
	else if (categoryField == 'staff')
	{
	
	temp="TAS";
	
	getDataLoc('', dataFor, temp, 'dataFor', dataContx.staffId+"KK"+indexId,temp1);
	}
	
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
	  
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
	 }
	
}

//caller for stafftime breakup

function staffAnalysis(measure){
	$("#backbttn").attr('onclick','StatckedChartStatusLoc12(measure1)').css('display','block');
	measure1=measure;
	xaxis4=measure;
	saFlag = true;
	var dept = $("#deptnameLoc").val();
	var url1='';
	if(measure.trim()=="staff"){measure == 'location';}
	 if(dept!="-1"  && !escalated && !reopen && saFlag)
	{
	url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getStaffFloorAnalysis.action?dataFlag="+indexId+"&xaxis="+measure+"&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept=="-1"  && !escalated && !reopen && saFlag)
	{
	url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getStaffFloorAnalysis.action?dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	
	 else if(dept=="-1"  && escalated && !reopen && saFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getStaffFloorAnalysis.action?filterFlag=escalated&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept=="-1" && !escalated && reopen && saFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getStaffFloorAnalysis.action?filterFlag=reopen&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept!="-1"  && escalated && !reopen && saFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getStaffFloorAnalysis.action?filterFlag=escalated&filterDeptId="+dept+"&dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 else if(dept!="-1"  && !escalated && reopen && saFlag)
	{
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getStaffFloorAnalysis.action?filterFlag=reopen&&filterDeptId="+dept+"dataFlag="+indexId+"&xaxis="+measure+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	}
	 if (measure == 'staff')
	{
	 measure = 'floor';
	}else if (measure == 'time'){
	measure = 'time';
	}
	console.log(url1);
	 
	
	 if(url1!=""){
	 $("#mytest").html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	 $.ajax({
	    type : "post",
	    url : url1,
	    type : "json",
	    success : function(data) {
	    	//console.log(data);
	    	var total=0;
	    	if(indexValue=='Resolved'){
	    	for (var i=0;i<=data.length-1;i++){
	    	total=total+data[i].Resolved;
	    	}
	    	}else if (indexValue=='Pending'){
	    	for (var i=0;i<=data.length-1;i++){
	    	total=total+data[i].Pending;
	    	}
	    	}
	    	for (var x in data)
	{
	 
	data[x].ResolvedPer = Math.round(data[x].Resolved / total * 100);
	//total=total+data[x].Resolved;
	}
	    
	     	var chart = AmCharts.makeChart("mytest", {
	  	  "type": "serial",
	  	  "titles": [{
	  	"text":  getFloorChartTitle('floor')+": "+total,
	  	  	  	"size": 15
	  	  	  	}
	  	  	  	],
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

	  	  "dataProvider": data,
	  	 "valueAxes": [{
	  	        "position": "left",
	  	        "gridAlpha":0
	  	    }],
	  	  "startDuration": 1,
	  	  "graphs": [{
	  	    "alphaField": "alpha",
	  	    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
	  	    "fillAlphas": 1,
	  	    "title": "Resolve",
	  	    "type": "column",
	  	    "valueField": "Resolved",
	  	  "labelText" : "[[ResolvedPer]]%",
	  	 "labelOffset":10,
	  	    "dashLengthField": "dashLengthColumn",
	  	    "fixedColumnWidth":30
	  	  }, {
	  	    "id": "graph2",
	  	    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
	  	    "bullet": "round",
	  	    "lineThickness": 3,
	  	    "bulletSize": 7,
	  	    "bulletBorderAlpha": 1,
	  	    "bulletColor": "#FFFFFF",
	  	    "useLineColorForBulletBorder": true,
	  	    "bulletBorderThickness": 3,
	  	    "fillAlphas": 0,
	  	    "lineAlpha": 1,
	  	    "title": "Pending",
	  	    "valueField": "Pending"
	  	  }],
	  	"depth3D": 20,
	  	"angle": 30,
	  	  "categoryField": ""+measure,
	  	  "categoryAxis": {
	  	    "gridPosition": "start",
	  	    "axisAlpha": 1,
	  	    "axisColor":"#B2B2B2",
	  	    "tickPosition": "start",
	  	    "tickLength": 10,
	  	    "gridAlpha":0,
	  	  
	  	   "autoWrap":true
	  	  },
	  	"legend": {
	  	    "useGraphSettings": true,
	  	    "position":"absolute"
	  	  },

	  	  "export": {
	  	    "enabled": true
	  	  }
	  	});
	  	chart.write("mytest");
	    //singleDoubleClick("1",AmCharts);
	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
	// it's less than that - it's a
	// double click

	AmCharts.doSingleClick2 = function(event)
	{
	
	}

	AmCharts.doDoubleClick2 = function(event)
	{
	console.log(event);
	var chart = event.chart;
	var categoryField = chart.categoryField;
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	var dataFor = graph.title;
	
	var temp1='Location';
	if(escalated)
	{
	temp1='escalated';
	}
	else if(reopen)
	{
	temp1='reopen';
	}
	var temp='';
	
	if (dataFor == 'Resolve')
	{
	dataFor = 'Resolved';
	} else if (dataFor == 'Park')
	{
	dataFor = 'Snooze';
	} else if (dataFor == 'Reassigned')
	{
	dataFor = 'Re-assigned';
	} else if (dataFor == 'Reopened')
	{
	dataFor = 'Re-opened';
	}
	if (categoryField == 'floor')
	{
	
	temp="SAL";
	
	getDataLoc('', dataFor, temp, 'dataFor', dataContx.floorId+"KK"+indexId,temp1);
	}
	else if (categoryField == 'time')
	{
	
	temp="SAT";
	
	getDataLoc('', dataFor, temp, 'dataFor', dataContx.timeID+"KK"+indexId,temp1);
	}
	
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
	  
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
	 }
	
}
