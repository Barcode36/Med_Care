function onloadDataGridView()
{
	var status=$("#status_search").val();
	var level=$("#level_search").val();
	var searchStr=$("#searchStr").val();
	//alert(status+"................"+level);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/dischargeAnnounce/beforeActivityBoardView.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&status="+status+"&level="+level+"&searchStr="+searchStr,
	    success : function(data) {
		dataGlobal=data;
       		$("#result_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });

}

/*function statusMapping(cellvalue, options, rowObject) 
{
	if(cellvalue=='0')
	{
		return "Transfer Request Initiated";
	}
	else if(cellvalue=='1')
	{
		return "Transfer Request Accepted";
	}
	else if(cellvalue=='2')
	{
		return "Transfer Request Accepted";
	}
	else if(cellvalue=='3')
	{
		return "Transfer Out from Current Nursing Unit";
	}
	else
	{
		return "Transfer Completed";
	}

}*/

function patientId(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
//	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
	return "<a href='#' title='View History' onClick='displayHistory("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";

}

function displayHistory(ids)
{
	var historyFor="both";
	//alert(historyFor);
	var patient_id =  jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_ID');
			patient_id=$(patient_id).text();
	var patient_name = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_NAME');
	var encounter_id = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var current_bed=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BED_NUM');
	var current_nursuing_unit = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'NURSING_UNIT');
	var add_date_time= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ADMISSION_DATE_TIME');
	var PRACTITIONER_NAME= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PRACTITIONER_NAME');
	var BL_LEVEL= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BL_LEVEL');
	var MT_LEVEL= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'MT_LEVEL');
	var BLNG_GRP_CUST_CODE= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BLNG_GRP_CUST_CODE');

	
	
	var event_status=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'EVENT_STATUS');
	var encounter_id=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var mt_ticket_no=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'MT_TICKET_NO');
			mt_ticket_no=$(mt_ticket_no).text();
	var bl_ticket_no=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BL_TICKET_NO');
			bl_ticket_no=$(bl_ticket_no).text();
	
	//alert(mt_ticket_no+"...."+event_status);
	
	//alert(encounter_id);
	$("#viewTest").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	url : "view/Over2Cloud/dischargeAnnounce/announceCommonHistory.action?id="+ids+"&patient_id="+patient_id+"&patient_name="+patient_name+"&encounter_id="+encounter_id+"&current_bed="+current_bed+"&current_nursuing_unit="+current_nursuing_unit+"&add_date_time="+add_date_time+"&event_status="+event_status+"&mt_ticket_no="+mt_ticket_no+"&bl_ticket_no="+bl_ticket_no+"&PRACTITIONER_NAME="+PRACTITIONER_NAME+"&BLNG_GRP_CUST_CODE="+BLNG_GRP_CUST_CODE+"&MT_LEVEL="+MT_LEVEL+"&BL_LEVEL="+BL_LEVEL+"&historyFor="+historyFor,
	 	success : function(data) 
	    {
 			$("#viewTest").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
	$("#viewTest").dialog({title: 'History View   UHID:'+patient_id+', Patient Name:'+patient_name  ,width: 1000});
	$("#viewTest").dialog('open');

}

function displayHistoryBilling(ids)
{
	var historyFor="BILLING";
	//alert(historyFor);
	var patient_id =  jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_ID');
			patient_id=$(patient_id).text();
	var patient_name = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_NAME');
	var encounter_id = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var current_bed=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BED_NUM');
	var current_nursuing_unit = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'NURSING_UNIT');
	var add_date_time= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ADMISSION_DATE_TIME');
	var PRACTITIONER_NAME= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PRACTITIONER_NAME');
	var BL_LEVEL= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BL_LEVEL');
	var MT_LEVEL= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'MT_LEVEL');
	var BLNG_GRP_CUST_CODE= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BLNG_GRP_CUST_CODE');
	var event_status=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'EVENT_STATUS');
	var encounter_id=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var mt_ticket_no=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'MT_TICKET_NO');
			mt_ticket_no=$(mt_ticket_no).text();
	var bl_ticket_no=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BL_TICKET_NO');
			bl_ticket_no=$(bl_ticket_no).text();
	
	//alert(mt_ticket_no+"...."+event_status);
	
	//alert(encounter_id);
	$("#viewTest").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
		url : "view/Over2Cloud/dischargeAnnounce/announceCommonHistory.action?id="+ids+"&patient_id="+patient_id+"&patient_name="+patient_name+"&encounter_id="+encounter_id+"&current_bed="+current_bed+"&current_nursuing_unit="+current_nursuing_unit+"&add_date_time="+add_date_time+"&event_status="+event_status+"&mt_ticket_no="+mt_ticket_no+"&bl_ticket_no="+bl_ticket_no+"&PRACTITIONER_NAME="+PRACTITIONER_NAME+"&BLNG_GRP_CUST_CODE="+BLNG_GRP_CUST_CODE+"&MT_LEVEL="+MT_LEVEL+"&BL_LEVEL="+BL_LEVEL+"&historyFor="+historyFor,
	 	success : function(data) 
	    {
 			$("#viewTest").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
	$("#viewTest").dialog({title: 'History View   UHID:'+patient_id+', Patient Name:'+patient_name  ,width: 1000});
	$("#viewTest").dialog('open');

}

function displayHistoryMT(ids)
{
	var historyFor="MT";
	//alert(historyFor);
	var patient_id =  jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_ID');
			patient_id=$(patient_id).text();
	var patient_name = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_NAME');
	var encounter_id = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var current_bed=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BED_NUM');
	var current_nursuing_unit = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'NURSING_UNIT');
	var add_date_time= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ADMISSION_DATE_TIME');
	var PRACTITIONER_NAME= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PRACTITIONER_NAME');
	var BL_LEVEL= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BL_LEVEL');
	var MT_LEVEL= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'MT_LEVEL');
	var BLNG_GRP_CUST_CODE= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BLNG_GRP_CUST_CODE');
	var event_status=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'EVENT_STATUS');
	var encounter_id=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var mt_ticket_no=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'MT_TICKET_NO');
			mt_ticket_no=$(mt_ticket_no).text();
	var bl_ticket_no=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'BL_TICKET_NO');
			bl_ticket_no=$(bl_ticket_no).text();
	
	//alert(mt_ticket_no+"...."+event_status);
	
	//alert(encounter_id);
	$("#viewTest").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
		url : "view/Over2Cloud/dischargeAnnounce/announceCommonHistory.action?id="+ids+"&patient_id="+patient_id+"&patient_name="+patient_name+"&encounter_id="+encounter_id+"&current_bed="+current_bed+"&current_nursuing_unit="+current_nursuing_unit+"&add_date_time="+add_date_time+"&event_status="+event_status+"&mt_ticket_no="+mt_ticket_no+"&bl_ticket_no="+bl_ticket_no+"&PRACTITIONER_NAME="+PRACTITIONER_NAME+"&BLNG_GRP_CUST_CODE="+BLNG_GRP_CUST_CODE+"&MT_LEVEL="+MT_LEVEL+"&BL_LEVEL="+BL_LEVEL+"&historyFor="+historyFor,
	 	success : function(data) 
	    {
 			$("#viewTest").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
	$("#viewTest").dialog({title: 'History View   UHID:'+patient_id+', Patient Name:'+patient_name  ,width: 1000});
	$("#viewTest").dialog('open');

}


function getStatusCounter()
{
	$("#Initiated").empty();
	$("#Accepted").empty();
	$("#Nursing").empty();
	$("#Completed").empty();
	$("#Total").empty();
	$.ajax
	({
	type : "post",
	url : "view/Over2Cloud/dischargeAnnounce/fetchCounterStatus.action",
	data:"fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val(),
	/*+"&speciality="+$('#speciality').val()+"&location="+$('#location_search').val()+"&stime="+$('#stime').val()+"&etime="+$('#etime').val()+"&nursingUnit="+$('#nursing_search').val()+"&addDoctor="+$('#admittung_search').val()+"&labName="+$('#lab_search').val(),*/
	success : function(data)
	{
		for (var i = 0; i <= data.length - 1; i++)
		{
			if(data[i].Open>0)
				$("#Open").html('<div id="count" style="margin-top: 6px;font-size: 14px;"><b>Open Ticket:'+data[i].Open+',</div>');
			if(data[i].Close>0)
				$("#Close").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Closed Ticket:'+data[i].Close+',</div>');
			if(data[i].Total>0)
				$("#Total").html('<div id="count" style="margin-top: 6px;font-size: 18px;margin-left:10px;"><b>Total:'+data[i].Total+'</div>');
			
		}
	 	 
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}



function MTTicket(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
//	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
	return "<a href='#' title='View History' onClick='displayHistoryMT("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";

}

function BLTicket(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
//	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
	return "<a href='#' title='View History' onClick='displayHistoryBilling("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";

}


function tat_MT_FM(cellvalue, options, row)
{
       //alert(row.escalation_date);
       if(row.MT_FM_TAT == '00:00' )
       {
               return "<a href='#' title='Out of TAT' style='font-weight:bold' class='blink_me'><font color='RED' >"+cellvalue+"</font></a>";
       }
       
       
       else
               {
               return "<a href='#' title='' style='color:green' >"+cellvalue+"</font></a>";
               }
       
}


function tat_BL(cellvalue, options, row)
{
       //alert(row.escalation_date);
       if(row.BL_TAT == '00:00' )
       {
               return "<a href='#' title='Out of TAT' style='font-weight:bold' class='blink_me'><font color='RED' >"+cellvalue+"</font></a>";
       }
       
       
       else
               {
               return "<a href='#' title='' style='color:green' >"+cellvalue+"</font></a>";
               }
       
}







function downloadFeedStatus()
{
	//alert("Hi"+$("#status22").val()+$("#fromDate").val()+"&toDate="+$("#toDate").val());
	$("#takeActionGrid").dialog({title: 'Check Column',width: 500,height: 400});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	url : "view/Over2Cloud/CallDetail/beforeCallDetailDownload.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val(),
	 	success : function(data) 
	    {
 			$("#takeActionGrid").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
}
