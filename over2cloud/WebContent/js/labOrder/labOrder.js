getGridView();
getStatusCounter();
function UpdateDropFilters()

{ 
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/labOrder/fetchFilterLocation.action",
	    data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val(),
	    success : function(feeddraft) {
		 $("#locationDiv").html(feeddraft);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/labOrder/fetchFilterNurshingUnit.action",
    data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val(),
    success : function(feeddraft) {
	 $("#nursingDiv").html(feeddraft); 
},
   error: function() {
            alert("error");
        }
 });
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/labOrder/fetchFilterSpeciality.action",
    data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val(),
    success : function(feeddraft) {
	 $("#specialityDiv").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });

 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/labOrder/fetchFilterDoctor.action",
	data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val(),
    success : function(feeddraft) {
	 $("#doctorDiv").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });


$.ajax({
    type : "post",
    url : "view/Over2Cloud/labOrder/fetchFilterLab.action",
	data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val(),
    success : function(feeddraft) {
	 $("#LabNameDiv").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });

}

function getGridView()
{
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/labOrder/viewLabOrderDetails.action",
 		data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&speciality="+$('#speciality').val()+"&location="+$('#location_search').val()+"&stime="+$('#stime').val()+"&etime="+$('#etime').val()+"&nursingUnit="+$('#nursing_search').val()+"&addDoctor="+$('#admittung_search').val()+"&labName="+$('#lab_search').val()+"&sts="+$('#sts_search').val(),
 		success : function(data) 
	    {
			$("#middleDiv").html(data);
		},
	    error: function()
	    {
	        alert("error viewLinsence");
	    }
 	}); 	
}
function getStatusCounter()
{
	$("#InProcess").empty();
	$("#Progress").empty();
	$("#Hold").empty();
	$("#RRA").empty();
	$("#Preliminary").empty();
	$("#Rejected").empty();
	$("#Complete").empty();
	$("#Partial").empty();
	$("#Total").empty();
	$.ajax
	({
	type : "post",
	url : "view/Over2Cloud/labOrder/fetchCounterStatus.action",
	data:"sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&speciality="+$('#speciality').val()+"&location="+$('#location_search').val()+"&stime="+$('#stime').val()+"&etime="+$('#etime').val()+"&nursingUnit="+$('#nursing_search').val()+"&addDoctor="+$('#admittung_search').val()+"&labName="+$('#lab_search').val()+"&sts="+$('#sts_search').val(),
	success : function(data)
	{
		for (var i = 0; i <= data.length - 1; i++)
		{
			if(data[i].InProcess>0)
				$("#InProcess").html('<div id="count" style="margin-top: 6px;font-size: 14px;"><b>In Process:'+data[i].InProcess+',</div>');
			if(data[i].Progress>0)
				$("#Progress").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>In Progress:'+data[i].Progress+',</div>');
			if(data[i].Hold>0)
				$("#Hold").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Hold:'+data[i].Hold+',</div>');
			if(data[i].Preliminary>0)
				$("#Preliminary").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Preliminary:'+data[i].Preliminary+',</div>');
			if(data[i].RRA>0)
				$("#RRA").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Registered:'+data[i].RRA+',</div>');
			if(data[i].Rejected>0)
				$("#Rejected").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Rejected:'+data[i].Rejected+',</div>');
			if(data[i].Complete>0)
				$("#Complete").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"> <b>Completed:'+data[i].Complete+',</div>');
			if(data[i].Partial>0)
				$("#Partial").html('<div id="count" style="margin-top: 6px;font-size: 14px;margin-left:10px;"><b>Partial Completed:'+data[i].Partial+',</div>');
			if(data[i].Total>0)
				$("#Total").html('<div id="count" style="margin-top: 3px;font-size: 18px;margin-left:10px;"><b>Total:'+data[i].Total+'</div>');

		}
	 	 
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}
var hover=0;
function refreshGridSetTime()
{
	if(hover==0){
		setInterval("getGridView();", 5000);
	}
}
//refreshGridSetTime();
function onOverInDiv(){
	hover=1;
}

function onOverOutDiv(){
	hover=0;
}

function historyView(cellvalue, options, row) 
{
	return "<a  href='#' onclick='historyDetails("+row.id+");'><b style='color:blue;cursor: pointer;'>"+cellvalue+"</b></a>";

}

function historyDetails(ids)
{
	var uhid =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'UHID');
	var patName = jQuery("#gridLabOrder").jqGrid('getCell',ids,'Patient_Name');
	var contact = jQuery("#gridLabOrder").jqGrid('getCell',ids,'CONTACT2_NO');
	var bedNo = jQuery("#gridLabOrder").jqGrid('getCell',ids,'BED_NUM');
	var nursingUnit = jQuery("#gridLabOrder").jqGrid('getCell',ids,'WARD');
	var addDoc= jQuery("#gridLabOrder").jqGrid('getCell',ids,'ADMITING_DOCTOR');
	var ordDoc= jQuery("#gridLabOrder").jqGrid('getCell',ids,'ORDERING_DOCTOR');
	var spec =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'ORDERING_DOCTOR_SPECIALTY');
	var specimenNo= jQuery("#gridLabOrder").jqGrid('getCell',ids,'SPECIMEN_NO');
	var specNo= $(jQuery("#gridLabOrder").jqGrid('getCell',ids,'SPECIMEN_NO')).text();
	var orderDate =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'ORD_DATE');
	var orderTime =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'ORD_TIME');
	var specCol =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'SPEC_COLLTD_DATE_TIME');
	var specReg =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'SPEC_REG_DT_TIME');
	var specDis =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'SPEC_DIS_DT_TIME');
	var labName =  jQuery("#gridLabOrder").jqGrid('getCell',ids,'LabName');

		$('#takeActionDialogHistory').dialog('open');
		$('#takeActionDialogHistory').dialog({title:'History Details: <b style="color:blue;cursor: pointer;">'+patName+'</b><b>, Specimen No:</b> <b style="color:blue;cursor: pointer;">'+specimenNo+'</b>',width:1100,height:400});
		$("#resultDiv").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/labOrder/viewHistoryDetails.action",
		    data:"id="+specNo+"&patient_name="+patName+"&specimenNo="+specimenNo+"&contact="+contact+"&addDoctor="+addDoc+"&ordDoctor="+ordDoc+"&uhid="+uhid+"&orderDate="+orderDate+"&admitting_speciality="+spec+"&bedNo="+bedNo+"&nursingUnit="+nursingUnit+"&orderTime="+orderTime+"&specCol="+specCol+"&specReg="+specReg+"&specDis="+specDis+"&labName="+labName,
		    success : function(subdeptdata) 
		   {
				$("#"+"resultDiv").html(subdeptdata);
		   },
		   error: function() 
		   {
	           alert("error");
	      }
		 });
}
