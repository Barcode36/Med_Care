function getAllotToDetails(deptId,deptName)
{
	$('#feedDialog').dialog({title: 'Ticket Allotment Details For '+deptName,width:'1000',height:'400'});
	$('#feedDialog').dialog('open');
	$.ajax( {
		type :"post",
		url :"view/Over2Cloud/feedback/deptAllotToDetails.action?feedStatus="+status.split(" ").join("%20")+"&deptId="+deptId,
		success : function(data) {
	 		$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}


function getDataForCounter(stat,fieldVal,searchField,searchString)
{
	$("#complianceDialog").dialog({title: 'Feedback Selected Data',width:'1000',height:'400'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $.ajax( {
		 
			type :"post",
			url :"view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+stat.split(" ").join("%20")+"&deptId="+fieldVal+"&searchField="+searchField+"&searchString="+searchString,
			success : function(data) {
		 		$("#datadiv").html(data);
			},
			error : function() {
				alert("error");
			}
		});
}

function getDataForFeedback(mode,searchFor)
{
	$("#complianceDialog").dialog({title: 'Feedback Details',width:'1000',height:'400'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $.ajax( {
		 
			type :"post",
			url :"view/Over2Cloud/feedback/beforeFeedbackCountersView.action?mode="+mode.split(" ").join("%20")+"&searchFor="+searchFor,
			success : function(data) {
		 		$("#datadiv").html(data);
			},
			error : function() {
				alert("error");
			}
		});
}
function showBarChart(id)
{
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	if(id=='block1')
	{
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/beforeJsonChartDataDept.action",
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
	else if(id=='block2')
	{
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/beforeJsonChartFeedback.action",
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
}

function showDetailsPiessChart(id) 
{
	if(id=='block2')
	{
		$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/pieChartFeedbackModes.action",
		    success : function(ticketdata) {
			$("#block2").html(ticketdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });

    }
}
function showPieChart(id)
{
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/beforePieChartForFeedback.action?block="+id,
		success : function(ticketdata) 
		{
			$("#"+id).html(ticketdata);
		},
		error : function() {
			alert("error");
		}
	});
}
function showNegativeFeedDataCounter(id){
	
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/beforeNegativeFeedCounter.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
}
function getNegativeFeedData(status,mode,ratingFlag)
{
	$( "#feedDialog" ).dialog({title: 'Negative Feedback Status For '+mode+' From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
	$("#feedDialog").dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/dataOnClickFeedbackMode.action?mode="+mode+"&status="+status+"&ratingFlag="+ratingFlag+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}

function showCounterDataFeedbackAction(id)
{
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/counterFeedbackAction.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
		success : function(ticketdata) {
		$("#"+id).html(ticketdata);
		},
		error : function() {
			alert("error");
		}
	});
}

function getQualityScore(deptId)
{
	$("#maximizeDataPlr").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/feedback_Activity/beforeQualityDetailsView.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val()+"&deptId="+deptId+"&moduleName=FM",
	    success : function(subdeptdata) {
       $("#maximizeDataPlr").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	$("#maxmizeViewPolar").dialog({title: 'Quality Counters',height: 400,width: 950});
	$("#maxmizeViewPolar").dialog('open');
}

function getProductivity(deptId,prodType)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalyticsGrid.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val()+"&deptId="+deptId+"&&dataFor="+prodType+"&moduleName=FM",
	    success : function(subdeptdata) {
       $("#maximizeDataPlr").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
	$("#maxmizeViewPolar").dialog({height:400,width:950});
	$("#maxmizeViewPolar").dialog('open');
}


function showCounterDataTicketing(id)
{
	
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	if(id=='jqxChart')
	{
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/beforeDashboardTicketsCounter.action?fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
	else if(id=='block2')
	{
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/counterFeedbackModes.action?block="+id+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
	else if(id=='block5')
	{
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/counterFeedbackModes.action?block="+id,
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
	else if(id=='block6')
	{
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/counterFeedbackModes.action?block="+id,
			success : function(ticketdata) {
			$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	}
}

function getOnClickDataForTicket(deptId,status,deptName)
{
	if(deptName=="")
	{
		$( "#feedDialog" ).dialog({title: 'Total '+status+' Tickets From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
	}
	else
	{
		$( "#feedDialog" ).dialog({title: ''+status+' Tickets of '+deptName+' From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
	}
	
	$("#feedDialog").dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/getDataOnClick.action?deptId="+deptId+"&status="+status+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}
function getFeedbackModeOnClick(mode,status,ratingFlag)
{
	// $("#mybuttondialog").dialog({title: 'Current Status '+status+' with '+level+', Open on '+openOn,width: 450,height: 350});
	$( "#feedDialog" ).dialog({title: 'Feedback '+mode+' Count For '+status+' Tickets From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
	$('#feedDialog').dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/dataOnClickFeedbackMode.action?mode="+mode+"&status="+status+"&ratingFlag="+ratingFlag+"&dataFor=feedbackMode&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}
//to be merged 31-10-2014
function showCounterDataAnalysis(id){
	
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/counterFeedbackAction.action?block="+id+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
		success : function(ticketdata) {
		$("#"+id).html(ticketdata);
		},
		error : function() {
			alert("error");
		}
	});
	
}
//abhay 10-10-2014
function getFeedbackActionOnClick(deptId,action,deptName)
{
	if(deptName=="")
	{
		$( "#feedDialog" ).dialog({title: 'Total Feedback '+action+' From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
	}
	else
	{
		$( "#feedDialog" ).dialog({title: 'Feedback '+action+' For '+deptName+' From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
	}	
	
	$('#feedDialog').dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/dataOnClickFeedbackMode.action?mode="+action+"&deptId="+deptId+"&dataFor=deptAction&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}
//abhay 29-10-2014
function getFeedbackAnalysisOnClick(action)
{
		$( "#feedDialog" ).dialog({title: 'Feedback Analysis For '+action+' From '+$("#sdate").val()+' to '+$("#edate").val(),width: 1200,height: 500});
		$('#feedDialog').dialog('open');
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/feedback/dataOnClickFeedbackMode.action?mode="+action+"&dataFor=FeedbackAnalysis&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
			success : function(data) 
			{
				$("#feedDialog").html(data);
			},
			error : function() {
				alert("error");
			}
		});	
}

function getOnClickDataForFeedbackType(type,status)
{
	$("#feedDialog").dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/getDataOnClick.action?type="+type+"&status="+status,
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}
function getPendingTicketData(status,feedId)
{
	$("#feedDialog").dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/getDataOnClick.action?status="+status+"&feedId="+feedId,
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
}
function getLevelDataOnClick(status,level,deptID)
{
//	alert(status+">><><><><><>"+level);
	$("#feedDialog").dialog('open');
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/feedback/getDataOnClick.action?status="+status+"&type="+level+"&deptId="+deptID,
		success : function(data) 
		{
			$("#feedDialog").html(data);
		},
		error : function() {
			alert("error");
		}
	});
	
}