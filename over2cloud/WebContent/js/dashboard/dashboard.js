/*function showDetailsPie(dataBlockDiv,graphBlockDiv)
{
	
	$("#"+graphBlockDiv).show();
	$("#"+dataBlockDiv).hide();
}

function showDetailsPie1(graphBlockDiv1,graphBlockDiv2,middleDiv)
{
	$("#"+middleDiv).css({"width":"85%","float":"left"});
	$("#"+graphBlockDiv1).show();
	$("#"+graphBlockDiv2).show();
}

function restoreBoard(graphBlockDiv1,graphBlockDiv2,middleDiv)
{
	$("#"+middleDiv).css({"width":"100%"});
	$("#"+graphBlockDiv1).hide();
	$("#"+graphBlockDiv2).hide();
	if(middleDiv=='jqxChart'){
	 countnext=0;
	 countprev=0;
	}else if(middleDiv=='levelChart'){
		countnext1=0;
		 countprev1=0;
	}
}
function showDetailsData(dataBlockDiv,graphBlockDiv)
{
	$("#"+dataBlockDiv).show();
	$("#"+graphBlockDiv).hide();
}*/
var catEscalated=false;
var catReopen=false;
function getCategoryData(Id)
{
	$("#maxmizeCatgDialog").dialog('open');
	$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getCatgDetail.action?id=" + Id,
		success : function(data)
		{
			$("#maximizeCatgDiv").html(data);
		},
		error : function()
		{
			alert("error");
		}
	});
}

function maximizeDiv(divBlock, height, width, blockHeading)
{
	$("#maxmizeViewDialog").dialog({
		title : blockHeading,
		height : height,
		width : width,
		dialogClass : 'transparent'
	});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getDashboardData.action?maximizeDivBlock=" + divBlock,
		success : function(data)
		{
			$("#maximizeDataDiv").html(data);
		},
		error : function()
		{
			alert("error");
		}
	});
}
// Maximise for 1st dashbaord
function beforeMaximise(divBlock, height, width, blockHeading, dataFor)
{

	if (maxDivId1 == '1stTable')
	{
		maximizeDiv4Analytics(270, 900, 'Ticket Count Status', 'T');
		showData('jqxChart', 'maximizeDataDiv');
	} else if (maxDivId1 == '1stStackedBar')
	{
		isMaxDept=true;
		maximizeDiv4Analytics(height, width, blockHeading, dataFor);
		if(status1=='All')
		{
			StatckedChartStatus('maximizeDataDiv', '', '', status1, title1, color1,'stack');
		}	
		else
		{
			StatckedChartStatus('maximizeDataDiv', '', '', status1, title1, color1,'column');
		}	
		
	}
	/*
	 * else if(maxDivId1=='1stColumnBar') {
	 * maximizeDiv4Analytics(height,width,blockHeading,dataFor);
	 * Column2AxesChartStatus('maximizeDataDiv','','');
	 *  } else if(maxDivId1=='1stLine') {
	 * maximizeDiv4Analytics(height,width,blockHeading,dataFor);
	 * lineStatus('maximizeDataDiv','','');
	 *  } else if(maxDivId1=='1stBubble') {
	 * maximizeDiv4Analytics(height,width,blockHeading,dataFor);
	 * bubbleStatus('maximizeDataDiv','','');
	 *  }
	 */
	else if (maxDivId1 == '1stPendingPie')
	{
		maximizeDiv4Analytics(400, width, blockHeading, dataFor);
		showPieStatus('maximizeDataDiv', status1, 'Pending Ticket Status', 'For All Departments');

	}

	else
	{
		maximizeDiv4Analytics(divBlock, height, width, blockHeading, dataFor);
	}

}

// maximize for 2nd dashbaord
function beforeMaximise1(divBlock, height, width, blockHeading, dataFor)
{

	if (maxDivId2 == '2ndTable')
	{
		maximizeDiv4Analytics(height, width, blockHeading, dataFor);
		showData('levelChart', 'maximizeDataDiv');

	} else if (maxDivId2 == '2ndStackedBar')
	{
		maximizeDiv4Analytics(height, width, blockHeading, dataFor);
		//alert(status2);
		if(status2=='All')
		{
			showLeveStackedBar('maximizeDataDiv', '', '', status2, title2, color2,'stack');
		}	
		else
		{
			showLeveStackedBar('maximizeDataDiv', '', '', status2, title2, color2,'column');
		}	

	} /*
		 * else if(maxDivId2=='2ndColumn2Axes'){
		 * maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		 * showLevelColumn2Axes('maximizeDataDiv','','');
		 * 
		 * }else if(maxDivId2=='LineLevel'){
		 * maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		 * lineLevel('maximizeDataDiv','','');
		 * 
		 * }else if(maxDivId2=='BubbleLevel'){
		 * maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		 * bubbleLevel('maximizeDataDiv','','');
		 *  }
		 */else if (maxDivId2 == '2ndPendingPie')
	{
		maximizeDiv4Analytics(400, width, blockHeading, dataFor);
		showPieLevel('maximizeDataDiv', status2, 'Pending Ticket Status', 'For All Departments');
	} else
	{
		maximizeDiv4Analytics(height, width, blockHeading, dataFor);
	}

}

// maximize for 3rd dashbaord
function beforeMaximise2(divBlock, height, width, blockHeading, dataFor)
{
	if (maxDivId3 == '3rdTable')
	{
		maximizeDiv4Analytics(270, 900, 'Subcategory Analysis', 'T');
		showData('CategChart', 'maximizeDataDiv');
	} else if (maxDivId3 == 'PieCateg')
	{
		maximizeDiv4Analytics(height, width, blockHeading + " For " + $("#sdate").val() + " To " + $("#edate").val(), dataFor);
		showPieCateg('maximizeDataDiv');

	} else if (maxDivId3 == 'BarCateg')
	{
		maximizeDiv4Analytics(370, 1100, blockHeading, dataFor);
		showBarCatg('maximizeDataDiv');
	} else
	{
		maximizeDiv4Analytics(divBlock, height, width, blockHeading, dataFor);
	}
}

//On change of subcategory dept DD
function onCategDropDown()
{
	if (maxDivId3 == '3rdTable')
	{
		showData('CategChart', 'maximizeDataDiv');
	} else if (maxDivId3 == 'PieCateg')
	{
		showPieCateg('CategChart');

	} else if (maxDivId3 == 'BarCateg')
	{
		showBarCatg('CategChart');
	}
}

function maximizeDiv4Analytics(height, width, blockHeading, dataFor)
{
	$("#maxmizeViewDialog").dialog({
		title : blockHeading,
		height : height,
		width : width,
		dialogClass : 'transparent'
	});
	$("#maxmizeViewDialog").dialog('open');
}

function filterData(div, type, deptId)
{
	$("#maxmizeViewDialog").dialog({
		title : "Filter Data Department Wise",
		height : 400,
		width : 900,
		dialogClass : 'transparent'
	});
	$("#maxmizeViewDialog").dialog('open');
	StatckedChartStatus(div, type, deptId);

}

function maximizeDiv4Level(divBlock, height, width, blockHeading, dataFor)
{
	$("#maxmizeViewDialog").dialog({
		title : blockHeading,
		height : height,
		width : width,
		dialogClass : 'transparent'
	});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getLevelAnalytics.action?maximizeDivBlock=" + divBlock + "&status_for=" + dataFor,
		success : function(data)
		{
			$("#maximizeDataDiv").html(data);
		},
		error : function()
		{
			alert("error");
		}
	});
}

function ShowTicketData(id, status_for)
{
	if (status_for == 'T')
	{
		$("#ticketDialog").dialog({
			title : 'Ticket Detail'
		});
	} else
	{
		$("#ticketDialog").dialog({
			title : 'Feedback By Detail'
		});
	}
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/ticketInfo.action?ticket_id=" + id + "&status_for=" + status_for,
		success : function(ticketdata)
		{
			$("#ticketsInfo").html(ticketdata);
		},
		error : function()
		{
			alert("error");
		}
	});
	$("#ticketDialog").dialog('open');
}

function getData(id, status, flag, dataForId, level)
{
	// alert();
	var dataFor = $("#" + dataForId).val();
	var ll = $("#level").val();
	//alert(ll);
	var reopen=null;
	if (flag == 'L')
	{

		if($("#deptname1").val()!=-1)
		{
			dataFor="H";
			id=$("#deptname1").val();
		}
			
		if( $('#reopenLevel').is(":checked"))
		{
			reopen='Yes';
		}	
		else
		{
			reopen='No';
		}	
		/*if(dataFor=="subdept")
		{
			dataFor = "H";
		}
		if(dataFor=="subcatg")
		{
			dataFor = "C";
		}*/
	}
	else if (flag == 'SC')
	{
		if(dataForDept=="dept")
		{
			dataFor = "H";
		}
		if(dataForDept=="subdept")
		{
			dataFor = "C";
		}
		if(dataForDept=="subcatg")
		{
			dataFor = "SC";
		}
	}
	else if (flag == 'T')
	{
		if ($("#deptname").val() != -1)
		{
			dataFor = "H";
		}
	}
	if (flag == 'LD' || flag == 'SD' || flag == 'TD')
	{

		if ($("#deptnameLoc").val() != -1)
		{
			dataFor = "H";
			id = $("#deptnameLoc").val();
		}
	}
	$("#confirmEscalationDialog").dialog({
		width : 1150,
		height : 500
	});
//	alert(dataFor);
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?dataCheck=deptLevel&feedStatus=" + status + "&dashFor=" + dataFor + "&d_subdept_id=" + id + "&dataFlag=" + flag + "&location=" + level + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val()+ "&reopen=" +reopen+"&dylevel="+ll,
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

function getDataLoc(id, status, flag, dataForId, level,filterFlag)
{
	var dataFor = $("#" + dataForId).val();
	if (flag == 'LD' || flag == 'SD' || flag == 'TD' || flag=='WAL' || flag=='WAT' || flag=='WAS' || flag=='RAL' || flag=='RAT' || flag=='RAS' || flag=='TAL' || flag=='TAS' || flag=='SAL' || flag=='SAT' )
	{
		if ($("#deptnameLoc").val() != -1)
		{
			dataFor = "H";
			id = $("#deptnameLoc").val();
		}
	}
	$("#confirmEscalationDialog").dialog({
		width : 1250,
		height : 500
	});
	
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?dataCheck="+filterFlag+"&feedStatus=" + status + "&dashFor=" + dataFor + "&d_subdept_id=" + id + "&dataFlag=" + flag + "&location=" + level + "&fromDate=" + $('#sdate').val() + "&toDate=" + $('#edate').val(),
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

function beforeCategAnalysis(){
	$("#maxmizeCatgDialog").dialog({
		title:"Category Analysis",
		width : 1250,
		height : 500
	});
	$("#maxmizeCatgDialog").dialog('open');
}
function getEscCat(val){
	if(val)
	{
		catEscalated=true;
		
	}else {
		
		catEscalated=false;
	}
	 
}

function getReoCat(val)
{
	if(val){
		
		catReopen=true;
	}else {
		
		catReopen=false;
	}
	 
	 
}
function categAnalysis(dataFor){
	var limit=$("#limit").val();
	var dataFor=$("#dataMeasure").val();
	
	
	var dept=$("#deptnameCat").val();
	url1='';
	 if(dept!="-1" && !catEscalated && !catReopen )
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforegetCounterCategoryData.action?dataFlag="+limit+"&dashFor="+dataFor+"&filterDeptId="+dept+"&fromDate="+$('#sdate1').val()+"&toDate="+$('#edate1').val();
		}
	 else if(dept!="-1" && catEscalated && !catReopen )
	 {
		 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforegetCounterCategoryData.action?dataFlag="+limit+"&filterFlag=escalated&dashFor="+dataFor+"&filterDeptId="+dept+"&fromDate="+$('#sdate1').val()+"&toDate="+$('#edate1').val();
	 }
 else if(dept!="-1" && !catEscalated && catReopen ){
	 url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/beforegetCounterCategoryData.action?dataFlag="+limit+"&filterFlag=reopen&dashFor="+dataFor+"&filterDeptId="+dept+"&fromDate="+$('#sdate1').val()+"&toDate="+$('#edate1').val();
	 }
	 else if(dept=="-1"  )
		{
			alert("Please select departmet");
		}
	if(url1!=''){
		$("#maximizeCatgDiv").html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
		$.ajax({
			type : "post",
			url : url1,
			success : function(feedbackdata)
			{
				$("#maximizeCatgDiv").html(feedbackdata);
			},
			error : function()
			{
				alert("error");
			}
		});
	}
	
}


function download(){
	
	
		//alert("3");
		html2canvas($("#prntTable"), {
            onrendered: function(canvas) {
                theCanvas = canvas;
                document.body.appendChild(canvas);
                // Convert and download as image 
                Canvas2Image.saveAsPNG(canvas); 
                //  var data = canvas.toDataURL("image/png").replace( '/^data:image\/png;base64,/', 'data:application/octet-stream');
                // window.location.href = data;
                var dt = canvas.toDataURL('image/png');
               // window.open(dt);
               this.href = dt.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
                $("#img-out").append(canvas);
                // Clean up 
                document.body.removeChild(canvas);
            }
        });
         
   
		
}

