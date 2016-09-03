<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>

<script type="text/javascript" src="<s:url value="/js/referral/referralValidation.js"/>"></script>
 
<STYLE type="text/css">
td.tdAlign {
	padding: 5px;
}
 
.textField
{
	height:20px;	
}

.textField:focus { 
    background-color: #E5F2FF;
}
span.needed
{
	height:26px;	
}

 td:hover{
 
  -webkit-box-shadow: 0.5px 1px 3px 0px rgba(0,0,0,0.5);
  box-shadow: 0.5px 1px 3px 0px rgba(0,0,0,0.5);
  border-radius: 2px;
}
	
 .ui-autocomplete-input {
	width:209px;
	height:26px;
}
.ui-autocomplete { 
height: 200px; 
overflow-y: 
scroll; 
overflow-x: hidden;
}

.ui-button .ui-widget .ui-state-default .ui-button-icon-only .ui-corner-right .ui-button-icon
{
	width:22px;
	height:26px;
	left:0px;
}

tr.color {
	background-color: #E6E6E6;
	border-color: #060606;
	border: 1;
	 
}


</STYLE>
<SCRIPT type="text/javascript">

	
	$.subscribe('level1', function(event, data) {
	 	$("#completionResult").dialog({title: 'Confirmation Message',width: 400,height: 100});
		$("#completionResult").dialog('open');
		
		clearFields();
		 onSearchData();
	 		fetchDropDownData();
	 		getStatusCounter();
 		var uh=$("#uhid").val();
 		checkUHID(uh);
	});

	 
	 
	function clearFields()
	{
		
		//$("#completionResult").dialog('close');
	
		$("#").attr("readonly", true);
	  	$('#ref_to_sub_spec').val("");
		$('#reason').val("");
		$('#priority').val("-1");
		$("#ref_id_div1").hide();
		$("#ref_id_div2").show();
		$("#ref_name_div1").show();
		$("#ref_name_div2").hide();
		$("#ref_spec_div1").hide();
		$("#ref_spec_div2").show();
		$("#refto_id_div1").show();
		$("#refto_id_div2").hide();
		
		$("#refto_name_div2").hide();
		
		$("#refto_spec_div2").hide();

		$("#refto_id_div1").show();
		$("#refto_spec_div1").show();
		$("#refto_name_div1").show();
		
	 		
	}
	function resetFormField(id) {
	    $('#' + id).val(function() {
	        return this.defaultValue;
	    });
	}
	function setDocDataADD()
	{
		var n=docIds.length;
		console.log(n);
 		if(n > 0)
		{
			
			$('#ref_to_emp_id_widget option').remove();
			$(docIds).each(function(){
			       
			       $('#ref_to_emp_id_widget').append($("<option></option>").attr("value",this.value).text(this.text));
			});
		}	
		var n1=docNames.length;
		if(n1 > 0)
		{
			
			$('#ref_to_widget option').remove();
			$(docNames).each(function(){
			      
			       $('#ref_to_widget').append($("<option></option>").attr("value",this.value).text(this.text));
			});
		}
	}
/* 	function cancelButton() {
		//viewMainPage();
		//onSearchData();
	} */
	function viewMainPage() {
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/Referral/beforeReferralViewHeader.action",
			success : function(data) {
				$("#" + "data_part").html(data);
			},
			error : function() {
				alert("error");
			}
		});
	}
	var docIds="";
	var docNames="";
	function resetForm(formId) {
		
	 	$('#' + formId).trigger("reset");
		$("#ref_id_div1").show();
		$("#ref_spec_div1").show();
		$("#ref_name_div1").show();
		$("#refto_id_div1").show();
		$("#refto_spec_div1").show();
		$("#refto_name_div1").show();
		$("#ref_id_div2").hide();
		$("#ref_spec_div2").hide();
		$("#ref_name_div2").hide();
		$("#refto_id_div2").hide();
		$("#refto_spec_div2").hide();
		$("#refto_name_div2").hide();
		setDocData();
		 $("#"+"uhidResult1").html("");
	}
	
	function setDocData()
	{
		var n=docIds.length;
		console.log(n);
 		if(n > 0)
		{
			$('#ref_by_emp_id_widget option').remove();
			$('#ref_to_emp_id_widget option').remove();
			$(docIds).each(function(){
			       $('#ref_by_emp_id_widget').append($("<option></option>").attr("value",this.value).text(this.text));
			       $('#ref_to_emp_id_widget').append($("<option></option>").attr("value",this.value).text(this.text));
			});
		}	
		var n1=docNames.length;
		if(n1 > 0)
		{
			$('#ref_by_widget option').remove();
			$('#ref_to_widget option').remove();
			$(docNames).each(function(){
			       $('#ref_by_widget').append($("<option></option>").attr("value",this.value).text(this.text));
			       $('#ref_to_widget').append($("<option></option>").attr("value",this.value).text(this.text));
			});
		}
	}
	$.subscribe('getRefByDetails', function(event,data)
	{
		getDetails("ref_spec_widget","ref_by_emp_id_widget","ref_by_widget");
	});
	
	$.subscribe('getRefToDetails', function(event,data)
	{
		getDetails("reffered_spec_widget","ref_to_emp_id_widget","ref_to_widget");
	});
	
	function getDetails(sourceId,targetId1,targetId2)
	{
		var data=$("#"+sourceId+" option:selected").val();
		docIds=$("#"+targetId1+" option");
		$("#"+targetId1+" option").remove();
		$(docIds).each(function(){
		    if (this.value.indexOf(data)>0) {
		       $('#'+targetId1).append($("<option></option>").attr("value",this.value).text(this.text));
		    }
		});
		docNames=$("#"+targetId2+" option");
		//alert("DocName:   "+docNames); Next Escalation On
		//console.log(temp);
		$("#"+targetId2+" option").remove();
		$(docNames).each(function(){
		    if (this.value.indexOf(data)>0) {
		       $('#'+targetId2).append($("<option></option>").attr("value",this.value).text(this.text));
		    }
		});
	}
	$.subscribe('fetchDoctorId', function(event,data)
	{
		showDetails("ref_by_widget","ref_by_spec","ref_by_id","ref_id_div2","ref_spec_div2","ref_id_div1","ref_spec_div1","");
	});
	
	$.subscribe('fetchDoctorName', function(event,data)
	{
		showDetails("ref_by_emp_id_widget","ref_by_name","ref_by_spec","ref_name_div2","ref_spec_div2","ref_name_div1","ref_spec_div1","ref_by");
	});
	
	$.subscribe('fetchRefDocId', function(event,data)
	{
		showDetails("ref_to_widget","ref_to_spec","ref_to_id","refto_id_div2","refto_spec_div2","refto_id_div1","refto_spec_div1","");
	});
			
	$.subscribe('fetchRefDocName', function(event,data)
	{
		showDetails("ref_to_emp_id_widget","ref_to_name","ref_to_spec","refto_name_div2","refto_spec_div2","refto_name_div1","refto_spec_div1","ref_to");
	});
	
	function showDetails(sourceId,targetId1,targetId2,showId1,showId2,hideId1,hideId2,id)
	{
		var temp=$("#"+sourceId+" option:selected").val();
		console.log(temp);
	 	$("#"+targetId1).val(temp.split("#")[1]);
		$("#"+targetId2).val(temp.split("#")[2]);
		$("#"+showId1).show();
		$("#"+showId2).show();
		$("#"+hideId1).hide();
		$("#"+hideId2).hide();
		if(id=="ref_to" || id=="ref_by")
		{
			$("#"+id).val(temp.split("#")[0]);
		}	
	}

	/* $.subscribe('refSpec', function(event,data)
	{
		var value=$("#ref_by_widget option:selected").val();
		//console.log(value);
//		console.log($("#ref_spec_widget option").eq(value).text());
		$("#ref_spec_widget").text($("#ref_spec_widget option").eq(value).text());
		$("#ref_by_emp_id_widget").text($("#ref_by_emp_id_widget option").eq(value).text());
	});
	
	$.subscribe('refdSpec', function(event,data)
	{
		$("#reffered_spec").val($("#ref_to_widget option:selected").val().split("#")[1]);
	}); */


	function showCurrentData(){
		$("#result").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var val=$("#uhid").val();	
				if(val.trim().length > 2){
					$("#completionResult").dialog({title: 'View for UHID: '+val,width: 1000,height: 520});
					$("#completionResult").dialog('open');
					
							$.ajax({
							    type : "post",
							    url : "view/Over2Cloud/Referral/beforeReferralUHID.action?uhidStatus="+val,
							    success : function(data) {
							    	  $("#"+"result").html(data);
							},
							   error: function() {
						            alert("error");
						        }
							 });			
					setTimeout(function() {$("#result").val(""); }, 3000);
				}
		}
	
	function checkUHID(val){

		setDocDataADD();
			if(val.trim().length > 2){

						$.ajax({
						    type : "post",
						    url : "view/Over2Cloud/Referral/UHIDCheck.action?searchString="+val,
						    success : function(data) {
						   var status=data.uhidStatus;
						  	   $("#"+"uhidResult1").html(status);
							    
						},
						   error: function() {
					            alert("error");
					        }
						 });
				}
		}





	

	
</script>
</head>
<!-- <div style=" 
  margin-left: 51%;
  width: 86%;
  margin-top: -65px;"> -->
<div class="lodgeId">

	<sj:dialog id="completionResult" showEffect="slide"
		hideEffect="explode"
		autoOpen="false"
		cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
		modal="true" draggable="true"
		resizable="true"
		>
		<div id="result"></div>
	</sj:dialog>
	
	
	
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/Referral" action="addReferral" theme="simple" method="post" enctype="multipart/form-data" focusElement="callerId">
		<s:hidden name="ifrepete" value="false"></s:hidden>
		
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%;height:100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px" align="center"></div>        
	        </div>
        </center>
		<table  rules="rows" width="100%" style="margin-bottom: 10px;margin-left: 0px;margin-top: 5px;" border="1">
	 		<tr height="30px" style="background-color: rgba(31, 199, 236,0.34);">
					<th colspan="6" align="left"><h5><b>Called By:</b></h5></th>
			</tr>
			
			<tr align="center">
		  	  	<td colspan="2">
		  	  	<label for="empId" style="font-size: 12px">Employee Id:</label>
				</td>
				<td  >
				<span class="pIds" style="display: none; ">caller_emp_id#Caller Emp ID#T#sc,</span>
				 <div class="newColumn" style="width: 99%">
			 		<div class="rightColumn" style="width: 99%">
						<span class="needed"></span>
					      <s:textfield
								id="caller_emp_id" name="caller_emp_id" autoFocus="true" cssClass="textField" theme="simple" placeHolder="Enter Caller EmpID"   onblur="getDetailsData(this.value,'emp','caller_emp_name','errZone','caller_emp_id');resetColor('.pIds');"/>
					</div>
				 </div>
				 </td>
				 
				 
				 <td colspan="2">
		  	  	<label for="empName" style="font-size: 12px">Employee Name:</label>
				</td>
				 <td  >
					 <div class="newColumn" style="width: 99%">
				 		<div class="rightColumn" style="width: 99%">
						      <s:textfield id="caller_emp_name" name="caller_emp_name" cssClass="textField"  placeHolder="Caller Emp Name" readonly="true" theme="simple"/>
						</div>
					 </div>
				 </td>
			</tr>
		  
		  <tr style="height:5px"></tr>
		  		<tr style="background-color:rgba(31, 199, 236,0.34);">
					<th align="left"><h5 style="margin-left: 0%;"><b>Patient Detail:</b></h5></th>
		 		<td>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
					       <s:select
							id="patient_type"  cssClass="button" list="{'IPD','Emergency'}" theme="simple" cssStyle="width:83%;height:26px;" onchange="fetchPatientType(this.value)"/>
					</div>
				 </div>
				 </td>
				 <td  colspan="4">
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
					      <strong> <div id="uhidResult1" style="display:block;" ></div>
					     <%-- <div id="uhidResult2" onclick="showUHIDGridView();" style="display:none;">View Data</div> --%>
					      </strong>
					      
					</div>
				 </div>
				 </td>
			</tr>
			
			<tr align="center">
				<td>
		  	  	<label for="uhId" style="font-size: 12px">Patient UHID:</label>
				</td>
				<td>
				<span class="pIds" style="display: none; ">uhid#Patient UHID#T#sc,</span>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
						<span class="needed"></span>
					       <s:textfield
							id="uhid" name="uhid" cssClass="textField"    value="MM"  theme="simple" onblur="getDetailsData(this.value,'patient','patient_name','errZone','uhid'); checkUHID(this.value);"/>
					</div>
				 </div>
				 </td>
				 
				 <td>
		  	  	<label for="Pname" style="font-size: 12px">Patient Name:</label>
				</td>
				 <td>
				 <span class="pIds" style="display: none; ">patient_name#Patient Name#T#sc,</span>
				 	 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
						      <s:textfield id="patient_name" name="patient_name" cssClass="textField" placeHolder="Patient Name" theme="simple" readonly="false" />
						</div>
					 </div>
					  
				 </td>
			   <td>
		  	  	<label for="bedNo" style="font-size: 12px">Bed No:</label>
				</td>
				<td>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
					       <s:textfield id="bed" name="bed" placeHolder="Bed No." readonly="true" theme="simple" cssClass="textField" />
					</div>
				 </div>
				 </td>
			 	 </tr>
				  	
				
				
				<tr align="center">
				 <td>
		  	  	<label for="nursingUnit" style="font-size: 12px">Nursing Unit:</label>
				</td>
				<td>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
						      <s:textfield id="nursing_unit" name="nursing_unit" placeHolder="Nursing Unit" readonly="true" theme="simple" cssClass="textField" />
						</div>
					 </div>
				 </td>
			 
			 
			 	<td>
		  	  	<label for="admDoc" style="font-size: 12px">Admission Doctor:</label>
				</td>
				<td>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
					       <s:textfield id="adm_doc" name="adm_doc" placeHolder="Admission Doctor" readonly="true" cssClass="textField" />
					</div>
				 </div>
				 </td>
				 
				 
				 <td>
		  	  	<label for="admSpec" style="font-size: 12px">Admission Specialty:</label>
				</td>
				 <td>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
						      <s:textfield id="adm_spec" name="adm_spec" placeHolder="Admission Specialty" readonly="true" cssClass="textField" />
						</div>
					 </div>
				 </td>
			</tr>
		
		
		
		
		 <tr style="height:5px"></tr>
		 
			<tr height="30px" style="background-color: rgba(31, 199, 236,0.34);">
					<th colspan="6" align="left"><h5 style="margin-left: 0%;"><b>Referral By:</b></h5></th>
			</tr>
			
			<tr align="center">
			<td>
		  	  	<label for="docName" style="font-size: 12px">Doctor Name:</label>
			</td>
			<td title="Referral Doctor Name">
			<span class="pIds" style="display: none; ">ref_by#Doctor Name#T#sc,</span>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%" >
							<div id="ref_name_div1" style="width: 99%">
							<span class="needed"></span>
								<sj:autocompleter id="ref_by"  name="ref_by" list="doctorNameMap"   selectBox="true" selectBoxIcon="false" placeHolder="Enter Referral Doctor"
					       			cssClass="textField" cssStyle="width:99%" theme="simple" onSelectTopics="fetchDoctorId" />
							</div>
					    	<div id="ref_name_div2" style="display: none;">
					    		<s:textfield id="ref_by_name"  cssClass="textField" theme="simple"></s:textfield>
					    	</div>
						</div>
					 </div>
				 </td>
				 
				 <td>
		  	  	<label for="docId" style="font-size: 12px">Doctor Id:</label>
				</td>
				<td title="Referral Doctor ID">
				<%-- <span class="pIds" style="display: none; ">ref_by#Referral Doctor#T#sc,</span> --%>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
						<div id="ref_id_div1">
							<sj:autocompleter id="ref_by_emp_id"    list="doctorIdMap" selectBox="true" selectBoxIcon="false" 
					       	cssClass="textField" theme="simple" onSelectTopics="fetchDoctorName"/>
						</div>
					    <div id="ref_id_div2" style="display: none;">
					    	<s:textfield id="ref_by_id" cssClass="textField" theme="simple"></s:textfield>
					    </div>    
					</div>
				 </div>
				 </td>
			 
			 
			 	<td>
		  	  	<label for="docSpec" style="font-size: 12px">Doctor Specialty:</label>
				</td>
				<td title="Referral Doctor Specialty">
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%" id="ref_by_specl">
							<div id="ref_spec_div1">
								<sj:autocompleter id="ref_spec"  list="doctorSpecSet" selectBox="true" selectBoxIcon="false" 
					    		   	cssClass="textField" theme="simple" onSelectTopics="getRefByDetails"/>
							</div>
					    	<div id="ref_spec_div2" style="display: none;">
					    		<s:textfield id="ref_by_spec"  cssClass="textField" theme="simple"></s:textfield>
					    	</div>
					</div>
				 </div>
				 </td>
		 	</tr>
		
		
		
		
		
		 <tr style="height:5px"></tr>
		 
			<tr height="30px" style="background-color: rgba(31, 199, 236,0.34);">
				<th colspan="6" align="left"><h5 style="margin-left: 0%;"><b>Referred To:</b></h5></th>
			</tr>
			
			<tr align="center">
				<td>
		  	  	<label for="docNameTo" style="font-size: 12px">Doctor Name:</label>
				</td>
		 	 <td title="Referred Doctor Name">
		 	 <span class="pIds" style="display: none; ">ref_to#Doctor Name#T#sc,</span>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
							<div id="refto_name_div1">
							<span class="needed"></span>
								<sj:autocompleter id="ref_to" name="ref_to" list="doctorNameMap" selectBox="true" selectBoxIcon="false" 
					       				cssClass="textField" theme="simple"    onSelectTopics="fetchRefDocId"/>
							</div>
					    	<div id="refto_name_div2" style="display: none;">
					    		<s:textfield id="ref_to_name"  cssClass="textField" theme="simple"></s:textfield>
					    	</div>
						</div>
					 </div>
				 </td>
				 
				<td>
		  	  	<label for="docIdTo" style="font-size: 12px">Doctor Id:</label>
				</td>
				<td title="Referred Doctor ID">
			 	 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
							<div id="refto_id_div1">
								<sj:autocompleter id="ref_to_emp_id" name="ref_to_emp_id"   list="doctorIdMap" selectBox="true" selectBoxIcon="false" 
					       			cssClass="textField" theme="simple" onSelectTopics="fetchRefDocName"/>
							</div>
					    	<div id="refto_id_div2" style="display: none;">
					    		<s:textfield id="ref_to_id"  cssClass="textField" theme="simple"></s:textfield>
					    	</div>
					</div>
				 </div>
				 </td>
				
				 <td>
		  	  	<label for="docSpecTo" style="font-size: 12px">Doctor Specialty:</label>
				</td>
				<td title="Referred Doctor Specialty">
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
							<div id="refto_spec_div1">
								<sj:autocompleter id="reffered_spec"  list="doctorSpecSet" selectBox="true" selectBoxIcon="false" 
					       				cssClass="textField" theme="simple" onSelectTopics="getRefToDetails"/>
							</div>
					    	<div id="refto_spec_div2" style="display: none;">
					    		<s:textfield id="ref_to_spec"  cssClass="textField" theme="simple"></s:textfield>
					    	</div>
					</div>
				 </div>
				 </td>
				 </tr>
				 
				<tr align="center">
				 <td>
		  	  	<label for="subSpec" style="font-size: 12px">Sub Specialty:</label>
				</td>
				<td>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
								<s:textfield id="ref_to_sub_spec" name="ref_to_sub_spec"   placeHolder="Enter Sub Specialty" theme="simple" cssClass="textField" />
						     
						</div>
					 </div>
				 </td>
		 	
		 
				 <td>
		  	  	<label for="priority" style="font-size: 12px">Priority:</label>
				</td>
				<td>
				 <span class="pIds" style="display: none; ">priority#Priority#D#,</span>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
						<span class="needed"></span>
						 <s:select id="priority" name="priority" list="#{'Routine':'Routine','Urgent':'Urgent','Stat':'Stat'}" headerKey="-1" headerValue="Select Priority"
							  	theme="simple" cssClass="button" cssStyle="width:84%;height:26px;" />
					</div>
				 </div>
				 </td>
				 
				 <td>
		  	  	<label for="reason" style="font-size: 12px">Reason:</label>
				</td>
				 <td>
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
					       <s:textfield id="reason" name="reason"    cssClass="textField" placeHolder="Enter Reason" />
					</div>
				 </div>
				 </td>
			</tr>
		<!-- </tr> -->
	</table>	 

	
		<center>
			<img id="indicator23" src="<s:url value="/images/indicator.gif"/>"
				alt="Loading..." style="display: none" />
		</center>
		<div class="clear" style="margin-top: 1px;margin-bottom:2px;" ></div>
		<center>
			<div class="type-button">
				<sj:submit targets="result" clearForm="false" value="Add"
					effect="highlight" effectOptions="{ color : '#FFFFFF'}"
				  button="true" onCompleteTopics="level1" cssStyle="height:26px;"
					  cssClass="submit" indicator="indicator23" onBeforeTopics="validate"/>

				&nbsp;&nbsp;
				<sj:submit value="Reset" button="true"  
					cssStyle="margin-left: -2px;margin-top: 0px;height:26px;"
					onclick="resetForm('formone');resetColor('.pIds');" />

			</div>
			
		</center>

	</s:form>
</div>
</html>