
 

function fetchReolveBy(){
	var targetid="resolveBy";
	$.ajax({
		type :"post",
		url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/fetchResolveEmpList.action",
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Technician"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}
function chk1(val, divId){
	if(val=='1'){
		$('#nerReqst').show();
		$.ajax({
		    type : "post",
		    async:false,
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchService.action",
		    success : function(data) 
			{  
				 $("#serviceID").html(data);
			},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else{
		$('#nerReqst').hide();
	}
}

function selectServices(value, divId)
{ 
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/selectServices.action?service_id = "+value,
	    success : function(data) {
	   data = data.servicelocation;
	  
	    if(data != null){
		    $('#'+divId+' option').remove();
		   $('#'+divId).append($("<option></option>").attr("value",-1).text('Select Location'));
		   $(data).each(function(index)
		   {
		   $('#'+divId).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
		  });
	}
		
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getModality2(serviceId,location,div){
	loc=$("#"+location+" option:selected").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchModality.action?servicesID="+serviceId+"&med_location="+loc,
	    success : function(data) {
		  $('#'+div+' option').remove();
		$('#'+div).append($("<option></option>").attr("value",-1).text('Select Modality'));
    	$(data).each(function(index)
		{
		   $('#'+div).append($("<option></option>").attr("value",this.id).text(this.Name));
		});
		
	},
	   error: function() {
            alert("error");
        }
	 });
}
function fetchBedType2(serviceId,location,div)
{
	loc=$("#"+location+" option:selected").val();
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchBedType.action?servicesID="+serviceId+"&med_location="+loc,
		    success : function(data) {
			  $('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Bed Type'));
	    	$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.id).text(this.Name));
			});
			
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function serviceFieldsss(sourceId, locId){
	//alert("sourceId :"+sourceId+" locId :"+locId);
	var serviceId=$("#"+sourceId+" option:selected").val();
	var locationId=$("#"+locId+" option:selected").val();
	var serviceFiledNameId = serviceId ;
	$("#packIDH").show();
	if(serviceId== '1'){
		serviceFiledNameId= 'EHC';
	}
	else if(serviceId== '2'){
		serviceFiledNameId= 'IPD';
		fetchBedType2(serviceId,'location','IPD_bed_typeF');
	}
	else if(serviceId== '3'){
		serviceFiledNameId= 'Radiology';
		getModality2(serviceId,'location','radiology_modalityF');
	}
	else if(serviceId== '4'){
		serviceFiledNameId= 'OPD';
	}
	else if(serviceId== '5'){
		serviceFiledNameId= 'DayCare';
	}
	/*else if(serviceId== '6'){
		serviceFiledNameId= 'Laboratory';
	}*/
	else if(serviceId== '7'){
		serviceFiledNameId= 'Emergency';
		getModality2(serviceId,'location','assistanceF');
	}
	else if(serviceId== '8'){
		serviceFiledNameId= 'Diagnostics';
		getModality2(serviceId,'location','Diagnostics_testID');
	}
	else if(serviceId== '9'){
		serviceFiledNameId= 'New Information Request';
	}
	else if(serviceId== '10'){
		serviceFiledNameId= 'Facilitation';
		getModality2(serviceId,'location','facilitationF');
	}
	else if(serviceId== '11'){
		serviceFiledNameId= 'Telemedicine';
		getModality2(serviceId,'location','telemedicineF');
	}

	if(serviceFiledNameId == "EHC")
	{
		var x = document.getElementById("EHCDivL1").innerHTML;
		$("#addFieldL1").html(x);

		$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");

		$('#'+'RadioF').html("");
		$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

		$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		
		$('#'+'EHCp').html("EHC_package_typeF#EHC Packages#D#D,");
		$('#'+'EHCp1').html("ehc_pack_serviceF#Packages#D#D,");
	   
	}else if(serviceFiledNameId == "OPD")
	{
		
	   var x = document.getElementById("OPDDivL1").innerHTML;
	   	$("#addFieldL1").html(x);
	   	specDOCGet('OPD_specialtyCC');

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");

		$('#'+'RadioF').html("");
		$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

		$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		
	   	$('#'+'OPDF').html("OPD_specialtyCC#Speciality#D#D,");
		$('#'+'OPDF1').html("OPD_doctor#Doctor Name#D#D,");
		
	}else if(serviceFiledNameId == "Radiology")
	{
	   var x = document.getElementById("RadiologyDivL1").innerHTML;  
	   	$("#addFieldL1").html(x);

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");
 
	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");

		$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");
		
		$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		
	   	$('#'+'RadioF').html("radiology_modalityF#Modality#D#D,");
	}
	
	else if(serviceFiledNameId == "Facilitation")
	{
	   var x = document.getElementById("FacilitationDivL1").innerHTML;  
	   	$("#addFieldL1").html(x);

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");
 
	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");

		$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		$('#'+'RadioF').html("");
		$('#'+'TelemedicineF').html("");
		
	   	$('#'+'FacilitationF').html("facilitationF#Facilitation For#D#D,");
	}
	else if(serviceFiledNameId == "Telemedicine")
	{
	   var x = document.getElementById("TelemedicineDivL1").innerHTML;  
	   	$("#addFieldL1").html(x);

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");
 
	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");

		$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		$('#'+'RadioF').html("");
		$('#'+'FacilitationF').html("");
	 	
	   	$('#'+'TelemedicineF').html("telemedicineF#Telemedicine For#D#D,");
	}
	
	else if(serviceFiledNameId == "IPD")
	{
	   var x = document.getElementById("IPDDivL1").innerHTML;
	   	$("#addFieldL1").html(x);
	   	specDOCGet('IPD_specialtyIPD');

		$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");
 
	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");
		
	   	$('#'+'RadioF').html("");
	   	
	   	$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

	   	$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		
	   	$('#'+'IPDFS').html("IPD_specialtyIPD#Speciality#D#D,");
		$('#'+'IPDFD').html("IPD_doctor#Doctor Name#D#D,");
		$('#'+'IPDFB').html("IPD_bed_typeF#Bed Type#D#D,");
		$('#'+'IPDPF').html("IPD_payment_typeF#Payment Type#D#D,");

	}
	else if(serviceFiledNameId == "DayCare")
	{
	   var x = document.getElementById("DayCareDivL1").innerHTML;
	   	$("#addFieldL1").html(x);
	   	specDOCGet('Day_care_specialtyDAY');
	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");

	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");
		
	   	$('#'+'RadioF').html("");
	   	$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

	   	$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		
		$('#'+'DayCareFS').html("Day_care_specialtyDAY#Speciality#D#D,");
		$('#'+'DayCareFD').html("Day_care_doctorD#Doctor Name#D#D,");
	}
	else if(serviceFiledNameId == "Laboratory")
	{
		
	   var x = document.getElementById("LaboratoryDivL1").innerHTML;
	  	$("#addFieldL1").html(x);

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");

	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");
		
	   	$('#'+'RadioF').html("");
	   	$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

	   	$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
		
		$('#'+'LaboratoryFM').html("laboratory_modality#Modality#D#D,");
		
	   
	}
	else if(serviceFiledNameId == "Emergency")
	{
	   var x = document.getElementById("EmergencyDivL1").innerHTML;
	   	$("#addFieldL1").html(x);
	   	specDOCGet('emergency');

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");

	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");
		
	   	$('#'+'RadioF').html("");
	   	$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

	   	$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'DiagnosticsFD').html("");
		
		$('#'+'EmergencyFS').html("emergency#Speciality#D#D,");
		$('#'+'EmergencyFE').html("assistanceF#Emergency#D#D,");
		
	}
	else if(serviceFiledNameId == "Diagnostics")
	{
	   var x = document.getElementById("DiagnosticsDivL1").innerHTML;
	   	$("#addFieldL1").html(x);

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");

	   	$('#'+'OPDF').html("");
	   	
		$('#'+'OPDF1').html("");
		
	   	$('#'+'RadioF').html("");
	   	$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

	   	$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("Diagnostics_testID#Diagnostics Test#D#D,");
	}
	else if(serviceFiledNameId == "New Information Request")
	{
	   var x = document.getElementById("NewServiceDiv12").innerHTML;
	   	$("#addFieldL1").html(x);

	   	$('#'+'EHCp').html("");
		$('#'+'EHCp1').html("");

	   	$('#'+'OPDF').html("");
		$('#'+'OPDF1').html("");
		
	   	$('#'+'RadioF').html("");
	   	$('#'+'FacilitationF').html("");
		$('#'+'TelemedicineF').html("");

	   	$('#'+'IPDFS').html("");
		$('#'+'IPDFD').html("");
		$('#'+'IPDFB').html("");
		$('#'+'IPDPF').html("");

		$('#'+'DayCareFS').html("");
		$('#'+'DayCareFD').html("");

		$('#'+'LaboratoryFM').html("");

		$('#'+'EmergencyFS').html("");
		$('#'+'EmergencyFE').html("");

		$('#'+'DiagnosticsFD').html("");
	}
	
	
}

function chk(val, divId){
	if(val=='1'){
		$('#nerReqst').show();
		$('#nerReqstP').show();
		$('#'+'parkedTill1').html("");
		$('#'+'locationSpan').html("location#Location#D#D,");
		$('#'+'serviceSpan').html("service#Service#D#D,");
		$('#'+'PreSe').html("preferred_time_date#Preferred Time#T#sc,");
	}
	else{
		$('#nerReqst').hide();
		$('#nerReqstP').hide();
		$("#packIDH").hide();
		$('#'+'PreSe').html("");
		$('#'+'parkedTill1').html("");
		$('#'+'locationSpan').html("");
		$('#'+'serviceSpan').html("");
	}
}
function changeDiv(data){
	
if(data=="Service In"){
	$('#'+'locationSpan').html("");
	$('#'+'serviceSpan').html("");
	$('#'+'resolveBy1').html("");
	$('#'+'PreSe').html("");
	$('#'+'parkedTill1').html("");
	$("#snooze").hide();
	$("#resolve").hide();
	$("#packIDH").hide();
	$("#newRequest").hide();
	$('#nerReqstP').hide();
	$('#nerReqst').hide();
	$("#seerviceManager").hide();
	$('#checkSMS').hide();
	if(document.getElementById("uhidCheck").value.length == 10)
	{
		
	}
	else
	{
		document.getElementById("uhidCheck").value = "MM";
	}
}
if(data=="Service Out"){
	$("#close").show();
	$('#'+'locationSpan').html("");
	$('#'+'serviceSpan').html("");
	$('#'+'PreSe').html("");
	$('#'+'parkedTill1').html("");
	$('#'+'resolveBy1').html("resolved_by#Resolve By#D#D,");
	$("#closeR").hide();
	$("#resolve").show();
	$("#newRequest").show();
	$("#snooze").hide();
	$("#packIDH").show();
	$("#seerviceManager").hide();
	$('#checkSMS').hide();
	if(document.getElementById("uhidCheck").value.length == 10)
	{
		
	}
	else
	{
		checkuhidOfAction1('MM');
	}
	
	fetchReolveBy();
	
}
if(data=="Re-Scheduled"){
	$('#'+'resolveBy1').html("");
	$('#'+'parkedTill1').html("");
	$("#close").hide();
	$("#closeR").show();
	$("#seerviceManager").show();
	$("#resolve").hide();
	$("#snooze").hide();
	$("#packIDH").show();
	$("#newRequest").hide();
	$('#nerReqst').show();
	$('#nerReqstP').show();
	$('#checkSMS').show();
	$('#'+'PreSe').html("preferred_time_date#Booked Schedule#T#sc,");
	$('#'+'locationSpan').html("location#Location#D#D,");
	$('#'+'serviceSpan').html("service#Service#D#D,");
	if(document.getElementById("uhidCheck").value.length == 10)
	{
		
	}
	else
	{
		document.getElementById("uhidCheck").value = "MM";
	}
	fetchReolveBy();
	
}
if(data=="Parked"){
	$('#'+'resolveBy1').html("");
	$('#'+'locationSpan').html("");
	$('#'+'serviceSpan').html("");
	$('#'+'PreSe').html("");
	$('#'+'parkedTill1').html("parkedTill#Parked Till#Date#Date,");
	$("#snooze").show();
	$("#resolve").hide();
	$("#packIDH").hide();
	$("#newRequest").hide();
	$('#nerReqstP').hide();
	$('#nerReqst').hide();
	$("#seerviceManager").hide();
	$('#checkSMS').hide();
	if(document.getElementById("uhidCheck").value.length == 10)
	{
		
	}
	else
	{
		document.getElementById("uhidCheck").value = "MM";
	}
}

if(data=="Cancel"){
	$('#'+'resolveBy1').html("");
	$('#'+'locationSpan').html("");
	$('#'+'serviceSpan').html("");
	$('#'+'PreSe').html("");
	$('#'+'parkedTill1').html("");
	$("#snooze").hide();
	$("#resolve").hide();
	$("#packIDH").hide();
	$("#newRequest").hide();
	$('#nerReqstP').hide();
	$('#nerReqst').hide();
	$("#seerviceManager").hide();
	$('#checkSMS').hide();
	if(document.getElementById("uhidCheck").value.length == 10)
	{
		
	}
	else
	{
		document.getElementById("uhidCheck").value = "MM";
	}
}
	
}
function specDOCGet(div){
	 Loc =  $("#location").val();
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchSpecLocData.action?Loc="+Loc,
		    success : function(data) {
			  $('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Speciality'));
	    	$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.id).text(this.Name));
			});
			
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function docNameGet(spec, docDiv){
	Loc =  $("#location").val(); 
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDoctorData.action?Loc="+Loc+"&spec="+spec,
			    success : function(data) {
				 
				$('#'+docDiv+' option').remove();
				$('#'+docDiv).append($("<option></option>").attr("value",-1).text('Select'));
				$(data).each(function(index)
				{
				   $('#'+docDiv).append($("<option></option>").attr("value",this.id).text(this.Name));
				});
				
			},
			   error: function() {
			        alert("error");
			    }
			 });
	}
function packageGetAction(pack){

	 madLoc =  $("#corp_name_ID").val();
	 var select_location='location';
	 var med_location=$("#"+select_location+" option:selected").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchPackageData.action?madLoc="+madLoc+"&pack="+pack+"&med_location="+med_location+"&dataFor='lodgePage'",
	    success : function(data) {
		$('#ehc_pack_serviceF option').remove();
		$('#ehc_pack_serviceF').append($("<option></option>").attr("value",-1).text('Select Package'));
  	$(data).each(function(index)
		{
		   $('#ehc_pack_serviceF').append($("<option></option>").attr("value",this.id).text(this.Name));
		});
		
	},
	   error: function() {
          alert("error");
      }
	 });
}
function checkuhidOfAction1(value)
{  
	if(value.length != 10)
	{
		errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit UHID ! </div>";
	       $("#uhidCheck").css("background-color","#ff701a");  // 255;165;79
	       $("#uhidCheck").focus();
	       setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
	       setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	       event.originalEvent.options.submit = false;
	}
}
function checkuhidOfAction(value)
{  
	if(value=='NA' || value=='MM')
	{
	}
	else if(value.trim()!= "" )
	{
		if(value.length == 10)
		{
  		}
		else
		{
			 errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit UHID ! </div>";
		       $("#uhidCheck").css("background-color","#ff701a");  // 255;165;79
		       $("#uhidCheck").focus();
		       setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		       setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		       event.originalEvent.options.submit = false;
		}
     
  }
}
function getCalander11()
{
	$('#textId11').hide();
	$('#dateId11').show();
	$('#openId11').show();
	$('#calId11').hide();
	document.getElementById("preferred_time_date").value = "";
}
function getOpen11()
{
	$('#textId11').show();
	$('#dateId11').hide();
	$('#openId11').hide();
	$('#calId11').show();
	document.getElementById("preferred_time_datecal").value = "";
}
function getToday11(date) 
{
	$('#textId11').show();
	$('#dateId11').hide();
	  $.ajax
	({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDatepreferedTime.action?fetchDate="+date,
	async:false,
	success : function(data)
	{
		if(data.length>0){
			$("#preferred_time_date").empty();
			var temp="";
			if(data[0].Today)
			{
			temp=data[0].Today;
			}
			if(data[0].Tomorrow)
			{
			temp=data[0].Tomorrow;
			}
			document.getElementById("preferred_time_date").value = temp;
			accountManagerRes(temp,'service_managerB');
		}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}



$.subscribe('checkValidation', function(event,data)
		{ 
			validate(event,data,"pidsFinal");
			var check=$("#statusService").val();
			var uhidlenth=$("#uhidCheck").val();
			if(check=='Service Out')
			{
				if(uhidlenth.length != 10)
				{
					checkuhidOfAction1('MM');
				}
			}
				
		});
		function validate(event,data, spanClass)
			{	
			var mystring=$("."+spanClass).text();
			    var fieldtype = mystring.split(",");
			    var pattern = /^\d{10}$/;
			  // 
			    var status;
			    for(var i=0; i<fieldtype.length; i++)
			    {
			        var fieldsvalues = fieldtype[i].split("#")[0];
			        var fieldsnames = fieldtype[i].split("#")[1];
			        var colType = fieldtype[i].split("#")[2];
			        var validationType = fieldtype[i].split("#")[3];
			        $("#"+fieldsvalues).css("background-color","");
			        errZone1.innerHTML="";
			        if(fieldsvalues!= "" )
			        {
			            if(colType=="D"){
			            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
			            {
			            	status=$("#"+fieldsvalues).val();
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            event.originalEvent.options.submit = false;
			            break;   
			              }
			            }
			            else if(colType =="T"){
			            if(validationType=="n"){
			            var numeric= /^[0-9]+$/;
			            if(!(numeric.test($("#"+fieldsvalues).val()))){
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();

			            event.originalEvent.options.submit = false;
			            break;
			              }   
			             }
			            else if(validationType=="an"){
			             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			           
			            event.originalEvent.options.submit = false;
			            break;
			              }
			            }
			            else if(validationType=="ans"){
			             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			         
			            event.originalEvent.options.submit = false;
			            break;
			              }
			            }
			            else if(validationType=="sc"){
				             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
				             if(fieldsnames=='UHID')
				            	{
 				            		if($("#"+fieldsvalues).val().length < 10 && $("#"+fieldsvalues).val()!='MM')
				            		{
				            			 errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit "+fieldsnames+"!!</div>";
								            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
								            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
								            $("#"+fieldsvalues).focus();
								            $("#"+fieldsvalues).css("background-color","#ff701a");
								            event.originalEvent.options.submit = false;
								            break;
				            		}
				            	}
				             else if(fieldsvalues=='remarkId')
				             {
				            	 if($("#"+fieldsvalues).val().length<=50)
				            	 {
						            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
						            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
						            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
						            $("#"+fieldsvalues).focus();
						            $("#"+fieldsvalues).css("background-color","#ff701a");
						            event.originalEvent.options.submit = false;
						            break;
				            	 }
				             }
				             else
				             {
					            if($("#"+fieldsvalues).val()==""){
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
					              }
				             }
					            
				            }
			            else if(validationType=="a"){
			            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            event.originalEvent.options.submit = false;
			            break;    
			              }
			             }
			            else if(validationType=="m"){
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             }
			             else if(validationType =="e"){
			             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
			             }else{
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
			            $("#"+fieldsvalues).focus();
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			            event.originalEvent.options.submit = false;
			            break;
			               }
			             }
			             else if(validationType =="w"){
			             }
			           }
			            else if(colType=="TextArea"){
			            if(validationType=="an"){
			            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            event.originalEvent.options.submit = false;
			            break;
			               }
			             }
			            else if(validationType=="mg"){
			             
			             
			             }   
			            }
			            else if(colType=="Time"){
			            if($("#"+fieldsvalues).val()=="")
			            {
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            event.originalEvent.options.submit = false;
			            break;   
			              }   
			            }
			            else if(colType=="Date"){
			            if($("#"+fieldsvalues).val()=="")
			            { 
			            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone1").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone1").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            event.originalEvent.options.submit = false;
			            break;   
			              }
			             } 
			        }
			    }       
			}