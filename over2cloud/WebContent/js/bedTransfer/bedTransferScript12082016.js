function onloadDataGridView()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/bedTransfer/beforeActivityBoardView.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val(),
	    success : function(data) {
		dataGlobal=data;
       		$("#result_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });

}

function statusMapping(cellvalue, options, rowObject) 
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

}

function patientHistory(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
//	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
	return "<a href='#' title='View Data' onClick='displayHistory("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";

}

function displayHistory(ids)
{
	
	var patient_id =  jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_ID');
			patient_id=$(patient_id).text();
	var patient_name = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'PATIENT_NAME');
	var contact_no = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'CONTACT2_NO');
	var encounter_id = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	var current_bed=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'CURRENT_BED');
	var current_nursuing_unit = jQuery("#gridBedTransfer").jqGrid('getCell',ids,'CURRENT_NURSING_UNIT');
	var add_date_time= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ADMISSION_DATE_TIME');
	var req_date_time=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQUEST_DATE_TIME');
	var requested_nursuing_unit=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQUESTED_NURSING_UNIT');
	var req_bed_class= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQUESTED_BED_CLASS');
	var req_bed_no =  jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQ_BED_NO');
	var tfr_req_status= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'TFR_REQ_STATUS');
	
	
	$("#viewTest").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	url : "view/Over2Cloud/bedTransfer/bedTransferHistory.action?id="+ids+"&patient_id="+patient_id+"&patient_name="+patient_name+"&contact_no="+contact_no+"&encounter_id="+encounter_id+"&current_bed="+current_bed+"&current_nursuing_unit="+current_nursuing_unit+"&req_date_time="+req_date_time+"&requested_nursuing_unit="+requested_nursuing_unit+"&req_bed_class="+req_bed_class+"&req_bed_no="+req_bed_no+"&tfr_req_status="+tfr_req_status+"&add_date_time="+add_date_time,
	 	success : function(data) 
	    {
 			$("#viewTest").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
	$("#viewTest").dialog({title: 'History View',width: 1000,height: 300});
	$("#viewTest").dialog('open');

}



function downloadFeedStatus()
{
	//alert("Hi"+$("#status22").val()+$("#fromDate").val()+"&toDate="+$("#toDate").val());
	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
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
