<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<%-- <s:url  id="contextz" value="/template/theme" /> --%>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<%-- <sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> --%>
<script type="text/javascript"
	src='<s:url value="/js/criticalPatient/criticalLodgeValidation.js"/>'></script>

<STYLE type="text/css">
	
 .ui-autocomplete-input {
	width:209px;
	height:26px;
}
</STYLE>
<SCRIPT type="text/javascript">
	$.subscribe('level', function(event, data) {
	$("#completionResult").dialog({
	title : 'Confirmation Message',
	width : 400,
	height : 100
	});
	$("#completionResult").dialog('open');
	setTimeout(function() {
	$("#result").fadeIn();
	}, 10);
	setTimeout(function() {
	$("#result").fadeOut();
	}, 5000);

	//$("#completionResult").dialog('close');
	resetFormAdd();
	//
	//pullDDValueAfterLodge();
	//resetFormField(formone);
	//criticalTicketLodge();

	
	onSearchData();


	});
notificationCall();
function notificationCall(){

	$.ajax({
	    type : "post",
	    url : "/over2cloud/viewNewsandalerts.action?dataFor=CRC",
	    success : function(data) {
		
		   $('#noti').html(data);
		
		
	},
	   error: function() {
          alert("error");
      }
	 });
}

function resetFormAdd() {
	//setDDValue();
            
         	//fetchDoctorName();
         	
         	
         	
         	var data= $("#doc_name_widget").val();
         	$("#doc_name_div_").hide();
			  $('#'+'tty').html("");
			$("#doc_name_div_txt").show();
			$("#doc_name_txt").val(data);
         	$('#uhidResult1').html("");
         	 $("#bed_no").val("");
         	//$('#uhidResult1').show();
         	
            resetTestType();
            
             $('#test_unit').val("");
             $('#patient_status').val("");	
             $(".ui-autocomplete-input").val("");
             $('#test_namea_widget').val("");
             $('#test_namei_widget').val("");
             $('#test_nameh_widget').val("");
             $('#test_nameg_widget').val("");
             $('#test_namef_widget').val("");
             $('#test_namee_widget').val("");
             $('#test_named_widget').val("");
             $('#test_namec_widget').val("");
             $('#test_nameb_widget').val("");
             $('#test_name_widget').val("");
             
             $("#test_name_txt_fd_").val("");
             $("#test_name_txt_fd_a").val("");
             $("#test_name_txt_fd_b").val("");
             $("#test_name_txt_fd_c").val("");
             $("#test_name_txt_fd_d").val("");
             $("#test_name_txt_fd_e").val("");
             $("#test_name_txt_fd_f").val("");
             $("#test_name_txt_fd_g").val("");
             $("#test_name_txt_fd_h").val("");
             $("#test_name_txt_fd_i").val("");
             
             
             
             
             $('#test_unita').val("");
             $('#test_unitb').val("");
             $('#test_unitc').val("");
             $('#test_unitd').val("");
             $('#test_unite').val("");
             $('#test_unitf').val("");
             $('#test_unitg').val("");
             $('#test_unith').val("");
             $('#test_uniti').val("");
	$('#ref_to_sub_spec').val("");
	$("#uhid1").val("");
	$("#patient_name").val("");
	$("#patient_mobile").val("");
	$("#addmision_doc_name").val("");
	$("#addmision_doc_mobile").val("");
	$("#nursing_unit").val("");
	$("#specialty").val("");
	$("uhidResult1").val("");
	$("#uhid1").show();
	$("#uhid1").val("MM");
	$("#test_type").val("-1");
	$("#test_name_widget").val("");
	$("#test_value").val("");
	$("#critical_value").val("");
	$("#comments_Lodge_form").val("");
	$("#formone_genderFemale").val("");

	$("#test_typea").val("-1");
	$("#test_namea_widget").val("");
	$("#test_valuea").val("");
	$("#critical_valuea").val("");
	$("#commenta").val("");

	$("#test_typeb").val("-1");
	$("#test_nameb_widget").val("");
	$("#test_valueb").val("");
	$("#critical_valueb").val("");
	$("#commentb").val("");

	$("#test_typec").val("-1");
	$("#test_namec_widget").val("");
	$("#test_valuec").val("");
	$("#critical_valuec").val("");
	$("#commentc").val("");

	$("#test_typee").val("-1");
	$("#test_namee_widget").val("");
	$("#test_valuee").val("");
	$("#critical_valuee").val("");
	$("#commente").val("");
	$("test_name_widget").val("");	
	$("#uhidResult1").show();
	
	$("#min1").hide();
	$("#max1").hide();
	$("#min2").hide();
	$("#max2").hide();
	$("#min3").hide();
	$("#max3").hide();
	$("#min4").hide();
	$("#max4").hide();
	$("#min5").hide();
	$("#max5").hide();
	$("#min6").hide();
	$("#max6").hide();
	$("#min7").hide();
	$("#max7").hide();
	$("#min7").hide();
	$("#max7").hide();


	$("#test_name_div_txt_").hide();
	$("#test_name_div_txt_a").hide();
	$("#test_name_div_txt_b").hide();
	$("#test_name_div_txt_c").hide();
	$("#test_name_div_txt_d").hide();
	$("#test_name_div_txt_e").hide();
	$("#test_name_div_txt_f").hide();
	$("#test_name_div_txt_g").hide();
	$("#test_name_div_txt_i").hide();
	$("#test_name_div_txt_j").hide();



	$("#test_name_div_").show();
	$("#test_name_div_a").show();
	$("#test_name_div_b").show();
	$("#test_name_div_c").show();
	$("#test_name_div_d").show();
	
	$("#test_name_div_e").show();
	$("#test_name_div_f").show();
	$("#test_name_div_g").show();
	$("#test_name_div_i").show();
	$("#test_name_div_").show();


	}
	function resetForm() {
	//setDDValue();
            
         	//fetchDoctorName();
         	$('#doc_name_widget').remove();
         	$("#doc_name_div_").show();
			 // $('#'+'tty').html("");
			$("#doc_name_div_txt").hide();
         	$('#uhidResult1').html("");
         	//$('#uhidResult1').show();
         	
	fetchDoctorName();
            resetTestType();
            
             $('#test_unit').val("");
            $('#doc_speciality').val("");
             $('#patient_status').val("");	
             $(".ui-autocomplete-input").val("");
             $('#test_namea_widget').val("");
             $('#test_namei_widget').val("");
             $('#test_nameh_widget').val("");
             $('#test_nameg_widget').val("");
             $('#test_namef_widget').val("");
             $('#test_namee_widget').val("");
             $('#test_named_widget').val("");
             $('#test_namec_widget').val("");
             $('#test_nameb_widget').val("");
             $('#test_name_widget').val("");
             
             $("#test_name_txt_fd_").val("");
             $("#test_name_txt_fd_a").val("");
             $("#test_name_txt_fd_b").val("");
             $("#test_name_txt_fd_c").val("");
             $("#test_name_txt_fd_d").val("");
             $("#test_name_txt_fd_e").val("");
             $("#test_name_txt_fd_f").val("");
             $("#test_name_txt_fd_g").val("");
             $("#test_name_txt_fd_h").val("");
             $("#test_name_txt_fd_i").val("");
             $("#bed_no").val("");
             
             
             
             $('#test_unita').val("");
             $('#test_unitb').val("");
             $('#test_unitc').val("");
             $('#test_unitd').val("");
             $('#test_unite').val("");
             $('#test_unitf').val("");
             $('#test_unitg').val("");
             $('#test_unith').val("");
             $('#test_uniti').val("");
	$('#ref_to_sub_spec').val("");
	$('#caller_emp_id').val("");
	$('#caller_emp_name').val("");
	$("#caller_emp_mobile").val("");
	 $('#doc_name_widget').val("");
	$("#doc_mobile").val("");
	$("#uhid1").val("");
	$("#patient_name").val("");
	$("#patient_mobile").val("");
	$("#addmision_doc_name").val("");
	$("#addmision_doc_mobile").val("");
	$("#nursing_unit").val("");
	$("#specialty").val("");
	$("uhidResult1").val("");
	$("#uhid1").show();
	$("#uhid1").val("MM");
	$("#test_type").val("-1");
	$("#test_name_widget").val("");
	$("#test_value").val("");
	$("#critical_value").val("");
	$("#comments_Lodge_form").val("");
	$("#formone_genderFemale").val("");

	$("#test_typea").val("-1");
	$("#test_namea_widget").val("");
	$("#test_valuea").val("");
	$("#critical_valuea").val("");
	$("#commenta").val("");

	$("#test_typeb").val("-1");
	$("#test_nameb_widget").val("");
	$("#test_valueb").val("");
	$("#critical_valueb").val("");
	$("#commentb").val("");

	$("#test_typec").val("-1");
	$("#test_namec_widget").val("");
	$("#test_valuec").val("");
	$("#critical_valuec").val("");
	$("#commentc").val("");

	$("#test_typee").val("-1");
	$("#test_namee_widget").val("");
	$("#test_valuee").val("");
	$("#critical_valuee").val("");
	$("#commente").val("");
	$("test_name_widget").val("");	
	$("#uhidResult1").show();
	
	$("#min1").hide();
	$("#max1").hide();
	$("#min2").hide();
	$("#max2").hide();
	$("#min3").hide();
	$("#max3").hide();
	$("#min4").hide();
	$("#max4").hide();
	$("#min5").hide();
	$("#max5").hide();
	$("#min6").hide();
	$("#max6").hide();
	$("#min7").hide();
	$("#max7").hide();
	$("#min7").hide();
	$("#max7").hide();


	$("#test_name_div_txt_").hide();
	$("#test_name_div_txt_a").hide();
	$("#test_name_div_txt_b").hide();
	$("#test_name_div_txt_c").hide();
	$("#test_name_div_txt_d").hide();
	$("#test_name_div_txt_e").hide();
	$("#test_name_div_txt_f").hide();
	$("#test_name_div_txt_g").hide();
	$("#test_name_div_txt_i").hide();
	$("#test_name_div_txt_j").hide();



	$("#test_name_div_").show();
	$("#test_name_div_a").show();
	$("#test_name_div_b").show();
	$("#test_name_div_c").show();
	$("#test_name_div_d").show();
	
	$("#test_name_div_e").show();
	$("#test_name_div_f").show();
	$("#test_name_div_g").show();
	$("#test_name_div_i").show();
	$("#test_name_div_").show();


	}

	function checkUHID(val) {

	//alert(val);
	//setDocDataADD();
	//$("#" + "uhidResult1").html("gggg");
	if (val.trim().length > 2) {

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/Critical/UHIDCheck.action?uhid=" + val,
	success : function(data) {
	var status = data.uhidStatus;
	var pat_id = data.dataFor;
	//alert(pat_id);
	$("#" + "patient_id").val(pat_id);
	$("#" + "uhidResult1").html(status);
	$("#uhid").show();
	},
	error : function() {
	alert("error");
	}
	});
	}
	}

	function showCurrentData() {
	$("#result")
	.html(
	"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var vala = $("#uhid1").val();
	var data = $("#patient_id").val();
	if (vala!="") {
	$("#completionResult").dialog({
	title : 'View for UHID: ' + vala,
	width : 1000,
	height : 600
	});
	$("#completionResult").dialog('open');

	$.ajax({
	type : "post",
	url : "view/Over2Cloud/Critical/beforeCriticalUHID?uhidStatus="
	+ vala + "&dataFor=" + data,
	success : function(data) {
	$("#" + "completionResult").html(data);
	$("#uhid").show();
	},
	error : function() {
	alert("error");
	}
	});

	}
	}

	// Show Hide Div of test type 
	function adddiv(val) {
	if (val == "aa") {
	document.getElementById("t11").textContent = "test_typea#Test Type#D#sc,";
	//document.getElementById("t12").textContent = "test_namea#Test Name#T#sc,";
	document.getElementById("t13").textContent = "test_valuea#Test Value#T#sc,";

	$("#a").show();
	$("#a2").show();
	$("#a1").show();
	}
	if (val == "ba") {

	document.getElementById("t21").textContent = "test_typeb#Test Type#D#sc,";
	//document.getElementById("t22").textContent = "test_nameb#Test Name#T#sc,";
	document.getElementById("t23").textContent = "test_valueb#Test Value#T#sc,";
	$("#b").show();
	$("#b2").show();
	$("#b1").show();
	}

	if (val == "ca") {
	document.getElementById("t31").textContent = "test_typec#Test Type#D#sc,";
	//document.getElementById("t32").textContent = "test_namec#Test Name#T#sc,";
	document.getElementById("t33").textContent = "test_valuec#Test Value#T#sc,";
	$("#c").show();
	$("#c1").show();
	$("#c2").show();
	}

	if (val == "da") {
	$("#d").show();
	$("#d1").show();
	$("#d2").show();
	}

	if (val == "ea") {
	document.getElementById("t41").textContent = "test_typee#Test Type#D#sc,";
	//document.getElementById("t42").textContent = "test_namee#Test Name#T#sc,";
	document.getElementById("t43").textContent = "test_valuee#Test Value#T#sc,";
	$("#e").show();
	$("#e1").show();
	$("#e2").show();
	}

	if (val == "fa") {
	document.getElementById("t51").textContent = "test_typef#Test Type#D#sc,";
	//document.getElementById("t52").textContent = "test_namef#Test Name#T#sc,";
	document.getElementById("t53").textContent = "test_valuef#Test Value#T#sc,";
	$("#f").show();
	$("#f1").show();
	$("#f2").show();
	}

	if (val == "ga") {
	$("#g").show();
	$("#g1").show();
	$("#g2").show();
	}

	if (val == "ha") {
	$("#h").show();
	$("#h1").show();
	$("#h2").show();
	}

	if (val == "ia") {
	$("#i").show();
	$("#i1").show();
	$("#i2").show();
	}

	if (val == "ja") {
	$("#j").show();
	$("#j1").show();
	$("#j2").show();
	}

	}

	function deldiv(val) {
	if (val == "as") {
	document.getElementById("t11").textContent = "";
	//document.getElementById("t12").textContent = "";
	document.getElementById("t13").textContent = "";
	$("#a").hide();
	$("#a1").hide();
	$("#a2").hide();
	document.getElementById("#content span").innerHTML = "";
	}

	if (val == "bs") {
	document.getElementById("t21").textContent = "";
	//document.getElementById("t22").textContent = "";
	document.getElementById("t23").textContent = "";
	$("#b").hide();
	$("#b1").hide();
	$("#b2").hide();
	}

	if (val == "cs") {
	document.getElementById("t31").textContent = "";
	//document.getElementById("t32").textContent = "";
	document.getElementById("t33").textContent = "";
	$("#c").hide();
	$("#c1").hide();
	$("#c2").hide();
	}

	if (val == "d") {
	$("#d").hide();
	$("#d1").hide();
	$("#d2").hide();
	}

	if (val == "es") {
	document.getElementById("t41").textContent = "";
	//document.getElementById("t42").textContent = "";
	document.getElementById("t43").textContent = "";
	$("#e").hide();
	$("#e1").hide();
	$("#e2").hide();
	}

	if (val == "fs") {
	document.getElementById("t51").textContent = "";
	//document.getElementById("t52").textContent = "";
	document.getElementById("t53").textContent = "";
	$("#f").hide();
	$("#f1").hide();
	$("#f2").hide();
	}

	if (val == "gs") {
	$("#g").hide();
	$("#g1").hide();
	$("#g2").hide();
	}

	if (val == "hs") {
	$("#h").hide();
	$("#h1").hide();
	$("#h2").hide();
	}

	if (val == "is") {
	$("#i").hide();
	$("#i1").hide();
	$("#i2").hide();
	}

	if (val == "js") {
	$("#j").hide();
	$("#j1").hide();
	$("#j2").hide();
	}
	}

	function checkLodgeNo() {
	var emp = $("#caller_emp_mobile").val();
	var testDoc = $("#doc_mobile").val();
	
if(emp!="")
	{
	if (emp == testDoc) {
	$("#doc_mobile").css("background-color", "");
	errZone.innerHTML = "";
	errZone.innerHTML = "<div class='user_form_inputError2'>Please Entered Different Mobile Number </div>";
	$("#doc_mobile").css("background-color", "#ff701a");
	$("#doc_mobile").focus(); //255;165;79
	setTimeout(function() {
	$("#errZone").fadeIn();
	}, 10);
	setTimeout(function() {
	$("#errZone").fadeOut();
	}, 2000);
	event.originalEvent.options.submit = false;

	} else {
	$("#doc_mobile").css("background-color", "#ffffff");
	}
	}
	}
	function checkDocNo() {
	var emp = $("#addmision_doc_mobile").val();
	var testDoc = $("#patient_mobile").val();
	if(emp!="")
	{
	if (emp == testDoc) {
	$("#addmision_doc_mobile").css("background-color", "");
	errZone.innerHTML = "";
	errZone.innerHTML = "<div class='user_form_inputError2'>Please Entered Different Mobile Number </div>";
	$("#addmision_doc_mobile").css("background-color", "#ff701a");
	$("#addmision_doc_mobile").focus(); //255;165;79
	setTimeout(function() {
	$("#errZone").fadeIn();
	}, 10);
	setTimeout(function() {
	$("#errZone").fadeOut();
	}, 2000);
	event.originalEvent.options.submit = false;

	} else {
	$("#addmision_doc_mobile").css("background-color", "#ffffff");
	}
	}

	}
	function fetchDoctorName()
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Critical/fetchDoctorName.action?",
		    success : function(data) {
			$('#doc_name option').remove();
	  	$(data).each(function(index)
			{
			   $('#doc_name_widget').append($("<option></option>").attr("value",this.name).text(this.name));
			});
			
		},
		   error: function() {
	          alert("error");
	      }
		 });
	}
	fetchDoctorName();

	$.subscribe('selectSpeciality', function(event,data)
			{
		fetchSpeciality('doc_name_widget');
			});
	function fetchSpeciality(docName) {
		var specDoc=$("#"+docName+" option:selected").val();
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/Critical/fetchSpeciality.action?docName=" + specDoc,// + "&dataFor=" + dataFor + "&patType=" +$("#patient_status").val(),
				success : function(data) {
				$("#doc_speciality").val(data[0].name);
				},
				error : function() {
					alert("error");
				}
			});
	}
</script>
</head>
<!-- <div style=" 
  margin-left: 51%;
  width: 86%;
  margin-top: -65px;"> -->
<div>

	<s:hidden name="patient_id" id="patient_id"></s:hidden>
	<sj:dialog id="completionResult" showEffect="slide"
	hideEffect="expjsonDatalode" autoOpen="false"
	cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
	modal="true" draggable="true" resizable="true">
	<div id="result"></div>
	</sj:dialog>

	<div id="form" style="width: 87%; float: left;     background-color: #f8f8f8;" >
	<s:form id="formone" name="formone"
	namespace="/view/Over2Cloud/Critical" action="lodgeCritical"
	theme="simple" method="post" enctype="multipart/form-data"
	focusElement="callerId">
	<center>
	<div
	style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; height: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	<div id="errZone" style="float: center; margin-left: 7px"
	align="center"></div>
	</div>
	</center>
	<table class="crs" rules="rows" width="99%"
	style="margin-bottom: 10px; margin-left: 0px; margin-top: 5px;">
	<tr height="30px"
	style="background-color: rgba(31, 199, 236, 0.34);">
	<th colspan="6" align="left"><h5 style="margin-left: 1%;">Called
	By</h5></th>
	</tr>

	<tr align="center">
	<td style="text-align: right;"><label for="empId"
	style="font-size: 15px; margin-right: 5%;">Employee ID:</label></td>
	<td><span class="pIds" style="display: none;">caller_emp_id#CallerEmp
	ID#T#sc,</span>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<s:textfield id="caller_emp_id" name="caller_emp_id"
	cssClass="textField" theme="simple"
	placeHolder="Enter Caller EmpID"
	onblur="getDetailsData(this.value,'emp','caller_emp_name','caller_emp_mobile','errZone','caller_emp_id');resetColor('.pIds');" />
	</div>
	</div></td>


	<td style="text-align: right;"><label for="empName"
	style="font-size: 15px; margin-right: 5%;">Employee Name:</label></td>
	<td><span class="pIds" style="display: none;">caller_emp_name#Caller
	Employee Name#T#sc,</span>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<s:textfield id="caller_emp_name" name="caller_emp_name"
	cssClass="textField" placeHolder="Caller Emp Name"
	readonly="false" theme="simple" />
	</div>
	</div></td>
<!--
	validation on called by mobile numbver by aarif

	-->
	<td style="text-align: right;"><label for="empMobile"
	style="font-size: 15px; margin-right: 5%;">Employee Mobile No:</label></td>
	<span class="pIds" style="display: none;">caller_emp_mobile#Caller emp Mobile#T#m,</span>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<s:textfield id="caller_emp_mobile" name="caller_emp_mobile"
	cssClass="textField" placeHolder="Caller Emp Mobile" maxLength="10"
	onblur="checkLodgeNo();" readonly="false" theme="simple" />
	</div>
	</div>
	</td>
	</tr>


	<tr align="center">
	<td style="text-align: right;"><label for="doctor"
	style="font-size: 15px; margin-right: 5%;">Doctor Name:</label></td>
	<td><span class="pIds" style="display: none;" id="tty">doc_name#Doctor Name#T#sc,</span>
	<div class="newColumn" style="width: 99%" id="doc_name_div_">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<sj:autocompleter id="doc_name"  name="doc_name"     selectBox="true" selectBoxIcon="false" placeHolder="Enter Doctor Name"
	cssClass="textField" cssStyle="width:99%" theme="simple" onSelectTopics="selectSpeciality"/>
	</div>
	</div>
	
	<div id="doc_name_div_txt" style="display:none;">
	 <span class="needed"></span>
	<s:textfield id="doc_name_txt" name="doc_name_txt"
	placeHolder="Enter Test Name" theme="simple"
	cssClass="textField"  /></div></td>


	<td style="text-align: right;"><label for="doctor"
	style="font-size: 15px; margin-right: 5%;">Doctor Speciality:</label></td>
	<td> 
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="doc_speciality"
	cssClass="textField" placeHolder="Doctor Speciality"
	maxLength="10" readonly="true" theme="simple" />
	</div>
	</div></td>

	<td style="text-align: right;"><label for="doctormobile"
	style="font-size: 15px; margin-right: 5%;">Doctor Mobile:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">

	<s:textfield id="doc_mobile" name="doc_mobile"
	cssClass="textField" placeHolder="Test Doctor Mobile No"
	onblur="checkLodgeNo();" maxLength="10" readonly="false" theme="simple" />
	</div>
	</div>
	</td>


	</tr>







	<tr style="height: 5px"></tr>
	<tr height="25px"
	style="background-color: rgba(31, 199, 236, 0.34);">
	<th align="left"><h5 style="margin-left: 6%;">UHID</h5></th>
	<td><span class="pIds" style="display: none;">uhid#UHID#T#sc,</span>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<s:textfield id="uhid1" name="uhid" cssClass="textField"
	value="MM" theme="simple"
	onblur="getDetailsData(this.value,'patient','patient_name','errZone','uhid'); checkUHID(this.value);" />
	</div>
	</div>
	</td>
	<td><label for="specimenno"
					style="font-size: 15px;margin-right: 6px;">Specimen No:</label></td>
					<td>
						<div class="newColumn" style="width: 99%">
							<div class="rightColumn" style="width: 99%">
								<s:textfield id="specimen_id" name="specimen_no"
									cssClass="textField" placeHolder="Enter Spacimen No" maxLength="10"
									onblur="getDetailsData(this.value,'patient','patient_name','errZone','uhid'); checkUHID(this.value);"/>
							</div>
						</div>
					</td>
	<td colspan="4">
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<strong>
	<div id="uhidResult1" style="display: block;"></div> <%-- <div id="uhidResult2" onclick="showUHIDGridView();" style="display:none;">View Data</div> --%>
	</strong>

	</div>
	</div>
	</td>

	</tr>





	<tr align="center">


	<td style="text-align: right;"><label for="Pname"
	style="font-size: 15px;margin-right: 6px;">Patient Name:</label></td>
	<td><span class="pIds" style="display: none;">patient_name#Patient Name#T#sc,</span>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<span class="needed"></span>
	<s:textfield id="patient_name" name="patient_name"
	cssClass="textField" placeHolder="Patient Name" theme="simple"
	readonly="true" />
	</div>
	</div>
	</td>
	<td style="text-align: right;"><label for="uhId"
	style="font-size: 15px;margin-right: 6px;">Patient Mobile No:</label></td>
	<td> 
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	 
	<s:textfield id="patient_mobile" name="patient_mob"
	cssClass="textField" placeHolder="Patient Mobile" maxLength="10"
	onblur="checkDocNo();" theme="simple" readonly="true" />
	</div>
	</div>
	</td>

	<td style="text-align: right;"><label for="uhId"
	style="font-size: 15px">Patient Status:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="patient_status" name="patient_status"
	 cssClass="textField"
	placeHolder="Patient Status" theme="simple" readonly="true" />
	</div>
	</div>
	</td>

	</tr>
	<tr align="center">

	<td style="text-align: right;"><label for="admDoc"
	style="font-size: 15px">Admission Doctor:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="addmision_doc_name" name="addmision_doc_name"
	placeHolder="Admission Doctor" readonly="true"
	cssClass="textField" />
	</div>
	</div>
	</td>
	
	<!--
	comment applied due to Requirement as discuss with manish and manab by aarif 02-01-2016

	<td style="text-align: right;"><label for="admDoc"
	style="font-size: 15px">Doctor Mobile No:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="addmision_doc_mobile"
	name="addmision_doc_mobile" placeHolder="Doctor Mobile No"
	onblur="checkDocNo();" readonly="false" cssClass="textField" />
	</div>
	</div>
	</td>



	-->
	
	<td style="text-align: right;"><label for="nursingUnit"
	style="font-size: 15px">Nursing Unit:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="nursing_unit" name="nursing_unit"
	placeHolder="Nursing Unit" readonly="true" theme="simple"
	cssClass="textField" />
	</div>
	</div>
	</td>
	
	
	<td style="text-align: right;"><label for="bedno"
	style="font-size: 15px; margin-right: 5%;">Bed No:</label></td>
	<td >
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="bed_no" name = "bed_no"
	placeHolder="Enter Bed No" readonly="true"
	cssClass="textField" />
	  </div>
	  </div>
	  </td>
	  </tr>
	
	<tr align="center" id="lab2">
	

	 

	
	<td style="text-align: right;"><label for="specialty"
	style="font-size: 15px; margin-right: 0%;">Specialty:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textfield id="specialty" name="specialty"
	placeHolder="Admission Specialty" readonly="true"
	cssClass="textField" />
	</div>
	</div>
	</td>
	</tr>

	<tr align="center">

	
	
	</tr>

	<tr style="height: 5px"></tr>
	<tr height="25px" style="background-color: rgba(31, 199, 236, 0.34);">
	<th align="left"><h5 style="margin-left: 6%; height: 26px;">Test Report For</h5></th>
	<td></td>
	<td colspan="4"></td>
	</tr>

	<tr align="center" id="lab2">
	
	<td style="text-align: right;"><label for="testname"
	style="font-size: 15px; margin-right: 5%;">Test Name:</label></td>
	<td title="Test Name">
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<div id="test_name_div_">
	<span class="pIds" id="tt1" style="display: none;">test_name#Test
	Name#T#sc,</span> <span class="needed"></span>
	<sj:autocompleter id="test_name" name="test_name"
	list="{'No data'}" selectBox="true" selectBoxIcon="false"
	placeHolder="Enter Test Name" cssClass="textField"
	  onSelectTopics="viewUnit"/>


	</div>
	<div id="test_name_div_txt_" style="display:none;">
	<span class="pIds" id="ttt2" style="display: none;">test_name_txt_fd_#Test
	Name#T#sc,</span> <span class="needed"></span>
	<s:textfield id="test_name_txt_fd_" name="test_name_txt"
	placeHolder="Enter Test Name" theme="simple"
	cssClass="textField"  />


	</div>
	<%-- <div id="test_name_textdiv" style="display: none;">
	<s:textfield id="test_name_text" cssClass="textField"
	theme="simple"></s:textfield>
	</div> --%>
	</div>
	</div>
	</td>
	
	<td style="text-align: right;"><label for="testname"
	style="font-size: 15px; margin-right: 5%;">Test For:</label></td>
	<td><span class="pIds" id="tt" style="display: none;">test_type#Test
	For#D#,</span>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:select id="test_type" name="test_type" cssClass="button"
	headerKey="-1" list="{'No Data'}"
	onchange="testName(this.value,'')" theme="simple"
	cssStyle="width:69%;height:26px;" />
	</div>
	</div></td>

	


	<td style="text-align: right;"><label for="testunit"
	style="font-size: 15px; margin-right: 0%;">Test Unit:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 88%">
	<s:textfield id="test_unit" name="test_unit"
	placeHolder="Enter Test Unit" theme="simple"
	cssClass="textField" />

	</div>
	</div>
	</td>

	<!--

	<td style="text-align: right;"><label for="testvalue"
	style="font-size: 15px; margin-right: 5%;">Gender:</label></td>
	<td title="Sub Test Name">
	<span class="pIds" style="display: none; ">gender#Gender#R#,</span> 
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<div id="sub_tets_div">
	<span class="needed"></span>
	  <s:radio label="gender" name="gender" list="#{'Male':'Male','Female':'Female'}" />

	</div>

	</div>
	</div>
	</td>
	-->
	</tr>
	<tr align="center" id="lab">

	</tr>

	<tr align="center" id="lab1">


	<td style="text-align: right;"><label for="testvalue"
	style="font-size: 15px; margin-right: 5%;">Test Value:</label></td>
	<td title="Sub Test Name">
	<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<div id="sub_tets_div">
	<span class="pIds" id="tt" style="display: none;">test_valuec#Test
	Value#T#sc,</span> <span class="needed" style="margin-top: 20px;"></span>
	<s:textarea id="test_valuec" name="test_value"
	placeHolder="Enter Test Unit" theme="simple"
	cssClass="textField" />

	</div>

	</div>
	</div>
	</td>


	<td style="text-align: right;"><label for="comment"
	style="font-size: 15px; margin-right: 0%;">Comments:</label></td>
	<td>
	<div class="newColumn" style="width: 99%">
	<div class="rightColumn" style="width: 99%">
	<s:textarea id="comments_Lodge_form" name="test_comment"
	cssClass="textField" placeHolder="Enter Comments" />
	</div>
	</div>
	</td>
	<td><div id="newDiv"
	style="float: right; width: 100%; margin-top: 0px; margin-right: 112px;">
	<sj:a value="+" onclick="adddiv('aa')" button="true">+</sj:a>
	</div></td>
	<td>
	<div id="min1" style="display: block;"></div>
	<div id="max1" style="display: block;"></div>
	</td>
	</tr>









	<!-- ADD DIV Code  -->


				<!-- --------------------------------------------------------------------------------------------------------------- -->

				<tbody id="a" style="display: none;" class="border">
					<tr align="center">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_a">
										 
										<sj:autocompleter id="test_namea" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit1"  />
									</div>
									
										<div id="test_name_div_txt_a" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd_a" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'a')" />


								</div>

								</div>
							</div>
						</td>
					
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td><span class="pIds" id="t11" style="display: none;"></span>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:select id="test_typea" name="test_type" cssClass="button"
										headerKey="-1" list="{'No Data'}"
										onchange="testName(this.value,'a')" theme="simple"
										  cssStyle="width:69%;height:26px;"/>
								</div>
							</div></td>

						

						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unita" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>



					</tr>


					<tr align="center">



					</tr>

					<tr align="center" id="a1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name"><span class="pIds" id="t13"
							style="display: none;"></span> <%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valuea" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div></td>


						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commenta" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 100%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('ba')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('as')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min2"style="display: block;"></div>
							<div id="max2" style="display: block;"></div>
						</td>
					</tr>

				</tbody>
				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="b" style="display: none;" class="border">

					<tr align="center" id="b2">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_b">
										 
										<sj:autocompleter id="test_nameb" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit2"  />
									</div>
									
										<div id="test_name_div_txt_b" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd_b" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'b')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<span class="pIds" id="t21" style="display: none;"></span>
									<s:select id="test_typeb" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>
						

						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unitb" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>


					</tr>
					<tr align="center">

					</tr>

					<tr align="center" id="b1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name">
							<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="pIds" id="t23" style="display: none;"></span> <span
											class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valueb" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div>
						</td>


						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commentb" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('ca')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('bs')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min3" style="display: block;"></div>
							<div id="max3" style="display: block;"></div>
						</td>
					</tr>
				</tbody>
				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="c" style="display: none;" class="border">

					<tr align="center" id="c2">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_c">
										 
										<sj:autocompleter id="test_namec" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit3"  />
									</div>
									
										<div id="test_name_div_txt_c" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd_c" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'c')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<span class="pIds" id="t31" style="display: none;"></span>
									<s:select id="test_typec" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>


						
						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unitc" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>

					</tr>


					<tr align="center">

					</tr>

					<tr align="center" id="c1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name"><span class="pIds" id="t33"
							style="display: none;"></span> <%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valuec" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div></td>





						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commentc" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('ea')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('cs')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min4" style="display: block;"></div>
							<div id="max4" style="display: block;"></div>
						</td>
					</tr>
				</tbody>

				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody id="d" style="display: none;" class="border">
					<tr align="center" id="d2">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_d">
										 
										<sj:autocompleter id="test_named" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit4" />
									</div>
										<div id="test_name_div_txt_d" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd_d" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'d')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:select id="test_typed" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>

						
						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unitd" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>

					</tr>

					<tr align="center" id="d1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name">
							<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valued" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />
									</div>
									<%-- <div id="sub_test_textdiv" style="display: none;">
	<s:textfield id="subtest_name_text" cssClass="textField" theme="simple"></s:textfield>
	</div> --%>
								</div>
							</div>
						</td>




						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commentd" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('da')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('d')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min5" style="display: block;"></div>
							<div id="max5" style="display: block;"></div>
						</td>
					</tr>
				</tbody>
				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="e" style="display: none;" class="border">

					<tr align="center" id="e2">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									< 
										<sj:autocompleter id="test_namee" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit5"  />
									</div>
									
										<div id="test_name_div_txt_e" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'e')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<span class="pIds" id="t41" style="display: none;"></span>
									<s:select id="test_typee" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>
						
						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unite" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>
					</tr>

					<tr align="center" id="e1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name">
							<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<span class="pIds" id="t43" style="display: none;"></span>
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valuee" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div>
						</td>


						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commente" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('fa')" button="true">+</sj:a>
						
								<sj:a value="-" onclick="deldiv('es')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min6" style="display: block;"></div>
							<div id="max6" style="display: block;"></div>
						</td>
					</tr>
				</tbody>

				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="f" style="display: none;" class="border">

					<tr align="center" id="f2">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_f">
										 
										 
										<sj:autocompleter id="test_namef" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit6"  />
									</div>
									
										<div id="test_name_div_txt_f" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd_f" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'f')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<span class="pIds" id="t51" style="display: none;"></span>
									<s:select id="test_typef" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>
						



						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unitf" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>



					</tr>

					<tr align="center" id="f1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name"><span class="pIds" id="t53"
							style="display: none;"></span> <%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valuef" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div></td>

						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commentf" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('ga')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('fs')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min7" style="display: block;"></div>
							<div id="max7" style="display: block;"></div>
						</td>
					</tr>
				</tbody>


				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="g" style="display: none;" class="border">

					<tr align="center" id="g2">
					
						<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_g">
									 
										<sj:autocompleter id="test_nameg" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit7"  />
									</div>
									
										<div id="test_name_div_txt_g" style="display:none;">
								 
								<s:textfield id="test_name_txt_fd_g" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'g')" />


								</div>

								</div>
							</div>
						</td>
					
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:select id="test_typeg" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>

					
						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unitg" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>

					</tr>



					<tr align="center" id="g1" style="display: none;">

						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name">
							<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valueg" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div>
						</td>




						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commentg" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('ia')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('gs')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min8" style="display: block;"></div>
							<div id="max8" style="display: block;"></div>
						</td>
					</tr>
				</tbody>

				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="i" style="display: none;" class="border">

					<tr align="center" id="i2">
					
					<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_i">
										 
										<sj:autocompleter id="test_namei" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit8"  />
									</div>
									
										<div id="test_name_div_txt_i" style="display:none;">
								 
								<s:textfield id="test_name_txt_fd_i" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'i')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:select id="test_typei" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>

						

						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_uniti" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>
					</tr>




					<tr align="center" id="i1" style="display: none;">


						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name">
							<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valuei" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div>
						</td>

						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commenti" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>

						<td><div id="newDiv"
								style="float: right; width: 80%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="+" onclick="adddiv('ja')" button="true">+</sj:a>
							
								<sj:a value="-" onclick="deldiv('is')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min9" style="display: block;"></div>
							<div id="max9" style="display: block;"></div>
						</td>
					</tr>
				</tbody>

				<!--------------------------------------------------------------------------------------------------------------------------------->
				<tbody align="center" id="j" style="display: none;" class="border">

					<tr align="center" id="j2">
					
						<td><label for="testname"
							style="font-size: 15px;">Test Name:</label></td>
						<td title="Test Name">
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="test_name_div_j">
										 
										<sj:autocompleter id="test_namej" name="test_name"
											list="doctorNameMap" selectBox="true" selectBoxIcon="false"
											placeHolder="Enter Test Name" cssClass="textField"
											cssStyle="width:99%" theme="simple"
											onSelectTopics="viewUnit9" />
									</div>
									
										<div id="test_name_div_txt_j" style="display:none;">
									 
								<s:textfield id="test_name_txt_fd_j" name="test_name_txt"
									placeHolder="Enter Test Name" theme="simple"
									cssClass="textField" onblur="isNull_testName_txt(this.value,'j')" />


								</div>

								</div>
							</div>
						</td>
					
						<td><label for="testname"
							style="font-size: 15px;">Test For:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:select id="test_typej" name="test_type" cssClass="button"
										list="{'No Data'}" onchange="testName(this.value)"
										theme="simple" cssStyle="width:69%;height:26px;" />
								</div>
							</div>
						</td>



					

						<td><label for="testunit"
							style="font-size: 15px;">Test Unit:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 88%">
									<s:textfield id="test_unitj" name="test_unit"
										placeHolder="Enter Test Unit" theme="simple"
										cssClass="textField" />

								</div>
							</div>
						</td>

					</tr>

					<tr align="center" id="j1" style="display: none;">


						<td><label for="testvalue"
							style="font-size: 15px;">Test Value:</label></td>
						<td title="Sub Test Name">
							<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<div id="sub_tets_div">
										<span class="needed" style="margin-top: 20px;"></span>
										<s:textarea id="test_valuej" name="test_value"
											placeHolder="Enter Test Unit" theme="simple"
											cssClass="textField" />

									</div>

								</div>
							</div>
						</td>





						<td><label for="comment"
							style="font-size: 15px;">Comments:</label></td>
						<td>
							<div class="newColumn" style="width: 99%">
								<div class="rightColumn" style="width: 99%">
									<s:textarea id="commentj" name="test_comment"
										cssClass="textField" placeHolder="Enter Comments" />
								</div>
							</div>
						</td>


						<td><div id="newDiv"
								style="float: right; width: 250%; margin-top: 0px; margin-right: 112px;">
								<sj:a value="-" onclick="deldiv('js')" button="true">-</sj:a>
							</div></td>
						<td>
							<div id="min10" style="display: block;"></div>
							<div id="max10" style="display: block;"></div>
						</td>
					</tr>
				</tbody>


				<!--  End ADD Div -->
	<!-- </tr> -->
	</table>


	<center>
	<img id="indicator23" src="<s:url value="/images/indicator.gif"/>"
	alt="Loading..." style="display: none" />
	</center>
	<div class="clear" style="margin-top: 1px; margin-bottom: 2px;"></div>
	<center>
	<div class="type-button">
	<sj:submit targets="result" clearForm="false" value="Add"
	effect="highlight" effectOptions="{ color : '#FFFFFF'}"
	effectDuration="100" button="true" onCompleteTopics="level"
	cssStyle="height:26px;" cssClass="submit" indicator="indicator23"
	onBeforeTopics="validate" />

	&nbsp;&nbsp;
	<sj:submit value="Reset" button="true"
	cssStyle="margin-left: -2px;margin-top: 0px;height:26px;"
	onclick="resetForm();resetColor('.pIds');lab('Lab');" />

	</div>

	</center>

	</s:form>
	</div>
	
	<div id="noti" style="width: 12%; float: left;  height:475px;border: 1px solid rgb(255, 255, 255);  background: rgb(250, 250, 250);  border-radius: 8px;box-shadow: 5px 5px 1px -1px rgb(221, 221, 221);  " >
</div>
</html>