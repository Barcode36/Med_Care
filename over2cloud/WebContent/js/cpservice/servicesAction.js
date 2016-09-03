
var serviceFiledNameId = $("#serviceFiledNameId").val();
makeValidation(serviceFiledNameId);
function makeValidation(serviceFiledNameId)
{
	//var serviceFiledNameId = $("#serviceFiledNameId").val();

	if(serviceFiledNameId == "EHC")
	{
	   var x = document.getElementById("EHCDiv").innerHTML;
	   //$("#serviceID").html(x);
	   $("#addField").html(x);
	   if($("#valueExist").val()!='NA'){
		  // $('#EHC_package_type' option').remove();
			$('#EHC_package_type_service').append($("<option></option>").attr("value",$("#keyExist").val()).text($("#keyExist").val()));
	   }
	   if($("#valueExistEsc").val()!='NA'){
		  
		   $('#EHC_packagesA_service').append($("<option></option>").attr("value",$("#keyExistSec").val()).text($("#keyExistSec").val()));
	   }
	   $('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");
		
	   $('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");

		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		 $('#'+'EHCvalidationService').html("EHC_package_type_service#EHC Packages#D#D,");
		 $('#'+'EHCvalidationService1').html("EHC_packagesA_service#Packages#D#D,");
		 $('#'+'bookEHC').html("service_book_timeEHC#Booked Schedule#Date#Date,");
		 $('#'+'EHCservice_manager').html("service_managerEHC#Service Manager#D#D,");
		 $('#'+'EHCremark').html("service_remarkEHC#EHC Remarks#T#sc,");

		
	}else if(serviceFiledNameId == "OPD")
	{
	   var x = document.getElementById("OPDDiv").innerHTML;
	   $("#addField").html(x);
	   specDOCGet('OPD_specialtyAService','OPD_doctor_nameA_ser');
	   
	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

	   $('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");

		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'OPDValidationService').html("OPD_specialtyAService#Specialty#D#D,");
		$('#'+'OPDValidationService1').html("OPD_doctor_nameA_ser#Doctor Name#D#D,");
		$('#'+'OPDbook').html("service_book_timeOPD#Booked Schedule#Date#Date,");
		$('#'+'OPDser_manager').html("service_managerOPD#Service Manager#D#D,");
		$('#'+'OPDremark').html("service_remark_opd#OPD Remarks#T#sc,");
	  
	}else if(serviceFiledNameId == "Radiology")
	{
	   var x = document.getElementById("RadiologyDiv").innerHTML;
	   $("#addField").html(x);
	   getModalityAction('radio_modality_mod');
	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");

		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		
		$('#'+'RadBook').html("service_book_timeRad#Booked Schedule#Date#Date,");
		$('#'+'RadRemark').html("service_remarkRad#Radiology Remarks#T#sc,");
		$('#'+'Radservice_manager').html("service_managerRad#Service Manager#D#D,");
	}
	
	else if(serviceFiledNameId == "Facilitation")
	{
	   var x = document.getElementById("FacilitationDiv").innerHTML;
	   $("#addField").html(x);
	   getModalityAction('facilitation_mod');
	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		
		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		
		
		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");

		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'FacilitationBook').html("service_book_timeFacilitation#Booked Schedule#Date#Date,");
		$('#'+'FacilitationRemark').html("service_remarkFacilitation#Facilitation Remarks#T#sc,");
		$('#'+'Facilitationservice_manager').html("service_managerFacilitation#Service Manager#D#D,");
	}
	else if(serviceFiledNameId == "Telemedicine")
	{
	   var x = document.getElementById("TelemedicineDiv").innerHTML;
	   $("#addField").html(x);
	   getModalityAction('telemedicine_mod');
	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		
		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
		
		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");

		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'TelemedicineBook').html("service_book_timeTelemedicine#Booked Schedule#Date#Date,");
		$('#'+'TelemedicineRemark').html("service_remarkTelemedicine#Telemedicine Remarks#T#sc,");
		$('#'+'Telemedicineservice_manager').html("service_managerTelemedicine#Service Manager#D#D,");
	}
	
	else if(serviceFiledNameId == "IPD")
	{
	   var x = document.getElementById("IPDDiv").innerHTML;
	   $("#addField").html(x);
	   specDOCGet('IPD_specialtyA_service','IPD_doctor_nameA_service');
	   fetchBedType('IPD_bed_type');
	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");

		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		
		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'IPDSpec').html("IPD_specialtyA_service#Specialty#D#D,");
		$('#'+'IPDDoc').html("IPD_doctor_nameA_service#Doctor Name#D#D,");
		$('#'+'IPDBed').html("IPD_bed_type#Bed Type#D#D,");
		$('#'+'IPDPay').html("IPD_payment_type#Payment Type#D#D,");
		$('#'+'IPDSer').html("service_managerIPD#Service Manager#D#D,");
		$('#'+'IPDBook').html("service_book_timeIPD#Booked Schedule#Date#Date,");
		$('#'+'IPDremark').html("service_remarkIPD#IPD Remarks#T#sc,");

		
	}
	else if(serviceFiledNameId == "Day Care")
	{
	   var x = document.getElementById("DayCareDiv").innerHTML;
	   $("#addField").html(x);
	   specDOCGet('Day_care_specialtyA_service', 'Day_care_doctorA_service');

	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'DaySpec').html("Day_care_specialtyA_service#Specialty#D#D,");
		$('#'+'DayDoc').html("Day_care_doctorA_service#Doctor Name#D#D,");
		$('#'+'DaySer').html("service_managerDay#Service Manager#D#D,");
		$('#'+'DayBook').html("service_book_time_day#Booked Schedule#Date#Date,");
		$('#'+'DayRemark').html("service_remark_day#Day Care Remarks#T#sc,");
	}
	else if(serviceFiledNameId == "Laboratory")
	{
	   var x = document.getElementById("LaboratoryDiv").innerHTML;
	   $("#addField").html(x);

	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");


		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'LabMod').html("laboratory_modality_action_lab#Modality#D#D,");
		$('#'+'LabSer').html("service_managerLab#Service Manager#D#D,");
		$('#'+'LabBook').html("service_book_time_lab#Booked Schedule#Date#Date,");
		$('#'+'LabRemark').html("service_remark_lab#Laboratry Remarks#T#sc,");
		
	       
	}
	else if(serviceFiledNameId == "Emergency")
	{
	   var x = document.getElementById("EmergencyDiv").innerHTML;
	   $("#addField").html(x);
	   specDOCGet('emergency_specialtyA_service','NA');

	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");


		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'EmerSpec').html("emergency_specialtyA_service#Specialty#D#D,");
		$('#'+'EmerSer').html("service_manager_emer#Service Manager#D#D,");
		$('#'+'EmerBook').html("service_book_time_emer#Booked Schedule#Date#Date,");
		$('#'+'EmerEmer').html("emergancy_assistanceEmer#Emergency#D#D,");
		$('#'+'EmerRemark').html("service_remark_emer#Emergency Remarks#T#sc,");
		
	       
	}
	else if(serviceFiledNameId == "Diagnostics")
	{
	   var x = document.getElementById("DiagnosticsDiv").innerHTML;
	   $("#addField").html(x);

	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");


		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");


		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
		
		$('#'+'DiagT').html("Diagnostics_testDT#Diagnostics Test#D#D,");
		$('#'+'DiagSer').html("service_managerDT#Service Manager#D#D,");
		$('#'+'DiagBook').html("service_book_timeDT#Booked Schedule#Date#Date,");
		$('#'+'DiagRemark').html("service_remarkDT#Diagnostics Remarks#T#sc,");
		
	      
	}
	else if(serviceFiledNameId == "New Information Request")
	{
	   var x = document.getElementById("NewServiceDiv").innerHTML;
	   $("#addField").html(x);

	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");
		

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");


		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");


		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("service_managerNS#Service Manager#D#D,");
		$('#'+'NewBook').html("service_book_timeNS#Booked Schedule#Date#Date,");
		$('#'+'NewRemarks').html("service_remarkNS#New Service Remarks#T#sc,");
	}   
}
function removeValidation()
{
	//Remove Validation
	
	   $('#'+'EHCvalidationService').html("");
	   $('#'+'EHCvalidationService1').html("");
	   $('#'+'bookEHC').html("");
	   $('#'+'EHCservice_manager').html("");
	   $('#'+'EHCremark').html("");

		$('#'+'OPDValidationService').html("");
		$('#'+'OPDValidationService1').html("");
		$('#'+'OPDbook').html("");
		$('#'+'OPDser_manager').html("");
		$('#'+'OPDremark').html("");

		$('#'+'RadBook').html("");
		$('#'+'RadRemark').html("");
		$('#'+'Radservice_manager').html("");
		
		
		$('#'+'FacilitationBook').html("");
		$('#'+'FacilitationRemark').html("");
		$('#'+'Facilitationservice_manager').html("");
	
		$('#'+'TelemedicineBook').html("");
		$('#'+'TelemedicineRemark').html("");
		$('#'+'Telemedicineservice_manager').html("");

		$('#'+'IPDSpec').html("");
		$('#'+'IPDDoc').html("");
		$('#'+'IPDBed').html("");
		$('#'+'IPDPay').html("");
		$('#'+'IPDSer').html("");
		$('#'+'IPDBook').html("");
		$('#'+'IPDremark').html("");


		$('#'+'DaySpec').html("");
		$('#'+'DayDoc').html("");
		$('#'+'DaySer').html("");
		$('#'+'DayBook').html("");
		$('#'+'DayRemark').html("");

		$('#'+'LabMod').html("");
		$('#'+'LabSer').html("");
		$('#'+'LabBook').html("");
		$('#'+'LabRemark').html("");


		$('#'+'EmerSpec').html("");
		$('#'+'EmerSer').html("");
		$('#'+'EmerBook').html("");
		$('#'+'EmerEmer').html("");
		$('#'+'EmerRemark').html("");

		$('#'+'DiagT').html("");
		$('#'+'DiagSer').html("");
		$('#'+'DiagBook').html("");
		$('#'+'DiagRemark').html("");

		$('#'+'NewSer').html("");
		$('#'+'NewBook').html("");
		$('#'+'NewRemarks').html("");
}


function getModalityAction(div)
{
	loc =  $("#location").val();
	serviceId=$("#service_id").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchModality.action?servicesID="+serviceId+"&med_location="+loc,
	    success : function(data) {
		  $('#'+div+' option').remove();
		$('#'+div).append($("<option></option>").attr("value",$("#keyExistSec").val()).text($("#keyExistSec").val()));
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

function specDOCGet(div, divS){
	
	 Loc =  $("#Loc").val();
		//  alert(val.value);
		//alert(Offeringlevel);
		valueExist
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchSpecLocData.action?Loc="+Loc,
		    success : function(data) {
			    
			 $('#'+div+' option').remove();
				$('#'+div).append($("<option></option>").attr("value",$("#keyExist").val()).text($("#valueExist").val()));
				if(divS!='NA'){
					 $('#'+divS+' option').remove();
					$('#'+divS).append($("<option></option>").attr("value",$("#keyExistSec").val()).text($("#valueExistSec").val()));
				}
		    	$(data).each(function(index)
				{
		    		//  alert(this.ID +" "+ this.NAME);
				   $('#'+div).append($("<option></option>").attr("value",this.id).text(this.Name));
				});
			
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function DOCNameGet(spec ){
	 Loc =  $("#Loc").val();
		//  alert(val.value);
		//alert(Offeringlevel);
		
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDocLocData.action?Loc="+Loc+"&spec="+spec,
		    success : function(data) {
			    
			$('#OPD_specialtyAService option').remove();
			$('#OPD_specialtyAService').append($("<option></option>").attr("value",-1).text('Select'));
	    	$(data).each(function(index)
			{
	    		//  alert(this.ID +" "+ this.NAME);
			   $('#OPD_specialtyAService').append($("<option></option>").attr("value",this.id).text(this.Name));
			});
			
		},
		   error: function() {
	            alert("error");
	        }
		 });
}


function docNameGet(spec, docDiv){

	Loc =  $("#Loc").val();
	//pefTime =  $("#pefTime").val();
	//alert(Loc +"  "+docDiv+"   "+spec);

			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDoctorData.action?Loc="+Loc+"&spec="+spec,
			    success : function(data) {
				 
				$('#'+docDiv+' option').remove();
				$('#'+docDiv).append($("<option></option>").attr("value",$("#keyExistSec").val()).text($("#keyExistSec").val()));
				$(data).each(function(index)
				{
					//  alert(this.ID +" "+ this.NAME);
				   $('#'+docDiv).append($("<option></option>").attr("value",this.id).text(this.Name));
				});
				
			},
			   error: function() {
			        alert("error");
			    }
			 });



		
	}
serviceLocation();
function serviceLocation()
{ 
	var value = $("#serviceFiled").val();
	//alert(value)
	var divId='location';
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchServiceLocation.action?service_id = "+value,
	    success : function(data) {
	   data = data.servicelocation;
	  
	    if(data != null){
		   $('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",$("#madLoc").val()).text($("#madLoc").val()));
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
function fetchBedType(div)
{
		loc =  $("#location").val();
		serviceId=$("#service_id").val();
		//alert("serviceId "+serviceId+"Loc "+loc);
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
function actionLoad(data){

	if(data=='A')
	{
		
		//getModalityAction();
		$('#'+'reasonParkTill').html("");
		$('#'+'reasonPark').html("");
		$('#'+'reasonAll').html("");
		$("#serviceID").show();
		$('#'+'locationSpan').html("location#Location#D#D,");
		$("#assign").show();
		$("#close").hide();
		$("#park").hide();
		$("#checkSMS").show();
		//makeValidation(serviceFiledNameId);
	}
	if(data=='C')
	{
		removeValidation();
		$("#serviceID").hide();
		$('#'+'locationSpan').html("");
		$("#assign").hide();
		$("#close").show();
		$("#park").hide();
		$("#checkSMS").hide();
		$('#'+'reasonParkTill').html("");
		$('#'+'reasonPark').html("");
		$('#'+'reasonAll').html("reason#Reason#T#sc,");
		
		
	}
	if(data=='I')
	{
		removeValidation();
		$("#serviceID").hide();
		$("#assign").hide();
		$("#close").show();
		$("#park").hide();
		$("#checkSMS").hide();
		$('#'+'reasonAll').html("");
		$('#'+'reasonParkTill').html("");
		$('#'+'reasonPark').html("");
		 
	}

	if(data=='P')
	{
		removeValidation();
		$("#serviceID").hide();
		$("#assign").hide();
		$("#close").hide();
		$("#park").show();
		$("#checkSMS").hide();
		$('#'+'reasonAll').html("");
		$('#'+'reasonParkTill').html("parked_till#Parked Till#Date#Date,");
		$('#'+'reasonPark').html("reasonP#Park Reason#T#sc,");
		
	}
}
$.subscribe('validateService', function(event,data)
		{ 
		//alert("mm");
			validate(event,data,"pIdsService");
		});
		function validate(event,data, spanClass)
			{	
			var mystring=$("."+spanClass).text();
			    var fieldtype = mystring.split(",");
			    var pattern = /^\d{10}$/;
			  // 
			    for(var i=0; i<fieldtype.length; i++)
			    {
			        var fieldsvalues = fieldtype[i].split("#")[0];
			        var fieldsnames = fieldtype[i].split("#")[1];
			        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
			        var validationType = fieldtype[i].split("#")[3];
			        $("#"+fieldsvalues).css("background-color","");
			        errZone.innerHTML="";
			        if(fieldsvalues!= "" )
			        {
			            if(colType=="D"){
			            	//alert(mystring);
			            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }
			            }
			            else if(colType =="T"){
			            if(validationType=="n"){
			            var numeric= /^[0-9]+$/;
			            if(!(numeric.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();

			            event.originalEvent.options.submit = false;
			            break;
			              }   
			             }
			            else if(validationType=="an"){
			             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			           
			            event.originalEvent.options.submit = false;
			            break;
			              }
			            }
			            else if(validationType=="ans"){
			             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			         
			            event.originalEvent.options.submit = false;
			            break;
			              }
			            }
			            else if(validationType=="sc"){
				             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
				             if(fieldsvalues=='reason' || fieldsvalues=='reasonP' || fieldsvalues=='service_remarkEHC' || fieldsvalues=='service_remark_opd' || fieldsvalues=='service_remarkRad' || fieldsvalues=='service_remarkFacilitation' || fieldsvalues=='service_remarkTelemedicine' || fieldsvalues=='service_remarkIPD' || fieldsvalues=='service_remark_day' || fieldsvalues=='service_remark_lab' || fieldsvalues=='service_remarkDT' || fieldsvalues=='service_remark_emer' || fieldsvalues=='service_remarkNS')
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
				             
				             else if($("#"+fieldsvalues).val()==""){
				            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				            $("#"+fieldsvalues).focus();
				            $("#"+fieldsvalues).css("background-color","#ff701a");
				            event.originalEvent.options.submit = false;
				            break;
				              }
				            }
			            else if(validationType=="a"){
			            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;    
			              }
			             }
			            else if(validationType=="m"){
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			               // $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                ///$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			               // $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             }
			             else if(validationType =="e"){
			             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
			             }else{
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
			           // $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			            $("#"+fieldsvalues).focus();
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
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
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
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
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }   
			            }
			            else if(colType=="Date"){
			            if($("#"+fieldsvalues).val()=="")
			            { 
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }
			             } 
			        }
			       // else{$("#completionResult").dialog('open');}
			        	
			    }       
			}
 