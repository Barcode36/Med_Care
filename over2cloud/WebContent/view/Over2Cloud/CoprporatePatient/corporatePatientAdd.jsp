<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>

<script type="text/javascript" src="<s:url value="/js/cpservice/cpServiceValidation.js"/>"></script>
<STYLE type="text/css">
 	
 .ui-autocomplete-input {
	width:209px;
	height:26px;
}
 
.ui-dialog-titlebar-close{
    display: display;
   
}
#ipdButton .ui-button
{
	width:22px;
	height:26px;
	left:0px !important; 
} 
</STYLE>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
        {
	setTimeout(function(){ $("#orglevel12").fadeIn(); }, 10);
    setTimeout(function(){ $("#orglevel12").fadeOut(); }, 1000);
          sussessMessage();
        });
function sussessMessage()
{
	 delay(function()
	{
	resetForm();
	 corporatePatientView('load');
	 loadDropDownDynamic($("#minDateValue").val(),$("#maxDateValue").val());
	 gridCurrentPage();
	    }, 1000 );
}
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();
function resetForm()
{
	newEntryCPS(); 
	 $("#pat_countryDD").show(); 
	$("#pat_stateDD").show(); 
	 	$("#DD_pat_city").show(); 
}
function resetPage()
{
	 $('#'+'formone').trigger("reset");
	 $('#'+'patient_nameText').hide();
	 $('#'+'patient_nameDD').show();
	 $('#'+'msg_div').empty();
}
function okButton()
	 {
	 $('#completionResult').dialog('close');
	 resetPage();
	 corporatePatientView('load');
}
function gridCurrentPage()
{
	var sourceId='service_manager'
	$("#"+sourceId+" option:selected").val('-1');
	$("#"+sourceId+" option:selected").text('Service Mng.');
	//corporatePatientView('load');
	onloadData();
}
function loadDropDownDynamic(mindate, maxdate){

	$.ajax({
	type :"post",
	url  : "view/Over2Cloud/CorporatePatientServices/cpservices/dynamicDDcall.action?minDateValue="+mindate+"&maxDateValue="+maxdate,
	success : function(data){
	console.log(data);
	$('#corp_namee option').remove();
	$('#corp_namee').append($("<option></option>").attr("value",-1).text("Corporate Name"));
	var empData=data[0]
    	$(empData).each(function(index)
	{
	   $('#corp_namee').append($("<option></option>").attr("value",this.id).text(this.name));
	});
	
	$('#service_manager option').remove();
	$('#service_manager').append($("<option></option>").attr("value",-1).text("Service Mng."));
	empData=data[1];
    	$(empData).each(function(index)
	{
	   $('#service_manager').append($("<option></option>").attr("value",this.id).text(this.name));
	});

    	$('#servicese option').remove();
	$('#servicese').append($("<option></option>").attr("value",-1).text("Services"));
	empData=data[2];
    	$(empData).each(function(index)
	{
	   $('#servicese').append($("<option></option>").attr("value",this.name).text(this.name));
	});


    	$('#med_locationn option').remove();
	$('#med_locationn').append($("<option></option>").attr("value",-1).text("Location"));
	empData=data[3];
    	$(empData).each(function(index)
	{
	   $('#med_locationn').append($("<option></option>").attr("value",this.name).text(this.name));
	});

    	$('#ac_managerr option').remove();
	$('#ac_managerr').append($("<option></option>").attr("value",-1).text("A/C Manager"));
	empData=data[4];
    	$(empData).each(function(index)
	{
	   $('#ac_managerr').append($("<option></option>").attr("value",this.name).text(this.name));
	});

    	$('#status option').remove();
	$('#status').append($("<option></option>").attr("value",-1).text("Status"));
	empData=data[5];
    	$(empData).each(function(index)
	{
	   $('#status').append($("<option></option>").attr("value",this.name).text(this.name));
	});
	    },
	    error : function () {
	alert("Somthing is wrong to get Data");
	}
	});

	
}
function newEntryCPS()
{
$.ajax({
  type : "post",
  async:false,
  url : "view/Over2Cloud/CorporatePatientServices/cpservices/berforeCPSAdd.action",
  success : function(feeddraft) {
	$("#CPSAdd").show().css('overflow');
  $("#"+"CPSAdd").html(feeddraft);
  $("#addID").val("c");
},
 error: function() 
 {
      alert("error");
  }
});



}
function showAgeText(age,div)
{
	var date=$("#currDOB").val();
	var curYear = date.substring(6, 10);
	var changeDOB = parseInt(curYear) - age;
	var DOB = date.substring(0, 6) + changeDOB;
	//alert("DOB "+div);
	//showAge(DOB,div);
	$('#'+div).val(DOB);
	$("#pat_dob").val(DOB);
	//document.getElementById('#'+div).value = DOB;
}


/*function showAgeText(date,div)
{
	 var date_regex = /^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/ ;
	if(date.indexOf("/") > -1 )
	{
	if(date_regex.test(date))
	    {
	    	showAge(date, div);
	    }
	    else{
	    	$('#'+div).empty();
	alert("Please enter in DD/MM/YYYY format");
	document.getElementById("pat_dob_txt").value = "";
	    }
	}
	else{

	showAge(date, div);
	}
}*/
function showAge(date, div)
{
	//alert("date "+date);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/ageView.action?date="+date,
	    async:false,
	    success : function(data) {
	$('#'+div).empty();
	var age = data[0].age;
	$(data).each(function(index)
	{
	    	  if(this.ageY=='0'){
	    	      $('#'+div).val(age.years+" Years , "+age.months+" Months , "+age.days+" Days ");
	    	}
	    	else  if(this.ageY=='1') {
	    	 $('#'+div).append(age);
	    	}
	    	else  if(this.ageY=='2') {
	    	 $('#'+div).append('<font color="Red"><b>'+age+'</b></font>');
	    	}
	});
	},
	   error: function() {
          alert("error");
      }
	 });
	//$("#age").hide();
	//$("#ageText").show();
	
}
function getCalander()
{
	$('#textId').hide();
	$('#dateId').show();
	$('#openId').show();
	$('#calId').hide();
	document.getElementById("preferred_timeID").value = "";
}
function getOpen()
{
	$('#textId').show();
	$('#dateId').hide();
	$('#openId').hide();
	$('#calId').show();
	document.getElementById("preferred").value = "";
}
function getToday1()
{
	var today=$("#today").val();
	$('#textId').show();
	$('#dateId').hide();
	document.getElementById("preferred_timeID").value = today;
}
function getToday(date) 
{
	//alert(date);
	$('#textId').show();
	$('#dateId').hide();
	  $.ajax
	({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDatepreferedTime.action?fetchDate="+date,
	async:false,
	success : function(data)
	{
	if(data.length>0){
	$("#preferred_timeID").empty();
	var temp="";
	if(data[0].Today)
	{
	temp=data[0].Today;
	}
	if(data[0].Tomorrow)
	{
	temp=data[0].Tomorrow;
	}
	document.getElementById("preferred_timeID").value = temp;
	}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}

function getTomorrow()
{
	var today=$("#tomorrow").val();
	$('#textId').show();
	$('#dateId').hide();
	document.getElementById("preferred_time").value = today;
}
function ageText(){

	var date=$("#currDOB").val();
	var curYear = date.substring(6, 10);
	var changeDOB = parseInt(curYear) - 30;
	var DOB = date.substring(0, 6) + changeDOB; 
	$("#pat_dob").val(DOB);
	
	$('#ageText').hide();
	$('#age').show();
	$('#dobCal').show();
	$('#dobtxt').hide();
	$('#msg_div').empty();
	//$('#msg_div').append('Format DD/MM/YYYY');
	//document.getElementById("pat_dob").value = "";
	
}
function ageCal(){
	$('#age').hide();
	$('#ageText').show();
	$('#dobCal').hide();
	$('#dobtxt').show();
	$('#msg_div').empty();
	document.getElementById("pat_dob").value = "";
	document.getElementById("pat_dob_txt").value = "";
	
}
$(document).ready(function()
	{
$("#pat_country_widget").val("IND");


$('#pat_state_widget').append($("<option></option>").text("value","Delhi").text("Delhi"));
$('#pat_city_widget').append($("<option></option>").text("value","Delhi Cantonment").text("Delhi Cantonment"));



	});

function getCity(sourceId,div){
	var state=$("#"+sourceId+" option:selected").val();
	$.ajax({
	   type : "post",
	   url : "view/Over2Cloud/CorporatePatientServices/cpservices/getStateList.action?stateId="+state,
	   success : function(data) {
	//$('#'+div).remove();
	$('#pat_stateDD').html('');
	$('#pat_stateDD').html(data);

	//   	$('#'+div+' option').remove();
	//$('#'+div).append($("<option></option>").attr("value",-1).text('Select State'));
	//$(data).each(function(index)
	//{
	 // $('#'+div).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
	//});
	},
	  error: function() {
	       alert("error");
	   }
	});
	}
function getCity2(sourceId,div){
	var state=$("#"+sourceId+" option:selected").val();
	$.ajax({
	   type : "post",
	   url : "view/Over2Cloud/CorporatePatientServices/cpservices/getCityList1.action?stateId="+state,
	   success : function(data) {
	//   alert(data);
	$('#DD_pat_city').html('');
	$('#DD_pat_city').html(data);
	   
//	   	$('#'+div+' option').remove();
//	$('#'+div).append($("<option></option>").attr("value",-1).text('Select City'));
//	$(data).each(function(index)
//	{
//	  $('#'+div).append($("<option></option>").text("value",this.NAME).text(this.NAME));
//	});
	},
	  error: function() {
	       alert("error");
	   }
	});
	}
$.subscribe('getSelectState', function(event,data)
	{
	getCity2("pat_state_widget","pat_city_widget");
	});
$.subscribe('getState', function(event,data)
	{
	getCity("pat_country_widget","pat_state_widget");
	});
$.subscribe('getCorporateName', function(event,data)
	{
	fetchCorName("ac_manager_widget","corp_name_widget");
	});
function fetchCorName(sourceId, divId)
{  
	var value=$("#"+sourceId+" option:selected").val();
	acmanager=value;
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchCorName.action?id = "+value,
	    success : function(data) {
	    //console.log(data);
	    data = data.jsonArray;
	    if(data != null){
	    $('#'+divId+' option').remove();
	   //$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Corporate Name'));
	   $(data).each(function(index)
	   {
		   if(this.NAME!='NA')
			{
	        	$('#'+divId).append( $("<option></option>").attr("value",this.ID).text(this.NAME) );
			}
	      
	   
	  });
	}
	
	},
	   error: function() {
          alert("error");
      }
	 });
}

// serviceWiseDiv

$.subscribe('serviceWiseDiv', function(event,data)
	{
	//alert("hii");
	
	serviceField("services_widget","med_location_widget" );
	});

function serviceField(sourceId, locId){
	//alert("Hii 2");
	var serviceId=$("#"+sourceId+" option:selected").val();
	var locationId=$("#"+locId+" option:selected").val();
	var serviceFiledNameId = serviceId ;
	if(serviceId== '1'){
	serviceFiledNameId= 'EHC';
	}
	else if(serviceId== '2'){
	serviceFiledNameId= 'IPD';
	docNameGet("IPD_specialty_widget","IPD_doctor_name_widget");
	}
	else if(serviceId== '3'){
	serviceFiledNameId= 'Radiology';
	getModality(serviceId,'med_location_widget','radiology_modality_widget');
	}
	else if(serviceId== '4'){
	serviceFiledNameId= 'OPD';
	docNameGet("OPD_specialty_widget","OPD_doctor_name_widget");
	//specDOCGet("OPD_doctor_name_widget","OPD_specialty_widget");
	}
	else if(serviceId== '5'){
	serviceFiledNameId= 'DayCare';
	docNameGet("Day_care_specialty_widget","Day_care_doctor_widget");
	}
	else if(serviceId== '6'){
	serviceFiledNameId= 'Laboratory';
	//getModality(serviceId,'laboratory_modality');
	
	} 
	else if(serviceId== '7'){
	serviceFiledNameId= 'Emergency';
	getModality(serviceId,'med_location_widget','emergancy_assistance_widget');
	}
	else if(serviceId== '8'){
	serviceFiledNameId= 'Diagnostics';
	getModality(serviceId,'med_location_widget','Diagnostics_test_widget');
	 
	}
	else if(serviceId== '9'){
	serviceFiledNameId= 'New Information Request';
	getModality(serviceId,'med_location_widget','remarks_DD'); 
	}

	else if(serviceId== '10'){
		serviceFiledNameId= 'Facilitation';
		getModality(serviceId,'med_location_widget','facilitation_widget');
		}
		else if(serviceId== '11'){
		serviceFiledNameId= 'Telemedicine';
		getModality(serviceId,'med_location_widget','telemedicine_widget');
		}

	if(serviceFiledNameId == "EHC")
	{
	   var x = document.getElementById("EHCDivL").innerHTML;
	   //$("#addFieldL").html(x);
	//Extra field Show service wise
	    $('#EHCDivL').show();
	$('#OPDDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html(""); 
	
	    $('#'+'OPDvalidation').html("");
	$('#'+'OPDvalidation1').html("");
	    $('#'+'IPDvalidation').html("");
	    $('#'+'IPDvalidation1').html("");
	$('#'+'Radiovalidation').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	$('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	    $('#'+'EHCvalidation').html("EHC_package_type#EHC Packages#D#D,");
	    $('#'+'EHCvalidation1').html("EHC_packages#Packages#D#D,");   
	   
	}else if(serviceFiledNameId == "OPD")
	{
	   var x = document.getElementById("OPDDivL").innerHTML;
	   //$("#addFieldL").html(x);
	   //Extra field Show service wise
	$('#EHCDivL').hide();
	$('#OPDDivL').show();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html("");   
	   //specDOCGet('OPD_specialty');
	   specDOCGet("OPD_doctor_name_widget","OPD_specialty_widget");
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'IPDvalidation').html("");
	   $('#'+'IPDvalidation1').html("");
	   $('#'+'DayCarevalidation').html("");
	   $('#'+'DayCarevalidation1').html("");
	   
	$('#'+'Radiovalidation').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	
	    //$('#'+'OPDvalidation').html("OPD_specialty#Specialty#D#D,");
	    $('#'+'OPDvalidation1').html("OPD_doctor_name_widget#Doctor Name#D#D,");   
	  
	}else if(serviceFiledNameId == "Radiology")
	{
	
	   var x = document.getElementById("RadiologyDivL").innerHTML;
	   //$("#addFieldL").html(x);
	//Extra field Show service wise
	    $('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').show();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html(""); 
	   
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation1').html("");
	   $('#'+'IPDvalidation').html("");
	   $('#'+'IPDvalidation1').html("");
	   $('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	
	    $('#'+'Radiovalidation').html("radiology_modality_widget#Modality#D#D,");
	  
	}
	else if(serviceFiledNameId == "IPD")
	{
	   var x = document.getElementById("IPDDivL").innerHTML;
	   //$("#addFieldL").html(x);
	//Extra field Show service wise
	   	$('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').show();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html("");    
	   //specDOCGet('IPD_specialty_widget');
	   specDOCGet("IPD_doctor_name_widget","IPD_specialty_widget");
	  
	 $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation1').html("");
	   $('#'+'Radiovalidation').html("");
	$('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	
	    //$('#'+'IPDvalidation').html("IPD_specialty_widget#Specialty#D#D,");
	    $('#'+'IPDvalidation1').html("IPD_doctor_name_widget#Doctor Name#D#D,");

	}
	else if(serviceFiledNameId == "DayCare")
	{
	   var x = document.getElementById("DayCareDivL").innerHTML;
	   //$("#addFieldL").html(x);
	   
	   //Extra field Show service wise
	    $('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').show();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide(); 
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html(""); 
	   //specDOCGet('Day_care_specialty');
	   specDOCGet("Day_care_doctor_widget","Day_care_specialty_widget");
	   
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation1').html("");
	   $('#'+'Radiovalidation').html("");
	   $('#'+'IPDvalidation').html("");
	   
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	
	    //$('#'+'DayCarevalidation').html("Day_care_specialty#Specialty#D#D,");
	    $('#'+'DayCarevalidation1').html("Day_care_doctor_widget#Doctor Name#D#D,");
	      
	}
	else if(serviceFiledNameId == "Laboratory")
	{
	 
	
	   var x = document.getElementById("LaboratoryDivL").innerHTML;
	   //$("#addFieldL").html(x);
	   
	   //Extra field Show service wise
	    $('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').show();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html(""); 
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation1').html("");
	   $('#'+'Radiovalidation').html("");
	   $('#'+'IPDvalidation').html("");
	$('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	    $('#'+'Laboratoryvalidation').html("laboratory_modality_widget#Modality#D#D,");
	}  
	else if(serviceFiledNameId == "Emergency")
	{
	
	
	   var x = document.getElementById("EmergencyDivL").innerHTML;
	   //$("#addFieldL").html(x);
	   //Extra field Show service wise
	    $('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').show();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	   
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html("");   
	   
	   specDOCGet("IPD_doctor_name_widget","emergency_specialty_widget");
	   
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'Radiovalidation').html("");
	   $('#'+'IPDvalidation').html("");
	$('#'+'DayCarevalidation').html("");
	$('#'+'Laboratoryvalidation').html("");
	
	$('#'+'Diagnosticsvalidation').html("");
	    $('#'+'Emergencyvalidation').html("emergency_specialty_widget#Specialty#D#D,");
	    $('#'+'Emergencyvalidation1').html("emergancy_assistance_widget#Emergency Assistance#D#D,");
	}
	else if(serviceFiledNameId == "Diagnostics")
	{
	
	   var x = document.getElementById("DiagnosticsDivL").innerHTML;
	  // $("#addFieldL").html(x);

	//Extra field Show service wise
	    $('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').show();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html(""); 

	   
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation1').html("");
	   $('#'+'Radiovalidation').html("");
	   $('#'+'IPDvalidation').html("");
	$('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	    $('#'+'Diagnosticsvalidation').html("Diagnostics_test_widget#Diagnostics Test#D#D,");
	}
	else if(serviceFiledNameId == "New Information Request")
	{
	//Extra field Show service wise
	$('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').show(); 
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').hide();
	$('#'+'Telemedicinevalidation').html("");
	$('#'+'Facilitationvalidation').html(""); 
	}
	else if(serviceFiledNameId == "Facilitation")
	{
 	var x = document.getElementById("FacilitationDivL").innerHTML;
	$('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').show();
	$('#TelemedicineDivL').hide();

	$('#'+'Telemedicinevalidation').html(""); 
	$('#'+'Radiovalidation').html("");  
	$('#'+'EHCvalidation').html("");
	$('#'+'EHCvalidation1').html("");
	$('#'+'OPDvalidation').html("");
	$('#'+'OPDvalidation1').html("");
	$('#'+'IPDvalidation').html("");
	$('#'+'IPDvalidation1').html("");
	$('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	
	    $('#'+'Facilitationvalidation').html("facilitation_widget#Facilitation For#D#D,");
	  
	}

	else if(serviceFiledNameId == "Telemedicine")
	{
	
	   var x = document.getElementById("TelemedicineDivL").innerHTML;
	$('#OPDDivL').hide();
	$('#EHCDivL').hide();
	$('#RadiologyDivL').hide();
	$('#IPDDivL').hide();
	$('#DayCareDivL').hide();
	$('#LaboratoryDivL').hide();
	$('#EmergencyDivL').hide();
	$('#DiagnosticsDivL').hide();
	$('#newDivL').hide();
	$('#FacilitationDivL').hide();
	$('#TelemedicineDivL').show();


	$('#'+'Facilitationvalidation').html("");  
	$('#'+'Radiovalidation').html("");  
	   $('#'+'EHCvalidation').html("");
	   $('#'+'EHCvalidation1').html("");
	   $('#'+'OPDvalidation').html("");
	   $('#'+'OPDvalidation1').html("");
	   $('#'+'IPDvalidation').html("");
	   $('#'+'IPDvalidation1').html("");
	   $('#'+'DayCarevalidation').html("");
	$('#'+'DayCarevalidation1').html("");
	$('#'+'Laboratoryvalidation').html("");
	$('#'+'Emergencyvalidation').html("");
	$('#'+'Emergencyvalidation1').html("");
	$('#'+'Diagnosticsvalidation').html("");
	
	    $('#'+'Telemedicinevalidation').html("telemedicine_widget#Telemedicine For#D#D,");
	  
	}
	
}
function getModality(serviceId,location,div){
	loc=$("#"+location+" option:selected").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchModality.action?servicesID="+serviceId+"&med_location="+loc,
	    success : function(data) {
	  $('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text(''));
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
//OPD
$.subscribe('fetchSpecialityOPD', function(event,data)
	{
	specDOCGet("OPD_doctor_name_widget","OPD_specialty_widget","OPD_specialty_id_div","OPD_specialty_div",'OPD_specialty_text');
	});
//Day Care
$.subscribe('fetchSpecialityDay', function(event,data)
	{
	specDOCGet("Day_care_doctor_widget","Day_care_specialty_widget","Day_care_specialty_id_div","Day_care_specialty_div",'Day_care_specialty_text');
	});
//IPD
$.subscribe('fetchSpeciality', function(event,data)
	{
	specDOCGet("IPD_doctor_name_widget","IPD_specialty_widget","IPD_specialty_id_div","IPD_specialty_div",'IPD_specialty_text');
	});
function specDOCGet(sourceId,div,showDiv,hideDiv,targetText){
	$("#"+showDiv).show();
	$("#"+hideDiv).hide();
	var spec=$("#"+sourceId+" option:selected").val();
	 Loc =  $("#med_location_widget").val(); 
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchSpecLocData.action?Loc="+Loc+"&spec="+spec,
	    success : function(data) {
	  $('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text(''));
	    	$(data).each(function(index)
	{
	    	if(showDiv==undefined)
	   $('#'+div).append($("<option></option>").attr("value",this.id).text(this.Name));
	    	else
	    	$('#'+targetText).val(this.Name); 
	    	
	});
	
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
//Day Care
$.subscribe('fetchDocNameOPD', function(event,data)
	{
	docNameGet("OPD_specialty_widget","OPD_doctor_name_widget");
	});
//Day Care
$.subscribe('fetchDocNameDay', function(event,data)
	{
	docNameGet("Day_care_specialty_widget","Day_care_doctor_widget");
	});
//IPD
$.subscribe('fetchDocName', function(event,data)
	{
	docNameGet("IPD_specialty_widget","IPD_doctor_name_widget");
	});
function docNameGet(sourceId, docDiv){
	var spec=$("#"+sourceId+" option:selected").val();
	Loc =  $("#med_location_widget").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDoctorData.action?Loc="+Loc+"&spec="+spec,
	    success : function(data) {
	$('#'+docDiv+' option').remove();
	$('#'+docDiv).append($("<option></option>").attr("value",-1).text(' '));
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
function packageGet(pack){

	 madLoc =  $("#corp_name_widget").val();
	 var select_location='med_location_widget';
	 var med_location=$("#"+select_location+" option:selected").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchPackageData.action?madLoc="+madLoc+"&pack="+pack+"&med_location="+med_location+"&dataFor='lodgePage'",
	    success : function(data) {
	    //console.log(data);
	$('#EHC_packages option').remove();
	$('#EHC_packages').append($("<option></option>").attr("value",-1).text('Select Packages'));
   	$(data).each(function(index)
	{
   	  if(this.Name!='NA')
   	  {
	   $('#EHC_packages').append($("<option></option>").attr("value",this.id).text(this.Name));
   	  }
	});
	
	},
	   error: function() {
           alert("error");
       }
	 });
}
//getLocation
$.subscribe('getLocation', function(event,data)
	{
	
	fetchServiceLocation("services_widget","med_location_widget");
	});
function fetchServiceLocation(sourceId, divId)
{ 
	var value=$("#"+sourceId+" option:selected").val();
	var valuevv=$("#"+sourceId+" option:selected").html();
	if(valuevv=='EHC')
	packageGet('Custom');
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchServiceLocation.action?service_id = "+value,
	    success : function(data) {
	   data = data.servicelocation;
	  
	    if(data != null){
	    $('#'+divId+' option').remove();
	   //$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Location'));
	   $(data).each(function(index)
	   {
		   if(this.NAME!='NA')
		   {
			   $('#'+divId).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
		   }
	   
	  });
	}
	
	},
	   error: function() {
          alert("error");
      }
	 });
	 if(valuevv=='New Information Request')
	 {

	 $('#remakText').hide();
	 $('#remakDD').show();
	  
	 }
	 else
	 {
	 $('#remakText').show();
	 $('#remakDD').hide();
	 $('#'+'remarks_DD').html("");
	 }
	
}
var acmanager;
function getAccountManager(sourceId)
{
	 $(".ui-autocomplete-input").first().val("");
	 $('#ac_manager1').hide();
	 $('#ac_manager12').show();
	 var value=$("#"+sourceId+" option:selected").val();
	 $.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchAccountManagerLodge.action?id = "+value,
	    success : function(data) {
	    if(data=='' || data=='NA')
	    {
	    	 $("#ac_manager_name").val(acmanager);
	    }
	    else
	    {
	    	$("#ac_manager_name").val(data);
	    }
	},
	   error: function() {
	          alert("error");
	      }
	 });
}
$.subscribe('getPatientName', function(event,data)
	{
	fetchPatientName("corp_name_widget","patient_name_widget");
	getAccountManager("corp_name_widget");
	});
function fetchPatientName(sourceId, divId)
{ 
	var value=$("#"+sourceId+" option:selected").val();
	
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchPatientName.action?id = "+value,
	    success : function(data) {
	   data = data.jsonArray;
	  
	    if(data != null){
	    $('#'+divId+' option').remove();
	   $('#'+divId).append($("<option></option>").attr("value",-1).text('Select Patient'));
	   $(data).each(function(index)
	   {
	   
	   $('#'+divId).append(  $("<option></option>").attr("value",this.ID).text(this.NAME));
	  });
	}
	
	},
	   error: function() {
          alert("error");
      }
	 });
}
function callDD(value,showID, hideID)
{
	if(value=='')
	{
	 $("#"+showID).hide();
   $("#"+hideID).show();
	}
}
//onchange="fillPatientName(this.value, 'patient_nameText','patient_nameDD')" 30 Years ,0 Months ,0 Days

$.subscribe('fillPatientName', function(event,data)
	{
	getPatientName("patient_name_widget","patient_nameText","patient_nameDD");
	});
function getPatientName(sourceId,showID, hideID)
{
	var value=$("#"+sourceId+" option:selected").val();
  if(value == '0')
  {
    document.getElementById("patient_nameId").value = "";
    document.getElementById("pat_email").value = "";
    document.getElementById("pat_dob_txt").value = "";
    document.getElementById("pat_gender1").value = "";
    document.getElementById("pat_country_name").value = "";
    document.getElementById("pat_state_name").value = "";
    document.getElementById("pat_city_name").value = "";
    document.getElementById("pat_mobile").value = "";
    document.getElementById("uhid").value = "";
    	$("#pat_countryDD").show();
  	$("#pat_genderID").show();
  	$('#'+'spanId').html("pat_gender#Gender#T#sc,");
  	$("#pat_countryDD").show();
  	$("#pat_stateDD").show();
  	$("#DD_pat_city").show();
  	
  	$("#pat_gender_div").hide();
  	$('#'+'spanId1').html("");
  	$("#country_div").hide();
  	$("#stateDD_div").hide();
  	$("#cityText").hide();
  	
    $("#"+showID).show();
    $("#"+hideID).hide();
    $('#'+'pname').html("");
    $('#'+'pname_ID').html("patient_nameId#Patient Name#T#sc,");
    
   }
  else
  {
	  $('#'+'pname_ID').html("");
  	$.ajax({
  	    type : "post",
  	    async:false,
  	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fillPatientDetails.action?id = "+value,
  	    success : function(data) { var pat_data = data.split("#"); 
	         $("#patient_nameId").val(pat_data[1]); 
 	         $("#pat_mobile").val(pat_data[2]);
 	         $("#pat_email").val(pat_data[3]);
 	        $("#age").hide();
 	      
 	        $("#ageText").show();
 	         $("#pat_dob_txt").val(pat_data[4]); 
 	       $('#'+'pat_dob').val(pat_data[4]);
 	         if(pat_data[5]!=null)
 	         {
 	        	$("#pat_genderID").hide();
 	        	
 	        	$('#'+'spanId').html("");
 	        	$("#pat_gender_div").show();
 	        	$('#'+'spanId').html("pat_gender1#Gender#T#a,");
 	         	$('#'+'pat_gender1').val(pat_data[5]);
 	         }
 	      if(pat_data[6]!=null)
	         {
	        	$("#pat_countryDD").hide();
	        	$("#country_div").show();
	         	$("#pat_country_name").val(pat_data[6]);
	         }
 	   if(pat_data[7]!=null)
     {
    	$("#pat_stateDD").hide();
    	$("#stateDD_div").show();
    	 $("#pat_state_name").val(pat_data[7]);
     }
 	if(pat_data[8]!=null)
  {
 	$("#DD_pat_city").hide();
 	$("#cityText").show();
 	 $("#pat_city_name").val(pat_data[8]);
  }
 	        
 	        
 	         $("#uhid").val(pat_data[9]);
  	},
  	   error: function() {
              alert("error");
          }
  	 });
}
 //
  }

function checkFormat(value)
{
	if(value=='NA')
	{
	//$('#'+'servicename').html("");
	}
	else if(value.trim()!=""){ 
      if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value.trim())){
      }else{
     errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
     $("#pat_email").css("background-color","#ff701a");  // 255;165;79
     $("#pat_email").focus();
     setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
     setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
     event.originalEvent.options.submit = false;
        } 
      }
}
function checkMobile(value)
{
	//alert(value);
	if(value.trim()!= "" )
  {//alert(value.length);
	if(value.length == 10)
	{
  	}
	else
	{
	 errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
	       $("#pat_mobile").css("background-color","#ff701a");  // 255;165;79
	       $("#pat_mobile").focus();
	       setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	       setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	       event.originalEvent.options.submit = false;
	}
     
  }
}
//UHID VALIDATION

function checkuhid(value)
{
	if(value=='NA')
	{
	}
	else if(value.trim()!= "" )
  { 
	if(value.length == 10)
	{
  	}
	else
	{
	 errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit UHID ! </div>";
	       $("#uhid").css("background-color","#ff701a");  // 255;165;79
	       $("#uhid").focus();
	       setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	       setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	       event.originalEvent.options.submit = false;
	}
     
  }
}

function corporateName(targetid){
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchCorporateName.action",
	    success : function(data) {
	    if(data != null){
	    $('#'+targetid+' option').remove();
	  $('#'+targetid).append($("<option></option>").attr("value",-1).text(' '));
	   $(data).each(function(index)
	   {
	        $('#'+targetid).append( $("<option></option>").attr("value",this.id).text(this.Name) );
	  });
	}
	    
	},
	   error: function() {
          alert("error");
      }
	 });
}
corporateName("corp_name_widget");
</script>
<STYLE type="text/css"><!--
	@media screen and (min-width: 240px) and (max-width: 479px) {
	#cpAdd { display: block; }
	#cpAdd tr { display: block; }
	#cpAdd td { display: block; }
	#cpAdd th { display: block; }
	#cpAdd tbody { display: block; }
	#cpAdd thead { display: block; }
	#CPSAdd{height: 1150px !important;}
	#tableHeading{height: 20px !important;}
	label{margin-bottom: 0px !important;     font-weight: normal !important;     float: left !important; margin-left: 8px !important;}
	.ui-autocomplete-input{width: 90% !important;}
	.textField{width: 98% !important;}
	#preferredschedule{margin-top: 20px !important;}
	#preferred_timeID{height: 25px !important;}
	#imgRes{margin-top: 8px !important; margin-left: 0px !important; }
	#div1Res{margin-left: 8px !important;}
	#div2Res{margin-left: 0px !important;}
	#imgRes1{margin-top: 6px !important; margin-left: -29px !important; }
	}
	
	@media screen and (min-width: 480px) and (max-width: 640px) {
	#cpAdd { display: block; }
	#cpAdd tr { display: block; }
	#cpAdd td { display: block; }
	#cpAdd th { display: block; }
	#cpAdd tbody { display: block; }
	#cpAdd thead { display: block; }
	#CPSAdd{height: 1150px !important;}
	#tableHeading{height: 20px !important;}
	label{margin-bottom: 0px !important;     font-weight: normal !important;     float: left !important; margin-left: 8px !important;}
	.ui-autocomplete-input{width: 90% !important;}
	.textField{width: 98% !important;}
	#preferredschedule{margin-left: 0px !important;margin-top: 5px !important;}
	#preferred_timeID{height: 25px !important;}
	#imgRes{margin-top: 8px !important; margin-left: 0px !important; }
	#div1Res{margin-left: 8px !important;}
	#div2Res{margin-left: 0px !important;}
	#imgRes1{margin-top: 6px !important; margin-left: 0px !important; }
	#countryRes{clear: both;}
	
	}
	@media screen and (max-width: 767px) {
	
	}
	@media screen and (min-width: 767px) and (max-width: 1052px) {
	/*	label{    font-weight: normal !important;     float: none !important; margin-left: 0px !important;}  */
	.ui-button{left: 0px !important;}
	#preferredschedule{margin-left: -115px !important;}
	.listviewButtonLayer{height: 65px !important;}
	}
	#ac_manager1 button{left: 0px !important; top: 0px !important;}
</STYLE>
</head>
  <div class="clear"></div>
<div class="lodgeId">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/CorporatePatientServices/cpservices" action="addCorporatePatience" theme="simple" method="post" enctype="multipart/form-data" focusElement="callerId">
	<s:hidden id="currDOB" value="%{DOB}"></s:hidden>
	
	<center>
	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%;height:100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px" align="center"></div>        
	        </div>
        </center>
	<table id="cpAdd"  rules="rows" width="100%" style="margin-bottom: 10px;margin-left: 0px;margin-top: 5px;" >
	 	<tr id="tableHeading" height="10px" style="background-color: rgba(31, 199, 236,0.34);">
	<th colspan="6" align="left"><h5 style="margin-left: 0%;"><center> <b>Corporate Patient Lodge Request</b></center></h5></th>
	</tr>
	
	<tr align="center">
	<td>
	  	  	<label for="acName" >Account Manager</label>
	</td>
	<td title="Account Manager Name">
	<span class="pIdss" style="display: none; ">ac_manager#Account Manager#T#sc,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<span class="needed"></span>
	<div id="ac_manager1" style="display: block;width: 99%">
	<sj:autocompleter id="ac_manager"  name="ac_manager" list="acManagerMap"   selectBox="true" selectBoxIcon="true" placeHolder="Enter Account Manager"
	       	cssClass="textField" cssStyle="width:99%" theme="simple" onSelectTopics="getCorporateName" />
	</div>
	    	<div id="ac_manager12" style="display: none;">
	    	<s:textfield id="ac_manager_name" name="ac_manager_text" cssClass="textField" theme="simple" cssStyle="width: 232px;"onblur="callDD(this.value, 'ac_manager12','ac_manager1');"></s:textfield>
	    	</div>
	</div>
	 </div>
	 </td>
	 
	<td>
	  	  	<label for="corName" >Corporate Name</label>
	</td>
	<td title="Account Manager Name">
	<span class="pIds" style="display: none; ">corp_name#Corporate Name#T#sc,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<div id="corp_name" style="width: 99%" >
	<span class="needed"></span>
	<sj:autocompleter id="corp_name"  name="corp_name" list="#{'-1':'No Data'}"  
	 	selectBox="true" 
	 	selectBoxIcon="true" 
	 	placeHolder="Enter Account Manager"
	 	onSelectTopics="getPatientName"
	       	cssClass="textField" 
	       	cssStyle="width:99%" theme="simple" 
	       	  />
	</div>
	<div id="corp_name1" style="display: none;">
	    	<s:textfield id="corp_name"  cssClass="textField" theme="simple"></s:textfield>
	    	</div>
	</div>
	 </div>
	 </td>
	 
	   <td>
	  	  	<label for="corName" >Patient Type</label>
	</td>
	<td title="Account Manager Name">
	<span class="pIds" style="display: none; ">feed_status_name#Patient Type#T#sc,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<div id="feed_status_name" style="width: 99%">
	<span class="needed"></span>
	<sj:autocompleter id="feed_status_name"  name="feed_status" list="#{'Standard':'Standard','VVIP':'VVIP','Priority':'Priority','Others':'Others'}"   selectBox="true" selectBoxIcon="true" placeHolder="Enter Account Manager"
	       	cssClass="textField" cssStyle="width:99%" theme="simple"  />
	</div>
	</div>
	 </div>
	 </td>
	 
	 	 </tr>
	
	 <tr style="height:5px"></tr>
	<tr align="center">
	<td>
	  	  	<label for="docIdTo" >Patient Name</label>
	</td>
	<td title="Patient Name">
	
	 <span id="pname" class="pIds" style="display: none; ">patient_name#Patient Name#D#sc,</span>
	 <span id="pname_ID" class="pIds" style="display: none; ">patient_nameId#Patient Name#T#sc,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	    	<div id="patient_nameDD">
	<sj:autocompleter id="patient_name" name="patient_name" list="#{'-1':'No Data'}"    selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple"    onSelectTopics="fillPatientName"/>
	</div>
	    	<div id="patient_nameText" style="display: none">
	    	<s:textfield id="patient_nameId" name="patientName"   theme="simple" cssClass="textField" placeHolder="Enter Patient Name" onblur="callDD(this.value, 'patient_nameText','patient_nameDD');"/>
	    	</div>
	</div>
	 </div>
	 </td>
	<td>
	  	  	 	<label for="docNameTo" >Gender</label>
	</td>
	 	 <td title="Gender">
	 	 <span id="spanId" class="pIds" style="display: none; ">pat_gender#Gender#T#sc,</span>
	 	 <span id="spanId1" class="pIds" style="display: none; ">pat_gender1#Gender#T#sc,</span>
	 	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<div id="pat_genderID" style="display: block;">
	<sj:autocompleter id="pat_gender" name="pat_gender"   list="#{'Male':'Male','Female':'Female'}" selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" />
	</div>
	    	<div id="pat_gender_div" style="display: none;">
	    	<s:textfield id="pat_gender1" name="patgender" cssClass="textField" theme="simple" cssStyle="width: 230px;" ></s:textfield>
	    	</div>
	</div>
	 </div>
	 </td>
	<td>
	  	  	<label for="docSpecTo" >UHID</label>
	</td>
	<td title="UHID">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="uhid" name="uhid" cssStyle="width: 231px;" cssClass="textField" theme="simple" placeHolder="Enter UHID"  onblur="checkuhid(this.value);" maxlength="10" />
	</div>
	 </div>
	 </td>
	
	 </tr>
	 
	<tr align="center">
	<td>
	  	  	<label for="docNameTo" >Mobile</label>
	</td>
	 	 <td title="Patient Mobile No">
	 	  <span class="pIds" style="display: none; ">pat_mobile#Mobile#T#m,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<s:textfield id="pat_mobile" name="pat_mobile" maxlength="10" cssStyle="width: 231px;" cssClass="textField" theme="simple" placeHolder="Enter Mobile No" onblur="checkMobile(this.value);"  />	     
	</div>
	 </div>
	 </td>
	 	
	 
	 <td>
	  	  	<label for="docIdTo" >Email</label>
	</td>
	<td title="Patient EmailID">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="pat_email" name="pat_email" cssClass="textField" cssStyle="width: 231px;" theme="simple" placeHolder="Enter Email"  onblur="checkFormat(this.value);"  />
	</div>
	 </div>
	 </td>
	 
	 <td>
	  	  	<label for="docIdTo" >Patient DOB</label>
	</td>
	 	 <td title="Patient DOB">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	       <div id="age" style="display: none;">
                               <sj:datepicker   cssClass="text"  name="pat_dob"  id="pat_dob" size="20"  readonly="false"  changeMonth="true" changeYear="true"  yearRange="1980" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Enter DOB" onchange="showAgemm(this.value, 'pat_dob')" cssStyle="width: 177px;text-align: center;font-size: medium;"/>
	</div>
	<div id="ageText" style="display: block;">
	<s:textfield   id="pat_dob_txt" name="pat_dob"   cssClass="textField" placeholder="Enter Age" onchange="showAgeText(this.value,'pat_dob_txt')" cssStyle="width: 197px;" maxlength="3"></s:textfield>
	</div>
	</div>
	 </div>
	 </td>
	 <td>
	 <div  style="width: 100%">
	 	<div id="div2Res" style="width: 99%;margin-left: -253px;margin-top: -8px;">
	       <div id = "dobtxt" style="display: block;">
       	<img id="imgRes1" alt="" src="images/calendar_blk.png" style="margin-top:14px;margin-left:35px; height: 22px; float: left;" onclick="ageText()">
      	 	</div>
      	 	<div id = "dobCal" style="display: none;">
       	<img alt="" src="images/textWrite.png" style="margin-top:14px;margin-left:35px; height: 22px; float: left;" onclick="ageCal()">
       	</div>
       	<div class="rightColumn" id="msg_div"></div>
	</div>
	 </div>
	</tr>
	
	<tr align="center">
	<td>
	<label id="countryRes" for="docIdTo" >Country</label>
	</td>
	<td title="Country">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<div id="pat_countryDD" style="display: block">
	<sj:autocompleter id="pat_country" name="pat_country" list="stateMap"     selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="getState" />
	</div>
	    	<div id="country_div" style="display: none;">
	    	<s:textfield id="pat_country_name" name="pat_country" cssClass="textField" theme="simple" cssStyle="width: 231px;" ></s:textfield>
	    	</div>
	</div>
	 </div>
	 </td>
	 
	<td>
	  	  	<label for="docIdTo" >State</label>
	</td>
	<td title="State">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<div id="pat_stateDD" style="display: block">
	<sj:autocompleter id="pat_state" name="pat_state"     selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="getSelectState" />
	</div>
	    	<div id="stateDD_div" style="display: none;">
	    	<s:textfield id="pat_state_name" name="pat_state" cssClass="textField" theme="simple" cssStyle="width: 231px;" ></s:textfield>
	    	</div>
	</div>
	 </div>
	 </td>
	 
	   <td>
	   <label for="docIdTo" >City</label>
	</td>
	<td title="City">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<div id="DD_pat_city" style="display: block">
	<sj:autocompleter id="pat_city" name="pat_city"      selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" />
	</div>
	    	<div id="cityText" style="display: none;">
	    	<s:textfield id="pat_city_name" name="pat_city"  cssClass="textField" theme="simple" cssStyle="width: 231px;" ></s:textfield>
	    	</div>
	</div>
	 </div>
	 </td>
	 
	 	 </tr>
	 	 
 	<tr align="center">
	 <td>
	   <label for="docIdTo" >Services</label>
	</td>
	<td title="Services">
	 <span class="pIds" style="display: none; ">services#Services#T#sc,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<span class="needed"></span>
	<sj:autocompleter id="services" name="services"   list="serviceMap"   selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="getLocation" />
	</div>
	 </div>
	 </td>
	<td>
	  	  	<label for="docSpecTo" >Location</label>
	</td>
	<td title="Medanta Location">
	 <span class="pIds" style="display: none; ">med_location#Location#T#sc,</span>
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%" >
	<span class="needed"></span>
	<sj:autocompleter id="med_location" name="med_location" list="#{'-1':'No Data'}"  selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="serviceWiseDiv"/>
	</div>
	 </div>
	 </td>
	 
	  <td>
	   <label for="docIdTo" >Remarks</label>
	</td>
	<td title="Remarks">
	 <div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 98%;height: 46px; ">
	<div id="remakText" style="display:block;">
	<s:textarea id="remarks_txt" name="remarks"  cssClass="textField" theme="simple" cssStyle="width: 231px;" placeHolder="Enter remarks"  />
	</div>
	<div id="remakDD" style="display:none;"  >
	<s:select
	id	=	"remarks_DD"
	    	name	=	"remarksNew"
	    	list=	"#{'-1':'No Data'}"
	headerKey="NA"
	headerValue="Select Remarks"
	cssClass="select"
	theme="simple"
	cssStyle="width: 81%;height:32px;">
	</s:select> 
	</div>
	</div>
	 </div>
	 </td>
	 	 </tr>
	<!-- </tr> -->
	
	</table>	 
 <div id="preferredschedule" class="newColumn" style="margin-left: -176px;margin-top: -19px;">
 	<div class="leftColumn"><label><b>Preferred Schedule</b></label></div>
	<div class="rightColumn" >
	<div id="textId" style="display: block" >
	<s:textfield id="preferred_timeID" name="preferred_time" cssClass="textField" theme="simple" placeholder="DD-MM-YYYY"  cssStyle="width: 104px;height: 30px;"  />
	</div>
	<div id="dateId" style="display: none" >
	 <sj:datepicker name="preferred_timecal" id="preferred"  cssStyle="width: 59px;height: 25px;"  timepickerOnly="false" minDate="0"  timepicker="false" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Preferred Schedule"/>
	 H:<s:select id="hour"name="hour"  list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}" cssClass="select" cssStyle="width:20%;height: 29px;" theme="simple"/>
	 M:<s:select id="minuts"name="minuts"  list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47','48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59','60':'60'}" cssClass="select" cssStyle="width:20%;height: 29px;" theme="simple"/>
	 <s:select id="ampm"name="ampm"  list="#{'AM':'AM','PM':'PM'}" cssClass="select" cssStyle="width:20%;height: 29px;margin-top: -29px;margin-left: 247px;" theme="simple"/>
	</div>
	</div>
	 </div>
	 <br>
<div id="div1Res" style="margin-left: 151px;">
<div id="calId" style="display: block;">
 <img id="imgRes" alt="" src="images/calendar_blk.png" title="Calander" style="margin-top:23px;margin-left: -321px; height: 22px; float: left;" onclick="getCalander()">
 </div>
 <div id="openId" style="display: none;">
 <img id="imgRes"  alt="" src="images/textWrite.png" title="Open" style="margin-top:23px;margin-left: -321px; height: 22px; float: left;" onclick="getOpen()">
 </div>
 <img id="imgRes" alt="" src="images/today.png" title="Today" style="margin-top:23px; margin-left: -295px; height: 22px; float: left;" onclick="getToday('today')">
 <img id="imgRes" alt="" src="images/tomorrow.png" title="Tomorrow" style="margin-top:23px; margin-left: -266px; height: 22px; float: left;" onclick="getToday('tomorrow')">
	</div>
	<div class="clear"></div>
	<!-- ----------------------------------------------------EHC----------------------------------------------- -->
	<div id="EHCDivL" style="display:none">
	      
	       <div class="newColumn" >
	  	<div class="leftColumn">EHC Packages:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="EHCvalidation" class="pIds" style="display: none; "></span>
                               <span class="needed"></span>
	          <s:select 
                                      id="EHC_package_type"
                                      name="ehc_pack_type"
                                      list=	"#{'Custom':'Custom','Standard':'Standard'}"
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="packageGet(this.value);"
                                      >
                          </s:select>
	    </div>
	        </div>
	     

	 <div class="newColumn">
                    <div class="leftColumn">Packages:</div>
                                <div class="rightColumn"  >
                                <span class="needed"></span>
                                <span id="EHCvalidation1" class="pIds" style="display: none; "></span>
                                <s:select 
                                      id="EHC_packages"
                                      name="ehc_pack"
                                      list=	"#{'-1':'No Data'}"
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                    
                                      >
                          </s:select>
                   </div>
                  </div> 
	     </div>
	     
	    <!-- -----------------------------------------IPD--------------------------------------------------- -->
	     
	     
	     
	     
	     <div id="ipdButton">
	     <div id="IPDDivL" style="display:none">
	     
	      <div class="newColumn" >
	  	<div class="leftColumn">Doctor Name:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="IPDvalidation1" class="pIds" style="display: none; "></span>
	  	 <sj:autocompleter id="IPD_doctor_name" name="ipd_doc" list="#{'-1':'No Data'}"  selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="fetchSpeciality"/>
                          </div>
	        </div>	
	     
	      <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  <span id="IPDvalidation" class="pIds" style="display: none; "></span>
	<div id="IPD_specialty_div" style="display: block; ">
	<sj:autocompleter id="IPD_specialty"  list="{}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple" onSelectTopics="fetchDocName"/>
	</div>
	<div id="IPD_specialty_id_div" style="display: none;">
	<s:textfield id="IPD_specialty_text" name="ipd_spec" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
	</div>
	</div>
	    </div>
	     </div>
	     </div>
	     
	     
	      <!-----------------------------------------------------------------------------Radiology FIELDS------------------------------------------------------------------------------------>
	      <div id="ipdButton">
	      <div id="RadiologyDivL" style="display:none">
	      <div class="newColumn" >
	  	<div class="leftColumn">Modality:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="Radiovalidation" class="pIds" style="display: none; "></span>
	  	<sj:autocompleter id="radiology_modality" name="radio_mod"  list="#{'-1':'No Data'}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple"/>
	    </div>
	        </div>	     
	     </div>
	     </div>
	     	     
	     	      <!-----------------------------------------------------------------------------Facilitation FIELDS------------------------------------------------------------------------------------>
	      <div id="ipdButton">
	      <div id="FacilitationDivL" style="display:none">
	      <div class="newColumn" >
	  	<div class="leftColumn">Facilitation For:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="Facilitationvalidation" class="pIds" style="display: none; "></span>
	  	<sj:autocompleter id="facilitation" name="facilitation_mod"  list="#{'-1':'No Data'}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple"/>
	    </div>
	        </div>	     
	     </div>
	     </div>
	     
	     
	      <!-----------------------------------------------------------------------------Telemedicine FIELDS------------------------------------------------------------------------------------>
	      <div id="ipdButton">
	      <div id="TelemedicineDivL" style="display:none">
	      <div class="newColumn" >
	  	<div class="leftColumn">Telemedicine For:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="Telemedicinevalidation" class="pIds" style="display: none; "></span>
	  	<sj:autocompleter id="telemedicine" name="telemedicine_mod"  list="#{'-1':'No Data'}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple"/>
	    </div>
	        </div>	     
	     </div>
	     </div>
 <!-----------------------------------------------------------------------------Laboratry FIELDS------------------------------------------------------------------------------------>	     
 
 
	     <div id="LaboratoryDivL" style="display:none"> 
	     </div>
	     
	    <!-- -----------------------------------------------emergency--------------------------------------------- -->
 	      <div id="ipdButton">
 	     <div id="EmergencyDivL" style="display:none">
	      <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="Emergencyvalidation" class="pIds" style="display: none; "></span>
	  	<sj:autocompleter id="emergency_specialty" name="em_spec" list="{}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple"/>
	    </div>
	        </div>
	     

	 <div class="newColumn" >
	  	<div class="leftColumn">Emergency:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="Emergencyvalidation1" class="pIds" style="display: none; "></span>
	  	<sj:autocompleter id="emergancy_assistance" name="em_spec_assis" list="#{'-1':'No Data'}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple"/>
	    </div>
	        </div>	     
                  </div>
                  </div>
	 <!--  ------------------------------------OPD------------------------------------------------------ -->
	     <div id="ipdButton">
	      <div id="OPDDivL" style="display:none">
	      
	       <div class="newColumn" >
	  	<div class="leftColumn">Doctor Name:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="OPDvalidation1" class="pIds" style="display: none; "></span>
	  	 <sj:autocompleter id="OPD_doctor_name" name="opd_doc" list="#{'-1':'No Data'}"  selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="fetchSpecialityOPD"/>
                          </div>
	        </div>	
	      
	      <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  <span id="OPDvalidation" class="pIds" style="display: none; "></span>
	<div id="OPD_specialty_div" style="display: block; ">
	<sj:autocompleter id="OPD_specialty"  list="{}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple" onSelectTopics="fetchDocNameOPD"/>
	</div>
	<div id="OPD_specialty_id_div" style="display: none;">
	<s:textfield id="OPD_specialty_text" name="opd_spec" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
	</div>
	</div>
	    </div>
	     </div>
	      
	      </div>
	      
	      
	   <!-- -----------------------------------------------Diagnostics---------------------------------------------------------- -->
	   <div id="ipdButton">
	     <div id="DiagnosticsDivL" style="display:none">
	     <div class="newColumn" >
	  	<div class="leftColumn">Diagnostics Test:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="Diagnosticsvalidation" class="pIds" style="display: none; "></span>
	  	<sj:autocompleter id="Diagnostics_test" name="diagnostics_test" list="#{'-1':'No Data'}"   selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple"/>
	    </div>
	        </div>	     
	     </div>
	     </div>
	    <!-- ----------------------------------------------------------day care--------------------------------------------------- -->
	    <div id="ipdButton">
	      <div id="DayCareDivL" style="display:none">
	      
	       <div class="newColumn" >
	  	<div class="leftColumn">Doctor Name:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="DayCarevalidation1" class="pIds" style="display: none; "></span>
	  	 <sj:autocompleter id="Day_care_doctor" name="daycare_doc" list="#{'-1':'No Data'}"  selectBox="true" selectBoxIcon="true" 
	       	cssClass="textField" theme="simple" onSelectTopics="fetchSpecialityDay"/>
                          </div>
	        </div>	
	      
	      <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  <span id="DayCarevalidation" class="pIds" style="display: none; "></span>
	<div id="Day_care_specialty_div" style="display: block; ">
	<sj:autocompleter id="Day_care_specialty"  list="{}" selectBox="true" selectBoxIcon="true"
	cssClass="textField" theme="simple" onSelectTopics="fetchDocNameDay"/>
	</div>
	<div id="Day_care_specialty_id_div" style="display: none;">
	<s:textfield id="Day_care_specialty_text" name="daycare_spec" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
	</div>
	</div>
	    </div>
	     </div>
	      
	      </div>
	     <!-----------------------------------------------------------------------------New Information FIELDS------------------------------------------------------------------------------------>	     
	    
	
	<div class="clear" style="margin-top: 1px;margin-bottom:2px;" ></div>
	<center>
	<div class="type-button">
	<sj:submit targets="level123" clearForm="false" value="Add"
	effect="highlight" effectOptions="{ color : '#FFFFFF'}"
	  button="true" onCompleteTopics="level1" cssStyle="height:27px;"
	  cssClass="submit"  onBeforeTopics="validate"/>

	&nbsp;&nbsp;

	<sj:a cssStyle="margin-left: -2px;margin-top: 0px;height:26px;"
	button="true" href="#" value="Reset" onclick="sussessMessage();"
	cssStyle="margin-left: -3px;margin-top: -3px;">Reset
	</sj:a>
	</div>
	<sj:div id="orglevel12"  effect="fold">
                   <sj:div  id="level123"></sj:div></sj:div>    
	</center>
	 
 	     
	</s:form>
</div>
</html>