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
	else if(cellvalue=='9')
	{
		return "Transfer Cancel";
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
	var add_date_time= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ADMISSION_DATE');
	var req_date_time=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQUEST_DATE');
	var requested_nursuing_unit=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQUESTED_NURSING_UNIT');
	var req_bed_class= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQUESTED_BED_CLASS');
	var req_bed_no =  jQuery("#gridBedTransfer").jqGrid('getCell',ids,'REQ_BED_NO');
	var tfr_req_status= jQuery("#gridBedTransfer").jqGrid('getCell',ids,'TFR_REQ_STATUS');
	var encounter_id=jQuery("#gridBedTransfer").jqGrid('getCell',ids,'ENCOUNTER_ID');
	
	//alert(encounter_id);
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
	$("#viewTest").dialog({title: 'History View   UHID:'+patient_id+', Patient Name:'+patient_name  ,width: 1000});
	$("#viewTest").dialog('open');

}

function getStatusCounter()
{
	$("#Initiated").empty();
	$("#Accepted").empty();
	$("#Nursing").empty();
	$("#Completed").empty();
	$("#Cancel").empty();
	$.ajax
	({
	type : "post",
	url : "view/Over2Cloud/bedTransfer/fetchCounterStatus.action",
	data:"fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val(),
	/*+"&speciality="+$('#speciality').val()+"&location="+$('#location_search').val()+"&stime="+$('#stime').val()+"&etime="+$('#etime').val()+"&nursingUnit="+$('#nursing_search').val()+"&addDoctor="+$('#admittung_search').val()+"&labName="+$('#lab_search').val(),*/
	success : function(data)
	{
		for (var i = 0; i <= data.length - 1; i++)
		{
			if(data[i].Initiated>0)
				$("#Initiated").html('<div id="count" style="margin-top: 6px;font-size: 14px;"><b>Transfer Request Initiated:'+data[i].Initiated+',</div>');
			if(data[i].Accepted>0)
				$("#Accepted").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Transfer Request Accepted:'+data[i].Accepted+',</div>');
			if(data[i].Nursing>0)
				$("#Nursing").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Transfer Out from Current Nursing Unit:'+data[i].Nursing+',</div>');
			if(data[i].Completed>0)
				$("#Completed").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Transfer Completed:'+data[i].Completed+',</div>');
			if(data[i].Cancel>0)
				$("#Cancel").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Transfer Cancel:'+data[i].Cancel+',</div>');
			
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
