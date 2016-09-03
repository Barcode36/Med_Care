 
function getCategoryData(Id)
{
	$("#maxmizeCatgDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/getCatgDetail.action?id="+Id,
	    success : function(data) 
	    {
			$("#maximizeCatgDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

function maximizeDiv(divBlock,height,width,blockHeading)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/getDashboardData.action?maximizeDivBlock="+divBlock,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}
//Maximise for 1st dashbaord
function beforeMaximise(divBlock,height,width,blockHeading,dataFor){
	
	if(maxDivId1=='1stTable')
	{
		maximizeDiv4Analytics(270,900,'Ticket Count Status','T');
		showData('jqxChart','maximizeDataDiv');
	}
	else if(maxDivId1=='1stStackedBar')
	{
			maximizeDiv4Analytics(height,width,blockHeading,dataFor);
			StatckedChartStatus('maximizeDataDiv','','',status1,title1,color1);
	}
	/*else if(maxDivId1=='1stColumnBar')
	{
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		 Column2AxesChartStatus('maximizeDataDiv','','');
		
	}
	else if(maxDivId1=='1stLine')
	{
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		 lineStatus('maximizeDataDiv','','');
	
    }
	else if(maxDivId1=='1stBubble')
    {
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		bubbleStatus('maximizeDataDiv','','');
	
    }*/
	else if(maxDivId1=='1stPendingPie')
    {
		maximizeDiv4Analytics(400,width,blockHeading,dataFor);
		showPieStatus('maximizeDataDiv',status1,'Pending Ticket Status','For All Departments');
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		}
	
}



//maximize for 2nd dashbaord
function beforeMaximise1(divBlock,height,width,blockHeading,dataFor){
	
	
     if(maxDivId2=='2ndTable'){
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		showData('levelChart','maximizeDataDiv');
	
    } else if(maxDivId2=='2ndStackedBar'){
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		showLeveStackedBar('maximizeDataDiv','','',status2,title2,color2);
	
    } /*else if(maxDivId2=='2ndColumn2Axes'){
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		showLevelColumn2Axes('maximizeDataDiv','','');
	
    }else if(maxDivId2=='LineLevel'){
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		lineLevel('maximizeDataDiv','','');
	
    }else if(maxDivId2=='BubbleLevel'){
		maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		 bubbleLevel('maximizeDataDiv','','');
	
    }*/else if(maxDivId2=='2ndPendingPie'){
		maximizeDiv4Analytics(400,width,blockHeading,dataFor);
		showPieLevel('maximizeDataDiv',status2,'Pending Ticket Status','For All Departments');
    }
	else {
			maximizeDiv4Analytics(height,width,blockHeading,dataFor);
		}
	
}

//maximize for 3rd dashbaord
function beforeMaximise2(divBlock,height,width,blockHeading,dataFor){
	
	
     if(maxDivId3=='3rdTable'){
		maximizeDiv4Analytics(270,900,'Category Analysis','T');
		showData('CategChart','maximizeDataDiv');
    } else if(maxDivId3=='PieCateg'){
    	maximizeDiv4Analytics(height,width,blockHeading+" For "+$("#sdate").val(),dataFor);
    	showPieCateg('maximizeDataDiv');
	
    } else if(maxDivId3=='DoughnutCateg'){
    	maximizeDiv4Analytics(height,width,blockHeading,dataFor);
    	DoughnutCateg('maximizeDataDiv');
    }
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		}
	
}

//maximize for 4th dashbaord
function beforeMaximise4(divBlock,height,width,blockHeading,dataFor){
	
	
	if(maxDivId4=='1stTable')
	{
		maximizeDiv4Analytics(270,900,'Ticket Count Status','T');
		showData('reopneChart','maximizeDataDiv','');
	}
	else if(maxDivId4=='1stStackedBar')
	{
			maximizeDiv4Analytics(height,width,blockHeading,dataFor);
			StatckedChartReOpen('maximizeDataDiv','','',status1,title1,color1);
			 
	}
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		}
	
}

function maximizeDiv4Analytics(height,width,blockHeading,dataFor)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
}


function filterData(div,type,deptId){
	$("#maxmizeViewDialog").dialog({title:"Filter Data Department Wise",height:400,width:900,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	 StatckedChartStatus(div,type,deptId);
	
}

function maximizeDiv4Level(divBlock,height,width,blockHeading,dataFor)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/getLevelAnalytics.action?maximizeDivBlock="+divBlock+"&status_for="+dataFor,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

function ShowTicketData(id,status_for)
 {
	if(status_for=='T')
	{$("#ticketDialog").dialog({title: 'Ticket Detail'});}
	else
	{$("#ticketDialog").dialog({title: 'Feedback By Detail'});}
   $.ajax( {
	type :"post",
	url :"view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/ticketInfo.action?ticket_id="+id+"&status_for="+status_for,
	success : function(ticketdata) {
	$("#ticketsInfo").html(ticketdata);
	},
	error : function() {
		alert("error");
	}
});
$("#ticketDialog").dialog('open');
 }



function getData(id,status,flag,dataForId,level)
{
	//alert(id);
	var dataFor=$("#"+dataForId).val();
	if(flag=='L')
	{
		if($("#deptname1").val()!=-1){
			dataFor="H";
			//id=$("#deptname1").val();
			if(id==-1)
			{
				id=$("#deptname").val();
			}
		}
	}	
	else if(flag=='T')
	{	
		if($("#deptname").val()!=-1){
			dataFor="H";
		if(id==-1)
		{
			id=$("#deptname").val();
		}
		//	id=$("#deptname").val();
		}
	}

	else if(flag=='Re')
		{
		if($("#deptnameReopen").val()!=-1)
		    {
				dataFor="H";
				//id=$("#deptnameReopen").val();
				if(id==-1)
				{
					id=$("#deptname").val();
				}
			}
		}
	else if(flag=='LD'||flag=='SD'||flag=='TD'){
		
		if($("#deptnameLoc").val()!=-1){
			dataFor="H";
			id=$("#deptnameLoc").val();
		}
	}
	//alert(dataFor);
	$("#confirmEscalationDialog").dialog({width: 1250,height: 500});
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?mgtFlag=MGT&feedStatus="+status+"&dashFor="+dataFor+"&d_subdept_id="+id+"&dataFlag="+flag+"&location="+level+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
	    success : function(feedbackdata) {
     $("#confirmingEscalation").html(feedbackdata);
	},
	   error: function() {
          alert("error");
      }
	 });
}


