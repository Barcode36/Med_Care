 
function getGridView()
{
	flagForDownload='patient_view';
	getStatusCounter();
	var total=0; 
	 var temp="0";
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/LongPatientStay/viewActivityBoardDetail.action",
		data: "fromDays="+$('#fromDays').val()+"&toDays="+$('#toDays').val()+"&location="+$('#location_search').val()+"&nursing="+$('#nursing_search').val()+"&range="+$('#range').val()+"&patDays="+$('#patDays').val()+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val()+"&doctor="+$('#admittung_search').val()+"&speciality="+$('#speciality').val(),
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
function getGridViewForTicket()
{
	flagForDownload='ticket_view';
	var total=0; 
	 var temp="0";
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/LongPatientStay/viewActivityBoardDetailForTicket.action",
		data: "location="+$('#location_search_ticket').val()+"&nursing="+$('#nursing_search_ticket').val()+"&sdate="+$('#startDate').val()+"&edate="+$('#endDate').val()+"&doctor="+$('#admittung_search_ticket').val()+"&speciality="+$('#speciality_ticket').val(),
 		success : function(data) 
	    {
			$("#middleDiv").html(data);
			getStatusCounterForTicket();
			
			 
		},
	    error: function()
	    {
	        alert("error viewLinsence");
	    }
 	}); 	
}
function getStatusCounterForTicket()
{
	$("#pending").empty();
	$("#close").empty();
	$("#parked").empty();
	$("#total").empty();
	$.ajax
	({
	type : "post",
	url : "view/Over2Cloud/LongPatientStay/fetchCounterStatusForTicket.action",
	data: "location="+$('#location_search_ticket').val()+"&nursing="+$('#nursing_search_ticket').val()+"&sdate="+$('#startDate').val()+"&edate="+$('#endDate').val()+"&doctor="+$('#admittung_search_ticket').val()+"&speciality="+$('#speciality_ticket').val(),
	success : function(data)
	{
		for (var i = 0; i <= data.length - 1; i++)
		{
			if(data[i].pending>0)
				$("#pending").html('<div id="count1" style="margin-top: -1px;font-size: 14px;"><b>Pending:'+data[i].pending+',</div>');
			else
				$("#pending").html('<div id="count1" style="margin-top: -1px;font-size: 14px;"><b>Pending:0,</div>');
			if(data[i].close>0)
				$("#close").html('<div id="count2" style="margin-top: -1px;font-size: 14px;margin-left:10px;"><b>Close:'+data[i].close+',</div>');
			else
				$("#close").html('<div id="count2" style="margin-top: -1px;font-size: 14px;margin-left:10px;"><b>Close:0,</div>');
			
			if(data[i].parked>0)
				$("#parked").html('<div id="count2" style="margin-top: -1px;font-size: 14px;margin-left:10px;"><b>Parked:'+data[i].parked+',</div>');
			else
				$("#parked").html('<div id="count2" style="margin-top: -1px;font-size: 14px;margin-left:10px;"><b>Parked:0,</div>');
	
			if(data[i].total>0)
				$("#total").html('<div id="count3" style="margin-top: -2px;font-size: 18px;margin-left:10px;"><b>Total:'+data[i].total+'</div>');
			else
				$("#total").html('<div id="count3" style="margin-top: -2px;font-size: 18px;margin-left:10px;"><b>Total:0</div>');
			 
		}
	 	 
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}
 
function UpdateDropFilters()
 
{ 
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/LongPatientStay/fetchFilterLocation.action?fromDays="+$('#fromDays').val()+"&toDays="+$('#toDays').val()+"&range="+$('#range').val()+"&patDays="+$('#patDays').val()+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val(),
	    success : function(feeddraft) {
		 $("#locationDiv").html(feeddraft);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/LongPatientStay/fetchFilterNurshingUnit.action?fromDays="+$('#fromDays').val()+"&toDays="+$('#toDays').val()+"&range="+$('#range').val()+"&patDays="+$('#patDays').val()+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val(),
    success : function(feeddraft) {
	 $("#nursingDiv").html(feeddraft); 
},
   error: function() {
            alert("error");
        }
 });
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/LongPatientStay/fetchFilterSpeciality.action?fromDays="+$('#fromDays').val()+"&toDays="+$('#toDays').val()+"&range="+$('#range').val()+"&patDays="+$('#patDays').val()+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val(),
    success : function(feeddraft) {
	 $("#specialityDiv").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });

 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/LongPatientStay/fetchFilterDoctor.action?fromDays="+$('#fromDays').val()+"&toDays="+$('#toDays').val()+"&range="+$('#range').val()+"&patDays="+$('#patDays').val()+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val(),
    success : function(feeddraft) {
	 $("#doctorDiv").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });

}



function UpdateDropFiltersForTicket()

{ 
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/LongPatientStay/fetchFilterLocationTicket.action?location="+$('#location_search_ticket').val()+"&nursing="+$('#nursing_search_ticket').val()+"&sdate="+$('#startDate').val()+"&edate="+$('#endDate').val()+"&doctor="+$('#admittung_search_ticket').val()+"&speciality="+$('#speciality_ticket').val(),
	    success : function(feeddraft) {
		 $("#locationDivTicket").html(feeddraft);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/LongPatientStay/fetchFilterNurshingUnitTicket.action?location="+$('#location_search_ticket').val()+"&nursing="+$('#nursing_search_ticket').val()+"&sdate="+$('#startDate').val()+"&edate="+$('#endDate').val()+"&doctor="+$('#admittung_search_ticket').val()+"&speciality="+$('#speciality_ticket').val(),
    success : function(feeddraft) {
	 $("#nursingDivTicket").html(feeddraft); 
},
   error: function() {
            alert("error");
        }
 });
 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/LongPatientStay/fetchFilterSpecialityTicket.action?location="+$('#location_search_ticket').val()+"&nursing="+$('#nursing_search_ticket').val()+"&sdate="+$('#startDate').val()+"&edate="+$('#endDate').val()+"&doctor="+$('#admittung_search_ticket').val()+"&speciality="+$('#speciality_ticket').val(),
    success : function(feeddraft) {
	 $("#specialityDivTicket").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });

 
$.ajax({
    type : "post",
    url : "view/Over2Cloud/LongPatientStay/fetchFilterDoctorTicket.action?location="+$('#location_search_ticket').val()+"&nursing="+$('#nursing_search_ticket').val()+"&sdate="+$('#startDate').val()+"&edate="+$('#endDate').val()+"&doctor="+$('#admittung_search_ticket').val()+"&speciality="+$('#speciality_ticket').val(),
    success : function(feeddraft) {
	 $("#doctorDivTicket").html(feeddraft);
},
   error: function() {
            alert("error");
        }
 });

}
 
function getStatusCounter()
{
	$.ajax
	({
	type : "post",
	url : "view/Over2Cloud/LongPatientStay/fetchCounterStatus.action?fromDays="+$('#fromDays').val()+"&toDays="+$('#toDays').val()+"&range="+$('#range').val()+"&patDays="+$('#patDays').val()+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val()+"&doctor="+$('#admittung_search').val()+"&speciality="+$('#speciality').val()+"&location="+$('#location_search').val()+"&nursing="+$('#nursing_search').val(),
	async:false,
	success : function(data)
	{
	if(data.length>0){
	var total=0;
	$("#statusCounter").empty();
	var temp="";
	if(data[0].Total>0)
	{
		temp+='<div id="count" style="margin-top: -12px;"><h1 style="font-size: 18px;">   '+data[0].Total+'  </h1></div>';
	}
	 
	$("#statusCounter").append(temp);
	}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}
/*function downloadExcelActivity1()
{
	
	var fromDays=$("#fromDays").val();
	var toDays=$("#toDays").val();
	var range=$("#range").val();
	var nursing=$("#nursing_search").val();
	var location=$("#location_search").val();
    var patDays=$("#patDays").val();
	$("#downloadExcelActivity").dialog({title: 'Check Column',width: 350,height: 600});
	$("#downloadExcelActivity").dialog('open');
	$("#downloadExcelActivity").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	url : "view/Over2Cloud/LongPatientStay/downloadExcelActivity.action",
	 	data:"fromDays="+fromDays+"&toDays="+toDays+"&range="+range+"&location="+location+"&patDays="+patDays+"&nursing="+nursing+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val()+"&doctor="+$('#admittung_search').val()+"&speciality="+$('#speciality').val(),
	 	success : function(data) 
	    {
 			$("#downloadExcelActivity").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
	
	setTimeout(function(){ 	$("#downloadExcelActivity").dialog('open'); }, 3000);
}*/

function downloadExcelActivity1()
{
	$("#downloadExcelActivity").dialog({title: 'Check Column',width: 350,height: 600});
	$("#downloadExcelActivity").dialog('open');
	$("#downloadExcelActivity").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(flagForDownload=='patient_view')
	{
		var fromDays=$("#fromDays").val();
		var toDays=$("#toDays").val();
		var range=$("#range").val();
		var nursing=$("#nursing_search").val();
		var location=$("#location_search").val();
	    var patDays=$("#patDays").val();
		$.ajax({
		    type : "post",
		 	url : "view/Over2Cloud/LongPatientStay/downloadExcelActivity.action",
		 	data:"fromDays="+fromDays+"&toDays="+toDays+"&range="+range+"&location="+location+"&patDays="+patDays+"&nursing="+nursing+"&sdate="+$('#sdate').val()+"&edate="+$('#edate').val()+"&rangeForDate="+$('#rangeForDate').val()+"&patDaysForDate="+$('#patDaysForDate').val()+"&fromDaysForDate="+$('#fromDaysForDate').val()+"&toDaysForDate="+$('#toDaysForDate').val()+"&doctor="+$('#admittung_search').val()+"&speciality="+$('#speciality').val(),
		 	success : function(data) 
		    {
	 			$("#downloadExcelActivity").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(flagForDownload='ticket_view')
	{
		var fromDays=$("#startDate").val();
		var toDays=$("#endDate").val();
		var nursing=$("#nursing_search_ticket").val();
		var location=$("#location_search_ticket").val();
		var speciality=$("#speciality_ticket").val();
		var admitDoctor=$("#admittung_search_ticket").val();
		$.ajax({
		    type : "post",
		 	url : "view/Over2Cloud/LongPatientStay/downloadExcelActivityTicket.action",
		 	data:"fromDays="+fromDays+"&toDays="+toDays+"&location="+location+"&nursing="+nursing+"&speciality="+speciality+"&doctor="+admitDoctor,
		 	success : function(data) 
		    {
	 			$("#downloadExcelActivity").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
		
	}
	
	
	setTimeout(function(){ 	$("#downloadExcelActivity").dialog('open'); }, 3000);
}

function reloadPage()
{
	var grid = $("#gridLongPatientStay");
	grid.trigger("reloadGrid",[{current:true}]);
}
function showHideMyDiv(opt)
{
	$("#patDays").val("");
	$("#patDaysForDate").val("");
	$("#sdate").val("");
	$("#edate").val("");
	$("#fromDays").val("");
	$("#toDays").val("");
	 if(opt=='Other')
	 {
		 
		$("#patDays").hide();
		$("#fromDays").show();
		$("#toDays").show();
		$("#patDaysForDate").hide();
		$("#rangeForDate").hide();
		$("#datePickerStatus").hide();
		$("#fromDaysForDate").hide();
		$("#toDaysForDate").hide();
	}
	 else if(opt=='DateRange')
	 {
		 
		 	$("#patDays").hide();
			$("#fromDays").hide();
			$("#toDays").hide();
			$("#patDaysForDate").show();
			$("#rangeForDate").show();
			$("#datePickerStatus").show();
			$("#fromDaysForDate").hide();
			$("#toDaysForDate").hide();
			 
	 }
	 else
	 {
		  
		$("#patDays").show();
		$("#fromDays").hide();
		$("#toDays").hide();
		$("#patDaysForDate").hide();
		$("#rangeForDate").hide();
		$("#datePickerStatus").hide();
		$("#fromDaysForDate").hide();
		$("#toDaysForDate").hide();
	}
}
function showHideMyDivForDate(opt)
{
	$("#patDaysForDate").val("");
	$("#fromDaysForDate").val("");
	$("#toDaysForDate").val("");
	 if(opt=='OtherForDate')
	 {
		$("#patDaysForDate").hide();
		$("#fromDaysForDate").show();
		$("#toDaysForDate").show();
	}
	 else
	 {
			$("#patDaysForDate").show();
			$("#fromDaysForDate").hide();
			$("#toDaysForDate").hide();
	}
}

$.subscribe('validateActionForLongPat', function(event,data)
		{
						var mystring=null;
					 if($("#actionStatus").val()!='' && $("#actionStatus").val()=='Snooze')
					{
						mystring=$(".sZpIds").text();
					}
					else if($("#actionStatus").val()!='' && $("#actionStatus").val()=='Close')
					{
						mystring=$(".repIds").text();
					}
				    var fieldtype = mystring.split(",");
				    var pattern = /^\d{10}$/;
				    for(var i=0; i<fieldtype.length; i++)
				    {
				        var fieldsvalues = fieldtype[i].split("#")[0];
				        var fieldsnames = fieldtype[i].split("#")[1];
				        var colType = fieldtype[i].split("#")[2]; 
				        var validationType = fieldtype[i].split("#")[3];
				    //    alert(fieldsvalues+"  "+fieldsnames+" "+colType+"  "+$("#"+fieldsvalues).val());
				        $("#"+fieldsvalues).css("background-color","");
				        errZone1.innerHTML="";
				        if(fieldsvalues!= "" )
				        {
				            if(colType=="D")
				            {
					            if(($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1"))
					            {
					            	//alert("sdkfhkldshh");
						            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
						            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
						            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
						            $("#"+fieldsvalues).focus();
						            $("#"+fieldsvalues).css("background-color","#ff701a");
						            event.originalEvent.options.submit = false;
						            break;   
					            }
					            
					            
				            }
				            else if(colType =="T")
				            {
					            if(validationType=="n")
					            {
						            var numeric= /^[0-9]+$/;
						            if(!(numeric.test($("#"+fieldsvalues).val())))
							        {
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;
							         }   
					             }
					            else if(validationType=="an")
					            {
						            var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
						            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
							        {
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;
						            }
					            }
					            
					            else if(validationType=="anl")
					            {
						            var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
						            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
							        {
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;
						            }
						            
						            
					            }
					            else if(validationType=="a")
					            {
						            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val())))
						            {
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;    
						             }
					            }
					            else if(validationType=="m")
					            {
						            if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
						            {
						                errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
						                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
						                $("#"+fieldsvalues).focus();
						                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
						                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
						                event.originalEvent.options.submit = false;
						                break;
						            }
						            else if (!pattern.test($("#"+fieldsvalues).val()))
						            {
						                errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
						                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
						                $("#"+fieldsvalues).focus();
						                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
						                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
						                event.originalEvent.options.submit = false;
						                break;
						             }
						             else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
						             {
						                errZone1.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
						                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
						                $("#"+fieldsvalues).focus();
						                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
						                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
						                event.originalEvent.options.submit = false;
						                break;
						             }
						          }
					              else if(validationType =="e")
					              {
					            	 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
					            	 {
					            	 }
					            	 else
					            	 {
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
							            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
							            $("#"+fieldsvalues).focus();
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            event.originalEvent.options.submit = false;
							            break;
					            	 }
					              }
					              else if(validationType =="w")
					              {
					              }
					              else if(validationType =="sc"){
					            	  if($("#"+fieldsvalues).val().length < 1)
							             {
							                errZone1.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
							                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
							                $("#"+fieldsvalues).focus();
							                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							                event.originalEvent.options.submit = false;
							                break;
							             }
					             }
				             	}
					            else if(colType=="TextArea")
					            {
						            if(validationType=="an")
						            {
							            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
							            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
							            {
								            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
								            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
								            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
								            $("#"+fieldsvalues).focus();
								            $("#"+fieldsvalues).css("background-color","#ff701a");
								            event.originalEvent.options.submit = false;
							                break;
							            }
						            }
						            else if(validationType=="mg")
						            {
						            }   
					            }
					            else if(colType=="Time")
					            {
					            	if($("#"+fieldsvalues).val()=="")
					            	{
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;   
					            	}   
					            }
					            else if(colType=="Date")
					            {
					            	if($("#"+fieldsvalues).val()=="")
					            	{
							            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
							            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
							            $("#"+fieldsvalues).focus();
							            $("#"+fieldsvalues).css("background-color","#ff701a");
							            event.originalEvent.options.submit = false;
							            break;   
					            	}
					            }
				        	}
				    	} 
				    
				    
				    if(event.originalEvent.options.submit != false)
				    {
				    	  
			 	    }
				});
 